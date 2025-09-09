package com.lottemart.epc.edi.consult.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.ConfigurationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.util.SecureUtil;
import com.lottemart.epc.edi.consult.model.Estimation;
import com.lottemart.epc.edi.consult.service.NEDMCST0220Service;
import com.lottemart.epc.edi.consult.service.PEDMSCT0003Service;


/**
 * 협업정보 - > 견적서 관리  - > 견적문서 조회   Controller
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
public class NEDMCST0220Controller {

	private static final Logger logger = LoggerFactory.getLogger(NEDMCST0220Controller.class);

	@Autowired
	private NEDMCST0220Service nedmcst0220Service;

	@Resource(name = "configurationService")
	private ConfigurationService config;


	/**
	 * 협업정보 - > 견적서 관리  - > 견적문서 조회  첫페이지
	 * @param locale
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/consult/NEDMCST0220.do", method = RequestMethod.GET)
	public String estimationMain(Locale locale,  ModelMap model) {
		Map<String, String> map = new HashMap();

		String nowDate = DateUtil.getToday("yyyy-MM-dd");

		map.put("srchStartDate", nowDate);
		map.put("srchEndDate", nowDate);

		model.addAttribute("paramMap",map);


		return "/edi/consult/NEDMCST0220";
	}

	/**
	 * 협업정보 - > 견적서 관리  - > 견적문서 조회
	 * @param locale
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/consult/NEDMCST0220Select.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> estimationMainSelect(@RequestBody Estimation map, HttpServletRequest request) throws Exception {

		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.setVenCds(epcLoginVO.getVendorId());

		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Estimation> estList = nedmcst0220Service.estimationMainSelect(map);

		resultMap.put("estList", estList);

		return resultMap;
	}

	/**
	 * 협업정보 - > 견적서 관리  - > 견적서 삭제
	 * @param locale
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/consult/NEDMCST0220Delete.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> estimationMainDelete(@RequestBody Estimation map, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String[] pids = map.getPids();

		for(int i=0;i<pids.length;i++){
			nedmcst0220Service.estimationMainDelete(pids[i]);
			nedmcst0220Service.estimationMainDetailDelete(pids[i]);
		}
		resultMap.put("result", "success");
		return resultMap;
	}


	/**
	 * 협업정보 - > 견적서 관리  - > 견적서 세부 조회
	 * @param locale
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/consult/NEDMCST0220SelectDetail.do", method = RequestMethod.POST)
	public String estimationMainDetail(@RequestParam Map<String,Object> map,  ModelMap model) throws Exception {

		String pid = map.get("forwardPID").toString();

		model.addAttribute("estListTop", nedmcst0220Service.estimationMainSelectDetailTop(pid));
		model.addAttribute("estListBottom",nedmcst0220Service.estimationMainSelectDetailBottom(pid));

		model.addAttribute("paramMap", map);

		return "/edi/consult/NEDMCST0221";
	}


	/**
	 * 협업정보 - > 견적서 관리  - > 견적서 수정 페이지
	 * @param Estimation
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/consult/NEDMCST0220UpdateDetailForward.do", method = RequestMethod.POST)
	public String estimationMainDetailUpdateInfo(Estimation map,ModelMap model,HttpServletRequest request, HttpServletResponse response) throws Exception {

		String pid = map.getPid();

		model.addAttribute("estListTop", nedmcst0220Service.estimationMainSelectDetailTop(pid));
		model.addAttribute("estListBottom",nedmcst0220Service.estimationMainSelectDetailBottom(pid));

		return "/edi/consult/NEDMCST0222";
	}

	/**
	 * 협업정보 - > 견적서 관리  - > 견적서 수정
	 * @param Estimation
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/consult/NEDMCST0220Update.do", method = RequestMethod.POST)
	public String estimationInsert(Estimation estimation, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {

		Calendar cal = Calendar.getInstance();
		String nowDate = DateUtil.getToday("yyyyMMdd");
		int nowHour = cal.get(Calendar.HOUR_OF_DAY);
		int nowMin = cal.get(Calendar.MINUTE);
		int nowSecond = cal.get(Calendar.SECOND);
		int nowMiliSecond = cal.get(Calendar.MILLISECOND);
		String e_text = estimation.getDocNm();
		String fileSize = estimation.getFileSize();

		String pid = nowDate+Integer.toString(nowHour)+Integer.toString(nowMin)+Integer.toString(nowMiliSecond);
		String fileUploadPath = ConfigUtils.getString("edi.consult.image.path")+"/"+nowDate+Integer.toString(nowHour)+Integer.toString(nowMin)+Integer.toString(nowSecond)+Integer.toString(nowMiliSecond);
		String delFile = estimation.getDel_file();
		String delFilePath = SecureUtil.splittingFilter(estimation.getDel_file_path());
		String delFileNM = estimation.getDel_file_nm();


		//String fileUploadPath = "c:/file"+"/"+nowDate+String.valueOf(nowHour)+String.valueOf(nowMin)+String.valueOf(nowSecond)+String.valueOf(nowMiliSecond);

		if("exist".equals(delFile)){
			//String filePath = ConfigUtils.getString("edi.consult.image.path")+"/";
			String filePath = "c:/file/";

			File   fileDel      = new File( filePath + delFilePath );
			if( fileDel.exists() ) {
				fileDel.delete();
			}


			if(estimation.getFile().getSize( )>Integer.parseInt(fileSize)){

				model.addAttribute("file_valid", "not_file");
				//model.addAttribute("back_value",vals);
				model.addAttribute("back_pid",pid);
				model.addAttribute("back_e_text",e_text);

				return "/edi/consult/NEDMCST0222";

			}else{
				File f = new File(fileUploadPath);
				try{
					MultipartFile file = estimation.getFile();
					file.transferTo(f);

					estimation.setFileNm(file.getOriginalFilename());
					estimation.setFileSeq(nowDate+Integer.toString(nowHour)+Integer.toString(nowMin)+Integer.toString(nowSecond)+Integer.toString(nowMiliSecond));
				}catch(Exception e){
					logger.error(e.getMessage());
				}
			}
		}else{
			estimation.setFileNm(delFileNM);
			estimation.setFileSeq(delFilePath);
		}
		//Estimation
		nedmcst0220Service.estimationUpdate(estimation);
		nedmcst0220Service.estimationMainDetailDelete(estimation);
		nedmcst0220Service.estimationSheetInsert(estimation);
		Map<String , Object> paramMap = new HashMap<String , Object>();
		String srchStartDate = DateUtil.getToday("yyyy-MM-dd");
		paramMap.put("srchStartDate", srchStartDate);
		paramMap.put("srchEndDate", srchStartDate);
		model.addAttribute("paramMap", paramMap);
		model.addAttribute("code", "SUCCESS");


		return "/edi/consult/NEDMCST0220";
	}

	/**
	 * 협업정보 - > 견적서 관리  - > 파일 다운로드
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/consult/NEDMCST0220FileDownload.do", method = RequestMethod.POST)
	public void fileDownload(@RequestParam Map<String,Object> map,  ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String pid = map.get("pid_file").toString();
		///////////////////해킹 막음 20131230 이동빈
		//model.addAttribute("estListTop", nedmcst0220Service.estimationMainSelectDetailTop(pid));
		//model.addAttribute("estListBottom",nedmcst0220Service.estimationMainSelectDetailBottom(pid));
		//String file_name = map.get("file_name").toString();
		//String file_path = map.get("file_path").toString();

		String file_path = "";

		Estimation hMap =  nedmcst0220Service.estimationMainSelectDetailTop(pid);
		String file_name = (String)hMap.getFileNm();
		file_path  = hMap.getFileSeq();
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

