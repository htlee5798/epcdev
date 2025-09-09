package com.lottemart.epc.product.controller;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lcn.module.common.views.AjaxJsonModelHelper;
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
import org.springframework.web.servlet.ModelAndView;

import com.lcnjf.util.StringUtil;
import com.lottemart.common.code.service.CommonCodeService;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.util.LoginUtil;
import com.lottemart.epc.product.model.PSCMPRD0040DTLVO;
import com.lottemart.epc.product.model.PSCMPRD0040VO;
import com.lottemart.epc.product.service.PSCMPRD0040Service;

/**
 *
 * @author choi
 * @Description :
 * @Class : com.lottemart.epc.product.controller
 *
 *        <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011.12.16  choi
 * @version :
 * </pre>
 */
@Controller("pscmprd0040Controller")
public class PSCMPRD0040Controller {

	private static final Logger logger = LoggerFactory
			.getLogger(PSCMPRD0040Controller.class);

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private CommonCodeService commonCodeService;
	@Autowired
	private PSCMPRD0040Service pscmprd0040Service;
	@Autowired
	private ConfigurationService config;

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
	 * Desc : IBSHEET 세팅
	 *
	 * @Method Name : selectSelSheet
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("product/selectSelSheet.do")
	public @ResponseBody Map selectSelSheet(HttpServletRequest request)
			throws Exception {
		Map rtnMap = new HashMap<String, Object>();

		try {

			DataMap param = new DataMap(request);

			// 리스트 조회
			List<PSCMPRD0040DTLVO> list = pscmprd0040Service
					.selectSelSheet(param);

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

	/**
	 * Desc : 셀링포인트 저장, 수정
	 *
	 * @Method Name : selSave
	 * @param request
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("product/selSave.do")
	public ModelAndView selSave(HttpServletRequest request) throws Exception {
		DataMap param = new DataMap(request);

		String message = "";
		param.put("startDate", param.getString("startDate").replaceAll("-", ""));
		param.put("endDate", param.getString("endDate").replaceAll("-", ""));
//		param.put("title", URLDecoder.decode(request.getParameter("title"), "UTF-8"));
//		param.put("content", URLDecoder.decode(request.getParameter("content"), "UTF-8"));

		try {
			// 저장, 수정
			int resultCnt = pscmprd0040Service.selSave(param);
			message = messageSource.getMessage("msg.common.fail.request", null,
					Locale.getDefault());
			if (resultCnt > 0) {
				return AjaxJsonModelHelper.create("");
			} else {
				return AjaxJsonModelHelper.create(message);
			}
		} catch (Exception e) {
			return AjaxJsonModelHelper.create(messageSource.getMessage(
					"msg.common.fail.request", null, Locale.getDefault()));
		}
	}

/*	*//**
	 * Desc : 상세 업데이트
	 *
	 * @Method Name : updateAllPoint
	 * @param request
	 * @param epcLoginVO
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 *//*
	@RequestMapping("product/updateAllPoint.do")
	public @ResponseBody JSONObject updateAllPoint(HttpServletRequest request,
			@ModelAttribute("loginVo") EpcLoginVO epcLoginVO) throws Exception {
		JSONObject jObj = new JSONObject();
		DataMap param = new DataMap(request);
		int resultCnt = 0;
		try {
			// 로그인 관련
			if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
				logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
			}

			// SHEET 데이터
			String mdTalkSeq = request.getParameter("mdTalkSeq");
			String regId = epcLoginVO.getRepVendorId();
			String[] applyToTypeCd = request.getParameter( "applyToTypeCd_chkVal").split("/");
			String[] applyToCd = request.getParameter("applyToCd_chkVal").split("/");

			//마스터 수정

			// 디테일 데이터 삭제
			pscmprd0040Service.deleteSelPont(mdTalkSeq);

			// 데이터 가공
			for (int i = 0; i < applyToCd.length; i++) {
				PSCMPRD0040DTLVO pscmprd0040dtlvo = new PSCMPRD0040DTLVO();
				pscmprd0040dtlvo.setMdTalkSeq(mdTalkSeq);
				pscmprd0040dtlvo.setRegId(regId);
				pscmprd0040dtlvo.setApplyToTypeCd(applyToTypeCd[i]);
				pscmprd0040dtlvo.setApplyToCd(applyToCd[i]);
				System.out.println(pscmprd0040dtlvo.toString());
				//resultCnt += pscmprd0040Service .insertAllPoint(pscmprd0040dtlvo);
			}

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
			logger.error("insertAllPoint==>>", e);
		}
		return JsonUtils.getResultJson(jObj);
	}*/

	/**
	 * Desc : 셀링포인트상세 수정
	 *
	 * @Method Name : editSelPont
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("product/editSelPont.do")
	public @ResponseBody JSONObject editSelPont(HttpServletRequest request,
			@ModelAttribute("loginVo") EpcLoginVO epcLoginVO) throws Exception {
		JSONObject jObj = new JSONObject();
		DataMap param = new DataMap(request);

		//데이터 세팅
 		param.put("startDate", param.getString("startDate").replaceAll("-", ""));
		param.put("endDate", param.getString("endDate").replaceAll("-", ""));
		param.put("regId", epcLoginVO.getRepVendorId());

		param.put("title", URLDecoder.decode(request.getParameter("title"), "UTF-8"));
		param.put("content", URLDecoder.decode(request.getParameter("content"), "UTF-8"));
		param.put("mdTalkSeq", URLDecoder.decode(request.getParameter("mdTalkSeq"), "UTF-8"));

		int resultCnt = 0;
		try {
			// 로그인 관련
			if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
				logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
			}
			// 수정
			resultCnt += pscmprd0040Service.editSelPont(param);

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
			logger.error("insertAllPoint==>>", e);
		}
		return JsonUtils.getResultJson(jObj);
	}

	/**
	 * Desc :
	 *
	 * @Method Name : editSelPont
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 *//*
	@RequestMapping("product/editSelPont.do")
	public ModelAndView editSelPont(HttpServletRequest request)
			throws Exception {
		DataMap param = new DataMap(request);

		String message = "";
		param.put("startDate", param.getString("announStartDy").replaceAll("-", ""));
		param.put("endDate", param.getString("announEndDy").replaceAll("-", ""));
		try {

			int resultCnt = pscmprd0040Service.editSelPont(param);
			message = messageSource.getMessage("msg.common.fail.request", null,
					Locale.getDefault());
			if (resultCnt > 0) {
				return AjaxJsonModelHelper.create("");
			} else {
				return AjaxJsonModelHelper.create(message);
			}
		} catch (Exception e) {
			return AjaxJsonModelHelper.create(messageSource.getMessage(
					"msg.common.fail.request", null, Locale.getDefault()));
		}

	}
*/
	/**
	 * Desc : 등록 팝업
	 *
	 * @Method Name : selCreatePop
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("product/selCreatePop.do")
	public String selCreatePop(HttpServletRequest request) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}
		//VendorId
		request.setAttribute("epcLoginVO", epcLoginVO);

		// 로그인 아이디
		request.setAttribute("regId", epcLoginVO.getRepVendorId());

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
		Calendar calVal = Calendar.getInstance();
		//startDate
		String startDate = dateFormat.format(calVal.getTime());

		// endDate를 현재날짜 일주일 후으로 셋팅
		calVal.add(Calendar.DAY_OF_MONTH, +7);
		String endDate = dateFormat.format(calVal.getTime());

		// 검색기간 셋팅
		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);

		return "product/PSCMPRD004001";
	}

	/**
	 * Desc : 셀링포인트 할당
	 *
	 * @Method Name : selectSelPointView
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("product/selectSelPointView.do")
	public String selectSelPointView(HttpServletRequest request)
			throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}

		// 문의유형공통코드 조회
		List<DataMap> codeList = commonCodeService.getCodeList("SM341");
		request.setAttribute("codeList", codeList);

		// 로그인 아이디
		request.setAttribute("regId", epcLoginVO.getRepVendorId());

		// 상세 SEQ
		String recommSeq = request.getParameter("mdTalkSeq");
		request.setAttribute("mdTalkSeq", recommSeq);
		try {
			// 상품톡 MD
			PSCMPRD0040VO qnatViewInfo = pscmprd0040Service.selectSelPointView(recommSeq);
			request.setAttribute("data", qnatViewInfo);

		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
		}
		return "product/PSCMPRD004002";
	}

	/**
	 * 셀링포인트 폼
	 *
	 * @see repProdCdForm
	 * @Locaton : com.lottemart.epc.product.controller
	 * @MethodName : repProdCdForm
	 * @author : choi
	 * @Description :
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/selPoint.do")
	public String selPoint(HttpServletRequest request) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}

		// 로그인 아이디
		request.setAttribute("epcLoginVO", epcLoginVO);
		// VendorId
		request.setAttribute("regId", epcLoginVO.getRepVendorId());

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
		Calendar calVal = Calendar.getInstance();
		//startDate
		String startDate = dateFormat.format(calVal.getTime());
		// endDate를 현재날짜 일주일 후으로 셋팅
		calVal.add(Calendar.DAY_OF_MONTH, +7);
		String endDate = dateFormat.format(calVal.getTime());


		// 검색기간 셋팅
		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);

		return "product/PSCMPRD0040";
	}

	/**
	 * 셀링포인트 검색
	 *
	 * @see selectRepProdCdList
	 * @Locaton : com.lottemart.epc.product.controller
	 * @MethodName : selectRepProdCdList
	 * @author : hjKim
	 * @Description :
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/selectSelPoint.do")
	public @ResponseBody Map selectSelPoint(HttpServletRequest request)
			throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		Map rtnMap = new HashMap<String, Object>();

		try {

			DataMap param = new DataMap(request);

			// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
			if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
				logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
			}
			request.setAttribute("epcLoginVO", epcLoginVO);
			logger.debug("===== >vendorId : "
					+ param.getString("searchVendorId") + "<=====");

			// 협력사코드 전체를 선택한 경우 로그인 세션에 있는 협력사 전체를 설정한다
			if ("".equals(param.getString("searchVendorId"))) {
				param.put("vendorId", LoginUtil.getVendorList(epcLoginVO)); // 협력업체
																			// 코드
			} else {
				ArrayList<String> vendorList = new ArrayList<String>();
				vendorList.add(param.getString("searchVendorId"));
				param.put("vendorId", vendorList); // 협력업체코드
			}
			//row 설정
			String rowPerPage = param.getString("rowsPerPage");
			String currentPage = param.getString("currentPage");
			int rowPage = Integer.parseInt(rowPerPage);
			int currPage = Integer.parseInt(currentPage);

			logger.debug("startDate    =    " + param.getString("startDate"));
			logger.debug("endDate    =    " + param.getString("endDate"));

			// 페이징 관련 변수
			String rowsPerPage = StringUtil.null2str(
					(String) param.get("rowsPerPage"),
					config.getString("count.row.per.page"));

			int startRow = ((Integer
					.parseInt((String) param.get("currentPage")) - 1) * Integer
					.parseInt(rowsPerPage)) + 1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) - 1;

			param.put("currentPage", (String) param.get("currentPage"));
			param.put("startRow", Integer.toString(startRow));
			param.put("endRow", Integer.toString(endRow));
			param.put("rowsPerPage", rowsPerPage);
			param.put("startDate",
					param.getString("startDate").replaceAll("-", ""));
			param.put("endDate", param.getString("endDate").replaceAll("-", ""));

			// 조건 관련 변수
			param.put("seachType", param.getString("seachType"));
			param.put("useYn", param.getString("useYn"));
			param.put("titleNm", param.getString("titleNm"));
			param.put("searchNm", param.getString("searchNm"));

			// 전체 조회 건수
			int totalCnt = pscmprd0040Service.selectSelPointCnt((Map) param);

			param.put("totalCount", Integer.toString(totalCnt));

			// 리스트 조회
			List<PSCMPRD0040VO> list = pscmprd0040Service.selectSelPoint(param);

			rtnMap = JsonUtils.convertList2Json((List) list, totalCnt,
					param.getString("currentPage"));

			rtnMap.put("result", true);
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}

	/**
	 * Desc : 셀링포인트 디테일
	 *
	 * @Method Name : selectSelPontDtl
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	// 수정변경으로 인한 변경
	/*
	 * @RequestMapping("product/selectSelPontDtl.do") public @ResponseBody Map
	 * selectSelPontDtl(HttpServletRequest request) throws Exception {
	 *
	 * Map rtnMap = new HashMap<String, Object>(); try { DataMap param = new
	 * DataMap(request);
	 *
	 * // 페이지 관련 param.put("currentPage", 1); int totalCnt = 1;
	 *
	 * // 조건 관련 param.put("mdTalkSeq", param.getString("mdTalkSeq"));
	 *
	 * List<PSCMPRD0040DTLVO> list = pscmprd0040Service
	 * .selectSelPontDtl(param); rtnMap = JsonUtils.convertList2Json((List)
	 * list, totalCnt, param.getString("currentPage"));
	 *
	 * rtnMap.put("result", true); } catch (Exception e) {
	 * logger.error("error message --> " + e.getMessage()); rtnMap.put("result",
	 * false); rtnMap.put("errMsg", e.getMessage()); }
	 *
	 * return rtnMap;
	 *
	 * }
	 */

	/**
	 * Desc : 셀링포인트 상세 시트 삭제
	 *
	 * @Method Name : deleteSelPont
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */

	/*
	 * // 로직 수정으로 변경
	 *
	 * @RequestMapping("product/deleteSelPont.do") public @ResponseBody
	 * JSONObject deleteSelPont(HttpServletRequest request) throws Exception {
	 * JSONObject jObj = new JSONObject(); int resultCnt = 0; try { resultCnt =
	 * pscmprd0040Service.deleteSelPont(request); if (resultCnt > 0) {
	 * jObj.put("Code", 1); jObj.put( "Message", resultCnt + "건의 " +
	 * messageSource.getMessage( "msg.common.success.request", null,
	 * Locale.getDefault())); } else { jObj.put("Code", 0); jObj.put("Message",
	 * messageSource.getMessage( "msg.common.fail.request", null,
	 * Locale.getDefault())); } } catch (Exception e) { jObj.put("Code", -1);
	 * jObj.put("Message", e.getMessage()); // 처리오류
	 * logger.error("updateSelPontError==>", e); } return
	 * JsonUtils.getResultJson(jObj); }
	 */

	/**
	 * Desc : 셀링포인트 시트 수정
	 *
	 * @Method Name : updateSelPont
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("product/updateSelPont.do")
	public @ResponseBody JSONObject updateSelPont(HttpServletRequest request)
			throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		DataMap dataMap = new DataMap(request);
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);
		dataMap.put("regId", epcLoginVO.getRepVendorId());

		JSONObject jObj = new JSONObject();
		int resultCnt = 0;
		try {
			resultCnt = pscmprd0040Service.updateSelPont(dataMap);
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

}
