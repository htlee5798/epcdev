package com.lottemart.epc.common.controller;

import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.model.PSCMCOM0007VO;
import com.lottemart.epc.common.service.PSCMCOM0007Service;
import com.lottemart.epc.util.Utils;

/**
 * Handles requests for the application home page.
 */
@Controller
public class PSCMCOM0007Controller {

	@Autowired
	private PSCMCOM0007Service PSCMCOM0007Service;

	/**
	 * 주문번호 목록
	 * @Description : 주문번호 조회
	 * @Method Name : selectCategorySearch
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/common/selectOrderIdList.do")
	public String selectPopupProductList(@ModelAttribute("searchVO") PSCMCOM0007VO searchVO, ModelMap model) throws Exception {

		String from_date = "";     // 기간(시작)
		String to_date 	 = "";     // 기간(끝)

		Calendar NowDate = Calendar.getInstance();
		Calendar NowDate2 = Calendar.getInstance();
		NowDate.add(Calendar.DATE, 0);
		NowDate2.add(Calendar.DATE, -21);

		String today_date = Utils.formatDate(NowDate.getTime(),"yyyy-MM-dd");
		String today_date2 = Utils.formatDate(NowDate2.getTime(),"yyyy-MM-dd");

		// 초최 오픈시 Default 값 세팅
		if(from_date == null || from_date.equals("")) from_date = today_date2;
		if(to_date   == null || to_date.equals(""))   to_date   = today_date;

		searchVO.setStartDate(from_date);
		searchVO.setEndDate(to_date);
		searchVO.setFromDate(searchVO.getStartDate().replaceAll("-", ""));
		searchVO.setToDate(searchVO.getEndDate().replaceAll("-", ""));

		List<DataMap> list = PSCMCOM0007Service.selectOrderIdList(searchVO);
		model.addAttribute("list", list);
        model.addAttribute("searchVO", searchVO);
		return "common/PSCMCOM0007";
	}
}
