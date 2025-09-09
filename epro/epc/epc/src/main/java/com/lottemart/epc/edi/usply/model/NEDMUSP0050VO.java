package com.lottemart.epc.edi.usply.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @Class Name : NEDMUSP0050VO
 * @Description : 미납사유조회 VO
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 11. 04.  SUN GIL CHOI 		오후 2:32:38 
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */

public class NEDMUSP0050VO implements Serializable {

	
	private static final long serialVersionUID = -6918711902569942487L;


	public NEDMUSP0050VO () {}
	
	//crud파람
	private List<NEDMUSP0050VO> updateParam;
	
	/** 검색시작일 */
	private String srchStartDate;
	/** 검색종료일 */
	private String srchEndDate;
	/** 협력업체코드 */
	private String searchEntpCd;
	/** 상품선택 */
	private String searchProductVal;
	/** 판매코드 */
	private String srchSellCode;
	/** 점포코드(배열) */
	private ArrayList searchStoreAl;	
	
	/** TEXT Data */
	private String textData;
	/** 협력업체 코드 */// 
	private String[] venCds;
	//
	
	/** 등록순번 */
	private String regVseq;
	/**미납일 */
	private String splyDy;
	/** 판매코드 */
	private String srcmkCd;
	/** 상품명 */
	private String prodNm;
	/** 상품규격 */
	private String prodStd;
	/** 발주입수 */
	private String ordIpsu;
	/** 발주단위 */
	private String ordUnit;
	/** 점포명  */
	private String strNm;
	/** 미납수량  */
	private String usplyQty;
	/** 미납원가금액  */
	private String usplyBuyAmt;
	/** 협력업체사유구분 */
	private String venReasonNm;
	/** 협력업체사유상세구분 */
	private String venDetailNm;
	/**  */
	private String regReasonFg;
	/** 확정사유구분 */
	private String cfmReasonFg;
	/**  */
	private String regDetailFg;
	/** 확정사유상세구분 */
	private String cfmDetailFg;
	/** 상품코드 */
	private String prodCd;
	/** 전표번호 */
	private String ordSlipNo;
	/** 협력업체사유구분 */
	private String venReasonFg;
	/** 협력업체상세구분 */
	private String venDetailFg;
	/** 협력업체확인구분 */
	private String venCfmFg;
	
	private String buyerConfirm;
	
	


	public List<NEDMUSP0050VO> getUpdateParam() {
		return updateParam;
	}

	public void setUpdateParam(List<NEDMUSP0050VO> updateParam) {
		this.updateParam = updateParam;
	}

	public String getSrchStartDate() {
		return srchStartDate;
	}

	public void setSrchStartDate(String srchStartDate) {
		this.srchStartDate = srchStartDate;
	}

	public String getSrchEndDate() {
		return srchEndDate;
	}

	public void setSrchEndDate(String srchEndDate) {
		this.srchEndDate = srchEndDate;
	}

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
	
	public String getSrchSellCode() {
		return srchSellCode;
	}

	public void setSrchSellCode(String srchSellCode) {
		this.srchSellCode = srchSellCode;
	}
	

	public ArrayList getSearchStoreAl() {
		if (this.searchStoreAl != null) {
			ArrayList ret = new ArrayList();
			for (int i = 0; i < searchStoreAl.size(); i++) {
				ret.add(i, this.searchStoreAl.get(i));
			}
			
			return ret;
		} else {
			return null;
		}
	}

	public void setSearchStoreAl(ArrayList searchStoreAl) {
		if (searchStoreAl != null) {
			this.searchStoreAl = new ArrayList();
			for (int i = 0; i < searchStoreAl.size();i++) {
				this.searchStoreAl.add(i, searchStoreAl.get(i));
			}
		} else {
			this.searchStoreAl = null;
		}
	}

	public String getTextData() {
		return textData;
	}

	public void setTextData(String textData) {
		this.textData = textData;
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

	public String getOrdSlipNo() {
		return ordSlipNo;
	}

	public void setOrdSlipNo(String ordSlipNo) {
		this.ordSlipNo = ordSlipNo;
	}
	
	public String getSplyDy() {
		return splyDy;
	}

	public void setSplyDy(String splyDy) {
		this.splyDy = splyDy;
	}
	public String getSrcmkCd() {
		return srcmkCd;
	}

	public void setSrcmkCd(String srcmkCd) {
		this.srcmkCd = srcmkCd;
	}

	public String getProdNm() {
		return prodNm;
	}

	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}

	public String getProdStd() {
		return prodStd;
	}

	public void setProdStd(String prodStd) {
		this.prodStd = prodStd;
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

	public String getStrNm() {
		return strNm;
	}

	public void setStrNm(String strNm) {
		this.strNm = strNm;
	}

	public String getUsplyQty() {
		return usplyQty;
	}

	public void setUsplyQty(String usplyQty) {
		this.usplyQty = usplyQty;
	}

	public String getUsplyBuyAmt() {
		return usplyBuyAmt;
	}

	public void setUsplyBuyAmt(String usplyBuyAmt) {
		this.usplyBuyAmt = usplyBuyAmt;
	}

	public String getVenReasonNm() {
		return venReasonNm;
	}

	public void setVenReasonNm(String venReasonNm) {
		this.venReasonNm = venReasonNm;
	}

	public String getVenDetailNm() {
		return venDetailNm;
	}

	public void setVenDetailNm(String venDetailNm) {
		this.venDetailNm = venDetailNm;
	}

	public String getRegReasonFg() {
		return regReasonFg;
	}

	public void setRegReasonFg(String regReasonFg) {
		this.regReasonFg = regReasonFg;
	}

	public String getCfmReasonFg() {
		return cfmReasonFg;
	}

	public void setCfmReasonFg(String cfmReasonFg) {
		this.cfmReasonFg = cfmReasonFg;
	}

	public String getRegDetailFg() {
		return regDetailFg;
	}

	public void setRegDetailFg(String regDetailFg) {
		this.regDetailFg = regDetailFg;
	}

	public String getCfmDetailFg() {
		return cfmDetailFg;
	}

	public void setCfmDetailFg(String cfmDetailFg) {
		this.cfmDetailFg = cfmDetailFg;
	}

	public String getProdCd() {
		return prodCd;
	}

	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}

	public String getVenReasonFg() {
		return venReasonFg;
	}

	public void setVenReasonFg(String venReasonFg) {
		this.venReasonFg = venReasonFg;
	}

	public String getVenDetailFg() {
		return venDetailFg;
	}

	public void setVenDetailFg(String venDetailFg) {
		this.venDetailFg = venDetailFg;
	}

	public String getVenCfmFg() {
		return venCfmFg;
	}

	public void setVenCfmFg(String venCfmFg) {
		this.venCfmFg = venCfmFg;
	}

	public String getRegVseq() {
		return regVseq;
	}

	public void setRegVseq(String regVseq) {
		this.regVseq = regVseq;
	}

	public String getBuyerConfirm() {
		if(this.regReasonFg != this.cfmReasonFg || this.regDetailFg != this.cfmDetailFg){
			buyerConfirm = "바이어 확정";
		}else{
			buyerConfirm = "바이어 미확정";
		}
		return buyerConfirm;
	}


	
}
