/**
 * @prjectName  : lottemart 프로젝트
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.product.service.impl;

import java.util.List;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.dao.PSCMPRD0009Dao;
import com.lottemart.epc.product.model.PSCMPRD0009VO;
import com.lottemart.epc.product.service.PSCMPRD0009Service;

/**
 * @Class Name : PSCMPRD0009ServiceImpl
 * @Description : 상품가격변경요청리스트를 조회하는 ServiceImpl 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 4:44:41 yskim
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Service("PSCMPRD0009Service")
public class PSCMPRD0009ServiceImpl implements PSCMPRD0009Service {

	@Autowired
	private PSCMPRD0009Dao pscmprd0009Dao;

	/**
	 * Desc : 상품가격변경요청리스트 조회하는 메소드
	 * @Method Name : selectPriceChangeList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public List<PSCMPRD0009VO> selectPriceChangeList(DataMap paramMap) throws Exception {
		return pscmprd0009Dao.selectPriceChangeList(paramMap);
	}

	/**
	 * Desc : 상품가격변경요청리스트 데이터 등록 메소드 (변경요청판매가가 인상일 경우 승인여부를 'Y'로 인하일 경우 'N'으로 설정)
	 * @Method Name : insertPriceChangeReq
	 * @param pscmprd0009VO
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public int insertPriceChangeReq(PSCMPRD0009VO pscmprd0009VO) throws Exception {
		int resultCnt = 0;
		try {

//		int size = pscmprd0009VO.size();
			resultCnt = pscmprd0009Dao.insertPriceChangeReq(pscmprd0009VO);
		} catch (Exception e) {
			return 0;
		}
		return resultCnt;

//		for(int i = 0; i < size; i++) {
//			PSCMPRD0009VO paramVO = pscmprd0009VO.get(i);
//
//			String vendorId = paramVO.getVendorId();
//
//			if(true) {
//				paramVO.setReqPsn(vendorId);
//				paramVO.setAprvYn("N");
//				paramVO.setRegId(vendorId);
//				paramVO.setModId(vendorId);
//
//				if("C".equals(paramVO.getIsUpd())) {
//					pscmprd0009Dao.insertPriceChangeReq(paramVO);
//				} else if("U".equals(paramVO.getIsUpd())) {
//					pscmprd0009Dao.updatePriceChangeReq(paramVO);
//				} else {
//					pscmprd0009Dao.insertPriceChangeReq(paramVO);
//				}
//			} else {
//				paramVO.setReqPsn(vendorId);
//				paramVO.setAprvYn("N");    //이하도 이젠 승인 필요 이동빈 수정
//				paramVO.setRegId(vendorId);
//				paramVO.setModId(vendorId);
//
//				if("C".equals(paramVO.getIsUpd())) {
//					pscmprd0009Dao.insertPriceChangeReq(paramVO);
//				} else if("U".equals(paramVO.getIsUpd())) {
//					pscmprd0009Dao.updatePriceChangeReq(paramVO);
//				} else {
//					pscmprd0009Dao.insertPriceChangeReq(paramVO);
//				}
//			}
//		}
	}

	/**
	 * Desc : 상품가격변경요청리스트 데이터 등록 메소드 (변경요청판매가가 인상일 경우 승인여부를 'Y'로 인하일 경우 'N'으로 설정)
	 * @Method Name : insertPriceChangeReq
	 * @param pscmprd0009VO
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public int updatePriceChangeReq(PSCMPRD0009VO pscmprd0009VO) throws Exception {
		int resultCnt = 0;
		try {

			resultCnt =  pscmprd0009Dao.updatePriceChangeReq(pscmprd0009VO);
		} catch (Exception e) {
			return 0;
		}
		return resultCnt;


	}

	/**
	 * Desc : 상품단품 정보를 조회하는 메소드
	 * @Method Name : selectProductItemList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public List<PSCMPRD0009VO> selectProductItemList(DataMap paramMap) throws Exception {
		return pscmprd0009Dao.selectProductItemList(paramMap);
	}

	/**
	 * Desc : 상품단품 정보 중복 카운트를 조회하는 메소드
	 * @Method Name : selectProductItemDupCount
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public List<DataMap> selectProductItemDupCount(DataMap paramMap) throws Exception {
		return pscmprd0009Dao.selectProductItemDupCount(paramMap);
	}

	/**
	 * Desc : 상품가격변경요청리스트 엑셀다운로드하는 메소드
	 * @Method Name : selectPriceChangeList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	public List<PSCMPRD0009VO> selectPriceChangeListExcel(PSCMPRD0009VO vo) throws Exception {
		return pscmprd0009Dao.selectPriceChangeListExcel(vo);
	}

}
