package com.lottemart.epc.product.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.framework.property.ConfigurationService;
import net.sf.json.JSONArray;
//import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//import xlib.cmc.GridData;
//import xlib.cmc.OperateGridData;

import com.lcnjf.util.StringUtil;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
//import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.product.model.PSCPPRD00041VO;
import com.lottemart.epc.product.model.PSCPPRD0004VO;
import com.lottemart.epc.product.service.PSCPPRD0002Service;
import com.lottemart.epc.product.service.PSCPPRD0004Service;

/**
 * @Class Name : PSCPPRD0004Controller.java
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
@Controller("pscpprd0004Controller")
public class PSCPPRD0004Controller {

	private static final Logger logger = LoggerFactory.getLogger(PSCPPRD0004Controller.class);

	@Autowired
	private PSCPPRD0004Service pscpprd0004Service;

	@Autowired
	private PSCPPRD0002Service pscpprd0002Service;

	@Autowired
	private ConfigurationService config;

	@Autowired
	private MessageSource messageSource;

	/**
	 * 딜상품 상품구성 폼
	 * @Description : 상품 추천상품 목록 초기페이지 로딩
	 * @Method Name : selectProductRecommendForm
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping("product/selectProductRecommendForm.do")
	public String selectProductRecommendForm(HttpServletRequest request) throws Exception {
		String prodCd = request.getParameter("prodCd");
		DataMap themaDealInfo = pscpprd0002Service.selectPrdExtThemaDealInfo(prodCd);
		request.setAttribute("themaDealInfo", themaDealInfo);
		return "product/internet/PSCPPRD000401";
	}

	/**
	 * 테마 내 상품 구성 폼
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/selectProductThemaForm.do")
	public String selectProductThemaForm(HttpServletRequest request) throws Exception {
		return "product/internet/PSCPPRD000403";
	}

	/**
	 * Desc : 딜상품 목록 for IBSheet
	 * 
	 * @Method Name : prdRecommendSearch
	 * @param request
	 * @return
	 * @throws Exception
	 * @exception Exception
	 */
	@RequestMapping("product/selectProductRecommendSearch.do")
	public @ResponseBody Map selectProductRecommendSearch(HttpServletRequest request) throws Exception {

		Map rtnMap = new HashMap<String, Object>();
		try {
			DataMap param = new DataMap(request);
			List<PSCPPRD0004VO> list = pscpprd0004Service.selectPrdRecommendList((Map) param); // 데이터 조회

			String imgUrl = config.getString("online.product.image.url");
			//logger.debug("imgUrl ===> " + imgUrl);
			for (int i = 0; i < list.size(); i++) {
				PSCPPRD0004VO bean = (PSCPPRD0004VO) list.get(i);
				bean.setNum(Integer.toString(i + 1));
				bean.setImgPath(imgUrl + bean.getImgPath());
			}
			rtnMap = JsonUtils.convertList2Json((List) list, null, null);
			// 성공
			rtnMap.put("result", true);
		} catch (Exception e) {
			// 작업오류
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}

	@RequestMapping("product/selectProductThemaSearch.do")
	public @ResponseBody Map selectProductThemaSearch(HttpServletRequest request) throws Exception {
		Map rtnMap = new HashMap<String, Object>();
		try {
			DataMap param = new DataMap(request);

			List<PSCPPRD00041VO> list = pscpprd0004Service.selectPrdThemaList((Map) param);
			for (int i = 0; i < list.size(); i++) {
				PSCPPRD00041VO vo = list.get(i);
				DataMap map = new DataMap();
				map.put("prodCd", vo.getProdCd());
				map.put("themaSeq", vo.getThemaSeq());
				List<PSCPPRD0004VO> list2 = pscpprd0004Service.selectPrdRecommendList((Map) map);
				JSONArray jsonArray = JSONArray.fromObject(list2);
				vo.setThemaProd(jsonArray.toString());
			}

			rtnMap = JsonUtils.convertList2Json((List) list, null, null);
			// 성공
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
	 * Desc : 추천상품 수정, 삭제 처리
	 * 
	 * @Method Name : prdRecommendListUpdate
	 * @param request
	 * @param response
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("product/updateProduectRecommendList.do")
	public @ResponseBody JSONObject updateProduectRecommendList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		JSONObject jObj = new JSONObject();
		String message = "";
		try {
			int resultCnt = 0;
			DataMap param = new DataMap(request);
			List<PSCPPRD0004VO> beanList = new ArrayList<PSCPPRD0004VO>(); // 값 셋팅

			String[] prodCds = request.getParameterValues("prodCd");
			if (prodCds != null && prodCds.length > 0) {
				for (int i = 0; i < prodCds.length; i++) {
					PSCPPRD0004VO bean = new PSCPPRD0004VO();
					
					bean.setProdCd(request.getParameterValues("prodCd")[i]);
					bean.setAssoCd(request.getParameterValues("assoCd")[i]);
					bean.setDispYn(request.getParameterValues("dispYn")[i]);
					if ("Y".equals(request.getParameterValues("repYn")[i])) {
						bean.setOrderSeq("1");
					} else {
						bean.setOrderSeq("0");
					}
					bean.setBatchOrderSeq(request.getParameterValues("batchOrderSeq")[i]);
					bean.setImgPath(request.getParameterValues("imgPath")[i]);
					bean.setThemaSeq("0");
					bean.setMainProdYn("");
					bean.setProdLinkKindCd("04");
					bean.setRegId(param.getString("vendorId"));
					
					// 값 체크
					String resultStr = validate(bean);
					if (!"".equals(resultStr)) {
						message = resultStr;
						jObj.put("Code", 0);
						jObj.put("Message", message);
						return jObj;
					}
					
					beanList.add(bean);
				}
				
				// 처리
				logger.debug("[ updatePrdRecommendList begin ]");
				DataMap themaMap = new DataMap();
				themaMap.put("prodCd", prodCds[0]);
				themaMap.put("dealViewCd", param.getString("dealViewCd"));
				themaMap.put("themaYn", param.getString("themaYn"));
				themaMap.put("scDpYn", param.getString("scDpYn"));
				themaMap.put("regId", param.getString("vendorId"));
				themaMap.put("modId",param.getString("vendorId"));
				
				/*int updCnt = */
				pscpprd0002Service.updatePrdExtThemaDealInfo(themaMap);

				resultCnt = pscpprd0004Service.updatePrdRecommendList(beanList, param.getString("mode"));
			}

			// 처리 결과
			if (resultCnt > 0) {
				message = messageSource.getMessage("msg.common.success.request", null, Locale.getDefault());
				jObj.put("Code", 1);
				jObj.put("Message", resultCnt + "건의 " + message);
			} else {
				message = messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault());
				jObj.put("Code", 0);
				jObj.put("Message", message);
			}

		} catch (Exception e) {
			e.printStackTrace();
			// 오류
			jObj.put("Code", -1);
			jObj.put("Message", e.getMessage());
		}

		return JsonUtils.getResultJson(jObj);
	}
	
	@RequestMapping("product/updateProduectThemaList.do")
	public @ResponseBody JSONObject updateProduectThemaList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		JSONObject jObj = new JSONObject();
		String message = "";
		int resultCnt = 0;

		try {
			String vendorId = request.getParameter("vendorId");;

			DataMap param = new DataMap(request);

			// List Loop  Delete & Insert
			List<PSCPPRD00041VO> themaList = new ArrayList<PSCPPRD00041VO>(); // 값 셋팅
			String prodCd = "";
			String[] themaSeq = request.getParameterValues("themaSeq");
			if (themaSeq != null && themaSeq.length > 0) {
				prodCd = request.getParameterValues("prodCd")[0];
				for (int i = 0; i < themaSeq.length; i++) {
					PSCPPRD00041VO thema = new PSCPPRD00041VO();
					thema.setProdCd(request.getParameterValues("prodCd")[i]);
					thema.setThemaSeq(request.getParameterValues("themaSeq")[i]);
					thema.setThemaNm(request.getParameterValues("themaNm")[i]);
					thema.setOrderSeq(request.getParameterValues("orderSeq")[i]);
					thema.setRegId(vendorId);
					thema.setModId(vendorId);
					thema.setThemaProd(request.getParameterValues("themaProd")[i]);

					JSONArray themaProdListJson = JSONArray.fromObject(thema.getThemaProd());
					if (themaProdListJson != null && themaProdListJson.size() > 0) {

						List<PSCPPRD0004VO> prodList = new ArrayList<PSCPPRD0004VO>();
						int size2 = themaProdListJson.size();
						for (int j = 0 ; j < size2; j++) {
							JSONObject dealProd = themaProdListJson.getJSONObject(j);
							PSCPPRD0004VO pdBean = new PSCPPRD0004VO();
							pdBean.setProdCd(dealProd.getString("prodCd"));
							pdBean.setAssoCd(dealProd.getString("assoCd"));
							if ("Y".equals(dealProd.getString("repYn"))) {
								pdBean.setOrderSeq("1");
							} else {
								pdBean.setOrderSeq("0");
							}
							pdBean.setDispYn("Y");
							pdBean.setProdLinkKindCd("04");
							pdBean.setBatchOrderSeq(dealProd.getString("batchOrderSeq"));
							pdBean.setThemaSeq(thema.getThemaSeq());
							pdBean.setMainProdYn(dealProd.getString("mainProdYn"));
							pdBean.setRegId(vendorId);
							pdBean.setModId(vendorId);
							prodList.add(pdBean);
						}
						thema.setThemaProdList(prodList);
					}
					themaList.add(thema);
				}
				DataMap themaMap = new DataMap();
				themaMap.put("prodCd", prodCd);
				themaMap.put("dealViewCd", param.getString("dealViewCd"));
				themaMap.put("themaYn", param.getString("themaYn"));
				themaMap.put("scDpYn", param.getString("scDpYn"));
				themaMap.put("regId", param.getString("vendorId"));
				themaMap.put("modId",param.getString("vendorId"));
				
				int updCnt = pscpprd0002Service.updatePrdExtThemaDealInfo(themaMap);

				resultCnt = pscpprd0004Service.updatePrdPrdThemaList(themaList);
			}

			// 처리 결과
			if (resultCnt > 0) {
				message = messageSource.getMessage("msg.common.success.request", null, Locale.getDefault());
				jObj.put("Code", 1);
				jObj.put("Message", resultCnt + "건의 " + message);
			} else {
				message = messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault());
				jObj.put("Code", 0);
				jObj.put("Message", message);
			}

		} catch (Exception e) {
			// 오류
			jObj.put("Code", -1);
			jObj.put("Message", e.getMessage());
		}

		return JsonUtils.getResultJson(jObj);
	}

	/*
	 * 추천상품 목록
	 * @Description : 조회버튼 클릭시 추천상품 목록을 얻어서 그리드에 리턴
	 * @Method Name : selectProductRecommendSearch
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	/*@RequestMapping("product/selectProductRecommendSearch_org.do")
	public String selectProductRecommendSearch_org(HttpServletRequest request) throws Exception {
		GridData gdRes = new GridData();
	
		try {
			// 요청객체
			String wiseGridData = request.getParameter("WISEGRID_DATA");
			GridData gdReq = OperateGridData.parse(wiseGridData);
	
			// 결과객체
			gdRes = OperateGridData.cloneResponseGridData(gdReq);
			gdRes.addParam("mode", gdReq.getParam("mode"));
	
			// 파라미터
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("prodCd", gdReq.getParam("prodCd"));
			paramMap.put("prodLinkKindCd", gdReq.getParam("prodLinkKindCd"));
	
			// 데이터 조회
			// logger.debug("::: selectPrdRecommendList :::");
			List<PSCPPRD0004VO> list = pscpprd0004Service.selectPrdRecommendList(paramMap);
			int listSize = list.size();
			logger.debug("listSize ==>" + listSize + "<==");
	
			// 데이터 없음
			if (list == null || listSize == 0) {
				// logger.debug("::: data not exists..! :::");
				gdRes.setStatus("false");
				request.setAttribute("wizeGridResult", gdRes);
	
				return "common/wiseGridResult";
			}
	
			// GridData 셋팅
			String imgUrl = config.getString("online.product.image.url");
			PSCPPRD0004VO bean = null;
	
			for (int i = 0; i < listSize; i++) {
				bean = (PSCPPRD0004VO) list.get(i);
	
				gdRes.getHeader("prodLinkKindNm").addValue(bean.getProdLinkKindNm(), "");
				gdRes.getHeader("assoCd").addValue(bean.getAssoCd(), "");
				gdRes.getHeader("prodNm").addValue(bean.getProdNm(), "");
				gdRes.getHeader("buyPrc").addValue(bean.getBuyPrc(), "");
				gdRes.getHeader("sellPrc").addValue(bean.getSellPrc(), "");
				gdRes.getHeader("currSellPrc").addValue(bean.getCurrSellPrc(), "");
				gdRes.getHeader("dispYn").addSelectedHiddenValue(bean.getDispYn());
				gdRes.getHeader("orderSeq").addValue(bean.getOrderSeq(), "");
				gdRes.getHeader("batchOrderSeq").addValue(bean.getBatchOrderSeq(), "");
				gdRes.getHeader("imgPath").addValue("", "", imgUrl + bean.getImgPath());
				gdRes.getHeader("prodCd").addValue(bean.getProdCd(), "");
	
				// tmp
				gdRes.getHeader("num").addValue(Integer.toString(i + 1), "");
				gdRes.getHeader("CHK").addValue("0", "");
			}
	
			// 성공
			// logger.debug("[[[ success ]]]");
			gdRes.setStatus("true");
	
		} catch (Exception e) {
			// 오류
			// logger.debug("[[[ Exception ]]]");
			gdRes.setMessage(e.getMessage());
			gdRes.setStatus("false");
			logger.debug("", e);
		}
	
		request.setAttribute("wizeGridResult", gdRes);
		// logger.debug("[[[ wiseGridResult ]]]");
	
		return "common/wiseGridResult";
	}*/

	/*
	 * 추천상품 수정, 삭제 처리
	 * @Description : 수정 및 삭제 버튼 클릭시 해당 처리를 완료한후에 메세지를 띄우고 그리드를 리로드한다.
	 * @Method Name : updateProduectRecommendList
	 * @param request
	 * @param response
	 * @return String
	 * @throws Exception
	 */
	/*@RequestMapping("product/updateProduectRecommendList_org.do")
	public void updateProduectRecommendList_org(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);
	
		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}
	
		GridData gdRes = new GridData();
	
		// Encode Type을 UTF-8로 변환한다.
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String message = "";
	
		try {
			// 요청객체
			String wiseGridData = request.getParameter("WISEGRID_DATA");
			GridData gdReq = OperateGridData.parse(wiseGridData);
	
			// 모드
			gdRes.addParam("mode", gdReq.getParam("mode"));
	
			// 값 셋팅
			List<PSCPPRD0004VO> beanList = new ArrayList<PSCPPRD0004VO>();
			PSCPPRD0004VO bean = null;
	
			int rowCount = gdReq.getHeader("num").getRowCount();
			// logger.debug("::: updatePrdRecommendList set :::");
			// logger.debug("mode ==>" + gdReq.getParam("mode") + "<==");
			// logger.debug("rowCount ==>" + rowCount + "<==");
	
			for (int i = 0; i < rowCount; i++) {
				bean = new PSCPPRD0004VO();
	
				bean.setProdCd(gdReq.getHeader("prodCd").getValue(i));
				bean.setAssoCd(gdReq.getHeader("assoCd").getValue(i));
				bean.setDispYn(gdReq.getHeader("dispYn").getComboHiddenValues()[gdReq.getHeader("dispYn").getSelectedIndex(i)]);
				bean.setOrderSeq(gdReq.getHeader("orderSeq").getValue(i));
				bean.setRegId(gdReq.getParam("vendorId"));
	
				// 값 채크
				String resultStr = validate2(bean);
	
				if (!"".equals(resultStr)) {
					// logger.debug("validate check fail (" + i + ")==>" + resultStr + "<==");
					message = resultStr;
					gdRes.addParam("saveData", message);
					gdRes.setStatus("true");
				}
	
				beanList.add(bean);
			}
	
			int resultCnt = 0;
	
			// 처리
			// logger.debug("::: updatePrdRecommendList begin :::");
			resultCnt = pscpprd0004Service.updatePrdRecommendList(beanList, (String) gdReq.getParam("mode"));
	
			// 처리 결과
			if (resultCnt > 0) {
				// logger.debug("[[[ success ]]]");
				message = messageSource.getMessage("msg.common.success.request", null, Locale.getDefault());
				gdRes.addParam("saveData", resultCnt + "건의 " + message);
				gdRes.setStatus("true");
			} else {
				logger.debug("[[[ fail ]]]");
				message = messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault());
				gdRes.addParam("saveData", message);
				gdRes.setStatus("true");
			}
		} catch (Exception e) {
			// 오류
			// logger.debug("[[[ Exception ]]]");
			gdRes.setMessage(e.getMessage());
			gdRes.setStatus("false");
		} finally {
			try {
				// 자료구조를 전문으로 변경해 Write한다.
				// logger.debug("[[[ OperateGridData write ]]]");
				OperateGridData.write(gdRes, out);
			} catch (Exception e) {
				// logger.debug("[[[ finally Exception ]]]");
				logger.error(e.getMessage());
			}
		}
	}*/

	/**
	 * 추천상품 입력 폼 페이지
	 * @Description : 상품 추천상품 입력 초기페이지 로딩 (팝업)
	 * @Method Name : insertProductRecommendForm
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping("product/insertProductRecommendForm.do")
	public String insertProductRecommendForm(HttpServletRequest request) throws Exception {
		return "product/internet/PSCPPRD000402";
	}

	/*
	 * 추천할 상품 목록
	 * @Description : 추천할 대상 상품 목록을 페이지별로 로딩하여 그리드에 리턴
	 * @Method Name : selectProductRecommendCrossSearch
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	/*@RequestMapping("product/selectProductRecommendCrossSearch_org.do")
	public String selectProductRecommendCrossSearch_org(HttpServletRequest request) throws Exception {
		GridData gdRes = new GridData();
	
		try {
			// 요청객체
			String wiseGridData = request.getParameter("WISEGRID_DATA");
			GridData gdReq = OperateGridData.parse(wiseGridData);
	
			// 결과객체
			gdRes = OperateGridData.cloneResponseGridData(gdReq);
			gdRes.addParam("mode", gdReq.getParam("mode"));
	
			// 페이징
			String rowsPerPage = StringUtil.null2str(gdReq.getParam("rowsPerPage"), config.getString("count.row.per.page"));
			int startRow = (Integer.parseInt(gdReq.getParam("currentPage")) - 1) * Integer.parseInt(rowsPerPage) + 1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) - 1;
	
			// 파라미터
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("prodCd", gdReq.getParam("prodCd"));
			paramMap.put("assoCd", gdReq.getParam("assoCd"));//검색어 (상품코드)
			paramMap.put("assoNm", gdReq.getParam("assoNm"));//검색어 (상품명)
			paramMap.put("prodLinkKindCd", gdReq.getParam("prodLinkKindCd"));//유형
			paramMap.put("dispYn", gdReq.getParam("dispYn"));
	
			paramMap.put("currentPage", gdReq.getParam("currentPage"));
			paramMap.put("startRow", Integer.toString(startRow));
			paramMap.put("endRow", Integer.toString(endRow));
	
			// 전체 조회 건수
			//logger.debug("::: selectPrdRecommendCrossTotalCnt :::");
			int totalCnt = pscpprd0004Service.selectPrdRecommendCrossTotalCnt(paramMap);
	
			// 페이징 변수
			gdRes.addParam("totalCount", Integer.toString(totalCnt));
			gdRes.addParam("rowsPerPage", rowsPerPage);
			gdRes.addParam("currentPage", gdReq.getParam("currentPage"));
	
			// 데이터 조회
			//logger.debug("::: selectPrdRecommendCrossList :::");
			List<PSCPPRD0004VO> list = pscpprd0004Service.selectPrdRecommendCrossList(paramMap);
			int listSize = list.size();
	
			// 데이터 없음
			if (list == null || listSize == 0) {
				//				logger.debug("::: data not exists..! :::");
				gdRes.setStatus("false");
				request.setAttribute("wizeGridResult", gdRes);
	
				return "common/wiseGridResult";
			}
	
			// GridData 셋팅
			PSCPPRD0004VO bean = null;
			String imgUrl = config.getString("online.product.image.url");
	
			//logger.debug("::: data set :::");
			for (int i = 0; i < listSize; i++) {
				bean = (PSCPPRD0004VO) list.get(i);
				gdRes.getHeader("num").addValue(bean.getNum(), "");
				gdRes.getHeader("prodCd").addValue(bean.getProdCd(), "");
				gdRes.getHeader("assoCd").addValue(bean.getAssoCd(), "");
				gdRes.getHeader("assoNm").addValue(bean.getAssoNm(), "");
				gdRes.getHeader("imgPath").addValue(imgUrl + bean.getImgPath(), "");
				gdRes.getHeader("prodDesc").addValue(bean.getProdDesc(), "");
				gdRes.getHeader("existChk").addValue(bean.getExistChk(), "");
				// tmp
				gdRes.getHeader("CHK").addValue("0", "");
			}
	
			// 성공
			//logger.debug("[[[ success ]]]");
			gdRes.setStatus("true");
		} catch (Exception e) {
			// 오류
			//logger.debug("[[[ Exception ]]]");
			gdRes.setMessage(e.getMessage());
			gdRes.setStatus("false");
			logger.debug("", e);
		}
	
		request.setAttribute("wizeGridResult", gdRes);
		//		logger.debug("[[[ wiseGridResult ]]]");
	
		return "common/wiseGridResult";
	}*/

	/**
	 * 추천할 상품 목록
	 * @Description : 추천할 대상 상품 목록을 페이지별로 로딩하여 그리드에 리턴
	 * @Method Name : prdRecommendCrossSearch
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("product/selectProductRecommendCrossSearch.do")
	public Map<String, Object> selectProductRecommendCrossSearch(HttpServletRequest request) throws Exception {

		Map<String, Object> rtnMap = new HashMap<String, Object>();

		try {
			DataMap param = new DataMap(request);

			String rowsPerPage = StringUtil.null2str((String) param.get("rowsPerPage"), config.getString("count.row.per.page"));

			int startRow = (Integer.parseInt((String) param.get("currentPage")) - 1) * Integer.parseInt(rowsPerPage) + 1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) - 1;

			param.put("currentPage", (String) param.get("currentPage"));
			param.put("startRow", Integer.toString(startRow));
			param.put("endRow", Integer.toString(endRow));
			param.put("rowsPerPage", rowsPerPage);
			int totalCnt = pscpprd0004Service.selectPrdRecommendCrossTotalCnt((Map) param);

			// 데이터 조회
			List<PSCPPRD0004VO> list = pscpprd0004Service.selectPrdRecommendCrossList((Map) param);

			String imgUrl = config.getString("online.product.image.url");
			logger.debug("imgUrl ===> " + imgUrl);
			for (int i = 0; i < list.size(); i++) {
				PSCPPRD0004VO bean = (PSCPPRD0004VO) list.get(i);
				bean.setNum(Integer.toString(i + 1));
				bean.setImgPath(imgUrl + bean.getImgPath());
			}

			rtnMap = JsonUtils.convertList2Json((List) list, totalCnt, param.getString("currentPage"));

			// 성공
			logger.debug("데이터 조회 완료");
			rtnMap.put("result", true);
			rtnMap.put("resultMsg", messageSource.getMessage("msg.common.success.select", null, Locale.getDefault()));

		} catch (Exception e) {
			// 작업오류
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}

	/*
	 * 추천상품 입력 처리
	 * @Description : 팝업창의 추천상품 입력 버튼 클릭시 추천상품 입력 및 기타 처리후 그리드를 리로드한다.
	 * @Method Name : insertProdutRecommend
	 * @param request
	 * @return void
	 * @throws Exception
	 */
	/*@RequestMapping("product/insertProdutRecommend_org.do")
	public void insertProdutRecommend_org(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);
	
		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}
	
		GridData gdRes = new GridData();
	
		// Encode Type을 UTF-8로 변환한다.
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String message = "";
	
		try {
			// 요청객체
			String wiseGridData = request.getParameter("WISEGRID_DATA");
			GridData gdReq = OperateGridData.parse(wiseGridData);
	
			// 모드
			gdRes.addParam("mode", gdReq.getParam("mode"));
	
			// 값 셋팅
			List<PSCPPRD0004VO> beanList = new ArrayList<PSCPPRD0004VO>();
			PSCPPRD0004VO bean = null;
	
			int rowCount = gdReq.getHeader("num").getRowCount();
			//			logger.debug("::: insertPrdRecommend set :::");
			//			logger.debug("mode ==>" + gdReq.getParam("mode") + "<==");
			//			logger.debug("rowCount ==>" + rowCount + "<==");
	
			for (int i = 0; i < rowCount; i++) {
				bean = new PSCPPRD0004VO();
				bean.setProdCd(gdReq.getHeader("prodCd").getValue(i));
				bean.setAssoCd(gdReq.getHeader("assoCd").getValue(i));
				bean.setProdLinkKindCd(gdReq.getParam("prodLinkKindCd"));
				bean.setDispYn(gdReq.getParam("dispYn"));
				bean.setRegId(gdReq.getParam("vendorId"));
	
				// 값 채크
				String resultStr = validate3(bean);
				if (!"".equals(resultStr)) {
					//					logger.debug("validate check fail (" + i + ")==>" + resultStr + "<==");
					message = resultStr;
					gdRes.addParam("saveData", message);
					gdRes.setStatus("true");
				}
	
				beanList.add(bean);
			}
	
			int resultCnt = 0;
	
			// 처리
			//			logger.debug("::: insertPrdRecommend begin :::");
			resultCnt = pscpprd0004Service.insertPrdRecommend(beanList);
	
			// 처리 결과
			if (resultCnt > 0) {
				//				logger.debug("[[[ success ]]]");
				message = messageSource.getMessage("msg.common.success.request", null, Locale.getDefault());
				gdRes.addParam("saveData", resultCnt + "건의 " + message);
				gdRes.setStatus("true");
			} else {
				//				logger.debug("[[[ fail ]]]");
				message = messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault());
				gdRes.addParam("saveData", message);
				gdRes.setStatus("true");
			}
		} catch (Exception e) {
			// 오류
			//			logger.debug("[[[ Exception ]]]");
			gdRes.setMessage(e.getMessage());
			gdRes.setStatus("false");
		} finally {
			try {
				// 자료구조를 전문으로 변경해 Write한다.
				//				logger.debug("[[[ OperateGridData write ]]]");
				OperateGridData.write(gdRes, out);
			} catch (Exception e) {
				//				logger.debug("[[[ finally Exception ]]]");
				logger.error(e.getMessage());
			}
		}
	}*/

	/**
	 * 추천상품 입력 처리
	 * @Description : 팝업창의 추천상품 입력 버튼 클릭시 추천상품 입력 및 기타 처리후 그리드를 리로드한다.
	 * @Method Name : prdRecommendInsert
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("product/insertProdutRecommend.do")
	public JSONObject insertProdutRecommend(HttpServletRequest request, HttpServletResponse response) throws Exception {

		JSONObject jObj = new JSONObject();
		String message = "";
		try {
			String dispYn = request.getParameter("dispYn");
			String prodLinkKindCd = request.getParameter("prodLinkKindCd");
			String vendorId = request.getParameter("vendorId");
			// 값 셋팅
			List<PSCPPRD0004VO> beanList = new ArrayList<PSCPPRD0004VO>();

			String[] prodCds = request.getParameterValues("prodCd");
			for (int i = 0; i < prodCds.length; i++) {
				PSCPPRD0004VO bean = new PSCPPRD0004VO();

				bean.setProdCd(request.getParameterValues("prodCd")[i]);
				bean.setAssoCd(request.getParameterValues("assoCd")[i]);
				bean.setDispYn(dispYn);
				bean.setProdLinkKindCd(prodLinkKindCd);
				bean.setRegId(vendorId);

				// 값 체크
				String resultStr = validate3(bean);
				if (!"".equals(resultStr)) {
					message = resultStr;
					jObj.put("Code", 0);
					jObj.put("Message", message);
					return jObj;
				}

				beanList.add(bean);
			}

			int resultInt = pscpprd0004Service.insertPrdRecommend(beanList);

			//처리 결과 메세지 생성
			if (resultInt > 0) {
				jObj.put("Code", 1);
				jObj.put("Message", resultInt + "건의 " + messageSource.getMessage("msg.common.success.request", null, Locale.getDefault()));
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

		logger.debug("================prdRecommendInsert_End================");
		return JsonUtils.getResultJson(jObj);
	}

	/*
	 * validate 체크
	 */
	public String validate(PSCPPRD0004VO bean) throws Exception {
		if (StringUtils.isEmpty(bean.getProdCd())) {
			return "상품 코드가 없습니다.";
		}
		if (StringUtils.isEmpty(bean.getAssoCd())) {
			return "추천상품 코드가 없습니다.";
		}
		if (StringUtils.isEmpty(bean.getDispYn())) {
			return "전시여부가 없습니다.";
		}
		if (StringUtils.isEmpty(bean.getOrderSeq())) {
			return "전시순서가 없습니다.";
		}
		if (StringUtils.isEmpty(bean.getRegId())) {
			return "로그인 정보가 없습니다.";
		}

		return "";
	}

	public String validate2(PSCPPRD0004VO bean) throws Exception {
		if (StringUtils.isEmpty(bean.getProdCd())) {
			return "상품 코드가 없습니다.";
		}
		if (StringUtils.isEmpty(bean.getAssoCd())) {
			return "추천상품 코드가 없습니다.";
		}
		if (StringUtils.isEmpty(bean.getDispYn())) {
			return "전시여부가 없습니다.";
		}
		if (StringUtils.isEmpty(bean.getOrderSeq())) {
			return "전시순서가 없습니다.";
		}
		if (StringUtils.isEmpty(bean.getRegId())) {
			return "로그인 정보가 없습니다.";
		}

		return "";
	}

	public String validate3(PSCPPRD0004VO bean) throws Exception {
		if (StringUtils.isEmpty(bean.getProdCd())) {
			return "상품 코드가 없습니다.";
		}
		if (StringUtils.isEmpty(bean.getAssoCd())) {
			return "추천상품 코드가 없습니다.";
		}
		if (StringUtils.isEmpty(bean.getRegId())) {
			return "로그인 정보가 없습니다.";
		}
		if (StringUtils.isEmpty(bean.getDispYn())) {
			return "전시여부 정보가 없습니다.";
		}
		if (StringUtils.isEmpty(bean.getProdLinkKindCd())) {
			return "추천상품 종류가 없습니다.";
		}

		return "";
	}

}
