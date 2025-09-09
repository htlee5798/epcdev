package com.lottemart.epc.order.service.impl;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.order.dao.PSCMORD0007Dao;
import com.lottemart.epc.order.model.PSCMORD0007VO;
import com.lottemart.epc.order.service.PSCMORD0007Service;

@Service("PSCMORD0007Service")
public class PSCMORD0007ServiceImpl implements PSCMORD0007Service {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PSCMORD0007Dao pscmord0007Dao;

	public List<PSCMORD0007VO> selectSaleProductList(Map<String, Object> map) throws Exception {
		return pscmord0007Dao.selectSaleProductList(map);
	}

	public void createTextProduct(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<PSCMORD0007VO> list = null;
		String resultMsg = null;
		try {
			list = pscmord0007Dao.selectSaleProductList(map);
			if (list == null || list.size() == 0) {
				resultMsg = messageSource.getMessage("msg.common.info.nodata", null, Locale.getDefault());
			}
		} catch (Exception e) {
			resultMsg = messageSource.getMessage("msg.common.fail.select", null, Locale.getDefault());
		}

		StringBuffer sb = new StringBuffer();
		sb.append("■ 상품별 매출내역").append("\r\n").append("\r\n");
		sb.append(map.get("text_data").toString()).append("\r\n");
		sb.append("------------------------------------------------------------------------------------").append("\r\n");
		if (list != null && list.size() > 0) {
			sb.append("상품코드;상품명;매출수량;매출금액").append("\r\n");
			for (int i = 0; i < list.size(); i++) {
				PSCMORD0007VO pscmord0007vo = list.get(i);
				sb.append(pscmord0007vo.getProdCd()).append(";");
				sb.append(pscmord0007vo.getProdNm()).append(";");
				sb.append(pscmord0007vo.getSaleQty()).append(";");
				sb.append(pscmord0007vo.getSaleAmt()).append("\r\n");
			}
		} else {
			sb.append(resultMsg).append(";").append("\r\n");;
		}
		sb.append("------------------------------------------------------------------------------------");
		commonUtil.createTextFile(request, response, sb);
	}

}