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
import com.lottemart.epc.edi.inventory.dao.NEDMINV0020Dao;
import com.lottemart.epc.edi.inventory.model.NEDMINV0020VO;
import com.lottemart.epc.edi.inventory.service.NEDMINV0020Service;

/**
 * @Class Name : NEDMINV0020ServiceImpl
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

@Service("nedminv0020Service")
public class NEDMINV0020ServiceImpl implements NEDMINV0020Service{
	
	@Autowired
	private NEDMINV0020Dao nedminv0020Dao;
	
	
	
	public List<NEDMINV0020VO> selectProductInfo(NEDMINV0020VO nEDMINV0020VO) throws Exception{
		return nedminv0020Dao.selectProductInfo(nEDMINV0020VO );
	}
	
	public void createTextProduct(NEDMINV0020VO nEDMINV0020VO ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		

		NEDMINV0020VO nedminv0020vo = new NEDMINV0020VO();
		StringBuffer sb = new StringBuffer();
		float now_stk_qty=0;
		float now_stk_amt=0;
		
		if (!StringUtils.trimToEmpty(request.getParameter("storeVal")).equals("")) {
			ArrayList	al	=	new ArrayList();
			String[] arrStore	=	request.getParameter("storeVal").split("-");
			
			for (int i = 0; i < arrStore.length; i++) {
				al.add(arrStore[i]);
			}
			
			nEDMINV0020VO.setSrchStoreVal(al);
		}
		
		List<NEDMINV0020VO> list = nedminv0020Dao.selectProductInfo(nEDMINV0020VO);
		
		sb.append("■ 현재고(상품)");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(nEDMINV0020VO.getTextData());
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("판매코드;상품코드;상품명;");
		sb.append("기초재고(수량);매입(수량);반품(수량);점출입(수량);매출(수량);현재고(수량);");
		sb.append("기초재고(금액);매입(금액);반품(금액);점출입(금액);매가인상하(금액);매출(금액);현재고(금액);");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			nedminv0020vo = list.get(i);
			now_stk_qty = Float.parseFloat(String.valueOf(nedminv0020vo.getBookFwdQty())) 
			+	Float.parseFloat(String.valueOf(nedminv0020vo.getBuyQty())) - Float.parseFloat(String.valueOf(nedminv0020vo.getRtnQty())) 
			+  	Float.parseFloat(String.valueOf(nedminv0020vo.getStrioQty())) - Float.parseFloat(String.valueOf(nedminv0020vo.getSaleQty())) 
			+ 	Float.parseFloat(String.valueOf(nedminv0020vo.getStkAdjQty())) ;
			
			now_stk_amt = Float.parseFloat(String.valueOf(nedminv0020vo.getBookFwdSale())) 
			+ Float.parseFloat(String.valueOf(nedminv0020vo.getBuySaleAmt())) - Float.parseFloat(String.valueOf(nedminv0020vo.getRtnSaleAmt())) 
			+ Float.parseFloat(String.valueOf(nedminv0020vo.getStrioSaleAmt())) 
			+ Float.parseFloat(String.valueOf(nedminv0020vo.getSalePrcUpdownAmt())) - Float.parseFloat(String.valueOf(nedminv0020vo.getSaleSaleAmt()))
			+ Float.parseFloat(String.valueOf(nedminv0020vo.getStkAdjSaleAmt()));
			
			sb.append(nedminv0020vo.getSrcmkCd());
			sb.append(";");
			sb.append(nedminv0020vo.getProdCd());
			sb.append(";");
			sb.append(nedminv0020vo.getProdNm());
			sb.append(";");
			sb.append(nedminv0020vo.getBookFwdQty());
			sb.append(";");
			sb.append(nedminv0020vo.getBuyQty());
			sb.append(";");
			sb.append(nedminv0020vo.getRtnQty());
			sb.append(";");
			sb.append(nedminv0020vo.getStrioQty());
			sb.append(";");
			sb.append(nedminv0020vo.getSaleQty());
			sb.append(";");
			sb.append(String.valueOf(now_stk_qty));
			sb.append(";");
			sb.append(nedminv0020vo.getBookFwdSale());
			sb.append(";");
			sb.append(nedminv0020vo.getBuySaleAmt());
			sb.append(";");
			sb.append(nedminv0020vo.getRtnSaleAmt());
			sb.append(";");
			sb.append(nedminv0020vo.getStrioSaleAmt());
			sb.append(";");
			sb.append(nedminv0020vo.getSalePrcUpdownAmt());
			sb.append(";");
			sb.append(nedminv0020vo.getSaleSaleAmt());
			sb.append(";");
			//sb.append(String.valueOf(now_stk_amt));
			sb.append(String.valueOf(nedminv0020vo.getFinalAmt()));
			sb.append("\r\n");
		}
		sb.append("------------------------------------------------------------------------------------");
		commonUtil.createTextFile(request, response, sb);
	}
	
	public void createTextProductText(NEDMINV0020VO nEDMINV0020VO ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		NEDMINV0020VO nedminv0020vo = new NEDMINV0020VO();
		StringBuffer sb = new StringBuffer();
		float now_stk_qty=0;
		float now_stk_amt=0;
		
		if (!StringUtils.trimToEmpty(request.getParameter("storeVal")).equals("")) {
			ArrayList	al	=	new ArrayList();
			String[] arrStore	=	request.getParameter("storeVal").split("-");
			
			for (int i = 0; i < arrStore.length; i++) {
				al.add(arrStore[i]);
			}
			
			nEDMINV0020VO.setSrchStoreVal(al);
		}
		
		List<NEDMINV0020VO> list = nedminv0020Dao.selectProductInfoText(nEDMINV0020VO );
		
		sb.append("■ 현재고(상품) 점포별");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(nEDMINV0020VO.getTextData());
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("거래선코드;점포코드;점포명;");
		sb.append("판매코드;상품코드;상품명;");
		sb.append("기초재고(수량);매입(수량);반품(수량);점출입(수량);매출(수량);현재고(수량);");
		sb.append("기초재고(금액);매입(금액);반품(금액);점출입(금액);매가인상하(금액);매출(금액);현재고(금액);");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			nedminv0020vo = list.get(i);
			
			now_stk_qty = Float.parseFloat(String.valueOf(nedminv0020vo.getBookFwdQty())) + Float.parseFloat(String.valueOf(nedminv0020vo.getBuyQty())) - Float.parseFloat(String.valueOf(nedminv0020vo.getRtnQty())) 
					+  Float.parseFloat(String.valueOf(nedminv0020vo.getStrioQty())) - Float.parseFloat(String.valueOf(nedminv0020vo.getSaleQty())) + Float.parseFloat(String.valueOf(nedminv0020vo.getStkAdjQty())) ;
					
			now_stk_amt = Float.parseFloat(String.valueOf(nedminv0020vo.getBookFwdSale())) + Float.parseFloat(String.valueOf(nedminv0020vo.getBuySaleAmt())) - Float.parseFloat(String.valueOf(nedminv0020vo.getRtnSaleAmt())) 
			+ Float.parseFloat(String.valueOf(nedminv0020vo.getStrioSaleAmt())) + Float.parseFloat(String.valueOf(nedminv0020vo.getSalePrcUpdownAmt())) - Float.parseFloat(String.valueOf(nedminv0020vo.getSaleSaleAmt()))
			+ Float.parseFloat(String.valueOf(nedminv0020vo.getStkAdjSaleAmt()));
			
			sb.append(nedminv0020vo.getVenCd());
			sb.append(";");
			sb.append(nedminv0020vo.getStrCd());
			sb.append(";");
			sb.append(nedminv0020vo.getStrNm());
			sb.append(";");
			sb.append(nedminv0020vo.getSrcmkCd());
			sb.append(";");
			sb.append(nedminv0020vo.getProdCd());
			sb.append(";");
			sb.append(nedminv0020vo.getProdNm());
			sb.append(";");
			sb.append(nedminv0020vo.getBookFwdQty());
			sb.append(";");
			sb.append(nedminv0020vo.getBuyQty());
			sb.append(";");
			sb.append(nedminv0020vo.getRtnQty());
			sb.append(";");
			sb.append(nedminv0020vo.getStrioQty());
			sb.append(";");
			sb.append(nedminv0020vo.getSaleQty());
			sb.append(";");
			sb.append(String.valueOf(now_stk_qty));
			sb.append(";");
			sb.append(nedminv0020vo.getBookFwdSale());
			sb.append(";");
			sb.append(nedminv0020vo.getBuySaleAmt());
			sb.append(";");
			sb.append(nedminv0020vo.getRtnSaleAmt());
			sb.append(";");
			sb.append(nedminv0020vo.getStrioSaleAmt());
			sb.append(";");
			sb.append(nedminv0020vo.getSalePrcUpdownAmt());
			sb.append(";");
			sb.append(nedminv0020vo.getSaleSaleAmt());
			sb.append(";");
			//sb.append(String.valueOf(now_stk_amt));
			sb.append(String.valueOf(nedminv0020vo.getFinalAmt()));
			sb.append("\r\n");
		}
		sb.append("------------------------------------------------------------------------------------");
		commonUtil.createTextFile(request, response, sb);
	}
	
}

