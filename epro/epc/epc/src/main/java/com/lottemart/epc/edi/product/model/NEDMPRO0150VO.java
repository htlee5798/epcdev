package com.lottemart.epc.edi.product.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.lottemart.epc.edi.comm.model.PagingVO;

public class NEDMPRO0150VO extends PagingVO implements Serializable {

	static final long serialVersionUID = 1776508153203875384L;
	
	/** 협력업체코드 */
	private String srchEntpCd;
	/** 판매(88)코드 */
	private String srchSrcmkCd;
	/** */
	private String downloadFlag;
	/** 협력업체 코드 */
	private String[] venCds;
	
	/** 순번 */
	private String rnum;
	/** 기준일자 */
	private String stdDate;
	/** 업체코드 */
	private String venCd;
	/** 업체명 */
	private String venNm;
	/** 상품코드 */
	private String prodCd;
	/** 판매코드 */
	private String srcmkCd;
	/** 상품명 */
	private String prodNm;
	/** 규격 */
	private String prodStd;
	/** 입수 */
	private String ipsu;
	/** 최소생산량(BOX) */
	private String minProdQty;
	/** 생산리드타임(일) */
	private String prodReadTime;
	/** 일별재고수량(BOX) */
	private String jaegoQty;
	/** 일별재고수량D1(BOX) */
	private String jaegoD1;
	/** 일별재고수량D2(BOX) */
	private String jaegoD2;
	/** 일별재고수량D3(BOX) */
	private String jaegoD3;
	/** 일별재고수량D4(BOX) */
	private String jaegoD4;
	/** 일별재고수량D5(BOX) */
	private String jaegoD5;
	/** 일별재고수량D6(BOX) */
	private String jaegoD6;
	/** 등록일시 */
	private String regDate;
	/** 등록자 */
	private String regId;
	/** 수정일시 */
	private String modDate;
	/** 수정자 */
	private String modId;
	/** ECO전송여부 */
	private String ecoSendYn;
	/** ECO전송일시 */
	private String ecoSendDate;
	
	/** 저장 ArrayList */
	private ArrayList alProdJaego;
	
	public String getSrchEntpCd() {
		return srchEntpCd;
	}
	public void setSrchEntpCd(String srchEntpCd) {
		this.srchEntpCd = srchEntpCd;
	}
	public String getSrchSrcmkCd() {
		return srchSrcmkCd;
	}
	public void setSrchSrcmkCd(String srchSrcmkCd) {
		this.srchSrcmkCd = srchSrcmkCd;
	}
	public String getDownloadFlag() {
		return downloadFlag;
	}
	public void setDownloadFlag(String downloadFlag) {
		this.downloadFlag = downloadFlag;
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
	public String getRnum() {
		return rnum;
	}
	public void setRnum(String rnum) {
		this.rnum = rnum;
	}
	public String getStdDate() {
		return stdDate;
	}
	public void setStdDate(String stdDate) {
		this.stdDate = stdDate;
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
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
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
	public String getIpsu() {
		return ipsu;
	}
	public void setIpsu(String ipsu) {
		this.ipsu = ipsu;
	}
	public String getMinProdQty() {
		return minProdQty;
	}
	public void setMinProdQty(String minProdQty) {
		this.minProdQty = minProdQty;
	}
	public String getProdReadTime() {
		return prodReadTime;
	}
	public void setProdReadTime(String prodReadTime) {
		this.prodReadTime = prodReadTime;
	}
	public String getJaegoQty() {
		return jaegoQty;
	}
	public void setJaegoQty(String jaegoQty) {
		this.jaegoQty = jaegoQty;
	}
	public String getJaegoD1() {
		return jaegoD1;
	}
	public void setJaegoD1(String jaegoD1) {
		this.jaegoD1 = jaegoD1;
	}
	public String getJaegoD2() {
		return jaegoD2;
	}
	public void setJaegoD2(String jaegoD2) {
		this.jaegoD2 = jaegoD2;
	}
	public String getJaegoD3() {
		return jaegoD3;
	}
	public void setJaegoD3(String jaegoD3) {
		this.jaegoD3 = jaegoD3;
	}
	public String getJaegoD4() {
		return jaegoD4;
	}
	public void setJaegoD4(String jaegoD4) {
		this.jaegoD4 = jaegoD4;
	}
	public String getJaegoD5() {
		return jaegoD5;
	}
	public void setJaegoD5(String jaegoD5) {
		this.jaegoD5 = jaegoD5;
	}
	public String getJaegoD6() {
		return jaegoD6;
	}
	public void setJaegoD6(String jaegoD6) {
		this.jaegoD6 = jaegoD6;
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
	public String getEcoSendYn() {
		return ecoSendYn;
	}
	public void setEcoSendYn(String ecoSendYn) {
		this.ecoSendYn = ecoSendYn;
	}
	public String getEcoSendDate() {
		return ecoSendDate;
	}
	public void setEcoSendDate(String ecoSendDate) {
		this.ecoSendDate = ecoSendDate;
	}
	public ArrayList getAlProdJaego() {
		if (this.alProdJaego != null) {
			ArrayList ret = new ArrayList();
			for (int i = 0; i < alProdJaego.size(); i++) {
				ret.add(i, this.alProdJaego.get(i));
			}
			
			return ret;
		} else {
			return null;
		}
	}
	public void setAlProdJaego(ArrayList alProdJaego) {
		if (alProdJaego != null) {
			this.alProdJaego = new ArrayList();
			for (int i = 0; i < alProdJaego.size();i++) {
				this.alProdJaego.add(i, alProdJaego.get(i));
			}
		} else {
			this.alProdJaego = null;
		}
	}
	
}
