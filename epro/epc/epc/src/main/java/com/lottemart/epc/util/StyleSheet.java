package com.lottemart.epc.util;

public class StyleSheet {
	String cl;
	String pageNumber;
	Integer linkPageNumber;

	public StyleSheet(String cl, String pageNumber, int linkPageNumber) {
		this.cl = cl;
		this.pageNumber = pageNumber;
		this.linkPageNumber = Integer.valueOf(linkPageNumber);
	}

	public String getCl() {
		return this.cl;
	}

	public String getPageNumber() {
		return this.pageNumber;
	}

	public int getLinkPageNumber() {
		return this.linkPageNumber.intValue();
	}
}
