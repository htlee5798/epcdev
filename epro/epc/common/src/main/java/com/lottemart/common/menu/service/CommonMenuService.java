package com.lottemart.common.menu.service;

import java.util.List;

import com.lottemart.common.menu.model.CommonMenuSearchVO;
import com.lottemart.common.menu.model.MenuVO;

public interface CommonMenuService {

	List<MenuVO> selectByName(CommonMenuSearchVO search) throws Exception;

	int countByName(CommonMenuSearchVO search) throws Exception;

}
