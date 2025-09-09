package com.lottemart.epc.edi.inventory.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.inventory.dao.NEDMINV0010Dao;
import com.lottemart.epc.edi.inventory.model.NEDMINV0010VO;
import com.lottemart.epc.edi.inventory.service.NEDMINV0010Service;

/**
 * @Class Name : NEDMINV0010ServiceImpl
 * @Description : 재고정보 현재고(점포) 조회 ServiceImpl Class
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

@Service("nedminv0010Service")
public class NEDMINV0010ServiceImpl implements NEDMINV0010Service{
	
	@Autowired
	private NEDMINV0010Dao nedminv0010Dao;
	
	
	
	public List<NEDMINV0010VO> selectStoreInfo(NEDMINV0010VO nEDMINV0010VO) throws Exception{
		return nedminv0010Dao.selectStoreInfo(nEDMINV0010VO );
	}
	
	public void createTextPeriod(NEDMINV0010VO nEDMINV0010VO ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		

		NEDMINV0010VO nedminv0010vo = new NEDMINV0010VO();
		StringBuffer sb = new StringBuffer();
		float now_stk_qty=0;
		float now_stk_amt=0;
		
		if (!StringUtils.trimToEmpty(request.getParameter("storeVal")).equals("")) {
			ArrayList	al	=	new ArrayList();
			String[] arrStore	=	request.getParameter("storeVal").split("-");
			
			for (int i = 0; i < arrStore.length; i++) {
				al.add(arrStore[i]);
			}
			
			nEDMINV0010VO.setSrchStoreVal(al);
		}
		
		List<NEDMINV0010VO> list = nedminv0010Dao.selectStoreInfo(nEDMINV0010VO );
		
		sb.append("■ 현재고(점포)");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(nEDMINV0010VO.getTextData());
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("점포명;기초재고(수량);매입(수량);반품(수량);점출입(수량);매출(수량);현재고(수량);");
		sb.append("기초재고(금액);매입(금액);반품(금액);점출입(금액);매가인상하(금액);매출(금액);현재고(금액)");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			nedminv0010vo = list.get(i);
			
			now_stk_qty = Float.parseFloat(String.valueOf(nedminv0010vo.getBookFwdQty())) + Float.parseFloat(String.valueOf(nedminv0010vo.getBuyQty())) - Float.parseFloat(String.valueOf(nedminv0010vo.getRtnQty())) 
						+  Float.parseFloat(String.valueOf(nedminv0010vo.getStrioQty())) - Float.parseFloat(String.valueOf(nedminv0010vo.getSaleQty())) + Float.parseFloat(String.valueOf(nedminv0010vo.getStkAdjQty()));
				
				
			now_stk_amt = Float.parseFloat(String.valueOf(nedminv0010vo.getBookFwdSale())) + Float.parseFloat(String.valueOf(nedminv0010vo.getBuySaleAmt())) - Float.parseFloat(String.valueOf(nedminv0010vo.getRtnSaleAmt())) 
			+ Float.parseFloat(String.valueOf(nedminv0010vo.getStrioSaleAmt())) + Float.parseFloat(String.valueOf(nedminv0010vo.getSalePrcUpdownAmt())) - Float.parseFloat(String.valueOf(nedminv0010vo.getSaleSaleAmt()))
			+ Float.parseFloat(String.valueOf(nedminv0010vo.getStkAdjSaleAmt()));

			sb.append(nedminv0010vo.getStrNm());
			sb.append(";");
			sb.append(nedminv0010vo.getBookFwdQty());
			sb.append(";");
			sb.append(nedminv0010vo.getBuyQty());
			sb.append(";");
			sb.append(nedminv0010vo.getRtnQty());
			sb.append(";");
			sb.append(nedminv0010vo.getStrioQty());
			sb.append(";");
			sb.append(nedminv0010vo.getSaleQty());
			sb.append(";");
			sb.append(String.valueOf(now_stk_qty));
			sb.append(";");
			sb.append(nedminv0010vo.getBookFwdSale());
			sb.append(";");
			sb.append(nedminv0010vo.getBuySaleAmt());
			sb.append(";");
			sb.append(nedminv0010vo.getRtnSaleAmt());
			sb.append(";");
			sb.append(nedminv0010vo.getStrioSaleAmt());
			sb.append(";");
			sb.append(nedminv0010vo.getSalePrcUpdownAmt());
			sb.append(";");
			sb.append(nedminv0010vo.getSaleSaleAmt());
			sb.append(";");
			//sb.append(String.valueOf(now_stk_amt));
			sb.append(nedminv0010vo.getFinalAmt());
			sb.append("\r\n");
		}
		sb.append("------------------------------------------------------------------------------------");
		commonUtil.createTextFile(request, response, sb);
	}
	
}

