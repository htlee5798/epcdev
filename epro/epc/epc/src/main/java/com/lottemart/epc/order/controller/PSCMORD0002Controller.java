package com.lottemart.epc.order.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.common.exception.AlertException;
import com.lottemart.common.exception.AppException;
import com.lottemart.common.exception.TopLevelException;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.service.CommonService;
import com.lottemart.epc.common.util.SecureUtil;
import com.lottemart.epc.order.model.PSCMORD0002VO;
import com.lottemart.epc.order.service.PSCMORD0002Service;
import com.lottemart.epc.util.Utils;

@Controller
public class PSCMORD0002Controller {

	private static final Logger logger = LoggerFactory.getLogger(PSCMORD0002Controller.class);

	@Autowired
	private ConfigurationService config;

	@Autowired
	private PSCMORD0002Service pscmord0002Service;

	@Autowired
	private CommonService commonService;

	@RequestMapping(value = "order/viewProductSaleSum.do")
	public String viewProductSaleSum(@ModelAttribute("searchVO") PSCMORD0002VO searchVO, HttpServletRequest request, ModelMap model) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}

		request.setAttribute("epcLoginVO", epcLoginVO);

		String from_date = ""; // 기간(시작)
		String to_date = ""; // 기간(끝)

		Calendar NowDate = Calendar.getInstance();
		Calendar NowDate2 = Calendar.getInstance();
		NowDate.add(Calendar.DATE, 0);
		NowDate2.add(Calendar.DATE, -21);

		String today_date = Utils.formatDate(NowDate.getTime(), "yyyy-MM-dd");
		String today_date2 = Utils.formatDate(NowDate2.getTime(), "yyyy-MM-dd");

		// 초최 오픈시 Default 값 세팅
		if (from_date == null || "".equals(from_date))
			from_date = today_date2;
		if (to_date == null || "".equals(to_date))
			to_date = today_date;

		searchVO.setStartDate(from_date);
		searchVO.setEndDate(to_date);
		searchVO.setFromDate(searchVO.getStartDate().replaceAll("-", ""));
		searchVO.setToDate(searchVO.getEndDate().replaceAll("-", ""));

		model.addAttribute("searchVO", searchVO);
		return "order/PSCMORD0002";
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/order/selectProductSaleSum.do")
	public @ResponseBody Map selectProductSaleSum(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		PSCMORD0002VO searchVO = new PSCMORD0002VO();
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		Map rtnMap = new HashMap<String, Object>();

		try {
			DataMap paramMap = new DataMap(request);

			String currentPage = paramMap.getString("currentPage");
			String prodFlag = paramMap.getString("prodFlag");
			String prodCd = paramMap.getString("prodCd");
			String rowsPerPage = paramMap.getString("rowsPerPage");
			String endRow = paramMap.getString("endRow");
			String startRow = paramMap.getString("startRow");

			if (!"4".equals(prodFlag)) {
				String prodCdSpl[] = prodCd.split(",");
				searchVO.setProdCdArr(prodCdSpl);
			}

			searchVO.setProdCd(prodCd);
			searchVO.setProdFlag(prodFlag);
			searchVO.setCategoryId(paramMap.getString("categoryId"));
			searchVO.setFromDate(paramMap.getString("startDate").replaceAll("-", ""));
			searchVO.setToDate(paramMap.getString("endDate").replaceAll("-", ""));
			searchVO.setRowsPerPage(rowsPerPage);
			searchVO.setEndRow(endRow);
			searchVO.setStartRow(startRow);
			searchVO.setCurrentPage(currentPage);

			// 선택한 vendorId가null 이거나 repVendorId ( 대표협력업체 ) 일때 협력사 전체 검색
			if (paramMap.getString("vendorId").length() == 0 || epcLoginVO.getRepVendorId().equals(paramMap.getString("vendorId"))) {
				searchVO.setVendorId(epcLoginVO.getVendorId());
			} else {
				String venderId[] = { paramMap.getString("vendorId") };
				searchVO.setVendorId(venderId);
			}
			// 선택한 vendor가 openappi 업체일 경우 전체 검색
			List<EpcLoginVO> openappiVendorId = commonService.selectOpenappiVendor();
			for (int l = 0; openappiVendorId.size() > l; l++) {
				if (openappiVendorId.get(l).getRepVendorId().equals(paramMap.getString("vendorId").replace("[", "").replace("]", "").trim())) {
					searchVO.setVendorId(epcLoginVO.getVendorId());
				}
			}

			// 데이터 조회
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			Date to = format.parse(searchVO.getToDate());
			Date from = format.parse(searchVO.getFromDate());
			long diffDay = (to.getTime() - from.getTime()) / (24 * 60 * 60 * 1000);
			String [] tmpVendorId = searchVO.getVendorId();

			if (diffDay > 3) { // 검색기간 3일 초과시에만 힌트 적용
				searchVO.setSqlVal(SecureUtil.sqlValid("/*+ LEADING(B) INDEX(O PK_OR_ORDER) */")); // 2023-01-05 DBA튜닝 가이드
			}
			if (tmpVendorId != null && tmpVendorId.length > 10) {
				searchVO.setSqlVal("");
			}
//			else{
//				sql = "/*+ leading(O A B ) INDEX(O IX_OR_ORDER_07) INDEX(A IX_OR_ORDER_ITEM_03) INDEX(B PK_PR_PRODUCT) */";
//			}

			List<DataMap> list = pscmord0002Service.selectProductSaleSumList(searchVO);
			rtnMap = JsonUtils.convertList2Json((List) list, -1, currentPage);

			rtnMap.put("result", true);
		} catch (AppException | TopLevelException | AlertException e) {
			logger.error("error getMessage --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", "조회시 오류가 발생하였습니다.");
		}

		return rtnMap;
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/order/selectProductSaleSumExcel.do")
	public String selectProductSaleSumExcel(@ModelAttribute("searchVO") PSCMORD0002VO searchVO, HttpServletRequest request, ModelMap model) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}

		request.setAttribute("epcLoginVO", epcLoginVO);

		String prodFlag = searchVO.getProdFlag();
		String prodCd = searchVO.getProdCd();

		if (!"4".equals(prodFlag)) {
			String prodCdSpl[] = prodCd.split(",");
			searchVO.setProdCdArr(prodCdSpl);
		}

		// 데이터 조회
		searchVO.setFromDate(searchVO.getStartDate().replaceAll("-", ""));
		searchVO.setToDate(searchVO.getEndDate().replaceAll("-", ""));

		// 선택한 vendorId가null 이거나 repVendorId ( 대표협력업체 ) 일때 협력사 전체 검색
		if (searchVO.getVendorId().length == 0 || epcLoginVO.getRepVendorId().equals(searchVO.getVendorId()[0].toString())) {
			searchVO.setVendorId(epcLoginVO.getVendorId());
		} else {
			request.setAttribute("vendorId", searchVO.getVendorId()[0].toString());
		}

		// 선택한 vendor가 openappi 업체일 경우 전체 검색
		List<EpcLoginVO> openappiVendorId = commonService.selectOpenappiVendor();
		for (int l = 0; openappiVendorId.size() > l; l++) {
			if (openappiVendorId.get(l).getRepVendorId().equals(searchVO.getVendorId()[0].toString().replace("[", "").replace("]", "").trim())) {
				searchVO.setVendorId(epcLoginVO.getVendorId());
			}
		}

		// 데이터 조회
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date to = format.parse(searchVO.getToDate());
		Date from = format.parse(searchVO.getFromDate());
		long diffDay = (to.getTime() - from.getTime()) / (24 * 60 * 60 * 1000);

		if (diffDay > 3) { // 검색기간 3일 초과시에만 힌트 적용
			searchVO.setSqlVal(SecureUtil.sqlValid("/*+ LEADING(B) INDEX(O PK_OR_ORDER) */")); // 2023-01-05 DBA튜닝 가이드
		}

		List<DataMap> list = pscmord0002Service.selectProductSaleSumListExcel(searchVO);
		model.addAttribute("list", list);
		return "order/PSCMORD000201";
	}

}
