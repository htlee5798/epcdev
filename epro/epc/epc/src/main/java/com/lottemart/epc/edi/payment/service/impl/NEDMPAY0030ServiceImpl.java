package com.lottemart.epc.edi.payment.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.payment.dao.NEDMPAY0030Dao;
import com.lottemart.epc.edi.payment.model.NEDMPAY0030VO;
import com.lottemart.epc.edi.payment.service.NEDMPAY0030Service;

@Service("nedmpay0030Service")
public class NEDMPAY0030ServiceImpl implements NEDMPAY0030Service{
	
	@Autowired
	private NEDMPAY0030Dao nedmpay0030Dao;
	
	
	/**
	 * 기간별 결산정보  - > 점포별 대금결제
	 * @param NEDMPAY0030VO
	 * @return
	 */
	public List<NEDMPAY0030VO> selectPaymentStoreInfo(NEDMPAY0030VO map) throws Exception{
		// TODO Auto-generated method stub
		return nedmpay0030Dao.selectPaymentStoreInfo(map );
	}
	/**
	 * 기간별 결산정보  - > 점포별 대금결제 txt 파일 생성
	 * @param NEDMPAY0030VO
	 * @param request
	 * @param response
	 * @return
	 */
	public void createTextPaymentStore(NEDMPAY0030VO map,HttpServletRequest request, HttpServletResponse response) throws Exception{
		List<NEDMPAY0030VO> list = null;
		NEDMPAY0030VO hmap = null;
		StringBuffer sb = new StringBuffer();
		
		if (!StringUtils.trimToEmpty(request.getParameter("storeVal")).equals("")) {
			ArrayList	al	=	new ArrayList();
			String[] arrStore	=	request.getParameter("storeVal").split("-");
			
			for (int i = 0; i < arrStore.length; i++) {
				al.add(arrStore[i]);
			}
			
			map.setSearchStoreAl(al);
		}
		
		list =  nedmpay0030Dao.selectPaymentStoreInfo(map);
		
		long pay_buy_sum		 = 0;
		
		long mul_sum			 = 0;
		long usply_sum			 = 0;
		long infoAnlyFee_sum	 = 0;
		long sinsang_sum		 = 0;
		long sinsang2_sum		 = 0;
		long newProdIncentFee_sum= 0;
		long ehbtIncentFee_sum	 = 0;
		long pfrmIncentFee_sum	 = 0;
		long facilityFee_sum	 = 0;
		long onlineAdFee_sum 	 = 0;
		long movStdFee_sum		 = 0;
		long bottDedu_sum		 = 0;
		long alcoBottDedu_sum	 = 0;
		long digitalAdFee_sum	 = 0;
		long etcDedu_sum		 = 0;
		
		long total_sum			 = 0;
		long sub_buy_sum		 = 0;
		long realPayBuy 		 = 0;
		
		sb.append("■ 점포별 대금 결제 정보");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(map.getTextData());
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("지급일자;점포;지급대상액;물류비;미납패널티;정보분석료;광고판촉분담금1;광고판촉분담금2;신상품장려금;매대진열장려금;성과장려금;시설물사용료;온라인광고료;점(코드)간 이동;공병수수료공제;주류공병공제;디지털광고료;기타공제;공제내역 및 금액 계;패밀리론;실지급액");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			hmap = list.get(i);
			
			if(hmap.getPayDay()==null){
				sb.append("");
			}else{
				sb.append(String.valueOf(hmap.getPayDay()).replaceAll("-", ""));
			}
			sb.append(";");
			sb.append(hmap.getStrNm());
			sb.append(";");
			sb.append(hmap.getPayBuy());
			sb.append(";");
			
			sb.append(hmap.getMul());
			sb.append(";");
			sb.append(hmap.getUsply());
			sb.append(";");
			sb.append(hmap.getInfoAnlyFee());
			sb.append(";");
			sb.append(hmap.getSinsang());
			sb.append(";");
			sb.append(hmap.getSinsang2());
			sb.append(";");
			sb.append(hmap.getNewProdIncentFee());
			sb.append(";");
			sb.append(hmap.getEhbtIncentFee());
			sb.append(";");
			sb.append(hmap.getPfrmIncentFee());
			sb.append(";");
			sb.append(hmap.getFacilityFee());
			sb.append(";");
			sb.append(hmap.getOnlineAdFee());
			sb.append(";");
			sb.append(hmap.getMovStdFee());
			sb.append(";");
			sb.append(hmap.getBottDedu());
			sb.append(";");
			sb.append(hmap.getAlcoBottDedu());
			sb.append(";");
			sb.append(hmap.getDigitalAdFee());
			sb.append(";");
			sb.append(hmap.getEtcDedu());
			sb.append(";");
			
			sb.append(hmap.getTotal());
			sb.append(";");
			sb.append("0");
			sb.append(";");
			sb.append(hmap.getRealPayBuy());
			sb.append("\r\n");
			
			pay_buy_sum 		 += Long.parseLong(String.valueOf(hmap.getPayBuy()));
			
			mul_sum 			 += Long.parseLong(String.valueOf(hmap.getMul()));
			usply_sum 			 += Long.parseLong(String.valueOf(hmap.getUsply()));
			infoAnlyFee_sum 	 += Long.parseLong(String.valueOf(hmap.getInfoAnlyFee()));
			sinsang_sum 	  	 += Long.parseLong(String.valueOf(hmap.getSinsang()));
			sinsang2_sum		 += Long.parseLong(String.valueOf(hmap.getSinsang2()));
			newProdIncentFee_sum += Long.parseLong(String.valueOf(hmap.getNewProdIncentFee()));
			ehbtIncentFee_sum 	 += Long.parseLong(String.valueOf(hmap.getEhbtIncentFee()));
			pfrmIncentFee_sum	 += Long.parseLong(String.valueOf(hmap.getPfrmIncentFee()));
			facilityFee_sum		 += Long.parseLong(String.valueOf(hmap.getFacilityFee()));
			onlineAdFee_sum 	 += Long.parseLong(String.valueOf(hmap.getOnlineAdFee()));
			movStdFee_sum 		 += Long.parseLong(String.valueOf(hmap.getMovStdFee()));
			bottDedu_sum 		 += Long.parseLong(String.valueOf(hmap.getBottDedu()));
			alcoBottDedu_sum	 += Long.parseLong(String.valueOf(hmap.getAlcoBottDedu()));
			digitalAdFee_sum	 += Long.parseLong(String.valueOf(hmap.getDigitalAdFee()));
			etcDedu_sum 		 += Long.parseLong(String.valueOf(hmap.getEtcDedu()));
			
			total_sum 			 += Long.parseLong(String.valueOf(hmap.getTotal()));
			sub_buy_sum			 += Long.parseLong(String.valueOf(hmap.getSubBuy()));
			realPayBuy 			 += Long.parseLong(String.valueOf(hmap.getRealPayBuy()));
		}
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("합계;");
		sb.append(String.valueOf(pay_buy_sum));
		sb.append(";");
		
		sb.append(String.valueOf(mul_sum));
		sb.append(";");
		sb.append(String.valueOf(usply_sum));
		sb.append(";");
		sb.append(String.valueOf(infoAnlyFee_sum));
		sb.append(";");
		sb.append(String.valueOf(sinsang_sum));
		sb.append(";");
		sb.append(String.valueOf(sinsang2_sum));
		sb.append(";");
		sb.append(String.valueOf(newProdIncentFee_sum));
		sb.append(";");
		sb.append(String.valueOf(ehbtIncentFee_sum));
		sb.append(";");
		sb.append(String.valueOf(pfrmIncentFee_sum));
		sb.append(";");
		sb.append(String.valueOf(facilityFee_sum));
		sb.append(";");
		sb.append(String.valueOf(onlineAdFee_sum));
		sb.append(";");
		sb.append(String.valueOf(movStdFee_sum));
		sb.append(";");
		sb.append(String.valueOf(bottDedu_sum));
		sb.append(";");
		sb.append(String.valueOf(alcoBottDedu_sum));
		sb.append(";");
		sb.append(String.valueOf(digitalAdFee_sum));
		sb.append(";");
		sb.append(String.valueOf(etcDedu_sum));
		sb.append(";");
		
		sb.append(String.valueOf(total_sum));
		sb.append(";");
		sb.append(String.valueOf(realPayBuy));
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		commonUtil.createTextFile(request, response, sb);
	}
	

}

