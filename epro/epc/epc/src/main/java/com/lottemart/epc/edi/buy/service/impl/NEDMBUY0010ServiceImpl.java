package com.lottemart.epc.edi.buy.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.edi.buy.dao.NEDMBUY0010Dao;
import com.lottemart.epc.edi.buy.model.NEDMBUY0010VO;
import com.lottemart.epc.edi.buy.service.NEDMBUY0010Service;
import com.lottemart.epc.edi.commonUtils.commonUtil;

/**
 * @Class Name : NEDMBUY0010ServiceImpl
 * @Description : 매입정보 일자별 조회 ServiceImpl Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      		수정자          		수정내용
 *  -------    --------    ---------------------------
 * 2015.11.16	O YEUN KWON	  최초생성
 * 
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */

@Service("nedmbuy0010Service")
public class NEDMBUY0010ServiceImpl implements NEDMBUY0010Service{
	
	@Autowired
	private NEDMBUY0010Dao nedmbuy0010Dao;

	/**
	 * 조회
	 */
	@Override
	public List<NEDMBUY0010VO> selectBuyInfo(NEDMBUY0010VO nEDMBUY0010VO) throws Exception {
		return nedmbuy0010Dao.selectBuyInfo(nEDMBUY0010VO );
	}
	
	/**
	 * 텍스트 생성
	 */
	@Override
	public void createTextOrdProdList(NEDMBUY0010VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<NEDMBUY0010VO> list = new ArrayList();
		NEDMBUY0010VO listVO = new NEDMBUY0010VO();
		
		vo.setSrchStartDate(vo.getSrchStartDate().replaceAll("-", ""));
		vo.setSrchEndDate(vo.getSrchEndDate().replaceAll("-", ""));
		
		if (!StringUtils.trimToEmpty(request.getParameter("storeVal")).equals("")) {
			ArrayList	al	=	new ArrayList();
			String[] arrStore	=	request.getParameter("storeVal").split("-");
			
			for (int i = 0; i < arrStore.length; i++) {
				al.add(arrStore[i]);
			}
			
			vo.setSearchStoreAl(al);
		}
		
		list = nedmbuy0010Dao.selectBuyInfo(vo);
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("■ 기간별 매입정보(일자별)");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(vo.getTextData());
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("매입일자;발주수량;발주금액;박스수량;낱개수량;금액");
		sb.append("\r\n");
		
		for (int i = 0; i < list.size(); i++) {
			listVO = list.get(i);

			/*if (listVO.getOrdDy() == null) {
				sb.append("");
			} else {
				sb.append(String.valueOf(listVO.getOrdDy()).replaceAll("-", ""));
			}*/
		
			sb.append(StringUtils.trimToEmpty(listVO.getBuyDy()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getTotQty()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getTotPrc()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getBuyBoxQty()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getBuyQty()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getBuyBuyPrc()));
			sb.append("\r\n");
		}
		sb.append("------------------------------------------------------------------------------------");
		
		commonUtil.createTextFile(request, response, sb);
	}
}
