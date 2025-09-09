/**
 * @Class Name  : FileDownController.java
 * @Description : 파일다운로드 테스트 컨트롤러
 * @Modification Information
 * 
 *     수정일         수정자                   수정내용
 *     -------          --------        ---------------------------
 *   
 *
 * @author 
 * @since 
 * @version 1.0
 * @see 
 * 
 *  Copyright (C) 2011 by lottemart  All right reserved.
 */
package com.lottemart.common.file.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;

import com.lottemart.common.file.model.FileVO;
import com.lottemart.common.file.service.FileMngService;

import lcn.module.framework.property.PropertyService;

/**
 * 
 * @Class Name : FileDownController.java
 * @Description : 파일 다운로드를 위한 컨트롤러 클래스
 * @Modification Information
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 8. 31.  shko file 예제 controller
 * 
 * @deprecated 해당 내용을 참조 할 뿐 사용하여 개발하는 것은 금지함(사용하기 위해 만든 controller 아님).
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Controller
public class FileDownController extends AbstractView {

	final static Logger logger = LoggerFactory.getLogger(FileDownController.class);

	// 첨부파일 관련
	@Resource(name = "FileMngService")
	private FileMngService fileMngService;

	@Resource(name = "propertiesService")
	protected PropertyService propertyService;

	public static final String[] INVALID_FILE_PATH = { "../", ".." };

	/**
	 * 브라우저 구분 얻기.
	 * 
	 * @param request
	 * @return
	 */
	private String getBrowser(HttpServletRequest request) {
		String header = request.getHeader("User-Agent");
		if (header.indexOf("MSIE") > -1) {
			return "MSIE";
		} else if (header.indexOf("Chrome") > -1) {
			return "Chrome";
		} else if (header.indexOf("Opera") > -1) {
			return "Opera";
		}
		return "Firefox";
	}

	/**
	 * Disposition 지정하기.
	 * 
	 * @param filename
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private void setDisposition(String filename, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String browser = getBrowser(request);

		String dispositionPrefix = "attachment; filename=";
		String encodedFilename = null;

		if (browser.equals("MSIE")) {
			encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
		} else if (browser.equals("Firefox")) {
			encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
		} else if (browser.equals("Opera")) {
			encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
		} else if (browser.equals("Chrome")) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < filename.length(); i++) {
				char c = filename.charAt(i);
				if (c > '~') {
					sb.append(URLEncoder.encode("" + c, "UTF-8"));
				} else {
					sb.append(c);
				}
			}
			encodedFilename = sb.toString();
		} else {
			// throw new RuntimeException("Not supported browser");
			throw new IOException("Not supported browser");
		}

		response.setHeader("Content-Disposition", dispositionPrefix + encodedFilename);

		if ("Opera".equals(browser)) {
			response.setContentType("application/octet-stream;charset=UTF-8");
		}
	}

	/**
	 * 파일 다운로드(파일 경로 및 이름을 통한 다운로드)
	 */
	@RequestMapping(value = "/FileDown.do")
	public ModelAndView download(HttpServletRequest request, Map<String, Object> model, @ModelAttribute("filevo") FileVO filevo) throws Exception {

		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest hsr = sra.getRequest();

		StringBuffer stordFilePath = new StringBuffer();

		logger.debug("[getRealPath]" + hsr.getSession().getServletContext().getRealPath("/"));
		logger.debug("[Globals.fileStorePath]" + propertyService.getString("Globals.fileStorePath"));

		stordFilePath.append(hsr.getSession().getServletContext().getRealPath("/"));
		stordFilePath.append(propertyService.getString("Globals.fileStorePath"));

		String filename = filevo.getStreFileNm();
		String original = filevo.getOrignlFileNm();
		String filestrecours = filevo.getFileStreCours();

		if ("".equals(filename)) {
			request.setAttribute("message", "File not found.");
			return new ModelAndView("/common/tools/FileDown");
		}

		if ("".equals(original)) {
			original = filename;
		}

		stordFilePath.append(filestrecours).append("/").append(filename);

		//--------------------------------------------------
		// 2012.10.29 jaeyulim 파일 다운로드 취약점 보완	
		String file = stordFilePath.toString();
		String fileName = original;
		for (int i = 0; i < INVALID_FILE_PATH.length; i++) {
			if (file.indexOf(INVALID_FILE_PATH[i]) >= 0 || fileName.indexOf(INVALID_FILE_PATH[i]) >= 0) {
				logger.debug("[invalid file path or name]");
				request.setAttribute("message", "invalid file name");
				return new ModelAndView("/common/tools/FileDown");
			}
		}

		// --------------------------------------------------
		model.put("file", new File(file));
		model.put("fileName", original);

		boolean existsFile = true;
		try {
			File f = new File(file);
			existsFile = f.exists();
		} catch(Exception e) {
			existsFile = false;
		}

		if (existsFile) {
			return new ModelAndView("downloadView", model);
		} else {
			model.put("message", "다운로드 할 파일이 존재하지 않습니다.");
			return new ModelAndView("/common/tools/FileMessage", model);
		}
	}

	/**
	 * 첨부파일로 등록된 파일에 대하여 다운로드를 제공한다.(파일 ID로 다운로드)
	 * 
	 * @param commandMap
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/FileDownById.do")
	public ModelAndView cvplFileDownload(HttpServletRequest request, Map<String, Object> model, @ModelAttribute("filevo") FileVO fileVO) throws Exception {

		StringBuffer stordFilePath = new StringBuffer();

		stordFilePath.append(propertyService.getString("Globals.fileStorePath"));

		FileVO fvo = fileMngService.selectFileInf(fileVO);

		String filename = fvo.getStreFileNm();
		String original = fvo.getOrignlFileNm();
		String filestrecours = fvo.getFileStreCours();

		if ("".equals(filename)) {
			request.setAttribute("message", "File not found.");
			return new ModelAndView("/common/tools/FileDown");
		}

		if ("".equals(original)) {
			original = filename;
		}

		stordFilePath.append(filestrecours).append("/").append(filename);

		model.put("file", new File(stordFilePath.toString()));
		model.put("fileName", original);

		return new ModelAndView("downloadView", model);
	}

	/**
	 * 파일 리스트
	 */
	@RequestMapping(value = "/FileDownlist.do")
	public ModelAndView download(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<FileVO> _result = this.fileMngService.selectFileList(new FileVO());
		return new ModelAndView("/common/tools/FileDown", "fileList", _result);
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
	
	}

}
