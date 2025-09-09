package com.lottemart.epc.edi.product.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.activation.MimetypesFileTypeMap;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lcnjf.util.StringUtil;
import com.lottemart.common.exception.AlertException;
import com.lottemart.common.file.service.FileMngService;
import com.lottemart.common.file.service.ImageFileMngService;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.util.SecureUtil;
import com.lottemart.epc.edi.comm.controller.BaseController;
//import com.lottemart.epc.edi.comm.service.PEDPCOM0001Service;
import com.lottemart.epc.edi.comm.taglibs.dao.CustomTagDao;
import com.lottemart.epc.edi.product.model.NewProduct;
import com.lottemart.epc.edi.product.model.PEDMPRO0011VO;
import com.lottemart.epc.edi.product.service.CommonProductService;
import com.lottemart.epc.edi.product.service.NEDMPRO0020Service;
import com.lottemart.epc.edi.product.service.NEDMPRO0030Service;
import com.lottemart.epc.edi.product.service.NEDMPRO0060Service;
import com.lottemart.epc.edi.product.service.PEDMPRO000Service;

import lcn.module.common.util.DateUtil;
import lcn.module.common.util.HashBox;
import lcn.module.framework.property.ConfigurationService;
import net.sf.json.JSONObject;

/**
 * @Class Name : NEDMPRO0060Controller
 * @Description : 상품일괄등록 Controller 클래스
 * @Modification Information
 *
 * << 개정이력(Modification Information) >>
 *
 * 수정일 수정자 수정내용 ------- -------- ---------------------------
 * 2016.05.17 projectBOS32 신규생성
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved. </pre>
 */
@Controller
public class NEDMPRO0060Controller extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0060Controller.class);

	@Autowired
	private ConfigurationService config;

	@Resource(name = "commonProductService")
	private CommonProductService commonProductService;

	@Resource(name = "nEDMPRO0060Service")
	private NEDMPRO0060Service nEDMPRO0060Service;

	@Resource(name="nEDMPRO0020Service")
	private NEDMPRO0020Service nEDMPRO0020Service;

	@Resource(name="imageFileMngService")
	private ImageFileMngService imageFileMngService;

	@Autowired
	private PEDMPRO000Service ediProductService;

	@Autowired
	private FileMngService fileMngService;

	@Resource(name = "customTagDao")
	private CustomTagDao customTagDao;

	private static final int BUFFER_SIZE = 1024 * 2;

	//20180904 상품키워드 입력 기능 추가
	@Autowired
	private PEDMPRO000Service pEDMPRO000Service;
	//20180904 상품키워드 입력 기능 추가

	@Resource(name = "nEDMPRO0030Service")
	private NEDMPRO0030Service nEDMPRO0030Service;

//	// 공통. 브랜드, 팀분류 코드 조회용 서비스
//	private PEDPCOM0001Service commService;
	/**
	 * 상품일괄등록 LIST 화면 FORM 호출
	 *
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/product/NEDMPRO0060.do")
	public String selectImsiProductFormInit(ModelMap model, HttpServletRequest request) throws Exception {

		if (model == null || request == null) {
			throw new IllegalArgumentException();
		}

		Map<String, Object> paramMap = new HashMap<String, Object>();

		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		model.addAttribute("epcLoginVO", epcLoginVO);
		model.addAttribute("srchStartDt", DateUtil.getToday("yyyy-MM-dd")); // 검색시작일
		model.addAttribute("srchEndDt", DateUtil.formatDate(DateUtil.addDay(DateUtil.getToday("yyyy-MM-dd"), 1), "-")); // 검색종료일
		model.addAttribute("venCds", epcLoginVO.getVendorId()); // 협력업체 코드리스트
		model.addAttribute("teamList", commonProductService.selectNteamList(paramMap, request)); // 팀리스트

		return "edi/product/NEDMPRO0060";
	}

	/**
	 * 상품일괄등록 팝업1 화면 FORM 호출
	 *
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/product/NEDMPRO0061.do")
	public String batchProductViewPopup1(ModelMap model, HttpServletRequest request) throws Exception {
		return "edi/product/NEDMPRO0061";
	}

	/**
	 * 상품일괄등록 팝업2 화면 FORM 호출
	 *
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/product/NEDMPRO0062.do")
	public String batchProductViewPopup2(ModelMap model,
			HttpServletRequest request) throws Exception {
		return "edi/product/NEDMPRO0062";
	}

	/**
	 * 상품일괄등록 LIST 조회
	 *
	 * @Description : 상품일괄등록 LIST 조회
	 * @Method Name : prodComponentSearch
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/edi/product/imsiProductSearch.do")
	public @ResponseBody Map imsiProductSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {

		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		Map rtnMap = new HashMap<String, Object>();

		try {
			DataMap paramMap = new DataMap(request);

			paramMap.put("srchStartDt", paramMap.getString("srchStartDt").replaceAll("-", ""));
			paramMap.put("srchEndDt", paramMap.getString("srchEndDt").replaceAll("-", ""));
			paramMap.put("venCds", epcLoginVO.getVendorId());

			String currentPage = paramMap.getString("currentPage");

			// 데이터 조회
			List<DataMap> list = nEDMPRO0060Service.selectImsiProduct(paramMap);

			rtnMap = JsonUtils.convertList2Json((List) list, -1, currentPage);

			rtnMap.put("result", true);
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}

	/**
	 * 일괄업로드 양식
	 *
	 * @param request
	 * @param reuqest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/batchProductExcelDown.do")
	public void batchProductExcelDown(HttpServletRequest request, HttpServletResponse response) throws Exception {

		DataMap paramMap = new DataMap(request);

		response.setContentType("application/x-msdownload;charset=UTF-8");

		String optionVal = request.getParameter("optionVal");

		String userAgent = request.getHeader("User-Agent");
		String fileNmVal = URLDecoder.decode(request.getParameter("fileName"), "UTF-8");
		String fileName = fileNmVal + "_양식.xls";

		fileName = URLEncoder.encode(fileName, "UTF-8");

		if (userAgent.indexOf("MSIE 5.5") > -1) {
			response.setHeader("Content-Disposition", "filename=" + fileName + ";");
		} else {
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ";");
		}

		// StringBuffer sb = new StringBuffer();

		String[] newHeaders = new String[1];
		int headerHeight = 0;

		if ("01".equals(optionVal) || "02".equals(optionVal)) { // 상품
			String[] headers = { "* 기준 순번", "* 상품속성", "* 팀코드", "* 대분류", "* 중분류",
					"* 소분류", "* 카테고리아이디", "* EC 표준카테고리 아이디", " *EC 전시카테고리 아이디",
					"* 정상매가", "* 통화단위",	"표시단위", "표시기준수량", "상품규격", "표시총량",
					"* 온라인 대표상품코드", "* 상품명",
					"상품사이즈(가로)", "상품사이즈(세로)", "상품사이즈(높이)", "상품사이즈단위",
					"* 계절구분(연도)", "* 계절구분(계절)", "* 원산지", "* 최소주문가능량",
					"* 최대주문가능량", "협력사 내부 상품코드", "* 상품유형", "예약주문가능일(시작)",
					"예약주분가능일(종료)", "예약상품 출고지시일", "희망배송가능일",
					"세트수량", "세트수량 TEXT", "친환경인증여부", "친환경인증분류명", "발행일",
					"가격 발급구분코드", "수량/중량구분", "모델명", "브랜드", "메이커",
					"* 판매(88)/내부 유효성검사용", "* 전상법 분류코드", "* KC인증 분류코드", "* 단품정보",
					"체험형 상품여부", "판매자 추천평", "성인상품여부(Y/N)" };
			newHeaders = headers;
			headerHeight = 3300;
		} else if ("03".equals(optionVal) || "04".equals(optionVal)) { // 전상법, KC인증
			String[] headers = { "상품명", "정보컬럼명", "* 컬럼값" };
			newHeaders = headers;
			headerHeight = 1000;
		} else if ("05".equals(optionVal) || "06".equals(optionVal)) { // 상세이미지, 대표이미지
			String title = "대표";
			if ("05".equals(optionVal)) {
				title = "상세";
			}
			String[] headers = { "* 상품코드", "상품명", "* " + title + "이미지파일명" };
			newHeaders = headers;
			headerHeight = 1500;
		}

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

		String infoVal = "※ * 표시된 항목은 필수 입력입니다. 미입력시 일괄등록이 되지 않으니 유의하시기 바랍니다.\n"
				+ "※ 셀서식 표시형식은 텍스트로 지정 후 입력 하세요.\n"
				+ "※ 거래유형이 위수탁만 등록이 가능합니다.";
		if ("01".equals(optionVal) || "02".equals(optionVal)) {
			infoVal += "\n"
					   + "※ 판매(88)/내부 유효성검사용 항목은 단품구성 상품만 기재 필요, 기획/복합 상품이나 바코드 없는 상품은 '1111111111111' 로 기재하십시오.\n"
					   + "    롯데마트 매장상품 중복 여부 체크용 바코드(88) 기재란, 숫자만 입력 가능!\n\n"
					   + "==== 등록방식 요약 ====\n"
					   + "1. 기준순번 - 순서대로 sequence 번호 입력\n"
					   + "2. 상품유형 - 상품유형 주문제작(02),유료설치상품(03),무료설치상품(13)일 경우 희망배송가능일, 골라담기상품(04)일 경우 세트수량, 세트수량TEXT\n"
					   + "                        예약상품(05)일 경우 예약주문가능일, 예약상품 출고지시일 항목은 필수 입니다.\n"
					   + "                        - 유료설치상품 선택 시 설치비가 있는 상품 , 무료설치상품 선택 시 설치비가 없는 상품\n"
					   //+ "3. 코드값 설명은 코드값 참고 시트에서 확인.\n"

						//20180904 상품키워드 입력 기능 추가
					    + "3. 코드값 설명은 코드값 참고, 전상법 분류코드, KC인증 분류코드 시트에서 확인.\n"
						+ "4. 기본정보 기준순번과 일치하는 상품키워드 기준순번 단위로 적용 되고 검색어는 한글기준 13글자, 영문/숫자 기준 39글자 까지만 적용됨.";
					   	//20180904 상품키워드 입력 기능 추가

			String val1 = "※ 입력 포멧 : YYYYMMDDHH";
			String val2 = "※ 입력 포멧: YYYYMMDD";
			String ecCategoryInfoVal = "※ [EC 카테고리별 속성 조회]에서\n"
							+ "카테고리아이디 확인 후 코드 입력\n"
							+ "롯데ON, 롯데마트몰 1개이상 필수\n"
							+ "입력시 쉼표(,) 단위로 구분\n"
							+ "ex) EC00000000,LM00000000";

			header1.createCell(8).setCellValue(ecCategoryInfoVal);
			header1.createCell(28).setCellValue(val1);
			header1.createCell(29).setCellValue(val1);
			header1.createCell(30).setCellValue(val2);
			header1.getCell(8).setCellStyle(styleHd2);
			header1.getCell(28).setCellStyle(styleHd2);
			header1.getCell(29).setCellStyle(styleHd2);
			header1.getCell(30).setCellStyle(styleHd2);

			if ("01".equals(optionVal)) {
				infoVal += "\n"
						+ "5. 기본정보 기준순번과 일치하는 단품속성 기준순번 단위로 적용 되고, [EC 카테고리별 속성 조회]메뉴에서 표준카테고리기준 상품속성값 확인 후 코드값으로 입력.";
			} else if ("02".equals(optionVal)) {
				infoVal += "\n"
						+ "5. 기본정보 기준순번과 일치하는 단품옵션 기준순번 단위로 적용 됨. \n"
						+ "6. 기본정보 기준순번, 단품옵션 단품코드와 일치하는 단품속성 기준순번, 단품코드 단위로 적용 되고, [EC 카테고리별 속성 조회]메뉴에서 표준카테고리기준 상품속성값 확인 후 코드값으로 입력.";
			}
		} else if ("05".equals(optionVal) || "06".equals(optionVal)) {
			infoVal += "\n"
					+ "※ 이미지 파일명은 확장자까지 입력 하세요.\n"
					+ "※ 업로드할 이미지 파일은 zip 파일로 전체 압축 후 업로드 하세요.";
		}

		header1.createCell(0).setCellValue(infoVal);
		header1.getCell(0).setCellStyle(styleHd2);
		header1.setHeight((short) headerHeight);

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

		if ("01".equals(optionVal) || "02".equals(optionVal)) {

			// 20180904 상품키워드 입력 기능 추가
			HSSFSheet keywordSheet = workbook.createSheet("상품키워드");
			HSSFRow keywordHeader = keywordSheet.createRow(0);
			String[] keywordheaders = { "* 기준 순번", "* 검색어" };

			for (int i = 0; i < keywordheaders.length; i++) {
				cell = keywordHeader.createCell(i);
				cell.setCellValue(keywordheaders[i]);
				cell.setCellStyle(styleHd);
				keywordSheet.setColumnWidth(i, 4000);
			}
			// 20180904 상품키워드 입력 기능 추가

			// 단품 옵션
			if ("02".equals(optionVal)) {
				HSSFSheet optSheet = workbook.createSheet("단품 옵션");
				HSSFRow optHeader = optSheet.createRow(0);
				String[] optheaders = { "* 기준 순번", "* 단품코드", "* 재고여부", "* 재고수량", "가격" };

				for (int i = 0; i < optheaders.length; i++) {
					cell = optHeader.createCell(i);
					cell.setCellValue(optheaders[i]);
					cell.setCellStyle(styleHd);
					optSheet.setColumnWidth(i, 4000);
				}
			}

			HSSFSheet attrSheet = workbook.createSheet("단품 속성");
			
			String[] attrHeaders = null;
			if ("01".equals(optionVal)) {
				attrHeaders = new String[] { "* 기준 순번", "* 상품속성아이디", "* 상품속성값아이디", "* 상품속성값텍스트입력" };

				HSSFRow attrHeader = attrSheet.createRow(0);
				for (int i = 0; i < attrHeaders.length; i++) {
					cell = attrHeader.createCell(i);
					cell.setCellValue(attrHeaders[i]);
					cell.setCellStyle(styleHd);
					attrSheet.setColumnWidth(i, 5500);
				}

			} else if ("02".equals(optionVal)) {
				attrHeaders = new String[] { "* 기준 순번", "* 단품코드", "* 상품속성아이디", "* 상품속성값아이디", "* 상품속성값텍스트입력" };

				attrSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
				attrSheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 4));

				String itemOptInfoVal1 = "※옵션 상품일 경우, EC표준카테고리에 매핑된 상품속성유형 아이디와 상품 속성값 아이디 필수 입력하여야함\n"
						+ "※EC카테고리별 속성 조회 메뉴에서 상품 속성유형아이디와 상품 속성값아이디 확인 가능\n"
						+ "ex) EC표준카테고리가 '의류>여성의류>상의>블라우스'인 단품코드 001,002를 가지는 상품일 경우\n"
						+ "    속성유형 통합색상,여성 상의 사이즈 필수 입력 해야함\n" 
						+ "    -001:통합색상:블랙/성인상의 사이즈:44\n"
						+ "    -002:통합색상:화이트/성인상의 사이즈:55일 경우 아래 표와 같이 입력";
				HSSFRow attrHeader0 = attrSheet.createRow(0);
				attrHeader0.createCell(0).setCellValue(itemOptInfoVal1);
				attrHeader0.getCell(0).setCellStyle(styleHd2);
				attrHeader0.setHeight((short) 1500);

				HSSFRow attrHeader1 = attrSheet.createRow(1);
				for (int i = 0; i < attrHeaders.length; i++) {
					cell = attrHeader1.createCell(i);
					cell.setCellValue(attrHeaders[i]);
					cell.setCellStyle(styleHd);
					attrSheet.setColumnWidth(i, 5500);
				}

				HSSFRow attrHeader2 = attrSheet.createRow(2);
				cell = attrHeader2.createCell(0);
				cell.setCellValue("1");
				cell = attrHeader2.createCell(1);
				cell.setCellValue("001");
				cell = attrHeader2.createCell(2);
				cell.setCellValue("12438");
				cell = attrHeader2.createCell(3);
				cell.setCellValue("114794");
				attrHeader2.getCell(0).setCellStyle(styleHd3);
				attrHeader2.getCell(1).setCellStyle(styleHd3);
				attrHeader2.getCell(2).setCellStyle(styleHd3);
				attrHeader2.getCell(3).setCellStyle(styleHd3);

				HSSFRow attrHeader3 = attrSheet.createRow(3);
				cell = attrHeader3.createCell(0);
				cell.setCellValue("1");
				cell = attrHeader3.createCell(1);
				cell.setCellValue("001");
				cell = attrHeader3.createCell(2);
				cell.setCellValue("12441");
				cell = attrHeader3.createCell(3);
				cell.setCellValue("114886");
				attrHeader3.getCell(0).setCellStyle(styleHd3);
				attrHeader3.getCell(1).setCellStyle(styleHd3);
				attrHeader3.getCell(2).setCellStyle(styleHd3);
				attrHeader3.getCell(3).setCellStyle(styleHd3);

				HSSFRow attrHeader4 = attrSheet.createRow(4);
				cell = attrHeader4.createCell(0);
				cell.setCellValue("2");
				cell = attrHeader4.createCell(1);
				cell.setCellValue("002");
				cell = attrHeader4.createCell(2);
				cell.setCellValue("12438");
				cell = attrHeader4.createCell(3);
				cell.setCellValue("114835");
				attrHeader4.getCell(0).setCellStyle(styleHd3);
				attrHeader4.getCell(1).setCellStyle(styleHd3);
				attrHeader4.getCell(2).setCellStyle(styleHd3);
				attrHeader4.getCell(3).setCellStyle(styleHd3);

				HSSFRow attrHeader5 = attrSheet.createRow(5);
				cell = attrHeader5.createCell(0);
				cell.setCellValue("2");
				cell = attrHeader5.createCell(1);
				cell.setCellValue("002");
				cell = attrHeader5.createCell(2);
				cell.setCellValue("12441");
				cell = attrHeader5.createCell(3);
				cell.setCellValue("114888");
				attrHeader5.getCell(0).setCellStyle(styleHd3);
				attrHeader5.getCell(1).setCellStyle(styleHd3);
				attrHeader5.getCell(2).setCellStyle(styleHd3);
				attrHeader5.getCell(3).setCellStyle(styleHd3);

				String itemOptInfoVal2 = "※상품속성값 텍스트 입력 시에는 상품속성값아이디란은 비워두세요.\n"
						+ "※하나의 옵션이라도 상품속성값텍스트를 입력할 경우, 전체옵션을 텍스트로 입력해주세요.";
				HSSFRow attrHeader6 = attrSheet.createRow(6);
				cell = attrHeader6.createCell(0);
				cell.setCellValue(itemOptInfoVal2);
				attrHeader6.getCell(0).setCellStyle(styleHd2);
				attrHeader6.setHeight((short) 500);

				HSSFRow attrHeader7 = attrSheet.createRow(7);
				for (int i = 0; i < attrHeaders.length; i++) {
					cell = attrHeader7.createCell(i);
					cell.setCellValue(attrHeaders[i]);
					cell.setCellStyle(styleHd);
				}
			}

			HSSFSheet codeInfoSheet = workbook.createSheet("코드값 참고");
			HSSFRow codeInfoHeader = codeInfoSheet.createRow(0);
			String[] codeInfoheaders = { "상품속성", "통화단위", "표시단위", "상품사이즈단위", "계절구분(계절)", "원산지", "상품유형", "친환경인증여부",
					"친환경인증분류명", "가격 발급구분 코드", "수량/중량구분", "단품정보", "체험형 상품여부" };

			for (int i = 0; i < codeInfoheaders.length; i++) {
				cell = codeInfoHeader.createCell(i);
				cell.setCellValue(codeInfoheaders[i]);
				cell.setCellStyle(styleHd);
				codeInfoSheet.setColumnWidth(i, 6000);
			}

			HSSFRow codeInfoRow = codeInfoSheet.createRow(1);
			codeInfoRow.setHeight((short) 20000);

			for (int j = 0; j < codeInfoheaders.length; j++) {
				String cellVal = "";
				cell = codeInfoRow.createCell(j);

				if ("상품속성".equals(codeInfoheaders[j])) {
					cellVal = "1 : 규격\n" + "5 : 패션";
					cell.setCellValue(cellVal);
				}

				if ("통화단위".equals(codeInfoheaders[j])) {
					cell.setCellValue(tpcCode("PRD40"));
				}

				if ("표시단위".equals(codeInfoheaders[j])) {
					cell.setCellValue(tpcCode("PRD17"));
				}

				if ("상품사이즈단위".equals(codeInfoheaders[j])) {
					cell.setCellValue(tpcCode("PRD42"));
				}

				if ("계절구분(계절)".equals(codeInfoheaders[j])) {
					cellVal = "0001 : 봄\n" + "0002 : 봄/여름\n" + "0003 : 여름\n"
							+ "0004 : 가을\n" + "0005 : 가을/겨울\n" + "0006 : 겨울\n"
							+ "0007 : 사계절";
					cell.setCellValue(cellVal);
				}

				if ("원산지".equals(codeInfoheaders[j])) {
					cell.setCellValue(tpcCode("PRD16"));
				}

				if ("상품유형".equals(codeInfoheaders[j])) {
					cell.setCellValue(tpcCode("SM335"));
				}

				if ("친환경인증여부".equals(codeInfoheaders[j])) {
					cellVal = "0 : N\n" + "1 : Y";
					cell.setCellValue(cellVal);
				}

				if ("친환경인증분류명".equals(codeInfoheaders[j])) {
					cell.setCellValue(tpcCode("PRD08"));
				}

				if ("가격 발급구분 코드".equals(codeInfoheaders[j])) {
					cellVal = "0 : 비규격\n" + "1 : 규격\n" + "9 : 임직원제외";
					cell.setCellValue(cellVal);
				}

				if ("수량/중량구분".equals(codeInfoheaders[j])) {
					cellVal = "0 : 수량\n" + "1 : 중량";
					cell.setCellValue(cellVal);
				}

				if ("단품정보".equals(codeInfoheaders[j])) {
					cellVal = "0 : 옵션 별 동일가격\n" + "1 : 옵션 별 별도가격";
					cell.setCellValue(cellVal);
				}

				if ("체험형 상품여부".equals(codeInfoheaders[j])) {
					cellVal = "0 : 체험형 상품아님\n" + "1 : 체험형 상품";
					cell.setCellValue(cellVal);
				}
				cell.setCellStyle(styleRow);
				codeInfoSheet.setColumnWidth(j, 6000);
			}

			/* ************************** */
			// 20181002 상품키워드 입력 기능 추가
			HSSFSheet codeInfoSheet02 = workbook.createSheet("전상법 분류코드 참고");
			HSSFRow codeInfoHeader02 = codeInfoSheet02.createRow(0);
			String[] codeInfoheaders02 = { "팀코드", "팀명", "대분류코드", "대분류명", "전상법 분류코드", "전상법 분류명" };

			for (int i = 0; i < codeInfoheaders02.length; i++) {
				cell = codeInfoHeader02.createCell(i);
				cell.setCellValue(codeInfoheaders02[i]);
				cell.setCellStyle(styleHd);
				codeInfoSheet02.setColumnWidth(i, 6000);
			}

			List<DataMap> codeInfo02List = new ArrayList<DataMap>();

			codeInfo02List = nEDMPRO0060Service.selectCodeInfo02();

			for (int i = 1; i <= codeInfo02List.size(); i++) {
				DataMap mapInfo = codeInfo02List.get(i - 1);
				HSSFRow codeInfoRow02 = codeInfoSheet02.createRow(i);

				for (int j = 0; j < 6; j++) {
					cell = codeInfoRow02.createCell(j);

					if (j == 0) {
						cell.setCellValue(mapInfo.getString("TEAM_CD"));
					} else if (j == 1) {
						cell.setCellValue(mapInfo.getString("TEAM_NM"));
					} else if (j == 2) {
						cell.setCellValue(mapInfo.getString("L1_CD"));
					} else if (j == 3) {
						cell.setCellValue(mapInfo.getString("L1_NM"));
					} else if (j == 4) {
						cell.setCellValue(mapInfo.getString("INFO_GRP_CD"));
					} else if (j == 5) {
						cell.setCellValue(mapInfo.getString("INFO_GRP_NM"));
					}

					cell.setCellStyle(styleRow);
					codeInfoSheet02.setColumnWidth(j, 6000);
				}
			}

			/* ************************** */

			HSSFSheet codeInfoSheet03 = workbook.createSheet("KC인증 분류코드 참고");
			HSSFRow codeInfoHeader03 = codeInfoSheet03.createRow(0);
			String[] codeInfoheaders03 = { "팀코드", "팀명", "대분류코드", "대분류명", "KC인증 분류코드", "KC인증 분류명" };

			for (int i = 0; i < codeInfoheaders03.length; i++) {
				cell = codeInfoHeader03.createCell(i);
				cell.setCellValue(codeInfoheaders03[i]);
				cell.setCellStyle(styleHd);
				codeInfoSheet03.setColumnWidth(i, 6000);
			}

			List<DataMap> codeInfo03List = new ArrayList<DataMap>();

			codeInfo03List = nEDMPRO0060Service.selectCodeInfo03();

			for (int i = 1; i <= codeInfo03List.size(); i++) {
				DataMap mapInfo = codeInfo03List.get(i - 1);
				HSSFRow codeInfoRow03 = codeInfoSheet03.createRow(i);

				for (int j = 0; j < 6; j++) {
					cell = codeInfoRow03.createCell(j);

					if (j == 0) {
						cell.setCellValue(mapInfo.getString("TEAM_CD"));
					} else if (j == 1) {
						cell.setCellValue(mapInfo.getString("TEAM_NM"));
					} else if (j == 2) {
						cell.setCellValue(mapInfo.getString("L1_CD"));
					} else if (j == 3) {
						cell.setCellValue(mapInfo.getString("L1_NM"));
					} else if (j == 4) {
						cell.setCellValue(mapInfo.getString("INFO_GRP_CD"));
					} else if (j == 5) {
						cell.setCellValue(mapInfo.getString("INFO_GRP_NM"));
					}

					cell.setCellStyle(styleRow);
					codeInfoSheet03.setColumnWidth(j, 6000);
				}
			}
			// 20181002 상품키워드 입력 기능 추가
			/* ************************** */

		} else if ("03".equals(optionVal) || "04".equals(optionVal)) { // 전상법, KC인증
			String[] pgmIdArr = paramMap.getString("pgmIdVal").split(",");
			paramMap.put("pgmIdArr", pgmIdArr);

			List<DataMap> list = new ArrayList<DataMap>();

			if ("03".equals(optionVal)) {
				list = nEDMPRO0060Service.selectBatchNorProdCode(paramMap);
			} else {
				list = nEDMPRO0060Service.selectBatchKcProdCode(paramMap);
			}

			for (int i = 1; i <= list.size(); i++) {
				DataMap mapInfo = list.get(i - 1);
				HSSFRow rowAdd = sheet.createRow(1 + i);

				for (int j = 0; j < 6; j++) {
					cell = rowAdd.createCell(j);

					if (j == 0) {
						cell.setCellValue(mapInfo.getString("PROD_NM"));
					} else if (j == 1) {
						cell.setCellValue(mapInfo.getString("INFO_COL_NM"));
					} else if (j == 2) {
						cell.setCellValue(mapInfo.getString("COL_VAL"));
					} else if (j == 3) {
						cell.setCellValue(mapInfo.getString("PGM_ID"));
						sheet.setColumnWidth(j, 1);
					} else if (j == 4) {
						cell.setCellValue(mapInfo.getString("INFO_GRP_CD"));
						sheet.setColumnWidth(j, 1);
					} else if (j == 5) {
						cell.setCellValue(mapInfo.getString("INFO_COL_CD"));
						sheet.setColumnWidth(j, 1);
					}
				}
			}
		} else if ("05".equals(optionVal) || "06".equals(optionVal)) {
			String[] pgmIdArr = paramMap.getString("pgmIdVal").split(",");
			paramMap.put("pgmIdArr", pgmIdArr);

			List<DataMap> list = nEDMPRO0060Service.selectImsiProductNm(paramMap);

			for (int i = 1; i <= list.size(); i++) {
				DataMap mapInfo = list.get(i - 1);
				HSSFRow rowAdd = sheet.createRow(1 + i);

				for (int j = 0; j < 3; j++) {
					cell = rowAdd.createCell(j);

					if (j == 0) {
						cell.setCellValue(mapInfo.getString("PGM_ID"));
					} else if (j == 1) {
						cell.setCellValue(mapInfo.getString("PROD_NM"));
					} else if (j == 2) {
						cell.setCellValue(mapInfo.getString("IMG_NM"));
					}
				}
			}
		}

		DataFormat format = workbook.createDataFormat();
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setDataFormat(format.getFormat("@"));

		/* 셀(cols) 갯수 확인 */
		Row rows = sheet.getRow(1);
		int cellCnt = (int) rows.getLastCellNum();
		/* 셀(cols) 갯수 확인 */

		for (int j = 0; j < cellCnt; j++) {
			sheet.setDefaultColumnStyle(j, cellStyle);
		}

		ServletOutputStream out = response.getOutputStream();
		workbook.write(out);
		out.flush();
	}

	/**
	 * 엑셀일괄업로드
	 *
	 * @param request
	 * @param reuqest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/batchProductExcelUpload.do")
	public void batchProductExcelUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;

		DataMap paramMap = new DataMap(request);
		String optionVal = paramMap.getString("optionVal");
		String entpCd = paramMap.getString("entpCd");

		int upCnt = 0;

		Boolean isErr = false;
		List<String> keyNoErr= new ArrayList<String>();

		//20180904 상품키워드 입력 기능 추가
		int keywordUpCnt = 0;
		int keywordSucCnt = 0;
		int keywordErrCnt = 0;

		int optUpCnt = 0;
		int sucCnt = 0;
		int errCnt = 0;

		int optSucCnt = 0;
		int optErrCnt = 0;
		
		String msg1 = "";
		String msg2 = "";
		String msgKeyword = "";

		int attrUpCnt = 0;
		int attrSucCnt = 0;
		int attrErrCnt = 0;

		String msg3 = "";
		String msg4 = "";
		String msgWrn = "";

		List<DataMap> imgList = new ArrayList<DataMap>();

		//20180904 상품키워드 입력 기능 추가
		PEDMPRO0011VO pedmpro0011VO = new PEDMPRO0011VO();

		try {

			if ("01".equals(optionVal) || "02".equals(optionVal)) {  // 상품

				boolean chk = false;

				//20180904 상품키워드 입력 기능 추가
				boolean keywordChk = false;

				boolean optChk = false;
				boolean attrChk = false;

				String[] validationCol ={"KEY_NO","PROD_DIVN_CD","TEAM_CD","L1_CD","L2_CD","L3_CD","CATEGORY_ID","STD_CAT_CD","DISP_CAT_CD","NOR_PROD_SALE_PRC","NOR_PROD_SALE_CURR"
						                          ,"ONLINE_REP_PROD_CD","PROD_NM","SESN_YEAR_CD","SESN_DIVN_CD","HOME_CD","MIN_ORD_PSBT_QTY"
						                          ,"MAX_ORD_PSBT_QTY","ONLINE_PROD_TYPE_CD","NOR_PROD_INFO_GRP_CD","KC_INFO_GRP_CD","MD_VALI_SELL_CD","OPTN_PROD_PRC_MGR_YN"};

				String[] colNms = {"KEY_NO","PROD_DIVN_CD","TEAM_CD","L1_CD","L2_CD","L3_CD","CATEGORY_ID","STD_CAT_CD","DISP_CAT_CD","NOR_PROD_SALE_PRC","NOR_PROD_SALE_CURR","DP_UNIT_CD"
						                   ,"DP_BASE_QTY","PROD_STANDARD_NM","DP_TOT_QTY","ONLINE_REP_PROD_CD","PROD_NM","PROD_HRZN_LENG"
						                   ,"PROD_VTCL_LENG","PROD_HIGT","SIZE_UNIT","SESN_YEAR_CD","SESN_DIVN_CD","HOME_CD","MIN_ORD_PSBT_QTY","MAX_ORD_PSBT_QTY","ENTP_IN_PROD_CD"
						                   ,"ONLINE_PROD_TYPE_CD","RSERV_ORD_PSBT_START_DY","RSERV_ORD_PSBT_END_DY","RSERV_PROD_PICK_IDCT_DY","HOPE_DELI_PSBT_DD"
						                   ,"OPTN_LOAD_SET_QTY","OPTN_LOAD_CONTENT","ECO_YN","ECO_NM","PRODUCT_DY","PRC_ISSUE_DIVN_CD","QTY_WEGT_DIVN_CD","MODEL_NM","BRAND_NM"
						                   ,"MAKER_NM","MD_VALI_SELL_CD","NOR_PROD_INFO_GRP_CD","KC_INFO_GRP_CD","OPTN_PROD_PRC_MGR_YN","EXPR_PROD_YN", "SELLER_RECOMM","ADL_YN"};

				//20180904 상품키워드 입력 기능 추가
				String[] keywordColNms = {"KEY_NO","SEARCH_KYWRD"};

				String[] attrColNms = null;

				String keyNo = ""; //상품키워드 기준순번
				String prodKeyNo = "";//상품 기본정보 기준순번
				Map<String,Integer> keyNoMap = new HashMap<String,Integer>(); //상품키워드 오류 기준순번 담을 맵
				Map<String,Integer> keyNoChkMap = new HashMap<String,Integer>();//상품키워드 기준순번 체크 맵

				List<DataMap> mapList = fileMngService.readUploadNullColExcelFile(mptRequest, colNms, 2, 0);
				upCnt = mapList.size();

				//20180904 상품키워드 입력 기능 추가
				List<DataMap> keywordMapList = fileMngService.readUploadNullColExcelFile(mptRequest, keywordColNms, 1, 1);
				keywordUpCnt = keywordMapList.size();

				List<DataMap> optMapList = null;
				List<DataMap> attrMapList = null;
				String[] optColNms = { "KEY_NO", "ITEM_CD", "STK_MGR_YN", "RSERV_STK_QTY", "OPTN_AMT" };

				if ("01".equals(optionVal)) {
					attrColNms = new String[] { "KEY_NO", "ATTR_ID", "ATTR_VAL_ID", "ATTR_VAL" };
					attrMapList = fileMngService.readUploadNullColExcelFile(mptRequest, attrColNms, 1, 2);
				} else if ("02".equals(optionVal)) {
					attrColNms = new String[] { "KEY_NO", "ITEM_CD", "ATTR_ID", "ATTR_VAL_ID", "ATTR_VAL" };
					optMapList = fileMngService.readUploadNullColExcelFile(mptRequest, optColNms, 1, 2);
					attrMapList = fileMngService.readUploadNullColExcelFile(mptRequest, attrColNms, 8, 3);
				}

				if(optMapList != null){
					optUpCnt = optMapList.size();
				}
				if(attrMapList != null){
					attrUpCnt = attrMapList.size();
				}

				int stopTradCnt =  nEDMPRO0060Service.selectCountVendorStopTrading(entpCd); //협력업체의 거래 중지여부 조회
				NewProduct tmpProduct = nEDMPRO0060Service.selectNewProductTradeType(entpCd); //협력업체의 거래유형

				if(tmpProduct == null) {
					tmpProduct= new NewProduct();
					tmpProduct.setTradeTypeCode("1");
				}

				if (stopTradCnt == 0) {
					isErr = true;
					msgWrn = "- 거래가 중지된 협력업체 입니다.";
					//errCnt = upCnt;
				} else if (!"6".equals(tmpProduct.getTradeTypeCode())) {
					isErr = true;
					msgWrn = "- 거래유형 : 위수탁이 아닙니다. ";
					//errCnt = upCnt;
				} else {

					for (int i = 0; i < mapList.size(); i++) {
						DataMap info = mapList.get(i);
						info.put("entpCd", entpCd);

						for (int j = 0; j < validationCol.length; j++) {
							if ("".equals(info.getString(validationCol[j]))) {
								chk = true;
								msg1 = "- 기본정보 필수입력 값 누락<br/>";
							}
						}

						if (!"".equals(info.getString("PROD_DIVN_CD"))) {
							String prodDivnCd = info.getString("PROD_DIVN_CD");
							if (StringUtil.getByteLength(prodDivnCd) > 1) { // 자릿수 초과
								chk = true;
								msg2 += "- 기본정보 " + (i + 1) + "번째 행 상품속성은 1 또는 5로 입력해주세요.(1:규격, 5:패션)<br/>";
							} else if ("1:5".indexOf(prodDivnCd) < 0) { // 입력값 이상
								msg2 += "- 기본정보 " + (i + 1) + "번째 행 상품속성은 1 또는 5로 입력해주세요.(1:규격, 5:패션)<br/>";
							}
						}

						if (!"".equals(info.getString("L1_CD"))) {
							String l1cd = info.getString("L1_CD");
							if (StringUtil.getByteLength(l1cd) != 3) { // 자릿수 체크
								chk = true;
								msg2 += "- 기본정보 " + (i + 1) + "번째 행 대분류 코드값은 3자리입니다.<br/>";
							}
						}

						if (!"".equals(info.getString("L2_CD"))) {
							String l2cd = info.getString("L2_CD");
							if (StringUtil.getByteLength(l2cd) != 6) { // 자릿수 체크
								chk = true;
								msg2 += "- 기본정보 " + (i + 1) + "번째 행 중분류 코드값은 6자리입니다.<br/>";
							}
						}

						if (!"".equals(info.getString("L3_CD"))) {
							String l3cd = info.getString("L3_CD");
							if (StringUtil.getByteLength(l3cd) != 9) { // 자릿수 체크
								chk = true;
								msg2 += "- 기본정보 " + (i + 1) + "번째 행 소분류 코드값은 9자리입니다.<br/>";
							}
						}

						if (!"".equals(info.getString("CATEGORY_ID"))) {
							/* 20190619 카테고리 중복 체크 */
							String[] catArr = info.getString("CATEGORY_ID").split(",");

							int catCnt = catArr.length;
							int chkCnt = categoryOverLapChk(catArr);

							if (catCnt != chkCnt) {
								chk = true;
								msg2 += "- 기본정보 " + (i + 1) + "번째 행 카테고리 코드는 중복 등록이 불가합니다.<br/>";
							}
						}

						DataMap onlineRepinfo = nEDMPRO0060Service.selectOnlineRepresentProdctInfo(info); //온라인 대표상품코드 조회

						if (onlineRepinfo == null) {
							chk = true;
							msg2 += "- 기본정보 " + (i + 1) + "번째 행 온라인 대표상품 코드가 존재하지 않습니다.<br/>";
						}

						if ("02".equals(info.getString("ONLINE_PROD_TYPE_CD")) || "03".equals(info.getString("ONLINE_PROD_TYPE_CD")) || "13".equals(info.getString("ONLINE_PROD_TYPE_CD"))) { // 상품유형(주문제작,설치상품)
							if ("".equals(info.getString("HOPE_DELI_PSBT_DD"))) {
								chk = true;
								msg2 += "- 기본정보 " + (i + 1) + "번째 행 희망배송가능일 누락<br/>";
							}
						} else if ("04".equals(info.getString("ONLINE_PROD_TYPE_CD"))) { // 상품유형(골라담기상품)
							if ("".equals(info.getString("OPTN_LOAD_SET_QTY"))) {
								chk = true;
								msg2 += "- 기본정보 " + (i + 1) + "번째 행 세트수량 누락<br/>";
							}

							if ("".equals(info.getString("OPTN_LOAD_CONTENT"))) {
								chk = true;
								msg2 += "- 기본정보 " + (i + 1) + "번째 행 세트수량TEXT 누락<br/>";
							}
						} else if ("07".equals(info.getString("ONLINE_PROD_TYPE_CD"))) { // 상품유형(예약상품)
							if ("".equals(info.getString("RSERV_ORD_PSBT_START_DY"))) {
								chk = true;
								msg2 += "- 기본정보 " + (i + 1) + "번째 행 예약주문가능일(시작) 누락<br/>";
							}

							if ("".equals(info.getString("RSERV_ORD_PSBT_END_DY"))) {
								chk = true;
								msg2 += "- 기본정보 " + (i + 1) + "번째 행 예약주문가능일(종료) 누락<br/>";
							}

							if ("".equals(info.getString("RSERV_PROD_PICK_IDCT_DY"))) {
								chk = true;
								msg2 += "- 기본정보 " + (i + 1) + "번째 행 예약상품 출고지시일 누락<br/>";
							}
						}

						if ("".equals(info.getString("STD_CAT_CD"))) {
							chk = true;
							msg2 += "- 기본정보 " + (i + 1) + "번째 행 EC 표준카테고리 누락<br/>";
						}

						ArrayList<String> dispCatCdOverlap = new ArrayList<String>();
						int dispLtOnCatCnt = 0;
						int dispMartCatCnt = 0;
						String[] dispCatCd = info.getString("DISP_CAT_CD").trim().split(",");

						for (int j = 0; j < dispCatCd.length; j++) {
							DataMap catCdMap = new DataMap();
							catCdMap.put("stdCatCd", info.getString("STD_CAT_CD"));
							catCdMap.put("dispCatCd", dispCatCd[j]);
							dispLtOnCatCnt = dispLtOnCatCnt + nEDMPRO0030Service.selectCountDispLton(catCdMap);
							dispMartCatCnt = dispMartCatCnt + nEDMPRO0030Service.selectCountDispMart(catCdMap);

							if (!dispCatCdOverlap.contains(dispCatCd[j])) {
								dispCatCdOverlap.add(dispCatCd[j]);
							}
						}

						if (dispLtOnCatCnt < 1) {
							chk = true;
							msg2 += "- 기본정보 " + (i + 1) + "번째 행 EC 전시카테고리(롯데ON) 누락<br/>";
						}

						if (dispMartCatCnt < 1) {
							chk = true;
							msg2 += "- 기본정보 " + (i + 1) + "번째 행 EC 전시카테고리(롯데마트몰) 누락<br/>";
						}

						if (dispLtOnCatCnt + dispMartCatCnt != dispCatCd.length) {
							chk = true;
							msg2 += "- 기본정보 " + (i + 1) + "번째 행 EC 표준/전시카테고리 번호를 확인하여주세요 (※표준/전시카테고리는 서로 연관되어야합니다.)<br/>";
						}

						if (dispCatCdOverlap.size() != dispCatCd.length) {
							chk = true;
							msg2 += "- 기본정보 " + (i + 1) + "번째 행 EC 전시카테고리에 중복값이 있습니다.<br/>";
						}

						if (chk) {
							chk = false;
							errCnt++;
							continue;
						}

						//TO-BE 미사용 메뉴로 판단되지만... 일단 수정
						//250624 Key생성방식 변경 _(구EPC) BOS 온라인상품쪽과 PGM_ID 겹치지 않기 위해, BOS에서 채번된 값을 받아서 사용 
						String newPgmId = ediProductService.selectNewProductCode();		//신상품코드 생성
						//신상품코드 생성 실패 시, exception
						if("".equals(newPgmId)) {
							throw new AlertException("신상품 가등록 코드 생성에 실패하였습니다.");
						}
						
						info.put("PGM_ID", newPgmId); //신규상품코드
						info.put("SELL_CD", onlineRepinfo.getString("SELL_CD")); //판매코드
						info.put("ONLINE_PROD_NM", onlineRepinfo.getString("ONLINE_PROD_NM")); //온라인 대표상품명
						info.put("PRFT_RATE", onlineRepinfo.getString("PRFT_RATE")); //이익율
						info.put("TAXAT_DIVN_CD", onlineRepinfo.getString("TAXAT_DIVN_CD")); //면과세구분

						nEDMPRO0060Service.insertProductInfo(info); //상품 기본정보 입력


						for (int j = 0; j < dispCatCd.length; j++) {
							DataMap catCdMap = new DataMap();
							catCdMap.put("PGM_ID", info.getString("PGM_ID"));
							catCdMap.put("PROD_CD", info.getString("PGM_ID"));
							catCdMap.put("STD_CAT_CD", info.getString("STD_CAT_CD"));
							catCdMap.put("DISP_CAT_CD", dispCatCd[j]);
							nEDMPRO0060Service.insertEcProductCategory(catCdMap); // EC전시카테고리 등록
						}

						//sucCnt++;

						//20180904 상품키워드 입력 기능 추가
						for (int j = 0; j < keywordMapList.size(); j++) {
							DataMap keywordInfo = keywordMapList.get(j);
							String pgmId = info.getString("PGM_ID");
							prodKeyNo = info.getString("KEY_NO");

							keyNoChkMap.put(prodKeyNo, keyNoChkMap.get(prodKeyNo));
							if (keyNoChkMap.get(prodKeyNo) == null) {
								keyNoChkMap.put(prodKeyNo, 0);//초기화
							}

							if (!"".equals(keywordInfo.getString("KEY_NO")) && info.getString("KEY_NO").equals(keywordInfo.getString("KEY_NO"))) {
								keyNoChkMap.put(prodKeyNo, keyNoChkMap.get(prodKeyNo)+1);

								for(int n=0; n<keywordColNms.length; n++) {
									if("".equals(keywordInfo.getString(keywordColNms[n]).trim())) {
										keywordChk = true;
										keyNoErr.add(keywordInfo.getString("KEY_NO"));
										msgKeyword = "- 상품키워드 필수입력 값 누락<br/>";
									}
								}

								if (!"".equals(keywordInfo.getString("SEARCH_KYWRD"))) {
									String tempVal = this.getMaxByteString(keywordInfo.getString("SEARCH_KYWRD"), 39);
									keywordInfo.put("SEARCH_KYWRD", tempVal);
								}

								if (keywordChk) {
									keywordChk = false;
									keywordErrCnt++;
									continue;
								}

								pedmpro0011VO.setPgmId(info.getString("PGM_ID"));
								pedmpro0011VO.setSeq("NEW");
								pedmpro0011VO.setSearchKywrd(keywordInfo.getString("SEARCH_KYWRD"));
								pedmpro0011VO.setRegId(info.getString("entpCd"));

								pEDMPRO000Service.insertTpcPrdKeyword(pedmpro0011VO);

								keyNo = keywordInfo.getString("KEY_NO");
								keyNoMap.put(keyNo, keyNoMap.get(keyNo));

								if (keyNoMap.get(keyNo) == null) {
									keyNoMap.put(keyNo,0);//초기화
								} else if(keyNoMap.containsKey(keyNo)) {
									keyNoMap.put(keyNo, keyNoMap.get(keyNo)+1);

									if (keyNoMap.get(keyNo) >= 10){
										msgWrn = "키워드는 10개 까지 등록 가능합니다.";
										pEDMPRO000Service.deleteTpcPrdAllKeyword(pgmId);
										pEDMPRO000Service.deleteTpcNewPrdAllKeyword(pgmId);
										if (keyNoMap.get(keyNo)==10) {//중복 카운트 방지
											keyNoErr.add(keyNo);
											keywordErrCnt +=11;
											errCnt++;
										} else {
											keywordErrCnt++;
										}
									}
								}

								//keywordSucCnt++;
							} else if ("".equals(keywordInfo.getString("KEY_NO"))) {
								msgKeyword = "- 상품키워드 필수입력 값 누락<br/>";
								if (i==0) { //중복 카운트 방지
									keywordErrCnt++;
								}
							} else if (i == (mapList.size() - 1) && !info.getString("KEY_NO").equals(keywordInfo.getString("KEY_NO"))) {
								 //상품 기본정보 기준순번은 미입력하였으나, 상품키워드 기준순번은 입력한 경우
								if(!keyNoChkMap.containsKey(keywordInfo.getString("KEY_NO"))) {
									msgKeyword = "- 기본정보 필수입력 값 누락 , 기준순번을 확인하세요.<br/>";
									keywordErrCnt++;
								}
							}
							if (j == keywordMapList.size() - 1) {
								if (keyNoChkMap.get(prodKeyNo) == 0) {
									msgKeyword = "- 상품키워드 필수입력 값 누락<br/>";  //상품키워드 미입력한 상품 기본정보 기준순번이 존재할 경우
									keyNoErr.add(prodKeyNo);
									pEDMPRO000Service.deleteTpcPrdAllKeyword(pgmId);
									pEDMPRO000Service.deleteTpcNewPrdAllKeyword(pgmId);
									errCnt++;
								}

								for (String key : keyNoMap.keySet()) {
									if (keyNoMap.get(key) < 2) {
										msgWrn = "상품 한 개당 상품키워드 3개 이상 등록해야합니다.<br/>";
										pEDMPRO000Service.deleteTpcPrdAllKeyword(pgmId);
										pEDMPRO000Service.deleteTpcNewPrdAllKeyword(pgmId);
										keywordErrCnt += keyNoMap.get(key);
										if (i == mapList.size() - 1) {
											errCnt++;
											keyNoErr.add(key);
										}
									}
								}
							}
						}
						sucCnt = upCnt-errCnt;
						keywordSucCnt = keywordUpCnt - keywordErrCnt;
						//20180904 상품키워드 입력 기능 추가

						if ("01".equals(optionVal)) {

							boolean isAttrOk = false;
							ArrayList<String> arrtAttrId = new ArrayList<String>();
							ArrayList<String> compareAttrId = new ArrayList<String>();
							List<String> attrValIdList = new ArrayList<String>();
							List<String> compAttrValIdList = new ArrayList<String>();
							String attrValIds = "";
							int tmpAttrCnt = 0; // 키값 별 옵션갯수
							int tmpAttrValIdCnt = 0;
							int tmpAttrValCnt = 0;

							for (int j = 0; j < attrMapList.size(); j++) {
								DataMap attrInfo = attrMapList.get(j);
								if(!"".equals(attrInfo.getString("KEY_NO")) && info.getString("KEY_NO").equals(attrInfo.getString("KEY_NO"))){
									arrtAttrId.add(attrInfo.getString("ATTR_ID"));
									// 검증 조건은 KEY_NO 와 일치하는 조건 하에  KEY_NO 하위의 옵션속성의 attr_val_id 와 attr_val 값의 전체갯수,
									tmpAttrCnt++;
									//logger.debug("ATTR_VAL_ID ::: [" + attrInfo.getString("ATTR_VAL_ID") + "]");
									//logger.debug("ATTR_VAL ::: [" + attrInfo.getString("ATTR_VAL") + "]");
									if ( !"".equals(attrInfo.getString("ATTR_VAL_ID"))) {
										attrValIds = attrInfo.getString("ATTR_VAL_ID");
										tmpAttrValIdCnt++;
									}
									if ( !"".equals(attrInfo.getString("ATTR_VAL"))) {
										tmpAttrValCnt++;
									}
								}
								if (!"".equals(attrValIds)) {
									attrValIdList.add(attrValIds);
								}
							}

							// 전체 단품속성 갯수와 속성값ID 갯수가 동일하고 속성값갯수가0 이거나 전체 단품속성 갯수와 속성값 갯수가 동일하고 속성값ID가 0 이어야
							// 속성값ID와 속성값 중 한가지만 사용하는것에 대한 검증이 완료
							isAttrOk = !(tmpAttrValIdCnt > 0 && tmpAttrValCnt > 0);

							if (!isAttrOk) {
								msg3 += "- [기준 순번] ["+info.getString("KEY_NO")+"] 단품속성 상품속성아이디에 맞는 상품속성값아이디와 상품속성값텍스트입력 중 한가지는 필수로 입력해야 합니다.<br/>";
								optErrCnt++;
								attrErrCnt = attrErrCnt + tmpAttrCnt;
							}

							if (isAttrOk) {
								for (int a = 0; a < arrtAttrId.size(); a++) { // 중복체크 (중복제거)
									if (!compareAttrId.contains(arrtAttrId.get(a))) {
										compareAttrId.add(arrtAttrId.get(a));
									}
								}

								for (int a = 0; a < compareAttrId.size(); a++) { // 중복체크 (중복데이터확인)
									arrtAttrId.remove(compareAttrId.get(a));
								}

								if (arrtAttrId.size() > 0) {
									msg3 += "- [기준 순번] ["+info.getString("KEY_NO")+"] 단품속성내에 상품속성아이디 "+arrtAttrId.get(0)+"가 중복된 값입니다.";
									if (optErrCnt == 0) { optErrCnt++; }
									attrErrCnt++;
									isAttrOk = false;
								} // 중복된 값은 허용안함
							}

							if (isAttrOk) {
								// 속성값코드
								for (int a = 0; a < attrValIdList.size(); a++) { // 중복체크 (중복제거)
									if (!compAttrValIdList.contains(attrValIdList.get(a))) {
										compAttrValIdList.add(attrValIdList.get(a));
									}
								}
								for (int a = 0; a < compAttrValIdList.size(); a++) { // 중복체크 (중복데이터확인)
									attrValIdList.remove(compAttrValIdList.get(a));
								}
								if (attrValIdList.size() > 0) {
									msg3 += "- [기준 순번] [" + info.getString("KEY_NO") + "] 단품속성내에 상품속성값아이디 " + attrValIdList.get(0) + "가 중복된 값입니다.";
									if (optErrCnt == 0) { optErrCnt++; }
									attrErrCnt++;
									isAttrOk = false;
								}
							}

							if (isAttrOk) {
								boolean isAttrExist = false;
								for (int j = 0; j < attrMapList.size(); j++) {
									DataMap attrInfo = attrMapList.get(j);
									if(!"".equals(attrInfo.getString("KEY_NO")) && info.getString("KEY_NO").equals(attrInfo.getString("KEY_NO"))){
										isAttrExist = true;
										for (int l = 0; l < attrColNms.length; l++) {
											if (!"ATTR_VAL_ID".equals(attrColNms[l]) && !"ATTR_VAL".equals(attrColNms[l])) {
												if ("".equals(attrInfo.getString(attrColNms[l]))) {
													attrChk = true;
													msg3 += "- 단품속성 필수입력 값 누락<br/>";
												}
											}
										}
										if ("".equals(attrInfo.getString("ATTR_VAL_ID")) && "".equals(attrInfo.getString("ATTR_VAL"))) {
											attrChk = true;
											msg3 += "- 단품속성 필수입력 값 누락<br/>";
										}

										attrInfo.put("STD_CAT_CD", info.getString("STD_CAT_CD"));
										attrInfo.put("ATTR_PI_TYPE", "P");
										isAttrExist = nEDMPRO0030Service.selectCountEcProductAttrValId(attrInfo) > 0 ? true : false;
										if (!isAttrExist) {
											attrChk = true;
											msg3 += "- EC 표준카테고리에 존재하는 단품속성정보가 아닙니다.<br/>";
										}

										if (attrChk) {
											attrChk = false;
											attrErrCnt++;
										} else {
											attrInfo.put("PGM_ID",info.getString("PGM_ID"));
											attrInfo.put("ITEM_CD","001");
											attrInfo.put("entpCd", info.getString("entpCd"));
											attrInfo.put("STD_CAT_CD", info.getString("STD_CAT_CD"));

											nEDMPRO0060Service.insertEcProductAttribute(attrInfo); // 단품 옵션 정보 등록
											attrSucCnt++;
										}
									}
								}

							}

						} else if ("02".equals(optionVal)) {
							DataMap attrParam = new DataMap();
							attrParam.put("STD_CAT_CD", info.getString("STD_CAT_CD"));
							attrParam.put("ATTR_PI_TYPE", "I");
							int attrIdCnt = nEDMPRO0030Service.selectCountEcProductAttrId(attrParam);

							// 중복값 검사용
							List<String> attrValIdList = new ArrayList<String>();
							List<String> compAttrValIdList = new ArrayList<String>();
							List<String> attrValList = new ArrayList<String>();
							List<String> compAttrValList = new ArrayList<String>();
							// 개별 검증 전 상품속성값아이디, 상품속성값텍스트입력 1가지 사용하는지 1차 검증 / 불일치일 경우 해당 기준순번의 전체 옵션값 입력 안함
							boolean isOptOk = false;
							int tmpOptCnt = 0;
							int tmpAttrCnt = 0; // 키값 별 옵션갯수
							int tmpAttrValIdCnt = 0;
							int tmpAttrValCnt = 0;
							for (int k = 0; k < optMapList.size(); k++) {
								String attrValIds = "";
								String attrVals = "";
								DataMap optInfo = optMapList.get(k);
								if (!"".equals(optInfo.getString("KEY_NO")) && info.getString("KEY_NO").equals(optInfo.getString("KEY_NO"))) {
									tmpOptCnt++;
									for (int j = 0; j < attrMapList.size(); j++) {
										DataMap attrInfo = attrMapList.get(j);
										if (!"".equals(attrInfo.getString("KEY_NO")) && !"".equals(attrInfo.getString("ITEM_CD"))
												&& optInfo.getString("KEY_NO").equals(attrInfo.getString("KEY_NO"))
												&& optInfo.getString("ITEM_CD").equals(attrInfo.getString("ITEM_CD"))) {
											// 검증 조건은 KEY_NO 와 ITEM_CD는 일치하는 조건 하에  KEY_NO 하위의 옵션속성의 attr_val_id 와 attr_val 값의 전체갯수,
											tmpAttrCnt++;
											//logger.debug("ATTR_VAL_ID ::: [" + attrInfo.getString("ATTR_VAL_ID") + "]");
											//logger.debug("ATTR_VAL ::: [" + attrInfo.getString("ATTR_VAL") + "]");
											if ( !"".equals(attrInfo.getString("ATTR_VAL_ID"))) { // 상품속성값ID 입력갯수
												attrValIds += attrInfo.getString("ATTR_VAL_ID") + ":";
												tmpAttrValIdCnt++;
											}
											if ( !"".equals(attrInfo.getString("ATTR_VAL"))) { // 상품속성값 입력갯수
												attrVals += attrInfo.getString("ATTR_VAL") + ":";
												tmpAttrValCnt++;
											}
										}
									}
									if (!"".equals(attrValIds)) {
										attrValIdList.add(attrValIds);
									}
									if (!"".equals(attrVals)) {
										attrValList.add(attrVals);
									}
								}
							}
							// 전체 옵션단품 속성 갯수와 속성값ID 갯수가 동일하고 속성값갯수가0 이거나 전체 옵션단품 속성 갯수와 속성값 갯수가 동일하고 속성값ID가 0 이어야
							// 속성값ID와 속성값 중 한가지만 사용하는것에 대한 검증이 완료
							isOptOk = (tmpAttrCnt == tmpAttrValIdCnt && tmpAttrValCnt == 0) || (tmpAttrCnt == tmpAttrValCnt && tmpAttrValIdCnt == 0);

							if (!isOptOk) {
								msg3 += "- [기준 순번] ["+info.getString("KEY_NO")+"] 단품속성 상품속성아이디에 맞는 상품속성값아이디와 상품속성값텍스트입력 중 한가지는 필수로 입력해야 합니다.<br/>";
								optErrCnt = optErrCnt + tmpOptCnt;
								attrErrCnt = attrErrCnt + tmpAttrCnt;
								//logger.error("===== OPT2 ::: 1 =====");
							}

							//logger.debug("attrValIdList ::: " + attrValIdList.toString());
							//logger.debug("compAttrValIdList ::: " + compAttrValIdList.toString());
							//logger.debug("attrValList ::: " + attrValList.toString());
							//logger.debug("compAttrValIdList ::: " + compAttrValList.toString());
							// 속성값코드
							for (int a = 0; a < attrValIdList.size(); a++) { // 중복체크 (중복제거)
								if (!compAttrValIdList.contains(attrValIdList.get(a))) {
									compAttrValIdList.add(attrValIdList.get(a));
								}
							}
							for (int a = 0; a < compAttrValIdList.size(); a++) { // 중복체크 (중복데이터확인)
								attrValIdList.remove(compAttrValIdList.get(a));
							}
							if (attrValIdList.size() > 0) {
								msg3 += "- [기준 순번] ["+info.getString("KEY_NO")+"] 단품옵션내에 중복되는 상품속성값아이디 정보가 존재합니다.<br/>";
								if (optErrCnt == 0) { optErrCnt++; }
								attrErrCnt = attrErrCnt + attrValIdList.size();
								isOptOk = false;
								//logger.error("===== OPT2 ::: 2 =====");
							}

							// 속성값
							for (int a = 0; a < attrValList.size(); a++) { // 중복체크 (중복제거)
								if (!compAttrValList.contains(attrValList.get(a))) {
									compAttrValList.add(attrValList.get(a));
								}
							}
							for (int a = 0; a < compAttrValList.size(); a++) { // 중복체크 (중복데이터확인)
								attrValList.remove(compAttrValList.get(a));
							}
							if (attrValList.size() > 0) {
								msg3 += "- [기준 순번] ["+info.getString("KEY_NO")+"] 단품옵션내에 중복되는 상품속성값텍스트입력 정보가 존재합니다.<br/>";
								if (optErrCnt == 0) { optErrCnt++; }
								attrErrCnt = attrErrCnt + attrValList.size();
								isOptOk = false;
								//logger.error("===== OPT2 ::: 3 =====");
							}
							// 단품옵션별 속성값코드, 속성값 중복체크 End

							if (isOptOk) {

								for (int k = 0; k < optMapList.size(); k++) {
									DataMap optInfo = optMapList.get(k);

									if (!"".equals(optInfo.getString("KEY_NO")) && info.getString("KEY_NO").equals(optInfo.getString("KEY_NO"))) {
										for (int m = 0; m < optColNms.length; m++) {
											if("".equals(optInfo.getString(optColNms[m])) && !"OPTN_AMT".equals(optColNms[m])){
												optChk = true;
												msg3 += "- [기준 순번] ["+info.getString("KEY_NO")+"] 단품옵션 필수입력 값 누락<br/>";
												//logger.error("===== OPT2 ::: 4 =====");
											}
										}

										if ("1".equals(info.getString("OPTN_PROD_PRC_MGR_YN")) && "".equals(optInfo.getString("OPTN_AMT"))) { // 옵션 별 별도가격
											optChk = true;
											msg4 += "- [기준 순번] ["+info.getString("KEY_NO")+"] 단품옵션"+(k+1)+"번째 행 가격 필수입력 값 누락<br/>";
											//logger.error("===== OPT2 ::: 5 =====");
										}

										if (optChk) {
											optChk = false;
											optErrCnt++;
											continue;
										}

										boolean isAttrExist = false;
										int attrIdValidCnt = 0;

										for (int j = 0; j < attrMapList.size(); j++) {
											DataMap attrInfo = attrMapList.get(j);
											if (!"".equals(attrInfo.getString("KEY_NO")) && !"".equals(attrInfo.getString("ITEM_CD"))
													&& optInfo.getString("KEY_NO").equals(attrInfo.getString("KEY_NO"))
													&& optInfo.getString("ITEM_CD").equals(attrInfo.getString("ITEM_CD"))) {
												//logger.error("===== OPT2 ::: KEY::[" + attrInfo.getString("KEY_NO") + "] , ITEM_CD::[" + attrInfo.getString("ITEM_CD") + "]");
												for (int l = 0; l < attrColNms.length; l++) {
													if (!"ATTR_VAL_ID".equals(attrColNms[l]) && !"ATTR_VAL".equals(attrColNms[l])) {
														if ("".equals(attrInfo.getString(attrColNms[l]))) {
															attrChk = true;
															msg3 += "- [기준 순번]단품코드 ["+info.getString("KEY_NO")+"]"+optInfo.getString("ITEM_CD")+"의 단품속성 필수입력 값 (상품속성아이디) 누락<br/>";
															//logger.error("===== OPT2 ::: 6 ===== " + attrInfo.getString(attrColNms[l]));
														}
													}
												}
												if ("".equals(attrInfo.getString("ATTR_VAL_ID")) && "".equals(attrInfo.getString("ATTR_VAL"))) {
													attrChk = true;
													msg3 += "- [기준 순번]단품코드 ["+info.getString("KEY_NO")+"]"+optInfo.getString("ITEM_CD")+"의 단품속성 필수입력 값(상품속성값아이디 또는 상품속성값텍스트입력) 누락<br/>";
													//logger.error("===== OPT2 ::: 7 =====");
												}
												attrInfo.put("STD_CAT_CD", info.getString("STD_CAT_CD"));
												attrInfo.put("ATTR_PI_TYPE", "I");
												isAttrExist = nEDMPRO0030Service.selectCountEcProductAttrValId(attrInfo) > 0 ? true : false;
												if (!isAttrExist) {
													attrChk = true;
													msg3 += "- [기준 순번]단품코드 ["+info.getString("KEY_NO")+"]"+optInfo.getString("ITEM_CD")+" EC 표준카테고리에 존재하는 단품속성정보가 아닙니다.<br/>";
													//logger.error("===== OPT2 ::: 8 =====");
												} else {
													attrIdValidCnt++;
												}

												if (attrChk) {
													attrChk = false;
													attrErrCnt++;
													continue;
												}
											}
										}

										if (attrIdCnt != attrIdValidCnt) {
											optChk = true;
											msg3 += "-[기준순번]단품코드 "+"["+optInfo.getString("KEY_NO")+"]"+optInfo.getString("ITEM_CD")+"의 롯데on 카테고리의 속성정보리스트는 "+attrIdCnt+"개 여야합니다.<br/>";
											//logger.error("===== OPT2 ::: 9 ===== -[기준순번]단품코드 "+"["+optInfo.getString("KEY_NO")+"]"+optInfo.getString("ITEM_CD")+"의 롯데on 카테고리의 속성정보리스트는 "+attrIdCnt+"개 여야합니다.<br/>");
										}

										if (optChk) {
											optChk = false;
											optErrCnt++;
											continue;
										}

										for (int j = 0; j < attrMapList.size(); j++) {
											DataMap attrInfo = attrMapList.get(j);
											if (!"".equals(attrInfo.getString("KEY_NO")) && !"".equals(attrInfo.getString("ITEM_CD"))
													&& optInfo.getString("KEY_NO").equals(attrInfo.getString("KEY_NO"))
													&& optInfo.getString("ITEM_CD").equals(attrInfo.getString("ITEM_CD"))) {
												
												attrInfo.put("PGM_ID", info.getString("PGM_ID"));
												attrInfo.put("entpCd", info.getString("entpCd"));
												attrInfo.put("STD_CAT_CD", info.getString("STD_CAT_CD"));
												
												nEDMPRO0060Service.insertEcProductAttribute(attrInfo); // 단품속성 등록
												attrSucCnt++;
											}
										}

										optInfo.put("PGM_ID", info.getString("PGM_ID"));
										optInfo.put("entpCd", info.getString("entpCd"));
										optInfo.put("SELL_CD", info.getString("SELL_CD"));

										nEDMPRO0060Service.insertProductItemInfo(optInfo); // 단품옵션 등록

										optSucCnt++;
									} else if ("".equals(optInfo.getString("KEY_NO"))) {
										msg3 += "- 단품옵션 필수입력 값 누락<br/>";
										optErrCnt++;
										//logger.error("===== OPT2 ::: 10 =====");
									}
								}

							}

						}
					}
				}
			} else if("03".equals(optionVal) || "04".equals(optionVal)) {  // 전상법, KC인증
				boolean prodChk = false;

				String[] colNms = {"PROD_NM","INFO_COL_NM","COL_VAL","PGM_ID","INFO_GRP_CD","INFO_COL_CD"};

				List<DataMap>  mapList = fileMngService.readUploadNullColExcelFile(mptRequest, colNms, 2, 0);
				upCnt = mapList.size();

				for (int i = 0; i < mapList.size(); i++) {
					DataMap info = mapList.get(i);

					for (int j = 0; j < colNms.length; j++) {
						if ("".equals(info.getString(colNms[j]))) {
							prodChk = true;
							msg1 = "- 필수항목 값 누락<br/>";
						}
					}

					info.put("pgmId", info.getString("PGM_ID"));
					int imsiProdCnt = nEDMPRO0060Service.selectImsiProductCnt(info);

					if (imsiProdCnt == 0) {
						msg1 += "- " + (i + 1) + "번째 행 상품코드 : " + info.getString("PGM_ID") + " 상품이 등록되어 있지 않습니다.<br/> ";
						errCnt++;
						continue;
					}

					if (!prodChk) {
						if ("".equals(info.getString("PGM_ID")) || "".equals(info.getString("INFO_GRP_CD")) || "".equals(info.getString("INFO_COL_CD"))) {
							prodChk = true;
							msg2 = "- 잘못된 업로드 양식 입니다. 다시 다운 받아서 진행 하세요.";
						}
					}

					if (!prodChk) {
						info.put("entpCd", paramMap.getString("entpCd"));

						if ("03".equals(optionVal)) { //전상법
							nEDMPRO0060Service.saveProdAddDetail(info);
						} else if ("04".equals(optionVal)) { //KC 인증
							nEDMPRO0060Service.saveProdCertDetail(info);
						}

						sucCnt++;
					}
				}

				errCnt = upCnt - sucCnt;
			} else if ("05".equals(optionVal) || "06".equals(optionVal)) { // 상품상세, 상품이미지
				String[] colNms = { "PROD_CD", "PROD_NM", "IMG_NM" };

				imgList = fileMngService.readUploadNullColExcelFile(mptRequest, colNms, 2, 0);
			}
		} catch (Exception e) {
			// 작업오류
			logger.error("error message --> ", e);

			msgWrn = "* 올바르지 않은 양식 입니다. 확인 후 다시 업로드 하세요.";
		} finally {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script type=\'text/javascript'>");
			out.println("	var rtnVal = new Object();");

			if (!isErr) {
				out.println("	var keyNoErr = new Array();");
				out.println("	rtnVal.upCnt = '" + upCnt + "'");
				out.println("	rtnVal.optUpCnt = '" + optUpCnt + "'");
				out.println("	rtnVal.attrUpCnt = '" + attrUpCnt + "'");
				out.println("	rtnVal.sucCnt = '" + sucCnt + "'");
				out.println("	rtnVal.errCnt = '" + errCnt + "'");
				out.println("	rtnVal.optSucCnt = '" + optSucCnt + "'");
				out.println("	rtnVal.optErrCnt = '" + optErrCnt + "'");
				out.println("	rtnVal.attrSucCnt = '" + attrSucCnt + "'");
				out.println("	rtnVal.attrErrCnt = '" + attrErrCnt + "'");

				//20180904 상품키워드 입력 기능 추가
				out.println("	rtnVal.keywordUpCnt = '" + keywordUpCnt + "'");
				out.println("	rtnVal.keywordSucCnt = '" + keywordSucCnt + "'");
				out.println("	rtnVal.keywordErrCnt = '" + keywordErrCnt + "'");
				//20180904 상품키워드 입력 기능 추가

				//상품키워드 기준순번 오류 추가
				for (int i = 0; i < keyNoErr.size(); i++) {
					out.println("	keyNoErr.push('" + keyNoErr.get(i) + "');");
				}
				out.println("	rtnVal.keyNoErr = keyNoErr");

				if ("05".equals(optionVal) || "06".equals(optionVal)) {
					if (imgList.size() > 0) {
						out.println("	var prodCdArray = new Array();"); // 상품코드
						out.println("	var prodNmArray = new Array();"); // 상품명
						out.println("	var imgNmArray = new Array();"); // 이미지파일

						for (int i = 0; i < imgList.size(); i++) {
							DataMap map = imgList.get(i);

							if ("".equals(map.getString("PROD_CD")) || "".equals(map.getString("IMG_NM"))) {
								msgWrn = "필수값이 입력 안되었거나 양식이 올바르지 않습니다.";
								break;
							}

							out.println("prodCdArray.push('" + map.getString("PROD_CD") + "');");
							out.println("prodNmArray.push('" + map.getString("PROD_NM") + "');");
							out.println("imgNmArray.push('" + map.getString("IMG_NM") + "');");
						}

						out.println("rtnVal.prodCdArr = prodCdArray;");
						out.println("rtnVal.prodNmArr = prodNmArray;");
						out.println("rtnVal.imgNmArr = imgNmArray;");
					}
				}
			}
			if ("".equals(msgWrn)) {
				out.println("	rtnVal.msg = '" + msg1 + msg2 + msgKeyword + msg3 + msg4 + "'");
			} else {
				out.println("	rtnVal.msg = '" + msgWrn + "'");
			}
			out.println("	parent.uploadReturn(rtnVal);");
			out.println("</script>");
		}
	}

	//20180904 상품키워드 입력 기능 추가
	/**
	 * 상품키워드 문자길이 확인 및 자르기
	 * @param String str 상품키워드
	 * @param int maxLen 최대문자길이
	 * @return 상품키워드 최대문자길이 초과할 경우 최대문자길이만큼 자른 후 반환
	 */
	public String getMaxByteString(String str, int maxLen) {
		StringBuilder sb = new StringBuilder();
		int curLen = 0;
		String curChar;

		for(int i=0; i<str.length(); i++) {
			curChar = str.substring(i, i+1);
			curLen += curChar.getBytes().length;
			if(curLen > maxLen) {
				break;
			} else {
				sb.append(curChar);
			}
		}

		return sb.toString();
	}
	//20180904 상품키워드 입력 기능 추가

	/**
	 * 이미지 압축파일 업로드
	 *
	 * @param request
	 * @param reuqest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/batchProductImgZip.do")
	public void batchProductImgZip(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;

		String prodImgUploadPath = config.getString("edi.online.image.path") + "/productImg";

		List<String> zipRtnList = null;
		int zipErrCtn = 0;
		int zipRtnCnt = 0;
		String zipRtnStr = "";
		String[] zipRtnArr = null;

		String orginFileName = ""; //원본 파일 명

		FileOutputStream outputStream = null;

		File dirPath = new File(prodImgUploadPath);

		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}

		try {
			final Map<String, MultipartFile> files = mptRequest.getFileMap();

			if (!files.isEmpty()) {
				MultipartFile file;
				Entry<String, MultipartFile> entry;

				Iterator<Entry<String, MultipartFile>> iter = files.entrySet().iterator();

				while (iter.hasNext()) {
					entry = iter.next();

					file = entry.getValue();
					orginFileName = file.getOriginalFilename();

					outputStream = new FileOutputStream(prodImgUploadPath + File.separator + orginFileName);
					int actResult = FileCopyUtils.copy(file.getInputStream(), outputStream);

					if (actResult == 0) {
						throw new IllegalArgumentException("업로드 작업중에 오류가 발생하였습니다.");
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (Exception e) {
					logger.error("batchProductImgZip (Exception) : " + e.getMessage());
				}
			}

			File orgFile = new File(prodImgUploadPath + File.separator + orginFileName);
			File orgFilePath = new File(prodImgUploadPath);

			zipRtnList = unzip(orgFile, orgFilePath);

			zipRtnCnt = zipRtnList.size();

			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script type=\'text/javascript'>");
			out.println("	var rtnVal = new Object();");
			out.println("	var prodCdArray = new Array();"); // 상품코드

			if (zipRtnCnt > 0) {

				/*파일(jpg, zip) 삭제*/
				for (File delfile : dirPath.listFiles()) {
				    synchronized (this) {
				        try {
				            if (delfile.isFile()) {
				                if (!delfile.delete()) {
				                    throw new IOException("파일 삭제 실패 : " + delfile.getPath());
				                }
				            }
				        } catch(IOException e) {
				            logger.error(e.getMessage());
				            throw e;
				        }
                    }
				}

				for (int i = 0; i < zipRtnList.size(); i++) {

					zipRtnStr = zipRtnList.get(i);
					zipRtnArr = zipRtnStr.split("\\|");

					if (!"fail".equals(zipRtnArr[0])) {
						zipErrCtn = Integer.parseInt(zipRtnArr[0]);

						if (zipErrCtn > 0) {
							out.println("prodCdArray.push('" + zipRtnArr[1] + " : " + zipRtnArr[2] + "');");
						}

					} else {
						out.println("prodCdArray.push('이미지 압축파일을 다시 확인 하시기 바랍니다.');");
					}
				}

				out.println("rtnVal.errCnt = '" + zipRtnCnt + "'");
				out.println("rtnVal.prodCdArr = prodCdArray;");
				out.println("parent.doImgFail(rtnVal);");

			} else {
				out.println("parent.doImgUpload();");
			}

			out.println("</script>");
		}
	}

	@RequestMapping("edi/product/batchProductImgUpload.do")
	public @ResponseBody JSONObject batchProductImgUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jObj = new JSONObject();
		JSONObject rstJObj = new JSONObject();

		int newSeq = 0;
		int sucCnt = 0;
		int errCnt = 0;

		String optionVal = SecureUtil.splittingFilterAll(request.getParameter("optionVal"));
		String entpCd = SecureUtil.splittingFilterAll(request.getParameter("entpCd"));
		String[] prodCds = SecureUtil.stripXSSArray(request.getParameterValues("PROD_CD"));
		String[] imgNms = request.getParameterValues("IMG_NM");
		String[] prodNms = request.getParameterValues("PROD_NM");
		String prodImgUploadPath = config.getString("edi.online.image.path") + "/productImg";
		String bfProdCd = "";
		String msg = "";
		ArrayList newFileSourceArr = new ArrayList();

		File files = new File(prodImgUploadPath);

		try {
			for (int i = 0; i < prodCds.length; i++) {
				for (File file : files.listFiles()) {
					if (file.isDirectory()) {
						continue;
					}

					if (file.isFile()) {
						if (imgNms[i].equals(file.getName())) {
							String typeChk = new MimetypesFileTypeMap().getContentType(file.getName());

							if (typeChk.indexOf("image") == -1) {
								msg += "- " + file.getName() + " 파일은 이미지 파일이 아닙니다.";

								errCnt++;
								continue;
							}

							FileOutputStream imageOutputStream = null;

							DataMap paramMap = new DataMap();
							paramMap.put("pgmId", prodCds[i]);
							paramMap.put("prodNm", prodNms[i]);
							paramMap.put("entpCd", entpCd);

							int imsiProdCnt = nEDMPRO0060Service.selectImsiProductCnt(paramMap);

							if (imsiProdCnt == 0) {
								msg += "- " + (i + 1) + "번째 행 상품코드 : " + prodCds[i] + " 상품이 등록되어 있지 않습니다. <br/>";
								errCnt++;
								continue;
							}

							try {
								String imgUploadDir = "";
								String newFileSource = "";

								if ("05".equals(optionVal)) { //상품상세내용이미지

									if (i > 0 && bfProdCd.equals(prodCds[i])) { //상품상세내용이미지는 한번만 등록
										continue;
									}

									newFileSource = config.getString("edi.namoeditor.file.path") + "/" + file.getName();

								} else if ("06".equals(optionVal)) { //상품 이미지
									if (!bfProdCd.equals(prodCds[i])) {
										newSeq = 0;
									} else {
										newSeq++;
									}

									imgUploadDir = makeSubFolderForOnline(prodCds[i]);
									newFileSource = imgUploadDir + "/" + SecureUtil.splittingFilterAll(prodCds[i]) + "_" + newSeq;
									newFileSourceArr.add(prodCds[i] + "_" + newSeq);
								}

								FileInputStream imageInputStream = new FileInputStream(file);
								imageOutputStream = new FileOutputStream(newFileSource);
								FileCopyUtils.copy(imageInputStream, imageOutputStream);
							} catch (IOException ex) {
								msg += "- 시스템에러 : " + ex.toString();
								errCnt++;
							} finally {
								if (imageOutputStream != null) {
									imageOutputStream.close();
								}

								if ("05".equals(optionVal)) { //상품상세내용이미지

									String productDescrImgUrl = config.getString("edi.namoeditor.file.url") + "/" + file.getName();
									paramMap.put("prodDesc", productDescrHtml(productDescrImgUrl));

									nEDMPRO0060Service.updateNewProdRegDesc(paramMap); //임시상품 테이블 update
									nEDMPRO0060Service.saveNewProdDescr(paramMap); //추가설명 테이블 insert,update

								} else if ("06".equals(optionVal)) { //상품 이미지
									Map<String, Object> prmMap = new HashMap<String, Object>();
									int fileCount = newSeq + 1;

									if (!bfProdCd.equals(prodCds[i])) {
										String pgmId = bfProdCd;

										if ("".equals(bfProdCd)) {
											pgmId = prodCds[i];
										}

										prmMap.put("pgmId", pgmId);
										prmMap.put("fileCount", fileCount);

										nEDMPRO0020Service.updateNewProdImgCnt(prmMap);
									}

									if (prodCds != null && (i == prodCds.length - 1)) {
										prmMap.put("pgmId", prodCds[i]);
										prmMap.put("fileCount", fileCount);

										nEDMPRO0020Service.updateNewProdImgCnt(prmMap);
									}
								}
								sucCnt++;
							}
						}
					}
				}

				bfProdCd = prodCds[i];
			}

			if ("06".equals(optionVal)) { //상품 이미지
				for (int i = 0; i < newFileSourceArr.size(); i++) {
					// ImageUtilsThumbnail.resizeAutoForEPC(newFileSourceArr.get(i).toString()); //구버전 이미지 리사이징 메소드
					imageFileMngService.purgeImageQCServer("01", newFileSourceArr.get(i).toString());
					imageFileMngService.purgeCDNServer("01", newFileSourceArr.get(i).toString());
				}
			}

			for (File delfile : files.listFiles()) {
				if (delfile.isFile()) {
					delfile.delete();
				}
			}

			rstJObj.put("upCnt", prodCds.length);
			rstJObj.put("sucCnt", sucCnt);
			rstJObj.put("errCnt", errCnt);
			rstJObj.put("msg", msg);

			jObj.put("Code", 1);
			jObj.put("Message", "rtn" + rstJObj.toString());
		} catch (Exception e) {
			jObj.put("Code", -1);
			jObj.put("Message", e.getMessage());
		}

		return JsonUtils.getResultJson(jObj);
	}

	/**
	 * Desc : EC 카테고리 조회
	 * @Method Name : NEDMPRO0063
	 * @param request
	 * @exception Exception
	 */
	@RequestMapping(value="/edi/product/NEDMPRO0063.do", method=RequestMethod.GET)
	public String NEDMPRO0063(HttpServletRequest request) throws Exception {
		return "edi/product/NEDMPRO0063";
	}

	/**
	 * Desc : EC 카테고리 상품속성 조회
	 * @Method Name : NEDMPRO0064
	 * @param request
	 * @exception Exception
	 */
	@RequestMapping(value="/edi/product/NEDMPRO0064.do", method=RequestMethod.GET)
	public String NEDMPRO0064(HttpServletRequest request) throws Exception {
		return "edi/product/NEDMPRO0064";
	}
	
	/**
	 * EC 카테고리 리스트 조회 jqGrid
	 * @param request
	 * @return HashMap<String,Object>
	 * @throws Exception
	 */
	@ResponseBody 
	@RequestMapping(value="/edi/product/selectEcStdDispMapping.do", method=RequestMethod.POST)
	public HashMap<String,Object> selectEcStdDispMapping(@RequestParam Map<String,Object> params, HttpServletRequest request) throws Exception {
		HashMap<String,Object> gridMap = new HashMap<String, Object>();
		
		int totalCount = 0;		//전체 페이지 카운트
		int totalPage = 1;		//전체 페이지
		
		DataMap paramMap = new DataMap(request);
		int currentPage = paramMap.getInt("page");	//현재 페이지
		int rowPerPage = paramMap.getInt("rows");	//페이지당 행 수
		rowPerPage = (rowPerPage == 0)? config.getInt("count.row.per.page"):rowPerPage;	//페이지당 행 수 없으면 default setting
		
		int startRow =  (currentPage - 1) * rowPerPage + 1;
		int endRow = startRow + rowPerPage - 1;
		
		
		paramMap.put("startRow", startRow);
		paramMap.put("endRow", endRow);
		
		//data list count
		totalCount = nEDMPRO0060Service.selectEcStdDispMappingCnt(paramMap);
		
		//data list
		List<DataMap> list = null;
		
		//data list exist
		if(totalCount > 0) {
			list = nEDMPRO0060Service.selectEcStdDispMappingList(paramMap);
			
			totalPage = (int) Math.ceil((double) totalCount / rowPerPage);
		}
		
		gridMap.put("page", currentPage);		//현재 페이지
		gridMap.put("total", totalPage);		//전체 페이지 수
		gridMap.put("records", totalCount);		//전체 데이터 수
		gridMap.put("list", list);				//데이터 리스트

		return gridMap;
	}
	
	/**
	 * EC 카테고리 상품속성 조회 jqGrid
	 * @param request
	 * @return HashMap<String,Object>
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/edi/product/selectEcStdAttrMapping.do")
	public HashMap<String,Object> selectEcStdAttrMapping(HttpServletRequest request) throws Exception {
		HashMap<String,Object> gridMap = new HashMap<String, Object>();
		
		int totalCount = 0;		//전체 페이지 카운트
		int totalPage = 1;		//전체 페이지
		
		DataMap paramMap = new DataMap(request);
		int currentPage = paramMap.getInt("page");	//현재 페이지
		int rowPerPage = paramMap.getInt("rows");	//페이지당 행 수
		rowPerPage = (rowPerPage == 0)? config.getInt("count.row.per.page"):rowPerPage;	//페이지당 행 수 없으면 default setting
		
		int startRow =  (currentPage - 1) * rowPerPage + 1;
		int endRow = startRow + rowPerPage - 1;
		
		
		paramMap.put("startRow", startRow);
		paramMap.put("endRow", endRow);
		
		//data list count
		totalCount = nEDMPRO0060Service.selectEcStdAttrMappingCnt(paramMap);
		
		//data list
		List<DataMap> list = null;
		
		//data list exist
		if(totalCount > 0) {
			list = nEDMPRO0060Service.selectEcStdAttrMappingList(paramMap);
			
			totalPage = (int) Math.ceil((double) totalCount / rowPerPage);
		}
		
		gridMap.put("page", currentPage);		//현재 페이지
		gridMap.put("total", totalPage);		//전체 페이지 수
		gridMap.put("records", totalCount);		//전체 데이터 수
		gridMap.put("list", list);				//데이터 리스트

		return gridMap;
	}

	private List<String> unzip(File zipFile, File targetDir) throws Exception {

		List<String> unZipResult = new ArrayList<String>();

		FileInputStream fis = null;
		ZipInputStream zis = null;
		ZipEntry zentry = null;
		boolean unNext = true;

		int limitSize = 1024 * 600;
		int minSize = 500;
		int maxSize = 1500;
		int zenErrCnt = 0;

		String msgWrn = "";

		try {
			fis = new FileInputStream(zipFile); // FileInputStream
			zis = new ZipInputStream(fis); // ZipInputStream

            /* 2019.06.20
             * while 문 변경
             * 변경전 while ((zentry = zis.getNextEntry()) != null)
             * 2019.07.04
             * 파일 용량 제한 및 사이즈 검증 로직 추가
             * 2020.10.20
             * 파일 용량 제한 변경 , 최대사이즈 변경
             * */
			while (unNext) {
				zentry = zis.getNextEntry();
				if (zentry != null) {
					String fileNameToUnzip = zentry.getName();
					File targetFile = new File(targetDir, fileNameToUnzip);

					unzipEntry(zis, targetFile);

					BufferedImage image = ImageIO.read(targetFile);

					Integer width = image.getWidth();
					Integer height = image.getHeight();

					if (targetFile.exists()) {
						long iFileSize = targetFile.length();

						if (iFileSize > limitSize) {
							msgWrn = "600KB이하의 이미지만 업로드 가능합니다.";
							logger.info("limitSize message --> " + msgWrn);
						} else if ((width - height != 0) || (width < minSize || height < minSize) || (width > maxSize || height > maxSize)) {
							msgWrn = "사이즈는 정사이즈 비율 500px 이상 1500px 이하입니다.";
							logger.info("imageSize message --> " + msgWrn);
						}

						if (!"".equals(msgWrn)) {
							zenErrCnt++;
							unZipResult.add(Integer.toString(zenErrCnt) + "|" + zentry.getName() + "|" + msgWrn);
							msgWrn = "";
						}
					}

				} else {
					unNext = false;
					break;
				}
			}

		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			unZipResult.add("fail");
		} finally {
			if (zis != null) {
				zis.close();
			}
			if (fis != null) {
				fis.close();
			}
		}

		return unZipResult;
	}

	protected static File unzipEntry(ZipInputStream zis, File targetFile) throws Exception {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(targetFile);

			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			boolean proc = true;

			/* 2019.06.20
			 * while 문 변경
			 * 변경전 while ((len = zis.read(buffer)) != -1)
			 * */
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

	private String tpcCode(String majorCd) throws Exception {
		String cellVal = "";

		HashBox hParam = new HashBox();

		hParam.put("parentCodeId", majorCd);
		hParam.put("orderSeqYn", "Y");

		List<HashBox> codeList = customTagDao.getCode(hParam);

		for (int k = 0; k < codeList.size(); k++) {
			HashBox boxVal = codeList.get(k);
			cellVal += boxVal.getString("CODE_ID") + " : " + boxVal.getString("CODE_NAME") + "\n";
		}

		return cellVal;
	}

	private String productDescrHtml(String imgUrl) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("<html xml:lang=\"ko\" lang=\"ko\">");
		sb.append("<head>");
		sb.append("<title>제목없음</title>");
		sb.append("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">");
		sb.append("<meta http-equiv=\"Content-type\" content=\"text/html; charset=utf-8\">");
		sb.append("<style type=\"text/css\"> body{font-family :굴림; color : #000000; font-size : 10pt;  margin : 0 0 0 3px;} p,li{line-height:1.2; margin-top:0; margin-bottom:0;}");
		sb.append("</style>");
		sb.append("</head>");
		sb.append("<body>");
		sb.append("<p><img title=\"\" style=\"border: 0px solid rgb(0, 0, 0); border-image: none; vertical-align: baseline;\" alt=\"\" src=\"" + imgUrl + "\"></p>");
		sb.append("</body>");
		sb.append("</html>");

		return sb.toString();
	}

	/*
	 * 카테고리 중복 체크
	 */
	private int categoryOverLapChk(String[] strChk) throws Exception {

		int chkCnt = 0;

		TreeSet<String> treeSet = new TreeSet<String>();
		for (int i = 0; i < strChk.length; i++) {
			treeSet.add(strChk[i].trim());
		}

		Iterator<String> it = treeSet.iterator();
		while (it.hasNext()) {
			it.next();
			chkCnt++;
		}

		return chkCnt;
	}

}
