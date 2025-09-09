package com.lottemart.epc.edi.buy.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.edi.buy.dao.NEDMBUY0030Dao;
import com.lottemart.epc.edi.buy.model.NEDMBUY0030VO;
import com.lottemart.epc.edi.buy.service.NEDMBUY0030Service;
import com.lottemart.epc.edi.commonUtils.commonUtil;

/**
 * @Class Name : NEDMBUY0030ServiceImpl
 * @Description : 매입정보 상품별 조회 ServiceImpl Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      		수정자          		수정내용
 *  -------    --------    ---------------------------
 * 2015.11.17	O YEUN KWON	  최초생성
 * 
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */

@Service("nedmbuy0030Service")
public class NEDMBUY0030ServiceImpl implements NEDMBUY0030Service{
	
	@Autowired
	private NEDMBUY0030Dao nedmbuy0030Dao;

	/**
	 * 조회
	 */
	@Override
	public List<NEDMBUY0030VO> selectProductInfo(NEDMBUY0030VO nEDMBUY0030VO) throws Exception {
		return nedmbuy0030Dao.selectProductInfo(nEDMBUY0030VO );
	}
	
	/**
	 * 텍스트 파일 생성
	 */
	@Override
	public void createTextOrdProdList(NEDMBUY0030VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<NEDMBUY0030VO> list = new ArrayList();
		NEDMBUY0030VO listVO = new NEDMBUY0030VO();
		
		vo.setSrchStartDate(vo.getSrchStartDate().replaceAll("-", ""));
		vo.setSrchEndDate(vo.getSrchEndDate().replaceAll("-", ""));
		
		if (!StringUtils.trimToEmpty(request.getParameter("storeVal")).equals("")) {
			ArrayList	al	=	new ArrayList();
			String[] arrStore	=	request.getParameter("storeVal").split("-");
			
			for (int i = 0; i < arrStore.length; i++) {
				al.add(arrStore[i]);
			}
			
			vo.setSrchStoreVal(al);
		}
		
		list = nedmbuy0030Dao.selectProductInfo(vo);
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("■ 기간별 매입정보(상품별)");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(vo.getTextData());
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("매입일자;상품코드;상품명;규격;입수;발주단위;발주수량;발주금액;매입구분;박스수량;낱개수량;금액");		
		sb.append("\r\n");
		
		for (int i = 0; i < list.size(); i++) {
			listVO = list.get(i);
			
			sb.append(StringUtils.trimToEmpty(listVO.getBuyDy()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getProdCd()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getProdNm()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getProdStd()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getOrdIpsu()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getDanwi()));
			sb.append(";");			
			sb.append(StringUtils.trimToEmpty(listVO.getTotQty()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getTotPrc()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getBuyRtnNm()));
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
