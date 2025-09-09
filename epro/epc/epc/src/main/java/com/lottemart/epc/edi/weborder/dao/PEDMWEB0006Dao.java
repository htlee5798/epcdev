package com.lottemart.epc.edi.weborder.dao;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.lottemart.epc.edi.weborder.model.EdiRtnPackListVO;
import com.lottemart.epc.edi.weborder.model.EdiRtnPackVO;
import com.lottemart.epc.edi.weborder.model.EdiRtnProdListVO;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.model.EdiRtnProdVO;
import com.lottemart.epc.edi.weborder.model.TedPoOrdMstVO;
import com.lottemart.epc.edi.weborder.model.TedPoOrdPackVO;


@Repository("pedmweb0006Dao")
public class PEDMWEB0006Dao extends AbstractDAO {

	


	
	
	/**
	 * 점포별,업체코드별 반품 등록 가능 상품 여부 확인  
	 * @param EdiRtnProdVO
	 * @return Int
	 * @throws SQLException
	 */
	public int selectRtnProdCnt(EdiRtnProdVO vo) {
		return ((Integer) getSqlMapClientTemplate().queryForObject("PEDMWEB0006.EDI_RETURN_PROD_DATA_02_01", vo)).intValue();
	}
	
	/**
	 * 반품상품 등록여부 확인  
	 * @param EdiRtnProdVO
	 * @return Int
	 * @throws SQLException
	 */
	public int selectRtnProdDuplicateCnt(EdiRtnProdVO vo) {
		return ((Integer) getSqlMapClientTemplate().queryForObject("PEDMWEB0006.EDI_RETURN_PROD_DATA_02_02", vo)).intValue();
	}
	
	/**
	 * 1. 반품상품 점별 마스터 생성  
	 * @param EdiRtnProdVO
	 * @return void
	 * @throws SQLException
	 */
	public int saveRtnProdMst(EdiRtnProdVO vo) throws Exception{
		return getSqlMapClientTemplate().update("PEDMWEB0006.EDI_RETURN_PROD_DATA_03", vo);
	}
	
	/**
	 * 2. 반품상품 점별 목록 생성
	 * @param EdiRtnProdVO
	 * @return void
	 * @throws SQLException
	 */
	public int saveRtnProdList(EdiRtnProdVO vo) throws Exception{
		return getSqlMapClientTemplate().update("PEDMWEB0006.EDI_RETURN_PROD_DATA_04", vo);
	}
	
	
	/**
	 * 3. 반품상품 마스터  합계 UPDATE
	 * @param EdiRtnProdVO
	 * @return void
	 * @throws SQLException
	 */
	public int saveRtnrodSum(EdiRtnProdVO vo) throws Exception{
		return (Integer)getSqlMapClientTemplate().update("PEDMWEB0006.EDI_RETURN_PROD_DATA_05", vo);
	}
	
	
	
	/**
	 *  당일 반품등록 내역 합계
	 * @param SearchWebOrder
	 * @return EdiRtnProdVO
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public EdiRtnProdVO selectDayRtnProdSum(SearchWebOrder vo) {
		// TODO Auto-generated method stub
		return (EdiRtnProdVO)getSqlMapClientTemplate().queryForObject("PEDMWEB0005.EDI_RETURN_PROD_SELECT_01_02", vo);
	}
	
	/**
	 *  당일 반품등록 내역 조회
	 * @param SearchWebOrder
	 * @return List<EdiRtnProdVO>
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<EdiRtnProdVO> selectDayRtnProdList(SearchWebOrder vo)  throws Exception{
		// TODO Auto-generated method stub
		return (List<EdiRtnProdVO>)getSqlMapClientTemplate().queryForList("PEDMWEB0005.EDI_RETURN_PROD_SELECT_02", vo);
	}
	
	/**
	 *  당일 반품등록 내역 조회
	 * @param SearchWebOrder
	 * @return List<EdiRtnProdVO>
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<EdiRtnProdVO> selectDayRtnProdListSend(SearchWebOrder vo)  throws Exception{
		// TODO Auto-generated method stub
		return (List<EdiRtnProdVO>)getSqlMapClientTemplate().queryForList("PEDMWEB0006.EDI_RETURN_PROD_SELECT_02_SEND", vo);
	}
	
	
	/**
	 *  당일 반품등록 내역 조회(합계포함 query)
	 * @param SearchWebOrder
	 * @return List<EdiRtnProdVO>
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<EdiRtnProdVO> selectDayRtnProdSumList(SearchWebOrder vo)  throws Exception{
		// TODO Auto-generated method stub
		return (List<EdiRtnProdVO>)getSqlMapClientTemplate().queryForList("PEDMWEB0006.EDI_RETURN_PROD_SELECT_03", vo);
	}
	
	
	
	/**
	 * 반품상품 마스터 삭제
	 * @param HashMap<String, String[]>
	 * @return int
	 * @throws SQLException
	 */
	public void deleteRtnrodList(EdiRtnProdVO param ) throws Exception{
		getSqlMapClientTemplate().delete("PEDMWEB0006.EDI_RETURN_PROD_DELETE_02", param);
	}
	
	
	
	/**
	 * 반품DATA 삭제 
	 * @param EdiRtnProdListVO
	 * @return void
	 * @throws SQLException
	 */
	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes"})
	public void deleteRtnrodListBatch(final EdiRtnProdListVO rtnDeleteList) throws Exception, SQLException {
		getSqlMapClientTemplate().execute(new SqlMapClientCallback(){
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
		    	executor.startBatch();
		    	for ( EdiRtnProdVO  rtnProd  : rtnDeleteList ) {
		    		executor.delete("PEDMWEB0006.EDI_RETURN_PROD_DELETE_01", rtnProd);
		    	}
		    	return new Integer(executor.executeBatch());
			}
		});
	}
	
	/**
	 * 반품 MD 전송  PROCEDURES 호출
	 * @param EdiRtnProdVO
	 * @return EdiRtnProdVO
	 * @throws SQLException
	 */
	public EdiRtnProdVO saveCallSendReturnProd(EdiRtnProdVO param )  throws Exception, SQLException {
		this.getSqlMapClient().queryForObject("PEDMWEB0006.EID_RETURN_PROCEDURE_01",param);
		return param;
	}
	
	public HashMap<String, Object> saveCallSendReturnProdHash(HashMap param )  throws Exception, SQLException {
		/*
		HashMap<String, Object> hmap = new HashMap();
		
		hmap.put("I_RTN_DY",	param.getRrlDy());		// 반품일자
		hmap.put("I_STR_CD",	param.getStrCd());		// 점포코드
		hmap.put("I_PROD_CD",	param.getProdCd());		// 상품코드
		hmap.put("I_VEN_CD",	param.getVenCd());		// 업체코드
		hmap.put("I_RTN_QTY",	param.getRrlQtyNum());	// 반품수량(ea)
		
		hmap.put("O_RTN","");
		hmap.put("O_PROC_CMT","");
		*/
		return (HashMap)getSqlMapClientTemplate().queryForObject("PEDMWEB0006.EID_RETURN_PROCEDURE_01",param);
	}
	
	
	/**
	 * 반품DATA 상태 update 
	 * @param EdiRtnProdListVO
	 * @return void
	 * @throws SQLException
	 */
	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes"})
	public void saveTedPoRrlProdBatch(final EdiRtnProdListVO rtnDeleteList) throws Exception, SQLException {
		getSqlMapClientTemplate().execute(new SqlMapClientCallback(){
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
		    	executor.startBatch();
		    	for ( EdiRtnProdVO  rtnProd  : rtnDeleteList ) {
		    		executor.update("PEDMWEB0006.EDI_RETURN_PROD_UPDATE_01", rtnProd);
		    	}
		    	return new Integer(executor.executeBatch());
			}
		});
	}
	
	
	
	
	
	

	/**
	 * 발주DATA Master 상태 update 
	 * @param String
	 * @return void
	 * @throws SQLException
	 */

	public int saveTedPoRrlMst(SearchWebOrder  vo) throws Exception, SQLException {
		return (Integer)getSqlMapClientTemplate().update("PEDMWEB0006.EDI_RETURN_PROD_UPDATE_02", vo);
	}
	
	
	
	

	
	/**
	 *  당일 반품등록 내역 조회
	 * @param SearchWebOrder
	 * @return List<EdiRtnProdVO>
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<EdiRtnProdVO> selectDayRtnList(SearchWebOrder vo)  throws Exception{
		// TODO Auto-generated method stub
		return (List<EdiRtnProdVO>)getSqlMapClientTemplate().queryForList("PEDMWEB0006.EDI_RETURN_SELECT_01", vo);
	}
	
	
	
	/**
	 *  당일 반품등록 내역 합계
	 * @param SearchWebOrder
	 * @return EdiRtnProdVO
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public EdiRtnProdVO selectDayRtnSum(SearchWebOrder vo) {
		// TODO Auto-generated method stub
		return (EdiRtnProdVO)getSqlMapClientTemplate().queryForObject("PEDMWEB0006.EDI_RETURN_SELECT_02", vo);
	}
	
	
	/**
	 *  당일 반품등록 내역 카운드
	 * @param SearchWebOrder
	 * @return int
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public int selectDayRtnListTotCnt(SearchWebOrder vo) {
		return ((Integer) getSqlMapClientTemplate().queryForObject("PEDMWEB0006.EDI_RETURN_SELECT_03", vo)).intValue();
	}
	
	
	
	
	/* ================================================================================================================ */
	
	/** 반품 등록번호 생성 */
	public String selectNewRtnReqNo() {
		return (String)selectByPk("PEDMWEB0006.selectNewRtnReqNo", null);
	}
	
	/** 반품 등록번호  조회 */
	public String selectRtnReqNo(EdiRtnProdVO vo) {
		return (String)selectByPk("PEDMWEB0006.selectRtnReqNo", vo);
	}
	
	// 반품 일괄 등록 카운트 조회
	@SuppressWarnings("deprecation")
	public int selectRtnPackListTotCnt(SearchWebOrder vo) {
		return ((Integer) getSqlMapClientTemplate().queryForObject("PEDMWEB0006.selectRtnPackListTotCnt", vo)).intValue();
	}
	
	// 반품 일괄 등록 목록 조회
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<EdiRtnPackVO> selectRtnPackList(SearchWebOrder vo) {
		// TODO Auto-generated method stub
		return (List<EdiRtnPackVO>)getSqlMapClientTemplate().queryForList("PEDMWEB0006.selectRtnPackList", vo);
	}
	
	// 반품 점포 수 및 총 개수 및 총 합계
	@SuppressWarnings({"deprecation" })
	public EdiRtnPackVO selectRtnPackCntSum(SearchWebOrder vo) {
		// TODO Auto-generated method stub
		return (EdiRtnPackVO)getSqlMapClientTemplate().queryForObject("PEDMWEB0006.selectRtnPackCntSum", vo);
	}
	
	/** 파일구분 코드 생성 */
	public String selectNewRtnPackDivnCd() {
		return (String)selectByPk("PEDMWEB0006.selectNewRtnPackDivnCd", null);
	}

	/** 파일 그룹 코드 생성 */
	public String selectNewRtnFileGrpCd(String venCd) {
		return (String)selectByPk("PEDMWEB0006.selectNewRtnFileGrpCd", venCd);
	}
	
	/**
	 * 업체 반품 요청일괄등록 저장
	 * @param EdiRtnPackListVO
	 * @return void
	 * @throws SQLException
	 */
	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes"})
	public void insertRtnPackInfo(final EdiRtnPackListVO packList) throws Exception, SQLException {
		getSqlMapClientTemplate().execute(new SqlMapClientCallback(){
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
		    	executor.startBatch();
		    	
		    	for ( EdiRtnPackVO  ord : packList ) {
		    		executor.insert("PEDMWEB0006.insertRtnPackInfo", ord);
		        }
		    	return new Integer(executor.executeBatch());
			}
		});
		
	}
	
	/**
	 * 업체 반품 요청일괄등록 수정
	 * @param EdiRtnPackListVO
	 * @return void
	 * @throws SQLException
	 */
	public void updateRtnPackInfo(EdiRtnPackVO vo) throws Exception, SQLException {
		update("PEDMWEB0006.updateRtnPackInfo", vo);
	}
	
	/**
	 * 업체 반품 요청 : 상태코드 업데이트
	 * @param String
	 * @return void
	 * @throws SQLException
	 */
	public void updateRtnPackState(String packDivnCd) throws SQLException {
		update("PEDMWEB0006.updateRtnPackState", packDivnCd);
	}
	
	/** 엑셀 반품요청 정보 삭제 */
	public void deleteExcelRtnInfo(EdiRtnPackVO vo) throws SQLException{
		this.delete("PEDMWEB0006.deleteExcelRtnInfo", vo);
	}
	
	//엑셀 반품요청 오류카운트 조회
	@SuppressWarnings("deprecation")
	public int selectRtnExcelErrorCnt(EdiRtnPackVO vo) {
		return ((Integer) getSqlMapClientTemplate().queryForObject("PEDMWEB0006.selectRtnExcelErrorCnt", vo)).intValue();
	}
	
	//엑셀 반품요청 중복 카운트 조회
	@SuppressWarnings("deprecation")
	public int selectRtnExcelDuplCnt(EdiRtnPackVO vo) {
		return ((Integer) getSqlMapClientTemplate().queryForObject("PEDMWEB0006.selectRtnExcelDuplCnt", vo)).intValue();
	}
	
	// 업체반품요청 상품정보에 기 등록된 데이터가 있는지 확인.
	@SuppressWarnings("deprecation")
	public int selectRtnDuplCnt(EdiRtnPackVO vo) {
		return ((Integer) getSqlMapClientTemplate().queryForObject("PEDMWEB0006.selectRtnDuplCnt", vo)).intValue();
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<EdiRtnPackVO> selectRtnPackStrInfo(EdiRtnPackVO vo) {
		// TODO Auto-generated method stub
		return (List<EdiRtnPackVO>)getSqlMapClientTemplate().queryForList("PEDMWEB0006.selectRtnPackStrInfo", vo);
	}
	
	@SuppressWarnings({"deprecation", "unchecked" })
	public List<EdiRtnPackVO> selectRtnPackInfoList(EdiRtnPackVO vo) {
		// TODO Auto-generated method stub
		return (List<EdiRtnPackVO>)getSqlMapClientTemplate().queryForList("PEDMWEB0006.selectRtnPackInfoList", vo);
	}
	
	/**
	 * 엑셀 업체반품요청 마스터 저장
	 * @param TedPoRrlMstVO
	 * @return void
	 * @throws SQLException
	 */
	public void insertRtnExcelMstInfo(EdiRtnProdVO vo) throws DataAccessException {
		insert("PEDMWEB0006.insertRtnExcelMstInfo", vo);
	}
	
	/**
	 * 엑셀 반품요청 상품정보 저장
	 * @param List<EdiRtnPackVO>
	 * @return void
	 * @throws SQLException
	 */
	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes"})
	public void insertRtnExcelProdInfo(final List<EdiRtnPackVO> ordList) throws Exception, SQLException {
		getSqlMapClientTemplate().execute(new SqlMapClientCallback(){
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
		    	executor.startBatch();
		    	for ( EdiRtnPackVO  ord : ordList ) {
		    		executor.insert("PEDMWEB0006.insertRtnExcelProdInfo", ord);
		    	}
		    	return new Integer(executor.executeBatch());
			}
		});
		
	}
	
	/**
	 * 엑셀 업체반품요청 마스터 상품 합계 저장
	 * @param EdiRtnPackVO
	 * @return void
	 * @throws SQLException
	 */
	public void updateExcelRtnMstSum(EdiRtnProdVO vo) throws SQLException {
		update("PEDMWEB0006.updateExcelRtnMstSum", vo);
	}
	
	/** 엑셀 반품요청 정보 삭제 : 저장 성공시*/
	public void deleteRtnPackInfo(EdiRtnPackVO vo) throws SQLException{
		this.delete("PEDMWEB0006.deleteRtnPackInfo", vo);
	}
	

}
