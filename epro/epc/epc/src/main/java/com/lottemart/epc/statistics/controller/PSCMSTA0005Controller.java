/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.statistics.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.framework.property.ConfigurationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import xlib.cmc.GridData;
import xlib.cmc.OperateGridData;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.statistics.service.PSCMSTA0005Service;

/**
 * @Class Name : PSCMSTA0005Controller
 * @Description : 적립마일리지정산관리 Controller 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 5:37:08 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Controller
public class PSCMSTA0005Controller {

	private static final Logger logger = LoggerFactory.getLogger(PSCMSTA0005Controller.class);
	
	@Autowired
	private ConfigurationService config;
	
	@Autowired
	private PSCMSTA0005Service pscmsta0005Service;
	

	/**
	 * Desc : 적립마일리지정산관리 목록 화면 이동 메소드
	 * @Method Name : savingMileageList
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@RequestMapping(value = "promotion/savingMileageList.do")
	public String savingMileageList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<DataMap> savingMileageCodeList = pscmsta0005Service.selectSavingMileageCodeList();
		
		request.setAttribute("savingMileageCodeList", savingMileageCodeList);
		
		return "statistics/PSCMSTA0005";
	}
	
	/**
	 * Desc : 적립마일리지정산관리 목록 조회 메소드
	 * @Method Name : savingMileageListSearch
	 * @param request
	 * @return
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@RequestMapping(value = "promotion/savingMileageListSearch.do")
	public String savingMileageListSearch(HttpServletRequest request) {
		GridData gdRes = new GridData();
				
		try {
			// 파라미터 획득
			String wiseGridData = request.getParameter("WISEGRID_DATA");
			GridData  gdReq = OperateGridData.parse(wiseGridData);
			gdRes = OperateGridData.cloneResponseGridData(gdReq);

			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("currentPage", gdReq.getParam("currentPage"));
			paramMap.put("rowsPerPage", config.getString("count.row.per.page"));
			paramMap.put("search_date", gdReq.getParam("search_date"));
			paramMap.put("milleage_code", gdReq.getParam("milleage_code"));

			// 데이터 조회
			List<DataMap> savingMileageList = pscmsta0005Service.selectSavingMileageList(paramMap);

			// 조회된 데이터 가 없는 경우의 처리
	        if(savingMileageList == null || savingMileageList.size() == 0) {
	            gdRes.setMessage("조회된 데이터가 없습니다.");
	            gdRes.setStatus("false");
	    		request.setAttribute("wizeGridResult", gdRes);
	    		return "common/wiseGridResult";
	        }

	        // GridData 셋팅
	        for(int i = 0; i < savingMileageList.size(); i++) {
	        	DataMap map = savingMileageList.get(i);
	        	
	        	gdRes.getHeader("NUMBER").addValue(map.getString("RANK_NUM"),"");
	        	gdRes.getHeader("MILG_SCT").addValue(map.getString("MILG_SCT"),"");
	        	gdRes.getHeader("MILE_DIVN_CD").addValue(map.getString("MILE_DIVN_CD"),"");
	        	gdRes.getHeader("START_DY").addValue(map.getString("START_DY"),"");
	        	gdRes.getHeader("END_DY").addValue(map.getString("END_DY"),"");
	        	gdRes.getHeader("SAVU_MILE").addValue(map.getString("SAVU_MILE"),"");
	        	gdRes.getHeader("REG_DATE").addValue(map.getString("REG_DATE"),"");
	        	gdRes.getHeader("REG_ID").addValue(map.getString("REG_ID"),"");
	        	gdRes.getHeader("DELETE_YN").addValue(map.getString("DELETE_YN"),"");
	        }

	        String totalCount = savingMileageList.get(0).getString("TOTAL_COUNT");

	        // 페이징 변수
	        gdRes.addParam("totalCount", totalCount);
	        gdRes.addParam("rowsPerPage", config.getString("count.row.per.page"));
	        gdRes.addParam("currentPage", gdReq.getParam("currentPage"));

		} catch(Exception e) {
			gdRes.setMessage(e.getMessage());
            gdRes.setStatus("false");
            logger.debug("", e);
		}

		request.setAttribute("wizeGridResult", gdRes);
		
		return "common/wiseGridResult";
	}
}
