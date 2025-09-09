package com.lottemart.epc.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.dao.PSCMCOM0002Dao;
import com.lottemart.epc.common.model.PSCMCOM0002VO;
import com.lottemart.epc.common.service.PSCMCOM0002Service;


@Service("PSCMCOM0002Service")
public class PSCMCOM0002ServiceImpl implements PSCMCOM0002Service {
	
	@Autowired
	private PSCMCOM0002Dao PSCMCOM0002Dao;

	public List<DataMap> selectVendorList(PSCMCOM0002VO searchVO) throws Exception {
		return PSCMCOM0002Dao.selectVendorList(searchVO);
	}
	
	public List<DataMap> selectVendorListGroupAtt(PSCMCOM0002VO searchVO) throws Exception {
		return PSCMCOM0002Dao.selectVendorListGroupAtt(searchVO);
	}
	
	public List<DataMap> selectVendorsSmallPopupList(PSCMCOM0002VO searchVO) throws Exception {
		return PSCMCOM0002Dao.selectVendorsSmallPopupList(searchVO);
	}
	
	public List<DataMap> selectVendorListAdmin(PSCMCOM0002VO searchVO) throws Exception {
		return PSCMCOM0002Dao.selectVendorListAdmin(searchVO);
	}
	
	public List<DataMap> selectVendorListAdminGroupAtt(PSCMCOM0002VO searchVO) throws Exception {
		return PSCMCOM0002Dao.selectVendorListAdminGroupAtt(searchVO);
	}
	
	public boolean checkAdminId(PSCMCOM0002VO searchVO) throws Exception {
		int cnt = PSCMCOM0002Dao.countAdminId(searchVO);
		
		if( cnt > 0 ) return true;
		else return false;
	}
	
	public boolean ssoCountAdminId(PSCMCOM0002VO searchVO) throws Exception {
		int cnt = PSCMCOM0002Dao.ssoCountAdminId(searchVO);
		
		if( cnt > 0 ) return true;
		else return false;
	}

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
	@Override
	public int pwdErrorCnt(PSCMCOM0002VO searchVO) throws Exception {
		return PSCMCOM0002Dao.pwdErrorCnt(searchVO);
	}

	/**
	 * Desc : 비밀번호 에러 카운트 증가 (5번 오류시 비활성화)
	 * @Method Name : insetPwdErrorCnt
	 * @param searchVO
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@Override
	public void insetPwdErrorCnt(PSCMCOM0002VO searchVO) throws Exception {
		PSCMCOM0002Dao.insertPwdErrorCnt(searchVO);
	}
	
}