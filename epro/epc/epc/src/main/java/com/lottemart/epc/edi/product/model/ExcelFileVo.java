package com.lottemart.epc.edi.product.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class ExcelFileVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -981107350310112860L;

	
	/**저장할 파일이름*/
	private String FileName;
	/**title map*/
	private HashMap<String, String> titleMap;
	/**footer map*/
	private HashMap<String, String> footerMap;
	/**cell style map*/
	private HashMap<String, String> dataStyleMap;
	/**footer Style map*/
	private HashMap<String, String> footerStyleMap;
	/**Keyset*/
	private String[] keyset;
	/**data list*/
	private List<HashMap<String, String>> datalist;
	/**cellWidth list*/
	private HashMap<String, Integer> cellWidthMap;
	public String getFileName() {
		return FileName;
	}
	public void setFileName(String fileName) {
		FileName = fileName;
	}
	public HashMap<String, String> getTitleMap() {
		return titleMap;
	}
	public void setTitleMap(HashMap<String, String> titleMap) {
		this.titleMap = titleMap;
	}
	public HashMap<String, String> getFooterMap() {
		return footerMap;
	}
	public void setFooterMap(HashMap<String, String> footerMap) {
		this.footerMap = footerMap;
	}
	public HashMap<String, String> getDataStyleMap() {
		return dataStyleMap;
	}
	public void setDataStyleMap(HashMap<String, String> dataStyleMap) {
		this.dataStyleMap = dataStyleMap;
	}
	public HashMap<String, String> getFooterStyleMap() {
		return footerStyleMap;
	}
	public void setFooterStyleMap(HashMap<String, String> footerStyleMap) {
		this.footerStyleMap = footerStyleMap;
	}
	public String[] getKeyset() {
		return keyset;
	}
	public void setKeyset(String[] keyset) {
		this.keyset = keyset;
	}
	public List<HashMap<String, String>> getDatalist() {
		return datalist;
	}
	public void setDatalist(List<HashMap<String, String>> datalist) {
		this.datalist = datalist;
	}
	public HashMap<String, Integer> getCellWidthMap() {
		return cellWidthMap;
	}
	public void setCellWidthMap(HashMap<String, Integer> cellWidthMap) {
		this.cellWidthMap = cellWidthMap;
	}
	
	
}
