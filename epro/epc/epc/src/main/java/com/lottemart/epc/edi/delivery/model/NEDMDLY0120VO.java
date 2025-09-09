package com.lottemart.epc.edi.delivery.model;

import java.io.Serializable;

// @4UP 삭제 비표준 클래스
//import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

public class NEDMDLY0120VO implements Serializable {

	static final long serialVersionUID = -7320590807628733232L;

	/** PROXY Name */
	private String proxyNm;

	/** Parameter */
	private String param;

	/** 텍스트데이터 Desc */
	private String textData;

	private String slipNo;
//	private String strNm;
	private String srcmkCd;
//	private String prodNm;
//	private String promDy;
//	private String qty;
//	private String saleDy;
//	private String custNm;
//	private String custTelNo1;
//	private String custTelNo2;
//	private String custAddr;
//	private String acceptDy;
//	private String recvNm;
//	private String recvTelNo1;
//	private String recvTelNo2;
//	private String recvAddr;
//	private String remark;

	//private String formIdx;
	private String formStartDate;
	private String formEndDate;
	private String[] formHangmok;
	private String[] formEntpCd;
	private String[] formStrCd;


	public String getProxyNm() {
		return proxyNm;
	}

	public void setProxyNm(String proxyNm) {
		this.proxyNm = proxyNm;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getTextData() {
		return textData;
	}

	public void setTextData(String textData) {
		this.textData = textData;
	}

	/// S

	public String getSlipNo() { 	//slipNo
		return slipNo;
	}

	public void setSlipNo(String slipNo) {
		this.slipNo = slipNo;
	}
	
	
	public String getSrcmkCd() {	// srcmkCd
		return srcmkCd;
	}

	public void setSrcmkCd(String srcmkCd) {
		this.srcmkCd = srcmkCd;
	}
	/*
	public String getStrNm() {	// strNm
		return strNm;
	}

	public void setStrNm(String strNm) {
		this.strNm = strNm;
	}


	public String getProdNm() {	// prodNm
		return prodNm;
	}

	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}
	public String getPromDy() {	// promDY
		return promDy;
	}

	public void setPromDy(String promDy) {
		this.promDy = promDy;
	}
	public String getQty() {	// qty
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getSaleDy() {	//saleDy
		return saleDy;
	}

	public void setSaleDy(String saleDy) {
		this.saleDy = saleDy;
	}

	public String getCustNm() {	//custNm
		return custNm;
	}

	public void setCustNm(String custNm) {
		this.custNm = custNm;
	}
	public String getCustTelNo1() {	// custTelNo1
		return custTelNo1;
	}

	public void setCustTelNo1(String custTelNo1) {
		this.custTelNo1 = custTelNo1;
	}
	public String getCustTelNo2() {	// custTelNo2
		return custTelNo2;
	}

	public void setCustTelNo2(String custTelNo2) {
		this.custTelNo2 = custTelNo2;
	}

	public String getCustAddr() {	//custAddr
		return custAddr;
	}

	public void setCustAddr(String custAddr) {
		this.custAddr = custAddr;
	}

	public String getAcceptDy() {	// acceptDy
		return acceptDy;
	}

	public void setAcceptDy(String acceptDy) {
		this.acceptDy = acceptDy;
	}

	public String getRecvNm() {	// recvNm
		return recvNm;
	}

	public void setRecvNm(String recvNm) {
		this.recvNm = recvNm;
	}

	public String getRecvTelNo1() {	// recvTelNo1
		return recvTelNo1;
	}

	public void setRecvTelNo1(String recvTelNo1) {
		this.recvTelNo1 = recvTelNo1;
	}


	public String getRecvTelNo2() {	// recvTelNo2
		return recvTelNo2;
	}

	public void setRecvTelNo2(String recvTelNo2) {
		this.recvTelNo2 = recvTelNo2;
	}

	public String getRecvAddr() {	// recvAddr
		return recvAddr;
	}

	public void setRecvAddr(String recvAddr) {
		this.recvAddr = recvAddr;
	}

	public String getRemark() {	// remark
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
*/
	public String getFormStartDate() {	// recvAddr
		return formStartDate;
	}

	public void setFormStartDate(String formStartDate) {
		this.formStartDate = formStartDate;
	}

	public String getFormEndDate() {	// remark
		return formEndDate;
	}

	public void setFormEndDate(String formEndDate) {
		this.formEndDate = formEndDate;
	}

/*
	public String getFormIdx() {	// remark
		return formIdx;
	}

	public void setFormIdx(String formIdx) {
		this.formIdx = formIdx;
	}
*/

	public String[] getFormHangmok() {
		if (this.formHangmok != null) {
			String[] ret = new String[formHangmok.length];
			for (int i = 0; i < formHangmok.length; i++) {
				ret[i] = this.formHangmok[i];
			}
			return ret;
		} else {
			return null;
		}
	}

	public void setFormHangmok(String[] formHangmok) {
		 if (formHangmok != null) {
			 this.formHangmok = new String[formHangmok.length];
			 for (int i = 0; i < formHangmok.length; ++i) {
				 this.formHangmok[i] = formHangmok[i];
			 }
		 } else {
			 this.formHangmok = null;
		 }
	}


	public String[] getFormEntpCd() {
		if (this.formEntpCd != null) {
			String[] ret = new String[formEntpCd.length];
			for (int i = 0; i < formEntpCd.length; i++) {
				ret[i] = this.formEntpCd[i];
			}
			return ret;
		} else {
			return null;
		}
	}

	public void setFormEntpCd(String[] formEntpCd) {
		 if (formEntpCd != null) {
			 this.formEntpCd = new String[formEntpCd.length];
			 for (int i = 0; i < formEntpCd.length; ++i) {
				 this.formEntpCd[i] = formEntpCd[i];
			 }
		 } else {
			 this.formEntpCd = null;
		 }
	}

	public String[] getFormStrCd() {
		if (this.formStrCd != null) {
			String[] ret = new String[formStrCd.length];
			for (int i = 0; i < formStrCd.length; i++) {
				ret[i] = this.formStrCd[i];
			}
			return ret;
		} else {
			return null;
		}
	}

	public void setFormStrCd(String[] formStrCd) {
		 if (formStrCd != null) {
			 this.formStrCd = new String[formStrCd.length];
			 for (int i = 0; i < formStrCd.length; ++i) {
				 this.formStrCd[i] = formStrCd[i];
			 }
		 } else {
			 this.formStrCd = null;
		 }
	}
}
