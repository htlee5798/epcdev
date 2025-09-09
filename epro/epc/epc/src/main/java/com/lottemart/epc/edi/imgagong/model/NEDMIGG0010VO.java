package com.lottemart.epc.edi.imgagong.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @Class Name : NEDMIGG0010VO
 * @Description : 임가공 출고 관리 VO
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 * 수정일			수정자          		수정내용
 * ----------	-----------		---------------------------
 * 2018.11.20	SHIN SE JIN		최초생성
 * 
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */


public class NEDMIGG0010VO implements Serializable {

	private static final long serialVersionUID = 4968945248239770511L;
	
	/** 전기일자 */
	private String budat;
    /** 점포 */
	private String werks;
    /** 점포명 */
	private String name1;
    /** 관리번호 */
	private String eblnr;
    /** SEQ */
	private String giSeq;
    /** 원재료 코드 */
	private String giMatnr;
    /** 원재료명 */
	private String giMaktx;
    /** 출고 단위 */
	private String giMeins;
    /** 출고 수량 */
	private String giQty;
    /** 기존 사용수량 */
	private String useQty;
    /** 기존 수율수량 */
	private String lossQty;
    /** 잔여재고 수량 */
	private String remainQty;
	/** 입고 순번 */
	private String grSeq;
    /** 완제품 코드 */
	private String grMatnr;
    /** 완제품명 */
	private String grMaktx;
    /** 입고 단위 */
	private String grMeins;
    /** 기존 입고수량 */
	private String grQty;
    /** 사용수량 */
	private String useNewQty;
	/** 수율수량 */
	private String lossNewQty;
	/** 입고수량 */
	private String grNewQty;
	/** 납품예정일 */
	private String splyDw;
	/** 메모 */
	private String memo;
	
	/** 등록일자 */
	private String regDt;
	/** 작업자ID */
	private String workId;
	/** 작업구분(등록:I, 삭제:D) */
	private String div;
	
	/** 입/출고 리스트 */
	private ArrayList<NEDMIGG0010VO> dataList;
	
	
	public String getBudat() {
		return budat;
	}
	public void setBudat(String budat) {
		this.budat = budat;
	}
	public String getWerks() {
		return werks;
	}
	public void setWerks(String werks) {
		this.werks = werks;
	}
	public String getName1() {
		return name1;
	}
	public void setName1(String name1) {
		this.name1 = name1;
	}
	public String getEblnr() {
		return eblnr;
	}
	public void setEblnr(String eblnr) {
		this.eblnr = eblnr;
	}
	public String getGiSeq() {
		return giSeq;
	}
	public void setGiSeq(String giSeq) {
		this.giSeq = giSeq;
	}
	public String getGiMatnr() {
		return giMatnr;
	}
	public void setGiMatnr(String giMatnr) {
		this.giMatnr = giMatnr;
	}
	public String getGiMaktx() {
		return giMaktx;
	}
	public void setGiMaktx(String giMaktx) {
		this.giMaktx = giMaktx;
	}
	public String getGiMeins() {
		return giMeins;
	}
	public void setGiMeins(String giMeins) {
		this.giMeins = giMeins;
	}
	public String getGiQty() {
		return giQty;
	}
	public void setGiQty(String giQty) {
		this.giQty = giQty;
	}
	public String getUseQty() {
		return useQty;
	}
	public void setUseQty(String useQty) {
		this.useQty = useQty;
	}
	public String getLossQty() {
		return lossQty;
	}
	public void setLossQty(String lossQty) {
		this.lossQty = lossQty;
	}
	public String getRemainQty() {
		return remainQty;
	}
	public void setRemainQty(String remainQty) {
		this.remainQty = remainQty;
	}
	public String getGrSeq() {
		return grSeq;
	}
	public void setGrSeq(String grSeq) {
		this.grSeq = grSeq;
	}
	public String getGrMatnr() {
		return grMatnr;
	}
	public void setGrMatnr(String grMatnr) {
		this.grMatnr = grMatnr;
	}
	public String getGrMaktx() {
		return grMaktx;
	}
	public void setGrMaktx(String grMaktx) {
		this.grMaktx = grMaktx;
	}
	public String getGrMeins() {
		return grMeins;
	}
	public void setGrMeins(String grMeins) {
		this.grMeins = grMeins;
	}
	public String getGrQty() {
		return grQty;
	}
	public void setGrQty(String grQty) {
		this.grQty = grQty;
	}
	public String getUseNewQty() {
		return useNewQty;
	}
	public void setUseNewQty(String useNewQty) {
		this.useNewQty = useNewQty;
	}
	public String getLossNewQty() {
		return lossNewQty;
	}
	public void setLossNewQty(String lossNewQty) {
		this.lossNewQty = lossNewQty;
	}
	public String getGrNewQty() {
		return grNewQty;
	}
	public void setGrNewQty(String grNewQty) {
		this.grNewQty = grNewQty;
	}
	public String getSplyDw() {
		return splyDw;
	}
	public void setSplyDw(String splyDw) {
		this.splyDw = splyDw;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getWorkId() {
		return workId;
	}
	public void setWorkId(String workId) {
		this.workId = workId;
	}
	public ArrayList<NEDMIGG0010VO> getDataList() {
		return dataList;
	}
	public void setDataList(ArrayList<NEDMIGG0010VO> dataList) {
		this.dataList = dataList;
	}
	public String getDiv() {
		return div;
	}
	public void setDiv(String div) {
		this.div = div;
	}
	
}
