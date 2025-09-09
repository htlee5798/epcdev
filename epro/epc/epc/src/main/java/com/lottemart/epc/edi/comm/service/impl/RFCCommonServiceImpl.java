package com.lottemart.epc.edi.comm.service.impl;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lcnjf.util.DateUtil;
import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.dao.RFCCommonDao;
import com.lottemart.epc.edi.comm.model.HistoryVo;
import com.lottemart.epc.edi.comm.service.HistoryCommonService;
import com.lottemart.epc.edi.comm.service.RFCCommonService;

import lcn.module.framework.property.ConfigurationService;

/**
 * 
 * @Class Name : RFCCommonServiceImpl.java
 * @Description : SAP Connection 공통
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	
 *               </pre>
 */
@Service("rfcCommonService")
public class RFCCommonServiceImpl implements RFCCommonService {
	
	private static final Logger logger = LoggerFactory.getLogger(RFCCommonServiceImpl.class);
	
	@Resource(name = "configurationService")
	private ConfigurationService config;

	@Autowired
	private RFCCommonDao rfcCommonDao;
	
	@Autowired
	private HistoryCommonService historyCommonService;
	
	
	//===============================================
	// History 생성용 공통 변수
	//===============================================
	private static final String OK_CODE = "S";
	private static final String NO_CODE = "F";
	private static final String IF_GBN = "SAPIF";
	private static String IF_RSLT_CD = "";		//인터페이스결과코드
	private static String IF_RSLT_MSG = "";		//인터페이스결과메세지
	
	
	/**
	 * RFC CALL (NO BATCH ID)
	 */
	public Map<String, Object> rfcCall(String proxyNm, String params, String adminId) throws Exception {
		return this.rfcCall(proxyNm, params, adminId, null); 
	}

	/**
	 * RFC Call
	 */
	public Map<String, Object> rfcCall(String proxyNm, String params, String adminId, String batchId) throws Exception {
		if (proxyNm == null || proxyNm.equals("")) {
			return null;
		}
		
		logger.info("---------------- [ START - RFC CALL ] ------------------- ");
		//HISTORY 용 I/F 결과 초기셋팅
		IF_RSLT_CD = NO_CODE;				//인터페이스 결과코드
		IF_RSLT_MSG= "호출에 실패하였습니다.";	//인터페이스 결과메세지
		String ifStDt = "";		//인터페이스 시작일시
		String ifEndDt = "";	//인터페이스 종료일시
		
		//
		Map<String, Object>	resultMap = new HashMap<String, Object>();
		HashMap<String, Object> hmLog = new HashMap<String, Object>();

		HttpURLConnection conn = null;
		StringBuffer retSb = null;
		String retStr = "";

		JSONParser jsonParser = new JSONParser();
		JSONObject jsonString = null;
		
		String connURL="";		//Connection URL
		
		//인터페이스 시작일시
		ifStDt = DateUtil.getCurrentTime(DateUtil.TIMESTAMP_FORMAT);
		try {
			String sapUrl 	= config.getString("sap.conn.url");
			String user 	= config.getString("sap.conn.user");
			String passwd 	= config.getString("sap.conn.passwd");

			connURL = sapUrl + "" + proxyNm + "/";

			// url 에 접속 계정 사용
			URL url = new URL(connURL);
			conn = (HttpURLConnection) url.openConnection();

			// HTTP basic authentication
			String userpassword = user + ":" + passwd;
			String encodedAuthorization = Base64.encodeBase64String(userpassword.getBytes());
			//logger.debug("----->" + conn);

			// 스트림 리드 타임아웃 20초
			conn.setReadTimeout(20000);
			conn.setDoOutput(true);

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json;");
			conn.setRequestProperty("Authorization", "Basic " + encodedAuthorization);

			// request json data
			/*OutputStream os = conn.getOutputStream();
			//os.write(params.getBytes());
			os.write(params.getBytes(Charset.forName("UTF-8")));
			os.flush();*/
			
			// try-with-resources >> bw 자원 해제(close)
			try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"))) {
				bw.write(params);
				bw.flush();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
			
			logger.debug("conn.getResponseCode() ----->" + conn.getResponseCode());
			logger.debug("conn.getResponseMessage()----->" + conn.getResponseMessage());
			// 응답코드 체크
			if (conn.getResponseCode() != 200) {
				//logger.debug("----->http response code error(" + conn.getResponseCode() + ")");
				if (conn != null) {
					IF_RSLT_MSG= "연결에 실패하였습니다.(response::"+conn.getResponseCode()+")";	//인터페이스 결과메세지
					conn.disconnect();
				}
				//System.exit(1);
			}
			
			// try-with-resources >> is,baos 자원 해제(close) 
			try (InputStream is = conn.getInputStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
	
				byte[] buf = new byte[2048];
				int n;
				while ((n = is.read(buf)) != -1) {
					baos.write(buf, 0, n);
				}
				baos.flush();
	
				// Return 받은 Json 정보
				retSb = new StringBuffer(new String(baos.toByteArray(), "UTF-8"));
			} catch (IOException e) {
			   logger.error("Error reading response: " + e.getMessage());
			}
			
			//return 받은 json 정보 string
			retStr = retSb.toString();
			
			// Return 할 json Format 정보
			jsonString = (JSONObject) jsonParser.parse(retStr);
			resultMap.put("result", jsonString);

			conn.disconnect();
			conn = null;
			
			IF_RSLT_CD = OK_CODE;				//인터페이스 결과코드
			IF_RSLT_MSG= "정상적으로 호출되었습니다.";	//인터페이스 결과메세지
		} catch (MalformedURLException e) {
			hmLog.put("rtnData", e.toString());
			IF_RSLT_MSG= "호출에 실패하였습니다.("+e.getMessage()+")";	//인터페이스 결과메세지
		} catch (IOException e) {
			hmLog.put("rtnData", e.toString());
			IF_RSLT_MSG= "호출에 실패하였습니다.("+e.getMessage()+")";	//인터페이스 결과메세지
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
			
			//인터페이스 종료일시
			ifEndDt = DateUtil.getCurrentTime(DateUtil.TIMESTAMP_FORMAT);
			// 실행로그생성  ---------------------------------------------------*/
			try {
				//소요시간
				String ifDur = DateUtil.getDurationMillis(ifStDt, ifEndDt, DateUtil.TIMESTAMP_FORMAT);
				
				String workId = "";	//작업자아이디
				String workNm = "";	//작업자명
				
				//---- 작업자 셋팅
				//로그인세션
				EpcLoginVO epcLoginVO = this.getWorkSessionVo();
				if(epcLoginVO != null) {	
					//로그인세션이 있을 경우,
					workId = epcLoginVO.getLoginWorkId();
					workNm = ("".equals(StringUtils.defaultString(epcLoginVO.getAdminNm())))? epcLoginVO.getLoginNm():epcLoginVO.getAdminNm();
				}else if(batchId != null && !"".equals(batchId)) {
					//로그인 세션 없는데, batchID 있는 경우, BATCH로 설정
					workId = "BATCH";
					workNm = "BATCH";
				}else {
					//로그인세션, batchId 모두 없을 경우 adminId로 셋팅
					workId = adminId;	
					workNm = adminId;
				}
				
				//---- 클라이언트 IP 추출
				String clientIp = historyCommonService.getClientIpAddr();
				
				//---- 실행로그 insert
				HistoryVo pLogVo = new HistoryVo();
				pLogVo.setIfGbn(IF_GBN);			//인터페이스구분코드
				pLogVo.setIfCd(proxyNm);			//인터페이스코드 -> SAP 호출 시, PROXY_NM 으로 셋팅
				pLogVo.setReqPayLoad(params);		//요청 파라미터
				pLogVo.setResPayLoad(retStr);		//응답 데이터
				pLogVo.setResultCd(IF_RSLT_CD);		//결과 코드
				pLogVo.setResultMsg(IF_RSLT_MSG);	//결과 메세지
				pLogVo.setIfStartDt(ifStDt);		//인터페이스 시작일시
				pLogVo.setIfEndDt(ifEndDt);			//인터페이스 종료일시
				pLogVo.setDuration(ifDur);			//인터페이스 소요시간
				pLogVo.setIfType("PRX");			//인터페이스 유형 (PROXY)
				pLogVo.setIfUrl(connURL);			//인터페이스 호출 URL
				pLogVo.setIfDirection("O");			//인터페이스 방향 (O:아웃바운드)
				pLogVo.setSysGbn("ECO");			//시스템구분
				pLogVo.setCallUserId(workId);		//호출아이디
				pLogVo.setCallUserNm(workNm);		//호출자명
				pLogVo.setCallIp(clientIp);			//호출IP
				//연관 배치 로그 아이디 - 배치 내부에서 call 하는 경우, 연관 batchId 셋팅
				pLogVo.setBatchLogId(batchId);	
				
				historyCommonService.insertTpcIfLog(pLogVo);
			}catch(Exception e) {
				logger.error("SAP I/F History Insert Error ::::: "+e.getMessage());
			}
			/*-------------------------------------------------------------*/
			
			logger.info("---------------- [ END - RFC CALL ] ------------------- ");
		}

		return resultMap;
	}
	
	/**
	 * 세션정보 추출
	 * @return EpcLoginVO
	 */
	private EpcLoginVO getWorkSessionVo() {
		EpcLoginVO epcLoginVO = null;
		String sessionKey = ConfigUtils.getString("lottemart.epc.session.key");
		
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
}
