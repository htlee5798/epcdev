package com.lottemart.epc.edi.product.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtils;
import com.lottemart.epc.edi.commonUtils.commonUtil;
import com.lottemart.epc.edi.order.service.NEDMORD0010Service;
import com.lottemart.epc.edi.product.dao.NEDMPRO0140Dao;
import com.lottemart.epc.edi.product.model.NEDMPRO0140VO;
import com.lottemart.epc.edi.product.service.NEDMPRO0140Service;

@Service("nedmpro0140Service")
public class NEDMPRO0140ServiceImpl implements NEDMPRO0140Service {
		


	
	@Autowired
	private NEDMPRO0140Dao dao;
	
	public List<NEDMPRO0140VO> selectPlcProductList(NEDMPRO0140VO vo) throws Exception {
							   
		List<NEDMPRO0140VO> list= new ArrayList();
		list = dao.selectPlcProductList(vo);
		
		return list;
	}
	
	
}
