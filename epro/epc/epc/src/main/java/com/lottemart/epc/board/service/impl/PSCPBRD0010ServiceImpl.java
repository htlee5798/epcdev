package com.lottemart.epc.board.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.board.dao.PSCPBRD0010Dao;
import com.lottemart.epc.board.model.PSCPBRD0010SearchVO;
import com.lottemart.epc.board.service.PSCPBRD0010Service;

/**
 * @Class Name : PSCPBRD0010ServiceImpl.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 12. 16. 오후 03:03:03 mjChoi
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Service("pscpbrd0010Service")
public class PSCPBRD0010ServiceImpl implements PSCPBRD0010Service 
{
	
	@Autowired
	private PSCPBRD0010Dao pscpbrd0010Dao;
	
	/**
	 * 콜센터 1:1문의 상세 내역을 조회
	 * @Method Name : insertCallCenterPopup
	 * @param VO
	 * @return VO
	 * @throws Exception
	 */
	public PSCPBRD0010SearchVO selectCallCenterPopupDetail(PSCPBRD0010SearchVO searchVO) throws Exception 
	{
		return pscpbrd0010Dao.selectCallCenterPopupDetail(searchVO);
	}
	
	/**
	 * 콜센터 1:1문의 상세 내역을 수정
	 * @Method Name : updateCallCenterPopupDetail
	 * @param VO
	 * @return void
	 * @throws Exception
	 */
	public void updateCallCenterPopupDetail(PSCPBRD0010SearchVO searchVO) throws Exception {
		pscpbrd0010Dao.updateCallCenterPopupDetail(searchVO);
	}

	
	public List<DataMap> selectCodeList(DataMap paramMap) throws Exception {
		return pscpbrd0010Dao.selectCodeList(paramMap);
	}			
	
}
 