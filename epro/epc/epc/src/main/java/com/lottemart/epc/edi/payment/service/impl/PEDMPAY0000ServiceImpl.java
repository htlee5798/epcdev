package com.lottemart.epc.edi.payment.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.payment.dao.PEDMPAY0000Dao;
import com.lottemart.epc.edi.payment.service.PEDMPAY0000Service;

@Service("pedmpay0000Service")
public class PEDMPAY0000ServiceImpl implements PEDMPAY0000Service{
	
	@Autowired
	private PEDMPAY0000Dao pedmpay0000Dao;
	
	
	public List selectCominforInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmpay0000Dao.selectCominforInfo(map );
	}
	
	public List selectPaymentDayInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmpay0000Dao.selectPaymentDayInfo(map );
	}
	
	public void createTextPaymentDay(Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception{
		List list = new ArrayList();
		HashMap<String, Object> hmap = new HashMap();
		StringBuffer sb = new StringBuffer();
		
		list =  pedmpay0000Dao.selectPaymentDayInfo(map );
		
		long pay_buy_sum=0;
		long mul_sum=0;
		long panjang_sum=0;
		long usply_sum=0;
		long sinsang_sum=0;
		long sugi_sum=0;
		long gita_sum=0;
		long total_sum=0;
		long sub_buy_sum=0;
		
		sb.append("■ 대금결제정보");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(map.get("text_data"));
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("대금지급일;지급대상액;물류비;판매장려금;미납패널티;신상품촉진비;수기장려금;기타공제;공제내역계;네트워크론지급;실지급액");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			hmap = (HashMap)list.get(i);
			
			if(hmap.get("PAY_DAY")==null){
				sb.append("");
			}else{
				sb.append(String.valueOf(hmap.get("PAY_DAY")).replaceAll("-", ""));
			}
			sb.append(";");
			sb.append(hmap.get("PAY_BUY"));
			sb.append(";");
			sb.append(hmap.get("MUL"));
			sb.append(";");
			sb.append(hmap.get("PANJANG"));
			sb.append(";");
			sb.append(hmap.get("USPLY"));
			sb.append(";");
			sb.append(hmap.get("SINSANG"));
			sb.append(";");
			sb.append(hmap.get("SUGI"));
			sb.append(";");
			sb.append(hmap.get("GITA"));
			sb.append(";");
			sb.append(hmap.get("TOTAL"));
			sb.append(";");
			sb.append("0");
			sb.append(";");
			sb.append(hmap.get("SUB_BUY"));
			sb.append("\r\n");
			
			pay_buy_sum += Long.parseLong(String.valueOf(hmap.get("PAY_BUY")));
			mul_sum += Long.parseLong(String.valueOf(hmap.get("MUL")));
			panjang_sum += Long.parseLong(String.valueOf(hmap.get("PANJANG")));
			usply_sum += Long.parseLong(String.valueOf(hmap.get("USPLY")));
			sinsang_sum += Long.parseLong(String.valueOf(hmap.get("SINSANG")));
			sugi_sum += Long.parseLong(String.valueOf(hmap.get("SUGI")));
			gita_sum += Long.parseLong(String.valueOf(hmap.get("GITA")));
			total_sum += Long.parseLong(String.valueOf(hmap.get("TOTAL")));
			sub_buy_sum += Long.parseLong(String.valueOf(hmap.get("SUB_BUY")));
		}
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("합계;");
		sb.append(String.valueOf(pay_buy_sum));
		sb.append(";");
		sb.append(String.valueOf(mul_sum));
		sb.append(";");
		sb.append(String.valueOf(panjang_sum));
		sb.append(";");
		sb.append(String.valueOf(usply_sum));
		sb.append(";");
		sb.append(String.valueOf(sinsang_sum));
		sb.append(";");
		sb.append(String.valueOf(sugi_sum));
		sb.append(";");
		sb.append(String.valueOf(gita_sum));
		sb.append(";");
		sb.append(String.valueOf(total_sum));
		sb.append(";");
		sb.append(String.valueOf(sub_buy_sum));
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		
		commonUtil.createTextFile(request, response, sb);
	}
	
	public List selectPaymentStoreInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmpay0000Dao.selectPaymentStoreInfo(map );
	}
	
	public void createTextPaymentStore(Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception{
		List list = new ArrayList();
		HashMap<String, Object> hmap = new HashMap();
		StringBuffer sb = new StringBuffer();
		
		list =  pedmpay0000Dao.selectPaymentStoreInfo(map );
		
		long pay_buy_sum=0;
		long mul_sum=0;
		long panjang_sum=0;
		long usply_sum=0;
		long sinsang_sum=0;
		long sugi_sum=0;
		long gita_sum=0;
		long total_sum=0;
		long sub_buy_sum=0;
		
		sb.append("■ 점포별 대금 결제 정보");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(map.get("text_data"));
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("지급일자;점포;지급대상액;물류비;판매장려금;미납패널티;신상품촉진비;수기장려금;기타공제;공제내역 및 금액 계;네트워크론지급;실지급액");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			hmap = (HashMap)list.get(i);
			
			if(hmap.get("PAY_DAY")==null){
				sb.append("");
			}else{
				sb.append(String.valueOf(hmap.get("PAY_DAY")).replaceAll("-", ""));
			}
			sb.append(";");
			sb.append(hmap.get("STR_NM"));
			sb.append(";");
			sb.append(hmap.get("PAY_BUY"));
			sb.append(";");
			sb.append(hmap.get("MUL"));
			sb.append(";");
			sb.append(hmap.get("PANJANG"));
			sb.append(";");
			sb.append(hmap.get("USPLY"));
			sb.append(";");
			sb.append(hmap.get("SINSANG"));
			sb.append(";");
			sb.append(hmap.get("SUGI"));
			sb.append(";");
			sb.append(hmap.get("GITA"));
			sb.append(";");
			sb.append(hmap.get("TOTAL"));
			sb.append(";");
			sb.append("0");
			sb.append(";");
			sb.append(hmap.get("SUB_BUY"));
			sb.append("\r\n");
			
			pay_buy_sum += Long.parseLong(String.valueOf(hmap.get("PAY_BUY")));
			mul_sum += Long.parseLong(String.valueOf(hmap.get("MUL")));
			panjang_sum += Long.parseLong(String.valueOf(hmap.get("PANJANG")));
			usply_sum += Long.parseLong(String.valueOf(hmap.get("USPLY")));
			sinsang_sum += Long.parseLong(String.valueOf(hmap.get("SINSANG")));
			sugi_sum += Long.parseLong(String.valueOf(hmap.get("SUGI")));
			gita_sum += Long.parseLong(String.valueOf(hmap.get("GITA")));
			total_sum += Long.parseLong(String.valueOf(hmap.get("TOTAL")));
			sub_buy_sum += Long.parseLong(String.valueOf(hmap.get("SUB_BUY")));
		}
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("합계;");
		sb.append(String.valueOf(pay_buy_sum));
		sb.append(";");
		sb.append(String.valueOf(mul_sum));
		sb.append(";");
		sb.append(String.valueOf(panjang_sum));
		sb.append(";");
		sb.append(String.valueOf(usply_sum));
		sb.append(";");
		sb.append(String.valueOf(sinsang_sum));
		sb.append(";");
		sb.append(String.valueOf(sugi_sum));
		sb.append(";");
		sb.append(String.valueOf(gita_sum));
		sb.append(";");
		sb.append(String.valueOf(total_sum));
		sb.append(";");
		sb.append(String.valueOf(sub_buy_sum));
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		commonUtil.createTextFile(request, response, sb);
	}
	
	public List selectCredAggInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmpay0000Dao.selectCredAggInfo(map );
	}
	
	public void createTextCredAgg(Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception{
		List list = new ArrayList();
		HashMap<String, Object> hmap = new HashMap();
		StringBuffer sb = new StringBuffer();
		
		list =  pedmpay0000Dao.selectCredAggInfo(map );
		
		long buy_amt_sum = 0;
		long vat_sum = 0;
		long total_sum= 0;
		
		sb.append("■ 세금계산서 정보");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(map.get("text_data"));
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("점포;과세구분;공급가;세액;합계");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			hmap = (HashMap)list.get(i);
			
			sb.append(hmap.get("STR_NM"));
			sb.append(";");
			sb.append(hmap.get("TAX_FG"));
			sb.append(";");
			sb.append(hmap.get("BUY_AMT"));
			sb.append(";");
			sb.append(hmap.get("VAT"));
			sb.append(";");
			sb.append(hmap.get("TOTAL"));
			sb.append("\r\n");
			
			buy_amt_sum += Long.parseLong(String.valueOf(hmap.get("BUY_AMT")));
			vat_sum += Long.parseLong(String.valueOf(hmap.get("VAT")));
			total_sum += Long.parseLong(String.valueOf(hmap.get("TOTAL")));
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
	
	public List selectLogiAmtInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmpay0000Dao.selectLogiAmtInfo(map );
	}
	
	public void createTextLogiAmt(Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception{
		List list = new ArrayList();
		HashMap<String, Object> hmap = new HashMap();
		StringBuffer sb = new StringBuffer();
		
		list =  pedmpay0000Dao.selectLogiAmtInfo(map );
		
		long str_sply_amt_sum = 0;
		long sub_amt_sum = 0;
		long vat_sum = 0;
		long sub_sum_sum = 0;
		
		sb.append("■ 물류비 정보");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(map.get("text_data"));
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("월;점포;물류통과금액;공급가;세액;합계;물류요율");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			hmap = (HashMap)list.get(i);
			
			sb.append(hmap.get("SUB_MM"));
			sb.append(";");
			sb.append(hmap.get("STR_NM"));
			sb.append(";");
			sb.append(hmap.get("STR_SPLY_AMT"));
			sb.append(";");
			sb.append(hmap.get("SUB_AMT"));
			sb.append(";");
			sb.append(hmap.get("VAT"));
			sb.append(";");
			sb.append(hmap.get("SUB_SUM"));
			sb.append(";");
			sb.append(hmap.get("RATE"));
			sb.append("\r\n");
			
			str_sply_amt_sum += Long.parseLong(String.valueOf(hmap.get("STR_SPLY_AMT")));
			sub_amt_sum += Long.parseLong(String.valueOf(hmap.get("SUB_AMT")));
			vat_sum += Long.parseLong(String.valueOf(hmap.get("VAT")));
			sub_sum_sum += Long.parseLong(String.valueOf(hmap.get("SUB_SUM")));
		}
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("합계;");
		sb.append(String.valueOf(str_sply_amt_sum));
		sb.append(";");
		sb.append(String.valueOf(sub_amt_sum));
		sb.append(";");
		sb.append(String.valueOf(vat_sum));
		sb.append(";");
		sb.append(String.valueOf(sub_sum_sum));
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		
		commonUtil.createTextFile(request, response, sb);
	}
	
	public List selectPromoSaleInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmpay0000Dao.selectPromoSaleInfo(map);
	}
	public List selectPromoNewSaleInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmpay0000Dao.selectPromoNewSaleInfo(map);
	}

}

