package com.lottemart.epc.product.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.model.PSCPPRD0020VO;

/**
 * 
 * @author projectBOS32
 * @Description : 상품관리 - 전상법관리
 * @Class : com.lottemart.epc.product.dao
 * 
 *        <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2016.03.31  projectBOS32
 * @version :
 * </pre>
 */
@Repository("pscmprd0030Dao")
public class PSCMPRD0030Dao {

	@Autowired
	private SqlMapClient sqlMapClient;

	/**
	 * 
	 * @see selectInfoGrpCdList
	 * @Locaton : com.lottemart.epc.product.dao
	 * @MethodName : selectInfoGrpCdList
	 * @author : projectBOS32
	 * @Description : 전상법 상품분류 목록 조회
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectInfoGrpCdList(DataMap paramMap)
			throws SQLException {
		return sqlMapClient.queryForList("PSCMPRD0030.selectInfoGrpCdList",
				paramMap);
	}

	/**
	 * Desc : 전상법 상품분류 상세 목록 조회
	 * 
	 * @Method Name : selectInfoGrpDescList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PSCPPRD0020VO> selectInfoGrpDetList(DataMap paramMap)
			throws Exception {
		return  sqlMapClient.queryForList("PSCMPRD0030.selectInfoGrpDetList",
				paramMap);
	}
	
	/**
	 * Desc : 전상법 상품분류 Cnt
	 * @Method Name : cntInfoGrpDetList
	 * @param param
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public int cntInfoGrpDetList(DataMap param) throws Exception{
		return (Integer) sqlMapClient.queryForObject("PSCMPRD0030.cntInfoGrpDetList", param);
	}

	/**
	 * 
	 * @see "selectElecCommColList"
	 * @Locaton : com.lottemart.epc.product.dao
	 * @MethodName : "selectElecCommColList"
	 * @author : projectBOS32
	 * @Description : 전상법 컬럼 정보 조회
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectElecCommColList(DataMap paramMap)
			throws SQLException {
		return sqlMapClient.queryForList("PSCMPRD0030.selectElecCommColList",
				paramMap);
	}

	/**
	 * 
	 * @see selectElecCommValList
	 * @Locaton : com.lottemart.epc.product.dao
	 * @MethodName : selectElecCommValList
	 * @author : projectBOS32
	 * @Description : 전상법 목록 조회
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectElecCommValList(DataMap paramMap)
			throws SQLException {
		return sqlMapClient.queryForList("PSCMPRD0030.selectElecCommValList",
				paramMap);
	}

	/**
	 * 
	 * @see updateElecComm
	 * @Locaton : com.lottemart.epc.product.dao
	 * @MethodName : updateElecComm
	 * @author : projectBOS32
	 * @Description : 전상법 수정
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	public int updateElecComm(DataMap paramMap) throws SQLException {
		return sqlMapClient.update("PSCMPRD0030.updateElecComm", paramMap);
	}
}
