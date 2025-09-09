package com.lottemart.epc.ecsees.mallInterface.service;

import java.util.HashMap;
import java.util.List;

import com.lottemart.epc.ecsees.mallInterface.model.*;

public interface EcseesService {
	public List<ChannelVO> selectChannelList(String sync_date) throws Exception;
	public List<Order_itemVO> selectOrder_itemList(HashMap<String, String> paramMap) throws Exception;
	public List<Order_itemVO> selectOrder_itemById(List<String> orderIdList) throws Exception;
	public List<Order_itemVO> selectOrderCancellation(HashMap<String, String> paramMap) throws Exception;
	public List<ProductVO> selectProductList(String sync_date) throws Exception;
	public List<ProductVO> selectProductById(String product_id) throws Exception;
	public List<StoreVO> selectStoreList(String sync_date) throws Exception;
	public List<TeamVO> selectTeamList(String sync_date) throws Exception;
	public List<CategoryVO> selectCategoryList(String sync_date) throws Exception;
	public List<AffiliateVO> selectAffiliateList(String sync_date) throws Exception;
	public List<ProductNaverFinalVO> selectProductNaverFinalList(HashMap<String, String> paramMap) throws Exception;
}
