package com.lottemart.epc.edi.buy.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.edi.buy.dao.NEDMBUY0060Dao;
import com.lottemart.epc.edi.buy.model.NEDMBUY0060VO;
import com.lottemart.epc.edi.buy.service.NEDMBUY0060Service;
import com.lottemart.epc.edi.commonUtils.commonUtil;

/**
 * @Class Name : NEDMBUY0060ServiceImpl
 * @Description : 매입정보 점포상품별 조회 ServiceImpl Class
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

@Service("nedmbuy0060Service")
public class NEDMBUY0060ServiceImpl implements NEDMBUY0060Service{
	
	@Autowired
	private NEDMBUY0060Dao nedmbuy0060Dao;

	@Override
	public int selectStoreProductInfoTotCnt(NEDMBUY0060VO nEDMBUY0060VO) throws Exception {
		return nedmbuy0060Dao.selectStoreProductInfoTotCnt(nEDMBUY0060VO);
	}
	
	/**
	 * 리스트 조회
	 */
	@Override
	public List<NEDMBUY0060VO> selectStoreProductInfo(NEDMBUY0060VO nEDMBUY0060VO) throws Exception {
		return nedmbuy0060Dao.selectStoreProductInfo(nEDMBUY0060VO );
	}
	
	
	/**
	 * 텍스트 파일 생성
	 * @param vo
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void createTextOrdProdList(NEDMBUY0060VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<NEDMBUY0060VO> list = new ArrayList();
		NEDMBUY0060VO listVO = new NEDMBUY0060VO();
		
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
		
		list = nedmbuy0060Dao.selectStoreProductInfo(vo);
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("■ 기간별 매입정보(점포상품별)");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(vo.getTextData());
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("판매코드;상품명;규격;입수;발주단위;점포명;발주수량;발주금액;매입구분;박스수량;낱개수량;금액");		
		sb.append("\r\n");
		
		for (int i = 0; i < list.size(); i++) {
			listVO = list.get(i);
			
			sb.append(StringUtils.trimToEmpty(listVO.getSrcmkCd()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getProdNm()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getProdStd()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getOrdIpsu()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getDanwi()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getStrNm()));
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
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getProdStd()));
			sb.append(";");			
			sb.append("\r\n");
		}
		sb.append("------------------------------------------------------------------------------------");
		
		commonUtil.createTextFile(request, response, sb);
	}
		
	
}
