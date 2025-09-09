package com.lottemart.epc.edi.srm.controller;

import com.lcnjf.util.StringUtil;
import com.lottemart.epc.edi.srm.model.SRMEVL0040ListVO;
import com.lottemart.epc.edi.srm.model.SRMEVL0040VO;
import com.lottemart.epc.edi.srm.service.SRMEVL0040Service;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
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
import java.util.*;


/**
 * 품질경영평가 / 품질평가 / 품질평가등록[회원사 기본정보,법규사항]
 *
 * @author LEE HYOUNG TAK
 * @since 2016.07.08
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ---------------------------
 *   2016.07.08  	LEE HYOUNG TAK				 최초 생성
 *
 * </pre>
 */
@Controller
public class SRMEVL0040Controller {

	@Autowired
	private SRMEVL0040Service srmevl0040Service;

	@Autowired
	private MessageSource messageSource;

	private static final Logger logger = LoggerFactory.getLogger(SRMEVL0040Controller.class);

	/**
	 * 품질평가등록[회원사 기본정보,법규사항] 화면 초기화
	 * @return
	 */
	@RequestMapping(value = "/edi/evl/SRMEVL0040.do")
	public String init(SRMEVL0040VO vo, Model model, HttpServletRequest request) throws Exception {
		// Tab List 가져오기
		List<SRMEVL0040VO> tabList = srmevl0040Service.selectEvlTabList(vo, request);

		model.addAttribute("tabList", tabList);

		// 활성화 Tab 정보가 없는 경우 Default '1' 설정
		if (StringUtil.null2str(vo.getActiveTab()).equals("")) {
			vo.setActiveTab("1");
		}

		//----- 품질경영평가 대분류가 있으면 첫번째 대분류의 Item을 가져온다 Start --------------------
		logger.debug("----->" + tabList.size());
		List<SRMEVL0040VO> itemList = null;
		if (tabList != null && tabList.size() > 0) {
			SRMEVL0040VO itemVO = new SRMEVL0040VO();
			itemVO = tabList.get(Integer.parseInt(vo.getActiveTab()) - 1);	// 배열은 0부터 시작하므로 -1을 함
			itemVO.setEvNo(vo.getEvNo());
			itemList = srmevl0040Service.selectEvlItem(itemVO, request);

			vo.setEvItemType1Code(itemVO.getEvItemType1Code());
			vo.setEvItemType2Code(itemVO.getEvItemType2Code());
			vo.setEvItemType3Code(itemVO.getEvItemType3Code());
		}

		model.addAttribute("itemList", itemList);
		model.addAttribute("vo", vo);
		//--------------------------------------------------

		return "/edi/srm/SRMEVL0040";
	}


	/**
	 * 품질경영평가 LIST 조회
	 * @param SRMEVL0040ListVO
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/evl/insertQualityEvaluation.json", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> insertQualityEvaluation(@RequestBody SRMEVL0040ListVO listVo, HttpServletRequest request) throws Exception {
		return srmevl0040Service.insertQualityEvaluation(listVo, request);
	}


	/**
	 * 품질평가 모두 입력 여부 Check
	 * @param SRMEVL0040VO
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/evl/selectQualityEvaluationEvalCheck.json", method = RequestMethod.POST)
	public @ResponseBody int selectQualityEvaluationEvalCheck(@RequestBody SRMEVL0040VO vo, HttpServletRequest request) throws Exception {
		return srmevl0040Service.selectQualityEvaluationEvalCheck(vo, request);
	}


	/**
	 * 품질경영평가 엑셀 다운로드
	 * @param Model
	 * @param SRMEVL0030VO
	 * @param HttpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/evl/selectQualityEvaluationItemListExcel.do")
	public void selectQualityEvaluationItemListExcel(SRMEVL0040VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

		response.setContentType("application/x-msdownload;charset=UTF-8");

		String userAgent = request.getHeader("User-Agent");

		String fileName = messageSource.getMessage("text.srm.field.srmevl0040.title", null, Locale.getDefault()) + ".xls";

		if (userAgent.indexOf("MSIE 5.5") > -1) {
			response.setHeader("Content-Disposition", "filename=" + fileName + ";");
		} else if (userAgent.indexOf("MSIE") > -1) {
			response.setHeader("Content-Disposition", "attachment;fileName=\""+ URLEncoder.encode(fileName, "UTF-8") + "\";");
		} else {
			response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("euc-kr"), "latin1") + ";");
		}

		// List 가져오기
		List<SRMEVL0040VO>	resultTabList = srmevl0040Service.selectEvlTabList(vo, request);

		// create a wordsheet
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFCell warningCell = null;
		HSSFCell headCell = null;
		HSSFCell cell0 = null;
		HSSFCell cell1 = null;
		HSSFCell cell2 = null;
		HSSFCell cell3 = null;
		HSSFCell cell4 = null;
		HSSFCell cell5 = null;



		for(int i=0; i <resultTabList.size(); i++) {
			SRMEVL0040VO tabVo = resultTabList.get(i);
			List<SRMEVL0040VO>	resultItemList= srmevl0040Service.selectEvlItem(tabVo, request);
			SRMEVL0040VO itemVo = resultItemList.get(0);

			// 헤더출력
			String[] headers = { messageSource.getMessage("table.srm.colum.title.no", null, Locale.getDefault())
								, messageSource.getMessage("table.srm.srmevl0040.colum.title2", null, Locale.getDefault())
								, messageSource.getMessage("table.srm.srmevl0040.colum.title3", null, Locale.getDefault())
								, messageSource.getMessage("table.srm.srmevl0040.colum.title4", null, Locale.getDefault())
								, messageSource.getMessage("table.srm.srmevl0040.colum.title5", null, Locale.getDefault())
								, messageSource.getMessage("table.srm.srmevl0040.colum.title6", null, Locale.getDefault())};

			int headerLength = headers.length;

//			CreationHelper createHelper = workbook.getCreationHelper();

			String sheetName = "";
			sheetName = itemVo.getEvItemType1CodeName();

			//특수문자 처리
			sheetName = sheetName.replaceAll("\"", "|");
			sheetName = sheetName.replaceAll("/", "|");

			HSSFSheet sheet = workbook.createSheet(sheetName);

			//경고문구
			HSSFRow warning = sheet.createRow(0);
			//시트 보호
			sheet.protectSheet("lotteemartSrm");

			warning.setHeight((short) 800);

			HSSFFont fontRed = workbook.createFont();
			fontRed.setColor(Font.COLOR_RED);

			CellStyle warningCellStyle = workbook.createCellStyle();
			warningCellStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
			warningCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			warningCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			warningCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			warningCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			warningCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			warningCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			warningCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			warningCellStyle.setFont(fontRed);
			warningCellStyle.setWrapText(true);

			for (int k = 0; k <= 13; k++) {
				warningCell = warning.createCell(k);
				warningCell.setCellStyle(warningCellStyle);
				if(k == 0 || k == 8) {
					warningCell.setCellValue("문서를 임의로 변경시 정상적인 파일 업로드가 \n이루어지지 않을 수 있습니다. \n선택점수는 대소문자 구분없이 O(영문자)를 평가항목당 1개만 입력해주세요. ");
				}
			}

			sheet.addMergedRegion(new CellRangeAddress(0, 0 , 0, 7));
			sheet.addMergedRegion(new CellRangeAddress(0, 0 , 8, 13));

			//해더
			HSSFRow header = sheet.createRow(1);
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

			HSSFCellStyle cellStyle3 = workbook.createCellStyle();
			cellStyle3.setBorderRight(HSSFCellStyle.BORDER_THIN);
			cellStyle3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			cellStyle3.setBorderTop(HSSFCellStyle.BORDER_THIN);
			cellStyle3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			cellStyle3.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
			cellStyle3.setWrapText(true);
			cellStyle3.setLocked(false);


			HSSFCellStyle hideCellStyle = workbook.createCellStyle();
			hideCellStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
			hideCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			hideCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			hideCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			hideCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			hideCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			hideCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			hideCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			hideCellStyle.setFont(fontRed);
			hideCellStyle.setWrapText(true);


			for (int k = 0; k < 8; k++) {
				headCell = header.createCell(k);
				headCell.setCellStyle(hideCellStyle);
			}
			for (int k = 8; k < headerLength+8; k++) {
				headCell = header.createCell(k);
				headCell.setCellStyle(headerCellDateStyle);
				headCell.setCellValue(headers[k-8]);
			}

			//data 출력
			Iterator iter = resultItemList.iterator();
			int rowNum = 2;
			int no = 1;
			int rowspan = 2;
			int subRowspan = 2;
			String beforeEvItemNo = "";
			String beforeEvItemType2CodeName = "";
			while (iter.hasNext()) {
				SRMEVL0040VO item = (SRMEVL0040VO) iter.next();
				if (item == null) {
					continue;
				}

				HSSFRow row = sheet.createRow(rowNum++);

				//해당 평가 키값
				HSSFCell houseCode = row.createCell(0);
				HSSFCell sgNo = row.createCell(1);
				HSSFCell evNo = row.createCell(2);
				HSSFCell vendorCode = row.createCell(3);
				HSSFCell evTplNo = row.createCell(4);
				HSSFCell evItemNo = row.createCell(5);
				HSSFCell evIdSeq = row.createCell(6);
				HSSFCell evIdScoreVal = row.createCell(7);

				houseCode.setCellStyle(hideCellStyle);
				sgNo.setCellStyle(hideCellStyle);
				evNo.setCellStyle(hideCellStyle);
				vendorCode.setCellStyle(hideCellStyle);
				evTplNo.setCellStyle(hideCellStyle);
				evItemNo.setCellStyle(hideCellStyle);
				evIdSeq.setCellStyle(hideCellStyle);
				evIdScoreVal.setCellStyle(hideCellStyle);

				houseCode.setCellValue(vo.getHouseCode());
				sgNo.setCellValue(vo.getSgNo());
				evNo.setCellValue(vo.getEvNo());
				vendorCode.setCellValue(vo.getVendorCode());
				evTplNo.setCellValue(item.getEvTplNo());
				evItemNo.setCellValue(item.getEvItemNo());
				evIdSeq.setCellValue(item.getEvIdSeq());
				evIdScoreVal.setCellValue(item.getEvIdScore());

				//셀 width
				sheet.setColumnWidth(0,2000);
				sheet.setColumnWidth(1,2000);
				sheet.setColumnWidth(2,5000);
				sheet.setColumnWidth(3,5000);
				sheet.setColumnWidth(4,5000);
				sheet.setColumnWidth(5,5000);
				sheet.setColumnWidth(6,2000);
				sheet.setColumnWidth(7,2000);

				sheet.setColumnWidth(8,2000);
				sheet.setColumnWidth(9,5000);
				sheet.setColumnWidth(10,2000);
				sheet.setColumnWidth(11,10000);
				sheet.setColumnWidth(12,4000);
				sheet.setColumnWidth(13,4000);

				//셀생성
				cell0 = row.createCell(8);
				cell1 = row.createCell(9);
				cell2 = row.createCell(10);
				cell3 = row.createCell(11);
				cell4 = row.createCell(12);
				cell5 = row.createCell(13);

				//스타일 적용
				cell0.setCellStyle(cellStyle1);
				cell1.setCellStyle(cellStyle1);
				cell2.setCellStyle(cellStyle1);
				cell3.setCellStyle(cellStyle2);
				cell4.setCellStyle(cellStyle1);
				cell5.setCellStyle(cellStyle1);

				if(!beforeEvItemNo.equals(item.getEvItemNo())) {

					//값 적용
					cell0.setCellValue(no);
					sheet.addMergedRegion(new CellRangeAddress(subRowspan, subRowspan + Integer.parseInt(item.getSubRowSpan())-1, 8, 8));
					if(!beforeEvItemType2CodeName.equals(item.getEvItemType2CodeName())) {
						cell1.setCellValue(item.getEvItemType2CodeName());
						sheet.addMergedRegion(new CellRangeAddress(rowspan, rowspan + Integer.parseInt(item.getRowSpan())-1, 9, 9));
						rowspan = rowspan + Integer.parseInt(item.getRowSpan());
					}
					cell2.setCellValue(item.getEvIdScore());
					cell3.setCellValue(item.getEvItemContents());


					sheet.addMergedRegion(new CellRangeAddress(subRowspan, subRowspan + Integer.parseInt(item.getSubRowSpan())-1, 10, 10));
					sheet.addMergedRegion(new CellRangeAddress(subRowspan, subRowspan + Integer.parseInt(item.getSubRowSpan())-1, 11, 11));

					no++;
					subRowspan = subRowspan + Integer.parseInt(item.getSubRowSpan());
				}
				cell4.setCellStyle(cellStyle2);
				cell4.setCellValue(item.getEvIdContents());
				cell5.setCellStyle(cellStyle3);
				cell5.setCellValue("");

				beforeEvItemNo = item.getEvItemNo();
				beforeEvItemType2CodeName = item.getEvItemType2CodeName();
			}
			//inset시 필요값 HIDE
			sheet.setColumnHidden(0, true);
			sheet.setColumnHidden(1, true);
			sheet.setColumnHidden(2, true);
			sheet.setColumnHidden(3, true);
			sheet.setColumnHidden(4, true);
			sheet.setColumnHidden(5, true);
			sheet.setColumnHidden(6, true);
			sheet.setColumnHidden(7, true);
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
	 * 가이드 라인 상세보기 팝업
	 * @return
	 */
	@RequestMapping(value = "/edi/evl/SRMEVLselectQualityEvaluationItemListExcelUpload.do")
	public String selectQualityEvaluationItemListExcelUpload(Model model, SRMEVL0040VO vo, HttpServletRequest request) throws Exception {

		HashMap<String,Object> resultMap = srmevl0040Service.selectQualityEvaluationItemListExcelUpload(vo, request);
		// Tab List 가져오기
		List<SRMEVL0040VO> tabList = srmevl0040Service.selectEvlTabList(vo, request);

		model.addAttribute("tabList", tabList);

		// 활성화 Tab 정보가 없는 경우 Default '1' 설정
		if (StringUtil.null2str(vo.getActiveTab()).equals("")) {
			vo.setActiveTab("1");
		}

		//----- 품질경영평가 대분류가 있으면 첫번째 대분류의 Item을 가져온다 Start --------------------
		logger.debug("----->" + tabList.size());
		List<SRMEVL0040VO> itemList = null;
		if (tabList != null && tabList.size() > 0) {
			SRMEVL0040VO itemVO = new SRMEVL0040VO();
			itemVO = tabList.get(Integer.parseInt(vo.getActiveTab()) - 1);	// 배열은 0부터 시작하므로 -1을 함
			itemVO.setEvNo(vo.getEvNo());
			itemList = srmevl0040Service.selectEvlItem(itemVO, request);

			vo.setEvItemType1Code(itemVO.getEvItemType1Code());
			vo.setEvItemType2Code(itemVO.getEvItemType2Code());
			vo.setEvItemType3Code(itemVO.getEvItemType3Code());
		}

		model.addAttribute("itemList", itemList);
		model.addAttribute("vo", vo);
		model.addAttribute("excelFileUpload", "Y");
		model.addAttribute("result", resultMap);

		//--------------------------------------------------

		return "/edi/srm/SRMEVL0040";
	}


	/**
	 * 가이드 라인 상세보기 팝업
	 * @return
	 */
	@RequestMapping(value = "/edi/evl/selectGuideLineDetailPopup.do")
	public String selectguideLineDetailPopup() throws Exception {
		return "/edi/srm/SRMEVL004001";
	}

}
