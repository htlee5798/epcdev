<%@ page import="nets.sso.agent.web.common.util.StrUtil" %>
<%@ page import="nets.sso.agent.web.v9.SSOAuthn" %>
<%@ page import="nets.sso.agent.web.v9.SSOUser" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    SSOUser user = SSOAuthn.authn(request, response);
    // user == null 인 경우, 페이지 이동이 되어야 하니, 더 이상 코드 진행이 되지 않아야 합니다.
    if (user == null)
        return;
%>
<!DOCTYPE html>
<html>
<head>
    <title>NETS*SSO</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv='X-UA-Compatible' content='IE=edge,chrome=1'/>
</head>
<body>
사용자 명: <%= user.getUserID() %>
<hr/>
사용자 속성<br/>
<%= StrUtil.join(user.getAttrs(), "<br>", "=") %>
<hr/>
<a href="<%=SSOAuthn.getUrl(request, response).getLogoutFullUrl()%>">Logout</a>
<hr/>
</body>
</html>