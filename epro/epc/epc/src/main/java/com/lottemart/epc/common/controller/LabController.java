package com.lottemart.epc.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LabController {

	@RequestMapping("common/wasCheck.do")
	public String wasCheck(HttpServletRequest request)throws Exception{

		return "lab/11";
	}
}
