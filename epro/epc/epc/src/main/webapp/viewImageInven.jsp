<%@ page language="java" 
contentType="image/jpeg;charset=euc-kr" 
import="
java.util.*,
java.sql.*,
java.io.*,
java.text.SimpleDateFormat,
java.net.*,
java.awt.*, 
java.awt.image.*,
javax.servlet.ServletOutputStream,
com.sun.image.codec.jpeg.*,
com.jcraft.jsch.*
" %>

<% 
  String s_img_file_nm = "blank.jpg";  
  String img_file_nm = request.getParameter("img_file_nm");   // 이미지경로   
  Boolean isBadImage = true;
    
  if (img_file_nm == null || img_file_nm.equals(" ") || img_file_nm == null || img_file_nm.equals("") || img_file_nm.equals("null")) {
      img_file_nm = s_img_file_nm;
      isBadImage = false;
  }
  else {
      //img_file_nm = "http://10.52.1.16/bad_prod/" + img_file_nm;
      img_file_nm = "/bprodimg/bad" + img_file_nm;
  }
  
  JSch jsch = new JSch();
  Session sessionJsch = null;
  ChannelSftp channelSftp = null;
  Channel channel = null;
  OutputStream outputStream = null;
  InputStream inputStream  = null;
  
  org.springframework.web.context.WebApplicationContext wac = org.springframework.web.context.support.WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
  lcn.module.framework.property.ConfigurationService configProperties = (lcn.module.framework.property.ConfigurationService)wac.getBean("configurationService");
  
  try {
      out.clear();
      out = pageContext.pushBody();
      
      String ftpUser = configProperties.getString("edi.badprod.user");
      String ftpPassword = configProperties.getString("edi.badprod.password");
      String fileServerIp = configProperties.getString("edi.badprod.serverIp");
     
      
      if ( isBadImage )
      {
          sessionJsch = jsch.getSession(ftpUser, fileServerIp, 22);
          sessionJsch.setConfig("StrictHostKeyChecking", "no");
          sessionJsch.setPassword(ftpPassword);
          sessionJsch.connect();
          
          channel = sessionJsch.openChannel("sftp");
          channel.connect();
          channelSftp = (ChannelSftp) channel; 
        
          inputStream = channelSftp.get(img_file_nm);
          outputStream = response.getOutputStream();
        
          int length;
          byte[] buffer = new byte[1024];
        
          while (( length = inputStream.read(buffer)) != -1 ) {
              outputStream.write(buffer, 0, length);
          }
        
          outputStream.close();
      } 
  }
  catch ( Exception e ) {
      System.out.println("[viewImageInven] " + e);
  }
  finally {
      if ( channelSftp != null) {
          channelSftp.exit();
          channelSftp = null;
      }
      
      if ( sessionJsch != null) {
          sessionJsch.disconnect();
          sessionJsch = null;
      }
    
      if ( inputStream != null ) {
          inputStream.close();
          inputStream = null;
      }
    
      if ( outputStream != null) {
          outputStream.close();
          outputStream = null;
      }
  }
%>    

  
