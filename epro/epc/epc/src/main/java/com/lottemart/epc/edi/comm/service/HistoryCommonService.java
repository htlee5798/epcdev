package com.lottemart.epc.edi.comm.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.edi.comm.model.HistoryVo;

/**
 * 
 * @Class Name : HistoryCommonService.java
 * @Description : 히스토리/로그 공통 서비스 
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2025.06.25		yun				최초생성
 *               </pre>
 */
public interface HistoryCommonService {
	
	/**
	 * 인터페이스 히스토리 등록
	 * @param param
	 */
	public void insertTpcIfLog(HistoryVo param);
	
	/**
	 * BATCH JOB LOG ID생성
	 * @return String
	 */
	public String selectNewBatchJobLogId();
	
	/**
	 * 배치 히스토리 등록
	 * @param param
	 */
	public void insertTpcBatchJobLog(HistoryVo param);
	
	/**
	 * 배치 히스토리 상태 변경
	 * @param param
	 */
	public void updateTpcBatchJobLog(HistoryVo param);
	
	/**
	 * 클라이언트 IP 주소 추출
	 * @param request
	 * @return String
	 */
	public String getClientIpAddr(HttpServletRequest request);
	
	/**
	 * 클라이언트 IP 주소 추출 (NoParameter)
	 * @return String
	 */
	public String getClientIpAddr();
	
}
