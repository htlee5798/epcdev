<%@ page  pageEncoding="UTF-8"%>

<%--
	Page Name 	: SRMRST002009.jsp
	Description : 입점상담 결과확인 > 진행현황 > 품질경영평가 업체 선택 팝업
	Modification Information

	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2018.12.14		LEE SANG GU	최초생성
--%>

<!doctype html>
<html lang="ko">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<link href="https://fonts.googleapis.com/css?family=Open+Sans:400,600,700" rel="stylesheet">
	<link href="https://cdn.rawgit.com/hiun/NanumSquare/master/nanumsquare.css" rel="stylesheet" type="text/css">
	
	<%@include file="./SRMCommon.jsp" %>

	<title><spring:message code='text.srm.field.srmrst002009.title1'/></title><%--spring:message : 평가기관 정보--%>

	<script language="JavaScript">
		/* 화면 초기화 */
		$(document).ready(function() {
			
			var selectedComp =  '<c:out value="${selectedComp }" />'
			
			if(selectedComp != ''){
				$("input:radio[name='evalSellerCode']:input[value=" + selectedComp + "]").attr("checked",true);
			}			
		});

		//닫기
		function func_ok() {
			window.close();
		}
		
		
		function doSave(){
			
			if($("input[name='evalSellerCode']:checked").length < 1){
				alert("품질경영평가 업체를 선택한 후 저장하세요");
				return false;
			}
			
			var postData = {};			
			$("input[name='evalSellerCode']:checked").parent().find('input').each(function(i,e){
				postData[$(this).attr('name')] = $(this).val();
							
			});
			
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/srm/UpdateCompSiteVisitComp.json"/>',
				data : JSON.stringify(postData),
				success : function(data) {
					if(data.message === 'SUCCESS'){
						alert('평가경영평가 업체 저장하였습니다.');
						self.close();
						opener.location.reload();						
					}
				},
				error:function(request,status,error){
		        	alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		       }
	
			});			
		}
		
		//2019-01-31  전화번호 포맷 변경
		function phoneFormatter(num) {				
			   var formatNum = '';
			   var type=1; //가운데 번호 가림표 없이 설정
			   try{
			      if (num.length == 11) {
			         if (type == 0) {
			            formatNum = num.replace(/(\d{3})(\d{4})(\d{4})/, '$1-****-$3');
			         } else {
			            formatNum = num.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
			            }
			      } else if (num.length == 8) {
			         formatNum = num.replace(/(\d{4})(\d{4})/, '$1-$2');
			      } else {
			         if (num.indexOf('02') == 0) {
			            if (type == 0) {
			               formatNum = num.replace(/(\d{2})(\d{4})(\d{4})/, '$1-****-$3');
			            } else {
			               formatNum = num.replace(/(\d{2})(\d{4})(\d{4})/, '$1-$2-$3');
			            }
			         } else {
			            if (type == 0) {
			               formatNum = num.replace(/(\d{3})(\d{3})(\d{4})/, '$1-***-$3');
			            } else {
			               formatNum = num.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3');
			            }
			         }
			      }
			   } catch(e) {
			      formatNum = num;
			      console.log(e);
			   }
			   return formatNum;
			}


		
	</script>

</head>


<body>
<form name="MyForm"  id="MyForm" method="post">
	<!-- popup wrap -->
	<div id="popup_wrap">
		<h1 class="popup_logo"><img src="/images/epc/edi/srm/common/logo.png" alt="Lotte Mart"></h1>

		<div class="tit_btns">
			<h2 class="tit_star">품질경영평가 업체 선택</h2>	<%-- 품질경영평가 기관 선택--%>			
			<div class="right_btns">
				<button type="button" class="btn_normal" onclick="javascript:doSave();">저장</button>	<%--저장--%>
			</div>
		</div>
		
		<p style="font-size:12px; margin:-15px 0 5px 0;">심사 관련 문의사항이 있는 경우, 업체별 담당자에게 연락 바랍니다. 고맙습니다.</p>
		
		<div style="width:100%; height:200px; overflow-x:hidden; overflow-y:scroll; table-layout:fixed; white-space:nowrap;">
			<table class="tbl_st1">
				<colgroup>
					<col width="10%"/>
					<col width="38%"/>
					<col width="20%"/>
					<col width="32%"/>				
				</colgroup>
				
				<thead>
					<tr>
						<th>선택</th>
						<th>품질경영평가 업체</th><%--spring:message : No--%>
						<th>사원명</th><%--spring:message : No--%>
						<th>연락처</th><%--spring:message : 대분류명--%>					
					</tr>
				</thead>
				<tbody id="dataListbody" style="overflow-y: scroll"/>
					<c:forEach var="list" items="${compList}">
						<tr>
							<td style="text-align: center;">
								<input type="radio" name="evalSellerCode" value="${list.evalSellerCode }"/>
								<input type="hidden" name="reqSeq"  id="reqSeq" value="${reqSeq }"/>
								<input type="hidden" name="sellerCode"  id="sellerCode" value="${sellerCode }"/>
							</td>
							<td style="text-align: center;">${list.sellerNameLoc }</td>
							<td style="text-align: center;">${list.evCtrlName }</td>	
							<td style="text-align: center;" id="phone_number">
								<script type="text/javascript">
								   var phone=phoneFormatter('${list.evCtrlPhone }','0');  
								   document.write(phone);
								</script>
							</td>		
						</tr>			
					</c:forEach>
			</table>
		</div>

		<p class="align-c mt10"><button type="button" class="btn_normal" onclick="func_ok();"><spring:message code='button.srm.close'/></button></p>	<%--닫기 --%>
	</div><!-- END popup wrap -->
</form>
</body>
</html>