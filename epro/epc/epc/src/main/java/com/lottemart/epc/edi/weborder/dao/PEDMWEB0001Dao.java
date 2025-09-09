package com.lottemart.epc.edi.weborder.dao;

import java.sql.SQLException;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.lcnjf.util.StringUtil;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.model.TedOrdList;
import com.lottemart.epc.edi.weborder.model.TedOrdList001VO;
import com.lottemart.epc.edi.weborder.model.TedPoOrdMstVO;


@Repository("pedmweb0001Dao")
public class PEDMWEB0001Dao extends AbstractDAO {

	
	@SuppressWarnings("deprecation")
	public int selectVendorException(SearchWebOrder vo) {
		return ((Integer) getSqlMapClientTemplate().queryForObject("PEDMWEB0001.selectVendorException", vo)).intValue();
	}
	
	@SuppressWarnings("deprecation")
	public int selectStoreOrdListTotCnt(SearchWebOrder vo) {
		return ((Integer) getSqlMapClientTemplate().queryForObject("PEDMWEB0001.selectStoreOrdListTotCnt", vo)).intValue();
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<TedOrdList> selectStoreOrdList(SearchWebOrder vo) {
		// TODO Auto-generated method stub
		return (List<TedOrdList>)getSqlMapClientTemplate().queryForList("PEDMWEB0001.selectStoreOrdList", vo);
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<TedOrdList> selectStoreOrdListFixedStr(SearchWebOrder vo) {
		// TODO Auto-generated method stub
		return (List<TedOrdList>)getSqlMapClientTemplate().queryForList("PEDMWEB0001.selectStoreOrdList_FixedStrCd", vo);
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public TedOrdList selectStoreOrdCntSum(SearchWebOrder vo) {
		// TODO Auto-generated method stub
		return (TedOrdList)getSqlMapClientTemplate().queryForObject("PEDMWEB0001.selectStoreOrdCntSum", vo);
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<TedOrdList> selectOrdDetInfo(SearchWebOrder vo) {
		// TODO Auto-generated method stub
		return (List<TedOrdList>)getSqlMapClientTemplate().queryForList("PEDMWEB0001.selectOrdDetInfo", vo);
	}
	
	/** 발주등록번호 생성 */
	public String selectNewOrdReqNo() {
		return (String)selectByPk("PEDMWEB0001.selectNewOrdReqNo", null);
	}
	
	/** 발주등록번호  조회 */
	public String selectOrdReqNo(TedPoOrdMstVO vo) {
		return (String)selectByPk("PEDMWEB0001.selectOrdReqNo", vo);
	}
	
	/**
	 * 업체발주요청 마스터 저장
	 * @param TedPoOrdMstVO
	 * @return void
	 * @throws SQLException
	 */
	public void insertOrdMstInfo(TedPoOrdMstVO vo) throws DataAccessException {
		insert("PEDMWEB0001.insertOrdMstInfo", vo);
	}
	
	/**
	 * 업체발주요청 상품정보 저장
	 * @param TedOrdList001VO
	 * @return void
	 * @throws SQLException
	 */
	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes"})
	public void insertOrdProdInfo(final TedOrdList001VO prodList) throws Exception, SQLException {
		getSqlMapClientTemplate().execute(new SqlMapClientCallback(){
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
		    	executor.startBatch();
		    	
		    	for ( TedOrdList  ord : prodList ) {
		    		//if("I".equals(ord.getSaveFg())){
		    		//if("".equals(ord.getOrdReqNo()) || ord.getOrdReqNo().length() == 0 || "null".equals(ord.getOrdReqNo())){
		    		if(StringUtil.isEmpty(ord.getOrdReqNo()) || "null".equals(ord.getOrdReqNo())){
		    			ord.setMdModCd("00");			// MD 수정구분
						ord.setRegStsCd("0");			// 발송구분
						ord.setRegId(ord.getVenCd());	// 등록자 ID
						ord.setModId(ord.getVenCd());	// 수정자 ID
		    			executor.insert("PEDMWEB0001.insertOrdProdInfo", ord);
		    		}else{
		    			executor.update("PEDMWEB0001.updateOrdProdInfo", ord);
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
	public void deleteOrdProdInfo(final TedOrdList001VO prodList) throws Exception, SQLException {
		getSqlMapClientTemplate().execute(new SqlMapClientCallback(){
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
		    	executor.startBatch();
		    	
		    	for ( TedOrdList  ord : prodList ) {
		    		executor.delete("PEDMWEB0001.deleteOrdProdInfo", ord);
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
		    		executor.delete("PEDMWEB0001.deleteInOrdProdInfo", ord);
		    		if(ordM == null) ordM = ord;
		    	}
		    	/*점포전체 삭제된 자료가 있을경우 MST 삭제*/
		    	executor.delete("PEDMWEB0001.deleteAllOrdMst", ordM);
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
		    		executor.delete("PEDMWEB0001.deleteInOrdProdInfo", ord);
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
	public void updateOrdMstProdSum(TedPoOrdMstVO vo) throws SQLException {
		update("PEDMWEB0001.updateOrdMstProdSum", vo);
	}
	
	/** 발주등록번호  조회 */
	public Integer selectProdCnt(TedPoOrdMstVO vo) {
		return (Integer) getSqlMapClientTemplate().queryForObject("PEDMWEB0001.selectProdCnt", vo);
	}
	
	/** 업체발주요청 마스터 삭제 */
	public void deleteOrdMst(TedPoOrdMstVO vo) throws SQLException{
		this.delete("PEDMWEB0001.deleteOrdMst", vo);
	}
	
	/** 업체발주요청 마스터 전체 삭제 */
	public void deleteAllOrdMst(TedPoOrdMstVO vo) throws SQLException{
		this.delete("PEDMWEB0001.deleteAllOrdMst", vo);
	}
	
	/** 승인 요청 카운트 (ven_cd, ordDy) */
	public int selectAppReqCnt(SearchWebOrder vo) {
		return ((Integer) getSqlMapClientTemplate().queryForObject("PEDMWEB0001.selectAppReqCnt", vo)).intValue();
	}
	
	/** 승인 요청가능 여부 확인 (ven_cd, ordDy) */
	public int selectAppReqChkCnt(SearchWebOrder vo) {
		return ((Integer) getSqlMapClientTemplate().queryForObject("PEDMWEB0001.selectAppReqChkCnt", vo)).intValue();
	}
	
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<TedOrdList> selectAppReqFailInfo(SearchWebOrder vo) {
		// TODO Auto-generated method stub
		return (List<TedOrdList>)getSqlMapClientTemplate().queryForList("PEDMWEB0001.selectAppReqFailInfo", vo);
	}
	
	/**
	 * 승인요청 정보 저장
	 * @param TedPoOrdMstVO
	 * @return void
	 * @throws SQLException
	 */
	public void updateOrdMstState(TedPoOrdMstVO vo) throws SQLException {
		update("PEDMWEB0001.updateOrdMstState", vo);
	}
	
	/**
	 * 승인요청 실패 데이터 상태값 변경
	 * @param TedOrdList
	 * @return void
	 * @throws SQLException
	 */
	public void updateOrdProdState(TedOrdList vo) throws SQLException {
		update("PEDMWEB0001.updateOrdProdState", vo);
	}
	
}
