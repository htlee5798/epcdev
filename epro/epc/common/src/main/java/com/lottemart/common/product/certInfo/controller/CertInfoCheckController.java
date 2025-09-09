package com.lottemart.common.product.certInfo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.common.product.certInfo.service.CertInfoCheckService;

/**
 * 
 * @Class Name : CertInfoCheckController.java
 * @Description : KC인증 정보 검증 (BO API CALL) 
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2025.08.01		yun				최초생성
 *               </pre>
 */
@Controller
public class CertInfoCheckController {

	private static final Logger logger = LoggerFactory.getLogger(CertInfoCheckController.class);

	@Autowired
	private CertInfoCheckService certInfoCheckService;

	@RequestMapping("product/certInfoCheck.do")
	public @ResponseBody Map<String, Object> certInfoCheck(HttpServletRequest request) throws Exception {
		Map<String, Object> rtnMap = new HashMap<String, Object>();

		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("certInfoCode", request.getParameter("certInfoCode"));
			condition.put("certInfoType", request.getParameter("certInfoType"));
			rtnMap = certInfoCheckService.certInfoCheckTransfer(condition);
			//logger.info(rtnMap.toString());
		} catch (Exception e) {
			// 작업오류
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}

}
