package com.lottemart.epc.edi.weborder.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.lottemart.epc.edi.weborder.model.NEDMWEB0010VO;
import com.lottemart.epc.edi.weborder.model.NEDMWEB0020VO;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.model.TedOrdList;
import com.lottemart.epc.edi.weborder.model.TedOrdList001VO;
import com.lottemart.epc.edi.weborder.model.TedOrdSuppInfoVO;

/**
 * @Class Name : NEDMWEB0020Dao
 * @Description : 상품별 발주등록회 Dao Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      		수정자          		수정내용
 *  -------    --------    ---------------------------
 * 2015.12.07	O YEUN KWON	  최초생성
 * 
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
*/
@Repository("nedmweb0020Dao")
public class NEDMWEB0020Dao extends AbstractDAO {

	public int selectProdOrdListTotCnt(NEDMWEB0020VO vo) {
		return ((Integer) getSqlMapClientTemplate().queryForObject("NEDMWEB0020.selectProdOrdListTotCnt", vo)).intValue();
	}
	
	@SuppressWarnings("deprecation")
	public int selectVendorException(NEDMWEB0020VO vo) {
		return ((Integer) getSqlMapClientTemplate().queryForObject("NEDMWEB0020.selectVendorException", vo)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<NEDMWEB0020VO> selectProdOrdList(NEDMWEB0020VO vo) {
		return (List<NEDMWEB0020VO>)getSqlMapClientTemplate().queryForList("NEDMWEB0020.selectProdOrdList", vo);
	}
	
	@SuppressWarnings("unchecked")
	public List<NEDMWEB0020VO> selectProdOrdDetInfo(NEDMWEB0020VO vo) {
		return (List<NEDMWEB0020VO>)getSqlMapClientTemplate().queryForList("NEDMWEB0020.selectProdOrdDetInfo", vo);
	}
	
	@SuppressWarnings("unchecked")
	public NEDMWEB0020VO selectProdCntSum(NEDMWEB0020VO vo) {
		return (NEDMWEB0020VO)getSqlMapClientTemplate().queryForObject("NEDMWEB0020.selectProdCntSum", vo);
	}
	
	/** 상품코드 조회 */
	public String selectChkProdCd(TedOrdList vo) {
		return (String)selectByPk("NEDMWEB0020.selectChkProdCd", vo);
	}
	
	/** 점포 코드 조회 */
	@SuppressWarnings("deprecation")
	public List<TedOrdSuppInfoVO> selectOrdStrCd(TedOrdSuppInfoVO vo) {
		return (List<TedOrdSuppInfoVO>)getSqlMapClientTemplate().queryForList("NEDMWEB0020.selectOrdStrCd", vo);
	}
	
	
	/**
	 * 점포 상품정보 삭제
	 * @param TedOrdList001VO
	 * @return void
	 * @throws SQLException
	 */
	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes"})
	public void deleteStrOrdInfo(final ArrayList<NEDMWEB0020VO> prodList) throws Exception, SQLException {
		getSqlMapClientTemplate().execute(new SqlMapClientCallback(){
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
		    	executor.startBatch();
		    	
		    	for ( NEDMWEB0020VO  ord : prodList ) {
		    		executor.delete("NEDMWEB0020.deleteStrOrdInfo", ord);
		        }
		    	return new Integer(executor.executeBatch());
			}
		});
		
	}
}
