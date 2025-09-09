package com.lottemart.epc.order.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lottemart.common.util.DataMap;

public interface PSCMORD0005Service {

	public List<DataMap> selectSaleInfoByDate(Map<String, Object> map) throws Exception;

	public void createTextDay(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
