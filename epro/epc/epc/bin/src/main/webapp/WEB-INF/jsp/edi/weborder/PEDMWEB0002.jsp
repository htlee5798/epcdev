<%--
- Author(s): 
- Created Date: 2014. 08. 06
- Version : 1.0
- Description : 상품별 발주등록

--%>
<%@include file="../common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>

<script  type="text/javascript" >

		/* 폼로드 */
		$(document).ready(function($) {		
			$("select[name='entp_cd']").val("<c:out value='${param.entp_cd}'/>");	// 협력업체 선택값 세팅
		});

	$(function() {
		_init();
		
		/* BUTTON CLICK 이벤트 처리 ----------------------------------------------------------------------------*/
		$('#btnSave').unbind().click(null,	_eventTempSave); 		// 임시저장 이벤트
		$('#btnSelectDel').unbind().click(null,	_eventSelectDelete);  	// 선택삭제 이벤트
		$('#btnSearch').unbind().click(null,	_eventSearch); 				// 조회 이벤트
		/*-----------------------------------------------------------------------------------------------------*/
		
		// 권역구분, 점포코드 , 협력업체코드, 작업구분 enter key이벤트 --------------
		$('#areaCd,, #strCd, #entp_cd, #prodCd, #prodNm, input[name=workGb], input[name=pageRowCount]').unbind().keydown(function(e) {
			switch(e.which) {
	    	case 13 : _eventSearch(this); break; // enter
	    	default : return true;
	    	}
	    	e.preventDefault();
	   	});
		//-----------------------------------------------------------------
		
		// 점포 체크박스 전체 선택
		$('#allCheckStr').click(function(){
			if ($("#allCheckStr").is(":checked")) { 
				$('input:checkbox[id^=strBox]:not(checked)').attr("checked", true); 
			} else { 
				$('input:checkbox[id^=strBox]:checked').attr("checked", false); 
			} 
				
		});
		
		// 권역 셀렉트 박스 변경시 점포 코드 변경
		$("#areaCd").change(function () {
			var _majorCD = $("#areaCd").val();
			_storeSelectCodeOptionTag(_majorCD, "#strCd", "전체");
			
		});
		
		/* 페이지번호 Click Event 발생시 조회함수 호출하다. */
		$('#paging a').live('click', function(e) {
			// #page : 서버로 보내는 Hidden Input Value 
			$('#page').val($(this).attr('link'));
			// 개발자가 만든 조회 함수
			chkAllStrBox();
			_eventSearch();
		});

		/* 서브코드 목록 Click Event */
		$('#datalistProd tr').live('click', function(e){
			var obj = $(this).children().last();		// last-td
			var prodCd = $(obj).children("input[name='prodCd']").val();
			
			if(prodCd){
				_doDetailSearch(prodCd);
			}else{
				return false;
			}
			
		});
		
		//입력필드 CHANGE  EVENT ---------------------------------------------------------
		$("#entp_cd").change(function () { _eventSelectVendorException();});  // 업체코드 셀렉트 박스 변경시 점포 코드 변경
		//------------------------------------------------------------------
		
		// 협력업체 코드로 상품 수량이 많은 업체 인지 조회
		_eventSelectVendorException();
	});


	/* 초기 설정 */
	function _init(){
		_setTbodyNoResult($("#datalistProd"), 	7, '지역/점포, 협력업체 코드, 상품코드, 작업구분을  선택 또는 입력하여 [조회] 하세요!');	// prod tBody 설정
		_setTbodyNoResult($("#datalistStr"),    7, '상품정보를 선택 하세요!');						// store VEN tBody 설정
	}
	
	function _eventSelectVendorException(){
		var str = {"venCd" 		: $("#entp_cd").val() };
		
		loadingMaskFixPos();
		
		$.ajaxSetup({
	  		contentType: "application/json; charset=utf-8" 
			});
		$.post(
				"<c:url value='/edi/weborder/vendorExceptionSelect.do'/>",
				JSON.stringify(str),
				function(data){
					if(data == null || data.state !="0"){
						hideLoadingMask();
						//자료조회중 오류가 발생하였습니다.[CODE:data.state]
						alert('협력업체 삼품 수량 조회 중 오류가 발생 하였습니다');		
					}
					else {
						hideLoadingMask();
						$('#vendorFlag').val(data.vendorFlag);
					}	
				},	"json"
			);
	}
	
	// 전체 체크박스 해제
	function chkAllStrBox(){
		$("input:checkbox[name=allCheckStr]").attr("checked", false);
	}
	
	// 선택 삭제
	function _eventSelectDelete(){
		if (!confirm('<spring:message code="msg.common.confirm.select.delete"/>')) {
            return;
        }
		
		var strDataList = [];
		
		var venCd = $('#schVenCd').val();
		var prodCd = $('#schProdCd').val();
		
		$("#datalistStr tr").each(function (index){
			// 점포 정보  ------------------------------------------
			var strData= {};
			$(this).find('input').map(function() {
				if(this.name == 'strBox'){
					if($(this).is(":checked")){
						strData['venCd'] = venCd;
						strData['prodCd'] = $(this).parent().parent().children('td:last').children('input[name="prodCd"]').attr('value');
						strData['strCd'] = $(this).parent().parent().children('td:last').children('input[name="strCd"]').attr('value');
						strData['ordDy'] = $(this).parent().parent().children('td:last').children('input[name="ordDy"]').attr('value');
						strDataList.push(strData);
					}
				}
			});
		});
		
		
		if(strDataList.length == 0){
			alert('<spring:message code="msg.common.error.no.data"/>');
			return;
		}
		
		loadingMaskFixPos();
		
		$.ajaxSetup({
	  		contentType: "application/json; charset=utf-8" 
			});
	 	$.post("<c:url value='/edi/weborder/tedProdOrdDelete.do'/>"
	 			,JSON.stringify({'tedOrdList001VO' : strDataList})
	  			,function(json){				
					if(jQuery.trim(json) == ""){//처리성공
						hideLoadingMask();
						alert('<spring:message code="msg.common.success.delete"/>');
						_doDetailSearch(prodCd);
					}else{
						alert(json);
						hideLoadingMask();
					}
	  			}, 'json');
	 	
	}
		
	// 현재 시간 조회
	function getCurrentTime(){
		today = new Date(); 
		
		var newToDay;
		var hours = today.getHours();
		var minutes = today.getMinutes();
		
		if (hours == 0) hours = 24;
		if (hours <= 9) hours = "0" + hours;
		if (minutes <= 9) minutes = "0" + minutes;
		
		newToDay = hours+""+minutes;
		
		return newToDay;
	}
	
	// 임시저장
	function _eventTempSave(){
		var webOrdFrDt = $('#vendorWebOrdFrDt').val();
		var webOrdtoDt = $('#vendorWebOrdToDt').val(); 
		
		var nowTime = getCurrentTime();
		
		if( Number(nowTime) < Number(webOrdFrDt) || Number(nowTime) > Number(webOrdtoDt) ){
			alert("발주/반품 가능 시간은"+'<spring:message code="msg.weborder.vendor.send.time"/>'+"까지 입니다.");
			return;
		}
		
		if (!confirm('<spring:message code="msg.common.confirm.save"/>')) {
            return;
        }
		
		var strDataList = [];
		var venCd = $('#schVenCd').val();
		var prodCd = $('#schProdCd').val();
		var chkValidte = true;
		var chkQty = null;
		
		$("#datalistStr tr").each(function (index){
			// 상품 정보  ------------------------------------------
			var strData= {};
			var ordQty = $(this).find('input[name=ordQty]').attr('value');
			var oldOrdQty = $(this).find('input[name=oldOrdQty]').attr('value');
			
			if(ordQty != null && ordQty != "" && ordQty != oldOrdQty){
				$(this).find('input').map(function() {
					if(this.type != 'button' || this.type != 'checkbox'){
						if(this.name != 'oldOrdQty'){
							if(this.name != 'strBox'){
								if(this.name == 'ordQty' || this.name == 'ordBuyPrc' || this.name == 'ordIpsu' || this.name == 'ordTotPrc' || this.name == 'ordTotAllQty'){
									
									chkQty = (Number($(this).val().replace(/\,/g,"")));
									
									if(!chkQty > 0 ) {
										chkValidte = false;
									}else{
										strData[this.name] = $(this).val().replaceAll(",","");
									}
								}else{
									strData[this.name] = $(this).val();
								}
							}
						}
					}
				});
				
				strData['prodCd'] = prodCd;
				strData['venCd'] = venCd;
				strDataList.push(strData);
			}
		});
		
		if(strDataList.length == 0){
			alert('<spring:message code="msg.common.error.no.data"/>');
			return;
		}
		

		if(!chkValidte){
			alert("발주 수량을 확인해 주십시요.");
			return;
		}
		
		loadingMaskFixPos();
		
		$.ajaxSetup({
	  		contentType: "application/json; charset=utf-8" 
			});
	 	$.post("<c:url value='/edi/weborder/tedProdOrdTempSave.do'/>"
	  			,JSON.stringify({'tedOrdList001VO' : strDataList})
	  			,function(json){				
					if(jQuery.trim(json) == ""){//처리성공
						hideLoadingMask();
						alert('<spring:message code="msg.common.success.insert"/>');
						//_eventSearch();
						_doDetailSearch($('#schProdCd').val());
					}else{
						alert(json);
						hideLoadingMask();
					}
	  			}, 'json');
	}
	
	// 점포별 상세 상품 목록을 조회
	function _doDetailSearch(prodCd){
		$('#datalistProd tr').css("background-color","ffffff");
		$('#prodTr_'+prodCd).css("background-color","99FFFF");
		
		var str = { "venCd" 	: $("#schVenCd").val(),
					"prodCd"	: prodCd,
					"areaCd"    : $('#schAreaCd').val(),
					"strCd"		: $('#schStrCd').val()
					
		};
		
		loadingMaskFixPos();
		
		$.ajaxSetup({
	  		contentType: "application/json; charset=utf-8" 
			});
		$.post(
				"<c:url value='/edi/weborder/tedProdOrdDetailSelect.do'/>",
				JSON.stringify(str),
				function(data){
					$('#schProdCd').val(prodCd);
					_setTbodyProdOrdDetailValue(data);
					chkAllStrBox();
					hideLoadingMask();
				},	"json"
			);
	}
	
	// 점포별 상세 상품 목록 리스트에 뷰.
	function _setTbodyProdOrdDetailValue(json){
		_setTbodyStrInit();
		
		var data = json.list, eleHtml = [], ordCntSum = json.totCnt, h = -1;
		
		if(data != null){
			var sz = json.list.length;
			if (sz > 0) {
				for ( var k = 0; k < sz; k++) {
					var cnt = k+1;
					var ordTotAllQty=0, ordTotPrc=0;
					
					if(data[k].ordQty  != null){
						ordTotAllQty = data[k].ordIpsu * data[k].ordQty;
						ordTotPrc = data[k].ordIpsu * data[k].ordQty * data[k].ordBuyPrc;
					}
					
					eleHtml[++h] = '<tr bgcolor=ffffff>' + "\n";
					eleHtml[++h] = "\t" + '<td align="center">'+cnt+'</td>' + "\n";
					if(data[k].ordQty  != null){
						eleHtml[++h] = "\t" + '<td align="center"><input type="checkbox" id="strBox" name="strBox" onclick="javascript:chkAllStrBox()"></td>' + "\n";
					}else{
						eleHtml[++h] = "\t" + '<td align="center"><input type="checkbox" id="strBox" name="strBox" onclick="javascript:chkAllStrBox()" disabled="disabled"></td>' + "\n";
					}
					
					eleHtml[++h] = "\t" + '<td align="center" class="dot_web0001">('+data[k].strCd+')'+data[k].strNm+'</td>' + "\n";
					if(data[k].ordQty == null){
						eleHtml[++h] = "\t" + '<td align="right"><input type="text" tabindex="'+cnt+'" id="ordQty" name="ordQty"  maxlength="10" style="text-align: right; width:45px;" onkeypress="onlyNumber();" onblur="amtFormat(this); sumOrdInfo(this);" onFocus="unNumberFormat(this);" />' + "\n";
						eleHtml[++h] = "\t" + '<input type="hidden" id="oldOrdQty" name="oldOrdQty" value="'+data[k].ordQty+'" ></td>' + "\n";					
					}else{
						eleHtml[++h] = "\t" + '<td align="right"><input type="text" tabindex="'+cnt+'" id="ordQty" name="ordQty" maxlength="10" value="'+data[k].ordQty+'" style="text-align: right; width:45px;" onkeypress="onlyNumber();" onblur="amtFormat(this); sumOrdInfo(this);" onFocus="unNumberFormat(this);"/>' + "\n";
						eleHtml[++h] = "\t" + '<input type="hidden" id="oldOrdQty" name="oldOrdQty" value="'+data[k].ordQty+'" ></td>' + "\n";
					}
					
					eleHtml[++h] = "\t" + '<td align="right"><input class="inputReadOnly" type="text" id="ordTotAllQty" name="ordTotAllQty" value="'+amtComma(ordTotAllQty)+'" style="text-align: right; color: blue; width:44px;" readonly="readonly"/></td>' + "\n";
					eleHtml[++h] = "\t" + '<td align="right"><input class="inputReadOnly" type="text" id="ordTotPrc" name="ordTotPrc" value="'+amtComma(ordTotPrc)+'" style="text-align: right; color: blue; width:80px;" readonly="readonly"/>' + "\n";
					eleHtml[++h] = "\t" + '<input type="hidden" name="strCd" value='+data[k].strCd+'>' + "\n";
					eleHtml[++h] = "\t" + '<input type="hidden" name="ordDy" value='+data[k].ordDy+'>' + "\n";
					eleHtml[++h] = "\t" + '<input type="hidden" name="ordIpsu" value='+data[k].ordIpsu+'>' + "\n";
					eleHtml[++h] = "\t" + '<input type="hidden" name="venNm" value="'+data[k].venNm+'">' + "\n";
					eleHtml[++h] = "\t" + '<input type="hidden" name="prodCd" value="'+data[k].prodCd+'">' + "\n";
					eleHtml[++h] = "\t" + '<input type="hidden" name="ordReqNo" value='+data[k].ordReqNo+'>' + "\n";
					eleHtml[++h] = "\t" + '<input type="hidden" name="ordBuyPrc" value='+data[k].ordBuyPrc+'></td>' + "\n";
					eleHtml[++h] = "\t" + '</tr>' + "\n";
					
					cnt++;
					
				}
				for (var j=sz; j<18; j++) {
					eleHtml[++h] = '<tr bgcolor=ffffff style="height:20px;"><td></td><td></td><td></td><td></td><td></td><td></td></tr>' + "\n";
				}
				
				$("#datalistStr").append(eleHtml.join(''));
			}
		}else{
			_setTbodyNoResult($("#datalistStr"), 7, null );
		}
		
		
		if(ordCntSum.ordTotQtySum == null || ordCntSum.ordTotQtySum == "") $("#ordTotQtySum").text("0");
		else $("#ordTotQtySum").text(amtComma(ordCntSum.ordTotQtySum));
		if(ordCntSum.ordTotAllQtySum == null || ordCntSum.ordTotAllQtySum == "") $("#ordTotAllQtySum").text("0");
		else $("#ordTotAllQtySum").text(amtComma(ordCntSum.ordTotAllQtySum));
		if(ordCntSum.ordTotPrcSum == null || ordCntSum.ordTotPrcSum == "") $("#ordTotPrcSum").text("0");
		else $("#ordTotPrcSum").text(amtComma(ordCntSum.ordTotPrcSum));
		
	}
	
	// 업체코드별 발주 가능 점포 목록 조회
	function _eventSearch(){
		var vendorFlag = $('#vendorFlag').val();
		
		if(vendorFlag == 'T'){
			if(!$.trim($("#prodCd").val()) && !$.trim($("#prodNm").val()) ){
				alert("상품코드 또는 상품명 중 하나이상의 검색조건을 입력하세요!");
				return false;
			}
		}
		
		if($.trim($("#prodCd").val()) && $.trim($("#prodCd").val()).length < 5) {
			alert("상품코드를 이용하여 조회할 경우 5자이상  검색조건 입력하세요!");
			$("#prodCd").focus();
			return false;
		}
		
		var str = { "venCd" 		: $("#entp_cd").val(),
					"strCd"			: $("#strCd").val(),
					"areaCd"		: $("#areaCd").val(),
					"prodCd"		: $("#prodCd").val(),
					"prodNm"		: $("#prodNm").val(),
					"page"			: $("#page").val(),
					"workGb"		: $('input[name="workGb"]:radio:checked').val(),
				    "pageRowCount"	: $('input[name="pageRowCount"]:radio:checked').val()
		};
		
		loadingMaskFixPos();
		
		$.ajaxSetup({
	  		contentType: "application/json; charset=utf-8" 
			});
		$.post(
				"<c:url value='/edi/weborder/tedProdOrdSelect.do'/>",
				JSON.stringify(str),
				function(data){
					$('#schVenCd').val($("#entp_cd").val());
					$('#schAreaCd').val($("#areaCd").val());
					$('#schStrCd').val($("#strCd").val());
					
					_setTbodyProdOrdValue(data);
					
					hideLoadingMask();
				},	"json"
			);
	}
	
	// 발주 가능 상품 목록 리스트에 뷰
	function _setTbodyProdOrdValue(json) {
		_setTbodyMstInit();

		var data = json.list, eleHtml = [], h = -1, pagHtml = [], j = -1;
		
		if(data != null){
			var sz = json.list.length;
			if (sz > 0) {
				for ( var k = 0; k < sz; k++) {
					eleHtml[++h] = '<tr bgcolor=ffffff id="prodTr_'+data[k].prodCd+'" style="cursor:pointer;">' + "\n";
					eleHtml[++h] = "\t" + '<td align="center">'+data[k].rowNum+'</td>' + "\n";
					eleHtml[++h] = "\t" + '<td align="left">&nbsp;'+data[k].prodCd+'</td>' + "\n";
					eleHtml[++h] = "\t" + '<td align="left">&nbsp;'+data[k].srcmkCd+'</td>' + "\n";
					eleHtml[++h] = "\t" + '<td align="left" class="dot_web0002" title="'+data[k].prodNm+'"><span>'+data[k].prodNm+'</span></td>' + "\n";
					eleHtml[++h] = "\t" + '<td align="right">'+data[k].ordIpsu+'&nbsp;</td>' + "\n";
					eleHtml[++h] = "\t" + '<td align="center">'+data[k].ordUnit+'' + "\n";
					eleHtml[++h] = "\t" + '<input type="hidden" name="ordDy" value='+data[k].ordDy+'>' + "\n";
					eleHtml[++h] = "\t" + '<input type="hidden" name="prodCd" value="'+data[k].prodCd+'"></td>' + "\n";
					eleHtml[++h] = "\t" + '</tr>' + "\n";
					
					$('#schOrdDy').val(data[k].ordDy);
				}
				
				for (var j=sz; j<20; j++) {
					eleHtml[++h] = '<tr bgcolor=ffffff style="height:20px;"><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>' + "\n";
				}
				
				$("#datalistProd").append(eleHtml.join(''));
				_setTbodyNoResult($("#datalistStr"), 7, "상품정보를 선택 하세요." );
			}
			
		}else {
			_setTbodyNoResult($("#datalistProd"), 7, null );
		}
		
		var page =  json.paging.list;
		var pageSz = json.paging.list.length;
		
		if(pageSz > 0){
			for ( var m = 0; m < pageSz; m++) {
				if (page[m].pageNumber == '<<'){
					pagHtml[++j] = '<a href="javascript:;" class="btn" link="'+page[m].linkPageNumber+'"><img src="/images/page/icon_prevend.gif" alt="처음" /></a>' + "\n";
				} else if (page[m].pageNumber == '<'){
					pagHtml[++j] = '<a href="javascript:;" class="btn" link="'+page[m].linkPageNumber+'"><img src="/images/page/icon_prev.gif" alt="이전" /></a>' + "\n";
				}else if(page[m].pageNumber == '>'){
					pagHtml[++j] = '<a href="javascript:;" class="btn" link="'+page[m].linkPageNumber+'"><img src="/images/page/icon_next.gif" alt="다음" /></a>' + "\n";
				}else if(page[m].pageNumber == '>>'){
					pagHtml[++j] = '<a href="javascript:;" class="btn" link="'+page[m].linkPageNumber+'"><img src="/images/page/icon_nextend.gif"  alt="마지막" /></a>' + "\n";
				}else{
					pagHtml[++j] = '<a href="javascript:;" class="'+page[m].cl+'" link="'+page[m].linkPageNumber+'" title="'+page[m].pageNumber+'">'+page[m].pageNumber+'</a>' + "\n";
				}
			}
			$("#paging").append(pagHtml.join(''));
		}
		
	}
	
	/*목록 검색 결과 없을시  */
	function _setTbodyNoResult(objBody, col, msg) {
		if(!msg) msg = "조회된 데이터가 없습니다.";
		objBody.append("<tr><td bgcolor='#ffffff' colspan='"+col+"' align=center>"+msg+"</td></tr>");
	}
	
	/* 상품 목록 초기화 */
	function _setTbodyMstInit() {
		$("#datalistProd tr").remove();
		$("#datalistStr tr").remove();
		$("#paging").empty();
		$("#ordTotQtySum").text("");
		$("#ordTotAllQtySum").text("");
		$("#ordTotPrcSum").text("");
	}
	
	/* 목록 초기화 */
	function _setTbodyStrInit() {
		$("#datalistStr tr").remove();
		$("#ordTotQtySum").text("");
		$("#ordTotAllQtySum").text("");
		$("#ordTotPrcSum").text("");
	}

	// 단위수량 입력시 EA 와 금액 계산
	function sumOrdInfo(obj){
		// 상품별 합계
		var ordQty = $(obj).parent().parent().children().children('input[name="ordQty"]').attr('value');
		var ordIpsu = $(obj).parent().parent().children('td:last').children('input[name="ordIpsu"]').attr('value');
		var ordBuyPrc = $(obj).parent().parent().children('td:last').children('input[name="ordBuyPrc"]').attr('value');
		var chkQty;
		
		ordQty = ordQty.replaceAll(",","");
		ordIpsu = ordIpsu.replaceAll(",","");
		ordBuyPrc = ordBuyPrc.replaceAll(",","");
		chkQty = (Number(ordQty.replace(/\,/g,"")));
		
		if(!isNumber(ordQty.replace(/\,/g,"")) || (ordQty != ""  && !chkQty > 0)) {
			alert("정상적인발주수량(EA)을 입력하세요!");
			$(obj).val("");
			$(obj).focus();
			return;	
		}
		
		$(obj).parent().parent().children().children('input[name="ordTotAllQty"]').val(amtComma(ordIpsu*ordQty));
		$(obj).parent().parent().children().children('input[name="ordTotPrc"]').val(amtComma(ordBuyPrc*ordIpsu*ordQty));
		
		// 총 합계
		var ordTotQty = 0, ordTotAllQty = 0, ordTotPrc = 0;
	
		$("input[id=ordQty]").each(function (index){
			var totQtySum = $(this).val();
			ordTotQty = Number(ordTotQty)+Number(totQtySum.replaceAll(",",""));
		});
		
		$("input[id=ordTotAllQty]").each(function (index){
			var totEaSum = $(this).val();
			ordTotAllQty = Number(ordTotAllQty)+Number(totEaSum.replaceAll(",",""));
		});
		
		$("input[id=ordTotPrc]").each(function (index){
			var totAmtSum = $(this).val();
			ordTotPrc = Number(ordTotPrc)+Number(totAmtSum.replaceAll(",",""));
		});
		
	 	$('#ordTotQtySum').text(amtComma(ordTotQty));
		$('#ordTotAllQtySum').text(amtComma(ordTotAllQty));
		$('#ordTotPrcSum').text(amtComma(ordTotPrc));
	}
	
	/* 권역별 점포코드 세팅 */
	function _storeSelectCodeOptionTag(commonMajorCode, toSelectTagID, firstOptionMessage, finallyMethod) {
		var str = { "majorCD": commonMajorCode };
		
		$.ajaxSetup({
	  		contentType: "application/json; charset=utf-8" 
			});
		$.post(
				"<c:url value='/CommonCodeHelperController/storeSelectList.do'/>",
				JSON.stringify(str),
				function(data){
					var json = _jsonParseCheck(data);
					if(json==null)
					{
						return;
					}
					$(toSelectTagID + " option").remove();
					if(firstOptionMessage == "none" || firstOptionMessage == null || firstOptionMessage == "null")
					{
						$(toSelectTagID).append(json);
					}
					else
					{
						$(toSelectTagID).append("<option value=''>" + firstOptionMessage + "</option>").append(json);
					}
					if(finallyMethod != null)
					{
						finallyMethod();
					}
				},	"text"
			);
	}
	
	/*  JSON 파싱 체크*/
	function _jsonParseCheck(jsonStr){
		//jsonParseCheck(jsonStr);
		
		//alert("jsonStr : " + jsonStr);
		if(jsonStr == null || jsonStr == "") {
			return null;
		}
	
		var json = JSON.parse(jsonStr);
		
		if(json==null){
			return null;
		}
		
		var resultCd = json.__RESULT__;
		if(resultCd==null || resultCd!="NG"){
			return json;
			
		}else{
			var errCd = json.__ERR_CD__;
			var errMsg = json.__ERR_MSG__;
			
			alert("[ 에 러 ]\n" + errMsg + ", Code : " + errCd);
			return null;
		}
		
	}
	
	
	//-----------------------------------------------------------------------
	//숫자만 입력가능하도록
	//-----------------------------------------------------------------------
	//예)
	//html : <input type='text' name='test' onkeypress="onlyNumber();">
	//-----------------------------------------------------------------------
	function onlyNumber()
	{

		if((event.keyCode<48) || (event.keyCode>57))
		{
			event.returnValue=false;
		}
	}
	
	//금액 콤마 - value 계산용
	function amtComma(amt) {
	    var num = amt + '';
	    for(var regx = /(\d+)(\d{3})/;regx.test(num); num = num.replace(regx, '$1' + ',' + '$2'));
	    return num;
	}

	//금액 콤마 - onkeyup용 
	function amtFormat(obj) {
	    var num = obj.value + '';
	    for(var regx = /(\d+)(\d{3})/;regx.test(num); num = num.replace(regx, '$1' + ',' + '$2'));
	    obj.value = num;
	}

	//금액 콤마 제거
	function unNumberFormat(obj) {
		var num = obj.value;
		obj.value =  (num.replace(/\,/g,""));
	}
	
	// 로딩바 감추기
	function hideLoadingMask(){
		$('#loadingLayer').remove();
		$('#loadingLayerBg').remove();
	}
	
	//숫자검사
	function isNumber(str) {
		var chars = "0123456789";
		return containsCharsOnly(str, chars);
	}
	//Chars 검사
	function containsCharsOnly(input,chars) {
	   for (var inx = 0; inx < input.length; inx++) {
	       if (chars.indexOf(input.charAt(inx)) == -1)
	           return false;
	    }
	    return true;
	}
</script>

<style>
.dot_web0001 span{display:block;overflow:hidden;width:90px;height:18px;white-space:nowrap;text-overflow:ellipsis;-o-text-overow: ellipsis;-moz-binding:url(js/ellipsis.xml#ellipsis)undefinedundefinedundefined}
.dot_web0002 span{display:block;overflow:hidden;width:130px;height:18px;white-space:nowrap;text-overflow:ellipsis;-o-text-overow: ellipsis;-moz-binding:url(js/ellipsis.xml#ellipsis)undefinedundefinedundefined}

.page { float:right;  margin-top: 0px; padding:0 0 0 0; text-align:center; }
.page img { vertical-align:middle;}
.page a { display:inline-block; width:15px; height:11px; padding:4px 0 0; text-align:center; border:1px solid #efefef; background:#fff; vertical-align:top; color:#8f8f8f; line-height:11px;}
.page a.btn,
.page a.btn:hover { width:auto; height:auto; padding:0; border:0; background:none;}
.page a.on,
.page a:hover {background:#518aac; font-weight:bold; color:#fff;}

</style>

</head>

<body>

	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>  >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form name="searchForm" method="post" action="#">
		<input type="hidden" id="widthSize" name="widthSize" value="${param.widthSize }" > 
		<input type="hidden" name="searchFlow" value="yes" />
		<input type="hidden" name="staticTableBodyValue">
		<input type="hidden" name="name">
		<input type="hidden" name="new_prod_id">
		<input type="hidden" name="vencd">
		<input type="hidden" name="proGu" />
		<input type="hidden" name="page" id="page" value="1" />
		<input type="hidden" name="schVenCd" id="schVenCd" />
		<input type="hidden" name="schOrdDy" id="schOrdDy" />
		<input type="hidden" id="schAreaCd" name="schAreaCd" />
		<input type="hidden" id="schStrCd" name="schStrCd" />
		<input type="hidden" id="schProdCd" name="schProdCd" />
		<input type="hidden" name="vendorFlag" id="vendorFlag" />
		<input type="hidden" name="vendorWebOrdFrDt" id="vendorWebOrdFrDt" value="${paramMap.vendorWebOrdFrDt}"/>
		<input type="hidden" name="vendorWebOrdToDt" id="vendorWebOrdToDt" value="${paramMap.vendorWebOrdToDt}"/>
		
		<div id="wrap_menu">
			<!--	@ 검색조건	-->
			<!-- 01 : search -->
			<div class="bbs_search">
				<ul class="tit">
					<li class="tit">검색조건
						<span style="font-weight: normal; color: #414fbb; margin-left: 25px;"><img src="/images/epc/btn/icon_04.png" alt="Notice" />&nbsp; 발주가능 시간은 <span style="color: red;"><spring:message code="msg.weborder.vendor.send.time"/> </span>입니다.</span>
					</li>
					<li class="btn">
						<%-- <a href="#" class="btn" id="btnSave"><span><spring:message code="button.common.save"/></span></a> --%>
						<%-- <a href="#" class="btn" id="btnSelectDel"><span><spring:message code="button.weborder.select.delete"/></span></a>&nbsp;&nbsp;&nbsp; --%>
						<a href="#" class="btn" id="btnSearch"><span><spring:message code="button.common.inquire"/></span></a>
					</li>
				</ul>
				<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
				
				<input type="hidden" id="storeVal" name="storeVal"  value="${param.storeVal }" />
				<input type="hidden" id="productVal" name="productVal" />
				<input type="hidden" id="entpCode" name="entpCode" />
				
				<colgroup>
					<col style="width:15%" />
					<col style="width:35%" />
					<col style="width:15%" />
					<col style="*" />
				</colgroup>
				<tr>
					<th>지역/점포</th>
					<td>
						<html:codeTag objId="areaCd" objName="areaCd" width="75px;" formName="form" dataType="AREA" comType="select"  defName="전체"  />
						<select name="strCd" id="strCd" style="width:120px">
							<option value="">전체</option>
						</select>
					</td>
					<th>협력업체 코드</th>
					<td>
						<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" formName="form" selectParam="" dataType="CP" comType="SELECT" />
					</td>
				</tr>
				<tr>
					<th>상품코드/상품명</th>
					<td>
						<input type="text" id="prodCd" style="width: 100px;" name="prodCd" value="${paramMap.prodCd}"> / <input type="text" id="prodNm" name="prodNm" value="${paramMap.prodNm}">
					</td>
					<th>작업구분</th>
					<td>
						<input type="Radio" name="workGb" value="1" <c:if test="${paramMap.workGb eq '1'}"> Checked</c:if> /> 전체
						<input type="Radio" name="workGb" value="2" <c:if test="${paramMap.workGb eq '2'}"> Checked</c:if> /> 등록
						<input type="Radio" name="workGb" value="3" <c:if test="${paramMap.workGb eq '3'}"> Checked</c:if> /> 미등록
					</td>
				</tr>
				</table>
			</div>
			<!-- 1검색조건 // -->
			
			
			<table cellpadding="0" cellspacing="0" border="0">
				<colgroup>
					<col width="55%"/>
					<col width="2px"/>
					<col width="45%"/>
				</colgroup>
				<tr>
					<td valign="top">
						<div class="bbs_list" style="margin-top: 2px;">	
							<ul class="tit">
								<li class="tit">발주등록 가능 상품 List</li>
							</ul>
							<table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=100% bgcolor=efefef height="432px;">
									<colgroup>
										<col width="27px" />
										<col width="70px" />
										<col width="95px" />
										<col width="*" />
										<col width="46px" />
										<col width="46px" />
										<col width="18px"/>
									</colgroup>
									<thead>
										<tr bgcolor="#e4e4e4" align=center>
											<th>No.</th>
											<th>상품코드</th>
											<th>판매코드</th>
											<th>상품명</th>
											<th>입수</th>
											<th>단위</th>
											<th></th>
										</tr>
									</thead>
									<tr> 
				                     	<td colspan=7>   
				                        	<div id="_dataList1" style="background-color:#FFFFFF; margin: 0; padding: 0; height:410px; overflow-y: scroll; overflow-x: hidden">
				                        		<table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=100% bgcolor="#EFEFEF">
				                     			<colgroup>
													<col width="25px" />
													<col width="70px" />
													<col width="95px" />
													<col width="*" />
													<col width="46px" />
													<col width="46px" />
												</colgroup>
												 <tbody id="datalistProd" />
				                     			</table>
				                     		</div>
				                     	</td>
				                	</tr>
				               </table>
				              </div>
						</td>
						
						<td style="border-left-color: red;">&nbsp;</td>
									
						<td valign="top">
							<div class="bbs_list" style="margin-top: 2px;">	
								<ul class="tit">
									<li class="tit">발주등록 가능 점포 List</li>
								</ul>
								<table id="dataTable" width="100%" cellpadding="1" cellspacing="1" border="0" bgcolor=efefef>
									<colgroup>
										<col width="29px"/>
										<col width="38px"/>
										<col width="*"/>
										<col width="47px"/>
										<col width="46px"/>
										<col width="82px"/>
										<col width="18px"/>
									</colgroup>	
									<thead>
			                        <tr bgcolor="#e4e4e4" align=center> 
			                          <th>No.</th>
			                          <th>삭제<br><input type="checkbox" id="allCheckStr" name="allCheckStr"></th>
			                          <th>점포</th>
			                          <th>단위<br>수량</th>
			                          <th>EA</th>
			                          <th>금액</th>
			                          <th rowspan="2"></th>
			                        </tr>
			                         <tr bgcolor="87CEFA">
										<td align="center" colspan="3">전점합계</td>
										<td align="right" style="font-weight: bold; font-size: 12px;"><span id="ordTotQtySum"></span></td>
										<td align="right" style="color: red; font-weight: bold; font-size: 12px;"><span id="ordTotAllQtySum"></span></td>
										<td align="right" style="color: red; font-weight: bold; font-size: 12px;"><span id="ordTotPrcSum"></span></td>
			                    	</tr>
			                        </thead>
			                        <tr> 
			                     		<td colspan=7>   
				                        	<div id="_dataList1" style="background-color:#FFFFFF; margin: 0; padding: 0; height:370px; overflow-y: scroll; overflow-x: hidden">
				                        		<table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=100% bgcolor="#EFEFEF">
				                     			<colgroup>
													<col width="28px"/>
													<col width="38px"/>
													<col width="*"/>
													<col width="47px"/>
													<col width="46px"/>
													<col width="82px"/>
												</colgroup>
												   <tbody id="datalistStr" />
				                     			</table>
				                     		</div>
				                     	</td>
				               		 </tr>
								</table>
							</div>
						</td>
					</tr>
					<tr><td colspan="3" height="2"></td></tr>
				</table>
		</div>
		<!-- Paging start ----------------------------------------------------->
		<div class="bbs_search" style="margin-top: 1px;">
		<table id="dataTable" cellpadding="1" cellspacing="1" border="0" bgcolor=efefef width="100%">
		<tr><td height="20">
		<span><strong>PageRow : </strong>
			<input type="radio" name="pageRowCount" id="pageRowCount" value="20" checked="checked">20
			<input type="radio" name="pageRowCount" id="pageRowCount" value="40">40
			<input type="radio" name="pageRowCount" id="pageRowCount" value="60">60
			<input type="radio" name="pageRowCount" id="pageRowCount" value="80">80
			<input type="radio" name="pageRowCount" id="pageRowCount" value="100">100
		</span>
		<div  align="center" style="margin-right: 50px; font-weight: bold;" id="paging" class="page"></div>
		</td></tr>
		</table>
		</div>
		<!-- Paging end ----------------------------------------------------->
		</form>
	</div>


	<!-- footer -->
	<div id="footer">
		<div id="footbox">
			<div class="msg" id="resultMsg"></div>
			<div class="notice"></div>
			<div class="location">
				<ul>
					<li>홈</li>
					<li>웹발주</li>
					<li>발주등록</li>
					<li class="last">상품별 발주등록 </li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>
<font color='white'><b>PEDMWEB0002.jsp</b></font>

</html>
