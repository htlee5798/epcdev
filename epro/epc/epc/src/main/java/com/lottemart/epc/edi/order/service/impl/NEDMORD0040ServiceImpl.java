package com.lottemart.epc.edi.order.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.order.dao.NEDMORD0040Dao;
import com.lottemart.epc.edi.order.model.NEDMORD0040VO;
import com.lottemart.epc.edi.order.service.NEDMORD0040Service;

@Service("nedmord0040Service")
public class NEDMORD0040ServiceImpl implements NEDMORD0040Service{
	
	@Autowired
	private NEDMORD0040Dao nedmord0040Dao;
	
	/**
	 * 기간정보 - > 점포별 조회
	 */
	public List<NEDMORD0040VO> selectStoreInfo(NEDMORD0040VO map) throws Exception{
		// TODO Auto-generated method stub
		
		return nedmord0040Dao.selectStoreInfo(map );
	}
	
	/**
	 * 기간정보 - > 점포별txt파일 생성
	 */
	public void createTextStore(NEDMORD0040VO map,HttpServletRequest request, HttpServletResponse response) throws Exception{
		// TODO Auto-generated method stub
		
		List<NEDMORD0040VO> list = new ArrayList();
		NEDMORD0040VO hmap = null;
		StringBuffer sb = new StringBuffer();
		
		if (!StringUtils.trimToEmpty(request.getParameter("storeVal")).equals("")) {
			ArrayList	al	=	new ArrayList();
			String[] arrStore	=	request.getParameter("storeVal").split("-");
			
			for (int i = 0; i < arrStore.length; i++) {
				al.add(arrStore[i]);
			}
			
			map.setSearchStoreAl(al);
		}
		
		list = nedmord0040Dao.selectStoreInfo(map );
		
		sb.append("■ 점포별 발주내역");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(map.getTextData());
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("점포;상품코드;판매코드;상품명;규격;단위;입수;단가;수량;발주금액");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			hmap = (NEDMORD0040VO)list.get(i);
			
			sb.append(hmap.getStrNm());
			sb.append(";");
			sb.append(hmap.getProdNm());
			sb.append(";");
			sb.append(hmap.getSrcmkCd());
			sb.append(";");
			sb.append(hmap.getProdNm());
			sb.append(";");
			sb.append(hmap.getProdStd());
			sb.append(";");
			sb.append(hmap.getPurUnitCdNm());
			sb.append(";");
			sb.append(hmap.getOrdIpsu());
			sb.append(";");
			sb.append(hmap.getBuyDan());
			sb.append(";");
			sb.append(hmap.getOrdQty());
			sb.append(";");
			sb.append(hmap.getBuyPrc());
			sb.append("\r\n");
		}
		sb.append("------------------------------------------------------------------------------------");
		
		commonUtil.createTextFile(request, response, sb);
	}
	

}
