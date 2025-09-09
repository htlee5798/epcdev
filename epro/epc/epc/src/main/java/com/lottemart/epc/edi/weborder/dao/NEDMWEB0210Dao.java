package com.lottemart.epc.edi.weborder.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.lcnjf.util.StringUtil;
import com.lottemart.epc.edi.weborder.model.NEDMWEB0210VO;
import com.lottemart.epc.edi.weborder.model.TedOrdList;
import com.lottemart.epc.edi.weborder.model.TedOrdList010VO;

/**
 * @Class Name : NEDMWEB0210Dao
 * @Description : 발주승인관리 Dao Class
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

@Repository("nedmweb0210Dao")
public class NEDMWEB0210Dao extends AbstractDAO {

	/**
     * 발주승인 대상 정보 TED_PO_ORD_MST 조회 Paging Count  
     * @Method selectVendOrdListTotCnt
     * @param SearchWebOrder
     * @return int 
     * @throws Exception
    */
	public int selectVendOrdListTotCnt(NEDMWEB0210VO vo) throws Exception {
		return ((Integer) getSqlMapClientTemplate().queryForObject("NEDMWEB0210.ORD_PROD_SELECT_TOT_CNT_01", vo)).intValue();
	}
	
	/**
     * 발주승인 대상 정보 TED_PO_ORD_MST  List   
     * @Method selectVenOrdInfo
     * @param SearchWebOrder
     * @return List<TedOrdList> 
     * @throws Exception
    */
	public List<NEDMWEB0210VO> selectVenOrdInfo(NEDMWEB0210VO vo) throws Exception{
		return (List<NEDMWEB0210VO>)getSqlMapClientTemplate().queryForList("NEDMWEB0210.ORD_PROD_SELECT_04",vo);
	}

	
	/**
	 * Desc : 점포별 발주 상세 펼침 조회
	 * @Method Name : selectStrCdList
	 * @param SearchWebOrder
	 * @return List<TedOrdList>
	 */
	public List<NEDMWEB0210VO> selectStrCdList(NEDMWEB0210VO vo) throws Exception {
		return	(List<NEDMWEB0210VO>)getSqlMapClientTemplate().queryForList("NEDMWEB0210.ORD_PROD_SELECT_02", vo);
	}
	
	public NEDMWEB0210VO selectVenOrdSumInfo(NEDMWEB0210VO vo) throws Exception{
		return	 (NEDMWEB0210VO)getSqlMapClientTemplate().queryForObject("NEDMWEB0210.ORD_PROD_SELECT_01", vo);
	}
	
	
	
	
	
	public List<NEDMWEB0210VO> selectStrCdProdList(NEDMWEB0210VO vo) throws Exception{
		return	(List<NEDMWEB0210VO>)getSqlMapClientTemplate().queryForList("NEDMWEB0210.ORD_PROD_SELECT_03", vo);
	}
	
	
	
	
	public NEDMWEB0210VO selectStrCdProdSumList(NEDMWEB0210VO vo) throws Exception{
		
		return	(NEDMWEB0210VO)getSqlMapClientTemplate().queryForObject("NEDMWEB0210.VEND-SELECT05", vo);
	}
	

	public void updateStrCd(final TedOrdList010VO strList, final String loginId) throws Exception,SQLException{
	
	//getSqlMapClientTemplate().update("NEDMWEB0210.VEND-UPDATE01", vo);
		getSqlMapClientTemplate().execute(new SqlMapClientCallback(){
			
		public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
	    	executor.startBatch();
	     	for ( TedOrdList  ord : strList ) {
	     		ord.setModId(loginId);
	    		executor.update("NEDMWEB0210.VEND-UPDATE01", ord);
	        }   	
	    	return new Integer(executor.executeBatch());
		}
		
	});
		
			
	}
	
	

	

/*	public void updateMstSum(final TedOrdList010VO strList) throws Exception, SQLException{
		//getSqlMapClientTemplate().update("NEDMWEB0210.VEND-UPDATE04", vo);
		
		getSqlMapClientTemplate().execute(new SqlMapClientCallback(){	
			
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
		    	executor.startBatch();
		  
		     	for ( TedOrdList  ord : strList ) {
		    			executor.update("NEDMWEB0210.VEND-UPDATE04", ord);
		        }   	
		    	return new Integer(executor.executeBatch());
			}
			
		});
			
	
		try {
			this.getSqlMapClient().startBatch();
			for ( TedOrdList  ord : strList ) {
				this.getSqlMapClient().update("NEDMWEB0210.VEND-UPDATE04", ord);
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
		getSqlMapClientTemplate().update("NEDMWEB0210.EID_ORD_PROD_UPDATE_04", value);
	}
	*/
	
	/*
	public void updateMstSum(final TedOrdList010VO strList, final String loginId) throws Exception,SQLException{
		
		//getSqlMapClientTemplate().update("NEDMWEB0210.VEND-UPDATE04", value);
		
		getSqlMapClientTemplate().execute(new SqlMapClientCallback(){	
			
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
		    	executor.startBatch();
		  
		     	for ( TedOrdList  ord : strList ) {
		     		ord.setModId(loginId);
		    		executor.update("NEDMWEB0210.EID_ORD_PROD_UPDATE_04", ord.getOrdReqNo());
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
	public int updateStrCdProd(final ArrayList<NEDMWEB0210VO> strList, final String loginId) throws Exception,SQLException{
		

		Integer batchCount = (Integer)getSqlMapClientTemplate().execute(new SqlMapClientCallback() {				
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
		    	executor.startBatch();
		    	String ordordRrqNo = "";
		     	for ( NEDMWEB0210VO  ord : strList ) {
		     			ord.setModId(loginId);
		     			if(StringUtil.isEmpty(ordordRrqNo)) ordordRrqNo = ord.getOrdReqNo();
		     			if(ord.getSaveFg()=="D"  ||ord.getSaveFg().equals("D") ){
		     				executor.update("NEDMWEB0210.EID_ORD_PROD_UPDATE_02", ord);
		     			}else{
		     				executor.update("NEDMWEB0210.EID_ORD_PROD_UPDATE_03", ord);
		     			}		
		        }
		    	return new Integer(executor.executeBatch());
			}
		});
		return batchCount.intValue();
	}
		

	public void updateMstProdSum(NEDMWEB0210VO vo ) throws Exception{
		getSqlMapClientTemplate().update("NEDMWEB0210.EID_ORD_PROD_UPDATE_04",vo);
	}
	
	
	
	/**
	 *  당일 반품등록 내역 조회
	 * @param SearchWebOrder
	 * @return List<EdiRtnProdVO>
	 * @throws SQLException
	 */
	public List<NEDMWEB0210VO> selectStrCdProdConfList(NEDMWEB0210VO vo)  throws Exception{
		return (List<NEDMWEB0210VO>)getSqlMapClientTemplate().queryForList("NEDMWEB0210.VEND-SELECT06", vo);
	}	
	
	
	/**
	 * 발주DATA 상태 Detail update 
	 * @param TedOrdList010VO
	 * @return void
	 * @throws SQLException
	 */
	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes"})
	public void saveTedPoOrdProdBatch(final ArrayList<NEDMWEB0210VO> rtnList) throws Exception, SQLException {
		getSqlMapClientTemplate().execute(new SqlMapClientCallback(){
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
		    	executor.startBatch();
		    	for ( NEDMWEB0210VO  rtnProd  : rtnList ) {
		    		executor.update("NEDMWEB0210.EDI_ORD_PROD_UPDATE_01", rtnProd);
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

	public int saveTedPoOrdMst(NEDMWEB0210VO  vo) throws Exception, SQLException {
	
		return (Integer)getSqlMapClientTemplate().update("NEDMWEB0210.EDI_ORD_MST_UPDATE_02", vo);
	}
	
	/**
	 * 발주승인 목록 PROD 삭제처리 ( 점포전체 - update  )
	 * @Method Name : updateStrCd
	 * @param TedOrdProcess010VO, HttpServletRequest
	 * @return Map<String,Object>
	 */
	public int updateStrCdProdData(NEDMWEB0210VO  vo) throws Exception, SQLException {
		return (Integer)getSqlMapClientTemplate().update("NEDMWEB0210.EDI_ORD_PROD_UPDATE_10", vo);
	}
	
	/**
	 * 발주승인 목록 PROD 삭제처리 ( 점포,상품별 - update  )
	 * @Method Name : updateStrCd
	 * @param TedOrdProcess010VO, HttpServletRequest
	 * @return Map<String,Object>
	 */
	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes"})
	public void updateStrCdProdDataBatch(final ArrayList<NEDMWEB0210VO> rtnList,final String loginId) throws Exception, SQLException {
		
		getSqlMapClientTemplate().execute(new SqlMapClientCallback(){
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
		    	executor.startBatch();
		    	
		    	for ( NEDMWEB0210VO  rtnProd  : rtnList ) {
		    		rtnProd.setModId(loginId); 
		    		executor.update("NEDMWEB0210.EDI_ORD_PROD_UPDATE_10_01", rtnProd);
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
	public int updateMstSumData(NEDMWEB0210VO  vo) throws Exception, SQLException {
		return (Integer)getSqlMapClientTemplate().update("NEDMWEB0210.EDI_ORD_PROD_UPDATE_11", vo);
	}
	
	
	
	
}
