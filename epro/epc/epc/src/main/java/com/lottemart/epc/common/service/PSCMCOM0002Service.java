package com.lottemart.epc.common.service;

import java.util.List;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.model.PSCMCOM0002VO;

public interface PSCMCOM0002Service {

	public List<DataMap> selectVendorList(PSCMCOM0002VO searchVO) throws Exception;
	
	public List<DataMap> selectVendorListGroupAtt(PSCMCOM0002VO searchVO) throws Exception;	
	
	public List<DataMap> selectVendorsSmallPopupList(PSCMCOM0002VO searchVO) throws Exception;
	
	public List<DataMap> selectVendorListAdmin(PSCMCOM0002VO searchVO) throws Exception;
	
	public List<DataMap> selectVendorListAdminGroupAtt(PSCMCOM0002VO searchVO) throws Exception;
		
	public boolean checkAdminId(PSCMCOM0002VO searchVO) throws Exception;
	
	public boolean ssoCountAdminId(PSCMCOM0002VO searchVO) throws Exception;
	
	/**
	 * Desc : 비밀번호 에러 카운트
	 * @Method Name : pwdErrorCnt
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public int pwdErrorCnt(PSCMCOM0002VO searchVO) throws Exception;
	
	/**
	 * Desc : 비밀번호 에러 카운트 증가 ( 5번 오류시 비활성화)
	 * @Method Name : insetPwdErrorCnt
	 * @param searchVO
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public void insetPwdErrorCnt(PSCMCOM0002VO searchVO) throws Exception;
	
}