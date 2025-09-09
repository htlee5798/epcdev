<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>롯데마트 입점상담</title>

<script language="JavaScript">

	var code = "<c:out value='${code}'/>";
	
	$(document).ready(function(){
		if (code == "SUCCESS") {
			alert("저장 되었습니다.");
		} else if (code != "") {
			alert("데이터 처리 도중 오류가 발생하였습니다.");
		}
		
		//검색값 받아와서 조회 상태로 해줄려면 여기서 코딩 하면 됩니다.
		
	});

	/* 전체선택, 전체해제 */
	function checkAll() {
		var ckCnt = $("input[name='ck']").length;
		
		if (ckCnt <= 0) {
			return;
		}
		
		if ($("input[name='ckall']").is(":checked")) {
			$("input[name='ck']:checkbox").each(function() {
				$(this).attr("checked", true);
			});
		} else {
			$("input[name='ck']:checkbox").each(function() {
				$(this).attr("checked", false);
			});
		}
	}

	function forwardDetailPage(val){
		var form = document.forms[0];

		form.forwardPID.value=val;

		form.action  = "<c:url value='/edi/consult/NEDMCST0220SelectDetail.do'/>";
		form.submit();	
	}
	
	function doSearch() {
		var searchInfo = {};
		
		// 조회기간(From)
		searchInfo["srchStartDate"] 	= $("#searchForm input[name='srchStartDate']").val();
		// 조회기간(To)
		searchInfo["srchEndDate"] 		= $("#searchForm input[name='srchEndDate']").val();
		// 협력업체(개별)
		searchInfo["searchEntpCd"] 		= $("#searchForm select[name='entp_cd']").val();
		if(dateValid()){
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				//url : '${ctx}/edi/product/test.json',
				url : '<c:url value="/edi/consult/NEDMCST0220Select.json" />',
				data : JSON.stringify(searchInfo),
				success : function(data) {
					
					//json 으로 호출된 결과값을 화면에 Setting 
					_setTbodyMasterValue(data);
				}
			});	
		}
		
		
		/* _eventSearch() 후처리(data  객체 그리기) */
		function _setTbodyMasterValue(json) { 
			var data = json.estList;
			setTbodyInit("dataListbody");	// dataList 초기화
			
			if (data.length > 0) {
				//console.log('pid = ' + data[0].pid);
				$("#dataListTemplate").tmpl(data).appendTo("#dataListbody"); // setComma(n)
				
			} else { 
				setTbodyNoResult("dataListbody", 6);
			}
					
		}
			
	}

	function doDelete(){

		if($('#dataListbody tr').length == 0){
			alert("<spring:message code='epc.cst.alert.msg7'/>");
			return;
		}

		if($("#searchForm input[name='ck']:checked").length == 0){
			alert("<spring:message code='epc.cst.alert.msg8'/>");
			return;
		}
		
		if(confirm("<spring:message code='epc.cst.alert.msg9'/>")){
			var searchInfo = {};
			// 조회기간(From)
			var pids = [];
				$("#searchForm input[name='ck']:checked").each(function(){
				pids.push($(this).val());
			});
		 	searchInfo["pids"] = pids;
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				//url : '${ctx}/edi/product/test.json',
				url : '<c:url value="/edi/consult/NEDMCST0220Delete.json" />',
				data : JSON.stringify(searchInfo),
				success : function(data) {
					//json 으로 호출된 결과값을 화면에 Setting 
					doSearch();
				}
			});	
		}
	}

	function dateValid(){

		var startDate 	= $("#searchForm input[name='srchStartDate']").val();
		var endDate 	= $("#searchForm input[name='srchEndDate']").val();
		
		var rangeDate = 0;
		
		if(startDate == "" || endDate == ""  ){
			alert("<spring:message code='msg.common.fail.nocalendar'/>");
			form.startDate.focus();
			return false;
		}


		// startDate, endDate 는 yyyy-mm-dd 형식

	    startDate = startDate.substring(0, 4) + startDate.substring(5, 7) + startDate.substring(8, 10);
	    endDate = endDate.substring(0, 4) + endDate.substring(5, 7) + endDate.substring(8, 10);

	   var intStartDate = parseInt(startDate);
	   var intEndDate = parseInt(endDate);
			
		
	    if (intStartDate > intEndDate) {
	        alert("<spring:message code='msg.common.fail.calendar'/>");
	        form.startDate.focus();
	        return false;
	    }

		
	    intStartDate = new Date(startDate.substring(0, 4),startDate.substring(4,6),startDate.substring(6, 8),0,0,0); 
	    endDate = new Date(endDate.substring(0, 4),endDate.substring(4,6),endDate.substring(6, 8),0,0,0); 


	    rangeDate=Date.parse(endDate)-Date.parse(intStartDate);
	    rangeDate=Math.ceil(rangeDate/24/60/60/1000);

	    /*
		if(rangeDate>30){
			alert("<spring:message code='msg.common.fail.rangecalendar_30'/>");
			form.startDate.focus();
			return false;
		}
	    */
		return true;
		
	}
</script>
<!-- Data List Templete -------------------------------------------------------------------------------------------------------------->
<script id="dataListTemplate" type="text/x-jquery-tmpl">
<tr class="r1">
	<td align="center"><input type="checkbox" name="ck" value="<c:out value="\${pid}"/>"/></td>
	<td align="center"><a href="javascript:forwardDetailPage('<c:out value="\${pid}"/>');"><c:out value="\${pid}"/></a></td>
	<td align="center"><c:out value="\${venCd}"/></td>
	<td>&nbsp;<c:out value="\${docNm}"/></td>
	<td>&nbsp;<c:out value="\${fileNm}"/></td>
	<td align="center"><c:out value="\${regDate}"/></td>
</tr>
</script>

<!-- Data List Templete -------------------------------------------------------------------------------------------------------------->

</head>

<body>
<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>  >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form id = "searchForm" name="searchForm" method="post" action="#" >
		
		<input type="hidden" id="widthSize" name="widthSize" value="<c:out value="${param.widthSize }" />" > 
		
		<div id="wrap_menu">
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit"><spring:message code='epc.cst.create'/></li>
						<li class="btn">
							<a href="#" class="btn" onclick="javascript:doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
							<a href="#" class="btn" onclick="javascript:doDelete();"><span><spring:message code="button.common.delete"/></span></a>
						</li>
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
					
					<colgroup>
						<col style="width:15%" />
						<col style="width:30%" />
						<col style="width:10%" />
						<col style="*" />
					</colgroup>
					<tr>
						<th><span class="star">*</span> <spring:message code='epc.cst.search.searchPeriod'/> </th>
						<td>
							<input type="text" class="day" id="srchStartDate" name="srchStartDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="<c:out value="${paramMap.srchStartDate}" />" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchStartDate');" style="cursor:hand;" />
							~
							<input type="text" class="day" id="srchEndDate" name="srchEndDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="<c:out value="${paramMap.srchEndDate}" />" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchEndDate');"  style="cursor:hand;" />
						</td>
							
						<th><spring:message code='epc.cst.search.entpCd'/></th>
						<td>
							<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="<c:out value='${param.entp_cd}'/>" dataType="CP" comType="SELECT" formName="form" defName="전체" />
						</td>
					</tr>
					</table>
				</div>
				<!-- 1검색조건 // -->
			</div>
			<!--	2 검색내역 	-->
			<div class="wrap_con">
				<!-- list -->
				<div class="bbs_list">
					<ul class="tit">
						<li class="tit"><spring:message code='epc.cst.search.result'/></li>
					</ul>
					<div style="width:100%; height:484px; overflow-y:scroll; overflow-x:scroll;">
						<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="addTable">
							<input type="hidden" name="deleteCode" />
							<input type="hidden" name="forwardPID" />
							<colgroup>
								<col style="width:30px;" />
								<col style="width:100px;" />
								<col style="width:100px;" />
								<col style="width:150px;" />
								<col />
								<col style="width:100px;"/>
							</colgroup>
							<tr>
								<th><input type="checkbox" id="ckall" name="ckall" onclick="javascript:checkAll();"/></th>
								<th><spring:message code='epc.cst.header.hCode11'/></th>
								<th><spring:message code='epc.cst.header.hCode12'/></th>
								<th><spring:message code='epc.cst.header.hCode13'/></th>
								<th><spring:message code='epc.cst.header.hCode14'/></th>
								<th><spring:message code='epc.cst.header.hCode15'/></th>
							</tr>
							<!-- Data List Body Start ------------------------------------------------------------------------------>
							<tbody id="dataListbody" />
							<!-- Data List Body End   ------------------------------------------------------------------------------>
							</table>
					</div>
				</div>
			</div>
		</div>
	</form>
</div>
		
		
		
<!-- footer -->
<div id="footer">
	<div id="footbox">
		<div class="msg" id="resultMsg"></div>
		<div class="notice"></div>
		<div class="location">
			<ul>
				<li><spring:message code='epc.cst.home'/></li>
				<li><spring:message code='epc.cst.cola'/></li>
				<li class="last"><spring:message code='epc.cst.retrive'/></li>
			</ul>
		</div>
	</div>
</div>
<!-- footer //-->
</div>

</body>
</html>