package com.lottemart.epc.product.dao;

import java.sql.SQLException;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;

/**
 * @Class Name : PSCMPRD0050Dao
 * @Description : 증정품관리 Dao 클래스
 * @Modification Information
 * <pre>
* << 개정이력(Modification Information) >>
 *   
 *   수정일       수정자           수정내용
 *  -------         --------    ---------------------------
 * 2016.06.07   projectBOS32	신규생성      
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Repository("pscmprd0050Dao")
public class PSCMPRD0050Dao extends AbstractDAO {

	/**
	 * Desc : 증정품관리 조회하는 메소드
	 * @Method Name : selectGiftList
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectGiftList(DataMap paramMap) throws SQLException {
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMPRD0050.selectGiftList", paramMap);
	}
	
}
