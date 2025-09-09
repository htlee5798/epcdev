package com.lottemart.epc.edi.order.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.order.dao.NEDMORD0050Dao;
import com.lottemart.epc.edi.order.model.NEDMORD0050VO;
import com.lottemart.epc.edi.order.service.NEDMORD0050Service;

@Service("nedmord0050Service")
public class NEDMORD0050ServiceImpl implements NEDMORD0050Service{
	
	@Autowired
	private NEDMORD0050Dao nedmord0050Dao;
	
	/**
	 * 기간정보 - > PDC전표상세 조회
	 */
	public List<NEDMORD0050VO> selectJunpyoDetailInfoPDC(NEDMORD0050VO map) throws Exception{
		// TODO Auto-generated method stub
		return nedmord0050Dao.selectJunpyoDetailInfoPDC(map );
	}
	
	/**
	 * 기간정보 - > PDC전표상세  txt파일 생성
	 */
	public void createTextJunpyoDetailPDC(NEDMORD0050VO map,HttpServletRequest request, HttpServletResponse response) throws Exception{
		List<NEDMORD0050VO> list = new ArrayList();
		NEDMORD0050VO hmap = null;
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
		
		list = nedmord0050Dao.selectJunpyoDetailInfoPDC(map );
		
		sb.append("■ 전표상세(PDC) 발주내역");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(map.getTextData());
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			hmap = (NEDMORD0050VO)list.get(i);
			
			if(!tmp.equals(String.valueOf(hmap.getOrdSlipNo()))){
				sb.append("------------------------------------------------------------------------------------");
				sb.append("\r\n");
				sb.append("센터명;점포명;점포코드;발주일;전표번호;발주수량;발주금액;센터입하일;점입하일");
				sb.append("\r\n");
				sb.append(hmap.getCtnNm());
				sb.append(";");
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
				if(hmap.getCtrArrDy()==null){
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
