<%@include file="../common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>
	<script>
	var uploadErrorMsg = 
	{		
			"type"		: "jpg 형식의 이미지 파일만 업로드가 가능합니다.",
			"extension" : "jpg 형식의 이미지 파일만 업로드가 가능합니다.",
			"size" 		: "500k 미만의 이미지 파일만 업로드 하실수 있습니다.",
			"productCopy"	: "상품정보가 정상적으로 복사되었습니다."
	 };
		
	<c:if test="${not empty result}">
		opener.doSearch();
		this.close();
	</c:if>

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
		var form = document.forms[0];
		
		loadingMaskFixPos();
		form.action  = '<c:url value="/edi/order/PEDMORD00101Select.do"/>';
		form.submit();	
	}

	function storePopUp(){
		PopupWindow("/comm.do");
	}

	function productPopUp(){
		PopupWindow("/product.do");
	}

	function nextStep() {
		location.href='${ctx}/edi/product/PEDMPRO00203.do';
	}

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

		var imageSeq = $("input[name=imageSeq]").val();
		var imageName = $("input[name=imageName]").val();
		var newProductCode = $("input[name=newProductCode]").val();
		var returnPage = $("input[name=returnPage]").val();
		var queryString = "&imageSeq="+imageSeq+"&imageName="+imageName+"&newProductCode="+newProductCode;

		$("input[name=returnPage]").val(returnPage+queryString);
		
		$("form[name=offlineImageForm]").submit();
	}
</script>

</head>

<body>
<div id="popup">

		<!--	@ BODY WRAP START 	image list갯수	${fn:length(uploadedOnlineImageList)} -->
<form name="offlineImageForm" id="uploadForm" method="POST" action="${ctx}/edi/product/PEDPPRO000101Save.do" enctype="multipart/form-data">
	<input type="hidden" name="imgSeq"     			value="${offlineImage.imgSeq}">
	<input type="hidden" name="colorCd"    			value="${offlineImage.colorCd}">
	<input type="hidden" name="prodImgId"  			value="${offlineImage.prodImgId}">
	<input type="hidden" name="imgNm"      			value="${offlineImage.imgNm}">
	<input type="hidden" name="productDivnCode"     value="${offlineImage.productDivnCode}">
	
	<input type="hidden" name="entpCode"     value="${offlineImage.entpCode}">
	<input type="hidden" name="sellCode"     value="${offlineImage.sellCode}">
	
	
	
   <!------------------------------------------------------------------ -->
    <!--    title -->
    <!------------------------------------------------------------------ -->
    <div id="p_title1">
        <h1>상품이미지 등록</h1>
        <span class="logo"><img src="/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
    </div>
    <!-------------------------------------------------- end of title -- -->
    
    <!------------------------------------------------------------------ -->
    <!--    process -->
    <!------------------------------------------------------------------ -->
    <!--    process 없음 -->
    <br>
    <!------------------------------------------------ end of process -- -->
	<div class="popup_contents">

	<!------------------------------------------------------------------ -->
	<!-- 	검색조건 -->
	<!------------------------------------------------------------------ -->
	<div class="bbs_search">
		<ul class="tit">
			<li class="tit">이미지 등록</li>
			<li class="btn">
				<a href="javascript:submitOfflineImage();" class="btn"><span><spring:message code="button.common.save"/></span></a>
                <a href="javascript:window.close();" class="btn"><span><spring:message code="button.common.close"/></span></a>
			</li>
		</ul>
	</div>
			
	<!----------------------------------------------------- end of 검색조건 -->
				
	<!-- -------------------------------------------------------- -->
	<!--	검색내역 	-->
	<!-- -------------------------------------------------------- -->
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
	<!-----------------------------------------------end of 검색내역  -->
    </div><!-- class popup_contents// -->
    
    <br/>
		
		
</div><!-- id popup// -->		
</form>
</body>
</html>
