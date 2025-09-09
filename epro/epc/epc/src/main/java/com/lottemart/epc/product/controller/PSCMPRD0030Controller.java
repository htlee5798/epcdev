package com.lottemart.epc.product.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.framework.property.ConfigurationService;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lottemart.common.file.service.FileMngService;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.service.CommonService;
import com.lottemart.epc.common.util.LoginUtil;
import com.lottemart.epc.product.service.PSCMPRD0030Service;

/**
 *
 * @author hjKim
 * @Description : 상품관리 - 전상법관리
 * @Class : com.lottemart.epc.product.controller
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2016.03.31  projectBOS32
 * @version :
 * </pre>
 */
@Controller("pscmprd0030Controller")
public class PSCMPRD0030Controller {

	private static final Logger logger = LoggerFactory.getLogger(PSCMPRD0030Controller.class);

	@Autowired
	private PSCMPRD0030Service pscmprd0030Service;

	@Autowired
	private ConfigurationService config;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private FileMngService fileMngService;

	@Autowired
	private CommonService commonService;

	/**
	 * 전상법관리 디폴트 페이지
	 * @see prodElecCommView
	 * @Locaton    : com.lottemart.epc.product.controller
	 * @MethodName : prodElecCommView
	 * @author     : projectBOS32
	 * @Description : 메뉴 클릭시 뜨는 디폴트 페이지
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/prodElecCommView.do")
	public String prodElecCommView(HttpServletRequest request) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}

		// 협력업체콤보
		request.setAttribute("epcLoginVO", epcLoginVO);

		DataMap paramMap = new DataMap();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
		Calendar calVal = Calendar.getInstance();
		String endDate = dateFormat.format(calVal.getTime());

		// 시작일자를 현재날짜 한달전으로 셋팅
		calVal.add(Calendar.DAY_OF_MONTH, -31);
		String startDate = dateFormat.format(calVal.getTime());

		paramMap.put("vendorId", LoginUtil.getVendorList(epcLoginVO));

		//상품분류
		List<DataMap> infoGrpList = pscmprd0030Service.selectInfoGrpCdList(paramMap);

		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);
		request.setAttribute("infoGrpList", infoGrpList);

		return "product/PSCMPRD0030";
	}

	/**
	 * 디폴트 페이지에서 조회버튼 클릭시 전상법 목록 조회
	 * @see selectRepProdCdList
	 * @Locaton    : com.lottemart.epc.product.controller
	 * @MethodName : selectElecCommList
	 * @author     : projectBOS32
	 * @Description : 전상법 목록 조회
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/selectElecCommList.do")
	public @ResponseBody Map selectElecCommList(HttpServletRequest request) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		Map rtnMap = new HashMap<String, Object>();

		try {

			DataMap paramMap = new DataMap(request);

			String prodFlag = paramMap.getString("prodFlag");
			String prodVal = paramMap.getString("prodVal");
			String startDate = paramMap.getString("startDate");
			String endDate = paramMap.getString("endDate");

			paramMap.put("startDate", startDate.replaceAll("-", ""));
			paramMap.put("endDate", endDate.replaceAll("-", ""));

			if("1".equals(prodFlag)){
				String prodCdSpl[] = prodVal.split(",");

				paramMap.put("prodCdArr", prodCdSpl);
			}

			// 선택한 vendorId가null 이거나 repVendorId ( 대표협력업체 ) 일때 협력사 전체 검색
			if(paramMap.getString("vendorId").length() == 0  || epcLoginVO.getRepVendorId().equals(request.getParameter("vendorId"))) {
				paramMap.put("vendorId", epcLoginVO.getVendorId());
			}else{
				String venderId[] = {paramMap.getString("vendorId")};
				paramMap.put("vendorId", venderId);
			}

			// 선택한 vendor가 openappi 업체일 경우 전체 검색
			List<EpcLoginVO> openappiVendorId = commonService.selectOpenappiVendor();
			for (int l = 0; openappiVendorId.size() > l; l++) {
				if (openappiVendorId.get(l).getRepVendorId().equals(paramMap.get("vendorId"))) {
					paramMap.put("vendorId", epcLoginVO.getVendorId());
				}
			}

			// 데이터 조회
			List<DataMap> list = pscmprd0030Service.selectElecCommList(paramMap);

			rtnMap = JsonUtils.convertList2Json((List) list, list.size(), null);

			// 성공
			rtnMap.put("result", true);

		} catch (Exception e) {
			// 작업오류
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}

	/**
	 * 전상법 컬럼 조회
	 * @see selectElecCommList
	 * @Locaton    : com.lottemart.epc.product.controller
	 * @MethodName : selectElecCommList
	 * @author     : projectBOS32
	 * @Description : 전상법 목록 조회
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/selectElecCommColList.do")
	public @ResponseBody Map selectElecCommColList(HttpServletRequest request) throws Exception {
		Map rtnMap = new HashMap<String, Object>();

		try {
			DataMap paramMap = new DataMap(request);

			List<DataMap> colList = pscmprd0030Service.selectElecCommColList(paramMap);
			rtnMap.put("columnList", colList);
			rtnMap.put("result", true);

		} catch (Exception e) {
			// 작업오류
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}

	/**
	 * 전상법 수정
	 * @see updateElecComm
	 * @Locaton    : com.lottemart.epc.product.controller
	 * @MethodName : updateElecComm
	 * @author     : projectBOS32
	 * @Description : 전상법 수정
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/updateElecComm.do")
	public @ResponseBody JSONObject updateElecComm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		if (epcLoginVO.getAdminId() != null && !"".equals(epcLoginVO.getAdminId())) {
			request.setAttribute("modId", epcLoginVO.getAdminId());
		} else {
			request.setAttribute("modId", epcLoginVO.getRepVendorId());
		}

		JSONObject jObj = new JSONObject();
		String message = "";
		int resultCnt = 0;

		try {

			resultCnt = pscmprd0030Service.updateElecComm(request);

			// 처리 결과
			if (resultCnt > 0) {
				jObj.put("Code", 1);
				message = messageSource.getMessage("msg.common.success.request", null, Locale.getDefault());
				jObj.put("Message", resultCnt + "건의 " + message);

			} else {
				jObj.put("Code", 0);
				message = messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault());
				jObj.put("Message", message);
			}

		} catch (Exception e) {
			jObj.put("Code", -1);
			jObj.put("Message", e.getMessage());
		}

		return JsonUtils.getResultJson(jObj);
	}

	/**
	 * 전상법 업로드용 엑셀
	 * @see elecCommExcel
	 * @Locaton    : com.lottemart.epc.product.controller
	 * @MethodName : elecCommExcel
	 * @author     : projectBOS32
	 * @Description : 전상법 업로드용 엑셀
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/elecCommExcel.do")
	public void elecCommExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		DataMap paramMap = new DataMap(request);

		String prodFlag = paramMap.getString("prodFlag");
		String prodVal = paramMap.getString("prodVal");
		String startDate = paramMap.getString("startDate");
		String endDate = paramMap.getString("endDate");

		paramMap.put("startDate", startDate.replaceAll("-", ""));
		paramMap.put("endDate", endDate.replaceAll("-", ""));

		if ("1".equals(prodFlag)) {
			String prodCdSpl[] = prodVal.split(",");
			paramMap.put("prodCdArr", prodCdSpl);
		}

		// 선택한 vendorId가null 이거나 repVendorId ( 대표협력업체 ) 일때 협력사 전체 검색
		if (paramMap.getString("vendorId").length() == 0 || epcLoginVO.getRepVendorId().equals(request.getParameter("vendorId"))) {
			paramMap.put("vendorId", epcLoginVO.getVendorId());
		} else {
			String venderId[] = { paramMap.getString("vendorId") };
			paramMap.put("vendorId", venderId);
		}

		// 선택한 vendor가 openappi 업체일 경우 전체 검색
		List<EpcLoginVO> openappiVendorId = commonService.selectOpenappiVendor();
		for (int l = 0; openappiVendorId.size() > l; l++) {
			if (openappiVendorId.get(l).getRepVendorId().equals(paramMap.get("vendorId"))) {
				paramMap.put("vendorId", epcLoginVO.getVendorId());
			}
		}

		//컬럼 조회
		List<DataMap> colList = pscmprd0030Service.selectElecCommColList(paramMap);

		// 데이터 조회
		List<DataMap> resultList = pscmprd0030Service.selectElecCommList(paramMap);

		response.setContentType("application/x-msdownload;charset=UTF-8");

		String userAgent = request.getHeader("User-Agent");
		String fileName = "전상법업로드_양식.xls";

		if (userAgent.indexOf("MSIE 5.5") > -1) {
			response.setHeader("Content-Disposition", "filename=" + fileName + ";");
		} else if (userAgent.indexOf("MSIE") > -1) {
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ";");
		} else {
			response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("euc-kr"), "latin1") + ";");
		}

		StringBuffer sb = new StringBuffer();
		int colLen = colList.size();

		String[] headers = new String[colLen + colLen + 2];

		headers[0] = "상품코드";

		for (int i = 0; i < colLen; i++) {
			DataMap infoMap = colList.get(i);
			headers[i + 1] = infoMap.getString("INFO_COL_NM");
		}

		// 헤더출력
		int headerLength = headers.length;

		// create a wordsheet
		HSSFWorkbook workbook = new HSSFWorkbook();
		CreationHelper createHelper = workbook.getCreationHelper();
		HSSFSheet sheet = workbook.createSheet("전상법현황");
		HSSFRow header = sheet.createRow(0);
		HSSFCellStyle styleHd = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		HSSFCell cell = null;

		font.setBoldweight((short) font.BOLDWEIGHT_BOLD);
		styleHd.setFont(font);
		styleHd.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleHd.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		styleHd.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		styleHd.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		for (int i = 0; i < headerLength; i++) {
			// header.createCell(i).setCellStyle(styleHd);
			cell = header.createCell(i);
			cell.setCellValue(headers[i]);
			cell.setCellStyle(styleHd);
		}

		Iterator iter = resultList.iterator();
		int rowNum = 1;

		sheet.setColumnHidden(colLen + 1, true);

		for (int k = 0; k < colLen; k++) {
			sheet.setColumnHidden(colLen + k + 2, true);
		}

		for (int l = 0; l < colLen + 2; l++) {
			sheet.setColumnWidth(l, 8000);
		}

		while (iter.hasNext()) {
			DataMap list = (DataMap) iter.next();
			if (list == null) {
				continue;
			}
			HSSFRow row = sheet.createRow(rowNum++);

			row.createCell(0).setCellValue(list.getString("PROD_CD"));

			for (int j = 0; j < colLen; j++) {
				row.createCell(j + 1).setCellValue(list.getString("COL_" + (j + 1)));
			}

			row.createCell(colLen + 1).setCellValue(list.getString("INFO_GRP_CD"));

			for (int k = 0; k < colLen; k++) {
				row.createCell(colLen + k + 2).setCellValue(list.getString("CODE_" + (k + 1)));
			}
		}

		ServletOutputStream out = response.getOutputStream();
		workbook.write(out);
		out.flush();
	}

	/**
	 * 전상법 업로드
	 * @see elecCommExcelUpload
	 * @Locaton    : com.lottemart.epc.product.controller
	 * @MethodName : elecCommExcelUpload
	 * @author     : projectBOS32
	 * @Description : 전상법 업로드
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/product/elecCommExcelUpload.do")
	public void elecCommExcelUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		if(epcLoginVO.getAdminId() != null && !"".equals(epcLoginVO.getAdminId()) ){
			request.setAttribute("modId", epcLoginVO.getAdminId());
		}else{
			request.setAttribute("modId", epcLoginVO.getRepVendorId());
		}

		MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;

		DataMap paramMap = new DataMap(request);
		Boolean valChk = true;
		int resultCnt = 0;
		String message = "";
		try {
			String infoGrpCd = paramMap.getString("infoGrpCd");

			//컬럼 조회
			List<DataMap> colList = pscmprd0030Service.selectElecCommColList(paramMap);
			int colLen = colList.size();

			String[] colNms = new String[colLen + colLen + 2];

			colNms[0] = "PROD_CD";

			for (int i = 0; i < colLen; i++) {
				colNms[i + 1] = "COL_" + (i + 1);
			}

			colNms[colLen + 1] = "INFO_GRP_CD";

			for (int j = 0; j < colLen; j++) {
				colNms[colLen + j + 2] = "CODE_" + (j + 1);
			}

			List<DataMap> mapList = fileMngService.readUploadExcelFile(mptRequest, colNms, 1);
			//List<DataMap> mapList = fileMngService.readUploadNullColExcelFile(mptRequest, colNms, 1, 0);

			for (int k = 0; k < mapList.size(); k++) {
				DataMap map = mapList.get(k);

				if (!map.getString("INFO_GRP_CD").equals(infoGrpCd)) {
					valChk = false;
					break;
				}
			}

			if (valChk) {
				resultCnt = pscmprd0030Service.updateElecCommExcel(mapList, request);
			}
		} catch (Exception e) {
			// 작업오류
			logger.error("error message --> " + e.getMessage());

		} finally {

			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();

			try {
				out.println("<script type=\'text/javascript'>");

				if (resultCnt > 0) {
					message = resultCnt + "건의 " + messageSource.getMessage("msg.common.success.request", null, Locale.getDefault());
				} else {
					if (!valChk) {
						message = "업로드 파일의 상품분류 항목이 일치 하지 않습니다.";
					} else {
						message = "업로드 파일형식이 잘못되었습니다.";
					}
				}

				out.println("parent.uploadReturn('" + message + "');");
				out.println("</script>");
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}
}
