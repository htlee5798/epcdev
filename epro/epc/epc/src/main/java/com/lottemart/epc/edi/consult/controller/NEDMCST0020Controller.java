package com.lottemart.epc.edi.consult.controller;

import java.util.Locale;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



/**
 * 협업정보 - > 협업정보  - > 거래절차  Controller
 * 
 * @author SUN GIL CHOI
 * @since 2015.11.04
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ---------------------------
 *   2015.11.04  	SUN GIL CHOI   최초 생성
 *
 * </pre>
 */
@Controller
public class NEDMCST0020Controller {

	
	/**
	 *  협업정보 - > 협업정보  - > 거래절차  
	 * @param locale
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/consult/NEDMCST0020.do", method = RequestMethod.GET)
	public String tranProcess(Locale locale,  ModelMap model) {
		
		return "/edi/consult/NEDMCST0020";
	}
	
	/**
	 *  협업정보 - > 협업정보  - > 판매장려금 수수료 결정
	 * @param locale
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/consult/NEDMCST0021.do", method = RequestMethod.GET)
	public String tranProcess2(Locale locale,  ModelMap model) {
		
		return "/edi/consult/NEDMCST0021";
	}
	
	/**
	 *  협업정보 - > 협업정보  - > 매장내 위치이동
	 * @param locale
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/consult/NEDMCST0022.do", method = RequestMethod.GET)
	public String tranProcess3(Locale locale,  ModelMap model) {
		
		return "/edi/consult/NEDMCST0022";
	}
	
	
    
}
