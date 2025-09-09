package com.lottemart.epc.system.service;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import xlib.cmc.GridData;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.system.model.PSCMSYS0003VO;

/**
 * @Class Name : PSCMSYS0003Service.java
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
public interface PSCMSYS0003Service 
{
	public int selectPartnerTotalCnt(DataMap dataMap) throws Exception;
	
	public List<PSCMSYS0003VO> selectPartnerList(DataMap dataMap) throws Exception;
	
	public void insertPartnerPopup(PSCMSYS0003VO vo) throws Exception;
	
	public int updatePartnerList(DataMap dataMap) throws Exception;
	
	public int deletePartnerList(DataMap dataMap) throws Exception;
	
}
