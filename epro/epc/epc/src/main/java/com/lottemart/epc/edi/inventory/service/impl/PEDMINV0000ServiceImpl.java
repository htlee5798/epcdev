package com.lottemart.epc.edi.inventory.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.inventory.dao.PEDMINV0000Dao;
import com.lottemart.epc.edi.inventory.model.PEDMINV0000VO;
import com.lottemart.epc.edi.inventory.service.PEDMINV0000Service;

@Service("pedminv0000Service")
public class PEDMINV0000ServiceImpl implements PEDMINV0000Service{
	
	@Autowired
	private PEDMINV0000Dao pedminv0000Dao;
	
	
	
	public List<PEDMINV0000VO> selectStoreInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedminv0000Dao.selectStoreInfo(map );
	}
	
	public void createTextPeriod(Map<String,Object> map ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		

		PEDMINV0000VO pedminv0000vo = new PEDMINV0000VO();
		StringBuffer sb = new StringBuffer();
		long now_stk_qty=0;
		long now_stk_amt=0;
		
		List list = pedminv0000Dao.selectStoreInfo(map );
		
		sb.append("■ 현재고(점포)");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(map.get("text_data"));
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("점포명;기초재고(수량);매입(수량);반품(수량);점출입(수량);매출(수량);현재고(수량);");
		sb.append("기초재고(금액);매입(금액);반품(금액);점출입(금액);매가인상하(금액);매출(금액);현재고(금액)");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			pedminv0000vo = (PEDMINV0000VO)list.get(i);
			
			now_stk_qty = Long.parseLong(String.valueOf(pedminv0000vo.getBookFwdQty())) + Long.parseLong(String.valueOf(pedminv0000vo.getBuyQty())) - Long.parseLong(String.valueOf(pedminv0000vo.getRtnQty())) 
						+  Long.parseLong(String.valueOf(pedminv0000vo.getStrioQty())) - Long.parseLong(String.valueOf(pedminv0000vo.getSaleQty())) + Long.parseLong(String.valueOf(pedminv0000vo.getStkAdjQty()));
				
				
			now_stk_amt = Long.parseLong(String.valueOf(pedminv0000vo.getBookFwdSale())) + Long.parseLong(String.valueOf(pedminv0000vo.getBuySaleAmt())) - Long.parseLong(String.valueOf(pedminv0000vo.getRtnSaleAmt())) 
			+ Long.parseLong(String.valueOf(pedminv0000vo.getStrioSaleAmt())) + Long.parseLong(String.valueOf(pedminv0000vo.getSalePrcUpdownAmt())) - Long.parseLong(String.valueOf(pedminv0000vo.getSaleSaleAmt()))
			+ Long.parseLong(String.valueOf(pedminv0000vo.getStkAdjSaleAmt()));

			sb.append(pedminv0000vo.getStrNm());
			sb.append(";");
			sb.append(pedminv0000vo.getBookFwdQty());
			sb.append(";");
			sb.append(pedminv0000vo.getBuyQty());
			sb.append(";");
			sb.append(pedminv0000vo.getRtnQty());
			sb.append(";");
			sb.append(pedminv0000vo.getStrioQty());
			sb.append(";");
			sb.append(pedminv0000vo.getSaleQty());
			sb.append(";");
			sb.append(String.valueOf(now_stk_qty));
			sb.append(";");
			sb.append(pedminv0000vo.getBookFwdSale());
			sb.append(";");
			sb.append(pedminv0000vo.getBuySaleAmt());
			sb.append(";");
			sb.append(pedminv0000vo.getRtnSaleAmt());
			sb.append(";");
			sb.append(pedminv0000vo.getStrioSaleAmt());
			sb.append(";");
			sb.append(pedminv0000vo.getSalePrcUpdownAmt());
			sb.append(";");
			sb.append(pedminv0000vo.getSaleSaleAmt());
			sb.append(";");
			sb.append(String.valueOf(now_stk_amt));
			sb.append("\r\n");
		}
		sb.append("------------------------------------------------------------------------------------");
		commonUtil.createTextFile(request, response, sb);
	}
	
	public List<PEDMINV0000VO> selectProductInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedminv0000Dao.selectProductInfo(map );
	}
	
	public void createTextProduct(Map<String,Object> map ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		PEDMINV0000VO pedminv0000vo = new PEDMINV0000VO();
		StringBuffer sb = new StringBuffer();
		long now_stk_qty=0;
		long now_stk_amt=0;
		
		List list = pedminv0000Dao.selectProductInfo(map );
		
		sb.append("■ 현재고(상품)");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(map.get("text_data"));
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("판매코드;상품코드;상품명;");
		sb.append("기초재고(수량);매입(수량);반품(수량);점출입(수량);매출(수량);현재고(수량);");
		sb.append("기초재고(금액);매입(금액);반품(금액);점출입(금액);매가인상하(금액);매출(금액);현재고(금액);");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			pedminv0000vo = (PEDMINV0000VO)list.get(i);
			now_stk_qty = Long.parseLong(String.valueOf(pedminv0000vo.getBookFwdQty())) +Long.parseLong(String.valueOf(pedminv0000vo.getBuyQty())) - Long.parseLong(String.valueOf(pedminv0000vo.getRtnQty())) 
			+  Long.parseLong(String.valueOf(pedminv0000vo.getStrioQty())) - Long.parseLong(String.valueOf(pedminv0000vo.getSaleQty())) + Long.parseLong(String.valueOf(pedminv0000vo.getStkAdjQty())) ;
			
			now_stk_amt = Long.parseLong(String.valueOf(pedminv0000vo.getBookFwdSale())) + Long.parseLong(String.valueOf(pedminv0000vo.getBuySaleAmt())) - Long.parseLong(String.valueOf(pedminv0000vo.getRtnSaleAmt())) 
			+ Long.parseLong(String.valueOf(pedminv0000vo.getStrioSaleAmt())) + Long.parseLong(String.valueOf(pedminv0000vo.getSalePrcUpdownAmt())) - Long.parseLong(String.valueOf(pedminv0000vo.getSaleSaleAmt()))
			+ Long.parseLong(String.valueOf(pedminv0000vo.getStkAdjSaleAmt()));
			
			sb.append(pedminv0000vo.getSrcmkCd());
			sb.append(";");
			sb.append(pedminv0000vo.getProdCd());
			sb.append(";");
			sb.append(pedminv0000vo.getProdNm());
			sb.append(";");
			sb.append(pedminv0000vo.getBookFwdQty());
			sb.append(";");
			sb.append(pedminv0000vo.getBuyQty());
			sb.append(";");
			sb.append(pedminv0000vo.getRtnQty());
			sb.append(";");
			sb.append(pedminv0000vo.getStrioQty());
			sb.append(";");
			sb.append(pedminv0000vo.getSaleQty());
			sb.append(";");
			sb.append(String.valueOf(now_stk_qty));
			sb.append(";");
			sb.append(pedminv0000vo.getBookFwdSale());
			sb.append(";");
			sb.append(pedminv0000vo.getBuySaleAmt());
			sb.append(";");
			sb.append(pedminv0000vo.getRtnSaleAmt());
			sb.append(";");
			sb.append(pedminv0000vo.getStrioSaleAmt());
			sb.append(";");
			sb.append(pedminv0000vo.getSalePrcUpdownAmt());
			sb.append(";");
			sb.append(pedminv0000vo.getSaleSaleAmt());
			sb.append(";");
			sb.append(String.valueOf(now_stk_amt));
			sb.append("\r\n");
		}
		sb.append("------------------------------------------------------------------------------------");
		commonUtil.createTextFile(request, response, sb);
	}
	
	public void createTextProductText(Map<String,Object> map ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		PEDMINV0000VO pedminv0000vo = new PEDMINV0000VO();
		StringBuffer sb = new StringBuffer();
		long now_stk_qty=0;
		long now_stk_amt=0;
		
		List list = pedminv0000Dao.selectProductInfoText(map );
		
		sb.append("■ 현재고(상품) 점포별");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(map.get("text_data"));
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("거래선코드;점포코드;점포명;");
		sb.append("판매코드;상품코드;상품명;");
		sb.append("기초재고(수량);매입(수량);반품(수량);점출입(수량);매출(수량);현재고(수량);");
		sb.append("기초재고(금액);매입(금액);반품(금액);점출입(금액);매가인상하(금액);매출(금액);현재고(금액);");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			pedminv0000vo = (PEDMINV0000VO)list.get(i);
			
			now_stk_qty = Long.parseLong(String.valueOf(pedminv0000vo.getBookFwdQty())) +Long.parseLong(String.valueOf(pedminv0000vo.getBuyQty())) - Long.parseLong(String.valueOf(pedminv0000vo.getRtnQty())) 
					+  Long.parseLong(String.valueOf(pedminv0000vo.getStrioQty())) - Long.parseLong(String.valueOf(pedminv0000vo.getSaleQty())) + Long.parseLong(String.valueOf(pedminv0000vo.getStkAdjQty())) ;
					
			now_stk_amt = Long.parseLong(String.valueOf(pedminv0000vo.getBookFwdSale())) + Long.parseLong(String.valueOf(pedminv0000vo.getBuySaleAmt())) - Long.parseLong(String.valueOf(pedminv0000vo.getRtnSaleAmt())) 
			+ Long.parseLong(String.valueOf(pedminv0000vo.getStrioSaleAmt())) + Long.parseLong(String.valueOf(pedminv0000vo.getSalePrcUpdownAmt())) - Long.parseLong(String.valueOf(pedminv0000vo.getSaleSaleAmt()))
			+ Long.parseLong(String.valueOf(pedminv0000vo.getStkAdjSaleAmt()));
			
			sb.append(pedminv0000vo.getVenCd());
			sb.append(";");
			sb.append(pedminv0000vo.getStrCd());
			sb.append(";");
			sb.append(pedminv0000vo.getStrNm());
			sb.append(";");
			sb.append(pedminv0000vo.getSrcmkCd());
			sb.append(";");
			sb.append(pedminv0000vo.getProdCd());
			sb.append(";");
			sb.append(pedminv0000vo.getProdNm());
			sb.append(";");
			sb.append(pedminv0000vo.getBookFwdQty());
			sb.append(";");
			sb.append(pedminv0000vo.getBuyQty());
			sb.append(";");
			sb.append(pedminv0000vo.getRtnQty());
			sb.append(";");
			sb.append(pedminv0000vo.getStrioQty());
			sb.append(";");
			sb.append(pedminv0000vo.getSaleQty());
			sb.append(";");
			sb.append(String.valueOf(now_stk_qty));
			sb.append(";");
			sb.append(pedminv0000vo.getBookFwdSale());
			sb.append(";");
			sb.append(pedminv0000vo.getBuySaleAmt());
			sb.append(";");
			sb.append(pedminv0000vo.getRtnSaleAmt());
			sb.append(";");
			sb.append(pedminv0000vo.getStrioSaleAmt());
			sb.append(";");
			sb.append(pedminv0000vo.getSalePrcUpdownAmt());
			sb.append(";");
			sb.append(pedminv0000vo.getSaleSaleAmt());
			sb.append(";");
			sb.append(String.valueOf(now_stk_amt));
			sb.append("\r\n");
		}
		sb.append("------------------------------------------------------------------------------------");
		commonUtil.createTextFile(request, response, sb);
	}
	
	public List<PEDMINV0000VO> selectCenterStoreInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedminv0000Dao.selectCenterStoreInfo(map );
	}
	
	public void createTextCenterStore(Map<String,Object> map ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		PEDMINV0000VO pedminv0000vo = new PEDMINV0000VO();
		StringBuffer sb = new StringBuffer();
		
		List list = pedminv0000Dao.selectCenterStoreInfo(map );
		
		sb.append("■ 센터점출입");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(map.get("text_data"));
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("센터;점포;점출수량;점출금액;점입수량;점입금액:수량합계:금액합계");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			pedminv0000vo = (PEDMINV0000VO)list.get(i);
			
			sb.append(pedminv0000vo.getCtrNm());
			sb.append(";");
			sb.append(pedminv0000vo.getStrNm());
			sb.append(";");
			sb.append(pedminv0000vo.getStroQty());
			sb.append(";");
			sb.append(pedminv0000vo.getStroSaleAmt());
			sb.append(";");
			sb.append(pedminv0000vo.getStriQty());
			sb.append(";");
			sb.append(pedminv0000vo.getStriSaleAmt());
			sb.append(";");
			sb.append(pedminv0000vo.getStrioQty());
			sb.append(";");
			sb.append(pedminv0000vo.getStrioSaleAmt());
			sb.append("\r\n");
			
			
			
		}
		sb.append("------------------------------------------------------------------------------------");
		commonUtil.createTextFile(request, response, sb);
	}
	
	public List<PEDMINV0000VO> selectCenterStoreDetailInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedminv0000Dao.selectCenterStoreDetailInfo(map );
	}
	
	public void createTextCenterStoreDetail(Map<String,Object> map ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		PEDMINV0000VO pedminv0000vo = new PEDMINV0000VO();
		StringBuffer sb = new StringBuffer();
		
		List list = pedminv0000Dao.selectCenterStoreDetailInfo(map );
		
		sb.append("■ 센터점출입상세");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(map.get("text_data"));
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("센터;점포;점출수량;점출금액;점입수량;점입금액;수량합계;금액합계");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			pedminv0000vo = (PEDMINV0000VO)list.get(i);
			
			sb.append(pedminv0000vo.getCtrNm());
			sb.append(";");
			sb.append(pedminv0000vo.getStrNm());
			sb.append(";");
			sb.append(pedminv0000vo.getStroQty());
			sb.append(";");
			sb.append(pedminv0000vo.getStroSaleAmt());
			sb.append(";");
			sb.append(pedminv0000vo.getStrioQty());
			sb.append(";");
			sb.append(pedminv0000vo.getStriSaleAmt());
			sb.append(";");
			sb.append(pedminv0000vo.getStrioQty());
			sb.append(";");
			sb.append(pedminv0000vo.getStrioSaleAmt());
			sb.append("\r\n");
		}
		sb.append("------------------------------------------------------------------------------------");
		commonUtil.createTextFile(request, response, sb);
	}


}

