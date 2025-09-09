<%@ page import="com.nets.sso.agent.AuthUtil"%>
<%@ page import="com.nets.sso.agent.configuration.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String siteID = AuthUtil.GetSiteID(request);
SSOProvider provider = SSOConfig.getInstance().getCurrentSSOProvider(siteID);
SSOSite site = SSOConfig.getInstance().getCurrentSSOSite(siteID);
%>
<html>
<head><title>SSO 환경설정</title></head>
<body>
<table align="center" border="0" cellpadding="=0" cellspacing="0" width="100%">
<tr>
    <td valign="top">
        <table border="0" cellpadding="0" cellspacing="0" width="120">
        <tr class="text-blackgul">
            <td align="center" height="24" width="120">SSO 환경설정</td>
        </tr>
        </table>
    </td>
</tr>
<tr>
    <td valign="top">
        <table bgcolor="#074C91" border="0" cellpadding="01" cellspacing="1" style="width: 99%;">
        <tr>
            <td bgcolor="#FFFFFF" valign="top" width="100%">
                <table border="0" cellspacing="6" width="100%">
                <tr>
                    <td valign="top">
                        <table align="left" bgcolor="#FFFFFF" border="0" cellpadding="0" cellspacing="1" width="100%">
                        <tr>
                            <td valign="top">
                                <table align="left" bgcolor="#FFFFFF" border="0" cellpadding="0" cellspacing="1" width="100%">
                                <tr>
                                    <td colspan="2" style="height:20px;"></td>
                                </tr>
                                <tr>
                                    <td nowrap>빌드번호</td>
                                    <td><%=provider.getPolicyVer()%></td>
                                </tr>
                                <tr>
                                    <td nowrap>사용자 ID 입력 태그명</td>
                                    <td><%=provider.getIdTagName()%></td>
                                </tr>
                                <tr>
                                    <td nowrap>비빌번호 입력 태그명</td>
                                    <td><%=provider.getPwdTagName()%></td>
                                </tr>
                                <tr>
                                    <td nowrap>자격증명 종류 태그명</td>
                                    <td><%=provider.getCredTypeTagName()%></td>
                                </tr>
                                <tr>
                                    <td nowrap>리턴 URL 태그명</td>
                                    <td><%=provider.getReturnUrlTagName()%></td>
                                </tr>
                                <tr>
                                    <td nowrap>로그온 요청 URL</td>
                                    <td><%=provider.getLogonUrl(request, site.getSiteDNS())%></td>
                                </tr>
                                <tr>
                                    <td nowrap>로그오프 요청 URL</td>
                                    <td><%=provider.getLogoffUrl(request, site.getSiteDNS())%></td>
                                </tr>
                                <tr>
                                    <td nowrap>인증 검사 요청 URL</td>
                                    <td><%=provider.getAuthCheckUrl(request, site.getSiteDNS())%></td>
                                </tr>
                                <tr>
                                    <td nowrap>중앙인증 도메인</td>
                                    <td><%=provider.getProviderDomain()%></td>
                                </tr>
                                <tr>
                                    <td nowrap>인증요청 IP 검사 여부</td>
                                    <td><%=provider.getClientIPCheck()%></td>
                                </tr>
                                <tr>
                                    <td nowrap>인증 유효 기간(idle-timeout)</td>
                                    <td><%=provider.getIdleTimeout()%></td>
                                </tr>
                                <tr>
                                    <td nowrap>인증 만료 시간 사용 여부</td>
                                    <td><%=provider.getTokenExpiredYN()%></td>
                                </tr>
                                <tr>
                                    <td nowrap>인증 만료 시간</td>
                                    <td><%=provider.getTokenExpiredTimeout()%> (Hour)</td>
                                </tr>
                                <tr>
                                    <td nowrap>세션 관리 여부</td>
                                    <td><%=provider.getDuplicateLogonYN()%></td>
                                </tr>
                                <tr>
                                    <td nowrap>세션처리 옵션</td>
                                    <td><%=provider.getDuplicateLogonFirstPriority()%> (true:선입자/false:후입자)</td>
                                </tr>
                                <tr>
                                    <td nowrap>세션 재설정 시간</td>
                                    <td><%=provider.getDuplicateLogonCheckTimeout()%></td>
                                </tr>
                                <tr>
                                    <td nowrap>SSO 도메인</td>
                                    <td><%=site.getSsoDomain()%></td>
                                </tr>
                                <tr>
                                    <td nowrap>SSO 도메인 인증 쿠키명</td>
                                    <td><%=site.getTokenName()%></td>
                                </tr>
                                <tr>
                                    <td nowrap>SSO 참여 여부</td>
                                    <td><%=site.getSsoYN()%></td>
                                </tr>
                                <tr>
                                    <td nowrap>SSO 도메인 쿠키</td>
                                    <td><%=site.getCookieName()%></td>
                                </tr>                                
                                <tr>
                                    <td nowrap>SSO 사용자정보 쿠키</td>
                                    <td><%=site.getCookieName()%></td>
                                </tr>
                                <tr>
                                    <td nowrap>SSO 도메인 쿠키목록</td>
                                    <td>
                                        <table width="500px">
                                            <tr>
                                                <td>
                                                    <table bgcolor="#E0E0E0" border="0" cellpadding="0" cellspacing="1" width="50%">
                                                        <tr bgcolor="#C7DDF3" class="text-black">
                                                            <td align="center" height="20" nowrap width="40%">쿠키명</td>
                                                        </tr>
                                                        <%
                                                            if ( site.getCookieList() != null && site.getCookieList().length > 0){
                                                                for (int i = 0; i < site.getCookieList().length; i++){
                                                             	   
                                                        %>
                                                        <tr>
                                                            <td><%=site.getCookieList()[i]%></td>
                                                        </tr>
                                                        <%
                                                                }
                                                            }
                                                            %>
                                                    </table>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                                <tr>
                                    <td nowrap>SSO 사이트명</td>
                                    <td><%=site.getSiteDomain()%></td>
                                </tr>
                                <tr>
                                    <td nowrap>HttpOnly</td>
                                    <td><%=site.getHttpOnlyYN()%></td>
                                </tr>
                                <tr>
                                    <td nowrap>Secure</td>
                                    <td><%=site.getSecureYN()%></td>
                                </tr>
                                <tr>
                                    <td colspan="2" style="height:20px"></td>
                                </tr>
                                <tr>
                                    <td nowrap>FileCookieUse</td>
                                    <td><%=provider.isFileCookieUse()%></td>
                                </tr>
                                <tr>
                                    <td nowrap>FileCookieexpireHour</td>
                                    <td><%=provider.getFileCookieexpireHour()%></td>
                                </tr>                                
                                </table>
                            </td>
                        </tr>
                        </table>
                    </td>
                </tr>
                </table>
            </td>
        </tr>
        </table>
    </td>
</tr>
</table>
</body>
</html>