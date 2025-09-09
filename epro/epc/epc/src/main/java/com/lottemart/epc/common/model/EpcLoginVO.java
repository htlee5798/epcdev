package com.lottemart.epc.common.model;

import java.io.Serializable;

public class EpcLoginVO implements Serializable {

	

	/** 
		 * @see com.lottemart.epc.common.model
		 * @Method Name  : EpcLoginVO.java
		 * @since      : 2011
		 * @author     : jmryu
		 * @version    :
		 * @Locaton    : com.lottemart.epc.common.model
		 * @description : 
		 * @return
	*/
	private static final long serialVersionUID = -4028986084630639047L;
	
	private String [] cono            ;           // 사업자번호
    private String [] vendorId        ;           // 거래처ID
    private String    loginNm         ;           // 로그인명  - 메인화면 출력용
    private String    adminId         ;
    private String    adminNm         ;
    private String    adminTypeCd     ;
    private String    vendorTypeCd    ;
    private String    repVendorId     ;
    private String [] tradeType       ;
    private String [] tradeTypeNm     ;
    
	private boolean   happyGubn = false;
    private boolean   allianceGubn = false;
    
    private String [] zzorg			;	//조직구분
    private String [] zzorgNm		;	//조직구분명
    
    private String	admFg			;	//어드민구분 (1: 협력사, 2:MD 등 관리자)
    private String	sysAdmFg		;	//시스템관리자구분
    
    private String	loginWorkId		;	//로그인 아이디_작업자구분용
    
    
    public String getAdminTypeCd() {
		return adminTypeCd;
	}
	public void setAdminTypeCd(String adminTypeCd) {
		this.adminTypeCd = adminTypeCd;
	}

    public boolean isAllianceGubn() {
		return allianceGubn;
	}
	public void setAllianceGubn(boolean allianceGubn) {
		this.allianceGubn = allianceGubn;
	}
	public boolean isHappyGubn() {
		return happyGubn;
	}
	public void setHappyGubn(boolean happyGubn) {
		this.happyGubn = happyGubn;
	}
	public String[] getCono() {
		return cono;
	}
	public void setCono(String[] cono) {
		this.cono = cono;
	}
	public String[] getVendorId() {
		return vendorId;
	}
	public void setVendorId(String[] vendorId) {
		this.vendorId = vendorId;
	}
	public String getLoginNm() {
		return loginNm;
	}
	public void setLoginNm(String loginNm) {
		this.loginNm = loginNm;
	}
	
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	public String getAdminNm() {
		return adminNm;
	}
	public void setAdminNm(String adminNm) {
		this.adminNm = adminNm;
	}
	public String getVendorTypeCd() {
		return vendorTypeCd;
	}
	public void setVendorTypeCd(String vendorTypeCd) {
		this.vendorTypeCd = vendorTypeCd;
	}
	public String getRepVendorId() {
		return repVendorId;
	}
	public void setRepVendorId(String repVendorId) {
		this.repVendorId = repVendorId;
	}    
    
	public String[] getTradeType() {
		return tradeType;
	}
	public void setTradeType(String[] tradeType) {
		this.tradeType = tradeType;
	}
	public String[] getTradeTypeNm() {
		return tradeTypeNm;
	}
	public void setTradeTypeNm(String[] tradeTypeNm) {
		this.tradeTypeNm = tradeTypeNm;
	}
	public String[] getZzorg() {
		return zzorg;
	}
	public void setZzorg(String[] zzorg) {
		this.zzorg = zzorg;
	}
	public String[] getZzorgNm() {
		return zzorgNm;
	}
	public void setZzorgNm(String[] zzorgNm) {
		this.zzorgNm = zzorgNm;
	}
	public String getAdmFg() {
		return admFg;
	}
	public void setAdmFg(String admFg) {
		this.admFg = admFg;
	}
	public String getSysAdmFg() {
		return sysAdmFg;
	}
	public void setSysAdmFg(String sysAdmFg) {
		this.sysAdmFg = sysAdmFg;
	}
	public String getLoginWorkId() {
		return loginWorkId;
	}
	public void setLoginWorkId(String loginWorkId) {
		this.loginWorkId = loginWorkId;
	}
    

}
