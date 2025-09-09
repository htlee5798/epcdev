/**
 * @prjectName  : 롯데정보통신 프로젝트   
 * @since       : 2011. 9. 7. 오전 10:43:08
 * @author      : yhs8462, 윤해성(yhs8462@lotte.net)
 * @Copyright(c) 2000 ~ 2011 롯데정보통신(주)
 *  All rights reserved.
 */
package com.lottemart.epc.common.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lcn.module.common.util.CommUtil;
import lcn.module.common.util.HashBox;
import lcn.module.common.views.AjaxJsonModelHelper;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lottemart.epc.common.model.CommonCodeModel;
import com.lottemart.epc.common.model.OptionTagVO;
import com.lottemart.epc.common.service.CommonCodeHelperService;
import com.lottemart.epc.common.tag.SelectOptionTagUtil;
import com.lottemart.epc.common.util.SecureUtil;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;

/**
 * @Class Name : 
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 9. 7. 오전 10:43:08 yhs8462, 윤해성(yhs8462@lotte.net)
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Controller
public class CommonCodeHelperController
{
	@Autowired
	CommonCodeHelperService commonCodeHelperService;
	
	private static final Logger logger = LoggerFactory.getLogger(CommonCodeHelperController.class);
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/CommonCodeHelperController/generateOptionTag.do")
	public ModelAndView generateOptionTag(HttpServletRequest req) throws Throwable
	{
		String selectedCode = req.getParameter("selectedCode");
		String selectViewCode = req.getParameter("selectViewCode");
		logger.info("selectedCode == " + selectedCode);
		logger.info("selectViewCode == " + selectViewCode);
		List<OptionTagVO> commonCodeList = (List<OptionTagVO>)commonCodeHelperService.selectCommonCodeList(selectedCode);
		
		List<OptionTagVO> list = new ArrayList();
		
		Iterator<OptionTagVO> itor = commonCodeList.iterator();
		
		while(itor.hasNext())
		{
			OptionTagVO map = (OptionTagVO) itor.next();
			list.add( new OptionTagVO((String)map.getName(),  (String)map.getCode()));			
		}
		
		String html = SelectOptionTagUtil.generateOptionsTag(list, selectViewCode);
		
		return AjaxJsonModelHelper.create(html);
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping("/CommonCodeHelperController/getCommonCodeColumn.do")
	public ModelAndView getCommonCodeColumn(HttpServletRequest req) throws Throwable
	{
		CommonCodeModel param = new CommonCodeModel();
		param.setMajorCD(req.getParameter("majorCD"));
		param.setMinorCD(req.getParameter("minorCD"));
		param.setColumnNM(SecureUtil.sqlValid(req.getParameter("columnNM")));
		
		String returlValue = commonCodeHelperService.getCommonCodeColumn(param);
		
		Map result = CommUtil.createMap("RETURN_VALUE", returlValue);
		
		return AjaxJsonModelHelper.create(result);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/CommonCodeHelperController/storeSelectList.do")
	public ModelAndView storeSelectList(@RequestBody CommonCodeModel vo,  HttpServletRequest req) throws Throwable
	{
		String selectedCode = vo.getMajorCD();
		String selectViewCode = req.getParameter("selectViewCode");
		logger.info("selectedCode == " + selectedCode);
		logger.info("selectViewCode == " + selectViewCode);
		List<OptionTagVO> commonCodeList = (List<OptionTagVO>)commonCodeHelperService.storeSelectList(selectedCode);
		
		List<OptionTagVO> list = new ArrayList();
		
		Iterator<OptionTagVO> itor = commonCodeList.iterator();
		
		while(itor.hasNext())
		{
			OptionTagVO map = (OptionTagVO) itor.next();
			list.add( new OptionTagVO((String)map.getName(),  (String)map.getCode()));			
		}
		
		String html = SelectOptionTagUtil.generateOptionsTag(list, selectViewCode);
		
		return AjaxJsonModelHelper.create(html);
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/CommonCodeHelperController/excelUploadInfoList.do")
	public ModelAndView excelUploadInfoList(@RequestBody CommonCodeModel vo,  HttpServletRequest req) throws Throwable
	{
		String selectedCode = vo.getMajorCD();
		String selectViewCode = req.getParameter("selectViewCode");
		logger.info("selectedCode == " + selectedCode);
		logger.info("selectViewCode == " + selectViewCode);
		List<OptionTagVO> commonCodeList = (List<OptionTagVO>)commonCodeHelperService.excelUploadInfoList(selectedCode);
		
		List<OptionTagVO> list = new ArrayList();
		
		Iterator<OptionTagVO> itor = commonCodeList.iterator();
		
		while(itor.hasNext())
		{
			OptionTagVO map = (OptionTagVO) itor.next();
			list.add( new OptionTagVO((String)map.getName(),  (String)map.getCode()));			
		}
		
		String html = SelectOptionTagUtil.generateOptionsTag(list, selectViewCode);
		
		return AjaxJsonModelHelper.create(html);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/CommonCodeHelperController/excelRtnUploadInfoList.do")
	public ModelAndView excelRtnUploadInfoList(@RequestBody CommonCodeModel vo,  HttpServletRequest req) throws Throwable
	{
		String selectedCode = vo.getMajorCD();
		String selectViewCode = req.getParameter("selectViewCode");
		logger.info("selectedCode == " + selectedCode);
		logger.info("selectViewCode == " + selectViewCode);
		List<OptionTagVO> commonCodeList = (List<OptionTagVO>)commonCodeHelperService.excelRtnUploadInfoList(selectedCode);
		
		List<OptionTagVO> list = new ArrayList();
		
		Iterator<OptionTagVO> itor = commonCodeList.iterator();
		
		while(itor.hasNext())
		{
			OptionTagVO map = (OptionTagVO) itor.next();
			list.add( new OptionTagVO((String)map.getName(),  (String)map.getCode()));			
		}
		
		String html = SelectOptionTagUtil.generateOptionsTag(list, selectViewCode);
		
		return AjaxJsonModelHelper.create(html);
	}
	
	
	
	//@RequestMapping("edi/weborder/PEDMWEB0005SendProd.do")
	//public ModelAndView sendReturnProdData(@RequestBody SearchWebOrder vo, HttpServletRequest request) throws Exception
	
	/**
	 * AS-IS
	 * @param vo
	 * @param req
	 * @return
	 * @throws Throwable
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/CommonCodeHelperController/venStoreSelectList.do")
	public ModelAndView venStoreSelectList(@RequestBody HashBox vo, HttpServletRequest req) throws Throwable
	{			
		
		List<OptionTagVO> commonCodeList = (List<OptionTagVO>)commonCodeHelperService.venStoreSelectList(vo);
		return AjaxJsonModelHelper.create(commonCodeList);
	}
	
	
	/**
	 * AS-IS
	 * @param vo
	 * @param req
	 * @return
	 * @throws Throwable
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/CommonCodeHelperController/venOrdStoreSelectList.do")
	public ModelAndView venOrdStoreSelectList(@RequestBody HashBox vo, HttpServletRequest req) throws Throwable
	{			
		
		List<OptionTagVO> commonCodeList = (List<OptionTagVO>)commonCodeHelperService.venOrdStoreSelectList(vo);
		return AjaxJsonModelHelper.create(commonCodeList);
	}
}
