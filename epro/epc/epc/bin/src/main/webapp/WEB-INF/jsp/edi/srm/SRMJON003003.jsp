<%@ page  pageEncoding="UTF-8"%>
<%--
	Page Name 	: SRMJON003002.jsp
	Description : 대분류 선택 팝업
	Modification Information
	
	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2016.07.08 		LEE HYOUNG TAK 	최초생성
--%>

<!doctype html>
<html lang="ko">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<%@include file="./SRMCommon.jsp" %>

	<title><spring:message code="text.srm.field.srmevl003003.title"/></title><%--spring:message : 대분류 조회--%>

	<script>
		$(document).ready(function() {
			doSearch();
			// 조회조건 입력필드 enter key이벤트
			$('#catLv1Code, #remark').unbind().keydown(function(e) {
				switch (e.which) {
					case 13: doSearch(); break; // enter
					default: return true;
				}
			});
		});

		//검색
		function doSearch() {
			var searchInfo = {};
			searchInfo["catLv1Code"] = $('#secrchForm input[name=catLv1Code]').val();
			searchInfo["remark"] = $('#secrchForm input[name=remark]').val();

			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/srm/selectCatLv1CodeList.json"/>',
				data : JSON.stringify(searchInfo),
				success : function(data) {
					_setTbodyMasterValue(data);
				}
			});
		}


		/* List Set */
		function _setTbodyMasterValue(data){
			$('#dataListbody').empty();
			for(var i=0; i<data.length; i++){
				data[i].no = i+1;
			}
			$("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
		}

		//선택
		function serRowValue(idx){

			var sgNo = $('#dataListbody input[name=sgNo]').eq(idx-1).val();
			var sgName = $('#dataListbody input[name=sgName]').eq(idx-1).val();
			var scKind = $('#dataListbody input[name=scKind]').eq(idx-1).val();
			opener.setCatLv1Code(sgNo, sgName, scKind);
			window.close();
		}
	</script>

<script id="dataListTemplate" type="text/x-jquery-tmpl">
	<tr onclick="javascript:serRowValue('<c:out value="\${no}"/>');" style="cursor:pointer">
		<td style="text-align: center;">
			<c:out value="\${no}"/>
		</td>
		<td><c:out value="\${sgName}"/></td>
		<td>
			<c:out value="\${remark}"/>
			<input type="hidden" id="sgNo" name="sgNo" value="<c:out value="\${sgNo}"/>"/>
			<input type="hidden" id="sgName" name="sgName" value="<c:out value="\${sgName}"/>"/>
			<input type="hidden" id="scKind" name="scKind" value="<c:out value="\${scKind}"/>"/>
		</td>
	</tr>
</script>
</head>

<body>

	<!-- popup wrap -->
	<div id="popup_wrap">
		<h1 class="popup_logo"><img src="/images/epc/edi/srm/common/logo.png" alt="Lotte Mart"></h1>

		<div class="tit_btns">
			<h2 class="tit_star"><spring:message code='text.srm.field.srmjon003003.sub.title1'/></h2>	<%-- 대분류 조회--%>
			<div class="right_btns">
				<button type="button" class="btn_normal" onclick="javascript:doSearch();"><spring:message code='button.srm.search2' /></button>	<%--조회--%>
			</div>
		</div>
		
		<form id="secrchForm" name="secrchForm" method="post" onsubmit="return false;">
		<table class="tbl_st1 form_style">
			<colgroup>
				<col style="width:15%;">
				<col style="width:35%;">
				<col style="width:15%;">
				<col>
			</colgroup>
			<tbody>
				<tr>
					<th><label for="catLv1Code"><spring:message code="text.srm.field.catLv1Code"/></label></th><%--spring:message : 대분류--%>
					<td>
						<input type="text" id="catLv1Code" name="catLv1Code" title="" class="input_txt_default"/>
					</td>
					<th><label for="remark"><spring:message code="text.srm.field.catLv1CodeDetail"/></label></th><%--spring:message : 상세내용--%>
					<td>
						<input type="text" id="remark" name="remark" title="" class="input_txt_default"/>
					</td>
				</tr>
			</tbody>
		</table><!-- END 정보 입력폼 -->
		</form>
		<br>
		<div style="width:100%; height:300px; overflow-x:hidden; overflow-y:scroll; table-layout:fixed; white-space:nowrap;">
			<table class="tbl_st1">
				<colgroup>
					<col width="10%"/>
					<col width="20%"/>
					<col width="*"/>
				</colgroup>
				
				<thead>
					<tr>
						<th><spring:message code='table.srm.colum.title.no'/></th><%--spring:message : No--%>
						<th><spring:message code='table.srm.srmjon003003.colum.title2'/></th><%--spring:message : 대분류명--%>
						<th><spring:message code='table.srm.srmjon003003.colum.title3'/></th><%--spring:message : 상세내용--%>
					</tr>
				</thead>

				<tbody id="dataListbody"/>
			</table>
		</div>

	</div><!-- END popup wrap -->

</body>
</html>

