package com.lottemart.epc.edi.order.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.order.dao.PEDMORD0000Dao;
import com.lottemart.epc.edi.order.service.PEDMORD0000Service;

@Service("pedmord0000Service")
public class PEDMORD0000ServiceImpl implements PEDMORD0000Service{
	
	@Autowired
	private PEDMORD0000Dao pedmord0000Dao;
	
	public List selectPeriodInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		
		List list= new ArrayList();
		list = pedmord0000Dao.selectPeriodInfo(map );
		
		return list;
	}
	
	public void createTextPeriod(Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		List list = new ArrayList();
		HashMap<String, Object> hmap = new HashMap();
		StringBuffer sb = new StringBuffer();
		
		list = pedmord0000Dao.selectPeriodInfo(map );
		
		sb.append("■ 상품별 발주내역");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(map.get("text_data"));
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("발주일자;상품코드;상품명;입수;발주단위;발주수량;발주금액");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			hmap = (HashMap)list.get(i);
			
			if(hmap.get("ORD_DY")==null){
				sb.append("");
			}else{
				sb.append(String.valueOf(hmap.get("ORD_DY")).replaceAll("-", ""));
			}
			sb.append(";");
			sb.append(hmap.get("PROD_CD"));
			sb.append(";");
			sb.append(hmap.get("PROD_NM"));
			sb.append(";");
			sb.append(hmap.get("ORD_IPSU"));
			sb.append(";");
			sb.append(hmap.get("PUR_UNIT_CD_NM"));
			sb.append(";");
			sb.append(hmap.get("ORD_QTY"));
			sb.append(";");
			sb.append(hmap.get("BUY_PRC"));
			sb.append("\r\n");
		}
		sb.append("------------------------------------------------------------------------------------");
		
		commonUtil.createTextFile(request, response, sb);
	}
	
	public List selectJunpyoInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmord0000Dao.selectJunpyoInfo(map );
	}
	
	public void createTextJunpyo(Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		List list = new ArrayList();
		HashMap<String, Object> hmap = new HashMap();
		StringBuffer sb = new StringBuffer();
		
		list = pedmord0000Dao.selectJunpyoInfo(map );
		
		sb.append("■ 전표별 발주내역");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(map.get("text_data"));
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("발주일자;점포;전표번호;발주구분;발주수량;발주금액;센터입하일;점입하일;조회여부");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			hmap = (HashMap)list.get(i);
			
			if(hmap.get("ORD_DY")==null){
				sb.append("");
			}else{
				sb.append(String.valueOf(hmap.get("ORD_DY")).replaceAll("-", ""));
			}
			sb.append(";");
			sb.append(hmap.get("STR_NM"));
			sb.append(";");
			sb.append(hmap.get("ORD_SLIP_NO"));
			sb.append(";");
			sb.append(hmap.get("ORD_FG_NM"));
			sb.append(";");
			sb.append(hmap.get("TOT_QTY"));
			sb.append(";");
			sb.append(hmap.get("TOT_PRC"));
			sb.append(";");
			sb.append(String.valueOf(hmap.get("CTR_ARR_DY")).replaceAll("-", ""));
			sb.append(";");
			sb.append(String.valueOf(hmap.get("SPLY_DY")).replaceAll("-", ""));
			sb.append(";");
			sb.append(hmap.get("USER_HIT"));
			sb.append("\r\n");
		}
		sb.append("------------------------------------------------------------------------------------");
		
		commonUtil.createTextFile(request, response, sb);
	}
	
	public List selectJunpyoDetailInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmord0000Dao.selectJunpyoDetailInfo(map );
	}
	
	public void createTextJunpyoDetail(Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception{
		List list = new ArrayList();
		HashMap<String, Object> hmap = new HashMap();
		StringBuffer sb = new StringBuffer();
		String tmp = "empty";
		
		list = pedmord0000Dao.selectJunpyoDetailInfo(map );
		
		sb.append("■ 전표상세 발주내역");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(map.get("text_data"));
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			hmap = (HashMap)list.get(i);
			
			if(!tmp.equals(String.valueOf(hmap.get("ORD_SLIP_NO")))){
				sb.append("------------------------------------------------------------------------------------");
				sb.append("\r\n");
				sb.append("점포명;점포코드;발주일;전표번호;발주수량;발주금액;센터입하일;점입하일");
				sb.append("\r\n");
				sb.append(hmap.get("STR_NM"));
				sb.append(";");
				sb.append(hmap.get("STR_CD"));
				sb.append(";");
				sb.append(hmap.get("ORD_DY").toString().replaceAll("-", ""));
				sb.append(";");
				sb.append(hmap.get("ORD_SLIP_NO"));
				sb.append(";");
				sb.append(hmap.get("TOT_QTY"));
				sb.append(";");
				sb.append(hmap.get("TOT_PRC"));
				sb.append(";");
				if(hmap.get("CTR_ARR_DY")==null){
					sb.append("");
				}else{
					sb.append(String.valueOf(hmap.get("CTR_ARR_DY")).replaceAll("-", ""));
				}
				sb.append(";");
				if(hmap.get("SPLY_DY")==null){
					sb.append("");
				}else{
					sb.append(String.valueOf(hmap.get("SPLY_DY")).replaceAll("-", ""));
				}
				sb.append("\r\n");
			}
			
			if(!tmp.equals(String.valueOf(hmap.get("ORD_SLIP_NO")))){
				sb.append("------------------------------------------------------------------------------------");
				sb.append("\r\n");
				sb.append("상품코드;판매코드;상품명;입수;단위;발주수량;발주금액");
				sb.append("\r\n");
			}
				sb.append(hmap.get("PROD_CD"));
				sb.append(";");
				sb.append(hmap.get("SRCMK_CD"));
				sb.append(";");
				sb.append(hmap.get("PROD_NM"));
				sb.append(";");
				sb.append(hmap.get("ORD_IPSU"));
				sb.append(";");
				sb.append(hmap.get("PUR_UNIT_CD_NM"));
				sb.append(";");
				sb.append(hmap.get("ORD_QTY"));
				sb.append(";");
				sb.append(hmap.get("BUY_PRC"));
				sb.append("\r\n");

				tmp = String.valueOf(hmap.get("ORD_SLIP_NO"));
		}
		
		commonUtil.createTextFile(request, response, sb);
	}
	
	public List selectStoreInfo(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		
		return pedmord0000Dao.selectStoreInfo(map );
	}
	
	public void createTextStore(Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception{
		// TODO Auto-generated method stub
		
		List list = new ArrayList();
		HashMap<String, Object> hmap = new HashMap();
		StringBuffer sb = new StringBuffer();
		
		list = pedmord0000Dao.selectStoreInfo(map );
		
		sb.append("■ 점포별 발주내역");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(map.get("text_data"));
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("점포;상품코드;판매코드;상품명;규격;단위;입수;단가;수량;발주금액");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			hmap = (HashMap)list.get(i);
			
			sb.append(hmap.get("STR_NM"));
			sb.append(";");
			sb.append(hmap.get("PROD_CD"));
			sb.append(";");
			sb.append(hmap.get("SRCMK_CD"));
			sb.append(";");
			sb.append(hmap.get("PROD_NM"));
			sb.append(";");
			sb.append(hmap.get("PROD_STD"));
			sb.append(";");
			sb.append(hmap.get("PUR_UNIT_CD_NM"));
			sb.append(";");
			sb.append(hmap.get("ORD_IPSU"));
			sb.append(";");
			sb.append(hmap.get("BUY_DAN"));
			sb.append(";");
			sb.append(hmap.get("ORD_QTY"));
			sb.append(";");
			sb.append(hmap.get("BUY_PRC"));
			sb.append("\r\n");
		}
		sb.append("------------------------------------------------------------------------------------");
		
		commonUtil.createTextFile(request, response, sb);
	}
	
	public List selectJunpyoDetailInfoPDC(Map<String,Object> map) throws Exception{
		// TODO Auto-generated method stub
		return pedmord0000Dao.selectJunpyoDetailInfoPDC(map );
	}
	
	public void createTextJunpyoDetailPDC(Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws Exception{
		List list = new ArrayList();
		HashMap<String, Object> hmap = new HashMap();
		StringBuffer sb = new StringBuffer();
		
		String tmp = "empty";
		
		list = pedmord0000Dao.selectJunpyoDetailInfoPDC(map );
		
		sb.append("■ 전표상세(PDC) 발주내역");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(map.get("text_data"));
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			hmap = (HashMap)list.get(i);
			
			if(!tmp.equals(String.valueOf(hmap.get("ORD_SLIP_NO")))){
				sb.append("------------------------------------------------------------------------------------");
				sb.append("\r\n");
				sb.append("센터명;점포명;점포코드;발주일;전표번호;발주수량;발주금액;센터입하일;점입하일");
				sb.append("\r\n");
				sb.append(hmap.get("CTN_NM"));
				sb.append(";");
				sb.append(hmap.get("STR_NM"));
				sb.append(";");
				sb.append(hmap.get("STR_CD"));
				sb.append(";");
				sb.append(hmap.get("ORD_DY").toString().replaceAll("-", ""));
				sb.append(";");
				sb.append(hmap.get("ORD_SLIP_NO"));
				sb.append(";");
				sb.append(hmap.get("TOT_QTY"));
				sb.append(";");
				sb.append(hmap.get("TOT_PRC"));
				sb.append(";");
				if(hmap.get("CTR_ARR_DY")==null){
					sb.append("");
				}else{
					sb.append(String.valueOf(hmap.get("CTR_ARR_DY")).replaceAll("-", ""));
				}
				sb.append(";");
				if(hmap.get("SPLY_DY")==null){
					sb.append("");
				}else{
					sb.append(String.valueOf(hmap.get("SPLY_DY")).replaceAll("-", ""));
				}
				sb.append("\r\n");
			}
			
			if(!tmp.equals(String.valueOf(hmap.get("ORD_SLIP_NO")))){
				sb.append("------------------------------------------------------------------------------------");
				sb.append("\r\n");
				sb.append("상품코드;판매코드;상품명;입수;단위;발주수량;발주금액");
				sb.append("\r\n");
			}
				sb.append(hmap.get("PROD_CD"));
				sb.append(";");
				sb.append(hmap.get("SRCMK_CD"));
				sb.append(";");
				sb.append(hmap.get("PROD_NM"));
				sb.append(";");
				sb.append(hmap.get("ORD_IPSU"));
				sb.append(";");
				sb.append(hmap.get("PUR_UNIT_CD_NM"));
				sb.append(";");
				sb.append(hmap.get("ORD_QTY"));
				sb.append(";");
				sb.append(hmap.get("BUY_PRC"));
				sb.append("\r\n");

				tmp = String.valueOf(hmap.get("ORD_SLIP_NO"));
		}
		
		commonUtil.createTextFile(request, response, sb);
	}

}
