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
import com.lottemart.epc.edi.weborder.model.NEDMWEB0010VO;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.model.TedOrdProcess001VO;
import com.lottemart.epc.edi.weborder.service.NEDMWEB0010Service;

import org.springframework.context.MessageSource;

/**
 * @Class Name : NEDMWEB0010Controller
 * @Description : 점포별 발주등록 Controller Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      		수정자          		수정내용
 *  -------    --------    ---------------------------
 * 2015.12.03	O YEUN KWON	  최초생성
 *
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */


@Controller
public class NEDMWEB0010Controller extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(NEDMWEB0010Controller.class);


	private NEDMWEB0010Service nEDMWEB0010Service;

	@Autowired
	public void setnEDMWEB0010Service(NEDMWEB0010Service nEDMWEB0010Service) {
		this.nEDMWEB0010Service = nEDMWEB0010Service;
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
	@RequestMapping(value="/edi/weborder/NEDMWEB0010.do")
    public String storeWebOrderList(NEDMWEB0010VO nEDMWEB0010VO, HttpServletRequest request, Model model) {
		SimpleDateFormat format = new SimpleDateFormat("HHmm", Locale.KOREA);

		int nowDt = Integer.parseInt(format.format(new Date()));
		String vendorWebOrdFrDt = ConfigUtils.getString("edi.weboder.vendor.fr");
		String vendorWebOrdToDt = ConfigUtils.getString("edi.weboder.vendor.to");

		if( ( nowDt < Integer.parseInt(vendorWebOrdFrDt)) || ( nowDt > Integer.parseInt(vendorWebOrdToDt)) ){
			return "edi/weborder/NEDMWEB0098";
		}

		nEDMWEB0010VO.setVendorWebOrdFrDt(vendorWebOrdFrDt);
		nEDMWEB0010VO.setVendorWebOrdToDt(vendorWebOrdToDt);

		if(StringUtils.isEmpty(nEDMWEB0010VO.getWorkGb())){
			nEDMWEB0010VO.setWorkGb("1");
		}
		if(StringUtils.isEmpty(nEDMWEB0010VO.getProdCd())){
			nEDMWEB0010VO.setProdCd("");
		}
		if(StringUtils.isEmpty(nEDMWEB0010VO.getProdNm())){
			nEDMWEB0010VO.setProdNm("");
		}

		model.addAttribute("paramMap", nEDMWEB0010VO);

		return "edi/weborder/NEDMWEB0010";
	}

	/**
	 * Desc : 협력업체 코드별 점포 정보 조회
	 * @Method Name : getStoreOrdInfo
	 * @param SearchWebOrder
	 * @return 조회 값
	 */
	@RequestMapping("/edi/weborder/NEDMWEB0010vendorExceptionSelect0010.json")
	public ModelAndView getVndorException(@RequestBody NEDMWEB0010VO nEDMWEB0010VO) throws Exception
	{
		Map<String, Object> rtnData = new HashMap<String, Object>();
		rtnData.put("state", "-1");

		try{
			rtnData = nEDMWEB0010Service.selectVendorException(nEDMWEB0010VO);

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
	@RequestMapping("/edi/weborder/NEDMWEB0010tedStoreOrdSelect.json")
	public ModelAndView getStoreOrdInfo(@RequestBody NEDMWEB0010VO nEDMWEB0010VO) throws Exception
	{
		Map<String, Object> rtnData = new HashMap<String, Object>();
		rtnData.put("state", "-1");

		try{
			rtnData = nEDMWEB0010Service.selectStoreOrdList(nEDMWEB0010VO);
			//rtnData = nEDMWEB0010Service.selectStoreOrdListFixedStr(vo);		// paging없이 사용할경우

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
	@RequestMapping("/edi/weborder/NEDMWEB0010tedStoreOrdDetailSelect.json")
	public ModelAndView getStoreOrdDetInfo(@RequestBody NEDMWEB0010VO nEDMWEB0010VO) throws Throwable
	{
		return AjaxJsonModelHelper.create(nEDMWEB0010Service.selectStoreOrdDetList(nEDMWEB0010VO));
	}

	/**
	 * Desc : 점포별 상품 수량 저장 및 수정
	 * @Method Name : getStoreOrdTempSave
	 * @param TedOrdProcess001VO
	 * @param HttpServletRequest
	 * @return message
	 */
	@RequestMapping("/edi/weborder/NEDMWEB0010tedStoreOrdTempSave.json")
	public ModelAndView getStoreOrdTempSave(@RequestBody NEDMWEB0010VO vo, HttpServletRequest request) throws Exception
	{
		String message;
		String result;

		message = messageSource.getMessage("msg.common.fail.insert", null, Locale.getDefault());

		try {
			result = nEDMWEB0010Service.insertStoreOrdTemp(vo, request);
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
	@RequestMapping("/edi/weborder/NEDMWEB0010tedStoreOrdDelete.json")
	public ModelAndView getStoreOrdDelete(@RequestBody NEDMWEB0010VO vo) throws Exception
	{
		String message;
		String result;

		message = messageSource.getMessage("msg.common.fail.delete", null, Locale.getDefault());

		try {
			result = nEDMWEB0010Service.deleteStoreOrdInfo(vo);
			if(result.equals("suc")) return AjaxJsonModelHelper.create("");
			else return AjaxJsonModelHelper.create(message);
		} catch (Exception e) {
			return AjaxJsonModelHelper.create(message);
		}

	}

}
