package com.lottemart.epc.edi.weborder.controller;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import lcn.module.common.views.AjaxJsonModelHelper;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.edi.comm.controller.BaseController;

import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.model.TedOrdProcess001VO;
import com.lottemart.epc.edi.weborder.service.PEDMWEB0002Service;


/**
 * @Class Name : PEDMWEB0002Controller
 * @Description : 상품별 발주등록 Controller 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2014. 08. 06. 오후 1:33:50 ljy
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */


@Controller
public class PEDMWEB0002Controller extends BaseController{


	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(PEDMWEB0002Controller.class);

	private PEDMWEB0002Service pEDMWEB0002Service;

	@Autowired
	private MessageSource messageSource;



	@Autowired
	public void setpEDMWEB0002Service(PEDMWEB0002Service pEDMWEB0002Service) {
		this.pEDMWEB0002Service = pEDMWEB0002Service;
	}

	/**
	 * Desc : 상품별 발주등록
	 * @Method Name : tempNewProductList
	 * @param SearchWebOrder
	 * @param HttpServletRequest
	 * @param Model
	 * @return view 페이지
	 */
	@RequestMapping(value="/edi/weborder/PEDMWEB0002")
    public String prodWebOrderList(SearchWebOrder searchParam, HttpServletRequest request, Model model) {
		SimpleDateFormat format = new SimpleDateFormat("HHmm", Locale.KOREA);

		int nowDt = Integer.parseInt(format.format(new Date()));

		String vendorWebOrdFrDt = ConfigUtils.getString("edi.weboder.vendor.fr");
		String vendorWebOrdToDt = ConfigUtils.getString("edi.weboder.vendor.to");

		if( ( nowDt < Integer.parseInt(vendorWebOrdFrDt)) || ( nowDt > Integer.parseInt(vendorWebOrdToDt)) ){
			return "edi/weborder/PEDMWEB0098";
		}

		searchParam.setVendorWebOrdFrDt(vendorWebOrdFrDt);
		searchParam.setVendorWebOrdToDt(vendorWebOrdToDt);

		if(StringUtils.isEmpty(searchParam.getWorkGb())){
			searchParam.setWorkGb("1");
		}
		if(StringUtils.isEmpty(searchParam.getProdCd())){
			searchParam.setProdCd("");
		}
		if(StringUtils.isEmpty(searchParam.getProdNm())){
			searchParam.setProdNm("");
		}

		model.addAttribute("paramMap",searchParam);

		return "edi/weborder/PEDMWEB0002";
	}

	/**
	 * Desc : 상품 리스트 조회
	 * @Method Name : getStoreOrdInfo
	 * @param SearchWebOrder
	 * @return 조회
	 */
	@RequestMapping("/edi/weborder/tedProdOrdSelect.do")
	public ModelAndView getStoreOrdInfo(@RequestBody SearchWebOrder vo) throws Throwable
	{
		return AjaxJsonModelHelper.create(pEDMWEB0002Service.selectProdOrdList(vo));
	}

	/**
	 * Desc : 점포 리스트 조회
	 * @Method Name : getStoreOrdInfo
	 * @param SearchWebOrder
	 * @return 조회
	 */
	@RequestMapping("/edi/weborder/tedProdOrdDetailSelect.do")
	public ModelAndView getStoreOrdDetInfo(@RequestBody SearchWebOrder vo) throws Throwable
	{
		return AjaxJsonModelHelper.create(pEDMWEB0002Service.selectProdOrdDetList(vo));
	}

	/**
	 * Desc : 상품 수량 저장 및 수정
	 * @Method Name : getProdOrdTempSave
	 * @param TedOrdProcess001VO
	 * @param HttpServletRequest
	 * @return message
	 */
	@RequestMapping("/edi/weborder/tedProdOrdTempSave.do")
	public ModelAndView getProdOrdTempSave(@RequestBody TedOrdProcess001VO vo, HttpServletRequest request) throws Exception
	{
		String message;
		String result;

		message = messageSource.getMessage("msg.common.fail.insert", null, Locale.getDefault());

		try {
			result = pEDMWEB0002Service.insertProdOrdTemp(vo, request);
			if(result.equals("suc")) return AjaxJsonModelHelper.create("");
			else return AjaxJsonModelHelper.create(message);

		} catch (Exception e) {
			return AjaxJsonModelHelper.create(message);
		}

	}

	/**
	 * Desc : 상품 정보 삭제
	 * @Method Name : getProdOrdDelete
	 * @param TedOrdProcess001VO
	 * @param HttpServletRequest
	 * @return message
	 */
	@RequestMapping("/edi/weborder/tedProdOrdDelete.do")
	public ModelAndView tedProdOrdDelete(@RequestBody TedOrdProcess001VO vo) throws Exception
	{
		String message;
		String result;

		message = messageSource.getMessage("msg.common.fail.insert", null, Locale.getDefault());

		try {
			result = pEDMWEB0002Service.deleteProdOrdInfo(vo);
			if(result.equals("suc")) return AjaxJsonModelHelper.create("");
			else return AjaxJsonModelHelper.create(message);
		} catch (Exception e) {
			return AjaxJsonModelHelper.create(message);
		}

	}
}
