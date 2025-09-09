package com.lottemart.epc.edi.consult.controller;

import java.util.Locale;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



/**
 * 협업정보 - > 협업정보  - > Loan  Controller
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
public class NEDMCST0010Controller {

	
	/**
	 *  협업정보 - > 협업정보  - > Loan  
	 * @param locale
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/consult/NEDMCST0010.do", method = RequestMethod.GET)
	public String loanMain(Locale locale,  ModelMap model) {
		
		return "/edi/consult/NEDMCST0010";
	}
	
	/**
	 *  협업정보 - > 협업정보  - > 협력업체 자금 지원 제도
	 * @param locale
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/consult/NEDMCST0011.do", method = RequestMethod.GET)
	public String loanDetail1(Locale locale,  ModelMap model) {
		
		return "/edi/consult/NEDMCST0011";
	}
	
	/**
	 *  협업정보 - > 협업정보  - > 협력업체 자금 지원 F&Q  
	 * @param locale
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/consult/NEDMCST0012.do", method = RequestMethod.GET)
	public String loanDetail2(Locale locale,  ModelMap model) {
		
		return "/edi/consult/NEDMCST0012";
	}
	
    
}
