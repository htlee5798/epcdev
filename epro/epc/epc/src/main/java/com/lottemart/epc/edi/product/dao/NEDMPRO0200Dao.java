package com.lottemart.epc.edi.product.dao;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.epc.edi.product.model.NEDMPRO0090VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0200VO;

@Component("nEDMPRO0200Dao")
public class NEDMPRO0200Dao extends SqlMapClientDaoSupport{
	
	@Resource(name = "sqlMapClient")
	public void setSuperSqlMapClient(SqlMapClient sqlMapClient) {
		super.setSqlMapClient(sqlMapClient);
	}
	
	/**
	 * 소분류별 영양성분 조회
	 * @param l3Cd 
	 * @return 
	 * @throws Exception
	 */
	public List<HashMap> selectNutAtt(String l3Cd) throws Exception {
		return (List<HashMap>) getSqlMapClientTemplate().queryForList("NEDMPRO0200.selectNutAtt", l3Cd);
	}
	
	/**
	 * 영양성분값 조회
	 * @param l3Cd 
	 * @return 
	 * @throws Exception
	 */
	public List<HashMap> selectNutAttInfo(String l3Cd) throws Exception {
		return (List<HashMap>) getSqlMapClientTemplate().queryForList("NEDMPRO0200.selectNutAttInfo", l3Cd);
	}

	/**
	 * 영양성분값 삭제
	 * @param prodCd
	 * @return
	 * @throws Exception
	 */
	public void deleteNutAmtSaved(String prodCd) throws Exception {
		getSqlMapClientTemplate().delete("NEDMPRO0200.deleteNutAmtSaved", prodCd);
	}
	
	/**
	 * 영양성분값 저장
	 * @param HashMap
	 * @return
	 * @throws Exception
	 */
	public void mergeNutAmtTmp(HashMap paramMap) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0200.mergeNutAmtTmp", paramMap);
	}
	
	/**
	 * 영양성분순번 획득
	 * @param HashMap
	 * @return
	 * @throws Exception	
	 */
	public String getProdNutMaxSeq(String prodCd) throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("NEDMPRO0200.getProdNutMaxSeq", prodCd);
	}
	
	/**
	 * SAP에서 응답안온 영양성분 개수
	 * @param HashMap
	 * @return
	 * @throws Exception
	 */
	public Integer getCntNotNutAttRes(NEDMPRO0200VO vo) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("NEDMPRO0200.getCntNotNutAttRes", vo);
	}
	
	/**
	 * 영양성분 저장한 값 불러오기
	 * @param HashMap
	 * @return
	 * @throws Exception
	 */
	public List<HashMap> selectNutAmtSaved(String prodCd) throws Exception {
		return (List<HashMap>) getSqlMapClientTemplate().queryForList("NEDMPRO0200.selectNutAmtSaved", prodCd);
	}
	
	/**
	 * ECO에 저장된 영양성분값 불러오기
	 * @param HashMap
	 * @return
	 * @throws Exception
	 */
	public List<HashMap> selectNutAmtEcoSaved(String prodCd) throws Exception {
		return (List<HashMap>) getSqlMapClientTemplate().queryForList("NEDMPRO0200.selectNutAmtEcoSaved", prodCd);
	}
	
	/**
	 * 영양성분 수정가능 구분자 수정 
	 * @param HashMap
	 * @return
	 * @throws Exception
	 */
	public void updateNutModifyStatus(NEDMPRO0200VO vo) throws Exception {
		getSqlMapClientTemplate().update("NEDMPRO0200.updateNutModifyStatus", vo);
	}
	
	/**
	 * 기존 시퀀스로 요청한 영양성분속성값이 있는지 확인 (개수확인)
	 * @param NEDMPRO0200VO
	 * @return
	 * @throws Exception
	 */
	public Integer selectCntNutReq(NEDMPRO0200VO vo) throws Exception {
		return (Integer)getSqlMapClientTemplate().queryForObject("NEDMPRO0200.selectCntNutReq", vo);
	}
	
	/**
	 * 영양성분 요청테이블 저장
	 * @param HashMap
	 * @return
	 * @throws Exception
	 */
	public void insertNutAmtReq(HashMap paramMap) throws Exception {
		getSqlMapClientTemplate().insert("NEDMPRO0200.insertNutAmtReq", paramMap);
	}
	
	/**
	 * 영양성분 요청테이블 삭제
	 * @param NEDMRPO0090VO
	 * @return
	 * @throws Exception
	 */
	public void deleteNutAmtReq(NEDMPRO0200VO vo) throws Exception {
		getSqlMapClientTemplate().delete("NEDMPRO0200.deleteNutAmtReq", vo);
	}
}
