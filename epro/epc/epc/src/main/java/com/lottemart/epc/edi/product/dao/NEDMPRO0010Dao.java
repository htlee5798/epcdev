package com.lottemart.epc.edi.product.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.epc.edi.product.model.NEDMPRO0010VO;

/**
 * @Class Name : NEDMPRO0010Dao
 * @Description : 신상품현황조회 Dao 
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>O
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2015.12.21 	SONG MIN KYO	최초생성
 * </pre>
 */

@Repository("nEDMPRO0010Dao")
public class NEDMPRO0010Dao extends SqlMapClientDaoSupport {
	
	@Resource(name = "sqlMapClient")
    public void setSuperSqlMapClient(SqlMapClient sqlMapClient) {
        super.setSqlMapClient(sqlMapClient);
    }
	
	/**
	 * 신상품 현황 조회[온오프]
	 * 2016.03.17 이전 버전
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<NEDMPRO0010VO> selectNewProdFixList(Map<String, Object> paramMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0010.selectNewProdFixList", paramMap);
	}
	
	/**
	 * 신상품 등록 현황조회[온오프]
	 * 2016.03.17 이후 버전
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<NEDMPRO0010VO> selectNewProdFixList_2(Map<String, Object> paramMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0010.selectNewProdFixList_2", paramMap);
	}
	
	/**
	 * RFC 전송할 이미지 정보 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HashMap> selectNewProdImgInfoToRFC(Map<String, Object> paramMap) throws Exception {
		return (List<HashMap>) getSqlMapClientTemplate().queryForList("NEDMPRO0010.selectNewProdImgInfoToRFC", paramMap);
	}
	
	/**
	 * 신상품 현황 조회[온라인]
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<NEDMPRO0010VO> selectNewProdOnlineFixList(Map<String, Object> paramMap) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0010.selectNewProdOnlineFixList", paramMap);
	}
		
}
