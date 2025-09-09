package com.lottemart.epc.edi.product.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;

import lcn.module.common.file.FileUtil;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.edi.product.dao.NEDMPRO0180Dao;
import com.lottemart.epc.edi.product.model.NEDMPRO0180VO;
import com.lottemart.epc.edi.product.service.NEDMPRO0180Service;

@Service("nedmpro0180Service")
public class NEDMPRO0180ServiceImpl implements NEDMPRO0180Service {
	
	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0180ServiceImpl.class);
	
	@Autowired
	private NEDMPRO0180Dao nedmpro0180Dao;

	/**
	 * PB상품 리스트 조회
	 * @return
	 * @throws Exception
	 */
	public List<NEDMPRO0180VO> selectPbProdList(NEDMPRO0180VO vo) throws Exception {
		return nedmpro0180Dao.selectPbProdList(vo);
	}
	
	/**
	 * 특정 PB상품 성적서 정보 조회
	 * @return
	 * @throws Exception
	 */
	public NEDMPRO0180VO selectPbProdReportInfo(NEDMPRO0180VO vo) throws Exception {
		return nedmpro0180Dao.selectPbProdReportInfo(vo);
	}

	/**
	 * 성적서 정보 업데이트
	 * @return
	 * @throws Exception
	 */
	public boolean updateReportFile(NEDMPRO0180VO reportVO) throws Exception {
		
		/* 이미지 업로드경로 */
		String imageUploadPath = ConfigUtils.getString("edi.pbprod.report.path");	
		String detailImageExt = "";
		String serverFileNm = "";
		
		String orgFileNm = "";
		String fileNmSaving = "";
		MultipartFile uploadfile = reportVO.getReportPbProdFile();
		
		/* 파일이름 문자필터링 */
		if (!uploadfile.isEmpty()) {
		    orgFileNm = uploadfile.getOriginalFilename();
			detailImageExt = FilenameUtils.getExtension(uploadfile.getOriginalFilename());
			fileNmSaving = orgFileNm.replace("."+detailImageExt,"");
			
			String charReplacing = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
			fileNmSaving = fileNmSaving.replaceAll(charReplacing, "");
			orgFileNm = fileNmSaving + "." + detailImageExt;
		}
		
		if (reportVO.getProdCd() == null || reportVO.getProdCd().isEmpty()) {
			return false;
		}
		
		imageUploadPath = imageUploadPath + "/" + reportVO.getProdCd(); 
				
		/* 파일 업로드 폴더 확인 없으면 폴더 생성 */
		File folderDir = new File(imageUploadPath);
		if (!folderDir.isDirectory()) {
			folderDir.mkdirs();
		}
		
		/* 서버파일이름 생성 */
		serverFileNm = "RPT1000_" + getTodayFormatYymmdd() + System.currentTimeMillis() + "." + detailImageExt;
		
		/* 파일 업로드 */
		FileOutputStream fileOs = new FileOutputStream(imageUploadPath + "/" + serverFileNm);	
		try {
		
			/* 파일서버에서 저장되어 있는 성적서 파일 삭제 */
			NEDMPRO0180VO pbProdReportDel = nedmpro0180Dao.selectPbProdReportInfo(reportVO);
			String reportFileNmInServer = null;
			
			if (pbProdReportDel != null) {
				reportFileNmInServer = pbProdReportDel.getServerFileNm();
			}
			
			if (reportFileNmInServer != null && !reportFileNmInServer.isEmpty()) {
				File reportFileInServer = new File(imageUploadPath+"/"+reportFileNmInServer);
				if (reportFileInServer.isFile()) {
					reportFileInServer.delete();
				}
			}
			
			FileCopyUtils.copy(uploadfile.getInputStream(), fileOs);
			
			/* DB에 저장할 파일 크기 및 서버파일명*/
			reportVO.setFileSize(String.valueOf(reportVO.getReportPbProdFile().getSize()));
			reportVO.setServerFileNm(serverFileNm);
			
			nedmpro0180Dao.updateReportFile(reportVO);	
			
		} catch (Exception e) {
			logger.debug("PBprod Report : "+e.getMessage());
			return false;
		} finally {
			if (fileOs != null) {
				try {
					fileOs.close();
				} catch (Exception e) {
					logger.debug("PBprod report : "+e.getMessage());
					return false;
				}
			}
		}
		
		return true;
	}
	
	
	/**
	 * 특정 성적서 정보 삭제
	 * @return
	 * @throws Exception
	 */
	public boolean deleteReportFile(NEDMPRO0180VO reportVO) throws Exception {
		
        String imageUploadPath = ConfigUtils.getString("edi.pbprod.report.path");
		String reportFileNmInServer = null;
		
		/* 서버에 저장된 성적서 파일명 불러오기 */
		reportFileNmInServer = nedmpro0180Dao.selectPbProdReportInfo(reportVO).getServerFileNm();
		 
		if (reportFileNmInServer == null || reportFileNmInServer.isEmpty()) {
			return false;
		}
		
		if (reportVO.getProdCd() == null || reportVO.getProdCd().isEmpty()) {
			return false;
		}
        
        imageUploadPath = imageUploadPath + "/" + reportVO.getProdCd();
		
		/* 서버에 저장된 파일 삭제*/
		File reportFile = new File(imageUploadPath + "/" + reportFileNmInServer);
		if (reportFile.isFile()) {
		   FileUtil.delete(reportFile);
		}		
		 
		/* DB에 성적서 파일 삭제 표시*/
		try {
			nedmpro0180Dao.deleteReportFile(reportVO);
		} catch(Exception e) {
			logger.debug("PBReport Del Error: "+e);
			return false;
		}
		
		return true;
	}
	
	/**
	 * 성적서 업로드 필요 개수 확인
	 * @return
	 * @throws Exception
	 */
	public Integer countNotValidPbProdReport(NEDMPRO0180VO reportVO) throws Exception {	
		Integer reportCntNotValid = nedmpro0180Dao.countNotValidPbProdReport(reportVO);		
		return reportCntNotValid;
	}
	
	
	/**
	 * PB 상품에 등록된 성적서 정보 데이터 개수
	 * @return
	 * @throws Exception
	 */
	public Integer countReportFileForAProd(NEDMPRO0180VO reportVO) throws Exception {
		Integer cntReportFileRegisterd = nedmpro0180Dao.countReportFileForAProd(reportVO);
		return cntReportFileRegisterd;
	}
	
	/**
	 * 성적서 관리부서 조회
	 * @return
	 * @throws Exception
	 */
	public List<String> selectAdminDeptPbReport(String adminId) throws Exception {
		List<String> adminDepts  = nedmpro0180Dao.selectAdminDeptPbReport(adminId);
		return adminDepts;
	}
	
	/**
	 * 성적서 다운로드
	 * @return
	 * @throws Exception
	 */
	public boolean downloadReportFile(NEDMPRO0180VO reportVO, HttpServletResponse response) throws Exception {
       try {
			
			String imageUploadPath = ConfigUtils.getString("edi.pbprod.report.path");
			NEDMPRO0180VO pbProdReportInfo = nedmpro0180Dao.selectPbProdReportInfo(reportVO);
			String serverFileNm = pbProdReportInfo.getServerFileNm();
			String srcFileNm = pbProdReportInfo.getSrcFileNm();
			String path = imageUploadPath + "/" + pbProdReportInfo.getProdCd() + "/" + serverFileNm;
			
			File file = new File(path);
			int fileSize = (int) file.length();		
			
			if (fileSize < 0) {
				throw new FileNotFoundException("파일이 없습니다");
			}
			
			String encodedFileName = "attachment; filename*=" + "UTF-8" + "''" + URLEncoder.encode(srcFileNm,"UTF-8");
		
		    response.setContentType("application/octet-stream; charset=utf-8");
		    response.setHeader("Content-Transfer-Encoding", "binary");
		    response.setHeader("Content-Disposition", encodedFileName);
		    response.setContentLength(fileSize);
		    BufferedInputStream in = null;
		    BufferedOutputStream out = null;	    
		   
		    
		    try {
		        in = new BufferedInputStream(new FileInputStream(file));
				out = new BufferedOutputStream(response.getOutputStream());
				    
		    	byte[] buffer = new byte[4096];
		    	int bytesRead = 0;
		    	
		    	while((bytesRead = in.read(buffer)) != -1) {
		    		out.write(buffer, 0, bytesRead);
		    	}
		    	
		    	out.flush();
		    } finally {
		    	if (in != null) {
		    		in.close();
		    	}
		    	if (out != null) {
		    		out.close();	
		    	}
		    }		    
			return true;
		} catch (Exception e) {
			logger.debug("PBprod report [down] : "+e.getMessage());
			return false;
		}
	}
	

	public String getTodayFormatYymmdd() throws Exception {
		StringBuffer sb = new StringBuffer();
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
		dateFormat.format(now, sb, new FieldPosition(0));
		return sb.toString();
	}

}
