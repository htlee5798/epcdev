package com.lottemart.epc.common.controller;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lottemart.common.exception.AppException;
import com.lottemart.common.file.service.FileMngService;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.common.util.RestAPIUtil;
import com.lottemart.common.util.RestConst;
import com.lottemart.epc.common.model.PSCMCOM0005VO;
import com.lottemart.epc.common.service.PSCMCOM0005Service;

import lcn.module.common.util.StringUtil;

/**
 * Handles requests for the application home page.
 */
@Controller
public class PSCMCOM0005Controller {

	private static final Logger logger = LoggerFactory.getLogger(PSCMCOM0005Controller.class);

	@Autowired
	private PSCMCOM0005Service pscmcom0005service;

	@Autowired
	private FileMngService fileMngService;

	@RequestMapping(value = "/common/selectPartnerInvoiceNoPopup.do")
	public String selectDeliCodePopup(ModelMap model) throws Exception {
		return "common/PSCMCOM0005";
	}

	// 20180917 송장업로드 결과 메시지 수정
	@RequestMapping(value = "/common/upload.do")
	public @ResponseBody Map<String, Object> upload(HttpServletRequest request, HttpServletResponse response, PSCMCOM0005VO vo, ModelMap model) throws Exception {

		Map<String, Object> rtnMap = null;

		try {
			DecimalFormat deliVery = new DecimalFormat("000");
			DecimalFormat ordId = new DecimalFormat("000000000000");
			DecimalFormat hoCd = new DecimalFormat("00");
			DecimalFormat vCd = new DecimalFormat("000000");

			if (request instanceof MultipartHttpServletRequest) {
				String[] colNms = { "ORD_DY", "PROD_NM", "ITEM_OPT", "SALE_PRICE", "ORD_CNT", "DELI_AMT", "ORDER_ID",
						"DELIVERY_ID", "VEN_CD", "INVOICE_NO", "ADD_INVOICE_NO", "HODECO_CD", "DELI_STATUS_CD" };
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				List<DataMap> invoiceList = fileMngService.readUploadNullColExcelFile(multipartRequest, colNms, 1, 0);

				int sucCnt = 0;
				int errCnt = 0;
				if (invoiceList != null && invoiceList.size() > 0) {
					int listSize = invoiceList.size();

					vo.setMajorCd("DE011");
					List<DataMap> DE011List = pscmcom0005service.getTetCodeList(vo);

					for (int i = 0; i < listSize; i++) {
						DataMap map = invoiceList.get(i);

						try {
							String venCd = map.getString("VEN_CD");
							String orderId = map.getString("ORDER_ID");
							String deliveryId = map.getString("DELIVERY_ID");
							String hodecoInvoiceNo = map.getString("INVOICE_NO");
							String hodecoAddInvoiceNo = map.getString("ADD_INVOICE_NO");
							String hodecoCd = map.getString("HODECO_CD");
							String venDeliStatusCd = map.getString("DELI_STATUS_CD");

							if (orderId.length() == 12) {
								orderId = ordId.format(Integer.parseInt(orderId));
							} else {
								++errCnt;
								throw new AppException("잘못된 주문 번호입니다.[" + orderId + "]");
							}

							if (deliveryId.length() == 3) {
								deliveryId = deliVery.format(Integer.parseInt(deliveryId));
							} else {
								++errCnt;
								throw new AppException("잘못된 배송지순번입니다.[" + deliveryId + "]");
							}

							if (venCd.length() == 6) {
								if (venCd.indexOf("T") == -1) {
									venCd = vCd.format(Integer.parseInt(venCd));
								}
							} else {
								++errCnt;
								throw new AppException("잘못된 업체ID입니다.[" + venCd + "]");
							}

							if (!(hodecoInvoiceNo.length() > 1)) {
								++errCnt;
								throw new AppException("잘못된 운송장 번호입니다.[" + hodecoInvoiceNo + "]");
							}
							if (StringUtil.getByteLength(hodecoInvoiceNo) > 30) {
								++errCnt;
								throw new AppException("운송장 번호는 30자리(Byte) 이하로 입력해주세요.[" + hodecoInvoiceNo + "]");
							}

							if (hodecoCd != null && hodecoCd.length() == 2) {
								hodecoCd = hoCd.format(Integer.parseInt(hodecoCd));
							} else {
								++errCnt;
								throw new AppException("잘못된 택배사 코드입니다.[" + hodecoCd + "]");
							}

							boolean checkCd = false;
							for (DataMap de011 : DE011List) {
								if (hodecoCd.equals(de011.getString("MINOR_CD"))) {
									checkCd = true;
									break;
								}
							}
							if (!checkCd) {
								throw new AppException("존재하지않는 택배사 코드가 있습니다.[" + hodecoCd + "]");
							}

							if ("04".equals(hodecoCd) && hodecoInvoiceNo.length() != 12) {
								++errCnt;
								throw new AppException("롯데택배의 운송장 번호는 12자리 입니다.[" + hodecoInvoiceNo + "]");
							}

							if (!("03".equals(venDeliStatusCd) || "09".equals(venDeliStatusCd)
									|| "".equals(venDeliStatusCd))) {
								//errorStatus = false;
								++errCnt;
								throw new AppException("배송상태코드가 잘못 입력되었습니다. (발송예정:03, 배송중:09)");
							}

							vo.setOrderId(orderId);
							vo.setDeliveryId(deliveryId);
							vo.setModId(venCd);
							vo.setHodecoCd(hodecoCd);
							vo.setHodecoInvoiceNo(hodecoInvoiceNo);
							vo.setHodecoAddInvoiceNo(hodecoAddInvoiceNo);

							//배송 상태 값 배송 예정(03) 입력 시 배송 상태 역행 여부 확인
							if ("03".equals(venDeliStatusCd) && pscmcom0005service.selectDeliveryStatusReverceCnt(vo) > 0) {
								++errCnt;
								throw new AppException("배송 상태 역행 (배송중 → 발송 예정)은 불가능 합니다.");
							}

							//주문건수조회
							DataMap orderCnt = pscmcom0005service.selectOrderItemCount(vo);
							DataMap orderDelivery = pscmcom0005service.selectDeliveryVendor(vo);
							String onlnDeliTypeCd = orderDelivery.getString("ONLN_DELI_TYPE_CD");
							int hodecoInfoCnt = 0;
							String deliveryCheckYN = "N";
							if (orderCnt != null && orderCnt.getInt("CNT") > 0
									&& ("09".equals(venDeliStatusCd) || "".equals(venDeliStatusCd))) {
								vo.setDeliNo(orderCnt.getString("DELI_NO"));
								if ("01".equals(onlnDeliTypeCd)) {
									DataMap hodecoInfoCntMap = pscmcom0005service.selectHodecoInfoCount(vo); // 중복 송장번호 체크
									hodecoInfoCnt = hodecoInfoCntMap.getInt("CNT");
									if (hodecoInfoCnt > 0) {
										throw new AppException("중복된 송장번호 입니다.");
									}
									deliveryCheckYN = pscmcom0005service.deliveryCheck(vo); // 송장번호 스윗트래커 체크
									if("Y".equals(deliveryCheckYN)){
										throw new AppException("*확인사항  1.택배사/송장번호를 올바르게 입력했는지 확인 2.택배사에 상품을 인계했는지 확인(인계 후 2시간 뒤 입력)");
									}
								}
							}

							if (orderCnt != null && orderCnt.getInt("CNT") > 0 && hodecoInfoCnt == 0
									&& "N".equals(deliveryCheckYN)) {

								if ("03".equals(venDeliStatusCd)) { //발송예정으로 상태변경
									vo.setDeliStatusCd("31");
									vo.setVenDeliStatusCd("03");
								} else if ("09".equals(venDeliStatusCd) || "".equals(venDeliStatusCd)) { //배송중으로 상태변경
									vo.setDeliStatusCd("45");
									vo.setVenDeliStatusCd("09");
								}
								vo.setDeliNo(orderCnt.getString("DELI_NO"));
								vo.setInvoiceSeq("1");
								vo.setRegId(venCd);
								vo.setOnlnDeliTypeCd(onlnDeliTypeCd);

								pscmcom0005service.updatePartnerFirmsOrderItem(vo);
								pscmcom0005service.updatePartnerFirmsDeliMst(vo);

								if (pscmcom0005service.selectPartnerFirmsHodecoInfoCnt(vo) > 0) {
									pscmcom0005service.updatePartnerFirmsHodecoInfo(vo);
								} else {
									pscmcom0005service.insertPartnerFirmsHodecoInfo(vo);
								}

								++sucCnt;

								//신주문일 경우 추가 처리
								if ("Y".equals(orderCnt.getString("EC_ORDER_YN"))) { // 배송중으로 처리할 경우 DL_API_0007 전송
									Map<String, String> apiParam = new HashMap<String, String>();
									apiParam.put("DELI_NO", vo.getDeliNo());
									apiParam.put("DELI_STATUS_CD", "43");
									String result = "";
									try {
										RestAPIUtil rest = new RestAPIUtil();
										result = rest.sendRestCall(RestConst.DL_API_0007, HttpMethod.POST, apiParam, 5000, true);
										logger.debug("API CALL RESULT = " + result);
									} catch (Exception e) {
										logger.error(e.getMessage());
									}
								}

							}
							map.put("SUC_FLAG", "성공");
							map.put("ERR_MSG", "");
						} catch (AppException e) {
							map.put("SUC_FLAG", "실패");
							map.put("ERR_MSG", e.getMessage());
						} catch (SQLException e) {
							map.put("SUC_FLAG", "실패");
							map.put("ERR_MSG", e.getMessage());
						} catch (Exception e) {
							map.put("SUC_FLAG", "실패");
							map.put("ERR_MSG", e.getMessage());
						} // try catch

					} // for
				}
				rtnMap = JsonUtils.convertList2Json((List<DataMap>) invoiceList, invoiceList.size(), "1");
				rtnMap.put("msg", "");
				rtnMap.put("errCnt", errCnt);
				rtnMap.put("sucCnt", sucCnt);
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			if ("".equals(e.getMessage().toString())) {
				rtnMap.put("msg", "파일 업로드 시 에러가 발생 하였습니다");
			} else {
				rtnMap.put("msg", e.getMessage().toString());
			}
		}

		return rtnMap;
	}
	// 20180917 송장업로드 결과 메시지 수정

}
