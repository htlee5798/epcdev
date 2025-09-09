<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
<h3>Bos Session 정보 </h3>
        <%      session = request.getSession(true);
                request.getSession().setAttribute("lottemart.epc.session.key","test");
        %>
        isNew():<%=session.isNew()%><br>
        세션ID:<%=session.getId()%><br>
        go BOS <a href="http://limadmin.lottemart.com/bos/ssession2.jsp">GO BOS</a>
        go CC <a href="http://limadmin.lottemart.com/cc/ssession2.jsp">GO CC</a>
</body>
</html>
