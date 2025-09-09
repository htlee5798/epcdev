/**
 * @prjectName  : lottemart 프로젝트
 * @since       : 2011. 11. 8. 오후 5:10:36
 * @author      : yskim
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */
package com.lottemart.epc.common.interceptor;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.interceptor.UserInterceptor;
import lcn.module.framework.property.ConfigurationService;
import nets.sso.agent.web.v9.SSOAuthn;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import xlib.cmc.GridData;
import xlib.cmc.OperateGridData;

import com.lcnjf.util.StringUtil;
import com.lottemart.common.exception.AccessDeniedException;
import com.lottemart.common.login.dao.LoginDao;
import com.lottemart.epc.common.dao.AccessCommonDao;
import com.lottemart.epc.common.model.EpcLoginVO;

/**
 * 
 * @Class Name : EpcUserInterceptor.java
 * @Description : EPC 공통 Interceptor
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 *  
 *               </pre>
 */
@Controller
public class EpcUserInterceptor extends UserInterceptor {

	@Autowired
	private ConfigurationService config;

	@Autowired
	private LoginDao loginDao;
	
	@Autowired
	AccessCommonDao accessDao;

	protected final Log logger = LogFactory.getLog(getClass());

    //세션 아웃 처리
    private final static String retUri = "/main/sessionOut.do";
    
    //SSO 로그인 PAGE
    private final static String ssoLoginPage = "/common/viewSSOEpcAdmLoginForm.do";
    
    //로그인 세션 불필요 (단일주소)
	public final static String [] loginFreeActions = { 
		"/main/sessionOut.do"							//session out 처리 페이지
		, "/common/viewEpcAdmLoginForm.do"				// 관리자 로그인 페이지
		, "/common/viewSSOEpcAdmLoginForm.do"			// 협력사관리자(SSO) 로그인 페이지
		, "/common/selectPartnerFirmsPopupSSL.do"		// 관리자 로그인 페이지 > 협력사 조회 팝업
		, "/common/selectSSOPartnerFirmsPopupSSL.do"	// 협력사 관리자 로그인 페이지(SSO) > 협력사 조회 팝업
		, "/common/epcAdmLogin.do"						// 관리자 로그인 처리
		, "/common/epcSSOAdmLogin.do"					// 협력사 관리자(SSO) 로그인 처리
		, "/common/epcVendorLogin.do"					// 협력사 로그인 처리 
//		"/main/intro.do",
//		"/common/commonHead.do",	//공통 스크립트
//		"/common/productImsiDetail.do", //가등록 상품 상세보기
//		"/excel/Down2Excel.do", 		//excel 다운로드
//		"/excel/DirectDown2Excel.do"	//excel direct 다운로드
	};

	//로그인 세션 불필요 (url pattern)
	public final String [] freeUrls = {
		"/restapi/"		//EPC RestApi
	};	
	
	//시스템 관리자만 접근 가능한 url path
	public final String [] sysAdmUrls = {
		"/mngr/"
	};

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		String sessionKey = config.getString("lottemart.epc.session.key");			//EPC 로그인 세션 키
		String uri = request.getRequestURI();					//전체 URI
		String contextPath = request.getContextPath();			//프로젝트 path
		String path = uri.substring(contextPath.length());		//contextPath 제외한 url path 정보
		
		//중복된 slash 제거
		path = path.replaceAll("//", "/").trim();
		
		if(path.indexOf("?")>-1) {
			path = path.substring(0, path.indexOf("?")+1);
		}
		
		
		boolean requiredLogin = true;	//로그인 필요 여부
		boolean sysAdminLogin = false;	//시스템 관리자만 접근 가능한 메뉴 접근여부
		
		//시스템 관리자만 접근 가능한 메뉴인지 체크
		for(String action : sysAdmUrls) {
			if(path.startsWith(action)) {
				sysAdminLogin = true;
				break;
			}
		}
		
		//URL 예외 패턴 체크
		for(String action : freeUrls) {
			if(path.startsWith(action)) {
				requiredLogin = false;
				break;
			}
		}
		
		boolean isAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
		boolean isRequest = (request.getHeader("Accept").contains("application/json") || path.endsWith(".json"));
		
		
		//단일주소 예외 체크
		List<String> loginFreeActionsUrl = Arrays.asList(loginFreeActions);
		if(loginFreeActionsUrl.contains(path)) requiredLogin = false;
		
		
		//SSO Interceptor 처리
		if(ssoLoginPage.equals(path)) {
			return SSOAuthn.authnAndSet(request, response);		//request 객체에 'ssoUser'와 'ssoUrl' 속성이 설정됩니다.
		}
		
		//--------------------------------------------- 로그인 세션 필요 페이지인 경우 확인
		if (requiredLogin || sysAdminLogin) {
			EpcLoginVO epcLoginVO = null;
			epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

			//--- 세션 check
			if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
				
				if(isRequest || isAjax){
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					return false;
				}else {
					response.sendRedirect(request.getContextPath() + confirmStartsWithSlash(retUri));
				}
			}
			
			String adminId = StringUtils.defaultString(epcLoginVO.getAdminId());		//관리자 아이디
			String[] cono = epcLoginVO.getCono();										//협력사 사업자번호 Array
			String sysAdminFg = StringUtils.defaultString(epcLoginVO.getSysAdmFg());	//시스템관리자 유형
			
			//--- 시스템 관리자만 접근 가능한 페이지일 경우, 시스템관리자 유형 체크
			if(sysAdminLogin) {
				if("".equals(sysAdminFg) || "SV".equals(sysAdminFg)) {
					throw new AccessDeniedException("해당 메뉴에 대한 접근 권한이 없습니다.");
				}
			}
			
			//--- access log insert
			//Access parameter
			String paramValue = this.getAccessParamValues(request);
			
			Map<String, String> paramMap = new HashMap<String, String>();
			String servletPath = ((HttpServletRequest) request).getServletPath();
			
			if(!"".equals(sysAdminFg)) {			// 시스템관리자
				paramMap.put("menuId", "EPC_AD");
				paramMap.put("adminId", adminId);
			}else if(!"".equals(adminId)) {			// 일반관리자
				paramMap.put("menuId", "SCM_AD");
				paramMap.put("adminId", adminId);
			}else {									// 협력사
				paramMap.put("menuId", "SCM_VE");
				paramMap.put("adminId", epcLoginVO.getCono()[0]);
			}
			
			paramMap.put("workIP", request.getRemoteAddr());
			paramMap.put("url", servletPath);
			paramMap.put("param", paramValue);
			loginDao.insertAdminWorkLogSCM(paramMap);
		}
		//------------------------------------------------------------------------

		return true;
	}

	/**
	 * URL prefix 슬래쉬 추가
	 * @param url
	 * @return String
	 */
	private String confirmStartsWithSlash(String url) {
		if (url != null & !url.startsWith("/")) {
			return "/" + url;
		} else {
			return url;
		}
	}
	
	/**
	 * Acess Parameter 추출
	 * @param request
	 * @return String
	 */
	private String getAccessParamValues(HttpServletRequest request) {
		String paramValue = "";
		try {
			Enumeration paNm = request.getParameterNames();

			while (paNm.hasMoreElements()) {
				String paramNm = (String) paNm.nextElement();

				if (paramNm.equals("WISEGRID_DATA")) {

					String wiseGridData = request.getParameter("WISEGRID_DATA");
					GridData gdReq = OperateGridData.parse(wiseGridData);

					for (int i = 0; i < gdReq.getParamNames().length; i++) {
						if ((!gdReq.getParamNames()[i].equals("staticTableBodyValue")) && (!gdReq.getParamNames()[i].equals("textList"))) {
							paramValue = paramValue + "," + gdReq.getParamNames()[i] + "=" + gdReq.getParam(gdReq.getParamNames()[i]);
						}
					}
				} else {
					if ((!paramNm.equals("staticTableBodyValue")) && (!paramNm.equals("textList"))) {
						paramValue = paramValue + "," + paramNm + "=" + request.getParameter(paramNm);
					}
				}
			}

			if (paramValue.getBytes("UTF-8").length > 2000) {
				paramValue = StringUtil.substringByBytes(paramValue, 0, 4000);
			}

		} catch (Exception e) {
			logger.error("Error Log Return.::" + e.getMessage());
			paramValue = "Error Log Return.";
		}
		
		return paramValue;
	}
	

}