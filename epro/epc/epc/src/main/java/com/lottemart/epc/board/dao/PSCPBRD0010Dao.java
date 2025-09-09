package com.lottemart.epc.board.dao;


import java.sql.SQLException;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.board.model.PSCPBRD0010SearchVO;

/**
 * @Class Name : PSCPBRD0010Dao.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 12. 16. 오후 03:03:03 mjChoi
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Repository("pscpbrd0010Dao")
public class PSCPBRD0010Dao extends AbstractDAO
{
	/**
	 * 콜센터1:1 상세 내역을 조회
	 * @Method Name : selectCallCenterPopupDetail
	 * @param VO
	 * @return VO
	 * @throws SQLException
	 */
	public PSCPBRD0010SearchVO selectCallCenterPopupDetail(PSCPBRD0010SearchVO searchVO) throws SQLException
	{
		return (PSCPBRD0010SearchVO) selectByPk("pscpbrd0010.selectCallCenterPopupDetail", searchVO);
	}
	
	/**
	 * Desc : 콜센터 1:1문의 상세보기에서 메모를 저장하는 메소드
	 * @Method Name : updateCallCenterPopupDetail
	 * @param VO
	 * @return void
	 * @throws Exception
	 */
	public void updateCallCenterPopupDetail(PSCPBRD0010SearchVO searchVO) throws SQLException{
		update("pscpbrd0010.updateCallCenterPopupDetail", searchVO);
	}

	@SuppressWarnings("unchecked")
	public List<DataMap> selectCodeList(DataMap paramMap) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("pscpbrd0010.selectCodeList", paramMap);
	}			
}
 