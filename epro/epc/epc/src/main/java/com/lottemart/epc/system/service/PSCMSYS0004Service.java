/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 05. 31. 오후 2:30:50
 * @author      : kslee
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.system.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.system.model.PSCMSYS0001VO;
import com.lottemart.epc.system.model.PSCMSYS0002VO;
import com.lottemart.epc.system.model.PSCMSYS0003VO;
import com.lottemart.epc.system.model.PSCMSYS0004VO;

/**
 * @Class Name : PSCMSYS0004Service
 * @Description : 업체정보 목록조회 Service 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 05. 31. 오후 2:35:30 kslee
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public interface PSCMSYS0004Service {
	
	/**
	 * Desc : 업체정보
	 * @Method Name : selectVendorInfoView
	 * @param recommSeq
	 * @throws SQLException
	 * @return PBOMBRD0003VO
	 */
	public PSCMSYS0003VO selectVendorInfoView(String vendorId) throws Exception;
	
	/**
	 * Desc : 업체정보 수정
	 * @Method Name : updateVendorInfo
	 * @param PSCMSYS0003VO
	 * @throws Exception
	 * @return 결과수
	 */
	public int updateVendorInfo(PSCMSYS0003VO vo) throws Exception;

	/**
	 * Desc : 업체담당자조회
	 * @Method Name : selectVendorUserList
	 * @param recommSeq
	 * @throws SQLException
	 * @return PSCMSYS0002VO
	 */
	public List<PSCMSYS0002VO> selectVendorUserList(Map<String, String> paramMap) throws Exception;

	/**
	 * Desc : 업체주소정보
	 * @Method Name : selectVendorAddrList
	 * @param recommSeq
	 * @throws SQLException
	 * @return PSCMSYS0004VO
	 */
	public List<PSCMSYS0004VO> selectVendorAddrList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * Desc : 업체배송비정보
	 * @Method Name : selectVendorDeliAmtList
	 * @param recommSeq
	 * @throws SQLException
	 * @return PSCMSYS0001VO
	 */
	public List<PSCMSYS0001VO> selectVendorDeliAmtList(Map<String, String> paramMap) throws Exception;

	/**
	 * Desc : 업체담당자 저장
	 * @Method Name : vendorUserListSave
	 * @param PSCMSYS0002VO
	 * @throws Exception
	 * @return 결과수
	 */
	public int vendorUserListSave(HttpServletRequest request) throws Exception;

	/**
	 * Desc : 업체주소 저장
	 * @Method Name : vendorAddrListSave
	 * @param PSCMSYS0004VO
	 * @throws Exception
	 * @return 결과수
	 */
	public int vendorAddrListSave(HttpServletRequest request) throws Exception;
		
	/**
	 * Desc : 업체기준배송비 저장
	 * @Method Name : vendorDeliListSave
	 * @param PSCMSYS0001VO
	 * @throws Exception
	 * @return 결과수
	 */
	public int vendorDeliListSave(HttpServletRequest request) throws Exception;

} 
