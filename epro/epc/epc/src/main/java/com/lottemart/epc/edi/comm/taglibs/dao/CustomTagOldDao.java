package com.lottemart.epc.edi.comm.taglibs.dao;


import java.util.HashMap;
import java.util.List;

import lcn.module.common.util.HashBox;
import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;



@Repository("customTagOldDao")
public class CustomTagOldDao extends AbstractDAO {
	
	
//	@Autowired
//	private SqlMapClient sqlMapClient;

    
   public List<HashBox> getCode(HashBox hParam) throws Exception {
	   return (List<HashBox>)getSqlMapClientTemplate().queryForList("common.COMMON_CODE_SQL01_OLD", hParam);
    }
   
   public List<HashBox> getTetCode(HashBox hParam) throws Exception {
	   return (List<HashBox>)getSqlMapClientTemplate().queryForList("common.COMMON_CODE_SQL02_OLD", hParam);
    }
 
    
   public  List<HashBox> getCpCode(HashMap hBmanNos) throws Exception {
   		return (List<HashBox>)getSqlMapClientTemplate().queryForList("common.COMMON_CP_SQL01_OLD", hBmanNos);
   }
   
   public  List<HashBox> getL1Code() throws Exception {
 		return (List<HashBox>)getSqlMapClientTemplate().queryForList("common.COMMON_L1_SQL01_OLD");
  }
  
   public  List<HashBox> getOrdL1Code() throws Exception {
 		return (List<HashBox>)getSqlMapClientTemplate().queryForList("common.COMMON_ORG_SQL01_OLD");
  }
   
   
   public List<HashBox> getBizCode(HashBox hParam) throws Exception {
	   return (List<HashBox>)getSqlMapClientTemplate().queryForList("common.VENDER_SQL01_OLD", hParam);
    }
  
   public List<HashBox> getAreaCode(HashBox hParam) throws Exception {
	   return (List<HashBox>)getSqlMapClientTemplate().queryForList("common.COMMON_AREA_SQL01_OLD", hParam);
    }
   
   
   
}
