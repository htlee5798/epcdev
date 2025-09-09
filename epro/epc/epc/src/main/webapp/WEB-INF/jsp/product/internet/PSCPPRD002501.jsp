<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@page import="lcn.module.common.util.DateUtil"%>
<%@page import="com.lottemart.epc.common.util.SecureUtil"%>
<% 
	String prodCd = SecureUtil.stripXSS(request.getParameter("prodCd"));
	String tabNo = "15";
    String vendorId = SecureUtil.stripXSS(request.getParameter("vendorId"));
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- PSCPPRD002501 -->
<head>
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />

<script type="text/javascript">
	$(document).ready(function(){
		// START of IBSheet Setting
		createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "120px");
		mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
		
		var ibdata = {};
		// SizeMode: 
		ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral,MergeSheet:msHeaderOnly}; // 10 row씩 Load
		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.Cols = [
		    {Header:"", 			    		         Type:"CheckBox", 	SaveName:"CHK", 		        		 	 Align:"Center", Width:33,  Sort:false}
		  , {Header:"인터넷상품코드", 	     Type:"Text", 			SaveName:"PROD_CD", 	      	     	 Align:"Center", Width:80,  Edit:0}
		  , {Header:"상품코드", 	                 Type:"Text", 		    SaveName:"MD_PROD_CD", 	         Align:"Center", Width:80,Edit:0} 
		  , {Header:"판매코드",                   Type:"Text", 				SaveName:"MD_SRCMK_CD",     	Align:"Center", Width:120	,  Edit:0}
		  , {Header:"대분류", 	                     Type:"Text", 			SaveName:"L1_NM", 	  				 	 Align:"Center", Width:80	,  Edit:0}
		  , {Header:"중분류", 	                     Type:"Text", 			SaveName:"L2_NM",    				 	 Align:"Center", Width:80,  Edit:0}
		  , {Header:"상품명", 			             Type:"Text", 			SaveName:"PROD_NM",  	             Align:"Center", Width:100,  Edit:0}
		  , {Header:"키워드", 	                     Type:"Text", 			SaveName:"SEARCH_KYWRD",       Align:"Center", 	   BackColor:"#A4A4A4",Width:400,Edit:1}
		  , {Header:"등록된 키워드수", 		 Type:"Text", 			SaveName:"SEQ_CNT",     				 Align:"Center",  	Width:75, 	Edit:0}
		  , {Header:"Status",						 Type:"Status" ,			SaveName:"Status", 		   					 Align:"Center",		Width:50, 	Edit:0 ,Hidden: true}
		];
		IBS_InitSheet(mySheet, ibdata);
			
		mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
		mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
		mySheet.FitColWidth();
		mySheet.SetComboOpenMode(1);

		doSearch(); //자동 검색
	});// end of ready
	
	/** ********************************************************
	 * 저장완료후 발생하는 이벤트(삭제 메세지 후 IBSheet 데이터 조회)
	 ******************************************************** */
	function mySheet_OnSaveEnd(code, Msg) {
		alert(Msg);
		goPage(1);
	}
	
	/** ********************************************************
	 * 자동 체크 이벤트
	 ******************************************************** */
	 function mySheet_OnChange(Row, Col, Value, OldValue, RaiseFlag) {
		 if(Col!=7){
				return;
		 }
			var colNm = Value;
			var T1 = colNm.indexOf(','); //콤마
			var T2 = colNm.indexOf('|'); //백스페이스
			var T3 = colNm.indexOf('ㅣ'); //한글 이
			if(T1>-1 || T2>-1  || T3>-1){	
				alert('구분자는 ;(세미콜론)만 가능합니다.');
				mySheet.SetCellValue(Row, Col, '');
				return;
			}
			if(mySheet.GetCellValue(Row,'SEARCH_KYWRD') != '')
			mySheet.SetCellValue(Row, "CHK", "1");
	}
	
	/** ********************************************************
	 * 키워드관리 정보 수정 (키워드 반영)
	 ******************************************************** */
	function doUpdate() {		
		if(  mySheet.CheckedRows("CHK") == 0){
			alert("선택된 로우가 없습니다.");
			return;
		}
		
		if (!confirm('<spring:message code="msg.common.confirm.update"/>')) {
			return;
		}
		
		//20180911 상품키워드 입력 기능 추가
        var aprvCd = '<c:out value="${prdMdAprInfo.aprvCd}" />';

    	if(aprvCd == "001") {
    		alert('현재 수정한 상품은 상품정보수정 승인요청 중인 상품입니다. 수정 값은 승인 완료 후 반영됩니다.');
    	} else if(aprvCd == "002") {
    		alert('현재 수정한 상품은 상품정보수정요청 중 반려된 상품으로 수정 후 아래의 경로에서 <재요청> 이 필요합니다.\n\n([SCM]상품정보관리→상품정보수정요청→상세키워드정리스트→목록 중 [보기] 항목 선택→<재요청> 신청)');
    	} else {
    		alert('수정 값은 관리자 승인 완료 후 반영됩니다.');
    	}
		//20180911 상품키워드 입력 기능 추가
		
		var url = '<c:url value="/product/keywordUpdate.do"/>';
		
		mySheet.DoSave( url, {Col:1, Sync:2, Quest:0});
		
	}
	
	/** ********************************************************
	 * 반영된 키워드 삭제
	 ******************************************************** */
	function doDelete() {
		if(  mySheet.CheckedRows("CHK") == 0){
			alert("선택된 로우가 없습니다.");
			return;
		}
		if (!confirm('<spring:message code="msg.common.confirm.delete"/>')) {
			return;
		}	

		var url = '<c:url value="/product/keywordDelete.do"/>';
		
		mySheet.DoSave( url, {Quest:0,Col:1});
		
	}
	
	/** ********************************************************
	 * 키워드관리 정보 조회
	 ******************************************************** */
	function doSearch() {
		goPage('1');
	}
	
	function goPage(currentPage){

		var param = new Object();
		//상품 코드
		param.prodCd					= $('#prodCd').val();
		var url = '<c:url value="/product/selectKeywordSearch.do"/>';
		loadIBSheetData(mySheet, url, currentPage,  null, param);
		
	}	
</script>
</head>
<body>

<form name="dataForm" id="dataForm">
<input type="hidden" id="strCd" name="strCd">
<input type="hidden" id="prodCd" name="prodCd" value="${prodCd}" />

<div id="content_wrap" style="width:990px; height:200px;">

    <div id="wrap_menu" style="width:990px;">
        <!--    @ 검색조건  -->

        <!-- 상품 상세보기 하단 탭 -->
        <c:set var="tabNo" value="<%=tabNo%>" />
        <c:import url="/common/productDetailTab.do" charEncoding="euc-kr" >
			<c:param name="tabNo" value="${tabNo}" />
			<c:param name="prodCd" value="${param.prodCd}" />
		</c:import>

        <div class="wrap_con">
            <!-- list -->
            <div class="bbs_list">
                <ul class="tit">
                    <li class="tit">키워드관리<font color="red"><strong> (키워드 입력시 구분자 ;(세미콜론)만 가능합니다. 기타 구분자 사용시 잘못된 키워드로 검색될 수 있습니다)</strong></font>
                    </li>

                    <li class="btn">
                    	<!-- 20180911 상품키워드 입력 기능 추가 (상품키워드 필수 입력 변경으로 전체 삭제 기능 제거) -->
                    	<!--
                        <a href="javascript:doDelete();" class="btn" ><span>삭제</span></a>
						-->
						<!-- 20180911 상품키워드 입력 기능 추가 (상품키워드 필수 입력 변경으로 전체 삭제 기능 제거) -->
						<a href="javascript:doUpdate();" class="btn" ><span>수정</span></a>
                    </li>
                </ul>

                <table cellpadding="0" cellspacing="0" border="0" width="100%" heigth="200px">
                    <tr>
                        <td><div id="ibsheet1"></div></td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>
</form>

<form name="form1" id="form1">
<input type="hidden" id="prodCd" name="prodCd" value="<%=prodCd%>" />
<input type="hidden" id="vendorId" name="vendorId" value="<%=vendorId%>"/>
</form>

</body>
</html>