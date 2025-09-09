package com.lottemart.epc.ecsees.mallInterface.dao;

import java.util.HashMap;
import java.util.List;

import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;

import com.lottemart.epc.ecsees.mallInterface.model.*;

@Repository("ecseesDao")
public class EcseesDao extends AbstractDAO {
	
	@SuppressWarnings("unchecked")
	public List<ChannelVO> getChannelList(String sync_date) throws Exception{
		return (List<ChannelVO>)getSqlMapClientTemplate().queryForList("mallInterface.channel", sync_date);
	};
	@SuppressWarnings("unchecked")
	public List<Order_itemVO> getOrder_itemList(HashMap<String, String> paramMap) throws Exception{
		return (List<Order_itemVO>)getSqlMapClientTemplate().queryForList("mallInterface.order_item", paramMap);
	};
	@SuppressWarnings("unchecked")
	public List<Order_itemVO> getOrder_itemById(List<String> orderIdList) throws Exception{
		return (List<Order_itemVO>)getSqlMapClientTemplate().queryForList("mallInterface.order_itemById", orderIdList);
	};
	@SuppressWarnings("unchecked")
	public List<Order_itemVO> getOrderCancellation(HashMap<String, String> paramMap) throws Exception{
		return (List<Order_itemVO>)getSqlMapClientTemplate().queryForList("mallInterface.orderCancellation", paramMap);
	};
	@SuppressWarnings("unchecked")
	public List<ProductVO> getProductList(String sync_date) throws Exception{
		return (List<ProductVO>)getSqlMapClientTemplate().queryForList("mallInterface.product", sync_date);
	};
	@SuppressWarnings("unchecked")
	public List<ProductVO> getProductById(String product_id) throws Exception{
		return (List<ProductVO>)getSqlMapClientTemplate().queryForList("mallInterface.productById", product_id);
	};
	@SuppressWarnings("unchecked")
	public List<StoreVO> getStoreList(String sync_date) throws Exception{
		return (List<StoreVO>)getSqlMapClientTemplate().queryForList("mallInterface.store", sync_date);
	};
	@SuppressWarnings("unchecked")
	public List<TeamVO> getTeamList(String sync_date) throws Exception{
		return (List<TeamVO>)getSqlMapClientTemplate().queryForList("mallInterface.team", sync_date);
	};
	@SuppressWarnings("unchecked")
	public List<CategoryVO> getCategoryList(String sync_date) throws Exception{
		return (List<CategoryVO>)getSqlMapClientTemplate().queryForList("mallInterface.category", sync_date);
	};
	@SuppressWarnings("unchecked")
	public List<AffiliateVO> getAffiliateList(String sync_date) throws Exception{
		return (List<AffiliateVO>)getSqlMapClientTemplate().queryForList("mallInterface.affiliate", sync_date);
	};
	@SuppressWarnings("unchecked")
	public List<ProductNaverFinalVO> getProductNaverFinalList(HashMap<String, String> paramMap) throws Exception{
		return (List<ProductNaverFinalVO>)getSqlMapClientTemplate().queryForList("mallInterface.productNaverFinalList", paramMap);
	};
	
	
}
