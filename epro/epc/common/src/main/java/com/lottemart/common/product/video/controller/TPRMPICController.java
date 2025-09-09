package com.lottemart.common.product.video.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lcnjf.util.StringUtil;
import com.lottemart.common.exception.AlertException;
import com.lottemart.common.login.model.LoginSession;
import com.lottemart.common.product.video.service.TPRMPICService;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;

import lcn.module.framework.idgen.IdGnrService;
import lcn.module.framework.property.ConfigurationService;
/**
 *  
 * @Class Name : TPRMPICController.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일          수정자	     수정내용
 *  -------         --------    ---------------------------
 * 2016. 6. 15. 오후 3:25:47   hyunjin
 * 
 * @Copyright (C) 2016 ~ 2016 lottemart All right reserved.
 */
@Controller
public class TPRMPICController {
	

	private static final Logger logger = LoggerFactory.getLogger(TPRMPICController.class);
	
	
	@Autowired
	private WECANDEOSupport WECANDEOSupport;
	
	@Autowired
	private ConfigurationService config;
	
	@Autowired
	private TPRMPICService tprmpicService;
	
	@Autowired
	private MessageSource messageSource;	

	@Resource(name = "tprMpicSeqGnrService")
	private IdGnrService idgenService;
	
	
	/**
	 * Desc : 동영상  목록 페이지
	 * @Method Name : selectTprMpicInfo
	 * @param prodCd
	 * @param request
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@RequestMapping("product/selectTprMpicinfo.do")
	public String selectTprMpicInfo(@RequestParam("prodCd") String prodCd,	HttpServletRequest request) throws Exception{
		request.setAttribute("prodCd", prodCd);
		request.setAttribute("tabNo", "19");
		return "common/TPRMPIC";
	}
	
	/**
	 * Desc : 동영상 목록 조회
	 * @Method Name : selectTprMpicList
	 * @param request
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@ResponseBody
	@RequestMapping("product/selectTprMpicList.do")
	public Map<String, Object> selectTprMpicList(HttpServletRequest request) throws Exception{
		logger.debug("================selectTprMpicList Start================");
		
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		 
		
		 try{
			DataMap param = new DataMap(request);
			
			String rowsPerPage = StringUtil.null2str((String)param.get("rowsPerPage"), config.getString("count.row.per.page"));

			param.put("rowsPerPage", rowsPerPage) ;
			
			int startRow = ((Integer.parseInt((String)param.get("currentPage"))-1)*Integer.parseInt(rowsPerPage))+1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) -1;
			
			param.put("currentPage", (String)param.get("currentPage"));
			param.put("startRow", Integer.toString(startRow));
			param.put("endRow", Integer.toString(endRow));
			
			List<Map<Object, Object>> list = tprmpicService.selectTPRMPICList(param);
			BigDecimal totCnt = new BigDecimal("0");
			if( list.size() != 0 ){
				totCnt = (BigDecimal) list.get(0).get("TOTAL_COUNT");
			}
			rtnMap = JsonUtils.convertList2Json((List)list,Integer.parseInt(totCnt.toString()) , param.getString("currentPage"));
			
			// 성공
			logger.debug("데이터 조회 완료");
			rtnMap.put("result", true);

		} catch (Exception e) {
			// 실패
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
	        rtnMap.put("errMsg", e.getMessage());
		}
		 
		logger.debug("================selectTprMpicList End================");
		return rtnMap;	
	}
	
	/**
	 * Desc : 선택정보 수정
	 * @Method Name : selectUpdateTprMpicInfo
	 * @param request
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@ResponseBody
	@RequestMapping("product/selectUpdateTprMpicInfo.do")
	public JSONObject selectUpdateTprMpicInfo(HttpServletRequest request) throws Exception{
		logger.debug("================selectUpdateTprMpicInfo Start================");
		LoginSession loginSession = LoginSession.getLoginSession(request);

		JSONObject jObj = new JSONObject();
		
		try{
			DataMap param = new DataMap(request);
			
			String regId = param.getString("regId");
			
			if(null != loginSession){
				regId = loginSession.getAdminId();
			}
			
			param.put("regId",regId);
			param.put("modId",regId);
			
			String prodCd   = request.getParameter("prodCd");
			
			param.put("prodCd",        prodCd);
			
			String[] mpicSeqArr        = request.getParameterValues("MPIC_SEQ");
			String[] useYnArr          = request.getParameterValues("USE_YN");
			String[] accessKeyArr          = request.getParameterValues("ACCESS_KEY");
			String[] cidArr          = request.getParameterValues("CID");
			
			param.put("mpicSeqArr",       mpicSeqArr);
			param.put("accessKeyArr",     accessKeyArr);
			param.put("useYnArr",         useYnArr);
			param.put("cidArr",           cidArr);
			
			
			int resultCnt = tprmpicService.updateTPRMPICList(param);
			
			String message  = resultCnt + "건을 "+ messageSource.getMessage("msg.common.success.update", null, Locale.getDefault());
			jObj.put("Code", 1);
			jObj.put("Message",   message);
            
			// 처리성공
		} catch (Exception e) {
			jObj.put("Code", -1);
			jObj.put("Message", e.getMessage());
			// 처리오류
			logger.error("", e);
		}
		
		
		logger.debug("================selectUpdateTprMpicInfo End================");
		return jObj;
	}
	
	/**
	 * Desc : 동영상 등록 페이지
	 * @Method Name : insertTprMpicInfo
	 * @param request
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@RequestMapping("product/insertTprMpicInfo.do")
	public String insertTprMpicInfo(HttpServletRequest request) throws Exception{
		LoginSession loginSession = LoginSession.getLoginSession(request);
		Map<Object, Object> WECANDEOINFO = (Map<Object, Object>) WECANDEOSupport.getUpdateToken().get("dataMap");
		request.getSession().setAttribute("WECANDEOINFO", WECANDEOINFO);
		
		String prodCd   = request.getParameter("prodCd");

		DataMap param = new DataMap(request);
		
		String regId = param.getString("regId");
		
		if(null != loginSession){
			regId = loginSession.getAdminId();
		}
		
		String cid    = idgenService.getNextStringId();
//		String folder = "2003498";
//		String pkg    = "1004641";
		String folder = config.getString("wecandeo.folder");
		String pkg    = config.getString("wecandeo.pkg");
		
		
		Map<Object, Object> paramMap = new HashMap<Object, Object>();
		paramMap.put("prodCd", prodCd);
		paramMap.put("cid", cid);
		paramMap.put("folder", folder);
		paramMap.put("pkg", pkg);
		paramMap.put("token", WECANDEOINFO.get("token") ); 
		paramMap.put("regId", regId);
		tprmpicService.insertTPRMPICinfo(paramMap);
		
		
		request.setAttribute("prodCd", prodCd); 
		request.setAttribute("cid"   , cid); 
		request.setAttribute("folder", folder); 
		request.setAttribute("pkg"   , pkg); 
		request.setAttribute("WECANDEOINFO", WECANDEOINFO );
		return "common/TPRMPICINS";
	}
	
	/**
	 * Desc : 동영상 업로드 정보 체크
	 * @Method Name : checkUpdateInfo
	 * @param request
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@ResponseBody
	@RequestMapping("product/checkUpdateInfo.do")
	public Map<String, Object> checkUpdateInfo(HttpServletRequest request) throws Exception{
		Map<Object, Object> WECANDEOINFO = (Map<Object, Object>) request.getSession().getAttribute("WECANDEOINFO");
		WECANDEOSupport.setData(WECANDEOINFO);
		logger.debug("================checkUpdateInfo Start================");
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		
		 try{
			 Map<String, Object> processMap = (Map<String, Object>) WECANDEOSupport.uploadProcess().get("processMap");
			 String state = (String) WECANDEOSupport.uploadState().get("state");
			 processMap.put("state",state);
			// 성공
			logger.debug("데이터 조회 완료");
			rtnMap.put("result", true);
			rtnMap.put("processMap", processMap);

		} catch (Exception e) {
			// 실패
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
	        rtnMap.put("errMsg", e.getMessage());
		}
		 
		logger.debug("================checkUpdateInfo End================");
		return rtnMap;	
	}
	
	/**
	 * Desc : 업로드 된 정보를 저장
	 * @Method Name : insertTprMpic
	 * @param request
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@ResponseBody
	@RequestMapping("product/insertTprMpic.do")
	public Map<String, Object> insertTprMpic(HttpServletRequest request) throws Exception{
		Map<Object, Object> WECANDEOINFO = (Map<Object, Object>) request.getSession().getAttribute("WECANDEOINFO");
		WECANDEOSupport.setData(WECANDEOINFO);
		logger.debug("================insertTprMpic Start================");
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		LoginSession loginSession = LoginSession.getLoginSession(request);
		
		DataMap param = new DataMap(request);
		String regId = param.getString("regId");
		
		if(null != loginSession){
			regId = loginSession.getAdminId();
		}
		
		try{
			Map<Object, Object> paramMap = new HashMap<Object, Object>();
			paramMap.put("token", WECANDEOSupport.getToken());
			
			paramMap.put("prodCd",             request.getParameter("prodCd"));
			paramMap.put("regId",              regId);
			paramMap.put("modId",              regId);
			
			paramMap.put("accessKey",          request.getParameter("accessKey"));
			paramMap.put("duration",           request.getParameter("duration"));
			paramMap.put("videoHeight",        request.getParameter("videoHeight"));
			paramMap.put("videoWidth",         request.getParameter("videoWidth"));
			paramMap.put("videoFramerate",     request.getParameter("videoFramerate"));
			paramMap.put("folder",             request.getParameter("folder"));
			paramMap.put("pkg",                request.getParameter("pkg"));
			paramMap.put("title",              request.getParameter("title"));
			paramMap.put("content",            request.getParameter("content"));
			paramMap.put("cid",                request.getParameter("cid"));
			paramMap.put("status",             request.getParameter("status"));
			paramMap.put("useYn",              "Y");
			
			tprmpicService.updateTPRMPICinfo(paramMap);
			// 성공
			logger.debug("데이터 조회 완료");
			rtnMap.put("result", true);
			rtnMap.put("resultMsg", messageSource.getMessage("msg.common.success.insert", null, Locale.getDefault()));
			
		} catch (Exception e) {
			// 실패
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		}
		
		logger.debug("================insertTprMpic End================");
		return rtnMap;	
	}
	
	/**
	 * Desc : 동영상 수정 페이지
	 * @Method Name : updateTprMpicInfo
	 * @param request
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@RequestMapping("product/updateTprMpicInfo.do")
	public String updateTprMpicInfo(HttpServletRequest request) throws Exception{
		String prodCd   = request.getParameter("prodCd");
		String mpicSeq   = request.getParameter("mpicSeq");
		
		Map<Object, Object> paramMap = new HashMap<Object, Object>();
		paramMap.put("prodCd", prodCd); 
		paramMap.put("mpicSeq", mpicSeq); 
		Map<Object, Object> tprMpicInfo = tprmpicService.selectTPRMPICInfo(paramMap);
		request.setAttribute("tprMpicInfo", tprMpicInfo); 
		return "common/TPRMPICUPD";
	}
	
	/**
	 * Desc : 업로드 된 정보를 저장
	 * @Method Name : insertTprMpic
	 * @param request
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@ResponseBody
	@RequestMapping("product/updateTprMpic.do")
	public Map<String, Object> updateTprMpic(HttpServletRequest request) throws Exception{
		logger.debug("================updateTprMpic Start================");
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		LoginSession loginSession = LoginSession.getLoginSession(request);
		DataMap param = new DataMap(request);
		String regId = param.getString("regId");
		
		if(null != loginSession){
			regId = loginSession.getAdminId();
		}
		
		try{
			Map<Object, Object> paramMap = new HashMap<Object, Object>();
			String prodCd   = request.getParameter("prodCd");
			String mpicSeq   = request.getParameter("mpicSeq");
			String accessKey   = request.getParameter("accessKey");
			String title   = request.getParameter("title");
			String content   = request.getParameter("content");
			String useYn   = request.getParameter("useYn");
			String cid   = request.getParameter("cid");
			
			paramMap.put("prodCd", prodCd); 
			paramMap.put("mpicSeq", mpicSeq); 
			paramMap.put("accessKey", accessKey); 
			paramMap.put("title", title); 
			paramMap.put("content", content); 
			paramMap.put("useYn", useYn); 
			paramMap.put("cid", cid); 
			paramMap.put("modId",              regId);
			
			tprmpicService.updateTPRMPICinfo2(paramMap);
			
			//처리 결과 메세지 생성
			rtnMap.put("resultCode", 0);
			rtnMap.put("resultMsg", messageSource.getMessage("msg.common.success.update", null, Locale.getDefault()));
			rtnMap.put("result", true);

		} catch (Exception e) {
			// 실패
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
	        rtnMap.put("errMsg", e.getMessage());
		}
		 logger.debug("================updateTprMpic_End================");
		return rtnMap;
	}
	
	/**
	 * Desc : 동영상 플레이어
	 * @Method Name : tprMpicPrevView
	 * @param keyㄱr
	 * @param request
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@RequestMapping("product/tprMpicPrevView.do")
	public String tprMpicPrevView(@RequestParam("key") String key, HttpServletRequest request) throws Exception{
		request.setAttribute("key", key);
		return "common/TPRMPICPrevView";
	}
	
	@ResponseBody
	@RequestMapping("common/updateState.do")
	public Map<String, Object> updateState(@RequestParam Map<Object, Object> paramMap, HttpServletRequest request) throws Exception{
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		try{
			tprmpicService.updateTPRMPICinfo(paramMap);
			// 성공
			logger.debug("데이터 등록 완료");
			rtnMap.put("result", true);
			rtnMap.put("resultMsg", messageSource.getMessage("msg.common.success.insert", null, Locale.getDefault()));
			
		} catch (Exception e) {
			// 실패
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		}
		
		return rtnMap;	
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("common/tprMpicCallback.do")
	public Map<Object, Object> tprMpicCallback(HttpServletRequest request) throws Exception{
		Map<Object, Object> rtnMap = new HashMap<Object, Object>();
		
		Map<Object, Object> paramMap = new HashMap<Object, Object>();
		paramMap.put("accessKey", request.getParameter("org_access_key"));
		paramMap.put("cid",       request.getParameter("cid"));
		paramMap.put("regId",    "000000000001");
		paramMap.put("modId",    "000000000001");
		String errorMsg = "";
		try{
			
			
			// 정보 데이터를 가져온다.
			Map<Object, Object> tprmpicInfo = tprmpicService.selectTPRMPICInfo2(paramMap);
			
			// 인자값에 accesskey 확인
			String accessKey = StringUtils.defaultString( (String) paramMap.get("accessKey") , "");
			// 인자값에 accesskey 가 없을 경우 정보 조회한 데이터를 이용
			if( !"".equals(accessKey) ){
				tprmpicInfo.put("ACCESS_KEY", paramMap.get("accessKey") );
			}
			
			if( ("").equals( tprmpicInfo.get("ACCESS_KEY") ) ){
				logger.error("ACCESS_KEY 가 없습니다.");
				errorMsg = "ACCESS_KEY 가 없습니다.";
				throw new IllegalArgumentException("ACCESS_KEY 가 없습니다.");
			}
			
			String sURL = "http://api.wecandeo.com/web/encoding/status.json?key="+config.getString("wecandeo.licenseKey")+"&access_key="+tprmpicInfo.get("ACCESS_KEY")+"&pkg="+tprmpicInfo.get("PKG");
			
			String jsonData = (String) WECANDEOSupport.sendGet(sURL).get("jsonData");
			logger.debug("jsonData => "+ jsonData);
			
			JSONParser jsonParser = new JSONParser();
			
			JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonData);
			
			Map<Object, Object> encodingStatus = new HashMap<Object, Object>();
			encodingStatus = (Map<Object, Object>) jsonObject.get("encodingStatus");
			Map<Object, Object> videoPublishInfo = new HashMap<Object, Object>();
			videoPublishInfo = (Map<Object, Object>) encodingStatus.get("videoPublishInfo");
			Map<Object, Object> errorInfo = new HashMap<Object, Object>();
			errorInfo = (Map<Object, Object>) encodingStatus.get("errorInfo");
			
			String errorCode = (String) errorInfo.get("errorCode");
			
			if( !("None").equals(errorCode) ){
				logger.error( (String) errorInfo.get("errorMessage") );
				errorMsg = (String) errorInfo.get("errorMessage");
				throw new AlertException( errorInfo.get("errorMessage").toString() );
			}
			
			
			String videoKey = (String) videoPublishInfo.get("videoKey");

			if( ("").equals(videoKey) ){
				logger.error("videoKey 가 없습니다.");
				errorMsg = "videoKey 가 없습니다.";
				throw new IllegalArgumentException("videoKey 가 없습니다.");
			}
			
			paramMap.put("mpicUrl", videoKey);
			paramMap.put("status", "COMPLETE");
			tprmpicService.updateTPRMPICinfo(paramMap);
			// 성공
			logger.debug("데이터 등록 완료");
			rtnMap.put("result", true);
			rtnMap.put("resultMsg", messageSource.getMessage("msg.common.success.insert", null, Locale.getDefault()));
			
		} catch (Exception e) {
			// 실패
			Map<Object, Object> errorInfo = new HashMap<Object, Object>();
			errorInfo.put("useYn", "N");
			errorInfo.put("cid", paramMap.get("cid"));
			errorInfo.put("status", "SERVICING_ERR ");
			tprmpicService.updateTPRMPICinfo(errorInfo);
			if( "".equals(errorMsg) ){
				logger.error("error message --> " + e.getMessage());
				rtnMap.put("result", false);
				rtnMap.put("errMsg", e.getMessage());
			}else{
				logger.error("error message --> " + errorMsg);
				rtnMap.put("result", false);
				rtnMap.put("errMsg", errorMsg);
			}
		}
		
		return rtnMap;	
	}
}
