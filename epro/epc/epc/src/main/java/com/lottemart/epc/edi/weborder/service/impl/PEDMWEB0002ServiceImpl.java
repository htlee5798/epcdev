package com.lottemart.epc.edi.weborder.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.edi.weborder.dao.PEDMWEB0001Dao;
import com.lottemart.epc.edi.weborder.dao.PEDMWEB0002Dao;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.model.TedOrdList;
import com.lottemart.epc.edi.weborder.model.TedOrdList001VO;
import com.lottemart.epc.edi.weborder.model.TedOrdProcess001VO;
import com.lottemart.epc.edi.weborder.model.TedPoOrdMstVO;
import com.lottemart.epc.edi.weborder.service.PEDMWEB0002Service;
import com.lottemart.epc.util.Paging;
import com.lottemart.epc.util.PagingFactory;

@Service("pEDMWEB0002Service")
public class PEDMWEB0002ServiceImpl implements PEDMWEB0002Service {

	public String DefaultPagePerCount = "10";
	public String DefaultPageRowCount = "20";

	@Autowired
	private PEDMWEB0001Dao pedmweb0001dao;

	@Autowired
	private PEDMWEB0002Dao pedmweb0002dao;

	/**
	 * 상품정보 조회
	 * @param SearchWebOrder
	 * @return void
	 * @throws SQLException
	 */
	public Map<String,Object> selectProdOrdList(SearchWebOrder vo) {
		Map<String,Object> result = new HashMap<String,Object>();
		List<TedOrdList> list = null;

		int page = Integer.parseInt(StringUtils.nvl(vo.getPage(),"1"));
		int pageRowCount = Integer.parseInt(StringUtils.nvl(vo.getPageRowCount(),DefaultPageRowCount));
		int pagePerCount = Integer.parseInt(StringUtils.nvl(vo.getPagePerCount(),DefaultPagePerCount));
		int totalCount = 0;

		totalCount = pedmweb0002dao.selectProdOrdListTotCnt(vo);
		Paging paging = PagingFactory.makePagingObject(totalCount, pageRowCount, pagePerCount, page);
		int startRowNum = paging.getStartRowNum();
		int endRowNum = paging.getEndRowNum();
		vo.setStartRowNo(startRowNum);
		vo.setEndRowNo(endRowNum);
		if (totalCount > 0){
			list = pedmweb0002dao.selectProdOrdList(vo);
		}

		result.put("totalCount", totalCount);
		result.put("list", list);
		result.put("paging", PagingFactory.makePagingObject(totalCount, pageRowCount, pagePerCount, page));

		return result;
	}

	/**
	 * 상품별 점포정보 조회
	 * @param SearchWebOrder
	 * @return void
	 * @throws SQLException
	 */
	public Map<String,Object> selectProdOrdDetList(SearchWebOrder vo) {
		Map<String,Object> result = new HashMap<String,Object>();
		List<TedOrdList> list = null;
		TedOrdList totCnt = null;

		list = pedmweb0002dao.selectProdOrdDetInfo(vo);
		totCnt = pedmweb0002dao.selectProdCntSum(vo);

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
	public String insertProdOrdTemp(TedOrdProcess001VO vo, HttpServletRequest request) throws Exception{
		TedOrdList001VO ordList = vo.getTedOrdList001VO();

		TedPoOrdMstVO       mst = new TedPoOrdMstVO();

		if(ordList == null || ordList.size() <=0) return "Parameter DataList is '0' Row !";

		// 업체발주요청상품정보 저장 TED_PO_ORD_PROD [ executeBatch.insert/update ]
		pedmweb0001dao.insertOrdProdInfo(ordList);
		//-----------------------------------------

		// 일괄작업용 KEY 조건 설정 ------------------
		mst.setVenCd(ordList.get(0).getVenCd());
		mst.setOrdDy(ordList.get(0).getOrdDy());
		//-----------------------------------------

		// 업체발주정보마스터 생성 -------------------
		pedmweb0001dao.insertOrdMstInfo(mst);
		//-----------------------------------------

		// 업체발주 요청 마스터 의 발주 총 수량, 발주총금액, 발주전체 총 수량 점포별 일괄 업데이트
		pedmweb0001dao.updateOrdMstProdSum(mst);
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
	public String deleteProdOrdInfo(TedOrdProcess001VO vo) throws Exception{
		TedOrdList001VO ordList = vo.getTedOrdList001VO();
		TedPoOrdMstVO  mst = new TedPoOrdMstVO();

		String result = null;
		String ordReqNo = null;
		int prodCnt = 0;

		// 점포별 삭제
		pedmweb0002dao.deleteStrOrdInfo(ordList);

		// 업체발주 요청 마스터 의 발주 총 수량, 발주총금액, 발주전체 총 수량 업데이트
		for ( TedOrdList  ord : ordList ) {
			mst.setStrCd(ord.getStrCd());
			mst.setOrdDy(ord.getOrdDy());
			mst.setVenCd(ord.getVenCd());

			ordReqNo = pedmweb0001dao.selectOrdReqNo(mst);
			mst.setOrdReqNo(ordReqNo);

			prodCnt = pedmweb0001dao.selectProdCnt(mst);

			if(prodCnt > 0){
				mst.setModId(ord.getVenCd());
				pedmweb0001dao.updateOrdMstProdSum(mst);
			}else{
				pedmweb0001dao.deleteOrdMst(mst);
			}
		}
		result = "suc";

		return result;

	}
}
