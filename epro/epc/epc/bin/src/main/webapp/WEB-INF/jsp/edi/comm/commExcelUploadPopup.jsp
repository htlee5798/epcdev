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
	var excelSysKnd = "${excelSysKnd}"; 	//메뉴 구분 
	var entpCd = "${entpCd}"				//파트너사코드 
	
	 $("#excelSysKnd").val(excelSysKnd);
	 $("#entpCd").val(entpCd);
});

/**********************************************************
 * 일괄업로드
 **********************************************************/
// 엑셀 업로드 버튼 
function doExcelInsert() {
    var formData = new FormData();
    var fileInput = $('#file')[0];

    // 선택된 파일 있는지 확인 
    if (fileInput.files.length === 0) {
        alert('업로드할 파일이 없습니다. 먼저 파일을 선택해 주세요.');
        return;
    }

    var file = fileInput.files[0];
    var fileName = file.name;
    var fileExt = fileName.slice(fileName.lastIndexOf(".") + 1).toLowerCase();

    // 확장자 검증
    if (fileExt !== "xls" && fileExt !== "xlsx") {
        alert("업로드는 엑셀파일(xls, xlsx)만 가능합니다.");
        $('#file').val(""); // 파일 입력 초기화
        return;
    }

    if (!confirm("엑셀 양식을 업로드 하시겠습니까?")) {
        return;
    }

    loadingMask(); // 로딩 표시 (선택 사항)

    
    var excelSysKnd = $("#excelSysKnd").val();
	var entpCd =  $("#entpCd").val();
    
    formData.append("file", file);
    formData.append("excelSysKnd",excelSysKnd);
    formData.append("entpCd",entpCd);

    $.ajax({
        url: "<c:url value='/edi/comm/commonExcelUpload.do'/>", 
        type: "post",
        async : false,
        data: formData,
        contentType: false, // 파일 전송 시 필요
        processData: false, // 파일 전송 시 필요
        success : function(data) {
            uploadReturn(data); 
            
            // 부모창의 콜백 함수 호출 하여 데이터 전달  그리드에 표시하지 않으려면 이부분 주석처리
            if (window.opener && !window.opener.closed) {
                window.opener.excelUploadcallBack(data);
            } else {
                alert("부모 창이 닫혀 있어 데이터를 전달할 수 없습니다.");
            }
        },
        error: function (xhr, status, error) {
            alert("업로드 중 오류가 발생했습니다.");
        }
    });
}

var msg = "";

//업로드된 데이터 화면에 상태별 건수 
function uploadReturn(rtnVal){
	$("#file").val("");

	msg = "";
	list = rtnVal.resultMap.list;
	
	
	if(list.length == 0){
		alert("양식에 등로된 데이터가 존재하지 않습니다.");
		this.close(); 
	}

		msg = "<span style='font-size:15px; font-weight:bold;'> 업로드 <span style='color:red;'>"+rtnVal.resultMap.list.length+"</span> 건 중 <span style='color:red;'>"+rtnVal.errorCnt+"</span> 건 오류</span><br/>"
			   + "*  정상등록 건수 : "+rtnVal.completeCnt+" 건<br/>"
	           + "*  비정상 건수 : "+rtnVal.errorCnt+" 건<br/>";
		$("#upCntTd").text("");
		$("#sucCntTd").text("");
		$("#errCntTd").text("");
		$("#upCntTd").text(Number(rtnVal.resultMap.list.length)+" 건");
		$("#sucCntTd").text(Number(rtnVal.completeCnt)+" 건");
		$("#errCntTd").text(Number(rtnVal.errorCnt)+" 건");

	$("#msgDiv").html(msg);
/* 	if(keyNo.length > 0){
		for( var i = 0 ; i<keyNo.length ; i++)
			$("#msgDiv").append("<span style='font-size:15px; font-weight:bold;'>기준순번 <span style='color:red;'>"+keyNo[i]+"</span>번 오류</span><br/>");
	} */

	hideLoadingMask();
}
</script>
</head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<body>
	<form id="excelUploadPopup" name="excelUploadPopup" method="post" enctype="multipart/form-data">
	<iframe name="frameForExcel" id="frameForExcel" style="display:none;"></iframe>
	<input type="hidden" id="optionVal" name="optionVal" value="<c:out escapeXml='false' value='${param.optionVal}'/>" />
	<input type="hidden" id="entpCd" name="entpCd" value="<c:out escapeXml='false' value='${param.entpCd}'/>" />
	<input type="hidden" id="excelSysKnd" name="excelSysKnd" value="<c:out escapeXml='false' value='${param.excelSysKnd}'/>" />
	<input type="hidden" id="excelWorkKnd" name="excelWorkKnd" value="APPL" />
		<div id="popup">
			<div class="popup_contents">
				<!-- 1검색조건 -->
				<div class="bbs_search3" style="margin-top:5px;">
					<ul class="tit">
						<li class="tit">엑셀 업로드</li>
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
								<!-- <input type="file" name="createFile" id="createFile" style="width:450px;" value="파일이름"  /> -->
								<input id="file" name="file" style="width:450px;" title="Excel File" type="file" />
								<a href="#" class="btn" onclick="javascript:doExcelInsert();"><span>업로드</span></a>
								<a href="#" class="btn" onclick="javascript:window.close();"><span>닫기</span></a>
							</td>
						</tr>
					</table>
				</div>
				 
				<div class="bbs_search3">
					<ul class="tit">
						<li class="tit">일괄 등록 처리 결과</li>
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
	<form id="popXlsUploadForm" type="hidden" style="display: none">
	</form>
</body>
</html>