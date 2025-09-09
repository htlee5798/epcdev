package com.lottemart.epc.common.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.common.util.ConfigUtils;
import com.lottemart.common.util.DataMap;

/**
 * 로그인 처리 DAO
 * @Class Name : 
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 9. 22. 오후 2:26:44 jschoi
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Repository("epcLoginDao")
public class PSCMCOM0001Dao {

	@Autowired
	private SqlMapClient sqlMapClient;

	/**
	 * 사업자정보 조회
	 * Desc : 
	 * @Method Name : getClienLoginInfo
	 * @param loginId
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public List<DataMap> getClienLoginInfo(String[] cono) throws SQLException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cono", cono);
		return (List<DataMap>) sqlMapClient.queryForList("EpcLogin.getClientLoginInfo", paramMap);
	}

	public DataMap checkAdminInfo(String loginId) throws SQLException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("loginId", loginId);
		return (DataMap) sqlMapClient.queryForObject("EpcLogin.checkAdminInfo", paramMap);
	}

	public DataMap checkHappyInfo(String loginId) throws SQLException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("loginId", loginId);
		return (DataMap) sqlMapClient.queryForObject("EpcLogin.checkHappyInfo", paramMap);
	}

	public DataMap checkAllianceInfo(String loginId) throws SQLException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("loginId", loginId);
		return (DataMap) sqlMapClient.queryForObject("EpcLogin.checkAllianceInfo", paramMap);
	}

	public DataMap checkClientInfo(DataMap paramMap) throws SQLException {
		return (DataMap) sqlMapClient.queryForObject("EpcLogin.checkClientInfo", paramMap);
	}

	public int updateLastLoginDate(String loginId) throws SQLException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("loginId", loginId);
		return sqlMapClient.update("EpcLogin.updateLastLoginDate", paramMap);
	}
	
	/**
	 * [협력사로그인] 업체 로그인 키 정보 조회
	 * @param bmanNo
	 * @return DataMap
	 * @throws SQLException
	 */
	public DataMap selectVendorLoginKeyInfo(String bmanNo) throws SQLException{
		int validSec = ConfigUtils.getInt("epc.lcn.login.key.valid.tm");		//키유효시간 (seconds)
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("bmanNo", bmanNo);
		paramMap.put("validSec", validSec);
		return (DataMap) sqlMapClient.queryForObject("EpcLogin.selectVendorLoginKeyInfo", paramMap);
	}

	/**
	 * HQ_VEN > TVE_VENDOR MERGE
	 * @param paramMap
	 * @return int
	 * @throws Exception
	 */
	public int updateMergeHqVenToTveVendor(Map<String, Object> paramMap) throws Exception {
		return (int) sqlMapClient.update("EpcLogin.updateMergeHqVenToTveVendor", paramMap);
	}
	
	/**
	 * EPC 시스템관리자 정보 조회
	 * @param loginId
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String, Object> selectEpcSysAdminUserInfo(String loginId) throws Exception{
		return (Map<String, Object>) sqlMapClient.queryForObject("accessCommon.selectEpcSysAdminUserInfo", loginId);
	}
	
	/**
	 * EPC 시스템관리자 최종 접속일시 업데이트
	 * @param loginId
	 * @return int
	 * @throws Exception
	 */
	public int updateSysAdminLastLoginDate(String loginId) throws Exception {
		return (int) sqlMapClient.update("EpcLogin.updateSysAdminLastLoginDate", loginId);
	}
}
