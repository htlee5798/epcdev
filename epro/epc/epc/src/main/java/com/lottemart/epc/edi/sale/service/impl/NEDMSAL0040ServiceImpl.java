package com.lottemart.epc.edi.sale.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.sale.dao.NEDMSAL0040Dao;
import com.lottemart.epc.edi.sale.model.NEDMSAL0040VO;
import com.lottemart.epc.edi.sale.model.PEDMSAL0000VO;
import com.lottemart.epc.edi.sale.service.NEDMSAL0040Service;

@Service("nedmsa0040Service")
public class NEDMSAL0040ServiceImpl implements NEDMSAL0040Service{
	@Autowired
	private NEDMSAL0040Dao nedmsa0040Dao;
	
	
	/**
	 * 매출정보 - > 매출정보(상품상세별)
	 * @param NEDMSAL0040VO
	 * @return
	 */
	public List<NEDMSAL0040VO> selectProductDetailInfo(NEDMSAL0040VO map) throws Exception{
		// TODO Auto-generated method stub
		return nedmsa0040Dao.selectProductDetailInfo(map );
	}
	/**
	 * 매출정보 - > 매출정보(상품상세별)  txt파일 생성
	 * @param NEDMSAL0040VO
	 * @param response
	 * @param request
	 * @return
	 */
	public void createTextProductDetail(NEDMSAL0040VO map,HttpServletRequest request, HttpServletResponse response) throws Exception{
	
		NEDMSAL0040VO nedmsa0040vo = new NEDMSAL0040VO();
		StringBuffer sb = new StringBuffer();
		
		if (!StringUtils.trimToEmpty(request.getParameter("storeVal")).equals("")) {
			ArrayList	al	=	new ArrayList();
			String[] arrStore	=	request.getParameter("storeVal").split("-");
			
			for (int i = 0; i < arrStore.length; i++) {
				al.add(arrStore[i]);
			}
			
			map.setSearchStoreAl(al);
		}
		
		List list = nedmsa0040Dao.selectProductDetailInfo(map );
		
		sb.append("■ 상품상세별 매출내역");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(map.getTextData());
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("점포명;점포코드;상품코드;상품명;판매코드;매출수량;매출금액");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			nedmsa0040vo = (NEDMSAL0040VO)list.get(i);
			
			sb.append(nedmsa0040vo.getStrNm());
			sb.append(";");
			sb.append(nedmsa0040vo.getStrCd());
			sb.append(";");
			sb.append(nedmsa0040vo.getProdCd());
			sb.append(";");
			sb.append(nedmsa0040vo.getProdNm());
			sb.append(";");
			sb.append(nedmsa0040vo.getSrcmkCd());
			sb.append(";");
			sb.append(nedmsa0040vo.getSaleQty());
			sb.append(";");
			sb.append(nedmsa0040vo.getSaleSaleAmt());
			sb.append("\r\n");
		}
		sb.append("------------------------------------------------------------------------------------");
		commonUtil.createTextFile(request, response, sb);
	}
	
}


