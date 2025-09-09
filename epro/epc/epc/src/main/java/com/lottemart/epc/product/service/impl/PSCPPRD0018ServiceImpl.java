package com.lottemart.epc.product.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.lottemart.epc.common.dao.CommonDao;
import com.lottemart.epc.product.dao.PSCPPRD0018Dao;
import com.lottemart.epc.product.model.PSCPPRD0018VO;
import com.lottemart.epc.product.service.PSCPPRD0018Service;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.RestAPIUtil;
import com.lottemart.common.util.RestConst;

/**
 *
 * @Class Name : PSCPPRD0018ServiceImpl
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자	   수정내용
 *  -------    --------    ---------------------------
 * 2011. 12. 27. 오후 03:03:03 mjChoi
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Service("pbomprd0004Service")
public class PSCPPRD0018ServiceImpl implements PSCPPRD0018Service{
	private static final Logger logger = LoggerFactory.getLogger(PSCPPRD0018ServiceImpl.class);
	@Autowired
	private PSCPPRD0018Dao pscpprd0018Dao;
	@Autowired
	private CommonDao commonDao;
	/**
	 * 상품종류
	 * @return String
	 * @throws Exception
	 */
	public DataMap selectPrdDivnType(String prodCd) throws Exception {
		return pscpprd0018Dao.selectPrdDivnType(prodCd);
	}

	/**
	 * 점포 목록
	 * @return DataMap
	 * @throws Exception
	 */
	public List<DataMap> selectPrdStoreList(String strGubun) throws Exception {
		return pscpprd0018Dao.selectPrdStoreList(strGubun);
	}

	/**
	 * 공통코드 목록
	 * @return DataMap
	 * @throws Exception
	 */
	public List<DataMap> selectPrdCommonCodeList(String prodCd) throws Exception {
		return pscpprd0018Dao.selectPrdCommonCodeList(prodCd);
	}

	/**
	 * 가격정보 목록
	 * @return PSCPPRD0018VO
	 * @throws Exception
	 */
	public List<PSCPPRD0018VO> selectPrdPriceList(DataMap paramMap) throws Exception {
		return pscpprd0018Dao.selectPrdPriceList(paramMap);
	}

	/**
	 * 가격정보 수정 처리
	 * @return int
	 * @throws Exception
	 */
	public int updatePrdPriceList(List<PSCPPRD0018VO> pbomprd0004VOList, String mode) throws Exception {
		int resultCnt = 0;
		int results = 0;

		try {
			// 채크된 항목에 대해 개별 가격정보 처리
			PSCPPRD0018VO bean = null;
			List<PSCPPRD0018VO> prdList;
//			List<PSCPPRD0018VO> prdList2;
//			PSCPPRD0018VO resultBean2;
			PSCPPRD0018VO resultBean;
//			PSCPPRD0018VO result2Bean;
			String dispYn = null;
//			String prodCd = null;
//			String insertYn = null;
//        	int itemCnt;
//        	int delivCnt;
			int listSize = pbomprd0004VOList.size();
			results = listSize;
//			String ins11Flag = "N";

			for (int i = 0; i < listSize; i++) {
				bean = (PSCPPRD0018VO)pbomprd0004VOList.get(i);

				// 가격정보 업데이트
				resultCnt = pscpprd0018Dao.updatePrdPrice(bean);

				if(resultCnt <= 0) {
					throw new IllegalArgumentException("가격 정보 수정 작업중에 오류가 발생하였습니다. 채크된 항목중 "+(i+1)+"번째 항목을 확인해주세요.");
				}

				logger.debug("40 %d \n", resultCnt);
				//System.out.printf("40 %d \n", resultCnt);


				//변경..
				//카테고리할당 수정
//				result2Bean = bean;
				resultCnt = pscpprd0018Dao.updatePrdCategoryAssign(bean);//--result2Bean
				if(resultCnt <= 0) {
					logger.debug("error : "+ resultCnt);
				}

				if("0003".equals(bean.getDeliOptnCd()) ){
					DataMap dm = new DataMap();
					dm.put("STR_CD", bean.getStrCd());
					dm.put("PROD_CD", bean.getProdCd());
					dm.put("ITEM_CD", bean.getItemCd());

					pscpprd0018Dao.insertSpMprInsert(dm);
				}



				//옥션연동대상일시 연동처리
				//옥션연동대상 채크
				//<<< 2015.10.26 by kmlee 옥션제휴 종료로 수정함
				// prdList = pscpprd0018Dao.selectChkAuctionPrdInsert(bean);
//				prdList = null;
				//>>>


				//11번가 연동대상일시 연동처리
				//11번가 연동 채크 채크
//				prdList = pscpprd0018Dao.selectChk11StPrdInfoUpdate(bean);
//
//				if("N".equals(ins11Flag))
//				if(!(prdList == null || prdList.size() == 0)) {
//		        	resultBean = (PSCPPRD0018VO)prdList.get(0);
//		        	prodCd = resultBean.getProdCd();
//		        	itemCnt = Integer.parseInt(resultBean.getItemCnt());//--null
//
//					if(!"".equals(prodCd) && "Y".equals(resultBean.getSiteRegYn()) && itemCnt == 1) {
//
//						//11번가 연동대상 채크2
//						prdList2 = pscpprd0018Dao.selectChk11stAbsenceVisible(bean);
//
//						if(!(prdList2 == null || prdList2.size() == 0)) {
//				        	resultBean2 = (PSCPPRD0018VO)prdList.get(0);
//				        	insertYn = resultBean2.getInsertYn();
//				        	if(!"Y".equals(insertYn)) {
//
//				        		//11번가 연동 처리
//								resultCnt = pscpprd0018Dao.insertSiteLinkInfo(bean);
//								if(resultCnt <= 0) {
//									throw new Exception("11번가 연동 정보 입력 작업중에 오류가 발생하였습니다. 채크된 항목중 "+(i+1)+"번째 항목을 확인해주세요.");
//								}
//								ins11Flag = "Y";//한번만 인서트..
//				        	}
//						}
//					}
//				}

				//--온라인품절일시 PR_ONL_SO_DTIME_MGMT에 저장-- /
				//--온라인품절 채크--
				prdList = pscpprd0018Dao.selectPrdOutOfStockDay(bean);

				if((prdList == null || prdList.size() == 0) && "Y".equals(bean.getOnlineSoutYn())) {

	        		//PR_ONL_SO_DTIME_MGMT 에 저장
					resultCnt = pscpprd0018Dao.insertPrdOutOfStockDay(bean);
					if(resultCnt <= 0) {
						throw new IllegalArgumentException("온라인 품절 정보 입력 작업중에 오류가 발생하였습니다. 채크된 항목중 "+(i+1)+"번째 항목을 확인해주세요.");
					}
				} else if(!(prdList == null || prdList.size() == 0) && "N".equals(bean.getOnlineSoutYn())) {

	        		//PR_ONL_SO_DTIME_MGMT 삭제
					resultCnt = pscpprd0018Dao.deletePrdOutOfStockDay(bean);
					if(resultCnt <= 0) {
						throw new IllegalArgumentException("온라인 품절 정보 삭제 작업중에 오류가 발생하였습니다. 채크된 항목중 "+(i+1)+"번째 항목을 확인해주세요.");
					}
				}
			}

			//상품 전시 채크
			prdList = pscpprd0018Dao.selectChkPrdVisible(bean);

			if(!(prdList == null || prdList.size() == 0)) {
	        	resultBean = (PSCPPRD0018VO)prdList.get(0);
	        	dispYn = resultBean.getDispYn();

	        	//--
//	        	result2Bean = bean;
//	        	result2Bean.setDispYn(dispYn);

	        	if("N".equals(dispYn)) {
					//상품마스터 전시여부 수정
					resultCnt = pscpprd0018Dao.updatePrdVisible(bean);

					// API 연동 (EPC -> 통합BO API)
					String prodCd = bean.getProdCd().substring(0,1);
					String result = "";
					try {
						commonDao.commit();
						RestAPIUtil rest = new RestAPIUtil();
						if(prodCd.equals("D")){
							if(commonDao.selectEcPlanRegisteredYn(bean.getProdCd())){
								result = rest.sendRestCall(RestConst.PD_API_0010+bean.getProdCd(), HttpMethod.POST, null, 5000, true);
							}
						}else {
							if(commonDao.selectEcApprovedYn(bean.getProdCd())){
								Map<String, Object> reqMap = new HashMap<String, Object>();
								reqMap.put("spdNo", bean.getProdCd());
								result = rest.sendRestCall(RestConst.PD_API_0005, HttpMethod.POST, reqMap, 5000, true);
							}
						}
						logger.debug("API CALL RESULT = " + result);

					} catch (Exception e) {
						logger.error(e.getMessage());
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}

		if(pbomprd0004VOList.size() > 0) {
			try {
				// API 연동 (EPC -> 통합BO API)
				commonDao.commit();
				if(commonDao.selectEcRegisteredYn(pbomprd0004VOList.get(0).getProdCd())){
					
					RestAPIUtil rest = new RestAPIUtil();
					Map<String, Object> reqMap = new HashMap<String, Object>();
					reqMap.put("spdNo", pbomprd0004VOList.get(0).getProdCd());
					String jsonString = rest.sendRestCall(RestConst.PD_API_0026, HttpMethod.POST, reqMap, RestConst.PD_API_TIME_OUT, true);//점포별-가격정보수정
					logger.debug("API CALL RESULT = " + jsonString);
					String jsonString2 = rest.sendRestCall(RestConst.PD_API_0003, HttpMethod.POST, reqMap, RestConst.PD_API_TIME_OUT, true);
					logger.debug("API CALL RESULT = " + jsonString2);
					String jsonString3 = rest.sendRestCall(RestConst.PD_API_0004, HttpMethod.POST, reqMap, RestConst.PD_API_TIME_OUT, true);
					logger.debug("API CALL RESULT = " + jsonString3);
					
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		return results;
	}

	/**
	 * 가격정보 상세 목록
	 * @return PSCPPRD0018VO
	 * @throws Exception
	 */
	public List<PSCPPRD0018VO> selectPrdPriceDetailList(Map<String, String> paramMap) throws Exception {
		return pscpprd0018Dao.selectPrdPriceDetailList(paramMap);
	}

}
