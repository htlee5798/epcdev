package com.lottemart.epc.edi.usply.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.usply.dao.NEDMUSP0010Dao;
import com.lottemart.epc.edi.usply.model.NEDMUSP0010VO;
import com.lottemart.epc.edi.usply.service.NEDMUSP0010Service;

@Service("nedmusp0010Service")
public class NEDMUSP0010ServiceImpl implements NEDMUSP0010Service{
	@Autowired
	private NEDMUSP0010Dao NEDMUSP0010Dao;
	/**
	 * 미납정보 - > 기간정보  - > 일자별 조회
	 * @param NEDMUSP0010VO
	 * @return
	 */
	public List<NEDMUSP0010VO> selectDayInfo(NEDMUSP0010VO map) throws Exception{
		// TODO Auto-generated method stub
		return NEDMUSP0010Dao.selectDayInfo(map );
	}
	
	/**
	 * 텍스트 생성
	 * @param vo
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void createTextOrdProdList(NEDMUSP0010VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<NEDMUSP0010VO> list = new ArrayList();
		NEDMUSP0010VO listVO = new NEDMUSP0010VO();
		
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
		
		list = NEDMUSP0010Dao.selectDayInfo(vo);
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("■ 기간별 미납정보(일자별)");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(vo.getTextData());
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("납품일자;발주수량;발주금액;매입수량;매입금액;미납수량;미납금액;미납률");
		sb.append("\r\n");
		
		for (int i = 0; i < list.size(); i++) {
			listVO = list.get(i);
		
			sb.append(StringUtils.trimToEmpty(listVO.getSplyDy()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getOrdQty()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getOrdBuyAmt()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getBuyQty()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getBuyBuyAmt()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getUsplyQty()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getUsplyBuyAmt()));
			sb.append(";");			
			sb.append(StringUtils.trimToEmpty(listVO.getUsplyRatio()));
			sb.append(";");
			
			sb.append("\r\n");
		}
		sb.append("------------------------------------------------------------------------------------");
		
		commonUtil.createTextFile(request, response, sb);
	}
	
}
