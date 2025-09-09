/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2015.02.23
 * @author      : hippie 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.statistics.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;

/**
 * @Class Name : PSCMSTA0011Dao
 * @Description : 에누리 Dao 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2015.02.23	hippie
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Repository("PSCMSTA0011Dao")
public class PSCMSTA0011Dao extends AbstractDAO {

	/**
	 * 구매상품 정보 목록
	 * Desc : 
	 * @Method Name : selectPrdItemList
	 * @param DataMap
	 * @return List
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectOrderProdItemList(Map<String, String> paramMap) throws SQLException{
		return (List<DataMap>) getSqlMapClientTemplate().queryForList("PSCMSTA0011.selectOrderProdItemList",paramMap);
	}
	
}
