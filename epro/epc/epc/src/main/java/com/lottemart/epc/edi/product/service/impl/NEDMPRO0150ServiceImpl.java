package com.lottemart.epc.edi.product.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.product.dao.NEDMPRO0150Dao;
import com.lottemart.epc.edi.product.model.NEDMPRO0150VO;
import com.lottemart.epc.edi.product.service.NEDMPRO0150Service;

import lcn.module.framework.property.ConfigurationService;

@Service("nedmpro0150Service")
public class NEDMPRO0150ServiceImpl implements NEDMPRO0150Service {
	
	@Autowired
	private ConfigurationService config;
	
	@Autowired
	private NEDMPRO0150Dao nedmpro0150Dao;
	
	/**
	 * 현재일자 가져오기
	 * @return
	 * @throws Exception
	 */
	public String selectCurrDate() throws Exception {
		return nedmpro0150Dao.selectCurrDate();
	}
	
	/**
	 * Count
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int selectPbProductCount(NEDMPRO0150VO vo) throws Exception {
		return nedmpro0150Dao.selectPbProductCount(vo);
	}
	
	/**
	 * List 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0150VO> selectPbProductList(NEDMPRO0150VO vo) throws Exception {
		return nedmpro0150Dao.selectPbProductList(vo);
	}
	
	/**
	 * 재고등록
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String insertPbProduct(NEDMPRO0150VO vo, HttpServletRequest request) throws Exception {
		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		String workId = epcLoginVO.getLoginWorkId();
		
		ArrayList alProdJaego = vo.getAlProdJaego() ;
		
		if (alProdJaego != null && alProdJaego.size() > 0) {
			HashMap hm = new HashMap();
			
			NEDMPRO0150VO paramVO = null;
			
			for (int i = 0; i < alProdJaego.size(); i++) {
				hm = (HashMap) alProdJaego.get(i);
				
				paramVO = new NEDMPRO0150VO();
				
				paramVO.setStdDate((String) hm.get("stdDate"));				// 기준일자
				paramVO.setVenCd((String) hm.get("venCd"));					// 파트너사코드
				paramVO.setProdCd((String) hm.get("prodCd"));				// 상품코드
				paramVO.setSrcmkCd((String) hm.get("srcmkCd"));				// 판매코드
				paramVO.setMinProdQty((String) hm.get("minProdQty"));		// 최소생산량(BOX)
				paramVO.setProdReadTime((String) hm.get("prodReadTime"));	// 생산리드타임(일)
				paramVO.setJaegoQty((String) hm.get("jaegoQty"));			// 일별 재고수량(BOX)
//				paramVO.setRegId(epcLoginVO.getRepVendorId());				// 등록/수정자
				paramVO.setRegId(workId);	//등록아이디
				paramVO.setModId(workId);	//수정아이디
				
				nedmpro0150Dao.insertPbProduct(paramVO);
			}
		}
		
		return "success";
	}
	
}
