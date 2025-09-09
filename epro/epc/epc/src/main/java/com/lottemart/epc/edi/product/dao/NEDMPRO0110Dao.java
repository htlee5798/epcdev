package com.lottemart.epc.edi.product.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.epc.edi.product.model.NEDMPRO0110VO;

@Component("nedmpro0110Dao")
public class NEDMPRO0110Dao extends SqlMapClientDaoSupport {
	
	@Resource(name = "sqlMapClient")
    public void setSuperSqlMapClient(SqlMapClient sqlMapClient) {
        super.setSqlMapClient(sqlMapClient);
    }
	
	/**
	 * Count
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int selectWholeProductCount(NEDMPRO0110VO vo) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("NEDMPRO0110.selectWholeProductCount", vo);
	}
	
	/**
	 * List 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0110VO> selectWholeProductList(NEDMPRO0110VO vo) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0110.selectWholeProductList", vo);
	}	
}
