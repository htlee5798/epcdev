/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.calculation.dao;

import java.sql.SQLException;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.calculation.model.PSCMCAL0002VO;

/**
 * @Class Name : PSCMCAL0002Dao
 * @Description : 배송료 정산 목록 Dao 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 4:11:19 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Repository("PSCMCAL0002Dao")
public class PSCMCAL0002Dao extends AbstractDAO {
	
	/**
	 * Desc : 배송료 정산 목록수를 조회하는 메소드
	 * @Method Name : selectDeliveryCostsCalculateCount
	 * @param searchVO
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectDeliveryCostsCalculateCount(PSCMCAL0002VO searchVO) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMCAL0002.selectDeliveryCostsCalculateCount", searchVO);
	}

	/**
	 * Desc : 배송료 정산 목록을 조회하는 메소드
	 * @Method Name : selectDeliveryCostsCalculateList
	 * @param searchVO
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectDeliveryCostsCalculateList(PSCMCAL0002VO searchVO) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMCAL0002.selectDeliveryCostsCalculateList", searchVO);
	}

	/**
	 * Desc : 배송료 정산 주문 통계를 조회하는 메소드
	 * @Method Name : selectDeliveryCostsCalculateOrderStats
	 * @param searchVO
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectDeliveryCostsCalculateOrderStats(PSCMCAL0002VO searchVO) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMCAL0002.selectDeliveryCostsCalculateOrderStats", searchVO);
	}

	/**
	 * Desc : 배송료 정산 배송료 통계를 조회하는 메소드
	 * @Method Name : selectDeliveryCostsCalculateDeliveryStats
	 * @param searchVO
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectDeliveryCostsCalculateDeliveryStats(PSCMCAL0002VO searchVO) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMCAL0002.selectDeliveryCostsCalculateDeliveryStats", searchVO);
	}

	/**
	 * Desc : 배송료 정산 목록을 엑셀다운로드하는 메소드
	 * @Method Name : selectDeliveryCostsCalculateListExcel
	 * @param searchVO
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectDeliveryCostsCalculateListExcel(PSCMCAL0002VO searchVO) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMCAL0002.selectDeliveryCostsCalculateListExcel", searchVO);
	}

}
