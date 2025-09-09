package com.lottemart.epc.edi.product.dao;

import com.lottemart.epc.edi.product.model.ColorSize;
import com.lottemart.epc.edi.product.model.NewProduct;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;


import lcn.module.framework.base.AbstractDAO;

@Repository
public class PEDMPRO0004Dao extends AbstractDAO {
	/*new_logi_bcd_edi INSERT or UPDATE*/
	public void insertColorSizeFromExcelBatch(List<ColorSize> colorSizeList) throws DataAccessException  {
		Iterator colorSizeIter = colorSizeList.iterator();
		while(colorSizeIter.hasNext()) {
			ColorSize argColorSize = (ColorSize)colorSizeIter.next();
			
			getSqlMapClientTemplate().insert("PEDMPRO0004.insertExcelBatchColorSize", argColorSize);
		}
	}

	public void insertProductInfoList(ArrayList<NewProduct> newProductArrayList) 
	throws DataAccessException {
		// TODO Auto-generated method stub
		Iterator productItr = newProductArrayList.iterator();
		while( productItr.hasNext() ) {
			NewProduct tmpProduct = (NewProduct)productItr.next();
			getSqlMapClientTemplate().insert("PEDMPRO0004.insertExcelBatchProduct", tmpProduct);
		}
	}
	
}
