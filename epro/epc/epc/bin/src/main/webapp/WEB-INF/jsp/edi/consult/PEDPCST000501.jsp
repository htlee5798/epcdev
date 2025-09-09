<%@include file="../common.jsp" %>

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

function forwardValue(){
	 var frm = document.form1;

	 var cnt = frm.sel2.options.length;
	 var val = "";



	 for (var i=0 ; i < cnt ; i++){
           val += frm.sel2.options[i].value + "-" ;
   }
	 opener.receiveVen(val);
	 
	 window.close();
}

</script>

</head>




<body onLoad="window.focus();">
<form id="form1" name="form1" method="post" action="#">
<input type="hidden" name="bman" value="${paramMap.bman }" />


<div id="popup">
    <!------------------------------------------------------------------ -->
    <!--    title -->
    <!------------------------------------------------------------------ -->
    <div id="p_title1">
        <h1>점포선택</h1>
        <span class="logo"><img src="/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
    </div>
    <!-------------------------------------------------- end of title -- -->
    
    <!------------------------------------------------------------------ -->
    <!--    process -->
    <!------------------------------------------------------------------ -->
    <!--    process 없음 -->
    <br>
    <!------------------------------------------------ end of process -- -->
	<div class="popup_contents">

	<!------------------------------------------------------------------ -->
	<!-- 	검색조건 -->
	<!------------------------------------------------------------------ -->
	<div class="bbs_search">
		<ul class="tit">
			<li class="tit">검색할 점포를 선택하세요.</li>
			<li class="btn">
				<a href="javascript:forwardValue();"  class="btn"><span><spring:message code="button.common.choice"/></span></a>
                
			</li>
		</ul>
	</div>
	
	<!----------------------------------------------------- end of 검색조건 -->
				
	<!-- -------------------------------------------------------- -->
	<!--	검색내역 	-->
	<!-- -------------------------------------------------------- -->
	<div class="wrap_con">
		<div class="bbs_list">
			<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
				<c:if test="${not empty venList }">
					<tr>
						<td align=center>
							<select name="sel1" style="width:150px; height:175px;" multiple>
								<c:forEach items="${venList}" var="list" varStatus="index" >
									<option value="${list.VENDOR_ID }">${list.VENDOR_ID }</option>
								</c:forEach>
		          			</select>
						</td>
						
						<td align=center width=50>
								<input type="button" value=" ▶ " onclick="addItem()">
								<br><br>
								<input type="button" value=" ◀ " onclick="removeItem()">
						</td>
						
						<td align=center>
							<select name="sel2" size="12" style="width:150px; height:175px;" multiple></select>
						</td>
					</tr>
				</c:if>
				<c:if test="${empty venList }">
					<tr><td>데이타가 없습니다.</td></tr>
				</c:if>
			</table>	
			
				
		</div>
	</div>
	<!-----------------------------------------------end of 검색내역  -->
    </div><!-- class popup_contents// -->
    
    <br/>

	<!-- -------------------------------------------------------- -->
	<!--    footer  -->
	<!-- -------------------------------------------------------- -->
	<div id="footer">
	    <div id="footbox">
	        <div class="msg" id="resultMsg"></div>
	    </div>
	</div>
	<!---------------------------------------------end of footer -->

</div><!-- id popup// -->
	
	
</form>	
				
</body>
</html>
