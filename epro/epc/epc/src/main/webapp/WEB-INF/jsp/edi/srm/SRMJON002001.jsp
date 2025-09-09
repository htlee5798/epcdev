<%@ page pageEncoding="UTF-8"%>


<%--
	Page Name 	: SRMJON0021.jsp
	Description : 기존입점상당신청 내역이 존재 할 경우 안내창
	Modification Information
	
	수정일      			수정자           		수정내용
	-----------    	------------    ------------------
	2016.07.06  	SHIN SE JIN		 최초 생성
--%>

<!doctype html>
<html lang="ko">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<%@ include file="/common/edi/taglibs.jsp"%>

<title><spring:message code='text.srm.field.message' /></title><%--Message --%>

<script language="JavaScript">
	/* 화면 초기화 */
	$(document).ready(function() {
		goPage("1");
		
		/* 해당row에 마우스over시 row background-color 변경 */
		$("#dataListbody tr").hover(function(){
			$("#dataListbody tr").css("background-color", "");
			$(this).css("background-color", "#efefef");
		});
		
		/* list에서 마우스out시 row background-color 초기화 */
		$("#dataListbody tr").mouseout(function(){
			$("#dataListbody tr").css("background-color", "");
		});
		
		/* RowCount 메뉴 선택시 호충 */
		$('#pageRowCount').live('change',function(){
			goPage();
		});
		
	});
	
	/* 상담신청 리스트 조회 */
	function goPage(pageIndex) {
		var searchInfo = {};
		
		searchInfo["pageIndex"] = pageIndex;
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/srm/selectCounselList.json"/>',
			data : JSON.stringify(searchInfo),
			success : function(json) {
				_setTbodyMasterValue(json);
			}
		});
	}

	/* List Set */
	function _setTbodyMasterValue(json){
		var data = json.list;
		setTbodyInit("dataListbody");	// dataList 초기화
		
		if (data.length > 0) {
			$("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
			$("#paging").html(json.contents);
		} else {
			setTbodyNoResult("dataListbody", 6);
			$("#paging").html();
		}
	}
	
	/* 선택한 정보 호출 */
	function selectRow(rnum) {
		
		var status = $("#MyForm input[name='status']").eq(rnum-1).val();		// 상태값
		var reqSeq = $("#MyForm input[name='reqSeq']").eq(rnum-1).val();		// 순번
		
		// 임지저장 시 약관동의 페이지
		var url = "<c:url value='/edi/srm/SRMJON0010.do'/>";
		
		// 임시저장 상태가 아닐 시 결과 페이지
		if (status != "") {
			url = "<c:url value='/edi/srm/SRMRST0020.do'/>";
		}
		
		$("#hiddenForm input[name=reqSeq]").val(reqSeq);
		$("#hiddenForm").attr("action", url);
		$("#hiddenForm").attr("target","_self");
		$("#hiddenForm").submit();
	
	}
	
	/* 신규신청 */
	function doRequest() {
	    $("#hiddenForm input[name=reqSeq]").val("");
		$("#hiddenForm").attr("action", "<c:url value='/edi/srm/SRMJON0010.do'/>");	// 약관동의
		$("#hiddenForm").attr("target","_self");
		$("#hiddenForm").submit();
	}
	
</script>

<script id="dataListTemplate" type="text/x-jquery-tmpl">
	<tr style="cursor:pointer;" onClick="selectRow('<c:out value="\${rnum}"/>')">
		<td style="text-align: center;"><c:out value="\${rnum}"/></td>
		<td><c:out value="\${channelCodeNm}"/></td>
		<td><c:out value="\${catLv1CodeNm}"/></td>

		{%if status == '' || status == null %}
		<td>
			<spring:message code="text.srm.field.noStatus" />
		</td>
		{%else%}
			{%if status == 'C02' || status == 'E01' || status == 'D05' || status == 'H04'%}
				<td style="color :red;">
					<c:out value="\${statusNm}" />
				</td>
			{%elif status == 'Z01'%}
				<td style="color :blue;">
					<c:out value="\${statusNm}" />
				</td>
			{%else%}
				<td>
					<c:out value="\${statusNm}" />
				</td>
			{%/if%}
		{%/if%}
		
		<td><c:out value="\${addDate}"/></td>

		<td><c:out value="\${requestDate}"/>
			<input type="hidden" id="status" name="status" value="<c:out value="\${status}" />" />
			<input type="hidden" id="reqSeq" name="reqSeq" value="<c:out value="\${reqSeq}" />" />		
		</td>
	</tr>
</script>

</head>

<body>
<form id="MyForm" name="MyForm" method="post">

	<!--Container-->
	<div id="container">
		<!-- Sub Wrap -->
		<div class="inner sub_wrap">
			<!-- 서브상단 -->
			<div class="sub_top">
				<h2 class="tit_page"><spring:message code="header.menu.text2" /></h2>	<%-- 입점상담 신청 --%>
				<p class="page_path">HOME <span><spring:message code="header.menu.text2" /></span> <span><spring:message code='text.srm.field.consultList' /></span></p>
			</div><!-- END 서브상단 -->
			
			<!-- 알림 -->
			<div class="noti_box">
				<ul class="noti_list">
					<li><spring:message code='text.srm.field.srmjon002001Notice' /></li>	<%-- 해당 리스트 선택시 상세정보를 볼 수 있습니다. --%>
				</ul>
			</div><!-- END 알림 -->
			
			<!-- 상단 버튼영역 -->
			<div class="tit_btns">
				<h3 class="tit_star"><spring:message code='text.srm.field.consultList' /></h3>	<%-- 상담신청 리스트 --%>
				<div class="right_btns">
					<button type="button" class="btn_normal" onclick="goPage(1)"><spring:message code='button.srm.search2' /></button>					<%--조회--%>
					<button type="button" class="btn_normal btn_blue" onclick="doRequest()"><spring:message code='button.srm.reConsult' /></button>		<%--다시 신청하기 --%>
				</div>
			</div><!-- END 상단 버튼영역 -->
			
			<table class="tbl_st1">
				<colgroup>
					<col width="50">
	            	<col width="15%">
	            	<col width="15%">
	            	<col width="*">
	            	<col width="15%">
	            	<col width="15%">
				</colgroup>
				
				<thead>
					<tr>
						<th class="col_num"><spring:message code='text.srm.field.no' /></th><%--No --%>
						<th class="col_df"><spring:message code='text.srm.field.channelCode' /></th><%--채널 --%>
						<th><spring:message code='text.srm.field.catLv1Code' /></th><%--대분류 --%>
						<th><spring:message code='text.srm.field.status' /></th><%--진행상태 --%>
						<th class="col_df"><spring:message code='text.srm.field.addDate' /></th><%--등록일자 --%>
						<th class="col_df"><spring:message code='text.srm.field.receiptDate' /></th><%--요청일자 --%>
					</tr>
				</thead>
				
				<tbody class="align-c" id="dataListbody"/>
				
			</table>
			
			<!-- 페이징 -->
			<!-- <div class="board-pager mt30">
				<div id="pagging">
				<button type="button"><img src="/images/epc/edi/srm/board_img/arrow_l_end.gif" alt="맨 앞으로"></button>
				<button type="button"><img src="/images/epc/edi/srm/board_img/arrow_l.gif" alt="이전"></button>
				<a href="#" class="on">1</a>
				<a href="#">2</a>
				<a href="#">3</a>
				<a href="#">4</a>
				<a href="#">5</a>
				<button type="button"><img src="/images/epc/edi/srm/board_img/arrow_r.gif" alt="다음"></button>
				<button type="button"><img src="/images/epc/edi/srm/board_img/arrow_r_end.gif" alt="맨 뒤로"></button>
				</div>
			</div> -->
			<!-- END 페이징 -->
			
			<!-- Paging 영역 start --------------------------->
			<div id="pages">
				<div id="paging" class="board-pager mt30"></div>
			</div>
			<!-- Paging 영역 end --------------------------->
		
		</div><!-- END Sub Wrap -->
	</div><!--END Container-->
</form>

<form name="hiddenForm" id="hiddenForm" method="post">
	<input type="hidden" id="reqSeq" name="reqSeq" />
</form>

</body>
</html>