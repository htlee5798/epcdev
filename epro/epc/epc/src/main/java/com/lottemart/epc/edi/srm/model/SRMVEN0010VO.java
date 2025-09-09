package com.lottemart.epc.edi.srm.model;

import java.io.Serializable;
import java.util.ArrayList;

public class SRMVEN0010VO implements Serializable {

	static final long serialVersionUID = -1208041506210207169L;
	
	//---------- 조회조건 ------------------------------
	/** 협력업체코드 배열(조회조건) */
	private String[] venCds;
	/** 협력업체 코드(조회조건) */
	private String srchVenCd;
	/** 상태값(조회조건) */
	private String srchStatus;
	/** 협력업체코드 선택 */
	private ArrayList srchVenCdAl;
	//--------------------------------------------------
	
	/** Proxy Name */
	private String proxyNm;
	
	//---------- HQ_VEN ------------------------------
	/** 협력업체코드 */
	private String venCd;
	/** 대표자명 */
	private String presidNm;
	/** 대표자 이메일 */
	private String presidEmail;
	/** 대표전화번호 */
	private String repTelNo;
	/** 담당자정보 */
	private String dutyInf;
	/** 핸드폰번호1 */
	private String hpNo1;
	/** EMAIL */
	private String email;
	/** MD이메일 */
	private String mdEmail;
	/** 팩스번호 */
	private String faxNo;
	/** 우편번호 */
	private String zip;
	/** 주소1 */
	private String addr;
	/** 주소2 */
	private String addr2;
	/** 협력업체명 */
	private String venNm;
	/** 사업자번호 */
	private String bmanNo;
	/** HACCP */
	private String qcFg01;
	/** FSSC22000 */
	private String qcFg02;
	/** ISO22000 */
	private String qcFg03;
	/** GMP인증 */
	private String qcFg04;
	/** KS인증 */
	private String qcFg05;
	/** 우수농산물GAP인증 */
	private String qcFg06;
	/** 유기가공식품인증 */
	private String qcFg07;
	/** 전통식품품질인증 */
	private String qcFg08;
	/** ISO_9001 */
	private String qcFg09;
	/** 수산물품질인증 */
	private String qcFg10;
	/** PAS_220 */
	private String qcFg11;
	/** 기타품질인증텍스트 */
	private String qcFg12;
	/** 등록일시 */
	private String regDt;
	/** 등록사번 */
	private String regEmpNo;
	/** 변경일시 */
	private String lstChgDt;
	/** 변경사번 */
	private String lstChgEmpNo;
	//--------------------------------------------------
	
	//---------- TPC_VEN_INFO_CHANGE ------------------------------
	/** 협력업체코드 배열(조회조건) */
	private ArrayList alVenCd;
	/** 협력업체코드 배열(조회조건) */
	private ArrayList alVenCdSeq;
	/** 대표자명 */
	private String modPresidNm;
	/** 대표자 이메일 */
	private String modPresidEmail;
	/** 대표전화번호 */
	private String modRepTelNo;
	/** 담당자정보 */
	private String modDutyInf;
	/** 핸드폰번호1 */
	private String modHpNo1;
	/** EMAIL */
	private String modEmail;
	/** 팩스번호 */
	private String modFaxNo;
	/** 우편번호 */
	private String modZip;
	/** 주소1 */
	private String modAddr;
	/** 주소2 */
	private String modAddr2;
	/** 순번 */
	private String seq;
	/** HACCP */
	private String zzqcFg1;
	/** FSSC22000 */
	private String zzqcFg2;
	/** ISO22000 */
	private String zzqcFg3;
	/** GMP인증 */
	private String zzqcFg4;
	/** KS인증 */
	private String zzqcFg5;
	/** 우수농산물GAP인증 */
	private String zzqcFg6;
	/** 유기가공식품인증 */
	private String zzqcFg7;
	/** 전통식품품질인증 */
	private String zzqcFg8;
	/** ISO_9001 */
	private String zzqcFg9;
	/** 수산물품질인증 */
	private String zzqcFg10;
	/** PAS_220 */
	private String zzqcFg11;
	/** 기타품질인증텍스트 */
	private String zzqcFg12;
	/** 상태 */
	private String status;
	/** 인터페이스일시 */
	private String ifDt;
	/** 등록일시 */
	private String regDate;
	/** 등록자 */
	private String regId;
	/** 수정일시 */
	private String modDate;
	/** 수정자 */
	private String modId;
	/** 저장/요청 구분 */
	private String confirmGbn;
	//--------------------------------------------------

	/**MD 명*/
	private String mdName;
	
	
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
	public String getSrchVenCd() {
		return srchVenCd;
	}
	public void setSrchVenCd(String srchVenCd) {
		this.srchVenCd = srchVenCd;
	}
	public String getSrchStatus() {
		return srchStatus;
	}
	public void setSrchStatus(String srchStatus) {
		this.srchStatus = srchStatus;
	}
	
	public ArrayList getSrchVenCdAl() {
		if (this.srchVenCdAl != null) {
			ArrayList ret = new ArrayList();
			for (int i = 0; i < srchVenCdAl.size(); i++) {
				ret.add(i, this.srchVenCdAl.get(i));
			}
			
			return ret;
		} else {
			return null;
		}
	}
	public void setSrchVenCdAl(ArrayList srchVenCdAl) {
		if (srchVenCdAl != null) {
			this.srchVenCdAl = new ArrayList();
			for (int i = 0; i < srchVenCdAl.size();i++) {
				this.srchVenCdAl.add(i, srchVenCdAl.get(i));
			}
		} else {
			this.srchVenCdAl = null;
		}
		this.srchVenCdAl = srchVenCdAl;
	}
	public String getProxyNm() {
		return proxyNm;
	}
	public void setProxyNm(String proxyNm) {
		this.proxyNm = proxyNm;
	}
	public String getVenCd() {
		return venCd;
	}
	public void setVenCd(String venCd) {
		this.venCd = venCd;
	}
	public String getPresidNm() {
		return presidNm;
	}
	public void setPresidNm(String presidNm) {
		this.presidNm = presidNm;
	}
	public String getPresidEmail() {
		return presidEmail;
	}
	public void setPresidEmail(String presidEmail) {
		this.presidEmail = presidEmail;
	}
	public String getRepTelNo() {
		return repTelNo;
	}
	public void setRepTelNo(String repTelNo) {
		this.repTelNo = repTelNo;
	}
	public String getDutyInf() {
		return dutyInf;
	}
	public void setDutyInf(String dutyInf) {
		this.dutyInf = dutyInf;
	}
	public String getHpNo1() {
		return hpNo1;
	}
	public void setHpNo1(String hpNo1) {
		this.hpNo1 = hpNo1;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMdEmail() {
		return mdEmail;
	}
	public void setMdEmail(String mdEmail) {
		this.mdEmail = mdEmail;
	}
	public String getFaxNo() {
		return faxNo;
	}
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	public String getVenNm() {
		return venNm;
	}
	public void setVenNm(String venNm) {
		this.venNm = venNm;
	}
	public String getBmanNo() {
		return bmanNo;
	}
	public void setBmanNo(String bmanNo) {
		this.bmanNo = bmanNo;
	}
	public String getQcFg01() {
		return qcFg01;
	}
	public void setQcFg01(String qcFg01) {
		this.qcFg01 = qcFg01;
	}
	public String getQcFg02() {
		return qcFg02;
	}
	public void setQcFg02(String qcFg02) {
		this.qcFg02 = qcFg02;
	}
	public String getQcFg03() {
		return qcFg03;
	}
	public void setQcFg03(String qcFg03) {
		this.qcFg03 = qcFg03;
	}
	public String getQcFg04() {
		return qcFg04;
	}
	public void setQcFg04(String qcFg04) {
		this.qcFg04 = qcFg04;
	}
	public String getQcFg05() {
		return qcFg05;
	}
	public void setQcFg05(String qcFg05) {
		this.qcFg05 = qcFg05;
	}
	public String getQcFg06() {
		return qcFg06;
	}
	public void setQcFg06(String qcFg06) {
		this.qcFg06 = qcFg06;
	}
	public String getQcFg07() {
		return qcFg07;
	}
	public void setQcFg07(String qcFg07) {
		this.qcFg07 = qcFg07;
	}
	public String getQcFg08() {
		return qcFg08;
	}
	public void setQcFg08(String qcFg08) {
		this.qcFg08 = qcFg08;
	}
	public String getQcFg09() {
		return qcFg09;
	}
	public void setQcFg09(String qcFg09) {
		this.qcFg09 = qcFg09;
	}
	public String getQcFg10() {
		return qcFg10;
	}
	public void setQcFg10(String qcFg10) {
		this.qcFg10 = qcFg10;
	}
	public String getQcFg11() {
		return qcFg11;
	}
	public void setQcFg11(String qcFg11) {
		this.qcFg11 = qcFg11;
	}
	public String getQcFg12() {
		return qcFg12;
	}
	public void setQcFg12(String qcFg12) {
		this.qcFg12 = qcFg12;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getRegEmpNo() {
		return regEmpNo;
	}
	public void setRegEmpNo(String regEmpNo) {
		this.regEmpNo = regEmpNo;
	}
	public String getLstChgDt() {
		return lstChgDt;
	}
	public void setLstChgDt(String lstChgDt) {
		this.lstChgDt = lstChgDt;
	}
	public String getLstChgEmpNo() {
		return lstChgEmpNo;
	}
	public void setLstChgEmpNo(String lstChgEmpNo) {
		this.lstChgEmpNo = lstChgEmpNo;
	}
	public ArrayList getAlVenCd() {
		if (this.alVenCd != null) {
			ArrayList ret = new ArrayList();
			for (int i = 0; i < alVenCd.size(); i++) {
				ret.add(i, this.alVenCd.get(i));
			}
			
			return ret;
		} else {
			return null;
		}
	}
	public void setAlVenCd(ArrayList alVenCd) {
		if (alVenCd != null) {
			this.alVenCd = new ArrayList();
			for (int i = 0; i < alVenCd.size();i++) {
				this.alVenCd.add(i, alVenCd.get(i));
			}
		} else {
			this.alVenCd = null;
		}
	}
	public ArrayList getAlVenCdSeq() {
		if (this.alVenCdSeq != null) {
			ArrayList ret = new ArrayList();
			for (int i = 0; i < alVenCdSeq.size(); i++) {
				ret.add(i, this.alVenCdSeq.get(i));
			}
			
			return ret;
		} else {
			return null;
		}
	}
	public void setAlVenCdSeq(ArrayList alVenCdSeq) {
		if (alVenCdSeq != null) {
			this.alVenCdSeq = new ArrayList();
			for (int i = 0; i < alVenCdSeq.size();i++) {
				this.alVenCdSeq.add(i, alVenCdSeq.get(i));
			}
		} else {
			this.alVenCdSeq = null;
		}
	}
	public String getModPresidNm() {
		return modPresidNm;
	}
	public void setModPresidNm(String modPresidNm) {
		this.modPresidNm = modPresidNm;
	}
	public String getModPresidEmail() {
		return modPresidEmail;
	}
	public void setModPresidEmail(String modPresidEmail) {
		this.modPresidEmail = modPresidEmail;
	}
	public String getModRepTelNo() {
		return modRepTelNo;
	}
	public void setModRepTelNo(String modRepTelNo) {
		this.modRepTelNo = modRepTelNo;
	}
	public String getModDutyInf() {
		return modDutyInf;
	}
	public void setModDutyInf(String modDutyInf) {
		this.modDutyInf = modDutyInf;
	}
	public String getModHpNo1() {
		return modHpNo1;
	}
	public void setModHpNo1(String modHpNo1) {
		this.modHpNo1 = modHpNo1;
	}
	public String getModEmail() {
		return modEmail;
	}
	public void setModEmail(String modEmail) {
		this.modEmail = modEmail;
	}
	public String getModFaxNo() {
		return modFaxNo;
	}
	public void setModFaxNo(String modFaxNo) {
		this.modFaxNo = modFaxNo;
	}
	public String getModZip() {
		return modZip;
	}
	public void setModZip(String modZip) {
		this.modZip = modZip;
	}
	public String getModAddr() {
		return modAddr;
	}
	public void setModAddr(String modAddr) {
		this.modAddr = modAddr;
	}
	public String getModAddr2() {
		return modAddr2;
	}
	public void setModAddr2(String modAddr2) {
		this.modAddr2 = modAddr2;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getZzqcFg1() {
		return zzqcFg1;
	}
	public void setZzqcFg1(String zzqcFg1) {
		this.zzqcFg1 = zzqcFg1;
	}
	public String getZzqcFg2() {
		return zzqcFg2;
	}
	public void setZzqcFg2(String zzqcFg2) {
		this.zzqcFg2 = zzqcFg2;
	}
	public String getZzqcFg3() {
		return zzqcFg3;
	}
	public void setZzqcFg3(String zzqcFg3) {
		this.zzqcFg3 = zzqcFg3;
	}
	public String getZzqcFg4() {
		return zzqcFg4;
	}
	public void setZzqcFg4(String zzqcFg4) {
		this.zzqcFg4 = zzqcFg4;
	}
	public String getZzqcFg5() {
		return zzqcFg5;
	}
	public void setZzqcFg5(String zzqcFg5) {
		this.zzqcFg5 = zzqcFg5;
	}
	public String getZzqcFg6() {
		return zzqcFg6;
	}
	public void setZzqcFg6(String zzqcFg6) {
		this.zzqcFg6 = zzqcFg6;
	}
	public String getZzqcFg7() {
		return zzqcFg7;
	}
	public void setZzqcFg7(String zzqcFg7) {
		this.zzqcFg7 = zzqcFg7;
	}
	public String getZzqcFg8() {
		return zzqcFg8;
	}
	public void setZzqcFg8(String zzqcFg8) {
		this.zzqcFg8 = zzqcFg8;
	}
	public String getZzqcFg9() {
		return zzqcFg9;
	}
	public void setZzqcFg9(String zzqcFg9) {
		this.zzqcFg9 = zzqcFg9;
	}
	public String getZzqcFg10() {
		return zzqcFg10;
	}
	public void setZzqcFg10(String zzqcFg10) {
		this.zzqcFg10 = zzqcFg10;
	}
	public String getZzqcFg11() {
		return zzqcFg11;
	}
	public void setZzqcFg11(String zzqcFg11) {
		this.zzqcFg11 = zzqcFg11;
	}
	public String getZzqcFg12() {
		return zzqcFg12;
	}
	public void setZzqcFg12(String zzqcFg12) {
		this.zzqcFg12 = zzqcFg12;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIfDt() {
		return ifDt;
	}
	public void setIfDt(String ifDt) {
		this.ifDt = ifDt;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getModDate() {
		return modDate;
	}
	public void setModDate(String modDate) {
		this.modDate = modDate;
	}
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}
	public String getConfirmGbn() {
		return confirmGbn;
	}
	public void setConfirmGbn(String confirmGbn) {
		this.confirmGbn = confirmGbn;
	}

	public String getMdName() {
		return mdName;
	}

	public void setMdName(String mdName) {
		this.mdName = mdName;
	}
}
