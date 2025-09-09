package com.lottemart.epc.product.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.views.AjaxJsonModelHelper;
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

import com.lottemart.common.exception.AlertException;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.product.model.PSCPPRD0005VO;
import com.lottemart.epc.product.model.PSCPPRD0017HistVO;
import com.lottemart.epc.product.model.PSCPPRD0017VO;
import com.lottemart.epc.product.service.PSCPPRD0017Service;

/**
 *  
 * @Class Name : PBOMPRD0006Controller
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자	   수정내용
 *  -------    --------    ---------------------------
 * 2011. 12. 09. 오후 03:03:03 mjChoi
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Controller("pscpprd0017Controller")
public class PSCPPRD0017Controller {

	private static final Logger logger = LoggerFactory.getLogger(PSCPPRD0017Controller.class);

	@Autowired
	private PSCPPRD0017Service pscpprd0017Service;

	@Autowired
	private MessageSource messageSource;

	/**
	 * 상품 키워드 폼 페이지
	 * @Description : 상품 키워드 목록 초기페이지 로딩
	 * @Method Name : prdKeywordForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/selectProductKeywordForm.do")
	public String prdKeywordForm(HttpServletRequest request) throws Exception {

		//20180911 상품키워드 입력 기능 추가
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("prodCd", request.getParameter("prodCd"));
		paramMap.put("typeCd", "005");
		PSCPPRD0005VO prdMdAprInfo = pscpprd0017Service.selectPrdMdAprvMst(paramMap);

		request.setAttribute("prdMdAprInfo", prdMdAprInfo);
		//20180911 상품키워드 입력 기능 추가

		return "product/internet/PSCPPRD001701";
	}

//	/**
//	 * 상품 키워드 목록
//	 * @Description : 페이지 로딩시 상품키워드 목록을 얻어서 그리드에 리턴
//	 * @Method Name : selectProductKeywordSearch
//	 * @param request
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("product/selectProductKeywordSearch_org.do")
//	public String selectProductKeywordSearch_org(HttpServletRequest request) throws Exception {
//		GridData gdRes = new GridData();
//
//		try {
//			// 요청객체
//			String wiseGridData = request.getParameter("WISEGRID_DATA");
//			GridData gdReq = OperateGridData.parse(wiseGridData);
//
//			// 결과객체
//			gdRes = OperateGridData.cloneResponseGridData(gdReq);
//			gdRes.addParam("mode", gdReq.getParam("mode"));
//
//			// 파라미터
//			Map<String, String> paramMap = new HashMap<String, String>();
//			paramMap.put("prodCd", gdReq.getParam("prodCd"));
//
//			// 데이터 조회
//			//logger.debug("::: selectPrdKeywordList :::");
//			List<PSCPPRD0017VO> list = pscpprd0017Service.selectPrdKeywordList(paramMap);
//
//			// 데이터 없음
//			if (list == null || list.size() == 0) {
//				//logger.debug("::: data not exists..! :::");
//				gdRes.setStatus("false");
//				request.setAttribute("wizeGridResult", gdRes);
//				gdRes.addParam("keyCount", "0");
//				gdRes.addParam("byteChk", "0");
//
//				return "common/wiseGridResult";
//			}
//
//			// GridData 셋팅
//			//logger.debug("data set ==>" + list.size() + "<==");
//
//			PSCPPRD0017VO bean;
//			int listSize = list.size();
//
//			for (int i = 0; i < listSize; i++) {
//				bean = (PSCPPRD0017VO) list.get(i);
//
//				gdRes.getHeader("prodCd").addValue(bean.getProdCd(), "");
//				gdRes.getHeader("seq").addValue(bean.getSeq(), "");
//				gdRes.getHeader("searchKywrd").addValue(bean.getSearchKywrd(), "");
//				gdRes.getHeader("keyCount").addValue(bean.getKeyCount(), "");
//				gdRes.getHeader("byteChk").addValue(bean.getByteChk(), "");
//				// tmp
//				gdRes.getHeader("num").addValue(Integer.toString(i + 1), "");
//				gdRes.getHeader("CHK").addValue("0", "");
//
//				if (i == 0) {
//					gdRes.addParam("keyCount", bean.getKeyCount() == null ? "0" : bean.getKeyCount());
//					gdRes.addParam("byteChk", bean.getByteChk() == null ? "0" : bean.getByteChk());
//				}
//			}
//
//			// 성공
//			//logger.debug("[[[ success ]]]");
//			gdRes.setStatus("true");
//
//		} catch (Exception e) {
//			// 오류
//			//logger.debug("[[[ Exception ]]]");
//			gdRes.setMessage(e.getMessage());
//			gdRes.setStatus("false");
//			logger.debug("", e);
//		}
//
//		request.setAttribute("wizeGridResult", gdRes);
//		//logger.debug("[[[ wiseGridResult ]]]");
//
//		return "common/wiseGridResult";
//	}

	/**
	 * 상품 키워드 목록 for IBSheet
	 * @Description : 페이지 로딩시 상품키워드 목록을 얻어서 그리드에 리턴
	 * @Method Name : prdKeywordSearch
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/selectProductKeywordSearch.do")
	public @ResponseBody Map selectProductKeywordSearch(HttpServletRequest request) throws Exception {

		Map rtnMap = new HashMap<String, Object>();

		try {
			DataMap param = new DataMap(request);

			// 데이터 조회
			logger.debug("[ selectPrdKeywordList ]");
			List<PSCPPRD0017VO> list = pscpprd0017Service.selectPrdKeywordList((Map) param);

			Map mp = new HashMap();
			if (list != null && list.size() > 0) {
				PSCPPRD0017VO bean = (PSCPPRD0017VO) list.get(0);
				bean.setKeyCount(bean.getKeyCount() == null ? "0" : bean.getKeyCount());
				bean.setByteChk(bean.getByteChk() == null ? "0" : bean.getByteChk());
			} else {
				mp.put("keyCount", "0");
				mp.put("byteChk", "0");
			}

			// JsonMap으로 변환
			rtnMap = JsonUtils.convertList2Json((List) list, null, null);
			if (list != null && (list.size() == 0)) {
				rtnMap.put("extInfo", mp);
			}

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

	/* [미호출 메소드] - [확인일: 2024-09-12] */
//	/**
//	 * 상품 키워드 입력 폼 페이지
//	 * @Description : 상품 키워드 입력 초기페이지 로딩 (팝업)
//	 * @Method Name : selectProductKeywordInsertForm
//	 * @param request
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("product/selectProductKeywordInsertForm.do")
//	public String selectProductKeywordInsertForm(HttpServletRequest request) throws Exception {
//
//		//20180911 상품키워드 입력 기능 추가
//		Map<String, String> paramMap = new HashMap<String, String>();
//		paramMap.put("prodCd", request.getParameter("prodCd"));
//		paramMap.put("typeCd", "005");
//		PSCPPRD0005VO prdMdAprInfo = pscpprd0017Service.selectPrdMdAprvMst(paramMap);
//
//		request.setAttribute("prdMdAprInfo", prdMdAprInfo);
//		//20180911 상품키워드 입력 기능 추가
//
//		return "product/internet/PSCPPRD001702";
//	}

//	/**
//	 * 상품 키워드 입력 처리
//	 * @Description : 팝업창의 상품 키워드 입력 버튼 클릭시 상품키워드 입력 및 기타 처리후 그리드를 리로드한다. 
//	 * @Method Name : insertProductKeyword
//	 * @param request
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("product/insertProductKeyword_org.do")
//	public ModelAndView insertProductKeyword_org(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		String message = null;
//		PSCPPRD0017VO bean = new PSCPPRD0017VO();
//
//		bean.setSearchKywrd((String) request.getParameter("searchKywrd"));
//		bean.setProdCd((String) request.getParameter("prodCd"));
//		bean.setRegId((String) request.getParameter("vendorId"));
//
//		// 값이 있을경우 에러
//		String resultStr = validate(bean);
//
//		message = messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault());
//
//		if (!"".equals(resultStr)) {
//			return AjaxJsonModelHelper.create(resultStr);
//		}
//
//		try {
//			int resultCnt = pscpprd0017Service.insertPrdKeyword(bean);
//
//			if (resultCnt > 0) {
//				return AjaxJsonModelHelper.create("");
//			} else {
//				return AjaxJsonModelHelper.create(message);
//			}
//		} catch (Exception e) {
//			return AjaxJsonModelHelper.create(message);
//		}
//	}

	/**
	 * 상품 키워드 입력 처리 for IBSheet
	 * @Description : 팝업창의 상품 키워드 입력 버튼 클릭시 상품키워드 입력 및 기타 처리후 그리드를 리로드한다. 
	 * @Method Name : prdKeywordInsert
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/insertProductKeyword.do")
	public @ResponseBody JSONObject insertProductKeyword(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String message;
		JSONObject jObj = new JSONObject();

		//logger.debug("[ input searchKywrd : " + request.getParameter("searchKywrd") + " ]");
		try {
			DataMap dm = new DataMap(request);
			String[] chks = request.getParameterValues("sStatus");
			String mode = dm.getString("mode");
			String prodCd = dm.getString("mainProdCd");
			String vendorId = dm.getString("vendorId");
			String[] rowChk = request.getParameterValues("searchKywrd"); //

			if (rowChk.length > 5) {
				throw new AlertException("상품키워드가 5개를 초과했습니다. 상품키워드는 5개 까지 등록 가능합니다.");
			}

			//20180911 상품키워드 입력 기능 추가
			String keywordSeq = "";

			DataMap paramMap = new DataMap();
			paramMap.put("mode", mode);
			paramMap.put("prodCd", prodCd);
			paramMap.put("vendorId", vendorId);
			paramMap.put("typeCd", "005");

			int iCnt = pscpprd0017Service.selectPrdMdAprvMstCnt(paramMap);

			if (iCnt > 0) {
				Map<String, String> param = new HashMap<String, String>();
				param.put("prodCd", prodCd);
				param.put("typeCd", "005");

				PSCPPRD0005VO prdMdAprInfo = pscpprd0017Service.selectPrdMdAprvMst(param);
				keywordSeq = prdMdAprInfo.getSeq();
			} else {
				PSCPPRD0005VO pSCPPRD0005VO = new PSCPPRD0005VO();
				pSCPPRD0005VO.setProdCd(prodCd);
				pSCPPRD0005VO.setRegId(vendorId);
				pSCPPRD0005VO.setAprvCd("001");
				pSCPPRD0005VO.setTypeCd("005");
				pSCPPRD0005VO.setVendorId(vendorId);
				keywordSeq = pscpprd0017Service.insertPrdMdAprvMst(pSCPPRD0005VO);
			}

			int resultCnt = 0;

			if (!"".equals(keywordSeq)) {
				paramMap.put("keywordSeq", keywordSeq);
				pscpprd0017Service.deletePrdKeywordHistAll(paramMap);
				for (int i = 0; i < rowChk.length; i++) {
					paramMap.put("searchKywrd", request.getParameterValues("searchKywrd")[i]);
					String searchKyword = request.getParameterValues("searchKywrd")[i];
					if (searchKyword.replaceAll("\\s", "").length() == 0) {
						throw new AlertException("키워드를 입력해주세요.");
					}
					paramMap.put("seq", request.getParameterValues("seq")[i]); //
					paramMap.put("regId", vendorId);
					paramMap.put("modId", vendorId);
					resultCnt += pscpprd0017Service.mergePrdKeywordHistAll(paramMap);

					PSCPPRD0017HistVO bean = new PSCPPRD0017HistVO();
					bean.setKeywordSeq(keywordSeq);
					bean.setProdCd(prodCd);
					bean.setSearchKywrd(request.getParameterValues("searchKywrd")[i]);
					bean.setSeq(request.getParameterValues("seq")[i]);
					bean.setKeyCount(request.getParameterValues("keyCount")[i]);
					bean.setByteChk(request.getParameterValues("byteChk")[i]);
					bean.setRegId(vendorId);

					// 값이 있을경우 에러
					String resultStr = validate3(bean);
					if (!"".equals(resultStr)) {
						jObj.put("Code", -1);
						jObj.put("Message", resultStr);
						return JsonUtils.getResultJson(jObj);
					}
				}
				pscpprd0017Service.masterKeywordHistUpdate(paramMap);

			}
			//20180911 상품키워드 입력 기능 추가

			/*int resultCnt = 0;
			for (int i=0; i<chks.length; i++) {

				PSCPPRD0017VO bean = new PSCPPRD0017VO();
				bean.setProdCd(prodCd);
				bean.setSearchKywrd(request.getParameterValues("searchKywrd")[i]);
				bean.setSeq(request.getParameterValues("seq")[i]);
				bean.setKeyCount(request.getParameterValues("keyCount")[i]);
				bean.setByteChk(request.getParameterValues("byteChk")[i]);
				bean.setRegId(vendorId);

				// 값이 있을경우 에러
				String resultStr = validate(bean);
				if (!"".equals(resultStr)){
					jObj.put("Code", -1);
					jObj.put("Message", resultStr);
					return JsonUtils.getResultJson(jObj);
				}
				resultCnt += pscpprd0017Service.insertPrdKeyword(bean);
			}*/

			//20180911 상품키워드 입력 기능 추가

			/*for (int i=1; i<chks.length; i++) {
				PSCPPRD0017HistVO bean = new PSCPPRD0017HistVO();
				bean.setKeywordSeq(keywordSeq);
				bean.setProdCd(prodCd);
				bean.setSearchKywrd(request.getParameterValues("searchKywrd")[i]);
				bean.setSeq(request.getParameterValues("seq")[i]);
				bean.setKeyCount(request.getParameterValues("keyCount")[i]);
				bean.setByteChk(request.getParameterValues("byteChk")[i]);
				bean.setRegId(vendorId);
			
				// 값이 있을경우 에러
				String resultStr = validate3(bean);
				if (!"".equals(resultStr)){
					jObj.put("Code", -1);
					jObj.put("Message", resultStr);
					return JsonUtils.getResultJson(jObj);
				}
			//	resultCnt += pscpprd0017Service.insertPrdKeywordHist(bean);
			}*/
			//20180911 상품키워드 입력 기능 추가

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

//	/**
//	 * 상품 키워드 수정, 삭제 처리
//	 * @Description : 수정 및 삭제 버튼 클릭시 해당 처리를 완료한후에 메세지를 띄우고 그리드를 리로드한다. 
//	 * @Method Name : updateProductKeywordList
//	 * @param request
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("product/updateProductKeywordList_org.do")
//	public void updateProductKeywordList_org(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		GridData gdRes = new GridData();
//
//		// Encode Type을 UTF-8로 변환한다.
//		request.setCharacterEncoding("UTF-8");
//		response.setContentType("text/html;charset=UTF-8");
//		PrintWriter out = response.getWriter();
//		String message = "";
//
//		try {
//			// 요청객체
//			String wiseGridData = request.getParameter("WISEGRID_DATA");
//			GridData gdReq = OperateGridData.parse(wiseGridData);
//
//			// 모드
//			gdRes.addParam("mode", gdReq.getParam("mode"));
//
//			// 값 셋팅
//			List<PSCPPRD0017VO> beanList = new ArrayList<PSCPPRD0017VO>();
//			PSCPPRD0017VO bean = null;
//			String resultStr = null;
//
//			int rowCount = gdReq.getHeader("num").getRowCount();
//
//			for (int i = 0; i < rowCount; i++) {
//				bean = new PSCPPRD0017VO();
//				bean.setProdCd(gdReq.getHeader("prodCd").getValue(i));
//				bean.setSearchKywrd(gdReq.getHeader("searchKywrd").getValue(i));
//				bean.setSeq(gdReq.getHeader("seq").getValue(i));
//				bean.setRegId(gdReq.getParam("vendorId"));
//
//				// 값 채크
//				resultStr = validate2(bean);//-- 각기 채크..
//
//				if (!"".equals(resultStr)) {
//					//logger.debug("validate check fail (" + i + ")==>" + resultStr + "<==");
//					message = resultStr;
//					gdRes.addParam("saveData", message);
//					gdRes.setStatus("true");
//				}
//
//				beanList.add(bean);
//			}
//
//			int resultCnt = 0;
//			// 처리
//			resultCnt = pscpprd0017Service.updatePrdKeywordList(beanList, (String) gdReq.getParam("mode"));
//
//			// 처리 결과
//			if (resultCnt > 0) {
//				message = messageSource.getMessage("msg.common.success.request", null, Locale.getDefault());
//				gdRes.addParam("saveData", resultCnt + "건의 " + message);
//				gdRes.setStatus("true");
//			} else {
//				message = messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault());
//				gdRes.addParam("saveData", message);
//				gdRes.setStatus("true");//--true   gdRes.setStatus("false");
//			}
//
//		} catch (Exception e) {
//			// 오류
//			// logger.debug("[[[ Exception ]]]");
//			gdRes.setMessage(e.getMessage());
//			gdRes.setStatus("false");
//		} finally {
//			try {
//				// 자료구조를 전문으로 변경해 Write한다.
//				//logger.debug("[[[ OperateGridData write ]]]");
//				OperateGridData.write(gdRes, out);
//			} catch (Exception e) {
//				//logger.debug("[[[ finally Exception ]]]");
//				logger.debug("", e);
//			}
//		}
//	}

	/**
	 * 상품 키워드 수정, 삭제 처리 for IBSheet
	 * @Description : 수정 및 삭제 버튼 클릭시 해당 처리를 완료한후에 메세지를 띄우고 그리드를 리로드한다. 
	 * @Method Name : updateProductKeywordList
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/updateProductKeywordList.do")
	public @ResponseBody JSONObject updateProductKeywordList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		JSONObject jObj = new JSONObject();
		String message = "";

		try {
			DataMap dm = new DataMap(request);
			String[] chks = request.getParameterValues("sStatus");
			String prodCd = dm.getString("mainProdCd");
			String vendorId = dm.getString("vendorId");

			//20180911 상품키워드 입력 기능 추가
			String keywordSeq = "";

			DataMap paramMap = new DataMap();
			paramMap.put("prodCd", prodCd);
			paramMap.put("vendorId", vendorId);
			paramMap.put("typeCd", "005");

			int iCnt = pscpprd0017Service.selectPrdMdAprvMstCnt(paramMap);

			if (iCnt > 0) {
				Map<String, String> param = new HashMap<String, String>();
				param.put("prodCd", prodCd);
				param.put("typeCd", "005");

				PSCPPRD0005VO prdMdAprInfo = pscpprd0017Service.selectPrdMdAprvMst(param);

				keywordSeq = prdMdAprInfo.getSeq();
			} else {
				PSCPPRD0005VO pSCPPRD0005VO = new PSCPPRD0005VO();
				pSCPPRD0005VO.setProdCd(prodCd);
				pSCPPRD0005VO.setRegId(vendorId);
				pSCPPRD0005VO.setAprvCd("001");
				pSCPPRD0005VO.setTypeCd("005");
				pSCPPRD0005VO.setVendorId(vendorId);

				keywordSeq = pscpprd0017Service.insertPrdMdAprvMst(pSCPPRD0005VO);
			}

			if (!"".equals(keywordSeq)) {
				paramMap.put("keywordSeq", keywordSeq);
				pscpprd0017Service.deletePrdKeywordHistAll(paramMap);
				pscpprd0017Service.insertPrdKeywordHistAll(paramMap);
			}
			//20180911 상품키워드 입력 기능 추가

			/*
			// 값 셋팅
			List<PSCPPRD0017VO> beanList = new ArrayList<PSCPPRD0017VO>();

			int resultCnt = 0;
			for (int i=0; i<chks.length; i++) {

				logger.debug("[ set i : "+i+" ]");
				PSCPPRD0017VO bean = new PSCPPRD0017VO();
				bean.setProdCd(prodCd);
				bean.setSearchKywrd(request.getParameterValues("searchKywrd")[i]);
				bean.setSeq(request.getParameterValues("seq")[i]);
				bean.setKeyCount(request.getParameterValues("keyCount")[i]);
				bean.setByteChk(request.getParameterValues("byteChk")[i]);
				bean.setRegId(vendorId);

				// 값 체크
				logger.debug("[ validate check ]");
				String resultStr = validate2(bean);//-- 각기 채크..
				if (!"".equals(resultStr)) {
					jObj.put("Code", -1);
					jObj.put("Message", resultStr);
					return JsonUtils.getResultJson(jObj);
				}
				beanList.add(bean);
			}

			// 처리
			logger.debug("[ updatePrdKeywordList begin ]");
			resultCnt = pscpprd0017Service.updatePrdKeywordList(beanList, dm.getString("mode"));
			*/

			//20180911 상품키워드 입력 기능 추가
			// 값 셋팅
			List<PSCPPRD0017HistVO> beanList = new ArrayList<PSCPPRD0017HistVO>();

			int resultCnt = 0;

			for (int i = 0; i < chks.length; i++) {
				PSCPPRD0017HistVO bean = new PSCPPRD0017HistVO();
				bean.setKeywordSeq(keywordSeq);
				bean.setProdCd(prodCd);
				bean.setSearchKywrd(request.getParameterValues("searchKywrd")[i]);
				bean.setSeq(request.getParameterValues("seq")[i]);
				bean.setKeyCount(request.getParameterValues("keyCount")[i]);
				bean.setByteChk(request.getParameterValues("byteChk")[i]);
				bean.setRegId(vendorId);

				// 값 체크
				String resultStr = validate4(bean);

				if (!"".equals(resultStr)) {
					jObj.put("Code", -1);
					jObj.put("Message", resultStr);
					return JsonUtils.getResultJson(jObj);
				}

				beanList.add(bean);
			}

			// 처리
			resultCnt = pscpprd0017Service.updatePrdKeywordHistList(beanList, dm.getString("mode"));
			//20180911 상품키워드 입력 기능 추가

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
	 * validate 체크
	 */
	public String validate(PSCPPRD0017VO bean) throws Exception {
		if (StringUtils.isEmpty(bean.getProdCd())) {
			return "상품 코드가 없습니다.";
		}
		if (StringUtils.isEmpty(bean.getSearchKywrd())) {
			return "상품 키워드가 없습니다.";
		}
		if (StringUtils.isEmpty(bean.getRegId())) {
			return "로그인 정보가 없습니다.";
		}

		return "";
	}

	public String validate2(PSCPPRD0017VO bean) throws Exception {
		if (StringUtils.isEmpty(bean.getProdCd())) {
			return "상품 코드가 없습니다.";
		}
		if (StringUtils.isEmpty(bean.getSearchKywrd())) {
			return "상품 키워드가 없습니다.";
		}
		if (StringUtils.isEmpty(bean.getSeq())) {
			return "시퀀스가 없습니다.";
		}
		if (StringUtils.isEmpty(bean.getRegId())) {
			return "로그인 정보가 없습니다.";
		}

		return "";
	}

	//20180911 상품키워드 입력 기능 추가
	public String validate3(PSCPPRD0017HistVO bean) throws Exception {
		if (StringUtils.isEmpty(bean.getProdCd())) {
			return "상품 코드가 없습니다.";
		}
		if (StringUtils.isEmpty(bean.getSearchKywrd())) {
			return "상품 키워드가 없습니다.";
		}
		if (StringUtils.isEmpty(bean.getRegId())) {
			return "로그인 정보가 없습니다.";
		}

		return "";
	}

	public String validate4(PSCPPRD0017HistVO bean) throws Exception {
		if (StringUtils.isEmpty(bean.getProdCd())) {
			return "상품 코드가 없습니다.";
		}
		if (StringUtils.isEmpty(bean.getSearchKywrd())) {
			return "상품 키워드가 없습니다.";
		}
		if (StringUtils.isEmpty(bean.getSeq())) {
			return "시퀀스가 없습니다.";
		}
		if (StringUtils.isEmpty(bean.getRegId())) {
			return "로그인 정보가 없습니다.";
		}

		return "";
	}
	//20180911 상품키워드 입력 기능 추가

}