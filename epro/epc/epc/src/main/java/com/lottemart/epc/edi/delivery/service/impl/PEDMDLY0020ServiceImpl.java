package com.lottemart.epc.edi.delivery.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.delivery.dao.PEDMDLY0020Dao;
import com.lottemart.epc.edi.delivery.service.PEDMDLY0020Service;

@Service("pedmdly0020Service")
public class PEDMDLY0020ServiceImpl implements PEDMDLY0020Service{
	@Autowired
	private PEDMDLY0020Dao pedmdly0020Dao;
	
	public List selectStatusInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmdly0020Dao.selectStatusInfo(map );
	}
	
	public List selectDeliverAcceptInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmdly0020Dao.selectDeliverAcceptInfo(map );
	}
	
	public void createTextDeliverAccept(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception{
		List list = new ArrayList();
		HashMap<String, Object> hmap = new HashMap();
		StringBuffer sb = new StringBuffer();
		list =  pedmdly0020Dao.selectDeliverAcceptInfo(map );
		
		sb.append("■ 배달TOY 접수현황");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(map.get("text_data"));
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("전표번호;점포명;판매코드;상품명;희망배송일;수량;접수;판매일자;의뢰고객명;의뢰주소;의뢰전화1;의뢰전화2;접수일자;인수고객;인수주소;인수전화1;인수전화2");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			hmap = (HashMap)list.get(i);
			
			sb.append(hmap.get("SLIP_NO"));
			sb.append(";");
			sb.append(hmap.get("STR_NM"));
			sb.append(";");
			sb.append(hmap.get("SRCMK_CD"));
			sb.append(";");
			sb.append(hmap.get("PROD_NM"));
			sb.append(";");
			sb.append(hmap.get("PROM_DY"));
			sb.append(";");
			sb.append(hmap.get("QTY"));
			sb.append(";");
			sb.append(" ");
			sb.append(";");
			if(hmap.get("SALE_DY")==null){
				sb.append("");
			}else{
				sb.append(String.valueOf(hmap.get("SALE_DY")).replaceAll(" ", ""));
			}
			sb.append(";");
			sb.append(hmap.get("CUST_NM"));
			sb.append(";");
			sb.append(hmap.get("CUST_ADD"));
			sb.append(";");
			sb.append(hmap.get("CUST_TEL_NO1"));
			sb.append(";");
			sb.append(hmap.get("CUST_TEL_NO2"));
			sb.append(";");
			if(hmap.get("ACCEPT_DY")==null){
				sb.append("");
			}else{
				sb.append(String.valueOf(hmap.get("ACCEPT_DY")).replaceAll(" ", ""));
			}
			sb.append(";");
			sb.append(hmap.get("RECV_NM"));
			sb.append(";");
			sb.append(hmap.get("RECV_ADDR"));
			sb.append(";");
			sb.append(hmap.get("RECV_TEL_NO1"));
			sb.append(";");
			sb.append(hmap.get("RECV_TEL_NO2"));
			sb.append("\r\n");
		}
		sb.append("------------------------------------------------------------------------------------");
		
		commonUtil.createTextFile(request, response, sb);
	}
	
	public List selectDeliverRegInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmdly0020Dao.selectDeliverRegInfo(map );
	}
	
	public void createTextDeliverReg(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception{
		List list = new ArrayList();
		HashMap<String, Object> hmap = new HashMap();
		StringBuffer sb = new StringBuffer();
		
		list =  pedmdly0020Dao.selectDeliverRegInfo(map );
		
		sb.append("■ 배달TOY 완료등록현황");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(map.get("text_data"));
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("전표번호;점포명;판매코드;상품명;희망배송일;수량;진행등록;사유;판매일자;의뢰고객명;의뢰주소;의뢰전화1;의뢰전화2;접수일자;인수고객;인수주소;인수전화1;인수전화2;일자;특이사항");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			hmap = (HashMap)list.get(i);
			
			sb.append(hmap.get("SLIP_NO"));
			sb.append(";");
			sb.append(hmap.get("STR_NM"));
			sb.append(";");
			sb.append(hmap.get("SRCMK_CD"));
			sb.append(";");
			sb.append(hmap.get("PROD_NM"));
			sb.append(";");
			sb.append(hmap.get("PROM_DY"));
			sb.append(";");
			sb.append(hmap.get("QTY"));
			sb.append(";");
			if(hmap.get("ACCEPT_FG")==null){
				sb.append("");
			}else{
				sb.append(String.valueOf(hmap.get("ACCEPT_FG")).replaceAll(" ", ""));
			}
			sb.append(";");
			if(hmap.get("UDELI_REASON_FG")==null){
				sb.append("");
			}else{
				sb.append(String.valueOf(hmap.get("UDELI_REASON_FG")).replaceAll(" ", ""));
			}
			sb.append(";");
			if(hmap.get("SALE_DY")==null){
				sb.append("");
			}else{
				sb.append(String.valueOf(hmap.get("SALE_DY")).replaceAll(" ", ""));
			}
			sb.append(";");
			sb.append(hmap.get("CUST_NM"));
			sb.append(";");
			sb.append(hmap.get("CUST_ADD"));
			sb.append(";");
			sb.append(hmap.get("CUST_TEL_NO1"));
			sb.append(";");
			sb.append(hmap.get("CUST_TEL_NO2"));
			sb.append(";");
			if(hmap.get("ACCEPT_DY")==null){
				sb.append("");
			}else{
				sb.append(String.valueOf(hmap.get("ACCEPT_DY")).replaceAll(" ", ""));
			}
			sb.append(";");
			sb.append(hmap.get("RECV_NM"));
			sb.append(";");
			sb.append(hmap.get("RECV_ADDR"));
			sb.append(";");
			sb.append(hmap.get("RECV_TEL_NO1"));
			sb.append(";");
			sb.append(hmap.get("RECV_TEL_NO2"));
			sb.append(";");
			if(hmap.get("DELI_END_DY")==null){
				sb.append("");
			}else{
				sb.append(String.valueOf(hmap.get("DELI_END_DY")).replaceAll(" ", ""));
			}
			sb.append(";");
			sb.append(hmap.get("REMARK"));
			sb.append("\r\n");
		}
		sb.append("------------------------------------------------------------------------------------");
		
		commonUtil.createTextFile(request, response, sb);
	}
	
	public void updateDeliverAcceptInfo(String obj) throws Exception{
		// TODO Auto-generated method stub
		
		String[] tmp=obj.split("@");
		
		HashMap<String, Object> hMap = new HashMap<String, Object>();
    	
		for (int i = 0; i < tmp.length; i++) {
			
			String[] tmpSp = tmp[i].split(";");
			  
			for(int j=0;j<tmpSp.length;j++){
				hMap.put("sale_dy", tmpSp[0]);
				hMap.put("str_cd", tmpSp[1]);
				hMap.put("pos_no", tmpSp[2]);
				hMap.put("trd_no", tmpSp[3]);
				hMap.put("srcmk_cd", tmpSp[4]);
				hMap.put("recv_seq", tmpSp[5]);
			}
	    	
			pedmdly0020Dao.updateDeliverAcceptInfo(hMap );
		} 
		
	}
	
	public void updateDeliverRegInfo(String obj, String obj2) throws Exception{
		// TODO Auto-generated method stub
		
		String[] tmp=obj.split("@");
		String[] tmp2=obj2.split("@");
		
		String forward_accept_fg ="";
		String forward_udeli_reason ="";
		String forward_end_dy ="";
		
		HashMap<String, Object> hMap = new HashMap<String, Object>();
    	
		for (int i = 0; i < tmp.length; i++) {
			
			String[] tmpSp = tmp[i].split(";");
			String[] tmpValue = tmp2[i].split(";");
			
			for(int j=0;j<tmpSp.length;j++){
				hMap.put("sale_dy", tmpSp[0]);
				hMap.put("str_cd", tmpSp[1]);
				hMap.put("pos_no", tmpSp[2]);
				hMap.put("trd_no", tmpSp[3]);
				hMap.put("srcmk_cd", tmpSp[4]);
				hMap.put("recv_seq", tmpSp[5]);
			}
			
			for(int j=0;j<tmpValue.length;j++){
				forward_accept_fg = tmpValue[0];
				forward_udeli_reason = tmpValue[1];
				forward_end_dy = tmpValue[2];
			}
			
			hMap.put("forward_accept", forward_accept_fg);
			hMap.put("forward_udeli", forward_udeli_reason);
			hMap.put("forward_end", forward_end_dy);
	    	
			pedmdly0020Dao.updateDeliverRegInfo(hMap );
		} 
		
	}
	
	
}


