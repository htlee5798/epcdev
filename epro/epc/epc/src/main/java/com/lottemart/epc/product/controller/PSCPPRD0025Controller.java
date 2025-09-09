package com.lottemart.epc.product.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.framework.property.ConfigurationService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.product.model.PSCPPRD0005VO;
import com.lottemart.epc.product.model.PSCPPRD0017HistVO;
import com.lottemart.epc.product.model.PSCPPRD0025HistVO;
import com.lottemart.epc.product.model.PSCPPRD0025VO;
import com.lottemart.epc.product.service.PSCPPRD0017Service;
import com.lottemart.epc.product.service.PSCPPRD0025Service;

/**
 * @Class Name : PSCPPRD0025Controller.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2016. 11. 21. 오전 10:47:45 최성웅
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Controller("PSCPPRD0025Controller")
public class PSCPPRD0025Controller {

	private static final Logger logger = LoggerFactory.getLogger(PSCPPRD0025Controller.class);
	@Autowired
	private PSCPPRD0025Service pscpprd0025Service;
	@Autowired
	private ConfigurationService config;
	@Autowired
	private MessageSource messageSource;	
	
	//20180911 상품키워드 입력 기능 추가
	@Autowired
	private PSCPPRD0017Service pscpprd0017Service;
	//20180911 상품키워드 입력 기능 추가
	
	/**
	 * Desc : 키워드관리 폼 페이지
	 * @Method Name : selectKeywordForm
	 * @param request
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@RequestMapping("product/selectKeywordForm.do")
	public String selectKeywordForm(HttpServletRequest request) throws Exception {
		// 인터넷 상품코드 
		request.setAttribute("prodCd",request.getParameter("prodCd"));
		
		//20180911 상품키워드 입력 기능 추가
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("prodCd", request.getParameter("prodCd"));
		paramMap.put("typeCd", "005");
		PSCPPRD0005VO prdMdAprInfo = pscpprd0017Service.selectPrdMdAprvMst(paramMap);	
		
		request.setAttribute("prdMdAprInfo", prdMdAprInfo);
		//20180911 상품키워드 입력 기능 추가
		
		return "product/internet/PSCPPRD002501";
	}
	
	/**
	 * Desc : 키워드 관리 조회
	 * @Method Name : selectKeywordSearch
	 * @param request
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@RequestMapping("product/selectKeywordSearch.do")
	public @ResponseBody Map selectKeywordSearch(HttpServletRequest request) throws Exception {
		  Map rtnMap = new HashMap<String, Object>();
		  try {
			  DataMap dataMap = new DataMap(request);
			  //데이터 조회
			  List<DataMap> resultList = pscpprd0025Service.selectKeywordSearch(dataMap);
			  
			// json
				JSONArray jArray = new JSONArray();
				if (resultList != null)
					jArray = (JSONArray) JSONSerializer.toJSON(resultList);

				String jStr = "{Data:" + jArray + "}";
				rtnMap.put("ibsList", jStr);

				// 조회된 데이터가 없는 경우
				if (jArray.isEmpty()) {
					rtnMap.put("result", false);
				}
				 logger.debug("데이터 조회 완료");
				rtnMap.put("result", true);  
			 
		} catch (Exception e) {
			// 실패
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
	        rtnMap.put("errMsg", e.getMessage());
		}
		  
		  return rtnMap;
	}
	
	/**
	 * Desc : 키워드관리 수정
	 * @Method Name : selectKeywordUpdate
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@RequestMapping("product/keywordUpdate.do")
	public @ResponseBody JSONObject keywordUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jObj = new JSONObject();
		int count = 0;
		try {
			//session check
			String sessionKey = config.getString("lottemart.epc.session.key");
			EpcLoginVO epcLoginVO =  (EpcLoginVO) request.getSession().getAttribute(sessionKey);
			
			//patameter
			String prodCd = request.getParameter("PROD_CD");
			String searchKeyword = request.getParameter("SEARCH_KYWRD");
			String keywordString = searchKeyword;
			String arrayKeyword[] = keywordString.split("\\;");
						
			//VO setting
			PSCPPRD0025HistVO pscpprd0025HistVo = new PSCPPRD0025HistVO();
			pscpprd0025HistVo.setProdCd(prodCd);
			pscpprd0025HistVo.setModId(epcLoginVO.getRepVendorId());
			
			//keyword를 하나 입력 했을때 ( 일반 키워드)
			pscpprd0025HistVo.setKeyword(keywordString);
			
			//5개 이상 등록 금지
			DataMap dataMap = new DataMap();
			dataMap.put("prodCd", prodCd);
			List<DataMap> dList = pscpprd0025Service.selectKeywordSearch(dataMap);
			String keywordLength[] =  dList.get(0).getString("SEARCH_KYWRD").split("\\;");
			
			//20180911 상품키워드 입력 기능 추가
			String keywordPipeLength[] =  dList.get(0).getString("SEARCH_KYWRD").split("\\|");
    		//20180911 상품키워드 입력 기능 추가	
			
			/*
			System.out.println(dList.get(0).getString("SEARCH_KYWRD"));
			System.out.println(keywordLength.length);
			System.out.println(arrayKeyword.length);
						
			if(arrayKeyword.length > 5 || keywordLength.length > 4){
				jObj.put("Code", 1);
				jObj.put("Message","5개이상 등록 할수 없습니다.");
				return JsonUtils.getResultJson(jObj);
			}
			*/
			
			//20180911 상품키워드 입력 기능 추가			
			if(arrayKeyword.length > 5 || keywordLength.length > 4 || keywordPipeLength.length > 4){
				jObj.put("Code", 1);
				jObj.put("Message","5개이상 등록 할수 없습니다.");
				return JsonUtils.getResultJson(jObj);
			}
			
			//keyword를 하나 이상 입력 했을때 (구분자 입력 키워드 )
			if(arrayKeyword.length > 0) {
				for(int j =0; j < arrayKeyword.length; j++) {
					pscpprd0025HistVo.setKeyword(arrayKeyword[j]);
					
					if(arrayKeyword[j] != null && !"".equals(arrayKeyword[j].trim())) {
						if(arrayKeyword[j].getBytes().length > 39) {
							jObj.put("Code", 1);
							jObj.put("Message","검색키워드 1개당 한글기준 13글자, 영문/숫자 기준 39글자 까지만 입력 가능합니다.");
							return JsonUtils.getResultJson(jObj);	
						}
					}
				}
			}		
			
			String vendorId = epcLoginVO.getRepVendorId();
			
			String keywordSeq = "";
			
			DataMap paramMap = new DataMap();
			paramMap.put("prodCd", prodCd);
			paramMap.put("vendorId", vendorId);
			paramMap.put("typeCd", "005");
			
    		int iCnt = pscpprd0017Service.selectPrdMdAprvMstCnt(paramMap);
    		
    		if(iCnt > 0) {
    			Map<String, String> param = new HashMap<String, String>();
    			param.put("prodCd", prodCd);
    			param.put("typeCd", "005");
    			
    			PSCPPRD0005VO prdMdAprInfo = pscpprd0017Service.selectPrdMdAprvMst(param);
				
    			keywordSeq = prdMdAprInfo.getSeq();
    		} else {
    			PSCPPRD0005VO pSCPPRD0005VO = new PSCPPRD0005VO();
    			pSCPPRD0005VO.setProdCd(prodCd);
    			pSCPPRD0005VO.setRegId(vendorId);
    			pSCPPRD0005VO.setAprvCd("001");
    			pSCPPRD0005VO.setTypeCd("005");
    			pSCPPRD0005VO.setVendorId(vendorId);
			
    			keywordSeq = pscpprd0017Service.insertPrdMdAprvMst(pSCPPRD0005VO);
    		}
    		
    		if(!keywordSeq.equals("")) {
    			paramMap.put("keywordSeq", keywordSeq);
    			pscpprd0017Service.deletePrdKeywordHistAll(paramMap);
    			pscpprd0017Service.insertPrdKeywordHistAll(paramMap);
    		}

    		pscpprd0025HistVo.setKeywordSeq(keywordSeq);
    		//20180911 상품키워드 입력 기능 추가
			
			//keyword를 하나 이상 입력 했을때 (구분자 입력 키워드 )
			if(arrayKeyword.length > 0) {
				for(int j =0; j < arrayKeyword.length; j++) {
					pscpprd0025HistVo.setKeyword(arrayKeyword[j]);
					
					if(arrayKeyword[j] != null && !"".equals(arrayKeyword[j].trim())) {
						pscpprd0025Service.keywordHistUpdate(pscpprd0025HistVo);
					}
				}
				
				pscpprd0025Service.masterKeywordHistUpdate(pscpprd0025HistVo);
			}
			count++;
			
			
    		//20180911 상품키워드 입력 기능 추가
			/*
			// 값 셋팅
			List<PSCPPRD0017HistVO> beanList = new ArrayList<PSCPPRD0017HistVO>();

			for (int i=0; i<arrayKeyword.length; i++) {			
				PSCPPRD0017HistVO bean = new PSCPPRD0017HistVO();
				bean.setProductKeywordHistSeq(productKeywordHistSeq);
				bean.setProdCd(prodCd);
				bean.setSearchKywrd(arrayKeyword[i]);
				bean.setSeq("00"+(i+1));
				bean.setRegId(vendorId);
				
				beanList.add(bean);
			}
			
			// 처리
			count = pscpprd0017Service.updatePrdKeywordHistList(beanList, "update"); 
    		*/
    		//20180911 상품키워드 입력 기능 추가

		//처리 결과 메세지 생성
		if(count > 0){
			jObj.put("Code", 1);
			jObj.put("Message",messageSource.getMessage("msg.common.success.request", null, Locale.getDefault()));
		} else {
			jObj.put("Code", 0);
			jObj.put("Message", messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault()));
		}
		
	} catch (Exception e) {
		jObj.put("Code", -1);
		jObj.put("Message", e.getMessage());
		// 처리오류
		logger.error("", e);
	}
	return JsonUtils.getResultJson(jObj);

}
		
	/**
	 * Desc : 키워드 관리 삭제
	 * @Method Name : keywordDelete
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@RequestMapping("product/keywordDelete.do")
	public @ResponseBody JSONObject keywordDelete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jObj = new JSONObject();
		int count = 0;
		try {
			
			//patameter
			String prodCd = request.getParameter("PROD_CD");
			
			//VO setting
			PSCPPRD0025VO pscpprd0025Vo = new PSCPPRD0025VO();
			pscpprd0025Vo.setProdCd(prodCd);
			
			//미등록 삭제 금지
			DataMap dataMap = new DataMap();
			dataMap.put("prodCd", prodCd);
			List<DataMap> dList = pscpprd0025Service.selectKeywordSearch(dataMap);
			String keywor =  dList.get(0).getString("SEARCH_KYWRD");
			
			if(keywor.equals("미등록")){
				jObj.put("Code", 1);
				jObj.put("Message","미등록된 키워드는 삭제 할수 없습니다");
				return JsonUtils.getResultJson(jObj);
			}
			
			//키워드 삭제
			pscpprd0025Service.keywordDelete(pscpprd0025Vo);
			
			count++;
			
			//처리 결과 메세지 생성
			if( count > 0){
				jObj.put("Code", 1);
				jObj.put("Message",  messageSource.getMessage("msg.common.success.request", null, Locale.getDefault()));
			}
			else{
				jObj.put("Code", 0);
				jObj.put("Message", messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault()));
			}
		} catch (Exception e) {
			jObj.put("Code", -1);
			jObj.put("Message", e.getMessage());
			// 처리오류
			logger.error("", e);
		}
		return  JsonUtils.getResultJson(jObj);
	}
}
