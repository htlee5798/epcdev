/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.product.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.model.PSCMPRD0014VO;

public interface PSCMPRD0014Service {

	public List<DataMap> selectStoreCombo() throws Exception;

	public List<DataMap> selectOutOfStockCount(DataMap paramMap) throws Exception;

	public List<PSCMPRD0014VO> selectOutOfStockList(DataMap paramMap) throws Exception;
	
	public int updateOutOfStock(HttpServletRequest request) throws Exception;

	/**
	 * Desc : 품절관리리스트 조회하는 메소드
	 * @Method Name : selectOutOfStockListExcel
	 * @param pscmprd0014VO
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<PSCMPRD0014VO> selectOutOfStockListExcel(PSCMPRD0014VO pscmprd0014VO) throws Exception;
	
	//20180626 - 업체 공통조건 사용시 무료배송 'N' 처리(주문파트 요청건) 및 업체 배송비 관리 수정 (배송비 업체공통 조건 문구가 보여서 추가 처리)
	public List<DataMap> selectDeliVendorInfo(DataMap paramMap) throws Exception;
}
