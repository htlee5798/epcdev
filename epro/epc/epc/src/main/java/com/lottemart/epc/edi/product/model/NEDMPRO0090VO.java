package com.lottemart.epc.edi.product.model;

import com.lottemart.epc.edi.comm.model.PagingVO;

public class NEDMPRO0090VO extends PagingVO  {

	private static final long serialVersionUID = 1L;
	
	private String entpCd;           // 협력업체코드
	private String srchL1Cd;         // 검색할 대분류
	private String srchSellCd;       // 검색할 판매코드
	private String srchCompleteStat; // 검색할 상품 승인 상태
	private String[] venCds;         // 협력업체 코드 배열
	
	private String prodCd;           // 상품코드
	private String srcmkCd;          // 판매코드
	private String l3Cd;             // 소분류
	
	private String chgFg;			 // 속성변경가능구분
	private String seq;  			 // 순번
	private String msg;  			 // 페이지 필요한 메세지
	private String aprvFg; 			 // 승인구분
	
	private String regDt; 			 // 요청일자
	private String regTm; 			 // 요청시간
	
	private String[] grpAttrId;      // 분석속성Id
	private String[] grpAttrValId;   // 분석속성내역
	private String[] grpAttrValNm;   // 분석속성값
	
	private String regId;			//등록아이디
	private String modId;			//수정아이디
	
	public String getEntpCd() {
		return entpCd;
	}
	
	public void setEntpCd(String entpCd) {
		this.entpCd = entpCd;
	}
	
	public String getSrchL1Cd() {
		return srchL1Cd;
	}
	
	public void setSrchL1Cd(String srchL1Cd) {
		this.srchL1Cd = srchL1Cd;
	}
	
	public String getSrchSellCd() {
		return srchSellCd;
	}
	
	public void setSrchSellCd(String srchSellCd) {
		this.srchSellCd = srchSellCd;
	}
	
	public String getSrchCompleteStat() {
		return srchCompleteStat;
	}
	
	public void setSrchCompleteStat(String srchCompleteStat) {
		this.srchCompleteStat = srchCompleteStat;
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

	public String getProdCd() {
		return prodCd;
	}

	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}

	public String getSrcmkCd() {
		return srcmkCd;
	}

	public void setSrcmkCd(String srcmkCd) {
		this.srcmkCd = srcmkCd;
	}

	public String getL3Cd() {
		return l3Cd;
	}

	public void setL3Cd(String l3Cd) {
		this.l3Cd = l3Cd;
	}
	
	public String getChgFg() {
		return chgFg;
	}

	public void setChgFg(String chgFg) {
		this.chgFg = chgFg;
	}
	
	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String getAprvFg() {
		return aprvFg;
	}

	public void setAprvFg(String aprvFg) {
		this.aprvFg = aprvFg;
	}
	
	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	
	public String getRegTm() {
		return regTm;
	}

	public void setRegTm(String regTm) {
		this.regTm = regTm;
	}
	
	public String[] getGrpAttrId() {
		return grpAttrId;
	}
	
	public void setGrpAttrId(String[] grpAttrId) {
		this.grpAttrId = grpAttrId;
	}
	
	public String[] getGrpAttrValId() {
		return grpAttrValId;
	}
	
	public void setGrpAttrValId(String[] grpAttrValId) {
		this.grpAttrValId = grpAttrValId;
	}
	
	public String[] getGrpAttrValNm() {
		return grpAttrValNm;
	}
	
	public void setGrpAttrValNm(String[] grpAttrValNm) {
		this.grpAttrValNm = grpAttrValNm;
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
