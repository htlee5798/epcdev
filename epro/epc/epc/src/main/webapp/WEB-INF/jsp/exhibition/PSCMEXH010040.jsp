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

<c:import url="/common/commonHead.do" />

<%@ include file="/common/scm/scmCommon.jsp" %>

<link type="text/css" rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/style_1024.css" ></link>
<script type="text/javascript" src="/js/epc/Ui_common.js" ></script>
<script type="text/javascript" src="/js/epc/common.js"></script>
<script type="text/javascript" src="/js/wisegrid/WiseGridTag.js" ></script>
<script type="text/javascript" src="/js/epc/paging.js" ></script>

<!-- 신규 공통 css 및 js 파일 INCLUDE -->

<!-- PSCMPRD0040 -->



<script type="text/javascript" >
$(document).ready(function(){
	
	// 등록버튼 클릭
// 	$('#create').click(function() {
// 		doCreate();	
// 	});
	
	// 수정버튼 클릭
// 	$('#edite').click(function() {
// 		doEdite();	
// 	});
	
	
	// START of IBSheet Setting
	createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "330px");
	mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
	mySheet.SetDataAutoTrim(true);
	
	var ibdata = {};
	// SizeMode: 사이즈 방식, SearchMode: 조회 방식, MergeSheet: 머지 종류 
	ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral, MergeSheet:msHeaderOnly}; // 10 row씩 Load
	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.Cols = [
	    {Header:"순번",						Type:"Int" ,					SaveName:"SEQ", 		    			Align:"Center",		Width:45, 		Edit:0}
	  ,	{Header:"",								Type:"CheckBox" ,		SaveName:"CHK",						Align:"Center", 		Width:45,  		Edit:1,  Sort:false}
	  , {Header:"판매코드",				Type:"Text" ,				SaveName:"PROD_CD", 		    	Align:"Center",		Width:90, 		Edit:0,  KeyField : 1} 
	  , {Header:"상품명", 	    			Type:"Text", 				SaveName:"PROD_NM",     			Align:"Left",			Width:160, 	Edit:0, Cursor:'pointer', FontColor:"#0000FF", FontUnderline:true}
	  , {Header:"원가", 	        			Type:"Int", 					SaveName:"BUY_PRC",   				Align:"Right", 	 		Width:60, 		Edit:0, Format:"#,###,###"}
	  , {Header:"매가", 						Type:"Int", 					SaveName:"SELL_PRC", 				Align:"Right", 	  		Width:60, 		Edit:0, Format:"#,###,###"}
	  , {Header:"판매가", 					Type:"Int", 					SaveName:"CURR_SELL_PRC",		Align:"Right",   		Width:60, 		Edit:0, Format:"#,###,###"}
	  , {Header:"품절여부", 				Type:"Combo", 			SaveName:"SOUT_YN",				Align:"Center",   		Width:70, 		Edit:0, ComboCode:"Y|N", ComboText:"품절|판매중"}
	  , {Header:"제조사", 					Type:"Text", 				SaveName:"VENDOR_NM",   		Align:"Center", 		Width:100, 	Edit:0}
	  , {Header:"전시여부", 				Type:"Combo", 			SaveName:"DISP_YN",  				Align:"Center",		Width:70, 		Edit:1, ComboCode:"Y|N", ComboText:"전시|미전시"}
	  , {Header:"전시순서",				Type:"Int" ,					SaveName:"DISP_SEQ", 		    	Align:"Center",		Width:70, 		Edit:1}
	  , {Header:"이미지", 					Type:"Button", 			SaveName:"IMG_PATH_BTN",  		Align:"Center", 		Width:70, 		Edit:0, Text:"Image"}
	  , {Header:"CATEGORY_ID",        Type:"Text", 	        	SaveName:"CATEGORY_ID",      	        	Hidden:true }
      , {Header:"MKDP_SEQ",           	Type:"Text", 				SaveName:"MKDP_SEQ",      	        		Hidden:true }       
      , {Header:"CONTENTS_SEQ",     Type:"Text", 				SaveName:"CONTENTS_SEQ",      	        Hidden:true }
      , {Header:"DIVN_SEQ",       		Type:"Text", 				SaveName:"DIVN_SEQ",  			Align:"Center", 		Width:70, 		Edit:0,Hidden:true}
	  , {Header:"IMG_PATH", 				Type:"Text", 				SaveName:"IMG_PATH",  			Align:"Center", 		Width:70, 		Edit:0,Hidden:true}
	  , {Header:"IMG250", 					Type:"Text", 				SaveName:"IMG250",  				Align:"Center", 		Width:70, 		Edit:0,Hidden:true}
	  , {Header:"MD_SRCMK_CD", 		Type:"Text", 				SaveName:"MD_SRCMK_CD",  	Align:"Center", 		Width:70, 		Edit:0,Hidden:true}
	  , {Header:"상태",						Type:"Status" ,			SaveName:"S_STATUS", 				Align:"Center",		Width:100, 	Edit:0,Hidden:true}
	];
	
	IBS_InitSheet(mySheet, ibdata);
	mySheet.ShowFilterRow();
	
	mySheet.SetComboOpenMode(1);
	mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
	mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
	mySheet.FitColWidth();
	
	fn_search();
	
	$("#divnSeq").change(function(){
		$("#dispYnView").text( $("#divnSeq option:selected").attr("class") );
		mySheet.SetFilterValue("DIVN_SEQ", $("#divnSeq option:selected").val(), 1);
		$("#rowTotalCount").text(mySheet.FilteredRowCount());

		
	});
	
	
}); // end of ready


//조회
function fn_search() {
// 	var divnSeq         = $("#divnSeq").val();
// 	var prodOrdSetupVal = $("#prodOrdSetupVal").val();
	
	var sUrl = '<c:url value="/exhibition/selectExhibitionProdInfoList.do"/>';		
	loadIBSheetData(mySheet, sUrl, null, '#dataForm', null);
	
}

	
	/* 상품추가 */
    function productAdd(){
		
		if($("#divnSeq").val() == "" || $("#divnSeq").val() == null ){
			alert("구분자가 없습니다.");
			return;
		}
		
		var notInVal = "";
		
    	var targetUrl = '<c:url value="/common/viewPopupProductList2.do"/>?vendorId='+$("#vendorId").val();//01:상품
   			
   		Common.centerPopupWindow(targetUrl, 'prd', {width : 750, height : 500});	
    }
 
function popupReturn(rtnVal){
   	for(var i=0; i<rtnVal.prodCdArr.length; i++){
//    		var rowIdx = mySheet.DataInsert(0);
   		var rowIdx = mySheet.DataInsert(mySheet.FilteredRowCount()+2);
//    		var rowIdx = mySheet.DataInsert(mySheet.FilteredRowCount());
   		var nRow = mySheet.FilteredRowCount()-1;
   		
       	mySheet.SetCellValue(rowIdx, "SEQ", nRow+1);
       	mySheet.SetCellValue(rowIdx, "CHK", "1");
       	mySheet.SetCellValue(rowIdx, "PROD_CD", rtnVal.prodCdArr[i]);
       	mySheet.SetCellValue(rowIdx, "PROD_NM", rtnVal.prodNmArr[i]);
       	mySheet.SetCellValue(rowIdx, "BUY_PRC", rtnVal.buyPrcArr[i]);
       	mySheet.SetCellValue(rowIdx, "SELL_PRC", rtnVal.sellPrcArr[i]);
       	mySheet.SetCellValue(rowIdx, "CURR_SELL_PRC", rtnVal.currSellPrcArr[i]);
       	mySheet.SetCellValue(rowIdx, "SELL_FLAG", rtnVal.sellFlagArr[i]);
       	mySheet.SetCellValue(rowIdx, "STOCK_QTY", rtnVal.stockQtyArr[i]);
       	mySheet.SetCellValue(rowIdx, "DISP_YN", rtnVal.dispYnArr[i]);
       	mySheet.SetCellValue(rowIdx, "VENDOR_NM", rtnVal.venDorNmArr[i]);
       	
       	mySheet.SetCellValue(rowIdx, "DIVN_SEQ", $("#divnSeq option:selected").val());
       	
   	}
}

// function viewImg(url, viewSize, size, nameSize, rowCnt, mdSrcmkCd) {
function viewImg(url, mdSrcmkCd) {
	var targetUrl = '<c:url value="/exhibition/prdImageDetailForm.do"/>?mdSrcmkCd='+mdSrcmkCd+'&url='+url;
	Common.centerPopupWindow(targetUrl, 'prdPrice', {width : 800, height : 800});
}


// 승인요청
function fn_aprv() {
	var f = document.dataForm;
	
	var aprvStsCdChk =  $("#aprvStsCdChk").val();
	 
	
	if($("#dispYnChk").val()=="Y" && aprvStsCdChk !="00"){
		alert("전시상태에서는 승인요청을  할 수 없습니다.")
		//alert(aprvStsCdChk);
		return;
	}	
	if(confirm("승인요청 하시겠습니까?")){
		
		var tranCount = mySheet.RowCount("I") + mySheet.RowCount("U");
		if(tranCount > 0){
			alert("저장을 완료하고 승인요청을 해주십시요.");
			return;
		}
		
		var mysheetCnt = mySheet.GetTotalRows();
		if(mysheetCnt <= 0){
			alert("상품이 최소 1개는 있어야 승인요청이 가능합니다.");
			return;
		}
	
// 		var sUrl = '<c:url value="/exhibition/updateAprv.do"/>';
// 		var categoryId  = "${categoryId }";
// 		var mkdpSeq     = "${mkdpSeq }";
// 		var vendorId     = "${vendorId }";
		
// 		sUrl += "?categoryId="         + categoryId;
// 		sUrl += "&mkdpSeq="      + mkdpSeq;
// 		sUrl += "&vendorId="      + vendorId;

		var formQueryString = $('*', '#dataForm').fieldSerialize();			
		//Ajax 저장처리
		$.ajax({
			type: 'POST',
			url: '<c:url value="/exhibition/updateAprv.do"/>',
			data: formQueryString,
			success: function(json) {
				try {
					alert(json.Result.Message);
					$("#aprvBtn").hide();
					fn_search();
					//if(json.resultCode > 0){
					//}						
					
				} catch (e) {}
			},
			error: function(e) {
				alert(e);
			}
		});
		
// 		mySheet.DoSave(sUrl, {Quest:false, col:1});
	}
}

function mySheet_OnChange(Row, Col, Value) {
	
	 if(mySheet.GetCellValue(Row,'DISP_YN') != '' ||  mySheet.GetCellValue(Row,'DISP_SEQ') != '')
		mySheet.SetCellValue(Row, "CHK", "1");
}
//저장
function fn_save() {
	
    var aprvStsCdChk =  $("#aprvStsCdChk").val();
    
    if(  mySheet.CheckedRows("CHK") == 0){
		alert("선택된 로우가 없습니다.");
		return;
	}
	 
	if($("#dispYnChk").val()=="Y" && aprvStsCdChk !="00"){
		alert("전시상태에서는 기획전을 수정할 수 없습니다.")
		return;
	}	
	var f = document.dataForm;
	
	$('input:radio[name="aprvStsCd"]:input[value="00"]').attr("checked", true);	
 	
 	var rowChk = mySheet.ColValueDup("2");
 	
 	if(rowChk > 0 ){
 		alert(rowChk-1+" 번째 판매코드가 중복되었습니다.");
 		return;
 	}
 	
 	//  트랜잭션 갯수 체크
 	//var checkCnt = mySheet.CheckedRows("CHK"); 	
	var tranCount = mySheet.RowCount("I") + mySheet.RowCount("U");

	if(confirm("저장하시겠습니까?")){
		var sUrl = '<c:url value="/exhibition/insertExhibitionProdInfo.do"/>';

		var categoryId  = "${categoryId }";
		var mkdpSeq     = "${mkdpSeq }";
		var vendorId     = "${vendorId }";
		
		sUrl += "?categoryId="         + categoryId;
		sUrl += "&mkdpSeq="      + mkdpSeq;
		sUrl += "&vendorId="      + vendorId;

		
		//수정건이 존재시 IbSheet기준으로 트랜잭션 날림
		if(tranCount>0){
			mySheet.DoSave(sUrl, {Quest:false, col:1});
			
		//수정건이 미존재시 AJAX로 처리	
		}else{
			//상품정렬방식 처리
			var formQueryString = $('*', '#dataForm').fieldSerialize();			
			//Ajax 저장처리
			$.ajax({
				type: 'POST',
				//url: '<c:url value="/exhibition/insertExhibitionProdSortInfo.do"/>',
				url: '<c:url value="/exhibition/insertExhibitionProdInfo.do"/>',
				data: formQueryString,
				success: function(json) {
					try {
						alert(json.Result.Message);
						//if(json.resultCode > 0){
						//}						
						
					} catch (e) {}
				},
				error: function(e) {
					alert(e);
				}
			});
		}
		
	}

}


//셀 클릭 시...
function mySheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
	if (Row < 1) return;
	
	if(mySheet.ColSaveName(Col) == 'IMG_PATH_BTN'){
		
// 		var url = mySheet.GetCellValue(Row, "IMG250");
		var imgDir = $("#imgDir").val();
		var mdSrcmkCd = mySheet.GetCellValue(Row, "MD_SRCMK_CD");
		var url 	= imgDir + "/" + mdSrcmkCd.substring(0, 5) + "/"+mdSrcmkCd+"_1_250.jpg";

		viewImg(url, mdSrcmkCd);
		
		
	}
}

	
//데이터 읽은 직후 이벤트
function mySheet_OnSearchEnd(cd,msg) {
	$("#regProdCnt").val(RETURN_IBS_OBJ.regProdCnt);
	$("#soutYnCnt").val(RETURN_IBS_OBJ.soutYnCnt);
	$("#divnSeq option:eq(0)").attr("selected", "selected");
	$("#divnSeq").trigger("change");
	
	
}

//저장후 재조회
//Code: 서버에서 저장한 코드, Msg: 서버에서 저장한 메세지, StCode: Http통신코드(200=정상), StMsg:HTTP메세지(OK)
function mySheet_OnSaveEnd(Code, Msg, StCode, StMsg) {
	alert(Msg);
	if (Code == 1) {		
		fn_search();
	}
}

function mySheet_OnSmartResize(Width, Height) {
	//변경된 정보에 따라 컬럼들의 너비를 재조정한다.
	mySheet.FitColWidth();
}
</script>
</head>

<body>

<div id="content_wrap">

<div class="content_scroll">

<form name="dataForm" id="dataForm">
<input type="hidden" id="categoryId" name="categoryId" value="<c:out value="${categoryId}"/>" >
<%-- <input type="hidden" id="strCd"      name="strCd"      value="<c:out value="${strCd }"/>" > --%>
<input type="hidden" id="mkdpSeq"    name="mkdpSeq"    value="<c:out value="${mkdpSeq }"/>" >
<input type="hidden" id="divnSeqSearch"    name="divnSeqSearch"    value="${value.CODE_YN }" >  
<input type="hidden" id="vendorId"    name="vendorId"    value="<c:out value="${vendorId }"/>" >
<input type="hidden" id="aprvStsCdChk" name="aprvStsCdChk" value="<c:out value="${aprvStsCdChk }"/>"">
<input type="hidden" id="imgDir" name="imgDir" value="<c:out value="${imgDir }"/>"">
<input type="hidden" id="dispYnChk" name="dispYnChk" value="<c:out value="${dispYn }"/>"">


<div id="wrap_menu">
<input type="hidden" name="regId" id="regId" value="${epcLoginVO}">
	<!-- 조회조건 -->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit">기획전 상품정보</li>
						<li class="btn">
						<%-- <c:if test="${aprvStsCdChk  eq '00' || aprvStsCdChk  eq '02'}"> --%>
						<c:if test="${aprvStsCdChk  eq '00' || aprvStsCdChk  eq '02'||aprvStsCdChk  eq '03' }">
							<li class="btn" id="aprvBtn"> <a href="#" class="btn" onclick="javascript:fn_aprv();"  ><span>승인요청</span></a></li>
						</c:if>
						</li>
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
						<colgroup>
							<col style="width:20%" />
							<col style="width:30%" />
							<col style="width:20%" />
							<col style="width:30%" />
						</colgroup>
						<tr>
							<th class="fst">
								기획전내 등록된 총 상품수
							</th>
							<td>
								<input type="text" name="regProdCnt" id="regProdCnt"  style="width: 95%" title="기획전내 등록된 총 상품수"  readonly>
							</td>
							<th class="fst">
								기획전내 품절된 총 상품수
							</th>
							<td>
								<input type="text" name="soutYnCnt" id="soutYnCnt"  style="width: 95%" title="기획전내 품절된 총 상품수" readonly>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<br/>
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit">구분자 정보</li>
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
						<colgroup>
							<col style="width:60%" />
							<col style="width:20%" />
							<col style="width:20%" />
						</colgroup>
						<tr>							
		                    <td>
		                        <select name="divnSeq" id="divnSeq"  style="width: 80%">
									<c:forEach items="${divnSeqList }" var="value">
			                      	<option value="${value.CODE }" class="${value.CODE_YN }" >${value.NAME }</option>       
			                      	</c:forEach>                                                                   
								</select>
		                    </td>
							<th class="fst">
								전시여부
							</th>
							<td id="dispYnView">
								
							</td>
						</tr>
					</table>
				</div>
			</div>
			<!-- 조회조건 // -->
			<br/>
			<li class="p_total" > [총건수 <em id="rowTotalCount" style="color: red">0</em> 건] 				
        </li>
		
			<!-- 조회결과 -->
			<div class="wrap_con">
				<!-- list -->
				<div class="bbs_list">
					<ul class="tit">
						<li class="tit"><spring:message code="text.common.title.searchResult"/></li>
						<%-- <c:if test="${aprvStsCdChk  eq '00' || aprvStsCdChk  eq '02'}"> --%>
						<c:if test="${aprvStsCdChk  eq '00' || aprvStsCdChk  eq '02'||aprvStsCdChk  eq '03' }">
							<li class="btn"> <a href="#" class="btn" onclick="javascript:fn_save();" ><span>저장</span></a> </li>
							<li class="btn"> <a href="#" class="btn" onclick="javascript:productAdd();"><span>추가</span></a> </li>
						</c:if>
					</ul>
		
					<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
							<div id="ibsheet1"></div><!-- IBSheet 위치 -->
						</tr>
					</table>
				</div>
			</div>
			<!-- 조회결과 //-->
</div>
</form>



</div>
	<!-- footer -->
	<div id="footer">
		<div id="footbox">
			<div class="msg" id="resultMsg"></div>
			<div class="location">
				<ul>
					<li>홈</li>
					<li>기획전관리</li>
					<li class="last">기획전 상품정보</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>

<!--	@ BODY WRAP  END  	// -->
</body>
</html>