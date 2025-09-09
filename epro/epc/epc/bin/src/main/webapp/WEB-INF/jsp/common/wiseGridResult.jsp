<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="org.apache.commons.lang.*" %>
<%@ page import="xlib.cmc.*" %>
<%
try {
	GridData gdRes = (GridData)request.getAttribute("wizeGridResult");
	OperateGridData.write(gdRes, out);
} catch(Exception e) {
	try {
	    // Exception 발생시 gdRes 객체를 초기화하고 메세지와 상태값만 셋팅합니다.
	    GridData gdRes = new GridData();
	    gdRes.setMessage("Error: " + e.getMessage());
	    gdRes.setStatus("false");
		OperateGridData.write(gdRes, out);
	} catch (Exception e2) {}
}
%>