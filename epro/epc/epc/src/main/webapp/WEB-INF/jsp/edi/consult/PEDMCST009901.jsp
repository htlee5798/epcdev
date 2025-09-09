<%@include file="../common.jsp" %>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="html" uri="http://lcnjf.lcn.co.kr/taglib/edi"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<script type="text/javascript"
	src="<c:url value="/js/jquery/jquery.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/js/common/Ui_common.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/js/common/common.js"/>"></script>

<script language="JavaScript">
	var idx = "";
	
	/* onLoad or key event */
	$(document).ready(function($) {
		idx = '<c:out value="${param.idx}" />';
		// 조회조건 입력필드 enter key이벤트
		$('#srchStreetNm').unbind().keydown(function(e) {
			switch (e.which) {
			case 13:
				doZipSearch(this);
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

	function doZipSearch() {
		var cityNm 			= $("#cityNm").val();
		var srchStreetNm 	= ($("#srchStreetNm").val()).trim();
		var srchStreetNmAl 	= srchStreetNm.split(" ");
		//console.log(srchStreetNmAl);
		//return;
		
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
		
		var searchInfo = {};
		searchInfo["cityNm"] = $("#cityNm").val();
		searchInfo["srchStreetNm"] = srchStreetNmAl[0];
		searchInfo["srchBidngMainNo"] = srchStreetNmAl[1];
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/consult/selectZipList.do" />',
			data : JSON.stringify(searchInfo),
			success : function(data) {
				$("#spTotCnt").text("");
				
				_setTbodyMasterValue(data);
			}
		});
	}
	
	/* _eventSearch() 후처리(data 마스터코드 객체 그리기) */
	function _setTbodyMasterValue(json) {
		var data = json.resultList;
		setTbodyInit("dataListbody"); // dataList 초기화
		
		if (data.length > 0) {
			$("#spTotCnt").attr("style", "color:#FF0000");
			$("#spTotCnt").text(" (총 " + data.length + "건)");
			/* for (var i = 0; i < data.length; i++) {
				data[i]["idx"] = i + 1;
			} */
			
			$("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
		} else {
			setTbodyNoResult("dataListbody", 10);
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
<div id="popup">
	<!------------------------------------------------------------------ -->
	<!--    title -->
	<!------------------------------------------------------------------ -->
	<div id="p_title1">
		<h1>우편번호검색</h1>
		<span class="logo"><img src="/images/epc/popup/logo_pop.gif"
			alt="LOTTE MART" /></span>
	</div>
	<!-------------------------------------------------- end of title -- -->

	<!------------------------------------------------------------------ -->
	<!--    process -->
	<!------------------------------------------------------------------ -->
	<!--    process 없음 -->
	<br>
	<!------------------------------------------------ end of process -- -->
	<div class="popup_contents">

		<!------------------------------------------------------------------ -->
		<!-- 	검색조건 -->
		<!------------------------------------------------------------------ -->
		<div class="bbs_search">
			<ul class="tit">
				<li class="tit">검색조건</li>
				<li class="btn">
					<a href="javascript:doZipSearch();" class="btn"><span><spring:message code="button.common.inquire" /></span></a>
				</li>
			</ul>
		</div>
		<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<th><span class="star">*</span>도로명 주소 <br> ☞ 도로명 + 건물번호 <font color="#FF8055">예)종로 6</font>
				</th>
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
					<input type="text" id="srchStreetNm" name="srchStreetNm" style="width: 120px; ime-mode:auto;">
				</td>
			</tr>
		</table>
		
		<!-- -------------------------------------------------------- -->
		<!--	검색내역 	-->
		<!-- -------------------------------------------------------- -->
		<div class="wrap_con">
			<!-- list -->
			<div class="bbs_list">
				<ul class="tit">
					<li class="tit">검색내역<span id="spTotCnt" /></li>
				</ul>

				<table class="bbs_list" cellpadding="0" cellspacing="0"
					border="0" id="testTable1">
					<colgroup>
						<%-- <col style="width: 50px;" /> --%>
						<col style="width: 50px;" />
						<col style="width: *;" />
						<col style="width: 17px;" />
					</colgroup>
					<tr>
						<!-- <th>순번</th> -->
						<th>우편번호</th>
						<th>주소</th>
						<th>&nbsp;</th>
					</tr>
				</table>
				
				<div style="width:100%; height:230px; overflow:auto;">
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable2">
						<colgroup>
							<%-- <col style="width: 50px;" /> --%>
							<col style="width: 50px;" />
							<col style="width: *;" />
						</colgroup>
						
						<tbody id="dataListbody" />
						
					</table>
				</div>
			</div>
		</div>

		<!-- -------------------------------------------------------- -->
		<!--    footer  -->
		<!-- -------------------------------------------------------- -->
		<div id="footer">
			<div id="footbox">
				<div class="msg" id="resultMsg"></div>
			</div>
		</div>
		<!---------------------------------------------end of footer -->
	</div>

</div>
<!-- id popup// -->
	
	
</body>
</html>
