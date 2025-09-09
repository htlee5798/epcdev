package com.lottemart.epc.edi.usply.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.usply.dao.NEDMUSP0060Dao;
import com.lottemart.epc.edi.usply.model.NEDMUSP0060VO;
import com.lottemart.epc.edi.usply.service.NEDMUSP0060Service;

@Service("nedmusp0060Service")
public class NEDMUSP0060ServiceImpl implements NEDMUSP0060Service{
	@Autowired
	private NEDMUSP0060Dao NEDMUSP0060Dao;
	/**
	 * 미납정보 - > 기간정보  - > 패널티 확정 조회
	 * @param NEDMUSP0060VO
	 * @return
	 */
	public List<NEDMUSP0060VO> selectPenaltyInfo(NEDMUSP0060VO map) throws Exception{
		// TODO Auto-generated method stub
		return NEDMUSP0060Dao.selectPenaltyInfo(map );
	}
	
	/**
	 * 텍스트 생성
	 * @param vo
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void createTextOrdProdList(NEDMUSP0060VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<NEDMUSP0060VO> list = new ArrayList();
		NEDMUSP0060VO listVO = new NEDMUSP0060VO();
		
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
		
		list = NEDMUSP0060Dao.selectPenaltyInfo(vo);
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("■ 기간별 미납정보(패널티확정)");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(vo.getTextData());
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("일자;상품코드;상품명;규격;입수;단위;점포;미납수량;미납금액;미납사유");
		sb.append("\r\n");
		
		for (int i = 0; i < list.size(); i++) {
			listVO = list.get(i);
			
			sb.append(StringUtils.trimToEmpty(listVO.getSplyDy()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getSrcmkCd()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getProdNm()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getProdStd()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getOrdIpsu()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getOrdUnit()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getStrNm()));
			sb.append(";");			
			sb.append(StringUtils.trimToEmpty(listVO.getUsplyQty()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getUsplyBuyAmt()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getMinorCd()));
			sb.append(";");
			
			sb.append("\r\n");
		}
		sb.append("------------------------------------------------------------------------------------");
		
		commonUtil.createTextFile(request, response, sb);
	}
}
