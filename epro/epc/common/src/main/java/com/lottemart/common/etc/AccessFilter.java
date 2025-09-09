package com.lottemart.common.etc;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.lcnjf.util.StringUtil;
import com.lottemart.common.login.dao.LoginDao;
import com.lottemart.common.login.model.LoginSession;
import com.lottemart.common.util.DataMap;

import xlib.cmc.GridData;
import xlib.cmc.OperateGridData;


public class AccessFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(AccessFilter.class);
	// 로그인체크/권한체크 없이 접근가능 페이지
	// TODO : [김연민] 매출 부가세 오픈시 한번만 처리후 삭제해야함 "/surtax/pandora/datacleaning.do" //오픈시 매출 부가세 미처리 데이터 처리
	// TODO : [김연민] 매출 부가세 오픈후 약 1달 유지 "/surtax/pandora/monitoring.do" //오픈후 매출 부가세 모니터링
	//(/lottemart-cc_surtax/src/main/webapp/WEB-INF/jsp/statistics/PCCMSTA0004.jsp 안에 부가세 확인 팝업 소스 제거해야함)
	public static final String[] loginFreeActions = {
		"/naver/ep/synchronization.do",
		"/common/login/viewLoginForm.do",
		"/common/login/viewExtLoginForm.do",
		"/common/login/otpConfirm.do",
		"/common/login/login.do",
		"/common/login/extLogin.do",
		"/common/login/ssologin.do",
		"/common/login/loginTest.do",
		"/common/login/logout.do",
		"/common/login/viewChangePwdForm.do",
		"/common/login/updateAdminPwd.do",
		"/common/login/viewAdminPwdAuthenticForm.do",
		"/common/login/updateAdminAuthPwd.do",
		"/common/login/updateAdminSelfCert.do",
		"/common/login/noIE.do", // IE 사용금지
		"/common/view/showView.do?viewName=common/endOfSessionError",
		"/common/view/showView.do?viewName=common/authError",
		"/common/view/showView.do?viewName=common/ajaxAuthError",
		"/common/view/showView.do?viewName=common/gridAuthError",
		"/common/view/showView.do?viewName=common/blank",
		"/common/login/gnb.do",
		"/common/login/lnb.do",
		"/common/login/content.do",
		"/common/login/menuClick.do",
		"/common/login/sessionOutView.do",
		"/hocommon/accept/find.do",
		"/dcmobile/login/PDCMGOT0001.do", 		//단말기 로그인폼
		"/dcmobile/login/login_test.do",		//단말기 로그인 테스트
		"/dcmobile/login/login.do",  			//단말기 로그인
		"/dcmobile/login/logout.do",  			//단말기 로그아웃
		"/dcmobile/login/viewAdminPwdAuthenticForm.do",
		"/dcmobile/login/viewChangePwdForm.do",
		"/mobile/got/members/login.do",  		//모바일 got 로그인
		"/mobile/got/members/logout.do",  		//모바일 got 로그아웃
		"/mobile/got/members/password/authentication.do",//모바일 got 비밀번호(인증)변경 폼
		"/mobile/got/members/password/update.do",//모바일 got 비밀번호 변경 폼
		"/mobile/got/app/certification/check.do",//모바일 got 인증서체크
		"/mobile/got/common/main.do",			//모바일 got 메인
		"/FileUp.do",  /**운영 전환시 삭제 **/
		"/FileDown.do", /**운영 전환시 삭제 **/
		"/FileUpload.do", /**운영 전환시 삭제 **/
		"/FileDownlist.do", /**운영 전환시 삭제 **/
		"/sample/", /**운영 전환시 삭제 **/
		"validator.do", /**운영 전환시 삭제 **/
		"/WMSIF8305/WMSIF8305.do",  /** WMS 결품 (예치금,취소) 시 PP모듈 호출하여 주문취소 및 송장 재생성 로직 반영 프로세스 호출 **/
		"/WMSIF8305/WMSIF8305freshExchangeDeposit.do", /** 신선WMS 작업 - 교환/재배송시 pp쪽 재계산 로직 호출을 위해 추가 **/
		"/PCCMDEM0012/TaxbillResultUpdate.do", // 전자세금계산서 결과 업데이트 및 메일전송
		"/paymentTest/transStrForm.do",
		"/paymentTest/transStr.do",
		"/dpmakeplan/pdpmmpl0011/doAdjustExecWithTmap.do", //배송기사 모바일(new) 배송 순번 변경
		"/product/PSCMPRD0060.do",         //점포검색 화면
		"/product/PSCMPRD0060CodeList.do", //점포검색
		"/product/PBOMPRD0060.do",         //점포검색 화면
		"/product/PBOMPRD0060CodeList.do", //점포검색
		"/product/PBOMPRD0061.do",         // 상품검색 화면
		"/product/PBOMPRD0061Search.do", // 상품검색
		"/test/dataCheck.do",
		"/newTemplate/PBOMTEM0200.do",      // 템플릿검색 화면
		"/bosmobile/common/login/mViewLoginForm.do", 	//모바일BOS 로그인 페이지
		"bosmobile/common/mlogin.do",
		"/bosmobile/common/mainDashBoard.do",
		"/bosmobile/common/mainDashBoard2.do",
		"/bosmobile/common/login/mssologin.do",
		"/bosmobile/common/login/logout.do",
		"/bosmobile/storePopUp/storePopUpView.do",
		"/bosmobile/storePopUp/storeSearch.do",
		"/common/productImsiDetail.do",
		"/mobile/common/menuClick.do",
		"/mobile/common/login/mobileGnb.do",
		"/PCCMAGT0041/exchangReceiptFront.do",			//교환접수
		"/PCCMAGT0042/cancelExchangeOrderFront.do",	//.교환접수취소처리
		"/common/tprMpicCallback.do",
		"/meshkorea/meshkoreaReceive.do", //메쉬코리아 수신을 위한 PP모듈 호출
	    "/orderReturn/orderPartReturnReceiptFront.do",   	/* 부분반품접수 */
	    "/orderReturn/orderPartReturnCancel.do",   		/* 부분반품접수 취소 */
	    "/orderReturn/orderReturnDeliAddrModify.do",   	/* 배송지변경 */
	    "/periorder/batch/createOrder.do",	//정기배송 결제배치
		"/product/prodOffMdAprv/updateNewProductInfoSet.do", //상품기본정보생성 호출
		"/surtax/pandora/datacleaning.do", //오픈시 매출 부가세 미처리 데이터 처리
		"/surtax/pandora/monitoring.do", //오픈후 매출 부가세 모니터링
		"/surtax/pandora/order_info.do", //오픈전 매출 부가세 데이터 확인
		"/product/prodOffMdAprv/updateFNewProductInfoSet.do", //패션상품기본정보생성 호출
		"/vendor/penalty/access.do",	//위수탁업체 송장미등록 패널티 부과 배치
		"/surtax/process.do",
		"/surtax/update.do",
		"/holidaystore/specifyholiday.do",
		"/sms/order/order_send_sms.do",
		"/sms/kakaotalk_failover/send_sms.do",
		"/add/experience/order.do",
		"/jira/csr/update.do",	//JIRA API 연동
		"/mobile/qpsgot/login/loginForm.do",						// qpsgot 로그인 화면
		"/mobile/qpsgot/login/login.do",							// qpsgot 로그인
		"/mobile/qpsgot/login/passwordAuthForm.do",			// qpsgot 비밀번호 인증
		"/mobile/qpsgot/login/passwordChangeForm.do",		// qpsgot 비밀번호 변경
		"/mobile/qpsgot/api/getPickingList.do",
		"/mobile/qpsgot/common/appDownload.do",
		"/api/ppToWcsPackingDone.do",
		"/api/semi/ppToWcsPackingDone.do",					// semidark 패킹완료
		"/api/semi/ppToWcsPickOrderTest.do",				// 점별 WCS서버 접속 TEST
		"/mobile/qpsgot/api/updatePickingFinish.do",
		"/wcsif/wcsSndTest/responseTest.do",
		"/product/updateQnaInfoSet.do"	,	// EC통합 Qna 관리 (데이터 이관 등록)
		"/product/updateStdInfoSet.do"		// EC통합 표카변경 관리 (데이터 이관 등록)
	};

	// 권한체크 없이 접근가능 페이지
	public static final String[] accessFreeActions = {
		"/common/login/viewMain.do",
		"/common/login/logout.do",
		"/common/login/noIE.do", // IE 사용금지
		"/dcmobile/login/login.do",  	//단말기 로그인
		"/dcmobile/login/login_test.do",//단말기 로그인 테스트
		"/dcmobile/login/logout.do",  	//단말기 로그아웃
		"/mobile/got/members/login.do",  		//모바일 got 로그인
		"/mobile/got/members/logout.do",  		//모바일 got 로그아웃
		"/mobile/got/app/certification/check.do",//모바일 got 인증서체크
		"/mobile/got/common/main.do",			//모바일 got 메인
		"/notice/popupNoticeView.do",
		"/dpmakeplan/pdpmmpl0011/doAdjustExecWithTmap.do", //배송기사 모바일(new) 배송 순번 변경
		"/bosmobile/common/mlogin.do"
		, "/orderReturn/orderPartReturnReceiptFront.do"   	/* 부분반품접수 */
		, "/orderReturn/orderPartReturnCancel.do"   		/* 부분반품접수 취소 */
		, "/orderReturn/orderReturnDeliAddrModify.do"   	/* 배송지변경 */
		, "/common/favorite/selectUserSite.do"
		, "/common/favorite/selectFavorMenuList.do"
		, "/CommonCodeHelperController/generateOptionTag.do"
		, "/common/admin/search.do"
		, "/system/userAuthorityManage/admin/selector.do"
		,"/jira/csr/update.do"	//JIRA API 연동
		, "/mobile/qpsgot/login/loginForm.do"							// qpsgot 로그인 화면
		, "/mobile/qpsgot/login/login.do"								// qpsgot 로그인
		, "/mobile/qpsgot/login/passwordAuthForm.do"			// qpsgot 비밀번호 인증
		, "/mobile/qpsgot/login/passwordChangeForm.do"		// qpsgot 비밀번호 변경
		, "/mobile/qpsgot/api/getPickingList.do"
		, "/mobile/qpsgot/common/appDownload.do"
		, "/api/ppToWcsPackingDone.do"
		, "/api/semi/ppToWcsPackingDone.do"					// semidark 패킹완료
		, "/api/semi/ppToWcsPickOrderTest.do"				// 점별 WCS서버 접속 TEST
		, "/mobile/qpsgot/api/updatePickingFinish.do"
	};

	public final static String sessionOutUri = "/common/login/sessionOutView.do";
	public final static String dcmobileLoginUri = "/dcmobile/login/PDCMGOT0001.do";
	public final static String mobileGotLoginUri = "/mobile/got/members/login.do";
	public final static String authErrorUri = "/common/view/showView.do?viewName=common/authError";
	public final static String ajaxAuthErrorUri = "/common/view/showView.do?viewName=common/ajaxAuthError";
	public final static String gridAuthErrorUri = "/common/view/showView.do?viewName=common/gridAuthError";
	public final static String qpsGotLoginFormUri = "/mobile/qpsgot/login/loginForm.do";

	public void init(FilterConfig arg0) throws ServletException {
		// Do nothing
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException  {
		logger.debug("============ AccessFilter ====== START ============");
		logger.debug("============ AccessFilter ====== START2 ============");

		// UserAgent 파싱
		Map<String, Object> userAgentMap = getUserAgent(request);
		((HttpServletRequest)request).setAttribute("uuid", userAgentMap.get("uuid"));

		String servletPath = ((HttpServletRequest)request).getServletPath();
        String uri = servletPath;

        // 로그인체크/권한체크 없이 접근가능 체크
        for (String action : loginFreeActions) {
			if (uri.equals(action)) {
        		try {

					allowAccess(request, response, chain);
					logger.debug("AccessFilter.action->"+action);
					logger.debug("AccessFilter loginFreeActions bypass!!");
					logger.debug("============ AccessFilter ====== END1 ============");
					return;
				} catch (Exception e){
        			logger.debug("sss",e);
				}

        	}else if ((uri!=null)&&(uri.startsWith(action))) { /**운영 전환시 삭제 **/
        		allowAccess(request, response, chain);
				return;
        	}else if ((uri!=null)&&(uri.endsWith(action))) { /**운영 전환시 삭제 **/
        		allowAccess(request, response, chain);
				return;
        	}
        }

		LoginSession loginSession = LoginSession.getLoginSession((HttpServletRequest)request);
		if (loginSession == null ) {
			logger.debug("AccessFilter.loginSession null  uri -> " + uri);

			if(uri.startsWith("/dcmobile/")) {
				((HttpServletResponse)response).sendRedirect(((HttpServletRequest)request).getContextPath() + dcmobileLoginUri);
			}else if(uri.startsWith("/mobile/got/")) {
				((HttpServletResponse)response).sendRedirect(((HttpServletRequest)request).getContextPath() + mobileGotLoginUri);
			}else if(uri.startsWith("/mobile/qpsgot/")) {
				((HttpServletResponse)response).sendRedirect(((HttpServletRequest)request).getContextPath() + qpsGotLoginFormUri+"?resultCode=-3");
			}else {
				((HttpServletResponse)response).sendRedirect(((HttpServletRequest)request).getContextPath() + sessionOutUri);
			}
			logger.debug("============ AccessFilter ====== END4 ============");
			return;
		} else {
			logger.debug("AccessFilter.loginSession else  uri -> " + uri);
			// 실행권한 체크 - 로그인 경우
			// 입력된 url 경로가 showView.do 인 경우는 파라미터를 포함하여 체크하고,
			// 기타의 경우는 파라미터는 제외하고 경로만으로 체크한다.
			String scenario = servletPath;
			if (StringUtils.startsWith(uri, "/common/view/showView.do")) {
				scenario = uri;
			}
			logger.debug("AccessFilter.servletPath -> " + servletPath);
			// 권한체크 불필요한 페이지 체크(로그인은 필요함)
	        for (String action : accessFreeActions) {
	        	if (action.equals(scenario)) {
					allowAccess(request, response, chain);
					logger.debug("AccessFilter.scenario->"+scenario);
					logger.debug("AccessFilter accessFreeActions bypass!!");
					logger.debug("============ AccessFilter ====== END2 ============");
					return;
	        	}
	        }

	        /*
	         * QPS GOT 중복 로그인 방지(기존 로그인 해지)
	         */
	        if(uri.startsWith("/mobile/qpsgot/")) {
	        	ApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
	        	LoginDao loginDao = (LoginDao)ctx.getBean("loginDao");

	        	try {
					DataMap loginInfo = loginDao.getAdminLogin(loginSession.getLoginId());
					String uuid = StringUtil.null2str((String) loginInfo.get("UUID"),"");
					String currentUuid = StringUtil.null2str((String) userAgentMap.get("uuid"));
					
					if(!StringUtil.isEmpty(currentUuid) && !uuid.equals(currentUuid)) {
						logger.debug(">>> 중복 로그인으로 인해 로그인이 해지됩니다.");
						((HttpServletRequest)request).getSession().removeAttribute(LoginSession.LOTTEMART_BOS_LOGIN_SESSION_KEY);
						((HttpServletResponse)response).sendRedirect(((HttpServletRequest)request).getContextPath() + qpsGotLoginFormUri+"?resultCode=-4");
						return;
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
	        }

	        Map<String, String> paramMap = new HashMap<String, String>();
			try {
				// 실행권한 체크
				ApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
				LoginDao loginDao = (LoginDao)ctx.getBean("loginDao");

				paramMap.put("adminId", loginSession.getAdminId());
				paramMap.put("sysDivnCd", loginSession.getSysDivnCd());
				paramMap.put("url", uri);
				//파라메터 정보 저장
				String paramValue = "";

				DataMap resultMap = new DataMap();
				String authChk = null;
				String urlAuthDivnCd = null;
				String autAuthDivnCd = null;

				// 모바일인 경우 pass 2016.8.26
				if ("07".equals(loginSession.getSysDivnCd())) {
					resultMap = null;
					authChk = "1";
				}
				// CC인 경우 2017.01.11
				else if ("02".equals(loginSession.getSysDivnCd())) {
					resultMap = loginDao.authCheckForCC(paramMap);
				} else {
					resultMap = loginDao.authCheck(paramMap);
				}

				if (resultMap != null) {
					authChk = resultMap.getString("AUTH_CHK");
					logger.debug("authChk->"+authChk);
					urlAuthDivnCd = resultMap.getString("URL_AUTH_DIVN_CD");  //2016.03.16 추가
					autAuthDivnCd = resultMap.getString("AUT_AUTH_DIVN_CD");  //2016.03.16 추가
					request.setAttribute("urlAuthDivnCd", urlAuthDivnCd);     //2016.03.16 추가
					request.setAttribute("autAuthDivnCd", autAuthDivnCd);     //2016.03.16 추가
				}

				if (authChk == null) {
					allowAccess(request, response, chain);
					return;
				} else if ("0".equals(authChk)) /* 메뉴에 대한 권한이 없을 경우 */{
					String gridData = request.getParameter("WISEGRID_DATA");

					/* ajax로 넘어온 경우 */
					if ("XMLHttpRequest".equals(((HttpServletRequest)request).getHeader("x-requested-with"))) {
						((HttpServletResponse)response).sendRedirect(((HttpServletRequest)request).getContextPath() + ajaxAuthErrorUri);
					}
					/* multipart로 넘어온 경우 */
					else if(request.getContentType() != null && request.getContentType().indexOf("multipart") != -1){
						((HttpServletResponse)response).sendRedirect(((HttpServletRequest)request).getContextPath() + authErrorUri);
					}
					/* WISEGRID에서 넘어온 경우 */
					else if (gridData != null && !"".equals(gridData)) {
						((HttpServletResponse)response).sendRedirect(((HttpServletRequest)request).getContextPath() + gridAuthErrorUri);
					}
					/* 그 외의 경우 */
					else {
						((HttpServletResponse)response).sendRedirect(((HttpServletRequest)request).getContextPath() + authErrorUri);
					}

	    	        return;
				}

	            // 로그인체크 및 실행권한 체크 성공 성공
				allowAccess(request, response, chain);

		        try {
		        	Enumeration paNm =  request.getParameterNames();

			        while (paNm.hasMoreElements()) {
			        	String paramNm = (String)paNm.nextElement();

			        	if(paramNm.equals("WISEGRID_DATA")){

			        			String wiseGridData = request.getParameter("WISEGRID_DATA");
			        			GridData  gdReq = OperateGridData.parse(wiseGridData);

			        			for(int i=0;i<gdReq.getParamNames().length ;i++){
			        				paramValue = paramValue + "," +gdReq.getParamNames()[i] + "=" + gdReq.getParam(gdReq.getParamNames()[i]);
			        			}
			        	}else{
							if ("menuId".equals(paramNm)) {
			        			paramMap.put("menuId", request.getParameter(paramNm));
			        		}
			        		paramValue = paramValue + "," +paramNm + "=" + request.getParameter(paramNm);
			        	}
			        }

			        //4000 byte 이하로 paramValue 수정
					byte[] strbyte = paramValue.getBytes();
					if (strbyte.length > 3999) {
						StringBuilder stringBuilder = new StringBuilder();
						int tempCount = 0;
						for(char ch : paramValue.toCharArray()){
							tempCount += String.valueOf(ch).getBytes().length;
							if(3999 < tempCount){
								break;
							}
							stringBuilder.append(ch);
						}
						paramValue = stringBuilder.toString();
					}

		        } catch (Exception e) {
					paramValue = "Error Log Return.";
				}

				paramMap.put("adminId", loginSession.getAdminId());
				logger.info("request.getRemoteAddr() : "+request.getRemoteAddr());
				paramMap.put("workIP", request.getRemoteAddr());
				paramMap.put("url", uri);
				paramMap.put("param", paramValue);

				loginDao.insertAdminWorkLog(paramMap);

				logger.debug("============ AccessFilter ====== END3 ============");

			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
				throw new ServletException(e);
			}

			return;
		}
	}

	public void destroy() {
		// Do nothing
	}

	// 접근허용
	private void allowAccess(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		((HttpServletResponse)response).addHeader( "Pragma", "no-cache" );
		((HttpServletResponse)response).addHeader( "Expires", "-1" );
        chain.doFilter( request, response );
	}

	/**
	 * UserAgent 파싱
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getUserAgent(ServletRequest request) {

		HashMap<String, Object> userAgentMap = new HashMap<String, Object>();

		try {
			String userAgentStr = ((HttpServletRequest)request).getHeader("User-Agent");
			if(!"".equals(StringUtil.null2str(userAgentStr))) {

				StringBuilder deviceIdstr = new StringBuilder();
	            String[] userAgentArray = userAgentStr.split(" ");
	            for(String item : userAgentArray){
	                item = StringUtil.null2str(item);
	                String[] itemArray = item.split("/");
	                if(itemArray!=null && itemArray.length==2){
	                	userAgentMap.put(itemArray[0], itemArray[1]);
	                }else if(itemArray!=null && itemArray.length > 2){
	                    for (int i = 0; i < itemArray.length; i++) {
	                        if(i != 0){
	                            deviceIdstr.append(itemArray[i]);
	                            if(i != itemArray.length -1){
	                                deviceIdstr.append("/");
	                            }
	                        }
	                    }
	                    userAgentMap.put(itemArray[0], deviceIdstr.toString());
	                }
	            }
			}
		} catch(Exception e) {
			logger.trace(e.getMessage(), e);
		}

		return userAgentMap;
	}
}
