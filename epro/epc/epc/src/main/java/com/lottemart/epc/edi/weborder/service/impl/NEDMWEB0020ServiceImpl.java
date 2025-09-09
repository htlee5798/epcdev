package com.lottemart.epc.edi.weborder.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.edi.weborder.dao.NEDMWEB0010Dao;
import com.lottemart.epc.edi.weborder.dao.NEDMWEB0020Dao;
import com.lottemart.epc.edi.weborder.model.NEDMWEB0010VO;
import com.lottemart.epc.edi.weborder.model.NEDMWEB0020VO;
import com.lottemart.epc.edi.weborder.service.NEDMWEB0020Service;
import com.lottemart.epc.util.Paging;
import com.lottemart.epc.util.PagingFactory;

/**
 * @Class Name : NEDMWEB0020ServiceImpl
 * @Description : 상품별 발주등록회 ServiceImpl Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      		수정자          		수정내용
 *  -------    --------    ---------------------------
 * 2015.12.07	O YEUN KWON	  최초생성
 *
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
*/

@Service("nEDMWEB0020Service")
public class NEDMWEB0020ServiceImpl implements NEDMWEB0020Service {

	public String DefaultPagePerCount = "10";
	public String DefaultPageRowCount = "20";

	@Autowired
	private NEDMWEB0010Dao nedmweb0010dao;

	@Autowired
	private NEDMWEB0020Dao nedmweb0020dao;

	/**
	 * 상품정보 조회
	 * @param SearchWebOrder
	 * @return void
	 * @throws SQLException
	 */
	public Map<String,Object> selectProdOrdList(NEDMWEB0020VO vo) {
		Map<String,Object> result = new HashMap<String,Object>();
		List<NEDMWEB0020VO> list = null;

		int page = Integer.parseInt(StringUtils.nvl(vo.getPage(),"1"));
		int pageRowCount = Integer.parseInt(StringUtils.nvl(vo.getPageRowCount(),DefaultPageRowCount));
		int pagePerCount = Integer.parseInt(StringUtils.nvl(vo.getPagePerCount(),DefaultPagePerCount));
		int totalCount = 0;

		totalCount = nedmweb0020dao.selectProdOrdListTotCnt(vo);
		Paging paging = PagingFactory.makePagingObject(totalCount, pageRowCount, pagePerCount, page);
		int startRowNum = paging.getStartRowNum();
		int endRowNum = paging.getEndRowNum();
		vo.setStartRowNo(startRowNum);
		vo.setEndRowNo(endRowNum);
		if (totalCount > 0){
			list = nedmweb0020dao.selectProdOrdList(vo);
		}

		result.put("totalCount", totalCount);
		result.put("list", list);
		result.put("paging", PagingFactory.makePagingObject(totalCount, pageRowCount, pagePerCount, page));

		return result;
	}

	/**
	 * 상품목록이 많은 협력 업체인지 조회
	 * @param SearchWebOrder
	 * @return void
	 * @throws SQLException
	 */
	public Map<String,Object> selectVendorException(NEDMWEB0020VO nEDMWEB0020VO) {
		Map<String,Object> result = new HashMap<String,Object>();
		String vendorFlag;

		int cnt = nedmweb0020dao.selectVendorException(nEDMWEB0020VO);

		if (cnt > 0) vendorFlag = "T";
		else vendorFlag = "F";

		result.put("vendorFlag", vendorFlag);

		return result;
	}

	/**
	 * 상품별 점포정보 조회
	 * @param SearchWebOrder
	 * @return void
	 * @throws SQLException
	 */
	public Map<String,Object> selectProdOrdDetList(NEDMWEB0020VO vo) {
		Map<String,Object> result = new HashMap<String,Object>();
		List<NEDMWEB0020VO> list = null;
		NEDMWEB0020VO totCnt = null;

		list = nedmweb0020dao.selectProdOrdDetInfo(vo);
		totCnt = nedmweb0020dao.selectProdCntSum(vo);

		result.put("list", list);
		result.put("totCnt", totCnt);

		return result;
	}

	/**
	 * Desc : 발주정보 저장
	 * @Method Name : insertProdOrdTemp
	 * @param TedOrdProcess001VO
	 * @param HttpServletRequest
	 * @return message
	 */
	public String insertProdOrdTemp(NEDMWEB0020VO vo, HttpServletRequest request) throws Exception{
		ArrayList<NEDMWEB0010VO> ordList = new ArrayList<NEDMWEB0010VO>();
		ArrayList<NEDMWEB0020VO> list = vo.getTedOrdList();

		NEDMWEB0010VO       mst = new NEDMWEB0010VO();


		for(NEDMWEB0020VO nEDMWEB0020VO : list){

			NEDMWEB0010VO nEDMWEB0010VO = new NEDMWEB0010VO();

			nEDMWEB0010VO.setOrdQty(nEDMWEB0020VO.getOrdQty());
			nEDMWEB0010VO.setVenCd(nEDMWEB0020VO.getVenCd());
			nEDMWEB0010VO.setOrdDy(nEDMWEB0020VO.getOrdDy());
			nEDMWEB0010VO.setStrCd(nEDMWEB0020VO.getStrCd());
			nEDMWEB0010VO.setProdCd(nEDMWEB0020VO.getProdCd());
			nEDMWEB0010VO.setOrdReqNo(nEDMWEB0020VO.getOrdReqNo());
			nEDMWEB0010VO.setVenNm(nEDMWEB0020VO.getVenNm());
			nEDMWEB0010VO.setOrdVseq(nEDMWEB0020VO.getOrdVseq());
			nEDMWEB0010VO.setOrdTotQty(nEDMWEB0020VO.getOrdTotQty());
			nEDMWEB0010VO.setOrdTotAllQty(nEDMWEB0020VO.getOrdTotAllQty());
			nEDMWEB0010VO.setOrdTotPrc(nEDMWEB0020VO.getOrdTotPrc());
			nEDMWEB0010VO.setOrdTotStkQty(nEDMWEB0020VO.getOrdTotStkQty());
			nEDMWEB0010VO.setOrdPrgsCd(nEDMWEB0020VO.getOrdPrgsCd());
			nEDMWEB0010VO.setProcEmpNo(nEDMWEB0020VO.getProcEmpNo());
			nEDMWEB0010VO.setRegId(nEDMWEB0020VO.getRegId());
			nEDMWEB0010VO.setRegDt(nEDMWEB0020VO.getRegDt());
			nEDMWEB0010VO.setModId(nEDMWEB0020VO.getModId());
			nEDMWEB0010VO.setModDt(nEDMWEB0020VO.getModDt());


			ordList.add(nEDMWEB0010VO);
		}


		if(ordList == null || ordList.size() <=0) return "Parameter DataList is '0' Row !";

		// 업체발주요청상품정보 저장 TED_PO_ORD_PROD [ executeBatch.insert/update ]
		nedmweb0010dao.insertOrdProdInfo(ordList);
		//-----------------------------------------

		// 일괄작업용 KEY 조건 설정 ------------------
		mst.setVenCd(ordList.get(0).getVenCd());
		mst.setOrdDy(ordList.get(0).getOrdDy());
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
	 * Desc : 상품 및 점포별 등록 정보 삭제
	 * @Method Name : deleteProdOrdInfo
	 * @param TedOrdProcess001VO
	 * @param HttpServletRequest
	 * @return message
	 */
	public String deleteProdOrdInfo(NEDMWEB0020VO vo) throws Exception{
		ArrayList<NEDMWEB0020VO> ordList = vo.getTedOrdList();

		NEDMWEB0010VO  mst = new NEDMWEB0010VO();

		String result = null;
		String ordReqNo = null;
		int prodCnt = 0;

		// 점포별 삭제
		nedmweb0020dao.deleteStrOrdInfo(ordList);

		// 업체발주 요청 마스터 의 발주 총 수량, 발주총금액, 발주전체 총 수량 업데이트
		for ( NEDMWEB0020VO  ord : ordList ) {
			mst.setStrCd(ord.getStrCd());
			mst.setOrdDy(ord.getOrdDy());
			mst.setVenCd(ord.getVenCd());

			ordReqNo = nedmweb0010dao.selectOrdReqNo(mst);
			mst.setOrdReqNo(ordReqNo);

			prodCnt = nedmweb0010dao.selectProdCnt(mst);

			if(prodCnt > 0){
				mst.setModId(ord.getVenCd());
				nedmweb0010dao.updateOrdMstProdSum(mst);
			}else{
				nedmweb0010dao.deleteOrdMst(mst);
			}
		}
		result = "suc";

		return result;

	}
}
