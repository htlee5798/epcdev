package com.lottemart.epc.edi.comm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lcnjf.util.DateUtil;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.model.HistoryVo;
import com.lottemart.epc.edi.comm.service.BosOpenApiService;
import com.lottemart.epc.edi.comm.service.HistoryCommonService;

import lcn.module.framework.property.ConfigurationService;

/**
 * 
 * @Class Name : BosOpenApiController.java
 * @Description : EPC-BOS API 공통 
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2025.06.04		yun				최초생성
 *               </pre>
 */
@Controller
public class BosOpenApiController {
	private final static Logger logger = LoggerFactory.getLogger(BosOpenApiController.class);
	private static final String OK_CODE = "S";
	private static final String NO_CODE = "F";
	
	@Autowired
	BosOpenApiService bosOpenApiService;
	
	@Autowired
	HistoryCommonService historyCommonService;
	
	@Autowired
	private ConfigurationService config;
	
	/**
	 * [INNER] BOS 기준정보 수신 INNER
	 * @param logVo
	 * @param param
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	private Map<String,Object> mergeBaseInfo(HistoryVo logVo, Map<String,Object> param, HttpServletRequest req) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("msgCd", "E99");
		result.put("message", "Undefined system error");
		
		int reccnt = 0;			//배치처리건수
		String ifCd = MapUtils.getString(param, "IF_CD", "");	//API 인터페이스 코드
		String batchLogId = logVo.getLogId();					//배치로그아이디
		
		//인터페이스 코드 없음
		if("".equals(ifCd)) {return result;}
		
		/* 1. 조회 파라미터 ------------------------------------------*/
		Map<String,Object> paramMap = new HashMap<String,Object>();
		String modDate = MapUtils.getString(param, "modDate", "");	//수정일시
		//수정일시 안넘어오면 오늘날짜로 조회
		if("".equals(modDate)) modDate = DateUtil.getCurrentDay("yyyyMMdd");
		paramMap.put("modDate", modDate);	//수정일시
		
		String ifStDt, ifEndDt, ifDur;	//인터페이스 시작,종료일시, 소요시간
		
		/* 2. data merge -----------------------------------------*/
		//배치 시작시간
		ifStDt = DateUtil.getCurrentTime(DateUtil.TIMESTAMP_FORMAT);
		try {
			String tmpRsltCd = "";		//임시테이블 저장결과
			String tmpRsltMsg = "";		//임시테이블 저장결과메세지
			String migRsltCd = "";		//운영테이블 이관결과
			String migRsltMsg = "";		//운영테이블 이관결과메세지
			
			//2-1. 기준정보 변경분 임시테이블 저장
			logger.info("-------[START BOS BASE INFO API TEMP INSERT ]----------------");
			HistoryVo histVo = new HistoryVo();
			histVo.setBatchLogId(batchLogId);
			Map<String,Object> tmpMap = bosOpenApiService.insertBaseInfoTempData(ifCd, paramMap, batchLogId);
			tmpRsltCd = StringUtils.defaultString(MapUtils.getString(tmpMap, "msgCd"));		//임시테이블 저장결과
			tmpRsltMsg = StringUtils.defaultString(MapUtils.getString(tmpMap, "message"));	//임시테이블 저장결과메세지
			
			result.put("msgCd", tmpRsltCd);
			result.put("message", tmpRsltMsg);
			logger.info("-------[END BOS BASE INFO API TEMP INSERT]----------------");
			
			//2-2. 임시테이블 저장 성공 이후, 운영 테이블 merge
			if("S".equals(tmpRsltCd)) {
				logger.info("-------[START BOS BASE INFO TEMP TO REAL MERGE ]----------------");
				Map<String,Object> migMap = bosOpenApiService.updateMigBaseInfoTmpToRealData(ifCd, paramMap);
				migRsltCd = StringUtils.defaultString(MapUtils.getString(migMap, "msgCd"));		//저장결과코드
				migRsltMsg = StringUtils.defaultString(MapUtils.getString(migMap, "message"));	//저장결과메세지
				reccnt = MapUtils.getIntValue(migMap, "reccnt", 0);		//처리건수
				
				result.put("msgCd", migRsltCd);
				result.put("message", migRsltMsg);
				logger.info("-------[END BOS BASE INFO TEMP TO REAL MERGE ]----------------");
			}
		}catch(Exception e) {
			logger.error("I/F BOS mergeBaseInfo("+ifCd+") IF BASE INFO UPDATE EXCEPTION " + e.getMessage());
			result.put("msgCd", "E98");
			result.put("message", "I/F BOS mergeBaseInfo("+ifCd+") IF BASE INFO UPDATE EXCEPTION " + e.getMessage());
			return result;
		}finally {
			//인터페이스 종료일시
			ifEndDt = DateUtil.getCurrentTime(DateUtil.TIMESTAMP_FORMAT);
			//소요시간
			ifDur = DateUtil.getDurationMillis(ifStDt, ifEndDt, DateUtil.TIMESTAMP_FORMAT);
			
			// 3. 실행로그생성  ---------------------------------------------------*/
			//배치로그 정보 추가 셋팅
			String batchStatus = "S".equals(MapUtils.getString(result, "msgCd", ""))? OK_CODE:NO_CODE;
			String batchMsg = MapUtils.getString(result, "message");
			
			logVo.setBatchStartDt(ifStDt);		//배치 시작일시
			logVo.setBatchEndDt(ifEndDt);		//배치 종료일시
			logVo.setDuration(ifDur);			//배치 소요시간
			logVo.setBatchStatus(batchStatus);	//배치 상태
			logVo.setBatchMsg(batchMsg);		//배치 로그메세지
			logVo.setReccnt(reccnt);			//배치 처리건수
			
			//History Insert
			historyCommonService.insertTpcBatchJobLog(logVo);
			/*------------------------------------------------------------------*/
		}
		return result;
	}
	
	/**
	 * [MANUAL] BOS 기준정보 수신
	 * @param param
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/openApi/mergeBaseInfo.json", method = RequestMethod.POST,	headers="Accept=application/json")
	public @ResponseBody Map<String,Object> mergeBaseInfoManual(@RequestBody Map<String,Object> param, HttpServletRequest req, HttpServletResponse res) throws Exception {
		logger.info("================== IF Start Manual - BOS mergeBaseInfoManual ====================");
		/*
		 * 1. 배치 실행을 위한 기본 정보 셋팅
		 */
		if(param == null || param.isEmpty()) param = new HashMap<String,Object>();		
		
		EpcLoginVO epcLoginVO = null;
		String sessionKey = config.getString("lottemart.epc.session.key");
		epcLoginVO = (EpcLoginVO) req.getSession().getAttribute(sessionKey);
		
		//작업자정보
		String workId = epcLoginVO.getLoginWorkId();
		String workNm = ("".equals(StringUtils.defaultString(epcLoginVO.getAdminNm())))? epcLoginVO.getLoginNm():epcLoginVO.getAdminNm();
		
		String logId = historyCommonService.selectNewBatchJobLogId();	//배치 로그아이디 생성
		String ifCd = MapUtils.getString(param, "IF_CD", "");	//인터페이스코드
		String jobId = "MERGE_BOS_BASE_INFO_"+ifCd;				//배치 job id
		
		//로그용 실행 파라미터 정보
		JSONObject paramJo = new JSONObject(param);
		String paramStr = paramJo.toString();
		
		//배치 기본정보 셋팅 ==========================================
		HistoryVo logVo = new HistoryVo();
		logVo.setLogId(logId);			//배치로그아이디
		logVo.setJobId(jobId);			//배치 JOB ID
		logVo.setBatchType("M");		//배치유형 - M : 수동배치
		logVo.setWorkId(workId);		//작업자아이디
		logVo.setWorkNm(workNm);		//작업자명
		logVo.setBatchParams(paramStr);	//배치실행파라미터
		//=======================================================
		
		/*
		 * 2. 배치 실행
		 */
		Map<String,Object> result = this.mergeBaseInfo(logVo, param, req);
		
		//배치실행결과
		String msgCd = MapUtils.getString(result, "msgCd", "E");
		String message = MapUtils.getString(result, "message", "");
		logger.info("Manual IF UserInfo Update  : " +msgCd + "("+message+")" );
		logger.info("================== IF End Manual - BOS mergeBaseInfoManual ====================");
		return result;
	}

	/**
	 * [BATCH_INNER] BOS 기준정보 수신_배치용공통 (내용은 동일하나, IF_CD 가 분기되므로... cron 소스 짧게하기 위해사용)
	 * @param ifCd
	 * @throws Exception
	 */
	private void mergeBaseInfoBatchInner(String ifCd) throws Exception {
		logger.info("================== IF Start Batch - BOS mergeBaseInfoBatch ====================");
		/*
		 * 1. 배치 실행을 위한 기본 정보 셋팅
		 */
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("IF_CD", ifCd);
		
		String logId = historyCommonService.selectNewBatchJobLogId();	//배치 로그아이디 생성
		String jobId = "MERGE_BOS_BASE_INFO_"+ifCd;				//배치 job id
		
		//로그용 실행 파라미터 정보
		JSONObject paramJo = new JSONObject(param);
		String paramStr = paramJo.toString();
		
		//배치 기본정보 셋팅 ==========================================
		HistoryVo logVo = new HistoryVo();
		logVo.setLogId(logId);			//배치로그아이디
		logVo.setJobId(jobId);			//배치 JOB ID
		logVo.setBatchType("B");		//배치유형 - B : 자동배치
		logVo.setWorkId("BATCH");		//작업자아이디
		logVo.setWorkNm("BATCH");		//작업자명
		logVo.setBatchParams(paramStr);	//배치실행파라미터
		
		logVo.setIfCd(ifCd);			//인터페이스코드
		//=======================================================
		
		/*
		 * 2. 배치 실행
		 */
		Map<String,Object> result = this.mergeBaseInfo(logVo, param, null);
		
		//배치실행결과
		String msgCd = MapUtils.getString(result, "msgCd", "E");
		String message = MapUtils.getString(result, "message", "");
		logger.info("Manual IF UserInfo Update  : " +msgCd + "("+message+")" );
		logger.info("================== IF End Batch - BOS mergeBaseInfoBatch ====================");
	}
	
	/**
	 * [BATCH] BOS 기준정보 수신_001.전상법 템플릿 마스터 
	 */
//	@Scheduled(cron = "") //일배치 : 
	public void mergeBaseInfoBatch_001() throws Exception {
    	if(!"Y".equals(config.getString("batch.process.execute"))) return;
    	this.mergeBaseInfoBatchInner("001");
    }
	
	/**
	 * [BATCH] BOS 기준정보 수신_002.전상법 템플릿 상세 
	 */
//	@Scheduled(cron = "") //일배치 : 
	public void mergeBaseInfoBatch_002() throws Exception {
		if(!"Y".equals(config.getString("batch.process.execute"))) return;
		this.mergeBaseInfoBatchInner("002");
	}
	
	/**
	 * [BATCH] BOS 기준정보 수신_003.KC인증 품목 마스터 수신
	 */
//	@Scheduled(cron = "") //일배치 : 
	public void mergeBaseInfoBatch_003() throws Exception {
		if(!"Y".equals(config.getString("batch.process.execute"))) return;
		this.mergeBaseInfoBatchInner("003");
	}
	
	/**
	 * [BATCH] BOS 기준정보 수신_004.KC인증 품목 상세 수신
	 */
//	@Scheduled(cron = "") //일배치 : 
	public void mergeBaseInfoBatch_004() throws Exception {
		if(!"Y".equals(config.getString("batch.process.execute"))) return;
		this.mergeBaseInfoBatchInner("004");
	}
	
	/**
	 * [BATCH] BOS 기준정보 수신_005.EC 표준 카테고리 조회
	 */
//	@Scheduled(cron = "") //일배치 : 
	public void mergeBaseInfoBatch_005() throws Exception {
		if(!"Y".equals(config.getString("batch.process.execute"))) return;
		this.mergeBaseInfoBatchInner("005");
	}
	/**
	 * [BATCH] BOS 기준정보 수신_006.EC 전시 카테고리 조회
	 */
//	@Scheduled(cron = "") //일배치 : 
	public void mergeBaseInfoBatch_006() throws Exception {
		if(!"Y".equals(config.getString("batch.process.execute"))) return;
		this.mergeBaseInfoBatchInner("006");
	}
	
	/**
	 * [BATCH] BOS 기준정보 수신_007.EC 표준/전시 카테고리 매핑정보 조회
	 */
//	@Scheduled(cron = "") //일배치 : 
	public void mergeBaseInfoBatch_007() throws Exception {
		if(!"Y".equals(config.getString("batch.process.execute"))) return;
		this.mergeBaseInfoBatchInner("007");
	}
	
	/**
	 * [BATCH] BOS 기준정보 수신_008.EC 표준/마트 카테고리 매핑정보 조회
	 */
//	@Scheduled(cron = "") //일배치 : 
	public void mergeBaseInfoBatch_008() throws Exception {
		if(!"Y".equals(config.getString("batch.process.execute"))) return;
		this.mergeBaseInfoBatchInner("008");
	}
	
	/**
	 * [BATCH] BOS 기준정보 수신_009.EC 표준카테고리 / EC 상품속성 매핑정보 조회
	 */
//	@Scheduled(cron = "") //일배치 : 
	public void mergeBaseInfoBatch_009() throws Exception {
		if(!"Y".equals(config.getString("batch.process.execute"))) return;
		this.mergeBaseInfoBatchInner("009");
	}
	
	/**
	 * [BATCH] BOS 기준정보 수신_010.EC 상품 속성코드(마스터) 조회
	 */
//	@Scheduled(cron = "") //일배치 : 
	public void mergeBaseInfoBatch_010() throws Exception {
		if(!"Y".equals(config.getString("batch.process.execute"))) return;
		this.mergeBaseInfoBatchInner("010");
	}
	
	/**
	 * [BATCH] BOS 기준정보 수신_011.EC 상품 속성값(아이템) 조회
	 */
//	@Scheduled(cron = "") //일배치 : 
	public void mergeBaseInfoBatch_011() throws Exception {
		if(!"Y".equals(config.getString("batch.process.execute"))) return;
		this.mergeBaseInfoBatchInner("011");
	}
	
	/**
	 * [BATCH] BOS 기준정보 수신_012.온라인 공통코드 조회
	 */
//	@Scheduled(cron = "") //일배치 : 
	public void mergeBaseInfoBatch_012() throws Exception {
		if(!"Y".equals(config.getString("batch.process.execute"))) return;
		this.mergeBaseInfoBatchInner("012");
	}
	
	/**
	 * [BATCH] BOS 기준정보 수신_013.온라인 전시 카테고리 조회
	 * - 250730_TEMP 미이용 / 운영테이블 ALL DELETE > INSERT 처리 
	 */
//	@Scheduled(cron = "") //일배치 : 
	public void mergeBaseInfoBatch_013() throws Exception {
		if(!"Y".equals(config.getString("batch.process.execute"))) return;
		//this.mergeBaseInfoBatchInner("013");
		this.insertRealBaseInfoAllBatchInner("013");
	}
	
	/**
	 * [BATCH] BOS 기준정보 수신_014.마트 기간계 카테고리 / 온라인 전시 카테고리 매핑정보 조회
	 * - 250730_TEMP 미이용 / 운영테이블 ALL DELETE > INSERT 처리
	 */
//	@Scheduled(cron = "") //일배치 : 
	public void mergeBaseInfoBatch_014() throws Exception {
		if(!"Y".equals(config.getString("batch.process.execute"))) return;
		//this.mergeBaseInfoBatchInner("014");
		this.insertRealBaseInfoAllBatchInner("014");
	}
	
	/**
	 * [BATCH] BOS 기준정보 수신_015. 팀 카테고리 매핑정보 조회
	 * - 250730_TEMP 미이용 / 운영테이블 ALL DELETE > INSERT 처리
	 */
//	@Scheduled(cron = "") //일배치 : 
	public void mergeBaseInfoBatch_015() throws Exception {
		if(!"Y".equals(config.getString("batch.process.execute"))) return;
		//this.mergeBaseInfoBatchInner("015");
		this.insertRealBaseInfoAllBatchInner("015");
	}
	
	/**
	 * [BATCH] BOS 기준정보 수신_016.신상품 - 오카도(롯데마트제타) 카테고리 입력 데이터 조회
	 */
//	@Scheduled(cron = "") //일배치 : 
	public void mergeBaseInfoBatch_016() throws Exception {
		if(!"Y".equals(config.getString("batch.process.execute"))) return;
		this.mergeBaseInfoBatchInner("016");
	}
	
	/**
	 * [BATCH] BOS 기준정보 수신_017.전상법 템플릿 상세 
	 * - 250730_TEMP 미이용 / 운영테이블 ALL DELETE > INSERT 처리
	 */
//	@Scheduled(cron = "") //일배치 : 
	public void mergeBaseInfoBatch_017() throws Exception {
		if(!"Y".equals(config.getString("batch.process.execute"))) return;
		//this.mergeBaseInfoBatchInner("017");
		this.insertRealBaseInfoAllBatchInner("017");
	}
	
	/**
	 * [BATCH] BOS 기준정보 수신_018.오카도 전시 카테고리 / 마트 소분류 코드 매핑정보 조회
	 * - 250730_TEMP 미이용 / 운영테이블 ALL DELETE > INSERT 처리
	 */
//	@Scheduled(cron = "") //일배치 : 
	public void mergeBaseInfoBatch_018() throws Exception {
		if(!"Y".equals(config.getString("batch.process.execute"))) return;
		//this.mergeBaseInfoBatchInner("018");
		this.insertRealBaseInfoAllBatchInner("018");
	}
	
	/**
	 * [BATCH] BOS 기준정보 수신_019.브랜드 정보 조회
	 */
//	@Scheduled(cron = "") //일배치 : 
	public void mergeBaseInfoBatch_019() throws Exception {
		if(!"Y".equals(config.getString("batch.process.execute"))) return;
		this.mergeBaseInfoBatchInner("019");
	}
	
	/**
	 * [BATCH] BOS 기준정보 수신_020.EC 패션 속성값 페이지 전시 유무 전송 조회
	 */
//	@Scheduled(cron = "") //일배치 : 
	public void mergeBaseInfoBatch_020() throws Exception {
		if(!"Y".equals(config.getString("batch.process.execute"))) return;
		this.mergeBaseInfoBatchInner("020");
	}
	
	/**
	 * [BATCH] BOS 기준정보 수신_021.EDI 공통코드 조회
	 */
//	@Scheduled(cron = "") //일배치 : 
	public void mergeBaseInfoBatch_021() throws Exception {
		if(!"Y".equals(config.getString("batch.process.execute"))) return;
		this.mergeBaseInfoBatchInner("021");
	}
	
	/**
	 * [BATCH] BOS 기준정보 수신_022.영양성분 마스터 조회
	 */
//	@Scheduled(cron = "") //일배치 : 
	public void mergeBaseInfoBatch_022() throws Exception {
		if(!"Y".equals(config.getString("batch.process.execute"))) return;
		this.mergeBaseInfoBatchInner("022");
	}
	
	/**
	 * [BATCH] BOS 기준정보 수신_023.영양성분 소분류 매핑 정보 조회
	 */
//	@Scheduled(cron = "") //일배치 : 
	public void mergeBaseInfoBatch_023() throws Exception {
		if(!"Y".equals(config.getString("batch.process.execute"))) return;
		this.mergeBaseInfoBatchInner("023");
	}
	
	/**
	 * [INNER] BOS 기준정보 전체 수신 (TMP 테이블 미이용 / REAL DELETE > INSERT)
	 * @param logVo
	 * @param param
	 * @param req
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	private Map<String,Object> insertRealBaseInfoAll(HistoryVo logVo, Map<String,Object> param, HttpServletRequest req) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("msgCd", "E99");
		result.put("message", "Undefined system error");
		
		int reccnt = 0;			//배치처리건수
		String ifCd = MapUtils.getString(param, "IF_CD", "");	//API 인터페이스 코드
		String batchLogId = logVo.getLogId();					//배치로그아이디
		
		//인터페이스 코드 없음
		if("".equals(ifCd)) {return result;}
		
		/* 1. 조회 파라미터 ------------------------------------------*/
		Map<String,Object> paramMap = new HashMap<String,Object>();
		String modDate = MapUtils.getString(param, "modDate", "");	//수정일시
		//수정일시 안넘어오면 오늘날짜로 조회
		if("".equals(modDate)) modDate = DateUtil.getCurrentDay("yyyyMMdd");
		paramMap.put("modDate", modDate);	//수정일시
		
		String ifStDt, ifEndDt, ifDur;	//인터페이스 시작,종료일시, 소요시간
		
		/* 2. data merge -----------------------------------------*/
		//배치 시작시간
		ifStDt = DateUtil.getCurrentTime(DateUtil.TIMESTAMP_FORMAT);
		try {
			String migRsltCd = "";		//운영테이블 이관결과
			String migRsltMsg = "";		//운영테이블 이관결과메세지
			
			//2-1. 기준정보 변경분 임시테이블 저장
			logger.info("-------[START BOS BASE INFO API REAL BULK INSERT ]----------------");
			HistoryVo histVo = new HistoryVo();
			histVo.setBatchLogId(batchLogId);
			
			Map<String,Object> migMap = bosOpenApiService.deleteInsertBaseInfoAll(ifCd, paramMap, batchLogId);
			migRsltCd = StringUtils.defaultString(MapUtils.getString(migMap, "msgCd"));		//저장결과코드
			migRsltMsg = StringUtils.defaultString(MapUtils.getString(migMap, "message"));	//저장결과메세지
			reccnt = MapUtils.getIntValue(migMap, "reccnt", 0);		//처리건수
			
			result.put("msgCd", migRsltCd);
			result.put("message", migRsltMsg);
			logger.info("-------[END BOS BASE INFO API REAL BULK INSERT]----------------");
		}catch(Exception e) {
			logger.error("I/F BOS mergeBaseInfo("+ifCd+") IF BASE INFO REAL BULK INSERT EXCEPTION " + e.getMessage());
			result.put("msgCd", "E98");
			result.put("message", "I/F BOS mergeBaseInfo("+ifCd+") IF BASE INFO REAL BULK INSERT EXCEPTION " + e.getMessage());
			return result;
		}finally {
			//인터페이스 종료일시
			ifEndDt = DateUtil.getCurrentTime(DateUtil.TIMESTAMP_FORMAT);
			//소요시간
			ifDur = DateUtil.getDurationMillis(ifStDt, ifEndDt, DateUtil.TIMESTAMP_FORMAT);
			
			// 3. 실행로그생성  ---------------------------------------------------*/
			//배치로그 정보 추가 셋팅
			String batchStatus = "S".equals(MapUtils.getString(result, "msgCd", ""))? OK_CODE:NO_CODE;
			String batchMsg = MapUtils.getString(result, "message");
			
			logVo.setBatchStartDt(ifStDt);		//배치 시작일시
			logVo.setBatchEndDt(ifEndDt);		//배치 종료일시
			logVo.setDuration(ifDur);			//배치 소요시간
			logVo.setBatchStatus(batchStatus);	//배치 상태
			logVo.setBatchMsg(batchMsg);		//배치 로그메세지
			logVo.setReccnt(reccnt);			//배치 처리건수
			
			//History Insert
			historyCommonService.insertTpcBatchJobLog(logVo);
			/*------------------------------------------------------------------*/
		}
		return result;
	}
	
	/**
	 * [MANUAL] BOS 기준정보 전체 수신 (TMP 테이블 미이용 / REAL DELETE > INSERT)
	 * @param param
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/openApi/insertRealBaseInfoAll.json", method = RequestMethod.POST,	headers="Accept=application/json")
	public @ResponseBody Map<String,Object> insertRealBaseInfoAllManual(@RequestBody Map<String,Object> param, HttpServletRequest req, HttpServletResponse res) throws Exception {
		logger.info("================== IF Start Manual - BOS insertRealBaseInfoAllManual ====================");
		/*
		 * 1. 배치 실행을 위한 기본 정보 셋팅
		 */
		if(param == null || param.isEmpty()) param = new HashMap<String,Object>();		
		
		EpcLoginVO epcLoginVO = null;
		String sessionKey = config.getString("lottemart.epc.session.key");
		epcLoginVO = (EpcLoginVO) req.getSession().getAttribute(sessionKey);
		
		//작업자정보
		String workId = epcLoginVO.getLoginWorkId();
		String workNm = ("".equals(StringUtils.defaultString(epcLoginVO.getAdminNm())))? epcLoginVO.getLoginNm():epcLoginVO.getAdminNm();
		
		String logId = historyCommonService.selectNewBatchJobLogId();	//배치 로그아이디 생성
		String ifCd = MapUtils.getString(param, "IF_CD", "");	//인터페이스코드
		String jobId = "MERGE_BOS_BASE_INFO_ALL_"+ifCd;				//배치 job id
		
		//로그용 실행 파라미터 정보
		JSONObject paramJo = new JSONObject(param);
		String paramStr = paramJo.toString();
		
		//배치 기본정보 셋팅 ==========================================
		HistoryVo logVo = new HistoryVo();
		logVo.setLogId(logId);			//배치로그아이디
		logVo.setJobId(jobId);			//배치 JOB ID
		logVo.setBatchType("M");		//배치유형 - M : 수동배치
		logVo.setWorkId(workId);		//작업자아이디
		logVo.setWorkNm(workNm);		//작업자명
		logVo.setBatchParams(paramStr);	//배치실행파라미터
		//=======================================================
		
		/*
		 * 2. 배치 실행
		 */
		Map<String,Object> result = this.insertRealBaseInfoAll(logVo, param, req);
		
		//배치실행결과
		String msgCd = MapUtils.getString(result, "msgCd", "E");
		String message = MapUtils.getString(result, "message", "");
		logger.info("Manual IF UserInfo Update  : " +msgCd + "("+message+")" );
		logger.info("================== IF End Manual - BOS insertRealBaseInfoAllManual ====================");
		return result;
	}

	/**
	 * [BATCH_INNER] BOS 기준정보 전체 수신 (TMP 테이블 미이용 / REAL DELETE > INSERT)
	 *               (MANUAL과 내용은 동일하나, IF_CD 가 분기되므로... cron 소스 짧게하기 위해사용)
	 * @param ifCd
	 * @throws Exception
	 */
	private void insertRealBaseInfoAllBatchInner(String ifCd) throws Exception {
		logger.info("================== IF Start Batch - BOS insertRealBaseInfoAllBatch ====================");
		/*
		 * 1. 배치 실행을 위한 기본 정보 셋팅
		 */
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("IF_CD", ifCd);
		
		String logId = historyCommonService.selectNewBatchJobLogId();	//배치 로그아이디 생성
		String jobId = "MERGE_BOS_BASE_INFO_ALL_"+ifCd;					//배치 job id
		
		//로그용 실행 파라미터 정보
		JSONObject paramJo = new JSONObject(param);
		String paramStr = paramJo.toString();
		
		//배치 기본정보 셋팅 ==========================================
		HistoryVo logVo = new HistoryVo();
		logVo.setLogId(logId);			//배치로그아이디
		logVo.setJobId(jobId);			//배치 JOB ID
		logVo.setBatchType("B");		//배치유형 - B : 자동배치
		logVo.setWorkId("BATCH");		//작업자아이디
		logVo.setWorkNm("BATCH");		//작업자명
		logVo.setBatchParams(paramStr);	//배치실행파라미터
		
		logVo.setIfCd(ifCd);			//인터페이스코드
		//=======================================================
		
		/*
		 * 2. 배치 실행
		 */
		Map<String,Object> result = this.insertRealBaseInfoAll(logVo, param, null);
		
		//배치실행결과
		String msgCd = MapUtils.getString(result, "msgCd", "E");
		String message = MapUtils.getString(result, "message", "");
		logger.info("Manual IF UserInfo Update  : " +msgCd + "("+message+")" );
		logger.info("================== IF End Batch - BOS insertRealBaseInfoAllBatch ====================");
	}
	
}