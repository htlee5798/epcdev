package com.lottemart.epc.edi.payment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lcn.module.common.util.DateUtil;
import lcn.module.common.views.AjaxJsonModelHelper;

import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.payment.service.PEDMPAY0030Service;


@Controller
public class PEDMPAY0030Controller extends BaseController {

	@Autowired
	private PEDMPAY0030Service pedmpay0030Service;

	@RequestMapping(value = "/edi/payment/PEDMPAY00301.do", method = RequestMethod.GET)
	public String credLed(Locale locale,  ModelMap model) {
		Map<String, String> map = new HashMap();

		String payYm =  DateUtil.getToday("yyyy");
		String payMm =  DateUtil.getToday("MM");

		map.put("startDate_year", payYm);
		map.put("startDate_month", payMm);


		model.addAttribute("paramMap",map);

		return "/edi/payment/PEDMPAY0031";
	}



	@RequestMapping(value = "/edi/payment/PEDMPAY00301Select.do", method = RequestMethod.POST)
	public String selectCominfor(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {

		model.addAttribute("paramMap",map);
		model.addAttribute("paymentList", pedmpay0030Service.selectPayCountData(map));

		return "/edi/payment/PEDMPAY0031";
	}


	@RequestMapping(value = "/edi/payment/PEDMPAY00301Send.do", method = RequestMethod.POST)
	public String sendPaymentShell(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {



		if(pedmpay0030Service.sendExecuteCommand(map)) {
			model.addAttribute("resultMessage", getText("msg.common.success.batch"));  //정상적으로전송처리가 수행되었습니다.
		}
		else{
			// 배치 수동 호출시  배치db 연결 관련 오류
			model.addAttribute("resultMessage", getText("msg.common.success.batch.error"));  //대금결제전송 배치요청중 오류가 발생하였습니다.
		}

		model.addAttribute("paramMap",map);
		model.addAttribute("paymentList", pedmpay0030Service.selectPayCountData(map));

		return "/edi/payment/PEDMPAY0031";
	}

	@RequestMapping("/edi/payment/PEDMPAY00301State.do")
    public ModelAndView selectPamentStayCount(
    		@RequestParam Map<String,Object> map) throws Exception {
		String state = "T";

		Integer  cnt = pedmpay0030Service.selectPamentStayCount(map);

		if(cnt > 0) state ="F";

		 return AjaxJsonModelHelper.create(state);
	}

	//pedmpay0030Servic.eselectPamentStayCount( map);


}
