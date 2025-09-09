package com.lottemart.epc.edi.product.controller;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.lottemart.common.exception.AlertException;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.product.model.ExcelTempUploadVO;
import com.lottemart.epc.edi.product.model.Result;
import com.lottemart.epc.edi.product.service.NEDMPROEXCELService;

import lcn.module.framework.property.ConfigurationService;


@Controller
public class NEDMPROEXCELController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(NEDMPROEXCELController.class);

	
	@Resource(name = "configurationService")
	private ConfigurationService config;
	
	@Resource(name = "nedmproExcelService")
	private NEDMPROEXCELService nedmproExcelService;
	
	
	/**
	 * NEW 엑셀 업로드 팝업 실행
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/comm/commExcelUploadPopupInit")
	public String commExcelUploadPopupInit(ModelMap model, HttpServletRequest request) throws Exception {
		String excelSysKnd = request.getParameter("excelSysKnd");
		String entpCd = request.getParameter("entpCd");
		
		//원가변경 필수 파라미터 설정 
		if(excelSysKnd.equals("500")) {
			String nbPbGbn = request.getParameter("nbPbGbn");
			String[] purDepts = request.getParameterValues("purDepts");
			
			model.addAttribute("nbPbGbn",nbPbGbn);
			model.addAttribute("purDepts",purDepts);
		}
		
		//공통 필수 파라미터 설정 
		model.addAttribute("excelSysKnd",excelSysKnd);
		model.addAttribute("entpCd",entpCd);
		
		return "edi/comm/commExcelUploadPopup";
	}
	
	/**
	 * 엑셀 업로드
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/comm/commonExcelUpload.do" , method = RequestMethod.POST)
	public @ResponseBody Result commonExcelUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 	ExcelTempUploadVO paramVo = new ExcelTempUploadVO();
		    MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;
		    
		    MultipartFile file = mptRequest.getFile("file"); 
		    
		    if (file == null || file.isEmpty()) {
		        throw new AlertException("업로드할 파일이 없습니다.");
		    }
		    
		    DataMap paramMap = new DataMap(request);
		    String excelSysKnd = paramMap.getString("excelSysKnd");  //엑셀 업로드 양식 구분 
		    String entpCd = paramMap.getString("entpCd");  	// 업체코드
		    
		    //===========
		    // 세션확인
		    //===========
		    EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(config.getString("lottemart.epc.session.key"));
		    //작업자셋팅
		    String regId = epcLoginVO.getLoginWorkId();		
		    paramVo.setRegId(regId);
		    //소속회사 리스트 추출
			String[] myVenCds = epcLoginVO.getVendorId(); 
			paramVo.setVenCds(myVenCds);
			//===========================
		    
		    //원가변경 파라미터 세팅 
		    if(excelSysKnd.equals("500")) {
		    	paramVo.setNbPbGbn(paramMap.getString("nbPbGbn"));
		    	paramVo.setPurDepts(request.getParameterValues("purDepts"));
		    }
		    
		    paramVo.setExcelSysKnd(excelSysKnd);
		    paramVo.setEntpCd(entpCd);
		    
		    
		    //엑셀 업로드 
		    Result result = nedmproExcelService.insertExcelUpload(paramVo, request, file);
		    
		    //엑셀정보 일괄 저장  
		    HashMap<String, Object> returnMap = new HashMap<>();
		    
		    returnMap = result.getResultMap();
		    
		    if(returnMap != null) {
		    	// list 키 값을 가져와 List<HashMap<String, Object>>으로 변환 
			    List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) returnMap.get("list");
			   
			    
				// 데이터 변환해서 해당 테이블에 일괄 저장  엑셀업로드 DB에 바로 저장시 아래 주석 커스터마이징해서 주석 해제하면 됨 
			    /*
			    int i =1; // 순번확인
			    for (HashMap<String, Object> item : list) {
			    	DataMap resultMap = new DataMap();
			    	
			    	resultMap.put("regDt", item.get("data1"));
			    	resultMap.put("teamNm", item.get("data2"));
			    	resultMap.put("sellCd", item.get("data3"));
			    	resultMap.put("prodNm", item.get("data4"));
			    	resultMap.put("returnEndDt", item.get("data5"));
			    	resultMap.put("requestMdDt", item.get("data6"));
			    	resultMap.put("status", item.get("data7"));
			    	resultMap.put("returnReason", item.get("data8"));
			    	resultMap.put("returnDtlReason", item.get("data9"));
			    	resultMap.put("returnPlace", item.get("data10"));
			    	
			        //필수정보 아래에 추가하여 필수정보가 모두 입력이 되었을때만 인서트되게 적용 
			        if(returnMap.get("sellCd") == null || returnMap.get("sellCd") == ""){
			        	logger.info( i + "번째 필수정보 누락");
			        	continue;
			        }else{
			        	//service 와 mapper 생성하여 매서드 맞춰서 인서트 *** 
			        	returnProductMapper.insertReturnProductBatch(resultMap);
			        }
			         i++;
			    }
			    */	
		    }
		 	
		    return result;  
	}
	
	
}
