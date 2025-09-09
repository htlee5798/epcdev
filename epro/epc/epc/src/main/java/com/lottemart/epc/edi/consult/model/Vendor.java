package com.lottemart.epc.edi.consult.model;

import java.io.Serializable;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class Vendor implements Serializable{

	private String bmanNo;
	private String passwd;
	private String hndNm;
	private String corpnDivnCd;
	private String corpnNm;

	private String ceoNm;
	private String zipNo;
	private String zipNo1;
	private String zipNo2;
	private String regDivnCd;
	private String chgStatusCd;
	private String bmanKindCd;

	private String businessKind;
	private String businessType;
	private String supplyAddr1;
	private String supplyAddr2;
	private String utakmanNm;

	//private String officeTel;
	private String telNo;
	private String handphone;
	private String email;
	private String email1;
	private String email2;
	private String homePageAddress;
	private String fax;

	private String orgCd;
	//private String dutyPersonTel;
	private String repTelNo; // 대표번호
	private String corpnRsdtNo;
	private String affYn; //계열사 여부

	private Integer yearSaleAmount;
	private Integer monthSaleAmount;
	private String foundationYear;
	private String factoryType;
	private String capitalAmount;
	private String mainProduct;
	private String companyDescription;
	private String companyCharCode;

	private String manuFacture;
	private String directImport;
	private String wholeSaler;
	private String vedor;
	private String productPlace;
	private String lease;
	private String lotteAffTradeCode;
	private String lotteAffTradeContent;
	private String pecuSubjectContent;
	private String attachFileCode;
	private String atchFileKindCd;
	private String l1Cd;//대분류선택코드
	private String teamCd;//팀코드

//	private String corpnRsdtNo1;
//	private String corpnRsdtNo2;
//	private String corpNm1;
//	private String corpNm2;


	private String tel1;
	private String tel2;
	private String tel3;
	private String fax1;
	private String fax2;
	private String fax3;


	private String cell1;
	private String cell2;
	private String cell3;
	private String officetel1;
	private String officetel2;
	private String officetel3;



	private Date regDate;
	private Date modDate;

	private String l1Nm;
	private String teamNm;

	private String smIndustryEntpNm;
	private String smIndustryEntpProdNm;
	private String smIndustryEntpAmt;


	/*입점상담 개편 관련 VO추가 - 서용욱 */
	private String seq;
	private String answer;
	private String colVal;
	private String dispYn;
	private String pepeJgmRsltDivnCd;


	private String wishStore;
	private String asisMystorecnt;
	private String tSubteamCd;

	private String uploadFolder; //20150811 이동빈 추가

	private MultipartFile kindAttachFile1;
	private String kindFile1;
	private MultipartFile kindAttachFile2;
	private String kindFile2;
	private MultipartFile kindAttachFile3;
	private String kindFile3;
	private MultipartFile kindAttachFile4;
	private String kindFile4;

	private String agreeYn;




	public String getUploadFolder() {
		return uploadFolder;
	}
	public void setUploadFolder(String uploadFolder) {
		this.uploadFolder = uploadFolder;
	}
	public String gettSubteamCd() {
		return tSubteamCd;
	}
	public void settSubteamCd(String tSubteamCd) {
		this.tSubteamCd = tSubteamCd;
	}

	public String getAsisMystorecnt() {
		return asisMystorecnt;
	}
	public void setAsisMystorecnt(String asisMystorecnt) {
		this.asisMystorecnt = asisMystorecnt;
	}
	public String getPepeJgmRsltDivnCd() {
		return pepeJgmRsltDivnCd;
	}
	public void setPepeJgmRsltDivnCd(String pepeJgmRsltDivnCd) {
		this.pepeJgmRsltDivnCd = pepeJgmRsltDivnCd;
	}
	private String queryDesc;
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getQueryDesc() {
		return queryDesc;
	}
	public void setQueryDesc(String queryDesc) {
		this.queryDesc = queryDesc;
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
	private String kindCd;
	//20150714 입점개편 이동빈
	public String getKindCd() {
		return kindCd;
	}
	public void setKindCd(String kindCd) {
		this.kindCd = kindCd;
	}

	public String getWishStore() {
		return wishStore;
	}
	public void setWishStore(String wishStore) {
		this.wishStore = wishStore;
	}

	public Date getRegDate() {
		return regDate;
	}




	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}




	public Date getModDate() {
		return modDate;
	}




	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}




	public String getAttachFileCode() {
		return attachFileCode;
	}




	public void setAttachFileCode(String attachFileCode) {
		this.attachFileCode = attachFileCode;
	}




	public String getPecuSubjectContent() {
		return pecuSubjectContent;
	}




	public void setPecuSubjectContent(String pecuSubjectContent) {
		this.pecuSubjectContent = pecuSubjectContent;
	}




	public String getLotteAffTradeCode() {
		return lotteAffTradeCode;
	}




	public void setLotteAffTradeCode(String lotteAffTradeCode) {
		this.lotteAffTradeCode = lotteAffTradeCode;
	}




	public String getLotteAffTradeContent() {
		return lotteAffTradeContent;
	}




	public void setLotteAffTradeContent(String lotteAffTradeContent) {
		this.lotteAffTradeContent = lotteAffTradeContent;
	}




	public String getCompanyCharCode() {
		return companyCharCode;
	}




	public void setCompanyCharCode(String companyCharCode) {
		this.companyCharCode = companyCharCode;
	}




	public Integer getYearSaleAmount() {
		return yearSaleAmount;
	}




	public void setYearSaleAmount(Integer yearSaleAmount) {
		this.yearSaleAmount = yearSaleAmount;
	}




	public Integer getMonthSaleAmount() {
		return monthSaleAmount;
	}




	public void setMonthSaleAmount(Integer monthSaleAmount) {
		this.monthSaleAmount = monthSaleAmount;
	}




	public String getFoundationYear() {
		return foundationYear;
	}




	public void setFoundationYear(String foundationYear) {
		this.foundationYear = foundationYear;
	}




	public String getFactoryType() {
		return factoryType;
	}




	public void setFactoryType(String factoryType) {
		this.factoryType = factoryType;
	}




	public String getCapitalAmount() {
		return capitalAmount;
	}




	public void setCapitalAmount(String capitalAmount) {
		this.capitalAmount = capitalAmount;
	}




	public String getMainProduct() {
		return mainProduct;
	}




	public void setMainProduct(String mainProduct) {
		this.mainProduct = mainProduct;
	}




	public String getCompanyDescription() {
		return companyDescription;
	}




	public void setCompanyDescription(String companyDescription) {
		this.companyDescription = companyDescription;
	}




	public String getManuFacture() {
		return manuFacture;

	}




	public String getDirectImport() {
		return directImport;
	}




	public String getWholeSaler() {
		return wholeSaler;
	}




	public String getVedor() {
		return vedor;
	}




	public String getProductPlace() {
		return productPlace;
	}




	public String getLease() {
		return lease;
	}




	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}




	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}




	public void setTel3(String tel3) {
		this.tel3 = tel3;
	}




	public void setFax1(String fax1) {
		this.fax1 = fax1;
	}




	public void setFax2(String fax2) {
		this.fax2 = fax2;
	}




	public void setFax3(String fax3) {
		this.fax3 = fax3;
	}




	public void setCell1(String cell1) {
		this.cell1 = cell1;
	}




	public void setCell2(String cell2) {
		this.cell2 = cell2;
	}




	public void setCell3(String cell3) {
		this.cell3 = cell3;
	}




	public void setOfficetel1(String officetel1) {
		this.officetel1 = officetel1;
	}




	public void setOfficetel2(String officetel2) {
		this.officetel2 = officetel2;
	}




	public void setOfficetel3(String officetel3) {
		this.officetel3 = officetel3;
	}




	public String getAffYn() {
		return affYn;
	}




    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((affYn == null) ? 0 : affYn.hashCode());
		result = prime * result
				+ ((bmanKindCd == null) ? 0 : bmanKindCd.hashCode());
		result = prime * result + ((bmanNo == null) ? 0 : bmanNo.hashCode());
		result = prime * result
				+ ((businessKind == null) ? 0 : businessKind.hashCode());
		result = prime * result
				+ ((businessType == null) ? 0 : businessType.hashCode());
		result = prime * result + ((ceoNm == null) ? 0 : ceoNm.hashCode());
		result = prime * result
				+ ((chgStatusCd == null) ? 0 : chgStatusCd.hashCode());
		result = prime * result
				+ ((corpnDivnCd == null) ? 0 : corpnDivnCd.hashCode());
		result = prime * result + ((corpnNm == null) ? 0 : corpnNm.hashCode());
		result = prime * result
				+ ((corpnRsdtNo == null) ? 0 : corpnRsdtNo.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((fax == null) ? 0 : fax.hashCode());
		result = prime * result
				+ ((handphone == null) ? 0 : handphone.hashCode());
		result = prime * result + ((hndNm == null) ? 0 : hndNm.hashCode());
		result = prime * result
				+ ((homePageAddress == null) ? 0 : homePageAddress.hashCode());
		result = prime * result + ((orgCd == null) ? 0 : orgCd.hashCode());
		result = prime * result + ((l1Cd == null) ? 0 : l1Cd.hashCode());
		result = prime * result + ((passwd == null) ? 0 : passwd.hashCode());
		result = prime * result
				+ ((regDivnCd == null) ? 0 : regDivnCd.hashCode());
		result = prime * result
				+ ((repTelNo == null) ? 0 : repTelNo.hashCode());
		result = prime * result
				+ ((supplyAddr1 == null) ? 0 : supplyAddr1.hashCode());
		result = prime * result
				+ ((supplyAddr2 == null) ? 0 : supplyAddr2.hashCode());
		result = prime * result + ((telNo == null) ? 0 : telNo.hashCode());
		result = prime * result
				+ ((utakmanNm == null) ? 0 : utakmanNm.hashCode());
		result = prime * result + ((zipNo == null) ? 0 : zipNo.hashCode());
		return result;
	}




	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vendor other = (Vendor) obj;
		if (affYn == null) {
			if (other.affYn != null)
				return false;
		} else if (!affYn.equals(other.affYn))
			return false;
		if (bmanKindCd == null) {
			if (other.bmanKindCd != null)
				return false;
		} else if (!bmanKindCd.equals(other.bmanKindCd))
			return false;
		if (bmanNo == null) {
			if (other.bmanNo != null)
				return false;
		} else if (!bmanNo.equals(other.bmanNo))
			return false;
		if (businessKind == null) {
			if (other.businessKind != null)
				return false;
		} else if (!businessKind.equals(other.businessKind))
			return false;
		if (businessType == null) {
			if (other.businessType != null)
				return false;
		} else if (!businessType.equals(other.businessType))
			return false;
		if (ceoNm == null) {
			if (other.ceoNm != null)
				return false;
		} else if (!ceoNm.equals(other.ceoNm))
			return false;
		if (chgStatusCd == null) {
			if (other.chgStatusCd != null)
				return false;
		} else if (!chgStatusCd.equals(other.chgStatusCd))
			return false;
		if (corpnDivnCd == null) {
			if (other.corpnDivnCd != null)
				return false;
		} else if (!corpnDivnCd.equals(other.corpnDivnCd))
			return false;
		if (corpnNm == null) {
			if (other.corpnNm != null)
				return false;
		} else if (!corpnNm.equals(other.corpnNm))
			return false;
		if (corpnRsdtNo == null) {
			if (other.corpnRsdtNo != null)
				return false;
		} else if (!corpnRsdtNo.equals(other.corpnRsdtNo))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (fax == null) {
			if (other.fax != null)
				return false;
		} else if (!fax.equals(other.fax))
			return false;
		if (handphone == null) {
			if (other.handphone != null)
				return false;
		} else if (!handphone.equals(other.handphone))
			return false;
		if (hndNm == null) {
			if (other.hndNm != null)
				return false;
		} else if (!hndNm.equals(other.hndNm))
			return false;
		if (homePageAddress == null) {
			if (other.homePageAddress != null)
				return false;
		} else if (!homePageAddress.equals(other.homePageAddress))
			return false;
		if (orgCd == null) {
			if (other.orgCd != null)
				return false;
		} else if (!orgCd.equals(other.orgCd))
			return false;
		if (l1Cd == null) {
			if (other.l1Cd != null)
				return false;
		} else if (!l1Cd.equals(other.l1Cd))
			return false;

		if (passwd == null) {
			if (other.passwd != null)
				return false;
		} else if (!passwd.equals(other.passwd))
			return false;
		if (regDivnCd == null) {
			if (other.regDivnCd != null)
				return false;
		} else if (!regDivnCd.equals(other.regDivnCd))
			return false;
		if (repTelNo == null) {
			if (other.repTelNo != null)
				return false;
		} else if (!repTelNo.equals(other.repTelNo))
			return false;
		if (supplyAddr1 == null) {
			if (other.supplyAddr1 != null)
				return false;
		} else if (!supplyAddr1.equals(other.supplyAddr1))
			return false;
		if (supplyAddr2 == null) {
			if (other.supplyAddr2 != null)
				return false;
		} else if (!supplyAddr2.equals(other.supplyAddr2))
			return false;
		if (telNo == null) {
			if (other.telNo != null)
				return false;
		} else if (!telNo.equals(other.telNo))
			return false;
		if (utakmanNm == null) {
			if (other.utakmanNm != null)
				return false;
		} else if (!utakmanNm.equals(other.utakmanNm))
			return false;
		if (zipNo == null) {
			if (other.zipNo != null)
				return false;
		} else if (!zipNo.equals(other.zipNo))
			return false;
		return true;
	}




	@Override
	public String toString() {
		return "Vendor [bmanNo=" + bmanNo + ", passwd=" + passwd + ", hndNm="
				+ hndNm + ", corpnDivnCd=" + corpnDivnCd + ", corpnNm="
				+ corpnNm + ", ceoNm=" + ceoNm + ", zipNo=" + zipNo
				+ ", regDivnCd=" + regDivnCd + ", chgStatusCd=" + chgStatusCd
				+ ", bmanKindCd=" + bmanKindCd + ", businessKind="
				+ businessKind + ", businessType=" + businessType
				+ ", supplyAddr1=" + supplyAddr1 + ", supplyAddr2="
				+ supplyAddr2 + ", utakmanNm=" + utakmanNm + ", telNo=" + telNo
				+ ", handphone=" + handphone + ", email=" + email
				+ ", homePageAddress=" + homePageAddress + ", fax=" + fax
				+ ", orgCd=" + orgCd + ", repTelNo=" + repTelNo
				+ ", l1Cd=" + l1Cd + ", corpnRsdtNo=" + corpnRsdtNo + ", affYn=" + affYn
				+ ", kindCd=" + kindCd + ", wishStore=" + wishStore + ", asisMystorecnt=" + asisMystorecnt  + ", tSubteamCd=" + tSubteamCd
				+ "]";
	}




	public void mappingField() {


		/*
    	String telNo 	= tel1+" "+tel2+tel3;
    	String repTelNo = officetel1+" "+officetel2+
    		officetel3;
    	String cellPhone = cell1+" "+cell2+cell3;
    	String fax = fax1+" "+fax2+fax3;
    	*/
    	String corpnRsdtNo = getCorpnRsdtNo1()+getCorpnRsdtNo2();

    	/*
    	setTelNo(telNo);
    	setRepTelNo(repTelNo);
    	setHandphone(cellPhone);
    	setFax(fax);
    	*/
    	setCorpnRsdtNo(corpnRsdtNo);
    }


	public void setAffYn(String affYn) {
		this.affYn = affYn;
	}
	Pattern spacePattern =Pattern.compile("[\\s]+");

	public Vendor() {}







	public String getUtakmanNm() {
		return utakmanNm;
	}







	public void setUtakmanNm(String utakmanNm) {
		this.utakmanNm = utakmanNm;
	}







	public String getTelNo() {
		return telNo;
	}







	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}







	public String getRepTelNo() {
		return repTelNo;
	}







	public void setRepTelNo(String repTelNo) {
		this.repTelNo = repTelNo;
	}







	public String getChgStatusCd() {
		return chgStatusCd;
	}







	public void setChgStatusCd(String chgStatusCd) {
		this.chgStatusCd = chgStatusCd;
	}







	public String getBmanKindCd() {
		return bmanKindCd;
	}







	public void setBmanKindCd(String bmanKindCd) {
		this.bmanKindCd = bmanKindCd;
	}







	public String getHndNm() {
		return hndNm;
	}







	public void setHndNm(String hndNm) {
		this.hndNm = hndNm;
	}







	public String getCorpnDivnCd() {
		return corpnDivnCd;
	}







	public void setCorpnDivnCd(String corpnDivnCd) {
		this.corpnDivnCd = corpnDivnCd;
	}







	public String getCorpnNm() {
		return corpnNm;
	}







	public void setCorpnNm(String corpnNm) {
		this.corpnNm = corpnNm;
	}







	public String getCeoNm() {
		return ceoNm;
	}







	public void setCeoNm(String ceoNm) {
		this.ceoNm = ceoNm;
	}







	public String getZipNo() {
		return zipNo;
	}







	public void setZipNo(String zipNo) {
		this.zipNo = zipNo;
	}







	public String getRegDivnCd() {
		return regDivnCd;
	}







	public void setRegDivnCd(String regDivnCd) {
		this.regDivnCd = regDivnCd;
	}







	public String getCorpnRsdtNo1() {
		return getCorpnRsdtNo() == null ? "" : getCorpnRsdtNo().substring(0, 6);
	}

	public String getCorpnRsdtNo2() {
		return getCorpnRsdtNo() == null ? "" :  getCorpnRsdtNo().substring(6, 13);
	}

	public String getCorpnRsdtNo() {
		return corpnRsdtNo;
	}

	public void setCorpnRsdtNo(String corpnRsdtNo) {
		this.corpnRsdtNo = corpnRsdtNo;
	}

	public String getTel1() {
	//	return getTelNo() == null ? "" : spacePattern.split(getTelNo())[0];
		return tel1;
	}

	public String getTel2() {
		/*
		if(getTelNo() != null) {
			String otherTel = spacePattern.split(getTelNo())[1];
			if(otherTel.length() == 8) {
				return otherTel == null ? "" : otherTel.substring(0, 4);
			} else {
				return otherTel == null ? "" : otherTel.substring(0, 3);
			}
		} else {
			return "";
		}
		*/
		return tel2;
	}

	public String getTel3() {
		/*
		if(getTelNo() != null) {
			String otherTel = spacePattern.split(getTelNo())[1];
		return otherTel == null ? "" : otherTel.substring(4, 8);
		} else {
			return "";
		}
		*/
		return tel3;
	}

	public String getFax1() {
		//return getFax() == null ? "" : spacePattern.split(getFax())[0];
		return fax1;
	}

	public String getFax2() {
		/*
		if(getFax() != null) {
			String otherFax = spacePattern.split(getFax())[1];
			if(otherFax.length() == 8) {
				return  otherFax == null ? "" :  otherFax.substring(0, 4);
			} else {
				return  otherFax == null ? "" : otherFax.substring(0, 3);
			}
		} else {
			return "";
		}
		*/
		return fax2;
	}

	public String getFax3() {

		/*
		if( getFax() != null) {
			String otherFax= spacePattern.split(getFax())[1];
			return otherFax == null ? "" : otherFax.substring(4, 8);
		} else {
			return "";
		}
		*/
		return fax3;

	}

	public String getCell1() {
	//	return getHandphone() == null ? "" : spacePattern.split(getHandphone())[0];
		return cell1;
	}

	public String getCell2() {
		/*
		if(getHandphone() != null) {
			String otherTel = spacePattern.split(getHandphone())[1];
			if(otherTel.length() == 8) {
				return otherTel == null ? "" : otherTel.substring(0, 4);
			} else {
				return otherTel == null ? "" : otherTel.substring(0, 3);
			}
		} else {
			return "";
		}*/
		return cell2;
	}

	public String getCell3() {
		/*
		if( getHandphone() != null) {
			String otherTel = spacePattern.split(getHandphone())[1];
			return otherTel == null ? "" : otherTel.substring(4, 8);
		} else {
			return "";
		}
		*/
		return cell3;
	}

	public String getOfficetel1() {

		return officetel1;
		//return getRepTelNo() == null ? "" : spacePattern.split(getRepTelNo())[0];
	}

	public String getOfficetel2() {
		/*
		if( getRepTelNo() != null ) {
			String otherTel = spacePattern.split(getRepTelNo())[1];
			if(otherTel.length() == 8) {
				return otherTel == null ? "" : otherTel.substring(0, 4);
			} else {
				return  otherTel == null ? "" : otherTel.substring(0, 3);
			}
		} else {
			return "";
		}
		*/
		return officetel2;
	}

	public String getOfficetel3() {
		/*
		if( getRepTelNo() != null ) {
			String otherTel = spacePattern.split(getRepTelNo())[1];
			return otherTel == null ? "" : otherTel.substring(4, 8);
		} else {
			return "";
		}
		*/
		return officetel3;
	}

	public String getCorpNm1() {
		return getCorpnNm() == null ? "" : getCorpnNm().substring(0, 6);
	}

	public String getCorpNm2() {

		return getCorpnNm() == null ? "" : getCorpnNm().substring(6, 13);
	}

	/**
	public String getDutyPersonTel() {
		return dutyPersonTel;
	}
	public void setDutyPersonTel(String dutyPersonTel) {
		this.dutyPersonTel = dutyPersonTel;
	}
	**/
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getOrgCd() {
		return orgCd;
	}
	public void setOrgCd(String orgCd) {
		this.orgCd = orgCd;
	}
	public String getBusinessKind() {
		return businessKind;
	}
	public void setBusinessKind(String businessKind) {
		this.businessKind = businessKind;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getSupplyAddr1() {
		return supplyAddr1;
	}
	public void setSupplyAddr1(String supplyAddr1) {
		this.supplyAddr1 = supplyAddr1;
	}
	public String getSupplyAddr2() {
		return supplyAddr2;
	}
	public void setSupplyAddr2(String supplyAddr2) {
		this.supplyAddr2 = supplyAddr2;
	}
	/**
	public String getDutyName() {
		return dutyName;
	}
	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}
	public String getOfficeTel() {
		return officeTel;
	}
	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
	}
	**/
	public String getHandphone() {
		return handphone;
	}
	public void setHandphone(String handphone) {
		this.handphone = handphone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getHomePageAddress() {
		return homePageAddress;
	}
	public void setHomePageAddress(String homePageAddress) {
		this.homePageAddress = homePageAddress;
	}
	public String getBmanNo() {
		return bmanNo;
	}
	public void setBmanNo(String bmanNo) {
		this.bmanNo = bmanNo;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}




	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}


	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}



	public String getZipNo1() {
		return zipNo1;
	}




	public void setZipNo1(String zipNo1) {
		this.zipNo1 = zipNo1;
	}




	public String getZipNo2() {
		return zipNo2;
	}




	public void setZipNo2(String zipNo2) {
		this.zipNo2 = zipNo2;
	}




	public String getL1Cd() {
		return l1Cd;
	}




	public void setL1Cd(String l1Cd) {
		this.l1Cd = l1Cd;
	}




	public String getTeamCd() {
		return teamCd;
	}




	public void setTeamCd(String teamCd) {
		this.teamCd = teamCd;
	}




	public String getL1Nm() {
		return l1Nm;
	}




	public void setL1Nm(String l1Nm) {
		this.l1Nm = l1Nm;
	}




	public String getTeamNm() {
		return teamNm;
	}




	public void setTeamNm(String teamNm) {
		this.teamNm = teamNm;
	}




	public String getSmIndustryEntpNm() {
		return smIndustryEntpNm;
	}




	public void setSmIndustryEntpNm(String smIndustryEntpNm) {
		this.smIndustryEntpNm = smIndustryEntpNm;
	}




	public String getSmIndustryEntpProdNm() {
		return smIndustryEntpProdNm;
	}




	public void setSmIndustryEntpProdNm(String smIndustryEntpProdNm) {
		this.smIndustryEntpProdNm = smIndustryEntpProdNm;
	}




	public String getSmIndustryEntpAmt() {
		return smIndustryEntpAmt;
	}




	public void setSmIndustryEntpAmt(String smIndustryEntpAmt) {
		this.smIndustryEntpAmt = smIndustryEntpAmt;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public MultipartFile getKindAttachFile1() {
		return kindAttachFile1;
	}
	public void setKindAttachFile1(MultipartFile kindAttachFile1) {
		this.kindAttachFile1 = kindAttachFile1;
	}
	public String getKindFile1() {
		return kindFile1;
	}
	public void setKindFile1(String kindFile1) {
		this.kindFile1 = kindFile1;
	}
	public MultipartFile getKindAttachFile2() {
		return kindAttachFile2;
	}
	public void setKindAttachFile2(MultipartFile kindAttachFile2) {
		this.kindAttachFile2 = kindAttachFile2;
	}
	public String getKindFile2() {
		return kindFile2;
	}
	public void setKindFile2(String kindFile2) {
		this.kindFile2 = kindFile2;
	}
	public MultipartFile getKindAttachFile3() {
		return kindAttachFile3;
	}
	public void setKindAttachFile3(MultipartFile kindAttachFile3) {
		this.kindAttachFile3 = kindAttachFile3;
	}
	public String getKindFile3() {
		return kindFile3;
	}
	public void setKindFile3(String kindFile3) {
		this.kindFile3 = kindFile3;
	}
	public MultipartFile getKindAttachFile4() {
		return kindAttachFile4;
	}
	public void setKindAttachFile4(MultipartFile kindAttachFile4) {
		this.kindAttachFile4 = kindAttachFile4;
	}
	public String getKindFile4() {
		return kindFile4;
	}
	public void setKindFile4(String kindFile4) {
		this.kindFile4 = kindFile4;
	}

	public String getAtchFileKindCd() {
		return atchFileKindCd;
	}
	public void setAtchFileKindCd(String atchFileKindCd) {
		this.atchFileKindCd = atchFileKindCd;
	}
	public String getAgreeYn() {
		return agreeYn;
	}
	public void setAgreeYn(String agreeYn) {
		this.agreeYn = agreeYn;
	}






}
