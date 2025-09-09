package com.lottemart.epc.edi.comm.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.comm.model.HistoryVo;

import lcn.module.framework.base.AbstractDAO;

/**
 * 
 * @Class Name : HistoryCommonDao.java
 * @Description : 히스토리/로그 공통 Dao
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2025.06.25		yun				최초생성
 *               </pre>
 */
@Repository(value="historyCommonDao")
public class HistoryCommonDao extends AbstractDAO {
	
	/**
	 * 인터페이스 히스토리 등록
	 * @param paramVo
	 * @throws Exception
	 */
	public void insertTpcIfLog(HistoryVo paramVo) throws Exception {
		getSqlMapClientTemplate().insert("HistoryCommonQuery.insertTpcIfLog", paramVo);
	}
	
	/**
	 * BATCH JOB LOG ID생성 
	 * @return String
	 * @throws Exception
	 */
	public String selectNewBatchJobLogId() throws Exception {
		return (String) getSqlMapClientTemplate().queryForObject("HistoryCommonQuery.selectNewBatchJobLogId");
	}
	
	/**
	 * 배치 히스토리 등록
	 * @param paramVo
	 * @throws Exception
	 */
	public void insertTpcBatchJobLog(HistoryVo paramVo) throws Exception {
		getSqlMapClientTemplate().insert("HistoryCommonQuery.insertTpcBatchJobLog", paramVo);
	}
	
	/**
	 * 배치 히스토리 상태 변경
	 * @param paramVo
	 * @throws Exception
	 */
	public void updateTpcBatchJobLog(HistoryVo paramVo) throws Exception {
		getSqlMapClientTemplate().update("HistoryCommonQuery.updateTpcBatchJobLog", paramVo);
	}

}
