package com.lottemart.epc.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lottemart.epc.edi.product.model.ExcelFileVo;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractXlsxStreamingView;

public class apachPoiExcelFileDownLoadViewResolver extends AbstractXlsxStreamingView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {

        ExcelFileVo excelFileVO = (ExcelFileVo) model.get("excelFile");
        
        //컬럼명 
        String[] keyset = excelFileVO.getKeyset();
        
        //데이터 리스트
        List<HashMap<String, String>> dataList = excelFileVO.getDatalist();
        
        //엑셀 헤더 제목 
        HashMap<String, String> title = excelFileVO.getTitleMap();
        
        //푸터 
        HashMap<String, String> footer = excelFileVO.getFooterMap();
        
        //데이터 스타일
        HashMap<String, String> dataStyle = excelFileVO.getDataStyleMap();
        
        //푸터 스타일
        HashMap<String, String> footerStyle = excelFileVO.getFooterStyleMap();
        
        //셀 넓이 설정 
        HashMap<String, Integer> cellWidth = excelFileVO.getCellWidthMap();

        Map<String, CellStyle> dataStyleMap = createCellStyles(workbook, dataStyle, keyset);
        Map<String, CellStyle> footerStyleMap = createCellStyles(workbook, footerStyle, keyset);

        // 시트 네임 
        String sheetName = excelFileVO.getFileName();
        String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String fileName = sheetName + date + ".xlsx";
        
        //브라우저 환경 별 인코딩 
        setFileNameToResponse(request, response, fileName);

        // 각 시트별 맥시멈 로우 수 
        int maxRowsPerSheet = 60000;
        
        //시트수 
   //     int totalSheets = (int) Math.ceil((double) dataList.size() / maxRowsPerSheet);
        
     	// 최소 1시트는 만들도록 보장
        int totalSheets = Math.max(1, (int) Math.ceil((double) dataList.size() / maxRowsPerSheet));

        for (int sheetIndex = 0; sheetIndex < totalSheets; sheetIndex++) {
        	// 시트 네임 (시트네임(1)) - 파일 이름으로 하므로 시트가 2개이상일 경우 인덱스 필요  
            Sheet sheet = workbook.createSheet(sheetName + "(" + (sheetIndex + 1) + ")");

            // Title row
            Row titleRow = sheet.createRow(0);
            for (int col = 0; col < keyset.length; col++) {
                Cell cell = titleRow.createCell(col);
                cell.setCellValue(title.get(keyset[col]));
                cell.setCellStyle(createTitleStyle(workbook));
                sheet.setColumnWidth(col, cellWidth.get(keyset[col]) * 256);
            }

            // Data rows
            int startIdx = sheetIndex * maxRowsPerSheet;
            int endIdx = Math.min(startIdx + maxRowsPerSheet, dataList.size());

            if (dataList.isEmpty()) {
            	  Row emptyRow = sheet.createRow(1);
            	    Cell cell = emptyRow.createCell(0);
            	    cell.setCellValue("조회된 데이터가 없습니다.");
            	    
            	    CellStyle style = workbook.createCellStyle();
            	    style.setAlignment(CellStyle.ALIGN_CENTER);
            	    Font font = workbook.createFont();
            	    cell.setCellStyle(style);

            	    // 첫 번째 열부터 병합 (전체 열 병합)
            	    sheet.addMergedRegion(new CellRangeAddress(
            	        1, 1, 0, keyset.length - 1
            	    ));
            }else {
            	
            	for (int i = startIdx; i < endIdx; i++) {
            		Row dataRow = sheet.createRow(i - startIdx + 1);
            		HashMap<String, String> rowData = dataList.get(i);
            		for (int col = 0; col < keyset.length; col++) {
            			String key = keyset[col];
            			Cell cell = dataRow.createCell(col);
            			
            			String value = "";
            			
            			if(String.valueOf(rowData.get(key)) != null &&  !String.valueOf(rowData.get(key)).equals("null")) {
            				value = String.valueOf(rowData.get(key));
            			}
            			
            			CellStyle style = dataStyleMap.getOrDefault(key, workbook.createCellStyle());
            			
            			// 숫자 포맷일 경우 숫자로 입력
            			if (style.getDataFormat() != 0 && isNumeric(value)) {
            				cell.setCellValue(Double.parseDouble(value));
            			} else {
            				cell.setCellType(1);	//String 으로 변환
            				cell.setCellValue(value);
            			}
            			cell.setCellStyle(style);
            			
            		}
            	}
            	
            }
            

            // Footer row
            if (footer != null && footer.size() > 0 && sheetIndex == totalSheets - 1) {
                Row footerRow = sheet.createRow(endIdx - startIdx + 1);
                for (int col = 0; col < keyset.length; col++) {
                    Cell cell = footerRow.createCell(col);
                    cell.setCellValue(footer.get(keyset[col]));
                    cell.setCellStyle(footerStyleMap.get(keyset[col]));
                }
            }
        }
    }

    private boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) return false;
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    private void setFileNameToResponse(HttpServletRequest request, HttpServletResponse response, String fileName)  throws UnsupportedEncodingException {

        String userAgent = request.getHeader("User-Agent");
        String encodedFileName;

        if (userAgent != null && (userAgent.contains("MSIE") || userAgent.contains("Trident"))) {
            encodedFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        } else if (userAgent != null && userAgent.contains("Chrome")) {
            StringBuilder sb = new StringBuilder();
            for (char c : fileName.toCharArray()) {
                if (c > '~') {
                    sb.append(URLEncoder.encode(Character.toString(c), "UTF-8"));
                } else {
                    sb.append(c);
                }
            }
            encodedFileName = sb.toString();
        } else {
            encodedFileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
        }

        response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFileName + "\"");
    }

    private CellStyle createTitleStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);

        Font font = workbook.createFont();
        font.setBoldweight((short)260);
        style.setFont(font);

        return style;
    }

    private Map<String, CellStyle> createCellStyles(Workbook workbook, HashMap<String, String> styleMap, String[] keyset) {
        Map<String, CellStyle> resultMap = new HashMap<>();

        for (String key : keyset) {
            String styleString = styleMap.getOrDefault(key, "");
            CellStyle style = workbook.createCellStyle();
            style.setBorderBottom(CellStyle.BORDER_THIN);
            style.setBorderTop(CellStyle.BORDER_THIN);
            style.setBorderLeft(CellStyle.BORDER_THIN);
            style.setBorderRight(CellStyle.BORDER_THIN);

            //컨트롤러에서 스타일 / 기준으로 오기때문에 / 으로 스플릿 해서 스타일 적용 
            String[] styles = styleString.split("/");
            DataFormat format = null;
            
            //컨트롤러에서 가져온 스타일 적용 
            for (String s : styles) {
                switch (s.trim()) {
                    case "LEFT":
                        style.setAlignment(CellStyle.ALIGN_LEFT); break;
                    case "CENTER":
                        style.setAlignment(CellStyle.ALIGN_CENTER); break;
                    case "RIGHT":
                        style.setAlignment(CellStyle.ALIGN_RIGHT); break;
                    case "#,##0":
                         format = workbook.createDataFormat();
                        style.setDataFormat(format.getFormat("#,##0")); break;
                    case "0.00":
                        style.setDataFormat(workbook.createDataFormat().getFormat("0.00")); break;
                    case "#,##0.00":
	                     format = workbook.createDataFormat();
	                    style.setDataFormat(format.getFormat("#,##0.00")); break;
                    case "0%":
                        style.setDataFormat(workbook.createDataFormat().getFormat("0\"%\"")); break;
                    case "0.00%":
                        style.setDataFormat(workbook.createDataFormat().getFormat("0.00\"%\"")); break;
                    case "BG_YELLOW":
                        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                        style.setFillPattern(CellStyle.SOLID_FOREGROUND); break;
                    case "BG_AQUA":
                        style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
                        style.setFillPattern(CellStyle.SOLID_FOREGROUND); break;
                    case "BG_BLACK":
                        style.setFillForegroundColor(IndexedColors.BLACK.getIndex());
                        style.setFillPattern(CellStyle.SOLID_FOREGROUND); break;
                    case "BG_BLUE":
                        style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
                        style.setFillPattern(CellStyle.SOLID_FOREGROUND); break;
                    case "BG_BLUE_GREY":
                        style.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
                        style.setFillPattern(CellStyle.SOLID_FOREGROUND); break;
                    case "BG_BRIGHT_GREEN":
                        style.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
                        style.setFillPattern(CellStyle.SOLID_FOREGROUND); break;
                    case "BG_BROWN":
                        style.setFillForegroundColor(IndexedColors.BROWN.getIndex());
                        style.setFillPattern(CellStyle.SOLID_FOREGROUND); break;
                    case "BG_CORAL":
                        style.setFillForegroundColor(IndexedColors.CORAL.getIndex());
                        style.setFillPattern(CellStyle.SOLID_FOREGROUND); break;
                    case "BG_DARK_BLUE":
                        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
                        style.setFillPattern(CellStyle.SOLID_FOREGROUND); break;
                    case "BG_DARK_GREEN":
                        style.setFillForegroundColor(IndexedColors.DARK_GREEN.getIndex());
                        style.setFillPattern(CellStyle.SOLID_FOREGROUND); break;
                    case "BG_DARK_RED":
                    	style.setFillForegroundColor(IndexedColors.DARK_RED.getIndex());
                    	style.setFillPattern(CellStyle.SOLID_FOREGROUND); break;
                    case "BG_DARK_TEAL":
                    	style.setFillForegroundColor(IndexedColors.DARK_TEAL.getIndex());
                    	style.setFillPattern(CellStyle.SOLID_FOREGROUND); break;
                    case "BG_DARK_YELLOW":
                    	style.setFillForegroundColor(IndexedColors.DARK_YELLOW.getIndex());
                    	style.setFillPattern(CellStyle.SOLID_FOREGROUND); break;
                    case "BG_GOLD":
                    	style.setFillForegroundColor(IndexedColors.GOLD.getIndex());
                    	style.setFillPattern(CellStyle.SOLID_FOREGROUND); break;
                    case "BG_GREEN":
                    	style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
                    	style.setFillPattern(CellStyle.SOLID_FOREGROUND); break;
                    case "BG_GREY_25_PERCENT":
                    	style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                    	style.setFillPattern(CellStyle.SOLID_FOREGROUND); break;
                    case "BG_GREY_40_PERCENT":
                    	style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
                    	style.setFillPattern(CellStyle.SOLID_FOREGROUND); break;
                    case "BG_GREY_50_PERCENT":
                    	style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
                    	style.setFillPattern(CellStyle.SOLID_FOREGROUND); break;
                    case "BG_GREY_80_PERCENT":
                    	style.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.getIndex());
                    	style.setFillPattern(CellStyle.SOLID_FOREGROUND); break;
                    case "BG_INDIGO":
                    	style.setFillForegroundColor(IndexedColors.INDIGO.getIndex());
                    	style.setFillPattern(CellStyle.SOLID_FOREGROUND); break;
                    case "BG_LAVENDER":
                    	style.setFillForegroundColor(IndexedColors.LAVENDER.getIndex());
                    	style.setFillPattern(CellStyle.SOLID_FOREGROUND); break;
                    case "BG_LIGHT_BLUE":
                    	style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
                    	style.setFillPattern(CellStyle.SOLID_FOREGROUND); break;
                    case "BG_LIGHT_GREEN":
                    	style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
                    	style.setFillPattern(CellStyle.SOLID_FOREGROUND); break;
                    case "BG_LIGHT_ORANGE":
                    	style.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
                    	style.setFillPattern(CellStyle.SOLID_FOREGROUND); break;
                    case "BG_LIGHT_TURQUOISE":
                    	style.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
                    	style.setFillPattern(CellStyle.SOLID_FOREGROUND); break;
                    case "BG_LIME":
                    	style.setFillForegroundColor(IndexedColors.LIME.getIndex());
                    	style.setFillPattern(CellStyle.SOLID_FOREGROUND); break;
                    case "BG_OLIVE_GREEN":
                    	style.setFillForegroundColor(IndexedColors.OLIVE_GREEN.getIndex());
                    	style.setFillPattern(CellStyle.SOLID_FOREGROUND); break;
                    case "BG_ORANGE":
                    	style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
                    	style.setFillPattern(CellStyle.SOLID_FOREGROUND); break;
                    case "BG_PALE_BLUE":
                    	style.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
                    	style.setFillPattern(CellStyle.SOLID_FOREGROUND); break;
                    case "BG_PINK":
                    	style.setFillForegroundColor(IndexedColors.PINK.getIndex());
                    	style.setFillPattern(CellStyle.SOLID_FOREGROUND); break;
                    case "BG_PLUM":
                    	style.setFillForegroundColor(IndexedColors.PLUM.getIndex());
                    	style.setFillPattern(CellStyle.SOLID_FOREGROUND); break;
                    case "BG_RED":
                    	style.setFillForegroundColor(IndexedColors.RED.getIndex());
                    	style.setFillPattern(CellStyle.SOLID_FOREGROUND); break;
                    case "BG_ROSE":
                    	style.setFillForegroundColor(IndexedColors.ROSE.getIndex());
                    	style.setFillPattern(CellStyle.SOLID_FOREGROUND); break;
                    case "BG_SEA_GREEN":
                    	style.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());
                    	style.setFillPattern(CellStyle.SOLID_FOREGROUND); break;
                    case "BG_SKY_BLUE":
                    	style.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
                    	style.setFillPattern(CellStyle.SOLID_FOREGROUND); break;
                    case "BG_TAN":
                    	style.setFillForegroundColor(IndexedColors.TEAL.getIndex());
                    	style.setFillPattern(CellStyle.SOLID_FOREGROUND); break;
                    case "BG_TEAL":
                    	style.setFillForegroundColor(IndexedColors.TAN.getIndex());
                    	style.setFillPattern(CellStyle.SOLID_FOREGROUND); break;
                    case "BG_TURQUOISE":
                    	style.setFillForegroundColor(IndexedColors.TURQUOISE.getIndex());
                    	style.setFillPattern(CellStyle.SOLID_FOREGROUND); break;
                    case "BG_VIOLET":
                    	style.setFillForegroundColor(IndexedColors.VIOLET.getIndex());
                    	style.setFillPattern(CellStyle.SOLID_FOREGROUND); break;
                }
            }

            resultMap.put(key, style);
        }

        return resultMap;
    }
}