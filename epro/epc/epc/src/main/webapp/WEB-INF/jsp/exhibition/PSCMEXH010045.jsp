<%--
- Author(s): jdj
- Created Date:
- Version : 1.0
- Description : 기획전관리 - 기획전 상품등록

--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%-- 버튼권한처리 태그 라이브러리 삭제 --%>
<%@page import="java.util.Map"%>
<%@page import="lcn.module.common.util.DateUtil"%>
<%@page import="com.lcnjf.util.StringUtil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />

<script type="text/javascript">

$(document).ready(function() {
	
	//기획전 내용 IBSheet==============================================================================================================
	// START of IBSheet Setting
	createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "477px");
	
	mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);	
	var ibdata = {};
	// SizeMode: 
	ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral}; // 10 row씩 Load
	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.Cols = [
        {Header:"순번",               Type:"Int",           SaveName:"SEQ",                         Align:"Center", Width:40,  Edit:0}      
      , {Header:"상태",               Type:"Status",        SaveName:"STATUS",                      Align:"Center", Width:40,  Edit:0, Hidden:true }      
      , {Header:"",                   Type:"CheckBox",      SaveName:"CHK",                         Align:"Center", Width:40,  Sort:false} // default=1(수정가능)

      , {Header:"점포",               Type:"Text",          SaveName:"STR_NM",                      Align:"Center", Width:60 , Edit:0}
      , {Header:"인터넷상품코드",     Type:"Text",          SaveName:"PROD_CD",                     Align:"Center", Width:100, Edit:0, FontColor:"#0000FF", Cursor:"Pointer"}
      , {Header:"상품명",             Type:"Text",          SaveName:"PROD_NM",                     Align:"Left"  , Width:150, Edit:0}
      
      , {Header:"전시순서",           Type:"Int",           SaveName:"DISP_SEQ",                    Align:"Center", Width:60,  Edit:1, EditLen:2}            
      , {Header:"근거리전용",         Type:"Text",          SaveName:"CMBN_MALL_SELL_PSBT_YN",      Align:"Center", Width:60,  Edit:0}
      
      , {Header:"협력업체",           Type:"Text",          SaveName:"VENDOR_NM",                   Align:"Left"  , Width:99,  Edit:0}      
      , {Header:"판매가",             Type:"Int",           SaveName:"CURR_SELL_PRC",               Align:"Right" , Width:99,  Edit:0, Format:"#,###,###", FormatFix:0}      
      , {Header:"전시여부",           Type:"Combo",         SaveName:"DISP_YN",                     Align:"Center", Width:60,  Edit:1, ComboCode:"Y|N", ComboText:"전시|미전시"}
      
      , {Header:"품절",               Type:"Text",          SaveName:"SOUT_YN",                     Align:"Center", Width:30,  Edit:0}
      
      , {Header:"이미지",             Type:"Image",         SaveName:"IMG_PATH",                    Align:"Center", Width:70 , Edit:0}
      
	  , {Header:"등록일", 	 	 	  Type:"Text", 			SaveName:"REG_DATE", 					Align:"Center", Width:80, Edit:0}
	  , {Header:"등록자", 			  Type:"Text", 			SaveName:"REG_NM",  			 		Align:"Left"  , Width:80, Edit:0}
	  , {Header:"최종수정일", 		  Type:"Text", 			SaveName:"MOD_DATE",  					Align:"Center", Width:80, Edit:0}
	  , {Header:"최종수정자",  		  Type:"Text", 			SaveName:"MOD_NM",					 	Align:"Left"  , Width:80, Edit:0}


	  , {Header:"CATEGORY_ID",        Type:"Text", 	        SaveName:"CATEGORY_ID",      	        Hidden:true }	  
	  , {Header:"MKDP_SEQ",           Type:"Text", 			SaveName:"MKDP_SEQ",      	        	Hidden:true }	  
	  , {Header:"DIVN_SEQ",           Type:"Text", 			SaveName:"DIVN_SEQ",      	        	Hidden:true, Align:"Center", Width:80, Edit:0 }
	  , {Header:"STR_CD",             Type:"Text", 			SaveName:"STR_CD",      	        	Hidden:true }
	  
	  , {Header:"CHK_KEY"   ,         Type:"Text", 			SaveName:"CHK_KEY"   ,      	        Hidden:true }
	  
	  
	];
	
	IBS_InitSheet(mySheet, ibdata);		
	mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.1
	mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
	
	$("#spanProdOrdSetup2").hide();

	var categoryId  = "${categoryId }";
	var mkdpSeq     = "${mkdpSeq }";
	
	$('#btnProductAdd').click(function() {
		//조회조건 점포가 전체일때만 상품추가 가능
		if($('#strCd').val()!=""){
			alert("점포가 전체일때만 상품추가가 가능합니다.");
			return;
		}		
		
		openProduct("M"); // M: 멀티 , S:싱글, B:싱글(정기배송)
	});	
	
	$('#divnSeq').change(function() {
		fn_search();
	});
	
	$('#strCd').change(function() {
		fn_search();
	});
	
	//기획전 상품등록 목록조회
	fn_search();
	
	//상품정렬방식2
	$('#prodOrdSetup2 option[value="06"]').remove();   //전시순서          삭제
	$('#prodOrdSetup2 option[value="07"]').remove();   //전시순서+자동정렬 삭제
});

//상품 팝업을 호출하기 위해서 필수로 필요하다.
function fnSetProduct(data) {

	var categoryId  = "${categoryId }";
	var mkdpSeq     = "${mkdpSeq }";
	var divnSeq     = $("#divnSeq").val();
	
	/*
	//상품코드 중복체크
	for(var i=0; i < data.prodCdArr.length; i++ ){ 
		var prodRow = mySheet.FindText("PROD_CD", data.prodCdArr[i], 0);
		
		if(prodRow>0){
			alert((prodRow)+"번째 "+mySheet.GetCellValue(prodRow, 'PROD_NM')+" 상품이 중복되었습니다.");
			return;
		}
	}
	
	*/

	//var formQueryString = $('*', '#form1').fieldSerialize();
	//var formQueryString = "prodCdArr="+data.prodCdArr+"&prodDivnCdArr="+data.prodDivnCdArr;
	jQuery.ajaxSettings.traditional = true
	//Ajax 저장처리
	$.ajax({
		type: 'POST',
		url: '<c:url value="/exhibition/selectProdItemList.do"/>',
		data: {  'prodCdArr'     : data.prodCdArr
	           , 'prodDivnCdArr' : data.prodDivnCdArr
	           
	           , 'categoryId'    : categoryId
	           , 'mkdpSeq'       : mkdpSeq
	           , 'divnSeq'       : divnSeq
		},
		success: function(json) {
			try {				
				//alert(json.resultMsg);
				var prodItemList = json.prodItemList;
				var cnt          = prodItemList.length;
				
				//중복체크
				for (var i=0; i < cnt; i++) {
					var prodRow = mySheet.FindText("CHK_KEY", prodItemList[i].CHK_KEY, 0);
					
					if(prodRow>0){
						alert((prodRow)+"번째 '"+mySheet.GetCellValue(prodRow, 'STR_NM')+"' 점포의 '"+mySheet.GetCellValue(prodRow, 'PROD_NM')+"' 상품이 중복되었습니다.");
						return;
					}
				}

				//상품목록 추가
				for (var i=0; i < cnt; i++) {
					//console.log(prodItemList[i]);					
					var rowIdx = mySheet.DataInsert( -1);
					mySheet.SetCellValue(rowIdx, 'CATEGORY_ID', categoryId);
					mySheet.SetCellValue(rowIdx, 'MKDP_SEQ'   , mkdpSeq);
					
					mySheet.SetCellValue(rowIdx, 'PROD_CD'      , prodItemList[i].PROD_CD);
					mySheet.SetCellValue(rowIdx, 'STR_CD'       , prodItemList[i].STR_CD);
					mySheet.SetCellValue(rowIdx, 'STR_NM'       , prodItemList[i].STR_NM);
					mySheet.SetCellValue(rowIdx, 'PROD_NM'      , prodItemList[i].PROD_NM);
					mySheet.SetCellValue(rowIdx, 'VENDOR_ID'    , prodItemList[i].VENDOR_ID);
					mySheet.SetCellValue(rowIdx, 'VENDOR_NM'    , prodItemList[i].VENDOR_NM);
					mySheet.SetCellValue(rowIdx, 'CURR_SELL_PRC', prodItemList[i].CURR_SELL_PRC);
					mySheet.SetCellValue(rowIdx, 'DISP_YN'      , prodItemList[i].DISP_YN);					
					
					mySheet.SetCellValue(rowIdx, 'SOUT_YN'      , prodItemList[i].SOUT_YN);
					mySheet.SetCellValue(rowIdx, 'CMBN_MALL_SELL_PSBT_YN', prodItemList[i].CMBN_MALL_SELL_PSBT_YN);
				}
				
			} catch (e) {}
		},
		error: function(e) {
			alert(e);
		}
	});


}

//조회
function fn_search() {
	var divnSeq         = $("#divnSeq").val();
	var prodOrdSetupVal = $("#prodOrdSetupVal").val();
	
	var sUrl = '<c:url value="/exhibition/selectExhibitionProdInfoList.do"/>';		
	loadIBSheetData(mySheet, sUrl, null, '#dataForm', null);
	
}

function mySheet_OnSearchEnd() {
	//총 카운트 입력
	$("#rowTotalCount").text(mySheet.RowCount());
	
	//점포 선택여부에 따라 전시순서 에디팅처리
	if($("#strCd").val()==""){
		mySheet.SetColEditable("DISP_SEQ", false);
	}else{
		mySheet.SetColEditable("DISP_SEQ", true);
	}
		
	//상품정렬방식 조회
	var formQueryString = $('*', '#dataForm').fieldSerialize();
	var targetUrl = '/lottemart-epc/exhibition/selectDivnProdSortInfo.do';	
	
	$.ajax({
		type: 'POST',
		url: targetUrl,
		data: formQueryString,
		success: function(json) {
			try {		
				var ordSetup    = json.divnProdSortInfo.PROD_ORD_SETUP;
				var ordSetupVal = json.divnProdSortInfo.PROD_ORD_SETUP_VAL;
				var ordSetup2   = json.divnProdSortInfo.PROD_ORD_SETUP2;
				
				$("#prodOrdSetup").val(ordSetup);
				$("#prodOrdSetupVal").val(ordSetupVal);
				$("#prodOrdSetup2").val(ordSetup2);
				
				if(ordSetup == '07'){
					$("#spanProdOrdSetup2").show();
				}else{
					$("#spanProdOrdSetup2").hide();
				}

			} catch (e) {}
		},
		error: function(e) {
			alert(e);
		}
	});
	
	
}	

//셀 클릭시 팝업 호출 
function mySheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
	if (Row < 1) return;
}

//기획전 내용(소개) /  기획전 배너이미지 조회 - 삭제
function fn_contentsDel(contentsDivnCd) {
	if(confirm("삭제하시겠습니까?")){
		var sUrl = '<c:url value="/exhibition/deleteContentsImage.do"/>';
		//mySheet.DoSave(sUrl, {Param:'gbn=1', Quest:false});
		mySheet.DoSave(sUrl, {Quest:false});   //기획전 내용(소개)

	}
}

//저장후 재조회
//Code: 서버에서 저장한 코드, Msg: 서버에서 저장한 메세지, StCode: Http통신코드(200=정상), StMsg:HTTP메세지(OK)
function mySheet_OnSaveEnd(Code, Msg, StCode, StMsg) {
	alert(Msg);
	if (Code == 1) {		
		fn_search();
	}
}

//저장
function fn_save() {
	var f = document.dataForm;
	
	//구분자명, 상품정렬방식 체크
 	if(f.divnSeq.value == "" || f.divnSeq.value == "null") {
		alert('<spring:message code="msg.common.error.required" arguments="구분자"/>');
		f.divnSeq.focus();
		return;
	}
	
 	if(f.prodOrdSetup.value == "" || f.prodOrdSetup.value == "null") {
		alert('<spring:message code="msg.common.error.required" arguments="상품정렬방식"/>');
		f.prodOrdSetup.focus();
		return;
	}
 	
 	if(f.prodOrdSetup.value == "07"){
 		if(f.prodOrdSetupVal.value == "" || f.prodOrdSetupVal.value == "null") {
 			alert('<spring:message code="msg.common.error.required" arguments="전시순서노출 개수"/>');
 			f.prodOrdSetupVal.focus();
 			return;
 		}
 		if(f.prodOrdSetup2.value == "" || f.prodOrdSetup2.value == "null") {
 			alert('<spring:message code="msg.common.error.required" arguments="세부 상품정렬방식"/>');
 			f.prodOrdSetup2.focus();
 			return;
 		}
 	}
 	
 	//* 저장할 내역이 상품정렬방식만 존재시 별도 처리
 	//  트랜잭션 갯수 체크
 	//var checkCnt = mySheet.CheckedRows("CHK"); 	
 	//console.log('checkCnt=>'+checkCnt);
	var tranCount = mySheet.RowCount("I") + mySheet.RowCount("U") + mySheet.RowCount("D");
	//console.log('tranCount=>'+tranCount);

	if(confirm("저장하시겠습니까?")){
		var sUrl = '<c:url value="/exhibition/insertExhibitionProdInfo.do"/>';
		var categoryId  = "${categoryId }";
		var mkdpSeq     = "${mkdpSeq }";
		var divnSeq         = $("#divnSeq").val();
		var prodOrdSetup    = $("#prodOrdSetup").val();			
		var prodOrdSetupVal = $("#prodOrdSetupVal").val();
		var prodOrdSetup2   = $("#prodOrdSetup2").val();
		
		var strCd           = $("#strCd").val();
		
		sUrl += "?divnSeq="         + divnSeq;
		sUrl += "&prodOrdSetup="    + prodOrdSetup;		
		sUrl += "&prodOrdSetupVal=" + prodOrdSetupVal;
		sUrl += "&prodOrdSetup2="   + prodOrdSetup2;		
		sUrl += "&categoryId="      + categoryId;
		sUrl += "&mkdpSeq="         + mkdpSeq;
		sUrl += "&strCd="           + strCd;
		
		//수정건이 존재시 IbSheet기준으로 트랜잭션 날림
		if(tranCount>0){
			mySheet.DoSave(sUrl, {Quest:false, col:2});
			
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

//삭제
function fn_delete() {
	if(confirm("삭제하시겠습니까?")){
		var sUrl = '<c:url value="/exhibition/deleteExhibitionProdInfo.do"/>';
		mySheet.DoSave(sUrl, {Quest:false});   //기획전 내용(소개)
	}	
	
}

//상품정렬 변경시    
function fn_sortChk(gbn) {
	var sortVal = gbn.value;	
	//console.log(sortVal);
	
	if(sortVal=='07'){
		$("#spanProdOrdSetup2").show();
	}else{
		$("#spanProdOrdSetup2").hide();
	}
	
}

//엑셀
function fn_excel() {
	var today = Common.getToday().replace(/-/g, '');
	//var xlsUrl = '<c:url value="/product/exportPBOMBRD0003Excel.do"/>';
	var xlsUrl = '<c:url value="/exhibition/exportPBOMEXH010045Excel.do"/>';
	var hideCols = 'CHK|IMG_PATH';
	directExcelDown(mySheet, '기획전할당상품엑셀_'+today, xlsUrl, '#dataForm', null, hideCols); // 전체 다운로드 
}

//전시여부 선택적용
function fn_dispYnApply() {
	var cnt     = mySheet.RowCount();	
	var dispYn  = $('#dispYn').val();
	
	var applyYn = 0;
	
	if(dispYn==""){
		alert("선택 적용할 전시여부를 선택하세요");
		return;
	}
	
	if(cnt==0){
		alert("적용 자료가 없습니다.");
		return;
	}
	
	var resultRow = mySheet.FindText("CHK", "1", 0);
	//console.log('resultRow=>'+resultRow);
	if(resultRow==-1){
		alert("선택된 자료가 없습니다.");
		return;
	}
	
	for (var rowIdx = 1; rowIdx <= cnt; rowIdx++) {
		if(mySheet.GetCellValue(rowIdx, 'CHK')=='1'){
			mySheet.SetCellValue(rowIdx, 'DISP_YN', dispYn);
			applyYn++;
		}
	}
	
	alert(applyYn+"건의 자료가 적용되었습니다. 저장하세요");	
}

</script>


</head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
<!-- <form name="form1" id="form1" method="post"  enctype="multipart/form-data"> -->
<form name="dataForm" id="dataForm" >

<input type="hidden" id="categoryId" name="categoryId" value="<c:out value="${categoryId}"/>" >
<input type="hidden" id="mkdpSeq"    name="mkdpSeq"    value="<c:out value="${mkdpSeq }"/>" >

	<div class="pop_box_01">
	  <h2>기획전 상품등록<span class="pop_close"><a href="javascript:window.close();"><img src="${_image_path}/epc/new/pop_close.png" alt="close" /></a></span></h2>
	  <div class="p_navi">
	    <ul>
	      <li><a href="#"><img src="${_image_path}/epc/new/icon_home.png" alt="home" /></a> > 기획전 관리 > 기획전 상품등록</li>
	    </ul>
	  </div>

	  <ul>
	  
		<h3>기획전 기본정보 <span class="p_btn"><span class="btnBG1"><a href="javascript:fn_search();">조회</a></span></span></h3>
	    <li>
	      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="tbl_02" summary="기획전 기본정보" >
	        <col width="157px" />
	        <col width="30%" />
	        <col width="157px" />
	        <col width=" " />
	        <tr>
	          <th>기획전 코드</th>
	          <td>${resultMap.CATEGORY_ID}</td>
	          <th>기획전명</th>
	          <td>${resultMap.MKDP_NM}</td>
	        </tr>
	        <tr>
	          <th>기획전 기간</th>
	          <td>${resultMap.VIEW_MKDP_START_DATE}~${resultMap.VIEW_MKDP_END_DATE}</td>
	          <th>진행상태</th>
	          <td>${resultMap.PROGRESS_STATUS}</td>
	        </tr>
	      </table>
	    </li>
	    <p>&nbsp;</p>	

	    <h3>기획전 상품목록</h3>	    
		<table width="100%" border="0" cellpadding="0" cellspacing="1" class="tbl_02" summary="기획전 상품목록">
	        <colgroup>
		        <col width="120px">
		        <col width="300px">
		        <col width="120px">
		        <col width="150px">
		        <col width="120px">
		        <col width=" ">
	        </colgroup>
	        <tbody>
	        <tr>
	        	<th><em>*</em>구분자명</th>
	          	<td >                                                                              
					<select name="divnSeq" id="divnSeq" style="width:280px" title="구분자명"> '; 
						<!-- <option value="">선택</option> -->                                                                   						                                                 
						<c:forEach items="${divnSeqList }" var="value">
                      	<option value="${value.CODE }">${value.NAME }</option>                           			
                      	</c:forEach>                                                                   
					</select>								
	          	</td>
	          	<th>점포</th>
	          	<td >                                                                              
					<select name="strCd" id="strCd" style="width:140px" title="점포명"> '; 
						<option value="">전체</option>
						<c:forEach items="${storeList }" var="value">
                      	<option value="${value.CAT_CD }">${value.CAT_NM }</option>                           			
                      	</c:forEach>                                                                   
					</select>								
	          	</td>
	          	<th>전시여부 선택적용</th>
	          	<td >                                                                              
					<select name="dispYn" id="dispYn" style="width:95px" title="전시여부 선택적용"> ';
						<option value="">선택</option>
						<option value="Y">전시</option>                                                                   
						<option value="N">미전시</option>
					</select>
					<span class="btnBG1"><a href="javascript:fn_dispYnApply();">적용</a></span>
	          	</td>
	          	
	        </tr>	        
	        <tr>
	        	<th><em>*</em>상품정렬방식</th>
	          	<td colspan="5">                                                                              
					<select name="prodOrdSetup" id="prodOrdSetup" style="width:140px" title="상품정렬방식" onchange="javascript:fn_sortChk(this);"> '; 
						<!-- <option value="">선택</option> -->                                                          						                                                 
						<c:forEach items="${prodSortCdList }" var="value">
                       	<option value="${value.MINOR_CD }">${value.CD_NM }</option>                           			
                       	</c:forEach>
					</select>
					
					<span id="spanProdOrdSetup2">
					전시순서 노출 개수 : 
					&nbsp;<input name="prodOrdSetupVal" type="text" id="prodOrdSetupVal" style="width:30px" title="전시순서 노출 개수" value="" maxLength="3">개 + 					
					<select name="prodOrdSetup2" id="prodOrdSetup2" style="width:140px" title="상품정렬방식2" > 
						<!-- <option value="">선택</option> -->
						                                                          						                                                 
						<c:forEach items="${prodSortCdList }" var="value">
                       	<option value="${value.MINOR_CD }">${value.CD_NM }</option>                  			
                       	</c:forEach>
                       	
					</select>
					</span>					
							
	          	</td>
	        </tr>	        
	  		</tbody>
	  	</table>		
		<li class="p_total"> [총건수 <em id="rowTotalCount">0</em>건] 				
		<span class="btn_frt"> 
			<span class="p_btn">
				<span class="btnBG1">
				<a id="btnProductAdd">상품추가</a>							
				</span>
				<span class="btnBG1">
				<a href="javascript:fn_delete();">삭제</a>							
				</span>
				<span class="btnBG1">
				<a href="javascript:fn_save();">저장</a>							
				</span>
				<span class="btnBG1">
				<a href="javascript:fn_excel();">excel</a>							
				</span>
				<span class="btnBG1"><a href="javascript:window.close();">닫기</a></span>
			</span>
        </li>
		<li class="lp">
			<table width="99.5%" border="0" cellpadding="0" cellspacing="1" class="tbl_01" summary=" "  id="sheetTbl">
				<col width="100%" />
				<tr>
				<td>
					<div id="ibsheet1"></div>
				</td><!-- IBSheet 위치 -->
				</tr>
			</table>
		</li>
	  </ul>

	</div>
	

</form>
</body>
</html>