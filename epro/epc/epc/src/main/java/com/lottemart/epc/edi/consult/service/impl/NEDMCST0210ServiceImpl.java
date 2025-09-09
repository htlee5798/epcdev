package com.lottemart.epc.edi.consult.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.consult.dao.NEDMCST0210Dao;
import com.lottemart.epc.edi.consult.model.Estimation;
import com.lottemart.epc.edi.consult.model.EstimationSheet;
import com.lottemart.epc.edi.consult.service.NEDMCST0210Service;

@Service("nedmcst0210Service")
public class NEDMCST0210ServiceImpl implements NEDMCST0210Service{
	
	@Autowired
	private NEDMCST0210Dao nedmcst0003Dao;
	
	
	public void estimationInsert(Estimation map, String pid) throws Exception{
		// TODO Auto-generated method stub
		map.setPid(pid);
		//nedmcst0003Dao.estimationInsert(map, pid );
	}
	
	public void estimationSheetInsert(Estimation map, String pid) throws Exception{
		// TODO Auto-generated method stub
		//nedmcst0003Dao.estimationSheetInsert(map,pid );
	}
	
	/**
	 * 
	 * @param map
	 * @param pid
	 * @throws Exception
	 */
	public void insertEstimation(Estimation map, String pid) throws Exception {
		map.setPid(pid);
		nedmcst0003Dao.insertEstimation(map);
		
		int seq = 1;
		for (EstimationSheet e : map.getEstimationSheet()) {
			e.setPid(pid);
			e.setSeq("" + seq);
			seq++;
			
			nedmcst0003Dao.insertEstimationSheet(e);
		}
	}
	
	
}
