package com.lottemart.epc.board.service;


import java.util.List;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.board.model.PSCPBRD0010SearchVO;

/**
 * @Class Name : PSCPBRD0010Service.java
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
public interface PSCPBRD0010Service 
{
	/**
	 * 콜센터 1:1문의 상세 내역을 조회
	 * @Method Name : selectCallCenterPopupDetail
	 * @param VO
	 * @return VO
	 * @throws Exception
	 */
	public PSCPBRD0010SearchVO selectCallCenterPopupDetail(PSCPBRD0010SearchVO searchVO) throws Exception;
	
	/**
	 * 콜센터 1:1문의  상세 내역을 수정
	 * @Method Name : updateCallCenterPopupDetail
	 * @param VO
	 * @return void
	 * @throws Exception
	 */
	public void updateCallCenterPopupDetail(PSCPBRD0010SearchVO searchVO) throws Exception;

	public List<DataMap> selectCodeList(DataMap paramMap) throws Exception;
	
}
 