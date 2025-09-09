package com.lottemart.epc.edi.sale.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.sale.dao.NEDMSAL0030Dao;
import com.lottemart.epc.edi.sale.model.NEDMSAL0030VO;
import com.lottemart.epc.edi.sale.service.NEDMSAL0030Service;

@Service("nedmsa0030Service")
public class NEDMSAL0030ServiceImpl implements NEDMSAL0030Service{
	@Autowired
	private NEDMSAL0030Dao nedmsa0030Dao;
	
	/**
	 * 매출정보 - > 매출정보(상품별)
	 * @param NEDMSAL0030VO
	 * @return
	 */
	public List<NEDMSAL0030VO> selectProductInfo(NEDMSAL0030VO map) throws Exception{
		// TODO Auto-generated method stub
		return nedmsa0030Dao.selectProductInfo(map );
	}
	/**
	 * 매출정보 - > 매출정보(상품별)   txt파일 생성
	 * @param NEDMSAL0030VO
	 * @return
	 */
	public void createTextProduct(NEDMSAL0030VO map,HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		NEDMSAL0030VO nedmsa0030vo = new NEDMSAL0030VO();
		StringBuffer sb = new StringBuffer();
		
		if (!StringUtils.trimToEmpty(request.getParameter("storeVal")).equals("")) {
			ArrayList	al	=	new ArrayList();
			String[] arrStore	=	request.getParameter("storeVal").split("-");
			
			for (int i = 0; i < arrStore.length; i++) {
				al.add(arrStore[i]);
			}
			
			map.setSearchStoreAl(al);
		}
		
		List list = nedmsa0030Dao.selectProductInfo(map );
		
		sb.append("■ 상품별 매출내역");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(map.getTextData());
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("상품코드;상품명;판매코드;매출수량;매출금액");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			nedmsa0030vo = (NEDMSAL0030VO)list.get(i);
			
			sb.append(nedmsa0030vo.getProdCd());
			sb.append(";");
			sb.append(nedmsa0030vo.getProdNm());
			sb.append(";");
			sb.append(nedmsa0030vo.getSrcmkCd());
			sb.append(";");
			sb.append(nedmsa0030vo.getSaleQty());
			sb.append(";");
			sb.append(nedmsa0030vo.getSaleAmt());
			sb.append("\r\n");
		}
		sb.append("------------------------------------------------------------------------------------");
		commonUtil.createTextFile(request, response, sb);
	}
	
}


