package com.lottemart.common.log.service;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.util.StopWatch;

import com.lottemart.common.log.model.LoginLog;
import com.lottemart.common.log.model.SysLog;


/**
 * @Class Name : LogManageAspect.java
 * @Description : 시스템 로그 생성을 위한 ASPECT 클래스
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
public class LogManageAspect {
	
	@Resource(name="LogManageService")
	private LogManageService logManageService;

	/**
	 * 시스템 로그정보를 생성한다.
	 * sevice Class의 insert로 시작되는 Method
	 * 
	 * @param ProceedingJoinPoint
	 * @return Object
	 * @throws Exception 
	 */
	public Object logInsert(ProceedingJoinPoint joinPoint) throws Throwable {
		
		StopWatch stopWatch = new StopWatch();
		
		try {
			stopWatch.start();
			
			Object retValue = joinPoint.proceed();
			return retValue;
		} catch (Throwable e) {
			throw e;
		} finally {
			stopWatch.stop();
			
			SysLog sysLog = new SysLog();
			String className = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			String processSeCode = "C";
			String processTime = Long.toString(stopWatch.getTotalTimeMillis());
			String uniqId = "";
			String ip = "";
			
	    	/* Authenticated  */
//			
//	        Boolean isAuthenticated = UserDetailsHelper.isAuthenticated();
//	    	if(isAuthenticated.booleanValue()) {
//				LoginVO user = (LoginVO)UserDetailsHelper.getAuthenticatedUser();
//				uniqId = user.getUniqId();
//				ip = user.getIp();
//	    	}

			sysLog.setSrvcNm(className);
			sysLog.setMethodNm(methodName);
			sysLog.setProcessSeCode(processSeCode);
			sysLog.setProcessTime(processTime);
			sysLog.setRqesterId(uniqId);
			sysLog.setRqesterIp(ip);
			
			logManageService.logInsertSysLog(sysLog);
			
		}
		
	}
	
	/**
	 * 시스템 로그정보를 생성한다.
	 * sevice Class의 update로 시작되는 Method
	 * 
	 * @param ProceedingJoinPoint
	 * @return Object
	 * @throws Exception 
	 */
	public Object logUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
		
		StopWatch stopWatch = new StopWatch();
		
		try {
			stopWatch.start();
			
			Object retValue = joinPoint.proceed();
			return retValue;
		} catch (Throwable e) {
			throw e;
		} finally {
			stopWatch.stop();
			
			SysLog sysLog = new SysLog();
			String className = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			String processSeCode = "U";
			String processTime = Long.toString(stopWatch.getTotalTimeMillis());
			String uniqId = "";
			String ip = "";
			
	    	/* Authenticated  */
//	        Boolean isAuthenticated = UserDetailsHelper.isAuthenticated();
//	    	if(isAuthenticated.booleanValue()) {
//				LoginVO user = (LoginVO)UserDetailsHelper.getAuthenticatedUser();
//				uniqId = user.getUniqId();
//				ip = user.getIp();
//	    	}

			sysLog.setSrvcNm(className);
			sysLog.setMethodNm(methodName);
			sysLog.setProcessSeCode(processSeCode);
			sysLog.setProcessTime(processTime);
			sysLog.setRqesterId(uniqId);
			sysLog.setRqesterIp(ip);
			
			logManageService.logInsertSysLog(sysLog);
			
		}
		
	}
	
	/**
	 * 시스템 로그정보를 생성한다.
	 * sevice Class의 delete로 시작되는 Method
	 * 
	 * @param ProceedingJoinPoint
	 * @return Object
	 * @throws Exception 
	 */
	public Object logDelete(ProceedingJoinPoint joinPoint) throws Throwable {
		
		StopWatch stopWatch = new StopWatch();
		
		try {
			stopWatch.start();
			
			Object retValue = joinPoint.proceed();
			return retValue;
		} catch (Throwable e) {
			throw e;
		} finally {
			stopWatch.stop();
			
			SysLog sysLog = new SysLog();
			String className = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			String processSeCode = "D";
			String processTime = Long.toString(stopWatch.getTotalTimeMillis());
			String uniqId = "";
			String ip = "";
			
	    	/* Authenticated  */
//	        Boolean isAuthenticated = UserDetailsHelper.isAuthenticated();
//	    	if(isAuthenticated.booleanValue()) {
//				LoginVO user = (LoginVO)UserDetailsHelper.getAuthenticatedUser();
//				uniqId = user.getUniqId();
//				ip = user.getIp();
//	    	}

			sysLog.setSrvcNm(className);
			sysLog.setMethodNm(methodName);
			sysLog.setProcessSeCode(processSeCode);
			sysLog.setProcessTime(processTime);
			sysLog.setRqesterId(uniqId);
			sysLog.setRqesterIp(ip);
			
			logManageService.logInsertSysLog(sysLog);
			
		}
		
	}
	
	/**
	 * 시스템 로그정보를 생성한다.
	 * sevice Class의 select로 시작되는 Method
	 * 
	 * @param ProceedingJoinPoint
	 * @return Object
	 * @throws Exception 
	 */
	public Object logSelect(ProceedingJoinPoint joinPoint) throws Throwable {
		
		StopWatch stopWatch = new StopWatch();
		
		try {
			stopWatch.start();
			
			Object retValue = joinPoint.proceed();
			return retValue;
		} catch (Throwable e) {
			throw e;
		} finally {
			stopWatch.stop();
			
			SysLog sysLog = new SysLog();
			String className = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			String processSeCode = "R";
			String processTime = Long.toString(stopWatch.getTotalTimeMillis());
			String uniqId = "";
			String ip = "";
			
	    	/* Authenticated  */
//	        Boolean isAuthenticated = UserDetailsHelper.isAuthenticated();
//	    	if(isAuthenticated.booleanValue()) {
//				LoginVO user = (LoginVO)UserDetailsHelper.getAuthenticatedUser();
//				uniqId = user.getUniqId();
//				ip = user.getIp();
//	    	}

			sysLog.setSrvcNm(className);
			sysLog.setMethodNm(methodName);
			sysLog.setProcessSeCode(processSeCode);
			sysLog.setProcessTime(processTime);
			sysLog.setRqesterId(uniqId);
			sysLog.setRqesterIp(ip);
			
			logManageService.logInsertSysLog(sysLog);
			
		}
		
	}
	
	/**
	 * 로그인 로그정보를 생성한다.
	 * LoginController.actionMain Method
	 * 
	 * @param 
	 * @return void
	 * @throws Exception 
	 */
	public void logLogin() throws Throwable {
		
		String uniqId = "";
		String ip = "";

		/* Authenticated  */
//        Boolean isAuthenticated = UserDetailsHelper.isAuthenticated();
//    	if(isAuthenticated.booleanValue()) {
//			LoginVO user = (LoginVO)UserDetailsHelper.getAuthenticatedUser();
//			uniqId = user.getUniqId();
//			ip = user.getIp();
//    	}

    	LoginLog loginLog = new LoginLog();
    	loginLog.setLoginId(uniqId);
        loginLog.setLoginIp(ip);
        loginLog.setLoginMthd("I"); // 로그인:I, 로그아웃:O
        loginLog.setErrOccrrAt("N");
        loginLog.setErrorCode("");
        logManageService.logInsertLoginLog(loginLog);

	}
	
	/**
	 * 로그아웃 로그정보를 생성한다.
	 * LoginController.actionLogout Method
	 * 
	 * @param 
	 * @return void
	 * @throws Exception 
	 */
	public void logLogout() throws Throwable {
		
		String uniqId = "";
		String ip = "";

		/* Authenticated  */
//        Boolean isAuthenticated = UserDetailsHelper.isAuthenticated();
//    	if(isAuthenticated.booleanValue()) {
//			LoginVO user = (LoginVO)UserDetailsHelper.getAuthenticatedUser();
//			uniqId = user.getUniqId();
//			ip = user.getIp();
//    	}

    	LoginLog loginLog = new LoginLog();
    	loginLog.setLoginId(uniqId);
        loginLog.setLoginIp(ip);
        loginLog.setLoginMthd("O"); // 로그인:I, 로그아웃:O
        loginLog.setErrOccrrAt("N");
        loginLog.setErrorCode("");
        logManageService.logInsertLoginLog(loginLog);
	}
	
}
