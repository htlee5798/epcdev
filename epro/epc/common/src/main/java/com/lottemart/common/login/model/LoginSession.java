package com.lottemart.common.login.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.common.util.DataMap;

public class LoginSession implements Serializable {
	
	private static final long serialVersionUID = -9003127277782284753L;
	public static final String LOTTEMART_BOS_LOGIN_SESSION_KEY = "lottemart.bos.loginSession";

	public static LoginSession getLoginSession(HttpServletRequest request) {
		return (LoginSession) request.getSession().getAttribute(
				LoginSession.LOTTEMART_BOS_LOGIN_SESSION_KEY);
	}

	protected String	adminId			;			//	관리자ID
	protected String	adminNm			;			//	관리자명
	protected String	loginId			;			//	로그인ID
	protected String	lmartEmpNo		;			//	롯데마트사원번호
	protected String    adminTypeCd		;			//  관리자유형코드(SM32)
	protected String	email			;			//	이메일
	protected String	strCd			;			//	점포코드
	protected String	idnoReadYn		;			//	주민번호열람여부
	protected String	cardnoReadYn	;			//	카드번호열람여부
	protected String	vaccntNoReadYn	;			//	가상계좌번호열람여부
	protected String	replyDivnCd		;			//	답변자구분코드
	protected String	pickerOrderSeq	;			//	피커정렬순번
	protected String    sysDivnCd       ;			//  시스템 구분코드(01:BOS, 02:CC, 03:PP)
	protected String    deptCd       ;			//  부서코드
	/* 2020-02-17 바로배송 추가 */
	protected String loginDateStr;		// 로그인 일자
	protected String qpsPickerType;				// 피커구분 (10:바로배송, 20:예약, 00:피커가 아님)
	protected String qpsPickerWorkYn;		// qps picker 작업 여부
	protected String lastLoginDate;		// lastLoginDate yyyyMMddhhmmss
	/* //2020-02-17 바로배송 추가 */
	protected List<DataMap> menuList = new ArrayList<DataMap>();
	protected List<DataMap> subMenuList = new ArrayList<DataMap>();
	
	//bos
	protected String    ssoDivnCd		;			//	SSO 로그인 구분 코드 		(ConstanceValue.java 참조)
	protected String    jobDivnCd		;			//	관리자 SSO job 구분 코드 	(ConstanceValue.java 참조)
	
	//세미다크
	protected  String semidarkYn;	//세마다크 지점여부
	
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	
	public String getAdminNm() {
		return adminNm;
	}
	public void setAdminNm(String adminNm) {
		this.adminNm = adminNm;
	}
	
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getLmartEmpNo() {
		return lmartEmpNo;
	}
	public void setLmartEmpNo(String lmartEmpNo) {
		this.lmartEmpNo = lmartEmpNo;
	}
	public String getAdminTypeCd() {
		return adminTypeCd;
	}
	public void setAdminTypeCd(String adminTypeCd) {
		this.adminTypeCd = adminTypeCd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStrCd() {
		return strCd;
	}
	public void setStrCd(String strCd) {
		this.strCd = strCd;
	}
	public String getIdnoReadYn() {
		return idnoReadYn;
	}
	public void setIdnoReadYn(String idnoReadYn) {
		this.idnoReadYn = idnoReadYn;
	}
	public String getCardnoReadYn() {
		return cardnoReadYn;
	}
	public void setCardnoReadYn(String cardnoReadYn) {
		this.cardnoReadYn = cardnoReadYn;
	}
	public String getVaccntNoReadYn() {
		return vaccntNoReadYn;
	}
	public void setVaccntNoReadYn(String vaccntNoReadYn) {
		this.vaccntNoReadYn = vaccntNoReadYn;
	}
	public String getReplyDivnCd() {
		return replyDivnCd;
	}
	public void setReplyDivnCd(String replyDivnCd) {
		this.replyDivnCd = replyDivnCd;
	}
	public String getPickerOrderSeq() {
		return pickerOrderSeq;
	}
	public void setPickerOrderSeq(String pickerOrderSeq) {
		this.pickerOrderSeq = pickerOrderSeq;
	}
	public static String getLottemartBosLoginSessionKey() {
		return LOTTEMART_BOS_LOGIN_SESSION_KEY;
	}
	public String getSysDivnCd() {
		return sysDivnCd;
	}
	public void setSysDivnCd(String sysDivnCd) {
		this.sysDivnCd = sysDivnCd;
	}
	public String getSsoDivnCd() {
		return ssoDivnCd;
	}
	public void setSsoDivnCd(String ssoDivnCd) {
		this.ssoDivnCd = ssoDivnCd;
	}
	public String getJobDivnCd() {
		return jobDivnCd;
	}
	public void setJobDivnCd(String jobDivnCd) {
		this.jobDivnCd = jobDivnCd;
	}
	public String getDeptCd() {
		return deptCd;
	}
	public void setDeptCd(String deptCd) {
		this.deptCd = deptCd;
	}
	public List<DataMap> getMenuList() {
		return menuList;
	}
	public void setMenuList(List<DataMap> menuList) {
		this.menuList = menuList;
	}
	public List<DataMap> getSubMenuList() {
		return subMenuList;
	}
	public void setSubMenuList(List<DataMap> subMenuList) {
		this.subMenuList = subMenuList;
	}
	public String getLoginDateStr() {
		return loginDateStr;
	}
	public void setLoginDateStr(String loginDateStr) {
		this.loginDateStr = loginDateStr;
	}
	public String getQpsPickerType() {
		return qpsPickerType;
	}
	public void setQpsPickerType(String qpsPickerType) {
		this.qpsPickerType = qpsPickerType;
	}
	public String getQpsPickerWorkYn() {
		return qpsPickerWorkYn;
	}
	public void setQpsPickerWorkYn(String qpsPickerWorkYn) {
		this.qpsPickerWorkYn = qpsPickerWorkYn;
	}
	public String getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getSemidarkYn()  {
		return semidarkYn;
	}

	public void setSemidarkYn(String semidarkYn) {
		this.semidarkYn = semidarkYn;
	}
}
