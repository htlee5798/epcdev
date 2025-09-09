package com.lottemart.epc.common.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.code.service.CommonCodeService;
import com.lottemart.common.exception.AlertException;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.dao.PSCMCOM0005Dao;
import com.lottemart.epc.common.model.PSCMCOM0005VO;
import com.lottemart.epc.common.service.PSCMCOM0005Service;
import lcn.module.framework.property.ConfigurationService;

@Service("pscmcom0005Service")
public class PSCMCOM0005ServiceImpl implements PSCMCOM0005Service {

	private static final Logger logger = LoggerFactory.getLogger(PSCMCOM0005ServiceImpl.class);
	@Autowired
	private PSCMCOM0005Dao pscmcom0005Dao;

	@Autowired
	private CommonCodeService commonCodeService;

	@Autowired
	private ConfigurationService config;

	public List<DataMap> getTetCodeList(PSCMCOM0005VO vo) throws Exception {
		return pscmcom0005Dao.getTetCodeList(vo);
	}

	public void updatePartnerFirmsOrderItem(PSCMCOM0005VO vo) throws Exception {
		pscmcom0005Dao.updatePartnerFirmsOrderItem(vo);
	}

	public void updatePartnerFirmsDeliMst(PSCMCOM0005VO vo) throws Exception {
		pscmcom0005Dao.updatePartnerFirmsDeliMst(vo);
	}

	public int selectPartnerFirmsHodecoInfoCnt(PSCMCOM0005VO vo) throws Exception {
		return pscmcom0005Dao.selectPartnerFirmsHodecoInfoCnt(vo);
	}

	public void updatePartnerFirmsHodecoInfo(PSCMCOM0005VO vo) throws Exception {
		pscmcom0005Dao.updatePartnerFirmsHodecoInfo(vo);
	}

	public void insertPartnerFirmsHodecoInfo(PSCMCOM0005VO vo) throws Exception {
		pscmcom0005Dao.insertPartnerFirmsHodecoInfo(vo);
	}

	public DataMap selectOrderItemCount(PSCMCOM0005VO vo) throws Exception {
		return pscmcom0005Dao.selectOrderItemCount(vo);
	}

	public DataMap selectHodecoInfoCount(PSCMCOM0005VO vo) throws Exception {
		return pscmcom0005Dao.selectHodecoInfoCount(vo);
	}

	public DataMap selectDeliveryVendor(PSCMCOM0005VO vo) throws Exception {
		return pscmcom0005Dao.selectDeliveryVendor(vo);
	}

	public String deliveryCheck(PSCMCOM0005VO vo) throws Exception {

		DataMap codeData = null; // 해외배송국가 데이터
		String emptyYn = "N";
		String result = "";
		String tCode = "";
		String[] t_invoice_empty = new String[2];

		if (!"06".equals(vo.getHodecoCd()) && !"13".equals(vo.getHodecoCd()) && !"08F".equals(vo.getHodecoCd())
				&& !"16".equals(vo.getHodecoCd())) {
			codeData = commonCodeService.getCode("DE011", vo.getHodecoCd());
			if (codeData != null) {
				if (codeData.size() > 0) {
					tCode = codeData.getString("LET_3_REF");
				}
			}
		}
		HttpURLConnection conn = null;
		String jsonString = "";

		try {

			String[] t_invoice = { vo.getHodecoInvoiceNo().replaceAll("-", ""), vo.getHodecoAddInvoiceNo().replaceAll("-", "") };
			for (int k = 0; k < t_invoice.length; k++) {
				//url 에 접속 계정 사용
				URL url = new URL(config.getString("sweet.tracker.url") + "/tracking?t_key=" + config.getString("sweet.tracker.t_key") + "&t_code=" + tCode + "&t_invoice=" + t_invoice[k]);

				conn = (HttpURLConnection) url.openConnection();

				conn.setReadTimeout(5000); //스트림 리드 타임아웃 5초
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");

				//logger.debug("getUrlConnToJsonString2:input=====>" + url);

				String input = "";

				OutputStream os = conn.getOutputStream();
				os.write(input.getBytes());
				os.flush();

				//응답코드 체크
				if (conn.getResponseCode() != 200) {
					logger.debug("http response code error(" + conn.getResponseCode() + ")");
					if (conn != null) {
						conn.disconnect();
					}
					throw new AlertException("http response code error");
				} else {

					InputStream is = conn.getInputStream();
					byte[] buf = new byte[2048];
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					while (true) {
						int n = is.read(buf, 0, 2048);
						if (n > 0) {
							baos.write(buf, 0, n);
						} else if (n == -1) {
							break;
						} else {
							break;
						}
					}
					baos.flush();
					if (baos.size() != 0) {
						jsonString = new String(baos.toByteArray(), "UTF-8");
					}

					conn.disconnect();
					conn = null;

					if (!"".equals(jsonString)) {
						JSONObject jsonListObj = new JSONObject(jsonString);

						if (jsonListObj.length() > 0) {
							if (!jsonListObj.isNull("result")) {
								result = jsonListObj.get("result").toString();
								if ("N".equals(result)) {
									emptyYn = "Y";
								}
							} else {
								result = jsonListObj.get("tracking_info").toString();
								if (result.contains("ErrorCode")) {
									emptyYn = "Y";
								}
							}
						}
					}
					t_invoice_empty[k] = emptyYn;
				}
			}
		} catch (MalformedURLException e) {
			throw new AlertException("SweetTracker 배송추적 조회 오류발생 : " + e.getMessage());
		} catch (IOException e) {
			throw new AlertException("sweettracker 배송추적 조회 오류발생 : " + e.getMessage());
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		if ("N".equals(t_invoice_empty[0]) || "N".equals(t_invoice_empty[1])) {
			emptyYn = "N";
		} else {
			emptyYn = "Y";
		}
		return emptyYn;
	}

	@Override
	public Integer selectDeliveryStatusReverceCnt(PSCMCOM0005VO vo) throws Exception {
		return pscmcom0005Dao.selectDeliveryStatusReverceCnt(vo);
	}
}
