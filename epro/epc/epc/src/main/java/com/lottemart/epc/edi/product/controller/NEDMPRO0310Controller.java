package com.lottemart.epc.edi.product.controller;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

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
import org.springframework.web.servlet.ModelAndView;

import com.lottemart.common.exception.TopLevelException;
import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.product.model.NEDMPRO0300VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0310VO;
import com.lottemart.epc.edi.product.service.NEDMPRO0310Service;

import lcn.module.common.util.DateUtil;
import lcn.module.common.util.StringUtil;

@Controller
public class NEDMPRO0310Controller {

	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0310Controller.class);

	@Autowired
	private NEDMPRO0310Service nedmpro0310Service;

	
	/**
	 * 파트너사 행사 신청 상세 화면
	 * @param map
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/selectProEventAppDetail.do")
	public ModelAndView selectProEventAppDetail(ModelMap model, NEDMPRO0310VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (model == null || request == null) {
			throw new TopLevelException("");
		}
		ModelAndView mv = new ModelAndView();
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(ConfigUtils.getString("lottemart.epc.session.key"));
		mv.addObject("taskGbn", vo.getTaskGbn());
		mv.addObject("epcLoginVO", epcLoginVO);
		mv.addObject("srchFromDt",	DateUtil.getToday("yyyy-MM-dd"));													//검색시작일
		mv.addObject("srchEndDt",		DateUtil.formatDate(DateUtil.addDay(DateUtil.getToday("yyyy-MM-dd"), 1), "-"));		//검색종료일
		mv.addObject("proEventInfo", nedmpro0310Service.selectProEventAppDetail(vo) ); // 행사 헤더 조회
		mv.addObject("proEventList", nedmpro0310Service.selectProEventAppItemList(vo) ); // 행사 아이템 조회
		mv.setViewName("/edi/product/NEDMPRO0310");
    	return mv;
	}
	
	/**
	 * 파트너사 행사 정보 저장
	 * @param vo
	 * @param result
	 * @return HashMap<String,Object> 
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/insertProEventApp.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody HashMap<String,Object> insertProEventApp(@RequestBody NEDMPRO0310VO vo, ModelMap model, HttpServletRequest request) throws Exception {
		if (model == null || request == null) {
			throw new TopLevelException("");
		}
		return nedmpro0310Service.insertProEventApp(vo, request);
	}
	
	/**
	 * 파트너사 행사 정보 삭제
	 * @param vo
	 * @param result
	 * @return HashMap<String,Object> 
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/deleteProEventApp.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody HashMap<String,Object> deleteProEventApp(@RequestBody NEDMPRO0310VO vo, ModelMap model, HttpServletRequest request) throws Exception {
		if (model == null || request == null) {
			throw new TopLevelException("");
		}
		return nedmpro0310Service.deleteProEventApp(vo, request);
	}
	
	/**
	 * 파트너사 행사 itme 삭제
	 * @param vo
	 * @param result
	 * @return HashMap<String,Object> 
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/deleteProEventAppItem.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody HashMap<String,Object> deleteProEventAppItem(@RequestBody NEDMPRO0310VO vo, ModelMap model, HttpServletRequest request) throws Exception {
		if (model == null || request == null) {
			throw new TopLevelException("");
		}
		return nedmpro0310Service.deleteProEventAppItem(vo, request);
	}
	
	/**
	 * 파트너사 행사 정보 ECO 전송
	 * @param vo
	 * @param result
	 * @return HashMap<String,Object> 
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/insertProEventAppRfcCall.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> insertProEventAppRfcCall(@RequestBody NEDMPRO0310VO vo, ModelMap model, HttpServletRequest request) throws Exception {
		if (model == null || request == null) {
			throw new TopLevelException("");
		}
		return nedmpro0310Service.insertProEventAppRfcCall(vo, request);
	}
	
	/**
	 * itme 업로드 양식 다운
	 * @param request
	 * @param reuqest
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/selectProEventAppExcelFromDown.do")
	public void selectProEventAppExcelFromDown(NEDMPRO0300VO paramVo, HttpServletRequest request, ModelMap model, HttpServletResponse response) throws Exception {
		if (model == null || request == null) {
			throw new TopLevelException("");
		}
		
		response.setContentType("application/x-msdownload;charset=UTF-8");

		String userAgent = request.getHeader("User-Agent");
		String fileName = "행사업로드양식.xls";
		String reqType = paramVo.getReqType(); 		// 행사유형
		String costType = paramVo.getCostType(); 	// 비용적용방식
		String reqDisc = paramVo.getReqDisc(); 		// 할인유형

		String[] newHeaders = new String[1];
		
		if( "1001".equals(reqType) || "1301".equals(reqType) || "1501".equals(reqType)) {
			// 가격 할인(1001), 엘포인트할인(1301), 카드할인(1501)
			if("1001".equals(reqType)) fileName = "행사업로드양식(가격할인).xls";
			else if("1301".equals(reqType)) fileName = "행사업로드양식(엘포인트할인).xls";
			else fileName = "행사업로드양식(카드할인).xls";
			
			String[] headers = { "판매바코드(13)", "상품코드", "정상원가(원)", "행사원가(원)", "정상판매가(원)", "행사판매가(원)", "분담율", "세부사항", "비고" };
			newHeaders = headers;
		}else if("1002".equals(reqType)) {
			// 번들
			fileName = "행사업로드양식(번들).xls";
			if( "01".equals( reqDisc )){
				// 번들 + 정액판매가 서식 4번
				String[] headers = { "판매바코드(13)", "상품코드", "정상원가(원)", "행사원가(원)", "기준1(해당상품N개구매시)", "행사판매가1(원)"
						, "기준2", "행사판매가2(원)", "기준3", "행사판매가3(원)", "상품별 분담율", "세부사항", "비고" };
				newHeaders = headers;
			}else if( "03".equals( reqDisc )){
				// 번들 + 정액할인 서식 5번
				String[] headers = { "판매바코드(13)", "상품코드", "정상원가(원)", "행사원가(원)", "기준1(해당상품N개구매시)", "할인액1(원)"
						, "기준2", "할인액2(원)", "기준3", "할인액3(원)", "상품별 분담율", "세부사항", "비고" };
				newHeaders = headers;
			}else if( "04".equals( reqDisc )){
				// 번들 + 정율할인 서식 6번
				String[] headers = { "판매바코드(13)", "상품코드", "정상원가(원)", "행사원가(원)", "기준1(해당상품N개구매시)", "할인율1(%)"
						, "기준2", "할인율2(%)", "기준3", "할인율3(%)", "상품별 분담율", "세부사항", "비고" };
				newHeaders = headers;
			}
		}else if("1003".equals(reqType)) {
			// M+N
			fileName = "행사업로드양식(M+N).xls";
			String[] headers = { "판매바코드(13)", "상품코드", "정상원가(원)", "행사원가(원)", "M(개)", "N(개)", "분담율", "세부사항", "비고" };
			newHeaders = headers;
		}else if("1004".equals(reqType)) {
			// 연관
			fileName = "행사업로드양식(연관).xls";
			String[] headers = { "판매바코드(13)", "상품코드", "정상원가(원)", "행사원가(원)", "정상 판매가(원)","행사 판매가(원)", "분담율", "세부사항", "비고" };
			newHeaders = headers;
		}else if("4002".equals(reqType)) {
			// 상품권
			fileName = "행사업로드양식(상품권).xls";
			String[] headers = { "판매바코드(13)", "상품코드", "결제금액 기준(동일입력)", "증정/사은금액", "분담율", "세부사항", "비고" };
			newHeaders = headers;
		}else {
			// 8001 : 원매가행사 공문
			fileName = "행사업로드양식(원매가행사).xls";
			String[] headers = { "판매바코드(13)", "상품코드", "정상원가(원)", "행사원가(원)", "세부사항", "비고" };
			newHeaders = headers;
		}
		
		fileName = URLEncoder.encode(fileName, "UTF-8");

		if (userAgent.indexOf("MSIE 5.5") > -1) {
			response.setHeader("Content-Disposition", "filename=" + fileName + ";");
		} else {
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ";");
		}
		
		// 헤더출력
		int headerLength = newHeaders.length;

		// create a wordsheet
		HSSFWorkbook workbook = new HSSFWorkbook();
		CreationHelper createHelper = workbook.getCreationHelper();
		HSSFSheet sheet = workbook.createSheet("행사 업로드 양식");
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
			cell.setCellValue(newHeaders[i]);
			cell.setCellStyle(styleHd);
			
			if (i < (headerLength - 1)) {
				//cell.setCellStyle(styleHd);
				sheet.setColumnWidth(i, 5000);
			} else {
				//cell.setCellStyle(styleHd2);
				sheet.setColumnWidth(i, 16000);
			}
		}
		
		//HSSFRow row = sheet.createRow(1);
		//row.createCell(0).setCellValue("");
		
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
	 * 판매바코드 조회 팝업 호출
	 * @param model
	 * @param vo
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/selectSaleBarcode.do")
	public ModelAndView selectSaleBarcodeInfo(ModelMap model, NEDMPRO0310VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (model == null || request == null) {
			throw new TopLevelException("");
		}
		
		ModelAndView mv = new ModelAndView();
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(ConfigUtils.getString("lottemart.epc.session.key"));
		mv.addObject("ean11", StringUtil.null2str(request.getParameter("ean11"), "") );
		mv.addObject("trNum", StringUtil.null2str(request.getParameter("trNum"), "") );
		mv.addObject("epcLoginVO", epcLoginVO);
		mv.setViewName("/edi/product/NEDMPRO0311");
    	return mv;
	}
	
	
	/**
	 * 판매바코드 조회
	 * @param paramVo
	 * @return HashMap<String,Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/selectSaleBarcodeList.json", method = RequestMethod.POST)
	public @ResponseBody HashMap<String,Object> selectProEventAppList(@RequestBody NEDMPRO0310VO paramVo, ModelMap model, HttpServletRequest request) throws Exception {
		if (model == null || request == null) {
			throw new TopLevelException("");
		}
		return nedmpro0310Service.selectSaleBarcodeList(paramVo);
	}
	
	
	/**
	 * 공문서 작성 요청
	 * @param vo
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/insertProdEcsIntgr.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> insertProdEcsIntgr(@RequestBody NEDMPRO0310VO vo, ModelMap model, HttpServletRequest request) throws Exception {
		if (model == null || request == null) {
			throw new TopLevelException("");
		}
		return nedmpro0310Service.insertProdEcsIntgr(vo);
	}
	
	
	/**
	 * ECS 연동 전 user id 받는 팝업 호출
	 * @param model
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/selectEcsUserId.do")
	public ModelAndView selectEcsUserId(ModelMap model, NEDMPRO0310VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (model == null || request == null) {
			throw new TopLevelException("");
		}
		ModelAndView mv = new ModelAndView();
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(ConfigUtils.getString("lottemart.epc.session.key"));
		mv.addObject("reqOfrcd", StringUtil.null2str(request.getParameter("reqOfrcd"), "") );
		mv.addObject("vkorg", StringUtil.null2str(request.getParameter("vkorg"), "") );
		mv.addObject("taskGbn", StringUtil.null2str(request.getParameter("taskGbn"), "") );
		mv.addObject("epcLoginVO", epcLoginVO);
		mv.setViewName("/edi/product/NEDMPRO0312");
    	return mv;
	}
	
	/**
	 * 행사 테마 번호 팝업 호출
	 * @param model
	 * @param vo
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/selectProdEvntThm.do")
	public ModelAndView selectProdEvntThmInfo(ModelMap model, NEDMPRO0310VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (model == null || request == null) {
			throw new TopLevelException("");
		}
		ModelAndView mv = new ModelAndView();
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(ConfigUtils.getString("lottemart.epc.session.key"));
		mv.addObject("subTxt", StringUtil.null2str(request.getParameter("subTxt"), "") );
		mv.addObject("vkorg", StringUtil.null2str(request.getParameter("vkorg"), "") );
		mv.addObject("epcLoginVO", epcLoginVO);
		mv.setViewName("/edi/product/NEDMPRO0311");
    	return mv;
	}
	
	/**
	 * 행사 테마번호 조회
	 * @param paramVo
	 * @return HashMap<String,Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/selectProdEvntThmList.json", method = RequestMethod.POST)
	public @ResponseBody HashMap<String,Object> selectProdEvntThmList(@RequestBody NEDMPRO0310VO paramVo, ModelMap model, HttpServletRequest request) throws Exception {
		if (model == null || request == null) {
			throw new TopLevelException("");
		}
		return nedmpro0310Service.selectProdEvntThmList(paramVo);
	}
	
	/**
	 * 팀 정보 팝업 호출
	 * @param model
	 * @param vo
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/selectDepCdInfo.do")
	public ModelAndView selectDepCdInfo(ModelMap model, NEDMPRO0310VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (model == null || request == null) {
			throw new TopLevelException("");
		}
		ModelAndView mv = new ModelAndView();
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(ConfigUtils.getString("lottemart.epc.session.key"));
		mv.addObject("depCdTxt", StringUtil.null2str(request.getParameter("depCdTxt"), "") );
		mv.addObject("epcLoginVO", epcLoginVO);
		mv.setViewName("/edi/product/NEDMPRO0313");
		return mv;
	}
	
	/**
	 * 팀 정보 조회
	 * @param paramVo
	 * @return HashMap<String,Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/selectDepCdList.json", method = RequestMethod.POST)
	public @ResponseBody HashMap<String,Object> selectDepCdList(@RequestBody NEDMPRO0310VO paramVo, ModelMap model, HttpServletRequest request) throws Exception {
		if (model == null || request == null) {
			throw new TopLevelException("");
		}
		return nedmpro0310Service.selectDepCdList(paramVo);
	}
	
	
}
