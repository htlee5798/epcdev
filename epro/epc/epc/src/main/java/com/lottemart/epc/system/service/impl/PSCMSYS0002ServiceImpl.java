package com.lottemart.epc.system.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.system.dao.PSCMSYS0002Dao;
import com.lottemart.epc.system.model.PSCMSYS0002VO;
import com.lottemart.epc.system.service.PSCMSYS0002Service;

/**
 * @Class Name : PSCMSYS0002ServiceImpl.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 20. 오후 02:02:02 mjChoi
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Service("pscmsys0002Service")
public class PSCMSYS0002ServiceImpl implements PSCMSYS0002Service
{
	@Autowired
	private PSCMSYS0002Dao pscmsys0002Dao;
	
	/**
	 * 담당자관리 폼 페이지
	 * @Method Name : selectPersonInChargeInfo
	 * @param paramMap
	 * @return List<VO>
	 * @throws Exception
	 */
	public List<PSCMSYS0002VO> selectPersonInChargeInfo(Map<String, String> paramMap) throws Exception 
	{
		return pscmsys0002Dao.selectPersonInChargeInfo(paramMap);
	}
	
	/**
	 * 담당자관리 수정 처리
	 * @Method Name : updateListPersonInCharge
	 * @param VO
	 * @return void
	 * @throws Exception
	 */
	public void updateListPersonInCharge(PSCMSYS0002VO pscmsys0002VO) throws Exception
	{
			pscmsys0002Dao.updateListPersonInCharge(pscmsys0002VO);
	}

}
