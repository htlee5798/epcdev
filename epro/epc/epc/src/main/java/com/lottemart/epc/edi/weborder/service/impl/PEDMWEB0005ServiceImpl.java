package com.lottemart.epc.edi.weborder.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.RuntimeException;






import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lcn.module.framework.property.ConfigurationService;






import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.weborder.dao.PEDMWEB0005Dao;
import com.lottemart.epc.edi.weborder.model.EdiRtnPackListVO;
import com.lottemart.epc.edi.weborder.model.EdiRtnPackVO;
import com.lottemart.epc.edi.weborder.model.EdiRtnProdListVO;
import com.lottemart.epc.edi.weborder.model.EdiRtnProdProcessVO;
import com.lottemart.epc.edi.weborder.model.EdiRtnProdVO;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.service.PEDMWEB0005Service;
import com.lottemart.epc.util.Paging;
import com.lottemart.epc.util.PagingFactory;

@Service("pEDMWEB0005Service")
public class PEDMWEB0005ServiceImpl implements PEDMWEB0005Service {

	public String DefaultPagePerCount = "10";
	public String DefaultPageRowCount = "15";

	@Resource(name = "configurationService")
	private ConfigurationService config;

	@Autowired
	private PEDMWEB0005Dao pedmweb0005dao;



	/**
     * 반품상품 저장
     * @param EdiRtnProdVO
     * @return HashMap<String, String>
     * @throws Exception
    */
	public HashMap<String, String> insertReturnProdData(EdiRtnProdVO vo, HttpServletRequest request) throws Exception{

		/**
		 *  state
		 *  0  : 정상 등록         			( SUCCESS          )
		 *  1  : 중복 등록 오류    			( DUPLICATE DATA   )
		 *  2  : 반품불가 상품   			    ( EDI_RTN_PROD_V1@DL_MD_MARTNIS   0 row  )
		 *  3  : TED_PO_RRL_PROD 미등록      ( TED_PO_RRL_PROD  0 row inserted error )
		 * -1  : 시스템 오류    				(exception message)
		 */
		HashMap<String, String> rtnData = new HashMap<String, String>();
		rtnData.put("state", "-1");

		/* ==[ 작업자 설정(로그인사용자 아이디 ] ============================== --*/
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
		vo.setWorkUser(epcLoginVO.getCono()[0]);
		/* ================================================================ --*/


		/* ==[ 반품저장 전 점포별상품 존재여부  검사 ] ======================== --*/
		int rtnProCnt = pedmweb0005dao.selectRtnProdCnt(vo);
		/*MARTNIS 없는상품 상품일경우 error code '2' Return --*/
		if(rtnProCnt <= 0 ) {
			rtnData.put("state", 	"2");
			rtnData.put("message", 	"EDI_RTN_PROD_V1@DL_MD_MARTNIS No Data Found! ");
			return rtnData;
		}
		/* ================================================================ --*/


		/* ==[ 반품저장 전 점포별상품 중복  검사 ] ============================ --*/
		int rtnDupCnt = pedmweb0005dao.selectRtnProdDuplicateCnt(vo);	//점포별 상품 등록 중복확인 Count

		/* 기등록 상품일경우 error code '1' Return --*/
		if(rtnDupCnt > 0 ) {
			rtnData.put("state", 	"1");
			rtnData.put("message", 	"DUPLICATE DATA");
			return rtnData;
		}
		/* ================================================================ --*/


		/* ==[ 반품상품저장 ] ============================================== --*/

		/*-- 반품상품 점별 마스터 생성   (INSERT TED_PO_RRL_MST) --*/
		pedmweb0005dao.saveRtnProdMst(vo);
		/*-- ----------------------------------------------------*/

		/*-- 반품상품 점별 상품목록 생성 (INSERT TED_PO_RRL_PROD) ---*/
		int prodCnt = pedmweb0005dao.saveRtnProdList(vo);
		if(prodCnt <= 0){
			// 해당 EXCEPTION 발생할경우 참조 하고있는 TABLE DATA 이상함...
			throw new IllegalArgumentException("TED_PO_RRL_PROD  0 row inserted error");
		}
		/*-- ----------------------------------------------------*/

		/*-- 반품상품 점별 마스터 합계   (UPDATE TED_PO_RRL_MST) ---*/
		pedmweb0005dao.saveRtnrodSum(vo);
		/*-- ----------------------------------------------------*/

		/* 반품 등록 정상 Return Data set --------------------- --*/
		rtnData.put("state", 	"0");
		rtnData.put("message", 	"SUCCESS");
		/*-- ----------------------------------------------------*/

		/* ================================================================ --*/

		return rtnData;
	}

	/**
	 *  당일 반품등록 내역 조회(합계 / 반품목록)
	 * @param SearchWebOrder
	 * @return List<EdiRtnProdVO>
	 * @throws SQLException
	 */
	public Map<String,Object> selectDayRtnProdList(SearchWebOrder vo, HttpServletRequest request)  throws Exception {


		/**
		 *  state
		 *  0  : 정상 등록      ( SUCCESS          )
		 * -1  : 시스템 오류    (exception message)
		 */
		Map<String,Object>  result	    = new HashMap<String,Object>();
		result.put("state","-1");

		EdiRtnProdVO		sumData     = null;
		List<EdiRtnProdVO> 	listData	= null;

		sumData 	= pedmweb0005dao.selectDayRtnProdSum(vo); 		// 합계
		listData	= pedmweb0005dao.selectDayRtnProdList(vo);		// 목록

		result.put("sumData", 		sumData);
		result.put("listData", 		listData);

		result.put("state",		"0");		// 처리상태
		result.put("message",	"SUCCESS");	// 처리메시지

		return result;
	}

	/**
	 *  당일 반품등록 내역 조회(합계 / 반품목록)
	 * @param SearchWebOrder
	 * @return List<EdiRtnProdVO>
	 * @throws SQLException
	 */
	public Map<String,Object> selectDayRtnProdList2(SearchWebOrder vo, HttpServletRequest request)  throws Exception {


		/**
		 *  state
		 *  0  : 정상 등록      ( SUCCESS          )
		 * -1  : 시스템 오류    (exception message)
		 */
		Map<String,Object>  result	    = new HashMap<String,Object>();
		result.put("state","-1");

		EdiRtnProdVO		sumData     = null;
		List<EdiRtnProdVO> 	listData	= null;

		sumData 	= pedmweb0005dao.selectDayRtnProdSum2(vo); 		// 합계
		listData	= pedmweb0005dao.selectDayRtnProdList2(vo);		// 목록

		result.put("sumData", 		sumData);
		result.put("listData", 		listData);

		result.put("state",		"0");		// 처리상태
		result.put("message",	"SUCCESS");	// 처리메시지

		return result;
	}

	/**
	 * TODAY 협력사 반품등록 삭제 리스트 처리
	 * @Method Name : deleteReturnProdData
	 * @param EdiRtnProdProcessVO
	 * @return HashMap<String, String>
	 */
	public HashMap<String, String> deleteReturnProdData(EdiRtnProdProcessVO vo, HttpServletRequest request)  throws Exception{

		HashMap<String, String> rtnData = new HashMap<String, String>();
		rtnData.put("state","-1");

		EdiRtnProdListVO rtnDeleteList = vo.getEdiRtnProdListVO();

		/*상품상세 삭제대상 parameter list 유무 확인--------------------------------*/
		if(rtnDeleteList== null || rtnDeleteList.size() <= 0){
			rtnData.put("state","1");
			rtnData.put("message","No Data Found!");
			return rtnData;
		}
		/*-----------------------------------------------------------------------*/

		/*상품상세 삭제(TED_PO_RRL_PROD 삭제)--------------------------------------*/
		pedmweb0005dao.deleteRtnrodListBatch(rtnDeleteList);
		/*-----------------------------------------------------------------------*/




		/*RRL_REQ_NO 추출  TED_PO_RRL_MST 삭제------------------------------------*/
		String rrlReqNo    = "";
		String rrlReqNoOld = "";
		String venCd       = "0";
		for ( EdiRtnProdVO  rtnProd : rtnDeleteList ) {
			if(!rrlReqNoOld.equals(rtnProd.getRrlReqNo())){
				rrlReqNo +=rtnProd.getRrlReqNo()+"_";
			}
			rrlReqNoOld = rtnProd.getRrlReqNo();

			if("0".equals(venCd)) venCd       = rtnProd.getVenCd();
		}

		EdiRtnProdVO param = new EdiRtnProdVO();
		param.setRrlReqNos(rrlReqNo.substring(0,rrlReqNo.length()-1).split("_"));
		param.setVenCd(venCd);
		pedmweb0005dao.deleteRtnrodList(param);
		rtnData.put("state","0");
		//-----------------------------------------------------------------------*/

		/*-- 반품상품 점별 마스터 합계   (UPDATE TED_PO_RRL_MST) -------------------*/
		pedmweb0005dao.saveRtnrodSum(param);
		//-----------------------------------------------------------------------*/

		return rtnData;
	}


	/**
	 *  당일 반품등록 내역 조회(합계 + 반품목록)
	 * @param SearchWebOrder
	 * @return List<EdiRtnProdVO>
	 * @throws SQLException
	 */
	public List<EdiRtnProdVO> selectDayRtnProdSumList(SearchWebOrder vo, HttpServletRequest request) throws Exception {
		return pedmweb0005dao.selectDayRtnProdSumList(vo);
	}



	/**
	 * TODAY 협력사코드별  반품자료 MD전송처리
	 * @Method Name : sendReturnProdData
	 * @param EdiRtnProdProcessVO
	 * @return HashMap<String, String>
	 */
	public HashMap<String, String> insertSendReturnProdData(SearchWebOrder vo, HttpServletRequest request)  throws Exception{
		HashMap<String, String> rtnData = new HashMap<String, String>();
		rtnData.put("state","-1");


		/*-세션정보-----------------------------*/
		/*String sessionKey = config .getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);*/
		/*------------------------------*/

		//수정자 아이디
		//vo.setWorkUser(epcLoginVO.getAdminId());
		//vo.setWorkUser("10000001");


		/*전송 반품상품내역조회 (TED_PO_RRL_PROD)==============================================*/
		vo.setRegStsfg("4"); //미전송,오류건 전송 구분(0,9)
		List<EdiRtnProdVO> prodList = pedmweb0005dao.selectDayRtnProdListSend(vo); //
		if(prodList == null || prodList.size() <=0){
			rtnData.put("state","1");
			rtnData.put("message","No Data Found!");
			return rtnData;
		}
		/*==================================================================================*/


		/*전송  MARTNIS.O_SPEC1_VEN_RTN_REG=================================================*/
		EdiRtnProdListVO rtnList = new EdiRtnProdListVO();
		String outRtn	  = "";	// SP return code
		String outprocCmt = ""; // SP return message

		int successCnt = 0;
		int fallCnt    = 0;
		int totalCnt   = prodList.size();
		for ( EdiRtnProdVO  prams : prodList ) {
			if(Integer.parseInt(prams.getRrlQty()) > Integer.parseInt(prams.getRrlStkQty())){
				outRtn = "8";
				outprocCmt	= "Large quantity in stock";

				prams.setO_ret(outRtn);
				prams.setO_proc_cmt(outprocCmt);
			}else{
				prams = pedmweb0005dao.saveCallSendReturnProd(prams);	// SP 호출, 결과 리턴

				/*실행결과  (정상 : '1',Success' )*/
				outRtn 		= prams.getO_ret();
				outprocCmt	= prams.getO_proc_cmt();
			}

			if("1".equals(outRtn)) successCnt++;
			else fallCnt++;


			/*4:반품등록 가능일자 오류 첫번째 로만 체크하여 return-------*/
			/* (sp 결과에상관없이 오류처리 하지 않는다.)
			if("4".equals(outRtn)) {
				rtnData.put("state",	outRtn);
				rtnData.put("message",	outprocCmt);
				return rtnData;
			}
			*/
			/*-----------------------------------------------------*/

			/* 9:O_SPEC1_VEN_RTN_REG SqlException 발생시 ROLBACK --*/
			/* (sp 결과에상관없이 오류처리 하지 않는다.)
			if("9".equals(outRtn)){
				throw new RuntimeException(outprocCmt+"[O_SPEC1_VEN_RTN_REG]");
			}
			*/
			/*-----------------------------------------------------*/

			prams.setWorkUser(prams.getVenCd());	//수정자아이디처리
			rtnList.add(prams);

		}
		/*==================================================================================*/


		/*반품결과 UPDATE ===================================================================*/
		pedmweb0005dao.saveTedPoRrlProdBatch(rtnList);
		/*==================================================================================*/


		/*반품결과 Master UPDATE ===================================================================*/
		pedmweb0005dao.saveTedPoRrlMst(vo);
		/*==================================================================================*/


		rtnData.put("successCnt",	String.valueOf(successCnt) );	// 정상카운트
		rtnData.put("fallCnt",		String.valueOf(fallCnt));		// 오류카운트
		rtnData.put("totalCnt",		String.valueOf(totalCnt));		// 전체카운트

		rtnData.put("state",		"0");			// 처리상태
		rtnData.put("message",		"SUCCESS");		// 처리메시지


		return rtnData;
	}



	//--- 반품 등록전체 현황


	/**
	 *  당일 반품등록 내역 조회(합계 / 반품목록)
	 * @param SearchWebOrder
	 * @return List<EdiRtnProdVO>
	 * @throws SQLException
	 */
	public Map<String,Object> selectDayRtnList(SearchWebOrder vo, HttpServletRequest request)  throws Exception {

		/**
		 *  state
		 *  0  : 정상 등록      ( SUCCESS          )
		 * -1  : 시스템 오류    (exception message)
		 */


		Map<String,Object>  result	    = new HashMap<String,Object>();
		result.put("state","-1");




			int page = Integer.parseInt(StringUtils.nvl(vo.getPage(),"1"));
			int pageRowCount = Integer.parseInt(StringUtils.nvl(vo.getPageRowCount(),DefaultPageRowCount));
			int pagePerCount = Integer.parseInt(StringUtils.nvl(vo.getPagePerCount(),DefaultPagePerCount));


			EdiRtnProdVO		sumData     = null;
			List<EdiRtnProdVO> 	listData	= null;
			int totalCount = 0;


			totalCount = pedmweb0005dao.selectDayRtnListTotCnt(vo);


			Paging paging = PagingFactory.makePagingObject(totalCount, pageRowCount, pagePerCount, page);
			int startRowNum = paging.getStartRowNum();
			int endRowNum = paging.getEndRowNum();
			vo.setStartRowNo(startRowNum);
			vo.setEndRowNo(endRowNum);


			if (totalCount > 0){
				listData	= pedmweb0005dao.selectDayRtnList(vo);		// 목록
			}

			sumData 	= pedmweb0005dao.selectDayRtnSum(vo); 		// 합계

			result.put("listData", 		listData);
			result.put("sumData", 		sumData);
			result.put("totalCount", totalCount);

			result.put("paging", PagingFactory.makePagingObject(totalCount, pageRowCount, pagePerCount, page));
			result.put("state",		"0");		// 처리상태
			result.put("message",	"SUCCESS");	// 처리메시지


		return result;
	}





	/* ============================================================================================ */


	/**
	 * 반품 일괄등록 정보 조회
	 * @param SearchWebOrder
	 * @return void
	 * @throws SQLException
	 */
	public Map<String,Object> selectRtnPackInfo(SearchWebOrder vo) {
		Map<String,Object> result = new HashMap<String,Object>();
		List<EdiRtnPackVO> list = null;
		EdiRtnPackVO rtnCntVo = null;

		String rowCount = "20";

		int page = Integer.parseInt(StringUtils.nvl(vo.getPage(),"1"));
		int pageRowCount = Integer.parseInt(StringUtils.nvl(vo.getPageRowCount(),rowCount));
		int pagePerCount = Integer.parseInt(StringUtils.nvl(vo.getPagePerCount(),DefaultPagePerCount));
		int totalCount = 0;

		totalCount = pedmweb0005dao.selectRtnPackListTotCnt(vo);
		Paging paging = PagingFactory.makePagingObject(totalCount, pageRowCount, pagePerCount, page);
		int startRowNum = paging.getStartRowNum();
		int endRowNum = paging.getEndRowNum();
		vo.setStartRowNo(startRowNum);
		vo.setEndRowNo(endRowNum);
		if (totalCount > 0){
			list = pedmweb0005dao.selectRtnPackList(vo);
		}
		// 전점 합계 조회
		rtnCntVo = pedmweb0005dao.selectRtnPackCntSum(vo);

		result.put("totalCount", totalCount);
		result.put("list", list);
		result.put("paging", PagingFactory.makePagingObject(totalCount, pageRowCount, pagePerCount, page));
		result.put("rtnCnt", rtnCntVo);

		return result;
	}


	/**
	 * 발주 일괄등록 정보 저장
	 * @param TedOrdProcess001VO
	 * @param HttpServletRequest
	 * @return void
	 * @throws SQLException
	 */
	public String insertRtnPackInfo(EdiRtnPackListVO vo, HttpServletRequest request, String ordVenCd) throws SQLException, Exception{
		String packDivnCd = null;
		String fileGrpCd = null;

		EdiRtnPackVO pakVO = new EdiRtnPackVO();

		String sessionKey = config.getString("lottemart.epc.session.key");

		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		// 파일 구분 코드 생성
		packDivnCd = pedmweb0005dao.selectNewRtnPackDivnCd();

		// 파일 그룹 코드 생성
		fileGrpCd = pedmweb0005dao.selectNewRtnFileGrpCd(ordVenCd);

		// 업체발주요청 마스터 저장
		for ( EdiRtnPackVO pack : vo ) {
			pack.setFileGrpCd(fileGrpCd);
			pack.setPackDivnCd(packDivnCd);
			pack.setRegId(epcLoginVO.getAdminId());
			pack.setModId(epcLoginVO.getAdminId());
		}

		// 업체발주요청상품정보 저장
		pedmweb0005dao.insertRtnPackInfo(vo);

		// 업체발주요청상품정보 수정
		pakVO.setPackDivnCd(packDivnCd);
		pedmweb0005dao.updateRtnPackInfo(pakVO);

		// 오류 코드 업데이트 ( TED_ORD_SUPP_INFO와 Excel 정보가 일치 하지 않은 경우)
		pedmweb0005dao.updateRtnPackState(packDivnCd);

		return "suc";
	}

	/**
	 * 반품 엑셀 정보 삭제
	 * @param String
	 * @return void
	 * @throws SQLException
	 */
	public String deleteExcelRtnInfo(EdiRtnPackVO vo) throws Exception{

		// 업체발주요청상품정보 삭제
		pedmweb0005dao.deleteExcelRtnInfo(vo);

		return "suc";

	}

	/**
	 * 반품 Excel 정보 저장
	 * @param EdiRtnPackVO
	 * @param HttpServletRequest
	 * @return void
	 * @throws SQLException
	 */
	public String insertExcelRtnInfo(SearchWebOrder scVo, HttpServletRequest request) throws Exception{

		List<EdiRtnPackVO> packList = new EdiRtnPackListVO();
		List<EdiRtnPackVO> rrlList = new EdiRtnPackListVO();
		EdiRtnProdVO  mst = new EdiRtnProdVO();
		EdiRtnPackVO  vo = new EdiRtnPackVO();

		vo.setVenCd(scVo.getVenCd());

		int excelErrorCnt = 0;
		int excelDuplCnt = 0;
		int rrlDuplCnt = 0;


		// 반품 엑셀 데이터 중 오류건이 있는지 확인. 정상 : suc, 오류( COUNT > 0 ) : fail-001
		excelErrorCnt = pedmweb0005dao.selectRtnExcelErrorCnt(vo);
		if(excelErrorCnt > 0){
			return "fail-001";
		}

		// 반품 엑셀 데이터중 중복 건이 있는지 확인. 정상 : suc, 오류( COUNT > 0 ) : fail-002
		excelDuplCnt = pedmweb0005dao.selectRtnExcelDuplCnt(vo);
		if(excelDuplCnt > 0){
			return "fail-002";
		}

		// 기존에 저장된 데이터와 비교하여 중복 건이 있는지 확인. 정상 : suc, 오류( COUNT > 0 ) : fail-003
		rrlDuplCnt = pedmweb0005dao.selectRtnDuplCnt(vo);
		if(rrlDuplCnt > 0){
			return "fail-003";
		}

		// 오류건과 중복건이 없을 경우 점포 코드 조회 후( ven_cd, ord_dy ) TED_PO_ORD_MST 점포별로 생성
		packList = pedmweb0005dao.selectRtnPackStrInfo(vo);
		rrlList = pedmweb0005dao.selectRtnPackInfoList(vo);

		// 처리할 데이터가 없을 경우 리턴
		if(packList == null || packList.size() == 0 || rrlList == null || rrlList.size() == 0){
			return "fail-004";
		}

		// 업체반품요청상품정보 저장 TED_PO_RRL_PACK [ executeBatch.insert/update ]
		pedmweb0005dao.insertRtnExcelProdInfo(rrlList);
		//-----------------------------------------

		// 일괄작업용 KEY 조건 설정 ------------------
		mst.setVenCd(packList.get(0).getVenCd());
		mst.setRrlDy(packList.get(0).getRrlDy());

		// 업체반품정보마스터 생성 -------------------
		pedmweb0005dao.insertRtnExcelMstInfo(mst);
		//-----------------------------------------

		// 업체반품 요청 마스터 의 발주 총 수량, 발주총금액, 발주전체 총 수량 점포별 일괄 업데이트
		pedmweb0005dao.updateExcelRtnMstSum(mst);
		//-----------------------------------------

		// 저장이 성공하면 TED_PO_ORD_PACK 정보 삭제.
		pedmweb0005dao.deleteRtnPackInfo(vo);

		return "suc";

	}
}
