package com.lottemart.epc.edi.product.controller;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lottemart.common.exception.AlertException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lottemart.common.exception.TopLevelException;
import com.lottemart.common.file.service.ImageFileMngService;
import com.lottemart.common.util.ConfigUtils;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.util.SecureUtil;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.product.dao.PEDMPRO000Dao;
import com.lottemart.epc.edi.product.model.ColorSize;
import com.lottemart.epc.edi.product.model.CommonProductVO;
import com.lottemart.epc.edi.product.model.NEDMPRO0020VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0024VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0042VO;
import com.lottemart.epc.edi.product.model.NewProduct;
import com.lottemart.epc.edi.product.model.EcProductCategory;
import com.lottemart.epc.edi.product.service.CommonProductService;
import com.lottemart.epc.edi.product.service.NEDMPRO0020Service;
import com.lottemart.epc.edi.product.service.NEDMPRO0040Service;
import com.lottemart.epc.edi.product.service.NEDMPRO0220Service;

import lcn.module.common.file.FileUtil;
import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.ConfigurationService;


/**
 * @Class Name : NEDMPRO0020Controller
 * @Description : 신상품등록(온오프)
 * @Modification Information
 * <pre>`
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2015.11.18. 	SONG MIN KYO	최초생성
 * </pre>
 */

@Controller
public class NEDMPRO0020Controller extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0020Controller.class);

	@Resource(name = "configurationService")
	private ConfigurationService config;

	@Resource(name = "commonProductService")
	private CommonProductService commonProductService;

	@Resource(name = "nEDMPRO0020Service")
	private NEDMPRO0020Service nEDMPRO0020Service;

	@Resource(name = "nEDMPRO0220Service")
	private NEDMPRO0220Service nEDMPRO0220Service;

	@Resource(name = "imageFileMngService")
	private ImageFileMngService imageFileMngService;

	/**
	 * 임시보관함 Service
	 */
	@Resource(name = "nEDMPRO0040Service")
	private NEDMPRO0040Service nEDMPRO0040Service;

	// 신상품 등록 관련 객체. 임시 보관함 삭제나 상품 정보 입력 및 수정.
	@Autowired
	private PEDMPRO000Dao pEDMPRO000Dao;

	/**
	 * 신규상품등록 (온오프 전용) 화면 Form 호출
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/edi/product/NEDMPRO0020.do")
	public String regNewProductOnOffInit(ModelMap model, HttpServletRequest request) throws Exception {

		if (model == null || request == null) {
			throw new TopLevelException("");
		}

		Map<String, Object>	paramMap	=	new HashMap<String, Object>();
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		model.addAttribute("epcLoginVO", 		epcLoginVO);
		model.addAttribute("teamList", commonProductService.selectNteamList(paramMap, request)); // 팀리스트
		model.addAttribute("seasonYearList", commonProductService.selectNseasonYear(paramMap)); // 계절년도
		model.addAttribute("nowYear", DateUtil.getToday("yyyy")); // 현재년도
		model.addAttribute("today", DateUtil.getToday("yyyyMMdd"));	//오늘날짜
		model.addAttribute("namoPath", ConfigUtils.getString("namo.link.path")); // 나모 에디터 패스

		return "edi/product/NEDMPRO0020";
	}

	/**
	 * 상품등록이후 상세 정보 호출
	 * @param pgmId
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/NEDMPRO0020Detail.do")
	public String newProdDetailInfo(@RequestParam String pgmId, ModelMap model, HttpServletRequest request) throws Exception {

		if (model == null || request == null) {
			throw new TopLevelException("");
		}

		String cfmFg = StringUtils.trimToEmpty(request.getParameter("cfmFg")); // 상품 확정구분

		Map<String, Object> paramMap = new HashMap<String, Object>();
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		model.addAttribute("epcLoginVO", epcLoginVO);
		model.addAttribute("teamList", commonProductService.selectNteamList(paramMap, request)); // 팀리스트
		model.addAttribute("seasonYearList", commonProductService.selectNseasonYear(paramMap)); // 계절년도
		model.addAttribute("nowYear", DateUtil.getToday("yyyy")); // 현재년도
		model.addAttribute("today", DateUtil.getToday("yyyyMMdd"));	//오늘날짜

		// -----상품상세정보
		paramMap.put("pgmId", StringUtils.trimToEmpty(pgmId));
		paramMap.put("cfmFg", cfmFg);
		NEDMPRO0020VO newProdDetailInfo = nEDMPRO0020Service.selectNewTmpProductDetailInfo(paramMap);

		// ---팀의 대분류
		List<CommonProductVO> resultList = commonProductService.selectNgetL1list(paramMap, request);

		// 등록된 신상품의 소분류의 표시단위, 표시기준수량 default 정보 존재 여부 조회
		paramMap.put("l3Cd", newProdDetailInfo.getL3Cd());
		int newProdEssenTialExistCnt = nEDMPRO0020Service.selectNewProdEssentialExistInfo(paramMap);

		model.addAttribute("newProdEssenTialExistCnt", newProdEssenTialExistCnt);
		model.addAttribute("newProdDetailInfo", newProdDetailInfo);
		model.addAttribute("namoPath", ConfigUtils.getString("namo.link.path")); // 나모 에디터 패스

		/* ****************************** */
		// 20180817 전산정보팀 이상구 추가.
		model.addAttribute("tpcPrdKeywordList", nEDMPRO0020Service.selectTpcPrdKeywordList(newProdDetailInfo.getPgmId()));
		/* ****************************** */

		model.addAttribute("ecCategory",	nEDMPRO0020Service.selectEcCategoryByProduct(newProdDetailInfo.getPgmId()));		//팀리스트


		// ---[210125 EC조립1]S

		//EC 속성추가
		List<ColorSize> itemListInTemp = nEDMPRO0020Service.selectProductItemListInTemp(newProdDetailInfo.getPgmId());
		model.addAttribute("itemListInTemp", itemListInTemp);



		//EC 속성의 개수
		if(!itemListInTemp.isEmpty()) {
			paramMap.put("stdCatCd",newProdDetailInfo.getStdCatCd());
			paramMap.put("attrPiType",itemListInTemp.get(0).getAttrPiType());
			model.addAttribute("ecAttrCnt", nEDMPRO0020Service.selectEcProductAttrCnt(paramMap));

			//[0515PIY]S

			model.addAttribute("inputEcAttrCnt",nEDMPRO0020Service.selectInputEcProductAttrCnt(newProdDetailInfo.getPgmId()));

			//[0515PIY]E
		}

		// ---[210125 EC조립1]E

		return "edi/product/NEDMPRO0020";
	}



	/**
	 * 신상품장려금 대상 업체인지 체크
	 * FROM JSP : CommonProductFunction.jsp
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/NcheckNewPromoFg.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> NcheckNewPromoFg(@RequestBody Map<String, Object> paramMap, HttpServletRequest request) throws Exception {
		if (paramMap == null || request == null) {
			throw new TopLevelException("");
		}


		int VendorNewPromoCnt = commonProductService.selectNcheckCountVendorNewPromoFg(paramMap, request); // 거래중지여부 조회

		Map<String, Object> returnMap = new HashMap<String, Object>();


		returnMap.put("VendorNewPromoCnt", VendorNewPromoCnt);
		return returnMap;

	}

	/**
	 * 거래중지된 업체인지 체크
	 * FROM JSP : CommonProductFunction.jsp
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/NcheckCountVendorStopTrading.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> NcheckCountVendorStopTrading(@RequestBody Map<String, Object> paramMap, HttpServletRequest request) throws Exception {
		if (paramMap == null || request == null) {
			throw new TopLevelException("");
		}

		Map<String, Object> returnMap = new HashMap<String, Object>();

		int VendorStopTradingCnt = commonProductService.selectNcheckCountVendorStopTrading(paramMap, request); // 거래중지여부 조회
		HashMap resultHash = commonProductService.selectNVendorTradeType(paramMap, request); // 거래유형조회
		String taxDivnCode = commonProductService.selectNVendorTaxType(paramMap, request); // 과세구분조회
		String tradeTypeCd = "1"; // 상품등록시 사용할 거래유형 타입 설정

		if (resultHash == null) {
			returnMap.put("tradeTypeCd", "1");
		} else {

			/**
			 *
			1 à KM01
			2 à KM02
			3 à KM07
			4 à KM03
			5 à KM06

			TO-BE 거래형태 구분 설명은 아래와 같습니다.
			-       KM01 : 상품 파트너사(직매입)
			-       KM02 : 상품 파트너사(특약1)
			-       KM03 : 상품 파트너사(특약2)
			-       KM04 : 상품 파트너사(직수입)
			-       KM05 : 상품 파트너사(위수탁)
			-       KM06 : 임대업체(임대갑)
			-       KM07 : 임대업체(임대을)
			-       KM08 : 마트 영업점
			-       KM09 : 상품 파트너사(종량제)
			 */

			/*if (StringUtils.trimToEmpty((String)resultHash.get("TRD_TYP_FG")).equals("KM01")) {
				tradeTypeCd = "1";
			} else if (StringUtils.trimToEmpty((String)resultHash.get("TRD_TYP_FG")).equals("KM02")) {
				tradeTypeCd = "2";
			} else if (StringUtils.trimToEmpty((String)resultHash.get("TRD_TYP_FG")).equals("KM07")) {
				tradeTypeCd = "3";
			} else if (StringUtils.trimToEmpty((String)resultHash.get("TRD_TYP_FG")).equals("KM03")) {
				tradeTypeCd = "4";
			} else if (StringUtils.trimToEmpty((String)resultHash.get("TRD_TYP_FG")).equals("KM06")) {
				tradeTypeCd = "5";

			//----- KM04, KM05,  KM08, KM09는 tradeTypeCd를 어떻게 바꿔야 되는지 확인 필요함 현재는 잉시로 그냥 끝자리 지정해놓음.
			} else if (StringUtils.trimToEmpty((String)resultHash.get("TRD_TYP_FG")).equals("KM04")) {
				tradeTypeCd = "9";
			} else if (StringUtils.trimToEmpty((String)resultHash.get("TRD_TYP_FG")).equals("KM08")) {
				tradeTypeCd = "9";
			} else if (StringUtils.trimToEmpty((String)resultHash.get("TRD_TYP_FG")).equals("KM09")) {
				tradeTypeCd = "9";
			} else if (StringUtils.trimToEmpty((String)resultHash.get("TRD_TYP_FG")).equals("KM05")) {
				tradeTypeCd = "9";
			} */

			returnMap.put("tradeTypeCd", StringUtils.trimToEmpty((String)resultHash.get("TRD_TYP_FG")));
		}

		returnMap.put("VendorStopTradingCnt", VendorStopTradingCnt);
		returnMap.put("taxDivnCode", StringUtils.trimToEmpty(taxDivnCode));

		return returnMap;
	}

	/**
	 * 팀의 대분류 조회
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/NselectL1List.json", method=RequestMethod.POST, headers="Accept=application/json")
    public @ResponseBody Map<String, Object> NselectL1List(@RequestBody Map<String, Object> paramMap,  HttpServletRequest request) throws Exception {
		if (paramMap == null || request == null) {
			throw new TopLevelException("");
		}

		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<CommonProductVO> resultList = commonProductService.selectNgetL1list(paramMap, request);
		returnMap.put("l1List", resultList);

		return returnMap;
	}

	/**
	 * 대분류의 중분류 조회
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/NselectL2List.json", method=RequestMethod.POST, headers="Accept=application/json")
    public @ResponseBody Map<String, Object> NselectL2List(@RequestBody Map<String, Object> paramMap,  HttpServletRequest request) throws Exception {
		if (paramMap == null || request == null) {
			throw new TopLevelException("");
		}

		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<CommonProductVO> resultList = commonProductService.selectNgetL2list(paramMap);
		returnMap.put("l2List", resultList);

		return returnMap;
	}

	/**
	 * 중분류의 소분류 조회
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/NselectL3List.json", method=RequestMethod.POST, headers="Accept=application/json")
    public @ResponseBody Map<String, Object> NselectL3List(@RequestBody Map<String, Object> paramMap,  HttpServletRequest request) throws Exception {
		if (paramMap == null || request == null) {
			throw new TopLevelException("");
		}

		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<CommonProductVO> resultList = commonProductService.selectNgetL3list(paramMap, request);
		returnMap.put("l3List", resultList);

		return returnMap;
	}

	//이동빈
	/**
	 * 중분류의 소분류 조회
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/NselectGrpList.json", method=RequestMethod.POST, headers="Accept=application/json")
    public @ResponseBody Map<String, Object> NselectGrpList(@RequestBody Map<String, Object> paramMap,  HttpServletRequest request) throws Exception {
		if (paramMap == null || request == null) {
			throw new TopLevelException("");
		}

		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<CommonProductVO> resultList = commonProductService.selectNgetGrplist(paramMap, request);
		returnMap.put("grpL3List", resultList);

		return returnMap;
	}

	/**
	 * 전상법 콤보박스 변경시 해당그룹의 전상법 리스트 조회
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/selectProdAddTemplateDetailList.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> selectProdAddTemplateDetailList(@RequestBody Map<String, Object> paramMap, HttpServletRequest request) throws Exception {
		if (paramMap == null || request == null) {
			throw new TopLevelException("");
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<CommonProductVO> resultList = commonProductService.selectProdAddTemplateDetailList(paramMap);
		resultMap.put("resultList", resultList);

		return resultMap;
	}

	/**
	 * KC인증 콤보박스 변경시 해당그룹의 리스트 조회
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/selectProdCertTemplateDetailList.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> selectProdCertTemplateDetailList(@RequestBody Map<String, Object> paramMap, HttpServletRequest request) throws Exception {

		if (paramMap == null || request == null) {
			throw new TopLevelException("");
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		String infoGrpCd = StringUtils.trimToEmpty((String) paramMap.get("infoGrpCd"));
		List<CommonProductVO> resultList = commonProductService.selectProdCertTemplateDetailList(paramMap);
		resultMap.put("resultList", resultList);

		return resultMap;
	}

	/**
	 * 대분류 변경으로 전자상거래 콤보리스트 조회
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/selectProdAddTemplateList.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> selectProdAddTemplateList(@RequestBody Map<String, Object> paramMap, HttpServletRequest request) throws Exception {

		if (paramMap == null || request == null) {
			throw new TopLevelException("");
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<CommonProductVO> resultList = commonProductService.selectProdAddTemplateList(paramMap);
		resultMap.put("resultList", resultList);

		return resultMap;
	}

	/**
	 * 대분류 변경으로 KC인증 콤보리스트 조회
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/selectProdCertTemplateList.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> selectProdCertTemplateList(@RequestBody Map<String, Object> paramMap, HttpServletRequest request) throws Exception {

		if (paramMap == null || request == null) {
			throw new TopLevelException("");
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<CommonProductVO> resultList = commonProductService.selectProdCertTemplateList(paramMap);
		resultMap.put("resultList", resultList);

		return resultMap;
	}

	/**
	 * 년도의 계절리스트 조회
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/NselectSeasonList.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> selectSeasonList(@RequestBody Map<String, Object> paramMap, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("seasonList", commonProductService.NselectSeasonList(paramMap, request));
		return resultMap;
	}

	/**
	 * 상품저장[기본정보, 추가정보, 상품상세설명]
	 * @param nEDMPRO0020VO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/insertNewProductMST.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> insertNewProductMst(@RequestBody NEDMPRO0020VO nEDMPRO0020VO, HttpServletRequest request) throws Exception {
		return nEDMPRO0020Service.insertNewProdMst(nEDMPRO0020VO);
	}

	/**
	 * 상품 수정[기본정보, 추가정보, 상품상세설명]
	 * @param nEDMPRO0020VO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/updateNewProductMST.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> updateNewProductMST(@RequestBody NEDMPRO0020VO nEDMPRO0020VO, HttpServletRequest request) throws Exception {
		//return nEDMPRO0020Service.updateNewProductMST(nEDMPRO0020VO);
		return nEDMPRO0020Service.insertNewProdMst(nEDMPRO0020VO);
	}

	/**
	 * 신상품 등록 [이미지 등록 탭]
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/NEDMPRO0022.do")
	public String newProductImageTabFormInit(NEDMPRO0020VO nEDMPRO0020VO, ModelMap model, HttpServletRequest request) throws Exception {
		if (nEDMPRO0020VO == null || model == null || request == null) {
			throw new TopLevelException("");
		}

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pgmId", StringUtils.trimToEmpty(nEDMPRO0020VO.getPgmId()));
		paramMap.put("cfmFg", StringUtils.trimToEmpty(nEDMPRO0020VO.getCfmFg()));

		// -----상품의 변형속성 카운트
		int newProdVarAttCnt = nEDMPRO0020Service.selectNewProdVarAttCnt(StringUtils.trimToEmpty(nEDMPRO0020VO.getPgmId()), StringUtils.trimToEmpty(nEDMPRO0020VO.getCfmFg()));

		// ----- 상품의 변형속성 리스트 조회[NEDMPRO0040.xml에 있는 쿼리 사용]
		List<NEDMPRO0042VO> prodVarAttList = nEDMPRO0040Service.selectNewSrcmkCd(paramMap, request);

		// -----상품의 상세정보 조회[NEDMPRO0040.xml에 있는 쿼리 사용]
		NEDMPRO0020VO prodDetailVO = nEDMPRO0020Service.selectNewTmpProductDetailInfo(paramMap);

		// -----온라인 이미지 파일 목록조회.
		String onlineUploadFolder = makeSubFolderForOnline(StringUtils.trimToEmpty(nEDMPRO0020VO.getPgmId()));
		ArrayList<String> onlineImageList = new ArrayList<String>();
		File dir = new File(onlineUploadFolder);
		FileFilter fileFilter = new WildcardFileFilter(StringUtils.trimToEmpty(nEDMPRO0020VO.getPgmId()) + "*");
		File[] files = dir.listFiles(fileFilter);
		for (int j = 0; j < files.length; j++) {
			String fileName = files[j].getName();
			if (fileName.lastIndexOf(".") < 0) {
				onlineImageList.add(fileName);
			}
		}

		//오프라인 pog이미지 파일 목록 조회. 규격, 패션 모두 포함 됨.
		/* String offlineUploadFolder	 = makeSubFolderForOffline(StringUtils.trimToEmpty(nEDMPRO0020VO.getPgmId()));
		ArrayList<String> offlineImageList = new ArrayList<String>();
		File offlineDir = new File(offlineUploadFolder);
		FileFilter offlineFileFilter = new WildcardFileFilter(nEDMPRO0020VO.getProdImgId()+"*");
		File[] offlineFiles = offlineDir.listFiles(offlineFileFilter);
		for (int jj = 0; jj < offlineFiles.length; jj++) {
			 offlineImageList.add(offlineFiles[jj].getName());
		}*/

		// 이미지 파일에 전달할 시간 파라미터. 브라우저 캐시 기능때문에 동일한 이름의 이미지 파일이
		// 파일만 바뀌었을 경우, 브라우저에서 이전 이미지가 보여지는 것을 방지해줌.

		// [ 와이드 이미지 등록 - PIY S]

		String wideUploadFolder = makeSubFolderForWide(nEDMPRO0020VO.getPgmId());
		ArrayList<String> onlineWideImage = new ArrayList<String>();
		File wideDir = new File(wideUploadFolder);
		FileFilter widefileFilter = new WildcardFileFilter(StringUtils.trimToEmpty(nEDMPRO0020VO.getPgmId()) + "*");
		File[] widefiles = wideDir.listFiles(widefileFilter);
		for (int j = 0; j < widefiles.length; j++) {
			String wideFileName = widefiles[j].getName();
			if (wideFileName.lastIndexOf(".") < 0) {
				onlineWideImage.add(wideFileName);
			}
		}

		// [ 와이드 이미지 등록 - PIY E]

		long milliSecond = System.currentTimeMillis();

		model.addAttribute("currentSecond", String.valueOf(milliSecond));
		model.addAttribute("onlineImageList", onlineImageList);
		model.addAttribute("onlineWideImage", onlineWideImage);
		//model.addAttribute("onlineWideImagePath",ConfigUtils.getString("online.product.wide.image.path"));
		model.addAttribute("imagePath", ConfigUtils.getString("system.cdn.static.path"));
		model.addAttribute("imagePathOnline", ConfigUtils.getString("edi.online.image.url"));
		model.addAttribute("imagePathSub", ConfigUtils.getString("system.cdn.static.path.sub"));
		model.addAttribute("subFolderName", subFolderName(StringUtils.trimToEmpty(nEDMPRO0020VO.getPgmId())));
		model.addAttribute("newProdVarAttCnt", newProdVarAttCnt);
		model.addAttribute("prodVarAttList", prodVarAttList);
		model.addAttribute("prodDetailVO", prodDetailVO);
		return "edi/product/NEDMPRO0022";
	}

	/**
	 * 신상품 등록 [이미지 등록 탭-일괄지정]
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/NEDMPRO0022ImgPop.do")
	public String NEDMPRO0022ImgPop(ModelMap model, HttpServletRequest request) throws Exception {
		if (model == null || request == null) {
			throw new TopLevelException("");
		}
		return "edi/product/NEDMPRO0022ImgPop";
	}

	/**
	 * 이미지 일괄적용
	 * @param paramMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/saveSaleImgAllApply.do")
	public String saveSaleImgAllApply(@RequestParam Map<String, Object> paramMap, HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("result", nEDMPRO0020Service.saveSaleImgAllApply(paramMap, request));
		return "edi/product/NEDMPRO0022ImgPop";
	}

	/**
	 * 업로드 대표이미지 정합성 체크 ( 600KB 이하, 500*500 ~ 1500*1500 px )
	 * @param checkFile
	 * @return
	 * @throws Exception
	 */
	private boolean imageFileSizeCheck(File checkFile) throws Exception {

		long maxSize = 1024 * 600; // 이미지 파일 MaxSize;
		int minPixelSize = 500 * 500; // 최소 픽셀 사이즈
		int maxPixelSize = 1500 * 1500; // 최대 픽셀 사이즈

		long fileSize = checkFile.length(); // 파일용량

		Image chkImg = null;
		try {
			chkImg = ImageIO.read(checkFile);
		} catch (IOException ioe) {
			logger.error("ImageIO read ERROR");
			throw ioe;
		}

		int imgHeight = chkImg.getHeight(null);
		int imgWidth = chkImg.getWidth(null);

		int xMinusY = imgHeight - imgWidth; // 가로 세로 1:1 비율 체크
		int imgPixelSize = imgHeight * imgWidth; // 전체 픽셀 사이즈

		boolean isOk = fileSize <= maxSize && xMinusY == 0 && minPixelSize <= imgPixelSize && imgPixelSize <= maxPixelSize;

		return isOk;
	}

	/**
	 * 온라인 이미지 저장
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/NEDMPRO0022Save.do")
	public String onlineImageSave(HttpServletRequest request, ModelMap model) throws Exception {
		if (request == null || model == null) {
			throw new TopLevelException("");
		}

		String pgmId = SecureUtil.splittingFilter(StringUtils.trimToEmpty((String) request.getParameter("pgmId")));
		String mode = StringUtils.trimToEmpty((String) request.getParameter("mode"));
		String uploadDir = makeSubFolderForOnline(pgmId);
		String uploadFieldCount = StringUtils.trimToEmpty((String) request.getParameter("uploadFieldCount")); // 파일개수
		String onOffDivnCd = StringUtils.trimToEmpty((String) request.getParameter("onOffDivnCd"));
		String newProdGenDivnCd = StringUtils.trimToEmpty((String) request.getParameter("newProdGenDivnCd")); //코리안넷에서 등록한 상품인지 EPC에서 등록한상품인지 구분

		// 정사각형 사이즈 체크
		// 용랑 600kb이하 및 500 ~ 1500 사이즈로 체크
		InputStream is = null;
		int limitSize = 1024*600;
		String errGbn = null;
		int maxSize = 1500;
		int minSize = 500;

		//온라인 이미지 파일 업로드.
		//업로드 할때 상품코드와 자연증가 seq변수 값으로 해당 이미지 파일명 설정.
		Integer newSeq = 0;
		MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;
		Iterator fileIter = mptRequest.getFileNames();

		String imgMsg = "";

		// 이미지체크 온라인상품에 적용 2019-07-02
		if ("1".equals(onOffDivnCd) || "2".equals(onOffDivnCd)) { // 온라인, 소셜 전용

			List<String> fileList = new ArrayList<String>(); // 업로드 파일 리스트
			boolean isAllOK = true; // 전체 업로드 처리 여부

			while (fileIter.hasNext()) {
				MultipartFile mFile = mptRequest.getFile((String) fileIter.next());
				if (!mFile.isEmpty()) {
					String fileNm = pgmId + "_" + newSeq;
					String newFileSource = uploadDir + "/" + fileNm;
					String fileFieldName = mFile.getName();
					if (!fileFieldName.startsWith("front")) { // 이미지 수정
						fileNm = SecureUtil.splittingFilter(fileFieldName);
						newFileSource = uploadDir + "/" + fileNm + "_T2MP";
					}

					FileOutputStream frontImageStream = new FileOutputStream(newFileSource);
					FileCopyUtils.copy(mFile.getInputStream(), frontImageStream);

					fileList.add(newFileSource);

					File fileCheck = new File(newFileSource);
					boolean isOk = imageFileSizeCheck(fileCheck);

				    // 최종 확인 조건
				    if (!isOk) { // 파일 OK 가 아니면
				    	isAllOK = isOk; // 한개라도 업로드 불가면 모두 업로드 불가
				    }

					newSeq++;

					if (frontImageStream != null) { // FileOutputStream 종료
						try {
							frontImageStream.close();
						} catch (Exception e) {
							logger.error("onlineImageSave (Exception) : ", e);
						}
					}
				}
			}

			if (isAllOK) { // 업로드된 전체 이미지 기준 충족
				if (fileList != null && fileList.size() > 0) {
					int size = fileList.size();
					for (int i = 0; i < size; i++) {
						String fileNm = fileList.get(i);

						if (fileNm.indexOf("_T2MP") > -1) {
							File tmpFile = new File(fileNm);
							File uploadFile = new File(fileNm.replaceAll("_T2MP", ""));
							FileCopyUtils.copy(tmpFile, uploadFile);
							if (tmpFile.isFile()) {
								FileUtil.delete(tmpFile);
							}
						}
						fileNm = fileNm.replaceAll("_T2MP", "").replaceAll(uploadDir + "/", "");
						imageFileMngService.purgeImageQCServer("01", fileNm);
						imageFileMngService.purgeCDNServer("01", fileNm);
					}

					if (uploadFieldCount != null || !"".equals(uploadFieldCount)) {
						Map<String, Object> paramMap = new HashMap<String, Object>();
						paramMap.put("pgmId", pgmId);
						paramMap.put("fileCount", uploadFieldCount);
						nEDMPRO0020Service.updateNewProdImgCnt(paramMap);
					}
				}
			} else { // 업로드된 이미지 기준 미달
				if (fileList != null && fileList.size() > 0) {
					int size = fileList.size();
					for (int i = 0; i < size; i++) {
						String fileNm = fileList.get(i);
						File file = new File(fileNm);
						if (file.isFile()) {
							FileUtil.delete(file);
						}
					}
				}
				imgMsg = "대표 이미지 등록 기준을 준수 바랍니다.\\n용량: 600KB 이하\\n사이즈: (정사이즈 비율) 500px이상 1500px이하"; // 이미지 기준미달 메시지
			}

		} else { // 온오프전용

			try
			{

				while (fileIter.hasNext()) {
					MultipartFile mFile = mptRequest.getFile((String)fileIter.next());
					logger.debug("1.이미지 변경 시작======================================================================");
					 if(!mFile.isEmpty()) {

					    is = mFile.getInputStream();
						BufferedImage image = ImageIO.read(is);
						long imgSize = mFile.getSize();
						Integer width = image.getWidth();
						Integer height = image.getHeight();
						String imageExtension = mFile.getContentType().toLowerCase();

						if (!imageExtension.equals("image/jpg") && !imageExtension.equals("image/jpeg")) {
							errGbn = "notPassExtension";
							throw new IllegalArgumentException("JPG 확장자 이미지만 업로드 가능합니다.");
						}
						// 정사각형 이미지 체크
						if (imgSize > limitSize) {
							errGbn = "sizeErr";
							throw new IllegalArgumentException("600kb이하의 이미지만 업로드 가능합니다.");
						}
						if (width - height != 0 || width < minSize || height < minSize || width > maxSize || height > maxSize) {
							errGbn = "imgSize";
		                	throw new IllegalArgumentException("사이즈는 정사이즈 비율 500px 이상 1500px 이하입니다.");
						}

						logger.debug("2.======================================================================");
						String fileNm = pgmId+"_"+newSeq;
						String newFileSource = uploadDir+"/"+fileNm;
						String fileFieldName = mFile.getName();
						if(!fileFieldName.startsWith("front")) {
							fileNm = SecureUtil.splittingFilter(fileFieldName);
							newFileSource = uploadDir+"/"+fileNm;
						}
						logger.debug("3.newFileSource =======>"+newFileSource+"<=======================================================");

						FileOutputStream  frontImageStream = new FileOutputStream(newFileSource);
						FileCopyUtils.copy(mFile.getInputStream(), frontImageStream);

						//ImageUtilsThumbnail.resizeAutoForEPC(newFileSource); //구버전 이미지 리사이징 메소드
						logger.debug("4.fileNm =======>"+fileNm+"<=======================================================");

						imageFileMngService.purgeImageQCServer("01",fileNm);
						imageFileMngService.purgeCDNServer("01", fileNm);
						newSeq++;

						if ( frontImageStream != null ){
							 frontImageStream.close();
							 frontImageStream = null;
						}
					}
				}

				if (uploadFieldCount != null || !"".equals(uploadFieldCount)) {
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("pgmId", pgmId);
					paramMap.put("fileCount", uploadFieldCount);
					nEDMPRO0020Service.updateNewProdImgCnt(paramMap);
				}
			}
			catch(Exception e)
			{
				model.addAttribute("status","fail");
				if("sizeErr".equals(errGbn))
				{
					model.addAttribute("ment","2"); // 600KB이하의 이미지만 업로드 가능합니다.
				}
				else if("imgSize".equals(errGbn))
				{
					model.addAttribute("ment","5"); // 사이즈는 정사이즈 비율 500px 이상 1500px 이하입니다.
				}
				else if("notPassExtension".equals(errGbn))
				{
					model.addAttribute("ment","6"); // 이미지 확장자 JPG만 업로드 가능합니다.
				}
				else {
					logger.error("onOffProd_OnlineImage_Exception",e);
				}
			}
		}
		if ("1".equals(onOffDivnCd) || "2".equals(onOffDivnCd)) {
			//logger.debug("5.onOffDivnCd : " + onOffDivnCd + " =====");
			request.setAttribute("pgmId", pgmId);
			request.setAttribute("mode", "modify");
			request.setAttribute("imgMsg", imgMsg);
			return "/edi/product/NEDMPRO0022Save"; // AlertMsg JSP
			//return "redirect:/edi/product/NEDMPRO0032.do?pgmId=" + pgmId + "&mode=modify"; //온라인, 소셜 전용
		} else {
			if ("KOR".equals(newProdGenDivnCd)) {
				logger.debug("6.onOffDivnCd =======>" + onOffDivnCd + "<=======================================================");
				return "redirect:/edi/product/NEDMPRO0102.do?pgmId=" + pgmId + "&mode=" + mode + "&bman_no=" + (String) request.getParameter("bman_no"); // 온오프전용[코리안넷]
			} else {
				logger.debug("7.onOffDivnCd =======>" + onOffDivnCd + "<=======================================================");
				return "redirect:/edi/product/NEDMPRO0022.do?pgmId="+pgmId+"&mode="+mode;	//온오프전용
			}
		}
	}


	/**
	 * 온라인 상품 상세 설명 저장
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/NEDMPRO0022SaveDetailImg.do", method = RequestMethod.POST)
	public String onlineDetailImgSave(NewProduct newProduct, HttpServletRequest request, ModelMap model) throws Exception {
		if (request == null || model == null) {
			throw new TopLevelException("");
		}
		
		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		String workId = epcLoginVO.getLoginWorkId();

		// logger.debug("입력된 신상품 필드값 :"+newProduct);

		String pgmId = SecureUtil.splittingFilter(StringUtils.trimToEmpty((String) request.getParameter("pgmId")));
		String prodNm = SecureUtil.splittingFilter(StringUtils.trimToEmpty((String) request.getParameter("prodNm")));
		String productDescription = StringUtils.trimToEmpty((String) request.getParameter("productDescription"));
		String entpCd = SecureUtil.splittingFilter(StringUtils.trimToEmpty((String) request.getParameter("entpCd")));
		String mode = StringUtils.trimToEmpty((String) request.getParameter("mode"));
		String onOffDivnCd = StringUtils.trimToEmpty((String) request.getParameter("onOffDivnCd"));
		String newProdGenDivnCd = StringUtils.trimToEmpty((String) request.getParameter("newProdGenDivnCd")); // 코리안넷에서 등록한 상품인지 EPC에서 등록한상품인지 구분
		String actGbn = ""; //상품 상세설명 저장 버튼 구분

		if (StringUtils.isBlank(pgmId)) {
			logger.error("임시상품코드 값이 없습니다.");
			throw new AlertException("임시상품코드 값이 없습니다.");
		}

		newProduct.setNewProductCode(pgmId);
		newProduct.setProductName(prodNm);
		newProduct.setProductDescription(productDescription);
		newProduct.setEntpCode(entpCd);
		newProduct.setRegId(workId);		//작업자정보 setting (로그인세션에서 추출)

		int retCnt = nEDMPRO0020Service.selectProductDescription(newProduct);

		if (retCnt > 0) {
			nEDMPRO0020Service.updateProductDescription(newProduct);
			actGbn = "mod";
		} else {
			pEDMPRO000Dao.insertProductDescription(newProduct); // 상품 설명 등록
			actGbn = "";
		}

		nEDMPRO0020Service.updateNewProdImgInfo(newProduct); //상품 이미지 정보 수정

		if ("1".equals(onOffDivnCd) || "2".equals(onOffDivnCd)) {
			// logger.debug("5.onOffDivnCd =======>"+onOffDivnCd+"<=======================================================");
			return "redirect:/edi/product/NEDMPRO0032.do?pgmId=" + pgmId + "&mode=modify&actGbn=" + actGbn;	//온라인, 소셜 전용
		}else {
			if (newProdGenDivnCd.equals("KOR")) {
				// logger.debug("6.onOffDivnCd =======>"+onOffDivnCd+"<=======================================================");
				return "redirect:/edi/product/NEDMPRO0102.do?pgmId="+pgmId+"&mode="+mode+"&bman_no="+(String)request.getParameter("bman_no");	//온오프전용[코리안넷]
			} else {
				// logger.debug("7.onOffDivnCd =======>"+onOffDivnCd+"<=======================================================");
				return "redirect:/edi/product/NEDMPRO0022.do?pgmId="+pgmId+"&mode="+mode;	//온오프전용
			}
		}
	}


	/**
	 * 온라인 이미지 저장[코리안넷 전용]
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/NEDMPRO0102Save.do")
	public String onlineImageSaveKorNet(HttpServletRequest request, ModelMap model) throws Exception {
		if (request == null || model == null) {
			throw new TopLevelException("");
		}

		String pgmId 	 = SecureUtil.splittingFilter(StringUtils.trimToEmpty((String) request.getParameter("pgmId")));
		String mode 	 = StringUtils.trimToEmpty((String) request.getParameter("mode"));
		String onOffDivnCd = StringUtils.trimToEmpty((String) request.getParameter("onOffDivnCd"));
		String newProdGenDivnCd = StringUtils.trimToEmpty((String) request.getParameter("newProdGenDivnCd")); //코리안넷에서 등록한 상품인지 EPC에서 등록한상품인지 구분
		String wideImgYn = StringUtils.trimToEmpty((String) request.getParameter("wideImgYn"));  			  //와이드 이미지 저장

		String uploadFieldCount = null;
		String uploadWideFieldCount = null;

		if(wideImgYn.equals("N")) {
			uploadFieldCount = StringUtils.trimToEmpty((String) request.getParameter("uploadFieldCount"));}
		else {
			uploadWideFieldCount = StringUtils.trimToEmpty((String) request.getParameter("uploadWideFieldCount"));}

		String uploadDir = null;
		InputStream is = null;
		InputStream wis = null;
		String errGbn = null;
		int limitSize = 1024*600;		// 이미지 크기

		int maxSize = 1500; 			// 온라인 이미지 최소 최대 크기
		int minSize = 500;

		int maxWidthSize  = 1312;		// 온라인 와이드 이미지 최소 최대 크기
		int minWidthSize  = 720;
		int maxHeightSize = 740;
		int minHeightSize = 405;

	   if(wideImgYn.equals("N")) {
			uploadDir = makeSubFolderForOnline(pgmId);}
	   else {
	 		uploadDir = makeSubFolderForWide(pgmId);}


		// 온라인 이미지 파일 업로드.
		// 업로드 할때 상품코드와 자연증가 seq변수 값으로 해당 이미지 파일명 설정.
		Integer newSeq = 0;
		MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;
		Iterator fileIter = mptRequest.getFileNames();

		try
		{
			while (fileIter.hasNext()) {
				MultipartFile mFile = mptRequest.getFile((String) fileIter.next());

				if (!mFile.isEmpty()) {
					if(wideImgYn.equals("N"))
					{
						String newFileSource = uploadDir + "/" + pgmId + "_" + newSeq;

						String fileFieldName = mFile.getName();
						if (!fileFieldName.startsWith("front")) {
							newFileSource = uploadDir + "/" + SecureUtil.splittingFilter(fileFieldName);
						}

					  	is = mFile.getInputStream();
						BufferedImage image = ImageIO.read(is);
						long imgSize = mFile.getSize();
						Integer width = image.getWidth();
						Integer height = image.getHeight();
						String imageExtension = mFile.getContentType().toLowerCase();

						if (!imageExtension.equals("image/jpg") && !imageExtension.equals("image/jpeg")) {
							errGbn = "notPassExtension";
							throw new IllegalArgumentException("JPG 확장자 이미지만 업로드 가능합니다.");
						}
						// 정사각형 이미지 체크
						if (imgSize > limitSize) {
							errGbn = "sizeErr";
							throw new IllegalArgumentException("600kb이하의 이미지만 업로드 가능합니다.");
						}
						if (width - height != 0 || width < minSize || height < minSize || width > maxSize || height > maxSize) {
							errGbn = "imgSize";
				        	throw new IllegalArgumentException("사이즈는 정사이즈 비율 500px 이상 1500px 이하입니다.");
						}

						FileOutputStream frontImageStream = new FileOutputStream(newFileSource);
						FileCopyUtils.copy(mFile.getInputStream(), frontImageStream);
						// ImageUtilsThumbnail.resizeAutoForEPC(newFileSource); //구버전 이미지 리사이징 메소드

						//
						imageFileMngService.purgeImageQCServer("01", newFileSource);
						imageFileMngService.purgeCDNServer("01", newFileSource);

						newSeq++;

						if ( frontImageStream != null ) {
							 frontImageStream.close();
							 frontImageStream = null;
						}

					}
					else {
						wis = mFile.getInputStream();
						BufferedImage image = ImageIO.read(wis);
						long imgSize = mFile.getSize();
						Integer width = image.getWidth();
						Integer height = image.getHeight();

						// 와이드 이미지 파일 체크
						if (imgSize > limitSize) {
							errGbn = "sizeErr";
							throw new IllegalArgumentException("600kb이하의 이미지만 업로드 가능합니다.");
						}
						if(width > maxWidthSize || height > maxHeightSize || width < minWidthSize || height < minHeightSize) {
							errGbn = "widthheightErr";
							throw new IllegalArgumentException("와이드형 사이즈는 720 X 405 이상 1312 X 740 이하입니다.");
						}


						String fileNm = pgmId+"_0"+newSeq;
						String newFileSource = uploadDir+"/"+fileNm;
						String fileFieldName = mFile.getName();
						if(!fileFieldName.startsWith("front")) {
							fileNm = SecureUtil.splittingFilter(fileFieldName);
							newFileSource = uploadDir+"/"+fileNm;
						}

						// 기존에 있던 파일 삭제
						File tmpFile = new File(newFileSource);
						if (tmpFile.isFile()) {
							FileUtil.delete(tmpFile);
						}

						FileOutputStream frontImageStream = new FileOutputStream(newFileSource);
						FileCopyUtils.copy(mFile.getInputStream(), frontImageStream);
						imageFileMngService.purgeImageQCServer("12",fileNm);
						imageFileMngService.purgeCDNServer("12", fileNm);

						if ( frontImageStream != null ) {
							 frontImageStream.close();
							 frontImageStream = null;
						}

					}
				}
			}

			if(wideImgYn.equals("N")) {
				if (uploadFieldCount != null || !"".equals(uploadFieldCount)) {
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("pgmId", pgmId);
					paramMap.put("fileCount", uploadFieldCount);
					nEDMPRO0020Service.updateNewProdImgCnt(paramMap);
				}
			}
			else {
				if (uploadWideFieldCount != null || !"".equals(uploadWideFieldCount)) {
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("pgmId", pgmId);
					paramMap.put("onlineWideImageCnt",uploadWideFieldCount);
					// 온라인 와이드 이미지 등록됐다는 체크 추가
					nEDMPRO0020Service.updateOnlineWideImageCnt(paramMap);
				}
			}
			model.addAttribute("ment","1");
		}
		catch(Exception e)
		{
			model.addAttribute("status","fail");
			if("sizeErr".equals(errGbn))
			{
				model.addAttribute("ment","2"); // 500KB이하의 이미지만 업로드 가능합니다.
			}
			else if("widthheightErr".equals(errGbn))
			{
				model.addAttribute("ment","3"); // 와이드형 사이즈는 720 X 405 이상 1312 X 740 이하입니다.
			}

			else if("imgSize".equals(errGbn))
			{
				model.addAttribute("ment","5"); // 사이즈는 정사이즈 비율 500px 이상 1500px 이하입니다
			}
			else if("notPassExtension".equals(errGbn))
			{
				model.addAttribute("ment","6"); // 이미지 확장자 JPG만 업로드 가능합니다.
			}
			else {
				logger.error("koreanNet_onOffProd_Image_exception",e);
			}
		}


		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pgmId", StringUtils.trimToEmpty(pgmId));
		paramMap.put("cfmFg", StringUtils.trimToEmpty(""));

		// -----상품의 변형속성 카운트
		int newProdVarAttCnt = nEDMPRO0020Service.selectNewProdVarAttCnt(StringUtils.trimToEmpty(pgmId), StringUtils.trimToEmpty(paramMap.get("cfmFg").toString()));

		// ----- 상품의 변형속성 리스트 조회[NEDMPRO0040.xml에 있는 쿼리 사용]
		List<NEDMPRO0042VO> prodVarAttList = nEDMPRO0040Service.selectNewSrcmkCd(paramMap, request);

		// -----상품의 상세정보 조회[NEDMPRO0040.xml에 있는 쿼리 사용]
		NEDMPRO0020VO prodDetailVO = nEDMPRO0020Service.selectNewTmpProductDetailInfo(paramMap);

		// -----온라인 이미지 파일 목록조회.
		String onlineUploadFolder = makeSubFolderForOnline(StringUtils.trimToEmpty(pgmId));
		ArrayList<String> onlineImageList = new ArrayList<String>();
		File dir = new File(onlineUploadFolder);
		FileFilter fileFilter = new WildcardFileFilter(StringUtils.trimToEmpty(pgmId) + "*");
		File[] files = dir.listFiles(fileFilter);
		for (int j = 0; j < files.length; j++) {
			String fileName = files[j].getName();
			if (fileName.lastIndexOf(".") < 0) {
				onlineImageList.add(fileName);
			}
		}

		String wideUploadFolder = makeSubFolderForWide(pgmId);
		ArrayList<String> onlineWideImage = new ArrayList<String>();
		File wideDir = new File(wideUploadFolder);
		FileFilter widefileFilter = new WildcardFileFilter(StringUtils.trimToEmpty(pgmId) + "*");
		File[] widefiles = wideDir.listFiles(widefileFilter);
		for (int j = 0; j < widefiles.length; j++) {
			String wideFileName = widefiles[j].getName();
			if (wideFileName.lastIndexOf(".") < 0) {
				onlineWideImage.add(wideFileName);
			}
		}

		// 이미지 파일에 전달할 시간 파라미터. 브라우저 캐시 기능때문에 동일한 이름의 이미지 파일이
		// 파일만 바뀌었을 경우, 브라우저에서 이전 이미지가 보여지는 것을 방지해줌.
		long milliSecond = System.currentTimeMillis();


		model.addAttribute("currentSecond", Long.toString(milliSecond));
		model.addAttribute("onlineWideImage", onlineWideImage);
		model.addAttribute("onlineImageList", onlineImageList);
		model.addAttribute("imagePath", ConfigUtils.getString("system.cdn.static.path"));
		model.addAttribute("imagePathOnline", ConfigUtils.getString("edi.online.image.url"));
		model.addAttribute("subFolderName", subFolderName(StringUtils.trimToEmpty(pgmId)));
		model.addAttribute("newProdVarAttCnt", newProdVarAttCnt);
		model.addAttribute("prodVarAttList", prodVarAttList);
		model.addAttribute("prodDetailVO", prodDetailVO);

		if(wideImgYn.equals("Y")) {
			return "redirect:/edi/product/NEDMPRO0102.do?pgmId=" + pgmId + "&mode=" + mode + "&bman_no=" + (String) request.getParameter("bman_no");
		}
		else return "/edi/product/NEDMPRO0102";

	}

	/**
	 * 온라인 와이드 이미지 저장[코리안넷 전용]
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping(value = "/edi/product/NEDMPRO0102SaveWideImage.do")
//	public String onlineWideImageSaveKorNet(HttpServletRequest request, ModelMap model) throws Exception {
//		if (request == null || model == null) {
//			throw new TopLevelException("");
//		}
//
//		String pgmId = SecureUtil.splittingFilter(StringUtils.trimToEmpty((String) request.getParameter("pgmId")));
//		String mode = StringUtils.trimToEmpty((String) request.getParameter("mode"));
//		String uploadDir = makeSubFolderForWide(pgmId);
//		String uploadWideFieldCount = StringUtils.trimToEmpty((String) request.getParameter("uploadWideFieldCount")); // 파일개수
//		String onOffDivnCd = StringUtils.trimToEmpty((String) request.getParameter("onOffDivnCd"));
//		String newProdGenDivnCd = StringUtils.trimToEmpty((String) request.getParameter("newProdGenDivnCd")); //코리안넷에서 등록한 상품인지 EPC에서 등록한상품인지 구분
//
//
//
//		// 온라인 와이드 이미지 저장 [S]
//
//		// 온라인 이미지 파일 업로드.
//		// 업로드 할때 상품코드와 자연증가 seq변수 값으로 해당 이미지 파일명 설정.
//		Integer newSeq = 0;
//		MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;
//		Iterator fileIter = mptRequest.getFileNames();
//
//		while (fileIter.hasNext()) {
//			MultipartFile mFile = mptRequest.getFile((String) fileIter.next());
//
//			if (!mFile.isEmpty()) {
//				String newFileSource = uploadDir + "/" + pgmId + "_" + newSeq;
//				String fileFieldName = mFile.getName();
//				if (!fileFieldName.startsWith("front")) {
//					newFileSource = uploadDir + "/" + SecureUtil.splittingFilter(fileFieldName);
//				}
//
//				FileOutputStream frontImageStream = new FileOutputStream(newFileSource);
//				FileCopyUtils.copy(mFile.getInputStream(), frontImageStream);
//				// ImageUtilsThumbnail.resizeAutoForEPC(newFileSource); //구버전 이미지 리사이징 메소드
//
//				if (uploadFieldCount != null || !"".equals(uploadFieldCount)) {
//					Map<String, Object> paramMap = new HashMap<String, Object>();
//					paramMap.put("pgmId", pgmId);
//					paramMap.put("fileCount", uploadFieldCount);
//					nEDMPRO0020Service.updateNewProdImgCnt(paramMap);
//				}
//
//				imageFileMngService.purgeImageQCServer("01", newFileSource);
//				imageFileMngService.purgeCDNServer("01", newFileSource);
//
//				newSeq++;
//			}
//		}
//		// -- 온라인 와이드 이미지 저장
//
//
//
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("pgmId", StringUtils.trimToEmpty(pgmId));
//		paramMap.put("cfmFg", StringUtils.trimToEmpty(""));
//
//		// -----상품의 변형속성 카운트
//		int newProdVarAttCnt = nEDMPRO0020Service.selectNewProdVarAttCnt(StringUtils.trimToEmpty(pgmId), StringUtils.trimToEmpty(paramMap.get("cfmFg").toString()));
//
//		// ----- 상품의 변형속성 리스트 조회[NEDMPRO0040.xml에 있는 쿼리 사용]
//		List<NEDMPRO0042VO> prodVarAttList = nEDMPRO0040Service.selectNewSrcmkCd(paramMap, request);
//
//		// -----상품의 상세정보 조회[NEDMPRO0040.xml에 있는 쿼리 사용]
//		NEDMPRO0020VO prodDetailVO = nEDMPRO0020Service.selectNewTmpProductDetailInfo(paramMap);
//
//		// -----온라인 이미지 파일 목록조회.
//		String onlineUploadFolder = makeSubFolderForOnline(StringUtils.trimToEmpty(pgmId));
//		ArrayList<String> onlineImageList = new ArrayList<String>();
//		File dir = new File(onlineUploadFolder);
//		FileFilter fileFilter = new WildcardFileFilter(StringUtils.trimToEmpty(pgmId) + "*");
//		File[] files = dir.listFiles(fileFilter);
//		for (int j = 0; j < files.length; j++) {
//			String fileName = files[j].getName();
//			if (fileName.lastIndexOf(".") < 0) {
//				onlineImageList.add(fileName);
//			}
//		}
//
//		// 이미지 파일에 전달할 시간 파라미터. 브라우저 캐시 기능때문에 동일한 이름의 이미지 파일이
//		// 파일만 바뀌었을 경우, 브라우저에서 이전 이미지가 보여지는 것을 방지해줌.
//		long milliSecond = System.currentTimeMillis();
//
//		model.addAttribute("currentSecond", Long.toString(milliSecond));
//		model.addAttribute("onlineImageList", onlineImageList);
//		model.addAttribute("imagePath", ConfigUtils.getString("system.cdn.static.path"));
//		model.addAttribute("imagePathOnline", ConfigUtils.getString("edi.online.image.url"));
//		model.addAttribute("subFolderName", subFolderName(StringUtils.trimToEmpty(pgmId)));
//		model.addAttribute("newProdVarAttCnt", newProdVarAttCnt);
//		model.addAttribute("prodVarAttList", prodVarAttList);
//		model.addAttribute("prodDetailVO", prodDetailVO);
//
//		return "/edi/product/NEDMPRO0102";
//	}

	/**
	 * 온라인 이미지 삭제
	 * @param pgmId
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/deleteProdOnlineImg.do")
	public String deleteProdOnlineImg(@RequestParam String pgmId, HttpServletRequest request, ModelMap model) throws Exception {

		if ("".equals(pgmId) || request == null || model == null) {
			throw new TopLevelException("");
		}

		String prodCd = SecureUtil.splittingFilter(pgmId.split("\\_")[0]);
		String rtnUrl = "redirect:/edi/product/NEDMPRO0022.do?pgmId=" + prodCd;

		if ("1".equals(request.getParameter("onOffDivnCd"))) { // 온라인상품인 경우에 "온라인상품등록(일반)" 링크로 변경
			rtnUrl = "redirect:/edi/product/NEDMPRO0032.do?pgmId=" + prodCd;
		}

		// -----상품코드+seq문자열

		int selectedProdIdx = Integer.parseInt(pgmId.split("\\_")[1]);

		String fullPath = makeSubFolderForOnline(prodCd);

		File dir = new File(fullPath);
		FileFilter fileFilter = new WildcardFileFilter(pgmId + "*");
		File[] files = dir.listFiles(fileFilter);

		boolean isDel = false;

		for (int j = 0; j < 5; j++) {
			if (!new File(fullPath + "/" + prodCd + "_" + j).exists()) {
				continue;
			}

			// 전체 목록중 삭제 선택된 파일의 시퀀스가 같으면 삭제
			if (j == selectedProdIdx) {
				isDel = FileUtils.deleteQuietly(new File(fullPath + "/" + pgmId));
				FileUtils.deleteQuietly(new File(fullPath + "/" + pgmId + "_80.jpg"));
				FileUtils.deleteQuietly(new File(fullPath + "/" + pgmId + "_90.jpg"));
				FileUtils.deleteQuietly(new File(fullPath + "/" + pgmId + "_94.jpg"));
				FileUtils.deleteQuietly(new File(fullPath + "/" + pgmId + "_120.jpg"));
				FileUtils.deleteQuietly(new File(fullPath + "/" + pgmId + "_140.jpg"));
				FileUtils.deleteQuietly(new File(fullPath + "/" + pgmId + "_158.jpg"));
				FileUtils.deleteQuietly(new File(fullPath + "/" + pgmId + "_185.jpg"));
				FileUtils.deleteQuietly(new File(fullPath + "/" + pgmId + "_204.jpg"));
				FileUtils.deleteQuietly(new File(fullPath + "/" + pgmId + "_208.jpg"));
				FileUtils.deleteQuietly(new File(fullPath + "/" + pgmId + "_210.jpg"));
				FileUtils.deleteQuietly(new File(fullPath + "/" + pgmId + "_220.jpg"));
				FileUtils.deleteQuietly(new File(fullPath + "/" + pgmId + "_250.jpg"));
				FileUtils.deleteQuietly(new File(fullPath + "/" + pgmId + "_272.jpg"));
				FileUtils.deleteQuietly(new File(fullPath + "/" + pgmId + "_308.jpg"));
				FileUtils.deleteQuietly(new File(fullPath + "/" + pgmId + "_320.jpg"));
				FileUtils.deleteQuietly(new File(fullPath + "/" + pgmId + "_375.jpg"));
				FileUtils.deleteQuietly(new File(fullPath + "/" + pgmId + "_450.jpg"));
				FileUtils.deleteQuietly(new File(fullPath + "/" + pgmId + "_464.jpg"));
				FileUtils.deleteQuietly(new File(fullPath + "/" + pgmId + "_500.jpg"));
				FileUtils.deleteQuietly(new File(fullPath + "/" + pgmId + "_584.jpg"));
				FileUtils.deleteQuietly(new File(fullPath + "/" + pgmId + "_640.jpg"));
			}

			// 순환 돌다가 삭제 선택된 파일의 시퀀스보다 순환문 내의 시퀀스가 크면,
			// 순환문내의 시퀀스 -1로 파일 생성하고 해당 순환문의 시퀀스 파일 삭제
			if (j > selectedProdIdx) {
				int newProductPrefix = j - 1;
				String newFileSource = fullPath + "/" + prodCd + "_" + newProductPrefix;
				String currentFileSource = fullPath + "/" + prodCd + "_" + j;

				FileCopyUtils.copy(new File(currentFileSource), new File(newFileSource));
				// ImageUtilsThumbnail.resizeAutoForEPC(newFileSource); // 구버전 이미지 리사이징 메소드

				imageFileMngService.purgeImageQCServer("01", prodCd + "_" + newProductPrefix);
				imageFileMngService.purgeCDNServer("01", prodCd + "_" + newProductPrefix);

				FileUtils.deleteQuietly(new File(currentFileSource));
				FileUtils.deleteQuietly(new File(currentFileSource + "_80.jpg"));
				FileUtils.deleteQuietly(new File(currentFileSource + "_90.jpg"));
				FileUtils.deleteQuietly(new File(currentFileSource + "_94.jpg"));
				FileUtils.deleteQuietly(new File(currentFileSource + "_120.jpg"));
				FileUtils.deleteQuietly(new File(currentFileSource + "_140.jpg"));
				FileUtils.deleteQuietly(new File(currentFileSource + "_158.jpg"));
				FileUtils.deleteQuietly(new File(currentFileSource + "_185.jpg"));
				FileUtils.deleteQuietly(new File(currentFileSource + "_204.jpg"));
				FileUtils.deleteQuietly(new File(currentFileSource + "_208.jpg"));
				FileUtils.deleteQuietly(new File(currentFileSource + "_210.jpg"));
				FileUtils.deleteQuietly(new File(currentFileSource + "_220.jpg"));
				FileUtils.deleteQuietly(new File(currentFileSource + "_250.jpg"));
				FileUtils.deleteQuietly(new File(currentFileSource + "_272.jpg"));
				FileUtils.deleteQuietly(new File(currentFileSource + "_308.jpg"));
				FileUtils.deleteQuietly(new File(currentFileSource + "_320.jpg"));
				FileUtils.deleteQuietly(new File(currentFileSource + "_375.jpg"));
				FileUtils.deleteQuietly(new File(currentFileSource + "_450.jpg"));
				FileUtils.deleteQuietly(new File(currentFileSource + "_464.jpg"));
				FileUtils.deleteQuietly(new File(currentFileSource + "_500.jpg"));
				FileUtils.deleteQuietly(new File(currentFileSource + "_584.jpg"));
				FileUtils.deleteQuietly(new File(currentFileSource + "_640.jpg"));
			}
		}

		// 온라인 상품에만 적용함 (온오프상품에는 비적용)
		// 온오프 상품에도 적용하려면 바로 아래 if 문만 제거해주면 됨
		if ("1".equals(request.getParameter("onOffDivnCd"))) {
			if (isDel) {
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("pgmId", prodCd);
				nEDMPRO0020Service.updateNewProdImgCntMinus(paramMap);
			}
		}

		return rtnUrl;
	}

	/**
	 * 오프라인 이미지 업로드 처리
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/NEDMPRO0022SaveOffImg.do")
	public String saveNewProdOffImg(HttpServletRequest request, ModelMap model) throws Exception {
		if (request == null || model == null) {
			throw new TopLevelException("");
		}
		String mode = StringUtils.trimToEmpty(request.getParameter("mode"));
		nEDMPRO0020Service.saveSaleImg(request);

		/*String pgmId		= StringUtils.trimToEmpty((String)request.getParameter("pgmId"));
		String uploadDir    = makeSubFolderForOffline(pgmId);

		MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest)request;
		Iterator fileIter = mptRequest.getFileNames();

		while (fileIter.hasNext()) {
			MultipartFile mFile = mptRequest.getFile((String)fileIter.next());

			if(!mFile.isEmpty()) {
				String ext = FilenameUtils.getExtension(mFile.getOriginalFilename()).toLowerCase();
				String postfix = mFile.getName();

				if(postfix.endsWith("front")) {
					postfix = postfix.replaceAll("_front", ".1");
				}
				if(postfix.endsWith("side")) {
					postfix = postfix.replaceAll("_side", ".2");
				}
				if(postfix.endsWith("top")) {
					postfix = postfix.replaceAll("_top", ".3");
				}
				if(postfix.endsWith("back")) {
					postfix = postfix.replaceAll("_back", ".4");
				}

				// String baseFileSource = uploadDir+"/"+offlineImage.getNewProductCode()+"_"+postfix;
				String newFileSource = uploadDir+"/"+postfix+".jpg";//+ext;

				FileOutputStream frontImageStream = new FileOutputStream(newFileSource);
				FileCopyUtils.copy(mFile.getInputStream(), frontImageStream);

			}
		}*/

		String pgmId = StringUtils.trimToEmpty((String) request.getParameter("pgmId"));

		return "redirect:/edi/product/NEDMPRO0022.do?pgmId=" + pgmId + "&mode=" + mode;
	}

	/**
	 * Desc : 오프라인 이미지 업로드 처리.
	 * @Method Name : onSubmitOfflineImageUpload
	 * @param OfflineImage
 	 * @param HttpServletRequest
 	 * @param Model
	 * @return String
	 */
	/*@RequestMapping(value="/edi/product/PEDMPRO000202", method=RequestMethod.POST)
	public String onSubmitOfflineImageUpload(OfflineImage offlineImage, HttpServletRequest request, Model model ) throws Exception   {

		  String uploadDir    = makeSubFolderForOffline(offlineImage.getNewProductCode());

		 // offlineImage.saveOfflineImage(uploadDir);

		  MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest)request;
		  Iterator fileIter = mptRequest.getFileNames();

		  while (fileIter.hasNext()) {
			 MultipartFile mFile = mptRequest.getFile((String)fileIter.next());

			 if(!mFile.isEmpty()) {
				 String ext = FilenameUtils.getExtension(mFile.getOriginalFilename()).toLowerCase();
				 String postfix = mFile.getName();
				 if(postfix.endsWith("front")) {
					 postfix = postfix.replaceAll("_front", ".1");
				 }
				 if(postfix.endsWith("side")) {
					 postfix = postfix.replaceAll("_side", ".2");
				 }
				 if(postfix.endsWith("top")) {
					 postfix = postfix.replaceAll("_top", ".3");
				 }
				 if(postfix.endsWith("back")) {
					 postfix = postfix.replaceAll("_back", ".4");
				 }
				// String baseFileSource = uploadDir+"/"+offlineImage.getNewProductCode()+"_"+postfix;
				 String newFileSource = uploadDir+"/"+postfix+".jpg";//+ext;

				 FileOutputStream frontImageStream = new FileOutputStream(newFileSource);
				 FileCopyUtils.copy(mFile.getInputStream(), frontImageStream);


				 FileOutputStream baseFileStream = new FileOutputStream(baseFileSource);
				 FileCopyUtils.copy(mFile.getInputStream(), baseFileStream);

			 }

		  }
		return "redirect:/edi/product/PEDMPRO000301.do?newProductCode="+offlineImage.getNewProductCode();
	}*/


	/****************************** 상품속성 TAB STart ******************************/

	/**
	 * 상품속성화면 Init
	 * @param nEDMPRO0024VO
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/NEDMPRO0021.do")
	public String regNewProductOnOffAttInit(NEDMPRO0020VO nEDMPRO0020VO, ModelMap model, HttpServletRequest request) throws Exception {

		if (model == null || request == null) {
			throw new TopLevelException("");
		}

		//----- 상품의 상세정보 가져오기 ----------
		Map<String, Object>	paramMap = new HashMap<String, Object>();

		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		model.addAttribute("epcLoginVO", epcLoginVO);

		paramMap.put("pgmId", nEDMPRO0020VO.getPgmId());
		paramMap.put("cfmFg", StringUtils.trimToEmpty(nEDMPRO0020VO.getCfmFg()));

		NEDMPRO0020VO prodDetailInfo = nEDMPRO0020Service.selectNewTmpProductDetailInfo(paramMap);
		//------------------------------------------------------------

		model.addAttribute("prodDetailInfo", prodDetailInfo);

		if (prodDetailInfo != null) {
			//----- 소분류에 해당하는 변형속성 그룹 및 그룹별 CLASS 가져오기 ----------
			NEDMPRO0024VO varAttVO = new NEDMPRO0024VO();
			varAttVO.setPgmId(prodDetailInfo.getPgmId());
			varAttVO.setProdRange(prodDetailInfo.getL3Cd());
			varAttVO.setCfmFg(prodDetailInfo.getCfmFg());
			varAttVO.setGrpCd(prodDetailInfo.getGrpCd());

			// logger.debug("----->" + nEDMPRO0020Service.selectProdVarAttClass(varAttVO));

			//----- 변형속성 정보 가져오기 -----
			model.addAttribute("classList", nEDMPRO0020Service.selectClass(varAttVO)); // 소분류별 변형속성 그룹
			model.addAttribute("prodVarAttClass", nEDMPRO0020Service.selectProdVarAttClass(varAttVO)); // 등록된 변형속성의 CLASS 조회
			model.addAttribute("prodVarAttOpt", nEDMPRO0020Service.selectProdVarAttOpt(varAttVO)); // 등록된 변형속성의 전체 속성정보 조회
			//model.addAttribute("attGrpOpt",	nEDMPRO0020Service.selectAttGrpOpt(varAttVO));	// 소분류별 변형속성 그룹 옵션
			//------------------------------------------------------------

			//----- 소분류별 그룹분석속성 정보 가져오기 -----
			//model.addAttribute("grpAtt", nEDMPRO0020Service.selectGrpAtt(prodDetailInfo.getL3Cd())); // 소분류별 그룹분석속성 조회
			model.addAttribute("grpAtt", nEDMPRO0020Service.selectGrpAttOne(varAttVO)); // 소분류별 그룹분석속성 조회

			//model.addAttribute("grpAttOpt", nEDMPRO0020Service.selectGrpAttOpt(varAttVO)); // 소분류별 그룹분석속성 속성값 Option 조회
			model.addAttribute("grpAttOpt", nEDMPRO0020Service.selectGrpAttOptOne(varAttVO)); // 소분류별 그룹분석속성 속성값 Option 조회

			//------------------------------------------------------------

			//----- 해당 변형속성의 모든 Option 값 가져오기 ----------
			//NEDMPRO0024VO varAttListVO = new NEDMPRO0024VO();
			//varAttListVO.setProdRange(prodDetailInfo.getL3Cd());
			//model.addAttribute("attGrpAllOpt", nEDMPRO0020Service.selectAttGrpOptVar(varAttListVO));
			//------------------------------------------------------------

			//----- 등록된 상품변형속성 조회
			model.addAttribute("varAtt", nEDMPRO0020Service.selectProdVarAtt(nEDMPRO0020VO.getPgmId(), nEDMPRO0020VO.getCfmFg()));

			// ---[210125 EC조립2]S
			//----- 등록된 쿼리값 조회
			model.addAttribute("itemListInTemp", 	nEDMPRO0020Service.selectProductItemListInTemp(nEDMPRO0020VO.getPgmId()));
			// ---[210125 EC조립2]E
		}

		return "edi/product/NEDMPRO0021";
	}

	/**
	 * 변형속성 그룹별 상세 속성 조회
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	/*@RequestMapping(value="/edi/product/selectAttGrpOptVar.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> selectAttGrpOptVar(@RequestBody NEDMPRO0024VO nEDMPRO0024VO, HttpServletRequest request) throws Exception {
		if (nEDMPRO0024VO == null || request == null) {
			throw new TopLevelException("");
		}

		Map<String, Object>	resultMap	=	new HashMap<String, Object>();
		List<NEDMPRO0024VO>	resultList	=	nEDMPRO0020Service.selectAttGrpOptVar(nEDMPRO0024VO);
		resultMap.put("attGrpOptVar", resultList);

		return resultMap;
	}*/

	/**
	 * 판매코드 중복체크
	 * @param nEDMPRO0024VO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/selectSellCdCount.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> selectSellCdCount(@RequestBody NEDMPRO0024VO nEDMPRO0024VO, HttpServletRequest request) throws Exception {
		if (nEDMPRO0024VO == null || request == null) {
			throw new TopLevelException("");
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		int retCnt = nEDMPRO0020Service.selectSellCdCount(nEDMPRO0024VO.getSellCd());
		resultMap.put("sellCdCnt", retCnt);

		return resultMap;
	}

	/**
	 * 판매코드로 위해상품으로 등록된 판매코드인지 조회
	 * @param nEDMPRO0024VO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/selectChkDangerProdCnt.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> selectChkDangerProdCnt(@RequestBody NEDMPRO0024VO nEDMPRO0024VO, HttpServletRequest request) throws Exception {
		if (nEDMPRO0024VO == null || request == null) {
			throw new TopLevelException("");
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();

		//-----2016.04.06 버전[현재 사용안함 2016.04.08 변경된걸로 사용]
		//int retCnt = nEDMPRO0020Service.selectChkDangerProdCnt(nEDMPRO0024VO.getSellCd());

		//-----[IF_DT 가 최근일자 기준으로 CFM_FG 컬럼값이  '2' 일 경우 위해상품의 판매코드로 결정] 2016.04.08 추가  by song min kyo
		String dangerCfmFg = nEDMPRO0020Service.selectChkDangerProdCnt_3(nEDMPRO0024VO.getSellCd());

		resultMap.put("dangerCfmFg", dangerCfmFg);
		return resultMap;
	}

	/**
	 * 상품 변형속성 저장
	 * @param nEDMPRO0024VO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/saveVarAtt.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> saveVarAtt(@RequestBody NEDMPRO0024VO nEDMPRO0024VO, HttpServletRequest request) throws Exception {
		if (nEDMPRO0024VO == null || request == null) {
			throw new TopLevelException("");
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		String retMsg = nEDMPRO0020Service.saveVarAtt(nEDMPRO0024VO, request);
		resultMap.put("msgCd", retMsg);

		return resultMap;
	}

	/**
	 * 변형속성 그룹별 상세 속성 조회
	 * 2016.03.17 해당 메소드 실행되는 부분 없어서 주석처리 by song min kyo
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	/*@RequestMapping(value="/edi/product/selectProdVarAtt.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> selectProdVarAtt(@RequestBody NEDMPRO0024VO nEDMPRO0024VO, HttpServletRequest request) throws Exception {
		if (nEDMPRO0024VO == null || request == null) {
			throw new TopLevelException("");
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<NEDMPRO0024VO> resultList = nEDMPRO0020Service.selectProdVarAtt(nEDMPRO0024VO.getPgmId());

		resultMap.put("varAtt", resultList);

		return resultMap;
	}*/

	/**
	 * 변형속성, 변형속성 Option 가져오기
	 * @param nEDMPRO0024VO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/selectClassVarAtt.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> selectClassVarAtt(@RequestBody NEDMPRO0024VO nEDMPRO0024VO, HttpServletRequest request) throws Exception {
		if (nEDMPRO0024VO == null || request == null) {
			throw new TopLevelException("");
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 변형속성
		List<NEDMPRO0024VO> varAtt = nEDMPRO0020Service.selectClassVarAtt(nEDMPRO0024VO);

		// 변형속성 Option
		List<NEDMPRO0024VO> varAttOpt = nEDMPRO0020Service.selectClassVarAttOpt(nEDMPRO0024VO);

		resultMap.put("varAtt", varAtt);
		resultMap.put("varAttOpt", varAttOpt);

		return resultMap;
	}

	/****************************** 상품속성 TAB End ******************************/


	/**
	 * 그룹분석속성 저장
	 * @param nEDMPRO0024VO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/saveGrpAtt.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> saveGrpAtt(@RequestBody NEDMPRO0024VO nEDMPRO0024VO, HttpServletRequest request) throws Exception {
		if (nEDMPRO0024VO == null || request == null) {
			throw new TopLevelException("");
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		String retMsg = nEDMPRO0020Service.saveGrpAtt(nEDMPRO0024VO, request);
		resultMap.put("msgCd", retMsg);

		return resultMap;
	}


	// ---[210125 EC조립3]S
	/**
	 * EC속성 저장
	 * @param nEDMPRO0024VO
	 * @param request
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/edi/product/saveEcAtt.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> saveEcAtt(@RequestBody NEDMPRO0020VO nEDMPRO0020VO, HttpServletRequest request) throws Exception {
		if (nEDMPRO0020VO == null || request == null) {
			throw new TopLevelException("");
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		String retMsg = nEDMPRO0020Service.saveEcAtt(nEDMPRO0020VO, request);

		resultMap.put("msgCd", retMsg);

		return resultMap;
	}
	// ---[210125 EC조립3]E


	/**
	 * 상품정보 복사[온오프는 상품범주와 소분류가 변경되지 않았을 경우에만 마스터 정보, 변형/분석속성정보, 이미지 정보까지 모두 복사 , 온라인은 AS-IS 그대로 적용]
	 * @param nEDMPRO0020VO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/insertNewProductCopyMST.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> insertNewProductCopyMST(@RequestBody NEDMPRO0020VO nEDMPRO0020VO, HttpServletRequest request) throws Exception {
		return nEDMPRO0020Service.insertNewProductCopyMST(nEDMPRO0020VO);
	}

	/**
	 * 코리안넷 규격 상품등록 화면 Init
	 *
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/NEDMPRO0100.do")
	public String regNewProductOnOffInit(NEDMPRO0020VO nEDMPRO0020VO, ModelMap model, HttpServletRequest request) throws Exception {

		if (model == null || request == null) {
			throw new TopLevelException("");
		}

		Map<String, Object> paramMap = new HashMap<String, Object>();

		String bman_no = StringUtils.trimToEmpty(request.getParameter("bman_no"));
		String issue_date = StringUtils.trimToEmpty(request.getParameter("issue_date"));

		nEDMPRO0020VO.setBman_no(bman_no);

		model.addAttribute("entpCdCnt", nEDMPRO0020Service.selectExistxKoreanNetVendor(nEDMPRO0020VO)); //넘어오는 사업자 번호로 해당 협력사 존재 유무 체크
		model.addAttribute("teamList", commonProductService.selectNteamList(paramMap, request)); //팀리스트
		model.addAttribute("seasonYearList", commonProductService.selectNseasonYear(paramMap)); // 계절년도
		model.addAttribute("nowYear", DateUtil.getToday("yyyy")); // 현재년도
		model.addAttribute("namoPath", ConfigUtils.getString("namo.link.path")); // 나모 에디터 패스
		model.addAttribute("issueDate", "20" + issue_date.replaceAll("\\/", "-")); // 나모 에디터 패스

		return "edi/product/NEDMPRO0100";
	}

	/**
	 * 코리안넷 상품등록 상세정보 조회
	 * @param pgmId
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/NEDMPRO0100Detail.do")
	public String newProdDetailInfoKoreanNet(@RequestParam String pgmId, ModelMap model, HttpServletRequest request) throws Exception {

		if (model == null || request == null) {
			throw new TopLevelException("");
		}

		Map<String, Object> paramMap = new HashMap<String, Object>();

		model.addAttribute("teamList", commonProductService.selectNteamList(paramMap, request)); // 팀리스트
		model.addAttribute("seasonYearList", commonProductService.selectNseasonYear(paramMap)); // 계절년도
		model.addAttribute("nowYear", DateUtil.getToday("yyyy")); // 현재년도

		// -----상품상세정보
		paramMap.put("pgmId", StringUtils.trimToEmpty(pgmId));
		paramMap.put("cfmFg", "");
		NEDMPRO0020VO newProdDetailInfo = nEDMPRO0020Service.selectNewTmpProductDetailInfo(paramMap);
		model.addAttribute("entpCd", newProdDetailInfo.getEntpCd()); // 협력업체코드

		// ---팀의 대분류
		List<CommonProductVO> resultList = commonProductService.selectNgetL1list(paramMap, request);

		// 등록된 신상품의 소분류의 표시단위, 표시기준수량 default 정보 존재 여부 조회
		paramMap.put("l3Cd", newProdDetailInfo.getL3Cd());
		int newProdEssenTialExistCnt = nEDMPRO0020Service.selectNewProdEssentialExistInfo(paramMap);
		
		model.addAttribute("newProdEssenTialExistCnt", newProdEssenTialExistCnt);
		model.addAttribute("newProdDetailInfo", newProdDetailInfo);
		model.addAttribute("namoPath", ConfigUtils.getString("namo.link.path")); // 나모 에디터 패스

		/* ****************************** */
		// 20180919 전산정보팀 이상구 추가.
		model.addAttribute("tpcPrdKeywordList", nEDMPRO0020Service.selectTpcPrdKeywordList(newProdDetailInfo.getPgmId()));
	    /* ****************************** */
		model.addAttribute("ecCategory", nEDMPRO0020Service.selectEcCategoryByProduct(newProdDetailInfo.getPgmId()));
		
		return "edi/product/NEDMPRO0100";
	}

	/**
	 * 코리안넷 변형속성 탭
	 * @param nEDMPRO0020VO
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/NEDMPRO0101.do")
	public String regNewProductOnOffAttInitKoreanNet(NEDMPRO0020VO nEDMPRO0020VO, ModelMap model, HttpServletRequest request) throws Exception {

		if (model == null || request == null) {
			throw new TopLevelException("");
		}

		//----- 상품의 상세정보 가져오기 ----------
		Map<String, Object>	paramMap = new HashMap<String, Object>();
		paramMap.put("pgmId", nEDMPRO0020VO.getPgmId());
		paramMap.put("cfmFg", nEDMPRO0020VO.getCfmFg());

		NEDMPRO0020VO prodDetailInfo = nEDMPRO0020Service.selectNewTmpProductDetailInfo(paramMap);
		//------------------------------------------------------------

		model.addAttribute("prodDetailInfo", prodDetailInfo);

		if (prodDetailInfo != null) {
			//----- 소분류에 해당하는 변형속성 그룹 및 그룹별 CLASS 가져오기 ----------
			NEDMPRO0024VO varAttVO = new NEDMPRO0024VO();
			varAttVO.setPgmId(prodDetailInfo.getPgmId());
			varAttVO.setProdRange(prodDetailInfo.getL3Cd());
			varAttVO.setGrpCd(prodDetailInfo.getGrpCd());

			// logger.debug("----->" + nEDMPRO0020Service.selectProdVarAttClass(varAttVO));

			//----- 변형속성 정보 가져오기 -----
			model.addAttribute("classList", nEDMPRO0020Service.selectClass(varAttVO)); // 소분류별 변형속성 그룹
			model.addAttribute("prodVarAttClass", nEDMPRO0020Service.selectProdVarAttClass(varAttVO)); // 등록된 변형속성의 CLASS 조회
			model.addAttribute("prodVarAttOpt", nEDMPRO0020Service.selectProdVarAttOpt(varAttVO)); // 등록된 변형속성의 전체 속성정보 조회
			//model.addAttribute("attGrpOpt", nEDMPRO0020Service.selectAttGrpOpt(varAttVO)); // 소분류별 변형속성 그룹 옵션
			//------------------------------------------------------------

			//----- 소분류별 그룹분석속성 정보 가져오기 -----
			//model.addAttribute("grpAtt", nEDMPRO0020Service.selectGrpAtt(prodDetailInfo.getL3Cd())); // 소분류별 그룹분석속성 조회
			//model.addAttribute("grpAttOpt", nEDMPRO0020Service.selectGrpAttOpt(varAttVO)); // 소분류별 그룹분석속성 속성값 Option 조회

			model.addAttribute("grpAtt", nEDMPRO0020Service.selectGrpAttOne(varAttVO)); // 소분류별 그룹분석속성 조회
			model.addAttribute("grpAttOpt", nEDMPRO0020Service.selectGrpAttOptOne(varAttVO)); // 소분류별 그룹분석속성 속성값 Option 조회
			//------------------------------------------------------------

			//----- 해당 변형속성의 모든 Option 값 가져오기 ----------
			//NEDMPRO0024VO varAttListVO = new NEDMPRO0024VO();
			//varAttListVO.setProdRange(prodDetailInfo.getL3Cd());
			//model.addAttribute("attGrpAllOpt", nEDMPRO0020Service.selectAttGrpOptVar(varAttListVO));
			//------------------------------------------------------------

			//----- 등록된 상품변형속성 조회
			model.addAttribute("varAtt", nEDMPRO0020Service.selectProdVarAtt(nEDMPRO0020VO.getPgmId(), nEDMPRO0020VO.getCfmFg()));
			//----- 등록된 EC상품속성 조회 [EC조립4]S

			model.addAttribute("itemListInTemp", 	nEDMPRO0020Service.selectProductItemListInTemp(nEDMPRO0020VO.getPgmId()));

			//-----[EC조립4]E
		}

		return "edi/product/NEDMPRO0101";
	}

	/**
	 * 코리안넷 이미지 정보 탭
	 * @param nEDMPRO0020VO
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/NEDMPRO0102.do")
	public String newProductImageTabFormInitKoreanNet(NEDMPRO0020VO nEDMPRO0020VO, ModelMap model, HttpServletRequest request) throws Exception {
		if (nEDMPRO0020VO == null || model == null || request == null) {
			throw new TopLevelException("");
		}

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pgmId", StringUtils.trimToEmpty(nEDMPRO0020VO.getPgmId()));
		paramMap.put("cfmFg", StringUtils.trimToEmpty(nEDMPRO0020VO.getCfmFg()));

		//-----상품의 변형속성 카운트
		int newProdVarAttCnt = nEDMPRO0020Service.selectNewProdVarAttCnt(StringUtils.trimToEmpty(nEDMPRO0020VO.getPgmId()), StringUtils.trimToEmpty(nEDMPRO0020VO.getCfmFg()));

		//----- 상품의 변형속성 리스트 조회[NEDMPRO0040.xml에 있는 쿼리 사용]
		List<NEDMPRO0042VO> prodVarAttList = nEDMPRO0040Service.selectNewSrcmkCd(paramMap, request);

		//-----상품의 상세정보 조회[NEDMPRO0040.xml에 있는 쿼리 사용]
		NEDMPRO0020VO prodDetailVO = nEDMPRO0020Service.selectNewTmpProductDetailInfo(paramMap);

		//-----온라인 이미지 파일 목록조회.
		String onlineUploadFolder	 = makeSubFolderForOnline(StringUtils.trimToEmpty(nEDMPRO0020VO.getPgmId()));
		ArrayList<String> onlineImageList = new ArrayList<String>();
		File dir = new File(onlineUploadFolder);
		FileFilter fileFilter = new WildcardFileFilter(StringUtils.trimToEmpty(nEDMPRO0020VO.getPgmId())+"*");
		File[] files = dir.listFiles(fileFilter);
		for (int j = 0; j < files.length; j++) {
			String fileName = files[j].getName();
			if( fileName.lastIndexOf(".") < 0 )  {
				onlineImageList.add(fileName);
			}
		}

		String wideUploadFolder = makeSubFolderForWide(nEDMPRO0020VO.getPgmId());
		ArrayList<String> onlineWideImage = new ArrayList<String>();
		File wideDir = new File(wideUploadFolder);
		FileFilter widefileFilter = new WildcardFileFilter(StringUtils.trimToEmpty(nEDMPRO0020VO.getPgmId()) + "*");
		File[] widefiles = wideDir.listFiles(widefileFilter);
		for (int j = 0; j < widefiles.length; j++) {
			String wideFileName = widefiles[j].getName();
			if (wideFileName.lastIndexOf(".") < 0) {
				onlineWideImage.add(wideFileName);
			}
		}

		//오프라인 pog이미지 파일 목록 조회. 규격, 패션 모두 포함 됨.
		/* String offlineUploadFolder = makeSubFolderForOffline(StringUtils.trimToEmpty(nEDMPRO0020VO.getPgmId()));
		 ArrayList<String> offlineImageList = new ArrayList<String>();
		 File offlineDir = new File(offlineUploadFolder);
		 FileFilter offlineFileFilter = new WildcardFileFilter(nEDMPRO0020VO.getProdImgId()+"*");
		 File[] offlineFiles = offlineDir.listFiles(offlineFileFilter);
		 for (int jj = 0; jj < offlineFiles.length; jj++) {
			 offlineImageList.add(offlineFiles[jj].getName());
		 }*/

		//이미지 파일에 전달할 시간 파라미터. 브라우저 캐시 기능때문에 동일한 이름의 이미지 파일이
		//파일만 바뀌었을 경우, 브라우저에서 이전 이미지가 보여지는 것을 방지해줌.
		long milliSecond = System.currentTimeMillis();

		model.addAttribute("onlineWideImage", onlineWideImage);
		model.addAttribute("currentSecond", Long.toString(milliSecond));
		model.addAttribute("onlineImageList", onlineImageList);
		model.addAttribute("imagePathOnline", ConfigUtils.getString("edi.online.image.url"));
		model.addAttribute("imagePath", ConfigUtils.getString("system.cdn.static.path"));
		model.addAttribute("subFolderName", subFolderName(StringUtils.trimToEmpty(nEDMPRO0020VO.getPgmId())));
		model.addAttribute("newProdVarAttCnt", newProdVarAttCnt);
		model.addAttribute("prodVarAttList", prodVarAttList);
		model.addAttribute("prodDetailVO", prodDetailVO);
		return "edi/product/NEDMPRO0102";
	}

	/**
	 * 코리안넷 물류바코드 정보 탭
	 * @param nEDMPRO0020VO
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/NEDMPRO0103.do")
	public String newProductLogiBcdTabFormInitKoreanNet(NEDMPRO0020VO nEDMPRO0020VO, ModelMap model, HttpServletRequest request) throws Exception {
		if (nEDMPRO0020VO == null || model == null || request == null) {
			throw new TopLevelException("");
		}

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pgmId", StringUtils.trimToEmpty(nEDMPRO0020VO.getPgmId()));

		// -----상품의 상세정보 조회[NEDMPRO0040.xml에 있는 쿼리 사용]
		NEDMPRO0020VO prodDetailVO = nEDMPRO0020Service.selectNewTmpProductDetailInfo(paramMap);
		List<NEDMPRO0042VO> classList = nEDMPRO0040Service.selectNewSrcmkCd(paramMap, request);

		model.addAttribute("prodDetailVO", prodDetailVO);
		model.addAttribute("today", DateUtil.formatDate(DateUtil.addDay(DateUtil.getToday("yyyy-MM-dd"), 1), "-"));
		model.addAttribute("classListCnt", nEDMPRO0220Service.selectImsiProdClassListCnt((String) paramMap.get("pgmId"))); //상품의 등록된 속성 리스트 카운트
		model.addAttribute("classList", classList); //상품의 등록된 속성 리스트

		return "edi/product/NEDMPRO0103";
	}

	/**
	 * 상품소분류의 변형속성 존재유무 체크
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/selectVarAttCnt.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object>selectVarAttCnt(@RequestBody Map<String, Object> paramMap, HttpServletRequest request) throws Exception {
		return nEDMPRO0020Service.selectVarAttCnt(paramMap);
	}

	/**
	 * 코리안넷 협력사 리스트
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/selectKoreanNetEntpCdList.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object>selectKoreanNetEntpCdList(@RequestBody Map<String, Object> paramMap, HttpServletRequest request) throws Exception {
		return nEDMPRO0020Service.selectKoreanNetEntpCdList(paramMap);
	}

	/**
	 * 소분류에 따른 이미지 등록제한 관리 정보 조회
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/selectProdEssentialInfo.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> selectProdEssentialInfo(@RequestBody Map<String, Object> paramMap, HttpServletRequest request) throws Exception {
		return nEDMPRO0020Service.selectProdEssentialInfo(paramMap);
	}

	/* ****************************** */
	//20180817 - 전산정보팀 이상구 추가.
	/**
	 * [온오프전용] 상품키워드 삭제
	 * @Method delOnlineKeywordInfo
	 * @param NewProduct
	 * @param HttpServletRequest
	 * @param Model
	 * @return view 페이지
	 */
	@RequestMapping(value = "/edi/product/delOnOffKeywordInfo.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object>delOnlineKeywordInfo(@RequestBody Map<String, Object> paramMap, HttpServletRequest reuqest) throws Exception {
		return nEDMPRO0020Service.deleteTpcPrdKeyword(paramMap);
	}
	//20180817 - 상품키워드 입력 기능 추가
	/* ****************************** */

	/**
	 * 온라인 전시 카테고리 조회 For Grid
	 * @param request
	 * @param response
	 * @return HashMap<String,Object>
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/edi/product/NEDMPRO0020Category.do")
	public HashMap<String,Object> selectCategoryInList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String,Object> gridMap = new HashMap<String, Object>();
		
		int totalCount = 0;		//전체 페이지 카운트
		int totalPage = 1;		//전체 페이지
		
		String categoryId = request.getParameter("categoryId");		//카테고리 아이디
		String[] categoryIdArr = (categoryId != null && !"".equals(categoryId))? categoryId.split(","):null;	//카테고리 아이디 array
		
		//data list
		List<DataMap> list = null;
		
		//카테고리 아이디 array 있을 때만 조회
		if(categoryIdArr != null && categoryIdArr.length > 0) {
			//파라미터 맵 셋팅
			Map paramMap = new HashMap<String, Object>();
			paramMap.put("categoryIdArr", categoryIdArr);	//카테고리 아이디 array
			
			//데이터 리스트 조회
			list = nEDMPRO0020Service.selectCategoryInList(paramMap);
		}
		
//		gridMap.put("page", currentPage);		//현재 페이지				-- 페이징 미적용
		gridMap.put("total", totalPage);		//전체 페이지 수
		gridMap.put("records", totalCount);		//전체 데이터 수
		gridMap.put("list", list);				//데이터 리스트
		
		return gridMap;
		

//		Map rtnMap = new HashMap<String, Object>();
//		Map paramMap = new HashMap<String, Object>();
//
//		try {
//			// 데이터 조회
//			String[] categoryIdArr = request.getParameter("categoryId").split(",");
//			paramMap.put("categoryIdArr", categoryIdArr);
//			List<DataMap> list = nEDMPRO0020Service.selectCategoryInList(paramMap);
//			rtnMap = JsonUtils.convertList2Json((List) list, list.size(), null);
//			rtnMap.put("result", true);
//		} catch (Exception e) {
//			logger.error("error message --> " + e.getMessage());
//			rtnMap.put("result", false);
//			rtnMap.put("errMsg", e.getMessage());
//		}
//
//		return rtnMap;
	}


	/**
	 * Desc : EC 전시 카테고리 매핑
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/edi/product/ecStandardCategoryMapping1.do")
	public @ResponseBody Map selectEcStandardCategoryMapping(HttpServletRequest request) {

		Map rtnMap = new HashMap<String, Object>();
		try {
			String martCatCd = StringUtils.defaultIfEmpty(request.getParameter("martCatCd"), "");

			DataMap ecDisplayCategoryMapping = nEDMPRO0020Service.selectEcStandardCategoryMapping(martCatCd);

			rtnMap.put("data", ecDisplayCategoryMapping);
			rtnMap.put("result", true);

		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
		    rtnMap.put("errMsg", e.getMessage());
		}
		return rtnMap;
	}

	// [ 와이드 이미지 저장 - PIY ]
	/**
	 * 온라인 이미지 저장
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/NEDMPRO0022SaveWideImage.do")
	public String onlineWideImageSave(HttpServletRequest request, ModelMap model) throws Exception {
		if (request == null || model == null) {
			throw new TopLevelException("");
		}

		String pgmId = SecureUtil.splittingFilter(StringUtils.trimToEmpty((String) request.getParameter("pgmId")));
		String mode = StringUtils.trimToEmpty((String) request.getParameter("mode"));
		String uploadDir = makeSubFolderForWide(pgmId);
		String newProdGenDivnCd = StringUtils.trimToEmpty((String) request.getParameter("newProdGenDivnCd")); //코리안넷에서 등록한 상품인지 EPC에서 등록한상품인지 구분
		String uploadWideFieldCount = StringUtils.trimToEmpty((String) request.getParameter("uploadWideFieldCount")); // 파일개수

		InputStream is = null;
		int limitSize = 1024*600;
		int maxWidthSize = 1312;
		int minWidthSize = 720;
		int maxHeightSize = 740;
		int minHeightSize = 405;
		String errGbn = null;

		/*
			int limitSize = 1024*600;
			int maxSize = 1500;
			int minSize = 500;
			int wMaxWidthSize = 1312; // WIDTH
			int wMaxHeightSize = 740;  // HEIGHT
		 */
		Integer newSeq = 0;
		MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;
		Iterator fileIter = mptRequest.getFileNames();


		try
		{

			while (fileIter.hasNext()) {
				MultipartFile mFile = mptRequest.getFile((String)fileIter.next());

				if(!mFile.isEmpty()) {

					is = mFile.getInputStream();
					BufferedImage image = ImageIO.read(is);
					long imgSize = mFile.getSize();
					Integer width = image.getWidth();
					Integer height = image.getHeight();
					String imageExtension = mFile.getContentType().toLowerCase();

					if (!imageExtension.equals("image/jpg") && !imageExtension.equals("image/jpeg")) {
						errGbn = "notPassExtension";
						throw new IllegalArgumentException("JPG 확장자 이미지만 업로드 가능합니다.");
					}
					// 와이드 이미지 파일 체크
					if (imgSize > limitSize) {
						errGbn = "sizeErr";
						throw new IllegalArgumentException("600kb이하의 이미지만 업로드 가능합니다.");
					}
					if(width > maxWidthSize || height > maxHeightSize || width < minWidthSize || height < minHeightSize){
						errGbn = "widthheightErr";
						throw new IllegalArgumentException("와이드형 사이즈는 720 X 405 이상 1312 X 740 이하입니다.");
					}


					String fileNm = pgmId+"_0"+newSeq;
					String newFileSource = uploadDir+"/"+fileNm;
					String fileFieldName = mFile.getName();
					if(!fileFieldName.startsWith("front")) {
						fileNm = SecureUtil.splittingFilter(fileFieldName);
						newFileSource = uploadDir+"/"+fileNm;
					}

					// 기존에 있던 파일 삭제
					File tmpFile = new File(newFileSource);
					if (tmpFile.isFile()) {
						FileUtil.delete(tmpFile);
					}

					FileOutputStream frontImageStream = new FileOutputStream(newFileSource);
					FileCopyUtils.copy(mFile.getInputStream(), frontImageStream);

					imageFileMngService.purgeImageQCServer("12",fileNm);
					imageFileMngService.purgeCDNServer("12", fileNm);

					newSeq++;

					if( frontImageStream != null ){
						frontImageStream.close();
						frontImageStream = null;
					}

				}
			}

			if (uploadWideFieldCount != null || !"".equals(uploadWideFieldCount) ) {
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("pgmId", pgmId);
				paramMap.put("onlineWideImageCnt",uploadWideFieldCount);
				// 온라인 와이드 이미지 등록됐다는 체크 추가
				nEDMPRO0020Service.updateOnlineWideImageCnt(paramMap);

				model.addAttribute("status","success");
				model.addAttribute("ment","1"); // 와이드 이미지가 저장됐습니다
			}
			else {
				model.addAttribute("status","fail");
			}
		}
		catch(Exception e)
		{
			model.addAttribute("status","fail");
			if("sizeErr".equals(errGbn))
			{
				model.addAttribute("ment","2"); // 500KB이하의 이미지만 업로드 가능합니다.
			}
			else if("widthheightErr".equals(errGbn))
			{
				model.addAttribute("ment","3"); // 와이드형 사이즈는 720 X 405 이하입니다.
			}
			else if("notPassExtension".equals(errGbn))
			{
				model.addAttribute("ment","6"); // 이미지 확장자 JPG만 업로드 가능합니다.
			}
			else {
				logger.error("onOffWideOnlineImage_exception : ",e);
			}
		}

		if ("KOR".equals(newProdGenDivnCd)) {
			return "redirect:/edi/product/NEDMPRO0102.do?pgmId=" + pgmId + "&mode=" + mode + "&bman_no=" + (String) request.getParameter("bman_no"); // 온오프전용[코리안넷]
		} else {
 			return "redirect:/edi/product/NEDMPRO0022.do?pgmId="+pgmId+"&mode="+mode;	//온오프전용
		}
	}


	/**
	 * 온라인 이미지 삭제
	 * @param pgmId
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/deleteProdWideOnlineImg.do")
	public String deleteProdWideOnlineImg(@RequestParam String pgmId, @RequestParam Integer fileLen, HttpServletRequest request, ModelMap model) throws Exception {

		if ("".equals(pgmId) || request == null || model == null) {
			throw new TopLevelException("");
		}

		String prodCd = SecureUtil.splittingFilter(pgmId.split("\\_")[0]);
		String rtnUrl = "redirect:/edi/product/NEDMPRO0022.do?pgmId=" + prodCd;

		// -----상품코드+seq문자열

		int selectedProdIdx = Integer.parseInt(pgmId.split("\\_")[1]);

		String fullPath = makeSubFolderForWide(prodCd);

		File dir = new File(fullPath);
		FileFilter fileFilter = new WildcardFileFilter(pgmId + "*");
		//File[] files = dir.listFiles(fileFilter);

		boolean isDel = false;

		for (int j = 0; j < 5; j++) {
			if (!new File(fullPath + "/" + prodCd + "_0" + j).exists()) {
				continue;
			}

			// 전체 목록중 삭제 선택된 파일의 시퀀스가 같으면 삭제
			if (j == selectedProdIdx) {
				isDel = FileUtils.deleteQuietly(new File(fullPath + "/" + pgmId));
				FileUtils.deleteQuietly(new File(fullPath + "/" + pgmId + "_720.jpg"));
			}

			// 순환 돌다가 삭제 선택된 파일의 시퀀스보다 순환문 내의 시퀀스가 크면,
			// 순환문내의 시퀀스 -1로 파일 생성하고 해당 순환문의 시퀀스 파일 삭제
			if (j > selectedProdIdx) {
				int newProductPrefix = j - 1;
				String newFileSource = fullPath + "/" + prodCd + "_0" + newProductPrefix;
				String currentFileSource = fullPath + "/" + prodCd + "_0" + j;

				FileCopyUtils.copy(new File(currentFileSource), new File(newFileSource));

				//-> 현재 파일 집어 넣는 방식으로 집어 넣는다.

				imageFileMngService.purgeImageQCServer("12", prodCd + "_" + newProductPrefix);
				imageFileMngService.purgeCDNServer("12", prodCd + "_" + newProductPrefix);

				FileUtils.deleteQuietly(new File(currentFileSource));
			}
		}

		if(isDel) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("pgmId", prodCd);
			paramMap.put("onlineWideImageCnt",fileLen-1);
			model.addAttribute("ment","4"); // 와이드 이미지가 삭제됐습니다.
			// 온라인 와이드 이미지 등록됐다는 체크 추가
			nEDMPRO0020Service.updateOnlineWideImageCnt(paramMap);
		}
		return rtnUrl;
	}

	
	/**
	 * 영양성분 탭 페이지 (edi)
	 * @param nEDMPRO0020VO
	 * @param model
	 * @param reuqest
	 * @return
	 * @throws Exception
	 */	
	@RequestMapping(value="/edi/product/NEDMPRO0027.do")
	public String selectNutOpt(NEDMPRO0020VO nEDMPRO0020VO, Model model, HttpServletRequest request) throws Exception {
		
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));			
		model.addAttribute("epcLoginVO", epcLoginVO);
		
		HashMap productInfo = new HashMap<String, String>();
		productInfo.put("pgmId", nEDMPRO0020VO.getPgmId());
		productInfo.put("entpCd", nEDMPRO0020VO.getEntpCd());
		model.addAttribute("productInfo",productInfo);
		
		List<HashMap> nutAttInfo = nEDMPRO0020Service.selectNutInfoByL3Cd(nEDMPRO0020VO.getL3Cd());
		model.addAttribute("nutAttInfo",nutAttInfo);
		
		List<HashMap> nutAttProd = nEDMPRO0020Service.selectNutInfoByPgmId (nEDMPRO0020VO.getPgmId());
		model.addAttribute("nutAttProd",nutAttProd);
		
		return "edi/product/NEDMPRO0027";
	}
	
	/**
	 * 영양성분 탭 페이지 (코리안넷)
	 * @param nEDMPRO0020VO
	 * @param model
	 * @param reuqest
	 * @return
	 * @throws Exception
	 */	
	@RequestMapping(value="/edi/product/NEDMPRO0104.do")
	public String selectNutOptKorNet(NEDMPRO0020VO nEDMPRO0020VO, Model model, HttpServletRequest request) throws Exception {
		

		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));			
		model.addAttribute("epcLoginVO", epcLoginVO);
		
		HashMap productInfo = new HashMap<String, String>();
		productInfo.put("pgmId", nEDMPRO0020VO.getPgmId());
		productInfo.put("entpCd", nEDMPRO0020VO.getEntpCd());
		model.addAttribute("productInfo",productInfo);
		
		List<HashMap> nutAttInfo = nEDMPRO0020Service.selectNutInfoByL3Cd(nEDMPRO0020VO.getL3Cd());
		model.addAttribute("nutAttInfo",nutAttInfo);
		
		List<HashMap> nutAttProd = nEDMPRO0020Service.selectNutInfoByPgmId (nEDMPRO0020VO.getPgmId());
		model.addAttribute("nutAttProd",nutAttProd);
		
		// 코리안넷 물류바코드탭 사용시 필요
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pgmId", StringUtils.trimToEmpty(nEDMPRO0020VO.getPgmId()));
		paramMap.put("cfmFg", StringUtils.trimToEmpty(""));
		NEDMPRO0020VO prodDetailVO = nEDMPRO0020Service.selectNewTmpProductDetailInfo(paramMap);
		model.addAttribute("prodDetailVO",prodDetailVO);
		
		return "edi/product/NEDMPRO0104";
	}
	
	/**
	 * 영양성분코드 관리
	 * @param nEDMPRO0020VO
	 * @param request
	 * @return
	 * @throws Exception
	 */	
	@RequestMapping(value = "/edi/product/updateNutAtt.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, String> updateNutAtt(@RequestBody NEDMPRO0020VO nEDMPRO0020VO, HttpServletRequest reuqest) throws Exception {
		
		HashMap<String, String> resultMap = new HashMap<String,String>();
		resultMap.put("updateStatus", "fail");
		
		try {
		    nEDMPRO0020Service.mergeNutAttWithPgmId(nEDMPRO0020VO);
		    resultMap.put("updateStatus", "success");
		} catch (Exception e) {
			logger.error("NutAttUdpdate Error : " + e.getMessage());
		}
		
		return resultMap;
	}

	/**
	 * 영양성분코드 관리
	 * @param nEDMPRO0020VO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/checkPbPartner.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, String> checkPbPartner(@RequestBody NEDMPRO0020VO nEDMPRO0020VO, HttpServletRequest reuqest) throws Exception {

		HashMap<String, String> resultMap = new HashMap<String,String>();
		resultMap.put("selectStatus", "fail");
		String isPbPartner = "notPbPartner";

		try {
			isPbPartner = nEDMPRO0020Service.checkPbPartner(nEDMPRO0020VO);
			resultMap.put("selectStatus", "success");
			resultMap.put("isPbPartner", isPbPartner);
		} catch (Exception e) {
			logger.error("NutAttUdpdate Error : " + e.getMessage());
		}

		return resultMap;
	}

	/**
	 * EC패션속성 페이지 전시 유무
	 * @param String
	 * @param List
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/displayEcFashionAttr.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> displayEcFashionAttr(@RequestBody NEDMPRO0020VO nEDMPRO0020VO, HttpServletRequest reuqest) throws Exception {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("selectStatus", "fail");

		try {
			List<HashMap> ecFashionAttrDisplay = nEDMPRO0020Service.displayEcFashionAttr(nEDMPRO0020VO.getStdCatCd());
			resultMap.put("selectStatus", "success");
			resultMap.put("ecFashionAttrDisplay", ecFashionAttrDisplay);
		} catch (Exception e) {
			logger.error("NutAttUdpdate Error : " + e.getMessage());
		}

		return resultMap;
	}

	/**
	 * Desc : OSP 전시 카테고리 팝업
	 * @param HttpServletRequest
	 * @param model
	 * @return view 페이지
	 */
	@RequestMapping("/edi/product/ospDisplayCategoryPop.do")
	public String ospDisplayCategoryPop(HttpServletRequest request, Model model) throws Exception {

//		DataMap paramMap = new DataMap(request);
//		boolean isMart = StringUtils.defaultIfEmpty(request.getParameter("isMart"), "N").equals("Y") ? true : false;
//		String[] mallCds = SecureUtil.stripXSS(StringUtils.defaultIfEmpty(request.getParameter("l3Cd"), "LTMT")).split(",");
//		paramMap.put("mallCds", mallCds);
		String l3Cd = request.getParameter("l3Cd");

		String ecCategoryFullName = request.getParameter("ecCategoryFullName").replace("_"," ▷ ");
//		String ospCatId = request.getParameter("ospCatId").replace("_","▶");
		String ospCatId = request.getParameter("ospCatId");

		List<HashMap> ospDisplayCategoryList = nEDMPRO0020Service.selectOspStandardCategoryMapping(l3Cd);

		model.addAttribute("ospDisplayCategoryList", ospDisplayCategoryList);
		model.addAttribute("ecCategoryFullName", ecCategoryFullName);
		model.addAttribute("ospCatId", ospCatId);
		return "edi/product/ospDisplayCategoryPop";
	}

	/**
	 * Desc : OSP 전시 카테고리 리스트 조회 For Grid
	 * @param HttpServletRequest
	 * @param model
	 * @return view 페이지
	 */
	@ResponseBody
	@RequestMapping("/edi/product/ospDisplayCategory.do")
	public HashMap<String,Object> ospDisplayCategory(HttpServletRequest request, Model model) throws Exception {
		HashMap<String,Object> gridMap = new HashMap<String, Object>();

		int totalCount = 0;		//전체 페이지 카운트
		int totalPage = 1;		//전체 페이지
		
		String pgmId = request.getParameter("pgmId");	//프로그램아이디
		
		//data list
		List<HashMap> list = nEDMPRO0020Service.selectOspCategorySaved(pgmId);
		
		//전체 data count
		totalCount = (list != null && !list.isEmpty())? list.size():0;

//		gridMap.put("page", currentPage);		//현재 페이지			-- paging 미사용
		gridMap.put("total", totalPage);		//전체 페이지 수
		gridMap.put("records", totalCount);		//전체 데이터 수
		gridMap.put("list", list);				//데이터 리스트

		return gridMap;
	}
}
