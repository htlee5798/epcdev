package com.lottemart.epc.edi.product.controller;

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

import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.util.PagingUtil;
import com.lottemart.epc.edi.comm.model.Constants;
import com.lottemart.epc.edi.product.model.NEDMPRO0110VO;
import com.lottemart.epc.edi.product.service.NEDMPRO0110Service;

@Controller
public class NEDMPRO0110Controller {

	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0110Controller.class);

	@Autowired
	private NEDMPRO0110Service nedmpro0110Service;

	/**
	 * 화면 초기화
	 * @param map
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/NEDMPRO0110.do")
	public String NEDMPRO0110Init(ModelMap model, HttpServletRequest request) throws Exception {
		// session 접근 N개의 협력사코드(VEN_CD) -------------------------------
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(ConfigUtils.getString("lottemart.epc.session.key"));
		//검색시 협력 업체코드를 선택하지 않은 경우, 로그인한 사업자의 전체 협력사에 대한 상품등록 정보가 조회됨.
		model.addAttribute("epcLoginVO", epcLoginVO);

		return "/edi/product/NEDMPRO0110";
	}

	/**
	 * 조회
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/NEDMPRO0110Select.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> selectWholeProductList(@RequestBody NEDMPRO0110VO vo, HttpServletRequest request) throws Exception {
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
		int totCnt = nedmpro0110Service.selectWholeProductCount(vo);
		paginationInfo.setTotalRecordCount(totCnt);

		// List 가져오기
		List<NEDMPRO0110VO>	resultList 	= 	nedmpro0110Service.selectWholeProductList(vo);
		map.put("resultList", resultList);

		// 화면에 보여줄 게시물 리스트
		map.put("pageIdx", vo.getPageIndex());

		// 화면에 보여줄 페이징 생성
		try {
			map.put("contents", PagingUtil.makingPagingContents(request, paginationInfo, "text", "goPage"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
	@RequestMapping(value="/edi/product/NEDMPRO0110ExcelCount.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> selectWholeProductExcelCount(@RequestBody NEDMPRO0110VO vo, HttpServletRequest request) throws Exception {
		Map<String, Object>	map = new HashMap<String, Object>();

		if (vo.getSrchEntpCode() == null || vo.getSrchEntpCode().equals("")) {
			map.put("resultList", "");
		}

		// List Total Count
		int totCnt = nedmpro0110Service.selectWholeProductCount(vo);
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
	@RequestMapping(value="/edi/product/NEDMPRO0110Excel.do")
	public void selectWholeProductExcel(NEDMPRO0110VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// List 가져오기
		List<NEDMPRO0110VO>	resultList = nedmpro0110Service.selectWholeProductList(vo);

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
		String[] headers = { "번호", "등록일자", "판매(88)코드", "상품코드", "상품명", "사용구분",
				"물류바코드", "입수", "가로", "세로", "높이", "중량", "소터구분", "물류바코드/n등록현황",
				"혼재구분" };

		int headerLength = headers.length;

		// create a wordsheet
		HSSFWorkbook workbook = new HSSFWorkbook();
		CreationHelper createHelper = workbook.getCreationHelper();
		HSSFSheet sheet = workbook.createSheet("전체상품현황");
		HSSFRow header = sheet.createRow(0);
		CellStyle cellDateStyle = workbook.createCellStyle();
		cellDateStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-mm-dd hh:mm"));

		for (int i = 0; i < headerLength; i++) {
			header.createCell(i).setCellValue(headers[i]);
		}

		Iterator iter = resultList.iterator();
		int rowNum = 1;
		while (iter.hasNext()) {
			NEDMPRO0110VO list = (NEDMPRO0110VO) iter.next();
			if (list == null) {
				continue;
			}
			HSSFRow row = sheet.createRow(rowNum++);

			row.createCell(0).setCellValue((rowNum - 1) + "");
			row.createCell(1).setCellValue(list.getRegDt());
			row.getCell(1).setCellStyle(cellDateStyle);

			row.createCell(2).setCellValue(list.getSrcmkCd());
			row.createCell(3).setCellValue(list.getProdCd());
			row.createCell(4).setCellValue(list.getProdNm());
			row.createCell(5).setCellValue(list.getUseFg());

			row.createCell(6).setCellValue(list.getLogiBcd());
			row.createCell(7).setCellValue(list.getOrdIpsu());
			row.createCell(8).setCellValue(list.getWidth());
			row.createCell(9).setCellValue(list.getLength());
			row.createCell(10).setCellValue(list.getHeight());
			row.createCell(11).setCellValue(list.getWg());

			row.createCell(12).setCellValue("논소터");
			if (Constants.SORTER_CD.equals(list.getSorterFg())) {
				row.createCell(12).setCellValue("소터");
			}
			row.createCell(13).setCellValue(list.getBarDesc());
			row.createCell(14).setCellValue(list.getMixProdFg());
		}

		ServletOutputStream out = response.getOutputStream();
		workbook.write(out);
		out.flush();
	}

}
