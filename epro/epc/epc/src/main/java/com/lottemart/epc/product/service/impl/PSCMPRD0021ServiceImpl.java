package com.lottemart.epc.product.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.product.dao.PSCMPRD0021Dao;
import com.lottemart.epc.product.service.PSCMPRD0021Service;
import com.lottemart.common.util.DataMap;

/**
 * 
 * @author khKim
 * @Description : 상품관리 - 대표상품코드관리 
 * @Class : com.lottemart.epc.product.service.impl
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011.12.16  khKim
 * @version : 
 * </pre>
 */
@Service("pscmprd0021Service")
public class PSCMPRD0021ServiceImpl implements PSCMPRD0021Service {

	@Autowired
	private PSCMPRD0021Dao pscmprd0021Dao;

	
	public List<DataMap> selectRepProdCdList(DataMap paramMap) throws Exception {
		return pscmprd0021Dao.selectRepProdCdList(paramMap);
	}
}
