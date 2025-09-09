package com.lottemart.epc.edi.product.dao;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.epc.edi.product.model.NEDMPRO0530VO;

/**
 * 
 * @Class Name : NEDMPRO0530Dao.java
 * @Description : 상품확장 Dao
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
@Repository("nEDMPRO0530Dao")
public class NEDMPRO0530Dao extends SqlMapClientDaoSupport {
	
	@Resource(name = "sqlMapClient")
    public void setSuperSqlMapClient(SqlMapClient sqlMapClient) {
        super.setSqlMapClient(sqlMapClient);
    }
	
	/**
	 * 확장가능 상품내역 리스트 카운트
	 * @param nEDMPRO0530VO
	 * @return int
	 * @throws Exception
	 */
	public int selectExtAvailProdListCount(NEDMPRO0530VO nEDMPRO0530VO) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("NEDMPRO0530.selectExtAvailProdListCount", nEDMPRO0530VO);
	}

	/**
	 * 확장가능 상품내역 리스트 조회
	 * @param nEDMPRO0530VO
	 * @return List<NEDMPRO0530VO>
	 * @throws Exception
	 */
	public List<NEDMPRO0530VO> selectExtAvailProdList(NEDMPRO0530VO nEDMPRO0530VO) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0530.selectExtAvailProdList", nEDMPRO0530VO);
	}

	/**
	 * 채널확장 상세정보 조회
	 * @param nEDMPRO0530VO
	 * @return NEDMPRO0530VO
	 * @throws Exception
	 */
	public NEDMPRO0530VO selectTpcProdChanExtendDetailInfo(NEDMPRO0530VO nEDMPRO0530VO) throws Exception {
		return (NEDMPRO0530VO) getSqlMapClientTemplate().queryForObject("NEDMPRO0530.selectTpcProdChanExtendDetailInfo", nEDMPRO0530VO);
	}
	
	/**
	 * 채널확장정보 등록
	 * @param nEDMPRO0530VO
	 * @return int
	 * @throws Exception
	 */
	public int updateTpcProdChanExtendDetailInfo(NEDMPRO0530VO nEDMPRO0530VO) throws Exception {
		return (int) getSqlMapClientTemplate().update("NEDMPRO0530.updateTpcProdChanExtendDetailInfo", nEDMPRO0530VO);
	}
	
	/**
	 * 등록된 확장요청 정보 조회 (TAB 구성용)
	 * @param nEDMPRO0530VO
	 * @return List<NEDMPRO0530VO>
	 * @throws Exception
	 */
	public List<NEDMPRO0530VO> selectExtTabList(NEDMPRO0530VO nEDMPRO0530VO) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0530.selectExtTabList", nEDMPRO0530VO);
	}
	
	/**
	 * 상품별 채널 확장 가능 상태 확인
	 * @param nEDMPRO0530VO
	 * @return selectChkExtAbleProdChan
	 * @throws Exception
	 */
	public List<NEDMPRO0530VO> selectChkExtAbleProdChan(NEDMPRO0530VO nEDMPRO0530VO) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0530.selectChkExtAbleProdChan", nEDMPRO0530VO);
	}
	
	/**
	 * 확장요청 상태 업데이트
	 * @param nEDMPRO0530VO
	 * @return int
	 * @throws Exception
	 */
	public int updateTpcProdExtendSts(NEDMPRO0530VO nEDMPRO0530VO) throws Exception {
		return (int) getSqlMapClientTemplate().update("NEDMPRO0530.updateTpcProdExtendSts", nEDMPRO0530VO);
	}
	
	/**
	 * 확장요청정보 전송 DATA 
	 * @param nEDMPRO0530VO
	 * @return List<HashMap>
	 * @throws Exception
	 */
	public List<HashMap> selectTpcProdChanExtendProxyData(NEDMPRO0530VO nEDMPRO0530VO) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0530.selectTpcProdChanExtendProxyData", nEDMPRO0530VO);
	}
	
	/**
	 * 상품마스터에서 상품속성 확인
	 * @param nEDMPRO0530VO
	 * @return NEDMPRO0530VO
	 * @throws Exception
	 */
	public NEDMPRO0530VO selectProductDetails(NEDMPRO0530VO nEDMPRO0530VO) throws Exception {
		return (NEDMPRO0530VO) getSqlMapClientTemplate().queryForObject("NEDMPRO0530.selectProductDetails", nEDMPRO0530VO);
	}
	
	/**
	 * 확장요청정보 삭제
	 * @param nEDMPRO0530VO
	 * @return int
	 * @throws Exception
	 */
	public int deleteTpcExtProdReg(NEDMPRO0530VO nEDMPRO0530VO) throws Exception {
		return (int) getSqlMapClientTemplate().delete("NEDMPRO0530.deleteTpcExtProdReg", nEDMPRO0530VO);
	}
}
