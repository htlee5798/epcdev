package com.lottemart.epc.edi.weborder.model;

import java.io.Serializable;

/**
 * @Class Name : TedStore
 * @Description : 점포 공통 VO
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2014. 08. 30. 오후 2:32:38 dada
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */

public class TedStore extends PagingVO implements Serializable {

	


	/**
	 * 
	 */
	private static final long serialVersionUID = -8899884728103144603L;


	public TedStore() {}
	
	/**업체코드*/
	private String venCd;
	/**권역코드*/
	private String areaCd;
	/**권역명칭*/
	private String areaNm;
	/**점포코드*/
	private String strCd;
	/**점포코드 다중선택*/
	private String[] strCds;
	/**점포명*/
	private String strNm;
	/**점포명*/
	private String[] strNms;


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
	public String getAreaNm() {
		return areaNm;
	}
	public void setAreaNm(String areaNm) {
		this.areaNm = areaNm;
	}
	public String getStrCd() {
		return strCd;
	}
	public void setStrCd(String strCd) {
		this.strCd = strCd;
	}
	public String[] getStrCds() {
		return strCds;
	}
	public void setStrCds(String[] strCds) {
		this.strCds = strCds;
	}
	public String getStrNm() {
		return strNm;
	}
	public void setStrNm(String strNm) {
		this.strNm = strNm;
	}
	public String[] getStrNms() {
		return strNms;
	}
	public void setStrNms(String[] strNms) {
		this.strNms = strNms;
	}


	
	
	

}
