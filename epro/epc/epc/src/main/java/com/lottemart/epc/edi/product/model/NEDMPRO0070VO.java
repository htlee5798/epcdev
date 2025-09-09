package com.lottemart.epc.edi.product.model;

import java.io.Serializable;

public class NEDMPRO0070VO implements Serializable {

	private static final long serialVersionUID = 5056332308880552424L;

	private String num = "";
	private String code = "";
	private String name = "";
	
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
