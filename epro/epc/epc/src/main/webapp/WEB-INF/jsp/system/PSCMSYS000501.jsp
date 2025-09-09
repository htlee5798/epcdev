<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link type="text/css" rel="stylesheet"
		href="${lfn:getString('system.cdn.static.path')}/css/style_1024.css"></link>
	<script type="text/javascript" src="/js/epc/Ui_common.js"></script>
	<script type="text/javascript" src="/js/epc/common.js"></script>
	<script type="text/javascript" src="/js/wisegrid/WiseGridTag.js"></script>
	<script type="text/javascript" src="/js/epc/paging.js"></script>
	<script type="text/javascript" src="/lottemart-epc/namoCross/js/namo_scripteditor.js"></script>
	<%@ include file="/common/scm/scmCommon.jsp"%>

	<!-- 신규 공통 css 및 js 파일 INCLUDE -->
	<c:import url="/common/commonHead.do" />
	<!-- product/PSCMPRD004101 -->

	<script type="text/javascript">
		$(document).ready(
				function() {
					// START of IBSheet Setting
					createIBSheet2(document.getElementById("ibsheet1"),
							"mySheet", "100%", "180px");
					mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
					

					var ibdata = {};
					// SizeMode: 사이즈 방식, SearchMode: 조회 방식, MergeSheet: 머지 종류 
					ibdata.Cfg = { SizeMode : sizeAuto, SearchMode : smGeneral, MergeSheet : msHeaderOnly }; // 10 row씩 Load
					// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
					ibdata.HeaderMode = { 	Sort : 1, ColMove : 0, ColResize : 1, 	HeaderCheck : 1 };

					// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
					ibdata.Cols = [ { Header : "순번", Type : "Text", 	SaveName : "NUM", Align : "Center", Width : 50, Edit : 0 }
					, { Header : "항목명", Type : "Text", SaveName : "INFOCOLNM", Align : "Center", 	Width : 250, Wrap:1, Edit : 0 }
					, { Header : "항목값", Type : "Text", SaveName : "colVal", Align : "Center", 	Width : 250, EditLen: 666 , Wrap:1, 	Edit : 1 	}
					, { Header : "정보그룹 코드", Type : "Text", 	SaveName : "INFOGRPCD", Align : "Center", Edit : 0, Hidden : true }
					, { Header : "정보컬럼코드", 	Type : "Text", 	SaveName : "INFOCOLCD", 	Align : "Center", Edit : 0, Hidden : true }
					, { Header : "확인", Type : "Status", SaveName : "S_STATUS", Align : "Center", Edit : 0, Hidden : true }
					];

					IBS_InitSheet(mySheet, ibdata);

					mySheet.SetEllipsis(1);	//말줄임
					mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
					mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
					
					DSheet();
				}); // end of ready
				
				/** ********************************************************
				 * 셀링포인트 글자제한
				 ******************************************************** */
				function fnChkByte(obj, maxByte){
					var str = obj.value;
					var str_len = str.length;

					var rbyte = 0;
					var rlen = 0;
					var one_char = "";
					var str2 = "";

					for(var i=0; i<str_len; i++){
					one_char = str.charAt(i);
					if(escape(one_char).length > 4){
						  rbyte += 3;   
					}else{
					    rbyte++;                                            //영문 등 나머지 1Byte
					}

					if(rbyte <= maxByte){
					    rlen = i+1;                                          //return할 문자열 갯수
					}
					}

					if(rbyte > maxByte){
					    alert("한글 "+parseInt(maxByte/3)+"자 / 영문 "+maxByte+"자를 초과 입력할 수 없습니다.");
					    str2 = str.substr(0,rlen);                                  //문자열 자르기
					    obj.value = str2;
					    fnChkByte(obj, maxByte);
					}
					}
				
		/** ********************************************************
		* SHEET 기본
		******************************************************** */
		function DSheet() {
			var url = '<c:url value="/system/selectComBox.do"/>';
			loadIBSheetData(mySheet, url, '1', '#dataForm', null);
		}

		/** ********************************************************
		 * 메세지 창
		 ******************************************************** */
		function mySheet_OnSaveEnd(Code, Msg, StCode, StMsg) {
			alert(Msg);
			opener.doSearch();
			top.close();
		}
		/** ********************************************************
		 * 전상법 템플릿 저장
		 ******************************************************** */
		 function doInsert() {
			 var form = document.dataForm;
			
			 if(confirm("선택항목을 저장하시겠습니까?")){
				 
				 if(form.title.value == ""){
						alert("제목을 입력해주세요.");
						form.title.focus();
						return;
				}else{
			 
					var title = escape(encodeURIComponent($('#title').val()));
					var useYn = $('#useYn').val();
					var infoGrpCd = $('#infoGrpCd').val();
					var url = '<c:url value="/system/insertEscTem.do"/>';
					mySheet.DoAllSave(url, {
						Param:'title='+title+'&useYn='+useYn
									+'&infoGrpCd='+infoGrpCd ,
									Quest:0});
				}	
			}
		
		}

		/** ********************************************************
		 * 조회 함수 처리
		 ******************************************************** */
		function doSearch() {
			goPage('1');
		}

		function goPage(currentPage) {
			var url = '<c:url value="/system/selectComBox.do"/>';
			loadIBSheetData(mySheet, url, currentPage, '#dataForm', null);
		}
		
		
		/**********************************************************
		 * 특수문자 입력 방지 ex) onKeyPress="keyCode(event)"
		 ******************************************************** */
		function keyCode(e)
	    {
	        var code = (window.event) ? event.keyCode : e.which; // IE : FF - Chrome both
	        
	        if (code >  32 && code <  48) keyResult(e);
	        if (code >  57 && code <  65) keyResult(e);
	        if (code >  90 && code <  97) keyResult(e);
	        if (code > 122 && code < 127) keyResult(e);
	    }
		 
		 function keyResult(e)
		 {
		        alert("특수문자를 사용할수 없습니다!");
		        
		        if (navigator.appName != "Netscape") {
		            event.returnValue = false;  //IE - Chrome both
		        }
		        else {
		            e.preventDefault(); //FF - Chrome both
		        }
		  }
		 
	</script>
</head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />

<body>

	<form name="dataForm" id="dataForm">
		<div id="popup">
			<!--  @title  -->
			<div id="p_title1">
				<h1>전상법 템플릿관리</h1>
				<span class="logo"><img
					src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif"
					alt="LOTTE MART" /></span>
			</div>
			<!--  @title  //-->
			<!--  @process  -->
			<div id="process1">
				<ul>
					<li>홈</li>
					<li>시스템관리</li>
					<li class="last">전상법 템플릿관리</li>
				</ul>
			</div>
		</div>
		<!--  @process  //-->
		<div class="popup_contents">
			<!-- -->
			<div class="bbs_search3">
				<ul class="tit">
					<li class="btn">
						<a href="javascript:void(0)" class="btn" onclick="doInsert()"><span><spring:message code="button.common.save" /></span></a>
						<a href="javascript:void(0)" class="btn" onclick="top.close();"><span><spring:message code="button.common.close" /></span></a>
					</li>
				</ul>

				<table class="bbs_grid2" cellpadding="0" cellspacing="0" border="1">
					<colgroup>
						<col width="15%">
						<col width="35%">
						<col width="15%">
						<col width="35%">
					</colgroup>
					<tr>
						<th>제목</th>
						<td colspan="3"><input type="text" id="title" name="title" size="77" onKeyUp="javascript:fnChkByte(this,'100')" onKeyPress="keyCode(event)" /></td>
					</tr>
					<tr>
						<th>상품유형</th>
						<td colspan="1"><select name="infoGrpCd" id="infoGrpCd"
							class="select" onchange="doSearch()" readonly style="width:60%;"> 
								<c:if test="${empty infoGrpList}">  <option value="">상품 없음</option></c:if>
								<c:forEach items="${infoGrpList}" var="code" begin="0">
									<option value="${code.INFO_GRP_CD}"> ${code.INFO_GRP_NM}</option>
								</c:forEach>
						</select></td>
						<th>사용여부</th>
						<td colspan="1">
						<select id="useYn" name="useYn" class="select" style="width:60%;">
								<option value="Y">사용</option>
								<option value="N">미사용</option>
						</select></td>
					</tr>
				</table>
				<br/>
				<!-- IBSHEET  -->
				<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td><div id="ibsheet1"></div></td>
						<!-- 페이징 -->
						</div>
					</tr>
				</table>
	</form>
	<iframe name="save" id="save" src="/html/epc/blank.html "
		style="display: none;"></iframe>
</body>

</html>