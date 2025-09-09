package com.lottemart.common.login.controller;

import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dreammirae.gt.radius.client.GptwrAuthService;
import com.lottemart.common.etc.RequestWrapper;
import com.lottemart.common.exception.AlertException;
import com.lottemart.common.login.dao.LoginDao;
import com.lottemart.common.login.exception.LoginException;
import com.lottemart.common.login.model.LoginSession;
import com.lottemart.common.login.service.LoginService;
import com.lottemart.common.notice.model.NoticeVO;
import com.lottemart.common.notice.service.NoticeService;
//import com.lottemart.common.security.xecuredb.service.XecuredbConn;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.RequestUtils;
import com.nets.sso.agent.authcheck.AuthCheck;
import com.nets.sso.common.AgentExceptionCode;
import com.nets.sso.common.enums.AuthStatus;

import Nets.Security.TripleDes.TripleDESCrypt;
import lcn.module.common.conts.ConstanceValue;
import lcn.module.common.util.StringUtil;
import lcn.module.common.views.AjaxJsonModelHelper;
import lcn.module.framework.property.ConfigurationService;
import lcn.module.framework.property.PropertyService;

/**
 * 로그인 컨트롤러
 * @Class Name :
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 7. 6. 오후 2:24:30 yhchoi
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Controller("loginController")
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private LoginService loginService;
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private LoginDao loginDao;
	@Autowired
	private ConfigurationService config;

	@Resource(name = "propertiesService")
	protected PropertyService propertyService;

//    /** XecureService 선언*/
//    @Resource(name = "xecuredb")
//    private XecuredbConn xecuredbConn;

    // System Properties 가져오기
    @Value("#{systemProperties['server.type'] == null ? 'local' : systemProperties['server.type']}")
	private static final String serverType = null;

	/**
	 * 로그인폼 표시
	 * Desc :
	 * @Method Name : viewLoginForm
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("common/login/viewLoginForm.do")
	public String viewLoginForm(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		model.addAttribute("serverType", serverType);

		boolean isIE = false;
		String browser = "";
		String userAgent = request.getHeader("User-Agent");
		if (userAgent.indexOf("MSIE") > -1) { // Edge
			browser = "ie 11 under";
			isIE = true;
		} else if (userAgent.indexOf("Trident") > -1) { // IE
			browser = "ie 11";
			isIE = true;
		} else if (userAgent.indexOf("Edge") > -1 || userAgent.indexOf("Edg") > -1) { // Edge
			browser = "edge";
		} else if (userAgent.indexOf("Whale") > -1) { // Naver Whale
			browser = "whale";
		} else if (userAgent.indexOf("Opera") > -1 || userAgent.indexOf("OPR") > -1) { // Opera
			browser = "opera";
		} else if (userAgent.indexOf("Firefox") > -1) { // Firefox
			browser = "firefox";
		} else if (userAgent.indexOf("Safari") > -1 && userAgent.indexOf("Chrome") == -1) { // Safari
			browser = "safari";
		} else if (userAgent.indexOf("Chrome") > -1) { // Chrome
			browser = "chrome";
		}
		logger.info("[User-Agent] : " + userAgent + " [Browser] : " + browser + " [isIE] : " + isIE);
		if (RequestUtils.isIE(request)) {
			return "redirect:/common/login/noIE.do";
		}

		return "common/loginForm";
	}

	/**
	 * 외부접속 로그인폼 표시
	 * Desc :
	 * @Method Name : viewLoginForm
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("common/login/viewExtLoginForm.do")
	public String viewLoginOtpForm() throws Exception {
		return "common/extLoginForm";
	}

	/**
	 * 모바일BOS 로그인폼 표시
	 * Desc :
	 * @Method Name : mViewLoginForm
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("bosmobile/common/login/mViewLoginForm.do")
	public String mViewLoginForm(HttpServletRequest request) throws Exception {
		String domain = config.getString("login.domain.url");

		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		String strIpAddress = request.getRemoteAddr();

        if (ipAddress == null){
        	ipAddress = request.getRemoteAddr();
        }

		logger.info("request.getRemoteAddr() : "+request.getRemoteAddr());

        // 운영에서는 관리자모드 사용금지
		if (domain.indexOf("mbos.lottemart.com") >= 0 ) {
			if(ipAddress.equals("10.52.58.138")){
				return "bosmobile/common/mlogin/mLoginForm";
			}
			if(ipAddress.equals("124.243.13.12")){
				return "bosmobile/common/mlogin/mLoginForm";
			}
			return "bosmobile/common/errorDefault";
		} else {
			return "bosmobile/common/mlogin/mLoginForm";
		}
	}

	/**
	 * 로그인 처리
	 * Desc :
	 * @Method Name : login
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
//	@RequestMapping(value = "common/login/login.do" , method = RequestMethod.GET )
	@RequestMapping("common/login/login.do")
	public String login(HttpServletRequest request) throws Exception {
		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");
		String sysDivnCd = request.getParameter("sysDivnCd");

		try {
			loginService.login(request, loginId, password, sysDivnCd,"","");
		} catch (AlertException e) {
			// @4UP 추가 로그인 처리 실패 로깅
			logger.error("로그인 처리 실패 - AlertException", e);
			request.setAttribute("alertMessage", e.getMessage());
			return "common/loginAlert";
		} catch (LoginException e) {
			// @4UP 추가 로그인 처리 실패 로깅
			logger.error("로그인 처리 실패 - LoginException", e);
			request.setAttribute("alertMessage", e.getMessage());
			return "common/loginError";
		} catch (Exception e) {
			// @4UP 추가 로그인 처리 실패 로깅
			logger.error("로그인 처리 실패 - Exception", e);
			request.setAttribute("alertMessage", e.getMessage());
			throw e;
		}
		//비밀번호 80일 이상 경과시 Exception 은 아니지만 loginError 페이지로 이동해서 alert창을 띄워 줌.
		//if("true".equals(request.getAttribute("loginError"))){
		//	return "common/loginError";
		//}

		request.setAttribute("serverType", serverType);
		//return "redirect:/common/login/viewMain.do";

		String requestUrl = request.getRequestURL().toString();
		if(requestUrl ==null )
			requestUrl  = "";
		/*
		if("02".equals(sysDivnCd) && requestUrl.startsWith("https")){
			StringBuffer bf = new StringBuffer();
			String returnUrl = bf.append("redirect:https://limadmin.lottemart.com").append(request.getContextPath()).append("/common/login/viewMain.do").toString();
			//"redirect:http://limadmin.lottemart.com/cc/common/login/viewMain.do";
			return returnUrl;
		}
		else*/
		if("03".equals(sysDivnCd) && requestUrl.startsWith("https")) {
			StringBuffer bf = new StringBuffer();
			// @4UP 수정 요청 호스트명에 마춰 https 로 변경
			String returnUrl = bf.append("redirect:https://").append(RequestUtils.getRequestHost(request)).append(request.getContextPath()).append("/common/login/viewMain.do").toString();
			return returnUrl;
		}
		else{
			/*
			StringBuffer bf = new StringBuffer();
			String returnUrl = bf.append("redirect:http://localhost:10001").append(request.getContextPath()).append("/common/login/viewMain.do").toString();
			//"redirect:http://localhost:10001/lottemart-cc/common/login/viewMain.do";
			return returnUrl;
			*/
			return "common/main";
		}
	}

	@RequestMapping("common/login/loginTest.do")
	public String login2(HttpServletRequest request) throws Exception {
		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");
		String sysDivnCd = request.getParameter("sysDivnCd");

		try {
			loginService.login(request, loginId, password, sysDivnCd,"","");
		} catch (AlertException e) {
			request.setAttribute("alertMessage", e.getMessage());
			return "common/loginAlert";
		} catch (LoginException e) {
			request.setAttribute("alertMessage", e.getMessage());
			return "common/loginError";
		}
		//비밀번호 80일 이상 경과시 Exception 은 아니지만 loginError 페이지로 이동해서 alert창을 띄워 줌.
		//if("true".equals(request.getAttribute("loginError"))){
		//	return "common/loginError";
		//}

		String requestUrl = request.getRequestURL().toString();
		if(requestUrl ==null )
			requestUrl  = "";

		if("03".equals(sysDivnCd) && requestUrl.startsWith("https")) {
			StringBuffer bf = new StringBuffer();
			String returnUrl = bf.append("redirect:https://limadmind.lottemart.com").append(request.getContextPath()).append("/common/login/viewMain.do").toString();
			logger.debug("******** return URL : " + returnUrl);
			return returnUrl;
		}
		else{
			/*
			StringBuffer bf = new StringBuffer();
			String returnUrl = bf.append("redirect:http://localhost:10001").append(request.getContextPath()).append("/common/login/viewMain.do").toString();
			//"redirect:http://localhost:10001/lottemart-cc/common/login/viewMain.do";
			return returnUrl;
			*/
			logger.debug("******** u'r not in if");
			return "common/main";
		}
	}

	/**
	 * 외부접속 로그인 처리
	 * Desc :
	 * @Method Name : login
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("common/login/extLogin.do")
	public String loginOtp(HttpServletRequest request) throws Exception {
		String loginId = request.getParameter("loginId");
		String sysDivnCd = request.getParameter("sysDivnCd");


		try {
			loginService.extLogin(request, loginId, sysDivnCd);
		} catch (AlertException e) {
			request.setAttribute("alertMessage", e.getMessage());
			return "common/loginAlert";
		} catch (LoginException e) {
			request.setAttribute("alertMessage", e.getMessage());
			return "common/loginError";
		}
		//비밀번호 80일 이상 경과시 Exception 은 아니지만 loginError 페이지로 이동해서 alert창을 띄워 줌.
		//if("true".equals(request.getAttribute("loginError"))){
		//	return "common/loginError";
		//}

		//return "redirect:/common/login/viewMain.do";
		return "common/main";
	}

	/**
	 * 모바일BOS 로그인 처리
	 * Desc :
	 * @Method Name : mlogin
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */

	@RequestMapping("bosmobile/common/mlogin.do")
	public String mlogin(HttpServletRequest request) {
		String sdomain = config.getString("login.sdomain.url");
//
//		if (request.getRequestURL().indexOf("localhost") == -1 && request.getRequestURL().indexOf("https") == -1) {
//			logger.debug("url https redirect ============================" + request.getRequestURL());
//			return "redirect:"+sdomain+"/bosmobile/common/login/mViewLoginForm.do";
//		}
//
		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");
		String sysDivnCd = request.getParameter("sysDivnCd");

		try {
			loginService.login(request, loginId, password, sysDivnCd,"","");

			// 모바일에서는 로그인시 메뉴정보를 가져온다. 2016.8.9 ljh TOP 메뉴 이동시 대메뉴조회
			List<DataMap> subMenuList = loginService.selectAdminTopMenuList2Mobile(request);

			request.setAttribute("subMenuList", subMenuList);

			LoginSession loginSession = LoginSession.getLoginSession(request);
			loginSession.setSubMenuList(subMenuList);
			loginSession.setSysDivnCd(sysDivnCd);

		} catch (AlertException e) {
			request.setAttribute("alertMessage", e.getMessage());
			logger.error(e.getMessage());
			return "bosmobile/common/mlogin/mLoginAlert";
		} catch (LoginException e) {
			request.setAttribute("alertMessage", e.getMessage());
			logger.error(e.getMessage());
			return "bosmobile/common/mlogin/mLoginError";
		} catch (Exception e) {
			request.setAttribute("alertMessage", e.getMessage());
			logger.error(e.getMessage());
			return "bosmobile/common/mlogin/mLoginError";
		}
		//비밀번호 80일 이상 경과시 Exception 은 아니지만 loginError 페이지로 이동해서 alert창을 띄워 줌.
		//if("true".equals(request.getAttribute("loginError"))){
		//	return "common/loginError";
		//}
		request.setAttribute("mBosTitle", "Back Office System");
		request.setAttribute("mFloatingTitle", "");
		logger.debug(config.getString("login.sdomain.url")+"/bosmobile/common/mainDashBoard.do");

		return "redirect:"+sdomain+"/bosmobile/common/mainDashBoard.do";
		//return "bosmobile/common/mobileMainHome";
	}

	/**
	 *
	 * @see updateAdminPwd
	 * @Locaton    : com.lottemart.common.login.controller
	 * @MethodName  : updateAdminPwd
	 * @author     : hjKim
	 * @Description : 비밀번호 변경 처리
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("common/login/otpConfirm.do")
	public ModelAndView otpConfirm(HttpServletRequest request) throws Exception{
		Map<String, String> rtnData = new HashMap<String, String>();
		String loginId = request.getParameter("loginId");
		String otpNo = request.getParameter("otpNo");
		rtnData = otpAuth(loginId, otpNo);
		if(!"0".equals(rtnData.get("resultCode"))){  //fail
			rtnData.put("result", "F");
		}else{
			rtnData.put("result", "S");
		}

		return AjaxJsonModelHelper.create(rtnData);
	}

	/**
	 * 로그인 처리
	 * Desc :
	 * @Method Name : login
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
//	@RequestMapping(value = "common/login/ssologin.do" , method = RequestMethod.GET )
	@RequestMapping("common/login/ssologin.do")
	public String ssologin(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");
		String sysDivnCd = request.getParameter("sysDivnCd");
		String rtnUrl = request.getParameter("returnUrl");

		if (RequestUtils.isIE(request)) {
			return "redirect:/common/login/noIE.do";
		}

		if(rtnUrl == null || "".equals(rtnUrl)){
			rtnUrl = "common/main";
		}else{
			rtnUrl = "redirect:/"+URLDecoder.decode(rtnUrl, "UTF-8");
		}

		try{

			AuthCheck auth = new AuthCheck(request, response); // 인증객체생성 및 인증확인
			AuthStatus status = auth.checkLogon(); // 인증확인

			//인증상태별 처리
			if(status == AuthStatus.SSOFirstAccess) {
				//최초 접속
				auth.trySSO();
			} else if(status == AuthStatus.SSOSuccess) {

				try {
					RequestWrapper param = new RequestWrapper(request);
					String empno = auth.getUserInfoCollection().get("empno");
					String jobDivnCd = auth.getUserInfoCollection().get("company");
					TripleDESCrypt crypt = new TripleDESCrypt("lottefrom2006key", "euc-kr");
					param.setParameter("SSOValidate",crypt.hexEncrypt(empno, "euc-kr"));
					param.setParameter("jobDivnCd",crypt.hexEncrypt(jobDivnCd, "euc-kr"));

					HttpServletRequest req = (HttpServletRequest)param;
					logger.info("empno="+crypt.hexEncrypt(empno, "euc-kr")+" jobDivnCd="+crypt.hexEncrypt(jobDivnCd, "euc-kr"));
					loginService.login(req, loginId, password, sysDivnCd,empno,jobDivnCd);
				} catch (AlertException e) {
					request.setAttribute("alertMessage", e.getMessage());
					return "common/loginAlert";
				} catch (LoginException e) {
					request.setAttribute("alertMessage", e.getMessage());
					return "common/loginError";
				}
				 //비밀번호 80일 이상 경과시 Exception 은 아니지만 loginError 페이지로 이동해서 alert창을 띄워 줌.
				//if("true".equals(request.getAttribute("loginError"))){
				//	return "common/loginError";
				//}

			} else if(status == AuthStatus.SSOFail){
				//인증실패
				if (auth.getErrCode() != AgentExceptionCode.NoException){
					logger.error("alertError('" + auth.getErrCode().toString() + "', '');");
				}
				if (auth.getErrCode() == AgentExceptionCode.SessionDuplicationCheckedFirstPriority
					|| auth.getErrCode() == AgentExceptionCode.SessionDuplicationCheckedLastPriority) {
					logger.error("alertError('중복로그인 정보 IP:" + auth.getDuplicationIP() + " TIME:" + auth.getDuplicationTime() + "', '');");
				}
				//SSO 실패 시 정책에 따라 자체 로그인 페이지로 이동 시키거나, SSO 인증을 위한 포탈 로그인 페이지로 이동
				//response.sendRedirect("이동할 URL");
				return "common/loginError";
			} else if(status == AuthStatus.SSOUnAvailable){
				//SSO장애
				//SSO 장애 시 정책에 따라 자체 로그인 페이지로 이동 시키거나, SSO 인증을 위한 포탈 로그인 페이지로 이동
				//response.sendRedirect("이동할 URL");
				logger.error("alertError('현재 통합인증 서비스가 불가합니다.', '');");
				return "common/loginError";
			} else if(status == AuthStatus.SSOAccessDenied){
				logger.error("alertError('인증은 되었지만, 현재 사이트에 접근 거부상태입니다', '');");
				return "common/loginError";
			}
		} catch (Exception e) {
			throw e;
		}

		LoginSession loginSession = LoginSession.getLoginSession(request);
		String adminId	= loginSession.getAdminId();
		String deptCd 		= loginSession.getDeptCd();

		DataMap map = new DataMap();
		map.put("adminId", adminId);
		map.put("deptCd", deptCd);

		request.setAttribute("eCommerceYn", "N");
		request.setAttribute("unanswerMsg", "");

		//return "redirect:/common/login/viewMain.do";
		return rtnUrl;
	}


	/**
	 * 로그인 처리
	 * Desc :
	 * @Method Name : login
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */

	@RequestMapping("bosmobile/common/login/mssologin.do")
	public String mssologin(HttpServletRequest request,HttpServletResponse response)  {
		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");
		String sysDivnCd = "07";
		//String rtnUrl = "bosmobile/common/mobileMainHome";

		try{

			AuthCheck auth = new AuthCheck(request, response); // 인증객체생성 및 인증확인
			AuthStatus status = auth.checkLogon(); // 인증확인

			//인증상태별 처리
			if(status == AuthStatus.SSOFirstAccess) {
				//최초 접속
				auth.trySSO();
			} else if(status == AuthStatus.SSOSuccess) {


					RequestWrapper param = new RequestWrapper(request);
					String empno = auth.getUserInfoCollection().get("empno");
					String jobDivnCd = auth.getUserInfoCollection().get("company");
					TripleDESCrypt crypt = new TripleDESCrypt("lottefrom2006key", "euc-kr");
					param.setParameter("SSOValidate",crypt.hexEncrypt(empno, "euc-kr"));
					param.setParameter("jobDivnCd",crypt.hexEncrypt(jobDivnCd, "euc-kr"));

					HttpServletRequest req = (HttpServletRequest)param;
					logger.info("empno="+crypt.hexEncrypt(empno, "euc-kr")+" jobDivnCd="+crypt.hexEncrypt(jobDivnCd, "euc-kr"));
					loginService.login(req, loginId, password, sysDivnCd,empno,jobDivnCd);

				 //비밀번호 80일 이상 경과시 Exception 은 아니지만 loginError 페이지로 이동해서 alert창을 띄워 줌.
				//if("true".equals(request.getAttribute("loginError"))){
				//	return "common/loginError";
				//}

				// 모바일에서는 로그인시 메뉴정보를 가져온다. 2016.8.9 ljh
				// loginId 로 메뉴를 가져오도록 한다 2016.8.9 ljh
				List<DataMap> subMenuList = loginService.selectAdminTopMenuList2Mobile(request);

				request.setAttribute("subMenuList", subMenuList);
				LoginSession loginSession = LoginSession.getLoginSession(request);
				loginSession.setSubMenuList(subMenuList);
				loginSession.setSysDivnCd(sysDivnCd);

			} else if(status == AuthStatus.SSOFail){
				//인증실패
				if (auth.getErrCode() != AgentExceptionCode.NoException){
					logger.error("alertError('" + auth.getErrCode().toString() + "', '');");
				}
				if (auth.getErrCode() == AgentExceptionCode.SessionDuplicationCheckedFirstPriority
						|| auth.getErrCode() == AgentExceptionCode.SessionDuplicationCheckedLastPriority) {
					logger.error("alertError('중복로그인 정보 IP:" + auth.getDuplicationIP() + " TIME:" + auth.getDuplicationTime() + "', '');");
				}
				//SSO 실패 시 정책에 따라 자체 로그인 페이지로 이동 시키거나, SSO 인증을 위한 포탈 로그인 페이지로 이동
				//response.sendRedirect("이동할 URL");
				return "bosmobile/common/mlogin/mLoginError";
			} else if(status == AuthStatus.SSOUnAvailable){
				//SSO장애
				//SSO 장애 시 정책에 따라 자체 로그인 페이지로 이동 시키거나, SSO 인증을 위한 포탈 로그인 페이지로 이동
				//response.sendRedirect("이동할 URL");
				logger.error("alertError('현재 통합인증 서비스가 불가합니다.', '');");
				return "bosmobile/common/mlogin/mLoginError";
			} else if(status == AuthStatus.SSOAccessDenied){
				logger.error("alertError('인증은 되었지만, 현재 사이트에 접근 거부상태입니다', '');");
				return "bosmobile/common/mlogin/mLoginErrorr";
			}
		} catch (AlertException e) {
			request.setAttribute("alertMessage", e.getMessage());
			logger.error(e.getMessage());
			return "bosmobile/common/mlogin/mLoginAlert";
		} catch (LoginException e) {
			request.setAttribute("alertMessage", e.getMessage());
			logger.error(e.getMessage());
			return "bosmobile/common/mlogin/mLoginError";
		} catch (Exception e) {
			request.setAttribute("alertMessage", e.getMessage());
			logger.error(e.getMessage());
			return "bosmobile/common/mlogin/mLoginError";
		}

		// https-> http로 리다이렉트
		String domain = config.getString("login.domain.url");
		logger.debug(config.getString("login.domain.url")+"/bosmobile/common/mainDashBoard.do");
		return "redirect:"+domain+"/bosmobile/common/mainDashBoard.do";
	}


	/***
	 * 2012.06.07 jylim(임재유) - 비밀번호 만료 규칙 변경으로 인한 추가
	 */
	@RequestMapping("common/login/goMain.do")
	public String goMain(HttpServletRequest request) throws Exception {
		return "common/main";
	}

	/**
	 *
	 * @see viewChangePwdForm
	 * @Locaton    : com.lottemart.common.login.controller
	 * @MethodName  : viewChangePwdForm
	 * @author     : hjKim
	 * @Description : 비밀번호 변경 페이지
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("common/login/viewChangePwdForm.do")
	public String viewChangePwdForm(HttpServletRequest request) throws Exception {

		String adminId = request.getParameter("adminId");

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("adminId", adminId);

		DataMap adminInfo = loginService.selectAdminPwdInfo(paramMap);

		request.setAttribute("adminInfo", adminInfo);
		request.setAttribute("adminId", adminId);

		return "common/changePwdForm";
	}

	/**
	 *
	 * @see updateAdminPwd
	 * @Locaton    : com.lottemart.common.login.controller
	 * @MethodName  : updateAdminPwd
	 * @author     : hjKim
	 * @Description : 비밀번호 변경 처리
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("common/login/updateAdminPwd.do")
	public ModelAndView updateAdminPwd(HttpServletRequest request) throws Exception{

		String adminId 	= request.getParameter("adminId");
		String targetPwd 	=  request.getParameter("password1");
		String newPwd 	=  request.getParameter("password3");
		String encryptNewPwd = "";
		String encryptTargetPwd = "";

		//got 여부 2017.04.03 eajo 추가 - B2C 물류팀 요청
		String gotYn = StringUtil.null2string(request.getParameter("gotYn"), "");

		try {
			// 오늘날짜를 가져옴
			DataMap resultMap = loginService.selectToDate();

			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("adminId", adminId);

			resultMap = loginService.selectAdminPwdInfo(paramMap);

			String loginId 		= resultMap.getString("LOGIN_ID");
			String phoneExNo 	= resultMap.getString("PHONE_EX_NO");
			String encryptOldPwd = resultMap.getString("PWD");
			String lmartEmpNo    = resultMap.getString("LMART_EMP_NO");

			//GOT 사용자(사번존재하고 직접 로그인) 관련 암호화 분기 - 2015.05.11
			if ("".equals(lmartEmpNo)) {
//				encryptTargetPwd = xecuredbConn.hash(targetPwd);
//				encryptNewPwd    = xecuredbConn.hash(newPwd);
			} else if (!"".equals(lmartEmpNo)) {
//				encryptTargetPwd = xecuredbConn.encrptBysha256(targetPwd);
//				encryptNewPwd    = xecuredbConn.encrptBysha256(newPwd);
			}

			if(!encryptTargetPwd.equals(encryptOldPwd)) {
				return AjaxJsonModelHelper.create("기존비밀번호가 맞지 않습니다.");
			}

			if(encryptNewPwd.equals(encryptOldPwd)) {
				return AjaxJsonModelHelper.create("변경하려는 비밀번호가 예전과 같습니다. 동일하지 않게 입력해주세요.");
			}

			if(newPwd.equals(loginId)) {
				return AjaxJsonModelHelper.create("로그인ID와 동일합니다. 동일하지 않게 입력해주세요.");
			}
			if(newPwd.equals(phoneExNo)) {
				return AjaxJsonModelHelper.create("내선번호와 동일합니다. 동일하지 않게 입력해주세요.");
			}
			if(newPwd.equals(adminId)) {
				return AjaxJsonModelHelper.create("관리자번호와 동일합니다. 동일하지 않게 입력해주세요.");
			}
			if(newPwd.indexOf(loginId) != -1) {
				return AjaxJsonModelHelper.create("비밀번호에 로그인아이디가 있습니다. 로그인 아이디가 포함되지 않게 입력해주세요.");
			}
			if(loginId.indexOf(newPwd) != -1) {
				return AjaxJsonModelHelper.create("로그인아이디에 비밀번호가 있습니다. 로그인 아이디의 일부가 비밀번호가 되지 않게 입력해주세요.");
			}

			// set argument
			paramMap.put("adminId", adminId);
			paramMap.put("loginId", loginId);
			paramMap.put("pwd", encryptNewPwd);

			//패스워드(비밀번호) 변경 히스토리 저장 -- 20150602 추가
			DataMap dm = new DataMap();
			dm.put("adminId",adminId);
			dm.put("pwd",encryptNewPwd);
			dm.put("adminIP",(String)request.getRemoteAddr());
			dm.put("stringPwd",newPwd);
			dm.put("memo","MAIN -  비밀번호 재설정으로 인한 변경 or 초기화.");

			//got 여부 2017.04.03 eajo 추가 - B2C 물류팀 요청
			dm.put("gotYn",gotYn);

			logger.info("request.getRemoteAddr() : "+request.getRemoteAddr());

			String returnValue = loginService.checkPwd(dm);
			if(!"".equals(returnValue)){
				return AjaxJsonModelHelper.create(returnValue);
			}
			// --20150602  여기까지 추가

			loginService.updateAdminPwd(paramMap);

			return AjaxJsonModelHelper.create("success");
		} catch (Exception e) {
			logger.error(e.getMessage());
			return AjaxJsonModelHelper.create("처리중 에러가 발생하였습니다.");
		}
	}

	/**
	 *
	 * @see viewAdminPwdAuthenticForm
	 * @Locaton    : com.lottemart.common.login.controller
	 * @MethodName  : viewAdminPwdAuthenticForm
	 * @author     : hjKim
	 * @Description : 인증번호 발급 페이지
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("common/login/viewAdminPwdAuthenticForm.do")
	public String viewAdminPwdAuthenticForm(HttpServletRequest request) throws Exception {

		String adminId = request.getParameter("adminId");

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("adminId", adminId);

		DataMap adminInfo = loginService.selectAdminPwdInfo(paramMap);

		request.setAttribute("adminInfo", adminInfo);
		request.setAttribute("adminId", adminId);

		return "common/adminPwdAuthenticForm";
	}

	/**
	 *
	 * @see updateAdminAuthPwd
	 * @Locaton    : com.lottemart.common.login.controller
	 * @MethodName  : updateAdminAuthPwd
	 * @author     : hjKim
	 * @Description : 비밀번호변경인증 처리
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("common/login/updateAdminAuthPwd.do")
	public ModelAndView updateAdminAuthPwd(HttpServletRequest request) throws Exception {

		String adminId 	= request.getParameter("adminId");
		String authNo 	=  request.getParameter("authNo");
		String newPwd 	=  request.getParameter("password3");
		String sysDivnCd  =  request.getParameter("sysDivnCd");

		// 2017.11.21 이지원 cc에서 비밀번호 변경시 암호화되어 넘어온 비밀번호를 복호화한다. - 김다인 대리 요청
		if(StringUtils.isNotEmpty(sysDivnCd) && ConstanceValue.SYSTEM_DIVN_CC.equals(sysDivnCd)){
			byte[] decoded = Base64.decodeBase64(newPwd);
			newPwd = new String(decoded);
		}

		String encryptNewPwd = "";

		//got 여부 2017.04.03 eajo 추가 - B2C 물류팀 요청
		String gotYn = StringUtil.null2string(request.getParameter("gotYn"), "");

		try {
			// 오늘날짜를 가져옴
			DataMap resultMap = loginService.selectToDate();

			String today = resultMap.getString("TO_DATE");

			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("adminId", adminId);

			resultMap = loginService.selectAdminPwdInfo(paramMap);

			//LMREQ-3777 : M3 계정일 때에는 비밀번호 변경 시 히스토리 테이블에서 직전의 비밀번호가 체크 해달라는 요청으로 인한 수정
			//TAD_ADMIN_TYPE 테이블에서 05 - (점포) Picking담당인지 조회
			String loginId 		= resultMap.getString("LOGIN_ID");
			String phoneExNo 	= resultMap.getString("PHONE_EX_NO");
			String cellAuthNo 	= resultMap.getString("CELL_AUTH_NO");
			String cellAuthDate = resultMap.getString("CELL_AUTH_DATE");
			String lmartEmpNo   = resultMap.getString("LMART_EMP_NO");
			int authAfterTime	= resultMap.getInt("AUTH_AFTER_TIME");
			String adminTypecd  = resultMap.getString("ADMIN_TYPE_CD"); // TAD_ADMIN_TYPE 테이블에 ADMIN_TYPE 값을 조회 

			if(!authNo.equals(cellAuthNo)) {
				return AjaxJsonModelHelper.create("인증번호가 맞지 않습니다.");
			}

			if(today.equals(cellAuthDate) && authAfterTime < -30) {
				return AjaxJsonModelHelper.create("인증시한이 지났습니다. 관리자에게 재문의 바랍니다.");
			}

			if(newPwd.equals(loginId)) {
				return AjaxJsonModelHelper.create("로그인ID와 동일합니다. 동일하지 않게 입력해주세요.");
			}

			if(newPwd.equals(phoneExNo)) {
				return AjaxJsonModelHelper.create("내선번호와 동일합니다. 동일하지 않게 입력해주세요.");
			}

			if(newPwd.equals(adminId)) {
				return AjaxJsonModelHelper.create("관리자번호와 동일합니다. 동일하지 않게 입력해주세요.");
			}

			if(newPwd.indexOf(loginId) != -1) {
				return AjaxJsonModelHelper.create("비밀번호에 로그인아이디가 있습니다. 로그인 아이디가 포함되지 않게 입력해주세요.");
			}

			if(loginId.indexOf(newPwd) != -1) {
				return AjaxJsonModelHelper.create("로그인아이디에 비밀번호가 있습니다. 로그인 아이디의 일부가 비밀번호가 되지 않게 입력해주세요.");
			}

			if(newPwd.length() < 10) {
				return AjaxJsonModelHelper.create("현재 사용하시는 패스워드가 10자리 미만입니다. 패스워드를 10자리 이상으로 변경바랍니다.");
			}

			// set argument
			if ("".equals(lmartEmpNo)) {
//				encryptNewPwd    = xecuredbConn.hash(newPwd);
			} else if (!"".equals(lmartEmpNo)) {
//				encryptNewPwd    = xecuredbConn.encrptBysha256(newPwd);
			}

			paramMap.put("adminId", adminId);
			paramMap.put("loginId", loginId);
			paramMap.put("pwd", encryptNewPwd);

			//패스워드(비밀번호) 변경 히스토리 저장 -- 20150602 추가
			DataMap dm = new DataMap();
			dm.put("adminId",adminId);
			dm.put("pwd",encryptNewPwd);
			dm.put("adminIP",(String)request.getRemoteAddr());
			dm.put("stringPwd",newPwd);
			dm.put("memo","MAIN - 인증번호 입력후. 비밀번호 변경");
			dm.put("adminTypeCd", adminTypecd); 

			//got 여부 2017.04.03 eajo 추가 - B2C 물류팀 요청
			dm.put("gotYn",gotYn);

			logger.info("request.getRemoteAddr() : "+request.getRemoteAddr());

			String returnValue = loginService.checkPwd(dm);
			if(!"".equals(returnValue)){
				return AjaxJsonModelHelper.create(returnValue);
			}
			// --20150602  여기까지 추가

			loginService.updateAdminInfo(paramMap);

			return AjaxJsonModelHelper.create("success");
		} catch (Exception e) {
			logger.error(e.getMessage());
			return AjaxJsonModelHelper.create("처리중 에러가 발생하였습니다.");
		}

	}

	/**
	 * 로그인처리 - 개발용도
	 * Desc :
	 * @Method Name : loginTest
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	//@RequestMapping("common/login/loginTest.do")
	//public String loginTest(HttpServletRequest request) throws Exception {
	//	String loginId = request.getParameter("loginId");
	//	String password = request.getParameter("password");
	//	String lotteEmpNo = request.getParameter("SSOValidate"); //	롯데마트 SSO
    //
	//	try {
	//		loginService.login(request, loginId, password);
	//	} catch (AlertException e) {
	//		request.setAttribute("alertMessage", e.getMessage());
	//		return "common/loginAlert";
	//	} catch (LoginException e) {
	//		request.setAttribute("alertMessage", e.getMessage());
	//		return "common/loginAlert";
	//	}
	//
	//	return "common/blank";
	//}

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
	@RequestMapping("common/login/logout.do")
	public String logout(HttpServletRequest request) throws Exception {
		try {
			//CC 윤해성 12.27일 오후 18:07분 추가 CC만 사용
			request.setAttribute("serverType", serverType);
			HttpSession session  = request.getSession();
			session.removeAttribute("memberNoIn");
			session.removeAttribute("memberNoOut");
			//
			loginService.logout(request);

		} catch (Exception e) {
			logger.error("error --> " + e.getMessage());
		}

		return "common/loginForm";
	}

	/**
	 * 메인페이지 표시 - 상단메뉴포함(사용X)
	 * Desc :
	 * @Method Name : viewMain
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("common/login/viewMain.do")
	public String viewMain(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 메뉴목록조회
		//List<DataMap> menuList = loginService.selectAdminMenuList(request);
		//request.setAttribute("menuList", menuList);
		//subMenu(request,response);

		return "common/main";
	}


	/**
	 * 메인페이지 표시 - 상단메뉴
	 * Desc :
	 * @Method Name : gnb
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */

	@RequestMapping("common/login/gnb.do")
	public String gnb(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*
		LoginSession loginSession = LoginSession.getLoginSession(request);
		String adminId 		= loginSession.getAdminId();
		String ssoDivn 		= loginSession.getSsoDivnCd();
		String sysDivnCd 	= loginSession.getSysDivnCd();
		String jobDivnCd	= loginSession.getJobDivnCd();
		Map<String, String> paramMap = null;
		*/

		String sysDivnCd = request.getParameter("sysDivnCd");
		logger.debug("gnb.sysDivnCd->"+sysDivnCd);

		/* List<DataMap> menuList = null;

		if(!"".equals(sysDivnCd) || !"null".equals(sysDivnCd)){
			request.setAttribute("sysDivnCd", sysDivnCd);
			logger.debug("getParameter---->"+request.getParameter("sysDivnCd"));

			// TOP 메뉴 이동시 대메뉴조회
			menuList = loginService.selectAdminTopMenuList2(request);
			request.setAttribute("menuList", menuList);
		} else{
			// 상단목록조회
			menuList = loginService.selectAdminTopMenuList(request);
			request.setAttribute("menuList", menuList);
		}

		logger.debug("menuList->"+menuList); */

		// TOP 메뉴 이동시 대메뉴조회
		List<DataMap> menuList = loginService.selectAdminTopMenuList2(request);
		request.setAttribute("menuList", menuList);

		// softphone 메뉴 사용 여부
		LoginSession loginSession = LoginSession.getLoginSession(request);
		String adminId = loginSession.getAdminId();

		DataMap paramMap = new DataMap();
		paramMap.put("adminId"	, adminId);
		paramMap.put("roleId"		, "C00000117"); // CC CTI로그인권한
		String recYn = loginService.selectAdminRoleAssign(paramMap);
		request.setAttribute("recYn", recYn);

		//lmsdvap01_servlet_engine6
		String sessionId = request.getSession().getId();
		String wasName = "";
		try {
			String [] session = sessionId.split("\\.");
			String [] session2 = session[1].split("\\_");
			wasName = session2[0];
		}catch(Exception e) {
			wasName = "-1";
		}
		request.setAttribute("wasName", wasName);
		request.setAttribute("sysDivnCd", sysDivnCd);

		/**
		 * 마이다스를 통해 bos에 로그인하면  첫메뉴가 EPC가 되도록 함
		 *  - EPC 접근권한이 없으면 alert 으로 공지
		 */
		//마이다스를 통해 bos에 로그인 하면 초기 메뉴는 EPC
		/*
		if(    ConstanceValue.SYSTEM_DIVN_BOS.equals(sysDivnCd)			// bos로 로그인
			&& ConstanceValue.SSO_DIVN_MIDAS.equals(ssoDivn)			// 마이다스를 통한 로그인
			&& ConstanceValue.MIDAS_JOB_DIVN_SINGYU.equals(jobDivnCd))	// 마이다스의 신규상담업체 버튼으로 로그인
			{

			//EPC 접근권한이 있는지 체크
			paramMap = new HashMap<String, String>();
			paramMap.put("adminId", 	adminId);
			paramMap.put("sysDivnCd", 	sysDivnCd);
			paramMap.put("menuId" , 	ConstanceValue.MENU_ID_BOS_EPC);
			// 권한이 있으면 첫메뉴를 EPC로
			if("TRUE".equals(loginDao.menuAuthCheck(paramMap).getString("AUTH_CHECK"))){
				request.setAttribute("isMidas", 	"TRUE");
				request.setAttribute("epcAuthChk", 	"TRUE");
				request.setAttribute("epcMenuId", 	ConstanceValue.MENU_ID_BOS_EPC);
			}
			else{
				request.setAttribute("isMidas", 	"TRUE");
				request.setAttribute("epcAuthChk", 	"FALSE");
				request.setAttribute("epcMenuId", 	ConstanceValue.MENU_ID_BOS_EPC);
			}
		}
		*/
		String typeCd = StringUtil.null2str((String) request.getParameter("typeCd"));
		request.setAttribute("typeCd", typeCd);

		return "common/gnb";
	}


	/**
	 * 메인페이지 표시 - 상단메뉴 및 권한확인
	 * Desc :
	 * @Method Name : gnb
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("/bosmobile/common/login/mobileGnb.do")
	public String mobileGnb(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 모바일BOS = 07  only 권한확인
		String sysDivnCd = "07";
		// softphone 메뉴 사용 여부
		LoginSession loginSession = LoginSession.getLoginSession(request);
		if(loginSession == null){
			request.setAttribute("alertMessage", "세션이 종료되었습니다.");
			return "bosmobile/common/mlogin/mLoginAlert";
		}
		//String adminId = loginSession.getAdminId();

		//request.setAttribute("menuList", loginSession.getMenuList());
		request.setAttribute("subMenuList", loginSession.getSubMenuList());

		//lmsdvap01_servlet_engine6
		String sessionId = request.getSession().getId();
		String wasName = "";
		try {
			String [] session = sessionId.split("\\.");
			String [] session2 = session[1].split("\\_");
			wasName = session2[0];
		}catch(Exception e) {
			wasName = "-1";
		}
		request.setAttribute("wasName", wasName);
		request.setAttribute("sysDivnCd", sysDivnCd);

//		return "mobile/include/mobileGnb";
		return "bosmobile/include/mobile_left_navi_menu";
	}

	/**
	 * 메인페이지 표시 - 좌측메뉴(초기화면 메뉴가져오기)
	 * Desc :
	 * @Method Name : lnb
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("common/login/lnb.do")
	public String lnb(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginSession loginSession = LoginSession.getLoginSession(request);

		if(loginSession == null){
			request.setAttribute("alertMessage", "세션이 종료되었습니다.");
			return "common/loginAlert2";
		}

		String adminId 		= loginSession.getAdminId();
		String ssoDivn 		= loginSession.getSsoDivnCd();
		String jobDivnCd	= loginSession.getJobDivnCd();


		//gnb.jsp에서 BOS, OLAP 클릭시 넘어오는 인자값
		String sysDivnCd = request.getParameter("sysDivnCd");
		logger.debug("lnb.sysDivnCd->"+sysDivnCd);

		Map<String, String> paramMap = null;

		// 상단목록조회
		List<DataMap> menuList = loginService.selectAdminTopMenuList2(request);

		String menuId = "";

		//첫 메뉴 id를 가져오기 위함
		if( menuList != null && menuList.size() != 0){
			menuId = menuList.get(0).getString("MENU_ID").toString();
		}
		logger.debug("lnb.menuId->"+menuId);

		if("01".equals(sysDivnCd)){
			/**
			 * 마이다스를 통해 bos에 로그인하면 첫메뉴가 EPC가 되도록 함 - EPC 접근권한이 없으면 alert 으로 공지
			 */
			//마이다스를 통해 bos에 로그인 하면 초기 메뉴는 EPC

			if(    ConstanceValue.SYSTEM_DIVN_BOS.equals(sysDivnCd)			// bos로 로그인
					&& ConstanceValue.SSO_DIVN_MIDAS.equals(ssoDivn)			// 마이다스를 통한 로그인
					&& ConstanceValue.MIDAS_JOB_DIVN_SINGYU.equals(jobDivnCd)) { // 마이다스의 신규상담업체 버튼으로 로그인

				//EPC 접근권한이 있는지 체크
				paramMap = new HashMap<String, String>();
				paramMap.put("adminId", 	adminId);
				paramMap.put("sysDivnCd", 	sysDivnCd);
				paramMap.put("menuId" , 	ConstanceValue.MENU_ID_BOS_EPC);

				// 권한이 있으면 첫메뉴를 EPC로
				if("TRUE".equals(loginDao.menuAuthCheck(paramMap).getString("AUTH_CHECK"))){
					menuId = ConstanceValue.MENU_ID_BOS_EPC;
				} else {
					request.setAttribute("isMidas", 	"TRUE");
					request.setAttribute("epcAuthChk", 	"FALSE");
				}
			}
		}

		List<DataMap> subMenuList = null;
		try {
			// 왼쪽목록조회
			subMenuList = loginService.selectAdminSubMenuList2(request, menuId);
		} catch (AlertException ae) {
			request.setAttribute("alertMessage", ae.getMessage());
			return "common/loginAlert";
		}

		request.setAttribute("sysDivnCd", sysDivnCd);
		request.setAttribute("menuList", menuList);
		request.setAttribute("subMenuList", subMenuList);

		String typeCd = StringUtil.null2str((String) request.getParameter("typeCd"));
		request.setAttribute("typeCd", typeCd);

		return "common/lnb";
	}

	/**
	 * 메인페이지 표시 - 상단메뉴 클릭시 좌측메뉴
	 * Desc :
	 * @Method Name : menuClick
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("common/login/menuClick.do")
	public String menuClick(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String menuId = request.getParameter("menuId").toString();
		String sysDivnCd = request.getParameter("sysDivnCd");
		logger.debug("menuClick.sysDivnCd->"+request.getParameter("sysDivnCd"));
		LoginSession loginSession = LoginSession.getLoginSession(request);

		if (loginSession == null) {
			return "redirect:/common/login/sessionOutView.do";
		}

		List<DataMap> subMenuList = null;
		try {
			// 왼쪽목록조회
			subMenuList = loginService.selectAdminSubMenuList2(request, menuId);
		} catch (AlertException ae) {
			request.setAttribute("alertMessage", ae.getMessage());
			return "common/loginAlert";
		}

		request.setAttribute("sysDivnCd", sysDivnCd);
		request.setAttribute("subMenuList", subMenuList);

		String typeCd = StringUtil.null2str((String) request.getParameter("typeCd"));
		request.setAttribute("typeCd", typeCd);

		return "common/lnb";
	}

	/**
	 * 모바일 메인페이지 표시 - 상단메뉴 클릭시 좌측메뉴
	 * Desc :
	 * @Method Name : menuClick
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	@RequestMapping("bosmobile/common/login/mbosMenuClick.do")
	public String mbosMenuClick(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String menuId = request.getParameter("menuId").toString();
		String sysDivnCd = request.getParameter("sysDivnCd");
		logger.debug("menuClick.sysDivnCd->"+request.getParameter("sysDivnCd"));

		List<DataMap> subMenuList = null;
		try {
			// 왼쪽목록조회
//			subMenuList = loginService.selectAdminSubMenuList2(request, menuId);
			subMenuList = loginService.selectAdminSubMenuList2Mobile(request, menuId);
		} catch (AlertException ae) {
			request.setAttribute("alertMessage", ae.getMessage());
			return "common/loginAlert";
		}

		request.setAttribute("sysDivnCd", sysDivnCd);
		request.setAttribute("subMenuList", subMenuList);

		return "bosmobile/include/mobile_left_navi_menu";
	}
	 */


	/**
	 * 메인페이지 표시 - body
	 * Desc :
	 * @Method Name : content
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("common/login/content.do")
	public String content(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 로그인 했을 경우 = 1, 로고 클릭시 = 0
		String loginFlag = StringUtils.defaultIfEmpty(request.getParameter("loginFlag"), "0");
		request.setAttribute("loginFlag", loginFlag);

		LoginSession loginSession = LoginSession.getLoginSession(request);

		if(loginSession == null){
			request.setAttribute("alertMessage", "세션이 종료되었습니다.");
			return "common/contentStart";
		}

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("strCd", loginSession.getStrCd());

		paramMap.put("sysDivnCd", loginSession.getSysDivnCd());

		List<DataMap> noticeList = loginService.selectNoticeList(paramMap);

//		List<NoticeVO> popupNoticeList = loginService.selectPopupNoticeList(paramMap);

		List<NoticeVO> popupNoticeList = noticeService.selectPopupNoticeList(paramMap);

		request.setAttribute("noticeList", noticeList);
		request.setAttribute("popupNoticeList", popupNoticeList);

		return "common/contentStart";
	}

	/**
	 * Desc : olap content
	 * @Method Name : contentOlap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("common/login/contentOlap.do")
	public String contentOlap(HttpServletRequest request, HttpServletResponse response) throws Exception {

		LoginSession loginSession = LoginSession.getLoginSession(request);

		if(loginSession == null){
			request.setAttribute("alertMessage", "세션이 종료되었습니다.");
			return "common/contentStartOlap";
		}

		String olap_userId = config.getString("olap.userId");
		String olap_password = config.getString("olap.password");
		String olap_spaceId = config.getString("olap.spaceId");
		String basicDomain = config.getString("olap.basic.domain");
		String advDomain = config.getString("olap.adv.domain");

		logger.debug("olap_userId->"+olap_userId);
		logger.debug("olap_password->"+olap_password);
		logger.debug("olap_spaceId->"+olap_spaceId);

		request.setAttribute("olap_userId", olap_userId);
		request.setAttribute("olap_password", olap_password);
		request.setAttribute("olap_spaceId", olap_spaceId);
		request.setAttribute("basicDomain", basicDomain);
		request.setAttribute("advDomain", advDomain);

		return "common/contentStartOlap";
	}

	@RequestMapping("common/login/changeSysType.do")
	public String changeSysType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String gubun = request.getParameter("gb");
		String sysDivnCd = request.getParameter("sysDivnCd");
		String typeCd = StringUtil.null2str((String) request.getParameter("typeCd"));

		LoginSession loginSession = LoginSession.getLoginSession(request);
		loginSession.setSysDivnCd(sysDivnCd);

		if(typeCd.equals("cc")){
			response.sendRedirect("/" + gubun + "/frameset.jsp?sysDivnCd=" + sysDivnCd + "&typeCd=" + typeCd);
		}else{
			response.sendRedirect("/" + gubun + "/frameset.jsp?sysDivnCd=" + sysDivnCd);
		}
		return "common/blank";
	}

	@RequestMapping("common/login/updateAdminSelfCert.do")
	public ModelAndView updateAdminSelfCert(HttpServletRequest request) throws Exception {

		String adminId 	=  request.getParameter("adminId");
		String message  = "";

		try {
			DataMap dm = new DataMap();
			dm.put("adminId",adminId);

			message = loginService.pwdSmsSend(dm);

			return AjaxJsonModelHelper.create(message);

		} catch (Exception e) {
			logger.error(e.getMessage());
			return AjaxJsonModelHelper.create("처리중 에러가 발생하였습니다.");
		}

	}

	/**
	 * OTP 권한 체크
	 * Desc :
	 * @Method Name : otpAuth
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public Map<String, String> otpAuth(String name, String otpNo) throws Exception {
		String rCode = null;
		String rMsg = null;
		String serverIp = propertyService.getString("Globals.otpIp");
		String port = propertyService.getString("Globals.otpPort");
		String sharedSecret = propertyService.getString("Globals.otpSecret");
		String timeout = "3";

		Map<String, String> resultMap  = new HashMap<String, String>();

		String result =  GptwrAuthService.auth(serverIp, port, sharedSecret, name, otpNo, timeout);

		String[] tmp = result.split("#");

		if(tmp.length >= 3){
			rCode = tmp[0];
			rMsg = tmp[2];
		}
		logger.info("serverIp : " + serverIp + ", port : " + port + ", sharedSecret : " + sharedSecret + ", 코드 : "+rCode+"    메세지 : "+rMsg);
		resultMap.put("resultCode", rCode);
		resultMap.put("resultMsg", rMsg);

		return resultMap;
	}

	/**
	 * Desc : gnb 클릭시, OLAP 메뉴로 이동
	 * @Method Name : olapMain
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("common/login/main.do")
	public String olapMain(HttpServletRequest request) throws Exception {
		String sysDivnCd = request.getParameter("sysDivnCd");
		request.setAttribute("sysDivnCd", sysDivnCd);
		String typeCd = StringUtil.null2str((String) request.getParameter("typeCd"));
		request.setAttribute("typeCd", typeCd);

		return "common/main";
	}


	@RequestMapping("common/login/makePwd.do")
	public ModelAndView makePwd(HttpServletRequest request) throws Exception{

		String makePwd = request.getParameter("makePwd");
		String resultPwd256 = "";
		String resultPwdHash = "";
//		String resultPwd256 = xecuredbConn.encrptBysha256(makePwd);
//		String resultPwdHash = xecuredbConn.hash(makePwd);

		logger.debug("resultPwd256=>"+resultPwd256);
		logger.debug("resultPwdHash=>"+resultPwdHash);

		return AjaxJsonModelHelper.create("success");
	}
	@RequestMapping("common/login/fileDown.do")
    public void fileDown(HttpServletResponse response, HttpServletRequest request,Map<String, Object> model) throws Exception {
		try {
			//String storedFileName = request.getSession().getServletContext().getRealPath(config.getString("user.manual.path")+"window_update.pdf");
			String storedFileName = request.getSession().getServletContext().getRealPath("/WEB-INF/jsp/excel/window_update.pdf");
			String originalFileName = "윈도우 업데이트 가이드.pdf";

			byte fileByte[] = FileUtils.readFileToByteArray(new File(storedFileName));
			response.setContentType("application/octet-stream");
			response.setContentLength(fileByte.length);
			response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(originalFileName,"UTF-8").replaceAll("\\+", "%20"));
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.getOutputStream().write(fileByte);
			response.getOutputStream().flush();
		} catch (Exception e) {
			logger.error("error --> " + e.getMessage());
		}finally{
			response.getOutputStream().close();
		}
    }

	/**
	 * Desc : 세션 종료 시, 안내 페이지 호출
	 * @Method Name : sessionOutView
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("common/login/sessionOutView.do")
	public String sessionOutView(Model model) throws Exception {
		model.addAttribute("serverType", serverType);
		return "common/sessionOutView";
	}

	@RequestMapping("/common/login/noIE.do")
	public String noIE(Model model) throws Exception {
		model.addAttribute("serverType", serverType);
		return "common/noIE";
	}

}
