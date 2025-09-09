package com.lottemart.epc.edi.weborder.dao;

import java.sql.SQLException;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.lcnjf.util.StringUtil;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.model.TedOrdList;
import com.lottemart.epc.edi.weborder.model.TedOrdList010VO;
import com.lottemart.epc.edi.weborder.model.TedOrdSumList;
import com.lottemart.epc.edi.weborder.model.TedPoOrdMstVO;


@Repository("pedmweb0010Dao")
public class PEDMWEB0010Dao extends AbstractDAO {

	/**
     * 발주승인 대상 정보 TED_PO_ORD_MST 조회 Paging Count  
     * @Method selectVendOrdListTotCnt
     * @param SearchWebOrder
     * @return int 
     * @throws Exception
    */
	public int selectVendOrdListTotCnt(SearchWebOrder swo) throws Exception {
		return ((Integer) getSqlMapClientTemplate().queryForObject("PEDMWEB0010.ORD_PROD_SELECT_TOT_CNT_01", swo)).intValue();
	}
	
	/**
     * 발주승인 대상 정보 TED_PO_ORD_MST  List   
     * @Method selectVenOrdInfo
     * @param SearchWebOrder
     * @return List<TedOrdList> 
     * @throws Exception
    */
	public List<TedOrdList> selectVenOrdInfo(SearchWebOrder swo) throws Exception{
		return (List<TedOrdList>)getSqlMapClientTemplate().queryForList("PEDMWEB0010.ORD_PROD_SELECT_04",swo);
	}

	
	/**
	 * Desc : 점포별 발주 상세 펼침 조회
	 * @Method Name : selectStrCdList
	 * @param SearchWebOrder
	 * @return List<TedOrdList>
	 */
	public List<TedOrdList> selectStrCdList(SearchWebOrder swo) throws Exception {
		return	(List<TedOrdList>)getSqlMapClientTemplate().queryForList("PEDMWEB0010.ORD_PROD_SELECT_02", swo);
	}
	
	public TedOrdSumList selectVenOrdSumInfo(SearchWebOrder swo) throws Exception{
		return	 (TedOrdSumList)getSqlMapClientTemplate().queryForObject("PEDMWEB0010.ORD_PROD_SELECT_01", swo);
	}
	
	
	
	
	
	public List<TedOrdList> selectStrCdProdList(SearchWebOrder swo) throws Exception{
		return	(List<TedOrdList>)getSqlMapClientTemplate().queryForList("PEDMWEB0010.ORD_PROD_SELECT_03", swo);
	}
	
	
	
	
	public TedOrdSumList selectStrCdProdSumList(SearchWebOrder swo) throws Exception{
		
		return	(TedOrdSumList)getSqlMapClientTemplate().queryForObject("PEDMWEB0010.VEND-SELECT05", swo);
	}
	

	public void updateStrCd(final TedOrdList010VO strList, final String loginId) throws Exception,SQLException{
	
	//getSqlMapClientTemplate().update("PEDMWEB0010.VEND-UPDATE01", vo);
		getSqlMapClientTemplate().execute(new SqlMapClientCallback(){
			
		public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
	    	executor.startBatch();
	     	for ( TedOrdList  ord : strList ) {
	     		ord.setModId(loginId);
	    		executor.update("PEDMWEB0010.VEND-UPDATE01", ord);
	        }   	
	    	return new Integer(executor.executeBatch());
		}
		
	});
		
			
	}
	
	

	

/*	public void updateMstSum(final TedOrdList010VO strList) throws Exception, SQLException{
		//getSqlMapClientTemplate().update("PEDMWEB0010.VEND-UPDATE04", vo);
		
		getSqlMapClientTemplate().execute(new SqlMapClientCallback(){	
			
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
		    	executor.startBatch();
		  
		     	for ( TedOrdList  ord : strList ) {
		    			executor.update("PEDMWEB0010.VEND-UPDATE04", ord);
		        }   	
		    	return new Integer(executor.executeBatch());
			}
			
		});
			
	
		try {
			this.getSqlMapClient().startBatch();
			for ( TedOrdList  ord : strList ) {
				this.getSqlMapClient().update("PEDMWEB0010.VEND-UPDATE04", ord);
			}
		}catch (Exception e) {
			 throw e;
		}finally 
		{
			this.getSqlMapClient().endTransaction();
		}
				
	}
	
	*/
	
	
	/*
	public void updateMstProdSum(String value) throws Exception{
		getSqlMapClientTemplate().update("PEDMWEB0010.EID_ORD_PROD_UPDATE_04", value);
	}
	*/
	
	/*
	public void updateMstSum(final TedOrdList010VO strList, final String loginId) throws Exception,SQLException{
		
		//getSqlMapClientTemplate().update("PEDMWEB0010.VEND-UPDATE04", value);
		
		getSqlMapClientTemplate().execute(new SqlMapClientCallback(){	
			
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
		    	executor.startBatch();
		  
		     	for ( TedOrdList  ord : strList ) {
		     		ord.setModId(loginId);
		    		executor.update("PEDMWEB0010.EID_ORD_PROD_UPDATE_04", ord.getOrdReqNo());
		        }   	
		    	return new Integer(executor.executeBatch());
			}
			
		});
			
		
		
	}
	*/
	
	

	/**
	 *  당일 점포,상품별 발주정보 변경
	 * @param List<TedOrdList010VO>
	 * @param String
	 * @return void
	 * @throws SQLException
	 */
	public int updateStrCdProd(final TedOrdList010VO strList, final String loginId) throws Exception,SQLException{
		

		Integer batchCount = (Integer)getSqlMapClientTemplate().execute(new SqlMapClientCallback() {				
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
		    	executor.startBatch();
		    	String ordordRrqNo = "";
		     	for ( TedOrdList  ord : strList ) {
		     			ord.setModId(loginId);
		     			if(StringUtil.isEmpty(ordordRrqNo)) ordordRrqNo = ord.getOrdReqNo();
		     			if(ord.getSaveFg()=="D"  ||ord.getSaveFg().equals("D") ){
		     				executor.update("PEDMWEB0010.EID_ORD_PROD_UPDATE_02", ord);
		     			}else{
		     				executor.update("PEDMWEB0010.EID_ORD_PROD_UPDATE_03", ord);
		     			}		
		        }
		    	return new Integer(executor.executeBatch());
			}
		});
		return batchCount.intValue();
	}
		

	public void updateMstProdSum(TedOrdList vo ) throws Exception{
		getSqlMapClientTemplate().update("PEDMWEB0010.EID_ORD_PROD_UPDATE_04",vo);
	}
	
	
	
	/**
	 *  당일 반품등록 내역 조회
	 * @param SearchWebOrder
	 * @return List<EdiRtnProdVO>
	 * @throws SQLException
	 */
	public List<TedOrdList> selectStrCdProdConfList(SearchWebOrder vo)  throws Exception{
		// TODO Auto-generated method stub
		return (List<TedOrdList>)getSqlMapClientTemplate().queryForList("PEDMWEB0010.VEND-SELECT06", vo);
	}
	
	
	/**
	 * 발주 MD 전송  PROCEDURES 호출
	 * @param EdiRtnProdVO
	 * @return EdiRtnProdVO
	 * @throws SQLException
	 */
	public TedOrdList saveCallSendProd(TedOrdList param )  throws Exception, SQLException {
		this.getSqlMapClient().queryForObject("PEDMWEB0010.EID_PROCEDURE_01",param);
		return param;
	}
	
	
	/**
	 * 발주DATA 상태 Detail update 
	 * @param TedOrdList010VO
	 * @return void
	 * @throws SQLException
	 */
	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes"})
	public void saveTedPoOrdProdBatch(final TedOrdList010VO rtnList) throws Exception, SQLException {
		getSqlMapClientTemplate().execute(new SqlMapClientCallback(){
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
		    	executor.startBatch();
		    	for ( TedOrdList  rtnProd  : rtnList ) {
		    		executor.update("PEDMWEB0010.EDI_ORD_PROD_UPDATE_01", rtnProd);
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

	public int saveTedPoOrdMst(SearchWebOrder  vo) throws Exception, SQLException {
	
		return (Integer)getSqlMapClientTemplate().update("PEDMWEB0010.EDI_ORD_MST_UPDATE_02", vo);
	}
	
	/**
	 * 발주승인 목록 PROD 삭제처리 ( 점포전체 - update  )
	 * @Method Name : updateStrCd
	 * @param TedOrdProcess010VO, HttpServletRequest
	 * @return Map<String,Object>
	 */
	public int updateStrCdProdData(TedPoOrdMstVO  vo) throws Exception, SQLException {
		return (Integer)getSqlMapClientTemplate().update("PEDMWEB0010.EDI_ORD_PROD_UPDATE_10", vo);
	}
	
	/**
	 * 발주승인 목록 PROD 삭제처리 ( 점포,상품별 - update  )
	 * @Method Name : updateStrCd
	 * @param TedOrdProcess010VO, HttpServletRequest
	 * @return Map<String,Object>
	 */
	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes"})
	public void updateStrCdProdDataBatch(final TedOrdList010VO rtnList,final String loginId) throws Exception, SQLException {
		
		getSqlMapClientTemplate().execute(new SqlMapClientCallback(){
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
		    	executor.startBatch();
		    	
		    	for ( TedOrdList  rtnProd  : rtnList ) {
		    		rtnProd.setModId(loginId); 
		    		executor.update("PEDMWEB0010.EDI_ORD_PROD_UPDATE_10_01", rtnProd);
		    	}
		    	
		    	return new Integer(executor.executeBatch());
			}
		});
	}
	
	
	/**
	 * 발주승인 마스터 MST SUM 재 계산( update  )
	 * @Method Name : updateMstSumData
	 * @param TedPoOrdMstVO
	 * @return int
	 */
	public int updateMstSumData(TedPoOrdMstVO  vo) throws Exception, SQLException {
		return (Integer)getSqlMapClientTemplate().update("PEDMWEB0010.EDI_ORD_PROD_UPDATE_11", vo);
	}
	
	
	
	
}
