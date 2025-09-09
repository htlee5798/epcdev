package com.lottemart.epc.edi.comm.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lcnjf.util.DateUtil;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.service.PSCMCOM0001Service;
import com.lottemart.epc.edi.comm.model.HistoryVo;
import com.lottemart.epc.edi.comm.model.ScheduledVO;
import com.lottemart.epc.edi.comm.service.BosOpenApiService;
import com.lottemart.epc.edi.comm.service.HistoryCommonService;
import com.lottemart.epc.edi.comm.service.ScheduledService;
import com.lottemart.epc.edi.product.model.NEDMPRO0040VO;

import lcn.module.framework.property.ConfigurationService;


/**
 * @Class Name : ScheduledController.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      		  	  수정자           		수정내용
 *  -------    		---------------    ---------------------------
 * 2025. 04. 09. 	 park jong gyu				최초생성
 *
 * </pre>
 */
@Controller
public class ScheduledController extends TagSupport
{
    @SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory.getLogger(ScheduledController.class);
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
    @Autowired
    ScheduledService scheduledService;
    
    @Autowired
    HistoryCommonService historyCommonService;
    
    @Autowired
    PSCMCOM0001Service epcLoginService;
    
    @Autowired
    BosOpenApiService bosOpenApiService;
    
    /**
     * 행사 마스터 데이터 계약 진행상태(계약완료) update!
     * @throws Exception 
     */
	@Scheduled(cron = "0/10 * * * * ?") // 10초마다 실행
    public void updateProdEvnt() throws Exception {
		ScheduledVO scheduledVO = new ScheduledVO();
		if( "Y".equals( config.getString("batch.process.execute") ) ) {
			scheduledService.updateProdEvntDoc(scheduledVO);
		}
    }

	/**
	 * 행사 마스터 데이터 계약 진행상태(계약삭제 / 계약폐기) update!
	 * @throws Exception 
	 */
    @Scheduled(cron = "0 */1 * * * ?") // 1분마다 실행
	public void updateProdEvntDelDoc() throws Exception {
		ScheduledVO scheduledVO = new ScheduledVO();
		if( "Y".equals( config.getString("batch.process.execute") ) ) {
			scheduledService.updateProdEvntDelDoc(scheduledVO);
		}
	}
	
    /**
     * 원가변경요청 > 전자공문 번호 업데이트
     * @throws Exception
     */
    @Scheduled(cron = "0/10 * * * * ?") // 10초마다 실행
    public void updateProdChgCostDcNum() throws Exception {
    	ScheduledVO scheduledVO = new ScheduledVO();
    	if("Y".equals( config.getString("batch.process.execute") ) ) {
			scheduledService.updateProdChgCostDcNum(scheduledVO);
		}
    }
    
    /**
     * [BATCH] BOS 승인대상 미처리 시, ERP 자동발송
     * @throws Exception
     */
    @Scheduled(cron = "0 0 8 * * 1-5")	// 평일 오전 8시 실행
//    @Scheduled(cron = "0 */1 * * * ?")	// 1분마다 실행
    public void updateImsiProductListFixAutoSendBatch() throws Exception {
    	if(!"Y".equals(config.getString("batch.process.execute"))) return;

    	logger.info("================== Start Batch - UPDATE IMSI PRODUCT AUTO SEND ====================");
    	
    	logger.info("------[SYSTEM PARAMETER------------------------------------");
    	NEDMPRO0040VO vo = new NEDMPRO0040VO();
    	vo.setSendGbn("ATO");		//전송대상구분 - 자동발송
    	vo.setBatchYn("Y");			//배치여부
		logger.info("-----------------------------------------------------------");
		
		//자동발송
    	Map<String,Object> result = scheduledService.updateImsiProductListFixAutoSend(vo, null);
    	String msg = MapUtils.getString(result, "msg", "");	//자동발송 결과
    	
    	//전송 성공 시, ERP 자동발송 결과 BOS로 전송
    	if("SUCCESS".equals(msg)) {
    		List<String> pgmIdList = (List<String>) MapUtils.getObject(result, "pgmIdList");
    		
    		//전송 성공한 pgmId list 있을 경우, bos 일괄전송
    		if(pgmIdList != null && !pgmIdList.isEmpty()) {
    			Map<String,Object> bosSendMap = new HashMap<String,Object>();
    			bosSendMap.put("pgmIdList", pgmIdList);
    			bosOpenApiService.insertNewProdAutoSendListToBos(bosSendMap, null);
    		}
    	}
    	
    	logger.info("Batch IMSI PRODUCT AUTO SEND  : " + msg);
    	logger.info("================== End Batch - UPDATE IMSI PRODUCT AUTO SEND ====================");
    }
	
    /**
     * [MANUAL] BOS 승인대상 미처리 시, ERP 자동 발송
     * @param vo
     * @param request
     * @return Map<String,Object>
     * @throws Exception
     */
    @RequestMapping(value="/edi/product/updateImsiProductListFixAutoSend.json",	method=RequestMethod.POST,	headers="Accept=application/json")
    public @ResponseBody Map<String,Object> updateImsiProductListFixAutoSendManual(@RequestBody NEDMPRO0040VO vo, HttpServletRequest request) throws Exception {
    	logger.info("================== Start Manual - UPDATE IMSI PRODUCT AUTO SEND ====================");
    	
    	logger.info("------[SYSTEM PARAMETER------------------------------------");
    	vo.setSendGbn("ATO");		//전송대상구분 - 자동발송
    	vo.setBatchYn("N");			//배치여부
		logger.info("-----------------------------------------------------------");
		
		//자동발송
    	Map<String,Object> result = scheduledService.updateImsiProductListFixAutoSend(vo, request);
    	String msg = MapUtils.getString(result, "msg", "");	//자동발송 결과
		
    	//전송 성공 시, ERP 자동발송 결과 BOS로 전송
    	if("SUCCESS".equals(msg)) {
    		List<String> pgmIdList = (List<String>) MapUtils.getObject(result, "pgmIdList");
    		
    		//전송 성공한 pgmId list 있을 경우, bos 일괄전송
    		if(pgmIdList != null && !pgmIdList.isEmpty()) {
    			Map<String,Object> bosSendMap = new HashMap<String,Object>();
    			bosSendMap.put("pgmIdList", pgmIdList);
    			bosOpenApiService.insertNewProdAutoSendListToBos(bosSendMap, null);
    		}
    	}
    	
    	logger.info("Manual IMSI PRODUCT AUTO SEND  : " + msg);
    	logger.info("================== End Manual - UPDATE IMSI PRODUCT AUTO SEND ====================");
    	return result;
    }
    
    /**
     * [MANUAL] google cloud storage 이용해서 Parquet 파일 가져오기
     */
    @RequestMapping(value="/edi/product/updateGcsGetParquetManual.json",	method=RequestMethod.POST,	headers="Accept=application/json")
    public @ResponseBody Map<String,Object> updateGcsGetParquetManual(@RequestBody ScheduledVO scheduledVO, HttpServletRequest request) throws Exception {
    	logger.info("================== Start Manual - UPDATE GCS GET PARQUET ====================");
    	Map<String,Object> result = new HashMap<String,Object>();
    	result.put("msgCd", "F");
    	
		scheduledService.updateGcsGetParquet(scheduledVO);
    	
    	result.put("msgCd", "S");
    	
    	logger.info("================== End Manual - UPDATE GCS GET PARQUET====================");
    	return result;
    }
	
    /**
     * google cloud storage 이용해서 Parquet 파일 가져오기
     * @throws Exception 
     */
	//@Scheduled(cron = "0/10 * * * * ?") // 10초마다 실행
	//@Scheduled(cron = "0 28 16 * * ?") // 10초마다 실행
    public void updateGcsGetParquet() throws Exception {
		ScheduledVO scheduledVO = new ScheduledVO();
		if( "Y".equals( config.getString("batch.process.execute") ) ) {
			scheduledService.updateGcsGetParquet(scheduledVO);
		}
    }
	
    /**
     * 당해년도 휴일 및 공휴일 등록
     * @throws Exception
     */
//    @Scheduled(cron = "0 */1 * * * ?") // 1분마다 실행
//    @Scheduled(cron = "0 0 0 1 1 ?")	// 매년 1월 1일
    public void insertTpcHolidayBatch() throws Exception {
    	if(!"Y".equals(config.getString("batch.process.execute"))) return;

    	logger.info("================== Start Batch - INSERT HOLIDAY ====================");
    	
    	logger.info("------[SYSTEM PARAMETER------------------------------------");
    	Map<String,Object> paramMap = new HashMap<String,Object>();
    	paramMap.put("YYYY", DateUtil.getThisYear());
		logger.info("-----------------------------------------------------------");
		
    	Map<String,Object> result = scheduledService.insertTpcHoliday(paramMap);
    	
    	logger.info("Batch INSERT HOLIDAY  : " + MapUtils.getString(result, "msg"));
    	logger.info("================== End Batch - INSERT HOLIDAY ====================");
    }
    
    /**
     * [MANUAL] HQ_VEN > TVE_VENDOR MERGE
     * @param paramMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/edi/product/updateMergeHqVenToTveVendor.json",	method=RequestMethod.POST,	headers="Accept=application/json")
    public @ResponseBody Map<String,Object> updateMergeHqVenToTveVendorManual(@RequestBody Map<String,Object> paramMap) throws Exception {
    	return this.updateMergeHqVenToTveVendorInner("M", paramMap);
    }
    
    /**
     * [BATCH] HQ_VEN > TVE_VENDOR MERGE
     * @param paramMap
     * @throws Exception
     */
//    @Scheduled(cron = "0 */5 * * * ?")	// 평일 오전 8시 실행
    public void updateMergeHqVenToTveVendorBatch() throws Exception {
    	if(!"Y".equals(config.getString("batch.process.execute"))) return;
    	this.updateMergeHqVenToTveVendorInner("B", null);
    }
    
    /**
     * [INNER] HQ_VEN > TVE_VENDOR MERGE
     * @param batchType
     * @param paramMap
     * @return Map<String, Object>
     * @throws Exception
     */
    private Map<String,Object> updateMergeHqVenToTveVendorInner(String batchType, Map<String,Object> paramMap) throws Exception {
    	Map<String,Object> result = new HashMap<String,Object>();
		result.put("msgCd", "E99");
		result.put("message", "Undefined system error");
		
    	logger.info("================== Start Batch - MERGE TVE_VENDOR ====================");
    	paramMap = (paramMap == null || paramMap.isEmpty())? new HashMap<String,Object>():paramMap;
    	
    	JSONObject jo = new JSONObject(paramMap);
    	String paramStr = jo.toString();
    	
    	String startDt = "";	//배치 실행시작시간
    	String endDt = "";		//배치 실행종료시간
    	String batchDur = "";	//배치 소요시간
    	int reccnt = 0;			//배치처리건수
    	
    	String msgCd = "S";		//업무 결과코드
    	
    	//배치 시작시간
    	startDt = DateUtil.getCurrentTime(DateUtil.TIMESTAMP_FORMAT);
    	try {
    		logger.info("-------[START TVE_VENDOR MERGE ]----------------");
    		
    		//tve_vendor merge
    		reccnt = epcLoginService.updateMergeHqVenToTveVendor(paramMap);
    		logger.info("-------[END TVE_VENDOR MERGE ]----------------");
    	}catch(Exception e) {
    		msgCd = "F";
    		logger.error("I/F updateMergeHqVenToTveVendor MERGE EXCEPTION " + e.getMessage());
			result.put("msgCd", "E98");
			result.put("message", "I/F updateMergeHqVenToTveVendor MERGE EXCEPTION " + e.getMessage());
			return result;
    	}finally {
    		//배치 종료시간
    		endDt = DateUtil.getCurrentTime(DateUtil.TIMESTAMP_FORMAT);
    		//배치 소요시간
    		batchDur = DateUtil.getDurationMillis(startDt, endDt, DateUtil.TIMESTAMP_FORMAT);
    		
    		String logId = historyCommonService.selectNewBatchJobLogId();	//배치 로그아이디 생성
    		String jobId = "MERGE_TVE_VENDOR";								//배치 JOB ID
    		
    		String batchStatus = "S".equals(msgCd)? "S":"F";				//배치 실행상태
    		String workId = "", workNm = "";
    		
    		//작업자정보 setting
    		EpcLoginVO epcLoginVO = this.getWorkSessionVo();
			if(epcLoginVO != null) {
				//작업자정보
				workId = epcLoginVO.getLoginWorkId();
				workNm = ("".equals(StringUtils.defaultString(epcLoginVO.getAdminNm())))? epcLoginVO.getLoginNm():epcLoginVO.getAdminNm();
			}else {
				workId = "BATCH";
				workNm = "BATCH";
			}
    		
			// 실행로그생성  ---------------------------------------------------*/
    		HistoryVo logVo = new HistoryVo();
    		logVo.setLogId(logId);				//배치로그아이디
    		logVo.setJobId(jobId);				//배치 JOB ID
    		logVo.setBatchType(batchType);		//배치유형 - M : 수동배치 / B : 자동배치 / R : 실시간
    		logVo.setWorkId(workId);			//작업자아이디
    		logVo.setWorkNm(workNm);			//작업자명
    		logVo.setBatchParams(paramStr);		//배치실행파라미터
    		logVo.setBatchStartDt(startDt);		//배치시작시간
    		logVo.setBatchEndDt(endDt);			//배치종료시간
    		logVo.setBatchStatus(batchStatus);	//배치 실행상태
    		logVo.setReccnt(reccnt);			//처리건수
    		logVo.setDuration(batchDur);		//배치 소요시간
    		historyCommonService.insertTpcBatchJobLog(logVo);
			/*-------------------------------------------------------------*/
    	}
    	
    	result.put("msgCd", msgCd);
    	result.put("message", "정상적으로 처리되었습니다.");
    	logger.info("IF TveVendor Update  : " +msgCd );
    	logger.info("IF TveVendor Update Count  : " +reccnt );
    	logger.info("================== END Batch - MERGE TVE_VENDOR ====================");
    	return result;
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
}
