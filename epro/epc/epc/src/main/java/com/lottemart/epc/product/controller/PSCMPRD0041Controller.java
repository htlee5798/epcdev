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
import javax.servlet.http.HttpServletResponse;

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

import com.lcnjf.util.StringUtil;
import com.lottemart.common.code.service.CommonCodeService;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.util.LoginUtil;
import com.lottemart.epc.product.model.PSCMPRD0041DtlVO;
import com.lottemart.epc.product.model.PSCMPRD0041VO;
import com.lottemart.epc.product.service.PSCMPRD0041Service;

/**
 * @Class Name : PSCMPRD0041Controller.java
 * @Description :
 * @Modification Information
 *
 *               <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2016. 5. 23. 오후 1:46:40 UNI
 *
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Controller("pscmprd0041Controller")
public class PSCMPRD0041Controller {

	private static final Logger logger = LoggerFactory
			.getLogger(PSCMPRD0041Controller.class);

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private CommonCodeService commonCodeService;
	@Autowired
	private PSCMPRD0041Service pscmprd0041Service;
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
	 * @Method Name : selectSheet
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("product/selectSheet.do")
	public @ResponseBody Map selectSheet(HttpServletRequest request)
			throws Exception {
		Map rtnMap = new HashMap<String, Object>();

		try {

			DataMap param = new DataMap(request);

			// 리스트 조회
			List<PSCMPRD0041DtlVO> list = pscmprd0041Service.selectSheet(param);

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
	 * Desc : 협력사공지 등록폼
	 *
	 * @Method Name : createVendorMgr
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("product/createVendorMgr.do")
	public String createVendorMgr(HttpServletRequest request,
			@ModelAttribute("loginVo") EpcLoginVO epcLoginVO) throws Exception {

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

		// startDate
		String startDate = dateFormat.format(calVal.getTime());

		// endDate를 현재날짜 일주일 전으로 셋팅
		calVal.add(Calendar.DAY_OF_MONTH, +7);
		String endDate = dateFormat.format(calVal.getTime());

		// 검색기간 셋팅
		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);

		// 문의유형공통코드 조회
		List<DataMap> codeList = commonCodeService.getCodeList("SM341");
		request.setAttribute("codeList", codeList);

		return "product/PSCMPRD004101";
	}

	/**
	 * Desc : 협력사 관리 디테일
	 *
	 * @Method Name : dtlVendorMgr
	 * @param request
	 * @param epcLoginVO
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("product/dtlVendorMgr.do")
	public String dtlVendorMgr(HttpServletRequest request,
			@ModelAttribute("loginVo") EpcLoginVO epcLoginVO) throws Exception {

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}

		// 로그인 아이디
		request.setAttribute("epcLoginVO", epcLoginVO);
		// VendorId
		request.setAttribute("regId", epcLoginVO.getRepVendorId());

		// 문의유형공통코드 조회
		List<DataMap> codeList = commonCodeService.getCodeList("SM341");
		request.setAttribute("codeList", codeList);

		// 상세 SEQ
		String recommSeq = request.getParameter("announSeq");
		request.setAttribute("announSeq", recommSeq);
		try {
			// 협력사 공지 디테일
			PSCMPRD0041VO qnatViewInfo = pscmprd0041Service .selectVendorMgrView(recommSeq);
			request.setAttribute("data", qnatViewInfo);

		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
		}

		return "product/PSCMPRD004102";
	}

	/**
	 * Desc : 협력사공지 초기세팅
	 *
	 * @Method Name : VendorMgr
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("product/VendorMgr.do")
	public String VendorMgr(HttpServletRequest request,
			@ModelAttribute("loginVo") EpcLoginVO epcLoginVO) throws Exception {

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

		// startDate
		String startDate = dateFormat.format(calVal.getTime());

		// endDate를 현재날짜 일주일 전으로 셋팅
		calVal.add(Calendar.DAY_OF_MONTH, +7);
		String endDate = dateFormat.format(calVal.getTime());

		// 검색기간 셋팅
		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);

		return "product/PSCMPRD0041";
	}

	/**
	 * Desc : 협력사 공지 검색
	 *
	 * @Method Name : selectVendorMgr
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("product/selectVendorMgr.do")
	public @ResponseBody Map selectVendorMgr(HttpServletRequest request,
			@ModelAttribute("loginVo") EpcLoginVO epcLoginVO) throws Exception {
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
			// row 설정
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
			param.put("selectSarch", param.getString("selectSarch"));
			param.put("titleNm", param.getString("titleNm"));
			param.put("seachType", param.getString("seachType"));
			param.put("searchNm", param.getString("searchNm"));

			// 전체 조회 건수
			int totalCnt = pscmprd0041Service.selectVendorMgrCnt((Map) param);

			param.put("totalCount", Integer.toString(totalCnt));

			// 리스트 조회
			List<PSCMPRD0041VO> list = pscmprd0041Service
					.selectVendorMgr(param);

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
	 * Desc : 협력사 공지 사용 승인해제
	 *
	 * @Method Name : updateVendorMgr
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("product/updateVendorMgr.do")
	public @ResponseBody JSONObject updateVendorMgr(HttpServletRequest request,
			@ModelAttribute("loginVo") EpcLoginVO epcLoginVO) throws Exception {
		JSONObject jObj = new JSONObject();
		int resultCnt = 0;
		try {
			// 로그인 관련
			if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
				logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
			}

			// SHEET 데이터
			String[] announSeq = request.getParameter("announSeq_chkValArr").split(",");
			String modId = epcLoginVO.getRepVendorId();
			// 2020.06.30 사용중지 기능만 사용
			String apryYn = "N";

			// 데이터 가공
			for (int i = 0; i < announSeq.length; i++) {
				PSCMPRD0041VO pscmprd0041vo = new PSCMPRD0041VO();
				pscmprd0041vo.setAnnounSeq(announSeq[i]);
				pscmprd0041vo.setModId(modId);
				pscmprd0041vo.setApryYn(apryYn);
				pscmprd0041vo.setModId(modId);
				resultCnt += pscmprd0041Service.updateVendorMgr(pscmprd0041vo);
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
			logger.error("updateSelPontError==>", e);
		}
		return JsonUtils.getResultJson(jObj);
	}

	/**
	 * Desc : 협력사 공지사항 등록
	 *
	 * @Method Name : saveVondorMgr
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 *//*
	@RequestMapping("product/saveVondorMgrTest.do")
	public void saveVondorMgr(HttpServletRequest request,
			@ModelAttribute("loginVo") EpcLoginVO epcLoginVO) throws Exception {
		DataMap param = new DataMap(request);
		param.put("startDate", param.getString("startDate").replaceAll("-", "")
				+ param.getString("startTime"));
		param.put("endDate", param.getString("endDate").replaceAll("-", "")
				+ param.getString("endTime"));
		param.put("venderId", epcLoginVO.getRepVendorId());

		try {
			// 로그인 관련
			if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
				logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
			}
			// VENDOR_ANNOUN_MGR 저장
			pscmprd0041Service.saveVondorMgr(param);
			logger.info("super save");

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}*/

	/**
	 * Desc : 협력사 공지사항 수정
	 *
	 * @Method Name : updateVondorMgr
	 * @param request
	 * @param epcLoginVO
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("product/updateVondorMgr.do")
	public void updateVondorMgr(HttpServletRequest request,
			@ModelAttribute("loginVo") EpcLoginVO epcLoginVO) throws Exception {
		DataMap param = new DataMap(request);
		param.put("startDate", param.getString("startDate").replaceAll("-", "")
				+ param.getString("startTime"));
		param.put("endDate", param.getString("endDate").replaceAll("-", "")
				+ param.getString("endTime"));
		param.put("venderId", epcLoginVO.getRepVendorId());

		try {
			// 로그인 관련
			if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
				logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
			}
			// VENDOR_ANNOUN_MGR 저장
			pscmprd0041Service.updateVondorMgr(param);
			logger.info("super save");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * Desc : 협력사 공지 저장,수정
	 *
	 * @Method Name : saveVondorMgr
	 * @param request
	 * @param epcLoginVO
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("product/saveVondorMgr.do")
	public @ResponseBody JSONObject saveVondorMgr(
			HttpServletRequest request,
			@ModelAttribute("loginVo") EpcLoginVO epcLoginVO) throws Exception {
		JSONObject jObj = new JSONObject();
		DataMap param = new DataMap(request);
		param.put("startDate", param.getString("startDate").replaceAll("-", "")
				+ param.getString("startTime"));
		param.put("endDate", param.getString("endDate").replaceAll("-", "")
				+ param.getString("endTime"));
		param.put("venderId", epcLoginVO.getRepVendorId());

		param.put("title", URLDecoder.decode(request.getParameter("title"), "UTF-8"));
		param.put("pcContent", URLDecoder.decode(request.getParameter("pcContent"), "UTF-8"));

		logger.debug("title="+URLDecoder.decode(request.getParameter("title"), "UTF-8"));
		int ecfailCnt = 0;
		try {

			// 로그인 관련
			if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
				logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
			}
				
			// 저장 실패한 cnt 값이 리턴
			ecfailCnt = pscmprd0041Service.saveVondorMgr(param);

			if (ecfailCnt > 0) {
				jObj.put("Code", 0);
				jObj.put("Message", ecfailCnt + "건의 " +"상품이 롯데on에 연동되어 있지 않아서 제외되었습니다.");
			} else {
				jObj.put("Code", 1);
				jObj.put(
						"Message", messageSource.getMessage(
										"msg.common.success.request", null,
										Locale.getDefault()));
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
	 * Desc : 협력사 관리 엑셀다운로드
	 *
	 * @Method Name : exportPSCMBRD0014Excel
	 *
	 * @param request
	 *
	 * @param response
	 *
	 * @throws Exception
	 *
	 * @return void
	 */
	@RequestMapping(value = "/product/exportPSCMPRD0041Excel.do")
	public void exportPSCMPRD0041Excel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		try {
			DataMap paramMap = new DataMap(request);
			paramMap.put("announSeq", paramMap.getString("announSeq"));
			List<Map<Object, Object>> list = pscmprd0041Service
					.selectPscmprd0041Export(paramMap);

			JsonUtils.IbsExcelDownload((List) list, request, response);
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
		}
	}

}
