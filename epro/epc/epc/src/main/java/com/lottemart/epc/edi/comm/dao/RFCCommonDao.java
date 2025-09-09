package com.lottemart.epc.edi.comm.dao;

import java.util.HashMap;

import lcn.module.framework.base.AbstractDAO;
import org.springframework.stereotype.Repository;

@Repository("rfcCommonDao")
public class RFCCommonDao extends AbstractDAO {

	/**
	 * RFC Call Log
	 * @param hm
	 * @throws Exception
	 */
	public void insertRFCLog(HashMap<String, Object> hm) throws Exception {
		this.insert("RFCLog.insertTpcRfcCallLog", hm);
	}

}
