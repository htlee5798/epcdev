package com.lottemart.epc.product.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.RestAPIUtil;
import com.lottemart.common.util.RestConst;
import com.lottemart.epc.common.dao.CommonDao;
import com.lottemart.epc.product.dao.PSCPPRD0006Dao;
import com.lottemart.epc.product.model.PSCPPRD0006VO;
import com.lottemart.epc.product.service.PSCPPRD0006Service;

/**
 * @Class Name : PSCPPRD0006ServiceImpl.java
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
@Service("pscpprd0006Service")
public class PSCPPRD0006ServiceImpl implements PSCPPRD0006Service {

	private static final Logger logger = LoggerFactory.getLogger(PSCPPRD0006ServiceImpl.class);

	@Autowired
	private PSCPPRD0006Dao pscpprd0006Dao;

	@Autowired
	private CommonDao commonDao;

	/**
	 * 상품 이미지 목록
	 * @param Map<String, String>
	 * @return List<VO>
	 * @throws Exception
	 */
	public List<PSCPPRD0006VO> selectPrdImageList(Map<String, String> paramMap) throws Exception {
		return pscpprd0006Dao.selectPrdImageList(paramMap);
	}

	/**
	 * 상품 이미지 추가 처리
	 * @param VO
	 * @return int
	 * @throws Exception
	 */
	public int updatePrdImageAdd(PSCPPRD0006VO bean) throws Exception {
		int resultCnt = 0;

		try {
			resultCnt = pscpprd0006Dao.updatePrdImageAdd(bean);
			if (resultCnt <= 0) {
				throw new IllegalArgumentException("상품 이미지 추가 작업중에 오류가 발생하였습니다.");
			}

		} catch (Exception e) {
			throw e;
		}

		// API 연동 (EPC -> 통합BO API)
		if (resultCnt > 0) {
			String prodCd = bean.getProdCd().substring(0, 1);
			String result = "";
			try {
				commonDao.commit();
				RestAPIUtil rest = new RestAPIUtil();
				if (prodCd.equals("D")) {
					if (commonDao.selectEcPlanRegisteredYn(bean.getProdCd())) {
						result = rest.sendRestCall(RestConst.PD_API_0010 + bean.getProdCd(), HttpMethod.POST, null, RestConst.PD_API_TIME_OUT, true);
					}
				} else {
					if (commonDao.selectEcApprovedYn(bean.getProdCd())) {
						Map<String, Object> reqMap = new HashMap<String, Object>();
						reqMap.put("spdNo", bean.getProdCd());
						result = rest.sendRestCall(RestConst.PD_API_0002, HttpMethod.POST, reqMap, RestConst.PD_API_TIME_OUT, true);
					}
				}
				//logger.debug("API CALL RESULT = " + result);

			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

		return resultCnt;
	}

	/**
	 * 상품 이미지 삭제 처리
	 * @param VO
	 * @return int
	 * @throws Exception
	 */
	public int updatePrdImageDel(PSCPPRD0006VO bean) throws Exception {
		int resultCnt = 0;

		try {
			resultCnt = pscpprd0006Dao.updatePrdImageDel(bean);

			if (resultCnt <= 0) {
				throw new IllegalArgumentException("상품 이미지 삭제 작업중에 오류가 발생하였습니다.");
			}
		} catch (Exception e) {
			throw e;
		}
		// API 연동 (EPC -> 통합BO API)
		if (resultCnt > 0) {
			String prodCd = bean.getProdCd().substring(0, 1);
			String result = "";
			try {
				commonDao.commit();
				RestAPIUtil rest = new RestAPIUtil();
				if (prodCd.equals("D")) {
					if (commonDao.selectEcPlanRegisteredYn(bean.getProdCd())) {
						result = rest.sendRestCall(RestConst.PD_API_0010 + bean.getProdCd(), HttpMethod.POST, null, RestConst.PD_API_TIME_OUT, true);
					}
				} else {

					if (commonDao.selectEcApprovedYn(bean.getProdCd())) {
						Map<String, Object> reqMap = new HashMap<String, Object>();
						reqMap.put("spdNo", bean.getProdCd());
						result = rest.sendRestCall(RestConst.PD_API_0002, HttpMethod.POST, reqMap, RestConst.PD_API_TIME_OUT, true);
					}
				}
				//logger.debug("API CALL RESULT = " + result);

			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		return resultCnt;
	}

	/**
	 * 상품 이미지 히스토리 수정 처리
	 * @param VO
	 * @return int
	 * @throws Exception
	 */
	public int updatePrdImageHist(PSCPPRD0006VO bean) throws Exception {
		int resultCnt = 0;
		try {
			resultCnt = pscpprd0006Dao.updatePrdImageHist(bean);
			if (resultCnt <= 0) {
				throw new IllegalArgumentException("상품 이미지 히스토리 수정 작업중에 오류가 발생하였습니다.");
			}
		} catch (Exception e) {
			throw e;
		}
		return resultCnt;
	}

	/**
	 * 유투브 링크 가져오기
	 */
	public DataMap selectYoutubeLink(Map<String, String> paramMap) throws Exception {
		return pscpprd0006Dao.selectYoutubeLink(paramMap);
	}

	/**
	 * Youtube 경로 수정
	 * @return int
	 * @throws Exception
	 */
	public int updateYoutubeSave(DataMap dm) throws Exception {
		int resultCnt = 0;

		try {
			resultCnt = pscpprd0006Dao.updateYoutubeSave(dm);

		} catch (Exception e) {
			throw e;
		}
		// API 연동 (EPC -> 통합BO API)
		if (resultCnt > 0) {
			// API 연동 (EPC -> 통합BO API)
			String prodCd = dm.getString("PROD_CD").substring(0, 1);
			String result = "";
			try {
				commonDao.commit();
				RestAPIUtil rest = new RestAPIUtil();
				if (!prodCd.equals("D")) {
					if (commonDao.selectEcApprovedYn(dm.getString("PROD_CD"))) {
						Map<String, Object> reqMap = new HashMap<String, Object>();
						reqMap.put("spdNo", dm.getString("PROD_CD"));
						result = rest.sendRestCall(RestConst.PD_API_0002, HttpMethod.POST, reqMap, RestConst.PD_API_TIME_OUT, true);
					}
				}
				//logger.debug("API CALL RESULT = " + result);

			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		return resultCnt;
	}

	//20180715 - 상품수정요청 시 승인요청 또는 반려 일 경우 알림 메시지 제공
	@Override
	public List<DataMap> selectAprvList(Map<String, String> paramMap) throws Exception {
		return pscpprd0006Dao.selectAprvList(paramMap);
	}

	/**
	 * 상품 와이드이미지 목록
	 * @param Map<String, String>
	 * @return List<VO>
	 * @throws Exception
	 */
	public List<PSCPPRD0006VO> selectWidePrdImageList(Map<String, String> paramMap) throws Exception {
		return pscpprd0006Dao.selectWidePrdImageList(paramMap);
	}

	//와이드이미지 등록여부 업데이트
	public int updateWideImgYN(DataMap map) throws Exception {
		int resultCnt = 0;
		try {
			resultCnt = pscpprd0006Dao.updateWideImgYN(map);
		} catch (Exception e) {
			throw e;
		}

		// API 연동 (EPC -> 통합BO API)
		if (resultCnt > 0) {
			String prodCd = map.getString("prodCd").substring(0, 1);
			String result = "";
			try {
				commonDao.commit();
				RestAPIUtil rest = new RestAPIUtil();
				if (!prodCd.equals("D")) {
					if (commonDao.selectEcApprovedYn(map.getString("prodCd"))) {
						Map<String, Object> reqMap = new HashMap<String, Object>();
						reqMap.put("spdNo", map.getString("prodCd"));
						result = rest.sendRestCall(RestConst.PD_API_0002, HttpMethod.POST, reqMap, RestConst.PD_API_TIME_OUT, true);
						//logger.debug("API CALL RESULT = " + result);
					}
				}
				//logger.debug("API CALL RESULT = " + result);

			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

		return resultCnt;
	}

	public DataMap selectMdSrcmkCd(DataMap paramMap) throws Exception {
		return pscpprd0006Dao.selectMdSrcmkCd(paramMap);
	}

	public PSCPPRD0006VO selectVideoUrl(PSCPPRD0006VO bean) throws Exception {
		return pscpprd0006Dao.selectVideoUrl(bean);
	}

	public int updateVideoUrl(PSCPPRD0006VO bean) throws Exception {
		int resultCnt = 0;
		try {
			resultCnt = pscpprd0006Dao.updateVideoUrl(bean);
		} catch (Exception e) {
			throw e;
		}

		// API 연동 (EPC -> 통합BO API)
		if (resultCnt > 0) {
			String prodCd = bean.getProdCd().substring(0, 1);
			String result = "";
			try {
				commonDao.commit();
				RestAPIUtil rest = new RestAPIUtil();
				if (!prodCd.equals("D")) {
					if (commonDao.selectEcApprovedYn(bean.getProdCd())) {
						Map<String, Object> reqMap = new HashMap<String, Object>();
						reqMap.put("spdNo", bean.getProdCd());
						result = rest.sendRestCall(RestConst.PD_API_0002, HttpMethod.POST, reqMap, RestConst.PD_API_TIME_OUT, true);
						//logger.debug("API CALL RESULT = " + result);
					}
				}
				//logger.debug("API CALL RESULT = " + result);

			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

		return resultCnt;
	}

	/**
	 * 대표이미지 변경 이력 등록 (For OSP)
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public int insertImgChgHist(DataMap dataMap) throws Exception {
		return pscpprd0006Dao.insertImgChgHist(dataMap);
	}

}
