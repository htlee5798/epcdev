package com.lottemart.epc.edi.usply.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.usply.dao.NEDMUSP0050Dao;
import com.lottemart.epc.edi.usply.model.NEDMUSP0050VO;
import com.lottemart.epc.edi.usply.service.NEDMUSP0050Service;

@Service("nedmusp0050Service")
public class NEDMUSP0050ServiceImpl implements NEDMUSP0050Service{
	@Autowired
	private NEDMUSP0050Dao NEDMUSP0050Dao;
	/**
	 * 미납정보 - > 기간정보  - > 미납사유 입력&조회 조회
	 * @param NEDMUSP0050VO
	 * @param request
	 * @return
	 */
	public List<NEDMUSP0050VO> selectUsplyReasonInfo(NEDMUSP0050VO map) throws Exception{
		// TODO Auto-generated method stub
		return NEDMUSP0050Dao.selectUsplyReasonInfo(map );
	}
	
	
	/**
	 * 미납정보 - > 기간정보  - > 미납사유 입력&조회 조회
	 * @param NEDMUSP0050VO
	 * @param request
	 * @return
	 */                        
	public List<NEDMUSP0050VO> selectUpdateUsplyReasonInfo(NEDMUSP0050VO map) throws Exception{
		// TODO Auto-generated method stub
		return NEDMUSP0050Dao.selectUpdateUsplyReasonInfo(map );
	}
	
	
	
	/**
	 * 미납정보 - > 기간정보  - > 미납사유 입력&조회 저장
	 * @param NEDMUSP0050VO
	 * @param request
	 * @return
	 */
	public void selectUsplyReasonUpdate(NEDMUSP0050VO map) throws Exception{
		// TODO Auto-generated method stub
		List<NEDMUSP0050VO> updateParams = map.getUpdateParam();
		for (NEDMUSP0050VO updateParam : updateParams) { 
			NEDMUSP0050Dao.selectUsplyReasonUpdate(updateParam);
		} 
	}
	
	/**
	 * 텍스트 생성
	 * @param vo
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void createTextOrdProdList(NEDMUSP0050VO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<NEDMUSP0050VO> list = new ArrayList();
		NEDMUSP0050VO listVO = new NEDMUSP0050VO();
		
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
		
	//	list = NEDMUSP0050Dao.selectUsplyReasonInfo(vo);
		list = NEDMUSP0050Dao.selectUpdateUsplyReasonInfo(vo);
		
		
		
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("■ 기간별 미납정보(미납사유)");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(vo.getTextData());
		sb.append("\r\n");
		sb.append("------------------------------------------------------------------------------------");
		sb.append("\r\n");
		sb.append("납품일자;상품코드;판매코드;상품명;점포;미납수량;미납금액;미납사유1;미납사유2;확정여부");
		sb.append("\r\n");
		
		for (int i = 0; i < list.size(); i++) {
			listVO = list.get(i);
		
			sb.append(StringUtils.trimToEmpty(listVO.getSplyDy()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getProdCd()));
			sb.append(";");
			
			sb.append(StringUtils.trimToEmpty(listVO.getSrcmkCd()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getProdNm()));
			sb.append(";");
		//	sb.append(StringUtils.trimToEmpty(listVO.getOrdIpsu()));
		//	sb.append(";");
		//	sb.append(StringUtils.trimToEmpty(listVO.getOrdUnit()));
		//	sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getStrNm()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getUsplyQty()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getUsplyBuyAmt()));
			sb.append(";");			
			sb.append(StringUtils.trimToEmpty(listVO.getVenReasonNm()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getVenDetailNm()));
			sb.append(";");
			sb.append(StringUtils.trimToEmpty(listVO.getBuyerConfirm()));
			sb.append(";");
			
			sb.append("\r\n");
		}
		sb.append("------------------------------------------------------------------------------------");
		
		commonUtil.createTextFile(request, response, sb);
	}
}
