package com.lottemart.epc.common.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.model.PSCMCOM0002VO;

import lcn.module.framework.base.AbstractDAO;

@Repository("PSCMCOM0002Dao")
public class PSCMCOM0002Dao extends AbstractDAO  {

	@Autowired
	private SqlMapClient sqlMapClient;

	@SuppressWarnings("unchecked")
	public List<DataMap> selectVendorList(PSCMCOM0002VO searchVO) throws SQLException{
		return (List<DataMap>)sqlMapClient.queryForList("PSCMCOM0002.selectVendorList", searchVO);
	}
	@SuppressWarnings("unchecked")
	public List<DataMap> selectVendorListGroupAtt(PSCMCOM0002VO searchVO) throws SQLException{
		return (List<DataMap>)sqlMapClient.queryForList("PSCMCOM0002.selectVendorListGroupAtt", searchVO);
	}

	@SuppressWarnings("unchecked")
	public List<DataMap> selectVendorsSmallPopupList(PSCMCOM0002VO searchVO) throws SQLException{
		return (List<DataMap>)sqlMapClient.queryForList("PSCMCOM0002.selectVendorsSmallPopupList", searchVO);
	}

	@SuppressWarnings("unchecked")
	public List<DataMap> selectVendorListAdmin(PSCMCOM0002VO searchVO) throws SQLException{
		return (List<DataMap>)sqlMapClient.queryForList("PSCMCOM0002.selectVendorListAdmin", searchVO);
	}
	@SuppressWarnings("unchecked")
	public List<DataMap> selectVendorListAdminGroupAtt(PSCMCOM0002VO searchVO) throws SQLException{
		return (List<DataMap>)sqlMapClient.queryForList("PSCMCOM0002.selectVendorListAdminGroupAtt", searchVO);
	}

	public int countAdminId(PSCMCOM0002VO searchVO) throws SQLException{
		return (Integer)sqlMapClient.queryForObject("PSCMCOM0002.countAdminId", searchVO);
	}

	public int ssoCountAdminId(PSCMCOM0002VO searchVO) throws SQLException{
		return (Integer)sqlMapClient.queryForObject("PSCMCOM0002.ssoCountAdminId", searchVO);
	}

	/**
	 * Desc : 비밀번호 에러 카운트
	 * @Method Name : pwdErrorCnt
	 * @param searchVO
	 * @return
	 * @throws SQLException
	 * @param
	 * @return
	 * @exception Exception
	 */
	public int pwdErrorCnt(PSCMCOM0002VO searchVO) throws SQLException{
		return (Integer)sqlMapClient.queryForObject("PSCMCOM0002.pwdErrorCnt", searchVO);
	}

	/**
	 * Desc : 비밀번호 에러 카운트 증가 (5번 오류시 비활성화)
	 * @Method Name : insertPwdErrorCnt
	 * @param searchVO
	 * @return
	 * @throws SQLException
	 * @param
	 * @return
	 * @exception Exception
	 */
	public void insertPwdErrorCnt(PSCMCOM0002VO searchVO) throws SQLException{
		sqlMapClient.update("PSCMCOM0002.insertPswdErrorCnt", searchVO);
	}

}




