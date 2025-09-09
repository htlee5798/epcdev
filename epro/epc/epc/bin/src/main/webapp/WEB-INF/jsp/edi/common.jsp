
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
<script type="text/javascript" src="/js/jquery/jquery-1.7.2.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.blockUI.2.39.js"></script>
<script type="text/javascript" src="/js/epc/common.js"></script>
<script type="text/javascript" src="/js/epc/paging.js"></script>
<script type="text/javascript" src="/js/epc/edi/consult/common.js"></script>
 
<!-- json handleer 추가 2015.10.26 SONG MIN KYO -->
<script type="text/javascript" src="/js/jquery/jquery.handler.js"></script>

<!-- jquery templete 추가 2015.11.16 SONG MIN KYO -->
<script type="text/javascript" src="/js/jquery/jquery.tmpl.js"></script>

<script language="javascript" type="text/javascript" src="/js/common/json2.js"></script>
<!-- IBSheet Common JS Load -->
<script type="text/javascript" src='/js/epc/ibsheet/ibsheetinfo.js'></script>
<script type="text/javascript" src='/js/epc/ibsheet/ibsheet.js'></script>
<script type="text/javascript" >
$(document).ready(function(){
	 $("a").bind('click', function () {
		 if($(this).attr("href") == "#"){
		 	return false;
		 };
	 });	 
	 
	$(document).bind("contextmenu", function(e) {
		return false;
 	});
	 	
	$(document).bind('selectstart',function() {return false;}); 
 	$(document).bind('dragstart',function(){return false;}); 
});
</script>