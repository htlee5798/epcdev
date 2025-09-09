<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
<h3>BOS Session 정보 </h3> 
        Attribute id 값 :<%=(String)request.getSession().getAttribute("lottemart.epc.session.key")%><br>
        isNew():<%=session.isNew()%><br>
        세션ID:<%=session.getId() %><br>
</body>
</html>