package com.lottemart.epc.edi.weborder.controller;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
import com.lottemart.epc.edi.weborder.model.NEDMWEB0010VO;
import com.lottemart.epc.edi.weborder.model.NEDMWEB0020VO;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.model.TedOrdProcess001VO;
import com.lottemart.epc.edi.weborder.service.NEDMWEB0020Service;


/**
 * @Class Name : NEDMWEB0020Controller
 * @Description : 상품별 발주등록 Controller Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      		수정자          		수정내용
 *  -------    --------    ---------------------------
 * 2015.12.07	O YEUN KWON	  최초생성
 *
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */


@Controller
public class NEDMWEB0020Controller extends BaseController{


	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(NEDMWEB0020Controller.class);

	private NEDMWEB0020Service nEDMWEB0020Service;

	@Autowired
	private MessageSource messageSource;



	@Autowired
	public void setpEDMWEB0002Service(NEDMWEB0020Service nEDMWEB0020Service) {
		this.nEDMWEB0020Service = nEDMWEB0020Service;
	}

	/**
	 * Desc : 상품별 발주등록
	 * @Method Name : tempNewProductList
	 * @param SearchWebOrder
	 * @param HttpServletRequest
	 * @param Model
	 * @return view 페이지
	 */
	@RequestMapping(value="/edi/weborder/NEDMWEB0020.do")
    public String prodWebOrderList(SearchWebOrder searchParam, HttpServletRequest request, Model model) {
		SimpleDateFormat format = new SimpleDateFormat("HHmm", Locale.KOREA);

		int nowDt = Integer.parseInt(format.format(new Date()));

		String vendorWebOrdFrDt = ConfigUtils.getString("edi.weboder.vendor.fr");
		String vendorWebOrdToDt = ConfigUtils.getString("edi.weboder.vendor.to");

		if( ( nowDt < Integer.parseInt(vendorWebOrdFrDt)) || ( nowDt > Integer.parseInt(vendorWebOrdToDt)) ){
			return "edi/weborder/NEDMWEB0098";
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

		return "edi/weborder/NEDMWEB0020";
	}

	/**
	 * Desc : 협력업체 코드별 점포 정보 조회
	 * @Method Name : getStoreOrdInfo
	 * @param SearchWebOrder
	 * @return 조회 값
	 */
	@RequestMapping("/edi/weborder/NEDMWEB0020vendorExceptionSelect0020.json")
	public ModelAndView getVndorException(@RequestBody NEDMWEB0020VO nEDMWEB0020VO) throws Exception
	{
		Map<String, Object> rtnData = new HashMap<String, Object>();
		rtnData.put("state", "-1");

		try{
			rtnData = nEDMWEB0020Service.selectVendorException(nEDMWEB0020VO);

			rtnData.put("state", 	"0");
			rtnData.put("message", 	"SUCCESS");
		}
		catch (Exception e) {
			rtnData.put("message",e.getMessage());
			logger.error(e.getMessage(), e);
			// TODO: handle exception
		}

		return AjaxJsonModelHelper.create(rtnData);
	}

	/**
	 * Desc : 상품 리스트 조회
	 * @Method Name : getStoreOrdInfo
	 * @param SearchWebOrder
	 * @return 조회
	 */
	@RequestMapping("/edi/weborder/NEDMWEB0020tedProdOrdSelect.json")
	public ModelAndView getStoreOrdInfo(@RequestBody NEDMWEB0020VO vo) throws Throwable
	{
		return AjaxJsonModelHelper.create(nEDMWEB0020Service.selectProdOrdList(vo));
	}

	/**
	 * Desc : 점포 리스트 조회
	 * @Method Name : getStoreOrdInfo
	 * @param SearchWebOrder
	 * @return 조회
	 */
	@RequestMapping("/edi/weborder/NEDMWEB0020tedProdOrdDetailSelect.json")
	public ModelAndView getStoreOrdDetInfo(@RequestBody NEDMWEB0020VO vo) throws Throwable
	{
		return AjaxJsonModelHelper.create(nEDMWEB0020Service.selectProdOrdDetList(vo));
	}

	/**
	 * Desc : 상품 수량 저장 및 수정
	 * @Method Name : getProdOrdTempSave
	 * @param TedOrdProcess001VO
	 * @param HttpServletRequest
	 * @return message
	 */
	@RequestMapping("/edi/weborder/NEDMWEB0020tedProdOrdTempSave.json")
	public ModelAndView getProdOrdTempSave(@RequestBody NEDMWEB0020VO vo, HttpServletRequest request) throws Exception
	{
		String message;
		String result;

		message = messageSource.getMessage("msg.common.fail.insert", null, Locale.getDefault());

		try {
			result = nEDMWEB0020Service.insertProdOrdTemp(vo, request);
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
	@RequestMapping("/edi/weborder/NEDMWEB0020tedProdOrdDelete.json")
	public ModelAndView tedProdOrdDelete(@RequestBody NEDMWEB0020VO vo) throws Exception
	{
		String message;
		String result;

		message = messageSource.getMessage("msg.common.fail.insert", null, Locale.getDefault());

		try {
			result = nEDMWEB0020Service.deleteProdOrdInfo(vo);
			if(result.equals("suc")) return AjaxJsonModelHelper.create("");
			else return AjaxJsonModelHelper.create(message);
		} catch (Exception e) {
			return AjaxJsonModelHelper.create(message);
		}

	}
}
