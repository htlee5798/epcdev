<%@ page  pageEncoding="UTF-8"%>

<%--
	Page Name 	: SRMVEN003005.jsp
	Description : SRM모니터링 상세 > PLC 등급 팝업
	Modification Information

	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2016.07.22		LEE HYOUNG TAK	최초생성
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
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
				url : '<c:url value="/edi/ven/selectSRMmoniteringDetailPlcList.json"/>',
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

<form name="MyForm"  id="MyForm" method="post">
	<div class="con_area" style="width:460px;">
		<div id="p_title1">
			<span class="logo"><img src="/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
		</div>
		<p style="margin-top: 10px;" />
		<div class="div_tit_dot">
			<div class="div_tit">
				<tt>PLC <spring:message code="text.srm.field.grade"/></tt>
			</div>
		</div>

		<div style="width:100%; height:350px;overflow-x:hidden; table-layout:fixed; white-space:nowrap;">
			<div class="tbl_default">
				<table>
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
		</div>
		<br>
		<div class="div_btn">
			<input type="button" id="" name="" value="<spring:message code='button.srm.close'/>" class="btn_normal btn_blue" onclick="func_ok();" /><%--spring:message : 닫기--%>
		</div>
	</div>

</form>

</body>
</html>