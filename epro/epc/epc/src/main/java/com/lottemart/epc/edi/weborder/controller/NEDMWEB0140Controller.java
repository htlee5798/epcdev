package com.lottemart.epc.edi.weborder.controller;




import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lcn.module.common.util.DateUtil;
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

import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.weborder.model.NEDMWEB0140VO;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.service.NEDMWEB0140Service;


/**
 * 웹발주 - > 반품등록  - > 반품전체 현황   Controller
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
public class NEDMWEB0140Controller extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(NEDMWEB0140Controller.class);

	@Autowired
	private NEDMWEB0140Service NEDMWEB0140Service;

		/**
		 * 상품별 반품등록  - > 반품등록 전체 현황   첫페이지
		 * @param SearchWebOrder
		 * @param model
		 * @param request
		 * @return
		 */
		@RequestMapping(value="/edi/weborder/NEDMWEB0140.do")
	    public String venderWebOrderRtnInfo(SearchWebOrder searchParam, HttpServletRequest request, Model model) {


			if(StringUtils.isEmpty(searchParam.getProdCd())){
				searchParam.setProdCd("");
			}
			if(StringUtils.isEmpty(searchParam.getRegStsfg())){
				searchParam.setRegStsfg("1");
			}

			String toDay = DateUtil.getToday("yyyy-MM-dd");

			searchParam.setRrlDy(toDay);


			model.addAttribute("paramMap",searchParam);

			return "edi/weborder/NEDMWEB0140";
		}




		/**
		 * 상품별 반품등록  - > 반품등록 전체 현황
		 * @param NEDMWEB0140VO
		 * @param model
		 * @param request
		 * @return
		 */
		@RequestMapping(value = "/edi/weborder/NEDMWEB0140Select.do")
	    public ModelAndView venderWebOrderRtnList(@RequestBody NEDMWEB0140VO vo,HttpServletRequest request)throws Exception{
			Map<String, Object> rtnData = new HashMap<String, Object>();
			rtnData.put("state","-1");
			try{

				rtnData= NEDMWEB0140Service.selectDayRtnList(vo, request);

			}catch (Exception e) {

				rtnData.put("message",e.getMessage());
				logger.error(e.getMessage(), e);
				// TODO: handle exception
			}

			return AjaxJsonModelHelper.create(rtnData);
		}
}
