package com.lottemart.epc.csr.model;

/**
 * @Class Name : CsrEmpVO.java
 * @Description : CsrEmp VO class
 * @Modification Information
 *
 * @author ywseo
 * @since 20131008
 * @version 1.0
 * @see
 *  
 *  Copyright (C)  All right reserved.
 */
public class CsrEmpVO extends CsrEmpDefaultVO{
    private static final long serialVersionUID = 1L;
    
    /** EMP_NO */
    private java.lang.String empNo;
    
    /** EMP_NM */
    private java.lang.String empNm;
    
    /** POSITION_CD */
    private java.lang.String positionCd;
    
    /** POSITION_NM */
    private java.lang.String positionNm;
    
    /** HP_NO */
    private java.lang.String hpNo;
    
    /** EMAIL */
    private java.lang.String email;
    
    /** RETIRE_FG */
    private java.lang.String retireFg;
    
    /** REG_DT */
    private java.sql.Date regDt;
    
    /** MOD_DT */
    private java.sql.Date modDt;
    
    public java.lang.String getEmpNo() {
        return this.empNo;
    }
    
    public void setEmpNo(java.lang.String empNo) {
        this.empNo = empNo;
    }
    
    public java.lang.String getEmpNm() {
        return this.empNm;
    }
    
    public void setEmpNm(java.lang.String empNm) {
        this.empNm = empNm;
    }
    
    public java.lang.String getPositionCd() {
        return this.positionCd;
    }
    
    public void setPositionCd(java.lang.String positionCd) {
        this.positionCd = positionCd;
    }
    
    public java.lang.String getPositionNm() {
        return this.positionNm;
    }
    
    public void setPositionNm(java.lang.String positionNm) {
        this.positionNm = positionNm;
    }
    
    public java.lang.String getHpNo() {
        return this.hpNo;
    }
    
    public void setHpNo(java.lang.String hpNo) {
        this.hpNo = hpNo;
    }
    
    public java.lang.String getEmail() {
        return this.email;
    }
    
    public void setEmail(java.lang.String email) {
        this.email = email;
    }
    
    public java.lang.String getRetireFg() {
        return this.retireFg;
    }
    
    public void setRetireFg(java.lang.String retireFg) {
        this.retireFg = retireFg;
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
