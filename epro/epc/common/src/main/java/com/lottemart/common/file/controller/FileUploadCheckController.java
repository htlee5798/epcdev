/**
 * <pre>
 * @prjectName  : 롯데마트 온라인 쇼핑몰 lottemart-common
 * @since    : 2011
 * @Description : 
 * @author : jmryu
 * @Copyright (C) 2011 ~ 2012 lottemart All right reserved.
 * </pre>
 */
package com.lottemart.common.file.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lcn.module.common.file.FileUploadProperty;
import lcn.module.common.file.FileUploadService;
import lcn.module.common.file.FileUploadUtils;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * @author jmryu
 * @Description : 파일업로드시 확장자 , 파일사이즈 체킹 
 * @Class : com.lottemart.common.file.controller
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 
 * @version : 
 * </pre>
 */
public class FileUploadCheckController  extends AbstractController
{
	  private MessageSourceAccessor messageSourceAccessor;
	  private FileUploadService fileUploadService;
	  private FileUploadProperty fileUploadProperty;
	  private String uploadResultPage;

	  protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	  {
	    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
	    MultipartFile multipartFile = multipartRequest.getFile((String)multipartRequest.getFileNames().next());
	    String error = FileUploadUtils.validate(multipartFile, this.fileUploadProperty.getMaximumSize(), this.fileUploadProperty.getUploadAllowedTypes(), this.fileUploadProperty.getUploadAllowedExtensions(), this.messageSourceAccessor);

	    ModelAndView modelAndView = new ModelAndView(this.uploadResultPage);
	    if (error != null) {
	      modelAndView.addObject("error", error);
	    }
	    else {
	      Object uploadInfo = this.fileUploadService.processUpload(multipartFile, this.fileUploadProperty.getUploadPath());
	      modelAndView.addObject("uploadInfo", uploadInfo);
	    }
	    return modelAndView;
	  }

	  public void setUploadResultPage(String uploadResultPage)
	  {
	    this.uploadResultPage = uploadResultPage;
	  }

	  public void setFileUploadService(FileUploadService fileUploadService)
	  {
	    this.fileUploadService = fileUploadService;
	  }

	  public void setFileUploadProperty(FileUploadProperty fileUploadProperty)
	  {
	    this.fileUploadProperty = fileUploadProperty;
	  }

	  public void setMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor)
	  {
	    this.messageSourceAccessor = messageSourceAccessor;
	  }

}
