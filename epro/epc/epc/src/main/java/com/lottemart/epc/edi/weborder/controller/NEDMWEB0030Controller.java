package com.lottemart.epc.edi.weborder.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
import org.springframework.web.servlet.ModelAndView;

import com.lcnjf.util.NumberUtil;
import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.weborder.model.ExcelUploadVO;
import com.lottemart.epc.edi.weborder.model.NEDMWEB0030VO;
import com.lottemart.epc.edi.weborder.service.NEDMWEB0030Service;

/**
 * @Class Name : NEDMWEB0030Controller
 * @Description : 발주일괄등록 Controller Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      		수정자          		수정내용
 *  -------    --------    ---------------------------
 * 2015.12.08	O YEUN KWON	  최초생성
 *
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */


@Controller
public class NEDMWEB0030Controller extends BaseController{

   private NEDMWEB0030Service nEDMWEB0030Service;

	@Autowired
	public void setnEDMWEB0003Service(NEDMWEB0030Service nEDMWEB0030Service) {
		this.nEDMWEB0030Service = nEDMWEB0030Service;
	}
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(NEDMWEB0030Controller.class);

	@Autowired
	private MessageSource messageSource;

	/**
	 * Desc : 발주 일괄등록 화면
	 * @Method Name : orderTotList
	 * @param NEDMWEB0030VO
	 * @param HttpServletRequest
	 * @param Model
	 * @return view 페이지
	 */
	@RequestMapping(value="/edi/weborder/NEDMWEB0030")
    public String orderTotList(NEDMWEB0030VO searchParam, HttpServletRequest request, Model model) {
		SimpleDateFormat format = new SimpleDateFormat("HHmm", Locale.KOREA);

		int nowDt = Integer.parseInt(format.format(new Date()));

		String vendorWebOrdFrDt = ConfigUtils.getString("edi.weboder.vendor.fr");
		String vendorWebOrdToDt = ConfigUtils.getString("edi.weboder.vendor.to");

		if( ( nowDt < Integer.parseInt(vendorWebOrdFrDt)) || ( nowDt > Integer.parseInt(vendorWebOrdToDt)) ){
			return "edi/weborder/NEDMWEB0098";
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

		return "edi/weborder/NEDMWEB0030";
	}


	/**
	 * Desc : 발주 일괄등록 정보 조회
	 * @Method Name : getStoreOrdDetInfo
	 * @param NEDMWEB0030VO
	 * @param Model
	 * @return 조회 값
	 */
	@RequestMapping("/edi/weborder/NEDMWEB0030tedOrdPackSelect.json")
	public ModelAndView getStoreOrdDetInfo(@RequestBody NEDMWEB0030VO vo) throws Throwable
	{
		return AjaxJsonModelHelper.create(nEDMWEB0030Service.selectOrdPackInfo(vo));
	}

	/**
	 * Desc : 엑셀 정보 삭제
	 * @Method Name : getExcelOrdDelete
	 * @param TedPoOrdPackVO
	 * @param HttpServletRequest
	 * @return message
	 */
	@RequestMapping("/edi/weborder/NEDMWEB0030tedOrdPackDelete.json")
	public ModelAndView getExcelOrdDelete(@RequestBody NEDMWEB0030VO vo) throws Exception
	{
		String message;
		String result;

		message = messageSource.getMessage("msg.common.fail.delete", null, Locale.getDefault());

		try {
			result = nEDMWEB0030Service.deleteExcelOrdInfo(vo);
			if(result.equals("suc")) return AjaxJsonModelHelper.create("");
			else return AjaxJsonModelHelper.create(message);
		} catch (Exception e) {
			return AjaxJsonModelHelper.create(message);
		}

	}

	/**
	 * Desc : 저장
	 * @Method Name : getExcelOrdSave
	 * @param TedOrdProcess001VO
	 * @param HttpServletRequest
	 * @return message
	 */
	@RequestMapping("/edi/weborder/NEDMWEB0030tedOrdPackSave.json")
	public ModelAndView getExcelOrdSave(@RequestBody NEDMWEB0030VO vo, HttpServletRequest request) throws Exception
	{
		String message1, message2, message3, message4, message5;
		String result;

		message1 = messageSource.getMessage("msg.common.fail.insert", null, Locale.getDefault());
		message2 = messageSource.getMessage("msg.weborder.fail.insert.error", null, Locale.getDefault());
		message3 = messageSource.getMessage("msg.weborder.fail.insert.excel.dupl", null, Locale.getDefault());
		message4 = messageSource.getMessage("msg.weborder.fail.insert.dupl", null, Locale.getDefault());
		message5 = messageSource.getMessage("msg.weborder.fail.insert.nodata", null, Locale.getDefault());

		try {
			result = nEDMWEB0030Service.insertExcelOrdInfo(vo, request);
			if(result.equals("suc")) return AjaxJsonModelHelper.create("");
			else if(result.equals("fail-001")) return AjaxJsonModelHelper.create(message2);
			else if(result.equals("fail-002")) return AjaxJsonModelHelper.create(message3);
			else if(result.equals("fail-003")) return AjaxJsonModelHelper.create(message4);
			else if(result.equals("fail-004")) return AjaxJsonModelHelper.create(message5);
			else return AjaxJsonModelHelper.create(message1);
		} catch (Exception e) {
			return AjaxJsonModelHelper.create(message1);
		}

	}

	/**
	 * Desc : 발주 정보 엑셀 업로드
	 * @Method Name : orderTotList
	 * @param NEDMWEB0030VO
	 * @param HttpServletRequest
	 * @param Model
	 * @return view 페이지
	 */
	@RequestMapping(value="/edi/weborder/NEDMWEB0030newOrdExcelUpload.do", method=RequestMethod.POST)
    public String ordExcelUpload(
    		@ModelAttribute("excel") ExcelUploadVO excel,
    		NEDMWEB0030VO vo,
    		HttpServletRequest request,
    		BindingResult result,
    		Model model) throws IOException{

		NEDMWEB0030VO searchParam = new NEDMWEB0030VO();
		SimpleDateFormat format = new SimpleDateFormat("HHmm", Locale.KOREA);

		int nowDt = Integer.parseInt(format.format(new Date()));

		String vendorWebOrdFrDt = ConfigUtils.getString("edi.weboder.vendor.fr");
		String vendorWebOrdToDt = ConfigUtils.getString("edi.weboder.vendor.to");

		if( ( nowDt < Integer.parseInt(vendorWebOrdFrDt)) || ( nowDt > Integer.parseInt(vendorWebOrdToDt)) ){
			return "edi/weborder/NEDMWEB0099";
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
			String ordVenCd = vo.getEntpCd();

	        try {
	        	//save & load location
				fileName = excel.getFile().getOriginalFilename();
				filePath = ConfigUtils.getString("edi.weborder.excel.path") + excel.getFile().getOriginalFilename();
				saveFile = new File(filePath);

				//save
				outputStream = new FileOutputStream(new File(filePath));
				outputStream.write(excel.getFile().getFileItem().get());
		        file = new FileInputStream(new File(filePath));

		        if(fileName.indexOf(".xlsx") > -1 || fileName.indexOf(".XLSX") > -1){
		        	workbook = new XSSFWorkbook(file);
		        }else{
		        	POIFSFileSystem myFileSystem = new POIFSFileSystem(file);
			    	oldWorkBook = new HSSFWorkbook(myFileSystem);
		        }

			} catch (Exception e) {
				logger.debug("=========================error " + e.toString());
				outputStream.close();
				saveFile.delete();
				model.addAttribute("stateCode", "data-error");
				return "edi/weborder/NEDMWEB0030";
			}


	        //sheet수 취득
			//int sheetCn = workbook.getNumberOfSheets();

			 // 엑셀 데이터를 담을 VO
	        ArrayList<NEDMWEB0030VO> list = new ArrayList<NEDMWEB0030VO>();
	        NEDMWEB0030VO excelData = null;

	        if(fileName.indexOf(".xlsx") > -1 || fileName.indexOf(".XLSX") > -1){
	        	for(int cn = 0; cn < 1; cn++){
					XSSFSheet sheet = workbook.getSheetAt(cn);
					// cell 개수
			        int cells = 3;
			        int rowNum = sheet.getLastRowNum();

			        if (rowNum > 10000){
			        	stateCode = "fail-006";
			        	model.addAttribute("stateCode", stateCode);
	       				return "edi/weborder/NEDMWEB0030";
			        }else{
			        	// sheet 개수 만큼 반복
				        for(Row row : sheet){
				        	cellNullFg = "F";
			        		excelData = new NEDMWEB0030VO();

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
						                   			else excelData.setOrdQty(value);
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
				               				return "edi/weborder/NEDMWEB0030";
				                   		}
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
					HSSFSheet sheet = oldWorkBook.getSheetAt(cn);
					// cell 개수
			        int cells = 3; //sheet.getRow(0).getLastCellNum();
			        int rowNum = sheet.getLastRowNum();

			        if (rowNum > 10000){
			        	stateCode = "fail-006";
			        	model.addAttribute("stateCode", stateCode);
	       				return "edi/weborder/NEDMWEB0030";
			        }else{
			        	// sheet 개수 만큼 반복
				        for(Row row : sheet){
				        	cellNullFg = "F";
			        		excelData = new NEDMWEB0030VO();

			        		if(sheet.getRow(row.getRowNum()).getCell((short)0) == null ){
				        		if(row.getCell((short)0) == null && row.getCell((short)1) == null && row.getCell((short)2) == null){
				        			cellNullFg = "T";
			        			}
			        		}

			        		if(!"T".equals(cellNullFg)){
			        			// cell 개수 만큼 반복
			        			for (int c = 0; c < cells; c++) {
			        				value = null;
			        				if(sheet.getRow(row.getRowNum()).getCell((short)0) == null ){
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
					                   			else excelData.setOrdQty(value);
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
			               				return "edi/weborder/NEDMWEB0030";
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
    			saveResult = nEDMWEB0030Service.insertOrdPackInfo(list, request, ordVenCd);
    			if(saveResult.equals("suc")) {
    				model.addAttribute("stateCode", "suc");
    			}else {
    				model.addAttribute("stateCode", "insert-fail");
    			}
    			list.clear();
   				return "edi/weborder/NEDMWEB0030";
    		} catch (Exception e) {
    			list.clear();
    			model.addAttribute("stateCode", "insert-fail");
   				return "edi/weborder/NEDMWEB0030";
    		}

	    }else{
	    	model.addAttribute("stateCode", "data-error");
	    }

		return "edi/weborder/NEDMWEB0030";
	}
}
