package com.lottemart.epc.edi.weborder.dao;

import java.sql.SQLException;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.model.TedOrdList;
import com.lottemart.epc.edi.weborder.model.TedPoOrdMstVO;
import com.lottemart.epc.edi.weborder.model.TedPoOrdPackList001VO;
import com.lottemart.epc.edi.weborder.model.TedPoOrdPackVO;


@Repository("pedmweb0003Dao")
public class PEDMWEB0003Dao extends AbstractDAO {
	
	// 발주 일괄 등록 카운트 조회
	@SuppressWarnings("deprecation")
	public int selectOrdPackListTotCnt(SearchWebOrder vo) {
		return ((Integer) getSqlMapClientTemplate().queryForObject("PEDMWEB0003.selectOrdPackListTotCnt", vo)).intValue();
	}
	
	// 발주 일괄 등록 목록 조회
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<TedOrdList> selectOrdPackList(SearchWebOrder vo) {
		// TODO Auto-generated method stub
		return (List<TedOrdList>)getSqlMapClientTemplate().queryForList("PEDMWEB0003.selectOrdPackList", vo);
	}
	
	// 점포 수 및 총 개수 및 총 합계
	@SuppressWarnings({"deprecation" })
	public TedOrdList selectOrdPackCntSum(SearchWebOrder vo) {
		// TODO Auto-generated method stub
		return (TedOrdList)getSqlMapClientTemplate().queryForObject("PEDMWEB0003.selectOrdPackCntSum", vo);
	}
	
	/** 파일 그룹 코드 생성 */
	public String selectNewFileGrpCd(String venCd) {
		return (String)selectByPk("PEDMWEB0003.selectNewFileGrpCd", venCd);
	}
	
	/** 파일구분 코드 생성 */
	public String selectNewPackDivnCd() {
		return (String)selectByPk("PEDMWEB0003.selectNewPackDivnCd", null);
	}
	
	/**
	 * 업체발주요청일괄등록 저장
	 * @param List<TED_PO_ORD_PACK>
	 * @return void
	 * @throws SQLException
	 */
	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes"})
	public void insertOrdPackInfo(final TedPoOrdPackList001VO packList) throws Exception, SQLException {
		getSqlMapClientTemplate().execute(new SqlMapClientCallback(){
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
		    	executor.startBatch();
		    	
		    	for ( TedPoOrdPackVO  ord : packList ) {
		    		executor.insert("PEDMWEB0003.insertOrdPackInfo", ord);
		        }
		    	return new Integer(executor.executeBatch());
			}
		});
		
	}
	
	/**
	 * 업체발주요청일괄등록 수정
	 * @param List<TED_PO_ORD_PACK>
	 * @return void
	 * @throws SQLException
	 */
	public void updateOrdPackInfo(TedPoOrdPackVO vo) throws Exception, SQLException {
		update("PEDMWEB0003.updateOrdPackInfo", vo);
	}
	
	/**
	 * 업체발주요청 테이블 : 상태코드 업데이트 상품코드 오류
	 * @param String
	 * @return void
	 * @throws SQLException
	 */
	public void updateOrdPackProdState(String packDivnCd) throws SQLException {
		update("PEDMWEB0003.updateOrdPackProdState", packDivnCd);
	}
	
	/**
	 * 업체발주요청 테이블 : 상태코드 업데이트, 점포 코드 오류
	 * @param String
	 * @return void
	 * @throws SQLException
	 */
	public void updateOrdPackStoreState(String packDivnCd) throws SQLException {
		update("PEDMWEB0003.updateOrdPackStoreState", packDivnCd);
	}
	
	/** 엑셀 발주요청 정보 삭제 */
	public void deleteExcelOrdInfo(TedPoOrdPackVO vo) throws SQLException{
		this.delete("PEDMWEB0003.deleteExcelOrdInfo", vo);
	}

	//엑셀 발주요청 오류카운트 조회
	@SuppressWarnings("deprecation")
	public int selectExcelErrorCnt(TedPoOrdPackVO vo) {
		return ((Integer) getSqlMapClientTemplate().queryForObject("PEDMWEB0003.selectExcelErrorCnt", vo)).intValue();
	}
	
	//엑셀 발주요청 중복 카운트 조회
	@SuppressWarnings("deprecation")
	public int selectExcelDuplCnt(TedPoOrdPackVO vo) {
		return ((Integer) getSqlMapClientTemplate().queryForObject("PEDMWEB0003.selectExcelDuplCnt", vo)).intValue();
	}
	
	// 업체발주요청 상품정보에 기 등록된 데이터가 있는지 확인.
	@SuppressWarnings("deprecation")
	public int selectOrdDuplCnt(TedPoOrdPackVO vo) {
		return ((Integer) getSqlMapClientTemplate().queryForObject("PEDMWEB0003.selectOrdDuplCnt", vo)).intValue();
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<TedPoOrdPackVO> selectPackStrInfo(TedPoOrdPackVO vo) {
		// TODO Auto-generated method stub
		return (List<TedPoOrdPackVO>)getSqlMapClientTemplate().queryForList("PEDMWEB0003.selectPackStrInfo", vo);
	}
	
	@SuppressWarnings({"deprecation", "unchecked" })
	public List<TedPoOrdPackVO> selectPackInfoList(TedPoOrdPackVO vo) {
		// TODO Auto-generated method stub
		return (List<TedPoOrdPackVO>)getSqlMapClientTemplate().queryForList("PEDMWEB0003.selectPackInfoList", vo);
	}
	
	/**
	 * 엑셀 업체발주요청 마스터 저장
	 * @param TedPoOrdMstVO
	 * @return void
	 * @throws SQLException
	 */
	public void insertExcelMstInfo(TedPoOrdMstVO vo) throws DataAccessException {
		insert("PEDMWEB0003.insertExcelMstInfo", vo);
	}
	
	/**
	 * 엑셀 발주요청 상품정보 저장
	 * @param List<TedPoOrdPackVO>
	 * @return void
	 * @throws SQLException
	 */
	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes"})
	public void insertExcelProdInfo(final List<TedPoOrdPackVO> ordList) throws Exception, SQLException {
		/*getSqlMapClientTemplate().execute(new SqlMapClientCallback(){
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
		    	executor.startBatch();
		    	for ( TedPoOrdPackVO  ord : ordList ) {
		    		executor.insert("PEDMWEB0003.insertExcelProdInfo", ord);
		    	}
		    	return new Integer(executor.executeBatch());
			}
		});*/
		
		for ( TedPoOrdPackVO  ord : ordList ) {
    		insert("PEDMWEB0003.insertExcelProdInfo", ord);
    	}
	}
	
	/**
	 * 엑셀 업체발주요청 마스터 상품 합계 저장
	 * @param TedPoOrdPackVO
	 * @return void
	 * @throws SQLException
	 */
	public void updateExcelOrdMstSum(TedPoOrdMstVO vo) throws SQLException {
		update("PEDMWEB0003.updateExcelOrdMstSum", vo);
	}
	
	/** 엑셀 발주요청 정보 삭제 : 저장 성공시*/
	public void deleteOrdPackInfo(TedPoOrdPackVO vo) throws SQLException{
		this.delete("PEDMWEB0003.deleteOrdPackInfo", vo);
	}

}

