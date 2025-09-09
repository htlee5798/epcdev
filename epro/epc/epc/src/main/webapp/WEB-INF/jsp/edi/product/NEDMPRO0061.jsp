<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LOTTE MART Back Office System</title>
<c:import url="/common/commonHead.do" />
<script type="text/javascript">

$(document).ready(function(){
	var optionVal = $("#optionVal").val();
	var titleVal = "상품";

	if (optionVal == "02") {
		titleVal += "(옵션 有)";
	} else if (optionVal == "03") {
		titleVal = "전상법";
	} else if (optionVal == "04") {
		titleVal = "KC인증";
	}

	$("#titleSpan1").text(titleVal);
	$("#titleSpan2").text(titleVal);
});

/**********************************************************
 * 일괄업로드
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

	if (!confirm("엑셀 양식을 업로드 하시겠습니까?")) {
		return;
	}

	loadingMask();

	form.target = "frameForExcel";
	form.action = '<c:url value="/edi/product/batchProductExcelUpload.do"/>';
	form.submit();
}

var msg = "";
var keyNo = "";

function uploadReturn(rtnVal){
	$("#createFile").val("");

	msg = "";
	keyNo = rtnVal.keyNoErr;

	<%-- /*
	if($("#optionVal").val() == "02"){
		msg = "<span style='font-size:15px; font-weight:bold;'>기본정보 업로드 <span style='color:red;'>"+rtnVal.upCnt+"</span> 건 중 <span style='color:red;'>"+rtnVal.errCnt+"</span> 건 오류</span><br/>"
		       + "<span style='font-size:15px; font-weight:bold;'>단품옵션 업로드 <span style='color:red;'>"+rtnVal.optUpCnt+"</span> 건 중 <span style='color:red;'>"+rtnVal.optErrCnt+"</span> 건 오류</span><br/><br/>"
			   + "* 기본정보 정상등록 건수 : "+rtnVal.sucCnt+" 건<br/>"
		       + "* 기본정보 비정상 건수 : "+rtnVal.errCnt+" 건<br/>"
		       + "* 단품옵션 정상등록 건수 : "+rtnVal.optSucCnt+" 건<br/>"
		       + "* 단품옵션 비정상 건수 : "+rtnVal.optErrCnt+" 건<br/><br/>"
			   + rtnVal.msg;
	}else{
		msg = "<span style='font-size:15px; font-weight:bold;'>업로드 <span style='color:red;'>"+rtnVal.upCnt+"</span> 건 중 <span style='color:red;'>"+(Number(rtnVal.errCnt)+Number(rtnVal.optErrCnt))+"</span> 건 오류</span><br/><br/>"
		       + rtnVal.msg;
	}

	$("#upCntTd").text((Number(rtnVal.upCnt)+Number(rtnVal.optUpCnt))+" 건");
	$("#sucCntTd").text((Number(rtnVal.sucCnt)+Number(rtnVal.optSucCnt))+" 건");
	$("#errCntTd").text((Number(rtnVal.errCnt)+Number(rtnVal.optErrCnt))+" 건");
	*/ --%>

	//20180904 상품키워드 입력 기능 추가
	if ($("#optionVal").val() == "02") {
		if (rtnVal.upCnt == null && rtnVal.keywordUpCnt == null) {
			alert(rtnVal.msg);
			this.close(); 
		} else {
			msg = "<span style='font-size:15px; font-weight:bold;'>기본정보 업로드 <span style='color:red;'>"+rtnVal.upCnt+"</span> 건 중 <span style='color:red;'>"+rtnVal.errCnt+"</span> 건 오류</span><br/>"
				   + "<span style='font-size:15px; font-weight:bold;'>상품키워드 업로드 <span style='color:red;'>"+rtnVal.keywordUpCnt+"</span> 건 중 <span style='color:red;'>"+rtnVal.keywordErrCnt+"</span> 건 오류</span><br/>"
				   + "<span style='font-size:15px; font-weight:bold;'>단품옵션 업로드 <span style='color:red;'>"+rtnVal.optUpCnt+"</span> 건 중 <span style='color:red;'>"+rtnVal.optErrCnt+"</span> 건 오류</span><br/>"
				   + "<span style='font-size:15px; font-weight:bold;'>단품속성 업로드 <span style='color:red;'>"+rtnVal.attrUpCnt+"</span> 건 중 <span style='color:red;'>"+rtnVal.attrErrCnt+"</span> 건 오류</span><br/><br/>"
			       + "* 기본정보 정상등록 건수 : "+rtnVal.sucCnt+" 건<br/>"
			       + "* 기본정보 비정상 건수 : "+rtnVal.errCnt+" 건<br/>"
			       + "* 상품키워드 정상등록 건수 : "+rtnVal.keywordSucCnt+" 건<br/>"
			       + "* 상품키워드 비정상 건수 : "+rtnVal.keywordErrCnt+" 건<br/>"
			       + "* 단품옵션 정상등록 건수 : "+rtnVal.optSucCnt+" 건<br/>"
			       + "* 단품옵션 비정상 건수 : "+rtnVal.optErrCnt+" 건<br/>"
			       + "* 단품속성 정상등록 건수 : "+rtnVal.attrSucCnt+" 건<br/>"
			       + "* 단품속성 비정상 건수 : "+rtnVal.attrErrCnt+" 건<br/><br/>"
			       + "<span style='font-size:15px; color:red;'>"+rtnVal.msg+"</span><br/><br/>";
			$("#upCntTd").text("");
			$("#sucCntTd").text("");
			$("#errCntTd").text("");
			$("#upCntTd").text((Number(rtnVal.upCnt)+Number(rtnVal.keywordUpCnt)+Number(rtnVal.optUpCnt)+Number(rtnVal.attrUpCnt))+" 건");
			$("#sucCntTd").text((Number(rtnVal.sucCnt)+Number(rtnVal.keywordSucCnt)+Number(rtnVal.optSucCnt)+Number(rtnVal.attrSucCnt))+" 건");
			$("#errCntTd").text((Number(rtnVal.errCnt)+Number(rtnVal.keywordErrCnt)+Number(rtnVal.optErrCnt)+Number(rtnVal.attrErrCnt))+" 건");
		}
	} else {
		if (rtnVal.upCnt == null && rtnVal.keywordUpCnt == null) {
			alert(rtnVal.msg);
			this.close();
		} else {
			msg = "<span style='font-size:15px; font-weight:bold;'>기본정보 업로드 <span style='color:red;'>"+rtnVal.upCnt+"</span> 건 중 <span style='color:red;'>"+rtnVal.errCnt+"</span> 건 오류</span><br/>"
		           + "<span style='font-size:15px; font-weight:bold;'>상품키워드 업로드 <span style='color:red;'>"+rtnVal.keywordUpCnt+"</span> 건 중 <span style='color:red;'>"+rtnVal.keywordErrCnt+"</span> 건 오류</span><br/>"
		           + "<span style='font-size:15px; font-weight:bold;'>단품속성 업로드 <span style='color:red;'>"+rtnVal.attrUpCnt+"</span> 건 중 <span style='color:red;'>"+rtnVal.attrErrCnt+"</span> 건 오류</span><br/><br/>"
				   + "* 기본정보 정상등록 건수 : "+rtnVal.sucCnt+" 건<br/>"
		           + "* 기본정보 비정상 건수 : "+rtnVal.errCnt+" 건<br/>"
		           + "* 상품키워드 정상등록 건수 : "+rtnVal.keywordSucCnt+" 건<br/>"
		           + "* 상품키워드 비정상 건수 : "+rtnVal.keywordErrCnt+" 건<br/>"
			       + "* 단품속성 정상등록 건수 : "+rtnVal.attrSucCnt+" 건<br/>"
			       + "* 단품속성 비정상 건수 : "+rtnVal.attrErrCnt+" 건<br/><br/>"
			       + "<span style='font-size:15px; color:red;'>"+rtnVal.msg+"</span><br/><br/>";
			$("#upCntTd").text("");
			$("#sucCntTd").text("");
			$("#errCntTd").text("");
			$("#upCntTd").text((Number(rtnVal.upCnt)+Number(rtnVal.keywordUpCnt)+Number(rtnVal.attrUpCnt))+" 건");
			$("#sucCntTd").text((Number(rtnVal.sucCnt)+Number(rtnVal.keywordSucCnt)+Number(rtnVal.attrSucCnt))+" 건");
			$("#errCntTd").text((Number(rtnVal.errCnt)+Number(rtnVal.keywordErrCnt)+Number(rtnVal.attrErrCnt))+" 건");
		}
	}
	//20180904 상품키워드 입력 기능 추가

	$("#msgDiv").html(msg);
	if(keyNo.length > 0){
		for( var i = 0 ; i<keyNo.length ; i++)
			$("#msgDiv").append("<span style='font-size:15px; font-weight:bold;'>기준순번 <span style='color:red;'>"+keyNo[i]+"</span>번 오류</span><br/>");
	}

	hideLoadingMask();
}
</script>
</head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<body>
	<form name="batchPordPopUp" id="batchPordPopUp" method="post" enctype="multipart/form-data">
	<iframe name="frameForExcel" style="display:none;"></iframe>
	<input type="hidden" id="optionVal" name="optionVal" value="<c:out escapeXml='false' value='${param.optionVal}'/>" />
	<input type="hidden" id="entpCd" name="entpCd" value="<c:out escapeXml='false' value='${param.entpCd}'/>" />
		<div id="popup">
			<div class="popup_contents">
				<!-- 1검색조건 -->
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
				</div>
				<!-- 1검색조건 // -->
				※ EC 카테고리조회 / EC 카테고리별 속성 조회 메뉴에서 기본정보를 확인해주세요 
				<!-- 2검색내역 -->
				<div class="bbs_search3">
					<ul class="tit">
						<li class="tit"><span id="titleSpan2"></span> 일괄 등록 처리 결과</li>
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
							<td id="upCntTd"></td>
							<th>
								<span class="star">정상등록 건수</span>
							</th>
							<td id="sucCntTd"></td>
							<th>
								<span class="star">비정상 건수</span>
							</th>
							<td id="errCntTd"></td>
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
			</div>
		</div>
	</form>
</body>
</html>