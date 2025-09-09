package com.lottemart.epc.edi.product.model;

import java.io.Serializable;

import com.lottemart.epc.edi.comm.model.PagingVO;

public class NEDMPRO0130VO extends PagingVO implements Serializable {

	static final long serialVersionUID = 1776508153203875384L;

	/** PROXY명 */
	private String proxyNm;

	/** 협력업체 */
	private String srchEntpCode;
	/** 상품구분 */
	private String srchProdDivnCode;
	/** 이미지 상태 */
	private String srchProdStatus;
	/** 등록기간(From) */
	private String srchStartDate;
	/** 등록기간(To) */
	private String srchEndDate;
	/** 판매(88)코드 */
	private String srchSellCode;

	/** 판매코드 */
	private String srcmkCd;
	/** 상품명 */
	private String prodNm;
	/** 이미지명 */
	private String imgNm;
	/** 확정구분 */
	private String cfmFg;
	/** 이미지순번 */
	private String imgSeq;
	/** 확정사유구분 */
	private String cfmReasonFg;
	/** 적용일자 */
	private String applyDy;
	/** 가로 */
	private String width;
	/** 세로길이 */
	private String length;
	/** 높이 */
	private String height;
	/** 가로(원본) */
	private String regWidth;
	/** 세로(원본) */
	private String regLength;
	/** 높이(원본) */
	private String regHeight;
	/** 협력업체코드 */
	private String venCd;
	/** 색상코드 */
	private String colorCd;
	/** 사이즈분류코드 */
	private String szCatCd;
	/** 사이즈코드 */
	private String szCd;
	/** 사이즈분류명 */
	private String szCatNm;
	/** 색상명 */
	private String colorNm;
	/** 사이즈명 */
	private String szNm;
	/** VARIANT */
	private String variant;

	/** 이미지사이즈 가로 */
	private String prodWidth;
	/** 이미지사이즈 세로 */
	private String prodLength;
	/** 이미지사이즈 높이 */
	private String prodHeight;
	/** 사이즈 단위 */
	private String sizeUnit;

	/** 협력업체 전체 검색 조건을 위해 추가 */
	private String[] venCds;			//협력업체코드 리스트

	/** TO-BE에서는 규격,패션에 상관없이 속성이 등록될수 있기 때문에 속성 명칭 추가 */
	private String varAttNm;

	/** 상품코드 */
	private String prodCd;

	/** 순번 */
	private String rnum;

	/** 상품이미지 아이디 */
	private String prodImgId;

	/** 이미지 반려사유 수기입력값 */
	private String cfmReasonTxt;

	/** 총중량 */
	private String wg;
	/** 순중량 */
	private String pclWg;
	/** 순중량 단위 */
	private String pclWgUnit;
	/** 요청 총중량 */
	private String reqGWgt;
	/** 요청 순중량 */
	private String reqNWgt;
	/** 요청 중량단위 */
	private String reqWgtUnit;
	/* 중량 순번 */
	private String wgtSeq;
	
	
	//작업자정보
	private String regId;		//등록아이디
	private String modId;		//수정아이디


	public String getProxyNm() {
		return proxyNm;
	}
	public void setProxyNm(String proxyNm) {
		this.proxyNm = proxyNm;
	}
	public String getSrchEntpCode() {
		return srchEntpCode;
	}
	public void setSrchEntpCode(String srchEntpCode) {
		this.srchEntpCode = srchEntpCode;
	}
	public String getSrchProdDivnCode() {
		return srchProdDivnCode;
	}
	public void setSrchProdDivnCode(String srchProdDivnCode) {
		this.srchProdDivnCode = srchProdDivnCode;
	}
	public String getSrchProdStatus() {
		return srchProdStatus;
	}
	public void setSrchProdStatus(String srchProdStatus) {
		this.srchProdStatus = srchProdStatus;
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
	public String getSrchSellCode() {
		return srchSellCode;
	}
	public void setSrchSellCode(String srchSellCode) {
		this.srchSellCode = srchSellCode;
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
	public String getImgNm() {
		return imgNm;
	}
	public void setImgNm(String imgNm) {
		this.imgNm = imgNm;
	}
	public String getCfmFg() {
		return cfmFg;
	}
	public void setCfmFg(String cfmFg) {
		this.cfmFg = cfmFg;
	}
	public String getImgSeq() {
		return imgSeq;
	}
	public void setImgSeq(String imgSeq) {
		this.imgSeq = imgSeq;
	}
	public String getCfmReasonFg() {
		return cfmReasonFg;
	}
	public void setCfmReasonFg(String cfmReasonFg) {
		this.cfmReasonFg = cfmReasonFg;
	}
	public String getApplyDy() {
		return applyDy;
	}
	public void setApplyDy(String applyDy) {
		this.applyDy = applyDy;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getRegWidth() {
		return regWidth;
	}
	public void setRegWidth(String regWidth) {
		this.regWidth = regWidth;
	}
	public String getRegLength() {
		return regLength;
	}
	public void setRegLength(String regLength) {
		this.regLength = regLength;
	}
	public String getRegHeight() {
		return regHeight;
	}
	public void setRegHeight(String regHeight) {
		this.regHeight = regHeight;
	}
	public String getVenCd() {
		return venCd;
	}
	public void setVenCd(String venCd) {
		this.venCd = venCd;
	}
	public String getColorCd() {
		return colorCd;
	}
	public void setColorCd(String colorCd) {
		this.colorCd = colorCd;
	}
	public String getSzCatCd() {
		return szCatCd;
	}
	public void setSzCatCd(String szCatCd) {
		this.szCatCd = szCatCd;
	}
	public String getSzCd() {
		return szCd;
	}
	public void setSzCd(String szCd) {
		this.szCd = szCd;
	}
	public String getSzCatNm() {
		return szCatNm;
	}
	public void setSzCatNm(String szCatNm) {
		this.szCatNm = szCatNm;
	}
	public String getColorNm() {
		return colorNm;
	}
	public void setColorNm(String colorNm) {
		this.colorNm = colorNm;
	}
	public String getSzNm() {
		return szNm;
	}
	public void setSzNm(String szNm) {
		this.szNm = szNm;
	}
	public String getVariant() {
		return variant;
	}
	public void setVariant(String variant) {
		this.variant = variant;
	}
	public String getProdWidth() {
		return prodWidth;
	}
	public void setProdWidth(String prodWidth) {
		this.prodWidth = prodWidth;
	}
	public String getProdLength() {
		return prodLength;
	}
	public void setProdLength(String prodLength) {
		this.prodLength = prodLength;
	}
	public String getProdHeight() {
		return prodHeight;
	}
	public void setProdHeight(String prodHeight) {
		this.prodHeight = prodHeight;
	}
	public String getSizeUnit() {
		return sizeUnit;
	}
	public void setSizeUnit(String sizeUnit) {
		this.sizeUnit = sizeUnit;
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
	public String getVarAttNm() {
		return varAttNm;
	}
	public void setVarAttNm(String varAttNm) {
		this.varAttNm = varAttNm;
	}
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	public String getRnum() {
		return rnum;
	}
	public void setRnum(String rnum) {
		this.rnum = rnum;
	}
	public String getProdImgId() {
		return prodImgId;
	}
	public void setProdImgId(String prodImgId) {
		this.prodImgId = prodImgId;
	}
	public String getCfmReasonTxt() {
		return cfmReasonTxt;
	}
	public void setCfmReasonTxt(String cfmReasonTxt) {
		this.cfmReasonTxt = cfmReasonTxt;
	}

	public String getWg() {
		return wg;
	}
	public void setWg(String wg) {
		this.wg = wg;
	}
	public String getPclWg() {
		return pclWg;
	}
	public void setPclWg(String pclWg) {
		this.pclWg = pclWg;

	}
	public String getPclWgUnit() {
		return pclWgUnit;
	}
	public void setPclWgUnit(String pclWgUnit) {
		this.pclWgUnit = pclWgUnit;
	}


	public String getReqGWgt() {
		return reqGWgt;
	}

	public void setReqGWgt(String reqGWgt) {
		this.reqGWgt = reqGWgt;
	}

	public String getReqNWgt() {
		return reqNWgt;
	}

	public void setReqNWgt(String reqNWgt) {
		this.reqNWgt = reqNWgt;
	}

	public String getReqWgtUnit() {
		return reqWgtUnit;
	}

	public void setReqWgtUnit(String reqWgtUnit) {
		this.reqWgtUnit = reqWgtUnit;
	}

	public String getWgtSeq() {
		return wgtSeq;
	}

	public void setWgtSeq(String wgtSeq) {
		this.wgtSeq = wgtSeq;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}
	
	
}
