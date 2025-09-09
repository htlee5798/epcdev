package com.lottemart.epc.edi.srm.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lcn.module.framework.property.ConfigurationService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lottemart.epc.common.model.EpcLoginVO;

/**
 * SRM 정보 메인화면 Controller
 *
 * @author SHIN SE JIN
 * @since 2016.07.26
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.07.26  	SHIN SE JIN		 최초 생성
 *
 * </pre>
 */

@Controller
public class MainSrmViewController {

	@Resource(name = "configurationService")
	private ConfigurationService config;

	//private static final Logger logger = LoggerFactory.getLogger(MainSrmViewController.class);


	/**
	 * SRM Top
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/edi/main/viewSrmTop.do")
	public String viewSrmTop(HttpServletRequest request) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;

		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);

		return "edi/srm/srmTop";
	}

	/**
	 * SRM Left
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/main/viewSrmLeft.do")
	public String viewSrmLeft(@RequestParam Map<String,Object> map, ModelMap model, HttpServletRequest request) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		if(map.get("pgm_code") == null || map.get("pgm_code").toString().length() <=0)
			map.put("pgm_code", "ORD");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		model.addAttribute("pgm",map);
		model.addAttribute("epcLoginVO",epcLoginVO);

		return "edi/srm/srmLeft";
	}

	/**
	 * SRM Main Frame
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/edi/main/viewSrmMain.do")
	public String viewSrmMain(HttpServletRequest request, @RequestParam(required = false) String def) throws Exception {

		String defPgmID = def == null ? "":def;

		int id;
		if (defPgmID.length() > 0) {
			id = defPgmID.length();
			for (int i = 0; i < id; i++) {
				if (Character.isWhitespace(defPgmID.charAt(i))) {
					request.setAttribute("defPgmID", defPgmID);
				}
			}
		}

		return "edi/srm/srmIndex";
	}

}
