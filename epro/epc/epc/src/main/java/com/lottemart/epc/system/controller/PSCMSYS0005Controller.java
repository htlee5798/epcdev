package com.lottemart.epc.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lcn.module.common.util.StringUtil;
import lcn.module.framework.property.ConfigurationService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.util.LoginUtil;
import com.lottemart.epc.product.model.PSCPPRD0020VO;
import com.lottemart.epc.product.service.PSCMPRD0030Service;
import com.lottemart.epc.system.model.PSCMSYS0005DtlVO;
import com.lottemart.epc.system.model.PSCMSYS0005VO;
import com.lottemart.epc.system.service.PSCMSYS0005Service;

/**
 * @Class Name : PSCMSYS0005Controller.java
 * @Description : 전상법 템플릿 컨트롤
 * @Modification Information
 *
 *               <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2016. 5. 28. 오후 5:00:31 UNI
 *
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Controller("pscmsys0005Controller")
public class PSCMSYS0005Controller {

	private static final Logger logger = LoggerFactory
			.getLogger(PSCMSYS0005Controller.class);

	@Autowired
	private ConfigurationService config;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private PSCMPRD0030Service pscmprd0030Service;
	@Autowired
	private PSCMSYS0005Service pscmsys0005Service;

	/**
	 * Desc : Session & LoginId
	 *
	 * @Method Name : loginVo
	 * @param request
	 * @return
	 * @param
	 * @return
	 * @exception Exception
	 */
	@ModelAttribute("loginVo")
	public EpcLoginVO loginVo(HttpServletRequest request) {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);
		return epcLoginVO;
	}

	/**
	 * Desc : 전상법 템플릿 기본 화면
	 *
	 * @Method Name : selectEcsTem
	 * @param request
	 * @param response
	 * @param epcLoginVO
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("system/selectEcsTem.do")
	public String selectEcsTem(HttpServletRequest request,
			@ModelAttribute("loginVo") EpcLoginVO epcLoginVO) throws Exception {

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}
		// 협력사 조회
		request.setAttribute("epcLoginVO", epcLoginVO);
		// 상품 분류
		DataMap dataMap = new DataMap();
		dataMap.put("vendorId", epcLoginVO.getVendorId());
		List<DataMap> infoGrpList = pscmprd0030Service.selectInfoGrpCdList(dataMap);
		request.setAttribute("infoGrpList", infoGrpList);

		return "system/PSCMSYS0005";
	}

	/**
	 * Desc : 전상법 템플릿 검색
	 *
	 * @Method Name : searchEscTem
	 * @param request
	 * @param epcLoginVO
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("system/searchEscTem.do")
	public @ResponseBody Map searchEscTem(HttpServletRequest request,
			@ModelAttribute("loginVo") EpcLoginVO epcLoginVO) throws Exception {

		Map rtnMap = new HashMap<String, Object>();

		try {
			DataMap param = new DataMap(request);

			// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
			if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
				logger.debug("===== > Sessiomcheck 실패<===== ");
			}
			// 협력사코드 전체를 선택한 경우 로그인 세션에 있는 협력사 전체를 설정한다
			if ("".equals(param.getString("searchVendorId"))) {
				param.put("vendorId", LoginUtil.getVendorList(epcLoginVO)); // 협력업체
																			// 코드
			} else {
				ArrayList<String> vendorList = new ArrayList<String>();
				vendorList.add(param.getString("searchVendorId"));
				param.put("vendorId", vendorList); // 협력업체코드
			}
			// row 설정
			String rowsPerPage = StringUtil.null2str(
					(String) param.get("rowsPerPage"),
					config.getString("count.row.per.page"));

			int startRow = ((Integer
					.parseInt((String) param.get("currentPage")) - 1) * Integer
					.parseInt(rowsPerPage)) + 1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) - 1;

			// 데이터 세팅
			param.put("currentPage", (String) param.get("currentPage"));
			param.put("startRow", Integer.toString(startRow));
			param.put("endRow", Integer.toString(endRow));
			param.put("rowsPerPage", rowsPerPage);
			param.put("searchVendorId", param.getString("searchVendorId"));

			// 전체 조회 건수
			int totalCnt = pscmsys0005Service.searchEscTemCnt((Map) param);
			param.put("totalCount", Integer.toString(totalCnt));

			// 데이터 조회
			List<PSCMSYS0005VO> list = pscmsys0005Service
					.searchEscTem((Map) param);
			rtnMap = JsonUtils.convertList2Json((List) list, totalCnt,
					param.getString("currentPage"));
			rtnMap.put("result", true);

		} catch (Exception e) {
			// 작업오류
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		}
		return rtnMap;
	}

	/**
	 * Desc : 전상법템플릿 삭제
	 *
	 * @Method Name : deleteEscTem
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("system/deleteEscTem.do")
	public @ResponseBody JSONObject deleteEscTem(HttpServletRequest request)
			throws Exception {
		JSONObject jObj = new JSONObject();
		DataMap dataMap = new DataMap(request);
		int resultCnt = 0;
		try {
			resultCnt = pscmsys0005Service.deleteEscTem(dataMap);
			if (resultCnt > 0) {
				jObj.put("Code", 1);
				jObj.put(
						"Message",
						resultCnt
								+ "건의 "
								+ messageSource.getMessage(
										"msg.common.success.request", null,
										Locale.getDefault()));
			} else {
				jObj.put("Code", 0);
				jObj.put("Message", messageSource.getMessage(
						"msg.common.fail.request", null, Locale.getDefault()));
			}
		} catch (Exception e) {
			jObj.put("Code", -1);
			jObj.put("Message", e.getMessage());
			// 처리오류
			logger.error("updateSelPontError==>", e);
		}
		return JsonUtils.getResultJson(jObj);
	}

	/**
	 * Desc : 템플릿 저장
	 *
	 * @Method Name : insertEscTem
	 * @param request
	 * @param epcLoginVO
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("system/insertEscTem.do")
	public @ResponseBody JSONObject insertEscTem(HttpServletRequest request,
			@ModelAttribute("loginVo") EpcLoginVO epcLoginVO) throws Exception {
		JSONObject jObj = new JSONObject();
		int resultCnt = 0;

		try {
			// 로그인 관련
			if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
				logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
			}

			// 데이터 가공
			request.setAttribute("vendorId", epcLoginVO.getRepVendorId());
			request.setAttribute("regId", epcLoginVO.getRepVendorId());


			 resultCnt = pscmsys0005Service.insertMasEscTem(request);

			 if( resultCnt > 0){
					jObj.put("Code", 1);
					jObj.put("Message", resultCnt + "건의 " + messageSource.getMessage("msg.common.success.request", null, Locale.getDefault()));
				}
				else{
					jObj.put("Code", 0);
					jObj.put("Message", messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault()));
				}
			} catch (Exception e) {
				jObj.put("Code", -1);
				jObj.put("Message", e.getMessage());
				// 처리오류
				logger.error("insertAllPoint==>>", e);
			}
			return JsonUtils.getResultJson(jObj);
		}


	/**
	 * Desc : 전상법 템플릿 업데이트
	 * @Method Name : updateEscTem
	 * @param request
	 * @param epcLoginVO
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("system/updateEscTem.do")
	public @ResponseBody JSONObject updateEscTem(HttpServletRequest request,
			@ModelAttribute("loginVo") EpcLoginVO epcLoginVO) throws Exception {
		JSONObject jObj = new JSONObject();
		int resultCnt = 0;

		try {
			// 로그인 관련
			if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
				logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
			}
			 request.setAttribute("modId", epcLoginVO.getRepVendorId());
			 resultCnt = pscmsys0005Service.updateMasEscTem(request);


			 if( resultCnt > 0){
					jObj.put("Code", 1);
					jObj.put("Message", resultCnt + "건의 " + messageSource.getMessage("msg.common.success.request", null, Locale.getDefault()));
				}
				else{
					jObj.put("Code", 0);
					jObj.put("Message", messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault()));
				}
			} catch (Exception e) {
				jObj.put("Code", -1);
				jObj.put("Message", e.getMessage());
				// 처리오류
				logger.error("insertAllPoint==>>", e);
			}
			return JsonUtils.getResultJson(jObj);
		}


	/**
	 * Desc : 콤보박스 엑션
	 *
	 * @Method Name : selectComBox
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("system/selectComBox.do")
	public  @ResponseBody Map selectComBox(HttpServletRequest request ,@ModelAttribute("loginVo") EpcLoginVO epcLoginVO)
			throws Exception {

		Map rtnMap = new HashMap<String, Object>();

		try {

			DataMap param = new DataMap(request);

			// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
			if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
				logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
			}
			// 조건 관련 변수
			String infoGrpCd = request.getParameter("infoGrpCd");
			param.put("infoGrpCd", infoGrpCd);

			// 전체 조회 건수
			int totalCnt = pscmprd0030Service.cntInfoGrpDetList(param);

			param.put("totalCount", Integer.toString(totalCnt));

			// 리스트 조회
			List<PSCPPRD0020VO> list = pscmprd0030Service .selectInfoGrpDetList(param);

			//json
			JSONArray jArray = new JSONArray();
	        if (list != null) jArray = (JSONArray)JSONSerializer.toJSON(list);

	        String jStr = "{Data:" + jArray + "}";
	        rtnMap.put("ibsList", jStr);

	     // 조회된 데이터가 없는 경우
	        if (jArray.isEmpty()) {
	        	rtnMap.put("result", false);
	        }
			rtnMap.put("result", true);
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}

	/**
	 * Desc : 전상법 템플릿 폼
	 * @Method Name : insertFormEcsTem
	 * @param request
	 * @param epcLoginVO
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("system/insertFormEcsTem.do")
	public String insertFormEcsTem(HttpServletRequest request,
			@ModelAttribute("loginVo") EpcLoginVO epcLoginVO) throws Exception {

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}
		// 협력사 조회
		request.setAttribute("epcLoginVO", epcLoginVO);
		// 상품 분류
		DataMap dataMap = new DataMap(request);
		dataMap.put("vendorId", epcLoginVO.getVendorId());
		List<DataMap> infoGrpList = pscmprd0030Service
				.selectInfoGrpCdList(dataMap);
		request.setAttribute("infoGrpList", infoGrpList);

		return "system/PSCMSYS000501";
	}

	/**
	 * Desc : 전상법 템플릿 상세
	 * @Method Name : seletFormEcsTemDtl
	 * @param request
	 * @param epcLoginVO
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("system/seletFormEcsTemDtl.do")
	public String seletFormEcsTemDtl(HttpServletRequest request,
			@ModelAttribute("loginVo") EpcLoginVO epcLoginVO) throws Exception {

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}
		// 협력사 조회
		request.setAttribute("epcLoginVO", epcLoginVO);
		// 상품 분류
		DataMap dataMap = new DataMap();
		dataMap.put("vendorId", epcLoginVO.getVendorId());
		List<DataMap> infoGrpList = pscmprd0030Service.selectInfoGrpCdList(dataMap);
		request.setAttribute("infoGrpList", infoGrpList);
		//전상법 템플릿 MASTER
		String norProdSeq =  request.getParameter("norProdSeq");
		PSCMSYS0005VO pscmsys0005vo = pscmsys0005Service.dtlEscTem(norProdSeq);
		request.setAttribute("pscmsys0005vo", pscmsys0005vo);
		request.setAttribute("norProdSeq", norProdSeq);
		return "system/PSCMSYS000502";
	}

	/**
	 * Desc : IBSHeet 세팅
	 * @Method Name : selectSheet
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("system/selectSheet.do")
	public @ResponseBody Map selectSheet(HttpServletRequest request)
			throws Exception {
		Map rtnMap = new HashMap<String, Object>();

		try {

			DataMap param = new DataMap(request);

			// 리스트 조회
			List<PSCMSYS0005DtlVO> list =  pscmsys0005Service.selectSheet(param);

			// json
			JSONArray jArray = new JSONArray();
			if (list != null)
				jArray = (JSONArray) JSONSerializer.toJSON(list);

			String jStr = "{Data:" + jArray + "}";
			rtnMap.put("ibsList", jStr);

			// 조회된 데이터가 없는 경우
			if (jArray.isEmpty()) {
				rtnMap.put("result", false);
			}
			rtnMap.put("result", true);
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}

}
