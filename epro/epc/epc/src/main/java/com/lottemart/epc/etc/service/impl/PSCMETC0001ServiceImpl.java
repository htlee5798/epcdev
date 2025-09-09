package com.lottemart.epc.etc.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.etc.dao.PSCMETC0001Dao;
import com.lottemart.epc.etc.service.PSCMETC0001Service;
import com.lottemart.epc.etc.model.PSCMETC0001VO;


@Service("PSCMETC0001SERVICE")
public class PSCMETC0001ServiceImpl  implements PSCMETC0001Service {
	@Autowired
	private PSCMETC0001Dao pscmetc0001Dao;
	
	
//	public List<DataMap> getAsianaBalanceAccountList(DataMap paramMap) throws Exception {
//		return pscmetc0001Dao.getAsianaBalanceAccountList(paramMap);
//	}	

	
	/**
	 * 관리자관리 리스트 조회
	 * @return List<DataMap>
	 * @throws Exception
	 */
	public List<DataMap> selectCodeMainList(DataMap paramMap) throws Exception {
		return pscmetc0001Dao.selectCodeMainList(paramMap);
	}	
	
	public void saveCodeMainList(List<PSCMETC0001VO> pscmetc0001VO) throws Exception {
		pscmetc0001Dao.saveCodeMainList(pscmetc0001VO);
	}		
	
	public void insertCodeMainList(List<PSCMETC0001VO> pscmetc0001VO) throws Exception {
		pscmetc0001Dao.insertCodeMainList(pscmetc0001VO);
	}	
	
	public void updateCodeMainList(List<PSCMETC0001VO> pscmetc0001VO) throws Exception {
		pscmetc0001Dao.updateCodeMainList(pscmetc0001VO);
	}	
	
	public void deleteCodeMainList(List<PSCMETC0001VO> pscmetc0001VO) throws Exception {
		pscmetc0001Dao.deleteCodeMainList(pscmetc0001VO);
	}		
	
}