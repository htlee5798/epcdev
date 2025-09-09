package com.lottemart.epc.common.util;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.lottemart.epc.edi.product.model.ExcelFileVo;

@Component
public class FileUtil {

	 public static ExcelFileVo toExcel(String fileName, List<HashMap<String, String>> datalist, String[] title, String[] dataStyle, int[] cellWidth, String[]keyset, String[] footer, String[] footerStyle){
			//Excel파일 정보/dataList
		 ExcelFileVo ExcelFileVo = new ExcelFileVo();
			//data list
			/*
			 * data는 Excel에 출력할 Column만 Query
			 */
			//title map
			HashMap<String, String> titleMap = new java.util.HashMap<String, String>();
			//footer map
			HashMap<String, String> footerMap = new java.util.HashMap<String, String>();
			//cell style map
			HashMap<String, String> dataStyleMap = new java.util.HashMap<String, String>();
			//footer Style map
			HashMap<String, String> footerStyleMap = new java.util.HashMap<String, String>();
			//cell width
			HashMap<String, Integer> cellWidthMap = new java.util.HashMap<String, Integer>();

			for(int i=0; i<keyset.length; i++){
			//	System.out.println("============Row: "+i+"  title:"+title[i]+" dataStyle:"+dataStyle[i]+" cellWidth:"+cellWidth[i]);
				titleMap.put(keyset[i], title[i]);
				dataStyleMap.put(keyset[i], dataStyle[i]);
				cellWidthMap.put(keyset[i], cellWidth[i]);
			}
			
			if(footer != null && footer.length>0) {
				for(int j=0; j<keyset.length; j++) {
					footerMap.put(keyset[j], footer[j]);
					cellWidthMap.put(keyset[j], cellWidth[j]);
					footerStyleMap.put(keyset[j], footerStyle[j]);
				}
			}

			ExcelFileVo.setFileName(fileName);
			ExcelFileVo.setDataStyleMap(dataStyleMap);
			ExcelFileVo.setFooterStyleMap(footerStyleMap);
			ExcelFileVo.setTitleMap(titleMap);
			ExcelFileVo.setFooterMap(footerMap);
			ExcelFileVo.setKeyset(keyset);
			ExcelFileVo.setDatalist(datalist);
			ExcelFileVo.setCellWidthMap(cellWidthMap);
			return ExcelFileVo;
		}
}
