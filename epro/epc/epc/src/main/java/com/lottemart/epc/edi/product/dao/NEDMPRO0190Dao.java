package com.lottemart.epc.edi.product.dao;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.epc.edi.product.model.NEDMPRO0080VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0190VO;

@Component("nEDMPRO0190Dao")
public class NEDMPRO0190Dao extends SqlMapClientDaoSupport{
	
	@Resource(name = "sqlMapClient")
	public void setSuperSqlMapClient(SqlMapClient sqlMapClient) {
		super.setSqlMapClient(sqlMapClient);
	}
	
	/**
	 * 영양성분 개수
	 * @param 
	 * @return 
	 * @throws Exception
	 */
	public int countNutAttrInfo(NEDMPRO0190VO vo) throws Exception {
		return (int) getSqlMapClientTemplate().queryForObject("NEDMPRO0190.countNutAttrInfo", vo);
	}
	
	/**
	 * 영양성분 값 조회
	 * @param 
	 * @return 
	 * @throws Exception
	 */
	public List<HashMap> selectNutAttrInfo(NEDMPRO0190VO vo) throws Exception {
		return (List<HashMap>) getSqlMapClientTemplate().queryForList("NEDMPRO0190.selectNutAttrInfo", vo);
	}
	
	/**
	 * 특정상품 영양성분 요청정보 조회 
	 * @param 
	 * @return 
	 * @throws Exception
	 */
	public List<HashMap> selectProdNutAprv(Map<String, Object> paramMap) throws Exception {
		return (List<HashMap>) getSqlMapClientTemplate().queryForList("NEDMPRO0190.selectProdNutAprv", paramMap);
	}
	
	/**
	 * 상품 대분류 매핑된 영양성분 조회
	 * @param 
	 * @return 
	 * @throws Exception
	 */
	public List<HashMap> selectNutWithProdL3Cd(Map<String, Object> paramMap) throws Exception {
		return (List<HashMap>) getSqlMapClientTemplate().queryForList("NEDMPRO0190.selectNutWithProdL3Cd", paramMap);
	}
}
