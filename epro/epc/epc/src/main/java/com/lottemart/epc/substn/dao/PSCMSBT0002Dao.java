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
 * @Class Name : PSCMSBT0002Dao
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
@Repository("PSCMSBT0002Dao")
public class PSCMSBT0002Dao extends AbstractDAO{
	@Autowired
	private SqlMapClient sqlMapClient;
	/**
     * 업체별 매출공제 목록
     * @return DataMap
     * @throws SQLException  DB 관련 오류
     */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectVendorSubStnList(DataMap paramMap) throws SQLException{
		return (List<DataMap>) sqlMapClient.queryForList("pscmsbt0002.selectVendorSubStnList",paramMap);
	}
	
	/**
     * 업체별 매출상세 목록
     * @return DataMap
     * @throws SQLException  DB 관련 오류
     */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectProdSubStnList(DataMap paramMap) throws SQLException{
		return (List<DataMap>) sqlMapClient.queryForList("pscmsbt0002.selectProdSubStnList",paramMap);
	}
	
	
	
	
}