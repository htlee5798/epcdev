<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jstl/core_rt"         %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions"   %>
<%@ taglib prefix="lfn"    uri="/WEB-INF/tlds/function.tld"               %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui"     uri="http://lcnjf.lcn.co.kr/taglib/paging"     %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"      %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt"         %>
<%@ include file="/common/scm/scmCommon.jsp"%>
<%@page import="lcn.module.common.util.DateUtil"%>

<%   String today = DateUtil.formatDate(DateUtil.getToday(),"-"); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/style_1024.css"></link>
<!-- <script type="text/javascript" src="/js/epc/Ui_common.js"></script>
<script type="text/javascript" src="/js/epc/common.js"></script>
<script type="text/javascript" src="/js/epc/paging.js"></script>
 -->
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<!-- system/PSCMSYS0003 -->

<script type="text/javascript">
	/** ********************************************************
	 * jQeury 초기화
	 ******************************************************** */
	$(document).ready(function(){
		// START of IBSheet Setting
		createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "360px");
		mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
		mySheet.SetDataAutoTrim(true);
		
		var ibdata = {};
		// SizeMode: 
		ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral}; // 10 row씩 Load
		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.Cols = [
			{Header:"", 		 											Type:"CheckBox", 	SaveName:"chk", 											Align:"Center",	Width:30, 		 	Edit:1, Sort : false }
		  , {Header:"확인",											 Type:"Status" ,				SaveName:"S_STATUS", 		    Align:"Center",			Width:50, 		Edit:0 ,Hidden:true } 
		  , {Header:"업체코드",		 							Type:"Text", 	SaveName:"vendorId",	  								Align:"Center",			Width:80,		 	Edit:0}
		  , {Header:"업체명",		 								Type:"Text", 	SaveName:"vendorNm",	  								Align:"left",	Width:150,		 	Edit:1, EditLen: 100 }
		  , {Header:"도서산간배송비여부",		 	Type:"Combo", 	SaveName:"islndDeliAmtYn",	  				Align:"Center",	Width:130,		 	Edit:1,  ComboText:"미배송|배송",ComboCode:"N|Y"}
		  , {Header:"전화번호",							 		Type:"Text", 	SaveName:"repTelNo",	  								Align:"left", 	Width:100,	ExceptKeys:"E",		Edit:1, EditLen: 14}
		  , {Header:"우편번호",								 	Type:"Text", 	SaveName:"zipCd",	 	  										Align:"Center", 	Width:80,	AcceptKeys:"N",		Edit:1, EditLen: 6 }
		  , {Header:"주소",										 	Type:"Text", 	SaveName:"addr_1_nm",	 	  							Align:"left", 		Width:200,			Edit:1, EditLen: 400 }
		  , {Header:"상세주소",		 							Type:"Text", 	SaveName:"addr_2_nm", 									Align:"left", 		Width:200,			Edit:1, EditLen: 400 }  
		  ];
		IBS_InitSheet(mySheet, ibdata);
			
		mySheet.SetEllipsis(1);	//말줄임
		mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
		mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
		
	    //--------------------------------   
	    //input enter 막기
	    //--------------------------------
	    $("*").keypress(function(e){
	        if(e.keyCode==13) return false;
	    });
	    //doSearch();
        $('#search').click(function() {
            doSearch();
        });
        $('#create').click(function() {
        	doInsert();
        });
        $('#update').click(function() {
        	doUpdate();
        });
        $('#delete').click(function() {
        	doDelete();
        });
	});  // end of ready
	
	/** ********************************************************
	 * 팝업창 할당
	 ******************************************************** */
		function mySheet_OnDblClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
			if (Row < 1) return;
			
			var colNm = mySheet.GetCellProperty(1, Col, "SaveName");
			if(colNm == 'vendorId'){	//셀링포인트 할당
				var recommSeq	 = mySheet.GetCellValue(Row,'vendorId' );
				popupDetailView(recommSeq);
				
			}
		}

	/** ********************************************************
	 *  할당 팝업창
	 ******************************************************** */
		function popupDetailView(recommSeq) {
			var targetUrl = '<c:url value="/system/selectVendorInfoMgr.do"/>'+'?vendorId='+recommSeq+'&type=1';
			var wSize = 0;
			
			Common.centerPopupWindow(targetUrl, 'vendorInfo', {width : 830, height : 795 ,scrollBars : 'YES'});
		}
	
	/** ********************************************************
	 * alert 창
	 ******************************************************** */
	function mySheet_OnSaveEnd(Code, Msg, StCode, StMsg) {
		alert(Msg);
		doSearch();	
	}

	
	/** ********************************************************
	 * 조회 처리 함수
	 ******************************************************** */
	function doSearch()  {
		goPage('1');
	}
	/** ********************************************************
	 * 조회 처리
	 ******************************************************** */
		function goPage(currentPage)  {
			var url = '<c:url value="/system/selectPartnerList.do"/>';
			
			loadIBSheetData(mySheet, url, currentPage, '#dataForm', null);
		}

	/** ********************************************************
	 * 저장 처리 함수
	 ******************************************************** */	
	 function doUpdate(){
		 var rowCnt = mySheet.RowCount() + 1;
		 var chkCnt = 0;
		 
		// 필수항목 체크
		 for (var i = 1; i < rowCnt; i++) {
			 if(mySheet.GetCellValue(i, "vendorNm") == "") {
					alert( i + "행에 업체명을 입력하세요.");
					return;
				}	 
		 }
		
		 var url =  '<c:url value="/system/updatePartnerList.do"/>';
		 mySheet.DoSave(url, "islndDeliAmtYn=1");
	}
	
	/** ********************************************************
	 * 등록버튼 클릭시 이벤트
	 ******************************************************** */
	function doInsert() {
		var targetUrl = '<c:url value="/system/insertPartnerPopUp.do"/>';
    	Common.centerPopupWindow(targetUrl, 'insert', {width : 600, height : 250});
	}
	
	/** ********************************************************
	 * 삭체 처리 함수
	 ******************************************************** */
	 function doDelete()  {
			if (!confirm('<spring:message code="msg.common.confirm.delete"/>')) {
				return;
			}
			var url = '<c:url value="/system/deletePartnerList.do"/>';
			mySheet.DoSave(url, {  Quest : 0 });
		} 
	 
</script>
</head>

<body>
<form name="dataForm" id="dataForm" >

<div id="content_wrap">
<div class="content_scroll">

<div id="wrap_menu">

    <!-- -------------------------------------------------------- -->
    <!-- 검색조건 -->
    <!-- -------------------------------------------------------- -->
    <div class="wrap_search">   
        <div class="bbs_search">
            
            <!------------------------------------------------------------------ -->
            <!--    title -->
            <!------------------------------------------------------------------ -->
            <ul class="tit">
                <li class="tit">[협력사 관리]</li>
                <li class="btn">
                    <a href="javascript:void(0)" class="btn" id="search"><span><spring:message code="button.common.inquire"/></span></a>
                    <a href="javascript:void(0)" class="btn" id="create"><span><spring:message code="button.common.create" /></span></a>
                    <a href="javascript:void(0)" class="btn" id="update"><span><spring:message code="button.common.save" /></span></a>
                    <a href="javascript:void(0)" class="btn" id="delete"><span><spring:message code="button.common.cancel" /></span></a>
                    
                </li>
            </ul>
            <!-------------------------------------------------- end of title -- -->
        </div><!-- id bbs_search// -->  
    </div><!-- id wrap_search// -->
    <!-----------------------------------------------end of 검색조건 -->
		
    <!-- -------------------------------------------------------- -->
    <!--    검색내역    -->
    <!-- -------------------------------------------------------- -->
    <div class="wrap_con">
        <!-- list -->
        <div class="bbs_list">
            <ul class="tit">
                <li class="tit">협력업체코드</li>
                <li class="tit">
                    <input type="text" name="vendorId" id="vendorId" value=""/>
<!--                      <a href="#" class="btn" id="search" onclick="popupVendorList();"><span>업체조회</span></a> -->
                </li>
                <li class="tit">협력업체명</li>
                <li class="tit">
                    <input type="text" name="vendorNm" id="vendorNm" value=""/>
                </li>
                
            </ul>
             <table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
				<tr>
 					<td><div id="ibsheet1"></div></td>
				</tr>
			</table>
		</div>      
    </div>
    <!-----------------------------------------------end of 검색내역  -->
	
	<!------------------------------------------------------------------ -->
	<!-- 	페이징 -->
	<!------------------------------------------------------------------ -->
	<div id="pagingDiv" class="pagingbox1" style="width: 100%;">
 		<script> setLMPaging("0", "0", "0","goPage","pagingDiv");</script>
 	</div>
 	<!------------------------------------------------------- end of 페이징 -->
	
</div><!-- id wrap_menu// -->
</div><!-- id content_scroll// -->

<!-- -------------------------------------------------------- -->
<!--    footer  -->
<!-- -------------------------------------------------------- -->
<div id="footer">
    <div id="footbox">
        <div class="msg" id="resultMsg"></div>
        <div class="location">
            <ul>
                <li>홈</li>
                <li>시스템관리</li>
                <li class="last">협력사관리</li>
            </ul>
        </div>
    </div>
</div>
<!---------------------------------------------end of footer -->
</div><!-- id content_wrap// -->
</form>
</body>
</html>