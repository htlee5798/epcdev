<%--
	Page Name 	: NEDMPRO0010ImgPop.jsp
	Description : 신상품현황조회 이미지 등록 팝업 화면
	Modification Information
	
	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2015.12.23		SONG MIN KYO 	최초생성
--%>
<%@include file="../common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>
	
	<script>	
		/* dom이 생성되면 ready method 실행 */
		$(document).ready(function() {
			//----- 이미지 저장후 넘어오는 result 처리
			var result	=	"<c:out value='${result}'/>";
			//alert(result);
			if (result != "" && result != null) {
				if (result == "SUCCESS") {
					alert("<spring:message code='msg.common.success.save'/>");
					opener._eventRefresh();
					self.close();
				}else if(result == "EXT_ERR"){
					alert("업로드 불가능한 확장자입니다.");
				}else {
					alert("<spring:message code='msg.common.fail.insert'/>");	
				}
			}
		});
		
		function submitOfflineImage() {
			var valid1 = document.getElementById("file_valid1");
			var valid2 = document.getElementById("file_valid2");
			var valid3 = document.getElementById("file_valid3");
			
			if(valid1.value==''){
				alert("정면 이미지를 선택하세요.");
				return;
			}
			if(valid2.value==''){
				alert("측면 이미지를 선택하세요.");
				return;
			}
			if(valid3.value==''){
				alert("윗면 이미지를 선택하세요.");
				return;
			}
			
			var uploadFieldCount = $("input:file").length;
			var flagVar = 0;
			$("input:file").each(function() {
				if( $(this).val() == '') {
					flagVar ++;
				}
			});
			
			if( uploadFieldCount == flagVar) {
				alert("업로드할 이미지를 선택해주세요.");
				return;
			}
			
			if (!confirm("<spring:message code='msg.common.confirm.save'/>")) {
				return;
			}
			
			
			$("form[name=offlineImageForm]").submit();
		}
	</script>

</head>

<body>
	<div id="popup">
	
		<form name="offlineImageForm" id="offlineImageForm" method="POST" action="<c:url value='/edi/product/saveSaleImgAllApply.do'/>"	 enctype="multipart/form-data">			
			<input type="hidden" name="pgmId"  			id="pgmId"				value="<c:out value='${param.pgmId}'/>"			/>
			<input type="hidden" name="prodImgId"  		id="prodImgId"			value="<c:out value='${param.prodImgId}'/>"		/>
			<input type="hidden" name="chkVariant"  	id="chkVariant"			value="<c:out value='${param.chkVariant}'/>"	/>
			<input type="hidden" name="entpCd"  		id="entpCd"				value="<c:out value='${param.entpCd}'/>"		/>
			<input type="hidden" name="entpCd"  		id="entpCd"				value="<c:out value='${param.entpCd}'/>"		/>
			<input type="hidden" name="cfmFg"  			id="cfmFg"				value="1"		/>										<!-- 오프라인 이미지 등록시 이미지 확정상태값은 1:심사중으로 준다 -->
			
		    <div id="p_title1">
		        <h1>상품이미지등록</h1>
		        <span class="logo"><img src="/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
		    </div>
	    
	    	<br>
	   
			<div class="popup_contents">
	
		
			<div class="bbs_search">
				<ul class="tit">
					<li class="tit">이미지등록</li>
					<li class="btn">
						<a href="javascript:submitOfflineImage();" class="btn"><span><spring:message code="button.common.save"/></span></a>
		                <a href="javascript:window.close();" class="btn"><span><spring:message code="button.common.close"/></span></a>
					</li>
				</ul>
				
			</div>
	
			<div class="wrap_con">
				<div class="bbs_list">
					<ul class="tit">
						<li class="tit">이미지 등록</li>
					</ul>
					<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
							<td>
								<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable1">
									<colgroup>
										<col />
										<col />
									</colgroup>
										
									<tr>
										<th style="width:20%">정면</th>
										<td align="center">
											<input type="file" id="file_valid1" style="width:250px;" name="_front" />
										</td>
									</tr>
									<tr>
										<th style="width:20%">측면</th>
										<td align="center">
											<input type="file" id="file_valid2" style="width:250px;" name="_side" />
										</td>
									</tr>
									<tr>
										<th style="width:20%">윗면</th>	
										<td align="center" >
											<input type="file" id="file_valid3" style="width:250px;" name="_top" />
										</td>
									</tr>		
								</table>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</form>	
    </div>
    
    <br/>

	<!-- -------------------------------------------------------- -->
	<!--    footer  -->
	<!-- -------------------------------------------------------- -->
	<div id="footer">
	    <div id="footbox">
	        <div class="msg" id="resultMsg"></div>
	    </div>
	</div>
	<!---------------------------------------------end of footer -->

</div>

</body>
</html>
