package com.lottemart.epc.edi.srm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 품질경영평가 / 품질경영평가 절차 안내 Controller
 * 
 * @author SHIN SE JIN
 * @since 2016.07.11
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.07.11  	SHIN SE JIN		 최초 생성
 *
 * </pre>
 */
@Controller
public class SRMEVL0020Controller {
	
	/**
	 * 품질경영평가 절차 안내 화면 초기화
	 * @return
	 */
	@RequestMapping(value = "/edi/evl/SRMEVL0020.do")
	public String SRMEVL0020() throws Exception {
		return "/edi/srm/SRMEVL0020";
	}
	
}
