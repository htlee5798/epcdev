package com.lottemart.epc.edi.inventory.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.inventory.dao.NEDMINV0040Dao;
import com.lottemart.epc.edi.inventory.model.NEDMINV0040VO;
import com.lottemart.epc.edi.inventory.service.NEDMINV0040Service;

/**
 * @Class Name : NEDMINV0040ServiceImpl
 * @Description : 재고정보 센터 점출입 상세 조회 ServiceImpl Class
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

@Service("nedminv0040Service")
public class NEDMINV0040ServiceImpl implements NEDMINV0040Service{
	
	@Autowired
	private NEDMINV0040Dao nedminv0040Dao;
	
	
	
	public List<NEDMINV0040VO> selectCenterStoreDetailInfo(NEDMINV0040VO nEDMINV0040VO) throws Exception{
		return nedminv0040Dao.selectCenterStoreDetailInfo(nEDMINV0040VO );
	}
	
	public void createTextCenterStoreDetail(NEDMINV0040VO nEDMINV0040VO ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		

		NEDMINV0040VO nedminv0040vo = new NEDMINV0040VO();
		StringBuffer sb = new StringBuffer();
		
		if (!StringUtils.trimToEmpty(request.getParameter("storeVal")).equals("")) {
			ArrayList	al	=	new ArrayList();
			String[] arrStore	=	request.getParameter("storeVal").split("-");
			
			for (int i = 0; i < arrStore.length; i++) {
				al.add(arrStore[i]);
			}
			
			nEDMINV0040VO.setSrchStoreVal(al);
		}
		
		List<NEDMINV0040VO> list = nedminv0040Dao.selectCenterStoreDetailInfo(nEDMINV0040VO);
		
		sb.append("■ 센터점출입상세");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(nEDMINV0040VO.getTextData());
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("센터;점포;점출수량;점출금액;점입수량;점입금액:수량합계:금액합계");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			nedminv0040vo = list.get(i);
			
			sb.append(nedminv0040vo.getCtrNm());
			sb.append(";");
			sb.append(nedminv0040vo.getStrNm());
			sb.append(";");
			sb.append(nedminv0040vo.getStroQty());
			sb.append(";");
			sb.append(nedminv0040vo.getStroSaleAmt());
			sb.append(";");
			sb.append(nedminv0040vo.getStriQty());
			sb.append(";");
			sb.append(nedminv0040vo.getStriSaleAmt());
			sb.append(";");
			sb.append(nedminv0040vo.getStrioQty());
			sb.append(";");
			sb.append(nedminv0040vo.getStrioSaleAmt());
			sb.append("\r\n");
		}
		sb.append("------------------------------------------------------------------------------------");
		commonUtil.createTextFile(request, response, sb);
	}
	
	
}

