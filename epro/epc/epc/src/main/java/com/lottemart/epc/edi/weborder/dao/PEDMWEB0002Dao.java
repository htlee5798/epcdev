package com.lottemart.epc.edi.weborder.dao;

import java.sql.SQLException;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.model.TedOrdList;
import com.lottemart.epc.edi.weborder.model.TedOrdList001VO;
import com.lottemart.epc.edi.weborder.model.TedOrdSuppInfoVO;


@Repository("pedmweb0002Dao")
public class PEDMWEB0002Dao extends AbstractDAO {

	public int selectProdOrdListTotCnt(SearchWebOrder vo) {
		return ((Integer) getSqlMapClientTemplate().queryForObject("PEDMWEB0002.selectProdOrdListTotCnt", vo)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<TedOrdList> selectProdOrdList(SearchWebOrder vo) {
		// TODO Auto-generated method stub
		return (List<TedOrdList>)getSqlMapClientTemplate().queryForList("PEDMWEB0002.selectProdOrdList", vo);
	}
	
	@SuppressWarnings("unchecked")
	public List<TedOrdList> selectProdOrdDetInfo(SearchWebOrder vo) {
		// TODO Auto-generated method stub
		return (List<TedOrdList>)getSqlMapClientTemplate().queryForList("PEDMWEB0002.selectProdOrdDetInfo", vo);
	}
	
	@SuppressWarnings("unchecked")
	public TedOrdList selectProdCntSum(SearchWebOrder vo) {
		// TODO Auto-generated method stub
		return (TedOrdList)getSqlMapClientTemplate().queryForObject("PEDMWEB0002.selectProdCntSum", vo);
	}
	
	/** 상품코드 조회 */
	public String selectChkProdCd(TedOrdList vo) {
		return (String)selectByPk("PEDMWEB0002.selectChkProdCd", vo);
	}
	
	/** 점포 코드 조회 */
	@SuppressWarnings("deprecation")
	public List<TedOrdSuppInfoVO> selectOrdStrCd(TedOrdSuppInfoVO vo) {
		return (List<TedOrdSuppInfoVO>)getSqlMapClientTemplate().queryForList("PEDMWEB0002.selectOrdStrCd", vo);
	}
	
	
	/**
	 * 점포 상품정보 삭제
	 * @param TedOrdList001VO
	 * @return void
	 * @throws SQLException
	 */
	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes"})
	public void deleteStrOrdInfo(final TedOrdList001VO prodList) throws Exception, SQLException {
		getSqlMapClientTemplate().execute(new SqlMapClientCallback(){
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
		    	executor.startBatch();
		    	
		    	for ( TedOrdList  ord : prodList ) {
		    		executor.delete("PEDMWEB0002.deleteStrOrdInfo", ord);
		        }
		    	return new Integer(executor.executeBatch());
			}
		});
		
	}
}
