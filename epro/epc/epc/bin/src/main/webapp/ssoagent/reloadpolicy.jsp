<%@ page import="com.nets.sso.agent.configuration.SSOConfig" %>
<%@ page import="com.nets.sso.agent.AuthUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%
int prePolicyVer = 0;
int postPolicyVer = 0; 
// String publicIP = "";
String siteID = "";
try{
// 	publicIP = AuthUtil.GetPublicIP(request);
	siteID = AuthUtil.GetSiteID(request);
	prePolicyVer = SSOConfig.getInstance().getCurrentSSOProvider(siteID).getPolicyVer();
} catch (Exception e){
}

SSOConfig.setReloading(false);
SSOConfig.getInstance().reLoad();

siteID = AuthUtil.GetSiteID(request);
postPolicyVer = SSOConfig.getInstance().getCurrentSSOProvider(siteID).getPolicyVer();
%>policy version:<%=prePolicyVer%> -> <%=postPolicyVer%>