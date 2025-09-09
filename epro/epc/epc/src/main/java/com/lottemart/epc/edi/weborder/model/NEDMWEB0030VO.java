package com.lottemart.epc.edi.weborder.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @Class Name : NEDMWEB0030VO
 * @Description : 발주일괄등록 등록 VO Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      		수정자          		수정내용
 *  -        -
 * 2015.12.07	O YEUN KWON	  최초생성
 * 
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */

public class NEDMWEB0030VO extends PagingVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6715413512908899432L;
	
	private ArrayList<NEDMWEB0030VO> tedPoOrdPackList;
	
	private String regId;			    // 등록자아이디 
	private String modId;			    // 수정자아이디 
	private String regDt;				// 등록일시 
	private String modDt;				// 수정일시
	private String packDivnCd; 			// 파일구분코드		
	private String prodCd; 				// 상품코드			
	private String ordDy; 				// 발주일자			
	private String strCd; 				// 첨포코드			
	private String strNm; 				// 점포명				
	private String ordReqNo; 			// 발주등록번호		
	private String packFileNm; 			// 일괄파일등록명		
	private String srcmkCd; 			// 판매코드			
	private String prodNm; 				// 상품명				
	private String prodStd; 			// 상품규격			
	private String ordIpsu; 			// 발주입수			
	private String ordUnit; 			// 발주단위			
	private String ordQty; 				// 발주수량			
	private String ordBuyPrc; 			// 발주원가			
	private String ordTotQtySum; 		// 발주총수량			
	private String ordTotPrcSum; 		// 발주금액			
	private String regStsCd; 			// 등록상태코드		
	private String regStsNm; 			// 등록상태명			
	private String fileGrpCd; 			// 파일그룹코드		
 	private String strCnt; 				// 발주총수량합계			
 	private String prodCnt; 			// 발주총수량합계			
	private String ordTotAllQtySum;		// 충수량EA합계			
	private String venCd; 				// 협력업체코드	
	private String entpCd; 				// 협력업체코드	
	private String vendorWebOrdFrDt;		// 협력업체 웹발주 가능 시작 시간
	private String vendorWebOrdToDt;		// 협력업체 웹발주 가능 끝 시간
	private String regStsfg;			// 등록상태  [1.전체, 2.정상, 3.오류, 4.미전송,오류]
	private String mdModFg;				// 조정승인 [1.전체, 2.조정, 3.삭제]
	private String uploadGb;			// 업로드 상태 [1.전체, 2.정상, 3.오류]
	private String areaCd;				// 권역코드

	public ArrayList<NEDMWEB0030VO> getTedPoOrdPackList() {
		if (this.tedPoOrdPackList != null) {
			ArrayList<NEDMWEB0030VO> ret = new ArrayList<NEDMWEB0030VO>();
			for (int i = 0; i < tedPoOrdPackList.size(); i++) {
				ret.add(i, this.tedPoOrdPackList.get(i));
			}
			
			return ret;
		} else {
			return null;
		}
	}

	public void setTedPoOrdPackList(ArrayList<NEDMWEB0030VO> tedPoOrdPackList) {
		if (tedPoOrdPackList != null) {
			this.tedPoOrdPackList = new ArrayList<NEDMWEB0030VO>();
			for (int i = 0; i < tedPoOrdPackList.size();i++) {
				this.tedPoOrdPackList.add(i,tedPoOrdPackList.get(i));
			}
		} else {
			this.tedPoOrdPackList = null;
		}
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

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getModDt() {
		return modDt;
	}

	public void setModDt(String modDt) {
		this.modDt = modDt;
	}

	public String getPackDivnCd() {
		return packDivnCd;
	}

	public void setPackDivnCd(String packDivnCd) {
		this.packDivnCd = packDivnCd;
	}

	public String getProdCd() {
		return prodCd;
	}

	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}

	public String getOrdDy() {
		return ordDy;
	}

	public void setOrdDy(String ordDy) {
		this.ordDy = ordDy;
	}

	public String getStrCd() {
		return strCd;
	}

	public void setStrCd(String strCd) {
		this.strCd = strCd;
	}

	public String getStrNm() {
		return strNm;
	}

	public void setStrNm(String strNm) {
		this.strNm = strNm;
	}

	public String getOrdReqNo() {
		return ordReqNo;
	}

	public void setOrdReqNo(String ordReqNo) {
		this.ordReqNo = ordReqNo;
	}

	public String getPackFileNm() {
		return packFileNm;
	}

	public void setPackFileNm(String packFileNm) {
		this.packFileNm = packFileNm;
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

	public String getOrdQty() {
		return ordQty;
	}

	public void setOrdQty(String ordQty) {
		this.ordQty = ordQty;
	}

	public String getOrdBuyPrc() {
		return ordBuyPrc;
	}

	public void setOrdBuyPrc(String ordBuyPrc) {
		this.ordBuyPrc = ordBuyPrc;
	}

	public String getOrdTotQtySum() {
		return ordTotQtySum;
	}

	public void setOrdTotQtySum(String ordTotQtySum) {
		this.ordTotQtySum = ordTotQtySum;
	}

	public String getOrdTotPrcSum() {
		return ordTotPrcSum;
	}

	public void setOrdTotPrcSum(String ordTotPrcSum) {
		this.ordTotPrcSum = ordTotPrcSum;
	}

	public String getRegStsCd() {
		return regStsCd;
	}

	public void setRegStsCd(String regStsCd) {
		this.regStsCd = regStsCd;
	}

	public String getRegStsNm() {
		return regStsNm;
	}

	public void setRegStsNm(String regStsNm) {
		this.regStsNm = regStsNm;
	}

	public String getFileGrpCd() {
		return fileGrpCd;
	}

	public void setFileGrpCd(String fileGrpCd) {
		this.fileGrpCd = fileGrpCd;
	}

	public String getStrCnt() {
		return strCnt;
	}

	public void setStrCnt(String strCnt) {
		this.strCnt = strCnt;
	}

	public String getProdCnt() {
		return prodCnt;
	}

	public void setProdCnt(String prodCnt) {
		this.prodCnt = prodCnt;
	}

	public String getOrdTotAllQtySum() {
		return ordTotAllQtySum;
	}

	public void setOrdTotAllQtySum(String ordTotAllQtySum) {
		this.ordTotAllQtySum = ordTotAllQtySum;
	}

	public String getVenCd() {
		return venCd;
	}

	public void setVenCd(String venCd) {
		this.venCd = venCd;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getVendorWebOrdFrDt() {
		return vendorWebOrdFrDt;
	}

	public void setVendorWebOrdFrDt(String vendorWebOrdFrDt) {
		this.vendorWebOrdFrDt = vendorWebOrdFrDt;
	}

	public String getVendorWebOrdToDt() {
		return vendorWebOrdToDt;
	}

	public void setVendorWebOrdToDt(String vendorWebOrdToDt) {
		this.vendorWebOrdToDt = vendorWebOrdToDt;
	}

	public String getRegStsfg() {
		return regStsfg;
	}

	public void setRegStsfg(String regStsfg) {
		this.regStsfg = regStsfg;
	}

	public String getMdModFg() {
		return mdModFg;
	}

	public void setMdModFg(String mdModFg) {
		this.mdModFg = mdModFg;
	}

	public String getUploadGb() {
		return uploadGb;
	}

	public void setUploadGb(String uploadGb) {
		this.uploadGb = uploadGb;
	}

	public String getAreaCd() {
		return areaCd;
	}

	public void setAreaCd(String areaCd) {
		this.areaCd = areaCd;
	}

	public String getEntpCd() {
		return entpCd;
	}

	public void setEntpCd(String entpCd) {
		this.entpCd = entpCd;
	}
	
}
