package com.lottemart.epc.edi.weborder.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lcn.module.common.views.AjaxJsonModelHelper;

import com.lcnjf.util.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;







import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.weborder.model.EdiPoEmpPoolVO;
import com.lottemart.epc.edi.weborder.model.EdiRtnProdVO;
import com.lottemart.epc.edi.weborder.model.EdiVendorVO;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.model.TedStore;
import com.lottemart.epc.edi.weborder.service.PEDMWEB0099Service;

/**
 * @Class Name : PEDMWEB0099Controller
 * @Description : 웹발주 공통 컨트롤러
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2014. 08. 26. 오후 12:33:50 ljy
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */


@Controller
public class PEDMWEB0099Controller extends BaseController{



	private PEDMWEB0099Service pEDMWEB0099Service;

	//공통코드 조회 서비스
//	private PEDPCOM0001Service commService;

	private static final Logger logger = LoggerFactory.getLogger(PEDMWEB0020Controller.class);

	@Autowired
	public void setpEDMWEB0020Service(PEDMWEB0099Service pEDMWEB0099Service) {
		this.pEDMWEB0099Service = pEDMWEB0099Service;
	}

//	@Autowired
//	public void setCommService(PEDPCOM0001Service commService) {
//		this.commService = commService;
//	}

	/**
	 * Desc : 엑셀 업로드 헬프 팝업
	 * @Method Name : excelHelpViewPopup
	 * @return view 페이지
	 */
	@RequestMapping(value="/edi/weborder/PEDMWEB00991Popup.do")
    public String excelHelpViewPopup() {
		return "edi/weborder/PEDMWEB00991Popup";
	}

	/**
	 * Desc : 반품상품목록 조회 공통 팝업
	 * @Method Name : venProdListInit
	 * @return view 페이지
	 */
	@RequestMapping(value = "/edi/weborder/PEDMWEB00992Popup.do")
    public String venProdListInit(SearchWebOrder searchParam, HttpServletRequest request, Model model)throws Exception{
		return "edi/weborder/PEDMWEB00992Popup";
	}

	/**
	 * Desc : 협력사 발주가능 점포 조회 공통 팝업
	 * @Method Name : venProdListInit
	 * @return view 페이지
	 */
	@RequestMapping(value = "/edi/weborder/PEDMWEB00993Popup.do")
    public String venStrListInit(SearchWebOrder searchParam, HttpServletRequest request, Model model)throws Exception{
		return "edi/weborder/PEDMWEB00993Popup";
	}




	/**
    * 담당자 사번 검색
	* @param SearchWebOrder
	* @return  AjaxJsonModelHelper[ HashMap<String, Object> ]
	*/
	@RequestMapping("edi/weborder/PEDMWEB0099SearchEmplPool.do")
	public ModelAndView searchEmpPoolData(@RequestBody SearchWebOrder vo) throws Throwable{
		HashMap<String, Object> rtnData = new HashMap<String, Object>();
		rtnData.put("state", "-1");
		try{

			EdiPoEmpPoolVO ediPoEmpPool = pEDMWEB0099Service.selectEmpPoolData(vo);
			rtnData.put("empPool", 	ediPoEmpPool);
			rtnData.put("state"  , 	"0");
			rtnData.put("message",	"SUCCESS");

		}catch (Exception e) {
			rtnData.put("empPool", null);
			rtnData.put("message",e.getMessage());
			logger.error(e.getMessage(), e);
			// TODO: handle exception
		}
		return AjaxJsonModelHelper.create(rtnData);
	}

	/**
	    * 담당자 또는 전체 협력사(특정1) 조회
		* @param SearchWebOrder
		* @return  AjaxJsonModelHelper[ HashMap<String, Object> ]
		*/
		@RequestMapping("edi/weborder/PEDMWEB0099SearchEmpVendor.do")
		public ModelAndView searchEmpVenData(@RequestBody SearchWebOrder vo, HttpServletRequest request) throws Throwable{
			HashMap<String, Object> rtnData = new HashMap<String, Object>();


			String loginId = StringUtils.nvl(vo.getEmpNo(),"");


			if("".equals(StringUtils.nvl(vo.getFindType(),""))) vo.setEmpNo(loginId);

			rtnData.put("state", "-1");
			try{

				EdiVendorVO ediVendor = pEDMWEB0099Service.selectEmpVenData(vo);
				rtnData.put("venData", 	ediVendor);
				rtnData.put("state"  , 	"0");
				rtnData.put("message",	"SUCCESS");

			}catch (Exception e) {
				rtnData.put("venData", null);
				rtnData.put("message",e.getMessage());
				logger.error(e.getMessage(), e);
				// TODO: handle exception
			}
			return AjaxJsonModelHelper.create(rtnData);
		}








	/**
    * 반품상품조회
	* @param SearchWebOrder
	* @return  AjaxJsonModelHelper[ EdiRtnProdVO ]
	*/
	@RequestMapping("edi/weborder/PEDMWEB0099SearchProd.do")
	public ModelAndView searchReturnProdData(@RequestBody SearchWebOrder vo) throws Throwable
	{
		/**
		 * State : -1:Exception, 0:정상상품 조회 완료, 1:조회상품정보 없음.
		 */
		EdiRtnProdVO  ediRtnProdVO = new EdiRtnProdVO();
		ediRtnProdVO.setState("-1");

		try{

			ediRtnProdVO = pEDMWEB0099Service.selectStrCdProdSumList(vo);

			if(ediRtnProdVO == null){
				ediRtnProdVO = new EdiRtnProdVO();

				ediRtnProdVO.setState("1");
				ediRtnProdVO.setMessage("NO DATA FOUND!");
			}
			else{
				ediRtnProdVO.setState("0");
				ediRtnProdVO.setMessage("SUCCESS");
			}
		}
		catch (Exception e) {
			ediRtnProdVO.setMessage(e.getMessage());
			logger.error(e.getMessage(), e);
			// TODO: handle exception
		}
		return AjaxJsonModelHelper.create(ediRtnProdVO);
	}


	/**
	    * 반품상품조회2
		* @param SearchWebOrder
		* @return  AjaxJsonModelHelper[ EdiRtnProdVO ]
		*/
		@RequestMapping("edi/weborder/PEDMWEB0099SearchProd2.do")
		public ModelAndView searchReturnProdData2(@RequestBody SearchWebOrder vo) throws Throwable
		{
			/**
			 * State : -1:Exception, 0:정상상품 조회 완료, 1:조회상품정보 없음.
			 */
			HashMap<String, Object> rtnData = new HashMap<String, Object>();
			rtnData.put("state", "-1");

			try{

				List<EdiRtnProdVO> list = pEDMWEB0099Service.selectStrCdProdSumList2(vo);

				if(list == null){
					rtnData.put("productList", list);
					rtnData.put("state","1");
					rtnData.put("message","NO DATA FOUND!");
				}
				else{
					rtnData.put("state","0");
					rtnData.put("message","SUCCESS");
				}
			}
			catch (Exception e) {
				rtnData.put("productList", null);
				rtnData.put("message",e.getMessage());
				logger.error(e.getMessage(), e);
				// TODO: handle exception
			}
			return AjaxJsonModelHelper.create(rtnData);
		}


	/**
    * 반품상품 목록 조회
	* @param SearchWebOrder
	* @return  AjaxJsonModelHelper[ EdiRtnProdVO ]
	*/
	@RequestMapping(value = "/edi/weborder/PEDMCOM0009SelectVenProd.do")
    public ModelAndView venProdListList(@RequestBody SearchWebOrder vo, HttpServletRequest request)throws Exception{
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("state","-1");
		try{
			result =	pEDMWEB0099Service.selectProdCodeList(vo);
		}catch(Exception e){

			result.put("message",e.getMessage());
			logger.error(e.getMessage(), e);
			// TODO: handle exception
		}


		return AjaxJsonModelHelper.create(result);
	}


	/**
    * 업체별 발주가능 점포 목록 조회
	* @param SearchWebOrder
	* @return  AjaxJsonModelHelper[ Map<String, Object>]
	*/
	@RequestMapping(value = "/edi/weborder/PEDMCOM0009SelectVenStore.do")
    public ModelAndView selectVenStoreCodeList(@RequestBody SearchWebOrder vo, HttpServletRequest request)throws Exception{
		Map<String, Object> result = new HashMap<String, Object>();

		result.put("state","-1");
		try{
			List<TedStore> list =	pEDMWEB0099Service.selectVenStoreCodeList(vo);
			result.put("dataList", list);


			result.put("state",		"0");
			result.put("message",	"SUCCESS");

		}catch(Exception e){

			result.put("message",e.getMessage());
			logger.error(e.getMessage(), e);
			// TODO: handle exception
		}

		return AjaxJsonModelHelper.create(result);
	}









}
