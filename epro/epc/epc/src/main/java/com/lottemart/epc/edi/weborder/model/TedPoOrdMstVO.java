package com.lottemart.epc.edi.weborder.model;

import java.io.Serializable;

/**
 * @author jyLee
 * @Description : 업체발주요청마스터
 * @Class : TedPoOrdMstVO
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      		수정자 		          수정내용
 *  -------    --------    ---------------------------
 *  2014.08.07	jyLee	최초작성
 * </pre>
 * @version : 1.0
 */

public class TedPoOrdMstVO extends PagingVO implements Serializable {



	/**
	 *
	 */
	private static final long serialVersionUID = -2674907221251939162L;
	/** 발주등록번호 삭제,확정 조건 */
	private String[] ordReqNos;
	/** 발주등록번호+상품코드 복합 삭제,확정 조건 */
	private String[] ordReqNoProdCds;

	/**발주 삭제 구분 1:마스터(점포별), 2:디테일(점포/상품별) */
	private String updateState ;

	/** 발주등록번호 */
	private String ordReqNo;
	/** 협력업체코드 */
	private String venCd;
	/** 협력업체명 */
	private String venNm;
	/** 점포코드 */
	private String strCd;
	/** 점포코드 */
	private String ordDy;
	/** 발주순번 */
	private String ordVseq;
	/** 발주총수량 */
	private String ordTotQty;
	/** 발주전체수량 */
	private String ordTotAllQty;
	/** 발주총금액 */
	private String ordTotPrc;
	/** 발주일재고총수량 */
	private String ordTotStkQty;
	/** 발주진행상태코드 */
	private String ordPrgsCd;
	/** 처리사번 */
	private String procEmpNo;
	/** 등록자아이디 */
	private String regId;
	/** 등록일시 */
	private String regDt;
	/** 수정일시 */
	private String modDt;
	/** 수정자아이디 */
	private String modId;



	public String[] getOrdReqNos() {
		return ordReqNos;
	}
	public void setOrdReqNos(String[] ordReqNos) {
		this.ordReqNos = ordReqNos;
	}
	public String[] getOrdReqNoProdCds() {
		return ordReqNoProdCds;
	}
	public void setOrdReqNoProdCds(String[] ordReqNoProdCds) {
		this.ordReqNoProdCds = ordReqNoProdCds;
	}

	public String getUpdateState() {
		return updateState;
	}
	public void setUpdateState(String updateState) {
		this.updateState = updateState;
	}
	public String getOrdReqNo() {
		return ordReqNo;
	}
	public void setOrdReqNo(String ordReqNo) {
		this.ordReqNo = ordReqNo;
	}
	public String getVenCd() {
		return venCd;
	}
	public void setVenCd(String venCd) {
		this.venCd = venCd;
	}
	public String getVenNm() {
		return venNm;
	}
	public void setVenNm(String venNm) {
		this.venNm = venNm;
	}
	public String getStrCd() {
		return strCd;
	}
	public void setStrCd(String strCd) {
		this.strCd = strCd;
	}
	public String getOrdDy() {
		return ordDy;
	}
	public void setOrdDy(String ordDy) {
		this.ordDy = ordDy;
	}
	public String getOrdVseq() {
		return ordVseq;
	}
	public void setOrdVseq(String ordVseq) {
		this.ordVseq = ordVseq;
	}
	public String getOrdTotQty() {
		return ordTotQty;
	}
	public void setOrdTotQty(String ordTotQty) {
		this.ordTotQty = ordTotQty;
	}
	public String getOrdTotAllQty() {
		return ordTotAllQty;
	}
	public void setOrdTotAllQty(String ordTotAllQty) {
		this.ordTotAllQty = ordTotAllQty;
	}
	public String getOrdTotPrc() {
		return ordTotPrc;
	}
	public void setOrdTotPrc(String ordTotPrc) {
		this.ordTotPrc = ordTotPrc;
	}
	public String getOrdTotStkQty() {
		return ordTotStkQty;
	}
	public void setOrdTotStkQty(String ordTotStkQty) {
		this.ordTotStkQty = ordTotStkQty;
	}
	public String getOrdPrgsCd() {
		return ordPrgsCd;
	}
	public void setOrdPrgsCd(String ordPrgsCd) {
		this.ordPrgsCd = ordPrgsCd;
	}
	public String getProcEmpNo() {
		return procEmpNo;
	}
	public void setProcEmpNo(String procEmpNo) {
		this.procEmpNo = procEmpNo;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getModDt() {
		return modDt;
	}
	public void setModDt(String modDt) {
		this.modDt = modDt;
	}
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}



}
