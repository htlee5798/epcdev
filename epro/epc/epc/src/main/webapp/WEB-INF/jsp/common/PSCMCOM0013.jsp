<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="epcutl" uri="/WEB-INF/tlds/epcutl.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHeadPopup.do" />

<!-- WISE 그리드 초기화 -->
<script type="text/javascript" for="WG1" event="Initialize()">
    setWiseGridProperty(WG1);
    setHeader();
</script>

<!--    서버와의 통신이 정상적으로 완료되면 발생한다.   -->
<script language=javascript for="WG1" event="EndQuery()">
    hideLoadingMask();
    GridEndQuery();

    /** ****************************************************
     * Paging 처리
     **************************************************** */
    var totalCnt = "0";
    var rowsPerPage = "0";
    var curPage = "0";

    try
    {
        totalCnt = WG1.getParam("totalCount");
        rowsPerPage = WG1.getParam("rowsPerPage");
        curPage = WG1.getParam("currentPage");
    }
    catch (e) 
    {
        console.error(e.message);
    }

    setLMPaging(totalCnt,rowsPerPage ,curPage ,'goPage','pagingDiv');
</script>

<!-- 통신 시 에러 발생 -->
<script language="javascript" for="WG1" event="ErrorQuery(strSource, nCode, strMessage, strDescription)">
    hideLoadingMask();
</script>

<script type="text/javascript" for="WG1" event="CellClick(strColumnKey,nRow)">

    var gridObj = document.WG1;
    
    //상세정보 함수
    if (strColumnKey == 'loginId')
    {
    	var loginId = gridObj.getCellValue('loginId', nRow);
    	var adminNm = gridObj.getCellValue('adminNm', nRow);
    	
    	opener.setAdminId(loginId,adminNm);
    	top.close();
    }
</script>

<script language="javascript" type="text/javascript">
    $(document).ready(function(){
    	// START of IBSheet Setting
		createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "300px");
		mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
		mySheet.SetDataAutoTrim(false);
		
		var ibdata = {};
		// SizeMode: 
		ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral}; // 10 row씩 Load
		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
		
		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.Cols = [
			{Header:"순번", 	Type:"Int", 	SaveName:"NUM", 		Align:"Center", Width:40,   Edit:0}
		  , {Header:"아이디",  	Type:"Text", 	SaveName:"LOGIN_ID", 	Align:"Center", Width:235,  Edit:0, Cursor:'pointer', Color:'blue', FontUnderline:true}
		  , {Header:"이름", 	Type:"Text", 	SaveName:"ADMIN_NM", 	Align:"Center", Width:382,  Edit:0}
		];

		
		IBS_InitSheet(mySheet, ibdata);
			
		mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.1
		mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
    	
        //input enter 막기
        $("*").keypress(function(e){
            if(e.keyCode==13) return false;
        });
        $('#search').click(function() {
        	doSearch();
        });
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
    
    function goPage(currentPage)
    {
        var url = '<c:url value="/board/selectCCagentSearch.do"/>';

        loadIBSheetData(mySheet, url, currentPage, '#dataForm', null);
    }
    
    function mySheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
    	// 상세정보 함수
		if (Row < 1) return;
		
		var colNm = mySheet.GetCellProperty(1, Col, "SaveName");
		
		if(colNm == 'LOGIN_ID'){
			var loginId = mySheet.GetCellValue(Row, 'LOGIN_ID');
			var adminNm = mySheet.GetCellValue(Row, 'ADMIN_NM');
			
			opener.setAdminId(loginId,adminNm);
	    	top.close();
		}
    }
</script>
</head>

<body>
<form name="dataForm" id="dataForm">
<div id="popup">
    <!--  @title  -->
    <div id="p_title1">
        <h1>상담원검색</h1>
        <span class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
    </div>
    <!--  @title  //-->
    <!--  @process  -->
    <div id="process1">
        <ul>
            <li>홈</li>
            <li class="last">상담원검색팝업</li>           
        </ul>
    </div>
    <!--  @process  //-->
    <div class="popup_contents">
        <!--  @작성양식 2 -->
        <div class="bbs_search3">
            <ul class="tit">
                <li class="tit">상담원조회</li>
                <li class="btn">
                    <a href="#" class="btn" id="search"><span><spring:message code="button.common.inquire"/></span></a>
                    <a href="#" class="btn" id="close" ><span><spring:message code="button.common.close"  /></span></a>
                </li>
            </ul>
            <table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
	            <colgroup>
                    <col width="15%">
                    <col width="35%">
                    <col width="15%">
                    <col width="35%">
	            </colgroup>
	            <tr>
	                <th><span class="star"></span >아이디</th>
	                <td>
	                    <input type="text" name="loginId" id="loginId" value="" style="width:70%;" />
	                </td>
	                    <th><span class="star"></span >이름</th>
	                <td>
	                    <input type="text" name="adminNm" id="adminNm" value="" style="width:70%;" />
	                </td>
	            </tr>
            </table>
        </div>
        <br/>
        <!-- list -->
        <div class="bbs_search3">
            <ul class="tit">
                <li class="tit">상담원목록</li>
            </ul>

            <table cellpadding="0" cellspacing="0" border="0" width="100%">
	            <tr>
	               <td><div id="ibsheet1"></div></td>
	            </tr>
            </table>
           </div>
       </div>

    <div id="pagingDiv" class="pagingbox2" style="width: 98%;">
        <script> setLMPaging("0", "0", "0","goPage","pagingDiv");</script>
    </div>
    
</div>
</form>

</body>
</html>