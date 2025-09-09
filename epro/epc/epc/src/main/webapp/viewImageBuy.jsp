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

    String s_img_file_nm = "blank.jpg";  
    String img_file_nm = request.getParameter("img_file_nm");		//(이미지경로);   
   
	  // out.println(img_file_nm);
	 //img_file_nm        = "20100204W030000825000.JPG";	
	   
	   
	if(img_file_nm.equals(" ")){
		img_file_nm = s_img_file_nm;
		//img_file_nm = "http://172.150.1.16/bad_prod/" + s_img_file_nm;
		img_file_nm = "http://10.52.1.16/bad_prod/" + s_img_file_nm;
	}
	else{
		//img_file_nm = "http://10.52.1.16/bad_prod/" + img_file_nm.substring(0,6) + "/" + img_file_nm;
		
		img_file_nm = "http://10.52.1.16/bad_prod/arr_deny_prod/" + img_file_nm.substring(0,6) + "/" + img_file_nm;
		
		//img_file_nm = "http://172.150.1.16/bad_prod/" + img_file_nm.substring(0,6) + "/" + img_file_nm;
		//img_file_nm = "http://10.52.1.16/bad_prod/201002/201002086091291075000.JPG" ;
		//img_file_nm = "http://10.52.1.16/bad_prod/201002/201002084021208654000.JPG" ;
		
		//img_file_nm = "http://10.52.1.16/bad_prod/20100208/201002084021124356000.JPG";
		
	}
	
	
	//img_file_nm = "http://172.150.1.16/bad_prod/200501/200501033031005663000.JPG";
	
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

  
