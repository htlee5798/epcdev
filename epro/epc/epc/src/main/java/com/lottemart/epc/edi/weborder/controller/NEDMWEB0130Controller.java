package com.lottemart.epc.edi.weborder.controller;




import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lcn.module.common.views.AjaxJsonModelHelper;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lcnjf.util.NumberUtil;
import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.weborder.model.EdiRtnPackListVO;
import com.lottemart.epc.edi.weborder.model.EdiRtnPackVO;
import com.lottemart.epc.edi.weborder.model.ExcelUploadVO;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.service.NEDMWEB0130Service;


/**
 * @Class Name : NEDMWEB0130Controller
 * @Description : 상품별 반품등록 Controller 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2015. 12. 11. 오후 2:30:34 DADA
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */


@Controller
public class NEDMWEB0130Controller extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(NEDMWEB0130Controller.class);

	@Autowired
	private NEDMWEB0130Service NEDMWEB0130Service;


	@Autowired
	private MessageSource messageSource;


	/* ============================================================================= */


	/**
	 * Desc : 반품 일괄등록
	 * @Method Name : returnPronList
	 * @param SearchWebOrder
	 * @param HttpServletRequest
	 * @param Model
	 * @return view 페이지
	 */
	@RequestMapping(value="/edi/weborder/NEDMWEB0130")
    public String returnPronList(SearchWebOrder searchParam, HttpServletRequest request, Model model) {

		SimpleDateFormat format = new SimpleDateFormat("HHmm", Locale.KOREA);

		int nowDt = Integer.parseInt(format.format(new Date()));

		String vendorWebOrdFrDt = ConfigUtils.getString("edi.weboder.vendor.rtn");
		String vendorWebOrdToDt = ConfigUtils.getString("edi.weboder.vendor.to");

		if( ( nowDt < Integer.parseInt(vendorWebOrdFrDt)) || ( nowDt > Integer.parseInt(vendorWebOrdToDt)) ){
			return "edi/weborder/NEDMWEB0111";
		}

		searchParam.setVendorWebOrdFrDt(vendorWebOrdFrDt);
		searchParam.setVendorWebOrdToDt(vendorWebOrdToDt);

		if(StringUtils.isEmpty(searchParam.getUploadGb())){
			searchParam.setUploadGb("1");
		}
		if(StringUtils.isEmpty(searchParam.getProdCd())){
			searchParam.setProdCd("");
		}
		model.addAttribute("paramMap",searchParam);

		return "edi/weborder/NEDMWEB0130";
	}

	/**
	 * Desc : 발주 일괄등록 정보 조회
	 * @Method Name : getRtnPackListInfo
	 * @param SearchWebOrder
	 * @param Model
	 * @return 조회 값
	 */
	@RequestMapping("/edi/weborder/tedRtnPackSelect.json")
	public ModelAndView getRtnPackListInfo(@RequestBody SearchWebOrder vo) throws Throwable
	{
		return AjaxJsonModelHelper.create(NEDMWEB0130Service.selectRtnPackInfo(vo));
	}


	/**
	 * Desc : 엑셀 정보 삭제
	 * @Method Name : getExcelRtnDelete
	 * @param TedPoOrdPackVO
	 * @param HttpServletRequest
	 * @return message
	 */
	@RequestMapping("/edi/weborder/tedRtnPackDelete.json")
	public ModelAndView getExcelRtnDelete(@RequestBody EdiRtnPackVO vo) throws Exception
	{
		String message;
		String result;

		message = messageSource.getMessage("msg.common.fail.delete", null, Locale.getDefault());

		try {
			result = NEDMWEB0130Service.deleteExcelRtnInfo(vo);
			if(result.equals("suc")) return AjaxJsonModelHelper.create("");
			else return AjaxJsonModelHelper.create(message);
		} catch (Exception e) {
			return AjaxJsonModelHelper.create(message);
		}

	}

	/**
	 * 반품일괄 등록 반품요청[RFC 전송]
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="edi/weborder/tedRtnPackRequest.json",	method=RequestMethod.POST,	headers="Accept=application/json")
	public @ResponseBody Map<String, Object> tedRtnPackRequest(@RequestBody SearchWebOrder vo, HttpServletRequest request) throws Exception {
		Map<String, Object>	resultMap	=	new HashMap<String, Object>();

		try {
			resultMap	=	NEDMWEB0130Service.insertSendReturnProdData(vo, request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("반품일괄 등록 반품요청 Exception 발생 ===============================================================================" + e.toString());
		}

		return resultMap;
	}

	/**
	 * Desc : 반품 정보 엑셀 업로드
	 * @Method Name : orderTotList
	 * @param ExcelUploadVO
	 * @param HttpServletRequest
	 * @param Model
	 * @return view 페이지
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/edi/weborder/prlExcelUpload.do", method=RequestMethod.POST)
    public String ordExcelUpload(
    		@ModelAttribute("excel") ExcelUploadVO excel,
    		HttpServletRequest request,
    		BindingResult result,
    		Model model) throws IOException{

		SearchWebOrder searchParam = new SearchWebOrder();
		SimpleDateFormat format = new SimpleDateFormat("HHmm", Locale.KOREA);

		int nowDt = Integer.parseInt(format.format(new Date()));

		String vendorWebOrdFrDt = ConfigUtils.getString("edi.weboder.vendor.rtn");
		String vendorWebOrdToDt = ConfigUtils.getString("edi.weboder.vendor.to");

		if( ( nowDt < Integer.parseInt(vendorWebOrdFrDt)) || ( nowDt > Integer.parseInt(vendorWebOrdToDt)) ){
			return "edi/weborder/NEDMWEB0111";
		}

		searchParam.setVendorWebOrdFrDt(vendorWebOrdFrDt);
		searchParam.setVendorWebOrdToDt(vendorWebOrdToDt);
		model.addAttribute("paramMap",searchParam);

		if(!result.hasErrors()){
			FileOutputStream outputStream = null;
			String value = null;
			String fileName = null;
			String filePath = null;
			String stateCode = null;
			String saveResult = null;
			String cellNullFg = null;
			String breakFg = null;

			XSSFWorkbook workbook = null;
			HSSFWorkbook oldWorkBook = null;
			File saveFile = null;
			FileInputStream file = null;
			String ordVenCd = request.getParameter("entp_cd");

	        try {
	        	//save & load location
				fileName = excel.getFile().getOriginalFilename();
				filePath = ConfigUtils.getString("edi.weborder.excel.path") + excel.getFile().getOriginalFilename();
				saveFile = new File(filePath);

				//save
				outputStream = new FileOutputStream(new File(filePath));
				outputStream.write(excel.getFile().getFileItem().get());

				//load
		        file = new FileInputStream(new File(filePath));

		        if(fileName.indexOf(".xlsx") > -1 || fileName.indexOf(".XLSX") > -1){
		        	workbook = new XSSFWorkbook(file);
		        }else{
		        	POIFSFileSystem myFileSystem = new POIFSFileSystem(file);
			    	oldWorkBook = new HSSFWorkbook(myFileSystem);
		        }
			} catch (Exception e) {
				outputStream.close();
				file.close();
				saveFile.delete();
				model.addAttribute("stateCode", "data-error");
				return "edi/weborder/NEDMWEB0130";
			}

	        //sheet수 취득
			//int sheetCn = workbook.getNumberOfSheets();

			 // 엑셀 데이터를 담을 VO
			EdiRtnPackListVO list = new EdiRtnPackListVO();
			EdiRtnPackVO excelData = null;

			if(fileName.indexOf(".xlsx") > -1 || fileName.indexOf(".XLSX") > -1){
				for(int cn = 0; cn < 1; cn++){
					XSSFSheet sheet = workbook.getSheetAt(cn);
					// cell 개수
			        int cells = 3;//sheet.getRow(0).getLastCellNum();
			        int rowNum = sheet.getLastRowNum();

			        if(rowNum > 10000){
			        	stateCode = "fail-006";
			        	model.addAttribute("stateCode", stateCode);
	       				return "edi/weborder/NEDMWEB0130";
			        }else{
			        	// sheet 개수 만큼 반복
				        for(Row row : sheet){
				        	cellNullFg = "F";
			        		excelData = new EdiRtnPackVO();

			        		if(sheet.getRow(row.getRowNum()).getCell((short)0).getCellType() == 3 ){
				        		if(row.getCell((short)0).getCellType() == 3 && row.getCell((short)1).getCellType() == 3 && row.getCell((short)2).getCellType() == 3){
				        			cellNullFg = "T";
			        			}
			        		}

			        		if(!"T".equals(cellNullFg)){
			        			// cell 개수 만큼 반복
			        			for (int c = 0; c < cells; c++) {
			        				value = null;
		        					if(sheet.getRow(row.getRowNum()).getCell((short)0).getCellType() == 3){
		        						breakFg = "T";
		        						break;
		        					}else if(!( row.getCell((short)0).getCellType() == 3|| row.getCell((short)1).getCellType() == 3|| row.getCell((short)2).getCellType() == 3)){
		        						switch (row.getCell(c).getCellType()) {
				        					case XSSFCell.CELL_TYPE_FORMULA:
				        						value = row.getCell(c).getCellFormula();
				        						break;
				        					case XSSFCell.CELL_TYPE_NUMERIC:
				        						value = "" + Long.toString(Long.parseLong(String.valueOf(Math.round(row.getCell(c).getNumericCellValue()))));
				        						break;
				        					case XSSFCell.CELL_TYPE_STRING:
				        						value = "" + row.getCell(c).getStringCellValue();
				        						break;
				        					case XSSFCell.CELL_TYPE_BLANK:
				        						value = "";
				        						break;
				        					case XSSFCell.CELL_TYPE_ERROR:
				        						value = "" + row.getCell(c).getErrorCellValue();
				        						break;
				        					default:
				                   		}
		        					}

			                   		if(value == null || "".equals(value) || value.length() == 0){
			                   			// 엑셀 값이 null인 경우
			                   			stateCode = "fail-005";
			                   		}else{
			                   			if(NumberUtil.isNumber(value)){
				                   			// 엑셀 데이터를 excelData에 담음
					                   		if( c == 0) {
					                   			// 점포코드가 3자리를 초과 했을경우.
					                   			if(value.getBytes().length > 3) stateCode = "fail-002";
					                   			else excelData.setStrCd(value);
					                   		}
					                   		if( c == 1 ) {
					                   			// 상품코드가 10자리를 초과 했을경우.
					                   			if(value.getBytes().length > 10) stateCode = "fail-003";
					                   			else excelData.setProdCd(value);
					                   		}
					                   		if( c == 2 ) {
					                   			//  상품수량이 10자리를 초과 했을경우.
					                   			if(value.getBytes().length > 10) stateCode = "fail-004";
					                   			else excelData.setRrlQty(value);
					                   		}
				                   		}else{
				                   			// 엑셀 값이 숫자가 아닌 경우.
				                   			stateCode = "fail-001";
				                   		}
			                   		}

			                   		// 에러 코드가 있는 경우 에러코드 리턴. 등록 화면으로 이동.
			                   		if(stateCode != null){
			                   			outputStream.close();
			            				saveFile.delete();
			                   			model.addAttribute("stateCode", stateCode);
			               				return "edi/weborder/NEDMWEB0130";
			                   		}
			                    }

			        			if("T".equals(breakFg)){
			        				// 엑셀 값이 null인 경우
		                   			stateCode = "fail-005";
		                   			model.addAttribute("stateCode", stateCode);
		               				return "edi/weborder/NEDMWEB0130";
			        			}

		        				// 파일 이름과 협력 업체코드 Set
			     	            excelData.setPackFileNm(fileName);
			     	            excelData.setVenCd(ordVenCd);

			     	            // list에 엑셀 한 로우의 값 저장.
			     	            list.add(excelData);
			        		}
				        }
			        }
				}
			}else{
				for(int cn = 0; cn < 1; cn++){
					HSSFSheet sheet =oldWorkBook.getSheetAt(cn);
					// cell 개수
			        int cells = 3;//sheet.getRow(0).getLastCellNum();
			        int rowNum = sheet.getLastRowNum();

			        if(rowNum > 10000){
			        	stateCode = "fail-006";
			        	model.addAttribute("stateCode", stateCode);
	       				return "edi/weborder/NEDMWEB0130";
			        }else{
			        	// sheet 개수 만큼 반복
				        for(Row row : sheet){
				        	cellNullFg = "F";
			        		excelData = new EdiRtnPackVO();

			        		if(sheet.getRow(row.getRowNum()).getCell((short)0) == null ){
				        		if(row.getCell((short)0) == null && row.getCell((short)1) == null && row.getCell((short)2) == null){
				        			cellNullFg = "T";
			        			}
			        		}

			        		if(!"T".equals(cellNullFg)){
			        			// cell 개수 만큼 반복
			        			for (int c = 0; c < cells; c++) {
			        				value = null;
			        				if(sheet.getRow(row.getRowNum()).getCell((short)0) == null){
		        						breakFg = "T";
		        						break;
		        					}else if(!( row.getCell((short)0) == null || row.getCell((short)1) == null || row.getCell((short)2) == null)){
		        						switch (row.getCell(c).getCellType()) {
				        					case HSSFCell.CELL_TYPE_FORMULA:
				        						value = row.getCell(c).getCellFormula();
				        						break;
				        					case HSSFCell.CELL_TYPE_NUMERIC:
				        						value = "" + Long.toString(Long.parseLong(String.valueOf(Math.round(row.getCell(c).getNumericCellValue()))));
				        						break;
				        					case HSSFCell.CELL_TYPE_STRING:
				        						value = "" + row.getCell(c).getStringCellValue();
				        						break;
				        					case HSSFCell.CELL_TYPE_BLANK:
				        						value = "";
				        						break;
				        					case HSSFCell.CELL_TYPE_ERROR:
				        						value = "" + row.getCell(c).getErrorCellValue();
				        						break;
				        					default:
				                   		}
		        					}

			                   		if(value == null || "".equals(value) || value.length() == 0){
			                   			// 엑셀 값이 null인 경우
			                   			stateCode = "fail-005";
			                   		}else{
			                   			if(NumberUtil.isNumber(value)){
				                   			// 엑셀 데이터를 excelData에 담음
					                   		if( c == 0) {
					                   			// 점포코드가 3자리를 초과 했을경우.
					                   			if(value.getBytes().length > 3) stateCode = "fail-002";
					                   			else excelData.setStrCd(value);
					                   		}
					                   		if( c == 1 ) {
					                   			// 상품코드가 10자리를 초과 했을경우.
					                   			if(value.getBytes().length > 10) stateCode = "fail-003";
					                   			else excelData.setProdCd(value);
					                   		}
					                   		if( c == 2 ) {
					                   			//  상품수량이 10자리를 초과 했을경우.
					                   			if(value.getBytes().length > 10) stateCode = "fail-004";
					                   			else excelData.setRrlQty(value);
					                   		}
				                   		}else{
				                   			// 엑셀 값이 숫자가 아닌 경우.
				                   			stateCode = "fail-001";
				                   		}
			                   		}

			                   		// 에러 코드가 있는 경우 에러코드 리턴. 등록 화면으로 이동.
			                   		if(stateCode != null){
			                   			outputStream.close();
			            				saveFile.delete();
			                   			model.addAttribute("stateCode", stateCode);
			               				return "edi/weborder/NEDMWEB0130";
			                   		}
		        				}

			        			if("T".equals(breakFg)){
			        				// 엑셀 값이 null인 경우
		                   			stateCode = "fail-005";
		                   			model.addAttribute("stateCode", stateCode);
		               				return "edi/weborder/NEDMWEB0130";
			        			}

			        			// 파일 이름과 협력 업체코드 Set
			     	            excelData.setPackFileNm(fileName);
			     	            excelData.setVenCd(ordVenCd);

			     	            // list에 엑셀 한 로우의 값 저장.
			     	            list.add(excelData);
			        		}
	        			}
			        }
				}
			}

            file.close();
            outputStream.close();
			saveFile.delete();

    		try {
    			saveResult = NEDMWEB0130Service.insertRtnPackInfo(list, request, ordVenCd);
    			if(saveResult.equals("suc")) {
    				model.addAttribute("stateCode", "suc");
    			}else {
    				model.addAttribute("stateCode", "insert-fail");
    			}
    			list.clear();
   				return "edi/weborder/NEDMWEB0130";
    		} catch (Exception e) {
    			list.clear();
    			model.addAttribute("stateCode", "insert-fail");
   				return "edi/weborder/NEDMWEB0130";
    		}

	    }else{
	    	model.addAttribute("stateCode", "data-error");
	    }

		return "edi/weborder/NEDMWEB0130";
	}
}
