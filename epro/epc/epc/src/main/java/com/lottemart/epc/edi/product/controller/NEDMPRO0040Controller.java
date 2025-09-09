package com.lottemart.epc.edi.product.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.common.exception.TopLevelException;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.controller.BaseController;
//import com.lottemart.epc.edi.comm.service.PEDPCOM0001Service;
import com.lottemart.epc.edi.product.model.NEDMPRO0040VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0042VO;
//import com.lottemart.epc.edi.product.service.CommonProductService;
import com.lottemart.epc.edi.product.service.NEDMPRO0040Service;

import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.ConfigurationService;

/**
 * @Class Name : NEDMPRO0040Controller
 * @Description : 임시보관함 Controller
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2015.11.27 	SONG MIN KYO	최초생성
 * </pre>
 */
@Controller
public class NEDMPRO0040Controller extends BaseController{

	//private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0040Controller.class);

	@Resource(name = "configurationService")
	private ConfigurationService config;

	@Resource(name = "nEDMPRO0040Service")
	private NEDMPRO0040Service nEDMPRO0040Service;

	// @Resource(name = "commonProductService")
	// private CommonProductService commonProductService;

	// @Resource(name="PEDPCOM0001Service")
	// private PEDPCOM0001Service commService;

	/**
	 * 임시보관함 LIST 화면 FORM 호출
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/product/NEDMPRO0040.do")
	public String selectImsiProductFormInit(ModelMap model, HttpServletRequest request) throws Exception {

		if (model == null || request == null) {
			throw new TopLevelException("");
		}

		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		model.addAttribute("epcLoginVO", 	epcLoginVO);
		// model.addAttribute("srchStartDt", DateUtil.getToday("yyyy-MM-dd")); // 검색시작일
		model.addAttribute("srchStartDt",DateUtil.formatDate(DateUtil.addDay(DateUtil.getToday("yyyy-MM-dd"), -1), "-")); // 검색시작일
		// model.addAttribute("srchEndDt", DateUtil.formatDate(DateUtil.addDay(DateUtil.getToday("yyyy-MM-dd"), 1), "-")); // 검색종료일
		model.addAttribute("srchEndDt", DateUtil.getToday("yyyy-MM-dd")); // 검색종료일
		model.addAttribute("venCds", epcLoginVO.getVendorId()); // 협력업체 코드리스트

		return "edi/product/NEDMPRO0040";
	}

	/**
	 * 임시보관함 LIST 조회
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/selectImsiProductList.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> selectImsiProductList(@RequestBody Map<String, Object> paramMap, HttpServletRequest request) throws Exception {

		if (request == null) {
			throw new TopLevelException("");
		}

		Map<String, Object> rsMap = new HashMap<String, Object>();

		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		paramMap.put("venCds", epcLoginVO.getVendorId());

		List<NEDMPRO0040VO> rsList = nEDMPRO0040Service.selectImsiProductList(paramMap);

		rsMap.put("rsList", rsList);

		//20180604-EDI 임시보관함 확정시 5개에서 20개로 변경 (온라인전용 및 소셜 상품만 적용) 하기 위한 구분값 설정 (온오프상품의 한번에 5개 이상은 RFC 요청이 안되는 조건은 동일)
//		rsMap.put("rsOnOffDivnCode", paramMap.get("srchOnOffDivnCode"));

		return rsMap;
	}

	/**
	 * 임시보관함 판매코드 조회 [패션상품]
	 * @param paramMapO
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/viewImsiProdSellCodePop.do")
	public String selectNewSrcmkCd(@RequestParam Map<String, Object> paramMap, ModelMap model, HttpServletRequest request) throws Exception {
		List<NEDMPRO0042VO> colorSize = nEDMPRO0040Service.selectNewSrcmkCd(paramMap, request);
		model.addAttribute("colorSizeList", colorSize);

		return "edi/product/NEDMPRO0040SellCodePop";
	}

	/**
	 * 임시보관함 선택상품 삭제
	 * @param nEDMPRO0040VO
	 * @param reuqest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/deleteImsiProductList.json",	method=RequestMethod.POST,	headers="Accept=application/json")
	public @ResponseBody Map<String, Object> deleteImsiProductList(@RequestBody NEDMPRO0040VO nEDMPRO0040VO, HttpServletRequest reuqest) throws Exception {
		return nEDMPRO0040Service.deleteImsiProductList(nEDMPRO0040VO, reuqest);
	}

	/**
	 * 임시보관함 신규상품 확정
	 * @param nEDMPRO0040VO
	 * @param reuqest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/fixImsiProductList.json",	method=RequestMethod.POST,	headers="Accept=application/json")
	public @ResponseBody Map<String, Object> updateImsiProductList(@RequestBody NEDMPRO0040VO nEDMPRO0040VO, HttpServletRequest request) throws Exception {	
		Map<String, Object> resultMap = new HashMap<String, Object>();

		/*try {
			resultMap = nEDMPRO0040Service.updateImsiProductList(nEDMPRO0040VO, request);
		} catch (Exception e) {
			logger.error("상품 확정 Exception 발생 ===" + e.toString() + "resultMap ==" + resultMap);
		}*/

		resultMap = nEDMPRO0040Service.updateImsiProductList(nEDMPRO0040VO, request);

		return resultMap;
	}

	/**
	 * 임시보관함 상품상세보기 Form Init
	 * @param nEDMPRO0041VO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/selectImsiNewProductDetail.do")
	public String selectImsiNewProductDetail(@RequestParam Map<String, Object> paramMap, HttpServletRequest request, ModelMap model) throws Exception {

		if (paramMap == null || model == null) {
			throw new TopLevelException("");
		}

		String viewPage = ""; // return Page

		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		//-----1.사용자가 선택한 상품의 newProductCode 값으로 해당 상품 상세 정보 조회
		//NEDMPRO0041VO tmpProduct	=	nEDMPRO0040Service.selectNewTmpProductDetailInfo(paramMap);

		model.addAttribute("epcLoginVO", epcLoginVO);

		/*if ("0".equals(tmpProduct.getOnoffDivnCd())) {
			viewPage = "edi/product/NEDMPRO0041";
		//온라인 전용, 소셜 상품	
		} else {
			viewPage = "edi/product/PEDMPRO000330";	
		}

		model.addAttribute("tmpProduct",	tmpProduct);*/

		/*//온오프 겸용 상품
		if ("0".equals(tmpProduct.getOnoffDivnCd())) {
			viewPage = "edi/product/NEDMPRO0041";
		//온라인 전용, 소셜 상품	
		} else {
			viewPage = "edi/product/PEDMPRO000330";	
		}

		//편집기 특수문자 처리. 
		String tmpProductDescription = StringEscapeUtils.escapeHtml(tmpProduct.getProdDesc());
		tmpProduct.setProdDesc(tmpProductDescription);

		//이미지 파일에 전달할 시간 파라미터. 브라우저 캐시 기능때문에 동일한 이름의 이미지 파일이 
		//파일만 바뀌었을 경우, 브라우저에서 이전 이미지가 보여지는 것을 방지해줌.
		long milliSecond = System.currentTimeMillis(); 
		model.addAttribute("currentSecond", new Long(milliSecond).toString());
		
		String fashionProductFlag = "n";
		//searchParam.setGroupCode(tmpProduct.getOfficialOrder().getTeamCode());
		
		//계절 목록 조회. 현재는 규격 상품의 계절목록 조회. 칼럼 사이즈 변경후 패션 계절 목록조회하도록
		//해당 서비스 클래스에 주석 제거		
		model.addAttribute("seasonList", commonProductService.NselectSeasonList(paramMap, request));
		
		if(isThisFashionProduct(tmpProduct.getProdDivnCd())) {
			fashionProductFlag = "y";
			//사용자가 입력한 변형속성 조회
			model.addAttribute("colorSizeListInTemp", 	  nEDMPRO0040Service.selectNewProductDetailVarAttInfo(paramMap));
		
			//pog이미지 표시에 사용될 고유한(중복 색상제거) 색상 목록 조회
			model.addAttribute("productColorList", nEDMPRO0040Service.selectNewSrcmkCd(paramMap, request));		
		}
		
		//전산법 추가
		//1.전상법큰분류
		//2.전상법 작은분류
		//3.전상법 가지고 있는거
		
		//	model.addAttribute("ecomAddInfoAddInTemp",pEDMPRO0003Service.selectProductEcomAddInfoListInTemp(tmpProduct.getNewProductCode())	   );		
		//	selectProductColorListInTemp
		
		//온라인 대표 상품 코드가 존재한다면, 즉 온라인 전용 이나 소셜 상품이라면
		if(StringUtils.isNotEmpty(tmpProduct.getOnlineRepProdCd())) {
			String entpCode = tmpProduct.getEntpCd();
			
			//현재 상품정보에 등록된 해당 협력업체의 온라인 대표상품 정보. 이익률, 88코드 조회
			List<NEDMPRO0044VO> onlineRepresentProductList = nEDMPRO0040Service.selectOnlineRepresentProdctList(entpCode);
			
			if(StringUtils.isNotEmpty(tmpProduct.getCategoryId())) {
				
				SearchParam tmpSearch = new SearchParam();
				
				tmpSearch.setDetailCode(tmpProduct.getCategoryId());
				model.addAttribute("l4GroupList", 	   commService.selectL4ListForOnlineAjax(tmpSearch));
			}
			
			//단품 정보 리스트
			model.addAttribute("itemListInTemp", 	 			nEDMPRO0040Service.selectOnlineItemListIntemp(tmpProduct.getPgmId()));
			model.addAttribute("onlineRepresentProductList", 	onlineRepresentProductList);
		}
						
		//----- 현재 상품상세 정보에 사용된 업체코드의 거래 유형 조회.
		paramMap.put("selectedVendor", tmpProduct.getEntpCd());			
		HashMap	resultHash			=	commonProductService.selectNVendorTradeType(paramMap, request);					//거래유형조회
		
		//----- 현재 상품의 거래 중지 정보
		if (resultHash == null) {			
			model.addAttribute("vendorTradeObj", 	"1");
		} else {
			model.addAttribute("vendorTradeObj", 	resultHash);
		}
		
		//----- 상품상세정보
		model.addAttribute("tmpProduct", 	   tmpProduct);
		
		//----- 전체 팀분류 목록
		model.addAttribute("teamList", 	  	   commonProductService.selectNteamList(paramMap, request));				//전체 팀분류 목록.
		
		//----- 현재 상품의 해당팀의 대분류
		paramMap.put("groupCode", 				tmpProduct.getTeamCd());
		model.addAttribute("l1GroupList", 	   commonProductService.selectNgetL1list(paramMap, request));				//현재 상품의 팀분류의 해당 대분류 목록.
		
		//-----온오프 상품일때만
		if ("0".equals(tmpProduct.getOnoffDivnCd())) {	
			paramMap.put("detailCode", tmpProduct.getL1Cd());
			model.addAttribute("l3GroupList", 	   commonProductService.selectNgetL3list(paramMap, request));
		}
			
		//전체 색상값 목록
		model.addAttribute("colorList", 	   commService.selectColorList());
		
		//전체 사이즈 분류 목록
		model.addAttribute("sizeCategoryList", commService.selectSizeCategoryList());
		
		//온라인 이미지 파일 목록 조회. 수정페이지에서 온라인 -> 오프라인 pog이미지 이런 순서로 업로드 함.
		 String onlineUploadFolder	 = makeSubFolderForOnline((String)paramMap.get("pgmId"));		 
		 ArrayList<String> onlineImageList = new ArrayList<String>(); 
		 
		 File dir = new File(onlineUploadFolder);	
		 FileFilter fileFilter = new WildcardFileFilter((String)paramMap.get("pgmId")+"*");
		 File[] files = dir.listFiles(fileFilter);
		 for (int j = 0; j < files.length; j++) {
			 String fileName = files[j].getName();
			 if( fileName.lastIndexOf(".") < 0 )  {
				 onlineImageList.add(fileName);
			 }
		 }

		 //오프라인 pog이미지 파일 목록 조회. 규격, 패션 모두 포함 됨. 
		 String offlineUploadFolder	 = makeSubFolderForOffline((String)paramMap.get("pgmId"));
		 ArrayList<String> offlineImageList = new ArrayList<String>(); 
		 File offlineDir = new File(offlineUploadFolder);	
		 FileFilter offlineFileFilter = new WildcardFileFilter(tmpProduct.getProdImgId()+"*");
		 File[] offlineFiles = offlineDir.listFiles(offlineFileFilter);
		 for (int jj = 0; jj < offlineFiles.length; jj++) {
			 offlineImageList.add(offlineFiles[jj].getName());
		 }
		 
		 model.addAttribute("onlineImageList",  onlineImageList);
		 model.addAttribute("offlineImageList", offlineImageList);
		 model.addAttribute("imagePath" ,		ConfigUtils.getString("system.cdn.static.path"));
		 model.addAttribute("subFolderName", 	subFolderName(tmpProduct.getPgmId()));
		 model.addAttribute("fashionProductFlag", fashionProductFlag);
		 model.addAttribute("epcLoginVO", epcLoginVO);
		 
		 return viewPage;*/

		return viewPage;
	}
	
	/**
	 * 기본출고지/반품지 등록 유무 확인
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/checkAddressInfo.json",	method=RequestMethod.POST,	headers="Accept=application/json")
	public @ResponseBody Map<String, Object> checkAddressInfo(@RequestBody Map<String, Object> paramMap, HttpServletRequest request) throws Exception {	
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		String[] vendorIds = new String[1];
		
		if(paramMap.get("entp_cd") == null || "".equals(paramMap.get("entp_cd"))) {
			vendorIds = epcLoginVO.getVendorId();
		} else {
			vendorIds[0] = (String) paramMap.get("entp_cd");
		}
		
		resultMap = nEDMPRO0040Service.checkAddressInfo(vendorIds);

		return resultMap;
	}
}
