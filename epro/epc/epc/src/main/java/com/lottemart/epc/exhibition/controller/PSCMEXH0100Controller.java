/**
 * @prjectName  : 롯데마트 온라인 쇼핑몰 lottemart-bos
 * @since    : 2016
 * @Description : SCM 기획전 관리
 * @author : LJH
 * @Copyright (C) 2011 ~ 2012 lottemart All right reserved.
 * </pre>
 */
package com.lottemart.epc.exhibition.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.views.AjaxJsonModelHelper;
import lcn.module.framework.property.ConfigurationService;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lcnjf.util.StringUtil;
import com.lottemart.common.code.service.CommonCodeService;
import com.lottemart.common.login.model.LoginSession;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.service.CommonService;
import com.lottemart.epc.edi.product.service.CommonProductService;
import com.lottemart.epc.exhibition.model.PSCMEXH010050VO;
import com.lottemart.epc.exhibition.service.PSCMEXH0100Service;

/**
 * @author jdj
 * @Class :
 * @version :
 */
@Controller("pscmexh0100Controller")
public class PSCMEXH0100Controller {
private static final Logger logger = LoggerFactory.getLogger(PSCMEXH0100Controller.class);

	@Autowired
	private CommonCodeService commonCodeService;
	@Autowired
	private PSCMEXH0100Service pscmexh0100Service;

	@Autowired
	private CommonService commonService;


	@Autowired
	private ConfigurationService config;

	@Resource(name="commonProductService")
	private CommonProductService commonProductService;

	@Autowired
	MessageSource messageSource;

	/**
	 * 기획전관리 조회 - for IBSheet
	 * @Description : 기획전관리 조회
	 * @Method Name : selectTemplateList
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exhibition/exhibitionSelect.do")
	public @ResponseBody Map selectExhibitionList(HttpServletRequest request) throws Exception {

        Map rtnMap = new HashMap<String, Object>();

		try {

			DataMap param = new DataMap(request);

			String rowsPerPage = StringUtil.null2str((String)param.get("rowsPerPage"), config.getString("count.row.per.page"));
			int startRow = ((Integer.parseInt((String)param.get("currentPage"))-1)*Integer.parseInt(rowsPerPage))+1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) -1;

			param.put("startDate", StringUtil.null2str((String) param.get("startDate")).replaceAll("-", ""));
			param.put("endDate", StringUtil.null2str((String) param.get("endDate")).replaceAll("-", ""));

			param.put("currentPage", (String)param.get("currentPage"));
			param.put("startRow", Integer.toString(startRow));
			param.put("endRow", Integer.toString(endRow));
			param.put("rowsPerPage", rowsPerPage);

			//승인여부
			String searchApplyCd[] = request.getParameterValues("searchApplyCd");
			param.put("searchApplyCd", searchApplyCd);

			// 데이터 조회
			List<DataMap> list = pscmexh0100Service.selectExhibitionList(param);

			rtnMap = JsonUtils.convertList2Json((List)list, -1, param.getString("currentPage"));

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
	 * 기획전관리 조회 - for IBSheet
	 * @Description : 기획전관리 조회
	 * @Method Name : selectTemplateList
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exhibition/exhibitionSelectSearch.do")
	public @ResponseBody Map exhibitionSelectSearch(HttpServletRequest request) throws Exception {

		Map rtnMap = new HashMap<String, Object>();

		try {

			DataMap param = new DataMap(request);

			String sessionKey = config.getString("lottemart.epc.session.key");
			EpcLoginVO epcLoginVO = null;
			epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

			// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
			if( epcLoginVO == null || epcLoginVO.getVendorId() == null )
			{
				logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
			}

			String rowsPerPage = StringUtil.null2str((String)param.get("rowsPerPage"), config.getString("count.row.per.page"));
			String currentPage = param.getString("currentPage");
			int startRow = ((Integer.parseInt((String)param.get("currentPage"))-1)*Integer.parseInt(rowsPerPage))+1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) -1;

			param.put("startDate", StringUtil.null2str((String) param.get("startDate")).replaceAll("-", ""));
			param.put("endDate", StringUtil.null2str((String) param.get("endDate")).replaceAll("-", ""));

			param.put("currentPage", (String)param.get("currentPage"));
			param.put("startRow", Integer.toString(startRow));
			param.put("endRow", Integer.toString(endRow));
			param.put("rowsPerPage", rowsPerPage);

			//승인여부
//			String searchApplyCd[] = request.getParameterValues("searchApplyCd");
//			param.put("searchApplyCd", searchApplyCd);

			ArrayList<String> aryList = null;

			if ( ( param.getString("vendorId") == null || "".equals(param.getString("vendorId")) ) && epcLoginVO != null )
			{
				aryList = new ArrayList<String>();

				for (int i = 0; i < epcLoginVO.getVendorId().length; i++)
				{
					aryList.add(epcLoginVO.getVendorId()[i]);
				}

				param.put("vendorId", aryList);
			}
			else
			{
				aryList = new ArrayList<String>();
				aryList.add(param.getString("vendorId"));

				param.put("vendorId", aryList);
			}


			// 데이터 조회
			List<DataMap> list = pscmexh0100Service.selectExhibitionList(param);

			int size = list.size();

			int totalCnt = 0 ;
			if(size > 0){
				totalCnt = list.size();
			}

			rtnMap = JsonUtils.convertList2Json((List)list, totalCnt, currentPage);

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
	 * 통합기획전관리 조회 - for IBSheet
	 * @Description : 통합기획전관리 조회
	 * @Method Name : selectTemplateList
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exhibition/exhibitionIntSelectSearch.do")
	public @ResponseBody Map exhibitionIntSelectSearch(HttpServletRequest request) throws Exception {

		Map rtnMap = new HashMap<String, Object>();

		try {

			DataMap param = new DataMap(request);

			String sessionKey = config.getString("lottemart.epc.session.key");
			EpcLoginVO epcLoginVO = null;
			epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

			// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
			if( epcLoginVO == null || epcLoginVO.getVendorId() == null )
			{
				logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
			}

			String rowsPerPage = StringUtil.null2str((String)param.get("rowsPerPage"), config.getString("count.row.per.page"));
			String currentPage = param.getString("currentPage");
			int startRow = ((Integer.parseInt((String)param.get("currentPage"))-1)*Integer.parseInt(rowsPerPage))+1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) -1;

			param.put("startDate", StringUtil.null2str((String) param.get("startDate")).replaceAll("-", ""));
			param.put("endDate", StringUtil.null2str((String) param.get("endDate")).replaceAll("-", ""));

			param.put("currentPage", (String)param.get("currentPage"));
			param.put("startRow", Integer.toString(startRow));
			param.put("endRow", Integer.toString(endRow));
			param.put("rowsPerPage", rowsPerPage);

			//승인여부
//			String searchApplyCd[] = request.getParameterValues("searchApplyCd");
//			param.put("searchApplyCd", searchApplyCd);

			ArrayList<String> aryList = null;

			if ( ( param.getString("vendorId") == null || "".equals(param.getString("vendorId")) ) && epcLoginVO != null )
			{
				aryList = new ArrayList<String>();

				for (int i = 0; i < epcLoginVO.getVendorId().length; i++)
				{
					aryList.add(epcLoginVO.getVendorId()[i]);
				}

				param.put("vendorId", aryList);
			}
			else
			{
				aryList = new ArrayList<String>();
				aryList.add(param.getString("vendorId"));

				param.put("vendorId", aryList);
			}


			// 데이터 조회
			List<DataMap> list = pscmexh0100Service.selectExhibitionIntList(param);

			int size = list.size();

			int totalCnt = 0 ;
			if(size > 0){
				totalCnt = list.size();
			}

			rtnMap = JsonUtils.convertList2Json((List)list, totalCnt, currentPage);

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
	 * 기획전 관리 페이지
	 * Desc : 기획전 관리 페이지
	 * @Method Name : exhibitionMng
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exhibition/exhibitionMng.do")
	public String exhibitionMng(HttpServletRequest request) throws Exception{

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if( epcLoginVO == null || epcLoginVO.getVendorId() == null )
		{
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}

		//승인여부
		List<DataMap> applyCdList = commonCodeService.getCodeList("SM314");
		request.setAttribute("applyCdList", applyCdList);

		// 협력업체콤보
		request.setAttribute("epcLoginVO", epcLoginVO);


		//return "newTemplate/PSCMTEM0100";
		return "exhibition/PSCMEXH0100";
	}


	/**
	 * 기획전 관리 페이지
	 * Desc : 기획전 관리 페이지
	 * @Method Name : exhibitionMng
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exhibition/exhibitionMngInt.do")
	public String exhibitionMngInt(HttpServletRequest request) throws Exception{

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if( epcLoginVO == null || epcLoginVO.getVendorId() == null )
		{
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}

		//승인여부
		List<DataMap> applyCdList = commonCodeService.getCodeList("SM314");
		request.setAttribute("applyCdList", applyCdList);

		// 협력업체콤보
		request.setAttribute("epcLoginVO", epcLoginVO);


		//return "newTemplate/PSCMTEM0100";
		return "exhibition/PSCMEXH0300";
	}

	/**
	 * 승인처리  - for IBSheet
	 * Desc : 승인처리
	 * @Method Name : updateApply
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exhibition/updateApply.do")
	public @ResponseBody JSONObject updateApply(HttpServletRequest request, HttpServletResponse response) throws Exception {

		JSONObject jObj = new JSONObject();
		int resultCnt = 0;

		try {
			resultCnt = pscmexh0100Service.updateApply(request);

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
			logger.error("", e);
		}
		return JsonUtils.getResultJson(jObj);
	}

    /**
	 * 승인반려사유 등록팝업  - for IBSheet
	 * Desc : 승인반려사유 등록팝업
	 * @Method Name : showCompanionForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exhibition/showCompanionForm.do")
	public String showCompanionForm(HttpServletRequest request) throws Exception{
		  return "exhibition/PSCMEXH010010";
	}

	/* ================ 1.기획전 기본정보 팝업 ======================================================= */

	/**
	 * 기획전 팝업 폼 (등록/수정)
	 * Desc : 기획전 팝업 폼 (등록/수정)
	 * @Method Name : categoryBlank
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exhibition/showExhibitionForm.do")
	public String showExhibitionForm(HttpServletRequest request) throws Exception{

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if( epcLoginVO == null || epcLoginVO.getVendorId() == null )
		{
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인페이지로 이동<<==");
		}

		DataMap param = new DataMap(request);
		DataMap map = new DataMap();

		String pageDiv = param.getString("pageDiv");
		String gubun = param.getString("gubun");
		String dispYnChk = param.getString("dispYnChk");
		String copyYn = param.getString("copyYn");

		if("".equals(pageDiv)){
			param.put("pageDiv", "insert");
		}

		//승인여부
		List<DataMap> applyCdList = commonCodeService.getCodeList("SM314");
		request.setAttribute("applyCdList", applyCdList);

		//기획전 카테고리(2 Depth)
		List<DataMap> exhibitionCategoryList = pscmexh0100Service.selectCategoryList();
		request.setAttribute("exhibitionCategoryList", exhibitionCategoryList);

		//카테고리분류
		List<DataMap> categoryTypeCdList = commonCodeService.getCodeList("SM315");
		request.setAttribute("categoryTypeCdList", categoryTypeCdList);

		//전시유형
//		List<DataMap> dispTypeList = commonCodeService.getCodeList("SM153");
//		request.setAttribute("dispTypeList", dispTypeList);

		//전시유형
		HashMap paramMap = new HashMap<String, String>();
		paramMap.put("refCd", "LET_1_REF"); // 필터링 컬럼
		paramMap.put("letCd", "SM147");  // 제외값
		paramMap.put("majorCd", "SM153"); // 메이저코드
		paramMap.put("useYn", "Y");  // 사용여부
		List<DataMap> dispTypeList = commonService.selectTetCodeRefList(paramMap);
		request.setAttribute("dispTypeList", dispTypeList);

		// 담당 MD
		request.setAttribute("teamList", commonProductService.selectNteamList(paramMap, request));

		// 협력업체콤보
		request.setAttribute("epcLoginVO", epcLoginVO);
		// 승인요청
		request.setAttribute("aprvStsCdChk", "00");


		if("U".equals(gubun) ){
			param.put("vendorId", param.getString("vendorIdView"));
		}

		if(pageDiv.equals("update")) {
			map = pscmexh0100Service.selectExhibitionInfo(param);
			request.setAttribute("dispTypeCdChk", map.getString("DISP_TYPE_CD"));
			request.setAttribute("vendorIdView", map.getString("VENDOR_ID"));
			request.setAttribute("aprvStsCd", map.getString("APRV_STS_CD"));
			request.setAttribute("aprvStsCdChk", map.getString("APRV_STS_CD"));
			request.setAttribute("_epc_image_path", 	config.getString("epc_image_path"));
		}

		request.setAttribute("copyYn",  copyYn);
		request.setAttribute("pageDiv", pageDiv);
		request.setAttribute("resultMap", map);
		String saveCode = StringUtil.null2str((String) param.get("saveCode"));
		request.setAttribute("saveCode",  StringUtil.null2str((String) param.get("saveCode")));

		String saveMsg = "";
		if("1".equals(saveCode)) {
			saveMsg = "정상 처리되었습니다.";
		}else if("0".equals(saveCode)) {
			saveMsg = "작업이 실패되었습니다.";
		}else if("-1".equals(saveCode)) {
			saveMsg = "같은 이미지명이 존재합니다.";
		}

		request.setAttribute("saveMsg" , saveMsg);
		return "exhibition/PSCMEXH010020";
	}

	/**
	 * 기획전 등록/수정 Tab
	 * Desc : 기획전 등록/수정
	 * @Method Name : basicExhibition
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exhibition/basicExhibition.do")
	public  String basicExhibition(HttpServletRequest request) throws Exception{

		LoginSession loginSession = LoginSession.getLoginSession(request);
		DataMap resultMap = new DataMap();
		String message = "";
		String code = "";
		int resultCnt = 0;

		String gubun = StringUtil.null2str(request.getParameter("gubun"));

		if("U".equals(gubun)){ // 기획전복사, 수정 시
			String mdiCategoryId = StringUtil.null2str(request.getParameter("mdiCategoryId"));
			String mdiMkdpSeq = StringUtil.null2str(request.getParameter("mdiMkdpSeq"));
			String mdiVendorId = StringUtil.null2str(request.getParameter("mdiVendorId"));
			String dispYn = StringUtil.null2str(request.getParameter("dispYn"));
			String copyYn = StringUtil.null2str(request.getParameter("copyYn"));



			logger.debug("dispYndispYndispYn1="+mdiCategoryId);
			logger.debug("dispYndispYndispYn2="+mdiMkdpSeq);
			logger.debug("dispYndispYndispYn3="+mdiVendorId);
			logger.debug("dispYndispYndispYn="+dispYn);
			logger.debug("copyYncopyYncopyYn="+copyYn);

			request.setAttribute("pageDiv",  		"update");
			request.setAttribute("gubun", 	 		gubun);
			request.setAttribute("mdiCategoryId",  	mdiCategoryId);
			request.setAttribute("mdiMkdpSeq",  	mdiMkdpSeq);
			request.setAttribute("mdiVendorId",  	mdiVendorId);
			request.setAttribute("dispYn",  		dispYn);
			request.setAttribute("copyYn",  		copyYn);

		}
		return "exhibition/PSCMEXH0200";

	}

	/**
	 * 기획전 등록/수정
	 * Desc : 기획전 등록/수정
	 * @Method Name : insertExhibition
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exhibition/insertExhibition.do")
	public  String insertExhibition(HttpServletRequest request) throws Exception{

//		LoginSession loginSession = LoginSession.getLoginSession(request);

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		DataMap resultMap = new DataMap();
		String message = "";
		String code = "";


		int resultCnt = 0;

		DataMap param = new DataMap(request);

		//String copyYn = param.getString("copyYn");
		try {

//			param.put("pageDiv", "insert");	// 지우고...

			param.put("tmpFile1Chk", param.getString("tmpFile1Chk"));
			param.put("tmpFile2Chk", param.getString("tmpFile2Chk"));
			param.put("tmpFile3Chk", param.getString("tmpFile3Chk"));
			param.put("ContentSeq1", param.getString("ContentSeq1"));
			param.put("ContentSeq2", param.getString("ContentSeq2"));
			param.put("ContentSeq3", param.getString("ContentSeq3"));
			param.put("ContentSeq4", param.getString("ContentSeq4"));
			param.put("ContentSeq5", param.getString("ContentSeq5"));
			param.put("categoryId", param.getString("categoryId2"));


			param.put("mkdpStartDate", StringUtil.null2str((String) param.get("mkdpStartDate")).replaceAll("-", ""));
			param.put("mkdpEndDate"  , StringUtil.null2str((String) param.get("mkdpEndDate")).replaceAll("-", ""));

			param.put("pageDiv", param.getString("pageDiv"));
			param.put("regId" , param.getString("vendorId"));
	    	param.put("modId" , param.getString("vendorId"));
	    	param.put("copyYn" , param.getString("copyYn"));
	    	//param.put("copyYn" , param.getString(""));


	    	//소카테고리 미선택시
	    	String categoryChildId = StringUtil.null2str((String) param.get("categoryChildId"));
	    	if("".equals(categoryChildId)){
	    		param.put("categoryChildId" ,StringUtil.null2str((String) param.get("categoryParentId")));
	    	}

			//저장
	    	resultCnt = pscmexh0100Service.insertExhibition(param, request);

			if (resultCnt > 0) {
				message  = messageSource.getMessage("msg.common.success.request", null, Locale.getDefault());
				code = "1";


			} else {
				message  = messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault());
				code = "0";
			}

 		} catch (Exception e) {
			message  = messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault());
			code = "-1";
			resultMap.put("Code", -1);
			resultMap.put("Message", e.getMessage());
			logger.info("이미지 등록 에러:" ,e );
		}

		request.setAttribute("saveCode", code);
		request.setAttribute("saveMsg", message);
		request.setAttribute("resultMap", resultMap);
		String pCategoryChildId   = (String) param.get("categoryChildId");
		String pStrCd                 = (String) param.get("strCd");
		String pMkdpSeq             = (String) param.get("mkdpSeq");
		String pVendorId             = (String) param.get("vendorId");
		if("-1".equals(resultMap.getString("Code"))){
			return "redirect:/exhibition/showExhibitionForm.do?pageDiv=insert"+"&saveCode="+code;
		}else{
		return "redirect:/exhibition/showExhibitionForm.do?categoryId="+pCategoryChildId
				                                                           +"&strCd="+pStrCd
				                                                           +"&mkdpSeq="+pMkdpSeq
				                                                           +"&saveCode="+code
				                                                           +"&vendorId="+pVendorId
				                                                           +"&pageDiv=update";
		}
	}

	/**
	 * 소카테고리 가져오기
	 * Desc : 소카테고리 가져오기
	 * @Method Name : selectCategoryChildIdList
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exhibition/selectCategoryChildIdList.do")
	public ModelAndView selectCategoryChildIdList(HttpServletRequest request) throws Exception {

		DataMap param = new DataMap(request);
		param.put("categoryParentId", request.getParameter("categoryParentId"));

		// 소분류콤보 가져오기
		List<DataMap> categoryChildIdList = pscmexh0100Service.selectCategoryChildIdList(param);

		// json 결과 생성
		DataMap resultMap = new DataMap();
		resultMap.put("exhibitionCategoryChildList", categoryChildIdList);
		resultMap.put("comboNm", "so");

		return AjaxJsonModelHelper.create(resultMap);
	}



	/* ================ 2.구분자 정보 팝업 ======================================================= */
	/**
	 * 구분자 팝업 폼 (등록/수정)
	 * Desc : 구분자 팝업 폼 (등록/수정)
	 * @Method Name : showDivnForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exhibition/showDivnForm.do")
	public String showDivnForm(HttpServletRequest request) throws Exception{

		DataMap param = new DataMap(request);
		DataMap map = new DataMap();

		//상품템블릿
		List<DataMap> goodsTemplateList = commonCodeService.getCodeList("SM318");
		request.setAttribute("goodsTemplateList", goodsTemplateList);

		//구분자 노출유형
		List<DataMap> divnTypeList = commonCodeService.getCodeList("SM307");
		request.setAttribute("divnTypeList", divnTypeList);

		//구분자 전시대상
		List<DataMap> divnDispTargetList = commonCodeService.getCodeList("SM317");
		request.setAttribute("divnDispTargetList", divnDispTargetList);

		//상품전시 템플릿 이미지 경로
		request.setAttribute("exhibitionTemplePathInfo", config.getString("exhibitionTemple.image.url"));

		request.setAttribute("categoryId", param.get("categoryId"));
		request.setAttribute("strCd"     , "981");
		request.setAttribute("mkdpSeq"   , param.get("mkdpSeq"));
		request.setAttribute("vendorId"   , param.get("vendorIdView"));
		request.setAttribute("aprvStsCdChk"   , param.get("aprvStsCdChk"));

		request.setAttribute("mkdpStartDate"   , param.get("mkdpStartDate"));
		request.setAttribute("mkdpStartHh"   , param.get("mkdpStartHh"));
		request.setAttribute("mkdpStartMm"   , param.get("mkdpStartMm"));
		request.setAttribute("mkdpEndDate"   , param.get("mkdpEndDate"));
		request.setAttribute("mkdpEndHh"   , param.get("mkdpEndHh"));
		request.setAttribute("mkdpEndMm"   , param.get("mkdpEndMm"));
		request.setAttribute("dispYn"   , param.get("dispYn"));

		return "exhibition/PSCMEXH010030";
	}

	/**
	 * 구분자정보목록 가져오기(Ajax)
	 * Desc : 구분자정보목록 가져오기(Ajax)
	 * @Method Name : selectCategoryChildIdList
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exhibition/selectDivnInfoList.do")
	public ModelAndView selectDivnInfoList(HttpServletRequest request) throws Exception {

		DataMap param = new DataMap(request);

		// 구분자정보목록 가져오기
		List<DataMap> divnInfoList = pscmexh0100Service.selectDivnInfoList(param);

		// json 결과 생성
		DataMap resultMap = new DataMap();
		resultMap.put("divnInfoList", divnInfoList);

		return AjaxJsonModelHelper.create(resultMap);
	}

	/**
	 * 구분자정보 등록/수정
	 * Desc : 구분자정보 등록/수정
	 * @Method Name : insertDivnInfo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exhibition/insertDivnInfo.do")
	public ModelAndView insertDivnInfo(HttpServletRequest request) throws Exception{

		int resultCnt = 0;
		DataMap param = new DataMap(request);
		DataMap resultMap = new DataMap();

    	//저장
		param.put("regId", param.getString("vendorId"));
		param.put("modId", param.getString("vendorId"));


		try {
			resultCnt = pscmexh0100Service.insertDivnInfo(param);

		} catch (Exception e) {
			resultMap.put("resultCode", -1);
    		resultMap.put("resultMsg", e.getMessage());
			return AjaxJsonModelHelper.create(resultMap);
		}

		// json 결과 생성
    	if(resultCnt > 0){
    		resultMap.put("resultCode", 1);
    		resultMap.put("resultMsg", messageSource.getMessage("msg.common.success.request", null, Locale.getDefault()));	// 요청처리가 성공적으로 수행되었습니다.
    	}
    	else{
    		resultMap.put("resultCode", -1);
    		resultMap.put("resultMsg", messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault()));	// 요청처리를 실패하였습니다.
    	}
		return AjaxJsonModelHelper.create(resultMap);
	}


	/* ================ 3.이미지/HTML 정보 팝업 ======================================================= */
	/**
	 * 이미지/HTML 정보 팝업 폼 (조회)
	 * Desc : 이미지/HTML 정보 팝업 폼 (조회)
	 * @Method Name : showDivnForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exhibition/showImageHtmlContentsForm.do")
	public String showImageHtmlContentsForm(HttpServletRequest request) throws Exception{

		DataMap param = new DataMap(request);

		//구분자정보 가져오기
		List<DataMap> divnSeqList = pscmexh0100Service.selectDivnSeqList(param);
		request.setAttribute("divnSeqList", divnSeqList);

		request.setAttribute("categoryId", param.get("categoryId"));
		request.setAttribute("strCd"     , param.get("strCd"));
		request.setAttribute("mkdpSeq"   , param.get("mkdpSeq"));
		request.setAttribute("vendorId"   , param.get("vendorIdView"));
		request.setAttribute("aprvStsCdChk"   , param.get("aprvStsCdChk"));
		request.setAttribute("dispYn"   , param.get("dispYn"));

		request.setAttribute("imgDir", config.getString("online.product.image.url"));
		return "exhibition/PSCMEXH010040";
	}

	/**
	 * 상품 이미지 view
	 * @Description : 상품 개별 이미지를 보여준다
	 * @Method Name : prdImageDetailForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exhibition/prdImageDetailForm.do")
	public String prdImageDetailForm(HttpServletRequest request) throws Exception {
		return "exhibition/PSCMEXH010070";
	}


	/**
	 * 이미지/HTML 정보 - 기획전 내용(소개) 조회 - for IBSheet
	 * @Description : 이미지/HTML 정보 조회
	 * @Method Name : selectTemplateList
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exhibition/selectContentsList.do")
	public @ResponseBody Map selectContentsList(HttpServletRequest request) throws Exception {

        Map rtnMap = new HashMap<String, Object>();

		try {
			DataMap param = new DataMap(request);

			// 데이터 조회
			List<DataMap> list = pscmexh0100Service.selectContentsList(param);
			rtnMap = JsonUtils.convertList2Json((List)list, null, null);

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
	 * 이미지/HTML 정보 - 기획전내용(소개) 이미지 팝업 폼 (등록)
	 * Desc : 이미지/HTML 정보 - 기획전내용(소개) 이미지 팝업 폼 (등록)
	 * @Method Name : showRegContentsImageForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exhibition/showRegContentsImageForm.do")
	public String showRegContentsImageForm(HttpServletRequest request) throws Exception{

		DataMap param = new DataMap(request);
		DataMap map = new DataMap();

		DataMap mkdpInfoMap = new DataMap();  //기획전 정보 가져오기

		request.setAttribute("categoryId" , param.get("categoryId"));
		request.setAttribute("mkdpSeq"    , param.get("mkdpSeq"));
		request.setAttribute("contentsSeq", param.get("contentsSeq"));

		//컨텐츠구분코드(01:기획전 내용(소개), 03:기획전 배너 이미지, 02:구분자 HTML)
		String contentsDivnCd = (String) ((param.get("contentsDivnCd")==null)?"01":param.get("contentsDivnCd"));
		request.setAttribute("contentsDivnCd",  contentsDivnCd);

		//채널코드 가져오기
		List<DataMap> chCdList = commonCodeService.getCodeList("SM310");
		request.setAttribute("chCdList", chCdList);

		//구분자정보 가져오기
		List<DataMap> divnSeqList = pscmexh0100Service.selectDivnSeqList(param);
		request.setAttribute("divnSeqList", divnSeqList);

		String pageDiv = param.getSafeString("pageDiv");

		//기획전 전시 정보(종료일자) 가져오기
		param.put("categoryChildId", param.get("categoryId"));
		mkdpInfoMap = pscmexh0100Service.selectExhibitionInfo(param);

		if(pageDiv.equals("update")) {
			map = pscmexh0100Service.selectContentsInfo(param);

		}

		request.setAttribute("pageDiv"    , pageDiv);
		request.setAttribute("resultMap"  , map);
		request.setAttribute("mkdpInfoMap", mkdpInfoMap);

		String saveCode = StringUtil.null2str((String) param.get("saveCode"));
		request.setAttribute("saveCode",  StringUtil.null2str((String) param.get("saveCode")));
		String saveMsg = "";
		if("1".equals(saveCode)) {
			saveMsg = "정상 처리되었습니다.";
		}else if("0".equals(saveCode)) {
			saveMsg = "작업이 실패되었습니다.";
		}
		request.setAttribute("saveMsg" , saveMsg);

		String showPage = "";
		if("01".equals(contentsDivnCd)) showPage = "exhibition/PSCMEXH010041";
		else if("03".equals(contentsDivnCd)) showPage = "exhibition/PSCMEXH010046";
		else if("02".equals(contentsDivnCd)) showPage = "exhibition/PSCMEXH010042";

		return showPage;
	}

	/**
	 * 이미지/HTML 정보 - 기획전내용(소개) 이미지 저장처리
	 * Desc : 이미지/HTML 정보 - 기획전내용(소개) 이미지 저장처리
	 * @Method Name : insertContentsImage
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exhibition/insertContentsImage.do")
	public  String insertContentsImage(HttpServletRequest request) throws Exception{

		LoginSession loginSession = LoginSession.getLoginSession(request);
		DataMap resultMap = new DataMap();
		String message = "";
		String code = "";
		int resultCnt = 0;
		String contentsDivnCd = "";

		try {
			DataMap param = new DataMap(request);

			//컨텐츠구분코드(01:기획전 내용(소개), 03:기획전 배너 이미지, 02:구분자 HTML)
			contentsDivnCd = (String) ((param.get("contentsDivnCd")==null)?"01":param.get("contentsDivnCd"));
			param.put("mkdpStartDate", StringUtil.null2str((String) param.get("mkdpStartDate")).replaceAll("-", ""));
			param.put("mkdpEndDate"  , StringUtil.null2str((String) param.get("mkdpEndDate")).replaceAll("-", ""));

			String gudnImg = "";

	    	param.put("regId", loginSession.getAdminId());
	    	param.put("modId", loginSession.getAdminId());
	    	param.put("imgPath", gudnImg);

	    	// 저장
			resultCnt = pscmexh0100Service.insertContentsImage(param, request);

			if (resultCnt > 0) {
				message  = messageSource.getMessage("msg.common.success.request", null, Locale.getDefault());
				code = "1";
			} else {
				message  = messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault());
				code = "0";
			}

		} catch (Exception e) {
			resultMap.put("Code", -1);
			resultMap.put("Message", e.getMessage());

			code = "0";
			message = e.getMessage();
			logger.error("이미지 저장 오류 : ", e);
		}

		request.setAttribute("saveCode", code);
		request.setAttribute("saveMsg", message);
		request.setAttribute("resultMap", resultMap);

		String showPage = "";
		if("01".equals(contentsDivnCd) || "03".equals(contentsDivnCd)) showPage = "exhibition/PSCMEXH010041";
		else if("02".equals(contentsDivnCd)) showPage = "exhibition/PSCMEXH010042";

		return showPage;
	}

	/**
	 * 이미지/HTML 정보 - 기획전내용(소개) 이미지 삭제처리  - for IBSheet
	 * Desc : 이미지/HTML 정보 - 기획전내용(소개) 이미지 삭제처리
	 * @Method Name : deleteContentsImage
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exhibition/deleteContentsImage.do")
	public @ResponseBody JSONObject deleteContentsImage(HttpServletRequest request, HttpServletResponse response) throws Exception {

		JSONObject jObj = new JSONObject();
		int resultCnt = 0;

		try {
			resultCnt = pscmexh0100Service.deleteContentsImage(request);

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
			logger.error("", e);
		}
		return JsonUtils.getResultJson(jObj);
	}

	/* ================ 4.기획전 내용(소개 ) HTML 정보 팝업 ======================================================= */
	/**
	 * 이미지/HTML 정보 - 기획전내용(소개) HTML 팝업 폼 (등록)
	 * Desc : 이미지/HTML 정보 - 기획전내용(소개) HTML 팝업 폼 (등록)
	 * @Method Name : showRegContentsHtmlForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exhibition/showRegContentsHtmlForm.do")            ///기획전 내용(소개 ) HTML 정보 팝업 등록 exhibition/insertContentsHtml.do
	public String showRegContentsHtmlForm(HttpServletRequest request) throws Exception{

		DataMap param       = new DataMap(request);
		DataMap map         = new DataMap();
		DataMap mkdpInfoMap = new DataMap();

		request.setAttribute("categoryId" , param.get("categoryId"));
		request.setAttribute("mkdpSeq"    , param.get("mkdpSeq"));
		request.setAttribute("contentsSeq", param.get("contentsSeq"));

		//컨텐츠구분코드(01:기획전 내용(소개), 03:기획전 배너 이미지, 02:구분자 HTML)
		String contentsDivnCd = (String) ((param.get("contentsDivnCd")==null)?"01":param.get("contentsDivnCd"));
		request.setAttribute("contentsDivnCd",  contentsDivnCd);

		//채널코드 가져오기
		List<DataMap> chCdList = commonCodeService.getCodeList("SM310");
		request.setAttribute("chCdList", chCdList);

		String pageDiv = param.getSafeString("pageDiv");

		//기획전 전시 정보(종료일자) 가져오기
		param.put("categoryChildId", param.get("categoryId"));
		mkdpInfoMap = pscmexh0100Service.selectExhibitionInfo(param);

		if(pageDiv.equals("update")) {
			map = pscmexh0100Service.selectContentsInfo(param);

		}

		request.setAttribute("pageDiv"    , pageDiv);
		request.setAttribute("resultMap"  , map);
		request.setAttribute("mkdpInfoMap", mkdpInfoMap);

		String saveCode = StringUtil.null2str((String) param.get("saveCode"));
		request.setAttribute("saveCode",  StringUtil.null2str((String) param.get("saveCode")));
		String saveMsg = "";
		if("1".equals(saveCode)) {
			saveMsg = "정상 처리되었습니다.";
		}else if("0".equals(saveCode)) {
			saveMsg = "작업이 실패되었습니다.";
		}
		request.setAttribute("saveMsg" , saveMsg);

		return "exhibition/PSCMEXH010043";
	}

	/**
	 * 이미지/HTML 정보 - 기획전내용(소개) HTML 저장처리
	 * Desc : 이미지/HTML 정보 - 기획전내용(소개) HTML 저장처리
	 * @Method Name : insertContentsHtml
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exhibition/insertContentsHtml.do")
	public  String insertContentsHtml(HttpServletRequest request) throws Exception{

		LoginSession loginSession = LoginSession.getLoginSession(request);
		DataMap resultMap = new DataMap();
		String message = "";
		String code = "";
		int resultCnt = 0;

		String contentsDivnCd = "";

		try {
			DataMap param = new DataMap(request);

			//컨텐츠구분코드(01:기획전 내용(소개), 03:기획전 배너 이미지, 02:구분자 HTML)
			contentsDivnCd = (String) ((param.get("contentsDivnCd")==null)?"01":param.get("contentsDivnCd"));

			param.put("mkdpStartDate", StringUtil.null2str((String) param.get("mkdpStartDate")).replaceAll("-", ""));
			param.put("mkdpEndDate"  , StringUtil.null2str((String) param.get("mkdpEndDate")).replaceAll("-", ""));

	    	param.put("regId", loginSession.getAdminId());
	    	param.put("modId", loginSession.getAdminId());

	    	// 저장
			resultCnt = pscmexh0100Service.insertContentsImage(param, request);

			if (resultCnt > 0) {

				message  = messageSource.getMessage("msg.common.success.request", null, Locale.getDefault());
				code = "1";
			} else {
				message  = messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault());
				code = "0";
			}

		} catch (Exception e) {
			resultMap.put("Code", -1);
			resultMap.put("Message", e.getMessage());

			code = "0";
			message = e.getMessage();
		}

		request.setAttribute("saveCode", code);
		request.setAttribute("saveMsg", message);
		request.setAttribute("resultMap", resultMap);

		return "exhibition/PSCMEXH010043";
	}

	/* ================ 5.기획전 상품등록 팝업 ======================================================= */
	/**
	 * 기획전 상품등록 팝업
	 * Desc : 기획전 상품등록 팝업
	 * @Method Name : showExhibitionProdInfoForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exhibition/showExhibitionProdInfoForm.do")
	public String showExhibitionProdInfoForm(HttpServletRequest request) throws Exception{

		DataMap param = new DataMap(request);
		DataMap map = new DataMap();

		request.setAttribute("categoryId", param.get("categoryId"));
		request.setAttribute("strCd"       , param.get("strCd"));
		request.setAttribute("mkdpSeq"  , param.get("mkdpSeq"));

		//상품정렬방식 가져오기
		List<DataMap> prodSortCdList = commonCodeService.getCodeList("SM305");
		request.setAttribute("prodSortCdList", prodSortCdList);

		//구분자정보 가져오기
		List<DataMap> divnSeqList = pscmexh0100Service.selectDivnSeqList(param);
		request.setAttribute("divnSeqList", divnSeqList);

		// 점포 콤보 조회
		// 단일점포  ljh
 	    //request.setAttribute("storeList", commonService.selectOnlineStrCdList(null));
		//request.setAttribute("storeList", commonService.selectStrCdList_all(null));

		//기획전 기본정보 가져오기
		map = pscmexh0100Service.selectExhibitionInfo(param);

		request.setAttribute("resultMap", map);

		String saveCode = StringUtil.null2str((String) param.get("saveCode"));
		request.setAttribute("saveCode",  StringUtil.null2str((String) param.get("saveCode")));
		String saveMsg = "";
		if("1".equals(saveCode)) {
			saveMsg = "정상 처리되었습니다.";
		}else if("0".equals(saveCode)) {
			saveMsg = "작업이 실패되었습니다.";
		}
		request.setAttribute("saveMsg" , saveMsg);

		return "exhibition/PSCMEXH010045";
	}

	/**
	 * 기획전 상품등록 팝업 조회 - for IBSheet
	 * @Description : 기획전 상품등록 팝업 조회
	 * @Method Name : selectExhibitionProdInfoList
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exhibition/selectExhibitionProdInfoList.do")
	public @ResponseBody Map selectExhibitionProdInfoList(HttpServletRequest request) throws Exception {

        Map rtnMap = new HashMap<String, Object>();

		try {
			DataMap param = new DataMap(request);

			// 데이터 조회
			List<DataMap> list = pscmexh0100Service.selectExhibitionProdInfoList(param);

			int regProdCnt = 0;
			int soutYnCnt = 0;
			if(list.size() > 0){
				for(int i = 0; i<list.size(); i++ ){
					if( "Y".equals(list.get(i).getString("SOUT_YN"))){
						soutYnCnt++;
					}
				}
				regProdCnt = Integer.parseInt(list.get(0).getString("REG_PROD_CNT"));
			}

			rtnMap = JsonUtils.convertList2Json((List)list, null, null);

			// 성공
            rtnMap.put("result", true);
            rtnMap.put("regProdCnt", regProdCnt);
            rtnMap.put("soutYnCnt", soutYnCnt);

		} catch (Exception e) {
			// 작업오류
	        logger.error("error message --> " + e.getMessage());
	        rtnMap.put("result", false);
	        rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}



	/**
	 * 기획전 상품등록 팝업 조회 - for IBSheet
	 * @Description : 기획전 상품등록 팝업 조회
	 * @Method Name : selectExhibitionProdList
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exhibition/selectExhibitionProdList1.do")
	public @ResponseBody Map selectExhibitionProdList1(HttpServletRequest request) throws Exception {

		Map rtnMap = new HashMap<String, Object>();

		try {
			DataMap param = new DataMap(request);

			String sessionKey = config.getString("lottemart.epc.session.key");
			EpcLoginVO epcLoginVO = null;
			epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

			// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
			if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
				logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
			}


			ArrayList<String> aryList = null;

			if ( ( param.getString("vendorId") == null || "".equals(param.getString("vendorId")) ) && epcLoginVO != null )
			{
				aryList = new ArrayList<String>();

				for (int i = 0; i < epcLoginVO.getVendorId().length; i++)
				{
					aryList.add(epcLoginVO.getVendorId()[i]);
				}

				param.put("vendorId", aryList);
			}
			else
			{
				aryList = new ArrayList<String>();
				aryList.add(param.getString("vendorId"));

				param.put("vendorId", aryList);
			}


			// 데이터 조회
			List<DataMap> list = pscmexh0100Service.selectExhibitionProdList1(param);

			rtnMap = JsonUtils.convertList2Json((List)list, null, null);

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
	 * 기획전 상품등록 팝업 조회 - for IBSheet
	 * @Description : 기획전 상품등록 팝업 조회
	 * @Method Name : selectExhibitionProdList
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exhibition/selectExhibitionProdList2.do")
	public @ResponseBody Map selectExhibitionProdList2(HttpServletRequest request) throws Exception {

		Map rtnMap = new HashMap<String, Object>();

		try {
			DataMap param = new DataMap(request);

			String sessionKey = config.getString("lottemart.epc.session.key");
			EpcLoginVO epcLoginVO = null;
			epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

			// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
			if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
				logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
			}

			ArrayList<String> aryList = null;

			if ( ( param.getString("vendorId") == null || "".equals(param.getString("vendorId")) ) && epcLoginVO != null )
			{
				aryList = new ArrayList<String>();

				for (int i = 0; i < epcLoginVO.getVendorId().length; i++)
				{
					aryList.add(epcLoginVO.getVendorId()[i]);
				}

				param.put("vendorId", aryList);
			}
			else
			{
				aryList = new ArrayList<String>();
				aryList.add(param.getString("vendorId"));

				param.put("vendorId", aryList);
			}

			// 데이터 조회
			List<DataMap> list = pscmexh0100Service.selectExhibitionProdList2(param);

			rtnMap = JsonUtils.convertList2Json((List)list, null, null);

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
	 * 기획전 상품 등록
	 * Desc : 기획전 상품 등록
	 * @Method Name : updateAprv
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exhibition/updateAprv.do")
	public @ResponseBody JSONObject updateAprv(HttpServletRequest request, HttpServletResponse response) throws Exception {

		JSONObject jObj = new JSONObject();
		int resultCnt = 0;

		try {
			resultCnt = pscmexh0100Service.updateAprv(request);

			if( resultCnt > 0){
				jObj.put("Code", 1);

				if(resultCnt>1) jObj.put("Message", (resultCnt) + "건의 " + messageSource.getMessage("msg.common.success.request", null, Locale.getDefault()));
				else jObj.put("Message", messageSource.getMessage("msg.common.success.request", null, Locale.getDefault()));
			}
			else{
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


	/**
	 * 통합기획전 상품 등록
	 * Desc : 통합기획전 상품 등록
	 * @Method Name : insertIntExhibitionProdInfo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exhibition/updateStsCd.do")
	public @ResponseBody JSONObject updateStsCd(HttpServletRequest request, HttpServletResponse response) throws Exception {

		JSONObject jObj = new JSONObject();
		int resultCnt = 0;

		try {
			resultCnt = pscmexh0100Service.updateStsCd(request);

			if( resultCnt > 0){
				jObj.put("Code", 1);

				if(resultCnt>1) jObj.put("Message", (resultCnt) + "건의 " + messageSource.getMessage("msg.common.success.request", null, Locale.getDefault()));
				else jObj.put("Message", messageSource.getMessage("msg.common.success.request", null, Locale.getDefault()));
			}
			else{
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



	/**
	 * 통합기획전 상품 삭제
	 * Desc : 통합기획전 상품 삭제
	 * @Method Name : insertIntExhibitionProdInfo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exhibition/deleteProdCd.do")
	public @ResponseBody JSONObject deleteProdCd(HttpServletRequest request, HttpServletResponse response) throws Exception {

		JSONObject jObj = new JSONObject();
		int resultCnt = 0;

		try {
			resultCnt = pscmexh0100Service.updateStsCd(request);
			resultCnt = pscmexh0100Service.deleteProdCd(request);

			if( resultCnt > 0){
				jObj.put("Code", 1);

				if(resultCnt>1) jObj.put("Message", (resultCnt) + "건의 " + messageSource.getMessage("msg.common.success.request", null, Locale.getDefault()));
				else jObj.put("Message", messageSource.getMessage("msg.common.success.request", null, Locale.getDefault()));
			}
			else{
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




	/**
	 * 기획전 상품 등록
	 * Desc : 기획전 상품 등록
	 * @Method Name : insertExhibitionProdInfo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exhibition/insertExhibitionProdInfo.do")
	public @ResponseBody JSONObject insertExhibitionProdInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {

		JSONObject jObj = new JSONObject();
		int resultCnt = 0;

		try {
			resultCnt = pscmexh0100Service.insertExhibitionProdInfo(request);

			if( resultCnt > 0){
				jObj.put("Code", 1);

				if(resultCnt>1) jObj.put("Message", (resultCnt) + "건의 " + messageSource.getMessage("msg.common.success.request", null, Locale.getDefault()));
				else jObj.put("Message", messageSource.getMessage("msg.common.success.request", null, Locale.getDefault()));
			}
			else{
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


	/**
	 * 통합기획전 상품 등록
	 * Desc : 통합기획전 상품 등록
	 * @Method Name : insertIntExhibitionProdInfo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exhibition/insertIntExhibitionProdInfo.do")
	public @ResponseBody JSONObject insertIntExhibitionProdInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {

		JSONObject jObj = new JSONObject();
		int resultCnt = 0;

		try {
			resultCnt = pscmexh0100Service.insertIntExhibitionProdInfo(request);

			if( resultCnt > 0){
				jObj.put("Code", 1);

				if(resultCnt>1) jObj.put("Message", (resultCnt) + "건의 " + messageSource.getMessage("msg.common.success.request", null, Locale.getDefault()));
				else jObj.put("Message", messageSource.getMessage("msg.common.success.request", null, Locale.getDefault()));
			}
			else{
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

	/**
	 * 기획전 상품 삭제  - for IBSheet
	 * Desc : 기획전 상품 삭제
	 * @Method Name : deleteExhibitionProdInfo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exhibition/deleteExhibitionProdInfo.do")
	public @ResponseBody JSONObject deleteExhibitionProdInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {

		JSONObject jObj = new JSONObject();
		int resultCnt = 0;

		try {
			resultCnt = pscmexh0100Service.deleteExhibitionProdInfo(request);

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
			logger.error("", e);
		}
		return JsonUtils.getResultJson(jObj);
	}

	/**
	 * 기획전 상품등록 상품정렬방식 조회
	 * Desc : 기획전 상품등록 상품정렬방식 조회          selectCategoryChildIdList.do 참고
	 * @Method Name : selectDivnProdSortInfo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exhibition/selectDivnProdSortInfo.do")
	public ModelAndView selectDivnProdSortInfo(HttpServletRequest request) throws Exception {

		DataMap param = new DataMap(request);
		DataMap selectDivnProdSortInfo = pscmexh0100Service.selectDivnProdSortInfo(param);

		// json 결과 생성
		DataMap resultMap = new DataMap();
		resultMap.put("divnProdSortInfo", selectDivnProdSortInfo);

		return AjaxJsonModelHelper.create(resultMap);
	}

	/**
	 * 기획전 상품등록 - 점포별 상품정보 조회(Ajax)
	 * Desc : 기획전 상품등록 - 점포별 상품정보 조회(Ajax)
	 * @Method Name : searchProdItemList
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exhibition/selectProdItemList.do")
	public ModelAndView selectProdItemList(HttpServletRequest request) throws Exception {

		DataMap param = new DataMap(request);

		List<DataMap> allList = new ArrayList<DataMap>();

		String[] arrProdCd     = (String[])request.getParameterValues("prodCdArr");
		String[] arrProdDivnCd = (String[])request.getParameterValues("prodDivnCdArr");

		// 상품별로 조회
		for (int i = 0; i < arrProdCd.length; i++) {
			String sProdCd     = arrProdCd[i];		// 상품정보 배열
			String sProdDivnCd = arrProdDivnCd[i];	// 상품구분코드 배열

			param.put("prodCd",     sProdCd);
			param.put("prodDivnCd", sProdDivnCd);

			// 상품정보 조회
			List<DataMap> prodItemList = pscmexh0100Service.selectProdItemList(param);

			// 조회한 상품정보를 설정
			for (int j = 0; j < prodItemList.size(); j++) {
				DataMap dmItemInfo = (DataMap)prodItemList.get(j);
				allList.add(dmItemInfo);
			}
		}

		// json 결과 생성
		DataMap resultMap = new DataMap();
		resultMap.put("prodItemList", allList);

		return AjaxJsonModelHelper.create(resultMap);
	}


    /**
     * Desc : 기획전 상품등록 엑셀다운로드
     * @Method Name : exportPSCMEXH010045Excel
     * @param request
     * @param response
     * @throws Exception
     * @param
     * @return
     * @exception Exception
     */
    @RequestMapping("exhibition/exportPSCMEXH010045Excel.do")
    public void exportPSCMEXH010045Excel(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	try{
	    	DataMap param = new DataMap(request);

	    	// 데이터 조회
			List<DataMap> list = pscmexh0100Service.selectExhibitionProdInfoList(param);
			JsonUtils.IbsExcelDownload((List)list, request, response);
	    } catch (Exception e) {
			// 작업오류
	        logger.error("error message --> " + e.getMessage());
		}
    }

	/**
	 * Desc : 협력사담당 MD LIST
	 * @Method Name : mdList
	 * @param request
	 * @throws Exception
	 * @return exhibition/PSCMEXH010050
	 */
	@RequestMapping("exhibition/mdList.do")
	public String mdList(HttpServletRequest request) throws Exception {
		return "exhibition/PSCMEXH010050";
	}

	/**
	 * Desc : 협력사 MD list 가져오기
	 *
	 * @Method Name : search
	 * @param request
	 * @throws Exception
	 * @return rtnMap
	 */
	@RequestMapping("exhibition/mdSearch.do")
	public @ResponseBody Map mdSearch(HttpServletRequest request) throws Exception {

		Map rtnMap = new HashMap<String, Object>();

		try {

			DataMap param = new DataMap(request);
			String rowsPerPage = StringUtil.null2str((String) param.get("rowsPerPage"),config.getString("count.row.per.page"));

			int startRow = ((Integer.parseInt((String) param.get("currentPage")) - 1) * Integer.parseInt(rowsPerPage)) + 1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) - 1;

			param.put("currentPage", (String) param.get("currentPage"));
			param.put("adminName", (String) param.get("adminName"));
			param.put("adminTypeCd", (String) param.get("adminTypeCd"));
			param.put("startRow", Integer.toString(startRow));
			param.put("endRow", Integer.toString(endRow));
			param.put("rowsPerPage", rowsPerPage);

			// 전체 조회 건수
			int totalCnt = pscmexh0100Service.selectMdTotalCnt((Map) param);
			param.put("totalCount", Integer.toString(totalCnt));

			// 리스트 조회
			List<PSCMEXH010050VO> cooperationList = pscmexh0100Service.selectMdList((Map) param);
			rtnMap = JsonUtils.convertList2Json((List) cooperationList, totalCnt, param.getString("currentPage"));

			// 처리성공
			rtnMap.put("result", true);

		} catch (Exception e) {
			// 작업오류
						logger.error("error --> " + e.getMessage());
						rtnMap.put("result", false);
						rtnMap.put("Message", e.getMessage());
			}
		return rtnMap;
	}


	/**
	 * Desc : 기획전 상품등록 팝업
	 *
	 * @Method Name : selectExhibitionProduct
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("exhibition/selectExhibitionProduct.do")
	public String selectSelPointView(HttpServletRequest request)
			throws Exception {
		DataMap param = new DataMap(request);


		try {

			String sessionKey = config.getString("lottemart.epc.session.key");
			EpcLoginVO epcLoginVO = null;
			epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

			// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
			if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
				logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
			}

			param.put("categoryId", param.getString("mdiCategoryId"));
			param.put("mkdpSeq", param.getString("mdiMkdpSeq"));

			String mkdpNm = pscmexh0100Service.mkdpNm(param);

			// 구분자 조회
			List<DataMap> divnSeqlist = pscmexh0100Service.selectDivnSeqlist(param);
			request.setAttribute("divnSeqlist", divnSeqlist);
//			String mkdpNm = new String(request.getParameter("mdiMkdpNm").getBytes("iso-8859-1"), "utf-8");

			//승인여부
			List<DataMap> applyCdList = commonCodeService.getCodeList("SM352");
			request.setAttribute("applyCdList", applyCdList);

			request.setAttribute("categoryId", param.getString("mdiCategoryId"));
			request.setAttribute("mkdpSeq", param.getString("mdiMkdpSeq"));
			request.setAttribute("mkdpNm", mkdpNm);

			request.setAttribute("startDatePop", param.getString("startDatePop"));
			request.setAttribute("endDatePop", param.getString("endDatePop"));
//			request.setAttribute("vendorId", param.getString("mdiVendorId"));
			request.setAttribute("imgDir", config.getString("online.product.image.url"));


		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
		}
		return "exhibition/PSCMEXH010060";
	}

	/**
	 * 이미지 미리보기 팝업 폼
	 * Desc : 이미지 미리보기 팝업 폼 (jsp 에서 동일하게 imgPath 파라미터 넘겨줌)
	 * @Method Name : categoryBlank
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exhibition/viewImageForm.do")
	public String viewImageForm(HttpServletRequest request) throws Exception{

		DataMap param = new DataMap(request);
		request.setAttribute("imgPath",  param.getString("imgUrl"));
		request.setAttribute("epc_image_path", 	config.getString("epc_image_path"));
		return "exhibition/PSCMEXH010080";
	}

	/**
	 * Desc : 협력사 게시판 작성자 폼
	 * @Method Name : writerList
	 * @param request
	 * @throws Exception
	 * @return board/cooperation/PBOMBRD001405
	 */
	@RequestMapping("exhibition/adminForm.do")
	public String writerList(HttpServletRequest request) throws Exception {
		return "exhibition/PSCMEXH010090";
	}

	@RequestMapping("exhibition/adminSearch.do")
	public @ResponseBody Map adminSearch(HttpServletRequest request) throws Exception {

		Map rtnMap = new HashMap<String, Object>();

		try {

			DataMap param = new DataMap(request);
			String rowsPerPage = StringUtil.null2str((String) param.get("rowsPerPage"),config.getString("count.row.per.page"));

			int startRow = ((Integer.parseInt((String) param.get("currentPage")) - 1) * Integer.parseInt(rowsPerPage)) + 1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) - 1;

			param.put("currentPage", (String) param.get("currentPage"));
			param.put("adminName", (String) param.get("adminName"));
			param.put("adminTypeCd", (String) param.get("adminTypeCd"));
			param.put("startRow", Integer.toString(startRow));
			param.put("endRow", Integer.toString(endRow));
			param.put("rowsPerPage", rowsPerPage);

			// 전체 조회 건수
			int totalCnt = pscmexh0100Service.selectAdminInfoTotalCnt((Map) param);
			param.put("totalCount", Integer.toString(totalCnt));

			// 리스트 조회
			List<DataMap> cooperationList = pscmexh0100Service.selectAdminInfoList((Map) param);
			rtnMap = JsonUtils.convertList2Json((List) cooperationList, totalCnt, param.getString("currentPage"));

			// 처리성공
			rtnMap.put("result", true);

		} catch (Exception e) {
			// 작업오류
						logger.error("error --> " + e.getMessage());
						rtnMap.put("result", false);
						rtnMap.put("Message", e.getMessage());
			}
		return rtnMap;
	}

}
