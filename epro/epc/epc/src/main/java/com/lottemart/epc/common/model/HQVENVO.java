package com.lottemart.epc.common.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * @Class Name : 업체[HQ_VEN] VO
 * @Description : 업체[HQ_VEN]
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2015.11.26. 	SONG MIN KYO	최초생성
 * </pre>
 */
public class HQVENVO implements Serializable {
	
	private static final long serialVersionUID = -8217038698476076150L;
	
	private String trdTypFg;	// 거래형태 구분	
	private String promoAmtRt;	//
	
	public String getTrdTypFg() {
		return trdTypFg;
	}
	public void setTrdTypFg(String trdTypFg) {
		this.trdTypFg = trdTypFg;
	}
	public String getPromoAmtRt() {
		return promoAmtRt;
	}
	public void setPromoAmtRt(String promoAmtRt) {
		this.promoAmtRt = promoAmtRt;
	}
	
	
}
