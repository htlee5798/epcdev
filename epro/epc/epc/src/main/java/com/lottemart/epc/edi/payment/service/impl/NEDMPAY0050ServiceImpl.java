package com.lottemart.epc.edi.payment.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.payment.dao.NEDMPAY0050Dao;
import com.lottemart.epc.edi.payment.model.NEDMPAY0050VO;
import com.lottemart.epc.edi.payment.service.NEDMPAY0050Service;

@Service("nedmpay0050Service")
public class NEDMPAY0050ServiceImpl implements NEDMPAY0050Service{
	
	@Autowired
	private NEDMPAY0050Dao nedmpay0050Dao;
	
	
	public List<NEDMPAY0050VO> selectLogiAmtInfo(NEDMPAY0050VO map) throws Exception{
		// TODO Auto-generated method stub
		return nedmpay0050Dao.selectLogiAmtInfo(map );
	}
	
	public void createTextLogiAmt(NEDMPAY0050VO map,HttpServletRequest request, HttpServletResponse response) throws Exception{
		List<NEDMPAY0050VO> list = null;
		NEDMPAY0050VO hmap = null;
		StringBuffer sb = new StringBuffer();
		
		if (!StringUtils.trimToEmpty(request.getParameter("storeVal")).equals("")) {
			ArrayList	al	=	new ArrayList();
			String[] arrStore	=	request.getParameter("storeVal").split("-");
			
			for (int i = 0; i < arrStore.length; i++) {
				al.add(arrStore[i]);
			}
			
			map.setSearchStoreAl(al);
		}
		
		list =  nedmpay0050Dao.selectLogiAmtInfo(map );
		
		long str_sply_amt_sum = 0;
		long sub_amt_sum = 0;
		long vat_sum = 0;
		long sub_sum_sum = 0;
		
		sb.append("■ 물류비 정보");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(map.getTextData());
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("월;점포;물류통과금액;공급가;세액;합계;물류요율");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			hmap = (NEDMPAY0050VO)list.get(i);
			
			sb.append(hmap.getSubMm());
			sb.append(";");
			sb.append(hmap.getStrNm());
			sb.append(";");
			sb.append(hmap.getStrSplyAmt());
			sb.append(";");
			sb.append(hmap.getSubAmt());
			sb.append(";");
			sb.append(hmap.getVat());
			sb.append(";");
			sb.append(hmap.getSubSum());
			sb.append(";");
			sb.append(hmap.getRate());
			sb.append("\r\n");
			
			str_sply_amt_sum += Long.parseLong(String.valueOf(hmap.getStrSplyAmt()));
			sub_amt_sum += Long.parseLong(String.valueOf(hmap.getSubAmt()));
			vat_sum += Long.parseLong(String.valueOf(hmap.getVat()));
			sub_sum_sum += Long.parseLong(String.valueOf(hmap.getSubSum()));
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

}

