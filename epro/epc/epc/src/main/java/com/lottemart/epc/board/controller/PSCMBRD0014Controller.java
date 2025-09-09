/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2016. 05 31. 오후 2:30:50
 * @author      : choi
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.board.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.util.DateUtil;
import lcn.module.common.util.StringUtil;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lottemart.common.code.service.CommonCodeService;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.board.model.PSCMBRD0014TemVO;
import com.lottemart.epc.board.model.PSCMBRD0014VO;
import com.lottemart.epc.board.service.PSCMBRD0014Service;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.util.LoginUtil;

/**
 * @Class Name : PSCMBRD0014Controller.java
 * @Description : 상품Q&A Controller 클래스
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2016. 4. 29. 오후 2:56:11 choi
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Controller
public class PSCMBRD0014Controller {

	private static final Logger logger = LoggerFactory
			.getLogger(PSCMBRD0014Controller.class);

	@Autowired
	private ConfigurationService config;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private CommonCodeService commonCodeService;
	@Autowired
	PSCMBRD0014Service pscmbrd0014Service;

	/**
	 * Desc : 상품 Q&A 목록
	 * 
	 * @Method Name : selectQnaList
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/board/selectQnaList.do")
	public String selectQnaList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		//VendorId
		request.setAttribute("epcLoginVO", epcLoginVO);

		String endDate = DateUtil.getToday("yyyy-MM-dd");
		String startDate = DateUtil.formatDate(DateUtil.addDay(endDate, -7),
				"-");

		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);

		// 문의유형공통코드 조회
		List<DataMap> codeList = commonCodeService.getCodeList("QA030");
		request.setAttribute("codeList", codeList);

		return "board/PSCMBRD0014";
	}

	/**
	 * Desc : 상품 Q&A 검색
	 * 
	 * @Method Name : selectQnaSearch
	 * @param request
	 * @return
	 * @throws Exception
	 * @return Map
	 */
	@RequestMapping(value = "/board/selectQnaSearch.do")
	public @ResponseBody Map selectQnaSearch(HttpServletRequest request)
			throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		logger.info("##########################################"
				+ "getAdminId : " + epcLoginVO.getAdminId() + "getLoginNm : "
				+ epcLoginVO.getLoginNm() + "getRepVendorId : "
				+ epcLoginVO.getRepVendorId());
		Map rtnMap = new HashMap<String, Object>();

		try {
			DataMap param = new DataMap(request);

			// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
			if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
				logger.debug("===== > Sessiomcheck 실패<===== ");
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
			param.put("userSrch", param.getString("userSrch"));
			param.put("userSrchNm", param.getString("userSrchNm"));
			param.put("startDate",
					param.getString("startDate").replaceAll("-", ""));
			param.put("endDate", param.getString("endDate").replaceAll("-", ""));
			param.put("prodSrch", param.getString("prodSrch"));
			param.put("prodSrchNm", param.getString("prodSrchNm"));
			param.put("qstTypeSrch", param.getString("qstTypeSrch"));
			param.put("procSrchYn", param.getString("procSrchYn"));
			param.put("searchVendorId", param.getString("searchVendorId"));

			// 전체 조회 건수
			int totalCnt = pscmbrd0014Service.selectQnaTotalCnt((Map) param);

			param.put("totalCount", Integer.toString(totalCnt));

			// 리스트 조회
			List<PSCMBRD0014VO> list = pscmbrd0014Service .selectQnaSearch((Map) param);
			rtnMap = JsonUtils.convertList2Json((List) list, totalCnt, param.getString("currentPage"));

			rtnMap.put("result", true);
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}

	/**
	 * Desc : 상품 Q&A 게시판 상세 페이지
	 * 
	 * @Method Name : qnaView
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @return String
	 */

	@RequestMapping(value = "/board/qnatView.do")
	public String qnaView(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String recommSeq = request.getParameter("ProdQnaSeq");
		// 상세정보
		try {
			PSCMBRD0014VO qnatViewInfo = pscmbrd0014Service
					.selectQnaView(recommSeq);
			request.setAttribute("data", qnatViewInfo);
			List<PSCMBRD0014TemVO> temComList = pscmbrd0014Service.temComList();
			request.setAttribute("temComList", temComList);
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
		}
		return "board/PSCPBRD001401";
	}

	/**
	 * Desc : 콤보박스 리스트
	 * 
	 * @Method Name : ComBoxList
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("board/ComBoxList.do")
	public @ResponseBody ModelAndView ComBoxList(HttpServletRequest request)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<PSCMBRD0014TemVO> temComList = pscmbrd0014Service.temComList();
		
		
		// joson 결과 생성
		resultMap.put("temComList", temComList);
		return AjaxJsonModelHelper.create(resultMap);
	}

	/**
	 * Desc : 콤보
	 * 
	 * @Method Name : selectTemComboList
	 * @return
	 * @throws Exception
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/board/selectComBox.do")
	public ModelAndView selectComBox(HttpServletRequest request)
			throws Exception {
		String temList = request.getParameter("temList");

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("temList", temList);

		PSCMBRD0014TemVO temComList = pscmbrd0014Service.selectComBox(paramMap);
		// joson 결과 생성
		DataMap resultMap = new DataMap();
		resultMap.put("temComList", temComList);
		return AjaxJsonModelHelper.create(resultMap);
	}

	/**
	 * Desc : 상품 Q&A 게시판 답변 달기
	 * 
	 * @Method Name : qnaupdate
	 * @param request
	 * @param response
	 * @throws Exception
	 * @return void
	 */
	//@4UP 수정 RequestMapping 공백제거
	@RequestMapping(value = "/board/qnaAnsUpdate.do")
	public ModelAndView qnaupdate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		String message = "";
		String recommSeq = request.getParameter("recommSeq");// 글번호
		String ansContent = request.getParameter("ansContent");// 답변

		PSCMBRD0014VO pscmbrd0014vo = new PSCMBRD0014VO();
		pscmbrd0014vo.setProdQnaSeq(recommSeq);
		pscmbrd0014vo.setAnsContent(ansContent);
		pscmbrd0014vo.setRegId(epcLoginVO.getAdminId());
		pscmbrd0014vo.setModId(epcLoginVO.getAdminId());

		try {
			int resultCnt = pscmbrd0014Service.qnaAnsUpdate(pscmbrd0014vo);
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

	/**
	 * Desc : 템플릿 저장
	 * 
	 * @Method Name : temAdd
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @return Map
	 */
	//@4UP 수정 RequestMapping 공백제거
	@RequestMapping(value = "/board/temAdd.do")
	public ModelAndView temAdd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		String message = "";
		String ansContent =  request.getParameter("ansContent");// 답변
		String templateTitle = URLDecoder.decode(request.getParameter("templateTitle"), "UTF-8");
				
		//request.getParameter("templateTitle"); // 제목

		PSCMBRD0014TemVO pscmbrd0014TemVO = new PSCMBRD0014TemVO();
		pscmbrd0014TemVO.setTemplateContent(ansContent);
		pscmbrd0014TemVO.setTemplateTitle(templateTitle);
		pscmbrd0014TemVO.setRegId(epcLoginVO.getRepVendorId());

		try {
			int resultCnt = pscmbrd0014Service.temAdd(pscmbrd0014TemVO);
			logger.debug("test");
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

	/**
	 * Desc : 템플릿 삭제
	 * 
	 * @Method Name : temDelete
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @return ModelAndView
	 */
	//@4UP 수정 RequestMapping 공백제거
	@RequestMapping(value = "/board/temDelete.do")
	public ModelAndView temDelete(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String message = "";
		String templateSeq = request.getParameter("templateSeq"); // seq

		PSCMBRD0014TemVO pscmbrd0014TemVO = new PSCMBRD0014TemVO();
		pscmbrd0014TemVO.setTemplateSeq(templateSeq);

		try {
			int resultCnt = pscmbrd0014Service.temDelete(pscmbrd0014TemVO);
			logger.debug("test");
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

	/**
	 * Desc : 상품 Q&A 리스트 엑셀다운로드
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
	@RequestMapping(value = "/board/exportPSCMBRD0014Excel.do")
	public void exportPSCMBRD0014Excel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		try {
			DataMap paramMap = new DataMap(request);

			// 협력사코드 전체를 선택한 경우 로그인 세션에 있는 협력사 코드 전체를 설정한다.
			if ("".equals(paramMap.getString("searchVendorId"))) {
				paramMap.put("vendorId", LoginUtil.getVendorList(epcLoginVO)); // 협력업체코드
			} else {
				ArrayList<String> vendorList = new ArrayList<String>();
				vendorList.add(paramMap.getString("searchVendorId"));
				paramMap.put("vendorId", vendorList); // 협력업체코드
			}

			paramMap.put("userSrch", paramMap.getString("userSrch"));
			paramMap.put("userSrchNm", paramMap.getString("userSrchNm"));
			paramMap.put("startDate", paramMap.getString("startDate")
					.replaceAll("-", ""));
			paramMap.put("endDate",
					paramMap.getString("endDate").replaceAll("-", ""));
			paramMap.put("prodSrch", paramMap.getString("prodSrch"));
			paramMap.put("prodSrchNm", paramMap.getString("prodSrchNm"));
			paramMap.put("qstTypeSrch", paramMap.getString("qstTypeSrch"));
			paramMap.put("procSrchYn", paramMap.getString("procSrchYn"));
			paramMap.put("searchVendorId", paramMap.getString("searchVendorId"));

			List<Map<Object, Object>> list = pscmbrd0014Service
					.selectPscmbrd0014Export(paramMap);

			JsonUtils.IbsExcelDownload((List) list, request, response);
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
		}
	}

}
