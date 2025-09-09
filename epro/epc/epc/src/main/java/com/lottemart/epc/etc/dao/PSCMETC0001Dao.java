
package com.lottemart.epc.etc.dao;

import java.sql.SQLException;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.etc.model.PSCMETC0001VO;


@Repository("pscmetc0001Dao")
public class PSCMETC0001Dao extends AbstractDAO{
//	@Autowired
//	private SqlMapClient sqlMapClient;

	
//	@SuppressWarnings("unchecked")
//	public List<DataMap> getAsianaBalanceAccountList(DataMap paramMap) throws SQLException{
//		return (List<DataMap>)sqlMapClient.queryForList("PromotionEvent.getAsianaBalanceAccountList", paramMap);
//	}
	
	/**
     * 관리자관리 리스트 조회
     * @return List<DataMap>
     * @throws SQLException  DB 관련 오류
     */
	@SuppressWarnings("unchecked")
	public List<DataMap> selectCodeMainList(DataMap paramMap) throws SQLException{
		return (List<DataMap>)getSqlMapClientTemplate().queryForList("PSCMETC0001.selectCodeMainList", paramMap);
	}
	
	
	/**
     * 관리자관리 리스트 저장
     * @return List<DataMap>
     * @throws SQLException  DB 관련 오류
     */
	public void saveCodeMainList(List<PSCMETC0001VO> pscmetc0001VO) throws SQLException{
		for(PSCMETC0001VO paramVO : pscmetc0001VO){
			if(paramVO.getCrud().toString().equals("I")){
				// 등록
				insert("PSCMETC0001.insertCodeMainList", paramVO);
			}else{
				// 수정
				update("PSCMETC0001.updateCodeMainList", paramVO);
			}
		}
	}		
	
	
	/**
     * 관리자관리 리스트 등록
     * @return List<DataMap>
     * @throws SQLException  DB 관련 오류
     */
	public void insertCodeMainList(List<PSCMETC0001VO> pscmetc0001VO) throws SQLException{
		for(PSCMETC0001VO paramVO : pscmetc0001VO){
			insert("PSCMETC0001.insertCodeMainList", paramVO);
		}
	}	
	
	
	/**
     * 관리자관리 리스트 수정
     * @return List<DataMap>
     * @throws SQLException  DB 관련 오류
     */
	public void updateCodeMainList(List<PSCMETC0001VO> pscmetc0001VO) throws SQLException{
		for(PSCMETC0001VO paramVO :  pscmetc0001VO){
			update("PSCMETC0001.updateCodeMainList", paramVO);
		}
	}	
	
	
	/**
     * 관리자관리 리스트 삭제
     * @return List<DataMap>
     * @throws SQLException  DB 관련 오류
     */
	public void deleteCodeMainList(List<PSCMETC0001VO> pscmetc0001VO) throws SQLException{
		for(PSCMETC0001VO paramVO : pscmetc0001VO){
			delete("PSCMETC0001.deleteCodeMainList", paramVO);
		}
	}		
}
