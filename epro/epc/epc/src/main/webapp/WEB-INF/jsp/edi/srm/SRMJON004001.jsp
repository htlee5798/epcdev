<%@ page  pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!doctype html>
<html lang="ko">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=1100">
	<link href="https://fonts.googleapis.com/css?family=Open+Sans:400,600,700" rel="stylesheet">
	<link href="https://cdn.rawgit.com/hiun/NanumSquare/master/nanumsquare.css" rel="stylesheet" type="text/css">
	
	<%@include file="./SRMCommon.jsp" %>
	
	<title></title>

<script language="JavaScript">
	var idx = "";
	
	/* onLoad or key event */
	$(document).ready(function($) {
		idx = '<c:out value="${param.idx}" />';
		// 조회조건 입력필드 enter key이벤트
		$('#srchStreetNm, #srchJiBun').unbind().keydown(function(e) {
			switch (e.which) {
			case 13:
				goPage('1');
				break; // enter
			default:
				return true;
			}
			e.preventDefault();
		});
		
		// 목록 Click Event
		$('#dataListbody tr').live('click', function(e) {
			var obj = $(this).children().last();	// last-td
			
			_setZipInfo(obj);
		});
		
		// 지번/도로명 선택 초기화
		$("input[name='srchGbn']").eq(0).attr("checked", true);
		fnSearchChange("1");
	});
	
	function _setZipInfo(obj) {
		var zipCd 	= $(obj).children("input[name='zipCd']").val();
		var juso 	= $(obj).children("input[name='juso']").val();
		
		if (zipCd != "") {
			//console.log(zipCd);
			//console.log(juso);
			
			opener.receiveValue(zipCd, juso);
			window.close();
		}
		
	}

	function goPage(pageIndex) {
		var searchInfo = {};
		var cityNm = "";
		var srchGbn 		= $("input[name='srchGbn']:checked").val();
		if (srchGbn == "1") {
			cityNm 			= $("#trLoad #cityNm").val();
		} else {
			cityNm 			= $("#trJiBun #cityNm").val();
		}

		var srchStreetNm 	= ($("#srchStreetNm").val()).trim();
		var srchJiBun		= $("input[name='srchJiBun']").val();
		var srchStreetNmAl 	= srchStreetNm.split(" ");
		//console.log(srchStreetNmAl);
		//return;
		
		if (srchGbn == "1") {
			if (cityNm == "") {
				alert("지역을 선택해주세요.");
				$("#cityNm").focus();
				return;
			}
			
			if (srchStreetNm == "") {
				alert("도로명 주소를 입력해주세요.");
				$("#srchStreetNm").focus();
				return;
			}
			
			searchInfo["srchStreetNm"] = srchStreetNmAl[0];
			searchInfo["srchBidngMainNo"] = srchStreetNmAl[1];
		} else {
			if (srchJiBun == "") {
				alert("읍/면/동을 입력해주세요.");
				$("#srchJiBun").focus();
				return;
			}
			
			searchInfo["srchStreetNm"] = srchJiBun;
		}
		
		searchInfo["cityNm"] = cityNm;
		
		searchInfo["srchGbn"] = $("input[name='srchGbn']:checked").val();
		searchInfo["pageIndex"] = pageIndex;
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/srm/selectZipList.do" />',
			data : JSON.stringify(searchInfo),
			success : function(data) {
				$("#spTotCnt").text("");
				
				_setTbodyMasterValue(data);
			}
		});
	}
	
	/* _eventSearch() 후처리(data 마스터코드 객체 그리기) */
	function _setTbodyMasterValue(json) {
		var data = json.listData;
		setTbodyInit("dataListbody"); // dataList 초기화
		
		if (data.length > 0) {
			$("#spTotCnt").attr("style", "color:#FF0000");
			$("#spTotCnt").text(" (총 " + json.totCnt + "건)");
			/* for (var i = 0; i < data.length; i++) {
				data[i]["idx"] = i + 1;
			} */
			
			$("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
			$("#paging").html(json.contents);
		} else {
			setTbodyNoResult("dataListbody", 10);
		}
	}
	
	/* 지번/도로명 변경 이벤트 */
	function fnSearchChange(value) {
		if (value == "0") {		// 지번검색
			$("#trLoad").hide();
			$("#trJiBun").show();
		} else {
			$("#trJiBun").hide();
			$("#trLoad").show();
		}
	}
</script>

<script id="dataListTemplate" type="text/x-jquery-tmpl">
	<tr style="cursor:pointer;">
		<!-- <td align="center"><c:out value="\${idx}" /></td> -->
		<td align="center"><c:out value="\${zipCd}" /></td>
		<td>
			<c:out value="\${juso}" />
			<input type="hidden" id="zipCd" name="zipCd" value="<c:out value="\${zipCd}" />" />
			<input type="hidden" id="juso" name="juso" value="<c:out value="\${juso}" />" />
		</td>
	</tr>
</script>

</head>

<body>
	<div id="popup_wrap">
		<h1 class="popup_logo"><img src="/images/epc/edi/srm/common/logo.png" alt="Lotte Mart"></h1>
	
		<div class="tit_btns">
			<h2 class="tit_star">우편번호검색</h2>	<%-- 대분류 조회--%>
		</div>
	
		<table class="tbl_st1 form_style">
			<colgroup>
				<col width="20%" />
				<col width="*" />
			</colgroup>
			<tr>
				<th>검색조건</th>
				<td>
					<input type="radio" id="srchGbn" name="srchGbn" value="1" checked="checked" onClick="fnSearchChange(this.value);"> 도로명검색
					<input type="radio" id="srchGbn" name="srchGbn" value="0" onClick="fnSearchChange(this.value);"> 지번검색
					<button type="button" class="btn_normal" onclick="javascript:goPage('1');" style="float:right;"><spring:message code="button.common.inquire"/></button>	<%--조회--%>
				</td>
			</tr>
	
			<tr id="trJiBun" style="display: none;">
				<th>읍/면/동</th>
				<td>
					<input type="text" id="srchJiBun" name="srchJiBun" style="width: 120px; ime-mode:auto;">
					<input type="hidden" id="cityNm" name="cityNm" />
				</td>
			</tr>
			<tr id="trLoad">
				<th>도로명 주소</th>
				<td>
					<select id="cityNm" name="cityNm">
						<option value=''>선택</option>
						<option value='강원'>강원</option>
						<option value='경기'>경기</option>
						<option value='경남'>경남</option>
						<option value='경북'>경북</option>
						<option value='광주'>광주</option>
						<option value='대구'>대구</option>
						<option value='대전'>대전</option>
						<option value='부산'>부산</option>
						<option value='서울'>서울</option>
						<option value='세종'>세종</option>
						<option value='울산'>울산</option>
						<option value='인천'>인천</option>
						<option value='전남'>전남</option>
						<option value='전북'>전북</option>
						<option value='제주'>제주</option>
						<option value='충남'>충남</option>
						<option value='충북'>충북</option>
					</select>
					<input type="text" id="srchStreetNm" name="srchStreetNm" style="ime-mode:auto;">
					<br> ☞ 도로명 + 건물번호 : <font color="#FF8055">예)종로 6</font>
				</td>
			</tr>
	
			<tr id="trJiBun" style="display: none;">
				<th>*읍/면/동</th>
				<td>
					<input type="text" id="srchJiBun" name="srchJiBun" style="ime-mode:auto;">
					<input type="hidden" id="cityNm" name="cityNm" />
				</td>
			</tr>
		</table>
	
		<div class="tit_btns">
			<h2 class="tit_star">검색내역<span id="spTotCnt" /></h2>
		</div>
		<!-- -------------------------------------------------------- -->
		<!--	검색내역 	-->
		<!-- -------------------------------------------------------- -->
	
		<table class="tbl_st1 form_style" id="testTable1">
			<colgroup>
				<col width="100" />
				<col width="*" />
				<col width="18" />
			</colgroup>
			<tbody>
				<tr>
					<!-- <th>순번</th> -->
					<th>우편번호</th>
					<th>주소</th>
					<th>&nbsp;</th>
				</tr>
			</tbody>
		</table>
	
		<div style="width:100%; height:260px; overflow:auto;">
			<table class="tbl_st1" cellpadding="0" cellspacing="0" border="0" id="testTable2">
				<colgroup>
					<col width="100" />
					<col width="*" />
					<%-- <col width="18" /> --%>
				</colgroup>
	
				<tbody id="dataListbody" />
	
			</table>
		</div>
		
		<!-- Paging 영역 start --------------------------->
		<div class="board-pager mt10" id="paging">
		</div>
		<!-- Paging 영역 end --------------------------->

	</div>
	
</body>
</html>
