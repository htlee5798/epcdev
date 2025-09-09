package com.lottemart.epc.edi.srm.controller;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.srm.model.SRMVEN0010VO;
import com.lottemart.epc.edi.srm.service.SRMVEN0010Service;
import lcn.module.common.util.StringUtil;
import lcn.module.framework.property.ConfigurationService;
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

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;

/**
 * SRM > SRM정보 > 파트너사정보변경 Controller
 *
 * @author AN TAE KYUNG
 * @since 2016.07.29
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           			수정내용
 *  -----------    	------------    ---------------------------
 *   2016.07.29  	AN TAE KYUNG	최초 생성
 *
 * </pre>
 */
@Controller
public class SRMVEN0010Controller {

	private static final Logger logger = LoggerFactory.getLogger(SRMVEN0010Controller.class);

	@Resource(name = "configurationService")
	private ConfigurationService config;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private SRMVEN0010Service srmven0010Server;

	/**
	 * 파트너사정보변경 화면 초기화
	 * @return
	 */
	@RequestMapping(value = "/edi/ven/SRMVEN0010.do")
	public String init(Model model, HttpServletRequest request) throws Exception {
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		String ven = "";
		for (int i = 0; i < epcLoginVO.getVendorId().length; i++) {
			ven += epcLoginVO.getVendorId()[i]+",";
		}
		ven = ven.substring(0, ven.length()-1);
		model.addAttribute("ven", ven);

		return "/edi/srm/SRMVEN0010";
	}

	/**
	 * 파트너사 조회
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/ven/selectVendorList.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> selectVendorList(@RequestBody SRMVEN0010VO vo, HttpServletRequest request) throws Exception {
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		vo.setVenCds(epcLoginVO.getVendorId());

		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<SRMVEN0010VO> list = srmven0010Server.selectVendorList(vo);

		resultMap.put("vendorList", list);

		return resultMap;
	}

	/**
	 * 파트너사 Insert/Update
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/ven/insertVenInfo.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> insertVenInfo(@RequestBody SRMVEN0010VO vo, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		String retValue = srmven0010Server.insertVenInfo(vo, request);

		resultMap.put("retMsg", retValue);

		return resultMap;
	}

	/**
	 * 파트너사 수정정보 확정요청
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/ven/updateVenInfoConfirm.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> insertVenInfoConfirm(@RequestBody SRMVEN0010VO vo, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		String retValue = srmven0010Server.updateVenInfoConfirm(vo, request);

		resultMap.put("retMsg", retValue);

		return resultMap;
	}

	/**
	 * 파트너사 Delete
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/ven/deleteVenInfo.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> deleteVenInfo(@RequestBody SRMVEN0010VO vo, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		String retValue = srmven0010Server.deleteVenInfo(vo);

		resultMap.put("retMsg", retValue);

		return resultMap;
	}

	/**
	 * 선택한 파트너사 상세정보 팝업
	 * @return
	 */
	@RequestMapping(value = "/edi/ven/presidInfoPopup.do")
	public String presidInfoPopup() throws Exception {
		return "/edi/srm/SRMVEN001001";
	}

	/**
	 * 선택한 파트너사 리스트
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/ven/selectSrmVenList.json", method = RequestMethod.POST)
	public @ResponseBody List<SRMVEN0010VO> selectSrmVenInfoList(@RequestBody SRMVEN0010VO vo) throws Exception {
		return srmven0010Server.selectSrmVenList(vo);
	}

	/**
	 * 파트너사 정보 엑셀 다운로드
	 * @param vo
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/ven/selectExcelDownList.do")
	public void selectExcelDownList(SRMVEN0010VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		vo.setVenCds(epcLoginVO.getVendorId());	// 협력업체코드

		response.setContentType("application/x-msdownload;charset=UTF-8");

		String userAgent = request.getHeader("User-Agent");	// 웹 브라우저 확인

		logger.debug("웹 브라우저 >>>" + userAgent);
		String fileName = messageSource.getMessage("text.srm.field.srmevl003002.title", null, Locale.getDefault()).replaceAll(" ","") + ".xls";

		if (userAgent.indexOf("MSIE 5.5") > -1) {
			response.setHeader("Content-Disposition", "filename=" + fileName + ";");
		} else if (userAgent.indexOf("MSIE") > -1) {
			response.setHeader("Content-Disposition", "attachment;fileName=\""+ URLEncoder.encode(fileName, "UTF-8") + "\";");
		} else {
			response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("euc-kr"), "latin1") + ";");
		}

		List<SRMVEN0010VO> list = srmven0010Server.selectVendorList(vo);

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFCell headCell = null;
		HSSFCell cell0 = null;
		HSSFCell cell1 = null;
		HSSFCell cell2 = null;
		HSSFCell cell3 = null;
		HSSFCell cell4 = null;
		HSSFCell cell5 = null;
		HSSFCell cell6 = null;
		HSSFCell cell7 = null;
		HSSFCell cell8 = null;

		String[] headers = { messageSource.getMessage("text.srm.field.no", null, Locale.getDefault())
							,messageSource.getMessage("table.srm.srmven0030.colum.title4", null, Locale.getDefault())
							,messageSource.getMessage("text.srm.field.sellerCeoName", null, Locale.getDefault())
							,messageSource.getMessage("text.srm.field.sellerCeoEmail", null, Locale.getDefault())
							,messageSource.getMessage("text.srm.field.vMainName", null, Locale.getDefault())
							,messageSource.getMessage("text.srm.field.vMobilePhone", null, Locale.getDefault())
							,messageSource.getMessage("text.srm.field.vEmail", null, Locale.getDefault())
							,messageSource.getMessage("text.srm.field.confirmStatus", null, Locale.getDefault())
							,messageSource.getMessage("text.srm.field.confirmDate", null, Locale.getDefault()) };

		int headerLength = headers.length;

		String sheetName = messageSource.getMessage("text.srm.field.srmevl003002.title", null, Locale.getDefault()).replaceAll(" ","");

		HSSFSheet sheet = workbook.createSheet(sheetName);	// sheet만들기
		HSSFRow header = sheet.createRow(0);				// sheet에 row 만들기
		CellStyle headerCellDateStyle = workbook.createCellStyle();	// cell 스타일 만들기

		headerCellDateStyle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);	// 셀에 색 채우기
		headerCellDateStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		headerCellDateStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);					// 셀의 인스턴스 속성
		headerCellDateStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);					// 오른쪽 테두리 설정
		headerCellDateStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);					// 왼쪽 테두리 설정
		headerCellDateStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);					// 위쪽 테두리 설정
		headerCellDateStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);					// 아래쪽 테두리 설정
		headerCellDateStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);

		HSSFCellStyle cellStyle1 = workbook.createCellStyle();
		cellStyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER);	// 가운데 정렬
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

		for (int k = 0; k < headerLength; k++) {
			headCell = header.createCell(k);
			headCell.setCellStyle(headerCellDateStyle);
			headCell.setCellValue(headers[k]);
		}

		//data 출력
		Iterator iter = list.iterator();
		int no = 1;
		while (iter.hasNext()) {
			SRMVEN0010VO item = (SRMVEN0010VO) iter.next();
			if (item == null) {
				continue;
			}

			HSSFRow row = sheet.createRow(no);	// 순서

			//셀 width
			sheet.setColumnWidth(0,1500);
			sheet.setColumnWidth(1,3000);
			sheet.setColumnWidth(2,3000);
			sheet.setColumnWidth(3,5000);
			sheet.setColumnWidth(4,3000);
			sheet.setColumnWidth(5,4000);
			sheet.setColumnWidth(6,5000);
			sheet.setColumnWidth(7,3000);
			sheet.setColumnWidth(8,3000);

			//셀생성
			cell0 = row.createCell(0);
			cell1 = row.createCell(1);
			cell2 = row.createCell(2);
			cell3 = row.createCell(3);
			cell4 = row.createCell(4);
			cell5 = row.createCell(5);
			cell6 = row.createCell(6);
			cell7 = row.createCell(7);
			cell8 = row.createCell(8);

			//스타일 적용
			cell0.setCellStyle(cellStyle1);
			cell1.setCellStyle(cellStyle1);
			cell2.setCellStyle(cellStyle1);
			cell3.setCellStyle(cellStyle2);
			cell4.setCellStyle(cellStyle1);
			cell5.setCellStyle(cellStyle1);
			cell6.setCellStyle(cellStyle2);
			cell7.setCellStyle(cellStyle1);
			cell8.setCellStyle(cellStyle1);

			//값 적용
			cell0.setCellValue(no);
			cell1.setCellValue(item.getVenCd());

			if (StringUtil.null2str(item.getStatus()).equals("0") || StringUtil.null2str(item.getStatus()).equals("9")) {
				cell2.setCellValue(item.getModPresidNm());
				cell3.setCellValue(item.getModPresidEmail());
				cell4.setCellValue(item.getModDutyInf());
				cell5.setCellValue(item.getModHpNo1());
				cell6.setCellValue(item.getModEmail());

			} else {
				cell2.setCellValue(item.getPresidNm());
				cell3.setCellValue(item.getPresidEmail());
				cell4.setCellValue(item.getDutyInf());
				cell5.setCellValue(item.getHpNo1());
				cell6.setCellValue(item.getEmail());
			}

			if (StringUtil.null2str(item.getStatus()).equals("9")) {
				cell7.setCellValue(messageSource.getMessage("button.srm.tempSave", null, Locale.getDefault()));		// 임시저장

			} else if (StringUtil.null2str(item.getStatus()).equals("0")) {
				cell7.setCellValue(messageSource.getMessage("text.srm.field.status0", null, Locale.getDefault()));	// 승인요청

			} else if (StringUtil.null2str(item.getStatus()).equals("1")) {
				cell7.setCellValue(messageSource.getMessage("text.srm.field.status1", null, Locale.getDefault()));	// 승인완료

			} else if (StringUtil.null2str(item.getStatus()).equals("2")) {
				cell7.setCellValue(messageSource.getMessage("text.srm.field.status2", null, Locale.getDefault()));	// 반려

			} else {
				cell7.setCellValue("");
			}
			cell8.setCellValue(item.getIfDt());
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

}
