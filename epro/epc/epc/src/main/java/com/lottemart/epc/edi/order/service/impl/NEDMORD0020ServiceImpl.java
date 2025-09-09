package com.lottemart.epc.edi.order.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.order.dao.NEDMORD0020Dao;
import com.lottemart.epc.edi.order.model.NEDMORD0020VO;
import com.lottemart.epc.edi.order.service.NEDMORD0020Service;

@Service("nedmord0020Service")
public class NEDMORD0020ServiceImpl implements NEDMORD0020Service{
	
	@Autowired
	private NEDMORD0020Dao nedmord0020Dao;
	
	/**
	 * 기간정보 - > 전표별 조회
	 */
	public List<NEDMORD0020VO> selectJunpyoInfo(NEDMORD0020VO map) throws Exception{
		// TODO Auto-generated method stub
		return nedmord0020Dao.selectJunpyoInfo(map );
	}
	
	/**
	 * 기간정보 - > 전표별 txt파일 생성
	 */
	public void createTextJunpyo(NEDMORD0020VO map,HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		List<NEDMORD0020VO> list = new ArrayList();
		NEDMORD0020VO hmap = null;
		StringBuffer sb = new StringBuffer();
		
		if (!StringUtils.trimToEmpty(request.getParameter("storeVal")).equals("")) {
			ArrayList	al	=	new ArrayList();
			String[] arrStore	=	request.getParameter("storeVal").split("-");
			
			for (int i = 0; i < arrStore.length; i++) {
				al.add(arrStore[i]);
			}
			
			map.setSearchStoreAl(al);
		}
		
		list = nedmord0020Dao.selectJunpyoInfo(map );
		
		sb.append("■ 전표별 발주내역");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(map.getTextData());
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("발주일자;점포;전표번호;발주구분;발주수량;발주금액;센터입하일;점입하일;조회여부");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			hmap = list.get(i);
			
			if(hmap.getOrdDy() == null){
				sb.append("");
			}else{
				sb.append(String.valueOf(hmap.getOrdDy()).replaceAll("-", ""));
			}
			sb.append(";");
			sb.append(hmap.getStrNm());
			sb.append(";");
			sb.append(hmap.getOrdSlipNo());
			sb.append(";");
			sb.append(hmap.getOrdFgNm());
			sb.append(";");
			sb.append(hmap.getTotQty());
			sb.append(";");
			sb.append(hmap.getTotPrc());
			sb.append(";");
			sb.append(String.valueOf(hmap.getCtrArrDy()).replaceAll("-", ""));
			sb.append(";");
			sb.append(String.valueOf(hmap.getSplyDy()).replaceAll("-", ""));
			sb.append(";");
			sb.append(hmap.getUserHit());
			sb.append("\r\n");
		}
		sb.append("------------------------------------------------------------------------------------");
		
		commonUtil.createTextFile(request, response, sb);
	}
	

}
