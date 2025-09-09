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
import com.lottemart.epc.product.model.PSCMPRD0011VO;

/**
 * @Class Name : PSCMPRD0011Dao
 * @Description : 상품이미지촬영스케쥴목록 Dao 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 4:27:40 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Repository("pscmprd0011Dao")
public class PSCMPRD0011Dao extends AbstractDAO {

//	@Autowired
//	private SqlMapClient sqlMapClient;
	
	
	/**
	 * Desc : 상품이미지촬영스케쥴목록 조회 메소드
	 * @Method Name : selectScheduleList
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PSCMPRD0011VO> selectScheduleList(DataMap paramMap) throws SQLException {
		return (List<PSCMPRD0011VO>)getSqlMapClientTemplate().queryForList("PSCMPRD0011.selectScheduleList", paramMap);
	}

	/**
	 * Desc : 상품이미지촬영스케쥴목록 수정 메소드
	 * @Method Name : updateSchedule
	 * @param pscmprd0011VO
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public int updateSchedule(PSCMPRD0011VO pscmprd0011VO) throws SQLException {
		return getSqlMapClientTemplate().update("PSCMPRD0011.updateSchedule", pscmprd0011VO);
	}

	/**
	 * Desc : 상품이미지촬영스케쥴목록 삭제 메소드
	 * @Method Name : deleteSchedule
	 * @param pscmprd0011VO
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public int deleteSchedule(PSCMPRD0011VO pscmprd0011VO) throws SQLException {
		return getSqlMapClientTemplate().update("PSCMPRD0011.deleteSchedule", pscmprd0011VO);
	}

	/**
	 * Desc : 상품이미지촬영스케쥴 중복 확인 메소드
	 * @Method Name : selectDupScheduleCount
	 * @param pscmprd0011VO
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectDupScheduleCount(PSCMPRD0011VO pscmprd0011VO) throws SQLException {
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMPRD0011.selectDupScheduleCount", pscmprd0011VO);
	}
	
	/**
	 * Desc : 상품이미지촬영스케쥴목록 엑셀다운로드하는 메소드
	 * @Method Name : selectScheduleMgrListExcel
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PSCMPRD0011VO> selectScheduleMgrListExcel(PSCMPRD0011VO pscmprd0011VO) throws SQLException {
		return (List<PSCMPRD0011VO>)getSqlMapClientTemplate().queryForList("PSCMPRD0011.selectScheduleMgrListExcel", pscmprd0011VO);
	}

}
