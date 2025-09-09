package com.lottemart.epc.edi.weborder.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.edi.weborder.dao.NEDMWEB0010Dao;
import com.lottemart.epc.edi.weborder.model.NEDMWEB0010VO;
import com.lottemart.epc.edi.weborder.service.NEDMWEB0010Service;
import com.lottemart.epc.util.Paging;
import com.lottemart.epc.util.PagingFactory;

/**
 * @Class Name : NEDMWEB0010ServiceImpl
 * @Description : 점포별 발주등록 ServiceImpl Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      		수정자          		수정내용
 *  -------    --------    ---------------------------
 * 2015.12.03	O YEUN KWON	  최초생성
 *
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */

@Service("nEDMWEB0010Service")
public class NEDMWEB0010ServiceImpl implements NEDMWEB0010Service {

	public String DefaultPagePerCount = "10";
	public String DefaultPageRowCount = "5";

	@Autowired
	private NEDMWEB0010Dao nedmweb0010dao;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(NEDMWEB0010ServiceImpl.class);

	/**
	 * 상품목록이 많은 협력 업체인지 조회
	 * @param SearchWebOrder
	 * @return void
	 * @throws SQLException
	 */
	public Map<String,Object> selectVendorException(NEDMWEB0010VO nEDMWEB0010VO) {
		Map<String,Object> result = new HashMap<String,Object>();
		String vendorFlag;

		int cnt = nedmweb0010dao.selectVendorException(nEDMWEB0010VO);

		if (cnt > 0) vendorFlag = "T";
		else vendorFlag = "F";

		result.put("vendorFlag", vendorFlag);

		return result;
	}


	/**
	 * 점포 정보 조회
	 * @param SearchWebOrder
	 * @return void
	 * @throws SQLException
	 */
	public Map<String,Object> selectStoreOrdList(NEDMWEB0010VO nEDMWEB0010VO) {
		Map<String,Object> result = new HashMap<String,Object>();
		List<NEDMWEB0010VO> list = null;
		NEDMWEB0010VO ordCntVo = null;

		int page = Integer.parseInt(StringUtils.nvl(nEDMWEB0010VO.getPage(),"1"));
		int pageRowCount = Integer.parseInt(StringUtils.nvl(nEDMWEB0010VO.getPageRowCount(),DefaultPageRowCount));
		int pagePerCount = Integer.parseInt(StringUtils.nvl(nEDMWEB0010VO.getPagePerCount(),DefaultPagePerCount));
		int totalCount = 0;

		if(nEDMWEB0010VO.getStrCds() == null &&   nEDMWEB0010VO.getStrCds().length  <= 0) nEDMWEB0010VO.setStrCds(null);

		totalCount = nedmweb0010dao.selectStoreOrdListTotCnt(nEDMWEB0010VO);
		Paging paging = PagingFactory.makePagingObject(totalCount, pageRowCount, pagePerCount, page);
		int startRowNum = paging.getStartRowNum();
		int endRowNum = paging.getEndRowNum();
		nEDMWEB0010VO.setStartRowNo(startRowNum);
		nEDMWEB0010VO.setEndRowNo(endRowNum);
		if (totalCount > 0){
			list = nedmweb0010dao.selectStoreOrdList(nEDMWEB0010VO);
		}
		// 전점 합계 조회
		ordCntVo = nedmweb0010dao.selectStoreOrdCntSum(nEDMWEB0010VO);

		result.put("totalCount", totalCount);
		result.put("list", list);
		result.put("paging", PagingFactory.makePagingObject(totalCount, pageRowCount, pagePerCount, page));
		result.put("ordCnt", ordCntVo);

		return result;
	}


	/**
	 * 점포 정보 조회 [점포코드 필수]
	 * @param SearchWebOrder
	 * @return void
	 * @throws SQLException
	 */
	public Map<String,Object> selectStoreOrdListFixedStr(NEDMWEB0010VO nEDMWEB0010VO) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		int totalCount = 0;

		List<NEDMWEB0010VO> list = nedmweb0010dao.selectStoreOrdList(nEDMWEB0010VO);
		NEDMWEB0010VO ordCntVo = nedmweb0010dao.selectStoreOrdCntSum(nEDMWEB0010VO);

		if(list != null) totalCount = list.size();

		result.put("totalCount", 	totalCount);
		result.put("list", 			list);
		result.put("ordCnt", 		ordCntVo);

		return result;
	}

	/**
	 * 점포별 상품 정보 리스트 조회
	 * @param SearchWebOrder
	 * @return void
	 * @throws SQLException
	 */
	public Map<String,Object> selectStoreOrdDetList(NEDMWEB0010VO nEDMWEB0010VO) {
		Map<String,Object> result = new HashMap<String,Object>();
		List<NEDMWEB0010VO> list = null;
		list = nedmweb0010dao.selectOrdDetInfo(nEDMWEB0010VO);

		result.put("list", list);

		return result;
	}

	/**
	 * @Description 발주정보 저장  [DADA : 2014.08.30 일괄처리 프로세스로 변경]
	 * @param TedOrdProcess001VO
	 * @param HttpServletRequest
	 * @return void
	 * @throws SQLException
	 */
	public String insertStoreOrdTemp(NEDMWEB0010VO vo, HttpServletRequest request) throws Exception{
		ArrayList<NEDMWEB0010VO> mstList = vo.getTedPoOrdMstList();
		ArrayList<NEDMWEB0010VO> ordList = vo.getTedOrdList();

		NEDMWEB0010VO       mst = new NEDMWEB0010VO();

		if(mstList == null || mstList.size() <=0) return "Parameter DataList is '0' Row !";

		// 업체발주요청상품정보 저장 TED_PO_ORD_PROD [ executeBatch.insert/update ]
		nedmweb0010dao.insertOrdProdInfo(ordList);
		//-----------------------------------------

		// 일괄작업용 KEY 조건 설정 ------------------
		mst = mstList.get(0);
		//-----------------------------------------

		// 업체발주정보마스터 생성 -------------------
		nedmweb0010dao.insertOrdMstInfo(mst);
		//-----------------------------------------

		// 업체발주 요청 마스터 의 발주 총 수량, 발주총금액, 발주전체 총 수량 점포별 일괄 업데이트
		nedmweb0010dao.updateOrdMstProdSum(mst);
		//-----------------------------------------

		return  "suc";
	}

	/**
	 * 발주정보 삭제
	 * @param TedOrdProcess001VO
	 * @return void
	 * @throws SQLException
	 *
	 */

	public String deleteStoreOrdInfo(NEDMWEB0010VO vo) throws Exception{
		ArrayList<NEDMWEB0010VO> mstList = vo.getTedPoOrdMstList();
		ArrayList<NEDMWEB0010VO> ordList = vo.getTedOrdList();

		String result = null;
		int prodCnt = 0;

		// 업체발주요청상품정보 삭제
		nedmweb0010dao.deleteOrdProdInfo(ordList);

		// 업체발주 요청 마스터 의 발주 총 수량, 발주총금액, 발주전체 총 수량 업데이트
		for ( NEDMWEB0010VO  mst : mstList ) {
			prodCnt = nedmweb0010dao.selectProdCnt(mst);

			if(prodCnt > 0){
				mst.setModId(mst.getVenCd());
				nedmweb0010dao.updateOrdMstProdSum(mst);
			}else{
				nedmweb0010dao.deleteOrdMst(mst);
			}
		}
		result = "suc";

		return result;

	}

}
