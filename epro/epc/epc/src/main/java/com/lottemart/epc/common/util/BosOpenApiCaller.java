package com.lottemart.epc.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcnjf.util.DateUtil;
import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.model.HistoryVo;
import com.lottemart.epc.edi.comm.service.BosOpenApiService;
import com.lottemart.epc.edi.comm.service.HistoryCommonService;

import lcn.module.framework.property.ConfigurationService;

@Component
public class BosOpenApiCaller {
	
	@Autowired
	BosOpenApiService bosOpenApiService;
	
	@Autowired
	HistoryCommonService historyCommonService;
	
	@Autowired
	private ConfigurationService config;
	
	private static final Logger logger = LoggerFactory.getLogger(BosOpenApiCaller.class);
	private static final String OK_CODE = "S";
	private static final String NO_CODE = "F";
	private static final String IF_GBN = "BOSIF";
	private static final String RES_OK_CODE = "OK";
	
	public Map<String, Object> call(String ifCd) {
		return call(ifCd, null, null, 1000 * 30);
		
	}
	public Map<String, Object> call(String ifCd, Map<String, Object> paramMap) {
		return call(ifCd, paramMap, null, 1000 * 30);
	}
	
	public Map<String, Object> call(String ifCd, Map<String, Object> paramMap, String batchLogId) {
		HistoryVo pLogVo = new HistoryVo();
		pLogVo.setBatchLogId(batchLogId);
		return call(ifCd, paramMap, pLogVo, 1000 * 30);
	}
	
	public Map<String, Object> call(String ifCd, Map<String, Object> paramMap, HistoryVo pLogVo) {
		return call(ifCd, paramMap, pLogVo, 1000 * 30);
	}
	
	public Map<String, Object> call(String ifCd, Map<String,Object> paramMap, HistoryVo pLogVo, int readTimeout) {
		logger.info("---------------- [ START - BOS OPEN API CALLER ] ------------------- ");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("msgCd", NO_CODE);
		resultMap.put("message", "OPEN API 호출에 실패하였습니다.");
		
		BufferedReader br = null;
		InputStream in = null;
		InputStreamReader isr = null;
		StringBuilder sb = new StringBuilder();
		
		String reqPayload = "";	//요청 파라미터 string
		String resPayload = ""; //응답 데이터 string
		String ifStDt, ifEndDt, ifDur;	//인터페이스 시작,종료일시, 소요시간
		String openapiUrl = "";	//인터페이스 호출 URL
		
		//인터페이스 시작일시
		ifStDt = DateUtil.getCurrentTime(DateUtil.TIMESTAMP_FORMAT);
		try {
			/* 1. I/F별 API URL SETTING ---------------------------------------*/
			//인터페이스 별 API URL 조회
			String apiUrl = bosOpenApiService.selectBosOpenApiUrl(ifCd);
			//맵핑되는 API URL 없음
			if("".equals(StringUtils.defaultString(apiUrl))) {
				logger.error("BOS OPEN API CALLER ("+ifCd+") - apiUrl : NO_MAPPING_API_URL");
				return resultMap;
			}
			
			/********************************** CONNECTION INFO **********************************/
			//connection 정보
			String userId = ConfigUtils.getString("epc.bos.openapi.id");			//아이디
			String userPwd = ConfigUtils.getString("epc.bos.openapi.pwd");			//패스워드
			String companyNo = ConfigUtils.getString("epc.bos.openapi.companyNo");	//사업자번호
			
			String protocol = ConfigUtils.getString("epc.bos.openapi.protocol");	//http, https
			String host = ConfigUtils.getString("epc.bos.openapi.host");			//host 주소
			String baseUrl = ConfigUtils.getString("epc.bos.openapi.baseUrl");		//호출 base url
			
			int port = (protocol.toLowerCase().startsWith("https"))?443:80;
			
			openapiUrl = host + baseUrl + "/" + apiUrl + "/" + companyNo;
			openapiUrl = protocol + openapiUrl.replaceAll("//", "/");
			/*************************************************************************************/
			
			logger.info("-- API URL :: "+apiUrl);
			logger.info("-- API FULL URL :: "+openapiUrl);
			
			HttpClient client = new HttpClient();
			client.getState().setCredentials(new AuthScope(host, port), new UsernamePasswordCredentials(userId, userPwd));
			client.getParams().setAuthenticationPreemptive(true);
			client.setTimeout(readTimeout);
			
			//파라미터 맵 없을 경우 빈 map 생성
			if(paramMap == null || paramMap.isEmpty()) {
				paramMap = new HashMap<String,Object>();
			}
			
			//요청 파라미터
			JSONObject reqJsonObj = new JSONObject(paramMap);
			reqPayload = reqJsonObj.toString();
			logger.info("-- reqPayload :: "+reqPayload);
			
			in = new ByteArrayInputStream(reqPayload.getBytes("utf-8"));
			
			// connection ========================================================================
			PostMethod method = new PostMethod(openapiUrl);
			method.setRequestHeader("Content-type", "application/json");
			method.setRequestBody(in);
			method.setDoAuthentication(true);
			
			int returnCode = client.executeMethod(method);
			logger.info("-- returnCode ::"+returnCode);
			
			if (returnCode == HttpStatus.SC_NOT_IMPLEMENTED) {
				logger.error("BOS OPEN API CALLER ("+ifCd+"The Post method is not implemented by this URI");
				// still consume the response body
				logger.error(method.getResponseBodyAsString()); 
			} else {
				isr = new InputStreamReader(method.getResponseBodyAsStream(), "utf-8");
				br = new BufferedReader(isr, 1024*64);		//64KB
				
				String readLine;
				while (((readLine = br.readLine()) != null)) {
					sb.append(readLine);
				}
			}
			
			// 응답결과 셋팅 ========================================================================
			//응답 message
			ObjectMapper objMapper = new ObjectMapper();
			Map<String,Object> resMap = objMapper.readValue(sb.toString(), new TypeReference<Map<String,Object>>() {});
			
			//응답 데이터
			JSONObject resObj = new JSONObject(resMap);
			resPayload = resObj.toString();
			logger.info("-- resPayload :: "+resPayload);
			
			Map<String,Object> header = (Map<String, Object>) MapUtils.getObject(resMap, "Header");
			if(header == null) {
				logger.error("BOS OPEN API CALLER ("+ifCd+") - header : 응답 결과가 없습니다.");
				resultMap.put("message", "응답 결과가 없습니다.");
				return resultMap;
			}
			
			//응답결과
			int resultCode = MapUtils.getInteger(header, "resultCode");	//응답코드(0:실패, 1:성공)
			String resultMsg = MapUtils.getString(header, "resultMsg");	//응답메세지
			
			//응답 실패 시,
			if(resultCode == 0) {
				logger.error("BOS OPEN API CALLER ("+ifCd+") - resultCode : "+resultCode);
				logger.error("BOS OPEN API CALLER ("+ifCd+") - resultMsg : "+resultMsg);
				resultMap.put("message", "응답 실패 하였습니다.");
				return resultMap;
			}
			
			//응답 결과 성공이 아닐 경우,
			if(!RES_OK_CODE.equals(resultMsg)) {
				logger.error("BOS OPEN API CALLER ("+ifCd+") - resultCode : "+resultCode);
				logger.error("BOS OPEN API CALLER ("+ifCd+") - resultMsg : "+resultMsg);
				resultMap.put("message", "응답 요청 처리 중 오류가 발생했습니다.");
				return resultMap;
			}
			
			//응답 성공 시, 결과데이터 추출
			Object bodyObj = MapUtils.getObject(resMap, "Body");
			
			if(bodyObj instanceof List) {
				List<Map<String,Object>> bodyMapList = (List<Map<String, Object>>) bodyObj;
				resultMap.put("resultData", bodyMapList);
			}else if(bodyObj instanceof Map) {
				Map<String,Object> bodyMap = (Map<String,Object>) bodyObj;
				resultMap.put("resultData", bodyMap);
			}else {
				resultMap.put("resultData", bodyObj);
			}
			
			resultMap.put("msgCd", OK_CODE);
			resultMap.put("message", "정상적으로 호출되었습니다.");
		} catch(Exception e) {
			logger.error("BOS OPEN API CALLER ("+ifCd+") - resultCode : "+e.getMessage());
			resultMap.put("message", e.getMessage());
		} finally {
			//인터페이스 종료일시
			ifEndDt = DateUtil.getCurrentTime(DateUtil.TIMESTAMP_FORMAT);
			//소요시간
			ifDur = DateUtil.getDurationMillis(ifStDt, ifEndDt, DateUtil.TIMESTAMP_FORMAT);
			
			if(br != null) try {br.close();} catch (IOException e) {logger.error(e.getMessage());}
			if(in != null) try {in.close();} catch (IOException e) {logger.error(e.getMessage());}
			if(isr != null) try {isr.close();} catch (IOException e) {logger.error(e.getMessage());}
			
			// 실행로그생성  ---------------------------------------------------*/
			try {
				String resultCd = MapUtils.getString(resultMap, "msgCd", "");
				String resultMsg = MapUtils.getString(resultMap, "message", "");
				
				if(pLogVo == null) pLogVo = new HistoryVo();
				
				//작업자정보
				String workId = StringUtils.defaultString(pLogVo.getWorkId());	//작업자아이디
				String workNm = StringUtils.defaultString(pLogVo.getWorkNm());	//작업자명
				String clientIp = this.getClientIp();	//클라이언트 IP
				
				//workId가 없을 경우, 서블릿리퀘스트가 있으면, 세션에서 정보 추출해서 셋팅 ==> 업체: 업체코드/관리자: adminId (loginId)
				if("".equals(workId)) {
					EpcLoginVO epcLoginVO = this.getWorkSessionVo();
					
					if(epcLoginVO != null) {
						//작업자정보
						workId = epcLoginVO.getLoginWorkId();
						workNm = ("".equals(StringUtils.defaultString(epcLoginVO.getAdminNm())))? epcLoginVO.getLoginNm():epcLoginVO.getAdminNm();
					}else {
						workId = "BATCH";
						workNm = "BATCH";
					}
				}
				
				pLogVo.setIfGbn(IF_GBN);			//인터페이스구분코드- 보스API
				pLogVo.setIfCd(ifCd);				//인터페이스코드
				pLogVo.setReqPayLoad(reqPayload);	//요청 파라미터
				pLogVo.setResPayLoad(resPayload);	//응답 데이터
				pLogVo.setResultCd(resultCd);		//결과 코드
				pLogVo.setResultMsg(resultMsg);		//결과 메세지
				pLogVo.setIfStartDt(ifStDt);		//인터페이스 시작일시
				pLogVo.setIfEndDt(ifEndDt);			//인터페이스 종료일시
				pLogVo.setDuration(ifDur);			//인터페이스 소요시간
				pLogVo.setIfType("API");			//인터페이스 유형 (API)
				pLogVo.setIfUrl(openapiUrl);		//인터페이스 호출 URL
				pLogVo.setIfDirection("O");			//인터페이스 방향 (O:아웃바운드)
				pLogVo.setSysGbn("BOS");			//시스템구분 - BOS
				pLogVo.setCallUserId(workId);		//호출아이디
				pLogVo.setCallUserNm(workNm);		//호출자명
				pLogVo.setCallIp(clientIp);			//호출IP
				
				//연관 배치 로그 아이디 - 배치 내부에서 api call 하는 경우, 연관 batch의 logid 셋팅해서 넘어옴
//				pLogVo.setBatchLogId(batchLogId);	
				historyCommonService.insertTpcIfLog(pLogVo);
			}catch(Exception e) {
				logger.error("BOS I/F History Insert Error ::::: "+e.getMessage());
			}
			/*-------------------------------------------------------------*/
			logger.info("---------------- END - BOS OPEN API CALLER ------------------- ");
		}
		
		return resultMap;
	}
	
	/**
	 * 세션정보 추출
	 * @return EpcLoginVO
	 */
	private EpcLoginVO getWorkSessionVo() {
		EpcLoginVO epcLoginVO = null;
		String sessionKey = config.getString("lottemart.epc.session.key");
		
		try {
			ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpServletRequest request = attrs.getRequest();
			
			if(request != null) {
				epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);
			}
			
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		
		return epcLoginVO;
	}
	
	/**
	 * IP 주소 추출
	 * @return String
	 */
	private String getClientIp() {
		String clientIp = "";
		try {
			ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpServletRequest request = attrs.getRequest();
			clientIp = historyCommonService.getClientIpAddr(request);
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		return clientIp;
	}

}
