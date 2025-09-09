package com.lottemart.epc.edi.srm.model;

import java.io.Serializable;

import com.lottemart.epc.edi.comm.model.PagingVO;

public class SRMVEN002001VO extends PagingVO implements Serializable {

	private static final long serialVersionUID = -5086768282532732513L;
	
	/** 순서 */
	private int rnum;
	/** 하우스코드 */
	private String houseCode;
	/** 품목코드	 */
	private String materialNumber;
	/** 자재명 */
	private String descriptionLoc;
	/** 자재명(영어) */
	private String descriptionEng;
	/** 협력업체코드 */
	private String venCd;
	/** 협력업체코드 */
	private String[] venCds;
	/** 상품코드 */
	private String prodCd;
	/** 검색타입 */
	private String searchType;
	
	public int getRnum() {
		return rnum;
	}
	public void setRnum(int rnum) {
		this.rnum = rnum;
	}
	public String getHouseCode() {
		return houseCode;
	}
	public void setHouseCode(String houseCode) {
		this.houseCode = houseCode;
	}
	public String getMaterialNumber() {
		return materialNumber;
	}
	public void setMaterialNumber(String materialNumber) {
		this.materialNumber = materialNumber;
	}
	public String getDescriptionLoc() {
		return descriptionLoc;
	}
	public void setDescriptionLoc(String descriptionLoc) {
		this.descriptionLoc = descriptionLoc;
	}
	public String getDescriptionEng() {
		return descriptionEng;
	}
	public void setDescriptionEng(String descriptionEng) {
		this.descriptionEng = descriptionEng;
	}
	public String getVenCd() {
		return venCd;
	}
	public void setVenCd(String venCd) {
		this.venCd = venCd;
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
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	
}
