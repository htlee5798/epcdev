/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 24. 오전 11:01:10
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */
package com.lottemart.epc.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lcn.module.common.paging.PaginationInfo;
import lcn.module.common.paging.PaginationManager;
import lcn.module.common.paging.PaginationRenderer;

import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @Class Name : LoginUtil
 * @Description : 로그인 관련 유틸리티 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 24. 오전 11:01:10 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public class PagingUtil {
	
	
	/**
	 * 
	 * @param paginationInfo
	 * @param type    (처음, 이전, 다음, 마지막을 이미지로 할지 텍스트로 할지 표시 "image", "text")
	 * @param function(페이지 넘버 Click Event명으로 JSP에 function으로 생성해서 사용)
	 * @return
	 */
	public static String makingPagingContents(HttpServletRequest request, PaginationInfo paginationInfo, String type, String function) throws Exception {
		
		HttpSession	session = request.getSession();		
		String contents = "";
		PaginationRenderer paginationRenderer = null;
		
		PaginationManager paginationManager = (PaginationManager) WebApplicationContextUtils.getWebApplicationContext(session.getServletContext()).getBean("paginationManager");
			
		if (paginationManager != null) {
			paginationRenderer = paginationManager.getRendererType(type);
            contents = paginationRenderer.renderPagination(paginationInfo, function);
		}
        				
		return contents;
	}
}
