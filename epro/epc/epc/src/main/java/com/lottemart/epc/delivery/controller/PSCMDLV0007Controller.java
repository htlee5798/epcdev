package com.lottemart.epc.delivery.controller;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lcn.module.common.util.StringUtil;
import lcn.module.framework.property.ConfigurationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.delivery.model.PSCMDLV0007VO;
import com.lottemart.epc.delivery.service.PSCMDLV0007Service;
import com.lottemart.epc.util.Utils;

@Controller
public class PSCMDLV0007Controller {

	private static final Logger logger = LoggerFactory.getLogger(PSCMDLV0007Controller.class);

	@Autowired
	private ConfigurationService config;

	@Autowired
	private PSCMDLV0007Service pscmdlv0007Service;


	@RequestMapping(value = "delivery/selectPartherReturnStatusList.do")
	public String selectPartherReturnStatusList(@ModelAttribute("searchVO") PSCMDLV0007VO searchVO, HttpServletRequest request, ModelMap model) throws Exception {


		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if( epcLoginVO == null || epcLoginVO.getVendorId() == null )
		{
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}

		request.setAttribute("epcLoginVO", epcLoginVO);

		// 주문일자 조회조건이 없을 경우
		if(StringUtil.isNVL(searchVO.getStartDate()) || StringUtil.isNVL(searchVO.getEndDate())) {

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

		}

		searchVO.setFromDate(searchVO.getStartDate().replaceAll("-", ""));
		searchVO.setToDate(searchVO.getEndDate().replaceAll("-", ""));

		// 협력사코드 전체를 선택한  경우 로그인 세션에 있는 협력사 코드 전체를 설정한다.
		if(searchVO.getVendorId()==null||searchVO.getVendorId().length==0) {
			searchVO.setVendorId(epcLoginVO.getVendorId());
		}else{
			request.setAttribute("vendorId", searchVO.getVendorId()[0].toString());
		}

		searchVO.setStrCd(config.getString("online.rep.str.cd"));

		List<DataMap> list = pscmdlv0007Service.selectPartherReturnStatusList(searchVO);

		DataMap map = pscmdlv0007Service.selectPartherReturnStatusSum(searchVO);

		if(list!=null && list.size() > 0){
			searchVO.setFlag("success");
		}else{
			searchVO.setFlag("zero");
		}
		model.addAttribute("list", list);
		model.addAttribute("map", map);
        model.addAttribute("searchVO", searchVO);
		return "delivery/PSCMDLV0007";

	}

}
