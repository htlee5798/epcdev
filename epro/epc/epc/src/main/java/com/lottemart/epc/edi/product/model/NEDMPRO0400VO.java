package com.lottemart.epc.edi.product.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.lottemart.epc.edi.comm.model.PagingVO;

public class NEDMPRO0400VO extends PagingVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4271398217436589114L;
	
	/** 협력업체 */
	private String srchEntpCd;
	
	/**팀코드*/
	private String srchTeamCd;
	
	/**대분류코드*/
	private String srchL1Cd;
	
	/**상품명*/
	private String srchProdNm;
	
	/**진행상태코드*/
	private String srchStatus;
	
	/**조회 시작날짜*/
	private String srchFromDt;
	
	/**조회 종료날짜*/
	private String srchEndDt;
	
	/**순번*/
	private String rnum;
	
	/**진행상태*/
	private String prdtStatus;
	
	/**파트너사코드*/
	private String venCd;
	
	/**팀*/
	private String teamCd;
	
	/**대분류명*/
	private String l1Cd;
	
	/**상품명*/
	private String prodShortNm;
	
	/**규격*/
	private String prodStandardNm;
	
	/**원가*/
	private int norProdPcost;
	
	/**판매가*/
	private int prodSellPrc;
	
	/**이익율*/
	private double prftRate;
	
	/**일반/시즌구분*/
	private String norSesnCd;
	
	/**루트*/
	private String prdtRt;
	
	/**입수*/
	private int inrcntQty;
	
	/**납품가능일*/
	private String delvPsbtDd;
	
	/**유사(타겟)상품 판매코드*/
	private String sellCd;
	
	/**판매채널*/
	private String sellChanCd;
	
	/**상온/냉장/냉동 구분*/
	private String frzCd;
	
	/**목표매출(月)*/
	private String trgtSaleCurr;
	
	/**상품특성(소구포인트)*/
	private String prdtSpec;
	
	/**등록일자*/
	private String regDt;
	
	/**이미지 아이디*/
	private String imgId;
	
	/**시퀀스*/
	private int seq;
	
	/**입점제안번호*/
	private String propRegNo;
	
	/** 요청 유무*/
	private String prdtReqYn;
	
	/** 삭제 여부*/
	private String delYn;
	
	/** 인터페이스 등록일시*/
	private String ifReqDt;
	
	/** 인터페이스 수신일시*/
	private String ifRcvDt;
	
	/**등록자*/
	private String regId;
	
	/** 수정자*/
	private String modId;
	
	/** 수정시간*/
	private String modDt;
	
	private String chkPropStore;
	
	/**RFC CALL NAME*/
	private String proxyNm;
	
	/* RFC 콜 요청구분 */
	private String gbn;
	
	private String orgImgId;

	/**상품특성 가격대*/
	private String prdtSpecRange;
	
	/**상품특성 고객 연령*/
	private String prdtSpecAge;
	
	/**상품특성 고객성별*/
	private String prdtSpecSex;
	
	/**통화구분 키 */
	private String waers;
	

	//----------------------- 이미지관리 --------------
	/**원본 파일 명*/
	private String orgFileNm;
	
	/**저장 파일 명*/
	private String saveFileNm;
	
	/**파일크기*/
	private int fileSize;
	
	/**파일경로*/
	private String filePath;
	
	/**이미지 등록 유무*/
	private String imgRegYn;
	
	private String imgfileUrl;
	
	private String prodImgInput;
	
	private String fileNameLink;

	private String[] venCds;
	
	private List<Map<String,Object>> selBoxChanCds;
	
	private List<NEDMPRO0400VO> prodArr;	//대상요청 list
	
	//파일 SFTP 전송정보
	private String sendYn;			//전송여부
	private String sendDate;		//전송일시
	private String sendPath;		//전송경로
	
	public String getWaers() {
		return waers;
	}

	public void setWaers(String waers) {
		this.waers = waers;
	}

	public String getPrdtSpecRange() {
		return prdtSpecRange;
	}

	public void setPrdtSpecRange(String prdtSpecRange) {
		this.prdtSpecRange = prdtSpecRange;
	}

	public String getPrdtSpecAge() {
		return prdtSpecAge;
	}

	public void setPrdtSpecAge(String prdtSpecAge) {
		this.prdtSpecAge = prdtSpecAge;
	}

	public String getPrdtSpecSex() {
		return prdtSpecSex;
	}

	public void setPrdtSpecSex(String prdtSpecSex) {
		this.prdtSpecSex = prdtSpecSex;
	}

	public String getOrgImgId() {
		return orgImgId;
	}

	public void setOrgImgId(String orgImgId) {
		this.orgImgId = orgImgId;
	}

	public String getGbn() {
		return gbn;
	}

	public void setGbn(String gbn) {
		this.gbn = gbn;
	}
	
	public String getProxyNm() {
		return proxyNm;
	}

	public void setProxyNm(String proxyNm) {
		this.proxyNm = proxyNm;
	}

	public String getDelYn() {
		return delYn;
	}

	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}

	public String getProdImgInput() {
		return prodImgInput;
	}

	public void setProdImgInput(String prodImgInput) {
		this.prodImgInput = prodImgInput;
	}

	public String getFileNameLink() {
		return fileNameLink;
	}

	public void setFileNameLink(String fileNameLink) {
		this.fileNameLink = fileNameLink;
	}

	public String getChkPropStore() {
		return chkPropStore;
	}

	public void setChkPropStore(String chkPropStore) {
		this.chkPropStore = chkPropStore;
	}

	public String getImgfileUrl() {
		return imgfileUrl;
	}

	public void setImgfileUrl(String imgfileUrl) {
		this.imgfileUrl = imgfileUrl;
	}

	public String getImgRegYn() {
		return imgRegYn;
	}

	public void setImgRegYn(String imgRegYn) {
		this.imgRegYn = imgRegYn;
	}

	public String getOrgFileNm() {
		return orgFileNm;
	}

	public void setOrgFileNm(String orgFileNm) {
		this.orgFileNm = orgFileNm;
	}


	public String getSaveFileNm() {
		return saveFileNm;
	}

	public void setSaveFileNm(String saveFileNm) {
		this.saveFileNm = saveFileNm;
	}


	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getPrdtReqYn() {
		return prdtReqYn;
	}

	public void setPrdtReqYn(String prdtReqYn) {
		this.prdtReqYn = prdtReqYn;
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

	public String getModDt() {
		return modDt;
	}

	public void setModDt(String modDt) {
		this.modDt = modDt;
	}

	public String getPropRegNo() {
		return propRegNo;
	}

	public void setPropRegNo(String propRegNo) {
		this.propRegNo = propRegNo;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getRnum() {
		return rnum;
	}

	public void setRnum(String rnum) {
		this.rnum = rnum;
	}

	public String getPrdtStatus() {
		return prdtStatus;
	}

	public void setPrdtStatus(String prdtStatus) {
		this.prdtStatus = prdtStatus;
	}

	public String getVenCd() {
		return venCd;
	}

	public void setVenCd(String venCd) {
		this.venCd = venCd;
	}

	public String getTeamCd() {
		return teamCd;
	}

	public void setTeamCd(String teamCd) {
		this.teamCd = teamCd;
	}

	public String getL1Cd() {
		return l1Cd;
	}

	public void setL1Cd(String l1Cd) {
		this.l1Cd = l1Cd;
	}

	public String getProdShortNm() {
		return prodShortNm;
	}

	public void setProdShortNm(String prodShortNm) {
		this.prodShortNm = prodShortNm;
	}

	public String getProdStandardNm() {
		return prodStandardNm;
	}

	public void setProdStandardNm(String prodStandardNm) {
		this.prodStandardNm = prodStandardNm;
	}

	public int getNorProdPcost() {
		return norProdPcost;
	}

	public void setNorProdPcost(int norProdPcost) {
		this.norProdPcost = norProdPcost;
	}

	public int getProdSellPrc() {
		return prodSellPrc;
	}

	public void setProdSellPrc(int prodSellPrc) {
		this.prodSellPrc = prodSellPrc;
	}


	public double getPrftRate() {
		return prftRate;
	}

	public void setPrftRate(double prftRate) {
		this.prftRate = prftRate;
	}

	public String getNorSesnCd() {
		return norSesnCd;
	}

	public void setNorSesnCd(String norSesnCd) {
		this.norSesnCd = norSesnCd;
	}

	public String getPrdtRt() {
		return prdtRt;
	}

	public void setPrdtRt(String prdtRt) {
		this.prdtRt = prdtRt;
	}


	public int getInrcntQty() {
		return inrcntQty;
	}

	public void setInrcntQty(int inrcntQty) {
		this.inrcntQty = inrcntQty;
	}

	public String getDelvPsbtDd() {
		return delvPsbtDd;
	}

	public void setDelvPsbtDd(String delvPsbtDd) {
		this.delvPsbtDd = delvPsbtDd;
	}

	public String getSellCd() {
		return sellCd;
	}

	public void setSellCd(String sellCd) {
		this.sellCd = sellCd;
	}

	public String getSellChanCd() {
		return sellChanCd;
	}

	public void setSellChanCd(String sellChanCd) {
		this.sellChanCd = sellChanCd;
	}

	public String getFrzCd() {
		return frzCd;
	}

	public void setFrzCd(String frzCd) {
		this.frzCd = frzCd;
	}

	public String getTrgtSaleCurr() {
		return trgtSaleCurr;
	}

	public void setTrgtSaleCurr(String trgtSaleCurr) {
		this.trgtSaleCurr = trgtSaleCurr;
	}

	public String getPrdtSpec() {
		return prdtSpec;
	}

	public void setPrdtSpec(String prdtSpec) {
		this.prdtSpec = prdtSpec;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getImgId() {
		return imgId;
	}

	public void setImgId(String imgId) {
		this.imgId = imgId;
	}


	public String getSrchEntpCd() {
		return srchEntpCd;
	}

	public void setSrchEntpCd(String srchEntpCd) {
		this.srchEntpCd = srchEntpCd;
	}

	public String getSrchTeamCd() {
		return srchTeamCd;
	}

	public void setSrchTeamCd(String srchTeamCd) {
		this.srchTeamCd = srchTeamCd;
	}

	public String getSrchL1Cd() {
		return srchL1Cd;
	}

	public void setSrchL1Cd(String srchL1Cd) {
		this.srchL1Cd = srchL1Cd;
	}

	public String getSrchProdNm() {
		return srchProdNm;
	}

	public void setSrchProdNm(String srchProdNm) {
		this.srchProdNm = srchProdNm;
	}

	public String getSrchStatus() {
		return srchStatus;
	}

	public void setSrchStatus(String srchStatus) {
		this.srchStatus = srchStatus;
	}

	public String getSrchFromDt() {
		return srchFromDt;
	}

	public void setSrchFromDt(String srchFromDt) {
		this.srchFromDt = srchFromDt;
	}

	public String getSrchEndDt() {
		return srchEndDt;
	}

	public void setSrchEndDt(String srchEndDt) {
		this.srchEndDt = srchEndDt;
	}

	public String[] getVenCds() {
		return venCds;
	}

	public void setVenCds(String[] venCds) {
		this.venCds = venCds;
	}

	public List<Map<String, Object>> getSelBoxChanCds() {
		return selBoxChanCds;
	}

	public void setSelBoxChanCds(List<Map<String, Object>> selBoxChanCds) {
		this.selBoxChanCds = selBoxChanCds;
	}

	public List<NEDMPRO0400VO> getProdArr() {
		return prodArr;
	}

	public void setProdArr(List<NEDMPRO0400VO> prodArr) {
		this.prodArr = prodArr;
	}

	public String getSendYn() {
		return sendYn;
	}

	public void setSendYn(String sendYn) {
		this.sendYn = sendYn;
	}

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public String getSendPath() {
		return sendPath;
	}

	public void setSendPath(String sendPath) {
		this.sendPath = sendPath;
	}

	
}
