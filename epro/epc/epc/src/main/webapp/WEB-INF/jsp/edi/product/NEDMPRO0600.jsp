<%--
	Page Name 	: NEDMPRO0020.jsp
	Description : IF 테스트 페이지
	Modification Information

	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	
--%>
<%@ include file="/WEB-INF/jsp/common/incTaglib.jsp" %>
<jsp:include page="/WEB-INF/jsp/edi/common.jsp"/>
<jsp:include page="/common/scm/scmCommon.jsp"/>
<jsp:include page="/WEB-INF/jsp/edi/product/CommonProductFunction.jsp"/>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>원가변경요청 등록</title>
<style>
table.sub-table td{padding:5px 3px; word-break:break-word;}
table.sub-table select, table.sub-table input, table.sub-table textarea{max-width:100%;}  
table.sub-table .tdr{text-align:right;}
table.sub-table .tdc{text-align:center;}
</style>
<script type="text/javascript">
$(function(){
});

//BOS 전송테스트
function eventTest01(){
	
	var saveInfo = {};
	saveInfo.IF_CD = $.trim($("#ifCd").val());
	saveInfo.modDate = $.trim($("#modDate").val().replace(/\D/g,""));
	
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		url : '<c:url value="/openApi/mergeBaseInfo.json"/>',
		data : JSON.stringify(saveInfo),
		success : function(data) {
			var msgCd = data.msgCd;
			if("S" == msgCd){
				alert("성공");
			}else{
				alert("오류\n"+data.message);
			}
		}
	});
	
}

//BOS 전송테스트 All
function eventTest04(){
	
	var saveInfo = {};
	saveInfo.IF_CD = $.trim($("#ifCd2").val());
	saveInfo.modDate = $.trim($("#modDate2").val().replace(/\D/g,""));
	
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		url : '<c:url value="/openApi/insertRealBaseInfoAll.json"/>',
		data : JSON.stringify(saveInfo),
		success : function(data) {
			var msgCd = data.msgCd;
			if("S" == msgCd){
				alert("성공");
			}else{
				alert("오류\n"+data.message);
			}
		}
	});
	
}

//HQ_VEN > TVE_VENDOR MERGE
function eventTest02(){
	
	var saveInfo = {};
	saveInfo.VEN_CD = $.trim($("#venCd").val());
	saveInfo.BMAN_NO = $.trim($("#bmanNo").val());
	
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		url : '<c:url value="/edi/product/updateMergeHqVenToTveVendor.json"/>',
		data : JSON.stringify(saveInfo),
		success : function(data) {
			var msgCd = data.msgCd;
			if("S" == msgCd){
				alert("성공");
			}else{
				alert("오류\n"+data.message);
			}
		}
	});
	
}

//ERP 자동발송
function eventTest03(){
	
	var pgmIds = $.trim($("#pgmIds").val());
	var pgmIdsArr = pgmIds.split(";");
	
	if(pgmIdsArr == null || pgmIdsArr.length == 0) {
		alert("pgmID필수");
		return;
	}
	
	var prodArr = [];
	
	
	$.each(pgmIdsArr, function(i,val){
		var prodData = {};
		
		if($.trim(val) != ""){
			prodData.pgmId = val;
			prodArr.push(prodData);
		}
	});
	
	
	var saveInfo = {};
	saveInfo.prodArr = prodArr;
	
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		async : false,
		url : '<c:url value="/edi/product/updateImsiProductListFixAutoSend.json"/>',
		data : JSON.stringify(saveInfo),
		success : function(data) {
			var msgCd = data.msgCd;
			if("SUCCESS" == msgCd){
				alert("성공");
			}else{
				alert("오류\n"+data.message);
			}
		}
	});
	
}



//GCS
function eventTest04(){
	
	var saveInfo = {};
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		url : '<c:url value="/edi/product/updateGcsGetParquetManual.json"/>',
		data : JSON.stringify(saveInfo),
		success : function(data) {
			var msgCd = data.msgCd;
			if("S" == msgCd){
				alert("성공");
			}else{
				alert("오류");
			}
		}
	});
	
}


</script>
</head>
<body>
<div id="content_wrap">
	<div id="wrap_menu">
		<div class="wrap_search">
			<div class="wrap_con">
				<div class="bbs_list">
					<ul class="tit">
						<li class="tit">I/F test</li>
						<li class="btn">
						</li>
					</ul>
					<div style="width:100%; height:458px;overflow-x:hidden; overflow-y:scroll; overflow-x:scroll; white-space:nowrap;">
						<form id="reqDetailForm" name="reqDetailForm" method="post">
							<table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=600px; bgcolor="efefef" style="table-layout:fixed;" class="sub-table">
								<tr>
									<th>전상법마스터</th>
									<td>
										<input type="text" id="modDate" name="modDate" value="" placeholder="yyyyMMdd"/>
										<html:codeTag objId="ifCd" objName="ifCd" width="150px;"  parentCode="BOSIF"  comType="SELECT" dataType="NTCPCD" orderSeqYn="Y" formName="form"/>
										<a href="javascript:void(0);" class="btn" onclick="eventTest01()"><span>요청</span></a>
									</td>
								</tr>
								<tr>
									<th>BOS 전체 DELETE INSERT</th>
									<td>
										<input type="text" id="modDate2" name="modDate" value="" placeholder="yyyyMMdd"/>
										<html:codeTag objId="ifCd2" objName="ifCd" width="150px;"  parentCode="BOSIF"  comType="SELECT" dataType="NTCPCD" orderSeqYn="Y" formName="form"/>
										<a href="javascript:void(0);" class="btn" onclick="eventTest04()"><span>요청</span></a>
									</td>
								</tr>
								<tr>
									<th>TVE_VENDOR MERGE</th>
									<td>
										※조건 필요시 입력
										<br/>
										<input type="text" id="bmanNo" name="bmanNo" value="" placeholder="사업자번호입력(미입력시 전체)"/>
										<input type="text" id="venCd" name="venCd" value="" placeholder="업체코드입력(미입력시 전체)"/>
										<a href="javascript:void(0);" class="btn" onclick="eventTest02()"><span>요청</span></a>
									</td>
								</tr>
								<tr>
									<th>ERP 자동발송</th>
									<td>
										<br/>
										<input type="text" id="pgmIds" name="pgmIds" value="" placeholder="pgmId입력 ;로구분"/>
										<a href="javascript:void(0);" class="btn" onclick="eventTest03()"><span>요청</span></a>
									</td>
								</tr>
								<tr>
									<th>GCS</th>
									<td>
										<br/>
										<a href="javascript:void(0);" class="btn" onclick="eventTest04()"><span>요청</span></a>
									</td>
								</tr>
							</table>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<form id="hiddenForm" name="hiddenForm" method="post" enctype="multipart/form-data">

</form>
</body>
</html>