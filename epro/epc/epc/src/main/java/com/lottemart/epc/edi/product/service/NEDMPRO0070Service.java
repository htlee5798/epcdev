package com.lottemart.epc.edi.product.service;

import java.util.List;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.edi.product.model.NEDMPRO0070VO;

public interface NEDMPRO0070Service {

	/**
	 * Desc : 공통코드
	 * @Method Name : getCode
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<NEDMPRO0070VO> getCode(DataMap paramMap) throws Exception;

	public List<NEDMPRO0070VO> getKc(DataMap paramMap) throws Exception;
	
	public List<NEDMPRO0070VO> getBo(DataMap paramMap) throws Exception;
	
	public List<NEDMPRO0070VO> getPl(DataMap dataMap) throws Exception;
}
