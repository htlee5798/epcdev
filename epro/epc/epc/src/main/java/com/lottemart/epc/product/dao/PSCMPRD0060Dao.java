package com.lottemart.epc.product.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

import lcn.module.framework.base.AbstractDAO;

/**
 * @Class Name : PSCMPRD0060Dao.java
 * @Description : 공통 점포 팝업 DAO
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2019. 4. 18. 오전 10:12:00 신규생성
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Repository("pscmprd0060Dao")
public class PSCMPRD0060Dao extends AbstractDAO {

	@Autowired
	private SqlMapClient sqlMapClient;

	/**
	 * Desc : 점포 정보를 가져온다.
	 * @Method Name : selectStoreList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectStoreList(Map<Object, Object> paramMap) throws Exception{
		return (List<Map<String, Object>>) sqlMapClient.queryForList("PSCMPRD0060.selectStoreList", paramMap);
	}
	
}
