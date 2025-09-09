package com.lottemart.epc.product.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.model.PSCMPRD0001VO;

/**
 * @Class Name : PSCMPRD0001Dao.java
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
@Repository("pscmprd0001Dao")
public class PSCMPRD0001Dao extends AbstractDAO {

	@Autowired
	private SqlMapClient sqlMapClient;

	/**
	 * 상품 구분 목록 Desc :
	 * 
	 * @Method Name : selectPrdTotalCnt
	 * @param
	 * @return List<DataMap>
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectProdDivNCdList() throws SQLException {
		return (List<DataMap>) getSqlMapClientTemplate().queryForList("pscmprd0001.selectProdDivNCdList");
	}

	/**
	 * 상품 목록 총카운트
	 * Desc : 
	 * @Method Name : selectPrdTotalCnt
	 * @param DataMap
	 * @return int
	 * @throws SQLException
	 */
	public int selectPrdTotalCnt(DataMap paramMap) throws SQLException {
		Integer iTotCnt = new Integer(0);
		iTotCnt = (Integer) getSqlMapClientTemplate().queryForObject("pscmprd0001.selectPrdTotalCnt", paramMap);
		return iTotCnt.intValue();
	}

	/**
	 * 상품 목록
	 * Desc : 
	 * @Method Name : selectPrdList
	 * @param DataMap
	 * @return List
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PSCMPRD0001VO> selectPrdList(DataMap paramMap) throws SQLException {
		return (List<PSCMPRD0001VO>) getSqlMapClientTemplate().queryForList("pscmprd0001.selectPrdList", paramMap);
	}

	/**
	 * 이미지 일괄 수정용 상품목록 조회
	 * Desc : 
	 * @Method Name : selectPrdListForBatchImageUpload
	 * @param DataMap
	 * @return List
	 * @throws SQLException
	 */
	public List<DataMap> selectPrdListForBatchImageUpload(DataMap paramMap) throws SQLException {
		return (List<DataMap>) getSqlMapClientTemplate().queryForList("pscmprd0001.selectPrdListForBatchImageUpload", paramMap);
	}

	/**
	 * 상품코드 목록
	 * Desc : 
	 * @Method Name : selectProdCdInfo
	 * @param String
	 * @return PSCMPRD0001VO
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public PSCMPRD0001VO selectProdCdInfo(String asKeywordValue) throws SQLException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("asKeywordValue", asKeywordValue);
		return (PSCMPRD0001VO) getSqlMapClientTemplate().queryForObject("pscmprd0001.selectProdCdInfo", paramMap);
	}

	/**
	 * 상품코드 목록
	 * @param asKeywordValue
	 * @return
	 * @throws SQLException
	 */
	public List<String> selectProdCdInfoList(String asKeywordValue) throws SQLException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("asKeywordValue", asKeywordValue);
		return (List<String>) sqlMapClient.queryForList("pscmprd0001.selectProdCdInfoList", paramMap);
	}

	@SuppressWarnings("unchecked")
	public List<PSCMPRD0001VO> selectProductExcel(DataMap paramMap) throws SQLException {
		return (List<PSCMPRD0001VO>) getSqlMapClientTemplate().queryForList("pscmprd0001.selectPrdList", paramMap);
	}

}
