package com.lottemart.epc.edi.zip.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lcn.module.common.views.AjaxJsonModelHelper;



import com.lottemart.epc.edi.comm.model.Constants;
import com.lottemart.epc.edi.comm.model.EdiCommonCode;
import com.lottemart.epc.edi.comm.model.SearchParam;


import com.lottemart.epc.edi.zip.service.PEDPZIP0001Service;



@Controller
public class PEDPZIP0001Controller {

	@Autowired
	private PEDPZIP0001Service pedpzip0001Service;

	/**
	 * 우편번호 조회 페이지 이동
	 * Desc :
	 * @Method Name : list
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/edi/zip/PEDPZIP0001List.do")
	public String list(HttpServletRequest request) throws Exception {
		return "/edi/zip/PEDPZIP0001";
	}

	@RequestMapping("/edi/zip/PEDPZIP0001ListNew.do")
	public String listNew(ModelMap model ,SearchParam searchParam,HttpServletRequest request) throws Exception {



		//List comList= new ArrayList();
//		comList =  pedpzip0001Service.selectCityList(searchParam);
//		String compare="";
//		if(comList.size()==0 || comList == null){
//			compare="none";
//		}
//		model.addAttribute("com",compare);



		//model.addAttribute("teamList", 	  pedpzip0001Service.selectCityList(searchParam));
		//Map<String, String> hmap = new HashMap();
		//model.addAttribute("hparamMap",hmap);

		return "/edi/zip/PEDPZIP0002";
	}



	/**
	 * 우편번호 조회
	 * Desc :
	 * @Method Name : list
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/edi/zip/PEDPZIP0001Select.do")
	public String selectList(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {


		model.addAttribute("zipList",pedpzip0001Service.selectZipCodeList(map));
		model.addAttribute("paramMap",map);

		return "/edi/zip/PEDPZIP0001";
	}


	@RequestMapping("/edi/zip/PEDPZIP0001SelectNew.do")
	public String selectListNew(@RequestParam Map<String,Object> map,SearchParam searchParam, ModelMap model,HttpServletRequest request) throws Exception {


	//	Map<String, Object> paramMap = new HashMap<String, Object>();


	//	model.addAttribute("teamList", 	  pedpzip0001Service.selectCityList(searchParam));
	//	Map<String, String> hmap = new HashMap();
	//	model.addAttribute("hparamMap",hmap);

//		paramMap.put("teamGroupCode", "서울");
//		System.out.println("leeeedb=======>"+ map.get("teamGroupCode"));
//		System.out.println("leeeedb=======>"+ map.get("l1GroupCode"));
//		System.out.println("leeeedb=======>"+ map.get("dong_nm"));

//		System.out.println("leeeedb=======>"+ map.get("cityNm"));
//		System.out.println("leeeedb=======>"+ map.get("guNm"));
//		System.out.println("leeeedb=======>"+ map.get("streetNm"));

		model.addAttribute("zipList",pedpzip0001Service.selectstreetCodeList(map));
		model.addAttribute("paramMap",map);
		return "/edi/zip/PEDPZIP0002";
	}




	//입점상담관리 대분류 AJAX
	@RequestMapping("/edi/zip/PEDPZIP0001SelectCode")
    public ModelAndView getCategoryL1List(SearchParam searchParam,
    		HttpServletRequest request) throws UnsupportedEncodingException {

		// Encode Type을 UTF-8로 변환한다.
		request.setCharacterEncoding("UTF-8");
	//	response.setContentType("text/html;charset=UTF-8");


		if(StringUtils.isEmpty(searchParam.getGroupCode())) {
			searchParam.setGroupCode(Constants.DEFAULT_TEAM_CD_CON);
		}
		//List<EdiCommonCode> resultL1List = pedpzip0001Service.getSelectedGuNmList(searchParam);
		//return AjaxJsonModelHelper.create(resultL1List);

		List<EdiCommonCode> resultGuNmList = pedpzip0001Service.getSelectedGuNmList(searchParam);
		return AjaxJsonModelHelper.create(resultGuNmList);

	}



}
