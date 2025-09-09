package com.lottemart.epc.board.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.util.DateUtil;
import lcn.module.common.util.StringUtil;
import lcn.module.framework.property.ConfigurationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.lottemart.epc.board.model.PSCMBRD0005SearchVO;


import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.board.service.PSCMBRD0005Service;
import com.lottemart.epc.common.model.EpcLoginVO;

/**
 * @Class Name : PSCMBRD0005Controller.java
 * @Description :
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
public class PSCMBRD0005Controller 
{
	private static final Logger logger = LoggerFactory.getLogger(PSCMBRD0005Controller.class);
	
	@Autowired
	private ConfigurationService config;
	@Autowired
	private PSCMBRD0005Service pscmbrd0005Service;

	/**
	 * Desc : 업체문의관리 목록
	 * 
	 * @Method Name : selectSuggestionList
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/board/selectSuggestionList.do")
	public String selectSuggestionList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);

		String endDate = DateUtil.getToday("yyyy-MM-dd");
		String startDate = DateUtil.formatDate(DateUtil.addDay(endDate, -7), "-");

		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);

		return "board/PSCMBRD0005";
	}
	
	/**
	 * Desc : 업체문의관리 목록을 조회하는 메소드
	 * @Method Name : selectSuggestionSearch
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
//	@RequestMapping(value = "/board/selectSuggestionSearch.do")
//	public String selectSuggestionSearch(HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		
//		String sessionKey = config.getString("lottemart.epc.session.key");
//		EpcLoginVO epcLoginVO = null;
//		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);
//
//		GridData gdRes = new GridData();
//
//		try {
//			// 파라미터 획득
//			String wiseGridData = request.getParameter("WISEGRID_DATA");
//			GridData gdReq = OperateGridData.parse(wiseGridData);
//			gdRes = OperateGridData.cloneResponseGridData(gdReq);
//
//			// mode 셋팅
//			gdRes.addParam("mode", gdReq.getParam("mode"));
//
//			config.getString("count.row.per.page");
//
//			DataMap paramMap = new DataMap();
//			paramMap.put("currentPage", gdReq.getParam("currentPage"));
//			paramMap.put("rowsPerPage",StringUtil.null2str(gdReq.getParam("rowsPerPage"),config.getString("count.row.per.page")));
//
//			paramMap.put("startDate", gdReq.getParam("startDate"));
//			paramMap.put("endDate", gdReq.getParam("endDate"));
//			paramMap.put("searchWord", gdReq.getParam("searchWord"));
//
//			// 데이터 조회
//			List<DataMap> list = pscmbrd0005Service.selectBoardList(paramMap);
//
//			int size = list.size();
//
//			// 조회된 데이터 가 없는 경우의 처리
//			if (list == null || size == 0) {
//				gdRes.setStatus("false");
//				request.setAttribute("wizeGridResult", gdRes);
//				return "common/wiseGridResult";
//			}
//
//			// GridData 셋팅
//			for (int i = 0; i < size; i++) {
//				DataMap map = list.get(i);
//
//				gdRes.getHeader("rownum").addValue(map.getString("ROWNUM"), "");
//				gdRes.getHeader("boardSeq").addValue(map.getString("BOARD_SEQ"), "");
//				gdRes.getHeader("title").addValue(map.getString("TITLE"), "");
//				gdRes.getHeader("viewCnt").addValue(map.getString("VIEW_CNT"), "");
//				gdRes.getHeader("commCnt").addValue(map.getString("COMM_CNT"), "");
//				gdRes.getHeader("regId").addValue(map.getString("REG_ID"), "");
//				gdRes.getHeader("regDate").addValue(map.getString("REG_DATE"), "");
//				gdRes.getHeader("scrpKindCd").addValue(map.getString("SCRP_KIND_CD"), "");
//			}
//
//			String totalCount = list.get(0).getString("TOTAL_COUNT");
//
//			// 페이징 변수
//			gdRes.addParam("totalCount", totalCount);
//			gdRes.addParam("rowsPerPage", gdReq.getParam("rowsPerPage"));
//			gdRes.addParam("currentPage", gdReq.getParam("currentPage"));
//			gdRes.setStatus("true");
//
//		} catch (Exception e) {
//			gdRes.setStatus("false");
//			gdRes.setMessage(e.getMessage());
//		}
//
//		request.setAttribute("wizeGridResult", gdRes);
//
//		return "common/wiseGridResult";
//	}
	
	
	
	
	@RequestMapping(value = "/board/selectSuggestionSearch.do")
	public @ResponseBody Map selectSuggestionSearch(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);
		
		Map rtnMap = new HashMap<String, Object>();
		
		try {
			// 파라미터 획득
//			String wiseGridData = request.getParameter("WISEGRID_DATA");
//			GridData gdReq = OperateGridData.parse(wiseGridData);
//			gdRes = OperateGridData.cloneResponseGridData(gdReq);
			
			// mode 셋팅
//			gdRes.addParam("mode", gdReq.getParam("mode"));
			
			config.getString("count.row.per.page");
			
			DataMap param = new DataMap(request);
			String rowPerPage  = param.getString("rowsPerPage");   
			String currentPage = param.getString("currentPage");   
			int rowPage = Integer.parseInt(rowPerPage);
			int currPage = Integer.parseInt(currentPage);
			
			// 페이징
			String rowsPerPage = StringUtil.null2str(rowPerPage, config.getString("count.row.per.page"));
			int startRow = ((Integer.parseInt(currentPage)-1)*Integer.parseInt(rowsPerPage))+1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) -1;
			param.put("startRow", Integer.toString(startRow));
			param.put("endRow", Integer.toString(endRow));
			param.put("startDate", param.getString("startDate").replaceAll("-", ""));
			param.put("endDate", param.getString("endDate").replaceAll("-", ""));
			
			// 데이터 조회
			List<PSCMBRD0005SearchVO> list = pscmbrd0005Service.selectBoardList(param);
			int size = list.size();
			int totalCount = 0;
			
			if(size > 0 ){
				totalCount = Integer.valueOf(list.get(0).getTotalCount());
			}
			
			rtnMap = JsonUtils.convertList2Json((List)list, totalCount, currentPage);
			
			// 처리성공
 	        rtnMap.put("result", true);
			
		} catch (Exception e) {
			logger.error("error --> " + e.getMessage());
 	        rtnMap.put("result", false);
 	        rtnMap.put("Message", e.getMessage());
		}
		
		return rtnMap;
	}
}
