package com.lottemart.epc.edi.weborder.dao;

import java.sql.SQLException;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.weborder.model.EdiPoEmpPoolVO;
import com.lottemart.epc.edi.weborder.model.EdiRtnProdVO;
import com.lottemart.epc.edi.weborder.model.EdiVendorVO;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.model.TedStore;



@Repository("pedmweb0099Dao")
public class PEDMWEB0099Dao extends AbstractDAO {
		
	
	
	/**
	 * 선택 담당자 조회  
	 * @param SearchWebOrder
	 * @return EdiPoEmpPoolVO
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public EdiPoEmpPoolVO selectEmpPoolData(SearchWebOrder vo) throws Exception{
		return	(EdiPoEmpPoolVO)getSqlMapClientTemplate().queryForObject("PEDMWEB0099.EDI_EMP_POOL_SELECT_01", vo);
	}
	
	
	/**
	 * 담당자별 또는 특정1협력사 전체 조회  
	 * @param SearchWebOrder
	 * @return EdiVendorVO
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public EdiVendorVO selectEmpVenData(SearchWebOrder vo) throws Exception{
		return	(EdiVendorVO)getSqlMapClientTemplate().queryForObject("PEDMWEB0099.EDI_EMP_POOL_SELECT_02", vo);
	}
	
	
	/**
	 * 협력업체 상품 조회  
	 * @param SearchWebOrder
	 * @return EdiVendorVO
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public EdiVendorVO selectProdVenData(SearchWebOrder vo) throws Exception{
		return	(EdiVendorVO)getSqlMapClientTemplate().queryForObject("PEDMWEB0099.EDI_PROD_VEN_SELECT_01", vo);
	}
	
	
	/**
	 * 반품상품 찾기  
	 * @param SearchWebOrder
	 * @return EdiRtnProdVO
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public EdiRtnProdVO selectEdiRtnProdData(SearchWebOrder vo) throws Exception{
		return	(EdiRtnProdVO)getSqlMapClientTemplate().queryForObject("PEDMWEB0099.EDI_RETURN_PROD_DATA_01", vo);
	}
	
	
	/**
	 * 반품상품 찾기2
	 * @param SearchWebOrder
	 * @return EdiRtnProdVO
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<EdiRtnProdVO> selectEdiRtnProdData2(SearchWebOrder vo) throws Exception{
		return	(List<EdiRtnProdVO>)getSqlMapClientTemplate().queryForList("PEDMWEB0099.EDI_RETURN_PROD_DATA_01", vo);
	}
	
	
	/**
	 * 반품상품 목록 조회  
	 * @param SearchWebOrder
	 * @return EdiRtnProdVO
	 * @throws SQLException
	 */
	@SuppressWarnings({ "unchecked" })
	public List<EdiRtnProdVO> selectProdCodeList(SearchWebOrder vo)
	{
		return (List<EdiRtnProdVO>)this.list("PEDMWEB0099.EDI_RETURN_PROD_SELECT_01", vo);
	}

	/**
	 * 반품상품 목록 조회  COUNT
	 * @param SearchWebOrder
	 * @return EdiRtnProdVO
	 * @throws SQLException
	 */
	@SuppressWarnings({ "unchecked" })
	public int selectProdCodeListCnt(SearchWebOrder vo)
	{
		return ((Integer)getSqlMapClientTemplate().queryForObject("PEDMWEB0099.EDI_RETURN_PROD_SELECT_02", vo)).intValue();
	}
	
	
	/**
	 * 업체 발주가능 점포조회  
	 * @param SearchWebOrder
	 * @return List<TedStore>
	 * @throws SQLException
	 */
	@SuppressWarnings({ "unchecked" })
	public List<TedStore> selectVenStoreCodeList(SearchWebOrder vo) throws SQLException
	{
		return (List<TedStore>)this.list("PEDMWEB0099.EDI_RETURN_VENDOR_STORE_SELECT_01", vo);
	}

}
