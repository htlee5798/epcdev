<%@ page  pageEncoding="UTF-8"%>

<%--
	Page Name 	: SRMMNT002005.jsp
	Description : SRM모니터링 상세 > PLC 등급 팝업
	Modification Information

	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2016.07.22		LEE HYOUNG TAK	최초생성
--%>

<!doctype html>
<html lang="ko">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<%@include file="./SRMCommon.jsp" %>

	<title>PLC<spring:message code="text.srm.field.grade"/></title><%--spring:message : 평가기관 정보--%>
	<link href='/css/epc/edi/srm/srm.css' type="text/css" rel="stylesheet">

	<script language="JavaScript">
		/* 화면 초기화 */
		$(document).ready(function() {
			$('.order').live("mouseover", function(){
				$(this).find('span[id^=order]').show();
			}).live("mouseout", function(){
				$(this).find('span[id^=order]').hide();
			});

		});


		//닫기
		function func_ok() {
			window.close();
		}

		//정렬
		function orderBy(obj, order){
			var orderValue = $(obj).find('input[name^=orderValue]').val();

			$('span[id=order1]').text("▼");
			$('input[name=orderValue2]').val("asc");

			$('span[id=order2]').text("▼");
			$('input[name=orderValue2]').val("asc");

			$('span[id=order3]').text("▼");
			$('input[name=orderValue3]').val("asc");

			if(orderValue == "desc") {
				$('span[id=order'+order+']').text("▼");
				$('input[name=orderValue'+order+']').val("asc");
			} else if(orderValue == "asc") {
				$('span[id=order'+order+']').text("▲");
				$('input[name=orderValue'+order+']').val("desc");
			}

			var saveInfo = {};
			saveInfo["order"] = order;
			saveInfo["orderValue"] = orderValue;
			saveInfo["countYear"] = "<c:out value="${vo.countYear}"/>";
			saveInfo["countMonth"] = "<c:out value="${vo.countMonth}"/>";
			saveInfo["vendorCode"] = "<c:out value="${vo.vendorCode}"/>";
			saveInfo["catLv2Code"] = "<c:out value="${vo.catLv2Code}"/>";

			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/mnt/selectSRMmoniteringDetailPlcList.json"/>',
				data : JSON.stringify(saveInfo),
				success : function(data) {
					_setTbodyMasterValue(data);
				}
			});

		}

		/* _eventSearch() 후처리(data  객체 그리기) */
		function _setTbodyMasterValue(data) {

			setTbodyInit("dataListbody");	// dataList 초기화

			if (data.length > 0) {
				//console.log('pid = ' + data[0].pid);
				$("#dataListTemplate").tmpl(data).appendTo("#dataListbody"); // setComma(n)

			} else {
				setTbodyNoResult("dataListbody", 3);
			}

		}
	</script>

	<script id="dataListTemplate" type="text/x-jquery-tmpl">
		<tr>
			<td style="text-align: center;"><c:out value="\${skuNumber}"/></td>
			<td><c:out value="\${skuName}"/></td>
			<td style="text-align: center;"><c:out value="\${plcGrade}"/></td>
		</tr>
	</script>

</head>


<body>
	<!-- popup wrap -->
	<div id="popup_wrap_full">
		<h1 class="popup_logo"><img src="/images/epc/edi/srm/common/logo.png" alt="Lotte Mart"></h1>

		<div class="tit_btns">
			<h2 class="tit_star">PLC <spring:message code="text.srm.field.grade"/></h2>	<%-- 대분류 조회--%>
		</div>

		<div style="width:100%; height:350px;overflow-x:hidden; table-layout:fixed; white-space:nowrap;">
			<table class="tbl_st1">
				<colgroup>
					<col width="20%" />
					<col width="*" />
					<col width="18%" />
				</colgroup>
				<thead>
				<tr>
					<th>
					<span class="order" onclick="javascript:orderBy(this,'1')">
					<spring:message code="text.srm.field.prodCd"/>
						<span id="order1" style="display:none;">▼</span>
						<input type="hidden" id="orderValue1" name="orderValue1" value="asc"/>
					</span>
					</th>
					<th>
					<span class="order" onclick="javascript:orderBy(this,'2')">
						<spring:message code="text.srm.field.prodCdNm"/>
						<span id="order2" style="display:none;">▼</span>
						<input type="hidden" id="orderValue2" name="orderValue2" value="asc"/>
					</span>
					</th>
					<th>
					<span class="order" onclick="javascript:orderBy(this,'3')">
						PLC<spring:message code="text.srm.field.grade"/>
						<span id="order3" style="display:none;">▼</span>
						<input type="hidden" id="orderValue3" name="orderValue3" value="asc"/>
					</span>
					</th>
				</tr>
				</thead>
				<tbody id="dataListbody">
				<c:if test="${not empty list}">
					<c:forEach var="list" items="${list}" varStatus="status">
						<tr>
							<td style="text-align: center;"><c:out value="${list.skuNumber}"/></td>
							<td><c:out value="${list.skuName}"/></td>
							<td style="text-align: center;"><c:out value="${list.plcGrade}"/></td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty list}">
					<tr><td colspan="3" style="text-align: center;"><spring:message code="msg.srm.alert.srmjon0030.notListData"/></td></tr>
				</c:if>
				</tbody>
			</table>
		</div>
		<!-- END 정보 입력폼 -->
		<p class="align-c mt10"><input type="button" id="" name="" value="<spring:message code='button.srm.close'/>" class="btn_normal btn_blue" onclick="func_ok();" /><%--spring:message : 닫기--%></p>
	</div><!-- END popup wrap -->


</body>
</html>