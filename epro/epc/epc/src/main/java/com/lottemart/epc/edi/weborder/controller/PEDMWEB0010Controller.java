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
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.model.TedOrdProcess010VO;
import com.lottemart.epc.edi.weborder.model.TedPoOrdMstVO;
import com.lottemart.epc.edi.weborder.service.PEDMWEB0010Service;


/**
 * @Class Name : PEDMWEB0010Controller
 * @Description : 발주확정관리 Controller 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2014. 08. 17. 오후 1:33:50 kks
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */


@Controller
public class PEDMWEB0010Controller extends BaseController{

	private PEDMWEB0010Service pEDMWEB0010Service;

	private static final Logger logger = LoggerFactory.getLogger(PEDMWEB0010Controller.class);

	@Autowired
	public void setpEDMPRO0010Service(PEDMWEB0010Service pEDMWEB0010Service) {
		this.pEDMWEB0010Service = pEDMWEB0010Service;
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
	@RequestMapping(value="/edi/weborder/PEDMWEB0010")
    public String VenOrdListView(SearchWebOrder searchParam, HttpServletRequest request, Model model) {


		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
		model.addAttribute("epcLoginVO", epcLoginVO);


		if(StringUtils.isEmpty(searchParam.getOrderSendfg())) searchParam.setOrderSendfg("1");

		model.addAttribute("paramMap",searchParam);

		return "edi/weborder/PEDMWEB0010";
	}



	/**
	 * Desc : 협력사별 발주목록 조회
	 * @Method Name : VenOrdList
	 * @param SearchProduct
	 * @param HttpServletRequest
	 * @param Model
	 * @return AjaxJsonModelHelper(Map<String,Object>)
	 */
	@RequestMapping(value = "/edi/weborder/PEDMWEB0010Select.do")
    public ModelAndView VenOrdList(@RequestBody SearchWebOrder swo, HttpServletRequest request)throws Exception{

		Map<String,Object> result = new HashMap<String,Object>();
		result.put("state", "-1");
		try{

			result = pEDMWEB0010Service.selectVenOrdInfo(swo,request);
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
	@RequestMapping(value = "/edi/weborder/PEDMWEB0010StrCdSelect.do")
    public ModelAndView StrCdOrdList(@RequestBody SearchWebOrder swo)throws Exception{

		Map<String,Object> result = pEDMWEB0010Service.selectStrCdList(swo);

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
	@RequestMapping(value="/edi/weborder/PEDMWEB0010Popup.do")
    public String VenOrdListViewPopup(SearchWebOrder swo, Model model) {

		model.addAttribute("ordReqNo",  swo.getOrdReqNo());
		return "edi/weborder/PEDMWEB0010Popup";
	}


	/**
	 * Desc : 점포별 발주상품 상세 조회 (팝업 화면)
	 * @Method Name : VenOrdProdList
	 * @param SearchWebOrder
	 * @param Model
	 * @return AjaxJsonModelHelper(Map<String,Object>)
	 */
	@RequestMapping(value = "/edi/weborder/PEDMWEB0010ProdSelect.do")
    public ModelAndView VenOrdProdList(@RequestBody SearchWebOrder swo)throws Exception{
		Map<String,Object> result =  new HashMap<String, Object>();
		result.put("state", "-1");
		try{
			result = pEDMWEB0010Service.selectVenOrdProdInfo(swo);
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
	@RequestMapping("/edi/weborder/PEDMWEB0010Delete.do")
	public ModelAndView getStoreOrdDelete(@RequestBody TedPoOrdMstVO vo, HttpServletRequest request) throws Throwable
	{
		Map<String,String>  rtnData	    = new HashMap<String,String>();
		rtnData.put("state", "-1");
		try{

			/**발주 삭제 구분 1:마스터(점포별), 2:디테일(점포/상품별) */
			vo.setUpdateState("1");


			int cnt = pEDMWEB0010Service.updateStrCd(vo,request);
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
	@RequestMapping("/edi/weborder/PEDMWEB0010ProdDelete.do")
	public ModelAndView getStoreOrdProdDelete(@RequestBody TedOrdProcess010VO vo, HttpServletRequest request) throws Throwable
	{
		Map<String,String>  rtnData	    = new HashMap<String,String>();
		rtnData.put("state", "-1");
		try{

			pEDMWEB0010Service.updateStrCdProd(vo,request);

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
	@RequestMapping("/edi/weborder/PEDMWEB0010ProdDetUpdate.do")
	public ModelAndView getStoreOrdPrdDelete(@RequestBody TedOrdProcess010VO vo, HttpServletRequest request) throws Throwable
	{
		Map<String,Object> result = new HashMap();
		result.put("state"  ,"-1");
		try{

		    pEDMWEB0010Service.updateStCdProd(vo,request);

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
	@RequestMapping("edi/weborder/PEDMWEB0010SendProd.do")
	public ModelAndView sendReturnProdData(@RequestBody TedOrdProcess010VO vo, HttpServletRequest request) throws Exception
	{
		HashMap<String, String> rtnData = new HashMap<String, String>();
		rtnData.put("state","-1");
		try
		{
		    rtnData = pEDMWEB0010Service.insertSendProdData(vo, request);

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

		model.addAttribute("VenList", pEDMWEB0010Service.selectVenOrdInfo(map));
		model.addAttribute("VenListSum", pEDMWEB0010Service.selectVenOrdSumInfo(map));


		return "edi/weborder/PEDMWEB0010";
	}
	*/






}
