<%@include file="../common.jsp"%>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<%--
	Page Name 	: SRMVEN0040.jsp
	Description : SRM 정보 > 품질경영평가조치
	Modification Information

	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2016.10.07		SHIN SE JIN		최초생성
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title>품질경영평가조치</title>

<script language="JavaScript">
	/* 화면 초기화 */
	$(document).ready(function() {
		goPage("1");
	});

	/* 파트너사 품질경영평가 내역 조회 */
	function goPage(pageIndex) {

		var searchInfo = {};

		searchInfo["houseCode"] = $("input[name='houseCode']").val();	// 하우스코드
		searchInfo["pageIndex"] = pageIndex;

		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/ven/selectEvalInfoList.json"/>',
			data : JSON.stringify(searchInfo),
			success : function(data) {
				_setTbodyMainValue(data);
			}
		});
	}

	/*파트너사 품질경영평가 내역 List Set */
	function _setTbodyMainValue(json){
		var data = json.list;
		setTbodyInit("dataMainListbody");	// dataList 초기화
		if (data.length > 0) {
			for (var i = 0; i < data.length; i++) {
				data[i].reqDate = date_format(data[i].reqDate);		//요청일자 날짜형식으로 변환
				data[i].evalDate = date_format(data[i].evalDate);	//평가일자 날짜형식으로 변환
			}
			$("#dataMainListTemplate").tmpl(data).appendTo("#dataMainListbody");
			$("#paging").html(json.contents);
		} else {
			setTbodyNoResult("dataMainListbody", 8);
			$("#paging").html();
		}
	}

	/*시행조치 팝업*/
	function doImpPopup(evNo, seq) {
		$("#frmHidden input[name='evNo']").val(evNo);
		$("#frmHidden input[name='seq']").val(seq);
		var cw = 780;
		var ch = 700;
		var url = "<c:url value='/edi/ven/selectCorrectiveActionPopup.do' />";
		var sw = screen.availWidth;
		var sh = screen.availHeight;
		var px = Math.round((sw-cw)/2);
		var py = Math.round((sh-ch)/2);

		var win = window.open("", "popup", "left=" + px + ",top=" + py + ",width=" + cw + ",height=" + ch + ",toolbar=no,menubar=no,status=no,resizable=no,scrollbars=yes");

		$("#frmHidden").attr("action", url);
		$("#frmHidden").attr("target", "popup");
		$("#frmHidden").submit();
		$("#frmHidden").attr("target", "_self");

	}

	/* 날짜 형식으로 바꾸기 */
	function date_format(num){
		if (num != null) {
			return num.replace(/([0-9]{4})([0-9]{2})([0-9]{2})/,"$1-$2-$3");
		} else {
			return num;
		}
	}

</script>

<!-- 품질경영평가조치 리스트 템플릿 -->
<script id="dataMainListTemplate" type="text/x-jquery-tmpl">
	<tr>
		<td style="text-align: center;"><c:out value="\${rnum}"/></td>
		<td style="text-align: center;"><c:out value="\${reqDate}"/></td>
		<td style="text-align: center;"><c:out value="\${evalDate}"/></td>
		<td style="text-align: center;"><c:out value="\${evalSellerNameLoc}"/></td>
		<%--<td style="text-align: center;"><c:out value="\${userNameLoc}"/></td>
		<td style="text-align: center;"><c:out value="\${officeTelNo}"/></td>--%>
		<td style="text-align: center;"><c:out value="\${statusNm}"/></td>
		{%if totImpCnt != 0 %}
			<td style="text-align: center; cursor:pointer;" onClick="javascript:doImpPopup('<c:out value='\${evalNoResult}'/>','<c:out value='\${visitSeq}'/>')">
				(<c:out value="\${impCnt}"/>/<c:out value="\${totImpCnt}"/>)
			</td>
		{%else%}
			<td class="click" style="text-align: center;">
				-
			</td>
		{%/if%}
	</tr>
</script>

</head>

<body>
	<div id="content_wrap">
		<div>
			<form name="MyForm"  id="MyForm" method="post" enctype="multipart/form-data">
				<div id="wrap_menu">
					<!----- 파트너사 인증정보 내역 Start ------------------------->
					<div class="wrap_con">
						<div class="bbs_list">
							<ul class="tit">
								<li class="tit">파트너사 품질경영평가</li>
								<li class="btn">
									<a href="#" class="btn" onclick="goPage('1');"><span><spring:message code="button.srm.search2"/></span></a><%--조회 --%>
								</li>
							</ul>
							<table class="bbs_list" cellpadding="0" cellspacing="0" border="1" style="border-color:#88a1b6; border-style: solid;" id="tblInfo" name="tblInfo">
								<colgroup>
									<col width="5%" />
									<col width="10%" />
									<col width="10%" />
									<col width="45%" />
									<%--<col width="10%" />--%>
									<%--<col width="15%" />--%>
									<col width="20%" />
									<col width="10%" />
								</colgroup>

								<tr bgcolor="#e4e4e4">
									<th>No</th><%-- --%>
									<th>요청일자</th><%-- --%>
									<th>평가일자</th><%-- --%>
									<th>평가기관명</th><%-- --%>
									<%--<th>담당자</th>--%><%-- --%>
									<%--<th>연락처</th>--%><%-- --%>
									<th>진행상태</th><%-- --%>
									<th>시정조치</th><%-- --%>
								</tr>

								<tbody id="dataMainListbody" />
							</table>
						</div>
					</div>
					<!----- 파트너사 인증정보 내역 End ------------------------->
					<!-- Paging 영역 start --------------------------->
					<div id="pages">
						<div id="paging" class="page"></div>
					</div>
					<!-- Paging 영역 end --------------------------->
				</div>
			</form>
		</div>
	</div>
	<form id="frmHidden" name="frmHidden" method="post">
		<input type="hidden" id="houseCode" name="houseCode" value="000"/>
		<input type="hidden" id="evNo"		name="evNo"					/>
		<input type="hidden" id="seq"		name="seq"					/>
	</form>
</body>
</html>