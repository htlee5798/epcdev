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
import com.lottemart.epc.edi.payment.dao.PEDMPAY0020Dao;
import com.lottemart.epc.edi.payment.service.PEDMPAY0020Service;

@Service("pedmpay0020Service")
public class PEDMPAY0020ServiceImpl implements PEDMPAY0020Service{
	
	@Autowired
	private PEDMPAY0020Dao pedmpay0020Dao;
	
	public List selectCredLedInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmpay0020Dao.selectCredLedInfo(map );
	}
	
	public void createTextCredLed(Map<String,Object> map ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		List list = new ArrayList();
		HashMap<String, Object> hmap = new HashMap();
		StringBuffer sb = new StringBuffer();
		
		list =  pedmpay0020Dao.selectCredLedInfo(map );
		
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
		sb.append(map.get("text_data"));
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("월;전월이월(공급가액);전월이월(세액);당월매입(공급가액);당월매입(세액);당월지급(공급가액);당월지급(세액);잔액(공급가액);잔액(세액);잔액합계");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			hmap = (HashMap)list.get(i);
			
			sb.append(String.valueOf(hmap.get("BUY_YM")).replaceAll("-", ""));
			sb.append(";");
			sb.append(hmap.get("TRANS_BUY_AMT_TOTAL"));
			sb.append(";");
			sb.append(hmap.get("TRANS_VAT_TOTAL"));
			sb.append(";");
			sb.append(hmap.get("BUY_AMT_TOTAL"));
			sb.append(";");
			sb.append(hmap.get("VAT_TOTAL"));
			sb.append(";");
			sb.append(hmap.get("PAY_BUY_AMT_TOTAL"));
			sb.append(";");
			sb.append(hmap.get("PAY_VAT_TOTAL"));
			sb.append(";");
			sb.append(hmap.get("REM_BUY_AMT_TOTAL"));
			sb.append(";");
			sb.append(hmap.get("REM_VAT_TOTAL"));
			sb.append(";");
			sb.append(hmap.get("REM_SUM_TOTAL"));
			sb.append("\r\n");
			
			tot_top1 += Long.parseLong(String.valueOf(hmap.get("TRANS_BUY_AMT_TOTAL")));
			tot_top2 += Long.parseLong(String.valueOf(hmap.get("BUY_AMT_TOTAL")));
			tot_top3 += Long.parseLong(String.valueOf(hmap.get("PAY_BUY_AMT_TOTAL")));
			tot_top4 += Long.parseLong(String.valueOf(hmap.get("REM_BUY_AMT_TOTAL")));
			tot_bottom1 += Long.parseLong(String.valueOf(hmap.get("TRANS_VAT_TOTAL")));
			tot_bottom2 += Long.parseLong(String.valueOf(hmap.get("VAT_TOTAL")));
			tot_bottom3 += Long.parseLong(String.valueOf(hmap.get("PAY_VAT_TOTAL")));
			tot_bottom4 += Long.parseLong(String.valueOf(hmap.get("REM_VAT_TOTAL")));
			tot_sum += Long.parseLong(String.valueOf(hmap.get("REM_SUM_TOTAL")));
			
			
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
	
	public List selectCredLedStoreDetail(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmpay0020Dao.selectCredLedStoreDetail(map );
	}
	
	public void createTextCredLedStoreDetail(Map<String,Object> map ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		List list = new ArrayList();
		HashMap<String, Object> hmap = new HashMap();
		StringBuffer sb = new StringBuffer();
		
		list =  pedmpay0020Dao.selectCredLedStoreDetail(map );
		
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
		sb.append(map.get("text_data"));
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("월;점포;전월이월(공급가액);전월이월(세액);당월매입(공급가액);당월매입(세액);당월지급(공급가액);당월지급(세액);잔액(공급가액);잔액(세액);잔액합계;비고");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			hmap = (HashMap)list.get(i);
			
			sb.append(String.valueOf(hmap.get("BUY_YM")).replaceAll("-", ""));
			sb.append(";");
			sb.append(hmap.get("STR_NM"));
			sb.append(";");
			sb.append(hmap.get("TRANS_BUY_AMT"));
			sb.append(";");
			sb.append(hmap.get("BUY_AMT"));
			sb.append(";");
			sb.append(hmap.get("PAY_BUY_AMT"));
			sb.append(";");
			sb.append(hmap.get("REM_BUY_AMT"));
			sb.append(";");
			sb.append(hmap.get("TRANS_VAT"));
			sb.append(";");
			sb.append(hmap.get("VAT"));
			sb.append(";");
			sb.append(hmap.get("PAY_VAT"));
			sb.append(";");
			sb.append(hmap.get("REM_VAT"));
			sb.append(";");
			sb.append(hmap.get("REM_SUM"));
			sb.append(";");
			
			if(hmap.get("BORU")==null){
				sb.append("");
			}else{
				sb.append(hmap.get("BORU"));
			}
			
			sb.append("\r\n");
			
			tot_top1 += Long.parseLong(String.valueOf(hmap.get("TRANS_BUY_AMT")));
			tot_top2 += Long.parseLong(String.valueOf(hmap.get("BUY_AMT")));
			tot_top3 += Long.parseLong(String.valueOf(hmap.get("PAY_BUY_AMT")));
			tot_top4 += Long.parseLong(String.valueOf(hmap.get("REM_BUY_AMT")));
			tot_bottom1 += Long.parseLong(String.valueOf(hmap.get("TRANS_VAT")));
			tot_bottom2 += Long.parseLong(String.valueOf(hmap.get("VAT")));
			tot_bottom3 += Long.parseLong(String.valueOf(hmap.get("PAY_VAT")));
			tot_bottom4 += Long.parseLong(String.valueOf(hmap.get("REM_VAT")));
			tot_sum += Long.parseLong(String.valueOf(hmap.get("REM_SUM")));
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
	
	public List selectCredLedStoreInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmpay0020Dao.selectCredLedStoreInfo(map );
	}
	
	public void createTextCredLedStore(Map<String,Object> map ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		List list = new ArrayList();
		HashMap<String, Object> hmap = new HashMap();
		StringBuffer sb = new StringBuffer();
		
		list =  pedmpay0020Dao.selectCredLedStoreInfo(map );
		
		long tot1 = 0;
		long tot2 = 0;
		long tot3 = 0;
		
		sb.append("■ 잔액내역");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(map.get("text_data"));
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("월;점포;사업자번호 ;공급가액;세액;합계");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			hmap = (HashMap)list.get(i);
			
			sb.append(String.valueOf(hmap.get("BUY_YM")).replaceAll("-", ""));
			sb.append(";");
			sb.append(hmap.get("STR_NM"));
			sb.append(";");
			sb.append(hmap.get("BMAN_NO"));
			sb.append(";");
			sb.append(hmap.get("REM_BUY_AMT"));
			sb.append(";");
			sb.append(hmap.get("REM_VAT"));
			sb.append(";");
			sb.append(hmap.get("REM_SUM"));
			sb.append("\r\n");
			
			tot1 += Long.parseLong(String.valueOf(hmap.get("REM_BUY_AMT")));
			tot2 += Long.parseLong(String.valueOf(hmap.get("REM_VAT")));
			tot3 += Long.parseLong(String.valueOf(hmap.get("REM_SUM")));
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
	
	public List selectFamilyLoan(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmpay0020Dao.selectFamilyLoan(map );
	}
	
	public void createTextFamilyLoan(Map<String,Object> map ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		List list = new ArrayList();
		HashMap<String, Object> hmap = new HashMap();
		StringBuffer sb = new StringBuffer();
		
		list =  pedmpay0020Dao.selectFamilyLoan(map );
		
		sb.append("■ 패밀리론");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(map.get("text_data"));
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("납품번호;정기지급일;은행통보금액 ;대출가능액;매입/매출금액;물류비	;장려금;공제예상액;당일전송대상액;통보금액;대출가능액;통보금액;대출가능액");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			hmap = (HashMap)list.get(i);
			
			sb.append(hmap.get("SLIP_NO"));
			sb.append(";");
			sb.append(String.valueOf(hmap.get("PAY_DAY")).replaceAll("-", ""));
			sb.append(";");
			sb.append(hmap.get("SEND_AMT"));
			sb.append(";");
			sb.append(hmap.get("ABLE_AMT"));
			sb.append(";");
			sb.append(hmap.get("SPLY_AMT"));
			sb.append(";");
			sb.append(hmap.get("LOGI_AMT"));
			
			sb.append(";");
			sb.append(hmap.get("SALE_PROMO_AMT"));
			sb.append(";");
			sb.append(hmap.get("RTN_AMT"));
			sb.append(";");
			sb.append(hmap.get("PAY_AMT"));
			sb.append(";");
			sb.append(hmap.get("AAA"));
			sb.append(";");
			sb.append(hmap.get("BBB"));
			sb.append(";");
			sb.append(hmap.get("CCC"));
			sb.append(";");
			sb.append(hmap.get("DDD"));
			sb.append("\r\n");
		}
		sb.append("------------------------------------------------------------------------------------");
		
		commonUtil.createTextFile(request, response, sb);
	}
	

}
