package com.lottemart.epc.edi.product.controller;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
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
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibm.icu.util.ChineseCalendar;
import com.lottemart.common.exception.TopLevelException;
import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.product.model.NEDMPRO0320VO;
import com.lottemart.epc.edi.product.service.CommonProductService;
import com.lottemart.epc.edi.product.service.NEDMPRO0320Service;

import lcn.module.common.util.DateUtil;

@Controller
public class NEDMPRO0320Controller extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0320Controller.class);

	@Autowired
	private NEDMPRO0320Service nedmpro0320Service;
	
	@Resource(name = "commonProductService")
	private CommonProductService commonProductService;
	
	/**
	 * 반품 제안 등록 화면 호출
	 * @param model
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/NEDMPRO0320.do")
	public String NEDMPRO0320Init(ModelMap model, HttpServletRequest request) throws Exception {
		
		if (model == null || request == null) {
			throw new TopLevelException("");
		}
		
		Map<String, Object>	paramMap	=	new HashMap<String, Object>();
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(ConfigUtils.getString("lottemart.epc.session.key"));
		//검색시 협력 업체코드를 선택하지 않은 경우, 로그인한 사업자의 전체 협력사에 대한 상품등록 정보가 조회됨.
		model.addAttribute("epcLoginVO", epcLoginVO);
		model.addAttribute("teamList", 		commonProductService.selectNteamList(paramMap, request)); // 팀리스트
		model.addAttribute("curYear",		DateUtil.getCurrentYearAsString() );												//현재년도
		model.addAttribute("curMonth",		DateUtil.getCurrentMonthAsString() );												//현재월
		model.addAttribute("srchFromDt",	DateUtil.getToday("yyyy-MM-dd"));													//검색시작일
		model.addAttribute("srchEndDt",		calculateDate(DateUtil.getToday("yyyy-MM-dd"), 0, 0, 1));							//검색종료일
		//model.addAttribute("nextMonth",		calculateDate(DateUtil.getToday("yyyy-MM-dd"), 0, 1, 0));							//한달뒤
		//model.addAttribute("prevMonth",		calculateDate(DateUtil.getToday("yyyy-MM-dd"), 0, -1, 0));							//한달전
		//model.addAttribute("next2Month",	calculateDate(DateUtil.getToday("yyyy-MM-dd"), 0, 2, 0)); 							//두달뒤
		model.addAttribute("nextMonth",		DateUtil.formatDate(DateUtil.addDay(DateUtil.getToday("yyyy-MM-dd"), 30), "-"));	//한달뒤
		model.addAttribute("prevMonth",		DateUtil.formatDate(DateUtil.addDay(DateUtil.getToday("yyyy-MM-dd"), -30), "-"));	//한달전
		model.addAttribute("next2Month",		DateUtil.formatDate(DateUtil.addDay(DateUtil.getToday("yyyy-MM-dd"), 60), "-"));//두달뒤
		model.addAttribute("newYear",		convertDay(Integer.parseInt(DateUtil.getCurrentYearAsString()), 1, 1) );			//설날
		model.addAttribute("chuseok",		convertDay(Integer.parseInt(DateUtil.getCurrentYearAsString()), 8, 15) );			//추석
		
		return "/edi/product/NEDMPRO0320";
	}
	
	public static String calculateDate(String date, int year, int month, int day) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(date, formatter);
		LocalDate resultDate = localDate.plusYears(year).plusMonths(month).plusDays(day);
		return resultDate.format(formatter);
	}
	

	/**
	 * 반품 제안 등록 조회
	 * @param request
	 * @param paramVo
	 * @return HashMap<String,Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/selectProdRtnItemList.json", method = RequestMethod.POST)
	public @ResponseBody HashMap<String,Object> selectProdRtnItemList(@RequestBody NEDMPRO0320VO paramVo, ModelMap model, HttpServletRequest request) throws Exception {
		if (model == null || request == null) {
			throw new TopLevelException("");
		}
		return nedmpro0320Service.selectProdRtnItemList(paramVo);
	}
	
	
	/**
	 * 반품 제안 등록
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/insertProdRtnItem.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody HashMap<String,Object> insertProdRtnItem(@RequestBody NEDMPRO0320VO vo, ModelMap model, HttpServletRequest request) throws Exception {
		if (model == null || request == null) {
			throw new TopLevelException("");
		}
		return nedmpro0320Service.insertProdRtnItem(vo, request);
	}
	
	
	/**
	 * 반품 제안 정보 삭제
	 * @param vo
	 * @param result
	 * @return HashMap<String,Object> 
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/deleteProdRtnItem.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody HashMap<String,Object> deleteProdRtnItem(@RequestBody NEDMPRO0320VO vo, ModelMap model, HttpServletRequest request) throws Exception {
		if (model == null || request == null) {
			throw new TopLevelException("");
		}
		return nedmpro0320Service.deleteProdRtnItem(vo, request);
	}
	
	/**
	 * 반품 제안 정보 ECO 전송
	 * @param vo
	 * @param result
	 * @return HashMap<String,Object> 
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/insertProdRtnItemRfcCall.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> insertProdRtnItemRfcCall(@RequestBody NEDMPRO0320VO vo, ModelMap model, HttpServletRequest request) throws Exception {
		if (model == null || request == null) {
			throw new TopLevelException("");
		}
		return nedmpro0320Service.insertProdRtnItemRfcCall(vo, request);
	}
	
	
	/**
	 * itme 업로드 양식 다운
	 * @param request
	 * @param reuqest
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/selectProdRtnItemExcelFromDown.do")
	public void selectProdRtnItemExcelFromDown(NEDMPRO0320VO paramVo, HttpServletRequest request, HttpServletResponse response) throws Exception {

		response.setContentType("application/x-msdownload;charset=UTF-8");

		String userAgent = request.getHeader("User-Agent");
		String fileName = "반품제안_업로드_양식.xls";

		fileName = URLEncoder.encode(fileName, "UTF-8");

		if (userAgent.indexOf("MSIE 5.5") > -1) {
			response.setHeader("Content-Disposition", "filename=" + fileName + ";");
		} else {
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ";");
		}
		
		String[] headers = { "등록일자", "계열사코드","파트너사코드", "팀코드", "판매코드", "상품코드", "반품마감일", "반품사유코드", "반품상세사유코드", "반품장소코드" };

		// 헤더출력
		int headerLength = headers.length;

		// create a wordsheet
		HSSFWorkbook workbook = new HSSFWorkbook();
		CreationHelper createHelper = workbook.getCreationHelper();
		HSSFSheet sheet = workbook.createSheet("반품제안 업로드 양식");
		HSSFRow header = sheet.createRow(0);
		HSSFCellStyle styleHd = workbook.createCellStyle();
		HSSFCellStyle styleHd2 = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		HSSFCell cell = null;
		DataFormat format = workbook.createDataFormat();
		
		//font.setBoldweight((short) font.BOLDWEIGHT_BOLD);
		font.setFontHeight((short) 200);
		styleHd.setFont(font);
		styleHd.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleHd.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		styleHd.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		styleHd.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		styleHd2.setDataFormat(format.getFormat("@"));
		styleHd2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleHd2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		for (int i = 0; i < headerLength; i++) {
			cell = header.createCell(i);
			cell.setCellValue(headers[i]);
			cell.setCellStyle(styleHd);
			sheet.setColumnWidth(i, 4000);
		}

		Row rows = sheet.getRow(0);
		int cellCnt = (int) rows.getLastCellNum();
		
		for (int j = 0; j < cellCnt; j++) {
			sheet.setDefaultColumnStyle(j, styleHd2);
		}

		ServletOutputStream out = response.getOutputStream();
		workbook.write(out);
		out.flush();
	}
	
	
	/**
	 * 음력 날짜 양력 변환
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static String convertDay(int year, int month, int day) {
		ChineseCalendar chineseCalendar = new ChineseCalendar();
		chineseCalendar.set(ChineseCalendar.EXTENDED_YEAR, Integer.parseInt(DateUtil.getCurrentYearAsString()) + 2637);
		chineseCalendar.set(ChineseCalendar.MONTH, month - 1);
		chineseCalendar.set(ChineseCalendar.DAY_OF_MONTH, day);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(chineseCalendar.getTimeInMillis());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(calendar.getTime());
	}
	
	
}
