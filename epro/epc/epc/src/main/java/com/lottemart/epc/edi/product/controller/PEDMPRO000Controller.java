package com.lottemart.epc.edi.product.controller;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeSet;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.lcnjf.util.StringUtil;
import com.lottemart.common.exception.AlertException;
import com.lottemart.common.util.ConfigUtils;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.service.PSCMCOM0004Service;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.comm.model.EdiCommonCode;
import com.lottemart.epc.edi.comm.model.SearchParam;
import com.lottemart.epc.edi.comm.service.PEDPCOM0001Service;
import com.lottemart.epc.edi.product.model.ColorSize;
import com.lottemart.epc.edi.product.model.NEDMPRO0070VO;
import com.lottemart.epc.edi.product.model.NewProduct;
import com.lottemart.epc.edi.product.model.OnlineDisplayCategory;
import com.lottemart.epc.edi.product.model.SearchProduct;
import com.lottemart.epc.edi.product.service.CommonProductService;
import com.lottemart.epc.edi.product.service.NEDMPRO0070Service;
import com.lottemart.epc.edi.product.service.PEDMPRO000Service;

import MarkAny.MaSaferJava.Madec;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import lcn.module.common.util.DateUtil;
import lcn.module.common.views.AjaxJsonModelHelper;
import lcn.module.framework.property.ConfigurationService;
import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;


/**
 * @Class Name : PEDMPRO000Controller
 * @Description : 신상품 등록 Controller 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 12. 03. 오후 1:33:50 kks
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */

@Controller
public class PEDMPRO000Controller extends BaseController{

	@Autowired
	private NEDMPRO0070Service nedmpr00070service;

	// 신상품 등록 관련 서비스
	private PEDMPRO000Service ediProductService;

	// 공통. 브랜드, 팀분류 코드 조회용 서비스
	private PEDPCOM0001Service commService;

	// SCM 온라인 전시 카테고리 검색에서 사용
	private PSCMCOM0004Service PSCMCOM0004Service;

	private static final Logger logger = LoggerFactory.getLogger(PEDMPRO000Controller.class);

	@Autowired
	public void setPSCMCOM0004Service(PSCMCOM0004Service pSCMCOM0004Service) {
		PSCMCOM0004Service = pSCMCOM0004Service;
	}

	@Autowired
	public void setEdiProductService(PEDMPRO000Service ediProductService) {
		this.ediProductService = ediProductService;
	}

	@Autowired
	public void setCommService(PEDPCOM0001Service commService) {
		this.commService = commService;
	}

	@Resource(name="commonProductService")
	private CommonProductService commonProductService;

	@Resource(name = "configurationService")
	private ConfigurationService config;

	@RequestMapping(value = "/edi/product/PEDMPRO0001Baner.do", method = RequestMethod.GET)
	public String outSideUrl(@RequestParam Map<String,Object> map, Locale locale,  ModelMap model) {

		map.put("defPgmID", "/edi/product/NEDMPRO0010.do");
		map.put("pgm_code", "PRO");
		map.put("pgm_sub",  "1");
		model.addAttribute("paramMap",map);
		return "/edi/main/ediIndex";
	}

	/**
	 * Desc : 신상품, 온오프 겸용상품  등록페이지.
	 * @Method Name : newProductRegistPage
	 * @param SearchParam
	 * @param model
	 * @return view 페이지
	 */
	@RequestMapping(value="/edi/product/PEDMPRO0002", method=RequestMethod.GET)
    public String newProductRegistPage(SearchParam searchParam, Model model, HttpServletRequest request) {

		model.addAttribute("teamList", 	  	   commService.selectDistinctTeamList());
		model.addAttribute("l1GroupList", 	   commService.selectL1List(searchParam));
		model.addAttribute("colorList", 	   commService.selectColorList());
		model.addAttribute("sizeCategoryList", commService.selectSizeCategoryList());

		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		model.addAttribute("epcLoginVO", epcLoginVO);

		return "edi/product/PEDMPRO0002";
	}


	/**
	 * Desc : 신상품    온라인전용, 소셜상품  등록페이지.
	 * @Method Name : newProductRegistPageForOnline
	 * @param SearchParam
	 * @param model
	 * @return view 페이지
	 */
	@RequestMapping(value="/edi/product/PEDMPRO000230", method=RequestMethod.GET)
    public String newProductRegistPageForOnline(SearchParam searchParam, Model model, HttpServletRequest request) throws Exception {

		Map<String, Object>	paramMap	=	new HashMap<String, Object>();

		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		model.addAttribute("epcLoginVO", epcLoginVO);
		model.addAttribute("teamList", commonProductService.selectNteamList(paramMap, request)); // 팀리스트
		model.addAttribute("seasonYearList", commonProductService.selectNseasonYear(paramMap)); // 계절년도
		model.addAttribute("nowYear", DateUtil.getToday("yyyy")); // 현재년도
		return "edi/product/NEDMPRO0031";
	}

	/**
	 * Desc : 상품일괄등록 카테고리 조회
	 * @Method Name : PEDMPRO00070
	 * @param searchParam
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value="/edi/product/PEDMPRO00070", method=RequestMethod.GET)
    public String PEDMPRO00070(SearchParam searchParam, Model model, HttpServletRequest request) throws Exception {
		Map<String, Object>	paramMap	=	new HashMap<String, Object>();
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		model.addAttribute("epcLoginVO", epcLoginVO);
		model.addAttribute("teamList",			commonProductService.selectNteamList(paramMap, request));		//팀리스트
	return "edi/product/NEDMPRO0070";
	}

	/**
	 * Desc : 상품일광등록 코드 검색
	 * @Method Name : PEDMPRO0007001
	 * @param searchParam
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value="/edi/product/PEDMPRO0007001", method=RequestMethod.GET)
    public String PEDMPRO0007001(SearchParam searchParam, Model model, HttpServletRequest request) throws Exception {
		Map<String, Object>	paramMap	=	new HashMap<String, Object>();
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		model.addAttribute("epcLoginVO", epcLoginVO);
		model.addAttribute("orderItem",request.getParameter("orderItem"));
		model.addAttribute("category", request.getParameter("category"));
	return "edi/product/NEDMPRO007001";
	}

	/**
	 * Desc : 카테고리 디테일 검색
	 * @Method Name : NEDMPRO007001Detail
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value="/edi/product/NEDMPRO007001Detail.do")
	public @ResponseBody Map NEDMPRO007001Detail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		Map rtnMap = new HashMap<String, Object>();
		List<NEDMPRO0070VO> list = new ArrayList<NEDMPRO0070VO>();
		try {
			DataMap paramMap = new DataMap(request);

			if(paramMap.getString("orderItem").equals("1")){
				paramMap.put("vendorId", epcLoginVO.getRepVendorId());
				list=nedmpr00070service.getPl(paramMap);
			}else if(paramMap.getString("orderItem").equals("2")){
				list=nedmpr00070service.getBo(paramMap);
			}else if (paramMap.getString("orderItem").equals("3")) {
				list = nedmpr00070service.getKc(paramMap);
			}else{
				list  = nedmpr00070service.getCode(paramMap);
			}
			// json
			JSONArray jArray = new JSONArray();
			if (list != null)
				jArray = (JSONArray) JSONSerializer.toJSON(list);

			String jStr = "{Data:" + jArray + "}";
			rtnMap.put("ibsList", jStr);

			// 조회된 데이터가 없는 경우
			if (jArray.isEmpty()) {
				rtnMap.put("result", false);
			}
			rtnMap.put("result", true);
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}

	/**
	 * Desc : 신상품 등록. 상품 기본 정보 , 추가정보 처리.
	 * 		     온라인, 오프라인 pog이미지는 수정페이지에서 업로드
	 * @Method Name : onSubmitNewProduct
 	 * @param NewProduct
 	 * @param HttpServletRequest
 	 * @param Model
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/edi/product/PEDMPRO0002", method = RequestMethod.POST)
	public String onSubmitNewProduct(NewProduct newProduct, HttpServletRequest request, Model model) throws Exception {

		logger.debug("입력된 신상품 필드값 :" + newProduct);
		
		//TO-BE 미사용 메뉴로 판단되지만... 일단 수정
		//250624 Key생성방식 변경 _(구EPC) BOS 온라인상품쪽과 PGM_ID 겹치지 않기 위해, BOS에서 채번된 값을 받아서 사용 
		String newPgmId = ediProductService.selectNewProductCode();		//신상품코드 생성
		//신상품코드 생성 실패 시, exception
		if("".equals(newPgmId)) {
			throw new AlertException("신상품 가등록 코드 생성에 실패하였습니다.");
		}
		newProduct.setNewProductCode(newPgmId); // 상품 저장시 사용되는 new_prod_cd값 시퀀스로 생성

		// pog이미지id값 설정. new_prod_cd값 이용.
		String newProductImageId = StringUtils.substring(newProduct.getNewProductCode(), 2, 8) + StringUtils.right(newProduct.getNewProductCode(), 5);
		newProduct.setProductImageId(newProductImageId);

		String prodPrcMgrYn = newProduct.getProdPrcMgrYn();
		String[] arrItemCd = newProduct.getItemCd();
		String newProductCd = "";

		String chkMinPsbQty = request.getParameter("officialOrder.minimumOrderQuantity");
		String chkMaxPsbQty = request.getParameter("officialOrder.maximumOrderQuantity");
		String returnUrl = null;
		try {

			// - 최소/최대 주문 가능 수량 체크.
			if (chkMinPsbQty != null && !"".equals(chkMinPsbQty) || chkMaxPsbQty != null && !"".equals(chkMaxPsbQty)) {
				int chkMinPsbQty_ = Integer.parseInt(chkMinPsbQty);
				int chkMaxPsbQty_ = Integer.parseInt(chkMaxPsbQty);

				if (chkMinPsbQty_ < 1) {
					throw new AlertException("최소 주문 가능 수량은 0을 입력 할 수 없습니다.");
				}
				if (chkMaxPsbQty_ < 1) {
					throw new AlertException("최대 주문 가능 수량은 0을 입력 할 수 없습니다.");
				}
				if (chkMaxPsbQty_ > 99999) {
					throw new AlertException("최대 주문 가능 수량은 99,999보다 클 수 없습니다.");
				}
				if (chkMaxPsbQty_ < chkMinPsbQty_) {
					throw new AlertException("최대 주문 가능 수량은 최소 주문 가능 수량 보다 크게 입력해주세요.");
				}
			}

			// 단품가격정보 검증 , 상품구분 옵션형 & 옵션 별 별도가격  일때 가격정보 검증
			if ("I".equals(newProduct.getAttrPiType()) && "1".equals(prodPrcMgrYn)) {
				if (arrItemCd != null && arrItemCd.length > 0) {
					int arrayLength = arrItemCd.length;

					for (int i = 0; i < arrayLength; i++) {
						String strAmt = newProduct.getOptnAmt()[i];
						int optnAmt = 0;

						if (strAmt != null && !"".equals(strAmt)) {
							optnAmt = Integer.parseInt(strAmt);

							if (optnAmt <= 0) {
								throw new AlertException("단품 가격을 0원 이상 입력하세요");
							}
						}
					}
				}
			}
			// 단품정보
			newProduct.makeItemObject();

			newProduct.makeEcAttrbuteObject(); // 20200205 ec 상품속성 추가

			// 키워드 입력값 검증
			String[] arrSearchKywrd = newProduct.getSearchKywrd();

			if (arrSearchKywrd != null && arrSearchKywrd.length > 0) {
				int arrayLength = arrSearchKywrd.length;

				for (int i = 0; i < arrayLength; i++) {
					String strSearchKywrd = newProduct.getSearchKywrd()[i];

					if (strSearchKywrd == null || "".equals(strSearchKywrd)) {
						throw new AlertException("키워드 정보가 누락되었습니다.");
					}
				}
			}

			newProduct.makeKeywordObject(); // 상품키워드

			newProductCd = newProduct.getNewProductCode();
			String onlineProdTypeCd = newProduct.getOnlineProdTypeCd();

			// 구매대행 배송예정일
			if ("09".equals(onlineProdTypeCd)) {
				String prodOptionDesc = newProduct.getDeliDueDate();
				newProduct.setProdOptionDesc(prodOptionDesc);
			}

			/* 카테고리 중복 체크 및 제거 */
			if (!StringUtils.isEmpty(newProduct.getOnlineDisplayCategoryCode())) {
				int idx = newProduct.getOnlineDisplayCategoryCode().indexOf(",");

				if (idx > -1) {
					String[] catArr = newProduct.getOnlineDisplayCategoryCode().split(",");

					try {
						newProduct.setOnlineDisplayCategoryCode(catOverLapChk(catArr));
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
				}
			}

			String[] catArr = newProduct.getDispCatCd().split(",");
			ArrayList<String> dispCatArrList = new ArrayList<>();
			for (int i = 0; i < catArr.length; i++) {
				if (!"".equals(catArr[i]) && catArr[i] != null) {
					dispCatArrList.add(catArr[i]);
				}
			}
			if (dispCatArrList.size() < 2) {
				throw new AlertException("EC 롯데ON 전시카테고리 또는 EC 롯데마트몰 전시카테고리가 존재하지 않습니다.");
			}

			if (!StringUtils.isEmpty(newProduct.getDispCatCd())) {
				int idx = newProduct.getDispCatCd().indexOf(",");

				if (idx > -1) {
					try {
						newProduct.setDispCatCd(catOverLapChk(catArr));
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
				}
			}

			String chkSellCdByte = newProduct.getSellCode();
			if (StringUtil.getByteLength(chkSellCdByte) > 15) {
				throw new AlertException("판매코드는 15Byte 이하로 입력해주세요.");
			}

			String chkModelNmByte = request.getParameter("officialOrder.modelName");
			if (StringUtils.isNotEmpty(chkModelNmByte) && StringUtil.getByteLength(chkModelNmByte) > 100) {
				throw new AlertException("모델명은 100Byte 이하로 입력해주세요.");
			}
			
			String chkEntpInProdCd = request.getParameter("entpInProdCd");
			if (StringUtils.isNotEmpty(chkEntpInProdCd) && StringUtil.getByteLength(chkEntpInProdCd) > 20) {
				throw new AlertException("협력사 내부 상품코드는 20Byte 이하로 입력해주세요.");
			}
			
			String chkOnlineProductCode = request.getParameter("onlineProductCode"); 
			if (StringUtils.isNotEmpty(chkOnlineProductCode) && StringUtil.getByteLength(chkOnlineProductCode) > 10) {
				throw new AlertException("온라인 대표 상품코드는 10Byte 이하로 입력해주세요.");
			}
						
			ediProductService.insertProductInfo(newProduct); // 실제 상품 정보 입력.

			// 전자상거래 입력 데이터
			String prodAddCdBf = newProduct.getProdAddCd();
			String prodAddNmBf = newProduct.getProdAddNm();
			String[] tempArrayCd = newProduct.getProdAddCd().split("#//#");
			String[] tempArrayNm = newProduct.getProdAddNm().split("#//#");

			ediProductService.deleteProdAddDetail(newProduct); // INSERT 하기전 모두 삭제

			for (int i = 0; i < tempArrayCd.length; i++) {
				if (tempArrayCd[i] != null && tempArrayCd[i].length() > 0) {

					if (tempArrayNm[i] != null && !"".equals(tempArrayNm[i].trim())) {
						String strTempArrayNm = tempArrayNm[i].trim();

						if (strTempArrayNm == null || "".equals(strTempArrayNm)) {
							throw new AlertException("전상법 정보는 필수 항목입니다.");
						}
						if (StringUtil.getByteLength(strTempArrayNm) > 2000) { // SQL Exception 방지
							throw new AlertException("전상법 정보는 2000Byte 이하로 입력해주세요.");
						}
						newProduct.setProdAddMasterCd(newProduct.getProdAddMasterCd());
						newProduct.setProdAddCd(tempArrayCd[i].trim());
						newProduct.setProdAddNm(strTempArrayNm);
						newProduct.setEntpCode(newProduct.getEntpCode());

						ediProductService.insertProdAddDetail(newProduct);
					}
				}
			}

			// KC 인증마크 입력 데이터
			String prodCertCdBf = newProduct.getProdCertCd();
			String prodCertNmBf = newProduct.getProdCertNm();
			String[] tempArrayCertCd = newProduct.getProdCertCd().split("#//#");
			String[] tempArrayCertNm = newProduct.getProdCertNm().split("#//#");

			ediProductService.deleteProdCertDetail(newProduct); // INSERT 하기전 모두 삭제

			for (int i = 0; i < tempArrayCertCd.length; i++) {
				if (tempArrayCertCd[i] != null && tempArrayCertCd[i].length() > 0) {

					if (tempArrayCertNm[i] != null && !"".equals(tempArrayCertNm[i].trim())) {
						String strTempArrayCertNm = tempArrayCertNm[i].trim();

						if (strTempArrayCertNm == null || "".equals(strTempArrayCertNm)) {
							throw new AlertException("KC 인증마크 정보는 필수 항목입니다.");
						}
						if (StringUtil.getByteLength(strTempArrayCertNm) > 2000) { // SQL Exception 방지
							throw new AlertException("KC 인증마크 정보는 2000Byte 이하로 입력해주세요.");
						}

						newProduct.setProdCertMasterCd(newProduct.getProdCertMasterCd());
						newProduct.setProdCertCd(tempArrayCertCd[i].trim());
						newProduct.setProdCertNm(strTempArrayCertNm);
						newProduct.setEntpCode(newProduct.getEntpCode());

						ediProductService.insertProdCertDetail(newProduct);
					}
				}
			}

			ArrayList<ColorSize> itemList = new ArrayList<ColorSize>();
			newProduct.setItemList(itemList);
			newProduct.setProdAddCd(prodAddCdBf);
			newProduct.setProdAddNm(prodAddNmBf);
			newProduct.setProdCertCd(prodCertCdBf);
			newProduct.setProdCertNm(prodCertNmBf);

			model.addAttribute("newProduct", newProduct);

			// 상품 정보 저장 후, 수정페이지에서 온라인 필수 이미지, 오프라인 pog이미지 업로드
			// 2015.11.27 by kmlee 기본정보가 저장되면, 임시보관함 상세화면으로 연결됨.
			// i-cert에서 작업하면 URL 반영이 필요함.
			//return "redirect:/edi/product/PEDMPRO000301.do?newProductCode="+newProduct.getNewProductCode()+"&mode=basicInfo";

			returnUrl = "redirect:/edi/product/NEDMPRO0030OnlineDetail.do?pgmId=" + newProductCd + "&mode=basic";
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			returnUrl = "redirect:/edi/product/NEDMPRO0030OnlineDetail.do";
			throw new AlertException(e.getMessage(), e.getCause());
		}

		return returnUrl;
	}

	/**
	 * Desc : 상품등록시 사용자가 선택한 협력업체의 거래중지여부, 거래형태 값, 과세 구분값 조회
	 * @Method Name : checkCountVendorStopTrading
	 * @param HttpServletRequest
	 * @return ModelAndView
	 */
	@RequestMapping("/edi/product/checkCountVendorStopTrading")
	public ModelAndView checkCountVendorStopTrading(HttpServletRequest request) {

		String vendorCode = request.getParameter("vendorCode");
		SearchParam searchParam = ediProductService.selectCountVendorStopTrading(vendorCode);
		return AjaxJsonModelHelper.create(searchParam);
	}

	/**
	 * Desc : 신상품 등록페이지시 사용되는 브랜드 조회 페이지
	 * @Method Name : brandPopup
	 * @param SearchParam
	 * @param model
	 * @return view 페이지
	 */
	@RequestMapping(value = "/edi/product/brand", method = RequestMethod.GET)
	public String brandPopup(SearchParam searchParam, Model model) {

		return "edi/comm/brand";
	}

	/**
	 * Desc : 브랜드 검색
	 * @Method Name : brandPopup
	 * @param HttpServletRequest
	 * @param model
	 * @return view 페이지
	 */
	@RequestMapping(value = "/edi/product/brand", method = RequestMethod.POST)
	public String brandSearch(HttpServletRequest request, Model model) {
		String brandName = request.getParameter("brandName");

		//사용자가 입력한 브랜드 명으로 검색
		if (StringUtils.isNotEmpty(brandName))
			model.addAttribute("brandList", commService.selectBrandList(brandName));

		return "edi/comm/brand";
	}

	/**
	 * Desc : 메이커 팝업 페이지
	 * @Method Name : makerPopup
	 * @param SearchParam
	 * @param model
	 * @return view 페이지
	 */
	@RequestMapping(value = "/edi/product/maker", method = RequestMethod.GET)
	public String makerPopup(SearchParam searchParam, Model model) {
		return "edi/comm/maker";
	}

	/**
	 * Desc : 메이커 검색
	 * @Method Name : makerSearch
	 * @param HttpServletRequest
	 * @param model
	 * @return view 페이지
	 */
	@RequestMapping(value = "/edi/product/maker", method = RequestMethod.POST)
	public String makerSearch(HttpServletRequest request, Model model) {
		String makerName = request.getParameter("makerName");

		if (StringUtils.isNotEmpty(makerName))
			model.addAttribute("makerList", commService.selectMakerList(makerName));
		return "edi/comm/maker";
	}

	/**
	 * Desc : 온라인 전시 카테고리 검색
	 * @Method Name : makerSearch
	 * @param HttpServletRequest
	 * @param model
	 * @return view 페이지
	 */
	@RequestMapping("/edi/product/onlineDisplayCategory")
	public String onlineDisplayCategorySearch(HttpServletRequest request, SearchParam searchParam, Model model) throws Exception {

		DataMap paramMap = new DataMap(request);
		String categoryIdDepth2 = StringUtils.defaultString(request.getParameter("categoryIdDepth2"), "");

		List<OnlineDisplayCategory> onlineDisplayCategoryList = commService.selectOnlineDisplayCategoryList(categoryIdDepth2);

		if ("1".equals(paramMap.getString("closeFlag"))) {
			paramMap.put("categoryIdDepth2", categoryIdDepth2);
			onlineDisplayCategoryList = commService.selectNewOnlineDisplayCategoryList(paramMap);
		}

		model.addAttribute("selectCategoryListDepth2", PSCMCOM0004Service.selectCategoryListDepth2("01"));
		model.addAttribute("onlineDisplayCategoryList", onlineDisplayCategoryList);

		// 코리안넷을 통해 온오프 상품 등록 시 전시카테고리 호출 url수정 (인터셉터에서 세션 문제때문에)
		String koreanNetFlag = StringUtils.defaultString(request.getParameter("koreanNet"), "");
		if (koreanNetFlag.equals("1")) {
			return "edi/product/onlineDisplayCategoryKoreanNet";
		}

		return "edi/comm/onlineDisplayCategory";
	}

	/**
	 * Desc : 온라인 전시 카테고리 검색
	 * @Method Name : makerSearch
	 * @param HttpServletRequest
	 * @param model
	 * @return view 페이지
	 */
	@RequestMapping(value = "edi/product/onlineDisplayAllCategory")
	public @ResponseBody Map onlineDisplayAllCategory(HttpServletRequest request, SearchParam searchParam, Model model) throws Exception {
		Map rtnMap = new HashMap<String, Object>();
		try {
			DataMap paramMap = new DataMap(request);
			List<OnlineDisplayCategory> list = commService.selectNewOnlineDisplayAllCategoryList(paramMap);

			// json
			JSONArray jArray = new JSONArray();
			if (list != null)
				jArray = (JSONArray) JSONSerializer.toJSON(list);

			String jStr = "{Data:" + jArray + "}";
			rtnMap.put("ibsList", jStr);

			// 조회된 데이터가 없는 경우
			if (jArray.isEmpty()) {
				rtnMap.put("result", false);
			}
			rtnMap.put("result", true);
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}

	/**
	 * Desc : 패션상품 등록에 사용되는 사이즈 분류 목록 조회. ajax방식으로 전달됨.
	 * @Method Name : getSizeByCategory
	 * @param HttpServletRequest
	 * @return ModelAndView
	 */
	/*
	@RequestMapping("/edi/product/sizeByCategory")
    public ModelAndView getSizeByCategory(HttpServletRequest request) {
		String sizeCategoryCode = request.getParameter("sizeCategoryCode");
		List<EdiCommonCode> resultSizeList = commService.selectSizeList(sizeCategoryCode);
		return AjaxJsonModelHelper.create(resultSizeList);
	}
	*/

	/**
	 * Desc : 패션상품 등록에 사용되는 사이즈 분류 목록 조회. ajax방식으로 전달됨.
	 * @Method Name : getSizeByCategory
	 * @param HttpServletRequest
	 * @return ModelAndView
	 */
	@RequestMapping("/edi/product/selectSeasonList")
	public ModelAndView getSeasonList(HttpServletRequest request) {
		String productDivnCode = request.getParameter("productDivnCode");
		List<EdiCommonCode> resultSizeList = commService.selectSeasonTypeList(productDivnCode);

		return AjaxJsonModelHelper.create(resultSizeList);
	}

	/**
	 * Desc : 사용자가 선택한 팀에 해당하는 대분류 목록 조회
	 * @Method Name : getL1List
	 * @param SearchParam
 	 * @param HttpServletRequest
	 * @return ModelAndView
	 */
	@RequestMapping("/edi/product/selectL1List")
	public ModelAndView getL1List(SearchParam searchParam, HttpServletRequest request) {
		List<EdiCommonCode> resultL1List = commService.selectL1List(searchParam);
		return AjaxJsonModelHelper.create(resultL1List);
	}

	/**
	 * Desc : 대분류 에 해당하는 하위 세분류 목록 조회
	 * @Method Name : getL4List
	 * @param SearchParam
 	 * @param HttpServletRequest
	 * @return ModelAndView
	 */
	@RequestMapping("/edi/product/selectL4List")
	public ModelAndView getL4List(SearchParam searchParam, HttpServletRequest request) {
		List<EdiCommonCode> resultL4List = commService.selectL4ListAjax(searchParam);
		return AjaxJsonModelHelper.create(resultL4List);
	}

	/**
	 * Desc : 선택한 온라인 하위 카테고리에  해당하는 하위 세분류 목록 조회
	 * @Method Name : getL4ListForOnline
	 * @param SearchParam
 	 * @param HttpServletRequest
	 * @return ModelAndView
	 */
	@RequestMapping("/edi/product/selectL4ListForOnline")
	public ModelAndView getL4ListForOnline(SearchParam searchParam, HttpServletRequest request) {
		List<EdiCommonCode> resultL4List = commService.selectL4ListForOnlineAjax(searchParam);
		return AjaxJsonModelHelper.create(resultL4List);
	}

	/**
	 * Desc : 해당 협력업체 코드의 온라인 대표 상품코드, 대표상품명, 이익률, 판매코드 조회
	 * @Method Name : getOnlineRepresentProduct
 	 * @param HttpServletRequest
	 * @return ModelAndView
	 */
	@RequestMapping("/edi/product/onlineRepresentProduct")
	public ModelAndView getOnlineRepresentProduct(HttpServletRequest request) {
		String entpCode = request.getParameter("entpCode");
		List<NewProduct> onlineRepresentProductList = ediProductService.selectOnlineRepresentProductList(entpCode);
		return AjaxJsonModelHelper.create(onlineRepresentProductList);
	}

	/**
	 * Desc : 전자 상거래 상품 Template 전달. ajax방식으로 전달됨.
	 * @Method Name : getSizeByCategory
	 * @param HttpServletRequest
	 * @return ModelAndView
	 */
	@RequestMapping("/edi/product/selectProdAddTemplateList")
	public ModelAndView selectProdAddTemplateList(HttpServletRequest request) {
		SearchParam searchParam = new SearchParam();
		searchParam.setFlag(request.getParameter("flag"));
		searchParam.setCatCd(request.getParameter("lCode"));
		searchParam.setInfoGrpCd(request.getParameter("infoGrpCd"));

		List<EdiCommonCode> resultList = commService.selectProdAddTemplateList(searchParam);

		return AjaxJsonModelHelper.create(resultList);
	}

	/**
	 * Desc : KC 인증마크 Template 전달. ajax방식으로 전달됨.
	 * @Method Name : getSizeByCategory
	 * @param HttpServletRequest
	 * @return ModelAndView
	 */
	@RequestMapping("/edi/product/selectProdCertTemplateList")
	public ModelAndView selectProdCertTemplateList(HttpServletRequest request) {

		SearchParam searchParam = new SearchParam();
		searchParam.setFlag(request.getParameter("flag"));
		searchParam.setCatCd(request.getParameter("lCode"));
		searchParam.setInfoGrpCd(request.getParameter("infoGrpCd"));

		List<EdiCommonCode> resultList = commService.selectProdCertTemplateList(searchParam);

		return AjaxJsonModelHelper.create(resultList);
	}

	/**
	 * Desc : 전자 상거래 상품 Template 전달. ajax방식으로 전달됨.
	 * @Method Name : getSizeByCategory
	 * @param HttpServletRequest
	 * @return ModelAndView
	 */
	@RequestMapping("/edi/product/selectProdAddTemplateUpdateList")
	public ModelAndView selectProdAddTemplateUpdateList(HttpServletRequest request) {

		SearchParam searchParam = new SearchParam();
		searchParam.setNewProductCode(request.getParameter("newProdCd"));
		searchParam.setFlag(request.getParameter("flag"));
		searchParam.setCatCd(request.getParameter("lCode"));
		searchParam.setInfoGrpCd(request.getParameter("infoGrpCd"));

		List<EdiCommonCode> resultList = commService.selectProdAddTemplateUpdateList(searchParam);

		return AjaxJsonModelHelper.create(resultList);
	}

	/**
	 * Desc : 전자 상거래 상품 Template 전달. ajax방식으로 전달됨.
	 * @Method Name : getSizeByCategory
	 * @param HttpServletRequest
	 * @return ModelAndView
	 */
	@RequestMapping("/edi/product/selectProdAddTemplateDetailList")
	public ModelAndView selectProdAddTemplateDetailList(HttpServletRequest request) {

		SearchParam searchParam = new SearchParam();
		//searchParam.setFlag(request.getParameter("flag"));
		//searchParam.setCatCd(request.getParameter("lCode"));
		searchParam.setInfoGrpCd(request.getParameter("infoGrpCd"));

		List<EdiCommonCode> resultDetailList = commService.selectProdAddTemplateDetailList(searchParam);

		return AjaxJsonModelHelper.create(resultDetailList);
	}

	/**
	 * Desc : KC 인증마크 상품 Template 전달. ajax방식으로 전달됨.
	 * @Method Name : getSizeByCategory
	 * @param HttpServletRequest
	 * @return ModelAndView
	 */
	@RequestMapping("/edi/product/selectProdCertTemplateDetailList")
	public ModelAndView selectProdCertTemplateDetailList(HttpServletRequest request) {

		SearchParam searchParam = new SearchParam();
		//searchParam.setFlag(request.getParameter("flag"));
		//searchParam.setCatCd(request.getParameter("lCode"));
		searchParam.setInfoGrpCd(request.getParameter("infoGrpCd"));

		List<EdiCommonCode> resultDetailList = commService.selectProdCertTemplateDetailList(searchParam);

		return AjaxJsonModelHelper.create(resultDetailList);
	}

	/**
	 * Desc : 전자 상거래 상품 Template 전달. ajax방식으로 전달됨.
	 * @Method Name : getSizeByCategory
	 * @param HttpServletRequest
	 * @return ModelAndView
	 */
	@RequestMapping("/edi/product/selectProdAddTemplateUpdateDetailList")
	public ModelAndView selectProdAddTemplateUpdateDetailList(HttpServletRequest request) {

		SearchParam searchParam = new SearchParam();
		//searchParam.setFlag(request.getParameter("flag"));
		//searchParam.setCatCd(request.getParameter("lCode"));
		searchParam.setNewProductCode(request.getParameter("newProdCd"));
		searchParam.setInfoGrpCd(request.getParameter("infoGrpCd"));

		List<EdiCommonCode> resultDetailList = commService.selectProdAddTemplateUpdateDetailList(searchParam);

		return AjaxJsonModelHelper.create(resultDetailList);
	}

	/**
	 * Desc : KC 인증마크 상품 Template 전달. ajax방식으로 전달됨.
	 * @Method Name : getSizeByCategory
	 * @param HttpServletRequest
	 * @return ModelAndView
	 */
	@RequestMapping("/edi/product/selectProdCertTemplateUpdateDetailList")
	public ModelAndView selectProdCertTemplateUpdateDetailList(HttpServletRequest request) {

		SearchParam searchParam = new SearchParam();
		//searchParam.setFlag(request.getParameter("flag"));
		//searchParam.setCatCd(request.getParameter("lCode"));
		searchParam.setNewProductCode(request.getParameter("newProdCd"));
		searchParam.setInfoGrpCd(request.getParameter("infoGrpCd"));

		List<EdiCommonCode> resultDetailList = commService.selectProdCertTemplateUpdateDetailList(searchParam);

		return AjaxJsonModelHelper.create(resultDetailList);
	}

	/**
	 * 구 EDI 신규상품등록현황 조회
	 * @param searchProduct
	 * @param model
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/product/PEDMPRO0001")
	public String newProductList(SearchProduct searchProduct, Model model, ModelMap modelMap, HttpServletRequest request) {

		Map<String, String> hmap = new HashMap();
		String nowDate = DateUtil.getToday("yyyy-MM-dd");
		String nextDate = DateUtil.formatDate(DateUtil.addDay(nowDate, 1), "-");

		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		searchProduct.setVenCds(epcLoginVO.getVendorId());
		hmap.put("repVendorId", StringUtils.defaultIfEmpty(epcLoginVO.getRepVendorId(), ""));
		hmap.put("vendorTypeCd", StringUtils.defaultIfEmpty(epcLoginVO.getVendorTypeCd(), ""));

		//필수검색 조건이 있을경우 조회 실행
		if (StringUtils.isNotEmpty(searchProduct.getStartDate())) {
			List<NewProduct> newProductList = ediProductService.selectNewProductListOld(searchProduct);

			model.addAttribute("newProductList", newProductList);

			//규격(1), 패션(5) 구분
			hmap.put("proCode", searchProduct.getProductDivnCode());
			//온오프겸용(0), 온라인전용(1)
			hmap.put("onOff", searchProduct.getOnOffDivnCode());
			hmap.put("startDate", searchProduct.getStartDate());
			hmap.put("endDate", searchProduct.getEndDate());
			modelMap.addAttribute("paramMap", hmap);
		} else {
			hmap.put("startDate", nowDate);
			hmap.put("endDate", nextDate);
			modelMap.addAttribute("paramMap", hmap);
		}

		return "edi/product/PEDMPRO0001";
	}

	/**
	 * 단품정보 일괄등록 양식 받기
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/edi/product/itemExcelDown.do")
	public void itemExcelDown(HttpServletRequest request, HttpServletResponse response) {
		String userAgent = null;
		String optionVal = null;
		String fileNmVal = null;
		String fileName = null;
		ServletOutputStream sos = null;

		try {
			response.setContentType("application/x-msdownload;charset=UTF-8");

			userAgent = request.getHeader("User-Agent");
			optionVal = request.getParameter("optionVal");
			fileNmVal = URLDecoder.decode(request.getParameter("fileName"), "UTF-8");
			fileName = fileNmVal + "_양식.xls";

			fileName = URLEncoder.encode(fileName, "UTF-8");

			if (userAgent.indexOf("MSIE 5.5") > -1) {
				response.setHeader("Content-Disposition", "filename=" + fileName + ";");
			} else {
				response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ";");
			}

			String[] newHeaders = new String[1];
			int headerHeight = 1000;

			if ("0".equals(optionVal)) {
				String[] headers = { "* 옵션설명", "* 재고여부 (Y,N)", "* 재고수량 (숫자만 입력)" };
				newHeaders = headers;
			} else if ("1".equals(optionVal)) {
				String[] headers = { "* 옵션설명", "* 재고여부 (Y,N)", "* 재고수량 (숫자만 입력)", "* 가격 (숫자만 입력)" };
				newHeaders = headers;
			}

			int headerLength = newHeaders.length;

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet(fileNmVal + " 양식");

			HSSFRow header = sheet.createRow(0);
			HSSFCellStyle styleHd = workbook.createCellStyle();
			HSSFCellStyle styleHd2 = workbook.createCellStyle();
			HSSFCellStyle styleRow = workbook.createCellStyle();
			HSSFFont font = workbook.createFont();
			HSSFFont wrnfont = workbook.createFont();
			HSSFCell cell = null;

			sheet.addMergedRegion(new Region((int) 0, (short) 0, (int) 0, (short) 20));

			font.setFontHeight((short) 200);
			styleHd.setFont(font);
			styleHd.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			styleHd.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			styleHd.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
			styleHd.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			wrnfont.setFontHeight((short) 170);
			wrnfont.setColor(wrnfont.COLOR_RED);
			styleHd2.setFont(wrnfont);
			styleHd2.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			styleHd2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			styleHd2.setWrapText(true);

			styleRow.setWrapText(true);
			styleRow.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			styleRow.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);

			String infoVal = "※ * 표시된 항목은 필수 입력항목입니다. 또한 입력 형식이 맞지 않을 경우 일괄등록이 되지 않으니 유의하시기 바랍니다.";

			header.createCell(0).setCellValue(infoVal);
			header.getCell(0).setCellStyle(styleHd2);
			header.setHeight((short) headerHeight);

			HSSFRow header2 = sheet.createRow(1);

			for (int i = 0; i < headerLength; i++) {
				cell = header2.createCell(i);
				cell.setCellValue(newHeaders[i]);
				cell.setCellStyle(styleHd);

				if (newHeaders[i].length() < 9) {
					sheet.setColumnWidth(i, 4000);
				} else {
					sheet.setColumnWidth(i, 7000);
				}
			}

			DataFormat format = workbook.createDataFormat();
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setDataFormat(format.getFormat("@"));

			Row rows = sheet.getRow(1);
			int cellCnt = (int) rows.getLastCellNum();

			for (int j = 0; j < cellCnt; j++) {
				sheet.setDefaultColumnStyle(j, cellStyle);
			}

			sos = response.getOutputStream();

			workbook.write(sos);
			sos.flush();
		} catch (UnsupportedEncodingException e) {
			logger.error("itemExcelDown Error Message 1 (UnsupportedEncodingException) : " + e.getMessage());
		} catch (IOException e) {
			logger.error("itemExcelDown Error Message 2 (IOException) : " + e.getMessage());
		} catch (Exception e) {
			logger.error("itemExcelDown Error Message 3 (Exception) : " + e.getMessage());
		} finally {
			if (sos != null) {
				try {
					sos.close();
				} catch (IOException e) {
					logger.error("itemExcelDown Error Message 4 (IOException) : " + e.getMessage());
				}
			}
		}
	}

	/**
	 * 단품정보 일괄등록
	 * @param request
	 * @param file
	 * @param optionVal
	 * @return List<Map<String, Object>>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/itemExcelUpload.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody List<Map<String, Object>> itemExcelUpload(HttpServletRequest request, @RequestParam("file") MultipartFile file, @RequestParam("optionVal") String optionVal) throws Exception {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		String filePath = null;
		FileOutputStream fos = null;
		File saveFile = null;
		FileInputStream fis = null;

		try {
			filePath = ConfigUtils.getString("edi.weborder.excel.path") + "/" + file.getOriginalFilename();

			byte fileData[] = file.getBytes();

			fos = new FileOutputStream(filePath);
			fos.write(fileData);

			saveFile = new File(filePath);

			fis = new FileInputStream(new File(filePath));

			Workbook workbook = Workbook.getWorkbook(fis);
			Sheet sheet = workbook.getSheet(0);
			int totalColumns = sheet.getColumns();
			int totalRows = sheet.getRows();

			for (int r = 0; r < totalRows; r++) {
				Map<String, Object> map = new HashMap<String, Object>();

				for (int c = 0; c < totalColumns; c++) {
					String cellData = sheet.getCell(c, r).getContents();

					if (r > 1) {
						if (c == 0) {
							map.put("OPTN_DESC", cellData);
						} else if (c == 1) {
							map.put("STK_MGR_YN", cellData);
						} else if (c == 2) {
							map.put("RSERV_STK_QTY", cellData);
						} else if (c == 3 && optionVal.equals("1")) {
							map.put("OPTN_AMT", cellData);
						}
					}
				}

				if (r > 1) {
					resultList.add(map);
				}
			}
		} catch (FileNotFoundException e) {
			logger.error("itemExcelUpload Error Message 1 (FileNotFoundException) : " + e.getMessage());
		} catch (IOException e) {
			logger.error("itemExcelUpload Error Message 2 (IOException) : " + e.getMessage());
		} catch (BiffException e) {
			logger.error("itemExcelUpload Error Message 3 (BiffException) : " + e.getMessage());
		} catch (Exception e) {
			logger.error("itemExcelUpload Error Message 4 (Exception) : " + e.getMessage());
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.error("itemExcelUpload Error Message 5 (IOException) : " + e.getMessage());
				}
			}

			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					logger.error("itemExcelUpload Error Message 6 (IOException) : " + e.getMessage());
				}
			}
			if (saveFile != null) {
				if (saveFile.exists() && saveFile.isFile()) {
					saveFile.delete();
				}
			}
		}

		return resultList;
	}

	/**
	 * DRM 해제 후 일반 엑셀 파일로 변환
	 * @param sourcePath
	 * @param targetPath
	 * @return DataMap
	 */
	public DataMap decryptDrmFileEdi(String sourcePath, String targetPath) {
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		DataMap dataMap = new DataMap();
		Madec clMadec = null;

		try {
			File soruceFile = new File(sourcePath); //업로드 된 파일로 DRM해제 작업
			File targetFile = new File(targetPath); //DRM해제 될 파일 경로

			long sourceFileLength = soruceFile.length();

			if (sourceFileLength == 0) {
				dataMap.put("result", "false");
				dataMap.put("resultCode", "22222");
				dataMap.put("message", "원본손상파일 또는 존재하지 않음");
				return dataMap;
			}

			in = new BufferedInputStream(new FileInputStream(soruceFile));
			out = new BufferedOutputStream(new FileOutputStream(targetFile));

			clMadec = new Madec(config.getString("markany.drm.dat.path"));

			long targetFileLength = clMadec.lGetDecryptFileSize(sourcePath, sourceFileLength, in);

			if (targetFileLength > 0) {
				String strRetCode = clMadec.strMadec(out);
				dataMap.put("result", "true");
				dataMap.put("resultCode", strRetCode);
				dataMap.put("message", "정상 변환");
			} else {
				dataMap.put("result", "false");
				dataMap.put("resultCode", "11111");
				dataMap.put("message", "DRM 파일 아님");
			}
		} catch (IOException e) {
			logger.error("decryptDrmFileEdi Error Message 1 (IOException) : " + e.getMessage());
			dataMap.put("result", "false");
			dataMap.put("resultCode", "33333");
			dataMap.put("message", "IOException 발생");
		} catch (Exception e) {
			logger.error("decryptDrmFileEdi Error Message 2 (Exception) : " + e.getMessage());
			dataMap.put("result", "false");
			dataMap.put("resultCode", "33333");
			dataMap.put("message", "Exception 발생");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("decryptDrmFileEdi Error Message 3 (IOException) : " + e.getMessage());
				}
			}

			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					logger.error("decryptDrmFileEdi Error Message 4 (IOException) : " + e.getMessage());
				}
			}
		}

		return dataMap;
	}

	// 20181011 - 엑셀일괄업로드 개선
	/**
	 * Desc : 신상품    온라인전용, 소셜상품  등록페이지.
	 * @Method Name : newProductRegistPageForOnline
	 * @param SearchParam
	 * @param model
	 * @return view 페이지
	 */
	@RequestMapping(value = "/edi/product/PEDMPRO000230New", method = RequestMethod.GET)
	public String newProductRegistPageForOnlineNew(SearchParam searchParam, Model model, HttpServletRequest request) throws Exception {
		// model.addAttribute("colorList", 	   commService.selectColorList());
		// model.addAttribute("sizeCategoryList", commService.selectSizeCategoryList());

		Map<String, Object> paramMap = new HashMap<String, Object>();

		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		model.addAttribute("epcLoginVO", epcLoginVO);
		model.addAttribute("teamList", commonProductService.selectNteamList(paramMap, request)); //팀리스트
		model.addAttribute("seasonYearList", commonProductService.selectNseasonYear(paramMap)); //계절년도
		model.addAttribute("nowYear", DateUtil.getToday("yyyy")); //현재년도

		// return "edi/product/PEDMPRO000230";
		return "edi/product/NEDMPRO0031_NEW";
	}

	/**
	 * 단품정보 일괄등록
	 * @param request
	 * @param file
	 * @param optionVal
	 * @return List<Map<String, Object>>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/itemExcelUploadNew.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody List<Map<String, Object>> itemExcelUploadNew(HttpServletRequest request, @RequestParam("file") MultipartFile file, @RequestParam("optionVal") String optionVal) throws Exception {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		String filePath = null;
		FileOutputStream fos = null;
		File saveFile = null;
		FileInputStream fis = null;
		File targetFile = null;
		String targetPath = null;

		try {
			filePath = ConfigUtils.getString("edi.weborder.excel.path") + file.getOriginalFilename();

			byte fileData[] = file.getBytes();

			fos = new FileOutputStream(filePath);
			fos.write(fileData);

			saveFile = new File(filePath);

			targetPath = ConfigUtils.getString("edi.weborder.excel.path") + "decrypt_" + file.getOriginalFilename();

			DataMap dataMap = this.decryptDrmFileEdi(filePath, targetPath);

			//00000 : 정상 변환, 11111 : DRM 파일 아님, 22222 : 원본손상파일 또는 존재하지 않음, 33333 : Exception 발생
			logger.debug("itemExcelUpload decryptDrmFile result : " + dataMap.getString("result"));
			logger.debug("itemExcelUpload decryptDrmFile resultCode : " + dataMap.getString("resultCode"));
			logger.debug("itemExcelUpload decryptDrmFile message : " + dataMap.getString("message"));

			targetFile = new File(targetPath);

			//DRM 파일이 아닌 경우 원본 파일 적용
			if (dataMap.getString("result").equals("false") && dataMap.getString("resultCode").equals("11111")) {
				logger.debug("일반 엑셀 파일 : " + dataMap.getString("message"));
				fis = new FileInputStream(filePath);
			} else {
				logger.debug("DRM 엑셀 파일 : " + dataMap.getString("message"));
				fis = new FileInputStream(targetFile);
			}

			Workbook workbook = Workbook.getWorkbook(fis);
			Sheet sheet = workbook.getSheet(0);
			int totalColumns = sheet.getColumns();
			int totalRows = sheet.getRows();

			for (int r = 0; r < totalRows; r++) {
				Map<String, Object> map = new HashMap<String, Object>();

				for (int c = 0; c < totalColumns; c++) {
					String cellData = sheet.getCell(c, r).getContents();

					if (r > 1) {
						if (c == 0) {
							map.put("OPTN_DESC", cellData);
						} else if (c == 1) {
							map.put("STK_MGR_YN", cellData);
						} else if (c == 2) {
							map.put("RSERV_STK_QTY", cellData);
						} else if (c == 3 && optionVal.equals("1")) {
							map.put("OPTN_AMT", cellData);
						}
					}
				}

				if (r > 1) {
					resultList.add(map);
				}
			}
		} catch (FileNotFoundException e) {
			logger.error("itemExcelUpload Error Message 1 (FileNotFoundException) : " + e.getMessage());
		} catch (IOException e) {
			logger.error("itemExcelUpload Error Message 2 (IOException) : " + e.getMessage());
		} catch (BiffException e) {
			logger.error("itemExcelUpload Error Message 3 (BiffException) : " + e.getMessage());
		} catch (Exception e) {
			logger.error("itemExcelUpload Error Message 4 (Exception) : " + e.getMessage());
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.error("itemExcelUpload Error Message 5 (IOException) : " + e.getMessage());
				}
			}

			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					logger.error("itemExcelUpload Error Message 6 (IOException) : " + e.getMessage());
				}
			}

			if(saveFile != null) {
				if (saveFile.exists() && saveFile.isFile()) {
					saveFile.delete();
				}
			}

			if(targetFile != null) {
				if (targetFile.exists() && targetFile.isFile()) {
					targetFile.delete();
				}
			}
		}

		return resultList;
	}
	// 20181011 - 엑셀일괄업로드 개선

	/*
	 * 카테고리 중복 체크
	 */
	private String catOverLapChk(String[] strChk) throws Exception {

		String strCat = "";
		TreeSet<String> treeSet = new TreeSet<String>();

		try {
			for (int i = 0; i < strChk.length; i++) {
				treeSet.add(strChk[i].trim());
			}

			ArrayList<String> arrCat = new ArrayList<String>(treeSet);
			for (int i = 0; i < arrCat.size(); i++) {
				if (i == 0)
					strCat = arrCat.get(i);
				else
					strCat += "," + arrCat.get(i);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return strCat;
	}

}