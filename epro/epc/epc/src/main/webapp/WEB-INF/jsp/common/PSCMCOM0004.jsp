<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=IE8" />

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />


<script language="javascript" type="text/javascript">
    $(document).ready(function()
    {
        //--------------------------------   
        //input enter 막기
        //--------------------------------
        $("*").keypress(function(e){
            if(e.keyCode==13) return false;
        });
        
        $('#search').click(function() {
        	doSearch();
        });
        $('#close').click(function() {
        	top.close();
        });
        
     // START of IBSheet Setting
    	createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "253px");
    	mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
    	
        var ibdata = {};
    	// SizeMode: 사이즈 방식, SearchMode: 조회 방식, MergeSheet: 머지 종류 
    	ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral, MergeSheet:msHeaderOnly}; // 10 row씩 Load
    	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
    	ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

    	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
    	ibdata.Cols = [
            {Header:"순번",               		Type:"Int",        	SaveName:"num",                    	Align:"Center", Width:45,  Edit:0}
    	  , {Header:"카테고리ID", 			Type:"Text",		SaveName:"categoryId", 			Align:"Left", 	  Width:120,  Edit:0, Cursor:'pointer', Color:'blue', FontUnderline:true}
    	  , {Header:"카테고리명", 		 	Type:"Text", 		SaveName:"categoryNm", 			Align:"Left", 	  Width:210,  Edit:0, Ellipsis:true}
    	  , {Header:"근거리전용여부", 		Type:"Text", 		SaveName:"stdistExuseYN", 		Align:"Center", Width:114,  Edit:0}
    	  , {Header:"시작날짜", 				Type:"Text", 		SaveName:"startDate", 				Align:"Center", Width:80,  Edit:0, Hidden:true}
    	  , {Header:"종료날짜",         		Type:"Text", 		SaveName:"endDate",      	        Align:"Center", Width:80,  Edit:0, Hidden:true}	  

    	];
    	
    	IBS_InitSheet(mySheet, ibdata);
	
    	mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
    	mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
    	
    	doSearch();
    });
        
    
    /** ********************************************************
     * 조회 처리 함수
     ******************************************************** */
    function doSearch()
    {
        var url = '<c:url value="/common/selectCategorySearch.do"/>';
        var param = new Object();
        
    	param.categoryTypeCd = $('#categoryTypeCd').val();
    	param.categoryIdDepth2 = $('#categoryIdDepth2').val();
    	param.mode = 'search';
    	
    	loadIBSheetData(mySheet, url, null, '#dataForm', param);
    }
 
    
	//셀 클릭 시...
	function mySheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
	
		var categoryId = mySheet.GetCellValue(Row, 'categoryId')
		var categoryNm = mySheet.GetCellValue(Row, 'categoryNm')
		var startDate = mySheet.GetCellValue(Row, 'startDate')
		var endDate = mySheet.GetCellValue(Row, 'endDate')
		
		opener.setCategoryInto(categoryId, categoryNm, startDate, endDate);
		top.close();
	}
	
	// 데이터 읽은 직후 이벤트
	function mySheet_OnSearchEnd() {
		mySheet.FitColWidth();
	}
</script>
</head>

<body>
<form name="dataForm" id="dataForm" method="post">
<!-- hidden value -->
<input type="hidden" id="categoryTypeCd" name="categoryTypeCd" value="<c:out value='${categoryTypeCd}' />"/>

<div id="popup" style="width:99%;">
    <!------------------------------------------------------------------ -->
    <!--    title -->
    <!------------------------------------------------------------------ -->
    <div id="p_title1">
        <h1>카테고리조회팝업</h1>
        <span class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
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
    <!--    검색조건 -->
    <!------------------------------------------------------------------ -->
    <div class="bbs_search2" style="width:100%;">
            <ul class="tit">
            <li class="tit">[카테고리]</li>
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
                <col width="30%">
                <col width="55%">
            </colgroup>
            <!-- row 1 ------------------------------------->
            <tr>
                <th>대분류</th>
                <td>
                    <select name="categoryIdDepth2" id="categoryIdDepth2" style="width:85px;" class="select">
                        <option value="">---전체---</option>
<c:forEach items="${selectCategoryListDepth2}" var="cat" varStatus="status">
                        <option value="<c:out value='${cat.CATEGORY_ID}' />"><c:out value="${cat.CATEGORY_NM}" /></option>
</c:forEach>
                    </select>
                </td>
                <td></td>
            </tr>
        </table>
        <!---------------------------------------------------- end of table -- -->
    </div>
    <!----------------------------------------------------- end of 검색조건 -->
                
    <!-- -------------------------------------------------------- -->
    <!--    검색내역    -->
    <!-- -------------------------------------------------------- -->
    <div class="wrap_con">
        <div class="bbs_list">
            <table cellpadding="0" cellspacing="0" border="0" width="100%">
                <tr>
                    <div id="ibsheet1"></div>
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
            <div class="location">
        </div>
    </div></div>
    <!---------------------------------------------end of footer -->

</div><!-- id popup// -->
</form>

</body>
</html>