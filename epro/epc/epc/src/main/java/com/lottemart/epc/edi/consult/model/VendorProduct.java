package com.lottemart.epc.edi.consult.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class VendorProduct implements Serializable {

	//table column mapping field
	private String businessNo;
	private Integer seq;
	private String sellCode;
	private String productName;
	private String productCost;
	private String productSalePrice;
	private String monthlyAverage;
	private String attachFileCode;
	private String atchFileFolder;  //20150810 이동빈추가 
	




	private String productDescription;
	private Date regDate;
	private MultipartFile attachFile;
	private String attachFileName;
	
	
	//logic mapping column
	private MultipartFile attachFile1;
	private MultipartFile attachFile2;
	private MultipartFile attachFile3;
	
	private String attachFileName1;
	private String attachFileName2;
	private String attachFileName3;
	                
	
	private String uploadFile1;
	private String uploadFile2;
	private String uploadFile3;
	private String vendorFile;
	
	private String uploadfolder1; //20150811 이동빈 추가
	private String uploadfolder2; //20150811 이동빈 추가
	private String uploadfolder3; //20150811 이동빈 추가
	
	private String[] attachSeq;
	private String[] AttachProductName;
	
	
	

	public String getUploadfolder1() {
		return uploadfolder1;
	}

	public void setUploadfolder1(String uploadfolder1) {
		this.uploadfolder1 = uploadfolder1;
	}

	public String getUploadfolder2() {
		return uploadfolder2;
	}

	public void setUploadfolder2(String uploadfolder2) {
		this.uploadfolder2 = uploadfolder2;
	}

	public String getUploadfolder3() {
		return uploadfolder3;
	}

	public void setUploadfolder3(String uploadfolder3) {
		this.uploadfolder3 = uploadfolder3;
	}
	
	
	
	
	private MultipartFile vendorAttachFile;
	private String vendorAttachFileName;
	
	private String content;
	
	
	
	public String getAtchFileFolder() {
		return atchFileFolder;
	}

	public void setAtchFileFolder(String atchFileFolder) {
		this.atchFileFolder = atchFileFolder;
	}
	
	
	public String getUploadFile1() {
		return uploadFile1;
	}

	public void setUploadFile1(String uploadFile1) {
		this.uploadFile1 = uploadFile1;
	}

	public String getUploadFile2() {
		return uploadFile2;
	}

	public void setUploadFile2(String uploadFile2) {
		this.uploadFile2 = uploadFile2;
	}

	public String getUploadFile3() {
		return uploadFile3;
	}

	public void setUploadFile3(String uploadFile3) {
		this.uploadFile3 = uploadFile3;
	}

	public String getVendorFile() {
		return vendorFile;
	}

	public void setVendorFile(String vendorFile) {
		this.vendorFile = vendorFile;
	}

	public MultipartFile getVendorAttachFile() {
		return vendorAttachFile;
	}

	public void setVendorAttachFile(MultipartFile vendorAttachFile) {
		this.vendorAttachFile = vendorAttachFile;
	}

	public String getVendorAttachFileName() {
		return vendorAttachFileName;
	}

	public void setVendorAttachFileName(String vendorAttachFileName) {
		this.vendorAttachFileName = vendorAttachFileName;
	}

	public String getAttachFileName() {
		return attachFileName;
	}

	public void setAttachFileName(String attachFileName) {
		this.attachFileName = attachFileName;
	}

	public MultipartFile getAttachFile() {
		return attachFile;
	}

	public void setAttachFile(MultipartFile attachFile) {
		this.attachFile = attachFile;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAttachFileName1() {
		return attachFileName1;
	}

	public void setAttachFileName1(String attachFileName1) {
		this.attachFileName1 = attachFileName1;
	}

	public String getAttachFileName2() {
		return attachFileName2;
	}

	public void setAttachFileName2(String attachFileName2) {
		this.attachFileName2 = attachFileName2;
	}

	public String getAttachFileName3() {
		return attachFileName3;
	}

	public void setAttachFileName3(String attachFileName3) {
		this.attachFileName3 = attachFileName3;
	}

	public MultipartFile getAttachFile1() {
		return attachFile1;
	}

	public void setAttachFile1(MultipartFile attachFile1) {
		this.attachFile1 = attachFile1;
	}

	public MultipartFile getAttachFile2() {
		return attachFile2;
	}

	public void setAttachFile2(MultipartFile attachFile2) {
		this.attachFile2 = attachFile2;
	}

	public MultipartFile getAttachFile3() {
		return attachFile3;
	}

	public void setAttachFile3(MultipartFile attachFile3) {
		this.attachFile3 = attachFile3;
	}

	public VendorProduct() {}

	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getSellCode() {
		return sellCode;
	}

	public void setSellCode(String sellCode) {
		this.sellCode = sellCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCost() {
		return productCost;
	}

	public void setProductCost(String productCost) {
		this.productCost = productCost;
	}

	public String getProductSalePrice() {
		return productSalePrice;
	}

	public void setProductSalePrice(String productSalePrice) {
		this.productSalePrice = productSalePrice;
	}

	public String getMonthlyAverage() {
		return monthlyAverage;
	}

	public void setMonthlyAverage(String monthlyAverage) {
		this.monthlyAverage = monthlyAverage;
	}

	public String getAttachFileCode() {
		return attachFileCode;
	}

	public void setAttachFileCode(String attachFileCode) {
		this.attachFileCode = attachFileCode;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String[] getAttachSeq() {
		return attachSeq;
	}

	public void setAttachSeq(String[] attachSeq) {
		this.attachSeq = attachSeq;
	}

	public String[] getAttachProductName() {
		return AttachProductName;
	}

	public void setAttachProductName(String[] attachProductName) {
		AttachProductName = attachProductName;
	}
	
	
	
	
}
