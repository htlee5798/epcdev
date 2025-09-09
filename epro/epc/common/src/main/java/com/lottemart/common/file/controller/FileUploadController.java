/**
 * @Class Name  : FileUploadController.java
 * @Description : 파일 업로드 컨트롤러
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

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.file.FileUploadProperty;
import lcn.module.common.file.FileUploadUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.lottemart.common.file.model.FileVO;
import com.lottemart.common.file.service.FileMngService;
import com.lottemart.common.file.util.FileMngUtil;

/**
 * 
 * @Class Name : FileUploadController.java
 * @Description : 파 관리 Controller 부분
 * @Modification Information
 * 
 *               <pre>
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
public class FileUploadController {

	protected final Log logger = LogFactory.getLog(getClass());

	// 첨부파일 관련
	@Resource(name = "FileMngService")
	private FileMngService fileMngService;

	@Resource(name = "FileMngUtil")
	private FileMngUtil fileMngUtil;

	@Resource(name = "fileUploadProperty")
	private FileUploadProperty fileUploadProperty;

	@Resource(name = "messageSourceAccessor")
	private MessageSourceAccessor messageSourceAccessor;

	// 종료

	@SuppressWarnings("unchecked")
	@RequestMapping("/FileUp.do")
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;
		Iterator fileIter = mptRequest.getFileNames();

		MultipartFile multipartFile = mptRequest.getFile((String) mptRequest
				.getFileNames().next());
		String error = FileUploadUtils.validate(multipartFile,
				fileUploadProperty.getMaximumSize(),
				fileUploadProperty.getUploadAllowedTypes(),
				fileUploadProperty.getUploadAllowedExtensions(),
				messageSourceAccessor);

		String jspStr = "/common/tools/FileUpload";
		if (error != null) {
			return new ModelAndView(jspStr, "error", error);
		} else {

			// 첨부파일 관련 첨부파일ID 생성
			List<FileVO> _result = null;
			String _atchFileId = "";

			final Map<String, MultipartFile> files = mptRequest.getFileMap();
			if (!files.isEmpty()) {
				_result = fileMngUtil.parseFileInf(files, "MTG_", 0, "", "");
				 
				// 파일이생성되고나면 생성된첨부파일 ID를 리턴한다.
				_atchFileId = fileMngService.insertFileInfs(_result);
			}

			return new ModelAndView(jspStr, "fileList", _result);
		}
	}

	@RequestMapping("/FileUpload.do")
	public String view() throws Exception {

		return "/common/tools/FileUpload";
	}
}
