package com.lottemart.epc.product.service.impl;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//import java.util.AbstractCollection;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtil;
import com.lottemart.common.exception.AlertException;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.product.dao.PSCMPRD0015Dao;
import com.lottemart.epc.product.model.PSCMPRD0015VO;
import com.lottemart.epc.product.service.PSCMPRD0015Service;


/**
 *
 * @author wjChoi
 * @Description : 상품관리 - 대표상품코드관리
 * @Class : com.lottemart.epc.product.service.impl
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2019.10.31  wjChoi
 * @version :
 * </pre>
 */
@Service("pscmprd0015Service")
public class PSCMPRD0015ServiceImpl implements PSCMPRD0015Service {

	private static final Logger logger = LoggerFactory.getLogger(PSCMPRD0015Service.class);

	@Autowired
	private PSCMPRD0015Dao pscmprd0015Dao;

	/* public List<DataMap> selectRepProdCdList(Map<String, String> paramMap) throws Exception {
		return pbomprd0034Dao.selectRepProdCdList(paramMap);
	}*/
	public List<PSCMPRD0015VO> selectRepProdCdList(	Map<Object, Object> paramMap) throws Exception {
		return pscmprd0015Dao.selectRepProdCdList(paramMap);
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
		return pscmprd0015Dao.selectProductItemDupCount(paramMap);
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
	public List<PSCMPRD0015VO> selectProductItemList(DataMap paramMap) throws Exception {
		return pscmprd0015Dao.selectProductItemList(paramMap);
	}

	public List<Map<String, Object>> selectRepProdCdComboList(Map<Object, Object> paramMap) throws Exception {
		return pscmprd0015Dao.selectRepProdCdComboList(paramMap);
	}
	public List<Map<String, Object>> selectTprStoreItemPriceList(Map<Object, Object> paramMap) throws Exception {
		return pscmprd0015Dao.selectTprStoreItemPriceList(paramMap);
	}

	public void insertRepProdCd(Map<Object, Object> paramMap) throws Exception {

		String prodCd = (String) paramMap.get("prodCd");
		String repProdCd = (String) paramMap.get("repProdCd");
		String applyStartDy = (String) paramMap.get("applyStartDy");
		String applyEndDy = (String) paramMap.get("applyEndDy");
		String regId = (String) paramMap.get("regId");
		String modId = (String) paramMap.get("modId");
		String taxatDivnCd = (String) paramMap.get("taxatDivnCd");
		String vendorId = (String) paramMap.get("vendorId");
		String optnProdPrcMgrYn = (String) paramMap.get("optnProdPrcMgrYn");
		String[] ItemCdArr = (String[]) paramMap.get("ItemCdArr");
		String[] sellPrcArr = (String[]) paramMap.get("sellPrcArr");
		String[] currSellPrcArr = (String[]) paramMap.get("currSellPrcArr");
		String[] saleRateArr = (String[]) paramMap.get("saleRateArr");
		String[] profitRateArr = (String[]) paramMap.get("profitRateArr");
		String[] beforeCurrSellPrcArr = (String[]) paramMap.get("beforeCurrSellPrcArr");
		String[] beforeProfitRtArr = (String[]) paramMap.get("beforeProfitRtArr");
		String[] reqReasonContentArr = (String[]) paramMap.get("reqReasonContentArr");

		for (int i = 0; i < currSellPrcArr.length; i++) {

			PSCMPRD0015VO pscmprd0015vo = new PSCMPRD0015VO();
			pscmprd0015vo.setProdCd(StringUtils.defaultString(prodCd, ""));
			pscmprd0015vo.setRepProdCd(StringUtils.defaultString(repProdCd, ""));
			pscmprd0015vo.setApplyStartDy(StringUtils.defaultString(applyStartDy, ""));
			pscmprd0015vo.setApplyEndDy(StringUtils.defaultString(applyEndDy, ""));

			if ("Y".equals(optnProdPrcMgrYn)) {
				pscmprd0015vo.setItemCd(StringUtils.defaultString(ItemCdArr[i], ""));
			} else {
				pscmprd0015vo.setItemCd("001");
			}
			pscmprd0015vo.setSellPrc(StringUtils.defaultString(sellPrcArr[i], ""));
			pscmprd0015vo.setCurrSellPrc(StringUtils.defaultString(currSellPrcArr[i], ""));
			pscmprd0015vo.setProfitRate(StringUtils.defaultString(profitRateArr[i], ""));
			pscmprd0015vo.setTaxatDivnCd(StringUtils.defaultString(taxatDivnCd, ""));
			pscmprd0015vo.setRegId(StringUtils.defaultString(regId, ""));
			pscmprd0015vo.setModId(StringUtils.defaultString(modId, ""));
			pscmprd0015vo.setVendorId(StringUtils.defaultString(vendorId, ""));
			pscmprd0015vo.setReqReasonContent(StringUtils.defaultString(reqReasonContentArr[i], ""));
			if (pscmprd0015vo.getSellPrc() == null || "".equals(pscmprd0015vo.getSellPrc())) {
				throw new IllegalArgumentException("매가(SELL_PRC)는 필수 입력값입니다.");
			}
			// logger.debug("[getApplyStartDy]" + pscmprd0015vo.getApplyStartDy());
			// logger.debug("[getApplyEndDy]" + pscmprd0015vo.getApplyEndDy());
			// logger.debug("[getProdCd]" + pscmprd0015vo.getProdCd());
			// logger.debug("[getTaxatDivnCd]" + pscmprd0015vo.getTaxatDivnCd());
			// logger.debug("[sellPrc]" + pscmprd0015vo.getSellPrc());
			// logger.debug("[currSellPrc]" + pscmprd0015vo.getCurrSellPrc());
			// logger.debug("[profitRate]" + pscmprd0015vo.getProfitRate());
			// logger.debug("[reqReasonContent]" + pscmprd0015vo.getReqReasonContent());

			DataMap taxMap = pscmprd0015Dao.selectRepProdCdTaxatDivnCd(pscmprd0015vo);
			if (taxMap != null) {
				pscmprd0015vo.setTaxatDivnCd(taxMap.getString("TAXAT_DIVN_CD"));
			}

			DataMap prodMap = pscmprd0015Dao.chkProduct(pscmprd0015vo);

			if (prodMap == null) {
				throw new AlertException("해당 협력사 상품이 아닙니다");
			}

			// logger.debug("[vendor chkeck]");

			// 변경 요청 프로세스가 종료되지 않은 상품정보에 대해서 중복 체크
			DataMap chgProdStsMap = pscmprd0015Dao.chkCurrChgSts(pscmprd0015vo);

			if (chgProdStsMap.getInt("CURR_CHG_STATUS") > 0) {
				String chgProdCd = pscmprd0015vo.getProdCd();
				throw new AlertException("변경 요청 정보가 존재합니다.");
			}

			// logger.debug("[histInfo chkeck]");

			// 적용시작일시를 현재날짜와 비교(적용여부 체크)
			DataMap currDateMap = pscmprd0015Dao.chkCurrDate(pscmprd0015vo);

			if (currDateMap.getInt("CURR_DATE_CHK") == -1) {	//오늘날짜 이전
				throw new AlertException("적용시작일자를 오늘날짜부터 입력하셔야 합니다.");
			}

			// logger.debug("[currDate chkeck]");

			pscmprd0015Dao.insertRepChgProdCd(pscmprd0015vo);

		}
	}

	public void updateRepProdCd(Map<Object, Object> paramMap) throws Exception {

		String prodCd = (String) paramMap.get("prodCd");
		String repProdCd = (String) paramMap.get("repProdCd");
		String applyStartDy = (String) paramMap.get("applyStartDy");
		String applyEndDy = (String) paramMap.get("applyEndDy");

		String regId = (String) paramMap.get("regId");
		String modId = (String) paramMap.get("modId");
		String taxatDivnCd = (String) paramMap.get("taxatDivnCd");
		String vendorId = (String) paramMap.get("vendorId");
		String optnProdPrcMgrYn = (String) paramMap.get("optnProdPrcMgrYn");
		String[] ItemCdArr = (String[]) paramMap.get("ItemCdArr");
		String[] reqReasonContentArr = (String[]) paramMap.get("reqReasonContentArr");
		String[] sellPrcArr = (String[]) paramMap.get("sellPrcArr");
		String[] currSellPrcArr = (String[]) paramMap.get("currSellPrcArr");
		String[] saleRateArr = (String[]) paramMap.get("saleRateArr");
		String[] profitRateArr = (String[]) paramMap.get("profitRateArr");
		String[] beforeCurrSellPrcArr = (String[]) paramMap.get("beforeCurrSellPrcArr");
		String[] beforeProfitRtArr = (String[]) paramMap.get("beforeProfitRtArr");

		PSCMPRD0015VO pscmprd0015vo = new PSCMPRD0015VO();

		for (int i = 0; i < currSellPrcArr.length; i++) {
			pscmprd0015vo.setProdCd(StringUtils.defaultString(prodCd, ""));

			if (optnProdPrcMgrYn.equals("Y")) {
				pscmprd0015vo.setItemCd(StringUtils.defaultString(ItemCdArr[i], ""));
			} else {
				pscmprd0015vo.setItemCd("001");
			}

			pscmprd0015vo.setRepProdCd(StringUtils.defaultString(repProdCd, ""));
			pscmprd0015vo.setApplyStartDy(StringUtils.defaultString(applyStartDy, ""));
			pscmprd0015vo.setReqReasonContent(StringUtils.defaultString(reqReasonContentArr[i], ""));
			pscmprd0015vo.setSellPrc(StringUtils.defaultString(currSellPrcArr[i], ""));
			pscmprd0015vo.setCurrSellPrc(StringUtils.defaultString(currSellPrcArr[i], ""));
			pscmprd0015vo.setProfitRate(StringUtils.defaultString(profitRateArr[i], ""));
			pscmprd0015vo.setTaxatDivnCd(StringUtils.defaultString(taxatDivnCd, ""));
			pscmprd0015vo.setModId(StringUtils.defaultString(modId, ""));
			pscmprd0015vo.setVendorId(StringUtils.defaultString(vendorId, ""));

			DataMap taxMap = pscmprd0015Dao.selectRepProdCdTaxatDivnCd(pscmprd0015vo);
			if (taxMap != null) {
				pscmprd0015vo.setTaxatDivnCd(taxMap.getString("TAXAT_DIVN_CD"));
			}

			pscmprd0015Dao.updateRepProdCd(pscmprd0015vo);
		}
	}

	public int deleteRepProdCd(HttpServletRequest request) throws Exception {

		// 처리수행
		int resultCnt = 0;
		PSCMPRD0015VO pscmprd0015vo = null;
		String applyStartDy = null;

		String[] prodCd = request.getParameterValues("prodCd");
		String[] repProdCd = request.getParameterValues("repProdCd");
		String[] APPLY_START_DY = request.getParameterValues("applyStartDy");
		String[] itemCd = request.getParameterValues("itemCd");
		String[] seqNo = request.getParameterValues("seqNo");

		try {
			for (int i = 0; i < prodCd.length; i++) {
				pscmprd0015vo = new PSCMPRD0015VO();
				pscmprd0015vo.setProdCd(prodCd[i]);
				pscmprd0015vo.setRepProdCd(repProdCd[i]);
				applyStartDy = APPLY_START_DY[i];
				applyStartDy = applyStartDy.replaceAll("-", "");
				pscmprd0015vo.setApplyStartDy(applyStartDy);
				pscmprd0015vo.setItemCd(itemCd[i]);
				pscmprd0015vo.setSeqNo(seqNo[i]);

				pscmprd0015Dao.deleteRepProdCd(pscmprd0015vo);

				resultCnt++;
			}
		} catch (Exception e) {
			resultCnt = 0;
		}
		return resultCnt;
	}

	public List<DataMap> selectRepProdCdInfoList(Map<String, String> paramMap) throws Exception {
		/*System.out.println("paramMapSeviceparamMapSeviceparamMapSevice"+paramMap.toString());*/
		return pscmprd0015Dao.selectRepProdCdInfoList(paramMap);
	}

	/**
	 * (non-Javadoc)
	 * @see com.lottemart.epc.product.service.PBOMPRD0034Service#getBeforeChangePrc(java.util.Map)
	 * @Locaton    : com.lottemart.epc.product.service.impl
	 * @MethodName  : getBeforeChangePrc
	 * @author     : jyLim
	 * @Description : 변경 전 가격정보를 가져온다
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> getBeforeChangePrc(Map<String, String> paramMap) throws Exception {
		//---------------------------------------
		// 변경이력에 가격정보가 없으면 상품마스터에서 조회해서 가져온다
		//----------------------------------------
		// DataMap map = pbomprd0034Dao.selectTprRepProdChgHst(paramMap);
		//
		//if(map==null)
		//	map = pbomprd0034Dao.selectTprProduct(paramMap);

		//---------------------------------------
		// 1.상품정보를 조회한다
		//----------------------------------------
		List<DataMap> map = pscmprd0015Dao.selectTprProduct1(paramMap);
		List<DataMap> map2 = pscmprd0015Dao.selectTprRepProdChgHst1(paramMap);

		//---------------------------------------
		// 1-1.상품마스터에 없으면 상품에러
		//---------------------------------------
		if (map == null) {
			throw new IllegalArgumentException("존재하지 않거나 해당 협력사 상품이 아닙니다");
		}

		//---------------------------------------
		// 1-2.히스토리가 있으면 히스토리 정보로 리턴
		//----------------------------------------
		if (map2 != null)
			map = map2;

		return map;
	}

	/**
	 * (non-Javadoc)
	 * @see com.lottemart.epc.product.service.PBOMPRD0034Service#selectProdInfo(xlib.cmc.GridData, javax.servlet.http.HttpServletRequest)
	 * @Locaton    : com.lottemart.epc.product.service.impl
	 * @MethodName  : selectProdInfo
	 * @author     : jyLim
	 * @Description : 그리드에서 상품코드리스트를 가져와 각각 상품코드마다 가격정보를 가져온다.
	 * @param gdReq
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectProdInfo(Map<Object, Object> param) throws Exception {

		List<DataMap> list = new ArrayList<DataMap>();
		DataMap map = new DataMap();
		DataMap map2 = new DataMap();

		Map<String, String> paramMap = new HashMap<String, String>();

		String[] prodArr = (String[]) param.get("prodCdArr");
		String[] itemCdArr = (String[]) param.get("itemCdArr");
		String vendorId = (String) param.get("vendorId");

		int rowCount = prodArr.length;
		int i;
		String prodCd;
		String itemCd;
		String optnProdCd = "";

		for (i = 0; i < rowCount; i++) {
			prodCd = prodArr[i];
			itemCd = itemCdArr[i];

			if (prodCd.equals(optnProdCd) && !itemCd.equals("001")) {
				throw new IllegalArgumentException("[" + (i + 2) + "번째 행]" + " 상품코드(" + prodCd + ")는 옵션별 가격관리를 하지 않습니다.\n단품코드(" + itemCd + ") 로우를 삭제후 등록해주세요.");
			}

			if (prodCd == null || prodCd.length() == 0) {
				throw new IllegalArgumentException("[" + (i + 2) + "번째 행]" + " 상품코드가 비었습니다");
			}

			if (itemCd == null || itemCd.length() == 0) {
				throw new IllegalArgumentException("[" + (i + 2) + "번째 행]" + " 단품코드가 비었습니다");
			}

			paramMap = new HashMap<String, String>();
			paramMap.put("prodCd", prodCd);
			paramMap.put("itemCd", itemCd);
			paramMap.put("applyEndDy", "99991231");
			paramMap.put("ref1", "grpInsert");
			paramMap.put("vendorId", vendorId);

			logger.debug("[PROD_CD]" + prodCd);
			logger.debug("[ITEM_CD]" + itemCd);
			logger.debug("[vendorId]" + vendorId);

			//---------------------------------------
			// 1.상품정보를 조회한다
			//----------------------------------------
			map = pscmprd0015Dao.selectTprProduct(paramMap);
			map2 = pscmprd0015Dao.selectTprRepProdChgHst(paramMap);

			//---------------------------------------
			// 1-1.상품마스터에 없으면 상품에러
			//---------------------------------------
			if (map == null) {
				throw new IllegalArgumentException("[" + (i + 2) + "번째 행]" + " 상품코드(" + prodCd + ")가 존재하지 않거나 \n해당 협력사 상품이 아닙니다.");
			} else {
				optnProdCd = "N".equals(map.get("OPTN_PROD_PRC_MGR_YN")) ? (String) map.get("PROD_CD") : "";
			}

			//---------------------------------------
			// 1-2.히스토리가 있으면 히스토리 정보로 리턴
			//----------------------------------------
			if (map2 != null) {
				map = map2;
			}

			map.put("SEQ", i + 1);
			list.add(map);
		}

		return list;
	}
	/**
	 * (non-Javadoc)
	 * @see com.lottemart.epc.product.service.PBOMPRD0034Service#insertRepProdCdList(java.util.List)
	 * @Locaton    : com.lottemart.epc.product.service.impl
	 * @MethodName  : insertRepProdCdList
	 * @author     : cwj
	 * @Description : 대표상품코드 일괄 등록
	 * -------------------------------------------------------
	 *		cdStr
	 *			성공적으로 저장한 상품코드들의 문자열
	 *	 		ex) L000000000036,L000000000040,S000000000016
	 *          파라미터로 배열을 전달할 수 없기 때문에 쉼표(,)로 구분하여 문자열로 바꿔 저장한다
	 *          jsp단에 도착 후 파싱해서 사용한다
	 * -------------------------------------------------------
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> insertRepProdCdList(List<PSCMPRD0015VO> list) throws Exception {

		Map<String, String> resultMap = new HashMap<String, String>();
		ArrayList<String> cdList = new ArrayList<String>();
		PSCMPRD0015VO pscmprd0015vo;
		String prodCd;

		Map<Object, Object> param = new HashMap<Object, Object>();

		int i;
		int rowCount = list.size();
		for (i = 0; i < rowCount; i++) {
			pscmprd0015vo = list.get(i);
			prodCd = pscmprd0015vo.getProdCd();

			try {
				//등록
				param.put("prodCd", pscmprd0015vo.getProdCd());
				param.put("repProdCd", pscmprd0015vo.getRepProdCd());
				param.put("applyStartDy", pscmprd0015vo.getApplyStartDy());
				param.put("applyEndDy", pscmprd0015vo.getApplyEndDy());
				param.put("regId", pscmprd0015vo.getRegId());
				param.put("modId", pscmprd0015vo.getModId());
				param.put("taxatDivnCd", pscmprd0015vo.getTaxatDivnCd());
				param.put("vendorId", pscmprd0015vo.getVendorId());
				param.put("optnProdPrcMgrYn", pscmprd0015vo.getOptnProdPrcMgrYn());

				String[] setValue1 = { pscmprd0015vo.getItemCd() };
				param.put("ItemCdArr", setValue1);

				String[] setValue2 = { pscmprd0015vo.getSellPrc() };
				param.put("sellPrcArr", setValue2);

				String[] setValue3 = { pscmprd0015vo.getCurrSellPrc() };
				param.put("currSellPrcArr", setValue3);

				String[] setValue4 = { pscmprd0015vo.getProfitRate() };
				param.put("profitRateArr", setValue4);

				String[] setValue5 = { pscmprd0015vo.getReqReasonContent() };
				param.put("reqReasonContentArr", setValue5);

				insertRepProdCd(param);
			} catch (AlertException e) {
				String mesasge = "[상품코드" + (prodCd) + "] " + e.getMessage();
				rowCount = -1;
				resultMap.put("alertEx", "Y");
				resultMap.put("message", mesasge);
				resultMap.put("resultCnt", String.valueOf(rowCount));

				return resultMap;

			} catch (Exception e) {
				String mesasge = "[상품코드" + (prodCd) + "] " + e.getMessage();
				rowCount = -1;
				resultMap.put("alertEx", "N");
				resultMap.put("message", mesasge);
				resultMap.put("cdStr", join(cdList, ","));
				resultMap.put("resultCnt", String.valueOf(rowCount));

				return resultMap;
			}
			cdList.add(prodCd);
		}
		resultMap.put("alertEx", "S");
		resultMap.put("resultCnt", String.valueOf(rowCount));
		resultMap.put("cdStr", join(cdList, ","));

		return resultMap;
	}

	//적용시작일을 오늘날짜로 등록한 시 긴급매가 적용처리 20180220 (SP_BO_REP_PROD_CD_UPDATE)
	public void processRepProdCd(String prodCd) throws Exception {
		Map<String, String> param = new HashMap<String, String>();
		param.put("prodCd", prodCd);

		//---------------------------------------
		// 1.긴급매가 대상 상품정보를 조회한다
		//----------------------------------------
		List<DataMap> mapProd = pscmprd0015Dao.selectRepProdSellingPrc(param);
		int mapCnt = 0;
		int rsltCnt = 0;
		mapCnt = mapProd.size();

		for (int i = 0; i < mapCnt; i++) {
			DataMap map = (DataMap) mapProd.get(i);
			if ("".equals(StringUtil.null2str(String.valueOf(map.getString("ITEM_CD"))).replaceAll("null", ""))) { //단품정보값이 없는 경우
				map.put("ITEM_CD", "");
			}

			//---------------------------------------
			// 2.긴급매가 - 점포별가격정보 가격 update
			//----------------------------------------
			pscmprd0015Dao.updateTprStoreItemPriceRepProd(map);

			//---------------------------------------
			// 3.긴급매가 - 점포별가격정보 이력 insert
			//----------------------------------------
			pscmprd0015Dao.insertTprStoreItemPriceHistRepProd(map);

			//---------------------------------------
			// 4.긴급매가 - 대표판매코드변경이력 적용여부 update
			//----------------------------------------
			pscmprd0015Dao.updateTcaRepProdChgHstRepProd(map);

			//한번만 적용하기 위해서
			if ("".equals(StringUtil.null2str(String.valueOf(map.getString("ITEM_CD"))).replaceAll("null", "")) ||
				"001".equals(StringUtil.null2str(String.valueOf(map.getString("ITEM_CD"))).replaceAll("null", ""))) { //단품정보값이 없거나 001인 경우
				//---------------------------------------
				// 5.상품마스터에 가격 update
				//----------------------------------------
				pscmprd0015Dao.updateTprProductRepProd(map);

				//---------------------------------------
				// 6.긴급매가 - 카테고리할당상품 가격 update
				//----------------------------------------
				pscmprd0015Dao.updateTcaCategoryAssignRepProd(map);
			}
		}
	}

	public String strDateFormat(String yyyymmdd) {
		if (StringUtils.isEmpty(yyyymmdd)) {
			return "";
		}
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append(yyyymmdd.substring(0, 4)).append("-")
		         .append(yyyymmdd.substring(4, 6)).append("-")
		         .append(yyyymmdd.substring(6, 8));
		return strBuffer.toString();
	}

	/**
	 *
	 * @see join
	 * @Locaton    : com.lottemart.epc.common.util
	 * @MethodName  : join
	 * @author     : jyLim
	 * @Description : 자바로 구현한 join. 자바스크립트의 join과 같은 기능을 한다
	 * @param s
	 * @param delimiter
	 * @return
	 */
	public String join(AbstractCollection<String> s, String delimiter) {
		StringBuffer buffer = new StringBuffer();
		Iterator<String> iter = s.iterator();
		if (iter.hasNext()) {
			buffer.append(iter.next());
			while (iter.hasNext()) {
				buffer.append(delimiter);
				buffer.append(iter.next());
			}
		}
		return buffer.toString();
	}

	/**
	 * 상품 목록을 조회한다.
	 **/
	public List<Map<String, Object>> pscmprd0015List(Map<Object, Object> paramMap) throws Exception {
		return pscmprd0015Dao.pscmprd0015List(paramMap);
	}
}
