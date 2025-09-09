<%@include file="../common.jsp" %>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>

<script>
	function PopupWindow(pageName) {
		var cw=400;
		var ch=300;
		var sw=screen.availWidth;
		var sh=screen.availHeight;
		var px=Math.round((sw-cw)/2);
		var py=Math.round((sh-ch)/2);
		window.open(pageName,"","left="+px+",top="+py+",width="+cw+",height="+ch+",toolbar=no,menubar=no,status=yes,resizable=no,scrollbars=yes");
	}
	
	function doSearch() {
		
		var searchInfo = {};
		
		// 협력업체(개별)
		searchInfo["searchEntpCd"] 		= $("#searchForm select[name='entp_cd']").val();
		
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false, 
				url : '<c:url value="/edi/payment/NEDMPAY0010Select.json"/>',
				data : JSON.stringify(searchInfo),
				success : function(data) {
					//json 으로 호출된 결과값을 화면에 Setting 
					_setTbodyMasterValue(data);
				}
			});
	}

	/* _eventSearch() 후처리(data 객체 그리기) */
	function _setTbodyMasterValue(json) {
		var data = json.paymentList;
		
		setTbodyInit("dataListbody"); // dataList 초기화
		
		if (data.length > 0) {
			$("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
			
		} else {
			setTbodyNoResult("dataListbody", 9);
		}
	}

	function popupSearch(tbName1, tbName2){		
		
		var tbody1 = $('#' + tbName1 + ' tbody');
		var tbody2 = $('#' + tbName2 + ' tbody');

		var vendor 		= $("#searchForm input[name='vendor']").val();
		var vencd 		= $("#searchForm select[name='entp_cd']").val();


		var tmp="";
		
		if(vencd==""){
			tmp=vendor;
		}else{
			tmp=vencd;
		}
		
		var bodyValue = "";
		bodyValue += "<CAPTION><spring:message code='epc.pay.dash.ownerInfo'/><br>";
		bodyValue += "[<spring:message code='epc.ord.vendor'/> : " + tmp + "]";
		bodyValue += "<br>";
		bodyValue += "</CAPTION>";
		bodyValue += tbody1.parent().html();
		bodyValue += tbody2.parent().html();
		//console.log(bodyValue);
		$("#searchForm input[id='staticTableBodyValue']").val(bodyValue);
		//console.log($("#searchForm input[id='staticTableBodyValue']").val());
	
	
		$("#searchForm input[id='name']").val("statics");
		$("#searchForm").attr("target", "_blank");
		$("#searchForm").attr("action", "<c:url value='/edi/comm/NEDPCOM0030.do'/>");
		$("#searchForm").submit();
		
		$("#searchForm").attr("target", "");
		$("#searchForm").attr("action", "");
	}	
</script>
<!-- Data List Templete -------------------------------------------------------------------------------------------------------------->
<script id="dataListTemplate" type="text/x-jquery-tmpl">
<tr class="r1">
	<td align="center"><c:out value="\${strNm}"/></td>
	<td align="center"><c:out value="\${strCd}"/></td>
	<td align="center"><c:out value="\${bmanNo}"/></td>
	<td >&nbsp;<c:out value="\${addr}"/></td>
	<td align="center"><c:out value="\${ceoNm}"/></td>
	<td align="center"><c:out value="\${repTelNo}"/></td>
	<td align="center"><c:out value="\${btyp}"/></td>
	<td align="center"><c:out value="\${bkind}"/></td>
</tr>
</script>

<!-- Data List Templete -------------------------------------------------------------------------------------------------------------->

</head>

<body>

	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>  >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form id="searchForm" name="searchForm" method="post" action="#">
		
		<input type="hidden" name="vendor" value="<c:out value="${paramMap.ven }" />">
		<input type="hidden" id="storeName" name="storeName" />
		
		<input type="hidden" id="staticTableBodyValue" name="staticTableBodyValue">
		<input type="hidden" name="name">
		
		<input type="hidden" id="widthSize" name="widthSize" value="<c:out value="${param.widthSize }" />" >
		<div id="wrap_menu">
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit"><spring:message code='epc.pay.search.condition'/></li>
						<li class="btn">
							<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
 							<a href="#" class="btn" onclick="popupSearch('testTable1','testTable2');"><span><spring:message code="button.common.excel"/></span></a> 
						</li>
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0" >
					
					<colgroup>
						<col style="width:15%" />
						<col style="width:30%" />
						<col style="width:10%" />
						<col style="*" />
					</colgroup>
					<tr>
						<th><spring:message code='epc.pay.search.entpCd'/></th>
						<td colspan="3">
							<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="<c:out value='${param.entp_cd}'/>" dataType="CP" comType="SELECT" formName="form"  />
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
						<li class="tit"><spring:message code='epc.pay.search.result'/></li>
					</ul>
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable1">
					<colgroup>
						<col style="width:90px;" />
						<col style="width:55px;" />
						<col style="width:90px;" />
						<col style="width:200px;"/>
						<col style="width:60px;" />
						<col style="width:90px;" />
						<col style="width:90px;" />
						<col  />
					</colgroup>
					<tr>
						<th><spring:message code='epc.pay.header.strNm'/></th>
						<th><spring:message code='epc.pay.header.strCd'/></th>
						<th><spring:message code='epc.pay.header.commpanyNm'/></th>
						<th><spring:message code='epc.pay.header.addr'/></th>
						<th><spring:message code='epc.pay.header.owner'/></th>
						<th><spring:message code='epc.pay.header.tel'/></th>
						<th><spring:message code='epc.pay.header.btyp'/></th>
						<th><spring:message code='epc.pay.header.bkind'/></th>
					</tr>
					</table>
					
					<div style="width:100%; height:461px;overflow-x:hidden; overflow-y:scroll; table-layout:fixed;">
					
					
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable2">
						<colgroup>
							<col style="width:90px;" />
							<col style="width:55px;" />
							<col style="width:90px;" />
							<col style="width:200px;"/>
							<col style="width:60px;" />
							<col style="width:90px;" />
							<col style="width:90px;" />
							<col  />
						</colgroup>
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
					<li><spring:message code='epc.pay.home'/></li>
					<li><spring:message code='epc.pay.salInfo'/></li>
					<li><spring:message code='epc.pay.dateInfo' /></li>
					<li class="last"><spring:message code='epc.pay.companyInfo'/></li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>
</html>

