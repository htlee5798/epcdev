package com.lottemart.epc.edi.usply.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.usply.dao.NEDMUSP0020Dao;
import com.lottemart.epc.edi.usply.model.NEDMUSP0020VO;
import com.lottemart.epc.edi.usply.service.NEDMUSP0020Service;

@Service("nedmusp0020Service")
public class NEDMUSP0020ServiceImpl implements NEDMUSP0020Service{
	@Autowired
	private NEDMUSP0020Dao NEDMUSP0020Dao;
	/**
	 * 미납정보 - > 기간정보  - > 점포별 조회
	 * @param NEDMUSP0020VO
	 * @return
	 */
	public List<NEDMUSP0020VO> selectStoreInfo(NEDMUSP0020VO map) throws Exception{
		// TODO Auto-generated method stub
		return NEDMUSP0020Dao.selectStoreInfo(map );
	}
	
	/**
	 * 텍스트 생성
	 * @param vo
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void createTextOrdProdList(NEDMUSP0020VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<NEDMUSP0020VO> list = new ArrayList();
		NEDMUSP0020VO listVO = new NEDMUSP0020VO();
		
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
		
		list = NEDMUSP0020Dao.selectStoreInfo(vo);
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("■ 기간별 미납정보(점포별)");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(vo.getTextData());
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("일자;점포;발주수량;발주금액;매입수량;매입금액;미납수량;미납금액");
		sb.append("\r\n");
		
		for (int i = 0; i < list.size(); i++) {
			listVO = list.get(i);
		
			sb.append(StringUtils.trimToEmpty(listVO.getSplyDy()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getStrNm()));
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
			
			sb.append("\r\n");
		}
		sb.append("------------------------------------------------------------------------------------");
		
		commonUtil.createTextFile(request, response, sb);
	}
}
