package com.lottemart.epc.edi.product.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.lcnjf.util.DateUtil;
import com.lottemart.epc.edi.comm.model.PagingVO;


/**
 * @Class Name : NEDMPRO0028VO
 * @Description : ESG 항목 등록 VO
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      	수정자           		수정내용
 *  -------    --------    ---------------------------
 * 2025.03.21  PARK JONG GYU 		최초생성
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */

public class NEDMPRO0028VO extends PagingVO  implements Serializable {

	
	private static final long serialVersionUID = -6918711902569942487L;


	public NEDMPRO0028VO () {}
	
	/** 검색시작일 */
	private String srchStartDate;
	
	/** 검색종료일 */
	private String srchEndDate;
	
	/** ESG 항목 item 정보 */
	private List<NEDMPRO0028VO> itemList;
	
	/** 신규상품코드 */
	private String pgmId;
	
	/** ESG 인증구분 코드 */
	private String esgGbn;
	
	/** ESG 인증유형 코드 */
	private String esgAuth;
	
	/** 상세인증유형코드 */
	private String esgAuthDtl;
	
	/** 인증시작일 */
	private String esgFrDt;
	
	/** 인증종료일 */
	private String esgToDt;
	
	/** 인증서 첨부파일 ID */
	private String esgFileId;
	
	/** 인터페이스 일시 */
	private String ifDt;
	
	/** 등록아이디 */
	private String regId;
	
	/** 등록일시 */
	private String regDate;
	
	/** 수정아이디 */
	private String modId;
	
	/** 수정일시 */
	private String modDate;
	
	/** ESG 상품여부 */
	private String esgYn;
	
	/** RE:EARTH 로고 사용유무  */
	private String esgEarth;

	/** ESG 인증구분 코드 */
	private String lLvCdn;
	
	/** ESG 인증구분 코드명 */
	private String lLvNmn;
	
	/** ESG 인증유형 코드 */
	private String mLvCdn;
	
	/** ESG 인증유형 코드 */
	private String mLvNmn;
	
	/** 상세인증유형코드 */
	private String sLvCdn;
	
	/** 상세인증유형코드명 */
	private String sLvNmn;
	
	/** 작업자 id */
	private String workId;
	
	/** 삭제여부 */
	private String delYn;
	
	/** 확정여부 */
	private String cfmFg;

	/** 서버에 등록된 파일명 */
	private String saveFileId;
	
	/** 서버에 등록할 file id */
	private String saveEsgFileId;
	
	/** 원본 파일명 */
	private String ordFileNm;
	
	/** 파일 확장자 */
	private String fileExt;
	
	/** 파일 타입*/
	private String fileType;
	
	/** row 속성 */
	private String rowAttri;
	
	/** 기존 ESG 인증구분 코드 */
	private String ordEsgGbn;
	
	/** 기존 ESG 인증유형 코드 */
	private String ordEsgAuth;
	
	/** 기존 상세인증유형코드 */
	private String ordEsgAuthDtl;
	
	/** 원본파일명 */
	private String orgFileNm;
	
	/** 저장 파일명	*/
	private String saveFileNm;
	
	/** 파일사이즈	*/
	private String fileSize;
	
	/** 파일확장자명 */
	private String filePath;
	
	/** 협력사코드 */
	private String entpCd;
	
	/** ESG인증 기간 필수여부 */
	private String esgDtFg;
	
	private MultipartFile esgFile;	//ESG 파일
	private List<NEDMPRO0028VO> prodDataArr;	//대상상품 list
	
	/** ESG 인증구분명 */
	private String esgGbnNm;
	
	/** ESG 인증유형명 */
	private String esgAuthNm;
	
	/** 상세인증유형명 */
	private String esgAuthDtlNm;
	
	/** 수정가능여부 */
	private String editYn;
	
	private String srchEsgGbn;		//조회_인증구분
	private String srchEsgAuth;		//조회_인증유형
	private String srchEsgAuthDtl;	//조회_인증상세유형
	
	//파일 SFTP 전송정보
	private String sendYn;			//전송여부
	private String sendDate;		//전송일시
	private String sendPath;		//전송경로
	
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

	public String getOrdEsgGbn() {
		return ordEsgGbn;
	}

	public void setOrdEsgGbn(String ordEsgGbn) {
		this.ordEsgGbn = ordEsgGbn;
	}

	public String getOrdEsgAuth() {
		return ordEsgAuth;
	}

	public void setOrdEsgAuth(String ordEsgAuth) {
		this.ordEsgAuth = ordEsgAuth;
	}

	public String getOrdEsgAuthDtl() {
		return ordEsgAuthDtl;
	}

	public void setOrdEsgAuthDtl(String ordEsgAuthDtl) {
		this.ordEsgAuthDtl = ordEsgAuthDtl;
	}

	public String getRowAttri() {
		return rowAttri;
	}

	public void setRowAttri(String rowAttri) {
		this.rowAttri = rowAttri;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getOrdFileNm() {
		return ordFileNm;
	}

	public void setOrdFileNm(String ordFileNm) {
		this.ordFileNm = ordFileNm;
	}

	public String getSaveEsgFileId() {
		return saveEsgFileId;
	}

	public void setSaveEsgFileId(String saveEsgFileId) {
		this.saveEsgFileId = saveEsgFileId;
	}

	public String getSaveFileId() {
		return saveFileId;
	}

	public void setSaveFileId(String saveFileId) {
		this.saveFileId = saveFileId;
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

	public List<NEDMPRO0028VO> getItemList() {
		return itemList;
	}

	public void setItemList(List<NEDMPRO0028VO> itemList) {
		this.itemList = itemList;
	}

	public String getPgmId() {
		return pgmId;
	}

	public void setPgmId(String pgmId) {
		this.pgmId = pgmId;
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

	public String getEsgFrDt() {
		return esgFrDt;
	}

	public void setEsgFrDt(String esgFrDt) {
		this.esgFrDt = esgFrDt;
	}
	
	public String getEsgFrDtFmt() {
		if(esgFrDt != null && !"".equals(esgFrDt) && DateUtil.isDate(esgFrDt, "yyyyMMdd")) {
			return DateUtil.string2String(esgFrDt, "yyyyMMdd", DateUtil.DATE_PATTERN);
		}
		return "";
	}

	public String getEsgToDt() {
		return esgToDt;
	}

	public void setEsgToDt(String esgToDt) {
		this.esgToDt = esgToDt;
	}
	
	public String getEsgToDtFmt() {
		if(esgToDt != null && !"".equals(esgToDt) && DateUtil.isDate(esgToDt, "yyyyMMdd")) {
			return DateUtil.string2String(esgToDt, "yyyyMMdd", DateUtil.DATE_PATTERN);
		}
		return "";
	}

	public String getEsgFileId() {
		return esgFileId;
	}

	public void setEsgFileId(String esgFileId) {
		this.esgFileId = esgFileId;
	}

	public String getIfDt() {
		return ifDt;
	}

	public void setIfDt(String ifDt) {
		this.ifDt = ifDt;
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

	public String getEsgYn() {
		return esgYn;
	}

	public void setEsgYn(String esgYn) {
		this.esgYn = esgYn;
	}

	public String getEsgEarth() {
		return esgEarth;
	}

	public void setEsgEarth(String esgEarth) {
		this.esgEarth = esgEarth;
	}

	public String getlLvCdn() {
		return lLvCdn;
	}

	public void setlLvCdn(String lLvCdn) {
		this.lLvCdn = lLvCdn;
	}

	public String getlLvNmn() {
		return lLvNmn;
	}

	public void setlLvNmn(String lLvNmn) {
		this.lLvNmn = lLvNmn;
	}

	public String getmLvCdn() {
		return mLvCdn;
	}

	public void setmLvCdn(String mLvCdn) {
		this.mLvCdn = mLvCdn;
	}

	public String getmLvNmn() {
		return mLvNmn;
	}

	public void setmLvNmn(String mLvNmn) {
		this.mLvNmn = mLvNmn;
	}

	public String getsLvCdn() {
		return sLvCdn;
	}

	public void setsLvCdn(String sLvCdn) {
		this.sLvCdn = sLvCdn;
	}

	public String getsLvNmn() {
		return sLvNmn;
	}

	public void setsLvNmn(String sLvNmn) {
		this.sLvNmn = sLvNmn;
	}

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public String getDelYn() {
		return delYn;
	}

	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}

	public String getCfmFg() {
		return cfmFg;
	}

	public void setCfmFg(String cfmFg) {
		this.cfmFg = cfmFg;
	}

	public String getEntpCd() {
		return entpCd;
	}

	public void setEntpCd(String entpCd) {
		this.entpCd = entpCd;
	}

	public String getEsgDtFg() {
		return esgDtFg;
	}

	public void setEsgDtFg(String esgDtFg) {
		this.esgDtFg = esgDtFg;
	}

	public MultipartFile getEsgFile() {
		return esgFile;
	}

	public void setEsgFile(MultipartFile esgFile) {
		this.esgFile = esgFile;
	}

	public List<NEDMPRO0028VO> getProdDataArr() {
		return prodDataArr;
	}

	public void setProdDataArr(List<NEDMPRO0028VO> prodDataArr) {
		this.prodDataArr = prodDataArr;
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

	public String getEditYn() {
		return editYn;
	}

	public void setEditYn(String editYn) {
		this.editYn = editYn;
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
