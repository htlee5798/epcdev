package com.lottemart.epc.edi.srm.controller;

import java.io.File;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lcn.module.framework.property.ConfigurationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.lottemart.epc.edi.srm.model.SRMSessionVO;

/**
 * 입점상담 Main Controller
 * 
 * @author AN TAE KYUNG
 * @since 2016.07.06
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           			수정내용
 *  -----------    	------------    ------------------
 *   2016.07.06  	AN TAE KYUNG	최초 생성
 *
 * </pre>
 */
@Controller
public class SRMJON0000Controller {

	private static final Logger logger = LoggerFactory.getLogger(SRMJON0000Controller.class);
	
	@Autowired
	private ConfigurationService config;
	
	public static final String[] INVALID_FILE_PATH = {"../", ".."};
	
	/**
	 * 초기화
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/SRMJONMain.do")
	public String init(@RequestParam(required = false) String lang, HttpServletRequest request) throws Exception {
		//logger.debug("lang----->" + lang);
		
		HttpSession session = request.getSession();
		Locale locales = null;
		
		
		//넘어온 파라미터에 ko가 있으면 Locale의 언어를 한국어로 바꿔준다. 그렇지 않을 경우 영어로 설정한다.
		if (lang != null) {
			if (lang.equals("en")) {
				locales = Locale.ENGLISH;
			} else {
				locales = Locale.KOREAN;
			}
		} else {	// lang이 null일 경우 세션의  Locale 값으로 설정
			locales = (Locale) session.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
		}
		
		
		//세션에 존재하는 Locale을 새로운 언어로 변경해준다.
		session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locales);
		
		
		//logger.debug("----->" + SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME + "::" + locales);
		
		//해당 컨트롤러에게 요청을 보낸 주소로 돌아간다.
		//String redirectURL = "redirect:" + request.getHeader("referer");
		//return redirectURL;
		return "/edi/srm/SRMJONMain";
	}
	
	/**
	 * 입점상담 로그아웃
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/SRMJONLogout.do")
	public String SRMSLogout(HttpServletRequest request) throws Exception {
		
		SRMSessionVO session = (SRMSessionVO)request.getSession().getAttribute(config.getString("lottemart.srm.session.key"));
		
		if (session != null) {
			//세션 삭제(로그아웃)
			//request.getSession().removeAttribute(config.getString("lottemart.srm.session.key"));
			request.getSession().invalidate();
		}
		
		return "/edi/srm/SRMJON0020";
	}
	
	/**
	 * 메뉴얼 다운로드
	 * @param HttpServletRequest
	 * @param Map<String, Object>
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/manulDown.do")
	public ModelAndView manualDown(HttpServletRequest request, Map<String, Object> model, @RequestParam String manulName) throws Exception {

		StringBuffer stordFilePath = new StringBuffer();
		String imageUploadPath = config.getString("edi.srm.manual.path");

		String original = manulName;
		
		if (original.equals("1")) {			// 입점상담 메뉴얼
			original = config.getString("edi.srm.manual.fileName1");
			
		} else if (original.equals("2")) {	// 품질경영평가 메뉴얼
			original = config.getString("edi.srm.manual.fileName2");
			
		} else if (original.equals("3")) {	// SRM 메뉴얼
			original = config.getString("edi.srm.manual.fileName3");
			
		} else if (original.equals("4")) {	// 관련 사이트 정보
			original = config.getString("edi.srm.manual.fileName4");
			
		} else if (original.equals("5")) {	// 품질경영평가(공장점검)시 체크리스트
			original = config.getString("edi.srm.manual.fileName5");
			
		} else if (original.equals("6")) {	// 비밀번호 변경 메뉴얼
			original = config.getString("edi.srm.manual.fileName6");
			
		} else {
			logger.debug("[invalid file path or name]");
			request.setAttribute("message", "invalid file name");
			return new ModelAndView("/common/tools/FileDown");
		}

		stordFilePath.append(imageUploadPath).append("/").append(original);

		String file = stordFilePath.toString();

		for(int i=0; i<INVALID_FILE_PATH.length; i++){
			if(file.indexOf(INVALID_FILE_PATH[i]) >=0 || original.indexOf(INVALID_FILE_PATH[i]) >= 0){
				logger.debug("[invalid file path or name]");
				request.setAttribute("message", "invalid file name");
				return new ModelAndView("/common/tools/FileDown");
			}
		}

		model.put("file", new File(file));
		model.put("fileName", original);
		//--------------------------------------------------

		return new ModelAndView("downloadView", model);
	}
	
}
