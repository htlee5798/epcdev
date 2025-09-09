package com.lottemart.epc.edi.comm.dao;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.stereotype.Repository;

import lcn.module.framework.base.AbstractDAO;

/**
 * 
 * @Class Name : EpcRestApiDao.java
 * @Description :  EPC REST API 공통 
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2025.06.05		yun				최초생성
 *               </pre>
 */
@Repository(value="epcRestApiDao")
public class EpcRestApiDao extends AbstractDAO {

	/**
	 * [BOS] 신상품등록 > 상태확인
	 * @param paramMap
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> selectNewProdRegSts(Map<String,Object> paramMap) throws Exception{
		return (Map<String, Object>) getSqlMapClientTemplate().queryForObject("epcRestApiQuery.selectNewProdRegSts", paramMap);
	}
	
	/**
	 * [BOS] 신상품등록 > BOS 승인상태 UPDATE
	 * @param paramMap
	 * @return int
	 * @throws Exception
	 */
	public int updateNewProdSts(Map<String,Object> paramMap) throws Exception{
		return (int) getSqlMapClientTemplate().update("epcRestApiQuery.updateNewProdSts", paramMap);
	}
	
	/**
	 * [BOS] 신상품등록 > MD 전송구분 UPDATE
	 * @param paramMap
	 * @return int
	 * @throws Exception
	 */
	public int updateNewProdMdSendDivnCd(Map<String,Object> paramMap) throws Exception{
		return (int) getSqlMapClientTemplate().update("epcRestApiQuery.updateNewProdMdSendDivnCd", paramMap);
	}
	
	/**
	 * [BOS] 신상품등록 > 소분류 매칭되지 않는 그룹분석속성 제거
	 * @param paramMap
	 * @return int
	 * @throws Exception
	 */
	public int deleteNewProdGrpAtt(Map<String,Object> paramMap) throws Exception{
		return (int) getSqlMapClientTemplate().update("epcRestApiQuery.deleteNewProdGrpAtt", paramMap);
	}
	
	/**
	 * [BOS] 신상품등록 > 전상법 온라인상품코드 UPDATE
	 * @param paramMap
	 * @return int
	 * @throws Exception
	 */
	public int updateNewProdAddInfoVal(Map<String,Object> paramMap) throws Exception{
		return (int) getSqlMapClientTemplate().update("epcRestApiQuery.updateNewProdAddInfoVal", paramMap);
	}
	
	/**
	 * [BOS] 신상품등록 > KC인증 온라인상품코드 UPDATE
	 * @param paramMap
	 * @return int
	 * @throws Exception
	 */
	public int updateNewProdCertInfoVal(Map<String,Object> paramMap) throws Exception{
		return (int) getSqlMapClientTemplate().update("epcRestApiQuery.updateNewProdCertInfoVal", paramMap);
	}
	
	/**
	 * [BOS] 신상품등록 > 오카도 전시카테고리 대표여부 UPDATE
	 * @param paramMap
	 * @return int
	 * @throws Exception
	 */
	public int updateTecOspCatProdMapping(Map<String,Object> paramMap) throws Exception{
		return (int) getSqlMapClientTemplate().update("epcRestApiQuery.updateTecOspCatProdMapping", paramMap);
	}
	
	/**
	 * [협력사로그인] 협력사 로그인 key 등록
	 * @param paramMap
	 * @return int
	 * @throws Exception
	 */
	public int updateMakeVendorLoginKey(Map<String,Object> paramMap) throws Exception{
		return (int) getSqlMapClientTemplate().update("epcRestApiQuery.updateMakeVendorLoginKey", paramMap);
	}
	
	/**
	 * [협력사로그인] 협력사 마스터에 존재하는 업체인치 확인
	 * @param paramMap
	 * @return int
	 * @throws Exception
	 */
	public int selectChkVendorInHqVen(Map<String,Object> paramMap) throws Exception{
		return (int) getSqlMapClientTemplate().queryForObject("epcRestApiQuery.selectChkVendorInHqVen", paramMap);	
	}
	
}
