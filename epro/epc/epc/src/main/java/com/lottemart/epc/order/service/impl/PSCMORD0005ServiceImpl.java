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
import com.lottemart.epc.order.dao.PSCMORD0005Dao;
import com.lottemart.epc.order.service.PSCMORD0005Service;

@Service("PSCMORD0005Service")
public class PSCMORD0005ServiceImpl implements PSCMORD0005Service {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PSCMORD0005Dao pscmord0005Dao;

	@Override
	public List<DataMap> selectSaleInfoByDate(Map<String, Object> map) throws Exception {
		return pscmord0005Dao.selectSaleInfoByDate(map);
	}

	@Override
	public void createTextDay(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<DataMap> list = null;
		String resultMsg = null;
		try {
			list = pscmord0005Dao.selectSaleInfoByDate(map);
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
		sb.append("일자;매출수량;매출금액").append("\r\n");
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				DataMap result = list.get(i);
				sb.append(result.get("SALE_DY")).append(";");
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
