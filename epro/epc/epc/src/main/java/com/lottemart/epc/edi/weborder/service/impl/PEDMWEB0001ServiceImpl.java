package com.lottemart.epc.edi.weborder.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lcn.module.framework.property.ConfigurationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diquest.ir.common.setting.dbwatcher.Description;
import com.lcnjf.util.StringUtil;
import com.lcnjf.util.StringUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.weborder.dao.PEDMWEB0001Dao;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.model.TedOrdList;
import com.lottemart.epc.edi.weborder.model.TedOrdList001VO;
import com.lottemart.epc.edi.weborder.model.TedOrdProcess001VO;
import com.lottemart.epc.edi.weborder.model.TedPoOrdMstList001VO;
import com.lottemart.epc.edi.weborder.model.TedPoOrdMstVO;
import com.lottemart.epc.edi.weborder.service.PEDMWEB0001Service;
import com.lottemart.epc.util.Paging;
import com.lottemart.epc.util.PagingFactory;

@Service("pEDMWEB0001Service")
public class PEDMWEB0001ServiceImpl implements PEDMWEB0001Service {
	
	public String DefaultPagePerCount = "10";
	public String DefaultPageRowCount = "5";
	
	@Autowired
	private PEDMWEB0001Dao pedmweb0001dao;
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(PEDMWEB0001ServiceImpl.class);
	
	/**
	 * 상품목록이 많은 협력 업체인지 조회
	 * @param SearchWebOrder
	 * @return void
	 * @throws SQLException
	 */
	public Map<String,Object> selectVendorException(SearchWebOrder vo) {
		Map<String,Object> result = new HashMap<String,Object>();
		String vendorFlag;
		
		int cnt = pedmweb0001dao.selectVendorException(vo);
		
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
	public Map<String,Object> selectStoreOrdList(SearchWebOrder vo) {
		Map<String,Object> result = new HashMap<String,Object>();
		List<TedOrdList> list = null;
		TedOrdList ordCntVo = null;
		
		int page = Integer.parseInt(StringUtils.nvl(vo.getPage(),"1"));
		int pageRowCount = Integer.parseInt(StringUtils.nvl(vo.getPageRowCount(),DefaultPageRowCount));
		int pagePerCount = Integer.parseInt(StringUtils.nvl(vo.getPagePerCount(),DefaultPagePerCount));
		int totalCount = 0;
		
		if(vo.getStrCds() == null &&   vo.getStrCds().length  <= 0) vo.setStrCds(null);
		
		totalCount = pedmweb0001dao.selectStoreOrdListTotCnt(vo);
		Paging paging = PagingFactory.makePagingObject(totalCount, pageRowCount, pagePerCount, page);
		int startRowNum = paging.getStartRowNum();
		int endRowNum = paging.getEndRowNum();
		vo.setStartRowNo(startRowNum);
		vo.setEndRowNo(endRowNum);
		if (totalCount > 0){
			list = pedmweb0001dao.selectStoreOrdList(vo);
		}
		// 전점 합계 조회
		ordCntVo = pedmweb0001dao.selectStoreOrdCntSum(vo);
		
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
	public Map<String,Object> selectStoreOrdListFixedStr(SearchWebOrder vo) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		int totalCount = 0;
		
		List<TedOrdList> list = pedmweb0001dao.selectStoreOrdList(vo);
		TedOrdList ordCntVo = pedmweb0001dao.selectStoreOrdCntSum(vo);
		
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
	public Map<String,Object> selectStoreOrdDetList(SearchWebOrder vo) {
		Map<String,Object> result = new HashMap<String,Object>();
		List<TedOrdList> list = null;
		list = pedmweb0001dao.selectOrdDetInfo(vo);
		
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
	public String insertStoreOrdTemp(TedOrdProcess001VO vo, HttpServletRequest request) throws Exception{
		TedPoOrdMstList001VO mstList = vo.getTedPoOrdMstList001VO();
		TedOrdList001VO ordList = vo.getTedOrdList001VO();
		
		TedPoOrdMstVO       mst = new TedPoOrdMstVO();
		
		if(mstList == null || mstList.size() <=0) return "Parameter DataList is '0' Row !";
		
		// 업체발주요청상품정보 저장 TED_PO_ORD_PROD [ executeBatch.insert/update ]
		pedmweb0001dao.insertOrdProdInfo(ordList);  
		//-----------------------------------------
		
		// 일괄작업용 KEY 조건 설정 ------------------
		mst = mstList.get(0);	 
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
	 * 발주정보 삭제 
	 * @param TedOrdProcess001VO
	 * @return void
	 * @throws SQLException
	 * 
	 */
	
	public String deleteStoreOrdInfo(TedOrdProcess001VO vo) throws Exception{
		TedPoOrdMstList001VO mstList = vo.getTedPoOrdMstList001VO();
		TedOrdList001VO ordList = vo.getTedOrdList001VO();
		
		String result = null;
		int prodCnt = 0;
		
		// 업체발주요청상품정보 삭제
		pedmweb0001dao.deleteOrdProdInfo(ordList);
		
		// 업체발주 요청 마스터 의 발주 총 수량, 발주총금액, 발주전체 총 수량 업데이트
		for ( TedPoOrdMstVO  mst : mstList ) {
			prodCnt = pedmweb0001dao.selectProdCnt(mst);
			
			if(prodCnt > 0){
				mst.setModId(mst.getVenCd());
				pedmweb0001dao.updateOrdMstProdSum(mst);
			}else{
				pedmweb0001dao.deleteOrdMst(mst);
			}
		}
		result = "suc";
		
		return result;
		
	}
	
	
	/**
	 * 승인요청 [프로세스 변경으로 사용안함.]
	 * @param SearchWebOrder
	 * @param HttpServletRequest
	 * @return void
	 * @throws SQLException
	 */
	public String upadteApprReqInfo(SearchWebOrder vo, HttpServletRequest request) throws Exception{
		List<TedOrdList> prodVo = null;
		String sessionKey = config.getString("lottemart.epc.session.key");

		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
		
		String result = null;
		int appReqChkCnt = 0;
		
		TedPoOrdMstVO mstVo = new TedPoOrdMstVO();
		mstVo.setVenCd(vo.getVenCd());
		mstVo.setOrdDy(vo.getOrdDy());
		mstVo.setModId(epcLoginVO.getAdminId());
		
		appReqChkCnt = pedmweb0001dao.selectAppReqChkCnt(vo);
		
		if(appReqChkCnt > 0) {
			prodVo = pedmweb0001dao.selectAppReqFailInfo(vo);
			
			for(TedOrdList pord :  prodVo){
				pord.setModId(epcLoginVO.getAdminId());
				pedmweb0001dao.updateOrdProdState(pord);
			}

			pedmweb0001dao.updateOrdMstState(mstVo);
			
		}else{
			pedmweb0001dao.updateOrdMstState(mstVo);
		}
		result = "suc";
		
		return result;
	}
	
}
