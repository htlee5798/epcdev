package com.lottemart.epc.edi.weborder.controller;

import javax.servlet.http.HttpServletRequest;

import lcn.module.common.util.DateUtil;
import lcn.module.common.views.AjaxJsonModelHelper;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.weborder.model.NEDMWEB0040VO;
import com.lottemart.epc.edi.weborder.service.NEDMWEB0040Service;


/**
 * @Class Name : NEDMWEB0040Controller
 * @Description : 발주전체현황 Controller Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      		수정자          		수정내용
 *  -------    --------    ---------------------------
 * 2015.12.08	O YEUN KWON	  최초생성
 * 
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */


@Controller
public class NEDMWEB0040Controller extends BaseController{

	
	private NEDMWEB0040Service nEDMWEB0040Service;
	
	@Autowired
	public void setnEDMWEB0040Service(NEDMWEB0040Service nEDMWEB0040Service) {
		this.nEDMWEB0040Service = nEDMWEB0040Service;
	}
	
	/**
	 * Desc : 점포별 발주등록 화면
	 * @Method Name : orderTotList
	 * @param NEDMWEB0040VO
	 * @param HttpServletRequest
	 * @param Model
	 * @return view 페이지
	 */
	@RequestMapping(value="/edi/weborder/NEDMWEB0040")
    public String orderTotList(NEDMWEB0040VO vo, HttpServletRequest request, Model model) {
		if(StringUtils.isEmpty(vo.getWorkGb())){
			vo.setWorkGb("1");
		}
		if(StringUtils.isEmpty(vo.getProdCd())){
			vo.setProdCd("");
		}
		if(StringUtils.isEmpty(vo.getRegStsfg())){
			vo.setRegStsfg("1");
		}
		if(StringUtils.isEmpty(vo.getMdModFg())){
			vo.setMdModFg("1");
		}
		
		String toDay = DateUtil.getToday("yyyy-MM-dd");
		vo.setOrdDy(toDay);
		
		model.addAttribute("paramMap",vo);
		
		return "edi/weborder/NEDMWEB0040";
	}
	
	/**
	 * Desc : 발주전체현황 조회
	 * @Method Name : getOrdTotInfo
	 * @param NEDMWEB0040VO
	 * @return 조회 값
	 */
	@RequestMapping("/edi/weborder/NEDMWEB0040tedOrdTotSelect.json")
	public ModelAndView getOrdTotInfo(@RequestBody NEDMWEB0040VO vo) throws Exception
	{
		return AjaxJsonModelHelper.create(nEDMWEB0040Service.selectOrdTotList(vo));
	}
	
}
