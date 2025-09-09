package com.lottemart.epc.edi.srm.model;

import java.io.Serializable;
import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

import com.lottemart.epc.edi.comm.model.PagingVO;

public class SRMVEN0020VO extends PagingVO implements Serializable {
	
	private static final long serialVersionUID = -9182078691197569401L;
	
	/*-------------조회조건-------------*/
	/** 협력업체코드 */
	private String srchVenCd;
	/** 인증대상 */
	private String srchCertiTarget;
	/** 인증명 */
	private String srchCertiName;
	/*--------------------------------*/
	
	/** 순번 */
	private int rnum;
	/** Locale */
	private String locale;
	/** 하우스코드 */
	private String houseCode;
	/** 구분 */
	private String certiType;
	/** 구분이름 */
	private String certiTypeNm;
	/** 인증번호 */
	private String certiNo;
	/** 인증명 */
	private String certiName;
	/** 고시일 */
	private String certiDate;
	/** 고시번호 */
	private String certiAnnNo;
	/** 비고 */
	private String remark;
	/** 생성일자 */
	private String addDate;
	/** 생성자 */
	private String addUserId;
	/** 변경일자 */
	private String changeDate;
	/** 변경자 */
	private String changeUserId;
	/** 삭제여부 */
	private String delFlag;
	/** 인증대상 */
	private String certiTarget;
	/** 인증대상 이름 */
	private String certiTargetNm;
	/** 사업자번호 */
	private String vendorCode;
	/** 협력업체코드 */
	private String venCd;
	/** 순번 */
	private String seq;
	/** 인증기관명 */
	private String certiOrgan;
	/** 인증기준일자 */
	private String certiStdDate;
	/** 인증종료일자 */
	private String certiEndDate;
	/** 상품코드 */
	private String prodCd;
	/** 상품명 */
	private String prodCdName;
	/** 첨부파일 원본이름 */
	private String srcFileName;
	/** 첨부파일 이름 */
	private String certiAttachNo;
	/** 첨부파일 */
	private MultipartFile certiAttachNoFile;
	/** 인증정보 삭제 List */
	private ArrayList delList;
	
	public String getSrchVenCd() {
		return srchVenCd;
	}
	public void setSrchVenCd(String srchVenCd) {
		this.srchVenCd = srchVenCd;
	}
	public String getSrchCertiTarget() {
		return srchCertiTarget;
	}
	public void setSrchCertiTarget(String srchCertiTarget) {
		this.srchCertiTarget = srchCertiTarget;
	}
	public String getSrchCertiName() {
		return srchCertiName;
	}
	public void setSrchCertiName(String srchCertiName) {
		this.srchCertiName = srchCertiName;
	}
	public int getRnum() {
		return rnum;
	}
	public void setRnum(int rnum) {
		this.rnum = rnum;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public String getHouseCode() {
		return houseCode;
	}
	public void setHouseCode(String houseCode) {
		this.houseCode = houseCode;
	}
	public String getCertiType() {
		return certiType;
	}
	public void setCertiType(String certiType) {
		this.certiType = certiType;
	}
	public String getCertiTypeNm() {
		return certiTypeNm;
	}
	public void setCertiTypeNm(String certiTypeNm) {
		this.certiTypeNm = certiTypeNm;
	}
	public String getCertiNo() {
		return certiNo;
	}
	public void setCertiNo(String certiNo) {
		this.certiNo = certiNo;
	}
	public String getCertiName() {
		return certiName;
	}
	public void setCertiName(String certiName) {
		this.certiName = certiName;
	}
	public String getCertiDate() {
		return certiDate;
	}
	public void setCertiDate(String certiDate) {
		this.certiDate = certiDate;
	}
	public String getCertiAnnNo() {
		return certiAnnNo;
	}
	public void setCertiAnnNo(String certiAnnNo) {
		this.certiAnnNo = certiAnnNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAddDate() {
		return addDate;
	}
	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}
	public String getAddUserId() {
		return addUserId;
	}
	public void setAddUserId(String addUserId) {
		this.addUserId = addUserId;
	}
	public String getChangeDate() {
		return changeDate;
	}
	public void setChangeDate(String changeDate) {
		this.changeDate = changeDate;
	}
	public String getChangeUserId() {
		return changeUserId;
	}
	public void setChangeUserId(String changeUserId) {
		this.changeUserId = changeUserId;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getCertiTarget() {
		return certiTarget;
	}
	public void setCertiTarget(String certiTarget) {
		this.certiTarget = certiTarget;
	}
	public String getCertiTargetNm() {
		return certiTargetNm;
	}
	public void setCertiTargetNm(String certiTargetNm) {
		this.certiTargetNm = certiTargetNm;
	}
	public String getVendorCode() {
		return vendorCode;
	}
	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}
	public String getVenCd() {
		return venCd;
	}
	public void setVenCd(String venCd) {
		this.venCd = venCd;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getCertiOrgan() {
		return certiOrgan;
	}
	public void setCertiOrgan(String certiOrgan) {
		this.certiOrgan = certiOrgan;
	}
	public String getCertiStdDate() {
		return certiStdDate;
	}
	public void setCertiStdDate(String certiStdDate) {
		this.certiStdDate = certiStdDate;
	}
	public String getCertiEndDate() {
		return certiEndDate;
	}
	public void setCertiEndDate(String certiEndDate) {
		this.certiEndDate = certiEndDate;
	}
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	public String getProdCdName() {
		return prodCdName;
	}
	public void setProdCdName(String prodCdName) {
		this.prodCdName = prodCdName;
	}
	public String getSrcFileName() {
		return srcFileName;
	}
	public void setSrcFileName(String srcFileName) {
		this.srcFileName = srcFileName;
	}
	public String getCertiAttachNo() {
		return certiAttachNo;
	}
	public void setCertiAttachNo(String certiAttachNo) {
		this.certiAttachNo = certiAttachNo;
	}
	public MultipartFile getCertiAttachNoFile() {
		return certiAttachNoFile;
	}
	public void setCertiAttachNoFile(MultipartFile certiAttachNoFile) {
		this.certiAttachNoFile = certiAttachNoFile;
	}
	public ArrayList getDelList() {
		if (this.delList != null) {
			ArrayList ret = new ArrayList();
			for (int i = 0; i < delList.size(); i++) {
				ret.add(i, this.delList.get(i));
			}
			return ret;
		} else {
			return null;
		}
	}
	public void setDelList(ArrayList delList) {
		if (delList != null) {
			this.delList = new ArrayList();
			for (int i = 0; i < delList.size();i++) {
				this.delList.add(i, delList.get(i));
			}
		} else {
			this.delList = null;
		}
	}

}
