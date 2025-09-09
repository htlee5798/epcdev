package com.lottemart.epc.product.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xlib.cmc.GridData;




import com.lottemart.epc.product.dao.PSCPPRD0022Dao;
import com.lottemart.epc.product.model.PSCPPRD0020VO;
import com.lottemart.epc.product.service.PSCPPRD0022Service;
import com.lottemart.common.util.DataMap;




@Service("PSCPPRD0022Service")
public class PSCPPRD0022ServiceImpl implements PSCPPRD0022Service{
	@Autowired
	private PSCPPRD0022Dao PSCPPRD0022Dao;

	/**
	 * 전자상거래 목록
	 * @return PSCPPRD0020VO
	 * @throws Exception
	 */
	public List<PSCPPRD0020VO> selectPrdCertList(Map<String, String> paramMap) throws Exception {
		return PSCPPRD0022Dao.selectPrdCertList(paramMap);
	}

	public List<DataMap> selectPrdCertCatCdList(Map<String, String> paramMap) throws Exception {
		return PSCPPRD0022Dao.selectPrdCertCatCdList(paramMap);
	}

	/**
	 * 추천할 상품 목록 카운트
	 * @return int
	 * @throws Exception
	 */
	public DataMap selectPrdCertCnt(Map<String, String> paramMap) throws Exception {
		return PSCPPRD0022Dao.selectPrdCertCnt(paramMap);
	}

	/**
	 * 전자상거래 수정, 삭제 처리
	 * @return int
	 * @throws Exception
	 */
	public int deletePrdCert(Map<String, String> paramMap) throws Exception {
		int resultCnt = PSCPPRD0022Dao.deletePrdCert(paramMap);
		return resultCnt;
	}

	/**
	 * 전자상거래 입력 처리
	 * @return int
	 * @throws Exception
	 */
	public int insertPrdCert(List<PSCPPRD0020VO> pscpprd0020VOList) throws Exception {
		int resultCnt = 0;
		int results = 0;

		try {

			PSCPPRD0020VO ListBean;
			PSCPPRD0020VO bean;
			int listSize = pscpprd0020VOList.size();
			results = listSize;

			for (int i = 0; i < listSize; i++) {

				ListBean = (PSCPPRD0020VO)pscpprd0020VOList.get(i);
				bean = new PSCPPRD0020VO();


				bean.setInfoGrpCd(ListBean.getInfoGrpCd());
				bean.setProdCd(ListBean.getProdCd());
				bean.setInfoColCd(ListBean.getInfoColCd());
				bean.setInfoColDesc(ListBean.getInfoColDesc());
				bean.setColVal(ListBean.getColVal());
				bean.setRegId(ListBean.getRegId());
				bean.setNewProdCd(ListBean.getNewProdCd());
				//bean FOR 현재값
				//resultBean FOR 현재결과값

				resultCnt = PSCPPRD0022Dao.insertPrdCert(bean);
				if(resultCnt <= 0) {
					throw new IllegalArgumentException("속성데이터 입력 작업중에 오류가 발생하였습니다.");
				}

			}
		} catch (Exception e) {
			throw e;
		}
		return results;
	}

	/**
	 * 추천할 상품 목록 카운트
	 * @return int
	 * @throws Exception
	 */
	public DataMap selectPrdCertTemp(Map<String, String> paramMap) throws Exception {
		return PSCPPRD0022Dao.selectPrdCertTempList(paramMap);
	}

	/**
	 * 전상법 수정자 정보 조회
	 * @return int
	 * @throws Exception
	 */
	@Override
	public DataMap prodCertUpdateInfo(Map<String, String> paramMap) throws Exception {
		return PSCPPRD0022Dao.prodCertUpdateInfo(paramMap);
	}

	@Override
	public int updatePrdCert(HttpServletRequest request) throws Exception {
		// 파라미터
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("prodCd", request.getParameter("prodCd"));

		// 값 셋팅
		List<PSCPPRD0020VO> beanList = new ArrayList<PSCPPRD0020VO>();
		PSCPPRD0020VO bean;

		int rowCount = request.getParameterValues("chk").length;

		deletePrdCert(paramMap);

		for (int i = 0; i < rowCount; i++) {
			bean = new PSCPPRD0020VO();

			bean.setProdCd(request.getParameter("prodCd"));
			bean.setInfoGrpCd(request.getParameterValues("infoGrpCd")[i]);
			bean.setInfoColCd(request.getParameterValues("infoColCd")[i]);
			bean.setInfoColNm(request.getParameterValues("infoColNm")[i]);
			bean.setInfoColDesc(request.getParameterValues("infoColDesc")[i]);
			bean.setColVal(request.getParameterValues("colVal")[i]);
			bean.setNewProdCd(request.getParameterValues("pgmId")[i]);
			bean.setRegId(request.getParameter("vendorId"));

			beanList.add(bean);
		}

		int resultCnt = insertPrdCert(beanList);
		return resultCnt;
	}


}
