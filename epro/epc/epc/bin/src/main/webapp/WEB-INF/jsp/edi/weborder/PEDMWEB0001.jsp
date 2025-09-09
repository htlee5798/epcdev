<%--
- Author(s): 
- Created Date: 2014. 08. 04
- Version : 1.0
- Description : 점포별 발주등록

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
		$('#btnSave').unbind().click(null,			_eventTempSave); 			// 임시저장 이벤트
		$('#btnSelectDel').unbind().click(null,		_eventSelectDelete);  		// 선택삭제 이벤트
		$('#btnSearch').unbind().click(null,		_eventSearch); 				// 조회 이벤트
		$('#btnSearchStore').unbind().click(null,	_eventSearchStore); 		// 협력사발주가능 점포찾기팝업 이벤트
		
		$('#btnClearStore').dblclick( function () { _eventClearStore('D'); }); 	// STORE 조건 초기화 이벤트 (double Click)
		$('#btnClearStore').click( function ()    { _eventClearStore('S'); }); 	// STORE 조건 초기화 이벤트 (one    Click)
		
		/*-----------------------------------------------------------------------------------------------------*/
		
		// 권역구분, 점포코드 , 협력업체코드, 작업구분 enter key이벤트 --------------
		$('#areaCd,, #strCd, #entp_cd, #prodCd, #prodNm, input[name=workGb]').unbind().keydown(function(e) {
			switch(e.which) {
	    	case 13 : _eventSearch(this); break; // entergroupRows
	    	default : return true;
	    	}
	    	e.preventDefault();
	   	});
		//-----------------------------------------------------------------
		
		// 체크박스 전체 선택
		$('#allCheck').click(function(){
			if ($("#allCheck").is(":checked")) { 
				$('input:checkbox[id=box]:not(checked)').attr("checked", true); 
			} else { 
				$('input:checkbox[id=box]:checked').attr("checked", false); 
			} 
			
			$('input:checkbox[id=box]:checked').each(function() {
				if($(this).attr("disabled")){
					$(this).attr("checked", false);
				}
			});
		}); 
		
		// 권역 셀렉트 박스 변경시 점포 코드 변경
		$("#areaCd").change(function () {
			var _majorCD = $("#areaCd").val();
			_storeSelectCodeOptionTag(_majorCD, "#strCd", "선택");
			
		});
		
		// 점포 셀렉트 박스 변경시 다중점포 코드 초기화
		$("#strCd").change(function () {
			_eventClearStore('D');
		});
		
		
	 	//입력필드 CHANGE  EVENT ---------------------------------------------------------
		$("#entp_cd").change(function () { _eventSelectVendorException();});  // 업체코드 셀렉트 박스 변경시 점포 코드 변경
		//-------------------------------------------------------------------------------
		
		/* 페이지번호 Click Event 발생시 조회함수 호출하다. */
		$('#paging a').live('click', function(e) {
			// #page : 서버로 보내는 Hidden Input Value 
			$('#page').val($(this).attr('link'));
			// 개발자가 만든 조회 함수
			_eventSearch();
		});
		
		// 전체 체크 박스 초기화
		$("input[name=allCheck]").attr("disabled",true);
		
		// 협력업체 코드로 상품 수량이 많은 업체 인지 조회
		_eventSelectVendorException();
	});
	
	/* 초기 설정 */
	function _init(){
		_setTbodyNoResult($("#datalist"), 	11, '지역/점포, 협력업체 코드, 작업구분을 입력 또는 선택 후 [조회] 하세요!');	// store tBody 설정
		_storeSelectCodeOptionTag();	// PAGE ON LOAD : VEN_CD 별 STORE_CD 설정
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
	
	// 선택 삭제
	function _eventSelectDelete(){
		if (!confirm('<spring:message code="msg.common.confirm.select.delete"/>')) {
            return;
        }
		var prodDataList = [];
		var mstDataList = [];
		
		var venCd = $('#schVenCd').val();
		
		$("[id^=strMstTr_]").each(function (index){
			var strCd = $(this).attr('id').replaceAll("strMstTr_", "");
			// 점포 정보  ------------------------------------------
			var mstData= {};
			mstData['venCd'] = venCd;
			mstData['strCd'] = strCd;
			mstData['ordReqNo'] = $(this).find('input[name=ordReqNo]').attr('value');
			mstData['venNm'] = $(this).find('input[name=venNm]').attr('value');
			mstData['ordDy'] = $('#schOrdDy').val();
			mstDataList.push(mstData);
			
			$("[id^=detStrInfo_"+strCd+"]").each(function (index){
				// 상품 정보  ------------------------------------------
				var prodData= {};
				$(this).find('input').map(function() {
					if(this.name == 'strCheck_'+strCd){
						if($(this).is(":checked")){
							prodData['venCd'] = venCd;
							prodData['prodCd'] = $(this).parent().parent().children('td:last').children('input[name="prodCd"]').attr('value');
							prodData['strCd'] = $(this).parent().parent().children('td:last').children('input[name="strCd"]').attr('value');
							prodData['ordDy'] = $(this).parent().parent().children('td:last').children('input[name="ordDy"]').attr('value');
							prodData['ordReqNo'] = $(this).parent().parent().children('td:last').children('input[name="ordReqNo"]').attr('value');
							prodDataList.push(prodData);
						}
					}
				});
				
			});
		});
		
		if(prodDataList.length == 0){
			alert('<spring:message code="msg.common.error.no.data"/>');
			return;
		}
		
		loadingMaskFixPos();
		
		$.ajaxSetup({
	  		contentType: "application/json; charset=utf-8" 
			});
	 	$.post("<c:url value='/edi/weborder/tedStoreOrdDelete.do'/>"
	 			,JSON.stringify({'tedPoOrdMstList001VO' : mstDataList, 'tedOrdList001VO' : prodDataList})
	  			,function(json){				
					if(jQuery.trim(json) == ""){//처리성공
						alert('<spring:message code="msg.common.success.delete"/>');
						$("input:checkbox[name=allCheck]").attr("checked", false);
						_eventSearch();
					}else{
						alert(json);
					}
					hideLoadingMask();
					
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
		
		var prodDataList = [];
		var mstDataList = [];
		var venCd = $('#schVenCd').val();
		var chkValidte = true;
		var chkQty = null;
		
		$("[id^=strMstTr_]").each(function (index){
			var strCd = $(this).attr('id').replaceAll("strMstTr_", "");
			// 점포 정보  ------------------------------------------
			var mstData= {};
			mstData['venCd'] = venCd;
			mstData['strCd'] = strCd;
			mstData['ordReqNo'] = $(this).find('input[name=ordReqNo]').attr('value');
			mstData['venNm'] = $(this).find('input[name=venNm]').attr('value');
			mstData['ordDy'] = $('#schOrdDy').val();
			mstDataList.push(mstData);
			
			$("[id^=detStrInfo_"+strCd+"]").each(function (index){
				// 상품 정보  ------------------------------------------
				var prodData= {};
				var ordQty = $(this).find('input[name=ordQty]').attr('value');
				var oldOrdQty = $(this).find('input[name=oldOrdQty]').attr('value');
				
				if(ordQty != null && ordQty != "" && ordQty != oldOrdQty){
					$(this).find('input').map(function() {
						if(this.type != 'button' || this.type != 'checkbox'){
							if(this.name != 'oldOrdQty'){
								if(this.name != 'strCheck_'+strCd){
									if(this.name == 'ordQty' || this.name == 'ordBuyPrc' || this.name == 'ordIpsu' || this.name == 'ordTotPrc' || this.name == 'ordTotAllQty'){
										chkQty = (Number($(this).val().replace(/\,/g,"")));
										
										if(!chkQty > 0 ) {
											chkValidte = false;
										}else{
											prodData[this.name] = $(this).val().replaceAll(",","");
										}
									}else{
										prodData[this.name] = $(this).val();
									}
								}
							}
						}
					});
					prodData['venCd'] = venCd;
					prodDataList.push(prodData);
				}
			});
		});
		
		if(!chkValidte){
			alert("발주 수량을 확인해 주십시요.");
			return;
		}
		
		if(prodDataList.length == 0){
			alert('<spring:message code="msg.common.error.no.data"/>');
			return;
		}
		
		loadingMaskFixPos();
		
		$.ajaxSetup({
	  		contentType: "application/json; charset=utf-8" 
			});
	 	$.post("<c:url value='/edi/weborder/tedStoreOrdTempSave.do'/>"
	  			,JSON.stringify({'tedPoOrdMstList001VO' : mstDataList, 'tedOrdList001VO' : prodDataList})
	  			,function(json){				
					if(jQuery.trim(json) == ""){//처리성공
						alert('<spring:message code="msg.common.success.insert"/>');
						_eventSearch();
					}else{
						alert(json);
					}
					
					hideLoadingMask();
	  			}, 'json');
	 	
	}
	
	// 단위수량 입력시 EA 와 금액 계산
	function sumOrdInfo(obj, strCd){
		
		// 상품별 합계
		var ordQty = $(obj).parent().parent().children().children('input[name="ordQty"]').attr('value');
		var ordIpsu = $(obj).parent().parent().children('td:last').children('input[name="ordIpsu"]').attr('value');
		var ordBuyPrc = $(obj).parent().parent().children('td:last').children('input[name="ordBuyPrc"]').attr('value');
		var chkQty;
		
		if(!isNumber(ordQty)) {
			alert("정상적인발주수량(EA)을 입력하세요!");
			$(obj).val("");
			$(obj).focus();
			return;	
		}

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
		
		
		// 점포별 합계
		var strQty = 0, strEa = 0, strPrc = 0;
		
		$('#datalist tr').each(function(i){
			if(this.id == 'detStrInfo_'+strCd){
				var strQtySum = $(this).children().children('input[name="ordQty"]').attr('value');
				strQty = Number(strQty)+Number(strQtySum.replaceAll(",",""));
				var strEaSum = $(this).children().children('input[name="ordTotAllQty"]').attr('value');
				strEa = Number(strEa)+Number(strEaSum.replaceAll(",",""));
				var strAmtSum = $(this).children().children('input[name="ordTotPrc"]').attr('value');
				strPrc = Number(strPrc)+Number(strAmtSum.replaceAll(",",""));
			}
		});
		
	 	$('input[name=ordTotQty_'+strCd+']').val(amtComma(strQty));
	 	$('input[name=ordTotAllQty_'+strCd+']').val(amtComma(strEa));
	 	$('input[name=ordTotPrc_'+strCd+']').val(amtComma(strPrc));
		
		
		// 총 합계
		var ordTotQty = 0, ordTotAllQty = 0, ordTotPrc = 0;
		
		$("input[id=ordTotQty]").each(function (index){
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
	
	// 점포별 체크박스 전체 선택 및 전체 해제
	function selectAllCheckbox(str_cd){
		if($("input:checkbox[name=strAllCheck_"+str_cd+"]").is(":checked")){
			$("input:checkbox[name=strCheck_"+str_cd+"]").attr("checked", true);
		}else{
			$("input:checkbox[name=strCheck_"+str_cd+"]").attr("checked", false);
			$("input:checkbox[name=allCheck]").attr("checked", false);
		}
		
		$('input:checkbox[id=box]:checked').each(function() {
			if($(this).attr("disabled")){
				$(this).attr("checked", false);
			}	
		});
	}

	// 전체 체크박스 해제
	function chkAllBox(obj){
		if(!$(obj).attr("checked")){
			var strCd = $(obj).parent().parent().attr('id').replaceAll("detStrInfo_", "");
			$("input:checkbox[name=strAllCheck_"+strCd+"]").attr("checked", false);
		}
		
		$("input:checkbox[name=allCheck]").attr("checked", false);
	}
	
	// 점포별 상세 상품 목록을 조회 하기전 해당 점포의 상품 정보를 조회 여부에 따라 분기 처리
	function selectStoreOrdDetail(strCd, upYn){

		var openYn = $('#openYn_'+strCd).val();
		var strDetSz = $('#detStrInfo_'+strCd).length;

		if(openYn == 'N') {
			$("#detImg_"+strCd).attr("src", "/images/epc/layout/lnb_minus.gif");
			$("#detTxt_"+strCd).text("숨김");
			$('#openYn_'+strCd).val("Y");
			
			if(strDetSz > 0){
				_doViewDetDiv(strCd, "Y");
			}else{
				if(upYn == 'Y'){
					 $("input[name=strAllCheck_"+strCd+"]").attr("disabled",false);
				}else{
					 $("input[name=strAllCheck_"+strCd+"]").attr("disabled",true);
				}	
				_doDetailSearch(strCd);
			}
			
		}else{
			$("#detImg_"+strCd).attr("src", "/images/epc/layout/lnb_plus.gif");
			$("#detTxt_"+strCd).text("펼침");
			$('#openYn_'+strCd).val("N");
			_doViewDetDiv(strCd, "N");
		}
		
	}
	
	// +/- 상태에 따라 상품 목록을 show/hide
	function _doViewDetDiv(strCd, openYn){
		if(openYn == 'Y'){
			$('#datalist tr').each(function(i){
				if(this.id == 'detStrInfo_'+strCd){
					$(this).css("display","block");
				}
			});
		}else{
			$('#datalist tr').each(function(i){
				if(this.id == 'detStrInfo_'+strCd){
					$(this).css("display","none");
				}
			}); 
		}
	}
	
	// 점포별 상세 상품 목록을 조회
	function _doDetailSearch(strCd){
		var str = {"venCd" 		: $("#schVenCd").val(), 
				   "strCd"		: strCd,
				   "prodCd"		: $("#prodCd").val(), 
				   "prodNm"		: $("#prodNm").val()
				   
		};
		
		loadingMaskFixPos();
		
		$.ajaxSetup({
	  		contentType: "application/json; charset=utf-8" 
			});
		$.post(
				"<c:url value='/edi/weborder/tedStoreOrdDetailSelect.do'/>",
				JSON.stringify(str),
				function(data){
					_setTbodyStoreOrdDetailValue(data, strCd);
					hideLoadingMask();
				},	"json"
			);
		
	}
	
	// 점포별 상세 상품 목록 리스트에 뷰.
	function _setTbodyStoreOrdDetailValue(json, strCd){
		var data = json.list, eleHtml = [], h = -1, cnt = 0, tabCnt = 0;
			
		if($('#tabIdx').val() != ""){
			tabCnt = Number($('#tabIdx').val());
		}
		
		if(data != null){
			var sz = json.list.length;
			if (sz > 0) {
				for ( var k = 0; k < sz; k++) {
					cnt = k+1;
					tabCnt++;
					
					var ordTotAllQty=0, ordTotPrc=0;
					
					if(data[k].ordQty  != null){
						ordTotAllQty = data[k].ordIpsu * data[k].ordQty;
						ordTotPrc = data[k].ordIpsu * data[k].ordQty * data[k].ordBuyPrc;
					}
					
					eleHtml[++h] = '<tr bgcolor=ffffff id="detStrInfo_'+data[k].strCd+'">' + "\n";
					eleHtml[++h] = "\t" + '<td align="center">'+cnt+'</td>' + "\n";
					if(data[k].ordQty  != null){
						eleHtml[++h] = "\t" + '<td align="center"><input type="checkbox" id="box" name="strCheck_'+data[k].strCd+'" onclick="javascript:chkAllBox(this)"></td>' + "\n";
					}else{
						eleHtml[++h] = "\t" + '<td align="center"><input type="checkbox" id="box" name="strCheck_'+data[k].strCd+'" onclick="javascript:chkAllBox(this)" disabled="disabled"></td>' + "\n";
					}
					
					eleHtml[++h] = "\t" + '<td align="left" style="color: blue;">&nbsp;'+data[k].prodCd+'</td>' + "\n";
					eleHtml[++h] = "\t" + '<td align="left">&nbsp;'+data[k].srcmkCd+'</td>' + "\n";
					/* eleHtml[++h] = "\t" + '<td align="left" class="dot_web0001" title="'+data[k].prodNm+'"><span>'+data[k].prodNm+'</span></td>' + "\n"; */
					eleHtml[++h] = "\t" + '<td align="left" title="'+data[k].prodNm+'"><span>'+data[k].prodNm+'</span></td>' + "\n";
					eleHtml[++h] = "\t" + '<td align="center">'+data[k].prodStd+'</td>' + "\n";
					eleHtml[++h] = "\t" + '<td align="right">'+data[k].ordIpsu+'&nbsp;</td>' + "\n";
					eleHtml[++h] = "\t" + '<td align="center">'+data[k].ordUnit+'&nbsp;</td>' + "\n";
					if(data[k].ordQty  != null){
						eleHtml[++h] = "\t" + '<td align="right"><input type="text" tabindex="'+tabCnt+'" id="ordQty" name="ordQty" maxlength="10" value="'+amtComma(data[k].ordQty)+'" style="text-align: right; width:50px;" onkeypress="onlyNumber(); " onblur="sumOrdInfo(this,\''+ data[k].strCd+'\'); amtFormat(this); " onFocus="unNumberFormat(this);">' + "\n";
						eleHtml[++h] = "\t" + '<input type="hidden" id="oldOrdQty" name="oldOrdQty" value="'+data[k].ordQty+'" ></td>' + "\n";
					}else{
						eleHtml[++h] = "\t" + '<td align="right"><input type="text" tabindex="'+tabCnt+'" id="ordQty" name="ordQty" maxlength="10" style="text-align: right; width:50px;" onkeypress="onlyNumber(); " onblur="sumOrdInfo(this,\''+ data[k].strCd+'\'); amtFormat(this);" onFocus="unNumberFormat(this);">' + "\n";
						eleHtml[++h] = "\t" + '<input type="hidden" id="oldOrdQty" name="oldOrdQty"></td>' + "\n";
					}
					
					eleHtml[++h] = "\t" + '<td align="right"><input type="text" value="'+amtComma(ordTotAllQty)+'"  name="ordTotAllQty" class="inputReadOnly" style="text-align: right; color: blue; width:50px;" readonly="readonly"></td>' + "\n";
					eleHtml[++h] = "\t" + '<td align="right"><input type="text" value="'+amtComma(ordTotPrc)+'" name="ordTotPrc" class="inputReadOnly" style="text-align: right;  color: blue; width:80px;" readonly="readonly">' + "\n";
					eleHtml[++h] = "\t" + '<input type="hidden" name="prodCd" value='+data[k].prodCd+'>' + "\n";
					eleHtml[++h] = "\t" + '<input type="hidden" name="strCd" value='+data[k].strCd+'>' + "\n";
					eleHtml[++h] = "\t" + '<input type="hidden" name="ordDy" value='+data[k].ordDy+'>' + "\n";
					eleHtml[++h] = "\t" + '<input type="hidden" name="ordIpsu" value='+data[k].ordIpsu+'>' + "\n";
					eleHtml[++h] = "\t" + '<input type="hidden" name="ordReqNo" value="'+data[k].ordReqNo+'">' + "\n";
					eleHtml[++h] = "\t" + '<input type="hidden" name="ordBuyPrc" value='+data[k].ordBuyPrc+'></td>' + "\n";
					eleHtml[++h] = "\t" + '</tr>' + "\n";
					
					$('#schOrdDy').val(data[k].ordDy);
					cnt++;
				}
				
				$('#tabIdx').val(tabCnt);
				$("#strMstTr_"+strCd).after(eleHtml.join(''));
			}
		}
	}
	
	// 업체코드별 발주 가능 점포 목록 조회
	function _eventSearch(){
		
		var vendorFlag = $('#vendorFlag').val();
		var storeCds = $("#storeCds").val().split(",");
		var strSearchType = "1";
		
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
		
		if($("#storeCds").val() && storeCds.length  > 0) strSearchType = "2";
	
		var str = { "venCd" 		 : $("#entp_cd").val(), 
				    "strCd"			 : $("#strCd").val(), 
				    "strCds"		 : storeCds,
				    "strSearchType"  : strSearchType,
				    "areaCd"		 : $("#areaCd").val(), 
				    "page" 			 : $("#page").val(),
				    "workGb"		 : $('input[name="workGb"]:radio:checked').val(),
				    "prodCd"		 : $("#prodCd").val(), 
				    "prodNm"		 : $("#prodNm").val()
		};
		
		loadingMaskFixPos();
		
		$.ajaxSetup({
	  		contentType: "application/json; charset=utf-8" 
			});
		$.post(
				"<c:url value='/edi/weborder/tedStoreOrdSelect.do'/>",
				JSON.stringify(str),
				function(data){
					
					if(data == null || data.state !="0"){
						hideLoadingMask();
						//자료조회중 오류가 발생하였습니다.[CODE:data.state]
						alert('<spring:message code="msg.weborder.common.error.select"/>'+'[CODE:'+data.state+']');		
					}
					else {
						hideLoadingMask();
						$('#schVenCd').val($("#entp_cd").val());
						_setTbodyStoreOrdValue(data);
					}	
					
					
				},	"json"
		);
		
	} 
	
	// 발주 가능 점포 목록 리스트에 뷰
	function _setTbodyStoreOrdValue(json) {
		_setTbodyInit();

		var data = json.list, ordCntSum = json.ordCnt, eleHtml = [], h = -1, pagHtml = [], j = -1;
	    
		if(data != null){
			var sz = json.list.length;
			var upCnt = 0;
			if (sz > 0) {
				for ( var k = 0; k < sz; k++) {
					eleHtml[++h] = '<tr id="strMstTr_'+data[k].strCd+'" class="groupRows">' + "\n";
					eleHtml[++h] = "\t" + '<td align="center" >-</td>' + "\n";
					eleHtml[++h] = "\t" + '<td align="center"><input type="checkbox" id="box" name="strAllCheck_'+data[k].strCd+'" onclick="javascript:selectAllCheckbox('+data[k].strCd+')" disabled="disabled"><input type="hidden" id="openYn_'+data[k].strCd+'" value="N"></td>' + "\n";
					eleHtml[++h] = "\t" + '<td align="left" colspan="6" style="font-weight: bold;">' + "\n";
					eleHtml[++h] = "\t" + '<img id="detImg_'+data[k].strCd+'" src="/images/epc/layout/lnb_plus.gif" class="middle" style="cursor:hand;" onclick="javascript:selectStoreOrdDetail(\''+ data[k].strCd+'\',\''+ data[k].upYn+'\')" />' + "\n";
					eleHtml[++h] = "\t" + '&nbsp;<span id="detTxt_'+data[k].strCd+'">펼침</span>&nbsp;['+data[k].strCd+']'+data[k].strNm+'&nbsp;'  + "\n";
					
					eleHtml[++h] = "\t" + '<div style="float:right; font-size:1.0em; text-align:right; margin-right:5px;">						' + "\n";
					eleHtml[++h] = "\t" + '<span style="color:#d5855e; font-weight: bold;">총상품:'+amtComma(data[k].prodTotSum)+' </span>&nbsp; '  + "\n";
					eleHtml[++h] = "\t" + '</div>' + "\n";	
					
					
					eleHtml[++h] = "\t" + '</td>' + "\n"; 
					eleHtml[++h] = "\t" + '<td align="right"><input class="inputReadOnly" id="ordTotQty" name="ordTotQty_'+data[k].strCd+'" type="text" value="'+amtComma(data[k].ordTotQty)+'" style="text-align: right; color: blue; width:50px;" readonly="readonly"/></td>' + "\n";
					eleHtml[++h] = "\t" + '<td align="right"><input class="inputReadOnly" id="ordTotAllQty" name="ordTotAllQty_'+data[k].strCd+'" type="text" value="'+amtComma(data[k].ordTotAllQty)+'" style="text-align: right; color: blue; width:50px;" readonly="readonly"/></td>' + "\n";
					eleHtml[++h] = "\t" + '<td align="right"><input class="inputReadOnly" id="ordTotPrc" name="ordTotPrc_'+data[k].strCd+'" type="text" value="'+amtComma(data[k].ordTotPrc)+'" style="text-align: right; color: blue; width:80px;" readonly="readonly"/>' + "\n";
					eleHtml[++h] = "\t" + '<input type="hidden" id="venNm" name="venNm" value="'+data[k].venNm+'">' + "\n";
					eleHtml[++h] = "\t" + '<input type="hidden" id="ordReqNo" name="ordReqNo" value="'+data[k].ordReqNo+'"></td>' + "\n";
					eleHtml[++h] = "\t" + '</tr>' + "\n";
				
					$('#schOrdDy').val(data[k].ordDy);
					if(data[k].upYn == 'Y') upCnt++;
				}
				$("#datalist").append(eleHtml.join(''));
			}
			
			if(upCnt > 0) $("input[name=allCheck]").attr("disabled",false);
			else $("input[name=allCheck]").attr("disabled",true);
			
			$("#ordTotQtySum").text(amtComma(ordCntSum.ordTotQtySum));
			$("#ordTotAllQtySum").text(amtComma(ordCntSum.ordTotAllQtySum));
			$("#ordTotPrcSum").text(amtComma(ordCntSum.ordTotPrcSum));
			
		}else {
			_setTbodyNoResult($("#datalist"), 11, null );
		};
		
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
	
	/* 목록 초기화 */
	function _setTbodyInit() {
		$("#datalist tr").remove();
		$("#paging").empty();
		$("#ordTotQtySum").text("");
		$("#ordTotAllQtySum").text("");
		$("#ordTotPrcSum").text("");
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
	
	
	/*다중선택 점포 초기화(1건씩)  type: onClick :S, doubleClick : D */
	function _eventClearStore(type){
		var storeNms = $("#storeNms").text().split(",");
		var storeCds = $("#storeCds").val().split(",");

		$("#storeNms").text("");
		$("#storeCds").val("");
		
		var lng = storeNms.length;
		
		if(type =="D"  || storeNms[lng-1].indexOf("(외") != -1)  return;
		
		var setStoreNms ="";
		var setStoreCds ="";
		
		for(var i=0; i<lng; i++){
			if(lng ==  i+1) continue;
			
			if(setStoreNms) setStoreNms +=",";
			setStoreNms +=storeNms[i];
			
			if(setStoreCds) setStoreCds +=",";
			setStoreCds +=storeCds[i];
		}
		
		
		$("#storeNms").text(setStoreNms);
		$("#storeCds").val(setStoreCds);
	}
	
	
	//업체 발주 가능 점포 검색 팝업 호출 
	 function _eventSearchStore(){
		
		 var param = new Object()
	 	, site = "<c:url value='/edi/weborder/PEDMWEB00993Popup.do'/>"
		, style ="dialogWidth:450px;dialogHeight:420px;resizable:yes;";	

		param.venCd = $("#entp_cd").val();
		param.rtnStrCd = "";
		param.rtnStrNm = "";
		param.rtnStrCnt = "";

		window.showModalDialog(site, param, style);
		
		if(param && param.rtnStrCd){ 
			
			$("#storeNms").text(param.rtnStrNm);
			$("#storeCds").val(param.rtnStrCd);
			$("#storeCnt").val(param.rtnStrCnt);
			
			//점포 DropDown 초기화
			$("#strCd").val('');
		}
		
		return false;
		
	 }
	
	
	
	/* 협력사별,발주별 점포코드 세팅 */
	function _storeSelectCodeOptionTagBak() {
		
		if(!$.trim($('#entp_cd').val())  ) return;
		
		
		var toSelectTagID   	= "#strCd";
		var firstOptionMessage	= "발주가능점포 ";
		var selectedVal   		= "";
		
		var  eleHtml = [], h = -1, j = -1;
		
		var str = {  "selectedCode" 	: $("#entp_cd").val()		 // 검색조건 상품코드 
		    	    ,"selectViewCode"	: $("#prodCd_view").text() 	 // 검색조건 업체코드
		      };
		
		//alert($("#prodCd_view").text());
			$.ajaxSetup({contentType: "application/json; charset=utf-8"});
			$.post(
				"<c:url value='/CommonCodeHelperController/venOrdStoreSelectList.do'/>",
				JSON.stringify(str),
				function(data){
					if(data == null || data.length <= 0){
						return;
					}
					else  {
						
						var	sz = data.length;
						$(toSelectTagID + " option").remove();
						
						if(firstOptionMessage){
							eleHtml[++h] = "<option value=''>"+ firstOptionMessage+" ["+sz+" 개점]" + "</option>";
						}
						
						for ( var k = 0; k < sz; k++) {
							eleHtml[++h] = " <option value='"+data[k].code+"' ";
							if(selectedVal && selectedVal == data[k].code) { 
								eleHtml[++h] = " selected ";
							}
							eleHtml[++h] = ">"  +data[k].name+  "</option>   \n";
						}
						
						$(toSelectTagID).append(eleHtml.join(''));
					} 
					
				},	"json"
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
.dot_web0001 span{display:block;overflow:hidden;width:145px;height:18px;white-space:nowrap;text-overflow:ellipsis;-o-text-overow: ellipsis;-moz-binding:url(js/ellipsis.xml#ellipsis)undefinedundefinedundefined}

.page { padding:1px 0 0; text-align:center; }
.page img { vertical-align:middle;}
.page a { display:inline-block; width:15px; height:11px; padding:4px 0 0; text-align:center; border:1px solid #efefef; background:#fff; vertical-align:top; color:#8f8f8f; line-height:11px;}
.page a.btn,
.page a.btn:hover { width:auto; height:auto; padding:0; border:0; background:none;}
.page a.on,
.page a:hover {background:#518aac; font-weight:bold; color:#fff;}

.groupRows td{border-bottom-style: solid; border-bottom-width: 1px; border-color:#fbfbfb;}
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
		<input type="hidden" name="tabIdx" id="tabIdx" />
		<input type="hidden" name="vendorFlag" id="vendorFlag" />
		<input type="hidden" name="vendorWebOrdFrDt" id="vendorWebOrdFrDt" value="${paramMap.vendorWebOrdFrDt}"/>
		<input type="hidden" name="vendorWebOrdToDt" id="vendorWebOrdToDt" value="${paramMap.vendorWebOrdToDt}"/>
		
		
			<!--	@ 검색조건	-->
			<div class="wrap_search">
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
						<col style="width:100px;" />
						<col style="width:200px;" />
						<col style="width:100px;" />
						<col style="*" />
					</colgroup>
					<tr>
						<th>협력업체 코드</th>
						<td>
							<html:codeTag objId="entp_cd" objName="entp_cd" width="132px;" formName="form" selectParam="" dataType="CP" comType="SELECT"  />
						</td>
						<th>작업구분</th>
						<td>
							<input type="Radio" name="workGb" value="1" <c:if test="${paramMap.workGb eq '1'}"> Checked</c:if> /> 전체
							<input type="Radio" name="workGb" value="2" <c:if test="${paramMap.workGb eq '2'}"> Checked</c:if> /> 등록
							<input type="Radio" name="workGb" value="3" <c:if test="${paramMap.workGb eq '3'}"> Checked</c:if> /> 미등록
						</td>
					</tr>
					<tr>
						<th>점포</th>
						<td class=groupRows>
							<html:codeTag objId="areaCd" objName="areaCd" width="50px;" formName="form" dataType="AREA" comType="select"  defName="선택"  />
							
							<select name="strCd" id="strCd" style="width:132px">
								<option value="">선택</option>
							</select>
						</td>
						<th>점포 다중선택</th>
						<td>
							<table cellpadding="0" cellspacing="0" border="0" width=100% >
								<colgroup>
									<col style="width:*" />
									<col style="width:50px;" />
								</colgroup>
								<tr>
									<td>
										<span id="storeNms" class="inputReadOnly" style="font-size:1.0em; color:#488ce7; height: 18px; width: 100%"></span>
										<input type="hidden" id="storeCds">
										<input type="hidden" id="storeCnt">
									</td>
									<td>
										<img id="btnClearStore" src=/images/epc/btn/icon_01.png  title="점포선택 초기화(더블클릭 전체)" style="cursor: pointer;">
										<img id="btnSearchStore" src=/images/epc/btn/icon_02.png title="점포선택 팝업"   style="cursor: pointer;">
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<th>상품코드</th>
						<td>
							<input type="text" id="prodCd" name="prodCd" value="${paramMap.prodCd}">
						</td>
						<th>상품명</th>
						<td>
							<input type="text" id="prodNm" name="prodNm" value="${paramMap.prodNm}">
						</td>
					</tr>
					</table>
				</div>
				<!-- 1검색조건 // -->
			</div>
			<!--	2 검색내역 	-->
			<div class="wrap_con">
				<!-- list -->
				<div class="bbs_list">
					<ul class="tit">
						<li class="tit">발주등록 가능 상품 List</li>
					</ul>
					 
					<table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=100% bgcolor=efefef>
                        <colgroup>
							<col width="36px"/>
							<col width="40px"/>
							<col width="74px"/>
							<col width="100px"/>
							<col width="*"/>
							<col width="70px"/>
							<col width="55px"/>
							<col width="70px"/>
							<col width="55px"/>
							<col width="55px"/>
							<col width="90px"/>
							<col width="18px"/>
						</colgroup>	
						<thead>
	                        <tr bgcolor="#e4e4e4" align=center> 
	                          <th rowspan="2" >No.</th>
	                          <th rowspan="2">삭제</th>
		                      <th colspan="6">상품정보</th>
		                      <th colspan="3">발주등록정보</th>
		                      <th rowspan="3"></th>
	                        </tr>
	                        <tr bgcolor="#e4e4e4" align=center>
							  <th>상품코드</th>
	                          <th>판매코드</th>
	                          <th>상품명</th>
	                          <th>규격</th>
	                          <th>입수</th>
	                          <th>단위</th>
	                          <th>단위수량</th>
	                          <th>EA</th>
	                          <th>금액</th>
	                        </tr>
	                        <tr bgcolor="87CEFA">
								<td align="center">-</td>
								<td align="center"><input type="checkbox" id="allCheck" name="allCheck"></td>
								<td align="center" colspan="6">전점합계</td>
								<td align="right" style="color: red; font-weight: bold; font-size: 12px;"><span id="ordTotQtySum"></span></td>
								<td align="right" style="color: red; font-weight: bold; font-size: 12px;"><span id="ordTotAllQtySum"></span></td>
								<td align="right" style="color: red; font-weight: bold; font-size: 12px;"><span id="ordTotPrcSum"></span></td>
	                    	</tr>
                        </thead>
                        <tr> 
	                     	<td colspan=12>   
	                        	<div id="_dataList1" style="background-color:#FFFFFF; margin: 0; padding: 0; height:334px; overflow-y: scroll; overflow-x: hidden">
	                        		<table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=100% bgcolor="#EFEFEF">
	                     			<colgroup>
										<col width="35px"/>
										<col width="40px"/>
										<col width="74px"/>
										<col width="100px"/>
										<col width="*"/>
										<col width="70px"/>
										<col width="55px"/>
										<col width="70px"/>
										<col width="55px"/>
										<col width="55px"/>
										<col width="90px"/>
									</colgroup>
									 <tbody id="datalist" />
	                     			</table>
	                     		</div>
	                     	</td>
		                </tr>
					</table>
			</div>
		</div>
		<!-- Paging start ----------------------------------------------------->
		<div class="bbs_search" style="margin-top: 1px;">
		<table id="dataTable" cellpadding="1" cellspacing="1" border="0" bgcolor=efefef width="100%">
		<tr><td height="20">
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
					<li class="last">점포별 발주등록 </li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>
<font color='white'><b>PEDMWEB0001.jsp</b></font>

</html>
