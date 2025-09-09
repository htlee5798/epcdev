package com.lottemart.epc.etc.dao;

import java.sql.SQLException;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.etc.model.PSCMETC0001VO;

@Repository("PSCPETC0002Dao")
public class PSCPETC0002Dao extends AbstractDAO{

	public PSCMETC0001VO selectCodePopup(PSCMETC0001VO vo) throws SQLException{
		return (PSCMETC0001VO) selectByPk("PSCMETC0001.selectCodePopup", vo);
	}
	
	public void insertCodePopup(PSCMETC0001VO vo) throws SQLException{
		insert("PSCMETC0001.insertCodeMainList", vo);
	}

	public void updateCodePopup(PSCMETC0001VO vo) throws SQLException{
		update("PSCMETC0001.updateCodeMainList", vo);
	}

	public void deleteCodePopup(PSCMETC0001VO vo) throws SQLException{
		delete("PSCMETC0001.deleteCodeMainList", vo);
	}
	
}
