package com.lottemart.epc.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.dao.PSCMPRD0001Dao;
import com.lottemart.epc.product.model.PSCMPRD0001VO;
import com.lottemart.epc.product.service.PSCMPRD0001Service;

/**
 * @Class Name : PSCMPRD0001ServiceImpl.java
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
@Service("pscmprd0001Service")
public class PSCMPRD0001ServiceImpl implements PSCMPRD0001Service {

	@Autowired
	private PSCMPRD0001Dao pscmprd0001Dao;

	/**
	 * 상품 구분 목록
	 * @param 
	 * @return List<DataMap>
	 * @throws Exception
	 */
	public List<DataMap> selectProdDivNCdList() throws Exception {
		return pscmprd0001Dao.selectProdDivNCdList();
	}

	/**
	 * 상품 목록 총카운트
	 * @param DataMap
	 * @return int
	 * @throws Exception
	 */
	public int selectPrdTotalCnt(DataMap paramMap) throws Exception {
		return pscmprd0001Dao.selectPrdTotalCnt(paramMap);
	}

	/**
	 * 상품 목록
	 * @param DataMap
	 * @return List
	 * @throws Exception
	 */
	public List<PSCMPRD0001VO> selectPrdList(DataMap paramMap) throws Exception {
		return pscmprd0001Dao.selectPrdList(paramMap);
	}

	public List<DataMap> selectPrdListForBatchImageUpload(DataMap paramMap) throws Exception {
		return pscmprd0001Dao.selectPrdListForBatchImageUpload(paramMap);
	}

	/**
	 * 상품코드 목록
	 * @param DataMap
	 * @return List
	 * @throws Exception
	 */
	public PSCMPRD0001VO selectProdCdInfo(String asKeywordValue) throws Exception {
		List<String> list = (List<String>) pscmprd0001Dao.selectProdCdInfoList(asKeywordValue);
		PSCMPRD0001VO pscmprd0001vo = new PSCMPRD0001VO();
		String prodCd = "";
		if (list != null && list.size() > 0) {
			int size = list.size();
			for (int i = 0; i < size; i++) {
				if (i >= 1) {
					prodCd += ",";
				}
				prodCd += list.get(i);
			}
		}
		pscmprd0001vo.setProdCd(prodCd);

		return pscmprd0001vo;
	}

	public List<PSCMPRD0001VO> selectProductExcel(DataMap paramMap) throws Exception {
		return pscmprd0001Dao.selectProductExcel(paramMap);
	}

}
