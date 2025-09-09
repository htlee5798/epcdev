<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jstl/core_rt"       %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"    %>
<%@ taglib prefix="lfn"    uri="/WEB-INF/tlds/function.tld"             %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt"       %>
<%@page import="com.lottemart.epc.common.util.SecureUtil"%>

<% 
	String prodCd   = SecureUtil.stripXSS(request.getParameter("prodCd"  ));
	String itemCd   = SecureUtil.stripXSS(request.getParameter("itemCd"  ));
	String strCd    = SecureUtil.stripXSS(request.getParameter("strCd"   ));
	String vendorId = SecureUtil.stripXSS(request.getParameter("vendorId"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Cache-Control"   content="No-Cache"     >
<meta http-equiv="Pragma"          content="No-Cache"     >
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<!-- WISE 그리드 초기화 -->
<script type="text/javascript" for="WG1" event="Initialize()">
	setWiseGridProperty(WG1);
	setHeader();
</script>

<!--  	서버와의 통신이 정상적으로 완료되면 발생한다.   -->
<script language=javascript for="WG1" event="EndQuery()">
	GridEndQuery();
</script>

<!-- 통신 시 에러 발생 -->
<script language="javascript" for="WG1" event="ErrorQuery(strSource, nCode, strMessage, strDescription)">
    hideLoadingMask();
</script>

<!--	 WiseGrid의 셀의 내용이 변경되었을때 발생한다. -->
<script language=javascript for="WG1" event="ChangeCell(strColumnKey,nRow,nOldValue,nNewValue)">
	//GridChangeCell(strColumnKey, nRow);
</script>

<!--   	 WiseGrid의 t_combo타입의 컬럼내용이 변경되었을때 발생합니다  -->
<script language=javascript for="WG1" event="ChangeCombo(strColumnKey,nRow,nOldIndex,nNewIndex)">
	GridChangeCell(strColumnKey, nRow);
</script>
<script type="text/javascript" for="WG1" event="CellClick(strColumnKey,nRow)">
	
</script>

<!-- 그리드 헤더처리 -->
<script type="text/javascript">
	/* 서버와의 통신이 정상적으로 완료되면 발생한다. */
	function GridEndQuery() {
		var GridObj = document.WG1;
		
		//서버에서 mode로 셋팅한 파라미터를 가져온다.
		var mode = GridObj.GetParam("mode");
	
		if(mode == "search") { 
			
			if(GridObj.GetStatus() != "true") 	{// 서버에서 전송한 상태코드를 가져온다.
				var error_msg = GridObj.GetMessage(); // 서버에서 전송한 상태코드값이 false라면 에러메세지를 가져온다.
				
				if(error_msg!=''){	// error	
					$('#resultMsg').text('msg.common.fail.request');
				}else{				// 0건
					$('#resultMsg').text('<spring:message code="msg.common.info.nodata"/>');
				}
				
			}else{
				$('#resultMsg').text('<spring:message code="msg.common.success.select"/>');
			}
		}
	
		if(mode == "insert"||mode == "update"||mode == "delete") {
			if(GridObj.GetStatus() == "true") 	{// 서버에서 전송한 상태코드를 가져온다.
				//서버에서 saveData 셋팅한 파라미터를 가져온다.
				var saveData = GridObj.GetParam("saveData");
				alert(saveData);
				//doSearch();
				opener.doSearch();
				top.close();
			} else	{
				var error_msg = GridObj.GetMessage(); // 서버에서 전송한 상태코드값이 false라면 에러메세지를 가져온다.
				alert(error_msg);
			}
		}	
	}
	function setHeader() {
	 	var gridObj = document.WG1;

	 	gridObj.AddHeader("num",   				"순번",			"t_number",  	100,	40,		false);
	    gridObj.AddHeader("applyStartDate",   	"적용시작일자", 	"t_text",  		100, 	170,	false);
	    gridObj.AddHeader("endDate",   			"적용종료일자", 	"t_text",  		100, 	170,	false);
	    gridObj.AddHeader("buyPrc",   			"원가", 			"t_number",  	100, 	125,	false);	    
	    gridObj.AddHeader("sellPrc",   			"매가", 			"t_number",  	100, 	125,	false);
	    gridObj.AddHeader("currSellPrc",   		"판매가", 		"t_number",  	100, 	125,	false);

	    gridObj.BoundHeader();

	    gridObj.SetNumberFormat("buyPrc", "#,##0");
	    gridObj.SetNumberFormat("sellPrc", "#,##0");
	    gridObj.SetNumberFormat("currSellPrc", "#,##0");
	    
	    gridObj.SetColCellAlign("num", "center");
	    gridObj.SetColCellAlign("applyStartDate", "center");
	    gridObj.SetColCellAlign("endDate", "center");
	    gridObj.SetColCellAlign("buyPrc", "center");
	    gridObj.SetColCellAlign("sellPrc", "center");
	    gridObj.SetColCellAlign("currSellPrc", "center");
	    
	}

	
	//WiseGrid 통신 시 에러 발생
	function ErrorQuery(strSource, nCode, strMessage, strDescription) {
		alert(strSource);
	}

	function goPage(currentPage){
		var gridObj = document.WG1;
		var url = '<c:url value="/product/prdPriceDetailSearch.do"/>';

		gridObj.setParam('prodCd', $("#prodCd").val());
		gridObj.setParam('itemCd', $("#itemCd").val());
		gridObj.setParam('strCd', $("#strCd").val());		
		gridObj.setParam('mode', 'search');
		
		gridObj.DoQuery(url);
	}
</script>
<script language="javascript" type="text/javascript">
	$(document).ready(function(){
		//input enter 막기
		$("*").keypress(function(e){
	        if(e.keyCode==13) return false;
		});
		goPage('1');
	});

	/** ********************************************************
	 * 조회 처리 함수
	 ******************************************************** */
	function doSearch() {
		goPage('1');
	}
	
	// 현재창 닫기
	function doClose(){
		top.close();
	}

</script>
</head>

<body>
<form name="dataForm" id="dataForm">

<input type="hidden" name="prodCd"   id="prodCd"   value="<%=prodCd%>"  />
<input type="hidden" name="prodCd"   id="itemCd"   value="<%=itemCd%>"  />
<input type="hidden" name="prodCd"   id="strCd"    value="<%=strCd%>"   />
<input type="hidden" name="vendorId" id="vendorId" value="<%=vendorId%>"/>


<div id="popup">
    <!--  @title  -->
     <div id="p_title1">
		<h1>가격정보</h1>
        <span class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
     </div>
     <!--  @title  //-->
     <!--  @process  -->
     <div id="process1">
		<ul>
			<li>홈</li>
			<li>상품관리</li>
			<li class="last">인터넷상품관리</li>			
		</ul>
     </div>
	 <!--  @process  //-->
	 <div class="popup_contents">
		<!--  @작성양식 2 -->


			<!-- list -->
			<div class="bbs_search3">
				<ul class="tit">
					<li class="tit">가격 상세 목록</li>
					<li class="btn">
						<a href="javascript:doClose();" class="btn" ><span>닫기</span></a>
					</li>
				</ul>
	
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td><script type="text/javascript">initWiseGrid("WG1", "774", "420");</script></td>
					</tr>
				</table>
			</div>
		</div>
	 	
	<!-- /div-->
	
</div>	
</form>

</body>
</html>