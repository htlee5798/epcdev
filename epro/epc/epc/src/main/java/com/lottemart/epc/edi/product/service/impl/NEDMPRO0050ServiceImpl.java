package com.lottemart.epc.edi.product.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lottemart.common.exception.AlertException;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.edi.product.dao.NEDMPRO0050Dao;
import com.lottemart.epc.edi.product.service.NEDMPRO0050Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @Class Name : NEDMPRO0050ServiceImpl
 * @Description : 온라인신상품등록(딜) 서비스 Impl
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2016.04.22		projectBOS32 	최초생성
 * </pre>
 */
@Service("nEDMPRO0050Service")
public class NEDMPRO0050ServiceImpl implements NEDMPRO0050Service {

	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0050ServiceImpl.class);

	@Resource(name = "nEDMPRO0050Dao")
	private NEDMPRO0050Dao nEDMPRO0050Dao;

	/**
	 * 묶음상품정보 등록
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public void newOnlineDealProductSave(DataMap paramMap) throws Exception {

		try {
			//logger.debug("paramMap.getString(\"dealThemaProdListJsonObject\") === " + paramMap.getString("dealThemaProdListJsonObject"));

			if ("Y".equals(paramMap.getString("themaYn"))) {
				JSONArray themaArray = JSONArray.fromObject(paramMap.getString("dealThemaProdListJsonObject"));
				if (themaArray != null && themaArray.size() > 0) {

					int size = themaArray.size();
					int totalSize = 0;
					for (int i = 0; i < size; i++) {
						JSONObject themaObj = themaArray.getJSONObject(i);
						JSONArray themaProdList = themaObj.getJSONArray("themaProd");
						if (themaProdList != null && themaProdList.size() > 0) {
							totalSize += themaProdList.size();
						}
					}
					if (totalSize > 100) {
						throw new AlertException("상품은 100개까지 적용 가능합니다.");
					}

					nEDMPRO0050Dao.deleteDealThema(paramMap); // 테마 삭제
					nEDMPRO0050Dao.deleteDealProduct(paramMap); // 테마 상품 삭제

					String pgmId = paramMap.getString("pgmId");
					String regModId = paramMap.getString("entpCode");

					for (int i = 0; i < size; i++) {
						JSONObject themaObj = themaArray.getJSONObject(i);
						DataMap themaMap = new DataMap();
						//logger.debug("themaObj.toString() === " + themaObj.toString());
						themaMap.put("pgmId", pgmId);
						themaMap.put("themaSeq", themaObj.getInt("themaSeq"));
						themaMap.put("themaNm", themaObj.getString("themaNm"));
						themaMap.put("orderSeq", themaObj.getInt("orderSeq"));
						themaMap.put("regId", regModId);
						themaMap.put("modId", regModId);
	
						nEDMPRO0050Dao.insertDealThema(themaMap); // 테마 등록
	
						JSONArray themaProdList = themaObj.getJSONArray("themaProd");
						if (themaProdList != null && themaProdList.size() > 0) {
	
							int size2 = themaProdList.size();
							for (int j = 0 ; j < size2; j++) {
	
								JSONObject dealProd = themaProdList.getJSONObject(j);
								DataMap dealMap = new DataMap();
								//logger.debug("dealProd.toString() === " + dealProd.toString());
								dealMap.put("pgmId", pgmId);
								dealMap.put("prodCd", dealProd.getString("prodCd"));
								dealMap.put("repYn", dealProd.getString("repYn"));
								dealMap.put("orderSeq", dealProd.getInt("orderSeq"));
								dealMap.put("themaSeq", themaObj.getInt("themaSeq"));
								dealMap.put("mainProdYn", dealProd.getString("mainProdYn"));
								dealMap.put("regId", regModId);
								dealMap.put("modId", regModId);
	
								nEDMPRO0050Dao.insertDealProduct(dealMap); // 테마상품 등록
							}
	
						}
					}
				}
			} else {

				JSONArray dealArray = JSONArray.fromObject(paramMap.getString("dealProdListJsonArray"));

				if (dealArray != null && dealArray.size() > 0) {
					nEDMPRO0050Dao.deleteDealThema(paramMap); // 테마 삭제
					nEDMPRO0050Dao.deleteDealProduct(paramMap);
	
					int size = dealArray.size();
					if (size > 100) {
						throw new AlertException("상품은 100개까지 적용 가능합니다.");
					}
					String pgmId = paramMap.getString("pgmId");
					String regModId = paramMap.getString("entpCode");
					for (int i = 0; i < size; i++) {
						JSONObject dealObj = dealArray.getJSONObject(i);
						DataMap dealMap = new DataMap();

						dealMap.put("pgmId", pgmId);
						dealMap.put("prodCd", dealObj.getString("prodCd"));
						dealMap.put("repYn", dealObj.getString("repYn"));
						dealMap.put("orderSeq", dealObj.getInt("orderSeq"));
						dealMap.put("mainProdYn", dealObj.getString("mainProdYn"));
						dealMap.put("themaSeq", null);
						dealMap.put("regId", regModId);
						dealMap.put("modId", regModId);

						nEDMPRO0050Dao.insertDealProduct(dealMap);
					}
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}

	}

	/**
	 * 묶음상품정보 조회
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectDealProductList(DataMap paramMap) throws Exception {
		return nEDMPRO0050Dao.selectDealProductList(paramMap);
	}

	/**
	 * 테마 목록 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectDealThemaList(DataMap paramMap) throws Exception {
		return nEDMPRO0050Dao.selectDealThemaList(paramMap);
	}

	/**
	 * 테마 딜 상품목록 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectDealThemaProdList(DataMap paramMap) throws Exception {
		return nEDMPRO0050Dao.selectDealThemaProdList(paramMap);
	}

}
