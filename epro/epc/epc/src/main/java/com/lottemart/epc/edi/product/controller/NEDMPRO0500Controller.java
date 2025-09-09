package com.lottemart.epc.edi.product.controller;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.common.exception.TopLevelException;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.product.model.NEDMPRO0500VO;
import com.lottemart.epc.edi.product.service.CommonProductService;
import com.lottemart.epc.edi.product.service.NEDMPRO0500Service;

import lcn.module.framework.property.ConfigurationService;

/**
 * @Class Name : NEDMPRO0500Controller
 * @Description : 원가변경요청 Controller
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2025.03.17		yun				최초생성
 * </pre>
 */

@Controller
public class NEDMPRO0500Controller extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0500Controller.class);
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	@Resource(name = "nEDMPRO0500Service")
	private NEDMPRO0500Service nEDMPRO0500Service;
	
	@Resource(name = "commonProductService")
	private CommonProductService commonProductService;
	
	/**
	 * 원가변경요청 init
	 * @param model
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/NEDMPRO0500.do")
	public String NEDMPRO0500Init(ModelMap model, HttpServletRequest request, NEDMPRO0500VO paramVo) throws Exception {
		if (model == null || request == null) {
			throw new TopLevelException("");
		}
		

		Map<String, Object>	paramMap	=	new HashMap<String, Object>();
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		// html 코드 태그 한번에 조회 
		paramMap.put("parentCodeId", "CORSD");
		List<HashMap<String, Object>> optList = commonProductService.selectCodeTagList(paramMap);
		
		ObjectMapper mapper = new ObjectMapper();
		String optionListJson = mapper.writeValueAsString(optList); // 이걸 model에 담기
		
		model.addAttribute("optionList", optionListJson);
		model.addAttribute("epcLoginVO", 		epcLoginVO);
		model.addAttribute("paramVo", 		paramVo);
		
		return "edi/product/NEDMPRO0500";
	}
	
	/**
	 * 원가변경 상세정보 조회
	 * @param nEDMPRO0500VO
	 * @param request
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/selectTpcProdChgCostDetail.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> selectTpcProdChgCostDetail(@RequestBody NEDMPRO0500VO nEDMPRO0500VO, HttpServletRequest request) throws Exception {
		return nEDMPRO0500Service.selectTpcProdChgCostDetail(nEDMPRO0500VO, request);
	}
	
	/**
	 * 원가변경요청 정보 저장
	 * @param nEDMPRO0500VO
	 * @param request
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/insertTpcProdChgCostInfo.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> insertTpcProdChgCostInfo(@RequestBody NEDMPRO0500VO nEDMPRO0500VO, HttpServletRequest request) throws Exception {
		return nEDMPRO0500Service.insertTpcProdChgCostInfo(nEDMPRO0500VO, request);
	}
	
	/**
	 * 원가변경요청 아이템 정보 삭제
	 * @param nEDMPRO0500VO
	 * @param request
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/deleteTpcProdChgCostItem.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> deleteTpcProdChgCostItem(@RequestBody NEDMPRO0500VO nEDMPRO0500VO, HttpServletRequest request) throws Exception {
		return nEDMPRO0500Service.deleteTpcProdChgCostItem(nEDMPRO0500VO, request);
	}
	
	/**
	 * 원가변경요청정보 판매코드 선택 가능 여부 확인
	 * @param nEDMPRO0500VO
	 * @param request
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/selectCheckProdChgCostSelOkStatus.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> selectCheckProdChgCostSelOkStatus(@RequestBody NEDMPRO0500VO nEDMPRO0500VO, HttpServletRequest request) throws Exception {
		return nEDMPRO0500Service.selectCheckProdChgCostSelOkStatus(nEDMPRO0500VO, request);
	}

	/**
	 * 원가변경요청정보 공문생성
	 * @param nEDMPRO0500VO
	 * @param request
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/insertCreDcDocProChgCost.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> insertCreDcDocProChgCost(@RequestBody NEDMPRO0500VO nEDMPRO0500VO, HttpServletRequest request) throws Exception {
		return nEDMPRO0500Service.insertCreDcDocProChgCost(nEDMPRO0500VO, request);
	}
	
	/**
	 * 원가변경요청 MD협의요청
	 * @param nEDMPRO0500VO
	 * @param request
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/insertReqMdProChgCost.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> insertReqMdProChgCost(@RequestBody NEDMPRO0500VO nEDMPRO0500VO, HttpServletRequest request) throws Exception {
		return nEDMPRO0500Service.insertReqMdProChgCost(nEDMPRO0500VO, request);
	}
	

	/**
	 * 엑셀 양식 다운로드 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/selectTpcProdChgCostDetailExcelDown")
	public void selectTpcProdChgCostDetailExcelDown(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataMap paramMap = new DataMap(request);

		response.setContentType("application/x-msdownload;charset=UTF-8");
		// 양식의 value 값
		String optionVal = request.getParameter("optionVal");
		String excelTmpCd = request.getParameter("hiddenExcelTmpCd");

		String userAgent = request.getHeader("User-Agent");
		
		//jsp에서 가져온 파일명 
		String fileNmVal = URLDecoder.decode(request.getParameter("fileName"), "UTF-8");
		
		String fileName = "";
		
		fileName = fileNmVal + "_양식.xlsx";
		
		fileName = URLEncoder.encode(fileName, "UTF-8");
		
		// 디폴트값 맵핑 
		String purDept = request.getParameter("hiddenSrchPurDept");
		String venCd = request.getParameter("hiddenSrchVenCd");
		String nbPbgbn = request.getParameter("hiddenSrchNbPbGbn");

		if (userAgent.indexOf("MSIE 5.5") > -1) {
			response.setHeader("Content-Disposition", "filename=" + fileName + ";");
		} else {
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ";");
		}

		// StringBuffer sb = new StringBuffer();

		String[] newHeaders = new String[1];
		int headerHeight = 0;
		
		// 엑셀양식 헤더 부분 
//		String[] headers = { "판매코드","상품코드","표시단위","변경원가","변경사유","변경상세사유","비고","원가변경요청일" };  
		String[] headers = {"*판매코드", "변경원가", "변경사유", "변경상세사유", "원가변경요청일", "비고"};
		newHeaders = headers;
		headerHeight = 1200;
		
		// 헤더출력
		int headerLength = newHeaders.length;
		
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		CreationHelper createHelper = workbook.getCreationHelper();
		XSSFSheet sheet = workbook.createSheet(fileNmVal + " 양식");
		
		XSSFRow header1 = sheet.createRow(0);
		XSSFCellStyle styleHd = workbook.createCellStyle();
		XSSFCellStyle styleHd2 = workbook.createCellStyle();
		XSSFCellStyle styleHd3 = workbook.createCellStyle();
		XSSFCellStyle styleRow = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		XSSFFont wrnfont = workbook.createFont();
		XSSFCell cell = null;
		
//		sheet.addMergedRegion(new CellRangeAddress((int) 0, (short) 0, (int) 0, (short) 7));
		sheet.addMergedRegion(new CellRangeAddress((int) 0, (short) 0, (int) 0, (short) headerLength-1));
		
		font.setFontHeight((short) 200);
		styleHd.setFont(font);
		styleHd.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		styleHd.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		styleHd.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		styleHd.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		styleHd.setBorderTop(XSSFCellStyle.BORDER_THIN);
		styleHd.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		styleHd.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		styleHd.setBorderRight(XSSFCellStyle.BORDER_THIN);

		wrnfont.setFontHeight((short) 170);
		wrnfont.setColor(wrnfont.COLOR_RED);
		styleHd2.setFont(wrnfont);
		styleHd2.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		styleHd2.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		styleHd2.setWrapText(true);

		styleHd3.setFont(wrnfont);
		styleHd3.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		styleHd3.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		styleHd3.setWrapText(true);

		styleRow.setWrapText(true);
		styleRow.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		styleRow.setVerticalAlignment(XSSFCellStyle.VERTICAL_TOP);
		styleRow.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		styleRow.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND); 
		styleRow.setBorderTop(XSSFCellStyle.BORDER_THIN);
		styleRow.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		styleRow.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		styleRow.setBorderRight(XSSFCellStyle.BORDER_THIN);
		
		
		
		//엑셀 헤더 위 안내 문구 설정
		String infoVal = "※ * 표시된 항목은 필수 입력입니다. 미입력시 일괄등록이 되지 않으니 유의하시기 바랍니다.\n"
		        + "※ 변경사유: 01(인상), 02(인하)\n"
		        + "※ 상세사유: 인상-01(원부자재상승), 03(경비상승) / 인하-02(원부자재하락), 04(생산비절감), 05(판매량저하)\n"
		        + "※ 변경사유와 상세사유는 코드값으로 입력 바랍니다. 코드값 외 입력 시 일괄등록이 되지 않으니 유의하시기 바랍니다.";
		
		
		//헤더에 문구 적용 및 스타일 저용 
		header1.createCell(0).setCellValue(infoVal);
		header1.getCell(0).setCellStyle(styleHd2);
		header1.setHeight((short) headerHeight);
		
		XSSFRow header2 = sheet.createRow(1);
		
		for (int i = 0; i < headerLength; i++) {
			// 컬럼 제목들 설정해줌 두번째 줄부터  header2 가 두번째 로우임 위에 sheet.createRow(1) 이거로 보면 인덱스 1임  
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

		/* 셀(cols) 갯수 확인 */
		Row rows = sheet.getRow(1);
		int cellCnt = (int) rows.getLastCellNum();
		/* 셀(cols) 갯수 확인 */
		
		for (int j = 0; j < cellCnt; j++) {
			sheet.setDefaultColumnStyle(j, cellStyle);
		}
		
		
		// 헤더 아래 첫 번째 데이터 행 추가
//		XSSFRow dataRow = sheet.createRow(2); // 헤더가 2줄 이므로 인덱스 2 부터 시작(3번째줄)
//
//		// 디폴트값 셀에 입력 (구매조직, 파트너사, NB/PB 순서)
//		cell = dataRow.createCell(0); // "* 구매조직"
//		cell.setCellValue(purDept);
//		cell.setCellStyle(styleRow);
//		cell.setCellType(1);
//
//		cell = dataRow.createCell(1); // "* 파트너사"
//		cell.setCellValue(venCd);
//		cell.setCellStyle(styleRow);
//		cell.setCellType(1);
//		
//		cell = dataRow.createCell(2); // "* NB/PB"
//		cell.setCellValue(nbPbgbn);
//		cell.setCellStyle(styleRow);
//		cell.setCellType(1);
		
		ServletOutputStream out = response.getOutputStream();
		workbook.write(out);
		out.flush();

	}
	
}
