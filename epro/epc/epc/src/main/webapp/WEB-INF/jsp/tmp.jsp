<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="html" uri="http://lcnjf.lcn.co.kr/taglib/edi"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>
	
	
	
	<!-- 
	<link rel="stylesheet" href="<c:url value="/css/edi/style.css"/>" />
	 -->
	 
	<script type="text/javascript" src="<c:url value="/js/jquery/jquery.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/js/common/Ui_common.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/js/common/common.js"/>"></script>

<script language="JavaScript">
     function addItem(){
          var frm = document.form1;
          var cnt = frm.sel1.options.length;
          var cnt2 = 0;

          for (var i=0 ; i < cnt ; i++){
               if(frm.sel1.options[i].selected == true){
                    frm.sel2.options.add(new Option(frm.sel1.options[i].text, frm.sel1.options[i].value));
                    cnt2++;
               }
          }

          for (var i=0 ; i < cnt2 ; i++){
               frm.sel1.options.remove(frm.sel1.selectedIndex);
          }
     }

     function removeItem(){
          var frm = document.form1;
          var cnt = frm.sel2.options.length;
          var cnt2 = 0;

          for (var i=0 ; i < cnt ; i++){
               if(frm.sel2.options[i].selected == true){
                    frm.sel1.options.add(new Option(frm.sel2.options[i].text, frm.sel2.options[i].value));
                    cnt2++;
               }
          }

          for (var i=0 ; i < cnt2 ; i++){
               frm.sel2.options.remove(frm.sel2.selectedIndex);
          }
     }
</script>



</head>

<body>

		
<form id="form1" name="form1" method="post" action="#">
<table border="0">
<tr>
	<td>
		<select name="sel1" size="10" style="width:100%" multiple>
          <option value="1">야근이 없다면 회사는?</option>
          <option value="2">덜덜덜</option>
          <option value="3">지름신의 압박!</option>
          <option value="4">헐</option>
          <option value="5">메롱</option>
          <option value="6">superkts.pe.kr</option>
          <option value="7">착한 것들</option>
          </select>
	</td>
	
	<td>
			<input type="button" value=" ▶ " onclick="addItem()">
			<br><br>
			<input type="button" value=" ◀ " onclick="removeItem()">
	</td>
	
	<td>
		<select name="sel2" size="10" style="width:100%" multiple></select>
	</td>
</tr>
</table>
</form>

</body>
</html>
