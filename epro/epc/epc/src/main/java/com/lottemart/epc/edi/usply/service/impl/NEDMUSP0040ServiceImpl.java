package com.lottemart.epc.edi.usply.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.usply.dao.NEDMUSP0040Dao;
import com.lottemart.epc.edi.usply.model.NEDMUSP0040VO;
import com.lottemart.epc.edi.usply.service.NEDMUSP0040Service;

@Service("nedmusp0040Service")
public class NEDMUSP0040ServiceImpl implements NEDMUSP0040Service{
	@Autowired
	private NEDMUSP0040Dao NEDMUSP0040Dao;
	/**
	 * 미납정보 - > 기간정보  - > 상품상세 첫페이지
	 * @param NEDMUSP0040VO
	 * @return
	 */
	public List<NEDMUSP0040VO> selectProductDetailInfo(NEDMUSP0040VO map) throws Exception{
		// TODO Auto-generated method stub
		return NEDMUSP0040Dao.selectProductDetailInfo(map );
	}
	
	public void createTextOrdProdList(NEDMUSP0040VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<NEDMUSP0040VO> list = new ArrayList();
		NEDMUSP0040VO listVO = new NEDMUSP0040VO();
		
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
		
		list = NEDMUSP0040Dao.selectProductDetailInfo(vo);
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("■ 기간별 미납정보(상품상세)");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(vo.getTextData());
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("상품코드;상품명;입수;단위;점포;발주수량;발주금액;매입수량;매입금액;미납수량;미납금액");
		sb.append("\r\n");
		
		for (int i = 0; i < list.size(); i++) {
			listVO = list.get(i);
		
			sb.append(StringUtils.trimToEmpty(listVO.getSrcmkCd()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getProdNm()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getOrdIpsu()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getOrdUnit()));
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
