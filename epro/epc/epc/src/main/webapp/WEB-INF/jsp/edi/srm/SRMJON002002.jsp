<%@ page pageEncoding="UTF-8"%>

<%--
	Page Name 	: SRMSPW0011.jsp
	Description : 비밀번호 변경 팝업창
	Modification Information

	수정일      			수정자           		수정내용
	-----------    	------------    ------------------
	2016.07.19  	SHIN SE JIN		 최초 생성
--%>

<!doctype html>
<html lang="ko">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<link href="https://fonts.googleapis.com/css?family=Open+Sans:400,600,700" rel="stylesheet">
	<link href="https://cdn.rawgit.com/hiun/NanumSquare/master/nanumsquare.css" rel="stylesheet" type="text/css">

	<%@include file="./SRMCommon.jsp" %>

<title><spring:message code='text.srm.field.tempPwChange1' /></title><%--비밀번호 변경 --%>

<style type="text/css">
.btn_search {
    position: absolute;
    width: 60px;
    right: 60px;
    top: 5px;
    bottom: 5px;
    background: #888;
    border-radius: 5px;
    color: #fff;
    font-size: 19px;
}

</style>

<script language="JavaScript">
	/* 화면 초기화 */
	$(document).ready(function() {
		
		
	});
	
	/* 사업자 번호 찾기  */
	function func_fnSearch() {
		$("input[name='resultSellerNameLoc']").val("");		
		var irsNo1 	= $("#MyForm input[name='irsNo1']").val();
		var irsNo2 	= $("#MyForm input[name='irsNo2']").val();
		var irsNo3 	= $("#MyForm input[name='irsNo3']").val();
		var irsNo	= irsNo1 + "" + irsNo2 + "" + irsNo3;

		if(irsNo =="") {
			alert("사업자 번호를 입력해 주세요.");	
			return false;
		}
		if(irsNo.length != 10) {
			alert("사업자 번호를 정확히 입력해주세요.");
			return false;
		}
		
		$("#MyForm input[name=irsNo]").val(irsNo);
		
		
		var dataInfo = {};

		dataInfo["houseCode"] 		= $("#MyForm input[name='houseCode']").val();				// 하우스코드	
		dataInfo["irsNo"] 			= $("#MyForm input[name=irsNo]").val();						// 사업자등록번호
		//dataInfo["sellerNameLoc"] 	= $("#MyForm input[name=sellerNameLoc]").val();				// 사업자명
		
		var data= $.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/srm/SRMJONSearch.json"/>',
			data : JSON.stringify(dataInfo), 
			success : function(data) {
				$("input[name='resultSellerNameLoc']").val("");
				if (data.msg == "EXIST") {
					alert("검색 결과가 존재합니다.");<%--검색 결과가 존재합니다. --%>					
					console.log(data.result.sellerNameLoc);
					$("input[name='resultSellerNameLoc']").val(data.result.sellerNameLoc);
					//$("input[name='resultBizNo']").val(data.irsNo);
					
					
				} else if(data.msg == "NOT_EXIST") {
					alert("검색 결과가 존재하지 않습니다.");<%--검색 결과가 존재합니다. --%>
					
				}			
				return false;
			}
		});
	}



	
	
</script>
</head>

<body>
	<!-- popup wrap -->
	<div id="popup_wrap">
		<h1 class="popup_logo"><img src="/images/epc/edi/srm/common/logo.png" alt="Lotte Mart"></h1>

		<form name="MyForm" id="MyForm" method="post">
		<input type="hidden" id="houseCode" name="houseCode" value="000" />
		<input type="hidden" id="irsNo" name="irsNo" />


		<h2 class="tit_star">사업자번호로 사업자명 찾기</h2>	<%--비밀번호 변경 --%>
		<p     style="font-color:#999999; margin: -5px 5px 5px 5px;"> 사업자 번호를 정확히 입력해주세요.</p>
		<!-- 회색박스 -->
		<div style="background:#f4f4f4; padding: 15px 10px;">
			<!-- 버튼포함 박스 -->
			<div class="input_box">
				<!-- 입력폼 -->
				<dl class="input_form">
					<!--  <dt><label for="sellerNameLoc"><spring:message code='text.srm.field.sellerNameLoc' /></label></dt>	<%--사업자명 --%>
					<dd>
						<input type="text" class="field_full" name="sellerNameLoc" id="sellerNameLoc">
					</dd> -->
									
					<dt><label for="irsNo1"><spring:message code='text.srm.field.irsNo' /></label></dt>	<%--사업자등록번호 --%>
					<dd>
						<div id="divD">
							<input type="text" class="field_num" name="irsNo1" id="irsNo1" maxlength="3" style="text-align: center;"> -
							<input type="text" class="field_num" name="irsNo2" id="irsNo2" maxlength="2" style="text-align: center;"> -
							<input type="text" class="field_num" name="irsNo3" id="irsNo3" maxlength="5" style="text-align: center;">
						</div>
					</dd>
				</dl><!-- END 입력폼 -->

				<!-- 확인 -->
				<button type="button"  name="btnSearch" class="plain btn_search" onclick="func_fnSearch();">찾기</button>	<%-- 확인 --%>
				<!-- END 확인 -->

			</div><!-- 버튼포함 박스 -->
		</div><!-- END 회색박스 -->


		<h2 class="tit_star">검색 결과</h2>	<%--비밀번호 변경 --%>		
		<!-- 회색박스 -->
		<div style="background:#f4f4f4; padding: 15px 10px;">
			<!-- 버튼포함 박스 -->
			<div class="input_box">
				<!-- 입력폼 -->
				<dl class="input_form">
					<dt><label for="sellerNameLoc"><spring:message code='text.srm.field.sellerNameLoc' /></label></dt>	<%--사업자명 --%>
					<dd>
						<input type="text" class="field_full" name="resultSellerNameLoc" id="resultSellerNameLoc" disabled>
					</dd>
					<!-- 
					<dt><label for="sellerNameLoc"><spring:message code='text.srm.field.irsNo' /></label></dt>	<%--사업자명 --%>
					<dd>
						<input type="text" class="field_full" name="resultBizNo" id="resultBizNo" disabled>
					</dd>					
					 -->
				</dl>
			
			</div>
		</div>



	</form>
	
	
	
		<p class="align-c mt10"><button type="button" id="btnOk" name="btnOk" class="btn_normal" onclick="window.close();">닫기</button></p>	<%--닫기 --%>
	</div><!-- END popup wrap -->
</body>
</html>