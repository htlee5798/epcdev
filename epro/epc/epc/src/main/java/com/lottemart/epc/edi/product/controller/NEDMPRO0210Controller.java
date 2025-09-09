package com.lottemart.epc.edi.product.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lcn.module.common.util.DateUtil;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.product.service.NEDMPRO0210Service;
/**
 * 상품정보 - > 물류바코드 관리 - > 물류바코드 현황   Controller
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
public class NEDMPRO0210Controller extends BaseController {

	@Autowired
	private NEDMPRO0210Service NEDMPRO0210Service;


	@Resource(name = "configurationService")
	private ConfigurationService config;

	/**
	 * 물류바코드 관리 - > 물류바코드 현황
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/edi/product/NEDMPRO0210")
    public String barcodeList( Model model, HttpServletRequest request) {

		Map<String, String> map = new HashMap();

		String endDate   = DateUtil.getToday();
		String startDate = DateUtil.addDay(endDate,-1);

		map.put("srchStartDate",DateUtil.formatDate(startDate,"-"));
		map.put("srchEndDate",DateUtil.formatDate(endDate,"-"));

		model.addAttribute("searchParam",map);

		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		model.addAttribute("epcLoginVO", epcLoginVO);

		return "edi/product/NEDMPRO0210";
	}



	/**
	 * 물류바코드 관리 - > 물류바코드 현황 test
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/edi/product/NEDMPRO0210", method=RequestMethod.POST)
    public String searchBarcodeList(@RequestParam Map<String,Object> map, ModelMap model ,HttpServletRequest request) {


		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		//------------------------------------------------------------------
		model.addAttribute("epcLoginVO", epcLoginVO);
		model.addAttribute("searchParam",map);
		model.addAttribute("barcodeList", NEDMPRO0210Service.selectBarcodeList(map));


		List barcodeList = new ArrayList();
		barcodeList.add(getTestData());
		model.addAttribute("barcodeList", barcodeList);
		return "edi/product/NEDMPRO0210";
	}

	public Map<String,Object> getTestData(){
		Map<String,Object> data = new HashMap<String , Object>();
		String SORTER_FG = "1"; 		// 0 1
		String USE_FG ="1";			//1
		String W_USE_FG ="0";		// '' 0 1
		String LOGI_CFM_FG ="01";		//00 01 02 03 04 05 09
		data.put("REG_DT", "20151216");
		data.put("SRCMK_CD", "102125215");
		data.put("PROD_NM", "테스트 상품");
		data.put("LOGI_BCD", "125111");
		data.put("WIDTH", "10");
		data.put("LENGTH", "13");
		data.put("HEIGHT", "26");
		data.put("WG", "T");
		data.put("SORTER_FG", SORTER_FG);
		data.put("LOGI_CFM_FG", LOGI_CFM_FG);
		data.put("W_USE_FG", W_USE_FG);
		data.put("USE_FG", USE_FG);
		return data;
	}


	/**
	 * 물류바코드 관리 - > 물류바코드 현황 조회
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/edi/product/NEDMPRO0210Select.json", method=RequestMethod.POST)
    public @ResponseBody Map<String, Object> NEDMPRO0210Select(@RequestBody Map<String,Object> map, ModelMap model ,HttpServletRequest request) {


		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		map.put("venCds", epcLoginVO.getVendorId());
		//------------------------------------------------------------------
		Map<String, Object>	resultMap	=	new HashMap<String, Object>();
		List barcodeList = NEDMPRO0210Service.selectBarcodeList(map);
		resultMap.put("barcodeList", barcodeList);


		return resultMap;
	}




}
