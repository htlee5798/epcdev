/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.product.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.util.DateUtil;
import lcn.module.common.util.StringUtil;
import lcn.module.framework.property.ConfigurationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.util.SecureUtil;
import com.lottemart.epc.product.service.PSCMPRD0012Service;

/**
 * @Class Name : PSCMPRD0012Controller
 * @Description : 상품이미지촬영스케쥴켈린더 조회 Controller 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 5:06:03 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Controller
public class PSCMPRD0012Controller {

	@Autowired
	private ConfigurationService config;

	@Autowired
	private PSCMPRD0012Service pscmprd0012Service;
	
	/**
	 * Desc : 상품이미지촬영스케쥴켈린더 조회 메소드
	 * @Method Name : selectCalendarScheduleList
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@RequestMapping(value = "/product/selectCalendarScheduleList.do")
	public String selectCalendarScheduleList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		String currentMonth = SecureUtil.stripXSS(request.getParameter("currentMonth"));
		
		if(StringUtil.isNVL(currentMonth)) {
			currentMonth = DateUtil.getToday("yyyyMM");
		}

		String beforeMonth = DateUtil.addMonth(currentMonth+"01", -1).substring(0, 6);
		String afterMonth = DateUtil.addMonth(currentMonth+"01", 1).substring(0, 6);
		
		String beforeYear = DateUtil.addYear(currentMonth+"01", -1).substring(0, 6);
		String afterYear = DateUtil.addYear(currentMonth+"01", 1).substring(0, 6);
		
		DataMap paramMap = new DataMap();
		paramMap.put("currentMonth", currentMonth);

		// 데이터 조회
		List<DataMap> pscmprd0012List = pscmprd0012Service.selectCalendarScheduleList(paramMap);
		
		request.setAttribute("list", pscmprd0012List);
		
		request.setAttribute("currentMonth", currentMonth);
		request.setAttribute("beforeMonth", beforeMonth);
		request.setAttribute("afterMonth", afterMonth);
		request.setAttribute("beforeYear", beforeYear);
		request.setAttribute("afterYear", afterYear);
		return "product/PSCMPRD0012";
	}
	
}
