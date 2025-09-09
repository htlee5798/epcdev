package com.lottemart.epc.delivery.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ibm.icu.util.StringTokenizer;
import com.lcnjf.util.DateUtil;
import com.lottemart.common.code.service.CommonCodeService;
import com.lottemart.common.exception.AlertException;
import com.lottemart.common.sms.model.LtsmsVO;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.RequestUtils;
import com.lottemart.common.util.RestAPIUtil;
import com.lottemart.common.util.RestConst;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.service.CommonService;
import com.lottemart.epc.delivery.model.PSCMDLV0006VO;
import com.lottemart.epc.delivery.service.PSCMDLV0006Service;
import com.lottemart.epc.util.Utils;
import com.lottemart.extend.util.MiscUtils;
import com.lottemart.utils.notifier.KakaoTalkNotifier;
import com.lottemart.utils.notifier.NotifierFactory;
import com.lottemart.utils.notifier.NotifierType;
import com.lottemart.utils.notifier.typehandlers.ServerType;
import com.lottemart.utils.notifier.typehandlers.ServiceAppType;

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
import lcn.module.common.util.StringUtil;
import lcn.module.common.views.AjaxJsonModelHelper;
import lcn.module.framework.property.ConfigurationService;

@Controller
public class PSCMDLV0006Controller {

	private static final Logger logger = LoggerFactory.getLogger(PSCMDLV0006Controller.class);

	@Resource(name = "commonNotifier")
	private NotifierFactory notifierFactory;

	@Autowired
	private ConfigurationService config;

	@Autowired
	private PSCMDLV0006Service pscmdlv0006Service;

	@Autowired
	private CommonService commonService;

	/**
	 * 공통코드서비스
	 */
	@Autowired
	private CommonCodeService commonCodeService;

	private final String[] titles = { 
			"주문일자", 
			"상품명", 
			"옵션", 
			"상품가격", 
			"주문수량", 
			"배송비", 
			"주문번호", 
			"배송지순번", 
			"협력업체ID", 
			"운송장번호",
			"추가송장번호", 
			"택배사코드", 
			"배송상태유형(발송예정:03 배송중:09)" };

	private final String[] columns = { 
			"ORD_DY", 
			"PROD_NM", 
			"ITEM_OPTION", 
			"TOT_SELL_AMT", 
			"ORD_QTY", 
			"DELIV_AMT",
			"ORDER_ID", 
			"DELIVERY_ID", 
			"VEN_CD", 
			"", 
			"", 
			"", 
			"" };

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("delivery/selectDeliveryDlayBlockYn.do")
	public ModelAndView selectDeliveryDlayBlockYn(HttpServletRequest request) throws Throwable {

		String blockYn = "N";
		try {
			blockYn = pscmdlv0006Service.selectDeliveryDlayBlockYn(request.getParameter("ordDy"));
			return AjaxJsonModelHelper.create(blockYn);
		} catch (Exception e) {
			logger.debug(e.toString());
		}

		return AjaxJsonModelHelper.create(blockYn);
	}

	@RequestMapping(value = "delivery/selectPartnerFirmsOrderList.do")
	public String selectPartnerFirmsOrderList(@ModelAttribute("searchVO") PSCMDLV0006VO searchVO, HttpServletRequest request, ModelMap model) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);

		// 주문일자 조회조건이 없을 경우
		if (StringUtil.isNVL(searchVO.getStartDate()) || StringUtil.isNVL(searchVO.getEndDate())) {

			String from_date = ""; // 기간(시작)
			String to_date = ""; // 기간(끝)

			Calendar NowDate = Calendar.getInstance();
			Calendar NowDate2 = Calendar.getInstance();
			NowDate.add(Calendar.DATE, 0);
			NowDate2.add(Calendar.DATE, -7);

			String today_date = Utils.formatDate(NowDate.getTime(), "yyyy-MM-dd");
			String today_date2 = Utils.formatDate(NowDate2.getTime(), "yyyy-MM-dd");

			// 초최 오픈시 Default 값 세팅
			if (from_date == null || "".equals(from_date)) {
				from_date = today_date2;
			}
			if (to_date == null || "".equals(to_date)) {
				to_date = today_date;
			}

			searchVO.setStartDate(from_date);
			searchVO.setEndDate(to_date);
			searchVO.setDeliStatusCode("01");
			searchVO.setDeliTypeCd("%"); // 배송구분 전체

			// 20181113 - {반품접수},{교환접수} 주문건이 발생핼을 때 사용자가 재검색해야 하는 것을 방지하기 위해
			// searchVO.setSaleStsCd("11"); //결제완료
			searchVO.setSaleStsCd("%"); // 결제완료

			searchVO.setDateGbn("1"); // 주문일
		}

		searchVO.setFromDate(StringUtils.defaultIfEmpty(searchVO.getStartDate(), "").replaceAll("-", ""));
		searchVO.setToDate(StringUtils.defaultIfEmpty(searchVO.getEndDate(), "").replaceAll("-", ""));

		// 온라인 점포 리스트
		List<DataMap> strCdList = pscmdlv0006Service.selectAllOnlineStore();
		model.addAttribute("strCdList", strCdList);

		searchVO.setMajorCd("DE014");
		List<DataMap> DE014List = pscmdlv0006Service.getTetCodeList(searchVO);

		searchVO.setMajorCd("DE011");
		List<DataMap> DE011List = pscmdlv0006Service.getTetCodeList(searchVO);

		searchVO.setMajorCd("OR002");
		List<DataMap> OR002List = pscmdlv0006Service.getTetCodeList(searchVO);

		searchVO.setMajorCd("DE017");
		List<DataMap> DE017List = pscmdlv0006Service.getTetCodeList(searchVO);

		model.addAttribute("DE014List", DE014List); // 배송상태 코드
		model.addAttribute("DE011List", DE011List); // 배송회사 코드
		model.addAttribute("OR002List", OR002List); // 주문상태 코드
		model.addAttribute("DE017List", DE017List); // 지연/불가사유 코드

		// 20181113 - {반품접수},{교환접수} 주문건이 발생핼을 때 사용자가 재검색해야 하는 것을 방지하기 위해
		// 배송조회
		if (StringUtils.defaultIfEmpty(searchVO.getSaleStsCd(), "").equals("")) {
			// searchVO.setSaleStsCd("11"); //결제완료
			searchVO.setSaleStsCd("%"); // 결제완료
		}

		// 선택한 vendorId가null 이거나 repVendorId ( 대표협력업체 ) 일때 협력사 전체 검색
		if (searchVO.getVendorId() == null || searchVO.getVendorId().length == 0 || (searchVO.getVendorId() != null && Arrays.asList(searchVO.getVendorId()).contains(epcLoginVO.getRepVendorId()))) {
			searchVO.setVendorId(epcLoginVO.getVendorId());
		} else {
			request.setAttribute("vendorId", searchVO.getVendorId()[0].toString());
		}

		// 선택한 vendor가 openappi 업체일 경우 전체 검색
		List<EpcLoginVO> openappiVendorId = commonService.selectOpenappiVendor();
		int totalSize = openappiVendorId.size();

		for (int l = 0; totalSize > l; l++) {
			if (openappiVendorId.get(l).getRepVendorId().equals(searchVO.getVendorId()[0].toString().replace("[", "").replace("]", "").trim())) {
				searchVO.setVendorId(epcLoginVO.getVendorId());
			}
		}

		searchVO.setStrCd(config.getString("online.rep.str.cd"));
		searchVO.setVendorMode(null);

		if ("06".equals(epcLoginVO.getVendorTypeCd())) {
			if ((searchVO.getVendorId()[0].toString()).indexOf("T") < 0) {
				searchVO.setVendorMode("V");
			}
		}
		if (searchVO.getDateGbn() == null || "".equals(searchVO.getDateGbn())) {
			throw new AlertException("조회조건(dateGbn)이 누락되었습니다.");
		} else {
			if ("1:2:3".indexOf(searchVO.getDateGbn()) < 0) {
				throw new AlertException("조회조건(dateGbn)값이 맞지않습니다.");
			}
		}

		List<DataMap> list = pscmdlv0006Service.selectPartnerFirmsList(searchVO);
		if (StringUtils.defaultIfEmpty(searchVO.getFlag(), "").equals("") && list != null && list.size() > 0) {
			searchVO.setFlag("success");
		} else {
			searchVO.setFlag("zero");
		}
		model.addAttribute("list", list);
		model.addAttribute("listSize", list.size());
		model.addAttribute("searchVO", searchVO);

		DE014List = null;
		DE011List = null;
		OR002List = null;
		DE017List = null;
		list = null;

		return "delivery/PSCMDLV0006";
	}

	@RequestMapping(value = "delivery/selectPartnerFirmsDeliList.do")
	public String selectPartnerFirmsOrderLists(@ModelAttribute("searchVO") PSCMDLV0006VO searchVO, HttpServletRequest request, ModelMap model) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);

		// 주문일자 조회조건이 없을 경우
		if (StringUtil.isNVL(searchVO.getStartDate()) || StringUtil.isNVL(searchVO.getEndDate())) {

			String from_date = ""; // 기간(시작)
			String to_date = ""; // 기간(끝)

			Calendar NowDate = Calendar.getInstance();
			Calendar NowDate2 = Calendar.getInstance();
			NowDate.add(Calendar.DATE, 0);
			NowDate2.add(Calendar.DATE, -7);

			String today_date = Utils.formatDate(NowDate.getTime(), "yyyy-MM-dd");
			String today_date2 = Utils.formatDate(NowDate2.getTime(), "yyyy-MM-dd");

			// 초최 오픈시 Default 값 세팅
			if (from_date == null || "".equals(from_date)) {
				from_date = today_date2;
			}
			if (to_date == null || "".equals(to_date)) {
				to_date = today_date;
			}

			searchVO.setStartDate(from_date);
			searchVO.setEndDate(to_date);
			searchVO.setDeliStatusCode("01");
			searchVO.setDeliTypeCd("04");

			// 20181113 - {반품접수},{교환접수} 주문건이 발생핼을 때 사용자가 재검색해야 하는 것을 방지하기 위해
			// searchVO.setSaleStsCd("11"); //결제완료
			searchVO.setSaleStsCd("%"); // 결제완료

			searchVO.setDateGbn("1"); // 주문일
		}

		searchVO.setFromDate(searchVO.getStartDate().replaceAll("-", ""));
		searchVO.setToDate(searchVO.getEndDate().replaceAll("-", ""));

		// 온라인 점포 리스트
		List<DataMap> strCdList = pscmdlv0006Service.selectAllOnlineStore();
		model.addAttribute("strCdList", strCdList);

		searchVO.setMajorCd("DE014");
		List<DataMap> DE014List = pscmdlv0006Service.getTetCodeList(searchVO);

		searchVO.setMajorCd("DE011");
		List<DataMap> DE011List = pscmdlv0006Service.getTetCodeList(searchVO);

		searchVO.setMajorCd("OR002");
		List<DataMap> OR002List = pscmdlv0006Service.getTetCodeList(searchVO);

		model.addAttribute("DE014List", DE014List); // 배송상태 코드
		model.addAttribute("DE011List", DE011List); // 배송회사 코드
		model.addAttribute("OR002List", OR002List); // 주문상태 코드

		// 20181113 - {반품접수},{교환접수} 주문건이 발생핼을 때 사용자가 재검색해야 하는 것을 방지하기 위해
		// 배송조회
		if (searchVO.getSaleStsCd().equals("")) {
			// searchVO.setSaleStsCd("11"); //결제완료
			searchVO.setSaleStsCd("%"); // 결제완료
		}

		// 선택한 vendorId가null 이거나 repVendorId ( 대표협력업체 ) 일때 협력사 전체 검색
		if (searchVO.getVendorId() == null || searchVO.getVendorId().length == 0 || epcLoginVO.getRepVendorId().equals(searchVO.getVendorId()[0].toString())) {
			searchVO.setVendorId(epcLoginVO.getVendorId());
		} else {
			request.setAttribute("vendorId", searchVO.getVendorId()[0].toString());
		}

		// 선택한 vendor가 openappi 업체일 경우 전체 검색
		List<EpcLoginVO> openappiVendorId = commonService.selectOpenappiVendor();
		for (int l = 0; openappiVendorId.size() > l; l++) {
			if (openappiVendorId.get(l).getRepVendorId().equals(searchVO.getVendorId()[0].toString().replace("[", "").replace("]", "").trim())) {
				searchVO.setVendorId(epcLoginVO.getVendorId());
			}
		}

		searchVO.setStrCd(config.getString("online.rep.str.cd"));
		searchVO.setVendorMode(null);

		if ("06".equals(epcLoginVO.getVendorTypeCd())) {
			if ((searchVO.getVendorId()[0].toString()).indexOf("T") < 0) {
				searchVO.setVendorMode("V");
			}
		}
		if (searchVO.getDateGbn() == null || "".equals(searchVO.getDateGbn())) {
			throw new AlertException("조회조건(dateGbn)이 누락되었습니다.");
		} else {
			if ("1:2:3".indexOf(searchVO.getDateGbn()) < 0) {
				throw new AlertException("조회조건(dateGbn)값이 맞지않습니다.");
			}
		}

		List<DataMap> list = pscmdlv0006Service.selectPartnerFirmsList(searchVO);
		if (searchVO.getFlag().equals("") && list != null && list.size() > 0) {
			searchVO.setFlag("success");
		} else {
			searchVO.setFlag("zero");
		}
		model.addAttribute("list", list);
		model.addAttribute("listSize", list.size());
		model.addAttribute("searchVO", searchVO);
		return "delivery/PSCMDLV0006";
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
		response.reset();
		response.setContentType("application/x-msdownload;charset=UTF-8");
		if (request.getHeader("User-Agent").indexOf("MSIE 5.5") > -1) {
			response.setHeader("Content-Disposition", "filename=PSCMDLV000601.xls;");
		} else {
			response.setHeader("Content-Disposition", "attachment; filename=PSCMDLV000601.xls;");
		}
		response.setHeader("Content-Transfer-Encoding", "binary;");
	}

	private WritableWorkbook getWorkBook(HttpServletResponse response) throws BiffException {

		WritableWorkbook workbook = null;

		try {
			WorkbookSettings ws = new WorkbookSettings();
			// ws.setLocale(Locale.KOREA);
			workbook = Workbook.createWorkbook(response.getOutputStream(), ws);
		} catch (IOException e) {
			logger.error(e + "");
		}

		return workbook;
	}

	private WritableSheet createWritableSheet(WritableWorkbook wWorkbook) {
		return wWorkbook.createSheet("PSCMDLV000601", 0);
	}

	private WritableSheet DataToExcelBind(WritableSheet wSheet, List<DataMap> list, String[] titles, String[] columns) throws RowsExceededException, WriteException {
		// 일반 헤더 포멧 정의
		WritableCellFormat t1Format = new WritableCellFormat(NumberFormats.TEXT);
		t1Format.setBackground(Colour.GREY_25_PERCENT);
		t1Format.setAlignment(Alignment.CENTRE);
		t1Format.setBorder(Border.ALL, BorderLineStyle.THIN);
		t1Format.setFont(new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLUE));

		// 입력 헤더 포멧 정의
		WritableCellFormat t2Format = new WritableCellFormat(NumberFormats.INTEGER);
		t2Format.setBackground(Colour.YELLOW);
		t2Format.setAlignment(Alignment.CENTRE);
		t2Format.setBorder(Border.ALL, BorderLineStyle.THIN);
		t2Format.setFont(new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLUE));

		// 컬럼포멧 정의
		WritableCellFormat nFormat = new WritableCellFormat(NumberFormats.INTEGER);
		nFormat.setBackground(Colour.WHITE);
		nFormat.setAlignment(Alignment.CENTRE);
		nFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		nFormat.setFont(new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK));

		WritableCellFormat tFormat = new WritableCellFormat(NumberFormats.TEXT);
		tFormat.setBackground(Colour.WHITE);
		tFormat.setAlignment(Alignment.CENTRE);
		tFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		tFormat.setFont(new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK));

		// 헤더를 선언할때 사용
		Label label = null;
		for (int i = 0; i < titles.length; i++) {
			if (i < 9) {
				label = new Label(i, 0, titles[i], t1Format);
			} else {
				label = new Label(i, 0, titles[i], t2Format);
			}
			wSheet.addCell(label);
		}

		// 내용을 쓸때 사용
		String value = null;
		int size = list.size();
		for (int idx = 0; idx < size; idx++) {
			DataMap dataMap = (DataMap) list.get(idx);
			for (int i = 0; i < titles.length; i++) {
				value = StringUtil.nvlStr(dataMap.getString(columns[i]));
				if (i < 9) {
					label = new Label(i, 1 + idx, value, tFormat);
				} else if (i == 9) {
					label = new Label(i, 1 + idx, value, nFormat);
				} else {
					label = new Label(i, 1 + idx, value, tFormat);
				}

				wSheet.setColumnView(i, 15);
				wSheet.addCell(label);
			}
		}

		return wSheet;
	}

	@RequestMapping(value = "delivery/selectPartnerFirmsOrderListExcelForm.do")
	public void selectPartnerFirmsOrderListExcelForm(@ModelAttribute("searchVO") PSCMDLV0006VO searchVO, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);

		searchVO.setFromDate(searchVO.getStartDate().replaceAll("-", ""));
		searchVO.setToDate(searchVO.getEndDate().replaceAll("-", ""));
		searchVO.setSaleStsCd("11"); // 결제완료
		// 협력사코드 전체를 선택한 경우 로그인 세션에 있는 협력사 코드 전체를 설정한다.
		if (searchVO.getVendorId() == null || searchVO.getVendorId().length == 0) {
			searchVO.setVendorId(epcLoginVO.getVendorId());
		} else {
			request.setAttribute("vendorId", searchVO.getVendorId()[0].toString());
		}

		searchVO.setVendorMode(null);

		if ("06".equals(epcLoginVO.getVendorTypeCd())) {
			if ((searchVO.getVendorId()[0].toString()).indexOf("T") < 0) {
				searchVO.setVendorMode("V");
			}
		}
		if (searchVO.getDateGbn() == null || "".equals(searchVO.getDateGbn())) {
			throw new AlertException("조회조건(dateGbn)이 누락되었습니다.");
		} else {
			if ("1:2:3".indexOf(searchVO.getDateGbn()) < 0) {
				throw new AlertException("조회조건(dateGbn)값이 맞지않습니다.");
			}
		}

		List<DataMap> list = pscmdlv0006Service.selectPartnerFirmsListExcel(searchVO);

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
		} catch (Exception e) {
			printMessage(response);
		}
	}

	@RequestMapping(value = "delivery/selectPartnerFirmsOrderListExcel.do")
	public String selectPartnerFirmsOrderListExcel(@ModelAttribute("searchVO") PSCMDLV0006VO searchVO, HttpServletRequest request, ModelMap model) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);

		searchVO.setFromDate(searchVO.getStartDate().replaceAll("-", ""));
		searchVO.setToDate(searchVO.getEndDate().replaceAll("-", ""));

		// 선택한 vendorId가null 이거나 repVendorId ( 대표협력업체 ) 일때 협력사 전체 검색
		if (searchVO.getVendorId() == null || searchVO.getVendorId().length == 0 || epcLoginVO.getRepVendorId().equals(searchVO.getVendorId()[0].toString())) {
			searchVO.setVendorId(epcLoginVO.getVendorId());
		} else {
			request.setAttribute("vendorId", searchVO.getVendorId()[0].toString());
		}

		// 선택한 vendor가 openappi 업체일 경우 전체 검색
		List<EpcLoginVO> openappiVendorId = commonService.selectOpenappiVendor();
		for (int l = 0; openappiVendorId.size() > l; l++) {
			if (openappiVendorId.get(l).getRepVendorId().equals(searchVO.getVendorId()[0].toString().replace("[", "").replace("]", "").trim())) {
				searchVO.setVendorId(epcLoginVO.getVendorId());
			}
		}

		searchVO.setStrCd(config.getString("online.rep.str.cd"));
		searchVO.setVendorMode(null);

		if ("06".equals(epcLoginVO.getVendorTypeCd())) {
			if ((searchVO.getVendorId()[0].toString()).indexOf("T") < 0) {
				searchVO.setVendorMode("V");
			}
		}
		if(searchVO.getDateGbn() == null || "".equals(searchVO.getDateGbn())){
			throw new AlertException("조회조건(dateGbn)이 누락되었습니다.");
		} else {
			if ("1:2:3".indexOf(searchVO.getDateGbn()) < 0) {
				throw new AlertException("조회조건(dateGbn)값이 맞지않습니다.");
			}
		}
		List<DataMap> list = pscmdlv0006Service.selectPartnerFirmsList(searchVO);

		model.addAttribute("list", list);
		model.addAttribute("searchVO", searchVO);
		return "delivery/PSCMDLV000602";
	}

	@RequestMapping(value = "delivery/updatePartnerFirmsItem.do")
	public String updatePartnerFirmsItem(@ModelAttribute("searchVO") PSCMDLV0006VO vo, HttpServletRequest request, ModelMap model) throws Exception {
		String codeList = vo.getCodeList();
		StringTokenizer _stoken = new StringTokenizer(codeList, "■");

		String counselSeq = "";
		StringBuffer smsMsg = new StringBuffer();

		while (_stoken.hasMoreTokens()) {
			String temp_cd = _stoken.nextToken();
			String[] array_cd = temp_cd.replace(":", ": ").split(":");

			// 발송불가, 배송지연일 경우 counselsql 생성
			if ("02".equals(array_cd[2].trim()) || "07".equals(array_cd[2].trim())) {
				counselSeq = pscmdlv0006Service.selectCounselSeq();
			}

			if (array_cd.length > 1) {
				if (StringUtils.isEmpty(array_cd[6].trim())) {
					throw new AlertException("배송번호(deliNo)가 누락되었습니다.");
				}
				vo.setOrderId(array_cd[0].trim());
				vo.setOrderItemSeq(array_cd[1].trim());
				vo.setVenDeliStatusCd(array_cd[2].trim());
				vo.setHodecoCd(array_cd[3].replace("%", "").trim());
				vo.setHodecoInvoiceNo(array_cd[4].replace("X", "").trim());
				vo.setDeliveryId(array_cd[5].trim());
				vo.setDeliNo(array_cd[6].trim());
				vo.setDeliStatusCd(array_cd[7].trim());
				vo.setVenCd(array_cd[8].trim());
				vo.setInvoiceSeq("1");
				vo.setRegId(array_cd[8].trim());
				vo.setModId(array_cd[8].trim());
				// 20160530 추가
				vo.setHodecoAddInvoiceNo(array_cd[9].trim());
				vo.setSndPrarDy(array_cd[10].trim().replaceAll("-", ""));
				vo.setDlayUnavlReasonCd(array_cd[11].trim());

				// 발송불가일 경우 DlayUnavlDtlReason에 counselSeq를 넣어줌
				if ("02".equals(array_cd[2].trim())) {
					vo.setDlayUnavlDtlReason(counselSeq);
				} else {
					// 배송지연,불가 상세사유 입력값 예외처리
					if(StringUtil.getByteLength(array_cd[12].trim())>150) {
						throw new AlertException("지연/불가상세사유(dlayUnavlDtlReason)의 입력값이 150byte를 초과했습니다.");
					}
					vo.setDlayUnavlDtlReason(array_cd[12].trim());
				}
				vo.setOnlnDeliTypeCd(array_cd[13].trim());
				vo.setCounselContent(array_cd[14].trim());

				/* 20181016 테스트 삭제예정 */
				vo.setOnlineProdTypeCd(array_cd[15].trim());
				vo.setOrgOnlnDeliTypeCd(array_cd[16].trim());
				/* -------------20181016 테스트 삭제예정 */

				// 배송중 상태 추가
				if ("09".equals(array_cd[2].trim())) {
					vo.setDeliStatusCd("45");
				}
				// 배송완료 주석처리
				// if(array_cd[2].trim().equals("05")){
				// vo.setDeliStatusCd("51");
				// }

				// 배송 상태 역행 방어 로직
				if(!pscmdlv0006Service.selectDeliveryReverseCheck(vo)){
					continue;
				}

				pscmdlv0006Service.updatePartnerFirmsOrderItem(vo);

				pscmdlv0006Service.updatePartnerFirmsDeliMst(vo);

				// EC 주문(신주문)인지 여부 조회
				String ecOrderYn = pscmdlv0006Service.selectEcOrderYn(vo.getDeliNo());

				// 택배사와 송장번호가 있으면 무조건 저장
				/* 20181108 테스트 삭제예정 */
				if (vo.getHodecoCd() != null) {
					if (vo.getHodecoCd().length() > 0 && vo.getHodecoInvoiceNo().length() > 0) {

						if (StringUtil.getByteLength(vo.getHodecoCd()) > 5) {
							throw new AlertException("택배사코드를 확인해주세요.");
						}

						if (pscmdlv0006Service.selectPartnerFirmsHodecoInfoCnt(vo) > 0) {
							pscmdlv0006Service.updatePartnerFirmsHodecoInfo(vo);
						} else {
							pscmdlv0006Service.insertPartnerFirmsHodecoInfo(vo);
						}
					}
				}

				// 발송불가, 배송지연일 경우 TOR_COUNSEL에 자료 저장
				if ("02".equals(array_cd[2].trim()) || "07".equals(array_cd[2].trim())) {
					if ("02".equals(array_cd[2].trim())) {
						vo.setTitle("주문번호:" + vo.getOrderId() + " 배송번호:" + vo.getDeliNo() + " 발송불가 처리요청");
						vo.setCounselSeq(counselSeq);
						vo.setContent(vo.getCounselContent());
						// FIXME shin..
						/*
						 * 이렇게 수정할 경우 반대임... -_-
						 */
						vo.setClmLgrpCd("QNAt0"); // - 반품/결품 system
						vo.setClmMgrpCd("QNAt2"); // - 결품 system

						/*
						 * cc inbound 주문상세 상담이력 문의유형에는 결품,결품안내로 보이고 나의상담이력
						 * 문의유형에는 null임... vo.setClmLgrpCd("010");
						 * vo.setClmMgrpCd("0012");
						 */
					} else if ("07".equals(array_cd[2].trim())) {
						vo.setTitle("주문번호:" + vo.getOrderId() + " 배송번호:" + vo.getDeliNo() + " 배송지연 처리요청");
						vo.setCounselSeq(counselSeq);
						vo.setContent(vo.getDlayUnavlDtlReason());
						vo.setClmLgrpCd("600");
						vo.setClmMgrpCd("0601");

						/* 배송 지연인 경우 고객 알림 문자를 발송한다. */
						String callBackNo = "1577-2500";
						String deliveryDlayDate = DateUtil.string2String(vo.getSndPrarDy(), "yyyyMMdd", "yyyy-MM-dd");

						// sms발송대상 정보 조회
						List<DataMap> list = pscmdlv0006Service.selectDeliveryDlaySMSTarget(vo);

						if (list.size() > 0) {// 상품존재

							DataMap info = list.get(0);
							String cellNo = info.getString("CELL_NO");
							String smsYn = info.getString("SMS_YN");
							String prodNm = info.getString("PROD_NM");

							// 상품명 최대 20자
							String msg = prodNm.length() > 20 ? prodNm.substring(0, 20) + "... " : prodNm;
							if (list.size() > 1) {
								msg += "외 " + (list.size() - 1) + "건";
							}

							if ("Y".equals(smsYn)) { // 핸드폰 번호 존재
								smsMsg.delete(0, smsMsg.length());
								smsMsg.append("[롯데마트몰]\n");
								smsMsg.append("주문하신 상품이 일시품절되어 발송이 지연되고 있습니다.\n");
								smsMsg.append("발송 예정일은 다음과 같습니다.\n\n");
								smsMsg.append("상품명 : ").append(msg);
								smsMsg.append("\n");
								smsMsg.append("발송예정일 : ").append(deliveryDlayDate);

								DataMap dm = new DataMap();
								dm.put("V_TITLE", "롯데마트몰 배송지연 안내"); // LMS title
								dm.put("V_MSG", smsMsg.toString()); // 메시지
								dm.put("V_FROM_NUM", callBackNo); // 발신자 번호, ex)15772500
								dm.put("V_TO_NUM", cellNo); // 수신자 번호, ex)010xxxxxxxx
								dm.put("V_CH_NM", "O03"); // 발신채널 SCM 서비스 코드값
								pscmdlv0006Service.insertSendLMS(dm);
							}
						}

					}

					pscmdlv0006Service.insertTorCounsel(vo);
					// 결품인 경우 DL_API_0006 결품정보 전송
					if ("02".equals(array_cd[2].trim()) && "Y".equals(ecOrderYn)) {
						Map<String, String> apiParam = new HashMap<String, String>();
						apiParam.put("DELI_NO", vo.getDeliNo());
						String result = "";
						try {
							RestAPIUtil rest = new RestAPIUtil();
							result = rest.sendRestCall(RestConst.DL_API_0006, HttpMethod.POST, apiParam, 5000, true);
						} catch (Exception e) {
							logger.error(e.getMessage());
						}
					}
				} else if ("03".equals(array_cd[2].trim()) && "Y".equals(ecOrderYn)) {
					// 배송중으로 처리할 경우 DL_API_0007 출고지시 전송
					Map<String, String> apiParam = new HashMap<String, String>();
					apiParam.put("DELI_NO", vo.getDeliNo());
					apiParam.put("DELI_STATUS_CD", "31");
					String result = "";
					try {
						RestAPIUtil rest = new RestAPIUtil();
						result = rest.sendRestCall(RestConst.DL_API_0007, HttpMethod.POST, apiParam, 5000, true);
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
				} else if (("05".equals(array_cd[2].trim()) || "09".equals(array_cd[2].trim())) && "Y".equals(ecOrderYn)) { // 사방넷에서 ven_deli_status_cd를 05로 보낼 경우 배송중으로 EC 연동 하기 위해 추가
					// 배송중으로 처리할 경우 DL_API_0007 발송완료 전송
					Map<String, String> apiParam = new HashMap<String, String>();
					apiParam.put("DELI_NO", vo.getDeliNo());
					apiParam.put("DELI_STATUS_CD", "43");
					String result = "";
					try {
						RestAPIUtil rest = new RestAPIUtil();
						result = rest.sendRestCall(RestConst.DL_API_0007, HttpMethod.POST, apiParam, 5000, true);
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
				}

			}
		}

		vo.setFlag("save");
		return selectPartnerFirmsOrderList(vo, request, model);
	}

	/**
	 *
	 * Desc : SMS->LMS발송으로 변경 (2016.04.27)
	 * 
	 * @Method Name : insertSendSMS
	 * @param vo
	 * @param request
	 * @param ltsmsVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "delivery/insertSendSMS.do")
	public String insertSendSMS(@ModelAttribute("searchVO") PSCMDLV0006VO vo, HttpServletRequest request, LtsmsVO ltsmsVO, ModelMap model) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);
		// 선택한 vendorId가null 이거나 repVendorId ( 대표협력업체 ) 일때 협력사 전체 검색
		if (vo.getVendorId() == null || vo.getVendorId().length == 0 || epcLoginVO.getRepVendorId().equals(vo.getVendorId()[0].toString())) {
			vo.setVendorId(epcLoginVO.getVendorId());
		} else {
			request.setAttribute("vendorId", vo.getVendorId()[0].toString());
		}

		// 선택한 vendor가 openappi 업체일 경우 전체 검색
		List<EpcLoginVO> openappiVendorId = commonService.selectOpenappiVendor();
		for (int l = 0; openappiVendorId.size() > l; l++) {
			if (openappiVendorId.get(l).getRepVendorId().equals(vo.getVendorId()[0].toString().replace("[", "").replace("]", "").trim())) {
				vo.setVendorId(epcLoginVO.getVendorId());
			}
		}

		vo.setAdminId(epcLoginVO.getAdminId());
		vo.setFromDate(vo.getStartDate().replaceAll("-", ""));
		vo.setToDate(vo.getEndDate().replaceAll("-", ""));

		String deliNo = "";
		String orderId = "";
		String deliveryId = "";
		// String hodecoNm = "";
		String hodecoCd = "";
		String toPsnNm = "";
		String hodecoInvoiceNo = "";
		String recpPsnCellNo = "";
		String smsSendYn = "";
		String venCd = "";
		String callBackNo = "1577-2500";
		String ordRtnDivnCd = "";
		String ordDy = "";
		String deliveryDy = "";
		String prodNm = "";

		StringBuffer smsMsg = new StringBuffer();

		vo.setVendorMode(null);
		if ("06".equals(epcLoginVO.getVendorTypeCd())) {

			if ((vo.getVendorId()[0].toString()).indexOf("T") < 0) {
				vo.setVendorMode("V");
			}
		}

		// sms발송대상 List조회
		List<DataMap> list = pscmdlv0006Service.selectPartnerFirmsSendSMSList(vo);

		if (list != null && list.size() > 0) {

			for (int i = 0; i < list.size(); i++) {

				deliNo = list.get(i).getString("DELI_NO");
				orderId = list.get(i).getString("ORDER_ID");
				hodecoInvoiceNo = list.get(i).getString("HODECO_INVOICE_NO");
				hodecoCd = list.get(i).getString("HODECO_CD");
				toPsnNm = list.get(i).getString("TO_PSN_NM");
				recpPsnCellNo = list.get(i).getString("RECP_PSN_CELL_NO");
				smsSendYn = list.get(i).getString("SMS_SEND_YN");
				venCd = list.get(i).getString("VEN_CD");
				deliveryId = list.get(i).getString("DELIVERY_ID");
				ordRtnDivnCd = list.get(i).getString("ORD_RTN_DIVN_CD");
				ordDy = list.get(i).getString("ORD_DY");
				deliveryDy = list.get(i).getString("DELIVERY_DY");
				prodNm = list.get(i).getString("PROD_NM");

				if ("N".equals(smsSendYn)) {
					// 알림톡발송
					String runTimeServer = System.getProperty("server.type");

					ServerType serverType = ServerType.STG;

					if ("prd".equalsIgnoreCase(runTimeServer)) {
						serverType = ServerType.PROD;
					}

					String kakoaTmplCode = "";
					String failCallBackUrl = "";

					Map<String, String> contentDataMap = new HashMap<String, String>();
					HashMap<String, String> buttonInfo = new HashMap<String, String>();

					if (!"11".equals(ordRtnDivnCd)) {
						kakoaTmplCode = "lmsc0001";

						contentDataMap.put("msg_ord_date", ordDy);
						contentDataMap.put("msg_send_date", deliveryDy);

						failCallBackUrl = config.getString("admin.server.url") + "/bos/sms/kakaotalk_failover/send_sms.do?smsType=SCM_DELIVERY_SEND&tmplCode=" + kakoaTmplCode + "&deliNo=" + deliNo + "&msg_ord_date=" + ordDy + "&msg_send_date=" + deliveryDy;
						buttonInfo.put("mobile_url", "/mobile/mypage/selectDeliveryStatus.do?hodecoCd=" + hodecoCd + "&hodecoInvoiceNo=" + hodecoInvoiceNo + "&orderId=" + orderId);
						buttonInfo.put("pc_url", "/mymart/popup/selectDeliveryStatus.do?hodecoCd=" + hodecoCd + "&hodecoInvoiceNo=" + hodecoInvoiceNo + "&orderId=" + orderId);

					} else { // 업체배송 - 명절주문 000030081902 180060992803
						kakoaTmplCode = "lmsc0002";

						contentDataMap.put("deliNo", deliNo);
						contentDataMap.put("ordDy", ordDy);
						contentDataMap.put("deliveryDy", deliveryDy);
						contentDataMap.put("prodNm", prodNm);
						contentDataMap.put("toPsnNm", toPsnNm);

						failCallBackUrl = config.getString("admin.server.url") + "/bos/sms/kakaotalk_failover/send_sms.do?smsType=SCM_DELIVERY_SEND&tmplCode=" + kakoaTmplCode + "&deliNo=" + deliNo + "&ordDy=" + ordDy + "&deliveryDy=" + deliveryDy + "&prodNm=" + prodNm + "&toPsnNm=" + toPsnNm;
						buttonInfo.put("HODECO_INVOICE_NO", hodecoInvoiceNo);
					}

					Map<Integer, HashMap<String, String>> buttonLinkData = new HashMap<Integer, HashMap<String, String>>();
					buttonLinkData.put(1, buttonInfo);
					KakaoTalkNotifier kkoNotifier = (KakaoTalkNotifier) notifierFactory.getNotifier(NotifierType.KAKAOTALK);
					kkoNotifier.send(recpPsnCellNo, callBackNo, kakoaTmplCode, failCallBackUrl, ServiceAppType.SCM, contentDataMap, buttonLinkData, null, serverType);

					// 알림톡 발송 후 발송여부 Y 처리
					vo.setSmsSendYn("Y");
					vo.setDeliNo(deliNo);
					vo.setModId(venCd);
					vo.setOrderId(orderId);
					vo.setDeliveryId(deliveryId);
					pscmdlv0006Service.updateSmsSendYn(vo);
				}
			}
		}

		return selectPartnerFirmsOrderList(vo, request, model);
	}

	@RequestMapping("/delivery/selectCustInfo")
	public ModelAndView selectCustInfo(@ModelAttribute("searchVO") PSCMDLV0006VO searchVO, HttpServletRequest request, ModelMap model) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);

		searchVO.setFromDate(searchVO.getStartDate().replaceAll("-", ""));
		searchVO.setToDate(searchVO.getEndDate().replaceAll("-", ""));

		// 협력사코드 전체를 선택한 경우 로그인 세션에 있는 협력사 코드 전체를 설정한다.
		if (searchVO.getVendorId() == null || searchVO.getVendorId().length == 0 || epcLoginVO.getRepVendorId().equals(searchVO.getVendorId()[0].toString())) {
			searchVO.setVendorId(epcLoginVO.getVendorId());
		} else {
			request.setAttribute("vendorId", searchVO.getVendorId()[0].toString());
		}

		searchVO.setStrCd(config.getString("online.rep.str.cd"));
		searchVO.setVendorMode(null);

		if ("06".equals(epcLoginVO.getVendorTypeCd())) {
			if ((searchVO.getVendorId()[0].toString()).indexOf("T") < 0) {
				searchVO.setVendorMode("V");
			}
		}
		if (searchVO.getDateGbn() == null || "".equals(searchVO.getDateGbn())) {
			throw new AlertException("조회조건(dateGbn)이 누락되었습니다.");
		} else {
			if ("1:2:3".indexOf(searchVO.getDateGbn()) < 0) {
				throw new AlertException("조회조건(dateGbn)값이 맞지않습니다.");
			}
		}

		List<DataMap> list = pscmdlv0006Service.selectPartnerFirmsList(searchVO);
		String status = "O";
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getString("STATUS2").equals("X")) {
				status = list.get(i).getString("STATUS2");
				break;
			}
		}
		return AjaxJsonModelHelper.create(status);
	}

	/**
	 * 배송지변경이력 Desc :
	 * @Method Name : selectDeliveryStatus
	 * @param request
	 * @return
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/delivery/selectDeliHistPopup.do")
	public String selectDeliCodePopup(@ModelAttribute("searchVO") PSCMDLV0006VO searchVO, HttpServletRequest request, ModelMap model) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);

		// 주문일자 조회조건이 없을 경우
		if (StringUtil.isNVL(searchVO.getStartDate()) || StringUtil.isNVL(searchVO.getEndDate())) {

			String from_date = ""; // 기간(시작)
			String to_date = ""; // 기간(끝)

			Calendar NowDate = Calendar.getInstance();
			Calendar NowDate2 = Calendar.getInstance();
			NowDate.add(Calendar.DATE, 0);
			NowDate2.add(Calendar.DATE, -7);

			String today_end = Utils.formatDate(NowDate.getTime(), "yyyy-MM-dd");
			String today_start = Utils.formatDate(NowDate2.getTime(), "yyyy-MM-dd");
			String today_date = Utils.formatDate(NowDate.getTime(), "yyyyMMdd");
			String today_date2 = Utils.formatDate(NowDate2.getTime(), "yyyyMMdd");

			// 초최 오픈시 Default 값 세팅
			if (from_date == null || "".equals(from_date)) {
				from_date = today_date2;
			}
			if (to_date == null || "".equals(to_date)) {
				to_date = today_date;
			}

			searchVO.setDateGbn("01"); // 주문일
			searchVO.setStartDate(today_start);
			searchVO.setEndDate(today_end);
			searchVO.setFromDate(today_date2);
			searchVO.setToDate(today_date);
		}
		// 선택한 vendorId가null 이거나 repVendorId ( 대표협력업체 ) 일때 협력사 전체 검색
		if (searchVO.getVendorId() == null || searchVO.getVendorId().length == 0 || epcLoginVO.getRepVendorId().equals(searchVO.getVendorId()[0].toString())) {
			searchVO.setVendorId(epcLoginVO.getVendorId());
		} else {
			request.setAttribute("vendorId", searchVO.getVendorId()[0].toString());
		}

		// 선택한 vendor가 openappi 업체일 경우 전체 검색
		List<EpcLoginVO> openappiVendorId = commonService.selectOpenappiVendor();
		int totalSize = openappiVendorId.size();

		for (int l = 0; totalSize > l; l++) {
			if (openappiVendorId.get(l).getRepVendorId().equals(searchVO.getVendorId()[0].toString().replace("[", "").replace("]", "").trim())) {
				searchVO.setVendorId(epcLoginVO.getVendorId());
			}
		}
		/*
		 * List<DataMap> list = pscmdlv0006Service.selectDeliHistList(searchVO);
		 * model.addAttribute("list", list); model.addAttribute("listSize",
		 * list.size());
		 */

		model.addAttribute("searchVO", searchVO);

		return "delivery/PSCMDLV000604";
	}

	@RequestMapping(value = "delivery/selectDeliHistList.do")
	public String selectDeliHistList(@ModelAttribute("searchVO") PSCMDLV0006VO searchVO, HttpServletRequest request, ModelMap model) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);

		// 주문일자 조회조건이 없을 경우
		if (StringUtil.isNVL(searchVO.getStartDate()) || StringUtil.isNVL(searchVO.getEndDate())) {

			String from_date = ""; // 기간(시작)
			String to_date = ""; // 기간(끝)

			Calendar NowDate = Calendar.getInstance();
			Calendar NowDate2 = Calendar.getInstance();
			NowDate.add(Calendar.DATE, 0);
			NowDate2.add(Calendar.DATE, -7);

			String today_date = Utils.formatDate(NowDate.getTime(), "yyyy-MM-dd");
			String today_date2 = Utils.formatDate(NowDate2.getTime(), "yyyy-MM-dd");

			// 초최 오픈시 Default 값 세팅
			if (from_date == null || from_date.equals(""))
				from_date = today_date2;
			if (to_date == null || to_date.equals(""))
				to_date = today_date;

			searchVO.setStartDate(from_date);
			searchVO.setEndDate(to_date);
			// searchVO.setDateGbn("01"); //주문일

		}

		searchVO.setFromDate(searchVO.getStartDate().replaceAll("-", ""));
		searchVO.setToDate(searchVO.getEndDate().replaceAll("-", ""));

		// 선택한 vendorId가null 이거나 repVendorId ( 대표협력업체 ) 일때 협력사 전체 검색
		if (searchVO.getVendorId() == null || searchVO.getVendorId().length == 0 || epcLoginVO.getRepVendorId().equals(searchVO.getVendorId()[0].toString())) {
			searchVO.setVendorId(epcLoginVO.getVendorId());
		} else {
			request.setAttribute("vendorId", searchVO.getVendorId()[0].toString());
		}

		// 선택한 vendor가 openappi 업체일 경우 전체 검색
		List<EpcLoginVO> openappiVendorId = commonService.selectOpenappiVendor();
		int totalSize = openappiVendorId.size();

		for (int l = 0; totalSize > l; l++) {
			if (openappiVendorId.get(l).getRepVendorId().equals(searchVO.getVendorId()[0].toString().replace("[", "").replace("]", "").trim())) {
				searchVO.setVendorId(epcLoginVO.getVendorId());
			}
		}

		searchVO.setStrCd(config.getString("online.rep.str.cd"));
		searchVO.setVendorMode(null);

		if ("06".equals(epcLoginVO.getVendorTypeCd())) {

			if ((searchVO.getVendorId()[0].toString()).indexOf("T") < 0) {
				searchVO.setVendorMode("V");
			}
		}

		List<DataMap> list = pscmdlv0006Service.selectDeliHistList(searchVO);
		String resultMsg = "해당 자료가 없습니다.";
		/*
		 * if(searchVO.getFlag().equals("")&& list!=null && list.size() > 0){
		 * searchVO.setFlag("success"); }else{ searchVO.setFlag("zero"); }
		 */
		if (list.size() > 0) {
			resultMsg = "정상적으로 조회되었습니다.";
		}

		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("list", list);
		model.addAttribute("listSize", list.size());
		model.addAttribute("searchVO", searchVO);

		list = null;
		return "delivery/PSCMDLV000604";
	}

	/**
	 * 업체택배배송추적 Desc :
	 * 
	 * @Method Name : selectDeliveryStatus
	 * @param request
	 * @return
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "PSCMDLV0006/selectDeliveryStatus.do")
	public String selectDeliveryStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {

		DataMap paramMap = RequestUtils.getStringParamDataMap(request);

		String hodecoCd = paramMap.getString("hodecoCd");
		String hodecoInvoiceNo = paramMap.getString("hodecoInvoiceNo");
		String tCode = "";
		String tCodeNm = "";

		DataMap codeData = null; // 해외배송국가 데이터

		if (!"06".equals(hodecoCd) && !"13".equals(hodecoCd) && !"08F".equals(hodecoCd) && !"16".equals(hodecoCd)) {
			codeData = commonCodeService.getCode("DE011", hodecoCd);
			if (codeData.size() > 0) {
				tCode = codeData.getString("LET_3_REF");
				tCodeNm = codeData.getString("CD_NM");

			}
		}

		String result = "";
		String receiverName = "";
		String senderName = "";
		String receiverAddr = "";
		String invoiceNo = "";
		String completeYN = "";
		String level = "";

		List<DataMap> dataMapList = new ArrayList<DataMap>();

		HttpURLConnection conn = null;
		String jsonString = "";

		try {
			// url 에 접속 계정 사용
			URL url = new URL(config.getString("sweet.tracker.url") + "/tracking?t_key=" + config.getString("sweet.tracker.t_key") + "&t_code=" + tCode + "&t_invoice=" + hodecoInvoiceNo);
			conn = (HttpURLConnection) url.openConnection();

			conn.setReadTimeout(5000); // 스트림 리드 타임아웃 5초
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			String input = "";

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			// 응답코드 체크
			if (conn.getResponseCode() != 200) {
				if (conn != null) {
					conn.disconnect();
				}
				throw new AlertException("http response code error(" + conn.getResponseCode() + ")"); // System.exit(1);
			} else {

				InputStream is = conn.getInputStream();
				byte[] buf = new byte[2048];
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				while (true) {
					int n = is.read(buf, 0, 2048);
					if (n > 0) {
						baos.write(buf, 0, n);
					} else if (n == -1) {
						// logger.debug("end of stream");
						break;
					} else {
						break;
					}
				}
				baos.flush();
				if (baos.size() != 0) {
					jsonString = new String(baos.toByteArray(), "UTF-8");
				}

				conn.disconnect();
				conn = null;

				if (!"".equals(jsonString)) {

					JSONObject jsonListObj = new JSONObject(jsonString);
					JSONArray trackingList = null;

					if (jsonListObj.length() > 0) {

						if (!jsonListObj.isNull("result")) {

							result = jsonListObj.get("result").toString();
							receiverName = jsonListObj.get("receiverName").toString();
							senderName = jsonListObj.get("senderName").toString();
							receiverAddr = jsonListObj.get("receiverAddr").toString();
							invoiceNo = jsonListObj.get("invoiceNo").toString();
							completeYN = jsonListObj.get("completeYN").toString();
							level = jsonListObj.get("level").toString();

							trackingList = jsonListObj.getJSONArray("trackingDetails");

							for (int i = trackingList.length(); i >= 1; i--) {

								Map<String, Object> dataMap1 = new HashMap<String, Object>();
								DataMap dataMap = new DataMap();

								dataMap1.put("where", trackingList.getJSONObject(i - 1).getString("where").replace("null", ""));
								dataMap1.put("timeString", trackingList.getJSONObject(i - 1).getString("timeString").replace("null", ""));
								dataMap1.put("manName", trackingList.getJSONObject(i - 1).getString("manName").replace("null", ""));
								dataMap1.put("telno", trackingList.getJSONObject(i - 1).getString("telno").replace("null", ""));
								dataMap1.put("telno2", trackingList.getJSONObject(i - 1).getString("telno2").replace("null", "")); // 배송기사 전화번호
								dataMap1.put("kind", trackingList.getJSONObject(i - 1).getString("kind").replace("null", ""));
								dataMap1.put("level", trackingList.getJSONObject(i - 1).getString("level").replace("null", "")); // 진행단계
								dataMap1.put("remark", trackingList.getJSONObject(i - 1).getString("remark").replace("null", ""));

								dataMap.putAll(dataMap1);
								dataMapList.add(dataMap);

							}
						}
					}

				}

			}

		} catch (MalformedURLException e) {
			throw new AlertException(e.getMessage());
		} catch (IOException e) {
			throw new AlertException(e.getMessage());
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}

		request.setAttribute("tCodeNm", tCodeNm);
		request.setAttribute("result", result);
		request.setAttribute("receiverName", receiverName);
		request.setAttribute("senderName", senderName);
		request.setAttribute("receiverAddr", receiverAddr);
		request.setAttribute("invoiceNo", invoiceNo);
		request.setAttribute("completeYN", completeYN);
		request.setAttribute("level", level);
		request.setAttribute("trackingDetails", dataMapList);
		request.setAttribute("hodecoCd", paramMap.getString("hodecoCd", ""));
		request.setAttribute("hodecoInvoiceNo", paramMap.getString("hodecoInvoiceNo", ""));

		return "common/selectDeliveryStatus";
	}

	/**
	 * 송장번호체크(xml파싱) Desc :
	 * 
	 * @Method Name : deliveryCheck
	 * @param request
	 * @return
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/PSCMDLV0006/deliveryCheck.do")
	public ModelAndView deliveryCheck(HttpServletRequest request, ModelMap model) throws Exception {

		DataMap paramMap = RequestUtils.getStringParamDataMap(request);

		String hodecoCd = paramMap.getString("hodecoCd");
		String hodecoInvoiceNo = paramMap.getString("hodecoInvoiceNo");
		String tCode = "";
		String tCodeNm = "";
		String result = "";
		String emptyYn = "N";

		DataMap codeData = null; // 해외배송국가 데이터

		if (!"06".equals(hodecoCd) && !"13".equals(hodecoCd) && !"08F".equals(hodecoCd) && !"16".equals(hodecoCd)) {
			codeData = commonCodeService.getCode("DE011", hodecoCd);
			if (codeData.size() > 0) {
				tCode = codeData.getString("LET_3_REF");
				tCodeNm = codeData.getString("CD_NM");
			}
		}

		HttpURLConnection conn = null;
		String jsonString = "";

		try {
			// url 에 접속 계정 사용
			URL url = new URL(config.getString("sweet.tracker.url") + "/tracking?t_key=" + config.getString("sweet.tracker.t_key") + "&t_code=" + tCode + "&t_invoice=" + hodecoInvoiceNo);
			conn = (HttpURLConnection) url.openConnection();

			conn.setReadTimeout(5000); // 스트림 리드 타임아웃 5초
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			String input = "";
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			// 응답코드 체크
			if (conn.getResponseCode() != 200) {
				if (conn != null) {
					conn.disconnect();
				}
				throw new AlertException("http response code error(" + conn.getResponseCode() + ")"); // System.exit(1);
			} else {

				InputStream is = conn.getInputStream();
				byte[] buf = new byte[2048];
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				while (true) {
					int n = is.read(buf, 0, 2048);
					if (n > 0) {
						baos.write(buf, 0, n);
					} else if (n == -1) {
						break;
					} else {
						break;
					}
				}
				baos.flush();
				if (baos.size() != 0) {
					jsonString = new String(baos.toByteArray(), "UTF-8");
				}

				conn.disconnect();
				conn = null;

				if (!"".equals(jsonString)) {
					JSONObject jsonListObj = new JSONObject(jsonString);

					if (jsonListObj.length() > 0) {
						if (!jsonListObj.isNull("result")) {
							result = jsonListObj.get("result").toString();
							if ("N".equals(result)) {
								emptyYn = "Y";
							}
						} else {
							result = jsonListObj.get("tracking_info").toString();
							if (result.contains("ErrorCode")) {
								emptyYn = "Y";
							}
						}
					}
				}
			}

		} catch (MalformedURLException e) {
			throw new AlertException(e.getMessage());
		} catch (IOException e) {
			throw new AlertException(e.getMessage());
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}

		return AjaxJsonModelHelper.create(emptyYn);
	}

	/**
	 * 송장번호체크(DB체크) Desc :
	 * 
	 * @Method Name : hodecoNoChk
	 * @param request
	 * @return
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/PSCMDLV0006/hodecoNoChk.do")
	public ModelAndView hodecoNoChk(HttpServletRequest request, ModelMap model) throws Exception {

		DataMap paramMap = RequestUtils.getStringParamDataMap(request);
		String dupliYN = "N";
		int cnt = pscmdlv0006Service.selectHodecoNoChk(paramMap);
		if (cnt != 0) {
			dupliYN = "Y";
		}

		return AjaxJsonModelHelper.create(dupliYN);
	}

	/**
	 * Desc : 결품정보 등록/상세 팝업
	 * 
	 * @Method Name : counselView
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "PSCMDLV0006/counselView.do")
	public String counselView(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String mode = request.getParameter("mode");
		String counselSeq = request.getParameter("counselSeq");
		String vendorId = request.getParameter("vendorId");
		String orderId = request.getParameter("orderId");
		String deliNo = request.getParameter("deliNo");
		String tdId = request.getParameter("tdId").replace("chk2_", "").trim();
		// 상세정보
		if ("search".equals(mode)) {
			try {
				List<DataMap> dataMap = pscmdlv0006Service.selectCounselContent(counselSeq);

				// clob을 사용하기 위해 적용
				for (Object object : dataMap) {

					Map<String, Object> map = (Map<String, Object>) object;
					//@4UP 수정 oracle CLOB 직접 참조 제거
					// String형태로 재할당.
					map.put("CONTENT", MiscUtils.getGuessClob2String(map.get("CONTENT")));
					request.setAttribute("CONTENT", map.get("CONTENT"));
					request.setAttribute("TITLE", map.get("TITLE"));
					request.setAttribute("vendorId", vendorId);
					request.setAttribute("tdId", tdId);
					request.setAttribute("mode", mode);
				}
			} catch (Exception e) {
				logger.error("error message --> " + e.getMessage());
			}
		} else if ("insert".equals(mode)) {

			DataMap paramMap = new DataMap();
			paramMap.put("orderId", orderId);
			paramMap.put("vendorId", vendorId);
			paramMap.put("deliNo", deliNo);
			List<DataMap> dataMap = pscmdlv0006Service.selectPartnerPopupList(paramMap);
			request.setAttribute("dataMap", dataMap);
			request.setAttribute("dataMapList", dataMap.size());
			request.setAttribute("vendorId", vendorId);
			request.setAttribute("tdId", tdId);
			request.setAttribute("mode", mode);
		}
		return "delivery/PSCMDLV000603";
	}

	@RequestMapping(value = "delivery/selectPartnerFirmsPopupList.do")
	public String selectPartnerFirmsPopupList(@ModelAttribute("searchVO") PSCMDLV0006VO searchVO,
			HttpServletRequest request, ModelMap model) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);

		// 선택한 vendorId가null 이거나 repVendorId ( 대표협력업체 ) 일때 협력사 전체 검색
		if (searchVO.getVendorId() == null || searchVO.getVendorId().length == 0 || epcLoginVO.getRepVendorId().equals(searchVO.getVendorId()[0].toString())) {
			searchVO.setVendorId(epcLoginVO.getVendorId());
		} else {
			request.setAttribute("vendorId", searchVO.getVendorId()[0].toString());
		}

		// 선택한 vendor가 openappi 업체일 경우 전체 검색
		List<EpcLoginVO> openappiVendorId = commonService.selectOpenappiVendor();
		for (int l = 0; openappiVendorId.size() > l; l++) {
			if (openappiVendorId.get(l).getRepVendorId().equals(searchVO.getVendorId()[0].toString().replace("[", "").replace("]", "").trim())) {
				searchVO.setVendorId(epcLoginVO.getVendorId());
			}
		}

		List<DataMap> list = pscmdlv0006Service.selectPartnerFirmsPopupList(searchVO);
		if (searchVO.getFlag().equals("") && list != null && list.size() > 0) {
			searchVO.setFlag("success");
		} else {
			searchVO.setFlag("zero");
		}
		model.addAttribute("list", list);
		model.addAttribute("listSize", list.size());
		model.addAttribute("searchVO", searchVO);
		return "delivery/PSCMDLV0006";
	}
}
