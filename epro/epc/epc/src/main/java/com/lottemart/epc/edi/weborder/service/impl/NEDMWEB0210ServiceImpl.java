package com.lottemart.epc.edi.weborder.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lcn.module.common.util.StringUtil;
import lcn.module.framework.property.ConfigurationService;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.service.RFCCommonService;
import com.lottemart.epc.edi.weborder.dao.NEDMWEB0210Dao;
import com.lottemart.epc.edi.weborder.model.NEDMWEB0210VO;
import com.lottemart.epc.edi.weborder.service.NEDMWEB0210Service;
import com.lottemart.epc.util.Paging;
import com.lottemart.epc.util.PagingFactory;

/**
 * @Class Name : NEDMWEB0210ServiceImpl
 * @Description : 발주승인관리 ServiceImpl Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      		수정자          		수정내용
 *  -------    --------    ---------------------------
 * 2015.12.08	O YEUN KWON	  최초생성
 * 
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */

@Service("nedmweb0210Service")
public class NEDMWEB0210ServiceImpl implements NEDMWEB0210Service{
	


	
	public String DefaultPagePerCount = "10";
	public String DefaultPageRowCount = "5";
	
	@Autowired 
	private NEDMWEB0210Dao nEDMWEB0210Dao;

	@Autowired
	private ConfigurationService config;
	
	@Resource(name="rfcCommonService")
	private RFCCommonService rfcCommonService;

	
	/**
     * 발주승인 대상 정보 목록 조회 (TED_PO_ORD_MST sum data)
     * @Method selectVenOrdInfo
     * @param EdiRtnProdVO
     * @return HashMap<String, Object> 
     * @throws Exception
    */
	public Map<String,Object> selectVenOrdInfo(NEDMWEB0210VO vo, HttpServletRequest request) throws Exception{
		// TODO Auto-generated method stub
		
		
		
		Map<String,Object> result = new HashMap<String,Object>();
		List<NEDMWEB0210VO> VenList=null;
		NEDMWEB0210VO tedOrdSumList= null;

		
		int page = Integer.parseInt(StringUtils.nvl(vo.getPage(),"1"));
		int pageRowCount = Integer.parseInt(StringUtils.nvl(vo.getPageRowCount(),DefaultPageRowCount));
		int pagePerCount = Integer.parseInt(StringUtils.nvl(vo.getPagePerCount(),DefaultPagePerCount));
		int totalCount = 0;


		/* Pageing Data 설정 ----------------------------- --*/
		totalCount = nEDMWEB0210Dao.selectVendOrdListTotCnt(vo);
		Paging paging 	= PagingFactory.makePagingObject(totalCount, pageRowCount, pagePerCount, page);
		int startRowNum = paging.getStartRowNum();
		int endRowNum 	= paging.getEndRowNum();
		vo.setStartRowNo(startRowNum);		// pageCount
		/*------------------------------------------------ --*/
		
		vo.setEndRowNo(endRowNum);			// 로그인사번
		
		// 발주확정 대상 마스터(SUM) 조회 [TED_PO_ORD_MST]--- --*/
		if (totalCount > 0){
			VenList 	 = nEDMWEB0210Dao.selectVenOrdInfo(vo);
			tedOrdSumList = nEDMWEB0210Dao.selectVenOrdSumInfo(vo);
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
	public Map<String,Object> selectStrCdList(NEDMWEB0210VO vo) throws Exception{
		
		Map<String,Object> result = new HashMap<String,Object>();
		List<NEDMWEB0210VO> strCdList=null;
		
		strCdList = nEDMWEB0210Dao.selectStrCdList(vo);	
		result.put("strCdList", strCdList);

		return result;
	}
		
	
	
	/**
	 * Desc : 점포별 발주상품 상세 조회
	 * @Method Name : selectVenOrdProdInfo
	 * @param SearchWebOrder
	 * @return Map<String,Object>
	 */
	public Map<String,Object> selectVenOrdProdInfo(NEDMWEB0210VO vo) throws Exception{
		
		Map<String,Object> result = new HashMap<String,Object>();
		List<NEDMWEB0210VO> VenProdList=null;
		
		VenProdList=nEDMWEB0210Dao.selectStrCdProdList(vo);
		
		
		nEDMWEB0210Dao.selectStrCdProdSumList(vo);
		
		
		result.put("VenProdList", 	  VenProdList);
		result.put("VenProdListSum",  nEDMWEB0210Dao.selectStrCdProdSumList(vo));
	
		
		return result;
		
	}
	

	/**
	 * 발주 목록 삭제처리( update  )
	 * @Method Name : updateStrCd
	 * @param TedOrdProcess010VO, HttpServletRequest
	 * @return Map<String,Object>
	 */
	public int updateStrCd(NEDMWEB0210VO vo,HttpServletRequest request) throws Exception{
		
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
		String loginId = epcLoginVO.getAdminId();
		


		vo.setModId(loginId);
		int cnt = nEDMWEB0210Dao.updateStrCdProdData(vo);		// PROD - 삭제 	  update
		if(cnt > 0) nEDMWEB0210Dao.updateMstSumData(vo);		// MST  - 합계정보 update
		 
		return cnt;
		
	}
	
	/**
	 * 발주 목록 삭제처리( update  )
	 * @Method Name : updateStrCd
	 * @param TedOrdProcess010VO, HttpServletRequest
	 * @return Map<String,Object>
	 */
	public void updateStrCdProd(NEDMWEB0210VO prc,HttpServletRequest request) throws Exception{
		

		
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
		String loginId = epcLoginVO.getAdminId();
		

		ArrayList<NEDMWEB0210VO> prodList = prc.getTedOrdList();
		
		// PROD - 삭제 	  update
		nEDMWEB0210Dao.updateStrCdProdDataBatch(prodList,loginId);	
		
		
		// MST  - 합계정보 update
		NEDMWEB0210VO vo = new NEDMWEB0210VO();
		String ordReqNo = "";
		for ( NEDMWEB0210VO  rtnProd  : prodList ) {
		
			if(StringUtil.isNotEmpty(ordReqNo) ) ordReqNo += ",";
			
			ordReqNo +=rtnProd.getOrdReqNo();
		}
		vo.setOrdReqNos(ordReqNo.split(","));
		nEDMWEB0210Dao.updateMstSumData(vo);		
		
	}
	
	
	
	/**
	 *  당일 점포,상품별 발주정보 변경
	 * @param TedOrdProcess010VO
	 * @param String
	 * @return void
	 * @throws SQLException
	 */
	public void updateStCdProd(NEDMWEB0210VO vo,HttpServletRequest request) throws Exception{
	 
		
		
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
		String loginId = epcLoginVO.getAdminId();
		
	
		int cnt = 0;
		NEDMWEB0210VO ordVo = null;
		ArrayList<NEDMWEB0210VO> ordList = vo.getTedOrdList();
	
		
		if(ordList != null && ordList.size() > 0) {
			cnt = nEDMWEB0210Dao.updateStrCdProd(ordList, loginId);
		
			if(cnt == 0) {
				ordVo = ordList.get(0);
				nEDMWEB0210Dao.updateMstProdSum(ordVo);
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
	public HashMap<String, String> insertSendProdData(NEDMWEB0210VO vo, HttpServletRequest request)  throws Exception{
		
		HashMap<String, String> rtnData = new HashMap<String, String>();
		rtnData.put("state","-1");
		
		NEDMWEB0210VO sendVenInfo = new NEDMWEB0210VO();
		ArrayList<NEDMWEB0210VO> venCdList = vo.getTedOrdList(); 
		
		/*== edi소습버젼 업체코드,사번 ==================*/
		
		NEDMWEB0210VO tedList = venCdList.get(0);
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
			for(NEDMWEB0210VO  venCds : venCdList){
				venCd += venCds.getVenCd()+"_";
			}

			venCd.split("_");
			// 업체번호(venCd)를 배열로 담는다
			sendVenInfo.setVenCds(venCd.split("_"));
		}
		/*==========================================================*/		
		

		
		
		/*전송 발주상품내역조회 (TED_PO_ORD_PROD)==============================================*/
		sendVenInfo.setRegStsfg("4"); //미전송,오류건 전송 구부(0,9) 
		List<NEDMWEB0210VO> prodList = nEDMWEB0210Dao.selectStrCdProdConfList(sendVenInfo); 
		if(prodList == null || prodList.size() <=0){
			rtnData.put("state","1");
			rtnData.put("message","No Data Found!");
			return rtnData;
		}
		/*==================================================================================*/
		

		
		
		/*전송  MARTNIS.O_SPEC1_VEN_ORD_REG=================================================*/
		
		ArrayList<NEDMWEB0210VO> rtnList = new ArrayList<NEDMWEB0210VO>();
		String outRtn	  = "";	// SP return code
		String outprocCmt = ""; // SP return message
		
		
		
		//boolean fRow = true;
		int successCnt = 0;
		int fallCnt    = 0;
		int totalCnt   = prodList.size();
		
		for ( NEDMWEB0210VO  prams : prodList ) {
			
			List<Map> param = new ArrayList<Map>();
			HashMap  pMap = new HashMap();
			
			pMap.put("PROD_CD", prams.getProdCd());
			pMap.put("STR_CD",  prams.getStrCd());
			pMap.put("ORD_DY",  prams.getOrdDy());
			pMap.put("VEN_CD",  prams.getVenCd());
			pMap.put("ORD_QTY", prams.getOrdQty());

			
			param.add(pMap);
			
			HashMap	 		reqCommonMap	=	new HashMap();												// RFC 응답
			
			reqCommonMap.put("ZPOSOURCE", "");
			reqCommonMap.put("ZPOTARGET", "");
			reqCommonMap.put("ZPONUMS",   "");
			reqCommonMap.put("ZPOROWS",   "");
			reqCommonMap.put("ZPODATE",   "");
			reqCommonMap.put("ZPOTIME",   "");
			
			JSONObject obj	=	new JSONObject();
					
			obj.put("TAB", param);					// HashMap에 담긴 데이터 JSONObject로 ...
			obj.put("REQCOMMON", reqCommonMap);		// RFC 응답 HashMap JsonObject로....
			
			//System.out.println("obj.toString()===>" + obj.toString());
			
			Map<String, Object> result = rfcCommonService.rfcCall("PUR0200", obj.toString(), empNo);
					
			JSONObject mapObj 			= new JSONObject(result.toString());								//MAP에 담긴 응답메세지를 JSONObject로.................							
			JSONObject resultObj    	= mapObj.getJSONObject("result");									//JSONObject에 담긴 응답메세지의 키는 result로 넘어 오기 떄문에 result로 꺼낸다.
			JSONObject respCommonObj    = resultObj.getJSONObject ("RESPCOMMON");							//<-------RESPCOMMON이 RFC 오리지날 응답메세지다.
			String rtnResult			= StringUtils.trimToEmpty(respCommonObj.getString("ZPOSTAT"));		//RFC 응답 성공 / 실패 여부를 담는 Key다
					
			
			if (rtnResult.equals("S")){
				prams.setO_ret("1");
				successCnt++;
			}else{
				prams.setO_ret("9");
				fallCnt++;
			}
						
			
			//prams.setModId(prams.getVenCd());	//수정자아이디처리
			prams.setModId(empNo);	//수정자아이디처리
			rtnList.add(prams);

			//fRow = false;
		}
		/*==================================================================================*/
		
		
		/*발주결과 prodDetail UPDATE ===================================================================*/
		nEDMWEB0210Dao.saveTedPoOrdProdBatch(rtnList);
		/*==================================================================================*/
		
		
		/*발주결과 Master UPDATE ===================================================================*/
		nEDMWEB0210Dao.saveTedPoOrdMst(sendVenInfo);
		/*==================================================================================*/
		
		

		rtnData.put("successCnt",	String.valueOf(successCnt) );	// 정상카운트
		rtnData.put("fallCnt",		String.valueOf(fallCnt));		// 오류카운트
		rtnData.put("totalCnt",		String.valueOf(totalCnt));		// 전체카운트
		
		
		rtnData.put("state",	"0");			// 처리상태
		rtnData.put("message",	"SUCCESS");		// 처리메시지
		
		
		return rtnData;
	}
	
	
}
