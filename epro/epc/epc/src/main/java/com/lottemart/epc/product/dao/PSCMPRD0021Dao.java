package com.lottemart.epc.product.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.common.util.DataMap;

/**
 * 
 * @author khkim
 * @Description : 상품관리 - 대표상품코드관리
 * @Class : com.lottemart.epc.product.dao
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011.12.16  khkim
 * @version : 
 * </pre>
 */
@Repository("pscmprd0021Dao")
public class PSCMPRD0021Dao {

	@Autowired
	private SqlMapClient sqlMapClient;
	
	/**
	 * 
	 * @see selectRepProdCdList
	 * @Locaton    : com.lottemart.epc.product.dao
	 * @MethodName  : selectRepProdCdList
	 * @author     : khkim
	 * @Description : 대표상품코드 목록 조회
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectRepProdCdList(DataMap paramMap) throws SQLException {
		return sqlMapClient.queryForList("pscmprd0021.selectRepProdCdList", paramMap);
	}

}
