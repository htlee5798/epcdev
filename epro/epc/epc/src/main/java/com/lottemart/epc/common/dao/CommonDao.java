package com.lottemart.epc.common.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.model.EpcLoginVO;

/**
 * @Class Name : CommonDao.java
 * @Description :
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 20. 오후 02:02:02 mjChoi
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Repository("commonDao")
public class CommonDao extends AbstractDAO {
	@Autowired
	private SqlMapClient sqlMapClient;

	/**
	 * 카테고리 대분류 콤보 Desc : 카테고리 대분류 콤보
	 * 
	 * @Method Name : selectDaeCdList
	 * @param Map
	 *            <String, String>
	 * @return List<DataMap>
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectDaeCdList() throws SQLException {
		return (List<DataMap>) sqlMapClient
				.queryForList("common.selectDaeCdList");
	}

	/**
	 * 카테고리 중분류 콤보 Desc : 카테고리 중분류 콤보
	 * 
	 * @Method Name : selectJungCdList
	 * @param Map
	 *            <String, String>
	 * @return List<DataMap>
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectJungCdList(Map<String, String> map)
			throws SQLException {
		return (List<DataMap>) sqlMapClient.queryForList(
				"common.selectJungCdList", map);
	}

	/**
	 * 카테고리 소분류 콤보 Desc : 카테고리 소분류 콤보
	 * 
	 * @Method Name : selectSoCdList
	 * @param Map
	 *            <String, String>
	 * @return List<DataMap>
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectSoCdList(Map<String, String> map)
			throws SQLException {
		return (List<DataMap>) sqlMapClient.queryForList(
				"common.selectSoCdList", map);
	}

	/**
	 * 카테고리 세분류 콤보 Desc : 카테고리 세분류 콤보
	 * 
	 * @Method Name : selectSeCdList
	 * @param Map
	 *            <String, String>
	 * @return List<DataMap>
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectSeCdList(Map<String, String> map)
			throws SQLException {
		return (List<DataMap>) sqlMapClient.queryForList(
				"common.selectSeCdList", map);
	}

	/**
	 * Desc : 공통코드 조회
	 * 
	 * @Method Name : selectTetCodeList
	 * @param map
	 * @return
	 * @throws SQLException
	 * @param
	 * @return
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectTetCodeList(Map<String, String> map)
			throws SQLException {
		return (List<DataMap>) sqlMapClient.queryForList(
				"common.selectTetCodeList", map);
	}

	/**
	 * Desc : 공통코드 조회(Ref로 필터링)
	 * 
	 * @Method Name : selectTetCodeRefList
	 * @param map
	 * @return
	 * @throws SQLException
	 * @param
	 * @return
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectTetCodeRefList(Map<String, String> map)
			throws SQLException {
		return (List<DataMap>) sqlMapClient.queryForList(
				"common.selectTetCodeRefList", map);
	}

	/**
	 * Desc : openappi 업체 대표 vendorId 조회
	 * 
	 * @Method Name : selectOpenappiVendor
	 * @return
	 * @throws SQLException
	 * @param
	 * @return
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<EpcLoginVO> selectOpenappiVendor() throws SQLException {
		return sqlMapClient.queryForList("common.selectOpenappiVendor");
	}
	
	/**
    * Desc : 트랜잭션중 강제커밋
    * @Method Name : commit
    * @param
    * @return
    * @throws SQLException    
    */
	public void commit() throws SQLException {
		//@4UP 수정 ojdbc 업그레이드를 위한 수정
		Connection	con;

		con	= sqlMapClient.getDataSource().getConnection();
		if (!con.getAutoCommit()) {
			con.commit();
		}
	}

	/**
	 * Desc : SaleVendorId 조회
	 * 
	 * @Method Name : selectTetCodeList
	 * @param map
	 * @return
	 * @throws SQLException
	 * @param
	 * @return
	 * @exception Exception
	 */
	public String selectSaleVendorId(String vendorId) {
		return (String)getSqlMapClientTemplate().queryForObject("common.selectSaleVendorId",vendorId);
	}

	/**
	 * Desc : 상품ec등록여부 조회 (PD_API_0003,0004,0026호출하기전에 , PD_API_0001 성공여부 조회)
	 * @Method Name : selectEcRegisteredYn
	 * @param prodCd
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean selectEcRegisteredYn (String prodCd) throws SQLException {
		return (Boolean) sqlMapClient.queryForObject("common.selectEcRegisteredYn",prodCd);
	}
	
	
	/**
	 * Desc : 상품ec승인여부 조회 (PD_API_0002호출하기 전에, PD_API_0001,0003,0004,0026 4개 모두 성공여부 조회)
	 * @Method Name : selectEcRegisteredYn
	 * @param prodCd
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean selectEcApprovedYn (String prodCd) throws SQLException {
		return (Boolean) sqlMapClient.queryForObject("common.selectEcApprovedYn",prodCd);
	}
	
	/**
	 * Desc : D코드 상품ec승인여부 조회 (PD_API_0010호출하기 전에, ec에 등록 성공했는지 확인 )
	 * @Method Name : selectEcPlanRegisteredYn
	 * @param prodCd
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean selectEcPlanRegisteredYn (String prodCd) throws SQLException {
		return (Boolean) sqlMapClient.queryForObject("common.selectEcPlanRegisteredYn",prodCd);
	}
}
