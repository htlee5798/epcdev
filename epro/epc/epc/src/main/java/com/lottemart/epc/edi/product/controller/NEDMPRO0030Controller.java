package com.lottemart.epc.edi.product.controller;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lcnjf.util.NumberUtil;
import com.lcnjf.util.StringUtil;
import com.lottemart.common.login.model.LoginSession;
import com.lottemart.common.util.ConfigUtils;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.service.PSCMCOM0004Service;
import com.lottemart.epc.common.util.SecureUtil;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.product.model.NEDMPRO0020VO;
import com.lottemart.epc.edi.product.model.NewProduct;
import com.lottemart.epc.edi.product.service.CommonProductService;
import com.lottemart.epc.edi.product.service.NEDMPRO0020Service;
import com.lottemart.epc.edi.product.service.NEDMPRO0030Service;
import com.lottemart.epc.edi.product.service.PEDMPRO0003Service;
import com.lottemart.epc.edi.product.service.PEDMPRO000Service;

import lcn.module.common.util.DateUtil;
import lcn.module.common.views.AjaxJsonModelHelper;
import lcn.module.framework.property.ConfigurationService;

/**
 * @Class Name : NEDMPRO0030Controller
 * @Description : 온라인전용 Controller
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2015.12.29 	SONG MIN KYO	최초생성
 * </pre>
 */

@Controller
public class NEDMPRO0030Controller extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0030Controller.class);

	@Resource(name = "configurationService")
	private ConfigurationService config;

	@Resource(name="commonProductService")
	private CommonProductService commonProductService;

	@Resource(name="nEDMPRO0020Service")
	private NEDMPRO0020Service nEDMPRO0020Service;

	@Resource(name="nEDMPRO0030Service")
	private NEDMPRO0030Service nEDMPRO0030Service;

	//edi 공통서비스
	@Resource(name="ediProductService")
	private PEDMPRO000Service ediProductService;

	@Resource(name="pEDMPRO0003Service")
	private PEDMPRO0003Service pEDMPRO0003Service;

	@Resource(name="pscmcom0004Service")
	private PSCMCOM0004Service pscmcom0004Service;

	/**
	 * 온라인전용 상품상세 보기 페이지
	 * @param nEDMPRO0020VO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/NEDMPRO0030OnlineDetail.do")
	public String selectNewOnlineProdDetail(@RequestParam String pgmId, HttpServletRequest request, ModelMap model) throws Exception {
		if (request == null) {
			throw new IllegalArgumentException();
		}

		Map<String, Object>	paramMap	=	new HashMap<String, Object>();
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		//-----상품상세정보
		paramMap.put("pgmId", StringUtils.trimToEmpty(pgmId));
		NEDMPRO0020VO	newProdDetailInfo	=	nEDMPRO0020Service.selectNewTmpOnlineProductDetailInfo(paramMap);

		//이미지 파일에 전달할 시간 파라미터. 브라우저 캐시 기능때문에 동일한 이름의 이미지 파일이
		//파일만 바뀌었을 경우, 브라우저에서 이전 이미지가 보여지는 것을 방지해줌.
		long milliSecond = System.currentTimeMillis();

		//-----현재 상품정보에 등록된 해당 협력업체의 온라인 대표상품 정보. 이익률, 88코드 조회
		List<NewProduct> onlineRepresentProductList = ediProductService.selectOnlineRepresentProductList(StringUtils.trimToEmpty(newProdDetailInfo.getEntpCd()));


		//-----상품 상세정보
		model.addAttribute("onlineRepresentProductList", 	onlineRepresentProductList);
		model.addAttribute("newProdDetailInfo", 	  		newProdDetailInfo);
		model.addAttribute("subFolderName", 				subFolderName(StringUtils.trimToEmpty(newProdDetailInfo.getPgmId())));
		model.addAttribute("epcLoginVO", 					epcLoginVO);
		model.addAttribute("currentSecond", 				Long.toString(milliSecond));
		model.addAttribute("itemListInTemp", 	 			pEDMPRO0003Service.selectProductItemListInTemp(newProdDetailInfo.getPgmId()));
		model.addAttribute("seasonYearList",	commonProductService.selectNseasonYear(paramMap));				//계절년도
		model.addAttribute("nowYear",			DateUtil.getToday("yyyy"));										//현재년도
		model.addAttribute("teamList",			commonProductService.selectNteamList(paramMap, request));		//팀리스트
		model.addAttribute("ecCategory",			nEDMPRO0030Service.selectEcCategoryByProduct(newProdDetailInfo.getPgmId()));		//팀리스트

		//20180904 - 상품키워드 입력 기능 추가
		model.addAttribute("tpcPrdKeywordList", ediProductService.selectTpcPrdKeywordList(newProdDetailInfo.getPgmId()));

		return "edi/product/NEDMPRO0031";
	}

	@RequestMapping(value = "/edi/product/NEDMPRO0030Category.do")
	public @ResponseBody Map selectProductSaleSum(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map rtnMap = new HashMap<String, Object>();
        Map paramMap = new HashMap<String, Object>();

		try {

			// 데이터 조회
			String[] categoryIdArr = request.getParameter("onlineDisplayCategoryCode").split(",");

			paramMap.put("categoryIdArr", categoryIdArr);

			List<DataMap> list = pscmcom0004Service.selectCategoryInList(paramMap);

			rtnMap = JsonUtils.convertList2Json((List)list, list.size(), null);

			rtnMap.put("result", true);
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
		    rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}


	/**
	 * 온라인전용 상품 이미지 등록
	 * @param nEDMPRO0020VO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/NEDMPRO0032.do")
	public String selectNewOnlineImageView(@RequestParam String pgmId, HttpServletRequest request, ModelMap model) throws Exception {
		if (request == null) {
			throw new IllegalArgumentException();
		}

		Map<String, Object>	paramMap	=	new HashMap<String, Object>();
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		//-----상품상세정보
		paramMap.put("pgmId", StringUtils.trimToEmpty(pgmId));
		paramMap.put("cfmFg", "");

		NEDMPRO0020VO	newProdDetailInfo	=	nEDMPRO0020Service.selectNewTmpProductDetailInfo(paramMap);

		//이미지 파일에 전달할 시간 파라미터. 브라우저 캐시 기능때문에 동일한 이름의 이미지 파일이
		//파일만 바뀌었을 경우, 브라우저에서 이전 이미지가 보여지는 것을 방지해줌.
		long milliSecond = System.currentTimeMillis();

		//온라인 이미지 파일 목록 조회. 수정페이지에서 온라인 -> 오프라인 pog이미지 이런 순서로 업로드 함.
		String onlineUploadFolder	 = makeSubFolderForOnline(StringUtils.trimToEmpty(newProdDetailInfo.getPgmId()));
		ArrayList<String> onlineImageList = new ArrayList<String>();
		File dir = new File(onlineUploadFolder);
		FileFilter fileFilter = new WildcardFileFilter(StringUtils.trimToEmpty(newProdDetailInfo.getPgmId())+"*");
		File[] files = dir.listFiles(fileFilter);
		for (int j = 0; j < files.length; j++) {
			String fileName = files[j].getName();
			if( fileName.lastIndexOf(".") < 0 )  {
				onlineImageList.add(fileName);
			}
		}

		model.addAttribute("newProdDetailInfo", 	  		newProdDetailInfo);
		model.addAttribute("onlineImageList",  				onlineImageList);
		model.addAttribute("imagePath" ,						ConfigUtils.getString("edi.online.image.url"));
		model.addAttribute("subFolderName", 				subFolderName(StringUtils.trimToEmpty(newProdDetailInfo.getPgmId())));
		model.addAttribute("epcLoginVO", 					epcLoginVO);
		model.addAttribute("currentSecond", 				Long.toString(milliSecond));

		return "edi/product/NEDMPRO0032";
	}

	/**
	 * 온라인전용 상품 배송정보 등록 페이지
	 * @param nEDMPRO0020VO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/NEDMPRO0033.do")
	public String selectNewOnlineDeliveryView(@RequestParam String pgmId, HttpServletRequest request, ModelMap model) throws Exception {
		if (request == null) {
			throw new IllegalArgumentException();
		}

		Map<String, Object>	paramMap	=	new HashMap<String, Object>();
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		//-----상품상세정보
		paramMap.put("pgmId", StringUtils.trimToEmpty(pgmId));
		paramMap.put("cfmFg", "");
		paramMap.put("vendorId", request.getParameter("entpCd")); // 업체코드

		NEDMPRO0020VO	newProdDetailInfo	=	nEDMPRO0020Service.selectNewTmpOnlineProductDetailInfo(paramMap);
		List<DataMap> vendorAddrlist = pscmcom0004Service.selectVendorAddrList(paramMap); //출고지, 반품/교환지 주소 조회
		List<DataMap> newOnlineDeliveryList = nEDMPRO0030Service.selectNewOnlineDeliveryList(paramMap);  //업체 배송정보 조회
		List<DataMap> addrMgrList = nEDMPRO0030Service.selectAddrMgrList(paramMap); //업체 주소정보 조회
		DataMap vendorDlvInfo = nEDMPRO0030Service.selectVendorDlvInfo(paramMap);  //공통배송비정보1 조회
		List<DataMap> vendorDeliInfoList = nEDMPRO0030Service.selectVendorDeliInfo(paramMap);  //공통배송비정보2 조회
		
		model.addAttribute("newProdDetailInfo", 	  newProdDetailInfo);
		model.addAttribute("vendorAddrlist", 	  	  vendorAddrlist);
		model.addAttribute("newOnlineDeliveryList",   newOnlineDeliveryList);
		model.addAttribute("vendorDlvInfo",           vendorDlvInfo);
		model.addAttribute("vendorDeliInfoList",      vendorDeliInfoList);
		model.addAttribute("addrMgrList",             addrMgrList);
		model.addAttribute("epcLoginVO", 			  epcLoginVO);

		//20180626 - 업체 공통조건 사용시 무료배송 'N' 처리(주문파트 요청건) 및 업체 배송비 관리 수정
		if (vendorDeliInfoList == null) {
			
			model.addAttribute("vendorDeliInfoYn",   "N");
		} else {
			model.addAttribute("vendorDeliInfoYn",   "Y");
			
		}

		return "edi/product/NEDMPRO0033";
	}

	/**
	 * 온라인전용 상품 배송정보 등록
	 * @param nEDMPRO0020VO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/NEDMPRO0033Save.do")
	public String NewOnlineDeliverySave(HttpServletRequest request, ModelMap model) throws Exception {
		if (request == null) {
			throw new IllegalArgumentException();
		}

		DataMap paramMap = new DataMap(request);
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		paramMap.put("modId",epcLoginVO.getAdminId());

		String pgmId = paramMap.getString("pgmId");
		String entpCd = paramMap.getString("entpCd");

		String deliCondAmt = paramMap.getString("deliCondAmt");
		String deliKindCd = paramMap.getString("deliKindCd");
		String deliAmt = paramMap.getString("deliAmt"+deliKindCd);
		String deliAmt04 = paramMap.getString("deliAmt04");
		String deliAmt05 = paramMap.getString("deliAmt05");
		String deliAmt06 = paramMap.getString("deliAmt06");
		String condUseYn = paramMap.getString("condUseYn");
		if (!"Y".equals(condUseYn)) {
			//상품금액별 차등 배송비가 숫자가 아닐 경우
			if ("01".equals(deliKindCd)) {
				if (!NumberUtil.isNumber(deliCondAmt) || !NumberUtil.isNumber(deliAmt)) {
					throw new IllegalArgumentException();
				}
			} else if ("03".equals(deliKindCd)) { // 고정배송비가 숫자가 아닐 경우
				if (!NumberUtil.isNumber(deliAmt)) {
					throw new IllegalArgumentException();
				}
			}
			//제주/도서간간 배송비가 숫자가 아닐경우
			if ("Y".equals(paramMap.getString("psbtChkYn"))) {
				if (!NumberUtil.isNumber(deliAmt04) || !NumberUtil.isNumber(deliAmt05)) {
					throw new IllegalArgumentException();
				}
			}
			//반품배송비가 숫자가 아닐 경우
			if (!NumberUtil.isNumber(deliAmt06)) {
				throw new IllegalArgumentException();
			}
		}
		
		nEDMPRO0030Service.newOnlineDeliverySave(paramMap);

		return "redirect:/edi/product/NEDMPRO0033.do?pgmId=" + pgmId +"&entpCd="+entpCd+"&mode=save";
	}

	/**
	 * 전상법 템플릿 selectBox 데이터
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/selectNorProdTempSel.do")
	public @ResponseBody Map<String, Object> selectNorProdTempSel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map rtnMap = new HashMap<String, Object>();
		DataMap paramMap = new DataMap(request);

		List<DataMap> list = nEDMPRO0030Service.selectNorProdTempList(paramMap);

		rtnMap.put("resultList", list);

		return rtnMap;
	}

	/**
	 * 전상법 템플릿 VALUES
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/selectNorProdTempVal.do")
	public @ResponseBody Map<String, Object> selectNorProdTempVal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map rtnMap = new HashMap<String, Object>();
		DataMap paramMap = new DataMap(request);

		List<DataMap> list = nEDMPRO0030Service.selectNorProdTempValList(paramMap);

		rtnMap.put("resultList", list);

		return rtnMap;
	}

	/**
	 * 온라인전용 상품 복사
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/NEDMPRO0333.do")
	public String newMakeCopycatProduct(HttpServletRequest request, ModelMap model) throws Exception {
		if (request == null || model == null) {
			throw new IllegalArgumentException();
		}

		DataMap resultMap = new DataMap();

		try {
			resultMap = nEDMPRO0030Service.newMakeCopycatProduct(request);
		} catch (Exception e) {
			logger.error("상품 복사 Exception 발생 ===============================================================================" + e.toString());
		}

		return "redirect:/edi/product/NEDMPRO0030OnlineDetail.do?pgmId=" + resultMap.getString("newPgmId") + "&message=productCopy";
	}

	/**
	 * 업체 거래형태 정보
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/edi/product/trdTypFgSel")
    public ModelAndView trdTypFgSel(HttpServletRequest request) throws Exception {
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		DataMap param = new DataMap();

		param.put("cono", epcLoginVO.getCono());

		List<DataMap> list = nEDMPRO0030Service.selecttrdTypFgSel(param);
		return AjaxJsonModelHelper.create(list);
	}

	/**
	 * EC 표준카테고리 조회 (분류별)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/ecStandardCategory.do")
	public @ResponseBody Map selectEcStandardCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {


		Map rtnMap = new HashMap<String, Object>();
		DataMap paramMap = new DataMap();

		try {

			// 데이터 조회
			String lrgStdCatCd = StringUtils.defaultIfEmpty(request.getParameter("ecStandardLargeCategory"), "");		// 대분류
			String midStdCatCd = StringUtils.defaultIfEmpty(request.getParameter("ecStandardMediumCategory"), "");		// 중분류
			String smlStdCatCd = StringUtils.defaultIfEmpty(request.getParameter("ecStandardSmallCategory"), "");		// 소분류
			/*
			lrgStdCatCd = "BN10000080";		// 대분류
			midStdCatCd = "BN30001270";		// 중분류
			smlStdCatCd = "BN50008970";		// 소분류
			*/
			paramMap.put("depth", "1");
			if(StringUtil.isNotEmpty(lrgStdCatCd)) {
				paramMap.put("lrgStdCatCd", lrgStdCatCd);
				paramMap.put("depth", "2");
			}
			if(StringUtil.isNotEmpty(midStdCatCd)) {
				paramMap.put("midStdCatCd", midStdCatCd);
				paramMap.put("depth", "3");
			}
			if(StringUtil.isNotEmpty(smlStdCatCd)) {
				paramMap.put("smlStdCatCd", smlStdCatCd);
				paramMap.put("depth", "4");
			}

			rtnMap.put("list", nEDMPRO0030Service.selectEcStandardCategory(paramMap));
			rtnMap.put("result", true);
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
		    rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}

	/**
	 * Desc : EC 전시 카테고리 매핑
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/edi/product/ecStandardCategoryMapping.do")
	public @ResponseBody Map selectEcStandardCategoryMapping(HttpServletRequest request) {

		Map rtnMap = new HashMap<String, Object>();
		try {
			String martCatCd = StringUtils.defaultIfEmpty(request.getParameter("martCatCd"), "");

			DataMap ecDisplayCategoryMapping = nEDMPRO0030Service.selectEcStandardCategoryMapping(martCatCd);

			rtnMap.put("data", ecDisplayCategoryMapping);
			rtnMap.put("result", true);

		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
		    rtnMap.put("errMsg", e.getMessage());
		}
		return rtnMap;
	}

	/**
	 * Desc : EC 전시 카테고리 팝업
	 * @param HttpServletRequest
	 * @param model
	 * @return view 페이지
	 */
	@RequestMapping("/edi/product/ecDisplayCategoryPop.do")
	public String onlineDisplayCategoryPop(HttpServletRequest request, Model model) throws Exception {

		DataMap paramMap = new DataMap(request);
		boolean isMart = StringUtils.defaultIfEmpty(request.getParameter("isMart"), "N").equals("Y") ? true : false;
		String[] mallCds = SecureUtil.stripXSS(StringUtils.defaultIfEmpty(request.getParameter("mallCd"), "LTMT")).split(",");
		paramMap.put("mallCds", mallCds);

		List<DataMap> ecDisplayCategoryList = nEDMPRO0030Service.selectEcDisplayCategory(paramMap);

		model.addAttribute("ecDisplayCategoryList", ecDisplayCategoryList);
		model.addAttribute("isMart", isMart);
		return "edi/product/ecDisplayCategoryPop";
	}

	/**
	 * Desc : EC 전시 카테고리 조회 For Grid
	 * @Method Name : makerSearch
	 * @param HttpServletRequest
	 * @param model
	 * @return view 페이지
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/edi/product/ecDisplayCategory.do")
	public HashMap<String,Object> onlineDisplayCategory(HttpServletRequest request, Model model) throws Exception {
		HashMap<String,Object> gridMap = new HashMap<String, Object>();

		int totalCount = 0;		//전체 페이지 카운트
		int totalPage = 1;		//전체 페이지
		
		DataMap paramMap = new DataMap(request);
		String[] mallCds = SecureUtil.stripXSS(StringUtils.defaultIfEmpty(request.getParameter("mallCd"), "LTMT")).split(",");
		paramMap.put("mallCds", mallCds);
		
		List<DataMap> list = null;
		
		//data list
		list = nEDMPRO0030Service.selectEcDisplayCategory(paramMap);
		
		//전체 data count
		totalCount = (list != null && !list.isEmpty())? list.size():0;
		
//		gridMap.put("page", currentPage);		//현재 페이지			-- paging 미사용
		gridMap.put("total", totalPage);		//전체 페이지 수
		gridMap.put("records", totalCount);		//전체 데이터 수
		gridMap.put("list", list);				//데이터 리스트

		return gridMap;
	}


	/**
	 * EC 표준카테고리 조회 (분류별)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/ecProductAttr.do")
	public @ResponseBody Map selectEcProductAttr(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map rtnMap = new HashMap<String, Object>();

		try {
			DataMap paramMap = new DataMap(request);

			rtnMap.put("list", nEDMPRO0030Service.selectEcProductAttr(paramMap));
			rtnMap.put("result", true);
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
		    rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}

}