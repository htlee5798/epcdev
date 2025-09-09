<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="com.softforum.xdbe.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Sample for jsp coding - SyncAPI</title>
</head>
<body>
<%
	int handle = 0;
	String spoolname = "aix64";
	String sIp = "10.52.2.137";
	String sDatabase = "LOTTE";
	String sOwner = "LOTTE";
	String sTable = "LOTTE";
	String sColumn = "pattern7";

	String sinput = "1234567123456";
	String sOutput64 = null;
	byte[] s2Output64 = null; 
	
	// Connection
	try {
		// connection with using poolname
 		handle = XdspNative.sync_connect_multi(spoolname, sDatabase, sOwner, sTable, sColumn);
	
		// connection with using ip and port
		/* handle = XdspNative.sync_connect("127.0.0.1", 9320, 5, "LOCALUSER", "78()uiOP", "DBNAME", "owner"
				, "table", "PATTEN7"); */
		out.println ("<font color='red'>" + "XdspNative.sync_connect: 연결 성공" + "</font></br></br>");
	} catch (XdspException e) {
		out.println ("<font color='red'>" + "XdspNative.sync_connect: 연결 실패" + "</font></br></br>");
    	e.printStackTrace();
	}
	
	// Encrpytion
	try {
		sOutput64 = XdspNative.sync_encrypt64(handle, sinput.getBytes());
		out.println ("<font color='red'> XdspNative.sync_encrypt64: " + sOutput64 + "</font></br></br>");
		
	} catch (XdspException e) {
		out.println ("<font color='red'>" + "XdspNative.sync_encrypt64: 수행 실패" + "</font></br></br>");
		XdspNative.sync_close(handle);
		e.printStackTrace();
	}
	
	// Decrpytion
	try {
		s2Output64 = XdspNative.sync_decrypt64(handle, sOutput64);
		String tmpString  = new String ( s2Output64 );
		out.println ("<font color='red'> XdspNative.sync_decrypt64: " + tmpString + "</font></br></br>");
		
	} catch (XdspException e) {
		out.println ("<font color='red'>" + "XdspNative.sync_decrypt64: 수행 실패" + "</font></br></br>");
		XdspNative.sync_close(handle);
		e.printStackTrace();
	}
	
	// Close
	try {
 		XdspNative.sync_close(handle);
		out.println ("<font color='red'>" + "XdspNative.sync_close: 연결 종료 성공" + "</font></br></br>");
	} catch (XdspException e) {
		out.println ("<font color='red'>" + "XdspNative.sync_close: 연결 종료 실패" + "</font></br></br>");
    	e.printStackTrace();
	}
	
	// Hash Function
	try {
		sOutput64 = XdspNative.hash64 (XdspNative.XDSP_API_HASH_ALGID_SHA256, sinput.getBytes() );
 		out.println ("<font color='red'> XdspNative.hash64: " + sOutput64 + "</font></br></br>");
	} catch (XdspException e) {
		out.println ("<font color='red'>" + "XdspNative.hash64: Hash Function 실패" + "</font></br></br>");
    	e.printStackTrace();
	}

%>
</body>
</html>