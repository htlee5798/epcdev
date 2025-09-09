package com.lottemart.epc.common.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;

import lcn.module.common.util.DateUtil;
import lcn.module.framework.base.AbstractServiceImpl;
import lcn.module.framework.property.ConfigurationService;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lottemart.common.exception.AlertException;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.service.ExcelFileMngService;


/**
 * @Class Name : FileMngServiceImpl.java
 * @Description : BOS에서 공통으로 사용하는 엑셀파일 복호화 서비스
 * @Modification Information
 *
 *               수정일 수정자 수정내용 ------- -------- ---------------------------
 *
 *
 * @author
 * @since
 * @version 1.0
 * @see Copyright (C) 2011 by lottemart All right reserved.
 */
@Service("ExcelFileMngService")
public class ExcelFileMngServiceImpl extends AbstractServiceImpl implements ExcelFileMngService {

	private final Logger logger = LoggerFactory.getLogger(ExcelFileMngServiceImpl.class);

	@Autowired
	private ConfigurationService config;

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
			mptRequest = ( MultipartHttpServletRequest)request;

			int actResult = 0;

			final Map<String, MultipartFile> files = mptRequest.getFileMap();
			if ( !files.isEmpty()) {

				MultipartFile file;
				Entry<String, MultipartFile> entry;
				int fileUploadCnt = 0;

				Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
				Iterator<Entry<String, MultipartFile>> iter = files.entrySet().iterator();


				while ( itr.hasNext()) {
					file = itr.next().getValue();

					if( !file.isEmpty()) {
						fileUploadCnt++;
					 }
				}
				//----------------------------------------

				if ( fileUploadCnt > 0 ) {
					try {
						String uploadDir = config.getString( "bos.drm.file.path"); //DRM 해제를 위한 파일 업로드 경로
						File dirPath = new File( uploadDir);
						if ( !dirPath.exists()) {
					        dirPath.mkdirs();
					    }
						uploadFilepath = uploadDir + File.separator;
					} catch ( Exception e) {
						runtimeErr = true;
						throw new AlertException( "업로드 관련 환경 설정시 문제가 발생했습니다.");
					}
				}

				while ( iter.hasNext()) {
				    entry = iter.next();

				    file = entry.getValue();
				    orginFileName = file.getOriginalFilename();
				    uploadFilepath = uploadFilepath + DateUtil.getCurrentDateTimeAsString() + "_" + orginFileName;

				    log.debug( "uploadFilepath = " + uploadFilepath);

			    	outputStream = new FileOutputStream(uploadFilepath);
					actResult = FileCopyUtils.copy( file.getInputStream(), outputStream);
					if ( actResult == 0) {
						runtimeErr = true;
						throw new AlertException( "업로드 작업중에 오류가 발생하였습니다.");
					}
				}
			}

			success = "true";

		} catch ( Exception e) {
			log.error("", e);
			if ( runtimeErr ) message = e.getMessage();
			else message = "fail"; // messageSource.getMessage("msg.dp.common.exec.error");
		} finally {
			if ( outputStream != null) {
				try {
					outputStream.close();
				} catch ( Exception e) {
					logger.debug(e.getMessage());
				}
			}
			uploadMap.put( "success", success);
			uploadMap.put( "message", message);
			uploadMap.put( "uploadFilepath", uploadFilepath);
			uploadMap.put( "uploadedFileName", orginFileName);

			request.setAttribute( "colNms", mptRequest.getParameter( "colNms"));	//IBSheet 칼럼명(^로 구분자)
			request.setAttribute( "hdRow", mptRequest.getParameter( "hdRow"));		//엑셀 Header 행수
			request.setAttribute( "func", mptRequest.getParameter( "func"));				//실행 자바스크립트 함수명
			request.setAttribute( "sheetNm", mptRequest.getParameter( "sheetNm"));	//IBSheet 명
			request.setAttribute("sheetRemoveAll", mptRequest.getParameter( "sheetRemoveAll"));	//IBSheet 데이터 초기화 여부

		}
		return uploadMap;
	}

	/**
	 * 엑셀파일 복호화 후 데이터를 List<DataMap>으로 리턴
	 * 엑셀파일 컬럼수와 colNms의 갯수가 맞아야 함.
	 * 엑셀파일은 한개만 가능.
	 * @Method Name : readUploadExcelFile
	 * @param request
	 * @param colNms: 컬럼명 배열
	 * @param hdRow: 헤더 갯수(기본=1)
	 * @return
	 * @throws Exception
	 * @exception Exception
	 */
	public List<DataMap> readUploadExcelFile(HttpServletRequest request) throws Exception {

		List<DataMap> resList = new ArrayList<DataMap>();
		try{
		String upload_excel_path = "";  // DRM해제 전 파일 경로
		String dec_excel_path = "";  	// DRM해제 후 파일 경로

		DataMap uploadMap = upload(request);
		if ( uploadMap.getString( "success").equals( "false")) {
			throw new AlertException( uploadMap.getString( "message"));
		}
		upload_excel_path = uploadMap.getString( "uploadFilepath");  // 업로드 된 파일 full path
		String file_name  = uploadMap.getString( "uploadedFileName"); // 업로드 된 파일 명

		log.debug("업로드 파일명 ====> " + file_name);
//		log.debug("암호화 파일 경로 ====> " + upload_excel_path);
//
//		dec_excel_path = config.getString( "bos.drm.dec.file.path") + File.separator + DateUtil.getCurrentDateTimeAsString() + "_" + file_name;
//		log.debug("복호화  파일 경로 ====> " + dec_excel_path);
//
//		File FileSample = new File( upload_excel_path); //업로드 된 파일로 DRM해제 작업
//		if ( FileSample.length() == 0 ) {
//			throw new Exception( "원본 파일이 손상되었습니다.");
//		}
		//칼럼명 목록
		log.debug("===colNms===>>>"+( (String)request.getAttribute( "colNms")));
		String[] colNms = ( (String)request.getAttribute( "colNms")).split("\\^");
		//헤더 행 수
		int hdRow = Integer.parseInt( (String)request.getAttribute( "hdRow"));

//		DataMap rMap = fileMngService.decryptDrmFile( upload_excel_path, dec_excel_path);
//		log.debug("복호화 결과 ====> " + rMap.getString( "result"));

//		if ( "true".equals( rMap.getString( "result"))) {
//			FileInputStream fis = new FileInputStream( new File( dec_excel_path));
//			// Workbook 형태의 데이터로 바꿈
//			HSSFWorkbook wb = new HSSFWorkbook(fis);
//            HSSFSheet sheet = wb.getSheetAt(0); 	// 시트 가져오기
//            int rows = sheet.getPhysicalNumberOfRows(); // 행 갯수 가져오기
//
//            for ( int i=hdRow; i<rows; i++) { // 하나의 Row 가져오기
//                HSSFRow row = sheet.getRow( i);
//                if ( row != null) {
//                    int cells = row.getPhysicalNumberOfCells(); //cell 갯수 가져오기
//                    log.debug("row: " + row.getRowNum()+", cells=> " + cells);
//                    log.debug("row: " + row.getRowNum()+", colNms.length=> " + colNms.length);
//                    if ( cells == 0) break;
//
//                    if ( cells != colNms.length) {
//            			throw new Exception( "DB컬럼 갯수와 엑셀컬럼 갯수가 다릅니다.");
//            		}
//
//                    DataMap param = new DataMap();
//                    for ( int c=0; c<cells; c++){ // cell 루프
//                        HSSFCell cell = row.getCell(c); //cell 가져오기
//                        String value = "";
//                        if ( cell != null) {
//                            switch ( cell.getCellType()){ //cell 타입에 따른 데이타 저장
//                                case HSSFCell.CELL_TYPE_FORMULA:
//                                    value = cell.getCellFormula();
//                                    break;
//                                case HSSFCell.CELL_TYPE_NUMERIC:
//                                    value = "" + (int)cell.getNumericCellValue();
//                                    break;
//                                case HSSFCell.CELL_TYPE_STRING:
//                                    value = "" + cell.getStringCellValue();
//                                    break;
//                                case HSSFCell.CELL_TYPE_BLANK:
//                                    value = "" + cell.getBooleanCellValue();
//                                    break;
//                                case HSSFCell.CELL_TYPE_ERROR:
//                                    value = "" + cell.getErrorCellValue();
//                                    break;
//                                default:
//                            }
//                        }
//                        param.put( colNms[c], value);
//                    }
//                    resList.add( param);
//                }
//            }
//		}else{
			FileInputStream fis = new FileInputStream( new File( upload_excel_path));
			// Workbook 형태의 데이터로 바꿈
			HSSFWorkbook wb = new HSSFWorkbook(fis);
            HSSFSheet sheet = wb.getSheetAt(0); 	// 시트 가져오기
            int rows = sheet.getPhysicalNumberOfRows(); // 행 갯수 가져오기

            for ( int i=hdRow; i<rows; i++) { // 하나의 Row 가져오기
                HSSFRow row = sheet.getRow( i);
                if ( row != null) {
                    int cells = row.getPhysicalNumberOfCells(); //cell 갯수 가져오기
                    log.debug("row: " + row.getRowNum()+", cells=> " + cells);
                    log.debug("row: " + row.getRowNum()+", colNms.length=> " + colNms.length);
                    if ( cells == 0) break;

                    if ( cells != colNms.length) {
            			throw new AlertException( "DB컬럼 갯수와 엑셀컬럼 갯수가 다릅니다.");
            		}

                    DataMap param = new DataMap();
                    for ( int c=0; c<cells; c++){ // cell 루프
                        HSSFCell cell = row.getCell(c); //cell 가져오기
                        String value = "";
                        if ( cell != null) {
                            switch ( cell.getCellType()){ //cell 타입에 따른 데이타 저장
                                case HSSFCell.CELL_TYPE_FORMULA:
                                    value = cell.getCellFormula();
                                    break;
                                case HSSFCell.CELL_TYPE_NUMERIC:
                                    value = "" + (int)cell.getNumericCellValue();
                                    break;
                                case HSSFCell.CELL_TYPE_STRING:
                                    value = "" + cell.getStringCellValue();
                                    break;
                                case HSSFCell.CELL_TYPE_BLANK:
                                    value = "" + cell.getBooleanCellValue();
                                    break;
                                case HSSFCell.CELL_TYPE_ERROR:
                                    value = "" + cell.getErrorCellValue();
                                    break;
                                default:
                            }
                        }
                        param.put( colNms[c], value);
                    }
                    resList.add( param);
                }
            }
//		}

		log.debug( "===upload_excel_path===>>"+upload_excel_path);
//		log.debug( "===dec_excel_path===>>"+dec_excel_path);
		if( upload_excel_path != null && new File( upload_excel_path).exists()){
			//원본 업로드 엑셀파일 삭제
			try {
				new File( upload_excel_path).delete();
			} catch( Exception e){
				logger.debug(e.getMessage());
			}
		}

//		if( dec_excel_path != null && new File( dec_excel_path).exists()){
//			//복호화 엑셀파일 삭제
//			try { new File( dec_excel_path).delete(); } catch( Exception e){}
//		}

		}catch(Exception e){
			logger.error("readUploadExcelFile 함수 실행 중 에러", e);
        	throw e;
		}

		return resList;
	}
}
