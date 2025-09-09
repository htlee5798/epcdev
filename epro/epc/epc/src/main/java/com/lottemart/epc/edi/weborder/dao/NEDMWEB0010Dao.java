package com.lottemart.epc.edi.weborder.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.lcnjf.util.StringUtil;
import com.lottemart.epc.edi.weborder.model.NEDMWEB0010VO;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.model.TedOrdList;
import com.lottemart.epc.edi.weborder.model.TedOrdList001VO;
import com.lottemart.epc.edi.weborder.model.TedPoOrdMstVO;


@Repository("nedmweb0010Dao")
public class NEDMWEB0010Dao extends AbstractDAO {
	private static final Logger logger = LoggerFactory.getLogger(NEDMWEB0010Dao.class);

	
	@SuppressWarnings("deprecation")
	public int selectVendorException(NEDMWEB0010VO nEDMWEB0010VO) {
		return ((Integer) getSqlMapClientTemplate().queryForObject("NEDMWEB0010.selectVendorException", nEDMWEB0010VO)).intValue();
	}
	
	@SuppressWarnings("deprecation")
	public int selectStoreOrdListTotCnt(NEDMWEB0010VO nEDMWEB0010VO) {
		return ((Integer) getSqlMapClientTemplate().queryForObject("NEDMWEB0010.selectStoreOrdListTotCnt", nEDMWEB0010VO)).intValue();
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<NEDMWEB0010VO> selectStoreOrdList(NEDMWEB0010VO nEDMWEB0010VO) {
		return (List<NEDMWEB0010VO>)getSqlMapClientTemplate().queryForList("NEDMWEB0010.selectStoreOrdList", nEDMWEB0010VO);
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<NEDMWEB0010VO> selectStoreOrdListFixedStr(NEDMWEB0010VO nEDMWEB0010VO) {
		return (List<NEDMWEB0010VO>)getSqlMapClientTemplate().queryForList("NEDMWEB0010.selectStoreOrdList_FixedStrCd", nEDMWEB0010VO);
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public NEDMWEB0010VO selectStoreOrdCntSum(NEDMWEB0010VO nEDMWEB0010VO) {
		return (NEDMWEB0010VO)getSqlMapClientTemplate().queryForObject("NEDMWEB0010.selectStoreOrdCntSum", nEDMWEB0010VO);
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<NEDMWEB0010VO> selectOrdDetInfo(NEDMWEB0010VO nEDMWEB0010VO) {
		return (List<NEDMWEB0010VO>)getSqlMapClientTemplate().queryForList("NEDMWEB0010.selectOrdDetInfo", nEDMWEB0010VO);
	}
	
	/** 발주등록번호 생성 */
	public String selectNewOrdReqNo() {
		return (String)selectByPk("NEDMWEB0010.selectNewOrdReqNo", null);
	}
	
	/** 발주등록번호  조회 */
	public String selectOrdReqNo(NEDMWEB0010VO nEDMWEB0010VO) {
		return (String)selectByPk("NEDMWEB0010.selectOrdReqNo", nEDMWEB0010VO);
	}
	
	/**
	 * 업체발주요청 마스터 저장
	 * @param TedPoOrdMstVO
	 * @return void
	 * @throws SQLException
	 */
	public void insertOrdMstInfo(NEDMWEB0010VO vo) throws DataAccessException {
		insert("NEDMWEB0010.insertOrdMstInfo", vo);
	}
	
	/**
	 * 업체발주요청 상품정보 저장
	 * @param TedOrdList001VO
	 * @return void
	 * @throws SQLException
	 */
	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes"})
	public void insertOrdProdInfo(final ArrayList<NEDMWEB0010VO> prodList) throws Exception, SQLException {
		getSqlMapClientTemplate().execute(new SqlMapClientCallback(){
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
		    	executor.startBatch();
		    	
		    	for ( NEDMWEB0010VO  ord : prodList ) {
		    		//if("I".equals(ord.getSaveFg())){
		    		//if("".equals(ord.getOrdReqNo()) || ord.getOrdReqNo().length() == 0 || "null".equals(ord.getOrdReqNo())){
		    		if(StringUtil.isEmpty(ord.getOrdReqNo()) || "null".equals(ord.getOrdReqNo())){
		    			ord.setMdModCd("00");			// MD 수정구분
						ord.setRegStsCd("0");			// 발송구분
						ord.setRegId(ord.getVenCd());	// 등록자 ID
						ord.setModId(ord.getVenCd());	// 수정자 ID
		    			executor.insert("NEDMWEB0010.insertOrdProdInfo", ord);
		    		}else{
		    			executor.update("NEDMWEB0010.updateOrdProdInfo", ord);
		    		}
		        }
		    	return new Integer(executor.executeBatch());
			}
		});
		
	}
	
	/**
	 * 업체발주요청 상품정보 삭제
	 * @param TedOrdList001VO
	 * @return void
	 * @throws SQLException
	 */
	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes"})
	public void deleteOrdProdInfo(final ArrayList<NEDMWEB0010VO> prodList) throws Exception, SQLException {
		getSqlMapClientTemplate().execute(new SqlMapClientCallback(){
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
		    	executor.startBatch();
		    	
		    	for ( NEDMWEB0010VO  ord : prodList ) {
		    		logger.debug("ORD_REQ_NO = " + ord.getOrdReqNo());
		    		logger.debug("PROD_CD =" + ord.getProdCd());
		    		executor.delete("NEDMWEB0010.deleteOrdProdInfo", ord);
		        }
		    	return new Integer(executor.executeBatch());
			}
		});
		
	}
	
	
	/**
	 * 업체발주요청 상품정보 + 마스터 정보 통합 삭제
	 * @param TedOrdList001VO
	 * @return void
	 * @throws SQLException
	 */
	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes"})
	public void deleteInOrdMstProdInfo(final TedOrdList001VO prodList) throws Exception, SQLException {
		getSqlMapClientTemplate().execute(new SqlMapClientCallback(){
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				TedOrdList  ordM = null;
				executor.startBatch();
		    	/*PROD 선택된 상품정보 삭제*/
		    	for ( TedOrdList  ord : prodList ) {
		    		executor.delete("NEDMWEB0010.deleteInOrdProdInfo", ord);
		    		if(ordM == null) ordM = ord;
		    	}
		    	/*점포전체 삭제된 자료가 있을경우 MST 삭제*/
		    	executor.delete("NEDMWEB0010.deleteAllOrdMst", ordM);
		    	return new Integer(executor.executeBatch());
			}
		});
		
	}
	
	/**
	 * 업체발주요청 상품정보 IN 조건 삭제
	 * @param TedOrdList001VO
	 * @return void
	 * @throws SQLException
	 */
	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes"})
	public void deleteInOrdProdInfo(final TedOrdList001VO prodList) throws Exception, SQLException {
		getSqlMapClientTemplate().execute(new SqlMapClientCallback(){
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				TedOrdList  ordM = null;
				executor.startBatch();
		    	/*PROD 선택된 상품정보 삭제*/
		    	for ( TedOrdList  ord : prodList ) {
		    		executor.delete("NEDMWEB0010.deleteInOrdProdInfo", ord);
		    	}
		    	return new Integer(executor.executeBatch());
			}
		});
		
	}	
	
	
	/**
	 * 업체발주요청 마스터 상품 합계 일괄  저장
	 * @param TedPoOrdMstVO
	 * @return void
	 * @throws SQLException
	 */
	public void updateOrdMstProdSum(NEDMWEB0010VO vo) throws SQLException {
		update("NEDMWEB0010.updateOrdMstProdSum", vo);
	}
	
	/** 발주등록번호  조회 */
	public Integer selectProdCnt(NEDMWEB0010VO vo) {
		return (Integer) getSqlMapClientTemplate().queryForObject("NEDMWEB0010.selectProdCnt", vo);
	}
	
	/** 업체발주요청 마스터 삭제 */
	public void deleteOrdMst(NEDMWEB0010VO vo) throws SQLException{
		this.delete("NEDMWEB0010.deleteOrdMst", vo);
	}
	
	/** 업체발주요청 마스터 전체 삭제 */
	public void deleteAllOrdMst(TedPoOrdMstVO vo) throws SQLException{
		this.delete("NEDMWEB0010.deleteAllOrdMst", vo);
	}
	
	/** 승인 요청 카운트 (ven_cd, ordDy) */
	public int selectAppReqCnt(SearchWebOrder vo) {
		return ((Integer) getSqlMapClientTemplate().queryForObject("NEDMWEB0010.selectAppReqCnt", vo)).intValue();
	}
	
	/** 승인 요청가능 여부 확인 (ven_cd, ordDy) */
	public int selectAppReqChkCnt(SearchWebOrder vo) {
		return ((Integer) getSqlMapClientTemplate().queryForObject("NEDMWEB0010.selectAppReqChkCnt", vo)).intValue();
	}
	
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<TedOrdList> selectAppReqFailInfo(SearchWebOrder vo) {
		return (List<TedOrdList>)getSqlMapClientTemplate().queryForList("NEDMWEB0010.selectAppReqFailInfo", vo);
	}
	
	/**
	 * 승인요청 정보 저장
	 * @param TedPoOrdMstVO
	 * @return void
	 * @throws SQLException
	 */
	public void updateOrdMstState(TedPoOrdMstVO vo) throws SQLException {
		update("NEDMWEB0010.updateOrdMstState", vo);
	}
	
	/**
	 * 승인요청 실패 데이터 상태값 변경
	 * @param TedOrdList
	 * @return void
	 * @throws SQLException
	 */
	public void updateOrdProdState(TedOrdList vo) throws SQLException {
		update("NEDMWEB0010.updateOrdProdState", vo);
	}
	
}
