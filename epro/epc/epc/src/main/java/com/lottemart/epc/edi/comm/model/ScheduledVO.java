package com.lottemart.epc.edi.comm.model;

import java.io.Serializable;


/**
 * @Class Name : NEDMPRO0310VO
 * @Description : 행사정보 등록내역 VO
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      	수정자           		수정내용
 *  -------    --------    ---------------------------
 * 2025.03.07  PARK JONG GYU 		최초생성
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */

public class ScheduledVO extends PagingVO  implements Serializable {

	
	private static final long serialVersionUID = -6918711902569942487L;


	public ScheduledVO () {}
	
	
	/** 파트너사행사번호 */
	private String reqOfrcd; 	
	
	/** ecs 연계 코드 */
	private String ifCd;
	
	/** 계약순번 */
	private String dcCnt;
	
	/** 진행상태 */
	private String dcStat;

	/** 작업자 ID */
	private String workId;
	
	/** 전자계약번호1 */
	private String docNo1;
	
	/** 전자계약번호2 */
	private String docNo2;
	
	/** 전자계약번호3 */
	private String docNo3;
	
	/** 계약번호 */
	private String dcNum;
	
	/** 계약번호2 */
	private String dcNum2;
	
	/** 계열사 */
	private String vkorg;
	
	/** 계열사 구분 */
	private String vkorgGbn;
	
	/** 행사 진행상태 */
	private String apprStatus;
	
	/** 계약형태 */
	private String dcOnoff;
	
	/** 계약 변경 여부 */
	private String dcChgIf;
	
	/** ecs 연계 계약 번호 */
	private String contCode;
	
	/** ecs 연계 계약 번호2 */
	private String contCode2;
	
	public String getDcNum2() {
		return dcNum2;
	}

	public void setDcNum2(String dcNum2) {
		this.dcNum2 = dcNum2;
	}

	public String getContCode() {
		return contCode;
	}

	public void setContCode(String contCode) {
		this.contCode = contCode;
	}

	public String getContCode2() {
		return contCode2;
	}

	public void setContCode2(String contCode2) {
		this.contCode2 = contCode2;
	}

	public String getDcChgIf() {
		return dcChgIf;
	}

	public void setDcChgIf(String dcChgIf) {
		this.dcChgIf = dcChgIf;
	}

	public String getDcOnoff() {
		return dcOnoff;
	}

	public void setDcOnoff(String dcOnoff) {
		this.dcOnoff = dcOnoff;
	}

	public String getApprStatus() {
		return apprStatus;
	}

	public void setApprStatus(String apprStatus) {
		this.apprStatus = apprStatus;
	}

	public String getVkorgGbn() {
		return vkorgGbn;
	}

	public void setVkorgGbn(String vkorgGbn) {
		this.vkorgGbn = vkorgGbn;
	}

	public String getDcNum() {
		return dcNum;
	}

	public void setDcNum(String dcNum) {
		this.dcNum = dcNum;
	}

	public String getVkorg() {
		return vkorg;
	}

	public void setVkorg(String vkorg) {
		this.vkorg = vkorg;
	}

	public String getDocNo1() {
		return docNo1;
	}

	public void setDocNo1(String docNo1) {
		this.docNo1 = docNo1;
	}

	public String getDocNo2() {
		return docNo2;
	}

	public void setDocNo2(String docNo2) {
		this.docNo2 = docNo2;
	}

	public String getDocNo3() {
		return docNo3;
	}

	public void setDocNo3(String docNo3) {
		this.docNo3 = docNo3;
	}

	public String getIfCd() {
		return ifCd;
	}

	public void setIfCd(String ifCd) {
		this.ifCd = ifCd;
	}

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public String getReqOfrcd() {
		return reqOfrcd;
	}

	public void setReqOfrcd(String reqOfrcd) {
		this.reqOfrcd = reqOfrcd;
	}

	public String getDcCnt() {
		return dcCnt;
	}

	public void setDcCnt(String dcCnt) {
		this.dcCnt = dcCnt;
	}

	public String getDcStat() {
		return dcStat;
	}

	public void setDcStat(String dcStat) {
		this.dcStat = dcStat;
	} 	
	
	
}
