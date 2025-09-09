<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ include file="../common.jsp"%>
<%@ include file="/common/scm/scmCommon.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<link type="text/css" rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/style_1024.css"></link>
<script type="text/javascript" src="/js/jquery/jquery-1.5.2.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.form.js"></script>
<script type="text/javascript" src="/js/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.validate.1.8.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.metadata.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.custom.js"></script>
<script type="text/javascript" src="/js/wisegrid/WiseGridTag-dev.js" ></script>
<script type="text/javascript" src="/js/wisegrid/WiseGridExtension.js" ></script>
<script type="text/javascript" src="/js/wisegrid/WiseGridConfig.js" ></script>
<script type="text/javascript" src="/js/epc/Ui_common.js"></script>
<script type="text/javascript" src="/js/epc/common.js"></script>
<script type="text/javascript" src="/js/epc/paging.js" ></script>
<script type="text/javascript" src="/js/epc/validate.js" ></script>
<script type="text/javascript" src="/js/epc/member.js" ></script>
<script type="text/javascript" src="/js/common/utils.js" ></script>
<script type="text/javascript" src="/js/jquery/jquery-1.7.2.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.blockUI.2.39.js"></script>
<script type="text/javascript" src="/js/epc/edi/consult/common.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.handler.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.tmpl.js"></script>
<script language="javascript" type="text/javascript" src="/js/common/json2.js"></script>

<script language="javascript">
$(document).ready(function(){
	
	$('#popupFlag').val("${popupFlag}");
	
	//검색
	$('#search').click(function() {
		goPage("1");
      });
	
	 // 닫기버튼 클릭
    $('#close').click(function() {
        //window.close();    
        top.close();
    });
	 
	 
  //----- 팀 변경시 이벤트
	$("#dataForm select[name=srchTeamCd]").change(function() {

		//----- 대, 중, 소분류 초기화
		$("#srchL1Cd option").not("[value='']").remove();
		$("#srchL2Cd option").not("[value='']").remove();
		$("#srchL3Cd option").not("[value='']").remove();

		_eventSelSrchL1List($(this).val().replace(/\s/gi, ''));
	});
  
	//----- 대분류 변경시 이벤트
	$("#dataForm select[name=srchL1Cd]").change(function() {
		var groupCode	=	$("#dataForm select[name=srchTeamCd]").val().replace(/\s/gi, '');

		//----- 중, 소분류 초기화
		$("#srchL2Cd option").not("[value='']").remove();
		$("#srchL3Cd option").not("[value='']").remove();
		
		commerceChange2(groupCode, $(this).val().replace(/\s/gi, ''), "", "", ""); //--20170520 infoGrpCd3 파라미터 추가
	});

	//----- 중분류 변경시 이벤트
	$("#dataForm select[name=srchL2Cd]").change(function() {
		var groupCode	=	$("#dataForm select[name=srchTeamCd]").val().replace(/\s/gi, '');
		var l1Cd		=	$("#dataForm select[name=srchL1Cd]").val().replace(/\s/gi, '');

		//----- 소분류 초기화
		$("#srchL3Cd option").not("[value='']").remove();

		_eventSelectL3List(groupCode, l1Cd, $(this).val().replace(/\s/gi, ''));
	});

	
	//----- 소분류 변경시 이벤트
	$("#dataForm select[name=l3Cd]").change(function() {
		var l3Cd		=	$("#dataForm select[name=srchL3Cd]").val().replace(/\s/gi, '');

		if ($(this).val().replace(/\s/gi, '') != "") {
			//----- 표시단위, 표시수량 Default 셋팅
			_eventSetDpBaseQty($(this).val());
		}
		
		var newL3Cd      = $("#srchL3Cd option:selected").val(); // 새로 입력한 소분류
        var oldL3Cd      = $("#oldL3Cd").val();        // 기존 입력했던 소분류

        $("input[name=srchL3Cd]").val(newL3Cd);


		_eventSelectGrpList($(this).val().replace(/\s/gi, ''));
	});
}); // end of ready


/* 대분류의 소분류 조회 */
function _eventSelectL3List(groupCode, l1Cd, val) {
	var paramInfo	=	{};

	paramInfo["groupCode"]	=	groupCode
	paramInfo["l1Cd"]		=	l1Cd;
	paramInfo["l2Cd"]		=	val;

	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		async : false,
		url : '<c:url value="/edi/product/NselectL3List.json"/>',
		data : JSON.stringify(paramInfo),
		success : function(data) {
			var resultList	=	data.l3List;
			if (resultList.length > 0) {

				var eleHtml = [];
				for (var i = 0; i < resultList.length; i++) {
					eleHtml[i] = "<option value='"+resultList[i].l3Cd+"'>"+resultList[i].l3Nm+"</option>"+"\n";
				}

				$("#srchL3Cd option").not("[value='']").remove();	//콤보박스 초기화
				$("#srchL3Cd").append(eleHtml.join(''));
			} else {
				$("#srchL3Cd option").not("[value='']").remove();	//콤보박스 초기화
			}
		}
	});
}


/* 대분류의 소분류 조회 */
function _eventSelectGrpList(val) {
	var paramInfo	=	{};
	paramInfo["l3Cd"]		=	val;


	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		async : false,
		url : '<c:url value="/edi/product/NselectGrpList.json"/>',
		data : JSON.stringify(paramInfo),
		success : function(data) {
			var resultList	=	data.grpL3List;
			if (resultList.length > 0) {

				var eleHtml = [];
				for (var i = 0; i < resultList.length; i++) {
					eleHtml[i] = "<option value='"+resultList[i].grpL3Cd+"'>"+resultList[i].grpL3Nm+"</option>"+"\n";
				}

				$("#srchL3Cd option").not("[value='']").remove();	//콤보박스 초기화
				$("#srchL3Cd").append(eleHtml.join(''));
			} else {
				$("#srchL3Cd option").not("[value='']").remove();	//콤보박스 초기화
			}
		}
	});


}


/* 대분류 변경시 이벤트 20170518 추가*/
function commerceChange2(groupCode, val, infoGrpCd, infoGrpCd2, infoGrpCd3){
	//-----중분류 셋팅
	_eventSelectSrchL2List(groupCode, val);
}

/* 대분류 변경시 중분류 호출 이벤트 */
function _eventSelectSrchL2List(groupCode, val) {
	var paramInfo = {};

	paramInfo["groupCode"]	=	groupCode;		//팀코드
	paramInfo["l1Cd"]		=	val;			//대븐류

	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		async : false,
		url : '<c:url value="/edi/product/NselectL2List.json"/>',
		data : JSON.stringify(paramInfo),
		success : function(data) {
			var resultList	=	data.l2List;
			if (resultList.length > 0) {

				var eleHtml = [];
				for (var i = 0; i < resultList.length; i++) {
					eleHtml[i] = "<option value='"+resultList[i].l2Cd+"'>"+resultList[i].l2Nm+"</option>"+"\n";
				}

				$("#srchL2Cd option").not("[value='']").remove();	//콤보박스 초기화
				$("#srchL2Cd").append(eleHtml.join(''));
			} else {
				$("#srchL2Cd option").not("[value='']").remove();	//콤보박스 초기화
			}
		}
	});
}


function _eventSelSrchL1List(val) {
	var paramInfo	=	{};

	paramInfo["groupCode"]	=	val;

	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		async : false,
		url : '<c:url value="/edi/product/NselectL1List.json"/>',
		data : JSON.stringify(paramInfo),
		success : function(data) {
			var resultList	=	data.l1List;
			if (resultList.length > 0) {

				var eleHtml = [];
				for (var i = 0; i < resultList.length; i++) {
					eleHtml[i] = "<option value='"+resultList[i].l1Cd+"'>"+resultList[i].l1Nm+"</option>"+"\n";
				}

				$("#srchL1Cd option").not("[value='']").remove();	//콤보박스 초기화
				$("#srchL1Cd").append(eleHtml.join(''));
			} else {
				$("#srchL1Cd option").not("[value='']").remove();	//콤보박스 초기화
			}
		}
	});
}


//조회 
function goPage(pageIndex) {

	var searchInfo = {};
	$("#dataForm").find("input, select").each(function(){
		if(this.name != null && this.name != ""){
				searchInfo[this.name] = $.trim($(this).val());
		}
	});
	
	searchInfo.pageIndex = pageIndex;
	
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		url : '<c:url value="/edi/product/selSrcmkCdPopupInfo.json"/>',
		data : JSON.stringify(searchInfo),
		success : function(data) {
			$("#pageIndex").val(pageIndex);
			
			//json 으로 호출된 결과값을 화면에 Setting
			_setTbody(data);
		}
	});	
}



/* List Data 셋팅 */
function _setTbody(json) {
    setTbodyInit("dataListbody"); // dataList 초기화

    let data = json.list;
    let popupFlag = $('#popupFlag').val();
    
    if (data != null && data.length > 0) {
		if(popupFlag == "prod"){
			$("#dataListProdTemplate").tmpl(data).appendTo("#dataListbody");
		}else{
	        $("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
		}
	    
		$("#paging-ul").html(json.contents);
    } else {
        setTbodyNoResult("dataListbody", 8);
        $("#paging-ul").html("");
    }
}


//판매코드 콜백
/* function selectSellCdDisplay(obj) {
	var rtnData = {};
	
	rtnData["trNum"] = $('input[name=trNum]').val();
	rtnData['srcmkCd'] = obj;
	opener.setSellCd(rtnData);
	top.close();
} */
 
function selectSellCdDisplay(obj) {
	var rtnData = {};
	
	$(obj).closest("tr").find("input").each(function(){
		console.log(this.name);
		if(this.name != undefined && this.name != null && this.name != ""){
			rtnData[this.name] = $.trim($(this).val());
		}
	});
	
	rtnData["trNum"] = $('input[name=trNum]').val();
	opener.setSellCd(rtnData);
	top.close();
}
 

//상품코드 콜백
/* function selectProdDisplay(prodCd,prodNm) {
	var rtnData = {};
	
	rtnData["trNum"] = $('input[name=trNum]').val();
	rtnData['prodCd'] = prodCd;
	rtnData['prodNm'] = prodNm;
	
	opener.setProdCd(rtnData);
	top.close();
} */
function selectProdDisplay(obj) { 
	var rtnData = {};
	
	$(obj).closest("tr").find("input").each(function(){
		console.log(this.name);
		if(this.name != undefined && this.name != null && this.name != ""){
			rtnData[this.name] = $.trim($(this).val());
		}
	});

	rtnData["trNum"] = $('input[name=trNum]').val();
	opener.setProdCd(rtnData);
	top.close();
}
</script>
	<!-- DATA LIST -->
	<script id="dataListProdTemplate" type="text/x-jquery-tmpl">
		<tr class="r1" bgcolor=ffffff>
			<input type="hidden" name="popupFlag" value="<c:out value='\${popupFlag}'/>"/>
			<%--
			<input type="hidden" name="ean11" value="<c:out value='\${ean11}'/>"/>
			<input type="hidden" name="maktm" value="<c:out value='\${maktm}'/>"/>
			<input type="hidden" name="matnr" value="<c:out value='\${matnr}'/>"/>
			--%>
			<input type="hidden" name="l1Cd" value="<c:out value='\${l1Cd}'/>"/>
			<input type="hidden" name="l1Nm" value="<c:out value='\${l1Nm}'/>"/>
			<input type="hidden" name="l2Cd" value="<c:out value='\${l2Cd}'/>"/>
			<input type="hidden" name="l2Nm" value="<c:out value='\${l2Nm}'/>"/>
			<input type="hidden" name="l3Cd" value="<c:out value='\${l3Cd}'/>"/>
			<input type="hidden" name="l3Nm" value="<c:out value='\${l3Nm}'/>"/>
			<input type="hidden" name="teamCd" value="<c:out value='\${teamCd}'/>"/>
			<input type="hidden" name="teamNm" value="<c:out value='\${teamNm}'/>"/>
			<input type="hidden" name="orgCost" value="<c:out value='\${orgCost}'/>"/>
			<input type="hidden" name="prodPatFg" value="<c:out value='\${prodPatFg}'/>"/>
			<input type="hidden" name="srcmkCd" value="<c:out value='\${srcmkCd}'/>"/>
			<input type="hidden" name="prodCd" value="<c:out value='\${prodCd}'/>"/>
			<input type="hidden" name="prodNm" value="<c:out value='\${prodNm}'/>"/>
			<td align="center">
				<c:out value="\${rnum}"/>
			</td>
			<td align="center">
				<c:out value="\${srcmkCd}" />
			</td>
			<td align="center">
				<a href="javascript:void(0);" onclick="selectProdDisplay(this);">
					 <c:out value="\${prodCd}" />
				 </a>
			</td>
			<td align="center">
				<c:out value="\${prodNm}" />
			</td>
			<td align="center">
				<c:out value="\${l3Nm}" />
			</td>
			<td align="left">
				<c:out value="\${teamNm}" />
			</td>
			<td align="center">
				<c:out value="\${l1Nm}" />
			</td>
			<td align="center">
				<c:out value="\${l2Nm}" />
			</td>
			<td align="center">
				<c:out value="\${l3Nm}" />
			</td>
		</tr>
	</script>
	<script id="dataListTemplate" type="text/x-jquery-tmpl">
		<tr class="r1" bgcolor=ffffff>
			<input type="hidden" name="popupFlag" value="<c:out value='\${popupFlag}'/>"/>
			<%--
			<input type="hidden" name="ean11" value="<c:out value='\${ean11}'/>"/>
			<input type="hidden" name="maktm" value="<c:out value='\${maktm}'/>"/>
			<input type="hidden" name="matnr" value="<c:out value='\${matnr}'/>"/>
			--%>
			<input type="hidden" name="l1Cd" value="<c:out value='\${l1Cd}'/>"/>
			<input type="hidden" name="l1Nm" value="<c:out value='\${l1Nm}'/>"/>
			<input type="hidden" name="l2Cd" value="<c:out value='\${l2Cd}'/>"/>
			<input type="hidden" name="l2Nm" value="<c:out value='\${l2Nm}'/>"/>
			<input type="hidden" name="l3Cd" value="<c:out value='\${l3Cd}'/>"/>
			<input type="hidden" name="l3Nm" value="<c:out value='\${l3Nm}'/>"/>
			<input type="hidden" name="teamCd" value="<c:out value='\${teamCd}'/>"/>
			<input type="hidden" name="teamNm" value="<c:out value='\${teamNm}'/>"/>
			<input type="hidden" name="orgCost" value="<c:out value='\${orgCost}'/>"/>
			<input type="hidden" name="prodPatFg" value="<c:out value='\${prodPatFg}'/>"/>
			<input type="hidden" name="srcmkCd" value="<c:out value='\${srcmkCd}'/>"/>
			<input type="hidden" name="prodCd" value="<c:out value='\${prodCd}'/>"/>
			<input type="hidden" name="prodNm" value="<c:out value='\${prodNm}'/>"/>
			<td align="center">
				<c:out value="\${rnum}"/>
			</td>
			<td align="center">
    			<a href="javascript:void(0);" onclick="selectSellCdDisplay(this);">
        			<c:out value="\${srcmkCd}" />
    			</a>
			</td>
			<td align="center">
				<c:out value="\${prodCd}" />
			</td>
			<td align="center">
				<c:out value="\${prodNm}" />
			</td>
			<td align="left">
				<c:out value="\${teamNm}" />
			</td>
			<td align="center">
				<c:out value="\${l1Nm}" />
			</td>
			<td align="center">
				<c:out value="\${l2Nm}" />
			</td>
			<td align="center">
				<c:out value="\${l3Nm}" />
			</td>
	
		</tr>
	</script>
</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />

<body>
<form name="dataForm" id="dataForm" >
<input type="hidden" id="closeFlag"  name="closeFlag" value="<c:out escapeXml='false' value='${param.closeFlag }'/>"/>
<input type="hidden" id="pageIndex" name="pageIndex" value="<c:out value="${param.pageIndex}" />" />
<input type="hidden" id="trNum" name="trNum" value="<c:out value="${trNum}" />" />
<input type="hidden" id="popupFlag" name="popupFlag" value="<c:out value="${popupFlag}" />" />


<input type="hidden" id="srchProdPatFg" name="srchProdPatFg" value="<c:out value='${param.prodPatFg}'/>"/>	<%-- 상품유형구분 --%>
<input type="hidden" id="srchTaxFg" name="srchTaxFg" value="<c:out value='${param.taxFg}'/>"/>				<%-- 면과세구분 --%>

<div id="popup">
    <!------------------------------------------------------------------ -->
    <!--    title -->
    <!------------------------------------------------------------------ -->
    <div id="p_title1">
        <span class="logo"><img src="/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
    </div>
    <!-------------------------------------------------- end of title -- -->
    
    <br>
	<div class="popup_contents">

	<!------------------------------------------------------------------ -->
	<!-- 	검색조건 -->
	<!------------------------------------------------------------------ -->
	<!-- <form name="searchForm" id="searchForm"> -->
		<div class="bbs_search2" style="width:100%;">
	         <ul class="tit">
	            <li class="tit">판매코드</li>
	            <li class="btn">
	                <a href="#" class="btn" id="search"><span><spring:message code="button.common.inquire"/></span></a>
	                <a href="#" class="btn" id="close" ><span><spring:message code="button.common.close"  /></span></a>
	            </li>
	        </ul>
	        <!------------------------------------------------------------------ -->
	        <!--    table -->
	        <!------------------------------------------------------------------ -->
	       
	        
	        <table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
	            <colgroup>
	                <col width="15%">
	                <col width="40%">
	                <col width="15%">
	                <col width="40%">
	            </colgroup>
	            <!-- row 1 ------------------------------------->
	            <tr>
	            	<th>파트너사</th>
	            	<td>
	            		<html:codeTag objId="srchEntpCd" objName="srchEntpCd"  width="150px;" dataType="CP" comType="SELECT" formName="form" defName="선택" />
					</td>
					<th>상품명</th>
	            	<td>
	            		<input type="text" name="srchProdNm" id="srchProdNm"  style="width:40%;" />
					</td>
					            <!--     <th>신선구분</th>
	                <td>
	                	<select id="srchFresh" name="srchFresh" class="select">
	                		<option value="">선택</option>
							<option value="1">정상</option>
							<option value="2">신선구분1</option>
							<option value="3">신선구분2</option>
	                	</select>
	                </td> -->
	            </tr>
	            <tr>
	           <!--  	<th>발주기능여부</th>
	                <td>
	                	 <select id="srchOrderPossible" name="srchOrderPossible" class="select">
	                		<option value="">전체</option>
							<option value="Y">Y</option>
							<option value="N">N</option>
	                	</select>
	                </td> -->
	                <th>판매코드</th>
	            	<td>
	            		<input type="text" name="srchSellCd" id="srchSellCd"  style="width:40%;" value="<c:out value='${param.srcmkCd}'/>"/>
					</td>
	                <th>상품코드</th>
	                <td>
	                    <input type="text" name="srchProdCd" id="srchProdCd"  style="width:40%;" value="<c:out value='${param.prodCd}'/>"/>
	                </td>
	            </tr>
	             <tr>
	            	<th>팀</th>
					<td>
						<select id="srchTeamCd" name="srchTeamCd" class="required" style="width: 150px;">
							<option value=""><spring:message code="button.common.choice" /></option>
								<c:forEach items="${teamList}" var="teamList" varStatus="index">
									<option value="${teamList.teamCd}">${teamList.teamNm}</option>
								</c:forEach>
						</select>
					</td>
	                <th>대분류</th>
	                <td>
	                	<select id="srchL1Cd" name="srchL1Cd" class="required" style="width: 150px;">
							<option value="">
								<spring:message code="button.common.choice" /></option>
						</select>
	                </td>
	            </tr>
	            <tr>
	            	<th>중분류</th>
	            	<td>
						<select id="srchL2Cd" name="srchL2Cd" class="required">
							<option value=""><spring:message code="button.common.choice" /></option>
						</select>
					</td>
	            	<th>소분류</th>
	            	<td>
	            		<select id="srchL3Cd" name="srchL3Cd" class="required">
							<option value=""><spring:message code="button.common.choice" /></option>
						</select>
						<input type="hidden" 	name="oldL3Cd" 					id="oldL3Cd" 				/>
					</td>
	            </tr>
	        </table>
	        <!---------------------------------------------------- end of table -- -->
	    </div>
	<!--  </form> -->
	<!----------------------------------------------------- end of 검색조건 -->
				
	<!-- -------------------------------------------------------- -->
	<!--	검색내역 	-->
	<!-- -------------------------------------------------------- -->
	<div class="wrap_con">
		<div class="bbs_list">
			<ul class="tit">
				<li class="tit">판매코드 조회</li>
			</ul>
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td>
						<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" width="100%" >
							<colgroup>
								<col width="5%">
								<col width="12%">
								<col width="12%">
								<col width="20%">
								<col width="12%">
								<col width="12%">
								<col width="12%">
								<col width="12%">
							</colgroup>
							<tr>
								<th>No.</th>
								<th>판매코드</th>
								<th>상품코드</th>
								<th>상품명</th>
								<th>상품팀</th>
								<th>대분류명</th>
								<th>중분류명</th>
								<th>소분류명</th>
								<!-- <th>신선구분</th>
								<th>발주가능여부</th> -->
							</tr>
						 	<tbody id="dataListbody" /> 
						</table>
			
					</td>
				</tr>
			
			</table>
		</div>
	</div>
		<!-- Pagging Start ---------->			
		<div id="paging_div" style="text-align:-webkit-center;">
	        <ul class="paging_align" id="paging-ul" style="width: 400px"></ul>
		</div>
		<!-- Pagging End ---------->
	
    </div><!-- class popup_contents// -->
    
    <br/>

</div><!-- id popup// -->
</form>

</body>
</html>