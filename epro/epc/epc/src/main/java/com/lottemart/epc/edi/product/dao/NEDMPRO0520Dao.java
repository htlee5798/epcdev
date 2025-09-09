package com.lottemart.epc.edi.product.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.epc.common.model.CommonFileVO;
import com.lottemart.epc.edi.product.model.NEDMPRO0040VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0400VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0520VO;

/**
 * 
 * @Class Name : NEDMPRO0520Dao.java
 * @Description : ESG 인증관리 Dao
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2025.03.28		yun				최초생성
 *               </pre>
 */
@Repository("nEDMPRO0520Dao")
public class NEDMPRO0520Dao extends SqlMapClientDaoSupport {
	
	@Resource(name = "sqlMapClient")
    public void setSuperSqlMapClient(SqlMapClient sqlMapClient) {
        super.setSqlMapClient(sqlMapClient);
    }
	
	/**
	 * ESG 인증 리스트 카운트
	 * @param nEDMPRO0520VO
	 * @return int
	 * @throws Exception
	 */
	public int selectProdEsgListCnt(NEDMPRO0520VO nEDMPRO0520VO) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("NEDMPRO0520.selectProdEsgListCnt", nEDMPRO0520VO);
	}
	
	/**
	 * ESG 인증 리스트 조회
	 * @param nEDMPRO0520VO
	 * @return List<NEDMPRO0520VO>
	 * @throws Exception
	 */
	public List<NEDMPRO0520VO> selectProdEsgList(NEDMPRO0520VO nEDMPRO0520VO) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0520.selectProdEsgList", nEDMPRO0520VO);
	}
	
	/**
	 * ESG 인증정보 상세
	 * @param nEDMPRO0520VO
	 * @return NEDMPRO0520VO
	 * @throws Exception
	 */
	public NEDMPRO0520VO selectProdEsgDetail(NEDMPRO0520VO nEDMPRO0520VO) throws Exception {
		return (NEDMPRO0520VO) getSqlMapClientTemplate().queryForObject("NEDMPRO0520.selectProdEsgDetail", nEDMPRO0520VO);
	}
	
	/**
	 * ESG 인증정보 변경
	 * @param nEDMPRO0520VO
	 * @return int
	 * @throws Exception
	 */
	public int updateProdEsgInfo(NEDMPRO0520VO nEDMPRO0520VO) throws Exception {
		return (int) getSqlMapClientTemplate().update("NEDMPRO0520.updateProdEsgInfo", nEDMPRO0520VO);
	}
	
	/**
	 * ESG 파일정보 등록
	 * @param nEDMPRO0520VO
	 * @return int
	 * @throws Exception
	 */
	public int mergeProdEsgFile(NEDMPRO0520VO nEDMPRO0520VO) throws Exception {
		return (int) getSqlMapClientTemplate().update("NEDMPRO0520.mergeProdEsgFile", nEDMPRO0520VO);
	}
	
	/**
	 * ESG 파일정보 조회
	 * @param nEDMPRO0520VO
	 * @return NEDMPRO0520VO
	 * @throws Exception
	 */
	public NEDMPRO0520VO selectEsgFileInfo(NEDMPRO0520VO nEDMPRO0520VO) throws Exception {
		return (NEDMPRO0520VO) getSqlMapClientTemplate().queryForObject("NEDMPRO0520.selectEsgFileInfo", nEDMPRO0520VO);
	}
	
	/**
	 * ESG Proxy 전송 데이터
	 * @param nEDMPRO0520VO
	 * @return List<HashMap>
	 * @throws Exception
	 */
	public List<HashMap> selectProdEsgRfcData(NEDMPRO0520VO nEDMPRO0520VO) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0520.selectProdEsgRfcData", nEDMPRO0520VO);
	}
	
	/**
	 * ESG 인증 변경 요청 상태 업데이트 
	 * @param nEDMPRO0520VO
	 * @return int
	 * @throws Exception
	 */
	public int updateProdEsgCfmFg(NEDMPRO0520VO nEDMPRO0520VO) throws Exception {
		return (int) getSqlMapClientTemplate().update("NEDMPRO0520.updateProdEsgCfmFg", nEDMPRO0520VO);
	}
	
	/**
	 * ESG 인증정보 파일아이디 업데이트
	 * @param nEDMPRO0520VO
	 * @return int
	 * @throws Exception
	 */
	public int updateProdEsgFileId(NEDMPRO0520VO nEDMPRO0520VO) throws Exception {
		return (int) getSqlMapClientTemplate().update("NEDMPRO0520.updateProdEsgFileId", nEDMPRO0520VO);
	}
	
	/**
	 * 등록된 ESG 파일정보 조회 (SFTP 전송용)
	 * @param nEDMPRO0520VO
	 * @return List<Map<String,Object>>
	 * @throws Exception
	 */
	public List<Map<String,Object>> selectTpcProdEsgFileForSend(NEDMPRO0520VO nEDMPRO0520VO) throws Exception {
		return (List<Map<String,Object>>) getSqlMapClientTemplate().queryForList("NEDMPRO0520.selectTpcProdEsgFileForSend", nEDMPRO0520VO);
	}
	
	/**
	 * ESG 파일 SFTP 전송 정보 UPDATE
	 * @param paramVo
	 * @return int
	 * @throws Exception
	 */
	public int updateTpcProdEsgFileSendInfo(NEDMPRO0520VO paramVo) throws Exception{
		return (int) getSqlMapClientTemplate().update("NEDMPRO0520.updateTpcProdEsgFileSendInfo", paramVo);
	}
}
