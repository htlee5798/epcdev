package com.lottemart.epc.edi.weborder.model;

import java.io.Serializable;

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

public class TedOrdSumList extends PagingVO implements Serializable {



	/**
	 *
	 */
	private static final long serialVersionUID = 7425840943707499407L;
	/** 발주총수량합계 */
	private String ordTotQtySum;
	/** 발주총금액합계 */
	private String ordTotPrcSum;
	/** 발주EA 총수량 */
	private String ordTotAllQtySum;

	/** EA 합계수량 */
	private String eaQtySum;




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

	public String getEaQtySum() {
		return eaQtySum;
	}
	public void setEaQtySum(String eaQtySum) {
		this.eaQtySum = eaQtySum;
	}




}
