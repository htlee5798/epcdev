package com.lottemart.epc.edi.main.controler;

import java.util.Map;
import java.lang.RuntimeException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lcn.module.framework.property.ConfigurationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lottemart.epc.common.model.EpcLoginVO;

/**
 * @Description : EDI 협력사 메인화면
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
 * </pre>
 */
@Controller
public class MainEdiViewController {
	private static final Logger logger = LoggerFactory
			.getLogger(MainEdiViewController.class);

	@Resource(name = "configurationService")
	private ConfigurationService config;

	/**
	 * @Description : 협력사Top
	 * @Method Name : viewEdiTop
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("/edi/main/viewEdiTop.do")
	public String viewEdiTop(HttpServletRequest request) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;

		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);

		return "/edi/main/ediTop";
	}

	/**
	 * @Description : 협력사Left
	 * @Method Name : viewEdiLeft
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/edi/main/viewEdiLeft.do")
	public String viewEdiLeft(@RequestParam Map<String, Object> map,
			ModelMap model, HttpServletRequest request) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		if (map.get("pgm_code") == null
				|| map.get("pgm_code").toString().length() <= 0)
			map.put("pgm_code", "ORD");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		model.addAttribute("pgm", map);
		model.addAttribute("epcLoginVO", epcLoginVO);

		return "/edi/main/ediLeft";
	}

	/**
	 * @Description : 협력사 Main Frame
	 * @Method Name : viewEdiMain
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("/edi/main/viewEdiMain.do")
	public String viewEdiMain(HttpServletRequest request) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		String defPgmID = request.getParameter("def") == null ? "" : request
				.getParameter("def");

		int pgmId;
		if (defPgmID.length() > 0) {
			pgmId = defPgmID.length();
			for (int i = 0; i < pgmId; i++) {
				if (Character.isWhitespace(defPgmID.charAt(i))) {
					request.setAttribute("defPgmID", defPgmID);
				}
			}
		}
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(
				sessionKey);

		if (epcLoginVO == null) {
			logger.debug("Main Access error : session value 가 존재하지 않습니다.");
			throw new IllegalArgumentException("세션정보가 올바르지 않습니다. 다시 로그인하여 주세요");
		}

		return "/edi/main/ediIndex";
	}

	/**
	 * Desc : 기능별 Q&A , 팀별 전화번호 팝업
	 * @Method Name : viewEtcMain
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("/edi/main/viewEtcMain.do")
	public String viewEtcMain(HttpServletRequest request) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute( sessionKey);
		String gubn = request.getParameter("gubn");

		if (epcLoginVO == null) {
			logger.debug("Main Access error : session value 가 존재하지 않습니다.");
			throw new IllegalArgumentException("세션정보가 올바르지 않습니다. 다시 로그인하여 주세요");
		}
		if(gubn.equals("Q")){
			return "main/qnaPop";
		}else if (gubn.equals("T")) {
			return "main/telPop";
		}
		return "/edi/main/afMain";
	}

	/**
	 * @Description : 협력사 old Main Frame
	 * @Method Name : viewEdiMain
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("/edi/main/viewEdiMainOld.do")
	public String viewEdiMainOld(HttpServletRequest request) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		String defPgmID = request.getParameter("def") == null ? "" : request
				.getParameter("def");

		int pgmId;
		if (defPgmID.length() > 0) {
			pgmId = defPgmID.length();
			for (int i = 0; i < pgmId; i++) {
				if (Character.isWhitespace(defPgmID.charAt(i))) {
					request.setAttribute("defPgmID", defPgmID);
				}
			}
		}
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(
				sessionKey);

		if (epcLoginVO == null) {
			logger.debug("Main Access error : session value 가 존재하지 않습니다.");
			throw new IllegalArgumentException("세션정보가 올바르지 않습니다. 다시 로그인하여 주세요");
		}

		return "/edi/main/ediIndexOld";
	}

	/**
	 * @Description : 협력사Top
	 * @Method Name : viewEdiTop
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("/edi/main/viewEdiTopOld.do")
	public String viewEdiTopOld(HttpServletRequest request) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;

		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);

		return "/edi/main/ediTopOld";
	}

	/**
	 * @Description : 협력사Left
	 * @Method Name : viewEdiLeft
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/edi/main/viewEdiLeftOld.do")
	public String viewEdiLeftOld(@RequestParam Map<String, Object> map,
			ModelMap model, HttpServletRequest request) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		if (map.get("pgm_code") == null
				|| map.get("pgm_code").toString().length() <= 0)
			map.put("pgm_code", "ORD");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		model.addAttribute("pgm", map);
		model.addAttribute("epcLoginVO", epcLoginVO);

		return "/edi/main/ediLeftOld";
	}

}
