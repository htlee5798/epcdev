package com.lottemart.epc.edi.product.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.epc.edi.product.model.NEDMPRO0500VO;

/**
 * 
 * @Class Name : NEDMPRO0500Dao.java
 * @Description : 원가변경요청 Dao
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2025.03.19		yun				최초생성
 *               </pre>
 */
@Repository("nEDMPRO0500Dao")
public class NEDMPRO0500Dao extends SqlMapClientDaoSupport {
	
	@Resource(name = "sqlMapClient")
    public void setSuperSqlMapClient(SqlMapClient sqlMapClient) {
        super.setSqlMapClient(sqlMapClient);
    }
	
	/**
	 * 원가변경요청 아이템리스트 카운트
	 * @param nEDMPRO0500VO
	 * @return int
	 * @throws Exception
	 */
	public int selectTpcProdChgCostItemListCnt(NEDMPRO0500VO nEDMPRO0500VO) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("NEDMPRO0500.selectTpcProdChgCostItemListCnt", nEDMPRO0500VO);
	}
	
	/**
	 * 원가변경요청 아이템리스트 조회
	 * @param nEDMPRO0500VO
	 * @return List<NEDMPRO0500VO>
	 * @throws Exception
	 */
	public List<NEDMPRO0500VO> selectTpcProdChgCostItemList(NEDMPRO0500VO nEDMPRO0500VO) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0500.selectTpcProdChgCostItemList", nEDMPRO0500VO);
	}
	
	/**
	 * 원가변경요청 상품코드/판매코드 별 최신 상태 정보조회
	 * @param nEDMPRO0500VO
	 * @return NEDMPRO0500VO
	 * @throws Exception
	 */
	public NEDMPRO0500VO selectTpcProdChgCostItemRcntStatus(NEDMPRO0500VO nEDMPRO0500VO) throws Exception {
		return (NEDMPRO0500VO) getSqlMapClientTemplate().queryForObject("NEDMPRO0500.selectTpcProdChgCostItemRcntStatus", nEDMPRO0500VO);
	}
	
	/**
	 * 원가변경요청 상태 조회
	 * @param nEDMPRO0500VO
	 * @return NEDMPRO0500VO
	 * @throws Exception
	 */
	public NEDMPRO0500VO selectTpcProdChgCostItemStatus(NEDMPRO0500VO nEDMPRO0500VO) throws Exception {
		return (NEDMPRO0500VO) getSqlMapClientTemplate().queryForObject("NEDMPRO0500.selectTpcProdChgCostItemStatus", nEDMPRO0500VO);
	}
	
	/**
	 * 원가변경요청 아이템 정보 저장
	 * @param nEDMPRO0500VO
	 * @return int
	 * @throws Exception
	 */
	public void insertTpcProdChgCostItem(NEDMPRO0500VO nEDMPRO0500VO) throws Exception {
//		return (int) getSqlMapClientTemplate().update("NEDMPRO0500.insertTpcProdChgCostItem", nEDMPRO0500VO);
		getSqlMapClientTemplate().insert("NEDMPRO0500.insertTpcProdChgCostItem", nEDMPRO0500VO);
	}
	
	/**
	 * 원가변경요청 아이템 정보 삭제
	 * @param nEDMPRO0500VO
	 * @return int
	 * @throws Exception
	 */
	public int deleteTpcProdChgCostItem(NEDMPRO0500VO nEDMPRO0500VO) throws Exception {
		return (int) getSqlMapClientTemplate().delete("NEDMPRO0500.deleteTpcProdChgCostItem", nEDMPRO0500VO);
	}
	
	/**
	 * 원가변경요청 진행상태변경
	 * @param nEDMPRO0500VO
	 * @return int
	 * @throws Exception
	 */
	public int updateTpcProdChgCostStats(NEDMPRO0500VO nEDMPRO0500VO) throws Exception {
		return (int) getSqlMapClientTemplate().update("NEDMPRO0500.updateTpcProdChgCostStats", nEDMPRO0500VO);
	}
	
	/**
	 * 공문번호 update
	 * @param nEDMPRO0500VO
	 * @return int
	 * @throws Exception
	 */
	public int updateDcContCode(NEDMPRO0500VO nEDMPRO0500VO) throws Exception {
		return (int) getSqlMapClientTemplate().update("NEDMPRO0500.updateDcContCode", nEDMPRO0500VO);
	}
	
	/**
	 * 원가변경요청 전송 데이터
	 * @param nEDMPRO0500VO
	 * @return List<HashMap>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HashMap> selectTpcChgCostItemSendData(NEDMPRO0500VO nEDMPRO0500VO) throws Exception {
		return (List<HashMap>) getSqlMapClientTemplate().queryForList("NEDMPRO0500.selectTpcChgCostItemSendData", nEDMPRO0500VO);
	}
	
	/**
	 * 원가변경요청 정보 ECS SendData
	 * @param nEDMPRO0500VO
	 * @return List<HashMap>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectTpcChgCostItemEcsSendData(NEDMPRO0500VO nEDMPRO0500VO) throws Exception {
		return (List<Map<String,Object>>) getSqlMapClientTemplate().queryForList("NEDMPRO0500.selectTpcChgCostItemEcsSendData", nEDMPRO0500VO);
	}
	
	/**
	 * ecs 연계 공문 번호 생성
	 * @param nEDMPRO0500VO
	 * @return String
	 * @throws Exception
	 */
	public String selectEcsContCode(NEDMPRO0500VO nEDMPRO0500VO) throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("NEDMPRO0500.selectEcsContCode", nEDMPRO0500VO);
	}
	
	/**
	 * 원가변경요청 RFC 응답 결과 및 상태 갱신
	 * @param nEDMPRO0500VO
	 * @return int
	 * @throws Exception
	 */
	public int updateTpcProdRfcStatus(NEDMPRO0500VO nEDMPRO0500VO) throws Exception {
		return (int) getSqlMapClientTemplate().update("NEDMPRO0500.updateTpcProdRfcStatus", nEDMPRO0500VO);
	}
	
	/**
	 * 원가변경 GroupId 생성
	 * @param nEDMPRO0500VO
	 * @return String
	 * @throws Exception
	 */
	public String selectTpcProdChgCostGrpId(NEDMPRO0500VO nEDMPRO0500VO) throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("NEDMPRO0500.selectTpcProdChgCostGrpId", nEDMPRO0500VO);
	}
	
	/**
	 * 원가변경 GroupId별 상태 확인 
	 * @param nEDMPRO0500VO
	 * @return NEDMPRO0500VO
	 * @throws Exception
	 */
	public NEDMPRO0500VO selectTpcProdChgCostItemStatusGrp(NEDMPRO0500VO nEDMPRO0500VO) throws Exception {
		return (NEDMPRO0500VO) getSqlMapClientTemplate().queryForObject("NEDMPRO0500.selectTpcProdChgCostItemStatusGrp", nEDMPRO0500VO);
	}
	
	/**
	 * 원가변경요청 동일 그룹아이디 일괄삭제
	 * @param nEDMPRO0500VO
	 * @return int
	 * @throws Exception
	 */
	public int deleteTpcProdChgCostItemGrp(NEDMPRO0500VO nEDMPRO0500VO) throws Exception {
		return (int) getSqlMapClientTemplate().delete("NEDMPRO0500.deleteTpcProdChgCostItemGrp", nEDMPRO0500VO);
	}
	
	/**
	 * 구매조직별 공문 기본생성구분 조회
	 * @param purDept
	 * @return String
	 * @throws Exception
	 */
	public String selectDcCreDefGbnForPurDept(String purDept) throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("NEDMPRO0500.selectDcCreDefGbnForPurDept", purDept);
	}
	
	/**
	 * 연관그룹 공문생성을 위한 요청정보 조회
	 * @param nEDMPRO0500VO
	 * @return List<NEDMPRO0500VO>
	 * @throws Exception
	 */
	public List<NEDMPRO0500VO> selectTpcProdChgCostItemsToDcCreGrp(NEDMPRO0500VO nEDMPRO0500VO) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0500.selectTpcProdChgCostItemsToDcCreGrp", nEDMPRO0500VO);
	}
	
	/**
	 * 선택한 요청건의 가장 빠른 원가변경요청일 조회
	 * @param nEDMPRO0500VO
	 * @return selectMinTpcChgReqCostDt
	 * @throws Exception
	 */
	public String selectMinTpcChgReqCostDt(NEDMPRO0500VO nEDMPRO0500VO) throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("NEDMPRO0500.selectMinTpcChgReqCostDt", nEDMPRO0500VO);
	}
	
	/**
	 * 그룹아이디로 요청정보 조회
	 * @param nEDMPRO0500VO
	 * @return List<NEDMPRO0500VO>
	 * @throws Exception
	 */
	public List<NEDMPRO0500VO> selectTpcProdChgCostItemsByGrpId(NEDMPRO0500VO nEDMPRO0500VO) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0500.selectTpcProdChgCostItemsByGrpId", nEDMPRO0500VO);
	}
	
	/**
	 * 현재 원가를 원가변경요청정보에 update
	 * @param nEDMPRO0500VO
	 * @return int
	 * @throws Exception
	 */
	public int updateTpcProdChgCostItemRcntOrgCost(NEDMPRO0500VO nEDMPRO0500VO) throws Exception {
		return (int) getSqlMapClientTemplate().update("NEDMPRO0500.updateTpcProdChgCostItemRcntOrgCost", nEDMPRO0500VO);
	}
	
	/**
	 * 원가변경요청정보의 원가정보 원복(null update)
	 * @param nEDMPRO0500VO
	 * @return int
	 * @throws Exception
	 */
	public int updateTpcProdChgCostItemOrgCostClear(NEDMPRO0500VO nEDMPRO0500VO) throws Exception {
		return (int) getSqlMapClientTemplate().update("NEDMPRO0500.updateTpcProdChgCostItemOrgCostClear", nEDMPRO0500VO);
	}
}
