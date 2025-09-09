<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="html" uri="http://lcnjf.lcn.co.kr/taglib/edi"  %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"   uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<link rel="stylesheet" href="/css/epc/edi/style_1024.css" />
<script type="text/javascript" src="/js/epc/Ui_common.js"></script>
<script type="text/javascript" src="/js/jquery/jquery-1.6.1.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.blockUI.2.39.js"></script>
<script type="text/javascript" src="/js/epc/common.js"></script>
<script type="text/javascript" src="/js/epc/paging.js"></script>
<script type="text/javascript" src="/js/epc/edi/consult/common.js"></script>

<script language="javascript" type="text/javascript" src="/js/common/json2.js"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>
	
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
    	 var val2= "";

    	 for (var i=0 ; i < cnt ; i++){
                 val += frm.sel2.options[i].value + "-" ;
                 val2 += frm.sel2.options[i].text + "," ;
         }

         val2 = val2.substring(0, val2.length-1);

    	 opener.searchForm.storeVal.value = val;
    	 opener.searchForm.storeName.value = val2;

		 window.close();
     }

     function fam(){
    	 
    	 document.form1.storeVal.value = opener.searchForm.storeVal.value;

         var frm = document.form1;
         var cnt = frm.sel1.options.length;
         var cnt2 = 0;
         
         var tmp = "";
         var tmp2=opener.searchForm.storeVal.value;

		 var optionIDX="";
		 var optionVAL="";

         if(tmp2 != ""){
        	 tmp=tmp2.split("-");

        	 for(var i=0 ;i<tmp.length-1;i++){
                 for(var j=0;j<cnt;j++){
                	 if(frm.sel1.options[j].value == tmp[i]){
                		 frm.sel2.options.add(new Option(frm.sel1.options[j].text, frm.sel1.options[j].value));
                		 optionIDX += j + ";";
                	 }
                 }
             }  

             optionVAL = optionIDX.split(";");

             for(var i=0;i<optionVAL.length-1;i++){
            	 frm.sel1.options.remove(optionVAL[i]);
             }
         }
     }     

</script>

</head>




<body onload="javascript:fam();">
<form id="form1" name="form1" method="post" action="#">
<input type="hidden" id="storeVal" name="storeVal"  />

<input type="hidden" name="storeName" />


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
				<c:if test="${not empty storeList }">
					<tr>
						<td align=center>
							<select name="sel1" style="width:150px; height:175px;" multiple>
								<c:forEach items="${storeList}" var="list" varStatus="index" >
						          <option value="${list.STR_CD }">${list.STR_NM }</option>
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
				<c:if test="${empty storeList }">
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
