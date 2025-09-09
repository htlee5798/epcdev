package com.lottemart.epc.product.dao;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.model.PSCPPRD0003VO;

/**
 * @Class Name : PSCPPRD0003Dao.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 20. 오후 02:02:02 mjChoi
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Repository("pscpprd0003Dao")
public class PSCPPRD0003Dao extends AbstractDAO {

	@Autowired
	private SqlMapClient sqlMapClient;
	
	/**
     * 단품 칼라 목록
     * @param 
     * @return List<DataMap>
     * @throws SQLException  DB 관련 오류
     */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectPrdItemColorList() throws SQLException {
		return (List<DataMap>) getSqlMapClientTemplate().queryForList("pscpprd0003.selectPrdItemColorList", "");
	}

	/**
     * 단품 사이즈 구분 목록
     * @param 
     * @return List<DataMap>
     * @throws SQLException  DB 관련 오류
     */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectPrdItemSizeCategoryList() throws SQLException {
		return (List<DataMap>) getSqlMapClientTemplate().queryForList("pscpprd0003.selectPrdItemSizeCategoryList", "");
	}

	/**
     * 단품 사이즈 목록
     * @param String
     * @return List<DataMap>
     * @throws SQLException  DB 관련 오류
     */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectPrdItemSizeList(String szCatCd) throws SQLException {
		return (List<DataMap>) getSqlMapClientTemplate().queryForList("pscpprd0003.selectPrdItemSizeList", szCatCd);
	}
	
	/**
	 * 속성 값 체크
	 * @return int
	 * @throws Exception
	 */
	public int selectPrdItemType(Map<String, String> paramMap) throws SQLException {
		Integer iTotCnt = new Integer(0);
		iTotCnt = (Integer) getSqlMapClientTemplate().queryForObject("pscpprd0003.selectPrdItemType", paramMap);

		return iTotCnt.intValue();
	}

	/**
	 * 인터넷 전용상품 여부
	 * Desc : 
	 * @Method Name : selectPrdItemType
	 * @param Map<String, String>
	 * @return int
	 * @throws SQLException
	 */
	public int selectPrdType(Map<String, String> paramMap) throws SQLException{
		Integer iTotCnt = new Integer(0);
		iTotCnt = (Integer)getSqlMapClientTemplate().queryForObject("pscpprd0003.selectPrdType",paramMap);
		return iTotCnt.intValue();
	}
	
	/**
	 * 단품 정보 목록
	 * Desc : 
	 * @Method Name : selectPrdItemList
	 * @param Map<String, String>
	 * @return List<PSCPPRD0003VO>
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PSCPPRD0003VO> selectPrdItemList(Map<String, String> paramMap) throws SQLException {
		return (List<PSCPPRD0003VO>) getSqlMapClientTemplate().queryForList("pscpprd0003.selectPrdItemList", paramMap);
	}

	/**
	 * 단품 정보 목록1
	 * Desc : 
	 * @Method Name : selectPrdItemList
	 * @param Map<String, String>
	 * @return List<PSCPPRD0003VO>
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PSCPPRD0003VO> selectPrdItemList1(Map<String, String> paramMap) throws SQLException {
		return (List<PSCPPRD0003VO>) getSqlMapClientTemplate().queryForList("pscpprd0003.selectPrdItemList1", paramMap);
	}

	/**
	 * 단품 정보 목록2
	 * Desc : 
	 * @Method Name : selectPrdItemList
	 * @param Map<String, String>
	 * @return List<PSCPPRD0003VO>
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PSCPPRD0003VO> selectPrdItemList2(Map<String, String> paramMap) throws SQLException {
		return (List<PSCPPRD0003VO>) getSqlMapClientTemplate().queryForList("pscpprd0003.selectPrdItemList2", paramMap);
	}

	/**
	 * 단품 정보 목록
	 * Desc : 
	 * @Method Name : selectPrdItemOnlineList
	 * @param DataMap
	 * @return List
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PSCPPRD0003VO> selectPrdItemOnlineList(Map<String, String> paramMap) throws SQLException{
		return (List<PSCPPRD0003VO>) getSqlMapClientTemplate().queryForList("pscpprd0003.selectPrdItemOnlineList",paramMap);
	}

	/**
	 * 단품 정보
	 * Desc : 
	 * @Method Name : selectPrdItem
	 * @param @param Map<String, String>
	 * @return VO
	 * @throws SQLException
	 */
	public PSCPPRD0003VO selectPrdItem(Map<String, String> paramMap) throws SQLException {
		return (PSCPPRD0003VO) sqlMapClient.queryForObject("pscpprd0003.selectPrdItem", paramMap);
	}

	/**
	 * 단품 정보 입력
	 * Desc : 
	 * @Method Name : insertPrdItem
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int insertPrdItem(PSCPPRD0003VO bean) throws SQLException {
		return getSqlMapClientTemplate().update("pscpprd0003.insertPrdItem", bean);
	}

	/**
	 * 단품 정보 업데이트
	 * Desc : 
	 * @Method Name : updatePrdItem
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int updatePrdItem(PSCPPRD0003VO bean) throws SQLException {
		return getSqlMapClientTemplate().update("pscpprd0003.updatePrdItem", bean);
	}


	/**
	 * 단품 정보 업데이트
	 * Desc : 
	 * @Method Name : updateTprItemListMerge
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int updateTprItemList(PSCPPRD0003VO bean) throws SQLException{
		return getSqlMapClientTemplate().update("pscpprd0003.updateTprItemList",bean);	
	}
	
	/**
	 * 단품 점포 정보 업데이트
	 * Desc : 
	 * @Method Name : updateTprStoreItemListMerge
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int updateTprStoreItemList(PSCPPRD0003VO bean) throws SQLException{
		return getSqlMapClientTemplate().update("pscpprd0003.updateTprStoreItemList",bean);	
	}
	
	/**
	 * 단품 정보 등록
	 * Desc : 
	 * @Method Name : insertTprItemListMerge
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public String insertTprItemList(PSCPPRD0003VO bean) throws SQLException{
		return (String)getSqlMapClientTemplate().insert("pscpprd0003.insertTprItemList",bean);	
	}
	
	/**
	 * 단품 점포 정보 등록
	 * Desc : 
	 * @Method Name : insertTprStoreItemListMerge
	 * @param VO
	 * @return int
	 * @throws SQLException
	 */	
	public int insertTprStoreItemList(PSCPPRD0003VO bean) throws SQLException{
		return getSqlMapClientTemplate().update("pscpprd0003.insertTprStoreItemList",bean);
	}
	
	/**
	 * 단품 사이즈 콤보 목록
	 * Desc : 
	 * @Method Name : selectItemSzCdList
	 * @param Map<String, String>
	 * @return List<DataMap>
	 * @throws SQLException
	 */	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectPrdItemSizeList(Map<String, String> map) throws SQLException {
		return (List<DataMap>) sqlMapClient.queryForList("pscpprd0003.selectPrdItemSizeList", map);
	}
	
	/* 2016.03.02 by kmlee
	 * 온라인 전용상품에서 단품이 2개 이상일 때 TPR_PRODUCT.VARIATION_YN = 'Y'로 UPDATE
	 */
	public int updateTprProductVariation(PSCPPRD0003VO bean) throws SQLException {
		return getSqlMapClientTemplate().update("pscpprd0003.updateTprProductVariation", bean);
	}

}
