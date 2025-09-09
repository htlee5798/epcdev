package com.lottemart.epc.order.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.framework.property.ConfigurationService;
import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lcnjf.util.StringUtil;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.util.LoginUtil;
import com.lottemart.epc.order.model.PSCMORD0010VO;
import com.lottemart.epc.order.service.PSCMORD0010Service;

@Controller
public class PSCMORD0010Controller {

	private static final Logger logger = LoggerFactory
			.getLogger(PSCMORD0010Controller.class);

	@Autowired
	private ConfigurationService config;

	 @Autowired
	 private PSCMORD0010Service pscmord0010Service;

	/**
	 * Desc : Session & LoginId
	 *
	 * @Method Name : loginVo
	 * @param request
	 * @return
	 * @param
	 * @return
	 * @exception Exception
	 */
	@ModelAttribute("loginVo")
	public EpcLoginVO loginVo(HttpServletRequest request) {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);
		return epcLoginVO;
	}

	/**
	 * Desc :
	 *
	 * @Method Name : productDetail
	 * @param locale
	 * @param model
	 * @param request
	 * @return
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("order/selectCSRList.do")
	public String selectCSRList(HttpServletRequest request, @ModelAttribute("loginVo") EpcLoginVO epcLoginVO) {
			// 로그인 관련
			if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
				logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
			}
			// 로그인 아이디
			request.setAttribute("epcLoginVO", epcLoginVO);

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy", Locale.KOREA);

			// now Year, month seting
			Calendar calval = Calendar.getInstance();
			request.setAttribute("nowMonth",calval.get(Calendar.MONTH)+1);
			request.setAttribute("nowYear", calval.get(Calendar.YEAR));

		return "order/PSCMORD0010";
	}

	/**
	 * Desc : 카테고리 판매별 순위 조회
	 * @Method Name : searchCSRList
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("order/searchCSRList.do")
	public @ResponseBody Map searchCSRList(HttpServletRequest request)
			throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		Map rtnMap = new HashMap<String, Object>();

		try {

			DataMap param = new DataMap(request);

			// 협력사코드 전체를 선택한 경우 로그인 세션에 있는 협력사 전체를 설정한다
			if ("".equals(param.getString("searchVendorId"))) {
				param.put("vendorId", LoginUtil.getVendorList(epcLoginVO)); // 협력업체
																						// 코드
			} else {
				ArrayList<String> vendorList = new ArrayList<String>();
				vendorList.add(param.getString("searchVendorId"));
				param.put("vendorId", vendorList); // 협력업체코드
			}

			/*	페이지 관련 변수 삭제
		String rowPerPage = param.getString("rowsPerPage");
			String currentPage = param.getString("currentPage");
			int rowPage = Integer.parseInt(rowPerPage);
			int currPage = Integer.parseInt(currentPage);

			// 페이징 관련 변수
			String rowsPerPage = StringUtil.null2str(
					(String) param.get("rowsPerPage"),
					config.getString("count.row.per.page"));

			int startRow = ((Integer
					.parseInt((String) param.get("currentPage")) - 1) * Integer
					.parseInt(rowsPerPage)) + 1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) - 1;

			param.put("currentPage", (String) param.get("currentPage"));
			param.put("startRow", Integer.toString(startRow));
			param.put("endRow", Integer.toString(endRow));
			param.put("rowsPerPage", rowsPerPage);*/
			////////////////////////////////////////////

			//날짜
			String year =   param.getString("year");
			String month = param.getString("month");

			//조회 관련 변수
			param.put("startDate", year+month+"01");
			param.put("endDate", year+month+"31");
			param.put("searchVendorId", param.getString("searchVendorId"));

			// 전체 조회 건수
			int totalCnt = pscmord0010Service.selectCSRListCnt((Map) param);
			param.put("totalCount", Integer.toString(totalCnt));

			// 리스트 조회
			List<PSCMORD0010VO> list = pscmord0010Service.selectCSRList(param);

			//페이지 설정시 주석 삭제
			/*rtnMap = JsonUtils.convertList2Json((List) list, totalCnt,
					param.getString("currentPage"));*/

			//페이지 비설정시 주석 처리

			// json
			JSONArray jArray = new JSONArray();
			if (list != null)
			jArray = (JSONArray) JSONSerializer.toJSON(list);

			String jStr = "{Data:" + jArray + "}";
			rtnMap.put("ibsList", jStr);

			// 조회된 데이터가 없는 경우
			if (jArray.isEmpty()) {
				rtnMap.put("result", false);
			}
			//////////////////////////////////////

			rtnMap.put("result", true);
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}



	/**
	 * Desc :
	 * @Method Name : exportPSCMORD0010Excel
	 * @param request
	 * @param response
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/order/exportPSCMORD0010Excel.do")
	public void exportPSCMORD0010Excel(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		try{
	    	DataMap paramMap = new DataMap(request);

			// 협력사코드 전체를 선택한 경우 로그인 세션에 있는 협력사 코드 전체를 설정한다.
			if("".equals(paramMap.getString("searchVendorId")))	{
				paramMap.put("vendorId", LoginUtil.getVendorList(epcLoginVO)); //협력업체코드
			} else	{
				ArrayList<String> vendorList = new ArrayList<String>();
				vendorList.add(paramMap.getString("searchVendorId"));
				paramMap.put("vendorId", vendorList); //협력업체코드
			}

			String rowPerPage = paramMap.getString("rowsPerPage");
			String currentPage = paramMap.getString("currentPage");
			int rowPage = Integer.parseInt(rowPerPage);
			int currPage = Integer.parseInt(currentPage);

			// 페이징 관련 변수
			String rowsPerPage = StringUtil.null2str(
					(String) paramMap.get("rowsPerPage"),
					config.getString("count.row.per.page"));

			int startRow = ((Integer
					.parseInt((String) paramMap.get("currentPage")) - 1) * Integer
					.parseInt(rowsPerPage)) + 1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) - 1;

			paramMap.put("currentPage", (String) paramMap.get("currentPage"));
			paramMap.put("startRow", Integer.toString(startRow));
			paramMap.put("endRow", Integer.toString(endRow));
			paramMap.put("rowsPerPage", rowsPerPage);

			//날짜
			String year =   paramMap.getString("year");
			String month = paramMap.getString("month");

			//조회 관련 변수
			paramMap.put("startDate", year+month+"01");
			paramMap.put("endDate", year+month+"31");
			paramMap.put("searchVendorId", paramMap.getString("searchVendorId"));

			List<Map<Object, Object>> list = pscmord0010Service.selectPscmord0011Export(paramMap);

			JsonUtils.IbsExcelDownload((List) list, request, response);
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
		}
	}

}
