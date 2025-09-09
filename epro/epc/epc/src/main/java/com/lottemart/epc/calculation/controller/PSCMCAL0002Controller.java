/**
 * @prjectName  : lottemart 프로젝트
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.calculation.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lcn.module.common.util.DateUtil;
import lcn.module.common.util.StringUtil;
import lcn.module.framework.property.ConfigurationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.calculation.model.PSCMCAL0002VO;
import com.lottemart.epc.calculation.service.PSCMCAL0002Service;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.util.LoginUtil;

/**
 * @Class Name : PSCMCAL0002Controller
 * @Description : 배송비 정산 목록 Controller 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 4:09:43 yskim
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Controller
public class PSCMCAL0002Controller {

	@Autowired
	private ConfigurationService config;

	@Autowired
	private PSCMCAL0002Service pscmcal0002Service;

	/**
	 * Desc : 배송비 정산 목록을 조회하는 메소드
	 * @Method Name : selectDeliveryCostsCalculateList
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/calculation/selectDeliveryCostsCalculateList.do")
	public String selectDeliveryCostsCalculateList(@ModelAttribute("searchVO") PSCMCAL0002VO searchVO, ModelMap model, HttpServletRequest request) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);

		if(searchVO.getVendorId() == null || searchVO.getVendorId().size() < 1) {
			searchVO.setVendorId(LoginUtil.getVendorList(epcLoginVO));
			searchVO.setSearchVendorId("");
		} else {
			searchVO.setSearchVendorId(searchVO.getVendorId().get(0));
		}

		if(StringUtil.isNVL(searchVO.getStartDate()) || StringUtil.isNVL(searchVO.getEndDate())) {
			String today = DateUtil.getToday("yyyy-MM-dd");
			searchVO.setStartDate(today);
			searchVO.setEndDate(today);
		}

		model.addAttribute("searchVO", searchVO);

		List<DataMap> orderStats = pscmcal0002Service.selectDeliveryCostsCalculateOrderStats(searchVO);
		model.addAttribute("orderStats", orderStats.get(0));

		List<DataMap> deliveryStats = pscmcal0002Service.selectDeliveryCostsCalculateDeliveryStats(searchVO);
		model.addAttribute("deliveryStats", deliveryStats.get(0));

		List<DataMap> list = pscmcal0002Service.selectDeliveryCostsCalculateList(searchVO);
		model.addAttribute("list", list);


        String resultMsg = "해당 자료가 없습니다.";
        if(list.size() > 0) {
        	resultMsg = "정상적으로 조회되었습니다.";
        }
        model.addAttribute("resultMsg", resultMsg);

		return "calculation/PSCMCAL0002";
	}

	/**
	 * Desc : 배송비 정산 목록을 엑셀다운로드하는 메소드
	 * @Method Name : selectDeliveryCostsCalculateListExcel
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/calculation/selectDeliveryCostsCalculateListExcel.do")
	public String selectDeliveryCostsCalculateListExcel(HttpServletRequest request, @ModelAttribute("searchVO") PSCMCAL0002VO searchVO, ModelMap model) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		if(searchVO.getVendorId() == null || searchVO.getVendorId().size() < 1) {
			searchVO.setVendorId(LoginUtil.getVendorList(epcLoginVO));
		}

		// 데이터 조회
		List<DataMap> list = pscmcal0002Service.selectDeliveryCostsCalculateListExcel(searchVO);
		model.addAttribute("list", list);
		return "calculation/PSCMCAL000201";
	}

}
