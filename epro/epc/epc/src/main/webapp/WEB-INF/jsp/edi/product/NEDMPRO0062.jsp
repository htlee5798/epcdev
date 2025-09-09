<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib 	prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LOTTE MART Back Office System</title>
<c:import url="/common/commonHead.do" />
<script type="text/javascript" >

$(document).ready(function(){
	var optionVal = $("#optionVal").val();
	var titleVal = "";

	if(optionVal == "05"){
		titleVal = "상품상세 정보";
	}else if(optionVal == "06"){
		titleVal = "상품 이미지";
	}

	$("#titleSpan1").text(titleVal);
	$("#titleSpan2").text(titleVal);

	initIBSheetGrid();
});

/* 그리드초기화 */
function initIBSheetGrid(){
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
		{Header:"순번",		Type:"Text",	SaveName:"NUM", 		Align:"Center",	Width:40,	Edit:0}
	  , {Header:"상품명",		Type:"Text",	SaveName:"PROD_NM", 	Align:"Left",  	Width:350,	Edit:0, Ellipsis:true}
	  , {Header:"이미지파일명",	Type:"Text",	SaveName:"IMG_NM", 	 	Align:"Center",	Width:290,	Edit:0}
	  , {Header:"상품코드",	Type:"Text",	SaveName:"PROD_CD", 								Hidden:true}
	];

	IBS_InitSheet(mySheet, ibdata);
	mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.1
	mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
}

/**********************************************************
 * 엑셀일괄업로드
 **********************************************************/
function doExcelInsert(){
	var form = document.batchPordPopUp;

	var fName = $('#createFile').val();
	if (fName.length < 3) {
		alert('업로드할 파일이 없습니다. 먼저 파일을 선택해 주세요.');
		return;
	}

	fName = fName.slice(fName.indexOf(".")+1).toLowerCase()

	if (fName != "xls") {
		alert("업로드는 엑셀파일(xls)만 가능 합니다.");
		$('#createFile').val("")
		return;
	}

	if (!confirm('엑셀 양식을 업로드 하시겠습니까?')) {
		return;
	}

	loadingMask();

	form.target = "frameForExcel";
	form.action = '<c:url value="/edi/product/batchProductExcelUpload.do"/>';
	form.submit();
}

function uploadReturn(rtnVal){
	$("#createFile").val("");

	if (rtnVal.msg != "") {
		alert(rtnVal.msg);
	} else {
		if (rtnVal.prodCdArr != null) {
			for (var i=0; i<rtnVal.prodCdArr.length; i++) {
				var rowIdx = mySheet.DataInsert(0);
				mySheet.SetCellValue(rowIdx, "NUM", i+1);
				mySheet.SetCellValue(rowIdx, "PROD_CD", rtnVal.prodCdArr[i]);
				mySheet.SetCellValue(rowIdx, "PROD_NM", rtnVal.prodNmArr[i]);
				mySheet.SetCellValue(rowIdx, "IMG_NM", rtnVal.imgNmArr[i]);
			}
		} else {
			alert("데이터가 존재하지 않거나 양식이 잘못 되었습니다.");
		}
	}

	hideLoadingMask();
}

/**********************************************************
 * 이미지일괄업로드
 **********************************************************/
function doImgInsert(){
	var form = document.imagePopUp;
	var fName = $('#createImgFile').val();
	if (fName.length < 3) {
		alert('업로드할 파일이 없습니다. 먼저 파일을 선택해 주세요.');
		return;
	}

	if (mySheet.RowCount() == 0){
		alert("엑셀 파일부터 업로드 하세요.");
		return;
	}

	fName = fName.slice(fName.indexOf(".")+1).toLowerCase()

	if (fName != "zip"){
		alert("이미지 업로드는 압축파일(zip)만 가능 합니다.");
		$('#createImgFile').val("")
		return;
	}

	if (!confirm('이미지파일을 업로드 하시겠습니까?')) {
		return;
	}

	loadingMask();

	form.target = "frameForImage";
	form.action = '<c:url value="/edi/product/batchProductImgZip.do"/>';
	form.submit();
}

function doImgUpload(){
	var udtUrl = '<c:url value="/edi/product/batchProductImgUpload.do"/>';
	var param = new Object();
	param.optionVal = $("#optionVal").val();
	param.entpCd = $("#entpCd").val();
	mySheet.DoSave(udtUrl, {Param:$.param(param), Col:0, Sync:2, Quest:0});
}

/**********************************************************
 * 이미지 검증 실패
 **********************************************************/
function doImgFail(rtnVal) {
	$('#createImgFile').val("");
	var msg = "";

	if (rtnVal.errCnt > 0){
		if(rtnVal.prodCdArr != null){
			$("#errCntTd").text(rtnVal.errCnt+" 건");
			msg = "<span style='font-size:13px; font-weight:bold;'>";
			for(var i=0; i<rtnVal.prodCdArr.length; i++){
				msg += "<span style='color:red;'>"+ rtnVal.prodCdArr[i] +"</span><br/>";
			}
		}
		$("#msgDiv").html(msg); 

	} else {
		alert("데이터가 존재하지 않거나 양식이 잘못 되었습니다.");
	}

	hideLoadingMask();
}

function mySheet_OnSaveEnd(Code, Msg) {
	$("#createImgFile").val("");
	var rtnMsg = JSON.parse(Msg.replaceAll("rtn",""));
	var msg = "<span style='font-size:15px; font-weight:bold;'>업로드 <span style='color:red;'>"+rtnMsg.upCnt+"</span> 건 중 <span style='color:red;'>"+rtnMsg.errCnt+"</span> 건 오류</span><br/><br/>" + rtnMsg.msg;

	$("#upCntTd").text(rtnMsg.upCnt+" 건");
	$("#sucCntTd").text(rtnMsg.sucCnt+" 건");
	$("#errCntTd").text(rtnMsg.errCnt+" 건");
	$("#msgDiv").html(msg);

	hideLoadingMask();
}
</script>
</head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<body>
	<div id="popup">
		<div class="popup_contents">
			<form name="batchPordPopUp" id="batchPordPopUp" method="post" enctype="multipart/form-data">
			<iframe name="frameForExcel" style="display:none;"></iframe>
			<input type="hidden" id="optionVal" name="optionVal" value="<c:out escapeXml='false' value='${param.optionVal}'/>" />
			<input type="hidden" id="entpCd" name="entpCd" value="<c:out escapeXml='false' value='${param.entpCd}'/>" />
			<div class="bbs_search3" style="margin-top:5px;">
				<ul class="tit">
					<li class="tit"><span id="titleSpan1"></span> 일괄 등록</li>
				</ul>
				<table class="bbs_list" cellspacing="0" border="0" style="color:red">
					<colgroup>
						<col width="20%">
						<col width="80%">
					</colgroup>
					<tr>
						<th>
							<span class="star">파일등록</span>
						</th>
						<td>
							<input type="file" name="createFile" id="createFile" style="width:450px;" value="파일이름"  />
							<a href="#" class="btn" onclick="javascript:doExcelInsert();"><span>업로드</span></a>
							<a href="#" class="btn" onclick="javascript:window.close();"><span>닫기</span></a>
						</td>
					</tr>
				</table>
				<table  cellpadding="0" cellspacing="1" border="0" width=100% bgcolor=efefef>
					<tr>
						<td colspan="4" bgcolor=ffffff>
							<strong>&nbsp;<font color="red">※ 엑셀양식 업로드 후 반영할 이미지 전체 zip 파일압축 업로드</font></strong>
						</td>
					</tr>
				</table>
			</div>
			<br/>
			<div class="bbs_search3">
				<ul class="tit">
					<li class="tit"><span id="titleSpan2"></span> 일괄 등록 업로드 결과</li>
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
			<iframe name="frameForImage" style="display:none;"></iframe>
			<div class="bbs_search3" style="margin-top:5px;">
				<ul class="tit">
					<li class="tit">이미지 업로드</li>
				</ul>
				<table class="bbs_list" cellspacing="0" border="0" style="color:red">
					<colgroup>
						<col width="20%">
						<col width="80%">
					</colgroup>
					<tr>
						<th>
							<span class="star">파일등록</span>
						</th>
						<td>
							<input type="file" name="createImgFile" id="createImgFile" style="width:500px;" value="파일이름"  />
							<a href="#" class="btn" onclick="javascript:doImgInsert();"><span>업로드</span></a>
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
							<div style="overflow-y:scroll; width:580px; height:150px; padding:10px;" id="msgDiv">
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