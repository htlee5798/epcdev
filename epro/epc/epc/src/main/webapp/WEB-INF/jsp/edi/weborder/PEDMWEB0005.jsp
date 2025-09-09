<%--
- Author(s): 
- Created Date: 2014. 08. 04
- Version : 1.0
- Description : 상품별 반품등록 탭화면

--%>
<%@include file="../common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>

<script  type="text/javascript" >


		/* 폼로드 */
		$(document).ready(function($) {		
			$("select[name='entp_cd']").val("<c:out value='${param.entp_cd}'/>");	// 협력업체 선택값 세팅
		});

	
	$(function() {
		
		
		_storeSelectCodeOptionTag();	// PAGE ON LOAD : VEN_CD 별 STORE_CD 설정
		doSearchReturnList();			// today 등록반품록조회
		
		//버튼 CLICK EVNET ---------------------------------------------------------------
		$('#btnProdSearch').unbind().click(null, _eventProdSearch);			// 반품상품조회
		$('#btnProdSave').unbind().click(null, 	 _eventProdSave);			// 등록하기
		$('#btnProdSend').unbind().click(null, 	 _eventProdSend);			// 반품요청
		$('#btnProdDelete').unbind().click(null, _eventProdDelete);			// 반품상품삭제
		$('#btnProdSelect').unbind().click(null, doSearchReturnList);		// 협력업체 코드별 반품상품조회
		
		$('#btnProImg').unbind().click(null,	 _eventProdFind);			// 상품목록조회팝업
		$('#btnVendCdDel').click( function () { setProdViewData(true); });  // 상품검색 정보 제거 
		//--------------------------------------------------------------------------------
		
		//입력필드 ENTER KEY EVENT --------------------------------------------------------
		$('#prodCd').unbind().keydown(function(e) {	// 상품코드
			switch(e.which) {
	    		case 13 : _eventProdSearch(this); break; // enter
	    		default : return true;
	    	}
	    	e.preventDefault();
	   	});
		
		$('#rrlQty').unbind().keydown(function(e) {	// 수량
			switch(e.which) {
	    		case 13 :  _eventProdSave(this); break; // enter
	    		default : return true;
	    	}
	    	e.preventDefault();
	   	});
	 	//-------------------------------------------------------------------------------
		
	 	
	 	//입력필드 CHANGE  EVENT ---------------------------------------------------------
		$("#entp_cd").change(function () { _storeSelectCodeOptionTag();});  // 업체코드 셀렉트 박스 변경시 점포 코드 변경
		//-------------------------------------------------------------------------------
		
		// 삭제 체크박스 전체 선택 ---------------------------------------------------------
		$('#allCheckStr').click(function(){
			if ($("#allCheckStr").is(":checked")) { 
				$('input:checkbox[id^=strBox]:not(checked)').attr("checked", true); 
			} else { 
				$('input:checkbox[id^=strBox]:checked').attr("checked", false); 
			} 
			
			$('input:checkbox[id^=strBox]:checked').each(function() {
				if($(this).attr("disabled")){
					$(this).attr("checked", false);
				}
			});
		});
		//-------------------------------------------------------------------------------
	 	
	});
	
	
	<%-- 상품코드 목록  검색 팝업 호출    --%>
	 function _eventProdFind(){
		
		 var param = new Object()
	 	, site = "<c:url value='/edi/weborder/PEDMWEB00992Popup.do'/>"
		, style ="dialogWidth:700px;dialogHeight:450px;resizable:yes;";	

		param.venCd  = $("#entp_cd").val();
		param.prodCd = "";
		param.prodNm = "";

		window.showModalDialog(site, param, style);

		if(param && param.prodCd){
			
			$("#prodCd").val(param.prodCd);
			 _eventProdSearch();	//상품코드를 통한 상세조회	 
		}
		
		return false;
	}
	
	
	<%--  반품상품 조회  --%>
	function _eventProdSearch(){
		
		<%-- 조회조건 검증 ============================= --%>
		if(!$.trim($('#strCd').val())){
			alert("선택된 점포가 없습니다.");
			$("#strCd").focus();
			return false;
		}
		
		if(!$.trim($('#prodCd').val()) || $('#prodCd').val().length != 10 ){
			alert("상품코드 10자리를 정확히 입력하세요!");
			$("#prodCd").focus();
			return false;
		}
		
		
		if(!isNumber($('#prodCd').val())) {
			alert('정상적인 상품코드를 입력하세요!');
			
			$('#prodCd').val('');
			$('#prodCd').focus();
			return false;
		}
		
		
		if(!$.trim($('#entp_cd').val())){
			alert("선택된 업체코드가 없습니다.");
			$("#entp_cd").focus();
			return false;
		}
		<%-- ------------------------------------------------------------- --%>
		
		<%--[조회된 상품정보 초기화 ]====================== --%>
		setProdViewData(false);
		loadingMaskFixPos();
		
		var str = {  "prodCd" 	: $("#prodCd"  ).val()	//검색조건 상품코드 
			    	,"venCd"	: $("#entp_cd" ).val() 	//검색조건 업체코드
			    	,"strCd"	: $("#strCd" ).val() 	//검색조건 점포코드
			      };
	
			$.ajaxSetup({
		  		contentType: "application/json; charset=utf-8" 
				});
			$.post(  
					"<c:url value="/edi/weborder/PEDMWEB0099SearchProd.do"/>",  // 공통 [PEDMWEB0099]
					JSON.stringify(str),
					function(data){
						
						if(data == null || data.state !="0"){
							hideLoadingMask();
						 	if(data.state =="1"){ alert("검색된 상품정보가 없습니다.");		// 검색결과 없음.
							$('#btnProImg').trigger('click');
						 	}else{ alert("상품정보검색중 오류가 발생하였습니다.");}			// 검색중 Exception 발생(data.message 로 확인가능) 
						 	return;
						}
						else
						{
			    		  	$('#prodCd_view'  ).text(data.prodCd);	// 상품코드
			    			$('#srcmkCd_view' ).text(data.srcmkCd);	// 판매코드
			    			$('#prodNm_view'  ).text(data.prodNm);	// 상품명
			    			$('#prodStd_view' ).text(data.prodStd);	// 상품규격
			    			$('#ordIpsu_view' ).text(data.ordIpsu);	// 발주입수
			    			
			    			$('#buyPrc_view').text(amtComma(data.buyPrc));	// 원가
			    			$('#salePrc_view').text(amtComma(data.salePrc));	// 매가
			    			
			    			$('#rrlStkQty').val(amtComma(data.rrlStkQty));
			    			
			    			$("#rrlQty").focus();	// 수량입력필드로 focus 설정
			    			
			    			//_storeSelectCodeOptionTag(); // 업체코드,상품코드의 속한 점포 설정
			    			
			    			
			    		}
						hideLoadingMask();
					},	"json"
			);
	}
	
	
	<%--  반품상품 저장  --%>
	function _eventProdSave(){
		var webOrdFrDt = $('#vendorWebOrdFrDt').val();
		var webOrdtoDt = $('#vendorWebOrdToDt').val(); 
		
		var nowTime = getCurrentTime();
		
		if( Number(nowTime) < Number(webOrdFrDt) || Number(nowTime) > Number(webOrdtoDt) ){
			alert("발주/반품 가능 시간은"+'<spring:message code="msg.weborder.vendor.send.time"/>'+"까지 입니다.");
			return;
		}
		
		var rrlQty 	= (Number($('#rrlQty').val().replace(/\,/g,"")));
	    var rrlStkQty = (Number($('#rrlStkQty').val().replace(/\,/g,"")));
	    
		<%-- 저장조건 검증 ============================= --%>
		if(rrlQty > rrlStkQty){
			alert("반품수량은 재고수량과 같거나 작아야 합니다!");
			$("#rrlQty").focus();
			return;	
		}
		
		if(!$.trim($('#prodCd_view').text())  ){
			alert("검색된 상품정보가 없습니다. 상품을 먼저 조회하세요!");
			$("#prodCd").focus();
			return;	
		}
		if(!rrlQty > 0) {
			alert("정상적인반품수량(EA)를 입력하세요!");
			$("#rrlQty").focus();
			return;	
		}
		<%-- ----------- ============================= --%>
		
		<%-- 저장 CONFIRM  ============================ --%>
		var confirmMsg = "선택하신 [업체/상품/점포]의 반품을 등록하시겠습니까?";
		if(!$("#strCd").val()) confirmMsg = "[전체점포] 대상으로 반품을 등록하시겠습니까?";
		if (!confirm(confirmMsg)) {
        	return false;
		}
		<%-- ----------- ============================= --%>
		
		<%-- 저장값 변수 설정 ========================== --%>
		var str = {  "prodCd" 	: $("#prodCd_view"	).text()	//상품코드 
		    		,"venCd"	: $("#entp_cd"		).val()		//업체코드
		    		,"strCd"	: $("#strCd"		).val()		//점포코드
		    		,"rrlQty"	: $("#rrlQty"		).val()		//반품수량
		      };
		<%-- ----------- ============================= --%>
		
		loadingMaskFixPos();
		
		<%-- 반품등록 실행 ============================= --%>
		$.ajaxSetup({
	  		contentType: "application/json; charset=utf-8" 
			});
		$.post(
				"<c:url value="/edi/weborder/PEDMWEB0005SaveProd.do"/>",
				JSON.stringify(str),
				function(data){
					if(data == null || data.state !="0"){
						if(data.state =="1")
							 alert("이미등록된 점포별 반품상품정보가 존재합니다. 다시확인하세요!\n[CODE:"+data.state+"]");
						else if(data.state =="2") 
							alert("선택 또는 전체 점포중 일치하는 상품 정보가 없습니다.\n[CODE:"+data.state+"]");
						else if(data.state =="3") 
							alert("선택한 상품정보와 점포가 일치하는 대상이 없습니다.\n[CODE:"+data.state+"]");
						else alert("반품처리중 오류가 발생하였습니다.\n[CODE:"+data.state+"]");
					}
					else{
						alert("정상적으로 등록되었습니다.");	
						setProdViewData(true);	//조회상품정보 초기화
						doSearchReturnList();	//반품등록 현황 조회
					}
					hideLoadingMask();
				},	"json"
		);
		<%-- ----------- ============================= --%>
		
	}
	
	<%-- 반품등록 삭제 --%>
	function _eventProdDelete(){
		var strDataList = [];
		
		$("#datalistStr tr").each(function (index){
			// 상품 정보  ------------------------------------------
			var strData= {};
			$(this).find('input').map(function() {
				if(this.name == 'strBox'){
					if($(this).is(":checked")){
						strData['rrlReqNo'] = $(this).parent().parent().children('td:last').children('input[name="rrlReqNo"]').attr('value');
						strData['prodCd'] 	= $(this).parent().parent().children('td:last').children('input[name="prodCd"]').attr('value');
						strData['venCd'] 	= $(this).parent().parent().children('td:last').children('input[name="venCd"]').attr('value');
						strDataList.push(strData);
					}
				}
			});
		});
		
		
		if(strDataList.length == 0){
			alert('선택된 반품등록 정보가 없습니다.');
			return;
		}
		
		loadingMaskFixPos();
		
		$.ajaxSetup({
	  		contentType: "application/json; charset=utf-8" 
			});
		
	 	$.post("<c:url value='/edi/weborder/PEDMWEB0005DeleteProd.do'/>"
	  			,JSON.stringify({'ediRtnProdListVO' : strDataList})
	  			,function(data){				
	  				if(data == null || data.state !="0"){
	  					if(data.state =="1") alert("선택한 반품상품 조건정보 누락!\n[CODE:"+data.state+"]");		// parameter 매핑 오류
	  					else alert("삭제처리 중 오류가 발생하였습니다.\n[CODE:"+data.state+"]");						// system exception 또는 기타오류
	  				}
	  				else{
	  					alert("정상적으로 삭제처리 되었습니다.");
	  					doSearchReturnList();	//반품등록 현황 다시 조회
	  				}
	  				hideLoadingMask();
	  			}, 'json');
	 	
	}

	
	<%--조회상품정보 초기화 --%>
	function setProdViewData(prodFocus){
		
		<%--[검색조건 초기화 & Focus ]==================== --%>
		if(prodFocus) {
			$('#prodCd').val(''); <%-- 상품코드(검색조건)--%>
			$("#prodCd").focus(); 
		}
		
		<%--[조회된 상품정보 초기화 ]====================== --%>
		$('#rrlQty').val('');			<%-- 수량  	 	--%>
		$('#rrlStkQty').val('');			<%-- 수량  	 	--%>
		
		$('#prodCd_view').text('');		<%-- 상품코드  	 --%>
		$('#srcmkCd_view').text('');	<%-- 판매코드      --%>
		$('#prodNm_view').text('');		<%-- 상품명        --%>
		$('#prodStd_view').text('');	<%-- 상품규격      --%>
		$('#ordIpsu_view').text('');	<%-- 발주입수      --%>


		$('#buyPrc_view').text('');		<%-- 원가        	 --%>
		$('#salePrc_view').text('');	<%-- 매가        	 --%>

	}
	
	<%--반품등록 현황 조회 --%>
	function doSearchReturnList(){
		
		loadingMaskFixPos();
		var str = {  "venCd" 	: $("#entp_cd").val()
					,"strCd" 	: $("#strCd").val() 
					};
		$.ajaxSetup({contentType: "application/json; charset=utf-8"});
		$.post(
				"<c:url value='/edi/weborder/PEDMWEB0005SearchList.do'/>",
				JSON.stringify(str),
				function(data){
					if(data == null || data.state != "0"){
						alert("반품목록 검색중 오류가 발생하였습니다.\n[CODE:"+data.state+"]");	
					}
					else  _setTbodyProdReturnValue(data); 
					hideLoadingMask();
				},	"json"
		);
			
	}	
	
	
	function _setTbodyProdReturnValue(json){
		
		_setTbodyMstInit();	// 목록 초기화
		
		var sumRow = json.sumData;
		var sz = json.listData.length;
		var data = json.listData, eleHtml = [], h = -1, pagHtml = [], j = -1;
		if(sumRow){
			$("#storeCount"	).text(amtComma(sumRow.strCdCnt));			//점포SUM
			$("#prodCount"	).text(amtComma(sumRow.rrlTotProdQtySum));	//상품SUM
			$("#qtySum"		).text(amtComma(sumRow.rrlTotQtySum));		//수량SUM
			$("#amtSum"		).text(amtComma(sumRow.rrlTotPrcSum));		//금액SUM
		}
	
		if (sz > 0) {
			for ( var k = 0; k < sz; k++) {
				
				eleHtml[++h] = "<tr bgcolor=ffffff >										\n";
				eleHtml[++h] = " <td align='center'>"+(k+1)+"</td>							\n";
				eleHtml[++h] = " <td align='center'>										\n";
				if(data[k].regStsCd == '1'){
					eleHtml[++h] = " <input type='checkbox' id='strBox' name='strBox' onclick='javascript:chkAllStrBox();' disabled='disabled'> \n";
				}else{
					eleHtml[++h] = " <input type='checkbox' id='strBox' name='strBox' onclick='javascript:chkAllStrBox();'> \n";
				}
				
				eleHtml[++h] = "</td>	\n";
				eleHtml[++h] = " <td align='center'><span title='"+data[k].regStsCdDetail+"' style=color:blue;>"+data[k].regStsCdNm+"</td>							\n";
				
				if(data[k].regStsCd == '9'){
					eleHtml[++h] = " <td class='dot_web0001 through_tr'><span title='["+data[k].strCd+"] "+data[k].strNm+"'>["+data[k].strCd+"]"+data[k].strNm+"</span></td>	\n";
					eleHtml[++h] = " <td align='center' class='through_tr'>"+data[k].prodCd+"</td>					\n";
					eleHtml[++h] = " <td align='center' class='through_tr'>"+data[k].srcmkCd+"</td>				\n";
					eleHtml[++h] = " <td class='dot_web0002 through_tr'><span title='"+data[k].prodNm+"'>"+data[k].prodNm+"</span></td>											\n";
					eleHtml[++h] = " <td align='center' class='through_tr'>"+data[k].ordIpsu+"</td>				\n";
					//eleHtml[++h] = " <td align='center'>"+data[k].rrlStkQty+"</td>				\n";
					//eleHtml[++h] = " <td align='center'>"+data[k].ordUnit+"</td>				\n";
					eleHtml[++h] = " <td align='right' class='through_tr'>"+amtComma(data[k].rrlQty)+"</td>		\n";
					eleHtml[++h] = " <td align='right' class='through_tr'>"+amtComma(data[k].stdProdPrc)+"			\n";
				}else{
					eleHtml[++h] = " <td class='dot_web0001'><span title='["+data[k].strCd+"] "+data[k].strNm+"'>["+data[k].strCd+"]"+data[k].strNm+"</span></td>	\n";
					eleHtml[++h] = " <td align='center'>"+data[k].prodCd+"</td>					\n";
					eleHtml[++h] = " <td align='center'>"+data[k].srcmkCd+"</td>				\n";
					eleHtml[++h] = " <td class='dot_web0002'><span title='"+data[k].prodNm+"'>"+data[k].prodNm+"</span></td>											\n";
					eleHtml[++h] = " <td align='center'>"+data[k].ordIpsu+"</td>				\n";
					//eleHtml[++h] = " <td align='center'>"+data[k].rrlStkQty+"</td>				\n";
					//eleHtml[++h] = " <td align='center'>"+data[k].ordUnit+"</td>				\n";
					eleHtml[++h] = " <td align='right' style='color:blue; font-weight: bolder;'>"+amtComma(data[k].rrlQty)+"</td>		\n";
					eleHtml[++h] = " <td align='right' style='color:red;'>"+amtComma(data[k].stdProdPrc)+"			\n";
				}
				

				
				eleHtml[++h] = "<input type='hidden' name='rrlReqNo' value='"+data[k].rrlReqNo+"'> \n";
				eleHtml[++h] = "<input type='hidden' name='prodCd' 	 value='"+data[k].prodCd+"'>   \n";
				eleHtml[++h] = "<input type='hidden' name='venCd' 	 value='"+data[k].venCd+"'>   \n";

				eleHtml[++h] = "</td>		\n";
				eleHtml[++h] = "</tr>		\n";
			}
			$("#datalistStr").append(eleHtml.join(''));
		}else{
			_setTbodyNoResult($("#datalistStr"), 11, null );
		}
		
	}
	
	/*목록 검색 결과 없을시  */
	function _setTbodyNoResult(objBody, col, msg) {
		if(!msg) msg = "조회된 데이터가 없습니다.";
		objBody.append("<tr><td bgcolor='#ffffff' colspan='"+col+"' align=center>"+msg+"</td></tr>");
	}
	
	/*MARTNIS 전송하기*/
	function _eventProdSend(){
		var webOrdFrDt = $('#vendorWebOrdFrDt').val();
		var webOrdtoDt = $('#vendorWebOrdToDt').val(); 
		
		var nowTime = getCurrentTime();
		
		if( Number(nowTime) < Number(webOrdFrDt) || Number(nowTime) > Number(webOrdtoDt) ){
			alert("발주/반품 가능 시간은"+'<spring:message code="msg.weborder.vendor.send.time"/>'+"까지 입니다.");
			return;
		}
		
		loadingMaskFixPos();
		var str = { "venCd" 	: $("#entp_cd").val() };
		$.ajaxSetup({contentType: "application/json; charset=utf-8"});
		$.post(
				"<c:url value='/edi/weborder/PEDMWEB0005SendProd.do'/>",
				JSON.stringify(str),
				function(data){
					if(data == null || data.state != "0"){
						if(data.state == "1")
							alert("반품승인 전송대상(미전송, 오류) 상품정보가 없습니다.\n[CODE:"+data.state+"]");
						else
							alert("반품목록 승인요청 중 오류가 발생하였습니다.\n[CODE:"+data.state+"]");	
					}
					else  {
						
						alert("정상적으로 승인요청 처리 되었습니다.\n[ 정상:"+data.successCnt+" 오류:"+data.fallCnt+" 전체:"+data.totalCnt+" ]\n반품요청된 정보는 반품 전체 현황에서 확인 하실 수 있습니다.");
	  					doSearchReturnList();	//반품등록 현황 다시 조회
					} 
					hideLoadingMask();
				},	"json"
		);
	}
	
	/* 목록 초기화 (합계 ROW, 상품목록List)*/
	function _setTbodyMstInit(){
		$("#datalistStr tr").remove();	//상품목록 TBODY
		
		$("#storeCount"	).text('0');	//점포SUM
		$("#prodCount"	).text('0');	//상품SUM
		$("#qtySum"		).text('0');	//수량SUM
		$("#amtSum"		).text('0');	//금액SUM
		
	}
	
	/* 협력사별,상품별 점포코드 세팅 */
	function _storeSelectCodeOptionTag() {
		
		if(!$.trim($('#entp_cd').val())  ) return;
		
		
		var toSelectTagID   	= "#strCd";
		var firstOptionMessage	= "선택 ";
		var selectedVal   		= "";
		
		var  eleHtml = [], h = -1, j = -1;
		
		var str = {  "selectedCode" 	: $("#entp_cd").val()		 // 검색조건 상품코드 
		    	    ,"selectViewCode"	: $("#prodCd_view").text() 	 // 검색조건 업체코드
		      };
		
		//alert($("#prodCd_view").text());
			$.ajaxSetup({contentType: "application/json; charset=utf-8"});
			$.post(
				"<c:url value='/CommonCodeHelperController/venStoreSelectList.do'/>",
				JSON.stringify(str),
				function(data){
					if(data == null || data.length <= 0){
						return;
					}
					else  {
						
						var	sz = data.length;
						$(toSelectTagID + " option").remove();
						
						if(firstOptionMessage){
							eleHtml[++h] = "<option value=''>===== 선택 =====</option>";
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
	
	
	// 전체 체크박스 해제
	function chkAllStrBox(){
		$("input:checkbox[name=allCheckStr]").attr("checked", false);
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
</script>	

<style>
.dot_web0001 span{display:block;overflow:hidden;width:100px;height:18px;white-space:nowrap;text-overflow:ellipsis;-o-text-overow: ellipsis;-moz-binding:url(js/ellipsis.xml#ellipsis)undefinedundefinedundefined}
.dot_web0002 span{display:block;overflow:hidden;width:110px;height:18px;white-space:nowrap;text-overflow:ellipsis;-o-text-overow: ellipsis;-moz-binding:url(js/ellipsis.xml#ellipsis)undefinedundefinedundefined}

/* 오류 데이터 라인 + 글씨 Color 회색*/
.through_tr { font-style:italic; text-decoration:line-through; color:gray }

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
		<input type="hidden" name="vendorWebOrdFrDt" id="vendorWebOrdFrDt" value="${paramMap.vendorWebOrdFrDt}"/>
		<input type="hidden" name="vendorWebOrdToDt" id="vendorWebOrdToDt" value="${paramMap.vendorWebOrdToDt}"/>
		
		<div id="wrap_menu">
		
		<!--	@ 검색조건	-->
			
			<div class="bbs_search">
				<ul class="tit">
					<li class="tit">반품상품 조회</li>
					<li class="btn"><span style="font-weight: normal;  color: #414fbb; text-align: right;"><span><img src="/images/epc/btn/icon_04.png" alt="Notice" /></span> 협력업체 선택 후 상품을 검색하여 반품수량을 입력한 다음 등록하기 버튼을 클릭하세요. </span></li>
				</ul>
				<table class="bbs_search" >
					<colgroup>
						<col style="width:15%" />
						<col style="width:15%" />
						<col style="width:10%" />
						<col style="width:15%" />
						<col style="width:10%" />
						<col style="width:10%" />
						<col style="width:10%" />
						<col style="*" />
					</colgroup>
					<tr>
						<th>상품검색</th>
						<td colspan=7  style="border-bottom-color: #ffffff;">
							 <table>
								<tr>
									<td><b>업체코드 </b></td>
									<td><html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" formName="form" selectParam="" dataType="CP" comType="SELECT" /></td>
									<td><b>점포</b> : <select name="strCd" id="strCd" style="width: 130px;"></select></td>
									<td><b>상품코드</b></td>
									<td><input type="text" id="prodCd" name="prodCd" class="required"  style="width:100px;" maxlength="10" /></td>
									<td>
										<a href="#" class="btn" id="btnProdSearch" ><span>검색</span></a> 
										<span id="btnVendCdDel"> <img src="/images/epc/btn/icon_01.png" class="middle" style="cursor:pointer;" title="상품정보 초기화" /></span>	
										<!-- <img id="btnProImg" src="/images/epc/btn/icon_02.png" class="middle" style="cursor:hand;" title="상품찾기 팝업" /> -->
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<th>상품명</th>
						<td colspan="5" style="border-bottom-color: #ffffff; "><span id="prodNm_view"></span></td>
						<th>상품코드</th>
						<td style="border-bottom-color: #ffffff;"><span id="prodCd_view"></span></td>
					</tr>
					<tr>
						<th>판매코드</th>
						<td style="border-bottom-color: #ffffff;"><span id="srcmkCd_view"></span></td>
						<th>규격</th>
						<td style="border-bottom-color: #ffffff;"><span id="prodStd_view"></span></td>
						<th>입수</th>
						<td style="border-bottom-color: #ffffff;"><span id="ordIpsu_view"></span></td>
						<th>원가 / 매가 </th>
						<td style="border-bottom-color: #ffffff;">
							<span id="buyPrc_view"></span> / <span id="salePrc_view"></span>
						</td>
					</tr>
				</table>
				</div>
				<div class="bbs_search" style="margin-top: 2px;">	
				<table class="bbs_search" >
					<colgroup>
						<col style="width:15%" />
						<col style="*" />
					</colgroup>
					<%--
					<tr>
						<th >등록 점포선택</th>
						<td style="border-bottom-color: #ffffff;">
							<html:codeTag objId="areaCd" objName="areaCd" width="75px;" formName="form" dataType="AREA" comType="select"  defName="선택"  />
							<select name="strCd" id="strCd" style="width:120px">
								<option value="">선택</option>
							</select>
						</td>
					</tr>
					--%>
					<tr>
						<th>반품수량(EA) </th>
						<td style="border-bottom-color: #ffffff; ">
							<table>
							<tr>
								<td><b>재고</b>
								<input type="text" id="rrlStkQty" name="rrlStkQty" class="inputReadOnly" size="10" style="text-align: right; color: blue; width:50px;" readonly="readonly">
								</td>
								<td><b>EA</b> : <input type=text name="rrlQty" maxlength="10" size="10" id="rrlQty" style="text-align: right;" onkeypress="onlyNumber();" onblur="amtFormat(this);" onFocus="unNumberFormat(this);">
								</td>
								<%-- <td><b>금액</b> : <input type=text name=text01  id=""> </td> --%>
								<td>
								<!-- <a href="#" class="btn" id="btnProdSave" ><span style="font-weight: bold; font-size:1.1em; color: red;">등록하기</span></a> -->
								<span style="font-weight: normal; color: #414fbb;">&nbsp; <span><img src="/images/epc/btn/icon_04.png" alt="Notice" /></span> 반품은 수량으로  입력하세요.
								<img src="/images/epc/btn/icon_04.png" alt="Notice" />&nbsp; 반품가능 시간은 <span style="color: red;"><spring:message code="msg.weborder.vendor.send.time.rtn"/> </span>입니다.</span>
								</td>
							</tr>	
							</table>
						</td>
					</tr>
				
				</table>
			</div>
			
			
			<div class="wrap_con">
				<!-- list -->
				<div class="bbs_list">
					<ul class="tit">
						<li class="tit">등록내역 </li>
						<li class="btn">
							<!-- <a href="#" class="btn" id="btnProdSend"><span>반품요청</span></a>
							<a href="#" class="btn" id="btnProdDelete" ><span>삭제</span></a -->>
							<a href="#" class="btn" id="btnProdSelect" ><span>조회</span></a>
						</li>
					</ul>
			
					<table id="dataTable" cellpadding="1" cellspacing="1" border="0" bgcolor=efefef width="100%">
                        <colgroup>
							<col width="31px"/>
							<col width="50px"/>
							<col width="55px"/>
							<col width="110px"/>
							<col width="80px"/>
							<col width="90px"/>
							<col width="*"/>
							<col width="55px"/>
							<!-- <col width="60px"/>  -->
						<!--	<col width="50px"/> -->
						 	<col width="60px"/>  	
							<col width="100px"/>
							<col width="18px"/>
						</colgroup>
						<thead>
                        	<tr bgcolor="#e4e4e4" align=center> 
                          		<th>No.</th>
                          		<th>삭제<input type="checkbox" id="allCheckStr" name="allCheckStr"></th>
                          		<th>상태</th>
                          		<th>점포</th>
                          		<th>상품코드</th>
                          		<th>판매코드</th>
                          		<th>상품명</th>
                          		<th>입수</th>
                          	<!-- 	<th>재고</th> -->
                          	<!--  <th>단위</th> -->	
                          		<th>수량</th>
                          		<th>금액</th>
                          		<th rowspan="2"></th>
                        	</tr>
                      		<tr bgcolor="87CEFA"> 
                        		<td colspan="3" bgcolor="#929292"><span style="width:100%; font-weight: bold; text-align: center; color: #ffffff;">합 계</span></td>
                          		<td>총[ <span id="storeCount" style="font-weight: bold; font-size: 1.1em; color:red;">0</span> ]개점</td>
                          		<td colspan="3">총[ <span  id="prodCount"  style="font-weight: bold; font-size: 1.1em; color:red;">0</span> ]개상품</td>
                          		<td style="text-align: center;">-</td>
                          		<!-- <td style="text-align: center;">-</td> -->
                          		<td style="text-align: right;"><span id="qtySum" style="font-weight: bold; font-size: 1.1em;">0</span></td>
                          		<td style="text-align: right;"><span id="amtSum" style="font-weight: bold; font-size: 1.1em;">0</span></td>
                          	</tr>
                        </thead>
                        	 <tr> 
		                     	<td colspan=11>   
		                        	<div id="_dataList1" style="background-color:#FFFFFF; margin: 0; padding: 0; height:346px; overflow-y: scroll; overflow-x: hidden">
		                        		<table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=100% bgcolor="#EFEFEF">
		                     			<colgroup>
											<col width="30px"/>
											<col width="50px"/>
											<col width="55px"/>
											<col width="110px"/>
											<col width="80px"/>
											<col width="90px"/>
											<col width="*"/>
											<col width="55px"/>
											<!-- <col width="60px"/> -->
										<!--	<col width="50px"/> -->
										 	<col width="60px"/>  	
											<col width="100px"/>
										</colgroup>
										<tbody id="datalistStr" />
		                     			</table>
		                     		</div>
		                     	</td>
		                    </tr>
					</table>
					
				</div>
			</div>
		</div>
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
					<li>반품등록</li>
					<li class="last">상품별 등록</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>
</html>
