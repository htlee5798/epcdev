package com.lottemart.epc.csr.model;

/**
 * @Class Name : CsrCodeVO.java
 * @Description : CsrCode VO class
 * @Modification Information
 *
 * @author ywseo
 * @since 20131008
 * @version 1.0
 * @see
 *  
 *  Copyright (C)  All right reserved.
 */
public class CsrCodeVO extends CsrCodeDefaultVO{
    private static final long serialVersionUID = 1L;
    
    /** CAT_NM */
    private java.lang.String catNm;
    
    /** CAT_CD */
    private java.lang.String catCd;
    
    /** CAT_PAR_CD */
    private java.lang.String catParCd;
    
    /** CAT_LVL */
    private java.lang.String catLvl;
    
    /** DSCRPT */
    private java.lang.String dscrpt;
    
    /** EXTENT01 */
    private java.lang.String extent01;
    
    /** REG_DT */
    private java.sql.Date regDt;
    
    /** MOD_DT */
    private java.sql.Date modDt;
    
    public java.lang.String getCatNm() {
        return this.catNm;
    }
    
    public void setCatNm(java.lang.String catNm) {
        this.catNm = catNm;
    }
    
    public java.lang.String getCatCd() {
        return this.catCd;
    }
    
    public void setCatCd(java.lang.String catCd) {
        this.catCd = catCd;
    }
    
    public java.lang.String getCatParCd() {
        return this.catParCd;
    }
    
    public void setCatParCd(java.lang.String catParCd) {
        this.catParCd = catParCd;
    }
    
    public java.lang.String getCatLvl() {
        return this.catLvl;
    }
    
    public void setCatLvl(java.lang.String catLvl) {
        this.catLvl = catLvl;
    }
    
    public java.lang.String getDscrpt() {
        return this.dscrpt;
    }
    
    public void setDscrpt(java.lang.String dscrpt) {
        this.dscrpt = dscrpt;
    }
    
    public java.lang.String getExtent01() {
        return this.extent01;
    }
    
    public void setExtent01(java.lang.String extent01) {
        this.extent01 = extent01;
    }
    
    public java.sql.Date getRegDt() {
        return this.regDt;
    }
    
    public void setRegDt(java.sql.Date regDt) {
        this.regDt = regDt;
    }
    
    public java.sql.Date getModDt() {
        return this.modDt;
    }
    
    public void setModDt(java.sql.Date modDt) {
        this.modDt = modDt;
    }
    
}
