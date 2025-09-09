package com.lottemart.epc.edi.product.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lcnjf.util.StringUtil;
import com.lottemart.common.exception.AlertException;
import com.lottemart.common.util.ConfigUtils;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.util.BaseUtils;
import com.lottemart.epc.common.util.ExcelUploadUtil;
import com.lottemart.epc.edi.product.dao.NEDMPROEXCELDao;
import com.lottemart.epc.edi.product.model.ExcelTempUploadVO;
import com.lottemart.epc.edi.product.model.Result;
import com.lottemart.epc.edi.product.service.NEDMPROEXCELService;

import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.ConfigurationService;

@Service("nedmproExcelService")
public class NEDMPROEXCELServiceImpl implements NEDMPROEXCELService {
	
	@Autowired
	private ConfigurationService config;
	
	@Autowired
	private NEDMPROEXCELDao nedmproExcelDao;
	
	final static Logger logger = LoggerFactory.getLogger(NEDMPROEXCELServiceImpl.class);

	/**
	 * NEW 엑셀 업로드 
	 */
	@Override
	public Result insertExcelUpload(ExcelTempUploadVO paramVo , HttpServletRequest request, MultipartFile mFile) throws Exception {
		Result result = new Result();					//결과 반환용 Result
		
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> hData = new HashMap<String, Object>();
		result.setMsgCd("fail");
		
		
		//파일확장자 제한 (Excel)
        String fileExtLimitStr = ConfigUtils.getString("fileCheck.atchFile.ext.excel");
        String[] fileExtLimit = (fileExtLimitStr == null || "".equals(fileExtLimitStr))? null : fileExtLimitStr.split("\\|");
        List<String> extLimit = (fileExtLimit == null)? null : Arrays.asList(fileExtLimit);
		
		String value = null;			// 엑셀 데이터
		String fileName = "";			//파일명
		String excelPath = "";		//엑셀 파일 물리경로
		String excelSysKnd = paramVo.getExcelSysKnd();			//엑셀 구분값 (500 : 원가변경, 400:신상품 입점 제안)
		String fileExt = "";			//업로드파일 확장자
		String[] myVenCds = paramVo.getVenCds();	//소속 회사 리스트
		List<String> myVenCdList = (myVenCds != null && myVenCds.length > 0)? Arrays.asList(myVenCds) : null;
		
		int completeCnt = 0;		//정상 등록 건수
		int errorCnt = 0;			//오류 건수
		
		DataMap uploadMap = upload(request);
		if (uploadMap.getString("success").equals("false")) {
			throw new AlertException(uploadMap.getString("message"));
		}
		
		excelPath = uploadMap.getString("uploadFilepath");  // 업로드 된 파일 full path
		fileName  = uploadMap.getString("uploadedFileName"); // 업로드 된 파일 명
		
		
		String filePath = null;			// 파일 저장 경로
		File saveFolder= null;			// 엑셀 데이터 저장 경로
		FileInputStream fileInputStream = null;
		FileOutputStream outputStream = null;
		
		XSSFWorkbook workbookXss = null;
		HSSFWorkbook workbookHss = null;
		
		try {
			//엑셀 임시저장 경로
			fileName = mFile.getOriginalFilename();
			//전체경로 경로순회 문자열 및 종단문자 제거
			fileName = StringUtil.getCleanPath(fileName, true);
			
			//업로드파일 확장자
			fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
			/***************************************/
			if(!extLimit.contains(fileExt.toLowerCase())) {
				result.setMessage("업로드 불가능한 파일입니다. (허용 확장자:"+fileExtLimitStr+")");
				return result;
			}
			/***************************************/
			
			//excelPath 에 파일명까지 포함되어 있음
			filePath = excelPath + fileName;  
			filePath = excelPath;  
			
			//전체경로 경로순회 문자열 및 종단문자 제거
			excelPath = StringUtil.getCleanPath(excelPath, true);
			
			// 억셀 임시저장 폴더가 없을 경우 생성한다.
			saveFolder = new File(excelPath);
			if (!saveFolder.exists()) {
				saveFolder.mkdirs();
			}
			
			// 파일 저장
		    File savedFile = new File(excelPath); 
		    
		    // 파일이 존재하는지 확인 후 저장
	        if (!savedFile.exists()) {
	            mFile.transferTo(savedFile);
	        }

	        // 저장된 파일을 읽기 위해 FileInputStream 생성
	        fileInputStream = new FileInputStream(savedFile);
			
			//DB 등록 작업 Start
			if (fileName.toUpperCase().indexOf(".XLSX") > -1) {        //넘어온 파일이 xls x확장자라면
				workbookXss = new XSSFWorkbook(fileInputStream);
				XSSFSheet sheetXss = workbookXss.getSheetAt(0);        //첫번 째 시트 가져오기

				int cells = sheetXss.getRow(0).getLastCellNum();    // cell 개수 : 업로드할 셀 개수 세팅
				int rows = sheetXss.getPhysicalNumberOfRows();        // row 개수

				list = new ArrayList<HashMap<String, Object>>();

				// rows 개수 만큼 반복
				for (int i = 0; i < rows; i++) {
					Row row = sheetXss.getRow(i);
					hData = new HashMap<String, Object>();
					// cell 개수 만큼 반복
					
					String stsChk = "S";
					
					// 행사 제안 or 반품( 추후 cell row 공통으로 변경해야함 
					if(excelSysKnd.equals("310") || excelSysKnd.equals("320")) {
						if (i >= 1) {            //i = 0,1 인경우는 헤더로 계산 X
							for (int c = 0; c < cells; c++) {
								value = ExcelUploadUtil.cellValueXss(row, c);
								hData.put(("data" + (c+1)), value);
								if(c == 0 && "".equals(value)) {
									stsChk = "F";
								}
							}// end for(cell 개수 만큼 반복 )
							if("S".equals(stsChk)) {
								list.add(hData);
							}
						}
					}else {
						if (i >= 2) {            //i = 0,1 인경우는 헤더로 계산 X
							for (int c = 0; c < cells; c++) {
								value = ExcelUploadUtil.cellValueXss(row, c);
								hData.put(("data" + (c+1)), value);
								System.out.println("data:"+  value);
								if(c == 0 && "".equals(value)) {
									stsChk = "F";
								}
							}// end for(cell 개수 만큼 반복 )
							if("S".equals(stsChk)) {
								list.add(hData);
							}
						}
					}
				}

			} else {
				workbookHss = new HSSFWorkbook(fileInputStream);
				HSSFSheet sheetHss = workbookHss.getSheetAt(0);                    //첫번 째 시트 가져오기

				int cells = sheetHss.getRow(0).getLastCellNum();                // cell 개수 : 업로드할 셀 개수 세팅
				int rows = sheetHss.getPhysicalNumberOfRows();                    // row 개수

				// rows 개수 만큼 반복
				for (int i = 0; i < rows; i++) {
					Row row = sheetHss.getRow(i);
					hData = new HashMap<String, Object>();
					String stsChk = "S";
					// 헤더는 저장하지 않음(3번째 줄부터 읽는거임 데이터로 헤더에따라 유동적으로 변경됨)
					// 행사 제안 or 반품( 추후 cell row 공통으로 변경해야함 
					if(excelSysKnd.equals("310") || excelSysKnd.equals("320")) {
						if (i >= 1) {
							// cell 개수 만큼 반복
							for (int c = 0; c < cells; c++) {
								value = ExcelUploadUtil.cellValueHss(row, c);
								hData.put("data" + (c+1), value);
								if(c == 0 && "".equals(value)) {
									stsChk = "F";
								}
							}
							if("S".equals(stsChk)) {
								list.add(hData);
							}
						}
					}else {
						if (i >= 2) {
							// cell 개수 만큼 반복
							for (int c = 0; c < cells; c++) {
								value = ExcelUploadUtil.cellValueHss(row, c);
								hData.put("data" + (c+1), value);
								if(c == 0 && "".equals(value)) {
									stsChk = "F";
								}
							}
							if("S".equals(stsChk)) {
								list.add(hData);
							}
						}
					}
				}
			}
		}catch (Exception e) {
			logger.error(e.getMessage());
			return result;
		}finally {
			// 서버 경로에서 파일 삭제(물리파일 삭제)
			String fileFullPath = excelPath;
			File f = new File(fileFullPath);

			if (outputStream != null) {
			    try {
			        outputStream.close();
			    } catch (Exception ignore) {
			        logger.error("outputStream close 에러 : " + ignore.getMessage());
			    }
			}

			if (fileInputStream != null) {
			    try {
			        fileInputStream.close(); // 먼저 닫기
			    } catch (Exception ignore) {
			        logger.error("fileInputStream close 에러 : " + ignore.getMessage());
			    }
			}

			// 파일이 존재할 경우에만 삭제
			synchronized (this) {
                try {
                    if (f.exists()) {
                        if (!f.delete()) {
                            throw new IOException("파일 삭제 실패: " + fileFullPath);
                        }
                    } else {
                        logger.warn("삭제하려는 파일이 이미 없음: " + fileFullPath);
                    } 
                } catch(IOException e) {
                    logger.error(e.getMessage());
                    return result;
                }
            }
			
			/**
			// 서버 경로에서 파일 삭제(물리파일삭제)
			String fileFullPath = excelPath;
			File f = new File(fileFullPath);
			f.delete();
			if (outputStream  != null) {
				try {
					outputStream.close();
				}catch (Exception ignore) {
					logger.error("outputStream close 에러 : " + ignore.getMessage());
				}
			}
			if (fileInputStream != null) {
				try {
					Path path = Paths.get(filePath);
					Files.delete(path);
					fileInputStream.close();
				} catch (Exception ignore) {
					logger.error("file close 에러 : " + ignore.getMessage());
					ignore.printStackTrace();
				}
			}
			*/
		}
		
		
		// 업로드 데이터 존재유무 체크
		int listSize = list.size();
		if(listSize <= 0){					//입력된 데이터가 존해하지않으면, 리턴
			result.setMsgCd("noneList");
			result.setMessage("업로드할 데이터가 없습니다.");
			return result;
		}
		
		
		HashMap<String, String> excelMap = new HashMap<String, String>();
		excelMap.put("regId",paramVo.getRegId());
		excelMap.put("entpCd",paramVo.getEntpCd());
		excelMap.put("excelSysKnd",paramVo.getExcelSysKnd());
		excelMap.put("excelWorkKnd",paramVo.getExcelWorkKnd());
		
		try {
			nedmproExcelDao.deleteExcelTemp(excelMap);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
				
		// ==================================== 엑셀 데이터 검증
		String chkVenCd = "";		//체크할 업체코드
		if(excelSysKnd.equals("500")) {
			//원가변경요청
			for(int i=0; i<list.size(); i++){
				HashMap<String, Object> filterData = list.get(i);

				//소속 회사 데이터인지 확인
				chkVenCd = MapUtils.getString(filterData, "entpCd", "");
				if(!myVenCdList.contains(chkVenCd)) {
					result.setMessage("소속 회사에 대한 데이터만 업로드 가능합니다. (업체코드:"+chkVenCd+")");
					return result;
				}
				
				//엑셀내에있는 변경원가 금액에 , 있으면 제거  
				String chgReqCost = BaseUtils.strCheck(filterData.get("data2"), "");
				String newChgReqCost = chgReqCost.replaceAll(",", "");
				filterData.put("data2", newChgReqCost);
			}
		}else if(excelSysKnd.equals("400")) {
			//상품입점제안
			for(int i=0; i<list.size(); i++){
				HashMap<String, Object> filterData = list.get(i);
				
				//소속 회사 데이터인지 확인
				chkVenCd = MapUtils.getString(filterData, "data1", "");
				if(!myVenCdList.contains(chkVenCd)) {
					result.setMessage("소속 회사에 대한 데이터만 업로드 가능합니다. (업체코드:"+chkVenCd+")");
					return result;
				}
				
//				for(int j=i+1; j<list.size(); j++){
//					HashMap<String, Object> targetData = list.get(j);
//					
//					if(venCd.equals(BaseUtils.strCheck(targetData.get("data1"), ""))){
//						logger.info("파트너사 일치!");
//					}else {
//						result.setMsgCd("fail");
//						logger.info("필수데이터가 일치하지 않습니다.!");
//						result.setMessage("파트너사 불일치");
//						return result;
//					}
//				}
			}
		}else if(excelSysKnd.equals("310")) {
			// 행사 제안 
			for(int i=0; i<list.size(); i++){
				HashMap<String, Object> filterData = list.get(i);
				String ean11 = BaseUtils.strCheck(filterData.get("data1"), "");
				String matnr = BaseUtils.strCheck(filterData.get("data2"), "");
				
				//소속 회사 데이터인지 확인 --- 행사는 쿼리 내에서 필터링하므로 생략
				
				for(int j=i+1; j<list.size(); j++){
					HashMap<String, Object> targetData = list.get(j);
					
				}
			}
		}else if(excelSysKnd.equals("320")) {
			// 반품 등록
			for(int i=0; i<list.size(); i++){
				HashMap<String, Object> filterData = list.get(i);

				//소속 회사 데이터인지 확인
				chkVenCd = MapUtils.getString(filterData, "data3", "");
				if(!myVenCdList.contains(chkVenCd)) {
					result.setMessage("소속 회사에 대한 데이터만 업로드 가능합니다. (업체코드:"+chkVenCd+")");
					return result;
				}
				
				//엑셀내에있는 변경원가 금액에 , 있으면 제거  
				String chgReqCost = BaseUtils.strCheck(filterData.get("data2"), "");
				String newChgReqCost = chgReqCost.replaceAll(",", "");
				filterData.put("data2", newChgReqCost);
			}
			
		}
		// ==================================== 엑셀 데이터 검증 end
	
		
		
		//list에 담긴 데이터 db에 저장 map에 파라미터 재가공해서 넣어서 디비에 저장 *
		for (HashMap<String, Object> map : list) {
			map.put("regId",paramVo.getRegId());
			map.put("entpCd",paramVo.getEntpCd());
			map.put("excelSysKnd",paramVo.getExcelSysKnd());
			map.put("excelWorkKnd",paramVo.getExcelWorkKnd());
			
			try {
				//excel temp 테이블에 데이터 저장 
				nedmproExcelDao.insertExcelTemp(map);		
			} catch (Exception e) {
				logger.error(e.getMessage());
				errorCnt++;
				continue;
			}
		}

		String msg = "";
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<HashMap<String, Object>> dataList = new ArrayList<HashMap<String,Object>>();
		//validation 유효성여부 확인
		boolean resultValid = true;
		
		if(excelSysKnd.equals("500")) {	//원가변경요청
			String pSellCd = "";		//판매코드
//			String pProdCd = "";		//상품코드
			
			//------------ 구매조직 SETTING
			String[] pPurDepts = paramVo.getPurDepts();	//선택한 구매조직 parameter ex) [KR02,KR04,KR05]
			String pPurDept = pPurDepts[0];				//KR02,KR04,KR05
			String[] arrayItem = pPurDept.split(",");	
			String purDeptParam = arrayItem[0]; 		//선택한 첫 번째 구매조직 ex) KR02
			
			//선택한 구매조직 - 원가변경 상태 등 체크를 위함
			paramVo.setPurDepts(arrayItem);
			//첫 번째 구매조직 기준으로 원가 조회
			paramVo.setPurDept(purDeptParam);
			
			//temp에 저장된 엑셀 데이터 조회해서 가져옴 
			Map<String,Object> prcExcelParam = new HashMap<String,Object>(excelMap);
			prcExcelParam.put("purDepts", arrayItem);		//구매조직 array
			prcExcelParam.put("purDept", purDeptParam);		//원가 표시 기준 구매조직 (구매조직 n개 선택 시, 첫 번째 구매조직이 꽂힘)
			
			dataList = nedmproExcelDao.selectExcelTempUploadProdChgPrcList(prcExcelParam);
			// 오류시 엑셀 행의 인덱스를 나타내기위한 변수 
			int i = 1;
			
			String chkProdZzmkt = "";		//validaiton_상품의 적용채널 미존재
			String chkSrcmkCd = "";			//validaiton_유효하지 않은 판매코드
			String chkProdCd = "";			//validaiton_유효하지 않은 상품코드
			String chkOrdUnit = "";			//validaiton_기본단위가 존재하지 않는 상품
			String chkProdSts = "";			//validaiton_등록불가능한 상태
			String chkProdStsTxt = "";		//validaiton_등록불가능한 상태(메세지반환용 Text)
			String chkPurDept = "";			//validaiton_선택한 구매조직으로 선택가능한 상품인지
			String chkDuplRnum = "";		//validaiton_엑셀 내 중복 데이터
			String chgCostYn = "";			//validaiton_원가변경가능대상여부
			
			for(HashMap<String, Object> chkMap : dataList) {
				pSellCd = MapUtils.getString(chkMap, "data1");		//판매코드
				
				chkProdZzmkt = MapUtils.getString(chkMap, "chkProdZzmkt");		//validaiton_상품의 적용채널 미존재
				chkSrcmkCd	= MapUtils.getString(chkMap, "chkSrcmkCd");			//validaiton_유효하지 않은 판매코드          
				chkProdCd	= MapUtils.getString(chkMap, "chkProdCd");			//validaiton_유효하지 않은 상품코드          
				chkOrdUnit	= MapUtils.getString(chkMap, "chkOrdUnit");			//validaiton_기본단위가 존재하지 않는 상품      
				chkProdSts	= MapUtils.getString(chkMap, "chkProdSts");			//validaiton_등록불가능한 상태             
				chkProdStsTxt = MapUtils.getString(chkMap, "chkProdStsTxt");	//validaiton_등록불가능한 상태(메세지반환용 Text)
				chkPurDept = MapUtils.getString(chkMap, "chkPurDept");			//validaiton_구매조직으로 선택가능한 상품인지
				chkDuplRnum = MapUtils.getString(chkMap, "chkDuplRnum");		//validaiton_엑셀 내 중복 데이터
				chgCostYn	= MapUtils.getString(chkMap, "chgCostYn");			//validaiton_원가변경가능대상여부
				
				if("F".equals(chkSrcmkCd)) {
					msg += "유효하지 않은 판매코드 입니다.\n다시 확인해주세요. ("+(i)+"번째 행)\n" ;
					resultValid = false;
					errorCnt++;
				}else if("F".equals(chkProdCd)) {
					msg += "상품코드가 존재하지 않습니다.\n다시 확인해주세요. ("+(i)+"번째 행)\n" ;
					resultValid = false;
					errorCnt++;
				}else if("F".equals(chkProdZzmkt)) {
					msg += "적용 가능한 채널이 존재하지 않습니다. ("+(i)+"번째 행)\n" ;
					resultValid = false;
					errorCnt++;
				}else if(!"Y".equalsIgnoreCase(chgCostYn)){
					msg += "해당 상품은 원가변경요청이 불가능한 상품입니다. ("+(i)+"번째 행)\n" ;
					resultValid = false;
					errorCnt++;
				}else if("F".equals(chkOrdUnit)) {
					msg += "상품에 등록된 단위가 없습니다.\n관리자에게 문의하세요. ("+(i)+"번째 행)\n" ;
					resultValid = false;
					errorCnt++;
				}else if("F".equals(chkProdSts)) {
					msg += "해당 판매코드에 대해 임시저장 또는 요청중인 원가변경요청이 존재합니다.\n구매조직 : "+chkProdStsTxt+" ("+(i)+"번째 행)\n" ;
					resultValid = false;
					errorCnt++;
				}else if("F".equals(chkPurDept)) {
					msg += "선택한 구매조직 중 해당 상품을 적용할 수 없는 건이 존재합니다. ("+(i)+"번째 행)\n" ;
					resultValid = false;
					errorCnt++;
				}else if(!"1".equals(chkDuplRnum)){	//중복 데이터 존재 시, 첫 번째 행만 insert
					msg += "중복된 상품정보가 존재합니다.\n해당 상품의 첫 번째 데이터만 업로드 되었습니다. ("+(i)+"번째 행)\n" ;
					//exception 처리 안 할거라서 flag 안 꺾음 (화면에서 안 그릴거임)
					//resultValid = false;
					errorCnt++;
				}else {
					completeCnt++;
				}
				
				i++;
			}
			
//			for(HashMap<String, Object> chkMap : dataList) {
//				String sellCd = String.valueOf(chkMap.get("data1"));
//				String prodCd = String.valueOf(chkMap.get("data2"));
//
//				
//				paramVo.setSrcmkCd(sellCd);
//				paramVo.setProdCd(prodCd);
//				
//				//첫번째구매조직 기준 원가 가져오기
//				String[] purDepts = paramVo.getPurDepts();	//[KR02,KR04,KR05]
//				String purDept = purDepts[0];	//KR02,KR04,KR05
//				String[] arrayItem = pPurDept.split(",");	
//				String purDeptParam = arrayItem[0]; 		//선택한 첫 번째 구매조직 ex) KR02
//				paramVo.setPurDepts(arrayItem);
//				
//				//원가 등록된 값들이 있는지 확인 
//				ExcelTempUploadVO excelVo = nedmproExcelDao.selectExcel500Validation(paramVo);
//				
//				int prStsCnt00 = Integer.parseInt(excelVo.getPrStsCnt00());		//임시저장상태 요청건수
//				int prStsCnt01 = Integer.parseInt(excelVo.getPrStsCnt01());		//승인대기상태 요청건수
//				
//				String prStsDept00 = excelVo.getPrStsDept00();
//				String prStsDept01 = excelVo.getPrStsDept01();
//				
//				//임시저장 건이 있을 경우, 추가불가
//				if(prStsCnt00 > 0) {
//					result.setMsgCd("fail");
//					msg += "동일한 판매/상품코드에 대한 임시저장 건이 존재합니다.\n(판매코드:" + sellCd + "/상품코드:" + prodCd + "/구매조직 +" + prStsDept00 + ") [ " +i+  "번째행 ]";  
//					resultValid = false;
//					errorCnt++;
//					continue;
//				}
//				
//				//승인대기 건이 있을 경우, 추가불가
//				if(prStsCnt01 > 0) {
//					result.setMsgCd("fail");
//					msg += "원가변경요청 승인 대기중입니다.\n(판매코드:" + sellCd + "/상품코드:" + prodCd + "/구매조직 +" + prStsDept01 + ") [" +i+  "번째행 ]";
//					resultValid = false;
//					errorCnt++;
//					continue;
//				}
//				
//				//원가 저장 변수 
//				String orgCost = "";
//				
//				HashMap<String, String> paramMap = new HashMap<>();
//				
//				paramMap.put("purDept", purDeptParam);
//				paramMap.put("sellCd", sellCd);
//				paramMap.put("prodCd", prodCd);
//				
//				if(!sellCd.isEmpty() && !prodCd.isEmpty()) {
//					//입력받은 판매코드 기준 원가 조회
//					orgCost = nedmproExcelDao.selectExcelOrdCostInfo(paramMap);
//				}
//				
//				if(!orgCost.isEmpty()) {
//				    // 원가 해당 데이터리스트에 저장 
//				    chkMap.put("orgCost", orgCost);
//				    
//					completeCnt++;
//				}else {
//					errorCnt++;
//				}
//				
//				
//				i++;
//			}
			
		}else if(excelSysKnd.equals("310")) {
			//temp에 저장된 엑셀 데이터 조회해서 가져옴 
			dataList = nedmproExcelDao.selectExcelTempUploadProdChk(excelMap);
			
	        // "id" 키 기준으로 중복 제거
	        Set<Object> seen = new HashSet<>();
	        /*List<HashMap<String, Object>> deduplicated = dataList.stream()
		            .filter(m -> seen.add(m.get("id")))
		            .collect(Collectors.toList());*/
	        dataList = dataList.stream()
	            .filter(m -> seen.add(m.get("data1")))
	            .collect(Collectors.toList());
	        // 결과 출력
	        //deduplicated.forEach(System.out::println);
	        //dataList = deduplicated;
	        int i = 1;
			
			for(HashMap<String, Object> chkMap : dataList) {
				String stsChk = String.valueOf(chkMap.get("stsChk"));
				String srcmkChk = String.valueOf(chkMap.get("srcmkChk"));
				String prodCdChk = String.valueOf(chkMap.get("prodCdChk"));
				
				if( "F".equals(srcmkChk)) {
					msg += "판매바코드를 잘못입력하셨습니다. 다시 확인해주세요 (" +(i+1)+"번째 행)\n" ;
					errorCnt++;
				}else if("F".equals(prodCdChk)){
					msg += "상품코드를 잘못입력하셨습니다. 다시 확인해주세요 (" +(i+1)+"번째 행)\n" ;
					errorCnt++;
				}else {
					completeCnt++;
				}
				i++;
			}
			//evntList.add(hData);
		}else if(excelSysKnd.equals("320")) {
			// 반품
			//temp에 저장된 엑셀 데이터 조회해서 가져옴 
			dataList = nedmproExcelDao.selectExcelTempUploadRtnChk(excelMap);
			
	        // "id" 키 기준으로 중복 제거
	        
	        int i = 1;
			
			for(HashMap<String, Object> chkMap : dataList) {
				String stsChk = String.valueOf(chkMap.get("stsChk"));
				String srcmkChk = String.valueOf(chkMap.get("srcmkChk"));
				String prodCdChk = String.valueOf(chkMap.get("prodCdChk"));
				
				String rtrsn = String.valueOf(chkMap.get("rtrsn"));
				String rtrsd = String.valueOf(chkMap.get("rtrsd"));
				String rtplc = String.valueOf(chkMap.get("rtplc"));
				String teamCdCHK = String.valueOf(chkMap.get("teamCdChk"));
				String bukrsCHK = String.valueOf(chkMap.get("bukrsChk"));
				
				if( "F".equals(srcmkChk)) {
					msg += "판매바코드를 잘못입력하셨습니다. 다시 확인해주세요 (" +(i+1)+"번째 행)\n" ;
					resultValid = false;
					errorCnt++;
				}else if("F".equals(prodCdChk)){
					msg += "상품코드를 잘못입력하셨습니다. 다시 확인해주세요 (" +(i+1)+"번째 행)\n" ;
					resultValid = false;
					errorCnt++;
				}else if("F".equals(rtrsn)){
					msg += "반품사유코드를 잘못입력하셨습니다. 다시 확인해주세요 (" +(i+1)+"번째 행)\n" ;
					resultValid = false;
					errorCnt++;
				}else if("F".equals(rtrsd)){
					msg += "반품상세사유코드를 잘못입력하셨습니다. 다시 확인해주세요 (" +(i+1)+"번째 행)\n" ;
					resultValid = false;
					errorCnt++;
				}else if("F".equals(rtplc)){
					msg += "반품장소코드를 잘못입력하셨습니다. 다시 확인해주세요 (" +(i+1)+"번째 행)\n" ;
					resultValid = false;
					errorCnt++;
				}else if("0".equals(teamCdCHK)){
					msg += "팀코드를 잘못입력하셨습니다. 다시 확인해주세요 (" +(i+1)+"번째 행)\n" ;
					resultValid = false;
					errorCnt++;
				}else if("F".equals(bukrsCHK)){
					msg += "계열사코드를 잘못입력하셨습니다. 다시 확인해주세요 (" +(i+1)+"번째 행)\n" ;
					resultValid = false;
					errorCnt++;
				}else {
					completeCnt++;
				}
				i++;
			}
			//evntList.add(hData);
		}else {
			//temp에 저장된 엑셀 데이터 조회해서 가져옴 
			dataList = nedmproExcelDao.selectExcelTempUploadList(excelMap);
			for(HashMap<String, Object> chkMap : dataList) {
				completeCnt++;
			}
		}
		
		
		/*
		 * 아예 데이터 안가져갈꺼면 jsp로 이거쓰기 
		 * 
		if(excelSysKnd.equals("500")) {
			//temp에 저장된 엑셀 데이터 조회해서 가져옴 
			dataList = nedmproExcelDao.selectExcelTempUploadProdChgPrcList(excelMap);
			
			if(dataList.size() >0) {
				
				//배열 순차적 접근을 위해 Iterator 사용 
				Iterator<HashMap<String, Object>> iterator = dataList.iterator();
				while (iterator.hasNext()) {
				    HashMap<String, Object> chkMap = iterator.next();
				    String prStat = String.valueOf(chkMap.get("prStat"));

				    if (prStat.equals("00")) {
				        result.setMessage("동일한 판매/상품코드에 대한 임시저장 건이 존재합니다.");
				        errorCnt++;
				        iterator.remove(); // 해당 상태값에 해당하면 리스트에서 항목 제거
				    } else if (prStat.equals("01")) {
				        result.setMessage("원가변경요청 승인 대기중입니다.\n동일한 판매/상품코드에 대한 요청이 불가능합니다.");
				        errorCnt++;
				        iterator.remove(); // 해당 상태값에 해당하면 리스트에서 항목 제거
				    }
				}	
			}
		}else {
			//temp에 저장된 엑셀 데이터 조회해서 가져옴 
			dataList = nedmproExcelDao.selectExcelTempUploadList(excelMap);
		}
		*/
		
		
		//유효성검사에서 걸러지는게 있는경우 튕겨냄
		if(resultValid == false) {
			result.setMessage(msg);
			result.setCompleteCnt(completeCnt);
			result.setErrorCnt(errorCnt);
			result.setMsgCd("fail");
			return result;
		}
		
		//최종 보낼데이터 
		map.put("list", dataList);
		
		
		result.setResultMap(map);
		result.setMessage(msg);
		result.setCompleteCnt(completeCnt);
		result.setErrorCnt(errorCnt);
		result.setMsgCd("success");

		return result;
	}
	
	private DataMap upload(HttpServletRequest request) {
		
		String success = "false";
		String message = null;
		MultipartHttpServletRequest mptRequest = null;		
		FileOutputStream outputStream = null;
		boolean runtimeErr = false;
		String uploadFilepath = null;	//최종 업로드 파일 full path
		String orginFileName = ""; //원본 파일 명
		DataMap uploadMap = new DataMap();
		
		try {
			mptRequest = (MultipartHttpServletRequest)request;
			
			int actResult = 0;
			
			final Map<String, MultipartFile> files = mptRequest.getFileMap();
			
			if (!files.isEmpty()) {
				
				MultipartFile file;
				Entry<String, MultipartFile> entry;
				int fileUploadCnt = 0;
				
				Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
				Iterator<Entry<String, MultipartFile>> iter = files.entrySet().iterator();
				
				
				while (itr.hasNext()) {					
					file = itr.next().getValue();
				      
					if(!file.isEmpty()) {						
						fileUploadCnt++;
					 }
				}				
				//----------------------------------------
				
				if ( fileUploadCnt > 0 ) {
					try {
						String uploadDir = config.getString("bos.drm.file.path"); //DRM 해제를 위한 파일 업로드 경로				
						File dirPath = new File(uploadDir);
						if (!dirPath.exists()) {
					        dirPath.mkdirs();
					    }					
						uploadFilepath = uploadDir + File.separator;
					} catch (Exception e) {
						runtimeErr = true;
						throw new AlertException("업로드 관련 환경 설정시 문제가 발생했습니다.");
					}
				}
				
				while (iter.hasNext()) {
				    entry = iter.next();

				    file = entry.getValue();
				    orginFileName = file.getOriginalFilename();
				    uploadFilepath = uploadFilepath + DateUtil.getCurrentDateTimeAsString() + "_" + orginFileName;
				    
//				    System.out.println("uploadFilepath = " + uploadFilepath);
				    logger.debug("uploadFilepath = " + uploadFilepath);

			    	outputStream = new FileOutputStream(uploadFilepath);						
					actResult = FileCopyUtils.copy(file.getInputStream(), outputStream);
					if (actResult == 0) {
						runtimeErr = true;
						throw new AlertException("업로드 작업중에 오류가 발생하였습니다.");						
					}
				}
			}
			
			success = "true";
			
		} catch (Exception e) {
			logger.error("", e);
			if ( runtimeErr ) message = e.getMessage();
			else message = "fail"; // messageSource.getMessage("msg.dp.common.exec.error");
		} finally {
			if (outputStream != null) {
				try { outputStream.close(); } catch (Exception e) {
					logger.error("error --> " + e.getMessage());
				}
			}
			uploadMap.put("success", success);
			uploadMap.put("message", message);
			uploadMap.put("uploadFilepath", uploadFilepath);
			uploadMap.put("uploadedFileName", orginFileName);
		}
		return uploadMap;
	}
	
}
