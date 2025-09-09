package com.lottemart.common.menu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.menu.dao.CommonMenuDao;
import com.lottemart.common.menu.model.CommonMenuSearchVO;
import com.lottemart.common.menu.model.MenuVO;
import com.lottemart.common.menu.service.CommonMenuService;

@Service("CommonMenuService")
public class CommonMenuServiceImpl implements CommonMenuService {

	@Autowired
	private CommonMenuDao commonMenuDao;

	@Override
	public List<MenuVO> selectByName(CommonMenuSearchVO search) throws Exception {
		return commonMenuDao.selectByName(search);
	}

	@Override
	public int countByName(CommonMenuSearchVO search) throws Exception {
		return commonMenuDao.countByName(search);
	}

}
