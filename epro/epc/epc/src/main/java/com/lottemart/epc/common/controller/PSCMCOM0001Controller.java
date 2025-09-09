package com.lottemart.epc.common.controller;

import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lcn.module.common.views.AjaxJsonModelHelper;
import lcn.module.framework.property.ConfigurationService;
import nets.sso.agent.web.v9.SSOUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lottemart.common.code.service.CommonCodeService;
import com.lottemart.common.exception.AlertException;
import com.lottemart.common.login.exception.LoginException;
//import com.lottemart.common.security.xecuredb.service.XecuredbConn;
import com.lottemart.epc.common.service.PSCMCOM0001Service;

/**
 * @Description : 협력사 로그인 컨트롤러
 * @Class Name :
 * @Description :
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 5. 오후 2:24:30 jschoi
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 *               </pre>
 */

@Controller
public class PSCMCOM0001Controller {
	private static final Logger logger = LoggerFactory.getLogger(PSCMCOM0001Controller.class);

	@Autowired
	private ConfigurationService config;

	@Autowired
	private PSCMCOM0001Service epcLoginService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private CommonCodeService commonCodeService;

//	/** XecureService 선언 */
//	@Resource(name = "xecuredb")
//	private XecuredbConn xecuredbConn;

	// System Properties 가져오기
	@Value("#{systemProperties['server.type'] == null ? 'local' : systemProperties['server.type']}")
	private String serverType;

	/**
	 * @Description : 관리자 로그인폼 표시
	 * @Method Name : viewEpcAdmLoginForm
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("common/viewEpcAdmLoginForm.do")
	public String viewEpcAdmLoginForm(Model model) throws Exception {
		model.addAttribute("serverType", serverType);

		return "common/epcAdmLoginForm";
	}

	/**
	 * @Description : 해피콜 로그인폼 표시
	 * @Method Name : viewEpcAdmLoginForm
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("common/viewHappyLoginForm.do")
	public String viewHappyLoginForm() throws Exception {
		return "common/happyLoginForm";
	}

	/**
	 * @Description : 제휴사 로그인폼 표시
	 * @Method Name : viewAllianceLoginForm
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("common/viewAllianceLoginForm.do")
	public String viewAllianceLoginForm() throws Exception {
		return "common/allianceLoginForm";
	}

	/**
	 * @Description : 협력사 어드민 로그인 처리
	 * @Method Name : epcAdmLogin
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
//	@RequestMapping("common/epcAdmLogin.do")
//	public String epcAdmLogin_old250710(HttpServletRequest request) throws Exception {
//		String loginId = request.getParameter("loginId");
//		String password = request.getParameter("password");
//		String cono = request.getParameter("cono");
//
//		try {
//			if (password != null && !"".equals(password)) {
////				password = xecuredbConn.hash(password);
//			}
//
//			epcLoginService.makeAdminSessionInfo(request, loginId, password, cono);
//
//		} catch (AlertException e) {
//			logger.debug("EPC ADM Login ERROR : " + e.getMessage());
//			request.setAttribute("alertMessage", e.getMessage());
//			return "common/loginAlert";
//		} catch (LoginException e) {
//			logger.debug("EPC ADM Login ERROR : " + e.getMessage());
//			request.setAttribute("alertMessage", e.getMessage());
//			return "common/loginAlert";
//		}
//
//		String domin = config.getString("login.domain.url");
//		// @4UP 추가 중복 contextPath 처리
//		String contextPath = request.getContextPath();
//		if (domin.startsWith(contextPath)) {
//			domin = domin.substring(contextPath.length());
//		}
//		return "redirect:" + domin + "/main/vendorIntro.do";
//	}

	@RequestMapping("common/epcSSOAdmLogin.do")
	public String epcSSOAdmLogin(HttpServletRequest request) throws Exception {
		String loginId = request.getParameter("loginId");
		String cono = request.getParameter("cono");

		try {

			HttpSession session = request.getSession();
			String login_id = (String) session.getAttribute("_LOGIN_ID_");
			if (!(login_id != null && login_id.equals(loginId))) {
				throw new LoginException("비정상적인 로그인 정보입니다. 정상 경로를 통해 재시도 해주세요.");
			} else {
				session.removeAttribute("_LOGIN_ID_");
			}

			epcLoginService.makeAdminSessionInfo(request, loginId, cono);

		} catch (AlertException e) {
			logger.debug("EPC ADM Login ERROR : " + e.getMessage());
			request.setAttribute("alertMessage", e.getMessage());
			return "common/loginAlert";
		} catch (LoginException e) {
			logger.debug("EPC ADM Login ERROR : " + e.getMessage());
			request.setAttribute("alertMessage", e.getMessage());
			return "common/loginAlert";
		}

		String domin = config.getString("login.domain.url");
		// @4UP 추가 중복 contextPath 처리
		String contextPath = request.getContextPath();
		if (domin.startsWith(contextPath)) {
			domin = domin.substring(contextPath.length());
		}
		return "redirect:" + domin + "/main/vendorIntro.do";
	}

	/**
	 * @Description : 협력사 어드민 로그인 처리
	 * @Method Name : epcAdmLogin
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
//	@RequestMapping("common/epcAdmLoginADMIN.do")
//	public ModelAndView epcAdmLoginTest(HttpServletRequest request) throws Exception {
//
//		String cono = request.getParameter("cono");
//		String message = messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault());
//
//		try {
//			epcLoginService.makeAdminSessionInfoChg(request, cono);
//
//			return AjaxJsonModelHelper.create("");
//		} catch (AlertException e) {
//			return AjaxJsonModelHelper.create(message);
//		} catch (LoginException e) {
//			return AjaxJsonModelHelper.create(message);
//		}
//
//	}

	/**
	 * @Description : 협력사 로그인 처리
	 * @Method Name : epcVendorLogin
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
//	@RequestMapping("common/epcVendorLogin.do")
//	public String epcVendorLogin(HttpServletRequest request) throws Exception {
//		String conoArrStr = StringUtil.removeWhitespace(request.getParameter("conoArrStr"));
//		logger.debug("conoArrStr = [" + conoArrStr + "]");
//
//		/*============== 암복화처리 원복 ===========================
//        // 복호화처리, 2016.06.15
//		String key = config.getString("lottemart.aes.Key");
//		Aes256Util aes256 = new Aes256Util(key);
//
//		String sConoArrStr =  request.getQueryString();
//		logger.debug("파라메터 = ["+sConoArrStr+"]");
//
//		// 복호화처리, 2016.06.15
//		String conoArrStr1 = aes256.aesDecode(sConoArrStr);
//		logger.debug("복호화 = ["+conoArrStr1+"]");
//
//		// 복호화한 부분에서 변수,값분리
//		String conoArrStr = conoArrStr1.substring(conoArrStr1.indexOf("=")+1, conoArrStr1.length());
//        logger.debug("conoArrStr = ["+conoArrStr+"]");
//		============================================================*/
//
//		String domin = config.getString("login.domain.url");
//		// @4UP 추가 중복 contextPath 처리
//		String contextPath = request.getContextPath();
//		if (domin.startsWith(contextPath)) {
//			domin = domin.substring(contextPath.length());
//		}
//
//		//공통코드로 관리
//		List<DataMap> codeList = commonCodeService.getCodeList("EPC02");
//		DataMap codeMap = codeList.get(0);
//
//		String url = "";
//		try {
//			boolean isSmsAuthMain =  epcLoginService.makeClientSessionInfoSms(request, conoArrStr, codeMap);
//			if (isSmsAuthMain) {
//				url = "/main/smsAuthMain.do";
//			} else {
//				url = "/main/vendorIntro.do";
//			}
//
//		} catch (AlertException e) {
//			logger.debug("EPC Vendor Login ERROR : " + e.getMessage());
//			request.setAttribute("alertMessage", e.getMessage());
//			return "common/loginAlert";
//		} catch (LoginException e) {
//			logger.debug("EPC Vendor Login ERROR : " + e.getMessage());
//			request.setAttribute("alertMessage", e.getMessage());
//			return "common/loginAlert";
//		}
//
//		return "redirect:" + domin + url;
//	}

	/**
	 * @Description : 해피콜 로그인 처리
	 * @Method Name : happyLogin
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("common/happyLogin.do")
	public String happyLogin(HttpServletRequest request) throws Exception {
		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");

		try {
//			password = xecuredbConn.hash(password);
			epcLoginService.makeHappyCallSessionInfo(request, loginId, password);
		} catch (AlertException e) {
			logger.debug("EPC Vendor Login ERROR : " + e.getMessage());
			request.setAttribute("alertMessage", e.getMessage());
			return "common/loginAlert";
		} catch (LoginException e) {
			logger.debug("EPC Vendor Login ERROR : " + e.getMessage());
			request.setAttribute("alertMessage", e.getMessage());
			return "common/loginAlert";
		}
		String domin = config.getString("login.domain.url");
		// @4UP 추가 중복 contextPath 처리
		String contextPath = request.getContextPath();
		if (domin.startsWith(contextPath)) {
			domin = domin.substring(contextPath.length());
		}
		return "redirect:" + domin + "/main/viewScmMain.do";
	}

	/**
	 * @Description : 제휴사 로그인 처리
	 * @Method Name : allianceLogin
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("common/allianceLogin.do")
	public String allianceLogin(HttpServletRequest request) throws Exception {
		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");

		try {
//			password = xecuredbConn.hash(password);
			epcLoginService.makeAllianceSessionInfo(request, loginId, password);
		} catch (AlertException e) {
			logger.debug("EPC Vendor Login ERROR : " + e.getMessage());
			request.setAttribute("alertMessage", e.getMessage());
			return "common/loginAlert";
		} catch (LoginException e) {
			logger.debug("EPC Vendor Login ERROR : " + e.getMessage());
			request.setAttribute("alertMessage", e.getMessage());
			return "common/loginAlert";
		}
		String domin = config.getString("login.domain.url");
		// @4UP 추가 중복 contextPath 처리
		String contextPath = request.getContextPath();
		if (domin.startsWith(contextPath)) {
			domin = domin.substring(contextPath.length());
		}
		return "redirect:" + domin + "/main/viewScmMain.do";
	}

	/**
	 * @Description : 로그아웃
	 * @Method Name : logout
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("common/epcLogout.do")
	public String epcAdmLogout(HttpServletRequest request) throws Exception {
		try {
			epcLoginService.logout(request);
		} catch (Exception e) {
			logger.debug("EPC logout ERROR : " + e.getMessage());
		}

		return "redirect:/index.jsp";
	}

	/**
	 * @Description : SSO 로그인폼 표시
	 * @Method Name : viewEpcAdmLoginForm
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	/*@RequestMapping("common/viewSSOEpcAdmLoginForm.do")
	public String viewSSOEpcAdmLoginForm(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

		String logonUrl = ""; // 로그온 URL
		String logoffUrl = ""; // 로그오프 URL
		String returnUrl = ""; // 되돌아 올 URL
		boolean isLogon = false; // 로그온 여부
		String logonId = ""; // 로그온 된 사용자 아이디
		String userInfo = ""; // 로그온 된 사용자 추가정보
		String errorMsg = ""; // 에러 메시지
		String idTagName = ""; // 아이디 태그명
		String pwdTagName = ""; // 비밀번호 태그명
		String credTagName = ""; // CredentialType 태그명
		String returnUrlTagName = ""; // 되돌아갈 URL 태그명

		HttpSession session = request.getSession();
		// AuthStatus.SSOFirstAccess : -1
		// AuthStatus.SSOSuccess : 0
		// AuthStatus.SSOFail : -2
		// AuthStatus.SSOUnAvailable : -3
		// AuthStatus.SSOAccessDenied : -4

		try {
			// 인증객체생성 및 인증확인
			AuthCheck auth = new AuthCheck(request, response);
			// 인증확인
			AuthStatus status = auth.checkLogon();
			model.addAttribute("status", status);

			// 인증상태별 처리
			if (status == AuthStatus.SSOFirstAccess) {
				// 최초 접속
				auth.trySSO();
				// errorMsg = "최초인증";
			} else if (status == AuthStatus.SSOSuccess) {
				// 인증성공

				returnUrl = request.getParameter(auth.getSsoProvider().getReturnUrlTagName());

				if (!Utility.isNullOrEmpty(returnUrl)) {
					response.sendRedirect(returnUrl);
				}
				isLogon = true;

				// 하기 인증정보를 이용하여, 내부인증을 만들어 사용하세요.
				// 로그인 아이디
				logonId = auth.getUserID();

				// 인증정보 모두 보기(화면에서 보고 싶을 때 주석을 제거 하세요)
				if (auth.getUserInfoCollection() != null && auth.getUserInfoCollection().size() > 0) {
					for (Enumeration<String> e = auth.getUserInfoCollection().keys(); e.hasMoreElements();) {
						if (!Utility.isNullOrEmpty(userInfo))
							userInfo += "<br />";
						String key = (String) e.nextElement();
						userInfo += key + ":" + auth.getUserInfoCollection().get(key);
						if ("login_id".equals(key)) {
							String loginId = auth.getUserInfoCollection().get(key);
							model.addAttribute("loginId", loginId);
							session.setAttribute("_LOGIN_ID_", loginId);
						}
					}
				}

				// 선입자를 끊고, 내가 인증 성공했을 경우, 선입자의 정보를 보여준다.
				if (!Utility.isNullOrEmpty(auth.getDuplicationIP())) {
					String dupInfo = "(끊어진 사용자정보)\\nIP:" + auth.getDuplicationIP() + "\\nTime:" + auth.getDuplicationTime();
					errorMsg = dupInfo;
				}
				logger.debug("주루루룩 : logonId - " + logonId);

				//사용자정보 조회 샘플
				//String somethingUserInfo = auth.getUserInfo("조회할이름");	//조회할 이름은 프로젝트 시, 정해지면 전달해드립니다.
				//errorMsg = "인증성공";
			} else if (status == AuthStatus.SSOFail) {
				// 인증실패
				// errorMsg="인증실패";
				if (auth.getErrCode() != AgentExceptionCode.NoException) {
					errorMsg = "alertError('" + auth.getErrCode().toString() + "', '');";
				}
				if (auth.getErrCode() == AgentExceptionCode.SessionDuplicationCheckedFirstPriority ||
						auth.getErrCode() == AgentExceptionCode.SessionDuplicationCheckedLastPriority){
					errorMsg = "중복로그인 정보 IP:" + auth.getDuplicationIP() + " TIME:" + auth.getDuplicationTime();
				}
				// 로그오프를 해야하는 상황
				if (	auth.getErrCode() == AgentExceptionCode.SessionDuplicationCheckedLastPriority ||
						auth.getErrCode() == AgentExceptionCode.TokenIdleTimeout ||
						auth.getErrCode() == AgentExceptionCode.TokenExpired ||
						auth.getErrCode() == AgentExceptionCode.NoExistUserIDSessionValue){
					errorMsg += "OnLogoff();";
				}

				// 리턴 URL 설정
				returnUrl = request.getParameter(auth.getSsoProvider().getReturnUrlTagName());
				if (!Utility.isNullOrEmpty(returnUrl)) {
					returnUrl = Utility.uRLEncode(returnUrl, "UTF-8");
				} else {
					returnUrl = Utility.uRLEncode(auth.getThisUrl(), "UTF-8");
				}
				//SSO 실패 시 정책에 따라 자체 로그인 페이지로 이동 시키거나, SSO 인증을 위한 포탈 로그인 페이지로 이동
				//response.sendRedirect("이동할 URL");

			} else if (status == AuthStatus.SSOUnAvailable) {
				// SSO장애
				// SSO 장애 시 정책에 따라 자체 로그인 페이지로 이동 시키거나, SSO 인증을 위한 포탈 로그인 페이지로 이동
				// response.sendRedirect("이동할 URL");
				errorMsg = "현재 통합인증 서비스가 불가합니다.";
			} else if (status == AuthStatus.SSOAccessDenied) {
				errorMsg = "인증은 되었지만, 현재 사이트에 접근 거부상태입니다";
			}

		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		model.addAttribute("errorMsg", errorMsg);
		logger.debug("errrrrr : " + errorMsg);
		return "common/ssoEpcAdmLoginForm";
	}*/

	/////// NEW EPC 추가
	/////// ---------------------------------------------------------------------
	/**
	 * [ADMIN] ADMIN 로그인 처리
	 * 
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping("common/epcAdmLogin.do")
	public String epcAdmLogin(HttpServletRequest request) throws Exception {
		String loginId = request.getParameter("loginId"); // 로그인 아이디
		String password = request.getParameter("password"); // 로그인 패스워드
		String cono = request.getParameter("cono"); // 사업자번호

		try {
			epcLoginService.makeAdminSessionInfo(request, loginId, password, cono);
		} catch (AlertException e) {
			logger.debug("EPC ADM Login ERROR : " + e.getMessage());
			request.setAttribute("alertMessage", e.getMessage());
			return "common/loginAlert";
		} catch (LoginException e) {
			logger.debug("EPC ADM Login ERROR : " + e.getMessage());
			request.setAttribute("alertMessage", e.getMessage());
			return "common/loginAlert";
		}

		String domin = config.getString("login.domain.url");
		// @4UP 추가 중복 contextPath 처리
		String contextPath = request.getContextPath();
		if (domin.startsWith(contextPath)) {
			domin = domin.substring(contextPath.length());
		}
		return "redirect:" + domin + "/main/vendorIntro.do";
	}

	/**
	 * [협력사] 유통SCM > 로그인 처리
	 * 
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "common/epcVendorLogin.do", method = RequestMethod.POST)
	public String epcVendorLogin(@RequestParam HashMap<String, Object> paramMap, HttpServletRequest request)
			throws Exception {
		// 로그인 domain url
		String domin = config.getString("login.domain.url");

		// @4UP 추가 중복 contextPath 처리
		String contextPath = request.getContextPath();
		if (domin.startsWith(contextPath)) {
			domin = domin.substring(contextPath.length());
		}

		try {
			epcLoginService.makeClientSessionInfo(request, paramMap);
		} catch (AlertException e) {
			logger.debug("EPC Vendor Login ERROR : " + e.getMessage());
			request.setAttribute("alertMessage", e.getMessage());
			return "common/loginAlert";
		} catch (LoginException e) {
			logger.debug("EPC Vendor Login ERROR : " + e.getMessage());
			request.setAttribute("alertMessage", e.getMessage());
			return "common/loginAlert";
		}

		return "redirect:" + domin + "/main/vendorIntro.do";
	}

	/**
	 * [ADMIN] 협력사 변경 로그인 처리
	 * 
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping("common/epcAdmLoginADMIN.do")
	public ModelAndView epcAdmLoginTest(HttpServletRequest request) throws Exception {
		String cono = request.getParameter("cono"); // 사업자번호
		String message = messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault()); // 에러메세지

		try {
			epcLoginService.makeAdminSessionInfoChg(request, cono);
			return AjaxJsonModelHelper.create("");
		} catch (AlertException e) {
			return AjaxJsonModelHelper.create(message);
		} catch (LoginException e) {
			return AjaxJsonModelHelper.create(message);
		}
	}
	
	/**
	 * [ADMIN] SSO 로그인 폼
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("common/viewSSOEpcAdmLoginForm.do")
	public String viewSSOEpcAdmLoginForm(HttpServletRequest request, HttpServletResponse response, Model model) {
		HttpSession session = request.getSession();
		
		try {
			//interceptor에서 request 객체에 설정한 sso정보 get
			SSOUser ssoUser = (SSOUser)request.getAttribute("ssoUser");
			//SSOUrl ssoUrl = (SSOUrl)request.getAttribute("ssoUrl");
			
			//최초 인증 시 null.. SSO에이전트로 인증을 재전송 필요하여 return ""; 처리
			if(ssoUser == null) {
				return "";
			}
			
			//ssoUser가 있으면 sso인증 성공
			if(!ObjectUtils.isEmpty(ssoUser)){
				String userId = ssoUser.getUserID();
				
				model.addAttribute("loginId", userId);
				model.addAttribute("status", "0");
				session.setAttribute("_LOGIN_ID_", userId);
			}
		} catch (Exception e) {
			logger.error("SSO인증 Exception message: " + e.getMessage());
		}

		return "common/ssoEpcAdmLoginForm";
	}
}
