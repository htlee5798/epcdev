package com.lottemart.epc.edi.comm.controller;

import java.io.File;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import lcn.module.common.namo.NamoMime;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.common.util.MimeUtil;
import com.lottemart.epc.edi.comm.model.Constants;

@Controller
public class BaseController   implements ServletContextAware {

	private MessageSourceAccessor messages;
	private ServletContext servletContext;
	

	@Autowired
	public void setMessages(MessageSourceAccessor messages) {
		this.messages = messages;
	}


	public boolean isThisFashionProduct(String productDivnCode) {
		boolean resultFlag = false;
		if(Constants.FASHION_PRODUCT_CD.equals(productDivnCode)) {
			resultFlag = true;
		}
		return resultFlag;
	}
	
	
	public boolean isThisOnlineOnlyProduct(String onOffDivnCode) {
		boolean resultFlag = false;
		  if(Constants.ONLINE_ONLY_PRODUCT_CD.equals(onOffDivnCode)) {
			  resultFlag = true;
		  }
		return resultFlag;
	}
	
	public String saveFileAndEditorHtmlContent(String newProductCode, HttpServletRequest request) throws Exception {
		MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest)request;
		String saveUrl = ConfigUtils.getString("online.product.detail.image.url");
		String savePath = ConfigUtils.getString("online.product.detail.image.path");
		NamoMime mime = MimeUtil.save(mptRequest.getParameter("productDescription"), saveUrl, savePath);
		
		return mime == null ? "" : mime.getBodyContent();//StringEscapeUtils.escapeHtml(mime.getBodyContent());
	}
	

	public String makeSubFolderForEditor(String newProductCode) {
		
		String subFolerName = newProductCode.substring(0, 4)+"/"+newProductCode.substring(4, 8);
		String uploadDir = ConfigUtils.getString("edi.namoeditor.file.path") + "/"+subFolerName;
		
		File dirPath = new File(uploadDir);
	    if (!dirPath.exists()) {
	        dirPath.mkdirs();
	    }
		return uploadDir;
	}

	
	public String makeSubFolderForOffline(String newProductCode) {
		
		String subFolerName = newProductCode.substring(0, 4)+"/"+newProductCode.substring(4, 8);
		String uploadDir = ConfigUtils.getString("edi.offline.image.path") + "/"+subFolerName; //getServletContext().getRealPath(ConfigUtils.getString("edi.offline.image.path")) + "/"+subFolerName;
		
		File dirPath = new File(uploadDir);
	    if (!dirPath.exists()) {
	        dirPath.mkdirs();
	    }
		return uploadDir;
	}

	public String makeSubFolderForOnline(String newProductCode) {

		String subFolerName = newProductCode.substring(0, 4) + "/" + newProductCode.substring(4, 8);
		String uploadDir = ConfigUtils.getString("edi.online.image.path") + "/" + subFolerName;//getServletContext().getRealPath(ConfigUtils.getString("edi.online.image.path")) + "/"+subFolerName;

		File dirPath = new File(uploadDir);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
		return uploadDir;
	}

	public String makeSubFolderForOnlineVideo(String newProductCode) {
		String subFolerName = newProductCode.substring(0, 4) + "/" + newProductCode.substring(4, 8);
		String uploadDir = ConfigUtils.getString("edi.video.image.path") + "/" + subFolerName;//getServletContext().getRealPath(ConfigUtils.getString("edi.online.image.path")) + "/"+subFolerName;

		File dirPath = new File(uploadDir);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
		return uploadDir;
	}

	// [WideImage 묶음 구간 ] S 와이드 이미지 경로
	
	// Prd   -> /nas_web/lim/static_root/images/edi/online	/wide	/2021/0202
	// Stg   -> /nas_web/webhomes/lim_stg/static_root/images/edi/online	/wide	/2021/0202
	// Dev   -> /nas_web/lim_dev/static_root/images/edi/online	/wide	/2021/0202
	// Local -> /dev_web/lim/static_root/images/edi/online	/wide	/2021/0202
	
	public String makeSubFolderForWide(String imgName)
	{
		String subFolderNames = imgName.substring(0, 4) +"/"+ imgName.substring(4, 8);
			
		String uploadDir = ConfigUtils.getString("edi.online.image.path") + "/wide/" + subFolderNames;
		File dirPath = new File(uploadDir);

		if (!dirPath.exists())
		{
	        dirPath.mkdirs();
	    }

		return uploadDir;
	}
	// [WideImage 묶음 구간 ] E 와이드 이미지 경로
	
	public String makeBatchExcelFileFolder() {
		String prefix = DateFormatUtils.format(new Date(), "yyyyMM", Locale.KOREA);
		String uploadDir =ConfigUtils.getString("edi.batch.excel.path") + "/"+prefix;
		
		File dirPath = new File(uploadDir);
	    if (!dirPath.exists()) {
	        dirPath.mkdirs();
	    }
		return uploadDir; 
	}
	
	public String subFolderName(String newProductCode) {
		
		return newProductCode.substring(0, 4)+"/"+newProductCode.substring(4, 8);
	}
	
	public String makeProductImageId(String newProductCode) {
		return StringUtils.substring(newProductCode, 2, 8)
			+ StringUtils.right(newProductCode, 5);
	}
	

	public String getText(String msgKey) {
		return messages.getMessage(msgKey, Locale.KOREAN);
	}
	

	public String getTextArgs(String msgKey, String args) {
		return getText(msgKey, args, Locale.KOREAN);
	}
	
	
	 /**
     * Convenience method for getting a i18n key's value.  Calling
     * getMessageSourceAccessor() is used because the RequestContext variable
     * is not set in unit tests b/c there's no DispatchServlet Request.
     *
     * @param msgKey
     * @param locale the current locale
     * @return
     */
    public String getText(String msgKey, Locale locale) {
        return messages.getMessage(msgKey, locale);
    }

    /**
     * Convenient method for getting a i18n key's value with a single
     * string argument.
     *
     * @param msgKey
     * @param arg
     * @param locale the current locale
     * @return
     */
    public String getText(String msgKey, String arg, Locale locale) {
        return getText(msgKey, new Object[] { arg }, locale);
    }

    /**
     * Convenience method for getting a i18n key's value with arguments.
     *
     * @param msgKey
     * @param args
     * @param locale the current locale
     * @return
     */
    public String getText(String msgKey, Object[] args, Locale locale) {
        return messages.getMessage(msgKey, args, locale);
    }


	public ServletContext getServletContext() {
		return servletContext;
	}


	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
    
}
