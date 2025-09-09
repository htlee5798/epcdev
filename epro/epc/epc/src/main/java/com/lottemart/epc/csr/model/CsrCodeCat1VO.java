package com.lottemart.epc.csr.model;
import java.io.Serializable;


public class CsrCodeCat1VO  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4404797588970856922L;

	private String catNm;
    private String catCd;    
    private String catParCd;  
    private String catLvl;    
    private String dscrpt;    
    private String extent01;  
    private String regDt;    
    private String modDt;

    public String getCatNm() {
		return catNm;
	}
	public void setCatNm(String catNm) {
		this.catNm = catNm;
	}
	public String getCatCd() {
		return catCd;
	}
	public void setCatCd(String catCd) {
		this.catCd = catCd;
	}
	public String getCatParCd() {
		return catParCd;
	}
	public void setCatParCd(String catParCd) {
		this.catParCd = catParCd;
	}
	public String getCatLvl() {
		return catLvl;
	}
	public void setCatLvl(String catLvl) {
		this.catLvl = catLvl;
	}
	public String getDscrpt() {
		return dscrpt;
	}
	public void setDscrpt(String dscrpt) {
		this.dscrpt = dscrpt;
	}
	public String getExtent01() {
		return extent01;
	}
	public void setExtent01(String extent01) {
		this.extent01 = extent01;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getModDt() {
		return modDt;
	}
	public void setModDt(String modDt) {
		this.modDt = modDt;
	}

	
    
    
    
	
	
}
