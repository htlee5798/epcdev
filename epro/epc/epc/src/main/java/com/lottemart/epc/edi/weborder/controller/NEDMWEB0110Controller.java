package com.lottemart.epc.edi.weborder.controller;




import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import lcn.module.common.views.AjaxJsonModelHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.weborder.model.NEDMWEB0110VO;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.service.NEDMWEB0110Service;


/**
 * @Class Name : NEDMWEB0110Controller
 * @Description : 상품별 반품등록 Controller 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2015. 12. 18. sun gil choi  오후 2:30:34 DADA
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */


@Controller
public class NEDMWEB0110Controller extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(NEDMWEB0110Controller.class);


	@Autowired
	private NEDMWEB0110Service NEDMWEB0110Service;

	/**
	 * Desc : 상품별 반품등록
	 * @Method Name : storeWebOrderList
	 * @param SearchWebOrder
	 * @param HttpServletRequest
	 * @param Model
	 * @return view 페이지
	 * @throws Exception
	 */
	//@RequestMapping(value="/edi/weborder/PEDMWEB0006")
	@RequestMapping(value="/edi/weborder/NEDMWEB0110")
    public String storeWebOrderList(SearchWebOrder searchParam, HttpServletRequest request, Model model) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("HHmm", Locale.KOREA);

		int nowDt = Integer.parseInt(format.format(new Date()));

		String vendorWebOrdFrDt = ConfigUtils.getString("edi.weboder.vendor.rtn");
		String vendorWebOrdToDt = ConfigUtils.getString("edi.weboder.vendor.to");
		if( ( nowDt < Integer.parseInt(vendorWebOrdFrDt)) || ( nowDt > Integer.parseInt(vendorWebOrdToDt))){
			//return "edi/weborder/PEDMWEB0099";
			return "edi/weborder/NEDMWEB0111";
		}
		return "edi/weborder/NEDMWEB0110";
	}



	/**
    * 반품상품저장
	* @param SearchWebOrder
	* @return  AjaxJsonModelHelper[ NEDMWEB0110VO ]
	*/
	@RequestMapping("/edi/weborder/NEDMWEB0110saveReturnProdData.json")
	public ModelAndView saveReturnProdData(ModelMap model,@RequestBody NEDMWEB0110VO vo, HttpServletRequest request) throws Throwable, RuntimeException
	{
		/**
			1. 반품상품 점별 마스터 생성   (saveRtnProdMst  -> INSERT TED_PO_RRL_MST  )
			2. 반품상품 점별 상품목록 생성 (saveRtnProdList -> INSERT TED_PO_RRL_PROD )
		*/

		/**
		 *  state
		 *  0  : 정상 등록         			( SUCCESS          )
		 *  1  : 중복 등록 오류    			( DUPLICATE DATA   )
		 *  2  : TED_PO_RRL_MST  미등록  	( TED_PO_RRL_MST   0 row inserted error )
		 *  3  : TED_PO_RRL_PROD 미등록       ( TED_PO_RRL_PROD  0 row inserted error )
		 * -1  : 시스템 오류    (exception message)
		 */
		HashMap<String, String> rtnData = new HashMap<String, String>();
		rtnData.put("state", "-1");
		try{
			rtnData  = NEDMWEB0110Service.insertReturnProdData(vo, request);
		}
		catch (Exception e) {
			rtnData.put("message",e.getMessage());
			logger.error(e.getMessage(), e);
			// TODO: handle exception
		}


		return AjaxJsonModelHelper.create(rtnData);
	}

	/**
    * 반품 상품 저장값 체크
	* @param SearchWebOrder
	* @return  AjaxJsonModelHelper[ NEDMWEB0110VO ]
	*/
	@RequestMapping("/edi/weborder/NEDMWEB0110selectDayRtnProdList.json")
	public ModelAndView selectDayRtnProdList(ModelMap model,@RequestBody NEDMWEB0110VO vo, HttpServletRequest request) throws Throwable, RuntimeException
	{
		HashMap<String, Object> rtnData = new HashMap<String, Object>();
		rtnData.put("state", "-1");
		try{
			/*vo.setVenCd("3999");
			vo.setProdCd("1000000010");*/
			List<NEDMWEB0110VO> result  = NEDMWEB0110Service.NEDMWEB0110selectDayRtnProdList(vo);
			rtnData.put("result", result);
		}
		catch (Exception e) {
			rtnData.put("message",e.getMessage());
			logger.error(e.getMessage(), e);
			// TODO: handle exception
		}



		return AjaxJsonModelHelper.create(rtnData);
	}




}
