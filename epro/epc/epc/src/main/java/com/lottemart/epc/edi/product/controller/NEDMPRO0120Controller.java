package com.lottemart.epc.edi.product.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.paging.PaginationInfo;
import lcn.module.framework.property.ConfigurationService;

import org.apache.commons.collections.MapUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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

import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.util.FileUtil;
import com.lottemart.epc.common.util.PagingUtil;
import com.lottemart.epc.edi.product.model.ExcelFileVo;
import com.lottemart.epc.edi.product.model.NEDMPRO0120VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0500VO;
import com.lottemart.epc.edi.product.service.NEDMPRO0120Service;

@Controller
public class NEDMPRO0120Controller {

	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0120Controller.class);

	@Autowired
	private NEDMPRO0120Service nedmpro0120Service;

	/**
	 * 화면 초기화
	 * @param map
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/NEDMPRO0120.do")
	public String NEDMPRO0120Init(ModelMap model, HttpServletRequest request) throws Exception {
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(ConfigUtils.getString("lottemart.epc.session.key"));
		//검색시 협력 업체코드를 선택하지 않은 경우, 로그인한 사업자의 전체 협력사에 대한 상품등록 정보가 조회됨.
		model.addAttribute("epcLoginVO", epcLoginVO);

		return "/edi/product/NEDMPRO0120";
	}

	/**
	 * 조회
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/buy/NEDMPRO0120Select.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> selectWholeProductList(@RequestBody NEDMPRO0120VO vo, HttpServletRequest request) throws Exception {
		Map<String, Object>	map = new HashMap<String, Object>();

		if (vo.getSrchEntpCode() == null || vo.getSrchEntpCode().equals("")) {
			map.put("resultList", "");
		}

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(vo.getPageIndex());
		paginationInfo.setRecordCountPerPage(vo.getRecordCountPerPage());
		paginationInfo.setPageSize(vo.getPageSize());

		map.put("paginationInfo", paginationInfo);

		vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
		vo.setLastIndex(paginationInfo.getLastRecordIndex());
		vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		// List Total Count
		int totCnt = nedmpro0120Service.selectWholeStoreProductCount(vo);
		paginationInfo.setTotalRecordCount(totCnt);

		// List 가져오기
		List<NEDMPRO0120VO>	resultList 	= 	nedmpro0120Service.selectWholeStoreProductList(vo);
		map.put("resultList", resultList);

		// 화면에 보여줄 게시물 리스트
		map.put("pageIdx", vo.getPageIndex());

		// 화면에 보여줄 페이징 생성
		try {
			map.put("contents", PagingUtil.makingPagingContents(request, paginationInfo, "text", "goPage"));
		} catch (Exception e) {
			logger.error("PagingUtil.makingPagingContents Exception ===" + e.toString());
		}

		return map;
	}

	/**
	 * Excel Count
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/NEDMPRO0120ExcelCount.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> selectWholeProductExcelCount(@RequestBody NEDMPRO0120VO vo, HttpServletRequest request) throws Exception {
		Map<String, Object>	map = new HashMap<String, Object>();

		if (vo.getSrchEntpCode() == null || vo.getSrchEntpCode().equals("")) {
			map.put("resultList", "");
		}

		// List Total Count
		int totCnt = nedmpro0120Service.selectWholeStoreProductCount(vo);
		map.put("totCnt", totCnt);

		return map;
	}

	/**
	 * Excel
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/NEDMPRO0120Excel.do")
	public void selectWholeProductExcel(NEDMPRO0120VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// List 가져오기
		List<NEDMPRO0120VO>	resultList = nedmpro0120Service.selectWholeStoreProductList(vo);
		

		response.setContentType("application/x-msdownload;charset=UTF-8");

		String userAgent = request.getHeader("User-Agent");
		String fileName = "AllEdiProduct.xls";

		if (userAgent.indexOf("MSIE 5.5") > -1) {
			response.setHeader("Content-Disposition", "filename=" + fileName + ";");
		} else if (userAgent.indexOf("MSIE") > -1) {
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ";");
		} else {
			response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("euc-kr"), "latin1") + ";");
		}

		StringBuffer sb = new StringBuffer();

		// 헤더출력
		String[] headers = { "번호", "업체코드", "점포코드", "점포명", "판매코드", "상품코드", "상품명", "코드상태", "등록일" };
		int headerLength = headers.length;

		// create a wordsheet
		HSSFWorkbook workbook = new HSSFWorkbook();
		CreationHelper createHelper = workbook.getCreationHelper();
		HSSFSheet sheet = workbook.createSheet("점포별상품현황");
		HSSFRow header = sheet.createRow(0);
		CellStyle cellDateStyle = workbook.createCellStyle();
		cellDateStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-mm-dd hh:mm"));

		for (int i = 0; i < headerLength; i++) {
			header.createCell(i).setCellValue(headers[i]);
		}

		Iterator iter = resultList.iterator();
		int rowNum = 1;
		while (iter.hasNext()) {
			NEDMPRO0120VO list = (NEDMPRO0120VO) iter.next();
			if (list == null) {
				continue;
			}
			HSSFRow row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue((rowNum - 1) + "");
			row.createCell(1).setCellValue(list.getVenCd());
			row.createCell(2).setCellValue(list.getStrCd());
			row.createCell(3).setCellValue(list.getStrNm());
			row.createCell(4).setCellValue(list.getSrcmkCd());
			row.createCell(5).setCellValue(list.getProdCd());
			row.createCell(6).setCellValue(list.getProdNm());
			row.createCell(7).setCellValue(list.getCodeStaus());
			row.createCell(8).setCellValue(list.getRegDt());
		}

		ServletOutputStream out = response.getOutputStream();
		workbook.write(out);
		out.flush();
	}
	
	/**
	 * 점포별 상품 List 조회 (PROXY)
	 * @param vo
	 * @param request
	 * @return Map<String,Object>
	 * @throws Exception
	 */
    @RequestMapping(value="/edi/product/selectStrProductListProxy.json",	method=RequestMethod.POST,	headers="Accept=application/json")
    public @ResponseBody Map<String,Object> selectStrProductListProxy(@RequestBody NEDMPRO0120VO vo, HttpServletRequest request) throws Exception {
    	return nedmpro0120Service.getSelectStrProductListProxy(vo);
    }
    
    /**
     * 점포별 상품 List ExcelDownload (PROXY)
     * @param vo
     * @param request
     * @param response
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/edi/product/selectStrProductListProxyExcelDownload.do")
	public ModelAndView selectStrProductListProxyExcelDownload(NEDMPRO0120VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ExcelFileVo excelVo = new ExcelFileVo();
	
		String fileName = "점포별상품현황"; 
		
		Map<String,Object> excelMap = nedmpro0120Service.getSelectStrProductListProxyExcelDownload(vo);
		List<HashMap<String,String>> datalist = null;
 		try {
 			datalist = (List<HashMap<String, String>>) MapUtils.getObject(excelMap, "resultList");
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		
		// titleparamVo
		String[] title = { "번호", "업체코드", "점포코드", "점포명", "판매코드", "상품코드", "상품명", "코드상태", "등록일" };
		
		// cell style
		String[] dataStyle = {"CENTER", "CENTER", "CENTER","LEFT","CENTER","CENTER","LEFT","CENTER","CENTER"};

		// cell width
		int[] cellWidth = { 10, 15, 15, 25, 25, 25, 35, 15, 15};
		
		// Keyset
		String[] keyset = { "SNUM","VEN_CD","STR_CD","STR_NM","SRCMK_CD","PROD_CD","PROD_NM","CODE_STATUS","REG_DT"};
		
		excelVo = FileUtil.toExcel(fileName, datalist, title, dataStyle, cellWidth, keyset, null, null);

		//XLSX 파일 형식으로 저장 
		ModelAndView modelAndView = new ModelAndView("apachPoiExcelFileDownLoad", "excelFile", excelVo);
		return modelAndView;
	}

}
