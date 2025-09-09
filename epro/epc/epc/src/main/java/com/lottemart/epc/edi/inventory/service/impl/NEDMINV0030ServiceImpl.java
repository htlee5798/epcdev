package com.lottemart.epc.edi.inventory.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.inventory.dao.NEDMINV0030Dao;
import com.lottemart.epc.edi.inventory.model.NEDMINV0030VO;
import com.lottemart.epc.edi.inventory.service.NEDMINV0030Service;

/**
 * @Class Name : NEDMINV0030ServiceImpl
 * @Description : 재고정보 센터 점출입 조회 ServiceImpl Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      		수정자          		수정내용
 *  -------    --------    ---------------------------
 * 2015.11.18	O YEUN KWON	  최초생성
 * 
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */

@Service("nedminv0030Service")
public class NEDMINV0030ServiceImpl implements NEDMINV0030Service{
	
	@Autowired
	private NEDMINV0030Dao nedminv0030Dao;
	
	
	
	public List<NEDMINV0030VO> selectCenterStoreInfo(NEDMINV0030VO nEDMINV0030VO) throws Exception{
		return nedminv0030Dao.selectCenterStoreInfo(nEDMINV0030VO );
	}
	
	public void createTextCenterStore(NEDMINV0030VO nEDMINV0030VO ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		

		NEDMINV0030VO nedminv0030vo = new NEDMINV0030VO();
		StringBuffer sb = new StringBuffer();
		
		if (!StringUtils.trimToEmpty(request.getParameter("storeVal")).equals("")) {
			ArrayList	al	=	new ArrayList();
			String[] arrStore	=	request.getParameter("storeVal").split("-");
			
			for (int i = 0; i < arrStore.length; i++) {
				al.add(arrStore[i]);
			}
			
			nEDMINV0030VO.setSrchStoreVal(al);
		}
		
		List<NEDMINV0030VO> list = nedminv0030Dao.selectCenterStoreInfo(nEDMINV0030VO);
		
		sb.append("■ 센터점출입");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(nEDMINV0030VO.getTextData());
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("센터;점포;점출수량;점출금액;점입수량;점입금액:수량합계:금액합계");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			nedminv0030vo = list.get(i);
			
			sb.append(nedminv0030vo.getCtrNm());
			sb.append(";");
			sb.append(nedminv0030vo.getStrNm());
			sb.append(";");
			sb.append(nedminv0030vo.getStroQty());
			sb.append(";");
			sb.append(nedminv0030vo.getStroSaleAmt());
			sb.append(";");
			sb.append(nedminv0030vo.getStriQty());
			sb.append(";");
			sb.append(nedminv0030vo.getStriSaleAmt());
			sb.append(";");
			sb.append(nedminv0030vo.getStrioQty());
			sb.append(";");
			sb.append(nedminv0030vo.getStrioSaleAmt());
			sb.append("\r\n");
		}
		sb.append("------------------------------------------------------------------------------------");
		commonUtil.createTextFile(request, response, sb);
	}
	
	
}

