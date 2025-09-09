/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 11. 1. 오후 2:44:35
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */
package com.lottemart.epc.common.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @Class Name : WebUtil
 * @Description : 웹 유틸리티 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 11. 1. 오후 2:44:35 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public class WebUtil {

	/**
	 * Desc : request의 실제 사용자 IP를 얻어오는 메소드 (L4가 있을 경우 대비)
	 * @Method Name : getRemoteAddr
	 * @param request
	 * @return
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public static String getRemoteAddr(HttpServletRequest request) {
		//X-Forwarded-For 설정위해 기존에 getRemoteAddr 함수 대신 getHeader 함수로 바꾸도록 설정하며,
		//L4 를 통해 접속되지 않는 거래시 IP 를 기존 형식대로 찍어야 함.
		String requestIPAddr = "";
		String IPCheck = "";
		if(request.getHeader("X-Forwarded-For") != null) {
			IPCheck = request.getHeader("X-Forwarded-For");
			while(IPCheck.indexOf(",")!=-1) { 
				// X-Forwarded-For 로 IP 추출시 간혹 IP가 여러개가 나오는 경우 발생함. 
				// 각 IP는 , 로 구분되어 있기에 제일 마지막 IP 만 추출하기 위해 while로 체크 
				IPCheck = IPCheck.substring(IPCheck.indexOf(",")+1).trim();
			}
				requestIPAddr = IPCheck;
		}
		else {
			requestIPAddr = request.getRemoteAddr();
		}
		return requestIPAddr;
	}
}
