<%--<%@ page contentType="text/html; charset=UTF-8"%>--%>

<%--
	Page Name 	: SRMMNT0020.jsp
	Description : 대표자 SRM 모니터링
	Modification Information
	
	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2016.08.25		LEE HYOUNG TAK		최초생성
--%>

<!doctype html>
<html lang="ko">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<%@include file="./SRMCommon.jsp" %>
	<link href="https://fonts.googleapis.com/css?family=Open+Sans:400,600,700" rel="stylesheet">
	<link href="https://cdn.rawgit.com/hiun/NanumSquare/master/nanumsquare.css" rel="stylesheet" type="text/css">

<title><spring:message code='text.srm.field.srmmnt0020.title' /></title><%--spring:message : SRM 모니터링 --%>

<script language="JavaScript">
	/* 화면 초기화 */
	$(document).ready(function() {
		<c:if test="${empty venCds}">
			$('#searchForm').attr("action", "<c:url value="/edi/mnt/SRMMNT0010.do"/>");
			$('#searchForm').submit();
		</c:if>
		//대분류 조회
		selectCodeData('1');


		$('#searchForm select[name=countYear]').live('change',function(){
			dp_makeYearCombo($(this), $(this).val());
		});

		$('#searchForm select[name=catLv1Code]').live('change',function(){
			//중분류 조회
			selectCodeData('2');
		});


		//LIST 마우스 OVER
//		$('table tbody[id=dataListbody] tr').live('mouseover',function(){
//			$(this).attr("bgcolor","#efefef");
//		});
//		$('table tbody[id=dataListbody] tr').live('mouseout',function(){
//			$(this).attr("bgcolor","#ffffff");
//		});

		//커스텀태그 defName Set
		//선택
		var option = "<option value= \"\"><spring:message code='text.srm.field.venCdAll' /></option>";
		$(option).prependTo("#searchForm select[name=channelCode]");
//		$(option).prependTo("#searchForm select[name=countYear]");
//		$(option).prependTo("#searchForm select[name=countMonth]");
		<%--if("<c:out value="${epcLoginSession.repVendorId}"/>" == ""){--%>
			<%--$(option).prependTo("#searchForm select[name=venCd]");--%>
		<%--}--%>


		//커스텀태그 값SET
		<jsp:useBean id="toDay" class="java.util.Date" />
		<c:set var="toYear"><fmt:formatDate value="${toDay}" pattern="yyyy"/></c:set>
		<c:set var="toMonth"><fmt:formatDate value="${toDay}" pattern="MM"/></c:set>
		$('#searchForm select[name=channelCode]').val("");
		$('#searchForm select[name=countYear]').val("<c:out value="${toYear}"/>");
		$('#searchForm select[name=countMonth]').val("<c:out value="${toMonth}"/>");

		goPage('1');
	});

	/**
	 * 현재년도부터 -5년까지의 콤보 생성
	 *
	 * @param obj
	 * @param year
	 */
	function dp_makeYearCombo(obj, year) {
		if ( obj == null ) return;
		$(obj).html("");
		var html = "";
		html += "<option value=''><spring:message code='text.srm.field.select'/></option>";/*spring:message : 선택*/

		for( var i = year-5; i < year; i++ ) {
			html+="<option value=" + i +">" + i + "</option>"
		}

		for( var i = year; i < Number(year)+5; i++ ) {
			html+="<option value=" + i +">" + i + "</option>"
		}

		$(html).appendTo(obj);
		$(obj).val(year);
	}

	/* 대분류/중분류 조회 */
	function selectCodeData(depth) {

		var searchInfo = {};
		searchInfo["depth"] = depth;
		if(depth != '1') {
			searchInfo["parentSgNo"] = $('#searchForm select[name=catLv1Code]').val();
		}

		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/mnt/selectCEOCatLv1CodeList.json"/>',
			data : JSON.stringify(searchInfo),
			success : function(data) {
				var html = "";
				html += "<option value= \"\"><spring:message code="text.srm.field.venCdAll"/></option>";/*spring:message : 전체*/
				if(data.length > 0){
					for(var i=0; i<data.length; i++){
						html += "<option value= \"" + data[i].code + "\">" + data[i].name + "</option>";
					}
				} else {

				}
				if(depth == '1') {
					$('#searchForm select[name=catLv1Code]').html(html);
				} else if(depth == '2') {
					$('#searchForm select[name=catLv2Code]').html(html);
				}
			}
		});
	}


	/* 대분류/중분류 조회 */
	function goPage(pageIndex) {

		var searchInfo = {};
		var venCds = [];
		$('#searchForm').find("select").each(function(){
			searchInfo[this.name] = $(this).val();
		});
		searchInfo["irsNo"] = "<c:out value="${irsNo}"/>";
		searchInfo["pageIndex"] = pageIndex;
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/mnt/selectCEOSRMmoniteringList.json"/>',
			data : JSON.stringify(searchInfo),
			success : function(data) {
				_setTbodyMainValue(data);
			}
		});
	}

	/* 파트너사 인증정보 List Set */
	function _setTbodyMainValue(json){
		setTbodyInit("dataListbody");	// dataList 초기화
		$("#paging").empty();			// paging 초기화
		var data = json.listData;
		if (data.length > 0) {
			$("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
			$("#paging").html(json.contents);
		} else {
			setTbodyNoResult("dataListbody", 11);
		}
	}

	//상세 팝업
	function doPopup(obj){
		$(obj).parent().find("input").each(function(){
			$('#popUpForm input[name='+this.name+']').val($(this).val());
		});
		var cw = 1100;
		var ch = 800;

		var sw = screen.availWidth;
		var sh = screen.availHeight;
		var px = Math.round((sw-cw)/2);
		var py = Math.round((sh-ch)/2);

		window.open("", "SRMMNT0020Popup", "left=" + px + ",top=" + py + ",width=" + cw + ",height=" + ch + ",toolbar=no,menubar=no,status=no,resizable=no,scrollbars=yes");

		$("#popUpForm").attr("action", "<c:url value='/edi/mnt/selectCEOSRMmoniteringDetailPopup.do'/>");
		$("#popUpForm").attr("target", "SRMMNT0020Popup");
		$("#popUpForm").submit();
		$("#popUpForm").attr("target", "_self");
	}

	/*개선요청팝업*/
	function doImpReqPopup(obj){
		$(obj).parent().find("input").each(function(){
			$('#popUpForm input[name='+this.name+']').val($(this).val());
		});
		var cw = 700;
		var ch = 720;

		var sw = screen.availWidth;
		var sh = screen.availHeight;
		var px = Math.round((sw-cw)/2);
		var py = Math.round((sh-ch)/2);


		window.open("", "SRMMNT002007Popup", "left=" + px + ",top=" + py + ",width=" + cw + ",height=" + ch + ",toolbar=no,menubar=no,status=no,resizable=no,scrollbars=yes");

		$("#popUpForm").attr("action", "<c:url value='/edi/mnt/selectSRMmoniteringDetailImpReqPopup.do'/>");
		$("#popUpForm").attr("target", "SRMMNT002007Popup");
		$("#popUpForm").submit();
		$("#popUpForm").attr("target", "_self");

	}
</script>
<script id="dataListTemplate" type="text/x-jquery-tmpl">
	<tr bgcolor="ffffff">
		<td style="text-align: center;"><c:out value="\${rnum}"/></td>
		<td><c:out value="\${sgName1}"/></td>
		<td><c:out value="\${sgName2}"/></td>
		<td style="text-align: center;"><c:out value="\${vendorCode}"/></td>
		<td class="cell_yellow" onclick="javascript:doPopup(this);" style="cursor:pointer; font-weight:bold;"><c:out value="\${vendorName}"/></td>
		<td style="text-align: center;"><c:out value="\${evGradeClass}"/></td>
		<td style="text-align: center;"><c:out value="\${evScoreSum}"/></td>

		{%if impCnt != '0'%}
			<td class="cell_yellow" style="text-align: center; cursor:pointer; font-weight:bold;" onclick="javascript:doImpReqPopup(this);"><c:out value="\${improveCount}"/></td>
		{%else%}
			<td style="text-align: center;"><c:out value="\${improveCount}"/></td>
		{%/if%}

		<td style="text-align: center;"><c:out value="\${egName}"/></td>
		<td style="text-align: center;"><c:out value="\${evTplSubject}"/></td>
		<td style="text-align: center;">
			<c:out value="\${evChannelName}"/>
			<input type="hidden" id="vendorName" name="vendorName" value="<c:out value="\${vendorName}"/>"/>
			<input type="hidden" id="evTplSubject" name="evTplSubject" value="<c:out value="\${evTplSubject}"/>"/>
			<input type="hidden" id="countYear" name="countYear" value="<c:out value="\${countYear}"/>"/>
			<input type="hidden" id="countMonth" name="countMonth" value="<c:out value="\${countMonth}"/>"/>
			<input type="hidden" id="vendorCode" name="vendorCode" value="<c:out value="\${vendorCode}"/>"/>
			<input type="hidden" id="catLv2Code" name="catLv2Code" value="<c:out value="\${catLv2Code}"/>"/>
			<input type="hidden" id="egNo" name="egNo" value="<c:out value="\${egNo}"/>"/>
			<input type="hidden" id="evTplNo" name="evTplNo" value="<c:out value="\${evTplNo}"/>"/>
			<input type="hidden" id="channelCode" name="channelCode" value="<c:out value="\${channelCode}"/>"/>
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
				<h2 class="tit_page"><spring:message code="text.srm.field.srmven0030.sub.title1"/></h2>	<%-- 입점평가--%>
			</div>
			<!-- END 서브상단 -->

			<div class="tit_btns">
				<h3 class="tit_star"><spring:message code="text.srm.field.srmven0030.sub.title1"/></h3>	<%-- 품질경영평가 대상 조회--%>
				<div class="right_btns">
					<button type="button" class="btn_normal" onclick="javascript:goPage('1');"><spring:message code="button.srm.search2"/></button>	<%-- 조회--%>
				</div>
			</div>
			<form name="searchForm"  id="searchForm" method="post">
				<%-- 검색조건 start -----------------------------------------------------%>
				<table class="tbl_st1 form_style">
					<colgroup>
						<col style="width: 15%" />
						<col style="width: 30%" />
						<col style="width: 10%" />
						<col style="" />
					</colgroup>
					<tr>
						<th><label for="channelCode"><spring:message code="text.srm.field.channelCode"/></label></th><%--spring:message : 채널--%>
						<td>
							<srm:codeTag comType="SELECT" objId="channelCode" objName="channelCode" formName="" parentCode="SRM028" width="100px"/>
						</td>
						<th><label for="catLv1Code"><spring:message code="text.srm.field.catLv1Code"/></label></th><%--spring:message : 카테고리--%>
						<td>
							<select id="catLv1Code" name="catLv1Code" style="width:100px;"></select>
							/
							<select id="catLv2Code" name="catLv2Code" style="width:100px;">
								<option value=""><spring:message code="text.srm.field.venCdAll"/></option><%--spring:message : 전체--%>
							</select>
						</td>
					</tr>
					<tr>
						<th>
							<label for="venCd"><spring:message code="text.srm.field.venCompany"/></label><%--spring:message : 협력업체--%>
						</th>
						<td>
							<select id="venCd" name="venCd" style="width:100px;">
								<option value=""><spring:message code="text.srm.field.venCdAll"/></option><%--spring:message : 전체--%>
								<c:forEach var="list" items="${venCds}" varStatus="status">
									<option value="<c:out value="${list.venCd}"/>"><c:out value="${list.venCd}"/></option>
								</c:forEach>
							</select>
						</td>
						<th>
							<label for="countYear"><spring:message code="text.srm.field.countDate"/></label><%--spring:message : 기준년월--%>
						</th>
						<td>
							<srm:codeTag comType="SELECT" dataType="YEAR" subCode="10" objId="countYear" objName="countYear" formName="" width="70px"/>
							<spring:message code="text.srm.field.year"/>
							<srm:codeTag comType="SELECT" dataType="MONTH" objId="countMonth" objName="countMonth" formName="" width="70px"/>
							<spring:message code="text.srm.field.month"/>
						</td>
					</tr>
				</table>
			</form>
			<%-- 검색조건 end ------------------------------------------------%>

			<br>
			<%-- 그리드 영역 Start ---------------------------%>
			<!-- 테이블이 넓을 경우 스크롤 됨. / 테이블 너비는 inline style로 조절 / 기본너비(css) 100%로 설정되어있음. -->
			<div class="tbl_scroll">
			<table class="tbl_st1" style="width:1500px;">
				<colgroup id="tbHead1">
					<col width="4%" />
					<col width="10%" />
					<col width="10%" />
					<col width="6%" />
					<col width="*" />
					<col width="5%" />
					<col width="6%" />
					<col width="8%" />
					<col width="15%" />
					<col width="15%" />
					<col width="7%" />
				</colgroup>
				<thead id="tbHead2">
				<tr bgcolor="#e4e4e4">
					<th><spring:message code="table.srm.colum.title.no"/></th><%--spring:message : No--%>
					<th><spring:message code="table.srm.srmmnt0020.colum.title2"/></th><%--spring:message : 대분류명--%>
					<th><spring:message code="table.srm.srmmnt0020.colum.title3"/></th><%--spring:message : 중분류명--%>
					<th><spring:message code="table.srm.srmmnt0020.colum.title4"/></th><%--spring:message : 파트너사코드--%>
					<th><spring:message code="table.srm.srmmnt0020.colum.title5"/></th><%--spring:message : 파트너사명--%>
					<th><spring:message code="table.srm.srmmnt0020.colum.title6"/></th><%--spring:message : 등급--%>
					<th><spring:message code="table.srm.srmmnt0020.colum.title7"/></th><%--spring:message : 점수--%>
					<th><spring:message code="table.srm.srmmnt0020.colum.title8"/></th><%--spring:message : 개선필요--%>
					<th><spring:message code="table.srm.srmmnt0020.colum.title9"/></th><%--spring:message : 평가그룹--%>
					<th><spring:message code="table.srm.srmmnt0020.colum.title10"/></th><%--spring:message : 템플릿명--%>
					<th><spring:message code="table.srm.srmmnt0020.colum.title11"/></th><%--spring:message : 채널--%>
				</tr>
				</thead>
				<tbody id="dataListbody" />
			</table>
			</div>
			<%-- 그리드 영역 end ---------------------------%>


			<%-- Paging 영역 start ---------------------------%>
			<div id="paging" class="board-pager mt10">
			</div>
			<%-- Paging 영역 end ---------------------------%>

		</div>
	</div>
<form id="popUpForm" name="popUpForm" method="POST">
	<input type="hidden" id="vendorName" name="vendorName"/>
	<input type="hidden" id="evTplSubject" name="evTplSubject"/>
	<input type="hidden" id="countYear" name="countYear"/>
	<input type="hidden" id="countMonth" name="countMonth"/>
	<input type="hidden" id="vendorCode" name="vendorCode"/>
	<input type="hidden" id="catLv2Code" name="catLv2Code"/>
	<input type="hidden" id="egNo" name="egNo"/>
	<input type="hidden" id="evTplNo" name="evTplNo"/>
	<input type="hidden" id="channelCode" name="channelCode"/>
</form>
</body>
</html>