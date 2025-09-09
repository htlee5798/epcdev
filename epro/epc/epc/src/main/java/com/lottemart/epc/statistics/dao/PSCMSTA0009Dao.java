/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.statistics.dao;

import java.sql.SQLException;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.statistics.model.PSCMSTA0009VO;

/**
 */
@Repository("PSCMSTA0009Dao")
public class PSCMSTA0009Dao extends AbstractDAO {

	@SuppressWarnings("unchecked")
	public List<DataMap> getLotteCardMallObjectCalList(PSCMSTA0009VO searchVO) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMSTA0009.selectLotteCardMallObjectCalList", searchVO);
	}
	
	@SuppressWarnings("unchecked")
	public List<DataMap> getLotteCardMallObjectCalListExcel(PSCMSTA0009VO searchVO) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMSTA0009.selectLotteCardMallObjectCalListExcel", searchVO);
	}
	
}
