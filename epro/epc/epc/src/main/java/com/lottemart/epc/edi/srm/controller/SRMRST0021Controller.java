package com.lottemart.epc.edi.srm.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 입점상담 > 입점상담결과확인  > 진행현황 상세 Controller
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
public class SRMRST0021Controller {

	/**
	 * 진행현황 상세화 초기화
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/SRMRST0021.do")
	public String SRMRST0021(HttpServletRequest request) throws Exception {
		return "/edi/srm/SRMRST0021";
	}
	
}
