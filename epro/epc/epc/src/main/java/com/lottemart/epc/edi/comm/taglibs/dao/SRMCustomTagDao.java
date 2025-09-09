package com.lottemart.epc.edi.comm.taglibs.dao;


import java.util.List;

import lcn.module.common.util.HashBox;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.srm.dao.SRMDBConnDao;

@Repository("srmCustomTagDao")
public class SRMCustomTagDao extends SRMDBConnDao {
	
	public List<HashBox> getCode(HashBox hParam) throws Exception {
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("SRMCommon.selectCommonCode", hParam);
	}
	/*
	public List<HashBox> getTetCode(HashBox hParam) throws Exception {
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("common.COMMON_CODE_SQL02", hParam);
	}
	
	public  List<HashBox> getCpCode(HashMap hBmanNos) throws Exception {
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("common.COMMON_CP_SQL01", hBmanNos);
	}
	
	public  List<HashBox> getL1Code() throws Exception {
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("common.COMMON_L1_SQL01");
	}
	
	public  List<HashBox> getOrdL1Code() throws Exception {
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("common.COMMON_ORG_SQL01");
	}
	
	public List<HashBox> getBizCode(HashBox hParam) throws Exception {
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("common.VENDER_SQL01", hParam);
	}
	
	public List<HashBox> getAreaCode(HashBox hParam) throws Exception {
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("common.COMMON_AREA_SQL01", hParam);
	}
	*/
}
