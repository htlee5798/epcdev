package com.lottemart.common.log.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * @Class Name : LogManageScheduling.java
 * @Description : 시스템 로그 요약을 위한 스케쥴링 클래스
 * @Modification Information
 *
 *    수정일       수정자         수정내용
 *    -------        -------     -------------------
 *       최초생성
 *
 * @author 
 * @since 2009. 3. 11.
 * @version
 * @see
 *
 */
@Service("LogManageScheduling")
public class LogManageScheduling {
	
	@Resource(name="LogManageService")
	private LogManageService logManageService;

	/**
	 * 시스템 로그정보를 요약한다.
	 * 전날의 로그를 요약하여 입력하고, 일주일전의 로그를 삭제한다.
	 * 
	 * @param 
	 * @return
	 * @throws Exception 
	 */
	public void sysLogSummary() throws Exception {
		logManageService.logInsertSysLogSummary();
	}

	/**
	 * 웹 로그정보를 요약한다.
	 * 전날의 로그를 요약하여 입력하고, 일주일전의 로그를 삭제한다.
	 * 
	 * @param 
	 * @return
	 * @throws Exception 
	 */
	public void webLogSummary() throws Exception {
		logManageService.logInsertWebLogSummary();
	}

	/**
	 * 송수신 로그정보를 요약한다.
	 * 전날의 로그를 요약하여 입력하고, 일주일전의 로그를 삭제한다.
	 * 
	 * @param 
	 * @return
	 * @throws Exception 
	 */
	public void trsmrcvLogSummary() throws Exception {
		logManageService.logInsertTrsmrcvLogSummary();
	}

	/**
	 * 사용자 로그정보를 생성한다.
	 * 
	 * @param 
	 * @return
	 * @throws Exception 
	 */
	public void userLogInsert() throws Exception {
		logManageService.logInsertUserLog();
	}

}
