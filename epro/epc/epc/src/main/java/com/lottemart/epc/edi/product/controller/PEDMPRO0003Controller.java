package com.lottemart.epc.edi.product.controller;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lcnjf.util.StringUtil;
import com.lottemart.common.exception.AlertException;
import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.util.SecureUtil;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.comm.model.Constants;
import com.lottemart.epc.edi.comm.model.SearchParam;
import com.lottemart.epc.edi.comm.service.PEDPCOM0001Service;
import com.lottemart.epc.edi.product.model.ColorSize;
import com.lottemart.epc.edi.product.model.NewProduct;
import com.lottemart.epc.edi.product.model.PEDMPRO0011VO;
import com.lottemart.epc.edi.product.model.SearchProduct;
import com.lottemart.epc.edi.product.service.NEDMPRO0020Service;
import com.lottemart.epc.edi.product.service.PEDMPRO0003Service;
import com.lottemart.epc.edi.product.service.PEDMPRO000Service;

import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.ConfigurationService;


/**
 * @Class Name : PEDMPRO0003Controller
 * @Description : 임시보관함 Controller 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 12. 05. 오후 1:33:50 kks
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */


@Controller
public class PEDMPRO0003Controller extends BaseController{

	//edi 공통서비스
	private PEDMPRO000Service ediProductService;

	//임시보관함용 서비스
	private PEDMPRO0003Service pEDMPRO0003Service;

	//공통코드 조회 서비스
	private PEDPCOM0001Service commService;

	private static final Logger logger = LoggerFactory.getLogger(PEDMPRO0003Controller.class);

	@Autowired
	public void setEdiProductService(PEDMPRO000Service ediProductService) {
		this.ediProductService = ediProductService;
	}

	@Autowired
	public void setpEDMPRO0003Service(PEDMPRO0003Service pEDMPRO0003Service) {
		this.pEDMPRO0003Service = pEDMPRO0003Service;
	}

	@Autowired
	public void setCommService(PEDPCOM0001Service commService) {
		this.commService = commService;
	}

	@Resource(name = "configurationService")
	private ConfigurationService config;

	@Resource(name = "nEDMPRO0020Service")
	private NEDMPRO0020Service nEDMPRO0020Service;

	/**
	 * Desc : 임시보관함
	 * @Method Name : tempNewProductList
	 * @param SearchProduct
	 * @param HttpServletRequest
	 * @param Model
	 * @return view 페이지
	 */
	@RequestMapping(value="/edi/product/PEDMPRO0003")
	public String tempNewProductList(SearchProduct searchParam, HttpServletRequest request, Model model) {

		//검색 기간 설정
		String searchFlow = request.getParameter("searchFlow");
		if (StringUtils.isEmpty(searchParam.getStartDate()))
			searchParam.setStartDate(DateUtil.getToday("yyyy-MM-dd"));
		if (StringUtils.isEmpty(searchParam.getEndDate())) {
			String todayString = DateUtil.getToday("yyyy-MM-dd");
			searchParam.setEndDate(DateUtil.formatDate(DateUtil.addDay(todayString, 1), "-"));
		}

		model.addAttribute("paramMap", searchParam);

		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(ConfigUtils.getString("lottemart.epc.session.key"));
		//검색시 협력 업체코드를 선택하지 않은 경우, 로그인한 사업자의 전체 협력사에 대한 상품등록 정보가 조회됨.
		searchParam.setVenCds(epcLoginVO.getVendorId());
		model.addAttribute("epcLoginVO", epcLoginVO);

		//사용자가  임시보관함 페이지에서 '검색' 버튼을 클릭했을때만 검색기능 실행.
		if (StringUtils.isNotBlank(searchFlow)) {
			List<NewProduct> newProductListInTemp = pEDMPRO0003Service.selectNewProductListInTemp(searchParam);
			model.addAttribute("newProductListInTemp", newProductListInTemp);
		}

		return "edi/product/PEDMPRO0003";
	}

	/**
	 * Desc : 임시보관함 목록 페이지에서 선택한 상품 상세 페이지.
	 * @Method Name : tempNewProductInfo
	 * @param SearchParam
	 * @param HttpServletRequest
	 * @param Model
	 * @return view 페이지. 상품 특성별로 수정페이지가 다름.
	 *         온라인 전용, 소셜상품 : edi/product/PEDMPRO000330
	 *         온오프 겸용 상품          : edi/product/PEDMPRO000301
	 */
	@RequestMapping(value="/edi/product/PEDMPRO000301")
    public String tempNewProductInfo(SearchParam searchParam, HttpServletRequest request, Model model) throws Exception {
		// view페이지(온라인 전용, 소셜 상품);
		String viewPage = "edi/product/PEDMPRO000330";

		//사용자가 선택한 상품의 newProductCode 값으로 해당 상품 상세 정보 조회
		NewProduct tmpProduct = pEDMPRO0003Service.selectNewProductInfoInTempOld(searchParam.getNewProductCode());

		//온오프 겸용 상품일 경우 기존 수정페이지로 이동
		if( Constants.ONLINE_OFFLINE_PRODUCT_CD.equals(tmpProduct.getOnOffDivnCode()) ) {
			viewPage = "edi/product/PEDMPRO000301";
		}

		//편집기 특수문자 처리.
		String tmpProductDescription = StringEscapeUtils.escapeHtml(tmpProduct.getProductDescription());
		tmpProduct.setProductDescription(tmpProductDescription);

		//이미지 파일에 전달할 시간 파라미터. 브라우저 캐시 기능때문에 동일한 이름의 이미지 파일이
		//파일만 바뀌었을 경우, 브라우저에서 이전 이미지가 보여지는 것을 방지해줌.
		long milliSecond = System.currentTimeMillis();
		model.addAttribute("currentSecond", String.valueOf(milliSecond));

		String fashionProductFlag = "n";
		searchParam.setGroupCode(tmpProduct.getOfficialOrder().getTeamCode());

		//계절 목록 조회. 현재는 규격 상품의 계절목록 조회. 칼럼 사이즈 변경후 패션 계절 목록조회하도록
		//해당 서비스 클래스에 주석 제거
		model.addAttribute("seasonList", commService.selectSeasonTypeListOld(tmpProduct.getProductDivnCode()));
		if (isThisFashionProduct(tmpProduct.getProductDivnCode())) {
			fashionProductFlag = "y";
			//사용자가 입력한 색상/사이즈 값 조회.
			model.addAttribute("colorSizeListInTemp", pEDMPRO0003Service.selectProductColorListInTempOld(tmpProduct.getNewProductCode()));

			//pog이미지 표시에 사용될 고유한(중복 색상제거) 색상 목록 조회
			model.addAttribute("productColorList", ediProductService.selectProductColorListOld(tmpProduct.getNewProductCode()));
		}
		//전산법 추가
		//1.전상법큰분류
		//2.전상법 작은분류
		//3.전상법 가지고 있는거

	//	model.addAttribute("ecomAddInfoAddInTemp",pEDMPRO0003Service.selectProductEcomAddInfoListInTemp(tmpProduct.getNewProductCode())	   );

	//	selectProductColorListInTemp

		//온라인 대표 상품 코드가 존재한다면, 즉 온라인 전용 이나 소셜 상품이라면
		if (StringUtils.isNotEmpty(tmpProduct.getOnlineProductCode())) {
			String entpCode = tmpProduct.getEntpCode();

			//현재 상품정보에 등록된 해당 협력업체의 온라인 대표상품 정보. 이익률, 88코드 조회
			List<NewProduct> onlineRepresentProductList = ediProductService.selectOnlineRepresentProductListOld(entpCode);
			if (StringUtils.isNotEmpty(tmpProduct.getOnlineDisplayCategoryCode())) {
				SearchParam tmpSearch = new SearchParam();
				tmpSearch.setDetailCode(tmpProduct.getOnlineDisplayCategoryCode());
				model.addAttribute("l4GroupList", commService.selectL4ListForOnlineAjaxOld(tmpSearch));
			}
			//단품 정보 리스트
			model.addAttribute("itemListInTemp", pEDMPRO0003Service.selectProductItemListInTempOld(tmpProduct.getNewProductCode()));
			model.addAttribute("onlineRepresentProductList", onlineRepresentProductList);
		}

		//현재 상품상세 정보에 사용된 업체코드의 거래 유형 조회.
		SearchParam vendorTradeObj = ediProductService.selectCountVendorStopTradingOld(tmpProduct.getEntpCode());

		searchParam.setGroupCode(tmpProduct.getOfficialOrder().getTeamCode());
		searchParam.setDetailCode("all");
		model.addAttribute("tmpProduct", tmpProduct);
		//전체 팀분류 목록.
		model.addAttribute("teamList", commService.selectDistinctTeamListOld());
		//현재 상품의 팀분류의 해당 대분류 목록.
		model.addAttribute("l1GroupList", commService.selectL1ListOld(searchParam));

		searchParam.setDetailCode(tmpProduct.getOfficialOrder().getlGroupCode());

		//현재 상품의 팀분류의 대분류의 해당 세분류 목록--온오프겸용의 경우
		if (Constants.ONLINE_OFFLINE_PRODUCT_CD.equals(tmpProduct.getOnOffDivnCode())) {
			model.addAttribute("l4GroupList", commService.selectL4ListAjaxOld(searchParam));
		}

		//현재 상품의 거래 중지 정보
		model.addAttribute("vendorTradeObj", vendorTradeObj);

		//전체 색상값 목록
		model.addAttribute("colorList", commService.selectColorListOld());

		//전체 사이즈 분류 목록
		model.addAttribute("sizeCategoryList", commService.selectSizeCategoryListOld());

		//온라인 이미지 파일 목록 조회. 수정페이지에서 온라인 -> 오프라인 pog이미지 이런 순서로 업로드 함.
		String onlineUploadFolder = makeSubFolderForOnline(searchParam.getNewProductCode());
		ArrayList<String> onlineImageList = new ArrayList<String>();
		File dir = new File(onlineUploadFolder);
		FileFilter fileFilter = new WildcardFileFilter(searchParam.getNewProductCode() + "*");
		File[] files = dir.listFiles(fileFilter);
		for (int j = 0; j < files.length; j++) {
			String fileName = files[j].getName();
			if (fileName.lastIndexOf(".") < 0) {
				onlineImageList.add(fileName);
			}
		}

		//오프라인 pog이미지 파일 목록 조회. 규격, 패션 모두 포함 됨.
		String offlineUploadFolder = makeSubFolderForOffline(searchParam.getNewProductCode());
		ArrayList<String> offlineImageList = new ArrayList<String>();
		File offlineDir = new File(offlineUploadFolder);
		FileFilter offlineFileFilter = new WildcardFileFilter(tmpProduct.getProductImageId() + "*");
		File[] offlineFiles = offlineDir.listFiles(offlineFileFilter);
		for (int jj = 0; jj < offlineFiles.length; jj++) {
			offlineImageList.add(offlineFiles[jj].getName());
		}
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		model.addAttribute("onlineImageList", onlineImageList);
		model.addAttribute("offlineImageList", offlineImageList);
		model.addAttribute("imagePath", ConfigUtils.getString("system.cdn.static.path"));
		model.addAttribute("subFolderName", subFolderName(tmpProduct.getNewProductCode()));
		model.addAttribute("fashionProductFlag", fashionProductFlag);
		model.addAttribute("epcLoginVO", epcLoginVO);

		return viewPage;
	}

	/**
	 * Desc :[온라인전용/쇼셜상품수정] 임시보관함 상세에서 수정한 상품정보 수정
	 * @Method Name : modifyTempNewProductInfo
	 * @param NewProduct
	 * @param HttpServletRequest
	 * @param Model
	 * @return view 페이지
	 */
	@RequestMapping(value = "/edi/product/PEDMPRO000302", method = RequestMethod.POST)
	public String modifyTempNewProductInfo(NewProduct tmpProduct, HttpServletRequest request, Model model) throws Exception {

		//html에디터 값 매핑
		//String editorHtmlContent = saveFileAndEditorHtmlContent(tmpProduct.getNewProductCode(), request);
		//tmpProduct.setProductDescription(StringUtils.defaultIfEmpty(editorHtmlContent, "&nbsp;"));
		//tmpProduct.setProductDescription(editorHtmlContent);
		String pgmId= tmpProduct.getNewProductCode();
		if (StringUtils.isBlank(pgmId) || pgmId == null) {
			throw new AlertException("신규상품코드가 없습니다.");
		}
		int mdSendCheckCnt = pEDMPRO0003Service.selectMdSendDivnCdCheck(pgmId);
		if(mdSendCheckCnt == 0) {

			tmpProduct.makeItemObject(); // 단품정보 리스트 생성
			tmpProduct.makeKeywordObject(); // 상품키워드

			tmpProduct.makeEcAttrbuteObject(); // 20200205 ec 상품속성 추가
			if("".equals(tmpProduct.getStdCatCd()) || tmpProduct.getStdCatCd() == null){
				throw new AlertException("EC표준카테고리를 입력해 주세요.");
			}

			EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
			String vendorId = epcLoginVO.getVendorId()[0];
			List<PEDMPRO0011VO> keywordList = tmpProduct.getKeywordList();
			for (int i = 0; i < keywordList.size(); i++) {
				PEDMPRO0011VO vo = keywordList.get(i);
				if (vo.getRegId() == null || "".equals(vo.getRegId())) {
					vo.setRegId(vendorId);
				}
				if (StringUtil.isEmpty(vo.getSearchKywrd())) {
					throw new AlertException("검색어를 입력해주세요.");
				}
				if (StringUtil.getByteLength(vo.getSearchKywrd()) > 40) {
					throw new AlertException("검색어는 최대 39바이트까지 입력가능합니다.");
				}
			}

			String[] dispCatArr = tmpProduct.getDispCatCd().split(",");
			ArrayList<String> dispCatArrList = new ArrayList<>();
			for (int i = 0; i < dispCatArr.length; i++) {
				if (!"".equals(dispCatArr[i]) && dispCatArr[i] != null) {
					dispCatArrList.add(dispCatArr[i]);
				}
			}
			if (dispCatArrList.size() < 2) {
				throw new AlertException("EC 롯데ON 전시카테고리 또는 EC 롯데마트몰 전시카테고리가 존재하지 않습니다.");
			}

			if (!StringUtils.isEmpty(tmpProduct.getDispCatCd())) {
				int idx = tmpProduct.getDispCatCd().indexOf(",");

				if (idx > -1) {
					try {
						tmpProduct.setDispCatCd(catOverLapChk(dispCatArr));
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
				}
			}

			pEDMPRO0003Service.updateNewProductInfoInTemp(tmpProduct); // 상품 기본, 추가 정보 수정.

			// 전자상거래 입력 데이터
			String[] tempArrayCd = tmpProduct.getProdAddCd().split("#//#");
			String[] tempArrayNm = tmpProduct.getProdAddNm().split("#//#");

			ediProductService.deleteProdAddDetail(tmpProduct); // INSERT 하기전 모두 삭제

			if (tmpProduct.getEntpCode() == null || "".equals(tmpProduct.getEntpCode())) {
				tmpProduct.setEntpCode(vendorId);
			}

			for (int i = 0; i < tempArrayCd.length; i++) {
				if (tempArrayCd[i] != null && tempArrayCd[i].length() > 0) {
					if (tempArrayNm[i] != null && !"".equals(tempArrayNm[i].trim())) {

						if (StringUtil.getByteLength(tempArrayNm[i]) > 2000) { // SQL Exception 방지
							throw new AlertException("전상법 정보는 2000Byte 이하로 입력해주세요.");
						}

						tmpProduct.setProdAddMasterCd(tmpProduct.getProdAddMasterCd());
						tmpProduct.setProdAddCd(tempArrayCd[i].trim());
						tmpProduct.setProdAddNm(tempArrayNm[i].trim());
						tmpProduct.setEntpCode(tmpProduct.getEntpCode());
						ediProductService.insertProdAddDetail(tmpProduct);
					}
				}
			}

			// KC 인증마크 입력 데이터
			String[] tempArrayCertCd = tmpProduct.getProdCertCd().split("#//#");
			String[] tempArrayCertNm = tmpProduct.getProdCertNm().split("#//#");

			ediProductService.deleteProdCertDetail(tmpProduct); // INSERT 하기전 모두 삭제

			for (int i = 0; i < tempArrayCertCd.length; i++) {
				if (tempArrayCertCd[i] != null && tempArrayCertCd[i].length() > 0) {

					if (tempArrayCertNm[i] != null && !"".equals(tempArrayCertNm[i].trim())) {

						if (StringUtil.getByteLength(tempArrayCertNm[i]) > 2000) { // SQL Exception 방지
							throw new AlertException("KC 인증마크 정보는 2000Byte 이하로 입력해주세요.");
						}

						tmpProduct.setProdCertMasterCd(tmpProduct.getProdCertMasterCd());
						tmpProduct.setProdCertCd(tempArrayCertCd[i].trim());
						tmpProduct.setProdCertNm(tempArrayCertNm[i].trim());
						tmpProduct.setEntpCode(tmpProduct.getEntpCode());
						ediProductService.insertProdCertDetail(tmpProduct);
					}
				}
			}
		} else {
			throw new IllegalArgumentException();
		}
		return "redirect:/edi/product/NEDMPRO0030OnlineDetail.do?pgmId=" + tmpProduct.getNewProductCode() + "&mode=modify";
	}

	/**
	 * Desc :[온라인전용] 단품 정보 삭제
	 * @Method Name : deleteTempProductItemInfo
	 * @param NewProduct
	 * @param HttpServletRequest
	 * @param Model
	 * @return view 페이지
	 */
	@RequestMapping(value="/edi/product/delOnlineItemInfo.json", method=RequestMethod.POST,	headers="Accept=application/json")
	public @ResponseBody Map<String, Object>delOnlineItemInfo(@RequestBody Map<String, Object> paramMap, HttpServletRequest reuqest) throws Exception {
		return ediProductService.deleteTempProductItem(paramMap);
	}

	//20180904 - 상품키워드 입력 기능 추가
	/**
	 * [온라인전용] 상품키워드 삭제
	 * @Method delOnlineKeywordInfo
	 * @param NewProduct
	 * @param HttpServletRequest
	 * @param Model
	 * @return view 페이지
	 */
	@RequestMapping(value="/edi/product/delOnlineKeywordInfo.json", method=RequestMethod.POST,	headers="Accept=application/json")
	public @ResponseBody Map<String, Object>delOnlineKeywordInfo(@RequestBody Map<String, Object> paramMap, HttpServletRequest reuqest) throws Exception {
		return ediProductService.deleteTpcPrdKeyword(paramMap);
	}

	/**
	 * Desc : 상품 확정. 온오프는 MD로 데이터(파일 포함)전송, 그외 상품은 TED_NEW_PROD_REG테이블에
	 *         MD_SND_FG칼럼값만 'S'로 갱신함.
	 * @Method Name : modifyTempNewProductInfo
	 * @param NewProduct
	 * @param HttpServletRequest
	 * @param Model
	 * @return view 페이지
	 */
	@RequestMapping(value = "/edi/product/PEDMPRO000303")
	public String fixTempNewProductInfo(NewProduct tmpProduct, HttpServletRequest request, Model model) throws Exception {

		//목록 페이지에서 사용자가 선택한 상품 코드 값 배열
		String[] checkTempProductCodeArray = request.getParameterValues("selectedProduct");
		int productCodeArrayLength = checkTempProductCodeArray.length;
		FTPClient ftp = new FTPClient();
		try {
			//MD서버에 ftp연결

			ftp.connect(ConfigUtils.getString("edi.md.ftp.url"));
			ftp.login(ConfigUtils.getString("edi.md.ftp.userid"), ConfigUtils.getString("edi.md.ftp.passwd"));
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			//사용자가 선택한 상품에 대한 반복문 실행.
			File dir = null;
			FileFilter fileFilter = null;
			String currentFileName = "";
			InputStream targetFile = null;
			int paramLength = 0;

			String productCode = "";
			String productType = "";
			String productImageId = "";

			String fullPath = "";
			ArrayList<String> fileList = null;
			int imgId;
			for (int i = 0; i < productCodeArrayLength; i++) {

				fileList = new ArrayList<String>();

				paramLength = checkTempProductCodeArray[i].split("\\_").length;

				productCode = checkTempProductCodeArray[i].split("\\_")[0];
				productType = checkTempProductCodeArray[i].split("\\_")[1];

				if (paramLength > 2)
					productImageId = checkTempProductCodeArray[i].split("\\_")[2] == null ? "" : checkTempProductCodeArray[i].split("\\_")[2];

				//온오프 겸용 상품일 이고 이미지아이디가 있는 경우,
				if (productType.equals(Constants.ONLINE_OFFLINE_PRODUCT_CD)) {
					if (productImageId.length() > 0) {
						imgId = productImageId.length();
						for (int k = 0; k < imgId; k++) {
							if (Character.isWhitespace(productImageId.charAt(k))) {
								//해당 상품의 오프라인 pog 이미지 md시스템으로 전송
								fullPath = makeSubFolderForOffline(productCode);

								dir = new File(fullPath);
								fileFilter = new WildcardFileFilter(productImageId + "*.jpg");
								File[] files = dir.listFiles(fileFilter);

								for (int j = 0; j < files.length; j++) {
									currentFileName = files[j].getName();
									targetFile = FileUtils.openInputStream(files[j]);
									ftp.storeFile(currentFileName, targetFile);

									fileList.add(currentFileName);
								}
							}
						}
					}
				}
				//해당 상품 정보 확정처리. 온오프라인은 MD시스템으로 상품정보 insert하고,
				//기타 온라인 전용, 소셜 상품은 자체 테이블에 MD전송구분 칼럼값만 'S'로 수정한다.
				pEDMPRO0003Service.fixNewProductInTemp(productCode, productType, fileList);
			}
		} catch (Exception e) {
			logger.debug("error message : " + e.getMessage());
		} finally {
			ftp.disconnect();
		}

		//fix파라미터로 메세지 처리
		return "redirect:/edi/product/PEDMPRO0003.do?fix=y";
	}

	/**
	 * Desc : 상품 삭제. 상품의 이미지 파일 도 삭제
	 * @Method Name : modifyTempNewProductInfo
	 * @param NewProduct
	 * @param HttpServletRequest
	 * @param Model
	 * @return view 페이지
	 */
	@RequestMapping(value = "/edi/product/PEDMPRO000304")
	public String deleteTempNewProductInfo(NewProduct tmpProduct, HttpServletRequest request, Model model) throws Exception {
		return "redirect:/edi/product/PEDMPRO0003.do";
	}

	/**
	 * Desc : 상품정보 복사(온라인 전용에서만 사용)
	 * 		  기존 상품정보에서 일부 필드 값만 변경해서 새로운 상품으로 등록함.
	 *       유사 상품 등록에 사용
	 * @Method Name : makeCopycatProduct
 	 * @param NewProduct
 	 * @param HttpServletRequest
 	 * @param Model
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/edi/product/PEDMPRO0333")
	public String makeCopycatProduct(NewProduct newProduct, HttpServletRequest request, Model model) throws Exception {

		if (newProduct == null || request == null || model == null) {
			throw new IllegalArgumentException();
		}

		//-----logger.debug("복사할 신상품 필드값 :"+newProduct);
		String legacyProductCode = SecureUtil.splittingFilter(StringUtils.trimToEmpty(request.getParameter("newProductCode")));

		//파라미터 값 매핑.
		String noPublishedBuyPossibleDivnCode = StringUtils.trimToEmpty(request.getParameter("noPublishedBuyPossibleDivnCode"));
		String newProductFirstPublishedDivnCode = StringUtils.trimToEmpty(request.getParameter("newProductFirstPublishedDivnCode"));
		String totalInspectYn = StringUtils.trimToEmpty(request.getParameter("totalInspectYn"));
		String mixYn = StringUtils.trimToEmpty(request.getParameter("mixYn"));
		String newProdPromoFg = StringUtils.trimToEmpty(request.getParameter("newProdPromoFg"));
		String overPromoFg = StringUtils.trimToEmpty(request.getParameter("overPromoFg"));
		String ecoYn = StringUtils.trimToEmpty(request.getParameter("ecoYn"));
		String dlvgaYn = StringUtils.trimToEmpty(request.getParameter("dlvgaYn"));
		String inscoYn = StringUtils.trimToEmpty(request.getParameter("inscoYn"));

		//-----VO에 값 설정
		if (!"".equals(noPublishedBuyPossibleDivnCode)) {
			newProduct.getOfficialOrder().setNoPublishedBuyPossibleDivnCode(noPublishedBuyPossibleDivnCode);
		}

		if (!"".equals(newProductFirstPublishedDivnCode)) {
			newProduct.getOfficialOrder().setNewProductFirstPublishedDivnCode(newProductFirstPublishedDivnCode);
		}

		if (!"".equals(totalInspectYn)) {
			newProduct.getOfficialOrder().setTotalInspectYn(totalInspectYn);
		}

		if (!"".equals(mixYn)) {
			newProduct.getOfficialOrder().setMixYn(mixYn);
		}

		if (!"".equals(newProdPromoFg)) {
			newProduct.getOfficialOrder().setNewProdPromoFg(newProdPromoFg);
		}

		if (!"".equals(overPromoFg)) {
			newProduct.getOfficialOrder().setOverPromoFg(overPromoFg);
		}

		if (!"".equals(ecoYn)) {
			newProduct.getOfficialOrder().setecoYn(ecoYn);
		}

		if (!"".equals(dlvgaYn)) {
			newProduct.getOfficialOrder().setdlvgaYn(dlvgaYn);
		}

		if (!"".equals(inscoYn)) {
			newProduct.getOfficialOrder().setinscoYn(inscoYn);
		}

		//-----상품 저장시 사용되는 new_prod_cd값 시퀀스로 생성
		newProduct.setNewProductCode(nEDMPRO0020Service.selectNewProductPgmId(""));
		String newProductCode = SecureUtil.splittingFilter(newProduct.getNewProductCode());

		//-----pog이미지id값 설정. new_prod_cd값 이용.
		String newProductImageId = StringUtils.substring(newProduct.getNewProductCode(), 2, 8) + StringUtils.right(newProduct.getNewProductCode(), 5);
		newProduct.setProductImageId(newProductImageId);

		newProduct.getOfficialOrder().setNewProductGeneratedDivnCode("EDI");

		//-----기존 상품의 온라인 이미지  복사
		String onlineUploadFolder = makeSubFolderForOnline(legacyProductCode);
		String onlineUploadFolderForNewCopyProduct = makeSubFolderForOnline(newProductCode);

		File dir = new File(onlineUploadFolder);
		FileFilter fileFilter = new WildcardFileFilter(legacyProductCode+"*");
		File[] files = dir.listFiles(fileFilter);

		for (int j = 0; j < files.length; j++) {
			String currnetOnlineImageFileName = files[j].getName();
			String newProductOnlineImageFileName = onlineUploadFolderForNewCopyProduct + "/" + StringUtils.replace(currnetOnlineImageFileName, legacyProductCode, newProductCode);

			FileOutputStream copiedProductOnlineImageStream = new FileOutputStream(newProductOnlineImageFileName);
			FileUtils.copyFile(files[j], copiedProductOnlineImageStream);
		}

		//----- 단품정보 구성
		newProduct.makeItemObject();

		//----- 실제 상품 정보 입력.
		ediProductService.insertProductInfo(newProduct);

		//----전자상거래 입력 데이터
		String[] tempArrayCd = newProduct.getProdAddCd().split("#//#");
		String[] tempArrayNm = newProduct.getProdAddNm().split("#//#");

		//INSERT 하기전 모두 삭제
		ediProductService.deleteProdAddDetail(newProduct);

		for (int i = 0; i < tempArrayCd.length; i++) {
			if (tempArrayCd[i] != null && tempArrayCd[i].length() > 0) {
				if (tempArrayNm[i] != null && !"".equals(tempArrayNm[i].trim())) {
					newProduct.setProdAddMasterCd(newProduct.getProdAddMasterCd());
					newProduct.setProdAddCd(tempArrayCd[i].trim());
					newProduct.setProdAddNm(tempArrayNm[i].trim());
					newProduct.setEntpCode(newProduct.getEntpCode());
					ediProductService.insertProdAddDetail(newProduct);
				}
			}

			model.addAttribute("newProduct", newProduct);
		}

		//KC 인증마크 입력 데이터
		String[] tempArrayCertCd = newProduct.getProdCertCd().split("#//#");
		String[] tempArrayCertNm = newProduct.getProdCertNm().split("#//#");

		//INSERT 하기전 모두 삭제
		ediProductService.deleteProdCertDetail(newProduct);

		for (int i = 0; i < tempArrayCertCd.length; i++) {
			if (tempArrayCertCd[i] != null && tempArrayCertCd[i].length() > 0) {
				if (tempArrayCertNm[i] != null && !"".equals(tempArrayCertNm[i].trim())) {
					newProduct.setProdCertMasterCd(newProduct.getProdCertMasterCd());
					newProduct.setProdCertCd(tempArrayCertCd[i].trim());
					newProduct.setProdCertNm(tempArrayCertNm[i].trim());
					newProduct.setEntpCode(newProduct.getEntpCode());

					ediProductService.insertProdCertDetail(newProduct);
				}
			}
		}

		//상품 정보 저장 후, 수정페이지에서 온라인 필수 이미지 업로드
		return "redirect:/edi/product/NEDMPRO0030OnlineDetail.do?pgmId=" + newProduct.getNewProductCode() + "&message=productCopy";
	}

	/**
	 * Desc : 컬러사이즈 상세조회(패션상품)
	 * @Method Name : tempNewProductList
	 * @param map
	 * @param HttpServletRequest
	 * @param Model
	 * @return view 페이지
	 */
	@RequestMapping(value = "/edi/order/PEDPPRO000302", method = RequestMethod.GET)
	public String sellCodeView(@RequestParam Map<String, Object> map, ModelMap model, HttpServletRequest request) {
		List<ColorSize> colorSize = pEDMPRO0003Service.sellCodeView(map);
		model.addAttribute("colorSizeList", colorSize);
		return "edi/product/PEDPPRO000302";
	}

	@RequestMapping(value = "/edi/product/PEDMPRO000303PogImageCheck")
	public String pogImageCheck(NewProduct tmpProduct, HttpServletRequest request, Model model) throws Exception {
		return "/edi/product/PEDMPRO0003";
	}

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
