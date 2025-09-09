package com.lottemart.epc.edi.comm.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lcnjf.util.DateUtil;
import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.edi.comm.model.HistoryVo;
import com.lottemart.epc.edi.comm.service.EpcRestApiService;
import com.lottemart.epc.edi.comm.service.HistoryCommonService;

/**
 * 
 * @Class Name : EpcRestApiController.java
 * @Description : EPC REST API 공통 
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2025.06.05		yun				최초생성
 *               </pre>
 */
@Controller
@RequestMapping("/restapi")
public class EpcRestApiController {
	
	private static final Logger logger = LoggerFactory.getLogger(EpcRestApiController.class);
	private static final String OK_CODE = "S";
	private static final String NO_CODE = "F";
	private static final String IF_GBN = "EPCIF";
	
	@Autowired
	EpcRestApiService epcRestApiService;
	
	@Autowired
	HistoryCommonService historyCommonService;
	
	/**
	 * [RESTAPI > BOS] 신상품 승인상태 변경
	 * @param paramMap
	 * @param request
	 * @return ResponseEntity<?>
	 * @throws Exception
	 */
	@RequestMapping(value= {"/newprod/cfrmsts.do"}, method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> updateTpcNewProdStsCfrm(@RequestBody Map<String,Object> paramMap, HttpServletRequest request) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("msgCd", NO_CODE);
		result.put("message", "처리 중 오류가 발생했습니다.");
		
		logger.info("================== Start EPC RESTAPI updateTpcNewProdStsCfrm ===============================");
		//parameter
		JSONObject pjo = new JSONObject(paramMap);
		String param = pjo.toString();
		
		String ifStDt, ifEndDt, ifDur;	//인터페이스 시작,종료일시, 소요시간
		//배치 시작시간
		ifStDt = DateUtil.getCurrentTime(DateUtil.TIMESTAMP_FORMAT);
		try {
			//신상품등록 승인상태변경
			result = epcRestApiService.updateTpcNewProdStsCfrm(paramMap, request);
		}catch(Exception e) {
			logger.error(e.getMessage());
		}finally {
			//인터페이스 종료일시
			ifEndDt = DateUtil.getCurrentTime(DateUtil.TIMESTAMP_FORMAT);
			//소요시간
			ifDur = DateUtil.getDurationMillis(ifStDt, ifEndDt, DateUtil.TIMESTAMP_FORMAT);
			
			// 실행로그생성  ---------------------------------------------------*/
			try {
				String resultCd = MapUtils.getString(result, "msgCd", "");
				String resultMsg = MapUtils.getString(result, "message", "");
				
				HistoryVo logVo = this.getDefEpcRestApiHistory(request);
				logVo.setIfCd("001");				//인터페이스코드
				logVo.setReqPayLoad(param);			//요청 파라미터
				logVo.setResultCd(resultCd);		//결과 코드
				logVo.setResultMsg(resultMsg);		//결과 메세지
				logVo.setIfStartDt(ifStDt);			//인터페이스 시작일시
				logVo.setIfEndDt(ifEndDt);			//인터페이스 종료일시
				logVo.setDuration(ifDur);			//인터페이스 소요시간
				
				//이력등록
				historyCommonService.insertTpcIfLog(logVo);
			}catch(Exception e) {
				logger.error("EPC I/F History Insert Error ::::: "+e.getMessage());
			}
			/*-------------------------------------------------------------*/
		}
		
		logger.info("================== End EPC RESTAPI updateTpcNewProdStsCfrm ===============================");
		
		return ResponseEntity.ok(result);
	}
	
	/**
	 * [RESTAPI > 협력사로그인] 유통SCM > 로그인 키 발번
	 * @param paramMap
	 * @param request
	 * @return ResponseEntity<?>
	 * @throws Exception
	 */
	@RequestMapping(value="/common/login/makeVenKey.do", method=RequestMethod.POST)
	public ResponseEntity<?> updateMakeVendorLoginKey(@RequestBody Map<String,Object> paramMap, HttpServletRequest request) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("msgCd", NO_CODE);
		result.put("message", "처리 중 오류가 발생했습니다.");
		
		logger.info("================== Start EPC RESTAPI updateMakeVendorLoginKey ===============================");
		//parameter
		JSONObject pjo = new JSONObject(paramMap);
		String param = pjo.toString();
		
		String ifStDt, ifEndDt, ifDur;	//인터페이스 시작,종료일시, 소요시간
		//배치 시작시간
		ifStDt = DateUtil.getCurrentTime(DateUtil.TIMESTAMP_FORMAT);
		try {
			result = epcRestApiService.updateMakeVendorLoginKey(paramMap, request);
		}catch(Exception e) {
			logger.error(e.getMessage());
		}finally {
			//인터페이스 종료일시
			ifEndDt = DateUtil.getCurrentTime(DateUtil.TIMESTAMP_FORMAT);
			//소요시간
			ifDur = DateUtil.getDurationMillis(ifStDt, ifEndDt, DateUtil.TIMESTAMP_FORMAT);
			
			// 실행로그생성  ---------------------------------------------------*/
			try {
				String resultCd = MapUtils.getString(result, "msgCd", "");
				String resultMsg = MapUtils.getString(result, "message", "");
				
				HistoryVo logVo = this.getDefEpcRestApiHistory(request);
				logVo.setIfCd("002");				//인터페이스코드
				logVo.setReqPayLoad(param);			//요청 파라미터
				logVo.setResultCd(resultCd);		//결과 코드
				logVo.setResultMsg(resultMsg);		//결과 메세지
				logVo.setIfStartDt(ifStDt);			//인터페이스 시작일시
				logVo.setIfEndDt(ifEndDt);			//인터페이스 종료일시
				logVo.setDuration(ifDur);			//인터페이스 소요시간
				
				//이력등록
				historyCommonService.insertTpcIfLog(logVo);
			}catch(Exception e) {
				logger.error("EPC I/F History Insert Error ::::: "+e.getMessage());
			}
			/*-------------------------------------------------------------*/
		}
		logger.info("================== End EPC RESTAPI updateMakeVendorLoginKey ===============================");
		return ResponseEntity.ok(result);
	}
	
	/**
	 * [RESTAPI > 공통] HistoryVo 공통 생성
	 * @param request
	 * @return HistoryVo
	 */
	private HistoryVo getDefEpcRestApiHistory(HttpServletRequest request) {
		String apiSessionKey = ConfigUtils.getString("epc.api.caller.session.key");	//api 사용자정보
		String clientIp = historyCommonService.getClientIpAddr(request);	//클라이언트 IP
		
		Map<String, String> apiUserInfo = null;
		try {
			apiUserInfo = (Map<String, String>) request.getAttribute(apiSessionKey);
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		
		String workId = MapUtils.getString(apiUserInfo, "userId", "");
		String workNm = MapUtils.getString(apiUserInfo, "userNm", workId);
		
		HistoryVo logVo = new HistoryVo();
		logVo.setIfGbn(IF_GBN);				//인터페이스구분코드-EPC API
		logVo.setIfType("API");				//인터페이스 유형 (API)
		logVo.setIfDirection("I");			//인터페이스 방향 (I:인바운드)
		logVo.setSysGbn("EPC");				//시스템구분 - EPC
		logVo.setCallIp(clientIp);			//호출 IP
		logVo.setCallUserId(workId);		//호출아이디
		logVo.setCallUserNm(workNm);		//호출자명
		
		return logVo;
	}
}
