package com.lottemart.epc.system.dao;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.epc.system.model.PSCMSYS0002VO;

/**
 * @Class Name : PSCMSYS0002Dao.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 20. 오후 02:02:02 mjChoi
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Repository("pscmsys0002Dao")
public class PSCMSYS0002Dao extends AbstractDAO{
	
	@Autowired
	private SqlMapClient sqlMapClient;
	
	/**
	 * 담당자관리 폼 페이지
	 * @Method Name : selectPersonInChargeInfo
	 * @param paramMap
	 * @return List<VO>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PSCMSYS0002VO> selectPersonInChargeInfo(Map<String, String> paramMap) throws SQLException{
		return sqlMapClient.queryForList("pscmsys0002.selectPersonInChargeInfo",paramMap);
	}
	
	/**
	 * 담당자관리 수정 처리
	 * @Method Name : updateListPersonInCharge
	 * @param VO
	 * @return void
	 * @throws Exception
	 */
	public void updateListPersonInCharge(PSCMSYS0002VO pscmsys0002VO) throws SQLException{
		sqlMapClient.update("pscmsys0002.updateListPersonInCharge",pscmsys0002VO);
	}
}
