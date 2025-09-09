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
import com.lottemart.epc.order.dao.PSCMORD0008Dao;
import com.lottemart.epc.order.model.PSCMORD0007VO;
import com.lottemart.epc.order.service.PSCMORD0008Service;

@Service("PSCMORD0008Service")
public class PSCMORD0008ServiceImpl implements PSCMORD0008Service {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PSCMORD0008Dao pscmord0008Dao;

	public List<PSCMORD0007VO> selectSaleProductDetailList(Map<String, Object> map) throws Exception {
		return pscmord0008Dao.selectSaleProductDetailList(map);
	}

	public void createTextProductDetail(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<PSCMORD0007VO> list = null;
		String resultMsg = null;
		try {
			list = pscmord0008Dao.selectSaleProductDetailList(map);
			if (list == null || list.size() == 0) {
				resultMsg = messageSource.getMessage("msg.common.info.nodata", null, Locale.getDefault());
			}
		} catch (Exception e) {
			resultMsg = messageSource.getMessage("msg.common.fail.select", null, Locale.getDefault());
		}

		StringBuffer sb = new StringBuffer();
		sb.append("■ 상품상세별 매출내역").append("\r\n").append("\r\n");
		sb.append(map.get("text_data").toString()).append("\r\n");
		sb.append("------------------------------------------------------------------------------------").append("\r\n");
		sb.append("점포명;점포코드;상품코드;상품명;매출수량;매출금액").append("\r\n");
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				PSCMORD0007VO pscmord0007vo = list.get(i);
				sb.append(pscmord0007vo.getStrNm()).append(";");
				sb.append(pscmord0007vo.getStrCd()).append(";");
				sb.append(pscmord0007vo.getProdCd()).append(";");
				sb.append(pscmord0007vo.getProdNm()).append(";");
				sb.append(pscmord0007vo.getSaleQty()).append(";");
				sb.append(pscmord0007vo.getSaleSaleAmt()).append("\r\n");
			}
		} else {
			sb.append(resultMsg).append(";").append("\r\n");;
		}
		sb.append("------------------------------------------------------------------------------------");
		commonUtil.createTextFile(request, response, sb);
	}
}