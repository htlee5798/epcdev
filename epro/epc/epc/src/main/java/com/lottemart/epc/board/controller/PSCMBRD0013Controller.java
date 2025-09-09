/**
 * @prjectName  : lottemart 프로젝트
 * @since       : 2016. 05 31. 오후 2:30:50
 * @author      : kslee
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */


package com.lottemart.epc.board.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lottemart.common.file.model.FileVO;
import com.lottemart.common.file.service.FileMngService;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.board.model.PSCMBRD0013VO;
import com.lottemart.epc.board.service.PSCMBRD0013Service;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.util.LoginUtil;
import com.lottemart.epc.common.util.SecureUtil;

import lcn.module.common.util.DateUtil;
import lcn.module.common.util.StringUtil;
import lcn.module.common.views.AjaxJsonModelHelper;
import lcn.module.framework.property.ConfigurationService;
import net.sf.json.JSONObject;

/**
 * @Class Name : PSCMBRD0013Controller
 * @Description : 상품평 Controller 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 19. 오후 2:30:50 wcpark
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */


@Controller
public class PSCMBRD0013Controller {

	private static final Logger logger = LoggerFactory.getLogger(PSCMBRD0013Controller.class);

	@Autowired
	private ConfigurationService config;
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PSCMBRD0013Service pscmbrd0013Service;
	// 첨부파일 관련 2014.10.06 박지혜 추가
	@Resource(name="FileMngService")
	private FileMngService fileMngService;

	/**
	 * Desc : 상품평 게시판 페이지
	 *
	 * @Method Name : selectRecommList
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/board/selectProductList.do")
	public String selectProductList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return "board/PSCMBRD0013";
	}


	/**
	 * Desc : 일반상품평 목록
	 *
	 * @Method Name : selectFreshList
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/board/selectFreshList.do")
	public String selectFreshList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);

		String endDate = DateUtil.getToday("yyyy-MM-dd");
		String startDate = DateUtil.formatDate(DateUtil.addDay(endDate, -7), "-");

		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);

		// 문의유형(대) 공통코드 조회
		//List<DataMap> codeList1 = commonCodeService.getCodeList("QA001");
		//request.setAttribute("codeList1", codeList1);
		return "board/PSCMBRD001301";
	}

	/**
	 * Desc : 체험형 상품평 목록
	 *
	 * @Method Name : selectExprList
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/board/selectExprList.do")
	public String selectExprList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);

		String endDate = DateUtil.getToday("yyyy-MM-dd");
		String startDate = DateUtil.formatDate(DateUtil.addDay(endDate, -7), "-");

		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);

		return "board/PSCMBRD001302";
	}

	/**
	 * Desc : 상품평 목록을 조회
	 * @Method Name : selectProductSearch
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/board/productSearch.do")
	public @ResponseBody Map selectProductSearch(HttpServletRequest request) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		Map rtnMap = new HashMap<String, Object>();

		try {
			DataMap paramMap = new DataMap(request);

			// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
			if( epcLoginVO == null || epcLoginVO.getVendorId() == null ) {
				logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
			} else {
				request.setAttribute("epcLoginVO", epcLoginVO);
				logger.debug("vendorId ==>" +paramMap.getString("searchVendorId") + "<==");

				// 협력사코드 전체를 선택한 경우 로그인 세션에 있는 협력사 코드 전체를 설정한다.
				if("".equals(paramMap.getString("searchVendorId")))	{
					paramMap.put("vendorId", LoginUtil.getVendorList(epcLoginVO)); //협력업체코드
				} else	{
					ArrayList<String> vendorList = new ArrayList<String>();
					vendorList.add(paramMap.getString("searchVendorId"));
					paramMap.put("vendorId", vendorList); //협력업체코드
				}
			}

			String rowsPerPage = StringUtil.null2str((String)paramMap.get("rowsPerPage"), config.getString("count.row.per.page"));

			int startRow = ((Integer.parseInt((String)paramMap.get("currentPage"))-1)*Integer.parseInt(rowsPerPage))+1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) -1;

			paramMap.put("currentPage", 	(String)paramMap.get("currentPage"));
			paramMap.put("startRow", 		Integer.toString(startRow));
			paramMap.put("endRow", 			Integer.toString(endRow));
			paramMap.put("rowsPerPage", 	rowsPerPage);
			paramMap.put("userSrch", 		paramMap.getString("userSrch"));
			paramMap.put("userSrchNm", 		paramMap.getString("userSrchNm"));
			paramMap.put("startDate", 		paramMap.getString("startDate").replaceAll("-", ""));
			paramMap.put("endDate", 		paramMap.getString("endDate").replaceAll("-", ""));
			paramMap.put("prodSrch",		paramMap.getString("prodSrch"));
			paramMap.put("prodSrchNm", 		paramMap.getString("prodSrchNm"));
			paramMap.put("searchVendorId", 	paramMap.getString("searchVendorId"));
			// mallDivnCd 몰구분(00001:롯데마트몰, 00002:토이저러스몰) 추가
			paramMap.put("mallDivnCd", 		paramMap.getString("mallDivnCd"));

			 // 전체 조회 건수
	        int totalCnt = pscmbrd0013Service.selectProductTotalCnt((Map)paramMap);

	        paramMap.put( "totalCount", Integer.toString(totalCnt));

			// 리스트 조회
			List<PSCMBRD0013VO> list = pscmbrd0013Service.selectProductSearch((Map)paramMap);
			rtnMap = JsonUtils.convertList2Json((List)list, totalCnt, paramMap.getString("currentPage"));

			rtnMap.put("result", true);
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
		    rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}

	/**
	 * Desc : 체험형 상품평 조회
	 * @Method Name : selectExprSearch
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/board/exprSearch.do")
	public @ResponseBody Map selectExprSearch(HttpServletRequest request) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		Map rtnMap = new HashMap<String, Object>();

		try {
			DataMap paramMap = new DataMap(request);

			// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
			if( epcLoginVO == null || epcLoginVO.getVendorId() == null ) {
				logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
			} else {
				request.setAttribute("epcLoginVO", epcLoginVO);
				logger.debug("vendorId ==>" +paramMap.getString("searchVendorId") + "<==");

				// 협력사코드 전체를 선택한 경우 로그인 세션에 있는 협력사 코드 전체를 설정한다.
				if("".equals(paramMap.getString("searchVendorId")))	{
					paramMap.put("vendorId", LoginUtil.getVendorList(epcLoginVO)); //협력업체코드
				} else	{
					ArrayList<String> vendorList = new ArrayList<String>();
					vendorList.add(paramMap.getString("searchVendorId"));
					paramMap.put("vendorId", vendorList); //협력업체코드
				}
			}

			String rowsPerPage = StringUtil.null2str((String)paramMap.get("rowsPerPage"), config.getString("count.row.per.page"));

			int startRow = ((Integer.parseInt((String)paramMap.get("currentPage"))-1)*Integer.parseInt(rowsPerPage))+1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) -1;

			paramMap.put("currentPage", (String)paramMap.get("currentPage"));
			paramMap.put("startRow", Integer.toString(startRow));
			paramMap.put("endRow", Integer.toString(endRow));
			paramMap.put("rowsPerPage", rowsPerPage);
			paramMap.put("reviewId", paramMap.getString("reviewId"));
			paramMap.put("startDt", paramMap.getString("startDate").replaceAll("-", ""));
			paramMap.put("endDt", paramMap.getString("endDate").replaceAll("-", ""));
			paramMap.put("prodSrch", paramMap.getString("prodSrch"));
			paramMap.put("prodSrchNm", paramMap.getString("prodSrchNm"));

			 // 전체 조회 건수
	        int totalCnt = pscmbrd0013Service.selectExprTotalCnt((Map)paramMap);

	        paramMap.put( "totalCount", Integer.toString(totalCnt));

			// 리스트 조회
			List<PSCMBRD0013VO> productList = pscmbrd0013Service.selectExprSearch((Map)paramMap);

			rtnMap = JsonUtils.convertList2Json((List)productList, totalCnt, paramMap.getString("currentPage"));


			rtnMap.put("result", true);
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
		    rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}

	/**
	 * Desc : 상품평 게시판 상세 페이지
	 * @Method Name : view
	 * @param request,response
	 * @throws Exception
	 * @return board/PSCPBRD0014
	 * 2014.10.06 박지혜 수정
	 */
	@RequestMapping(value = "/board/productView.do")
	public String productView(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String recommSeq = request.getParameter("recommSeq");
		String atchFileId = "";

		PSCMBRD0013VO faqViewInfo = pscmbrd0013Service.selectProductView(recommSeq);
		faqViewInfo.setAdminMemo(StringUtil.null2str(faqViewInfo.getAdminMemo()));

		atchFileId = StringUtil.null2str(faqViewInfo.getAtchFileId());

		//이미지 관련 URL
		String IMG_URL =  config.getString("imageqc.root.url");
		String PHOTOREVIEW_FILE_UPLOAD_PATH =  config.getString("imageqc.sub.url.productreview");
		String URL  = IMG_URL+ PHOTOREVIEW_FILE_UPLOAD_PATH+faqViewInfo.getFileUrl() ;

		//여러개의 파일을 가져올경우....
		List<FileVO> fileList = null;
		FileVO filevo = new FileVO();
		if(!"".equals(atchFileId)){
			filevo.setAtchFileId(atchFileId);
			fileList = this.fileMngService.selectImageFileList(filevo);
		}

		request.setAttribute("data", faqViewInfo);
		request.setAttribute("fileList", fileList);
		request.setAttribute("URL", URL);

		return "board/PSCPBRD001301";
	}

	/**
	 * Desc : 체험형 상품평 게시판 상세 페이지
	 * @Method Name : exprView
	 * @param request,response
	 * @throws Exception
	 * @return board/PSCPBRD0014
	 */
	@RequestMapping(value = "/board/exprView.do")
	public String exprView(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String recommSeq = request.getParameter("recommSeq");

		PSCMBRD0013VO faqViewInfo = pscmbrd0013Service.selectExprView(recommSeq);

		//이미지 관련 URL
		String IMG_URL =  config.getString("imageqc.root.url");
		String PHOTOREVIEW_FILE_UPLOAD_PATH =  config.getString("imageqc.sub.url.productreview");
		faqViewInfo.setFileUrl(IMG_URL+ PHOTOREVIEW_FILE_UPLOAD_PATH+faqViewInfo.getFileUrl());

		request.setAttribute("data", faqViewInfo);

		return "board/PSCMBRD001303";
	}

	/**
	 * Desc : 체험형 상품평 게시판 수정
	 * @Method Name : exprProdInfoUpdate
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("board/exprProdInfoUpdate.do")
	public ModelAndView exprProdInfoUpdate(HttpServletRequest request) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);



		String message = "";
		String recommSeq = request.getParameter("recommSeq");//글번호
		String delYN = request.getParameter("delYN");

		PSCMBRD0013VO bean = new PSCMBRD0013VO();
		bean.setRecommSeq(recommSeq);
		bean.setDelYN(StringUtil.null2str(delYN));
		bean.setModId(epcLoginVO.getAdminId());

		try {
	    	int resultCnt = pscmbrd0013Service.updateExprProd(bean);
	    	message = messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault());
	    	if(resultCnt > 0){
	    		return AjaxJsonModelHelper.create("");
	    	}else{
	    		return AjaxJsonModelHelper.create(message);
	    	}
		} catch (Exception e) {
			return AjaxJsonModelHelper.create(messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault()));
		}
    }

	/**
	 * Desc : 상품평 게시판 우수 상품평선정 및 해제 업데이트 IBSheet
	 * @Method Name : exlnSltYn
	 * @param request,response
	 * @throws Exception
	 * @return gdRes
	 */
	@RequestMapping("/board/exlnSltYn.do")
	public @ResponseBody JSONObject ExlnSltYn(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jObj = new JSONObject();

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		try {

			//Sheet에서 수정으로 체크된 게시물의 게시물 번호
			String[] recommSeq = request.getParameterValues("recommSeq");
			String   exlnSltYn = request.getParameter("exlnSltYn");

			//수정할 게시물 목록 가공
			List<PSCMBRD0013VO> productList = new ArrayList<PSCMBRD0013VO>();

			for ( int i = 0; i < recommSeq.length; i++) {
				PSCMBRD0013VO bean = new PSCMBRD0013VO();
				bean.setRecommSeq(recommSeq[i]);
				bean.setExlnSltYn(exlnSltYn);
				bean.setModId(epcLoginVO.getAdminId());
				productList.add(bean);
			}

			//게시물 수정
			int resultCnt = 0;
			resultCnt = pscmbrd0013Service.updateExlnSltYn(productList);

			//처리 결과 메세지 생성
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
	 * Desc : 상품평 게시판 업데이트
	 * @Method Name : infoUpdate
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("board/productInfoUpdate.do")
	public ModelAndView productInfoUpdate(HttpServletRequest request) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);



		String message = "";
		String recommSeq = request.getParameter("recommSeq");//글번호
		String delYN = request.getParameter("delYN");
		//String recommPnt = request.getParameter("recommPnt");
		//String title = request.getParameter("title");//제목
		//String recommContent = request.getParameter("recommContent");
		//String adminMemo = request.getParameter("adminMemo");//정렬순서
		//2014.10.29 박지혜 추가
		String mainDpSeq = request.getParameter("mainDpSeq");
		String exlnSltYn = request.getParameter("exlnSltYn");

		PSCMBRD0013VO bean = new PSCMBRD0013VO();
		bean.setRecommSeq(recommSeq);
		//bean.setTitle(StringUtil.null2str(title));
		bean.setDelYN(StringUtil.null2str(delYN));
		//bean.setRecommPnt(StringUtil.null2str(recommPnt));
		//bean.setRecommContent(StringUtil.null2str(recommContent));
		//bean.setAdminMemo(StringUtil.null2str(adminMemo));
		bean.setExlnSltYn(StringUtil.null2str(exlnSltYn));
		bean.setMainDpSeq(StringUtil.null2str(mainDpSeq)); //2014.10.29 박지혜 추가
		bean.setRegId(epcLoginVO.getAdminId());
		bean.setModId(epcLoginVO.getAdminId());

		try {
	    	int resultCnt = pscmbrd0013Service.updateProduct(bean);
	    	message = messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault());
	    	if(resultCnt > 0){
	    		return AjaxJsonModelHelper.create("");
	    	}else{
	    		return AjaxJsonModelHelper.create(message);
	    	}
		} catch (Exception e) {
			return AjaxJsonModelHelper.create(messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault()));
		}
    }

    /**
     * Desc : 상품평 리스트 엑셀다운로드
     * @Method Name : exportPSCMBRD0013Excel
     * @param request
     * @param response
     * @throws Exception
     * @param
     * @return
     * @exception Exception
     */
    @RequestMapping("board/exportPSCMBRD0013Excel.do")
    public void exportPSCMBRD0013Excel(HttpServletRequest request, HttpServletResponse response) throws Exception{

    	String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

    	try{
	    	DataMap paramMap = new DataMap(request);

			// 협력사코드 전체를 선택한 경우 로그인 세션에 있는 협력사 코드 전체를 설정한다.
			if("".equals(paramMap.getString("searchVendorId")))	{
				paramMap.put("vendorId", LoginUtil.getVendorList(epcLoginVO)); //협력업체코드
			} else	{
				ArrayList<String> vendorList = new ArrayList<String>();
				vendorList.add(paramMap.getString("searchVendorId"));
				paramMap.put("vendorId", vendorList); //협력업체코드
			}

			paramMap.put("startDate", 		paramMap.getString("startDate").replaceAll("-", ""));
			paramMap.put("endDate", 		paramMap.getString("endDate").replaceAll("-", ""));
			paramMap.put("userSrch", 		paramMap.getString("userSrch"));
			paramMap.put("userSrchNm", 		paramMap.getString("userSrchNm"));
			paramMap.put("prodSrch", 		paramMap.getString("prodSrch"));
			paramMap.put("prodSrchNm", 		paramMap.getString("prodSrchNm"));
			paramMap.put("searchVendorId", 	paramMap.getString("searchVendorId"));
			// mallDivnCd 몰구분(00001:롯데마트몰, 00002:토이저러스몰) 추가
			paramMap.put("mallDivnCd", 		paramMap.getString("mallDivnCd"));

	    	List<Map<Object, Object>> list = pscmbrd0013Service.selectPscmbrd0013Export(paramMap);

			JsonUtils.IbsExcelDownload((List)list, request, response);
	    } catch (Exception e) {
			// 작업오류
	        logger.error("error message --> " + e.getMessage());
		}
    }

	@RequestMapping("board/photoReviewDetailForm.do")
	public String imageDetailForm(HttpServletRequest request) throws Exception {
		DataMap map = new DataMap();

		String atchFileId 			= SecureUtil.stripXSS(request.getParameter("atchFileId"));

		map.put("atchFileId", atchFileId);

		DataMap prodInfo = pscmbrd0013Service.selectProdPhotoView(map);
		request.setAttribute("photoImg", config.getString("imageqc.root.url") + "/files/boardfile/productreview"+ prodInfo.getString("FILEURL"));

		return "board/PSCPBRD001302";
	}

	/**
     * Desc : 체험형 상품평 리스트 엑셀다운로드
     * @Method Name : exportPSCMBRD001302Excel
     * @param request
     * @param response
     * @throws Exception
     * @param
     * @return
     * @exception Exception
     */
    @RequestMapping("board/exportPSCMBRD001302Excel.do")
    public void exportPSCMBRD001302Excel(HttpServletRequest request, HttpServletResponse response) throws Exception{

    	String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

    	try{
	    	DataMap paramMap = new DataMap(request);

			// 협력사코드 전체를 선택한 경우 로그인 세션에 있는 협력사 코드 전체를 설정한다.
			if("".equals(paramMap.getString("searchVendorId")))	{
				paramMap.put("vendorId", LoginUtil.getVendorList(epcLoginVO)); //협력업체코드
			} else	{
				ArrayList<String> vendorList = new ArrayList<String>();
				vendorList.add(paramMap.getString("searchVendorId"));
				paramMap.put("vendorId", vendorList); //협력업체코드
			}

			paramMap.put("startDt", paramMap.getString("startDate").replaceAll("-", ""));
			paramMap.put("endDt", paramMap.getString("endDate").replaceAll("-", ""));
			paramMap.put("userSrch", paramMap.getString("userSrch"));
			paramMap.put("userSrchNm", paramMap.getString("userSrchNm"));
			paramMap.put("prodSrch", paramMap.getString("prodSrch"));
			paramMap.put("prodSrchNm", paramMap.getString("prodSrchNm"));

	    	List<PSCMBRD0013VO> list = pscmbrd0013Service.selectPscmbrd001302Export((Map)paramMap);

			JsonUtils.IbsExcelDownload((List<PSCMBRD0013VO>)list, request, response);
	    } catch (Exception e) {
			// 작업오류
	        logger.error("error message --> " + e.getMessage());
		}
    }

}
