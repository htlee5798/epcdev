package com.lottemart.epc.edi.srm.model;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public class SRMJON0041VO implements Serializable {

	static final long serialVersionUID = -254238134556327798L;
	/***/
	private String houseCode;
	/***/
	private String sellerCode;

	/** 설립일자 */
	private String foundationDate;
	/** 자본금 */
	private String basicAmt;
	/** 연간 매출액(최근 3년) */
	private String salesAmt;
	/** 종업원 수(정규직) */
	private String empCount;
	/** 공장소유구분 */
	private String plantOwnType;
	/** 공장 운영 형태{scode_type=M792} */
	private String plantRoleType;
	/** 주요거래처 */
	private String mainCustomer;
	/** 롯데마트 旣 진출 채널 */
	private String aboardChannelText;
	/** 롯데마트 旣 진출 국가 */
	private String aboardCountryText;
	/** 상품명 */
	private String productName;
	/** 납품가 */
	private String productPrice;
	/** 통화 */
	private String cur;
	/** 상품이미지 */
	private String productImgPath;
	/** 제품소개서 첨부파일 */
	private String productIntroAttachNo;
	/** 회사소개서 첨부파일 */
	private String companyIntroAttachNo;
	/** 상품정보 */
	private String mainProduct;
	/** 주사용 브랜드 */
	private String dealingBrandProduct;
	/** 사업자등록번호 */
	private String irsNo;
	/***/
	private MultipartFile productImgPathFile;
	/***/
	private MultipartFile productIntroAttachNoFile;
	/***/
	private MultipartFile companyIntroAttachNoFile;
	/***/
	private String productImgPathFileName;
	/***/
	private String productIntroAttachNoFileName;
	/***/
	private String companyIntroAttachNoFileName;
	/***/
	private String url;
	/**순번*/
	private String reqSeq;
	/**상품이미지 원본파일명*/
	private String productImgPathName;
	/**제품소개서 원본파일명*/
	private String productIntroAttachNoName;
	/**회사소개서 원본파일명*/
	private String companyIntroAttachNoName;
	/**파일구분*/
	private String fileGbn;
	/**중소기업여부*/
	private String smFlag;
	/** 중소기업확인증 */
	private String smAttachNo;
	/***/
	private MultipartFile smAttachNoFile;
	/***/
	private String smAttachNoFileName;
	/**중소기업확인증 원본파일명*/
	private String smAttachNoName;

	public String getFoundationDate() {
		return foundationDate;
	}

	public void setFoundationDate(String foundationDate) {
		this.foundationDate = foundationDate;
	}

	public String getBasicAmt() {
		return basicAmt;
	}

	public void setBasicAmt(String basicAmt) {
		this.basicAmt = basicAmt;
	}

	public String getSalesAmt() {
		return salesAmt;
	}

	public void setSalesAmt(String salesAmt) {
		this.salesAmt = salesAmt;
	}

	public String getEmpCount() {
		return empCount;
	}

	public void setEmpCount(String empCount) {
		this.empCount = empCount;
	}

	public String getPlantOwnType() {
		return plantOwnType;
	}

	public void setPlantOwnType(String plantOwnType) {
		this.plantOwnType = plantOwnType;
	}

	public String getPlantRoleType() {
		return plantRoleType;
	}

	public void setPlantRoleType(String plantRoleType) {
		this.plantRoleType = plantRoleType;
	}

	public String getMainCustomer() {
		return mainCustomer;
	}

	public void setMainCustomer(String mainCustomer) {
		this.mainCustomer = mainCustomer;
	}

	public String getAboardChannelText() {
		return aboardChannelText;
	}

	public void setAboardChannelText(String aboardChannelText) {
		this.aboardChannelText = aboardChannelText;
	}

	public String getAboardCountryText() {
		return aboardCountryText;
	}

	public void setAboardCountryText(String aboardCountryText) {
		this.aboardCountryText = aboardCountryText;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}

	public String getCur() {
		return cur;
	}

	public void setCur(String cur) {
		this.cur = cur;
	}

	public String getProductImgPath() {
		return productImgPath;
	}

	public void setProductImgPath(String productImgPath) {
		this.productImgPath = productImgPath;
	}

	public String getProductIntroAttachNo() {
		return productIntroAttachNo;
	}

	public void setProductIntroAttachNo(String productIntroAttachNo) {
		this.productIntroAttachNo = productIntroAttachNo;
	}

	public String getCompanyIntroAttachNo() {
		return companyIntroAttachNo;
	}

	public void setCompanyIntroAttachNo(String companyIntroAttachNo) {
		this.companyIntroAttachNo = companyIntroAttachNo;
	}

	public String getMainProduct() {
		return mainProduct;
	}

	public void setMainProduct(String mainProduct) {
		this.mainProduct = mainProduct;
	}

	public String getDealingBrandProduct() {
		return dealingBrandProduct;
	}

	public void setDealingBrandProduct(String dealingBrandProduct) {
		this.dealingBrandProduct = dealingBrandProduct;
	}

	public String getIrsNo() {
		return irsNo;
	}

	public void setIrsNo(String irsNo) {
		this.irsNo = irsNo;
	}

	public MultipartFile getProductImgPathFile() {
		return productImgPathFile;
	}

	public void setProductImgPathFile(MultipartFile productImgPathFile) {
		this.productImgPathFile = productImgPathFile;
	}

	public MultipartFile getProductIntroAttachNoFile() {
		return productIntroAttachNoFile;
	}

	public void setProductIntroAttachNoFile(MultipartFile productIntroAttachNoFile) {
		this.productIntroAttachNoFile = productIntroAttachNoFile;
	}

	public MultipartFile getCompanyIntroAttachNoFile() {
		return companyIntroAttachNoFile;
	}

	public void setCompanyIntroAttachNoFile(MultipartFile companyIntroAttachNoFile) {
		this.companyIntroAttachNoFile = companyIntroAttachNoFile;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHouseCode() {
		return houseCode;
	}

	public void setHouseCode(String houseCode) {
		this.houseCode = houseCode;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public String getReqSeq() {
		return reqSeq;
	}

	public void setReqSeq(String reqSeq) {
		this.reqSeq = reqSeq;
	}

	public String getProductImgPathFileName() {
		return productImgPathFileName;
	}

	public void setProductImgPathFileName(String productImgPathFileName) {
		this.productImgPathFileName = productImgPathFileName;
	}

	public String getProductIntroAttachNoFileName() {
		return productIntroAttachNoFileName;
	}

	public void setProductIntroAttachNoFileName(String productIntroAttachNoFileName) {
		this.productIntroAttachNoFileName = productIntroAttachNoFileName;
	}

	public String getCompanyIntroAttachNoFileName() {
		return companyIntroAttachNoFileName;
	}

	public void setCompanyIntroAttachNoFileName(String companyIntroAttachNoFileName) {
		this.companyIntroAttachNoFileName = companyIntroAttachNoFileName;
	}

	public String getProductImgPathName() {
		return productImgPathName;
	}

	public void setProductImgPathName(String productImgPathName) {
		this.productImgPathName = productImgPathName;
	}

	public String getProductIntroAttachNoName() {
		return productIntroAttachNoName;
	}

	public void setProductIntroAttachNoName(String productIntroAttachNoName) {
		this.productIntroAttachNoName = productIntroAttachNoName;
	}

	public String getCompanyIntroAttachNoName() {
		return companyIntroAttachNoName;
	}

	public void setCompanyIntroAttachNoName(String companyIntroAttachNoName) {
		this.companyIntroAttachNoName = companyIntroAttachNoName;
	}

	public String getFileGbn() {
		return fileGbn;
	}

	public void setFileGbn(String fileGbn) {
		this.fileGbn = fileGbn;
	}
	
	public String getsmFlag() {
		return smFlag;
	}

	public void setsmFlag(String smFlag) {
		this.smFlag = smFlag;
	}
	
	public String getsmAttachNo() {
		return smAttachNo;
	}

	public void setsmAttachNo(String smAttachNo) {
		this.smAttachNo = smAttachNo;
	}

	public MultipartFile getsmAttachNoFile() {
		return smAttachNoFile;
	}

	public void setsmAttachNoFile(MultipartFile smAttachNoFile) {
		this.smAttachNoFile = smAttachNoFile;
	}
	
	public String getsmAttachNoFileName() {
		return smAttachNoFileName;
	}

	public void setsmAttachNoFileName(String smAttachNoFileName) {
		this.smAttachNoFileName = smAttachNoFileName;
	}

	public String getsmAttachNoName() {
		return smAttachNoName;
	}

	public void setsmAttachNoName(String smAttachNoName) {
		this.smAttachNoName = smAttachNoName;
	}
}
