package com.lottemart.epc.edi.comm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lcnjf.util.StringUtil;
import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.edi.comm.service.ImageLoadService;

/**
 * 
 * @Class Name : ImageLoadController.java
 * @Description : 이미지 공통 load 
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2025.09.04		yun				최초생성
 *               </pre>
 */
@Controller
public class ImageLoadController {
	
	private static final Logger logger = LoggerFactory.getLogger(ImageLoadController.class);
	
	@Autowired
	ImageLoadService imageLoadService;
	
	/**
	 * 공통 이미지 로드
	 * @param workGbn		업무구분
	 * @param atchFileId	첨부파일아이디
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/common/{workGbn}/{atchFileId}/loadImageCommon.do", method = RequestMethod.GET)
	public void loadImageCommon(@PathVariable("workGbn") String workGbn, @PathVariable("atchFileId") String atchFileId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String logMsg = "";		//로그용메세지
		
		//이미지정보
		Map<String,Object> imageInfo = null;
		
		//파라미터 공백 제거
		workGbn = StringUtil.removeWhitespace(workGbn);				//업무구분
		atchFileId = StringUtil.removeWhitespace(atchFileId);		//파일아이디
		
		//로그용 메세지 셋팅
		logMsg = String.format("(workGbn:%s, atchFildId:%s)", workGbn, atchFileId);
		
		//첨부파일 아이디 누락
		if("".equals(atchFileId)) {
			logger.error("이미지 조회 실패 - 첨부파일 아이디 누락");
			return;
		}
		
		//이미지정보 조회를 위한 파라미터 셋팅
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("atchFileId", atchFileId);
		
		//업무 구분에 따른 이미지 정보 조회
		switch(workGbn) {
			case "esg":		//ESG 인증(신규, 갱신)
				imageInfo = imageLoadService.selectImageInfoProductEsg(paramMap);
				break;
			case "prop":	//신상품입점제안
				imageInfo = imageLoadService.selectImageInfoNewProdProp(paramMap);
				break;
			default:
				logger.error("이미지 조회 실패 - 유효하지 않은 업무 구분"+logMsg);
				return;
		}
		
		//이미지 정보 없을 경우
		if(imageInfo == null) {
			logger.error("이미지 조회 실패 - 이미지 정보 조회 불가"+logMsg);
			return;
		}
		
		
		String filePath = MapUtils.getString(imageInfo, "FILE_PATH", "");		//첨부파일 경로
		String orgFileNm = MapUtils.getString(imageInfo, "ORG_FILE_NM", "");	//원본파일명
		String saveFileNm = MapUtils.getString(imageInfo, "SAVE_FILE_NM", "");	//저장파일명
		long fileSizeLong = MapUtils.getLongValue(imageInfo, "FILE_SIZE", 0);	//첨부파일 사이즈
		
		//첨부파일 업로드 full 경로
		String fullFilePath = filePath + "/" + saveFileNm;
		fullFilePath = StringUtil.getCleanPath(fullFilePath, false);
		
		InputStream fi = null;
		
		try {
			//파일 사이즈 없음
			if(fileSizeLong == 0) {
				logger.error("이미지 조회 실패 - 비어있는 파일"+logMsg);
				return;
			}
			
			//파일 존재여부 확인
	        File file = new File(fullFilePath);
	        if(!file.exists()) {
	        	logger.error("이미지 조회 실패 - 존재하지 않는파일"+logMsg);
	        	return;
	        }
	        
			//view 가능 확장자 체크
			String fileExtLimitStr = ConfigUtils.getString("fileCheck.atchFile.ext.imgpdf");	//이미지, PDF 형식만 VIEW가능
			String[] fileExtLimit = (fileExtLimitStr == null || "".equals(fileExtLimitStr))? null : fileExtLimitStr.split("\\|");
	        List<String> extLimit = (fileExtLimit == null)? null : Arrays.asList(fileExtLimit);
	        
	      	//첨부파일 확장자 추출
	        String fileExt = orgFileNm.substring(orgFileNm.lastIndexOf(".")+1).toLowerCase();
	        
	        if(!extLimit.contains(fileExt.toLowerCase())) {
	        	logger.error("이미지 조회 실패 - 조회 불가능한 확장자"+logMsg);
	        	return;
	        }
	        
	        fi = new FileInputStream(fullFilePath);
	        response.setContentLength((int) file.length());
	        
	        if("pdf".equals(fileExt)) {
	        	//PDF
	        	response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", "attachment; filename=\""+orgFileNm+"\";" );
	        }else{
	        	//이미지
	        	response.setHeader("Content-Disposition", "inline;");
	        }
			
	        FileCopyUtils.copy(fi, response.getOutputStream());
		}catch(Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}finally {
			if(fi != null) fi.close();
		}
	}
	

}
