package com.lottemart.epc.product.dao;

import java.sql.SQLException;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;

/**
 * @Class Name : PSCMPRD0010Dao
 * @Description : 추가구성품관리 Dao 클래스
 * @Modification Information
 * <pre>
* << 개정이력(Modification Information) >>
 *   
 *   수정일       수정자           수정내용
 *  -------         --------    ---------------------------
 * 2016.04.27   projectBOS32	신규생성      
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Repository("pscmprd0010Dao")
public class PSCMPRD0010Dao extends AbstractDAO {

	/**
	 * Desc : 추가구성품을 조회하는 메소드
	 * @Method Name : selectComponentList
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectComponentList(DataMap paramMap) throws SQLException {
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMPRD0010.selectComponentList", paramMap);
	}
	
}
