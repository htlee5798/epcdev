package com.lottemart.epc.edi.weborder.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.comm.service.RFCCommonService;
import com.lottemart.epc.edi.weborder.dao.NEDMWEB0120Dao;
import com.lottemart.epc.edi.weborder.model.NEDMWEB0120VO;
import com.lottemart.epc.edi.weborder.service.NEDMWEB0120Service;

@Service("NEDMWEB0120Service")
public class NEDMWEB0120ServiceImpl implements NEDMWEB0120Service {

	@Resource(name="rfcCommonService")
	private RFCCommonService rfcCommonService;

	@Autowired
	private NEDMWEB0120Dao nedmweb0120dao;


	/**
	 * 조회
	 * @param NEDMWEB0110VO
	 * @return List<NEDMWEB0110VO>
	 * @throws SQLException
	 */
	public List<NEDMWEB0120VO> NEDMWEB0120selectDayRtnProdList(NEDMWEB0120VO vo) throws Exception {
		return nedmweb0120dao.selectDayRtnProdList(vo);		// 목록
	}

	/**
	 * 수량등록
	 */
	public String updateReturnProdData(NEDMWEB0120VO updateParam) throws Exception {
		for (NEDMWEB0120VO param: updateParam.getInsertParam()) {
			nedmweb0120dao.EDI_TPC_PO_RRL_MST_01(param);
			nedmweb0120dao.EDI_TPC_PO_RRL_PROD_02(param);
		}

		return "0";
	}

	/**
	 * 삭제
	 */
	public String deleteReturnProdData(NEDMWEB0120VO updateParam) throws Exception {
		for (NEDMWEB0120VO  param: updateParam.getInsertParam()) {
			String rrlReqNo = param.getRrlReqNo();
			String rrlTotPrc = param.getRrlTotPrc();
			String rrlQty = param.getRrlQty();

			nedmweb0120dao.EDI_TPC_PO_RRL_MST_02(param);
			nedmweb0120dao.EDI_TPC_PO_RRL_PROD_03(param);
		}

		return "0";
	}

	/**
	 * 반품요청
	 * @param NEDMWEB0110VO
	 * @throws SQLException
	 */
	public String sendReturn(NEDMWEB0120VO updateParam,String adminId) throws Exception {
		Map<String , Object> reqParam = new HashMap<String , Object>();
		List<String> rrlReqNos = new ArrayList<String>();

		for (NEDMWEB0120VO  param: updateParam.getInsertParam()) {
			nedmweb0120dao.EDI_TPC_PO_RRL_MST_01(param);
			nedmweb0120dao.EDI_TPC_PO_RRL_PROD_02(param);
			rrlReqNos.add(param.getRrlReqNo());
		}

		reqParam.put("rrlReqNos", rrlReqNos);
		List<Map> result = nedmweb0120dao.selectRfcReqData(reqParam);
		HashMap	 		reqCommonMap	=	new HashMap();	// RFC 응답

		reqCommonMap.put("ZPOSOURCE", "");
		reqCommonMap.put("ZPOTARGET", "");
		reqCommonMap.put("ZPONUMS", "");
		reqCommonMap.put("ZPOROWS", "");
		reqCommonMap.put("ZPODATE", "");
		reqCommonMap.put("ZPOTIME", "");

		JSONObject obj	=	new JSONObject();

		obj.put("TAB", result);			// HashMap에 담긴 데이터 JSONObject로 ...
		obj.put("REQCOMMON", reqCommonMap);		// RFC 응답 HashMap JsonObject로....

		rfcCommonService.rfcCall("INV0690", obj.toString(), adminId);

		nedmweb0120dao.updateRfcReqData(reqParam);

		return "0";
	}

}
