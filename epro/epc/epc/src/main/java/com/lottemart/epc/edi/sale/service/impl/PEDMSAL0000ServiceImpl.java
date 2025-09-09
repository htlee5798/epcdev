package com.lottemart.epc.edi.sale.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.sale.dao.PEDMSAL0000Dao;
import com.lottemart.epc.edi.sale.model.PEDMSAL0000VO;
import com.lottemart.epc.edi.sale.service.PEDMSAL0000Service;

@Service("pedmsal0000Service")
public class PEDMSAL0000ServiceImpl implements PEDMSAL0000Service{
	@Autowired
	private PEDMSAL0000Dao pedmsal0000Dao;
	
	
	
	public List<PEDMSAL0000VO> selectDayInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmsal0000Dao.selectDayInfo(map );
	}
	
	public void createTextDay(Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		List list = new ArrayList();
	
		PEDMSAL0000VO pedmsal0000vo = new PEDMSAL0000VO();
		StringBuffer sb = new StringBuffer();
		long total_qty=0;
		long total_amt=0;
		
		list = pedmsal0000Dao.selectDayInfo(map );
		
		sb.append("■ 일자별 매출내역");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(map.get("text_data"));
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("일자;매출수량;매출금액;누계매출수량;누계매출금액");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			pedmsal0000vo = (PEDMSAL0000VO)list.get(i);
			
			total_qty += Long.parseLong(String.valueOf(pedmsal0000vo.getSaleQty()));
			total_amt += Long.parseLong(String.valueOf(pedmsal0000vo.getSaleAmt()));
			
			if(pedmsal0000vo.getStkDy()==null || pedmsal0000vo.getStkDy().length() <=0){
				sb.append("");
			}else{
				sb.append(String.valueOf(pedmsal0000vo.getStkDy()).replaceAll("-", ""));
			}
			sb.append(";");
			sb.append(pedmsal0000vo.getSaleQty());
			sb.append(";");
			sb.append(pedmsal0000vo.getSaleAmt());
			sb.append(";");
			sb.append(String.valueOf(total_qty));
			sb.append(";");
			sb.append(String.valueOf(total_amt));
			sb.append("\r\n");
		}
		sb.append("------------------------------------------------------------------------------------");
		commonUtil.createTextFile(request, response, sb);
		
	}
	
	public List<PEDMSAL0000VO> selectStoreInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmsal0000Dao.selectStoreInfo(map );
	}
	
	public void createTextStore(Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		PEDMSAL0000VO pedmsal0000vo = new PEDMSAL0000VO();
		StringBuffer sb = new StringBuffer();
		long total_qty=0;
		long total_amt=0;
		
		List list = pedmsal0000Dao.selectStoreInfo(map );
		
		sb.append("■ 점포별 매출내역");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(map.get("text_data").toString());
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("점포명;점포코드;매출수량;매출금액;누계매출수량;누계매출금액");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			pedmsal0000vo = (PEDMSAL0000VO)list.get(i);
			
			total_qty += Long.parseLong(String.valueOf(pedmsal0000vo.getSaleQty()));
			total_amt += Long.parseLong(String.valueOf(pedmsal0000vo.getSaleAmt()));
			
			sb.append(pedmsal0000vo.getStrNm());
			sb.append(";");
			sb.append(pedmsal0000vo.getStrCd());
			sb.append(";");
			sb.append(pedmsal0000vo.getSaleQty());
			sb.append(";");
			sb.append(pedmsal0000vo.getSaleAmt());
			sb.append(";");
			sb.append(String.valueOf(total_qty));
			sb.append(";");
			sb.append(String.valueOf(total_amt));
			sb.append("\r\n");
		}
		sb.append("------------------------------------------------------------------------------------");
		commonUtil.createTextFile(request, response, sb);
	}
	
	public List<PEDMSAL0000VO> selectProductInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmsal0000Dao.selectProductInfo(map );
	}
	
	public void createTextProduct(Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		PEDMSAL0000VO pedmsal0000vo = new PEDMSAL0000VO();
		StringBuffer sb = new StringBuffer();
		
		List list = pedmsal0000Dao.selectProductInfo(map );
		
		sb.append("■ 상품별 매출내역");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(map.get("text_data").toString());
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("상품코드;상품명;판매코드;매출수량;매출금액");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			pedmsal0000vo = (PEDMSAL0000VO)list.get(i);
			
			sb.append(pedmsal0000vo.getProdCd());
			sb.append(";");
			sb.append(pedmsal0000vo.getProdNm());
			sb.append(";");
			sb.append(pedmsal0000vo.getSrcmkCd());
			sb.append(";");
			sb.append(pedmsal0000vo.getSaleQty());
			sb.append(";");
			sb.append(pedmsal0000vo.getSaleAmt());
			sb.append("\r\n");
		}
		sb.append("------------------------------------------------------------------------------------");
		commonUtil.createTextFile(request, response, sb);
	}
	
	public List selectProductDetailInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmsal0000Dao.selectProductDetailInfo(map );
	}
	
	public void createTextProductDetail(Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception{
	
		PEDMSAL0000VO pedmsal0000vo = new PEDMSAL0000VO();
		StringBuffer sb = new StringBuffer();
		
		List list = pedmsal0000Dao.selectProductDetailInfo(map );
		
		sb.append("■ 상품상세별 매출내역");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(map.get("text_data").toString());
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("점포명;점포코드;상품코드;상품명;판매코드;매출수량;매출금액");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			pedmsal0000vo = (PEDMSAL0000VO)list.get(i);
			
			sb.append(pedmsal0000vo.getStrNm());
			sb.append(";");
			sb.append(pedmsal0000vo.getStrCd());
			sb.append(";");
			sb.append(pedmsal0000vo.getProdCd());
			sb.append(";");
			sb.append(pedmsal0000vo.getProdNm());
			sb.append(";");
			sb.append(pedmsal0000vo.getSrcmkCd());
			sb.append(";");
			sb.append(pedmsal0000vo.getSaleQty());
			sb.append(";");
			sb.append(pedmsal0000vo.getSaleSaleAmt());
			sb.append("\r\n");
		}
		sb.append("------------------------------------------------------------------------------------");
		commonUtil.createTextFile(request, response, sb);
	}
	
}


