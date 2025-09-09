package com.lottemart.epc.edi.consult.controller;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

import lcn.module.common.util.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.edi.consult.service.PEDMSCT0003Service;


@Controller
public class PEDMSCT0003Controller {

	private static final Logger logger = LoggerFactory.getLogger(PEDMSCT0003Controller.class);

	@Autowired
	private PEDMSCT0003Service pedmsct0003Service;

    //견적서 등록 첫페이지
	@RequestMapping(value = "/edi/consult/PEDMCST0003.do", method = RequestMethod.GET)
	public String estimationMain(Locale locale,  ModelMap model) {

		return "/edi/consult/PEDMCST0003";
	}

	//견적서 등록
	@RequestMapping(value = "/edi/consult/PEDMCST0003Update.do", method = RequestMethod.POST)
	public String estimationInsert(@RequestParam Map<String,Object> map, ModelMap model, @RequestParam("file") MultipartFile file) throws Exception {

		Calendar cal = Calendar.getInstance();
		String nowDate = DateUtil.getToday("yyyyMMdd");
		int nowHour = cal.get(Calendar.HOUR_OF_DAY);
		int nowMin = cal.get(Calendar.MINUTE);
		int nowSecond = cal.get(Calendar.SECOND);
		int nowMiliSecond = cal.get(Calendar.MILLISECOND);
		String vals = map.get("forward_values").toString();
		String e_text = map.get("e_text").toString();
		String fileSize = map.get("file_size").toString();

		String pid = nowDate+Integer.toString(nowHour)+Integer.toString(nowMin)+Integer.toString(nowMiliSecond);
		String fileUploadPath = ConfigUtils.getString("edi.consult.image.path")+"/"+nowDate+Integer.toString(nowHour)+Integer.toString(nowMin)+Integer.toString(nowSecond)+Integer.toString(nowMiliSecond);
		//String fileUploadPath = "c:/file"+"/"+nowDate+String.valueOf(nowHour)+String.valueOf(nowMin)+String.valueOf(nowSecond)+String.valueOf(nowMiliSecond);

		if(file.getSize()>Integer.parseInt(fileSize)){
			model.addAttribute("file_valid", "not_file");
			model.addAttribute("back_value",vals);
			model.addAttribute("back_pid",pid);
			model.addAttribute("back_e_text",e_text);

			return "/edi/consult/PEDMCST0003";

		}else{
			File f = new File(fileUploadPath);
			try{
				file.transferTo(f);

				map.put("file_nm",file.getOriginalFilename());
				map.put("file_seq", nowDate+Integer.toString(nowHour)+Integer.toString(nowMin)+Integer.toString(nowSecond)+Integer.toString(nowMiliSecond));
			}catch(Exception e){
				logger.error(e.getMessage());
			}
		}

		pedmsct0003Service.estimationInsert(map, pid);
		pedmsct0003Service.estimationSheetInsert(pid, vals);

		map.put("startDate", nowDate);
		map.put("endDate", nowDate);
		model.addAttribute("paramMap",map);

		return "/edi/consult/PEDMCST0004";
	}

}

