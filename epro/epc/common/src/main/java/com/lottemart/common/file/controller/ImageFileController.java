package com.lottemart.common.file.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lottemart.common.file.model.FileVO;
import com.lottemart.common.file.service.FileMngService;

import lcn.module.common.util.StringUtil;
import lcn.module.framework.property.PropertyService;

/**
 * @Class Name : ImageFileController.java
 * @Description :
 * @Modification Information
 * 
 *               수정일 수정자 수정내용 ------- ------- ------------------- 2011. 9. 2.
 *               고성훈
 * 
 * @author 고성훈
 * @since 2011. 9. 2.
 * @version
 * @see
 * 
 */
@Controller
public class ImageFileController extends HttpServlet {

	private static final long serialVersionUID = 6295214080991383289L;

	@Resource(name = "FileMngService")
	private FileMngService fileService;
	
	@Resource(name = "propertiesService")
    protected PropertyService propertyService;

	final static Logger logger = LoggerFactory.getLogger(ImageFileController.class);

	/**
	 * 첨부된 이미지에 대한 미리보기 기능을 제공한다.
	 * 
	 * @param atchFileId
	 * @param fileSn
	 * @param sessionVO
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/files/getImage.do")
	public void getImageInf(ModelMap model, Map<String, Object> commandMap,	@RequestParam("atchFileId") String atchFileId,
			@RequestParam("fileSn") String fileSn, HttpServletRequest request, HttpServletResponse response) throws Exception {

//		String atchFileId = (String) commandMap.get("atchFileId");
//		String fileSn = (String) commandMap.get("fileSn");

		FileVO vo = new FileVO();

		vo.setAtchFileId(atchFileId);
		vo.setFileSn(fileSn);

		FileVO fvo = fileService.selectFileInf(vo);
		
		StringBuffer stordFilePath = new StringBuffer();
		
		stordFilePath.append(request.getSession().getServletContext().getRealPath("/"));
		stordFilePath.append(propertyService.getString("Globals.fileStorePath"));

		stordFilePath.append(fvo.getFileStreCours());

		File file = new File(stordFilePath.toString(), fvo.getStreFileNm());
		FileInputStream fis = new FileInputStream(file);

		BufferedInputStream in = new BufferedInputStream(fis);
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();

		int imgByte;
		while (true) {//(imgByte = in.read()) != -1
			imgByte = in.read();
			if(imgByte != -1) {
			bStream.write(imgByte);
			}else {
				break;
			}
		}
		in.close();

		String type = "";

		if (fvo.getFileExtsn() != null && !"".equals(fvo.getFileExtsn())) {
			if ("jpg".equals(StringUtil.lowerCase(fvo.getFileExtsn()))) {
				type = "image/jpeg"; 
			} else {
				type = "image/" + StringUtil.lowerCase(fvo.getFileExtsn());
			}
			type = "image/" + StringUtil.lowerCase(fvo.getFileExtsn());

		} else {
			logger.debug("Image fileType is null.");
		}

		response.setHeader("Content-Type", type);
		response.setContentLength(bStream.size());

		bStream.writeTo(response.getOutputStream());

		fis.close();
		
		response.getOutputStream().flush();
		response.getOutputStream().close();

	}
}
