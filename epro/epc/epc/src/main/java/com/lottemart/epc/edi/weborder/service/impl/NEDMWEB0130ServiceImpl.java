package com.lottemart.epc.edi.weborder.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lcn.module.framework.property.ConfigurationService;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.service.RFCCommonService;
import com.lottemart.epc.edi.weborder.controller.PEDMWEB0001Controller;
import com.lottemart.epc.edi.weborder.dao.NEDMWEB0130Dao;
import com.lottemart.epc.edi.weborder.model.EdiRtnPackListVO;
import com.lottemart.epc.edi.weborder.model.EdiRtnPackVO;
import com.lottemart.epc.edi.weborder.model.EdiRtnProdListVO;
import com.lottemart.epc.edi.weborder.model.EdiRtnProdVO;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.service.NEDMWEB0130Service;
import com.lottemart.epc.util.Paging;
import com.lottemart.epc.util.PagingFactory;
@Service("NEDMWEB0130Service")
public class NEDMWEB0130ServiceImpl implements NEDMWEB0130Service {

	public String DefaultPagePerCount = "10";
	public String DefaultPageRowCount = "15";

	@Resource(name = "configurationService")
	private ConfigurationService config;

	@Resource(name="rfcCommonService")
	private RFCCommonService rfcCommonService;

	@Autowired
	private NEDMWEB0130Dao nedmweb0130dao;

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

		totalCount = nedmweb0130dao.selectRtnPackListTotCnt(vo);
		Paging paging = PagingFactory.makePagingObject(totalCount, pageRowCount, pagePerCount, page);
		int startRowNum = paging.getStartRowNum();
		int endRowNum = paging.getEndRowNum();
		vo.setStartRowNo(startRowNum);
		vo.setEndRowNo(endRowNum);
		if (totalCount > 0){
			list = nedmweb0130dao.selectRtnPackList(vo);

			//전점 합계 조회
			rtnCntVo = nedmweb0130dao.selectRtnPackCntSum(vo);
		}


		result.put("totalCount", totalCount);
		result.put("list", list);
		result.put("paging", PagingFactory.makePagingObject(totalCount, pageRowCount, pagePerCount, page));
		result.put("rtnCnt", rtnCntVo);

		return result;
	}

	/**
	 * 반품 엑셀 정보 삭제
	 * @param String
	 * @return void
	 * @throws SQLException
	 */
	public String deleteExcelRtnInfo(EdiRtnPackVO vo) throws Exception{

		// 업체발주요청상품정보 삭제
		nedmweb0130dao.deleteExcelRtnInfo(vo);

		return "suc";

	}

	/**
	 * TODAY 협력사코드별  반품자료 MD전송처리
	 * @Method Name : sendReturnProdData
	 * @param EdiRtnProdProcessVO
	 * @return HashMap<String, String>
	 */
	public Map<String, Object> insertSendReturnProdData(SearchWebOrder scVo, HttpServletRequest request)  throws Exception{
		Map<String, Object> resultMap	=	new HashMap<String, Object>();
		List<EdiRtnPackVO> packList = new EdiRtnPackListVO();
		List<EdiRtnPackVO> rrlList = new EdiRtnPackListVO();
		EdiRtnProdVO  mst = new EdiRtnProdVO();
		EdiRtnPackVO  vo = new EdiRtnPackVO();
		int excelErrorCnt = 0;
		int excelDuplCnt = 0;
		int rrlDuplCnt = 0;

		vo.setVenCd(scVo.getVenCd());

		// 반품 엑셀 데이터 중 오류건이 있는지 확인. 정상 : suc, 오류( COUNT > 0 ) : fail-001
		excelErrorCnt = nedmweb0130dao.selectRtnExcelErrorCnt(vo);
		if(excelErrorCnt > 0){
			resultMap.put("msg", "fail-001");
			return resultMap;
		}

		// 반품 엑셀 데이터중 중복 건이 있는지 확인. 정상 : suc, 오류( COUNT > 0 ) : fail-002
		excelDuplCnt = nedmweb0130dao.selectRtnExcelDuplCnt(vo);
		if(excelDuplCnt > 0){
			resultMap.put("msg", "fail-002");
			return resultMap;
		}

		// 기존에 저장된 데이터와 비교하여 중복 건이 있는지 확인. 정상 : suc, 오류( COUNT > 0 ) : fail-003
		rrlDuplCnt = nedmweb0130dao.selectRtnDuplCnt(vo);
		if(rrlDuplCnt > 0){
			resultMap.put("msg", "fail-003");
			return resultMap;
		}

		// 오류건과 중복건이 없을 경우 점포 코드 조회 후( ven_cd, ord_dy ) TPC_PO_ORD_MST 점포별로 생성
		packList = nedmweb0130dao.selectRtnPackStrInfo(vo);
		rrlList = nedmweb0130dao.selectRtnPackInfoList(vo);

		// 처리할 데이터가 없을 경우 리턴
		if(packList == null || packList.size() == 0 || rrlList == null || rrlList.size() == 0){
			//return "fail-004";
			resultMap.put("msg", "fail-004");
			return resultMap;
		}

		// 업체반품요청상품정보 저장 TPC_PO_RRL_PACK [ executeBatch.insert/update ]
		nedmweb0130dao.insertRtnExcelProdInfo(rrlList);

		// 일괄작업용 KEY 조건 설정 ------------------
		mst.setVenCd(packList.get(0).getVenCd());
		mst.setRrlDy(packList.get(0).getRrlDy());

		// 업체반품정보마스터 생성 -------------------
		nedmweb0130dao.insertRtnExcelMstInfo(mst);
		//-----------------------------------------

		// 업체반품 요청 마스터 의 발주 총 수량, 발주총금액, 발주전체 총 수량 점포별 일괄 업데이트
		nedmweb0130dao.updateExcelRtnMstSum(mst);
		//-----------------------------------------

		// 저장이 성공하면 TPC_PO_ORD_PACK 정보 삭제.
		nedmweb0130dao.deleteRtnPackInfo(vo);


		/* 1. 전송 반품상품내역조회 (TPC_PO_RRL_PROD)================================== ========*/
		scVo.setRegStsfg("4"); //미전송,오류건 전송 구분(0,9)
		List<EdiRtnProdVO> prodList = nedmweb0130dao.selectDayRtnProdListSend(scVo); //
		if(prodList == null || prodList.size() <=0){
			resultMap.put("msg", "No Data Found!");
			return resultMap;
		}
		/*============================================================================*/


		/* 2.반품일괄 등록 RFC 전송 요청 ========================================================*/
		List<Map> result = nedmweb0130dao.selectRfcReqDataBatch(scVo);
		HashMap	 		reqCommonMap	=	new HashMap();	// RFC 응답

		reqCommonMap.put("ZPOSOURCE", "");
		reqCommonMap.put("ZPOTARGET", "");
		reqCommonMap.put("ZPONUMS", "");
		reqCommonMap.put("ZPOROWS", "");
		reqCommonMap.put("ZPODATE", "");
		reqCommonMap.put("ZPOTIME", "");

		JSONObject obj	=	new JSONObject();

		obj.put("TAB", result);					// HashMap에 담긴 데이터 JSONObject로 ...
		obj.put("REQCOMMON", reqCommonMap);		// RFC 응답 HashMap JsonObject로....

		Map<String, Object> rfcMap;

		rfcMap = rfcCommonService.rfcCall("INV0690", obj.toString(), scVo.getVenCd());

		//----- RFC 응답 메세지의 성공 / 실패 여부에 따라 리턴메세지를 처리해준다.
		JSONObject mapObj 			= new JSONObject(rfcMap.toString());								//MAP에 담긴 응답메세지를 JSONObject로.................
		JSONObject resultObj    	= mapObj.getJSONObject("result");									//JSONObject에 담긴 응답메세지의 키는 result로 넘어 오기 떄문에 result로 꺼낸다.
		JSONObject respCommonObj    = resultObj.getJSONObject ("RESPCOMMON");							//<-------RESPCOMMON이 RFC 오리지날 응답메세지다.
		String rtnResult			= StringUtils.trimToEmpty(respCommonObj.getString("ZPOSTAT"));		//RFC 응답 성공 / 실패 여부를 담는 Key다

		//성공이 아니면 실패로 간주한다.
		if (!rtnResult.equals("S")) {
			throw new IllegalArgumentException();
		}
		/*==========================================================================*/

		/* 3.반품결과 UPDATE ===========================================================*/
		EdiRtnProdListVO rtnList = new EdiRtnProdListVO();

		for ( EdiRtnProdVO  prams : prodList ) {
			rtnList.add(prams);
		}
		nedmweb0130dao.saveTedPoRrlProdBatch(rtnList);
		/*==========================================================================*/

		/* 4.반품결과 Master UPDATE ====================================================*/
		nedmweb0130dao.saveTedPoRrlMst(scVo);
		/*==========================================================================*/

		resultMap.put("msg", "SUCCESS");
		return resultMap;
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
		packDivnCd = nedmweb0130dao.selectNewRtnPackDivnCd();

		// 파일 그룹 코드 생성
		fileGrpCd = nedmweb0130dao.selectNewRtnFileGrpCd(ordVenCd);

		// 업체발주요청 마스터 저장
		for ( EdiRtnPackVO pack : vo ) {
			pack.setFileGrpCd(fileGrpCd);
			pack.setPackDivnCd(packDivnCd);
			pack.setRegId(epcLoginVO.getAdminId());
			pack.setModId(epcLoginVO.getAdminId());
		}

		// 업체발주요청상품정보 저장
		nedmweb0130dao.insertRtnPackInfo(vo);

		// 업체발주요청상품정보 수정
		pakVO.setPackDivnCd(packDivnCd);
		//nedmweb0130dao.updateRtnPackInfo(pakVO);

		// 오류 코드 업데이트 ( TED_ORD_SUPP_INFO와 Excel 정보가 일치 하지 않은 경우)
		nedmweb0130dao.updateRtnPackState(packDivnCd);

		return "suc";
	}
}
