package com.lottemart.epc.edi.weborder.model;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class ExcelUploadVO {
	
	private String strCd = null;
	private String prodCd = null;
	private String ordQty = null;
	
	private CommonsMultipartFile file = null;
	
	public String getStrCd() {
		return strCd;
	}

	public void setStrCd(String strCd) {
		this.strCd = strCd;
	}

	public String getProdCd() {
		return prodCd;
	}

	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}

	public String getOrdQty() {
		return ordQty;
	}

	public void setOrdQty(String ordQty) {
		this.ordQty = ordQty;
	}

	public CommonsMultipartFile getFile() {
		return file;
	}

	public void setFile(CommonsMultipartFile file) {
		this.file = file;
	}
}
