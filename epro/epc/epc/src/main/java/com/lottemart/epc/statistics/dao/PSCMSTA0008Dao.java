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
import com.lottemart.epc.statistics.model.PSCMSTA0008SearchVO;

/**
 * @Class Name : PSCMSTA0004Dao
 * @Description : 아시아나정산관리 목록 조회 Dao 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 5:31:51 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Repository("PSCMSTA0008Dao")
public class PSCMSTA0008Dao extends AbstractDAO {

	/**
	 * Desc : 아시아나정산관리 목록 조회 메소드
	 * @Method Name : getAsianaMileageList
	 * @param searchVO
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> getLotteCardMallCalList(PSCMSTA0008SearchVO searchVO) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMSTA0008.selectLotteCardMallCalList", searchVO);
	}
	
	/**
	 * Desc : 아시아나정산관리 목록 엑셀조회 메소드
	 * @Method Name : getAsianaMileageList
	 * @param searchVO
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> getLotteCardMallCalListExcel(PSCMSTA0008SearchVO searchVO) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMSTA0008.selectLotteCardMallCalListExcel", searchVO);
	}
}
