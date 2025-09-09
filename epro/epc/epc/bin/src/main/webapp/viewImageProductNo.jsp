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
com.sun.image.codec.jpeg.*; 

" %>

<%

    String img_file_nm = request.getParameter("img_file_nm");		//(이미지경로);   
		
	img_file_nm = "http://10.52.1.163/IKBimage/EDIImg/" + img_file_nm;
	
		
	byte img;
	int i;
	
	try{
		out.clear(); 
		out=pageContext.pushBody();

	    ServletOutputStream outStream = response.getOutputStream();
    	DataInputStream ina = new DataInputStream((new URL(img_file_nm)).openStream());

    	byte[] imgLong= new byte[512];
    	
    	while((i = ina.read(imgLong)) > 0){
    		outStream.write(imgLong,0,i);
    	}
    	outStream.close();
	
	}catch(IOException e){}
	
	
%>    

  
