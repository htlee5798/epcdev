package com.lottemart.epc.product.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.RestAPIUtil;
import com.lottemart.common.util.RestConst;
import com.lottemart.epc.common.dao.CommonDao;
import com.lottemart.epc.product.dao.PSCPPRD0004Dao;
import com.lottemart.epc.product.model.PSCPPRD00041VO;
import com.lottemart.epc.product.model.PSCPPRD0004VO;
import com.lottemart.epc.product.service.PSCPPRD0004Service;

/**
 * @Class Name : PSCPPRD0004ServiceImpl.java
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
@Service("pscpprd0004Service")
public class PSCPPRD0004ServiceImpl implements PSCPPRD0004Service {

	private static final Logger logger = LoggerFactory.getLogger(PSCPPRD0004ServiceImpl.class);

	@Autowired
	private PSCPPRD0004Dao pscpprd0004Dao;

	@Autowired
	private CommonDao commonDao;

	/**
	 * 추천상품 목록
	 * @param Map<String, String>
	 * @return List<VO>
	 * @throws Exception
	 */
	public List<PSCPPRD0004VO> selectPrdRecommendList(Map<String, String> paramMap) throws Exception {
		return pscpprd0004Dao.selectPrdRecommendList(paramMap);
	}

	public List<PSCPPRD00041VO> selectPrdThemaList(Map<String, String> paramMap) throws Exception {
		return pscpprd0004Dao.selectPrdThemaList(paramMap);
	}

	/**
	 * 추천상품 수정, 삭제 처리
	 * @param List<VO>
	 * @param String
	 * @return int
	 * @throws Exception
	 */
	public int updatePrdRecommendList(List<PSCPPRD0004VO> list, String mode) throws Exception {
		int resultCnt = 0;
		int listSize = 0;

		try {
			if (list != null && list.size() > 0) {

				PSCPPRD0004VO tmp = list.get(0);
				PSCPPRD00041VO delVo = new PSCPPRD00041VO();
				delVo.setProdCd(tmp.getProdCd());
				pscpprd0004Dao.deletePrdThema(delVo);
				pscpprd0004Dao.deletePrdThemaProd(delVo);

				listSize = list.size();
				for (int i = 0; i < listSize; i++) {
					PSCPPRD0004VO bean = list.get(i);
					bean.setThemaSeq(null);
					/*if (!"".equals(bean.getImgPath())) {
						resultCnt = pscpprd0004Dao.deletePrdRecommend(bean);
						logger.debug("resultCnt = " + resultCnt);
						if (resultCnt <= 0) {
							throw new IllegalArgumentException("상품구성정보 등록/수정 작업중에 오류가 발생하였습니다. 채크된 항목중 " + (bean.getNum()) + "번째 항목을 확인해주세요.");
						}
					}*/
					resultCnt = pscpprd0004Dao.insertPrdRecommend(bean);
					logger.debug("resultCnt = " + resultCnt);
					if (resultCnt <= 0) {
						throw new IllegalArgumentException("상품구성정보 등록/수정 작업중에 오류가 발생하였습니다. 채크된 항목중 " + (bean.getNum()) + "번째 항목을 확인해주세요.");
					}
				}
			}

		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.debug(sw.toString());
			throw e;
		}

		try {
			PSCPPRD0004VO dealProd = list.get(0);
			RestAPIUtil rest = new RestAPIUtil();
			String result = "";
			if (dealProd.getProdCd().startsWith("D")) {
				if (commonDao.selectEcPlanRegisteredYn(dealProd.getProdCd())) {
					result = rest.sendRestCall(RestConst.PD_API_0010 + dealProd.getProdCd(), HttpMethod.POST, null, RestConst.PD_API_TIME_OUT, true);
					logger.debug("API CALL RESULT = " + result);
				}
			}
			logger.debug("API CALL RESULT = " + result);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.debug(sw.toString());
			throw e;
		}

		return listSize;
	}

	public int updatePrdPrdThemaList(List<PSCPPRD00041VO> list) throws Exception {
		int resultCnt = 0;
		int listSize = 0;
		int zeroListCnt = 0;

		try {
			if (list != null && list.size() > 0) {

				PSCPPRD00041VO tmp = list.get(0);
				PSCPPRD00041VO delVo = new PSCPPRD00041VO();
				delVo.setProdCd(tmp.getProdCd());
				pscpprd0004Dao.deletePrdThema(delVo);
				pscpprd0004Dao.deletePrdThemaProd(delVo);

				listSize = list.size();
				for (int i = 0; i < listSize; i++) {
					PSCPPRD00041VO themaVo = list.get(i);

					resultCnt = pscpprd0004Dao.insertPrdThema(themaVo);

					List<PSCPPRD0004VO> pdList = themaVo.getThemaProdList();
					if (pdList != null && pdList.size() > 0) {
						int pdSize = pdList.size();
						for (int j = 0; j < pdSize; j++) {
							PSCPPRD0004VO prod = pdList.get(j);
							resultCnt = pscpprd0004Dao.insertPrdRecommend(prod);
						}
					} else {
						zeroListCnt++;
					}
				}
				commonDao.commit();
			}

		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.debug(sw.toString());
			throw e;
		}

		if (zeroListCnt == 0) {
			try {
				PSCPPRD00041VO themaVo = list.get(0);
				RestAPIUtil rest = new RestAPIUtil();
				String result = "";
				if (themaVo.getProdCd().startsWith("D")) {
					if (commonDao.selectEcPlanRegisteredYn(themaVo.getProdCd())) {
						result = rest.sendRestCall(RestConst.PD_API_0010 + themaVo.getProdCd(), HttpMethod.POST, null, RestConst.PD_API_TIME_OUT, true);
						logger.debug("API CALL RESULT = " + result);
					}
				}
				logger.debug("API CALL RESULT = " + result);
			} catch (Exception e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				logger.debug(sw.toString());
				throw e;
			}
		}


		return listSize;
	}

	/**
	 * 추천할 상품 목록 총카운트
	 * @param Map<String, String>
	 * @return int
	 * @throws Exception
	 */
	public int selectPrdRecommendCrossTotalCnt(Map<String, String> paramMap) throws Exception {
		return pscpprd0004Dao.selectPrdRecommendCrossTotalCnt(paramMap);
	}

	/**
	 * 추천할 상품 목록
	 * @param Map<String, String>
	 * @return List<VO>
	 * @throws Exception
	 */
	public List<PSCPPRD0004VO> selectPrdRecommendCrossList(Map<String, String> paramMap) throws Exception {
		return pscpprd0004Dao.selectPrdRecommendCrossList(paramMap);
	}

	/**
	 * 추천상품 입력 처리
	 * @param List<VO>
	 * @return int
	 * @throws Exception
	 */
	public int insertPrdRecommend(List<PSCPPRD0004VO> pscpprd0004VOList) throws Exception {
		int resultCnt = 0;
		int listSize = 0;

		try {
			PSCPPRD0004VO ListBean = null;
			PSCPPRD0004VO bean = null;
			listSize = pscpprd0004VOList.size();

			for (int i = 0; i < listSize; i++) {
				ListBean = (PSCPPRD0004VO) pscpprd0004VOList.get(i);
				bean = new PSCPPRD0004VO();

				bean.setProdCd(ListBean.getProdCd());
				bean.setAssoCd(ListBean.getAssoCd());
				bean.setRegId(ListBean.getRegId());
				bean.setDispYn(ListBean.getDispYn());
				bean.setProdLinkKindCd(ListBean.getProdLinkKindCd());

				resultCnt = pscpprd0004Dao.insertPrdRecommend(bean);
				if (resultCnt <= 0) {
					throw new IllegalArgumentException("추천 상품 입력 작업중에 오류가 발생하였습니다.");
				}
			}

			for (int i = 0; i < listSize; i++) {
				ListBean = (PSCPPRD0004VO) pscpprd0004VOList.get(i);
				bean = new PSCPPRD0004VO();
				bean.setProdCd(ListBean.getProdCd());
				if (resultCnt > 0) {
					String result = "";
					try {
						commonDao.commit();
						RestAPIUtil rest = new RestAPIUtil();
						if (bean.getProdCd().startsWith("D")) {
							if (commonDao.selectEcPlanRegisteredYn(bean.getProdCd())) {
								result = rest.sendRestCall(RestConst.PD_API_0010 + bean.getProdCd(), HttpMethod.POST, null, RestConst.PD_API_TIME_OUT, true);
								logger.debug("API CALL RESULT = " + result);
							}
						}
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}

		return listSize;
	}

}
