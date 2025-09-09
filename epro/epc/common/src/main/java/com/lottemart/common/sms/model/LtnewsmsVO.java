package com.lottemart.common.sms.model;

import java.io.Serializable;

public class LtnewsmsVO implements Serializable {

	/**
	 * @see
	 * @FieldName : long
	 * @FieldType : LtnewsmsVO.java
	 */
	private static final long serialVersionUID = -7200304633680333924L;

	private String TRAN_PR;
	private String TRAN_REFKEY;
	private String TRAN_ID;
	private String TRAN_PHONE;
	private String TRAN_CALLBACK;
	private String TRAN_STATUS;
	private String TRAN_DATE;
	private String TRAN_RSLTDATE;
	private String TRAN_REPORTDATE;
	private String TRAN_RSLT;
	private String TRAN_NET;
	private String TRAN_TITLE;	// 2016-02-26 김학수 추가(LMS)
	private String TRAN_MSG;
	private String TRAN_MSG2;  // 2017-05-24 CC 이효근(lottmart.lhg@lotte.net) 추가
	private String TRAN_ETC1;
	private String TRAN_ETC2;
	private String TRAN_ETC3;
	private String TRAN_ETC4;
	private String TRAN_TYPE;
	private String START;
	private String END;
	private String TABLE_NAME;  /*EM_LOG_yyyyMM*/
	private String NUM; 		// 2011-12-22 CC  윤해성(yhs8462@lotte.net)  추가
	private String ADMIN_NM; 	// 2011-12-22 CC  윤해성(yhs8462@lotte.net)  추가
	private String LOGIN_ID; 	// 2011-12-22 CC  윤해성(yhs8462@lotte.net)  추가
	private String START_DATE; 	// 2011-12-22 CC  윤해성(yhs8462@lotte.net)  추가
	private String END_DATE; 	// 2011-12-22 CC  윤해성(yhs8462@lotte.net)  추가
	private String START_DATE2; // 2012-02-01 BOS 임재유(jaeyuLim@lotte.net) 추가
	private String END_DATE2; 	// 2012-02-01 BOS 임재유(jaeyuLim@lotte.net) 추가
	private String TABLE_NAME2; // 2012-02-01 BOS 임재유(jaeyuLim@lotte.net) 추가
	private String MMS_SEQ; // 2017-05-24 CC 이효근(lottmart.lhg@lotte.net) 추가
	private String HOUR10; // 2018-06-27 CC 이한우(mg193@lottemart.com) 추가
	private String mtPr; //2018-08-03 김연민 추가(알림톡 발송 번호)

	//lms, mms 용
	private String FILE_CNT; //2018-08-29 이종일
	private String MMS_BODY; //2018-08-29 이종일
	private String FILE_TYPE1; //2018-08-29 이종일
	private String FILE_NAME1; //2018-08-29 이종일
	private String SERVICE_DEP1; //2018-08-29 이종일
	
	public String getFILE_CNT() {
		return FILE_CNT;
	}

	public void setFILE_CNT(String fILE_CNT) {
		FILE_CNT = fILE_CNT;
	}

	public String getMMS_BODY() {
		return MMS_BODY;
	}

	public void setMMS_BODY(String mMS_BODY) {
		MMS_BODY = mMS_BODY;
	}

	public String getFILE_TYPE1() {
		return FILE_TYPE1;
	}

	public void setFILE_TYPE1(String fILE_TYPE1) {
		FILE_TYPE1 = fILE_TYPE1;
	}

	public String getFILE_NAME1() {
		return FILE_NAME1;
	}

	public void setFILE_NAME1(String fILE_NAME1) {
		FILE_NAME1 = fILE_NAME1;
	}

	public String getSERVICE_DEP1() {
		return SERVICE_DEP1;
	}

	public void setSERVICE_DEP1(String sERVICE_DEP1) {
		SERVICE_DEP1 = sERVICE_DEP1;
	}

	public String getSTART_DATE() {
		return START_DATE;
	}

	public void setSTART_DATE(String sTART_DATE) {
		START_DATE = sTART_DATE;
	}
	

	public String getSTART_DATE2() {
		return START_DATE2;
	}

	public void setSTART_DATE2(String sTART_DATE2) {
		START_DATE2 = sTART_DATE2;
	}

	public String getEND_DATE2() {
		return END_DATE2;
	}

	public void setEND_DATE2(String eND_DATE2) {
		END_DATE2 = eND_DATE2;
	}

	public String getEND_DATE() {
		return END_DATE;
	}

	public void setEND_DATE(String eND_DATE) {
		END_DATE = eND_DATE;
	}

	public String getADMIN_NM() {
		return ADMIN_NM;
	}

	public void setADMIN_NM(String aDMIN_NM) {
		ADMIN_NM = aDMIN_NM;
	}

	public String getLOGIN_ID() {
		return LOGIN_ID;
	}

	public void setLOGIN_ID(String lOGIN_ID) {
		LOGIN_ID = lOGIN_ID;
	}

	public String getNUM() {
		return NUM;
	}

	public void setNUM(String nUM) {
		NUM = nUM;
	}

	public String getTRAN_PR() {
		return TRAN_PR;
	}

	public void setTRAN_PR(String tRAN_PR) {
		TRAN_PR = tRAN_PR;
	}

	public String getTRAN_REFKEY() {
		return TRAN_REFKEY;
	}

	public void setTRAN_REFKEY(String tRAN_REFKEY) {
		TRAN_REFKEY = tRAN_REFKEY;
	}

	public String getTRAN_ID() {
		return TRAN_ID;
	}

	public void setTRAN_ID(String tRAN_ID) {
		TRAN_ID = tRAN_ID;
	}

	public String getTRAN_PHONE() {
		return TRAN_PHONE;
	}

	public void setTRAN_PHONE(String tRAN_PHONE) {
		TRAN_PHONE = tRAN_PHONE;
	}

	public String getTRAN_CALLBACK() {
		return TRAN_CALLBACK;
	}

	public void setTRAN_CALLBACK(String tRAN_CALLBACK) {
		TRAN_CALLBACK = tRAN_CALLBACK;
	}

	public String getTRAN_STATUS() {
		return TRAN_STATUS;
	}

	public void setTRAN_STATUS(String tRAN_STATUS) {
		TRAN_STATUS = tRAN_STATUS;
	}

	public String getTRAN_DATE() {
		return TRAN_DATE;
	}

	public void setTRAN_DATE(String tRAN_DATE) {
		TRAN_DATE = tRAN_DATE;
	}

	public String getTRAN_RSLTDATE() {
		return TRAN_RSLTDATE;
	}

	public void setTRAN_RSLTDATE(String tRAN_RSLTDATE) {
		TRAN_RSLTDATE = tRAN_RSLTDATE;
	}

	public String getTRAN_REPORTDATE() {
		return TRAN_REPORTDATE;
	}

	public void setTRAN_REPORTDATE(String tRAN_REPORTDATE) {
		TRAN_REPORTDATE = tRAN_REPORTDATE;
	}

	public String getTRAN_RSLT() {
		return TRAN_RSLT;
	}

	public void setTRAN_RSLT(String tRAN_RSLT) {
		TRAN_RSLT = tRAN_RSLT;
	}

	public String getTRAN_NET() {
		return TRAN_NET;
	}

	public void setTRAN_NET(String tRAN_NET) {
		TRAN_NET = tRAN_NET;
	}

	public String getTRAN_MSG() {
		return TRAN_MSG;
	}

	public void setTRAN_MSG(String tRAN_MSG) {
		TRAN_MSG = tRAN_MSG;
	}

	public String getTRAN_ETC1() {
		return TRAN_ETC1;
	}

	public void setTRAN_ETC1(String tRAN_ETC1) {
		TRAN_ETC1 = tRAN_ETC1;
	}

	public String getTRAN_ETC2() {
		return TRAN_ETC2;
	}

	public void setTRAN_ETC2(String tRAN_ETC2) {
		TRAN_ETC2 = tRAN_ETC2;
	}

	public String getTRAN_ETC3() {
		return TRAN_ETC3;
	}

	public void setTRAN_ETC3(String tRAN_ETC3) {
		TRAN_ETC3 = tRAN_ETC3;
	}

	public String getTRAN_ETC4() {
		return TRAN_ETC4;
	}

	public void setTRAN_ETC4(String tRAN_ETC4) {
		TRAN_ETC4 = tRAN_ETC4;
	}

	public String getTRAN_TYPE() {
		return TRAN_TYPE;
	}

	public void setTRAN_TYPE(String tRAN_TYPE) {
		TRAN_TYPE = tRAN_TYPE;
	}

	public String getSTART() {
		return START;
	}

	public void setSTART(String sTART) {
		START = sTART;
	}

	public String getEND() {
		return END;
	}

	public void setEND(String eND) {
		END = eND;
	}

	public String getTABLE_NAME() {
		return TABLE_NAME;
	}

	public void setTABLE_NAME(String tABLE_NAME) {
		TABLE_NAME = tABLE_NAME;
	}
	
	public String getTABLE_NAME2() {
		return TABLE_NAME2;
	}

	public void setTABLE_NAME2(String tABLE_NAME2) {
		TABLE_NAME2 = tABLE_NAME2;
	}

	public String getTRAN_TITLE() {
		return TRAN_TITLE;
	}

	public void setTRAN_TITLE(String tRAN_TITLE) {
		TRAN_TITLE = tRAN_TITLE;
	}

	public String getMMS_SEQ() {
		return MMS_SEQ;
	}

	public void setMMS_SEQ(String mMS_SEQ) {
		MMS_SEQ = mMS_SEQ;
	}

	public String getTRAN_MSG2() {
		return TRAN_MSG2;
	}

	public void setTRAN_MSG2(String tRAN_MSG2) {
		TRAN_MSG2 = tRAN_MSG2;
	}
	
	public String getHOUR10() {
		return HOUR10;
	}

	public void setHOUR10(String hOUR10) {
		HOUR10 = hOUR10;
	}

	public String getMtPr() {
		return mtPr;
	}

	public void setMtPr(String mtPr) {
		this.mtPr = mtPr;
	}
	
}
