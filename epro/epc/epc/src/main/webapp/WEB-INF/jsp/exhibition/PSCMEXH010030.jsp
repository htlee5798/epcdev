<%--
- Author(s): jdj
- Created Date:
- Version : 1.0
- Description : 기획전관리 - 구분자정보 관리 팝업

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
<%@ include file="/common/scm/scmCommon.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />

<script type="text/javascript">

$(document).ready(function() {
	
	var categoryId  = "${categoryId }";
	var mkdpSeq     = "${mkdpSeq }";
	$("#viewMkdpSeq", parent.document).val( $("#mkdpSeq").val() );
	var delCount = 0;
	
	//구분자 정보목록 조회
	fn_search(categoryId, mkdpSeq);	
});

function fn_search(categoryId, mkdpSeq) {	
	var formQueryString = $('*', '#form1').fieldSerialize();	
	$.ajax({
		type: 'POST',
		url: '<c:url value="/exhibition/selectDivnInfoList.do"/>',
		data: formQueryString,
		success: function(json) {
			try {				
				//alert(json.resultMsg);
				//삭제대상 구분자목록 초기화				
				$("#deleteItemDivnSeq").val("");				
				
				//목록 조회
				var divnInfoList = json.divnInfoList;
				//var dispYnChk = divnInfoList[0].DISP_YN
				
				
			     //$("#dispYnChk").val(dispYnChk);
				
				var cnt = divnInfoList.length;

				if(cnt==0){
					fn_addRemoveItem('1');  //최초 신규상태인 경우 기본행 추가
				}else if(cnt>0){
					//구분자 정보목록 추가
					for (var i=0; i < cnt; i++) {
						//console.log(divnInfoList[i]);
						fn_addRemoveItem('1', divnInfoList[i]);
					}
					
				}
				
			} catch (e) {}
		},
		error: function(e) {
			alert(e);
		}
	});
}

//화면 이동
function fn_tabMove(gbn) {
	var targetUrl = "";
	
	var categoryId  = "${categoryId }";
	var mkdpSeq     = "${mkdpSeq }";
	
	if(mkdpSeq==""){
		alert("기획전 기본정보를 먼저 등록하세요.");
		return;
	}
	
	if(gbn=='1'){		
		//카테고리번호, 점포코드, 기획전번호
		
		targetUrl = '${pageContext.request.contextPath}/exhibition/showExhibitionForm.do?pageDiv=update';
		targetUrl += "&categoryParentId=" + categoryId.substring(0, 8);
		targetUrl += "&categoryChildId="  + categoryId;
		targetUrl += "&mkdpSeq="+mkdpSeq;

	}else if(gbn=='2'){

		//카테고리번호, 점포코드, 기획전번호
		targetUrl = '<c:url value="/exhibition/showDivnForm.do"/>';
		targetUrl += "?categoryId="+categoryId;
		targetUrl += "&mkdpSeq="+mkdpSeq;
		targetUrl += "&pageDiv=update";
		
	}else if(gbn=='3'){                          //이미지/HTML정보 이동		
		//카테고리번호, 점포코드, 기획전번호
		targetUrl = '<c:url value="/exhibition/showImageHtmlContentsForm.do"/>';
		targetUrl += "?categoryId="+categoryId;
		targetUrl += "&mkdpSeq="+mkdpSeq;
		targetUrl += "&pageDiv=update";
	}
	
	window.document.location.href = targetUrl;
}

function fn_addRemoveItem(gbn, divnInfo) {
	
	var divnInfoList = $("#divnInfoList");
	var strHtml = "";
	
	//총카운트
	var cnt = $("[id^='liDivnItem']").length;
	
	//KEY
	var divnSeq        = "";     //구분자번호
	
	var divnNm         = "";     //구분자명
	var dispSeq	       = "";     //전시순번
// 	var divnType	   = "";     //구분자유형코드
	var dispYn	       = "";     //전시여부
// 	var dispToCd       = "";     //전시대상코드
// 	var dispProdCnt    = "";     //전시상품수
	var prodTemplateCd = "";     //전시템플릿SM318
// 	var mkdpStartDate  = "";     //시작일시
// 	var mkdpStartHh    = "";
// 	var mkdpStartMm    = "";
// 	var mkdpEndDate    = "";     //종료일시
// 	var mkdpEndHh      = "";
// 	var mkdpEndMm      = "";
	
	var subStrCd       = "";
	var subStrNm       = "";
	
	/*신규추가시 divnInfo = undefined
	  Ajax조회시 divnInfo = 조회된 데이터
	 */
	if (typeof divnInfo !== "undefined") {
		divnSeq        = divnInfo.DIVN_SEQ;                           //구분자번호
		
		divnNm         = divnInfo.DIVN_NM;                            //구분자명
		dispSeq	       = divnInfo.DISP_SEQ;                           //전시순번
// 		divnType	   = divnInfo.DIVN_TYPE;                          //구분자유형코드
		dispYn	       = divnInfo.DISP_YN;                            //전시여부
		
// 		divnImgUrl     = divnInfo.DIVN_IMG_URL;                       //구분이미지URL		
// 		if(divnImgUrl==null){
// 			divnImgUrl = "";
// 		}
		
// 		dispToCd       = divnInfo.DISP_TO_CD;                         //전시대상코드
// 		dispProdCnt    = divnInfo.DISP_PROD_CNT;                      //전시상품수
		prodTemplateCd = divnInfo.PROD_TEMPLATE_CD;                   //전시템플릿SM318
// 		mkdpStartDate  = divnInfo.START_DATE.substring(0,8);          //시작일시
// 		mkdpStartHh    = divnInfo.START_DATE.substring(8,10);
// 		mkdpStartMm    = divnInfo.START_DATE.substring(10,12);
// 		mkdpEndDate    = divnInfo.END_DATE.substring(0,8);            //종료일시
// 		mkdpEndHh      = divnInfo.END_DATE.substring(8,10);
// 		mkdpEndMm      = divnInfo.END_DATE.substring(10,12);	
		
		subStrCd       = divnInfo.SUB_STR_CD;
 		if(subStrCd==null){
			subStrCd = "";
 		}
		
 		subStrNm       = divnInfo.SUB_STR_NM;
 		if(subStrNm==null){
 			subStrNm = "";
 		}
	}

	if(gbn=='1'){           //추가		
		var idx = cnt + 1;
		
		strHtml  = '<li id="liDivnItem'+idx+'">                                                                                                                                              ';
		
		strHtml += '<input type="hidden" id="divnSeq'+idx+'" name="divnSeq'+idx+'" value="'+divnSeq+'">  ';
		
		//점포코드(멀티) 수정시 Y로 변경
 		strHtml += '<input type="hidden" id="storeModifyYn'+idx+'" name="storeModifyYn'+idx+'" value="N">  ';
		
		//점포코드(멀티) 수정전 값 보관
 		strHtml += '<input type="hidden" id="oldSubStrCd'+idx+'" name="oldSubStrCd'+idx+'" value="'+subStrCd+'" >   ';
		
		//점포코드(멀티)
 		strHtml += '<input type="hidden" id="subStrCd'+idx+'" name="subStrCd'+idx+'" value="'+subStrCd+'" >   ';
		
		strHtml += '  <table class="bbs_search" cellpadding="0" cellspacing="0" border="0">                                                  ';		
		strHtml += '	<col width="20%" />                                                                                                                             ';
		strHtml += '	<col width="30%" />                                                                                                                             ';
		strHtml += '	<col width="20%" />                                                                                                                             ';
		strHtml += '	<col width=" " />                                                                                                                               ';
		strHtml += '	<tr>                                                                                                                                            ';
		strHtml += '		<th><em>*</em>하위 상품그룹명</th>                                                                                                                ';
		strHtml += '		<td><input type="text" name="divnNm'+idx+'"  id="divnNm'+idx+'" value="'+divnNm+'" style="width:80%"></td>                                                          ';
		strHtml += '		                                                                                                                                            ';
		strHtml += '		<th><em>*</em>전시여부</th>                                                                                                                 ';
		strHtml += '		<td>                                                                                                                                        ';
		strHtml += '			 <label>                                                                                                                                ';
		strHtml += '			   <input name="dispYn'+idx+'" id="dispYn'+idx+'"  type="radio" class="choice" title="Y" value="Y" '+ (dispYn=='Y'||dispYn==''?'checked':'') +' />전시</label>                             ';
		strHtml += '			 &nbsp;                                                                                                                                 ';
		strHtml += '			 <label>                                                                                                                                ';
		strHtml += '			   <input name="dispYn'+idx+'" id="dispYn'+idx+'"  type="radio" class="choice" title="N" value="N" '+ (dispYn=='N'?'checked':'') +' />전시안함</label>	          	                    ';
		strHtml += '			   </span><span class="tb_img_btnbox"><a href="javascript:fn_removeItem(\''+idx+'\');"><img src="/images/epc/new/btn_a_minus.png" alt="삭제" class="search"></a></span>	          	                    ';
		strHtml += '		</td>                                                                                                                                       ';
		strHtml += '	</tr>                                                                                                                                           ';
		strHtml += '	                                                                                                                                                ';
		strHtml += '	<tr>                                                                                                                                            ';
		strHtml += '		<th><em>*</em>하위 상품그룹 순위</th>                                                                                                          ';
		strHtml += '		<td><input type="text" name="dispSeq'+idx+'" id="dispSeq'+idx+'" value="'+dispSeq+'" style="width:20px" maxLength="3" onkeydown="return onlyNumber(event)" onkeyup="removeChar(event)" > <span style="padding-right: 120px;">1 ~ 999 입력</td>';
		//strHtml += '		<td><input type="text" name="dispSeq'+idx+'" id="dispSeq'+idx+'" value="'+dispSeq+'" style="width:20px" maxLength="3"> <span style="padding-right: 120px;">1 ~ 999 입력</span><a href="javascript:fn_removeItem(\''+idx+'\');"><img src="${_image_path}/epc/new/btn_a_minus.png" alt="삭제" class="search"></a></td>';
		strHtml += '		<th>상품전시 템플릿</th>                                                                                                          ';
		strHtml += '		<td>                                                                                                                                        ';
		strHtml += '			<select name="prodTemplateCd'+idx+'" id="prodTemplateCd'+idx+'" style="width:140px" title="상품전시 템플릿" > ';
		strHtml += '				<option value="">선택</option>                                                                                                      ';
		strHtml += '				<c:forEach items="${goodsTemplateList }" var="value">                                                                               ';
		strHtml += '					<option value="${value.MINOR_CD }">${value.CD_NM }</option>                                                                     ';
		strHtml += '				</c:forEach>                                                                                                                        ';
		strHtml += '			</select>                                                                                                                               ';		
		strHtml += '            <a href="javascript:fn_preview(\'2\', \'prodTemplateCd'+idx+'\');" class="btn"><span>미리보기</span></a>   ';				
		strHtml += '		</td>                                                                                                                                       ';
		strHtml += '	</tr>                                                                                                                                           ';

		strHtml += '	<tr>                                                                                                                                            ';
		strHtml += '		<th><em>*</em>점포할당</th>                                                                                                                 ';
		strHtml += '		<td colspan="3">                                                                                                                            ';
		strHtml += '            <textarea name="subStrNm'+idx+'" id="subStrNm'+idx+'" rows="3" cols="108" readonly style="resize:none;" >'+subStrNm+'</textarea>    ';
		<%--@4UP 수정 [#JSP-002]--%>
		strHtml += '			<a href="javascript:fn_strPopUp('+idx+');"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/btn/bt_search.gif" alt="" class="middle" ></a>';
		strHtml += '		</td>                                                                                                                                       ';
		strHtml += '	</tr>                                                                                                                                           ';
		
		strHtml += '  </table>                                                                                                                                          ';		
		strHtml += '                                                                                                                                               ';
		strHtml += '<br/>                                                                                                                                              ';
		strHtml += '</li>                                                                                                                                               ';
		divnInfoList.append(strHtml);
		
		//템플릿코드 콤보입력
		$("#prodTemplateCd"+idx).val(prodTemplateCd);		
				
// 		$("input:radio[name=divnType"+idx+"]").change(function(){			
//         	//이미지URL 초기화
//        		$("#divnImgUrl"+idx).val("");        	
//         	if($(this).val()=='01'){
//         		$("#divnImgUrl"+idx).attr("readonly", true);
//         	}else{
//         		$("#divnImgUrl"+idx).attr("readonly", false);
//         	}
// 		});
		
		
		//전시대상이 HTML이면 상품수, 템플릿 초기화 및 Readonly 처리
		$("input:radio[name=dispToCd"+idx+"]").change(function(){
       		$("#dispProdCnt"+idx).val(0);
       		$("#prodTemplateCd"+idx).val("");
       		fn_dispToCdProcess(idx);
		});
		fn_dispToCdProcess(idx);
		
	}else if(gbn=='2'){     //삭제
		/*if(cnt==0){
			alert("삭제할 구분자정보가 없습니다.");
			return false;
		}
		
		 //삭제대상 히든값에 입력하기
		var deleteItemDivnSeqVal = $("#deleteItemDivnSeq").val();
		if(deleteItemDivnSeqVal=="") deleteItemDivnSeqVal += $("#divnSeq"+cnt).val();
		else deleteItemDivnSeqVal += "T" + $("#divnSeq"+cnt).val();
		
		$("#deleteItemDivnSeq").val(deleteItemDivnSeqVal);
				
		divnInfoList.children(":last").remove();
		$("#count").val(cnt-1); 
		*/
	}

	//공통 카렌더 적용
	$(".day").each(function(i){
		id = $(".day").eq(i).attr("id");
		openCals(id);
		$("#"+id).inputmask({
			mask: "y-1-2", 
			placeholder: "YYYY-MM-DD", 
			leapday: "-02-29", 
			separator: "-", 
			alias: "yyyy/mm/dd"
		});
	});	
}

function fn_dispToCdProcess(idx) {
	if($("input:radio[name=dispToCd"+idx+"]:checked").val() == "02"){
		$("#dispProdCnt"+idx).attr("readonly", true);
		$("#prodTemplateCd"+idx).attr("disabled", true);
	}else{ 
		$("#dispProdCnt"+idx).attr("readonly", false);
		$("#prodTemplateCd"+idx).attr("disabled", false);
	}
}

function fn_calDateDisplay(target) {
	openCal(target);
}

//저장
function fn_save() {	
	var msg = "";
	delCount = 0;
	
	
	var aprvStsCdChk=  $("#aprvStsCdCKK").val();
	
	if($("#dispYnChk").val()=="Y" && aprvStsCdChk !="00"){
		alert("전시상태에서는 기획전을 수정할 수 없습니다.")
		return;
	}	
	

	if(!fn_validation()){
		return;
	}
	
	msg = '<spring:message code="msg.common.confirm.regist" />';
	msg = '저장하시겠습니까?';
	
	if(!confirm(msg)) {
		return;
	}
	
	fn_refreshIdxList();
	var insCnt = $("[id^='liDivnItem']").length;
	$("#insCount").val(insCnt);
	
	var formQueryString = $('*', '#form1').fieldSerialize();
	//Ajax 저장처리
	$.ajax({
		type: 'POST',
		url: '<c:url value="/exhibition/insertDivnInfo.do"/>',
		data: formQueryString,
		success: function(json) {
			try {				
				alert(json.resultMsg);
				
				if(json.resultCode > 0){
					$("[id^='liDivnItem']").remove();        //초기화				
					var categoryId  = "${categoryId }";
					var mkdpSeq     = "${mkdpSeq }";
					fn_search(categoryId, mkdpSeq);   //목록 조회
				}				
				
				
			} catch (e) {}
		},
		error: function(e) {
			alert(e);
		}
	});
	
}





//유효성 검사 
function fn_validation() {
	
	//총카운트
	var cnt  = $("[id^='liDivnItem']").length;
	var list = $("[id^='liDivnItem']");
	
	var rtn = true;
	
	list.each(function(seq,item){
		var idx = item.id.replaceAll('liDivnItem', '');
		if(fn_valCheck(idx, seq)==false){
			rtn = false;			
			return false;  //break
		}		
	});
	
	return rtn;
}

function onlyNumber(event){
	event = event || window.event;
	var keyID = (event.which) ? event.which : event.keyCode;
	if ( (keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105) || keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 ) 
		return;
	else
		return false;
}
function removeChar(event) {
	event = event || window.event;
	var keyID = (event.which) ? event.which : event.keyCode;
	if ( keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 ) 
		return;
	else
		event.target.value = event.target.value.replace(/[^0-9]/g, "");
}

//항목 검사
function fn_valCheck(idx, seq) {
	
	seq += 1;  //0번째를 1번째로 증가
	
	if($("#divnNm"+idx).val() == "" || $("#divnNm"+idx).val() == "null") {		
		alert(seq+'번째 '+'<spring:message code="msg.common.error.required" arguments="구분자명"/>');
		$("#divnNm"+idx).focus();
		return false;
	}
	
	//구분자 전시순위
	if($("#dispSeq"+idx).val() == "" || $("#dispSeq"+idx).val() == "null") {		
		alert(seq+'번째 '+'<spring:message code="msg.common.error.required" arguments="구분자 전시순위"/>');
		$("#dispSeq"+idx).focus();
		return false;
	}
	
	var dispSeqCnt = Number($("#dispSeq"+idx).val());	
	//전시순서 : 1 ~ 999 범위 체크
	if( (dispSeqCnt > 0 && dispSeqCnt <= 999)== false ){
		alert(seq+'번째 '+'전시순서는 1 부터 999 사이에만 입력이 가능합니다.');
		$("#dispSeq"+idx).focus();
		return false;
	}
	
	if($("#prodTemplateCd"+idx).val() == "" || $("#prodTemplateCd"+idx).val() == "null") {		
		alert(seq+'번째 '+'<spring:message code="msg.common.error.required" arguments="상품전시 템플릿"/>');
		$("#prodTemplateCd"+idx).focus();
		return false;
	}
	
	//점포할당
	if($("#subStrCd"+idx).val() == "" || $("#subStrCd"+idx).val() == "null") {
		alert(seq+'번째 '+'<spring:message code="msg.common.error.required" arguments="점포할당"/>');
		$("#subStrCd"+idx).focus();
		return false;
	}

	
	return true;
}

//시간 체크
function fHourCheck(val){
	var hourChk = false;
	if(val > 23){
		hourChk = true;
	}
	return hourChk;
}
// 분 체크
function fMinuteCheck(val){
	var minuteChk = false;
	if(val > 59){
		minuteChk = true;
	}
	return minuteChk;
}

//상품전시 템플릿 일괄적용
function fn_goodsTemplateApply(){
	var prodTemplateCdApplyVal = $("#prodTemplateCdApply").val();
	if(prodTemplateCdApplyVal==""){
		alert('일괄적용할 템플릿을 선택하세요.');
		return;
	}
	
	//$("[id^='prodTemplateCd']").val(prodTemplateCdApplyVal);
	$("[id^='prodTemplateCd']:enabled").val(prodTemplateCdApplyVal);
}

var gFocusIdx = 0;      //멀티행에 대한 인덱스값
	
/* function fn_strPopUp(idx) {
	gFocusIdx = idx;
	openStr("M");
} */

//점포팝업 반환값 셋팅 
// function fnSetStr(data) {
/* 	$("#subStrCd"+gFocusIdx).val(data.strCd);
	$("#subStrNm"+gFocusIdx).val(data.strNm); */
	
// 	var strCd = "", strNm = "";	
	
// 	if(jQuery.isArray(data.strCdArr)){
// 		for(var i=0; i < data.strCdArr.length; i++ ){
// 			//console.log('strCd=>'+strCd);
// 			strCd += data.strCdArr[i]+',';
// 			strNm += data.strNmArr[i]+',';
// 		}	
// 		if(strCd!="") strCd = strCd.substring(0, strCd.length-1);
// 		if(strNm!="") strNm = strNm.substring(0, strNm.length-1);
		
// 	}else{		
// 		strCd = data.strCd;
// 		strNm = data.strNm;
// 	}
	
// 	$("#subStrCd"+gFocusIdx).val(strCd);
// 	$("#subStrNm"+gFocusIdx).val(strNm);
		
// 	//점포코드 수정여부체크
// 	var oldStrCd = $("#oldSubStrCd"+gFocusIdx).val();	
	
// 	if(oldStrCd != strCd){
// 		$("#storeModifyYn"+gFocusIdx).val("Y");
// 	}else{
// 		$("#storeModifyYn"+gFocusIdx).val("N");
// 	}
// }

function fn_removeItem(idx) {
	//var cnt = $("[id^='liDivnItem']").length;
	// 리스트 체크를 해서 1개일 때에는 삭제가 안되도록 추가
	var cnt = $("[id^='liDivnItem']").length;
	if( cnt > 1) {
		$.  delCount++; 
		//삭제대상 히든값에 입력하기
		var deleteItemDivnSeqVal = $("#deleteItemDivnSeq").val();	
		if(deleteItemDivnSeqVal=="") deleteItemDivnSeqVal += $("#divnSeq"+idx).val();
		else deleteItemDivnSeqVal += "T" + $("#divnSeq"+idx).val();
		$("#deleteItemDivnSeq").val(deleteItemDivnSeqVal);
		
		$("#liDivnItem"+idx).remove();
		$("#delCount").val($("#delCount").val()+1);
	} else {
		alert("삭제할 수 없습니다.");
	} 
}

//idx 구분자로 리스트 보관
function fn_refreshIdxList() {
	var cnt  = $("[id^='liDivnItem']").length;

	var list = $("[id^='liDivnItem']");
	
	var idxList = "";
	var idx     = "";
	list.each(function(index, item){
		idx = item.id.replaceAll('liDivnItem', '');
		
		if(index==0){
			idxList += idx;
		}else{
			idxList += "T" + idx;
		}		
	});
	if(idxList!="") $("#idxList").val(idxList);
}

function fn_preview(gbn, name) {
// 	var categoryId = $("#categoryId").val();	
// 	window.open("http://www.lottemart.com/plan/planMain.do?CategoryID="+categoryId+"&previewYN=Y", "preView", "width=1000,height=700,toolbar=no");	

	var selectVal = '';
	if(gbn==1){
		selectVal = $('#prodTemplateCdApply').val();
	}else{
		selectVal = $('#'+name).val();
	}

	if(selectVal==''){
		alert('상품템플릿을 선택하세요');
		return;
	}

	//상품템플릿에 대한 미리보기링크
	var exhibitionTemplePathInfo = "${exhibitionTemplePathInfo}";	
	var targetUrl = '<c:url value="/exhibition/viewImageForm.do"/>'        
		//+"?imgUrl="+ exhibitionTemplePathInfo + '/temple' + selectVal + '.jpg';
		+"?imgUrl="+ exhibitionTemplePathInfo + '/temple' + selectVal + '.png';
		Common.centerPopupWindow(targetUrl, 'imageForm', {
			title : '이미지 미리보기 팝업', 
			width : 600, 
			height : 600, 
			scrollBars : 'NO'
		});
}


var gFocusIdx = 0;      //멀티행에 대한 인덱스값

function fn_strPopUp(idx) {
	gFocusIdx = idx;
	//openStr("M");
	openStr("M", "subStrCd"+gFocusIdx);
}

//점포팝업 반환값 셋팅
function fnSetStr(data) {
/* 	$("#subStrCd"+gFocusIdx).val(data.strCd);
	$("#subStrNm"+gFocusIdx).val(data.strNm); */

	var strCd = "", strNm = "";

	if(jQuery.isArray(data.strCdArr)){
		for(var i=0; i < data.strCdArr.length; i++ ){
			//console.log('strCd=>'+strCd);
			strCd += data.strCdArr[i]+',';
			strNm += data.strNmArr[i]+',';
		}
		if(strCd!="") strCd = strCd.substring(0, strCd.length-1);
		if(strNm!="") strNm = strNm.substring(0, strNm.length-1);

	}else{
		strCd = data.strCd;
		strNm = data.strNm;
	}

	$("#subStrCd"+gFocusIdx).val(strCd);
	$("#subStrNm"+gFocusIdx).val(strNm);

	//점포코드 수정여부체크
	var oldStrCd = $("#oldSubStrCd"+gFocusIdx).val();

	if(oldStrCd != strCd){
		$("#storeModifyYn"+gFocusIdx).val("Y");
	}else{
		$("#storeModifyYn"+gFocusIdx).val("N");
	}
}



</script>


</head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
<div id="content_wrap">

<div class="content_scroll">
<form name="form1" id="form1" method="post"  enctype="multipart/form-data">
<input type="hidden" id="pageDiv"    name="pageDiv"    value="<c:out value="${pageDiv }"/>"">

<input type="hidden" id="categoryId" name="categoryId" value="<c:out value="${categoryId}"/>" >
<%-- <input type="hidden" id="strCd"      name="strCd"      value="<c:out value="${strCd }"/>" > --%>
<input type="hidden" id="mkdpSeq"    name="mkdpSeq"    value="<c:out value="${mkdpSeq }"/>" >
<input type="hidden" id="vendorId"    name="vendorId"    value="<c:out value="${vendorId }"/>" >
<input type="hidden" id="aprvStsCdChk" name="aprvStsCdChk" value="<c:out value="${aprvStsCd }"/>"">
<input type="hidden" id="aprvStsCdCKK" name="aprvStsCdCKK" value="<c:out value="${aprvStsCdChk }"/>"">

<input type="hidden" id="mkdpStartDate" name="mkdpStartDate" value="<c:out value="${mkdpStartDate }"/>"">
<input type="hidden" id="mkdpStartHh" name="mkdpStartHh" value="<c:out value="${mkdpStartHh }"/>"">
<input type="hidden" id="mkdpStartMm" name="mkdpStartMm" value="<c:out value="${mkdpStartMm }"/>"">
<input type="hidden" id="mkdpEndDate" name="mkdpEndDate" value="<c:out value="${mkdpEndDate }"/>"">
<input type="hidden" id="mkdpEndHh" name="mkdpEndHh" value="<c:out value="${mkdpEndHh }"/>"">
<input type="hidden" id="mkdpEndMm" name="mkdpEndMm" value="<c:out value="${mkdpEndMm }"/>"">
<input type="hidden" id="dispYnChk" name="dispYnChk" value="<c:out value="${dispYn }"/>"">



   

<input type="hidden" id="delCount"      name="delCount"      value="" >
<input type="hidden" id="insCount"      name="insCount"      value="" >

<input type="hidden" id="idxList"    name="idxList"    value="" >

<input type="hidden" id="deleteItemDivnSeq" name="deleteItemDivnSeq" value="" >

	  
	  <ul id="divnInfoList">	  	  
		  <div class="wrap_search">
					<!-- 01 : search -->
					<div class="bbs_search">
						<ul class="tit">
							<li class="tit">하위 상품그룹명</li>
							<li class="btn">
							<%-- <c:if test="${aprvStsCdChk  eq '00' || aprvStsCdChk  eq '02'}"> --%>
							<c:if test="${aprvStsCdChk  eq '00' || aprvStsCdChk  eq '02'||aprvStsCdChk  eq '03' }">
	                        	<a href="javascript:fn_save();" class="btn" id="save"><span>저장</span></a>
							</c:if>
							   					     
							</li>
						</ul>

	      <table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
	        <col width="20%" />
	        <col width="30%" />
	        <col width="20%" />
	        <col width=" " />
	        <tr>
	        	<th><em>*</em>상품전시 템플릿 일괄적용</th>
	          	<td colspan="3">                                                                                    
					<select name="prodTemplateCdApply" id="prodTemplateCdApply" style="width:140px" title="상품전시 템플릿 일괄적용" > '; 
						<option value="">선택</option>                                                                   
						<c:forEach items="${goodsTemplateList }" var="value">                                                 
							<option value="${value.MINOR_CD }">${value.CD_NM }</option>                                       
						</c:forEach>                                 
					</select>
						
                       	<a href="javascript:fn_preview('1', null);"  class="btn" id="prev"><span>미리보기</span></a>
                       	<a href="javascript:fn_goodsTemplateApply()"  class="btn"  id="goodtmp"><span>적용</span></a>
						
	          	</td>
	        </tr>
	  		</table>	  		

	  <!-- 구분자 정보 아이템 영역 -->   
	  </ul>	  
	  <ul><li><a href="javascript:fn_addRemoveItem('1');"><img src="/images/epc/new/btn_a_plus.png" alt="추가" class="search"></img></a></li></ul>
	  </div>		
	  <!-- footer -->
	<div id="footer">
		<div id="footbox">
			<div class="msg" id="resultMsg"></div>
			<div class="location">
				<ul>
					<li>홈</li>
					<li>기획전관리</li>
					<li class="last">구분자 정보</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->  	 
	</div>
	


</form>
</body>
</html>