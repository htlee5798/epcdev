package com.lottemart.epc.edi.product.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lcn.module.common.util.DateUtil;
import lcn.module.common.util.HashBox;
import lcn.module.framework.property.ConfigurationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.product.model.NEDMPRO0042VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0220VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0221VO;
import com.lottemart.epc.edi.product.service.NEDMPRO0040Service;
import com.lottemart.epc.edi.product.service.NEDMPRO0220Service;

/**
 * @Class Name : NEDMPRO0220Controller
 * @Description : 물류바코드등록 Controller
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2015.11.30 	SONG MIN KYO	최초생성
 * </pre>
 */

@Controller
public class NEDMPRO0220Controller extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0220Controller.class);

	@Resource(name = "configurationService")
	private ConfigurationService config;

	@Resource(name = "nEDMPRO0220Service")
	private NEDMPRO0220Service nEDMPRO0220Service;

	@Resource(name = "nEDMPRO0040Service")
	private NEDMPRO0040Service nEDMPRO0040Service;



	/**
	 * 임시보관함에서 바코드 등록을 위한 기본 정보 조회
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/imsiProductRegLogiBcpPop.do")
    public String NEDMPRO0220SearchTmp(@RequestParam Map<String,Object> paramMap, ModelMap model, HttpServletRequest request ) throws Exception {

		String nowDate 					= DateUtil.getToday("yyyy-MM-dd");
		//HashMap hData 					=  (HashMap)nEDMPRO0220Service.selectNewBarcodeRegistTmp(paramMap);
		List<NEDMPRO0042VO> classList	=  nEDMPRO0040Service.selectNewSrcmkCd(paramMap, request);

		model.addAttribute("classListCnt",		nEDMPRO0220Service.selectImsiProdClassListCnt((String)paramMap.get("pgmId")));	//상품의 등록된 속성 리스트 카운트
		model.addAttribute("classList",			classList);																		//상품의 등록된 속성 리스트
		//model.addAttribute("tmpBarcodeList",	hData);																			//상품정보
		model.addAttribute("nowDate",			nowDate);

		model.addAttribute("varAttNm",			nEDMPRO0220Service.selectVarAttNmInfo(paramMap));

		return "edi/product/NEDMPRO0220Pop";
	}

	/**
	 * [임시보관함 바코드 등록 팝업] 해당상품의 등록된 속성이 있을경우 등록된 속성의 물류바코드 조회
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/selectImsiProdLogiBcd.json",	method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> selectImsiProdLogiBcd(@RequestBody Map<String, Object> paramMap, HttpServletRequest request) throws Exception {
		return nEDMPRO0220Service.selectImsiProdClassToLogiBcdInfo(paramMap);
	}

	/**
	 * 임시보관함에서 물류 바코드 저장
	 * @param nEDMPRO0220VO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/insertImsiProdLogiBcd.json",	method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> insertImsiProdLogiBcd(@RequestBody NEDMPRO0220VO nEDMPRO0220VO, HttpServletRequest request) throws Exception {
		Map<String, Object> result = null;
		try{
			result = nEDMPRO0220Service.insertImsiProdLogiBcd(nEDMPRO0220VO, request);
		}catch(Exception e){
			logger.error("물류바코드 등록  Exception 발생 ===============================================================================" + e.toString());
		}

		return result;
	}

	/**
	 * 임시보관함에서 등록하는 물류바코드 저장
	 * @param barcodeParam
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/NEDMPRO0220Save.do")
	public String insertNewProductBarcodeTmp(NEDMPRO0220VO nEDMPRO0220VO, Model model, HttpServletRequest request) throws Exception {
		Map<String, Object>	paramMap	=	new HashMap<String, Object>();

		// 1.저장
		//nEDMPRO0220Service.insertNewProductBarcodeTmp(nEDMPRO0220VO, request);

		// 2.상품정보 조회 (조건:신규상품코드) ----------------------------------------------------------
		//paramMap.put("pgmId", nEDMPRO0220VO.getNew_prod_id());
		//HashMap hData =  (HashMap)nEDMPRO0220Service.selectNewBarcodeRegistTmp(paramMap);

		/*if(hData != null)
		{
			//3.조회된 상품정보의 바코드 정보 조회 ---
			//model.addAttribute("barcodeList", 		nEDMPRO0220Service.selectNewBarcodeListTmp(nEDMPRO0220VO, request));
			model.addAttribute("tmpBarcodeList",	hData);
		}*/

		model.addAttribute("searchParam",nEDMPRO0220VO);  // 조회조건 유지
		model.addAttribute("result", "insert");

		return "edi/product/NEDMPRO0220Pop";

	}


	///////////////////////////////////////////////////////////


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
	@RequestMapping(value="/edi/product/NEDMPRO0220", method=RequestMethod.GET)
    public String newBarcodeRegist(Model model,  HttpServletRequest request) throws Exception {

		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		model.addAttribute("epcLoginVO", epcLoginVO);

		return "edi/product/NEDMPRO0220";
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
	@RequestMapping(value="/edi/product/NEDMPRO0220Search", method=RequestMethod.POST)
    public String searchbarcodeProductInfo(NEDMPRO0221VO searchParam, ModelMap model ,HttpServletRequest request) throws Exception {
		model.addAttribute("searchParam", searchParam);  // 조회조건 유지

		// 상품정보 조회 (조건:협력업체코드,판매코드) -------------------------------------
//		HashBox hData = null;
//		hData =  (HashBox)nEDMPRO0220Service.selectBarCodeProductInfo(searchParam);
		
		
		HashMap<String , Object> hData = new HashMap<>();
		hData =  nEDMPRO0220Service.selectBarCodeProductInfo(searchParam);
		//hData = getProduct();

		if (hData != null) {
			// 조회된 상품정보의 바코드 정보 조회 -----------------------------------------
			List barcodeList = null;
			barcodeList = nEDMPRO0220Service.selectBarcodeList(searchParam);
			//barcodeList = getBCDList();
			model.addAttribute("barcodeList", barcodeList);

			model.addAttribute("productData", hData);
		}

		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		model.addAttribute("epcLoginVO", epcLoginVO);

		return "edi/product/NEDMPRO0220";
	}

	/**
	 * 물류바코드 수정 및 신규저장
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/NEDMPRO0220SaveBarcode.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> NEDMPRO0220SaveBarcode(@RequestBody NEDMPRO0220VO vo, HttpServletRequest request) throws Exception {
		if (vo == null || request == null) {
			return null;
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		String retMsg = nEDMPRO0220Service.insertNewbarcode(vo, request);

		resultMap.put("msgCd", retMsg);

		return resultMap;
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
	/*@RequestMapping(value="/edi/product/NEDMPRO0220SaveBCD", method=RequestMethod.POST)
    public String onSubmitNewBarcode( NEDMPRO0221VO barcodeParam, Model model,HttpServletRequest request) throws Exception {
		//logger.debug("--------------[1.START :  /edi/product/PEDMPRO0006Save ]-------------------------------");
		barcodeParam.setRegId(barcodeParam.getVenCd());	// 등록자 아이디(협력업체코드로 저장함)
		//--바코드 입력정보 수정 및 신규저장 -------------------------------------------------------------------
		nEDMPRO0220Service.insertNewBarcode(barcodeParam);
		//----------------------------------------------------------------------------------------------
		logger.debug("--------------[3.insertNewBarcode :  "+barcodeParam.getSearchVenCd()+" ]-----------------------------");

		// 상품정보 조회 (조건:협력업체코드,판매코드) ----------------------------------------------------------
		barcodeParam.setSearchSrcmkCd(barcodeParam.getSrcmkCd());	 //조회조건 판매(88)/내부
		barcodeParam.setSearchVenCd(barcodeParam.getVenCd() ); 		//조회조건 협력업체노드


		logger.debug("--------------[4.insertNewBarcode :  "+barcodeParam.getSearchVenCd()+" ]-----------------------------");
		HashBox hData =  (HashBox)nEDMPRO0220Service.selectBarCodeProductInfo(barcodeParam);

		if(hData != null)
		{

			logger.debug("--------------[5.hData not null :  "+barcodeParam.getSearchVenCd()+" ]-----------------------------");
			// 조회된 상품정보의 바코드 정보 조회 ---
			model.addAttribute("barcodeList", nEDMPRO0220Service.selectBarcodeList(barcodeParam));
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

		logger.debug("--------------[7. End   :  "+barcodeParam.getSearchVenCd()+" ]-----------------------------");

		return "edi/product/NEDMPRO0220";
	}*/

}
