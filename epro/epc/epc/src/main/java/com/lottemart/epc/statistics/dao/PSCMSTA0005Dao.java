/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.statistics.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;

/**
 * @Class Name : PSCMSTA0005Dao
 * @Description : 적립마일리지정산관리 Dao 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 5:37:12 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Repository("PSCMSTA0005Dao")
public class PSCMSTA0005Dao extends AbstractDAO {

	/**
	 * Desc : 적립마일리지정산관리 목록 조회 메소드
	 * @Method Name : getSavingMileageList
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> getSavingMileageList(Map<String, String> paramMap) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMSTA0005.getSavingMileageList", paramMap);
	}

	/**
	 * Desc : 적립마일리지 코드 조회 메소드
	 * @Method Name : getSavingMileageCodeList
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> getSavingMileageCodeList() throws SQLException{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMSTA0005.getSavingMileageCodeList", paramMap);
	}
}
