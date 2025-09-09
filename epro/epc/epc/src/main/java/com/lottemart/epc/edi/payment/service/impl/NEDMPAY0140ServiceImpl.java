package com.lottemart.epc.edi.payment.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.payment.dao.NEDMPAY0140Dao;
import com.lottemart.epc.edi.payment.model.NEDMPAY0140VO;
import com.lottemart.epc.edi.payment.service.NEDMPAY0140Service;

@Service("nedmpay0140Service")
public class NEDMPAY0140ServiceImpl implements NEDMPAY0140Service{
	
	@Autowired
	private NEDMPAY0140Dao nedmpay0140Dao;
	/**
	 * 거래실적조회  - > 패밀리론 txt파일생성
	 * @param NEDMPAY0140VO
	 * @return
	 */
	public List<NEDMPAY0140VO> selectFamilyLoan(NEDMPAY0140VO map) throws Exception{
		// TODO Auto-generated method stub
		return nedmpay0140Dao.selectFamilyLoan(map );
	}
	/**
	 * 거래실적조회  - > 패밀리론 txt파일생성
	 * @param NEDMPAY0140VO
	 * @param request
	 * @param response
	 * @return
	 */
	public void createTextFamilyLoan(NEDMPAY0140VO map ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		List<NEDMPAY0140VO> list = null;
		NEDMPAY0140VO hmap = null;
		StringBuffer sb = new StringBuffer();
		
		
		list =  nedmpay0140Dao.selectFamilyLoan(map );
		
		sb.append("■ 패밀리론");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(map.getTextData());
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("납품번호;정기지급일;은행통보금액 ;대출가능액;매입/매출금액;물류비	;장려금;공제예상액;당일전송대상액;통보금액;대출가능액;통보금액;대출가능액");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			hmap = list.get(i);
			
			sb.append(hmap.getSlipNo());
			sb.append(";");
			sb.append(String.valueOf(hmap.getPayDy()).replaceAll("-", ""));
			sb.append(";");
			sb.append(hmap.getSendAmt());
			sb.append(";");
			sb.append(hmap.getAbleAmt());
			sb.append(";");
			sb.append(hmap.getSplyAmt());
			sb.append(";");
			sb.append(hmap.getLogiAmt());
			
			sb.append(";");
			sb.append(hmap.getSalePromoAmt());
			sb.append(";");
			sb.append(hmap.getRtnAmt());
			sb.append(";");
			sb.append(hmap.getPayAmt());
			sb.append(";");
			sb.append(hmap.getAaa());
			sb.append(";");
			sb.append(hmap.getBbb());
			sb.append(";");
			sb.append(hmap.getCcc());
			sb.append(";");
			sb.append(hmap.getDdd());
			sb.append("\r\n");
		}
		sb.append("------------------------------------------------------------------------------------");
		
		commonUtil.createTextFile(request, response, sb);
	}
	

}
