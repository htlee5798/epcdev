package com.lottemart.epc.edi.payment.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.payment.dao.NEDMPAY0040Dao;
import com.lottemart.epc.edi.payment.model.NEDMPAY0040VO;
import com.lottemart.epc.edi.payment.service.NEDMPAY0040Service;

@Service("nedmpay0040Service")
public class NEDMPAY0040ServiceImpl implements NEDMPAY0040Service{
	
	@Autowired
	private NEDMPAY0040Dao nedmpay0040Dao;
	
	/**
	 * 기간별 결산정보  - > 세금계산서 정보 
	 * @param NEDMPAY0040VO
	 * @return
	 */
	public List<NEDMPAY0040VO> selectCredAggInfo(NEDMPAY0040VO map) throws Exception{
		// TODO Auto-generated method stub
		return nedmpay0040Dao.selectCredAggInfo(map );
	}
	/**
	 * 기간별 결산정보  - > 세금계산서 정보 txt 파일 생성
	 * @param NEDMPAY0040VO
	 * @param request
	 * @param response
	 * @return
	 */
	public void createTextCredAgg(NEDMPAY0040VO map,HttpServletRequest request, HttpServletResponse response) throws Exception{
		List<NEDMPAY0040VO> list = null;
		NEDMPAY0040VO hmap = null;
		StringBuffer sb = new StringBuffer();
		
		if (!StringUtils.trimToEmpty(request.getParameter("storeVal")).equals("")) {
			ArrayList	al	=	new ArrayList();
			String[] arrStore	=	request.getParameter("storeVal").split("-");
			
			for (int i = 0; i < arrStore.length; i++) {
				al.add(arrStore[i]);
			}
			
			map.setSearchStoreAl(al);
		}
		
		list =  nedmpay0040Dao.selectCredAggInfo(map );
		
		float buy_amt_sum = 0;
		float vat_sum = 0;
		float total_sum= 0;
		
		sb.append("■ 세금계산서 정보");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(map.getTextData());
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("점포;과세구분;공급가;세액;합계");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			hmap = (NEDMPAY0040VO)list.get(i);
			
			sb.append(hmap.getStrNm());
			sb.append(";");
			sb.append(hmap.getTaxFg());
			sb.append(";");
			sb.append(hmap.getBuyAmt());
			sb.append(";");
			sb.append(hmap.getVat());
			sb.append(";");
			sb.append(hmap.getTotal());
			sb.append("\r\n");
			
			if (!StringUtils.trimToEmpty(hmap.getBuyAmt().toString()).equals("")) {
				buy_amt_sum += Float.parseFloat(String.valueOf(hmap.getBuyAmt()));
			}
			
			if (!StringUtils.trimToEmpty(hmap.getVat().toString()).equals("")) {
				vat_sum += Float.parseFloat(String.valueOf(hmap.getVat()));
			}
			
			if (!StringUtils.trimToEmpty(hmap.getTotal().toString()).equals("")) {
				total_sum += Float.parseFloat(String.valueOf(hmap.getTotal()));
			}			
		}
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("합계;");
		sb.append(String.valueOf(buy_amt_sum));
		sb.append(";");
		sb.append(String.valueOf(vat_sum));
		sb.append(";");
		sb.append(String.valueOf(total_sum));
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		
		commonUtil.createTextFile(request, response, sb);
	}

}

