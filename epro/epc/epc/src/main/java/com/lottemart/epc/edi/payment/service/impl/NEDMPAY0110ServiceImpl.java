package com.lottemart.epc.edi.payment.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.payment.dao.NEDMPAY0110Dao;
import com.lottemart.epc.edi.payment.model.NEDMPAY0110VO;
import com.lottemart.epc.edi.payment.service.NEDMPAY0110Service;

@Service("nedmpay0110Service")
public class NEDMPAY0110ServiceImpl implements NEDMPAY0110Service{
	
	@Autowired
	private NEDMPAY0110Dao nedmpay0110Dao;
	/**
	 * 거래실적조회  - > 거래실적조회
	 * @param NEDMPAY0110VO
	 * @return
	 */
	public List<NEDMPAY0110VO> selectCredLedInfo(NEDMPAY0110VO map) throws Exception{
		// TODO Auto-generated method stub
		return nedmpay0110Dao.selectCredLedInfo(map );
	}
	/**
	 * 거래실적조회  - > 거래실적조회  txt파일 생성
	 * @param NEDMPAY0110VO
	 * @param request
	 * @param response
	 * @return
	 */
	public void createTextCredLed(NEDMPAY0110VO map ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		List<NEDMPAY0110VO> list = null;
		NEDMPAY0110VO hmap = null;
		StringBuffer sb = new StringBuffer();
		
		if (!StringUtils.trimToEmpty(request.getParameter("storeVal")).equals("")) {
			ArrayList	al	=	new ArrayList();
			String[] arrStore	=	request.getParameter("storeVal").split("-");
			
			for (int i = 0; i < arrStore.length; i++) {
				al.add(arrStore[i]);
			}
			
			map.setSearchStoreAl(al);
		}
		
		list =  nedmpay0110Dao.selectCredLedInfo(map );
		
		long tot_top1 = 0;
		long tot_top2 = 0;
		long tot_top3 = 0;
		long tot_top4 = 0;
		long tot_bottom1 = 0;
		long tot_bottom2 = 0;
		long tot_bottom3 = 0;
		long tot_bottom4 = 0;
		long tot_sum = 0;
		
		sb.append("■ 거래실적내역");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(map.getTextData());
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("월;전월이월(공급가액);전월이월(세액);당월매입(공급가액);당월매입(세액);당월지급(공급가액);당월지급(세액);잔액(공급가액);잔액(세액);잔액합계");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			hmap = list.get(i);
			
			sb.append(String.valueOf(hmap.getBuyYm()).replaceAll("-", ""));
			sb.append(";");
			sb.append(hmap.getTransBuyAmtTotal());
			sb.append(";");
			sb.append(hmap.getTransVatTotal());
			sb.append(";");
			sb.append(hmap.getBuyAmtTotal());
			sb.append(";");
			sb.append(hmap.getVatTotal());
			sb.append(";");
			sb.append(hmap.getPayBuyAmtTotal());
			sb.append(";");
			sb.append(hmap.getPayVatTotal());
			sb.append(";");
			sb.append(hmap.getRemBuyAmtTotal());
			sb.append(";");
			sb.append(hmap.getRemVatTotal());
			sb.append(";");
			sb.append(hmap.getRemSumTotal());
			sb.append("\r\n");
			
			tot_top1 += Long.parseLong(String.valueOf(hmap.getTransBuyAmtTotal()));
			tot_top2 += Long.parseLong(String.valueOf(hmap.getBuyAmtTotal()));
			tot_top3 += Long.parseLong(String.valueOf(hmap.getPayBuyAmtTotal()));
			tot_top4 += Long.parseLong(String.valueOf(hmap.getRemBuyAmtTotal()));
			tot_bottom1 += Long.parseLong(String.valueOf(hmap.getTransVatTotal()));
			tot_bottom2 += Long.parseLong(String.valueOf(hmap.getVatTotal()));
			tot_bottom3 += Long.parseLong(String.valueOf(hmap.getPayVatTotal()));
			tot_bottom4 += Long.parseLong(String.valueOf(hmap.getRemVatTotal()));
			tot_sum += Long.parseLong(String.valueOf(hmap.getRemSumTotal()));
			
			
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
