package com.lottemart.epc.edi.product.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.product.dao.NEDMPRO0300Dao;
import com.lottemart.epc.edi.product.model.NEDMPRO0300VO;
import com.lottemart.epc.edi.product.service.NEDMPRO0300Service;

@Service("nedmpro0300Service")
public class NEDMPRO0300ServiceImpl implements NEDMPRO0300Service {
	
	@Autowired
	private NEDMPRO0300Dao nedmpro0300Dao;
	
	/**
	 * 행사정보 등록내역 조회
	 */
	public HashMap<String,Object> selectProEventAppList(NEDMPRO0300VO paramVo, HttpServletRequest request) throws Exception {
		
		HashMap<String, Object> returnMap = new HashMap<String, Object>();		//반환할 맵
		List<NEDMPRO0300VO> list = new ArrayList<NEDMPRO0300VO>();				//리스트 조회 결과 Vo
		
		
		int totalCount = 0;
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(ConfigUtils.getString("lottemart.epc.session.key"));
		//paramVo.setLifnr(epcLoginVO.getRepVendorId()); // 파트너사 코드
		if(epcLoginVO != null) {
			String[] ven_cd = epcLoginVO.getVendorId();
			paramVo.setVenCdArr(ven_cd);
		}
		// 행사정보 등록내역 카운터 조회
		totalCount = nedmpro0300Dao.selectProEventAppListCount(paramVo);

		if(totalCount > 0){	// 행사정보 등록내역 리스트 조회
			list = nedmpro0300Dao.selectProEventAppList(paramVo);
		}
		
		returnMap.put("list", list);							//리스트 데이터
		returnMap.put("totalCount", totalCount);				//조회 결과 카운터
		
		return returnMap;
	}
	
	/**
	 * ECS 계약서 조회
	 */
	public NEDMPRO0300VO selectEcsDocInfo(NEDMPRO0300VO paramVo) throws Exception {
		return nedmpro0300Dao.selectEcsDocInfo(paramVo);
		
	}
	
	
}
