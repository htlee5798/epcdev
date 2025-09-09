<%--
	Page Name 	: NEDMPRO0311.jsp
	Description : 행사 테마번호 조회 팝업
	Modification Information
	
	  수정일 			  수정자 				수정내용
	---------- 		---------    		-------------------------------------
	2025.04.15 		park jong gyu	 		최초생성
	
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
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

<script type="text/javascript">
$(document).ready(function(){	
    // 닫기버튼 클릭
    $('#close').click(function() {
        //window.close();    
        top.close();
    });
    
    
    doSearch('1');
    
    $('#srcSubTxt').keydown(function() {
	    if (event.keyCode == 13) {
    		event.preventDefault();
    		doSearch('1');
	    }
	});
}); // end of ready


// 조회
function doSearch(pageIndex){
	
	let searchInfo = { 
				'srcSubTxt' : $('input[name="srcSubTxt"]').val()
			,	'vkorg' : $('input[name="vkorg"]').val() 				
	};
	
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		url : '<c:url value="/edi/product/selectProdEvntThmList.json"/>',
		data : JSON.stringify(searchInfo),
		success : function(data) {
			$("#pageIndex").val(pageIndex);
			
			//json 으로 호출된 결과값을 화면에 Setting
			_setTbody(data.list);
		}
	});	
	
}

function _setTbody(json){
	setTbodyInit("dataListbody");	// dataList 초기화
	
	let data = json;
	
	if (data.length > 0) {
		$("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
		$("#paging-ul").html(json.contents);
	} else {
		setTbodyNoResult("dataListbody", 7);
		$("#paging-ul").html("");
	}
}

function selectProdEvntThm(obj) {
	var rtnData = {};
	
	$(obj).closest("tr").find("input").each(function(){
		if(this.name != undefined && this.name != null && this.name != ""){
			rtnData[this.name] = $.trim($(this).val());
		}
	});
	
	//rtnData["trNum"] = $('input[name=trNum]').val();
	opener.setProdEvntThm(rtnData);
	top.close();
	
}

</script>

<script id="dataListTemplate" type="text/x-jquery-tmpl">
<tr class="r1"  bgcolor=ffffff>
	<td align="left"><c:out value="\${rowNum}" /></td>
	<td align="center" style="text-align: center;">
		<a href="javascript:void(0);" onclick="selectProdEvntThm(this);">
			<c:out value="\${subNo}" />	
		</a>
	</td>
	<td align="left"><c:out value="\${subTxt}" /></td>
	<td align="left"><c:out value="\${stDy}" /></td>
	<td align="left"><c:out value="\${enDy}" /></td>
	<td align="left"><c:out value="\${purStDy}" /></td>
	<td align="left"><c:out value="\${purEnDy}" /></td>

	<input type="hidden" name="subNo" value="<c:out value='\${subNo}'/>"/>
	<input type="hidden" name="subTxt" value="<c:out value='\${subTxt}'/>"/>
	<input type="hidden" name="stDy" value="<c:out value='\${stDy}'/>"/>
	<input type="hidden" name="enDy" value="<c:out value='\${enDy}'/>"/>
	<input type="hidden" name="purStDy" value="<c:out value='\${purStDy}'/>"/>
	<input type="hidden" name="purEnDy" value="<c:out value='\${purEnDy}'/>"/>
</tr>
</script>

</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>

	<input type="hidden" name="vkorg" id="vkorg" value="<c:out value='${vkorg}'/>" > 	

<div id="popup">
    <!------------------------------------------------------------------ -->
    <!--    title -->
    <!------------------------------------------------------------------ -->
    <div id="p_title1">
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
	<div class="bbs_search2" style="width:100%;">
         <ul class="tit">
            <li class="tit">검색조건</li>
            <li class="btn">
                <a href="#" class="btn" id="search" onclick="doSearch(1);"><span><spring:message code="button.common.inquire"/></span></a>
                <a href="#" class="btn" id="close" ><span><spring:message code="button.common.close"  /></span></a>
            </li>
        </ul>
        <table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
        </table>
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
            	<th>테마번호&테마명</th>
            	<td colspan="3">
            		<input type="text" name="srcSubTxt" id="srcSubTxt" value="<c:out value='${subTxt}'/>" style="width: 40%;">
				</td>
            </tr>
        </table>
        <!---------------------------------------------------- end of table -- -->
    </div>
    </div><!-- id popup// -->

	<!----------------------------------------------------- end of 검색조건 -->
				
	<!-- -------------------------------------------------------- -->
	<!--	검색내역 	-->
	<!-- -------------------------------------------------------- -->
	<div class="wrap_con">
		<div class="bbs_list">
			<ul class="tit">
				<li class="tit">행사테마번호 조회</li>
			</ul>
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td>
						<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" width="100%" >
							<colgroup>
								<col style="width:35px;" />
								<col style="width:100px;" />
								<col style="width:100px;" />
								<col style="width:100px;" />
								<col style="width:100px;" />
								<col style="width:100px;" />
								<col style="width:100px;" />
							</colgroup>
							<tr>
								<th>No.</th>
								<th>테마 번호</th>
								<th>테마명</th>
								<th>행사시작일</th>
								<th>행사종료일</th>
								<th>발주시작일</th>
								<th>발주종료일</th>
							</tr>
							<tbody id="dataListbody" />
						</table>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<!-----------------------------------------------end of 검색내역  -->
    </div><!-- class popup_contents// -->
    
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



</body>
</html>