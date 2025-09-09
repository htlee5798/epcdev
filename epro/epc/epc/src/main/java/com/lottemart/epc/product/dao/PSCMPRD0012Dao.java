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

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;

/**
 * @Class Name : PSCMPRD0012Dao
 * @Description : 상품이미지촬영스케쥴켈린더 Dao 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 5:06:06 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Repository("pscmprd0012Dao")
public class PSCMPRD0012Dao extends AbstractDAO {

	/**
	 * Desc : 상품이미지촬영스케쥴켈린더 조회 메소드
	 * @Method Name : selectCalendarScheduleList
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectCalendarScheduleList(DataMap paramMap) throws SQLException {
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMPRD0012.selectCalendarScheduleList", paramMap);
	}
}
