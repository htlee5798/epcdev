package com.lottemart.epc.product.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lcnjf.util.StringUtil;
import com.lottemart.common.exception.AlertException;
import com.lottemart.common.exception.AppException;
import com.lottemart.common.exception.TopLevelException;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.service.CommonService;
import com.lottemart.epc.product.model.PSCMPRD0015VO;
import com.lottemart.epc.product.service.PSCMPRD0015Service;

import lcn.module.common.views.AjaxJsonModelHelper;
import lcn.module.framework.property.ConfigurationService;
import net.sf.json.JSONObject;

/**
 * @Class Name : PSCMPRD0015Controller
 * @Description : 대표상품코드 리스트를 조회하는 Controller 클래스
 * @Modification Information
 *
 *               <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Controller("pscmprd0015Controller")
public class PSCMPRD0015Controller {

	private static final Logger logger = LoggerFactory.getLogger(PSCMPRD0015Controller.class);

	@Autowired
	private PSCMPRD0015Service pscmprd0015Service;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ConfigurationService config;

	@Autowired
	private CommonService commonService;

	/**
	 * 대표상품코드관리 디폴트 페이지
	 * @see repProdCdForm
	 * @Locaton    : com.lottemart.epc.product.controller
	 * @MethodName  : repProdCdForm
	 * @author     :
	 * @Description : 메뉴 클릭시 뜨는 디폴트 페이지
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/product/selectItemPriceChangeView.do")
	public String repProdCdForm(HttpServletRequest request) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
		Calendar calVal = Calendar.getInstance();
		String endDate = dateFormat.format(calVal.getTime());

		// 시작일자를 현재날짜 한달전으로 셋팅
		calVal.add(Calendar.DAY_OF_MONTH, -31);
		String startDate = dateFormat.format(calVal.getTime());

		// 대분류콤보
		List<DataMap> daeCdList =  commonService.selectDaeCdList();
		request.setAttribute("daeCdList", daeCdList);
		//검색기간 셋팅
		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);
		request.setAttribute("epcLoginVO", epcLoginVO);

		return "product/PSCMPRD0015";
	}

	/**
	 * 디폴트 페이지에서 조회버튼 클릭시 대표상품코드 목록 조회, WISEGRID로 리턴
	 * @see selectRepProdCdList
	 * @Locaton    : com.lottemart.epc.product.controller
	 * @MethodName  : selectRepProdCdList
	 * @author     :
	 * @Description : 대표상품코드 목록 조회
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("product/repProdCd/selectRepProdCdList.do")
	public Map<String, Object> selectRepProdCdList(HttpServletRequest request) throws Exception {

		Map<String, Object> rtnMap = new HashMap<String, Object>();

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		ArrayList<String> aryList = null;

		try {
			DataMap param = new DataMap(request);
			String rowsPerPage = StringUtil.null2str((String) param.get("rowsPerPage"), config.getString("count.row.per.page"));

			int startRow = ((Integer.parseInt((String) param.get("currentPage")) - 1) * Integer.parseInt(rowsPerPage)) + 1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) - 1;

			param.put("currentPage", (String) param.get("currentPage"));
			param.put("startRow", Integer.toString(startRow));
			param.put("endRow", Integer.toString(endRow));
			param.put("rowsPerPage", rowsPerPage);
			param.put("prodCd", (String) param.get("prodCd"));
			param.put("prodNm", (String) param.get("prodNm"));
			param.put("endDate", (String) param.get("endDate"));
			param.put("startDate", (String) param.get("startDate"));
			param.put("admYn", (String) param.get("admYn"));
			param.put("l1Nm", (String) param.get("selDaeGoods"));
			param.put("optnPrcYn", (String) param.get("optnPrcYn"));

			//param.put("vendorId", (String)param.get("vendorId"));

			// 선택한 vendorId가null 이거나 repVendorId ( 대표협력업체 ) 일때 협력사 전체 검색
			if ((param.getString("vendorId") == null || param.getString("vendorId").equals(epcLoginVO.getRepVendorId())
					|| "".equals(param.getString("vendorId"))) && epcLoginVO != null) {
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

			// 튜너 튜닝 처리 관련 파라미터값
			param.put("hintFlag", "2");
			if ((String) param.get("prodCd") == null || ((String) param.get("prodCd")).length() == 0) {
				if ((String) param.get("prodNm") != null && ((String) param.get("prodNm")).length() > 0) {
					param.put("hintFlag", "1");
				}
			}

			// 데이터 조회
//			List<Map<String, Object>> resultList = pscmprd0015Service.selectRepProdCdList(param);
			List<PSCMPRD0015VO> resultList = pscmprd0015Service.selectRepProdCdList(param);

			int totCnt = 0;
			if (resultList.size() != 0) {
				totCnt = Integer.valueOf(resultList.get(0).getCnt());
			}
			rtnMap = JsonUtils.convertList2Json((List) resultList, totCnt, (String) param.get("currentPage"));

			// 성공
			logger.debug("데이터 조회 완료");
			rtnMap.put("result", true);

		} catch (AppException | TopLevelException | AlertException e) {
			logger.error("error getMessage --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		} catch (Exception e) {
			// 실패
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", "조회시 오류가 발생하였습니다.");
		}

		logger.debug("================eventPlanMgtSearch_End================");
		return rtnMap;
	}

	/**
	 * Desc : 상품단품 정보를 조회하는 메소드 (현재 승인여부 'N'인 데이터는 제외)
	 *
	 * @Method Name : selectProductItemList
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/product/repProdCd/selectProductItemList.do")
	public @ResponseBody Map selectProductItemList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		Map rtnMap = new HashMap<String, Object>();

		try {
			DataMap param = new DataMap(request);

			String rowPerPage = param.getString("rowsPerPage");
			String currentPage = param.getString("currentPage");
			int rowPage = Integer.parseInt(rowPerPage);
			int currPage = Integer.parseInt(currentPage);
			param.put("startRecord", (currPage - 1) * rowPage + 1);
			param.put("endRecord", currPage * rowPage);

			// 페이징
			String rowsPerPage = StringUtil.null2str(rowPerPage, config.getString("count.row.per.page"));
			int startRow = ((Integer.parseInt(currentPage) - 1) * Integer.parseInt(rowsPerPage)) + 1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) - 1;
			param.put("startRow", Integer.toString(startRow));
			param.put("endRow", Integer.toString(endRow));
			param.put("startDt", param.getString("startDt").replaceAll("-", ""));
			param.put("endDt", param.getString("endDt").replaceAll("-", ""));
			param.put("strCd", config.getString("online.rep.str.cd"));

			String[] selectedProdCd = param.getString("selectedProdCd").split(",");

			ArrayList<String> list = new ArrayList<String>();
			for( String prodcd : selectedProdCd ){
				list.add((prodcd.replace("[", "")).replace("]", "").trim());
			}

			param.put("selectedProdCd", list);

			// 기 요청된 단품 경고 메시지 처리를 위한 조회
			List<DataMap> pscmprd0015DupCount = pscmprd0015Service.selectProductItemDupCount(param);

			String dupCount = pscmprd0015DupCount.get(0).getString("TOTAL_COUNT");

			// 데이터 조회
			List<PSCMPRD0015VO> pscmprd0015List = pscmprd0015Service.selectProductItemList(param);

			int size = 0;

			rtnMap = JsonUtils.convertList2Json((List)pscmprd0015List, size, currentPage);

			// 처리성공
			rtnMap.put("result", true);
			rtnMap.put("dupCount", dupCount);
			rtnMap.put("mode", "choice");
		} catch (AppException | TopLevelException | AlertException e) {
			logger.error("error getMessage --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		} catch (Exception e) {
			logger.error("error --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("Message", e.getMessage());
		}

		return rtnMap;
	}

	/**
	 * 협력사별 대표판매코드 목록 조회
	 * @see selectRepProdCdComboList
	 * @Locaton    : com.lottemart.epc.product.controller
	 * @MethodName  : selectRepProdCdComboList
	 * @author     :
	 * @Description : 인터넷상품코드 변경시 해당 상품의 협력사정보에 연동되는 대표판매코드목록 조회
	 * @param request
	 * @return
	 * @throws Exception
	 */

//	public @ResponseBody Map<String, Object> selectRepProdCdComboList(@RequestBody Map<String, Object> paramMap,  HttpServletRequest request) throws Exception {
	@RequestMapping(value="product/repProdCd/selectRepProdCdList.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> selectRepProdCdDropDnList(HttpServletRequest request) throws Exception {

		String vendorId = request.getParameter("vendorId");
		DataMap paramMap = new DataMap();
		paramMap.put("vendorId", vendorId);

		Map<String, Object> returnMap = new HashMap<String, Object>();

		logger.debug("[vendorId]"+vendorId);
		List<Map<String, Object>> repProdCdComboList = pscmprd0015Service.selectRepProdCdComboList(paramMap);

		// json 결과 생성
//    	DataMap resultMap = new DataMap();
//    	resultMap.put("repProdCdComboList", repProdCdComboList);
    	returnMap.put("repProdCdComboList", repProdCdComboList);

    	return returnMap;
//		return AjaxJsonModelHelper.create(resultMap);
	}

	/**
	 * 대표상품코드 등록 팝업화면
	 * @see viewInsertRepProdCdForm
	 * @Locaton    : com.lottemart.epc.product.controller
	 * @MethodName  : viewInsertRepProdCdForm
	 * @author     :
	 * @Description : 등록버튼 클릭시 대표상품코드 등록 팝업화면을 띄운다.
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/repProdCd/viewInsertRepProdCdForm.do")
	public String viewInsertRepProdCdForm(HttpServletRequest request) throws Exception {

		//List<DataMap> repProdCdComboList = pbomprd0034Service.selectRepProdCdComboList();
		request.setAttribute("IUFlag", "insert");

		return "product/PSCMPRD001501";
	}

	/**
	 * 대표상품코드 수정 팝업화면
	 * @see viewDetailRepProdCdForm
	 * @Locaton    : com.lottemart.epc.product.controller
	 * @MethodName  : viewDetailRepProdCdForm
	 * @author     :
	 * @Description : 대표상품코드 디폴트 페이지에서 인터넷상품코드 클릭시 상세정보 조회, 수정 팝업화면을 띄운다.
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/repProdCd/viewDetailRepProdCdForm.do")
	public String viewDetailRepProdCdForm(HttpServletRequest request) throws Exception {

		String prodCd = request.getParameter("prodCd");
		String repProdCd = request.getParameter("repProdCd");
		String applyStartDy = request.getParameter("applyStartDy");
		String applyEndDy = request.getParameter("applyEndDy");

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("prodCd", prodCd);
		paramMap.put("repProdCd", repProdCd);

		//날짜값에서 '-' 제외
		if ( applyStartDy != null && applyStartDy.length() > 0) {
			applyStartDy = applyStartDy.replaceAll("-", "");
		}

		paramMap.put("applyStartDy", applyStartDy);
		paramMap.put("applyEndDy", applyEndDy);

		//DataMap paramMaps = new DataMap(request);

		/*List<DataMap> repProdCdInfo = (List<DataMap>) pscmprd0015Service.selectRepProdCdInfo(paramMap);*/
		List<DataMap> repProdCdList = pscmprd0015Service.selectRepProdCdInfoList(paramMap);
		request.setAttribute("repProdCdList", repProdCdList);
		request.setAttribute("IUFlag", "update");

		return "product/PSCMPRD001501";
	}

	/**
	 * 협력사별 대표판매코드 목록 조회
	 * @see selectRepProdCdComboList
	 * @Locaton    : com.lottemart.epc.product.controller
	 * @MethodName  : selectRepProdCdComboList
	 * @author     :
	 * @Description : 인터넷상품코드 변경시 해당 상품의 협력사정보에 연동되는 대표판매코드목록 조회
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/repProdCd/selectRepProdCdComboList.do")
	public ModelAndView selectRepProdCdComboList(HttpServletRequest request) throws Exception {

		String vendorId = request.getParameter("vendorId");
		/*Map<String, Object> paramMap = new HashMap<String, Object>();*/
		DataMap paramMap = new DataMap(request);
		paramMap.put("vendorId", vendorId);

		logger.debug("[vendorId]"+vendorId);
		List<Map<String, Object>> repProdCdComboList = pscmprd0015Service.selectRepProdCdComboList(paramMap);

		// json 결과 생성
		DataMap resultMap = new DataMap();
		resultMap.put("repProdCdComboList", repProdCdComboList);

		return AjaxJsonModelHelper.create(resultMap);
	}

	/**
	 * 협력사별 대표판매코드 목록 조회
	 * @see selectRepProdCdComboList
	 * @Locaton    : com.lottemart.epc.product.controller
	 * @MethodName  : selectRepProdCdComboList
	 * @author     :
	 * @Description : 인터넷상품코드 변경시 해당 상품의 협력사정보에 연동되는 대표판매코드목록 조회
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/repProdCd/selectTprStoreItemPriceList.do")
	public ModelAndView selectTprStoreItemPriceList(HttpServletRequest request) throws Exception {

		DataMap paramMap = new DataMap(request);
		List<Map<String, Object>> tprStoreItemPriceList = pscmprd0015Service.selectTprStoreItemPriceList(paramMap);

		// json 결과 생성
		DataMap resultMap = new DataMap();
		resultMap.put("tprStoreItemPriceList", tprStoreItemPriceList);

		return AjaxJsonModelHelper.create(resultMap);
	}


	/**
	 *
	 * @see selectOriginalData
	 * @Locaton    : com.lottemart.epc.product.controller
	 * @MethodName  : selectOriginalData
	 * @author     : jyLim
	 * @Description : 인터넷상품코드 변경시 해당 상품의 변경 전 가격정보를 조회
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/repProdCd/selectOriginalData.do")
	public ModelAndView selectOriginalData(HttpServletRequest request) throws Exception {

		String prodCd = request.getParameter("prodCd");
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("prodCd", prodCd);
		paramMap.put("applyEndDy", "99991231"); //마지막으로 예약된 가격정보조회

		List<DataMap> beforeChangePrc = pscmprd0015Service.getBeforeChangePrc(paramMap);

		// json 결과 생성
		DataMap resultMap = new DataMap();
		resultMap.put("beforeChangePrc", beforeChangePrc);

		return AjaxJsonModelHelper.create(resultMap);
	}

	/**
	 * 대표상품코드 등록
	 * @see insertRepProdCd
	 * @Locaton    : com.lottemart.epc.product.controller
	 * @MethodName  : insertRepProdCd
	 * @author     :
	 * @Description : 대표상품코드 등록 팝업화면에서 저장버튼 클릭시 대표상품코드 정보를 등록, 처리여부 메시지 리턴
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/repProdCd/insertRepProdCd.do")
	public ModelAndView insertRepProdCd(HttpServletRequest request) throws Exception {

		String prodCd = request.getParameter("prodCd");
		String repProdCd = request.getParameter("repProdCd");
		String applyStartDy = request.getParameter("startDate");
		String applyEndDy = request.getParameter("endDate");

		// 배열로 가저오는 데이터
		String[] ItemCdArr = (String[]) request.getParameterValues("ITEM_CD");
		String[] sellPrcArr = (String[]) request.getParameterValues("SELL_PRC");
		String[] currSellPrcArr = (String[]) request.getParameterValues("CURR_SELL_PRC");
		String[] saleRateArr = (String[]) request.getParameterValues("SALE_RATE");
		String[] profitRateArr = (String[]) request.getParameterValues("PROFIT_RATE");
		String[] beforeCurrSellPrcArr = (String[]) request.getParameterValues("BEFORE_CURR_SELL_PRC");
		String[] beforeProfitRtArr = (String[]) request.getParameterValues("BEFORE_PROFIT_RT");
		String[] reqReasonContentArr = (String[]) request.getParameterValues("REQ_REASON_CONTENT");

//		String currSellPrc = request.getParameter("currSellPrc");
//		String profitRate = request.getParameter("profitRt");
		String taxatDivnCd = request.getParameter("taxatDivnCd");
		String optnProdPrcMgrYn = request.getParameter("optnProdPrcMgrYn");

		// 년월일날짜값에서 '-' 제외
		applyStartDy = applyStartDy.replaceAll("-", "");
		applyEndDy = applyEndDy.replaceAll("-", "");

		/*등록업체 ID*/
		String vendorId = request.getParameter("vendorId");

		Map<Object, Object> param = new HashMap<Object, Object>();

		param.put("prodCd", prodCd);
		param.put("repProdCd", repProdCd);
		param.put("applyStartDy", applyStartDy);
		param.put("applyEndDy", applyEndDy);
		param.put("regId", vendorId);
		param.put("modId", vendorId);
		param.put("taxatDivnCd", taxatDivnCd);
		param.put("vendorId", vendorId);
		param.put("optnProdPrcMgrYn", optnProdPrcMgrYn);

		param.put("ItemCdArr", ItemCdArr);
		param.put("sellPrcArr", sellPrcArr);
		param.put("currSellPrcArr", currSellPrcArr);
		param.put("saleRateArr", saleRateArr);
		param.put("profitRateArr", profitRateArr);
		param.put("beforeCurrSellPrcArr", beforeCurrSellPrcArr);
		param.put("beforeProfitRtArr", beforeProfitRtArr);

		param.put("vendorId", vendorId);
		param.put("reqReasonContentArr", reqReasonContentArr);

		try {
			pscmprd0015Service.insertRepProdCd(param);

			return AjaxJsonModelHelper.create("success");
		} catch (Exception e) {
			return AjaxJsonModelHelper.create(e.getMessage()/*"처리중 에러가 발생하였습니다."*/);
		}
	}


	/**
	 * 대표상품코드 수정
	 * @see updateRepProdCd
	 * @Locaton    : com.lottemart.epc.product.controller
	 * @MethodName  : updateRepProdCd
	 * @author     :
	 * @Description : 대표상품코드 수정 팝업화면에서 저장버튼 클릭시 대표상품코드 정보를 수정, 처리여부 메시지 리턴
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/repProdCd/updateRepProdCd.do")
	public ModelAndView updateRepProdCd(HttpServletRequest request) throws Exception {

		String prodCd = request.getParameter("prodCd");
		String repProdCd = request.getParameter("repProdCd");
		String applyStartDy = request.getParameter("startDate");
		String applyEndDy = request.getParameter("endDate");

		// 배열로 가저오는 데이터
		String[] ItemCdArr = (String[]) request.getParameterValues("ITEM_CD");
		String[] reqReasonContentArr = (String[]) request.getParameterValues("REQ_REASON_CONTENT");
		String[] sellPrcArr = (String[]) request.getParameterValues("SELL_PRC");
		String[] currSellPrcArr = (String[]) request.getParameterValues("CURR_SELL_PRC");
		String[] saleRateArr = (String[]) request.getParameterValues("SALE_RATE");
		String[] profitRateArr = (String[]) request.getParameterValues("PROFIT_RATE");
		String[] beforeCurrSellPrcArr = (String[]) request.getParameterValues("BEFORE_CURR_SELL_PRC");
		String[] beforeProfitRtArr = (String[]) request.getParameterValues("BEFORE_PROFIT_RT");

//		String currSellPrc = request.getParameter("currSellPrc");
//		String profitRate = request.getParameter("profitRt");
		String taxatDivnCd = request.getParameter("taxatDivnCd");
		String vendorId = request.getParameter("vendorId");

		String optnProdPrcMgrYn = request.getParameter("optnProdPrcMgrYn");

		// 년월일날짜값에서 '-' 제외
		applyStartDy = applyStartDy.replaceAll("-", "");
		applyEndDy = applyEndDy.replaceAll("-", "");

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		String sessionId = epcLoginVO.getAdminId();

		Map<Object, Object> param = new HashMap<Object, Object>();

		param.put("prodCd", prodCd);
		param.put("repProdCd", repProdCd);
		param.put("applyStartDy", applyStartDy);
		param.put("applyEndDy", applyEndDy);
		param.put("regId", sessionId);
		param.put("modId", sessionId);
		param.put("taxatDivnCd", taxatDivnCd);
		param.put("vendorId", vendorId);
		param.put("optnProdPrcMgrYn", optnProdPrcMgrYn);

		param.put("ItemCdArr", ItemCdArr);
		param.put("reqReasonContentArr", reqReasonContentArr);

		param.put("sellPrcArr", sellPrcArr);
		param.put("currSellPrcArr", currSellPrcArr);
		param.put("saleRateArr", saleRateArr);
		param.put("profitRateArr", profitRateArr);
		param.put("beforeCurrSellPrcArr", beforeCurrSellPrcArr);
		param.put("beforeProfitRtArr", beforeProfitRtArr);

		try {
			pscmprd0015Service.updateRepProdCd(param);

			return AjaxJsonModelHelper.create("success");
		} catch (Exception e) {
			return AjaxJsonModelHelper.create(e.getMessage()/* "처리중 에러가 발생하였습니다." */);
		}
	}

	/**
	 * 대표상품코드 삭제
	 * @see deleteRepProdCd
	 * @Locaton    : com.lottemart.epc.product.controller
	 * @MethodName  : deleteRepProdCd
	 * @author     :
	 * @Description : 디폴트 페이지에서 삭제버튼 클릭시 체크한 대표상품코드 삭제, WISEGRID로 리턴
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/repProdCd/deleteRepProdCd.do")
	public @ResponseBody JSONObject deleteRepProdCd(HttpServletRequest request) throws Exception {

		logger.debug("#####################################################");
		logger.debug("###### deleteRepProdCd_start ######");
		logger.debug("#####################################################");

		JSONObject jObj = new JSONObject();
		int resultCnt = 0;
		try {
			resultCnt = pscmprd0015Service.deleteRepProdCd(request);

			if (resultCnt > 0) {
				jObj.put("Code", 1);
				jObj.put("Message", resultCnt + "건의 "
						+ messageSource.getMessage("msg.common.success.request", null, Locale.getDefault()));
			} else {
				jObj.put("Code", 0);
				jObj.put("Message", messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault()));
			}
		} catch (Exception e) {
			jObj.put("Code", -1);
			jObj.put("Message", e.getMessage());
			// 처리오류
			logger.error("", e);
		}

		return JsonUtils.getResultJson(jObj);
	}

	//------------------------------------------------------------------------------------
	//2012-03-21  아래 부터 대표상품코드일괄등록 기능
	//------------------------------------------------------------------------------------

	/**
	 *
	 * @see viewGrpInsertForm
	 * @Locaton    : com.lottemart.epc.product.controller
	 * @MethodName  : viewGrpInsertForm
	 * @author     : jyLim
	 * @Description : 일괄등록 팝업화면 로드
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/repProdCd/viewGrpInsertForm.do")
	public String viewGrpInsertForm(HttpServletRequest request) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		// 검색기간 셋팅
		request.setAttribute("epcLoginVO", epcLoginVO);

		return "product/PSCMPRD001502";
	}

	/**
	 *
	 * @see selectProdInfo
	 * @Locaton    : com.lottemart.epc.product.controller
	 * @MethodName  : selectProdInfo
	 * @author     : jyLim
	 * @Description : 상품코드가 담긴 엑셀파일을 업로드 하면 각각 상품코드마다 가격정보를 가져온다.
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("product/repProdCd/selectProdInfo.do")
	public Map<String, Object> selectProdInfo(HttpServletRequest request) throws Exception {

		Map<String, Object> rtnMap = new HashMap<String, Object>();

		try {

			DataMap param = new DataMap(request);

			String[] prodCdArr = request.getParameterValues("prodCdArr");
			String[] itemCdArr = request.getParameterValues("itemCdArr");
			param.put("vendorId", (String) param.get("vendorId"));
			param.put("prodCdArr", prodCdArr);
			param.put("itemCdArr", itemCdArr);

			// 데이터 조회
			List<DataMap> resultList = pscmprd0015Service.selectProdInfo(param);

			rtnMap = JsonUtils.convertList2Json((List) resultList, 10000, " 1");

			// 성공
			logger.debug("데이터 조회 완료");
			rtnMap.put("result", true);

		} catch (Exception e) {
			// 실패
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		}

		logger.debug("================PBOMPRD0061Search End================");
		return rtnMap;
	}

	/**
	 *
	 * @see insertRepProdCdList
	 * @Locaton    : com.lottemart.epc.product.controller
	 * @MethodName  : insertRepProdCdList
	 * @author     : jyLim
	 * @Description : 대표상품코드 일괄등록
	 * -------------------------------------------------------
	 *		cdStr
	 *			성공적으로 저장한 상품코드들의 문자열
	 *	 		ex) L000000000036,L000000000040,S000000000016
	 *          파라미터로 배열을 전달할 수 없기 때문에 쉼표(,)로 구분하여 문자열로 바꿔 저장한다
	 *          jsp단에 도착 후 파싱해서 사용한다
	 * -------------------------------------------------------
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("product/repProdCd/insertRepProdCdList.do")
	public JSONObject insertRepProdCdList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//logger.debug("================displayBatchInsert_Start================");

		JSONObject jObj = new JSONObject();
		String resultStr;
		String message;
		Map<String, String> resultMap = null;

		try {
			String[] hdSEQ = request.getParameterValues("SEQ");
			String[] hdPROD_CD = request.getParameterValues("PROD_CD");
			String[] hdITEM_CD = request.getParameterValues("ITEM_CD");
			String[] hdREP_PROD_CD = request.getParameterValues("REP_PROD_CD");
			String[] hdAPPLY_START_DY = request.getParameterValues("APPLY_START_DY");
			String[] hdAPPLY_END_DY = request.getParameterValues("APPLY_END_DY");
			// String[] hdSELL_PRC = request.getParameterValues("SELL_PRC");
			String[] hdCURR_SELL_PRC = request.getParameterValues("CURR_SELL_PRC");
			String[] hdPROFIT_RATE = request.getParameterValues("PROFIT_RATE");
			String[] hdTAXAT_DIVN_CD = request.getParameterValues("TAXAT_DIVN_CD");
			String[] hdOPTN_PROD_PRC_MGR_YN = request.getParameterValues("OPTN_PROD_PRC_MGR_YN");
			String[] hdREQ_REASON_CONTENT = request.getParameterValues("REQ_REASON_CONTENT");
			String[] BEFORE_CURR_SELL_PRC = request.getParameterValues("BEFORE_CURR_SELL_PRC");

			String vendorId = request.getParameter("vendorId");

			int rowCount = hdPROD_CD.length;
			int i;

			List<PSCMPRD0015VO> list = new ArrayList<PSCMPRD0015VO>();
			PSCMPRD0015VO pscmprd0015vo;

			for (i = 0; i < rowCount; i++) {
				pscmprd0015vo = new PSCMPRD0015VO();
				pscmprd0015vo.setVendorId(vendorId);
				pscmprd0015vo.setProdCd(hdPROD_CD[i]);
				pscmprd0015vo.setItemCd(hdITEM_CD[i]);
				pscmprd0015vo.setRepProdCd(hdREP_PROD_CD[i]);
				pscmprd0015vo.setApplyStartDy(getOnlyNumber(hdAPPLY_START_DY[i]));
				pscmprd0015vo.setApplyEndDy(getOnlyNumber(hdAPPLY_END_DY[i]));
				pscmprd0015vo.setSellPrc(BEFORE_CURR_SELL_PRC[i]);
				pscmprd0015vo.setCurrSellPrc(hdCURR_SELL_PRC[i]);
				pscmprd0015vo.setProfitRate(hdPROFIT_RATE[i]);
				pscmprd0015vo.setTaxatDivnCd(hdTAXAT_DIVN_CD[i]);
				pscmprd0015vo.setOptnProdPrcMgrYn(hdOPTN_PROD_PRC_MGR_YN[i]);
				pscmprd0015vo.setReqReasonContent(hdREQ_REASON_CONTENT[i]);
				pscmprd0015vo.setRegId(vendorId);
				pscmprd0015vo.setModId(vendorId);

				resultStr = validate(pscmprd0015vo);

				//----------------------
				// 1.validation failed
				//----------------------
				if (!"".equals(resultStr)) {
					logger.debug("[순번" + hdSEQ[i] + "] " + resultStr); //ex) [순번14] 상품코드가 없습니다.
					message = "[순번" + hdSEQ[i] + "] " + resultStr;
					throw new IllegalArgumentException(message);
				}

				// logger.debug("=========================================================");
				// logger.debug("insertRepProdCdList idx(" + i + " totalCnt " + (rowCount - 1) + ")");
				// logger.debug("=========================================================");
				// logger.debug("[getVendorId]" + pscmprd0015vo.getVendorId());
				// logger.debug("[getProdCd]" + pscmprd0015vo.getProdCd());
				// logger.debug("[getRepProdCd]" + pscmprd0015vo.getRepProdCd());
				// logger.debug("[getApplyStartDy]" + pscmprd0015vo.getApplyStartDy());
				// logger.debug("[getApplyEndDy]" + pscmprd0015vo.getApplyEndDy());
				// logger.debug("[getTaxatDivnCd]" + pscmprd0015vo.getTaxatDivnCd());
				// logger.debug("[getSellPrc]" + pscmprd0015vo.getSellPrc());
				// logger.debug("[currSellPrc]" + pscmprd0015vo.getCurrSellPrc());
				// logger.debug("[profitRate]" + pscmprd0015vo.getProfitRate());
				// logger.debug("[reqReasonContent]" + pscmprd0015vo.getReqReasonContent());
				// logger.debug("=========================================================");

				list.add(pscmprd0015vo);
			}
			resultMap = pscmprd0015Service.insertRepProdCdList(list);

			int resultCnt = Integer.parseInt(resultMap.get("resultCnt"));
			String alertEx = resultMap.get("alertEx");

			if ("Y".equals(alertEx)) {
				// 사용자 AlertException 처리
				jObj.put("Code", -1);
				jObj.put("Message", resultMap.get("message"));
			} else {
				//처리 결과 메세지 생성
				if (resultCnt > 0) {
					jObj.put("Code", 1);
					jObj.put("Message", resultCnt + "건의 " + messageSource.getMessage("msg.common.success.request", null, Locale.getDefault()));
				} else {
					jObj.put("Code", -1);
					jObj.put("Message", resultMap.get("message"));
					logger.debug(resultMap.get("message"));
				}
			}

		} catch (Exception e) {
			jObj.put("Code", -1);
			jObj.put("Message", e.getMessage());

			// 처리오류
			logger.error("", e);
		}

		// logger.debug("================displayBatchInsert_End================");
		return JsonUtils.getResultJson(jObj);
	}

	public String validate(PSCMPRD0015VO bean) {
		if (StringUtils.isEmpty(bean.getProdCd())) {
			return "상품코드가 없습니다.";
		}
		if (StringUtils.isEmpty(bean.getRepProdCd())) {
			return "대표상품코드가 없습니다.";
		}
		if (StringUtils.isEmpty(bean.getApplyStartDy())) {
			return "적용시작일자가 없습니다.";
		}
		if (StringUtils.isEmpty(bean.getApplyEndDy())) {
			return "적용종료일자가 없습니다.";
		}
		if (StringUtils.isEmpty(bean.getReqReasonContent())) {
			return "변경 요청 사유가 없습니다.";
		}
		if (StringUtils.isEmpty(bean.getCurrSellPrc())) {
			return "판매가 정보가 없습니다.";
		}
		if (StringUtils.isEmpty(bean.getProfitRate())) {
			return "이익율 정보가 없습니다.";
		}
		return "";
	}

	/** ********************************************
	 * 기호와 숫자가 함께 들어있는 문자열를 받아 숫자만 걸러내어 반환한다
	 ***********************************************/
	public static String getOnlyNumber(String str) {
		if (str == null)
			return "";
		StringBuffer out = new StringBuffer(512);
		char c;

		int str_length = str.length();
		for (int i = 0; i < str_length; i++) {
			c = str.charAt(i);
			if ('0' <= c && c <= '9')
				out.append(c);
		}
		return out.toString();
	}

	/**
	 * Desc : 상품 기본 화면
	 * 
	 * @Method Name : PBOMPRD0061
	 * @param mode
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("product/repProdCd/selectProdCdPop.do")
	public String selectProdCdPop(@RequestParam(value="mode", defaultValue="S") String mode
			,@RequestParam(value="aprvYn", defaultValue="ALL") String aprvYn
			,@RequestParam(value="periDeli", defaultValue="N") String periDeli
			,@RequestParam(value="onOffYn", defaultValue="") String onOffYn
			,@RequestParam(value="dealCtpdYn", defaultValue="") String dealCtpdYn
			,@RequestParam(value="dealYn", defaultValue="") String dealYn
			,@RequestParam(value="ctpdYn", defaultValue="") String ctpdYn
			,HttpServletRequest request, HttpServletResponse response) throws Exception{
		// logger.debug("================selectProdCdPop Start================");
		// logger.debug("=============== mode : "+ mode +"===============");

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		// 검색기간 셋팅
		request.setAttribute("epcLoginVO", epcLoginVO);
		request.setAttribute("mode", mode);
		request.setAttribute("aprvYn", aprvYn);
		request.setAttribute("periDeli", periDeli);
		request.setAttribute("onOffYn", onOffYn);
		request.setAttribute("dealCtpdYn", dealCtpdYn);
		request.setAttribute("dealYn", dealYn);
		request.setAttribute("ctpdYn", ctpdYn);
		// logger.debug("================selectProdCdPop End ================");

		String returnUrl = "";

		if ("DISP".equals(aprvYn))
			returnUrl = "product/common/PBOMPRD006101";
		else
			returnUrl = "product/PSCMPRD001503";

		return returnUrl;
	}


	/**
	 * Desc : 상품 목록 조회
	 * @param mallDivnCd
	 * @param mallDivnCd
	 * @Method Name : selectRepChgProdCdSearch
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@ResponseBody
	@RequestMapping("/product/repProdCd/selectRepChgProdCdSearch.do")
	public Map<String, Object> selectRepChgProdCdSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// logger.debug("================selectRepChgProdCdSearch Start================");

		Map<String, Object> rtnMap = new HashMap<String, Object>();
		try {
			DataMap param = new DataMap(request);

			String rowsPerPage = StringUtil.null2str((String) param.get("rowsPerPage"), config.getString("count.row.per.page"));

			int startRow = ((Integer.parseInt((String) param.get("currentPage")) - 1) * Integer.parseInt(rowsPerPage)) + 1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) - 1;

			param.put("currentPage", (String) param.get("currentPage"));
			param.put("startRow", Integer.toString(startRow));
			param.put("endRow", Integer.toString(endRow));
			String[] prodCdArr = request.getParameterValues("prodCdArr");
			param.put("prodCdArr", prodCdArr);
			if (prodCdArr == null) {
				param.put("searFile", "N");
				param.put("rowsPerPage", rowsPerPage);
			} else {
				param.put("searFile", "Y");
				param.put("rowsPerPage", "1000");
			}
			List<Map<String, Object>> list = pscmprd0015Service.pscmprd0015List(param);
			BigDecimal totCnt = new BigDecimal("0");
			if (list.size() != 0) {
				totCnt = (BigDecimal) list.get(0).get("TOTAL_COUNT");
			}
			rtnMap = JsonUtils.convertList2Json((List) list, Integer.parseInt(totCnt.toString()), param.getString("currentPage"));
			if (prodCdArr == null) {
				rtnMap.put("rowsPerPage", rowsPerPage);
			} else {
				rtnMap.put("rowsPerPage", "1000");
			}
			// 성공
			// logger.debug("데이터 조회 완료");
			rtnMap.put("result", true);
		} catch (Exception e) {
			// 실패
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());

		}
		// logger.debug("================selectRepChgProdCdSearch End================");
		return rtnMap;
	}

}
