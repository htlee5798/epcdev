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
import com.lottemart.epc.edi.sale.dao.NEDMSAL0010Dao;
import com.lottemart.epc.edi.sale.model.NEDMSAL0010VO;
import com.lottemart.epc.edi.sale.model.PEDMSAL0000VO;
import com.lottemart.epc.edi.sale.service.NEDMSAL0010Service;

@Service("nedmsa0010Service")
public class NEDMSAL0010ServiceImpl implements NEDMSAL0010Service{
	@Autowired
	private NEDMSAL0010Dao nedmsa00l0Dao;
	
	
	/**
	 * 매입정보 - > //매출정보(일자별) 조회
	 * @param NEDMSAL0010VO
	 * @return
	 */
	public List<NEDMSAL0010VO> selectDayInfo(NEDMSAL0010VO map) throws Exception{
		// TODO Auto-generated method stub
		return nedmsa00l0Dao.selectDayInfo(map );
	}
	@Override
	public int selectDayInfoCntSum(NEDMSAL0010VO map) throws Exception{
		// TODO Auto-generated method stub
		return nedmsa00l0Dao.selectDayInfoCntSum(map);
	}
	
	
	/**
	 * 매입정보 - > //매출정보(일자별) txt파일 생성
	 * @param NEDMSAL0010VO
	 * @return
	 */
	public void createTextDay(NEDMSAL0010VO map,HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		List list = new ArrayList();
	
		NEDMSAL0010VO nedmsa0010vo = new NEDMSAL0010VO();
		StringBuffer sb = new StringBuffer();
		long total_qty=0;
		long total_amt=0;
		
		if (!StringUtils.trimToEmpty(request.getParameter("storeVal")).equals("")) {
			ArrayList	al	=	new ArrayList();
			String[] arrStore	=	request.getParameter("storeVal").split("-");
			
			for (int i = 0; i < arrStore.length; i++) {
				al.add(arrStore[i]);
			}
			
			map.setSearchStoreAl(al);
		}
		
		list = nedmsa00l0Dao.selectDayInfo(map );
		
		sb.append("■ 일자별 매출내역");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(map.getTextData());
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("일자;매출수량;매출금액;누계매출수량;누계매출금액");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			nedmsa0010vo = (NEDMSAL0010VO)list.get(i);
			
			total_qty += Long.parseLong(String.valueOf(nedmsa0010vo.getSaleQty()));
			total_amt += Long.parseLong(String.valueOf(nedmsa0010vo.getSaleAmt()));
			
			if(nedmsa0010vo.getStkDy()==null || nedmsa0010vo.getStkDy().length() <=0){
				sb.append("");
			}else{
				sb.append(String.valueOf(nedmsa0010vo.getStkDy()).replaceAll("-", ""));
			}
			sb.append(";");
			sb.append(nedmsa0010vo.getSaleQty());
			sb.append(";");
			sb.append(nedmsa0010vo.getSaleAmt());
			sb.append(";");
			sb.append(String.valueOf(total_qty));
			sb.append(";");
			sb.append(String.valueOf(total_amt));
			sb.append("\r\n");
		}
		sb.append("------------------------------------------------------------------------------------");
		commonUtil.createTextFile(request, response, sb);
		
	}
	
	
}


