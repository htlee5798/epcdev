package com.lottemart.epc.edi.product.dao;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lcnjf.util.StringUtil;
import com.lottemart.common.exception.AlertException;
import com.lottemart.epc.edi.product.model.ColorSize;
import com.lottemart.epc.edi.product.model.EcProductAttribute;
import com.lottemart.epc.edi.product.model.EcProductCategory;
import com.lottemart.epc.edi.product.model.NewProduct;
import com.lottemart.epc.edi.product.model.OfflineImage;
import com.lottemart.epc.edi.product.model.PEDMPRO0005VO;
import com.lottemart.epc.edi.product.model.PEDMPRO0009VO;
import com.lottemart.epc.edi.product.model.PEDMPRO0011VO;
import com.lottemart.epc.edi.product.model.SearchProduct;

/**
 * @Class Name : PEDMPRO000Dao.java
 * @Description : 신상품 등록 관련 dao 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 12. 03. 오후11:02:02 kks
 *
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Repository
public class PEDMPRO000Dao extends SqlMapClientDaoSupport {

	private static final Logger logger = LoggerFactory.getLogger(PEDMPRO000Dao.class);

	@Resource(name = "sqlMapClient")
    public void setSuperSqlMapClient(SqlMapClient sqlMapClient) {
        super.setSqlMapClient(sqlMapClient);
    }

	/**
	 * 상품테이블(TED_NEW_PROD_REG)에 NEW_PROD_CD칼럼(varchar 11)에 사용될 시퀀스 값 조회.
	 * @Method Name : selectNewProductCode
	 * @param
	 * @return String
	 * @throws DataAccessException
	 */
	public String selectNewProductCode() throws DataAccessException  {
		// TODO Auto-generated method stub
		return (String) getSqlMapClientTemplate().queryForObject("PEDMPRO001.getNewProductCode", null);
	}

	/**
	 * 상품 등록
	 * @Method Name : insertProductInfo
	 * @param  NewProduct
	 * @return void
	 * @throws DataAccessException
	 */
	public void insertProductInfo(NewProduct newProduct) throws DataAccessException {
		// 온오프 겸용 상품일 경우,
		if("0".equals(newProduct.getOnOffDivnCode()) ) {
			getSqlMapClientTemplate().insert("PEDMPRO001.insertNewOnOffProduct", newProduct);

		// 온오프 겸용이 아닌 온라인 전용, 소셜 상품 등록 일 경우
		} else {
			getSqlMapClientTemplate().insert("PEDMPRO001.insertNewOnlineProduct", newProduct);
		}
	}


	/**
	 * 패션 상품 색상 / 사이즈 정보 저장
	 * @Method Name : insertProductColorSizeInfo
	 * @param  newProduct
	 * @return void
	 * @throws DataAccessException
	 */
	@SuppressWarnings("rawtypes")
	public void insertProductColorSizeInfo(NewProduct newProduct) throws DataAccessException {
		// TODO Auto-generated method stub
		Iterator colorSizeIter = newProduct.getColorSizeList().iterator();
		while(colorSizeIter.hasNext()) {
			ColorSize argColorSize = (ColorSize)colorSizeIter.next();

			//협력업체 코드
			argColorSize.setEnterpriseCode(newProduct.getEntpCode());
			//상품명 varchar(50)
			argColorSize.setProductName(newProduct.getProductName());
			//색상 코드
			String leftpadedColorCode = StringUtils.leftPad(argColorSize.getColorCode(), 3, "0");
			//상품 코드 varchar(19(
			argColorSize.setNewProductCode(newProduct.getNewProductCode());
			//pog image id
			argColorSize.setProductImageId(newProduct.getProductImageId()+leftpadedColorCode);

			getSqlMapClientTemplate().insert("PEDMPRO001.insertFashionProductColorSize", argColorSize);
		}
	}


	/**
	 * 패션상품 색상, 사이즈 조회
	 * @Method Name : selectProductColorList
	 * @param  newProductCode
	 * @return ArrayList<String>
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	//public ArrayList<String> selectProductColorList(String newProductCode) throws DataAccessException {
	public List<String> selectProductColorList(String newProductCode) throws DataAccessException {
		// TODO Auto-generated method stub
		//return (ArrayList<String>)getSqlMapClientTemplate().queryForList("PEDMPRO001.selectProductColorList", newProductCode);
		return (List<String>)getSqlMapClientTemplate().queryForList("PEDMPRO001.selectProductColorList", newProductCode);
	}

	@SuppressWarnings("unchecked")
	public List<String> selectProductColorListOld(String newProductCode) throws DataAccessException {
		return (List<String>)getSqlMapClientTemplate().queryForList("PEDMPRO001.selectProductColorList", newProductCode);
	}

	/**
	 * 상품목록 조회
	 * @Method Name : selectNewProductList
	 * @param  searchProduct
	 * @return List<NewProduct>
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<NewProduct> selectNewProductList(SearchProduct searchProduct) throws DataAccessException  {
		// 온오프 겸용 상품 일 경우,
		if("0".equals(searchProduct.getOnOffDivnCode())) {
			if("1".equals(searchProduct.getProductDivnCode())){
				return (List<NewProduct>)getSqlMapClientTemplate().queryForList("PEDMPRO001.selectNewProductListOnOff", searchProduct);
			}else{
				return (List<NewProduct>)getSqlMapClientTemplate().queryForList("PEDMPRO001.selectNewProductListOnOffFassion", searchProduct);
			}
		// 온라인 전용, 소셜 상품일 경우
		} else {
			return (List<NewProduct>)getSqlMapClientTemplate().queryForList("PEDMPRO001.selectNewProductListOnline", searchProduct);
		}
	}

	/**
	 * 전체 상품목록 조회. 온오프 겸용만 해당됨.
	 * @Method Name : selectWholeProductList
	 * @param  searchProduct
	 * @return List<PEDMPRO0005VO>
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<PEDMPRO0005VO> selectWholeProductList(SearchProduct searchProduct) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<PEDMPRO0005VO>)getSqlMapClientTemplate().queryForList("PEDMPRO001.selectWholeProductList", searchProduct);
	}

	/**
	 * 점포멸  상품목록 조회. 온오프 겸용만 해당됨.
	 * @Method Name : selectWholeProductList
	 * @param  searchProduct
	 * @return List<PEDMPRO0005VO>
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<PEDMPRO0009VO> selectWholeStoreProductList(SearchProduct searchProduct) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<PEDMPRO0009VO>)getSqlMapClientTemplate().queryForList("PEDMPRO001.selectWholeStoreProductList", searchProduct);
	}



	@SuppressWarnings("unchecked")
	public List<PEDMPRO0005VO> selectWholeProductListExcel(SearchProduct searchProduct) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<PEDMPRO0005VO>)getSqlMapClientTemplate().queryForList("PEDMPRO001.selectWholeProductListExcel", searchProduct);
	}

	@SuppressWarnings("unchecked")
	public List<PEDMPRO0009VO> selectWholeStoreStoreProductListExcel(SearchProduct searchProduct) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<PEDMPRO0009VO>)getSqlMapClientTemplate().queryForList("PEDMPRO001.selectWholeStoreProductListExcel", searchProduct);
	}



	/**
	 * 온라인 대표상품 코드, 명, 이익률, 88코드 조회
	 * @Method Name : selectOnlineRepresentProductList
	 * @param  entpCode(협럭업체코드)
	 * @return List<NewProduct>
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<NewProduct> selectOnlineRepresentProductList(String entpCode) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<NewProduct>)getSqlMapClientTemplate()
			.queryForList("PEDMPRO001.selectOnlineRepresentProdctList", entpCode);
	}

	@SuppressWarnings("unchecked")
	public List<NewProduct> selectOnlineRepresentProductListOld(String entpCode) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<NewProduct>)getSqlMapClientTemplate()
			.queryForList("PEDMPRO001.selectOnlineRepresentProdctListOld", entpCode);
	}

	/**
	 * 상품 상세정보 조회
	 * @Method Name : selectNewProductInfo
	 * @param  newProductCode(가등록 된 상품코드)
	 * @return NewProduct
	 * @throws DataAccessException
	 */
	public NewProduct selectNewProductInfo(String newProductCode) throws DataAccessException  {
		// TODO Auto-generated method stub
		return  (NewProduct) getSqlMapClientTemplate().queryForObject("PEDMPRO001.selectNewProductInfo", newProductCode);
	}

	/**
	 * 상품 입력
	 * @Method Name : insertProductDescription
	 * @param  NewProduct
	 * @return void
	 * @throws DataAccessException
	 */
	public void insertProductDescription(NewProduct newProduct) throws DataAccessException {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().insert("PEDMPRO001.insertProductDescription", newProduct);
	}






	/**
	 * 패션상품 색상 / 사이즈 정보 삭제
	 * @Method Name : deleteProductColorSizeList
	 * @param  newProductCode (가등록 상품코드)
	 * @return void
	 * @throws DataAccessException
	 */
	public void deleteProductColorSizeList(String newProductCode) throws DataAccessException{
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().delete("PEDMPRO001.deleteFashionProductColorSize", newProductCode);
	}

	/**
	 * 임시보관함에서 선택한 상품 삭제
	 * @Method Name : deleteNewProductInTemp
	 * @param  productCode (가등록 상품코드)
	 * @return void
	 * @throws DataAccessException
	 */
	public void deleteNewProductInTemp(String productCode) {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().delete("PEDMPRO001.deleteNewProductInTemp", productCode);
	}

	/**
	 * 임시보관함에서 선택한 상품 삭제
	 * @Method Name : deleteNewProductInTemp
	 * @param  productCode (가등록 상품코드)
	 * @return void
	 * @throws DataAccessException
	 */
	public void deleteNewProductEcomAddInfoTemp(String productCode) {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().delete("PEDMPRO001.deleteNewProductEcomAddInfoTemp", productCode);
	}




	/**
	 * 사용자가 입력한 88코드 중복여부 조회
	 * @Method Name : selectSellCodeCount
	 * @param  sellCode
	 * @return 1:중복, 0:중복아님
	 * @throws DataAccessException
	 */
	public Integer selectSellCodeCount(String sellCode) {
		// TODO Auto-generated method stub
		return (Integer) getSqlMapClientTemplate().queryForObject("PEDMPRO001.selectCountSellCodeDuplicate", sellCode);
	}

	/**
	 * 협력업체의 거래 중지여부 조회
	 * @Method Name : selectCountVendorStopTrading
	 * @param  vendorCode(협력업체코드)
	 * @return Integer
	 * @throws DataAccessException
	 */
	public Integer selectCountVendorStopTrading(String vendorCode) {
		// TODO Auto-generated method stub
		return (Integer) getSqlMapClientTemplate().queryForObject("PEDMPRO001.selectCountVendorStopTrading", vendorCode);
	}


	public Integer selectCountVendorStopTradingOld(String vendorCode) {
		// TODO Auto-generated method stub
		return (Integer) getSqlMapClientTemplate().queryForObject("PEDMPRO001.selectCountVendorStopTradingOld", vendorCode);
	}

	/**
	 * 해당 협력업체의 거래 유형 조회. 1:직매입,  2:특정1, 3:임대(을), 4:특정2, 5:임대(갑)
	 * @Method Name : selectNewProductTradeType
	 * @param  entpCode (협력업체코드)
	 * @return NewProduct (거래 유형, 권장금액 구분 정보만 조회됨)
	 * @throws DataAccessException
	 */
	public NewProduct selectNewProductTradeType(String entpCode) throws DataAccessException  {
		// TODO Auto-generated method stub
		return  (NewProduct) getSqlMapClientTemplate().queryForObject("PEDMPRO001.selectVendorTradeType", entpCode);
	}

	public NewProduct selectNewProductTradeTypeOld(String entpCode) throws DataAccessException  {
		// TODO Auto-generated method stub
		return  (NewProduct) getSqlMapClientTemplate().queryForObject("PEDMPRO001.selectVendorTradeTypeOld", entpCode);
	}

	/**
	 * 해당 협력업체의 면과세 정보 조회. 1:면세,   2:과세, 3:영세
	 * @Method Name : selectVendorTaxDivnCode
	 * @param  entpCode ( 협력업체코드 )
	 * @return String
	 * @throws DataAccessException
	 */
	public String selectVendorTaxDivnCode(String entpCode) throws DataAccessException {
		// TODO Auto-generated method stub
		return  (String) getSqlMapClientTemplate().queryForObject("PEDMPRO001.selectVendorTaxType", entpCode);
	}

	public String selectVendorTaxDivnCodeOld(String entpCode) throws DataAccessException {
		// TODO Auto-generated method stub
		return  (String) getSqlMapClientTemplate().queryForObject("PEDMPRO001.selectVendorTaxTypeOld", entpCode);
	}

	/**
	 * 해당 협력업체의 전체 등록 상품 갯수
	 * @Method Name : selectTotalProductCount
	 * @param  SearchProduct ( 협력업체코드 )
	 * @return Integer
	 * @throws DataAccessException
	 */
	public Integer selectTotalProductCount(SearchProduct searchParam) throws DataAccessException  {
		// TODO Auto-generated method stub
		return (Integer) getSqlMapClientTemplate().queryForObject("PEDMPRO001.selectWholeProductCount", searchParam);
	}

	public Integer selectTotalStoreProductCount(SearchProduct searchParam) throws DataAccessException  {
		// TODO Auto-generated method stub
		return (Integer) getSqlMapClientTemplate().queryForObject("PEDMPRO001.selectWholeStoreProductCount", searchParam);
	}
	/**
	 * 거래형태별(1) 이익율계산 (면세:1 , 과세:2 )
	 * @Method Name : selectProfitRate
	 * @param  NewProduct
	 * @return String
	 * @throws DataAccessException
	 */
	public String selectProfitRate(NewProduct tmpProduct)  throws DataAccessException  {
		return (String) getSqlMapClientTemplate().queryForObject("PEDMPRO001.selectProfitRate", tmpProduct);
	}

	/**
	 * 거래형태별(1) VIC 이익율계산 (면세:1 , 과세:2 )
	 * @Method Name : selectProfitRate
	 * @param  NewProduct
	 * @return String
	 * @throws DataAccessException
	 */
	public String selectProfitRatevic(NewProduct tmpProduct)  throws DataAccessException  {
		return (String) getSqlMapClientTemplate().queryForObject("PEDMPRO001.selectProfitRatevic", tmpProduct);
	}


	/**
	 * 거래형태별(2) 원가계산 (면세:1 , 과세:2 )
	 * @Method Name : selectNormalProductCode
	 * @param  NewProduct
	 * @return String
	 * @throws DataAccessException
	 */
	public String selectNormalProductCode(NewProduct tmpProduct) throws DataAccessException  {
		return (String) getSqlMapClientTemplate().queryForObject("PEDMPRO001.selectNormalProductCost", tmpProduct);
	}

	/**
	 * 거래형태별(2) vic 원가계산 (면세:1 , 과세:2 )
	 * @Method Name : selectNormalProductCodevic
	 * @param  NewProduct
	 * @return String
	 * @throws DataAccessException
	 */
	public String selectNormalProductCodevic(NewProduct tmpProduct) throws DataAccessException  {
		return (String) getSqlMapClientTemplate().queryForObject("PEDMPRO001.selectNormalProductCostvic", tmpProduct);
	}






	/**
	 * 쇼설일경우 패션 상품 색상 / 사이즈 정보 강제생성
	 * @Method Name : insertProductColorSizeInfo
	 * @param  newProduct
	 * @return void
	 * @throws DataAccessException
	 */
	public void insertProductColorSizeTemp(NewProduct newProduct) throws DataAccessException {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().insert("PEDMPRO001.insertFashionProductColorSizeTemp", newProduct);

	}

	/**
	 * 온라인전용 단품 등록
	 * @Method Name : insertProductItemInfo
	 * @param  newProduct
	 * @return void
	 * @throws DataAccessException
	 */
	@SuppressWarnings("rawtypes")
	public void insertProductItemInfo(NewProduct newProduct) throws DataAccessException {
		// TODO Auto-generated method stub
		Iterator itemIter = newProduct.getItemList().iterator();
		while(itemIter.hasNext()) {
			ColorSize argItem = (ColorSize)itemIter.next();

			//협력업체 코드
			argItem.setEnterpriseCode(newProduct.getEntpCode());
			//상품명 varchar(50)
			argItem.setProductName(newProduct.getProductName());
			//상품 코드 varchar(19(
			argItem.setNewProductCode(newProduct.getNewProductCode());

			getSqlMapClientTemplate().insert("PEDMPRO001.insertProductItemInfo", argItem);
		}

	}

	/**
	 * 온라인전용 단품 수정
	 * @Method Name : updateProductItemInfo
	 * @param  newProduct
	 * @return void
	 * @throws DataAccessException
	 */
	@SuppressWarnings("rawtypes")
	public void updateProductItemInfo(NewProduct newProduct) throws DataAccessException {
		// TODO Auto-generated method stub
		Iterator itemIter = newProduct.getItemList().iterator();
		while(itemIter.hasNext()) {
			ColorSize argItem = (ColorSize)itemIter.next();

			//협력업체 코드
			argItem.setEnterpriseCode(newProduct.getEntpCode());
			//상품명 varchar(50)
			argItem.setProductName(newProduct.getProductName());
			//상품 코드 varchar(19(
			argItem.setNewProductCode(newProduct.getNewProductCode());

			getSqlMapClientTemplate().insert("PEDMPRO001.insertProductItemInfo", argItem);
		}

	}




	/**
	 * 온라인/오프라인 상품현황에서 이미지 등록/수정 POPUP 실행시 MD이미지 정보 조회
	 * @Method Name : selectProductColorSizeImage
	 * @param  newProduct
	 * @return void
	 * @throws DataAccessException
	 */
	public OfflineImage selectProductColorSizeImage(Map<String,Object> map) throws DataAccessException {
		// TODO Auto-generated method stub
		return (OfflineImage) getSqlMapClientTemplate().queryForObject("PEDMPRO001.selectProductColorSizeImage", map);

	}

	/**
	 * 온라인/오프라인 상품현황  MD이미지 정보 update
	 * @Method Name : selectProductColorSizeImage
	 * @param  newProduct
	 * @return void
	 * @throws DataAccessException
	 */
	public void insertProductColorSizeImage(Map<String,Object> map) throws DataAccessException {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().insert("PEDMPRO001.insertProductColorSizeImage", map);
	}

	/**
	 * 온라인/오프라인 상품현황  MD이미지 정보 delete (등록전 삭제처리후 진행함.)
	 * @Method Name : selectProductColorSizeImage
	 * @param  newProduct
	 * @return void
	 * @throws DataAccessException
	 */
	public void deleteProductColorSizeImage(Map<String,Object> map) throws DataAccessException {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().delete("PEDMPRO001.deleteProductColorSizeImage", map);
	}


	/**
	 * 온라인/오프라인 상품현황  MD color/size 정보 조회
	 * @Method Name : selectProductColorSizeList
	 * @param  newProduct
	 * @return void
	 * @throws DataAccessException
	 */
	public List<ColorSize> selectProductColorSizeList(Map<String,Object> map)  throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<ColorSize>)getSqlMapClientTemplate().queryForList("PEDMPRO001.selectProductFashColorSz", map);
	}

	public void updateNewprodOnimgCnt(HashMap<String,Object> map) throws Exception{
		getSqlMapClientTemplate().update("PEDMPRO001.PRODUCT-ONIMG-CNT_UPDATE01",map);
	}

	public void deleteProdAddDetail(NewProduct newProduct) throws DataAccessException  {
		getSqlMapClientTemplate().update("PEDMPRO001.PRODUCT-PROD-ADD_DELETE",newProduct);
	}

	public void insertProdAddDetail(NewProduct newProduct) throws DataAccessException  {
		getSqlMapClientTemplate().insert("PEDMPRO001.PRODUCT-PROD-ADD_INSERT",newProduct);
	}

	public void deleteProdCertDetail(NewProduct newProduct) throws DataAccessException  {
		getSqlMapClientTemplate().update("PEDMPRO001.PRODUCT-PROD-CERT_DELETE",newProduct);
	}

	public void insertProdCertDetail(NewProduct newProduct) throws DataAccessException  {
		getSqlMapClientTemplate().insert("PEDMPRO001.PRODUCT-PROD-CERT_INSERT",newProduct);
	}

	/**
	 * 온라인전용상품 단품 정보 삭제
	 * @Method Name : deleteTempProductItem
	 * @param  newProductCode, itemCode
	 * @return void
	 * @throws DataAccessException
	 */
	public void deleteTempProductItem(Map<String,Object> map) throws DataAccessException {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().delete("PEDMPRO001.deleteTempProductItem", map);
	}

	/**
	 * 온라인전용상품 단품 정보 전부 삭제
	 * @Method Name : deleteTempProductItemAll
	 * @param  newProductCode
	 * @return void
	 * @throws DataAccessException
	 */
	public void deleteTempProductItemAll(String newProductCode) throws DataAccessException {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().delete("PEDMPRO001.deleteTempProductItemAll", newProductCode);
	}


	/**
	 * 구 EDI 신상품등록현황 조회
	 * @param searchProduct
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<NewProduct> selectNewProductListOld(SearchProduct searchProduct) throws DataAccessException  {
		// 온오프 겸용 상품 일 경우,
		if("0".equals(searchProduct.getOnOffDivnCode())) {
			if("1".equals(searchProduct.getProductDivnCode())){
				return (List<NewProduct>)getSqlMapClientTemplate().queryForList("PEDMPRO001.selectNewProductListOnOffOld", searchProduct);
			}else{
				return (List<NewProduct>)getSqlMapClientTemplate().queryForList("PEDMPRO001.selectNewProductListOnOffFassionOld", searchProduct);
			}
		// 온라인 전용, 소셜 상품일 경우
		} else {
			return (List<NewProduct>)getSqlMapClientTemplate().queryForList("PEDMPRO001.selectNewProductListOnlineOld", searchProduct);
		}
	}

	//20180904 - 상품키워드 입력 기능 추가
	/**
	 * 신규 상품키워드 조회
	 * @param newProductCode
	 * @return LIST
	 * @throws Exception
	 */
	public List<PEDMPRO0011VO> selectTpcPrdKeywordList(String newProductCode)  throws Exception {
		return (List<PEDMPRO0011VO>)getSqlMapClientTemplate().queryForList("PEDMPRO001.selectTpcPrdKeywordList", newProductCode);
	}

	/**
	 * 신규 상품키워드 등록/수정
	 * @param VO
	 * @return INT
	 * @throws Exception
	 */
	public int insertUpdateTpcPrdKeyword(PEDMPRO0011VO bean) throws Exception {
		return getSqlMapClientTemplate().update("PEDMPRO001.insertUpdateTpcPrdKeyword", bean);
	}

	/**
	 * 신규 상품키워드 등록
	 * @param VO
	 * @return INT
	 * @throws Exception
	 */
	public int insertTpcPrdKeyword(PEDMPRO0011VO bean) throws Exception {
		return getSqlMapClientTemplate().update("PEDMPRO001.insertTpcPrdKeyword", bean);
	}

	/**
	 * 신규 상품키워드 삭제
	 * @param MAP
	 * @return INT
	 * @throws Exception
	 */
	public int deleteTpcPrdKeyword(Map<String,Object> map) throws Exception {
		return getSqlMapClientTemplate().update("PEDMPRO001.deleteTpcPrdKeyword", map);
	}

	/**
	 * 신규 상품키워드 전체삭제
	 */

	public int deleteTpcPrdAllKeyword(String pgmId) throws Exception{
		return getSqlMapClientTemplate().delete("PEDMPRO001.deleteTpcPrdAllKeyword", pgmId);
	}

	/**
	 * 신규 상품키워드 상품테이블 전체삭제
	 */

	public int deleteTpcNewPrdAllKeyword(String pgmId) throws Exception{
		return getSqlMapClientTemplate().delete("PEDMPRO001.deleteTpcNewPrdAllKeyword", pgmId);
	}

	/**
	 * 신규 상품키워드 전체 조회
	 * @param VO
	 * @return LIST
	 * @throws Exception
	 */
	public List<PEDMPRO0011VO> selectChkTpcPrdTotalKeyword(PEDMPRO0011VO bean) throws Exception {
		return (List<PEDMPRO0011VO>) getSqlMapClientTemplate().queryForList("PEDMPRO001.selectChkTpcPrdTotalKeyword",bean);
	}

	/**
	 * 신규 상품키워드 전체 등록
	 * @param VO
	 * @return INT
	 * @throws Exception
	 */
	public int insertTpcPrdTotalKeyword(PEDMPRO0011VO bean) throws Exception {
		return getSqlMapClientTemplate().update("PEDMPRO001.insertTpcPrdTotalKeyword", bean);
	}

	/**
	 * 신규 상품키워드 전체 수정
	 * @param VO
	 * @return INT
	 * @throws Exception
	 */
	public int updateTpcPrdTotalKeyword(PEDMPRO0011VO bean) throws Exception {
		return getSqlMapClientTemplate().update("PEDMPRO001.updateTpcPrdTotalKeyword",bean);
	}

	/**
	 * 신규 상품키워드 등록/수정 및 상품키워드 전체(SEQ:000) 등록/수정/삭제
	 * @Method Name : mergeTpcPrdKeyword
	 * @param  newProduct
	 * @return void
	 */
	@SuppressWarnings("rawtypes")
	public void mergeTpcPrdKeyword(NewProduct newProduct) {

		int resultCnt = 0;

		String pgmId = "";
		String regId = "";

		try {
			Iterator kewordIter = newProduct.getKeywordList().iterator();

			while(kewordIter.hasNext()) {
				PEDMPRO0011VO pedmpro0011VO = (PEDMPRO0011VO)kewordIter.next();

				pgmId = pedmpro0011VO.getPgmId();
				regId = pedmpro0011VO.getRegId();
				// 키워드 입력
				resultCnt = this.insertUpdateTpcPrdKeyword(pedmpro0011VO);
			}

			if (resultCnt <= 0) {
				throw new AlertException("키워드 입력 작업중에 오류가 발생하였습니다.");
			}

			PEDMPRO0011VO bean = new PEDMPRO0011VO();

			if(!pgmId.equals("") && !regId.equals("")) {
				bean.setPgmId(pgmId);
				bean.setRegId(regId);
			}

			// 전체 키워드 셋팅
			List<PEDMPRO0011VO> prdList = this.selectChkTpcPrdTotalKeyword(bean);

			if(!(prdList == null || prdList.size() == 0)) {
				PEDMPRO0011VO resultBean = (PEDMPRO0011VO)prdList.get(0);
	        	String totalKywrd = resultBean.getTotalKywrd();
	        	String seq = resultBean.getSeq();

	        	bean.setTotalKywrd(totalKywrd);

	        	if("000".equals(seq)) {
					//전체 키워드 업데이트
					resultCnt = this.updateTpcPrdTotalKeyword(bean);

					if(resultCnt <= 0) {
						throw new AlertException("전체 키워드 수정 작업중에 오류가 발생하였습니다.");
					}
				} else {
					//전체 키워드 입력
					resultCnt = this.insertTpcPrdTotalKeyword(bean);

					if(resultCnt <= 0) {
						throw new AlertException("전체 키워드 입력 작업중에 오류가 발생하였습니다.");
					}
				}
			}
		} catch(Exception e) {
			logger.error(e.getMessage());
		}

	}



	/**
	 * 상품별 EC 카테고리 정보 삭제
	 */

	public int deleteEcProductCategory(String pgmId) throws DataAccessException {
		return getSqlMapClientTemplate().delete("PEDMPRO001.deleteEcProductCategory", pgmId);
	}

	/**
	 * 상품별 EC 카테고리 정보 등록
	 * @param VO
	 * @return INT
	 * @throws Exception
	 */
	public void insertEcProductCategory(EcProductCategory bean) throws DataAccessException {
		getSqlMapClientTemplate().insert("PEDMPRO001.insertEcProductCategory", bean);
	}


	/**
	 * 상품/단품별 EC 속성매핑 정보 삭제
	 */

	public int deleteEcProductAttribute(String pgmId) throws DataAccessException {
		return getSqlMapClientTemplate().delete("PEDMPRO001.deleteEcProductAttribute", pgmId);
	}

	/**
	 * 상품/단품별 EC 속성매핑
	 * @param VO
	 * @return INT
	 * @throws Exception
	 */
	public void insertEcProductAttribute(NewProduct newProduct) throws DataAccessException {

		Iterator itemIter = newProduct.getAttrbuteList().iterator();
		while(itemIter.hasNext()) {
			EcProductAttribute ecProdAttr = (EcProductAttribute)itemIter.next();

			ecProdAttr.setRegId(newProduct.getEntpCode());
			ecProdAttr.setProdCd(newProduct.getNewProductCode());
			ecProdAttr.setPgmId(newProduct.getNewProductCode());

			getSqlMapClientTemplate().insert("PEDMPRO001.insertEcProductAttribute", ecProdAttr);
		}
	}

}
