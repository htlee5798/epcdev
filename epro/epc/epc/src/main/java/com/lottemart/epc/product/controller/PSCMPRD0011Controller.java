/**
 * @prjectName  : lottemart 프로젝트
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.product.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;

import lcn.module.common.util.DateUtil;
import lcn.module.common.util.StringUtil;
import lcn.module.framework.property.ConfigurationService;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xlib.cmc.GridData;
import xlib.cmc.OperateGridData;

import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.util.WiseGridUtil;
import com.lottemart.epc.product.model.PSCMPRD0011VO;
import com.lottemart.epc.product.service.PSCMPRD0011Service;

/**
 * @Class Name : PSCMPRD0011Controller
 * @Description : 상품이미지촬영스케쥴목록 Controller 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 5:00:45 yskim
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Controller
public class PSCMPRD0011Controller {

	private static final Logger logger = LoggerFactory.getLogger(PSCMPRD0011Controller.class);

	@Autowired
	private ConfigurationService config;

	@Autowired
	private PSCMPRD0011Service pscmprd0011Service;

	@Autowired
	private MessageSource messageSource;

	/**
	 * Desc : 상품이미지촬영스케쥴목록 화면 이동 메소드
	 * @Method Name : selectScheduleMgrView
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/product/selectScheduleMgrView.do")
	public String selectScheduleMgrView(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);

		String startDate = DateUtil.getToday("yyyy-MM-dd");
		String endDate = DateUtil.formatDate(DateUtil.addDay(startDate, 7), "-");

		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);

		return "product/PSCMPRD0011";
	}

	/**
	 * Desc : 상품이미지촬영스케쥴목록 조회하는 메소드
	 * @Method Name : selectScheduleMgrList
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
//	@RequestMapping(value = "/product/selectScheduleMgrList.do")
//	public String selectScheduleMgrList(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		String sessionKey = config.getString("lottemart.epc.session.key");
//		EpcLoginVO epcLoginVO = null;
//		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
//
//		GridData gdRes = new GridData();
//
//		try {
//			// 파라미터 획득
//			String wiseGridData = request.getParameter("WISEGRID_DATA");
//			GridData  gdReq = OperateGridData.parse(wiseGridData);
//			gdRes = OperateGridData.cloneResponseGridData(gdReq);
//
//			// mode 셋팅
//			gdRes.addParam("mode", gdReq.getParam("mode"));
//
//			config.getString("count.row.per.page");
//
//			DataMap paramMap = new DataMap();
//			paramMap.put("currentPage", gdReq.getParam("currentPage"));
//			paramMap.put("rowsPerPage", StringUtil.null2str(gdReq.getParam("rowsPerPage"), config.getString("count.row.per.page")));
//            paramMap.put("startDate", gdReq.getParam("startDate"));
//            paramMap.put("endDate", gdReq.getParam("endDate"));
//
//			// 데이터 조회
//			List<DataMap> pscmprd0011List = pscmprd0011Service.selectScheduleList(paramMap);
//
//			int size = pscmprd0011List.size();
//
//			// 조회된 데이터 가 없는 경우의 처리
//	        if(pscmprd0011List == null || size == 0) {
//	            gdRes.setStatus("false");
//	    		request.setAttribute("wizeGridResult", gdRes);
//	    		return "common/wiseGridResult";
//	        }
//
//	        // GridData 셋팅
//	        for(int i = 0; i < size; i++) {
//	        	DataMap map = pscmprd0011List.get(i);
//
//	        	gdRes.getHeader("crud").addValue("", "");
//	        	gdRes.getHeader("selected").addValue("0", "");
//	        	gdRes.getHeader("rowNum").addValue(map.getString("RANK_NUM"), "");
//	        	gdRes.getHeader("scdlSeqs").addValue(map.getString("SCDL_SEQS"), "");
//	        	gdRes.getHeader("vendorId").addValue(map.getString("VENDOR_ID"),"");
//	        	gdRes.getHeader("vendorNm").addValue(map.getString("VENDOR_NM"),"");
//	        	gdRes.getHeader("rservStartDy").addValue(map.getString("RSERV_START_DY"),"");
//	        	gdRes.getHeader("rservStartHour").addSelectedHiddenValue(map.getString("RSERV_START_TM").substring(0, 2));
//	        	gdRes.getHeader("rservStartMin").addSelectedHiddenValue(map.getString("RSERV_START_TM").substring(2, 4));
//	        	gdRes.getHeader("rservEndHour").addSelectedHiddenValue(map.getString("RSERV_END_TM").substring(0, 2));
//	        	gdRes.getHeader("rservEndMin").addSelectedHiddenValue(map.getString("RSERV_END_TM").substring(2, 4));
//	        	gdRes.getHeader("scdlMemo").addValue(map.getString("SCDL_MEMO"),"");
//	        }
//
//	        String totalCount = pscmprd0011List.get(0).getString("TOTAL_COUNT");
//
//	        // 페이징 변수
//	        gdRes.addParam("totalCount", totalCount);
//	        gdRes.addParam("rowsPerPage", gdReq.getParam("rowsPerPage"));
//	        gdRes.addParam("currentPage", gdReq.getParam("currentPage"));
//	        gdRes.setStatus("true");
//
//		} catch(Exception e) {
//			gdRes.setStatus("false");
//			gdRes.setMessage(e.getMessage());
//		}
//
//		request.setAttribute("wizeGridResult", gdRes);
//
//		return "common/wiseGridResult";
//	}



	/**
	 * Desc : 상품이미지촬영스케쥴목록 조회하는 메소드
	 * @Method Name : selectScheduleMgrList
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/product/selectScheduleMgrList.do")
	public @ResponseBody Map selectScheduleMgrList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		Map rtnMap = new HashMap<String, Object>();

		try {
			// 파라미터 획득
//			String wiseGridData = request.getParameter("WISEGRID_DATA");
//			GridData  gdReq = OperateGridData.parse(wiseGridData);
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
			List<PSCMPRD0011VO> pscmprd0011List = pscmprd0011Service.selectScheduleList(param);

			int size = pscmprd0011List.size();
			int totalCount = 0;
			if(size > 0 ){
				totalCount = Integer.valueOf(pscmprd0011List.get(0).getTotalCount());
			}

			rtnMap = JsonUtils.convertList2Json((List)pscmprd0011List, totalCount, currentPage);

			// 처리성공
 	        rtnMap.put("result", true);

		} catch(Exception e) {
			logger.error("error --> " + e.getMessage());
 	        rtnMap.put("result", false);
 	        rtnMap.put("Message", e.getMessage());
		}

		return rtnMap;
	}

	/**
	 * Desc : 상품이미지촬영스케쥴목록 수정하는 메소드
	 * @Method Name : updateSchedule
	 * @param request
	 * @param response
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
//	@RequestMapping(value = "/product/updateSchedule.do")
//	public void updateSchedule(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		String sessionKey = config.getString("lottemart.epc.session.key");
//		EpcLoginVO epcLoginVO = null;
//		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
//
//		GridData gdRes = new GridData();
//
//		// Encode Type을 UTF-8로 변환한다.
//		request.setCharacterEncoding("UTF-8");
//		response.setContentType("text/html;charset=UTF-8");
//		PrintWriter out = response.getWriter();
//		try {
//
//			String wiseGridData = request.getParameter("WISEGRID_DATA");
//			GridData gdReq = OperateGridData.parse(wiseGridData);
//
//			// 모드셋팅
//			gdRes.addParam("mode", gdReq.getParam("mode"));
//
//			// 처리수행
//			int rowCount = gdReq.getHeader("selected").getRowCount();
//
//			List<PSCMPRD0011VO> pscmprd0011VOList = new ArrayList<PSCMPRD0011VO>();
//			PSCMPRD0011VO pscmprd0011VO = null;
//
//			// header data VO객체에 셋팅
//			for(int index = 0; index < rowCount; index++){
//				pscmprd0011VO = new PSCMPRD0011VO();
//				pscmprd0011VO = (PSCMPRD0011VO)WiseGridUtil.getWiseGridHeaderDataToObject(index, gdReq, pscmprd0011VO);
//				pscmprd0011VOList.add(pscmprd0011VO);
//			}
//
//			//등록 수행
//			gdRes = doSave(gdReq, pscmprd0011VOList);
//
//
//		} catch (Exception e) {
//			gdRes.setMessage(e.getMessage());
//		} finally {
//			try {
//				// 자료구조를 전문으로 변경해 Write한다.
//				OperateGridData.write(gdRes, out);
//			} catch (Exception e) {
//				logger.error(e+"");
//			}
//		}
//	}



	@RequestMapping(value = "/product/updateSchedule.do")
	public @ResponseBody JSONObject updateSchedule(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		JSONObject jObj = new JSONObject();
		String message = "";
		int resultCnt = 0;

		// Encode Type을 UTF-8로 변환한다.
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {

			DataMap resultMap = new DataMap();

			String mode = request.getParameter("mode");

			if("save".equals(mode)){
				resultCnt =  pscmprd0011Service.updateSchedule(request);
			} else if("delete".equals(mode)){
				resultCnt =  pscmprd0011Service.deleteSchedule(request);
			}

			if (resultCnt > 0) {
				message  = messageSource.getMessage("msg.common.success.request", null, Locale.getDefault());
				jObj.put("Code", 1);
				jObj.put("Message", resultCnt + "건의 " + message);
			} else {
//				message = resultMap.get("rstMsg").toString();

				if(message.equals("")) {
					message  = messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault());
				}

				jObj.put("Code", 0);
				jObj.put("Message", message);
			}

		} catch (Exception e) {
			jObj.put("Code", -1);
			jObj.put("Message", e.getMessage());
			// 처리오류
			logger.error("", e);
		} finally {
			jObj.put("Code", -2);
			// 처리오류
		}
			return JsonUtils.getResultJson(jObj);
	}

	/**
	 * Desc : 상품이미지촬영스케쥴목록 수정하는 메소드 (서비스 클래스 호출)
	 * @Method Name : doSave
	 * @param gdReq
	 * @param pscmprd0011VO
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public GridData doSave(GridData gdReq, List<PSCMPRD0011VO> pscmprd0011VO) throws Exception {

		GridData gdRes = new GridData();
		String returnData = "";
		String mode = gdReq.getParam("mode");

		try {
			/* 화면에 전달할  파라미터를 설정한다.
			 * 메세지를 셋팅한다.
			 * Status를 설정한다
			 */
			returnData = WiseGridUtil.getSendData(pscmprd0011VO.size(), mode);
			gdRes.addParam("saveData", returnData);
			gdRes.setStatus("true");

		} catch (Exception e) {
			gdRes.setMessage(e.getMessage());
			gdRes.setStatus("false");
			logger.error(e+"");
		}
		gdRes.addParam("mode", gdReq.getParam("mode"));
		return gdRes;
	}

	/**
	 * Desc : 상품이미지촬영스케쥴목록 엑셀다운로드하는 메소드
	 * @Method Name : selectScheduleMgrListExcel
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/product/selectScheduleMgrListExcel.do")
	public void selectScheduleMgrListExcel(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("searchVO") PSCMPRD0011VO searchVO, ModelMap model) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		// 데이터 조회
		List<PSCMPRD0011VO> list = pscmprd0011Service.selectScheduleMgrListExcel(searchVO);
		model.addAttribute("list", list);
		JsonUtils.IbsExcelDownload((List)list, request, response);
	}
}
