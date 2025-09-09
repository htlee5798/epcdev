package com.lottemart.epc.edi.sale.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.sale.dao.NEDMSAL0020Dao;
import com.lottemart.epc.edi.sale.model.NEDMSAL0020VO;
import com.lottemart.epc.edi.sale.service.NEDMSAL0020Service;
/**
 * 매출정보 - > 기간별 매출정보  - > 점포별 Controller
 * 
 * @author SUN GIL CHOI
 * @since 2015.11.04
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ---------------------------
 *   2015.11.04  	SUN GIL CHOI   최초 생성
 *
 * </pre>
 */
@Service("nedmsa0020Service")
public class NEDMSAL0020ServiceImpl implements NEDMSAL0020Service{
	@Autowired
	private NEDMSAL0020Dao nedmsa0020Dao;
	
	/**
	 * 매출정보 - > 매출정보(점포별)  
	 * @param NEDMSAL0020VO
	 * @return
	 */
	public List<NEDMSAL0020VO> selectStoreInfo(NEDMSAL0020VO map) throws Exception{
		// TODO Auto-generated method stub
		return nedmsa0020Dao.selectStoreInfo(map );
	}
	
	/**
	 * 매출정보 - > 매출정보(점포별)  
	 * @param NEDMSAL0020VO
	 * @param request
	 * @param response
	 * @return
	 */
	public void createTextStore(NEDMSAL0020VO map,HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		NEDMSAL0020VO pedmsa0020vo = new NEDMSAL0020VO();
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
		
		List list = nedmsa0020Dao.selectStoreInfo(map );
		
		sb.append("■ 점포별 매출내역");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(map.getTextData());
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("점포명;점포코드;매출수량;매출금액");
		//sb.append("점포명;점포코드;매출수량;매출금액;누계매출수량;누계매출금액");
		sb.append("\r\n");
		for(int i=0;i<list.size();i++){
			pedmsa0020vo = (NEDMSAL0020VO)list.get(i);
			
			total_qty += Long.parseLong(String.valueOf(pedmsa0020vo.getSaleQty()));
			total_amt += Long.parseLong(String.valueOf(pedmsa0020vo.getSaleAmt()));
			
			sb.append(pedmsa0020vo.getStrNm());
			sb.append(";");
			sb.append(pedmsa0020vo.getStrCd());
			sb.append(";");
			sb.append(pedmsa0020vo.getSaleQty());
			sb.append(";");
			sb.append(pedmsa0020vo.getSaleAmt());
//			sb.append(";");
//			sb.append(String.valueOf(total_qty));
//			sb.append(";");
//			sb.append(String.valueOf(total_amt));
			sb.append("\r\n");
		}
		sb.append("------------------------------------------------------------------------------------");
		commonUtil.createTextFile(request, response, sb);
	}
	
}


