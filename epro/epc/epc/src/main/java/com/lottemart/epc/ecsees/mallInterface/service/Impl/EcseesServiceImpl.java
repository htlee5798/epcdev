package com.lottemart.epc.ecsees.mallInterface.service.Impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.ecsees.mallInterface.dao.EcseesDao;
import com.lottemart.epc.ecsees.mallInterface.model.*;
import com.lottemart.epc.ecsees.mallInterface.service.EcseesService;

@Service("EcseesService")
public class EcseesServiceImpl implements EcseesService {
	
	@Autowired
	private EcseesDao ecseesDao;

	@Override
	public List<ChannelVO> selectChannelList(String sync_date) throws Exception {
		return ecseesDao.getChannelList(sync_date);
	}
	
	@Override
	public List<StoreVO> selectStoreList(String sync_date) throws Exception {
		return ecseesDao.getStoreList(sync_date);
	}
	
	@Override
	public List<Order_itemVO> selectOrder_itemList(HashMap<String, String> paramMap) throws Exception {
		return ecseesDao.getOrder_itemList(paramMap);
	}
	@Override
	public List<Order_itemVO> selectOrder_itemById(List<String> orderIdList)
			throws Exception {
		return ecseesDao.getOrder_itemById(orderIdList);
	}
	@Override
	public List<Order_itemVO> selectOrderCancellation(
			HashMap<String, String> paramMap) throws Exception {
		return ecseesDao.getOrderCancellation(paramMap);
	}
	@Override
	public List<ProductVO> selectProductList(String sync_date) throws Exception {
		return ecseesDao.getProductList(sync_date);
	}
	@Override
	public List<ProductVO> selectProductById(String product_id) throws Exception {
		return ecseesDao.getProductById(product_id);
	}
	@Override
	public List<TeamVO> selectTeamList(String sync_date) throws Exception {
		return ecseesDao.getTeamList(sync_date);
	}
	@Override
	public List<CategoryVO> selectCategoryList(String sync_date)
			throws Exception {
		return ecseesDao.getCategoryList(sync_date);
	}
	@Override
	public List<AffiliateVO> selectAffiliateList(String sync_date)
			throws Exception {
		return ecseesDao.getAffiliateList(sync_date);
	}
	@Override
	public List<ProductNaverFinalVO> selectProductNaverFinalList(
			HashMap<String, String> paramMap) throws Exception {
		return ecseesDao.getProductNaverFinalList(paramMap);
	}
}
