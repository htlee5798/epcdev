package com.lottemart.epc.edi.product.dao;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.epc.edi.product.model.NEDMPRO0080VO;

@Component("nedmtst0080Dao")
public class NEDMPRO0080Dao extends SqlMapClientDaoSupport{
	
	@Resource(name = "sqlMapClient")
	public void setSuperSqlMapClient(SqlMapClient sqlMapClient) {
		super.setSqlMapClient(sqlMapClient);
	}
	
	/**
	 * 대분류 값 가져오기
	 * @param 
	 * @return 사용하는 모든 대분류
	 * @throws Exception
	 */
	public List<HashMap> selectL1CdAll() throws Exception {
		return (List<HashMap>) getSqlMapClientTemplate().queryForList("NEDMPRO0080.selectL1CdAll");
	}
	
	/**
	 * 분석속성 개수 얻기
	 * @param 
	 * @return 
	 * @throws Exception
	 */
	public int countGrpAttrInfo(NEDMPRO0080VO vo) throws Exception {
		return (int) getSqlMapClientTemplate().queryForObject("NEDMPRO0080.countGrpAttrInfo", vo);
	}
	
	/**
	 * 분석속성 값 가져오기
	 * @param 
	 * @return 
	 * @throws Exception
	 */
	public List<HashMap> selectGrpAttrInfo(NEDMPRO0080VO vo) throws Exception {
		return (List<HashMap>) getSqlMapClientTemplate().queryForList("NEDMPRO0080.selectGrpAttrInfo", vo);
	}
	
	/**
	 * 분석속성 승인 정보 가져오기 한 상품
	 * @param 
	 * @return 
	 * @throws Exception
	 */
	public List<HashMap> selectAttrAprvInfoAProd(Map<String, Object> paramMap) throws Exception {
		return (List<HashMap>) getSqlMapClientTemplate().queryForList("NEDMPRO0080.selectAttrAprvInfoAProd", paramMap);
	}
	
	/**
	 * 등록가능 분석속성 정보 가져오기
	 * @param 
	 * @return 
	 * @throws Exception
	 */
	public List<HashMap> selectAttrInfoAProd(Map<String, Object> paramMap) throws Exception {
		return (List<HashMap>) getSqlMapClientTemplate().queryForList("NEDMPRO0080.selectAttrInfoAProd", paramMap);
	}
}
