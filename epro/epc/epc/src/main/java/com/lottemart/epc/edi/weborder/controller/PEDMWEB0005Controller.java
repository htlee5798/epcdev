package com.lottemart.epc.edi.weborder.controller;




import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lcn.module.common.util.DateUtil;
import lcn.module.common.views.AjaxJsonModelHelper;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lcnjf.util.NumberUtil;
import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.weborder.model.EdiRtnPackListVO;
import com.lottemart.epc.edi.weborder.model.EdiRtnPackVO;
import com.lottemart.epc.edi.weborder.model.EdiRtnProdProcessVO;
import com.lottemart.epc.edi.weborder.model.EdiRtnProdVO;
import com.lottemart.epc.edi.weborder.model.ExcelUploadVO;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.service.PEDMWEB0005Service;


/**
 * @Class Name : PEDMWEB0005Controller
 * @Description : 상품별 반품등록 Controller 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2014. 08. 11. 오후 2:30:34 DADA
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */


@Controller
public class PEDMWEB0005Controller extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(PEDMWEB0005Controller.class);


	private PEDMWEB0005Service pEDMWEB0005Service;

	@Autowired
	public void setpEDMWEB0005Service(PEDMWEB0005Service pEDMWEB0005Service) {
		this.pEDMWEB0005Service = pEDMWEB0005Service;
	}

	@Autowired
	private MessageSource messageSource;

	/**
	 * Desc : 상품별 반품등록
	 * @Method Name : storeWebOrderList
	 * @param SearchWebOrder
	 * @param HttpServletRequest
	 * @param Model
	 * @return view 페이지
	 */
	@RequestMapping(value="/edi/weborder/PEDMWEB0005")
    public String storeWebOrderList(SearchWebOrder searchParam, HttpServletRequest request, Model model) {
		SimpleDateFormat format = new SimpleDateFormat("HHmm", Locale.KOREA);

		int nowDt = Integer.parseInt(format.format(new Date()));

		String vendorWebOrdFrDt = ConfigUtils.getString("edi.weboder.vendor.rtn");
		String vendorWebOrdToDt = ConfigUtils.getString("edi.weboder.vendor.to");

		if( ( nowDt < Integer.parseInt(vendorWebOrdFrDt)) || ( nowDt > Integer.parseInt(vendorWebOrdToDt)) ){
			return "edi/weborder/PEDMWEB0099";
		}

		searchParam.setVendorWebOrdFrDt(vendorWebOrdFrDt);
		searchParam.setVendorWebOrdToDt(vendorWebOrdToDt);

		model.addAttribute("paramMap",searchParam);

		return "edi/weborder/PEDMWEB0005";
	}



	/**
    * 반품상품저장
	* @param SearchWebOrder
	* @return  AjaxJsonModelHelper[ EdiRtnProdVO ]
	*/
	@RequestMapping("edi/weborder/PEDMWEB0005SaveProd.do")
	public ModelAndView saveReturnProdData(@RequestBody EdiRtnProdVO vo, HttpServletRequest request) throws Throwable, RuntimeException
	{
		/**
			1. 반품상품 점별 마스터 생성   (saveRtnProdMst  -> INSERT TED_PO_RRL_MST  )
			2. 반품상품 점별 상품목록 생성 (saveRtnProdList -> INSERT TED_PO_RRL_PROD )
			3. 반품상품 점별 마스터 합계   (saveRtnrodSum   -> UPDATE TED_PO_RRL_MST  )
		*/

		/**
		 *  state
		 *  0  : 정상 등록         			( SUCCESS          )
		 *  1  : 중복 등록 오류    			( DUPLICATE DATA   )
		 *  2  : TED_PO_RRL_MST  미등록  	( TED_PO_RRL_MST   0 row inserted error )  --사용안함
		 *  3  : TED_PO_RRL_PROD 미등록       ( TED_PO_RRL_PROD  0 row inserted error )
		 * -1  : 시스템 오류    (exception message)
		 */
		HashMap<String, String> rtnData = new HashMap<String, String>();
		rtnData.put("state", "-1");
		try{
			rtnData  = pEDMWEB0005Service.insertReturnProdData(vo, request);
		}
		catch (Exception e) {
			rtnData.put("message",e.getMessage());
			logger.error(e.getMessage(), e);
			// TODO: handle exception
		}


		return AjaxJsonModelHelper.create(rtnData);
	}


	/**
	 * TODAY 협력사 반품상품 리스트 조회
	 * @Method Name : selectDayRtnProdList
	 * @param SearchWebOrder
	 * @return AjaxJsonModelHelper(Map<String,Object>)
	 */
	@RequestMapping("edi/weborder/PEDMWEB0005SearchList.do")
	public ModelAndView selectDayRtnProdList(@RequestBody SearchWebOrder vo, HttpServletRequest request) throws Throwable
	{

		/**
		 *  state
		 *  0  : 정상 등록      ( SUCCESS          )
		 * -1  : 시스템 오류    (exception message)
		 */
		Map<String,Object>  result	    = new HashMap<String,Object>();
		result.put("state","-1");

		try{
			result = pEDMWEB0005Service.selectDayRtnProdList(vo, request);
		}catch (Exception e) {
			result.put("message",e.getMessage());
			logger.error(e.getMessage(), e);
			// TODO: handle exception
		}
		return AjaxJsonModelHelper.create(result);
	}

	/**
	 * TODAY 협력사 반품상품 리스트 조회
	 * @Method Name : selectDayRtnProdList
	 * @param SearchWebOrder
	 * @return AjaxJsonModelHelper(Map<String,Object>)
	 */
	@RequestMapping("edi/weborder/PEDMWEB0005SearchList2.do")
	public ModelAndView selectDayRtnProdList2(@RequestBody SearchWebOrder vo, HttpServletRequest request) throws Throwable
	{

		/**
		 *  state
		 *  0  : 정상 등록      ( SUCCESS          )
		 * -1  : 시스템 오류    (exception message)
		 */
		Map<String,Object>  result	    = new HashMap<String,Object>();
		result.put("state","-1");

		try{
			result = pEDMWEB0005Service.selectDayRtnProdList2(vo, request);
		}catch (Exception e) {
			result.put("message",e.getMessage());
			logger.error(e.getMessage(), e);
			// TODO: handle exception
		}
		return AjaxJsonModelHelper.create(result);
	}

	/**
	 * TODAY 협력사 반품등록 삭제 리스트 처리
	 * @Method Name : deleteReturnProdData
	 * @param EdiRtnProdProcessVO
	 * @return AjaxJsonModelHelper(HashMap<String, String>)
	 */
	@RequestMapping("edi/weborder/PEDMWEB0005DeleteProd.do")
	public ModelAndView deleteReturnProdData(@RequestBody EdiRtnProdProcessVO vo, HttpServletRequest request) throws Exception
	{
		HashMap<String, String> rtnData = new HashMap<String, String>();
		rtnData.put("state","-1");
		try
		{
			rtnData = pEDMWEB0005Service.deleteReturnProdData(vo,request);

		}catch (Exception e) {
			rtnData.put("message",e.getMessage());
			logger.error(e.getMessage(), e);
			// TODO: handle exception
		}

		return AjaxJsonModelHelper.create(rtnData);
	}

	/**
	 * TODAY 협력사코드별  반품승인 등록 처리(MARTNIS 전송)
	 * @Method Name : sendReturnProdData
	 * @param SearchWebOrder
	 * @return AjaxJsonModelHelper(HashMap<String, String>)
	 */
	@RequestMapping("edi/weborder/PEDMWEB0005SendProd.do")
	public ModelAndView sendReturnProdData(@RequestBody SearchWebOrder vo, HttpServletRequest request) throws Exception
	{
		HashMap<String, String> rtnData = new HashMap<String, String>();
		rtnData.put("state","-1");
		try
		{
			rtnData = pEDMWEB0005Service.insertSendReturnProdData(vo, request);

		}catch (Exception e) {
			rtnData.put("message",e.getMessage());
			logger.error(e.getMessage(), e);
			// TODO: handle exception
		}
		return AjaxJsonModelHelper.create(rtnData);
	}





	//-- 반품등록 전체 현황
	@RequestMapping(value="/edi/weborder/PEDMWEB00053.do")
    public String venderWebOrderRtnInfo(SearchWebOrder searchParam, HttpServletRequest request, Model model) {


		if(StringUtils.isEmpty(searchParam.getProdCd())){
			searchParam.setProdCd("");
		}
		if(StringUtils.isEmpty(searchParam.getRegStsfg())){
			searchParam.setRegStsfg("1");
		}

		String toDay = DateUtil.getToday("yyyy-MM-dd");

		searchParam.setRrlDy(toDay);


		model.addAttribute("paramMap",searchParam);

		return "edi/weborder/PEDMWEB00053";
	}




	/**
	 * Desc : 점포별 반품전제현황
	 * @Method Name : tempNewProductList
	 * @param SearchProduct
	 * @param HttpServletRequest
	 * @param Model
	 * @return view 페이지
	 */
	@RequestMapping(value = "/edi/weborder/PEDMWEB00053Select.do")
    public ModelAndView venderWebOrderRtnList(@RequestBody SearchWebOrder vo,HttpServletRequest request)throws Exception{
		Map<String, Object> rtnData = new HashMap<String, Object>();
		rtnData.put("state","-1");
		try{

			rtnData= pEDMWEB0005Service.selectDayRtnList(vo, request);

		}catch (Exception e) {

			rtnData.put("message",e.getMessage());
			logger.error(e.getMessage(), e);
			// TODO: handle exception
		}

		return AjaxJsonModelHelper.create(rtnData);
	}









	/* ============================================================================= */


	/**
	 * Desc : 반품 일괄등록
	 * @Method Name : returnPronList
	 * @param SearchWebOrder
	 * @param HttpServletRequest
	 * @param Model
	 * @return view 페이지
	 */
	@RequestMapping(value="/edi/weborder/PEDMWEB00051")
    public String returnPronList(SearchWebOrder searchParam, HttpServletRequest request, Model model) {

		SimpleDateFormat format = new SimpleDateFormat("HHmm", Locale.KOREA);

		int nowDt = Integer.parseInt(format.format(new Date()));

		String vendorWebOrdFrDt = ConfigUtils.getString("edi.weboder.vendor.rtn");
		String vendorWebOrdToDt = ConfigUtils.getString("edi.weboder.vendor.to");

		if( ( nowDt < Integer.parseInt(vendorWebOrdFrDt)) || ( nowDt > Integer.parseInt(vendorWebOrdToDt)) ){
			return "edi/weborder/PEDMWEB0099";
		}

		searchParam.setVendorWebOrdFrDt(vendorWebOrdFrDt);
		searchParam.setVendorWebOrdToDt(vendorWebOrdToDt);

		if(StringUtils.isEmpty(searchParam.getUploadGb())){
			searchParam.setUploadGb("1");
		}
		if(StringUtils.isEmpty(searchParam.getProdCd())){
			searchParam.setProdCd("");
		}
		model.addAttribute("paramMap",searchParam);

		return "edi/weborder/PEDMWEB00051";
	}

	/**
	 * Desc : 발주 일괄등록 정보 조회
	 * @Method Name : getStoreOrdDetInfo
	 * @param SearchWebOrder
	 * @param Model
	 * @return 조회 값
	 */
	@RequestMapping("/edi/weborder/tedRtnPackSelect.do")
	public ModelAndView getRtnPackListInfo(@RequestBody SearchWebOrder vo) throws Throwable
	{
		return AjaxJsonModelHelper.create(pEDMWEB0005Service.selectRtnPackInfo(vo));
	}


	/**
	 * Desc : 엑셀 정보 삭제
	 * @Method Name : getExcelOrdDelete
	 * @param TedPoOrdPackVO
	 * @param HttpServletRequest
	 * @return message
	 */
	@RequestMapping("/edi/weborder/tedRtnPackDelete.do")
	public ModelAndView getExcelRtnDelete(@RequestBody EdiRtnPackVO vo) throws Exception
	{
		String message;
		String result;

		message = messageSource.getMessage("msg.common.fail.delete", null, Locale.getDefault());

		try {
			result = pEDMWEB0005Service.deleteExcelRtnInfo(vo);
			if(result.equals("suc")) return AjaxJsonModelHelper.create("");
			else return AjaxJsonModelHelper.create(message);
		} catch (Exception e) {
			return AjaxJsonModelHelper.create(message);
		}

	}

	/**
	 * TODAY 협력사코드별  반품승인 등록 처리(MARTNIS 전송)
	 * @Method Name : sendRetReqProdData
	 * @param SearchWebOrder
	 * @return AjaxJsonModelHelper(HashMap<String, String>)
	 */
	@RequestMapping("edi/weborder/tedRtnPackRequest.do")
	public ModelAndView sendRetReqProdData(@RequestBody SearchWebOrder vo, HttpServletRequest request) throws Exception
	{
		String message1, message2, message3, message4, message5;
		String result;

		message1 = messageSource.getMessage("msg.common.fail.insert", null, Locale.getDefault());
		message2 = messageSource.getMessage("msg.weborder.fail.insert.error", null, Locale.getDefault());
		message3 = messageSource.getMessage("msg.weborder.fail.insert.excel.dupl", null, Locale.getDefault());
		message4 = messageSource.getMessage("msg.weborder.fail.insert.rtndupl", null, Locale.getDefault());
		message5 = messageSource.getMessage("msg.weborder.fail.insert.nodata", null, Locale.getDefault());

		HashMap<String, String> rtnData = new HashMap<String, String>();
		rtnData.put("state","-1");
		try
		{
			result = pEDMWEB0005Service.insertExcelRtnInfo(vo, request);
			if(result.equals("suc")) {
				rtnData = pEDMWEB0005Service.insertSendReturnProdData(vo, request);
			}
			else if(result.equals("fail-001")) rtnData.put("message", message2);
			else if(result.equals("fail-002")) rtnData.put("message", message3);
			else if(result.equals("fail-003")) rtnData.put("message", message4);
			else if(result.equals("fail-004")) rtnData.put("message", message5);
			else rtnData.put("message", message1);
		}catch (Exception e) {
			rtnData.put("message", message1);
			return AjaxJsonModelHelper.create(rtnData);
		}
		return AjaxJsonModelHelper.create(rtnData);
	}

	/**
	 * Desc : 반품 정보 엑셀 업로드
	 * @Method Name : orderTotList
	 * @param SearchWebOrder
	 * @param HttpServletRequest
	 * @param Model
	 * @return view 페이지
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/edi/weborder/prlExcelUpload", method=RequestMethod.POST)
    public String ordExcelUpload(
    		@ModelAttribute("excel") ExcelUploadVO excel,
    		HttpServletRequest request,
    		BindingResult result,
    		Model model) throws IOException{

		SearchWebOrder searchParam = new SearchWebOrder();
		SimpleDateFormat format = new SimpleDateFormat("HHmm", Locale.KOREA);

		int nowDt = Integer.parseInt(format.format(new Date()));

		String vendorWebOrdFrDt = ConfigUtils.getString("edi.weboder.vendor.rtn");
		String vendorWebOrdToDt = ConfigUtils.getString("edi.weboder.vendor.to");

		if( ( nowDt < Integer.parseInt(vendorWebOrdFrDt)) || ( nowDt > Integer.parseInt(vendorWebOrdToDt)) ){
			return "edi/weborder/PEDMWEB0099";
		}

		searchParam.setVendorWebOrdFrDt(vendorWebOrdFrDt);
		searchParam.setVendorWebOrdToDt(vendorWebOrdToDt);
		model.addAttribute("paramMap",searchParam);

		if(!result.hasErrors()){
			FileOutputStream outputStream = null;
			String value = null;
			String fileName = null;
			String filePath = null;
			String stateCode = null;
			String saveResult = null;
			String cellNullFg = null;
			String breakFg = null;

			XSSFWorkbook workbook = null;
			HSSFWorkbook oldWorkBook = null;
			File saveFile = null;
			FileInputStream file = null;
			String ordVenCd = request.getParameter("entp_cd");

	        try {
	        	//save & load location
				fileName = excel.getFile().getOriginalFilename();
				filePath = ConfigUtils.getString("edi.weborder.excel.path") + excel.getFile().getOriginalFilename();
				saveFile = new File(filePath);

				//save
				outputStream = new FileOutputStream(new File(filePath));
				outputStream.write(excel.getFile().getFileItem().get());

				//load
		        file = new FileInputStream(new File(filePath));

		        if(fileName.indexOf(".xlsx") > -1 || fileName.indexOf(".XLSX") > -1){
		        	workbook = new XSSFWorkbook(file);
		        }else{
		        	POIFSFileSystem myFileSystem = new POIFSFileSystem(file);
			    	oldWorkBook = new HSSFWorkbook(myFileSystem);
		        }
			} catch (Exception e) {
				outputStream.close();
				saveFile.delete();
				model.addAttribute("stateCode", "data-error");
				return "edi/weborder/PEDMWEB00051";
			}

	        //sheet수 취득
			//int sheetCn = workbook.getNumberOfSheets();

			 // 엑셀 데이터를 담을 VO
			EdiRtnPackListVO list = new EdiRtnPackListVO();
			EdiRtnPackVO excelData = null;

			if(fileName.indexOf(".xlsx") > -1 || fileName.indexOf(".XLSX") > -1){
				for(int cn = 0; cn < 1; cn++){
					XSSFSheet sheet = workbook.getSheetAt(cn);
					// cell 개수
			        int cells = 3;//sheet.getRow(0).getLastCellNum();
			        int rowNum = sheet.getLastRowNum();

			        if(rowNum > 10000){
			        	stateCode = "fail-006";
			        	model.addAttribute("stateCode", stateCode);
	       				return "edi/weborder/PEDMWEB00051";
			        }else{
			        	// sheet 개수 만큼 반복
				        for(Row row : sheet){
				        	cellNullFg = "F";
			        		excelData = new EdiRtnPackVO();

			        		if(sheet.getRow(row.getRowNum()).getCell((short)0).getCellType() == 3 ){
				        		if(row.getCell((short)0).getCellType() == 3 && row.getCell((short)1).getCellType() == 3 && row.getCell((short)2).getCellType() == 3){
				        			cellNullFg = "T";
			        			}
			        		}

			        		if(!"T".equals(cellNullFg)){
			        			// cell 개수 만큼 반복
			        			for (int c = 0; c < cells; c++) {
			        				value = null;
		        					if(sheet.getRow(row.getRowNum()).getCell((short)0).getCellType() == 3){
		        						breakFg = "T";
		        						break;
		        					}else if(!( row.getCell((short)0).getCellType() == 3|| row.getCell((short)1).getCellType() == 3|| row.getCell((short)2).getCellType() == 3)){
		        						switch (row.getCell(c).getCellType()) {
				        					case XSSFCell.CELL_TYPE_FORMULA:
				        						value = row.getCell(c).getCellFormula();
				        						break;
				        					case XSSFCell.CELL_TYPE_NUMERIC:
				        						value = "" + Long.toString(Long.parseLong(String.valueOf(Math.round(row.getCell(c).getNumericCellValue()))));
				        						break;
				        					case XSSFCell.CELL_TYPE_STRING:
				        						value = "" + row.getCell(c).getStringCellValue();
				        						break;
				        					case XSSFCell.CELL_TYPE_BLANK:
				        						value = "";
				        						break;
				        					case XSSFCell.CELL_TYPE_ERROR:
				        						value = "" + row.getCell(c).getErrorCellValue();
				        						break;
				        					default:
				                   		}
		        					}

			                   		if(value == null || "".equals(value) || value.length() == 0){
			                   			// 엑셀 값이 null인 경우
			                   			stateCode = "fail-005";
			                   		}else{
			                   			if(NumberUtil.isNumber(value)){
				                   			// 엑셀 데이터를 excelData에 담음
					                   		if( c == 0) {
					                   			// 점포코드가 3자리를 초과 했을경우.
					                   			if(value.getBytes().length > 3) stateCode = "fail-002";
					                   			else excelData.setStrCd(value);
					                   		}
					                   		if( c == 1 ) {
					                   			// 상품코드가 10자리를 초과 했을경우.
					                   			if(value.getBytes().length > 10) stateCode = "fail-003";
					                   			else excelData.setProdCd(value);
					                   		}
					                   		if( c == 2 ) {
					                   			//  상품수량이 10자리를 초과 했을경우.
					                   			if(value.getBytes().length > 10) stateCode = "fail-004";
					                   			else excelData.setRrlQty(value);
					                   		}
				                   		}else{
				                   			// 엑셀 값이 숫자가 아닌 경우.
				                   			stateCode = "fail-001";
				                   		}
			                   		}

			                   		// 에러 코드가 있는 경우 에러코드 리턴. 등록 화면으로 이동.
			                   		if(stateCode != null){
			                   			outputStream.close();
			            				saveFile.delete();
			                   			model.addAttribute("stateCode", stateCode);
			               				return "edi/weborder/PEDMWEB00051";
			                   		}
			                    }

			        			if("T".equals(breakFg)){
			        				// 엑셀 값이 null인 경우
		                   			stateCode = "fail-005";
		                   			model.addAttribute("stateCode", stateCode);
		               				return "edi/weborder/PEDMWEB00051";
			        			}

		        				// 파일 이름과 협력 업체코드 Set
			     	            excelData.setPackFileNm(fileName);
			     	            excelData.setVenCd(ordVenCd);

			     	            // list에 엑셀 한 로우의 값 저장.
			     	            list.add(excelData);
			        		}
				        }
			        }
				}
			}else{
				for(int cn = 0; cn < 1; cn++){
					HSSFSheet sheet =oldWorkBook.getSheetAt(cn);
					// cell 개수
			        int cells = 3;//sheet.getRow(0).getLastCellNum();
			        int rowNum = sheet.getLastRowNum();

			        if(rowNum > 10000){
			        	stateCode = "fail-006";
			        	model.addAttribute("stateCode", stateCode);
	       				return "edi/weborder/PEDMWEB00051";
			        }else{
			        	// sheet 개수 만큼 반복
				        for(Row row : sheet){
				        	cellNullFg = "F";
			        		excelData = new EdiRtnPackVO();

			        		if(sheet.getRow(row.getRowNum()).getCell((short)0) == null ){
				        		if(row.getCell((short)0) == null && row.getCell((short)1) == null && row.getCell((short)2) == null){
				        			cellNullFg = "T";
			        			}
			        		}

			        		if(!"T".equals(cellNullFg)){
			        			// cell 개수 만큼 반복
			        			for (int c = 0; c < cells; c++) {
			        				value = null;
			        				if(sheet.getRow(row.getRowNum()).getCell((short)0) == null){
		        						breakFg = "T";
		        						break;
		        					}else if(!( row.getCell((short)0) == null || row.getCell((short)1) == null || row.getCell((short)2) == null)){
		        						switch (row.getCell(c).getCellType()) {
				        					case HSSFCell.CELL_TYPE_FORMULA:
				        						value = row.getCell(c).getCellFormula();
				        						break;
				        					case HSSFCell.CELL_TYPE_NUMERIC:
				        						value = "" + Long.toString(Long.parseLong(String.valueOf(Math.round(row.getCell(c).getNumericCellValue()))));
				        						break;
				        					case HSSFCell.CELL_TYPE_STRING:
				        						value = "" + row.getCell(c).getStringCellValue();
				        						break;
				        					case HSSFCell.CELL_TYPE_BLANK:
				        						value = "";
				        						break;
				        					case HSSFCell.CELL_TYPE_ERROR:
				        						value = "" + row.getCell(c).getErrorCellValue();
				        						break;
				        					default:
				                   		}
		        					}

			                   		if(value == null || "".equals(value) || value.length() == 0){
			                   			// 엑셀 값이 null인 경우
			                   			stateCode = "fail-005";
			                   		}else{
			                   			if(NumberUtil.isNumber(value)){
				                   			// 엑셀 데이터를 excelData에 담음
					                   		if( c == 0) {
					                   			// 점포코드가 3자리를 초과 했을경우.
					                   			if(value.getBytes().length > 3) stateCode = "fail-002";
					                   			else excelData.setStrCd(value);
					                   		}
					                   		if( c == 1 ) {
					                   			// 상품코드가 10자리를 초과 했을경우.
					                   			if(value.getBytes().length > 10) stateCode = "fail-003";
					                   			else excelData.setProdCd(value);
					                   		}
					                   		if( c == 2 ) {
					                   			//  상품수량이 10자리를 초과 했을경우.
					                   			if(value.getBytes().length > 10) stateCode = "fail-004";
					                   			else excelData.setRrlQty(value);
					                   		}
				                   		}else{
				                   			// 엑셀 값이 숫자가 아닌 경우.
				                   			stateCode = "fail-001";
				                   		}
			                   		}

			                   		// 에러 코드가 있는 경우 에러코드 리턴. 등록 화면으로 이동.
			                   		if(stateCode != null){
			                   			outputStream.close();
			            				saveFile.delete();
			                   			model.addAttribute("stateCode", stateCode);
			               				return "edi/weborder/PEDMWEB00051";
			                   		}
		        				}

			        			if("T".equals(breakFg)){
			        				// 엑셀 값이 null인 경우
		                   			stateCode = "fail-005";
		                   			model.addAttribute("stateCode", stateCode);
		               				return "edi/weborder/PEDMWEB00051";
			        			}

			        			// 파일 이름과 협력 업체코드 Set
			     	            excelData.setPackFileNm(fileName);
			     	            excelData.setVenCd(ordVenCd);

			     	            // list에 엑셀 한 로우의 값 저장.
			     	            list.add(excelData);
			        		}
	        			}
			        }
				}
			}

            file.close();
            outputStream.close();
			saveFile.delete();

    		try {
    			saveResult = pEDMWEB0005Service.insertRtnPackInfo(list, request, ordVenCd);
    			if(saveResult.equals("suc")) {
    				model.addAttribute("stateCode", "suc");
    			}else {
    				model.addAttribute("stateCode", "insert-fail");
    			}
    			list.clear();
   				return "edi/weborder/PEDMWEB00051";
    		} catch (Exception e) {
    			list.clear();
    			model.addAttribute("stateCode", "insert-fail");
   				return "edi/weborder/PEDMWEB00051";
    		}

	    }else{
	    	model.addAttribute("stateCode", "data-error");
	    }

		return "edi/weborder/PEDMWEB00051";
	}
}
