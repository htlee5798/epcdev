package com.lottemart.epc.edi.delivery.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.message.CMessageSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.delivery.dao.PEDMDLY0000Dao;
import com.lottemart.epc.edi.delivery.service.PEDMDLY0000Service;


@Service("pedmdly0000Service")
public class PEDMDLY0000ServiceImpl implements PEDMDLY0000Service  {
	@Autowired
	private PEDMDLY0000Dao pedmdly0000Dao;
	
	@Resource(name = "CMessageSource")
	 CMessageSource msgSource;
	
	
	
	public List selectStatusInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmdly0000Dao.selectStatusInfo(map );
	}
	
	public List selectDeliverAcceptInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmdly0000Dao.selectDeliverAcceptInfo(map );
	}
	
	public void createTextDeliverAccept(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception{
		List list = new ArrayList();
		HashMap<String, Object> hmap = new HashMap();
		StringBuffer sb = new StringBuffer();
		
		list =  pedmdly0000Dao.selectDeliverAcceptInfo(map );
		
		sb.append("■ 배달가전 접수현황");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(map.get("text_data"));
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("전표번호;점포코드;의뢰점포;상품코드;상품명;모델명;수량;접수;진행상태;판매일자;접수일자;의뢰고객;의뢰주소;의뢰전화1;의뢰전화2;요청일자;인수고객;인수주소;인수전화1;인수전화2;비고");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			hmap = (HashMap)list.get(i);
			
			sb.append(hmap.get("DELI_SLIP_NO"));
			sb.append(";");
			sb.append(hmap.get("STR_CD"));
			sb.append(";");
			sb.append(hmap.get("STR_NM"));
			sb.append(";");
			sb.append(hmap.get("PROD_CD"));
			sb.append(";");
			sb.append(hmap.get("PROD_NM"));
			sb.append(";");
			sb.append(hmap.get("MODEL_NM"));
			sb.append(";");
			sb.append(hmap.get("DELI_SALE_QTY"));
			sb.append(";");
			if("0".equals(hmap.get("ACCEPT_FG"))){
				sb.append(msgSource.getMessage("msg.delivery.noaccept"));
			}else if("1".equals(hmap.get("ACCEPT_FG"))){
				sb.append(msgSource.getMessage("msg.delivery.accept"));
			}else{
				sb.append(msgSource.getMessage("msg.delivery.cancel"));
			}
			sb.append(";");
			if("2".equals(hmap.get("CANCEL_FG"))){
				sb.append(msgSource.getMessage("msg.delivery.canceljunpyo"));
			}else{
				if("U".equals(hmap.get("CHG_FG2"))){
					sb.append(msgSource.getMessage("msg.delivery.infochange"));
				}else if("D".equals(hmap.get("CHG_FG2"))){
					sb.append(msgSource.getMessage("msg.delivery.delete"));
				}else{
					if("U".equals(hmap.get("CHG_FG"))){
						sb.append(msgSource.getMessage("msg.delivery.delichange"));
					}else if("D".equals(hmap.get("CHG_FG"))){
						sb.append(msgSource.getMessage("msg.delivery.delete"));
					}else{
						if("0".equals(hmap.get("ACCEPT_FG"))){
							sb.append(msgSource.getMessage("msg.delivery.noaccept"));
						}else{
							sb.append(msgSource.getMessage("msg.delivery.yesaccept"));
						}
					}
				}
			}
			sb.append(";");
			if(hmap.get("SALE_DY")==null){
				sb.append("");
			}else{
				sb.append(String.valueOf(hmap.get("SALE_DY")).replaceAll(" ", ""));
			}
			sb.append(";");
			if(hmap.get("ACCEPT_DY")==null){
				sb.append("");
			}else{
				sb.append(String.valueOf(hmap.get("ACCEPT_DY")).replaceAll(" ", ""));
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
			if(hmap.get("DELI_REQ_DY")==null){
				sb.append("");
			}else{
				sb.append(String.valueOf(hmap.get("DELI_REQ_DY")).replaceAll(" ", ""));
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
			sb.append(hmap.get("MAP"));
			
			sb.append("\r\n");
		}
		sb.append("------------------------------------------------------------------------------------");
		
		commonUtil.createTextFile(request, response, sb);
	}
	
	public List selectDeliverRegInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmdly0000Dao.selectDeliverRegInfo(map );
	}
	
	public void createTextDeliverReg(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception{
		List list = new ArrayList();
		HashMap<String, Object> hmap = new HashMap();
		StringBuffer sb = new StringBuffer();
		
		list =  pedmdly0000Dao.selectDeliverRegInfo(map );
		
		sb.append("■ 배달가전 완료등록현황");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(map.get("text_data"));
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("전표번호;의뢰점포;판매코드;상품명;모델명;수량;진행등록;사유;진행상태;판매일자;접수일자;의뢰고객;의뢰주소;의뢰전화1;의뢰전화2;요청일자;인수고객;인수주소;인수전화1;인수전화2;일자;특이사항");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			hmap = (HashMap)list.get(i);
			
			sb.append(hmap.get("DELI_SLIP_NO"));
			sb.append(";");
			sb.append(hmap.get("STR_NM"));
			sb.append(";");
			sb.append(hmap.get("SRCMK_CD"));
			sb.append(";");
			sb.append(hmap.get("PROD_NM"));
			sb.append(";");
			sb.append(hmap.get("MODEL_NM"));
			sb.append(";");
			sb.append(hmap.get("DELI_SALE_QTY"));
			sb.append(";");
			sb.append(hmap.get("DELI_STATUS_NM"));
			sb.append(";");
			sb.append(hmap.get("UDELI_REASON_NM"));
			sb.append(";");
			if("U".equals(hmap.get("CHG_FG2"))){
				sb.append(msgSource.getMessage("msg.delivery.infochange"));
			}else if("D".equals(hmap.get("CHG_FG2"))){
				sb.append(msgSource.getMessage("msg.delivery.delete"));
			}else{
				if("U".equals(hmap.get("CHG_FG"))){
					sb.append(msgSource.getMessage("msg.delivery.delichange"));
				}else if("D".equals(hmap.get("CHG_FG"))){
					sb.append(msgSource.getMessage("msg.delivery.delete"));
				}else{
					if("0".equals(hmap.get("ACCEPT_FG"))){
						sb.append(msgSource.getMessage("msg.delivery.noaccept"));
					}else if("1".equals(hmap.get("ACCEPT_FG"))){
						sb.append(msgSource.getMessage("msg.delivery.noaccept"));
					}else{
						sb.append(msgSource.getMessage("msg.delivery.cancel"));
					}
				}
			}
			sb.append(";");
			if(hmap.get("SALE_DY")==null){
				sb.append("");
			}else{
				sb.append(String.valueOf(hmap.get("SALE_DY")).replaceAll(" ", ""));
			}
			sb.append(";");
			if(hmap.get("ACCEPT_DY")==null){
				sb.append("");
			}else{
				sb.append(String.valueOf(hmap.get("ACCEPT_DY")).replaceAll(" ", ""));
			}
			sb.append(";");
			sb.append(hmap.get("CUST_NM"));
			sb.append(";");
			sb.append(hmap.get("CUST_ADDR"));
			sb.append(";");
			sb.append(hmap.get("CUST_TEL_NO1"));
			sb.append(";");
			sb.append(hmap.get("CUST_TEL_NO2"));
			sb.append(";");
			if(hmap.get("DELI_REQ_DY")==null){
				sb.append("");
			}else{
				sb.append(String.valueOf(hmap.get("DELI_REQ_DY")).replaceAll(" ", ""));
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
			if(hmap.get("DELI_STATUS_DY")==null){
				sb.append("");
			}else{
				sb.append(String.valueOf(hmap.get("DELI_STATUS_DY")).replaceAll(" ", ""));
			}
			sb.append(";");
			sb.append(hmap.get("MAP"));
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
	    			hMap.put("sale_prod_seq", tmpSp[4]);
    			}
    			pedmdly0000Dao.updateDeliverAcceptInfo(hMap );
    		} 
		
	}
	
	public void updateDeliverRegInfo(String obj, String obj2) throws Exception{
		// TODO Auto-generated method stub
		
		String[] tmp=obj.split("@");
		String[] tmp2=obj2.split("@");
		
		String forward_deli_status ="";
		String forward_udeli_reason ="";
		String forward_end_dy ="";
		
		HashMap<String, Object> hMap = new HashMap<String, Object>();
    	
		for (int i = 0; i < tmp.length; i++) {
			
			String[] tmpSp = tmp[i].split(";");
			String[] tmpValue = tmp2[i].split(";");
			
			for(int j=0;j<tmpSp.length;j++){
				hMap.put("sale_dy", tmpSp[0].replace("/", ""));
    			hMap.put("str_cd", tmpSp[1]);
    			hMap.put("pos_no", tmpSp[2]);
    			hMap.put("trd_no", tmpSp[3]);
    			hMap.put("sale_prod_seq", tmpSp[4]);
			}
			
			for(int j=0;j<tmpValue.length;j++){
				forward_deli_status = tmpValue[0];
				forward_udeli_reason = tmpValue[1];
				forward_end_dy = tmpValue[2];
			}
			
			hMap.put("forward_deli_status", forward_deli_status);
			hMap.put("forward_udeli", forward_udeli_reason);
			hMap.put("forward_end", forward_end_dy);
			
			pedmdly0000Dao.updateDeliverRegInfo(hMap );
	    	
		} 
		
		
	}
	
	public HashMap cancelDeliverAccept(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		
		String[] tmp = map.get("cancelJyun").toString().split("/");
		
		for(int i=0;i<tmp.length;i++){
			map.put("sale_dy_str", tmp[0]);
			map.put("str_cd_str", tmp[1]);
			map.put("pos_no_str", tmp[2]);
			map.put("trd_no_str", tmp[3]);
			map.put("sale_prod_seq_str", tmp[4]);
		}
		
		return pedmdly0000Dao.cancelDeliverAccept(map );
	}
}


