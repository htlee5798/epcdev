package com.lottemart.common.log.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lcn.module.framework.base.AbstractServiceImpl;
import lcn.module.framework.idgen.IdGnrService;

import org.springframework.stereotype.Service;

import com.lottemart.common.log.dao.LogManageDAO;
import com.lottemart.common.log.model.LoginLog;
import com.lottemart.common.log.model.SysLog;
import com.lottemart.common.log.model.TrsmrcvLog;
import com.lottemart.common.log.model.UserLog;
import com.lottemart.common.log.model.WebLog;
import com.lottemart.common.log.service.LogManageService;


/**
 * @Class Name : LogManageServiceImpl.java
 * @Description : 시스템 로그 관리를 위한 서비스 구현 클래스
 * @Modification Information
 *
 *    수정일       수정자         수정내용
 *    -------        -------     -------------------
 *    
 *
 * @author 
 * @since 2009. 3. 11.
 * @version
 * @see
 *
 */
@Service("LogManageService")
public class LogManageServiceImpl extends AbstractServiceImpl implements
		LogManageService {

	@Resource(name="logManageDAO")
	private LogManageDAO logManageDAO;	
	
    /** ID Generation */    
	@Resource(name="LoginLogIdGnrService")
	private IdGnrService LoginLogIdGnrService;

	@Resource(name="SysLogIdGnrService")
	private IdGnrService SysLogIdGnrService;

	@Resource(name="WebLogIdGnrService")
	private IdGnrService WebLogIdGnrService;

	@Resource(name="TrsmrcvLogIdGnrService")
	private IdGnrService TrsmrcvLogIdGnrService;

	/**
	 * 접속로그를 기록한다.
	 * 
	 * @param LoginLog
	 */
	public void logInsertLoginLog(LoginLog loinLog) throws Exception {
		// TODO Auto-generated method stub
		String logId = LoginLogIdGnrService.getNextStringId();
		loinLog.setLogId(logId);
		
		logManageDAO.logInsertLoginLog(loinLog);    	

	}

	/**
	 * 시스템 로그정보를 생성한다.
	 * 
	 * @param SysLog
	 */
	public void logInsertSysLog(SysLog sysLog) throws Exception {
		// TODO Auto-generated method stub
		String requstId = SysLogIdGnrService.getNextStringId();
		sysLog.setRequstId(requstId);
		
		logManageDAO.logInsertSysLog(sysLog);    	
	}

	/**
	 * 시스템 로그정보를 요약한다.
	 * 
	 * @param 
	 */
	public void logInsertSysLogSummary()
			throws Exception {
		// TODO Auto-generated method stub

		logManageDAO.logInsertSysLogSummary();    	
	}

	/**
	 * 웹 로그를 기록한다.
	 * 
	 * @param WebLog
	 */
	public void logInsertWebLog(WebLog webLog) throws Exception {
		// TODO Auto-generated method stub
		String requstId = WebLogIdGnrService.getNextStringId();
		webLog.setRequstId(requstId);
		
		logManageDAO.logInsertWebLog(webLog);    	
	}

	/**
	 * 웹 로그정보를 요약한다.
	 * 
	 * @param 
	 */
	public void logInsertWebLogSummary()
			throws Exception {
		// TODO Auto-generated method stub

		logManageDAO.logInsertWebLogSummary();    	
	}

	/**
	 * 송수신로그 정보를 생성한다.
	 * 
	 * @param TrsmrcvLog
	 */
	public void logInsertTrsmrcvLog(TrsmrcvLog trsmrcvLog) throws Exception {
		// TODO Auto-generated method stub
		String requstId = TrsmrcvLogIdGnrService.getNextStringId();
		trsmrcvLog.setRequstId(requstId);
		
		logManageDAO.logInsertTrsmrcvLog(trsmrcvLog);    	
	}

	/**
	 * 송수신 로그정보를 요약한다.
	 * 
	 * @param 
	 */
	public void logInsertTrsmrcvLogSummary()
			throws Exception {
		// TODO Auto-generated method stub
		
		logManageDAO.logInsertTrsmrcvLogSummary();    	
	}
	
	/**
	 * 사용자 로그정보를 생성한다.
	 * 
	 * @param 
	 */
	public void logInsertUserLog() throws Exception {
		// TODO Auto-generated method stub

		logManageDAO.logInsertUserLog();    	
	}

	/**
	 * 접속로그를 조회한다.
	 * 
	 * @param loginLog
	 * @return loginLog
	 * @throws Exception 
	 */
	public LoginLog selectLoginLog(LoginLog loginLog) throws Exception{
		
		return logManageDAO.selectLoginLog(loginLog);
	}	

	/**
	 * 접속로그 목록을 조회한다.
	 * 
	 * @param LoginLog
	 */
	public Map selectLoginLogInf(LoginLog loinLog) throws Exception {
		// TODO Auto-generated method stub
		List _result = logManageDAO.selectLoginLogInf(loinLog);
		int _cnt = logManageDAO.selectLoginLogInfCnt(loinLog);
		 
		Map<String, Object> _map = new HashMap();
		_map.put("resultList", _result);
		_map.put("resultCnt", Integer.toString(_cnt));
		 
		return _map;
	}

	/**
	 * 시스템 로그정보를 조회한다.
	 * 
	 * @param sysLog
	 * @return sysLog
	 * @throws Exception 
	 */
	public SysLog selectSysLog(SysLog sysLog) throws Exception{
		
		return logManageDAO.selectSysLog(sysLog);
	}	

	/**
	 * 시스템 로그정보 목록을 조회한다.
	 * 
	 * @param SysLog
	 */
	public Map selectSysLogInf(SysLog sysLog) throws Exception {
		// TODO Auto-generated method stub
		
		List _result = logManageDAO.selectSysLogInf(sysLog);
		int _cnt = logManageDAO.selectSysLogInfCnt(sysLog);
		 
		Map<String, Object> _map = new HashMap();
		_map.put("resultList", _result);
		_map.put("resultCnt", Integer.toString(_cnt));
		 
		return _map;
	}

	/**
	 * 웹 로그정보를 조회한다.
	 * 
	 * @param webLog
	 * @return webLog
	 * @throws Exception 
	 */
	public WebLog selectWebLog(WebLog webLog) throws Exception{
		
		return logManageDAO.selectWebLog(webLog);
	}	

	/**
	 * 웹 로그정보 목록을 조회한다.
	 * 
	 * @param WebLog
	 */
	public Map selectWebLogInf(WebLog webLog) throws Exception {
		// TODO Auto-generated method stub

		List _result = logManageDAO.selectWebLogInf(webLog);
		int _cnt = logManageDAO.selectWebLogInfCnt(webLog);
		 
		Map<String, Object> _map = new HashMap();
		_map.put("resultList", _result);
		_map.put("resultCnt", Integer.toString(_cnt));
		 
		return _map;
	}

	/**
	 * 송수신 로그정보를 조회한다.
	 * 
	 * @param trsmrcvLog
	 * @return trsmrcvLog
	 * @throws Exception 
	 */
	public TrsmrcvLog selectTrsmrcvLog(TrsmrcvLog trsmrcvLog) throws Exception{
		
		return logManageDAO.selectTrsmrcvLog(trsmrcvLog);
	}	

	/**
	 * 송수신 로그정보 목록을 조회한다.
	 * 
	 * @param TrsmrcvLog
	 */
	public Map selectTrsmrcvLogInf(TrsmrcvLog trsmrcvLog) throws Exception {
		// TODO Auto-generated method stub
		List _result = logManageDAO.selectTrsmrcvLogInf(trsmrcvLog);
		int _cnt = logManageDAO.selectTrsmrcvLogInfCnt(trsmrcvLog);
		 
		Map<String, Object> _map = new HashMap();
		_map.put("resultList", _result);
		_map.put("resultCnt", Integer.toString(_cnt));
		 
		return _map;
	}

	/**
	 * 사용자 로그정보를 조회한다.
	 * 
	 * @param userLog
	 * @return userLog
	 * @throws Exception 
	 */
	public UserLog selectUserLog(UserLog userLog) throws Exception{
		
		return logManageDAO.selectUserLog(userLog);
	}	

	/**
	 * 사용자 로그정보 목록을 조회한다.
	 * 
	 * @param UserLog
	 */
	public Map selectUserLogInf(UserLog userLog) throws Exception {
		// TODO Auto-generated method stub

		List _result = logManageDAO.selectUserLogInf(userLog);
		int _cnt = logManageDAO.selectUserLogInfCnt(userLog);
		 
		Map<String, Object> _map = new HashMap();
		_map.put("resultList", _result);
		_map.put("resultCnt", Integer.toString(_cnt));
		 
		return _map;
	}


}
