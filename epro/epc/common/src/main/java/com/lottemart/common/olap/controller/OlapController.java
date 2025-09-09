package com.lottemart.common.olap.controller;

import javax.servlet.http.HttpServletRequest;
import lcn.module.framework.property.ConfigurationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Class Name : OlapController.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2016. 7. 27. 오후 1:35:49 jgahn
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Controller("olapController")
public class OlapController {
	private static final Logger logger = LoggerFactory.getLogger(OlapController.class);

	@Autowired
	private ConfigurationService config;
	
	/**
	 * Desc : OLAP화면 호출
	 * @Method Name : viewLoginForm
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@RequestMapping("common/olap/viewOlapForm.do")
	public String viewLoginForm(HttpServletRequest request) throws Exception {
		
		String olap_userId = config.getString("olap.userId");
		String olap_password = config.getString("olap.password");
		String olap_spaceId = config.getString("olap.spaceId");
		
		String basicDomain = config.getString("olap.basic.domain");
		String advDomain = config.getString("olap.adv.domain");
		
		logger.debug("olap_userId->"+olap_userId);
		logger.debug("olap_password->"+olap_password);
		logger.debug("olap_spaceId->"+olap_spaceId);
		
		request.setAttribute("olap_userId", olap_userId);
		request.setAttribute("olap_password", olap_password);
		request.setAttribute("olap_spaceId", olap_spaceId);
		request.setAttribute("basicDomain", basicDomain);
		request.setAttribute("advDomain", advDomain);
		
		return "olap/PBOMOLAP0001";
	}
		
}
