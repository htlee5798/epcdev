package com.lottemart.epc.board.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.lottemart.common.code.dao.CommonCodeDao;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.common.util.RestAPIUtil;
import com.lottemart.common.util.RestConst;
import com.lottemart.epc.board.model.ReviewCondition;
import com.lottemart.epc.board.service.PSCMBRD0015Service;
import com.lottemart.epc.common.dao.CommonDao;
import com.lottemart.epc.common.model.EpcLoginVO;

import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.ConfigurationService;
import net.sf.json.JSONException;

/**
 * @Class Name : PSCMBRD0015ServiceImpl.java
 * @Description :
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2019. 01. 09 pjw
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Service("PSCMBRD0015Service")
public class PSCMBRD0015ServiceImpl implements PSCMBRD0015Service {
	
	private static final Logger logger = LoggerFactory.getLogger(PSCMBRD0015ServiceImpl.class);
	
	@Autowired
	private CommonDao commonDao;
	@Autowired
	private CommonCodeDao commonCodeDao;
	
	@Override
	public void ecReviewForm(HttpServletRequest request, ConfigurationService config) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		//VendorId
		request.setAttribute("epcLoginVO", epcLoginVO);

		String endDate = DateUtil.getToday("yyyy-MM-dd");
		String startDate = DateUtil.formatDate(DateUtil.addDay(endDate, -7),"-");

		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);
		request.setAttribute("rvTypCd", commonCodeDao.getEcCodeList("RV_TYP_CD"));
		request.setAttribute("rvDvsCd", commonCodeDao.getEcCodeList("RV_DVS_CD"));
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> ecReviewSearch(ReviewCondition condition, HttpServletRequest request, ConfigurationService config) {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		logger.info("##########################################"
				+ "getAdminId : " + epcLoginVO.getAdminId() + "getLoginNm : "
				+ epcLoginVO.getLoginNm() + "getRepVendorId : "
				+ epcLoginVO.getRepVendorId());
		Map<String, Object> rtnMap = new HashMap<String, Object>();

		try {
			DataMap param = new DataMap(request);

			// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
			if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
				logger.debug("===== > Session check 실패<===== ");
				throw new Exception();
			}
			
			String repVendorId = commonDao.selectSaleVendorId(epcLoginVO.getRepVendorId());
			int rowsPerPage = Integer.parseInt(request.getParameter("rowsPerPage"));
			int currentPage = Integer.parseInt(request.getParameter("currentPage"));

			if(rowsPerPage > 100){
				rowsPerPage = 100;
			}
			
			condition.setModStrDttm(param.getString("startDate").replaceAll("-", "")+"000000");
			condition.setModEndDttm(param.getString("endDate").replaceAll("-", "")+"235959");
			condition.setRowsPerPage(Integer.toString(rowsPerPage));
			condition.setPageNo((String) param.get("currentPage"));
			condition.setSpdNo(param.getString("spdNo"));
			condition.setRvDvsCd(param.getString("rvDvsCd"));
			condition.setRvTypCd(param.getString("rvTypCd"));
			condition.setLrtrNo(repVendorId);
			
			RestAPIUtil rest = new RestAPIUtil();
			String json = rest.sendRestCall(RestConst.PD_API_0014, HttpMethod.POST, condition, RestConst.PD_API_TIME_OUT, true);
			logger.debug("API CALL RESULT = " + json);
			
			ObjectMapper mapper = new ObjectMapper();
			
			if (!"".equals(json)) {
				HashMap<String,Object> value = mapper.readValue(json,new TypeReference<HashMap<String,Object>>(){});
				Map<String,Object> bodyArr = (Map<String, Object>) value.get("body");
				if(bodyArr!=null){
					ArrayList<Map<String,Object>> dataArr = (ArrayList<Map<String, Object>>)bodyArr.get("data");
					for (int i = 0; i < dataArr.size(); i++) {
						String prodCd = (String) dataArr.get(i).get("spdNo");					
						dataArr.get(i).put("spdNo", prodCd.replace("LM", ""));
						if(currentPage > 1){
							dataArr.get(i).put("num",currentPage*rowsPerPage+i+1);
						}else{
							dataArr.get(i).put("num",i+1);	
						}
					}
					rtnMap = JsonUtils.convertList2Json(dataArr, rowsPerPage*50, request.getParameter("currentPage"));
				}else{
					rtnMap = JsonUtils.convertList2Json(null, 0, request.getParameter("currentPage"));
				}
			}else {
				throw new JSONException();
			}
			rtnMap.put("result", true);
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		}
		return rtnMap;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void ecReviewExcelDown(ReviewCondition condition, HttpServletRequest request, HttpServletResponse response, ConfigurationService config) {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		logger.info("##########################################"
				+ "getAdminId : " + epcLoginVO.getAdminId() + "getLoginNm : "
				+ epcLoginVO.getLoginNm() + "getRepVendorId : "
				+ epcLoginVO.getRepVendorId());
		
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		
		try {

			// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
			if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
				logger.debug("===== > Session check 실패<===== ");
				throw new Exception();
			}

			String repVendorId = commonDao.selectSaleVendorId(epcLoginVO.getRepVendorId());
			
			condition.setModStrDttm(StringUtils.defaultIfEmpty(request.getParameter("startDate"), "").replaceAll("-", "")+"000000");
			condition.setModEndDttm(StringUtils.defaultIfEmpty(request.getParameter("endDate"), "").replaceAll("-", "")+"235959");
			condition.setRowsPerPage("999");
			condition.setPageNo((String) request.getParameter("currentPage"));
			condition.setSpdNo(request.getParameter("spdNo"));
			condition.setRvDvsCd(request.getParameter("rvDvsCd"));
			condition.setRvTypCd(request.getParameter("rvTypCd"));
			condition.setLrtrNo(repVendorId);
			
			RestAPIUtil rest = new RestAPIUtil();
			String json = rest.sendRestCall(RestConst.PD_API_0014, HttpMethod.POST, condition, RestConst.PD_API_TIME_OUT, true);
			logger.debug("API CALL RESULT = " + json);
			ObjectMapper mapper = new ObjectMapper();

			if (!"".equals(json)) {
				HashMap<String,Object> value = mapper.readValue(json,new TypeReference<HashMap<String,Object>>(){});
				Map<String,Object> bodyArr = (Map<String, Object>) value.get("body");
				if(bodyArr != null){
					ArrayList<Map<String,Object>> dataArr = (ArrayList<Map<String, Object>>)bodyArr.get("data");
					for (int i = 0; i < dataArr.size(); i++) {
						String prodCd = (String) dataArr.get(i).get("spdNo");					
						dataArr.get(i).put("spdNo", prodCd.replace("LM", ""));
						dataArr.get(i).put("num",i+1);
					}
					JsonUtils.IbsExcelDownload(dataArr, request, response);
				}else{
					JsonUtils.IbsExcelDownload(null, request, response);
				}
			}else {
				throw new JSONException();
			}
			rtnMap.put("result", true);
			
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
		}
	}
	
}
