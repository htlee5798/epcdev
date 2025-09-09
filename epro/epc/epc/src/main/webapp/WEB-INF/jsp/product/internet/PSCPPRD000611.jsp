<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="epcutl" uri="/WEB-INF/tlds/epcutl.tld" %>
<%@page import="com.lottemart.epc.common.util.SecureUtil"%>
<%
String imgType = request.getParameter("imgType");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LOTTE MART Back Office System</title>
<c:import url="/common/commonHeadPopup.do" />
<script type="text/javascript" >

$(document).ready(function() {
	//input enter 막기
	$("*").keypress(function(e) {
		if (e.keyCode==13) return false;
	});
	$("#close").click(function() {
		top.close();
	});

	var imgType = $("#imgType").val();
	var titleVal = "";
	
	if (imgType == "1") {
		titleVal = "상품 이미지";
	} else if (imgType == "2") {
		titleVal = "상품상세 정보";
	}

	$("#titleSpan1").text(titleVal);
	$("#titleSpan2").text(titleVal);

	initIBSheetGrid();
});

/* 그리드초기화 */
function initIBSheetGrid() {
	// START of IBSheet Setting
	createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "200px");
	mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
	mySheet.SetDataAutoTrim(false);

	var ibdata = {};
	// SizeMode: 
	ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral}; // 10 row씩 Load
	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.Cols = [
		{Header:"상품코드",		Type:"Text", SaveName:"PROD_CD",  Align:"Left",  Width:150,  Edit:0 }
	  , {Header:"상품명",			Type:"Text", SaveName:"PROD_NM",  Align:"Left",  Width:300,  Edit:0, Ellipsis:true }
	  , {Header:"상품별 이미지순번",	Type:"Text", SaveName:"IMG_SEQ",  Align:"Left",  Width:100,  Edit:0 }
	  , {Header:"이미지파일명",		Type:"Text", SaveName:"IMG_NM",   Align:"Left",  Width:400,  Edit:0 }
	  , {Header:"오류내용",		Type:"Text", SaveName:"ERR_MSG",  Align:"Left",  Width:300,  Edit:0 }
	];

	IBS_InitSheet(mySheet, ibdata);
	mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.1
	mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
}

/**********************************************************
 * 엑셀일괄업로드
 **********************************************************/
/* function doExcelInsert() {

	var form = document.batchPordPopUp;

	var fName = $("#createFile").val();
	if (fName.length < 3) {
		alert("업로드할 파일이 없습니다. 먼저 파일을 선택해 주세요.");
		return;
	}

	fName = fName.slice(fName.indexOf(".")+1).toLowerCase();

	if (fName != "xls") {
		alert("업로드는 엑셀파일(xls)만 가능 합니다.");
		$("#createFile").val("")
		return;
	}

	if (!confirm("엑셀 양식을 업로드 하시겠습니까?")) {
		return;
	}

	loadingMask();

	form.target = "frameForExcel";
	form.action = '<c:url value="/product/batchProductExcelUpload.do"/>';
	form.submit();
} */

var excelErrCnt = 0;

function doExcelInsertAjax() {
	
	var form = document.batchPordPopUp;

	var fName = $("#createFile").val();
	if (fName.length < 3) {
		alert("업로드할 파일이 없습니다. 먼저 파일을 선택해 주세요.");
		return;
	}

	fName = fName.slice(fName.indexOf(".")+1).toLowerCase();

	if (fName != "xls") {
		alert("업로드는 엑셀파일(xls)만 가능 합니다.");
		$("#createFile").val("")
		return;
	}

	if (!confirm("엑셀 양식을 업로드 하시겠습니까?")) {
		return;
	}
	loadingMask();

	var data = new FormData(form);
	$.ajax({
		type: "POST",
		enctype: 'multipart/form-data',
		url: "<c:url value='/product/batchProductExcelUpload.do'/>",
		data: data,
		processData: false,
		contentType: false,
		cache: false,
		timeout: 600000,
		success: function (data) {
			//$('#btnUpload').prop('disabled', false);
			$("#createFile").val("");
			if (data.result) {
				mySheet.LoadSearchData(data.ibsList);
			}
			if (data.errCnt > 0) {
				excelErrCnt = data.errCnt;
				alert("필수값이 입력 안되었거나 양식이 올바르지 않은 입력건이 있습니다.\n\n엑셀파일 내용 확인/수정 후 업로드해주세요.");
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

/* function uploadReturn(rtnVal) {

	$("#createFile").val("");

	if (rtnVal.msg != "") {
		alert(rtnVal.msg);
	} else {
		if (rtnVal.prodCdArr != null) {
			for (var i = 0; i < rtnVal.prodCdArr.length; i++) {
				var rowIdx = mySheet.DataInsert(0);
				mySheet.SetCellValue(rowIdx, "NUM", i+1);
				mySheet.SetCellValue(rowIdx, "PROD_CD", rtnVal.prodCdArr[i]);
				mySheet.SetCellValue(rowIdx, "PROD_NM", rtnVal.prodNmArr[i]);
				mySheet.SetCellValue(rowIdx, "IMG_SEQ", rtnVal.imgSeqArr[i]);
				mySheet.SetCellValue(rowIdx, "IMG_NM", rtnVal.imgNmArr[i]);
			}
		} else {
			alert("데이터가 존재하지 않거나 양식이 잘못 되었습니다.");
		}
	}
	hideLoadingMask();
} */

/**********************************************************
 * 이미지일괄업로드
 **********************************************************/
/* function doImgInsert() {

	var form = document.imagePopUp;

	var fName = $("#createImgFile").val();
	if (fName.length < 3) {
		alert("업로드할 파일이 없습니다. 먼저 파일을 선택해 주세요.");
		return;
	}

	if (mySheet.RowCount() == 0) {
		alert("엑셀 파일부터 업로드 하세요.");
		return;
	}

	fName = fName.slice(fName.indexOf(".")+1).toLowerCase()
	if (fName != "zip") {
		alert("이미지 업로드는 압축파일(zip)만 가능 합니다.");
		$("#createImgFile").val("")
		return;
	}

	if (!confirm("이미지파일을 업로드 하시겠습니까?")) {
		return;
	}
	loadingMask();

	form.target = "frameForImage";
	form.action = '<c:url value="/product/batchProductImgZip.do"/>';
	form.submit();
} */

function doImgInsertAjax() {

	var form = document.imagePopUp;

	if (mySheet.RowCount() == 0) {
		alert("엑셀 파일을 먼저 업로드해주세요.");
		return;
	}

	if (excelErrCnt != 0) {
		alert("업로든된 엑셀파일이 올바르지 않습니다.\n\n올바른 양식의 엑셀 파일을 업로드 후에 이미지를 업로드해주세요.");
		return;
	}

	var fName = $("#createImgFile").val();
	if (fName.length < 3) {
		alert("업로드할 파일이 없습니다. 먼저 파일을 선택해 주세요.");
		return;
	}

	fName = fName.slice(fName.indexOf(".")+1).toLowerCase()
	if (fName != "zip") {
		alert("이미지 업로드는 압축파일(zip)만 가능 합니다.");
		$("#createImgFile").val("")
		return;
	}

	if (!confirm("이미지파일을 업로드 하시겠습니까?")) {
		return;
	}
	loadingMask();

	var data = new FormData(form);
	var listSize = mySheet.GetTotalRows();
	var param = [];
	for (var i = 1; i <= listSize; i++) {
		data.append("prodCd", mySheet.GetCellValue(i, "PROD_CD"));
		data.append("prodNm", mySheet.GetCellValue(i, "PROD_NM"));
		data.append("imgSeq", mySheet.GetCellValue(i, "IMG_SEQ"));
		data.append("imgNm", mySheet.GetCellValue(i, "IMG_NM"));
	}

	$.ajax({
		type: "POST",
		enctype: 'multipart/form-data',
		url: "<c:url value='/product/batchProductImgZip.do'/>",
		data: data,
		processData: false,
		contentType: false,
		cache: false,
		timeout: 600000,
		success: function (data) {
			console.log("return data :: " + JSON.stringify(data));
			//$('#btnUpload').prop('disabled', false);
			$("#createImgFile").val("");

			var rtnMsg = data;

			if (rtnMsg.sucFlag == "SUCC") {
				if (rtnMsg.errCnt == 0) {
					alert("파일업로드 및 이미지 수정요청에 성공하였습니다.");
				} else {
					alert("파일업로드 및 이미지 수정요청 성공 " + rtnMsg.sucCnt + "건, 실패 " + rtnMsg.errCnt + " 건 입니다.");
				}
			} else {
				alert("zip파일을 확인 후 다시 업로드해주세요.");
			}

			var up_cnt = rtnMsg.upCnt == undefined ? "" : rtnMsg.upCnt;
			var suc_cnt = rtnMsg.sucCnt == undefined ? "" : rtnMsg.sucCnt;
			var err_cnt = rtnMsg.errCnt == undefined ? "" : rtnMsg.errCnt;
			$("#upCntTd").text(up_cnt + " 건");
			$("#sucCntTd").text(suc_cnt + " 건");
			$("#errCntTd").text(err_cnt + " 건");

			var errMsgs = "";
			if (rtnMsg.errMsg != undefined && rtnMsg.errMsg.length > 0) {
				for (var i = 0; i < rtnMsg.errMsg.length; i++) {
					errMsgs += rtnMsg.errMsg[i] + "<br/>";
				}
			}
			var msg = "<span style='font-size:15px; font-weight:bold;'>"
			+ "업로드 <span style='color:red;'>" + up_cnt + "</span> 건 중 "
			+ "<span style='color:red;'>" + err_cnt + "</span> 건 오류</span><br/><br/>" + errMsgs;
			$("#msgDiv").html(msg);

			hideLoadingMask();
		},
		error: function (e) {
			console.log("error msg :: " + e);
			// $('#btnUpload').prop('disabled', false);
			alert("처리 중 오류가 발생하였습니다.");
			hideLoadingMask();
		}
	});

}

/* function doImgUpload() {
	var udtUrl = '<c:url value="/edi/product/batchProductImgUpload.do"/>';

	var param = new Object();
	param.imgType = $("#imgType").val();
	param.vendorId = $("#vendorId").val();

	mySheet.DoSave(udtUrl, {Param:$.param(param), Col:0, Sync:2, Quest:0});
} */

/**********************************************************
 * 이미지 검증 실패
 **********************************************************/
/* function doImgFail(rtnVal) {
	$("#createImgFile").val("");
	var msg = "";

	if (rtnVal.errCnt > 0) {
		if (rtnVal.prodCdArr != null) {
			$("#errCntTd").text(rtnVal.errCnt+" 건");
			msg = "<span style='font-size:13px; font-weight:bold;'>";

			for (var i = 0; i < rtnVal.prodCdArr.length; i++) {
				msg += "<span style='color:red;'>"+ rtnVal.prodCdArr[i] +"</span><br/>";
			}
		}
		$("#msgDiv").html(msg);

	} else {
		alert("데이터가 존재하지 않거나 양식이 잘못 되었습니다.");
	}

	hideLoadingMask();
} */

/* function mySheet_OnSaveEnd(Code, Msg) {
	$("#createImgFile").val("");

	var rtnMsg = JSON.parse(Msg.replaceAll("rtn",""));
	var msg = "<span style='font-size:15px; font-weight:bold;'>"
	+ "업로드 <span style='color:red;'>" + rtnMsg.upCnt + "</span> 건 중 "
	+ "<span style='color:red;'>" + rtnMsg.errCnt + "</span> 건 오류</span><br/><br/>" + rtnMsg.msg;

	$("#upCntTd").text(rtnMsg.upCnt+" 건");
	$("#sucCntTd").text(rtnMsg.sucCnt+" 건");
	$("#errCntTd").text(rtnMsg.errCnt+" 건");
	$("#msgDiv").html(msg);

	hideLoadingMask();
} */
</script>
</head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<body>
<div id="popup">
	<div class="popup_contents">
		<form name="batchPordPopUp" id="batchPordPopUp" method="post" enctype="multipart/form-data">
		<iframe name="frameForExcel" style="display:none;"></iframe>
		<input type="hidden" id="imgType" name="imgType" value="<c:out escapeXml='false' value='${param.imgType}'/>" />
		<input type="hidden" id="vendorId" name="vendorId" value="<c:out escapeXml='false' value='${param.vendorId}'/>" />
		<div class="bbs_search3" style="margin-top:5px;">
			<ul class="tit">
				<li class="tit"><span id="titleSpan1"></span> 일괄 등록</li>
			</ul>
			<table class="bbs_list" cellspacing="0" border="0" style="color:red">
				<colgroup>
					<col width="10%">
					<col width="60%">
					<col width="10%">
					<col width="20%">
				</colgroup>
				<tr>
					<th>
						<span class="star">파일등록</span>
					</th>
					<td>
						<input type="file" name="createFile" id="createFile" style="width:450px;" value="파일이름" />
						<a href="#" class="btn" onclick="javascript:doExcelInsertAjax();"><span>업로드</span></a>
						<a href="#" class="btn" onclick="javascript:window.close();"><span>닫기</span></a>
					</td>
					<th>협렵업체코드</th>
					<td>${param.vendorId}</td>
				</tr>
			</table>
			<table cellpadding="0" cellspacing="1" border="0" width="100%" bgcolor="#EFEFEF">
				<tr>
					<td colspan="4" bgcolor="#FFFFFF">
						<strong>&nbsp;<font color="red">※ 엑셀양식(xls) 업로드 후 수정할 이미지 전체  zip파일압축 업로드</font></strong><br/>
						<strong>&nbsp;<font color="red">※ 엑셀양식 업로드 요청 목록은 20개로 제한됩니다.</font></strong>
					</td>
				</tr>
			</table>
		</div>
		<div class="bbs_search3">
			<ul class="tit">
				<li class="tit"><span id="titleSpan2"></span> 일괄 수정 업로드 결과</li>
			</ul>
			<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
				<tr>
 					<td><div id="ibsheet1"></div></td>
				</tr>
			</table>
		</div>
		</form>
		<br/>
		<form name="imagePopUp" id="imagePopUp" method="post" enctype="multipart/form-data">
		<input type="hidden" id="imgType" name="imgType" value="<c:out escapeXml='false' value='${param.imgType}'/>" />
		<input type="hidden" id="vendorId" name="vendorId" value="<c:out escapeXml='false' value='${param.vendorId}'/>" />
		<iframe name="frameForImage" style="display:none;"></iframe>
		<div class="bbs_search3" style="margin-top:5px;">
			<ul class="tit">
				<li class="tit">이미지 업로드</li>
			</ul>
			<table class="bbs_list" cellspacing="0" border="0" style="color:red">
				<colgroup>
					<col width="10%">
					<col width="60%">
					<col width="10%">
					<col width="20%">
				</colgroup>
				<tr>
					<th>
						<span class="star">파일등록</span>
					</th>
					<td>
						<input type="file" name="createImgFile" id="createImgFile" style="width:500px;" value="파일이름" />
						<a href="#" class="btn" onclick="javascript:doImgInsertAjax();"><span>업로드</span></a>
					</td>
					<th>협렵업체코드</th>
					<td>${param.vendorId}</td>
				</tr>
			</table>
			<table cellpadding="0" cellspacing="1" border="0" width="100%" bgcolor="#EFEFEF">
				<tr>
					<td colspan="4" bgcolor="#FFFFFF">
						<strong>&nbsp;<font color="red">※ 업로드시 zip파일 용량 10MB(10,485,760 Byte) 이하, 파일 내 이미지갯수 20개 입니다.</font></strong><br/>
						<% if("2".equals(imgType)) { %><strong>&nbsp;<font color="red">※ zip파일 내 상세이미지 1개당 5MB(5,242,880 Byte)까지 가능합니다.</font></strong><% } %>
					</td>
				</tr>
			</table>
		</div>
		<br/>
		<div class="bbs_search3">
			<ul class="tit">
				<li class="tit">이미지 업로드 처리 결과</li>
			</ul>
			<table class="bbs_list" cellspacing="0" border="0">
				<colgroup>
					<col width="15%">
					<col width="19%">
					<col width="15%">
					<col width="19%">
					<col width="15%">
					<col width="19%">
				</colgroup>
				<tr>
					<th>
						<span class="star">전체 업로드 건수</span>
					</th>
					<td id="upCntTd">
					</td>
					<th>
						<span class="star">정상등록 건수</span>
					</th>
					<td id="sucCntTd">
					</td>
					<th>
						<span class="star">비정상 건수</span>
					</th>
					<td id="errCntTd">
					</td>
				</tr>
				<tr>
					<th>
						<span class="star">등록 결과</span>
					</th>
					<td colspan="5" style="text-align:left;">
						<div style="overflow-y:scroll; width:1000px; height:300px; padding:10px;" id="msgDiv">
						</div>
					</td>
				</tr>
			</table>
		</div>
		</form>
	</div>
</div>
</body>
</html>