/**
 * @prjectName  : 롯데마트 온라인 쇼핑몰 lottemart-common
 * @since    : 2011
 * @Description : 
 * @author : jmryu
 * @Copyright (C) 2011 ~ 2012 lottemart All right reserved.
 * </pre>
 */
package com.lottemart.common.file.controller;

import java.io.File;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import eu.medsea.mimeutil.MimeUtil;

/**
 * @author jmryu
 * @Class : com.lottemart.common.file.controller
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 
 * @version : 
 */

@Controller("fileCheckController")
public class FileCheckController {
	
	final static Logger logger = LoggerFactory.getLogger(FileCheckController.class);
	
	 @RequestMapping(value = "/main/fileSize.do")
	 public void sizeCheck(@RequestParam("size") String size, final HttpServletResponse response) throws Exception { 
		 logger.debug("size : "+size);
		 
	  MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.ExtensionMimeDetector");

	  File file = new File(size);
	  logger.debug("file : "+file.getAbsolutePath());
	  String flag ="0";
	  String ext ="";
	//  MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
	  Collection<String> mimeTypes  = MimeUtil.getMimeTypes(file); 
	  //String extflag ="|image/jpeg|image/gif|image/png|image/bmp|application/msword|application/pdf|application/zip";
	  String extflag ="|jpeg|jpg|gif|png|bmp|ppt|pptx|xls|xlsx|doc|docx|pdf|";
	  int cnt = file.getAbsolutePath().lastIndexOf(".");
	  if(cnt == -1){
		  flag = "1";
	  }else{
		  ext = file.getAbsolutePath().substring(cnt +1);
	  }
	  Long filebyte = file.length();
		 logger.debug("ext : "+ext);
		 logger.debug("mimeType : "+mimeTypes);
	  if(extflag.indexOf(ext.toLowerCase()) == -1){
		flag ="1";
	  }
	  logger.debug("filebyte : "+filebyte);
	  if(filebyte > 10240000){
		  flag ="2";
	  }
	  PrintWriter pw = response.getWriter();
	  pw.print(flag);
	  pw.flush();
	  pw.close();    
	 }

}
