package com.lottemart.epc.edi.product.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lcn.module.common.util.DateUtil;
import lcn.module.common.util.HashBox;
import lcn.module.common.views.AjaxJsonModelHelper;
import lcn.module.framework.property.ConfigurationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;



import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.product.model.NewProduct;
import com.lottemart.epc.edi.product.model.PEDMPRO0006VO;

import com.lottemart.epc.edi.product.model.PEDMPRO00061VO;

import com.lottemart.epc.edi.product.model.SearchProduct;
import com.lottemart.epc.edi.product.service.PEDMPRO0006Service;

@Controller
public class PEDMPRO0006Controller extends BaseController {


	@Autowired
	private PEDMPRO0006Service pEDMPRO0006Service;


	@Resource(name = "configurationService")
	private ConfigurationService config;


	private static final Logger logger = LoggerFactory.getLogger(PEDMPRO0006Controller.class);


	/**
	 * Desc : 물류바코드 등록 Loding Page
	 * @Method Name : newBarcodeRegist
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value="/edi/product/PEDMPRO0006", method=RequestMethod.GET)
    public String newBarcodeRegist(Model model,  HttpServletRequest request) {

		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		model.addAttribute("epcLoginVO", epcLoginVO);

		return "edi/product/PEDMPRO0006";
	}

	/**
	 * Desc : 신규상품 임시보관함 물류바코드 등록 Loding Page
	 * @Method Name : newProdBarcodeRegist
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value="/edi/product/PEDPPRO0006", method=RequestMethod.GET)
    public String newProdBarcodeRegist(@RequestParam Map<String,Object> map, ModelMap model ) {

		//model.addAttribute("productData", (HashBox)pEDMPRO0006Service.selectBarCodeProductInfo(map));
		return "edi/product/PEDMPRO0006";
	}

	/**
	 * Desc : 물류바코드 등록을 위한 상품정보 조회  및 선택 상품의 바코드 List 조회
	 * @Method Name : searchbarcodeProductInfo
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value="/edi/product/PEDMPRO0006Search", method=RequestMethod.POST)
    public String searchbarcodeProductInfo(PEDMPRO0006VO searchParam, ModelMap model ,HttpServletRequest request) {



		model.addAttribute("searchParam",searchParam);  // 조회조건 유지

		// 상품정보 조회 (조건:협력업체코드,판매코드) -------------------------------------
		HashBox hData =  (HashBox)pEDMPRO0006Service.selectBarCodeProductInfo(searchParam);

		if(hData != null)
		{
			// 조회된 상품정보의 바코드 정보 조회 -----------------------------------------
			model.addAttribute("barcodeList", pEDMPRO0006Service.selectBarcodeList(searchParam));
			model.addAttribute("productData", hData);
		}

		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		model.addAttribute("epcLoginVO", epcLoginVO);

		return "edi/product/PEDMPRO0006";
	}


	/**
	 * Desc : 물류바코드 저장
	 * @Method Name : saveBarcode
	 * @param PEDMPRO0006VO
	 * @param model
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value="/edi/product/PEDMPRO0006Save", method=RequestMethod.POST)
    public String onSubmitNewBarcode( PEDMPRO0006VO barcodeParam, Model model,HttpServletRequest request) {

		try
		{
			logger.debug("--------------[1.START :  /edi/product/PEDMPRO0006Save ]-------------------------------");
			barcodeParam.setLstChgEmpNo(barcodeParam.getVenCd());	// 등록자 아이디(협력업체코드로 저장함)
			//--바코드 입력정보 수정 및 신규저장 -------------------------------------------------------------------
			pEDMPRO0006Service.insertNewBarcode(barcodeParam);
			//----------------------------------------------------------------------------------------------
//			logger.debug("--------------[3.insertNewBarcode :  "+barcodeParam.getSearchVenCd()+" ]-----------------------------");

			// 상품정보 조회 (조건:협력업체코드,판매코드) ----------------------------------------------------------
			barcodeParam.setSearchSrcmkCd(barcodeParam.getSrcmkCd());	 //조회조건 판매(88)/내부
			barcodeParam.setSearchVenCd(barcodeParam.getVenCd() ); 		//조회조건 협력업체노드


//			logger.debug("--------------[4.insertNewBarcode :  "+barcodeParam.getSearchVenCd()+" ]-----------------------------");
			HashBox hData =  (HashBox)pEDMPRO0006Service.selectBarCodeProductInfo(barcodeParam);

			if(hData != null)
			{

//				logger.debug("--------------[5.hData not null :  "+barcodeParam.getSearchVenCd()+" ]-----------------------------");
				// 조회된 상품정보의 바코드 정보 조회 ---
				model.addAttribute("barcodeList", pEDMPRO0006Service.selectBarcodeList(barcodeParam));
				model.addAttribute("productData", hData);


			}
			else
			{
				logger.debug("--------------[6.hData  null :  "+barcodeParam.getSearchVenCd()+" ]-----------------------------");
			}
			model.addAttribute("searchParam",barcodeParam);  // 조회조건 유지

			EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
			model.addAttribute("epcLoginVO", epcLoginVO);
			//----------------------------------------------------------------------------------------------

//			logger.debug("--------------[7. End   :  "+barcodeParam.getSearchVenCd()+" ]-----------------------------");

		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage());
		}
		return "edi/product/PEDMPRO0006";




	}

	//임시보관함 물류바코드 등록 페이지
	@RequestMapping(value="/edi/product/PEDMPRO0006SearchTmp", method=RequestMethod.POST)
    public String newBarcodeRegistTmp(@RequestParam Map<String,Object> map, ModelMap model ) {

		String nowDate = DateUtil.getToday("yyyy-MM-dd");
		String newProd = map.get("new_prod_id").toString();

		HashMap hData =  (HashMap)pEDMPRO0006Service.newBarcodeRegistTmp(newProd);

		model.addAttribute("tmpBarcodeList",hData);
		model.addAttribute("nowDate",nowDate);


		return "edi/product/PEDPPRO000601";
	}

	//임시보관함 물류바코드 수정 페이지
		@RequestMapping(value="/edi/product/PEDMPRO0006UpdatePageTmp", method=RequestMethod.POST)
	    public String newBarcodeRegistTmpUpdatePage(@RequestParam Map<String,Object> map, ModelMap model, PEDMPRO0006VO barcodeParam ) {

			String newProd = map.get("new_prod_id").toString();
			String vencd = map.get("vencd").toString();


			barcodeParam.setNew_prod_id(newProd);	 //조회조건 판매(88)/내부
			barcodeParam.setSearchVenCd(vencd); 		//조회조건 협력업체노드

			HashMap hData =  (HashMap)pEDMPRO0006Service.newBarcodeRegistTmp(newProd);

			if(hData != null)
			{
				// 조회된 상품정보의 바코드 정보 조회 ---
				model.addAttribute("barcodeList", pEDMPRO0006Service.selectBarcodeListTmp(barcodeParam));
				model.addAttribute("tmpBarcodeList",hData);
			}

			return "edi/product/PEDPPRO000601";
		}

	//임시보관함 물류바코드 등록
	@RequestMapping(value="/edi/product/PEDMPRO000601Save", method=RequestMethod.POST)
    public String onSubmitNewBarcodeTmp( PEDMPRO0006VO barcodeParam, Model model) {

		String newProd = barcodeParam.getNew_prod_id();
		String vencd = barcodeParam.getVenCd();

		try
		{
			barcodeParam.setLstChgEmpNo(barcodeParam.getVenCd());	// 등록자 아이디(협력업체코드로 저장함)
			//--바코드 입력정보 수정 및 신규저장 -------------------------------------------------------------------
			pEDMPRO0006Service.insertNewBarcodeTmp(barcodeParam);
			//----------------------------------------------------------------------------------------------

			// 상품정보 조회 (조건:협력업체코드,판매코드) ----------------------------------------------------------
			barcodeParam.setNew_prod_id(newProd);	 //조회조건 판매(88)/내부
			barcodeParam.setSearchVenCd(vencd); 		//조회조건 협력업체노드

			HashMap hData =  (HashMap)pEDMPRO0006Service.newBarcodeRegistTmp(newProd);

			if(hData != null)
			{
				// 조회된 상품정보의 바코드 정보 조회 ---
				model.addAttribute("barcodeList", (HashMap)pEDMPRO0006Service.selectBarcodeListTmp(barcodeParam));
				model.addAttribute("tmpBarcodeList",hData);
			}
			model.addAttribute("searchParam",barcodeParam);  // 조회조건 유지
			//----------------------------------------------------------------------------------------------
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage());
		}

		model.addAttribute("result", "insert");

		return "edi/product/PEDPPRO000601";


	}



	/*
	@RequestMapping(value="/edi/product/PEDMPRO0006", method=RequestMethod.POST)
    public String onSubmitNewBarcode(PEDMPRO0006VO barCode, Model model) {
	*/


		/**
		 * 1. ajax로 조회해서 설정되는 값
		 * #newProductCode#,  #l1GroupCode#,
		 *
		 *
		 * 2.사용자가 입력하는  필드값
		 *   #sellCode#,   	#logiBarcode#, #entpCode#, #useFlag#,
			 #weight#,    		 $width$,  			$length$,
			 $height$,   #mixProductFlag#,


			 null,    			 sysdate,          sysdate,    		'LOGI_EDI',
				'I',
		 *
		 *
		 *   		 '01', 				'01',
			 #conveyFlag#,       	$innerIpsu$,
			 $plateLayerQty$, 	 $plateHeightQty$,
			 $logiBoxIpsu$,

 	 		3.연산으로 도출되는 값
 	 		 #codeFlag#
			 #totalBoxCount#,
			  #sorterFlag#,

		 */



		/*
		logger.debug("barCode "+barCode);
		pEDMPRO0006Service.insertNewBarcode(barCode);
		return "edi/product/PEDMPRO0006";
	}
	*/

}
