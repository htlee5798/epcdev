package com.lottemart.epc.edi.consult.controller;


import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.ConfigurationService;


import org.joda.time.Minutes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.consult.service.PEDMSCT0004Service;
import com.lottemart.epc.edi.consult.service.PEDMSCT0003Service;

import java.io.BufferedOutputStream;
import java.net.URLEncoder;
import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;



@Controller
public class PEDMSCT0004Controller {

	private static final Logger logger = LoggerFactory.getLogger(PEDMSCT0004Controller.class);

	@Autowired
	private PEDMSCT0004Service pedmsct0004Service;

	@Autowired
	private PEDMSCT0003Service pedmsct0003Service;

	@Resource(name = "configurationService")
	private ConfigurationService config;


    //견적서 조회 첫페이지
	@RequestMapping(value = "/edi/consult/PEDMCST0004.do", method = RequestMethod.GET)
	public String estimationMain(Locale locale,  ModelMap model) {
		Map<String, String> map = new HashMap();

		String nowDate = DateUtil.getToday("yyyy-MM-dd");

		map.put("startDate", nowDate);
		map.put("endDate", nowDate);

		model.addAttribute("paramMap",map);


		return "/edi/consult/PEDMCST0004";
	}

	//견적서 조회
	@RequestMapping(value = "/edi/consult/PEDMCST0004Select.do", method = RequestMethod.POST)
	public String estimationMainSelect(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {

		// session 접근 N개의 협력사코드(VEN_CD) ----------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());

		model.addAttribute("paramMap",map);
		model.addAttribute("estList", pedmsct0004Service.estimationMainSelect(map));

		return "/edi/consult/PEDMCST0004";
	}

	//견적서 삭제
	@RequestMapping(value = "/edi/consult/PEDMCST0004Delete.do", method = RequestMethod.POST)
	public String estimationMainDelete(@RequestParam Map<String,Object> map,  ModelMap model,HttpServletRequest request) throws Exception {

		String[] pid = map.get("deleteCode").toString().split(";");

		for(int i=0;i<pid.length;i++){
			pedmsct0004Service.estimationMainDelete(pid[i]);
			pedmsct0004Service.estimationMainDetailDelete(pid[i]);
		}

		return estimationMainSelect(map, model, request);
	}


	//견적서 세부 조회
	@RequestMapping(value = "/edi/consult/PEDMCST0004SelectDetail.do", method = RequestMethod.POST)
	public String estimationMainDetail(@RequestParam Map<String,Object> map,  ModelMap model) throws Exception {

		String pid = map.get("forwardPID").toString();

		model.addAttribute("estListTop", (HashMap)pedmsct0004Service.estimationMainSelectDetailTop(pid));
		model.addAttribute("estListBottom",pedmsct0004Service.estimationMainSelectDetailBottom(pid));

		return "/edi/consult/PEDMCST000401";
	}


	//견적서 수정 페이지
	@RequestMapping(value = "/edi/consult/PEDMCST0004UpdateDetailForward.do", method = RequestMethod.POST)
	public String estimationMainDetailUpdateInfo(@RequestParam Map<String,Object> map,  ModelMap model) throws Exception {

		String vals = map.get("forward_values").toString();
		String pid = map.get("forward_pid").toString();
		String etext = map.get("forward_e_text").toString();

		model.addAttribute("estListTop", (HashMap)pedmsct0004Service.estimationMainSelectDetailTop(pid));

		model.addAttribute("pid_b",pid);
		model.addAttribute("vals_b",vals);
		model.addAttribute("etext_b",etext);

		return "/edi/consult/PEDMCST000402";
	}

	//견적서 수정
	@RequestMapping(value = "/edi/consult/PEDMCST0004Update.do", method = RequestMethod.POST)
	public String estimationInsert(@RequestParam Map<String,Object> map, ModelMap model, @RequestParam("file") MultipartFile file) throws Exception {

		Calendar cal = Calendar.getInstance();
		String nowDate = DateUtil.getToday("yyyyMMdd");
		int nowHour = cal.get(Calendar.HOUR_OF_DAY);
		int nowMin = cal.get(Calendar.MINUTE);
		int nowSecond = cal.get(Calendar.SECOND);
		int nowMiliSecond = cal.get(Calendar.MILLISECOND);
		String vals = map.get("forward_values").toString();
		String e_text = map.get("e_text").toString();
		String pid = map.get("pid").toString();
		String fileSize = map.get("file_size").toString();

		String fileUploadPath = ConfigUtils.getString("edi.consult.image.path")+"/"+nowDate+Integer.toString(nowHour)+Integer.toString(nowMin)+Integer.toString(nowSecond)+Integer.toString(nowMiliSecond);
		String delFile = map.get("del_file").toString();
		String delFilePath = map.get("del_file_path").toString();
		String delFileNM = map.get("del_file_nm").toString();


		//String fileUploadPath = "c:/file"+"/"+nowDate+String.valueOf(nowHour)+String.valueOf(nowMin)+String.valueOf(nowSecond)+String.valueOf(nowMiliSecond);

		if("exist".equals(delFile)){
			//String filePath = ConfigUtils.getString("edi.consult.image.path")+"/";
			String filePath = "c:/file/";

			File   fileDel      = new File( filePath + delFilePath );
			if( fileDel.exists() ) {
				fileDel.delete();
			}


			if(file.getSize()>Integer.parseInt(fileSize)){

				model.addAttribute("file_valid", "not_file");
				model.addAttribute("back_value",vals);
				model.addAttribute("back_pid",pid);
				model.addAttribute("back_e_text",e_text);

				return "/edi/consult/PEDMCST000402";

			}else{
				File f = new File(fileUploadPath);
				try{
					file.transferTo(f);

					map.put("file_nm",file.getOriginalFilename());
					map.put("file_seq",nowDate+Integer.toString(nowHour)+Integer.toString(nowMin)+Integer.toString(nowSecond)+Integer.toString(nowMiliSecond));
				}catch(Exception e){
					logger.error(e.getMessage());
				}
			}
		}else{
			map.put("file_nm",delFileNM);
			map.put("file_seq",delFilePath);
		}

		pedmsct0004Service.estimationUpdate(map,pid);
		pedmsct0004Service.estimationMainDetailDelete(pid);
		pedmsct0003Service.estimationSheetInsert(pid, vals);

		map.put("startDate", nowDate);
		map.put("endDate", nowDate);
		model.addAttribute("paramMap",map);

		return "/edi/consult/PEDMCST0004";
	}

	//파일 다운로드
	@RequestMapping(value = "/edi/consult/PEDMCST0004FileDownload.do", method = RequestMethod.POST)
	public void fileDownload(@RequestParam Map<String,Object> map,  ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String pid = map.get("pid_file").toString();
		///////////////////해킹 막음 20131230 이동빈
		//model.addAttribute("estListTop", pedmsct0004Service.estimationMainSelectDetailTop(pid));
		//model.addAttribute("estListBottom",pedmsct0004Service.estimationMainSelectDetailBottom(pid));
		//String file_name = map.get("file_name").toString();
		//String file_path = map.get("file_path").toString();

		String file_path = "";

		HashMap hMap =  pedmsct0004Service.estimationMainSelectDetailTop(pid);
		String file_name = (String)hMap.get("FILE_NM");
		file_path  = (String)hMap.get("FILE_SEQ");
		//System.out.println("file_name:::::::::::::::::>"+file_name);
		//System.out.println("file_path:::::::::::::::::>"+file_path);

		//해킹 방지
		String blockchar[] = {"..", "../", "..\\"};
		Boolean checkResult = true;
		// 금지할 문자열 포함 여부 체크
		for(int i=0; i<blockchar.length;i++) {
		if( file_path.indexOf(blockchar[i]) != -1 ){
		checkResult = false;
	//	System.out.println("특수값있어요");
		}
		}

		String fileUploadPath = ConfigUtils.getString("edi.consult.image.path")+"/";
		//String fileUploadPath = "c:/file"+"/";

		file_path = fileUploadPath + file_path;

		try{

			File file =	 new File(file_path);

			response.setContentType("application/octet-stream;" + "; charset=utf-8");

			 if (request.getHeader("User-Agent").indexOf("MSIE 5.5") > -1) { // MS IE 5.5 이하
			       response.setHeader("Content-Disposition", "filename=" + URLEncoder.encode(file_name, "UTF-8") + ";");
			     } else if (request.getHeader("User-Agent").indexOf("MSIE") > -1) { // MS IE (보통은 6.x 이상 가정)
			       response.setHeader("Content-Disposition", "attachment; filename="
			           + java.net.URLEncoder.encode(file_name, "UTF-8") + ";");
			     } else { // 모질라나 오페라
			       response.setHeader("Content-Disposition", "attachment; filename="
			           + new String(file_name.getBytes("UTF-8"), "latin1") + ";");
			     }

			response.setHeader("Content-Length", "" + file.length() );

			if (file.isFile() && file.length() > 0) // 파일 크기가 0보다 커야 한다.
			{

			// BufferedInputStream fin = new BufferedInputStream(new FileInputStream(file));
			 //BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());

			 OutputStream out = response.getOutputStream();
			 FileInputStream fis = null;

			 fis = new FileInputStream(file);
			 FileCopyUtils.copy(fis, out);

			 if(fis != null){
				 try{
					 fis.close();
					 out.flush();
					 out.close();
				 }catch(Exception e){
					 logger.error(e.getMessage());
				 }
			 }

			}
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

}

