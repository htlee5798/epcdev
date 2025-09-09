package com.lottemart.epc.common.dao;

import java.util.List;

import lcn.module.framework.base.AbstractDAO;
import org.springframework.stereotype.Repository;
import com.lottemart.epc.common.model.PSCMCOM0008VO;

@Repository("PSCMCOM0008Dao")
public class PSCMCOM0008Dao extends AbstractDAO  {

	
	/**
	 * Desc : 
	 * @Method Name : selectVendCodeList
	 * @param selectedCode
	 * @return
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings({ "unchecked" })
	public List<PSCMCOM0008VO> selectVendCodeList(PSCMCOM0008VO vo)
	{
		return (List<PSCMCOM0008VO>)this.list("PSCMCOM0008.selectVendCodeList", vo);
	}

	
	/**
	 * Desc : 
	 * @Method Name : selectVendCodeListCnt
	 * @param selectedCode
	 * @return int
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings({ "unchecked" })
	public int selectVendCodeListCnt(PSCMCOM0008VO vo)
	{
		return ((Integer)getSqlMapClientTemplate().queryForObject("PSCMCOM0008.selectVendCodeCnt", vo)).intValue();

	}



}




