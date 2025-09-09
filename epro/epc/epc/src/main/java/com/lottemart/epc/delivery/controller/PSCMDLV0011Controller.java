package com.lottemart.epc.delivery.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import lcn.module.common.message.CMessageSource;
import lcn.module.common.util.StringUtil;
import lcn.module.framework.property.ConfigurationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lcnjf.util.DateUtil;
import com.lottemart.common.code.service.CommonCodeService;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.service.CommonService;
import com.lottemart.epc.common.util.LoginUtil;
import com.lottemart.epc.delivery.model.PSCMDLV0011VO;
import com.lottemart.epc.delivery.service.PSCMDLV0011Service;
import com.lottemart.epc.util.Utils;

@Controller
public class PSCMDLV0011Controller {
	private static final Logger logger = LoggerFactory.getLogger(PSCMDLV0011Controller.class);

	/** MessageSource */
    @Resource(name = "CMessageSource")
    CMessageSource messageSource;

	@Autowired
	private ConfigurationService config;

	@Autowired
	private CommonService commonService;

    @Autowired
    private PSCMDLV0011Service pscmdlv0011Service;

	private final String[] titles = {
		"순번",
		"주문일자",
		"주문번호",
		"상품코드",
		"상품명",
		"발송여부",
		"발송소요일 (주문일 기준)"
		//,"페널티 적용 횟수"
	};

	private final String[] columns = {
		"NUM",
		"ORD_DY",
		"ORDER_ID",
		"PROD_CD",
		"PROD_NM",
		"DELI_YN",
		"D_DAY"
		//,"PENALTY_CNT"
	};

	@RequestMapping("delivery/pscmdlv0011/init.do")
	public String noticeForm(@ModelAttribute("searchVO") PSCMDLV0011VO searchVO, HttpServletRequest request, ModelMap model) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if( epcLoginVO == null || epcLoginVO.getVendorId() == null ) {
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}

		request.setAttribute("epcLoginVO", epcLoginVO);

		// 주문일자 조회조건이 없을 경우
		if(StringUtil.isNVL(searchVO.getStartDate()) || StringUtil.isNVL(searchVO.getEndDate())) {

			String from_date ="";     // 기간(시작)
			String to_date 	 = "";     // 기간(끝)

			Calendar NowDate = Calendar.getInstance();
			Calendar NowDate2 = Calendar.getInstance();
			NowDate.add(Calendar.DATE, 0);
			NowDate2.add(Calendar.DATE, -7);

			String today_date = Utils.formatDate(NowDate.getTime(),"yyyy-MM-dd");
			String today_date2 = Utils.formatDate(NowDate2.getTime(),"yyyy-MM-dd");
			// 초최 오픈시 Default 값 세팅
			if(from_date == null || from_date.equals("")) from_date = today_date2;
			if(to_date   == null || to_date.equals(""))   to_date   = today_date;

			searchVO.setStartDate(from_date);
			searchVO.setEndDate(to_date);

		}

		model.addAttribute("searchVO", searchVO);

		return "delivery/PSCMDLV0011";
	}

	@RequestMapping("delivery/pscmdlv0011/search.do")
	public @ResponseBody Map selectVendorDeliDyStatus(@ModelAttribute("searchVO") PSCMDLV0011VO searchVO, HttpServletRequest request, ModelMap model) throws Exception {

		Map rtnMap = new HashMap<String, Object>();

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);

		try {
			DataMap param = new DataMap(request);

			// 선택한 vendorId가null 이거나 repVendorId ( 대표협력업체 ) 일때 협력사 전체 검색
			if("".equals(param.getString("vendorId")) || epcLoginVO.getRepVendorId().equals(param.getString("vendorId"))) {
				param.put("vendorId", LoginUtil.getVendorList(epcLoginVO));
			} else {
				ArrayList<String> vendorList = new ArrayList<String>();
				vendorList.add(param.getString("vendorId"));
				param.put("vendorId", vendorList);
			}

			// 선택한 vendor가 openappi 업체일 경우 전체 검색
			List<EpcLoginVO> openappiVendorId = commonService.selectOpenappiVendor();

			logger.info("openappiVendorId size : ",openappiVendorId.size());
			for(int l=0; openappiVendorId.size()>l; l++ ){
				if(openappiVendorId.get(l).getRepVendorId().equals(param.getString("vendorId").replace("[", "").replace("]", "").trim())){
					param.put("vendorId", LoginUtil.getVendorList(epcLoginVO));
				}
			}

			// 페이징 정보
			String rowsPerPage = StringUtil.null2str((String)param.get("rowsPerPage"), config.getString("count.row.per.page"));
			int startRow = ((Integer.parseInt((String)param.get("currentPage"))-1)*Integer.parseInt(rowsPerPage))+1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) -1;

			param.put("currentPage", (String)param.get("currentPage"));
			param.put("rowsPerPage", rowsPerPage);
			param.put("startRow", Integer.toString(startRow));
			param.put("endRow", Integer.toString(endRow));

			//필터링 정보
			String startDate = param.getString("startDate").replaceAll("-", "");
			String endDate = param.getString("endDate").replaceAll("-", "");
			String prodCd =param.getString("prodCd");
			String prodNm =param.getString("prodNm");
			String penaltyCnt =param.getString("penaltyCnt");

			param.put("startDate", startDate);
			param.put("endDate", endDate);
			param.put("prodCd", prodCd);
			param.put("prodNm", prodNm);
			param.put("penaltyCnt", penaltyCnt);

			 // 전체 조회 건수
			int totalCnt = pscmdlv0011Service.vendorPenaltyTotalCnt((Map)param);
			param.put( "totalCount", Integer.toString(totalCnt));

			List<DataMap> list = pscmdlv0011Service.selectVendorPenaltyCompRate(param);
			rtnMap = JsonUtils.convertList2Json((List)list, totalCnt, param.getString("currentPage"));

			// 처리성공
			rtnMap.put("result", true);

		} catch (Exception e) {
			// 작업오류
			logger.error("error --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("Message", e.getMessage());
		}

		return rtnMap;
	}

	@RequestMapping("delivery/pscmdlv0011/excel.do")
	public void excelVendorDeliDyStatus(@ModelAttribute("searchVO") PSCMDLV0011VO searchVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {


		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if( epcLoginVO == null || epcLoginVO.getVendorId() == null ) {
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}

		request.setAttribute("epcLoginVO", epcLoginVO);

		DataMap paramMap = new DataMap();

		paramMap.put("startDate", searchVO.getStartDate().replaceAll("-", ""));
		paramMap.put("endDate", searchVO.getEndDate().replaceAll("-", ""));

		paramMap.put("currentPage", "1");
		paramMap.put("rowsPerPage", "65000");

		// 선택한 vendorId가null 이거나 repVendorId ( 대표협력업체 ) 일때 협력사 전체 검색
		if("".equals(searchVO.getVendorId()) || epcLoginVO.getRepVendorId().equals(searchVO.getVendorId()) ) {
			paramMap.put("vendorId", LoginUtil.getVendorList(epcLoginVO));
		} else {
			ArrayList<String> vendorList = new ArrayList<String>();
			vendorList.add(searchVO.getVendorId());
			paramMap.put("vendorId", vendorList);
		}

		// 선택한 vendor가 openappi 업체일 경우 전체 검색
		List<EpcLoginVO> openappiVendorId = commonService.selectOpenappiVendor();
		for(int l=0; openappiVendorId.size()>l; l++ ){
			if(openappiVendorId.get(l).getRepVendorId().equals(searchVO.getVendorId().replace("[", "").replace("]", "").trim())){
				paramMap.put("vendorId", LoginUtil.getVendorList(epcLoginVO));
			}
		}

		List<DataMap> list = pscmdlv0011Service.selectVendorPenaltyCompRateExcel(paramMap);

		// JXL을 이용한 엑셀 다운로드
		WritableWorkbook wWorkbook = null;
		WritableSheet wSheet = null;

		try {
			setHeader(request, response);
			wWorkbook = getWorkBook(response);
			wSheet = createWritableSheet(wWorkbook);
			wSheet = DataToExcelBind(wSheet, list, titles, columns);
			wWorkbook.write();
			wWorkbook.close();
		} catch(Exception e) {
			printMessage(response);
		}
	}

	private void printMessage(HttpServletResponse response) {
		PrintWriter out = null;
		response.setContentType("text/html;charset=euc-kr");

		try {
		    out = response.getWriter();
		    out.println("<SCRIPT>");
		    out.println("alert('The data which searches does not exist.')");
		    out.println("</SCRIPT>");
		} catch (IOException e) {
		    logger.error(e.getMessage());
		} finally {
		    out.close();
		}
	}

	private void setHeader(HttpServletRequest request, HttpServletResponse response) {
		String time = DateUtil.getCurrentTime("yyyyMMddHHmmss");

		String fileName = "Delivery_Status_"+time+".xls"; //실제 파일명

		String header = request.getHeader("User-Agent");
		response.reset();
		response.setContentType("application/x-msdownload;charset=UTF-8");
		response.setHeader("Content-Transfer-Encoding", "binary;");

		//PSCMPRD0030Controller - 전상법 업로드용 양식 엑셀 다운로드 참조
	    try {
			if (header.indexOf("MSIE 5.5") > -1) {
				response.setHeader("Content-Disposition", "filename=" + fileName + ";");
			} else if (header.indexOf("MSIE") > -1) {
				response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ";");
			} else {
				response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("euc-kr"), "latin1") + ";");
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("error --> " + e.getMessage());
		}
	}

	private WritableWorkbook getWorkBook(HttpServletResponse response) throws BiffException {
		WritableWorkbook workbook = null;
		try {
			WorkbookSettings ws = new WorkbookSettings();
			//ws.setLocale(Locale.KOREA);
			workbook = Workbook.createWorkbook(response.getOutputStream(), ws);
		} catch (IOException e) {
			logger.error(e+"");
		}
		return workbook;
	}

	private WritableSheet createWritableSheet(WritableWorkbook wWorkbook) {
		return wWorkbook.createSheet("발송일 준수율", 0);
	}

	private WritableSheet DataToExcelBind(WritableSheet wSheet, List<DataMap> list, String[] titles, String[] columns) throws RowsExceededException, WriteException {
        // 일반 헤더 포멧 정의
		WritableCellFormat t1Format= new WritableCellFormat(NumberFormats.TEXT);
		t1Format.setBackground(Colour.GREY_25_PERCENT);
		t1Format.setAlignment(Alignment.CENTRE);
		t1Format.setBorder(Border.ALL, BorderLineStyle.THIN);
		t1Format.setFont(new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLUE));

        // 입력 헤더 포멧 정의
		WritableCellFormat t2Format= new WritableCellFormat(NumberFormats.INTEGER);
		t2Format.setBackground(Colour.YELLOW);
		t2Format.setAlignment(Alignment.CENTRE);
		t2Format.setBorder(Border.ALL, BorderLineStyle.THIN);
		t2Format.setFont(new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLUE));

		// 컬럼포멧 정의
		WritableCellFormat nFormat= new WritableCellFormat(NumberFormats.INTEGER);
		nFormat.setBackground(Colour.WHITE);
		nFormat.setAlignment(Alignment.CENTRE);
		nFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		nFormat.setFont(new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK));

		WritableCellFormat tFormat= new WritableCellFormat(NumberFormats.TEXT);
		tFormat.setBackground(Colour.WHITE);
		tFormat.setAlignment(Alignment.CENTRE);
		tFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		tFormat.setFont(new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK));


		// 헤더를 선언할때 사용
		Label label = null;
        for (int i = 0; i < titles.length; i++) {
        	/*if( i < 9 ){
        		label = new Label(i, 0, titles[i], t1Format);
        	}else{
        		label = new Label(i, 0, titles[i], t2Format);
        	}*/

    		label = new Label(i, 0, titles[i], t1Format);

            wSheet.addCell(label);
        }

        // 내용을 쓸때 사용
        String value = null;
        int size = list.size();
        for(int idx = 0; idx < size; idx++) {
        	DataMap dataMap = (DataMap)list.get(idx);
        	for(int i=0; i < titles.length; i++) {
        		value = StringUtil.nvlStr(dataMap.getString(columns[i]));
        		/*if( i < 9 ){
        			label = new Label(i, 1+idx, value, tFormat);
            	}else if( i == 9 ){
            		label = new Label(i, 1+idx, value, nFormat);
            	}else{
            		label = new Label(i, 1+idx, value, tFormat);
            	}*/

        		label = new Label(i, 1+idx, value, tFormat);
        		wSheet.setColumnView(i, 15);
        		wSheet.addCell(label);
        	}
        }

        return wSheet;
	}

}
