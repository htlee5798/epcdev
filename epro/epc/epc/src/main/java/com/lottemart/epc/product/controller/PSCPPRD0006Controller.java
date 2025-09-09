package com.lottemart.epc.product.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
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
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.lcnjf.util.DateUtil;
import com.lottemart.common.exception.AlertException;
import com.lottemart.common.exception.AppException;
import com.lottemart.common.file.service.FileMngService;
import com.lottemart.common.file.service.ImageFileMngService;
//import com.lottemart.common.login.model.LoginSession;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.service.CommonService;
import com.lottemart.epc.common.util.SecureUtil;
import com.lottemart.epc.product.model.PSCPPRD0005VO;
import com.lottemart.epc.product.model.PSCPPRD0006VO;
import com.lottemart.epc.product.service.PSCMPRD0001Service;
import com.lottemart.epc.product.service.PSCPPRD0005Service;
import com.lottemart.epc.product.service.PSCPPRD0006Service;

import lcn.module.common.views.AjaxJsonModelHelper;
import lcn.module.framework.property.ConfigurationService;

/**
 * @Class Name : PSCPPRD0006Controller.java
 * @Description :
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 20. 오후 02:02:02 mjChoi
 *
 * &#64;Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 *               </pre>
 */
@Controller
public class PSCPPRD0006Controller {

	private static final Logger logger = LoggerFactory.getLogger(PSCPPRD0006Controller.class);

	@Autowired
	private PSCPPRD0006Service pscpprd0006Service;

	@Autowired
	private ConfigurationService config;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PSCPPRD0005Service pscpprd0005Service;

	@Resource(name = "imageFileMngService")
	private ImageFileMngService imageFileMngService;

	@Autowired
	private FileMngService fileMngService;

	@Autowired
	private PSCMPRD0001Service pscmprd0001Service;

	@Autowired
	private CommonService commonService;

	private static final int BUFFER_SIZE = 1024 * 2;

	/**
	 * 상품 이미지 개별 수정 폼 페이지
	 * 
	 * @Description : 상품 개별 이미지를 보여주고 수정이 가능하다
	 * @Method Name : selectProductImageDetailForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/selectProductImageDetailForm.do")
	public String selectProductImageDetailForm(HttpServletRequest request) throws Exception {
		return "product/internet/PSCPPRD000602";
	}

	/**
	 * 상품 이미지 폼 페이지
	 * 
	 * @Description : 상품 이미지 목록 초기페이지 로딩, 페이지 로딩시 상품이미지 목록을 얻는다
	 * @Method Name : selectProductImageForm
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping("product/selectProductImageForm.do")
	public String selectProductImageForm(HttpServletRequest request) throws Exception {
		try {
			// 파라미터
			Map<String, String> paramMap = new HashMap<String, String>();

			paramMap.put("prodCd", request.getParameter("prodCd"));

			// 데이터 조회
			DataMap resultMap = pscpprd0006Service.selectYoutubeLink(paramMap);
			request.setAttribute("resultMap", resultMap);

			// 데이터 조회
			List<PSCPPRD0006VO> list = pscpprd0006Service.selectPrdImageList(paramMap);
			List<PSCPPRD0006VO> wideList = pscpprd0006Service.selectWidePrdImageList(paramMap);
			PSCPPRD0006VO videoInfoSearch = new PSCPPRD0006VO();
			videoInfoSearch.setProdCd(request.getParameter("prodCd"));
			PSCPPRD0006VO videoInfo = pscpprd0006Service.selectVideoUrl(videoInfoSearch);

			// 데이터 없음
			if (list != null && list.size() > 0) {
				request.setAttribute("resultList", list);
			}

			if (wideList != null && wideList.size() > 0) {
				request.setAttribute("wideList", wideList);
			}

			if (videoInfo != null && StringUtils.isNotEmpty(videoInfo.getVideoUrl())) {
				String fileArr [] = videoInfo.getVideoUrl().split(Matcher.quoteReplacement(File.separator));
				videoInfo.setVideoUrl(fileArr[fileArr.length - 1]);
				request.setAttribute("videoInfo", videoInfo);
			}

			request.setAttribute("imgDir", config.getString("online.product.image.url"));

			//20180715 - 상품수정요청 시 승인요청 또는 반려 일 경우 알림 메시지 제공
			paramMap.put("prodCd", request.getParameter("prodCd"));
			paramMap.put("typeCd", "001");

			List<DataMap> aprvList = pscpprd0006Service.selectAprvList(paramMap);
			request.setAttribute("aprvList", aprvList);
		} catch (Exception e) {
			logger.error("selectProductImageForm (Exception) : " + e.getMessage());
		}

		return "product/internet/PSCPPRD0006";
	}

	/**
	 * 상품 이미지 업데이트 처리
	 * 
	 * @Description : 이미지 등록 버튼 클릭시 업로드된 이미지를 저장 처리 및 삭제, 교체 한다.
	 * @Method Name : prdImageUpdate
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping("product/updateProductImage.do")
	public String updateProductImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String prodCd = (String) request.getParameter("prodCd");
		String itemCd = (String) request.getParameter("itemCd");
		String vendorId = (String) request.getParameter("vendorId");
		String mode = (String) request.getParameter("mode");
		String mdSrcmkCd = (String) SecureUtil.splittingFilterAll(request.getParameter("mdSrcmkCd"));
		String rowCnt = (String) SecureUtil.splittingFilterAll(request.getParameter("rowCnt"));
		String seq = "";

		FileOutputStream frontImageStream = null;
		InputStream is = null;
		int limitSize = 1024 * 600;
		int maxSize = 1500;
		int minSize = 500;
		int wMaxWidthSize = 1312; // WIDTH
		int wMaxHeightSize = 740; // HEIGHT

		/*if("00".equals(rowCnt)) {
			minSize = 740;
		} else {
			minSize = 1500;
		}*/

		String errGbn = "";
		try {
			PSCPPRD0006VO bean = new PSCPPRD0006VO();

			bean.setProdCd(prodCd);
			bean.setItemCd(itemCd);
			bean.setRegId(vendorId);
			bean.setRowCnt(rowCnt);

			DataMap imgChgMap = new DataMap(); // 대표이미지 변경이력
			imgChgMap.put("prodCd", prodCd);
			imgChgMap.put("mdSrcmkCd", mdSrcmkCd);
			imgChgMap.put("rowCnt", rowCnt);
			imgChgMap.put("regId", vendorId);

			String imgName = mdSrcmkCd;
			String uploadDir = makeSubFolderForOnline(imgName);
			String uploadTempDir = makeSubFolderTemp(imgName);

			String fileName = null;
			File fileDeleted = null;
			int actResult = 0;
			boolean actResults = false;

			if ("updateOne".equals(mode)) {
				String size = (String) SecureUtil.splittingFilter(request.getParameter("nameSize"));

				// 이미지 삭제
				String[] legacyFileList = new File(uploadDir).list();

				if (legacyFileList.length > 0) {
					fileName = imgName + "_" + rowCnt + "_" + size + ".jpg";
					fileDeleted = new File(uploadDir + "/" + fileName);
					actResults = fileDeleted.delete();
				}

				// 이미지 입력
				MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;

				@SuppressWarnings("rawtypes")
				Iterator fileIter = mptRequest.getFileNames();

				if (fileIter.hasNext()) {
					MultipartFile mFile = mptRequest.getFile((String) fileIter.next());

					if (!mFile.isEmpty()) {

						String newFileSource = uploadDir + "/" + imgName + "_" + rowCnt + "_" + size + ".jpg";
						frontImageStream = new FileOutputStream(newFileSource);
						actResult = FileCopyUtils.copy(mFile.getInputStream(), frontImageStream);

						if (actResult == 0) {
							throw new IllegalArgumentException("이미지 단건 수정 작업중에 오류가 발생하였습니다.");
						}

					}
				}
			}

			if ("delete".equals(mode)) {
				// 이미지 삭제
				String[] legacyFileList = new File(uploadDir).list();

				//와이드 이미지를 삭제했지만 해당 상품의 대표이미지가 있을 경우 legacyFileList > 0
				if (legacyFileList.length > 0) {
					if (!"00".equals(rowCnt)) {
						fileName = imgName + "_" + rowCnt + "_500.jpg";
						fileDeleted = new File(uploadDir + "/" + fileName);
						actResults = fileDeleted.delete();

						fileName = imgName + "_" + rowCnt + "_250.jpg";
						fileDeleted = new File(uploadDir + "/" + fileName);
						actResults = fileDeleted.delete();

						fileName = imgName + "_" + rowCnt + "_208.jpg";
						fileDeleted = new File(uploadDir + "/" + fileName);
						actResults = fileDeleted.delete();

						fileName = imgName + "_" + rowCnt + "_120.jpg";
						fileDeleted = new File(uploadDir + "/" + fileName);
						actResults = fileDeleted.delete();

						fileName = imgName + "_" + rowCnt + "_90.jpg";
						fileDeleted = new File(uploadDir + "/" + fileName);
						actResults = fileDeleted.delete();

						fileName = imgName + "_" + rowCnt + "_80.jpg";
						fileDeleted = new File(uploadDir + "/" + fileName);
						actResults = fileDeleted.delete();

						fileName = imgName + "_" + rowCnt;
						fileDeleted = new File(uploadDir + "/" + fileName);
						actResults = fileDeleted.delete();
					}
				}

				if ("00".equals(rowCnt)) {
					String wideUploadDir = makeSubFolderForWide(imgName);
					legacyFileList = new File(wideUploadDir).list();
					if (legacyFileList.length > 0) {
						//와이드 이미지 삭제
						fileName = imgName + "_" + rowCnt + "_720_405.jpg";
						fileDeleted = new File(uploadDir + "/" + fileName);
						actResults = fileDeleted.delete();

						//와이드이미지 삭제여부 update
						DataMap histMap = new DataMap();
						histMap.put("prodCd", prodCd);
						histMap.put("wideYN", "N");
						histMap.put("modId", bean.getRegId());
						int result = pscpprd0006Service.updateWideImgYN(histMap);
					}
				} else {
					// 대표이미지 수정이력 (삭제)
					if (!prodCd.startsWith("B") && !prodCd.startsWith("D") && !prodCd.startsWith("L")) { // L코드 상품외에는 대표 이미지 수정이력 테이블에 등록
						imgChgMap.put("chgStatus", "D");
						pscpprd0006Service.insertImgChgHist(imgChgMap);
					}
				}
			}

			if ("update".equals(mode)) { //수정 이력 남기기
				/*DataMap paramMap = new DataMap();
				paramMap.put("prodCd", prodCd);
				paramMap.put("typeCd", "001");
				paramMap.put("rowCnt", rowCnt);

				int iCnt = pscpprd0005Service.selectPrdMdAprvMstCnt(paramMap);
				if (iCnt > 0) {
					Map<String, String> param = new HashMap<String, String>();
					param.put("prodCd", request.getParameter("prodCd"));
					param.put("typeCd", "001");
					param.put("rowCnt", rowCnt);
					PSCPPRD0005VO prdMdAprInfo = pscpprd0005Service.selectPrdMdAprvMst(param);
				
					seq = prdMdAprInfo.getSeq();
				} else {
					PSCPPRD0005VO bean2 = new PSCPPRD0005VO();
					bean2.setProdCd(bean.getProdCd());
					bean2.setRegId(bean.getRegId());
					bean2.setAprvCd("001");
					bean2.setTypeCd("001");
					bean2.setVendorId(vendorId);
				
					seq = pscpprd0005Service.insertPrdMdAprvMst(bean2);
				}
				bean.setSeq(seq);*/

				// 이미지 삭제
				//String[] legacyFileList = new File(uploadTempDir).list();

				//fileName = seq + "_" + rowCnt + ".jpg";
				//fileDeleted = new File(uploadTempDir + "/" + fileName);

				// actResults  = fileDeleted.delete();
				// if (!actResults && "delete".equals(mode)) throw new Exception("이미지 수정1 작업중에 오류가 발생하였습니다.");

				// 이미지 입력
				MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;
				@SuppressWarnings("rawtypes")
				Iterator fileIter = mptRequest.getFileNames();

				if (fileIter.hasNext()) {
					MultipartFile mFile = mptRequest.getFile((String) fileIter.next());

					if (!mFile.isEmpty()) {

						DataMap paramMap = new DataMap();
						paramMap.put("prodCd", prodCd);
						paramMap.put("typeCd", "001");
						paramMap.put("rowCnt", rowCnt);

						int iCnt = pscpprd0005Service.selectPrdMdAprvMstCnt(paramMap);
						if (iCnt > 0) {
							Map<String, String> param = new HashMap<String, String>();
							param.put("prodCd", request.getParameter("prodCd"));
							param.put("typeCd", "001");
							param.put("rowCnt", rowCnt);
							PSCPPRD0005VO prdMdAprInfo = pscpprd0005Service.selectPrdMdAprvMst(param);

							seq = prdMdAprInfo.getSeq();
						} else {
							PSCPPRD0005VO bean2 = new PSCPPRD0005VO();
							bean2.setProdCd(bean.getProdCd());
							bean2.setRegId(bean.getRegId());
							bean2.setAprvCd("001");
							bean2.setTypeCd("001");
							bean2.setVendorId(vendorId);

							seq = pscpprd0005Service.insertPrdMdAprvMst(bean2);
						}
						bean.setSeq(seq);

						// 이미지 삭제
						String[] legacyFileList = new File(uploadTempDir).list();

						fileName = seq + "_" + rowCnt + ".jpg";
						fileDeleted = new File(uploadTempDir + "/" + fileName);

						/** 2019.07.10 파일 용량/사이즈 검증 **/
						is = mFile.getInputStream();
						BufferedImage image = ImageIO.read(is);
						long imgSize = mFile.getSize();
						Integer width = image.getWidth();
						Integer height = image.getHeight();

						if (imgSize > limitSize) {
							errGbn = "limit";
							throw new IllegalArgumentException("600KB이하의 이미지만 업로드 가능합니다.");
						}

						if ("00".equals(rowCnt)) {
							if (width > wMaxWidthSize || height > wMaxHeightSize) {
								errGbn = "imgWideSize";
								throw new IllegalArgumentException("와이드형 사이즈는 1312 X 740 이하입니다.");
							}
						} else {
							if (width - height != 0 || width < minSize || height < minSize || width > maxSize || height > maxSize) {
								errGbn = "imgSize";
								throw new IllegalArgumentException("사이즈는 정사이즈 비율 500px 이상 1500px 이하입니다.");
							}
						}
						/** 파일 용량/사이즈 검증 종료 **/

						if (fileDeleted.exists()) {
							actResults = fileDeleted.delete();
							if (!actResults && "delete".equals(mode)) {
								throw new IllegalArgumentException("이미지 수정1 작업중에 오류가 발생하였습니다.");
							}
						}

						String newFileSource = uploadTempDir + "/" + seq + "_" + rowCnt + ".jpg";
						frontImageStream = new FileOutputStream(newFileSource);

						actResult = FileCopyUtils.copy(mFile.getInputStream(), frontImageStream);
						if (actResult == 0) {
							throw new IllegalArgumentException("이미지 수정2 작업중에 오류가 발생하였습니다.");
						}

						// ImageUtilsThumbnail.resizeAutoForEPC(newFileSource);
						// fileDeleted = new File(newFileSource); //템퍼러리 이미지 삭제
						// fileDeleted.delete();

						//수정이력 남기기
						DataMap map = new DataMap();
						map.put("prodCd", prodCd);
						map.put("typeCd", "001");
						try {
							int resultCnt = pscpprd0006Service.updatePrdImageHist(bean);
						} catch (Exception e) {
							logger.error("updateProductImage1 (Exception) : ", e);
						}

						// 대표이미지 수정시 BOS에서 승인 처리 완료 후 TPR_IMG_CHG_HIST테이블에 등록처리해야하므로 EPC에서 수정에 대한 등록작업 불필요

					}
				}

				//수정이력 남기기
				/*DataMap map = new DataMap();
				map.put("prodCd", prodCd);
				map.put("typeCd", "001");
				try {
					int resultCnt = pscpprd0006Service.updatePrdImageHist(bean);
				} catch (Exception e) {
					logger.error("updateProductImage1 (Exception) : ", e);
				}*/
			}

			if ("insert".equals(mode)) {
				// 이미지 입력
				MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;
				@SuppressWarnings("rawtypes")
				Iterator fileIter = mptRequest.getFileNames();

				if (fileIter.hasNext()) {
					MultipartFile mFile = mptRequest.getFile((String) fileIter.next());

					if (!mFile.isEmpty()) {
						/** 2019.07.10 파일 용량/사이즈 검증 **/
						is = mFile.getInputStream();
						BufferedImage image = ImageIO.read(is);
						long imgSize = mFile.getSize();
						Integer width = image.getWidth();
						Integer height = image.getHeight();

						if (imgSize > limitSize) {
							errGbn = "limit";
							throw new IllegalArgumentException("600KB이하의 이미지만 업로드 가능합니다.");
						}

						if ("00".equals(rowCnt)) {
							if (width > wMaxWidthSize || height > wMaxHeightSize) {
								errGbn = "imgWideSize";
								throw new IllegalArgumentException("와이드형 사이즈는 1312 X 740 이하입니다.");
							}
						} else {
							if (width - height != 0 || width < minSize || height < minSize || width > maxSize || height > maxSize) {
								errGbn = "imgSize";
								throw new IllegalArgumentException("사이즈는 정사이즈 비율 500px 이상 1500px 이하입니다.");
							}
						}
						/** 파일 용량/사이즈 검증 종료 **/

						String newFileSource = "";
						if (!"00".equals(rowCnt)) { //대표이미지 등록
							newFileSource = uploadDir + "/" + imgName + "_" + rowCnt;

							frontImageStream = new FileOutputStream(newFileSource);

							actResult = FileCopyUtils.copy(mFile.getInputStream(), frontImageStream);
							if (actResult == 0)
								throw new IllegalArgumentException("이미지 등록 작업중에 오류가 발생하였습니다.");

							imageFileMngService.purgeImageQCServer("02", imgName + "_" + rowCnt);
							imageFileMngService.purgeCDNServer("02", imgName + "_" + rowCnt);

							// 대표이미지 수정이력 (등록)
							if (!prodCd.startsWith("B") && !prodCd.startsWith("D") && !prodCd.startsWith("L")) { // L코드 상품외에는 대표 이미지 수정이력 테이블에 등록
								imgChgMap.put("chgStatus", "I");
								pscpprd0006Service.insertImgChgHist(imgChgMap);
							}

							// fileDeleted = new File(newFileSource); //템퍼러리 이미지 삭제
							// fileDeleted.delete();
						} else { //와이드 이미지 등록
							String wideUploadDir = makeSubFolderForWide(imgName);
							newFileSource = wideUploadDir + "/" + imgName + "_" + rowCnt;
							frontImageStream = new FileOutputStream(newFileSource);

							actResult = FileCopyUtils.copy(mFile.getInputStream(), frontImageStream);
							if (actResult == 0)
								throw new IllegalArgumentException("이미지 등록 작업중에 오류가 발생하였습니다.");

							imageFileMngService.purgeImageQCServer("11", imgName + "_" + rowCnt);
							imageFileMngService.purgeCDNServer("11", imgName + "_" + rowCnt);

							//와이드이미지 등록여부 update
							DataMap histMap = new DataMap();
							int result = 0;
							histMap.put("prodCd", prodCd);
							histMap.put("wideYN", "Y");
							histMap.put("modId", bean.getRegId());
							result = pscpprd0006Service.updateWideImgYN(histMap);
						}
					}
				}

			}

			if ("insert".equals(mode) || "delete".equals(mode)) {
				try {
					int resultCnt = 0;
					//와이드 이미지가 아닐 경우에만 업데이트
					if (!"00".equals(rowCnt)) {
						if ("insert".equals(mode)) {
							resultCnt = pscpprd0006Service.updatePrdImageAdd(bean);
						} else if ("delete".equals(mode)) {
							resultCnt = pscpprd0006Service.updatePrdImageDel(bean);
						}
					}

					if ("00".equals(rowCnt)) { //와이드이미지 TYPE : 11
						imageFileMngService.purgeImageQCServer("11", imgName + "_" + rowCnt);
						imageFileMngService.purgeCDNServer("11", imgName + "_" + rowCnt);
					} else {
						imageFileMngService.purgeImageQCServer("02", imgName + "_" + rowCnt);
						imageFileMngService.purgeCDNServer("02", imgName + "_" + rowCnt);
					}
				} catch (Exception e) {
					logger.error("updateProductImage2 (Exception) : ", e);
				}
			}

			request.setAttribute("mode", mode);
			request.setAttribute("prodCd", prodCd);
			request.setAttribute("status", "success");
			request.setAttribute("ment", "처리완료 되었습니다.");
		} catch (Exception e) {
			// 오류
			request.setAttribute("mode", mode);
			request.setAttribute("prodCd", prodCd);
			request.setAttribute("status", "fail");
			if ("limit".equals(errGbn)) {
				request.setAttribute("ment", "600KB이하의 이미지만 업로드 가능합니다.");
			} else if ("imgWideSize".equals(errGbn)) {
				request.setAttribute("ment", "와이드형 사이즈는 1312 X 740 이하입니다.");
			} else if ("imgSize".equals(errGbn)) {
				request.setAttribute("ment", "사이즈는 정사이즈 비율 500px 이상 1500px 이하입니다.");
			} else {
				request.setAttribute("ment", "이미지 등록,수정,삭제 작업중에 오류가 발생하였습니다.");
			}

			logger.error("updateProductImage3 (Exception) : ", e);
		} finally {
			if (frontImageStream != null) {
				try {
					frontImageStream.close();
				} catch (Exception e) {
					logger.error("updateProductImage4 (Exception) : ", e);
				}
			}
		}

		return "product/internet/PSCPPRD000601";
	}

	public String makeSubFolderForOnline(String imgName) {
		String subFolderNames = subFolderName(imgName);
		String uploadDir = config.getString("online.product.image.path") + "/" + subFolderNames;
		//--저장경로와 조회경로 구분 및 개발, 실서버의 저장경로 구분 필요

		File dirPath = new File(uploadDir);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
		return uploadDir;
	}

	public String makeSubFolderForWide(String imgName) {
		String subFolderNames = imgName.substring(0, 5) + "/" + imgName.substring(5, 9);
		String uploadDir = config.getString("online.product.wide.image.path") + "/" + subFolderNames;

		File dirPath = new File(uploadDir);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
		return uploadDir;
	}

	public String makeSubFolderTemp(String imgName) {
		String uploadDir = config.getString("online.product.imageTemp.path") + "/" + imgName;
		//--저장경로와 조회경로 구분 및 개발, 실서버의 저장경로 구분 필요

		File dirPath = new File(uploadDir);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
		return uploadDir;
	}

	public String subFolderName(String imgName) {
		return imgName.substring(0, 5);
	}
	
	public String makeDetailFolder(String imgDetailMainPath) {
		String uploadDir = imgDetailMainPath + "/" + "images" + "/" + getChildDirectory(imgDetailMainPath + "/" + "images", "");

		File dirPath = new File(uploadDir);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}

		return uploadDir;
	}

	public String makeSubFolderForVideo(String videoName) {
		String subFolderNames = videoName.substring(0, 5) + "/" + videoName.substring(5, 9);
		String uploadDir = config.getString("online.product.video.image.path") + "/" + subFolderNames;
		//logger.debug("[video real dir : " + uploadDir + " ]");

		File dirPath = new File(uploadDir);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
		return uploadDir;
	}

	private boolean movFileSizeCheck(File checkFile) throws Exception {
		long maxSize = 1024 * 1024 * 10; // 10,485,760 Byte -> 10MB
		long fileSize = checkFile.length(); // 파일용량
		boolean isOk = fileSize <= maxSize;
		return isOk;
	}

	@RequestMapping("product/updateProductVideo.do")
	public String videoSave(HttpServletRequest request) throws Exception {
		String vendorId = request.getParameter("vendorId");
		String prodCd = request.getParameter("prodCd");
		String mode = request.getParameter("mode");

		int actResult;
		FileOutputStream frontImageStream = null;
		try {
			String videoName = prodCd; // + ".mp4"

			PSCPPRD0006VO currVideo = new PSCPPRD0006VO();
			currVideo.setProdCd(prodCd);
			currVideo = pscpprd0006Service.selectVideoUrl(currVideo);
			String currVideoUrl = currVideo.getVideoUrl();

			String uploadDir = makeSubFolderForVideo(prodCd);

			// 기존 파일 삭제
			/*String[] legacyFileList = new File(uploadDir).list();
			if (legacyFileList.length > 0) {
				String fileName = videoName;
				File delFile = new File(uploadDir + "/" + fileName);
				delFile.delete();
			}*/

			if ("update".equals(mode)) {
				//logger.debug("[ video add begin ]");
				MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;
				@SuppressWarnings("rawtypes")
				Iterator fileIter = mptRequest.getFileNames();

				if (fileIter.hasNext()) {
					MultipartFile mFile = mptRequest.getFile((String) fileIter.next());

					String orgFileNm = mFile.getOriginalFilename();
					String [] orgFileNmSplit = orgFileNm.split("[.]");
					String fileExt = orgFileNmSplit[orgFileNmSplit.length-1]; // 파일확장자 추출

					String newFileSource = uploadDir + "/" + videoName + "." + fileExt;
					String tmpFileSource = uploadDir + "/" + videoName + "." + fileExt + "_T2MP";
					
					//logger.debug("video upload : " + tmpFileSource);
					frontImageStream = new FileOutputStream(tmpFileSource);
					actResult = FileCopyUtils.copy(mFile.getInputStream(), frontImageStream);

					if (actResult == 0) {
						throw new AlertException("동영상 업로드 작업중에 오류가 발생하였습니다.");
					}
					actResult = 0;
					// 업로드 파일이 상품코드 + 확장자 + _T2MP 로 정상 생성되면
					// 기존 파일을 삭제하고
					//logger.debug("currVideoUrl = [" + currVideoUrl + "]");
					if (StringUtils.isNotBlank(currVideoUrl)) {
						File delFile = new File(currVideoUrl);
						if (delFile.isFile()) {
							delFile.delete();
						}
					}

					// 새로운 파일을 _T2MP 를 날리고 새로 쓴다.
					//logger.debug("newFileSource = [" + newFileSource + "]");
					//logger.debug("tmpFileSource = [" + tmpFileSource + "]");
					File newFile = new File(newFileSource);
					File tmpFile = new File(tmpFileSource);
					actResult = FileCopyUtils.copy(tmpFile, newFile);
					if (actResult > 0) {
						tmpFile.delete();
					}

					PSCPPRD0006VO videoBean = new PSCPPRD0006VO();
					videoBean.setProdCd(prodCd);
					videoBean.setVideoUrl(newFile.getAbsolutePath());
					videoBean.setRegId(vendorId);
					videoBean.setModId(vendorId);
					int result = pscpprd0006Service.updateVideoUrl(videoBean);
					/*if (result == 0) {
						logger.debug("========== " + prodCd + " 동영상 등록여부 update 오류=======");
					}*/
				}

			} else {
				if (StringUtils.isNotBlank(currVideoUrl)) {
					File delFile = new File(currVideoUrl);
					if (delFile.isFile()) {
						delFile.delete();
					}
				}

				PSCPPRD0006VO videoBean = new PSCPPRD0006VO();
				videoBean.setProdCd(prodCd);
				videoBean.setVideoUrl(null);
				videoBean.setRegId(vendorId);
				videoBean.setModId(vendorId);
				int result = pscpprd0006Service.updateVideoUrl(videoBean);
				/*if (result == 0) {
					logger.debug("========== " + prodCd + " 동영상 삭제여부 update 오류=======");
				}*/
			}

			//logger.debug("[ video add finish ]");

//			pscpprd0006Service.sendRestCall(prodCd);
			request.setAttribute("mode", mode);
			request.setAttribute("prodCd", prodCd);
			request.setAttribute("status", "success");
			request.setAttribute("ment", "처리완료 되었습니다.");

		} catch (Exception e) {
			/*StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.error(sw.toString());*/
			// 오류
			request.setAttribute("mode", mode);
			request.setAttribute("prodCd", prodCd);
			request.setAttribute("status", "fail");
			request.setAttribute("ment", "동영상 작업중에 오류가 발생하였습니다.");
			logger.error("[ videoSave ] " + e.getMessage());
		} finally {
			if (frontImageStream != null) {
				try {
					frontImageStream.close();
				} catch (Exception e) {
					logger.error("updateProductMovie (Exception) : ", e);
				}
			}
		}
		return "product/internet/PSCPPRD000601";
	}

	public String getDetailUrl(String detailFilePath) {
		//edi.namoeditor.file.path=/nas_web/lim/static_root/images/onlinedescr
		//edi.namoeditor.file.url=https://simage.lottemart.com/lim/static_root/images/onlinedescr  "/" + 
		String detailUrl = config.getString("edi.namoeditor.file.url") + detailFilePath.replaceAll(config.getString("edi.namoeditor.file.path"), "");
		return detailUrl;
	}

	public String getChildDirectory(String path, String maxCount) {

		if ("".equals(maxCount)) {
			maxCount = "100";
		}

		boolean childFlag = false;
		int childNum = 0;
		String childName = "";
		int fileInt = 0;

		File dir = new File(path);
		if (!dir.exists()) {
			return childName = "";
		}
		int listLength = 0;
		if (dir != null) {
			listLength = dir.list().length;
		}

		for (int i = 0; i < listLength; i++) {
			File tmpFile = new File(path + File.separator + dir.list()[i]);
			try {
				if (tmpFile.isDirectory()) {
					fileInt = Integer.parseInt(tmpFile.getName());
					childFlag = true;
					if (fileInt > childNum) {
						childNum = fileInt;
						childName = tmpFile.getName();
					}
				}
			} catch (NumberFormatException e) {
				continue;
			}
		}

		if (!childFlag) {
			childNum++;
			childName = "000000" + Integer.toString(childNum);
			childName = childName.substring(childName.length() - 6);
			File dirNew = new File(path + File.separator + childName);
			dirNew.mkdir();
		}

		String childPath = path + File.separator + childName;

		File dir3 = new File(childPath);
		int cCount = 0;
		if (dir3 != null) {
			listLength = dir3.list().length;
		}
		for (int i = 0; i < listLength; i++) {
			File tmpFiles = new File(childPath + File.separator + dir3.list()[i]);
			if (tmpFiles.isFile()) {
				cCount++;
			}
		}
		if (cCount >= Integer.parseInt(maxCount)) {
			childNum++;
			if (Integer.toString(childNum).length() > 6) {
				childName = Integer.toString(childNum);
			} else {
				childName = "000000" + Integer.toString(childNum);
				childName = childName.substring(childName.length() - 6);
			}

			File dir4 = new File(path + File.separator + childName);
			dir4.mkdir();
		}
		return childName;
	}

	/**
	 * Youtube 업로드
	 * @Description : Youtube 업로드
	 * @Method Name : prdImageUpdate
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/youtubeSave.do")
	public ModelAndView youtubeSave(HttpServletRequest request) throws Exception {

		String message = "";
		message = messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault());

		String prodCd = StringUtils.defaultString(request.getParameter("PROD_CD"));
		String prodUrl = StringUtils.defaultString(request.getParameter("PROD_URL"));

		DataMap dm = new DataMap();
		dm.put("PROD_CD", prodCd);
		dm.put("REG_ID", StringUtils.defaultString(request.getParameter("vendorId")));

		if (prodUrl != null && !("").equals(prodUrl)) {
			if (prodUrl.indexOf("youtu.be/") > -1) {
				prodUrl = "https://www.youtube.com/embed/" + prodUrl.substring(prodUrl.indexOf("youtu.be/") + 9, prodUrl.length());
			}

			try {
				URL url = new URL(prodUrl);
				URLConnection conn = url.openConnection();
				InputStream is = conn.getInputStream();
				Scanner scan = new Scanner(is);
				is.close();
				scan.close();
			} catch (MalformedURLException e) {
				return AjaxJsonModelHelper.create("YOUTUBE 주소가 잘못되었습니다.");
			} catch (IOException e) {
				return AjaxJsonModelHelper.create("YOUTUBE 연결할수 없습니다.");
			}
		}

		try {
			dm.put("PROD_URL", prodUrl);
			int resultCnt = pscpprd0006Service.updateYoutubeSave(dm);

			if (resultCnt > 0) {
				return AjaxJsonModelHelper.create("");
			} else {
				return AjaxJsonModelHelper.create("");
				//return AjaxJsonModelHelper.create("업데이트 된 상품이 없습니다.");
			}
		} catch (Exception e) {
			return AjaxJsonModelHelper.create(message);
		}
	}

	/**
	 * 이미지 일괄 수정
	 * 
	 * @Description : 이미지 일괄 수정
	 * @Method Name : batchProductImageUploadForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/batchProductImageUploadForm.do")
	public String batchProductImageUploadForm(HttpServletRequest request) throws Exception {
		return "product/internet/PSCPPRD000611";
	}

	/**
	 * 이미지 일괄 수정 - 엑셀양식 다운로드
	 * 
	 * @Description : 이미지 일괄 수정 - 엑셀양식 다운로드
	 * @Method Name : batchProductExcelUploadForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/batchProductExcelUploadForm.do")
	public void batchProductExcelUploadForm(HttpServletRequest request, HttpServletResponse response) throws Exception {

		DataMap paramMap = new DataMap(request);
		String imgType = request.getParameter("imgType");
		paramMap.put("imgType", imgType);

		response.setContentType("application/x-msdownload;charset=UTF-8");
		String userAgent = request.getHeader("User-Agent");
		String fileNmVal = "이미지_일괄수정";
		if ("1".equals(imgType)) {
			fileNmVal = "대표이미지_일괄수정";
		} else if ("2".equals(imgType)) {
			fileNmVal = "상세이미지_일괄수정";
		}

		String fileName = fileNmVal + "_양식.xls";

		fileName = URLEncoder.encode(fileName, "UTF-8");

		if (userAgent.indexOf("MSIE 5.5") > -1) {
			response.setHeader("Content-Disposition", "filename=" + fileName + ";");
		} else {
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ";");
		}

		String[] newHeaders = new String[1];
		int headerHeight = 0;

		String title = "1".equals(imgType) ? "대표" : "상세";
		String[] headers = { "* 상품코드", "상품명", "* 상품 이미지순번", "* " + title + "이미지 파일명" };
		newHeaders = headers;
		headerHeight = 2000;

		// 헤더출력
		int headerLength = newHeaders.length;

		// create a wordsheet
		HSSFWorkbook workbook = new HSSFWorkbook();
		CreationHelper createHelper = workbook.getCreationHelper();
		HSSFSheet sheet = workbook.createSheet(fileNmVal + " 양식");

		HSSFRow header1 = sheet.createRow(0);
		HSSFCellStyle styleHd = workbook.createCellStyle();
		HSSFCellStyle styleHd2 = workbook.createCellStyle();
		HSSFCellStyle styleHd3 = workbook.createCellStyle();
		HSSFCellStyle styleRow = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		HSSFFont wrnfont = workbook.createFont();
		HSSFCell cell = null;

		sheet.addMergedRegion((new Region((int) 0, (short) 0, (int) 0, (short) 7)));

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

		styleHd3.setFont(wrnfont);
		styleHd3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleHd3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		styleHd3.setWrapText(true);

		styleRow.setWrapText(true);
		styleRow.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleRow.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);

		StringBuilder infoVal = new StringBuilder();
		infoVal.append("※ * 표시된 항목은 필수 입력입니다. 미입력시 일괄등록이 되지 않으니 유의하시기 바랍니다.\n");
		infoVal.append("※ 셀서식 표시형식은 텍스트로 지정 후 입력 하세요.\n");
		infoVal.append("※ 거래유형이 위수탁만 등록이 가능합니다.\n");
		infoVal.append("※ 이미지 파일명은 확장자까지 입력 하세요.\n");
		infoVal.append("※ 업로드할 이미지 파일은 zip 파일로 전체 압축 후 업로드 하세요.\n");
		infoVal.append("※ 대표이미지, 상세이미지 zip 파일은 10MB(10,485,760 Byte) 이하로 업로드 가능합니다.\n");
		infoVal.append("※ zip 파일 내 이미지 파일수는 20개 이하로 가능합니다.");
		if ("2".equals(imgType)) {
			infoVal.append("\n");
			infoVal.append("※ zip 파일 내 상세이미지는 1개당 5MB(5,242,880 Byte)까지 가능합니다.");
		}

		header1.createCell(0).setCellValue(infoVal.toString());
		header1.getCell(0).setCellStyle(styleHd2);
		header1.setHeight((short) headerHeight);

		HSSFRow header2 = sheet.createRow(1);

		for (int i = 0; i < headerLength; i++) {
			cell = header2.createCell(i);
			cell.setCellValue(newHeaders[i]);
			cell.setCellStyle(styleHd);

			if (newHeaders[i].length() < 10) {
				sheet.setColumnWidth(i, 4000);
			} else {
				sheet.setColumnWidth(i, 10000);
			}
		}

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);
		ArrayList<String> aryList = null;
		// 선택한 vendorId가null 이거나 repVendorId ( 대표협력업체 ) 일때 협력사 전체 검색
		/*if ((paramMap.getString("asVendorId") == null || paramMap.getString("asVendorId").equals(epcLoginVO.getRepVendorId())
				|| "".equals(paramMap.getString("asVendorId"))) && epcLoginVO != null) {
			aryList = new ArrayList<String>();
		
			for (int i = 0; i < epcLoginVO.getVendorId().length; i++) {
				aryList.add(epcLoginVO.getVendorId()[i]);
			}
			paramMap.put("asVendorId", aryList);
		} else {*/
		aryList = new ArrayList<String>();
		aryList.add(paramMap.getString("asVendorId"));
		paramMap.put("asVendorId", aryList);
		/*}*/

		// 선택한 vendor가 openappi 업체일 경우 전체 검색
		/*List<EpcLoginVO> openappiVendorId = commonService.selectOpenappiVendor();
		for (int l = 0; openappiVendorId.size() > l; l++) {
			if (openappiVendorId.get(l).getRepVendorId().equals(paramMap.getString("asVendorId").replace("[", "").replace("]", "").trim())) {
				aryList = new ArrayList<String>();
				for (int a = 0; a < epcLoginVO.getVendorId().length; a++) {
					aryList.add(epcLoginVO.getVendorId()[a]);
					paramMap.put("asVendorId", aryList);
				}
			}
		}*/

		// 상품코드 여러건 조회
		if ("01".equals(paramMap.getString("asKeywordSelect"))) {
			String[] prodCdArr = paramMap.getString("asKeywordValue").split(",");
			paramMap.put("prodCdArr", prodCdArr);
		}

		if ("1".equals(imgType) || "2".equals(imgType)) {
			List<DataMap> list = pscmprd0001Service.selectPrdListForBatchImageUpload(paramMap); // TODO 수정요망

			String[] colMapping = { "PROD_CD", "PROD_NM", "IMG_SEQ", "IMG_NM" };
			for (int i = 1; i <= list.size(); i++) {
				DataMap mapInfo = list.get(i - 1);
				HSSFRow rowAdd = sheet.createRow(1 + i);

				for (int j = 0; j < colMapping.length; j++) {
					cell = rowAdd.createCell(j);
					cell.setCellValue(mapInfo.getString(colMapping[j]));
				}
			}
		} /*else {
			HSSFRow rowAdd = sheet.createRow(2);
			cell = rowAdd.createCell(0);
			cell.setCellValue("L000000000001");
			cell = rowAdd.createCell(1);
			cell.setCellValue("상품명");
			cell = rowAdd.createCell(2);
			cell.setCellValue("1");
			cell = rowAdd.createCell(3);
			cell.setCellValue("image.jpg");
			}*/

		DataFormat format = workbook.createDataFormat();
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setDataFormat(format.getFormat("@"));

		Row rows = sheet.getRow(1);
		int cellCnt = (int) rows.getLastCellNum();

		for (int j = 0; j < cellCnt; j++) {
			sheet.setDefaultColumnStyle(j, cellStyle);
		}

		ServletOutputStream out = response.getOutputStream();
		workbook.write(out);
		out.flush();
	}

	/**
	 * 이미지 일괄 수정 - 엑셀업로드
	 * 
	 * @Description : 이미지 일괄 수정 - 엑셀업로드
	 * @Method Name : batchProductExcelUpload
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/batchProductExcelUpload.do")
	public @ResponseBody Map batchProductExcelUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {

		MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;
		Map<String, Object> rtnMap = null;
		DataMap paramMap = new DataMap(request);
		String imgType = paramMap.getString("imgType");
		String vendorId = paramMap.getString("vendorId");

		int imageMaxFileCnt = config.getInt("online.product.zip.image.maxCount"); // 대표이미지 압축파일내 파일 제한갯수 20개
		int detailImageMaxFileCnt = config.getInt("online.product.zip.detailImage.maxCount"); // 상세이미지 압축파일내 파일 제한갯수 20개
		int maxFileCnt = 20;
		if ("1".equals(imgType)) {
			maxFileCnt = imageMaxFileCnt;
		} else if ("2".equals(imgType)) {
			maxFileCnt = detailImageMaxFileCnt;
		}

		int sucCnt = 0;
		int errCnt = 0;
		int listSize = 0;
		boolean isSuccess = true;

		String msgWrn = "";
		List<DataMap> imgList = null;
		try {
			String[] colNms = { "PROD_CD", "PROD_NM", "IMG_SEQ", "IMG_NM" };
			//logger.debug("/product/batchProductExcelUpload.do EXCEL UPLOAD START");
			imgList = fileMngService.readUploadNullColExcelFile(mptRequest, colNms, 2, 0);
			//logger.debug("/product/batchProductExcelUpload.do EXCEL " + (imgList == null ? "LIST NULL" : "LIST NOT NULL"));
			//logger.debug("/product/batchProductExcelUpload.do EXCEL " + (imgList != null ? "SIZE = " + imgList.size() : "SIZE = 0"));
			//logger.debug("/product/batchProductExcelUpload.do EXCEL UPLOAD END");
		} catch (Exception e) {
			// 작업오류
			logger.error("error message --> ", e);
			msgWrn = "* 올바르지 않은 양식 입니다. 확인 후 다시 업로드 하세요.";
		}

		if ("".equals(msgWrn)) { // 파일형식이 정상인경우
			//logger.info("/product/batchProductExcelUpload.do EXCEL UPLOAD SCRIPT START");

			if (imgList != null && imgList.size() > 0) {
				listSize = imgList.size();

				if (listSize <= maxFileCnt) {
					for (int i = 0; i < listSize; i++) {
						DataMap map = imgList.get(i);

						if ("".equals(map.getString("PROD_CD")) || "".equals(map.getString("IMG_NM"))) {
							msgWrn = "필수값이 입력 안되었거나 양식이 올바르지 않습니다.";
							map.put("ERR_MSG", "필수값이 입력 안되었거나 양식이 올바르지 않습니다.");
							errCnt++;
							isSuccess = false;
						} else {
							if (map.getString("PROD_CD").length() != 13) {
								msgWrn = "상품코드의 자릿수가 맞지 않습니다.";
								map.put("ERR_MSG", "상품코드의 자릿수가 맞지 않습니다.");
								errCnt++;
								isSuccess = false;
							} else {
								DataMap selectMap = new DataMap();
								selectMap.put("prodCd", map.getString("PROD_CD"));
								selectMap.put("vendorId", vendorId);

								DataMap mdSrcmkCd = pscpprd0006Service.selectMdSrcmkCd(selectMap);
								if (mdSrcmkCd == null) {
									msgWrn = "상품코드를 찾을 수 없습니다.";
									map.put("ERR_MSG", "상품코드를 찾을 수 없습니다.");
									errCnt++;
									isSuccess = false;
								}
							}
						}

						String imgSeq = map.getString("IMG_SEQ");
						if ("1".equals(imgType)) { // 대표이미지
							if ("1:2:3:4".indexOf(imgSeq) < 0) {
								msgWrn = "대표이미지의 이미지순번은 1~4 까지 가능합니다.";
								map.put("ERR_MSG", "대표이미지의 이미지순번은 1~4 까지 가능합니다.");
								errCnt++;
								isSuccess = false;
							}
						} /*else if ("2".equals(imgType)) { // 상세이미지
							
							if (!"1".equals(imgSeq)) {
								msgWrn = "상세이미지의 상품 이미지순번은 1 로 입력해주세요.";
								map.put("ERR_MSG", "필수값이 입력 안되었거나 양식이 올바르지 않습니다.");
								errCnt++;
							}
							}*/
						if (isSuccess) {
							sucCnt++;
						} else {
							isSuccess = true;
						}
					}

					rtnMap = JsonUtils.convertList2Json((List) imgList, imgList.size(), "1");
					rtnMap.put("msg", "");
					rtnMap.put("errCnt", errCnt);
					rtnMap.put("sucCnt", sucCnt);
				} else {
					if (rtnMap == null) {
						rtnMap = new HashMap<String, Object>();
					}
					rtnMap.put("errCnt", -999);
					rtnMap.put("msg", "업로드할 상품코드와 이미지파일명은  제한 갯수(" + maxFileCnt + "개) 이하로 입력해주세요.");
				}
			}
		} else {
			if (rtnMap == null) {
				rtnMap = new HashMap<String, Object>();
			}
			rtnMap.put("errCnt", -999);
			rtnMap.put("msg", msgWrn);
		}
		return rtnMap;
	}

	/**
	 * 이미지 압축파일 업로드
	 *
	 * @param request
	 * @param reuqest
	 * @return
	 * @throws Exception DataMap reqParamMap
	 */
	@RequestMapping(value = "/product/batchProductImgZip.do")
	public @ResponseBody Map batchProductImgZip(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> rtnMap = new HashMap<String, Object>();
		DataMap reqParamMap = new DataMap(request);
		String imgType = reqParamMap.getString("imgType"); // 1: 대표, 2: 상세
		String vendorId = reqParamMap.getString("vendorId");

		String imsiDir = "/" + vendorId + "_" + DateUtil.getTimeStamp(); // zipTemp 하위 임시디렉토리 생성패턴: 300900_20211217110954808
		String prodImgUploadPath = config.getString("online.product.zipTempPath") + imsiDir; // /nas_web/lim/static_root/images/zipTemp
		File dirPath = new File(prodImgUploadPath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}

		//int mpFileCnt = 0;
		DataMap unZipRsltMap = null;
		List<String> zipRtnList = null;
		int zipErrCtn = 0;
		int zipRtnCnt = 0;
		String zipRtnStr = "";
		String[] zipRtnArr = null;
		List<String> errMsgList = new ArrayList<String>();

		final long imageMaxFileSize = config.getInt("online.product.zip.image.maxSize"); // 대표이미지 압축파일 제한 사이즈 10MB
		final long detailImageMaxFileSize = config.getInt("online.product.zip.detailImage.maxSize"); // 상세이미지 압축파일 제한 사이즈 10MB
		long maxFileSize = 10485760; // Default 10MB
		final long imageMaxFileCnt = config.getInt("online.product.zip.image.maxCount"); // 대표이미지 압축파일내 파일 제한갯수 20개
		final long detailImageMaxFileCnt = config.getInt("online.product.zip.detailImage.maxCount"); // 상세이미지 압축파일내 파일 제한갯수 20개
		long maxFileCnt = 10;
		if ("1".equals(imgType)) {
			maxFileSize = imageMaxFileSize;
			maxFileCnt = imageMaxFileCnt;
		} else if ("2".equals(imgType)) {
			maxFileSize = detailImageMaxFileSize;
			maxFileCnt = detailImageMaxFileCnt;
		}

		FileOutputStream fos = null;
		InputStream is = null;
		MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;
		try {
			String orginFileName = ""; //원본 파일 명
			final Map<String, MultipartFile> fileMap = mptRequest.getFileMap();

			if (!fileMap.isEmpty()) {
				MultipartFile mpFile;
				Entry<String, MultipartFile> entry;

				Iterator<Entry<String, MultipartFile>> iter = fileMap.entrySet().iterator();
				while (iter.hasNext()) {
					entry = iter.next();

					mpFile = entry.getValue();
					logger.error("mpFile.getSize() : " + mpFile.getSize());

					boolean isProc = true; // 처리 플래그

					/* 용량체크 */
					long fileSize = mpFile.getSize();
					if (fileSize > maxFileSize) {
						isProc = false;
						rtnMap.put("errCnt", 1);
						throw new AppException("업로드된 zip파일이 제한용량보다 큽니다.");
					}

					if (isProc) { // 용량 기준에 맞으면 처리
						orginFileName = mpFile.getOriginalFilename();
						is = mpFile.getInputStream();

						fos = new FileOutputStream(prodImgUploadPath + File.separator + orginFileName);
						int actResult = FileCopyUtils.copy(is, fos);

						if (actResult == 0) {
							throw new AppException("업로드 작업중에 오류가 발생하였습니다.");
						} else { // 업로드 정상인경우 스트림 닫기
							if (fos != null) {
								try {
									fos.close();
								} catch (Exception e) {
									StringWriter sw = new StringWriter();
									e.printStackTrace(new PrintWriter(sw));
									logger.error("batchProductImgZip (Exception) : " + sw.toString());
								}
							}
							if (is != null) {
								try {
									is.close();
								} catch (Exception e) {
									StringWriter sw = new StringWriter();
									e.printStackTrace(new PrintWriter(sw));
									logger.error("batchProductImgZip (Exception) : " + sw.toString());
								}
							}
						}

						File orgFile = new File(prodImgUploadPath + File.separator + orginFileName);
						File orgFilePath = new File(prodImgUploadPath);

						unZipRsltMap = unzip(orgFile, orgFilePath, imgType); // zip파일 압축해제 후 결과 리턴
						zipRtnList = (List<String>) unZipRsltMap.get("UnzipResult");

						int fileCount = unZipRsltMap.getInt("FileCount");
						if (fileCount > maxFileCnt) {
							throw new AppException("zip파일 내 이미지파일이 제한 갯수를 넘어갑니다.");
						}

						zipRtnCnt = zipRtnList.size();

						if (zipRtnCnt > 0) { // 규격에 맞지 않는 파일이 있으면 압축파일 삭제 및 메시지 생성
							for (File delfile : dirPath.listFiles()) { // 파일(jpg, zip) 삭제
								if (delfile.isFile()) {
									delfile.delete();
								}
							}

							for (int i = 0; i < zipRtnCnt; i++) {
								zipRtnStr = zipRtnList.get(i);
								zipRtnArr = zipRtnStr.split("\\|");

								if (!"fail".equals(zipRtnArr[0])) {
									zipErrCtn = Integer.parseInt(zipRtnArr[0]);
									if (zipErrCtn > 0) {
										errMsgList.add(zipRtnArr[1] + " : " + zipRtnArr[2]);
									}
								} /* else {
									errMsgList.add("이미지 압축파일을 다시 확인 하시기 바랍니다.");
									rtnMap.put("upCnt", 1);
									rtnMap.put("sucCnt", 0);
									}*/
							}
							rtnMap.put("errMsg", errMsgList);
							rtnMap.put("errCnt", zipRtnCnt);
						}
					}
					//mpFileCnt++;
				} // while
					//logger.info("MultipartFile mpFileCnt : " + mpFileCnt);

				if (zipRtnCnt == 0) {
					if ("1".equals(imgType)) {
						rtnMap = batchProductImgZipToMain(request, prodImgUploadPath);
						//logger.info("1 rtnMap.toString() : " + rtnMap.toString());
					} else if ("2".equals(imgType)) {
						rtnMap = batchProductImgZipToDetail(request, prodImgUploadPath);
						//logger.info("2 rtnMap.toString() : " + rtnMap.toString());
					}
				} else {
					rtnMap.put("sucFlag", "FAIL");
				}
			}
		} catch (Exception e) {
			errMsgList.add(e.getMessage());
			rtnMap.put("errMsg", errMsgList);
			rtnMap.put("sucFlag", "FAIL");
			zipRtnCnt = -999;
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.error(sw.toString());
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e) {
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					logger.error("batchProductImgZip (Exception) : " + sw.toString());
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) {
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					logger.error("batchProductImgZip (Exception) : " + sw.toString());
				}
			}
			if (zipRtnCnt != 0) { // 압축파일 검증 중 오류사항 발생으로 수정처리 미실행시 삭제처리
				if (dirPath.isDirectory()) {
					// 처리 후 파일 삭제
					for (File delfile : dirPath.listFiles()) {
						if (delfile.isFile()) {
							delfile.delete();
						}
					}
					if (dirPath.listFiles().length == 0) {
						dirPath.delete();
					}
				}
			}
		}
		return rtnMap;
	}

	/**
	 * 이미지압축파일 to 대표이미지
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> batchProductImgZipToMain(HttpServletRequest request, String prodImgUploadPath) throws Exception {

		Map<String, Object> rtnMap = new HashMap<String, Object>();
		DataMap reqParamMap = new DataMap(request);
		//String imgType = reqParamMap.getString("imgType"); //  1: 대표, 2: 상세
		String vendorId = reqParamMap.getString("vendorId");

		List<String> prodCdList = (List<String>) reqParamMap.get("prodCd"); // 엑셀업로드 리스트 prodCd
		List<String> prodNmList = (List<String>) reqParamMap.get("prodNm"); // 엑셀업로드 리스트 prodNm
		List<String> imgSeqList = (List<String>) reqParamMap.get("imgSeq"); // 엑셀업로드 리스트 imgSeq
		List<String> imgNmList = (List<String>) reqParamMap.get("imgNm"); // 엑셀업로드 리스트 imgNm

		File dirPath = new File(prodImgUploadPath);

		int prodCdSize = 0;
		int sucCnt = 0;
		int errCnt = 0;
		List<String> errMsgList = new ArrayList<String>();

		try {
			// 엑셀업로드 리스트 기준으로 압축파일의 이미지를 확인한다.
			if (prodCdList != null && prodCdList.size() > 0 && imgSeqList != null && imgSeqList.size() > 0) {
				File filez = new File(prodImgUploadPath);
				prodCdSize = prodCdList.size();
				for (int i = 0; i < prodCdSize; i++) {
					//logger.info("prodCd:[" + prodCdList.get(i) + "], prodNm:[" + prodNmList.get(i) + "], imgSeq:[" + imgSeqList.get(i) + "], imgNm:[" + imgNmList.get(i) + "]");
					String prodCd = prodCdList.get(i);
					String imgSeq = imgSeqList.get(i);
					String imgNm = imgNmList.get(i);
					String seq = "";
					for (File imgFile : filez.listFiles()) {
						if (imgFile.isDirectory()) {
							continue;
						}
						if (!imgNm.equals(imgFile.getName())) {
							continue;
						}

						if (imgFile.isFile()) {

							FileInputStream fis = null;
							FileOutputStream fos = null;
							try {
								String fileName = null;
								File fileDeleted = null;
								boolean actResults = false;
								int actResult = 0;
								PSCPPRD0006VO bean = new PSCPPRD0006VO();

								bean.setProdCd(prodCd);
								bean.setItemCd("001");
								bean.setRegId(vendorId);
								bean.setRowCnt(imgSeq);

								DataMap selectMap = new DataMap();
								selectMap.put("prodCd", prodCd);
								selectMap.put("typeCd", "001");
								selectMap.put("rowCnt", imgSeq);
								selectMap.put("vendorId", vendorId);

								DataMap mdSrcmkCd = pscpprd0006Service.selectMdSrcmkCd(selectMap);
								if (mdSrcmkCd == null) {
									throw new AppException("상품코드: " + prodCd + ", 순번:" + imgSeq + " / 상품코드를 찾을 수 없습니다.");
								}

								int iCnt = pscpprd0005Service.selectPrdMdAprvMstCnt(selectMap);
								if (iCnt > 0) {
									Map<String, String> param = new HashMap<String, String>();
									param.put("prodCd", prodCd);
									param.put("typeCd", "001");
									param.put("rowCnt", imgSeq);
									PSCPPRD0005VO prdMdAprInfo = pscpprd0005Service.selectPrdMdAprvMst(param);
									seq = prdMdAprInfo.getSeq();
								} else {
									PSCPPRD0005VO bean2 = new PSCPPRD0005VO();
									bean2.setProdCd(bean.getProdCd());
									bean2.setRegId(bean.getRegId());
									bean2.setAprvCd("001");
									bean2.setTypeCd("001");
									bean2.setVendorId(vendorId);
									seq = pscpprd0005Service.insertPrdMdAprvMst(bean2);
								}

								bean.setSeq(seq);

								String imgName = mdSrcmkCd.getString("MD_SRCMK_CD");
								String uploadTempDir = makeSubFolderTemp(imgName);

								fileName = seq + "_" + imgSeq + ".jpg";
								fileDeleted = new File(uploadTempDir + "/" + fileName);

								if (fileDeleted.exists()) {
									actResults = fileDeleted.delete();
									if (!actResults) {
										throw new AppException("상품코드: " + prodCd + ", 순번:" + imgSeq + " / 대표이미지 수정(1) 작업중 오류가 발생하였습니다.");
									}
								}

								fis = new FileInputStream(imgFile);
								String newFileSource = uploadTempDir + "/" + seq + "_" + imgSeq + ".jpg";
								fos = new FileOutputStream(newFileSource);
								actResult = FileCopyUtils.copy(fis, fos);
								if (actResult == 0) {
									throw new AppException("상품코드: " + prodCd + ", 순번:" + imgSeq + " / 대표이미지 수정(2) 작업중 오류가 발생하였습니다.");
								}

								try {
									int resultCnt = pscpprd0006Service.updatePrdImageHist(bean); // 수정이력
								} catch (Exception e) {
									StringWriter sw = new StringWriter();
									e.printStackTrace(new PrintWriter(sw));
									logger.error(sw.toString());
									throw new SQLException("상품코드: " + prodCd + ", 순번:" + imgSeq + " / 대표이미지 수정요청 등록중 오류가 발생하였습니다.");
								}

								sucCnt++;
							} catch (Exception e) {
								errCnt++;
								errMsgList.add(e.getMessage());
								StringWriter sw = new StringWriter();
								e.printStackTrace(new PrintWriter(sw));
								logger.error(sw.toString());
							} finally {
								if (fos != null) {
									try {
										fos.close();
									} catch (Exception e) {
										logger.error("batchProductImgZip (Exception) : " + e.getMessage());
									}
								}
								if (fis != null) {
									try {
										fis.close();
									} catch (Exception e) {
										logger.error("batchProductImgZip (Exception) : " + e.getMessage());
									}
								}
							}

						}
					}

				}
			}
			rtnMap.put("sucFlag", "SUCC");
		} catch (IllegalArgumentException e) {
			rtnMap.put("sucFlag", "FAIL");
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.error(sw.toString());
		} catch (Exception e) {
			rtnMap.put("sucFlag", "FAIL");
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.error(sw.toString());
		} finally {
			if (dirPath.isDirectory()) {
				// 처리 후 파일 삭제
				for (File delfile : dirPath.listFiles()) {
					if (delfile.isFile()) {
						delfile.delete();
					}
				}
				if (dirPath.listFiles().length == 0) {
					dirPath.delete();
				}
			}
			rtnMap.put("upCnt", prodCdSize);
			rtnMap.put("sucCnt", sucCnt);
			rtnMap.put("errCnt", errCnt);
			rtnMap.put("errMsg", errMsgList);
		}

		return rtnMap;
	}

	/**
	 * 이미지압축파일 to 상세이미지
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> batchProductImgZipToDetail(HttpServletRequest request, String prodImgUploadPath) throws Exception {

		Map<String, Object> rtnMap = new HashMap<String, Object>();
		DataMap reqParamMap = new DataMap(request);
		//String imgType = reqParamMap.getString("imgType"); //  1: 대표, 2: 상세
		String vendorId = reqParamMap.getString("vendorId");

		List<String> prodCdList = (List<String>) reqParamMap.get("prodCd"); // 엑셀업로드 리스트 prodCd
		List<String> prodNmList = (List<String>) reqParamMap.get("prodNm"); // 엑셀업로드 리스트 prodNm
		List<String> imgSeqList = (List<String>) reqParamMap.get("imgSeq"); // 엑셀업로드 리스트 imgSeq
		List<String> imgNmList = (List<String>) reqParamMap.get("imgNm"); // 엑셀업로드 리스트 imgNm

		File dirPath = new File(prodImgUploadPath);

		int prodCdSize = 0;
		int sucCnt = 0;
		int errCnt = 0;
		List<String> errMsgList = new ArrayList<String>();
		try {
			// 엑셀업로드 리스트 기준으로 압축파일의 이미지를 확인한다.
			if (prodCdList != null && prodCdList.size() > 0 && imgSeqList != null && imgSeqList.size() > 0) {
				File filez = new File(prodImgUploadPath);
				List<String> imgUrlList = new ArrayList<String>();
				prodCdSize = prodCdList.size();
				for (int i = 0; i < prodCdSize; i++) {
					//logger.info("prodCd:[" + prodCdList.get(i) + "], prodNm:[" + prodNmList.get(i) + "], imgSeq:[" + imgSeqList.get(i) + "], imgNm:[" + imgNmList.get(i) + "]");

					String prodCd = prodCdList.get(i);
					String imgSeq = imgSeqList.get(i);
					String imgNm = imgNmList.get(i);
					String seq = "";
					String nxProdCd = "";
					for (File imgFile : filez.listFiles()) {
						if (imgFile.isDirectory()) {
							continue;
						}
						if (!imgNm.equals(imgFile.getName())) {
							continue;
						}

						if (imgFile.isFile()) {
							FileInputStream fis = null;
							FileOutputStream fos = null;
							try {
								DataMap selectMap = new DataMap();
								selectMap.put("prodCd", prodCd);
								selectMap.put("vendorId", vendorId);

								DataMap mdSrcmkCd = pscpprd0006Service.selectMdSrcmkCd(selectMap);
								if (mdSrcmkCd == null) {
									throw new AppException("상품코드: " + prodCd + ", 순번:" + imgSeq + " / 상품코드를 찾을 수 없습니다.");
								}

								//boolean actResults = false;
								int actResult = 0;
								//edi.namoeditor.file.path + images
								String uploadDetailDir = makeDetailFolder(config.getString("edi.namoeditor.file.path"));
								String newFileSource = uploadDetailDir + "/" + imgFile.getName();
								//logger.info("newFileSource ::: " + newFileSource);
								fis = new FileInputStream(imgFile);
								fos = new FileOutputStream(newFileSource);
								actResult = FileCopyUtils.copy(fis, fos);
								if (actResult == 0) {
									throw new AppException("상품코드: " + prodCd + ", 순번:" + imgSeq + " / 상세이미지 작업중 오류가 발생하였습니다.");
								}

								if (i < prodCdSize - 1) {
									nxProdCd = prodCdList.get(i + 1);
								}

								setImgTag(imgUrlList, getDetailUrl(newFileSource)); // 상세이미지URL
								if (!prodCd.equals(nxProdCd) || i == prodCdSize - 1) { // 다음것과 다른 상품코드인경우 || 마지막번째 상품코드
									PSCPPRD0005VO bean = new PSCPPRD0005VO();
									bean.setProdCd(prodCd);
									bean.setSeq(seq);
									bean.setRegId(vendorId);
									bean.setAprvCd("001");
									bean.setTypeCd("002");
									bean.setVendorId(vendorId);
									bean.setAddDesc(productDescrHtml(imgUrlList));

									DataMap map = new DataMap();
									map.put("prodCd", prodCd);
									map.put("typeCd", "002");
									String resultStr = "";
									try {
										int resultCnt = 0;
										int iCnt = pscpprd0005Service.selectPrdMdAprvMstCnt(map);
										if (iCnt > 0) {
											Map<String, String> param = new HashMap<String, String>();
											param.put("prodCd", prodCd);
											param.put("typeCd", "002");
											PSCPPRD0005VO prdMdAprInfo = pscpprd0005Service.selectPrdMdAprvMst(param);
											bean.setSeq(prdMdAprInfo.getSeq());
											resultCnt = pscpprd0005Service.updatePrdDescriptionHist(bean);
										} else {
											bean.setTitle("상품정보");
											resultCnt = pscpprd0005Service.insertPrdDescriptionHist(bean);
										}

										if (resultCnt > 0) {
											resultStr = messageSource.getMessage("msg.common.success.insert", null, Locale.getDefault());
										} else {
											resultStr = messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault());
										}
									} catch (Exception e) {
										StringWriter sw = new StringWriter();
										e.printStackTrace(new PrintWriter(sw));
										logger.error(sw.toString());
										throw new SQLException("상품코드: " + prodCd + ", 순번:" + imgSeq + "/ 상세이미지 수정요청 등록중 오류가 발생하였습니다.");
									}
									imgUrlList.clear(); // 다음 상품코드 이미지 URL생성을 위해 Clear
								}
								sucCnt++;
							} catch (Exception e) {
								errCnt++;
								errMsgList.add(e.getMessage());
								StringWriter sw = new StringWriter();
								e.printStackTrace(new PrintWriter(sw));
								logger.error(sw.toString());
							} finally {
								if (fos != null) {
									try {
										fos.close();
									} catch (Exception e) {
										logger.error("batchProductImgZip (Exception) : " + e.getMessage());
									}
								}
								if (fis != null) {
									try {
										fis.close();
									} catch (Exception e) {
										logger.error("batchProductImgZip (Exception) : " + e.getMessage());
									}
								}
							}
						}
					}
				}
			}
			rtnMap.put("sucFlag", "SUCC");
		} catch (IllegalArgumentException e) {
			rtnMap.put("sucFlag", "FAIL");
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.error(sw.toString());
		} catch (Exception e) {
			rtnMap.put("sucFlag", "FAIL");
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.error(sw.toString());
		} finally {
			if (dirPath.isDirectory()) {
				// 처리 후 파일 삭제
				for (File delfile : dirPath.listFiles()) {
					if (delfile.isFile()) {
						delfile.delete();
					}
				}
				if (dirPath.listFiles().length == 0) {
					dirPath.delete();
				}
			}
			rtnMap.put("upCnt", prodCdSize);
			rtnMap.put("sucCnt", sucCnt);
			rtnMap.put("errCnt", errCnt);
			rtnMap.put("errMsg", errMsgList);
		}
		return rtnMap;
	}

	private void setImgTag(List<String> imgUrlList, String imgUrl) throws Exception {
		imgUrlList.add(imgUrl);
	}

	private String productDescrHtml(List<String> imgUrlList) throws Exception {
		StringBuffer sb = new StringBuffer();

		sb.append("<html xml:lang=\"ko\" lang=\"ko\">");
		sb.append("<head>");
		sb.append("<title>제목없음</title>");
		sb.append("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">");
		sb.append("<meta http-equiv=\"Content-type\" content=\"text/html; charset=utf-8\">");
		sb.append("<style type=\"text/css\"> body{font-family :굴림; color:#000000; font-size:10pt; margin:0 0 0 3px;} p,li{line-height:1.2; margin-top:0; margin-bottom:0;}");
		sb.append("</style>");
		sb.append("</head>");
		sb.append("<body>");
		if (imgUrlList != null && imgUrlList.size() > 0) {
			for (int i = 0; i < imgUrlList.size(); i++) {
				sb.append("<p><img title=\"\" style=\"border:0px solid rgb(0,0,0); border-image:none; vertical-align: baseline;\" alt=\"\" src=\"");
				sb.append(imgUrlList.get(i));
				sb.append("\"></p>");
			}
		}
		sb.append("</body>");
		sb.append("</html>");
		return sb.toString();
	}

	private DataMap unzip(File zipFile, File targetDir, String imgType) throws Exception {

		DataMap unZipRsltMap = new DataMap();
		List<String> unZipResult = new ArrayList<String>();

		FileInputStream fis = null;
		ZipInputStream zis = null;
		ZipEntry zentry = null;
		boolean unNext = true;
		int fileCount = 0;

		int limitSize = 1024 * 600; // 대표이미지 최대용량 600KB
		int minSize = 500; // 대표이미지 Width/Height Pixel Size Min
		int maxSize = 1500; // 대표이미지 Width/Height Pixel Size Max
		int detailLimitSize = 5242880; // 상세이미지 최대용량 5MB (나모에디터 기준과 동일)
		int zenErrCnt = 0;
		String msgWrn = "";

		try {
			fis = new FileInputStream(zipFile); // FileInputStream
			zis = new ZipInputStream(fis); // ZipInputStream

			while (unNext) {
				zentry = zis.getNextEntry();
				if (zentry != null) {
					String fileNameToUnzip = zentry.getName();
					File targetFile = new File(targetDir, fileNameToUnzip);

					unzipEntry(zis, targetFile);

					if (targetFile.exists()) {
						fileCount++;

						long iFileSize = targetFile.length();
						try {
							/*String typeChk = new MimetypesFileTypeMap().getContentType(targetFile.getName());
							if (typeChk.indexOf("image") == -1) {
								msgWrn = targetFile.getName() + " - 이미지 파일이 아닙니다. ";
							}*/
							if ("1".equals(imgType)) { // 대표이미지에 대해서만 용량 사이즈체크
								BufferedImage image = ImageIO.read(targetFile);
								Integer width = image.getWidth();
								Integer height = image.getHeight();

								if (iFileSize > limitSize) {
									msgWrn = "600KB이하의 이미지만 업로드 가능합니다.";
									// logger.info("limitSize message --> " + msgWrn);
								} else if ((width - height != 0) || (width < minSize || height < minSize)
										|| (width > maxSize || height > maxSize)) {
									msgWrn = "사이즈는 정사이즈 비율 500px 이상 1500px 이하입니다.";
									// logger.info("imageSize message --> " + msgWrn);
								}
							} else if ("2".equals(imgType)) { // 상세이미지 기준은 ImageUpload.jsp - int maxSize = 5242880; 를 참고
								if (iFileSize > detailLimitSize) {
									msgWrn = "5MB이하의 이미지만 업로드 가능합니다.";
								}
							} else { // imgType 에  1, 2 외 값  입력될경우
								msgWrn = "유효한 이미지 타입이 아닙니다.";
							}
						} catch (IOException e) {
							msgWrn = "유효한 이미지파일이 아닙니다.";
						} catch (Exception e) {
							msgWrn = "유효한 이미지파일이 아닙니다.";
						}

						if (!"".equals(msgWrn)) {
							zenErrCnt++;
							unZipResult.add(Integer.toString(zenErrCnt) + "|" + zentry.getName() + "|" + msgWrn);
							logger.error("unzip ::: " + Integer.toString(zenErrCnt) + "|" + zentry.getName() + "|" + msgWrn);
							msgWrn = "";
						}
					}

				} else {
					unNext = false;
					break;
				}
			}

		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.error("error message --> " + sw.toString());
			unZipResult.add("fail");
		} finally {
			if (zis != null) {
				zis.close();
			}
			if (fis != null) {
				fis.close();
			}
			unZipRsltMap.put("FileCount", fileCount);
			unZipRsltMap.put("UnzipResult", unZipResult);
		}
		logger.info("ZIP FILE COUNT ::: " + fileCount);

		return unZipRsltMap;
	}

	protected static File unzipEntry(ZipInputStream zis, File targetFile) throws Exception {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(targetFile);

			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			boolean proc = true;

			while (proc) {
				len = zis.read(buffer);
				if (len != -1) {
					fos.write(buffer, 0, len);
				} else {
					proc = false;
					break;
				}
			}
		} finally {
			if (fos != null) {
				fos.close();
			}
		}
		return targetFile;
	}

}
