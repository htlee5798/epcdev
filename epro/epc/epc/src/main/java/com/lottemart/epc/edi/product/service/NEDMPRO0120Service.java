package com.lottemart.epc.edi.product.service;

import java.util.List;
import java.util.Map;

import com.lottemart.epc.edi.product.model.NEDMPRO0120VO;

/**
 * 
 * @Class Name : NEDMPRO0120Service.java
 * @Description : 점포별 상품리스트 조회
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	
 *               </pre>
 */
public interface NEDMPRO0120Service {

	/**
	 * Count
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int selectWholeStoreProductCount(NEDMPRO0120VO vo) throws Exception;
	
	/**
	 * List 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0120VO> selectWholeStoreProductList(NEDMPRO0120VO vo) throws Exception;
	
	/**
	 * 점포별 상품 List 조회
	 * @param vo
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> getSelectStrProductListProxy(NEDMPRO0120VO vo) throws Exception;
	
	/**
	 * 점포별 상품 List ExcelDownload (PROXY)
	 * @param vo
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> getSelectStrProductListProxyExcelDownload(NEDMPRO0120VO vo) throws Exception;
	
}
