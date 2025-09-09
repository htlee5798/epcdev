package com.lottemart.epc.edi.weborder.model;

import java.io.Serializable;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Class Name : NEDMWEB0210VO
 * @Description : 발주승인관리 VO Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      		수정자          		수정내용
 *  -        -
 * 2015.12.08	O YEUN KWON	  최초생성
 *
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */

public class NEDMWEB0210VO extends PagingVO implements Serializable {

	private static final Logger logger = LoggerFactory.getLogger(NEDMWEB0210VO.class);

	/**
	 *
	 */
	private static final long serialVersionUID = 6715413512908897485L;

	    private String venCd;  					// 협력업체코드
	    private String venNm; 					// 협력업체명
	    private String ordDy; 					// 발주일자
	    private String ordTotQty; 				// 발주총수량
	    private String ordTotPrc; 				// 발주총금액
	    private String ordTotAllQty; 			// 발주총금액
	    private String totCnt;				 	// 점포별 등록갯수
	    private String notSendSum;         	 	// 점포별 미전송 갯수 (REG_STS_CD : 0)
	    private String sucSendSum;         	 	// 점포별 정상 전송갯수 (REG_STS_CD : 1)
	    private String nonSendSum;         	 	// 점포별 발주중단 또는 누락 전송 (REG_STS_CD : 2)
	    private String timSendSum;         	 	// 점포별 마감 미전송 갯수 (REG_STS_CD : 4)
	    private String falSendSum;         	 	// 점포별 기타오류 미전송 갯수 (REG_STS_CD : 9)
	    private String falAllSum;         	 	// 점포별 전체오류 (REG_STS_CD : 9+4+2)
	    private String ordTotQtySum; 			// 발주총수량합계
	    private String ordTotPrcSum; 			// 발주총금액합계
	    private String ordTotAllQtySum; 		// EA 총수량
	    private String ordReqNo;  			    // 발주코드번호
	    private String strCd;  				    // 점포코드
	    private String strNm; 				    // 점포명
	    private String ordCfmQty; 			    // 발주확정수량
	    private String eaQty; 				    // EA/수량
	    private String eaPrc; 		            // EA/금액
	    private String ordPrgsCd;  			    // 발주진행상태코드
	    private String prodCd;  			    // 상품코드
	    private String prodNm;  			    // 상품명
	    private String mdModCd; 			    // 작업구분
	    private String srcmkCd; 			    // 판매코드
	    private String prodStd; 			    // 규격
	    private String ordIpsu; 		    	// 입수
	    private String ordUnit; 		    	// 단위
	    private String ordQty; 		        	// 발주수량
	    private String prc; 		        	// 금액
	    private String ordBuyPrc; 		    	// 발주원가
	    private String regStsCd; 		    	// 등록상태코드
	    private String regStsNm;        	 	// 일괄등록상태명칭
		private String regStsCdDetail;       	// 일괄등록상태상세
	    private String eaQtySum; 			 	// EA 합계수량
	    private String empNo; 			 		// 사원번호
	    private String modId;			   		// 수정자아이디
		private String saveFg;					// 신규/수정 유무
		private String updateState ;		    // 발주 삭제 구분 1:마스터(점포별), 2:디테일(점포/상품별)
		private String procEmpNo;				// 처리사번
		private String regStsfg;				// 등록상태  [1.전체, 2.정상, 3.오류, 4.미전송,오류]
		private String orderSendfg;				// 발주전송 목록선택[1.전체, 2.화면리스트]
		private String[] ordReqNos;				// 발주등록번호 삭제,확정 조건
		private String[] venCds;				// 로그인사용자의 협력업체코드 전체

		/**SP OUT*/
		private String o_ret			;
		private String o_proc_cmt		;

	    private ArrayList<NEDMWEB0210VO> tedOrdList;


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
		public String getOrdDy() {
			return ordDy;
		}
		public void setOrdDy(String ordDy) {
			this.ordDy = ordDy;
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
		public String getOrdReqNo() {
			return ordReqNo;
		}
		public void setOrdReqNo(String ordReqNo) {
			this.ordReqNo = ordReqNo;
		}
		public String getStrCd() {
			return strCd;
		}
		public void setStrCd(String strCd) {
			this.strCd = strCd;
		}
		public String getStrNm() {
			return strNm;
		}
		public void setStrNm(String strNm) {
			this.strNm = strNm;
		}
		public String getOrdCfmQty() {
			return ordCfmQty;
		}
		public void setOrdCfmQty(String ordCfmQty) {
			this.ordCfmQty = ordCfmQty;
		}
		public String getEaQty() {
			return eaQty;
		}
		public void setEaQty(String eaQty) {
			this.eaQty = eaQty;
		}
		public String getEaPrc() {
			return eaPrc;
		}
		public void setEaPrc(String eaPrc) {
			this.eaPrc = eaPrc;
		}
		public String getOrdPrgsCd() {
			return ordPrgsCd;
		}
		public void setOrdPrgsCd(String ordPrgsCd) {
			this.ordPrgsCd = ordPrgsCd;
		}
		public String getProdCd() {
			return prodCd;
		}
		public void setProdCd(String prodCd) {
			this.prodCd = prodCd;
		}
		public String getProdNm() {
			return prodNm;
		}
		public void setProdNm(String prodNm) {
			this.prodNm = prodNm;
		}
		public String getMdModCd() {
			return mdModCd;
		}
		public void setMdModCd(String mdModCd) {
			this.mdModCd = mdModCd;
		}
		public String getSrcmkCd() {
			return srcmkCd;
		}
		public void setSrcmkCd(String srcmkCd) {
			this.srcmkCd = srcmkCd;
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
		public String getOrdQty() {
			return ordQty;
		}
		public void setOrdQty(String ordQty) {
			this.ordQty = ordQty;
		}
		public String getPrc() {
			return prc;
		}
		public void setPrc(String prc) {
			this.prc = prc;
		}
		public String getOrdBuyPrc() {
			return ordBuyPrc;
		}
		public void setOrdBuyPrc(String ordBuyPrc) {
			this.ordBuyPrc = ordBuyPrc;
		}
		public String getRegStsCd() {
			return regStsCd;
		}
		public void setRegStsCd(String regStsCd) {
			this.regStsCd = regStsCd;
		}
		public String getRegStsNm() {
			return regStsNm;
		}
		public void setRegStsNm(String regStsNm) {
			this.regStsNm = regStsNm;
		}
		public String getRegStsCdDetail() {
			return regStsCdDetail;
		}
		public void setRegStsCdDetail(String regStsCdDetail) {
			this.regStsCdDetail = regStsCdDetail;
		}
		public String getEaQtySum() {
			return eaQtySum;
		}
		public void setEaQtySum(String eaQtySum) {
			this.eaQtySum = eaQtySum;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		public String getEmpNo() {
			return empNo;
		}
		public void setEmpNo(String empNo) {
			this.empNo = empNo;
		}
		public String getModId() {
			return modId;
		}
		public void setModId(String modId) {
			this.modId = modId;
		}
		public String getSaveFg() {
			return saveFg;
		}
		public void setSaveFg(String saveFg) {
			this.saveFg = saveFg;
		}
		public ArrayList<NEDMWEB0210VO> getTedOrdList() {
			if (this.tedOrdList != null) {
				ArrayList<NEDMWEB0210VO> ret = new ArrayList<NEDMWEB0210VO>();
				for (int i = 0; i < tedOrdList.size(); i++) {
					ret.add(i, this.tedOrdList.get(i));
				}

				return ret;
			} else {
				return null;
			}
		}

		public void setTedOrdList(ArrayList<NEDMWEB0210VO> tedOrdList) {
			if (tedOrdList != null) {
				this.tedOrdList = new ArrayList<NEDMWEB0210VO>();
				for (int i = 0; i < tedOrdList.size();i++) {
					this.tedOrdList.add(i, tedOrdList.get(i));
				}
			} else {
				this.tedOrdList = null;
			}
		}
		public String[] getOrdReqNos() {
			if (this.ordReqNos != null) {
				String[] ret = new String[ordReqNos.length];
				for (int i = 0; i < ordReqNos.length; i++) {
					ret[i] = this.ordReqNos[i];
				}
				return ret;
			} else {
				return null;
			}
		}

		public void setOrdReqNos(String[] ordReqNos) {
			 if (ordReqNos != null) {
				 this.ordReqNos = new String[ordReqNos.length];
				 for (int i = 0; i < ordReqNos.length; ++i) {
					 this.ordReqNos[i] = ordReqNos[i];
				 }
			 } else {
				 this.ordReqNos = null;
			 }

		}
		public String getUpdateState() {
			return updateState;
		}
		public void setUpdateState(String updateState) {
			this.updateState = updateState;
		}
		public String getProcEmpNo() {
			return procEmpNo;
		}
		public void setProcEmpNo(String procEmpNo) {
			this.procEmpNo = procEmpNo;
		}
		public String[] getVenCds() {
			if (this.venCds != null) {
				String[] ret = new String[venCds.length];
				for (int i = 0; i < venCds.length; i++) {
					ret[i] = this.venCds[i];
				}
				return ret;
			} else {
				return null;
			}
		}

		public void setVenCds(String[] venCds) {
			 if (venCds != null) {
				 this.venCds = new String[venCds.length];
				 for (int i = 0; i < venCds.length; ++i) {
					 this.venCds[i] = venCds[i];
				 }
			 } else {
				 this.venCds = null;
			 }

		}
		public String getRegStsfg() {
			return regStsfg;
		}
		public void setRegStsfg(String regStsfg) {
			this.regStsfg = regStsfg;
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
		public String getOrderSendfg() {
			return orderSendfg;
		}
		public void setOrderSendfg(String orderSendfg) {
			this.orderSendfg = orderSendfg;
		}

}
