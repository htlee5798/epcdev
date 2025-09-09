package com.lottemart.epc.edi.srm.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 입점상담 FAQ Controller
 * 
 * @author SHIN SE JIN
 * @since 2016.07.20
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.07.20  	SHIN SE JIN		 최초 생성
 *
 * </pre>
 */
@Controller
public class SRMFAQ0010Controller {
	
	@Autowired
	//private SRMNFA0010Service srmnfa0010Service;
	
	/**
	 * 입점상담 FAQ init
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/SRMFAQ0010.do")
	public String init(HttpServletRequest request) throws Exception {
		return "/edi/srm/SRMFAQ0010";
	}
	
}
