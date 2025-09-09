package com.lottemart.epc.edi.consult.controller;


import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.util.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.edi.consult.model.Estimation;
import com.lottemart.epc.edi.consult.service.NEDMCST0210Service;


/**
 * 협업정보 - > 견적서 관리  - > 견적서 등록   Controller
 *
 * @author SUN GIL CHOI
 * @since 2015.11.04
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ---------------------------
 *   2015.11.04  	SUN GIL CHOI   최초 생성
 *
 * </pre>
 */
@Controller
public class NEDMCST0210Controller {
	private static final Logger logger = LoggerFactory.getLogger(NEDMCST0210Controller.class);

	@Autowired
	private NEDMCST0210Service nedmcst0210Service;

	/**
	 * 협업정보 - > 견적서 관리  - > 견적서 등록 첫페이지
	 * @param locale
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/consult/NEDMCST0210.do", method = RequestMethod.GET)
	public String estimationMain(Locale locale,  ModelMap model) {

		return "/edi/consult/NEDMCST0210";
	}


	/**
	 * 협업정보 - > 견적서 관리  - > 견적서 등록
	 * @param Estimation
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/consult/NEDMCST0210Update.do", method = RequestMethod.POST)
	public String estimationInsert(Estimation estimation, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		//pc product_id product_name product_pop standard pack rating estimate_price origin remark
		Calendar cal = Calendar.getInstance();
		String nowDate = DateUtil.getToday("yyyyMMdd");
		int nowHour = cal.get(Calendar.HOUR_OF_DAY);
		int nowMin = cal.get(Calendar.MINUTE);
		int nowSecond = cal.get(Calendar.SECOND);
		int nowMiliSecond = cal.get(Calendar.MILLISECOND);
		String e_text = estimation.getDocNm();
		String fileSize = estimation.getFileSize();

		String pid = nowDate + Integer.toString(nowHour) + Integer.toString(nowMin) + Integer.toString(nowMiliSecond);
		String fileUploadPath = ConfigUtils.getString("edi.consult.image.path") + "/" + nowDate + nowHour + nowMin + nowSecond + nowMiliSecond;
		//String fileUploadPath = "c:/file"+"/"+nowDate+String.valueOf(nowHour)+String.valueOf(nowMin)+String.valueOf(nowSecond)+String.valueOf(nowMiliSecond);
		if (estimation.getFile().getSize( ) > Integer.parseInt(fileSize)) {
			model.addAttribute("file_valid", "not_file");
			//model.addAttribute("back_value",vals);
			model.addAttribute("back_pid",pid);
			model.addAttribute("back_e_text",e_text);

			return "/edi/consult/NEDMCST0210";
		} else {
			File f = new File(fileUploadPath);
			try {
				MultipartFile file = estimation.getFile();
				file.transferTo(f);

				logger.debug("----->" + file.getOriginalFilename());
				logger.debug("----->" + nowDate + Integer.toString(nowHour) + Integer.toString(nowMin) + Integer.toString(nowSecond) + Integer.toString(nowMiliSecond));

				estimation.setFileNm(file.getOriginalFilename());
				estimation.setFileSeq(nowDate + Integer.toString(nowHour) + Integer.toString(nowMin) + Integer.toString(nowSecond) + Integer.toString(nowMiliSecond));
			} catch(Exception e) {
				logger.error(e.getMessage(),e);
			}
		}

		nedmcst0210Service.insertEstimation(estimation, pid);
		//nedmcst0210Service.estimationInsert(estimation, pid);
		//nedmcst0210Service.estimationSheetInsert(estimation,pid);

		Map<String , Object> paramMap = new HashMap<String , Object>();
		String srchStartDate = DateUtil.getToday("yyyy-MM-dd");
		paramMap.put("srchStartDate", srchStartDate);
		paramMap.put("srchEndDate", srchStartDate);
		model.addAttribute("paramMap",paramMap);

		return "/edi/consult/NEDMCST0220";
	}

}

