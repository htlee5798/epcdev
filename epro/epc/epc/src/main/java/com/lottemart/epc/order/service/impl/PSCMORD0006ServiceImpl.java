package com.lottemart.epc.order.service.impl;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.order.dao.PSCMORD0006Dao;
import com.lottemart.epc.order.service.PSCMORD0006Service;

@Service("PSCMORD0006Service")
public class PSCMORD0006ServiceImpl implements PSCMORD0006Service {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PSCMORD0006Dao pscmord0006Dao;

	public List<DataMap> selectSaleInfoByStore(Map<String, Object> map) throws Exception {
		return pscmord0006Dao.selectSaleInfoByStore(map);
	}

	@Override
	public void createTextDay(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<DataMap> list = null;
		String resultMsg = null;
		try {
			list = pscmord0006Dao.selectSaleInfoByStore(map);
			if (list == null || list.size() == 0) {
				resultMsg = messageSource.getMessage("msg.common.info.nodata", null, Locale.getDefault());
			}
		} catch (Exception e) {
			resultMsg = messageSource.getMessage("msg.common.fail.select", null, Locale.getDefault());
		}

		StringBuffer sb = new StringBuffer();
		sb.append("■ 일자별 매출내역").append("\r\n").append("\r\n");
		sb.append(map.get("text_data")).append("\r\n");
		sb.append("------------------------------------------------------------------------------------").append("\r\n");
		sb.append("점포명;점포코드;매출수량;매출금액").append("\r\n");
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				DataMap result = list.get(i);
				
				sb.append(result.get("STR_NM")).append(";");
				sb.append(result.get("STR_CD")).append(";");
				sb.append(result.get("SALE_QTY")).append(";");
				sb.append(result.get("SALE_AMT")).append("\r\n");
			}
		} else {
			sb.append(resultMsg).append(";").append("\r\n");;
		}
		sb.append("------------------------------------------------------------------------------------");
		commonUtil.createTextFile(request, response, sb);
	}

}
