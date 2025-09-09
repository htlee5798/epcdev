package com.lottemart.epc.board.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.common.code.service.CommonCodeService;
import com.lottemart.common.exception.AlertException;
import com.lottemart.common.exception.AppException;
import com.lottemart.common.exception.TopLevelException;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.board.model.PSCPBRD0010VO;
import com.lottemart.epc.board.service.PSCMBRD0010Service;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.service.CommonService;

import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.ConfigurationService;

/**
 * @Class Name : PSCMBRD0010Controller.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2016. 01. 15. projectBOS32
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Controller
public class PSCMBRD0010Controller {

	private static final Logger logger = LoggerFactory.getLogger(PSCMBRD0010Controller.class);

	@Autowired
	private CommonCodeService commonCodeService;

	@Autowired
	private ConfigurationService config;

	@Autowired
	private PSCMBRD0010Service pscmbrd0010Service;

	@Autowired
	private CommonService commonService;

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
	@RequestMapping(value = "/board/selectCcQnaList.do")
	public String selectSuggestionList(@ModelAttribute("searchVO") PSCPBRD0010VO searchVO, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);

		// 선택한 vendorId가null 이거나 repVendorId ( 대표협력업체 ) 일때 협력사 전체 검색
		if (searchVO.getVendorId() == null || searchVO.getVendorId().length == 0 || epcLoginVO.getRepVendorId().equals(searchVO.getVendorId()[0].toString())) {
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

		if ("06".equals(epcLoginVO.getVendorTypeCd())) {
			if ((searchVO.getVendorId()[0].toString()).indexOf("T") < 0) {
				searchVO.setVendorMode("V");
			}
		}

		String endDate = DateUtil.getToday("yyyy-MM-dd");
		String startDate = DateUtil.formatDate(DateUtil.addDay(endDate, -7), "-");

		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);

		List<DataMap> codeList1 = commonCodeService.getCodeList("QA001"); // 문의유형(대) 공통코드 조회

		List<DataMap> codeList2 = commonCodeService.getCodeList("QA003"); // 글종류 공통코드 조회

		List<DataMap> codeList3 = commonCodeService.getCodeList("QA004"); // 진행상태 공통코드 조회

		List<DataMap> codeList4 = commonCodeService.getCodeList("QA005"); // 문의접수처 공통코드 조회

		request.setAttribute("codeList1", codeList1);
		request.setAttribute("codeList2", codeList2);
		request.setAttribute("codeList3", codeList3);
		request.setAttribute("codeList4", codeList4);

		return "board/PSCMBRD0010";
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
	@RequestMapping(value = "/board/selectCcQnaSearch.do")
	public @ResponseBody Map selectSuggestionSearch(@ModelAttribute("searchVO") PSCPBRD0010VO searchVO, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		Map rtnMap = new HashMap<String, Object>();

		try {
			request.setAttribute("epcLoginVO", epcLoginVO);

			DataMap paramMap = new DataMap(request);

			String currentPage = paramMap.getString("currentPage");
			paramMap.put("startDate", paramMap.getString("startDate").replaceAll("-", ""));
			paramMap.put("endDate", paramMap.getString("endDate").replaceAll("-", ""));

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
				if (openappiVendorId.get(l).getRepVendorId().equals(searchVO.getVendorId()[0].toString().replace("[", "").replace("]", "").trim())) {
					searchVO.setVendorId(epcLoginVO.getVendorId());
				}
			}

			paramMap.put("vendorId", searchVO.getVendorId());
			paramMap.put("adminId", epcLoginVO.getAdminId());

			// 데이터 조회
			List<DataMap> list = pscmbrd0010Service.selectBoardList(paramMap);
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
}
