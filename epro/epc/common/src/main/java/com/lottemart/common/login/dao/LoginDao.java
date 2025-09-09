package com.lottemart.common.login.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.common.notice.model.NoticeVO;
import com.lottemart.common.util.DataMap;

/**
 * 로그인 처리 DAO
 * @Class Name : 
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 7. 6. 오후 2:26:44 yhchoi
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Repository("loginDao")
public class LoginDao {
	@Autowired
	private SqlMapClient sqlMapClient;

	/**
	 * 관리자로그인정보 조회
	 * Desc : 
	 * @Method Name : getAdminLogin
	 * @param loginId
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public DataMap getAdminLogin(String loginId) throws SQLException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("loginId", loginId);
		return (DataMap)sqlMapClient.queryForObject("Login.getAdminLogin", paramMap);
	}
	
	/**
	 * 관리자로그인정보 조회 (SSO)
	 * @see getAdminLoginLotte
	 * @Locaton    : com.lottemart.common.login.dao
	 * @MethodName  : getAdminLoginLotte
	 * @author     : hjKim
	 * @Description : 
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	public DataMap getAdminLoginLotte(Map<String, String> paramMap) throws SQLException {
		return (DataMap)sqlMapClient.queryForObject("Login.getAdminLoginLotte", paramMap);
	}

	public DataMap getNowDate() throws SQLException {
		return (DataMap)sqlMapClient.queryForObject("Login.getNowDate");
	}
	 
	/**
	 * 로그인 오류횟수 갱신
	 * Desc : 
	 * @Method Name : updateLoginErrorCnt
	 * @param adminId
	 * @param errorCnt
	 * @param activeYn
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public int updateLoginErrorCnt(Map<String, String> paramMap) throws SQLException {
		return (int)sqlMapClient.update("Login.updateLoginErrorCnt", paramMap);
	}
	
	//5회 오류시 직접 인증번호 초기화
	public int updateSmsSendInit(DataMap dataMap) throws SQLException {
		return sqlMapClient.update("Login.updateSmsSendInit", dataMap);
	}
	
	/**
	 * 
	 * @see getAdminCellNoInfo
	 * @Locaton    : com.lottemart.common.login.dao
	 * @MethodName  : getAdminCellNoInfo
	 * @author     : hjKim
	 * @Description : 관리자 휴대폰정보 조회 
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	public DataMap getAdminCellNoInfo(Map<String, String> paramMap) throws SQLException {
		return (DataMap)sqlMapClient.queryForObject("Login.getAdminCellNoInfo", paramMap);
	}
	
	/**
	 * 
	 * @see getAdMobileInfo
	 * @Locaton    : com.lottemart.common.login.dao
	 * @MethodName  : getAdMobileInfo
	 * @author     : hjKim
	 * @Description : 관리자 & 로그인관리자 휴대폰정보 조회
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> getAdMobileInfo(Map<String, String> paramMap) throws SQLException {
		return (List<DataMap>)sqlMapClient.queryForList("Login.getAdMobileInfo", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public List<DataMap> getSMSAdminList(DataMap dmMap) throws SQLException {
		return (List<DataMap>)sqlMapClient.queryForList("Login.getSMSAdminList", dmMap);
	}
	
	/**
	 * 관리자  인증번호 SMS 전송
	 * Desc : 
	 * @Method Name : insertAdminAuthNoSMS
	 * @param trPhone
	 * @param trMsg
	 * @param trCallBack
	 * @param trEtc4
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public DataMap insertAdminAuthNoSMS(String trPhone, String trMsg, String trCallBack, String trEtc4) throws SQLException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("trPhone", trPhone);
		paramMap.put("trMsg", trMsg);
		paramMap.put("trCallBack", trCallBack);
		paramMap.put("trEtc4", trEtc4);
		return (DataMap)sqlMapClient.insert("Login.insertAdminAuthNoSMS", paramMap);
	}

	/**
	 * 
	 * @see getToDate
	 * @Locaton    : com.lottemart.common.login.dao
	 * @MethodName  : getToDate
	 * @author     : hjKim
	 * @Description : 오늘날짜 조회 
	 * @return
	 * @throws SQLException
	 */
	public DataMap getToDate() throws SQLException {
		return (DataMap)sqlMapClient.queryForObject("Login.getToDate");
	}
	
	/**
	 * 
	 * @see getAdminPwdInfo
	 * @Locaton    : com.lottemart.common.login.dao
	 * @MethodName  : getAdminPwdInfo
	 * @author     : hjKim
	 * @Description : 관리자정보 조회
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	public DataMap getAdminPwdInfo(Map<String, String> paramMap) throws SQLException {
		return (DataMap)sqlMapClient.queryForObject("Login.getAdminPwdInfo", paramMap);
	}
	
	/**
	 * 
	 * @see updateAdminPwd
	 * @Locaton    : com.lottemart.common.login.dao
	 * @MethodName  : updateAdminPwd
	 * @author     : hjKim
	 * @Description : 비밀번호 수정
	 * @param paramMap
	 * @throws SQLException
	 */
	public void updateAdminPwd(Map<String, String> paramMap) throws SQLException {
		sqlMapClient.update("Login.updateAdminPwd", paramMap);
	}
	
	/**
	 * 
	 * @see updateAdminInfo
	 * @Locaton    : com.lottemart.common.login.dao
	 * @MethodName  : updateAdminInfo
	 * @author     : hjKim
	 * @Description : 관리자정보 수정(비밀번호변경인증 처리) 
	 * @param paramMap
	 * @throws SQLException
	 */
	public void updateAdminInfo(Map<String, String> paramMap) throws SQLException {
		sqlMapClient.update("Login.updateAdminInfo", paramMap);
	}

	/**
	 * 
	 * @see initPwdErrorCount
	 * @Locaton    : com.lottemart.common.login.dao
	 * @MethodName  : initPwdErrorCount
	 * @author     : hjKim
	 * @Description : 로그인 성공시 비밀번호에러카운트 초기화
	 * @param paramMap
	 */
	public void initPwdErrorCount(Map<String, String> paramMap) throws SQLException {
		sqlMapClient.update("Login.initPwdErrorCount", paramMap);
	}
	
	/**
	 * 
	 * @see updateCustLoginYn
	 * @Locaton    : com.lottemart.common.login.dao
	 * @MethodName  : updateCustLoginYn
	 * @author     : hjKim
	 * @Description : 고객로그인여부 수정
	 * @param paramMap
	 * @throws SQLException
	 */
	public void updateCustLoginYn(Map<String, String> paramMap) throws SQLException {
		sqlMapClient.update("Login.updateCustLoginYn", paramMap);
	}
	
	/**
	 * 관리자 세션로그 기록(로그인)
	 * Desc : 
	 * @Method Name : insertLoginSessionLog
	 * @param sessionNo
	 * @param adminId
	 * @param adminIp
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public void insertLoginSessionLog(String sessionNo, String adminId, String adminIp, String affiliate , String msg_log, String uuid) throws SQLException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("sessionNo", sessionNo);
		paramMap.put("adminId", adminId);
		paramMap.put("adminIp", adminIp);
		paramMap.put("affiliate", affiliate);
		paramMap.put("msg_log", msg_log);
		paramMap.put("uuid", uuid);
		
		int sessionChk = (Integer)sqlMapClient.queryForObject("Login.getLoginSessionChk", paramMap);
		
		if(sessionChk < 1){
			sqlMapClient.insert("Login.insertLoginSessionLog", paramMap);
		}
	}
	
	/**
	 * 관리자 세션로그 기록(로그아웃)
	 * Desc : 
	 * @Method Name : updateLogoutSessionLog
	 * @param sessionNo
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public DataMap updateLogoutSessionLog(String sessionNo) throws SQLException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("sessionNo", sessionNo);
		return (DataMap)sqlMapClient.insert("Login.updateLogoutSessionLog", paramMap);
	}

	/**
	 * 메인화면의 상단메뉴 조회
	 * Desc : 
	 * @Method Name : getAdminTopMenuList
	 * @param systemType
	 * @param roleId
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> getAdminTopMenuList(String sysDivnCd, String adminId) throws SQLException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("sysDivnCd", sysDivnCd);
		paramMap.put("adminId", adminId);
		return (List<DataMap>)sqlMapClient.queryForList("Login.getAdminTopMenuList", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public List<DataMap> getAdminTopMenuList(String systemType, String roleId, String menuVal) throws SQLException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("systemType", systemType);
		paramMap.put("roleId", roleId);
		paramMap.put("menuVal", menuVal);
		return (List<DataMap>)sqlMapClient.queryForList("Login.getTestMenu", paramMap);
	}
	
	
	/**
	 * Mobile 메인화면의 상단메뉴 조회
	 * Desc :  (모바일메뉴 가져오기, 시스템구분,로그인 id 로 처리 2016.8.25)  ljh
	 * @Method Name : getAdminTopMenuListMobile
	 * @param sysDivnCd
	 * @param loginId
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> getAdminTopMenuListMobile(String sysDivnCd, String loginId) throws SQLException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("sysDivnCd", sysDivnCd);
		paramMap.put("loginId", loginId);
		return (List<DataMap>)sqlMapClient.queryForList("Login.getAdminTopMenuListMobile", paramMap);
	}	
	
	
	/**
	 * 메인화면의 상단서브메뉴 조회
	 * Desc : 
	 * @Method Name : getAdminSubMenuList
	 * @param menuId
	 * @param roleId
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> getAdminSubMenuList(String sysDivnCd, String adminId, String menuId) throws SQLException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("sysDivnCd", sysDivnCd);
		paramMap.put("adminId", adminId);
		paramMap.put("menuId", menuId);
		return (List<DataMap>)sqlMapClient.queryForList("Login.getAdminSubMenuList", paramMap);
	}
	@SuppressWarnings("unchecked")
	public List<DataMap> selectSubMenuList(String menuId, String roleId) throws SQLException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roleId", roleId);
		paramMap.put("menuId", menuId);
		return (List<DataMap>)sqlMapClient.queryForList("Login.getAdminSubMenuList", paramMap);
	}
	
	/**
	 * Mobile 메인화면의 상단서브메뉴 조회
	 * Desc : 
	 * @Method Name : getAdminSubMenuList
	 * @param menuId
	 * @param roleId
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> getAdminSubMenuListMobile(String sysDivnCd, String adminId, String menuId) throws SQLException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("sysDivnCd", sysDivnCd);
		paramMap.put("adminId", adminId);
		paramMap.put("menuId", menuId);
		return (List<DataMap>)sqlMapClient.queryForList("Login.getAdminSubMenuListMobile", paramMap);
	}	
	
	
	/**
	 * 실행권한체크
	 * Desc : 
	 * @Method Name : getAdminMenuAssign
	 * @param roleId
	 * @param scenario
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public DataMap getAdminMenuAssign(String roleId, String scenario) throws SQLException {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("roleId", roleId);			
		paramMap.put("scenario", scenario);			
		return (DataMap)sqlMapClient.queryForObject("Login.getAdminMenuAssign", paramMap);
	}

	public void insertAdminWorkLog(Map<String, String> paramMap) throws SQLException {
		sqlMapClient.insert("Login.insertAdminWorkLog", paramMap);
	}

	/**
	 * 
	 * @see selectNoticeList
	 * @Locaton    : com.lottemart.common.login.dao
	 * @MethodName  : selectNoticeList
	 * @author     : hjKim
	 * @param paramMap 
	 * @Description : 공지사항 목록 조회 
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectNoticeList(Map<String, String> paramMap) throws SQLException {
		return sqlMapClient.queryForList("Login.selectNoticeList", paramMap);
	}

	/**
	 * 
	 * @see authCheck
	 * @Locaton    : com.lottemart.common.login.dao
	 * @MethodName  : authCheck
	 * @author     : hjKim
	 * @Description : 실행권한체크 
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	public DataMap authCheck(Map<String, String> paramMap) throws SQLException {
		return (DataMap) sqlMapClient.queryForObject("Login.authCheck", paramMap);
	}
	/**
	 * 
	 * @see selectPopupNoticeList
	 * @Locaton    : com.lottemart.common.login.dao
	 * @MethodName  : selectPopupNoticeList
	 * @author     : hjKim
	 * @Description : 팝업공지목록 조회
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<NoticeVO> selectPopupNoticeList(Map<String, String> paramMap) throws SQLException {
		return sqlMapClient.queryForList("Login.selectPopupNoticeList", paramMap);
	}
	
	/**
	 * 
	 * @see selectPopupNoticeList
	 * @Locaton    : com.lottemart.common.login.dao
	 * @MethodName  : selectPopupNoticeList
	 * @author     : hjKim
	 * @Description : 팝업공지목록 조회
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public DataMap selectSsoPassInfo() throws SQLException {
		Map<String, String> paramMap = new HashMap<String, String>();
		return (DataMap) sqlMapClient.queryForObject("Login.selectSsoPassInfo", paramMap);
	}
	/**
	 * 
	 * @see menuAuthCheck
	 * @Locaton    : com.lottemart.common.login.dao
	 * @MethodName  : menuAuthCheck
	 * @author     : jyLim
	 * @Description : 
	 * @param paramMap
	 * @return TRUE: 권한있음 , FALSE: 권한없음
	 * @throws SQLException
	 */
	public DataMap menuAuthCheck(Map<String, String> paramMap) throws SQLException {
		return (DataMap) sqlMapClient.queryForObject("Login.menuAuthCheck", paramMap);
	}
	
	/**
	 * 
	 * @see insertAdminWorkLogSCM
	 * @Locaton    : com.lottemart.common.login.dao
	 * @MethodName  : insertAdminWorkLogSCM
	 * @author     : 
	 * @Description : SCM 관리지 로그 입력 
	 * @param paramMap
	 * @return :
	 * @throws SQLException
	 */
	public void insertAdminWorkLogSCM(Map<String, String> paramMap) throws SQLException {
		sqlMapClient.insert("Login.insertAdminWorkLogSCM", paramMap);
	}

	public DataMap selectPwd(DataMap dmap) throws SQLException{
		return (DataMap)sqlMapClient.queryForObject("Login.selectPwd",dmap);
	}
	
	public DataMap selectPwdHist(DataMap dmap) throws SQLException{
		return (DataMap)sqlMapClient.queryForObject("Login.selectPwdHist",dmap);
	}
	
	public DataMap selectPwdHist05(DataMap dmap) throws SQLException{
		return (DataMap)sqlMapClient.queryForObject("Login.selectPwdHist05",dmap);
	}
	
	public void insertPwdHist(DataMap dmap) throws SQLException {
		sqlMapClient.insert("Login.insertPwdHist", dmap);
	}
	
	public String selectAdminRoleAssign(DataMap paramMap) throws SQLException{
		return (String)sqlMapClient.queryForObject("Login.selectAdminRoleAssign",paramMap);
	}
	
	public DataMap authCheckForCC(Map<String, String> paramMap) throws SQLException {
		return (DataMap) sqlMapClient.queryForObject("Login.authCheckForCC", paramMap);
	}
	
	public String selectOnceDayChk(DataMap paramMap) throws SQLException{
		return (String)sqlMapClient.queryForObject("Login.selectOnceDayChk",paramMap);
	}
	
	public String selectUnanswerCnt(DataMap paramMap) throws SQLException{
		return (String)sqlMapClient.queryForObject("Login.selectUnanswerCnt",paramMap);
	}
	
	/**
	 * Picker 정보 업데이트
	 * @param paramMap
	 * @throws SQLException
	 */
	public void updatePickerInfo(DataMap paramMap) throws SQLException {
		sqlMapClient.update("Login.updatePickerInfo", paramMap);
	}
	
	/**
	 * 관리자 정보 조회 by adminId
	 * @param adminId
	 * @return
	 * @throws SQLException
	 */
	public DataMap getAdminInfoByAdminId(DataMap paramMap) throws SQLException {
		return (DataMap)sqlMapClient.queryForObject("Login.getAdminInfoByAdminId", paramMap);
	}

	/**
	 * 세미다크 지점여부
	 * @param strCd
	 * @return
	 * @throws SQLException
	 */
	public String getSemidarkYn(String strCd) throws SQLException {
		return (String)sqlMapClient.queryForObject("Login.getSemidarkYn", strCd);
	}
}
