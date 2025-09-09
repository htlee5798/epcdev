package com.lottemart.epc.system.model;


import java.io.Serializable;

/**
 * @Class Name : PSCMSYS0001VO.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 20. 오후 02:02:02 mjChoi
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
public class PSCMSYS0001VO implements Serializable
{  
	private static final long serialVersionUID = 812751959266321133L;
	
	private String VENDOR_ID			= "";	//협력업체번호
	private String PROD_CD				= "";	//prod코드
		
	//
	private String APPLY_START_DY		= "";	//적용시작일자 
	private String APPLY_END_DY		= "";	//적용종료일자 
	private String DELI_BASE_MIN_AMT	= "";	//기준하안금액 
	private String DELI_BASE_MAX_AMT	= "";	//기준상안금액 
	private String DELIVERY_AMT		= "";	//배송비
	private String REG_ID 				= "";	//등록자
	private String MOD_ID				= "";	//수정자
	private String DELI_DIVN_CD        = "";
	private String DELI_DIVN_NM        = "";
	private String DO_MODIFY           = "";
	private String DO_DELETE           = "";

	private String RTN_AMT             = "";   //반품비
	private String EXCH_AMT            = "";   //교환비
	private String ADD_DELI_AMT1       = "";   //추가배송비_제주
	private String ADD_DELI_AMT2       = "";   //추가배송비_도서산간
	private String BASE_DLV_AMT        = "";   //기본배송비
	
	//
	private String ONE_DAY_BEFORE		= "";
	
	// 페이징
	private String currentPage = "";//페이지수
	private int startRow = 1;
	private int endRow = 10;
	private String num = "";
	
	public String getVENDOR_ID() {
		return VENDOR_ID;
	}
	public void setVENDOR_ID(String vENDOR_ID) {
		VENDOR_ID = vENDOR_ID;
	}
	public String getPROD_CD() {
		return PROD_CD;
	}
	public void setPROD_CD(String pROD_CD) {
		PROD_CD = pROD_CD;
	}
	public String getAPPLY_START_DY() {
		return APPLY_START_DY;
	}
	public void setAPPLY_START_DY(String aPPLY_START_DY) {
		APPLY_START_DY = aPPLY_START_DY;
	}
	public String getAPPLY_END_DY() {
		return APPLY_END_DY;
	}
	public void setAPPLY_END_DY(String aPPLY_END_DY) {
		APPLY_END_DY = aPPLY_END_DY;
	}
	public String getDELI_BASE_MIN_AMT() {
		return DELI_BASE_MIN_AMT;
	}
	public void setDELI_BASE_MIN_AMT(String dELI_BASE_MIN_AMT) {
		DELI_BASE_MIN_AMT = dELI_BASE_MIN_AMT;
	}
	public String getDELI_BASE_MAX_AMT() {
		return DELI_BASE_MAX_AMT;
	}
	public void setDELI_BASE_MAX_AMT(String dELI_BASE_MAX_AMT) {
		DELI_BASE_MAX_AMT = dELI_BASE_MAX_AMT;
	}
	public String getDELIVERY_AMT() {
		return DELIVERY_AMT;
	}
	public void setDELIVERY_AMT(String dELIVERY_AMT) {
		DELIVERY_AMT = dELIVERY_AMT;
	}
	public String getREG_ID() {
		return REG_ID;
	}
	public void setREG_ID(String rEG_ID) {
		REG_ID = rEG_ID;
	}
	public String getMOD_ID() {
		return MOD_ID;
	}
	public void setMOD_ID(String mOD_ID) {
		MOD_ID = mOD_ID;
	}
	public String getONE_DAY_BEFORE() {
		return ONE_DAY_BEFORE;
	}
	public void setONE_DAY_BEFORE(String oNE_DAY_BEFORE) {
		ONE_DAY_BEFORE = oNE_DAY_BEFORE;
	}
	public String getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public int getEndRow() {
		return endRow;
	}
	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}
	public String getDELI_DIVN_CD() {
		return DELI_DIVN_CD;
	}
	public void setDELI_DIVN_CD(String dELI_DIVN_CD) {
		DELI_DIVN_CD = dELI_DIVN_CD;
	}
	public String getDO_MODIFY() {
		return DO_MODIFY;
	}
	public void setDO_MODIFY(String dO_MODIFY) {
		DO_MODIFY = dO_MODIFY;
	}
	public String getDO_DELETE() {
		return DO_DELETE;
	}
	public void setDO_DELETE(String dO_DELETE) {
		DO_DELETE = dO_DELETE;
	}
	public String getDELI_DIVN_NM() {
		return DELI_DIVN_NM;
	}
	public void setDELI_DIVN_NM(String dELI_DIVN_NM) {
		DELI_DIVN_NM = dELI_DIVN_NM;
	}
	public String getRTN_AMT() {
		return RTN_AMT;
	}
	public void setRTN_AMT(String rTN_AMT) {
		RTN_AMT = rTN_AMT;
	}
	public String getEXCH_AMT() {
		return EXCH_AMT;
	}
	public void setEXCH_AMT(String eXCH_AMT) {
		EXCH_AMT = eXCH_AMT;
	}
	public String getADD_DELI_AMT1() {
		return ADD_DELI_AMT1;
	}
	public void setADD_DELI_AMT1(String aDD_DELI_AMT1) {
		ADD_DELI_AMT1 = aDD_DELI_AMT1;
	}
	public String getADD_DELI_AMT2() {
		return ADD_DELI_AMT2;
	}
	public void setADD_DELI_AMT2(String aDD_DELI_AMT2) {
		ADD_DELI_AMT2 = aDD_DELI_AMT2;
	}
	public String getBASE_DLV_AMT() {
		return BASE_DLV_AMT;
	}
	public void setBASE_DLV_AMT(String bASE_DLV_AMT) {
		BASE_DLV_AMT = bASE_DLV_AMT;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
}
