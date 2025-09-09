package com.lottemart.epc.system.model;

import java.io.Serializable;

/**
 * @Class Name : PSCMSYS0005VO.java
 * @Description : 전상법 템플릿 VO
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2016. 5. 28. 오후 4:59:06 UNI
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
public class PSCMSYS0005VO implements Serializable {
	private static final long serialVersionUID = -7514113565265397321L;
	// 순번
	private int num;
	// 전상법 쳄플릿 순번
	private String norProdSeq = "";
	// 제목
	private String title = "";
	// 사용여부
	private String useYn = "";
	// 정보그룹코드
	private String infoGrpCd = "";
	// 거래처 ID
	private String vendorId = "";
	// 등록자
	private String regId = "";
	// 등록일시
	private String regDate = "";
	// 수정자
	private String modId = "";
	// 수정일시
	private String modDate = "";
	// 정보그룹 이름
	private String infoGrpNm = "";

	public String getNorProdSeq() {
		return norProdSeq;
	}

	public void setNorProdSeq(String norProdSeq) {
		this.norProdSeq = norProdSeq;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getInfoGrpCd() {
		return infoGrpCd;
	}

	public void setInfoGrpCd(String infoGrpCd) {
		this.infoGrpCd = infoGrpCd;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
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

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getInfoGrpNm() {
		return infoGrpNm;
	}

	public void setInfoGrpNm(String infoGrpNm) {
		this.infoGrpNm = infoGrpNm;
	}

}
