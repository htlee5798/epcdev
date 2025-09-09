package com.lottemart.epc.product.service.impl;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.lottemart.common.code.service.CommonCodeService;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.ObjectUtil;
import com.lottemart.common.util.RestAPIUtil;
import com.lottemart.common.util.RestConst;
import com.lottemart.epc.common.dao.CommonDao;
import com.lottemart.epc.product.dao.PSCPPRD0002Dao;
import com.lottemart.epc.product.model.PSCPPRD0002VO;
import com.lottemart.epc.product.service.PSCPPRD0002Service;

/**
 * @Class Name : PSCPPRD0002ServiceImpl.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 20. 오후 02:02:02 mjChoi
 *
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Service("pscpprd0002Service")
public class PSCPPRD0002ServiceImpl implements PSCPPRD0002Service {

	private static final Logger logger = LoggerFactory.getLogger(PSCPPRD0002ServiceImpl.class);

	@Autowired
	private PSCPPRD0002Dao pscpprd0002Dao;

	@Autowired
	private CommonCodeService commonCodeService;
	
	@Autowired
	private CommonDao commonDao;

	/**
	 * 공통코드 목록
	 * @return DataMap
	 * @throws Exception
	 */
	public List<DataMap> selectPrdCommonCodeList() throws Exception {
		return pscpprd0002Dao.selectPrdCommonCodeList();
	}

	/**
	 * 상품 테마 딜 설정 조회
	 * @param prodCd
	 * @return
	 * @throws Exception
	 */
	public DataMap selectPrdExtThemaDealInfo(String prodCd) throws Exception {
		DataMap dataMap = pscpprd0002Dao.selectPrdExtThemaDealInfo(prodCd);
		return dataMap;
	}

	/**
	 * 상품 테마 딜 설정 등록/수정
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	public int updatePrdExtThemaDealInfo(DataMap dataMap) throws Exception {
		return pscpprd0002Dao.updatePrdExtThemaDealInfo(dataMap);
	}

	/**
	 * 상품 상세정보 조회
	 * @param String
	 * @return DataMap
	 * @throws Exception
	 */
	public DataMap selectPrdInfo(String prodCd) throws Exception {
		DataMap dataMap = pscpprd0002Dao.selectPrdInfo(prodCd);
/*
		if (dataMap.getString("EC_LINK_YN").equals("Y")) {
			// ec api
			String ecDispYn = "";
			String slStatNm = "";
			try {
				RestAPIUtil rest = new RestAPIUtil();

				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("spdNo", (String) dataMap.getString("PROD_CD"));

				String json = rest.sendRestCall(RestConst.PD_API_0007, HttpMethod.POST, condition,
						RestConst.PD_API_TIME_OUT, true);
				logger.debug("API CALL RESULT = " + json);

				ObjectMapper mapper = new ObjectMapper();

				if (!"".equals(json)) {
					Map<String, Object> value = mapper.readValue(json, new TypeReference<Map<String, Object>>() {
					});
					Map<String, Object> body = (Map<String, Object>) value.get("body");

					if (!ObjectUtil.isEmpty(value.get("body"))) {
						Map<String, Object> data = (Map<String, Object>) body.get("data");
						ecDispYn = data.get("dpYn").toString(); // 전시여부
						String slStatCd = data.get("slStatCd").toString(); // 판매상태
						if (ecDispYn == null || ecDispYn.trim().length() == 0) {
							ecDispYn = "N";
						}
						DataMap paramMap = new DataMap();
						paramMap.put("ecMajorCd", "SL_STAT_CD");
						paramMap.put("ecMinorCd", slStatCd);
						DataMap ecCodeMap = commonCodeService.getEcCode(paramMap);
						slStatNm = ecCodeMap.getString("EC_CD_NM");
					}
				}

			} catch (Exception e) {
				logger.error(e.getMessage());
			}

			dataMap.put("EC_DISP_YN", ecDispYn.equals("Y") ? "전시" : "전시안함");
			dataMap.put("EC_SL_STAT_NM", slStatNm);

		} else {
			dataMap.put("EC_DISP_YN", "-");
			dataMap.put("EC_SL_STAT_NM", "-");
		}*/

		dataMap.put("EC_DISP_YN", "-"); 
		dataMap.put("EC_SL_STAT_NM", "-"); 

		return dataMap;
	}

	/**
	 * 상품 상세정보 수정
	 * @param VO
	 * @return int
	 * @throws Exception
	 */
	public int updatePrdInfo(PSCPPRD0002VO bean) throws Exception {
		int resultCnt = 0;
		int exprResult = 0;
		try {
			//--디비결과처리..
			resultCnt = pscpprd0002Dao.insertProdHist(bean);
			if (resultCnt <= 0) {
				throw new IllegalArgumentException("상품정보 이력 저장시 에러가 발생하였습니다.");
			}

			//11번가 연동처리
			List<PSCPPRD0002VO> prdList = pscpprd0002Dao.selectchk11StPrdInfoUpdate(bean);

			if (!(prdList == null || prdList.size() == 0)) {
				PSCPPRD0002VO resultBean = (PSCPPRD0002VO) prdList.get(0);
				String prodCd = resultBean.getProdCd();
				int itemCnt = Integer.parseInt(resultBean.getItemCnt());//--null
				String siteRegYn = resultBean.getSiteRegYn();

				if (!"".equals(prodCd) && itemCnt == 1 && "Y".equals(siteRegYn)) {
					resultCnt = pscpprd0002Dao.insertSiteLinkInfo(bean);

					if (resultCnt <= 0) {
						throw new IllegalArgumentException("11번가 연동 정보 입력 작업중에 오류가 발생하였습니다.");
					}
				}
			}

			//상세정보 업데이트
			resultCnt = pscpprd0002Dao.updatePrdDetailInfo(bean);
			if (resultCnt <= 0) {
				throw new IllegalArgumentException("상품 상세 정보 입력 작업중에 오류가 발생하였습니다.");
			}

			//체험형정보 업데이트
			exprUpdate(bean);

			//카테고리 할당상품의 상품명정보변경 ( 협력업체는 카테고리 수정 불가 )
			resultCnt = pscpprd0002Dao.insertProdHist(bean);
			if(resultCnt <= 0) {
				throw new IllegalArgumentException("상품정보 이력 저장시 에러가 발생하였습니다.");
			}

			// API 연동 (EPC -> 통합BO API)
			String prodCd = bean.getProdCd().substring(0,1);
			String result = "";
			try {
				commonDao.commit();
				RestAPIUtil rest = new RestAPIUtil();
				if ("D".equals(prodCd)) {
					//D코드일 경우, EC에 승인된 D코드일 경우에만 기획전형 상품수정API호출
					if( commonDao.selectEcPlanRegisteredYn(bean.getProdCd())){
						result = rest.sendRestCall(RestConst.PD_API_0010+bean.getProdCd(), HttpMethod.POST, null, RestConst.PD_API_TIME_OUT, true);
						logger.debug("PD_API_0010 API CALL RESULT = " + result);
					}
				} else {
					//D코드가 아닐 경우  , EC에 승인된 상품에 한해서만 상품수정 API호출  
					if(commonDao.selectEcApprovedYn(bean.getProdCd())){

						Map<String, Object> reqMap = new HashMap<String, Object>();
						reqMap.put("spdNo", bean.getProdCd());

						result = rest.sendRestCall(RestConst.PD_API_0002, HttpMethod.POST, reqMap, RestConst.PD_API_TIME_OUT, true);
						logger.debug("PD_API_0002 API CALL RESULT = " + result);


						result = rest.sendRestCall(RestConst.PD_API_0026, HttpMethod.POST, reqMap, RestConst.PD_API_TIME_OUT, true);
						logger.debug("PD_API_0026 API CALL RESULT = " + result);

						if ("07".equals(bean.getOnlineProdTypeCd())) {
							String jsonString2 = rest.sendRestCall(RestConst.PD_API_0035, HttpMethod.POST, reqMap, RestConst.PD_API_TIME_OUT, true);
							logger.debug("PD_API_0035 API CALL RESULT = " + jsonString2);
						}
					}
				}

			} catch (Exception e) {
				logger.error(e.getMessage());
			}

		} catch (Exception e) {
			throw e;
		}

		return resultCnt;
	}

	private void exprUpdate(PSCPPRD0002VO bean) throws Exception {
		DataMap exParamMap = new DataMap();
		String prodCd = bean.getProdCd();
		String eSellerRecomm = bean.getSellerRecomm();
		String eModId = bean.getModId();
		String eRegId = bean.getRegId();
		boolean exprProdCd = bean.getExprTypeCd();
		String eTypeCd = "false";
		if(exprProdCd) {
			eTypeCd = "EXPERIENCE";
		}
		int tprCountCnt = pscpprd0002Dao.countTprProdExInfo(prodCd);

		exParamMap.put("prodCd", prodCd);
		exParamMap.put("sellerRecomm", eSellerRecomm);
		exParamMap.put("typeCd", eTypeCd);
		exParamMap.put("regId", eRegId);
		exParamMap.put("modId", eModId);

		if(tprCountCnt > 0 && !exprProdCd) { // extension에 있으면서 체험상품 값이 N으로 넘어온경우 delete
			int delCnt = pscpprd0002Dao.deleteTprProdExInfo(prodCd);
			if(delCnt <= 0) {
				throw new IllegalArgumentException("체험형 상세정보 삭제 중에 오류가 발생했습니다.");
			}
		}

		if(exprProdCd) { //체험상품 값이 Y일때
			int typeCheckCnt = pscpprd0002Dao.selectTypeCdCheck(prodCd);
			if(typeCheckCnt > 0) { //type_cd가 fresh값인 상품
				throw new IllegalArgumentException("추가 속성을 중복으로 가질 수 없습니다. ex) 체험형 상품, 신선 상품");
			} else { // type_cd가 fresh가 아닌 상품(null or experience)
				int exUpdateCnt = pscpprd0002Dao.mergeExprDetailInfo(exParamMap);
				if(exUpdateCnt <= 0) {
					throw new IllegalArgumentException("체험형 상세정보 수정 작업중에 오류가 발생했습니다.");
				}
			}
		}
	}

	/**
	 * PRD 증정품 COMBO내용
	 * @param String
	 * @return List<DataMap>
	 * @throws Exception
	 */
	public List<DataMap> selectPrdPresentList(String prodCd) throws Exception {
		return pscpprd0002Dao.selectPrdPresentList(prodCd);
	}

	/**
	 * 전상법 등록 유무
	 * Parameter  prodCd , flag --> new_prod_cd 인지 prod_cd 인지  , 1일경우 prodCd , 2일경우 new_prod_cd
	 * @return int
	 * @throws Exception
	 */
	public String prodCommerceCheck(String prodCd, String flag) throws Exception {

		String prodCheckValue = "";

		try {
			DataMap paramMap = new DataMap();
			paramMap.put("prodCd", prodCd);

			DataMap resultMap = new DataMap();

			if("1".equals(flag)){
				resultMap = (DataMap)pscpprd0002Dao.selectProdCommerceCheck(paramMap);
				prodCheckValue = resultMap.getString("PRODCHK");
			}else{
				resultMap = (DataMap)pscpprd0002Dao.selectNewProdCommerceCheck(paramMap);
				prodCheckValue = resultMap.getString("PRODCHK");
			}

			if("N".equals(prodCheckValue)){
				throw new IllegalArgumentException("전상법 품목이 제대로 등록되지 않았습니다.");
			}

		} catch (Exception e) {
			throw e;
		}

		return prodCheckValue;
	}

	//20180911 - 상품키워드 입력 기능 추가
	public Map<String, Object> selectKeywordYnChk(Map<String, Object> paramMap) throws Exception {
		Map<String, Object>	resultMap = new HashMap<String, Object>();

		String keywordYn = pscpprd0002Dao.selectKeywordYnChk((String) paramMap.get("prodCd"));

		String keywordAprvYn = pscpprd0002Dao.selectKeywordAprvYnChk((String) paramMap.get("prodCd"));

		if(keywordYn.equals("N") && keywordAprvYn.equals("N")) {
			resultMap.put("msg", "FAIL");
		} else {
			resultMap.put("msg", "SUCCESS");
		}

		return resultMap;
	}
	//20180911 - 상품키워드 입력 기능 추가

}
