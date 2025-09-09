package com.lottemart.epc.csr.model;

/**
 * @Class Name : CsrDyRegVO.java
 * @Description : CsrDyReg VO class
 * @Modification Information
 *
 * @author ywseo
 * @since 20131008
 * @version 1.0
 * @see
 *  
 *  Copyright (C)  All right reserved.
 */
public class CsrDyRegVO extends CsrDyRegDefaultVO{
    private static final long serialVersionUID = 1L;
    
    /** EMP_NO */
    private java.lang.String empNo;
    
    /** REG_DY */
    private java.lang.String regDy;
    
    /** SEQ */
    private java.lang.String seq;
    
	/** L1_CAT_CD */
    private java.lang.String l1CatCd;
    
    /** L2_CAT_CD */
    private java.lang.String l2CatCd;
    
    /** L3_CAT_CD */
    private java.lang.String l3CatCd;
    
    /** L1_CD */
    private java.lang.String l1Cd;
    
    /** L2_CD */
    private java.lang.String l2Cd;
    
    /** HH */
    private java.lang.String hh;
    
    /** MM */
    private java.lang.String mm;
    
    /** REG_DT */
    private java.sql.Date regDt;
    
    /** MOD_DT */
    private java.sql.Date modDt;
    
    
    public java.lang.String getL1CatCd() {
		return l1CatCd;
	}

	public void setL1CatCd(java.lang.String l1CatCd) {
		this.l1CatCd = l1CatCd;
	}

	public java.lang.String getL2CatCd() {
		return l2CatCd;
	}

	public void setL2CatCd(java.lang.String l2CatCd) {
		this.l2CatCd = l2CatCd;
	}


    
    
    
    public java.lang.String getEmpNo() {
        return this.empNo;
    }
    
    public void setEmpNo(java.lang.String empNo) {
        this.empNo = empNo;
    }
    
    public java.lang.String getRegDy() {
        return this.regDy;
    }
    
    public void setRegDy(java.lang.String regDy) {
        this.regDy = regDy;
    }
    
    public java.lang.String getSeq() {
        return this.seq;
    }
    
    public void setSeq(java.lang.String seq) {
        this.seq = seq;
    }
    
    public java.lang.String getL3CatCd() {
        return this.l3CatCd;
    }
    
    public void setL3CatCd(java.lang.String l3CatCd) {
        this.l3CatCd = l3CatCd;
    }
    
    public java.lang.String getL1Cd() {
        return this.l1Cd;
    }
    
    public void setL1Cd(java.lang.String l1Cd) {
        this.l1Cd = l1Cd;
    }
    
    public java.lang.String getL2Cd() {
        return this.l2Cd;
    }
    
    public void setL2Cd(java.lang.String l2Cd) {
        this.l2Cd = l2Cd;
    }
    
    public java.lang.String getHh() {
        return this.hh;
    }
    
    public void setHh(java.lang.String hh) {
        this.hh = hh;
    }
    
    public java.lang.String getMm() {
        return this.mm;
    }
    
    public void setMm(java.lang.String mm) {
        this.mm = mm;
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
