package com.lottemart.epc.edi.weborder.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lcn.module.framework.property.ConfigurationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.weborder.model.NEDMWEB0120VO;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.service.NEDMWEB0120Service;


/**
 * 웹발주 - > 발주등록  - > 점포별 발주등록   Controller
 *
 * @author SUN GIL CHOI
 * @since 2015.11.04
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ---------------------------
 *   2015.11.04  	SUN GIL CHOI   최초 생성
 *
 * </pre>
 */


@Controller
public class NEDMWEB0120Controller extends BaseController{

	@Autowired
	private NEDMWEB0120Service NEDMWEB0120Service;

	@Resource(name = "configurationService")
	private ConfigurationService config;

	/**
	 * 화면초기화
	 * @param SearchWebOrder
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/edi/weborder/NEDMWEB0120")
    public String storeWebOrderList(SearchWebOrder searchParam, HttpServletRequest request, Model model) throws Exception {

		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		SimpleDateFormat format = new SimpleDateFormat("HHmm", Locale.KOREA);
		int nowDt = Integer.parseInt(format.format(new Date()));

		String vendorWebOrdFrDt = ConfigUtils.getString("edi.weboder.vendor.rtn");
		String vendorWebOrdToDt = ConfigUtils.getString("edi.weboder.vendor.to");

		model.addAttribute("epcLoginVO", 		epcLoginVO);

		if ((nowDt < Integer.parseInt(vendorWebOrdFrDt)) || (nowDt > Integer.parseInt(vendorWebOrdToDt))) {
			//return "edi/weborder/PEDMWEB0099";
			return "edi/weborder/NEDMWEB0111";
		}

		return "edi/weborder/NEDMWEB0120";
	}

	/**
	 * 조회
	 * @param SearchWebOrder
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/edi/weborder/NEDMWEB0120selectDayRtnProdList.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> selectDayRtnProdList(ModelMap model,@RequestBody NEDMWEB0120VO vo, HttpServletRequest request) throws Exception {
		Map<String, Object>	resultMap	=	new HashMap<String, Object>();

		List<NEDMWEB0120VO> resultList  = NEDMWEB0120Service.NEDMWEB0120selectDayRtnProdList(vo);
		resultMap.put("result", resultList);

		return resultMap;
	}

	/**
	 * 저장
	 * @param NEDMWEB0120VO
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/edi/weborder/NEDMWEB0120updateReturnProdData.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> saveReturnProdData(ModelMap model,@RequestBody NEDMWEB0120VO vo, HttpServletRequest request) throws Exception {
		Map<String, Object>	resultMap	=	new HashMap<String, Object>();

		String retString = NEDMWEB0120Service.updateReturnProdData(vo);
		resultMap.put("msgCd", retString);

		return resultMap;
	}

	/**
	 * 삭제
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/weborder/NEDMWEB0120deleteReturnProdData.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> NEDMWEB0120deleteReturnProdData(@RequestBody NEDMWEB0120VO vo, HttpServletRequest request) throws Exception {
		Map<String, Object>	resultMap	=	new HashMap<String, Object>();

		String retString = NEDMWEB0120Service.deleteReturnProdData(vo);
		resultMap.put("msgCd", retString);

		return resultMap;
	}

	/**
	 * 반품요청
	 * @param NEDMWEB0120VO
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/edi/weborder/NEDMWEB0120sendReturn.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> NEDMWEB0120sendReturn(@RequestBody NEDMWEB0120VO vo, HttpServletRequest request) throws Exception {
		Map<String, Object>	resultMap	=	new HashMap<String, Object>();

		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		String adminId = epcLoginVO.getAdminId();

		String retString = NEDMWEB0120Service.sendReturn(vo,adminId);
		resultMap.put("msgCd", retString);

		return resultMap;
	}

	/**
	 * 삭제
	 * @param NEDMWEB0120VO
	 * @param request
	 * @return
	 */
	/*@RequestMapping("/edi/weborder/NEDMWEB0120deleteReturnProdData.json")
	public ModelAndView NEDMWEB0120deleteReturnProdData(ModelMap model,@RequestBody NEDMWEB0120VO vo, HttpServletRequest request) throws Throwable, RuntimeException
	{
		HashMap<String, String> rtnData = new HashMap<String, String>();
		rtnData.put("state", "0");
		try{
			NEDMWEB0120Service.deleteReturnProdData(vo);
		}
		catch (Exception e) {
			rtnData.put("state", "1");
			rtnData.put("message",e.getMessage());
			logger.error(e.getMessage(), e);
			// TODO: handle exception
		}


		return AjaxJsonModelHelper.create(rtnData);
	}*/

}
