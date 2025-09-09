package com.lottemart.epc.edi.product.controller;

import java.awt.Image;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.file.FileUtil;
import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.ConfigurationService;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

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

import com.lcnjf.util.StringUtil;
import com.lottemart.common.exception.AlertException;
import com.lottemart.common.exception.TopLevelException;
import com.lottemart.common.file.service.ImageFileMngService;
import com.lottemart.common.util.ConfigUtils;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.util.SecureUtil;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.product.model.NEDMPRO0020VO;
import com.lottemart.epc.edi.product.model.NewProduct;
import com.lottemart.epc.edi.product.service.CommonProductService;
import com.lottemart.epc.edi.product.service.NEDMPRO0020Service;
import com.lottemart.epc.edi.product.service.NEDMPRO0050Service;
import com.lottemart.epc.edi.product.service.PEDMPRO0003Service;
import com.lottemart.epc.edi.product.service.PEDMPRO000Service;
//import com.lottemart.epc.product.service.PSCMPRD0010Service;

/**
 * @Class Name : NEDMPRO0050Controller
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
public class NEDMPRO0050Controller extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0050Controller.class);

	// 신상품 등록 관련 서비스
	@Resource(name = "ediProductService")
	private PEDMPRO000Service ediProductService;

	// 임시보관함용 서비스
	@Resource(name = "pEDMPRO0003Service")
	private PEDMPRO0003Service pEDMPRO0003Service;

	@Resource(name = "nEDMPRO0050Service")
	private NEDMPRO0050Service nEDMPRO0050Service;

	@Autowired
	private ConfigurationService config;

	@Resource(name = "commonProductService")
	private CommonProductService commonProductService;

	// @Autowired
	// private PSCMPRD0010Service pscmprd0010Service;

	@Resource(name = "nEDMPRO0020Service")
	private NEDMPRO0020Service nEDMPRO0020Service;

	@Resource(name = "imageFileMngService")
	private ImageFileMngService imageFileMngService;

	/**
	 * Desc : 딜상품  등록페이지. 
	 * @Method Name : insertDealForm
	 * @param SearchParam
	 * @param model
	 * @return view 페이지
	 */
	@RequestMapping(value = "/edi/product/NEDMPRO0050.do")
	public String insertDealForm(Model model, HttpServletRequest request) throws Exception {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		model.addAttribute("epcLoginVO", epcLoginVO);
		model.addAttribute("teamList", commonProductService.selectNteamList(paramMap, request)); // 팀리스트
		model.addAttribute("nowYear", DateUtil.getToday("yyyy")); // 현재년도

		return "edi/product/NEDMPRO0051";
	}

	/**
	 * Desc: 딜상품 테마 내 상품코드 등록 팝업 폼
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/NEDMPRO005101.do")
	public String insertDealThemaProdForm(Model model, HttpServletRequest request) throws Exception {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		model.addAttribute("epcLoginVO", epcLoginVO);
		return "edi/product/NEDMPRO005101";
	}

	/**
	 * Desc : 딜상품  상세보기 페이지. 
	 * @param 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/DealDetail.do")
	public String selectNewOnlineProdDealDetail(@RequestParam String pgmId, HttpServletRequest request, ModelMap model) throws Exception {
		if (request == null) {
			throw new TopLevelException("");
		}

		Map<String, Object> paramMap = new HashMap<String, Object>();
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		// -----상품상세정보
		paramMap.put("pgmId", StringUtils.trimToEmpty(pgmId));
		paramMap.put("cfmFg", "");
		NEDMPRO0020VO newProdDetailInfo = nEDMPRO0020Service.selectNewTmpOnlineProductDetailInfo(paramMap);

		// 이미지 파일에 전달할 시간 파라미터. 브라우저 캐시 기능때문에 동일한 이름의 이미지 파일이
		// 파일만 바뀌었을 경우, 브라우저에서 이전 이미지가 보여지는 것을 방지해줌.
		long milliSecond = System.currentTimeMillis();

		// 온라인 이미지 파일 목록 조회. 수정페이지에서 온라인 -> 오프라인 pog이미지 이런 순서로 업로드 함.
		String onlineUploadFolder = makeSubFolderForOnline(StringUtils.trimToEmpty(newProdDetailInfo.getPgmId()));
		ArrayList<String> onlineImageList = new ArrayList<String>();
		File dir = new File(onlineUploadFolder);
		FileFilter fileFilter = new WildcardFileFilter(StringUtils.trimToEmpty(newProdDetailInfo.getPgmId()) + "*");
		File[] files = dir.listFiles(fileFilter);
		for (int j = 0; j < files.length; j++) {
			String fileName = files[j].getName();
			if (fileName.lastIndexOf(".") < 0) {
				onlineImageList.add(fileName);
			}
		}
		
		String viewVideoUrl = "";
		if (StringUtils.isNotEmpty(newProdDetailInfo.getVideoUrl())) {
			String videoUrl = newProdDetailInfo.getVideoUrl();
			String filePathArr [] = videoUrl.split(Matcher.quoteReplacement(File.separator));
			viewVideoUrl = filePathArr[filePathArr.length - 1];
		}

		// -----상품 상세정보
		model.addAttribute("newProdDetailInfo", newProdDetailInfo);
		model.addAttribute("viewVideoUrl", viewVideoUrl);
		model.addAttribute("onlineImageList", onlineImageList);
		model.addAttribute("imagePath", ConfigUtils.getString("edi.online.image.url"));
		model.addAttribute("subFolderName", subFolderName(StringUtils.trimToEmpty(newProdDetailInfo.getPgmId())));
		model.addAttribute("epcLoginVO", epcLoginVO);
		model.addAttribute("currentSecond", String.valueOf(milliSecond));
		model.addAttribute("nowYear", DateUtil.getToday("yyyy")); // 현재년도
		model.addAttribute("teamList", commonProductService.selectNteamList(paramMap, request)); // 팀리스트

		return "edi/product/NEDMPRO0051";
	}

	/**
	 * 업로드 동영상 파일 사이즈 체크
	 * @param checkFile
	 * @return
	 * @throws Exception
	 */
	private boolean movFileSizeCheck(File checkFile) throws Exception {
		long maxSize = 1024 * 1024 * 10; // 10,485,760 Byte -> 10MB
		long fileSize = checkFile.length(); // 파일용량
		boolean isOk = fileSize <= maxSize;
		return isOk;
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
	
	private boolean saveDealImg(MultipartHttpServletRequest mptRequest, String pgmId, String uploadFieldCount, String saveModify) throws Exception {

		int newSeq = 0;
		String uploadDir = makeSubFolderForOnline(pgmId); // 대이미지
		Iterator<String> fileIter = mptRequest.getFileNames();
		
		int uploadCnt = 0;
		
		List<String> fileList = new ArrayList<String>(); // 업로드 파일 리스트
		boolean isAllOK = true; // 전체 업로드 처리 여부
		
		while (fileIter.hasNext()) {
			MultipartFile mFile = mptRequest.getFile(fileIter.next());

			if (!mFile.isEmpty()) {
				//logger.debug("UPLOAD mFile.getName() === " + mFile.getName());
				//logger.debug("UPLOAD mFile.getOriginalFilename() === " + mFile.getOriginalFilename());

				if (!"movieFile".equals(mFile.getName())) {
					String fileNm = pgmId + "_" + newSeq;
					String newFileSource = uploadDir + "/" + fileNm;
					String fileFieldName = mFile.getName();
					if (!fileFieldName.startsWith("front")) {
						fileNm = SecureUtil.splittingFilter(fileFieldName);
						newFileSource = uploadDir + "/" + fileNm + "_T2MP";
					}

					FileOutputStream frontImageStream = new FileOutputStream(newFileSource);
					FileCopyUtils.copy(mFile.getInputStream(), frontImageStream);

					fileList.add(newFileSource);

					File fileCheck = new File(newFileSource);
					boolean isOk = imageFileSizeCheck(fileCheck);

					// 최종 확인 조건
					if (!isOk) {
						// 파일 OK 가 아니면
						isAllOK = isOk; // 한개라도 업로드 불가면 모두 업로드 불가
					} else {
						uploadCnt++;
					}
					
					newSeq++;
					
					if (frontImageStream != null) { // FileOutputStream 종료
						try {
							frontImageStream.close();
						} catch (Exception e) {
							logger.error(saveModify, e);
						}
					}
				}
			}
		}

		//logger.debug("uploadFieldCount ::: [" + uploadFieldCount + "]");
		//logger.debug("uploadCnt ::: [" + uploadCnt + "]");

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

				if (uploadFieldCount != null && !"".equals(uploadFieldCount) || uploadCnt > 0) {
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("pgmId", pgmId);
					if (uploadFieldCount != null && !"".equals(uploadFieldCount)) {
						paramMap.put("fileCount", uploadFieldCount);
					} else if (uploadCnt > 0) {
						paramMap.put("fileCount", uploadCnt);
					}

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
		}
		return isAllOK;
	}
	
	// 딜상품 동영상 등록/수정
	private Map saveDealMovie(MultipartHttpServletRequest mptRequest, String pgmId, String saveModify) throws Exception {

		Map rtnMap = new HashMap();
		boolean isAllOK = true;

		int newSeq = 0;
		String videoUploadDir = makeSubFolderForOnlineVideo(pgmId); // 동영상
		Iterator<String> fileIter = mptRequest.getFileNames();

		List<String> fileList = new ArrayList<String>(); // 업로드 파일 리스트
		String newFileSource = "";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pgmId", pgmId);
		String asIsMovieUrl = nEDMPRO0020Service.selectNewProdMovieUrl(paramMap);
		while (fileIter.hasNext()) {
			MultipartFile mFile = mptRequest.getFile(fileIter.next());

			if (!mFile.isEmpty()) {
				if ("movieFile".equals(mFile.getName())) {

					String orgFileNm = mFile.getOriginalFilename();
					String [] orgFileNmSplit = orgFileNm.split("[.]");
					String fileExt = orgFileNmSplit[orgFileNmSplit.length-1]; // 파일확장자 추출

					//String fileNm = pgmId + "_" + newSeq + "." + fileExt; // 여러개 올리는것 대비 "_숫자" 는 보류
					String fileNm = pgmId + "." + fileExt;
					newFileSource = videoUploadDir + "/" + fileNm;
					//String fileFieldName = mFile.getName();
					if (StringUtils.isNotEmpty(asIsMovieUrl)) {
						newFileSource = newFileSource + "_T2MP";
					}
					/*if (StringUtils.isNotEmpty(mptRequest.getParameter("pgmId"))) {
						//fileNm = SecureUtil.splittingFilter(fileFieldName);
						newFileSource = videoUploadDir + "/" + fileNm + "_T2MP";
					}*/
					
					//전체경로 경로순회 문자열 및 종단문자 제거
					newFileSource = StringUtil.getCleanPath(newFileSource, true);
					FileOutputStream frontImageStream = new FileOutputStream(newFileSource);
					FileCopyUtils.copy(mFile.getInputStream(), frontImageStream);

					fileList.add(newFileSource);
					
					File fileCheck = new File(newFileSource);
					boolean isOk = movFileSizeCheck(fileCheck);

					// 최종 확인 조건
					if (!isOk) {
						// 파일 OK 가 아니면
						isAllOK = isOk; // 한개라도 업로드 불가면 모두 업로드 불가
					}
					newSeq++;

					if (frontImageStream != null) { // FileOutputStream 종료
						try {
							frontImageStream.close();
						} catch (Exception e) {
							logger.error(saveModify, e);
						}
					}
				}
			}
		}

		String videoUrl = "";
		if (isAllOK) { // 업로드된 전체 이미지 기준 충족
			if (fileList != null && fileList.size() > 0) {
				int size = fileList.size();
				for (int i = 0; i < size; i++) {
					String fileNm = fileList.get(i);

					//전체경로 경로순회 문자열 및 종단문자 제거
					fileNm = StringUtil.getCleanPath(fileNm, true);
					if (fileNm.indexOf("_T2MP") > -1) {
						File tmpFile = new File(fileNm);
						File uploadFile = new File(fileNm.replaceAll("_T2MP", ""));
						videoUrl = uploadFile.getAbsolutePath();
						FileCopyUtils.copy(tmpFile, uploadFile);
						if (tmpFile.isFile()) {
							FileUtil.delete(tmpFile);
						}
						if (StringUtils.isNotEmpty(asIsMovieUrl)) {
							File asisFile = new File(asIsMovieUrl);
							if (asisFile.isFile()) {
								FileUtil.delete(asisFile);
							}
						}
					} else {
						File uploadFile = new File(fileNm);
						videoUrl = uploadFile.getAbsolutePath();
					}
				}

				if (videoUrl != null && !"".equals(videoUrl)) {
					Map<String, Object> updMap = new HashMap<String, Object>();
					updMap.put("pgmId", pgmId);
					updMap.put("videoUrl", videoUrl);
					nEDMPRO0020Service.updateNewProdMovieUrl(updMap);
				}
			}


		} else { // 업로드된 이미지 기준 미달
			if (fileList != null && fileList.size() > 0) {
				int size = fileList.size();
				for (int i = 0; i < size; i++) {
					String fileNm = fileList.get(i);
					//전체경로 경로순회 문자열 및 종단문자 제거
					fileNm = StringUtil.getCleanPath(fileNm, true);
					File file = new File(fileNm);
					if (file.isFile()) {
						FileUtil.delete(file);
					}
				}
			}
		}

		rtnMap.put("isAllOK", isAllOK);
		rtnMap.put("videoUrl", videoUrl);

		return rtnMap;
	}
	
	/**
	 * 딜상품 동영상 삭제
	 * @param newProduct
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/product/deleteDealMovie", method=RequestMethod.POST)
	public String deleteDealMovie(NewProduct newProduct, HttpServletRequest request, Model model) throws Exception {

		String pgmId = SecureUtil.splittingFilter(newProduct.getNewProductCode());

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pgmId", pgmId);
		String asIsMovieUrl = nEDMPRO0020Service.selectNewProdMovieUrl(paramMap);

		String movDelOk = "";
		if (StringUtils.isNotEmpty(asIsMovieUrl)) {
			File asisFile = new File(asIsMovieUrl);
			if (asisFile.isFile()) {
				FileUtil.delete(asisFile);
			}
			movDelOk = asisFile.exists() ? "N" : "Y";

			if ("Y".equals(movDelOk)) {
				Map<String, Object> updMap = new HashMap<String, Object>();
				updMap.put("pgmId", pgmId);
				updMap.put("videoUrl", null);
				nEDMPRO0020Service.updateNewProdMovieUrl(updMap);
			}
		}

		return "redirect:/edi/product/DealDetail.do?pgmId=" + pgmId + "&mode=delete&imgOk=Y&movOk=Y&movDelOk=" + movDelOk;
	}

	/**
	 * Desc : 추가구성품 등록
	 *        온라인 pog이미지 업로드
	 * @Method Name : saveComponent
	 * @param NewProduct
	 * @param HttpServletRequest
	 * @param Model
	 * @return ModelAndView
	 */
	@RequestMapping(value="/product/saveDealProduct", method=RequestMethod.POST)
	public String saveDealProduct(NewProduct newProduct, HttpServletRequest request, Model model) throws Exception {

		DataMap prmMap = new DataMap(request);

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

		String newProductCd = newProduct.getNewProductCode();
		prmMap.put("pgmId", newProductCd);

		ediProductService.insertProductInfo(newProduct); // 실제 상품 정보 입력.

		nEDMPRO0050Service.newOnlineDealProductSave(prmMap); // 묶음상품 정보 입력

		String pgmId = SecureUtil.splittingFilter(StringUtils.trimToEmpty(newProductCd));
		//String uploadDir = makeSubFolderForOnline(pgmId); // 대이미지
		//String videoUploadDir = makeSubFolderForOnlineVideo(pgmId); // 동영상
		String uploadFieldCount = StringUtils.trimToEmpty((String) request.getParameter("uploadFieldCount")); // 파일개수

		// 온라인 이미지 파일 업로드.
		// 업로드 할때 상품코드와 자연증가 seq변수 값으로 해당 이미지 파일명 설정.
		//int newImgSeq = 0;
		//int newMovSeq = 0;
		MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;
		//Iterator<String> fileIter = mptRequest.getFileNames();
		//List<String> fileList = new ArrayList<String>(); // 업로드 파일 리스트

		// 전체 이미지 업로드 처리 여부
		boolean isImgAllOK = saveDealImg(mptRequest, pgmId, uploadFieldCount, "/product/saveDealProduct.do  saveDealProduct IMAGE (Exception) : ");
		Map movAllOKMap = saveDealMovie(mptRequest, pgmId, "/product/saveDealProduct.do  saveDealProduct MOVIE (Exception) : ");
		Boolean isMovAllOK = (Boolean)movAllOKMap.get("isAllOK");
		//String videoUrl = (String)movAllOKMap.get("videoUrl");

		model.addAttribute("newProduct", newProduct);

		return "redirect:/edi/product/DealDetail.do?pgmId=" + newProductCd + "&mode=save&imgOk=" + (isImgAllOK ? "Y" : "N") + "&movOk=" + (isMovAllOK ? "Y" : "N");
	}

	/**
	 * Desc : 추가구성품 수정
	 *        온라인 pog이미지 업로드
	 * @Method Name : updateComponent
	 * @param NewProduct
	 * @param HttpServletRequest
	 * @param Model
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/product/modifyDealProduct", method = RequestMethod.POST)
	public String updateComponent(NewProduct newProduct, HttpServletRequest request, Model model) throws Exception {
		DataMap prmMap = new DataMap(request);
		//logger.debug("request entpCode === " + request.getParameter("entpCode"));

		String pgmId = SecureUtil.splittingFilter(newProduct.getNewProductCode());
		//String uploadDir = makeSubFolderForOnline(pgmId);
		//String videoUploadIdr = makeSubFolderForOnlineVideo(pgmId); // 동영상
		String uploadFieldCount = StringUtils.trimToEmpty((String) request.getParameter("uploadFieldCount")); // 파일개수

		newProduct.makeItemObject();

		pEDMPRO0003Service.updateNewProductInfoInTemp(newProduct); // 상품 기본, 추가 정보 수정.

		prmMap.put("pgmId", pgmId);

		nEDMPRO0050Service.newOnlineDealProductSave(prmMap); // 묶음상품 정보 입력

		// 온라인 이미지 파일 업로드.
		// 업로드 할때 상품코드와 자연증가 seq변수 값으로 해당 이미지 파일명 설정.
		Integer newSeq = 0;
		MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;
		Iterator<String> fileIter = mptRequest.getFileNames();

		List<String> fileList = new ArrayList<String>(); // 업로드 파일 리스트
		boolean isAllOK = true; // 전체 업로드 처리 여부

		isAllOK = saveDealImg(mptRequest, pgmId, uploadFieldCount, "/product/modifyDealProduct.do  updateComponent IMAGE (Exception) : ");
		Map movAllOKMap = saveDealMovie(mptRequest, pgmId, "/product/modifyDealProduct.do  updateComponent MOVIE (Exception) : ");
		Boolean isMovAllOK = (Boolean)movAllOKMap.get("isAllOK");
		//String videoUrl = (String)movAllOKMap.get("videoUrl");

		model.addAttribute("newProduct", newProduct);

		return "redirect:/edi/product/DealDetail.do?pgmId=" + pgmId + "&mode=update&imgOk=" + (isAllOK ? "Y" : "N") + "&movOk=" + (isMovAllOK ? "Y" : "N");
	}

	@RequestMapping(value = "/edi/product/NEDMPRO0050DealProduct.do")
	public @ResponseBody Map selectDealProductList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map rtnMap = new HashMap<String, Object>();
		DataMap paramMap = new DataMap(request);

		try {
			List<DataMap> list = nEDMPRO0050Service.selectDealProductList(paramMap);
			rtnMap = JsonUtils.convertList2Json((List) list, list.size(), null);
			rtnMap.put("result", true);
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}

	@RequestMapping(value = "/edi/product/NEDMPRO0050ThemaDeal.do")
	public @ResponseBody Map selectThemaDealList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map rtnMap = new HashMap<String, Object>();
		DataMap paramMap = new DataMap(request);

		try {
			List<DataMap> list = nEDMPRO0050Service.selectDealThemaList(paramMap);
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					DataMap thm = list.get(i);
					DataMap param = new DataMap();
					param.put("pgmId", thm.getString("PGM_ID"));
					param.put("themaSeq", thm.getInt("THEMA_SEQ"));
					thm.put("THEMA_PROD", list2JsonOld(nEDMPRO0050Service.selectDealThemaProdList(param)));
				}
			}
			rtnMap = JsonUtils.convertList2Json((List) list, list.size(), null);
			rtnMap.put("result", true);
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		}
		return rtnMap;
	}

	public static String list2JsonOld(List<DataMap> list) {
		JSONArray json_arr = new JSONArray();
		for (Map<Object, Object> map : list) {
			JSONObject json_obj = new JSONObject();
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				String key = (String) entry.getKey();
				Object value = entry.getValue();
				try {
					json_obj.put(key, value);
				} catch (JSONException e) {
					logger.error("list2JsonOld Error", e);
				}
			}
			json_arr.add(json_obj);
		}
		return json_arr.toString();
	}

}
