package com.lottemart.epc.edi.payment.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.payment.dao.NEDMPAY0020Dao;
import com.lottemart.epc.edi.payment.model.NEDMPAY0020VO;
import com.lottemart.epc.edi.payment.service.NEDMPAY0020Service;

@Service("nedmpay0020Service")
public class NEDMPAY0020ServiceImpl implements NEDMPAY0020Service{
	
	@Autowired
	private NEDMPAY0020Dao nedmpay0020Dao;
	
	
	/**
	 * 기간별 결산정보  - > 대금결제정보 
	 * @param NEDMPAY0020VO
	 * @return
	 */
	public List<NEDMPAY0020VO> selectPaymentDayInfo(NEDMPAY0020VO map) throws Exception{
		// TODO Auto-generated method stub
		return nedmpay0020Dao.selectPaymentDayInfo(map );
	}
	/**
	 * 기간별 결산정보  - > 대금결제정보 txt 파일 생성
	 * @param NEDMPAY0020VO
	 * @param request
	 * @param response
	 * @return
	 */
	public void createTextPaymentDay(NEDMPAY0020VO map,HttpServletRequest request, HttpServletResponse response) throws Exception{
		List<NEDMPAY0020VO> list = new ArrayList();
		NEDMPAY0020VO hmap = null;
		StringBuffer sb = new StringBuffer();
		
		list =  nedmpay0020Dao.selectPaymentDayInfo(map );
		
		long pay_buy_sum		 =0;
		
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
		long realPayBuy			 = 0;
		
		sb.append("■ 대금결제정보");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(map.getTextData());
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("지급일자;지급대상액;물류비;미납패널티;정보분석료;광고판촉분담금1;광고판촉분담금2;신상품장려금;매대진열장려금;성과장려금;시설물사용료;온라인광고료;점(코드)간 이동;공병수수료공제;주류공병공제;디지털광고료;기타공제;공제내역 및 금액 계;패밀리론;실지급액");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			hmap = list.get(i);
			
			if(hmap.getPayDay()==null){
				sb.append("");
			}else{
				sb.append(String.valueOf(hmap.getPayDay()).replaceAll("-", ""));
			}
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
			
			total_sum			 += Long.parseLong(String.valueOf(hmap.getTotal()));
			sub_buy_sum 		 += Long.parseLong(String.valueOf(hmap.getSubBuy()));
			realPayBuy			 += Long.parseLong(String.valueOf(hmap.getRealPayBuy()));
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

