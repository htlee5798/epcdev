package com.lottemart.epc.edi.imgagong.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.edi.imgagong.model.NEDMIGG0010VO;

import lcn.module.framework.base.AbstractDAO;

/**
 * @Class Name : NEDMIGG0010Dao
 * @Description : 임가공 출고 관리 Dao
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 * 수정일			수정자          		수정내용
 * ----------	-----------		---------------------------
 * 2018.11.20	SHIN SE JIN		최초생성
 * 
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */

@Repository("NEDMIGG0010Dao")
public class NEDMIGG0010Dao extends AbstractDAO {
	
	/**
	 * 임가공 입고 정보 저장
	 * @param NEDMIGG0010VO
	 * @throws SQLException
	 */
	public void insertGrDataInfo(NEDMIGG0010VO paramVO) throws SQLException {
		insert("NEDMIGG0010.insertGrDataInfo", paramVO);
	}
	
	/**
	 * 임가공 입고정보 조회
	 * @param NEDMIGG0010VO
	 * @return List<NEDMIGG0010VO>
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<NEDMIGG0010VO> selectGrDataList(NEDMIGG0010VO paramVO) throws SQLException {
		return list("NEDMIGG0010.selectGrDataList", paramVO);
	}
	
	/**
	 * 임가공 RFC호출 로그저장(입고,삭제)
	 * @param HashMap<String, Object>
	 * @throws SQLException
	 */
	public String insertTpcRfcCallIgglog(HashMap<String, Object> paramMap) throws SQLException {
		return (String)insert("NEDMIGG0010.insertTpcRfcCallIgglog", paramMap);
	}
	
	/**
	 * 임가공 RFC호출 로그상세정보 저장(입고,삭제)
	 * @param HashMap<String, Object>
	 * @throws SQLException
	 */
	public void updateTpcRfcCallIgglog(HashMap<String, Object> paramMap) throws SQLException {
		insert("NEDMIGG0010.updateTpcRfcCallIgglog", paramMap);
	}

}