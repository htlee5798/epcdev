package com.lottemart.epc.talk.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.util.SecureUtil;
import com.lottemart.epc.talk.service.TalkService;

@Controller
public class TalkController {

	private static final Logger logger = LoggerFactory.getLogger(TalkController.class);

	@Autowired
	private TalkService talkService;

	@RequestMapping(value = "/talk/info.do")
	public String selectOrderFrame(HttpServletRequest request, HttpServletResponse response) throws Exception {

//		String sessionKey = config.getString("lottemart.epc.session.key");
//		EpcLoginVO epcLoginVO = null;
//		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		String memberNo = SecureUtil.stripXSS(request.getParameter("memberNo"));
		request.setAttribute("memberNo", memberNo);

		chkUserIpAddress(request);

		return "talk/tabFrame";
	}

	@RequestMapping(value = "/talk/memberTab.do")
	public String selectMemberList(HttpServletRequest request, HttpServletResponse response) throws Exception {

//		String sessionKey = config.getString("lottemart.epc.session.key");
//		EpcLoginVO epcLoginVO = null;
//		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		String memberNo  = request.getParameter("memberNo");
		request.setAttribute("memberNo", memberNo);
		request.setAttribute("mode", "search");

		return "talk/memberInfo";
	}

	@RequestMapping(value = "/talk/memberTab/search.do", method = RequestMethod.POST)
	public String selectMemberSearch(@RequestParam Map<String,Object> map, ModelMap model ,HttpServletRequest request) throws Exception {

		List<DataMap> list = talkService.selectMemberInfo(map);
		model.addAttribute("memerList", list);
		request.setAttribute("mode", "ready");
		return "talk/memberInfo";
	}

	@RequestMapping(value = "/talk/orderTab.do")
	public String selectOrderList(HttpServletRequest request, HttpServletResponse response) throws Exception {

//		String sessionKey = config.getString("lottemart.epc.session.key");
//		EpcLoginVO epcLoginVO = null;
//		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		String memberNo  = request.getParameter("memberNo");
		request.setAttribute("memberNo", memberNo);
		request.setAttribute("mode", "search");

		return "talk/orderInfo";
	}

	@RequestMapping(value = "/talk/orderTab/search.do", method = RequestMethod.POST)
	public String selectOrderSearch(@RequestParam Map<String,Object> map, ModelMap model ,HttpServletRequest request) throws Exception {

		List<DataMap> list = talkService.selectOrderInfo(map);
		model.addAttribute("orderList", list);
		request.setAttribute("mode", "ready");
		return "talk/orderInfo";
	}

	public static boolean chkUserIpAddress(HttpServletRequest request){
		//	------------------------------------------------------
			// set IP adress
			//------------------------------------------------------
			//서버용
			String ipAddress = request.getHeader("X-FORWARDED-FOR");
	        if (ipAddress == null){
	        	ipAddress = request.getRemoteAddr();
	        }
	        logger.error("@IP:"+ipAddress);
	        return true;
	}
}
