package com.lottemart.common.menu.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.common.menu.model.CommonMenuSearchVO;
import com.lottemart.common.menu.model.MenuVO;

@Repository("CommonMenuDao")
public class CommonMenuDao {

	@Autowired
	private SqlMapClient sqlMapClient;

	@SuppressWarnings("unchecked")
	public List<MenuVO> selectByName(CommonMenuSearchVO search) throws SQLException {
		return sqlMapClient.queryForList("CommonMenu.selectByName", search);
	}

	public int countByName(CommonMenuSearchVO search) throws SQLException {
		return (Integer) sqlMapClient.queryForObject("CommonMenu.countByName", search);
	}

}