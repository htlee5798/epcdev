package com.lottemart.epc.edi.product.model;

import java.io.Serializable;
import java.util.List;

import com.lcnjf.util.DateUtil;

public class NEDMPRO0510VO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 661625462790929921L;
	
	private String reqNo;		//요청번호
	private String venCd;		//파트너사코드
	private String purDept;		//구매조직코드
	private String purDeptNm;	//구매조직명
	private String nbPbGbn;		//NB,PB 구분
	private String nbPbGbnNm;	//NB,PB 구분
	private String prStat;		//진행상태코드
	private String prStatNm;	//진행상태코드명
	private String dcNum;		//공문번호
	private String rtnRsn;		//반려사유
	private String srcmkCd;		//판매코드
	private String chgReqCost;	//변경요청금액
	private String costRsnCd;	//변경사유코드
	private String costRsnCdNm;	//변경사유코드명
	private String costRsnDtlCd; //변경상세사유코드
	private String costRsnDtlCdNm; //변경상세사유코드명
	private String cmt;			//비고
	private String chgReqCostDt; //변경시작일시
	private String delFg;		//삭제구분
	private String prodCd;		//상품코드
	private String prodNm;		//상품명
	private String dispUnit;	//표시단위
	private String seq;			//순번
	private String chgReqDy;	//MD 요청일자
	
	private String orgReqNo;	//원본요청번호
	
	private String orgCost;		//기존원가
	private String incDecRate;	//증감율
	
	private String l1Cd;		//대분류코드
	private String l2Cd;		//중분류코드
	private String l3Cd;		//소분류코드
	private String l1Nm;		//대분류코드명
	private String l2Nm;		//중분류코드명
	private String l3Nm;		//소분류코드명
	private String prodPatFg;	//상품유형코드
	
	private String ifReqDt;		//인터페이스송신일시
	private String ifRcvDt;		//인터페이스수신일시
	
	private String rnum;		//행번호
	private String regId;		//등록아이디
	private String regDate;		//등록일시
	private String modId;		//수정아이디
	private String modDate;		//수정일시
	
	private List<NEDMPRO0510VO> prodDataArr;	//대상상품 list
	
	//검색조건
	private String srchPurDept;				//조회_구매조직
	private String srchVenCd;				//조회_파트너사코드
	private String srchNbPbGbn;				//조회_nb/pb구분
	private String srchSrcmkCd;				//조회_판매코드
	private String srchChgReqCostDt;		//조회_변경시작일시
	private String srchPrStat;				//조회_상태
	
	private String rowStat;					//행 저장상태
	
	private String taxFg;		//면과세구분

	/* ECS */
	private String dcCreGbn;	//공문생성구분
	private String contCode;	//ECS 공문 내부관리번호
	private String dcStat;		//공문진행상태
	private String ecsWrtId;	//ECS 작성자아이디
	private String ecsRecvCompNum;	//ECS 수신처사업자등록번호
	private String ecsRecvCompName;	//ECS 수신처사업자명
	
	private String srchProdPatFg;	//조회_상품유형구분
	private String srchTaxFg;		//조회_면과세구분

	private String ifRtnCode;		//인터페이스 결과코드
	private String ifRtnMsg;		//인터페이스 결과에러메세지
	
	private String freshStdYn;		//신선규격상품구분
	private String[] srchPurDepts;	//조회_구매조직 array
	private String[] purDepts;		//구매조직 array
	private String srchFreshStdYn;	//조회_신선규격상품구분
	private String taxFgNm;			//과세유형명
	
	
	public String getReqNo() {
		return reqNo;
	}
	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
	}
	public String getVenCd() {
		return venCd;
	}
	public void setVenCd(String venCd) {
		this.venCd = venCd;
	}
	public String getPurDept() {
		return purDept;
	}
	public void setPurDept(String purDept) {
		this.purDept = purDept;
	}
	public String getNbPbGbn() {
		return nbPbGbn;
	}
	public void setNbPbGbn(String nbPbGbn) {
		this.nbPbGbn = nbPbGbn;
	}
	public String getPrStat() {
		return prStat;
	}
	public void setPrStat(String prStat) {
		this.prStat = prStat;
	}
	public String getPrStatNm() {
		return prStatNm;
	}
	public void setPrStatNm(String prStatNm) {
		this.prStatNm = prStatNm;
	}
	public String getDcNum() {
		return dcNum;
	}
	public void setDcNum(String dcNum) {
		this.dcNum = dcNum;
	}
	public String getRtnRsn() {
		return rtnRsn;
	}
	public void setRtnRsn(String rtnRsn) {
		this.rtnRsn = rtnRsn;
	}
	public String getSrcmkCd() {
		return srcmkCd;
	}
	public void setSrcmkCd(String srcmkCd) {
		this.srcmkCd = srcmkCd;
	}
	public String getChgReqCost() {
		return chgReqCost;
	}
	public void setChgReqCost(String chgReqCost) {
		this.chgReqCost = chgReqCost;
	}
	public String getCostRsnCd() {
		return costRsnCd;
	}
	public void setCostRsnCd(String costRsnCd) {
		this.costRsnCd = costRsnCd;
	}
	public String getCostRsnDtlCd() {
		return costRsnDtlCd;
	}
	public void setCostRsnDtlCd(String costRsnDtlCd) {
		this.costRsnDtlCd = costRsnDtlCd;
	}
	public String getCmt() {
		return cmt;
	}
	public void setCmt(String cmt) {
		this.cmt = cmt;
	}
	public String getChgReqCostDt() {
		return chgReqCostDt;
	}
	public void setChgReqCostDt(String chgReqCostDt) {
		this.chgReqCostDt = chgReqCostDt;
	}
	public String getChgReqCostDtFmt() {
		if(chgReqCostDt !=null && !"".equals(chgReqCostDt) && DateUtil.isDate(chgReqCostDt, "yyyyMMdd")) {
			return DateUtil.string2String(chgReqCostDt, "yyyyMMdd", DateUtil.DATE_PATTERN);
		}
		return "";
	}
	public String getDelFg() {
		return delFg;
	}
	public void setDelFg(String delFg) {
		this.delFg = delFg;
	}
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	public String getDispUnit() {
		return dispUnit;
	}
	public void setDispUnit(String dispUnit) {
		this.dispUnit = dispUnit;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getChgReqDy() {
		return chgReqDy;
	}
	public void setChgReqDy(String chgReqDy) {
		this.chgReqDy = chgReqDy;
	}
	public String getOrgReqNo() {
		return orgReqNo;
	}
	public void setOrgReqNo(String orgReqNo) {
		this.orgReqNo = orgReqNo;
	}
	public String getOrgCost() {
		return orgCost;
	}
	public void setOrgCost(String orgCost) {
		this.orgCost = orgCost;
	}
	public String getIncDecRate() {
		return incDecRate;
	}
	public void setIncDecRate(String incDecRate) {
		this.incDecRate = incDecRate;
	}
	public String getL1Cd() {
		return l1Cd;
	}
	public void setL1Cd(String l1Cd) {
		this.l1Cd = l1Cd;
	}
	public String getL2Cd() {
		return l2Cd;
	}
	public void setL2Cd(String l2Cd) {
		this.l2Cd = l2Cd;
	}
	public String getL3Cd() {
		return l3Cd;
	}
	public void setL3Cd(String l3Cd) {
		this.l3Cd = l3Cd;
	}
	public String getL1Nm() {
		return l1Nm;
	}
	public void setL1Nm(String l1Nm) {
		this.l1Nm = l1Nm;
	}
	public String getL2Nm() {
		return l2Nm;
	}
	public void setL2Nm(String l2Nm) {
		this.l2Nm = l2Nm;
	}
	public String getL3Nm() {
		return l3Nm;
	}
	public void setL3Nm(String l3Nm) {
		this.l3Nm = l3Nm;
	}
	public String getProdPatFg() {
		return prodPatFg;
	}
	public void setProdPatFg(String prodPatFg) {
		this.prodPatFg = prodPatFg;
	}
	public String getIfReqDt() {
		return ifReqDt;
	}
	public void setIfReqDt(String ifReqDt) {
		this.ifReqDt = ifReqDt;
	}
	public String getIfRcvDt() {
		return ifRcvDt;
	}
	public void setIfRcvDt(String ifRcvDt) {
		this.ifRcvDt = ifRcvDt;
	}
	public String getRnum() {
		return rnum;
	}
	public void setRnum(String rnum) {
		this.rnum = rnum;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}
	public String getModDate() {
		return modDate;
	}
	public void setModDate(String modDate) {
		this.modDate = modDate;
	}
	public List<NEDMPRO0510VO> getProdDataArr() {
		return prodDataArr;
	}
	public void setProdDataArr(List<NEDMPRO0510VO> prodDataArr) {
		this.prodDataArr = prodDataArr;
	}
	public String getSrchPurDept() {
		return srchPurDept;
	}
	public void setSrchPurDept(String srchPurDept) {
		this.srchPurDept = srchPurDept;
	}
	public String getSrchVenCd() {
		return srchVenCd;
	}
	public void setSrchVenCd(String srchVenCd) {
		this.srchVenCd = srchVenCd;
	}
	public String getSrchNbPbGbn() {
		return srchNbPbGbn;
	}
	public void setSrchNbPbGbn(String srchNbPbGbn) {
		this.srchNbPbGbn = srchNbPbGbn;
	}
	public String getSrchSrcmkCd() {
		return srchSrcmkCd;
	}
	public void setSrchSrcmkCd(String srchSrcmkCd) {
		this.srchSrcmkCd = srchSrcmkCd;
	}
	public String getSrchChgReqCostDt() {
		return srchChgReqCostDt;
	}
	public void setSrchChgReqCostDt(String srchChgReqCostDt) {
		this.srchChgReqCostDt = srchChgReqCostDt;
	}
	public String getSrchPrStat() {
		return srchPrStat;
	}
	public void setSrchPrStat(String srchPrStat) {
		this.srchPrStat = srchPrStat;
	}
	public String getRowStat() {
		return rowStat;
	}
	public void setRowStat(String rowStat) {
		this.rowStat = rowStat;
	}
	public String getPurDeptNm() {
		return purDeptNm;
	}
	public void setPurDeptNm(String purDeptNm) {
		this.purDeptNm = purDeptNm;
	}
	public String getNbPbGbnNm() {
		return nbPbGbnNm;
	}
	public void setNbPbGbnNm(String nbPbGbnNm) {
		this.nbPbGbnNm = nbPbGbnNm;
	}
	public String getCostRsnCdNm() {
		return costRsnCdNm;
	}
	public void setCostRsnCdNm(String costRsnCdNm) {
		this.costRsnCdNm = costRsnCdNm;
	}
	public String getCostRsnDtlCdNm() {
		return costRsnDtlCdNm;
	}
	public void setCostRsnDtlCdNm(String costRsnDtlCdNm) {
		this.costRsnDtlCdNm = costRsnDtlCdNm;
	}
	public String getProdNm() {
		return prodNm;
	}
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}
	public String getTaxFg() {
		return taxFg;
	}
	public void setTaxFg(String taxFg) {
		this.taxFg = taxFg;
	}
	public String getDcCreGbn() {
		return dcCreGbn;
	}
	public void setDcCreGbn(String dcCreGbn) {
		this.dcCreGbn = dcCreGbn;
	}
	public String getContCode() {
		return contCode;
	}
	public void setContCode(String contCode) {
		this.contCode = contCode;
	}
	public String getDcStat() {
		return dcStat;
	}
	public void setDcStat(String dcStat) {
		this.dcStat = dcStat;
	}
	public String getEcsWrtId() {
		return ecsWrtId;
	}
	public void setEcsWrtId(String ecsWrtId) {
		this.ecsWrtId = ecsWrtId;
	}
	public String getEcsRecvCompNum() {
		return ecsRecvCompNum;
	}
	public void setEcsRecvCompNum(String ecsRecvCompNum) {
		this.ecsRecvCompNum = ecsRecvCompNum;
	}
	public String getEcsRecvCompName() {
		return ecsRecvCompName;
	}
	public void setEcsRecvCompName(String ecsRecvCompName) {
		this.ecsRecvCompName = ecsRecvCompName;
	}
	public String getSrchProdPatFg() {
		return srchProdPatFg;
	}
	public void setSrchProdPatFg(String srchProdPatFg) {
		this.srchProdPatFg = srchProdPatFg;
	}
	public String getSrchTaxFg() {
		return srchTaxFg;
	}
	public void setSrchTaxFg(String srchTaxFg) {
		this.srchTaxFg = srchTaxFg;
	}
	public String getIfRtnCode() {
		return ifRtnCode;
	}
	public void setIfRtnCode(String ifRtnCode) {
		this.ifRtnCode = ifRtnCode;
	}
	public String getIfRtnMsg() {
		return ifRtnMsg;
	}
	public void setIfRtnMsg(String ifRtnMsg) {
		this.ifRtnMsg = ifRtnMsg;
	}
	public String getFreshStdYn() {
		return freshStdYn;
	}
	public void setFreshStdYn(String freshStdYn) {
		this.freshStdYn = freshStdYn;
	}
	public String[] getSrchPurDepts() {
		return srchPurDepts;
	}
	public void setSrchPurDepts(String[] srchPurDepts) {
		this.srchPurDepts = srchPurDepts;
	}
	public String[] getPurDepts() {
		return purDepts;
	}
	public void setPurDepts(String[] purDepts) {
		this.purDepts = purDepts;
	}
	public String getSrchFreshStdYn() {
		return srchFreshStdYn;
	}
	public void setSrchFreshStdYn(String srchFreshStdYn) {
		this.srchFreshStdYn = srchFreshStdYn;
	}
	public String getTaxFgNm() {
		return taxFgNm;
	}
	public void setTaxFgNm(String taxFgNm) {
		this.taxFgNm = taxFgNm;
	}
	
}
