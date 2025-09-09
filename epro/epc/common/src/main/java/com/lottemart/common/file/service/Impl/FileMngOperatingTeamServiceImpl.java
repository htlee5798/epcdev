package com.lottemart.common.file.service.Impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasoo.fcwpkg.packager.WorkPackager;
import com.lottemart.common.exception.AlertException;
import com.lottemart.common.file.service.FileMngForOperatingTeamService;
import com.lottemart.common.file.util.FileMngUtil;
import com.lottemart.common.mail.service.impl.LteMailServiceImpl;
import com.lottemart.common.util.DataMap;

import MarkAny.MaSaferJava.Madec;
import lcn.module.common.util.DateUtil;
import lcn.module.framework.base.AbstractServiceImpl;
import lcn.module.framework.property.ConfigurationService;

@Service("FileMngOperatingTeamServiceImpl")
public class FileMngOperatingTeamServiceImpl extends AbstractServiceImpl implements
FileMngForOperatingTeamService {
	
	@Autowired
	private ConfigurationService config;
	
	@Resource(name="FileMngUtil")
	private FileMngUtil fileMngUtil;

	final static Logger logger = LoggerFactory.getLogger(FileMngOperatingTeamServiceImpl.class);
	
	public DataMap fileUploadAndDecrypt(HttpServletRequest request) throws Exception {
		DataMap rtn = new DataMap();
		String up_file_path = "";		//업로드 DRM파일 위치
		String up_file_name = "";		//업로드 DRM파일명
		String origin_file_name = "";		//업로드 DRM파일 위치
		String decrpt_file_path = "";	//업로드 DRM해제파일 위치
		String decrpt_file_name = "";	//업로드 DRM해제파일명

		DataMap uploadMap = upload(request);
		
		if (uploadMap.getString("success").equals("false")) {
			throw new AlertException(uploadMap.getString("message"));
		}
		origin_file_name = uploadMap.getString("orginFileName");
		up_file_path = uploadMap.getString("uploadFilepath");
		up_file_name  = uploadMap.getString("uploadFileName");

		decrpt_file_path = config.getString("bos.drm.dec.file.path") ;
		decrpt_file_name = DateUtil.getCurrentDateTimeAsString() + "_" + origin_file_name;
		

		File uploadFile = new File(up_file_path + File.separator + up_file_name);
		File decFile = new File(decrpt_file_path + File.separator + decrpt_file_name);
		
		if (uploadFile.length() == 0 ) {
			throw new AlertException("원본 파일이 손상되었습니다.");
		}
		log.debug("■■■■■■■■■■■■■■■■■■■■ up_file_path : " + up_file_path);
		log.debug("■■■■■■■■■■■■■■■■■■■■ up_file_name : " + up_file_name);
		
		DataMap rMap = decryptDrmFile(up_file_path, up_file_name, decrpt_file_path, decrpt_file_name);
		log.debug("복호화 결과 ====> " + rMap.getString("result"));
		
		if("true".equals(rMap.get("result"))){
			rtn.put("result", "true");
			rtn.put("up_file_path", up_file_path);
			rtn.put("up_file_name", up_file_name);
			rtn.put("up_file_full_path", up_file_path + File.separator + up_file_name);
			rtn.put("decrpt_file_path", decrpt_file_path);
			rtn.put("decrpt_file_name", decrpt_file_name);
			rtn.put("decrpt_file_full_path", decrpt_file_path + File.separator + decrpt_file_name);
		}else{
			rtn.put("result", "false");
			rtn.put("message", "복호화 실패");
			
		}
		return rtn;
	}
	
	private DataMap upload(HttpServletRequest request) {
		
		String success = "false";
		String message = null;
		MultipartHttpServletRequest mptRequest = null;		
		FileOutputStream outputStream = null;
		boolean runtimeErr = false;
		String uploadFilepath = null;
		String uploadFileName = null;
		String orginFileName = "";
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
				
				if ( fileUploadCnt > 0 ) {
					try {
						String uploadDir = config.getString("bos.drm.file.path");				
						File dirPath = new File(uploadDir);
						if (!dirPath.exists()) {
					        dirPath.mkdirs();
					    }					
						uploadFilepath = uploadDir;
					} catch (Exception e) {
						runtimeErr = true;
						throw new AlertException("업로드 관련 환경 설정시 문제가 발생했습니다.");
					}
				}
				
				while (iter.hasNext()) {
				    entry = iter.next();

				    file = entry.getValue();
				    orginFileName = file.getOriginalFilename();
				    uploadFileName = DateUtil.getCurrentDateTimeAsString() + "_" + orginFileName ;
			    	outputStream = new FileOutputStream(uploadFilepath + File.separator + uploadFileName);						
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
			else message = "fail";
		} finally {
			if (outputStream != null) {
				try { outputStream.close(); } catch (Exception e) {
					log.error("error --> " + e.getMessage());
				}
			}
			uploadMap.put("success", success);
			uploadMap.put("message", message);
			uploadMap.put("orginFileName", orginFileName);
			uploadMap.put("uploadFilepath", uploadFilepath);
			uploadMap.put("uploadFileName", uploadFileName);
		}
		return uploadMap;
	}
	
	@Override
	public DataMap decryptDrmFile(String strEncFilePath, String strEncFileName, String strDecFilePath, String strDecFileName) throws Exception {
		log.debug("■■■■■■■■■■■■■■■■■■■■ decryptDrmFile 시작");
		
		String strEncFullPath = strEncFilePath + File.separator + strEncFileName;
		
		DataMap dataMap = new DataMap();  
		File fileEncFile = new File(strEncFullPath);
		
		long longEncFileLength = fileEncFile.length();
		if (longEncFileLength == 0) {  
			dataMap.put("result","false");
			dataMap.put("message", "원본 파일이 손상되었거나 존재하지 않습니다. sourcePath : " + strEncFullPath);		
			log.error("원본 파일이 손상되었거나 존재하지 않습니다. sourcePath : " + strEncFullPath);
			return dataMap;
		}
		
		String strConfigDecFilePath = config.getString("bos.drm.dec.file.path");			
		File f = new File(strConfigDecFilePath);
		if (!f.exists()) {
	        f.mkdirs();
	    }		
		
		boolean bret = false;
		boolean iret = false; 
		int retVal = 0;  // 파일타입 검사결과 
		
		String strFsdinitPath = config.getString("bos.drm.key.path"); 
		String strCPID = config.getString("bos.drm.key.keyId");
		String strDecFullPath = strDecFilePath + File.separator + strDecFileName;
		
		WorkPackager objWorkPackager = new WorkPackager();

		log.debug("■■■■■■■■■■■■■■■■■■■■ strSourceFullPath =====>> " + strEncFullPath);
		log.debug("■■■■■■■■■■■■■■■■■■■■ strTargetFullPath =====>> " + strDecFullPath);
		retVal = objWorkPackager.GetFileType(strEncFullPath);
		log.debug("■■■■■■■■■■■■■■■■■■■■ 파일타입 검사결과 =====>> " + retVal);
		
		if (retVal == 101) {
			dataMap = decryptMarkAnyDrmFile(strEncFullPath, strDecFullPath);
			
			return dataMap;
		} 

//		@4UP 수정 Fasoo 권고에 의해 IsSupportFile 는 사용하지 않음
//		iret = objWorkPackager.IsSupportFile(strFsdinitPath, strCPID, strEncFullPath);
//		log.debug("■■■■■■■■■■■■■■■■■■■■ 파일 지원 유무 =====>> " + iret);
		
		if (retVal == 103) {
			log.debug("■■■■■■■■■■■■■■■■■■■■ FASOO DRM decrypt 시작");
			bret = objWorkPackager.DoExtract(
									strFsdinitPath,
									strCPID, 
									strEncFullPath,
									strDecFullPath
									);
			
			log.debug("■■■■■■■■■■■■■■■■■■■■ 복호화 결과값 =====>> " + bret);
			log.debug("■■■■■■■■■■■■■■■■■■■■ strFsdinitPath =====>> " + strFsdinitPath);
			log.debug("■■■■■■■■■■■■■■■■■■■■ strCPID =====>> " + strCPID);
			log.debug("■■■■■■■■■■■■■■■■■■■■ strEncFilePath =====>> " + strEncFullPath);
			log.debug("■■■■■■■■■■■■■■■■■■■■ strDecFilePath =====>> " + strDecFullPath);
			log.debug("■■■■■■■■■■■■■■■■■■■■ 복호화 문서 =====>> " + objWorkPackager.getContainerFilePathName());
			log.debug("■■■■■■■■■■■■■■■■■■■■ 오류코드 =====>> " + objWorkPackager.getLastErrorNum());
			log.debug("■■■■■■■■■■■■■■■■■■■■ 오류값 =====>> " + objWorkPackager.getLastErrorStr());			
			
			if(bret == true){
				dataMap.put("message", "success");
				dataMap.put("result", "true");
			}else{
				dataMap.put("message", "ERR [ErrorCode] " + objWorkPackager.getLastErrorNum());
				dataMap.put("result", "false");
				log.error("ERR [ErrorCode] " + objWorkPackager.getLastErrorNum());
			}				
		}
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
	
	private DataMap decryptMarkAnyDrmFile(String strEncFullPath, String strDecFullPath) throws Exception {
		log.debug("■■■■■■■■■■■■■■■■■■■■ MarkAny DRM 시작 ■■■■■■■■■■■■■■■■■■■■");
		
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		DataMap dataMap = new DataMap();  
		Madec clMadec = null;
		try {
			File fileEnc = new File(strEncFullPath);
			File fileDec = new File(strDecFullPath);
			
			long sourceFileLength = fileEnc.length();
			if (sourceFileLength == 0) {  
				dataMap.put("result","false");
				dataMap.put("message", "원본 파일이 손상되었거나 존재하지 않습니다. sourcePath : " + strEncFullPath);		
				log.error("원본 파일이 손상되었거나 존재하지 않습니다. sourcePath : " + strEncFullPath);
				return dataMap;
			}
			
			in = new BufferedInputStream(new FileInputStream(fileEnc));
			out = new BufferedOutputStream(new FileOutputStream(fileDec));
			
			clMadec = new Madec(config.getString("markany.drm.dat.path"));
			log.debug("■■■■■■■■■■■■■■■■■■■■ markany.drm.dat.path ===> " + config.getString("markany.drm.dat.path"));
						
			long targetFileLength = clMadec.lGetDecryptFileSize(strEncFullPath, sourceFileLength, in);			
			log.debug("MarkAny Soruce File : " + fileEnc.getName() + " : " + sourceFileLength);
			log.debug("MarkAny Target File : " + fileDec.getName() + " : " + targetFileLength);
			if (targetFileLength > 0) {
				String strRetCode = clMadec.strMadec(out);
				dataMap.put("message", strRetCode);
				dataMap.put("result", "true");	
			}else{
				dataMap.put("result","false");
				dataMap.put("message", "drm 파일이 아닐수 있습니다 sourcePath : " + strEncFullPath);		
				log.error("drm 파일이 아닐수 있습니다 : " + strEncFullPath);
			}
			log.debug("■■■■■■■■■■■■■■■■■■■■ MarkAny DRM decrypt complete !!!");
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
		}

		return dataMap;
	}
}