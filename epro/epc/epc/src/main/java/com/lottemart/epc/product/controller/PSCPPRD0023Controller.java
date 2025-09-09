package com.lottemart.epc.product.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.product.service.PSCPPRD0023Service;


@Controller("PSCPPRD0023Controller")
public class PSCPPRD0023Controller {

	private static final Logger logger = LoggerFactory.getLogger(PSCPPRD0023Controller.class);


	@Autowired
	private PSCPPRD0023Service pscpprd0023Service;
	@Autowired
	private MessageSource messageSource;

	/**
	 * 추가구성품 폼 페이지
	 * @Description : 추가구성품 목록 초기페이지 로딩
	 * @Method Name : prodComponentForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/selectProductComponentForm.do")
	public String prodComponentForm(HttpServletRequest request) throws Exception {
		  return "product/internet/PSCPPRD002301";
	}

	/**
	 * 연관상품 폼 페이지
	 * @Description : 연관상품 목록 초기페이지 로딩
	 * @Method Name : prodComponentForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/selectProductRelationForm.do")
	public String prodRelationForm(HttpServletRequest request) throws Exception {
		  return "product/internet/PSCPPRD002302";
	}

	/**
	 * 추가구성품 조회
	 * @Description : 추가구성품 목록 조회
	 * @Method Name : prodComponentSearch
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/selectComponentSearch.do")
	public @ResponseBody Map prodComponentSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map rtnMap = new HashMap<String, Object>();

		try {

			DataMap paramMap = new DataMap(request);

			String currentPage = paramMap.getString("currentPage");

			// 데이터 조회
			List<DataMap> list = pscpprd0023Service.selectProdComponentList(paramMap);

			rtnMap = JsonUtils.convertList2Json((List)list, -1, currentPage);

			rtnMap.put("result", true);
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
		    rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}

	/**
	 * 추가구성품, 연관상품 등록
	 * @Description : 추가구성품, 연관상품 등록 로직
	 * @Method Name : saveProdComponent
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/saveProdComponent.do")
	public @ResponseBody JSONObject saveProdComponent(HttpServletRequest request, HttpServletResponse response) throws Exception {

        JSONObject jObj = new JSONObject();
		String message = "";
		int resultCnt = 0;

		try {

			resultCnt = pscpprd0023Service.saveProdComponent(request);

			// 처리 결과
			if (resultCnt > 0) {
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
}