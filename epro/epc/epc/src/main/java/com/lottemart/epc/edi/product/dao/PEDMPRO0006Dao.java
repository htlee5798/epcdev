package com.lottemart.epc.edi.product.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lcn.module.common.util.HashBox;
import lcn.module.framework.base.AbstractDAO;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.epc.edi.product.model.ColorSize;
import com.lottemart.epc.edi.product.model.NewProduct;
import com.lottemart.epc.edi.product.model.PEDMPRO00061VO;
import com.lottemart.epc.edi.product.model.PEDMPRO0006VO;
import com.lottemart.epc.edi.product.model.SearchProduct;


@Repository
public class PEDMPRO0006Dao  extends AbstractDAO  {

	
	
	
	public HashMap selectBarCodeProductInfo(PEDMPRO0006VO searchParam) {
		// TODO Auto-generated method stub
		return (HashMap)getSqlMapClientTemplate().queryForObject("PEDMPRO0006.selectBarcodeProductInfo", searchParam);
	}
	
	/*신규상품 임시보관함 상품정보 조회*/
	public HashMap selectNewprodregInfo(PEDMPRO0006VO searchParam) {
		// TODO Auto-generated method stub
		return (HashMap)getSqlMapClientTemplate().queryForObject("PEDMPRO0006.selectNewprodregInfo", searchParam);
	}
	
	
	public List selectBarcodeList(PEDMPRO0006VO searchParam) {
		// TODO Auto-generated method stub
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMPRO0006.selectBarcodeList", searchParam);
	}
	
	
	public String selectBarcodeList() throws DataAccessException  {
		// TODO Auto-generated method stub
		return (String)getSqlMapClientTemplate().queryForObject("PEDMPRO0006.selectPgmId");
	}
	
	
	/*new_logi_bcd_edi INSERT or UPDATE*/
	public void insertNewLogiBcdEdi(PEDMPRO00061VO pedmpro00061vo) throws DataAccessException  {
		getSqlMapClientTemplate().insert("PEDMPRO0006.insertNewLogiBcdEdi", pedmpro00061vo);
	}
	public void updateNewLogiBcdEdi(PEDMPRO00061VO pedmpro00061vo) throws DataAccessException  {
		getSqlMapClientTemplate().insert("PEDMPRO0006.updateNewLogiBcdEdi", pedmpro00061vo);
	}
	
	/*new_logi_cd_edi INSERT or UPDATE*/
	public void insertNewLogiCdEdi(PEDMPRO00061VO pedmpro00061vo) throws DataAccessException  {
		getSqlMapClientTemplate().insert("PEDMPRO0006.insertNewLogiCdEdi", pedmpro00061vo);
	}
	public void updateNewLogiCdEdi(PEDMPRO00061VO pedmpro00061vo) throws DataAccessException  {
		getSqlMapClientTemplate().insert("PEDMPRO0006.updateNewLogiCdEdi", pedmpro00061vo);
	}
	
	
	/*new_sale_logi_edi INSERT or UPDATE*/
	public void insertNewSaleLogiEdi(PEDMPRO00061VO pedmpro00061vo) throws DataAccessException  {
		getSqlMapClientTemplate().insert("PEDMPRO0006.insertNewSaleLogiEdi", pedmpro00061vo);
	}
	public void updateNewSaleLogiEdi(PEDMPRO00061VO pedmpro00061vo) throws DataAccessException  {
		getSqlMapClientTemplate().insert("PEDMPRO0006.updateNewSaleLogiEdi", pedmpro00061vo);
	}
	
	public HashMap newBarcodeRegistTmp(String newprod) {
		// TODO Auto-generated method stubnewBarcodeRegistTmp
		return (HashMap)getSqlMapClientTemplate().queryForObject("PEDMPRO0006.newBarcodeRegistTmp", newprod);
	}
	
	public HashMap selectBarcodeListTmp(PEDMPRO0006VO searchParam) {
		// TODO Auto-generated method stub
		return (HashMap)getSqlMapClientTemplate().queryForObject("PEDMPRO0006.selectBarcodeListTmp", searchParam);
	}
	
	
	/*
	public String selectNewProductCode() throws DataAccessException  {
		// TODO Auto-generated method stub
		return (String) getSqlMapClientTemplate().queryForObject("PEDMPRO001.getNewProductCode", null);
	}

	public void insertProductInfo(NewProduct newProduct) throws DataAccessException {
		// TODO Auto-generated method stub
		
		getSqlMapClientTemplate().insert("PEDMPRO001.insertNewProduct", newProduct);
	}

	public void insertProductColorSizeInfo(ArrayList<ColorSize> colorSizeList) throws DataAccessException {
		// TODO Auto-generated method stub
		Iterator colorSizeIter = colorSizeList.iterator();
		while(colorSizeIter.hasNext()) {
			ColorSize argColorSize = (ColorSize)colorSizeIter.next();
			getSqlMapClientTemplate().insert("PEDMPRO001.insertFashionProductColorSize", argColorSize);
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<String> selectProductColorList(String newProductCode) throws DataAccessException {
		// TODO Auto-generated method stub
		return (ArrayList<String>)getSqlMapClientTemplate().queryForList("PEDMPRO001.selectProductColorList", newProductCode);
	}

	public List<NewProduct> selectNewProductListInTemp(SearchProduct searchParam) {
		// TODO Auto-generated method stub
		return (List<NewProduct>)getSqlMapClientTemplate().queryForList("PEDMPRO001.selectProductListInTemp", searchParam);
	}
	*/
	
	/*
	public void insertNewBarcode(PEDMPRO0006VO barCode) throws DataAccessException {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().insert("PEDMPRO0006.insertNewSaleLogi", barCode);
		
		getSqlMapClientTemplate().insert("PEDMPRO0006.insertNewLogiCdEdi", barCode);
		
		getSqlMapClientTemplate().insert("PEDMPRO0006.insertNewLogiBcdEdi", barCode);
	}


	@SuppressWarnings("unchecked")
	public List selectBarcodeList(SearchProduct searchProduct) {
		// TODO Auto-generated method stub
		return (List<PEDMPRO0006VO>)getSqlMapClientTemplate().queryForList("PEDMPRO0006.selectBarcodeList", searchProduct);
	} 
	*/
	
}




