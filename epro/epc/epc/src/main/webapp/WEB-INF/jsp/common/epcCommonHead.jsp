<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<title><spring:message code="title.epc"/></title>
<link type="text/css" rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/style_1024.css" ></link>
<script type="text/javascript" src="/js/jquery/jquery-1.7.2.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.form.js"></script>
<script type="text/javascript" src="/js/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.validate.1.8.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.metadata.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.custom.js"></script>
<script type="text/javascript" src="/js/epc/Ui_common.js"></script>
<script type="text/javascript" src="/js/epc/common.js"></script>
<script type="text/javascript" src="/js/epc/paging.js" ></script>
<script type="text/javascript" src="/js/epc/validate.js" ></script>
<script type="text/javascript" src="/js/epc/member.js" ></script>
<script type="text/javascript" src="/js/common/utils.js" ></script>
<!-- IBSheet Common JS Load -->
<!-- <script type="text/javascript" src='/epc/ibsheet/ibsheetinfo.js'></script> -->
<!-- <script type="text/javascript" src='/epc/ibsheet/ibsheet.js'></script> -->

<script type="text/javascript" src="/js/jquery/jquery.blockUI.2.39.js"></script>
<!-- jquery handler -->
<script type="text/javascript" src="/js/jquery/jquery.handler.js"></script>

<!-- jqGrid -->
<link type="text/css" rel="stylesheet" href="/js/jquery/css/jquery-ui-1.11.4.css" ></link>
<link type="text/css" rel="stylesheet" href="/js/epc/jqGrid/css/ui.jqgrid.css" ></link>
<script type="text/javascript" src='/js/epc/jqGrid/js/i18n/grid.locale-${pageContext.response.locale}.js'></script>
<script type="text/javascript" src='/js/epc/jqGrid/js/jquery.jqGrid.min.js'></script>
<script type="text/javascript" src='/js/epc/jqGrid/src/grid.common.js'></script>


<!-- NamoCross(2016-01-15) JS Load -->
<%-- <script type="text/javascript" src='<c:url value="/namoCross/js/namo_scripteditor.js"/>'></script> --%>
<script type="text/javascript" src="${lfn:getString('namo.link.path')}"></script>
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

$(window).on("load resize", function(e) {
	if ( typeof mySheet != 'undefined') {
		setTimeout(function() {
			if ( mySheet.GetSheetWidth() > 1200 ) {
				mySheet.FitColWidth();
				if ( typeof mySheet2 != 'undefined') mySheet2.FitColWidth();
			}
		}, 100);
	};
});
</script>
