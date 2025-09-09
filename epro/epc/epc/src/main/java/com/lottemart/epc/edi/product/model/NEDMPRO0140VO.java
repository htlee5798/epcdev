package com.lottemart.epc.edi.product.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.lottemart.epc.edi.comm.model.PagingVO;

public class NEDMPRO0140VO extends PagingVO implements Serializable {

	//static final long serialVersionUID = 1776508153203875384L;

	public NEDMPRO0140VO () {}
	
	
	/** 등록기간(From) */
	private String srchStartDate;
	/** 등록기간(To) */
	//private String srchEndDate;
	/** 협력업체 */
	private String searchEntpCd;
	/** 상품구분 */
	private String searchProductVal;

	private String sumDy;
	private String sumWk;
	private String venCd;

	public String getSumDy() {
		return sumDy;
	}
	public void setSumDy(String sumDy) {
		this.sumDy = sumDy;
	}
	public String getSumWk() {
		return sumWk;
	}
	public void setSumWk(String sumWk) {
		this.sumWk = sumWk;
	}
	public String getVenCd() {
		return venCd;
	}
	public void setVenCd(String venCd) {
		this.venCd = venCd;
	}
	public String getL1Cd() {
		return l1Cd;
	}
	public void setL1Cd(String l1Cd) {
		this.l1Cd = l1Cd;
	}
	public String getL1Nm() {
		return l1Nm;
	}
	public void setL1Nm(String l1Nm) {
		this.l1Nm = l1Nm;
	}
	public String getL2Cd() {
		return l2Cd;
	}
	public void setL2Cd(String l2Cd) {
		this.l2Cd = l2Cd;
	}
	public String getL2Nm() {
		return l2Nm;
	}
	public void setL2Nm(String l2Nm) {
		this.l2Nm = l2Nm;
	}
	public String getL3Cd() {
		return l3Cd;
	}
	public void setL3Cd(String l3Cd) {
		this.l3Cd = l3Cd;
	}
	public String getL3Nm() {
		return l3Nm;
	}
	public void setL3Nm(String l3Nm) {
		this.l3Nm = l3Nm;
	}
	public String getSrcmkCd() {
		return srcmkCd;
	}
	public void setSrcmkCd(String srcmkCd) {
		this.srcmkCd = srcmkCd;
	}


	private String prodCd; 
	private String prodNm; 
	private String l1Cd;
	private String l1Nm;	
	private String l2Cd;
	private String l2Nm;
	private String l3Cd;
	private String l3Nm;
	private String srcmkCd;
	private String gradeCd;
	private String gradeNm;
	private String zzekorg;
	private String plcFg;
	public String getplcFg() {
		return plcFg;
	}
	public void setplcFg(String plcFg) {
		this.plcFg = plcFg;
	}
	public String getZzekorg() {
		return zzekorg;
	}
	public void setZzekorg(String zzekorg) {
		this.zzekorg = zzekorg;
	}
	public String getGradeNm() {
		return gradeNm;
	}
	public void setGradeNm(String gradeNm) {
		this.gradeNm = gradeNm;
	}
	public String getGradeCd() {
		return gradeCd;
	}
	public void setGradeCd(String gradeCd) {
		this.gradeCd = gradeCd;
	}



	/** 발주일 */
	private String ordDy; 

	/** 발주입수 */
	private String ordIpsu; 
	/** 발주단위 */
	private String ordUnit; 
	/** 발주단위명 */
	private String purUnitCdNm; 
	/** 발주수량 */
	private String ordQty; 
	/** 원가 */
	private String buyPrc;
	/** 협력업체 코드 */
	private String[] venCds;
	

	public String getSrchStartDate() {
		return srchStartDate;
	}
	public void setSrchStartDate(String srchStartDate) {
		this.srchStartDate = srchStartDate;
	}
	//public String getSrchEndDate() {
	//	return srchEndDate;
	//}
	//public void setSrchEndDate(String srchEndDate) {
	//	this.srchEndDate = srchEndDate;
	//}
	public String getSearchEntpCd() {
		return searchEntpCd;
	}
	public void setSearchEntpCd(String searchEntpCd) {
		this.searchEntpCd = searchEntpCd;
	}
	public String getSearchProductVal() {
		return searchProductVal;
	}
	public void setSearchProductVal(String searchProductVal) {
		this.searchProductVal = searchProductVal;
	}
	public String getOrdDy() {
		return ordDy;
	}
	public void setOrdDy(String ordDy) {
		this.ordDy = ordDy;
	}
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	public String getProdNm() {
		return prodNm;
	}
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}
	public String getOrdIpsu() {
		return ordIpsu;
	}
	public void setOrdIpsu(String ordIpsu) {
		this.ordIpsu = ordIpsu;
	}
	public String getOrdUnit() {
		return ordUnit;
	}
	public void setOrdUnit(String ordUnit) {
		this.ordUnit = ordUnit;
	}
	public String getPurUnitCdNm() {
		return purUnitCdNm;
	}
	public void setPurUnitCdNm(String purUnitCdNm) {
		this.purUnitCdNm = purUnitCdNm;
	}
	public String getOrdQty() {
		return ordQty;
	}
	public void setOrdQty(String ordQty) {
		this.ordQty = ordQty;
	}
	public String getBuyPrc() {
		return buyPrc;
	}
	public void setBuyPrc(String buyPrc) {
		this.buyPrc = buyPrc;
	}
	public String[] getVenCds() {
		if (this.venCds != null) {
			String[] ret = new String[venCds.length];
			for (int i = 0; i < venCds.length; i++) { 
				ret[i] = this.venCds[i]; 
			}
			return ret;
		} else {
			return null;
		}
	}
	public void setVenCds(String[] venCds) {
		if (venCds != null) {
			 this.venCds = new String[venCds.length];			 
			 for (int i = 0; i < venCds.length; ++i) {
				 this.venCds[i] = venCds[i];
			 }
		 } else {
			 this.venCds = null;
		 }
	}



}
