package com.lottemart.epc.product.controller;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.lang.RuntimeException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.ConfigurationService;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lottemart.common.exception.AlertException;
import com.lottemart.common.file.service.ImageFileMngService;
import com.lottemart.common.util.ConfigUtils;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.service.CommonService;
import com.lottemart.epc.common.util.LoginUtil;
import com.lottemart.epc.common.util.SecureUtil;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.comm.model.SearchParam;
import com.lottemart.epc.edi.product.model.NEDMPRO0020VO;
import com.lottemart.epc.edi.product.model.NewProduct;
import com.lottemart.epc.edi.product.service.CommonProductService;
import com.lottemart.epc.edi.product.service.NEDMPRO0020Service;
import com.lottemart.epc.edi.product.service.PEDMPRO0003Service;
import com.lottemart.epc.edi.product.service.PEDMPRO000Service;
import com.lottemart.epc.product.service.PSCMPRD0010Service;

/**
 * @Class Name : PSCMPRD0010Controller
 * @Description : 추가구성품관리 Controller 클래스
 * @Modification Information
 *
 * << 개정이력(Modification Information) >>
 *
 *   수정일       수정자           수정내용
 *  -------         --------    ---------------------------
 * 2016.04.27   projectBOS32	신규생성
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Controller
public class PSCMPRD0010Controller extends BaseController{

	private static final Logger logger = LoggerFactory
			.getLogger(PSCMPRD0010Controller.class);

	// 신상품 등록 관련 서비스
	@Resource(name="ediProductService")
	private PEDMPRO000Service ediProductService;

	//임시보관함용 서비스
	@Resource(name="pEDMPRO0003Service")
	private PEDMPRO0003Service pEDMPRO0003Service;

	@Autowired
	private ConfigurationService config;

	@Resource(name="commonProductService")
	private CommonProductService commonProductService;

	@Autowired
	private PSCMPRD0010Service pscmprd0010Service;

	@Resource(name="nEDMPRO0020Service")
	private NEDMPRO0020Service nEDMPRO0020Service;

	@Resource(name="imageFileMngService")
	private ImageFileMngService imageFileMngService;

	@Autowired
	private CommonService commonService;

	/**
	 * Desc : 추가구성품 조회 폼
	 *
	 * @Method Name : selectComponentView
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/product/selectComponentView.do")
	public String selectComponentView(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);

		String endDate = DateUtil.getToday("yyyy-MM-dd");
		String startDate = DateUtil.formatDate(DateUtil.addDay(endDate, -7), "-");

		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);

		return "product/PSCMPRD0010";
	}

	/**
	 * Desc : 추가구성품 목록을 조회하는 메소드
	 * @Method Name : selectSuggestionSearch
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/product/selectComponentList.do")
	public @ResponseBody Map selectComponentListSearch(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		Map rtnMap = new HashMap<String, Object>();

		try {

			DataMap paramMap = new DataMap(request);

			String currentPage = paramMap.getString("currentPage");
			paramMap.put("startDate", paramMap.getString("startDate").replaceAll("-", ""));
			paramMap.put("endDate", paramMap.getString("endDate").replaceAll("-", ""));

			// 선택한 vendorId가null 이거나 repVendorId ( 대표협력업체 ) 일때 협력사 전체 검색
			if(paramMap.getString("vendorId").length() == 0 || epcLoginVO.getRepVendorId().equals(paramMap.get("vendorId"))) {
				paramMap.put("vendorId", LoginUtil.getVendorList(epcLoginVO));
			}else{
				String venderId[] = {paramMap.getString("vendorId")};
				paramMap.put("vendorId", venderId);
			}

			// 선택한 vendor가 openappi 업체일 경우 전체 검색
			List<EpcLoginVO> openappiVendorId = commonService.selectOpenappiVendor();
			for(int l=0; openappiVendorId.size()>l; l++ ){
				if(openappiVendorId.get(l).getRepVendorId().equals(request.getParameter("vendorId"))){
					paramMap.put("vendorId", LoginUtil.getVendorList(epcLoginVO));
				}
			}

			// 데이터 조회
			List<DataMap> list = pscmprd0010Service.selectComponentList(paramMap);

			rtnMap = JsonUtils.convertList2Json((List)list, -1, currentPage);

			rtnMap.put("result", true);
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
		    rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}

	/**
	 * Desc : 추가구성품  등록페이지.
	 * @Method Name : insertComponentPopupForm
	 * @param SearchParam
	 * @param model
	 * @return view 페이지
	 */
	@RequestMapping(value="/product/insertComponentPopupForm")
    public String insertComponentPopupForm(SearchParam searchParam, Model model, HttpServletRequest request) throws Exception {

		Map<String, Object>	paramMap	=	new HashMap<String, Object>();

		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		model.addAttribute("epcLoginVO", epcLoginVO);
		model.addAttribute("teamList",			commonProductService.selectNteamList(paramMap, request));		//팀리스트
		model.addAttribute("nowYear",			DateUtil.getToday("yyyy"));										//현재년도

		return "product/PSCMPRD001001";
	}

	/**
	 * Desc : 추가구성품  상세보기 페이지.
	 * @param
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/product/ComponentDetail.do")
	public String selectNewOnlineProdDetail(@RequestParam String pgmId, HttpServletRequest request, ModelMap model) throws Exception {
		if (request == null) {
			throw new IllegalArgumentException();
		}

		Map<String, Object>	paramMap	=	new HashMap<String, Object>();
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		//-----상품상세정보
		paramMap.put("pgmId", StringUtils.trimToEmpty(pgmId));
		paramMap.put("cfmFg", "");
		NEDMPRO0020VO	newProdDetailInfo	=	nEDMPRO0020Service.selectNewTmpOnlineProductDetailInfo(paramMap);

		//이미지 파일에 전달할 시간 파라미터. 브라우저 캐시 기능때문에 동일한 이름의 이미지 파일이
		//파일만 바뀌었을 경우, 브라우저에서 이전 이미지가 보여지는 것을 방지해줌.
		long milliSecond = System.currentTimeMillis();

		//-----현재 상품정보에 등록된 해당 협력업체의 온라인 대표상품 정보. 이익률, 88코드 조회
		List<NewProduct> onlineRepresentProductList = ediProductService.selectOnlineRepresentProductList(StringUtils.trimToEmpty(newProdDetailInfo.getEntpCd()));

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

		//-----상품 상세정보
		model.addAttribute("onlineRepresentProductList", 	onlineRepresentProductList);
		model.addAttribute("newProdDetailInfo", 	  		newProdDetailInfo);
		model.addAttribute("onlineImageList",  				onlineImageList);
		model.addAttribute("imagePath" ,						ConfigUtils.getString("edi.online.image.url"));
		model.addAttribute("subFolderName", 				subFolderName(StringUtils.trimToEmpty(newProdDetailInfo.getPgmId())));
		model.addAttribute("epcLoginVO", 					epcLoginVO);
		model.addAttribute("currentSecond", 				Long.toString(milliSecond));
		model.addAttribute("nowYear",			DateUtil.getToday("yyyy"));										//현재년도
		model.addAttribute("teamList",			commonProductService.selectNteamList(paramMap, request));		//팀리스트

		return "product/PSCMPRD001001";
	}

	/**
	 * Desc : 추가구성품 등록
	 * 		     온라인 pog이미지 업로드
	 * @Method Name : saveComponent
 	 * @param NewProduct
 	 * @param HttpServletRequest
 	 * @param Model
	 * @return ModelAndView
	 */
	@RequestMapping(value="/product/saveComponent", method=RequestMethod.POST)
	public String saveComponent(NewProduct newProduct, HttpServletRequest request, Model model ) throws Exception {

		String newProductCd = "";

		//TO-BE 미사용 메뉴로 판단되지만... 일단 수정
		//250624 Key생성방식 변경 _(구EPC) BOS 온라인상품쪽과 PGM_ID 겹치지 않기 위해, BOS에서 채번된 값을 받아서 사용 
		String newPgmId = ediProductService.selectNewProductCode();		//신상품코드 생성
		//신상품코드 생성 실패 시, exception
		if("".equals(newPgmId)) {
			throw new AlertException("신상품 가등록 코드 생성에 실패하였습니다.");
		}
		// 상품 저장시 사용되는 new_prod_cd값 시퀀스로 생성
		newProduct.setNewProductCode(newPgmId);

		// pog이미지id값 설정. new_prod_cd값 이용.
		String newProductImageId = StringUtils.substring(newProduct.getNewProductCode(), 2, 8)
			+ StringUtils.right(newProduct.getNewProductCode(), 5);
		newProduct.setProductImageId(newProductImageId);

		newProductCd = newProduct.getNewProductCode();

		// 실제 상품 정보 입력.
		ediProductService.insertProductInfo(newProduct);

		String pgmId				= SecureUtil.splittingFilter(StringUtils.trimToEmpty(newProductCd));
		String uploadDir		= makeSubFolderForOnline(pgmId);
		String uploadFieldCount = StringUtils.trimToEmpty((String)request.getParameter("uploadFieldCount"));		//파일개수


		if (uploadFieldCount != null || !"".equals(uploadFieldCount)) {
			Map<String, Object>	paramMap	=	new HashMap<String, Object>();

			paramMap.put("pgmId",		pgmId);
			paramMap.put("fileCount", 	uploadFieldCount);

			nEDMPRO0020Service.updateNewProdImgCnt(paramMap);
		}



		//온라인 이미지 파일 업로드.
		//업로드 할때 상품코드와 자연증가 seq변수 값으로 해당 이미지 파일명 설정.
		Integer newSeq 							= 0;
		MultipartHttpServletRequest mptRequest 	= (MultipartHttpServletRequest)request;
		Iterator fileIter 						= mptRequest.getFileNames();

		while (fileIter.hasNext()) {
			MultipartFile mFile = mptRequest.getFile((String)fileIter.next());

			 if(!mFile.isEmpty()) {
				 String fileNm = pgmId+"_"+newSeq;
				 String newFileSource = uploadDir+"/"+fileNm;
				 String fileFieldName = mFile.getName();
				 if(!fileFieldName.startsWith("front")) {
					 fileNm = SecureUtil.splittingFilter(fileFieldName);
					 newFileSource = uploadDir+"/"+SecureUtil.splittingFilterAll(fileNm);
				 }

				 FileOutputStream frontImageStream = new FileOutputStream(newFileSource);
				 FileCopyUtils.copy(mFile.getInputStream(), frontImageStream);
//				 ImageUtilsThumbnail.resizeAutoForEPC(newFileSource); //구버전 이미지 리사이징 메소드

				 imageFileMngService.purgeImageQCServer("01",fileNm);
				 imageFileMngService.purgeCDNServer("01", fileNm);

				 newSeq++;
			 }
		}

		model.addAttribute("newProduct", newProduct);

		return "redirect:/product/ComponentDetail.do?pgmId=" + newProductCd + "&mode=save";
	}

	/**
	 * Desc : 추가구성품 수정
	 * 		     온라인 pog이미지 업로드
	 * @Method Name : saveComponent
 	 * @param NewProduct
 	 * @param HttpServletRequest
 	 * @param Model
	 * @return ModelAndView
	 */
	@RequestMapping(value="/product/modifyComponent", method=RequestMethod.POST)
	public String updateComponent(NewProduct newProduct, HttpServletRequest request, Model model ) throws Exception {
		String pgmId				= SecureUtil.splittingFilter(newProduct.getNewProductCode());
		String uploadDir		= makeSubFolderForOnline(pgmId);

		newProduct.makeItemObject();

		//상품 기본, 추가 정보 수정.
		pEDMPRO0003Service.updateNewProductInfoInTemp(newProduct);

		//온라인 이미지 파일 업로드.
		//업로드 할때 상품코드와 자연증가 seq변수 값으로 해당 이미지 파일명 설정.
		Integer newSeq 							= 0;
		MultipartHttpServletRequest mptRequest 	= (MultipartHttpServletRequest)request;
		Iterator fileIter 						= mptRequest.getFileNames();

		while (fileIter.hasNext()) {
			MultipartFile mFile = mptRequest.getFile((String)fileIter.next());

			 if(!mFile.isEmpty()) {
				 String fileNm = pgmId+"_"+newSeq;
				 String newFileSource = uploadDir+"/"+fileNm;
				 String fileFieldName = mFile.getName();
				 if(!fileFieldName.startsWith("front")) {
					 fileNm = SecureUtil.splittingFilter(fileFieldName);
					 newFileSource = uploadDir+"/"+fileNm;
				 }

				 FileOutputStream frontImageStream = new FileOutputStream(newFileSource);
				 FileCopyUtils.copy(mFile.getInputStream(), frontImageStream);
//				 ImageUtilsThumbnail.resizeAutoForEPC(newFileSource); //구버전 이미지 리사이징 메소드

				 imageFileMngService.purgeImageQCServer("01",fileNm);
				 imageFileMngService.purgeCDNServer("01", fileNm);

				 newSeq++;
			 }
		}

		model.addAttribute("newProduct", newProduct);

		return "redirect:/product/ComponentDetail.do?pgmId=" + pgmId + "&mode=update";
	}
}
