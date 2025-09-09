package com.lottemart.epc.edi.payment.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.payment.dao.NEDMPAY0130Dao;
import com.lottemart.epc.edi.payment.model.NEDMPAY0130VO;
import com.lottemart.epc.edi.payment.service.NEDMPAY0130Service;

@Service("nedmpay0130Service")
public class NEDMPAY0130ServiceImpl implements NEDMPAY0130Service{
	
	@Autowired
	private NEDMPAY0130Dao nedmpay0130Dao;
	/**
	 * 거래실적조회  - > 잔액조회
	 * @param NEDMPAY0130VO
	 * @return
	 */
	public List<NEDMPAY0130VO> selectCredLedStoreInfo(NEDMPAY0130VO  map) throws Exception{
		// TODO Auto-generated method stub
		return nedmpay0130Dao.selectCredLedStoreInfo(map );
	}
	/**
	 * 거래실적조회  - > 잔액조회 txt파일 생성
	 * @param NEDMPAY0130VO
	 * @param request
	 * @param response
	 * @return
	 */
	public void createTextCredLedStore(NEDMPAY0130VO  map ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		List<NEDMPAY0130VO> list = null;
		NEDMPAY0130VO hmap = null;
		StringBuffer sb = new StringBuffer();
		
		if (!StringUtils.trimToEmpty(request.getParameter("storeVal")).equals("")) {
			ArrayList	al	=	new ArrayList();
			String[] arrStore	=	request.getParameter("storeVal").split("-");
			
			for (int i = 0; i < arrStore.length; i++) {
				al.add(arrStore[i]);
			}
			
			map.setSearchStoreAl(al);
		}
		
		list =  nedmpay0130Dao.selectCredLedStoreInfo(map );
		
		long tot1 = 0;
		long tot2 = 0;
		long tot3 = 0;
		
		sb.append("■ 잔액내역");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(map.getTextData());
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("월;점포;사업자번호 ;공급가액;세액;합계");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			hmap = list.get(i);
			
			sb.append(String.valueOf(hmap.getBuyYm()).replaceAll("-", ""));
			sb.append(";");
			sb.append(hmap.getStrNm());
			sb.append(";");
			sb.append(hmap.getBmanNo());
			sb.append(";");
			sb.append(hmap.getRemBuyAmt());
			sb.append(";");
			sb.append(hmap.getRemVat());
			sb.append(";");
			sb.append(hmap.getRemSum());
			sb.append("\r\n");
			
			tot1 += Long.parseLong(String.valueOf(hmap.getRemBuyAmt()));
			tot2 += Long.parseLong(String.valueOf(hmap.getRemVat()));
			tot3 += Long.parseLong(String.valueOf(hmap.getRemSum()));
		}
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("합계;");
		sb.append(String.valueOf(tot1));
		sb.append(";");
		sb.append(String.valueOf(tot2));
		sb.append(";");
		sb.append(String.valueOf(tot3));
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		
		
		commonUtil.createTextFile(request, response, sb);
	}
	

}
