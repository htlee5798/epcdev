package com.lottemart.epc.product.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.RestAPIUtil;
import com.lottemart.common.util.RestConst;
import com.lottemart.epc.common.dao.CommonDao;
import com.lottemart.epc.product.dao.PSCPPRD0024Dao;
import com.lottemart.epc.product.service.PSCPPRD0024Service;




@Service("PSCPPRD0024Service")
public class PSCPPRD0024ServiceImpl implements PSCPPRD0024Service{
	private static final Logger logger = LoggerFactory.getLogger(PSCPPRD0024ServiceImpl.class);
	@Autowired
	private PSCPPRD0024Dao PSCPPRD0024Dao;
	@Autowired
	private CommonDao commonDao;

	/**
	 * 배송정보 조회
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectDeliveryList(DataMap paramMap) throws Exception {
		return PSCPPRD0024Dao.selectDeliveryList(paramMap);
	}

	/**
	 * 배송정보 조회2
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public DataMap selectDeliveryInfo(DataMap paramMap) throws Exception {
		return PSCPPRD0024Dao.selectDeliveryInfo(paramMap);
	}

	/**
	 * 상품 배송비정보
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<DataMap> selectDInfoList(DataMap paramMap) throws Exception {
		return PSCPPRD0024Dao.selectDInfoList(paramMap);
	}
	/**
	 * 공통배송비정보1
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public DataMap selectVendorDlvInfo(DataMap paramMap) throws Exception {
		return PSCPPRD0024Dao.selectVendorDlvInfo(paramMap);
	}

	/**
	 * 공통배송비정보2
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public DataMap selectVendorDeliInfo(DataMap paramMap) throws Exception {
		return PSCPPRD0024Dao.selectVendorDeliInfo(paramMap);
	}

	/**
	 * 묶음배송
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public DataMap selectBdlDelYn(DataMap paramMap) throws Exception {
		return PSCPPRD0024Dao.selectBdlDelYn(paramMap);
	}
	
	/**
	 * 온라인상품유형 조회
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public DataMap selectOnlineProdInfo(DataMap paramMap) throws Exception {
		return PSCPPRD0024Dao.selectOnlineProdInfo(paramMap);
	}
	/**
	 * 배송정보 저장
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public int deliverySave(HttpServletRequest request) throws Exception {
		int resultCnt = 0;

		DataMap paramMap = new DataMap();

		String[] sStatus = request.getParameterValues("S_STATUS");
		String[] prodCds = request.getParameterValues("PROD_CD");
		String[] deliKindCds = request.getParameterValues("DELIVERY_KIND_CD");
		String[] seqs = request.getParameterValues("SEQ");
		String[] deliveryAmts = request.getParameterValues("DELIVERY_AMT");
		String[] deliCondAmts = request.getParameterValues("DELI_COND_AMT");

		for (int i=0; i<sStatus.length; i++) {
			if(sStatus[i].equals("U")) {
				paramMap.put("prodCd", prodCds[i]);
				paramMap.put("deliKindCd", deliKindCds[i]);
				paramMap.put("seq", seqs[i]);
				paramMap.put("deliveryAmt", deliveryAmts[i]);
				paramMap.put("deliCondAmt", deliCondAmts[i]);

				resultCnt += PSCPPRD0024Dao.updateDelivery(paramMap);
				PSCPPRD0024Dao.insertDelivery(paramMap);
			}
		}

		return resultCnt;
	}

	/**
	 * 상품 배송비 저장
	 * @param DataMap
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	@Override
	public void updatePrdEvtCopy(DataMap paramMap) throws SQLException {

		String condUseYn = paramMap.getString("condUseYn"); // 업체 공통조건 사용여부

		if ("Y".equals(condUseYn)) {
			paramMap.put("entpProdCondDeliYn", "Y");
			paramMap.put("nochDeliYn", "N");
			paramMap.put("bdlDeliYn", "Y");
			PSCPPRD0024Dao.updateDeliveryBld(paramMap);

		} else {
			// 수정된 값
			String deliKindCd = paramMap.getString("deliKindCdSel"); // 배송비종류코드( 수정후)
			logger.debug("deliKindCddeliKindCddeliKindCd=" + deliKindCd);
			String deliKindCdSelBf = paramMap.getString("deliKindCdSelBf"); // 배송비종류코드( 수정전 )
			logger.debug("deliKindCdSelBfdeliKindCdSelBfdeliKindCdSelBf=" + deliKindCdSelBf);

			if (!"".equals(deliKindCd)) {
				int startDyCheck = PSCPPRD0024Dao.selectDeliStartDy(paramMap);
				if (startDyCheck > 0) {
					// 당일적용배송비 데이터 삭제
					PSCPPRD0024Dao.deleteDayDelivery(paramMap);
					PSCPPRD0024Dao.updatePoliceDelivery(paramMap);
					commonDao.commit();
				} else {
					if ("02".equals(deliKindCdSelBf)) { // 수량별 차등(수정전)

						for (int i = 1; i < 6; i++) {
							String seq = paramMap.getString("deliAmt02Seq_" + i);
							String prodCd = paramMap.getString("prodCd");

							paramMap.put("deliKindCd", deliKindCdSelBf);
							paramMap.put("seq", seq);
							paramMap.put("prodCd", prodCd);

							PSCPPRD0024Dao.updateDelivery(paramMap);
						}

					} else if (!"".equals(deliKindCdSelBf)) { // 수량별 차등이 아닐때(수정전)
						String seq = paramMap.getString("deliAmtSeq" + deliKindCdSelBf);
						String prodCd = paramMap.getString("prodCd");

						paramMap.put("deliKindCd", deliKindCdSelBf);
						paramMap.put("seq", seq);
						paramMap.put("prodCd", prodCd);
						PSCPPRD0024Dao.updateDelivery(paramMap);
					}

					String psbtChkYnBf = paramMap.getString("psbtChkYnBf"); // 제주/도서산간 사용여부( 수정전 )

					if ("Y".equals(psbtChkYnBf)) { // 제주/도서산간
						for (int i = 1; i < 3; i++) {
							deliKindCdSelBf = "0" + (3 + i);
							String seq = paramMap.getString("deliAmtSeq0" + (3 + i));
							String prodCd = paramMap.getString("prodCd");

							paramMap.put("deliKindCd", deliKindCdSelBf);
							paramMap.put("seq", seq);
							paramMap.put("prodCd", prodCd);

							PSCPPRD0024Dao.updateDelivery(paramMap);
						}
					}

					String rtnChkYnBf = paramMap.getString("rtnChkYnBf"); // 반품배송비

					if ("Y".equals(rtnChkYnBf)) {
						deliKindCdSelBf = "06";
						String seq = paramMap.getString("deliAmtSeq06");
						String prodCd = paramMap.getString("prodCd");

						paramMap.put("deliKindCd", deliKindCdSelBf);
						paramMap.put("seq", seq);
						paramMap.put("prodCd", prodCd);

						PSCPPRD0024Dao.updateDelivery(paramMap);
					}
				}

				if ("02".equals(deliKindCd)) { // 수량별 차등일때( 수정후)

					for (int i = 1; i < 6; i++) {
						String deliAmt = paramMap.getString("deliAmt02_" + i);
						String seq = paramMap.getString("deliAmt02Seq_" + i);
						String minSetQty = paramMap.getString("minSetQty_" + i);
						String maxSetQty = paramMap.getString("maxSetQty_" + i);

						if (!"".equals(deliAmt)) {
							paramMap.put("deliKindCd", deliKindCd);
							paramMap.put("deliveryAmt", deliAmt);
							paramMap.put("deliBaseMinQty", minSetQty);
							paramMap.put("deliBaseMaxQty", maxSetQty);
							paramMap.put("seq", seq);
							PSCPPRD0024Dao.insertDelivery(paramMap);
							paramMap.put("bdlDeliYn", paramMap.getString("bdlDeliYn" + deliKindCd));
							paramMap.put("entpProdCondDeliYn", "N");
							paramMap.put("nochDeliYn", "N");
							PSCPPRD0024Dao.updateDeliveryBld(paramMap);
						}
					}

				} else if (!"".equals(deliKindCd)) { // 수량별 차등이 아닐때(수정후)
					String deliAmt = paramMap.getString("deliAmt" + deliKindCd);
					String seq = paramMap.getString("deliAmtSeq" + deliKindCd);
					String deliCondAmt = paramMap.getString("deliCondAmt");

					paramMap.put("deliKindCd", deliKindCd);
					paramMap.put("deliveryAmt", deliAmt);
					paramMap.put("seq", seq);
					paramMap.put("deliCondAmt", deliCondAmt);
					if (!"01".equals(deliKindCd)) {
						paramMap.put("deliCondAmt", "");
					}
					if ("01".equals(deliKindCd) || "03".equals(deliKindCd)) {
						PSCPPRD0024Dao.insertDelivery(paramMap);
					}

					paramMap.put("entpProdCondDeliYn", "N");
					paramMap.put("bdlDeliYn", paramMap.getString("bdlDeliYn" + deliKindCd));
					// 무료배송 정책 변경 : deliKindCd "03"(고정배송비) 이면서 deliAmt "0"원 일 경우
					if ("03".equals(deliKindCd) && "0".equals(deliAmt)) {
						paramMap.put("nochDeliYn", "Y");
					} else {
						paramMap.put("nochDeliYn", "N");
					}
					PSCPPRD0024Dao.updateDeliveryBld(paramMap);
				}

				// 제주/도서산간 사용여부( 수정후)
				String psbtChkYn = paramMap.getString("psbtChkYn");

				// 제주/도서산간( 수정후)
				if ("Y".equals(psbtChkYn)) {

					for (int i = 1; i < 3; i++) {
						deliKindCd = "0" + (3 + i);
						String seq = paramMap.getString("deliAmtSeq0" + (3 + i));
						String deliAmt = paramMap.getString("deliAmt" + deliKindCd);
						String deliCondAmt = paramMap.getString("deliCondAmt");

						if (!"".equals(deliAmt) || deliAmt != null) {
							paramMap.put("deliKindCd", deliKindCd);
							paramMap.put("seq", seq);
							paramMap.put("deliveryAmt", deliAmt);
							paramMap.put("deliCondAmt", deliCondAmt);

							PSCPPRD0024Dao.insertDelivery(paramMap);
						}
					}
				}

				// 반품 배송비
				String deliAmt = paramMap.getString("deliAmt06");

				if (!"".equals(deliAmt) || deliAmt != null) {
					paramMap.put("deliKindCd", "06");
					paramMap.put("deliveryAmt", deliAmt);
					paramMap.put("minSetQty", "");
					paramMap.put("maxSetQty", "");

					PSCPPRD0024Dao.insertDelivery(paramMap);
				}

				// API 연동 (EPC -> 통합BO API)
				String prodCd = paramMap.getString("prodCd").substring(0, 1);
				String result = "";
				try {
					commonDao.commit();
					RestAPIUtil rest = new RestAPIUtil();
					if (!prodCd.equals("D")) {
						if (commonDao.selectEcApprovedYn(paramMap.getString("prodCd"))) {
							Map<String, Object> reqMap = new HashMap<String, Object>();
							reqMap.put("spdNo", paramMap.getString("prodCd"));
							result = rest.sendRestCall(RestConst.PD_API_0002, HttpMethod.POST, reqMap, RestConst.PD_API_TIME_OUT, true);
						}
					}
					logger.debug("API CALL RESULT = " + result);

				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			}

		}

	}

	//20181211 - 배송지 설정 수정
	@Override
	public String selectVendorAddrInfoCnt(Map<String, Object> paramMap) throws Exception {
		return PSCPPRD0024Dao.selectVendorAddrInfoCnt(paramMap);
	}

	@Override
	public void updateDeliveryAddressInfo(DataMap paramMap) throws SQLException {
		//상품배송가능지역
		PSCPPRD0024Dao.updatePsbtRegnCd(paramMap);

		//출고지주소
		paramMap.put("addrSeq", "1");
		paramMap.put("newVendorSeq", paramMap.get("addr1"));
		PSCPPRD0024Dao.updateDlvAddr(paramMap);

		//반품교환주소
		paramMap.put("addrSeq", "2");
		paramMap.put("newVendorSeq", paramMap.get("addr2"));
		PSCPPRD0024Dao.updateDlvAddr(paramMap);

		// API 연동 (EPC -> 통합BO API)
		String prodCd = paramMap.getString("prodCd").substring(0,1);
		String result = "";
		try {
			if(!prodCd.equals("D")){
				commonDao.commit();
				if(commonDao.selectEcApprovedYn(paramMap.getString("prodCd"))){
					RestAPIUtil rest = new RestAPIUtil();
					Map<String, Object> reqMap = new HashMap<String, Object>();
					reqMap.put("spdNo", paramMap.getString("prodCd"));
					result = rest.sendRestCall(RestConst.PD_API_0002, HttpMethod.POST, reqMap, RestConst.PD_API_TIME_OUT, true);
					logger.debug("API CALL RESULT = " + result);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	//20181211 - 배송지 설정 수정
	
}
