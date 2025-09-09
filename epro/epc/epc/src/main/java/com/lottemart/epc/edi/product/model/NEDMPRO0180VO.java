package com.lottemart.epc.edi.product.model;

import org.springframework.web.multipart.MultipartFile;

import com.lottemart.epc.edi.comm.model.PagingVO;

public class NEDMPRO0180VO extends PagingVO  {
	
	
	/** 상품명 */
	private String prodNm;
	/** 협력업체코드 */
	private String[] entpCds;
	/** 업체코드 */
	private String entpCd;	
	/** 상품코드 */
	private String prodCd;
	/** 성적서 업로드 날짜 */
	private String uploadDt;
	/** 성적서 만료 날짜 */
	private String expireDt;
	/** 등록한 파일명 */
	private String srcFileNm;
	/** 서버에 등록된 파일명 */
	private String serverFileNm;
	/** 등록한 파일크기 */
	private String fileSize;
	/** 등록 일자 및 시간 */
	private String regDt;
	/** 등록자  */
	private String regId;
	/** 수정 일자 및 시간 */
	private String modDt;
	/** 수정자  */
	private String modId;
	/** 상품유형코드 */
	private String prodType;
	/** 상품유형이름 */
	private String prodTypeName;
	/** 상품유형이름 */
	private String seq;
	private MultipartFile reportPbProdFile;

	
	public String getProdNm() {
		return prodNm;
	}
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}	
	
	public String[] getEntpCds() {
		if (this.entpCds != null) {
			String[] ret = new String[entpCds.length];
			for (int i = 0; i < entpCds.length; i++) { 
				ret[i] = this.entpCds[i]; 
			}
			return ret;
		} else {
			return null;
		}
	}
	public void setEntpCds(String[] entpCds) {
		if (entpCds != null) {
			 this.entpCds = new String[entpCds.length];			 
			 for (int i = 0; i < entpCds.length; ++i) {
				 this.entpCds[i] = entpCds[i];
			 }
		 } else {
			 this.entpCds = null;
		 }
	}
	
	public String getEntpCd() {
		return entpCd;
	}
	public void setEntpCd(String entpCd) {
		this.entpCd = entpCd;
	}
	
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	
	public String getUploadDt() {
		return uploadDt;
	}
	public void setUploadDt(String uploadDt) {
		this.uploadDt = uploadDt;
	}
	
	public String getExpireDt() {
		return expireDt;
	}
	public void setExpireDt(String expireDt) {
		this.expireDt = expireDt;
	}
	
	public String getSrcFileNm() {
		return srcFileNm;
	}
	public void setSrcFileNm(String srcFileNm) {
		this.srcFileNm = srcFileNm;
	}
	
	public String getServerFileNm() {
		return serverFileNm;
	}
	public void setServerFileNm(String serverFileNm) {
		this.serverFileNm = serverFileNm;
	}
	
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	
	public String getModDt() {
		return modDt;
	}
	public void setModDt(String modDt) {
		this.modDt = modDt;
	}
	
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}
	
	public MultipartFile getReportPbProdFile() {
		return reportPbProdFile;
	}

	public void setReportPbProdFile(MultipartFile reportPbProdFile) {
		this.reportPbProdFile = reportPbProdFile;
	}
	
	public String getProdType() {
		return prodType;
	}
	public void setProdType(String prodType) {
		this.prodType = prodType;
	}
	
	public String getProdTypeName() {
		return prodTypeName;
	}
	public void setProdTypeName(String prodTypeName) {
		this.prodTypeName = prodTypeName;
	}
	
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
}
