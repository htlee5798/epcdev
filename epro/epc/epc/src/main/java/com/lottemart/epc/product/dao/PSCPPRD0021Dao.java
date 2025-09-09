package com.lottemart.epc.product.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.model.PSCPPRD0021VO;

/**
 * 
 * @author jyLim
 * @Class : com.lottemart.bos.product.dao
 * @Description : 통계 > 업체상품 수정요청
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *     수정일      	 	    수정자                                         수정내용
 *  -----------    	--------    ---------------------------
 * 2012. 3. 9.	 	jyLim
 * @version : 1.0
 * </pre>
 */
@Repository("PSCPPRD0021Dao")
public class PSCPPRD0021Dao {

	@Autowired
	private SqlMapClient sqlMapClient;
	
	/**
	 * 
	 * @see selectConcernProdSoldout_detail
	 * @Locaton    : com.lottemart.bos.product.dao
	 * @MethodName  : selectConcernProdSoldout_detail
	 * @author     : jyLim
	 * @Description : 업체상품 수정요청 상세 조회
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectProdImgChgHist(DataMap paramMap) throws SQLException {
		return (List<DataMap>)sqlMapClient.queryForList("pscpprd0021.selectProdImgChgHist",paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectProdDescChgHist(DataMap paramMap) throws SQLException {
		return (List<DataMap>)sqlMapClient.queryForList("pscpprd0021.selectProdDescChgHist",paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectProdAddChgHist(DataMap paramMap) throws SQLException {
		return (List<DataMap>)sqlMapClient.queryForList("pscpprd0021.selectProdAddChgHist",paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectProdMstChgHist(DataMap paramMap) throws SQLException {
		return (List<DataMap>)sqlMapClient.queryForList("pscpprd0021.selectProdMstChgHist",paramMap);
	}
	
	/**
	 * Desc : 업체상품 수정요청 상품 반려
	 * @Method Name : prodNoAprv
	 * @param PSCPPRD0021VO
	 * @throws SQLException
	 * @return 결과수
	 */
	public int prodAprvStatus(PSCPPRD0021VO bean) throws SQLException{
		return sqlMapClient.update("pscpprd0021.prodAprvStatus", bean);
	}
	
	/**
	 * Desc : 업체상품 수정요청 상품 승인
	 * @Method Name : prodAprv
	 * @param PSCPPRD0021VO
	 * @throws SQLException
	 * @return 결과수
	 */
	
	public int prodImageStatusAprv(PSCPPRD0021VO bean) throws SQLException{
		return sqlMapClient.update("pscpprd0021.prodImageStatusAprv", bean);
	}
	
	public int prodDescStatusAprv(PSCPPRD0021VO bean) throws SQLException{
		return sqlMapClient.update("pscpprd0021.prodDescStatusAprv", bean);
	}
	
	public int prodAddInfoStatusAprv(PSCPPRD0021VO bean) throws SQLException{
		return sqlMapClient.update("pscpprd0021.prodAddInfoStatusAprv", bean);
	}
	
	public int prodAddInfoStatusDelete(PSCPPRD0021VO bean) throws SQLException{
		return sqlMapClient.update("pscpprd0021.prodAddInfoStatusDelete", bean);
	}
	
	@SuppressWarnings("unchecked")
	public DataMap selectProdDesc(DataMap paramMap) throws SQLException {
		return (DataMap)sqlMapClient.queryForObject("pscpprd0021.selectProdDesc",paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public DataMap selectProdInfoView(DataMap paramMap) throws SQLException {
		return (DataMap)sqlMapClient.queryForObject("pscpprd0021.selectProdInfoView",paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectProdAddBefore(DataMap paramMap) throws SQLException {
		return (List<DataMap>)sqlMapClient.queryForList("pscpprd0021.selectProdAddBefore",paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectProdAddAfter(DataMap paramMap) throws SQLException {
		return (List<DataMap>)sqlMapClient.queryForList("pscpprd0021.selectProdAddAfter",paramMap);
	}
	
	//20180911 상품키워드 입력 기능 추가	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectProdKeywordChgHist(DataMap paramMap) throws SQLException {
		return (List<DataMap>)sqlMapClient.queryForList("pscpprd0021.selectProdKeywordChgHist",paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectProdKeywordChgBefore(DataMap paramMap) throws SQLException {
		return (List<DataMap>)sqlMapClient.queryForList("pscpprd0021.selectProdKeywordChgBefore",paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public List<DataMap> selectProdKeywordChgAfter(DataMap paramMap) throws SQLException {
		return (List<DataMap>)sqlMapClient.queryForList("pscpprd0021.selectProdKeywordChgAfter",paramMap);
	}
	//20180911 상품키워드 입력 기능 추가
	
}
