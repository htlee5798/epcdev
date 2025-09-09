package com.lottemart.epc.edi.product.dao;

import java.util.List;
import java.util.HashMap;
import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.epc.edi.product.model.NEDMPRO0090VO;

@Component("nedmtst0090Dao")
public class NEDMPRO0090Dao extends SqlMapClientDaoSupport{
	
	@Resource(name = "sqlMapClient")
	public void setSuperSqlMapClient(SqlMapClient sqlMapClient) {
		super.setSqlMapClient(sqlMapClient);
	}
	
	/**
	 * 소분류별 그룹분석속성 조회
	 * @param l3Cd 
	 * @return 
	 * @throws Exception
	 */
	public List<HashMap> selectGrpAtt(String l3Cd) throws Exception {
		return (List<HashMap>) getSqlMapClientTemplate().queryForList("NEDMPRO0090.selectGrpAtt", l3Cd);
	}
	
	/**
	 * 소분류별 그룹분석속성값 조회
	 * @param l3Cd 
	 * @return 
	 * @throws Exception
	 */
	public List<HashMap> selectGrpAttOpt(String l3Cd) throws Exception {
		return (List<HashMap>) getSqlMapClientTemplate().queryForList("NEDMPRO0090.selectGrpAttOpt", l3Cd);
	}

	/**
	 * 그룹분석속성 삭제
	 * @param prodCd
	 * @return
	 * @throws Exception
	 */
	public void deleteGrpAttWithProdCd(String prodCd) throws Exception {
		getSqlMapClientTemplate().delete("NEDMPRO0090.deleteGrpAttOptSaved", prodCd);
	}
	
	/**
	 * 그룹분석속성 저장
	 * @param HashMap
	 * @return
	 * @throws Exception
	 */
	public void mergeGrpAttOptTmp(HashMap paramMap) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0090.mergeGrpAttOptTmp", paramMap);
	}
	
	/**
	 * 분석속성순번 획득
	 * @param HashMap
	 * @return
	 * @throws Exception	
	 */
	public String getMaxSeqAttr(String prodCd) throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("NEDMPRO0090.getMaxSeqAttr", prodCd);
	}
	
	/**
	 * SAP에서 응답오지 않은 분석속성 개수
	 * @param HashMap
	 * @return
	 * @throws Exception
	 */
	public Integer getCntNotResponseAttr(NEDMPRO0090VO vo) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("NEDMPRO0090.getCntNotResponseAttr", vo);
	}
	
	/**
	 * 분석속성 저장한 값 불러오기
	 * @param HashMap
	 * @return
	 * @throws Exception
	 */
	public List<HashMap> selectGrpAttrSelectedOptInfo(String prodCd) throws Exception {
		return (List<HashMap>) getSqlMapClientTemplate().queryForList("NEDMPRO0090.selectGrpAttrSelectedOptInfo", prodCd);
	}
	
	/**
	 * ECO에 저장된 분석속성값 불러오기
	 * @param HashMap
	 * @return
	 * @throws Exception
	 */
	public List<HashMap> selectGrpAttrEcoSavedOptInfo(String prodCd) throws Exception {
		return (List<HashMap>) getSqlMapClientTemplate().queryForList("NEDMPRO0090.selectGrpAttrEcoSavedOptInfo", prodCd);
	}
	
	/**
	 * 분석속성 저장한 값 불러오기
	 * @param HashMap
	 * @return
	 * @throws Exception
	 */
	public void updateModifyStatusAttr(NEDMPRO0090VO vo) throws Exception {
		getSqlMapClientTemplate().update("NEDMPRO0090.updateModifyStatusAttr", vo);
	}
	
	/**
	 * 기존 시퀀스로 요청한 분석속성값이 있는지 확인 (개수확인)
	 * @param NEDMPRO0090VO
	 * @return
	 * @throws Exception
	 */
	public Integer selectCntGrpReq(NEDMPRO0090VO vo) throws Exception {
		return (Integer)getSqlMapClientTemplate().queryForObject("NEDMPRO0090.selectCntGrpReq", vo);
	}
	
	/**
	 * 그룹분석속성 요청테이블 저장
	 * @param HashMap
	 * @return
	 * @throws Exception
	 */
	public void insertGrpAttOptReq(HashMap paramMap) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0090.insertGrpAttOptReq", paramMap);
	}
	
	/**
	 * 그룹분석속성 요청테이블 삭제
	 * @param NEDMRPO0090VO
	 * @return
	 * @throws Exception
	 */
	public void deleteGrpAttOptReq(NEDMPRO0090VO vo) throws Exception {
		getSqlMapClientTemplate().delete("NEDMPRO0090.deleteGrpAttOptReq", vo);
	}
}
