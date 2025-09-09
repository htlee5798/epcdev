package com.lottemart.epc.edi.weborder.dao;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.lottemart.epc.edi.weborder.model.EdiRtnPackListVO;
import com.lottemart.epc.edi.weborder.model.EdiRtnPackVO;
import com.lottemart.epc.edi.weborder.model.EdiRtnProdListVO;
import com.lottemart.epc.edi.weborder.model.EdiRtnProdVO;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;


@Repository("nedmweb0130Dao")
public class NEDMWEB0130Dao extends AbstractDAO {

	
	// 반품 일괄 등록 카운트 조회
	@SuppressWarnings("deprecation")
	public int selectRtnPackListTotCnt(SearchWebOrder vo) {
		return ((Integer) getSqlMapClientTemplate().queryForObject("NEDMWEB0130.selectRtnPackListTotCnt", vo)).intValue();
	}
	
	// 반품 일괄 등록 목록 조회
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<EdiRtnPackVO> selectRtnPackList(SearchWebOrder vo) {
		// TODO Auto-generated method stub
		return (List<EdiRtnPackVO>)getSqlMapClientTemplate().queryForList("NEDMWEB0130.selectRtnPackList", vo);
	}
	
	// 반품 점포 수 및 총 개수 및 총 합계
	@SuppressWarnings({"deprecation" })
	public EdiRtnPackVO selectRtnPackCntSum(SearchWebOrder vo) {
		// TODO Auto-generated method stub
		return (EdiRtnPackVO)getSqlMapClientTemplate().queryForObject("NEDMWEB0130.selectRtnPackCntSum", vo);
	}
	
	/** 엑셀 반품요청 정보 삭제 */
	public void deleteExcelRtnInfo(EdiRtnPackVO vo) throws SQLException{
		this.delete("NEDMWEB0130.deleteExcelRtnInfo", vo);
	}
	
	//엑셀 반품요청 오류카운트 조회
	@SuppressWarnings("deprecation")
	public int selectRtnExcelErrorCnt(EdiRtnPackVO vo) {
		return ((Integer) getSqlMapClientTemplate().queryForObject("NEDMWEB0130.selectRtnExcelErrorCnt", vo)).intValue();
	}
	
	//엑셀 반품요청 중복 카운트 조회
	@SuppressWarnings("deprecation")
	public int selectRtnExcelDuplCnt(EdiRtnPackVO vo) {
		return ((Integer) getSqlMapClientTemplate().queryForObject("NEDMWEB0130.selectRtnExcelDuplCnt", vo)).intValue();
	}
	
	// 업체반품요청 상품정보에 기 등록된 데이터가 있는지 확인.
	@SuppressWarnings("deprecation")
	public int selectRtnDuplCnt(EdiRtnPackVO vo) {
		return ((Integer) getSqlMapClientTemplate().queryForObject("NEDMWEB0130.selectRtnDuplCnt", vo)).intValue();
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<EdiRtnPackVO> selectRtnPackStrInfo(EdiRtnPackVO vo) {
		// TODO Auto-generated method stub
		return (List<EdiRtnPackVO>)getSqlMapClientTemplate().queryForList("NEDMWEB0130.selectRtnPackStrInfo", vo);
	}
	
	@SuppressWarnings({"deprecation", "unchecked" })
	public List<EdiRtnPackVO> selectRtnPackInfoList(EdiRtnPackVO vo) {
		// TODO Auto-generated method stub
		return (List<EdiRtnPackVO>)getSqlMapClientTemplate().queryForList("NEDMWEB0130.selectRtnPackInfoList", vo);
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
		    		executor.insert("NEDMWEB0130.insertRtnExcelProdInfo", ord);
		    	}
		    	return new Integer(executor.executeBatch());
			}
		});
		
	}
	
	/**
	 * 엑셀 업체반품요청 마스터 저장
	 * @param TedPoRrlMstVO
	 * @return void
	 * @throws SQLException
	 */
	public void insertRtnExcelMstInfo(EdiRtnProdVO vo) throws DataAccessException {
		insert("NEDMWEB0130.insertRtnExcelMstInfo", vo);
	}
	
	/**
	 * 엑셀 업체반품요청 마스터 상품 합계 저장
	 * @param EdiRtnPackVO
	 * @return void
	 * @throws SQLException
	 */
	public void updateExcelRtnMstSum(EdiRtnProdVO vo) throws SQLException {
		update("NEDMWEB0130.updateExcelRtnMstSum", vo);
	}
	
	/** 엑셀 반품요청 정보 삭제 : 저장 성공시*/
	public void deleteRtnPackInfo(EdiRtnPackVO vo) throws SQLException{
		this.delete("NEDMWEB0130.deleteRtnPackInfo", vo);
	}
		
		
		/**
		 * 발주DATA Master 상태 update 
		 * @param String
		 * @return void
		 * @throws SQLException
		 */

		public int saveTedPoRrlMst(SearchWebOrder  vo) throws Exception, SQLException {
			return (Integer)getSqlMapClientTemplate().update("NEDMWEB0130.EDI_RETURN_PROD_UPDATE_02", vo);
		}
		
		/** 파일구분 코드 생성 */
		public String selectNewRtnPackDivnCd() {
			return (String)selectByPk("NEDMWEB0130.selectNewRtnPackDivnCd", null);
		}

		/** 파일 그룹 코드 생성 */
		public String selectNewRtnFileGrpCd(String venCd) {
			return (String)selectByPk("NEDMWEB0130.selectNewRtnFileGrpCd", venCd);
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
			    		executor.insert("NEDMWEB0130.insertRtnPackInfo", ord);
			        }
			    	return new Integer(executor.executeBatch());
				}
			});
			
		}
		
		/**
		 * 업체 반품 요청 : 상태코드 업데이트
		 * @param String
		 * @return void
		 * @throws SQLException
		 */
		public void updateRtnPackState(String packDivnCd) throws SQLException {
			update("NEDMWEB0130.updateRtnPackState", packDivnCd);
		}
		
		/**
		 * 업체 반품 요청일괄등록 수정
		 * @param EdiRtnPackListVO
		 * @return void
		 * @throws SQLException
		 */
		public void updateRtnPackInfo(EdiRtnPackVO vo) throws Exception, SQLException {
			update("NEDMWEB0130.updateRtnPackInfo", vo);
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
			    		executor.update("NEDMWEB0130.EDI_RETURN_PROD_UPDATE_01", rtnProd);
			    	}
			    	return new Integer(executor.executeBatch());
				}
			});
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
			return (List<EdiRtnProdVO>)getSqlMapClientTemplate().queryForList("NEDMWEB0130.EDI_RETURN_PROD_SELECT_02_SEND", vo);
		}
		
		
		/**
		 * RFC전송 요청할 정보 조회
		 * @param vo
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		public List<Map> selectRfcReqDataBatch(SearchWebOrder vo)  throws Exception{
			// TODO Auto-generated method stub
			return (List<Map> )getSqlMapClientTemplate().queryForList("NEDMWEB0130.selectRfcReqDataBatch", vo);
		}
}
