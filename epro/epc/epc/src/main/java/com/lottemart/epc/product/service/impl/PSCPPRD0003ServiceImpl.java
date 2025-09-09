package com.lottemart.epc.product.service.impl;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.exception.TopLevelException;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.dao.PSCPPRD0003Dao;
import com.lottemart.epc.product.model.PSCPPRD0003VO;
import com.lottemart.epc.product.service.PSCPPRD0003Service;

/**
 * @Class Name : PSCPPRD0003ServiceImpl.java
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
@Service("pscpprd0003Service")
public class PSCPPRD0003ServiceImpl implements PSCPPRD0003Service {

	@Autowired
	private PSCPPRD0003Dao pscpprd0003Dao;

	/**
	 * 단품 칼라 목록
	 * @param 
	 * @return DataMap
	 * @throws Exception
	 */
	public List<DataMap> selectPrdItemColorList() throws Exception {
		return pscpprd0003Dao.selectPrdItemColorList();
	}

	/**
	 * 단품 사이즈 구분 목록
	 * @param 
	 * @return DataMap
	 * @throws Exception
	 */
	public List<DataMap> selectPrdItemSizeCategoryList() throws Exception {
		return pscpprd0003Dao.selectPrdItemSizeCategoryList();
	}

	/**
	 * 단품 사이즈 목록
	 * @param String
	 * @return DataMap
	 * @throws Exception
	 */
	public List<DataMap> selectPrdItemSizeList(String szCatCd) throws Exception {
		return pscpprd0003Dao.selectPrdItemSizeList(szCatCd);
	}

	/**
	 * 인터넷 전용상품 여부
	 * @param Map<String, String>
	 * @return int
	 * @throws Exception
	 */
	public int selectPrdType(Map<String, String> paramMap) throws Exception {
		return pscpprd0003Dao.selectPrdType(paramMap);
	}

	/**
	 * 속성 값 체크
	 * @return int
	 * @throws Exception
	 */
	@Override
	public int selectPrdItemType(Map<String, String> paramMap) throws Exception {
		return pscpprd0003Dao.selectPrdItemType(paramMap);
	}

	private void prdItemMapPut(String currItemCd, StringBuffer attrNm, StringBuffer attrValNm, Map<String, PSCPPRD0003VO> listMap) {
		PSCPPRD0003VO pscpprd0003vo = new PSCPPRD0003VO();
		attrNm.setLength(attrNm.length() - 3);
		attrValNm.setLength(attrValNm.length() - 3);
		pscpprd0003vo.setItemCd(currItemCd);
		pscpprd0003vo.setAttrNm(attrNm.toString());
		pscpprd0003vo.setAttrValNm(attrValNm.toString());
		listMap.put(currItemCd, pscpprd0003vo);
	}

	private void prdItemSet(String currItemCd, String classNm, StringBuffer szCatCd, StringBuffer attrsNm, Map<String, PSCPPRD0003VO> listMap) {
		PSCPPRD0003VO pscpprd0003vo = listMap.get(currItemCd);
		if (pscpprd0003vo != null) {
			szCatCd.setLength(szCatCd.length() - 1);
			attrsNm.setLength(attrsNm.length() - 1);
			pscpprd0003vo.setItemCd(currItemCd);
			pscpprd0003vo.setClassNm(classNm);
			pscpprd0003vo.setSzCatCd(szCatCd.toString());
			pscpprd0003vo.setAttrsNm(attrsNm.toString());
		}
		//listMap.put(currItemCd, pbomprd0003vo);
	}

	/**
	 * 단품 정보 목록
	 * @param paramMap
	 * @return PSCPPRD0003VO
	 * @throws Exception
	 */
	public List<PSCPPRD0003VO> selectPrdItemList(Map<String, String> paramMap) throws Exception {

		List<PSCPPRD0003VO> list1 = pscpprd0003Dao.selectPrdItemList1(paramMap);
		List<PSCPPRD0003VO> list2 = pscpprd0003Dao.selectPrdItemList2(paramMap);

		List<PSCPPRD0003VO> listAll = null;
		Map<String, PSCPPRD0003VO> listMap = new LinkedHashMap<String, PSCPPRD0003VO>();

//		try {
			if (list1 != null && list1.size() > 0) {
				int list1size = list1.size();
				String bfItemCd = null;
				StringBuffer attrNm = new StringBuffer();
				StringBuffer attrValNm = new StringBuffer();

				for (int i = 0; i < list1size; i++) {
					PSCPPRD0003VO vo1 = list1.get(i);
					if (i == 0) {
						bfItemCd = vo1.getItemCd();
					}

					if (!bfItemCd.equals(vo1.getItemCd())) {
						this.prdItemMapPut(bfItemCd, attrNm, attrValNm, listMap);
						//logger.debug("ItemCd: " + currItemCd + "\n attrValNm: " + pscpprd0003vo.getAttrNm() + "\n attrValNm: " + pscpprd0003vo.getAttrValNm());

						attrNm.setLength(0);
						attrValNm.setLength(0);
						bfItemCd = vo1.getItemCd();
					}

					attrNm.append(vo1.getAttrNm()).append(" | ");
					attrValNm.append(vo1.getAttrValNm()).append(" | ");

					if (i == list1size - 1) {
						this.prdItemMapPut(bfItemCd, attrNm, attrValNm, listMap);
					}
				}
			}

			if (list2 != null && list2.size() > 0) {
				int list2size = list2.size();
				String currItemCd = null;
				String classNm = null;
				StringBuffer szCatCd = new StringBuffer();
				StringBuffer attrsNm = new StringBuffer();

				for (int i = 0; i < list2size; i++) {

					PSCPPRD0003VO vo2 = list2.get(i);
					if (i == 0) {
						currItemCd = vo2.getItemCd();
					}

					if (!currItemCd.equals(vo2.getItemCd())) {
						this.prdItemSet(currItemCd, classNm, szCatCd, attrsNm, listMap);
						//logger.debug("ItemCd: " + currItemCd + "\n classNm: " + pbomprd0003vo.getClassNm() + "\n szCatCd: " + pbomprd0003vo.getSzCatCd() + "\n attrsNm: " + pbomprd0003vo.getAttrsNm());

						szCatCd.setLength(0);
						attrsNm.setLength(0);
						currItemCd = vo2.getItemCd();
					}

					classNm = vo2.getClassNm();
					szCatCd.append(vo2.getSzCatCd()).append(",");
					attrsNm.append(vo2.getAttrsNm()).append(",");

					if (i == list2size - 1) {
						this.prdItemSet(currItemCd, classNm, szCatCd, attrsNm, listMap);
					}

				}
			}

			listAll = new ArrayList<PSCPPRD0003VO>(listMap.values());
//		} catch(Exception e) {
//			e.printStackTrace();
//		}

		return listAll;
	}

	private void prdItemOnlineListAdd(String currItemCd, StringBuffer attrNm, StringBuffer attrValNm, String optnDesc, String rservStkQty, List<PSCPPRD0003VO> listAll) {
		PSCPPRD0003VO pscpprd0003vo = new PSCPPRD0003VO();
		attrNm.setLength(attrNm.length() - 3);
		attrValNm.setLength(attrValNm.length() - 3);
		pscpprd0003vo.setItemCd(currItemCd);
		pscpprd0003vo.setAttrNm(attrNm.toString());
		pscpprd0003vo.setAttrValNm(attrValNm.toString());
		pscpprd0003vo.setOptnDesc(optnDesc);
		pscpprd0003vo.setRservStkQty(rservStkQty);
		listAll.add(pscpprd0003vo);
	}

	/**
	 * 단품 정보 목록
	 * @return PSCPPRD0003VO
	 * @throws Exception
	 */
	public List<PSCPPRD0003VO> selectPrdItemOnlineList(Map<String, String> paramMap) throws Exception {

		List<PSCPPRD0003VO> list1 = pscpprd0003Dao.selectPrdItemOnlineList(paramMap);
		List<PSCPPRD0003VO> listAll = new ArrayList<PSCPPRD0003VO>();

		if (list1 != null && list1.size() > 0) {

			int list1size = list1.size();
			String bfItemCd = null;
			StringBuffer attrNm = new StringBuffer();
			String optnDesc = null;
			StringBuffer attrValNm = new StringBuffer();
			String rservStkQty = null;

			for (int i = 0; i < list1size; i++) {
				PSCPPRD0003VO vo1 = list1.get(i);

				if (i == 0) {
					bfItemCd = vo1.getItemCd();
				}

				if (!bfItemCd.equals(vo1.getItemCd())) {
					this.prdItemOnlineListAdd(bfItemCd, attrNm, attrValNm, optnDesc, rservStkQty, listAll);
					//logger.debug("ItemCd: " + currItemCd + "\n attrValNm: " + pscpprd0003vo.getAttrNm() + "\n attrValNm: " + pscpprd0003vo.getAttrValNm());
					attrNm.setLength(0);
					attrValNm.setLength(0);
					bfItemCd = vo1.getItemCd();
				}

				attrNm.append(vo1.getAttrNm()).append(" | ");
				attrValNm.append(vo1.getAttrValNm()).append(" | ");
				optnDesc = vo1.getOptnDesc();
				rservStkQty = vo1.getRservStkQty();

				if (i == list1size - 1) {
					this.prdItemOnlineListAdd(bfItemCd, attrNm, attrValNm, optnDesc, rservStkQty, listAll);
				}
			}
		}
		return listAll;
	}

	/**
	 * 단품 정보
	 * @param paramMap
	 * @return PSCPPRD0003VO
	 * @throws Exception
	 */
	public PSCPPRD0003VO selectPrdItem(Map<String, String> paramMap) throws Exception {
		return pscpprd0003Dao.selectPrdItem(paramMap);
	}

	/**
	 * 단품 정보 입력 처리
	 * @param VO
	 * @return int
	 * @throws Exception
	 */
	public int insertPrdItem(PSCPPRD0003VO bean) throws Exception {
		int resultCnt = 0;

		try {
			// 단품 입력
			resultCnt = pscpprd0003Dao.insertPrdItem(bean);
			if (resultCnt <= 0) {
				throw new TopLevelException("단품 정보 입력 작업중에 오류가 발생하였습니다.");
			}

		} catch (Exception e) {
			throw e;
		}

		return resultCnt;
	}
	
	/**
	 * 단품 정보 수정 처리
	 * @param VO
	 * @return int
	 * @throws Exception
	 */
	public int updatePrdItem(PSCPPRD0003VO bean) throws Exception {
		int resultCnt = 0;

		try {
			// 단품 수정
			resultCnt = pscpprd0003Dao.updatePrdItem(bean);
			if (resultCnt <= 0) {
				throw new TopLevelException("단품 정보 수정 작업중에 오류가 발생하였습니다.");
			}

		} catch (Exception e) {
			throw e;
		}

		return resultCnt;
	}

	/**
	 * 단품 정보 수정 처리(온라인)
	 * @return int
	 * @throws Exception
	 */
	public int updateTprItemList(PSCPPRD0003VO bean, String mode) throws Exception {
		int resultCnt = 0;

		try {

			// 단품 수정
			if ("insert".equals(mode)) {
				String itemCd = pscpprd0003Dao.insertTprItemList(bean);
				bean.setItemCd(itemCd);

				resultCnt += pscpprd0003Dao.insertTprStoreItemList(bean);

				// 2016.03.02 by kmlee 단품이 2개 이상일 때 TPR_PRODUCT.VARIATION_YN = 'Y'로 UPDATE
				if (!"001".equals(bean.getItemCd()))
					pscpprd0003Dao.updateTprProductVariation(bean);

			} else {
				pscpprd0003Dao.updateTprItemList(bean);
				resultCnt += pscpprd0003Dao.updateTprStoreItemList(bean);
			}

			if (resultCnt <= 0) {
				throw new TopLevelException("단품 정보 수정 작업중에 오류가 발생하였습니다.");
			}

		} catch (Exception e) {
			throw e;
		}
		return resultCnt;
	}

	/**
	 * 단품 사이즈 콤보 목록
	 * @param map
	 * @return List<DataMap>
	 * @throws Exception
	 */
	public List<DataMap> selectPrdItemSizeList(Map<String, String> map) throws Exception {
		return pscpprd0003Dao.selectPrdItemSizeList(map);
	}

}
