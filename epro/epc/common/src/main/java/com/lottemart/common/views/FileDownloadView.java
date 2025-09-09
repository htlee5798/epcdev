package com.lottemart.common.views;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

public class FileDownloadView extends AbstractView {

	private static final Logger logger = LoggerFactory.getLogger(FileDownloadView.class);

	public FileDownloadView() {
		// 받드시 octet-stream으로 설정해야함
		super.setContentType("application/octet-stream");
	}

	@Override
	protected void renderMergedOutputModel(Map model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		File file = (File) model.get("file");
		String fileName = (String) model.get("fileName");

		logger.info("== download할 파일 : " + file.getAbsolutePath()
				+ ", 지정할 파일명 : " + fileName);

		response.setContentType(super.getContentType());
		response.setContentLength((int) file.length());
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Content-Disposition", "attachment;fileName=\""
				+ URLEncoder.encode(fileName, "UTF-8") + "\";");
//

//	  
//	    String userAgent = request.getHeader("User-Agent");	    
//	    if (userAgent.indexOf("MSIE 5.5") > -1) { // MS IE 5.5 이하
//	      response.setHeader("Content-Disposition", "filename=" + URLEncoder.encode(fileName, "UTF-8") + ";");
//	    } else if (userAgent.indexOf("MSIE") > -1) { // MS IE (보통은 6.x 이상 가정)
//	      response.setHeader("Content-Disposition", "attachment; filename="
//	          + java.net.URLEncoder.encode(fileName, "UTF-8") + ";");
//	    } else { // 모질라나 오페라
//	      response.setHeader("Content-Disposition", "attachment; filename="
//	          + new String(fileName.getBytes("euc-kr"), "latin1") + ";");
//	    }
		
//		
		OutputStream out = response.getOutputStream();
		FileInputStream fis = null;
		logger.debug("file = " + file);
		try {
			fis = new FileInputStream(file);
			FileCopyUtils.copy(fis, out);
		} finally {
			if (fis != null) {
				fis.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}

}
