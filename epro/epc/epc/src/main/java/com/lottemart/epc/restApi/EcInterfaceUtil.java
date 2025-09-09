package com.lottemart.epc.restApi;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.RestConst;

@Component("EcInterfaceUtil")
public class EcInterfaceUtil {

	private static final Logger logger = LoggerFactory.getLogger(EcInterfaceUtil.class);

	private final RestAPIUtil restApiUtil = new RestAPIUtil();
	/**
	 * EC I/F DL_API_0006
	 * Desc : DL_API_0006
	 * @Method Name : sendDL0006
	 * @param DataMap
	 * @return String
	 * @exception Exception
	 */
	public String sendDL0006(DataMap paramMap){
		//요청 파라미터 1 : 배송번호(DELI_NO)(필수), 배송순번(DELI_SEQ)
		//요청 파라미터 2 : 주문번호(ORDER_ID)(필수), 주문아이템순번(ORDER_ITEM_SEQ)
		String jsonString = "";
		try {
			jsonString = restApiUtil.sendRestCall(RestConst.DL_API_0006, HttpMethod.POST, paramMap, true);
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return jsonString;
	}

	/**
	 * EC I/F DL_API_0007
	 * Desc : DL_API_0007
	 * @Method Name : sendDL0007
	 * @param Map<String, Object>
	 * @return String
	 * @exception Exception
	 */
	public String sendDL0007(DataMap paramMap){
		//요청 파라미터 1 : 배송번호(DELI_NO)(필수), 배송순번(DELI_SEQ), 배송상태코드(DELI_STATUS_CD)
		//요청 파라미터 2 : 주문번호(ORDER_ID)(필수), 주문아이템순번(ORDER_ITEM_SEQ), 배송상태코드(DELI_STATUS_CD)
		//요청 파라미터 3 : 점포코드(STR_CD)(필수), 배송일자(DELI_HOPE_DY)(필수), 배송차수(DELI_SEQ)(필수), 배송상태코드(DELI_STATUS_CD)

		//요청 파라미터로 배송상태코드(DELI_STATUS_CD)를 포함할 경우, 해당 상태값으로 ec 전송
		//요청 파라미터로 배송상태코드(DELI_STATUS_CD)를 포함하지 않을 경우, DB상의 상태값으로 ec 전송
		String jsonString = "";
		try {
			//재배송일 경우, 일반 배송 송장으로 변경하여 전송

			jsonString = restApiUtil.sendRestCall(RestConst.DL_API_0007, HttpMethod.POST, paramMap, true);
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return jsonString;
	}

}
