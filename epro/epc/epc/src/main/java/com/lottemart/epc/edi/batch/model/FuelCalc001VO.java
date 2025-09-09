package com.lottemart.epc.edi.batch.model;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

	public class FuelCalc001VO implements Serializable {
	
		

		/**
		 * 
		 */
		private static final long serialVersionUID = -6867330578809776565L;


		
		private String sendXml = "";
		private String sendXmlSrid = "";
		private String sendXmlRetcd = "";
		private String sendXmlShopCd = "";
		private String sendXmlSaleDate = "";
		private String sendXmlPosNo = "";
		private String sendXmlRegiSeq = "";
		private String sendXmlIndexNo = "";
		private String sendXmlDtCnt = "";
		private String sendXmlShopCd1 = "";
		private String sendXmlSaleDate1 = "";
		private String sendXmlPosNo1 = "";
		private String sendXmlRegiSeq1 = "";
		private String sendXmlEmpNo = "";
		private String sendXmlCloseFg = "";
		private String sendXmlOpenDt = "";
		private String sendXmlCloseDt = "";
		private String sendXmlTotBillCnt = "";
		private String sendXmlTotSaleAmt = "";
		private String sendXmlTotDcAmt = "";
		private String sendXmlSvcTipAmt = "";
		private String sendXmlTotEtcAmt = "";
		private String sendXmlDcmSaleAmt = "";
		private String sendXmlVatSaleAmt = "";
		private String sendXmlVatAmt = "";
		private String sendXmlNoVatSaleAmt = "";
		private String sendXmlNoTaxSaleAmt = "";
		private String sendXmlRetBillCnt = "";
		private String sendXmlRetBillAmt = "";
		private String sendXmlVisitCstCnt = "";
		private String sendXmlPosReadyAmt = "";
		private String sendXmlPosCshInAmt = "";
		private String sendXmlPosCshOutAmt = "";
		private String sendXmlWeaInCshAmt = "";
		private String sendXmlWeaInCrdAmt = "";
		private String sendXmlTkGftSaleCshAmt = "";
		private String sendXmlTkGftSaleCrdAmt = "";
		private String sendXmlTkFodSaleCshAmt = "";
		private String sendXmlTkFodSaleCrdAmt = "";
		private String sendXmlCashCnt = "";
		private String sendXmlCashAmt = "";
		private String sendXmlCashBillCnt = "";
		private String sendXmlCashBillAmt = "";
		private String sendXmlCrdCardCnt = "";
		private String sendXmlCrdCardAmt = "";
		private String sendXmlWesCnt = "";
		private String sendXmlWesAmt = "";
		private String sendXmlTkGftCnt = "";
		private String sendXmlTkGftAmt = "";
		private String sendXmlTkFodCnt = "";
		private String sendXmlTkFodAmt = "";
		private String sendXmlCstPointCnt = "";
		private String sendXmlCstPointAmt = "";
		private String sendXmlJcdCardCnt = "";
		private String sendXmlJcdCardAmt = "";
		private String sendXmlRfcCnt = "";
		private String sendXmlRfcAmt = "";
		private String sendXmlDcGenCnt = "";
		private String sendXmlDcGenAmt = "";
		private String sendXmlDcSvcCnt = "";
		private String sendXmlDcSvcAmt = "";
		private String sendXmlDcJcdCnt = "";
		private String sendXmlDcJcdAmt = "";
		private String sendXmlDcCpnCnt = "";
		private String sendXmlDcCpnAmt = "";
		private String sendXmlDcCstCnt = "";
		private String sendXmlDcCstAmt = "";
		private String sendXmlDcTfdCnt = "";
		private String sendXmlDcTfdAmt = "";
		private String sendXmlDcPrmCnt = "";
		private String sendXmlDcPrmAmt = "";
		private String sendXmlDcCrdCnt = "";
		private String sendXmlDcCrdAmt = "";
		private String sendXmlDcPackCnt = "";
		private String sendXmlDcPackAmt = "";
		private String sendXmlRemCheckCnt = "";
		private String sendXmlRemCheckAmt = "";
		private String sendXmlRemW100000Cnt = "";
		private String sendXmlRemW50000Cnt = "";
		private String sendXmlRemW10000Cnt = "";
		private String sendXmlRemW5000Cnt = "";
		private String sendXmlRemW1000Cnt = "";
		private String sendXmlRemW500Cnt = "";
		private String sendXmlRemW100Cnt = "";
		private String sendXmlRemW50Cnt = "";
		private String sendXmlRemW10Cnt = "";
		private String sendXmlRemCashAmt = "";
		private String sendXmlRemTkGftCnt = "";
		private String sendXmlRemTkGftAmt = "";
		private String sendXmlRemTkFodCnt = "";
		private String sendXmlRemTkFodAmt = "";
		private String sendXmlEtcTkFodAmt = "";
		private String sendXmlLossCashAmt = "";
		private String sendXmlLossTkGftAmt = "";
		private String sendXmlLossTkFodAmt = "";
		private String sendXmlRepayCashCnt = "";
		private String sendXmlRepayCashAmt = "";
		private String sendXmlRepayTkGftCnt = "";
		private String sendXmlRepayTkGftAmt = "";
		private String sendXmlComCnt = "";
		private String sendXmlComAmt = "";
		private String sendXmlPrepayCnt = "";
		private String sendXmlPrepayAmt = "";
		private String sendXmlInsDt = "";
		private String sendXmlBillNoEnd = "";

		public String getSendXml() {
			return sendXml;
		}
		public void setSendXml(String sendXml) {
			this.sendXml = sendXml;
		}
		
		public String getSendXmlSrid() {
			return StringUtils.defaultString(sendXmlSrid, "");			
		}
		public void setSendXmlSrid(String sendXmlSrid) {
			this.sendXmlSrid = sendXmlSrid;
		}
		public String getSendXmlRetcd() {
			return StringUtils.defaultString(sendXmlRetcd, "");
		}
		public void setSendXmlRetcd(String sendXmlRetcd) {
			this.sendXmlRetcd = sendXmlRetcd;
		}
		public String getSendXmlShopCd() {
			return StringUtils.defaultString(sendXmlShopCd, "");
		}
		public void setSendXmlShopCd(String sendXmlShopCd) {
			this.sendXmlShopCd = sendXmlShopCd;
		}
		public String getSendXmlSaleDate() {
			return StringUtils.defaultString(sendXmlSaleDate, "");
		}
		public void setSendXmlSaleDate(String sendXmlSaleDate) {
			this.sendXmlSaleDate = sendXmlSaleDate;
		}
		public String getSendXmlPosNo() {
			return StringUtils.defaultString(sendXmlPosNo, "");
		}
		public void setSendXmlPosNo(String sendXmlPosNo) {
			this.sendXmlPosNo = sendXmlPosNo;
		}
		public String getSendXmlRegiSeq() {
			return StringUtils.defaultString(sendXmlRegiSeq, "");
		}
		public void setSendXmlRegiSeq(String sendXmlRegiSeq) {
			this.sendXmlRegiSeq = sendXmlRegiSeq;
		}
		public String getSendXmlIndexNo() {
			return StringUtils.defaultString(sendXmlIndexNo, "");
		}
		public void setSendXmlIndexNo(String sendXmlIndexNo) {
			this.sendXmlIndexNo = sendXmlIndexNo;
		}
		public String getSendXmlDtCnt() {
			return StringUtils.defaultString(sendXmlDtCnt, "");
		}
		public void setSendXmlDtCnt(String sendXmlDtCnt) {
			this.sendXmlDtCnt = sendXmlDtCnt;
		}
		
		public String getSendXmlShopCd1() {
			return StringUtils.defaultString(sendXmlShopCd1, "");
		}
		public void setSendXmlShopCd1(String sendXmlShopCd1) {
			this.sendXmlShopCd1 = sendXmlShopCd1;
		}
		public String getSendXmlSaleDate1() {
			return StringUtils.defaultString(sendXmlSaleDate1, "");
		}
		public void setSendXmlSaleDate1(String sendXmlSaleDate1) {
			this.sendXmlSaleDate1 = sendXmlSaleDate1;
		}
		public String getSendXmlPosNo1() {
			return StringUtils.defaultString(sendXmlPosNo1, "");
		}
		public void setSendXmlPosNo1(String sendXmlPosNo1) {
			this.sendXmlPosNo1 = sendXmlPosNo1;
		}
		public String getSendXmlRegiSeq1() {
			return StringUtils.defaultString(sendXmlRegiSeq1, "");
		}
		public void setSendXmlRegiSeq1(String sendXmlRegiSeq1) {
			this.sendXmlRegiSeq1 = sendXmlRegiSeq1;
		}
		public String getSendXmlEmpNo() {
			return StringUtils.defaultString(sendXmlEmpNo, "");
		}
		public void setSendXmlEmpNo(String sendXmlEmpNo) {
			this.sendXmlEmpNo = sendXmlEmpNo;
		}
		public String getSendXmlCloseFg() {
			return StringUtils.defaultString(sendXmlCloseFg, "");
		}
		public void setSendXmlCloseFg(String sendXmlCloseFg) {
			this.sendXmlCloseFg = sendXmlCloseFg;
		}
		public String getSendXmlOpenDt() {
			return StringUtils.defaultString(sendXmlOpenDt, "");
		}
		public void setSendXmlOpenDt(String sendXmlOpenDt) {
			this.sendXmlOpenDt = sendXmlOpenDt;
		}
		public String getSendXmlCloseDt() {
			return StringUtils.defaultString(sendXmlCloseDt, "");
		}
		public void setSendXmlCloseDt(String sendXmlCloseDt) {
			this.sendXmlCloseDt = sendXmlCloseDt;
		}
		public String getSendXmlTotBillCnt() {
			return StringUtils.defaultString(sendXmlTotBillCnt, "");
		}
		public void setSendXmlTotBillCnt(String sendXmlTotBillCnt) {
			this.sendXmlTotBillCnt = sendXmlTotBillCnt;
		}
		public String getSendXmlTotSaleAmt() {
			return StringUtils.defaultString(sendXmlTotSaleAmt, "");
		}
		public void setSendXmlTotSaleAmt(String sendXmlTotSaleAmt) {
			this.sendXmlTotSaleAmt = sendXmlTotSaleAmt;
		}
		public String getSendXmlTotDcAmt() {
			return StringUtils.defaultString(sendXmlTotDcAmt, "");
		}
		public void setSendXmlTotDcAmt(String sendXmlTotDcAmt) {
			this.sendXmlTotDcAmt = sendXmlTotDcAmt;
		}
		public String getSendXmlSvcTipAmt() {
			return StringUtils.defaultString(sendXmlSvcTipAmt, "");
		}
		public void setSendXmlSvcTipAmt(String sendXmlSvcTipAmt) {
			this.sendXmlSvcTipAmt = sendXmlSvcTipAmt;
		}
		public String getSendXmlTotEtcAmt() {
			return StringUtils.defaultString(sendXmlTotEtcAmt, "");
		}
		public void setSendXmlTotEtcAmt(String sendXmlTotEtcAmt) {
			this.sendXmlTotEtcAmt = sendXmlTotEtcAmt;
		}
		public String getSendXmlDcmSaleAmt() {
			return StringUtils.defaultString(sendXmlDcmSaleAmt, "");
		}
		public void setSendXmlDcmSaleAmt(String sendXmlDcmSaleAmt) {
			this.sendXmlDcmSaleAmt = sendXmlDcmSaleAmt;
		}
		public String getSendXmlVatSaleAmt() {
			return StringUtils.defaultString(sendXmlVatSaleAmt, "");
		}
		public void setSendXmlVatSaleAmt(String sendXmlVatSaleAmt) {
			this.sendXmlVatSaleAmt = sendXmlVatSaleAmt;
		}
		public String getSendXmlVatAmt() {
			return StringUtils.defaultString(sendXmlVatAmt, "");
		}
		public void setSendXmlVatAmt(String sendXmlVatAmt) {
			this.sendXmlVatAmt = sendXmlVatAmt;
		}
		public String getSendXmlNoVatSaleAmt() {
			return StringUtils.defaultString(sendXmlNoVatSaleAmt, "");
		}
		public void setSendXmlNoVatSaleAmt(String sendXmlNoVatSaleAmt) {
			this.sendXmlNoVatSaleAmt = sendXmlNoVatSaleAmt;
		}
		public String getSendXmlNoTaxSaleAmt() {
			return StringUtils.defaultString(sendXmlNoTaxSaleAmt, "");
		}
		public void setSendXmlNoTaxSaleAmt(String sendXmlNoTaxSaleAmt) {
			this.sendXmlNoTaxSaleAmt = sendXmlNoTaxSaleAmt;
		}
		public String getSendXmlRetBillCnt() {
			return StringUtils.defaultString(sendXmlRetBillCnt, "");
		}
		public void setSendXmlRetBillCnt(String sendXmlRetBillCnt) {
			this.sendXmlRetBillCnt = sendXmlRetBillCnt;
		}
		public String getSendXmlRetBillAmt() {
			return StringUtils.defaultString(sendXmlRetBillAmt, "");
		}
		public void setSendXmlRetBillAmt(String sendXmlRetBillAmt) {
			this.sendXmlRetBillAmt = sendXmlRetBillAmt;
		}
		public String getSendXmlVisitCstCnt() {
			return StringUtils.defaultString(sendXmlVisitCstCnt, "");
		}
		public void setSendXmlVisitCstCnt(String sendXmlVisitCstCnt) {
			this.sendXmlVisitCstCnt = sendXmlVisitCstCnt;
		}
		public String getSendXmlPosReadyAmt() {
			return StringUtils.defaultString(sendXmlPosReadyAmt, "");
		}
		public void setSendXmlPosReadyAmt(String sendXmlPosReadyAmt) {
			this.sendXmlPosReadyAmt = sendXmlPosReadyAmt;
		}
		public String getSendXmlPosCshInAmt() {
			return StringUtils.defaultString(sendXmlPosCshInAmt, "");
		}
		public void setSendXmlPosCshInAmt(String sendXmlPosCshInAmt) {
			this.sendXmlPosCshInAmt = sendXmlPosCshInAmt;
		}
		public String getSendXmlPosCshOutAmt() {
			return StringUtils.defaultString(sendXmlPosCshOutAmt, "");
		}
		public void setSendXmlPosCshOutAmt(String sendXmlPosCshOutAmt) {
			this.sendXmlPosCshOutAmt = sendXmlPosCshOutAmt;
		}
		public String getSendXmlWeaInCshAmt() {
			return StringUtils.defaultString(sendXmlWeaInCshAmt, "");
		}
		public void setSendXmlWeaInCshAmt(String sendXmlWeaInCshAmt) {
			this.sendXmlWeaInCshAmt = sendXmlWeaInCshAmt;
		}
		public String getSendXmlWeaInCrdAmt() {
			return StringUtils.defaultString(sendXmlWeaInCrdAmt, "");
		}
		public void setSendXmlWeaInCrdAmt(String sendXmlWeaInCrdAmt) {
			this.sendXmlWeaInCrdAmt = sendXmlWeaInCrdAmt;
		}
		public String getSendXmlTkGftSaleCshAmt() {
			return StringUtils.defaultString(sendXmlTkGftSaleCshAmt, "");
		}
		public void setSendXmlTkGftSaleCshAmt(String sendXmlTkGftSaleCshAmt) {
			this.sendXmlTkGftSaleCshAmt = sendXmlTkGftSaleCshAmt;
		}
		public String getSendXmlTkGftSaleCrdAmt() {
			return StringUtils.defaultString(sendXmlTkGftSaleCrdAmt, "");
		}
		public void setSendXmlTkGftSaleCrdAmt(String sendXmlTkGftSaleCrdAmt) {
			this.sendXmlTkGftSaleCrdAmt = sendXmlTkGftSaleCrdAmt;
		}
		public String getSendXmlTkFodSaleCshAmt() {
			return StringUtils.defaultString(sendXmlTkFodSaleCshAmt, "");
		}
		public void setSendXmlTkFodSaleCshAmt(String sendXmlTkFodSaleCshAmt) {
			this.sendXmlTkFodSaleCshAmt = sendXmlTkFodSaleCshAmt;
		}
		public String getSendXmlTkFodSaleCrdAmt() {
			return StringUtils.defaultString(sendXmlTkFodSaleCrdAmt, "");
		}
		public void setSendXmlTkFodSaleCrdAmt(String sendXmlTkFodSaleCrdAmt) {
			this.sendXmlTkFodSaleCrdAmt = sendXmlTkFodSaleCrdAmt;
		}
		public String getSendXmlCashCnt() {
			return StringUtils.defaultString(sendXmlCashCnt, "");
		}
		public void setSendXmlCashCnt(String sendXmlCashCnt) {
			this.sendXmlCashCnt = sendXmlCashCnt;
		}
		public String getSendXmlCashAmt() {
			return StringUtils.defaultString(sendXmlCashAmt, "");
		}
		public void setSendXmlCashAmt(String sendXmlCashAmt) {
			this.sendXmlCashAmt = sendXmlCashAmt;
		}
		public String getSendXmlCashBillCnt() {
			return StringUtils.defaultString(sendXmlCashBillCnt, "");
		}
		public void setSendXmlCashBillCnt(String sendXmlCashBillCnt) {
			this.sendXmlCashBillCnt = sendXmlCashBillCnt;
		}
		public String getSendXmlCashBillAmt() {
			return StringUtils.defaultString(sendXmlCashBillAmt, "");
		}
		public void setSendXmlCashBillAmt(String sendXmlCashBillAmt) {
			this.sendXmlCashBillAmt = sendXmlCashBillAmt;
		}
		public String getSendXmlCrdCardCnt() {
			return StringUtils.defaultString(sendXmlCrdCardCnt, "");
		}
		public void setSendXmlCrdCardCnt(String sendXmlCrdCardCnt) {
			this.sendXmlCrdCardCnt = sendXmlCrdCardCnt;
		}
		public String getSendXmlCrdCardAmt() {
			return StringUtils.defaultString(sendXmlCrdCardAmt, "");
		}
		public void setSendXmlCrdCardAmt(String sendXmlCrdCardAmt) {
			this.sendXmlCrdCardAmt = sendXmlCrdCardAmt;
		}
		public String getSendXmlWesCnt() {
			return StringUtils.defaultString(sendXmlWesCnt, "");
		}
		public void setSendXmlWesCnt(String sendXmlWesCnt) {
			this.sendXmlWesCnt = sendXmlWesCnt;
		}
		public String getSendXmlWesAmt() {
			return StringUtils.defaultString(sendXmlWesAmt, "");
		}
		public void setSendXmlWesAmt(String sendXmlWesAmt) {
			this.sendXmlWesAmt = sendXmlWesAmt;
		}
		public String getSendXmlTkGftCnt() {
			return StringUtils.defaultString(sendXmlTkGftCnt, "");
		}
		public void setSendXmlTkGftCnt(String sendXmlTkGftCnt) {
			this.sendXmlTkGftCnt = sendXmlTkGftCnt;
		}
		public String getSendXmlTkGftAmt() {
			return StringUtils.defaultString(sendXmlTkGftAmt, "");
		}
		public void setSendXmlTkGftAmt(String sendXmlTkGftAmt) {
			this.sendXmlTkGftAmt = sendXmlTkGftAmt;
		}
		public String getSendXmlTkFodCnt() {
			return StringUtils.defaultString(sendXmlTkFodCnt, "");
		}
		public void setSendXmlTkFodCnt(String sendXmlTkFodCnt) {
			this.sendXmlTkFodCnt = sendXmlTkFodCnt;
		}
		public String getSendXmlTkFodAmt() {
			return StringUtils.defaultString(sendXmlTkFodAmt, "");
		}
		public void setSendXmlTkFodAmt(String sendXmlTkFodAmt) {
			this.sendXmlTkFodAmt = sendXmlTkFodAmt;
		}
		public String getSendXmlCstPointCnt() {
			return StringUtils.defaultString(sendXmlCstPointCnt, "");
		}
		public void setSendXmlCstPointCnt(String sendXmlCstPointCnt) {
			this.sendXmlCstPointCnt = sendXmlCstPointCnt;
		}
		public String getSendXmlCstPointAmt() {
			return StringUtils.defaultString(sendXmlCstPointAmt, "");
		}
		public void setSendXmlCstPointAmt(String sendXmlCstPointAmt) {
			this.sendXmlCstPointAmt = sendXmlCstPointAmt;
		}
		public String getSendXmlJcdCardCnt() {
			return StringUtils.defaultString(sendXmlJcdCardCnt, "");
		}
		public void setSendXmlJcdCardCnt(String sendXmlJcdCardCnt) {
			this.sendXmlJcdCardCnt = sendXmlJcdCardCnt;
		}
		public String getSendXmlJcdCardAmt() {
			return StringUtils.defaultString(sendXmlJcdCardAmt, "");
		}
		public void setSendXmlJcdCardAmt(String sendXmlJcdCardAmt) {
			this.sendXmlJcdCardAmt = sendXmlJcdCardAmt;
		}
		public String getSendXmlRfcCnt() {
			return StringUtils.defaultString(sendXmlRfcCnt, "");
		}
		public void setSendXmlRfcCnt(String sendXmlRfcCnt) {
			this.sendXmlRfcCnt = sendXmlRfcCnt;
		}
		public String getSendXmlRfcAmt() {
			return StringUtils.defaultString(sendXmlRfcAmt, "");
		}
		public void setSendXmlRfcAmt(String sendXmlRfcAmt) {
			this.sendXmlRfcAmt = sendXmlRfcAmt;
		}
		public String getSendXmlDcGenCnt() {
			return StringUtils.defaultString(sendXmlDcGenCnt, "");
		}
		public void setSendXmlDcGenCnt(String sendXmlDcGenCnt) {
			this.sendXmlDcGenCnt = sendXmlDcGenCnt;
		}
		public String getSendXmlDcGenAmt() {
			return StringUtils.defaultString(sendXmlDcGenAmt, "");
		}
		public void setSendXmlDcGenAmt(String sendXmlDcGenAmt) {
			this.sendXmlDcGenAmt = sendXmlDcGenAmt;
		}
		public String getSendXmlDcSvcCnt() {
			return StringUtils.defaultString(sendXmlDcSvcCnt, "");
		}
		public void setSendXmlDcSvcCnt(String sendXmlDcSvcCnt) {
			this.sendXmlDcSvcCnt = sendXmlDcSvcCnt;
		}
		public String getSendXmlDcSvcAmt() {
			return StringUtils.defaultString(sendXmlDcSvcAmt, "");
		}
		public void setSendXmlDcSvcAmt(String sendXmlDcSvcAmt) {
			this.sendXmlDcSvcAmt = sendXmlDcSvcAmt;
		}
		public String getSendXmlDcJcdCnt() {
			return StringUtils.defaultString(sendXmlDcJcdCnt, "");
		}
		public void setSendXmlDcJcdCnt(String sendXmlDcJcdCnt) {
			this.sendXmlDcJcdCnt = sendXmlDcJcdCnt;
		}
		public String getSendXmlDcJcdAmt() {
			return StringUtils.defaultString(sendXmlDcJcdAmt, "");
		}
		public void setSendXmlDcJcdAmt(String sendXmlDcJcdAmt) {
			this.sendXmlDcJcdAmt = sendXmlDcJcdAmt;
		}
		public String getSendXmlDcCpnCnt() {
			return StringUtils.defaultString(sendXmlDcCpnCnt, "");
		}
		public void setSendXmlDcCpnCnt(String sendXmlDcCpnCnt) {
			this.sendXmlDcCpnCnt = sendXmlDcCpnCnt;
		}
		public String getSendXmlDcCpnAmt() {
			return StringUtils.defaultString(sendXmlDcCpnAmt, "");
		}
		public void setSendXmlDcCpnAmt(String sendXmlDcCpnAmt) {
			this.sendXmlDcCpnAmt = sendXmlDcCpnAmt;
		}
		public String getSendXmlDcCstCnt() {
			return StringUtils.defaultString(sendXmlDcCstCnt, "");
		}
		public void setSendXmlDcCstCnt(String sendXmlDcCstCnt) {
			this.sendXmlDcCstCnt = sendXmlDcCstCnt;
		}
		public String getSendXmlDcCstAmt() {
			return StringUtils.defaultString(sendXmlDcCstAmt, "");
		}
		public void setSendXmlDcCstAmt(String sendXmlDcCstAmt) {
			this.sendXmlDcCstAmt = sendXmlDcCstAmt;
		}
		public String getSendXmlDcTfdCnt() {
			return StringUtils.defaultString(sendXmlDcTfdCnt, "");
		}
		public void setSendXmlDcTfdCnt(String sendXmlDcTfdCnt) {
			this.sendXmlDcTfdCnt = sendXmlDcTfdCnt;
		}
		public String getSendXmlDcTfdAmt() {
			return StringUtils.defaultString(sendXmlDcTfdAmt, "");
		}
		public void setSendXmlDcTfdAmt(String sendXmlDcTfdAmt) {
			this.sendXmlDcTfdAmt = sendXmlDcTfdAmt;
		}
		public String getSendXmlDcPrmCnt() {
			return StringUtils.defaultString(sendXmlDcPrmCnt, "");
		}
		public void setSendXmlDcPrmCnt(String sendXmlDcPrmCnt) {
			this.sendXmlDcPrmCnt = sendXmlDcPrmCnt;
		}
		public String getSendXmlDcPrmAmt() {
			return StringUtils.defaultString(sendXmlDcPrmAmt, "");
		}
		public void setSendXmlDcPrmAmt(String sendXmlDcPrmAmt) {
			this.sendXmlDcPrmAmt = sendXmlDcPrmAmt;
		}
		public String getSendXmlDcCrdCnt() {
			return StringUtils.defaultString(sendXmlDcCrdCnt, "");
		}
		public void setSendXmlDcCrdCnt(String sendXmlDcCrdCnt) {
			this.sendXmlDcCrdCnt = sendXmlDcCrdCnt;
		}
		public String getSendXmlDcCrdAmt() {
			return StringUtils.defaultString(sendXmlDcCrdAmt, "");
		}
		public void setSendXmlDcCrdAmt(String sendXmlDcCrdAmt) {
			this.sendXmlDcCrdAmt = sendXmlDcCrdAmt;
		}
		public String getSendXmlDcPackCnt() {
			return StringUtils.defaultString(sendXmlDcPackCnt, "");
		}
		public void setSendXmlDcPackCnt(String sendXmlDcPackCnt) {
			this.sendXmlDcPackCnt = sendXmlDcPackCnt;
		}
		public String getSendXmlDcPackAmt() {
			return StringUtils.defaultString(sendXmlDcPackAmt, "");
		}
		public void setSendXmlDcPackAmt(String sendXmlDcPackAmt) {
			this.sendXmlDcPackAmt = sendXmlDcPackAmt;
		}
		public String getSendXmlRemCheckCnt() {
			return StringUtils.defaultString(sendXmlRemCheckCnt, "");
		}
		public void setSendXmlRemCheckCnt(String sendXmlRemCheckCnt) {
			this.sendXmlRemCheckCnt = sendXmlRemCheckCnt;
		}
		public String getSendXmlRemCheckAmt() {
			return StringUtils.defaultString(sendXmlRemCheckAmt, "");
		}
		public void setSendXmlRemCheckAmt(String sendXmlRemCheckAmt) {
			this.sendXmlRemCheckAmt = sendXmlRemCheckAmt;
		}
		public String getSendXmlRemW100000Cnt() {
			return StringUtils.defaultString(sendXmlRemW100000Cnt, "");
		}
		public void setSendXmlRemW100000Cnt(String sendXmlRemW100000Cnt) {
			this.sendXmlRemW100000Cnt = sendXmlRemW100000Cnt;
		}
		public String getSendXmlRemW50000Cnt() {
			return StringUtils.defaultString(sendXmlRemW50000Cnt, "");
		}
		public void setSendXmlRemW50000Cnt(String sendXmlRemW50000Cnt) {
			this.sendXmlRemW50000Cnt = sendXmlRemW50000Cnt;
		}
		public String getSendXmlRemW10000Cnt() {
			return StringUtils.defaultString(sendXmlRemW10000Cnt, "");
		}
		public void setSendXmlRemW10000Cnt(String sendXmlRemW10000Cnt) {
			this.sendXmlRemW10000Cnt = sendXmlRemW10000Cnt;
		}
		public String getSendXmlRemW5000Cnt() {
			return StringUtils.defaultString(sendXmlRemW5000Cnt, "");
		}
		public void setSendXmlRemW5000Cnt(String sendXmlRemW5000Cnt) {
			this.sendXmlRemW5000Cnt = sendXmlRemW5000Cnt;
		}
		public String getSendXmlRemW1000Cnt() {
			return StringUtils.defaultString(sendXmlRemW1000Cnt, "");
		}
		public void setSendXmlRemW1000Cnt(String sendXmlRemW1000Cnt) {
			this.sendXmlRemW1000Cnt = sendXmlRemW1000Cnt;
		}
		public String getSendXmlRemW500Cnt() {
			return StringUtils.defaultString(sendXmlRemW500Cnt, "");
		}
		public void setSendXmlRemW500Cnt(String sendXmlRemW500Cnt) {
			this.sendXmlRemW500Cnt = sendXmlRemW500Cnt;
		}
		public String getSendXmlRemW100Cnt() {
			return StringUtils.defaultString(sendXmlRemW100Cnt, "");
		}
		public void setSendXmlRemW100Cnt(String sendXmlRemW100Cnt) {
			this.sendXmlRemW100Cnt = sendXmlRemW100Cnt;
		}
		public String getSendXmlRemW50Cnt() {
			return StringUtils.defaultString(sendXmlRemW50Cnt, "");
		}
		public void setSendXmlRemW50Cnt(String sendXmlRemW50Cnt) {
			this.sendXmlRemW50Cnt = sendXmlRemW50Cnt;
		}
		public String getSendXmlRemW10Cnt() {
			return StringUtils.defaultString(sendXmlRemW10Cnt, "");
		}
		public void setSendXmlRemW10Cnt(String sendXmlRemW10Cnt) {
			this.sendXmlRemW10Cnt = sendXmlRemW10Cnt;
		}
		public String getSendXmlRemCashAmt() {
			return StringUtils.defaultString(sendXmlRemCashAmt, "");
		}
		public void setSendXmlRemCashAmt(String sendXmlRemCashAmt) {
			this.sendXmlRemCashAmt = sendXmlRemCashAmt;
		}
		public String getSendXmlRemTkGftCnt() {
			return StringUtils.defaultString(sendXmlRemTkGftCnt, "");
		}
		public void setSendXmlRemTkGftCnt(String sendXmlRemTkGftCnt) {
			this.sendXmlRemTkGftCnt = sendXmlRemTkGftCnt;
		}
		public String getSendXmlRemTkGftAmt() {
			return StringUtils.defaultString(sendXmlRemTkGftAmt, "");
		}
		public void setSendXmlRemTkGftAmt(String sendXmlRemTkGftAmt) {
			this.sendXmlRemTkGftAmt = sendXmlRemTkGftAmt;
		}
		public String getSendXmlRemTkFodCnt() {
			return StringUtils.defaultString(sendXmlRemTkFodCnt, "");
		}
		public void setSendXmlRemTkFodCnt(String sendXmlRemTkFodCnt) {
			this.sendXmlRemTkFodCnt = sendXmlRemTkFodCnt;
		}
		public String getSendXmlRemTkFodAmt() {
			return StringUtils.defaultString(sendXmlRemTkFodAmt, "");
		}
		public void setSendXmlRemTkFodAmt(String sendXmlRemTkFodAmt) {
			this.sendXmlRemTkFodAmt = sendXmlRemTkFodAmt;
		}
		public String getSendXmlEtcTkFodAmt() {
			return StringUtils.defaultString(sendXmlEtcTkFodAmt, "");
		}
		public void setSendXmlEtcTkFodAmt(String sendXmlEtcTkFodAmt) {
			this.sendXmlEtcTkFodAmt = sendXmlEtcTkFodAmt;
		}
		public String getSendXmlLossCashAmt() {
			return StringUtils.defaultString(sendXmlLossCashAmt, "");
		}
		public void setSendXmlLossCashAmt(String sendXmlLossCashAmt) {
			this.sendXmlLossCashAmt = sendXmlLossCashAmt;
		}
		public String getSendXmlLossTkGftAmt() {
			return StringUtils.defaultString(sendXmlLossTkGftAmt, "");
		}
		public void setSendXmlLossTkGftAmt(String sendXmlLossTkGftAmt) {
			this.sendXmlLossTkGftAmt = sendXmlLossTkGftAmt;
		}
		public String getSendXmlLossTkFodAmt() {
			return StringUtils.defaultString(sendXmlLossTkFodAmt, "");
		}
		public void setSendXmlLossTkFodAmt(String sendXmlLossTkFodAmt) {
			this.sendXmlLossTkFodAmt = sendXmlLossTkFodAmt;
		}
		public String getSendXmlRepayCashCnt() {
			return StringUtils.defaultString(sendXmlRepayCashCnt, "");
		}
		public void setSendXmlRepayCashCnt(String sendXmlRepayCashCnt) {
			this.sendXmlRepayCashCnt = sendXmlRepayCashCnt;
		}
		public String getSendXmlRepayCashAmt() {
			return StringUtils.defaultString(sendXmlRepayCashAmt, "");
		}
		public void setSendXmlRepayCashAmt(String sendXmlRepayCashAmt) {
			this.sendXmlRepayCashAmt = sendXmlRepayCashAmt;
		}
		public String getSendXmlRepayTkGftCnt() {
			return StringUtils.defaultString(sendXmlRepayTkGftCnt, "");
		}
		public void setSendXmlRepayTkGftCnt(String sendXmlRepayTkGftCnt) {
			this.sendXmlRepayTkGftCnt = sendXmlRepayTkGftCnt;
		}
		public String getSendXmlRepayTkGftAmt() {
			return StringUtils.defaultString(sendXmlRepayTkGftAmt, "");
		}
		public void setSendXmlRepayTkGftAmt(String sendXmlRepayTkGftAmt) {
			this.sendXmlRepayTkGftAmt = sendXmlRepayTkGftAmt;
		}
		public String getSendXmlComCnt() {
			return StringUtils.defaultString(sendXmlComCnt, "");
		}
		public void setSendXmlComCnt(String sendXmlComCnt) {
			this.sendXmlComCnt = sendXmlComCnt;
		}
		public String getSendXmlComAmt() {
			return StringUtils.defaultString(sendXmlComAmt, "");
		}
		public void setSendXmlComAmt(String sendXmlComAmt) {
			this.sendXmlComAmt = sendXmlComAmt;
		}
		public String getSendXmlPrepayCnt() {
			return StringUtils.defaultString(sendXmlPrepayCnt, "");
		}
		public void setSendXmlPrepayCnt(String sendXmlPrepayCnt) {
			this.sendXmlPrepayCnt = sendXmlPrepayCnt;
		}
		public String getSendXmlPrepayAmt() {
			return StringUtils.defaultString(sendXmlPrepayAmt, "");
		}
		public void setSendXmlPrepayAmt(String sendXmlPrepayAmt) {
			this.sendXmlPrepayAmt = sendXmlPrepayAmt;
		}
		public String getSendXmlInsDt() {
			return StringUtils.defaultString(sendXmlInsDt, "");
		}
		public void setSendXmlInsDt(String sendXmlInsDt) {
			this.sendXmlInsDt = sendXmlInsDt;
		}
		public String getSendXmlBillNoEnd() {
			return StringUtils.defaultString(sendXmlBillNoEnd, "");
		}
		public void setSendXmlBillNoEnd(String sendXmlBillNoEnd) {
			this.sendXmlBillNoEnd = sendXmlBillNoEnd;
		}
		
		
		private String recvXml = "";
		private String recvXmlRetcd = "";
		private String recvXmlSrid = "";
		private String recvXmlBillCd = "";
		private String recvXmlPosNo = "";
		private String recvXmlSaleDate = "";
		private String recvXmlShopCd = "";			
		private String recvXmlIndexNo = "";	
		
		
	
	
		public String getRecvXml() {
			return recvXml;
		}
		public void setRecvXml(String recvXml) {
			this.recvXml = recvXml;
		}
		
		public String getRecvXmlRetcd() {
			return recvXmlRetcd;
		}
		public void setRecvXmlRetcd(String recvXmlRetcd) {
			this.recvXmlRetcd = recvXmlRetcd;
		}
		public String getRecvXmlSrid() {
			return recvXmlSrid;
		}
		public void setRecvXmlSrid(String recvXmlSrid) {
			this.recvXmlSrid = recvXmlSrid;
		}			
		public String getRecvXmlBillCd() {
			return recvXmlBillCd;
		}
		public void setRecvXmlBillCd(String recvXmlBillCd) {
			this.recvXmlBillCd = recvXmlBillCd;
		}
		public String getRecvXmlPosNo() {
			return recvXmlPosNo;
		}
		public void setRecvXmlPosNo(String recvXmlPosNo) {
			this.recvXmlPosNo = recvXmlPosNo;
		}
		public String getRecvXmlSaleDate() {
			return recvXmlSaleDate;
		}
		public void setRecvXmlSaleDate(String recvXmlSaleDate) {
			this.recvXmlSaleDate = recvXmlSaleDate;
		}
		public String getRecvXmlShopCd() {
			return recvXmlShopCd;
		}
		public void setRecvXmlShopCd(String recvXmlShopCd) {
			this.recvXmlShopCd = recvXmlShopCd;
		}
		public String getRecvXmlIndexNo() {
			return recvXmlIndexNo;
		}
		public void setRecvXmlIndexNo(String recvXmlIndexNo) {
			this.recvXmlIndexNo = recvXmlIndexNo;
		}
		
		
		
}
