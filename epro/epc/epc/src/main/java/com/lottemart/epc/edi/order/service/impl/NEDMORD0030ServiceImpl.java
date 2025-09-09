package com.lottemart.epc.edi.order.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.order.dao.NEDMORD0030Dao;
import com.lottemart.epc.edi.order.model.NEDMORD0030VO;
import com.lottemart.epc.edi.order.service.NEDMORD0030Service;

@Service("nedmord0030Service")
public class NEDMORD0030ServiceImpl implements NEDMORD0030Service{
	
	@Autowired
	private NEDMORD0030Dao nedmord0030Dao;
	
	/**
	 * 기간정보 - > 전표상세 조회
	 */
	public List<NEDMORD0030VO> selectJunpyoDetailInfo(NEDMORD0030VO map) throws Exception{
		// TODO Auto-generated method stub
		return nedmord0030Dao.selectJunpyoDetailInfo(map );
	}
	
	/**
	 * 기간정보 - > 전표상세 txt파일 생성
	 */
	public void createTextJunpyoDetail(NEDMORD0030VO map,HttpServletRequest request, HttpServletResponse response) throws Exception{
		List<NEDMORD0030VO> list = new ArrayList();
		NEDMORD0030VO hmap = null;
		StringBuffer sb = new StringBuffer();
		String tmp = "empty";
		
		if (!StringUtils.trimToEmpty(request.getParameter("storeVal")).equals("")) {
			ArrayList	al	=	new ArrayList();
			String[] arrStore	=	request.getParameter("storeVal").split("-");
			
			for (int i = 0; i < arrStore.length; i++) {
				al.add(arrStore[i]);
			}
			
			map.setSearchStoreAl(al);
		}
		
		list = nedmord0030Dao.selectJunpyoDetailInfo(map );
		
		sb.append("■ 전표상세 발주내역");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(map.getTextData());
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			hmap = (NEDMORD0030VO)list.get(i);
			
			if(!tmp.equals(String.valueOf(hmap.getOrdSlipNo()))){
				sb.append("------------------------------------------------------------------------------------");
				sb.append("\r\n");
				sb.append("점포명;점포코드;발주일;전표번호;발주수량;발주금액;센터입하일;점입하일");
				sb.append("\r\n");
				sb.append(hmap.getStrNm());
				sb.append(";");
				sb.append(hmap.getStrCd());
				sb.append(";");
				sb.append(hmap.getOrdDy().toString().replaceAll("-", ""));
				sb.append(";");
				sb.append(hmap.getOrdSlipNo());
				sb.append(";");
				sb.append(hmap.getTotQty());
				sb.append(";");
				sb.append(hmap.getTotPrc());
				sb.append(";");
				if(hmap.getCtrArrDy() ==null){
					sb.append("");
				}else{
					sb.append(String.valueOf(hmap.getCtrArrDy()).replaceAll("-", ""));
				}
				sb.append(";");
				if(hmap.getSplyDy() ==null){
					sb.append("");
				}else{
					sb.append(String.valueOf(hmap.getSplyDy()).replaceAll("-", ""));
				}
				sb.append("\r\n");
			}
			
			if(!tmp.equals(String.valueOf(hmap.getOrdSlipNo()))){
				sb.append("------------------------------------------------------------------------------------");
				sb.append("\r\n");
				sb.append("상품코드;판매코드;상품명;입수;단위;발주수량;발주금액");
				sb.append("\r\n");
			}
				sb.append(hmap.getProdCd());
				sb.append(";");
				sb.append(hmap.getSrcmkCd());
				sb.append(";");
				sb.append(hmap.getProdNm());
				sb.append(";");
				sb.append(hmap.getOrdIpsu());
				sb.append(";");
				sb.append(hmap.getPurUnitCdNm());
				sb.append(";");
				sb.append(hmap.getOrdQty());
				sb.append(";");
				sb.append(hmap.getBuyPrc());
				sb.append("\r\n");

				tmp = String.valueOf(hmap.getOrdSlipNo());
		}
		
		commonUtil.createTextFile(request, response, sb);
	}

}
