package com.lottemart.epc.edi.product.controller;

import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lottemart.common.exception.AlertException;
import com.lottemart.common.exception.TopLevelException;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.util.FileUtil;
import com.lottemart.epc.common.util.PagingUtil;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.comm.taglibs.dao.CustomTagDao;
import com.lottemart.epc.edi.consult.model.NEDMCST0310VO;
import com.lottemart.epc.edi.product.model.CommonProductVO;
import com.lottemart.epc.edi.product.model.ExcelFileVo;
import com.lottemart.epc.edi.product.model.NEDMPRO0400VO;
import com.lottemart.epc.edi.product.service.CommonProductService;
import com.lottemart.epc.edi.product.service.NEDMPRO0400Service;

import lcn.module.common.paging.PaginationInfo;
import lcn.module.common.util.DateUtil;
import lcn.module.common.util.HashBox;
import lcn.module.framework.property.ConfigurationService;

@Controller
public class NEDMPRO0400Controller extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0400Controller.class);

	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	@Resource(name = "commonProductService")
	private CommonProductService commonProductService;
	
	@Autowired
	private CustomTagDao customTagDao;
	
	@Autowired
	NEDMPRO0400Service  nedmpro0400Service;
	

	/**
	 * 신규 입점 제안 등록 
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/NEDMPRO0400.do")
	public String newProposeOpenStore(ModelMap model, HttpServletRequest request) throws Exception {

		if (model == null || request == null) {
			throw new IllegalArgumentException();
		}
		
		Map<String, Object>	paramMap	=	new HashMap<String, Object>();
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		String[] ven_cd = epcLoginVO.getVendorId();

		List<CommonProductVO> venList = new ArrayList<>();
		
		for(int i=0; ven_cd.length >i; i++)
		{
			CommonProductVO venListVo = new CommonProductVO();
			
			venListVo.setVenCd(ven_cd[i]);
			venListVo.setVenNm(ven_cd[i]);

			venList.add(venListVo);
		}
		
		//상품제안 채널 전체 조회 (selecbox 구성용 data)
		List<Map<String,Object>> propChanCodes = nedmpro0400Service.selectTpcNewPropChanCodes(paramMap);
		
		
		
		model.addAttribute("vendorList", 		venList);
		model.addAttribute("epcLoginVO", 		epcLoginVO);
		model.addAttribute("srchFromDt",	DateUtil.getToday("yyyy-MM-dd"));													//검색시작일
		model.addAttribute("srchEndDt",		DateUtil.formatDate(DateUtil.addDay(DateUtil.getToday("yyyy-MM-dd"), 1), "-"));		//검색종료일
		model.addAttribute("teamList",			commonProductService.selectNteamList(paramMap, request));		//팀리스트
		model.addAttribute("propChanCodes",		propChanCodes);
		return "edi/product/NEDMPRO0400";
	}
	
	/**
	 * 신규 입점 제안 조회 
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/selectProposeStore.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> selectProposeStoreList(@RequestBody NEDMPRO0400VO vo, HttpServletRequest request) throws Exception {
		Map<String, Object>	map = new HashMap<String, Object>();

		if (vo.getSrchEntpCd() == null || vo.getSrchEntpCd().equals("")) {
			map.put("resultList", "");
		}

//		PaginationInfo paginationInfo = new PaginationInfo();
//		paginationInfo.setCurrentPageNo(vo.getPageIndex());
//		paginationInfo.setRecordCountPerPage(vo.getRecordCountPerPage());
//		paginationInfo.setPageSize(vo.getPageSize());

//		map.put("paginationInfo", paginationInfo);

//		vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
//		vo.setLastIndex(paginationInfo.getLastRecordIndex());
//		vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		//소속회사 리스트만 조회 
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		String[] myVenCds = epcLoginVO.getVendorId(); 
		vo.setVenCds(myVenCds);

		// List Total Count
		try {
			int totCnt = nedmpro0400Service.selectProposeStoreListCount(vo, request);
//			paginationInfo.setTotalRecordCount(totCnt);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
		}

		// List 가져오기
		List<NEDMPRO0400VO>	resultList 	= 	nedmpro0400Service.selectProposeStoreList(vo, request);
		map.put("resultList", resultList);

		/**
		// 화면에 보여줄 게시물 리스트
		map.put("pageIdx", vo.getPageIndex());

		// 화면에 보여줄 페이징 생성
		try {
			map.put("contents", PagingUtil.makingPagingContents(request, paginationInfo, "text", "goPage"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("PagingUtil.makingPagingContents Exception ===" + e.toString());
		}
		 */
		return map;
	}
	
	/**
	 * 신규 입정 제안 정보 저장 
	 * @param paramVo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/insertNewPropStore.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> insertNewPropStore( @RequestParam("data") String data, @RequestParam("files") MultipartFile[] files, HttpServletRequest request) throws Exception {
	    Map<String, Object> result = new HashMap<>();
	    result.put("msg", "FAIL");
	    
		// JSON 데이터 파싱
	    ObjectMapper mapper = new ObjectMapper();
	    List<NEDMPRO0400VO> paramList = mapper.readValue(data, new TypeReference<List<NEDMPRO0400VO>>() {});
	
	    result = nedmpro0400Service.insertNewPropStore(paramList, request, files);	
		
	    return result;
	}
	
	
	/**
	 * 상세이미지 조회	
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edi/product/propImgPreViewPopup.do", method = RequestMethod.POST)
	public String imgPreViewPopup(@RequestParam("hiddenImgUrl") String imgUrl,@RequestParam("hiddenImgId") String imgId, Model model) throws Exception {
//		//이미지 조회 url
//		String viewUrl = "";
//		
//		//이미지 아이디가 있을 경우, 파일 조회 url 셋팅
//		NEDMPRO0400VO imgVo = nedmpro0400Service.selectDetailImgInfo(imgId);
//		if(imgVo != null) {
//			viewUrl = config.getString("edi.propNewStore.image.url") + "/" + imgVo.getSaveFileNm();
//		}
		
		//조회대상 이미지 url
		model.addAttribute("imgUrl", imgUrl);
		model.addAttribute("workGbn", "prop");
		model.addAttribute("atchFileId", imgId);
	    return "edi/comm/imgPreViewPopup";
	}
	
	/**
	 * 상품 소구 특성 등록 팝업
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edi/product/NEDMPRO0401.do")
	public String prodDetailRegPopup(ModelMap model, HttpServletRequest request) {
	    String propRegNo = request.getParameter("propRegNo");	//요청번호
	    String rnum = request.getParameter("rnum");				// 아이디순번
	    String prdtSpecRange = request.getParameter("prdtSpecRange");		//	소구 특성
	    String prdtSpecSex = request.getParameter("prdtSpecSex");		//	소구 특성
	    String prdtSpecAge = request.getParameter("prdtSpecAge");		//	소구 특성
	    String screenFlag = request.getParameter("screenFlag");  // 화면구분

	    model.addAttribute("screenFlag", (screenFlag == null || screenFlag.isEmpty()) ? "" : screenFlag);
	    model.addAttribute("prdtSpecRange", (prdtSpecRange == null || prdtSpecRange.isEmpty()) ? "" : prdtSpecRange);
	    model.addAttribute("prdtSpecSex", (prdtSpecSex == null || prdtSpecSex.isEmpty()) ? "" : prdtSpecSex);
	    model.addAttribute("prdtSpecAge", (prdtSpecAge == null || prdtSpecAge.isEmpty()) ? "" : prdtSpecAge);
	    model.addAttribute("rnum", (rnum == null || rnum.isEmpty()) ? "1" : rnum);
	    model.addAttribute("propRegNo", (propRegNo == null || propRegNo.isEmpty()) ? "" : propRegNo);

	    return "edi/product/NEDMPRO0401";
	}
	
	/**
	 * 신규 입점 상품 삭제
	 * @param paramList
	 * @param request
	 * @param mFile
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/deleteNewPropStore.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> deleteNewPropStore(@RequestBody List<NEDMPRO0400VO> paramList, HttpServletRequest request , MultipartFile mFile) throws Exception {
		Map<String, Object> result = new HashMap<>();
		result.put("msg", "FAIL");

		try {
			result = nedmpro0400Service.deleteNewPropStore(paramList);
		} catch (AlertException e) {		//화면표시용 message
			result.put("errMsg", e.getMessage());
		} catch (Exception e) {				//그 외 모든 exception
			logger.error(e.getMessage());	
		}
		
	    return result;
	}
	
	/**
	 * 제안요청
	 * @param paramList
	 * @param request
	 * @param mFile
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/updateNewPropStoreRequest.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> updateNewPropStoreRequest(@RequestBody List<NEDMPRO0400VO> paramList, HttpServletRequest request , MultipartFile mFile) throws Exception {
		Map<String, Object> result = new HashMap<>();
		result.put("msg", "FAIL");
	
		try {
			result = nedmpro0400Service.updateNewPropStoreRequest(paramList,request);
		} catch (AlertException e) {		//화면표시용 message
			result.put("errMsg", e.getMessage());
		} catch (Exception e) {				//그 외 모든 exception
			logger.error(e.getMessage());	
		}

	    return result;
	}
	
	/**
	 * 신규 입점 상품 조회 화면 
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/NEDMPRO0410.do")
	public String newSelProposeOpenStore(ModelMap model, HttpServletRequest request) throws Exception {

		if (model == null || request == null) {
			throw new IllegalArgumentException();
		}
		
		Map<String, Object>	paramMap	=	new HashMap<String, Object>();
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		String[] ven_cd = epcLoginVO.getVendorId();

		List<CommonProductVO> venList = new ArrayList<>();
		
		for(int i=0; ven_cd.length >i; i++)
		{
			CommonProductVO venListVo = new CommonProductVO();
			
			venListVo.setVenCd(ven_cd[i]);
			venListVo.setVenNm(ven_cd[i]);

			venList.add(venListVo);
		}
		
		
		//상온구분 공통코드 가져오기 		
		List<HashBox> frzList = new ArrayList<>();
		HashBox								map = new HashBox();
		map.put("parentCodeId", "PRD12");
		frzList = customTagDao.getCode(map);
		
		
		model.addAttribute("frzList",			frzList);		//상온구분
		model.addAttribute("vendorList", 		venList);
		model.addAttribute("epcLoginVO", 		epcLoginVO);
		model.addAttribute("srchFromDt",	DateUtil.getToday("yyyy-MM-dd"));													//검색시작일
		model.addAttribute("srchEndDt",		DateUtil.formatDate(DateUtil.addDay(DateUtil.getToday("yyyy-MM-dd"), 1), "-"));		//검색종료일
		
	
		model.addAttribute("teamList",			commonProductService.selectNteamList(paramMap, request));		//팀리스트
		return "edi/product/NEDMPRO0410";
	}
	
	/**
	 * 대분류 리스트 조회 
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/selectTempL1List.json", method=RequestMethod.POST, headers="Accept=application/json")
    public @ResponseBody Map<String, Object> selectTempL1List(@RequestBody Map<String, Object> paramMap,  HttpServletRequest request) throws Exception {
		if (paramMap == null || request == null) {
			throw new TopLevelException("");
		}

		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<CommonProductVO> resultList = nedmpro0400Service.selectTempL1List(paramMap, request);
		returnMap.put("l1List", resultList);

		return returnMap;
	}
	
	
	/**
	 * 조회된 팀의 대분류조회 한번에 다건 조회
	 * @param teamL1CdList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/selectTempL1List2.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> selectTempL1List2(@RequestBody CommonProductVO teamL1CdList) throws Exception {
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			List<CommonProductVO> resultList = nedmpro0400Service.selectTempL1List2(teamL1CdList);
			returnMap.put("l1List", resultList);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return returnMap;
	}
	
	
	/**
	 * 신상품 입점제안 엑셀 다운로드 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/selectNewPropStoreExcelDownload.do")
	public ModelAndView selectNewPropStoreExcelDownload(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ExcelFileVo excelVo = new ExcelFileVo();
		
		String fileName = "신상품입점제안정보_리스트"; 
		
		NEDMPRO0400VO paramVo = new NEDMPRO0400VO();
		
		paramVo.setSrchFromDt(request.getParameter("hiddenSrchFromDt"));
		paramVo.setSrchEndDt(request.getParameter("hiddenSrchEndDt"));
		paramVo.setSrchEntpCd(request.getParameter("hiddenSrchEntpCd"));
		paramVo.setSrchL1Cd(request.getParameter("hiddenSrchL1Cd"));
		paramVo.setSrchProdNm(request.getParameter("hiddenSrchProdNm"));
		paramVo.setSrchTeamCd(request.getParameter("hiddenSrchTeamCd"));
		
		// data list
		List<HashMap<String, String>> datalist = new ArrayList<HashMap<String,String>>();
		
		//소속회사 리스트만 조회 
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		String[] myVenCds = epcLoginVO.getVendorId(); 
		paramVo.setVenCds(myVenCds);

		
		datalist = nedmpro0400Service.selectNewPropStoreExcelInfo(paramVo);
		
		// titleparamVo
		String[] title = { 
				 "진행상태"
				,"파트너사코드"
				,"팀"
				,"대분류"
				,"쇼카드상품명"
				
				,"규격"
				,"원가"
				,"판매가"
				,"이익율"
				,"상품구분"
				
				,"루트"
				,"입수"
				,"납품가능일자"
				,"유사(타겟)상품"
				,"판매채널"
				
				,"상온구분"
				,"목표매출"
				,"통화키"
				,"상품특성(가격)"
				,"상품특성(연령)"
				
				,"상품특성(성별)"
				,"등록자"
				,"등록시간"
		};
		
		// cell style
		String[] dataStyle = {"CENTER", "CENTER", "CENTER","CENTER","CENTER"
								,"CENTER","CENTER/#,##0","CENTER/#,##0","CENTER","CENTER"
								,"CENTER","CENTER","CENTER","CENTER","CENTER"
								,"CENTER","CENTER","CENTER","CENTER","CENTER"
								,"CENTER","CENTER","CENTER"	
							};		

		// cell width
		int[] cellWidth = { 15,25,30,20,25
							,15,15,15,15,25
							,15,15,15,15,30
							,15,15,15,15,15
							,15,15,20
							};
		
		// Keyset
		String[] keyset = { "PRDT_STATUS","VEN_CD","TEAM_CD","L1_NM","PROD_SHORT_NM"
							,"PROD_STANDARD_NM","NOR_PROD_PCOST","PROD_SELL_PRC","PRFT_RATE","NOR_SESN_CD"
							,"PRDT_RT","INRCNT_QTY","DELV_PSBT_DD","SELL_CD","SELL_CHAN_CD"
							,"FRZ_CD","TRGT_SALE_CURR","WAERS","PRDT_SPEC_RANGE","PRDT_SPEC_AGE"
							,"PRDT_SPEC_SEX","REG_ID","REG_DATE"
							};
		
		excelVo = FileUtil.toExcel(fileName, datalist, title, dataStyle, cellWidth, keyset, null, null);

		//XLSX 파일 형식으로 저장 
		ModelAndView modelAndView = new ModelAndView("apachPoiExcelFileDownLoad", "excelFile", excelVo);
		return modelAndView;
	}
	
	/**
	 * 엑셀 양식 다운로드 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/selectNewPropStoreExcelTmplDownload")
	public void selectTpcProdChgCostDetailExcelDown(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataMap paramMap = new DataMap(request);

		response.setContentType("application/x-msdownload;charset=UTF-8");
		// 양식의 value 값
		String optionVal = request.getParameter("optionVal");
		String excelTmpCd = request.getParameter("hiddenExcelTmpCd");
		
		String userAgent = request.getHeader("User-Agent");
		
		//jsp에서 가져온 파일명 
		String fileNmVal = URLDecoder.decode(request.getParameter("fileName"), "UTF-8");
		
		String fileName = "";
		
		fileName = fileNmVal + "_양식.xlsx";
		
		fileName = URLEncoder.encode(fileName, "UTF-8");
		
		if (userAgent.indexOf("MSIE 5.5") > -1) {
			response.setHeader("Content-Disposition", "filename=" + fileName + ";");
		} else {
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ";");
		}
		
		String[] newHeaders = new String[1];
		int headerHeight = 0;
		
		//반품 엑셀양식 헤더 부분 
		String[] headers = { "* 파트너사코드", "* 팀코드", "* 대분류코드","* 쇼카드상품명","* 규격","* 원가","* 판매가","* 이익율","* 상품구분","* 루트","* 입수","* 납품가능일자","유사(타겟)상품","* 판매채널","* 상온구분","* 목표매출","상품특성(가격)","상품특성(연령)","상품특성(성별)","통화키" };  
		newHeaders = headers;
		headerHeight = 1500;
		
		// 헤더출력
		int headerLength = newHeaders.length;
		
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		CreationHelper createHelper = workbook.getCreationHelper();
		XSSFSheet sheet = workbook.createSheet(fileNmVal + " 양식");
		
		XSSFRow header1 = sheet.createRow(0);
		XSSFCellStyle styleHd = workbook.createCellStyle();
		XSSFCellStyle styleHd2 = workbook.createCellStyle();
		XSSFCellStyle styleHd3 = workbook.createCellStyle();
		XSSFCellStyle styleRow = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		XSSFFont wrnfont = workbook.createFont();
		XSSFCell cell = null;
		
//		sheet.addMergedRegion(new CellRangeAddress((int) 0, (short) 0, (int) 0, (short) 7));
		sheet.addMergedRegion(new CellRangeAddress((int) 0, (short) 0, (int) 0, (short) headerLength-1));
		
		font.setFontHeight((short) 200);
		styleHd.setFont(font);
		styleHd.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		styleHd.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		styleHd.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		styleHd.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		styleHd.setBorderTop(XSSFCellStyle.BORDER_THIN);
		styleHd.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		styleHd.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		styleHd.setBorderRight(XSSFCellStyle.BORDER_THIN);

		wrnfont.setFontHeight((short) 170);
		wrnfont.setColor(wrnfont.COLOR_RED);
		styleHd2.setFont(wrnfont);
		styleHd2.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		styleHd2.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		styleHd2.setWrapText(true);

		styleHd3.setFont(wrnfont);
		styleHd3.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		styleHd3.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		styleHd3.setWrapText(true);

		styleRow.setWrapText(true);
		styleRow.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		styleRow.setVerticalAlignment(XSSFCellStyle.VERTICAL_TOP);
		styleRow.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		styleRow.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND); 
		styleRow.setBorderTop(XSSFCellStyle.BORDER_THIN);
		styleRow.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		styleRow.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		styleRow.setBorderRight(XSSFCellStyle.BORDER_THIN);
		
		
		
		//엑셀 헤더 위 안내 문구 설정
		String infoVal = "※ * 표시된 항목은 필수 입력입니다. 미입력시 일괄등록이 되지 않으니 유의하시기 바랍니다.\n"
				+ "※ 파트너사코드는 동일한 파트너사코드로 작성 바랍니다.\n"
				+ "※ 팀코드와 대분류 코드는 팀_대분류_MAPPING 시트에서 통화코드는 통화코드_MAPPING 사트에서 확인하여 정확하게 작성 바랍니다.\n"
		        + "※ 상품구분, 루트, 판매채널, 상품특성 등은 코드 가이드 시트를 확인하여 정확하게 작성 바랍니다.\n"
		        + "※ 상품특성의 경우 연령,가격,성별에서 각각 한가지만 선택하여 작성 바랍니다.(ex- 연령= 1 (O) , 연령= 1,2(X))\n"
		        + "※ 위와 같이 정확하게 코드 미입력시 일괄 등록이 되지 않을수 있으니 유의하시기 바랍니다.";
		
		
		//헤더에 문구 적용 및 스타일 저용 
		header1.createCell(0).setCellValue(infoVal);
		header1.getCell(0).setCellStyle(styleHd2);
		header1.setHeight((short) headerHeight);
		
		XSSFRow header2 = sheet.createRow(1);
		
		for (int i = 0; i < headerLength; i++) {
			// 컬럼 제목들 설정해줌 두번째 줄부터  header2 가 두번째 로우임 위에 sheet.createRow(1) 이거로 보면 인덱스 1임  
			cell = header2.createCell(i);
			cell.setCellValue(newHeaders[i]);
			cell.setCellStyle(styleHd);

			if (newHeaders[i].length() < 9) {
				sheet.setColumnWidth(i, 4000);
			} else {
				sheet.setColumnWidth(i, 7000);
			}
		}
		
		
		DataFormat format = workbook.createDataFormat();
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setDataFormat(format.getFormat("@"));

		/* 셀(cols) 갯수 확인 */
		Row rows = sheet.getRow(1);
		int cellCnt = (int) rows.getLastCellNum();
		/* 셀(cols) 갯수 확인 */
		
		for (int j = 0; j < cellCnt; j++) {
			sheet.setDefaultColumnStyle(j, cellStyle);
		}
		
		
		// 헤더 아래 첫 번째 데이터 행 추가
		XSSFRow dataRow = sheet.createRow(2); // 헤더가 2줄 이므로 인덱스 2 부터 시작(3번째줄)
		// 디폴트값 맵핑 
		String entpCd = request.getParameter("hiddenSrchEntpCd");
		// 디폴트값 셀에 입력 (파트너사)
		cell = dataRow.createCell(0); // "* 파트너사"
		cell.setCellValue(entpCd);
		cell.setCellStyle(styleRow);
		cell.setCellType(1);
		
		
		//코드 가이드 시트----------------------------------------------------------
		XSSFSheet sheet2 = workbook.createSheet("코드_가이드");
		
		// 코드 가이드 표 구조 만들기
		int rowIdx = 2; // 기존 안내 문구 이후 줄부터 시작
		String[][] guideTable = {
		    {"항목", "코드값", "설명"},
		    {"판매채널", "1", "마트"},
		    {"판매채널", "2", "슈퍼"},
		    {"판매채널", "3", "마/슈 공통"},
		    {"상온구분", "1", "상온"},
		    {"상온구분", "2", "저온"},
		    {"상온구분", "3", "패션"},
		    {"상온구분", "4", "신선pc"},
		    {"상품특성(가격)", "1", "가성비"},
		    {"상품특성(가격)", "2", "프리미엄"},
		    {"상품특성(가격)", "3", "저가형"},
		    {"상품특성(연령)", "1", "YOUNG"},
		    {"상품특성(연령)", "2", "SILVER"},
		    {"상품특성(연령)", "3", "일반"},
		    {"상품특성(성별)", "1", "남"},
		    {"상품특성(성별)", "2", "여"},
		    {"상품구분", "1", "일반"},
		    {"상품구분", "2", "시즌"},
		    {"루트", "1", "직납"},
		    {"루트", "2", "TC"},
		    {"루트", "3", "DC"},
		    {"루트", "4", "PDC"}
		};

		CellStyle tableStyle = workbook.createCellStyle();
		tableStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		tableStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		tableStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		tableStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		tableStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);

		
		// 헤더 스타일 (연한 파랑 배경 + 볼드)
		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.cloneStyleFrom(tableStyle); // 기본 스타일 복사
		headerStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

		XSSFFont boldFont = workbook.createFont();
		boldFont.setBold(true);
		headerStyle.setFont(boldFont);
		
		for (int i = 0; i < guideTable.length; i++) {
		    Row row = sheet2.createRow(rowIdx++);
		    sheet2.setColumnWidth(i, 5000);
		    for (int j = 0; j < guideTable[i].length; j++) {
		        Cell cell2 = row.createCell(j);
		        cell2.setCellValue(guideTable[i][j]);
		        if (i == 0) {
		            cell2.setCellStyle(headerStyle);
		        } else {
		            cell2.setCellStyle(tableStyle);
		        }
		    }
		}
		
		
		//팀 대분류 맵핑 시트----------------------------------------------------------start
		
		XSSFSheet sheet3 = workbook.createSheet("팀_대분류_MAPPING_시트");

		Map<String, Object> parameterMap = new HashMap<String, Object>();
		
		List<CommonProductVO> teamL1cdList = commonProductService.selectTeamL1CdList(parameterMap);

		int colOffset = 1; 
		int rowStart = 2;
		int rowIdx2 = rowStart;

		// 헤더
		Row headerRow = sheet3.createRow(rowIdx2++);
		String[] header = {"팀코드", "팀명", "대분류코드", "대분류명"};
		for (int i = 0; i < header.length; i++) {
		    Cell cell2 = headerRow.createCell(i + colOffset);
		    cell2.setCellValue(header[i]);
		    cell2.setCellStyle(headerStyle);
		    sheet3.setColumnWidth(i + colOffset, 6000);
		}

		// 데이터
		CellStyle sharedStyle2 = workbook.createCellStyle();
		sharedStyle2.setWrapText(true);

		for (CommonProductVO teamVo : teamL1cdList) {
		    Row row = sheet3.createRow(rowIdx2++);
		    
		    Cell cell0 = row.createCell(colOffset);
		    cell0.setCellValue(teamVo.getTeamCd());
		    cell0.setCellStyle(sharedStyle2);

		    Cell cell1 = row.createCell(colOffset + 1);
		    cell1.setCellValue(teamVo.getTeamNm());
		    cell1.setCellStyle(sharedStyle2);

		    Cell cell2 = row.createCell(colOffset + 2);
		    cell2.setCellValue(teamVo.getL1Cd());
		    cell2.setCellStyle(sharedStyle2);

		    Cell cell3 = row.createCell(colOffset + 3);
		    cell3.setCellValue(teamVo.getL1Nm());
		    cell3.setCellStyle(sharedStyle2);
		}
		
		//팀 대분류 맵핑 시트----------------------------------------------------------end
		
		
		
		
		//통화키 맵핑 시트----------------------------------------------------------start

		XSSFSheet sheet4 = workbook.createSheet("통화키_MAPPING_시트");

		Map<String, Object> parameterMap2 = new HashMap<String, Object>();
		
		List<CommonProductVO> waersCdList = commonProductService.selectWaersCdList(parameterMap2);

		int colOffset2 = 1; 
		int rowStart2 = 2;
		int rowIdx3 = rowStart2;

		// 헤더
		Row headerRow3 = sheet4.createRow(rowIdx3++);
		String[] header3 = {"통화코드", "통화코드명"};
		for (int i = 0; i < header3.length; i++) {
		    Cell cell2 = headerRow3.createCell(i + colOffset2);
		    cell2.setCellValue(header3[i]);
		    cell2.setCellStyle(headerStyle);
		    sheet4.setColumnWidth(i + colOffset2, 6000);
		}

		// 데이터
		CellStyle sharedStyle3 = workbook.createCellStyle();
		sharedStyle3.setWrapText(true);

		for (CommonProductVO vo : waersCdList) {
		    Row row = sheet4.createRow(rowIdx3++);
		    
		    Cell cell0 = row.createCell(colOffset);
		    cell0.setCellValue(vo.getSubDtlCd());
		    cell0.setCellStyle(sharedStyle2);

		    Cell cell1 = row.createCell(colOffset + 1);
		    cell1.setCellValue(vo.getSubDtlNm());
		    cell1.setCellStyle(sharedStyle2);
		}
		
		//통화키 맵핑 시트----------------------------------------------------------end
	
		
		ServletOutputStream out = response.getOutputStream();
		workbook.write(out);
		out.flush();
	}
	
	
	
	
	
	
	@RequestMapping(value = "/edi/product/selectNewPropStoreExcelTmplDownload2")
	public ModelAndView selectExcelFormDownload(@RequestParam HashMap<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ExcelFileVo ExcelFileVo = new ExcelFileVo();
		
		String fileName = "test 양식"; 
		
		String entpCd = request.getParameter("hiddenSrchEntpCd");
		// data list
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("VEN_CD", entpCd);
		/*
		map.put("TEAM_CD", "");
		map.put("L1_CD", "");
		map.put("PROD_SHORT_NM", "");
		map.put("PROD_STANDARD_NM", "");
		map.put("PROD_SELL_PRC", "");
		map.put("PRT_RATE", "");
		map.put("NPOR_SESN_CD", "");
		map.put("PRDT_RT", "");
		map.put("INRCNT_QTY", "");
		map.put("DELV_PSBT_DD", "");
		map.put("SELL_CD", "");
		map.put("SELL_CHAN_CD", "");
		map.put("FRZ_CD", "");
		map.put("TRGT__SALE_CURR", "");
		map.put("PRDT_SPEC_RANGE", "");
		map.put("PRDT_SPEC_AGE", "");
		map.put("PRDT_SPEC_SEX", "");
		*/
		List<HashMap<String, String>> datalist = new ArrayList<HashMap<String,String>>();
		
		datalist.add(map);
	
		// titleparamVo
		String[] title = { 
				 "* 파트너사코드"
				, "* 팀코드"
				, "* 대분류코드"
				,"* 쇼카드상품명"
				,"* 규격"
				,"* 원가"
				,"* 판매가"
				,"* 이익율"
				,"* 상품구분"
				,"* 루트"
				,"* 입수"
				,"* 납품가능일자"
				,"유사(타겟)상품"
				,"* 판매채널"
				,"* 상온구분"
				,"* 목표매출"
				,"상품특성(가격)"
				,"상품특성(연령)"
				,"상품특성(성별)"
		};
		
		

		
		// cell style
		String[] dataStyle = {
				"CENTER",
				"CENTER",
				"CENTER",
				"CENTER",
				"CENTER",
				"CENTER",
				"CENTER",
				"CENTER",
				"CENTER",
				"CENTER",
				
				"CENTER",
				"CENTER",
				"CENTER",
				"CENTER",
				"CENTER",
				"CENTER",
				"CENTER",
				"CENTER",
				"CENTER"
			};		

		// cell width
		int[] cellWidth = {20, 20, 20, 20, 20
						, 20, 20, 20, 20, 20
						, 20, 20, 20, 20, 20
						, 20, 20, 20, 20};
		
		// Keyset
		String[] keyset = {
				"VEN_CD", 
				"TEAM_CD", 
				"L1_CD", 
				"PROD_SHORT_NM", 
				"PROD_STANDARD_NM", 
				"NOR_PROD_PCOST", 
				"PROD_SELL_PRC", 
				"PRT_RATE", 
				"NPOR_SESN_CD", 
				"PRDT_RT", 
				"INRCNT_QTY", 
				"DELV_PSBT_DD", 
				"SELL_CD", 
				"SELL_CHAN_CD", 
				"FRZ_CD", 
				"TRGT__SALE_CURR", 
				"PRDT_SPEC_RANGE", 
				"PRDT_SPEC_AGE", 
				"PRDT_SPEC_SEX"
			};

		ExcelFileVo = FileUtil.toExcel(fileName, datalist, title, dataStyle, cellWidth, keyset, null, null);

		ModelAndView modelAndView = new ModelAndView("apachPoiExcelFileDownLoadViewResolver", "excelFile", ExcelFileVo);
		return modelAndView;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
