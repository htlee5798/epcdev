package com.lottemart.epc.edi.weborder.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.lottemart.epc.edi.weborder.model.NEDMWEB0030VO;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.model.TedOrdList;
import com.lottemart.epc.edi.weborder.model.TedPoOrdMstVO;
import com.lottemart.epc.edi.weborder.model.TedPoOrdPackList001VO;
import com.lottemart.epc.edi.weborder.model.TedPoOrdPackVO;

/**
 * @Class Name : NEDMWEB0030Dao
 * @Description : 발주일괄등록 Dao Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      		수정자          		수정내용
 *  -------    --------    ---------------------------
 * 2015.12.08	O YEUN KWON	  최초생성
 * 
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */

@Repository("nedmweb0003Dao")
public class NEDMWEB0030Dao extends AbstractDAO {
	
	// 발주 일괄 등록 카운트 조회
	@SuppressWarnings("deprecation")
	public int selectOrdPackListTotCnt(NEDMWEB0030VO vo) {
		return ((Integer) getSqlMapClientTemplate().queryForObject("NEDMWEB0030.selectOrdPackListTotCnt", vo)).intValue();
	}
	
	// 발주 일괄 등록 목록 조회
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<NEDMWEB0030VO> selectOrdPackList(NEDMWEB0030VO vo) {
		// TODO Auto-generated method stub
		return (List<NEDMWEB0030VO>)getSqlMapClientTemplate().queryForList("NEDMWEB0030.selectOrdPackList", vo);
	}
	
	// 점포 수 및 총 개수 및 총 합계
	@SuppressWarnings({"deprecation" })
	public NEDMWEB0030VO selectOrdPackCntSum(NEDMWEB0030VO vo) {
		// TODO Auto-generated method stub
		return (NEDMWEB0030VO)getSqlMapClientTemplate().queryForObject("NEDMWEB0030.selectOrdPackCntSum", vo);
	}
	
	/** 파일 그룹 코드 생성 */
	public String selectNewFileGrpCd(String venCd) {
		return (String)selectByPk("NEDMWEB0030.selectNewFileGrpCd", venCd);
	}
	
	/** 파일구분 코드 생성 */
	public String selectNewPackDivnCd() {
		return (String)selectByPk("NEDMWEB0030.selectNewPackDivnCd", null);
	}
	
	/**
	 * 업체발주요청일괄등록 저장
	 * @param List<TED_PO_ORD_PACK>
	 * @return void
	 * @throws SQLException
	 */
	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes"})
	public void insertOrdPackInfo(final ArrayList<NEDMWEB0030VO> packList) throws Exception, SQLException {
		getSqlMapClientTemplate().execute(new SqlMapClientCallback(){
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
		    	executor.startBatch();
		    	
		    	for ( NEDMWEB0030VO  ord : packList ) {
		    		executor.insert("NEDMWEB0030.insertOrdPackInfo", ord);
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
	public void updateOrdPackInfo(NEDMWEB0030VO vo) throws Exception, SQLException {
		update("NEDMWEB0030.updateOrdPackInfo", vo);
	}
	
	/**
	 * 업체발주요청 테이블 : 상태코드 업데이트 상품코드 오류
	 * @param String
	 * @return void
	 * @throws SQLException
	 */
	public void updateOrdPackProdState(String packDivnCd) throws SQLException {
		update("NEDMWEB0030.updateOrdPackProdState", packDivnCd);
	}
	
	/**
	 * 업체발주요청 테이블 : 상태코드 업데이트, 점포 코드 오류
	 * @param String
	 * @return void
	 * @throws SQLException
	 */
	public void updateOrdPackStoreState(String packDivnCd) throws SQLException {
		update("NEDMWEB0030.updateOrdPackStoreState", packDivnCd);
	}
	
	/** 엑셀 발주요청 정보 삭제 */
	public void deleteExcelOrdInfo(NEDMWEB0030VO vo) throws SQLException{
		this.delete("NEDMWEB0030.deleteExcelOrdInfo", vo);
	}

	//엑셀 발주요청 오류카운트 조회
	@SuppressWarnings("deprecation")
	public int selectExcelErrorCnt(NEDMWEB0030VO vo) {
		return ((Integer) getSqlMapClientTemplate().queryForObject("NEDMWEB0030.selectExcelErrorCnt", vo)).intValue();
	}
	
	//엑셀 발주요청 중복 카운트 조회
	@SuppressWarnings("deprecation")
	public int selectExcelDuplCnt(NEDMWEB0030VO vo) {
		return ((Integer) getSqlMapClientTemplate().queryForObject("NEDMWEB0030.selectExcelDuplCnt", vo)).intValue();
	}
	
	// 업체발주요청 상품정보에 기 등록된 데이터가 있는지 확인.
	@SuppressWarnings("deprecation")
	public int selectOrdDuplCnt(NEDMWEB0030VO vo) {
		return ((Integer) getSqlMapClientTemplate().queryForObject("NEDMWEB0030.selectOrdDuplCnt", vo)).intValue();
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<NEDMWEB0030VO> selectPackStrInfo(NEDMWEB0030VO vo) {
		return (List<NEDMWEB0030VO>)getSqlMapClientTemplate().queryForList("NEDMWEB0030.selectPackStrInfo", vo);
	}
	
	@SuppressWarnings({"deprecation", "unchecked" })
	public List<TedPoOrdPackVO> selectPackInfoList(TedPoOrdPackVO vo) {
		// TODO Auto-generated method stub
		return (List<TedPoOrdPackVO>)getSqlMapClientTemplate().queryForList("NEDMWEB0030.selectPackInfoList", vo);
	}
	
	/**
	 * 엑셀 업체발주요청 마스터 저장
	 * @param TedPoOrdMstVO
	 * @return void
	 * @throws SQLException
	 */
	public void insertExcelMstInfo(NEDMWEB0030VO vo) throws DataAccessException {
		insert("NEDMWEB0030.insertExcelMstInfo", vo);
	}
	
	/**
	 * 엑셀 발주요청 상품정보 저장
	 * @param List<TedPoOrdPackVO>
	 * @return void
	 * @throws SQLException
	 */
	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes"})
	public void insertExcelProdInfo(final List<NEDMWEB0030VO> ordList) throws Exception, SQLException {
		/*getSqlMapClientTemplate().execute(new SqlMapClientCallback(){
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
		    	executor.startBatch();
		    	for ( TedPoOrdPackVO  ord : ordList ) {
		    		executor.insert("NEDMWEB0030.insertExcelProdInfo", ord);
		    	}
		    	return new Integer(executor.executeBatch());
			}
		});*/
		
		for ( NEDMWEB0030VO  ord : ordList ) {
    		insert("NEDMWEB0030.insertExcelProdInfo", ord);
    	}
	}
	
	/**
	 * 엑셀 업체발주요청 마스터 상품 합계 저장
	 * @param TedPoOrdPackVO
	 * @return void
	 * @throws SQLException
	 */
	public void updateExcelOrdMstSum(NEDMWEB0030VO vo) throws SQLException {
		update("NEDMWEB0030.updateExcelOrdMstSum", vo);
	}
	
	/** 엑셀 발주요청 정보 삭제 : 저장 성공시*/
	public void deleteOrdPackInfo(NEDMWEB0030VO vo) throws SQLException{
		this.delete("NEDMWEB0030.deleteOrdPackInfo", vo);
	}

}

