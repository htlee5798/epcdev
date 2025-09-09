package com.lottemart.epc.edi.weborder.controller;





import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lcn.module.common.views.AjaxJsonModelHelper;
import lcn.module.framework.property.ConfigurationService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.weborder.model.NEDMWEB0210VO;
import com.lottemart.epc.edi.weborder.service.NEDMWEB0210Service;


/**
 * @Class Name : NEDMWEB0210Controller
 * @Description : 발주승인관리 Controller Class
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
public class NEDMWEB0210Controller extends BaseController{

	private NEDMWEB0210Service nEDMWEB0210Service;

	private static final Logger logger = LoggerFactory.getLogger(NEDMWEB0210Controller.class);

	@Autowired
	public void setnEDMPRO2010Service(NEDMWEB0210Service nEDMWEB0210Service) {
		this.nEDMWEB0210Service = nEDMWEB0210Service;
	}

	@Resource(name = "configurationService")
	private ConfigurationService config;


	/**
	 * Desc : 점포별 발주확정 init
	 * @Method Name : VenOrdListView
	 * @param SearchWebOrder
	 * @param HttpServletRequest
	 * @param Model
	 * @return view 페이지
	 */
	@RequestMapping(value="/edi/weborder/NEDMWEB0210.do")
    public String VenOrdListView(NEDMWEB0210VO searchParam, HttpServletRequest request, Model model) {


		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
		model.addAttribute("epcLoginVO", epcLoginVO);


		if(StringUtils.isEmpty(searchParam.getOrderSendfg())) searchParam.setOrderSendfg("1");

		model.addAttribute("paramMap",searchParam);

		return "edi/weborder/NEDMWEB0210";
	}



	/**
	 * Desc : 협력사별 발주목록 조회
	 * @Method Name : VenOrdList
	 * @param SearchProduct
	 * @param HttpServletRequest
	 * @param Model
	 * @return AjaxJsonModelHelper(Map<String,Object>)
	 */
	@RequestMapping(value = "/edi/weborder/NEDMWEB0210Select.json")
    public ModelAndView VenOrdList(@RequestBody NEDMWEB0210VO vo, HttpServletRequest request)throws Exception{

		Map<String,Object> result = new HashMap<String,Object>();
		result.put("state", "-1");
		try{

			result = nEDMWEB0210Service.selectVenOrdInfo(vo,request);
			result.put("state", 	"0");
			result.put("message", 	"SUCCESS");
		}
		catch (Exception e) {
			// TODO: handle exception
			result.put("message",e.getMessage());
		}

		return AjaxJsonModelHelper.create(result);
	}




	/**
	 * Desc : 점포별 발주 상세 펼침 조회
	 * @Method Name : StrCdOrdList
	 * @param SearchWebOrder
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/edi/weborder/NEDMWEB0210StrCdSelect.json")
    public ModelAndView StrCdOrdList(@RequestBody NEDMWEB0210VO vo)throws Exception{

		Map<String,Object> result = nEDMWEB0210Service.selectStrCdList(vo);

		return AjaxJsonModelHelper.create(result);
	}

	/**
	 * Desc : 점포별 발주등록
	 * @Method Name : tempNewProductList
	 * @param SearchProduct
	 * @param HttpServletRequest
	 * @param Model
	 * @return view 페이지
	 */
	@RequestMapping(value="/edi/weborder/NEDMWEB0210Popup.do")
    public String VenOrdListViewPopup(NEDMWEB0210VO vo, Model model) {

		model.addAttribute("ordReqNo",  vo.getOrdReqNo());
		return "edi/weborder/NEDMWEB0210Popup";
	}


	/**
	 * Desc : 점포별 발주상품 상세 조회 (팝업 화면)
	 * @Method Name : VenOrdProdList
	 * @param SearchWebOrder
	 * @param Model
	 * @return AjaxJsonModelHelper(Map<String,Object>)
	 */
	@RequestMapping(value = "/edi/weborder/NEDMWEB0210ProdSelect.json")
    public ModelAndView VenOrdProdList(@RequestBody NEDMWEB0210VO vo)throws Exception{
		Map<String,Object> result =  new HashMap<String, Object>();
		result.put("state", "-1");
		try{
			result = nEDMWEB0210Service.selectVenOrdProdInfo(vo);
			result.put("state",   "0");
			result.put("message", "success");
		}
		catch (Exception e) {
			// TODO: handle exception

			result.put("message",e.getMessage());
		}

		return AjaxJsonModelHelper.create(result);
	}



	/**
	 * 발주승인 목록 삭제처리 (점포별 사용)
	 * @Method Name : getStoreOrdDelete
	 * @param TedPoOrdMstVO
	 * @param HttpServletRequest
	 * @return AjaxJsonModelHelper(HashMap<String, String>)
	 */
	@RequestMapping("/edi/weborder/NEDMWEB0210Delete.json")
	public ModelAndView getStoreOrdDelete(@RequestBody NEDMWEB0210VO vo, HttpServletRequest request) throws Throwable
	{
		Map<String,String>  rtnData	    = new HashMap<String,String>();
		rtnData.put("state", "-1");
		try{

			/**발주 삭제 구분 1:마스터(점포별), 2:디테일(점포/상품별) */
			vo.setUpdateState("1");


			int cnt = nEDMWEB0210Service.updateStrCd(vo,request);
			if(cnt <= 0 )
			{
				rtnData.put("state",   "1");
				rtnData.put("message", "0 row Update!");
			}
			else
			{
				rtnData.put("state",   "0");
				rtnData.put("message", "SUCCESS");
			}
		}catch(Exception e){
			rtnData.put("message",e.getMessage());
			logger.error(e.getMessage(), e);
		}
		return AjaxJsonModelHelper.create(rtnData);

	}


	/**
	 * 발주승인 목록 삭제처리 (점포별, 상품별 사용)
	 * @Method Name : getStoreOrdProdDelete
	 * @param TedPoOrdMstVO
	 * @param HttpServletRequest
	 * @return AjaxJsonModelHelper(HashMap<String, String>)
	 */
	@RequestMapping("/edi/weborder/PEDMWEB0210ProdDelete.json")
	public ModelAndView getStoreOrdProdDelete(@RequestBody NEDMWEB0210VO vo, HttpServletRequest request) throws Throwable
	{
		Map<String,String>  rtnData	    = new HashMap<String,String>();
		rtnData.put("state", "-1");
		try{

			nEDMWEB0210Service.updateStrCdProd(vo,request);

			rtnData.put("state",   "0");
			rtnData.put("message", "SUCCESS");

		}catch(Exception e){
			rtnData.put("message",e.getMessage());
			logger.error(e.getMessage(), e);
		}
		return AjaxJsonModelHelper.create(rtnData);

	}



	/**
	 * 상품별 발주량 수정 저장
	 * @Method Name : getStoreOrdPrdDelete
	 * @param TedPoOrdMstVO
	 * @param HttpServletRequest
	 * @return AjaxJsonModelHelper(HashMap<String, String>)
	 */
	@RequestMapping("/edi/weborder/NEDMWEB0210ProdDetUpdate.json")
	public ModelAndView getStoreOrdPrdDelete(@RequestBody NEDMWEB0210VO vo, HttpServletRequest request) throws Throwable
	{
		Map<String,Object> result = new HashMap();
		result.put("state"  ,"-1");
		try{

		    nEDMWEB0210Service.updateStCdProd(vo,request);

			result.put("state"  ,"0");
			result.put("message","SUCCESS");



		}catch(Exception e){

			result.put("message",e.getMessage());
			logger.error(e.getMessage(), e);
		}
		return AjaxJsonModelHelper.create(result);
	}




	/**
	 * TODAY 협력사코드별  반품승인 등록 처리(MARTNIS 전송)
	 * @Method Name : sendReturnProdData
	 * @param SearchWebOrder
	 * @return AjaxJsonModelHelper(HashMap<String, String>)
	 */
	@RequestMapping("edi/weborder/NEDMWEB0210SendProd.json")
	public ModelAndView sendReturnProdData(@RequestBody NEDMWEB0210VO vo, HttpServletRequest request) throws Exception
	{
		HashMap<String, String> rtnData = new HashMap<String, String>();
		rtnData.put("state","-1");
		try
		{
		    rtnData = nEDMWEB0210Service.insertSendProdData(vo, request);

		}catch (Exception e) {
			rtnData.put("message",e.getMessage());
			logger.error(e.getMessage(), e);
			// TODO: handle exception
		}
		return AjaxJsonModelHelper.create(rtnData);
	}


//	public void updateStCdProd(TedOrdProcess010VO vo,HttpServletRequest request) throws Exception{




	/**
	 * Desc : 점포별 발주등록
	 * @Method Name : tempNewProductList
	 * @param SearchProduct
	 * @param HttpServletRequest
	 * @param Model
	 * @return view 페이지
	 */
/*	@RequestMapping(value = "/edi/weborder/PEDMWEB0010Select.do", method = RequestMethod.POST)
    public String VenOrdList(@RequestParam Map<String,Object> map, HttpServletRequest request, Model model)throws Exception{

		model.addAttribute("VenList", nEDMWEB0210Service.selectVenOrdInfo(map));
		model.addAttribute("VenListSum", nEDMWEB0210Service.selectVenOrdSumInfo(map));


		return "edi/weborder/PEDMWEB0010";
	}
	*/






}
