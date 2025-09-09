package com.lottemart.epc.common.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.paging.PaginationInfo;
import lcn.module.common.util.HashBox;
import lcn.module.common.views.AjaxJsonModelHelper;
import lcn.module.framework.property.ConfigurationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lottemart.common.util.ConfigUtils;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.service.CommonService;
import com.lottemart.epc.common.util.PagingUtil;
import com.lottemart.epc.edi.comm.model.SearchParam;
import com.lottemart.epc.edi.product.model.CommonProductVO;
import com.lottemart.epc.edi.product.model.NEDMPRO0420VO;
import com.lottemart.epc.edi.product.service.CommonProductService;
import com.lottemart.epc.edi.product.service.impl.CommonProductServiceImpl;
import com.lottemart.epc.product.model.PSCPPRD0019VO;
import com.lottemart.epc.product.service.PSCPPRD0019Service;

/**
 * @Class Name : CommonController.java
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
@Controller
public class CommonController
{
    @SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory.getLogger(CommonController.class);
	@Autowired
	private CommonService commonService;
	@Autowired
	private PSCPPRD0019Service PSCPPRD0019Service;

	@Resource(name = "commonProductService")
	private CommonProductService commonProductService;
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
    /**
     * Desc : 상품목록(팝업)공통으로 사용되는 CSS 및 Javascript 파일 페이지
     * @Method Name : getCommonHeadPopup
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @param
     * @return
     * @exception Exception
     */
    @RequestMapping("common/commonHeadPopup.do")
    public String getCommonHeadPopup(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
    	return "common/epcCommonHeadPopup";
    }

    /**
     * Desc : 상품목록 공통으로 사용되는 팝업용 CSS 및 Javascript 파일 페이지
     * @Method Name : getCommonHead
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @param
     * @return
     * @exception Exception
     */
    @RequestMapping("common/commonHead.do")
    public String getCommonHead(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
    	return "common/epcCommonHead";
    }

    /**
     * Desc : 상품 상세보기 하단 탭
     * @Method Name : productDetailTab
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @param
     * @return
     * @exception Exception
     */
    @RequestMapping("common/productDetailTab.do")
    public String productDetailTab(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
    	Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("prodCd", request.getParameter("prodCd"));
		PSCPPRD0019VO resultAttributeCategory = PSCPPRD0019Service.selectPrdAttributeCategory(paramMap);
		String categoryId = (String)(resultAttributeCategory.getCategoryId()==null?"":resultAttributeCategory.getCategoryId());
		if(!(resultAttributeCategory == null || categoryId == null || "".equals(categoryId) || "null".equals(categoryId))) {
			//상품에 지정된 카테고리가 있으면 셋팅
			request.setAttribute("categoryId", categoryId);
		}
    	return "product/internet/productDetailTab";
    }

	/**
	 * 카테고리 대분류 콤보박스 변경시 중분류 콤보박스 가져오기
	 * Desc : 카테고리 대분류 콤보박스 변경시 중분류 콤보박스 가져오기
	 * @Method Name : selectCategoryJungComboList
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("category/selectCategoryJungComboList.do")
	public ModelAndView selectCategoryJungComboList(HttpServletRequest request) throws Exception
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("daeCd", request.getParameter("selDaeGoods"));

		// 중분류콤보 가져오기
		List<DataMap> jungCdList = commonService.selectJungCdList(map);

		// json 결과 생성
    	DataMap resultMap = new DataMap();
    	resultMap.put("jungCdList", jungCdList);
    	resultMap.put("comboNm", "jung");

		return AjaxJsonModelHelper.create(resultMap);
	}

	/**
	 * 카테고리 중분류 콤보박스 변경시 소분류 콤보박스 가져오기
	 * Desc : 카테고리 중분류 콤보박스 변경시 소분류 콤보박스 가져오기
	 * @Method Name : selectCategorySoComboList
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("category/selectCategorySoComboList.do")
	public ModelAndView selectCategorySoComboList(HttpServletRequest request) throws Exception
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("daeCd", request.getParameter("selDaeGoods"));
		map.put("jungCd", request.getParameter("selJungGoods"));

		// 소분류콤보 가져오기
		List<DataMap> soCdList = commonService.selectSoCdList(map);

		// json 결과 생성
		DataMap resultMap = new DataMap();
		resultMap.put("soCdList", soCdList);
		resultMap.put("comboNm", "so");

		return AjaxJsonModelHelper.create(resultMap);
	}

	/**
	 * 카테고리 소분류 콤보박스 변경시 세분류 콤보박스 가져오기
	 * Desc : 카테고리 소분류 콤보박스 변경시 세분류 콤보박스 가져오기
	 * @Method Name : selectCategorySeComboList
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("category/selectCategorySeComboList.do")
	public ModelAndView selectCategorySeComboList(HttpServletRequest request) throws Exception
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("daeCd", request.getParameter("selDaeGoods"));
		map.put("jungCd", request.getParameter("selJungGoods"));
		map.put("soCd", request.getParameter("selSoGoods"));

		// 세분류콤보 가져오기
		List<DataMap> seCdList = commonService.selectSeCdList(map);

		// json 결과 생성
		DataMap resultMap = new DataMap();
		resultMap.put("seCdList", seCdList);
		resultMap.put("comboNm", "se");

		return AjaxJsonModelHelper.create(resultMap);
	}

	/**
	 * IBSheet 엑셀 다운로드를 위한 JSP 호출 - 2015.12.24 김남갑
	 * @Method Name : IbsDown2Excel
	 * @param page, request
	 * @return String
	 * @throws Exception
	 * @exception Exception
	 */
    @RequestMapping("excel/{page}.do")
    public String IbsDown2Excel(@PathVariable("page") String page, HttpServletRequest request) throws Exception {
    	return "excel/" + page;
    }

    @RequestMapping("/product/imageDetailPrevView")
    public @ResponseBody Map imageDetailPrevView(HttpServletRequest request) throws Exception {
		Map rtnMap = new HashMap<String, Object>();

		try {

			String prodCd= request.getParameter("prodCd");
			String productImgPath = ConfigUtils.getString("online.product.image.url");
			String productImg = prodCd.substring(0, 5)+"/"+prodCd+"_1_500.jpg";

			rtnMap.put("productImgPath", productImgPath);
			rtnMap.put("productImg", productImg);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}

		return rtnMap;

	}
    
	/**
	 * 판매코드 팝업 조회 
	 * @param request
	 * @param searchParam
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/selSrcmkCdPopup")
	public String srcmkCdDisplaySearch(HttpServletRequest request, CommonProductVO searchParam, Model model) throws Exception {
		Map<String, Object>	paramMap	=	new HashMap<String, Object>();
		
	    model.addAttribute("popupFlag", request.getParameter("popupFlag"));
	    model.addAttribute("trNum", request.getParameter("trNum"));
	    model.addAttribute("teamCd", request.getParameter("teamCd"));
	    model.addAttribute("teamList", commonProductService.selectNteamList(paramMap, request)); // 팀리스트
		
	    return "edi/comm/sellCdDisplay";
	}
	
	
	
	/**
	 * ECS 수신 담당자 팝업 
	 * @param request
	 * @param searchParam
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/selEcsReceptionistPopup")
	public String selEcsReceptionistPopup(HttpServletRequest request, CommonProductVO searchParam, Model model) throws Exception {
	    return "edi/comm/ecsReceptionistDisplay";
	}
	
	/**
	 * 판매코드 팝업 조회 
	 * @param request
	 * @param searchParam
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/selSrcmkCdPopupInfo.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> srcmkCdDisplaySearch(@RequestBody CommonProductVO vo, HttpServletRequest request) throws Exception {
		Map<String, Object>	map = new HashMap<String, Object>();

		if (vo.getSrchEntpCd() == null || vo.getSrchEntpCd().equals("")) {
			map.put("resultList", "");
		}

		//소속회사 리스트만 조회 
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		String[] myVenCds = epcLoginVO.getVendorId(); 
		vo.setVenCds(myVenCds);
		
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(vo.getPageIndex());
		paginationInfo.setRecordCountPerPage(vo.getRecordCountPerPage());
		paginationInfo.setPageSize(vo.getPageSize());

		map.put("paginationInfo", paginationInfo);

		vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
		vo.setLastIndex(paginationInfo.getLastRecordIndex());
		vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
	
		// List Total Count
		int totCnt = commonProductService.selectSrcmkCdListCount(vo);
		paginationInfo.setTotalRecordCount(totCnt);
		
	 	List<CommonProductVO> sellCdDisplayList = new ArrayList<>();
		 
	 	sellCdDisplayList = commonProductService.selectSrcmkCdList(vo);	
	 	map.put("list", sellCdDisplayList);
		
		 
		// 화면에 보여줄 게시물 리스트
		map.put("pageIdx", vo.getPageIndex());
		
		// 화면에 보여줄 페이징 생성
		try {
			map.put("contents", PagingUtil.makingPagingContents(request, paginationInfo, "text", "goPage"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("PagingUtil.makingPagingContents Exception ===" + e.toString());
		}
		
		return map;
	}
	
	
	/**
	 * 점포 조회 팝업 
	 * @param request
	 * @param searchParam
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/selStoreCdList")
	public String storeCdDisplaySearch(HttpServletRequest request, SearchParam searchParam, Model model) throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
		Map<String, Object> map = new HashMap<>();
		NEDMPRO0420VO vo = new NEDMPRO0420VO();
		
		// 페이지 번호와 페이지 크기 설정
		int pageIndex = request.getParameter("pageIndex") != null ? Integer.parseInt(request.getParameter("pageIndex")) : 1;
	    int pageSize = 30;  // 한 페이지에 30개씩 표시

	    PaginationInfo paginationInfo = new PaginationInfo();
	    paginationInfo.setCurrentPageNo(pageIndex);
	    paginationInfo.setRecordCountPerPage(pageSize);
	    paginationInfo.setPageSize(10);
	    
	    // 조회 파라미터 설정
	    paramMap.put("lastIndex", paginationInfo.getLastRecordIndex());
	    paramMap.put("firstIndex", (pageIndex - 1) * pageSize); // OFFSET 계산
	    paramMap.put("recordCountPerPage", pageSize);
	    
	    paramMap.put("srchStoreCd", request.getParameter("srchStoreCd"));
	    paramMap.put("srchStoreNm", request.getParameter("srchStoreNm"));
	    paramMap.put("srchBmanNo", request.getParameter("srchBmanNo"));
	    paramMap.put("srchComNm", request.getParameter("srchComNm"));
	    
	    // 총 레코드 수 조회
	    int totCnt = commonProductService.selectStrCdListCount(paramMap);
	    paginationInfo.setTotalRecordCount(totCnt);
	    
	    
	    // 데이터 조회
	    List<CommonProductVO> selectStrCdList = commonProductService.selectStrCdList(paramMap);
		
	    // 모델에 데이터 추가
	    model.addAttribute("selectStrCdList", selectStrCdList);
	    model.addAttribute("paginationInfo", paginationInfo);
	    model.addAttribute("trNum", request.getParameter("rnum"));
	    
		return "edi/comm/storeCdDisplay";
	}
    
	/**
	 * ECS 수신 담당자 조회 
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/selEcsReceiverPopupInfo.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String, Object> selEcsReceiverPopupInfo(@RequestBody CommonProductVO vo, HttpServletRequest request) throws Exception {
		Map<String, Object>	map = new HashMap<String, Object>();

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(vo.getPageIndex());
		paginationInfo.setRecordCountPerPage(vo.getRecordCountPerPage());
		paginationInfo.setPageSize(vo.getPageSize());

		map.put("paginationInfo", paginationInfo);

		vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
		vo.setLastIndex(paginationInfo.getLastRecordIndex());
		vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
	
		// List Total Count
		int totCnt = commonProductService.selEcsReceiverPopupInfoCount(vo);
		paginationInfo.setTotalRecordCount(totCnt);
		
	 	List<CommonProductVO> ecsReceiverDisplayList = new ArrayList<>();
		 
	 	ecsReceiverDisplayList = commonProductService.selEcsReceiverPopupInfo(vo);	
	 	map.put("list", ecsReceiverDisplayList);
		
		 
		// 화면에 보여줄 게시물 리스트
		map.put("pageIdx", vo.getPageIndex());
		
		// 화면에 보여줄 페이징 생성
		try {
			map.put("contents", PagingUtil.makingPagingContents(request, paginationInfo, "text", "goPage"));
		} catch (Exception e) {
			logger.error("PagingUtil.makingPagingContents Exception ===" + e.toString());
		}
		
		return map;
	}
	
	/**
	 * ECS 공문/계약 생성 전 선택 팝업
	 * @param request
	 * @param searchParam
	 * @param model
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/insCommDcDocCrePopup.do")
	public String commDcDocCrePopupInit(HttpServletRequest request, CommonProductVO paramVo, Model model) throws Exception {
		model.addAttribute("paramVo", paramVo);
	    return "edi/comm/commDcDocCrePopup";
	}
	
	/**
	 * 업체코드별 계열사 및 구매조직 정보 조회
	 * @param paramVo
	 * @param request
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/selectVendorZzorgInfo.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Map<String,Object> selectVendorZzorgInfo(@RequestBody CommonProductVO paramVo, HttpServletRequest request) throws Exception {
		return commonProductService.selectVendorZzorgInfo(paramVo, request);
	}
	
	/**
	 * [공통코드] TPC_NEW_CODE 공통코드 조회
	 * @param paramVo
	 * @param request
	 * @return List<HashBox>
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/selectTpcNewCode.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody List<HashBox> selectTpcNewCode(@RequestBody CommonProductVO paramVo) throws Exception {
		return commonProductService.selectTpcNewCode(paramVo);
	}
	
	/**
	 * 업체코드별 업무에 사용 가능한 구매조직 list 조회
	 * @param paramVo
	 * @param request
	 * @return List<HashBox>
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/selectVendorPurDeptsWorkUsable.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody List<HashBox> selectVendorPurDeptsWorkUsable(@RequestBody CommonProductVO paramVo, HttpServletRequest request) throws Exception {
		return commonProductService.selectVendorPurDeptsWorkUsable(paramVo, request);
	}
	
	/**
	 * 나의 업체 리스트 조회 (로그인 세션에서 추출)
	 * @param paramVo
	 * @param request
	 * @return List<Map<String,Object>>
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/selectMyVendorList.json", method=RequestMethod.POST)
	public @ResponseBody List<Map<String,Object>> selectMyVendorList(@RequestBody CommonProductVO paramVo, HttpServletRequest request) throws Exception {
		return commonProductService.selectMyVendorList(paramVo, request);
	}
}
