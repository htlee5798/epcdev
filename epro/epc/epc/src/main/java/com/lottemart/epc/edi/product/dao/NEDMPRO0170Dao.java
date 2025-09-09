package com.lottemart.epc.edi.product.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.epc.edi.product.model.NEDMPRO0150VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0170VO;

@Component("nedmpro0170Dao")
public class NEDMPRO0170Dao extends SqlMapClientDaoSupport {
 
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
		return (String) getSqlMapClientTemplate().queryForObject("NEDMPRO0170.selectCurrDate", null);
	}

	/**
	 * Count
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int selectDisconProductCount(NEDMPRO0170VO vo) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("NEDMPRO0170.selectDisconProductCount", vo);
	}
	
	/**
	 * 등록한 상품 LISt 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0170VO> selectProductList(NEDMPRO0170VO vo) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0170.selectProductList", vo);
	}
	
	/**
	 * 단품정보 등록
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public void insertDisconProduct(NEDMPRO0170VO vo) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0170.insertDisconProduct", vo);
	}
	
	/**
	 * 단품정보 삭제
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public void deleteDisconProduct(NEDMPRO0170VO vo) throws Exception {
		getSqlMapClientTemplate().delete("NEDMPRO0170.deleteDisconProduct", vo);
	}
	
	/**
	 * 등록한 단품정보 LISt 조회 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0170VO> selectDisconProductList(NEDMPRO0170VO vo) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0170.selectDisconProductList", vo);
	}
	
	/**
	 * 등록한 단품정보 LISt 조회 ( 날짜에 기반해서 )
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0170VO> selectDisconProductListByDate(NEDMPRO0170VO vo) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0170.selectDisconProductListByDate", vo);
	}
}
