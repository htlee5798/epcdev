/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.product.dao;

import java.sql.SQLException;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.model.PSCMPRD0009VO;

/**
 * @Class Name : PSCMPRD0009Dao
 * @Description : 상품가격변경요청리스트를 조회하는 Dao 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 4:29:20 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Repository("pscmprd0009Dao")
public class PSCMPRD0009Dao extends AbstractDAO {

	@Autowired
	private SqlMapClient sqlMapClient;
	
	/**
	 * Desc : 상품가격변경요청리스트 조회하는 메소드
	 * @Method Name : selectPriceChangeList
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PSCMPRD0009VO> selectPriceChangeList(DataMap paramMap) throws SQLException {
		return (List<PSCMPRD0009VO>)getSqlMapClientTemplate().queryForList("PSCMPRD0009.selectPriceChangeList", paramMap);
	}
	
	/**
	 * Desc : 상품가격변경요청리스트 데이터 등록 메소드
	 * @Method Name : insertPriceChangeReq
	 * @param pscmprd0009
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public int insertPriceChangeReq(PSCMPRD0009VO pscmprd0009) throws SQLException {
		return sqlMapClient.update("PSCMPRD0009.insertPriceChangeReq", pscmprd0009);
	}

	/**
	 * Desc : 상품가격변경요청리스트 데이터 수정 메소드
	 * @Method Name : updatePriceChangeReq
	 * @param pscmprd0009
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public int updatePriceChangeReq(PSCMPRD0009VO pscmprd0009) throws SQLException {
		return sqlMapClient.update("PSCMPRD0009.updatePriceChangeReq", pscmprd0009);
	}

	/**
	 * Desc : 상품가격 데이터 수정 메소드
	 * @Method Name : updateProductPriceChange
	 * @param pscmprd0009
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
//	public void updateProductPriceChange(PSCMPRD0009VO pscmprd0009) throws SQLException {
//		insert("PSCMPRD0009.updateProductPrice", pscmprd0009);
//	}
	
	public void updateCategoryAssignChange(PSCMPRD0009VO pscmprd0009) throws SQLException {
		insert("PSCMPRD0009.updateCategoryAssign", pscmprd0009);
	}

	/**
	 * Desc : 상품단품가격 데이터 수정 메소드
	 * @Method Name : updateStoreItemPriceChange
	 * @param pscmprd0009
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
//	public void updateStoreItemPriceChange(PSCMPRD0009VO pscmprd0009) throws SQLException {
//		insert("PSCMPRD0009.updateStoreItemPrice", pscmprd0009);
//	}

	/**
	 * Desc : 점포단품가격변경로그 데이터 등록 메소드
	 * @Method Name : insertStoreItemPriceLog
	 * @param pscmprd0009
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public void insertStoreItemPriceLog(PSCMPRD0009VO pscmprd0009) throws SQLException {
		insert("PSCMPRD0009.insertStoreItemPriceLog", pscmprd0009);
	}

	/**
	 * Desc : 상품단품 정보를 조회하는 메소드
	 * @Method Name : selectProductItemList
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PSCMPRD0009VO> selectProductItemList(DataMap paramMap) throws SQLException {
		return (List<PSCMPRD0009VO>)getSqlMapClientTemplate().queryForList("PSCMPRD0009.selectProductItemList", paramMap);
	}

	/**
	 * Desc : 상품단품 정보 중복 카운트를 조회하는 메소드
	 * @Method Name : selectProductItemDupCount
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectProductItemDupCount(DataMap paramMap) throws SQLException {
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMPRD0009.selectProductItemDupCount", paramMap);
	}

	/**
	 * Desc : 상품가격변경요청리스트 엑셀다운로드하는 메소드
	 * @Method Name : selectPriceChangeList
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PSCMPRD0009VO> selectPriceChangeListExcel(PSCMPRD0009VO vo) throws SQLException {
		return (List<PSCMPRD0009VO>)getSqlMapClientTemplate().queryForList("PSCMPRD0009.selectPriceChangeListExcel", vo);
	}
	
}
