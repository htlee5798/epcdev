
package com.lottemart.common.login.service;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.icu.util.Calendar;
import com.lottemart.common.exception.AlertException;
import com.lottemart.common.login.controller.LoginController;
import com.lottemart.common.login.dao.LoginDao;
import com.lottemart.common.login.exception.LoginException;
import com.lottemart.common.login.model.LoginSession;
import com.lottemart.common.notice.model.NoticeVO;
//import com.lottemart.common.security.xecuredb.service.XecuredbConn;
import com.lottemart.common.sms.model.LtnewsmsVO;
import com.lottemart.common.sms.service.LtsmsService;
import com.lottemart.common.util.DataMap;
import com.lottemart.utils.notifier.KakaoTalkNotifier;
import com.lottemart.utils.notifier.NotifierFactory;
import com.lottemart.utils.notifier.NotifierType;
import com.lottemart.utils.notifier.typehandlers.ServerType;
import com.lottemart.utils.notifier.typehandlers.ServiceAppType;

import Nets.Security.TripleDes.TripleDESCrypt;
import lcn.module.common.conts.ConstanceValue;
import lcn.module.common.util.StringUtil;
import lcn.module.framework.property.ConfigurationService;

@Service("loginService")
public class LoginService {
	@Autowired
	private LoginDao loginDao;
	@Autowired
	private LtsmsService ltsmsService;
	
	@Resource(name = "commonNotifier")
	private NotifierFactory notifierFactory;

//	/** XecureService 선언*/
//    @Resource(name = "xecuredb")
//    private XecuredbConn xecuredbConn;
    
    @Autowired
	private ConfigurationService config;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	/**
	 * 메인화면 - 로그인
	 * Desc :
	 * @Method Name : login
	 * @param request, login_id, password
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public void login(HttpServletRequest request, String login_id, String password, String sysDivnCd, String empno, String jobDivnCd1 ) throws Exception {

		//boolean isMoblie = BooleanUtils.toBooleanObject(ObjectUtils.toString(request.getAttribute("_isMoblie"),"false"));

		logger.info("request.getRemoteAddr() : "+request.getRemoteAddr());
		
		String tmpPassword = password;
		String serverType = System.getProperty("server.type","local");
		logger.debug("server.type=>"+serverType); //mig

		// 2017.11.21 이지원 cc 로그인 시 암호화되어 넘어온 비밀번호를 복호화한다.- 김다인 대리 요청
		if(ConstanceValue.SYSTEM_DIVN_CC.equals(sysDivnCd)){
			byte[] decoded = Base64.decodeBase64(password);
			tmpPassword = new String(decoded);
		}

		String inLmartEmpNo = empno;
		String SSOTime      = request.getParameter("SSOTime");
		String jobDivnCd 	= jobDivnCd1;
		String affiliate    = "BOS"; //midas , bos 유입경로
		String msg_log      = ""; //로그인 성공, 실패 사유
		String currentUuid		= StringUtil.null2str((String) request.getAttribute("uuid"));			// 현재 UUID

		DataMap loginInfo = null;
		
		DataMap ssoPassInfo = loginDao.selectSsoPassInfo();
		
		boolean isSSO = false;

		logger.debug("1_inLmartEmpNo=>"+inLmartEmpNo+"SSOTime=>"+SSOTime); //mig
		// 일반 아이디/비밀번호 로그인
		if (inLmartEmpNo == null || "".equals(inLmartEmpNo)) {
			if(StringUtils.isEmpty(login_id)) {
				//if(isMoblie) throw new Exception("아이디가 없습니다");
				throw new AlertException("현재 입력하신 아이디 또는 비밀번호를 잘못 입력하셨습니다.");
			}
			if(StringUtils.isEmpty(tmpPassword)) {
				//if(isMoblie) throw new Exception("비밀번호가 없습니다");
				throw new AlertException("현재 입력하신 아이디 또는 비밀번호를 잘못 입력하셨습니다.");
			}
			if(StringUtils.isEmpty(sysDivnCd)) {
				//if(isMoblie) throw new Exception("알 수 없는 오류가 발생하였습니다.");
				throw new AlertException("알 수 없는 오류가 발생하였습니다.");
			}
			loginInfo = loginDao.getAdminLogin(login_id);
		} 
		else /* 롯데마트 SSO */ {	//20190816 마이더스 OTP 접속 장애시 로그인 체크로직 제거 (Line 114 ~ 145)
			if ("1".equals(ssoPassInfo.getString("LET_1_REF"))) {
				affiliate = "MIDAS";
				
				TripleDESCrypt crypt = new TripleDESCrypt("lottefrom2006key", "euc-kr");
				//inLmartEmpNo = crypt.hexDecrypt(inLmartEmpNo, "euc-kr");
				SSOTime      = crypt.hexDecrypt(SSOTime     , "euc-kr");
				
				logger.debug("MIDAS_inLmartEmpNo=>"+inLmartEmpNo+"SSOTime=>"+SSOTime); //mig
				// set argument
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("lmartEmpNo", inLmartEmpNo);
				paramMap.put("SSOTime"   , SSOTime);
				
				loginInfo = loginDao.getAdminLoginLotte(paramMap);
				
				// 2012.08.18 임재유 SSO 로그인을 시도 하다 실패한 경우 관리자로그인 페이지로 리다이렉션
				if(loginInfo == null){
					request.setAttribute("LoginError", "true");
					request.setAttribute("LoginErrorGbn", "1");		// 관리자 로그인 페이지로 이동
					
					if(!"mig".equals(serverType)){
						loginDao.insertLoginSessionLog(request.getSession().getId(), "login", request.getRemoteAddr(), affiliate , login_id + " : SSO로그인정보 NULL", currentUuid);
					}
					
					throw new LoginException("입력하신 아이디 또는 비밀번호를 잘못 입력하셨습니다.");
				}
				// SSO 로그인 성공
				else{
					isSSO = true;
				}
			}
		}

		if(loginInfo == null){
			//if(isMoblie) throw new Exception("등록된 사용자 ID 가 아닙니다.");
			msg_log = "입력하신 아이디 또는 비밀번호를 잘못 입력하셨습니다.";

			logger.debug("loginInfo_inLmartEmpNo=>"+inLmartEmpNo+"SSOTime=>"+SSOTime);
			if(!"mig".equals(serverType)){
				loginDao.insertLoginSessionLog(request.getSession().getId(), "login", request.getRemoteAddr(), affiliate , login_id + " : " +msg_log, currentUuid);
			}

			throw new AlertException(msg_log);
		}
		
		// ----------------------------------------------------------------
		// 사용자의 로그인 정보는 session 에 담아놓는다
		// ----------------------------------------------------------------
		String	adminId			= loginInfo.getString("ADMIN_ID"         );			//	관리자ID
		String	adminNm			= loginInfo.getString("ADMIN_NM"         );			//	관리자명
		String	loginId			= loginInfo.getString("LOGIN_ID"         );			//	로그인ID
		String	pwd			    = loginInfo.getString("PWD"              );			//	비밀번호
		String	lmartEmpNo		= loginInfo.getString("LMART_EMP_NO"     );			//	롯데마트사원번호
		String	adminTypeCd		= loginInfo.getString("ADMIN_TYPE_CD"    );			//	관리자유형코드(SM032)
		String	email			= loginInfo.getString("EMAIL"            );			//	이메일
		String	strCd			= loginInfo.getString("STR_CD"           );			//	점포코드
		int		pwdChangeDate	= 0;
		if (inLmartEmpNo == null || "".equals(inLmartEmpNo)) {
				pwdChangeDate	= loginInfo.getInt("PWD_CHANGE_DATE");				//	비밀번호변경일시
		}
		String	activeYn		= loginInfo.getString("ACTIVE_YN"        );			//	활성화여부
		String	idnoReadYn		= loginInfo.getString("IDNO_READ_YN"     );			//	주민번호열람여부
		String	cardnoReadYn	= loginInfo.getString("CARDNO_READ_YN"   );			//	카드번호열람여부
		String	vaccntNoReadYn	= loginInfo.getString("VACCNT_NO_READ_YN");			//	가상계좌번호열람여부
		String	replyDivnCd		= loginInfo.getString("REPLY_DIVN_CD"    );			//	답변자구분코드
		String  cellAuthDate	= loginInfo.getString("CELL_AUTH_DATE"   );			//	휴대폰인증일시
		String  authDate		= loginInfo.getString("AUTH_DATE"    	 );			//	인증번호 보낸 날짜 비교용
		int     authAfterTime	= loginInfo.getInt("AUTH_AFTER_TIME"  );			//	인증번호 보낸 시간 30분이내 비교용
		String  pwdErrorDy		= loginInfo.getString("PWD_ERROR_DY"   );			//	비밀번호오류일자
		int	    pwdErrorCnt	    = loginInfo.getInt("PWD_ERROR_CNT"       );			//	비밀번호오류수
		String	pickerOrderSeq	= loginInfo.getString("PICKER_ORDER_SEQ" );			//	피커정렬순번
		String  today           = loginInfo.getString("TO_DATE"          );			//  인증체크를 위한 날짜

		String  pickerYn		= loginInfo.getString("PICKER_YN"          );  		//  피커 role_id
		String  deptCd			= loginInfo.getString("DEPT_CD");  		//  부서코드
		
		/* 2020-02-17 바로배송 추가 */
		String uuid						= StringUtil.null2str(loginInfo.getString("UUID"),"");						// 단말ID
		String lastLoginDate		= StringUtil.null2str(loginInfo.getString("LAST_LOGIN_DATE"),"");		// 마지막 로그인 일자
		String qpsPickerType			= StringUtil.null2str(loginInfo.getString("QPS_PICKER_TYPE"), "00");	// 피커구분 (10:바로배송, 20:예약, 00:피커가 아님)
		String qpsPickerWorkYn	= StringUtil.null2str(loginInfo.getString("QPS_PICKER_WORK_YN"), "N");	// qps got picker 작업 가능 여부
		/* //2020-02-17 바로배송 추가 */

		String  ssoDivnCd	    = "";
		
		if ("1".equals(ssoPassInfo.getString("LET_1_REF"))) {
			if(isSSO){		//20190816 마이더스 OTP 접속 장애시 로그인 체크로직 제거 (Line 192 ~ 216)
				ssoDivnCd =  ConstanceValue.SSO_DIVN_MIDAS;	// 마이다스를 통한 로그인
			}

			//GOT 로그인 시 접근권한 체크 20150325 김동준
			if ("Y".equals(request.getAttribute("gotYN"))) {
				if ("N".equals(pickerYn)) {
					msg_log = "GOT접근 권한이 없습니다.";

					if(!"mig".equals(serverType)){
						loginDao.insertLoginSessionLog(request.getSession().getId(), adminId, request.getRemoteAddr(), affiliate , loginId + " : " +msg_log, currentUuid);
					}

					throw new AlertException(msg_log);
				}
			/* 2020-02-17 바로배송 추가 */
			} else if ("Y".equals(request.getAttribute("qpsGotYn"))) {
				// qps got 접근 권한 체크
				if ("00".equals(qpsPickerType)) {
					msg_log = "QPS GOT 접근 권한이 없습니다.";

					if(!"mig".equals(serverType)){
						loginDao.insertLoginSessionLog(request.getSession().getId(), adminId, request.getRemoteAddr(), affiliate , loginId + " : " +msg_log, currentUuid);
					}

					throw new AlertException(msg_log);
				}
			/* //2020-02-17 바로배송 추가 */
			}  else {
				/*정직원의 경우 마이다스로 로그인하도록 수정*/
				if(lmartEmpNo != null && !lmartEmpNo.equals("") && ssoDivnCd.equals("")){
					msg_log = "MIDAS 로 로그인하시기 바랍니다.";
					if(!"mig".equals(serverType)){
						loginDao.insertLoginSessionLog(request.getSession().getId(), adminId, request.getRemoteAddr(), affiliate , loginId + " : " +msg_log, currentUuid);
					}
					throw new AlertException(msg_log);
				}
			}
		}

		// 활성화 여부 체크
		if(activeYn.equals("N")) {
			String message = "사용가능한 사용자 ID 가 아닙니다.";

			if (pwdErrorCnt >= 4 && (inLmartEmpNo == null || "".equals(inLmartEmpNo))) {

				request.setAttribute("LoginError", "true");
				request.setAttribute("LoginErrorGbn", "4");
				request.setAttribute("adminId", adminId);

				msg_log = "아이디 또는 비밀번호를 잘못입력하여 인증후 로그인이 가능합니다.";
				if(!"mig".equals(serverType)){
					loginDao.insertLoginSessionLog(request.getSession().getId(), adminId, request.getRemoteAddr(), affiliate , loginId + " : " +msg_log, currentUuid);
				}
				throw new LoginException(msg_log);

			}
			if(!"mig".equals(serverType)){
				loginDao.insertLoginSessionLog(request.getSession().getId(), adminId, request.getRemoteAddr(), affiliate , loginId + " : " +message, currentUuid);
			}
			throw new AlertException(message);
		}

		int PASS_ERROR_ONE_CNT = 1;
		int checkErrorCnt = PASS_ERROR_ONE_CNT + pwdErrorCnt;
		Map<String, String> paramMap = new HashMap<String, String>();

		// 일반로그인 사용자에 대한 체크
		if (inLmartEmpNo == null || "".equals(inLmartEmpNo)) {

			// 비밀번호의 길이는 10자리 이상이어야 한다. (비밀번호가 일치했지만 비밀번호의 길이가 8자리 미만인 경우)
			/*if (pwd.length() < 10 && password.equals(pwd)) {
				request.setAttribute("LoginError", "true");
				request.setAttribute("LoginErrorGbn", "3");
				request.setAttribute("adminId", adminId);

				msg_log = "현재 사용하시는 패스워드가 10자리 미만입니다. 패스워드를 10자리 이상으로 변경바랍니다.";
				loginDao.insertLoginSessionLog(request.getSession().getId(), adminId, request.getRemoteAddr(), affiliate , loginId + " : " +msg_log);
				throw new LoginException(msg_log);

			}*/

			// 인증받은지 30분이내에는 인증후 로그인이 가능하다.
			if(today.equals(authDate) && authAfterTime > -30){
				request.setAttribute("LoginError", "true");
				request.setAttribute("LoginErrorGbn", "4");
				request.setAttribute("adminId", adminId);
				//if(isMoblie) throw new Exception("관리자에게 인증번호를 받은지 30분 이내이므로  인증후 로그인이 가능합니다.");

				msg_log = "관리자에게 인증번호를 받은지 30분 이내이므로  인증후 로그인이 가능합니다.";
				if(!"mig".equals(serverType)){
					loginDao.insertLoginSessionLog(request.getSession().getId(), adminId, request.getRemoteAddr(), affiliate , loginId + " : " +msg_log, currentUuid);
				}
				throw new LoginException(msg_log);
			}

//			logger.debug("password before=>"+password);
			//정직원(사번존재) 확인하여 비밀번호 암호화 방식 결정 - 포털계정은 SHA256, 로컬계정은 Hash(md5+sha256) - 2016.04.22 추가
			if(lmartEmpNo != null && !("").equals(lmartEmpNo)) {
//				tmpPassword = xecuredbConn.encrptBysha256(tmpPassword);
//				logger.debug("password1=>"+password);
			} else {
//				tmpPassword = xecuredbConn.hash(tmpPassword);
//				logger.debug("password2=>"+password);
			}

//			logger.debug("************************************************************************************************");
//			logger.debug("lmartEmpNo=>"+lmartEmpNo);
//			logger.debug("password=>"+password);
//			logger.debug("pwd=>"+pwd);
//			logger.debug("************************************************************************************************");

			// 비밀번호가 일치하지 않을시
			if (!tmpPassword.equals(pwd)) {

				// 비밀번호 오류횟수 4회이상일시
				if (pwdErrorCnt >= 4) {
					request.setAttribute("LoginError", "true");
					request.setAttribute("LoginErrorGbn", "4");
					request.setAttribute("adminId", adminId);

					msg_log = "아이디 또는 비밀번호를 잘못입력하여 인증후 로그인이 가능합니다.";
					if(!"mig".equals(serverType)){
						loginDao.insertLoginSessionLog(request.getSession().getId(), adminId, request.getRemoteAddr(), affiliate , loginId + " : " +msg_log, currentUuid);
					}
					throw new LoginException(msg_log);
				} else {
					//같은 날짜 로그인 비밀번호 에러 체크 1씩 증가
					if (today == pwdErrorDy || today.equals(pwdErrorDy)) {
						paramMap.put("adminId", adminId);
						paramMap.put("errorCnt", (PASS_ERROR_ONE_CNT + pwdErrorCnt) + "");

						// 동일일자 비밀번호 오류횟수 4회이상일시 ACTIVE_YN = 'N'으로 수정
						if(checkErrorCnt > 3){
							paramMap.put("activeYn" , "N");
						}else{
							paramMap.put("activeYn" , "Y");
						}


					// 다른 날짜 로그인 비밀번호 에러  체크 1부터 시작
					} else if(today != pwdErrorDy || !today.equals(pwdErrorDy)) {
						paramMap.put("adminId", adminId);
						paramMap.put("errorCnt", PASS_ERROR_ONE_CNT + "");
						paramMap.put("activeYn" , "Y");
					}

					// 비밀번호 에러카운트, 에러날짜 ACTIVE_YN 업데이트
					loginDao.updateLoginErrorCnt(paramMap);

					if(checkErrorCnt > 3){
						//담당자와 해당로그인 관리자 휴대폰 정보 가져옴
						paramMap.put("adminId", adminId);
						List<DataMap> resultList = loginDao.getAdMobileInfo(paramMap);

						int size = resultList.size();

						for(int i = 0; i < size; i++) {
							DataMap resultMap = resultList.get(i);

							String cellNo = StringUtils.defaultIfEmpty(resultMap.getString("CELL_NO"), "");

							if (cellNo != null && !cellNo.equals("")) {
								
								ServerType onServerType = "prd".equalsIgnoreCase(System.getProperty("server.type"))?ServerType.PROD:ServerType.STG;
								
								String kakoaTmplGrpCode=  "lmcc0010";
								
								Map<String, String> contentDataMap = new HashMap<String, String>();
								contentDataMap.put("LOGIN_ID", loginId);
								
								KakaoTalkNotifier kkoNotifier = (KakaoTalkNotifier) notifierFactory.getNotifier(NotifierType.KAKAOTALK);
								
								ServiceAppType serviceAppType;
								if(ConstanceValue.SYSTEM_DIVN_CC.equals(sysDivnCd)){
									serviceAppType = ServiceAppType.CALLCENTER;
								}else if(ConstanceValue.SYSTEM_DIVN_PP.equals(sysDivnCd)) {
									serviceAppType = ServiceAppType.PARTNER;
								}else {
									serviceAppType = ServiceAppType.BACKOFFICE;
								}
								
								String failUrl = config.getString("console.base.url") + "/bos/sms/kakaotalk_failover/send_sms.do";
								String failUrlParam = "?smsType=COMMON_LOGIN_SEND";
								failUrlParam += "&kakao_tmpl_code=" + kakoaTmplGrpCode;
								failUrlParam += "&service_channel=" + serviceAppType.getCode();
								failUrlParam += "&admin_id=" + adminId;
								failUrlParam += "&LOGIN_ID=" + loginId;
								
								try {
									kkoNotifier.send(cellNo.trim(), "1577-2500", kakoaTmplGrpCode, failUrl + failUrlParam, serviceAppType, contentDataMap, null, null, onServerType);
								}catch(Exception e) {
									logger.error(e.getMessage(), e);
								}
							}
						}
					}

//					msg_log = "비밀번호가 틀립니다. " + (PASS_ERROR_ONE_CNT + pwdErrorCnt) + "회 오류 입니다.";
					msg_log = "입력하신 아이디 또는 비밀번호를 잘못 입력하셨습니다.";
					if(!"mig".equals(serverType)){
						loginDao.insertLoginSessionLog(request.getSession().getId(), adminId, request.getRemoteAddr(), affiliate , loginId + " : " +msg_log, currentUuid);
					}
					throw new AlertException(msg_log);

				}
			} else /* 비밀번호 일치할 경우 */{
				// 비밀번호 에러카운트 초기화
				paramMap.put("adminId", adminId);
				paramMap.put("errorCnt", "0");
				if(!"mig".equals(serverType)){
					loginDao.initPwdErrorCount(paramMap);
				}
			}
			
			/* 2020-02-17 바로배송 추가 */
			String loginCheckPassYn = StringUtil.null2str((String) request.getParameter("loginCheckPassYn"), "N");
			String qpsGotYn = StringUtil.null2str((String) request.getAttribute("qpsGotYn"), "N");
			if("Y".equals(qpsGotYn) && "N".equals(loginCheckPassYn)) {
				// 바로배송이면서 중복로그인 alert 노출 전일 경우
				
				if(!StringUtil.isEmpty(lastLoginDate) && lastLoginDate.length() >= 8) {
					lastLoginDate = lastLoginDate.substring(0, 8);
				}
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
				String currentDate = sdf.format(Calendar.getInstance().getTime());
				if(!StringUtil.isEmpty(uuid)
					&& !"".equals(uuid)
					&& !uuid.equals(StringUtil.null2str((String) request.getAttribute("uuid"),""))
					&& currentDate.equals(lastLoginDate)) {
					request.setAttribute("otherLoginYn", "Y");
					throw new AlertException("입력하신 아이디 현재 사용 중입니다.\n해당 아이디로 로그인하시겠습니까?");
				}
			}
			/* //2020-02-17 바로배송 추가 */
			
			/* ***************************************************
			 * 2012.06.07 jylim(임재유) - 비밀번호 만료 규칙 변경
			 * ***************************************************
			 *   1. 80일 경과 시,   비밀번호변경 유도 메시지만 띄우고 로그인 가능
			 *   2. 90일  이상경과 시, 비밀번호변경 페이지로 이동하고 변경해야만 로그인 가능
			 *   3. 500일 이상경과 시, 비밀번호 초기화를 통한 메세지일수 있다.
			 */
			
			//비밀번호만료, 비밀번호 초기화
			if ( "Y".equals(request.getAttribute("gotYN")) || (lmartEmpNo == null || "".equals(lmartEmpNo)) || "Y".equals(request.getAttribute("qpsGotYn"))) {
				if(pwdChangeDate <= -90   && pwdChangeDate > -100){
					request.setAttribute("LoginError", "true");
					request.setAttribute("LoginErrorGbn", "3");
					request.setAttribute("adminId", adminId);

					if(pwdChangeDate > -500){
						msg_log = "비밀번호 변경일이 90일이 경과하였습니다 비밀번호를 변경하셔야 로그인이 가능합니다.";
					}else{
						msg_log = "비밀번호를 재설정 하셔야 로그인이 가능합니다.";
					}
					if(!"mig".equals(serverType)){
						loginDao.insertLoginSessionLog(request.getSession().getId(), adminId, request.getRemoteAddr(), affiliate , loginId + " : " +msg_log, currentUuid);
					}
					throw new LoginException(msg_log);
				}
			}
//			if(pwdChangeDate <= -100){
//				request.setAttribute("LoginError", "true");
//				request.setAttribute("LoginErrorGbn", "4");
//				request.setAttribute("adminId", adminId);
//				throw new LoginException("비밀번호 변경일이 100일이 경과하였습니다. 인증후 로그인이 가능합니다.");
//			}
			//DataMap rikeInfo = loginDao.getAdminLogin(login_id);
		}
		
		LoginSession loginSession = new LoginSession();

		//lmartEmpNo = "T17020019";


		loginSession.setAdminId  			(adminId		);
		loginSession.setAdminNm  			(adminNm		);
		loginSession.setLoginId  			(loginId		);
		loginSession.setLmartEmpNo			(lmartEmpNo		);
		loginSession.setAdminTypeCd			(adminTypeCd	);
		loginSession.setDeptCd			(deptCd	);
		loginSession.setEmail				(email			);
		loginSession.setStrCd		        (strCd			);
		loginSession.setIdnoReadYn		    (idnoReadYn		);
		loginSession.setCardnoReadYn		(cardnoReadYn	);
		loginSession.setVaccntNoReadYn		(vaccntNoReadYn);
		loginSession.setReplyDivnCd         (replyDivnCd	);
		loginSession.setPickerOrderSeq      (pickerOrderSeq );
		loginSession.setSysDivnCd           (sysDivnCd 		);
		loginSession.setSsoDivnCd           (ssoDivnCd		);
		loginSession.setJobDivnCd           (jobDivnCd		);
		
		/* 2020-02-17 바로배송 추가 */
		loginSession.setQpsPickerType(qpsPickerType);
		loginSession.setQpsPickerWorkYn(qpsPickerWorkYn);
		loginSession.setLastLoginDate(lastLoginDate);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd aa hh:mm:ss", Locale.KOREA);
		loginSession.setLoginDateStr(sdf.format(System.currentTimeMillis()));
		/* //2020-02-17 바로배송 추가 */

		/*2020-12-01 세미다크 추가*/
		String semidarkYn = StringUtil.null2str(loginDao.getSemidarkYn(strCd),"N");//세미다크 지점여부 조회
		loginSession.setSemidarkYn(semidarkYn);
		/*2020-12-01 세미다크 추가*/

		request.getSession().setAttribute(LoginSession.LOTTEMART_BOS_LOGIN_SESSION_KEY, loginSession);

		// 로그인 로그 기록
		if(!"mig".equals(serverType)){
			loginDao.insertLoginSessionLog(request.getSession().getId(), loginSession.getAdminId(), request.getRemoteAddr(), affiliate , "성공", currentUuid);
		}

		paramMap.put("adminId", adminId);
		paramMap.put("custLoginYn", "Y");
		// 고객로그인여부 Y
		if(!"mig".equals(serverType)){
			loginDao.updateCustLoginYn(paramMap);
		}


		//비밀번호만료
		if ( "Y".equals(request.getAttribute("gotYN")) || (lmartEmpNo == null || "".equals(lmartEmpNo)) || "Y".equals(request.getAttribute("qpsGotYn"))) {
			if (pwdChangeDate <= -80 && pwdChangeDate > -90) {

				//if(isMoblie) throw new Exception("비밀번호 변경일이 80일이 경과하였습니다. 비밀번호를 변경하시기 바랍니다.");

				request.setAttribute("loginError", "true");
				request.setAttribute("LoginErrorGbn", "2");
				request.setAttribute("adminId", adminId);
				//request.setAttribute("alertMessage", "비밀번호 변경일이 80일 이상 경과하였습니다.                               비밀번호를 변경하시기 바랍니다.                                            [BOS]-[시스템관리]-[사용자권한관리]-[관리자관리] 본인 ID로 조회 후 변경");
				request.setAttribute("alertMessage", "비밀번호 변경일이 80일 이상 경과하였습니다.\\n비밀번호를 변경하시기 바랍니다.\\n[지금 변경하시겠습니까?]");
			}
		}
		
		/* 2020-02-17 바로배송 추가 */
		/*
		 * qpsgot 마지막 로그인 및 uuid 업데이트
		 */
		String qpsGotYn = StringUtil.null2str((String) request.getAttribute("qpsGotYn"), "N");
		if("Y".equals(qpsGotYn)) {
			DataMap loginMap = new DataMap();
			loginMap.put("loginId", login_id);
			loginMap.put("uuid", StringUtil.null2str((String) request.getAttribute("uuid")));
			loginMap.put("lastLoginYn", "Y");
			
			loginDao.updatePickerInfo(loginMap);
		}
		/* //2020-02-17 바로배송 추가 */
	}

	/**
	 * 메인화면 - 사설인증 로그인
	 * Desc :
	 * @Method Name : loginOtp
	 * @param request, login_id
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public void extLogin(HttpServletRequest request, String login_id, String sysDivnCd ) throws Exception {

		String jobDivnCd 	= request.getParameter("jobDivnCd");
		String affiliate    = "BOS"; //midas , bos 유입경로
		String msg_log      = ""; //로그인 성공, 실패 사유

		logger.info("request.getRemoteAddr() : "+request.getRemoteAddr());
		
		DataMap loginInfo = null;

		if(StringUtils.isEmpty(login_id)) {
			throw new AlertException("현재 입력하신 아이디를 잘못 입력하셨습니다.");
		}

		if(StringUtils.isEmpty(sysDivnCd)) {
			throw new AlertException("알 수 없는 오류가 발생하였습니다.");
		}

		loginInfo = loginDao.getAdminLogin(login_id);

		if(loginInfo == null){
			msg_log = "입력하신 아이디를 잘못 입력하셨습니다.";
			loginDao.insertLoginSessionLog(request.getSession().getId(), "login", request.getRemoteAddr(), affiliate , login_id + " : " +msg_log, "");
			throw new AlertException(msg_log);
		}

		// ----------------------------------------------------------------
		// 사용자의 로그인 정보는 session 에 담아놓는다
		// ----------------------------------------------------------------
		String	adminId			= loginInfo.getString("ADMIN_ID"         );			//	관리자ID
		String	adminNm			= loginInfo.getString("ADMIN_NM"         );			//	관리자명
		String	loginId			= loginInfo.getString("LOGIN_ID"         );			//	로그인ID
		String	pwd			    = loginInfo.getString("PWD"              );			//	비밀번호
		String	lmartEmpNo		= loginInfo.getString("LMART_EMP_NO"     );			//	롯데마트사원번호
		String	deptCd		= loginInfo.getString("deptCd");			//	롯데마트사원번호
		String	adminTypeCd		= loginInfo.getString("ADMIN_TYPE_CD"    );			//	관리자유형코드(SM032)
		String	email			= loginInfo.getString("EMAIL"            );			//	이메일
		String	strCd			= loginInfo.getString("STR_CD"           );			//	점포코드
		String	activeYn		= loginInfo.getString("ACTIVE_YN"        );			//	활성화여부
		String	idnoReadYn		= loginInfo.getString("IDNO_READ_YN"     );			//	주민번호열람여부
		String	cardnoReadYn	= loginInfo.getString("CARDNO_READ_YN"   );			//	카드번호열람여부
		String	vaccntNoReadYn	= loginInfo.getString("VACCNT_NO_READ_YN");			//	가상계좌번호열람여부
		String	replyDivnCd		= loginInfo.getString("REPLY_DIVN_CD"    );			//	답변자구분코드
		String	pickerOrderSeq	= loginInfo.getString("PICKER_ORDER_SEQ" );			//	피커정렬순번

		// 활성화 여부 체크
		if(activeYn.equals("N")) {
			String message = "사용가능한 사용자 ID 가 아닙니다.";

			loginDao.insertLoginSessionLog(request.getSession().getId(), adminId, request.getRemoteAddr(), affiliate , loginId + " : " +message, "");
			throw new AlertException(message);
		}

		LoginSession loginSession = new LoginSession();

		loginSession.setAdminId  			(adminId		);
		loginSession.setAdminNm  			(adminNm		);
		loginSession.setLoginId  			(loginId		);
		loginSession.setLmartEmpNo			(lmartEmpNo		);
		loginSession.setDeptCd			(deptCd		);
		loginSession.setAdminTypeCd			(adminTypeCd	);
		loginSession.setEmail				(email			);
		loginSession.setStrCd		        (strCd			);
		loginSession.setIdnoReadYn		    (idnoReadYn		);
		loginSession.setCardnoReadYn		(cardnoReadYn	);
		loginSession.setVaccntNoReadYn		(vaccntNoReadYn);
		loginSession.setReplyDivnCd         (replyDivnCd	);
		loginSession.setPickerOrderSeq      (pickerOrderSeq );
		loginSession.setSysDivnCd           (sysDivnCd 		);
		loginSession.setSsoDivnCd           (""				);
		loginSession.setJobDivnCd           (jobDivnCd		);


		request.getSession().setAttribute(LoginSession.LOTTEMART_BOS_LOGIN_SESSION_KEY, loginSession);

		// 로그인 로그 기록
		loginDao.insertLoginSessionLog(request.getSession().getId(), loginSession.getAdminId(), request.getRemoteAddr(), affiliate , "성공", "");

		Map<String, String> paramMap = new HashMap<String, String>();

		paramMap.put("adminId", adminId);
		paramMap.put("custLoginYn", "Y");
		// 고객로그인여부 Y
		loginDao.updateCustLoginYn(paramMap);

	}

	/**
	 *
	 * @see selectAdminCellNoInfo
	 * @Locaton    : com.lottemart.common.login.service
	 * @MethodName  : selectAdminCellNoInfo
	 * @author     : hjKim
	 * @Description : 관리자 휴대폰정보 조회
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	public DataMap selectAdminCellNoInfo(Map<String, String> paramMap) throws Exception {
		return loginDao.getAdminCellNoInfo(paramMap);
	}

	/**
	 *
	 * @see selectToDate
	 * @Locaton    : com.lottemart.common.login.service
	 * @MethodName  : selectToDate
	 * @author     : hjKim
	 * @Description : 오늘날짜 조회
	 * @return
	 * @throws Exception
	 */
	public DataMap selectToDate() throws Exception {
		return loginDao.getToDate();
	}

	/**
	 *
	 * @see seleteAdminPwdInfo
	 * @Locaton    : com.lottemart.common.login.service
	 * @MethodName  : seleteAdminPwdInfo
	 * @author     : hjKim
	 * @Description : 관리자정보 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public DataMap selectAdminPwdInfo(Map<String, String> paramMap) throws Exception {
		return loginDao.getAdminPwdInfo(paramMap);
	}

	/**
	 *
	 * @see updateAdminPwd
	 * @Locaton    : com.lottemart.common.login.service
	 * @MethodName  : updateAdminPwd
	 * @author     : hjKim
	 * @Description : 비밀번호 수정
	 * @param paramMap
	 * @throws Exception
	 */
	public void updateAdminPwd(Map<String, String> paramMap) throws Exception {
		loginDao.updateAdminPwd(paramMap);
	}

	/**
	 *
	 * @see updateAdminInfo
	 * @Locaton    : com.lottemart.common.login.service
	 * @MethodName  : updateAdminInfo
	 * @author     : hjKim
	 * @Description : 관리자정보 수정(비밀번호변경인증 처리)
	 * @param paramMap
	 * @throws SQLException
	 */
	public void updateAdminInfo(Map<String, String> paramMap) throws SQLException {
		loginDao.updateAdminInfo(paramMap);
	}

	/**
	 * 메인화면 - 로그아웃
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
		String serverType = System.getProperty("server.type","local");
		logger.debug("server.type=>"+serverType); //mig

		LoginSession loginSession = LoginSession.getLoginSession(request);

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("adminId", loginSession.getAdminId());
		paramMap.put("custLoginYn", "N");
		// 고객로그인여부 N
		if(!"mig".equals(serverType)){
			loginDao.updateCustLoginYn(paramMap);
			loginDao.updateLogoutSessionLog(request.getSession().getId());
		}

		request.getSession().removeAttribute(LoginSession.LOTTEMART_BOS_LOGIN_SESSION_KEY);
		
		/* 2020-02-17 바로배송 추가 */
		/*
		 * qpsgot 마지막 로그인 및 uuid 업데이트
		 */
		String qpsGotYn = StringUtil.null2str((String) request.getAttribute("qpsGotYn"), "N");
		if("Y".equals(qpsGotYn)) {
			DataMap loginMap = new DataMap();
			loginMap.put("loginId", loginSession.getLoginId());
			loginMap.put("uuid", "");
//			loginMap.put("lastLoginYn", "N");
			
			loginDao.updatePickerInfo(loginMap);
		}
		/* //2020-02-17 바로배송 추가 */
	}

	/**
	 * 메인화면 - 상단메뉴
	 * Desc :
	 * @Method Name : selectAdminMenuList
	 * @param request
	 * @return
	 * @throws SQLException
	 * @param
	 * @return
	 * @exception Exception
	 */
	public List<DataMap> selectAdminTopMenuList(HttpServletRequest request) throws SQLException {

		LoginSession loginSession = LoginSession.getLoginSession(request);
		List<DataMap> menuList = loginDao.getAdminTopMenuList(loginSession.getSysDivnCd(), loginSession.getAdminId());

		return menuList;
	}

	/**
	 * 메인화면 - 좌측 메뉴 뿌리기
	 * Desc :
	 * @Method Name : selectAdminMenuList
	 * @param request
	 * @return
	 * @throws SQLException
	 * @param
	 * @return
	 * @exception Exception
	 */
	public List<DataMap> selectAdminSubMenuList(HttpServletRequest request, String menuId) throws Exception {

		LoginSession loginSession = LoginSession.getLoginSession(request);

		if (loginSession == null) {
			throw new AlertException("로그인 세션이 끊겼습니다.");
		}

		List<DataMap> menuList = loginDao.getAdminSubMenuList(loginSession.getSysDivnCd(), loginSession.getAdminId(), menuId);

		return menuList;
	}

	/**
	 *
	 * @see selectNoticeList
	 * @Locaton    : com.lottemart.common.login.service
	 * @MethodName  : selectNoticeList
	 * @author     : hjKim
	 * @param paramMap
	 * @Description : 메인페이지 공지사항 목록 조회
	 * @return
	 */
	public List<DataMap> selectNoticeList(Map<String, String> paramMap) throws SQLException{

		return loginDao.selectNoticeList(paramMap);
	}

	/**
	 *
	 * @see selectPopupNoticeList
	 * @Locaton    : com.lottemart.common.login.service
	 * @MethodName  : selectPopupNoticeList
	 * @author     : hjKim
	 * @Description : 팝업공지 목록 조회
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	public List<NoticeVO> selectPopupNoticeList(Map<String, String> paramMap) throws SQLException {
		return loginDao.selectPopupNoticeList(paramMap);
	}
	
	/**
	 *
	 * @see selectPopupNoticeList
	 * @Locaton    : com.lottemart.common.login.service
	 * @MethodName  : selectPopupNoticeList
	 * @author     : hjKim
	 * @Description : 팝업공지 목록 조회
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	public DataMap selectSsoPassInfo() throws SQLException {
		return loginDao.selectSsoPassInfo();
	}
	
	/**
	 * 메인화면 서브메소드 - 상단메뉴 포함
	 * Desc :
	 * @Method Name : selectAdminMenuListRecursive
	 * @param menu
	 * @param roleId
	 * @throws SQLException
	 * @param
	 * @return
	 * @exception Exception
	 */
	//private void selectAdminMenuListRecursive(DataMap menu, String roleId) throws SQLException {
	//	List<DataMap> subMenuList = loginDao.getAdminSubMenuList(menu.getString("MENU_ID"), roleId);
	//	menu.put("subMenuList", subMenuList);
	//	if (subMenuList != null && subMenuList.size() > 0) {
	//		for (DataMap subMenu : subMenuList) {
	//			selectAdminMenuListRecursive(subMenu, roleId);
	//		}
	//	}
	//}
	//private void selectSubMenuList(DataMap menu, String roleId) throws SQLException {
	//	List<DataMap> subMenuList = loginDao.selectSubMenuList(menu.getString("MENU_ID"), roleId);
	//	menu.put("subMenuList", subMenuList);
	//	if (subMenuList != null && subMenuList.size() > 0) {
	//		for (DataMap subMenu : subMenuList) {
	//			selectSubMenuList(subMenu, roleId);
	//		}
	//	}
	//}

	public String checkPwd(DataMap dmap) throws Exception {
		String returnValue = "";

		String password_PATTERN1 = "^(?=.*[0-9]).{1,}$"; // 숫자
		String password_PATTERN2 = "^(?=.*[a-z]).{1,}$"; //소문자
		String password_PATTERN3 = "^(?=.*[A-Z]).{1,}$"; //대문자
		String password_PATTERN4 = "^(?=.*[^a-zA-Z0-9]).{1,}$"; //특수문자

		String password_PATTERN5 = "^(?=.*[A-Za-z]).{1,}$"; //영문자

		try {
			String adminTypeCd = (String) dmap.getString("adminTypeCd");
			DataMap resultMap = new DataMap();
			
			if("Y".equals(adminTypeCd)) { //TAD_ADMIN_TYPE 테이블에서 05 - (점포) Picking담당인지 조회 - 카운트로 해서 카운트가 있으면 Y
				resultMap = (DataMap)loginDao.selectPwdHist05(dmap);
			} else {
				resultMap = (DataMap)loginDao.selectPwdHist(dmap); 
			}
			
			if(resultMap.size() == 0){
				return "존재하지 않는 ID 입니다.";
			}

			if(resultMap.getInt("CNT") > 0){
				returnValue = "과거 비밀번호는 사용불가합니다.";
				return returnValue;
			}

			if((dmap.getString("stringPwd")).length() < 10){
				returnValue = "비밀번호는 10자리 이상 등록 가능합니다.";
				return returnValue;
			}
			//got 여부
			if("Y".equals(dmap.getString("gotYn"))){
				if(!Pattern.matches(password_PATTERN1, dmap.getString("stringPwd")) ||
				!Pattern.matches(password_PATTERN5, dmap.getString("stringPwd"))){
					   returnValue = "비밀번호는  숫자, 영문자 필수 입니다.";
					   return returnValue;
				}
			}
			else{
				if(!Pattern.matches(password_PATTERN1, dmap.getString("stringPwd")) ||
				   !Pattern.matches(password_PATTERN2, dmap.getString("stringPwd")) ||
				   !Pattern.matches(password_PATTERN3, dmap.getString("stringPwd")) ||
				   !Pattern.matches(password_PATTERN4, dmap.getString("stringPwd"))){
				   returnValue = "비밀번호는 특수기호, 숫자, 대문자, 소문자 필수 입니다.";
				   return returnValue;
				}
			}
			if(resultMap.getString("LOGIN_ID") != null && !resultMap.getString("LOGIN_ID").equals("") && (dmap.getString("stringPwd").toLowerCase()).indexOf(resultMap.getString("LOGIN_ID"))>-1 ){
				returnValue = "ID는 비밀번호로 불가합니다.";
				return returnValue;
			}

			if(resultMap.getString("LMART_EMP_NO") != null && !resultMap.getString("LMART_EMP_NO").equals("") && dmap.getString("stringPwd").indexOf(resultMap.getString("LMART_EMP_NO"))>-1 ){
				returnValue = "사번은 비밀번호로 불가합니다.";
				return returnValue;
			}

			if(resultMap.getString("CELL_NO") != null && !resultMap.getString("CELL_NO").equals("") && dmap.getString("stringPwd").indexOf(resultMap.getString("CELL_NO"))>-1 ){
				returnValue = "전화번호 뒤 4자리는 비밀번호로 불가합니다.";
				return returnValue;
			}

			//비밀번호 히스토리 등록
			loginDao.insertPwdHist(dmap);

		} catch (Exception e) {
			returnValue = "처리중 에러가 발생하였습니다.";
			return returnValue;
		}

		return returnValue;
	}

	//비밀번호 초기화 문자발송
	public String pwdSmsSend(DataMap dmap) throws Exception {
		// 비밀번호 랜덤 생성
		String iStr = "abcdefghijklmnopqrstuvwxyz0123456789";

		DataMap infoMap = loginDao.selectPwd(dmap);

		int index = 0;
		Character c;
		String randomPwd = "";
		boolean numFlag = false;

		//1분이후마다 발송가능토록
		if(infoMap.getInt("AUTH_AFTER_TIME") < -1){
			for (int i = 0; i < 8; i++) {
				index = (int)(Math.random() * 36);
				c = iStr.charAt(index);

				// random 문자가 숫자인 경우
				if(c >= 48 && c <= 58) {
					numFlag = true;
				}

				// 패스워드의 마지막 문자를 결정할 때 패스워드에 숫자가 하나도 포함이 안된 경우
				if(i == 7 && !numFlag) {
					i--;
					continue;
				}
				randomPwd += c;
			}

			dmap.put("cellAuthNo",randomPwd);

			//인증번호 생성 등록.
			loginDao.updateSmsSendInit(dmap);

			String cellNo = StringUtils.defaultIfEmpty(infoMap.getString("SMS_CELL_NO"), "");

			if (cellNo != null && !cellNo.equals("")) {
				
				
				ServerType onServerType = "prd".equalsIgnoreCase(System.getProperty("server.type"))?ServerType.PROD:ServerType.STG;
				
				String kakoaTmplGrpCode=  "lmcc0011";
				
				String sysDivnCd = config.getString("sysDivnCd");
				
				ServiceAppType serviceAppType;
				if(ConstanceValue.SYSTEM_DIVN_BOS.equals(sysDivnCd)){
					serviceAppType = ServiceAppType.BACKOFFICE;
				}else if(ConstanceValue.SYSTEM_DIVN_PP.equals(sysDivnCd)) {
					serviceAppType = ServiceAppType.PARTNER;
				}else {
					serviceAppType = ServiceAppType.CALLCENTER;
				}
				
				Map<String, String> contentDataMap = new HashMap<String, String>();
				contentDataMap.put("RANDOM_PWD", randomPwd);
				
				KakaoTalkNotifier kkoNotifier = (KakaoTalkNotifier) notifierFactory.getNotifier(NotifierType.KAKAOTALK);
				
				String failUrl = config.getString("console.base.url") + "/bos/sms/kakaotalk_failover/send_sms.do";
				failUrl += "?smsType=COMMON_LOGIN_SEND";
				failUrl += "&kakao_tmpl_code=" + kakoaTmplGrpCode;
				failUrl += "&service_channel=" + serviceAppType.getCode();
				failUrl += "&admin_id=" + dmap.getString("adminId");
				failUrl += "&RANDOM_PWD=" + randomPwd;
				
				kkoNotifier.send(cellNo.trim(), "1577-2500", kakoaTmplGrpCode, failUrl, serviceAppType, contentDataMap, null, null, onServerType);
				
			}

			//관리자에게 번호 발송
			List<DataMap> resultList = loginDao.getSMSAdminList(dmap);

			int size = resultList.size();
			for(int i = 0; i < size; i++) {
				DataMap resultMap = resultList.get(i);

				cellNo = StringUtils.defaultIfEmpty(resultMap.getString("CELL_NO"), "");

				if (cellNo != null && !cellNo.equals("")) {
					StringBuffer strBuffer = new StringBuffer();
					strBuffer.append(cellNo.substring(0, 4).trim()).append("-")
					         .append(cellNo.substring(4, 8).trim()).append("-")
					         .append(cellNo.substring(8, 12).trim());

					cellNo = strBuffer.toString();

					String trMsg;
					String trCallback;

					trMsg = infoMap.getString("ADMIN_TYPE") + "의 " + infoMap.getString("LOGIN_ID")+" 님에게 "+"인증번호가 발송되었습니다.";
					trCallback = "02-2145-8165";

					LtnewsmsVO sms = new LtnewsmsVO();

					sms.setTRAN_MSG(trMsg);
					sms.setTRAN_PHONE(cellNo);
					sms.setTRAN_CALLBACK(trCallback);
					sms.setTRAN_STATUS("1");
					sms.setTRAN_TYPE("4");
					sms.setTRAN_ETC2("O02");
					sms.setTRAN_ETC4("05");	// AS-IS에 있던 코드 의미?

					// 문자발송
					ltsmsService.insertNewSmsSend(sms);
				}
			}
		}else{
			return "인증번호를 발송한지 1분이후 재발송 가능합니다.";
		}
		return "정상적으로 발송되었습니다.";
	}

	public String selectAdminRoleAssign(DataMap paramMap) throws Exception {
		return loginDao.selectAdminRoleAssign(paramMap);
	}


	/**
	 * Desc : TOP 메뉴 이동시 대메뉴조회
	 * @Method Name : selectAdminTopMenuList2
	 * @param request
	 * @return
	 * @throws SQLException
	 * @param
	 * @return
	 * @exception Exception
	 */
	public List<DataMap> selectAdminTopMenuList2(HttpServletRequest request) throws SQLException {

		LoginSession loginSession = LoginSession.getLoginSession(request);
		List<DataMap> menuList = loginDao.getAdminTopMenuList(request.getParameter("sysDivnCd"), loginSession.getAdminId());

		return menuList;
	}




	/**
	 * Desc : Mobile TOP 메뉴 대메뉴조회(sso 인 경우 로그인 id로 메뉴를 가져오도록 함)
	 * @Method Name : selectAdminTopMenuList2Mobile
	 * @param request
	 * @return
	 * @throws SQLException
	 * @param
	 * @return
	 * @exception Exception
	 */
	public List<DataMap> selectAdminTopMenuList2Mobile(HttpServletRequest request) throws SQLException {

		LoginSession loginSession = LoginSession.getLoginSession(request);
		List<DataMap> menuList = loginDao.getAdminTopMenuListMobile(request.getParameter("sysDivnCd"), loginSession.getLoginId());

		return menuList;
	}



	/**
	 * Desc : TOP 메뉴 이동시 좌측메뉴조회
	 * @Method Name : selectAdminSubMenuList2
	 * @param request
	 * @param menuId
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public List<DataMap> selectAdminSubMenuList2(HttpServletRequest request, String menuId) throws Exception {

		LoginSession loginSession = LoginSession.getLoginSession(request);

		if (loginSession == null) {
			throw new AlertException("로그인 세션이 끊겼습니다.");
		}

		List<DataMap> menuList = loginDao.getAdminSubMenuList(request.getParameter("sysDivnCd"), loginSession.getAdminId(), menuId);

		return menuList;
	}

	/**
	 * Desc : Mobile TOP 메뉴 Sub 메뉴조회(sso 인 경우 loginId 로 메뉴를 가져오도록 처리)
	 * @Method Name : selectAdminSubMenuList2Mobile
	 * @param request
	 * @param menuId
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public List<DataMap> selectAdminSubMenuList2Mobile(HttpServletRequest request, String menuId) throws Exception {

		LoginSession loginSession = LoginSession.getLoginSession(request);

		if (loginSession == null) {
			throw new AlertException("로그인 세션이 끊겼습니다.");
		}

		List<DataMap> menuList = loginDao.getAdminSubMenuListMobile(request.getParameter("sysDivnCd"), loginSession.getLoginId(), menuId);

		return menuList;
	}

	public String selectOnceDayChk(DataMap paramMap) throws Exception {
		return loginDao.selectOnceDayChk(paramMap);
	}

	public String selectUnanswerCnt(DataMap paramMap) throws Exception {
		return loginDao.selectUnanswerCnt(paramMap);
	}

	public void insertAdminWorkLog(Map<String, String> paramMap) throws Exception {
		loginDao.insertAdminWorkLog(paramMap);
	}

}
