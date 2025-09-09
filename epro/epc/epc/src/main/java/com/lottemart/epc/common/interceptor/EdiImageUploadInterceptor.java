package com.lottemart.epc.common.interceptor;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.file.FileUploadProperty;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

/**
 * @Class Name :
 * @Description : HandlerInterceptor로서, 권한처리, 세션처리 등을 담당할 것임.
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public class EdiImageUploadInterceptor implements HandlerInterceptor {

	protected final Log logger = LogFactory.getLog(getClass());

    //로그인 페이지
    private String loginPage;

    private List<String> ignoreUrlList ;

    private FileUploadProperty imageUploadProperty;

//    private MessageSourceAccessor messageSourceAccessor;

//	@Autowired
//	public void setMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor) {
//		this.messageSourceAccessor = messageSourceAccessor;
//	}

	@Resource(name="imageUploadProperty")
	public void setImageUploadProperty(FileUploadProperty imageUploadProperty) {
		this.imageUploadProperty = imageUploadProperty;
		this.imageUploadProperty.setMaximumSize(500000L);// 크기가 500k 미만인 이미지만 업로드 가능
		this.imageUploadProperty.getUploadAllowedExtensions().remove("gif");
		this.imageUploadProperty.getUploadAllowedExtensions().remove("bmp");
		this.imageUploadProperty.getUploadAllowedExtensions().remove("png");

		this.imageUploadProperty.getUploadAllowedTypes().remove("image/png");
		this.imageUploadProperty.getUploadAllowedTypes().remove("image/tiff");
		this.imageUploadProperty.getUploadAllowedTypes().remove("image/bmp");
		this.imageUploadProperty.getUploadAllowedTypes().remove("image/gif");
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		String returnPage = request.getParameter("returnPage");
		if (StringUtils.isEmpty(returnPage))
			returnPage = request.getRequestURL().toString();

		ModelAndView modelAndView = new ModelAndView("redirect:" + returnPage);

		boolean resultFlag = true;

		MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;
		Iterator fileIter = mptRequest.getFileNames();
		while (fileIter.hasNext()) {
			MultipartFile mFile = mptRequest.getFile((String) fileIter.next());
			if (!mFile.isEmpty()) {
				String error = null;

				// file type check
				boolean isAllowedType = this.imageUploadProperty
						.getUploadAllowedTypes().contains(
								mFile.getContentType().toLowerCase());
				if (!isAllowedType) {
					error = "type";
					resultFlag = false;
					modelAndView.addObject("message", error);
					break;
				}

				boolean isAllowedExtension = this.imageUploadProperty
						.getUploadAllowedExtensions().contains(
								FilenameUtils.getExtension(
										mFile.getOriginalFilename())
										.toLowerCase());
				if (!isAllowedExtension) {
					error = "extension";
					resultFlag = false;
					modelAndView.addObject("message", error);
					break;
				}

				if (this.imageUploadProperty.getMaximumSize() < mFile.getSize()) {
					error = "size";
					resultFlag = false;
					modelAndView.addObject("message", error);
					break;
				}
			}
		}

		if (!resultFlag) {
			throw new ModelAndViewDefiningException(modelAndView);

		} else {
			return true;
		}
	}


	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

    public static boolean isAjaxJsonRequest(HttpServletRequest request){
    	String requestUri = request.getRequestURI();
    	return requestUri.endsWith(".json");
    }

	public String getLoginPage() {
		return loginPage;
	}

	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}

	public List<String> getIgnoreUrlList() {
		return ignoreUrlList;
	}

	public void setIgnoreUrlList(List<String> ignoreUrlList) {
		this.ignoreUrlList = ignoreUrlList;
	}
}
