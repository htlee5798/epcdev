package com.lottemart.epc.order.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lottemart.epc.order.model.PSCMORD0007VO;

public interface PSCMORD0008Service {

	public List<PSCMORD0007VO> selectSaleProductDetailList(Map<String, Object> map) throws Exception;

	public void createTextProductDetail(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception;

}
