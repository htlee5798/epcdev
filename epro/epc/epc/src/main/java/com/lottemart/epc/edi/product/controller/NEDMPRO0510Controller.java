package com.lottemart.epc.edi.product.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lottemart.common.exception.TopLevelException;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.util.FileUtil;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.product.model.ExcelFileVo;
import com.lottemart.epc.edi.product.model.NEDMPRO0500VO;
import com.lottemart.epc.edi.product.service.CommonProductService;
import com.lottemart.epc.edi.product.service.NEDMPRO0510Service;

import lcn.module.framework.property.ConfigurationService;

/**
 * @Class Name : NEDMPRO0510Controller
 * @Description : 원가변경요청 Controller
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2025.03.17		yun				최초생성
 * </pre>
 */

@Controller
public class NEDMPRO0510Controller extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0510Controller.class);
	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	@Resource(name = "nEDMPRO0510Service")
	private NEDMPRO0510Service nEDMPRO0510Service;
	
	@Resource(name = "commonProductService")
	private CommonProductService commonProductService;
	
	/**
	 * 원가변경요청 init
	 * @param model
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value="/edi/product/NEDMPRO0510.do")
	public String NEDMPRO0510Init(ModelMap model, HttpServletRequest request) throws Exception {
		if (model == null || request == null) {
			throw new TopLevelException("");
		}

		Map<String, Object>	paramMap	=	new HashMap<String, Object>();
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));

		model.addAttribute("epcLoginVO", 		epcLoginVO);

		// html 코드 태그 한번에 조회 
		paramMap.put("parentCodeId", "CORSD");
		List<HashMap<String, Object>> optList = commonProductService.selectCodeTagList(paramMap);
		
		ObjectMapper mapper = new ObjectMapper();
		String optionListJson = mapper.writeValueAsString(optList); // 이걸 model에 담기
		
		model.addAttribute("optionList", optionListJson);
		model.addAttribute("epcLoginVO", 		epcLoginVO);
		
		
		return "edi/product/NEDMPRO0510";
	}
	
	/**
	 * 원가변경요청 현황 조회
	 * @param nEDMPRO0500VO
	 * @param request
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/selectTpcProdChgCostDetailView.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> selectTpcProdChgCostDetailView(@RequestBody NEDMPRO0500VO nEDMPRO0500VO, HttpServletRequest request) throws Exception {
		return nEDMPRO0510Service.selectTpcProdChgCostDetailView(nEDMPRO0500VO, request);
	}
	
	/**
	 * 원가변경요청정보 복사
	 * @param nEDMPRO0500VO
	 * @param request
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/insertCopyTpcProdChgCost.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> insertCopyTpcProdChgCost(@RequestBody NEDMPRO0500VO nEDMPRO0500VO, HttpServletRequest request) throws Exception {
		return nEDMPRO0510Service.insertCopyTpcProdChgCost(nEDMPRO0500VO, request);
	}
	
	/**
	 * 원가변경요청 엑셀 다운로드
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/selectTpcProdChgCostDetailExcelDownload.do")
	public ModelAndView selectTpcProdChgCostDetailExcelDownload(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ExcelFileVo excelVo = new ExcelFileVo();
	
		String fileName = "원가변경요청정보_리스트"; 

		NEDMPRO0500VO paramVo = new NEDMPRO0500VO();
		
		paramVo.setSrchVenCd(request.getParameter("srchVenCd"));				//파트너사코드
		paramVo.setSrchPurDept(request.getParameter("srchPurDept"));			//구매조직
		paramVo.setSrchNbPbGbn(request.getParameter("srchNbPbGbn"));			//nb/pb구분
		paramVo.setSrchChgReqCostDt(request.getParameter("srchChgReqCostDt"));	//변경시작일자
		paramVo.setSrchSrcmkCd(request.getParameter("srchSrcmkCd"));			//판매코드
		paramVo.setSrchPrStat(request.getParameter("srchPrStat"));				//상태
		paramVo.setSrchTaxFg(request.getParameter("srchTaxFg"));				//과세유형
		paramVo.setSrchProdPatFg(request.getParameter("srchProdPatFg"));		//상품유형
		
		
		
		// data list
		List<HashMap<String, String>> datalist = new ArrayList<HashMap<String,String>>();
		
		datalist = nEDMPRO0510Service.selectTpcProdChgCostDetailExcelInfo(paramVo);
		
		// titleparamVo
		String[] title = { 
				 "진행상태"
				,"공문번호"
				,"파트너사코드"
				,"상품코드"
				,"판매코드"
				,"구매조직"
				,"NB/PB"
				,"대분류"
				,"중분류"
				,"소분류"
				
				,"상품명"
				,"원가변경요청일"
				,"기존원가"
				,"변경원가"
				,"증감율"
				,"변경사유"
				,"상세사유"
				,"비고"
				,"등록자"
				,"등록시간"
		};
		
		// cell style
		String[] dataStyle = {"CENTER", "CENTER", "CENTER","CENTER","CENTER","CENTER","CENTER","CENTER","CENTER","CENTER"
								,"LEFT","CENTER","CENTER/#,##0","CENTER/#,##0","CENTER","CENTER","CENTER","LEFT","CENTER","CENTER"
								};		

		// cell width
		int[] cellWidth = { 15,25,15,15,15,15,10,25,25,25
							,50,15,15,15,15,15,20,30,20,15
							};
		
		// Keyset
		String[] keyset = { "PR_STAT_NM","DC_NUM","VEN_CD","PROD_CD","SRCMK_CD","PUR_DEPT_NM","NB_PB_GBN_NM","L1_NM","L2_NM","L3_NM"
							,"PROD_NM","CHG_REQ_COST_DT","ORG_COST","CHG_REQ_COST","INC_DEC_RATE","COST_RSN_CD_NM","COST_RSN_DTL_CD_NM","CMT","REG_ID","REG_DATE"
							};
		
		excelVo = FileUtil.toExcel(fileName, datalist, title, dataStyle, cellWidth, keyset, null, null);

		//XLSX 파일 형식으로 저장 
		ModelAndView modelAndView = new ModelAndView("apachPoiExcelFileDownLoad", "excelFile", excelVo);
		return modelAndView;
	}
	
	/**
	 * 원가변경요청정보 수정 저장
	 * @param nEDMPRO0500VO
	 * @param request
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/product/updateTpcProdChgCostInfo.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> updateTpcProdChgCostInfo(@RequestBody NEDMPRO0500VO nEDMPRO0500VO, HttpServletRequest request) throws Exception {
		return nEDMPRO0510Service.updateTpcProdChgCostInfo(nEDMPRO0500VO, request);
	}
}
