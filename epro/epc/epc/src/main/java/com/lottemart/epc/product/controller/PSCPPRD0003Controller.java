package com.lottemart.epc.product.controller;


import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.views.AjaxJsonModelHelper;
import lcn.module.framework.property.ConfigurationService;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.common.util.RestAPIUtil;
import com.lottemart.common.util.RestConst;
import com.lottemart.epc.common.dao.CommonDao;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.product.model.PSCPPRD0003VO;
import com.lottemart.epc.product.service.PSCPPRD0003Service;

/**
 * @Class Name : PSCPPRD0003Controller.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 20. 오후 02:02:02 mjChoi
 *
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Controller
public class PSCPPRD0003Controller
{
	private static final Logger logger = LoggerFactory.getLogger(PSCPPRD0003Controller.class);

	@Autowired
	private PSCPPRD0003Service pscpprd0003Service;
	@Autowired
	private ConfigurationService config;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private CommonDao commonDao;

	/**
	 * 단품 정보 폼 페이지
	 * @Description : 단품 정보 목록 초기페이지 로딩
	 * @Method Name : selectItemForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/selectProductItemForm.do")
	public String selectItemForm(HttpServletRequest request) throws Exception {
		// 파라미터
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("prodCd", request.getParameter("prodCd"));
		String pageUrlLink = "";
		// 인터넷 전용상품 여부
		// logger.debug("::: selectPrdItemType :::");

		int prdType = pscpprd0003Service.selectPrdType(paramMap);

		if (prdType == 1) {
			int itemType = pscpprd0003Service.selectPrdItemType(paramMap);

			if (itemType > 0) request.setAttribute("visible", "Y"); // 추가,저장 버튼 노출
			request.setAttribute("add_mode", "Y");
			pageUrlLink = "product/internet/PSCPPRD000303";
		} else {
			request.setAttribute("add_mode", "N");
			pageUrlLink = "product/internet/PSCPPRD000301";
		}

		return pageUrlLink;
	}

	/**
	 * 단품 정보 목록
	 * @Description : 페이지 로딩시 상품키워드 목록을 얻어서 그리드에 리턴
	 * @Method Name : selectProductItem
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/selectProductItem.do")
	public @ResponseBody Map selectProductItem(HttpServletRequest request) throws Exception {
		Map rtnMap = new HashMap<String, Object>();

		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("prodCd", request.getParameter("prodCd"));

			List<PSCPPRD0003VO> list = pscpprd0003Service.selectPrdItemList(paramMap); // 데이터 조회
			rtnMap = JsonUtils.convertList2Json((List) list, list.size(), null);

			rtnMap.put("result", true);
		} catch (Exception e) {
			// 오류
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}

	/**
	 * 단품 정보 목록
	 * @Description : 페이지 로딩시 상품키워드 목록을 얻어서 그리드에 리턴
	 * @Method Name : prdItemSearch
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/selectProductItemOnline.do")
	public @ResponseBody Map prdItemSearchOnline(HttpServletRequest request) throws Exception {
		Map rtnMap = new HashMap<String, Object>();

		try {

			// 파라미터
			Map<String, String> paramMap = new HashMap<String, String>();

			paramMap.put("prodCd", request.getParameter("prodCd"));

			// 데이터 조회
			List<PSCPPRD0003VO> list = pscpprd0003Service.selectPrdItemOnlineList(paramMap);

			rtnMap = JsonUtils.convertList2Json((List) list, list.size(), null);

			rtnMap.put("result", true);
		} catch (Exception e) {
			// 오류
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}

	/**
	 * 단품 정보 입력,수정 폼 페이지
	 * @Description : 단품 정보 입력,수정 초기페이지 로딩 (팝업)
	 * @Method Name : saveProductItemForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/saveProductItemForm.do")
	public String saveProductItemForm(HttpServletRequest request) throws Exception
	{
		String mode = request.getParameter("mode");

		if ("update".equals(mode))
		{
			// 파라미터
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("prodCd", request.getParameter("prodCd"));
			paramMap.put("itemCd", request.getParameter("itemCd"));

			// 상세정보
			PSCPPRD0003VO prdItemInfo = pscpprd0003Service.selectPrdItem(paramMap);
			if(prdItemInfo == null)
			{
				logger.debug("error : " + prdItemInfo);
			}

			request.setAttribute("resultItemInfo", prdItemInfo);
		}

		// 단품 칼라 COMBO 내용
		List<DataMap> colorList = pscpprd0003Service.selectPrdItemColorList();

		// 단품 사이즈 구분 COMBO 내용
		List<DataMap> szCatList = pscpprd0003Service.selectPrdItemSizeCategoryList();

		request.setAttribute("colorList", colorList);
		request.setAttribute("szCatList", szCatList);

		return "product/internet/PSCPPRD000302";
	}

	/**
	 * 단품 정보 입력,수정 처리
	 * @Description : 팝업창의 단품 정보 입력 버튼 클릭시 상품키워드 입력/수정 처리후 그리드를 리로드한다.
	 * @Method Name : saveProductItem
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/saveProductItem.do")
	public ModelAndView saveProductItem(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if( epcLoginVO == null || epcLoginVO.getVendorId() == null )
		{
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}

    	String message = null;
    	String mode = null;

		PSCPPRD0003VO bean = new PSCPPRD0003VO();
		bean.setProdCd((String) request.getParameter("prodCd"));
		bean.setItemCd((String) request.getParameter("itemCd"));
		bean.setColorCd((String) request.getParameter("colorCd"));
		bean.setSzCatCd((String) request.getParameter("szCatCd"));
		bean.setSzCd((String) request.getParameter("szCd"));
		bean.setMdProdCd((String) request.getParameter("mdProdCd"));
		bean.setMdSrcmkCd((String) request.getParameter("mdSrcmkCd"));
		bean.setRepCd((String) request.getParameter("repCd"));
		bean.setRegId((String) request.getParameter("vendorId"));

		mode = (String) request.getParameter("mode");

		// 값이 있을경우 에러
		String resultStr = "";

		if ("insert".equals(mode))
		{
			resultStr = validate(bean);
		}
		else if ("update".equals(mode))
		{
			resultStr = validate2(bean);
		}

		message = messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault());

		if (!"".equals(resultStr))
		{
			return AjaxJsonModelHelper.create(resultStr);
		}

		try
		{
			int resultCnt = 0;

			if ("insert".equals(mode))
			{
				resultCnt = pscpprd0003Service.insertPrdItem(bean);
			}
			else if (mode.equals("update"))
			{
				resultCnt = pscpprd0003Service.updatePrdItem(bean);
			}

	    	if (resultCnt > 0)
	    	{
				// API 연동 (EPC -> 통합BO API)
				try {
					String prodCd = request.getParameter("prodCd");
					String result = "";
					
					RestAPIUtil rest = new RestAPIUtil();
					if(!prodCd.substring(0,1).equals("D")){
						if(commonDao.selectEcRegisteredYn(prodCd)){
							Map<String, Object> reqMap = new HashMap<String, Object>();
							reqMap.put("spdNo", prodCd);
							result = rest.sendRestCall(RestConst.PD_API_0026, HttpMethod.POST, reqMap, RestConst.PD_API_TIME_OUT, true);
							logger.debug("API CALL RESULT = " + result);
							result = rest.sendRestCall(RestConst.PD_API_0003, HttpMethod.POST, reqMap, RestConst.PD_API_TIME_OUT, true);
							logger.debug("API CALL RESULT = " + result);
							result = rest.sendRestCall(RestConst.PD_API_0004, HttpMethod.POST, reqMap, RestConst.PD_API_TIME_OUT, true);
							logger.debug("API CALL RESULT = " + result);
							result = rest.sendRestCall(RestConst.PD_API_0002, HttpMethod.POST, reqMap, RestConst.PD_API_TIME_OUT, true);
							logger.debug("API CALL RESULT = " + result);
						}
					}
					
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
	    		return AjaxJsonModelHelper.create("");
	    	}
	    	else
	    	{
	    		return AjaxJsonModelHelper.create(message);
	    	}
		}
		catch (Exception e)
		{
			return AjaxJsonModelHelper.create(message);
		}
	}

	/**
	 * 단품 정보 입력,수정 처리(온라인)
	 * @Description : 팝업창의 단품 정보 입력 버튼 클릭시 상품키워드 입력/수정 처리후 그리드를 리로드한다.
	 * @Method Name : saveProductItemOnline
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/saveProductItemOnline.do")
	public @ResponseBody JSONObject saveProductItemOnline(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		String vendorId = null;
		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}

		// vendorId null인경우 대책
		if (request.getParameter("vendorId") != null) {
			vendorId = request.getParameter("vendorId");
		} else if (epcLoginVO != null) {
			if (epcLoginVO.getVendorId() != null && epcLoginVO.getVendorId()[0] != null) {
				vendorId = epcLoginVO.getVendorId()[0];
			} else if (epcLoginVO.getAdminId() != null) {
				vendorId = epcLoginVO.getAdminId();
			} else if (epcLoginVO.getCono() != null && epcLoginVO.getCono()[0] != null) {
				vendorId = epcLoginVO.getCono()[0];
			}
		}

		JSONObject jObj = new JSONObject();

		String message = "";

		// 값 셋팅
		PSCPPRD0003VO bean;
		int resultCnt = 0;
		String updateMode = "";
		String[] itemCds	= request.getParameterValues("itemCd");
		String[] optnDescs	= request.getParameterValues("optnDesc");
		String[] rservStkQtys	= request.getParameterValues("rservStkQty");

		try {
			for (int i = 0; i < optnDescs.length; i++) {
				bean = new PSCPPRD0003VO();

				bean.setProdCd(request.getParameter("prodCd"));
				bean.setItemCd(itemCds[i]);
				bean.setOptnDesc(optnDescs[i]);
				bean.setRservStkQty(rservStkQtys[i]);

				//옥션연동관련
				bean.setRegId(vendorId);

				//tpr_item 신규 추가
				if(bean.getItemCd() == null || "".equals(bean.getItemCd())){
					updateMode = "insert";
				}else{
					updateMode = "update";
				}
				resultCnt += pscpprd0003Service.updateTprItemList(bean,updateMode);
			}

			// 처리 결과
			if (resultCnt > 0) {
				
				// API 연동 (EPC -> 통합BO API)
				try {
					String prodCd = request.getParameter("prodCd");
					String result = "";
					
					RestAPIUtil rest = new RestAPIUtil();
					if(!prodCd.substring(0,1).equals("D")){
						if(commonDao.selectEcRegisteredYn(prodCd)){
							Map<String, Object> reqMap = new HashMap<String, Object>();
							reqMap.put("spdNo", prodCd);
							result = rest.sendRestCall(RestConst.PD_API_0026, HttpMethod.POST, reqMap, RestConst.PD_API_TIME_OUT, true);
							logger.debug("API CALL RESULT = " + result);
							result = rest.sendRestCall(RestConst.PD_API_0003, HttpMethod.POST, reqMap, RestConst.PD_API_TIME_OUT, true);
							logger.debug("API CALL RESULT = " + result);
							result = rest.sendRestCall(RestConst.PD_API_0004, HttpMethod.POST, reqMap, RestConst.PD_API_TIME_OUT, true);
							logger.debug("API CALL RESULT = " + result);
							result = rest.sendRestCall(RestConst.PD_API_0002, HttpMethod.POST, reqMap, RestConst.PD_API_TIME_OUT, true);
							logger.debug("API CALL RESULT = " + result);
						}
					}
					
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				
				jObj.put("Code", 1);
				message  = messageSource.getMessage("msg.common.success.request", null, Locale.getDefault());
				jObj.put("Message", resultCnt + "건의 " + message);

			} else {
				jObj.put("Code", 0);
				message  = messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault());
				jObj.put("Message", message);
			}

		} catch (Exception e) {
			jObj.put("Code", -1);
			jObj.put("Message", e.getMessage());
		}

		return JsonUtils.getResultJson(jObj);
	}

	/**
	 * 단품 사이즈 콤보 목록
	 * @Description : 단품의 사이즈카테고리 선택시 사이즈 콤보의 목록을 로딩하여 리턴한다
	 * @Method Name : selectProductItemSizeList
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/selectProductItemSizeList.do")
	public ModelAndView selectProductItemSizeList(HttpServletRequest request) throws Exception
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("szCatCd", request.getParameter("szCatCd"));

		// 사이즈 콤보 가져오기
		List<DataMap> itemSzCdList = pscpprd0003Service.selectPrdItemSizeList(map);

		// json 결과 생성
		DataMap resultMap = new DataMap();
		resultMap.put("itemSzCdList", itemSzCdList);
		resultMap.put("comboNm", "szCd");

		return AjaxJsonModelHelper.create(resultMap);
	}

	/*
	 * validate 체크
	 */
	public String validate(PSCPPRD0003VO bean) throws Exception
	{
		//--체크
		if(StringUtils.isEmpty(bean.getProdCd())) {
			return "상품 코드가 없습니다.";
		}

		return "";
	}

	public String validate2(PSCPPRD0003VO bean) throws Exception
	{
		//--체크
		if(StringUtils.isEmpty(bean.getProdCd())) {
			return "상품 코드가 없습니다.";
		}
		if(StringUtils.isEmpty(bean.getItemCd())) {
			return "단품 코드가 없습니다.";
		}

		return "";
	}

}
