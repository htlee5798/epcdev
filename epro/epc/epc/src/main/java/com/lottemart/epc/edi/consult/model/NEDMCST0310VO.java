package com.lottemart.epc.edi.consult.model;

import java.io.Serializable;

import com.lottemart.epc.edi.comm.model.Constants;

public class NEDMCST0310VO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1389842604426902601L;
	private String vendorCode;
	private String startDate;
	private String endDate;
	private String productConfirmFlag;
	
	
	private String groupCode;
	private String detailCode;
	private String l4Code;
	private String l1Code;
	private String teamCode;
	private String teamCd;
	
	private String teamNm;
	private String mainProd;
	private String cellCntCd;

	private String businessNo;
	private String password;
	private String vendorName;
	
	private String newProductCode;
	
	
	private Integer stopTradeVendorCount;
	
	private String tradeType;
	private String taxDivnCode;
	
	
	private String onlineCategoryName;
	
	private String ceoName;
	private String email;
	
	private String prodArraySeq;
	
	private String catCd;
	private String infoGrpCd;
	private String flag;
	
	private String cityNm;
//	private String guNm;
//	private String streetNm;
	
	private String bmanNo;  		 				// 사업자번호 
	private String hndNm;  		 					// 상호명 
	private String chgStatusCd;  		         	// 변경상태코드 
	private String regDate;  		 	         	// 등록일시 
	private String papeJgmRsltDivnCd;  	    	 	// 서류심사결과구분코드 
	private String papeJgmRetnDivnCd;  	     		// 서류심사반려구분코드 
	private String papeJgmProcContent;           	// 서류심사처리내용 
	private String cnslRsltDivnCd;  	         	// 상담결과구분코드 
	private String cnslRetnDivnCd;  	         	// 상담반려구분코드 
	private String cnslProcContent;  	         	// 상담처리내용 
	private String entshpRsltDivnCd;  	 			// 입점결과구분코드 
	private String entshpRetnDivnCd;  	   			// 입점반려구분코드 
	private String entshpProcContent;  	    	 	// 입점처리내용 
	private String papeJgmProcDy;  		     		// 서류심사처리일자 
	private String cnslProcDy;  		         	// 상담처리일자 
	private String entshpProcDy;  		     		// 입점처리일자 
	
	private String seq;								// 상담신청 분류 SEQ
	private String colVal;							// 답 YES OR NO
	private String dispYn;							
	private String kindCd;							// 상담신청 분류
	
	
	private String papeJgmUtakmnTelNo;				// 서류심사담당자전화번호
	private String cnslUtakmnTelNo;   				// 상담담당자전화번호
	private String entshpUtakmnTelNo; 				// 입점담당자전화번호
	private String lastUtakmn;        				// 최종담당자명
	
	private String vendorBusinessNo;				// businessNo
	private String strMsg;							// strMsg
	private String objFocus;						// objFocus
		
	private String agreeYn;							// 약관 동의여부
	
	
	public String getCeoName() {
		return ceoName;
	}
	public void setCeoName(String ceoName) {
		this.ceoName = ceoName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOnlineCategoryName() {
		return onlineCategoryName;
	}
	public void setOnlineCategoryName(String onlineCategoryName) {
		this.onlineCategoryName = onlineCategoryName;
	}
	public String getTaxDivnCode() {
		return taxDivnCode;
	}
	public void setTaxDivnCode(String taxDivnCode) {
		this.taxDivnCode = taxDivnCode;
	}
	public Integer getStopTradeVendorCount() {
		return stopTradeVendorCount;
	}
	public void setStopTradeVendorCount(Integer stopTradeVendorCount) {
		this.stopTradeVendorCount = stopTradeVendorCount;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getNewProductCode() {
		return newProductCode;
	}
	public void setNewProductCode(String newProductCode) {
		this.newProductCode = newProductCode;
	}
	public String getL1Code() {
		return l1Code;
	}
	public void setL1Code(String l1Code) {
		this.l1Code = l1Code;
	}
	public String getTeamCode() {
		return teamCode;
	}
	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}
	public String getL4Code() {
		return l4Code;
	}
	public void setL4Code(String l4Code) {
		this.l4Code = l4Code;
	}	
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getBusinessNo() {
		return businessNo;
	}
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getDetailCode() {
		return detailCode == null ? Constants.DEFAULT_DETAIL_CD : detailCode;
	}
	public void setDetailCode(String detailCode) {
		this.detailCode = detailCode;
	}
	public String getVendorCode() {
		return vendorCode;
	}
	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getProductConfirmFlag() {
		return productConfirmFlag;
	}
	public void setProductConfirmFlag(String productConfirmFlag) {
		this.productConfirmFlag = productConfirmFlag;
	}
	public String getProdArraySeq() {
		return prodArraySeq;
	}
	public void setProdArraySeq(String prodArraySeq) {
		this.prodArraySeq = prodArraySeq;
	}
	public String getCatCd() {
		return catCd;
	}
	public void setCatCd(String catCd) {
		this.catCd = catCd;
	}
	public String getInfoGrpCd() {
		return infoGrpCd;
	}
	public void setInfoGrpCd(String infoGrpCd) {
		this.infoGrpCd = infoGrpCd;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getCityNm() {
		return cityNm;
	}
	public void setCityNm(String cityNm) {
		this.cityNm = cityNm;
	}
	public String getBmanNo() {
		return bmanNo;
	}
	public void setBmanNo(String bmanNo) {
		this.bmanNo = bmanNo;
	}
	public String getHndNm() {
		return hndNm;
	}
	public void setHndNm(String hndNm) {
		this.hndNm = hndNm;
	}
	public String getChgStatusCd() {
		return chgStatusCd;
	}
	public void setChgStatusCd(String chgStatusCd) {
		this.chgStatusCd = chgStatusCd;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getPapeJgmRsltDivnCd() {
		return papeJgmRsltDivnCd;
	}
	public void setPapeJgmRsltDivnCd(String papeJgmRsltDivnCd) {
		this.papeJgmRsltDivnCd = papeJgmRsltDivnCd;
	}
	public String getPapeJgmRetnDivnCd() {
		return papeJgmRetnDivnCd;
	}
	public void setPapeJgmRetnDivnCd(String papeJgmRetnDivnCd) {
		this.papeJgmRetnDivnCd = papeJgmRetnDivnCd;
	}
	public String getPapeJgmProcContent() {
		return papeJgmProcContent;
	}
	public void setPapeJgmProcContent(String papeJgmProcContent) {
		this.papeJgmProcContent = papeJgmProcContent;
	}
	public String getCnslRetnDivnCd() {
		return cnslRetnDivnCd;
	}
	public void setCnslRetnDivnCd(String cnslRetnDivnCd) {
		this.cnslRetnDivnCd = cnslRetnDivnCd;
	}
	public String getCnslProcContent() {
		return cnslProcContent;
	}
	public void setCnslProcContent(String cnslProcContent) {
		this.cnslProcContent = cnslProcContent;
	}
	public String getEntshpRsltDivnCd() {
		return entshpRsltDivnCd;
	}
	public void setEntshpRsltDivnCd(String entshpRsltDivnCd) {
		this.entshpRsltDivnCd = entshpRsltDivnCd;
	}
	public String getEntshpRetnDivnCd() {
		return entshpRetnDivnCd;
	}
	public void setEntshpRetnDivnCd(String entshpRetnDivnCd) {
		this.entshpRetnDivnCd = entshpRetnDivnCd;
	}
	public String getEntshpProcContent() {
		return entshpProcContent;
	}
	public void setEntshpProcContent(String entshpProcContent) {
		this.entshpProcContent = entshpProcContent;
	}
	public String getPapeJgmProcDy() {
		return papeJgmProcDy;
	}
	public void setPapeJgmProcDy(String papeJgmProcDy) {
		this.papeJgmProcDy = papeJgmProcDy;
	}
	public String getCnslProcDy() {
		return cnslProcDy;
	}
	public void setCnslProcDy(String cnslProcDy) {
		this.cnslProcDy = cnslProcDy;
	}
	public String getEntshpProcDy() {
		return entshpProcDy;
	}
	public void setEntshpProcDy(String entshpProcDy) {
		this.entshpProcDy = entshpProcDy;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getCnslRsltDivnCd() {
		return cnslRsltDivnCd;
	}
	public void setCnslRsltDivnCd(String cnslRsltDivnCd) {
		this.cnslRsltDivnCd = cnslRsltDivnCd;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getColVal() {
		return colVal;
	}
	public void setColVal(String colVal) {
		this.colVal = colVal;
	}
	public String getDispYn() {
		return dispYn;
	}
	public void setDispYn(String dispYn) {
		this.dispYn = dispYn;
	}
	public String getKindCd() {
		return kindCd;
	}
	public void setKindCd(String kindCd) {
		this.kindCd = kindCd;
	}
	public String getPapeJgmUtakmnTelNo() {
		return papeJgmUtakmnTelNo;
	}
	public void setPapeJgmUtakmnTelNo(String papeJgmUtakmnTelNo) {
		this.papeJgmUtakmnTelNo = papeJgmUtakmnTelNo;
	}
	public String getCnslUtakmnTelNo() {
		return cnslUtakmnTelNo;
	}
	public void setCnslUtakmnTelNo(String cnslUtakmnTelNo) {
		this.cnslUtakmnTelNo = cnslUtakmnTelNo;
	}
	public String getEntshpUtakmnTelNo() {
		return entshpUtakmnTelNo;
	}
	public void setEntshpUtakmnTelNo(String entshpUtakmnTelNo) {
		this.entshpUtakmnTelNo = entshpUtakmnTelNo;
	}
	public String getLastUtakmn() {
		return lastUtakmn;
	}
	public void setLastUtakmn(String lastUtakmn) {
		this.lastUtakmn = lastUtakmn;
	}
	public String getVendorBusinessNo() {
		return vendorBusinessNo;
	}
	public void setVendorBusinessNo(String vendorBusinessNo) {
		this.vendorBusinessNo = vendorBusinessNo;
	}
	public String getStrMsg() {
		return strMsg;
	}
	public void setStrMsg(String strMsg) {
		this.strMsg = strMsg;
	}
	public String getObjFocus() {
		return objFocus;
	}
	public void setObjFocus(String objFocus) {
		this.objFocus = objFocus;
	}
	public String getTeamCd() {
		return teamCd;
	}
	public void setTeamCd(String teamCd) {
		this.teamCd = teamCd;
	}
	public String getTeamNm() {
		return teamNm;
	}
	public void setTeamNm(String teamNm) {
		this.teamNm = teamNm;
	}
	public String getMainProd() {
		return mainProd;
	}
	public void setMainProd(String mainProd) {
		this.mainProd = mainProd;
	}
	public String getCellCntCd() {
		return cellCntCd;
	}
	public void setCellCntCd(String cellCntCd) {
		this.cellCntCd = cellCntCd;
	}
	public String getAgreeYn() {
		return agreeYn;
	}
	public void setAgreeYn(String agreeYn) {
		this.agreeYn = agreeYn;
	}	
}
