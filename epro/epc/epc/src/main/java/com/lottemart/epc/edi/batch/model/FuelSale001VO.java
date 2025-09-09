package com.lottemart.epc.edi.batch.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;


	public class FuelSale001VO implements Serializable {
	
		
		
		

		/**
		 * 
		 */
		private static final long serialVersionUID = -3884540093576486549L;


		private String sendXml = "";
		
		
		private String sendXmlSrid ="";
		private String sendXmlRetcd ="";
		private String sendXmlTrhdrCnt ="";
		private String sendXmlTrdtlCnt ="";
		private String sendXmlTrcshCnt ="";
		private String sendXmlTrcrdCnt ="";
		private String sendXmlTrcstCnt ="";
		private String sendXmlTrfodCnt ="";
		private String sendXmlTrgftCnt ="";
		private String sendXmlTrjcdCnt ="";
		private String sendXmlTrwesCnt = "";
		private String sendXmlTrpsqCnt = "";
		private String sendXmlTrcsaCnt = "";
		private String sendXmlTrgfaCnt = "";
		private String sendXmlTrfoaCnt = "";
		private String sendXmlTrrfcCnt = "";
		private String sendXmlRunMode = "";
		private String sendXmlShopCd = "";
		private String sendXmlSaleDate = "";
		private String sendXmlPosNo = "";
		private String sendXmlBillNo = "";
		private String sendXmlRegiSeq = "";
		private String sendXmlSaleYn = "";
		private String sendXmlTotSaleAmt = "";
		private String sendXmlTotDcAmt = "";
		
		
		private String sendXmlSvcTipAmt = "";
		private String sendXmlDcmSaleAmt = "";
		private String sendXmlVatAmt = "";
		
		private String sendXmlTotEtcAmt = "";
		private String sendXmlVatSaleAmt = "";
		
		private String sendXmlNoVatSaleAmt = "";
		private String sendXmlNoTaxSaleAmt = "";
		private String sendXmlExpPayAmt = "";
		private String sendXmlGstPayAmt = "";
		private String sendXmlRetPayAmt = "";
		private String sendXmlCashAmt = "";
		private String sendXmlCrdCardAmt = "";
		private String sendXmlWesAmt = "";
		private String sendXmlTkGftAmt = "";
		private String sendXmlTkFodAmt = "";
		private String sendXmlCstPointAmt = "";
		private String sendXmlJcdCardAmt = "";
		private String sendXmlDcGenAmt = "";
		private String sendXmlDcSvcAmt = "";
		private String sendXmlDcPcdAmt = "";
		private String sendXmlDcCpnAmt = "";
		private String sendXmlDcCstAmt = "";
		private String sendXmlDcTfdAmt = "";
		private String sendXmlDcPrmAmt = "";
		private String sendXmlDcCrdAmt = "";
		private String sendXmlDcPackAmt = "";
		private String sendXmlRepayCashAmt = "";
		private String sendXmlRepayTkGftAmt = "";
		private String sendXmlCashBillAmt = "";
		private String sendXmlDlvOrderFg = "";
		private String sendXmlFdTblCd = "";
		private String sendXmlFdGstCntT = "";
		private String sendXmlFdGstCnt1 = "";
		private String sendXmlFdGstCnt2 = "";
		private String sendXmlFdGstCnt3 = "";
		private String sendXmlFdGstCnt4 = "";
		private String sendXmlOrgBillNo = "";
		private String sendXmlOrderNo = "";
		private String sendXmlInsDt = "";
		private String sendXmlEmpNo = "";
		private String sendXmlPayOutDt = "";
		private String sendXmlCstNm = "";
		private String sendXmlDlvEmpNo = "";
		private String sendXmlDlvStartDt = "";
		private String sendXmlDlvPayinEmpNo = "";
		private String sendXmlDlvPayinDt = "";
		private String sendXmlNewDlvAddrYn = "";
		private String sendXmlDlvAddr = "";
		private String sendXmlDlvAddrDtl = "";
		private String sendXmlNewDlvTelNoYn = "";
		private String sendXmlDlvTelNo = "";
		private String sendXmlDlvClCd = "";
		private String sendXmlDlvCmCd = "";
		private String sendXmlTravelCd = "";
		private String sendXmlRsvNo = "";
		private String sendXmlPrePayCash = "";
		private String sendXmlPrePayCard = "";
		private String sendXmlRsvUserNm = "";
		private String sendXmlRsvUserTelNo = "";
		private String sendXmlPrepayAmt = "";
		private String sendXmlComAmt = "";
		private String sendXmlDtlNo = "";
		private String sendXmlRegiSeq1 = "";
		private String sendXmlSaleYn1 = "";

		

		private String sendXmlProdCd = "";	
		private String sendXmlProdTypeFg = "";
		private String sendXmlCornerCd = "";
		private String sendXmlChgBillNo = "";
		private String sendXmlTaxYn = "";
		private String sendXmlDlvPackFg = "";
		private String sendXmlOrgSaleMgCd = "";
		private String sendXmlOrgSaleUprc = "";
		private String sendXmlNormalUprc = "";
		private String sendXmlSaleMgCd = "";
		private String sendXmlSaleQty = "";
		private String sendXmlSaleUprc = "";
		private String sendXmlSaleAmt = "";
		private String sendXmlDcAmt = "";
		private String sendXmlEtcAmt = "";
		private String sendXmlSvcTipAmt1 = "";
	    private String sendXmlDcmSaleAmt1 = "";
		private String sendXmlVatAmt1 = "";
		private String sendXmlSvcCd = "";
		private String sendXmlTkCpnCd = "";
		private String sendXmlDcAmtGen = "";
		private String sendXmlDcAmtSvc = "";
		private String sendXmlDcAmtJcd = "";
		private String sendXmlDcAmtCpn = "";
		private String sendXmlDcAmtCst = "";
		private String sendXmlDcAmtFod = "";
		private String sendXmlDcAmtPrm = "";
		private String sendXmlDcAmtCrd = "";
		private String sendXmlDcAmtPack = "";
		private String sendXmlCstSalePoint = "";
		private String sendXmlCstUsePoint = "";
		private String sendXmlPrmProcYn = "";
		private String sendXmlPrmCd = "";
		private String sendXmlPrmSeq = "";
		private String sendXmlSdaCd = "";
		private String sendXmlSdsOrgDtlNo = "";
		
		private String sendXmlInsDtT = "";
		private String sendXmlEmpNoT = "";
		private String sendXmlPrepayAmtT = "";
		private String sendXmlComAmtT = "";

		private String sendXmlDcAmtEmp = "";
		
		public String getSendXmlInsDtT() {
			return StringUtils.defaultString(sendXmlInsDtT, "");
			//return sendXmlInsDtT;
		}
		public void setSendXmlInsDtT(String sendXmlInsDtT) {
			this.sendXmlInsDtT = sendXmlInsDtT;
		}
		public String getSendXmlEmpNoT() {
			return StringUtils.defaultString(sendXmlEmpNoT, "");
			//return sendXmlEmpNoT;
		}
		public void setSendXmlEmpNoT(String sendXmlEmpNoT) {
			this.sendXmlEmpNoT = sendXmlEmpNoT;
		}
		public String getSendXmlPrepayAmtT() {
			return StringUtils.defaultString(sendXmlPrepayAmtT, "");
			//return sendXmlPrepayAmtT;
		}
		public void setSendXmlPrepayAmtT(String sendXmlPrepayAmtT) {
			this.sendXmlPrepayAmtT = sendXmlPrepayAmtT;
		}
		public String getSendXmlComAmtT() {
			return StringUtils.defaultString(sendXmlComAmtT, "");
			//return sendXmlComAmtT;
		}
		public void setSendXmlComAmtT(String sendXmlComAmtT) {
			this.sendXmlComAmtT = sendXmlComAmtT;
		}


		private String sendXmlRfcAmt = "";
		private String sendXmlCstNo = "";
		private String sendXmlCstCardNo = "";
		private String sendXmlDcEmpAmt = "";

		public String getSendXmlSrid() {
			return StringUtils.defaultString(sendXmlSrid, "");
			//return sendXmlSrid;
		}
		public void setSendXmlSrid(String sendXmlSrid) {
			this.sendXmlSrid = sendXmlSrid;
		}
		public String getSendXmlRetcd() {
			return StringUtils.defaultString(sendXmlRetcd, "");
			//return sendXmlRetcd;
		}
		public void setSendXmlRetcd(String sendXmlRetcd) {
			this.sendXmlRetcd = sendXmlRetcd;
		}
		public String getSendXmlTrhdrCnt() {
			return StringUtils.defaultString(sendXmlTrhdrCnt, "");
			//return sendXmlTrhdrCnt;
		}
		public void setSendXmlTrhdrCnt(String sendXmlTrhdrCnt) {
			this.sendXmlTrhdrCnt = sendXmlTrhdrCnt;
		}
		public String getSendXmlTrdtlCnt() {
			return StringUtils.defaultString(sendXmlTrdtlCnt, "");
			//return sendXmlTrdtlCnt;
		}
		public void setSendXmlTrdtlCnt(String sendXmlTrdtlCnt) {
			this.sendXmlTrdtlCnt = sendXmlTrdtlCnt;
		}
		public String getSendXmlTrcshCnt() {
			return StringUtils.defaultString(sendXmlTrcshCnt, "");
			//return sendXmlTrcshCnt;
		}
		public void setSendXmlTrcshCnt(String sendXmlTrcshCnt) {
			this.sendXmlTrcshCnt = sendXmlTrcshCnt;
		}
		public String getSendXmlTrcrdCnt() {
			return StringUtils.defaultString(sendXmlTrcrdCnt, "");
			//return sendXmlTrcrdCnt;
		}
		public void setSendXmlTrcrdCnt(String sendXmlTrcrdCnt) {
			this.sendXmlTrcrdCnt = sendXmlTrcrdCnt;
		}
		public String getSendXmlTrcstCnt() {
			return StringUtils.defaultString(sendXmlTrcstCnt, "");
			//return sendXmlTrcstCnt;
		}
		public void setSendXmlTrcstCnt(String sendXmlTrcstCnt) {
			this.sendXmlTrcstCnt = sendXmlTrcstCnt;
		}
		public String getSendXmlTrfodCnt() {
			return StringUtils.defaultString(sendXmlTrfodCnt, "");
			//return sendXmlTrfodCnt;
		}
		public void setSendXmlTrfodCnt(String sendXmlTrfodCnt) {
			this.sendXmlTrfodCnt = sendXmlTrfodCnt;
		}
		public String getSendXmlTrgftCnt() {
			return StringUtils.defaultString(sendXmlTrgftCnt, "");
			//return sendXmlTrgftCnt;
		}
		public void setSendXmlTrgftCnt(String sendXmlTrgftCnt) {
			this.sendXmlTrgftCnt = sendXmlTrgftCnt;
		}
		public String getSendXmlTrjcdCnt() {
			return StringUtils.defaultString(sendXmlTrjcdCnt, "");
			//return sendXmlTrjcdCnt;
		}
		public void setSendXmlTrjcdCnt(String sendXmlTrjcdCnt) {
			this.sendXmlTrjcdCnt = sendXmlTrjcdCnt;
		}
		public String getSendXmlTrwesCnt() {
			return StringUtils.defaultString(sendXmlTrwesCnt, "");
			//return sendXmlTrwesCnt;
		}
		public void setSendXmlTrwesCnt(String sendXmlTrwesCnt) {
			this.sendXmlTrwesCnt = sendXmlTrwesCnt;
		}
		public String getSendXmlTrpsqCnt() {
			return StringUtils.defaultString(sendXmlTrpsqCnt, "");
			//return sendXmlTrpsqCnt;
		}
		public void setSendXmlTrpsqCnt(String sendXmlTrpsqCnt) {
			this.sendXmlTrpsqCnt = sendXmlTrpsqCnt;
		}
		public String getSendXmlTrcsaCnt() {
			return StringUtils.defaultString(sendXmlTrcsaCnt, "");
			//return sendXmlTrcsaCnt;
		}
		public void setSendXmlTrcsaCnt(String sendXmlTrcsaCnt) {
			this.sendXmlTrcsaCnt = sendXmlTrcsaCnt;
		}
		public String getSendXmlTrgfaCnt() {
			return StringUtils.defaultString(sendXmlTrgfaCnt, "");
			//return sendXmlTrgfaCnt;
		}
		public void setSendXmlTrgfaCnt(String sendXmlTrgfaCnt) {
			this.sendXmlTrgfaCnt = sendXmlTrgfaCnt;
		}
		public String getSendXmlTrfoaCnt() {
			return StringUtils.defaultString(sendXmlTrfoaCnt, "");
			//return sendXmlTrfoaCnt;
		}
		public void setSendXmlTrfoaCnt(String sendXmlTrfoaCnt) {
			this.sendXmlTrfoaCnt = sendXmlTrfoaCnt;
		}
		public String getSendXmlTrrfcCnt() {
			return StringUtils.defaultString(sendXmlTrrfcCnt, "");
			//return sendXmlTrrfcCnt;
		}
		public void setSendXmlTrrfcCnt(String sendXmlTrrfcCnt) {
			this.sendXmlTrrfcCnt = sendXmlTrrfcCnt;
		}
		public String getSendXmlRunMode() {
			return StringUtils.defaultString(sendXmlRunMode, "");
			//return sendXmlRunMode;
		}
		public void setSendXmlRunMode(String sendXmlRunMode) {
			this.sendXmlRunMode = sendXmlRunMode;
		}
		public String getSendXmlShopCd() {
			return StringUtils.defaultString(sendXmlShopCd, "");
			//return sendXmlShopCd;
		}
		public void setSendXmlShopCd(String sendXmlShopCd) {
			this.sendXmlShopCd = sendXmlShopCd;
		}
		public String getSendXmlSaleDate() {
			return StringUtils.defaultString(sendXmlSaleDate, "");
			//return sendXmlSaleDate;
		}
		public void setSendXmlSaleDate(String sendXmlSaleDate) {
			this.sendXmlSaleDate = sendXmlSaleDate;
		}
		public String getSendXmlPosNo() {
			return StringUtils.defaultString(sendXmlPosNo, "");
			//return sendXmlPosNo;
		}
		public void setSendXmlPosNo(String sendXmlPosNo) {
			this.sendXmlPosNo = sendXmlPosNo;
		}
		public String getSendXmlBillNo() {
			return StringUtils.defaultString(sendXmlBillNo, "");
			//return sendXmlBillNo;
		}
		public void setSendXmlBillNo(String sendXmlBillNo) {
			this.sendXmlBillNo = sendXmlBillNo;
		}
		public String getSendXmlRegiSeq() {
			return StringUtils.defaultString(sendXmlRegiSeq, "");
			//return sendXmlRegiSeq;
		}
		public void setSendXmlRegiSeq(String sendXmlRegiSeq) {
			this.sendXmlRegiSeq = sendXmlRegiSeq;
		}
		public String getSendXmlSaleYn() {
			return StringUtils.defaultString(sendXmlSaleYn, "");
			//return sendXmlSaleYn;
		}
		public void setSendXmlSaleYn(String sendXmlSaleYn) {
			this.sendXmlSaleYn = sendXmlSaleYn;
		}
		public String getSendXmlTotSaleAmt() {
			return StringUtils.defaultString(sendXmlTotSaleAmt, "");
			//return sendXmlTotSaleAmt;
		}
		public void setSendXmlTotSaleAmt(String sendXmlTotSaleAmt) {
			this.sendXmlTotSaleAmt = sendXmlTotSaleAmt;
		}
		public String getSendXmlTotDcAmt() {
			return StringUtils.defaultString(sendXmlTotDcAmt, "");
			//return sendXmlTotDcAmt;
		}
		public void setSendXmlTotDcAmt(String sendXmlTotDcAmt) {
			this.sendXmlTotDcAmt = sendXmlTotDcAmt;
		}
		public String getSendXmlSvcTipAmt() {
			return StringUtils.defaultString(sendXmlSvcTipAmt, "");
			//return sendXmlSvcTipAmt;
		}
		public void setSendXmlSvcTipAmt(String sendXmlSvcTipAmt) {
			this.sendXmlSvcTipAmt = sendXmlSvcTipAmt;
		}
		public String getSendXmlTotEtcAmt() {
			return StringUtils.defaultString(sendXmlTotEtcAmt, "");
			//return sendXmlTotEtcAmt;
		}
		public void setSendXmlTotEtcAmt(String sendXmlTotEtcAmt) {
			this.sendXmlTotEtcAmt = sendXmlTotEtcAmt;
		}
		public String getSendXmlSvcTipAmt1() {
			return StringUtils.defaultString(sendXmlSvcTipAmt1, "");
			//return sendXmlSvcTipAmt1;
		}
		public void setSendXmlSvcTipAmt1(String sendXmlSvcTipAmt1) {
			this.sendXmlSvcTipAmt1 = sendXmlSvcTipAmt1;
		}
		public String getSendXmlDcmSaleAmt1() {
			return StringUtils.defaultString(sendXmlDcmSaleAmt1, "");
			//return sendXmlDcmSaleAmt1;
		}
		public void setSendXmlDcmSaleAmt1(String sendXmlDcmSaleAmt1) {
			this.sendXmlDcmSaleAmt1 = sendXmlDcmSaleAmt1;
		}
		public String getSendXmlVatAmt1() {
			return StringUtils.defaultString(sendXmlVatAmt1, "");
			//return sendXmlVatAmt1;
		}
		public void setSendXmlVatAmt1(String sendXmlVatAmt1) {
			this.sendXmlVatAmt1 = sendXmlVatAmt1;
		}
		
		public String getSendXmlDcmSaleAmt() {
			return StringUtils.defaultString(sendXmlDcmSaleAmt, "");
			//return sendXmlDcmSaleAmt;
		}
		public void setSendXmlDcmSaleAmt(String sendXmlDcmSaleAmt) {
			this.sendXmlDcmSaleAmt = sendXmlDcmSaleAmt;
		}
		public String getSendXmlVatSaleAmt() {
			return StringUtils.defaultString(sendXmlVatSaleAmt, "");
			//return sendXmlVatSaleAmt;
		}
		public void setSendXmlVatSaleAmt(String sendXmlVatSaleAmt) {
			this.sendXmlVatSaleAmt = sendXmlVatSaleAmt;
		}
		public String getSendXmlVatAmt() {
			return StringUtils.defaultString(sendXmlVatAmt, "");
			//return sendXmlVatAmt;
		}
		public void setSendXmlVatAmt(String sendXmlVatAmt) {
			this.sendXmlVatAmt = sendXmlVatAmt;
		}
		public String getSendXmlNoVatSaleAmt() {
			return StringUtils.defaultString(sendXmlNoVatSaleAmt, "");
			//return sendXmlNoVatSaleAmt;
		}
		public void setSendXmlNoVatSaleAmt(String sendXmlNoVatSaleAmt) {
			this.sendXmlNoVatSaleAmt = sendXmlNoVatSaleAmt;
		}
		public String getSendXmlNoTaxSaleAmt() {
			return StringUtils.defaultString(sendXmlNoTaxSaleAmt, "");
			//return sendXmlNoTaxSaleAmt;
		}
		public void setSendXmlNoTaxSaleAmt(String sendXmlNoTaxSaleAmt) {
			this.sendXmlNoTaxSaleAmt = sendXmlNoTaxSaleAmt;
		}
		public String getSendXmlExpPayAmt() {
			return StringUtils.defaultString(sendXmlExpPayAmt, "");
			//return sendXmlExpPayAmt;
		}
		public void setSendXmlExpPayAmt(String sendXmlExpPayAmt) {
			this.sendXmlExpPayAmt = sendXmlExpPayAmt;
		}
		public String getSendXmlGstPayAmt() {
			return StringUtils.defaultString(sendXmlGstPayAmt, "");
			//return sendXmlGstPayAmt;
		}
		public void setSendXmlGstPayAmt(String sendXmlGstPayAmt) {
			this.sendXmlGstPayAmt = sendXmlGstPayAmt;
		}
		public String getSendXmlRetPayAmt() {
			return StringUtils.defaultString(sendXmlRetPayAmt, "");
			//return sendXmlRetPayAmt;
		}
		public void setSendXmlRetPayAmt(String sendXmlRetPayAmt) {
			this.sendXmlRetPayAmt = sendXmlRetPayAmt;
		}
		public String getSendXmlCashAmt() {
			return StringUtils.defaultString(sendXmlCashAmt, "");
			//return sendXmlCashAmt;
		}
		public void setSendXmlCashAmt(String sendXmlCashAmt) {
			this.sendXmlCashAmt = sendXmlCashAmt;
		}
		public String getSendXmlCrdCardAmt() {
			return StringUtils.defaultString(sendXmlCrdCardAmt, "");
			//return sendXmlCrdCardAmt;
		}
		public void setSendXmlCrdCardAmt(String sendXmlCrdCardAmt) {
			this.sendXmlCrdCardAmt = sendXmlCrdCardAmt;
		}
		public String getSendXmlWesAmt() {
			return StringUtils.defaultString(sendXmlWesAmt, "");
			//return sendXmlWesAmt;
		}
		public void setSendXmlWesAmt(String sendXmlWesAmt) {
			this.sendXmlWesAmt = sendXmlWesAmt;
		}
		public String getSendXmlTkGftAmt() {
			return StringUtils.defaultString(sendXmlTkGftAmt, "");
			//return sendXmlTkGftAmt;
		}
		public void setSendXmlTkGftAmt(String sendXmlTkGftAmt) {
			this.sendXmlTkGftAmt = sendXmlTkGftAmt;
		}
		public String getSendXmlTkFodAmt() {
			return StringUtils.defaultString(sendXmlTkFodAmt, "");
			//return sendXmlTkFodAmt;
		}
		public void setSendXmlTkFodAmt(String sendXmlTkFodAmt) {
			this.sendXmlTkFodAmt = sendXmlTkFodAmt;
		}
		public String getSendXmlCstPointAmt() {
			return StringUtils.defaultString(sendXmlCstPointAmt, "");
			//return sendXmlCstPointAmt;
		}
		public void setSendXmlCstPointAmt(String sendXmlCstPointAmt) {
			this.sendXmlCstPointAmt = sendXmlCstPointAmt;
		}
		public String getSendXmlJcdCardAmt() {
			return StringUtils.defaultString(sendXmlJcdCardAmt);
			//return sendXmlJcdCardAmt;
		}
		public void setSendXmlJcdCardAmt(String sendXmlJcdCardAmt) {
			this.sendXmlJcdCardAmt = sendXmlJcdCardAmt;
		}
		public String getSendXmlDcGenAmt() {
			return StringUtils.defaultString(sendXmlDcGenAmt, "");
			//return sendXmlDcGenAmt;
		}
		public void setSendXmlDcGenAmt(String sendXmlDcGenAmt) {
			this.sendXmlDcGenAmt = sendXmlDcGenAmt;
		}
		public String getSendXmlDcSvcAmt() {
			return StringUtils.defaultString(sendXmlDcSvcAmt, "");
			//return sendXmlDcSvcAmt;
		}
		public void setSendXmlDcSvcAmt(String sendXmlDcSvcAmt) {
			this.sendXmlDcSvcAmt = sendXmlDcSvcAmt;
		}
		public String getSendXmlDcPcdAmt() {
			return StringUtils.defaultString(sendXmlDcPcdAmt, "");
			//return sendXmlDcPcdAmt;
		}
		public void setSendXmlDcPcdAmt(String sendXmlDcPcdAmt) {
			this.sendXmlDcPcdAmt = sendXmlDcPcdAmt;
		}
		public String getSendXmlDcCpnAmt() {
			return StringUtils.defaultString(sendXmlDcCpnAmt, "");
			//return sendXmlDcCpnAmt;
		}
		public void setSendXmlDcCpnAmt(String sendXmlDcCpnAmt) {
			this.sendXmlDcCpnAmt = sendXmlDcCpnAmt;
		}
		public String getSendXmlDcCstAmt() {
			return StringUtils.defaultString(sendXmlDcCstAmt, "");
			//return sendXmlDcCstAmt;
		}
		public void setSendXmlDcCstAmt(String sendXmlDcCstAmt) {
			this.sendXmlDcCstAmt = sendXmlDcCstAmt;
		}
		public String getSendXmlDcTfdAmt() {
			return StringUtils.defaultString(sendXmlDcTfdAmt, "");
			//return sendXmlDcTfdAmt;
		}			
		public void setSendXmlDcTfdAmt(String sendXmlDcTfdAmt) {
			this.sendXmlDcTfdAmt = sendXmlDcTfdAmt;
		}			
		public String getSendXmlDcPrmAmt() {
			return StringUtils.defaultString(sendXmlDcPrmAmt, "");
			//return sendXmlDcPrmAmt;
		}
		public void setSendXmlDcPrmAmt(String sendXmlDcPrmAmt) {
			this.sendXmlDcPrmAmt = sendXmlDcPrmAmt;
		}
		public String getSendXmlDcCrdAmt() {
			return StringUtils.defaultString(sendXmlDcCrdAmt, "");
			//return sendXmlDcCrdAmt;
		}
		public void setSendXmlDcCrdAmt(String sendXmlDcCrdAmt) {
			this.sendXmlDcCrdAmt = sendXmlDcCrdAmt;
		}
		public String getSendXmlDcPackAmt() {
			return StringUtils.defaultString(sendXmlDcPackAmt, "");
			//return sendXmlDcPackAmt;
		}
		public void setSendXmlDcPackAmt(String sendXmlDcPackAmt) {
			this.sendXmlDcPackAmt = sendXmlDcPackAmt;
		}
		public String getSendXmlRepayCashAmt() {
			return StringUtils.defaultString(sendXmlRepayCashAmt, "");
			//return sendXmlRepayCashAmt;
		}
		public void setSendXmlRepayCashAmt(String sendXmlRepayCashAmt) {
			this.sendXmlRepayCashAmt = sendXmlRepayCashAmt;
		}
		public String getSendXmlRepayTkGftAmt() {
			return StringUtils.defaultString(sendXmlRepayTkGftAmt, "");
			//return sendXmlRepayTkGftAmt;
		}
		public void setSendXmlRepayTkGftAmt(String sendXmlRepayTkGftAmt) {
			this.sendXmlRepayTkGftAmt = sendXmlRepayTkGftAmt;
		}
		public String getSendXmlCashBillAmt() {
			return StringUtils.defaultString(sendXmlCashBillAmt, "");
			//return sendXmlCashBillAmt;
		}
		public void setSendXmlCashBillAmt(String sendXmlCashBillAmt) {
			this.sendXmlCashBillAmt = sendXmlCashBillAmt;
		}
		public String getSendXmlDlvOrderFg() {
			return StringUtils.defaultString(sendXmlDlvOrderFg, "");
			//return sendXmlDlvOrderFg;
		}
		public void setSendXmlDlvOrderFg(String sendXmlDlvOrderFg) {
			this.sendXmlDlvOrderFg = sendXmlDlvOrderFg;
		}
		public String getSendXmlFdTblCd() {
			return StringUtils.defaultString(sendXmlFdTblCd, "");
			//return sendXmlFdTblCd;
		}
		public void setSendXmlFdTblCd(String sendXmlFdTblCd) {
			this.sendXmlFdTblCd = sendXmlFdTblCd;
		}
		public String getSendXmlFdGstCntT() {
			return StringUtils.defaultString(sendXmlFdGstCntT, "");
			//return sendXmlFdGstCntT;
		}
		public void setSendXmlFdGstCntT(String sendXmlFdGstCntT) {
			this.sendXmlFdGstCntT = sendXmlFdGstCntT;
		}
		public String getSendXmlFdGstCnt1() {
			return StringUtils.defaultString(sendXmlFdGstCnt1, "");
			//return sendXmlFdGstCnt1;
		}
		public void setSendXmlFdGstCnt1(String sendXmlFdGstCnt1) {
			this.sendXmlFdGstCnt1 = sendXmlFdGstCnt1;
		}
		public String getSendXmlFdGstCnt2() {
			return StringUtils.defaultString(sendXmlFdGstCnt2, "");
			//return sendXmlFdGstCnt2;
		}
		public void setSendXmlFdGstCnt2(String sendXmlFdGstCnt2) {
			this.sendXmlFdGstCnt2 = sendXmlFdGstCnt2;
		}
		public String getSendXmlFdGstCnt3() {
			return StringUtils.defaultString(sendXmlFdGstCnt3, "");
			//return sendXmlFdGstCnt3;
		}
		public void setSendXmlFdGstCnt3(String sendXmlFdGstCnt3) {
			this.sendXmlFdGstCnt3 = sendXmlFdGstCnt3;
		}
		public String getSendXmlFdGstCnt4() {
			return StringUtils.defaultString(sendXmlFdGstCnt4, "");
			//return sendXmlFdGstCnt4;
		}
		public void setSendXmlFdGstCnt4(String sendXmlFdGstCnt4) {
			this.sendXmlFdGstCnt4 = sendXmlFdGstCnt4;
		}
		
		public String getSendXmlOrgBillNo() {
			return StringUtils.defaultString(sendXmlOrgBillNo, "");
			//return sendXmlOrgBillNo;
		}			
		public void setSendXmlOrgBillNo(String sendXmlOrgBillNo) {
			this.sendXmlOrgBillNo = sendXmlOrgBillNo;
		}
		public String getSendXmlOrderNo() {
			return StringUtils.defaultString(sendXmlOrderNo, "");
			//return sendXmlOrderNo;				
		}
		
		public void setSendXmlOrderNo(String sendXmlOrderNo) {
			this.sendXmlOrderNo = sendXmlOrderNo;
		}
		public String getSendXmlInsDt() {
			return StringUtils.defaultString(sendXmlInsDt, "");
			//return sendXmlInsDt;
		}
		public void setSendXmlInsDt(String sendXmlInsDt) {
			this.sendXmlInsDt = sendXmlInsDt;
		}
		public String getSendXmlEmpNo() {
			return StringUtils.defaultString(sendXmlEmpNo, "");
			//return sendXmlEmpNo;
		}
		public void setSendXmlEmpNo(String sendXmlEmpNo) {
			this.sendXmlEmpNo = sendXmlEmpNo;
		}
		public String getSendXmlPayOutDt() {
			return StringUtils.defaultString(sendXmlPayOutDt, "");
			//return sendXmlPayOutDt;
		}
		public void setSendXmlPayOutDt(String sendXmlPayOutDt) {
			this.sendXmlPayOutDt = sendXmlPayOutDt;
		}
		public String getSendXmlCstNm() {
			return StringUtils.defaultString(sendXmlCstNm, "");
			//return sendXmlCstNm;
		}
		public void setSendXmlCstNm(String sendXmlCstNm) {
			this.sendXmlCstNm = sendXmlCstNm;
		}
		public String getSendXmlDlvEmpNo() {
			return StringUtils.defaultString(sendXmlDlvEmpNo, "");
			//return sendXmlDlvEmpNo;
		}
		public void setSendXmlDlvEmpNo(String sendXmlDlvEmpNo) {
			this.sendXmlDlvEmpNo = sendXmlDlvEmpNo;
		}
		public String getSendXmlDlvStartDt() {
			return StringUtils.defaultString(sendXmlDlvStartDt, "");
			//return sendXmlDlvStartDt;
		}
		public void setSendXmlDlvStartDt(String sendXmlDlvStartDt) {
			this.sendXmlDlvStartDt = sendXmlDlvStartDt;
		}
		public String getSendXmlDlvPayinEmpNo() {
			return StringUtils.defaultString(sendXmlDlvPayinEmpNo, "");
			//return sendXmlDlvPayinEmpNo;
		}
		public void setSendXmlDlvPayinEmpNo(String sendXmlDlvPayinEmpNo) {
			this.sendXmlDlvPayinEmpNo = sendXmlDlvPayinEmpNo;
		}
		public String getSendXmlDlvPayinDt() {
			return StringUtils.defaultString(sendXmlDlvPayinDt, "");
			//return sendXmlDlvPayinDt;
		}
		public void setSendXmlDlvPayinDt(String sendXmlDlvPayinDt) {
			this.sendXmlDlvPayinDt = sendXmlDlvPayinDt;
		}
		public String getSendXmlNewDlvAddrYn() {
			return StringUtils.defaultString(sendXmlNewDlvAddrYn, "");
			//return sendXmlNewDlvAddrYn;
		}
		public void setSendXmlNewDlvAddrYn(String sendXmlNewDlvAddrYn) {
			this.sendXmlNewDlvAddrYn = sendXmlNewDlvAddrYn;
		}
		public String getSendXmlDlvAddr() {
			return StringUtils.defaultString(sendXmlDlvAddr, "");
			//return sendXmlDlvAddr;
		}
		public void setSendXmlDlvAddr(String sendXmlDlvAddr) {
			this.sendXmlDlvAddr = sendXmlDlvAddr;
		}
		public String getSendXmlDlvAddrDtl() {
			return StringUtils.defaultString(sendXmlDlvAddrDtl, "");
			//return sendXmlDlvAddrDtl;
		}
		public void setSendXmlDlvAddrDtl(String sendXmlDlvAddrDtl) {
			this.sendXmlDlvAddrDtl = sendXmlDlvAddrDtl;
		}
		public String getSendXmlNewDlvTelNoYn() {
			return StringUtils.defaultString(sendXmlNewDlvTelNoYn, "");
			//return sendXmlNewDlvTelNoYn;
		}
		public void setSendXmlNewDlvTelNoYn(String sendXmlNewDlvTelNoYn) {
			this.sendXmlNewDlvTelNoYn = sendXmlNewDlvTelNoYn;
		}
		public String getSendXmlDlvTelNo() {
			return StringUtils.defaultString(sendXmlDlvTelNo, "");
			//return sendXmlDlvTelNo;
		}
		public void setSendXmlDlvTelNo(String sendXmlDlvTelNo) {
			this.sendXmlDlvTelNo = sendXmlDlvTelNo;
		}
		public String getSendXmlDlvClCd() {
			return StringUtils.defaultString(sendXmlDlvClCd, "");
			//return sendXmlDlvClCd;
		}
		public void setSendXmlDlvClCd(String sendXmlDlvClCd) {
			this.sendXmlDlvClCd = sendXmlDlvClCd;
		}
		public String getSendXmlDlvCmCd() {
			return StringUtils.defaultString(sendXmlDlvCmCd, "");
			//return sendXmlDlvCmCd;
		}
		public void setSendXmlDlvCmCd(String sendXmlDlvCmCd) {
			this.sendXmlDlvCmCd = sendXmlDlvCmCd;
		}
		public String getSendXmlTravelCd() {
			return StringUtils.defaultString(sendXmlTravelCd, "");
			//return sendXmlTravelCd;
		}
		public void setSendXmlTravelCd(String sendXmlTravelCd) {
			this.sendXmlTravelCd = sendXmlTravelCd;
		}
		public String getSendXmlRsvNo() {
			return StringUtils.defaultString(sendXmlRsvNo, "");
			//return sendXmlRsvNo;
		}
		public void setSendXmlRsvNo(String sendXmlRsvNo) {
			this.sendXmlRsvNo = sendXmlRsvNo;
		}
		public String getSendXmlPrePayCash() {
			return StringUtils.defaultString(sendXmlPrePayCash, "");
			//return sendXmlPrePayCash;
		}
		public void setSendXmlPrePayCash(String sendXmlPrePayCash) {
			this.sendXmlPrePayCash = sendXmlPrePayCash;
		}
		public String getSendXmlPrePayCard() {
			return StringUtils.defaultString(sendXmlPrePayCard, "");
			//return sendXmlPrePayCard;
		}
		public void setSendXmlPrePayCard(String sendXmlPrePayCard) {
			this.sendXmlPrePayCard = sendXmlPrePayCard;
		}
		public String getSendXmlRsvUserNm() {
			return StringUtils.defaultString(sendXmlRsvUserNm, "");
			//return sendXmlRsvUserNm;
		}
		public void setSendXmlRsvUserNm(String sendXmlRsvUserNm) {
			this.sendXmlRsvUserNm = sendXmlRsvUserNm;
		}
		public String getSendXmlRsvUserTelNo() {
			return StringUtils.defaultString(sendXmlRsvUserTelNo, "");
			//return sendXmlRsvUserTelNo;
		}
		public void setSendXmlRsvUserTelNo(String sendXmlRsvUserTelNo) {
			this.sendXmlRsvUserTelNo = sendXmlRsvUserTelNo;
		}
		public String getSendXmlPrepayAmt() {
			return StringUtils.defaultString(sendXmlPrepayAmt, "");
			//return sendXmlPrepayAmt;
		}
		public void setSendXmlPrepayAmt(String sendXmlPrepayAmt) {
			this.sendXmlPrepayAmt = sendXmlPrepayAmt;
		}
		public String getSendXmlComAmt() {
			return StringUtils.defaultString(sendXmlComAmt, "");
			//return sendXmlComAmt;
		}
		public void setSendXmlComAmt(String sendXmlComAmt) {
			this.sendXmlComAmt = sendXmlComAmt;
		}
		public String getSendXmlDtlNo() {
			return StringUtils.defaultString(sendXmlDtlNo, "");
			//return sendXmlDtlNo;
		}
		public void setSendXmlDtlNo(String sendXmlDtlNo) {
			this.sendXmlDtlNo = sendXmlDtlNo;
		}
		public String getSendXmlRegiSeq1() {
			return StringUtils.defaultString(sendXmlRegiSeq1, "");
			//return sendXmlRegiSeq1;
		}
		public void setSendXmlRegiSeq1(String sendXmlRegiSeq1) {
			this.sendXmlRegiSeq1 = sendXmlRegiSeq1;
		}
		public String getSendXmlSaleYn1() {
			return StringUtils.defaultString(sendXmlSaleYn1, "");
			//return sendXmlSaleYn1;
		}
		public void setSendXmlSaleYn1(String sendXmlSaleYn1) {
			this.sendXmlSaleYn1 = sendXmlSaleYn1;
		}		
		public String getSendXmlProdCd() {
			return StringUtils.defaultString(sendXmlProdCd, "");
			//return sendXmlProdCd;
		}
		public void setSendXmlProdCd(String sendXmlProdCd) {
			this.sendXmlProdCd = sendXmlProdCd;
		}
		public String getSendXmlProdTypeFg() {
			return StringUtils.defaultString(sendXmlProdTypeFg, "");
			//return sendXmlProdTypeFg;
		}
		public void setSendXmlProdTypeFg(String sendXmlProdTypeFg) {
			this.sendXmlProdTypeFg = sendXmlProdTypeFg;
		}
		public String getSendXmlCornerCd() {
			return StringUtils.defaultString(sendXmlCornerCd, "");
			//return sendXmlCornerCd;
		}
		public void setSendXmlCornerCd(String sendXmlCornerCd) {
			this.sendXmlCornerCd = sendXmlCornerCd;
		}
		public String getSendXmlChgBillNo() {
			return StringUtils.defaultString(sendXmlChgBillNo, "");
			//return sendXmlChgBillNo;
		}
		public void setSendXmlChgBillNo(String sendXmlChgBillNo) {
			this.sendXmlChgBillNo = sendXmlChgBillNo;
		}
		public String getSendXmlTaxYn() {
			return StringUtils.defaultString(sendXmlTaxYn, "");
			//return sendXmlTaxYn;
		}
		public void setSendXmlTaxYn(String sendXmlTaxYn) {
			this.sendXmlTaxYn = sendXmlTaxYn;
		}
		public String getSendXmlDlvPackFg() {
			return StringUtils.defaultString(sendXmlDlvPackFg, "");
			////return sendXmlDlvPackFg;
		}
		public void setSendXmlDlvPackFg(String sendXmlDlvPackFg) {
			this.sendXmlDlvPackFg = sendXmlDlvPackFg;
		}
		public String getSendXmlOrgSaleMgCd() {
			return StringUtils.defaultString(sendXmlOrgSaleMgCd, "");
			//return sendXmlOrgSaleMgCd;
		}
		public void setSendXmlOrgSaleMgCd(String sendXmlOrgSaleMgCd) {
			this.sendXmlOrgSaleMgCd = sendXmlOrgSaleMgCd;
		}
		public String getSendXmlOrgSaleUprc() {
			return StringUtils.defaultString(sendXmlOrgSaleUprc, "");
			//return sendXmlOrgSaleUprc;
		}
		public void setSendXmlOrgSaleUprc(String sendXmlOrgSaleUprc) {
			this.sendXmlOrgSaleUprc = sendXmlOrgSaleUprc;
		}
		public String getSendXmlNormalUprc() {
			return StringUtils.defaultString(sendXmlNormalUprc, "");
			//return sendXmlNormalUprc;
		}
		public void setSendXmlNormalUprc(String sendXmlNormalUprc) {
			this.sendXmlNormalUprc = sendXmlNormalUprc;
		}
		public String getSendXmlSaleMgCd() {
			return StringUtils.defaultString(sendXmlSaleMgCd, "");
			//return sendXmlSaleMgCd;
		}
		public void setSendXmlSaleMgCd(String sendXmlSaleMgCd) {
			this.sendXmlSaleMgCd = sendXmlSaleMgCd;
		}
		public String getSendXmlSaleQty() {
			return StringUtils.defaultString(sendXmlSaleQty, "");
			//return sendXmlSaleQty;
		}
		public void setSendXmlSaleQty(String sendXmlSaleQty) {
			this.sendXmlSaleQty = sendXmlSaleQty;
		}
		public String getSendXmlSaleUprc() {
			return StringUtils.defaultString(sendXmlSaleUprc, "");
			//return sendXmlSaleUprc;
		}
		public void setSendXmlSaleUprc(String sendXmlSaleUprc) {
			this.sendXmlSaleUprc = sendXmlSaleUprc;
		}
		public String getSendXmlSaleAmt() {
			return StringUtils.defaultString(sendXmlSaleAmt, "");
			//return sendXmlSaleAmt;
		}
		public void setSendXmlSaleAmt(String sendXmlSaleAmt) {
			this.sendXmlSaleAmt = sendXmlSaleAmt;
		}
		public String getSendXmlDcAmt() {
			return StringUtils.defaultString(sendXmlDcAmt);
			//return sendXmlDcAmt;
		}
		public void setSendXmlDcAmt(String sendXmlDcAmt) {
			this.sendXmlDcAmt = sendXmlDcAmt;
		}
		public String getSendXmlEtcAmt() {
			return StringUtils.defaultString(sendXmlEtcAmt, "");
			//return sendXmlEtcAmt;
		}
		public void setSendXmlEtcAmt(String sendXmlEtcAmt) {
			this.sendXmlEtcAmt = sendXmlEtcAmt;
		}
		public String getSendXmlSvcCd() {
			return StringUtils.defaultString(sendXmlSvcCd, "");
			//return sendXmlSvcCd;
		}		
		public void setSendXmlSvcCd(String sendXmlSvcCd) {
			this.sendXmlSvcCd = sendXmlSvcCd;
		}
		public String getSendXmlTkCpnCd() {
			return StringUtils.defaultString(sendXmlTkCpnCd, "");
			//return sendXmlTkCpnCd;
		}
		public void setSendXmlTkCpnCd(String sendXmlTkCpnCd) {
			this.sendXmlTkCpnCd = sendXmlTkCpnCd;
		}
		public String getSendXmlDcAmtGen() {
			return StringUtils.defaultString(sendXmlDcAmtGen, "");
		//	return sendXmlDcAmtGen;
		
		}
		public void setSendXmlDcAmtGen(String sendXmlDcAmtGen) {
			this.sendXmlDcAmtGen = sendXmlDcAmtGen;
		}
		public String getSendXmlDcAmtSvc() {
			return StringUtils.defaultString(sendXmlDcAmtSvc, "");
		//	return sendXmlDcAmtSvc;
		}
		public void setSendXmlDcAmtSvc(String sendXmlDcAmtSvc) {
			this.sendXmlDcAmtSvc = sendXmlDcAmtSvc;
		}
		public String getSendXmlDcAmtJcd() {
			return StringUtils.defaultString(sendXmlDcAmtJcd, "");
			//return sendXmlDcAmtJcd;
		}
		public void setSendXmlDcAmtJcd(String sendXmlDcAmtJcd) {
			this.sendXmlDcAmtJcd = sendXmlDcAmtJcd;
		}
		public String getSendXmlDcAmtCpn() {
			return StringUtils.defaultString(sendXmlDcAmtCpn, "");
			
			//return sendXmlDcAmtCpn;
		}
		public void setSendXmlDcAmtCpn(String sendXmlDcAmtCpn) {
			this.sendXmlDcAmtCpn = sendXmlDcAmtCpn;
		}
		public String getSendXmlDcAmtCst() {
			return StringUtils.defaultString(sendXmlDcAmtCst, "");
			//return sendXmlDcAmtCst;
		}
		public void setSendXmlDcAmtCst(String sendXmlDcAmtCst) {
			this.sendXmlDcAmtCst = sendXmlDcAmtCst;
		}
		public String getSendXmlDcAmtFod() {
			return StringUtils.defaultString(sendXmlDcAmtFod, "");
			//return sendXmlDcAmtFod;
		}
		public void setSendXmlDcAmtFod(String sendXmlDcAmtFod) {
			this.sendXmlDcAmtFod = sendXmlDcAmtFod;
		}
		public String getSendXmlDcAmtPrm() {
			return StringUtils.defaultString(sendXmlDcAmtPrm, "");
			//return sendXmlDcAmtPrm;
		}
		public void setSendXmlDcAmtPrm(String sendXmlDcAmtPrm) {
			this.sendXmlDcAmtPrm = sendXmlDcAmtPrm;
		}
		public String getSendXmlDcAmtCrd() {
			return StringUtils.defaultString(sendXmlDcAmtCrd, "");
			//return sendXmlDcAmtCrd;
		}
		public void setSendXmlDcAmtCrd(String sendXmlDcAmtCrd) {
			this.sendXmlDcAmtCrd = sendXmlDcAmtCrd;
		}
		public String getSendXmlDcAmtPack() {
			return StringUtils.defaultString(sendXmlDcAmtPack, "");
			//return sendXmlDcAmtPack;			
		}
		public void setSendXmlDcAmtPack(String sendXmlDcAmtPack) {
			this.sendXmlDcAmtPack = sendXmlDcAmtPack;
		}
		public String getSendXmlCstSalePoint() {
			return StringUtils.defaultString(sendXmlCstSalePoint, "");
			//return sendXmlCstSalePoint;
		}
		public void setSendXmlCstSalePoint(String sendXmlCstSalePoint) {
			this.sendXmlCstSalePoint = sendXmlCstSalePoint;
		}
		public String getSendXmlCstUsePoint() {
			return StringUtils.defaultString(sendXmlCstUsePoint, "");
			//return sendXmlCstUsePoint;
		}
		public void setSendXmlCstUsePoint(String sendXmlCstUsePoint) {
			this.sendXmlCstUsePoint = sendXmlCstUsePoint;
		}
		public String getSendXmlPrmProcYn() {
			return StringUtils.defaultString(sendXmlPrmProcYn, "");
			//return sendXmlPrmProcYn;
		}
		public void setSendXmlPrmProcYn(String sendXmlPrmProcYn) {
			this.sendXmlPrmProcYn = sendXmlPrmProcYn;
		}
		public String getSendXmlPrmCd() {
			return StringUtils.defaultString(sendXmlPrmCd, "");
			//return sendXmlPrmCd;
		}
		public void setSendXmlPrmCd(String sendXmlPrmCd) {
			this.sendXmlPrmCd = sendXmlPrmCd;
		}
		public String getSendXmlPrmSeq() {
			return StringUtils.defaultString(sendXmlPrmSeq, "");
			//return sendXmlPrmSeq;
		}
		public void setSendXmlPrmSeq(String sendXmlPrmSeq) {
			this.sendXmlPrmSeq = sendXmlPrmSeq;
		}
		public String getSendXmlSdaCd() {
			return StringUtils.defaultString(sendXmlSdaCd, "");
			//return sendXmlSdaCd;
		}
		public void setSendXmlSdaCd(String sendXmlSdaCd) {
			this.sendXmlSdaCd = sendXmlSdaCd;
		}
		public String getSendXmlSdsOrgDtlNo() {
			return StringUtils.defaultString(sendXmlSdsOrgDtlNo, "");
			//return sendXmlSdsOrgDtlNo;
		}
		public void setSendXmlSdsOrgDtlNo(String sendXmlSdsOrgDtlNo) {
			this.sendXmlSdsOrgDtlNo = sendXmlSdsOrgDtlNo;
		}
		public String getSendXmlDcAmtEmp() {
			return StringUtils.defaultString(sendXmlDcAmtEmp, "");
			//return sendXmlDcAmtEmp;
		}
		public void setSendXmlDcAmtEmp(String sendXmlDcAmtEmp) {
			this.sendXmlDcAmtEmp = sendXmlDcAmtEmp;
		}
		public String getSendXmlRfcAmt() {
			return StringUtils.defaultString(sendXmlRfcAmt, "");
			//return sendXmlRfcAmt;
		}
		public void setSendXmlRfcAmt(String sendXmlRfcAmt) {
			this.sendXmlRfcAmt = sendXmlRfcAmt;
		}
		public String getSendXmlCstNo() {
			return StringUtils.defaultString(sendXmlCstNo, "");
			//return sendXmlCstNo;
		}
		public void setSendXmlCstNo(String sendXmlCstNo) {
			this.sendXmlCstNo = sendXmlCstNo;
		}
		public String getSendXmlCstCardNo() {
			return StringUtils.defaultString(sendXmlCstCardNo, "");
			//return sendXmlCstCardNo;
		}
		public void setSendXmlCstCardNo(String sendXmlCstCardNo) {
			this.sendXmlCstCardNo = sendXmlCstCardNo;
		}
		public String getSendXmlDcEmpAmt() {
			return StringUtils.defaultString(sendXmlDcEmpAmt, "");
			//return sendXmlDcEmpAmt;
		}
		public void setSendXmlDcEmpAmt(String sendXmlDcEmpAmt) {
			this.sendXmlDcEmpAmt = sendXmlDcEmpAmt;
		}
		
		
		private String recvXml = "";
		private String recvXmlRetcd = "";
		private String recvXmlSrid = "";
		private String recvXmlBillCd = "";
		private String recvXmlPosNo = "";
		private String recvXmlSaleDate = "";
		private String recvXmlShopCd = "";	
		
		public String getSendXml() {
			return sendXml;
		}
		public void setSendXml(String sendXml) {
			this.sendXml = sendXml;
		}
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
		
		
		
		

}
