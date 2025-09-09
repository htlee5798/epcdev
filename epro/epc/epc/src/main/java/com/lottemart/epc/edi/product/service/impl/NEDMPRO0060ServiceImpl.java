package com.lottemart.epc.edi.product.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.edi.product.dao.NEDMPRO0060Dao;
import com.lottemart.epc.edi.product.dao.PEDMPRO000Dao;
import com.lottemart.epc.edi.product.model.NewProduct;
import com.lottemart.epc.edi.product.service.NEDMPRO0060Service;

/**
 * @Class Name : NEDMPRO0060ServiceImpl
 * @Description : 상품일괄등록 서비스 Impl
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2016.05.18		projectBOS32 	최초생성
 * </pre>
 */
@Service("nEDMPRO0060Service")
public class NEDMPRO0060ServiceImpl implements NEDMPRO0060Service {

	@Resource(name="nEDMPRO0060Dao")
	private NEDMPRO0060Dao	nEDMPRO0060Dao;

	@Autowired
	private PEDMPRO000Dao pEDMPRO000Dao;

	/**
	 * 상품기본정보 조회
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectImsiProduct(DataMap paramMap) throws Exception {
		return nEDMPRO0060Dao.selectImsiProduct(paramMap);
	}

	public int selectImsiProductCnt(DataMap paramMap) throws Exception {
		return nEDMPRO0060Dao.selectImsiProductCnt(paramMap);
	}

	public int selectCountVendorStopTrading(String vendorCode) throws Exception {
		return pEDMPRO000Dao.selectCountVendorStopTrading(vendorCode);
	}

	public NewProduct selectNewProductTradeType(String vendorCode) throws Exception {
		return pEDMPRO000Dao.selectNewProductTradeType(vendorCode);
	}

	public DataMap selectOnlineRepresentProdctInfo(DataMap paramMap) throws Exception {
		return nEDMPRO0060Dao.selectOnlineRepresentProdctInfo(paramMap);
	}

	/**
	 *  이미지 업로드용 상품명 조회
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectImsiProductNm(DataMap paramMap) throws Exception {
		return nEDMPRO0060Dao.selectImsiProductNm(paramMap);
	}

	/**
	 * 전상법 일괄업로드용 데이터 조회
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectBatchNorProdCode(DataMap paramMap) throws Exception {
		return nEDMPRO0060Dao.selectBatchNorProdCode(paramMap);
	}

	/**
	 * KC인증 일괄업로드용 데이터 조회
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectBatchKcProdCode(DataMap paramMap) throws Exception {
		return nEDMPRO0060Dao.selectBatchKcProdCode(paramMap);
	}

	public void insertProductInfo(DataMap paramMap) throws Exception {
		NewProduct newProduct = new NewProduct();
		String norProdSalePrc = paramMap.getString("NOR_PROD_SALE_PRC").replaceAll(",", "");
		paramMap.put("NOR_PROD_SALE_PRC", norProdSalePrc);

		newProduct.setTaxatDivCode(paramMap.getString("TAXAT_DIVN_CD"));
		newProduct.setNormalProductSalePrice(paramMap.getString("NOR_PROD_SALE_PRC"));
		newProduct.setProfitRate(paramMap.getString("PRFT_RATE"));

		String taxFreeProductCosts = pEDMPRO000Dao.selectNormalProductCode(newProduct);

		paramMap.put("NOR_PROD_PCOST", taxFreeProductCosts);

		paramMap.put("SELL_PRC", paramMap.getString("NOR_PROD_SALE_PRC"));//20190418 판매가 등록

		nEDMPRO0060Dao.insertProductInfo(paramMap);
	}

	public void insertProductItemInfo(DataMap paramMap) throws Exception {
		nEDMPRO0060Dao.insertProductItemInfo(paramMap);
	}
	
	public void insertEcProductCategory(DataMap paramMap) throws Exception {
		nEDMPRO0060Dao.insertEcProductCategory(paramMap);
	}
	
	public void insertEcProductAttribute(DataMap paramMap) throws Exception {
		nEDMPRO0060Dao.insertEcProductAttribute(paramMap);
	}

	public void saveProdAddDetail(DataMap paramMap) throws Exception {
		nEDMPRO0060Dao.saveProdAddDetail(paramMap);
	}

	public void saveProdCertDetail(DataMap paramMap) throws Exception {
		nEDMPRO0060Dao.saveProdCertDetail(paramMap);
	}

	public void updateNewProdRegDesc(DataMap paramMap) throws Exception {
		nEDMPRO0060Dao.updateNewProdRegDesc(paramMap);
	}

	public void saveNewProdDescr(DataMap paramMap) throws Exception {
		nEDMPRO0060Dao.saveNewProdDescr(paramMap);
	}

	/* ************************** */
	//20181002 상품키워드 입력 기능 추가

	@Override
	public List<DataMap> selectCodeInfo02() throws Exception {
		return nEDMPRO0060Dao.selectCodeInfo02();
	}

	@Override
	public List<DataMap> selectCodeInfo03() throws Exception {
		return nEDMPRO0060Dao.selectCodeInfo03();
	}

	//20181002 상품키워드 입력 기능 추가
	/* ************************** */
	
	public int selectEcStdDispMappingCnt(DataMap paramMap) throws Exception {
		return nEDMPRO0060Dao.selectEcStdDispMappingCnt(paramMap);
	}
	
	public List<DataMap> selectEcStdDispMappingList(DataMap paramMap) throws Exception {
		return nEDMPRO0060Dao.selectEcStdDispMappingList(paramMap);
	}
	
	public int selectEcStdAttrMappingCnt(DataMap paramMap) throws Exception {
		return nEDMPRO0060Dao.selectEcStdAttrMappingCnt(paramMap);
	}
	
	public List<DataMap> selectEcStdAttrMappingList(DataMap paramMap) throws Exception {
		return nEDMPRO0060Dao.selectEcStdAttrMappingList(paramMap);
	}
	
}
