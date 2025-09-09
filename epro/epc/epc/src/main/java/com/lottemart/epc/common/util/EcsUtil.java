package com.lottemart.epc.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lcnjf.util.DateUtil;
import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.model.HistoryVo;
import com.lottemart.epc.edi.comm.service.HistoryCommonService;

/**
 * 
 * @Class Name : EcsUtil.java
 * @Description : ECS 계약/공문 공통 Util 
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2025.04.10		yun				최초생성
 *               </pre>
 */
@Component
public class EcsUtil {
	
	@Autowired
	private HistoryCommonService historyCommonService;
	
	private static final Logger logger = LoggerFactory.getLogger(EcsUtil.class);
	
	private static final String OK_CODE = "S";
	private static final String NO_CODE = "F";
	
	//===============================================
	// History 생성용 공통 변수
	//===============================================
	private static final String IF_GBN = "ECSGB";		//ECS는 양식구분값으로 히스토리생성
	private static String IF_ST_DT = "";
	private static String IF_END_DT = "";
	private static String IF_SYS_GBN = "ECS";
	private static String IF_RSLT_CD = NO_CODE;
	private static String IF_RSLT_MSG = "";
	private static String IF_REQ_PAYLOAD = "";
	private static String IF_RES_PAYLOAD = "";
	private static String IF_CD = "";
	
	
	/**
	 * ECS 계약/공문 발송
	 * @param header
	 * @param body
	 * @return Map<String,Object>
	 */
	public Map<String, Object> sendEcsDcDoc(String ecsGbn, String header, String body) {
		logger.info("---------------- [ START - ECS I/F ] ------------------- ");
		HashMap<String, Object> returnMap = new HashMap<String, Object>();		//반환할 맵
		boolean result = false;
		
		URL url = null;
		HttpURLConnection connection = null;
		OutputStream os = null;
		BufferedReader br = null;
		InputStream is = null;
		InputStreamReader isr = null;
		
		String ecsUrl = "";			//ECS 반환URL
		String resChk = "";			//ECS 응답결과 확인용
		String errMsg = "";			//오류메세지
		
		//인터페이스코드 (양식정보)
		IF_CD = ecsGbn;
		//인터페이스 시작일시
		IF_ST_DT = DateUtil.getCurrentTime(DateUtil.TIMESTAMP_FORMAT);
		try {
			url = new URL(ConfigUtils.getString("ecs.conn.url"));
			
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			connection.setDoOutput(true);
			
			String xmlData = createDcDoc(header, body);			//ECS 계약 본문 생성
			String urlEncXmlData = URLEncoder.encode(xmlData, "UTF-8");
			String sendData = "data="+urlEncXmlData;			//ECS 전송 Data
			logger.info(" xmlData === " +  urlEncXmlData );
			IF_REQ_PAYLOAD = xmlData;		//history_전송파라미터
			
			// 데이터 전송
			os = connection.getOutputStream();
			byte[] input = sendData.getBytes("UTF-8");
			os.write(input, 0, input.length);
			os.flush();
			os.close();
			
			int responseCode = connection.getResponseCode();
			logger.info(" Response Code ::::: " + responseCode );
			
			//응답결과
			switch(responseCode) {
			case 200:
				break;		//정상응답
			case 500:		//서버오류 - 전송 xml 데이터 확인필요
				errMsg = "[500] 공문(계약)생성 중 오류가 발생하였습니다.";
				logger.error(errMsg); 
				returnMap.put("errCode", "ERR01");
				returnMap.put("errMsg", errMsg);
				return returnMap;
				default:
					errMsg = "["+responseCode+"] ECS 응답이 없습니다.";
					logger.error(errMsg);
					returnMap.put("errCode", "ERR01");
					returnMap.put("errMsg", errMsg);
					return returnMap;
			
			}
			
			
			// 서버 응답 읽기
			is = connection.getInputStream();
			isr = new InputStreamReader(is, "UTF-8");
			br = new BufferedReader(isr);
			
			StringBuilder response = new StringBuilder();
			String responseLine;
			while( (responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
			
			logger.info("response ::::::" + response);
			
			//응답 string 변환
			String resStr = response.toString();
			IF_RES_PAYLOAD = resStr;		//history_응답파라미터
			
			//응답 결과 없음
			if(resStr == null || "".equals(resStr)) {
				errMsg = "ECS 응답 결과가 없습니다.";
				logger.error(errMsg);
				returnMap.put("errCode", "ERR02");
				returnMap.put("errMsg", errMsg);
				return returnMap;
			}
			
			//응답 결과가 정상인지 확인
			resChk = resStr.split("-")[0];
			if(!"00".equals(resChk)) {
				errMsg = resStr;
				logger.error(errMsg);
				returnMap.put("errCode", "ERR04");
				returnMap.put("errMsg", errMsg);
				return returnMap;
			}
			
			//응답 결과에서 ECS URL 추출
			ecsUrl = resStr.split("-")[1];
			//추출실패
			if(ecsUrl == null || "".equals(ecsUrl)) {
				errMsg = "ECS URL 추출에 실패하였습니다.";
				logger.error(errMsg);
				returnMap.put("errCode", "ERR03");
				returnMap.put("errMsg", errMsg);
				return returnMap;
			}
			
			//연결해제
			connection.disconnect();
			//성공 시 결과값 셋팅
			result = true;
		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
			returnMap.put("errMsg", e.getMessage());
		} catch (ProtocolException e) {
			logger.error(e.getMessage());
			returnMap.put("errMsg", e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
			returnMap.put("errMsg", e.getMessage());
		} finally {
			if(os!=null)try {os.close();} catch (IOException e) {logger.error(e.getMessage());}
			if(br!=null)try {br.close();} catch (IOException e) {logger.error(e.getMessage());}
			if(isr!=null)try {isr.close();} catch (IOException e) {logger.error(e.getMessage());}
			if(connection!=null) connection.disconnect();
			
			IF_END_DT = DateUtil.getCurrentTime(DateUtil.TIMESTAMP_FORMAT);	//인터페이스 종료일시
			
			returnMap.put("result", result);	//결과 true,false
			returnMap.put("url", ecsUrl);		//ECS 반환URL
			
			
			//
			IF_RSLT_CD = (result)? OK_CODE:NO_CODE;
			IF_RSLT_MSG = (result)? "정상적으로 호출되었습니다.":MapUtils.getString(returnMap, "errMsg", "ECS 호출에 실패하였습니다.");
			
			insertIfHistory();
			logger.info("---------------- [ END - ECS I/F ] ------------------- ");
		}
		
		
		return returnMap;
	}
	
	/**
	 * ECS 계약/공문 본문 Xml생성 - header
	 * @param param
	 * @return String
	 */
	public static String createHeader(Map<String, Object> param) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("<HEAD_INFO>\n");
		
		sb.append(createContent("SYS_CODE", MapUtils.getString(param, "SYS_CODE", "PC")));
		sb.append(createContent("MASTER_ID", MapUtils.getString(param, "MASTER_ID", "")));
		sb.append(createContent("FAMILY_YN", MapUtils.getString(param, "FAMILY_YN", "")));
		sb.append(createContent("COMP_SEQ", MapUtils.getString(param, "COMP_SEQ", "")));
		sb.append(createContent("CONT_CODE", MapUtils.getString(param, "CONT_CODE", "")));
		sb.append(createContent("CONT_CODE_OLD", MapUtils.getString(param, "CONT_CODE_OLD", "")));
		sb.append(createContent("DC_NAME", MapUtils.getString(param, "DC_NAME", "")));
		sb.append(createContent("DC_CONDATE", MapUtils.getString(param, "DC_CONDATE", "")));
		sb.append(createContent("DC_FROM", MapUtils.getString(param, "DC_FROM", "")));
		sb.append(createContent("DC_END", MapUtils.getString(param, "DC_END", "")));
		sb.append(createContent("DC_GONAMT", MapUtils.getString(param, "DC_GONAMT", "0")));
		sb.append(createContent("DC_VAT", MapUtils.getString(param, "DC_VAT", "0")));
		sb.append(createContent("DC_PREAMT", MapUtils.getString(param, "DC_PREAMT", "0")));
		sb.append(createContent("DC_MIDAMT", MapUtils.getString(param, "DC_MIDAMT", "0")));
		sb.append(createContent("DC_JANAMT", MapUtils.getString(param, "DC_JANAMT", "0")));
		sb.append(createContent("PAY_TERMS", MapUtils.getString(param, "PAY_TERMS", "")));
		sb.append(createContent("EMP_SABUN", MapUtils.getString(param, "EMP_SABUN", "")));
		sb.append(createContent("EMP_NAME", MapUtils.getString(param, "EMP_NAME", "")));
		sb.append(createContent("DEP_CD", MapUtils.getString(param, "DEP_CD", "")));
		sb.append(createContent("LIN_CD", MapUtils.getString(param, "LIN_CD", "")));
		sb.append(createContent("DC_CD", MapUtils.getString(param, "DC_CD", "")));
		sb.append(createContent("DC_NM_CD", MapUtils.getString(param, "DC_NM_CD", "")));
		
		sb.append("</HEAD_INFO>\n");
		
		return sb.toString();
	}
	
	/**
	 * Xml 전문 병합
	 * @param header
	 * @param body
	 * @return String
	 */
	public static String createDcDoc(String header,String body) {
		StringBuilder sb = new StringBuilder();
		sb.append("<root>\n");
		sb.append(header);
		sb.append(body);
		sb.append("</root>\n");
		
		// '%' 문자 전송 시, 500error 발생 --> 퍼센트문자 URL Encoding 처리함 
//		return sb.toString().replaceAll("%", "%25").replaceAll("&", "%26");
		return sb.toString();
	}
	
	/**
	 * Xml생성_CDATA로 값 묶음
	 * @param val
	 * @return String
	 */
	public static String getCData(Object val) {
		return "<![CDATA[" + (val == null ? "" : val) + "]]>"; 
	}
	
	/**
	 * Xml생성_Key, Value로 내용구성(String)
	 * @param key
	 * @param value
	 * @return String
	 */
	public static String createContent(String key,String value) {
		return "<"+key+"><![CDATA["+(value == null ? "":value)+"]]></"+key+">\n";
	}
	
	/**
	 * Xml생성_Key, Value로 내용구성(Object)
	 * @param key
	 * @param value
	 * @return
	 */
	public static String createContent(String key, Object value) {
		return "<"+key+"><![CDATA[" + (value == null ? "" : value.toString()) + "]]></"+key+">\n";
	}
	
	/**
	 * Xml생성_Key, Value로 내용구성 및 값이 없을 경우 기본 셋팅(Object)
	 * @param key
	 * @param value
	 * @param def
	 * @return String
	 */
	public static String createContent(String key, Object value, String def) {
		if(def == null) def = "";
		return "<"+key+"><![CDATA[" + (value == null ? def : value.toString()) + "]]></"+key+">\n";
	}
	
	/**
	 * Xml생성_Map값 추출하여 단건항목 셋팅 (Custom)
	 * @param listNm
	 * @param dataMap
	 * @return
	 */
	public static String getMapData(String listNm, Map<String,Object> dataMap) {
		Set<String> keySet = dataMap.keySet();
		Iterator<String> keys = keySet.iterator();
		
		String key = "";
		String val = "";
		
		StringBuilder sb = new StringBuilder();
		sb.append("<"+listNm+">\n");
		
		while (keys.hasNext()) {
			key = (String) keys.next();
			val = MapUtils.getString(dataMap, key, "");
			
			sb.append(createContent(key, val));
		}
		
		sb.append("</"+listNm+">\n");
		
		return sb.toString();
	}
	
	/**
	 * Xml생성_Map값 추출하여 단건항목 셋팅 (Official)
	 * @param dcNmCd
	 * @param dataMap
	 * @return String
	 */
	public static String getMapOfficialItem(String dcNmCd, Map<String,Object> dataMap) {
		Set<String> keySet = dataMap.keySet();
		Iterator<String> keys = keySet.iterator();
		
		String key = "";
		String val = "";
		
		StringBuilder sb = new StringBuilder();
		
		while (keys.hasNext()) {
			key = (String) keys.next();
			val = MapUtils.getString(dataMap, key, "");
			
			sb.append("<IF_RECV_OFFICIAL_ITEMS>\n");
			sb.append(createContent("DC_NM_CD", dcNmCd));	//계약서코드
			sb.append(createContent("ITEM_ID", key));
			sb.append(createContent("ITEM_DATA", val));
			sb.append("</IF_RECV_OFFICIAL_ITEMS>\n");
		}
		
		
		return sb.toString();
	}
	
	/**
	 * Xml생성_MapList값 추출하여 테이블 리스트 데이터 셋팅 (Official)
	 * @param dcNmCd
	 * @param listNum
	 * @param dataListMap
	 * @return String
	 */
	public static String getMapOfficialTable(String dcNmCd, String listNum, List<Map<String,Object>> dataListMap) {
		return getMapTableData("IF_RECV_OFFICIAL_TABLE_ROW", dcNmCd, listNum, dataListMap);
	}
	
	/**
	 * Xml생성_MapList값 추출하여 테이블 리스트 데이터 셋팅 (Custom)
	 * @param dcNmCd
	 * @param listNum
	 * @param dataListMap
	 * @return String
	 */
	public static String getMapTableData(String tableNm, String dcNmCd, String listNum, List<Map<String,Object>> dataListMap) {
		if(dataListMap == null || dataListMap.size() == 0) return "";
		
		StringBuilder sb = new StringBuilder();
		Set<String> keySet;
		Iterator<String> keys;
		String key = "";
		String val = "";
		
		for(Map<String,Object> dataMap : dataListMap) {
			keySet = dataMap.keySet();
			keys = keySet.iterator();
			
			sb.append("<"+tableNm+">\n");
			sb.append(createContent("DC_NM_CD", dcNmCd));	//계약서코드
			sb.append(createContent("LIST_NUM", listNum));	//몇번째 표인지
			while (keys.hasNext()) {
				key = (String) keys.next();
				val = MapUtils.getString(dataMap, key, "");
				
				sb.append(createContent(key, val));
			}
			sb.append("</"+tableNm+">\n");
		}
		
		return sb.toString();
	}
	
	/**
	 * IF 로그생성
	 * @param reqPayload
	 * @throws Exception
	 */
	private void insertIfHistory(){
		try {
			/*
			 * 1) 작업자정보
			 */
			//로그인세션
			EpcLoginVO epcLoginVO = this.getWorkSessionVo();
			
			//작업자 정보
			String workId = "";	//작업자아이디
			String workNm = "";	//작업자명
			
			//로그인 세션 존재 시, 작업자 정보 setting
			if(epcLoginVO != null) {
				workId = epcLoginVO.getLoginWorkId();
				workNm = ("".equals(StringUtils.defaultString(epcLoginVO.getAdminNm())))? epcLoginVO.getLoginNm():epcLoginVO.getAdminNm();
			}
			
			//클라이언트 IP
			String clientIp = historyCommonService.getClientIpAddr();
			
			/*
			 * 2) 히스토리 데이터
			 */
			//소요시간
			String ifDur = DateUtil.getDurationMillis(IF_ST_DT, IF_END_DT, DateUtil.TIMESTAMP_FORMAT);
			
			/*
			 * 3) 히스토리 생성
			 */
			HistoryVo pLogVo = new HistoryVo();
			pLogVo.setIfGbn(IF_GBN);				//인터페이스구분코드
			pLogVo.setIfCd(IF_CD);					//인터페이스코드
			pLogVo.setReqPayLoad(IF_REQ_PAYLOAD);	//요청 파라미터
			pLogVo.setResPayLoad(IF_RES_PAYLOAD);	//응답 데이터
			pLogVo.setResultCd(IF_RSLT_CD);			//결과 코드
			pLogVo.setResultMsg(IF_RSLT_MSG);		//결과 메세지
			pLogVo.setIfStartDt(IF_ST_DT);			//인터페이스 시작일시
			pLogVo.setIfEndDt(IF_END_DT);			//인터페이스 종료일시
			pLogVo.setDuration(ifDur);				//인터페이스 소요시간
			pLogVo.setIfType("API");				//인터페이스 유형
//			pLogVo.setIfUrl(connUrl);				//인터페이스 호출 URL
			pLogVo.setIfDirection("O");				//인터페이스 방향 (O:아웃바운드)
			pLogVo.setSysGbn(IF_SYS_GBN);			//시스템구분
			pLogVo.setCallUserId(workId);			//호출아이디
			pLogVo.setCallUserNm(workNm);			//호출자명
			pLogVo.setCallIp(clientIp);				//호출IP
			
			historyCommonService.insertTpcIfLog(pLogVo);
		}catch(Exception e) {
			logger.error("ECS I/F History Insert Error ::::: "+e.getMessage());
		}
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
