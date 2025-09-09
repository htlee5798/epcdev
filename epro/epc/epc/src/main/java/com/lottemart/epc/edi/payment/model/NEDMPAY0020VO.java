package com.lottemart.epc.edi.payment.model;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * @Class Name : NEDMORD0010VO
 * @Description : 발주정보 -> 기간정보 VO
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

public class NEDMPAY0020VO implements Serializable {

	
	private static final long serialVersionUID = -6918711902569942487L;


	public NEDMPAY0020VO () {}
	
	/** 검색시작일 */
	private String srchStartDate;
	/** 검색종료일 */
	private String srchEndDate;
	/** 협력업체코드 */
	private String searchEntpCd;
	/** 상품선택 */
	private String searchProductVal;
	/** 점포코드(배열) */
	private ArrayList searchStoreAl;
	/** TEXT Data */
	private String textData;
	/** 발주정보 */
	private String searchOrdering;
	/** 협력업체 코드 */
	private String[] venCds;
	
	private String payDay; 				//
	private String payBuy; 			    //
	private String subBuy; 			    //
	
    private String mul; 
    private String usply; 
    private String infoAnlyFee; 
    private String sinsang; 
    private String sinsang2; 
    private String newProdIncentFee; 
    private String ehbtIncentFee; 
    private String pfrmIncentFee; 
    private String facilityFee; 
    private String onlineAdFee; 
    private String movStdFee; 
    private String bottDedu; 
    private String alcoBottDedu; 
    private String digitalAdFee;
    private String etcDedu;
    private String total;
	private String occationPayBuy;      // 
	private String occationSubPay;      //
	private String realPayBuy;
	
	

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
	public String getSearchOrdering() {
		return searchOrdering;
	}
	public void setSearchOrdering(String searchOrdering) {
		this.searchOrdering = searchOrdering;
	}
	public String getPayDay() {
		return payDay;
	}
	public void setPayDay(String payDay) {
		this.payDay = payDay;
	}
	public String getPayBuy() {
		return payBuy;
	}
	public void setPayBuy(String payBuy) {
		this.payBuy = payBuy;
	}
	public String getSubBuy() {
		return subBuy;
	}
	public void setSubBuy(String subBuy) {
		this.subBuy = subBuy;
	}
	public String getMul() {
		return mul;
	}
	public void setMul(String mul) {
		this.mul = mul;
	}
	public String getUsply() {
		return usply;
	}
	public void setUsply(String usply) {
		this.usply = usply;
	}
	public String getInfoAnlyFee() {
		return infoAnlyFee;
	}
	public void setInfoAnlyFee(String infoAnlyFee) {
		this.infoAnlyFee = infoAnlyFee;
	}
	public String getSinsang() {
		return sinsang;
	}
	public void setSinsang(String sinsang) {
		this.sinsang = sinsang;
	}
	public String getSinsang2() {
		return sinsang2;
	}
	public void setSinsang2(String sinsang2) {
		this.sinsang2 = sinsang2;
	}
	public String getNewProdIncentFee() {
		return newProdIncentFee;
	}
	public void setNewProdIncentFee(String newProdIncentFee) {
		this.newProdIncentFee = newProdIncentFee;
	}
	public String getEhbtIncentFee() {
		return ehbtIncentFee;
	}
	public void setEhbtIncentFee(String ehbtIncentFee) {
		this.ehbtIncentFee = ehbtIncentFee;
	}
	public String getPfrmIncentFee() {
		return pfrmIncentFee;
	}
	public void setPfrmIncentFee(String pfrmIncentFee) {
		this.pfrmIncentFee = pfrmIncentFee;
	}
	public String getFacilityFee() {
		return facilityFee;
	}
	public void setFacilityFee(String facilityFee) {
		this.facilityFee= facilityFee;
	}
	public String getOnlineAdFee() {
		return onlineAdFee;
	}
	public void setOnlineAdFee(String onlineAdFee) {
		this.onlineAdFee = onlineAdFee;
	}
	public String getMovStdFee() {
		return movStdFee;
	}
	public void setMovStdFee(String movStdFee) {
		this.movStdFee = movStdFee;
	}
	public String getBottDedu() {
		return bottDedu;
	}
	public void setBottDedu(String bottDedu) {
		this.bottDedu = bottDedu;
	}
	public String getAlcoBottDedu() {
		return alcoBottDedu;
	}
	public void setAlcoBottDedu(String alcoBottDedu) {
		this.alcoBottDedu = alcoBottDedu;
	}
	public String getDigitalAdFee() {
		return digitalAdFee;
	}
	public void setDigitalAdFee(String digitalAdFee) {
		this.digitalAdFee = digitalAdFee;
	}
	public String getEtcDedu() {
		return etcDedu;
	}
	public void setEtcDedu(String etcDedu) {
		this.etcDedu = etcDedu;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getOccationPayBuy() {
		return occationPayBuy;
	}
	public void setOccationPayBuy(String occationPayBuy) {
		this.occationPayBuy = occationPayBuy;
	}
	public String getOccationSubPay() {
		return occationSubPay;
	}
	public void setOccationSubPay(String occationSubPay) {
		this.occationSubPay = occationSubPay;
	}
	public String getRealPayBuy() {
		return realPayBuy;
	}
	public void setRealPayBuy(String realPayBuy) {
		this.realPayBuy = realPayBuy;
	}
	
}
