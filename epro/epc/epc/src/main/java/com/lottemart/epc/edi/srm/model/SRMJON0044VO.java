package com.lottemart.epc.edi.srm.model;

import com.lottemart.epc.common.model.CommonFileVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SRMJON0044VO implements Serializable {

	static final long serialVersionUID = -254238134556327798L;

	/**하우스코드*/
	private String houseCode;
	/**업체코드*/
	private String sellerCode;
	/**채널선택{scode_type=SRM053}*/
	private String channelCode;
	/**순번*/
	private String reqSeq;
	/**업체소싱국가구분[M025]*/
	private String shipperType;
	/**대분류*/
	private String catLv1Code;
	/**대분류*/
	private String catLv1CodeNm;
	/**사업자등록번호*/
	private String irsNo;
	/**임시비밀번호*/
	private String tempPw;
	/**업체명{한글}*/
	private String sellerNameLoc;
	/**BDC 필드 추가필요 - 대표자명*/
	private String sellerCeoName;
	/**업체마트 담당자(이름)*/
	private String vMainName;
	/**등록자 유선 전화번호*/
	private String vPhone1;
	/**등록자 유선 전화번호*/
	private String phoneNo;
	/**모바일(이동전화)*/
	private String vMobilePhone;
	/**BDC EMAIL CHECK 필요*/
	private String vEmail;
	/**FAX*/
	private String faxFhone;
	/**우편번호*/
	private String zipcode;
	/**주소2*/
	private String address1;
	/**국가코드{scode_type=M001}*/
	private String country;
	/**설립일자*/
	private String foundationDate;
	/**자본금*/
	private String basicAmt;
	/**연간 매출액(최근 3년)*/
	private String salesAmt;
	/**종업원 수(정규직)*/
	private String empCount;
	/**공장소유구분*/
	private String plantOwnType;
	/***/
	private String locale;
	/***/
	private String status;

	/** 회사소개서 첨부파일ID*/
	private String companyIntroAttachNo;
	/**첨부파일*/
	ArrayList<MultipartFile> companyIntroAttachNoFile;
	/**첨부문서*/
	private ArrayList docNo;
	/**첨부문서 순번*/
	private ArrayList docSeq;

	private List<CommonFileVO> companyIntroAttachNoFileList;


	/**온라인몰 메일발송 대상*/
	private String onlineMailTarget;
	/**로컬푸드여부*/
	private String localFoodYn;

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getBasicAmt() {
		return basicAmt;
	}

	public void setBasicAmt(String basicAmt) {
		this.basicAmt = basicAmt;
	}

	public String getCatLv1Code() {
		return catLv1Code;
	}

	public void setCatLv1Code(String catLv1Code) {
		this.catLv1Code = catLv1Code;
	}

	public String getCatLv1CodeNm() {
		return catLv1CodeNm;
	}

	public void setCatLv1CodeNm(String catLv1CodeNm) {
		this.catLv1CodeNm = catLv1CodeNm;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmpCount() {
		return empCount;
	}

	public void setEmpCount(String empCount) {
		this.empCount = empCount;
	}

	public String getFaxFhone() {
		return faxFhone;
	}

	public void setFaxFhone(String faxFhone) {
		this.faxFhone = faxFhone;
	}

	public String getFoundationDate() {
		return foundationDate;
	}

	public void setFoundationDate(String foundationDate) {
		this.foundationDate = foundationDate;
	}

	public String getHouseCode() {
		return houseCode;
	}

	public void setHouseCode(String houseCode) {
		this.houseCode = houseCode;
	}

	public String getIrsNo() {
		return irsNo;
	}

	public void setIrsNo(String irsNo) {
		this.irsNo = irsNo;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getPlantOwnType() {
		return plantOwnType;
	}

	public void setPlantOwnType(String plantOwnType) {
		this.plantOwnType = plantOwnType;
	}

	public String getReqSeq() {
		return reqSeq;
	}

	public void setReqSeq(String reqSeq) {
		this.reqSeq = reqSeq;
	}

	public String getSalesAmt() {
		return salesAmt;
	}

	public void setSalesAmt(String salesAmt) {
		this.salesAmt = salesAmt;
	}

	public String getSellerCeoName() {
		return sellerCeoName;
	}

	public void setSellerCeoName(String sellerCeoName) {
		this.sellerCeoName = sellerCeoName;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public String getSellerNameLoc() {
		return sellerNameLoc;
	}

	public void setSellerNameLoc(String sellerNameLoc) {
		this.sellerNameLoc = sellerNameLoc;
	}

	public String getShipperType() {
		return shipperType;
	}

	public void setShipperType(String shipperType) {
		this.shipperType = shipperType;
	}

	public String getTempPw() {
		return tempPw;
	}

	public void setTempPw(String tempPw) {
		this.tempPw = tempPw;
	}

	public String getvEmail() {
		return vEmail;
	}

	public void setvEmail(String vEmail) {
		this.vEmail = vEmail;
	}

	public String getvMainName() {
		return vMainName;
	}

	public void setvMainName(String vMainName) {
		this.vMainName = vMainName;
	}

	public String getvMobilePhone() {
		return vMobilePhone;
	}

	public void setvMobilePhone(String vMobilePhone) {
		this.vMobilePhone = vMobilePhone;
	}

	public String getvPhone1() {
		return vPhone1;
	}

	public void setvPhone1(String vPhone1) {
		this.vPhone1 = vPhone1;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCompanyIntroAttachNo() {
		return companyIntroAttachNo;
	}

	public void setCompanyIntroAttachNo(String companyIntroAttachNo) {
		this.companyIntroAttachNo = companyIntroAttachNo;
	}


	public ArrayList getDocNo() {
		if (this.docNo != null) {
			ArrayList ret = new ArrayList();
			for (int i = 0; i < docNo.size(); i++) {
				ret.add(i, this.docNo.get(i));
			}

			return ret;
		} else {
			return null;
		}
	}

	public void setDocNo(ArrayList docNo) {
		if (docNo != null) {
			this.docNo = new ArrayList();
			for (int i = 0; i < docNo.size();i++) {
				this.docNo.add(i, docNo.get(i));
			}
		} else {
			this.docNo = null;
		}
		this.docNo = docNo;
	}


	public ArrayList getDocSeq() {
		if (this.docSeq != null) {
			ArrayList ret = new ArrayList();
			for (int i = 0; i < docSeq.size(); i++) {
				ret.add(i, this.docSeq.get(i));
			}

			return ret;
		} else {
			return null;
		}
	}

	public void setDocSeq(ArrayList docSeq) {
		if (docSeq != null) {
			this.docSeq = new ArrayList();
			for (int i = 0; i < docSeq.size();i++) {
				this.docSeq.add(i, docSeq.get(i));
			}
		} else {
			this.docSeq = null;
		}
		this.docSeq = docSeq;
	}

	public ArrayList<MultipartFile> getCompanyIntroAttachNoFile() {
		if (this.companyIntroAttachNoFile != null) {
			ArrayList ret = new ArrayList();
			for (int i = 0; i < companyIntroAttachNoFile.size(); i++) {
				ret.add(i, this.companyIntroAttachNoFile.get(i));
			}

			return ret;
		} else {
			return null;
		}
	}

	public void setCompanyIntroAttachNoFile(ArrayList<MultipartFile> companyIntroAttachNoFile) {
		if (companyIntroAttachNoFile != null) {
			this.companyIntroAttachNoFile = new ArrayList();
			for (int i = 0; i < companyIntroAttachNoFile.size();i++) {
				this.companyIntroAttachNoFile.add(i, companyIntroAttachNoFile.get(i));
			}
		} else {
			this.companyIntroAttachNoFile = null;
		}
		this.companyIntroAttachNoFile = companyIntroAttachNoFile;
	}


	public List<CommonFileVO> getCompanyIntroAttachNoFileList() {
		if (this.companyIntroAttachNoFileList != null) {
			ArrayList ret = new ArrayList();
			for (int i = 0; i < companyIntroAttachNoFileList.size(); i++) {
				ret.add(i, this.companyIntroAttachNoFileList.get(i));
			}

			return ret;
		} else {
			return null;
		}
	}

	public void setCompanyIntroAttachNoFileList(List<CommonFileVO> companyIntroAttachNoFileList) {
		if (companyIntroAttachNoFileList != null) {
			this.companyIntroAttachNoFileList = new ArrayList();
			for (int i = 0; i < companyIntroAttachNoFileList.size();i++) {
				this.companyIntroAttachNoFileList.add(i, companyIntroAttachNoFileList.get(i));
			}
		} else {
			this.companyIntroAttachNoFileList = null;
		}
		this.companyIntroAttachNoFileList = companyIntroAttachNoFileList;
	}

	public String getLocalFoodYn() {
		return localFoodYn;
	}

	public void setLocalFoodYn(String localFoodYn) {
		this.localFoodYn = localFoodYn;
	}

	public String getOnlineMailTarget() {
		return onlineMailTarget;
	}

	public void setOnlineMailTarget(String onlineMailTarget) {
		this.onlineMailTarget = onlineMailTarget;
	}
}
