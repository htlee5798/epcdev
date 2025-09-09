package com.lottemart.epc.edi.srm.model;

import java.io.Serializable;

import com.lottemart.epc.edi.comm.model.PagingVO;

public class SRMVEN0040VO extends PagingVO implements Serializable {
	
	private static final long serialVersionUID = 7132458974437330287L;
	
	/** 순번 */
	private String rnum;
	/** 하우스코드 */
	private String houseCode;
	/** 협력업체코드 배열(조회조건) */
	private String[] venCds;
	/**요청일자	*/
	private String reqDate;			
	/**평가일자	*/
	private String evalDate;
	/**평가기관이름 */
	private String evalSellerNameLoc;
	/**담당자 */
	private String userNameLoc;
	/**담당자 연락처 */
	private String officeTelNo;	
	/**상태 */
	private String status;
	/**상태이름 */
	private String statusNm;
	/**진행상태 */
	private String progressCode;	
	/**시정조치 완료 수 */
	private String impCnt; 			
	/**시정조치 전체 수 */
	private String totImpCnt; 		
	/**평가결과번호 */
	private String evalNoResult;
	/**평가요청 순번 */
	private String visitSeq;
	
	public String getRnum() {
		return rnum;
	}
	public void setRnum(String rnum) {
		this.rnum = rnum;
	}
	public String getHouseCode() {
		return houseCode;
	}
	public void setHouseCode(String houseCode) {
		this.houseCode = houseCode;
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
	public String getReqDate() {
		return reqDate;
	}
	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}
	public String getEvalDate() {
		return evalDate;
	}
	public void setEvalDate(String evalDate) {
		this.evalDate = evalDate;
	}
	public String getEvalSellerNameLoc() {
		return evalSellerNameLoc;
	}
	public void setEvalSellerNameLoc(String evalSellerNameLoc) {
		this.evalSellerNameLoc = evalSellerNameLoc;
	}
	public String getUserNameLoc() {
		return userNameLoc;
	}
	public void setUserNameLoc(String userNameLoc) {
		this.userNameLoc = userNameLoc;
	}
	public String getOfficeTelNo() {
		return officeTelNo;
	}
	public void setOfficeTelNo(String officeTelNo) {
		this.officeTelNo = officeTelNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusNm() {
		return statusNm;
	}
	public void setStatusNm(String statusNm) {
		this.statusNm = statusNm;
	}
	public String getProgressCode() {
		return progressCode;
	}
	public void setProgressCode(String progressCode) {
		this.progressCode = progressCode;
	}
	public String getImpCnt() {
		return impCnt;
	}
	public void setImpCnt(String impCnt) {
		this.impCnt = impCnt;
	}
	public String getTotImpCnt() {
		return totImpCnt;
	}
	public void setTotImpCnt(String totImpCnt) {
		this.totImpCnt = totImpCnt;
	}
	public String getEvalNoResult() {
		return evalNoResult;
	}
	public void setEvalNoResult(String evalNoResult) {
		this.evalNoResult = evalNoResult;
	}
	public String getVisitSeq() {
		return visitSeq;
	}
	public void setVisitSeq(String visitSeq) {
		this.visitSeq = visitSeq;
	}
	
}
