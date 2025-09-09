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




import com.lottemart.epc.product.dao.PSCPPRD0005Dao;
import com.lottemart.epc.product.dao.PSCPPRD0020Dao;
import com.lottemart.epc.product.model.PSCPPRD0005VO;
import com.lottemart.epc.product.model.PSCPPRD0020VO;
import com.lottemart.epc.product.service.PSCPPRD0020Service;
import com.lcnjf.util.StringUtil;
import com.lottemart.common.util.DataMap;




@Service("PSCPPRD0020Service")
public class PSCPPRD0020ServiceImpl implements PSCPPRD0020Service{
	@Autowired
	private PSCPPRD0020Dao PSCPPRD0020Dao;
	@Autowired
	private PSCPPRD0005Dao pscpprd0005Dao;
	/**
	 * 전자상거래 목록
	 * @return PSCPPRD0020VO
	 * @throws Exception
	 */
	public List<PSCPPRD0020VO> selectPrdCommerceList(Map<String, String> paramMap) throws Exception {
		return PSCPPRD0020Dao.selectPrdCommerceList(paramMap);
	}

	public List<DataMap> selectPrdCommerceCatCdList(Map<String, String> paramMap) throws Exception {
		return PSCPPRD0020Dao.selectPrdCommerceCatCdList(paramMap);
	}

	/**
	 * 추천할 상품 목록 카운트
	 * @return int
	 * @throws Exception
	 */
	public DataMap selectPrdCommerceCnt(Map<String, String> paramMap) throws Exception {
		return PSCPPRD0020Dao.selectPrdCommerceCnt(paramMap);
	}

	/**
	 * 전자상거래 수정, 삭제 처리
	 * @return int
	 * @throws Exception
	 */
	public int deletePrdCommerce(Map<String, String> paramMap) throws Exception {
		int resultCnt = PSCPPRD0020Dao.deletePrdCommerce(paramMap);
		return resultCnt;
	}

	/**
	 * 전자상거래 입력 처리
	 * @return int
	 * @throws Exception
	 */
	public int insertPrdCommerce(List<PSCPPRD0020VO> pscpprd0020VOList) throws Exception {
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

				resultCnt = PSCPPRD0020Dao.insertPrdCommerce(bean);
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
	public DataMap selectPrdCommerceTemp(Map<String, String> paramMap) throws Exception {
		return PSCPPRD0020Dao.selectPrdCommerceTempList(paramMap);
	}

	/**
	 * 전상법 수정자 정보 조회
	 * @return int
	 * @throws Exception
	 */
	public DataMap prodCommerceUpdateInfo(Map<String, String> paramMap) throws Exception {
		return PSCPPRD0020Dao.prodCommerceUpdateInfo(paramMap);
	}

	@Override
	public int updatePrdCommerce(GridData gdReq) throws Exception {
		// 파라미터
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("prodCd", gdReq.getParam("prodCd"));

		// 값 셋팅
		List<PSCPPRD0020VO> beanList = new ArrayList<PSCPPRD0020VO>();
		PSCPPRD0020VO bean;

		int rowCount = gdReq.getHeader("infoGrpCd").getRowCount();

		deletePrdCommerce(paramMap);

		for (int i = 0; i < rowCount; i++) {
			bean = new PSCPPRD0020VO();

			bean.setProdCd(gdReq.getParam("prodCd"));
			bean.setInfoGrpCd(gdReq.getHeader("infoGrpCd").getValue(i));
			bean.setInfoColCd(gdReq.getHeader("infoColCd").getValue(i));
			bean.setInfoColNm(gdReq.getHeader("infoColNm").getValue(i));
			bean.setInfoColDesc(gdReq.getHeader("infoColDesc").getValue(i));
			bean.setColVal(gdReq.getHeader("colVal").getValue(i));
			bean.setNewProdCd(gdReq.getHeader("newProdCd").getValue(i));
			bean.setRegId("SCM");


			beanList.add(bean);
		}

		int resultCnt = insertPrdCommerce(beanList);
		return resultCnt;
	}

	@Override
	public int updatePrdCommerceHist(HttpServletRequest request) throws Exception {
		// 파라미터
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("prodCd", request.getParameter("prodCd"));
		paramMap.put("typeCd", "003");

		// 값 셋팅
		List<PSCPPRD0020VO> beanList = new ArrayList<PSCPPRD0020VO>();
		PSCPPRD0020VO bean;
		String seq = "";

		DataMap map = new DataMap();
		map.put("prodCd", request.getParameter("prodCd"));
		map.put("typeCd", "003");

		int iCnt = pscpprd0005Dao.selectPrdMdAprvMstCnt(map);
		if (iCnt > 0){
			PSCPPRD0005VO prdMdAprInfo = pscpprd0005Dao.selectPrdMdAprvMst(paramMap);
			seq = prdMdAprInfo.getSeq();

		}else{
			PSCPPRD0005VO bean2 = new PSCPPRD0005VO();
			bean2.setProdCd(request.getParameter("prodCd"));

			bean2.setRegId(request.getParameter("vendorId"));
			bean2.setAprvCd("001");
			bean2.setTypeCd("003");
			bean2.setVendorId(request.getParameter("vendorId"));

			seq = pscpprd0005Dao.insertPrdMdAprvMst(bean2);
		}

		int rowCount = request.getParameterValues("chk").length;

		paramMap.put("seq", seq);
		deletePrdCommerceHist(paramMap);

		for (int i = 0; i < rowCount; i++) {
			bean = new PSCPPRD0020VO();

			bean.setSeq(seq);
			bean.setProdCd(request.getParameter("prodCd"));
			bean.setInfoGrpCd(request.getParameterValues("infoGrpCd")[i]);
			bean.setInfoColCd(request.getParameterValues("infoColCd")[i]);
			bean.setInfoColNm(request.getParameterValues("infoColNm")[i]);
			bean.setInfoColDesc(request.getParameterValues("infoColDesc")[i]);
			bean.setColVal(request.getParameterValues("colVal")[i]);
			bean.setNewProdCd(request.getParameterValues("pgmId")[i]);
			bean.setRegId(request.getParameter("vendorId"));
			
			if (StringUtil.getByteLength(request.getParameterValues("colVal")[i]) > 2000) {
				throw new IllegalArgumentException("입력값이 2000 Byte 초과 입니다. 2000 Byte 이하로 입력해주세요.");
			}

			beanList.add(bean);
		}

		int resultCnt = insertPrdCommerceHist(beanList);
		return resultCnt;
	}

	/**
	 * 전자상거래 수정, 삭제 처리
	 * @return int
	 * @throws Exception
	 */
	public int deletePrdCommerceHist(Map<String, String> paramMap) throws Exception {
		int resultCnt = PSCPPRD0020Dao.deletePrdCommerceHist(paramMap);
		return resultCnt;
	}

	/**
	 * 전자상거래 입력 처리
	 * @return int
	 * @throws Exception
	 */
	public int insertPrdCommerceHist(List<PSCPPRD0020VO> pscpprd0020VOList) throws Exception {
		int resultCnt = 0;
		int results = 0;

		try {

			PSCPPRD0020VO listBean;
			PSCPPRD0020VO bean;
			int listSize = pscpprd0020VOList.size();
			results = listSize;

			for (int i = 0; i < listSize; i++) {

				listBean = (PSCPPRD0020VO)pscpprd0020VOList.get(i);
				bean = new PSCPPRD0020VO();

				bean.setSeq(listBean.getSeq());
				bean.setInfoGrpCd(listBean.getInfoGrpCd());
				bean.setProdCd(listBean.getProdCd());
				bean.setInfoColCd(listBean.getInfoColCd());
				bean.setInfoColDesc(listBean.getInfoColDesc());
				bean.setColVal(listBean.getColVal());
				bean.setRegId(listBean.getRegId());
				bean.setNewProdCd(listBean.getProdCd());
				bean.setNewProdCd(listBean.getNewProdCd());
				//bean FOR 현재값
				//resultBean FOR 현재결과값

				resultCnt = PSCPPRD0020Dao.insertPrdCommerceHist(bean);
				if(resultCnt <= 0) {
					throw new IllegalArgumentException("속성데이터 입력 작업중에 오류가 발생하였습니다.");
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return results;
	}
}
