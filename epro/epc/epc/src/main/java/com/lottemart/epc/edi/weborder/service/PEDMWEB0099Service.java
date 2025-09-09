package com.lottemart.epc.edi.weborder.service;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.edi.weborder.model.EdiPoEmpPoolVO;
import com.lottemart.epc.edi.weborder.model.EdiRtnProdVO;
import com.lottemart.epc.edi.weborder.model.EdiVendorVO;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.model.TedStore;



public interface PEDMWEB0099Service {
	
	
	
	/**
	 * 사원정보 찾기(empNo조건)
	 * @param SearchWebOrder
	 * @return EdiPoEmpPoolVO
	 * @throws SQLException
	 */
	public EdiPoEmpPoolVO selectEmpPoolData(SearchWebOrder vo) throws Exception;


	/**
	 * 담당자별 또는 특정1협력사 전체 조회  
	 * @param SearchWebOrder
	 * @return EdiVendorVO
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public EdiVendorVO selectEmpVenData(SearchWebOrder vo) throws Exception;
	
	
	/**
     * 반품상품 조회  MARTNIS DB LINK (EDI_RTN_PROD_V1@DL_MD_MARTNIS)
     * @param SearchWebOrder
     * @return EdiRtnProdVO
     * @throws Exception
    */
	public EdiRtnProdVO selectStrCdProdSumList(SearchWebOrder swo) throws Exception;
	
	/**
     * 반품상품 조회2  MARTNIS DB LINK (EDI_RTN_PROD_V1@DL_MD_MARTNIS)
     * @param SearchWebOrder
     * @return EdiRtnProdVO
     * @throws Exception
    */
	public List<EdiRtnProdVO> selectStrCdProdSumList2(SearchWebOrder swo) throws Exception;
	
	/**
     * 반품상품 모곡 조회  MARTNIS DB LINK (EDI_RTN_PROD_V1@DL_MD_MARTNIS)
     * @param SearchWebOrder
     * @return Map<String,List<EdiRtnProdVO>>
     * @throws Exception
    */
	public Map<String,Object> selectProdCodeList(SearchWebOrder vo) throws Exception ;
	
	
	/**
     * 업체 발주가능 점포 조회
     * @param SearchWebOrder
     * @return List<TedStore>
     * @throws Exception
    */
	public List<TedStore> selectVenStoreCodeList(SearchWebOrder vo) throws Exception;
	
}

