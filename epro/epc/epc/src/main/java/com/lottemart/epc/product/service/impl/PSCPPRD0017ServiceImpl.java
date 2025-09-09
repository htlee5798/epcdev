package com.lottemart.epc.product.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.dao.PSCPPRD0017Dao;
import com.lottemart.epc.product.model.PSCPPRD0005VO;
import com.lottemart.epc.product.model.PSCPPRD0017HistVO;
import com.lottemart.epc.product.model.PSCPPRD0017VO;
import com.lottemart.epc.product.service.PSCPPRD0017Service;

/**
 *
 * @Class Name : PSCPPRD0017ServiceImpl
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자	   수정내용
 *  -------    --------    ---------------------------
 * 2011. 12. 09. 오후 03:03:03 mjChoi
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Service("pscpprd0017Service")
public class PSCPPRD0017ServiceImpl implements PSCPPRD0017Service {

	@Autowired
	private PSCPPRD0017Dao pscpprd0017Dao;

	/**
	 * 상품 키워드 목록
	 * @return PSCPPRD0017VO
	 * @throws Exception
	 */
	public List<PSCPPRD0017VO> selectPrdKeywordList(Map<String, String> paramMap) throws Exception {
		return pscpprd0017Dao.selectPrdKeywordList(paramMap);
	}

	/**
	 * 상품 키워드 입력 처리
	 * @return int
	 * @throws Exception
	 */
	public int insertPrdKeyword(PSCPPRD0017VO bean) throws Exception {
		int resultCnt = 0;

		try {
			resultCnt = pscpprd0017Dao.insertPrdKeyword(bean); // 키워드 등록
			if (resultCnt <= 0) {
				throw new IllegalArgumentException("키워드 입력 작업중에 오류가 발생하였습니다.");
			}

			// 전체 키워드 셋팅
			List<PSCPPRD0017VO> prdList = pscpprd0017Dao.selectChkPrdTotalKeyword(bean);

			if (prdList != null && prdList.size() > 0) {
				PSCPPRD0017VO resultBean = (PSCPPRD0017VO) prdList.get(0);
				String totalKywrd = resultBean.getTotalKywrd();
				String seq = resultBean.getSeq();
				bean.setTotalKywrd(totalKywrd);

				if ("000".equals(seq)) { // 전체키워드 업데이트
					resultCnt = pscpprd0017Dao.updatePrdTotalKeyword(bean);
					if (resultCnt <= 0) {
						throw new IllegalArgumentException("전체 키워드 수정 작업중에 오류가 발생하였습니다.");
					}
				} else { // 전체키워드 입력
					resultCnt = pscpprd0017Dao.insertPrdTotalKeyword(bean);
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
	 * 상품 키워드 수정, 삭제 처리
	 * @return int
	 * @throws Exception
	 */
	public int updatePrdKeywordList(List<PSCPPRD0017VO> pbomprd0006VOList, String mode) throws Exception {

		int resultCnt = 0;
		int results = 0;

		try {
			if (pbomprd0006VOList != null && pbomprd0006VOList.size() > 0) {

				// 체크된 항목에 대해 개별 키워드 처리 (수정, 삭제)
				int listSize = pbomprd0006VOList.size();
				results = listSize;
				PSCPPRD0017VO bean2 = new PSCPPRD0017VO(); // 전체키워드

				for (int i = 0; i < listSize; i++) {
					PSCPPRD0017VO listBean = (PSCPPRD0017VO) pbomprd0006VOList.get(i);
					PSCPPRD0017VO bean = new PSCPPRD0017VO();
					bean.setProdCd(listBean.getProdCd());
					bean.setSearchKywrd(listBean.getSearchKywrd());
					bean.setSeq(listBean.getSeq());
					bean.setRegId(listBean.getRegId());
					bean2.setProdCd(listBean.getProdCd());
					bean2.setRegId(listBean.getRegId());

					if ("update".equals(mode)) { // 키워드 업데이트
						resultCnt = pscpprd0017Dao.updatePrdKeyword(bean);
						if (resultCnt <= 0) {
							throw new IllegalArgumentException("키워드 수정 작업중에 오류가 발생하였습니다. 체크된 항목중 " + (i + 1) + "번째 항목을 확인해주세요.");
						}
					} else { // 키워드 삭제
						resultCnt = pscpprd0017Dao.deletePrdKeyword(bean);
						if (resultCnt <= 0) {
							throw new IllegalArgumentException("키워드 수정 작업중에 오류가 발생하였습니다. 체크된 항목중 " + (i + 1) + "번째 항목을 확인해주세요.");
						}
					}
				}

				// 전체키워드 셋팅
				List<PSCPPRD0017VO> prdList = pscpprd0017Dao.selectChkPrdTotalKeyword(bean2);

				if (prdList != null && prdList.size() > 0) {
					PSCPPRD0017VO resultBean = (PSCPPRD0017VO) prdList.get(0);
					String totalKywrd = resultBean.getTotalKywrd();
					String seq = resultBean.getSeq();

					bean2.setTotalKywrd(totalKywrd);

					if ("000".equals(seq)) { // 전체키워드 업데이트
						resultCnt = pscpprd0017Dao.updatePrdTotalKeyword(bean2);
						if (resultCnt <= 0) {
							throw new IllegalArgumentException("전체 키워드 수정 작업중에 오류가 발생하였습니다.");
						}
					} else {
						if (totalKywrd != null && !"".equals(totalKywrd)) { // 전체키워드 입력
							resultCnt = pscpprd0017Dao.insertPrdTotalKeyword(bean2);
							if (resultCnt <= 0) {
								throw new IllegalArgumentException("전체 키워드 입력 작업중에 오류가 발생하였습니다.");
							}
						}
					}
				}
			}

		} catch (Exception e) {
			throw e;
		}

		return results;
	}

	//20180911 상품키워드 입력 기능 추가
	/**
	 * 상품키워드 이력
	 * @param Map<String, String>
	 * @return PSCPPRD0005VO
	 * @throws Exception
	 */
	public PSCPPRD0005VO selectPrdMdAprvMst(Map<String, String> paramMap) throws Exception {
		return pscpprd0017Dao.selectPrdMdAprvMst(paramMap);
	}

	/**
	 * 상품키워드 이력 마스터
	 * @param DataMap
	 * @return int
	 * @throws Exception
	 */
	public int selectPrdMdAprvMstCnt(DataMap paramMap) throws Exception {
		return pscpprd0017Dao.selectPrdMdAprvMstCnt(paramMap);
	}

	/**
	 * 상품키워드 이력 마스터 입력 처리
	 * @param VO
	 * @return int
	 * @throws Exception
	 */
	public String insertPrdMdAprvMst(PSCPPRD0005VO bean) throws Exception {
		return  (String) pscpprd0017Dao.insertPrdMdAprvMst(bean);
	}

	/**
	 * 상품키워드 이력 원본 삭제
	 * @Method deletePrdKeywordHist
	 * @param DataMap
	 * @return INT
	 * @throws Exception
	 */
	public int deletePrdKeywordHistAll(DataMap paramMap) throws Exception {
		return pscpprd0017Dao.deletePrdKeywordHistAll(paramMap);
	}

	/**
	 * 상품키워드 이력 원본 입력
	 * @Method insertPrdKeywordHist
	 * @param DataMap
	 * @return INT
	 * @throws Exception
	 */
	public int insertPrdKeywordHistAll(DataMap paramMap) throws Exception {
		return pscpprd0017Dao.insertPrdKeywordHistAll(paramMap);
	}

	/**
	 * 상품 키워드 입력 처리
	 * @return INT
	 * @throws Exception
	 */
	public int insertPrdKeywordHist(PSCPPRD0017HistVO bean) throws Exception {
		int resultCnt = 0;

		try {
			// 키워드 등록
			resultCnt = pscpprd0017Dao.insertPrdKeywordHist(bean);
			if (resultCnt <= 0) {
				throw new IllegalArgumentException("키워드 입력 작업중에 오류가 발생하였습니다.");
			}

			// 전체 키워드 셋팅
			List<PSCPPRD0017HistVO> prdList = pscpprd0017Dao.selectChkPrdTotalKeywordHist(bean);

			if (!(prdList == null || prdList.size() == 0)) {
				PSCPPRD0017HistVO resultBean = (PSCPPRD0017HistVO) prdList.get(0);
				String totalKywrd = resultBean.getTotalKywrd();
				String seq = resultBean.getSeq();
				bean.setTotalKywrd(totalKywrd);

				if ("000".equals(seq)) { // 전체키워드 업데이트
					resultCnt = pscpprd0017Dao.updatePrdTotalKeywordHist(bean);
					if (resultCnt <= 0) {
						throw new IllegalArgumentException("전체 키워드 수정 작업중에 오류가 발생하였습니다.");
					}
				} else { // 전체키워드 입력
					resultCnt = pscpprd0017Dao.insertPrdTotalKeywordHist(bean);
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
	 * 상품 키워드 수정, 삭제 처리
	 * @return INT
	 * @throws Exception
	 */
	public int updatePrdKeywordHistList(List<PSCPPRD0017HistVO> pscpprd0017HistList, String mode) throws Exception {

		int resultCnt = 0;
		int results = 0;

		try {
			if (pscpprd0017HistList != null && pscpprd0017HistList.size() > 0) {

				// 체크된 항목에 대해 개별 키워드 처리 (수정, 삭제)
				int listSize = pscpprd0017HistList.size();
				results = listSize;
				PSCPPRD0017HistVO bean2 = new PSCPPRD0017HistVO();//전체키워드

				for (int i = 0; i < listSize; i++) {
					PSCPPRD0017HistVO listBean = pscpprd0017HistList.get(i);
					PSCPPRD0017HistVO bean = new PSCPPRD0017HistVO();
					bean.setKeywordSeq(listBean.getKeywordSeq());
					bean.setProdCd(listBean.getProdCd());
					bean.setSearchKywrd(listBean.getSearchKywrd());
					bean.setSeq(listBean.getSeq());
					bean.setRegId(listBean.getRegId());
					bean2.setKeywordSeq(listBean.getKeywordSeq());
					bean2.setProdCd(listBean.getProdCd());
					bean2.setRegId(listBean.getRegId());

					if ("update".equals(mode)) { // 키워드 업데이트
						resultCnt = pscpprd0017Dao.updatePrdKeywordHist(bean);
						if (resultCnt <= 0) {
							throw new IllegalArgumentException("키워드 수정 작업중에 오류가 발생하였습니다. 체크된 항목중 " + (i + 1) + "번째 항목을 확인해주세요.");
						}
					} else { // 키워드 삭제
						resultCnt = pscpprd0017Dao.deletePrdKeywordHist(bean);
						if (resultCnt <= 0) {
							throw new IllegalArgumentException("키워드 수정 작업중에 오류가 발생하였습니다. 체크된 항목중 " + (i + 1) + "번째 항목을 확인해주세요.");
						}
					}
				}

				// 전체키워드 셋팅
				List<PSCPPRD0017HistVO> prdList = pscpprd0017Dao.selectChkPrdTotalKeywordHist(bean2);

				if (prdList != null && prdList.size() > 0) {
					PSCPPRD0017HistVO resultBean = (PSCPPRD0017HistVO) prdList.get(0);
					String totalKywrd = resultBean.getTotalKywrd();
					String seq = resultBean.getSeq();
					bean2.setTotalKywrd(totalKywrd);

					if ("000".equals(seq)) { // 전체키워드 업데이트
						resultCnt = pscpprd0017Dao.updatePrdTotalKeywordHist(bean2);
						if (resultCnt <= 0) {
							throw new IllegalArgumentException("전체 키워드 수정 작업중에 오류가 발생하였습니다.");
						}
					} else {
						if (totalKywrd != null && !"".equals(totalKywrd)) { // 전체키워드 입력
							resultCnt = pscpprd0017Dao.insertPrdTotalKeywordHist(bean2);
							if (resultCnt <= 0) {
								throw new IllegalArgumentException("전체 키워드 입력 작업중에 오류가 발생하였습니다.");
							}
						}
					}
				}
			}

		} catch (Exception e) {
			throw e;
		}

		return results;
	}
	//20180911 상품키워드 입력 기능 추가

	public int mergePrdKeywordHistAll(DataMap paramMap) throws Exception {
		return pscpprd0017Dao.mergePrdKeywordHistAll(paramMap);
	}

	public int masterKeywordHistUpdate(DataMap paramMap) throws Exception {
		return pscpprd0017Dao.masterKeywordHistUpdate(paramMap);
	}

}
