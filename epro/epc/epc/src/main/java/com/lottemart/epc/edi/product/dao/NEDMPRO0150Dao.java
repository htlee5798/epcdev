package com.lottemart.epc.edi.product.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.epc.edi.product.model.NEDMPRO0150VO;

@Component("nedmpro0150Dao")
public class NEDMPRO0150Dao extends SqlMapClientDaoSupport {

	@Resource(name = "sqlMapClient")
	public void setSuperSqlMapClient(SqlMapClient sqlMapClient) {
		super.setSqlMapClient(sqlMapClient);
	}
	
	/**
	 * 현재일자 가져오기
	 * @return
	 * @throws Exception
	 */
	public String selectCurrDate() throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("NEDMPRO0150.selectCurrDate", null);
	}

	/**
	 * Count
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int selectPbProductCount(NEDMPRO0150VO vo) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("NEDMPRO0150.selectPbProductCount", vo);
	}

	/**
	 * List 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0150VO> selectPbProductList(NEDMPRO0150VO vo) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0150.selectPbProductList", vo);
	}
	
	/**
	 * 재고등록
	 * @param vo
	 * @throws Exception
	 */
	public void insertPbProduct(NEDMPRO0150VO vo) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0150.insertPbProduct", vo);
	}
	
}
