package com.lottemart.epc.edi.weborder.model;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author jyLee
 * @Description : 발주정보 리스트 조회
 * @Class : TedOrdList
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      		수정자 		          수정내용
 *  -------    --------    ---------------------------
 *  2014.08.07	jyLee	최초작성
 * </pre>
 * @version : 1.0
 */

public class TedOrdList extends PagingVO implements Serializable {

	private static final Logger logger = LoggerFactory.getLogger(TedOrdList.class);


	/**
	 *
	 */
	private static final long serialVersionUID = 6497258913154902055L;
	/** 점포코드 */
	private String strCd;
	/** 발주일자 */
	private String ordDy;
	/** 상품코드 */
	private String prodCd;
	/** 상품코드 다중처리 */
	private String[] prodCds;
	/** 상품명 */
	private String prodNm;
	/** 단축명 */
	private String shortNm;
	/** 협력업체코드 */
	private String venCd;
	/** 협력업체코드배열  */
	private String[] venCds;
	/** 협력업체명 */
	private String venNm;
	/** 판매코드 */
	private String srcmkCd;
	/** 행사코드 */
	private String evtCd;
	/** 상품패턴구분 */
	private String prodPatFg;
	/** 상품규격 */
	private String prodStd;
	/** 환산치 */
	private String convRt;
	/** 발주입수 */
	private String ordIpsu;
	/** 발주원가 */
	private String ordBuyPrc;
	/** 발주매가 */
	private String ordSalePrc;
	/** 발주단위 */
	private String ordUnit;
	/** 발주단위명 */
	private String ordUnitNm;
	/** 권고발주량 */
	private String rcmdOrdQty;
	/** 거래형태구분 */
	private String trdTypFg;
	/** 루트구분 */
	private String routeFg;
	/** 센터입하일자 */
	private String ctrArrDy;
	/** 점포납품일자 */
	private String strSplyDy;
	/** 발주등록번호 */
	private String ordReqNo;
	/** 발주순번 */
	private String ordVseq;
	/** 발주총수량 */
	private String ordTotQty;
	/** 발주총금액 */
	private String ordTotPrc;
	/** 발주일재고총수량 */
	private String ordTotStkQty;
	/** 발주전체수량 */
	private String ordTotAllQty;
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
	/** 세분류코드 */
	private String l4Cd;
	/** 발주수량 */
	private String ordQty;
	/** 발주확정수량 */
	private String ordCfmQty;
	/** 발주일재고수량 */
	private String ordStkQty;
	/** 등록상태코드 */
	private String regStsCd;
	/**등록상태상세내용*/
	private String regStsCdDetail;
	/** MD조정구분코드 */
	private String mdModCd;
	/** 등록여부 */
	private String upYn;
	/** 발주진행상태명칭 */
	private String cdNm;
	/** 점포명칭 */
	private String strNm;


	/** 발주총수량합계 */
	private String ordTotQtySum;
	/** 발주총금액합계 */
	private String ordTotPrcSum;
	/** 발주일재고총수량합계 */
	private String ordTotStkQtySum;
	/** 점보 EA 합계 수량  */
	private String eaQtySum;
	/** 충수량EA합계 */
	private String ordTotAllQtySum;

	/**점포별발주가능 상품수량*/
	private String prodTotSum;
	/**점포별발주등록 상품수량*/
	private String prodAddSum;



	/** 단가수량 */
	private String eaQty;
	/** 금액 */
	private String prc;

	/** EA/금액 */
	private String eaPrc;

	/** 로우 넘버*/
	private String rowNum;

	/** 신규/수정 유무*/
	private String saveFg;
	/** PROD_CD 유무 확인용 */
	private String bProdCd;

	/** 점포 갯수 */
	private String strCnt;
	/** 상품 갯수 */
	private String prodCnt;
	/** 등록상태구분명 */
	private String regStsNm;
	/** MD 조정구분명*/
	private String mdModNm;


	/**SP OUT*/
	private String o_ret			;
	private String o_proc_cmt		;


	/** 점포별 등록갯수 */
	private String totCnt;
	/** 점포별 미전송 갯수 (REG_STS_CD : 0) */
	private String notSendSum;
	/** 점포별 정상 전송갯수 (REG_STS_CD : 1) */
	private String sucSendSum;
	/** 점포별 발주중단 또는 누락 전송 (REG_STS_CD : 2) */
	private String nonSendSum;
	/** 점포별 마감 미전송 갯수 (REG_STS_CD : 4) */
	private String timSendSum;
	/** 점포별 기타오류 미전송 갯수 (REG_STS_CD : 9) */
	private String falSendSum;
	/**  오류전체 미전송 갯수 (REG_STS_CD : 2+4+9) */
	private String falAllSum;


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
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	public String[] getProdCds() {
		return prodCds;
	}
	public void setProdCds(String[] prodCds) {
		this.prodCds = prodCds;
	}
	public String getProdNm() {
		return prodNm;
	}
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}
	public String getShortNm() {
		return shortNm;
	}
	public void setShortNm(String shortNm) {
		this.shortNm = shortNm;
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
	public String getSrcmkCd() {
		return srcmkCd;
	}
	public void setSrcmkCd(String srcmkCd) {
		this.srcmkCd = srcmkCd;
	}
	public String getEvtCd() {
		return evtCd;
	}
	public void setEvtCd(String evtCd) {
		this.evtCd = evtCd;
	}
	public String getProdPatFg() {
		return prodPatFg;
	}
	public void setProdPatFg(String prodPatFg) {
		this.prodPatFg = prodPatFg;
	}
	public String getProdStd() {
		return prodStd;
	}
	public void setProdStd(String prodStd) {
		this.prodStd = prodStd;
	}
	public String getConvRt() {
		return convRt;
	}
	public void setConvRt(String convRt) {
		this.convRt = convRt;
	}
	public String getOrdIpsu() {
		return ordIpsu;
	}
	public void setOrdIpsu(String ordIpsu) {
		this.ordIpsu = ordIpsu;
	}
	public String getOrdBuyPrc() {
		return ordBuyPrc;
	}
	public void setOrdBuyPrc(String ordBuyPrc) {
		this.ordBuyPrc = ordBuyPrc;
	}
	public String getOrdSalePrc() {
		return ordSalePrc;
	}
	public void setOrdSalePrc(String ordSalePrc) {
		this.ordSalePrc = ordSalePrc;
	}
	public String getOrdUnit() {
		return ordUnit;
	}
	public void setOrdUnit(String ordUnit) {
		this.ordUnit = ordUnit;
	}
	public String getRcmdOrdQty() {
		return rcmdOrdQty;
	}
	public void setRcmdOrdQty(String rcmdOrdQty) {
		this.rcmdOrdQty = rcmdOrdQty;
	}
	public String getTrdTypFg() {
		return trdTypFg;
	}
	public void setTrdTypFg(String trdTypFg) {
		this.trdTypFg = trdTypFg;
	}
	public String getRouteFg() {
		return routeFg;
	}
	public void setRouteFg(String routeFg) {
		this.routeFg = routeFg;
	}
	public String getCtrArrDy() {
		return ctrArrDy;
	}
	public void setCtrArrDy(String ctrArrDy) {
		this.ctrArrDy = ctrArrDy;
	}
	public String getStrSplyDy() {
		return strSplyDy;
	}
	public void setStrSplyDy(String strSplyDy) {
		this.strSplyDy = strSplyDy;
	}
	public String getOrdReqNo() {
		return ordReqNo;
	}
	public void setOrdReqNo(String ordReqNo) {
		this.ordReqNo = ordReqNo;
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
	public String getL4Cd() {
		return l4Cd;
	}
	public void setL4Cd(String l4Cd) {
		this.l4Cd = l4Cd;
	}
	public String getOrdQty() {
		return ordQty;
	}
	public void setOrdQty(String ordQty) {
		this.ordQty = ordQty;
	}
	public String getOrdCfmQty() {
		return ordCfmQty;
	}
	public void setOrdCfmQty(String ordCfmQty) {
		this.ordCfmQty = ordCfmQty;
	}
	public String getOrdStkQty() {
		return ordStkQty;
	}
	public void setOrdStkQty(String ordStkQty) {
		this.ordStkQty = ordStkQty;
	}
	public String getRegStsCd() {
		return regStsCd;
	}
	public void setRegStsCd(String regStsCd) {
		this.regStsCd = regStsCd;
	}
	public String getMdModCd() {
		return mdModCd;
	}
	public void setMdModCd(String mdModCd) {
		this.mdModCd = mdModCd;
	}
	public String getUpYn() {
		return upYn;
	}
	public void setUpYn(String upYn) {
		this.upYn = upYn;
	}
	public String getCdNm() {
		return cdNm;
	}
	public void setCdNm(String cdNm) {
		this.cdNm = cdNm;
	}
	public String getStrNm() {
		return strNm;
	}
	public void setStrNm(String strNm) {
		this.strNm = strNm;
	}

	public String getOrdTotPrcSum() {
		return ordTotPrcSum;
	}
	public void setOrdTotPrcSum(String ordTotPrcSum) {
		this.ordTotPrcSum = ordTotPrcSum;
	}
	public String getOrdTotStkQtySum() {
		return ordTotStkQtySum;
	}
	public void setOrdTotStkQtySum(String ordTotStkQtySum) {
		this.ordTotStkQtySum = ordTotStkQtySum;
	}

	public String getOrdTotQtySum() {
		return ordTotQtySum;
	}
	public void setOrdTotQtySum(String ordTotQtySum) {
		this.ordTotQtySum = ordTotQtySum;
	}


	public String getEaQty() {
		return eaQty;
	}
	public void setEaQty(String eaQty) {
		this.eaQty = eaQty;
	}
	public String getPrc() {
		return prc;
	}
	public void setPrc(String prc) {
		this.prc = prc;
	}
	public String getOrdTotAllQty() {
		return ordTotAllQty;
	}
	public void setOrdTotAllQty(String ordTotAllQty) {
		this.ordTotAllQty = ordTotAllQty;
	}
	public String getRowNum() {
		return rowNum;
	}
	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}

	public String getEaQtySum() {
		return eaQtySum;
	}
	public void setEaQtySum(String eaQtySum) {
		this.eaQtySum = eaQtySum;
	}
	public String getOrdTotAllQtySum() {
		return ordTotAllQtySum;
	}
	public void setOrdTotAllQtySum(String ordTotAllQtySum) {
		this.ordTotAllQtySum = ordTotAllQtySum;
	}
	public String getProdTotSum() {
		return prodTotSum;
	}
	public void setProdTotSum(String prodTotSum) {
		this.prodTotSum = prodTotSum;
	}
	public String getProdAddSum() {
		return prodAddSum;
	}
	public void setProdAddSum(String prodAddSum) {
		this.prodAddSum = prodAddSum;
	}
	public String getEaPrc() {
		return eaPrc;
	}
	public void setEaPrc(String eaPrc) {
		this.eaPrc = eaPrc;
	}
	public String getSaveFg() {
		return saveFg;
	}
	public void setSaveFg(String saveFg) {
		this.saveFg = saveFg;
	}
	public String getbProdCd() {
		return bProdCd;
	}
	public void setbProdCd(String bProdCd) {
		this.bProdCd = bProdCd;
	}
	public String getStrCnt() {
		return strCnt;
	}
	public void setStrCnt(String strCnt) {
		this.strCnt = strCnt;
	}
	public String getProdCnt() {
		return prodCnt;
	}
	public void setProdCnt(String prodCnt) {
		this.prodCnt = prodCnt;
	}
	public String getRegStsNm() {
		return regStsNm;
	}
	public void setRegStsNm(String regStsNm) {
		this.regStsNm = regStsNm;
	}
	public String getMdModNm() {
		return mdModNm;
	}
	public void setMdModNm(String mdModNm) {
		this.mdModNm = mdModNm;
	}

	public String getOrdUnitNm() {
		return ordUnitNm;
	}
	public void setOrdUnitNm(String ordUnitNm) {
		this.ordUnitNm = ordUnitNm;
	}


	public String getO_ret() {
		return o_ret;
	}
	public void setO_ret(String o_ret) {
		this.o_ret = o_ret;
	}
	public String getO_proc_cmt() {
		return o_proc_cmt;
	}
	public void setO_proc_cmt(String o_proc_cmt) {
		this.o_proc_cmt = o_proc_cmt;
	}

	public String[] getVenCds() {
		return venCds;
	}
	public void setVenCds(String[] venCds) {
		this.venCds = venCds;
	}

	public String getRegStsCdDetail() {
		return regStsCdDetail;
	}
	public void setRegStsCdDetail(String regStsCdDetail) {
		this.regStsCdDetail = regStsCdDetail;
	}


	public Integer getOrdQtyNum() {
		int rtnNum = 0;
		try{
			rtnNum = Integer.parseInt(ordCfmQty);
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		return rtnNum;
	}
	public String getTotCnt() {
		return totCnt;
	}
	public void setTotCnt(String totCnt) {
		this.totCnt = totCnt;
	}
	public String getNotSendSum() {
		return notSendSum;
	}
	public void setNotSendSum(String notSendSum) {
		this.notSendSum = notSendSum;
	}
	public String getSucSendSum() {
		return sucSendSum;
	}
	public void setSucSendSum(String sucSendSum) {
		this.sucSendSum = sucSendSum;
	}
	public String getNonSendSum() {
		return nonSendSum;
	}
	public void setNonSendSum(String nonSendSum) {
		this.nonSendSum = nonSendSum;
	}
	public String getTimSendSum() {
		return timSendSum;
	}
	public void setTimSendSum(String timSendSum) {
		this.timSendSum = timSendSum;
	}
	public String getFalSendSum() {
		return falSendSum;
	}
	public void setFalSendSum(String falSendSum) {
		this.falSendSum = falSendSum;
	}
	public String getFalAllSum() {
		return falAllSum;
	}
	public void setFalAllSum(String falAllSum) {
		this.falAllSum = falAllSum;
	}




}
