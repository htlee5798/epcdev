package com.lottemart.epc.edi.product.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lottemart.common.exception.TopLevelException;
import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.product.model.NEDMPRO0300VO;
import com.lottemart.epc.edi.product.service.NEDMPRO0300Service;

import lcn.module.common.util.DateUtil;
import lcn.module.common.util.StringUtil;

@Controller
public class NEDMPRO0300Controller {

	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0300Controller.class);

	@Autowired
	private NEDMPRO0300Service nedmpro0300Service;

	/**
	 * 행사정보 등록내역 화면
	 * @param map
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/NEDMPRO0300.do")
	public String NEDMPRO0300Init(ModelMap model, HttpServletRequest request) throws Exception {
		if (model == null || request == null) {
			throw new TopLevelException("");
		}
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(ConfigUtils.getString("lottemart.epc.session.key"));
		//검색시 협력 업체코드를 선택하지 않은 경우, 로그인한 사업자의 전체 협력사에 대한 상품등록 정보가 조회됨.
		model.addAttribute("epcLoginVO", epcLoginVO);
		model.addAttribute("today",			DateUtil.getToday("yyyy-MM-dd"));													// 현재일
		model.addAttribute("firstDay",		DateUtil.getToday("yyyy-MM") + "-01");												// 현재월
		model.addAttribute("srchStartDt",	DateUtil.formatDate(DateUtil.addDay(DateUtil.getToday("yyyy-MM-dd"), -60), "-"));	// 60일전
		model.addAttribute("srchEndDt",		DateUtil.formatDate(DateUtil.addDay(DateUtil.getToday("yyyy-MM-dd"), 30), "-"));	// 30일뒤
		
		return "/edi/product/NEDMPRO0300";
	}
	
	
	/**
	 * 행사정보 등록내역 조회
	 * @param request
	 * @param paramVo
	 * @return HashMap<String,Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/selectProEventAppList.json", method = RequestMethod.POST)
	public @ResponseBody HashMap<String,Object> selectProEventAppList(@RequestBody NEDMPRO0300VO paramVo, ModelMap model, HttpServletRequest request) throws Exception {
		if (model == null || request == null) {
			throw new TopLevelException("");
		}
		return nedmpro0300Service.selectProEventAppList(paramVo, request);
	}
	
	
	/**
	 * Excel Down
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/selectProEventAppExcelDown.do")
	public void selectProEventAppExcelDown(NEDMPRO0300VO paramVo, HttpServletRequest request, ModelMap model, HttpServletResponse response) throws Exception {
		if (model == null || request == null) {
			throw new TopLevelException("");
		}
		// List 가져오기
		HashMap<String,Object>	result = nedmpro0300Service.selectProEventAppList(paramVo, request);
		List<NEDMPRO0300VO>	resultList = new ArrayList<NEDMPRO0300VO>();

		int cnt = (int) result.get("totalCount");
		
		if( cnt > 0) {
			resultList = (List<NEDMPRO0300VO>) result.get("list");
		}
		
		response.setContentType("application/x-msdownload;charset=UTF-8");

		String userAgent = request.getHeader("User-Agent");
		String fileName = "행사정보 등록내역.xls";

		if (userAgent.indexOf("MSIE 5.5") > -1) {
			response.setHeader("Content-Disposition", "filename=" + fileName + ";");
		} else if (userAgent.indexOf("MSIE") > -1) {
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ";");
		} else {
			response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("euc-kr"), "latin1") + ";");
		}

		StringBuffer sb = new StringBuffer();

		// 헤더출력
		String[] headers = { "No", "파트너사행사코드", "행사명", "계열사", "팀", "상태", "등록일자",
				"파트너사명", "발주시작일", "발주종료일", "행사시작일", "행사종료일", "행사유형", "할인유형", "상품 수",
				"승인 상품수", "롯데마트 공문", "롯데슈퍼 공문", "CS공문" };

		int headerLength = headers.length;

		// create a wordsheet
		HSSFWorkbook workbook = new HSSFWorkbook();
		CreationHelper createHelper = workbook.getCreationHelper();
		HSSFSheet sheet = workbook.createSheet("행사정보 등록내역");
		HSSFRow header = sheet.createRow(0);
		HSSFCellStyle cellDateStyle = workbook.createCellStyle();
		cellDateStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-mm-dd hh:mm"));
		
		//정렬
		cellDateStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellDateStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		
		//sheet.autoSizeColumn(350);
		sheet.setDefaultColumnWidth(50);
		
		//테두리 라인
		//cellDateStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		//cellDateStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		//cellDateStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		//cellDateStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		
		//배경색
		cellDateStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);  
		cellDateStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			
		for (int i = 0; i < headerLength; i++) {
			header.createCell(i).setCellValue(headers[i]);
			header.getCell(i).setCellStyle(cellDateStyle);
		}

		Iterator iter = resultList.iterator();
		int rowNum = 1;
		while (iter.hasNext()) {
			NEDMPRO0300VO list = (NEDMPRO0300VO) iter.next();
			if (list == null) {
				continue;
			}
			HSSFRow row = sheet.createRow(rowNum++);
			
			row.createCell(0).setCellValue((rowNum - 1) + "");
			row.createCell(1).setCellValue(list.getReqOfrcd());
			row.createCell(2).setCellValue(list.getOfrTxt());
			row.createCell(3).setCellValue(list.getVkorgTxt());
			row.createCell(4).setCellValue(list.getDepCd());
			row.createCell(5).setCellValue(list.getZdealTxt());
			row.createCell(6).setCellValue(list.getRegDate());
			row.createCell(7).setCellValue(list.getLifnrTxt());
			row.createCell(8).setCellValue(list.getPrsdt());
			row.createCell(9).setCellValue(list.getPredt());
			row.createCell(10).setCellValue(list.getOfsdt());
			row.createCell(11).setCellValue(list.getOfedt());
			row.createCell(12).setCellValue(list.getReqTypeTxt());
			row.createCell(13).setCellValue(list.getReqDiscTxt());
			
			row.createCell(14).setCellValue(list.getReqDiscTxt());
			row.createCell(15).setCellValue(list.getReqDiscTxt());
			row.createCell(16).setCellValue(list.getReqDiscTxt());
			row.createCell(17).setCellValue(list.getReqDiscTxt());
			row.createCell(18).setCellValue(list.getReqDiscTxt());
			
		}

		ServletOutputStream out = response.getOutputStream();
		workbook.write(out);
		out.flush();
	}
	
	/**
	 * ECS 계약서 조회
	 * @param model
	 * @param vo
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/selectEcsDocInfo.do")
	public ModelAndView selectEcsDocInfo(ModelMap model, NEDMPRO0300VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (model == null || request == null) {
			throw new TopLevelException("");
		}
		ModelAndView mv = new ModelAndView();
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(ConfigUtils.getString("lottemart.epc.session.key"));
		mv.addObject("dcNum", StringUtil.null2str(request.getParameter("dcNum"), "") );
		mv.addObject("ecsDocInfo", nedmpro0300Service.selectEcsDocInfo(vo) ); // 행사 헤더 조회
		mv.addObject("epcLoginVO", epcLoginVO);
		mv.setViewName("/edi/product/NEDMPRO0301");
    	return mv;
	}
	
	
	
	
	
	


}
