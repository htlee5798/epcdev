package com.lottemart.epc.edi.weborder.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lcn.module.common.util.HashBox;
import lcn.module.common.util.StringUtil;
import lcn.module.framework.property.ConfigurationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lcnjf.util.StringUtils;
import com.lottemart.common.login.model.LoginSession;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.model.TedOrdList;
import com.lottemart.epc.edi.weborder.model.TedOrdList010VO;
import com.lottemart.epc.edi.weborder.model.TedOrdProcess010VO;
import com.lottemart.epc.edi.weborder.model.TedOrdSumList;
import com.lottemart.epc.edi.weborder.model.TedPoOrdMstVO;
import com.lottemart.epc.edi.weborder.service.PEDMWEB0010Service;
import com.lottemart.epc.edi.weborder.dao.PEDMWEB0010Dao;
import com.lottemart.epc.util.Paging;
import com.lottemart.epc.util.PagingFactory;



@Service("pedmweb0010Service")
public class PEDMWEB0010ServiceImpl implements PEDMWEB0010Service{
	


	
	public String DefaultPagePerCount = "10";
	public String DefaultPageRowCount = "5";
	
	@Autowired 
	private PEDMWEB0010Dao pEDMWEB0010Dao;

	@Autowired
	private ConfigurationService config;

	
	/**
     * 발주승인 대상 정보 목록 조회 (TED_PO_ORD_MST sum data)
     * @Method selectVenOrdInfo
     * @param EdiRtnProdVO
     * @return HashMap<String, Object> 
     * @throws Exception
    */
	public Map<String,Object> selectVenOrdInfo(SearchWebOrder swo, HttpServletRequest request) throws Exception{
		// TODO Auto-generated method stub
		
		
		
		Map<String,Object> result = new HashMap<String,Object>();
		List<TedOrdList> VenList=null;
		TedOrdSumList tedOrdSumList= null;

		
		int page = Integer.parseInt(StringUtils.nvl(swo.getPage(),"1"));
		int pageRowCount = Integer.parseInt(StringUtils.nvl(swo.getPageRowCount(),DefaultPageRowCount));
		int pagePerCount = Integer.parseInt(StringUtils.nvl(swo.getPagePerCount(),DefaultPagePerCount));
		int totalCount = 0;


		/* Pageing Data 설정 ----------------------------- --*/
		totalCount = pEDMWEB0010Dao.selectVendOrdListTotCnt(swo);
		Paging paging 	= PagingFactory.makePagingObject(totalCount, pageRowCount, pagePerCount, page);
		int startRowNum = paging.getStartRowNum();
		int endRowNum 	= paging.getEndRowNum();
		swo.setStartRowNo(startRowNum);		// pageCount
		/*------------------------------------------------ --*/
		
		swo.setEndRowNo(endRowNum);			// 로그인사번
		
		// 발주확정 대상 마스터(SUM) 조회 [TED_PO_ORD_MST]--- --*/
		if (totalCount > 0){
			VenList 	 = pEDMWEB0010Dao.selectVenOrdInfo(swo);
			tedOrdSumList = pEDMWEB0010Dao.selectVenOrdSumInfo(swo);
		}
		
		/*------------------------------------------------ --*/

		result.put("VenList", 	 VenList);		//목록
		result.put("VenListSum", tedOrdSumList);//합계
		
		
		result.put("totalCount", totalCount);
		result.put("paging", PagingFactory.makePagingObject(totalCount, pageRowCount, pagePerCount, page));
		
		return result;
	}
	
	
	/**
	 * Desc : 점포별 발주 상세 펼침 조회
	 * @Method Name : selectStrCdList
	 * @param SearchWebOrder
	 * @return Map<String,Object>
	 */
	public Map<String,Object> selectStrCdList(SearchWebOrder swo) throws Exception{
		
		Map<String,Object> result = new HashMap<String,Object>();
		List<TedOrdList> strCdList=null;
		
		strCdList = pEDMWEB0010Dao.selectStrCdList(swo);	
		result.put("strCdList", strCdList);

		return result;
	}
		
	
	
	/**
	 * Desc : 점포별 발주상품 상세 조회
	 * @Method Name : selectVenOrdProdInfo
	 * @param SearchWebOrder
	 * @return Map<String,Object>
	 */
	public Map<String,Object> selectVenOrdProdInfo(SearchWebOrder swo) throws Exception{
		
		Map<String,Object> result = new HashMap<String,Object>();
		List<TedOrdList> VenProdList=null;
		
		VenProdList=pEDMWEB0010Dao.selectStrCdProdList(swo);
		
		
		pEDMWEB0010Dao.selectStrCdProdSumList(swo);
		
		
		result.put("VenProdList", 	  VenProdList);
		result.put("VenProdListSum",  pEDMWEB0010Dao.selectStrCdProdSumList(swo));
	
		
		return result;
		
	}
	

	/**
	 * 발주 목록 삭제처리( update  )
	 * @Method Name : updateStrCd
	 * @param TedOrdProcess010VO, HttpServletRequest
	 * @return Map<String,Object>
	 */
	public int updateStrCd(TedPoOrdMstVO vo,HttpServletRequest request) throws Exception{
		
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
		String loginId = epcLoginVO.getAdminId();
		


		vo.setModId(loginId);
		int cnt = pEDMWEB0010Dao.updateStrCdProdData(vo);		// PROD - 삭제 	  update
		if(cnt > 0) pEDMWEB0010Dao.updateMstSumData(vo);		// MST  - 합계정보 update
		 
		return cnt;
		
	}
	
	/**
	 * 발주 목록 삭제처리( update  )
	 * @Method Name : updateStrCd
	 * @param TedOrdProcess010VO, HttpServletRequest
	 * @return Map<String,Object>
	 */
	public void updateStrCdProd(TedOrdProcess010VO prc,HttpServletRequest request) throws Exception{
		

		
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
		String loginId = epcLoginVO.getAdminId();
		

		TedOrdList010VO prodList = prc.getTedOrdList010VO();
		
		// PROD - 삭제 	  update
		pEDMWEB0010Dao.updateStrCdProdDataBatch(prodList,loginId);	
		
		
		// MST  - 합계정보 update
		TedPoOrdMstVO vo = new TedPoOrdMstVO();
		String ordReqNo = "";
		for ( TedOrdList  rtnProd  : prodList ) {
		
			if(StringUtil.isNotEmpty(ordReqNo) ) ordReqNo += ",";
			
			ordReqNo +=rtnProd.getOrdReqNo();
		}
		vo.setOrdReqNos(ordReqNo.split(","));
		pEDMWEB0010Dao.updateMstSumData(vo);		
		
	}
	
	
	
	/**
	 *  당일 점포,상품별 발주정보 변경
	 * @param TedOrdProcess010VO
	 * @param String
	 * @return void
	 * @throws SQLException
	 */
	public void updateStCdProd(TedOrdProcess010VO vo,HttpServletRequest request) throws Exception{
	 
		
		
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
		String loginId = epcLoginVO.getAdminId();
		
	
		int cnt = 0;
		TedOrdList ordVo = null;
		TedOrdList010VO ordList = vo.getTedOrdList010VO();
	
		
		if(ordList != null && ordList.size() > 0) {
			cnt = pEDMWEB0010Dao.updateStrCdProd(ordList, loginId);
		
			if(cnt == 0) {
				ordVo = ordList.get(0);
				pEDMWEB0010Dao.updateMstProdSum(ordVo);
			}
		}

	}
	
	
	
	
	/**
	 *  발주정보 확정 처리
	 * @param TedOrdProcess010VO
	 * @param HttpServletRequest
	 * @return HashMap<String, String> 
	 * @throws SQLException
	 */
	public HashMap<String, String> insertSendProdData(TedOrdProcess010VO vo, HttpServletRequest request)  throws Exception{
		
		HashMap<String, String> rtnData = new HashMap<String, String>();
		rtnData.put("state","-1");
		
		SearchWebOrder sendVenInfo = new SearchWebOrder();
		TedOrdList010VO venCdList = vo.getTedOrdList010VO();
		
		/*== edi소습버젼 업체코드,사번 ==================*/
		
		TedOrdList tedList = venCdList.get(0);
		String venCdYn= StringUtils.nvl(tedList.getVenCd(),"");
		String loginId= StringUtils.nvl(tedList.getProcEmpNo() ,"");
		/*==================================================*/
		
		String venCd="";
		String empNo = loginId;		
		sendVenInfo.setEmpNo(empNo);
		
		// 파라미터로 넘어온 업체코드가 있으면 배열로 담는다.
		/*====bos소스버젼 ================================*/
		/*if(venCdList.size() != 0 ){
			for(TedOrdList  venCds : venCdList){
				venCd += venCds.getVenCd()+"_";
			}

			venCd.split("_");
			// 업체번호(venCd)를 배열로 담는다
			sendVenInfo.setVenCds(venCd.split("_"));
		}
		*/
		/*==============================================*/
		
		
		
		/*=======Edi소스버젼 ======================================*/
			 if(!"".equals(StringUtils.nvl(tedList.getVenCd(),""))){
					for(TedOrdList  venCds : venCdList){
						venCd += venCds.getVenCd()+"_";
					}

					venCd.split("_");
					// 업체번호(venCd)를 배열로 담는다
					sendVenInfo.setVenCds(venCd.split("_"));
				}
		/*==========================================================*/		
		

		
		
		/*전송 발주상품내역조회 (TED_PO_ORD_PROD)==============================================*/
		sendVenInfo.setRegStsfg("4"); //미전송,오류건 전송 구부(0,9) 
		List<TedOrdList> prodList = pEDMWEB0010Dao.selectStrCdProdConfList(sendVenInfo); 
		if(prodList == null || prodList.size() <=0){
			rtnData.put("state","1");
			rtnData.put("message","No Data Found!");
			return rtnData;
		}
		/*==================================================================================*/
		

		
		
		/*전송  MARTNIS.O_SPEC1_VEN_ORD_REG=================================================*/
		
		TedOrdList010VO rtnList = new TedOrdList010VO();
		String outRtn	  = "";	// SP return code
		String outprocCmt = ""; // SP return message
		
		
		
		//boolean fRow = true;
		int successCnt = 0;
		int fallCnt    = 0;
		int totalCnt   = prodList.size();
		
		for ( TedOrdList  prams : prodList ) {
			
			prams = pEDMWEB0010Dao.saveCallSendProd(prams);// SP 호출, 결과 리턴
			
			/*실행결과  (정상 : '1',Success' )*/
			outRtn 		= prams.getO_ret();	 
			outprocCmt	= prams.getO_proc_cmt();
			
			
			if("1".equals(outRtn)) successCnt++;
			else fallCnt++;
			
			
			/*4:발주등록 가능일자 오류 첫번째 로만 체크하여 return-------*/
			/*if(fRow && "4".equals(outRtn)) {
				rtnData.put("state",	outRtn);
				rtnData.put("message",	outprocCmt);
				return rtnData;
			}*/
			/*-----------------------------------------------------*/
			
			/* 9:O_SPEC1_VEN_ORD_REG SqlException 발생시 ROLBACK --*/ 
			/*if("9".equals(outRtn)){
				throw new RuntimeException(outprocCmt+"[O_SPEC1_VEN_ORD_REG]");
			}*/
			/*-----------------------------------------------------*/
			
			
			//prams.setModId(prams.getVenCd());	//수정자아이디처리
			prams.setModId(empNo);	//수정자아이디처리
			rtnList.add(prams);

			//fRow = false;
		}
		/*==================================================================================*/
		
		
		/*발주결과 prodDetail UPDATE ===================================================================*/
		pEDMWEB0010Dao.saveTedPoOrdProdBatch(rtnList);
		/*==================================================================================*/
		
		
		/*발주결과 Master UPDATE ===================================================================*/
		pEDMWEB0010Dao.saveTedPoOrdMst(sendVenInfo);
		/*==================================================================================*/
		
		

		rtnData.put("successCnt",	String.valueOf(successCnt) );	// 정상카운트
		rtnData.put("fallCnt",		String.valueOf(fallCnt));		// 오류카운트
		rtnData.put("totalCnt",		String.valueOf(totalCnt));		// 전체카운트
		
		
		rtnData.put("state",	"0");			// 처리상태
		rtnData.put("message",	"SUCCESS");		// 처리메시지

		
		
		
		return rtnData;
	}
	
	
}
