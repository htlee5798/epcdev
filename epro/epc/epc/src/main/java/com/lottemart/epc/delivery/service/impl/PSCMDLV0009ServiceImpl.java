package com.lottemart.epc.delivery.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.delivery.dao.PSCMDLV0009Dao;
import com.lottemart.epc.delivery.model.PSCMDLV0009VO;
import com.lottemart.epc.delivery.service.PSCMDLV0009Service;
import com.lottemart.epc.restApi.EcInterfaceUtil;

@Service("PSCMDLV0009Service")
public class PSCMDLV0009ServiceImpl  implements PSCMDLV0009Service {

	private static final Logger logger = LoggerFactory.getLogger(PSCMDLV0009ServiceImpl.class);

	@Autowired
	private PSCMDLV0009Dao pscmdlv0009dao;

	@Autowired
	private EcInterfaceUtil ec;

	public List<DataMap> getTetCodeList(PSCMDLV0009VO vo) throws Exception {
		return pscmdlv0009dao.getTetCodeList(vo);
	}

	public List<DataMap> selectPartnerReturnList(DataMap paramMap) throws Exception {
		return pscmdlv0009dao.selectPartnerReturnList(paramMap);
	}

	public int insertTsaBatchLog2(PSCMDLV0009VO vo) throws Exception {
		return pscmdlv0009dao.insertTsaBatchLog2(vo);
	}

	public int updateTorOrderItem(List<PSCMDLV0009VO> orderItemList) throws Exception {
		int resultCnt = 0;
		int sqlCnt = 0;
		List<String> ecOrderIdList = new ArrayList<String>();
		String modId = "online";

		try {
			int size = orderItemList.size();
			for (int i = 0; i < size; i++) {
				sqlCnt = 0;

				PSCMDLV0009VO listBean = orderItemList.get(i);
				PSCMDLV0009VO bean = new PSCMDLV0009VO();
				bean.setOrderId(listBean.getOrderId());
				bean.setOrderItemSeq(listBean.getOrderItemSeq());
				bean.setDeliStatusCd(listBean.getDeliStatusCd());
				bean.setDeliveryId(listBean.getDeliveryId());
				bean.setModId(listBean.getModId());
				resultCnt = pscmdlv0009dao.updateTorOrderItem(bean);
				if (resultCnt > 0) {
					pscmdlv0009dao.updateTdeDeliMst(bean);
				}

				// 교환회수완료일경우 TOR_ORDER에 주문상태를 교환완료로 처리
				if ("74".equals(listBean.getDeliStatusCd()) || "77".equals(listBean.getDeliStatusCd())) {
					sqlCnt = pscmdlv0009dao.selectExchFnshChk(bean);
					if (sqlCnt == 0) {
						// 교환완료처리
						pscmdlv0009dao.updateTorOrderOrdStsCd(bean);
						// 원주문의 주문상태를 교환요청에서 결제완료로 변경
						pscmdlv0009dao.updateTorOrderOrdStsCd11(bean);
					}
				}
				if ("Y".equals(listBean.getEcOrderYn())) {
					// 신주문 반품/교환 처리를 위해 주문번호 저장
					if (ecOrderIdList.indexOf(bean.getOrderId()) < 0) { //중복없이
						ecOrderIdList.add(bean.getOrderId());
						modId = bean.getModId();
					}
				}
			}
			// 위 아이템 처리에서 전체회수된 or 구주문인 경우 주문번호 수집해서 아래에서 처리
			// 신주문이고 전체회수일 경우 추가 처리
			if (ecOrderIdList.size() > 0) {
				for (String ecOrderId : ecOrderIdList) {
					DataMap dataMap = pscmdlv0009dao.selectRtnMap(ecOrderId).get(0);
					dataMap.put("MOD_ID", modId);
					if ("Y".equals(dataMap.getString("ALL_RETURN_YN"))) {
						// 배송마스터 반품완료, 교환완료
						// PSCMDLV0009Dao.updateDeliMstByEnter(dataMap); 위 로직에서 처리.
						// 예약배송마스터 취소처리-보류
						pscmdlv0009dao.updateInvoiceByEnter(dataMap);
						if ("66".equals(dataMap.getString("DELI_STATUS_CD"))) { // 반품일 경우 반품완료처리(66), 상태값 전송
							// 반품매출완료 처리
							if (updateRtnComplate(dataMap) > 0) {
								// ec 상태값 전송
								DataMap sendMap = new DataMap();
								sendMap.put("ORDER_ID", dataMap.getString("ORDER_ID"));
								sendMap.put("DELI_STATUS_CD", "64"); // 회수완료
								ec.sendDL0007(sendMap);

								// ec 상태값 전송
								DataMap sendMap2 = new DataMap();
								sendMap2.put("ORDER_ID", dataMap.getString("ORDER_ID"));
								sendMap2.put("DELI_STATUS_CD", "66"); // 회수확정
								ec.sendDL0007(sendMap2);

								// 미수령 반품 완료 시(스토어픽), TEC_DELIVERY_SPP 테이블에 데이터 INSERT
								DataMap sppMap = new DataMap();
								sppMap.put("deliNo", dataMap.getString("DELI_NO"));
								sppMap.put("regId", dataMap.getString("MOD_ID"));
								List<DataMap> sppDeliTypeList = pscmdlv0009dao.selectSppDeliType(sppMap);
								if (sppDeliTypeList != null && sppDeliTypeList.size() > 0) {
									// 클레임 사유 코드 : 310(자동반품)의 SPP3 미수령 회수지시 전송시점 : 크로스픽(회수지시) / 스토어픽(반품완료)
									if ("310".equals(sppDeliTypeList.get(0).get("CLAIM_REASON_CD"))) {
										if ("S".equals(sppDeliTypeList.get(0).get("SPP_DELI_TYPE"))) {
											sppMap.put("sppDeliType", "SR");
											sppMap.put("pickupStatCd", "40");
											pscmdlv0009dao.insertSppInfo(sppMap);
										}
									} else {
										// 그외에는 리버스픽 SPP12 반품완료 전송
										pscmdlv0009dao.updateSppInfoForReturnEnd(sppMap);
									}
								}
							}
						} else {// 교환일 경우 교환완료처리(77), 상태값전송
								// EC DL_API_0007  회수완료, 회수확정 전송

							insertTsaBatchLog3ByRtnDeliAmt(dataMap); // 반품/교환비 매출완료처리 2022-03-15

							// ec 상태값 전송
							DataMap paramMap = new DataMap();
							paramMap.put("DELI_NO", dataMap.getString("DELI_NO"));
							paramMap.put("DELI_STATUS_CD", "73");//회수완료
							ec.sendDL0007(paramMap);

							//ec 상태값 전송
							DataMap paramMap2 = new DataMap();
							paramMap2.put("DELI_NO", dataMap.getString("DELI_NO"));
							paramMap2.put("DELI_STATUS_CD", "74");//회수확정
							ec.sendDL0007(paramMap2);
						}
					}
				}
			}

		} catch (Exception e) {
			return 0;
		}
		return resultCnt;
	}

	public int updateRtnComplate(DataMap paramMap) throws Exception {
		int rtn = 0;
		// 반품주문 회수완료처리
		// 0. 반품주문인지 확인
		if ("66".equals(paramMap.getString("DELI_STATUS_CD"))) {
			// 1. TOR_ORDER 변경 (반품주문 변경)
			// 2. TOR_ORDER_ITEM 변경 (반품주문 변경)
			// 1. TOR_ORDER 변경 (반품주문 변경)
			pscmdlv0009dao.updateReturnTorderStatus(paramMap);
			// 2. TOR_ORDER_ITEM 변경 (반품주문 변경)
			pscmdlv0009dao.updateReturnTorderItemStatus(paramMap);
			// 3-1. 결품재배송 > 모든상품 취소 (전체반품일경우) >> 없어도 될듯
			// 3-2. 결품재배송 > 일부상품 취소 (부분반품일경우) >> 없어도 될듯

			// 4. 쿠폰사용 취소
			// 4-1. 결제목록 조회
			List<DataMap> demandList = pscmdlv0009dao.selectDemandList(paramMap);
			// 쿠폰사용 취소처리
			for (DataMap demand : demandList) {
				String setlTypeCd = demand.getString("SETL_TYPE_CD");
				String cardcoCd = demand.getString("CARDCO_CD");
				if ("03".equals(setlTypeCd) || "05".equals(setlTypeCd)) { // 결제수단 : 쿠폰 or 배송비 쿠폰
					String couponId = demand.getString("ACCOUNT_NO");
					DataMap bx = new DataMap();
					bx.put("couponId", couponId);
					bx.put("order_id", paramMap.getString("ORDER_ID"));
					bx.put("mod_id", paramMap.getString("MOD_ID"));
					String repCouponId = pscmdlv0009dao.selectRepCouponId(demand.getString("REP_COUPON_ID"));
					// 사용한 쿠폰이 에누리쿠폰일 경우 사용취소처리 하지 않음
					if (!"C00000000397765".equals(repCouponId) && !"C00000004921669".equals(repCouponId)) {
						if ("21".equals(cardcoCd) || "22".equals(cardcoCd) || "24".equals(cardcoCd)) {
							logger.debug("[" + demand.getString("ORDER_ID") + "][" + couponId + "]사용한 쿠폰이 에누리쿠폰일 경우 사용취소처리 하지 않음");
						} else {
							pscmdlv0009dao.updateCouponIssuedUsedYn(bx); // 지불쿠폰 USE_YN 처리
						}
					}
				}
			}

			List<DataMap> addPointCouponList = pscmdlv0009dao.selectAddPointCouponList(paramMap); //4-2. 적립 쿠폰 조회
			// 영수증 적립쿠폰 취소 처리
			for (DataMap demand : addPointCouponList) {
				String couponId = demand.getString("COUPON_ID");
				DataMap bx = new DataMap();
				bx.put("couponId", couponId);
				bx.put("order_id", paramMap.getString("ORDER_ID"));
				bx.put("mod_id", paramMap.getString("MOD_ID"));
				pscmdlv0009dao.updateCouponIssuedUsedYn(bx);
			}

			pscmdlv0009dao.updateReturnConfirmTorDemandStatus(paramMap); // 5. TOR_DEMAND 결제 취소 (반품접수주문)
			pscmdlv0009dao.updateTorCounselProcessStatus(paramMap); // 6. 상담접수 현황의 처리상태 Update
			pscmdlv0009dao.updateOrgTorderReturnCompleteStatus(paramMap); // 7. 원주문 반품완료

			insertTsaBatchLog3ByRtnDeliAmt(paramMap); // 반품/교환비 매출완료처리 2022-03-15

			// 반품매출완료 처리
			DataMap tsa2 = new DataMap();
			tsa2.put("orderId", paramMap.getString("ORDER_ID"));
			tsa2.put("statusFg", "2");
			pscmdlv0009dao.insertTsaBatchLog3(tsa2);

			DataMap tsa4 = new DataMap();
			tsa4.put("orderId", paramMap.getString("ORDER_ID"));
			tsa4.put("statusFg", "4");
			pscmdlv0009dao.insertTsaBatchLog3(tsa4);
			rtn++;
		}

		return rtn;
	}

	// 반품비매출완료처리
	private void insertTsaBatchLog3ByRtnDeliAmt(DataMap paramMap) throws Exception {
		DataMap rtnDeliAmtOrder = pscmdlv0009dao.selectRtnDeliAmtOrder(paramMap); // 반품/교환주문건에 대한 반품배송비 '주문확정' 건 조회
		if (rtnDeliAmtOrder != null && "N".equals(rtnDeliAmtOrder.get("FG_3_YN"))) { // 반품/교환주문건에 대한 반품배송비 '매출확정' 건이 존재하지 않으면

			DataMap updMap = new DataMap();
			updMap.put("ORDER_ID", rtnDeliAmtOrder.get("ORDER_ID"));
			updMap.put("MOD_ID", paramMap.get("MOD_ID"));
			pscmdlv0009dao.updateRtnDeliAmtOrder(updMap);
			pscmdlv0009dao.updateRtnDeliAmtOrderItem(updMap);
			
			DataMap tsa3 = new DataMap();
			tsa3.put("orderId", rtnDeliAmtOrder.get("ORDER_ID")); // 반품배송비 주문번호
			tsa3.put("statusFg", "3"); // 매출확정
			pscmdlv0009dao.insertTsaBatchLog3(tsa3);
		}
	}

}