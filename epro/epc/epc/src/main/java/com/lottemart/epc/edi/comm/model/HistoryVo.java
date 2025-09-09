package com.lottemart.epc.edi.comm.model;

import java.io.Serializable;

/**
 * 
 * @Class Name : HistoryVo.java
 * @Description : 공통 히스토리/로그 VO 
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2025.06.26		yun				최초생성
 *               </pre>
 */
public class HistoryVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4018055065530032976L;
	
	/** 인터페이스 로그 **/
	private String logId;		//로그아이디
	private String logDt;		//로그생성일시
	
	private String ifGbn;		//인터페이스구분
	private String ifCd;		//인터페이스코드
	private String ifNm;		//인터페이스명
	private String reqPayLoad;	//요청파라미터
	private String resPayLoad;	//응답메세지
	private String resultCd;	//결과코드
	private String resultMsg;	//결과메세지
	private String ifStartDt;	//인터페이스 시작일시
	private String ifEndDt;		//인터페이스 종료일시
	private String ifType;		//인터페이스 유형
	private String ifUrl;		//요청URL
	private String ifDirection;	//인터페이스 방향(I:인바운드, O:아웃바운드)
	private String callUserId;	//요청아이디
	private String callUserNm;	//요청자명
	private String callIp;		//요청IP
	private String sysGbn;		//시스템구분
	private String batchLogId;	//배치로그아이디
	
	/** 배치 로그 **/
	private String jobId;			//Batch Job Id
	private String batchType;		//배치유형
	private String batchStartDt;	//배치시작일시
	private String batchEndDt;		//배치종료일시
	private String batchStatus;		//배치실행상태
	private String batchMsg;		//로그메세지
	private String batchParams;		//배치실행파라미터
	private int reccnt;				//처리건수
	private String activeNm;		//실행자명
	private String activeId;		//실행아이디
	
	private String workId;			//작업자아이디
	private String workNm;			//작업자명
	
	private String duration;		//소요시간
	
	public String getLogId() {
		return logId;
	}
	public void setLogId(String logId) {
		this.logId = logId;
	}
	public String getLogDt() {
		return logDt;
	}
	public void setLogDt(String logDt) {
		this.logDt = logDt;
	}
	public String getIfGbn() {
		return ifGbn;
	}
	public void setIfGbn(String ifGbn) {
		this.ifGbn = ifGbn;
	}
	public String getIfCd() {
		return ifCd;
	}
	public void setIfCd(String ifCd) {
		this.ifCd = ifCd;
	}
	public String getIfNm() {
		return ifNm;
	}
	public void setIfNm(String ifNm) {
		this.ifNm = ifNm;
	}
	public String getReqPayLoad() {
		return reqPayLoad;
	}
	public void setReqPayLoad(String reqPayLoad) {
		this.reqPayLoad = reqPayLoad;
	}
	public String getResPayLoad() {
		return resPayLoad;
	}
	public void setResPayLoad(String resPayLoad) {
		this.resPayLoad = resPayLoad;
	}
	public String getResultCd() {
		return resultCd;
	}
	public void setResultCd(String resultCd) {
		this.resultCd = resultCd;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	public String getIfStartDt() {
		return ifStartDt;
	}
	public void setIfStartDt(String ifStartDt) {
		this.ifStartDt = ifStartDt;
	}
	public String getIfEndDt() {
		return ifEndDt;
	}
	public void setIfEndDt(String ifEndDt) {
		this.ifEndDt = ifEndDt;
	}
	public String getIfType() {
		return ifType;
	}
	public void setIfType(String ifType) {
		this.ifType = ifType;
	}
	public String getIfUrl() {
		return ifUrl;
	}
	public void setIfUrl(String ifUrl) {
		this.ifUrl = ifUrl;
	}
	public String getIfDirection() {
		return ifDirection;
	}
	public void setIfDirection(String ifDirection) {
		this.ifDirection = ifDirection;
	}
	public String getCallUserId() {
		return callUserId;
	}
	public void setCallUserId(String callUserId) {
		this.callUserId = callUserId;
	}
	public String getCallUserNm() {
		return callUserNm;
	}
	public void setCallUserNm(String callUserNm) {
		this.callUserNm = callUserNm;
	}
	public String getCallIp() {
		return callIp;
	}
	public void setCallIp(String callIp) {
		this.callIp = callIp;
	}
	public String getSysGbn() {
		return sysGbn;
	}
	public void setSysGbn(String sysGbn) {
		this.sysGbn = sysGbn;
	}
	public String getBatchLogId() {
		return batchLogId;
	}
	public void setBatchLogId(String batchLogId) {
		this.batchLogId = batchLogId;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getBatchType() {
		return batchType;
	}
	public void setBatchType(String batchType) {
		this.batchType = batchType;
	}
	public String getBatchStartDt() {
		return batchStartDt;
	}
	public void setBatchStartDt(String batchStartDt) {
		this.batchStartDt = batchStartDt;
	}
	public String getBatchEndDt() {
		return batchEndDt;
	}
	public void setBatchEndDt(String batchEndDt) {
		this.batchEndDt = batchEndDt;
	}
	public String getBatchStatus() {
		return batchStatus;
	}
	public void setBatchStatus(String batchStatus) {
		this.batchStatus = batchStatus;
	}
	public String getBatchMsg() {
		return batchMsg;
	}
	public void setBatchMsg(String batchMsg) {
		this.batchMsg = batchMsg;
	}
	public String getBatchParams() {
		return batchParams;
	}
	public void setBatchParams(String batchParams) {
		this.batchParams = batchParams;
	}
	public int getReccnt() {
		return reccnt;
	}
	public void setReccnt(int reccnt) {
		this.reccnt = reccnt;
	}
	public String getActiveNm() {
		return activeNm;
	}
	public void setActiveNm(String activeNm) {
		this.activeNm = activeNm;
	}
	public String getActiveId() {
		return activeId;
	}
	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}
	public String getWorkId() {
		return workId;
	}
	public void setWorkId(String workId) {
		this.workId = workId;
	}
	public String getWorkNm() {
		return workNm;
	}
	public void setWorkNm(String workNm) {
		this.workNm = workNm;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	
}
