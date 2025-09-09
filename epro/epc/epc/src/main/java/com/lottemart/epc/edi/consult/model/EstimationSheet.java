package com.lottemart.epc.edi.consult.model;

public class EstimationSheet {
	/*PID					#pid#
	, SEQ                   	, #seq#
	, ES_PC                 	, #pc#
	, ES_STANDARD           	, #standard#
	, ES_PROD_CD            	, #product_id#
	, ES_PROD_NM            	, #product_name#
	, ES_PACK_TYPE          	, #pack#
	, ES_GRADE              	, #rating#
	, ES_PRICE              	, #estimate_price#
	, ES_ORGIN              	, #origin#
	, ES_DETAIL             	, #remark#
	, REG_DATE              	, SYSDATE
	*/
	private String pid;
	private String seq; 
	private String esPc; 
	private String esStandard; 
	private String esProdCd; 
	private String esProdNm; 
	private String esPackType; 
	private String esGrade; 
	private String esPrice; 
	private String esOrgin; 
	private String esDetail; 
	private String regDate;
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getEsPc() {
		return esPc;
	}
	public void setEsPc(String esPc) {
		this.esPc = esPc;
	}
	public String getEsStandard() {
		return esStandard;
	}
	public void setEsStandard(String esStandard) {
		this.esStandard = esStandard;
	}
	public String getEsProdCd() {
		return esProdCd;
	}
	public void setEsProdCd(String esProdCd) {
		this.esProdCd = esProdCd;
	}
	public String getEsProdNm() {
		return esProdNm;
	}
	public void setEsProdNm(String esProdNm) {
		this.esProdNm = esProdNm;
	}
	public String getEsPackType() {
		return esPackType;
	}
	public void setEsPackType(String esPackType) {
		this.esPackType = esPackType;
	}
	public String getEsGrade() {
		return esGrade;
	}
	public void setEsGrade(String esGrade) {
		this.esGrade = esGrade;
	}
	public String getEsPrice() {
		return esPrice;
	}
	public void setEsPrice(String esPrice) {
		this.esPrice = esPrice;
	}
	public String getEsDetail() {
		return esDetail;
	}
	public void setEsDetail(String esDetail) {
		this.esDetail = esDetail;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getEsOrgin() {
		return esOrgin;
	}
	public void setEsOrgin(String esOrgin) {
		this.esOrgin = esOrgin;
	}
}
