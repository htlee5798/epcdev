<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib 	prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LOTTE MART Back Office System</title>
<c:import url="/common/commonHead.do" />
<!-- CSS URL -->
<link type="text/css" rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/style_1024.css" ></link>

<!-- JS URL -->
<script type="text/javascript" src="/js/epc/Ui_common.js" ></script>
<script type="text/javascript" src="/js/epc/common.js" ></script>
<script language="javascript">


window.onload = function() {

	// 20180917 송장업로드 결과 메시지 수정
	if('<c:out value="${message}"/>' != '') {
		var msg = '<c:out value="${message}"/>';
		msg = msg.replace("||","\n");
		alert(msg);
		top.opener.doSearch();
		self.close();	
	} else if('<c:out value="${resultStatus}"/>' == 'false' && '<c:out value="${errorStatus}"/>' == 'true' && '<c:out value="${message}"/>' == '') {
		alert("잘못된 양식이거나 송장업로드 양식에 입력된 상품 송장 정보를 다시 한번 확인부탁드립니다.");
		top.opener.doSearch();
		self.close();
	}
}


$(document).ready(function(){
	initIBSheetGrid();
});

/* 그리드초기화 */
function initIBSheetGrid() {
	// START of IBSheet Setting
	createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "400px");
	mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
	//mySheet.SetDataAutoTrim(false);

	var ibdata = {};
	// SizeMode: 
	ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral}; // 10 row씩 Load
	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.Cols = [
		{Header:"주문일자",	Type:"Text", SaveName:"ORD_DY",			Align:"Center", Width:100,  Edit:0 }
	  , {Header:"상품명",		Type:"Text", SaveName:"PROD_NM",		Align:"Left",   Width:200,  Edit:0, Ellipsis:true }
	  , {Header:"옵션",		Type:"Text", SaveName:"ITEM_OPT",		Align:"Left",   Width:100,  Edit:0 }
	  , {Header:"상품가격",	Type:"Int",  SaveName:"SALE_PRICE",		Align:"Right",  Width:100,  Edit:0 , Format:"Integer"}
	  , {Header:"주문수량",	Type:"Int",  SaveName:"ORD_CNT",		Align:"Right",  Width:100,  Edit:0 }
	  , {Header:"배송비",		Type:"Int",  SaveName:"DELI_AMT",		Align:"Right",  Width:100,  Edit:0 }
	  , {Header:"주문번호",	Type:"Text", SaveName:"ORDER_ID",		Align:"Center", Width:150,  Edit:0 }
	  , {Header:"배송지순번",	Type:"Text", SaveName:"DELIVERY_ID",	Align:"Center", Width:100,  Edit:0 }
	  , {Header:"협력업체ID",	Type:"Text", SaveName:"VEN_CD",			Align:"Center", Width:100,  Edit:0 }
	  , {Header:"운송장번호",	Type:"Text", SaveName:"INVOICE_NO",		Align:"Left",   Width:150,  Edit:0 }
	  , {Header:"추가송장번호",	Type:"Text", SaveName:"ADD_INVOICE_NO",	Align:"Left",   Width:150,  Edit:0 }
	  , {Header:"택배사코드",	Type:"Text", SaveName:"HODECO_CD",		Align:"Center", Width:100,  Edit:0 }
	  , {Header:"배송상태유형",	Type:"Text", SaveName:"DELI_STATUS_CD",	Align:"Center", Width:120,  Edit:0 }
	  , {Header:"성공여부",	Type:"Text", SaveName:"SUC_FLAG",		Align:"Center", Width:90,  Edit:0 }
	  , {Header:"실패사유",	Type:"Text", SaveName:"ERR_MSG",		Align:"Left",   Width:400,  Edit:0 }
	];

	IBS_InitSheet(mySheet, ibdata);
	mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.1
	mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
}

function doClose() {
	parent.opener.location.reload();
	window.close();
}

function excelUpload() {
	f = document.fileForm;

	if (f.excelupload.value == "" || f.excelupload.value == null) {
		alert("등록할 파일이 없습니다.");
		return;
	}

	//업로드 한글 체크
	if (calculateBytes(f.excelupload.value) == false) {
		alert("파일경로나 파일명은 한글을 지원하지 않습니다");
		return;
	}

	if (confirm("선택된 첨부파일을 추가 하시겠습니까?")) {
		f.target = "_self";
			f.action = "<c:url value="/common/upload.do"/>";
		f.submit();
	} else {
		return;
	}
}

function excelUploadAjax() {
	
	var f = document.fileForm;

	if (f.excelupload.value == "" || f.excelupload.value == null) {
		alert("등록할 파일이 없습니다.");
		return;
	}

	//업로드 한글 체크
	if (calculateBytes(f.excelupload.value) == false) {
		alert("파일경로나 파일명은 한글을 지원하지 않습니다");
		return;
	}

	if (!confirm("선택된 첨부파일을 추가 하시겠습니까?")) {
		return;
	}
	loadingMask();

	var data = new FormData(f);
	$.ajax({
		type: "POST",
		enctype: 'multipart/form-data',
		url: "<c:url value="/common/upload.do"/>",
		data: data,
		processData: false,
		contentType: false,
		cache: false,
		timeout: 600000,
		success: function (data) {
			//$('#btnUpload').prop('disabled', false);
			$("#excelupload").val("");
			if (data.result) {
				mySheet.LoadSearchData(data.ibsList);
			}
			if (data.errCnt > 0) {
				excelErrCnt = data.errCnt;
				//alert("필수값이 입력 안되었거나 양식이 올바르지 않은 입력건이 있습니다.\n\n엑셀파일 내용 확인/수정 후 업로드해주세요.");
			} else if (data.errCnt < 0) {
				excelErrCnt = data.errCnt;
				alert(data.msg);
			} else {
				excelErrCnt = 0;
			}
			hideLoadingMask();
		},
		error: function (e) {
			// $('#btnUpload').prop('disabled', false);
			alert("엑셀 업로드 중 오류가 발생하였습니다.");
			hideLoadingMask();
		}
	});
}

//업로드 한글 체크
function calculateBytes(val) {
	var tcount = 0;

	var temp = val.length;

	var onechar;
	for (k=0; k<temp; k++ ) {
		onechar = val.charAt(k) ;
		if (escape(onechar).length > 4) {
			return false;
		}
	}

	return true;
}

</script>
</head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
	<form name="fileForm" id="fileForm" method="post" enctype="multipart/form-data" >
		<div id="popup">
			<div id="p_title1">
				<h1>송장업로드</h1>
				<span class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
			</div>
			<br/>
			<div class="popup_contents">
				<!-- 1검색조건 -->
				<div class="bbs_search3">
					<ul class="tit">
						<li class="btn">
							<a href="#" class="btn" onclick="javascript:excelUploadAjax();"><span>업로드</span></a>
						</li>
					</ul>
					<table class="bbs_grid2" cellspacing="0" border="0">
						<colgroup>
							<col width="10%">
							<col width="90%">
						</colgroup>
						<tr>
							<th>엑셀파일 업로드</th>
							<td><input type="file" name="excelupload" size="50"></td>
						</tr>
					</table>
				</div>
				<!-- 	1검색조건 // -->
				<br/>
				<div class="bbs_search3">
					<ul class="tit">
						<li class="tit">처리 결과</li>
					</ul>
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col width="100%">
					</colgroup>
					<tr>
						<td>
							<div id="ibsheet1"></div>
						</td>
					</tr>
				</table>
				</div>
			</div>
		</div>
	</form>
</body>
</html>