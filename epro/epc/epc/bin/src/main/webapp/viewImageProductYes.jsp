<%@ page language="java" 
contentType="image/jpeg;charset=euc-kr" 
import="
java.util.*,
java.sql.*,
java.io.*,
java.net.*,
com.lottemart.common.util.ConfigUtils 
" %>

<%
	String imgPath = ConfigUtils.getString("pog.fix.path");
	//System.out.println("imgPath ===" + imgPath);

    String img_file_nm = request.getParameter("img_file_nm");
    //String img_file_nm = "0419113900006.1";
   	
	//-----[DEV]
	//img_file_nm = "http://10.52.11.133/TEST_IKB_Img/" + img_file_nm.substring(0,7) + "/" + img_file_nm.substring(7,img_file_nm.length());
	
	//-----[REAL]
	//img_file_nm = "http://10.52.11.133/IKB_Image/" + img_file_nm.substring(0,7) + "/" + img_file_nm.substring(7,img_file_nm.length());
	
	img_file_nm = imgPath + img_file_nm.substring(0,7) + "/" + img_file_nm.substring(7,img_file_nm.length());	
	//System.out.println("img_file_nm ==" + img_file_nm);
		
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