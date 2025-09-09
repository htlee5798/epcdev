package com.lottemart.epc.product.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.common.code.service.CommonCodeService;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.product.service.PSCMPRD0060Service;

import net.sf.json.JSONObject;

/**
 * @Class Name : PSCMPRD0060Controller.java
 * @Description : 공통 점포 팝업
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2019. 4. 18. 오전 10:12:00 신규생성
 *
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Controller
public class PSCMPRD0060Controller extends BaseController{

	private static final Logger logger = LoggerFactory
			.getLogger(PSCMPRD0060Controller.class);

	@Autowired
	private PSCMPRD0060Service pscmprd0060Service;

	@Autowired
	private CommonCodeService commonCodeService;

	/**
	 * Desc : 점포 기본 화면
	 * @Method Name : PSCMPRD0060
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("product/PSCMPRD0060.do")
	public String PSCMPRD0060(@RequestParam(value="mode", defaultValue="S") String mode
			,@RequestParam(value="id", defaultValue="") String id
			,HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.debug("================PBOMPRD00 Start================");
		logger.debug("=============== mode : "+ mode +"===============");
		logger.debug("=============== id   : "+ id +"===============");
		request.setAttribute("mode", mode);
		request.setAttribute("id", id);
		List<DataMap> str04CodeList = commonCodeService.getCodeList("STR04");
		request.setAttribute("str04CodeList", str04CodeList);
		logger.debug("================PBOMPRD00 End================");
		return "product/PSCMPRD0060";
	}


	/**
	 * Desc : 점포 목록 조회
	 * @Method Name : PSCMPRD0060CodeList
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("product/PSCMPRD0060CodeList.do")
	@ResponseBody
	public JSONObject PSCMPRD0060CodeList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		JSONObject jObj = new JSONObject();
		DataMap param = new DataMap(request);
		// 점포코드 조회
		List<Map<String, Object>> storeList = pscmprd0060Service.selectStoreList(param);
		jObj.put("data", storeList);
		return JsonUtils.getResultJson(jObj);
	}


}
