<%@ page  pageEncoding="UTF-8"%>

<!doctype html>
<html lang="ko">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=1100">
	<link href="https://fonts.googleapis.com/css?family=Open+Sans:400,600,700" rel="stylesheet">
	<link href="https://cdn.rawgit.com/hiun/NanumSquare/master/nanumsquare.css" rel="stylesheet" type="text/css">
	
	<%@include file="./SRMCommon.jsp" %>
	
<title><spring:message code="text.srm.field.srmjon0042.sub.title2" /></title><!-- spring:message : 신용평가정보 -->

<script language="JavaScript">
	
	$(document).ready(function($) {
		
		searchCreditInfo();	// 신용평가사별 정보 조회
		
	});

	function searchCreditInfo() {
		var searchInfo = {};
		searchInfo["companyType"] = "<c:out value="${companyType}"/>";
		searchInfo["companyRegNo"] = "<c:out value="${companyRegNo}"/>";

		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/srm/selectCreditAllInfo.json"/>',
			data : JSON.stringify(searchInfo),
			success : function(data) {
				_setTbodyMasterValue(data);
			}
		});
	}
	
	function _setTbodyMasterValue(data) {
		
		setTbodyInit("dataMainListbody"); // dataList 초기화
		
		if (data.length > 0) {
			for (var i=0; i < data.length; i++) {
				data[i].rnum = i+1;
				data[i].creditBasicDate = date_format(data[i].creditBasicDate);
			}
			$("#dataListTemplate").tmpl(data).appendTo("#dataMainListbody");
		} else {
			setTbodyNoResult("dataMainListbody", 4);
		}
	}
	
	/* 날짜 형식으로 바꾸기 */
	function date_format(num){
		return num.replace(/([0-9]{4})([0-9]{2})([0-9]{2})/,"$1-$2-$3");
	}
	
	/* 신용평가정보 리스트 선택 */
	function selectRow(creditRating, creditBasicDate, creditCompanyCode) {
		opener.receiveValue(creditRating, creditBasicDate, creditCompanyCode);
		window.close();
	}
	
</script>

<script id="dataListTemplate" type="text/x-jquery-tmpl">
	<tr style="cursor:pointer;" onClick="selectRow('<c:out value="\${creditRating}" />', '<c:out value="\${creditBasicDate}" />', '<c:out value="\${creditCompanyCode}" />')">
		<td style="text-align :center;"><c:out value="\${rnum}" 			/></td>
		<td style="text-align :center;"><c:out value="\${creditBasicDate}"	/></td>
		<td style="text-align :center;"><c:out value="\${creditRating}"		/></td>
		<td style="text-align :center;"><c:out value="\${creditCompanyName}"/></td>
	</tr>
</script>

</head>

<body>

<!-- popup wrap -->
<div id="popup_wrap">
	<h1 class="popup_logo"><img src="/images/epc/edi/srm/common/logo.png" alt="Lotte Mart"></h1>
	
	<!-- 알림 -->
	<div class="noti_box mt10">
		<ul class="noti_list">
			<li class="txt_l"><em><spring:message code="text.srm.field.srmjon0040Notice2"/></em></li>	<%--spring:message : ※신용평가기관에 롯데마트 정보동의를 요청하셔야 정보확인이 가능합니다.(요청 후 익일 확인가능)--%>
			<li> 
			  사업자번호 또는 법인번호 일치여부 정확히 확인 바랍니다. <br>
			  (기본정보의 법인사업자경우 법인번호 입력)
			</li>
			<li> 
			  법인사업자일 경우 신용평가서의 법인번호와 기본정보에 <br> 
			  입력한 번호 일치여부 확인하시기 바랍니다.
		  </li>   
		</ul>
	</div><!-- END 알림 -->
	
	<div class="tit_btns">
		<h2 class="tit_star"><spring:message code="text.srm.field.srmjon0042.sub.title2" /></h2>	<%-- 신용평가정보--%>
	</div>
	
	<div style="width:100%; overflow-x:hidden; table-layout:fixed; white-space:nowrap;">
		<table class="tbl_st1">
			<colgroup>
				<col width="10%" />
				<col width="30%" />
				<col width="15%" />
				<col width="50%" />
			</colgroup>
			<thead>
			<tr>
				<th><spring:message code='text.srm.field.no' 				/></th><%--No --%>
				<th><spring:message code='text.srm.field.creditBasicDate2' 	/></th><%--기준일자 --%>
				<th><spring:message code='text.srm.field.creditRating' 		/></th><%--신용등급 --%>
				<th><spring:message code='text.srm.field.creditCompanyCode' /></th><%--신용평가사 --%>
			</tr>
			</thead>
			<tbody id="dataMainListbody" />
		</table>
	</div>
</div><!-- END popup wrap -->

</body>
</html>
