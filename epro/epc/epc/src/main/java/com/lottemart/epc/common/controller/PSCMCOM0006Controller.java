package com.lottemart.epc.common.controller;

import java.io.File;
import java.io.FileFilter;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang.StringUtils;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lottemart.common.file.service.FileMngService;
import com.lottemart.common.util.ConfigUtils;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.model.PSCMCOM0006VO;
import com.lottemart.epc.common.service.PSCMCOM0006Service;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.product.model.NEDMPRO0020VO;
import com.lottemart.epc.edi.product.service.NEDMPRO0020Service;

import lcn.module.framework.property.ConfigurationService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class PSCMCOM0006Controller extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(PSCMCOM0006Controller.class);

	@Autowired
	private ConfigurationService config;

	@Autowired
	private PSCMCOM0006Service PSCMCOM0006Service;

	@Resource(name = "nEDMPRO0020Service")
	private NEDMPRO0020Service nEDMPRO0020Service;

	@Autowired
	private FileMngService fileMngService;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/common/viewPopupProductList.do")
	public String selectPopupProductList(@ModelAttribute("searchVO") PSCMCOM0006VO searchVO, ModelMap model) throws Exception {
		return "common/PSCMCOM0006";
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/common/viewPopupProductList2.do")
	public String selectPopupProductList2(HttpServletRequest request, @ModelAttribute("searchVO") PSCMCOM0006VO searchVO, ModelMap model) throws Exception {

		DataMap param = new DataMap(request);

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}

		// 협력업체콤보
		request.setAttribute("epcLoginVO", epcLoginVO);
		request.setAttribute("gubun", param.getString("gubun"));

		return "exhibition/PSCMEXH010047";
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/common/selectPopupProductList.do")
	public @ResponseBody Map selectPopupProductList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		PSCMCOM0006VO searchVO = new PSCMCOM0006VO();

		Map rtnMap = new HashMap<String, Object>();

		try {
			DataMap paramMap = new DataMap(request);

			String currentPage = paramMap.getString("currentPage");

			if (paramMap.getString("vendorId").length() == 0) {
				searchVO.setVendorId(epcLoginVO.getVendorId());
			} else {
				String venderId[] = { paramMap.getString("vendorId") };
				searchVO.setVendorId(venderId);
			}

			searchVO.setCondition1(paramMap.getString("condition1"));
			searchVO.setCondition2(paramMap.getString("condition2"));

			String notInVal[] = paramMap.getString("notInVal").split(","); // 제외상품 조건

			if (!"".equals(notInVal[0])) {
				searchVO.setNotInVal(notInVal);
			}

			searchVO.setPageSize(paramMap.getInt("rowsPerPage"));
			searchVO.setPageIndex(paramMap.getInt("currentPage"));

			searchVO.setOnlineProdTypeCd(paramMap.getString("onlineProdTypeCd")); // 온라인상품유형코드
			searchVO.setProdDivnCd(paramMap.getString("prodDivnCd")); // 온라인상품유형코드
			searchVO.setAprvYn(paramMap.getString("aprvYn")); // 상품승인여부
			searchVO.setEcLinkYn(paramMap.getString("ecLinkYn")); // EC연동여부
			searchVO.setDealProdYn(paramMap.getString("dealProdYn")); // 딜상품 검색여부

			// 데이터 조회
			List<DataMap> list = PSCMCOM0006Service.selectPopupProductList(searchVO);

			rtnMap = JsonUtils.convertList2Json((List) list, -1, currentPage);

			rtnMap.put("result", true);
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}

	/**
	 * 대표이미지 가져오기
	 * @param request
	 * @param reuqest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/common/imsiProductImg")
    public @ResponseBody Map imsiProductImg(HttpServletRequest request) throws Exception {
		Map rtnMap = new HashMap<String, Object>();
		
		try {
				
			String pgmId= request.getParameter("pgmId");
			String productImgPath = ConfigUtils.getString("edi.online.image.url");
			String productImg = pgmId.substring(0, 4)+"/"+pgmId.substring(4, 8)+"/"+pgmId+"_0_640.jpg";
			
			rtnMap.put("productImgPath", productImgPath);
			rtnMap.put("productImg", productImg);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("상품 확정 Exception 발생 =====" + e.toString());
		}
		
		return rtnMap;
				
	}
	
	/**
	 * 상품검색 일괄업로드 양식
	 * @param request
	 * @param reuqest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/common/productExcelDown.do")
	public void productExcelDown(HttpServletRequest request, HttpServletResponse response) throws Exception {

		response.setContentType("application/x-msdownload;charset=UTF-8");

		String userAgent = request.getHeader("User-Agent");
		String fileName = "상품검색_일괄업로드_양식.xls";

		fileName = URLEncoder.encode(fileName, "UTF-8");

		if (userAgent.indexOf("MSIE 5.5") > -1) {
			response.setHeader("Content-Disposition", "filename=" + fileName + ";");
		} else {
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ";");
		}

		StringBuffer sb = new StringBuffer();

		String[] headers = { "상품코드", "* 상품코드는 필수값 입니다. 해당업체 상품이 아닐시 조회 되지 않습니다." };

		// 헤더출력
		int headerLength = headers.length;

		// create a wordsheet
		HSSFWorkbook workbook = new HSSFWorkbook();
		CreationHelper createHelper = workbook.getCreationHelper();
		HSSFSheet sheet = workbook.createSheet("상품검색 일괄업로드 양식");
		HSSFRow header = sheet.createRow(0);
		HSSFCellStyle styleHd = workbook.createCellStyle();
		HSSFCellStyle styleHd2 = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		HSSFCell cell = null;

		font.setBoldweight((short) font.BOLDWEIGHT_BOLD);
		font.setFontHeight((short) 250);
		styleHd.setFont(font);
		styleHd.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleHd.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		styleHd.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		styleHd.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		styleHd2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleHd2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		for (int i = 0; i < headerLength; i++) {
			cell = header.createCell(i);
			cell.setCellValue(headers[i]);

			if (i < (headerLength - 1)) {
				cell.setCellStyle(styleHd);
				sheet.setColumnWidth(i, 4000);
			} else {
				cell.setCellStyle(styleHd2);
				sheet.setColumnWidth(i, 16000);
			}
		}

		HSSFRow row = sheet.createRow(1);
		row.createCell(0).setCellValue("");

		ServletOutputStream out = response.getOutputStream();
		workbook.write(out);
		out.flush();
	}

	/**
	 * 상품검색 일괄업로드
	 * @param request
	 * @param reuqest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/common/productExcelUpload.do")
	public void elecCommExcelUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String message = "";

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		PSCMCOM0006VO searchVO = new PSCMCOM0006VO();

		MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;

		DataMap paramMap = new DataMap(request);

		List<DataMap> list = new ArrayList<DataMap>();

		try {
			String[] colNms = { "PROD_CD" };

			if (paramMap.getString("vendorId").length() == 0) {
				searchVO.setVendorId(epcLoginVO.getVendorId());
			} else {
				String venderId[] = { paramMap.getString("vendorId") };
				searchVO.setVendorId(venderId);
			}

			List<DataMap> mapList = fileMngService.readUploadExcelFile(mptRequest, colNms, 1);

			String[] prodCd = new String[mapList.size()];

			for (int i = 0; i < mapList.size(); i++) {
				DataMap map = mapList.get(i);

				prodCd[i] = map.getString("PROD_CD").trim();
			}

			searchVO.setInVal(prodCd);

			// 데이터 조회
			list = PSCMCOM0006Service.selectPopupProductList(searchVO);

		} catch (Exception e) {
			// 작업오류
			logger.error("error message --> " + e.getMessage());
			message = e.getMessage();
		} finally {

			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();

			try {
				out.println("<script type=\'text/javascript'>");

				if (list.size() > 0) {
					out.println("	var rtnVal = new Object();");
					out.println("	var prodCdArray = new Array();"); // (인터넷상품코드)
					out.println("	var prodNmArray = new Array();"); // (인터넷상품명)
					out.println("	var buyPrcArray = new Array();"); // (원가)
					out.println("	var sellPrcArray = new Array();"); // (매가)
					out.println("	var currSellPrcArray = new Array();"); // (판매가)
					out.println("	var sellFlagArray = new Array();"); // (판매상태)
					out.println("	var stockQtyArray = new Array();"); // (재고수량)
					out.println("	var dispYnArray = new Array();"); // (전시여부)
					out.println("	var venDorNmArray = new Array();"); // (제조사)
					out.println("	var repProdCdArray = new Array();"); // (대표상품코드)

					for (int i = 0; i < list.size(); i++) {
						DataMap map = list.get(i);

						out.println("prodCdArray.push('" + map.getString("PROD_CD") + "');");
						out.println("prodNmArray.push('" + map.getString("PROD_NM") + "');");
						out.println("buyPrcArray.push('" + map.getString("BUY_PRC") + "');");
						out.println("sellPrcArray.push('" + map.getString("SELL_PRC") + "');");
						out.println("currSellPrcArray.push('" + map.getString("CURR_SELL_PRC") + "');");
						out.println("sellFlagArray.push('" + map.getString("SELL_FLAG") + "');");
						out.println("stockQtyArray.push('" + map.getString("RSERV_STK_QTY") + "');");
						out.println("dispYnArray.push('" + map.getString("DISP_YN") + "');");
						out.println("venDorNmArray.push('" + map.getString("VENDOR_NM") + "');");
						out.println("repProdCdArray.push('" + map.getString("REP_PROD_CD") + "');");
					}

					out.println("rtnVal.prodCdArr = prodCdArray;");
					out.println("rtnVal.prodNmArr = prodNmArray;");
					out.println("rtnVal.buyPrcArr = buyPrcArray;");
					out.println("rtnVal.sellPrcArr = sellPrcArray;");
					out.println("rtnVal.currSellPrcArr = currSellPrcArray;");
					out.println("rtnVal.sellFlagArr = sellFlagArray;");
					out.println("rtnVal.stockQtyArr = stockQtyArray;");
					out.println("rtnVal.dispYnArr = dispYnArray;");
					out.println("rtnVal.venDorNmArr = venDorNmArray;");
					out.println("rtnVal.repProdCdArr = repProdCdArray;");

					out.println("	parent.uploadRe(rtnVal);");
				} else {
					if ("".equals(message)) {
						out.println("alert('업로드 파일에 해당되는 상품이 존재하지 않습니다.')");
					} else {
						out.println("alert('" + message + "')");
					}

					out.println("parent.createFile.value = '';");
					out.println("parent.hideLoadingMask();");
				}
				out.println("</script>");
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}

	@RequestMapping(value = "/common/productImsiDetail.do")
	public String selectImsiProductDetail(ModelMap model, HttpServletRequest request) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();

		String pgmId = request.getParameter("pgmId");

		paramMap.put("pgmId", StringUtils.trimToEmpty(pgmId));

		NEDMPRO0020VO newProdDetailInfo = nEDMPRO0020Service.selectNewTmpOnlineProductDetailInfo(paramMap); //임시상품 상세

		List<DataMap> norProdDetailList = PSCMCOM0006Service.selectNorProdDetailList(paramMap); //전상법 상세
		List<DataMap> kcProdDetailList = PSCMCOM0006Service.selectKcProdDetailList(paramMap); //KC 인증 상세

		String onlineUploadFolder = makeSubFolderForOnline(StringUtils.trimToEmpty(newProdDetailInfo.getPgmId()));

		ArrayList<String> onlineImageList = new ArrayList<String>();
		File dir = new File(onlineUploadFolder);
		FileFilter fileFilter = new WildcardFileFilter(StringUtils.trimToEmpty(newProdDetailInfo.getPgmId()) + "*");
		File[] files = dir.listFiles(fileFilter);
		for (int j = 0; j < files.length; j++) {
			String fileName = files[j].getName();
			onlineImageList.add(fileName);
		}

		model.addAttribute("newProdDetailInfo", newProdDetailInfo);
		model.addAttribute("norProdDetailList", norProdDetailList);
		model.addAttribute("kcProdDetailList", kcProdDetailList);
		model.addAttribute("onlineImageList", onlineImageList);
		model.addAttribute("imagePath", ConfigUtils.getString("edi.online.image.url"));
		model.addAttribute("subFolderName", subFolderName(StringUtils.trimToEmpty(newProdDetailInfo.getPgmId())));
		model.addAttribute("frontUrl", ConfigUtils.getString("online.front.url"));

		return "common/productImsiDetail";
	}
}
