package com.lottemart.epc.common.interceptor;

import com.lottemart.common.login.dao.LoginDao;
import com.lottemart.epc.edi.srm.model.SRMSessionVO;
import lcn.module.common.interceptor.UserInterceptor;
import lcn.module.framework.property.ConfigurationService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import xlib.cmc.GridData;
import xlib.cmc.OperateGridData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 입점상담 > 입점상담 신청 Interceptor
 *
 * @author AN TAE KYUNG
 * @since 2016.07.07
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 * 	수정일				수정자				수정내용
 *  -----------    	------------    ---------------------------
 *   2016.07.07  	AN TAE KYUNG	최초 생성
 *
 * </pre>
 */

@Controller
public class SrmInterceptor extends UserInterceptor {

	@Autowired
	private ConfigurationService config;

	@Autowired
	private LoginDao loginDao;

	protected final Log logger = LogFactory.getLog(getClass());

	public final String [] loginFreeActions = {
			"/edi/srm/SRMJONMain.do"		// 언어설정
		,	"/edi/srm/SRMSTP0010.do"		// 입점상담신청 절차안내(롯데마트)
		,	"/edi/srm/SRMSTP0020.do"		// 입점상담신청 절차안내(VIC 마켓)
		,	"/edi/srm/SRMSTP0030.do"		// 입점상담신청 절차안내(롯데마트몰(Online))
		,	"/edi/srm/SRMSTP0040.do"		// 입점상담신청 절차안내(임대매장)
		,	"/edi/srm/SRMSTP0050.do"		// 입점상담신청 절차안내(지원구매)
		,	"/edi/srm/SRMJON0020.do"		// 입점상담신청 로그인
		,	"/edi/srm/SRMRST0010.do"		// 입점상담결과 로그인
		,	"/edi/srm/SRMSPW0010.do"		// 비밀번호변경
		,	"/edi/srm/SRMFAQ0010.do"		// FAQ
		,	"/edi/srm/SRMFAQ001001.do"		// FAQ 글 목록
		,	"/edi/srm/SRMFAQ001002.do"		// FAQ 글 작성
		,	"/edi/srm/SRMQNA0010.do"		// 테넌트 입점 문의 작성 페이지
		,	"/edi/srm/insertQnaInfo.do"     // 테넌트 입점 문의 작성
		,	"/edi/evl/SRMEVL0010.do"		// 품질경영평가 로그인
		,	"/edi/evl/SRMEVL0020.do"		// 품질경영평가 절차안내
		,	"/edi/srm/selectJusoPopup.do"	// 우편번호 찾기 화면
		,	"/edi/srm/selectZipList.do"		// 우편번초 찾기 조회
		,	"/edi/srm/passwdChangPopup.do"	// 비밀번호 변경 팝업
		,	"/edi/srm/searchCompanyPopup.do"// 회사정보 찾기 팝업
		,	"/edi/srm/manulDown.do"			// 메뉴얼 다운로드
		,   "/edi/srm/tempPasswdChangPopup.do" // 임시비밀번호 변경 팝업
		,   "/edi/srm/pwChangeOver90Popup.do" // 90일 이상 비밀번호 변경 없을시 팝업
	};

	public final String [] freeUrls = {
			"/edi/srm/SRMJONLogin.json"			// 입점상담 로그인
		,	"/edi/srm/SRMJONReLogin.json"		// 입점상담신청 로그인(비밀번호 포함)
		,	"/edi/srm/SRMRSTLogin.json"			// 입점상담결과 로그인 처리
		,	"/edi/evl/SRMEVLLogin.json"			// 품질경영평가 로그인 처리
		,	"/edi/srm/passwdChangeCheck.json"	// 비밀번호 변경전 확인(SRMSPW0010)
		,	"/edi/srm/updatePasswdChange.json"	// 비밀번호 변경
		,	"/edi/srm/updateTempPasswdChange.json"	// 임시비밀번호 변경
		,   "/edi/srm/SRMJONSearch.json"
		,   "/edi/srm/fetchQnaCategory.json"
		,   "/edi/srm/fetchQnaStoreArea.json"
		,   "/edi/srm/fetchQnaStore.json"
	};

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		logger.debug("### SrmInterceptor preHandle start...................................................................................");

		// 입점상담신청 로그인 페이지
		String jonUri = config.getString("lottemart.srm.session.jonUri");

		// 입점상담결과 로그인 페이지
		String rstUrl = config.getString("lottemart.srm.session.rstUrl");

		// 품질경영평가 로그인 페이지
		String evlUrl = config.getString("lottemart.srm.session.evlUrl");

		String spath = request.getServletPath();
		//logger.debug("spath----->" + spath);
		String reqUrl = request.getRequestURL().toString();
		//logger.debug("reqUrl----->" + reqUrl);

		HttpSession session = request.getSession();
		SessionLocaleResolver slr = new SessionLocaleResolver();
		//logger.debug("Local----->" + slr.resolveLocale(request));

		// 필요에 따라서 사용
		String strCurrentUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		//logger.debug("strCurrentUrl----->" + strCurrentUrl);

		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String path = uri.substring(contextPath.length());

		path = path.replaceAll("//", "/");
		//logger.debug("path----->" + path);
		boolean requiredLogin = true;

		// 단일주소 예외적용
		for (String action : loginFreeActions) {
			if (path.indexOf(action) > -1 ) {
				// 로그인체크/권한체크 없이 접근가능
				requiredLogin = false;
				break;
			}
		}

		// url예외 패턴 추가
		for (String action : freeUrls) {
			if (path.indexOf(action) > -1) {
				requiredLogin = false;
				break;
			}
		}

		//logger.debug("requiredLogin----->" + requiredLogin);

		if (requiredLogin) {
			String lastUrl = spath.substring(spath.lastIndexOf("/") + 1, spath.length());
			//logger.debug("lastUrl----->" + lastUrl);

			// 입점상담 Session VO
			SRMSessionVO srmSessionVO = null;

			if (spath.indexOf("/edi/evl/") > -1) {	// 품질경영평가
				srmSessionVO = (SRMSessionVO) request.getSession().getAttribute(config.getString("lottemart.srm.evl.session.key"));

				// 세션 check
				if (srmSessionVO == null || srmSessionVO.getEvalSellerCode() == null) {
					//response.sendRedirect(request.getContextPath() + confirmStartsWithSlash(evlUrl));
					response.sendRedirect(evlUrl);

					return false;
				}
			} else {	// 입점상담
				srmSessionVO = (SRMSessionVO) request.getSession().getAttribute(config.getString("lottemart.srm.session.key"));

				// 세션 check
				if (srmSessionVO == null || srmSessionVO.getIrsNo() == null) {

					if (lastUrl.indexOf("RST") > -1) {		// 입점상담결과 로그인
						//response.sendRedirect(request.getContextPath() + confirmStartsWithSlash(rstUrl));
						response.sendRedirect(rstUrl);
					} else {								// 입점상담신청 로그인
						//response.sendRedirect(request.getContextPath() + confirmStartsWithSlash(jonUri));
						response.sendRedirect(jonUri);
					}

					return false;
				}
			}

			/*if (lastUrl.indexOf("EVL") > -1) {
				srmSessionVO = (SRMSessionVO) request.getSession().getAttribute(config.getString("lottemart.srm.evl.session.key"));

				// 세션 check
				if (srmSessionVO == null || srmSessionVO.getEvalSellerCode() == null) {
					response.sendRedirect(request.getContextPath() + confirmStartsWithSlash(evlUrl));

					return false;
				}
			} else {
				srmSessionVO = (SRMSessionVO) request.getSession().getAttribute(config.getString("lottemart.srm.session.key"));

				// 세션 check
				if (srmSessionVO == null || srmSessionVO.getIrsNo() == null) {

					if (lastUrl.indexOf("RST") > -1) {		// 입점상담결과 로그인
						response.sendRedirect(request.getContextPath() + confirmStartsWithSlash(rstUrl));
					} else {								// 입점상담신청 로그인
						response.sendRedirect(request.getContextPath() + confirmStartsWithSlash(jonUri));
					}

					return false;
				}
			}*/

			//smr 시스템 로그 입력
			//파라메터 정보 저장
			if(srmSessionVO.getSellerCode() != null && !"".equals(srmSessionVO.getSellerCode())){
				String paramValue = "";
				try {
					Enumeration paNm =  request.getParameterNames();

					while (paNm.hasMoreElements()) {
						String paramNm = (String)paNm.nextElement();

						if(paramNm.equals("WISEGRID_DATA")){

							String wiseGridData = request.getParameter("WISEGRID_DATA");
							GridData  gdReq = OperateGridData.parse(wiseGridData);

							for(int i=0;i<gdReq.getParamNames().length ;i++){
								if((!gdReq.getParamNames()[i].equals("staticTableBodyValue")) && (!gdReq.getParamNames()[i].equals("textList"))){
									paramValue = paramValue + "," +gdReq.getParamNames()[i] + "=" + gdReq.getParam(gdReq.getParamNames()[i]);
								}
							}
						}else{
							if((!paramNm.equals("staticTableBodyValue")) && (!paramNm.equals("textList"))){
								paramValue = paramValue + "," +paramNm + "=" + request.getParameter(paramNm);
							}
						}
					}

					if(paramValue.getBytes("UTF-8").length > 2000 ){
						paramValue = paramValue.substring(0,2000);
					}

				} catch (Exception e) {
					paramValue = "Error Log Return.";
				}
				Map<String, String> paramMap = new HashMap<String, String>();
				String servletPath = ((HttpServletRequest)request).getServletPath();

				paramMap.put("menuId", "SRM");
				paramMap.put("adminId", srmSessionVO.getSellerCode());
				paramMap.put("workIP", request.getRemoteAddr());
				paramMap.put("url", servletPath);
				paramMap.put("param", paramValue);
				loginDao.insertAdminWorkLogSCM(paramMap);

			}
		}

		return true;
	}

}
