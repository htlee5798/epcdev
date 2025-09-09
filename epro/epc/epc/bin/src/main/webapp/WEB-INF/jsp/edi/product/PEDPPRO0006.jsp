<%@include file="../common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>

<script>
$(function() {
	
	$("#footer").css("margin-top", "40px");
	
	
});
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
		
		
		if(!form.searchSrcmkCd.value){
			alert("<spring:message code='msg.common.fail.srcmkcd'/>");
			form.searchSrcmkCd.focus();
			return false;
		}
		
		loadingMaskFixPos();
		form.action  = "<c:url value="/edi/product/PEDMPRO0006Search.do"/>";
		form.submit();		
				
	}
	
	
	
	function doSubmit() {
		
		var form = document.forms[0];
		
		
		
		if(!form.venCd.value.trim())
		{
			alert("협력업체코드 및 판매(88)/내부 코드를 입력하시고\n상품정보를 먼저 조회하세요!");	
			return;
		}
		
		if(!form.pgmId) return;
		
		var venCdState = false;
		if(form.venCd.value =="899999" || form.venCd.value =="899915" || form.venCd.value =="000242" )  venCdState= true;
		
		
		var objlen = form.pgmId.length;

		if(document.getElementById("BarcodeLayer").style.display == "none")
		{
			objlen = objlen-1;
		}
		
		for(var i=0; objlen > i; i++)
		{
			if(i == 0 || document.getElementById("BarcodeLayer").style.display == "none") continue;  <%-- 첫번째 row 는 배열로 설정하기위한  빈 필드 서브밋 할때 삭제 함. --%>
			
		
			if(form.logiBcd[i].value.trim().length != 14) {
				alert("물류바코드를 입력하세요!");
				form.logiBcd[i].focus();
				return;	
			}
			if(!form.width[i].value.trim() || Number(form.width[i].value.trim()) <= 0){
				alert("박스체적 [가로]를 입력하세요!");
				form.width[i].focus();
				return;
			}
			 
			if(!form.length[i].value.trim() || Number(form.length[i].value.trim()) <=0 ){
				alert("박스체적 [세로]를 입력하세요!");
				form.length[i].focus();
				return;
			}
			if(!form.height[i].value.trim() || Number(form.height[i].value.trim()) <=0){
				alert("박스체적 [높이]를 입력하세요!");
				form.height[i].focus();
				return;
			}
			if(!form.wg[i].value.trim() || Number(form.wg[i].value.trim()) <=0 ){
				alert("박스체적 [총중량]을 입력하세요!");
				form.wg[i].focus();
				return;
			}
			
			//파레트가하나라도 입력되었으면 가로,세로,높이 모두입력
			if( form.innerIpsu[i].value.trim() ||  form.pltLayerQty[i].value.trim() || form.pltHeightQty[i].value.trim())
			{
			
				if(!form.innerIpsu[i].value.trim() || Number(form.innerIpsu[i].value.trim()) <=0){
					alert("팔레트값을 입력하실경우 [가로박스수]를 입력하세요!");
					form.innerIpsu[i].focus();
					return;
				}
				
				if(!form.pltLayerQty[i].value.trim() || Number(form.pltLayerQty[i].value.trim()) <= 0 ){
					alert("팔레트값을 입력하실경우 [세로박스수]를 입력하세요!");
					form.pltLayerQty[i].focus();
					return;
				}
				
				if(!form.pltHeightQty[i].value.trim() || Number(form.pltHeightQty[i].value.trim()) <= 0){
					alert("팔레트값을 입력하실경우 [높이박스수]를 입력하세요!");
					form.pltHeightQty[i].focus();
					return;
				}
			}
			
			
			form.crsdkFg[i].value = "0";
			if(venCdState) // 업체 하드코딩
			{ 	
				var vol_sum = form.width[i].value * form.height[i].value * form.length[i].value;
				if(vol_sum >=7486500) form.crsdkFg[i].value = "1";
			}
			
			<%--  소터에러사유 설정에따른 소트구분 정의--%>
			/*
			if(form.conveyFg[i].value == "0")	//소터에러사유없음 구분
			{
				if( Number(form.width[i].value) >= 225 &&  Number(form.width[i].value) <= 850)
				{	if (Number(form.length[i].value) >= 100 && Number(form.length[i].value) <= 600 && Number(form.height[i].value) >=50 && Number(form.height[i].value) <=500)
					{	if (Number(form.wg[i].value) >= 0.5 && Number(form.wg[i].value) <= 30 )
							form.sorterFg[i].value = "1";
						else form.sorterFg[i].value = "0";
					}
					else form.sorterFg[i].value = "0";
				}
				else  form.sorterFg[i].value = "0";
			}
			else form.sorterFg[i].value = "0";
			*/
			
		}	
		
		<%-- 사용하지 않는 바코드 등록 Row 제거 -------------------------------- --%>
		if(document.getElementById("BarcodeLayer").style.display == "none") {
			$("#BarcodeLayer").remove();
		}
		$("#BlankLayer").remove();  // Blank input 제거
		<%-- End Row 제거--%>
		
		loadingMaskFixPos();
		form.action="<c:url value="/edi/product/PEDMPRO0006Save.do"/>";
		form.submit();
	}
		

	
	function initPage() {
		var state = false;
		<c:if test="${empty barcodeList }">state = true;</c:if>	
		
		 barcodeSet(state); 
	}
	
	function barcodeSet(state){
		
		if(state){
			document.getElementById("BarcodeLayer").style.display = "";
			setBarcodeBtn(false);
		}
		else{
			document.getElementById("BarcodeLayer").style.display = "none";
			setBarcodeBtn(true);
		}
		
	}
	
	function setBarcodeBtn(state){
		
		if(state){
			document.getElementById("BarcodeLayerBtnAdd").style.display = "";
			document.getElementById("BarcodeLayerBtnDel").style.display = "none";
		}
		else
		{
			
			<c:if test="${not empty barcodeList }">	
				document.getElementById("BarcodeLayerBtnAdd").style.display = "none";
				document.getElementById("BarcodeLayerBtnDel").style.display = "";	
			</c:if>
		}
	}
	
	
	function MIX_NO_READY(name)
	{
		alert("<spring:message code='msg.product.mixnoready.info'/>");
		document.getElementsByName(name)[0].checked = true;
		//document.getElementById(obj.name)[0].checked = true;
	}
	
	function digitcheck(obj){
		if(!isNumberDu(obj)) {
			obj.value="";
			alert("<spring:message code='msg.number'/>");
			obj.focus();
			return false;
		}
		return true;
	}
	
	
	function setRadioValue(oObject) {
		//var oTd = obj.parentElement;
		
		//oTd.children(0).value = obj.value;
		//var objUnitAmt       = oTr.children(2).children(0); 
		
		var oTb  = oObject.parentElement.parentElement.parentElement;
		var oTr1 = oTb.children(2);
		var wgObj 		=	oTr1.children(1).children(5);	//총중량    this(oObject)
		
		digit_sorter_check(wgObj);
	}
	
	
	function digit_sorter_check(oObject) 
	{ 
		
		var form = document.forms[0];
		
		
		
		var oTb  = oObject.parentElement.parentElement.parentElement;
		var oTr1 = oTb.children(2);
		var oTr2 = oTb.children(3);
		
		
		var widthObj    = 	oTr1.children(1).children(2);	//가로  
		var lengthObj 	=   oTr1.children(1).children(3);	//세로 
		var heightObj 	=	oTr1.children(1).children(4);	//높이
		var wgObj 		=	oTr1.children(1).children(5);	//총중량    this(oObject)
		
		var conveyFgObj =   oTr2.children(1).children(1);	//소터에러사유(없음)
		var sorterFgObj =   oTr2.children(3).children(0);   //소터구분
		var sorterFgObjNm = oTr2.children(3).children(1);   //소터구분명
		
		var oldSorter = sorterFgObj.value;
		
		
		sorterFgObj.value = "";
		sorterFgObjNm.innerHTML="";
		
		if(!digitcheck(oObject)) return; 
		
		
		if(oObject.name == wgObj.name)  //총중량    this(oObject) 일때만
	    {
			if (!widthObj.value.trim())
			{
				alert("박스체적 가로를 먼저입력해 주세요.");
				wgObj.value="";
				widthObj.focus(); 
				return;			
			}
			if (!lengthObj.value.trim())
			{
				alert("박스체적 세로를 입력해 주세요.");
				form.wg.value="";
				lengthObj.focus(); 
				return;			
			}
			if (!heightObj.value.trim())
			{
				alert("박스체적 높이를 먼저입력해 주세요.");
				wgObj.value="";
				heightObj.focus(); 
				return;			
			}
			
	    }
		
		
		if(!widthObj.value.trim() || !lengthObj.value.trim() || !heightObj.value.trim() || !wgObj.value.trim() )
			return;
		
		var WIDTH , LENGTH , HEIGHT , WG, CONVEY_FG;
		WIDTH	= widthObj.value;
		LENGTH	= lengthObj.value;
		HEIGHT	= heightObj.value;
		WG		= wgObj.value;
		
		
		if(conveyFgObj.checked == true)
		{
			if( WIDTH >= 225 && WIDTH <= 850)
			{
				if (LENGTH >= 100 && LENGTH <= 600 && HEIGHT >=50 && HEIGHT <=500)
				{
					
					if (WG >= 0.5 && WG <= 30 )
					{
						
						sorterFgObj.value = "1";
						if(oldSorter != sorterFgObj.value || oObject.name == wgObj.name) alert("소터구분은 sorter입니다.");
						
						
						sorterFgObjNm.innerHTML="<font color='2f6084'><b>sorter</b></font>";

					}
					else
					{
						
						sorterFgObj.value = "0";
						if(oldSorter != sorterFgObj.value || oObject.name == wgObj.name) alert("총중량으로 인하여 소터구분은 non-sorter입니다.");	
						sorterFgObjNm.innerHTML="<font color='2f6084'><b>non-sorter</b></font>";
					}
							
				}
				else 
				{
					sorterFgObj.value = "0";
					if(oldSorter != sorterFgObj.value || oObject.name == wgObj.name) alert("세로 및 높이 길이로 인하여 소터구분은 non-sorter입니다.");
					sorterFgObjNm.innerHTML="<font color='2f6084'><b>non-sorter</b></font>";
				}
			
			}
			else 
			{
				
				sorterFgObj.value = "0";
				if(oldSorter != sorterFgObj.value || oObject.name == wgObj.name) alert("가로 길이로 인하여 소터구분은 non-sorter입니다.");
				sorterFgObjNm.innerHTML="<font color='2f6084'><b>non-sorter</b></font>";
			}
		}
		else
		{
			
			sorterFgObj.value = "0";
			if(oldSorter != sorterFgObj.value || oObject.name == wgObj.name) alert("소터에러사유로 인하여 소터구분은 non-sorter입니다.");
			sorterFgObjNm.innerHTML="<font color='2f6084'><b>non-sorter</b></font>";
		}
			
		
	}
	
	
	
	function digit_pallet(oObject) //팔레트
	{
		var total_box ;	
	
		 
		
		var oTb  = oObject.parentElement.parentElement.parentElement;
		var oTr1 = oTb.children(4);
		
		var innerIpsuObj      = oTr1.children(1).children(0);	//가로박스수
		var pltLayerQtyObj    = oTr1.children(1).children(1);	//세로박스수
		var pltHeightQtyObj   = oTr1.children(1).children(2);	//높이박스수
		
		var totalBoxCountNmObj    = oTr1.children(3).children(0);
		var totalBoxCountObj      = oTr1.children(3).children(1);
		
		totalBoxCountObj.value       = "";	
		totalBoxCountNmObj.innerHTML = "";
		
		if(!digitcheck(oObject)) return;	
		
		if(oObject.name == pltHeightQtyObj.name) //입력필드가높이일때만 보이기
		{
			if(!innerIpsuObj.value.trim())
			{
				alert("가로박스수를 먼저입력해 주세요.");
				innerIpsuObj.focus(); 
				return;
			}
			
			if(!pltLayerQtyObj.value.trim())
			{
				alert("세로박스수를 먼저입력해 주세요.");
				pltLayerQtyObj.focus(); 
				return;
			}
			
			
			
			
		}
		
		if( !innerIpsuObj.value.trim() ||  !pltLayerQtyObj.value.trim() || !pltHeightQtyObj.value.trim())
			return;
			
		
		
		total_box =  innerIpsuObj.value * pltLayerQtyObj.value * pltHeightQtyObj.value ;
		//alert("총박수스 : " + total_box);
		
		totalBoxCountObj.value       = total_box;	
		totalBoxCountNmObj.innerHTML = "<font color='2f6084'><b>"+total_box+"</b></font>";
		
		
		
	}
	
</script>




<style type="text/css">
input { width:30px; }
</style>
</head>

<body onLoad="initPage();">
	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>  >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form name="searchForm" method="post">
		<input type="hidden" id="entpCode" name="entpCode" value="${param.widthSize }" /> 
		
		<%-- 공통 마스터 Parameter 값 --%>
		<input type="hidden" id="venCd"  name="venCd" 	 value="${productData.VEN_CD }" /> 
		<input type="hidden" id="l1Cd"   name="l1Cd" 	 value="${productData.L1_CD }" />
		<input type="hidden" id="prodCd" name="prodCd" 	 value="${productData.PROD_CD }" />
		<input type="hidden" id="SrcmkCd" name="SrcmkCd" value="${productData.SRCMK_CD }" />
		<div id="wrap_menu">
		
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit">물류바코드 등록</li>
						<li class="btn">
							<a href="#" class="btn" onclick="doSearch();"><span>검색</span></a>
						</li>
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
					
					<colgroup>
						<col style="width:15%" />
						<col style="width:30%" />
						<col style="width:15%" />
						<col style="*" />
					</colgroup>
					<tr>
						
						<th>* 협력업체</th>
						<td>
							<html:codeTag objId="searchVenCd" objName="searchVenCd" width="150px;" selectParam="${searchParam.searchVenCd}" dataType="CP" comType="SELECT" formName="searchForm" />
						</td>
						<th>* 판매(88)/내부 </th>
						<td>
							<input type="text" id="searchSrcmkCd" name="searchSrcmkCd" value="${searchParam.searchSrcmkCd}" style="width:150px"/></a>
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
						<li class="tit">검색내역</li>
					</ul>
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col style="width:100px;"  />
						<col style="width:100px;"  />
						<col style="width:100px;"  />
						<col  />
						<col style="width:100px;"  />
					</colgroup>
					<tr>
						<th>등록일자</th>
						<th>판매(88)코드</th>
						<th>상품코드</th>
						<th>상품명</th>
						<th>입수</th>
					</tr>
					<c:choose>
						<c:when test="${!empty productData }">
							<tr class="r1">
								<td align="center">${productData.REG_DT }</td>
								<td align="center">${productData.SRCMK_CD }</td>
								<td align="center">${productData.PROD_CD }</td>
								<td>&nbsp;${productData.SHORT_NM }</td>
								<td align="right"><fmt:formatNumber value="${productData.ORD_IPSU }" type="number" currencySymbol="" />&nbsp;</td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr class="r1" name="info_row">
								<td align="center" colspan="5"><font color="red">판매(88)/내부 코드를 입력하시고 검색하시기 바랍니다.</font></td>
							</tr>
						</c:otherwise>
					</c:choose>
					</table>
					
					<!-- <div style="width:100%; height:333px;overflow-x:hidden; overflow-y:scroll; table-layout:fixed;white-space:nowrap;">  -->
					
				</div>
			</div>
			
			
			<!-- 물류 바코드 자료 필드 시작 -->
			<div class="wrap_con">
				<!-- list -->
				<div class="bbs_list">
				
				<div id="BlankLayer" style="display:none;">
					<input type="hidden" name="chgFg">
					<input type="hidden" name="pgmId">
					<input type="hidden" name="seq">
					<input type="hidden" name="crsdkFg">
					<input type="hidden" name="logiBcd">
					<input type="hidden" name="logiBoxIpsu">
					<input type="hidden" name="regDt" >
					<input type="hidden" name="useFg">
					<input type="hidden" name="width">
					<input type="hidden" name="length">
					<input type="hidden" name="height">
					<input type="hidden" name="wg">
					<input type="hidden" name="mixProdFg">
					<input type="hidden" name="conveyFg">
					<input type="hidden" name="sorterFg">
					<input type="hidden" name="innerIpsu">
					<input type="hidden" name="pltLayerQty">
					<input type="hidden" name="pltHeightQty">
					<input type="hidden" name="totalBoxCount">
				</div>
					
				<ul class="tit" >
						<li class="btn" style="float:right">	
							<div><a href="javascript:doSubmit();" class="btn" ><span>저장</span></a></div>
						</li>	
						<li class="btn" style="float:right">
							<div id="BarcodeLayerBtnAdd" style="display:none;"><a href="javascript:barcodeSet(true);" class="btn" ><span>추가</span></a>&nbsp;</div>
							<div id="BarcodeLayerBtnDel" style="display:none;"><a href="javascript:barcodeSet(false);" class="btn" ><span>취소</span></a>&nbsp;</div>
						</li>
				</ul>
			
				<table cellpadding="0" cellspacing="0" border=0 width=100%>
				    <tr>
							<td>
								<div id="BarcodeLayer" style="display:none;">
								<input type="hidden" name="pgmId"   value="${productData.PGM_ID}">
								<input type="hidden" name="seq"     value="${productData.MAX_SEQ}">
								<input type="hidden" name="crsdkFg" value="0">
								<input type="hidden" name="chgFg"   value="I">
								<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0">	
									<tr>
										<th colspan="6"><b>&nbsp;물&nbsp;&nbsp; 류&nbsp;&nbsp; 바&nbsp;&nbsp; 코&nbsp;&nbsp; 드&nbsp;&nbsp;  정&nbsp;&nbsp; 보&nbsp;&nbsp;</b></th>
										<th width=100>바코드 상태</th>
										<td width=100 align=center>
											&nbsp;
											<b><font color="blue">등록요망</font></b>
										</td>
									</tr>
									<tr>
										<th align="center"  width=100>물류바코드</th>
										<td align="left" width=100><input type="text" name="logiBcd" value="" style="width: 100px;" maxlength="14" onblur="digitcheck(this)" /></td>
										<th align="center" width=100>물류박스 입수</td>
										<td align="center" width=100><input type="text" class="inputReadOnly" name="logiBoxIpsu" value="1" style="width: 80px;" readOnly  /></td>
										<th align="center"  width=80>등록날짜</th>
										<td align="center"><input type="text" class="inputReadOnly" name="regDt" value="${productData.REG_DT_NOW}"  style="width: 60px;" readOnly  /></td>
										<th align="center">사용여부</th>
										<td align="center">
											&nbsp; 
											사용
											<input type="hidden" name="useFg" value="1">
										</td>
									</tr>
									<tr >
										<th align="center">박스체적 </th>
										<td align="left" style="padding-left:20px" colspan="5">
											
											<font color="red">
												※ 정확한 숫자를 입력하셔야 납품이 가능합니다.
												예)30cm=300mm,1,000g=1kg 
											</font>
											<br>
											가로 : <input type="text" name="width"  maxlength="4" style="width:40px;" onblur="digit_sorter_check(this)"  value="" /> mm&nbsp; &nbsp;
											세로 : <input type="text" name="length" maxlength="4" style="width:40px;" onblur="digit_sorter_check(this)"  value="" /> mm&nbsp; &nbsp;
											높이 : <input type="text" name="height" maxlength="4" style="width:40px;" onblur="digit_sorter_check(this)"  value="" /> mm&nbsp; &nbsp;
											총중량 : <input type="text" name="wg"   maxlength="4" style="width:40px;" onblur="digit_sorter_check(this)"  value="" /> kg&nbsp; &nbsp;
										</td>
										<th>혼재여부</th>
										<td align="center">
											<input type="hidden" name="mixProdFg" value="0">
											<input type="radio"  name="mixProdFg_new" value="0" checked >비혼재<br> 
											<input type="radio"  name="mixProdFg_new" value="1"  onclick="MIX_NO_READY('mixProdFg_new');" >혼재
										</td>
									</tr>
									<tr>
										<th align="center">소터에러사유</th>
										<td style="padding-left:15px" colspan="5">
											<input type="hidden" name="conveyFg" value="0">
											<input type="radio" name="conveyFg_new" onClick="setRadioValue(this)" value="0" checked />없음 &nbsp;
											<input type="radio" name="conveyFg_new" onClick="setRadioValue(this)" value="1" />파손가능상품 &nbsp;
											<input type="radio" name="conveyFg_new" onClick="setRadioValue(this)" value="2" />비닐포장제품 &nbsp;
										</td>
										
										<th align="center">소터구분</th>
										<td align="center">
											<input type="hidden" name="sorterFg" value="0">
											<div id=sorterFgNm></div>
										</td>
									</tr>
									<tr>
										<th align="center">팔레트</th>
										<td style="padding-left:20px" colspan="5">
											가로박스수 <input type="text" name="innerIpsu" 	value=""  maxlength="4" onblur="digit_pallet(this)"	style="width:50px;" />&nbsp;&nbsp;&nbsp;
											세로박스수 <input type="text" name="pltLayerQty" 	value=""  maxlength="4" onblur="digit_pallet(this)"	style="width:50px;" />&nbsp;&nbsp;&nbsp;
										        높이박스수 <input type="text" name="pltHeightQty" 	value=""  maxlength="4" onblur="digit_pallet(this)"	style="width:50px;" />
										</td>
										<th align="center">총박스수</th>
										<td align="center">
											<div id=totalBoxCountNm>${list.TOTAL_BOX}</div>
											<input type ="hidden" name="totalBoxCount" value="${list.TOTAL_BOX}"/>
										</td>
									</tr>
									
								</table>
								</div>	
							</td>
						</tr>
						
				    <c:set var="row_cnt"  value="1" />
					<c:if test="${not empty barcodeList }">
					<c:forEach items="${barcodeList}" var="list" varStatus="index" >
						<tr>
						<td>
						<c:choose>
						<c:when test="${list.LOGI_CFM_FG eq '00' or  list.LOGI_CFM_FG eq '02' eq list.LOGI_CFM_FG eq '09'}">
							<%-- 조회 모드
								LOGI_CFM_FG_IDX =='00' or LOGI_CFM_FG_IDX =='02'  : 심사중
  								LOGI_CFM_FG_IDX =='09'   						  : 완료
							--%>
							<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0">
								<tr>
									<th colspan="6"><b>&nbsp;물&nbsp;&nbsp; 류&nbsp;&nbsp; 바&nbsp;&nbsp; 코&nbsp;&nbsp; 드&nbsp;&nbsp;  정&nbsp;&nbsp; 보&nbsp;&nbsp;NO.&nbsp;<c:out value="${index.count}"/></b></th>
									<th width=100>바코드 상태</th>
									<td align=center width=100>
										&nbsp;
										<c:choose>
											<c:when test="${list.LOGI_CFM_FG eq '00' or list.LOGI_CFM_FG eq '02'}"><b><font color="blue">심사중</font></b></c:when>
											<c:otherwise><b><font color="2f6084">완료</font></b></c:otherwise>
										</c:choose>
									</td>
								</tr>
								<tr>
									<th align="center"  width=100>물류바코드</th>
									<td align="left" width=100>${list.LOGI_BCD }</td>
									<th align="center" width=100>물류박스 입수</td>
									<td align="center" width=100>${list.LOGI_BOX_IPSU}</td>
									<th align="center"  width=80>등록날짜</th>
									<td align="center">${list.REG_DT}</td>
									<th align="center">사용여부</th>
									<td align="center">
										&nbsp;
										<c:choose>
											<c:when test="${list.USE_FG eq '1'}">사용</c:when>
											<c:otherwise><font color="2f6084">사용안함</font></c:otherwise>
										</c:choose>
									</td>
								</tr>
								<tr >
									<th align="center">박스체적 </th>
									<td align="left" style="padding-left:20px" colspan="5">
										
										가로 : ${list.WIDTH}&nbsp;mm&nbsp; &nbsp; &nbsp;
										세로 : ${list.LENGTH}&nbsp;mm&nbsp; &nbsp; &nbsp;
										높이 : ${list.HEIGHT}&nbsp;mm&nbsp; &nbsp; &nbsp;
										총중량 :${list.WG}&nbsp;kg&nbsp; &nbsp; &nbsp;
									</td>
									<th>혼재여부</th>
									<td align="center">
										&nbsp;
										<c:choose>
											<c:when test="${list.MAX_PROD_FG eq '0'}">비혼재</c:when>
											<c:otherwise>혼재</c:otherwise>
										</c:choose>
									</td>
								</tr>
								<tr>
									<th align="center">소터에러사유</th>
									<td style="padding-left:15px" colspan="5">
										&nbsp;
										<c:choose>
											<c:when test="${list.CONVEY_FG eq '0'}">비혼재</c:when>
											<c:when test="${list.CONVEY_FG eq '1'}">유리는 소터할 수 없습니다.</c:when>
											<c:otherwise>파손가능상품, 비닐포장제품은 소터를 사용할 수 없습니다.</c:otherwise>
										</c:choose>
									</td>
									
									<th align="center">소터구분</th>
									<td align="center">
									&nbsp;
										<c:choose>
											<c:when test="${list.SORTER_FG eq '1'}">SORTER</c:when>
											<c:otherwise>non-sorter</c:otherwise>
										</c:choose>
									</td>
								</tr>
								<tr>
									<th align="center">팔레트</tj>
									<td style="padding-left:20px" colspan="5">
										가로박스수&nbsp;:&nbsp;${list.INNER_IPSU}&nbsp;&nbsp;&nbsp;
										세로박스수&nbsp;:&nbsp;${list.PLT_LAYER_QTY}&nbsp;&nbsp;&nbsp;
									        높이박스수&nbsp;:&nbsp;${list.PLT_HEIGHT_QTY}
									</td>
									<th align="center">총박스수</th>
									<td align="center">
									&nbsp;
									${list.TOTAL_BOX}
									</td>
								</tr>
							</table>
							</c:when>
							<c:otherwise>
								<%-- 수정가능 모드 --%>	
								<input type="hidden" name="pgmId"   value="${list.PGM_ID}">
								<input type="hidden" name="seq"     value="${list.SEQ}">
								<input type="hidden" name="crsdkFg" value="${list.CRSDK_FG}">		
								<input type="hidden" name="chgFg"   value="U">					
								<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0">
								<tr>
									<th colspan="6"><b>&nbsp;물&nbsp;&nbsp; 류&nbsp;&nbsp; 바&nbsp;&nbsp; 코&nbsp;&nbsp; 드&nbsp;&nbsp;  정&nbsp;&nbsp; 보&nbsp;&nbsp;NO.&nbsp;<c:out value="${index.count}"/></b></th>
									<th width=100>바코드 상태</th>
									<td align=center width=100>
										&nbsp;
										<b><font color="blue">수정요청</font></b>
									</td>
								</tr>
								<tr>
									<th align="center"  width=100>*물류바코드</th>
									<td align="left" width=100><input type="text" name="logiBcd" value="${list.LOGI_BCD }" style="width: 100px;" onblur="digitcheck(this)" maxlength="14" /></td>
									<th align="center" width=100>물류박스 입수</td>
									<td align="center"  width=100><input type="text" class="inputReadOnly" readOnly name="logiBoxIpsu"  value="${list.LOGI_BOX_IPSU}" style="width: 80px;"   /></td>
									<th align="center"  width=80>등록날짜</th>
									<td align="center"><input type="text" class="inputReadOnly" readOnly name="regDt" value="${list.REG_DT}"  style="width: 60px;"  /></td>
									<th align="center">사용여부</th>
									<td align="center">
										<c:choose>
											<c:when test="${list.USE_FG eq '1'}">사용</c:when>
											<c:otherwise><font color="2f6084">사용안함</font></c:otherwise>
										</c:choose>
										<input type="hidden" name="useFg" value="${list.USE_FG}">
									</td>
								</tr>
								<tr >
									<th align="center">박스체적 </th>
									<td align="left" style="padding-left:20px" colspan="5">
										
										<font color="red">
											※ 정확한 숫자를 입력하셔야 납품이 가능합니다.
											예)30cm=300mm,1,000g=1kg 
										</font>
										<br> 
										가로 :   <input type="text" name="width"   style="width:40px;"  maxlength="4"  value="${list.WIDTH}"   onblur="digit_sorter_check(this)" /> mm&nbsp; &nbsp;
										세로 :   <input type="text" name="length"  style="width:40px;"  maxlength="4"  value="${list.LENGTH}"  onblur="digit_sorter_check(this)" /> mm&nbsp; &nbsp;
										높이 :   <input type="text" name="height"  style="width:40px;"  maxlength="4"  value="${list.HEIGHT}"  onblur="digit_sorter_check(this)" /> mm&nbsp; &nbsp;
										총중량 : <input type="text" name="wg"      style="width:40px;"  maxlength="4"  value="${list.WG}"      onblur="digit_sorter_check(this)"  /> kg&nbsp; &nbsp;
									</td>
									<th>혼재여부</th>
									<td align="center">
										<input type=hidden id="mixProdFg_edit" name="mixProdFg" value="${list.MIX_PROD_FG}">
										<input type="radio" name="mixProdFg_edit" value="0" <c:if test="${ list.MIX_PROD_FG eq '0' }"> checked	</c:if>  />비혼재<br>
										<input type="radio" name="mixProdFg_edit" value="1" onClick="MIX_NO_READY('mixProdFg_edit');"  <c:if test="${ list.MIX_PROD_FG eq '1' }"> checked	</c:if> />혼재
									</td>
								</tr>
								<tr>
									<th align="center">소터에러사유</th>
									<td style="padding-left:15px" colspan="5">
										<input type="hidden" name="conveyFg" value="${ list.CONVEY_FG}">
										<input type="radio" name="conveyFg_${index.count}" value="0" onClick="setRadioValue(this)" <c:if test="${ list.CONVEY_FG eq '0' }"> checked	</c:if> />없음 &nbsp;
										<input type="radio" name="conveyFg_${index.count}" value="1" onClick="setRadioValue(this)" <c:if test="${ list.CONVEY_FG eq '1' }"> checked	</c:if> />파손가능상품 &nbsp;
										<input type="radio" name="conveyFg_${index.count}" value="2" onClick="setRadioValue(this)" <c:if test="${ list.CONVEY_FG eq '2' }"> checked	</c:if> />비닐포장제품 &nbsp;
										
									</td>
									
									<th align="center">소터구분</th>
									<td align="center">
										<input type="hidden" name="sorterFg" value="${list.SORTER_FG}">
										<c:choose>
											<c:when test="${list.SORTER_FG eq '1'}"><div id=sorterFgNm><font color="2f6084">sorter</font></div></c:when>
											<c:otherwise><div id=sorterFgNm><font color="2f6084">non-sorter</font></div></c:otherwise>
										</c:choose>
										
									</td>
								</tr>
								<tr>
									<th align="center">팔레트</th> 
									<td style="padding-left:20px" colspan="5">
										가로박스수 <input type="text" name="innerIpsu" 	value="${list.INNER_IPSU}" 		onblur="digit_pallet(this)" style="width:50px;" />&nbsp;&nbsp;&nbsp;
										세로박스수 <input type="text" name="pltLayerQty" 	value="${list.PLT_LAYER_QTY}" 	onblur="digit_pallet(this)" style="width:50px;" />&nbsp;&nbsp;&nbsp;
									        높이박스수 <input type="text" name="pltHeightQty" 	value="${list.PLT_HEIGHT_QTY}"  onblur="digit_pallet(this)" style="width:50px;" />
									</td>
									<th align="center">총박스수</th>
									<td align="center">
										<div id=totalBoxCountNm>${list.TOTAL_BOX}</div>
										<input type ="hidden" name="totalBoxCount" value="${list.TOTAL_BOX}"/>
									</td>
								</tr>
							</table>
							</c:otherwise>
							</c:choose>
							</td>
						</tr>	
						<tr><td height=1 bgcolor=a3a3a3></td></tr>
						
						<c:set var="row_cnt" value="${row_cnt+1 }" />
						
						
					</c:forEach>
					</c:if>
						
					
						
					</table>			
				
				
					
					
				<!-- <div style="width:100%; height:333px;overflow-x:hidden; overflow-y:scroll; table-layout:fixed;white-space:nowrap;">  -->
					
				</div>
			</div>
			<!-- 물류 바코드 자료 필드 끝 -->
			
					
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
					<li>상품정보</li>
					<li>신규상품관리</li>
					<li class="last">물류바코드등록</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>

</body>
</html>
