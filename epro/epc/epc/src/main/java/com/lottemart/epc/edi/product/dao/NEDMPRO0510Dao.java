package com.lottemart.epc.edi.product.dao;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.epc.edi.product.model.NEDMPRO0500VO;

/**
 * 
 * @Class Name : NEDMPRO0510Dao.java
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
@Repository("nEDMPRO0510Dao")
public class NEDMPRO0510Dao extends SqlMapClientDaoSupport {
	
	@Resource(name = "sqlMapClient")
    public void setSuperSqlMapClient(SqlMapClient sqlMapClient) {
        super.setSqlMapClient(sqlMapClient);
    }
	
	/**
	 * 원가변경요청 아이템리스트 카운트
	 * @param NEDMPRO0500VO
	 * @return int
	 * @throws Exception
	 */
	public int selectTpcProdChgCostItemListCnt(NEDMPRO0500VO NEDMPRO0500VO) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("NEDMPRO0510.selectTpcProdChgCostItemListCnt", NEDMPRO0500VO);
	}
	
	/**
	 * 원가변경요청 아이템리스트 조회
	 * @param NEDMPRO0500VO
	 * @return List<NEDMPRO0500VO>
	 * @throws Exception
	 */
	public List<NEDMPRO0500VO> selectTpcProdChgCostItemList(NEDMPRO0500VO NEDMPRO0500VO) throws Exception {
		return getSqlMapClientTemplate().queryForList("NEDMPRO0510.selectTpcProdChgCostItemList", NEDMPRO0500VO);
	}
	
	/**
	 * 원가변경요청 아이템 복사
	 * @param NEDMPRO0500VO
	 * @return int
	 * @throws Exception
	 */
	public int insertCopyTpcProdChgCostItem(NEDMPRO0500VO NEDMPRO0500VO) throws Exception {
		return (Integer) getSqlMapClientTemplate().update("NEDMPRO0510.insertCopyTpcProdChgCostItem", NEDMPRO0500VO);
	}
	
	/**
	 * 원가변경요청 리스트 엑셀다운로드 
	 * @param NEDMPRO0500VO
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String, String>> selectTpcProdChgCostDetailExcelInfo(NEDMPRO0500VO NEDMPRO0500VO) throws Exception {
		return (List<HashMap<String, String>>) getSqlMapClientTemplate().queryForList("NEDMPRO0510.selectTpcProdChgCostDetailExcelInfo", NEDMPRO0500VO);
	}
	
	/**
	 * 원가변경요청 아이템 정보 수정 저장
	 * @param NEDMPRO0500VO
	 * @return Integer
	 * @throws Exception
	 */
	public int updateTpcProdChgCostItem(NEDMPRO0500VO NEDMPRO0500VO) throws Exception {
		return (Integer) getSqlMapClientTemplate().update("NEDMPRO0510.updateTpcProdChgCostItem", NEDMPRO0500VO);
	}
	
	/**
	 * 원가변경요청 동일 그룹아이디 데이터 동일하게 수정
	 * @param NEDMPRO0500VO
	 * @return int
	 * @throws Exception
	 */
	public int updateTpcProdChgCostItemGrp(NEDMPRO0500VO NEDMPRO0500VO) throws Exception {
		return (Integer) getSqlMapClientTemplate().update("NEDMPRO0510.updateTpcProdChgCostItemGrp", NEDMPRO0500VO);
	}
}
