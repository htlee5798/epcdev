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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.edi.comm.controller.BaseController;

import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.model.TedOrdProcess001VO;
import com.lottemart.epc.edi.weborder.service.PEDMWEB0001Service;
import org.springframework.context.MessageSource;

/**
 * @Class Name : PEDMWEB0001Controller
 * @Description : 점포별 발주등록 Controller 클래스
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
public class PEDMWEB0001Controller extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(PEDMWEB0001Controller.class);


	private PEDMWEB0001Service pEDMWEB0001Service;

	@Autowired
	public void setpEDMWEB0001Service(PEDMWEB0001Service pEDMWEB0001Service) {
		this.pEDMWEB0001Service = pEDMWEB0001Service;
	}

	@Autowired
	private MessageSource messageSource;

	/**
	 * Desc : 점포별 발주등록 화면
	 * @Method Name : storeWebOrderList
	 * @param SearchWebOrder
	 * @param HttpServletRequest
	 * @param Model
	 * @return view 페이지
	 */
	@RequestMapping(value="/edi/weborder/PEDMWEB0001")
    public String storeWebOrderList(SearchWebOrder searchParam, HttpServletRequest request, Model model) {
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

		return "edi/weborder/PEDMWEB0001";
	}

	/**
	 * Desc : 협력업체 코드별 점포 정보 조회
	 * @Method Name : getStoreOrdInfo
	 * @param SearchWebOrder
	 * @return 조회 값
	 */
	@RequestMapping("/edi/weborder/vendorExceptionSelect.do")
	public ModelAndView getVndorException(@RequestBody SearchWebOrder vo) throws Exception
	{
		Map<String, Object> rtnData = new HashMap<String, Object>();
		rtnData.put("state", "-1");

		try{
			rtnData = pEDMWEB0001Service.selectVendorException(vo);

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
	 * Desc : 협력업체 코드별 점포 정보 조회
	 * @Method Name : getStoreOrdInfo
	 * @param SearchWebOrder
	 * @return 조회 값
	 */
	@RequestMapping("/edi/weborder/tedStoreOrdSelect.do")
	public ModelAndView getStoreOrdInfo(@RequestBody SearchWebOrder vo) throws Exception
	{
		Map<String, Object> rtnData = new HashMap<String, Object>();
		rtnData.put("state", "-1");

		try{
			rtnData = pEDMWEB0001Service.selectStoreOrdList(vo);
			//rtnData = pEDMWEB0001Service.selectStoreOrdListFixedStr(vo);		// paging없이 사용할경우

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
	 * Desc : 점포별 상품정보 조회
	 * @Method Name : getStoreOrdDetInfo
	 * @param SearchWebOrder
	 * @param Model
	 * @return 조회 값
	 */
	@RequestMapping("/edi/weborder/tedStoreOrdDetailSelect.do")
	public ModelAndView getStoreOrdDetInfo(@RequestBody SearchWebOrder vo) throws Throwable
	{
		return AjaxJsonModelHelper.create(pEDMWEB0001Service.selectStoreOrdDetList(vo));
	}

	/**
	 * Desc : 점포별 상품 수량 저장 및 수정
	 * @Method Name : getStoreOrdTempSave
	 * @param TedOrdProcess001VO
	 * @param HttpServletRequest
	 * @return message
	 */
	@RequestMapping("/edi/weborder/tedStoreOrdTempSave.do")
	public ModelAndView getStoreOrdTempSave(@RequestBody TedOrdProcess001VO vo, HttpServletRequest request) throws Exception
	{
		String message;
		String result;

		message = messageSource.getMessage("msg.common.fail.insert", null, Locale.getDefault());

		try {
			result = pEDMWEB0001Service.insertStoreOrdTemp(vo, request);
			if(result.equals("suc")) return AjaxJsonModelHelper.create("");
			else return AjaxJsonModelHelper.create(message);
		} catch (Exception e) {
			return AjaxJsonModelHelper.create(message);
		}

	}

	/**
	 * Desc : 점포별 상품 정보 삭제
	 * @Method Name : getStoreOrdDelete
	 * @param TedOrdProcess001VO
	 * @param HttpServletRequest
	 * @return message
	 */
	@RequestMapping("/edi/weborder/tedStoreOrdDelete.do")
	public ModelAndView getStoreOrdDelete(@RequestBody TedOrdProcess001VO vo) throws Exception
	{
		String message;
		String result;

		message = messageSource.getMessage("msg.common.fail.delete", null, Locale.getDefault());

		try {
			result = pEDMWEB0001Service.deleteStoreOrdInfo(vo);
			if(result.equals("suc")) return AjaxJsonModelHelper.create("");
			else return AjaxJsonModelHelper.create(message);
		} catch (Exception e) {
			return AjaxJsonModelHelper.create(message);
		}

	}


	/**
	 * Desc : 승인 요청
	 * @Method Name : setOrdApprReq
	 * @param SearchWebOrder
	 * @param HttpServletRequest
	 * @return message
	 */
	@RequestMapping("/edi/weborder/tedOrdApprReq.do")
	public ModelAndView setOrdApprReq(@RequestBody SearchWebOrder vo, HttpServletRequest request) throws Exception
	{
		String message;
		String result;

		message = messageSource.getMessage("msg.common.fail.insert", null, Locale.getDefault());

		try {
			result = pEDMWEB0001Service.upadteApprReqInfo(vo, request);
			if(result.equals("suc")) return AjaxJsonModelHelper.create("");
			else return AjaxJsonModelHelper.create(message);

		} catch (Exception e) {
			return AjaxJsonModelHelper.create(message);





		}

	}
}
