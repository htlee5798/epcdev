<%@include file="../common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>

<script>
	/* onLoad or key event */
	$(document).ready(function($) {
		_eventSearch();
	});
	
	function PopupWindow(pageName) {
		var cw=400;
		var ch=440;
		var sw=screen.availWidth;
		var sh=screen.availHeight;
		var px=Math.round((sw-cw)/2);
		var py=Math.round((sh-ch)/2);
		
		window.open(pageName,"","left="+px+",top="+py+",width="+cw+",height="+ch+",toolbar=no,menubar=no,status=yes,resizable=no,scrollbars=yes");
	}

	function _eventSearch() {
		var searchInfo = {};
		
		$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/consult/selectPEDMCST0099.do" />',
				data : JSON.stringify(searchInfo),
				success : function(data) {
					_setTbodyMasterValue(data);
				}
			});
	}
	
	/* _eventSearch() 후처리(data 마스터코드 객체 그리기) */
	function _setTbodyMasterValue(json) {
		var data = json.resultList;
		setTbodyInit("dataListbody"); // dataList 초기화
		
		if (data.length > 0) {
			for (var i = 0; i < data.length; i++) {
				data[i]["idx"] = i + 1;
				data[i].beforeZip	=	data[i].beforeZip.substr(0, 3) + "-" +	data[i].beforeZip.substr(3) 	
			}
			
			$("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
		} else {
			setTbodyNoResult("dataListbody", 10);
		}
	}
	
	var gIdx = "";
	function searchZipCd(idx) {
		//alert(idx);
		gIdx = parseInt(idx);
		PopupWindow("<c:url value='/edi/consult/PEDMCST009901.do'/>?idx=" + idx);
	}
	
	function receiveValue(zipCd, juso) {
		//alert(zipCd + "::" + juso);
		if (zipCd != "") {
			//alert(gIdx);
			form1.zipCd[gIdx].value = zipCd;
			form1.addr1[gIdx].value = juso;
			//$("#zipCd").eq(gIdx).val(zipCd);
			//$("#juso").eq(gIdx).val(juso);
		}
	}
	
	/* 저장 */
	function _eventSave() {
		var paramInfo = {};
		
		var zipRow = $("#testTable2 tr").length;
		//console.log(zipRow);
		
		if (zipRow <= 1) {
			alert("저장할 주소를 입력해주세요.");
			return;
		}
		
		if (!confirm("<spring:message code="msg.common.confirm.save" />")) {
			return;
		}
		
		var zipInfoAl = new Array();
		for (var i = 1; i < zipRow; i++) {
			$row = $("#testTable2 tr:eq(" + i + ")");
			if ($row != null && $row != "") {
				var info = {};
				$row.find("input").each(function() {
					// 일자일 경우 '-' 제거
					/* if (this.name == "zipCd") {
						if ($(this).val() == "") {
							return;
						}
					} */
					info[this.name] = $(this).val();
				});
				
				zipInfoAl.push(info);
			}
		}
		
		paramInfo["zipInfoAl"] = zipInfoAl;
		
		//console.log(paramInfo);
		//return;
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/consult/saveZip.do" />',
			data : JSON.stringify(paramInfo),
			success : function(data) {
				if (data.msgCd == "0") {
					alert("<spring:message code="msg.common.success.save" />");
				}
			}
		});
	}
</script>

<script id="dataListTemplate" type="text/x-jquery-tmpl">
	<tr>
		<td align="center">
			<c:out value="\${bmanNo}" />
			<input type="hidden" id="bmanNo" name="bmanNo" value="<c:out value="\${bmanNo}" />" />
		</td>
		<td><c:out value="\${venNm}" /></td>
		<td align="center">
			<input type="text" id="zipCd" name="zipCd" style="width:50PX; text-align:center;" value="<c:out value="\${zipCd}" />" readonly />
			<input type="button"	onclick="searchZipCd('<c:out value="\${idx}" />');"	value="찾기"	style="cursor:pointer"></input>		
		</td>
		<td>
			구주소 : \${beforeZip}		\${beforeAddr1}		\${beforeAddr2}
			<br/>
			<input type="text" id="addr1" name="addr1"  value="<c:out value="\${addr1}" />"		style="width:250px;"/>
			<input type="text" id="addr2" name="addr2"  value="<c:out value="\${addr2}" />"		style="width:100px;" />
		</td>
	</tr>
</script>

</head>

<body>
<form name="form1">
	<div id="content_wrap"
		<c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>>
		<div>
			<form name="searchForm" method="post" action="#">
				<input type="hidden" name="staticTableBodyValue" />
				<input type="hidden" id="widthSize" name="widthSize" value="${param.widthSize }" />
				<div id="wrap_menu">
					<!--	@ 검색조건	-->
					<div class="wrap_search">
						<!-- 01 : search -->
						<div class="bbs_search">
							<ul class="tit">
								<li class="tit">검색</li>
								<li class="btn">
									<a href="#" class="btn" onclick="_eventSearch();"><span><spring:message code="button.common.inquire" /></span></a>
									<%-- <a href="#" class="btn" onclick="_eventSave();"><span><spring:message code="button.common.save" /></span></a> --%>
								</li>
							</ul>
						</div>
						<!-- 1검색조건 // -->
					</div>
					
					<!--	2 검색내역 	-->
					<div class="wrap_con">
						<!-- list -->
						<div class="bbs_list">
							<ul class="tit">
								<li class="tit">검색내역</li>
							</ul>

							<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable1">
								<colgroup>
									<col style="width: 80px;" />
									<col style="width: 150px;" />
									<col style="width: 150px;" />
									<col />									
								</colgroup>								
								<tr>
									<th rowspan="2">사업자번호</th>
									<th rowspan="2">업체명</th>
									<th rowspan="2">우편번호</th>									
									<th>이전주소</th>
								</tr>
								<tr>
									<th>신주소</th>
								</tr>
							</table>
							
							<div class="datagrade_scroll_sum">
								<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable2">
									<colgroup>
										<col style="width: 80px;" />
										<col style="width: 150px;" />
										<col style="width: 150px;" />
										<col />										
									</colgroup>
									
									<tr style="display: block;">
										<td>
											<input type="hidden" id="bmanNo" name="bmanNo" />
											<input type="hidden" id="zipCd" name="zipCd" value="134" />
											<input type="hidden" id="addr1" name="addr1" />
											<input type="hidden" id="addr2" name="addr2" />
										</td>
									</tr>
									
									<tbody id="dataListbody" />
									
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
						<li>홈</li>
						<li>매입정보</li>
						<li>기간정보</li>
						<li class="last">일자별</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- footer //-->
	</div>

</form>

</body>
</html>
