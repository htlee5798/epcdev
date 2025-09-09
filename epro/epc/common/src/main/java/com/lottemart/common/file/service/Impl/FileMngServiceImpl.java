package com.lottemart.common.file.service.Impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

import com.fasoo.fcwpkg.packager.WorkPackager;
import com.lottemart.common.exception.AlertException;
import com.lottemart.common.file.dao.FileManageDAO;
import com.lottemart.common.file.model.FileVO;
import com.lottemart.common.file.service.FileMngService;
import com.lottemart.common.file.util.FileMngUtil;
import com.lottemart.common.util.DataMap;

import MarkAny.MaSaferJava.Madec;
import lcn.module.common.util.DateUtil;
import lcn.module.framework.base.AbstractServiceImpl;
import lcn.module.framework.property.ConfigurationService;

/**
 * @Class Name : FileMngService.java
 * @Description : 파일정보의 관리를 위한 서비스 인터페이스
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
@Service("FileMngService")
public class FileMngServiceImpl extends AbstractServiceImpl implements
		FileMngService {
	
	@Autowired
	private ConfigurationService config;
	
	@Resource(name="FileMngUtil")
	private FileMngUtil fileMngUtil;
	
	@Resource(name = "FileManageDAO")
	private FileManageDAO fileMngDAO;

	final static Logger logger = LoggerFactory.getLogger(FileMngServiceImpl.class);

	/**
	 * 여러 개의 파일을 삭제한다.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void deleteFileInfs(List fvoList) throws Exception {
		fileMngDAO.deleteFileInfs(fvoList);
	}

	/**
	 * 하나의 파일에 대한 정보(속성 및 상세)를 등록한다.
	 * 
	 */
	public String insertFileInf(FileVO fvo) throws Exception {
		String atchFileId = fvo.getAtchFileId();

		fileMngDAO.insertFileInf(fvo);

		return atchFileId;
	}

	/**
	 * 여러 개의 파일에 대한 정보(속성 및 상세)를 등록한다.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String insertFileInfs(List fvoList) throws Exception {
		StringBuffer atchFileId = new StringBuffer();

		if (fvoList.size() != 0) {
			FileVO vo = (FileVO) fvoList.get(0);
			
			atchFileId.append(fileMngDAO.insertFileMaster(vo));
			
			Iterator it = fvoList.iterator();
			while(it.hasNext()) {
				vo = (FileVO) it.next();
				fileMngDAO.insertFileDetail(vo);
			}
			
		}

		return atchFileId.toString();
	}

	/**
	 * 파일에 대한 목록을 조회한다.
	 * 
	 */
	public List<FileVO> selectFileInfs(FileVO fvo) throws Exception {
		return fileMngDAO.selectFileInfs(fvo);
	}

	/**
	 * 여러 개의 파일에 대한 정보(속성 및 상세)를 수정한다.
	 * @deprecated
	 */
	@SuppressWarnings("unchecked")
	public void updateFileInfs(List fvoList) throws Exception {
		// Delete & Insert
		fileMngDAO.updateFileInfs(fvoList);
	}

	/**
	 * 하나의 파일을 삭제한다.
	 * 
	 */
	public void deleteFileInf(FileVO fvo) throws Exception {
		fileMngDAO.deleteFileInf(fvo);
	}

	/**
	 * 파일에 대한 상세정보를 조회한다.
	 * 
	 */
	public FileVO selectFileInf(FileVO fvo) throws Exception {
		return fileMngDAO.selectFileInf(fvo);
	}

	/**
	 * 파일 구분자에 대한 최대값을 구한다.
	 * 
	 */
	public int getMaxFileSN(FileVO fvo) throws Exception {
		return fileMngDAO.getMaxFileSN(fvo);
	}

	/**
	 * 전체 파일을 삭제한다.
	 * 
	 */
	public void deleteAllFileInf(FileVO fvo) throws Exception {
		fileMngDAO.deleteAllFileInf(fvo);
	}

	/**
	 * 파일명 검색에 대한 목록을 조회한다.
	 * 
	 */
	public Map<String, Object> selectFileListByFileNm(FileVO fvo)
			throws Exception {
		List<FileVO> result = fileMngDAO.selectFileListByFileNm(fvo);
		int cnt = fileMngDAO.selectFileListCntByFileNm(fvo);

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("resultList", result);
		map.put("resultCnt", Integer.toString(cnt));

		return map;
	}

	/**
	 * 이미지 파일에 대한 목록을 조회한다.
	 * 
	 */
	public List<FileVO> selectImageFileList(FileVO vo) throws Exception {
		return fileMngDAO.selectImageFileList(vo);
	}

	@Override
	public List<FileVO> selectFileList(FileVO vo) throws Exception {
		// TODO Auto-generated method stub
		return fileMngDAO.selectFileList(vo);
	}

	/**
	 * 동일 아이디를 갖는 파일들을 추가 한다.
	 * 
	 */
	@Override
	public void insertAppendFileInfs(List<FileVO> fvoList) throws Exception {
		// TODO Auto-generated method stub

		FileVO vo;
		Iterator iter = fvoList.iterator();
		while (iter.hasNext()) {
			vo = (FileVO) iter.next();

			fileMngDAO.insertAppendFileInf(vo);
		}
	}

	/**
	 * 동일 아이디를 갖는 파일을 추가 한다.
	 * 
	 */
	@Override
	public void insertAppendFileInfs(FileVO fvo) throws Exception {
		// TODO Auto-generated method stub
		fileMngDAO.insertAppendFileInf(fvo);
	}

	/** (non-Javadoc)
	 	 * @see com.lottemart.common.file.service.FileMngService#deleteRealFileInf(com.lottemart.common.file.model.FileVO, java.lang.String)
		 * @Method Name  : FileMngServiceImpl.java
		 * @since      : 2011
		 * @author     : sunghoon
		 * @version    :
		 * @Locaton    : com.lottemart.common.file.service.Impl
		 * @Description : 실제 파일도 함께 삭제한다.(파일 정보 중 atchFileId 또는  atchFileId ,fileSn를 필요로 함.)
	     * @param vo
	     * @param path
	     * @return
	     * @throws Exception
	 */
	@Override
	public int deleteRealFileInf(FileVO vo, String path) throws Exception {
		// TODO Auto-generated method stub
		
		List<FileVO> vinfs = this.selectFileList(vo);  
		
		Iterator it = vinfs.iterator();
		while(it.hasNext()) {
			FileVO info = (FileVO) it.next();
			
			this.deleteFileInf(info);
			
			fileMngUtil.deleteRealFile(info, path);
		}
		
		return 0;
	}

	/** (non-Javadoc)
	 * @see com.lottemart.common.file.service.FileMngService#deleteNFileSN(com.lottemart.common.file.model.FileVO)
	 * @Location : com.lottemart.common.file.service.Impl
	 * @Method Name  : deleteNFileSN
	 * @author     : sunghoon
	 * @Description : 파일 전체 삯제시 TB_NFile 사용 정보 N로 변경 
	 * @param vo
	 * @return
	 * @throws Exception
	*/
	@Override
	public int deleteNFileSN(FileVO vo) throws Exception {
		// TODO Auto-generated method stub
		
		return fileMngDAO.deleteNFileSN(vo);
	}

    /**
     * Desc : DRM File 을 복화화
     * @Method Name : decryptDrmFile
     * @param sourcePath DRM 대상 파일
     * @param targetPath DRM 복호화 저장 파일
     * @return 복호화 성공 여부
     * @throws Exception
     * @param 
     * @return 
     * @exception Exception
     */
	@Override
	public DataMap decryptDrmFile(String sourcePath, String targetPath) throws Exception {
		log.debug("DRM decrypt start !!!");
		
		DataMap dataMap = new DataMap();  
		File soruceFile = new File(sourcePath); //업로드 된 파일로 DRM해제 작업
		long sourceFileLength = soruceFile.length();
		if (sourceFileLength == 0) {  
			dataMap.put("result","false");
			dataMap.put("message", "원본 파일이 손상되었거나 존재하지 않습니다. sourcePath : " + sourcePath);		
			log.error("원본 파일이 손상되었거나 존재하지 않습니다. sourcePath : " + sourcePath);
			return dataMap;
		}
		
		//dec 폴더 생성
		String downloadDir = config.getString("bos.drm.dec.file.path"); //DRM해제 후 파일 경로			
		File dirPath = new File(downloadDir);
		if (!dirPath.exists()) {
	        dirPath.mkdirs();
	    }		
		
		boolean bret = false;
		boolean iret = false; 
		int retVal = 0;  // 파일타입 검사결과 
		
		//DRM Config Information
		//String strFsdinitPath = "C:/Project-lottemart/fsdinit";
		String strFsdinitPath = config.getString("bos.drm.key.path"); 
		String strCPID = config.getString("bos.drm.key.keyId");
		String strEncFilePath = sourcePath;
		String strDecFilePath = targetPath;
		
		WorkPackager objWorkPackager = new WorkPackager();
		
		retVal = objWorkPackager.GetFileType(strEncFilePath);
		log.debug("fasoo test 1 : " + retVal);
		
		/*
		대상 문서가 암호화 되었을 때만 복호화 실행
		평문파일일 경우 복호화 과정 필요없이 서버에 업로드 처리하면 됨
		마크애니 파일일 경우 복호화 불가능
		*/
		
		//MarkAny 인 경우 (2020-10-22 markAny,fasoo 둘다 사용함으로  추가 됨.) markany 사용 안할 경우 삭제 할 것 =======
		if (retVal == 101) {
			dataMap = decryptMarkAnyDrmFile(sourcePath, targetPath);
			
			return dataMap;
		} 
		// ===================================================================
		
//		@4UP 수정 Fasoo 권고에 의해 IsSupportFile 는 사용하지 않음
//		//파일 확장자 체크( IsSupportFile() ) 로직
//		iret = objWorkPackager.IsSupportFile(strFsdinitPath, strCPID, strEncFilePath);
//		log.debug("fasoo test 2 : " + iret);
		
		//지원 확장자의 경우 복호화 진행
		if (retVal == 103) {
			log.debug("FASOO DRM decrypt start !!!");
			
			// 암호화 된 파일 복호화
			bret = objWorkPackager.DoExtract(
									strFsdinitPath,					//fsdinit 폴더 FullPath 설정
									strCPID,				//고객사 Key(default) 
									strEncFilePath,			//복호화 대상 문서 FullPath + FileName
									strDecFilePath		//복호화 된 문서 FullPath + FileName
									);
			
			log.debug("복호화 결과값 : " + bret);
			log.debug("strDecFilePath : " + strDecFilePath);
			log.debug("복호화 문서 : " + objWorkPackager.getContainerFilePathName());
			log.debug("오류코드 : " + objWorkPackager.getLastErrorNum());
			log.debug("오류값 : " + objWorkPackager.getLastErrorStr());			
			
			if(bret == true){
				dataMap.put("message", "success");
				dataMap.put("result", "true");
			}else{
				dataMap.put("message", "ERR [ErrorCode] " + objWorkPackager.getLastErrorNum());
				dataMap.put("result", "false");
				log.error("ERR [ErrorCode] " + objWorkPackager.getLastErrorNum());
			}				
		}
		/*else if (retVal == 101) { // 2020-10-22 markAny,fasoo 둘다 사용함으로  주석 처리함. markany 사용 안할 경우 주석 풀기
			dataMap.put("result","false");
			dataMap.put("message", "MarkAny 파일은 복호화 불가능 합니다.");
			log.error("MarkAny 파일은 복호화 불가능 합니다.");
		}*/
		else if (retVal == 29) {
			dataMap.put("result","false");
			dataMap.put("message", "평문파일은 복호화 과정 불필요 합니다.");
			log.error("평문파일은 복호화 과정 불필요 합니다.");
		}	
		else {
			dataMap.put("result","false");
			dataMap.put("message", "정상적인 암호화 파일이 아닌경우 복호화 불가능 합니다.["+ retVal + "]");
			log.error("정상적인 암호화 파일이 아닌경우 복호화 불가능 합니다.["+ retVal + "]");
		}

		return dataMap;
	}
	
	private DataMap decryptMarkAnyDrmFile(String sourcePath, String targetPath) throws Exception {
		log.debug("MarkAny DRM decrypt start !!!");
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		DataMap dataMap = new DataMap();  
		Madec clMadec = null;
		FileInputStream fi = null;
		FileOutputStream fo = null;
		try {
			File soruceFile = new File(sourcePath); //업로드 된 파일로 DRM해제 작업
			File targetFile = new File(targetPath); //DRM해제 될 파일 경로
			
			long sourceFileLength = soruceFile.length();
			if (sourceFileLength == 0) {  
				dataMap.put("result","false");
				dataMap.put("message", "원본 파일이 손상되었거나 존재하지 않습니다. sourcePath : " + sourcePath);		
				log.error("원본 파일이 손상되었거나 존재하지 않습니다. sourcePath : " + sourcePath);
				return dataMap;
			}
			
			fi = new FileInputStream(soruceFile);
			fo = new FileOutputStream(targetFile);
			
			in = new BufferedInputStream(fi);
			out = new BufferedOutputStream(fo);
			
			// create instance
			clMadec = new Madec(config.getString("markany.drm.dat.path")); //drm 데이터 파일 읽어들임
			log.debug("markany.drm.dat.path ===> " + config.getString("markany.drm.dat.path"));
						
			long targetFileLength = clMadec.lGetDecryptFileSize(sourcePath, sourceFileLength, in);			
			log.debug("MarkAny Soruce File : " + soruceFile.getName() + " : " + sourceFileLength);
			log.debug("MarkAny Target File : " + targetFile.getName() + " : " + targetFileLength);
			if (targetFileLength > 0) {
				String strRetCode = clMadec.strMadec(out);
				dataMap.put("message", strRetCode);
				dataMap.put("result", "true");	
			}else{
				dataMap.put("result","false");
				dataMap.put("message", "drm 파일이 아닐수 있습니다 sourcePath : " + sourcePath);		
				log.error("drm 파일이 아닐수 있습니다 : " + sourcePath);
			}
			log.debug("MarkAny DRM decrypt complete !!!");
		} catch (Exception e) {
			log.error("decryptDrmFile 함수 실행 중 에러 발생", e);
			String strErrorCode = clMadec.strGetErrorCode();
			log.error("ERR [ErrorCode] " + strErrorCode + " [ErrorDescription] " + clMadec.strGetErrorMessage(strErrorCode));
			
			dataMap.put("result","false");
			dataMap.put("message", "ERR [ErrorCode] " + strErrorCode + " [ErrorDescription] "+ clMadec.strGetErrorMessage(strErrorCode));
		} finally {
			try { if (in != null) in.close(); } catch(IOException e){
				log.error("error --> " + e.getMessage());
			}
			try { if (out != null) out.close(); } catch(IOException e){
				log.error("error --> " + e.getMessage());
			}
			try { if (fi != null) fi.close(); } catch(IOException e){
				log.error("error --> " + e.getMessage());
			}
			try { if (fo != null) fo.close(); } catch(IOException e){
				log.error("error --> " + e.getMessage());
			}
		}

		return dataMap;
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
                    log.debug("uploadFilepath = " + uploadFilepath);

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
			log.error("", e);
			if ( runtimeErr ) message = e.getMessage();
			else message = "fail"; // messageSource.getMessage("msg.dp.common.exec.error");
		} finally {
			if (outputStream != null) {
				try { outputStream.close(); } catch (Exception e) {
					log.error("error --> " + e.getMessage());
				}
			}
			uploadMap.put("success", success);
			uploadMap.put("message", message);
			uploadMap.put("uploadFilepath", uploadFilepath);
			uploadMap.put("uploadedFileName", orginFileName);
		}
		return uploadMap;
	}
	
	/*
	 * 파일 삭제 플래그 없을 경우, 기본적으로 삭제함
	 * @see com.lottemart.common.file.service.FileMngService#readUploadExcelFile(javax.servlet.http.HttpServletRequest, java.lang.String[], int)
	 */
	public List<DataMap> readUploadExcelFile(HttpServletRequest request, String[] colNms, int hdRow) throws Exception {
		
		return readUploadExcelFile(request, colNms, hdRow, 3);
	}

	/**
	 * 엑셀파일 복호화 후 데이터를 List<DataMap>으로 리턴
	 * 엑셀파일 컬럼수와 colNms의 갯수가 맞아야 함.
	 * 엑셀파일은 한개만 가능.
	 * @Method Name : readUploadExcelFile
	 * @param request
	 * @param colNms: 컬럼명 배열
	 * @param hdRow: 헤더 갯수(기본=1)
	 * @param delFlag: 0:삭제안함, 1:원본파일만 삭제, 2:복호화파일만 삭제, 3:모두삭제함.
	 * @return
	 * @throws Exception
	 * @exception Exception
	 */
	public List<DataMap> readUploadExcelFile(HttpServletRequest request, String[] colNms, int hdRow, int delFlag) throws Exception {

		List<DataMap> resList = new ArrayList<DataMap>();
		String upload_excel_path = "";  // DRM해제 전 파일 경로
		String decrpt_excel_path = "";  // DRM해제 후 파일 경로

		DataMap uploadMap = upload(request);
		if (uploadMap.getString("success").equals("false")) {
			throw new AlertException(uploadMap.getString("message"));
		}
		
		upload_excel_path = uploadMap.getString("uploadFilepath");  // 업로드 된 파일 full path
		String file_name  = uploadMap.getString("uploadedFileName"); // 업로드 된 파일 명

		log.error("업로드 파일명 ====> " + file_name);
		log.error("암호화 파일 경로 ====> " + upload_excel_path);

		decrpt_excel_path = config.getString("bos.drm.dec.file.path") + File.separator + DateUtil.getCurrentDateTimeAsString() + "_" + file_name;
		log.error("복호화  파일 경로 ====> " + decrpt_excel_path);

		File orgFile = new File(upload_excel_path); // 업로드 된 파일(풀패스+파일명)
		File decFile = new File(decrpt_excel_path); // 복호화 된 파일(풀패스+파일명)
		if (orgFile.length() == 0 ) {  
			throw new AlertException("원본 파일이 손상되었습니다.");
		}
	
		DataMap rMap = decryptDrmFile(upload_excel_path, decrpt_excel_path);
		log.error("복호화 결과 ====> " + rMap.getString("result"));
		
		if ("true".equals(rMap.getString("result"))) {
			FileInputStream fis = new FileInputStream(new File(decrpt_excel_path));
			 
			// Workbook 형태의 데이터로 바꿈
			HSSFWorkbook wb = new HSSFWorkbook(fis);
            HSSFSheet sheet = wb.getSheetAt(0); 	// 시트 가져오기
            int rows = sheet.getPhysicalNumberOfRows(); // 행 갯수 가져오기
            
            for (int i=hdRow; i<rows; i++) { // 하나의 Row 가져오기
                HSSFRow row = sheet.getRow(i);
                if (row != null) {
                    int cells = row.getPhysicalNumberOfCells(); //cell 갯수 가져오기
//                    System.out.println("row: " + row.getRowNum()+", cells=> " + cells);
                    log.error("row: " + row.getRowNum()+", cells=> " + cells);

                    if (cells == 0) break;

                    if (cells != colNms.length) {  
            			throw new AlertException("DB컬럼 갯수와 엑셀컬럼 갯수가 다릅니다.");
            		}

                    DataMap param = new DataMap();
                    for (int c=0; c<cells; c++){ // cell 루프
                        HSSFCell cell = row.getCell(c); //cell 가져오기
                        String value = "";
                        if (cell != null) {
                            switch (cell.getCellType()){ //cell 타입에 따른 데이타 저장
                                case HSSFCell.CELL_TYPE_FORMULA:
                                    value = cell.getCellFormula();
                                    break;
                                case HSSFCell.CELL_TYPE_NUMERIC:
                                    value = "" + cell.getNumericCellValue();
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
                        param.put(colNms[c], value);
                    }
                    resList.add(param);
                }
            }
            
            // 작업 끝난 후 파일 삭제
            if (delFlag == 1) {
            	orgFile.delete();
            } else if (delFlag == 2) {
            	decFile.delete();
            }  else if (delFlag == 3) {
            	orgFile.delete();
            	decFile.delete();
            }
            if (fis != null) {
				try { fis.close(); } catch (Exception e) {
					log.error("error --> " + e.getMessage());
				}
			}
		}else{

			FileInputStream fis = new FileInputStream(new File(upload_excel_path));
			 
			// Workbook 형태의 데이터로 바꿈
			HSSFWorkbook wb = new HSSFWorkbook(fis);
            HSSFSheet sheet = wb.getSheetAt(0); 	// 시트 가져오기
            int rows = sheet.getPhysicalNumberOfRows(); // 행 갯수 가져오기
            
            for (int i=hdRow; i<rows; i++) { // 하나의 Row 가져오기
                HSSFRow row = sheet.getRow(i);
                if (row != null) {
                    int cells = row.getPhysicalNumberOfCells(); //cell 갯수 가져오기
//                    System.out.println("row: " + row.getRowNum()+", cells=> " + cells);
                    log.error("row: " + row.getRowNum()+", cells=> " + cells);

                    if (cells == 0) break;

                    if (cells != colNms.length) {  
            			throw new AlertException("DB컬럼 갯수와 엑셀컬럼 갯수가 다릅니다.");
            		}

                    DataMap param = new DataMap();
                    for (int c=0; c<cells; c++){ // cell 루프
                        HSSFCell cell = row.getCell(c); //cell 가져오기
                        String value = "";
                        if (cell != null) {
                            switch (cell.getCellType()){ //cell 타입에 따른 데이타 저장
                                case HSSFCell.CELL_TYPE_FORMULA:
                                    value = cell.getCellFormula();
                                    break;
                                case HSSFCell.CELL_TYPE_NUMERIC:
                                    value = "" + cell.getNumericCellValue();
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
                        param.put(colNms[c], value);
                    }
                    resList.add(param);
                }
            }
            
            // 작업 끝난 후 파일 삭제
            if (delFlag == 1) {
            	orgFile.delete();
            } else if (delFlag == 2) {
            	decFile.delete();
            }  else if (delFlag == 3) {
            	orgFile.delete();
            	decFile.delete();
            }

            if (fis != null) {
				try { fis.close(); } catch (Exception e) {
					log.error("error --> " + e.getMessage());
				}
			}
		}
	
		return resList;
	}
	
	/**
	 * 엑셀파일 복호화 후 시트 갯수별 데이터를 List<DataMap>으로 리턴
	 * 엑셀파일은 한개만 가능.
	 * @Method Name : readUploadNullColExcelFile
	 * @param request
	 * @param colNms: 컬럼명 배열
	 * @param hdRow: 헤더 갯수
	 * @param sheetIndex: 시트 인덱스
	 * @return
	 * @throws Exception
	 * @exception Exception
	 */
	public List<DataMap> readUploadNullColExcelFile(HttpServletRequest request, String[] colNms, int hdRow, int sheetIndex) throws Exception {
		
		List<DataMap> resList = new ArrayList<DataMap>();
		String upload_excel_path = "";  // DRM해제 전 파일 경로
//		String decrpt_excel_path = "";  // DRM해제 후 파일 경로

		DataMap uploadMap = upload(request);
		if (uploadMap.getString("success").equals("false")) {
			throw new AlertException(uploadMap.getString("message"));
		}
		
		upload_excel_path = uploadMap.getString("uploadFilepath");  // 업로드 된 파일 full path
		String file_name  = uploadMap.getString("uploadedFileName"); // 업로드 된 파일 명

		log.debug("Null Col 업로드 파일명 ====> " + file_name);
//		log.debug("암호화 파일 경로 ====> " + upload_excel_path);
//
//		decrpt_excel_path = config.getString("bos.drm.dec.file.path") + File.separator + DateUtil.getCurrentDateTimeAsString() + "_" + file_name;
//		log.debug("복호화  파일 경로 ====> " + decrpt_excel_path);
//
		File orgFile = new File(upload_excel_path); // 업로드 된 파일(풀패스+파일명)
//		File decFile = new File(decrpt_excel_path); // 복호화 된 파일(풀패스+파일명)
//		if (orgFile.length() == 0 ) {  
//			throw new Exception("원본 파일이 손상되었습니다.");
//		}
//	
//		DataMap rMap = decryptDrmFile(upload_excel_path, decrpt_excel_path);
//		log.debug("복호화 결과 ====> " + rMap.getString("result"));
//		
//		if ("true".equals(rMap.getString("result"))) {
//			FileInputStream fis = new FileInputStream(new File(decrpt_excel_path));
//			 
//			// Workbook 형태의 데이터로 바꿈
//			HSSFWorkbook wb = new HSSFWorkbook(fis);
//            HSSFSheet sheet = wb.getSheetAt(sheetIndex); 	// 시트 가져오기
//            int rows = sheet.getPhysicalNumberOfRows(); // 행 갯수 가져오기
//            int colChkCnt = 0;
//            
//            for (int i=hdRow; i<rows; i++) { // 하나의 Row 가져오기
//                HSSFRow row = sheet.getRow(i);
//                if (row != null) {
//                    
//                    DataMap param = new DataMap();
//                    for (int c=0; c<colNms.length; c++){ // cell 루프
//                        HSSFCell cell = row.getCell(c); //cell 가져오기
//                        String value = "";
//                        if (cell != null) {
//                            switch (cell.getCellType()){ //cell 타입에 따른 데이타 저장
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
//                        
//                        if("false".equals(value)){
//                        	value = "";	
//                        }
//                        
//                        if(value.trim() == ""){
//                        	colChkCnt++;
//                        }
//                        
//                        param.put(colNms[c], value);
//                    }
//                    
//                    if(colChkCnt < colNms.length){
//                    	resList.add(param);
//                    }
//                    
//                    colChkCnt = 0;
//                }
//            }
//           
//        	orgFile.delete();
//        	decFile.delete();
//       
//		}else{
			FileInputStream fis = new FileInputStream(new File(upload_excel_path));
			 
			// Workbook 형태의 데이터로 바꿈
			HSSFWorkbook wb = new HSSFWorkbook(fis);
            HSSFSheet sheet = wb.getSheetAt(sheetIndex); 	// 시트 가져오기
            int rows = sheet.getPhysicalNumberOfRows(); // 행 갯수 가져오기
            int colChkCnt = 0;
            
            for (int i=hdRow; i<rows; i++) { // 하나의 Row 가져오기
                HSSFRow row = sheet.getRow(i);
                if (row != null) {
                    
                    DataMap param = new DataMap();
                    for (int c=0; c<colNms.length; c++){ // cell 루프
                        HSSFCell cell = row.getCell(c); //cell 가져오기
                        String value = "";
                        if (cell != null) {
                            switch (cell.getCellType()){ //cell 타입에 따른 데이타 저장
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
                        
                        if("false".equals(value)){
                        	value = "";	
                        }
                        
                        if(value.trim() == ""){
                        	colChkCnt++;
                        }
                        
                        param.put(colNms[c], value);
                    }
                    
                    if(colChkCnt < colNms.length){
                    	resList.add(param);
                    }
                    
                    colChkCnt = 0;
                }
            }
           
        	orgFile.delete();
//        	decFile.delete();
        	

            if (fis != null) {
				try { fis.close(); } catch (Exception e) {
					log.error("error --> " + e.getMessage());
				}
			}
//		}
	
		return resList;
	}
	
	/**
	 * 엑셀파일 복호화 후 데이터를 List<DataMap>으로 리턴
	 * 엑셀파일 컬럼 필수값 체크 나머지 컬럼 null 허용
	 * 엑셀파일은 한개만 가능.
	 * @Method Name : readUploadExcelFileRequirement
	 * @param request
	 * @param colNms: 컬럼명 배열
	 * @param hdRow: 헤더 갯수(기본=1)
	 * @param delFlag: 0:삭제안함, 1:원본파일만 삭제, 2:복호화파일만 삭제, 3:모두삭제함.
	 * @return
	 * @throws Exception
	 * @exception Exception
	 */
	public List<DataMap> readUploadExcelFileRequirement(HttpServletRequest request, String[] colNms, String[] reqCol,int hdRow, int delFlag) throws Exception {

		List<DataMap> resList = new ArrayList<DataMap>();
		String upload_excel_path = "";  // DRM해제 전 파일 경로
		String decrpt_excel_path = "";  // DRM해제 후 파일 경로

		DataMap uploadMap = upload(request);
		if (uploadMap.getString("success").equals("false")) {
			throw new AlertException(uploadMap.getString("message"));
		}
		
		upload_excel_path = uploadMap.getString("uploadFilepath");  // 업로드 된 파일 full path
		String file_name  = uploadMap.getString("uploadedFileName"); // 업로드 된 파일 명

		log.debug("업로드 파일명 ====> " + file_name);
		log.debug("암호화 파일 경로 ====> " + upload_excel_path);

		decrpt_excel_path = config.getString("bos.drm.dec.file.path") + File.separator + DateUtil.getCurrentDateTimeAsString() + "_" + file_name;
		log.debug("복호화  파일 경로 ====> " + decrpt_excel_path);

		File orgFile = new File(upload_excel_path); // 업로드 된 파일(풀패스+파일명)
		File decFile = new File(decrpt_excel_path); // 복호화 된 파일(풀패스+파일명)
		if (orgFile.length() == 0 ) {  
			throw new AlertException("원본 파일이 손상되었습니다.");
		}
	
		DataMap rMap = decryptDrmFile(upload_excel_path, decrpt_excel_path);
		log.debug("복호화 결과 ====> " + rMap.getString("result"));
		
		if ("true".equals(rMap.getString("result"))) {
			FileInputStream fis = new FileInputStream(new File(decrpt_excel_path));
			 
			// Workbook 형태의 데이터로 바꿈
			HSSFWorkbook wb = new HSSFWorkbook(fis);
            HSSFSheet sheet = wb.getSheetAt(0); 	// 시트 가져오기
            int rows = sheet.getPhysicalNumberOfRows(); // 행 갯수 가져오기
            
            for (int i=hdRow; i<rows; i++) { // 하나의 Row 가져오기
                HSSFRow row = sheet.getRow(i);
                if (row != null) {
                    int cells = row.getPhysicalNumberOfCells(); //cell 갯수 가져오기
                    log.debug("row: " + row.getRowNum()+", cells=> " + cells);

                    if (cells == 0) break;

                    DataMap param = new DataMap();
                    for (int c=0; c<colNms.length; c++){ // cell 루프
                        HSSFCell cell = row.getCell(c); //cell 가져오기
                        String value = "";
                        if (cell != null) {
                            switch (cell.getCellType()){ //cell 타입에 따른 데이타 저장
                                case HSSFCell.CELL_TYPE_FORMULA:
                                    value = cell.getCellFormula();
                                    break;
                                case HSSFCell.CELL_TYPE_NUMERIC:
                                    value = "" + cell.getNumericCellValue();
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
                        // 필수 컬럼 체크
                        for(int r=0; r<reqCol.length; r++){
                        	if(colNms[c]!=null && reqCol[r] !=null && colNms[c].equals(reqCol[r])){
                        		if(cell==null || "".equals(value) || value.equals("false")){
                        			throw new AlertException(i+"row에 필수 컬럼값이 없습니다.(컬럼명:"+reqCol[r]+")");
                        		}
                        	}
                        }
                        
                        param.put(colNms[c], value);
                    }
                    resList.add(param);
                }
            }
            
            // 작업 끝난 후 파일 삭제
            if (delFlag == 1) {
            	orgFile.delete();
            } else if (delFlag == 2) {
            	decFile.delete();
            }  else if (delFlag == 3) {
            	orgFile.delete();
            	decFile.delete();
            }
            

            if (fis != null) {
				try { fis.close(); } catch (Exception e) {
					log.error("error --> " + e.getMessage());
				}
			}
		}else{

			FileInputStream fis = new FileInputStream(new File(upload_excel_path));
			 
			// Workbook 형태의 데이터로 바꿈
			HSSFWorkbook wb = new HSSFWorkbook(fis);
            HSSFSheet sheet = wb.getSheetAt(0); 	// 시트 가져오기
            int rows = sheet.getPhysicalNumberOfRows(); // 행 갯수 가져오기
            
            for (int i=hdRow; i<rows; i++) { // 하나의 Row 가져오기
                HSSFRow row = sheet.getRow(i);
                if (row != null) {
                    int cells = row.getPhysicalNumberOfCells(); //cell 갯수 가져오기
//                    System.out.println("row: " + row.getRowNum()+", cells=> " + cells);
                    log.debug("row: " + row.getRowNum()+", cells=> " + cells);

                    if (cells == 0) break;

                    DataMap param = new DataMap();
                    for (int c=0; c<colNms.length; c++){ // cell 루프
                        HSSFCell cell = row.getCell(c); //cell 가져오기
                        String value = "";
                        if (cell != null) {
                            switch (cell.getCellType()){ //cell 타입에 따른 데이타 저장
                                case HSSFCell.CELL_TYPE_FORMULA:
                                    value = cell.getCellFormula();
                                    break;
                                case HSSFCell.CELL_TYPE_NUMERIC:
                                    value = "" + cell.getNumericCellValue();
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
                        // 필수 컬럼 체크
                        for(int r=0; r<reqCol.length; r++){
                        	if(colNms[c]!=null && reqCol[r] !=null && colNms[c].equals(reqCol[r])){
                        		if(cell==null || "".equals(value) || value.equals("false")){
                        			throw new AlertException(i+"row에 필수 컬럼값이 없습니다.(컬럼명:"+reqCol[r]+")");
                        		}
                        	}
                        }
                        
                        param.put(colNms[c], value);
                    }
                    resList.add(param);
                }
            }
            
            // 작업 끝난 후 파일 삭제
            if (delFlag == 1) {
            	orgFile.delete();
            } else if (delFlag == 2) {
            	decFile.delete();
            }  else if (delFlag == 3) {
            	orgFile.delete();
            	decFile.delete();
            }
            

            if (fis != null) {
				try { fis.close(); } catch (Exception e) {
					log.error("error --> " + e.getMessage());
				}
			}
		}
	
		return resList;
	}
	
	/**
	 * 컬럼이 고정된 값이 아닌경우 사용 2024.04.23 추가 (한정수량구매설정 장표에서 사용 중) 
	 * 엑셀파일 복호화 후 시트 갯수별 데이터를 List<DataMap>으로 리턴
	 * 엑셀파일은 한개만 가능.
	 * @Method Name : readUploadNullColExcelFile
	 * @param request
	 * @param colNms: 기본 고정 컬럼명 배열(필수 컬럼)
	 * @param hdRow: 헤더 갯수
	 * @param sheetIndex: 시트 인덱스
	 * @return
	 * @throws Exception
	 * @exception Exception
	 */
	public List<DataMap> readUploadChangeColExcelFile(HttpServletRequest request, String[] colNms, int hdRow, int sheetIndex) throws Exception {
		
		List<DataMap> resList = new ArrayList<DataMap>();
		String upload_excel_path = "";  // DRM해제 전 파일 경로
		String decrpt_excel_path = "";  // DRM해제 후 파일 경로

		DataMap uploadMap = upload(request);
		if (uploadMap.getString("success").equals("false")) {
			throw new AlertException(uploadMap.getString("message"));
		}
		
		upload_excel_path = uploadMap.getString("uploadFilepath");  // 업로드 된 파일 full path
		String file_name  = uploadMap.getString("uploadedFileName"); // 업로드 된 파일 명

		log.error("업로드 파일명 ====> " + file_name);
		log.error("암호화 파일 경로 ====> " + upload_excel_path);

		decrpt_excel_path = config.getString("bos.drm.dec.file.path") + File.separator + DateUtil.getCurrentDateTimeAsString() + "_" + file_name;
		log.debug("복호화  파일 경로 ====> " + decrpt_excel_path);

		File orgFile = new File(upload_excel_path); // 업로드 된 파일(풀패스+파일명)
		File decFile = new File(decrpt_excel_path); // 복호화 된 파일(풀패스+파일명)
		if (orgFile.length() == 0 ) {  
			throw new Exception("원본 파일이 손상되었습니다.");
		}
	
		DataMap rMap = decryptDrmFile(upload_excel_path, decrpt_excel_path);
		log.debug("복호화 결과 ====> " + rMap.getString("result"));
		
		if ("true".equals(rMap.getString("result"))) {
			FileInputStream fis = new FileInputStream(new File(decrpt_excel_path));
			 
		// 임시 작업 ==========================================================
		//if ("false".equals(rMap.getString("result"))) {
			//FileInputStream fis = new FileInputStream(new File(upload_excel_path));
		// =================================================================
			
			// Workbook 형태의 데이터로 바꿈
			HSSFWorkbook wb = new HSSFWorkbook(fis);
            HSSFSheet sheet = wb.getSheetAt(sheetIndex); 	// 시트 가져오기
            int rows = sheet.getPhysicalNumberOfRows(); // 행 갯수 가져오기
            int colChkCnt = 0;
            
            // 엑셀에 등록된 셀의 수
            int cells = sheet.getRow(0).getPhysicalNumberOfCells();
            String cellNmArr [] = new String[cells];
            
            for (int i=hdRow; i<rows; i++) { // 하나의 Row 가져오기
                HSSFRow row = sheet.getRow(i);
                
                if (row != null) {
                    DataMap param = new DataMap();
                    for (int c=0; c<cells; c++){ // cell 루프
                    	
                    	// 헤더 데이터 가져오기
                        if (i == hdRow) {
                    		HSSFCell cellNm = sheet.getRow(0).getCell(c); //
                         	String cellNmValue = "";
                             
                         	if (cellNm != null) {
                                 switch (cellNm.getCellType()){ //cell 타입에 따른 데이타 저장
                                     case HSSFCell.CELL_TYPE_FORMULA:
                                     	cellNmValue = cellNm.getCellFormula();
                                         break;
                                     case HSSFCell.CELL_TYPE_NUMERIC:
                                     	cellNmValue = "" + (int)cellNm.getNumericCellValue();
                                         break;
                                     case HSSFCell.CELL_TYPE_STRING:
                                     	cellNmValue = "" + cellNm.getStringCellValue();
                                         break;
                                     case HSSFCell.CELL_TYPE_BLANK:
                                     	cellNmValue = "" + cellNm.getBooleanCellValue();
                                         break;
                                     case HSSFCell.CELL_TYPE_ERROR:
                                     	cellNmValue = "" + cellNm.getErrorCellValue();
                                         break;
                                     default:
                                 }
                             }
                         	
                         	cellNmArr[c] = cellNmValue; // 헤더 데이터 담기
                    	}

                    	HSSFCell cell = row.getCell(c); //cell 가져오기
                        String value = "";
                        
                        if (cell != null) {
                            switch (cell.getCellType()){ //cell 타입에 따른 데이타 저장
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
                        
                        if ("false".equals(value)) {
                        	value = "";	
                        }
                        
                        if (value.trim() == "") {
                        	colChkCnt++;
                        }
                        
                        // 파라미터로 넘겨진 고정된 헤더
                        if ( c < colNms.length) {
                        	param.put(colNms[c], value);
                        } else {
                        	// 가변 헤더
                        	param.put(cellNmArr[c], value);
                        }
                    }
                    
                    if (colChkCnt < cells) {
                    	resList.add(param);
                    }
                    
                    colChkCnt = 0;
                }
            }
           
        	orgFile.delete();
        	decFile.delete();
       
		} else {
			FileInputStream fis = new FileInputStream(new File(upload_excel_path));
			 
			// Workbook 형태의 데이터로 바꿈
			HSSFWorkbook wb = new HSSFWorkbook(fis);
            HSSFSheet sheet = wb.getSheetAt(sheetIndex); 	// 시트 가져오기
            int rows = sheet.getPhysicalNumberOfRows(); // 행 갯수 가져오기
            int colChkCnt = 0;
            
            // 엑셀에 등록된 셀의 수
            int cells = sheet.getRow(0).getPhysicalNumberOfCells();
            String cellNmArr [] = new String[cells];
            
            for (int i=hdRow; i<rows; i++) { // 하나의 Row 가져오기
                HSSFRow row = sheet.getRow(i);
                if (row != null) {
                    
                    DataMap param = new DataMap();
                    for (int c=0; c<=cells; c++){ // cell 루프
                        
                    	// 헤더 데이터 가져오기
                        if (i == hdRow) {
                    		HSSFCell cellNm = sheet.getRow(0).getCell(c); //
                         	String cellNmValue = "";
                             
                         	if (cellNm != null) {
                                 switch (cellNm.getCellType()){ //cell 타입에 따른 데이타 저장
                                     case HSSFCell.CELL_TYPE_FORMULA:
                                     	cellNmValue = cellNm.getCellFormula();
                                         break;
                                     case HSSFCell.CELL_TYPE_NUMERIC:
                                     	cellNmValue = "" + (int)cellNm.getNumericCellValue();
                                         break;
                                     case HSSFCell.CELL_TYPE_STRING:
                                     	cellNmValue = "" + cellNm.getStringCellValue();
                                         break;
                                     case HSSFCell.CELL_TYPE_BLANK:
                                     	cellNmValue = "" + cellNm.getBooleanCellValue();
                                         break;
                                     case HSSFCell.CELL_TYPE_ERROR:
                                     	cellNmValue = "" + cellNm.getErrorCellValue();
                                         break;
                                     default:
                                 }
                             }
                         	
                         	cellNmArr[c] = cellNmValue; // 헤더 데이터 담기
                    	}
                        
                    	HSSFCell cell = row.getCell(c); //cell 가져오기
                        String value = "";
                        
                        if (cell != null) {
                            switch (cell.getCellType()){ //cell 타입에 따른 데이타 저장
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
                        
                        if("false".equals(value)){
                        	value = "";	
                        }
                        
                        if(value.trim() == ""){
                        	colChkCnt++;
                        }
                        
                        // 파라미터로 넘겨진 고정된 헤더
                        if ( c < colNms.length) {
                        	param.put(colNms[c], value);
                        } else {
                        	// 가변 헤더
                        	param.put(cellNmArr[c], value);
                        }
                    }
                    
                    if(colChkCnt < cells){
                    	resList.add(param);
                    }
                    
                    colChkCnt = 0;
                }
            }
           
        	orgFile.delete();
        	decFile.delete();
        	

            if (fis != null) {
				try { fis.close(); } catch (Exception e) {
					log.error("error --> " + e.getMessage());
				}
			}
		}
	
		return resList;
	}
}