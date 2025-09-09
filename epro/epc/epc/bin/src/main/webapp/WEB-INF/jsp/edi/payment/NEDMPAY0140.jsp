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
		
		// 조회기간(From)
		searchInfo["srchStartDate"] 	= $("#searchForm input[name='srchStartDate']").val();
		// 조회기간(To)
		searchInfo["srchEndDate"] 		= $("#searchForm input[name='srchEndDate']").val();
		// 협력업체(개별)
		searchInfo["searchEntpCd"] 		= $("#searchForm select[name='entp_cd']").val();
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			//url : '${ctx}/edi/product/test.json',
			url : '<c:url value="/edi/payment/NEDMPAY0140Select.json"/>',
			data : JSON.stringify(searchInfo),
			success : function(data) {
				
				//json 으로 호출된 결과값을 화면에 Setting 
				_setTbodyMasterValue(data);
			}
		});	
		
		
		/* _eventSearch() 후처리(data  객체 그리기) */
		function _setTbodyMasterValue(json) { 
			var data = json.paymentList;
				
			setTbodyInit("dataListbody");	// dataList 초기화
			
			if (data.length > 0) {
				
				$("#dataListTemplate").tmpl(data).appendTo("#dataListbody"); // setComma(n)
				
			} else { 
				setTbodyNoResult("dataListbody", 9);
			}
		}
	}

function popupSearch(tbName1, tbName2){		
		
		var tbody1 = $('#' + tbName1 + ' tbody');
		var tbody2 = $('#' + tbName2 + ' tbody');		


		var date 		= $("#searchForm input[name='srchStartDate']").val() + "~" + $("#searchForm input[name='srchEndDate']").val();
		var store 		= $("#searchForm input[name='storeName']").val();
		var vendor 		= $("#searchForm input[name='vendor']").val();
		var vencd 		= $("#searchForm select[name='entp_cd']").val();


		var tmp="";
		if(vencd==""){
			tmp=vendor;
		}else{
			tmp=vencd;
		}	
		
		var bodyValue = "";
		bodyValue += "<CAPTION><spring:message code='epc.pay.loan'/><br>";		// 제목 패밀리론으로 수정함 160405 박창민
		bodyValue += "[<spring:message code='epc.ord.period'/> : " + date + "]";
		//bodyValue += "[<spring:message code='epc.ord.store'/> : " + store + "]";
		bodyValue += "[<spring:message code='epc.ord.vendor'/> : " + tmp + "]";
		bodyValue += "<br>";
		bodyValue += "</CAPTION>";
// 		bodyValue += tbody1.parent().html();					//tbName1가 없어서 엑셀 제목에 null로 나와서 삭제함 160405 박창민
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

	function printText(){
		var date 		= $("#searchForm input[name='srchStartDate']").val() + "~" + $("#searchForm input[name='srchEndDate']").val();
		var store 		= $("#searchForm input[name='storeName']").val();
		var vendor 		= $("#searchForm input[name='vendor']").val();
		var vencd 		= $("#searchForm select[name='entp_cd']").val();
		
		var tmp="";
		if(vencd==""){
			tmp=vendor;
		}else{
			tmp=vencd;
		}
		var textData = "";
		textData += "[<spring:message code='epc.ord.period'/> : " + date + "]"
		textData += "[업체 : "+tmp+"]";
		$("#searchForm input[name='textData']").val(textData);
		$("#searchForm input[name='searchEntpCd']").val(vencd);
		
		$("#searchForm").attr("action", "<c:url value='/edi/payment/NEDMPAY0140Text.do'/>");
		$("#searchForm").submit();	
	
	
	}

</script>
<!-- Data List Templete -------------------------------------------------------------------------------------------------------------->
	
<script id="dataListTemplate" type="text/x-jquery-tmpl">
<tr class="r1" bgcolor=ffffff>
	<td align="center"><c:out value="\${slipNo}"/></td>
	<td align="center"><c:out value="\${payDy}"/></td>
	<td align="right"><c:out value="\${sendAmt}"/></td>
	<td align="right"><c:out value="\${ableAmt}"/></td>
	<td align="right"><c:out value="\${splyAmt}"/></td>
	<td align="right"><c:out value="\${logiAmt}"/></td>
	<td align="right"><c:out value="\${salePromoAmt}"/></td>
	<td align="right"><c:out value="\${rtnAmt}"/></td>
	<td align="right"><c:out value="\${payAmt}"/></td>
	<td align="right"><c:out value="\${aaa}"/></td>
	<td align="right"><c:out value="\${bbb}"/></td>
	<td align="right"><c:out value="\${ccc}"/></td>
	<td align="right"><c:out value="\${ddd}"/></td>
</tr>
</script>
<!-- Data List Templete -------------------------------------------------------------------------------------------------------------->
</head>

<body>

	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>  >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form id="searchForm" name="searchForm" method="post" action="#">
			<input type="hidden" name="textData" />
			<input type="hidden" name="vendor" value="<c:out value="${paramMap.ven }" />">
			<input type="hidden" id="storeName" name="storeName" />
			<input type="hidden" id="staticTableBodyValue" name="staticTableBodyValue">
			<input type="hidden" name="name">
			<input type="hidden" id="widthSize" name="widthSize" value="<c:out value="${param.widthSize }" />" >
			
			<input type="hidden"	id="searchEntpCd"	name="searchEntpCd"/>
			
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
 							<a href="#" class="btn" onclick="javascript:printText();"><span><spring:message code="button.common.text"/></span></a> 
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
						<th><span class="star">*</span> <spring:message code='epc.pay.search.searchPeriod'/> </th>
						<td>
							<input type="text" class="day" id="srchStartDate" name="srchStartDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="${paramMap.srchStartDate}" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchStartDate');" style="cursor:hand;" />
							~
							<input type="text" class="day" id="srchEndDate" name="srchEndDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="${paramMap.srchEndDate}" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchEndDate');"  style="cursor:hand;" />
						</td>
							
						<th><spring:message code='epc.pay.search.entpCd'/></th>
						<td colspan="3">
							<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="<c:out value='${param.entp_cd}'/>" dataType="CP" comType="SELECT" formName="form"  defName="전체" />
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
					
					<div style="width:100%; height:485px;overflow-x:hidden; overflow-y:scroll; overflow-x:scroll; table-layout:fixed;">
						<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" width=985 bgcolor=efefef  id="testTable2">
							<colgroup>
								<col style="width:120px;" />
								<col style="width:120px;" />
								<col style="width:120px;" />
								<col style="width:120px;" />
								<col style="width:120px;" />
								<col style="width:120px;" />
								<col style="width:120px;" />
								<col style="width:120px;" />
								<col style="width:120px;" />
								<col style="width:120px;" />
								<col style="width:120px;" />
								<col style="width:120px;" />
								<col style="width:120px;" />
							</colgroup>
							<tr bgcolor="#e4e4e4">
								<th rowspan="2"><spring:message code='epc.pay.header.hCode16'/></th>
								<th rowspan="2"><spring:message code='epc.pay.header.hCode17'/></th>
								<th colspan="2"><spring:message code='epc.pay.header.hCode18'/></th>
								<th colspan="5"><spring:message code='epc.pay.header.hCode19'/></th>
								<th colspan="2"><spring:message code='epc.pay.header.hCode21'/></th>
								<th colspan="2"><spring:message code='epc.pay.header.hCode22'/></th>
							</tr>
							<tr bgcolor="#e4e4e4">
								<th><spring:message code='epc.pay.header.hCode23'/></th>
								<th><spring:message code='epc.pay.header.hCode24'/></th>
								<th><spring:message code='epc.pay.header.hCode25'/>/</th>
								<th><spring:message code='epc.pay.header.hCode26'/></th>
								<th><spring:message code='epc.pay.header.hCode27'/></th>
								<th><spring:message code='epc.pay.header.hCode28'/></th>
								<th><spring:message code='epc.pay.header.hCode29'/></th>
								<th><spring:message code='epc.pay.header.hCode30'/></th>
								<th><spring:message code='epc.pay.header.hCode31'/></th>
								<th><spring:message code='epc.pay.header.hCode32'/></th>
								<th><spring:message code='epc.pay.header.hCode33'/></th>
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
					<li><spring:message code='epc.pay.home'/></li>
					<li><spring:message code='epc.pay.salInfo'/></li>
					<li><spring:message code='epc.pay.siljukInfo'/></li>
					<li class="last"><spring:message code='epc.pay.loan'/></li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>
</html>

