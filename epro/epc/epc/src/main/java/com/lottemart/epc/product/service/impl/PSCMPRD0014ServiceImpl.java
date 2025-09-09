/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.product.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.RestAPIUtil;
import com.lottemart.common.util.RestConst;
import com.lottemart.epc.common.dao.CommonDao;
import com.lottemart.epc.product.dao.PSCMPRD0014Dao;
import com.lottemart.epc.product.model.PSCMPRD0014VO;
import com.lottemart.epc.product.service.PSCMPRD0014Service;

@Service("PSCMPRD0014Service")
public class PSCMPRD0014ServiceImpl implements PSCMPRD0014Service {
	
	private static final Logger logger = LoggerFactory.getLogger(PSCMPRD0014Service.class);
	
	@Autowired
	private PSCMPRD0014Dao pscmprd0014Dao;
	
	@Autowired
	private CommonDao commonDao;

	public List<DataMap> selectStoreCombo() throws Exception {
		return pscmprd0014Dao.selectStoreCombo();
	}

	public List<DataMap> selectOutOfStockCount(DataMap paramMap) throws Exception {
		return pscmprd0014Dao.selectOutOfStockCount(paramMap);
	}

	public List<PSCMPRD0014VO> selectOutOfStockList(DataMap paramMap) throws Exception {
		return pscmprd0014Dao.selectOutOfStockList(paramMap);
	}

	@Override
	public int updateOutOfStock(HttpServletRequest request) throws Exception {
		DataMap paramMap = new DataMap(request);

		int resultCnt = 0;

		String[] prodCds = request.getParameterValues("PROD_CD");
		String[] chgProdNms = request.getParameterValues("CHG_PROD_NM");
		String[] sellStateCds = request.getParameterValues("SELL_STATE_CD");
		String[] dispYns = request.getParameterValues("DISP_YN");
		String[] stkQtys = request.getParameterValues("RSERV_STK_QTY");
		String[] itemCds = request.getParameterValues("ITEM_CD");
		String[] strCds = request.getParameterValues("STR_CD");
		String[] nochDeliYn = request.getParameterValues("NOCH_DELI_YN");
		String[] entpProdCondDeliYn = request.getParameterValues("ENTP_PROD_COND_DELI_YN");

		try {
			for (int i = 0; i < prodCds.length; i++) {
				paramMap.put("prodNm", chgProdNms[i]);
				paramMap.put("sellStateCd", sellStateCds[i]);
				paramMap.put("dispYn", dispYns[i]);
				paramMap.put("stkQty", stkQtys[i]);
				paramMap.put("nochDeliYn", nochDeliYn[i]);
				paramMap.put("entpProdCondDeliYn", entpProdCondDeliYn[i]);

				paramMap.put("prodCd", prodCds[i]);
				paramMap.put("itemCd", itemCds[i]);
				paramMap.put("strCd", strCds[i]);
				paramMap.put("modId", request.getAttribute("modId").toString());

				if ("01".equals(paramMap.getString("chgFlag"))) {
					if (chgProdNms[i] != null && !"".equals(chgProdNms[i].trim())) { // 변경 후 상품명 NotNull & NotEmpty -> 상품명Update (Null Update 방어)
						resultCnt += pscmprd0014Dao.updateOutOfStockProd(paramMap);
					}
				} else if ("05".equals(paramMap.getString("chgFlag"))) {
					resultCnt += pscmprd0014Dao.updateEntpProdCondDeli(paramMap);
				} else if ("03".equals(paramMap.getString("chgFlag"))) {
					pscmprd0014Dao.updateOutOfStockItemMst(paramMap); // master update
					pscmprd0014Dao.updateOutOfStockItem(paramMap); // item update
					resultCnt++;
				} else {
					resultCnt += pscmprd0014Dao.updateOutOfStockItem(paramMap);
				}
			}
		} catch (Exception e) {
			return 0;
		}
		// API 연동 (EPC -> 통합BO API)
		if (resultCnt > 0) {
			try {
				commonDao.commit();
				for (int i = 0; i < prodCds.length; i++) {

					String prodCd = prodCds[i].substring(0, 1);
					RestAPIUtil rest = new RestAPIUtil();
					Map<String, Object> reqMap = new HashMap<String, Object>();
					reqMap.put("spdNo", prodCds[i]);

					if ("01".equals(paramMap.getString("chgFlag"))) { // 상품명 변경
						if (chgProdNms[i] != null && !"".equals(chgProdNms[i].trim())) { // 변경 후 상품명 NotNull & NotEmpty -> EC전송 (Null Update 방어)
							String result = "";
							if ("D".equals(prodCd)) {
								if (commonDao.selectEcPlanRegisteredYn(prodCds[i])) {
									result = rest.sendRestCall(RestConst.PD_API_0010 + prodCds[i], HttpMethod.POST, null, RestConst.PD_API_TIME_OUT, true);
								}
							} else {
								if (commonDao.selectEcApprovedYn(prodCds[i])) {
									reqMap.put("spdNo", prodCds[i]);
									result = rest.sendRestCall(RestConst.PD_API_0002, HttpMethod.POST, reqMap, RestConst.PD_API_TIME_OUT, true);
								}
							}
							// logger.debug("API CALL RESULT = " + result);
						}
					} else if (!"05".equals(paramMap.getString("chgFlag"))) {
						
						if (prodCd.equals("D")) {
							if (commonDao.selectEcPlanRegisteredYn(prodCds[i])) {
								String jsonString = rest.sendRestCall(RestConst.PD_API_0010+prodCds[i], HttpMethod.POST, null, RestConst.PD_API_TIME_OUT, true);
								// logger.debug("API CALL RESULT = " + jsonString);
							}

						} else {
							if("03".equals(paramMap.getString("chgFlag"))){
								
								if(commonDao.selectEcApprovedYn(prodCds[i])){
									String jsonString = rest.sendRestCall(RestConst.PD_API_0002, HttpMethod.POST, reqMap, RestConst.PD_API_TIME_OUT, true);
									// logger.debug("API CALL RESULT = " + jsonString);								
									String jsonString2 = rest.sendRestCall(RestConst.PD_API_0005, HttpMethod.POST, reqMap, RestConst.PD_API_TIME_OUT, true);
									// logger.debug("API CALL RESULT = " + jsonString2);
								}
							}
							
							if (commonDao.selectEcRegisteredYn(prodCds[i])) {
								String jsonString3 = rest.sendRestCall(RestConst.PD_API_0026, HttpMethod.POST, reqMap, RestConst.PD_API_TIME_OUT, true);//점포별-가격정보수정
								// logger.debug("API CALL RESULT = " + jsonString3);
								String jsonString4 = rest.sendRestCall(RestConst.PD_API_0003, HttpMethod.POST, reqMap, RestConst.PD_API_TIME_OUT, true);
								// logger.debug("API CALL RESULT = " + jsonString4);
								String jsonString5 = rest.sendRestCall(RestConst.PD_API_0004, HttpMethod.POST, reqMap, RestConst.PD_API_TIME_OUT, true);
								// logger.debug("API CALL RESULT = " + jsonString5);
							}
						}
					}
				
				}
			}catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		
		return resultCnt;
	}

	public List<PSCMPRD0014VO> selectOutOfStockListExcel(PSCMPRD0014VO pscmprd0014VO) throws Exception {
		return pscmprd0014Dao.selectOutOfStockListExcel(pscmprd0014VO);
	}
	
	//20180626 - 업체 공통조건 사용시 무료배송 'N' 처리(주문파트 요청건) 및 업체 배송비 관리 수정 (배송비 업체공통 조건 문구가 보여서 추가 처리)
	@Override
	public List<DataMap> selectDeliVendorInfo(DataMap paramMap) throws Exception {
		return pscmprd0014Dao.selectDeliVendorInfo(paramMap);
	}

}
