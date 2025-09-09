package com.lottemart.epc.edi.main.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.main.model.MainDashBoardVO;

import lcn.module.framework.base.AbstractDAO;


@Repository("mainDashBoardDao")
public class MainDashBoardDao extends AbstractDAO {

	/**
	 * Main → 파트너사 실적 조회
	 * @param paramVo
	 * @return List<Map<String, Object>>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectVenPrfrList(MainDashBoardVO paramVo) throws Exception {
		return (List<Map<String,Object>>) getSqlMapClientTemplate().queryForList("MainDashBoard.selectVenPrfrList", paramVo);
	}
	
	
	/**
	 * Main → 파트너사 매입액 TOP 10 카운터 조회
	 * @param paramVo
	 * @return int
	 * @throws Exception
	 */
	public int selectVenBuyCount(MainDashBoardVO paramVo) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("MainDashBoard.selectVenBuyCount", paramVo);
	}
	
	/**
	 * Main → 파트너사 매입액 TOP 10 조회
	 * @param paramVo
	 * @return MainDashBoardVO>
	 * @throws Exception
	 */
	public List<MainDashBoardVO> selectVenBuyList(MainDashBoardVO paramVo) throws Exception{
		return (List<MainDashBoardVO>)getSqlMapClientTemplate().queryForList("MainDashBoard.selectVenBuyList", paramVo);
	}
	
	/**
	 * Main → 신상품 실적 카운터 조회
	 * @param paramVo
	 * @return  int
	 * @throws Exception
	 */
	public int selectNewProdPrfrCount(MainDashBoardVO paramVo) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("MainDashBoard.selectNewProdPrfrCount", paramVo);
	}
	
	/**
	 * Main → 신상품 실적 조회
	 * @param paramVo
	 * @return List<MainDashBoardVO>
	 * @throws Exception
	 */
	public List<MainDashBoardVO> selectNewProdPrfrList(MainDashBoardVO paramVo) throws Exception{
		return (List<MainDashBoardVO>)getSqlMapClientTemplate().queryForList("MainDashBoard.selectNewProdPrfrList", paramVo);
	}
	
	/**
	 * Main → 신상품 입점 제안 카운터 조회
	 * @param paramVo
	 * @return  int
	 * @throws Exception
	 */
	public int selectProdNewPropCount(MainDashBoardVO paramVo) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("MainDashBoard.selectProdNewPropCount", paramVo);
	}
	
	/**
	 * Main → 신상품 입점 제안 조회
	 * @param paramVo
	 * @return List<MainDashBoardVO>
	 * @throws Exception
	 */
	public List<MainDashBoardVO> selectProdNewPropList(MainDashBoardVO paramVo) throws Exception{
		return (List<MainDashBoardVO>)getSqlMapClientTemplate().queryForList("MainDashBoard.selectProdNewPropList", paramVo);
	}
	
	
	/**
	 * Main → 원가변경요청 내역 및 상태 조회 Count
	 * @param paramVo
	 * @return  int
	 * @throws Exception
	 */
	public int selectMainTpcProdChgCostItemCnt(MainDashBoardVO paramVo) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("MainDashBoard.selectMainTpcProdChgCostItemCnt", paramVo);
	}
	
	/**
	 * Main → 원가변경요청 내역 및 상태 조회
	 * @param paramVo
	 * @return List<MainDashBoardVO>
	 * @throws Exception
	 */
	public List<MainDashBoardVO> selectMainTpcProdChgCostItem(MainDashBoardVO paramVo) throws Exception{
		return (List<MainDashBoardVO>)getSqlMapClientTemplate().queryForList("MainDashBoard.selectMainTpcProdChgCostItem", paramVo);
	}
	
	/**
	 * Main → 미납내역 조회 Count
	 * @param paramVo
	 * @return  int
	 * @throws Exception
	 */
	public int selectMainNonPayItemsCnt(MainDashBoardVO paramVo) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("MainDashBoard.selectMainNonPayItemsCnt", paramVo);
	}
	
	/**
	 * Main → 미납내역 조회
	 * @param paramVo
	 * @return List<MainDashBoardVO>
	 * @throws Exception
	 */
	public List<MainDashBoardVO> selectMainNonPayItems(MainDashBoardVO paramVo) throws Exception{
		return (List<MainDashBoardVO>)getSqlMapClientTemplate().queryForList("MainDashBoard.selectMainNonPayItems", paramVo);
	}
	
	/**
	 * Main → 미납내역 > 미납율 조회
	 * @param paramVo
	 * @return String
	 * @throws Exception
	 */
	public String selectMainNonPayRate(MainDashBoardVO paramVo) throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("MainDashBoard.selectMainNonPayRate", paramVo);
	}
	
	/**
	 * Main → 파트너사별 SKU 현황 조회 Count
	 * @param paramVo
	 * @return  int
	 * @throws Exception
	 */
	public int selectMainVenSkuCnt(MainDashBoardVO paramVo) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("MainDashBoard.selectMainVenSkuCnt", paramVo);
	}
	
	/**
	 * Main → 파트너사별 SKU 현황 조회
	 * @param paramVo
	 * @return List<MainDashBoardVO>
	 * @throws Exception
	 */
	public List<MainDashBoardVO> selectMainVenSku(MainDashBoardVO paramVo) throws Exception{
		return (List<MainDashBoardVO>)getSqlMapClientTemplate().queryForList("MainDashBoard.selectMainVenSku", paramVo);
	}
	
	/**
	 * Main → 입고 거부 상품 미조치 내역 Count
	 * @param paramVo
	 * @return  int
	 * @throws Exception
	 */
	public int selectMainInboundRejectsCnt(MainDashBoardVO paramVo) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("MainDashBoard.selectMainInboundRejectsCnt", paramVo);
	}
	
	/**
	 * Main → 입고 거부 상품 미조치 내역 조회
	 * @param paramVo
	 * @return List<MainDashBoardVO>
	 * @throws Exception
	 */
	public List<MainDashBoardVO> selectMainInboundRejects(MainDashBoardVO paramVo) throws Exception{
		return (List<MainDashBoardVO>)getSqlMapClientTemplate().queryForList("MainDashBoard.selectMainInboundRejects", paramVo);
	}
	
	/**
	 * Main → 입고 거부 상품 미조치 내역 > 거부율 조회
	 * @param paramVo
	 * @return String
	 * @throws Exception
	 */
	public String selectMainInboundRejectsRate(MainDashBoardVO paramVo) throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("MainDashBoard.selectMainInboundRejectsRate", paramVo);
	}
	
}
