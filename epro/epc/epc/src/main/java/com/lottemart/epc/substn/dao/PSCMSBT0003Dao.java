package com.lottemart.epc.substn.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.common.util.DataMap;

/**
 *  
 * @Class Name : PSCMSBT0003Dao
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자	   수정내용
 *  -------    --------    ---------------------------
 * 2015. 12. 7   skc
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Repository("PSCMSBT0003Dao")
public class PSCMSBT0003Dao extends AbstractDAO{
	@Autowired
	private SqlMapClient sqlMapClient;
	/**
     * 정산내역조회
     * @return DataMap
     * @throws SQLException  DB 관련 오류
     */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectSubStnDeductList(DataMap paramMap) throws SQLException{
		return (List<DataMap>) sqlMapClient.queryForList("pscmsbt0003.selectSubStnDeductList",paramMap);
	}
	/**
     * 정산내역집
     * @return DataMap
     * @throws SQLException  DB 관련 오류
     */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectSubStnDeductSumList(DataMap paramMap) throws SQLException{
		return (List<DataMap>) sqlMapClient.queryForList("pscmsbt0003.selectSubStnDeductSumList",paramMap);
	}
		
	
	
	
}