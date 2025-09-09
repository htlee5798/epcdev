package com.lottemart.epc.edi.order.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.order.dao.NEDMORD0010Dao;
import com.lottemart.epc.edi.order.model.NEDMORD0010VO;
import com.lottemart.epc.edi.order.service.NEDMORD0010Service;

@Service("nedmord0010Service")
public class NEDMORD0010ServiceImpl implements NEDMORD0010Service {
	
	@Autowired
	private NEDMORD0010Dao dao;

	/**
	 * 기간정보 - > 상품별 조회
	 */
	public List<NEDMORD0010VO> selectOrdPordList(NEDMORD0010VO vo) throws Exception {
	
		List<NEDMORD0010VO> list= new ArrayList();
		list = dao.selectOrdPordList(vo);
		
		return list;
	}
	
	/**
	 * 발주정보(상품별) txt파일 생성
	 */
	public void createTextOrdProdList(NEDMORD0010VO vo,HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		List<NEDMORD0010VO> list = new ArrayList();
		NEDMORD0010VO listVO = new NEDMORD0010VO();
		
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
		
		list = dao.selectOrdPordList(vo);
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("■ 상품별 발주내역");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(vo.getTextData());
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("발주일자;상품코드;상품명;입수;발주단위;발주수량;발주금액");
		sb.append("\r\n");
		
		for (int i = 0; i < list.size(); i++) {
			listVO = list.get(i);

			if (listVO.getOrdDy() == null) {
				sb.append("");
			} else {
				sb.append(String.valueOf(listVO.getOrdDy()).replaceAll("-", ""));
			}
			
			sb.append(";");
			sb.append(listVO.getProdCd());
			sb.append(";");
			sb.append(listVO.getProdNm());
			sb.append(";");
			sb.append(listVO.getOrdIpsu());
			sb.append(";");
			sb.append(listVO.getPurUnitCdNm());
			sb.append(";");
			sb.append(listVO.getOrdQty());
			sb.append(";");
			sb.append(listVO.getBuyPrc());
			sb.append("\r\n");
		}
		sb.append("------------------------------------------------------------------------------------");
		
		commonUtil.createTextFile(request, response, sb);
	}
	
}
