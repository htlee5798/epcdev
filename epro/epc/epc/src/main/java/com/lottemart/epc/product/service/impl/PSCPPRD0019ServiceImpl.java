package com.lottemart.epc.product.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

import com.lottemart.common.login.model.LoginSession;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.dao.PSCPPRD0019Dao;
import com.lottemart.epc.product.model.PSCPPRD0019VO;
import com.lottemart.epc.product.service.PSCPPRD0019Service;

/**
 *
 * @Class Name : PSCPPRD0019ServiceImpl
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자	   수정내용
 *  -------    --------    ---------------------------
 * 2011. 9. 7   jib
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Service("PSCPPRD0019Service")
public class PSCPPRD0019ServiceImpl implements PSCPPRD0019Service{
	private static final Logger logger = LoggerFactory.getLogger(PSCPPRD0019ServiceImpl.class);
	@Autowired
	private PSCPPRD0019Dao PSCPPRD0019Dao;

	/**
	 * 추가속성 목록
	 * @return PSCPPRD0019VO
	 * @throws Exception
	 */
	public List<PSCPPRD0019VO> selectPrdAttributeList(Map<String, String> paramMap) throws Exception {
		return PSCPPRD0019Dao.selectPrdAttributeList(paramMap);
	}

	/**
	 * 추가속성 입력, 삭제 처리
	 * @return int
	 * @throws Exception

	public int updatePrdAttributeList(List<PSCPPRD0019VO> PSCPPRD0019VOList, String mode) throws Exception {
		int resultCnt = 0;
		int results = 0;

		try {
			// 채크된 항목에 대해 개별 추가속성 처리 (입력, 삭제)

			PSCPPRD0019VO ListBean;
			PSCPPRD0019VO bean;
			int listSize = PSCPPRD0019VOList.size();
			results = listSize;

			for (int i = 0; i < listSize; i++) {
				ListBean = (PSCPPRD0019VO)PSCPPRD0019VOList.get(i);
				bean = new PSCPPRD0019VO();
				bean.setProdCd(ListBean.getProdCd());
				bean.setAddColSeq(ListBean.getAddColSeq());
				bean.setAddColValSeq(ListBean.getAddColValSeq());
				bean.setCategoryId(ListBean.getCategoryId());
				bean.setColVal(ListBean.getColVal());
				bean.setCondSearchYn(ListBean.getCondSearchYn());

				bean.setRegId(ListBean.getRegId());
				if("insert".equals(mode)) {
					// 추가속성 입력
					resultCnt = PSCPPRD0019Dao.insertPrdAttributeList(bean);
					if(resultCnt <= 0) {
						throw new Exception("추가속성 입력 작업중에 오류가 발생하였습니다. 채크된 항목중 "+(i+1)+"번째 항목을 확인해주세요.");
					}
				} else {
					// 추가속성 삭제
					resultCnt = PSCPPRD0019Dao.deletePrdAttribute(bean);
					if(resultCnt <= 0) {
						throw new Exception("추가속성 삭제 작업중에 오류가 발생하였습니다. 채크된 항목중 "+(i+1)+"번째 항목을 확인해주세요.");
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return results;
	}*/

	/**
	 * 추가속성 입력, 삭제 처리
	 * @return int
	 * @throws Exception
	 */
	public int updatePrdAttributeList(HttpServletRequest request) throws Exception {
		int resultCnt = 0;
		LoginSession loginSession = LoginSession.getLoginSession(request);
		DataMap param = new DataMap(request);

		try {
			// 체크된 항목에 대해 개별 추가속성 처리 (입력, 삭제)
			logger.debug("[ updatePrdAttributeList for loof start ]");

			String mode = param.getString("mode");
			String prodCd = param.getString("prodCd");
			String[] categoryIds 	= request.getParameterValues("categoryId");
			String[] addColSeqs 	= request.getParameterValues("addColSeq");
			String[] addColValSeqs 	= request.getParameterValues("addColValSeq");
			String[] condSearchYns 	= request.getParameterValues("condSearchYn");
			String[] colVals 		= request.getParameterValues("colVal");

			for (int i=0; i<categoryIds.length; i++) {
				DataMap dm = new DataMap();
				dm.put("prodCd", 		prodCd);
				dm.put("categoryId", 	categoryIds[i]);
				dm.put("addColSeq", 	addColSeqs[i]);
				dm.put("addColValSeq", 	addColValSeqs[i]);
				dm.put("condSearchYn", 	condSearchYns[i]);
				dm.put("colVal", 		colVals[i]);
				dm.put("regId", 		param.getString("vendorId"));

				if ("insert".equals(mode)) {
					// 추가속성 입력
					logger.debug("[ insertPrdAttributeList ]");
					resultCnt += PSCPPRD0019Dao.insertPrdAttributeList(dm);
					if (resultCnt <= 0) {
						logger.debug("[ insertPrdAttributeList error ]");
						throw new IllegalArgumentException("추가속성 입력 작업중에 오류가 발생하였습니다. \n체크된 항목중 "+(i+1)+"번째 항목을 확인해주세요.");
					}
				} else {
					// 추가속성 삭제
					logger.debug("[ deletePrdAttribute ]");
					resultCnt += PSCPPRD0019Dao.deletePrdAttribute(dm);
					if (resultCnt <= 0) {
						logger.debug("[ deletePrdAttribute error ]");
						throw new IllegalArgumentException("추가속성 삭제 작업중에 오류가 발생하였습니다. \n체크된 항목중 "+(i+1)+"번째 항목을 확인해주세요.");
					}
				}
			}
			logger.debug("[ updatePrdAttributeList for loof end ]");
		} catch (Exception e) {
			logger.debug("[ Exception ]");
			throw e;
		}
		return resultCnt;
	}

	/**
	 * 추가속성 카테고리
	 * @return PSCPPRD0019VO
	 * @throws Exception
	 */
	public PSCPPRD0019VO selectPrdAttributeCategory(Map<String, String> paramMap) throws Exception {
		return PSCPPRD0019Dao.selectPrdAttributeCategory(paramMap);
	}

	/**
	 * 추가속성 항목값
	 * @return PSCPPRD0019VO
	 * @throws Exception
	 */
	public PSCPPRD0019VO selectPrdAttributeColVal(Map<String, String> paramMap) throws Exception {
		return PSCPPRD0019Dao.selectPrdAttributeColVal(paramMap);
	}

	/**
	 * 추가속성 카테고리 목록
	 * @return PSCPPRD0019VO
	 * @throws Exception
	 */
	public List<PSCPPRD0019VO> selectPrdAttributeCategoryList(Map<String, String> paramMap) throws Exception {
		return PSCPPRD0019Dao.selectPrdAttributeCategoryList(paramMap);
	}

	/**
	 * 추가속성 N 입력, 업데이트 처리
	 * @return int
	 * @throws Exception
	 */
	public int insertPrdAttribute(PSCPPRD0019VO bean) throws Exception {
		int resultCnt = 0;

		try {
			String mode = (String)bean.getMode();
			if("insert".equals(mode)) {
				// 추가속성 입력
				resultCnt = PSCPPRD0019Dao.insertPrdAttribute(bean);
				if(resultCnt <= 0) {
					throw new IllegalArgumentException("추가속성 입력 작업중에 오류가 발생하였습니다.");
				}
			} else {
				// 추가속성 업데이트
				resultCnt = PSCPPRD0019Dao.updatePrdAttribute(bean);
				if(resultCnt <= 0) {
					throw new IllegalArgumentException("추가속성 업데이트 작업중에 오류가 발생하였습니다.");
				}
			}

		} catch (Exception e) {
			throw e;
		}
		return resultCnt;
	}
}