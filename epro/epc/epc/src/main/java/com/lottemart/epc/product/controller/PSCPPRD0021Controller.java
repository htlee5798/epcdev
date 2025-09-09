package com.lottemart.epc.product.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.util.DateUtil;
import lcn.module.common.views.AjaxJsonModelHelper;
import lcn.module.framework.property.ConfigurationService;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import xlib.cmc.GridData;
import xlib.cmc.OperateGridData;

import com.lcnjf.util.StringUtil;
import com.lottemart.common.exception.AlertException;
import com.lottemart.common.exception.AppException;
import com.lottemart.common.exception.TopLevelException;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.service.CommonService;
import com.lottemart.epc.product.model.PSCPPRD0021VO;
import com.lottemart.epc.product.service.PSCPPRD0021Service;

/**
 * 
 * @author jyLim
 * @Class : com.lottemart.bos.product.controller
 * @Description : 통계 > 업체상품 수정요청
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *     수정일      	 	    수정자                                         수정내용
 *  -----------    	--------    ---------------------------
 * 2012. 3. 9.	 	jyLim
 * @version : 1.0
 * </pre>
 */
@Controller("PSCPPRD0021Controller")
public class PSCPPRD0021Controller {

	private static final Logger logger = LoggerFactory.getLogger(PSCPPRD0021Controller.class);

	@Autowired
	private PSCPPRD0021Service pscpprd0021service;

	@Autowired
	private ConfigurationService config;

	@Autowired
	MessageSource messageSource;

	@Autowired
	private CommonService commonService;

	/**
	 * @see dafault
	 * @Locaton : com.lottemart.bos.product.controller
	 * @MethodName : dafault
	 * @author : jyLim
	 * @Description : 업체상품 수정요청 탭페이지
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/prodChgHist.do")
	public String dafault(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "product/internet/PSCPPRD0021";
	}

	/**
	 * @see selectImage
	 * @Locaton : com.lottemart.bos.product.controller
	 * @MethodName : totalTab
	 * @author : jyLim
	 * @Description : 업체상품 수정요청 이미지탭
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/product/selectProdImgChgHistForm.do")
	public String selectImageForm(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		//if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
		//	logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		//}

		// 협력업체콤보
		request.setAttribute("epcLoginVO", epcLoginVO);

		// 거래형태콤보
		Map<String, String> map = new HashMap<String, String>();
		map.put("majorCd", "PRD34");
		List<DataMap> statusCdList = commonService.selectTetCodeList(map);
		request.setAttribute("statusCdList", statusCdList);

		// 헤더정보 셋팅
		formHeaderSet(request);

		return "product/internet/PSCPPRD002101";
	}

	/**
	 * @see selectDescForm
	 * @Locaton : com.lottemart.bos.product.controller
	 * @MethodName : totalTab
	 * @author : jyLim
	 * @Description : 업체상품 수정요청 상세설명탭
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/product/selectProdDescChgHistForm.do")
	public String selectDescForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		//if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
		//	logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		//}

		// 협력업체콤보
		request.setAttribute("epcLoginVO", epcLoginVO);

		// 거래형태콤보
		Map<String, String> map = new HashMap<String, String>();
		map.put("majorCd", "PRD34");
		List<DataMap> statusCdList = commonService.selectTetCodeList(map);
		request.setAttribute("statusCdList", statusCdList);

		// 헤더정보 셋팅
		formHeaderSet(request);

		return "product/internet/PSCPPRD002102";
	}

	/**
	 * @see selectProdAddForm
	 * @Locaton : com.lottemart.bos.product.controller
	 * @MethodName : totalTab
	 * @author : jyLim
	 * @Description : 업체상품 수정요청 전상법탭
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/product/selectProdAddChgHistForm.do")
	public String selectProdAddForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		//if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
		//	logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		//}

		// 협력업체콤보
		request.setAttribute("epcLoginVO", epcLoginVO);

		// 거래형태콤보
		Map<String, String> map = new HashMap<String, String>();
		map.put("majorCd", "PRD34");
		List<DataMap> statusCdList = commonService.selectTetCodeList(map);
		request.setAttribute("statusCdList", statusCdList);

		// 헤더정보 셋팅
		formHeaderSet(request);

		return "product/internet/PSCPPRD002103";
	}

	/**
	 * @see selectProdAddForm
	 * @Locaton : com.lottemart.bos.product.controller
	 * @MethodName : totalTab
	 * @author : jyLim
	 * @Description : 업체상품 수정요청 전상법탭
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/product/selectProdMstChgHistForm.do")
	public String selectProdMstForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		//if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
		//	logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		//}

		// 협력업체콤보
		request.setAttribute("epcLoginVO", epcLoginVO);

		// 거래형태콤보
		Map<String, String> map = new HashMap<String, String>();
		map.put("majorCd", "PRD34");
		List<DataMap> statusCdList = commonService.selectTetCodeList(map);
		request.setAttribute("statusCdList", statusCdList);

		// 헤더정보 셋팅
		formHeaderSet(request);

		return "product/internet/PSCPPRD002107";
	}

	public void formHeaderSet(HttpServletRequest request) {
		String stDt = DateUtil.getToday();
		String endDt = DateUtil.formatDate(stDt, "-");
		stDt = DateUtil.formatDate(DateUtil.addDay(stDt, -7), "-");

		String prodCd = StringUtils.defaultIfEmpty(request.getParameter("prodCd"), "");
		String prodNm = StringUtils.defaultIfEmpty(request.getParameter("prodNm"), "");
		String startDate = StringUtils.defaultIfEmpty(request.getParameter("startDate"), stDt);
		String endDate = StringUtils.defaultIfEmpty(request.getParameter("endDate"), endDt);
		String statusCd = StringUtils.defaultIfEmpty(request.getParameter("statusCd"), "");

		request.setAttribute("prodCd", prodCd);
		request.setAttribute("prodNm", prodNm);
		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);
		request.setAttribute("statusCd", statusCd);
	}

	/**
	 * @see selectImage
	 * @Locaton : com.lottemart.bos.product.controller
	 * @MethodName : totalTab
	 * @author : jyLim
	 * @Description : 업체상품 수정요청 이미지탭
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/product/selectProdImgChgHist.do")
	public @ResponseBody Map selectImage(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map rtnMap = new HashMap<String, Object>();

		try {
			String sessionKey = config.getString("lottemart.epc.session.key");
			EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

			// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
			//if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
			//	logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
			//}

			DataMap param = new DataMap(request);

			ArrayList<String> aryList = null;

			String rowPerPage = param.getString("rowsPerPage");
			String currentPage = param.getString("currentPage");
			int rowPage = Integer.parseInt(rowPerPage);
			int currPage = Integer.parseInt(currentPage);

			// 페이징
			String rowsPerPage = StringUtil.null2str(rowPerPage, config.getString("count.row.per.page"));
			int startRow = ((Integer.parseInt(currentPage) - 1) * Integer.parseInt(rowsPerPage)) + 1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) - 1;
			param.put("startRow", Integer.toString(startRow));
			param.put("endRow", Integer.toString(endRow));
			param.put("startDate", param.getString("startDate").replaceAll("-", ""));
			param.put("endDate", param.getString("endDate").replaceAll("-", ""));

			// 선택한 vendorId가null 이거나 repVendorId ( 대표협력업체 ) 일때 협력사 전체 검색
			if (epcLoginVO != null && (param.getString("vendorId") == null || epcLoginVO.getRepVendorId().equals(param.getString("vendorId")) || "".equals(param.getString("vendorId")))) {
				aryList = new ArrayList<String>();

				for (int i = 0; i < epcLoginVO.getVendorId().length; i++) {
					aryList.add(epcLoginVO.getVendorId()[i]);
				}

				param.put("vendorId", aryList);
			} else {
				aryList = new ArrayList<String>();
				aryList.add(param.getString("vendorId"));
				param.put("vendorId", aryList);
			}

			// 선택한 vendor가 openappi 업체일 경우 전체 검색
			List<EpcLoginVO> openappiVendorId = commonService.selectOpenappiVendor();
			for (int l = 0; openappiVendorId.size() > l; l++) {
				if (openappiVendorId.get(l).getRepVendorId().equals(param.getString("vendorId").replace("[", "").replace("]", "").trim())) {
					aryList = new ArrayList<String>();

					for (int a = 0; a < epcLoginVO.getVendorId().length; a++) {
						aryList.add(epcLoginVO.getVendorId()[a]);
						param.put("vendorId", aryList);
					}
				}
			}

			// 데이터 조회
			List<DataMap> list = pscpprd0021service.selectProdImgChgHist(param);

			int size = list.size();

			int totalCnt = 0;
			if (size > 0) {
				totalCnt = size;
			}

			rtnMap = JsonUtils.convertList2Json((List) list, totalCnt, currentPage);

			// 처리성공
			rtnMap.put("result", true);

		} catch (AppException | TopLevelException | AlertException e) {
			logger.error("error getMessage --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		} catch (Exception e) {
			logger.error("error --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("Message", "조회시 오류가 발생하였습니다.\n"+e.getClass().getName());
			rtnMap.put("errMsg", "조회시 오류가 발생하였습니다.");
		}

		return rtnMap;
	}

	/**
	 * @see selectImage
	 * @Locaton : com.lottemart.bos.product.controller
	 * @MethodName : totalTab
	 * @author : jyLim
	 * @Description : 업체상품 수정요청 이미지탭
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/product/selectProdDescChgHist.do")
	public @ResponseBody Map selectDesc(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map rtnMap = new HashMap<String, Object>();

		try {
			String sessionKey = config.getString("lottemart.epc.session.key");
			EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

			// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
			//if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
			//	logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
			//}

			DataMap param = new DataMap(request);

			// 요청객체 획득
			ArrayList<String> aryList = null;
			// 결과객체 생성

			// 페이징
			String rowPerPage = param.getString("rowsPerPage");
			String currentPage = param.getString("currentPage");
			int rowPage = Integer.parseInt(rowPerPage);
			int currPage = Integer.parseInt(currentPage);

			// 페이징
			String rowsPerPage = StringUtil.null2str(rowPerPage, config.getString("count.row.per.page"));
			int startRow = ((Integer.parseInt(currentPage) - 1) * Integer.parseInt(rowsPerPage)) + 1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) - 1;
			param.put("startRow", Integer.toString(startRow));
			param.put("endRow", Integer.toString(endRow));
			param.put("startDate", param.getString("startDate").replaceAll("-", ""));
			param.put("endDate", param.getString("endDate").replaceAll("-", ""));

			// 선택한 vendorId가null 이거나 repVendorId ( 대표협력업체 ) 일때 협력사 전체 검색
			if (epcLoginVO != null && (param.getString("vendorId") == null || epcLoginVO.getRepVendorId().equals(param.getString("vendorId")) || "".equals(param.getString("vendorId")))) {
				aryList = new ArrayList<String>();

				for (int i = 0; i < epcLoginVO.getVendorId().length; i++) {
					aryList.add(epcLoginVO.getVendorId()[i]);
				}
				param.put("vendorId", aryList);
			} else {
				aryList = new ArrayList<String>();
				aryList.add(param.getString("vendorId"));
				param.put("vendorId", aryList);
			}

			// 선택한 vendor가 openappi 업체일 경우 전체 검색
			List<EpcLoginVO> openappiVendorId = commonService.selectOpenappiVendor();
			for (int l = 0; openappiVendorId.size() > l; l++) {
				if (openappiVendorId.get(l).getRepVendorId().equals(param.getString("vendorId").replace("[", "").replace("]", "").trim())) {
					aryList = new ArrayList<String>();

					for (int a = 0; a < epcLoginVO.getVendorId().length; a++) {
						aryList.add(epcLoginVO.getVendorId()[a]);
						param.put("vendorId", aryList);
					}
				}
			}

			// 데이터 조회
			List<DataMap> list = pscpprd0021service.selectProdDescChgHist(param);
			int size = list.size();
			int totalCnt = 0;
			if (size > 0) {
				totalCnt = size;
			}

			rtnMap = JsonUtils.convertList2Json((List) list, totalCnt, currentPage);

			// 처리성공
			rtnMap.put("result", true);
		} catch (AppException | TopLevelException | AlertException e) {
			logger.error("error getMessage --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		} catch (Exception e) {
			logger.error("error --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("Message", "조회시 오류가 발생하였습니다.\n"+e.getClass().getName());
			rtnMap.put("errMsg", "조회시 오류가 발생하였습니다.");
		}

		return rtnMap;
	}

	/**
	 * @see selectImage
	 * @Locaton : com.lottemart.bos.product.controller
	 * @MethodName : totalTab
	 * @author : jyLim
	 * @Description : 업체마스터 반려정보
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/product/selectProdMstChgHist.do")
	public @ResponseBody Map selectProdMst(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map rtnMap = new HashMap<String, Object>();

		try {
			String sessionKey = config.getString("lottemart.epc.session.key");
			EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

			// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
			//if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
			//	logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
			//}

			DataMap param = new DataMap(request);

			// 요청객체 획득
			ArrayList<String> aryList = null;

			// 페이징
			String rowPerPage = param.getString("rowsPerPage");
			String currentPage = param.getString("currentPage");
			int rowPage = Integer.parseInt(rowPerPage);
			int currPage = Integer.parseInt(currentPage);

			// 페이징
			String rowsPerPage = StringUtil.null2str(rowPerPage, config.getString("count.row.per.page"));
			int startRow = ((Integer.parseInt(currentPage) - 1) * Integer.parseInt(rowsPerPage)) + 1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) - 1;
			param.put("startRow", Integer.toString(startRow));
			param.put("endRow", Integer.toString(endRow));
			param.put("startDate", param.getString("startDate").replaceAll("-", ""));
			param.put("endDate", param.getString("endDate").replaceAll("-", ""));

			// 선택한 vendorId가null 이거나 repVendorId ( 대표협력업체 ) 일때 협력사 전체 검색
			if (epcLoginVO != null && (param.getString("vendorId") == null || epcLoginVO.getRepVendorId().equals(param.getString("vendorId")) || "".equals(param.getString("vendorId")))) {
				aryList = new ArrayList<String>();

				for (int i = 0; i < epcLoginVO.getVendorId().length; i++) {
					aryList.add(epcLoginVO.getVendorId()[i]);
				}

				param.put("vendorId", aryList);
			} else {
				aryList = new ArrayList<String>();
				aryList.add(param.getString("vendorId"));
				param.put("vendorId", aryList);
			}

			// 선택한 vendor가 openappi 업체일 경우 전체 검색
			List<EpcLoginVO> openappiVendorId = commonService.selectOpenappiVendor();
			for (int l = 0; openappiVendorId.size() > l; l++) {
				if (openappiVendorId.get(l).getRepVendorId().equals(param.getString("vendorId").replace("[", "").replace("]", "").trim())) {
					aryList = new ArrayList<String>();

					for (int a = 0; a < epcLoginVO.getVendorId().length; a++) {
						aryList.add(epcLoginVO.getVendorId()[a]);
						param.put("vendorId", aryList);
					}
				}
			}

			// 데이터 조회
			List<DataMap> list = pscpprd0021service.selectProdMstChgHist(param);
			int size = list.size();
			int totalCnt = 0;
			if (size > 0) {
				totalCnt = size;
			}

			rtnMap = JsonUtils.convertList2Json((List) list, totalCnt, currentPage);

			// 처리성공
			rtnMap.put("result", true);

		} catch (AppException | TopLevelException | AlertException e) {
			logger.error("error getMessage --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		} catch (Exception e) {
			logger.error("error --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("Message", e.getMessage());
			rtnMap.put("errMsg", "조회시 오류가 발생하였습니다.");
		}

		return rtnMap;
	}

	/**
	 * @see selectProdAdd
	 * @Locaton : com.lottemart.bos.product.controller
	 * @MethodName : totalTab
	 * @author : jyLim
	 * @Description : 업체상품 수정요청 이미지탭
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/product/selectProdAddChgHist.do")
	public @ResponseBody Map selectProdAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map rtnMap = new HashMap<String, Object>();

		try {
			String sessionKey = config.getString("lottemart.epc.session.key");
			EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

			// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
			//if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
			//	logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
			//}

			DataMap param = new DataMap(request);

			// 요청객체 획득
			ArrayList<String> aryList = null;

			// 페이징
			String rowPerPage = param.getString("rowsPerPage");
			String currentPage = param.getString("currentPage");
			int rowPage = Integer.parseInt(rowPerPage);
			int currPage = Integer.parseInt(currentPage);

			String startDate = param.getString("startDate").replaceAll("-", "");
			String endDate = param.getString("endDate").replaceAll("-", "");
			if (startDate.compareTo(endDate) > 0) {
				throw new AlertException("시작일자가 종료일자보다 클 수 없습니다.");
			}

			// 페이징
			String rowsPerPage = StringUtil.null2str(rowPerPage, config.getString("count.row.per.page"));
			int startRow = ((Integer.parseInt(currentPage) - 1) * Integer.parseInt(rowsPerPage)) + 1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) - 1;
			param.put("startRow", Integer.toString(startRow));
			param.put("endRow", Integer.toString(endRow));
			param.put("startDate", param.getString("startDate").replaceAll("-", ""));
			param.put("endDate", param.getString("endDate").replaceAll("-", ""));

			// 선택한 vendorId가null 이거나 repVendorId ( 대표협력업체 ) 일때 협력사 전체 검색
			if (epcLoginVO != null && (param.getString("vendorId") == null || epcLoginVO.getRepVendorId().equals(param.getString("vendorId")) || "".equals(param.getString("vendorId")))) {
				aryList = new ArrayList<String>();

				for (int i = 0; i < epcLoginVO.getVendorId().length; i++) {
					aryList.add(epcLoginVO.getVendorId()[i]);
				}

				param.put("vendorId", aryList);
			} else {
				aryList = new ArrayList<String>();
				aryList.add(param.getString("vendorId"));

				param.put("vendorId", aryList);
			}

			// 선택한 vendor가 openappi 업체일 경우 전체 검색
			List<EpcLoginVO> openappiVendorId = commonService.selectOpenappiVendor();
			for (int l = 0; openappiVendorId.size() > l; l++) {
				if (openappiVendorId.get(l).getRepVendorId().equals(param.getString("vendorId").replace("[", "").replace("]", "").trim())) {
					aryList = new ArrayList<String>();

					for (int a = 0; a < epcLoginVO.getVendorId().length; a++) {
						aryList.add(epcLoginVO.getVendorId()[a]);
						param.put("vendorId", aryList);
					}
				}
			}

			// 데이터 조회
			List<DataMap> list = pscpprd0021service.selectProdAddChgHist(param);

			int size = list.size();

			int totalCnt = 0;
			if (size > 0) {
				totalCnt = size;
			}

			rtnMap = JsonUtils.convertList2Json((List) list, totalCnt, currentPage);

			// 처리성공
			rtnMap.put("result", true);

		} catch (AppException | TopLevelException | AlertException e) {
			logger.error("error getMessage --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		} catch (Exception e) {
			logger.error("error --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("Message", "조회시 오류가 발생하였습니다.");
			rtnMap.put("errMsg", "조회시 오류가 발생하였습니다.");
		}

		return rtnMap;
	}

	@RequestMapping("product/imageDetailForm.do")
	public String imageDetailForm(HttpServletRequest request) throws Exception {
		DataMap map = new DataMap();

		String seq = request.getParameter("seq");
		String prodCd = request.getParameter("prodCd");

		map.put("seq", seq);
		map.put("prodCd", prodCd);

		DataMap prodInfo = pscpprd0021service.selectProdInfoView(map);

		request.setAttribute("prodInfo", prodInfo);

		//와이드 이미지이면
		if ("00".equals(prodInfo.getString("PROD_IMG_NO"))) {
			//logger.debug("beforeImg=="+config.getString("online.product.image.url")+"/wide/"+prodInfo.getString("PROD_CD").substring(0, 5) + "/" + prodInfo.getString("PROD_CD").substring(5, 9) + "/" + prodInfo.getString("PROD_CD") + "_" + prodInfo.getString("PROD_IMG_NO") + "_720_405.jpg");
			//logger.debug("afterImg=="+config.getString("online.product.imageTemp.url")+ "/" + prodInfo.getString("PROD_CD") + "/" + prodInfo.getString("SEQ") + "_" + prodInfo.getString("PROD_IMG_NO") + ".jpg");
			request.setAttribute("beforeImg", config.getString("online.product.image.url")+"/wide/"+prodInfo.getString("MD_SRCMK_CD").substring(0, 5) + "/" + prodInfo.getString("PROD_CD").substring(5, 9) + "/" + prodInfo.getString("MD_SRCMK_CD") + "_" + prodInfo.getString("PROD_IMG_NO") + "_720_405.jpg");
			request.setAttribute("afterImg", config.getString("online.product.imageTemp.url")+ "/" + prodInfo.getString("MD_SRCMK_CD") + "/" + prodInfo.getString("SEQ") + "_" + prodInfo.getString("PROD_IMG_NO") + ".jpg");
		} else {
			//logger.debug("beforeImg=="+config.getString("online.product.image.url")+"/"+prodInfo.getString("PROD_CD").substring(0, 5) + "/" + prodInfo.getString("PROD_CD") + "_" + prodInfo.getString("PROD_IMG_NO") + "_500.jpg");
			//logger.debug("afterImg=="+config.getString("online.product.imageTemp.url")+ "/" + prodInfo.getString("PROD_CD") + "/" + prodInfo.getString("SEQ") + "_" + prodInfo.getString("PROD_IMG_NO") + ".jpg");
			request.setAttribute("beforeImg", config.getString("online.product.image.url")+"/"+prodInfo.getString("MD_SRCMK_CD").substring(0, 5) + "/" + prodInfo.getString("MD_SRCMK_CD") + "_" + prodInfo.getString("PROD_IMG_NO") + "_500.jpg");
			request.setAttribute("afterImg", config.getString("online.product.imageTemp.url")+ "/" + prodInfo.getString("MD_SRCMK_CD") + "/" + prodInfo.getString("SEQ") + "_" + prodInfo.getString("PROD_IMG_NO") + ".jpg");
		}
		return "product/internet/PSCPPRD002104";
	}

	@RequestMapping("product/descDetailForm.do")
	public String descDetailForm(HttpServletRequest request) throws Exception {
		DataMap map = new DataMap();

		String seq = request.getParameter("seq");
		String prodCd = request.getParameter("prodCd");

		map.put("seq", seq);
		map.put("prodCd", prodCd);

		DataMap prodInfo = pscpprd0021service.selectProdDesc(map);

		request.setAttribute("prodInfo", prodInfo);
		request.setAttribute("beforeDesc", StringUtils.defaultIfEmpty(prodInfo.getString("BEFORE_DESC"), " "));
		request.setAttribute("afterDesc", StringUtils.defaultIfEmpty(prodInfo.getString("AFTER_DESC"), " "));

		return "product/internet/PSCPPRD002105";
	}

	@RequestMapping("product/addInfoDetailForm.do")
	public String addInfoDetailForm(HttpServletRequest request) throws Exception {
		DataMap map = new DataMap();

		String seq = request.getParameter("seq");
		String prodCd = request.getParameter("prodCd");

		map.put("seq", seq);
		map.put("prodCd", prodCd);

		DataMap prodInfo = pscpprd0021service.selectProdInfoView(map);
		request.setAttribute("prodInfo", prodInfo);

		return "product/internet/PSCPPRD002106";
	}

	@RequestMapping("product/prodAddInfoList_org.do")
	public String prodAddInfoList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		GridData gdRes = null;

		try {
			// 요청객체 획득
			String wiseGridData = request.getParameter("WISEGRID_DATA");
			GridData gdReq = OperateGridData.parse(wiseGridData);

			// 결과객체 생성
			gdRes = OperateGridData.cloneResponseGridData(gdReq);

			String seq = StringUtil.null2str(gdReq.getParam("seq"), "");
			String prodCd = StringUtil.null2str(gdReq.getParam("prodCd"), "");

			DataMap dmMap = new DataMap();

			dmMap.put("seq", seq);
			dmMap.put("prodCd", prodCd);

			List<DataMap> list = null;

			if ("".equals(seq)) {
				gdRes.addParam("mode", "1");
				list = pscpprd0021service.selectProdAddBefore(dmMap);
			} else {
				gdRes.addParam("mode", "2");
				list = pscpprd0021service.selectProdAddAfter(dmMap);
			}

			// 조회된 데이터 가 없는 경우의 처리
			if (list == null || list.size() == 0) {
				gdRes.setMessage("");
				gdRes.setStatus("false");
				request.setAttribute("wizeGridResult", gdRes);
				return "common/wiseGridResult";
			}

			// GridData 셋팅
			int iCnt = list.size();

			for (int i = 0; i < iCnt; i++) {
				DataMap map = list.get(i);
				gdRes.getHeader("INFO_GRP_NM").addValue(map.getString("INFO_GRP_NM"), "");
				gdRes.getHeader("INFO_COL_NM").addValue(map.getString("INFO_COL_NM"), "");
				gdRes.getHeader("INFO_COL_DESC").addValue(map.getString("INFO_COL_DESC"), "");
				gdRes.getHeader("COL_VAL").addValue(map.getString("COL_VAL"), "");
			}

			// 처리성공
			gdRes.setMessage(messageSource.getMessage("msg.common.success.request", null, Locale.getDefault()));
			gdRes.setStatus("true");
		} catch (Exception e) {
			// 작업오류
			gdRes.setMessage(e.getMessage());
			gdRes.setStatus("false");
			logger.error("", e);
		}

		request.setAttribute("wizeGridResult", gdRes);
		return "common/wiseGridResult";
	}

	@RequestMapping(value = "product/prodAddInfoList.do")
	public @ResponseBody Map selectProductSaleSum(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map rtnMap = new HashMap<String, Object>();
		DataMap dmMap = new DataMap(request);

		try {
			List<DataMap> list = null;

			if ("".equals(dmMap.getString("seq"))) {
				list = pscpprd0021service.selectProdAddBefore(dmMap);
			} else {
				list = pscpprd0021service.selectProdAddAfter(dmMap);
			}

			rtnMap = JsonUtils.convertList2Json((List) list, list.size(), null);

			rtnMap.put("result", true);
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}

	@RequestMapping("product/prodAllAprv.do")
	public @ResponseBody Map prodAllAprv(HttpServletRequest request) throws Exception {

		JSONObject jObj = new JSONObject();
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		//Map rtnMap = new HashMap<String, Object>();
		try {
			//DataMap param = new DataMap(request);
			String regId = epcLoginVO.getAdminId();
			String[] chk = request.getParameterValues("CHK");
			String[] seq = request.getParameterValues("SEQ");
			String[] memo = request.getParameterValues("MEMO");
//			String[] typeCd = "004";

//			int rowCount = gdReq.getHeader("CHK").getRowCount();
			PSCPPRD0021VO pbomPRD0046vo = new PSCPPRD0021VO();

			int resultCnt = 0;
			for (int i = 0; i < chk.length; i++) {

				pbomPRD0046vo.setSeq(seq[i]);
				pbomPRD0046vo.setTypeCd("004");
				pbomPRD0046vo.setStatusCd("001");
				pbomPRD0046vo.setModId(regId);

				if ("".equals(memo[i])) {
					pbomPRD0046vo.setMemo("내용없음");
				} else {
					pbomPRD0046vo.setMemo(memo[i]);
				}

				resultCnt = pscpprd0021service.prodAprvStatus(pbomPRD0046vo);
			}

			if (resultCnt > 0) {
				jObj.put("Code", 1);
				jObj.put("Message", resultCnt + "건의 " + messageSource.getMessage("msg.common.success.request", null, Locale.getDefault()));

			} else {
				jObj.put("Code", 0);
				jObj.put("Message", messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault()));
			}

		} catch (Exception e) {
			// 처리오류
			jObj.put("Code", -1);
			jObj.put("Message", e.getMessage());
		}
		return JsonUtils.getResultJson(jObj);
	}

	@RequestMapping("product/prodAprv.do")
	public ModelAndView prodAprv(HttpServletRequest request) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		String regId = epcLoginVO.getAdminId();
		String seq = StringUtils.defaultIfEmpty(request.getParameter("seq"), "");
		//String memo = StringUtils.defaultIfEmpty(request.getParameter("memo"), "내용없음");
		String prodCd = StringUtils.defaultIfEmpty(request.getParameter("prodCd"), "");
		String typeCd = StringUtils.defaultIfEmpty(request.getParameter("typeCd"), "");
		//String prodImgNo = StringUtils.defaultIfEmpty(request.getParameter("prodImgNo"), "");
		//String mdSrcmkCd = StringUtils.defaultIfEmpty(request.getParameter("mdSrcmkCd"), "");

		int resultCnt = 0;
		PSCPPRD0021VO pbomPRD0046vo = new PSCPPRD0021VO();
		try {
			pbomPRD0046vo.setSeq(seq);
			pbomPRD0046vo.setProdCd(prodCd);
			pbomPRD0046vo.setStatusCd("001");
			pbomPRD0046vo.setTypeCd(typeCd);
			pbomPRD0046vo.setModId(regId);

			resultCnt = pscpprd0021service.prodAprvStatus(pbomPRD0046vo);

			if (resultCnt > 0) {
				return AjaxJsonModelHelper.create("success");
			} else {
				return AjaxJsonModelHelper.create("fail");
			}

		} catch (Exception e) {
			return AjaxJsonModelHelper.create(e.getMessage()/* "처리중 에러가 발생하였습니다." */);
		}
	}

	// 20180911 상품키워드 입력 기능 추가
	/**
	 * @selectProdKeywordChgHistForm
	 * @상품키워드수정리스트 장표 (탭)
	 * @param request
	 * @param response
	 * @return MV
	 * @throws Exception
	 */
	@RequestMapping("/product/selectProdKeywordChgHistForm.do")
	public String selectProdKeywordChgHistForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		// Session check 실패 : 에러페이지 혹은 로그인패이지로 이동
		//if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
		//	logger.debug("Session check 실패 : 에러페이지 혹은 로그인패이지로 이동");
		//}

		// 협력업체콤보
		request.setAttribute("epcLoginVO", epcLoginVO);

		// 거래형태콤보
		Map<String, String> map = new HashMap<String, String>();
		map.put("majorCd", "PRD34");
		List<DataMap> statusCdList = commonService.selectTetCodeList(map);
		request.setAttribute("statusCdList", statusCdList);

		// 헤더정보 셋팅
		formHeaderSet(request);

		return "product/internet/PSCPPRD002108";
	}

	/**
	 * @selectProdKeywordChgHist
	 * @상품키워드수정리스트
	 * @param request
	 * @param response
	 * @return MV
	 * @throws Exception
	 */
	@RequestMapping("/product/selectProdKeywordChgHist.do")
	public @ResponseBody Map selectProdKeywordChgHist(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map rtnMap = new HashMap<String, Object>();

		try {
			String sessionKey = config.getString("lottemart.epc.session.key");
			EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

			// Session check 실패 : 에러페이지 혹은 로그인패이지로 이동
			//if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
			//	logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
			//}

			DataMap param = new DataMap(request);

			// 요청객체 획득
			ArrayList<String> aryList = null;
			// 결과객체 생성

			// 페이징
			String rowPerPage = param.getString("rowsPerPage");
			String currentPage = param.getString("currentPage");
			int rowPage = Integer.parseInt(rowPerPage);
			int currPage = Integer.parseInt(currentPage);

			// 페이징
			String rowsPerPage = StringUtil.null2str(rowPerPage, config.getString("count.row.per.page"));
			int startRow = ((Integer.parseInt(currentPage) - 1) * Integer.parseInt(rowsPerPage)) + 1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) - 1;
			param.put("startRow", Integer.toString(startRow));
			param.put("endRow", Integer.toString(endRow));
			param.put("startDate", param.getString("startDate").replaceAll("-", ""));
			param.put("endDate", param.getString("endDate").replaceAll("-", ""));

			// 선택한 vendorId가null 이거나 repVendorId ( 대표협력업체 ) 일때 협력사 전체 검색
			if (epcLoginVO != null && (param.getString("vendorId") == null || epcLoginVO.getRepVendorId().equals(param.getString("vendorId")) || "".equals(param.getString("vendorId")))) {
				aryList = new ArrayList<String>();

				for (int i = 0; i < epcLoginVO.getVendorId().length; i++) {
					aryList.add(epcLoginVO.getVendorId()[i]);
				}

				param.put("vendorId", aryList);
			} else {
				aryList = new ArrayList<String>();
				aryList.add(param.getString("vendorId"));

				param.put("vendorId", aryList);
			}

			// 선택한 vendor가 openappi 업체일 경우 전체 검색
			List<EpcLoginVO> openappiVendorId = commonService.selectOpenappiVendor();

			for (int l = 0; openappiVendorId.size() > l; l++) {
				if (openappiVendorId.get(l).getRepVendorId().equals(param.getString("vendorId").replace("[", "").replace("]", "").trim())) {
					aryList = new ArrayList<String>();

					for (int a = 0; a < epcLoginVO.getVendorId().length; a++) {
						aryList.add(epcLoginVO.getVendorId()[a]);
						param.put("vendorId", aryList);
					}
				}
			}

			// 데이터 조회
			List<DataMap> list = pscpprd0021service.selectProdKeywordChgHist(param);
			int size = list.size();
			int totalCnt = 0;
			if (size > 0) {
				totalCnt = size;
			}

			rtnMap = JsonUtils.convertList2Json((List) list, totalCnt, currentPage);

			// 처리성공
			rtnMap.put("result", true);
		} catch (AppException | TopLevelException | AlertException e) {
			logger.error("error getMessage --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		} catch (Exception e) {
			logger.error("error message : " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("Message", e.getMessage());
			rtnMap.put("errMsg", "조회시 오류가 발생하였습니다.");
		}

		return rtnMap;
	}

	@RequestMapping("product/keywordChgDetailForm.do")
	public String keywordDetailForm(HttpServletRequest request) throws Exception {
		DataMap map = new DataMap();

		String seq = request.getParameter("seq");
		String prodCd = request.getParameter("prodCd");

		map.put("seq", seq);
		map.put("prodCd", prodCd);

		DataMap prodInfo = pscpprd0021service.selectProdInfoView(map);
		request.setAttribute("prodInfo", prodInfo);

		return "product/internet/PSCPPRD002109";
	}

	@RequestMapping(value = "product/keywordChgDetailList.do")
	public @ResponseBody Map keywordInfoList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map rtnMap = new HashMap<String, Object>();
		DataMap dmMap = new DataMap(request);

		try {
			List<DataMap> list = null;

			if ("".equals(dmMap.getString("seq"))) {
				list = pscpprd0021service.selectProdKeywordChgBefore(dmMap);
			} else {
				list = pscpprd0021service.selectProdKeywordChgAfter(dmMap);
			}

			rtnMap = JsonUtils.convertList2Json((List) list, list.size(), null);

			rtnMap.put("result", true);
		} catch (Exception e) {
			logger.error("error message : " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}
	// 20180911 상품키워드 입력 기능 추가
}
