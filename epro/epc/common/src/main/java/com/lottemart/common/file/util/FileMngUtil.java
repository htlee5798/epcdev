package com.lottemart.common.file.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.lottemart.common.file.model.FileVO;

import lcn.module.common.conts.Globals;
import lcn.module.common.util.StringUtil;
import lcn.module.framework.idgen.IdGnrService;
import lcn.module.framework.property.ConfigurationService;
import lcn.module.framework.property.PropertyService;

/**
 * @Class Name : FileMngUtil.java
 * @Description : 메시지 처리 관련 유틸리티
 * @Modification Information
 */
@Component("FileMngUtil")
public class FileMngUtil {
	/**
	 * Logger for this class
	 */
	final static Logger logger = LoggerFactory.getLogger(FileMngUtil.class);

	public static final int BUFF_SIZE = 2048;

	@Resource(name = "propertiesService")
	protected PropertyService propertyService;

	@Resource(name = "FileIdGnrService")
	private IdGnrService idgenService;
	
	@Autowired
	private ConfigurationService config;
	
	/**
	 * 첨부파일에 대한 목록 정보를 취득한다. (단건)
	 *
	 * @param file       파일
	 * @param atchFileId 파일 아이디 지정
	 * @param fileDiv    파일구분 (이미지, 파일 등)
	 * @param storePath  저장경로
	 * @return
	 * @throws Exception
	 */
	public List<FileVO> parseFileInfSingle(MultipartFile file, String atchFileId, String fileDiv, String storePath) throws Exception {
		return parseFileInfSingle( file, atchFileId, fileDiv, storePath, "" );
	}

	/**
	 * 첨부파일에 대한 목록 정보를 취득한다. (단건)
	 *
	 * @param file       파일
	 * @param atchFileId 파일 아이디 지정
	 * @param fileDiv    파일구분 (이미지, 파일 등)
	 * @param storePath  저장경로
	 * @return
	 * @throws Exception
	 */
	public List<FileVO> parseFileInfSingle(MultipartFile file, String atchFileId, String fileDiv, String storePath, String subpath) throws Exception {
		long posblAtchFileSize = 0;

		// 파일 사이즈 지정
		if (fileDiv != null) {
			posblAtchFileSize = Long.valueOf( config.getString( "fileCheck.atchFile.size" ) );
		}


		String fileStorePath = config.getString( storePath ) + subpath;
		File saveFolder = new File( fileStorePath );

		if (!saveFolder.exists() || saveFolder.isFile()) {
			saveFolder.mkdirs();
		}

		String filePath = "";
		List<FileVO> result = new ArrayList<FileVO>();
		FileVO fileVO;

		String fileSizeError = "N";

		if (file.getSize() > 0) {

			long _size = file.getSize();
			if (posblAtchFileSize < _size) {
				fileSizeError = "Y";
				return result;
			}

			// 파일명이 없는 경우 처리
			String orginFileName = file.getOriginalFilename();
			if ("".equals( orginFileName )) {
				return result;
			}

			// 이미지파일 받아야 하는데 이미지파일이 아닐경우
			int index = orginFileName.lastIndexOf( "." );
			String fileExt = orginFileName.substring( index + 1 );
			String filePyscName = StringUtil.getTimeStamp();

			// 이미지 일경우 확장자를 붙인다.
			if (fileDiv.equals( "image" )) {
				filePyscName += "." + fileExt;
			}

			if (!"".equals( orginFileName )) {
				filePath = fileStorePath + filePyscName;

				// 파일이름 중복체크
				File checkFile = new File( filePath );
				if (checkFile.exists() && checkFile.isFile()) {
					filePyscName = StringUtil.getTimeStamp() + StringUtil.getTimeStamp().substring( 15, 17 ) + "." + fileExt;
					filePath = fileStorePath + filePyscName;
				}

				file.transferTo( new File( filePath ) );
			}

			String atchFileIdString = "";
			if ("".equals( atchFileId ) || atchFileId == null) {
				atchFileIdString = idgenService.getNextStringId();
			} else {
				atchFileIdString = atchFileId;
			}

			fileVO = new FileVO();
			fileVO.setAtchFileId( atchFileIdString );
			fileVO.setOrignlFileNm( orginFileName );
			fileVO.setStreFileNm( filePyscName );
			fileVO.setFileStreCours( fileStorePath );
			fileVO.setFileMg( Long.toString( _size ) );
			fileVO.setFileExtsn( fileExt );
			result.add( fileVO );
		}

		// 파일사이즈 초과시 첨부한 전체파일 삭제
		if ("Y".equals( fileSizeError )) {
			fileVO = null;

			for (int i = 0; i < result.size(); i++) {
				fileVO = result.get( i );

				deleteFile( fileVO.getFileStreCours(), fileVO.getStreFileNm() );
			}
			result = null;
		}

		return result;
	}

	public String getSubpath(String gubun) {
		String subPath = "";
		if ("Y".equals( gubun )) {
			Calendar today = Calendar.getInstance();
			subPath = today.get( Calendar.YEAR ) + "/";

		} else if ("M".equals( gubun )) {
			Calendar today = Calendar.getInstance();
			subPath = today.get( Calendar.YEAR ) + String.format( "%02d", (today.get( Calendar.MONTH ) + 1) ) + "/";
		} else if ("D".equals( gubun )) {
			Calendar today = Calendar.getInstance();
			subPath = today.get( Calendar.YEAR ) + String.format( "%02d", (today.get( Calendar.MONTH ) + 1) ) + String.format( "%02d", today.get( Calendar.DAY_OF_MONTH ) ) + "/";
		}
		return subPath;
	}

	/**
	 * 첨부파일에 대한 목록 정보를 취득한다. (다건)
	 *
	 * @param files      파일 목록
	 * @param atchFileId 파일 아이디 지정
	 * @param fileDiv    파일구분 (이미지, 파일 등)
	 * @param storePath  저장경로
	 * @return
	 * @throws Exception
	 */
	public List<FileVO> parseFileInf(Map<String, MultipartFile> files, String atchFileId, String fileDiv, String storePath) throws Exception {

		long posblAtchFileSize = 0;


		// 파일 사이즈 지정 (이미지/파일)
		if (fileDiv.equals( "image" ) && fileDiv != null) {
			posblAtchFileSize = Long.valueOf( config.getString( "fileCheck.atchFile.size" ) );
		}

		String fileStorePath = config.getString( storePath );
		logger.debug( "파일 저장 디렉토리 >>  " + fileStorePath );

		File saveFolder = new File( fileStorePath );

		if (!saveFolder.exists() || saveFolder.isFile()) {
			saveFolder.mkdirs();
		}

		Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
		MultipartFile file;
		String filePath = "";
		List<FileVO> result = new ArrayList<FileVO>();
		FileVO fileVO;

		String fileSizeError = "N";

		while (itr.hasNext()) {

			Entry<String, MultipartFile> entry = itr.next();
			file = entry.getValue();

			if (file.getSize() > 0) {

				// 파일사이즈 초과  될 경우
				long _size = file.getSize();
				if (posblAtchFileSize < _size) {
					fileSizeError = "Y";
					logger.debug( "파일 사이즈 초과" );
					break;
				}

				logger.debug( "0-3" );

				// 파일명이 없는 경우 처리
				String orginFileName = file.getOriginalFilename();
				if ("".equals( orginFileName )) {
					logger.debug( "파일명 없음 에러 " );
					break;
				}

				// 이미지파일 받아야 하는데 이미지파일이 아닐경우
				int index = orginFileName.lastIndexOf( "." );
				String fileExt = orginFileName.substring( index + 1 );
				String filePyscName = StringUtil.getTimeStamp();


				// 이미지 일경우 확장자를 붙인다.
				if (fileDiv.equals( "image" )) {
					filePyscName += "." + fileExt;
				}

				if (!"".equals( orginFileName )) {
					filePath = fileStorePath + filePyscName;
					file.transferTo( new File( filePath ) );
				}


				String atchFileIdString = "";
				if ("".equals( atchFileId ) || atchFileId == null) {
					atchFileIdString = idgenService.getNextStringId();
				} else {
					atchFileIdString = atchFileId;
				}


				fileVO = new FileVO();
				fileVO.setAtchFileId( atchFileIdString );
				fileVO.setOrignlFileNm( orginFileName );
				fileVO.setStreFileNm( filePyscName );
				fileVO.setFileStreCours( fileStorePath );
				fileVO.setFileMg( Long.toString( _size ) );
				fileVO.setFileExtsn( fileExt );
				result.add( fileVO );
			}

		}

		// 파일사이즈 초과시 첨부한 전체파일 삭제
		if ("Y".equals( fileSizeError )) {
			fileVO = null;

			for (int i = 0; i < result.size(); i++) {
				fileVO = result.get( i );

				deleteFile( fileVO.getFileStreCours(), fileVO.getStreFileNm() );
			}
			result = null;
		}

		return result;
	}


	/**
	 * 서버 파일을 삭제 처리한다.
	 *
	 * @param filePath
	 * @param filePyscName
	 * @throws Exception
	 */
	public static void deleteFile(String filePath, String filePyscName) {

		if (filePath != null && !"".equals( filePath ) && filePyscName != null && !"".equals( filePyscName )) {

			String deleteFileName = filePath + File.separator + filePyscName;

			File file = new File( deleteFileName );
			if (file.exists() && file.isFile()) {
				file.delete();
			}
		}
	}

	/**
	 * 첨부파일에 대한 목록 정보를 취득한다.
	 * 
	 * @param files
	 * @return
	 * @throws Exception
	 */
	public List<FileVO> parseFileInf(Map<String, MultipartFile> files,
			String KeyStr, int fileKeyParam, String atchFileId, String storePath)
			throws Exception {
		int fileKey = fileKeyParam;
		
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

	    HttpServletRequest hsr = sra.getRequest();
		
//	    logger.info("servlet : "+hsr.getSession().getServletContext().getRealPath("/"));

		String atchFileIdString = "";

		StringBuffer absolutePath = new StringBuffer();
		
		absolutePath.append(hsr.getSession().getServletContext().getRealPath("/"));
		
		if ("".equals(storePath) || storePath == null) {
			absolutePath.append(propertyService
					.getString("Globals.fileStorePath"));
		} else {
			absolutePath.append(propertyService.getString(storePath));
		}

		if ("".equals(atchFileId) || atchFileId == null) {
			atchFileIdString = idgenService.getNextStringId();
		} else {
			atchFileIdString = atchFileId;
		}
		
		

		Calendar calVal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
		String today = dateFormat.format(calVal.getTime());

		StringBuffer datePath = new StringBuffer();
		datePath.append("/");
		datePath.append(today.substring(0, 4));
		datePath.append("/");
		datePath.append(today.substring(4, 6));
		datePath.append("/");
		datePath.append(today.substring(6, 8));

		absolutePath.append(datePath);

		File saveFolder = new File(absolutePath.toString());

		if (!saveFolder.exists() || saveFolder.isFile()) {
			saveFolder.mkdirs();
		}

		Iterator<Entry<String, MultipartFile>> itr = files.entrySet()
				.iterator();
		MultipartFile file;
		String filePath = "";
		List<FileVO> result = new ArrayList<FileVO>();
		FileVO fvo;

		while (itr.hasNext()) {
			Entry<String, MultipartFile> entry = itr.next();

			file = entry.getValue();
			String orginFileName = file.getOriginalFilename();

			// --------------------------------------
			// 원 파일명이 없는 경우 처리
			// (첨부가 되지 않은 input file type)
			// --------------------------------------
			if ("".equals(orginFileName)) {
				continue;
			}
			// //------------------------------------

			int index = orginFileName.lastIndexOf(".");
			// String fileName = orginFileName.substring(0, index);
			String fileExt = orginFileName.substring(index + 1);
			String newName = KeyStr + StringUtil.getTimeStamp() + fileKey;
			long _size = file.getSize();

			if (!"".equals(orginFileName)) {
				filePath = absolutePath.toString() + File.separator + newName;
				file.transferTo(new File(filePath));
			}
			fvo = new FileVO();
			fvo.setFileExtsn(fileExt);
			fvo.setFileStreCours(datePath.toString());
			fvo.setFileMg(Long.toString(_size));
			fvo.setOrignlFileNm(orginFileName);
			fvo.setStreFileNm(newName);
			fvo.setAtchFileId(atchFileIdString);
			fvo.setFileSn(String.valueOf(fileKey));

			// writeFile(file, newName, storePathString);
			result.add(fvo);

			fileKey++;
		}

		return result;
	}
	
	/**
	 * 첨부파일에 대한 목록 정보를 취득한다.
	 * 
	 * @param files
	 * @return
	 * @throws Exception
	 */
	public List<FileVO> parseFileInf(Map<String, MultipartFile> files,
			String KeyStr, int fileKeyParam, String atchFileId, String storePath, String appendPath)
			throws Exception {
		int fileKey = fileKeyParam;
		
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

	    HttpServletRequest hsr = sra.getRequest();
		
//	    logger.info("servlet : "+hsr.getSession().getServletContext().getRealPath("/"));

		String atchFileIdString = "";

		StringBuffer absolutePath = new StringBuffer();
		
		absolutePath.append(hsr.getSession().getServletContext().getRealPath("/"));
		
		if ("".equals(storePath) || storePath == null) {
			absolutePath.append(propertyService
					.getString("Globals.fileStorePath"));
		} else {
			absolutePath.append(propertyService.getString(storePath));
		}

		if ("".equals(atchFileId) || atchFileId == null) {
			atchFileIdString = idgenService.getNextStringId();
		} else {
			atchFileIdString = atchFileId;
		}

		Calendar calVal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
		String today = dateFormat.format(calVal.getTime());

		StringBuffer datePath = new StringBuffer();
		datePath.append("/").append(appendPath);
		datePath.append("/");
		datePath.append(today.substring(0, 4));
		datePath.append("/");
		datePath.append(today.substring(4, 6));
		datePath.append("/");
		datePath.append(today.substring(6, 8));

		absolutePath.append(datePath);

		File saveFolder = new File(absolutePath.toString());

		if (!saveFolder.exists() || saveFolder.isFile()) {
			saveFolder.mkdirs();
		}

		Iterator<Entry<String, MultipartFile>> itr = files.entrySet()
				.iterator();
		MultipartFile file;
		String filePath = "";
		List<FileVO> result = new ArrayList<FileVO>();
		FileVO fvo;

		while (itr.hasNext()) {
			Entry<String, MultipartFile> entry = itr.next();

			file = entry.getValue();
			String orginFileName = file.getOriginalFilename();

			// --------------------------------------
			// 원 파일명이 없는 경우 처리
			// (첨부가 되지 않은 input file type)
			// --------------------------------------
			if ("".equals(orginFileName)) {
				continue;
			}
			// //------------------------------------

			int index = orginFileName.lastIndexOf(".");
			// String fileName = orginFileName.substring(0, index);
			String fileExt = orginFileName.substring(index + 1);
			String newName = KeyStr + StringUtil.getTimeStamp() + fileKey;
			long _size = file.getSize();

			if (!"".equals(orginFileName)) {
				filePath = absolutePath.toString() + File.separator + newName;
				file.transferTo(new File(filePath));
			}
			fvo = new FileVO();
			fvo.setFileExtsn(fileExt);
			fvo.setFileStreCours(datePath.toString());
			fvo.setFileMg(Long.toString(_size));
			fvo.setOrignlFileNm(orginFileName);
			fvo.setStreFileNm(newName);
			fvo.setAtchFileId(atchFileIdString);
			fvo.setFileSn(String.valueOf(fileKey));

			// writeFile(file, newName, storePathString);
			result.add(fvo);

			fileKey++;
		}

		return result;
	}

	/**
	 * 첨부파일을 서버에 저장한다.
	 * 
	 * @param file
	 * @param newName
	 * @param stordFilePath
	 * @throws Exception
	 */
	protected void writeUploadedFile(MultipartFile file, String newName,
			String stordFilePath) throws Exception {
		InputStream stream = null;
		OutputStream bos = null;

		try {
			stream = file.getInputStream();
			File cFile = new File(stordFilePath);

			if (!cFile.isDirectory()) {
				boolean _flag = cFile.mkdir();
				if (!_flag) {
					throw new IOException("Directory creation Failed ");
				}
			}

			bos = new FileOutputStream(stordFilePath + File.separator + newName);

			int bytesRead = 0;
			byte[] buffer = new byte[BUFF_SIZE];

			while (true) {//bytesRead = stream.read(buffer, 0, BUFF_SIZE)) != -1
				bytesRead = stream.read(buffer, 0, BUFF_SIZE);
				if(bytesRead != -1)	{
				bos.write(buffer, 0, bytesRead);
				}else {
					break;
				}
			}
		} catch (FileNotFoundException fnfe) {
			logger.error("error --> " + fnfe.getMessage());
		} catch (IOException ioe) {
			logger.error("error --> " + ioe.getMessage());
		} catch (Exception e) {
			logger.error("error --> " + e.getMessage());
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (Exception ignore) {
					logger.debug("IGNORED: " + ignore.getMessage());
				}
			}
			if (stream != null) {
				try {
					stream.close();
				} catch (Exception ignore) {
					logger.debug("IGNORED: " + ignore.getMessage());
				}
			}
		}
	}

	/**
	 * 첨부로 등록된 파일을 서버에 업로드한다.
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, String> uploadFile(MultipartFile file)
			throws Exception {

		HashMap<String, String> map = new HashMap<String, String>();
		// Write File 이후 Move File????
		String newName = "";
		String stordFilePath = propertyService
				.getString("Globals.fileStorePath"); // Globals.fileStorePath;
														// //propertyService.getString("Globals.fileStorePath");

		// ComProperties.getProperty("Globals.fileStorePath");
		String orginFileName = file.getOriginalFilename();

		int index = orginFileName.lastIndexOf(".");
		// String fileName = orginFileName.substring(0, _index);
		String fileExt = orginFileName.substring(index + 1);
		long size = file.getSize();

		// newName 은 Naming Convention에 의해서 생성
		newName = StringUtil.getTimeStamp() + "." + fileExt;
		writeFile(file, newName, stordFilePath);
		// storedFilePath는 지정
		map.put(Globals.ORIGIN_FILE_NM, orginFileName);
		map.put(Globals.UPLOAD_FILE_NM, newName);
		map.put(Globals.FILE_EXT, fileExt);
		map.put(Globals.FILE_PATH, stordFilePath);
		map.put(Globals.FILE_SIZE, String.valueOf(size));

		// newName = "F"+System.nanoTime();

		return map;
	}

	/**
	 * 파일을 실제 물리적인 경로에 생성한다.
	 * 
	 * @param file
	 * @param newName
	 * @param stordFilePath
	 * @throws Exception
	 */
	protected static void writeFile(MultipartFile file, String newName,
			String stordFilePath) throws Exception {
		InputStream stream = null;
		OutputStream bos = null;

		try {
			stream = file.getInputStream();

			// 디렉토리 생성
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
			Calendar calVal = Calendar.getInstance();
			String today = dateFormat.format(calVal.getTime());
			today = today.substring(0, 4) + "/" + today.substring(4, 6) + "/"
					+ today.substring(6, 8) + "/";
			// String filePath = stordFilePath+ today;

			File cFile = new File(stordFilePath + today);

			if (!cFile.isDirectory())
				cFile.mkdir();

			bos = new FileOutputStream(stordFilePath + File.separator + newName);

			int bytesRead = 0;
			byte[] buffer = new byte[BUFF_SIZE];

			while (true) {//(bytesRead = stream.read(buffer, 0, BUFF_SIZE)) != -1
				bytesRead = stream.read(buffer, 0, BUFF_SIZE);
				if(bytesRead != -1) {
				bos.write(buffer, 0, bytesRead);
				}else {
					break;
				}
			}
		} catch (FileNotFoundException fnfe) {
			logger.error("error --> " + fnfe.getMessage());
		} catch (IOException ioe) {
			logger.error("error --> " + ioe.getMessage());
		} catch (Exception e) {
			logger.error("error --> " + e.getMessage());
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (Exception ignore) {
					logger.debug("IGNORED: " + ignore.getMessage());
				}
			}
			if (stream != null) {
				try {
					stream.close();
				} catch (Exception ignore) {
					logger.debug("IGNORED: " + ignore.getMessage());
				}
			}
		}
	}

	/**
	 * 서버 파일에 대하여 다운로드를 처리한다.
	 * 
	 * @param response
	 * @param streFileNm
	 *            : 파일저장 경로가 포함된 형태
	 * @param orignFileNm
	 * @throws Exception
	 */
	public void downFile(HttpServletResponse response, String streFileNm,
			String orignFileNm) throws Exception {
		String downFileName = streFileNm;
		String orgFileName = orignFileNm;

		File file = new File(downFileName);

		if (!file.exists()) {
			throw new FileNotFoundException(downFileName);
		}

		if (!file.isFile()) {
			throw new FileNotFoundException(downFileName);
		}

		// byte[] b = new byte[BUFF_SIZE]; //buffer size 2K.
		int fSize = (int) file.length();
		if (fSize > 0) {
			BufferedInputStream in = new BufferedInputStream(
					new FileInputStream(file));
			String mimetype = "text/html"; // "application/x-msdownload"

			response.setBufferSize(fSize);
			response.setContentType(mimetype);
			response.setHeader("Content-Disposition:", "attachment; filename="
					+ orgFileName);
			response.setContentLength(fSize);
			FileCopyUtils.copy(in, response.getOutputStream());
			in.close();
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}

	}

	/**
	 * 서버 파일에 대하여 다운로드를 처리한다.
	 * 
	 * @param response
	 * @param streFileNm
	 *            : 파일저장 경로가 포함된 형태
	 * @param orignFileNm
	 * @throws Exception
	 * 
	 * 한글 파일명이 깨지는 현상 수정
	 */
	public void downFileUTF8(HttpServletResponse response, String streFileNm,
			String orignFileNm) throws Exception {
		String downFileName = streFileNm;
		String orgFileName = orignFileNm;

		File file = new File(downFileName);

		if (!file.exists()) {
			throw new FileNotFoundException(downFileName);
		}

		if (!file.isFile()) {
			throw new FileNotFoundException(downFileName);
		}

		// byte[] b = new byte[BUFF_SIZE]; //buffer size 2K.
		int fSize = (int) file.length();
		if (fSize > 0) {
			BufferedInputStream in = new BufferedInputStream(
					new FileInputStream(file));
			String mimetype = "application/octet-stream; charset=euc-kr"; // "application/x-msdownload"

			response.setBufferSize(fSize);
			response.setContentType(mimetype);
			response.setHeader("Content-Disposition:", "attachment; filename="
					+ java.net.URLEncoder.encode(orgFileName, "UTF-8") +";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setContentLength(fSize);
			FileCopyUtils.copy(in, response.getOutputStream());
			in.close();
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}

	}
	
	/** 
		 * @Method Name  : FileMngUtil.java
		 * @since      : 2011. 10. 6.
		 * @author     : sunghoon
		 * @version    :
		 * @Locaton    : com.lottemart.common.file.util
		 * @Description :  물리파일 삭제 (1이면 성공 0이면 실패)
	     * @param fvo
	     * @param storePath
	     * @return
	     * @throws Exception 
	*/
	public int deleteRealFile(FileVO fvo, String storePath) throws Exception {
		// TODO Auto-generated method stub
		
		StringBuffer absolutePath = new StringBuffer();
		
		if ("".equals(storePath) || storePath == null) {
		    absolutePath.append(propertyService.getString("Globals.fileStorePath"));
		} else {
		    absolutePath.append(propertyService.getString(storePath));
		}
		
		absolutePath.append(fvo.getFileStreCours());
		absolutePath.append("/").append(fvo.getStreFileNm());
		
		File file = new File(absolutePath.toString());
		if(file.exists()) {
			if(file.delete()) {
				return 1;
			}else {
				return 0;
			}
		}
		return 0;
	}
	
	public void downloadExcelSampleFile(HttpServletResponse response, HttpServletRequest request, String filePath, String originalFileName) throws Exception {
		this.downloadApplicationFile(response, request, "/WEB-INF/jsp/excel/" + filePath, originalFileName);
	}
	
	public void downloadApplicationFile(HttpServletResponse response, HttpServletRequest request, String filePath, String originalFileName) throws Exception {
		try {
			String storedFileName = request.getSession().getServletContext().getRealPath(filePath);
			
			byte fileByte[] = FileUtils.readFileToByteArray(new File(storedFileName));
			
			response.setContentType("application/octet-stream");
			response.setContentLength(fileByte.length);
			
			response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(originalFileName,"UTF-8").replaceAll("\\+", "%20"));
			response.setHeader("Content-Transfer-Encoding", "binary");
			
			response.getOutputStream().write(fileByte);
			response.getOutputStream().flush();
		} catch (Exception e) {
			logger.error("error --> " + e.getMessage());
		} finally {
			response.getOutputStream().close();
		}
	}
}
