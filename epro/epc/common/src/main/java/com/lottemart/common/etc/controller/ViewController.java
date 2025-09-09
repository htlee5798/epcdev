package com.lottemart.common.etc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lottemart.common.file.controller.FileMngController;

/**
 * JSP 조회 컨트롤러
 * @Class Name : 
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 7. 6. 오후 2:25:34 yhchoi
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Controller
public class ViewController {
    @SuppressWarnings("unused")
    final static Logger logger = LoggerFactory.getLogger(ViewController.class);

    /**
     * JSP 조회
     * Desc : 
     * @Method Name : showView
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @param 
     * @return 
     * @exception Exception
     */
    @RequestMapping("common/view/showView.do")
    public String showView(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return request.getParameter("viewName");
	}
}
