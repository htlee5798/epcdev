package com.lottemart.common.mail.model;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class LteMailVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7305889628265150789L;
	
	public String MID;
	public String SUBID;
	public String TID;
	public String SNAME;
	public String SMAIL;
	public String SID;
	public String RPOS;
	public String QUERY;
	public String CTNPOS;
	public String SUBJECT;
	public String CONTENTS;
	public String CDATE;
	public String SDATE;
	public String STATUS;
	public String DBCODE;
	public String CHARSET;
	public String ISSECURE;
	public String SECURETEMPLATE;
	public String ATTACHFILE01;
	public String ATTACHFILE02;
	public String ATTACHFILE03;
	public String ATTACHFILE04;
	public String ATTACHFILE05;
	public String RID;
	public String RNAME;
	public String RMAIL;
	public String TNAME;
	public String TDESC;
	public String SENDNAME;
	public String SENDEMAIL;
	public String TEMPLATE_USE;
	public String CDATE_S;
	public String CDATE_E;
	public String SDATE_S;
	public String SDATE_E;
	
	/**paging*/
	public String START;
	public String END;
	public String ROW;
	
	/**for Templeate**/
	public String PARAMS;

	//템플릿 이용을 위한 추가
	private String	mailTemplate;
	private File templateFile;
	private Map<String, String> bindMap = new HashMap<String, String>();
	
	
	public String getMID() {
		return MID;
	}
	public void setMID(String mID) {
		MID = mID;
	}
	public String getSUBID() {
		return SUBID;
	}
	public void setSUBID(String sUBID) {
		SUBID = sUBID;
	}
	public String getTID() {
		return TID;
	}
	public void setTID(String tID) {
		TID = tID;
	}
	public String getSNAME() {
		return SNAME;
	}
	public void setSNAME(String sNAME) {
		SNAME = sNAME;
	}
	public String getSMAIL() {
		return SMAIL;
	}
	public void setSMAIL(String sMAIL) {
		SMAIL = sMAIL;
	}
	public String getSID() {
		return SID;
	}
	public void setSID(String sID) {
		SID = sID;
	}
	public String getRPOS() {
		return RPOS;
	}
	public void setRPOS(String rPOS) {
		RPOS = rPOS;
	}
	public String getQUERY() {
		return QUERY;
	}
	public void setQUERY(String qUERY) {
		QUERY = qUERY;
	}
	public String getCTNPOS() {
		return CTNPOS;
	}
	public void setCTNPOS(String cTNPOS) {
		CTNPOS = cTNPOS;
	}
	public String getSUBJECT() {
		return SUBJECT;
	}
	public void setSUBJECT(String sUBJECT) {
		SUBJECT = sUBJECT;
	}
	public String getCONTENTS() {
		return CONTENTS;
	}
	public void setCONTENTS(String cONTENTS) {
		CONTENTS = cONTENTS;
	}
	public String getCDATE() {
		return CDATE;
	}
	public void setCDATE(String cDATE) {
		CDATE = cDATE;
	}
	public String getSDATE() {
		return SDATE;
	}
	public void setSDATE(String sDATE) {
		SDATE = sDATE;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public String getDBCODE() {
		return DBCODE;
	}
	public void setDBCODE(String dBCODE) {
		DBCODE = dBCODE;
	}
	public String getCHARSET() {
		return CHARSET;
	}
	public void setCHARSET(String cHARSET) {
		CHARSET = cHARSET;
	}
	public String getISSECURE() {
		return ISSECURE;
	}
	public void setISSECURE(String iSSECURE) {
		ISSECURE = iSSECURE;
	}
	public String getSECURETEMPLATE() {
		return SECURETEMPLATE;
	}
	public void setSECURETEMPLATE(String sECURETEMPLATE) {
		SECURETEMPLATE = sECURETEMPLATE;
	}
	public String getATTACHFILE01() {
		return ATTACHFILE01;
	}
	public void setATTACHFILE01(String aTTACHFILE01) {
		ATTACHFILE01 = aTTACHFILE01;
	}
	public String getATTACHFILE02() {
		return ATTACHFILE02;
	}
	public void setATTACHFILE02(String aTTACHFILE02) {
		ATTACHFILE02 = aTTACHFILE02;
	}
	public String getATTACHFILE03() {
		return ATTACHFILE03;
	}
	public void setATTACHFILE03(String aTTACHFILE03) {
		ATTACHFILE03 = aTTACHFILE03;
	}
	public String getATTACHFILE04() {
		return ATTACHFILE04;
	}
	public void setATTACHFILE04(String aTTACHFILE04) {
		ATTACHFILE04 = aTTACHFILE04;
	}
	public String getATTACHFILE05() {
		return ATTACHFILE05;
	}
	public void setATTACHFILE05(String aTTACHFILE05) {
		ATTACHFILE05 = aTTACHFILE05;
	}
	public String getRID() {
		return RID;
	}
	public void setRID(String rID) {
		RID = rID;
	}
	public String getRNAME() {
		return RNAME;
	}
	public void setRNAME(String rNAME) {
		RNAME = rNAME;
	}
	public String getRMAIL() {
		return RMAIL;
	}
	public void setRMAIL(String rMAIL) {
		RMAIL = rMAIL;
	}
	public String getTNAME() {
		return TNAME;
	}
	public void setTNAME(String tNAME) {
		TNAME = tNAME;
	}
	public String getTDESC() {
		return TDESC;
	}
	public void setTDESC(String tDESC) {
		TDESC = tDESC;
	}
	public String getSENDNAME() {
		return SENDNAME;
	}
	public void setSENDNAME(String sENDNAME) {
		SENDNAME = sENDNAME;
	}
	public String getSENDEMAIL() {
		return SENDEMAIL;
	}
	public void setSENDEMAIL(String sENDEMAIL) {
		SENDEMAIL = sENDEMAIL;
	}
	public String getTEMPLATE_USE() {
		return TEMPLATE_USE;
	}
	public void setTEMPLATE_USE(String tEMPLATE_USE) {
		TEMPLATE_USE = tEMPLATE_USE;
	}
	public String getPARAMS() {
		return PARAMS;
	}
	public void setPARAMS(String pARAMS) {
		PARAMS = pARAMS;
	}
	public String getCDATE_S() {
		return CDATE_S;
	}
	public void setCDATE_S(String cDATE_S) {
		CDATE_S = cDATE_S;
	}
	public String getCDATE_E() {
		return CDATE_E;
	}
	public void setCDATE_E(String cDATE_E) {
		CDATE_E = cDATE_E;
	}
	public String getSDATE_S() {
		return SDATE_S;
	}
	public void setSDATE_S(String sDATE_S) {
		SDATE_S = sDATE_S;
	}
	public String getSDATE_E() {
		return SDATE_E;
	}
	public void setSDATE_E(String sDATE_E) {
		SDATE_E = sDATE_E;
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
	public String getROW() {
		return ROW;
	}
	public void setROW(String rOW) {
		ROW = rOW;
	}
	
	public File getTemplateFile() {
		return templateFile;
	}
	public void setTemplateFile(File templateFile) {
		this.templateFile = templateFile;
	}
	public Map<String, String> getBindMap() {
		return bindMap;
	}
	public void setBindMap(Map<String, String> bindMap) {
		this.bindMap = bindMap;
	}
	public String getMailTemplate() {
		return mailTemplate;
	}
	public void setMailTemplate(String mailTemplate) {
		this.mailTemplate = mailTemplate;
	}

}
