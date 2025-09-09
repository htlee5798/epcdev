package com.lottemart.common.log.dao;

import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.log.model.LoginLog;
import com.lottemart.common.log.model.SysLog;
import com.lottemart.common.log.model.TrsmrcvLog;
import com.lottemart.common.log.model.UserLog;
import com.lottemart.common.log.model.WebLog;


/**
 * @Class Name : LogManageDAO.java
 * @Description : 시스템 로그 관리를 위한 데이터 접근 클래스
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
@Repository("logManageDAO")
public class LogManageDAO extends AbstractDAO {

	/**
	 * 접속로그를 기록한다.
	 * 
	 * @param LoginLog
	 * @return
	 * @throws Exception 
	 */
	public void logInsertLoginLog(LoginLog loginLog) throws Exception{
		insert("LogManageDAO.logInsertLoginLog", loginLog);
	}

	/**
	 * 시스템 로그정보를 생성한다.
	 * 
	 * @param SysLog
	 * @return
	 * @throws Exception 
	 */
	public void logInsertSysLog(SysLog sysLog) throws Exception{
		insert("LogManageDAO.logInsertSysLog", sysLog);
	}

	/**
	 * 시스템 로그정보를 요약한다.
	 * 
	 * @param 
	 * @return
	 * @throws Exception 
	 */
	public void logInsertSysLogSummary() throws Exception{
		insert("LogManageDAO.logInsertSysLogSummary", null);
		delete("LogManageDAO.logDeleteSysLogSummary", null);
	}

	/**
	 * 웹 로그를 기록한다.
	 * 
	 * @param WebLog
	 * @return
	 * @throws Exception 
	 */
	public void logInsertWebLog(WebLog webLog) throws Exception{
		insert("LogManageDAO.logInsertWebLog", webLog);
	}

	/**
	 * 웹 로그정보를 요약한다.
	 * 
	 * @param 
	 * @return
	 * @throws Exception 
	 */
	public void logInsertWebLogSummary() throws Exception{
		insert("LogManageDAO.logInsertWebLogSummary", null);
		delete("LogManageDAO.logDeleteWebLogSummary", null);
	}

	/**
	 * 송수신로그를 기록한다.
	 * 
	 * @param TrsmrcvLog
	 * @return
	 * @throws Exception 
	 */
	public void logInsertTrsmrcvLog(TrsmrcvLog trsmrcvLog) throws Exception{
		insert("LogManageDAO.logInsertTrsmrcvLog", trsmrcvLog);
	}

	/**
	 * 송수신 로그정보를 요약한다.
	 * 
	 * @param 
	 * @return
	 * @throws Exception 
	 */
	public void logInsertTrsmrcvLogSummary() throws Exception{
		insert("LogManageDAO.logInsertTrsmrcvLogSummary", null);
		delete("LogManageDAO.logDeleteTrsmrcvLogSummary", null);
	}

	/**
	 * 사용자 로그정보를 생성한다.
	 * 
	 * @param 
	 * @return
	 * @throws Exception 
	 */
	public void logInsertUserLog() throws Exception{
		insert("LogManageDAO.logInsertUserLog", null);
	}

	/**
	 * 접속로그를 조회한다.
	 * 
	 * @param loginLog
	 * @return loginLog
	 * @throws Exception 
	 */
	public LoginLog selectLoginLog(LoginLog loginLog) throws Exception{
		
		return (LoginLog) selectByPk("LogManageDAO.selectLoginLog", loginLog);
	}	

	/**
	 * 접속로그를 목록을 조회한다.
	 * 
	 * @param loginLog
	 * @return
	 * @throws Exception 
	 */
	public List selectLoginLogInf(LoginLog loginLog) throws Exception{
		return list("LogManageDAO.selectLoginLogInf", loginLog);
	}

	/**
	 * 접속로그 목록의 숫자를 조회한다.
	 * @param loginLog
	 * @return
	 * @throws Exception
	 */
	public int selectLoginLogInfCnt(LoginLog loginLog) throws Exception{
		
		return (Integer)getSqlMapClientTemplate().queryForObject("LogManageDAO.selectLoginLogInfCnt", loginLog);
	}
	
	/**
	 * 시스템 로그정보를 조회한다.
	 * 
	 * @param sysLog
	 * @return sysLog
	 * @throws Exception 
	 */
	public SysLog selectSysLog(SysLog sysLog) throws Exception{
		
		return (SysLog) selectByPk("LogManageDAO.selectSysLog", sysLog);
	}	

	/**
	 * 시스템 로그정보 목록을 조회한다.
	 * 
	 * @param sysLog
	 * @return
	 * @throws Exception 
	 */
	public List selectSysLogInf(SysLog sysLog) throws Exception{
		return list("LogManageDAO.selectSysLogInf", sysLog);
	}

	/**
	 * 시스템 로그정보 목록의 숫자를 조회한다.
	 * @param sysLog
	 * @return
	 * @throws Exception
	 */
	public int selectSysLogInfCnt(SysLog sysLog) throws Exception{
		
		return (Integer)getSqlMapClientTemplate().queryForObject("LogManageDAO.selectSysLogInfCnt", sysLog);
	}
	
	/**
	 * 웹 로그정보를 조회한다.
	 * 
	 * @param webLog
	 * @return webLog
	 * @throws Exception 
	 */
	public WebLog selectWebLog(WebLog webLog) throws Exception{
		
		return (WebLog) selectByPk("LogManageDAO.selectWebLog", webLog);
	}	

	/**
	 * 웹 로그정보 목록을 조회한다.
	 * 
	 * @param webLog
	 * @return
	 * @throws Exception 
	 */
	public List selectWebLogInf(WebLog webLog) throws Exception{
		return list("LogManageDAO.selectWebLogInf", webLog);
	}

	/**
	 * 웹 로그정보 목록의 숫자를 조회한다.
	 * @param webLog
	 * @return
	 * @throws Exception
	 */
	public int selectWebLogInfCnt(WebLog webLog) throws Exception{
		
		return (Integer)getSqlMapClientTemplate().queryForObject("LogManageDAO.selectWebLogInfCnt", webLog);
	}
	
	/**
	 * 송수신 로그정보를 조회한다.
	 * 
	 * @param trsmrcvLog
	 * @return trsmrcvLog
	 * @throws Exception 
	 */
	public TrsmrcvLog selectTrsmrcvLog(TrsmrcvLog trsmrcvLog) throws Exception{
		
		return (TrsmrcvLog) selectByPk("LogManageDAO.selectTrsmrcvLog", trsmrcvLog);
	}	

	/**
	 * 송수신 로그정보 목록을 조회한다.
	 * 
	 * @param TrsmrcvLog
	 * @return
	 * @throws Exception 
	 */
	public List selectTrsmrcvLogInf(TrsmrcvLog trsmrcvLog) throws Exception{
		return list("LogManageDAO.selectTrsmrcvLogInf", trsmrcvLog);
	}

	/**
	 * 송수신 로그정보 목록의 숫자를 조회한다.
	 * @param TrsmrcvLog
	 * @return
	 * @throws Exception
	 */
	public int selectTrsmrcvLogInfCnt(TrsmrcvLog trsmrcvLog) throws Exception{
		
		return (Integer)getSqlMapClientTemplate().queryForObject("LogManageDAO.selectTrsmrcvLogInfCnt", trsmrcvLog);
	}
	
	/**
	 * 사용자 로그정보를 조회한다.
	 * 
	 * @param userLog
	 * @return userLog
	 * @throws Exception 
	 */
	public UserLog selectUserLog(UserLog userLog) throws Exception{
		
		return (UserLog) selectByPk("LogManageDAO.selectUserLog", userLog);
	}	

	/**
	 * 사용자 로그정보 목록을 조회한다.
	 * 
	 * @param UserLog
	 * @return
	 * @throws Exception 
	 */
	public List selectUserLogInf(UserLog userLog) throws Exception{
		return list("LogManageDAO.selectUserLogInf", userLog);
	}

	/**
	 * 사용자 로그정보 목록의 숫자를 조회한다.
	 * @param UserLog
	 * @return
	 * @throws Exception
	 */
	public int selectUserLogInfCnt(UserLog userLog) throws Exception{
		
		return (Integer)getSqlMapClientTemplate().queryForObject("LogManageDAO.selectUserLogInfCnt", userLog);
	}
	
}
