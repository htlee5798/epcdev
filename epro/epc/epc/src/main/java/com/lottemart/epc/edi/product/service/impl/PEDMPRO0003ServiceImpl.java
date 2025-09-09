package com.lottemart.epc.edi.product.service.impl;

import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.common.util.SecureUtil;
import com.lottemart.epc.edi.comm.model.Constants;
import com.lottemart.epc.edi.product.dao.PEDMPRO0003Dao;
import com.lottemart.epc.edi.product.dao.PEDMPRO000Dao;
import com.lottemart.epc.edi.product.dao.NEDMPRO0020Dao;
import com.lottemart.epc.edi.product.model.ColorSize;
import com.lottemart.epc.edi.product.model.EcProductCategory;
import com.lottemart.epc.edi.product.model.EcomAddInfo;


import com.lottemart.epc.edi.product.model.NewProduct;
import com.lottemart.epc.edi.product.model.SearchProduct;
import com.lottemart.epc.edi.product.service.PEDMPRO0003Service;


/**
 * @Class Name : PEDMPRO0003ServiceImpl
 * @Description : 임시 보관함  ServiceImpl 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 12. 03. 오전 11:37:05 kks
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Service("pEDMPRO0003Service")
public class PEDMPRO0003ServiceImpl implements PEDMPRO0003Service {

	@Autowired
	private PEDMPRO0003Dao pEDMPRO0003Dao;

	@Autowired
	private PEDMPRO000Dao pEDMPRO000Dao;

	@Autowired
	private NEDMPRO0020Dao nEDMPRO0020Dao;

	private static final Logger logger = LoggerFactory.getLogger(PEDMPRO0003ServiceImpl.class);

	/**
	 * Desc : [온오프/온라인전용/쇼셜상품수정] 상품정보 수정. 온오프 겸용 일 경우에만 거래 유형에 따라 이익률, 정상원가 계산
	 * @Method Name : updateNewProductInfoInTemp
	 * @param NewProduct
	 * @return 
	 */
	public void updateNewProductInfoInTemp(NewProduct newProductInTemp) {
		//현재 상품의 거래 정보 조회
		NewProduct tmpProduct = pEDMPRO000Dao.selectNewProductTradeType(newProductInTemp.getEntpCode());
		int retCnt = 0;

		//상품 상세성명 정보 확인
		try {
			retCnt = nEDMPRO0020Dao.selectProductDescription(newProductInTemp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}

		//온오프 겸용 상품에 한해서만
		//장려금 구분(promotionAmountFlag : String) - 0:미적용, 1:적용, 
		//거래 유형 (tradeTypeCode : String)        - 1:직매입,  2:특정1, 3:임대(을), 4:특정2, 5:임대(갑)
		//면과세 구 분 (taxatDivCode : String)       - 1:면세,   2:과세, 3:영세
		if (Constants.ONLINE_OFFLINE_PRODUCT_CD.equals(newProductInTemp.getOnOffDivnCode())) {
			if (StringUtils.isEmpty(newProductInTemp.getProfitRate())) {
				newProductInTemp.setProfitRate("0");
			}

			//권장금액(장려금) 구분 코드 값 설정
			if (!"0".equals(tmpProduct.getPromotionAmountFlag())) {
				newProductInTemp.setPromotionAmountFlag("1");
			} else {
				newProductInTemp.setPromotionAmountFlag(tmpProduct.getPromotionAmountFlag());
			}

			//상품 유형구분이 PB(2)인 경우
			if ("2".equals(newProductInTemp.getProductTypeDivnCode())) {
				newProductInTemp.setPromotionAmountFlag("0");
			}

			//해당 협력 업체 코드로 조회한 거래 유형값 설정
			newProductInTemp.setTradeTypeCode(tmpProduct.getTradeTypeCode());

			//정상매가
//			Double salePrice = new Double(newProductInTemp.getNormalProductSalePrice());
			//정상원가
//			Double normalCost = new Double(StringUtils.defaultIfEmpty(newProductInTemp.getNormalProductCost(), "0"));
			
			//거래 유형이 직매입(1)이고
			if ("1".equals(tmpProduct.getTradeTypeCode())) {

				//--[직매입시 면세/과세 별 이익율 계산 ]-----------------------------------
				String profitRateLong = pEDMPRO000Dao.selectProfitRate(newProductInTemp);
				newProductInTemp.setProfitRate(profitRateLong);
				//-----------------------------------------------------------------
				//--[VIC 직매입시 면세/과세 별 이익율 계산 ]-----------------------------------
				String profitRatevicLong = pEDMPRO000Dao.selectProfitRatevic(newProductInTemp);
				newProductInTemp.setVicprofitRate(profitRatevicLong);

			//거래 유형이 직매입(1) 이 아닌 경우	
			} else {
				
				//Double profitRate = new Double(newProductInTemp.getProfitRate());
				//int normalIntCost = 0;

				//--[과세일경우 원가계산 ]-----------------------------------
				String taxFreeProductCosts = pEDMPRO000Dao.selectNormalProductCode(newProductInTemp);
				newProductInTemp.setNormalProductCost(taxFreeProductCosts);
				//--[VIC 과세일경우 원가계산 ]-----------------------------------
				String victaxFreeProductCosts = pEDMPRO000Dao.selectNormalProductCodevic(newProductInTemp);
				newProductInTemp.setVicnormalProductCost(victaxFreeProductCosts);
				//-------------------------------------------------------

			}

		//온라인전용, 소셜 상품인 경우
		} else {
			//정상매가
			// Double salePrice = new Double(newProductInTemp.getNormalProductSalePrice());
			//대표상품코드 이익률
			// Double profitRate = new Double(newProductInTemp.getProfitRate());
			// int normalIntCost = 0;

			//String kkk=newProductInTemp.getNormalProductSalePrice();
			//String kkk2=newProductInTemp.getProfitRate();

			//면과세 구분이 과세(2)가 아닌경우. 과세가 아닌경우는 현재 계산 값을 1.1로 한번더 나눠주면 된다. 
			//Double normalProductCost = salePrice  - salePrice * profitRate  / 100;
			// BigDecimal bigDecimalProductCost = new BigDecimal(newProductInTemp.getNormalProductSalePrice()).subtract(new BigDecimal(newProductInTemp.getNormalProductSalePrice()).multiply(new BigDecimal(newProductInTemp.getProfitRate())).divide(new BigDecimal("100")));
			// long taxFreeProductCost = bigDecimalProductCost.longValue();
			// newProductInTemp.setNormalProductCost(Long.toString(taxFreeProductCost));

			String taxFreeProductCosts = pEDMPRO000Dao.selectNormalProductCode(newProductInTemp);
			newProductInTemp.setNormalProductCost(taxFreeProductCosts);	

			//면과세 구분이 과세(2)인 경우
			//	if("2".equals(newProductInTemp.getTaxatDivCode())) {
			//		BigDecimal productCostTax = bigDecimalProductCost.divide(new BigDecimal("1.1"),2, BigDecimal.ROUND_UP);
			//		long taxPlusProductCost = productCostTax.longValue();	
			//		newProductInTemp.setNormalProductCost(Long.toString(taxPlusProductCost));
			//	} 
			
			// 온라인전용 재고관련 정보 입력
			//if("1".equals(newProductInTemp.getOnOffDivnCode()) ){
			pEDMPRO000Dao.deleteTempProductItemAll(newProductInTemp.getNewProductCode());
			if (newProductInTemp.getAttrPiType() != null && "I".equals(newProductInTemp.getAttrPiType()) && newProductInTemp.getItemList().size() > 0) {
				pEDMPRO000Dao.updateProductItemInfo(newProductInTemp);
			} else {
				//패션일때
				// 온라인전용, 쇼설일경우 패션상품 color_size 더미 data 생성해야함.
				if ("5".equals(newProductInTemp.getProductDivnCode())) {
					pEDMPRO000Dao.insertProductColorSizeTemp(newProductInTemp);
				}
			}

			//}
			
			//20180904 - 상품키워드 입력 기능 추가
			//상품키워드
			if(newProductInTemp.getKeywordList().size() > 0) {
				pEDMPRO000Dao.mergeTpcPrdKeyword	(newProductInTemp);
			}

			pEDMPRO000Dao.deleteEcProductAttribute(newProductInTemp.getNewProductCode());
			if (newProductInTemp.getAttrbuteList().size() > 0) {
				for (int i = 0; i < newProductInTemp.getAttrbuteList().size(); i++) {
					if (newProductInTemp.getAttrbuteList().get(i).getAttrId() == null || "".equals(newProductInTemp.getAttrbuteList().get(i).getAttrId())) {
						throw new IllegalArgumentException("속성유형값을 입력해주세요.");
					}
				}
				pEDMPRO000Dao.insertEcProductAttribute(newProductInTemp);
			}

			pEDMPRO000Dao.deleteEcProductCategory(newProductInTemp.getNewProductCode());
			if (!StringUtils.isEmpty(newProductInTemp.getDispCatCd())) {
				EcProductCategory ecProductCategory = new EcProductCategory();
				ecProductCategory.setProdCd(newProductInTemp.getNewProductCode());
				ecProductCategory.setPgmId(newProductInTemp.getNewProductCode());
				ecProductCategory.setRegId(newProductInTemp.getEntpCode());
				ecProductCategory.setStdCatCd(newProductInTemp.getStdCatCd());
				String[] dispCatArr = newProductInTemp.getDispCatCd().split(",");
				for (int i = 0; i < dispCatArr.length; i++) {
					ecProductCategory.setDispCatCd(dispCatArr[i]);
					pEDMPRO000Dao.insertEcProductCategory(ecProductCategory);
				}
			}
		}

		//상품 정보 수정
		//온오프 겸용 상품일때 
		if (Constants.ONLINE_OFFLINE_PRODUCT_CD.equals(newProductInTemp.getOnOffDivnCode())) {
			newProductInTemp.setProductHorizontalLength(SecureUtil.sqlValid(newProductInTemp.getProductHorizontalLength()));
			newProductInTemp.setProductVerticalLength(SecureUtil.sqlValid(newProductInTemp.getProductVerticalLength()));
			newProductInTemp.setProductHeight(SecureUtil.sqlValid(newProductInTemp.getProductHeight()));
			pEDMPRO0003Dao.updateOnoffNewProductInfoInTemp(newProductInTemp);
			//온라인 전용, 소셜 상품일때
		} else {
			pEDMPRO0003Dao.updateOnlineSocialNewProductInfoInTemp(newProductInTemp);
		}

		//상품 상세 테이블(TED_NEW_PROD_DESCR)수정
		//온오프 겸용 상품일때 
		if (Constants.ONLINE_OFFLINE_PRODUCT_CD.equals(newProductInTemp.getOnOffDivnCode())) {
			pEDMPRO0003Dao.updateNewProductDescriptionInTemp(newProductInTemp);
			//온라인 전용, 소셜 상품일때	
		} else {

			//상품상세 정보가 있을때
			if (retCnt > 0) {
				pEDMPRO0003Dao.updateTitleProductDescription(newProductInTemp);
			}
		}
	}

	/**
	 * Desc : 임시 보관함 상품 상세 정보 조회
	 * @Method Name : selectNewProductInfoInTemp
	 * @param newProductCode 상품 가등록 코드
	 * @return NewProduct
	 */
	public NewProduct selectNewProductInfoInTemp(String newProductCode) {
		return pEDMPRO0003Dao.selectNewProductInfoInTemp(newProductCode);
	}

	/**
	 * 구 EDI 임시보관함, 신규상품등록현황 조회 상품상세정보 조회
	 */
	public NewProduct selectNewProductInfoInTempOld(String newProductCode) {
		return pEDMPRO0003Dao.selectNewProductInfoInTempOld(newProductCode);
	}

	/**
	 * Desc : 임시보관함 상품 목록 조회
	 * @Method Name : selectNewProductListInTemp
	 * @param SearchProduct 
	 * @return List<NewProduct>
	 */
	public List<NewProduct> selectNewProductListInTemp(SearchProduct searchParam) {
		return pEDMPRO0003Dao.selectNewProductListInTemp(searchParam);
	}

	/**
	 * Desc : 임시보관함 목록에서 선택한 상품 확정 처리.
	 * @Method Name : fixNewProductInTemp
	 * @param productCode
	 * @param productType - 0 : 온오프 겸용, 1: 온라인전용, 2: 소셜상품. 
	 * @return List<NewProduct>
	 */
	public void fixNewProductInTemp(String productCode, String productType, List fileList) throws Exception {
		pEDMPRO0003Dao.fixSubmitNewProductInTemp(productCode, productType, fileList);
	}

	/**
	 * Desc : 사용자가 입력한 색상/사이즈 값 조회. 색상 값 중복 가능
	 * @Method Name : selectProductColorListInTemp
	 * @param newProductCode
	 * @return List<ColorSize>
	 */
	public List<ColorSize> selectProductColorListInTemp(String newProductCode) {
		return pEDMPRO0003Dao.selectProductColorListInTemp(newProductCode);
	}

	public List<ColorSize> selectProductColorListInTempOld(String newProductCode) {
		return pEDMPRO0003Dao.selectProductColorListInTempOld(newProductCode);
	}

	public List<EcomAddInfo> selectProductEcomAddInfoListInTemp(String newProductCode) {
		return pEDMPRO0003Dao.selectProductEcomAddInfoListInTemp(newProductCode);
	}

	/**
	 * Desc : 임시보관함 컬러사이즈 상세조회
	 * @Method Name : selectNewProductListInTemp
	 * @param SearchProduct 
	 * @return List<NewProduct>
	 */
	public List<ColorSize> sellCodeView(Map<String, Object> map) {
		return pEDMPRO0003Dao.sellCodeView(map);
	}

	public List selctPogList() throws Exception {
		return pEDMPRO0003Dao.selctPogList();
	}

	public void updatePogList(HashBox hData) throws Exception {
		pEDMPRO0003Dao.updatePogList(hData);
	}

	/**
	 * Desc : 온라인 전용 상품 단품 정보 리스트
	 * @Method Name : selectProductItemListInTemp
	 * @param newProductCode
	 * @return List<ColorSize>
	 */
	public List<ColorSize> selectProductItemListInTemp(String newProductCode) {
		return pEDMPRO0003Dao.selectProductItemListInTemp(newProductCode);
	}

	public List<ColorSize> selectProductItemListInTempOld(String newProductCode) {
		return pEDMPRO0003Dao.selectProductItemListInTempOld(newProductCode);
	}

	/**
	 * Desc : 온라인가등록상품 승인여부 체크
	 * @Method Name : selectMdSendDivnCdCheck
	 * @param pgmID 
	 * @return int
	 */
	public int selectMdSendDivnCdCheck(String pgmId) throws Exception {
		return pEDMPRO0003Dao.selectMdSendDivnCdCheck(pgmId);
	}

}
