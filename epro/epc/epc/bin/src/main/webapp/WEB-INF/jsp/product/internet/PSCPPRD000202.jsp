<%@page import="com.lottemart.epc.common.util.SecureUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="epcutl" uri="/WEB-INF/tlds/epcutl.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% 
	String prodCd = SecureUtil.stripXSS(request.getParameter("prodCd"));
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Cache-Control"   content="No-Cache"     >
<meta http-equiv="Pragma"          content="No-Cache"     >

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHeadPopup.do" />

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
	ErrorQeury(strSource, nCode, strMessage, strDescription);
</script>

<!--     WiseGrid의 셀의 내용이 변경되었을때 발생한다. -->
<script language=javascript for="WG1" event="ChangeCell(strColumnKey,nRow,nOldValue,nNewValue)">
    GridChangeCell(strColumnKey, nRow);
</script>

<!-- 그리드 헤더처리 -->
<script type="text/javascript">
	/* 서버와의 통신이 정상적으로 완료되면 발생한다. */
	function GridEndQuery() 
	{
		var GridObj = document.WG1;
		
		//서버에서 mode로 셋팅한 파라미터를 가져온다.
		var mode = GridObj.GetParam("mode");
	
		if (mode == "search") 
		{ 
			if (GridObj.GetStatus() != "true") // 서버에서 전송한 상태코드를 가져온다. 	
			{
				var error_msg = GridObj.GetMessage(); // 서버에서 전송한 상태코드값이 false라면 에러메세지를 가져온다.
				
				if (error_msg!='') // error
				{	
					$('#resultMsg').text('<spring:message code="msg.common.fail.request"/>');
				}
				else // 0건
				{
					$('#resultMsg').text('<spring:message code="msg.common.info.nodata"/>');
				}
			}
			else
			{
				$('#resultMsg').text('<spring:message code="msg.common.success.select"/>');
			}

			// 조회된 데이터 checkbox 비활성화------------------------- 
			var rowCount = GridObj.getRowCount();
			for ( var i=0; i<rowCount; i++) 
			{ 
				if (GridObj.GetCellValue("existChk", i) == '01')
				{
				    // 체크박스 비활성화
					GridObj.SetCellActivation('CHK', i, 'disable');
				}
			}
		}
	
		if (mode == "insert"||mode == "update"||mode == "delete") 
		{
			if (GridObj.GetStatus() == "true") // 서버에서 전송한 상태코드를 가져온다.
			{
				var saveData = GridObj.GetParam("saveData"); //서버에서 saveData 셋팅한 파라미터를 가져온다.
				alert(saveData);
				opener.doSearch();
				top.close();
			} 
			else	
			{
				var error_msg = GridObj.GetMessage(); // 서버에서 전송한 상태코드값이 false라면 에러메세지를 가져온다.
				alert(error_msg);
			}
		}	
	}
	
	function setHeader() 
	{
	 	var gridObj = document.WG1;
	
	 	gridObj.AddHeader("CHK",                    "",                     "t_checkbox",   100,     20,     true);      
        gridObj.AddHeader("num",                    "순번",                 "t_number",     100,     49,    false);
        gridObj.AddHeader("siteNm",                 "연동사이트",           "t_text",       100,    140,    false);
        gridObj.AddHeader("siteProdCd",             "연동사이트상품코드",   "t_text",       100,    150,    false);
        gridObj.AddHeader("fedayMallProdDivnNm",    "상품속성",             "t_text",       100,    200,    false);
        gridObj.AddHeader("catCd",                  "카테고리",             "t_text",       100,    140,    false);
        gridObj.AddHeader("useYn",                  "사용유무",             "t_text",       100,     75,    false);
        gridObj.AddHeader("siteCd",                 "사이트코드",           "t_text",       100,    280,    false);
        gridObj.AddHeader("prodCd",                 "상품코드",             "t_text",       100,    400,    false);

	    gridObj.BoundHeader();
	    
        //체크박스 전체선택 true
        gridObj.SetColHDCheckBoxVisible("CHK", true); 

        // 수정 가능 컬럼에 표시
        gridObj.SetColCellBgColor("useYn", '<spring:message code="config.common.grid.enableChangeColor"/>');

        gridObj.SetColCellAlign("num", "center");
        gridObj.SetColCellAlign("siteNm", "center");
        gridObj.SetColCellAlign("siteProdCd", "center");
        gridObj.SetColCellAlign("fedayMallProdDivnNm", "center");
        gridObj.SetColCellAlign("catCd", "center");
        gridObj.SetColCellAlign("useYn", "center");
        
        gridObj.SetColHide("prodCd", true);
        gridObj.SetColHide("siteCd", true);

        gridObj.SetColButtonDisplayStyle('useYn','always');
        gridObj.AddComboListValue("useYn", "Y", "Y"); 
        gridObj.AddComboListValue("useYn", "N", "N");
	}

	
	//WiseGrid 통신 시 에러 발생
	function ErrorQuery(strSource, nCode, strMessage, strDescription) 
	{
		alert(strSource);
	}
	
    function GridChangeCell(strColumnKey, nRow) {
        var GridObj = document.WG1;
        if(strColumnKey != "CHK") {
            GridObj.SetCellValue("CHK", nRow, "1");
            GridObj.SetCellBgColor(strColumnKey, nRow, '<spring:message code="config.common.grid.changedColor"/>');
        }
    }

	function goPage(currentPage)
	{
		var gridObj = document.WG1;
		var url = '<c:url value="/product/selectProductSiteLinkSearch.do"/>';

		gridObj.setParam('prodCd', $("#prodCd").val());
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
        $('#close').click(function() {
        	top.close();
        });
	});

	/** ********************************************************
	 * 조회 처리 함수
	 ******************************************************** */
	function doSearch() 
	{
		goPage('1');
	}
</script>
</head>

<body>
<form name="dataForm" id="dataForm">
<input type="hidden" name="prodCd" id="prodCd" value="<%=prodCd%>">

<div id="popup">
    <!--  @title  -->
     <div id="p_title1">
		<h1>연동사이트 정보</h1>
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
		<div class="bbs_search3">
				<ul class="tit">
					<li class="tit">연동사이트 정보</li>
					<li class="btn">
						<a href="#" class="btn" id="close"><span><spring:message code="button.common.close"/></span></a>
					</li>
				</ul>

				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td><script type="text/javascript">initWiseGrid("WG1", "774", "150");</script></td>
					</tr>
				</table>
		</div>
	 </div>
</div>	
</form>

</body>
</html>