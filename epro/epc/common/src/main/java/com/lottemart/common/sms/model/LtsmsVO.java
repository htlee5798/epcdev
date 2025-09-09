package com.lottemart.common.sms.model;

import java.io.Serializable;

public class LtsmsVO implements Serializable{
	private static final long serialVersionUID = -1171635896691326388L;

	public String NUM;
	public String SERIALNO;
	public String DESTCALLNO;
	public String CALLBACKNO;
	public String TYPE;
	public String DATA;
	public String JEOBSU_TIME;
	public String YEYAK_TIME;
	public String START_TIME;
	public String END_TIME;
	public String RESULT;
	public String RESERVED;
	public String SUBJECT;
	public String USER_NO;
	public String COMPANY_NO;
	public String JEOBSU_NO;
	public String JEOBSU_SUB_NO;
	public String USER_NM;
	public String START;
	public String END;
	
	//list
	public String FROM_JEOBSU_TIME;
	public String TO_JEOBSU_TIME;
	public String FROM_YEYAK_TIME;
	public String TO_YEYAK_TIME;
	
	
	public String getNUM(){
		return NUM;
	}
	public void setNUM(String NUM){
		this.NUM = NUM;
	}
	public String getSERIALNO() {
		return SERIALNO;
	}
	public void setSERIALNO(String sERIALNO) {
		SERIALNO = sERIALNO;
	}
	public String getDESTCALLNO() {
		return DESTCALLNO;
	}
	public void setDESTCALLNO(String dESTCALLNO) {
		DESTCALLNO = dESTCALLNO;
	}
	public String getCALLBACKNO() {
		return CALLBACKNO;
	}
	public void setCALLBACKNO(String cALLBACKNO) {
		CALLBACKNO = cALLBACKNO;
	}
	public String getTYPE() {
		return TYPE;
	}
	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}
	public String getDATA() {
		return DATA;
	}
	public void setDATA(String dATA) {
		DATA = dATA;
	}
	public String getJEOBSU_TIME() {
		return JEOBSU_TIME;
	}
	public void setJEOBSU_TIME(String jEOBSU_TIME) {
		JEOBSU_TIME = jEOBSU_TIME;
	}
	public String getYEYAK_TIME() {
		return YEYAK_TIME;
	}
	public void setYEYAK_TIME(String yEYAK_TIME) {
		YEYAK_TIME = yEYAK_TIME;
	}
	public String getSTART_TIME() {
		return START_TIME;
	}
	public void setSTART_TIME(String sTART_TIME) {
		START_TIME = sTART_TIME;
	}
	public String getEND_TIME() {
		return END_TIME;
	}
	public void setEND_TIME(String eND_TIME) {
		END_TIME = eND_TIME;
	}
	public String getRESULT() {
		return RESULT;
	}
	public void setRESULT(String rESULT) {
		RESULT = rESULT;
	}
	public String getRESERVED() {
		return RESERVED;
	}
	public void setRESERVED(String rESERVED) {
		RESERVED = rESERVED;
	}
	public String getSUBJECT() {
		return SUBJECT;
	}
	public void setSUBJECT(String sUBJECT) {
		SUBJECT = sUBJECT;
	}
	public String getUSER_NO() {
		return USER_NO;
	}
	public void setUSER_NO(String uSER_NO) {
		USER_NO = uSER_NO;
	}
	public String getCOMPANY_NO() {
		return COMPANY_NO;
	}
	public void setCOMPANY_NO(String cOMPANY_NO) {
		COMPANY_NO = cOMPANY_NO;
	}
	public String getJEOBSU_NO() {
		return JEOBSU_NO;
	}
	public void setJEOBSU_NO(String jEOBSU_NO) {
		JEOBSU_NO = jEOBSU_NO;
	}
	public String getJEOBSU_SUB_NO() {
		return JEOBSU_SUB_NO;
	}
	public void setJEOBSU_SUB_NO(String jEOBSU_SUB_NO) {
		JEOBSU_SUB_NO = jEOBSU_SUB_NO;
	}
	public String getUSER_NM() {
		return USER_NM;
	}
	public void setUSER_NM(String uSER_NM) {
		USER_NM = uSER_NM;
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
	public String getFROM_JEOBSU_TIME() {
		return FROM_JEOBSU_TIME;
	}
	public void setFROM_JEOBSU_TIME(String fROM_JEOBSU_TIME) {
		FROM_JEOBSU_TIME = fROM_JEOBSU_TIME;
	}
	public String getTO_JEOBSU_TIME() {
		return TO_JEOBSU_TIME;
	}
	public void setTO_JEOBSU_TIME(String tO_JEOBSU_TIME) {
		TO_JEOBSU_TIME = tO_JEOBSU_TIME;
	}
	public String getFROM_YEYAK_TIME() {
		return FROM_YEYAK_TIME;
	}
	public void setFROM_YEYAK_TIME(String fROM_YEYAK_TIME) {
		FROM_YEYAK_TIME = fROM_YEYAK_TIME;
	}
	public String getTO_YEYAK_TIME() {
		return TO_YEYAK_TIME;
	}
	public void setTO_YEYAK_TIME(String tO_YEYAK_TIME) {
		TO_YEYAK_TIME = tO_YEYAK_TIME;
	}
	

}
