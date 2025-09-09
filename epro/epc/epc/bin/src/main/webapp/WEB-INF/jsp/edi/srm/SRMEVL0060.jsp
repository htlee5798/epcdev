<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>

<%--
	Page Name 	: SRMEVL0060.jsp
	Description : 품질경영평가 대상 정기평가
	Modification Information
	
	수정일      			수정자           		수정내용
	-----------    	------------    ------------------
	2016.10.07  	LeeHyoungTak		 최초 생성
--%>

<!doctype html>
<html lang="ko">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title><spring:message code="text.srm.field.srmevl0030.title"/></title><%--spring:message : 품질경영평가 대상--%>

<script>
	$(document).ready(function() {
		goPage('1');

		// 파트너사 enter key이벤트 --------------
		$('#searchForm input[name=sellerNameLoc]').unbind().keydown(function(e) {
			switch(e.which) {
				case 13 :  goPage('1'); break; // enter
				default : return true;
			}
			e.preventDefault();
		});
	});

	//팝업 윈도우
	function popupWindow(url, width, height){
		var cw=width;
		var ch=height;
		var sw=screen.availWidth;
		var sh=screen.availHeight;
		var px=Math.round((sw-cw)/2);
		var py=Math.round((sh-ch)/2);
		window.open("","popup","left="+px+",top="+py+",width="+cw+",height="+ch+",toolbar=no,menubar=no,status=yes,resizable=no,scrollbars=yes");
		$("#hiddenForm").attr("action", url);
		$("#hiddenForm").attr("target", "popup");
		$("#hiddenForm").submit();
		$("#hiddenForm").attr("target", "_self");
	}

	//품질경영평가 조회
	function goPage(pageIndex){

		var searchInfo = {};
		searchInfo["sellerNameLoc"] = $('#searchForm input[name=sellerNameLoc]').val();
		searchInfo["status"] = $('#searchForm select[name=status]').val();
		searchInfo["pageIndex"] = pageIndex;
//		searchInfo["pageRowCount"] = $('input[name="pageRowCount"]:radio:checked').val();

		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/evl/selectQualityEvaluationPeriodicList.json"/>',
			data : JSON.stringify(searchInfo),
			success : function(json) {
				_setTbodyMasterValue(json);
			}
		});
	}

	//LIST 조회된 값 SET
	function _setTbodyMasterValue(json){
		var data = json.listData;
		setTbodyInit("dataListbody");	// dataList 초기화
		$("#paging").empty();			// paging 초기화
		if (data!= "") {
			$("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
			$("#paging").html(json.contents);
		} else {
			setTbodyNoResult("dataListbody", 8);
		}
	}

	//품질경영평가 대상 정보 상세
	function doDetail(houseCode, sgNo, evNo, vendorCode, evTplNo, statusNm, sellerNameLoc, evalNoResult, visitSeq, seq, progressCode) {
		//접수 상태 여부 확인
		$('#hiddenForm input[name="houseCode"]').val(houseCode);
		$('#hiddenForm input[name="sgNo"]').val(sgNo);
		$('#hiddenForm input[name="evNo"]').val(evNo);
		$('#hiddenForm input[name="vendorCode"]').val(vendorCode);
		$('#hiddenForm input[name="sellerCode"]').val(vendorCode);
		$('#hiddenForm input[name="statusNm"]').val(statusNm);
		$('#hiddenForm input[name="sellerNameLoc"]').val(sellerNameLoc);
		$('#hiddenForm input[name="evalNoResult"]').val(evalNoResult);
		$('#hiddenForm input[name="evTplNo"]').val(evTplNo);
		$('#hiddenForm input[name="visitSeq"]').val(visitSeq);
		$('#hiddenForm input[name="seq"]').val(seq);
		$('#hiddenForm input[name="evalFlag"]').val('1');


		if(progressCode == 100) {
			//접수화면
			popupWindow("<c:url value='/edi/evl/receiptPeriodicPopup.do'/>",880,720);
			return;
		} else if(progressCode == 200 || progressCode == 300) {
			//평가항목 등록 상세 화면
			$("#hiddenForm input[name=progressCode]").val(progressCode);
			$("#hiddenForm").attr("action", "<c:url value='/edi/evl/SRMEVL0040.do'/>");
			$("#hiddenForm").submit();
			return;
		}
		<%--else if(progressCode == 300) {--%>
			<%--//평가항목 상세 팝업--%>
			<%--popupWindow("<c:url value='/edi/evl/selectQualityEvaluationSiteVisitDetailPopup.do'/>", 950, 650);--%>
			<%--return;--%>
		<%--}--%>
	}

	//엑셀다운로드
	function doExcelDown(){
		$("#excelForm input[name=sellerNameLoc]").val($('#searchForm input[name=sellerNameLoc]').val());
		$("#excelForm input[name=status]").val($('#searchForm select[name=status]').val());

		$("#excelForm").attr("action", "<c:url value='/edi/evl/selectQualityEvaluationPeriodicListExcel.do'/>");
		$("#excelForm").submit();
	}


	//결과보고서 팝업
	function doReport(houseCode, sellerCode, seq, visitSeq, evNo){
		$('#hiddenForm input[name="houseCode"]').val(houseCode);
		$('#hiddenForm input[name="vendorCode"]').val(sellerCode);
		$('#hiddenForm input[name="seq"]').val(seq);
		$('#hiddenForm input[name="visitSeq"]').val(visitSeq);
		$('#hiddenForm input[name="evNo"]').val(evNo);
		$('#hiddenForm input[name="evalFlag"]').val('1');
		popupWindow("<c:url value='/edi/evl/selectQualityEvaluationSiteVisitReportPopup.do'/>",950,650);
	}

</script>

<%-- 리스트 템플릿 --%>
	<script id="dataListTemplate" type="text/x-jquery-tmpl">
	<tr>
		<td><c:out value="\${rnum}"/></td>
		{%if status == 'G10'%}
			<td>
				<c:out value="\${evalNoResult}"/>-<c:out value="\${visitSeq}"/>
			</td>
		{%else%}
			<td class="cell_yellow">
			<a href="#" style="font-weight:bold;" onclick="javascript:doDetail('<c:out value="\${houseCode}"/>'
																				,'<c:out value="\${catLv1Code}"/>'
																				,'<c:out value="\${evalNoResult}"/>'
																				,'<c:out value="\${sellerCode}"/>'
																				,'<c:out value="\${evTplNo}"/>'
																				,'<c:out value="\${statusNm}"/>'
																				,'<c:out value="\${sellerNameLoc}"/>'
																				,'<c:out value="\${evalNoResult}"/>'
																				,'<c:out value="\${visitSeq}"/>'
																				,'<c:out value="\${seq}"/>'
																				,'<c:out value="\${progressCode}"/>')">
				<c:out value="\${evalNoResult}"/>-<c:out value="\${visitSeq}"/>
			</a>
		</td>
		{%/if%}
		{%if status == 'G10'%}
			<td align="left">
				<c:out value="\${sellerNameLoc}"/>
			</td>
		{%else%}
			<td class="cell_yellow" style="text-align: left;">
				<a href="#" style="font-weight:bold;" onclick="javascript:doDetail('<c:out value="\${houseCode}"/>'
																					,'<c:out value="\${catLv1Code}"/>'
																					,'<c:out value="\${evalNoResult}"/>'
																					,'<c:out value="\${sellerCode}"/>'
																					,'<c:out value="\${evTplNo}"/>'
																					,'<c:out value="\${statusNm}"/>'
																					,'<c:out value="\${sellerNameLoc}"/>'
																					,'<c:out value="\${evalNoResult}"/>'
																					,'<c:out value="\${visitSeq}"/>'
																					,'<c:out value="\${seq}"/>'
																					,'100')">
					<c:out value="\${sellerNameLoc}"/>
				</a>
			</td>
		{%/if%}		</td>
		<%--<td><c:out value="\${catLv1CodeName}"/></td>--%>
		<td><c:out value="\${reqDate}"/></td>
		<td><c:out value="\${receiptDate}"/></td>
		<%--<td><c:out value="\${changeDate}"/></td>--%>
		<td align="center">
			{%if status == 'G10'%}
				<div class="status_circle sc_darkgray" data-tooltip-text="<c:out value="\${statusNm}"/>" />
			{%else%}
				{%if progressCode == '100'%}
					<div class="status_circle sc_gray" data-tooltip-text="<c:out value="\${statusNm}"/>" />
					<%--<spring:message code="text.srm.search.field.status.option1"/>--%>
				{%elif progressCode == '200'%}
					<div class="status_circle sc_yellow" data-tooltip-text="<c:out value="\${statusNm}"/>" />
					<%--<spring:message code="text.srm.search.field.status.option2"/>--%>
				{%elif progressCode == '300'%}
					<div class="status_circle sc_green" data-tooltip-text="<c:out value="\${statusNm}"/>" />
					<%--<spring:message code="text.srm.search.field.status.option3"/>--%>
				{%/if%}
			{%/if%}
		</td>
		<td>
			{%if progressCode == '300'%}
				<input type="button" class="btn_normal btn_black" value="view" onclick="javascript:doReport('<c:out value="\${houseCode}"/>'
																												,'<c:out value="\${sellerCode}"/>'
																												,'<c:out value="\${seq}"/>'
																												,'<c:out value="\${visitSeq}"/>'
																												,'<c:out value="\${evalNoResult}"/>')"/>
			{%/if%}
		</td>
	</tr>
</script>

</head>

<body>
	<!--Container-->
	<div id="container">
		<!-- Sub Wrap -->
		<div class="inner sub_wrap">
			<!-- 서브상단 -->
			<div class="sub_top">
				<h2 class="tit_page"><spring:message code="evlHeader.menu.text3" /></h2>	<%-- 입점평가--%>
				<p class="page_path"><spring:message code="evlHeader.menu.text3" /><span><spring:message code="text.srm.field.srmevl0030.sub.title1"/></span></p>
			</div>
			<!-- END 서브상단 -->

			<div class="tit_btns">
				<h3 class="tit_star"><spring:message code="text.srm.field.srmevl0030.sub.title2"/></h3>	<%-- 품질경영평가 대상 조회--%>
				<div class="right_btns">
					<button type="button" class="btn_normal" onclick="javascript:goPage('1');"><spring:message code="button.srm.search2"/></button>	<%-- 조회--%>
					<button type="button" class="btn_normal" onclick="javascript:doExcelDown();"><spring:message code="button.srm.excel"/></button>	<%-- 엑셀--%>
				</div>
			</div>

			<%-- 검색조건 start -----------------------------------------------------%>
			<form id="searchForm" name="searchForm" method="post" onsubmit="return false;">
				<table class="tbl_st1 form_style">
					<colgroup>
						<col style="width:15%;">
						<col style="width:35%;">
						<col style="width:15%;">
						<col>
					</colgroup>
					<tbody>
					<tr>
						<th><label for="sellerNameLoc"><spring:message code="text.srm.search.field.sellerNameLoc"/></label></th><%--spring:message : 파트너사명--%>
						<td><input type="text" id="sellerNameLoc" name="sellerNameLoc" title=""></td>
						<th><label for="status"><spring:message code="text.srm.search.field.status"/></label></th><%--spring:message : 진행상태--%>
						<td>
							<select id="status" name="status" title="<spring:message code="text.srm.search.field.status"/>" style="width:100px;">
								<option value="">전체</option>
								<option value="100"><spring:message code="text.srm.search.field.status.option1"/></option><%--spring:message : 품질경영평가요청--%>
								<option value="200"><spring:message code="text.srm.search.field.status.option2"/></option><%--spring:message : 품질경영평가요청확인--%>
								<option value="300"><spring:message code="text.srm.search.field.status.option3"/></option><%--spring:message : 품질경영평가완료--%>
							</select>
						</td>
					</tr>
					</tbody>
				</table>
			</form>
			<%-- 검색조건 end ------------------------------------------------%>

			<br>
			<%-- 그리드 영역 Start ---------------------------%>
			<table class="tbl_st1">
				<colgroup>
					<col width="50">
					<col width="130">
					<col width="*">
					<%--<col width="120">--%>
					<col width="100">
					<col width="100">
					<%-- <col width="120"> --%>
					<col width="70">
					<col width="80">
				</colgroup>
				<thead>
				<tr>
					<th><spring:message code="table.srm.colum.title.no"/></th><%--spring:message : No--%>
					<th><spring:message code="table.srm.srmevl0030.colum.title2"/></th><%--spring:message : 의뢰번호--%>
					<th><spring:message code="table.srm.srmevl0030.colum.title3"/></th><%--spring:message : 파트너사명--%>
					<%--<th><spring:message code="table.srm.srmevl0030.colum.title4"/></th>&lt;%&ndash;spring:message : 분류&ndash;%&gt;--%>
					<th><spring:message code="table.srm.srmevl0030.colum.title5"/></th><%--spring:message : 신청일자--%>
					<th><spring:message code="table.srm.srmevl0030.colum.title6"/></th><%--spring:message : 접수일자--%>
					<%-- <th><spring:message code="table.srm.srmevl0030.colum.title7"/></th> --%><%--spring:message : 상태변경일자--%>
					<th><spring:message code="table.srm.srmevl0030.colum.title8"/></th><%--spring:message : 진행상태--%>
					<th><spring:message code="table.srm.srmevl0030.colum.title9"/></th><%--spring:message : 결과보고서--%>
				</tr>
				</thead>
				<%-- List --%>
				<tbody class="align-c" id="dataListbody"/>
			</table>
			<%-- 그리드 영역 end ---------------------------%>


			<%-- Paging 영역 start ---------------------------%>
			<div id="paging" class="board-pager mt10">
			</div>
			<%-- Paging 영역 end ---------------------------%>

		</div>
	</div>


	<form id="hiddenForm" name="hiddenForm" method="post">
		<input type="hidden" name="houseCode" id="houseCode" />
		<input type="hidden" name="sellerCode" id="sellerCode" />
		<input type="hidden" name="seq" id="seq" />
		<input type="hidden" name="sgNo" id="sgNo" />
		<input type="hidden" name="evNo" id="evNo" />
		<input type="hidden" name="vendorCode" id="vendorCode" />
		<input type="hidden" name="evTplNo" id="evTplNo" />
		<input type="hidden" id="evUserId" name="evUserId"/>
		<input type="hidden" name="statusNm" id="statusNm" />
		<input type="hidden" name="sellerNameLoc" id="sellerNameLoc" />
		<input type="hidden" name="evalNoResult" id="evalNoResult" />
		<input type="hidden" name="visitSeq" id="visitSeq" />
		<input type="hidden" name="evalFlag" id="evalFlag" />
		<input type="hidden" name="activeTab" id="activeTab" value="1" />
		<input type="hidden" name="progressCode" id="progressCode" />

	</form>

	<form id="excelForm" name="excelForm" method="post">
		<input type="hidden" name="sellerNameLoc" id="sellerNameLoc" />
		<input type="hidden" name="status" id="status" />
	</form>

</body>
</html>