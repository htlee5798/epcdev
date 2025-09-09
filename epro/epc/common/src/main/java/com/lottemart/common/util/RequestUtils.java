package com.lottemart.common.util;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.StringUtils;

import xlib.cmc.GridData;

public class RequestUtils {
	private static final Logger logger = LoggerFactory.getLogger(RequestUtils.class);

	@SuppressWarnings("unchecked")
	public static Map<String, String> getStringParamMap(HttpServletRequest request) {
		Map<String, String> paramMap = new HashMap<String, String>();
		Enumeration names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = (String)names.nextElement();
			paramMap.put(name, request.getParameter(name));
		}
		return paramMap;
	}
	
	@SuppressWarnings("rawtypes")
	public static DataMap getStringParamDataMap(HttpServletRequest request) {
		DataMap paramMap = new DataMap();
		Enumeration names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = (String)names.nextElement();
			paramMap.put(name, request.getParameter(name));
		}
		return paramMap;
	}
	
	@SuppressWarnings("rawtypes")
	public static DataMap getStringAttributeMap(HttpServletRequest request) {
		DataMap paramMap = new DataMap();
		Enumeration names = request.getAttributeNames();
		while (names.hasMoreElements()) {
			String name = (String)names.nextElement();
//			Object obj = request.getAttribute(name);
			
			if(name.indexOf(".") < 0
//				&& (obj instanceof String
//					|| obj instanceof Integer
//					|| obj instanceof Double
//					|| obj instanceof Long
//					|| obj instanceof Boolean
//				)
			) {
				paramMap.put(name, request.getAttribute(name));
			}
		}
		return paramMap;
	}

	/**
	 * GridData 파라미터를 DTO 객체에 담는다. 
	 * Desc : 
	 * @Method Name : getWiseGridParamToDTO
	 * @param gdReq
	 * @param dto
	 * @return
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public static Object getWiseGridParamToDTO(GridData  gdReq, Object dto) {
		BeanWrapper dtoWrapper = new BeanWrapperImpl(dto);
		String[] paramNames = gdReq.getParamNames();
		
		for (String paramName : paramNames) {
			try {
				dtoWrapper.setPropertyValue(paramName, gdReq.getParam(paramName));
			} catch (Exception e) {
				logger.error("error --> " + e.getMessage());
			}
		}
		return dto; 
	}
	/**
	 * GridData 파라미터를 DataMap 에 담아 리턴한다. 
	 * @see getWiseGridParamToDataMap
	 * @Method Name  : RequestUtils.java
	 * @since      : 2015. 4. 10.
	 * @author     : jyLim
	 * @version    :
	 * @Locaton    : com.lottemart.common.util
	 * @Description : 
	 * @param 
	 * @return  Object
	 * @throws
	 */
	public static DataMap getWiseGridParamToDataMap(GridData  gdReq) {
		DataMap paramMap = new DataMap();
		String[] paramNames = gdReq.getParamNames();
		
		for (String paramName : paramNames) {
			try {
				paramMap.put(paramName, gdReq.getParam(paramName));
			} catch (Exception e) {
				logger.error("error --> " + e.getMessage());
			}
		}
		return paramMap; 
	}
	

	/**
	 *  요청 호스트명을 얻습니다.
	 * @4UP 추가
	 * @param req
	 * @return
	 */
	public static String getRequestHost(HttpServletRequest req) {
		String	rv;
		
		rv	= req.getHeader("X-Forwarded-Host");
		if (StringUtils.isEmpty(rv)) {
			try {
				rv	= new URL(req.getRequestURL().toString()).toURI().getHost();
			} catch (Exception e) {
				rv	= req.getServerName();
			}
		}
		return rv;
	}
	
	public static boolean isIE(HttpServletRequest request) {
		
		boolean isIE = false;
		//String browser = "";
		String userAgent = request.getHeader("User-Agent");
		if (userAgent.indexOf("MSIE") > -1) { // Edge
			//browser = "ie 11 under";
			isIE = true;
		} else if (userAgent.indexOf("Trident") > -1) { // IE
			//browser = "ie 11";
			isIE = true;
		} /* else if (userAgent.indexOf("Edge") > -1 || userAgent.indexOf("Edg") > -1) { // Edge
			//browser = "edge";
			} else if (userAgent.indexOf("Whale") > -1) { // Naver Whale
			//browser = "whale";
			} else if (userAgent.indexOf("Opera") > -1 || userAgent.indexOf("OPR") > -1) { // Opera
			//browser = "opera";
			} else if (userAgent.indexOf("Firefox") > -1) { // Firefox
			//browser = "firefox";
			} else if (userAgent.indexOf("Safari") > -1 && userAgent.indexOf("Chrome") == -1) { // Safari
			//browser = "safari";
			} else if (userAgent.indexOf("Chrome") > -1) { // Chrome
			//browser = "chrome";
			}*/
		return isIE;
	}
}
