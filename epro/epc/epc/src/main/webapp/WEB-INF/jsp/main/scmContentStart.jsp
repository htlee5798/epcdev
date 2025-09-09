<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>LOTTE MART Back Office System</title>
<link rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/epc/mdi.css" />
<script type="text/javascript" src="/js/jquery/jquery-1.6.1.js"></script>
<script type="text/javascript" src="/js/epc/Ui_common.js"></script>
<script type="text/javascript" src="/js/epc/tdi.tabs.js"></script>
<script type="text/javascript">

$(document).ready(function(){
	//탭관련 추가
	$("#adddivtab").dynatabs({
		tabBodyID : 'adddivtabbody',
		showCloseBtn : true,
		confirmDelete : true
	});

	<c:if test="${ param.initUrl != null && param.initUrl != '' &&  param.initTitle == '1'}">
	clickTabMenu( '<c:url value="${ param.initUrl}"/>',  '해피콜관리');
	</c:if>
	<c:if test="${ param.initUrl != null && param.initUrl != '' &&  param.initTitle == '2'}">
	clickTabMenu( '<c:url value="${ param.initUrl}"/>',  '인터넷상품관리');
	</c:if>
	<c:if test="${ param.initUrl != null && param.initUrl != '' &&  param.initTitle == '3'}">
	clickTabMenu( '<c:url value="${ param.initUrl}"/>',  '업체정보관리');
	</c:if>
}); // end of ready

//
function clickTabMenu(url, menuNm) {

	menuNm = menuNm.replace( /(\s*)/g, ""); //공백만 제거함

	//url에 파라미터를 포함할 경우 파라미터 포함 여부 파라미터를 추가한다.(권한체크 인터셉터에서 url로 권한체크시에 중복 제거 위해)
	if( url.indexOf("?") != -1){
		url = url + "&menuParam="+encodeURIComponent( url.substring( url.indexOf("?")));
	}

	var isOpened = false;

	$("#adddivtab").find("a").each(function( index ) {
		if( $( this ).attr("title") == menuNm){
			$( this).trigger("click");
			isOpened = true;
		}
	});

	//이미 열려있는 탭일 경우에는 리턴
	if (isOpened) {
		return;
	}

	if( $("#adddivtab").find("a").length >= 6){
		alert("탭의 최대수는 6개입니다. 탭을 닫으십시오");
		return;
	}

	$.addDynaTab({
		tabID : 'adddivtab',
		type : 'ajax',
		url : url,
		method : 'get',
		dtype : 'html',
		params : {},
		tabTitle : menuNm
	});
}

// Top에서 인터넷상품 검색시 사용
function clickTabMenu2(url, menuNm, gubun) {

	menuNm = menuNm.replace( /(\s*)/g, ""); //공백만 제거함

	//url에 파라미터를 포함할 경우 파라미터 포함 여부 파라미터를 추가한다.(권한체크 인터셉터에서 url로 권한체크시에 중복 제거 위해)
	if (url.indexOf("?") != -1) {
		url = url + "&menuParam="+encodeURIComponent( url.substring( url.indexOf("?")));
	}

	var isOpened = false;

	$("#adddivtab").find("a").each(function( index ) {
		if( $( this ).attr("title") == menuNm){
			$( this).trigger("click");
			isOpened = true;
		}
	});

	//이미 열려있는 탭일 경우에는 리턴
	if (isOpened) {
		if (gubun == "gnbSearch"){
			document.getElementById('iframe_인터넷상품관리').src = url;
			return;
		} else if( gubun == "exhSearch" ){
			document.getElementById('iframe_기획전등록').src = url;
			return;
		} else {
			return;
		}
		
	}

	if ($("#adddivtab").find("a").length >= 6) {
	    alert("탭의 최대수는 6개입니다. 탭을 닫으십시오");
	    return;
	}

    $.addDynaTab({
		tabID : 'adddivtab',
		type : 'ajax',
		url : url,
		method : 'get',
		dtype : 'html',
		params : {},
		tabTitle : menuNm
	});
}
</script>
</head>
<body>
<div class="wrap" id="content_wrap">
	<!-- Content box -->
    <div class="content_box" id="wrap_menu"> 
    	<!-- content right -->
    	<div id="adddivtabdiv">
    		<!-- Tab Headers Start -->
          	<ul class="tabs" id="adddivtab">
          	</ul>
          	<!-- Tab Headers End -->
          	<div class="tabcontents" id="adddivtabbody">
          	</div> 
    	</div>
    </div>
</div>
</body>
</html>