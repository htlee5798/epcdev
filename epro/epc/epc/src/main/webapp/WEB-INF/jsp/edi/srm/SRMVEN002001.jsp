<%@ page contentType="text/html; charset=UTF-8"%>

<%--
	Page Name 	: SRMVEN002001.jsp
	Description : 상품검색 팝업
	Modification Information
	
	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2016.07.27		SHIN SE JIN		최초생성
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<%@include file="../common.jsp"%>
	<%@ include file="/common/edi/ediCommon.jsp"%>

	<title></title>

<script language="JavaScript">

	$(document).ready(function() {
		
		$('#prodCd').unbind().keydown(function(e) {	
			if	(e.keyCode == 13) {
				goPage('1');
			}
	   	});
		
		/* RowCount 메뉴 선택시 호충 */
		$('#pageRowCount').live('change',function(){
			goPage();
		});
	});
	
	/* 상품검색 */
	function goPage(pageIndex) {
		
		var searchInfo = {};
		var searchType = $("#MyForm input[name='searchType']:radio:checked").val();
		var prodCd = $("#MyForm input[name='prodCd']").val();
		
		/* 검색내용이 입력되지 않았을 경우 */
		if ($.trim(prodCd) == "") {
			alert("<spring:message code='msg.srm.alert.reqSearchInfo' />");<%--상품코드나 상품명을  입력해주세요. --%>
			$("#MyForm input[name=prodCd]").focus();
			return;
		}
		
		/* 상품코드일때 숫자만 가능하게 */
		if (searchType == '1') {
			var pattern = new RegExp('[^0-9]', 'i');
			if (pattern.exec(prodCd) != null) {
				alert("<spring:message code='msg.srm.alert.number'/>");<%--숫자만 입력할 수 있습니다. --%>
				$("#MyForm input[name=prodCd]").focus();
				$("#MyForm input[name=prodCd]").val("");
				return;
			}
		}
		
		searchInfo["prodCd"] = prodCd;
		searchInfo["searchType"] = searchType;
		searchInfo["houseCode"] = "<c:out value='${param.houseCode}'/>";
		searchInfo["recordCountPerPage"] = $("#MyForm input[name='pageRowCount']:radio:checked").val();
		searchInfo["pageIndex"] = pageIndex;
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/ven/selectProdInfo.json"/>',
			data : JSON.stringify(searchInfo),
			success : function(json) {
				_setTbodyValue(json);
			}
		});
	}
	
	/* List Set */
	function _setTbodyValue(json){
		var data = json.list;
		
		setTbodyInit("dataListbody");	// dataList 초기화
		if (data.length > 0) {
			$("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
			$("#paging").html(json.contents);
		} else {
			setTbodyNoResult("dataListbody", 3);
			$("#paging").html("");
		}
	}
	
	/* 상품검색 리스트 선택 */
	function onSelectRow(materialNumber, descriptionLoc) {
		parent.window.opener.setProdInfo(materialNumber, descriptionLoc);	// 선택한 상품정보 부모창 텍스트에 값 입력
		window.close();
	}
	
</script>

<!-- 상품검색 템플릿 -->
<script id="dataListTemplate" type="text/x-jquery-tmpl">
	<tr class="r1" bgcolor=ffffff onClick="onSelectRow('<c:out value="\${materialNumber}"/>', '<c:out value="\${descriptionLoc}"/>');" style="cursor:pointer;">
		<td style="text-align: center;"><c:out value="\${rnum}"/></td>
		<td style="text-align: center;"><c:out value="\${materialNumber}"/></td>
		<td style="text-align: left;"><c:out value="\${descriptionLoc}"/></td>
	</tr>
</script>

</head>
<body onload="window.focus();">
			
<form id="MyForm" name="MyForm" method="post" action="#" onsubmit="return false">
	<div id="popup">
	    <div id="p_title1">
	        <h1><spring:message code="text.srm.field.prodSearch" /></h1><%--상품검색 --%>
	        <span class="logo"><img src="/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
	    </div>
	    <br>
		<div class="popup_contents">
	
		<!------------------------------ 검색조건 ------------------------------>
		<div class="bbs_search">
			<ul class="tit">
				<li class="tit"><spring:message code='text.srm.field.searchType' /></li><%--검색조건 --%>
				<li class="btn">
					<a href="javascript:goPage(1);"  class="btn"><span><spring:message code="button.srm.find" /></span></a>
	                <a href="javascript:window.close();" class="btn"><span><spring:message code="button.srm.close"/></span></a>
				</li>
			</ul>
			<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
				<col style="width:70px;" />
				<col style="width:110px;"/>
				<tr>
					<th>
						<input type="Radio" name="searchType" value="1" checked="checked" /><spring:message code='text.srm.field.prodCd' /><%--상품코드 --%>
						<input type="Radio" name="searchType" value="2" /><spring:message code='text.srm.field.prodCdNm' /><%--상품명 --%>
					</th>
					
					<td><input type="text" id="prodCd" name="prodCd" style="width:200px;" /></td>
				</tr>	
	        </table> 
		</div>
		<!----- 상품검색 List 내역 Start ------------------------->
		<div class="wrap_con">
			<div class="bbs_list">
				<ul class="tit">
					<li class="tit"><spring:message code='text.srm.field.searchResult' /></li><%--검색내역 --%>
				</ul>
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td>
							<div style="width:100%; height:230px; overflow:auto;">
								<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="tblInfo">
									<colgroup>
										<col style="width:30px;" />
										<col style="width:40px;" />
										<col style="width:100px;" />
									</colgroup>	
									<thead>
										<tr bgcolor="#e4e4e4">
											<th><spring:message code='text.srm.field.no' 		/></th><%--No --%>
											<th><spring:message code='text.srm.field.prodCd' 	/></th><%--상품코드 --%>
											<th><spring:message code='text.srm.field.prodCdNm'	/></th><%--상품명 --%>
										</tr>
									</thead>
									<tbody id="dataListbody" />
								</table>	
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<!----- 상품검색 List 내역 End ------------------------->
		
		<!-- Paging 영역 start --------------------------->
		<div id="pages">
			<span><strong>PageRow : </strong>
				<input type="radio" name="pageRowCount" id="pageRowCount" value="15" checked="checked">15
				<input type="radio" name="pageRowCount" id="pageRowCount" value="30">30
				<input type="radio" name="pageRowCount" id="pageRowCount" value="50">50
				<input type="radio" name="pageRowCount" id="pageRowCount" value="100">100
			</span>
			<div id="paging" class="page"></div>
		</div>
		<!-- Paging 영역 end --------------------------->
						
		</div>
	</div>
</form>
</body>
</html>