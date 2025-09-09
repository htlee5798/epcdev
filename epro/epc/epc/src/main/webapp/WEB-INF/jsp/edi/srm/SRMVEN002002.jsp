<%@ page contentType="text/html; charset=UTF-8"%>

<%--
	Page Name 	: SRMVEN002002.jsp
	Description : 인증정보 팝업
	Modification Information
	
	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2016.08.04		SHIN SE JIN		최초생성
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<%@include file="../common.jsp"%>
	<%@ include file="/common/edi/ediCommon.jsp"%>

	<title></title>

<script language="JavaScript">

	$(document).ready(function() {
		selectData();
	});
	
	/* 인증정보 내역 */
	function selectData() {
		var searchInfo = {};
		searchInfo["houseCode"] = $("#MyForm input[name='houseCode']").val();
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/ven/selectCertiInfoList.json"/>',
			data : JSON.stringify(searchInfo),
			success : function(data) {
				_setTbodyMasterValue(data);
			}
		});
	}
	
	/* 인증정보 내역 List Set */
	function _setTbodyMasterValue(data){
		
		setTbodyInit("dataMainListbody");	// dataList 초기화
		
		if (data.length > 0) {
			for (var i = 0; i < data.length; i++) {
				data[i].rnum = i+1;
				data[i].certiDate = date_format(data[i].certiDate);	// 날짜 형식으로 바꾸기 
			}
			$("#dataMainListTemplate").tmpl(data).appendTo("#dataMainListbody");
		} else {
			setTbodyNoResult("dataMainListbody", 9);
		}
	}
	
	/* 날짜 형식으로 바꾸기 */
	function date_format(num){
		return num.replace(/([0-9]{4})([0-9]{2})([0-9]{2})/,"$1-$2-$3");
	}
	
	/* 인증정보 리스트 선택 */
	function onSelectRow(certiTarget, certiType, certiNo, certiName) {
		parent.window.opener.setcertiInfo(certiTarget, certiType, certiNo, certiName);	// 선택한 인증정보 부모창 텍스트에 값 입력
		window.close();
	}
	
</script>

<!-- 인증정보 템플릿 -->
<script id="dataMainListTemplate" type="text/x-jquery-tmpl">
	<tr class="r1" bgcolor=ffffff onClick="onSelectRow('<c:out value="\${certiTarget}"/>','<c:out value="\${certiType}"/>', '<c:out value="\${certiNo}"/>','<c:out value="\${certiName}"/>');" style="cursor:pointer;">
		<td style="text-align: center;"><c:out value="\${rnum}"/></td>
		<td style="text-align: center;"><c:out value="\${certiTargetNm}"/></td>
		<td style="text-align: left;"><c:out value="\${certiTypeNm}"/></td>
		<td style="text-align: center;"><c:out value="\${certiNo}"/></td>
		<td style="text-align: left;"><c:out value="\${certiName}"/></td>
		<td style="text-align: center;"><c:out value="\${certiDate}"/></td>
		<td style="text-align: center;"><c:out value="\${certiAnnNo}"/></td>
		<td style="text-align: left;"><c:out value="\${remark}"/></td>
		<td style="text-align: center;"><c:out value="\${addDate}"/></td>
	</tr>
</script>

</head>
<body onload="window.focus();">
			
<form id="MyForm" name="MyForm" method="post" action="#">
	<input type="hidden" id="houseCode" name="houseCode" value="000">
	
	<div id="popup">
	    <div id="p_title1">
	    	<h1><spring:message code="text.srm.field.srmjon0042.sub.title1" /></h1><%--인증정보 --%>
	        <span class="logo"><img src="/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
	    </div>
		<div class="popup_contents">
		<!----- 인증정보 List 내역 Start ------------------------->
		<div class="wrap_con">
			<div class="bbs_list">
				<ul class="tit">
					<li class="tit"><spring:message code='text.srm.field.srmjon0042.sub.title1' /></li><%--인증정보 --%>
				</ul>
				<div style="width:100%; height:230px; overflow:auto;">
					<table class="bbs_list" cellpadding="1" cellspacing="1" border="0" bgcolor=efefef id="tblInfo">
						<colgroup>
							<col width="30px;" />
							<col width="45px" />
							<col width="120px;" />
							<col width="80px;" />
							<col width="90px;" />
							<col width="60px;" />
							<col width="100px;" />
							<col width="90px;" />
							<col width="60px;" />
						</colgroup>
						<thead>
							<tr bgcolor="#e4e4e4">
								<th><spring:message code='text.srm.field.no' 			/></th><%--No --%>
								<th><spring:message code='text.srm.field.certiTarget' 	/></th><%--인증대상 --%>
								<th><spring:message code='text.srm.field.certiType' 	/></th><%--구분 --%>
								<th><spring:message code='text.srm.field.certiNo' 		/></th><%--인증번호 --%>
								<th><spring:message code='text.srm.field.certiName' 	/></th><%--인증명 --%>
								<th><spring:message code='text.srm.field.certiDate' 	/></th><%--고시일 --%>
								<th><spring:message code='text.srm.field.certiAnnNo' 	/></th><%--고시번호 --%>
								<th><spring:message code='text.srm.field.remark' 		/></th><%--비고 --%>
								<th><spring:message code='text.srm.field.addDate'		/></th><%--등록일 --%>
							</tr>
						</thead>
						<tbody id="dataMainListbody" />
					</table>	
				</div>
			</div>
		</div>
		<!----- 인증정보 List 내역 End ------------------------->
		</div>
	</div>
</form>
</body>
</html>