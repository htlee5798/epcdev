package com.lottemart.epc.edi.payment.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.payment.dao.NEDMPAY0120Dao;
import com.lottemart.epc.edi.payment.dao.PEDMPAY0020Dao;
import com.lottemart.epc.edi.payment.model.NEDMPAY0120VO;
import com.lottemart.epc.edi.payment.service.NEDMPAY0120Service;
import com.lottemart.epc.edi.payment.service.PEDMPAY0020Service;

@Service("nedmpay0120Service")
public class NEDMPAY0120ServiceImpl implements NEDMPAY0120Service{
	
	@Autowired
	private NEDMPAY0120Dao nedmpay0120Dao;
	
	public List<NEDMPAY0120VO> selectCredLedStoreDetail(NEDMPAY0120VO map) throws Exception{
		// TODO Auto-generated method stub
		return nedmpay0120Dao.selectCredLedStoreDetail(map );
	}
	
	public void createTextCredLedStoreDetail(NEDMPAY0120VO map ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		List<NEDMPAY0120VO> list = null;
		NEDMPAY0120VO hmap = null;
		StringBuffer sb = new StringBuffer();
		
		if (!StringUtils.trimToEmpty(request.getParameter("storeVal")).equals("")) {
			ArrayList	al	=	new ArrayList();
			String[] arrStore	=	request.getParameter("storeVal").split("-");
			
			for (int i = 0; i < arrStore.length; i++) {
				al.add(arrStore[i]);
			}
			
			map.setSearchStoreAl(al);
		}
		
		list =  nedmpay0120Dao.selectCredLedStoreDetail(map );
		
		long tot_top1 = 0;
		long tot_top2 = 0;
		long tot_top3 = 0;
		long tot_top4 = 0;
		long tot_bottom1 = 0;
		long tot_bottom2 = 0;
		long tot_bottom3 = 0;
		long tot_bottom4 = 0;
		long tot_sum = 0;
		
		sb.append("■ 점포별거래실적");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(map.getTextData());
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("월;점포;전월이월(공급가액);전월이월(세액);당월매입(공급가액);당월매입(세액);당월지급(공급가액);당월지급(세액);잔액(공급가액);잔액(세액);잔액합계;비고");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			hmap = list.get(i);
			
			sb.append(String.valueOf(hmap.getBuyYm()).replaceAll("-", ""));
			sb.append(";");
			sb.append(hmap.getStrNm());
			sb.append(";");
			sb.append(hmap.getTransBuyAmt());
			sb.append(";");
			sb.append(hmap.getBuyAmt());
			sb.append(";");
			sb.append(hmap.getPayBuyAmt());
			sb.append(";");
			sb.append(hmap.getRemBuyAmt());
			sb.append(";");
			sb.append(hmap.getTransVat());
			sb.append(";");
			sb.append(hmap.getVat());
			sb.append(";");
			sb.append(hmap.getPayVat());
			sb.append(";");
			sb.append(hmap.getRemVat());
			sb.append(";");
			sb.append(hmap.getRemSum());
			sb.append(";");
			
			if(hmap.getBoru()==null){
				sb.append("");
			}else{
				sb.append(hmap.getBoru());
			}
			
			sb.append("\r\n");
			
			tot_top1 += Long.parseLong(String.valueOf(hmap.getTransBuyAmt()));
			tot_top2 += Long.parseLong(String.valueOf(hmap.getBuyAmt()));
			tot_top3 += Long.parseLong(String.valueOf(hmap.getPayBuyAmt()));
			tot_top4 += Long.parseLong(String.valueOf(hmap.getRemBuyAmt()));
			tot_bottom1 += Long.parseLong(String.valueOf(hmap.getTransVat()));
			tot_bottom2 += Long.parseLong(String.valueOf(hmap.getVat()));
			tot_bottom3 += Long.parseLong(String.valueOf(hmap.getPayVat()));
			tot_bottom4 += Long.parseLong(String.valueOf(hmap.getRemVat()));
			tot_sum += Long.parseLong(String.valueOf(hmap.getRemSum()));
		}
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("합계;");
		sb.append(String.valueOf(tot_top1));
		sb.append(";");
		sb.append(String.valueOf(tot_top2));
		sb.append(";");
		sb.append(String.valueOf(tot_top3));
		sb.append(";");
		sb.append(String.valueOf(tot_top4));
		sb.append(";");
		sb.append(String.valueOf(tot_bottom1));
		sb.append(";");
		sb.append(String.valueOf(tot_bottom2));
		sb.append(";");
		sb.append(String.valueOf(tot_bottom3));
		sb.append(";");
		sb.append(String.valueOf(tot_bottom4));
		sb.append(";");
		sb.append(String.valueOf(tot_sum));
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		
		commonUtil.createTextFile(request, response, sb);
	}
	
	

}
