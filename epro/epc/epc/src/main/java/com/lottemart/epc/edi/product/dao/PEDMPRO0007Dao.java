package com.lottemart.epc.edi.product.dao;

import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;
import lcn.module.framework.base.AbstractDAO;

import org.springframework.stereotype.Repository;
import com.lottemart.epc.edi.product.model.PEDMPRO0005VO;
import com.lottemart.epc.edi.product.model.SearchProduct;


@Repository("pEDMPRO0007Dao")
public class PEDMPRO0007Dao  extends AbstractDAO  {


	
	@SuppressWarnings("unchecked")
	public List<PEDMPRO0005VO> selectStandardProductImageList(SearchProduct searchProduct) {
		// TODO Auto-generated method stub
		return (List<PEDMPRO0005VO>)getSqlMapClientTemplate().queryForList("PEDMPRO0007.selectStandardProductImageList", searchProduct);
	}

	
	@SuppressWarnings("unchecked")
	public List<PEDMPRO0005VO> selectFashionProductImageList(SearchProduct searchProduct) {
		// TODO Auto-generated method stub
		return (List<PEDMPRO0005VO>)getSqlMapClientTemplate().queryForList("PEDMPRO0007.selectFashionProductImageList", searchProduct);
	}

	public void deleteMDLegacyImageData(String imageSeq) {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().delete("PEDMPRO0007.deleteMDLegacyImageData", imageSeq);
	}

	public void changeMDLegacyImageData(String imageSeq) {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().delete("PEDMPRO0007.changeMDLegacyImageDataState", imageSeq);
	}


	public String selectMDSellCodeData(SearchProduct searchProduct) {
		// TODO Auto-generated method stub
		return (String)getSqlMapClientTemplate().queryForObject("PEDMPRO0007.selectMDSellCodeinReserv", searchProduct);
	}

	

	public void updateMDSizeInReserv(SearchProduct searchProduct) {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().update("PEDMPRO0007.updateMDSizeInReserv", searchProduct);
	}

	

	public void inserNewMDSize(SearchProduct searchProduct) {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().insert("PEDMPRO0007.inserNewMDSize", searchProduct);
	}

}
