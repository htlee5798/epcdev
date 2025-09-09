package com.lottemart.epc.delivery.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lcnjf.util.StringUtil;
import com.lottemart.common.code.dao.CommonCodeDao;
import com.lottemart.common.code.service.CommonCodeService;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.delivery.service.PDCMHPC0001Service;

import lcn.module.common.views.AjaxJsonModelHelper;
import lcn.module.framework.property.ConfigurationService;
import net.sf.json.JSONObject;


/**
 * @author binary27
 * @Class : com.lottemart.epc.delivery.controller
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 12. 8. binary27
 * @version :  
 */
@Controller("pdcmhpc0001Controller")
public class PDCMHPC0001Controller 
{
	private static final Logger logger = LoggerFactory.getLogger(PDCMHPC0001Controller.class);
	
	@Autowired
	private PDCMHPC0001Service pdcmhpc0001Service;
	@Autowired
	private ConfigurationService config;
	@Autowired
	private CommonCodeDao commonCodeDao;
	@Autowired
	private CommonCodeService commonCodeService;
	@Autowired
	MessageSource messageSource;


	/** 
		 * @see PDCMHPC0001
		 * @Method Name  : PDCMHPC0001Controller.java
		 * @since      : 2011. 12. 8.
		 * @author     : binary27
		 * @version    :
		 * @Locaton    : com.lottemart.epc.delivery.controller
		 * @Description : 해피콜 조회 화면 초기화
	     * @param 
		 * @return  String
	     * @throws 
	*/
	@RequestMapping(value="delivery/PDCMHPC0001.do")
	public String PDCMHPC0001(HttpServletRequest request) throws Exception 
	{
		String sessionKey = config.getString("lottemart.epc.session.key");
		
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
		
		/*Enumeration enums = request.getSession().getAttributeNames();
		while (enums.hasMoreElements()) {
			System.out.println(enums.nextElement());
		}*/
		
		// Session check 실패 --> 메인페이지로 이동
		if( epcLoginVO == null ){
			logger.debug("로그인 권한이 종료되었습니다.");
			request.setAttribute("loginSession", false);	
			return "delivery/PDCMHPC0001";
		}else{
			request.setAttribute("loginSession", true);
		}
	
		// 협력업체콤보
		request.setAttribute("epcLoginVO", epcLoginVO);
		
		//공통코드 : 접수구분(HO02)
		request.setAttribute("acceptDivnCdList", commonCodeService.getCodeList("HO02"));
		
		//해피콜상태코드
		String[] statusCds = {"20","30","40","50","60"};
		request.setAttribute("happyCallList", commonCodeService.getCodeList("HO19",statusCds));
		
		//공통코드 : 배송진행단계(HO04)
		request.setAttribute("cdHO04", commonCodeDao.getCodeList("HO04"));			
		
		//공통코드 : 배송진행단계상태(HO05)
		request.setAttribute("cdHO05", commonCodeDao.getCodeList("HO05"));		
		
		//점포코드
		request.setAttribute("storeList", pdcmhpc0001Service.selectStoreList());
		
		//현재일자 / 조회일자차수가 적용된 일자 / 조회일자차수 정보 조회
		request.setAttribute("confInfo", pdcmhpc0001Service.selectConfInfo());
		
		//로그인ID
		request.setAttribute("loginId", epcLoginVO.getAdminId());
		
		// 활성 ID 리스트
		request.setAttribute("activeId", pdcmhpc0001Service.selectActiveIdList());
		List<DataMap> hpcUtakmnIdUnAss = pdcmhpc0001Service.selectActiveIdList();
		request.setAttribute("activeIdd", hpcUtakmnIdUnAss);
		
		return "delivery/PDCMHPC0001";
	}

	
	/** 
		 * @see selectHappyCallList
		 * @Method Name  : PDCMHPC0001Controller.java
		 * @since      : 2011. 12. 8.
		 * @author     : binary27
		 * @version    :
		 * @Locaton    : com.lottemart.epc.delivery.controller
		 * @Description : 해피콜 정보를 검색한다.
	     * @param 
		 * @return  String
	     * @throws 
	*/

	@RequestMapping("delivery/selectHappyCallList.do")
	public @ResponseBody Map selectHappyCallList(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		GridData gdRes = new GridData();
		
		DataMap dataMap = new DataMap(request);
        Map rtnMap = new HashMap<String, Object>();
        
		try {
			//Session check 실패 --> 메인페이지로 이동
			String sessionKey = config.getString("lottemart.epc.session.key");
			EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
			
			if(epcLoginVO == null){	//권한이 종료된 경우
				logger.debug("로그인 권한이 종료되었습니다.");
//				gdRes.setMessage(messageSource.getMessage("msg.login.necessary", null, Locale.getDefault()));
				rtnMap.put("result", false);
				rtnMap.put("mode", "NOSESSION");
//		        rtnMap.put("errMsg", e.getMessage());
		        return rtnMap;
			}
			
			
			String rowsPerPage = StringUtil.null2str((String)dataMap.get("rowsPerPage"), config.getString("count.row.per.page"));
    		int startRow = ((Integer.parseInt((String)dataMap.get("currentPage"))-1)*Integer.parseInt(rowsPerPage))+1;
    		int endRow = startRow + Integer.parseInt(rowsPerPage) -1;
    		
    		dataMap.put("currentPage", (String)dataMap.get("currentPage"));
//    		dataMap.put("startRow", Integer.toString(startRow));
//    		dataMap.put("endRow", Integer.toString(endRow));
    		dataMap.put("rowsPerPage", rowsPerPage);
			
			String rtnAcceptFromDt 	= "";
			String rtnAcceptToDt   	= "";
			
			//날짜값에서 '-' 제외
			if ( dataMap.get("rtnAcceptFromDt") != null && String.valueOf(dataMap.get("rtnAcceptFromDt")).length() > 0) {
				rtnAcceptFromDt = String.valueOf(dataMap.get("rtnAcceptFromDt")).replaceAll("-", "");
				rtnAcceptFromDt += "000000";	//데이터 통일을 위한 시간,분,초(00,00,00)
			}
			if ( dataMap.get("rtnAcceptToDt") != null && String.valueOf(dataMap.get("rtnAcceptToDt")).length() > 0) {
				rtnAcceptToDt = String.valueOf(dataMap.get("rtnAcceptToDt")).replaceAll("-", "");
				rtnAcceptToDt += "235959";		//데이터 통일을 위한 시간,분,초(23,59,59)
			}
			dataMap.put("rtnAcceptFromDt",		rtnAcceptFromDt);	//회송접수시작일
			dataMap.put("rtnAcceptToDt", 		rtnAcceptToDt);		//회송접수종료일
			
			//해피콜메시지 배열
			String hpclContentNm = String.valueOf(dataMap.get("hpclContentNm"));			
			if(hpclContentNm!=null && hpclContentNm.length()>0){
				dataMap.put("hpclContentNm", hpclContentNm.split("/"));
			}
			
			//해피콜 로그인 아이디			
			String hpcIdsLoginId = String.valueOf(dataMap.get("hpcIdsLoginId")); // 미할당
			String hpcUtakmnId = StringUtil.null2str((String)dataMap.getSafeString("hpcUtakmnId"));
			
		 
			logger.debug("====================>hpcUtakmnId========>  "+hpcIdsLoginId);	
			dataMap.put("hpcUtakmnId", hpcUtakmnId);		
			dataMap.put("hpcIdsLoginId", hpcIdsLoginId.split(",")); // 미할당
			
			String deliOrderDy = "";
			if ( dataMap.get("deliOrderDy") != null && String.valueOf(dataMap.get("deliOrderDy")).length() > 0) {
				deliOrderDy = String.valueOf(dataMap.get("deliOrderDy")).replaceAll("-", "");
			}
			dataMap.put("deliOrderDy", deliOrderDy);
			
			//상품유형
			String tempProdDivnNmArray = StringUtils.defaultString(dataMap.getString("tempProdDivnNmArr"), "");

			if(!tempProdDivnNmArray.equals("")){
				String[] tempProdDivnNmArr = tempProdDivnNmArray.split("/");
				dataMap.put("tempProdDivnNmArr", tempProdDivnNmArr);

				if(tempProdDivnNmArr.length > 0){
					dataMap.put("tempProdDivnNmArrFlag", "Y");
				}
			}
			
			//전화번호
			String cellNo = "";
			if ( dataMap.get("cellNo") != null && String.valueOf(dataMap.get("cellNo")).length() > 0) {
				cellNo = String.valueOf(dataMap.get("cellNo")).replaceAll("-", "");
			}
			dataMap.put("cellNo", cellNo);
	
			// 데이터 조회
			List<DataMap> list = pdcmhpc0001Service.selectHappyCallList(dataMap);

			rtnMap = JsonUtils.convertList2Json((List)list, -1, dataMap.getString("currentPage"));
			
			//로그인ID
			rtnMap.put("loginId", epcLoginVO.getAdminId());
			
    		// 성공
            rtnMap.put("result", true);
            
		}catch (Exception e) {
			// 작업오류
	        logger.error("error message --> " + e.getMessage());
	        rtnMap.put("result", false);
	        rtnMap.put("mode", "SEARCH");
	        rtnMap.put("errMsg", e.getMessage());
		}
		
		return rtnMap;
	}
	
	
	/** 
	 * @see PDCMHPC000101
	 * @Method Name  : PDCMHPC0001Controller.java
	 * @since      : 2019. 4. 11.
	 * @author     : mg098
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.controller
	 * @Description : 해피콜 실적 집계
     * @param 
	 * @return  String
     * @throws 
	*/
	@RequestMapping(value="delivery/PDCMHPC000101.do")
	public String PDCMHPC000101(HttpServletRequest request) throws Exception 
	{
		String sessionKey = config.getString("lottemart.epc.session.key");
		
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
		
		// Session check 실패 --> 메인페이지로 이동
		if( epcLoginVO == null ){
			logger.debug("로그인 권한이 종료되었습니다.");
			request.setAttribute("loginSession", false);	
			return "delivery/PDCMHPC000101";
		}else{
			request.setAttribute("loginSession", true);
		}
		
		//현재일자 / 조회일자차수가 적용된 일자 / 조회일자차수 정보 조회
		request.setAttribute("confInfo", pdcmhpc0001Service.selectConfInfo());
		
		//로그인ID
		request.setAttribute("loginId", epcLoginVO.getAdminId());
		
		return "delivery/PDCMHPC000101";
	}
	
	
	/** 
	 * @see selectHappyCallCount
	 * @Method Name  : PDCMHPC0001Controller.java
	 * @since      : 2019. 4. 11.
	 * @author     : mg098
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.controller
	 * @Description : 해피콜 실적을 검색한다.
     * @param 
	 * @return  String
     * @throws 
	*/
	
	@RequestMapping("delivery/selectHappyCallCount.do")
	public @ResponseBody Map selectHappyCallCount(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		DataMap dataMap = new DataMap(request);
	    Map rtnMap = new HashMap<String, Object>();
	    
		try {
			//Session check 실패 --> 메인페이지로 이동
			String sessionKey = config.getString("lottemart.epc.session.key");
			EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
			
			if(epcLoginVO == null){	//권한이 종료된 경우
				logger.debug("로그인 권한이 종료되었습니다.");
				rtnMap.put("result", false);
				rtnMap.put("mode", "NOSESSION");
		        return rtnMap;
			}
			
			
			String rowsPerPage = StringUtil.null2str((String)dataMap.get("rowsPerPage"), config.getString("count.row.per.page"));
			int startRow = ((Integer.parseInt((String)dataMap.get("currentPage"))-1)*Integer.parseInt(rowsPerPage))+1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) -1;
			
			dataMap.put("currentPage", (String)dataMap.get("currentPage"));
			dataMap.put("rowsPerPage", rowsPerPage);
			
			String rtnAcceptFromDt 	= "";
			String rtnAcceptToDt   	= "";
			
			//날짜값에서 '-' 제외
			if ( dataMap.get("rtnAcceptFromDt") != null && String.valueOf(dataMap.get("rtnAcceptFromDt")).length() > 0) {
				rtnAcceptFromDt = String.valueOf(dataMap.get("rtnAcceptFromDt")).replaceAll("-", "");
				rtnAcceptFromDt += "000000";	//데이터 통일을 위한 시간,분,초(00,00,00)
			}
			if ( dataMap.get("rtnAcceptToDt") != null && String.valueOf(dataMap.get("rtnAcceptToDt")).length() > 0) {
				rtnAcceptToDt = String.valueOf(dataMap.get("rtnAcceptToDt")).replaceAll("-", "");
				rtnAcceptToDt += "235959";		//데이터 통일을 위한 시간,분,초(23,59,59)
			}
			dataMap.put("rtnAcceptFromDt",		rtnAcceptFromDt);	//회송접수시작일
			dataMap.put("rtnAcceptToDt", 		rtnAcceptToDt);		//회송접수종료일

			// 데이터 조회
			List<DataMap> list = pdcmhpc0001Service.selectHappyCallCount(dataMap);
	
			rtnMap = JsonUtils.convertList2Json((List)list, -1, dataMap.getString("currentPage"));
			
			//로그인ID
			rtnMap.put("loginId", epcLoginVO.getAdminId());
			
			// 성공
	        rtnMap.put("result", true);
	        
		}catch (Exception e) {
			// 작업오류
	        logger.error("error message --> " + e.getMessage());
	        rtnMap.put("result", false);
	        rtnMap.put("mode", "SEARCH");
	        rtnMap.put("errMsg", e.getMessage());
		}
		
		return rtnMap;
	}
	
	@RequestMapping("delivery/updateHappyCallUtakmnId.do")
	public ModelAndView updateHpcId(HttpServletRequest request) throws Exception{
		
			JSONObject  message = new JSONObject();
	        try {
			DataMap paramMap = new DataMap();
			
			String utakmnId = request.getParameter("utakmnId"); 
			String invoiceNo[] = request.getParameterValues("INVOICE_NO"); 
			
			paramMap.put("HPCL_UTAKMN_ID",utakmnId);
			paramMap.put("INVOICE_NO",invoiceNo);
			
			pdcmhpc0001Service.updatdUtakmnId(paramMap);
			
			 message.put("Code", "1");
		     message.put("Message", "저장 되었습니다.");
	    } catch (Exception e) {
	        //오류 처리
	        message.put("Code", "-1");
	        message.put("Message", "저장이 실패 하였습니다.");
	        logger.error("StackTrace : ", e);
	     }
	
	     JSONObject  result = new JSONObject();
	     result.put("Result",message);
	     return AjaxJsonModelHelper.create(result );
		
	}
	
	/** 
	 * @see PDCMHPC000102
	 * @Method Name  : PDCMHPC0001Controller.java
	 * @since      : 2021. 12. 23.
	 * @author     : mg098
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.controller
	 * @Description : 출고일 조회
     * @param 
	 * @return  String
     * @throws 
	*/
	@RequestMapping(value="delivery/PDCMHPC000102.do")
	public String PDCMHPC000102(HttpServletRequest request) throws Exception 
	{
		String sessionKey = config.getString("lottemart.epc.session.key");
		
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
		
		// Session check 실패 --> 메인페이지로 이동
		if( epcLoginVO == null ){
			logger.debug("로그인 권한이 종료되었습니다.");
			request.setAttribute("loginSession", false);	
			return "delivery/PDCMHPC000102";
		}else{
			request.setAttribute("loginSession", true);
		}
		
		//현재일자 / 조회일자차수가 적용된 일자 / 조회일자차수 정보 조회
		request.setAttribute("confInfo", pdcmhpc0001Service.selectConfInfo());
		
		//로그인ID
		request.setAttribute("loginId", epcLoginVO.getAdminId());
		
		return "delivery/PDCMHPC000102";
	}
	
	/** 
	 * @see selectHappyCallCountTotal
	 * @Method Name  : PDCMHPC0001Controller.java
	 * @since      : 2021. 12. 23.
	 * @author     : mg098
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.controller
	 * @Description : 해피콜 실적 및 출고일을 검색한다.
     * @param 
	 * @return  String
     * @throws 
	*/
	
	@RequestMapping("delivery/selectHappyCallCountTotal.do")
	public @ResponseBody Map selectHappyCallCountTotal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		DataMap dataMap = new DataMap(request);
	    Map rtnMap = new HashMap<String, Object>();
	    
		try {
			//Session check 실패 --> 메인페이지로 이동
			String sessionKey = config.getString("lottemart.epc.session.key");
			EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
			
			if(epcLoginVO == null){	//권한이 종료된 경우
				logger.debug("로그인 권한이 종료되었습니다.");
				rtnMap.put("result", false);
				rtnMap.put("mode", "NOSESSION");
		        return rtnMap;
			}
			
			
			String rowsPerPage = StringUtil.null2str((String)dataMap.get("rowsPerPage"), config.getString("count.row.per.page"));
			int startRow = ((Integer.parseInt((String)dataMap.get("currentPage"))-1)*Integer.parseInt(rowsPerPage))+1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) -1;
			
			dataMap.put("currentPage", (String)dataMap.get("currentPage"));
			dataMap.put("rowsPerPage", rowsPerPage);
			
			String rtnAcceptFromDt 	= "";
			String rtnAcceptToDt   	= "";
			
			//날짜값에서 '-' 제외
			if ( dataMap.get("rtnAcceptFromDt") != null && String.valueOf(dataMap.get("rtnAcceptFromDt")).length() > 0) {
				rtnAcceptFromDt = String.valueOf(dataMap.get("rtnAcceptFromDt")).replaceAll("-", "");
				rtnAcceptFromDt += "000000";	//데이터 통일을 위한 시간,분,초(00,00,00)
			}
			if ( dataMap.get("rtnAcceptToDt") != null && String.valueOf(dataMap.get("rtnAcceptToDt")).length() > 0) {
				rtnAcceptToDt = String.valueOf(dataMap.get("rtnAcceptToDt")).replaceAll("-", "");
				rtnAcceptToDt += "235959";		//데이터 통일을 위한 시간,분,초(23,59,59)
			}
			dataMap.put("rtnAcceptFromDt",		rtnAcceptFromDt);	//회송접수시작일
			dataMap.put("rtnAcceptToDt", 		rtnAcceptToDt);		//회송접수종료일

			// 데이터 조회
			List<DataMap> list = pdcmhpc0001Service.selectHappyCallCountTotal(dataMap);
	
			rtnMap = JsonUtils.convertList2Json((List)list, -1, dataMap.getString("currentPage"));
			
			//로그인ID
			rtnMap.put("loginId", epcLoginVO.getAdminId());
			
			// 성공
	        rtnMap.put("result", true);
	        
		}catch (Exception e) {
			// 작업오류
	        logger.error("error message --> " + e.getMessage());
	        rtnMap.put("result", false);
	        rtnMap.put("mode", "SEARCH");
	        rtnMap.put("errMsg", e.getMessage());
		}
		
		return rtnMap;
	}
	
	/** 
	 * @see selectHappyCallCountDeliDy
	 * @Method Name  : PDCMHPC0001Controller.java
	 * @since      : 2021. 12. 23.
	 * @author     : mg098
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.controller
	 * @Description : 해피콜 실적 및 출고일을 검색한다.
     * @param 
	 * @return  String
     * @throws 
	*/
	
	@RequestMapping("delivery/selectHappyCallCountDeliDy.do")
	public @ResponseBody Map selectHappyCallCountDeliDy(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		DataMap dataMap = new DataMap(request);
	    Map rtnMap = new HashMap<String, Object>();
	    
		try {
			//Session check 실패 --> 메인페이지로 이동
			String sessionKey = config.getString("lottemart.epc.session.key");
			EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
			
			if(epcLoginVO == null){	//권한이 종료된 경우
				logger.debug("로그인 권한이 종료되었습니다.");
				rtnMap.put("result", false);
				rtnMap.put("mode", "NOSESSION");
		        return rtnMap;
			}
			
			
			String rowsPerPage = StringUtil.null2str((String)dataMap.get("rowsPerPage"), config.getString("count.row.per.page"));
			int startRow = ((Integer.parseInt((String)dataMap.get("currentPage"))-1)*Integer.parseInt(rowsPerPage))+1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) -1;
			
			dataMap.put("currentPage", (String)dataMap.get("currentPage"));
			dataMap.put("rowsPerPage", rowsPerPage);
			
			String rtnAcceptFromDt 	= "";
			String rtnAcceptToDt   	= "";
			
			//날짜값에서 '-' 제외
			if ( dataMap.get("rtnAcceptFromDt") != null && String.valueOf(dataMap.get("rtnAcceptFromDt")).length() > 0) {
				rtnAcceptFromDt = String.valueOf(dataMap.get("rtnAcceptFromDt")).replaceAll("-", "");
				rtnAcceptFromDt += "000000";	//데이터 통일을 위한 시간,분,초(00,00,00)
			}
			if ( dataMap.get("rtnAcceptToDt") != null && String.valueOf(dataMap.get("rtnAcceptToDt")).length() > 0) {
				rtnAcceptToDt = String.valueOf(dataMap.get("rtnAcceptToDt")).replaceAll("-", "");
				rtnAcceptToDt += "235959";		//데이터 통일을 위한 시간,분,초(23,59,59)
			}
			dataMap.put("rtnAcceptFromDt",		rtnAcceptFromDt);	//회송접수시작일
			dataMap.put("rtnAcceptToDt", 		rtnAcceptToDt);		//회송접수종료일

			// 데이터 조회
			List<DataMap> list = pdcmhpc0001Service.selectHappyCallCountDeliDy(dataMap);
	
			rtnMap = JsonUtils.convertList2Json((List)list, -1, dataMap.getString("currentPage"));
			
			//로그인ID
			rtnMap.put("loginId", epcLoginVO.getAdminId());
			
			// 성공
	        rtnMap.put("result", true);
	        
		}catch (Exception e) {
			// 작업오류
	        logger.error("error message --> " + e.getMessage());
	        rtnMap.put("result", false);
	        rtnMap.put("mode", "SEARCH");
	        rtnMap.put("errMsg", e.getMessage());
		}
		
		return rtnMap;
	}
	
	
}