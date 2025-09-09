package com.lottemart.epc.order.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lottemart.epc.order.model.PSCMORD0007VO;

public interface PSCMORD0007Service {

	public List<PSCMORD0007VO> selectSaleProductList(Map<String, Object> map) throws Exception;

	public void createTextProduct(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception;

}
