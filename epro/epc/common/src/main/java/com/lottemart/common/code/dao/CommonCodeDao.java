package com.lottemart.common.code.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.common.util.DataMap;

/**
 * 공통코드 DAO
 * @Class Name : 
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 7. 6. 오후 2:26:14 yhchoi
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Repository("commonCodeDao")
public class CommonCodeDao {

	@Autowired
	private SqlMapClient sqlMapClient;

	/**
	 * 공통코드목록조회
	 * Desc : 
	 * @Method Name : getCodeList
	 * @param majorCd
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> getCodeList(String majorCd) throws SQLException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("majorCd", majorCd);
		return (List<DataMap>)sqlMapClient.queryForList("CommonCode.getCodeList", paramMap);
	}

	@SuppressWarnings("unchecked")
	public List<DataMap> getCodeList(String majorCd, String[] minorCds) throws SQLException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("majorCd", majorCd);
		paramMap.put("minorCds", minorCds);
		return (List<DataMap>)sqlMapClient.queryForList("CommonCode.getCodeListByMinorCd", paramMap);
	}

	/**
	 * 공통코드 조회
	 * Desc : 
	 * @Method Name : getCode
	 * @param majorCd
	 * @param minorCd
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public DataMap getCode(String majorCd, String minorCd) throws SQLException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("majorCd", majorCd);
		paramMap.put("minorCd", minorCd);
		return (DataMap)sqlMapClient.queryForObject("CommonCode.getCode", paramMap);
	}
	
	/**
	 * 권한 조회
	 * Desc : 
	 * @Method Name : selectAuthYn
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public String selectAuthYn(DataMap paramMap)  throws SQLException{
		return (String) sqlMapClient.queryForObject("CommonCode.selectAuthYn", paramMap);
	}
	

	/**
	 * 롯데ON 공통코드 조회
	 * Desc : 
	 * @Method Name : getEcCode
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public DataMap getEcCode(DataMap paramMap) throws SQLException {
		return (DataMap)sqlMapClient.queryForObject("CommonCode.getEcCode", paramMap);
	}

	/**
	 * 롯데ON 공통코드 리스트 조회
	 * @Method Name : getEcCodeList	
	 * @param ecMajorCd
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> getEcCodeList(String ecMajorCd) throws SQLException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ecMajorCd", ecMajorCd);
		return (List<DataMap>)sqlMapClient.queryForList("CommonCode.getEcCodeList", paramMap);
	}	
	
}
