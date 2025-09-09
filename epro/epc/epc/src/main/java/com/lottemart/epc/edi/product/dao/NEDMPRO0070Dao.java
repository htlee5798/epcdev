package com.lottemart.epc.edi.product.dao;

import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.edi.product.model.NEDMPRO0070VO;

@Repository("NEDMPRO0070Dao")
public class NEDMPRO0070Dao extends AbstractDAO {

   @SuppressWarnings("unchecked")
   public List<NEDMPRO0070VO> getCode(DataMap dataMap) throws Exception {
	   return (List<NEDMPRO0070VO>)getSqlMapClientTemplate().queryForList("NEDMPRO0070.COMMON_CODE", dataMap);
    }
	   
   @SuppressWarnings("unchecked")
   public List<NEDMPRO0070VO> getKc(DataMap dataMap) throws Exception{
	   return (List<NEDMPRO0070VO>)getSqlMapClientTemplate().queryForList("NEDMPRO0070.COMMON_CODE_KC", dataMap);
   }
   
   @SuppressWarnings("unchecked")
   public List<NEDMPRO0070VO> getBo(DataMap dataMap) throws Exception{
	   return (List<NEDMPRO0070VO>)getSqlMapClientTemplate().queryForList("NEDMPRO0070.COMMON_CODE_BO", dataMap);
   }
   
   @SuppressWarnings("unchecked")
   public List<NEDMPRO0070VO> getPl(DataMap dataMap) throws Exception{
	   return (List<NEDMPRO0070VO>)getSqlMapClientTemplate().queryForList("NEDMPRO0070.COMMON_CODE_PL", dataMap);
   }
}
