package com.lottemart.epc.common.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xlib.cmc.GridData;
import xlib.cmc.OperateGridData;

import com.lottemart.epc.common.model.PSCMCOM0004VO;
import com.lottemart.epc.common.service.PSCMCOM0004Service;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.common.util.SecureUtil;


/**
 * @Class Name : PSCMCOM0004Controller.java
 * @Description :카테고리 조회 팝업 Controller
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 20. 오후 02:02:02 mjChoi
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Controller
public class PSCMCOM0004Controller
{
	private static final Logger logger = LoggerFactory.getLogger(PSCMCOM0004Controller.class);

	@Autowired
	private PSCMCOM0004Service PSCMCOM0004Service;

	/**
	 * 기본카테고리 폼 페이지
	 * @Description : 기본카테고리 초기페이지 로딩
	 * @Method Name : selectCategoryPopUpView
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping("common/selectCategoryPopUpView.do")
	public String selectCategoryPopUpView(HttpServletRequest request) throws Exception 
	{
		String        categoryTypeCd           = SecureUtil.stripXSS(request.getParameter("categoryTypeCd"));
		List<DataMap> selectCategoryListDepth2 = PSCMCOM0004Service.selectCategoryListDepth2(categoryTypeCd);
		
		request.setAttribute("selectCategoryListDepth2", selectCategoryListDepth2);
		request.setAttribute("categoryTypeCd",           categoryTypeCd          );

		return "common/PSCMCOM0004";
	}

	/**
	 * 기본카테고리 목록
	 * @Description : 기본카테고리 목록을 얻어서 그리드에 리턴
	 * @Method Name : selectCategorySearch
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping("common/selectCategorySearch.do")
	public @ResponseBody Map selectCategorySearch(HttpServletRequest request) throws Exception 
	{
		Map rtnMap = new HashMap<String, Object>();

		try 
		{
			DataMap paramMap = new DataMap(request);
			

			// 데이터 조회
			List<PSCMCOM0004VO> categoryList = PSCMCOM0004Service.selectCategoryList(paramMap);
			
			int categoryList_size = 0;
			if(categoryList_size > 0){
				
				categoryList_size = categoryList_size;
			}

			rtnMap = JsonUtils.convertList2Json((List)categoryList, categoryList_size, null);
			
			// 처리성공
	        rtnMap.put("result", true);

		} 
		catch (Exception e) 
		{
			logger.error("error --> " + e.getMessage());
	        rtnMap.put("result", false);
	        rtnMap.put("Message", e.getMessage());
		}

		return rtnMap;
	}

}
