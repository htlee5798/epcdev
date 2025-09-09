package com.lottemart.epc.common.service.impl;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lcn.module.framework.property.ConfigurationService;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lcnjf.util.DateUtil;
import com.lcnjf.util.StringUtil;
import com.lottemart.common.exception.AlertException;
import com.lottemart.common.login.model.LoginSession;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.dao.PSCMCOM0001Dao;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.service.PSCMCOM0001Service;
import com.lottemart.epc.common.util.EPCUtil;
import com.lottemart.epc.edi.comm.model.HistoryVo;
import com.lottemart.epc.edi.comm.service.HistoryCommonService;


@Service("PSCMCOM0001Service")
public class PSCMCOM0001ServiceImpl implements PSCMCOM0001Service {

	private static final Logger logger = LoggerFactory.getLogger(PSCMCOM0001ServiceImpl.class);

	private final String [] tmpVendor = {"999999"};

	@Resource(name = "configurationService")
	private ConfigurationService config;

	@Autowired
	private PSCMCOM0001Dao epcLoginDao;
	
	@Autowired
	private HistoryCommonService historyCommonService;

	//250723 미사용로직 (sms 체크삭제)
	public boolean makeClientSessionInfoSms(HttpServletRequest request, String conoArrStr, DataMap codeMap) throws Exception {

		boolean rtnIsSmsAuth = true;
		String[] arrVen = null;
		String[] arrVen2 = null;

		String smsAuthYn = String.valueOf(codeMap.get("LET_1_REF")); // SMS 2차 인증 : Y (특정IP SMS인증제어 체크를 한다) , N (특정IP SMS인증제어 체크 안함)
		String ipSmsAuthNoneYn = String.valueOf(codeMap.get("LET_3_REF")); // 특정IP SMS인증예외 : Y (SMS인증불필요) , N (SMS인증필요)
		String sessionKey = config.getString("lottemart.epc.temp.key"); // Default SMS 2차 인증
		//logger.debug("LET_1_REF:" + smsAuthYn);
		//logger.debug("LET_3_REF:" + ipSmsAuthNoneYn);
		//logger.debug("sessionKey (1):" + sessionKey);
		if (!"Y".equals(smsAuthYn)) { // SMS 2차 인증 해제
			sessionKey = config.getString("lottemart.epc.session.key");
			rtnIsSmsAuth = false;
			//logger.error("sessionKey (1-1):" + sessionKey);
			//logger.error("rtnIsSmsAuth(1-1):" + rtnIsSmsAuth);
		}
		//logger.debug("EPC Session Key = [" + sessionKey + "]");

		if (conoArrStr == null || conoArrStr.length() < 10) {
			throw new AlertException("올바르지않은 사업자번호 입니다.");
		}

		String[] cono = conoArrStr.split(";");

		if (cono == null || cono.length < 1) {
			throw new AlertException("사업자번호가 존재하지 않습니다.");
		}

		for (int i = 0; i < cono.length; i++) {
			if (cono[i] == null || cono[i].length() != 10) {
				throw new AlertException("사업자번호의 길이가 올바르지 않습니다.");
			}
			cono[i] = StringUtil.removeWhitespace(cono[i]);
		}

		EpcLoginVO epcLoginVO = new EpcLoginVO();
		epcLoginVO.setCono(cono);

		List<DataMap> infoArr = epcLoginDao.getClienLoginInfo(cono);

		if (infoArr != null && infoArr.size() > 0) {
			arrVen = new String[infoArr.size()];
			arrVen2 = new String[infoArr.size()];

			for (int i = 0; i < infoArr.size(); i++) {
				DataMap infoMap = (DataMap) infoArr.get(i);
				arrVen[i] = infoMap.getString("VEN_CD", "");
				epcLoginVO.setLoginNm(infoMap.getString("VEN_NM", ""));
				epcLoginVO.setVendorTypeCd(infoMap.getString("VEN_TYPE_CD", ""));
				arrVen2[i] = infoMap.getString("TRD_TYP_NM", "");
			}

			epcLoginVO.setRepVendorId(arrVen[0]);
			epcLoginVO.setVendorId(arrVen);
			epcLoginVO.setTradeTypeNm(arrVen2); // 2016.05.17 거래유형 추가

			//logger.debug("rtnIsSmsAuth(2):" + rtnIsSmsAuth);
			if ("Y".equals(ipSmsAuthNoneYn) && rtnIsSmsAuth) { // 특정IP SMS인증 예외 (Y: SMS인증불필요, N: SMS인증필요)
				DataMap ipChkMap = new DataMap();
				ipChkMap.put("ip", request.getRemoteAddr());
				DataMap ipRtnMap = epcLoginDao.checkClientInfo(ipChkMap);
				if (ipRtnMap != null) {
					rtnIsSmsAuth = false;
					sessionKey = config.getString("lottemart.epc.session.key");
				}
			}

			request.getSession().setAttribute(sessionKey, epcLoginVO);
		} else {
			throw new IllegalArgumentException("올바르지않은 사업자번호 입니다.");
		}

		return rtnIsSmsAuth;
	}

//	public void makeAdminSessionInfo_OLD250710(HttpServletRequest request, String loginId, String pwd, String cono) throws Exception {
//		String sessionKey = config.getString("lottemart.epc.session.key");
//		//logger.debug("EPC Session Key = [" + sessionKey + "]");
//
//		// 선택한 대표 vendorId
//		String repVendorId = request.getParameter("repVendorId");
//
//		EpcLoginVO epcLoginVO = new EpcLoginVO();
//
//		String[] arrVen = null;
//		String[] arrVen2 = null;
//		String[] arrVen3 = null;
//
//		if (loginId == null || loginId.length() < 1) {
//			throw new AlertException("ID가 입력되지 않았습니다.");
//		}
//
//		if (cono == null || cono.length() != 10) {
//			throw new AlertException("올바르지않은 사업자번호 입니다.");
//		}
//
//		DataMap adminMap = epcLoginDao.checkAdminInfo(loginId);
//
//		if (adminMap == null || adminMap.isEmpty()) {
//			throw new AlertException("로그인정보가 적절하지 않습니다.");
//		}
//
//		if (pwd == null || pwd.length() < 1) {
//			throw new AlertException("password가 입력되지 않았습니다.");
//		}
//
//		if (!adminMap.getString("PWD").equals(pwd)) {
//			throw new AlertException("패스워드가 올바르지 않습니다.");
//		}
//
//		if (repVendorId != null && repVendorId.length() > 1) {
//			epcLoginVO.setRepVendorId(repVendorId);
//		}
//
//		String[] arrCono = { cono };
//		epcLoginVO.setAdminId(loginId);
//		epcLoginVO.setAdminNm(adminMap.getString("ADMIN_NM"));
//		epcLoginVO.setCono(arrCono);
//
//		List<DataMap> infoArr = epcLoginDao.getClienLoginInfo(arrCono);
//
//		if (infoArr != null && infoArr.size() > 0) {
//			arrVen = new String[infoArr.size()];
//			arrVen2 = new String[infoArr.size()];
//			arrVen3 = new String[infoArr.size()];
//			for (int i = 0; i < infoArr.size(); i++) {
//				DataMap infoMap = (DataMap) infoArr.get(i);
//				arrVen[i] = infoMap.getString("VEN_CD", "");
//				epcLoginVO.setLoginNm(infoMap.getString("VEN_NM", ""));
//				epcLoginVO.setVendorTypeCd(infoMap.getString("VEN_TYPE_CD", ""));
//				arrVen2[i] = infoMap.getString("TRD_TYP_FG", ""); // 2016.05.17 거래유형 추가
//				arrVen3[i] = infoMap.getString("TRD_TYP_NM", ""); // 2016.05.17 거래유형 추가
//			}
//			epcLoginVO.setVendorId(arrVen);
//			epcLoginVO.setTradeType(arrVen2);
//			epcLoginVO.setTradeTypeNm(arrVen3);
//
//			request.getSession().setAttribute(sessionKey, epcLoginVO);
//
//		} else {
//			throw new AlertException("올바르지않은 사업자번호 입니다.");
//		}
//
//	}

//	public void makeAdminSessionInfo_old250723(HttpServletRequest request, String loginId, String cono) throws Exception {
//		String sessionKey = config.getString("lottemart.epc.session.key");
//		//logger.debug("EPC Session Key = [" + sessionKey + "]");
//		// 선택한 대표 vendorId
//		String repVendorId = request.getParameter("repVendorId");
//
//		EpcLoginVO epcLoginVO = new EpcLoginVO();
//
//		String[] arrVen = null;
//
//		if (loginId == null || loginId.length() < 1) {
//			throw new AlertException("ID가 입력되지 않았습니다.");
//		}
//
//		/*if( pwd == null || pwd.length() < 1 ) {
//			throw new AlertException("password가 입력되지 않았습니다.");
//		} */
//
//		if (cono == null || cono.length() != 10) {
//			throw new AlertException("올바르지않은 사업자번호 입니다.");
//		}
//
//		DataMap adminMap = epcLoginDao.checkAdminInfo(loginId);
//
//		if (adminMap == null || adminMap.isEmpty()) {
//			throw new AlertException("로그인정보가 적절하지 않습니다.");
//		}
//
//		if (repVendorId != null && repVendorId.length() > 1) {
//			epcLoginVO.setRepVendorId(repVendorId);
//		}
//
//		String[] arrCono = { cono };
//		epcLoginVO.setAdminId(loginId);
//		epcLoginVO.setAdminNm(adminMap.getString("ADMIN_NM"));
//		epcLoginVO.setCono(arrCono);
//
//		List<DataMap> infoArr = epcLoginDao.getClienLoginInfo(arrCono);
//
//		if (infoArr != null && infoArr.size() > 0) {
//			arrVen = new String[infoArr.size()];
//
//			for (int i = 0; i < infoArr.size(); i++) {
//				DataMap infoMap = (DataMap) infoArr.get(i);
//				arrVen[i] = infoMap.getString("VEN_CD", "");
//				epcLoginVO.setLoginNm(infoMap.getString("VEN_NM", ""));
//				epcLoginVO.setVendorTypeCd(infoMap.getString("VEN_TYPE_CD", ""));
//			}
//			epcLoginVO.setVendorId(arrVen);
//
//			request.getSession().setAttribute(sessionKey, epcLoginVO);
//
//		} else {
//			throw new AlertException("올바르지않은 사업자번호 입니다.");
//		}
//	}

//	public void makeAdminSessionInfoChg_OLD250723(HttpServletRequest request, String cono) throws Exception {
//		String sessionKey = config.getString("lottemart.epc.session.key");
//		//logger.debug("EPC Session Key = [" + sessionKey + "]");
//		String repVendorId = request.getParameter("repVendorId");
//
//		LoginSession loginSession = LoginSession.getLoginSession((HttpServletRequest) request);
//
//		EpcLoginVO epcLoginVO = new EpcLoginVO();
//
//		String[] arrVen = null;
//		String[] arrCono = { cono };
//
//		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);
//
//		String loginId = epcLoginVO.getAdminId();
//
//		DataMap adminMap = epcLoginDao.checkAdminInfo(loginId);
//
//		epcLoginVO.setAdminId(epcLoginVO.getAdminId());
//		epcLoginVO.setAdminNm(adminMap.getString("ADMIN_NM"));
//		epcLoginVO.setCono(arrCono);
//
//		//logger.debug("arrCono:" + arrCono);
//		List<DataMap> infoArr = epcLoginDao.getClienLoginInfo(arrCono);
//
//		if (repVendorId != null && repVendorId.length() > 1) {
//			epcLoginVO.setRepVendorId(repVendorId);
//		}
//
//		if (infoArr != null && infoArr.size() > 0) {
//			arrVen = new String[infoArr.size()];
//
//			for (int i = 0; i < infoArr.size(); i++) {
//				DataMap infoMap = (DataMap) infoArr.get(i);
//				arrVen[i] = infoMap.getString("VEN_CD", "");
//				epcLoginVO.setLoginNm(infoMap.getString("VEN_NM", ""));
//				epcLoginVO.setVendorTypeCd(infoMap.getString("VEN_TYPE_CD", ""));
//			}
//
//			//logger.debug("arrVen:" + arrVen);
//			epcLoginVO.setVendorId(arrVen);
//
//			//logger.debug("epcLoginVO.getVendorId:" + epcLoginVO.getVendorId());
//			request.getSession().setAttribute(sessionKey, epcLoginVO);
//
//		} else {
//			throw new AlertException("올바르지않은 사업자번호 입니다.");
//		}
//	}

	public void makeHappyCallSessionInfo(HttpServletRequest request, String loginId, String pwd) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		//logger.debug("EPC Session Key = [" + sessionKey + "]");

		EpcLoginVO epcLoginVO = new EpcLoginVO();

		String[] arrVen = null;

		if (loginId == null || loginId.length() < 1) {
			throw new AlertException("ID가 입력되지 않았습니다.");
		}

		if (pwd == null || pwd.length() < 1) {
			throw new AlertException("password가 입력되지 않았습니다.");
		}

		DataMap adminMap = epcLoginDao.checkHappyInfo(loginId);

		if (adminMap == null || adminMap.isEmpty()) {
			throw new AlertException("로그인정보가 적절하지 않습니다.");
		}

		if (!adminMap.getString("PWD").equals(pwd)) {
			throw new AlertException("패스워드가 올바르지 않습니다.");
		}
		
		epcLoginDao.updateLastLoginDate(loginId);

		epcLoginVO.setAdminId(loginId);
		epcLoginVO.setVendorId(tmpVendor);
		epcLoginVO.setAdminNm(adminMap.getString("ADMIN_NM"));
		epcLoginVO.setHappyGubn(true);

		request.getSession().setAttribute(sessionKey, epcLoginVO);
	}

	public void makeAllianceSessionInfo(HttpServletRequest request, String loginId, String pwd) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		//logger.debug("EPC Session Key = [" + sessionKey + "]");

		EpcLoginVO epcLoginVO = new EpcLoginVO();

		String[] arrVen = null;

		if (loginId == null || loginId.length() < 1) {
			throw new AlertException("ID가 입력되지 않았습니다.");
		}

		if (pwd == null || pwd.length() < 1) {
			throw new AlertException("password가 입력되지 않았습니다.");
		}

		DataMap adminMap = epcLoginDao.checkAllianceInfo(loginId);

		if (adminMap == null || adminMap.isEmpty()) {
			throw new AlertException("로그인정보가 적절하지 않습니다.");
		}

		if (!adminMap.getString("PWD").equals(pwd)) {
			throw new AlertException("패스워드가 올바르지 않습니다.");
		}

		epcLoginVO.setAdminId(loginId);
		epcLoginVO.setVendorId(tmpVendor);
		epcLoginVO.setAdminNm(adminMap.getString("ADMIN_NM"));
		epcLoginVO.setAdminTypeCd(adminMap.getString("ADMIN_TYPE_CD"));
		epcLoginVO.setAllianceGubn(true);

		request.getSession().setAttribute(sessionKey, epcLoginVO);
	}

	/**
	 * 로그아웃
	 * Desc :
	 * @Method Name : logout
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public void logout(HttpServletRequest request) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		//logger.debug("EPC Session Key = [" + sessionKey + "]");
		request.getSession().removeAttribute(sessionKey);
	}

	
	
	////// NEW EPC 수정 ----------------------------------------------------------------------------------------
	/**
	 * [관리자] 로그인 검증 및 세션 생성
	 */
	public void makeAdminSessionInfo(HttpServletRequest request, String loginId, String pwd, String cono) throws Exception {
		// 선택한 대표 vendorId
		String repVendorId = request.getParameter("repVendorId");
		
		/*
		 * 1. 필수데이터 체크
		 */
		//아이디
		if (loginId == null || loginId.length() < 1) {
			throw new AlertException("ID가 입력되지 않았습니다.");
		}
		
		//비밀번호
		if(pwd == null || pwd.length() < 1) {
			throw new AlertException("패스워드가 입력되지 않았습니다.");
		}
		
		//사업자번호
		if (cono == null || cono.length() != 10) {
			throw new AlertException("올바르지않은 사업자번호 입니다.");
		}
		
		//시스템 관리자 계정인지 확인
		Map<String,Object> sysAdminInfo = epcLoginDao.selectEpcSysAdminUserInfo(loginId);
		if(sysAdminInfo == null || sysAdminInfo.isEmpty()) {
			throw new AlertException("로그인 정보가 올바르지 않습니다.");
		}
		
		//패스워드 일치여부 확인
		String sysAdminPwd = MapUtils.getString(sysAdminInfo, "USER_PWD", "");	//DB 조회한 패스워드
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean passwordEqual = passwordEncoder.matches(pwd, sysAdminPwd);
        
        //패스워드 불일치 시,
        if(!passwordEqual) {
        	throw new AlertException("패스워드가 올바르지 않습니다.");
        }
	
		/*
		 * 2. 파트너사 정보 동기화 (HQ_VEN > TVE_VENDOR)
		 */
		this.updateMergeHqVenToTveVendorInLogin(cono);
		
		/*
		 * 3. 세션데이터 생성
		 */
		// LoginSession 생성용 VO
		EpcLoginVO epcLoginVO = new EpcLoginVO();
		
		//----- 관리자정보 setting
		//관리자 ID 셋팅
		epcLoginVO.setAdminId(loginId);
		
		//관리자명 셋팅
		String loginNm = MapUtils.getString(sysAdminInfo, "USER_NM", loginId);
		epcLoginVO.setAdminNm(loginNm);
		
		//시스템 관리자 구분 셋팅
		String sysAdminFg = MapUtils.getString(sysAdminInfo, "USER_TYPE", "SV");
		epcLoginVO.setSysAdmFg(sysAdminFg);	
		
		//----- 대표 파트너사코드 셋팅
		if (repVendorId != null && repVendorId.length() > 1) epcLoginVO.setRepVendorId(repVendorId);
		
		//파트너사 정보 setting
		String[] arrCono = { cono };	//사업자번호
		
		//시스템관리자 최종 로그인 일시 update
		epcLoginDao.updateSysAdminLastLoginDate(loginId);
		
		//파트너사 session 생성
		this.getClientLoginInfo(request, epcLoginVO, arrCono);
	}
	
	/**
	 * 세션생성용 클라이언트 정보 setting
	 */
	private void getClientLoginInfo(HttpServletRequest request, EpcLoginVO epcLoginVO, String[] arrCono) throws Exception {
		String[] arrVenCds = null;		//파트너사코드
//		String[] arrVenNms = null;		//파트너사명
		String[] arrTrdTypCds = null;	//거래유형
		String[] arrTrdTypNms = null;	//거래유형명
		String[] arrZzorgs = null;		//거래조직구분
		String[] arrZzorgNms = null;	//거래조직명
		
		String loginVenNm	= "";		//로그인파트너사명
		String vendorTypeCd = "";		//업체유형
		
		//login Session 생성용 VO 없으면 생성
		if(epcLoginVO == null) epcLoginVO = new EpcLoginVO();
			
		//대표업체코드
		String repVendorId = StringUtils.defaultString(epcLoginVO.getRepVendorId());
		
		//업체 사업자번호 setting
		epcLoginVO.setCono(arrCono);
		
		//사업자번호에 해당하는 업체정보 전체 조회
		List<DataMap> infoArr = epcLoginDao.getClienLoginInfo(arrCono);
		
		//업체정보 없을 경우 Exception
		if(infoArr == null || infoArr.size() == 0) {
			throw new AlertException("올바르지않은 사업자번호 입니다.");
		}
		
		arrVenCds 		= new String[infoArr.size()];	//파트너사코드
		arrTrdTypCds 	= new String[infoArr.size()];	//거래유형
		arrTrdTypNms 	= new String[infoArr.size()];	//거래유형명
		arrZzorgs 		= new String[infoArr.size()];	//거래조직구분
		arrZzorgNms 	= new String[infoArr.size()];	//거래조직명
		
		for (int i = 0; i < infoArr.size(); i++) {
			DataMap infoMap = (DataMap) infoArr.get(i);
			
			arrVenCds[i] 	= infoMap.getString("VEN_CD", "");		//파트너사코드
			arrTrdTypCds[i] = infoMap.getString("TRD_TYP_FG", "");	//거래유형코드
			arrTrdTypNms[i] = infoMap.getString("TRD_TYP_NM", "");	//거래유형명
			arrZzorgs[i] 	= infoMap.getString("ZZORG", "");		//거래조직구분
			arrZzorgNms[i] 	= infoMap.getString("ZZORG_NM", "");	//거래조직명
			
			//마지막 data일 경우, 공통 변수로 setting
			if(i+1 == infoArr.size()) {
				loginVenNm = infoMap.getString("VEN_NM", "");		 //로그인명
				vendorTypeCd = infoMap.getString("VEN_TYPE_CD", ""); //업체유형
			}
		}
		
		epcLoginVO.setVendorId(arrVenCds);			//파트너사코드
		epcLoginVO.setTradeType(arrTrdTypCds);		//거래유형코드
		epcLoginVO.setTradeTypeNm(arrTrdTypNms);	//거래유형명
		epcLoginVO.setZzorg(arrZzorgs);				//거래조직구분
		epcLoginVO.setZzorgNm(arrZzorgNms);			//거래조직명
		
		epcLoginVO.setLoginNm(loginVenNm);			//로그인업체명
		epcLoginVO.setVendorTypeCd(vendorTypeCd);	//업체유형
		
		//대표업체코드 없을 경우, 조회된 데이터 중 첫 번째 업체 코드로 셋팅
		if("".equals(repVendorId)) {
			repVendorId = arrVenCds[0];
			epcLoginVO.setRepVendorId(repVendorId);
		}
				
		//어드민 여부 체크해서 세션에 셋팅
		String adminId = StringUtils.defaultString(epcLoginVO.getAdminId());			//관리자 아이디_SSO 로그인 or 시스템관리자 로그인 시에만 셋팅됨
		String sysAdminFg = StringUtils.defaultString(epcLoginVO.getSysAdmFg());		//시스템관리자 유형_시스템관리자 로그인 시에만 셋팅됨
		
		//어드민 여부 ---- 1: 협력사, 3: 관리자
		String adminFg = ("".equals(adminId) || "SV".equals(sysAdminFg))? "1":"3";
		epcLoginVO.setAdmFg(adminFg);
		
		//regId, modId 셋팅용 작업자 아이디 생성
		String loginWorkId = "";
		if(!"".equals(sysAdminFg)) {			//시스템관리자 (일반관리자와 구분위해 앞에 식별자 붙임)
			loginWorkId = "NEPC_"+adminId;
		}else if(!"".equals(adminId)) {			//일반관리자(MD)등
			loginWorkId = adminId;
		}else {									//파트너사
			loginWorkId = repVendorId;	
		}
		epcLoginVO.setLoginWorkId(loginWorkId);
		
		//session Key
		String sessionKey = config.getString("lottemart.epc.session.key");
		//session 생성
		request.getSession().setAttribute(sessionKey, epcLoginVO);
	}
	
	/**
	 * [협력사] 로그인 검증 및 세션 생성
	 */
	public void makeClientSessionInfo(HttpServletRequest request, Map<String, Object> paramMap) throws Exception {
		String conoArrStr  = MapUtils.getString(paramMap, "conoArrStr", "");	//사업자번호 arrayString
		String encLoginKey = MapUtils.getString(paramMap, "loginKey", "");		//암호화된 로그인 Key (sha256)
		
		/*
		 * 1. 필수데이터 체크
		 */
		//사업자번호
		if (conoArrStr == null || conoArrStr.length() < 10) {
			throw new AlertException("올바르지 않은 사업자번호 입니다.");
		}
		
		//로그인 key
		if("".equals(encLoginKey)) {
			throw new AlertException("올바르지 않은 인증정보 입니다.");
		}
		
		//사업자번호 배열로 split
		String[] arrCono = conoArrStr.split(";");
		if (arrCono == null || arrCono.length < 1) {
			throw new AlertException("사업자번호가 존재하지 않습니다.");
		}
		
		//사업자번호 유효성체크
		for(String conoStr : arrCono) {
			//사업자번호 공백제거
			conoStr = (conoStr != null)? StringUtil.removeWhitespace(conoStr) : "";
			
			//사업자번호 10자리가 아닌 경우
			if(conoStr.length() != 10) {
				throw new AlertException("사업자번호의 길이가 올바르지 않습니다. (사업자번호 : "+conoStr+")");
			}
		}
		
		/*
		 * 2. 로그인 키 유효성 체크 (사업자번호 - key 검증)
		 */
		//첫번째 사업자번호 -> lcn에서 일부 업체가 지점별로 사업자번호가 다른경우가 존재함 (부속사업자번호) ==> parameter로 넘어온 첫 번째 사업자번호를 기준으로 key 체크함 
		String repCono = arrCono[0];
		logger.info("first bmanNo:::" + repCono);
		
		//첫번째 사업자번호로 등록된 key 정보 확인 (유효시간 내의 KEY만 조회)
		DataMap keyInfo = epcLoginDao.selectVendorLoginKeyInfo(repCono);
		
		if(keyInfo == null) {
			throw new AlertException("로그인 인증정보가 만료되었거나 유효하지 않습니다.");
		}
		
		String savedKey = keyInfo.getString("LOGIN_KEY", "");				//저장된 업체의 로그인 key
		logger.debug("encLoginKey :::"+encLoginKey);
		logger.debug("savedKey :::"+savedKey);
		
		//KEY 일치 여부 체크	
		if(!(encLoginKey.trim()).equals(savedKey)) {
			throw new AlertException("로그인 인증정보 검증에 실패하였습니다.");
		}
		
		/*
		 * 3. 파트너사 정보 동기화 (HQ_VEN > TVE_VENDOR)
		 */
		//로그인업체 정보 동기화 (사업자번호별)
		for(String conoStr:arrCono) {
			this.updateMergeHqVenToTveVendorInLogin(conoStr);
		}

		/*
		 * 4. 파트너사 session 생성
		 */
		//파트너사 session 생성
		EpcLoginVO epcLoginVO = new EpcLoginVO();
		this.getClientLoginInfo(request, epcLoginVO, arrCono);
		
	}
	
	/**
	 * 업체 로그인 시, 해당 업체 정보 HQ_VEN > TVE_VENDOR 동기화
	 * @param cono
	 * @throws Exception
	 */
	private void updateMergeHqVenToTveVendorInLogin(String cono) throws Exception{
		logger.info("================== Start RealTime - MERGE TVE_VENDOR ====================");
		logger.info("cono :::::"+cono);
    	Map<String,Object> paramMap = new HashMap<String,Object>();
    	paramMap.put("BMAN_NO", cono);
    	
    	JSONObject jo = new JSONObject(paramMap);
    	String paramStr = jo.toString();
    	
    	String startDt = "";	//배치 실행시작시간
    	String endDt = "";		//배치 실행종료시간
    	String batchDur = "";	//배치 소요시간
    	int reccnt = 0;			//배치처리건수
    	
    	String msgCd = "S";		//업무 결과코드
    	
    	//동기화 시작시간
    	startDt = DateUtil.getCurrentTime(DateUtil.TIMESTAMP_FORMAT);
    	try {
    		//merge
    		reccnt = epcLoginDao.updateMergeHqVenToTveVendor(paramMap);
    	}catch(Exception e) {
    		msgCd = "F";
    		logger.error("I/F updateMergeHqVenToTveVendor MERGE EXCEPTION " + e.getMessage());
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
			workId = "LOGIN";
			workNm = "LOGIN";
    		
			// 실행로그생성  ---------------------------------------------------*/
    		HistoryVo logVo = new HistoryVo();
    		logVo.setLogId(logId);				//배치로그아이디
    		logVo.setJobId(jobId);				//배치 JOB ID
    		logVo.setBatchType("R");			//배치유형 - M : 수동배치 / B : 자동배치 / R : 실시간
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
		logger.info("================== End RealTime - MERGE TVE_VENDOR ====================");
	}
	
	/**
	 * (스케쥴러에서만 사용!!!!)
	 * HQ_VEN > TVE_VENDOR
	 */
	public int updateMergeHqVenToTveVendor(Map<String,Object> paramMap) throws Exception{
		return epcLoginDao.updateMergeHqVenToTveVendor(paramMap);
	}
	
	/**
	 * [관리자] 로그인 세션 변경
	 */
	public void makeAdminSessionInfoChg(HttpServletRequest request, String cono) throws Exception {
		// 선택한 대표 vendorId
		String repVendorId = request.getParameter("repVendorId");
		
		/*
		 * 1. 필수데이터 체크
		 */
		//사업자번호
		if (cono == null || cono.length() != 10) {
			throw new AlertException("올바르지않은 사업자번호 입니다.");
		}
		
		/*
		 * 2. 파트너사 정보 동기화 (HQ_VEN > TVE_VENDOR)
		 */
		this.updateMergeHqVenToTveVendorInLogin(cono);
		
		/*
		 * 3. 세션데이터 변경 생성
		 */
		//로그인 세션 키
		String sessionKey = config.getString("lottemart.epc.session.key");

		//현재 로그인 세션
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);
		
		//대표 파트너사코드 셋팅
		if (repVendorId != null && repVendorId.length() > 1) epcLoginVO.setRepVendorId(repVendorId);
		
		//파트너사 정보 setting
		String[] arrCono = { cono };	//사업자번호
		
		//파트너사 session 생성
		this.getClientLoginInfo(request, epcLoginVO, arrCono);
	}
	
	/**
	 *  [관리자] 로그인 검증 및 세션 생성 (SSO로그인이후 세션생성 비밀번호 체크X)
	 */
	public void makeAdminSessionInfo(HttpServletRequest request, String loginId, String cono) throws Exception {
		// 선택한 대표 vendorId
		String repVendorId = request.getParameter("repVendorId");
		
		/*
		 * 1. 필수데이터 체크
		 */
		//아이디
		if (loginId == null || loginId.length() < 1) {
			throw new AlertException("ID가 입력되지 않았습니다.");
		}
		
		//사업자번호
		if (cono == null || cono.length() != 10) {
			throw new AlertException("올바르지않은 사업자번호 입니다.");
		}
		
		//TODO_JIA ::: 관리자용 체크로직 추가 필요 (아이디가 존재하는지)
		
		/*
		 * 2. 파트너사 정보 동기화 (HQ_VEN > TVE_VENDOR)
		 */
		this.updateMergeHqVenToTveVendorInLogin(cono);
		
		/*
		 * 3. 세션데이터 생성
		 */
		// LoginSession 생성용 VO
		EpcLoginVO epcLoginVO = new EpcLoginVO();
		
		//관리자정보 setting
		epcLoginVO.setAdminId(loginId);		//관리자 ID 셋팅
		epcLoginVO.setAdminNm(loginId);		//관리자명 셋팅
		
		//대표 파트너사코드 셋팅
		if (repVendorId != null && repVendorId.length() > 1) epcLoginVO.setRepVendorId(repVendorId);
		
		//파트너사 정보 setting
		String[] arrCono = { cono };	//사업자번호
		
		//파트너사 session 생성
		this.getClientLoginInfo(request, epcLoginVO, arrCono);
	}
}
