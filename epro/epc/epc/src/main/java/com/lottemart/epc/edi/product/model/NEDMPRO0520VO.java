package com.lottemart.epc.edi.product.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.lcnjf.util.DateUtil;

/**
 * 
 * @Class Name : NEDMPRO0520VO.java
 * @Description : ESG 인증관리 VO 
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2025.03.28		yun				최초생성
 *               </pre>
 */
public class NEDMPRO0520VO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7250095401991046727L;
	
	private String prodCd;		//상품코드
	private String esgGbn;		//ESG 인증구분코드
	private String esgAuth;		//ESG 인증유형코드
	private String esgAuthDtl;	//ESG 상세인증유형코드
	private String esgGbnNm;	//ESG 인증구분코드명
	private String esgAuthNm;	//ESG 인증유형코드명
	private String esgAuthDtlNm;//ESG 상세인증유형코드명
	private String esgFrDt;		//ESG 인증시작일
	private String esgToDt;		//ESG 인증종료일
	private String ifDt;		//인터페이스일시
	private String delYn;		//삭제여부
	private String esgAfFrDt;	//ESG 인증시작일(After)
	private String esgAfToDt;	//ESG 인증종료일(After)
	private String venCd;		//협력사코드
	private String srcmkCd;		//판매코드
	private String prodNm;		//상품명
	private String prodStd;		//규격
	private String expTgYn;		//인증서종료대상여부
	
	private String l1Cd;		//대분류코드
	private String l2Cd;		//중분류코드
	private String l3Cd;		//소분류코드
	private String l1Nm;		//대분류코드명
	private String l2Nm;		//중분류코드명
	private String l3Nm;		//소분류코드명
	
	//첨부파일 정보
	private String esgFileId;	//ESG 인증서파일 아이디
	private String orgFileNm;	//원본파일명
	private String saveFileNm;	//저장파일명
	private String fileSize;	//첨부파일사이즈
	private String filePath;	//파일경로
	private String fileExt;		//파일확장자명
	
	private String rnum;		//행번호
	private String regId;		//등록아이디
	private String regDate;		//등록일시
	private String modId;		//수정아이디
	private String modDate;		//수정일시
	
	//검색조건
	private String srchVenCd;				//조회_파트너사코드
	private String srchSrcmkCd;				//조회_판매코드
	private String srchL1Cd;				//조회_대분류
	private String srchL2Cd;				//조회_중분류
	private String srchL3Cd;				//조회_소분류
	private String srchExpTgYn;				//조회_인증서종료대상
	private String srchYn;					//조회_검색조건입력여부
	private String srchWithDel;				//조회_삭제된정보 포함
	
	private List<NEDMPRO0520VO> prodDataArr;	//대상상품 list
	
	private String rowStat;		//행상태
	private MultipartFile esgFile;	//ESG 파일
	private String tempYn;		//임시파일
	
	private String[] venCds;		//해당 업체 조회조건
	private String esgEarth;		//RE:EARTH 로고 사용여부
	private String esgEarthAf;		//RE:EARTH 로고 사용여부(after)
	private String esgDtFg;			//ESG 인증시작/종료일 필수 Flag
	private String imgFg;			//이미지첨부여부
	private String esgCfmFg;		//ESG인증정보변경 확정상태
	private String esgCfmFgNm;		//ESG인증정보변경 확정상태명
	private String rtnMsg;			//반려사유
	
	private String esgCtgUseYn;		//ESG인증정보(구분-유형-유형상세) 사용여부
	
	private String delEsgYn;		//삭제된인증정보인지 확인
	
	private String srchEsgGbn;		//조회_인증구분
	private String srchEsgAuth;		//조회_인증유형
	private String srchEsgAuthDtl;	//조회_인증상세유형
	private String srchStartDt;		//조회_조회시작일
	private String srchEndDt;		//조회_조회종료일
	
	private String srchEsgCfmFg;	//조회_확정상태
	
	private String isTodyCfmOk;		//오늘승인된건인지 확인
	
	//파일 SFTP 전송정보
	private String sendYn;			//전송여부
	private String sendDate;		//전송일시
	private String sendPath;		//전송경로

	public String getProdCd() {
		return prodCd;
	}

	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}

	public String getEsgGbn() {
		return esgGbn;
	}

	public void setEsgGbn(String esgGbn) {
		this.esgGbn = esgGbn;
	}

	public String getEsgAuth() {
		return esgAuth;
	}

	public void setEsgAuth(String esgAuth) {
		this.esgAuth = esgAuth;
	}

	public String getEsgAuthDtl() {
		return esgAuthDtl;
	}

	public void setEsgAuthDtl(String esgAuthDtl) {
		this.esgAuthDtl = esgAuthDtl;
	}

	public String getEsgGbnNm() {
		return esgGbnNm;
	}

	public void setEsgGbnNm(String esgGbnNm) {
		this.esgGbnNm = esgGbnNm;
	}

	public String getEsgAuthNm() {
		return esgAuthNm;
	}

	public void setEsgAuthNm(String esgAuthNm) {
		this.esgAuthNm = esgAuthNm;
	}

	public String getEsgAuthDtlNm() {
		return esgAuthDtlNm;
	}

	public void setEsgAuthDtlNm(String esgAuthDtlNm) {
		this.esgAuthDtlNm = esgAuthDtlNm;
	}

	public String getEsgFrDt() {
		return esgFrDt;
	}

	public void setEsgFrDt(String esgFrDt) {
		this.esgFrDt = esgFrDt;
	}

	public String getEsgToDt() {
		return esgToDt;
	}

	public void setEsgToDt(String esgToDt) {
		this.esgToDt = esgToDt;
	}

	public String getIfDt() {
		return ifDt;
	}

	public void setIfDt(String ifDt) {
		this.ifDt = ifDt;
	}

	public String getDelYn() {
		return delYn;
	}

	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}

	public String getEsgAfFrDt() {
		return esgAfFrDt;
	}

	public void setEsgAfFrDt(String esgAfFrDt) {
		this.esgAfFrDt = esgAfFrDt;
	}

	public String getEsgAfToDt() {
		return esgAfToDt;
	}

	public void setEsgAfToDt(String esgAfToDt) {
		this.esgAfToDt = esgAfToDt;
	}

	public String getVenCd() {
		return venCd;
	}

	public void setVenCd(String venCd) {
		this.venCd = venCd;
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

	public String getExpTgYn() {
		return expTgYn;
	}

	public void setExpTgYn(String expTgYn) {
		this.expTgYn = expTgYn;
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

	public String getEsgFileId() {
		return esgFileId;
	}

	public void setEsgFileId(String esgFileId) {
		this.esgFileId = esgFileId;
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

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
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

	public String getSrchVenCd() {
		return srchVenCd;
	}

	public void setSrchVenCd(String srchVenCd) {
		this.srchVenCd = srchVenCd;
	}

	public String getSrchSrcmkCd() {
		return srchSrcmkCd;
	}

	public void setSrchSrcmkCd(String srchSrcmkCd) {
		this.srchSrcmkCd = srchSrcmkCd;
	}

	public String getSrchL1Cd() {
		return srchL1Cd;
	}

	public void setSrchL1Cd(String srchL1Cd) {
		this.srchL1Cd = srchL1Cd;
	}

	public String getSrchL2Cd() {
		return srchL2Cd;
	}

	public void setSrchL2Cd(String srchL2Cd) {
		this.srchL2Cd = srchL2Cd;
	}

	public String getSrchExpTgYn() {
		return srchExpTgYn;
	}

	public void setSrchExpTgYn(String srchExpTgYn) {
		this.srchExpTgYn = srchExpTgYn;
	}

	public String getSrchYn() {
		return srchYn;
	}

	public void setSrchYn(String srchYn) {
		this.srchYn = srchYn;
	}

	public List<NEDMPRO0520VO> getProdDataArr() {
		return prodDataArr;
	}

	public void setProdDataArr(List<NEDMPRO0520VO> prodDataArr) {
		this.prodDataArr = prodDataArr;
	}
	
	public String getEsgFrDtFmt() {
		if(esgFrDt != null && !"".equals(esgFrDt) && DateUtil.isDate(esgFrDt, "yyyyMMdd")) {
			return DateUtil.string2String(esgFrDt, "yyyyMMdd", DateUtil.DATE_PATTERN);
		}
		return "";
	}
	
	public String getEsgToDtFmt() {
		if(esgToDt != null && !"".equals(esgToDt) && DateUtil.isDate(esgToDt, "yyyyMMdd")) {
			return DateUtil.string2String(esgToDt, "yyyyMMdd", DateUtil.DATE_PATTERN);
		}
		return "";
	}
	
	public String getEsgAfFrDtFmt() {
		if(esgAfFrDt != null && !"".equals(esgAfFrDt) && DateUtil.isDate(esgAfFrDt, "yyyyMMdd")) {
			return DateUtil.string2String(esgAfFrDt, "yyyyMMdd", DateUtil.DATE_PATTERN);
		}
		return "";
	}
	
	public String getEsgAfToDtFmt() {
		if(esgAfToDt != null && !"".equals(esgAfToDt) && DateUtil.isDate(esgAfToDt, "yyyyMMdd")) {
			return DateUtil.string2String(esgAfToDt, "yyyyMMdd", DateUtil.DATE_PATTERN);
		}
		return "";
	}

	public String getRowStat() {
		return rowStat;
	}

	public void setRowStat(String rowStat) {
		this.rowStat = rowStat;
	}

	public MultipartFile getEsgFile() {
		return esgFile;
	}

	public void setEsgFile(MultipartFile esgFile) {
		this.esgFile = esgFile;
	}

	public String getTempYn() {
		return tempYn;
	}

	public void setTempYn(String tempYn) {
		this.tempYn = tempYn;
	}

	public String[] getVenCds() {
		return venCds;
	}

	public void setVenCds(String[] venCds) {
		this.venCds = venCds;
	}

	public String getEsgEarth() {
		return esgEarth;
	}

	public void setEsgEarth(String esgEarth) {
		this.esgEarth = esgEarth;
	}

	public String getEsgDtFg() {
		return esgDtFg;
	}

	public void setEsgDtFg(String esgDtFg) {
		this.esgDtFg = esgDtFg;
	}

	public String getImgFg() {
		return imgFg;
	}

	public void setImgFg(String imgFg) {
		this.imgFg = imgFg;
	}

	public String getEsgCfmFg() {
		return esgCfmFg;
	}

	public void setEsgCfmFg(String esgCfmFg) {
		this.esgCfmFg = esgCfmFg;
	}

	public String getRtnMsg() {
		return rtnMsg;
	}

	public void setRtnMsg(String rtnMsg) {
		this.rtnMsg = rtnMsg;
	}

	public String getEsgEarthAf() {
		return esgEarthAf;
	}

	public void setEsgEarthAf(String esgEarthAf) {
		this.esgEarthAf = esgEarthAf;
	}

	public String getEsgCtgUseYn() {
		return esgCtgUseYn;
	}

	public void setEsgCtgUseYn(String esgCtgUseYn) {
		this.esgCtgUseYn = esgCtgUseYn;
	}

	public String getDelEsgYn() {
		return delEsgYn;
	}

	public void setDelEsgYn(String delEsgYn) {
		this.delEsgYn = delEsgYn;
	}

	public String getSrchWithDel() {
		return srchWithDel;
	}

	public void setSrchWithDel(String srchWithDel) {
		this.srchWithDel = srchWithDel;
	}

	public String getSrchEsgGbn() {
		return srchEsgGbn;
	}

	public void setSrchEsgGbn(String srchEsgGbn) {
		this.srchEsgGbn = srchEsgGbn;
	}

	public String getSrchEsgAuth() {
		return srchEsgAuth;
	}

	public void setSrchEsgAuth(String srchEsgAuth) {
		this.srchEsgAuth = srchEsgAuth;
	}

	public String getSrchEsgAuthDtl() {
		return srchEsgAuthDtl;
	}

	public void setSrchEsgAuthDtl(String srchEsgAuthDtl) {
		this.srchEsgAuthDtl = srchEsgAuthDtl;
	}

	public String getSrchL3Cd() {
		return srchL3Cd;
	}

	public void setSrchL3Cd(String srchL3Cd) {
		this.srchL3Cd = srchL3Cd;
	}

	public String getSrchStartDt() {
		return srchStartDt;
	}

	public void setSrchStartDt(String srchStartDt) {
		this.srchStartDt = srchStartDt;
	}

	public String getSrchEndDt() {
		return srchEndDt;
	}

	public void setSrchEndDt(String srchEndDt) {
		this.srchEndDt = srchEndDt;
	}

	public String getEsgCfmFgNm() {
		return esgCfmFgNm;
	}

	public void setEsgCfmFgNm(String esgCfmFgNm) {
		this.esgCfmFgNm = esgCfmFgNm;
	}

	public String getSrchEsgCfmFg() {
		return srchEsgCfmFg;
	}

	public void setSrchEsgCfmFg(String srchEsgCfmFg) {
		this.srchEsgCfmFg = srchEsgCfmFg;
	}

	public String getIsTodyCfmOk() {
		return isTodyCfmOk;
	}

	public void setIsTodyCfmOk(String isTodyCfmOk) {
		this.isTodyCfmOk = isTodyCfmOk;
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