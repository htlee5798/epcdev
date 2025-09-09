package com.lottemart.epc.edi.srm.controller;

import com.lottemart.epc.edi.srm.model.SRMEVL0060VO;
import com.lottemart.epc.edi.srm.service.SRMEVL0060Service;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 품질경영평가 / 품질경영평가 대상 Controller
 *
 * @author Lee Hyoung Tak
 * @since 2016.10.07
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.10.07  	Lee Hyoung Tak		 최초 생성
 *
 * </pre>
 */
@Controller
public class SRMEVL0060Controller {

	private static final Logger logger = LoggerFactory.getLogger(SRMEVL0060Controller.class);

	@Autowired
	private SRMEVL0060Service srmevl0060Service;

	@Autowired
	private MessageSource messageSource;

	/**
	 * 품질경영평가 대상 화면 초기화
	 * @return
	 */
	@RequestMapping(value = "/edi/evl/SRMEVL0060.do")
	public String SRMEVL0030() throws Exception {
		return "/edi/srm/SRMEVL0060";
	}

	/**
	 * 품질경영평가 LIST 조회
	 * @param SRMEVL0030VO
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/evl/selectQualityEvaluationPeriodicList.json", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> selectQualityEvaluationPeriodicList(@RequestBody SRMEVL0060VO vo, HttpServletRequest request) throws Exception {
		return srmevl0060Service.selectQualityEvaluationPeriodicList(vo, request);
	}

	/**
	 * 품질경영평가 엑셀 다운로드
	 * @param Model
	 * @param SRMEVL0030VO
	 * @param HttpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/evl/selectQualityEvaluationPeriodicListExcel.do")
	public void selectQualityEvaluationPeriodicListExcel(Model model, SRMEVL0060VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		model.addAttribute("list", srmevl0060Service.selectQualityEvaluationListExcel(vo, request));

		response.setContentType("application/x-msdownload;charset=UTF-8");

		String userAgent = request.getHeader("User-Agent");
		String fileName = messageSource.getMessage("text.srm.field.srmevl0030.title", null, Locale.getDefault()).replaceAll(" ","") + ".xls";



		if (userAgent.indexOf("MSIE 5.5") > -1) {
			response.setHeader("Content-Disposition", "filename=" + fileName + ";");
		} else if (userAgent.indexOf("MSIE") > -1) {
			response.setHeader("Content-Disposition", "attachment;fileName=\""+ URLEncoder.encode(fileName, "UTF-8") + "\";");
		} else {
			response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("euc-kr"), "latin1") + ";");
		}


		// create a wordsheet
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFCell headCell = null;
		HSSFCell cell0 = null;
		HSSFCell cell1 = null;
		HSSFCell cell2 = null;
//		HSSFCell cell3 = null;
		HSSFCell cell4 = null;
		HSSFCell cell5 = null;
		HSSFCell cell6 = null;
		HSSFCell cell7 = null;


		List<SRMEVL0060VO> resultItemList= srmevl0060Service.selectQualityEvaluationListExcel(vo, request);

		// 헤더출력
		String[] headers = { messageSource.getMessage("table.srm.colum.title.no", null, Locale.getDefault())
							, messageSource.getMessage("table.srm.srmevl0030.colum.title2", null, Locale.getDefault())
							, messageSource.getMessage("table.srm.srmevl0030.colum.title3", null, Locale.getDefault())
//							, messageSource.getMessage("table.srm.srmevl0030.colum.title4", null, Locale.getDefault())
							, messageSource.getMessage("table.srm.srmevl0030.colum.title5", null, Locale.getDefault())
							, messageSource.getMessage("table.srm.srmevl0030.colum.title6", null, Locale.getDefault())
							, messageSource.getMessage("table.srm.srmevl0030.colum.title7", null, Locale.getDefault())
							, messageSource.getMessage("table.srm.srmevl0030.colum.title8", null, Locale.getDefault())};

		int headerLength = headers.length;

//		CreationHelper createHelper = workbook.getCreationHelper();

		String sheetName = messageSource.getMessage("text.srm.field.srmevl0030.title", null, Locale.getDefault()).replaceAll(" ","");

		HSSFSheet sheet = workbook.createSheet(sheetName);
		HSSFRow header = sheet.createRow(0);
		CellStyle headerCellDateStyle = workbook.createCellStyle();

		headerCellDateStyle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
		headerCellDateStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		headerCellDateStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerCellDateStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		headerCellDateStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		headerCellDateStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		headerCellDateStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		headerCellDateStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);

		HSSFCellStyle cellStyle1 = workbook.createCellStyle();
		cellStyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle1.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle1.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyle1.setWrapText(true);

		HSSFCellStyle cellStyle2 = workbook.createCellStyle();
		cellStyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle2.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
		cellStyle2.setWrapText(true);


		for (int k = 0; k < headerLength; k++) {
			headCell = header.createCell(k);
			headCell.setCellStyle(headerCellDateStyle);
			headCell.setCellValue(headers[k]);
		}

		//data 출력
		Iterator iter = resultItemList.iterator();
		int no = 1;
		while (iter.hasNext()) {
			SRMEVL0060VO item = (SRMEVL0060VO) iter.next();
			if (item == null) {
				continue;
			}

			HSSFRow row = sheet.createRow(no);
			//셀 width
			sheet.setColumnWidth(0,2000);
			sheet.setColumnWidth(1,5000);
			sheet.setColumnWidth(2,4000);
//			sheet.setColumnWidth(3,5000);
			sheet.setColumnWidth(3,4000);
			sheet.setColumnWidth(4,4000);
			sheet.setColumnWidth(5,7000);
			sheet.setColumnWidth(6,5000);

			//셀생성
			cell0 = row.createCell(0);
			cell1 = row.createCell(1);
			cell2 = row.createCell(2);
//			cell3 = row.createCell(3);
			cell4 = row.createCell(3);
			cell5 = row.createCell(4);
			cell6 = row.createCell(5);
			cell7 = row.createCell(6);

			//스타일 적용
			cell0.setCellStyle(cellStyle1);
			cell1.setCellStyle(cellStyle1);
			cell2.setCellStyle(cellStyle2);
//			cell3.setCellStyle(cellStyle1);
			cell4.setCellStyle(cellStyle1);
			cell5.setCellStyle(cellStyle1);
			cell6.setCellStyle(cellStyle1);
			cell7.setCellStyle(cellStyle1);

			//값 적용
			cell0.setCellValue(no);
			cell1.setCellValue(item.getEvalNoResult() + "-" + item.getVisitSeq() );
			cell2.setCellValue(item.getSellerNameLoc());
//			cell3.setCellValue(item.getCatLv1CodeName());
			cell4.setCellValue(item.getReqDate());
			cell5.setCellValue(item.getReceiptDate());
			cell6.setCellValue(item.getChangeDate());

			if(item.getProgressCode().equals("100")) {
				cell7.setCellValue(messageSource.getMessage("text.srm.search.field.status.option1", null, Locale.getDefault()));
			} else if(item.getProgressCode().equals("200")) {
				cell7.setCellValue(messageSource.getMessage("text.srm.search.field.status.option2", null, Locale.getDefault()));
			} else if(item.getProgressCode().equals("300")) {
				cell7.setCellValue(messageSource.getMessage("text.srm.search.field.status.option3", null, Locale.getDefault()));
			} else {
				cell7.setCellValue("");
			}

			no++;
		}

		ServletOutputStream out = response.getOutputStream();
		try {
			workbook.write(out);
			out.flush();
		} catch (Exception e) {
			logger.debug(e.toString());
		} finally {
			if (out != null) {
				try {
					out.close();
			} catch (Exception e) {
				logger.debug("error : " + e.getMessage());
				}
			}
		}

	}

	/**
	 * 품질경영평가 업체정보/접수 팝업
	 * @param Model
	 * @param SRMEVL0030VO
	 * @return
	 * @throws Exception
     */
	@RequestMapping(value = "/edi/evl/receiptPeriodicPopup.do")
	public String receiptPopup(Model model, SRMEVL0060VO vo, HttpServletRequest request) throws Exception {
		model.addAttribute("vo", vo);
		model.addAttribute("srmComp", srmevl0060Service.selectQualityEvaluationCompInfo(vo, request));
		return "/edi/srm/SRMEVL006001";
	}

}
