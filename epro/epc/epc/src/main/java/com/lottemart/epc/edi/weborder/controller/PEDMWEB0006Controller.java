package com.lottemart.epc.edi.weborder.controller;




import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lcn.module.common.views.AjaxJsonModelHelper;

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
import com.lottemart.epc.edi.weborder.model.EdiRtnProdProcessVO;
import com.lottemart.epc.edi.weborder.model.EdiRtnProdVO;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.service.PEDMWEB0006Service;


/**
 * @Class Name : PEDMWEB0006Controller
 * @Description : 상품별 반품등록 Controller 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2014. 08. 11. 오후 2:30:34 DADA
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */


@Controller
public class PEDMWEB0006Controller extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(PEDMWEB0006Controller.class);


	private PEDMWEB0006Service pEDMWEB0006Service;

	@Autowired
	public void setpEDMWEB0006Service(PEDMWEB0006Service pEDMWEB0006Service) {
		this.pEDMWEB0006Service = pEDMWEB0006Service;
	}

	/**
	 * Desc : 상품별 반품등록
	 * @Method Name : storeWebOrderList
	 * @param SearchWebOrder
	 * @param HttpServletRequest
	 * @param Model
	 * @return view 페이지
	 */
	@RequestMapping(value="/edi/weborder/PEDMWEB0006")
    public String storeWebOrderList(SearchWebOrder searchParam, HttpServletRequest request, Model model) {
		SimpleDateFormat format = new SimpleDateFormat("HHmm", Locale.KOREA);

		int nowDt = Integer.parseInt(format.format(new Date()));

		String vendorWebOrdFrDt = ConfigUtils.getString("edi.weboder.vendor.rtn");
		String vendorWebOrdToDt = ConfigUtils.getString("edi.weboder.vendor.to");

		if( ( nowDt < Integer.parseInt(vendorWebOrdFrDt)) || ( nowDt > Integer.parseInt(vendorWebOrdToDt)) ){
			return "edi/weborder/PEDMWEB0099";
		}
		return "edi/weborder/PEDMWEB0006";
	}



	/**
    * 반품상품저장
	* @param SearchWebOrder
	* @return  AjaxJsonModelHelper[ EdiRtnProdVO ]
	*/
	@RequestMapping("edi/weborder/PEDMWEB0006SaveProd.do")
	public ModelAndView saveReturnProdData(@RequestBody EdiRtnProdVO vo, HttpServletRequest request) throws Throwable, RuntimeException
	{
		/**
			1. 반품상품 점별 마스터 생성   (saveRtnProdMst  -> INSERT TED_PO_RRL_MST  )
			2. 반품상품 점별 상품목록 생성 (saveRtnProdList -> INSERT TED_PO_RRL_PROD )
			3. 반품상품 점별 마스터 합계   (saveRtnrodSum   -> UPDATE TED_PO_RRL_MST  )
		*/

		/**
		 *  state
		 *  0  : 정상 등록         			( SUCCESS          )
		 *  1  : 중복 등록 오류    			( DUPLICATE DATA   )
		 *  2  : TED_PO_RRL_MST  미등록  	( TED_PO_RRL_MST   0 row inserted error )  --사용안함
		 *  3  : TED_PO_RRL_PROD 미등록       ( TED_PO_RRL_PROD  0 row inserted error )
		 * -1  : 시스템 오류    (exception message)
		 */
		HashMap<String, String> rtnData = new HashMap<String, String>();
		rtnData.put("state", "-1");
		try{
			rtnData  = pEDMWEB0006Service.insertReturnProdData(vo, request);
		}
		catch (Exception e) {
			rtnData.put("message",e.getMessage());
			logger.error(e.getMessage(), e);
			// TODO: handle exception
		}


		return AjaxJsonModelHelper.create(rtnData);
	}


	/**
	 * TODAY 협력사 반품상품 리스트 조회2
	 * @Method Name : selectDayRtnProdList
	 * @param SearchWebOrder
	 * @return AjaxJsonModelHelper(Map<String,Object>)
	 */
	@RequestMapping("edi/weborder/PEDMWEB0006SearchList.do")
	public ModelAndView selectDayRtnProdList(@RequestBody SearchWebOrder vo, HttpServletRequest request) throws Throwable
	{

		/**
		 *  state
		 *  0  : 정상 등록      ( SUCCESS          )
		 * -1  : 시스템 오류    (exception message)
		 */
		Map<String,Object>  result	    = new HashMap<String,Object>();
		result.put("state","-1");

		try{
			vo.setStrCd(null);
			result = pEDMWEB0006Service.selectDayRtnProdList(vo, request);
		}catch (Exception e) {
			result.put("message",e.getMessage());
			logger.error(e.getMessage(), e);
			// TODO: handle exception
		}
		return AjaxJsonModelHelper.create(result);
	}

	/**
	 * TODAY 협력사 반품등록 삭제 리스트 처리
	 * @Method Name : deleteReturnProdData
	 * @param EdiRtnProdProcessVO
	 * @return AjaxJsonModelHelper(HashMap<String, String>)
	 */
	@RequestMapping("edi/weborder/PEDMWEB0006DeleteProd.do")
	public ModelAndView deleteReturnProdData(@RequestBody EdiRtnProdProcessVO vo, HttpServletRequest request) throws Exception
	{
		HashMap<String, String> rtnData = new HashMap<String, String>();
		rtnData.put("state","-1");
		try
		{
			rtnData = pEDMWEB0006Service.deleteReturnProdData(vo,request);

		}catch (Exception e) {
			rtnData.put("message",e.getMessage());
			logger.error(e.getMessage(), e);
			// TODO: handle exception
		}

		return AjaxJsonModelHelper.create(rtnData);
	}

	/**
	 * TODAY 협력사코드별  반품승인 등록 처리(MARTNIS 전송)
	 * @Method Name : sendReturnProdData
	 * @param SearchWebOrder
	 * @return AjaxJsonModelHelper(HashMap<String, String>)
	 */
	@RequestMapping("edi/weborder/PEDMWEB0006SendProd.do")
	public ModelAndView sendReturnProdData(@RequestBody SearchWebOrder vo, HttpServletRequest request) throws Exception
	{
		HashMap<String, String> rtnData = new HashMap<String, String>();
		rtnData.put("state","-1");
		try
		{
			rtnData = pEDMWEB0006Service.insertSendReturnProdData(vo, request);

		}catch (Exception e) {
			rtnData.put("message",e.getMessage());
			logger.error(e.getMessage(), e);
			// TODO: handle exception
		}
		return AjaxJsonModelHelper.create(rtnData);
	}


	/* ============================================================================= */


}
