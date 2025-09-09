package com.lottemart.epc.system.model;

import java.io.Serializable;

/**
 * @Class Name : PSCMSYS0005DtlVO.java
 * @Description : 전상법 템플릿 상세
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2016. 6. 1. 오전 11:02:58 UNI
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
public class PSCMSYS0005DtlVO implements Serializable {
	private static final long serialVersionUID = -9074610227095969354L;
	// 순번
	private int num;
	// 전상법 템플릿순번
	private String norProdSeq;
	// 정보그룹 코드
	private String infoGrpCd = "";
	// 정보컬럼 코드
	private String infoColCd = "";
	// 컬럼값
	private String colVal = "";
	// 등록자
	private String regId = "";
	// 등록일시
	private String regDate = "";
	// 수정자
	private String modId = "";
	// 수정일시
	private String modDate = "";

	private String infoColNm = "";

	public String getInfoGrpCd() {
		return infoGrpCd;
	}

	public void setInfoGrpCd(String infoGrpCd) {
		this.infoGrpCd = infoGrpCd;
	}

	public String getInfoColCd() {
		return infoColCd;
	}

	public void setInfoColCd(String infoColCd) {
		this.infoColCd = infoColCd;
	}

	public String getColVal() {
		return colVal;
	}

	public void setColVal(String colVal) {
		this.colVal = colVal;
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

	public String getInfoColNm() {
		return infoColNm;
	}

	public void setInfoColNm(String infoColNm) {
		this.infoColNm = infoColNm;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getNorProdSeq() {
		return norProdSeq;
	}

	public void setNorProdSeq(String norProdSeq) {
		this.norProdSeq = norProdSeq;
	}

}
