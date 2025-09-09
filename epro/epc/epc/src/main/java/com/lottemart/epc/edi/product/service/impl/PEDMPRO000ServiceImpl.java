package com.lottemart.epc.edi.product.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.Logger;
import com.lottemart.epc.edi.comm.model.Constants;
import com.lottemart.epc.edi.comm.model.SearchParam;
import com.lottemart.epc.edi.comm.service.BosOpenApiService;
import com.lottemart.epc.edi.product.dao.PEDMPRO000Dao;
import com.lottemart.epc.edi.product.model.EcProductCategory;
import com.lottemart.epc.edi.product.model.NewProduct;
import com.lottemart.epc.edi.product.model.PEDMPRO0011VO;
import com.lottemart.epc.edi.product.model.SearchProduct;
import com.lottemart.epc.edi.product.service.PEDMPRO000Service;

/**
 * @Class Name : PEDMPRO000ServiceImpl
 * @Description : 신상품 등록 ServiceImpl 클래스
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
@Service("ediProductService")
public class PEDMPRO000ServiceImpl implements PEDMPRO000Service {

	// 신상품 등록 관련 객체. 임시 보관함 삭제나 상품 정보 입력 및 수정.
	@Autowired
	private PEDMPRO000Dao pEDMPRO000Dao;
	
	@Autowired
	private BosOpenApiService bosApiService;

	/**
	 * Desc : 가등록 상품코드 TED_NEW_PROD_REG.NEW_PROD_CD 칼럼에 사용될 값 조회
	 * @Method Name : selectNewProductCode
	 * @param
	 * @return String
	 */
	public String selectNewProductCode() {
//		return pEDMPRO000Dao.selectNewProductCode();
		String pgmId = "";
		try {
			pgmId = bosApiService.selectTpcNewProdRegKey();
		} catch (Exception e) {
			pgmId = "";
		}
		return pgmId; 
	}

	/**
	 * Desc : 상품정보 저장. 물류바코드 등록( ex: 코리안 넷 통해 상품 등록시 바코드 정보가 존재할 경우)
	 *        2015.11.26   by kmlee 온라인 전용상품만 취급하는 소스이므로, 물류바코드 관련 소스 삭제함.
	 * @Method Name : insertProductInfo
	 * @param newProduct(VO)
	 * @return
	 */
	public void insertProductInfo(NewProduct newProduct) throws Exception {

		//<<< 2015.11.30 by kmlee #officialOrder.seasonDivnCode# 계절구분을 년도와 계절로 구분하여 사용
//		String szValue = newProduct.getOfficialOrder().getSeasonDivnCode();
//
//		if(szValue.length() >= 8) {
//			newProduct.getOfficialOrder().setSeasonDivnCode(szValue.substring(4,4));
//			newProduct.getOfficialOrder().setSeasonDivnYear(szValue.substring(0,4));
//		} else {
//			newProduct.getOfficialOrder().setSeasonDivnYear("");
//		}
		//>>>

		// 협력업체 별 거래 유형 조회
		NewProduct tmpProduct = pEDMPRO000Dao.selectNewProductTradeType(newProduct.getEntpCode());

		// 온라인 전용 상품에 대해서만
		if (Constants.ONLINE_ONLY_PRODUCT_CD.equals(newProduct.getOnOffDivnCode())) {

			// --[과세 및 면세,영세 일경우 원가계산 ]-----------------------------------
			String taxFreeProductCosts = pEDMPRO000Dao.selectNormalProductCode(newProduct);
			newProduct.setNormalProductCost(taxFreeProductCosts);

			// 온라인전용 재고관련 정보 입력.
			if (newProduct.getAttrPiType() != null && "I".equals(newProduct.getAttrPiType()) && newProduct.getItemList().size() > 0) {
				pEDMPRO000Dao.insertProductItemInfo(newProduct);
			} else {
				// 온라인전용, 쇼설일경우 패션상품 color_size 더미 data 생성해야함.
				if ("5".equals(newProduct.getProductDivnCode())) {
					pEDMPRO000Dao.insertProductColorSizeTemp(newProduct);
				}
			}

			// 20180904 - 상품키워드 입력 기능 추가
			// 상품키워드
			if (newProduct.getKeywordList().size() > 0) {
				pEDMPRO000Dao.mergeTpcPrdKeyword(newProduct);
			}
			
			if (newProduct.getAttrbuteList().size() > 0) {
				for(int i=0; i< newProduct.getAttrbuteList().size(); i++){
					if(newProduct.getAttrbuteList().get(i).getAttrId() == null || "".equals(newProduct.getAttrbuteList().get(i).getAttrId())){
						throw new IllegalArgumentException("속성유형값을 입력해주세요.");
					}
				}
				pEDMPRO000Dao.insertEcProductAttribute(newProduct);
			}

		}

		// 20190312 - 온라인 상품 등록시 상품상세설명 등록 위치 변경으로 상품등록시 skip
		// 상품 설명 등록
		if ("".equals(newProduct.getRegType()) || newProduct.getRegType() == null) {
			pEDMPRO000Dao.insertProductDescription(newProduct);
		}

		if (StringUtils.isEmpty(newProduct.getProfitRate())) {
			newProduct.setProfitRate("0");
		}

		if (!StringUtils.isEmpty(newProduct.getDispCatCd())) {
			EcProductCategory ecProductCategory = new EcProductCategory();
			ecProductCategory.setProdCd(newProduct.getNewProductCode());
			ecProductCategory.setPgmId(newProduct.getNewProductCode());
			ecProductCategory.setRegId(newProduct.getEntpCode());
			ecProductCategory.setStdCatCd(newProduct.getStdCatCd());
			String[] dispCatArr = newProduct.getDispCatCd().split(",");
			for (int i = 0; i < dispCatArr.length; i++) {
				ecProductCategory.setDispCatCd(dispCatArr[i]);
				pEDMPRO000Dao.insertEcProductCategory(ecProductCategory);
			}
		}

		pEDMPRO000Dao.insertProductInfo(newProduct); // 상품 등록
	}

	/**
	 * Desc : 온라인 전용 대표 상품 코드 조회
	 * @Method Name : selectOnlineRepresentProductList
	 * @param entpCode(협력업체코드
	 * @return
	 */
	public List<NewProduct> selectOnlineRepresentProductList(String entpCode) {
		return pEDMPRO000Dao.selectOnlineRepresentProductList(entpCode);
	}

	public List<NewProduct> selectOnlineRepresentProductListOld(String entpCode) {
		return pEDMPRO000Dao.selectOnlineRepresentProductListOld(entpCode);
	}

	/**
	 * Desc : 거래 중지 여부, 거래유형, 과세구분 정보 조회
	 * @Method Name : selectCountVendorStopTrading
	 * @param vendorCode(협력업체코드)
	 * @return SearchParam(vo)
	 */
	public SearchParam selectCountVendorStopTrading(String vendorCode) {
		// 거래중지 여부
		Integer stopCount = pEDMPRO000Dao.selectCountVendorStopTrading(vendorCode);
		// 거래 유형
		NewProduct tmpProduct = pEDMPRO000Dao.selectNewProductTradeType(vendorCode);
		if (tmpProduct == null) {
			tmpProduct = new NewProduct();
			tmpProduct.setTradeTypeCode("1");
		}
		// 과세 여부
		String taxDivnCode = pEDMPRO000Dao.selectVendorTaxDivnCode(vendorCode);

		SearchParam resultParam = new SearchParam();
		resultParam.setStopTradeVendorCount(stopCount);
		resultParam.setTradeType(tmpProduct.getTradeTypeCode());
		resultParam.setTaxDivnCode(taxDivnCode);

		return resultParam;
	}

	public SearchParam selectCountVendorStopTradingOld(String vendorCode) {
		// 거래중지 여부
		Integer stopCount = pEDMPRO000Dao.selectCountVendorStopTradingOld(vendorCode);
		// 거래 유형
		NewProduct tmpProduct = pEDMPRO000Dao.selectNewProductTradeTypeOld(vendorCode);
		if (tmpProduct == null) {
			tmpProduct = new NewProduct();
			tmpProduct.setTradeTypeCode("1");
		}
		// 과세 여부
		String taxDivnCode = pEDMPRO000Dao.selectVendorTaxDivnCodeOld(vendorCode);

		SearchParam resultParam = new SearchParam();
		resultParam.setStopTradeVendorCount(stopCount);
		resultParam.setTradeType(tmpProduct.getTradeTypeCode());
		resultParam.setTaxDivnCode(taxDivnCode);

		return resultParam;
	}

	public void deleteProdAddDetail(NewProduct newProduct) {
		pEDMPRO000Dao.deleteProdAddDetail(newProduct);
	}

	public void insertProdAddDetail(NewProduct newProduct) {
		pEDMPRO000Dao.insertProdAddDetail(newProduct);
	}

	public void deleteProdCertDetail(NewProduct newProduct) {
		pEDMPRO000Dao.deleteProdCertDetail(newProduct);
	}

	public void insertProdCertDetail(NewProduct newProduct) {
		pEDMPRO000Dao.insertProdCertDetail(newProduct);
	}

	/**
	 * Desc : 패션 상품 색상/사이즈 조회
	 * @Method Name : selectProductColorList
	 * @param newProductCode(가등록 상품코드)
	 * @return ArrayList<String>
	 *
	 */
	public List<String> selectProductColorList(String newProductCode) {
		return pEDMPRO000Dao.selectProductColorList(newProductCode);
	}

	public List<String> selectProductColorListOld(String newProductCode) {
		return pEDMPRO000Dao.selectProductColorListOld(newProductCode);
	}

	/**
	 * Desc : 온라인전용상품 단품 정보 삭제
	 * @Method Name : deleteNewProductInTemp
	 * @param newproductcd, itemcd
	 * @return
	 */
	public Map<String, Object> deleteTempProductItem(Map<String, Object> map) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("msg", "FAIL");

		// ----- 단품정보 삭제
		pEDMPRO000Dao.deleteTempProductItem(map);
		resultMap.put("msg", "SUCCESS");

		return resultMap;
	}

	/**
	 * 구 EDI 신상품등록현황 조회
	 */
	public List<NewProduct> selectNewProductListOld(SearchProduct searchProduct) {
		return pEDMPRO000Dao.selectNewProductListOld(searchProduct);
	}

	// 20180904 - 상품키워드 입력 기능 추가
	/**
	 * 신규 상품키워드 조회
	 * @param newProductCode
	 * @return List<PEDMPRO0003VO>
	 * @throws Exception
	 */
	public List<PEDMPRO0011VO> selectTpcPrdKeywordList(String newProductCode) throws Exception {
		return pEDMPRO000Dao.selectTpcPrdKeywordList(newProductCode);
	}

	/**
	 * 신규 상품키워드 등록
	 * @return int
	 * @throws Exception
	 */
	public int insertTpcPrdKeyword(PEDMPRO0011VO bean) throws Exception {
		int resultCnt = 0;

		try {
			// --10개 이상 채크
			// 키워드 입력
			resultCnt = pEDMPRO000Dao.insertTpcPrdKeyword(bean);

			if (resultCnt <= 0) {
				throw new IllegalArgumentException("키워드 입력 작업중에 오류가 발생하였습니다.");
			}

			// 전체 키워드 셋팅
			List<PEDMPRO0011VO> prdList = pEDMPRO000Dao.selectChkTpcPrdTotalKeyword(bean);

			if (!(prdList == null || prdList.size() == 0)) {
				PEDMPRO0011VO resultBean = (PEDMPRO0011VO) prdList.get(0);
				String totalKywrd = resultBean.getTotalKywrd();
				String seq = resultBean.getSeq();

				bean.setTotalKywrd(totalKywrd);

				if ("000".equals(seq)) {
					// 전체키워드 업데이트
					resultCnt = pEDMPRO000Dao.updateTpcPrdTotalKeyword(bean);

					if (resultCnt <= 0) {
						throw new IllegalArgumentException("전체 키워드 수정 작업중에 오류가 발생하였습니다.");
					}
				} else {
					// 전체키워드 입력
					resultCnt = pEDMPRO000Dao.insertTpcPrdTotalKeyword(bean);

					if (resultCnt <= 0) {
						throw new IllegalArgumentException("전체 키워드 입력 작업중에 오류가 발생하였습니다.");
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}

		return resultCnt;
	}

	/**
	 * 신규 상품키워드 삭제
	 * @return MAP
	 * @throws Exception
	 */
	public Map<String, Object> deleteTpcPrdKeyword(Map<String, Object> map) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		int resultCnt = 0;

		String pgmId = "";
		String regId = "";

		if (map.get("pgmId") != null && map.get("seq") != null) {
			pgmId = (String) map.get("pgmId");
			regId = (String) map.get("seq");
		}

		pEDMPRO000Dao.deleteTpcPrdKeyword(map);

		PEDMPRO0011VO bean = new PEDMPRO0011VO();

		bean.setPgmId(pgmId);
		bean.setRegId(regId);

		// 전체 키워드 셋팅
		List<PEDMPRO0011VO> prdList = pEDMPRO000Dao.selectChkTpcPrdTotalKeyword(bean);

		if (!(prdList == null || prdList.size() == 0)) {
			PEDMPRO0011VO resultBean = (PEDMPRO0011VO) prdList.get(0);
			String totalKywrd = resultBean.getTotalKywrd();
			String seq = resultBean.getSeq();

			bean.setTotalKywrd(totalKywrd);

			if ("000".equals(seq)) {
				// 전체 키워드 업데이트
				resultCnt = pEDMPRO000Dao.updateTpcPrdTotalKeyword(bean);

				if (resultCnt <= 0) {
					resultMap.put("msg", "FAIL");
					throw new IllegalArgumentException("전체 키워드 수정 작업중에 오류가 발생하였습니다.");
				}
			} else {
				if (!("".equals(totalKywrd) || totalKywrd == null)) {
					// 전체키워드 입력
					resultCnt = pEDMPRO000Dao.insertTpcPrdTotalKeyword(bean);

					if (resultCnt <= 0) {
						resultMap.put("msg", "FAIL");
						throw new IllegalArgumentException("전체 키워드 입력 작업중에 오류가 발생하였습니다.");
					}
				}
			}
		}

		resultMap.put("msg", "SUCCESS");

		return resultMap;
	}

	public int deleteTpcPrdAllKeyword(String pgmId) throws Exception {
		return pEDMPRO000Dao.deleteTpcPrdAllKeyword(pgmId);
	}

	public int deleteTpcNewPrdAllKeyword(String pgmId) throws Exception {
		return pEDMPRO000Dao.deleteTpcNewPrdAllKeyword(pgmId);
	}

}
