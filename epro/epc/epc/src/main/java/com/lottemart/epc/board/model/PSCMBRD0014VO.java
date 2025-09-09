package com.lottemart.epc.board.model;

import java.io.Serializable;

/**
 * @Class Name : PSCMBRD0014VO.java
 * @Description :
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2016. 4. 29. 오후 5:52:28 choi
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
/**
 * @Class Name : PSCMBRD0014VO.java
 * @Description :
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2016. 5. 2. 오후 2:07:45 UNI
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
public class PSCMBRD0014VO implements Serializable {
	private static final long serialVersionUID = 1L;
	// 게시판 순번
	private String num = "";
	// 상품 Q&A 순번
	private String prodQnaSeq = "";
	// 인터넷 상품코드
	private String prodCd = "";
	// 문의유형코드
	private String qstTypeCd = "";
	//문의 유형 이름
	private String qstTypeNm="";
	// 상품명
	private String prodNm = "";
	// 제목
	private String title = "";
	// 문의내용
	private String qstContent = "";
	// 문의일시
	private String qstDate = "";
	// 답변내용
	private String ansContent = "";
	// 답변일시
	private String ansDate = "";
	// 처리여부
	private String procYn = "";
	// 회원번호
	private String memberNo = "";
	// 등록자
	private String regId = "";
	// 등록일시
	private String regDate = "";
	// 수정자
	private String modId = "";
	// 수정일시
	private String modDate = "";

	private String itemCd = "";
	// 공개 여부
	private String npblYn = "";
	//몰구분
	private String mallDivnCd="";
	//버튼
	private String button = "";
	//주문번호
	private String order_id = "";
	// 회원ID
	private String ecMemberId = "";
	// 회원명
	private String ecMemberNm = "";

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getProdQnaSeq() {
		return prodQnaSeq;
	}

	public void setProdQnaSeq(String prodQnaSeq) {
		this.prodQnaSeq = prodQnaSeq;
	}

	public String getProdCd() {
		return prodCd;
	}

	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}

	public String getQstTypeCd() {
		return qstTypeCd;
	}

	public void setQstTypeCd(String qstTypeCd) {
		this.qstTypeCd = qstTypeCd;
	}

	public String getProdNm() {
		return prodNm;
	}

	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getQstContent() {
		return qstContent;
	}

	public void setQstContent(String qstContent) {
		this.qstContent = qstContent;
	}

	public String getQstDate() {
		return qstDate;
	}

	public void setQstDate(String qstDate) {
		this.qstDate = qstDate;
	}

	public String getAnsContent() {
		return ansContent;
	}

	public void setAnsContent(String ansContent) {
		this.ansContent = ansContent;
	}

	public String getAnsDate() {
		return ansDate;
	}

	public void setAnsDate(String ansDate) {
		this.ansDate = ansDate;
	}

	public String getProcYn() {
		return procYn;
	}

	public void setProcYn(String procYn) {
		this.procYn = procYn;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
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

	public String getItemCd() {
		return itemCd;
	}

	public void setItemCd(String itemCd) {
		this.itemCd = itemCd;
	}

	public String getNpblYn() {
		return npblYn;
	}

	public void setNpblYn(String npblYn) {
		this.npblYn = npblYn;
	}

	public String getQstTypeNm() {
		return qstTypeNm;
	}

	public void setQstTypeNm(String qstTypeNm) {
		this.qstTypeNm = qstTypeNm;
	}

	public String getMallDivnCd() {
		return mallDivnCd;
	}

	public void setMallDivnCd(String mallDivnCd) {
		this.mallDivnCd = mallDivnCd;
	}

	public String getButton() {
		return button;
	}

	public void setButton(String button) {
		this.button = button;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getEcMemberId() {
		return ecMemberId;
	}

	public void setEcMemberId(String ecMemberId) {
		this.ecMemberId = ecMemberId;
	}

	public String getEcMemberNm() {
		return ecMemberNm;
	}

	public void setEcMemberNm(String ecMemberNm) {
		this.ecMemberNm = ecMemberNm;
	}

}
