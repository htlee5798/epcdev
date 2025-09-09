package com.lottemart.epc.delivery.model;

import java.io.Serializable;

public class PDCPHPC0001VO implements Serializable {

	/** 
	 * @see com.lottemart.epc.delivery.model
	 * @Method Name  : PDCPHPC0001VO.java
	 * @since      : 2011. 12. 07
	 * @author     : binary27
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.model
	 * @description : 
	 * @return
	*/
	private static final long serialVersionUID = 2627298651226651456L;
	
	
	/** 등록/수정아이디 */
    private String regId;
    private String modId;
	
    /** 운송장번호 */
    private String invoiceNo;
    
    /** 해피콜내용 */
    private String hpclContent;
    
    /** 해피콜결과코드 */
    private String hpclRsltCd;
    
    /** 희망배송일자 */
    private String hopeDeliDy;
    
    /** 보내신분 이름 */
    private String sendNm;

	/** 보내신분 핸드폰번호 */
    private String sendPsnCellNo;
    
    /** 보내신분 전화번호 */
    private String sendPsnTelNo;

	/** 보내신분 우편번호 */
    private String sendPsnZipNo;
    
	/** 보내신분 우편번호 순번 */
    private String sendPsnZipNoSeq;
    
    /** 보내신분 주소 */
    private String sendPsnZipAddr;
    
    /** 보내신분 상세주소 */
    private String sendPsnAddr;
    
    /** 받으실분 이름 */
    private String recvNm;
    
    /** 받으실분 핸드폰번호 */
    private String recvPsnCellNo;
    
    /** 받으실분 전화번호 */
    private String recvPsnTelNo;

	/** 받으실분 우편번호 */
    private String recvPsnZipNo;
    
	/** 받으실분 우편번호 순번 */
    private String recvPsnZipNoSeq;
    
    /** 받으실분 주소 */
    private String recvPsnZipAddr;
    
    /** 받으실분 상세주소 */
    private String recvPsnAddr;
    
    /** 배송메세지 */
    private String deliMsg;
    
    /** 배송점포코드 */
    private String deliStrCd;

	/** 주소 수정 여부 */
    private String updateRecv;
    
    /** 출고일 */
    private String deliOrderDy;
    
    
    
    public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getModId() {
		return modId;
	}

	public void setModId(String modId) {
		this.modId = modId;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getHpclContent() {
		return hpclContent;
	}

	public void setHpclContent(String hpclContent) {
		this.hpclContent = hpclContent;
	}

	public String getHpclRsltCd() {
		return hpclRsltCd;
	}

	public void setHpclRsltCd(String hpclRsltCd) {
		this.hpclRsltCd = hpclRsltCd;
	}

	public String getHopeDeliDy() {
		return hopeDeliDy;
	}

	public void setHopeDeliDy(String hopeDeliDy) {
		this.hopeDeliDy = hopeDeliDy;
	}

	public String getSendNm() {
		return sendNm;
	}

	public void setSendNm(String sendNm) {
		this.sendNm = sendNm;
	}

	public String getSendPsnCellNo() {
		return sendPsnCellNo;
	}

	public void setSendPsnCellNo(String sendPsnCellNo) {
		this.sendPsnCellNo = sendPsnCellNo;
	}

	public String getSendPsnTelNo() {
		return sendPsnTelNo;
	}

	public void setSendPsnTelNo(String sendPsnTelNo) {
		this.sendPsnTelNo = sendPsnTelNo;
	}

	public String getSendPsnZipNo() {
		return sendPsnZipNo;
	}

	public void setSendPsnZipNo(String sendPsnZipNo) {
		this.sendPsnZipNo = sendPsnZipNo;
	}

	public String getSendPsnZipNoSeq() {
		return sendPsnZipNoSeq;
	}

	public void setSendPsnZipNoSeq(String sendPsnZipNoSeq) {
		this.sendPsnZipNoSeq = sendPsnZipNoSeq;
	}

	public String getSendPsnZipAddr() {
		return sendPsnZipAddr;
	}

	public void setSendPsnZipAddr(String sendPsnZipAddr) {
		this.sendPsnZipAddr = sendPsnZipAddr;
	}

	public String getSendPsnAddr() {
		return sendPsnAddr;
	}

	public void setSendPsnAddr(String sendPsnAddr) {
		this.sendPsnAddr = sendPsnAddr;
	}
	
	public String getRecvNm() {
		return recvNm;
	}

	public void setRecvNm(String recvNm) {
		this.recvNm = recvNm;
	}
	
    public String getRecvPsnCellNo() {
		return recvPsnCellNo;
	}

	public void setRecvPsnCellNo(String recvPsnCellNo) {
		this.recvPsnCellNo = recvPsnCellNo;
	}

	public String getRecvPsnTelNo() {
		return recvPsnTelNo;
	}

	public void setRecvPsnTelNo(String recvPsnTelNo) {
		this.recvPsnTelNo = recvPsnTelNo;
	}
	
	public String getRecvPsnZipNo() {
		return recvPsnZipNo;
	}

	public void setRecvPsnZipNo(String recvPsnZipNo) {
		this.recvPsnZipNo = recvPsnZipNo;
	}

	public String getRecvPsnZipNoSeq() {
		return recvPsnZipNoSeq;
	}

	public void setRecvPsnZipNoSeq(String recvPsnZipNoSeq) {
		this.recvPsnZipNoSeq = recvPsnZipNoSeq;
	}

	public String getRecvPsnZipAddr() {
		return recvPsnZipAddr;
	}

	public void setRecvPsnZipAddr(String recvPsnZipAddr) {
		this.recvPsnZipAddr = recvPsnZipAddr;
	}

	public String getRecvPsnAddr() {
		return recvPsnAddr;
	}

	public void setRecvPsnAddr(String recvPsnAddr) {
		this.recvPsnAddr = recvPsnAddr;
	}
	
    public String getDeliStrCd() {
		return deliStrCd;
	}

	public void setDeliStrCd(String deliStrCd) {
		this.deliStrCd = deliStrCd;
	}
	
	public String getUpdateRecv() {
		return updateRecv;
	}

	public void setUpdateRecv(String updateRecv) {
		this.updateRecv = updateRecv;
	}

	public String getDeliMsg() {
		return deliMsg;
	}

	public void setDeliMsg(String deliMsg) {
		this.deliMsg = deliMsg;
	}

	public String getDeliOrderDy() {
		return deliOrderDy;
	}

	public void setDeliOrderDy(String deliOrderDy) {
		this.deliOrderDy = deliOrderDy;
	}



}
