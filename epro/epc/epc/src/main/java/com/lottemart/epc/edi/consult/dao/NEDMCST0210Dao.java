package com.lottemart.epc.edi.consult.dao;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.consult.model.Estimation;
import com.lottemart.epc.edi.consult.model.EstimationSheet;


@Repository("nedmcst0210Dao")
public class NEDMCST0210Dao extends AbstractDAO {
	
	/**
	 * 
	 * @param map
	 * @param pid
	 * @throws Exception
	 */
	public void insertEstimation(Estimation map) throws Exception{
		getSqlMapClientTemplate().insert("NEDMCST0210.TSC_ESTIMATION-INSERT01", map);
	}
	
	/**
	 * 견적서 세부 입력
	 * @param map
	 * @throws Exception
	 */
	public void insertEstimationSheet(EstimationSheet e) throws Exception {
		getSqlMapClientTemplate().insert("NEDMCST0210.TSC_ESTIMATION_SHEET-INSERT01", e);
	}

}
