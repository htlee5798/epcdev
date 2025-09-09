package com.lottemart.epc.edi.srm.controller;

import com.lottemart.epc.edi.srm.model.SRMMNT0010VO;
import com.lottemart.epc.edi.srm.service.SRMMNT0010Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * 대표자 SRM 모니터링
 * 
 * @author LEE HYOUNG TAK
 * @since 2016.08.25
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.08.25  	LEE HYOUNG TAK		 최초 생성
 *
 * </pre>
 */
@Controller
public class SRMMNT0010Controller {

	@Autowired
	private SRMMNT0010Service srmmnt0010Service;

	/**
	 * 대표자 SRM 모니터링 로그인 화면
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = {"/edi/mnt/SRMMNT0010.do"})
	public String init() throws Exception {
		return "/edi/srm/SRMMNT0010";
	}


	/**
	 * 현재까지 평가점수
	 * @param SRMMNT0020VO
	 * @return HashMap<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/mnt/selectCEOSRMmoniteringLogin.json", method = RequestMethod.POST)
	public @ResponseBody HashMap<String, Object> selectCEOSRMmoniteringLogin(@RequestBody SRMMNT0010VO vo, HttpServletRequest request) throws Exception {
		return srmmnt0010Service.selectCEOSRMmoniteringLogin(vo, request);
	}
}
