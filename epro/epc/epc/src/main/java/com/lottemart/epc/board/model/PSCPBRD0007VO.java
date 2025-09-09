/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.board.model;

import java.io.Serializable;

/**
 * @Class Name : PSCPBRD0007VO
 * @Description : 업체문의사항 상세 VO 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 4:05:38 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public class PSCPBRD0007VO implements Serializable {

	private static final long serialVersionUID = 1595306185018834312L;
	
	private String boardSeq;
	private String boardDivnCd;
	private String listSeq;
	private String upBoardSeq;
	private String ntceStartDy;
	private String ntceEndDy;
	private String boardDpSeq;
	private String title;
	private String content;
	private String memo;
	private String smryContent;
	private String pwd;
	private String scrpKindCd;
	private String wrtrDivnCd;
	private String memberNo;
	private String memberNm;
	private String childScrpCnt;
	private String depth;
	private String viewCnt;
	private String recomCnt;
	private String recomPnt;
	private String atchFileYn;
	private String delYn;
	private String orderId;
	private String scrpObjectCd;
	private String custQstDivnCd;
	private String custQstMgrpCd;
	private String clmLgrpCd;
	private String clmMgrpCd;
	private String boardPrgsStsCd;
	private String telNo;
	private String cellNov;
	private String email;
	private String smsDspYn;
	private String ansSmsRecvYn;
	private String ordYn;
	private String wrtDy;
	private String pblYn;
	private String email_snd_yn;
	private String ansEmailRecvYn;
	private String searchKywrd;
	private String url;
	private String wpmnAnncDy;
	private String prizeContents;
	private String prizePrvsMethodCd;
	private String qstnObjectCdStrCd;
	private String acceptLocaCd;
	private String acceptId;
	private String dicmfDeclareYn;
	private String let1Ref;
	private String let2Ref;
	private String let3Ref;
	private String let4Ref;
	private String regDate;
	private String regId;
	private String modDate;
	private String modId;
	private String atchFileId;
	
	
	public String getBoardSeq() {
		return boardSeq;
	}
	public void setBoardSeq(String boardSeq) {
		this.boardSeq = boardSeq;
	}
	public String getBoardDivnCd() {
		return boardDivnCd;
	}
	public void setBoardDivnCd(String boardDivnCd) {
		this.boardDivnCd = boardDivnCd;
	}
	public String getListSeq() {
		return listSeq;
	}
	public void setListSeq(String listSeq) {
		this.listSeq = listSeq;
	}
	public String getUpBoardSeq() {
		return upBoardSeq;
	}
	public void setUpBoardSeq(String upBoardSeq) {
		this.upBoardSeq = upBoardSeq;
	}
	public String getNtceStartDy() {
		return ntceStartDy;
	}
	public void setNtceStartDy(String ntceStartDy) {
		this.ntceStartDy = ntceStartDy;
	}
	public String getNtceEndDy() {
		return ntceEndDy;
	}
	public void setNtceEndDy(String ntceEndDy) {
		this.ntceEndDy = ntceEndDy;
	}
	public String getBoardDpSeq() {
		return boardDpSeq;
	}
	public void setBoardDpSeq(String boardDpSeq) {
		this.boardDpSeq = boardDpSeq;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getSmryContent() {
		return smryContent;
	}
	public void setSmryContent(String smryContent) {
		this.smryContent = smryContent;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getScrpKindCd() {
		return scrpKindCd;
	}
	public void setScrpKindCd(String scrpKindCd) {
		this.scrpKindCd = scrpKindCd;
	}
	public String getWrtrDivnCd() {
		return wrtrDivnCd;
	}
	public void setWrtrDivnCd(String wrtrDivnCd) {
		this.wrtrDivnCd = wrtrDivnCd;
	}
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	public String getMemberNm() {
		return memberNm;
	}
	public void setMemberNm(String memberNm) {
		this.memberNm = memberNm;
	}
	public String getChildScrpCnt() {
		return childScrpCnt;
	}
	public void setChildScrpCnt(String childScrpCnt) {
		this.childScrpCnt = childScrpCnt;
	}
	public String getDepth() {
		return depth;
	}
	public void setDepth(String depth) {
		this.depth = depth;
	}
	public String getViewCnt() {
		return viewCnt;
	}
	public void setViewCnt(String viewCnt) {
		this.viewCnt = viewCnt;
	}
	public String getRecomCnt() {
		return recomCnt;
	}
	public void setRecomCnt(String recomCnt) {
		this.recomCnt = recomCnt;
	}
	public String getRecomPnt() {
		return recomPnt;
	}
	public void setRecomPnt(String recomPnt) {
		this.recomPnt = recomPnt;
	}
	public String getAtchFileYn() {
		return atchFileYn;
	}
	public void setAtchFileYn(String atchFileYn) {
		this.atchFileYn = atchFileYn;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getScrpObjectCd() {
		return scrpObjectCd;
	}
	public void setScrpObjectCd(String scrpObjectCd) {
		this.scrpObjectCd = scrpObjectCd;
	}
	public String getCustQstDivnCd() {
		return custQstDivnCd;
	}
	public void setCustQstDivnCd(String custQstDivnCd) {
		this.custQstDivnCd = custQstDivnCd;
	}
	public String getCustQstMgrpCd() {
		return custQstMgrpCd;
	}
	public void setCustQstMgrpCd(String custQstMgrpCd) {
		this.custQstMgrpCd = custQstMgrpCd;
	}
	public String getClmLgrpCd() {
		return clmLgrpCd;
	}
	public void setClmLgrpCd(String clmLgrpCd) {
		this.clmLgrpCd = clmLgrpCd;
	}
	public String getClmMgrpCd() {
		return clmMgrpCd;
	}
	public void setClmMgrpCd(String clmMgrpCd) {
		this.clmMgrpCd = clmMgrpCd;
	}
	public String getBoardPrgsStsCd() {
		return boardPrgsStsCd;
	}
	public void setBoardPrgsStsCd(String boardPrgsStsCd) {
		this.boardPrgsStsCd = boardPrgsStsCd;
	}
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	public String getCellNov() {
		return cellNov;
	}
	public void setCellNov(String cellNov) {
		this.cellNov = cellNov;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSmsDspYn() {
		return smsDspYn;
	}
	public void setSmsDspYn(String smsDspYn) {
		this.smsDspYn = smsDspYn;
	}
	public String getAnsSmsRecvYn() {
		return ansSmsRecvYn;
	}
	public void setAnsSmsRecvYn(String ansSmsRecvYn) {
		this.ansSmsRecvYn = ansSmsRecvYn;
	}
	public String getOrdYn() {
		return ordYn;
	}
	public void setOrdYn(String ordYn) {
		this.ordYn = ordYn;
	}
	public String getWrtDy() {
		return wrtDy;
	}
	public void setWrtDy(String wrtDy) {
		this.wrtDy = wrtDy;
	}
	public String getPblYn() {
		return pblYn;
	}
	public void setPblYn(String pblYn) {
		this.pblYn = pblYn;
	}
	public String getEmail_snd_yn() {
		return email_snd_yn;
	}
	public void setEmail_snd_yn(String email_snd_yn) {
		this.email_snd_yn = email_snd_yn;
	}
	public String getAnsEmailRecvYn() {
		return ansEmailRecvYn;
	}
	public void setAnsEmailRecvYn(String ansEmailRecvYn) {
		this.ansEmailRecvYn = ansEmailRecvYn;
	}
	public String getSearchKywrd() {
		return searchKywrd;
	}
	public void setSearchKywrd(String searchKywrd) {
		this.searchKywrd = searchKywrd;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getWpmnAnncDy() {
		return wpmnAnncDy;
	}
	public void setWpmnAnncDy(String wpmnAnncDy) {
		this.wpmnAnncDy = wpmnAnncDy;
	}
	public String getPrizeContents() {
		return prizeContents;
	}
	public void setPrizeContents(String prizeContents) {
		this.prizeContents = prizeContents;
	}
	public String getPrizePrvsMethodCd() {
		return prizePrvsMethodCd;
	}
	public void setPrizePrvsMethodCd(String prizePrvsMethodCd) {
		this.prizePrvsMethodCd = prizePrvsMethodCd;
	}
	public String getQstnObjectCdStrCd() {
		return qstnObjectCdStrCd;
	}
	public void setQstnObjectCdStrCd(String qstnObjectCdStrCd) {
		this.qstnObjectCdStrCd = qstnObjectCdStrCd;
	}
	public String getAcceptLocaCd() {
		return acceptLocaCd;
	}
	public void setAcceptLocaCd(String acceptLocaCd) {
		this.acceptLocaCd = acceptLocaCd;
	}
	public String getAcceptId() {
		return acceptId;
	}
	public void setAcceptId(String acceptId) {
		this.acceptId = acceptId;
	}
	public String getDicmfDeclareYn() {
		return dicmfDeclareYn;
	}
	public void setDicmfDeclareYn(String dicmfDeclareYn) {
		this.dicmfDeclareYn = dicmfDeclareYn;
	}
	public String getLet1Ref() {
		return let1Ref;
	}
	public void setLet1Ref(String let1Ref) {
		this.let1Ref = let1Ref;
	}
	public String getLet2Ref() {
		return let2Ref;
	}
	public void setLet2Ref(String let2Ref) {
		this.let2Ref = let2Ref;
	}
	public String getLet3Ref() {
		return let3Ref;
	}
	public void setLet3Ref(String let3Ref) {
		this.let3Ref = let3Ref;
	}
	public String getLet4Ref() {
		return let4Ref;
	}
	public void setLet4Ref(String let4Ref) {
		this.let4Ref = let4Ref;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getModDate() {
		return modDate;
	}
	public void setModDate(String modDate) {
		this.modDate = modDate;
	}
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}
	public String getAtchFileId() {
		return atchFileId;
	}
	public void setAtchFileId(String atchFileId) {
		this.atchFileId = atchFileId;
	}
	
	
}
