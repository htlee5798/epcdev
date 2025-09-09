package com.lottemart.epc.edi.comm.taglibs.dao;


import java.util.HashMap;
import java.util.List;

import lcn.module.common.util.HashBox;
import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;



@Repository("customTagDao")
public class CustomTagDao extends AbstractDAO {
	
	
//	@Autowired
//	private SqlMapClient sqlMapClient;

    
   public List<HashBox> getCode(HashBox hParam) throws Exception {
	   return (List<HashBox>)getSqlMapClientTemplate().queryForList("common.COMMON_CODE_SQL01", hParam);
    }
   
   //TPC_NEW_CODE
   public List<HashBox> getNewTcpCode(HashBox hParam) throws Exception {
	   return (List<HashBox>)getSqlMapClientTemplate().queryForList("common.COMMON_CODE_SQL03", hParam);
   }
   
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
   
   
   
}
