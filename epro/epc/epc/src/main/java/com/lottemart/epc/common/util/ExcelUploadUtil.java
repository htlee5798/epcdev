package com.lottemart.epc.common.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;

import com.ibm.icu.text.SimpleDateFormat;


public class ExcelUploadUtil {
	
	/**
	 * 확장자(XLS) 데이터 가져오기
	 */
	public static String cellValueHss(Row row, int c) throws Exception {
		String value = "";

		if(row.getCell(c) != null) {
			switch (row.getCell(c).getCellType()) {
			case HSSFCell.CELL_TYPE_FORMULA:
				//value = row.getCell(c).getCellFormula();
				row.getCell(c).setCellType(HSSFCell.CELL_TYPE_STRING);
				value = "" + row.getCell(c).getStringCellValue();
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:

				if (HSSFDateUtil.isCellDateFormatted(row.getCell(c))){
				        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
				        value = formatter.format(row.getCell(c).getDateCellValue());
				} else {
					 row.getCell(c).setCellType(HSSFCell.CELL_TYPE_STRING);
					 value = "" + row.getCell(c).getStringCellValue();
				}
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

		return value;
	}
	
	/**
	 * 확장자(XLSX) 데이터 가져오기
	 */
	public static String cellValueXss(Row row, int c) throws Exception{
		String value = "";

		if(row.getCell(c) != null) {

			switch (row.getCell(c).getCellType()) {
			case XSSFCell.CELL_TYPE_FORMULA:
				//value = row.getCell(c).getCellFormula();
				row.getCell(c).setCellType(HSSFCell.CELL_TYPE_STRING);
				value = "" + row.getCell(c).getStringCellValue();
				break;
			case XSSFCell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(row.getCell(c))){
			        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
			        value = formatter.format(row.getCell(c).getDateCellValue());
				} else {
					row.getCell(c).setCellType(HSSFCell.CELL_TYPE_STRING);
					 value = "" + row.getCell(c).getStringCellValue();
				}
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
		return value;
	}

}
