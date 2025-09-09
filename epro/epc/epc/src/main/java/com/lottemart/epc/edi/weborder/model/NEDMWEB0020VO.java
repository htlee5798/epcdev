package com.lottemart.epc.edi.weborder.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @Class Name : NEDMWEB0020VO
 * @Description : 상품별 발주 등록 VO Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      		수정자          		수정내용
 *  -        -
 * 2015.12.07	O YEUN KWON	  최초생성
 * 
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */

public class NEDMWEB0020VO extends PagingVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6715413512908899412L;
	
	
    private String rowNum; 			    		// 로우넘버			
    private String prodCd; 			    		// 상품코드			
    private String ordDy; 			    		// 발주일자			
    private String srcmkCd; 			    	// 판매코드			
    private String prodNm; 			    		// 상품명				
    private String prodStd; 			    	// 상품규격			
    private String ordIpsu; 			    	// 발주입수			
    private String ordUnit; 			    	// 발주단위			
    private String bProdCd; 			    	// 발주등록번호		
    private String strCd; 			    		// 점포코드			
    private String venNm; 			    		// 협력업체명			
    private String strNm; 			    		// 점포이름			
    private String ordBuyPrc; 		    		// 발주원가			
    private String ordQty; 			    		// 발주수량			
    private String ordReqNo; 		    		// 발주등록번호		
    private String ordTotQtySum; 			    // 발주총수량합계			
    private String ordTotPrcSum; 			    // 발주총금액합계			
    private String ordTotAllQtySum; 		    // 충수량EA합계		
    private String venCd;			    // 조회조건에서 선택될 협력 업체코드
    private String areaCd;
    private String workGb;			    // 작업구분
    private String ordTotQty; 		    // 발주총수량		
	private String ordTotPrc; 		    // 발주총금액		
	private String ordTotAllQty; 	    // 발주전체총수량	
    private String ordVseq;				// 발주순번 
	private String ordTotStkQty;		// 발주일재고총수량 
	private String procEmpNo;			// 처리사번 
	private String regDt;				// 등록일시 
	private String modDt;				// 수정일시
	private String ordPrgsCd; 		    // 발주진행상태코드
	private String regId;			    // 등록자아이디 
	private String modId;			    // 수정자아이디 
    
	private ArrayList<NEDMWEB0020VO> tedPoOrdMstList;
	private ArrayList<NEDMWEB0020VO> tedOrdList;
	   
	   
	public String getRowNum() {
		return rowNum;
	}
	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	public String getOrdDy() {
		return ordDy;
	}
	public void setOrdDy(String ordDy) {
		this.ordDy = ordDy;
	}
	public String getSrcmkCd() {
		return srcmkCd;
	}
	public void setSrcmkCd(String srcmkCd) {
		this.srcmkCd = srcmkCd;
	}
	public String getProdNm() {
		return prodNm;
	}
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}
	public String getProdStd() {
		return prodStd;
	}
	public void setProdStd(String prodStd) {
		this.prodStd = prodStd;
	}
	public String getOrdIpsu() {
		return ordIpsu;
	}
	public void setOrdIpsu(String ordIpsu) {
		this.ordIpsu = ordIpsu;
	}
	public String getOrdUnit() {
		return ordUnit;
	}
	public void setOrdUnit(String ordUnit) {
		this.ordUnit = ordUnit;
	}
	public String getbProdCd() {
		return bProdCd;
	}
	public void setbProdCd(String bProdCd) {
		this.bProdCd = bProdCd;
	}
	public String getStrCd() {
		return strCd;
	}
	public void setStrCd(String strCd) {
		this.strCd = strCd;
	}
	public String getVenNm() {
		return venNm;
	}
	public void setVenNm(String venNm) {
		this.venNm = venNm;
	}
	public String getStrNm() {
		return strNm;
	}
	public void setStrNm(String strNm) {
		this.strNm = strNm;
	}
	public String getOrdBuyPrc() {
		return ordBuyPrc;
	}
	public void setOrdBuyPrc(String ordBuyPrc) {
		this.ordBuyPrc = ordBuyPrc;
	}
	public String getOrdQty() {
		return ordQty;
	}
	public void setOrdQty(String ordQty) {
		this.ordQty = ordQty;
	}
	public String getOrdReqNo() {
		return ordReqNo;
	}
	public void setOrdReqNo(String ordReqNo) {
		this.ordReqNo = ordReqNo;
	}
	public String getOrdTotQtySum() {
		return ordTotQtySum;
	}
	public void setOrdTotQtySum(String ordTotQtySum) {
		this.ordTotQtySum = ordTotQtySum;
	}
	public String getOrdTotPrcSum() {
		return ordTotPrcSum;
	}
	public void setOrdTotPrcSum(String ordTotPrcSum) {
		this.ordTotPrcSum = ordTotPrcSum;
	}
	public String getOrdTotAllQtySum() {
		return ordTotAllQtySum;
	}
	public void setOrdTotAllQtySum(String ordTotAllQtySum) {
		this.ordTotAllQtySum = ordTotAllQtySum;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public ArrayList<NEDMWEB0020VO> getTedOrdList() {
		if (this.tedOrdList != null) {
			ArrayList<NEDMWEB0020VO> ret = new ArrayList<NEDMWEB0020VO>();
			for (int i = 0; i < tedOrdList.size(); i++) {
				ret.add(i, this.tedOrdList.get(i));
			}
			
			return ret;
		} else {
			return null;
		}
	}

	public void setTedOrdList(ArrayList<NEDMWEB0020VO> tedOrdList) {
		if (tedOrdList != null) {
			this.tedOrdList = new ArrayList<NEDMWEB0020VO>();
			for (int i = 0; i < tedOrdList.size();i++) {
				this.tedOrdList.add(i, tedOrdList.get(i));
			}
		} else {
			this.tedOrdList = null;
		}
	}
	public ArrayList<NEDMWEB0020VO> getTedPoOrdMstList() {
		if (this.tedPoOrdMstList != null) {
			ArrayList<NEDMWEB0020VO> ret = new ArrayList<NEDMWEB0020VO>();
			for (int i = 0; i < tedPoOrdMstList.size(); i++) {
				ret.add(i, this.tedPoOrdMstList.get(i));
			}
			
			return ret;
		} else {
			return null;
		}
	}

	public void setTedPoOrdMstList(ArrayList<NEDMWEB0020VO> tedPoOrdMstList) {
		if (tedPoOrdMstList != null) {
			this.tedPoOrdMstList = new ArrayList<NEDMWEB0020VO>();
			for (int i = 0; i < tedPoOrdMstList.size();i++) {
				this.tedPoOrdMstList.add(i, tedPoOrdMstList.get(i));
			}
		} else {
			this.tedPoOrdMstList = null;
		}
	}
	public String getVenCd() {
		return venCd;
	}
	public void setVenCd(String venCd) {
		this.venCd = venCd;
	}
	public String getAreaCd() {
		return areaCd;
	}
	public void setAreaCd(String areaCd) {
		this.areaCd = areaCd;
	}
	public String getWorkGb() {
		return workGb;
	}
	public void setWorkGb(String workGb) {
		this.workGb = workGb;
	}
	public String getOrdVseq() {
		return ordVseq;
	}
	public void setOrdVseq(String ordVseq) {
		this.ordVseq = ordVseq;
	}
	public String getOrdTotStkQty() {
		return ordTotStkQty;
	}
	public void setOrdTotStkQty(String ordTotStkQty) {
		this.ordTotStkQty = ordTotStkQty;
	}
	public String getProcEmpNo() {
		return procEmpNo;
	}
	public void setProcEmpNo(String procEmpNo) {
		this.procEmpNo = procEmpNo;
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
	public String getOrdTotAllQty() {
		return ordTotAllQty;
	}
	public void setOrdTotAllQty(String ordTotAllQty) {
		this.ordTotAllQty = ordTotAllQty;
	}
	public String getOrdPrgsCd() {
		return ordPrgsCd;
	}
	public void setOrdPrgsCd(String ordPrgsCd) {
		this.ordPrgsCd = ordPrgsCd;
	}
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
	   
	   
}
