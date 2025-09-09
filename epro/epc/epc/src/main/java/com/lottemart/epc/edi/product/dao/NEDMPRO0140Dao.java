package com.lottemart.epc.edi.product.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.product.model.NEDMPRO0140VO;

import lcn.module.framework.base.AbstractDAO;

@Repository("nedmpro0140Dao")
public class NEDMPRO0140Dao extends AbstractDAO {

	
	public List<NEDMPRO0140VO> selectPlcProductList(NEDMPRO0140VO vo) throws Exception{
		return (List<NEDMPRO0140VO>)getSqlMapClientTemplate().queryForList("NEDMPRO0140.selectPlcProductList", vo);
	}
	
	
}
