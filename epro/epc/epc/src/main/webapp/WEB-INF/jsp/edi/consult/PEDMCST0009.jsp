<%@include file="../common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>

<script>
	function PopupWindow(pageName) {
		var cw=400;
		var ch=440;
		var sw=screen.availWidth;
		var sh=screen.availHeight;
		var px=Math.round((sw-cw)/2);
		var py=Math.round((sh-ch)/2);
		window.open(pageName,"","left="+px+",top="+py+",width="+cw+",height="+ch+",toolbar=no,menubar=no,status=yes,resizable=no,scrollbars=yes");
	}

	function checkAll(){
		form=document.forms[0];
		var checkboxList = form.ck;

		if(form.notData.value=="none"){
			return;
		}

		if(form.ckall.length){
			if(form.ckall.checked==true){
				for(var i=0;i<checkboxList.length;i++){
					checkboxList[i].checked=true;
				}
			}else if(form.ckall.checked==false){
				for(var i=0;i<checkboxList.length;i++){
					checkboxList[i].checked=false;
				}
			}
		}else{
			if(form.ckall.checked==true){
				for(var i=0;i<checkboxList.length;i++){
					checkboxList.checked=true;
				}
			}else if(form.ckall.checked==false){
				for(var i=0;i<checkboxList.length;i++){
					checkboxList.checked=false;
				}
			}
		}
	
		if(form.ckall.checked==true){
			for(var i=0;i<checkboxList.length;i++){
				checkboxList[i].checked=true;
			}
		}else if(form.ckall.checked==false){
			for(var i=0;i<checkboxList.length;i++){
				checkboxList[i].checked=false;
			}
		}
	}

	function doSearch() {
		
		var form = document.forms[0];

		var tmp="";

		if(form.proCode1.value=="" && form.proCode2.value=="" && form.proCode3.value==""
			&& form.proCode4.value=="" && form.proCode5.value=="" && form.proCode6.value==""){

			alert("<spring:message code='msg.consult.orderstop.product'/>");
			return;
		} 

		
		if(form.proCode1.value != "" ){
			tmp += form.proCode1.value+";";
		}
		if(form.proCode2.value != "" ){
			tmp += form.proCode2.value+";";
		}
		if(form.proCode3.value != "" ){
			tmp += form.proCode3.value+";";
		}
		if(form.proCode4.value != "" ){
			tmp += form.proCode4.value+";";
		}
		if(form.proCode5.value != "" ){
			tmp += form.proCode5.value+";";
		}
		if(form.proCode6.value != "" ){
			tmp += form.proCode6.value+";";
		}

		

		form.productVal.value=tmp;
		
		loadingMaskFixPos();
		form.action  = "<c:url value='/edi/consult/PEDMCST0009Select.do'/>";
		form.submit();		
		
	}

	function storePopUp(){
		PopupWindow("<c:url value='/edi/comm/PEDPCOM0001.do'/>");
	}


	function fnOnlyNumber(){
		if(event.keyCode < 48 || event.keyCode > 57)
		event.keyCode = null;
	}




	function storeClear(){
		var form = document.forms[0];
		form.storeVal.value="";
	}

	function doInsert(){
	//	alert("2016/2/20부터 발중중단 기능을 제한합니다.");
	//	return;
		var form = document.forms[0];

		var checkboxList = form.ck;

		var tmp="";
		var tmp_ck="none";

		var tmp="";

		if(form.notData.value == "none"){
			alert("조회된 Data가 없습니다.");
			return;
		}

		if(checkboxList.length){
			for(var i=0;i<checkboxList.length;i++){
				if(checkboxList[i].checked==true){
					tmp_ck="exist";
				}
			}
		}else{
			if(checkboxList.checked==true){
				tmp_ck="exist";
			}
		}

		if(tmp_ck=="none"){
			alert("선택된 Data가 없습니다.");
			return;
		}
		
		if(confirm("<spring:message code='msg.consult.orderstop.reg'/>")){
			if(form.ck.length){
				for(var i=0;i<form.ck.length;i++){
					if(form.ck[i].checked){
						tmp += form.ck[i].value+"@";
					}
				}
			}else{
				tmp=form.ck.value+"@";
			}


			form.forward_value.value=tmp;
			loadingMaskFixPos();
			form.action  = "<c:url value='/edi/consult/PEDMCST0009Insert.do'/>";
			form.submit();	
		}
	}

	function keyValid(obj){
		for( i=0 ; i<obj.value.length; i++){
			if(obj.value.charAt(i) <"0" || obj.value.charAt(i)>"9"){
				alert("<spring:message code='msg.common.error.noNum'/>");
				obj.focus();
				obj.value="";
				return;
			}
		}
	}
</script>

</head>

<body>
	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if> >
		<div>
			<form name="searchForm" method="post" action="#">
			<input type="hidden" id="widthSize" name="widthSize" value="${param.widthSize }" > 
			<input type="hidden" id="forward_value" name="forward_value" / >
			<div id="wrap_menu">
				<!--	@ 검색조건	-->
				<div class="wrap_search">
					<!-- 01 : search -->
					<div class="bbs_search">
						<ul class="tit">
							<li class="tit">검색조건</li>
							<li class="btn">
						<!-- 	<font color="blue">* 2016/2/21부터 발주중단 확정 기능 중지를 알려드립니다.</font>
						 -->
						 		<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
								<%-- <a href="#" class="btn" onclick="doInsert();"><span><spring:message code="button.common.confirmation"/></span></a> --%>
							</li>
						</ul>
						<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
						
						<input type="hidden" id="storeVal" name="storeVal"  value="${param.storeVal }" />
						<input type="hidden" id="productVal" name="productVal"  />
						
						<colgroup>
							<col style="width:100px;" />
							<col style="width:200px;" />
							<col style="width:100px;" />
							<col style="width:200px;" />
						</colgroup>
						<tr>
							<th>협력업체 코드</th>
							<td>
								<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="" dataType="CP" comType="SELECT" formName="form" defName="전체" />
							</td>
							<th><span class="star">*</span> 점포선택</th>
							<td>
								<input type="Radio" name="strCdType" <c:if test="${empty param.storeVal }"> Checked</c:if> onclick="javascript:storeClear();"/>전점조회
								<input type="Radio" name="strCdType" <c:if test="${not empty param.storeVal }"> Checked</c:if> onclick="javascript:storePopUp();"/>점포선택
							</td>
							
						</tr>
						<tr>
							<th>상품코드</th>
							<td colspan="3">
								1)&nbsp;<input type="text" name="proCode1" maxlength="10" value="${paramMap.proCode1}" onkeyup="javsscript:keyValid(this);"/>&nbsp;&nbsp;
								2)&nbsp;<input type="text" name="proCode2" maxlength="10" value="${paramMap.proCode2}" onkeyup="javsscript:keyValid(this);"/>&nbsp;&nbsp;
								3)&nbsp;<input type="text" name="proCode3" maxlength="10" value="${paramMap.proCode3}" onkeyup="javsscript:keyValid(this);"/><br>
								4)&nbsp;<input type="text" name="proCode4" maxlength="10" value="${paramMap.proCode4}" onkeyup="javsscript:keyValid(this);"/>&nbsp;&nbsp;
								5)&nbsp;<input type="text" name="proCode5" maxlength="10" value="${paramMap.proCode5}" onkeyup="javsscript:keyValid(this);"/>&nbsp;&nbsp;
								6)&nbsp;<input type="text" name="proCode6" maxlength="10" value="${paramMap.proCode6}" onkeyup="javsscript:keyValid(this);"/>
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
							<col style="width:30px;" />
							<col style="width:50px;" />
							<col style="width:100px;" />
							<col style="width:150px;" />
							<col style="width:300px;" />
							<col  />
						</colgroup>
						<tr>
							<th><input type="checkbox" name="ckall" onclick="javascript:checkAll();" /></th>
							<th>NO.</th>
							<th>발주중단 점포</th>
							<th>상품코드</th>
							<th>상품명</th>
							<th>등록일자</th>
						</tr>
						</table>
						<div class="datagrade_scroll_sum">
						<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
						<colgroup>
							<col style="width:30px;" />
							<col style="width:50px;" />
							<col style="width:100px;" />
							<col style="width:150px;" />
							<col style="width:300px;" />
							<col  />
						</colgroup>
						<c:if test="${not empty conList }">
							<input type="hidden" name="notData" value="exist"/>
							<c:set var="num"  value="1" />
							<c:forEach items="${conList}" var="list" varStatus="index" >
								<tr class="r1">
									<td align="center"><input type="checkbox" name="ck" value="${list.STR_CD };${list.INSERT_REG_DT};${list.VEN_CD};${list.PROD_CD}"/></td>
									<td align="center">${num }</td>
									<td align="center">${list.STR_NM }</td>
									<td align="center">${list.PROD_CD }</td>
									<td align="left">&nbsp;&nbsp;${list.PROD_NM }</td>
									<td align="center">${list.REG_DT }</td>
								</tr>
								<c:set var="num" value="${num + 1 }" />
							</c:forEach>
						</c:if>
						<c:if test="${empty conList }">
							<input type="hidden" name="notData" value="none"/>
							<tr><td colspan="6" align=center>Data가 없습니다.</td></tr>
						</c:if>
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
						<li>협업</li>
						<li>협업정보</li>
						<li class="last">발주중단 등록</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- footer //-->		
	</div>

</body>
</html>

