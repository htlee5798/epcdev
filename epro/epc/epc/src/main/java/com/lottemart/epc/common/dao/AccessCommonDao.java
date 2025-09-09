package com.lottemart.epc.common.dao;

import lcn.module.framework.base.AbstractDAO;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;


/**
 * 
 * @Class Name : AccessCommonDao.java
 * @Description : EPC 접근 공통 Dao 
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2025.07.17		yun				최초생성
 *               </pre>
 */
@Repository("accessCommonDao")
public class AccessCommonDao extends AbstractDAO {
	@Autowired
	private SqlMapClient sqlMapClient;
	
	/**
	 * API 호출 사용자 정보 조회
	 * @param apiUserId
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String, Object> selectEpcApiUserInfo(String apiUserId) throws Exception{
		return (Map<String, Object>) sqlMapClient.queryForObject("accessCommon.selectEpcApiUserInfo", apiUserId);
	}
	
	/**
	 * API 호출 가능 URL 확인
	 * @param apiUserId
	 * @return int
	 * @throws Exception
	 */
	public int selectEpcApiUrlChk(String apiUrl) throws Exception{
		return (int) sqlMapClient.queryForObject("accessCommon.selectEpcApiUrlChk", apiUrl);
	}
	
	/**
	 * EPC 시스템관리자 정보 조회
	 * @param sysAdminId
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String, Object> selectEpcSysAdminUserInfo(String sysAdminId) throws Exception{
		return (Map<String, Object>) sqlMapClient.queryForObject("accessCommon.selectEpcSysAdminUserInfo", sysAdminId);
	}

}
