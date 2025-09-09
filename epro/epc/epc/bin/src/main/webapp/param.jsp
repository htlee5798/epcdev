<%@page import="com.lottemart.epc.common.util.SecureUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%

String param = SecureUtil.stripXSS(request.getParameter("name"));

out.println("param="+param);
%>
