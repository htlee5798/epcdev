package com.lottemart.epc.edi.consult.model;

import java.io.Serializable;
import java.util.ArrayList;

public class PEDMSCT0099VO implements Serializable {
	
	static final long serialVersionUID = -2526060967995750027L;
	
	private String srchStreetNm;
	private String srchBidngMainNo;
	
	private String bmanNo;
	private String venNm;
	private String zipCd;
	private String addr1;
	private String addr2;
	
	private String cityNm;
	private String guNm;
	private String streetNm;
	private String bidngMainNo;
	private String bidngSubNo;
	private String fullBidngNo;
	private String juso;
	
	private ArrayList zipInfoAl;
	
	private String beforeZip;
	private String beforeAddr1;
	private String beforeAddr2;	
	
	public String getSrchStreetNm() {
		return srchStreetNm;
	}
	public void setSrchStreetNm(String srchStreetNm) {
		this.srchStreetNm = srchStreetNm;
	}
	public String getSrchBidngMainNo() {
		return srchBidngMainNo;
	}
	public void setSrchBidngMainNo(String srchBidngMainNo) {
		this.srchBidngMainNo = srchBidngMainNo;
	}
	public String getBmanNo() {
		return bmanNo;
	}
	public void setBmanNo(String bmanNo) {
		this.bmanNo = bmanNo;
	}
	public String getVenNm() {
		return venNm;
	}
	public void setVenNm(String venNm) {
		this.venNm = venNm;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	public String getZipCd() {
		return zipCd;
	}
	public void setZipCd(String zipCd) {
		this.zipCd = zipCd;
	}
	public String getCityNm() {
		return cityNm;
	}
	public void setCityNm(String cityNm) {
		this.cityNm = cityNm;
	}
	public String getGuNm() {
		return guNm;
	}
	public void setGuNm(String guNm) {
		this.guNm = guNm;
	}
	public String getStreetNm() {
		return streetNm;
	}
	public void setStreetNm(String streetNm) {
		this.streetNm = streetNm;
	}
	public String getBidngMainNo() {
		return bidngMainNo;
	}
	public void setBidngMainNo(String bidngMainNo) {
		this.bidngMainNo = bidngMainNo;
	}
	public String getBidngSubNo() {
		return bidngSubNo;
	}
	public void setBidngSubNo(String bidngSubNo) {
		this.bidngSubNo = bidngSubNo;
	}
	public String getFullBidngNo() {
		return fullBidngNo;
	}
	public void setFullBidngNo(String fullBidngNo) {
		this.fullBidngNo = fullBidngNo;
	}
	public String getJuso() {
		return juso;
	}
	public void setJuso(String juso) {
		this.juso = juso;
	}
	public ArrayList getZipInfoAl() {
		if (this.zipInfoAl != null) {
			ArrayList ret = new ArrayList();
			for (int i = 0; i < zipInfoAl.size(); i++) {
				ret.add(i, this.zipInfoAl.get(i));
			}
			
			return ret;
		} else {
			return null;
		}
	}
	public void setZipInfoAl(ArrayList zipInfoAl) {
		if (zipInfoAl != null) {
			this.zipInfoAl = new ArrayList();
			for (int i = 0; i < zipInfoAl.size();i++) {
				this.zipInfoAl.add(i, zipInfoAl.get(i));
			}
		} else {
			this.zipInfoAl = null;
		}
	}
	public String getBeforeZip() {
		return beforeZip;
	}
	public void setBeforeZip(String beforeZip) {
		this.beforeZip = beforeZip;
	}
	public String getBeforeAddr1() {
		return beforeAddr1;
	}
	public void setBeforeAddr1(String beforeAddr1) {
		this.beforeAddr1 = beforeAddr1;
	}
	public String getBeforeAddr2() {
		return beforeAddr2;
	}
	public void setBeforeAddr2(String beforeAddr2) {
		this.beforeAddr2 = beforeAddr2;
	}
}
