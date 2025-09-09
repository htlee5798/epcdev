<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LOTTE MART Back Office System</title>
<%-- <link type="text/css" rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/style_1024.css" ></link> --%>
<!-- <script type="text/javascript" src="/js/epc/Ui_common.js" ></script> -->
<!-- <script type="text/javascript" src="/js/epc/common.js"></script> -->
<!-- <script type="text/javascript" src="/js/wisegrid/WiseGridTag.js" ></script> -->
<!-- <script type="text/javascript" src="/js/epc/paging.js" ></script> -->

<!-- 신규 공통 css 및 js 파일 INCLUDE -->
<c:import url="/common/commonHead.do" />

<script language="javascript" type="text/javascript">
var prodCdList = new  Array(); 
var currSellPrcList = new  Array(); 

$(document).ready(function(){
	var obj = new Object();
	
// 	$('#con').hide();
// 	$('#filefrm').hide();
	
	$('#createExcelForm').click(function () {
		if (!confirm('엑셀 양식을 다운로드하시겠습니까?')) {
			return;
		}
		excelFormDown(mySheet, 'Test1_양식', 2, null);
	});
		
	//찾아보기
	$('#findFile').click(function() {
		$('#file').click();
		msgPath = "파일이 업로드 되었습니다 업로드 버튼을 눌러주세요";
		if( $('#file').val().length > 0 ){ // 파일 선택 시
			$("#excelOp").val(msgPath);
		}else{
			$("#excelOp").val("");
		}
    });
	
	// 엑셀파일 업로드 (서버 통신 필요)
	$('#uploadExcel').click(function () {
		if( $("#file").val()==""){
			alert('업로드할 파일이 없습니다. 먼저 파일을 선택해 주세요.');
			return;
		}
		
		$("#filefrm").attr("src","load");
		$("#bosform").attr("enctype","multipart/form-data");
		document.bosform.action = '<c:url value="/excelLoad/IBSheetExcelLoad.do"/>';
		document.bosform.target = "filefrm";
		document.bosform.submit();
		$("#bosform").attr("enctype","");
	});
	
	$('#filefrm').load( function(){
		if( $("#filefrm").attr("src") != ""){
			
			obj.prodCdList = prodCdList;
			obj.currSellPrcList = currSellPrcList;
			opener.setProdArray(obj);
			self.close();
		}
	});
	
	
	// START of IBSheet Setting
	createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "30px");
	mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
	
	var ibdata = {};
	// SizeMode: 
	ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral}; // 10 row씩 Load
	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.Cols = [
	    {Header:"인터넷상품코드",  Type:"Text", 		SaveName:"PROD_CD",               Align:"Center", Width:100,  Edit:0, Hidden:1}
	  , {Header:"변경판매가", 	    Type:"Text", 		SaveName:"CURR_SELL_PRC",      Align:"Center", Width:80,  Edit:0, Hidden:1}
	];
	
	IBS_InitSheet(mySheet, ibdata);
		
	mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
	mySheet.FitColWidth();
	mySheet.SetComboOpenMode(1);
	
	$('#con').hide();
});

</script>

<script type="text/javascript">

// function callAjaxByForm(form, url, target, Type) {

// 	var formQueryString = $('*', form).fieldSerialize();
	
// 	$.ajax({
// 		type: Type,
// 		url: url,
// 		data: formQueryString,
// 		success: function(json) {
// 			try {
// 				if(jQuery.trim(json) == ""){//처리성공
// 					alert('<spring:message code="msg.common.success.request"/>');
// 					//top.close();
// 					opener.doSearch();
// 				}else{
// 					alert(jQuery.trim(json));
// 				}
// 			} catch (e) {}
// 		},
// 		error: function(e) {
// 			alert(e);
// 		}
// 	});
// }	

</script>



<script type="text/javascript">
	
	//exeExcelFile 양식다운
// 	function exeExcelFile(){
		
// 		var gridObj = document.WG1;
// 		var excelName = "상품리스트";
// 		var bosform = document.dataForm;
		
// 		if (!confirm('엑셀 양식을 다운로드하시겠습니까?')) {
// 			return;
// 		}
// 		gridObj.RemoveAllData();
// 		gridObj.strDefaultExportFileName=excelName;
// 		gridObj.ClearExcelInfo();
		
// 		gridObj.ExcelExport('', '', true, true);		

// 		if (!confirm('엑셀 양식을 다운로드하시겠습니까?')) {
// 			return;
// 		}
// 		excelFormDown(mySheet, 'TEST_양식', 1);
		
// 	}
	
	//찾아보기
// 	function excelImport() {	
		
// 		var gridObj = gridObj = document.WG1;
		
// 		var bosform = document.dataForm;
// 		var strPath = "";
// 		var msgPath = "파일이 추가 되었습니다. 추가 버튼을 눌러주세요";
		
// 		//파일 필터
// // 		gridObj.strDefaultImportFileFilter = 'xls';
		
// 		gridObj.RemoveAllData();
// 		$("#excelInsert").attr('disabled', true);
// 		gridObj.excelImport(strPath , 'importall', 'row', true, true);

// 		if(gridObj.GetRowCount() == 0){
// 			bosform.excelOp.value ='';
// 		}else if(gridObj.GetRowCount() > 50){
// 			alert("상품수 50개 제한입니다.");
// 			bosform.excelOp.value ='';
// 		}else{
// 			bosform.excelOp.value = msgPath;
// 			$("#excelInsert").attr('disabled', false);
// 			$("#excelInsert").focus();
// 		}
// 	}
	
	
	//엑셀 일괄등록  엑셀 업로드
// 	function doExcelInsert(){
		
// 		var gridObj = document.WG1;
// 		var bosform = document.dataForm;
// 		var bosformExcel = null;
// 		var prodArray = "";
		
// 		if(gridObj.GetRowCount() == 0){
// 			alert('엑셀 자료가 잘못되었습니다');
// 			bosform.excelOp.value = '';
// 			$("#excelInsert").attr('disabled', true);
// 			return;
// 		}
		
// 		for(var i=0; i<gridObj.GetRowCount(); i++){
// 			var gridColB = gridObj.GetCellValue("PROD_CD", i);
// 			var gridColC = gridObj.GetCellValue("CURR_SELL_PRC", i);
			
// 			if(gridColB == '' || gridColB == null || gridColB.length < 13){
// 				alert(i+1 + "번째 상품코드가 잘못 되었습니다.");
// 				return;
// 			}

// 			prodArray = prodArray + gridColB +":"+ gridColC + ",";
// 		}		
		
// 		if (!confirm('엑셀 파일을 추가 하시겠습니까?')) {
// 			return;
// 		}
		
// 		opener.setProdArray(prodArray);
// 		top.close();
// 	}
	
	//저장후 호출되는 이벤트
	function mySheet_OnSaveEnd(code, msg) {
		
	    if(code >= 0) {
	        alert(msg);  // 저장 성공 메시지
	    } else {
	        alert(msg); // 저장 실패 메시지
	    }
	}
	
	function setData( PROD_CD, CURR_SELL_PRC ){
// 		alert(PROD_CD+"   =   "+PROD_CD.length);
// 		var rowIdx = mySheet.DataInsert(0);
// 		mySheet.SetCellValue(rowIdx, 'PROD_CD', PROD_CD);
// 		mySheet.SetCellValue(rowIdx, 'CURR_SELL_PRC', CURR_SELL_PRC);
// 		$("#pagingDivCnt em").text(rowIdx);
		
		prodCdList.push( PROD_CD );
		currSellPrcList.push( CURR_SELL_PRC );

		
		
		
// 		var cnt = 1;
// 		var obj = new Object();
// 		var prodCdList = new Array();	
// 		var cullSellPrcList = new Array();
		
// 		prodCdList.push(PROD_CD);
// 		cullSellPrcList.push(CURR_SELL_PRC);
		
// 		obj.prodCdList = prodCdList;
// 		obj.cullSellPrcList = cullSellPrcList;
// 		opener.setProdArray(obj);
		

		
		
	}
	//데이터 읽은 직후 이벤트
	function mySheet_OnSearchEnd(cd,msg) {

		
	}

	
</script>

</head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
<form name="bosform" id="bosform" method="post" enctype="multipart/form-data">

  <div id="popup">
    <!--  @title  -->
     <div id="p_title1">
		<h1>상품리스트</h1>
        <span class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
     </div>
     <!--  @title  //-->
	 
	 <div class="popup_contents">
		<!--  @작성양식 2 -->
		<tr style="height: 6px;">&nbsp;</tr>
		
		<!-----------------  대상할당 START ----------------------------------------->
		<div class="bbs_search3">
			<ul class="tit">
				<li class="tit">상품등록</li>
				<li class="btn">
					<a href="#" class="btn" onclick="self.close();"><span>닫기</span></a>
				</li>
			</ul>
			<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">				
				<colgroup>
					<col width="0%">
					<col width="100%">
				</colgroup>
				<tr>
					<th class="fst">&nbsp;</th>
					<td>
						<input id="excelOp" name="excelOp" type="text" maxlength="50" class="day" style="width:65%;" />
						<input type="file" name="file" id="file"  class="text" style="display:none;"  value=""  />
						<input type="hidden" name="colNms" size="35" value="PROD_CD^CURR_SELL_PRC" /><!-- IBSheet 칼럼명 (구분자 ^, 필수 파라미터) -->
						<input type="hidden" name="func" value="setData" /><!-- 실행 자바스크립트 명 ( 필수 파리미터) -->
						<input type="hidden" name="sheetNm" value="mySheet" /><!-- IBSheet 명 (필수 파라미터)-->
						<input type="hidden" name="sheetRemoveAll" value="Y" />
						<input type="hidden" name="hdRow" value="1" />
<%-- 			          	<authutl:btnAuth buttonAuth="00002" admAuth="${ buttonAuth.adminMenuAuth}" buttonTagStart="<span class='p_btn'><span class='btnBG1'><a href='#' class='btn' id='createExcelForm'>"  buttonMsg=""  buttonTagEnd="양식</a></span></span>" applyYn="1"/> --%>
<%-- 			    		<authutl:btnAuth buttonAuth="00002" admAuth="${ buttonAuth.adminMenuAuth}" buttonTagStart="<span class='p_btn'><span class='btnBG1'><a href='#' class='btn' id='findFile'>"  buttonMsg=""  buttonTagEnd="찾아보기</a></span></span>" applyYn="1"/> --%>
<%-- 			    		<authutl:btnAuth buttonAuth="00002" admAuth="${ buttonAuth.adminMenuAuth}" buttonTagStart="<span class='p_btn'><span class='btnBG1'><a href='#' class='btn' id='uploadExcel'>"  buttonMsg=""  buttonTagEnd="업로드</a></span></span>" applyYn="1"/> --%>
						<a href="#" class="btn" id="createExcelForm"><span>양식</span></a>
						<a href="#" class="btn" id="findFile"><span>찾아보기</span></a>
						<a href="#" class="btn" id="uploadExcel" ><span>추가</span></a>
<!-- 						<a href="#" id="excelInsert" class="btn"><span>추가</span></a> -->
						
					</td>
				</tr>
				<tr>
					<td colspan="2" style="">
					<span class="star">* 최대상품 50개 제한입니다. </span> 
					</td>	
				</tr>
			</table>
	
			<div class="wrap_con" >
				<div class="bbs_list">
					<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
<!-- 							<td><script type="text/javascript"> initWiseGrid("WG1", "0%", "0");</script></td> -->
							<td id="con"><div id="ibsheet1" ></div></td>
						</tr>
					</table>
				</div>
				
			</div>
		</div>
		<!------------------------------  대상할당 END ------------------------------------->
	</div>
  </div>
  <iframe id="filefrm" name="filefrm" src=""  style="display:none;"></iframe>
</form>  
</body>
</html>