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



	$(function() {
		

		/* 관리자 로그인 아닌경우 세션 종류*/
		<c:if test="${empty epcLoginVO.adminId}">	
			 doLogout();
		</c:if>
		
		_init();
		
		
		/*-------------------------- MDer설정 */
		$('#btnEmpClear').unbind().click(null, _eventClearMder);
		
		$('#findEmpNo').unbind().keydown(function(e) {
			switch(e.which) {
		   	case 13 : _eventMDerFind(this); break; // enter
		   	default : return true;
		   	}
	    	e.preventDefault();
		});
		/*----------------------------------*/
		
		
		
		
		//버튼 CLICK EVNET ---------------------------------------------------------------
		$('#btnFix').unbind().click(null, 		doFix);		// 발주확정
		$('#btnDel').unbind().click(null, 	 	doDelete);	// 삭제
		$('#btnSearch').unbind().click(null, 	doSearch);	// 조회
	
		
	
		/*-------------------------- 업체정보찾기 */
		$('#btnVendCdfind').unbind().click(null, doVendFind);	// 업체코드찾기
		$('#btnVendCdDel').unbind().click(null, delVendorInto);	// 업체코드지움
		/*----------------------------------*/
		
		//--------------------------------------------------------------------------------
		
		
		// 권역구분, 점포코드 , 협력업체코드, 작업구분 enter key이벤트 --------------
		$('#vendNm,input[name=pageRowCount]').unbind().keydown(function(e) {
			switch(e.which) {
		   	case 13 : doSearch(this); break; // enter
		   	default : return true;
		   	}
	    	e.preventDefault();
	});
	
		
		$('#vendCd').unbind().keydown(function(e) { // 업체코드
			switch(e.which) {
	    		case 13 : doVendCdFind(this); break; // enter
	    		default : return true;
	    	}
	    	e.preventDefault();
	   	});
		
	
	
	
		/* 페이지번호 Click Event 발생시 조회함수 호출하다. */
		$('#paging a').live('click', function(e) {
			// #page : 서버로 보내는 Hidden Input Value 
			$('#page').val($(this).attr('link'));
			// 개발자가 만든 조회 함수
			doSearch();
		});
			
		
		/*-------------------------- 업체정보찾기 */
		/* 업체코드 포커스를 잃었을 경우 업체코드가 공백이면 업체코드 삭제 */
	 	$('#vendCd').live('blur', function(e) {
	 		if(!$.trim($("#vendCd").val())){
	 			$("#vendNm").val('');
			}
		}); 
		
	 	/*--------------------------------------*/
		
	});

	
	function doLogout()
	{
		var form = document.forms[0];
		var url = '<c:url value="/common/epcLogout.do"/>';
		form.target="_parent";
		form.action=url;
		form.submit();
	}
		

	/* 초기 설정 */
	function _init(){
		_setTbodyNoResult($("#datalist"), 	9, '<spring:message code="text.web.field.srchInit10"/>');	// prod tBody 설정
	}

	/*-- 업체정보찾기 */
	function doVendCdFind(){
	
		<%-- 조회조건 검증 ============================= --%>
		var venCd  =  $("#vendCd").val();  // 업체코드 조건
		
		if(!$.trim(venCd)){
			$('#btnSearch').trigger('click');
			return false;
		}
	
		if( venCd.length != 6 ){
			alert("<spring:message code='msg.web.error.venCdMin6'/>");
			$("#vendCd").focus();
			return false;
		}

		
		
		if(!isNumber(venCd)) {
			alert("<spring:message code='msg.web.error.valVenCd'/>");
			$('#vendCd').val('');
			$('#vendCd').focus();
			return false;
		}
		
		
		
		delVendorInto();
		
		 if(!$('#empNo').val()) {
				alert("<spring:message code='msg.web.error.mder'/>");
				$('#findEmpNo').focus();
				return false;
		 }	

		
		var str = {  "venCd" 	: venCd,	//검색조건  
			     	 "empNo"    :  $("#empNo").val() 
	      };
		
		//loadingMaskFixPos();
		$.ajaxSetup({
	  		contentType: "application/json; charset=utf-8" 
			});
		$.post(
				"<c:url value='/edi/weborder/NEDMWEB0099SearchEmpVendor.json'/>",
				JSON.stringify(str),
				function(data){
					if(data == null || data.state !="0"){
						alert("<spring:message code='msg.web.error.venCd' arguments='"+data.state+"'/>");	
						//hideLoadingMask();
					 	return;
					}
					else
					{
						var venData = data.venData;
						
						if (venData == null || !venData.venCd) {
					
							alert("<spring:message code='msg.web.error.srchVenCd'/>");
							//$('#btnVendCdfind').trigger('click');
							doVendFind();
							//hideLoadingMask();
						}else{

							$('#vendCd').val(venData.venCd);
							$('#vendCdOrg').val(venData.venCd);
							$('#vendNm').val(venData.venNm);	
							//$('#btnSearch').trigger('click');
							//hideLoadingMask();
							
						}
						
					}
					
					
					//hideLoadingMask();
					
				},	"json"
			);
	

	} 
	

//업체코드 검색 팝업 호출 
 function doVendFind(){
	

	 var param = new Object()
	 	, site = "<c:url value='/common/PEDMCOM0008.do'/>"
		, style ="dialogWidth:700px;dialogHeight:450px;resizable:yes;";	

		 if(!$('#empNo').val()) {
				
				alert("<spring:message code='msg.web.error.mder'/>");
				$('#findEmpNo').focus();
				return false;
		 }	

		
		param.venCd = "";
		param.venNm = "";
		param.empNo = $('#empNo').val();

		window.showModalDialog(site, param, style);

		if(param && param.venCd){ 

			$('#vendCd').val(param.venCd);
			$('#vendCdOrg').val(param.venCd);
			$('#vendNm').val(param.venNm);
		}
		
		return false;
	
 }	

 //검색된 업체코드 값 지움
 function delVendorInto(){
	 
	 $("#vendCd").val('');
	 $("#vendCdOrg").val('');
	 $("#vendNm").val('');
 }
		
		

 
 	/*발주 확정대상 조회*/
 	function doSearch(){
 		
 		 if(!$('#empNo').val()) {
 				alert("<spring:message code='msg.web.error.mder'/>");
 				$('#findEmpNo').focus();
 				return false;
 		 }	

 		
		var str = {"venCd": $("#vendCd").val(),
				  "empNo" : $("#empNo").val(),
				  "page"  : $("#page").val(),
				  "pageRowCount"	: $('input[name="pageRowCount"]:radio:checked').val()
		  		  };
		
		
	    //loadingMaskFixPos();
	 
		$.ajaxSetup({
	  		contentType: "application/json; charset=utf-8" 
			});
		$.post(
				"<c:url value='/edi/weborder/NEDMWEB0210Select.json'/>",
				JSON.stringify(str),
				function(data){
					if(data == null || data.state != "0"){
					 	alert("<spring:message code='msg.web.error.srchError' arguments='"+data.state+"'/>");	
					}
					else _setTbodyMasterValue(data);
					//hideLoadingMask();
				},	"json"
			);


	} 
	
 
 
 
	// 점포별 상세 상품 목록을 조회
	function _doDetailSearch(venCd){
		var str = {"venCd":venCd};
	    //loadingMaskFixPos(); 
		$.ajaxSetup({
	  		contentType: "application/json; charset=utf-8" 
			});
		$.post(
				"<c:url value='/edi/weborder/NEDMWEB0210StrCdSelect.json'/>",
				JSON.stringify(str),
				function(data){
					_setTbodyStoreOrdDetailValue(data, venCd);
					//hideLoadingMask();
				},	"json"
			);
		

		
	}
 
	//업체 점포별 발주목록 조회
 	function _setTbodyMasterValue(json){
 		_setTbodyInit();
 		var data = json.VenList, eleHtml = [], h = -1, pagHtml = [], j = -1,eleHtmlSum=[],dataSum=json.VenListSum,h1 = -1;

 		if(data != null){
			var sz = json.VenList.length;
			if (sz > 0) {
				for ( var k = 0; k < sz; k++) {
					eleHtml[++h] = '<tr id="strMstTr_'+data[k].venCd+'">' + "\n";
					eleHtml[++h] = "\t" + '<td align="center">-</td>' + "\n";
					eleHtml[++h] = "\t" + '<td align="center"><input type="checkbox" name="strAllCheck_'+data[k].venCd+'" onclick="javascript:selectAllCheckbox(\''+data[k].venCd+'\')"   disabled="disabled"><input type="hidden" id="openYn_'+data[k].venCd+'" value="N"></td>' + "\n";
					eleHtml[++h] = "\t" + '<td align="left" colspan="3" ><img id="detImg_'+data[k].venCd+'" src="/images/epc/layout/lnb_plus.gif" class="middle" onclick="javascript:selectStoreOrdDetail(\''+ data[k].venCd+'\')" style="cursor:hand;" />&nbsp;<span id="detTxt_'+data[k].venCd+'"><spring:message code="text.web.field.detTxtOpn"/></span>'+ "\n";
					
					eleHtml[++h] = "\t" + '<strong>'+data[k].venNm+'('+data[k].venCd+')</strong> '  + "\n";
					
					eleHtml[++h] = "\t" + '<div style="float:right; font-size:1.0em; text-align:right; margin-right:5px;">' + "\n";
					eleHtml[++h] = "\t" + '<span style="color:#d5855e; font-weight: bold;"><spring:message code="text.web.field.tottPrd"/>:'+data[k].totCnt+' </span>&nbsp;'  + "\n";
					eleHtml[++h] = "\t" + '<span style="color:#9d8f89;"><spring:message code="text.web.field.sendn"/>:'+data[k].notSendSum+'	</span>&nbsp;'  + "\n";
					eleHtml[++h] = "\t" + '<span style="color:#4f88c1;"><spring:message code="text.web.field.sendy"/>:'+data[k].sucSendSum+'		</span>&nbsp;'  + "\n";
					eleHtml[++h] = "\t" + '<span style="color:#d24d66;"><spring:message code="text.web.field.sendError"/>:'+data[k].falAllSum+' 		</span>'  + "\n";
					eleHtml[++h] = "\t" + '</div>' + "\n";					
					
					eleHtml[++h] = "\t" + '</td>' + "\n";
					eleHtml[++h] = "\t" + '<td align="right"><span style="font-weight: bold; color:blue;"> '+amtComma(data[k].ordTotQty)+'</span> </td>' + "\n";
					eleHtml[++h] = "\t" + '<td align="right"><span style="font-weight: bold; color:blue;"> '+amtComma(data[k].ordTotAllQty)+'</span></td>' + "\n";
					eleHtml[++h] = "\t" + '<td align="right"><span style="font-weight: bold; color:blue;"> '+amtComma(data[k].ordTotPrc)+' </span>' + "\n";
					eleHtml[++h] = "\t" + '<input type="hidden" name="venCd" id="venCd" value='+data[k].venCd+'></td>' + "\n";
					eleHtml[++h] = '</tr>' + "\n";
				}	
				
		 if(dataSum !=null){
					eleHtmlSum[++h1] = '<tr  class="r1" bgcolor=87CEFA>' + "\n";
					eleHtmlSum[++h1] = "\t" +'<td colspan="5" align="center"  style="font-weight:bold;" ><spring:message code="text.web.field.totalSum"/></td>' + "\n";
					eleHtmlSum[++h1] = "\t" +'<td align="right"><span style="font-weight: bold; color:red; font-size:1.1em;">'+ amtComma(dataSum.ordTotQtySum)+'</span></td>' + "\n";
					eleHtmlSum[++h1] = "\t" +'<td align="right"><span style="font-weight: bold; color:red; font-size:1.1em;">'+ amtComma(dataSum.ordTotAllQtySum)+'</span></td>' + "\n";
					eleHtmlSum[++h1] = "\t" +'<td align="right"><span style="font-weight: bold; color:red; font-size:1.1em;">'+ amtComma(dataSum.ordTotPrcSum)+'</span></td>' + "\n";
					eleHtmlSum[++h1] = "\t" +'<tr>' + "\n";
		 }
			
				$("#datalist").append(eleHtml.join('')).append(eleHtmlSum.join(''));
			}
		}else {
			_setTbodyNoResult($("#datalist"), 9, null );
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
	
 	
 	
	// 점포별 상세 상품 목록 리스트에 뷰.
	function _setTbodyStoreOrdDetailValue(json, venCd){

		//alert("venCd  "   +  venCd);
		var data = json.strCdList, eleHtml = [], h = -1;
		
		if(data != null){
			var sz = json.strCdList.length;
			if (sz > 0) {
				
				var cnt=0;
				for ( var k = 0; k < sz; k++) {
					eleHtml[++h] = '<tr bgcolor=ffffff id="detStrInfo_'+data[k].venCd+'">' + "\n";
					eleHtml[++h] = "\t" + '<td align="center">'+ ++cnt +'</td>' + "\n";
					if(data[k].ordCfmQty==0 || data[k].ordPrgsCd != 02 ){
						eleHtml[++h] = "\t" + '<td align="center"><input type="checkbox" id="box" name="strCheck_'+data[k].venCd+'"  disabled></td>' + "\n";
					}else{
						eleHtml[++h] = "\t" + '<td align="center"><input type="checkbox" id="box" name="strCheck_'+data[k].venCd+'" ></td>' + "\n";
					}
					
					eleHtml[++h] = "\t" + '<td align="center" style="color: blue;"><a href="javascript:prodDelPopUp(\''+data[k].ordReqNo+'\',\''+ data[k].ordPrgsCd  +'\' )">' +"\n";
					eleHtml[++h] = "\t" + '<span><img src=/images/epc/btn/bt_search.gif align="absmiddle"> '+data[k].ordReqNo+'</span></a>' + "\n";
					
					eleHtml[++h] = "\t" + '<input type="hidden" name="ordReqNo" value='+data[k].ordReqNo+'></td>' + "\n";
					eleHtml[++h] = "\t" + '<td align="center" ">&nbsp;'+data[k].strCd+'</td>' + "\n";
					eleHtml[++h] = "\t" + '<td><strong>'+data[k].strNm+'</strong> ('+data[k].totCnt+')' + "\n";
					/*
					eleHtml[++h] = "\t" + '<span style="float:right; font-size:1.0em; text-align:right; margin-left:5px;">' + "\n";
					eleHtml[++h] = "\t" + '총상품:'+data[k].totCnt+'  '  + "\n";
					eleHtml[++h] = "\t" + '미전송: '+data[k].notSendSum+'  '  + "\n";
					eleHtml[++h] = "\t" + '전송:'+data[k].sucSendSum+'  '  + "\n";
					eleHtml[++h] = "\t" + '오류:'+data[k].falAllSum+'  '  + "\n";
					eleHtml[++h] = "\t" + '</span>' + "\n";					
					*/
					eleHtml[++h] = "\t" + '</td>' + "\n";
					eleHtml[++h] = "\t" + '<td align="right">&nbsp;'+amtComma(data[k].ordCfmQty)+'</td>' + "\n";
					eleHtml[++h] = "\t" + '<td align="right" >&nbsp;'+amtComma(data[k].eaQty)+'</td>' + "\n";
					eleHtml[++h] = "\t" + '<td align="right">&nbsp;'+amtComma(data[k].eaPrc) +'</td>' + "\n";
					eleHtml[++h] = "\t" + '</tr>' + "\n";	
				}

				$("#strMstTr_"+venCd).after(eleHtml.join(''));
			}
		}
	}
	
 	
 	

 	
	/*목록 검색 결과 없을시  */
	function _setTbodyNoResult(objBody, col, msg) {
		if(!msg) msg = "<spring:message code='text.web.field.srchNodData'/>";
		objBody.append("<tr><td bgcolor='#ffffff' colspan='"+col+"' align=center>"+msg+"</td></tr>");
	}
 	 
 	 
	
	
 	
	/*MARTNIS 전송하기*/
	function doFix(){
		
		
	   /* 발주전송 전체 여부*/
	   var orderSendfg =	$('input[name="orderSendfg"]:radio:checked').val();
	   var strDataList = [];
	   
	   /* 화면에 있는 업체코드만 전송*/
	   if(orderSendfg ==2){
		
			$("[id^=strMstTr]").each(function (index){
				var strData ={};
	
				$(this).find('input').map(function() {
					if(this.name=='venCd'){
						strData[this.name]=$(this).val();
						strData['procEmpNo'] = $("#empNo").val();	
					}
				});
			
				strDataList.push(strData);		
			});
	
			if(strDataList == null|| strDataList.length == 0){
				alert('<spring:message code="msg.common.error.no.data"/>');
				//hideLoadingMask();
				return;
			}  
	   }else{
			var strData ={};
			strData['procEmpNo'] = $("#empNo").val();	
			strDataList.push(strData);	
	   }
	   

	   
	   if (!confirm('<spring:message code="msg.common.confirm.ord.send"/>')) {
       		return;
   	    }

	   
		
		//loadingMaskFixPos();

		$.ajaxSetup({contentType: "application/json; charset=utf-8"});
		$.post(
				"<c:url value='/edi/weborder/NEDMWEB0210SendProd.json'/>",
				/* JSON.stringify(str), */
	            JSON.stringify({'tedOrdList' : strDataList}),
				function(data){
					if(data == null || data.state != "0"){
						
						if(data.state == "1")
							 alert("<spring:message code='msg.web.error.prodOrdSend1' arguments='"+data.state+"'/>");
						else alert("<spring:message code='msg.web.error.prodOrdSendElse' arguments='"+data.state+"'/>");
					}
					else  {
						alert("<spring:message code='msg.web.error.prodOrdSend' arguments='"+data.successCnt+","+data.fallCnt+","+data.totalCnt+"'/>");
						doSearch();	//반품등록 현황 다시 조회
					} 
					
					//hideLoadingMask();
				},	"json"
		);

		
	
	}
	

	
	
 	/*ROW 펼침,숨김 Click Evnet*/
 	function selectStoreOrdDetail(venCd){
 	

 		var openYn = $('#openYn_'+venCd).val();
 		var strDetSz = $('#detStrInfo_'+venCd).length;
 		

 		
 		if(openYn == 'N') {
 			
			$("#detImg_"+venCd).attr("src", "/images/epc/layout/lnb_minus.gif");
			$("#detTxt_"+venCd).text("<spring:message code='text.web.field.detTxtCls' />");
			$('#openYn_'+venCd).val("Y");
			
			if(strDetSz > 0){
				_doViewDetDiv(venCd, "Y");
			}else{
				 $("input[name=strAllCheck_"+venCd+"]").attr("disabled",false);
				_doDetailSearch(venCd);
			}
 		}else{
 			
 			$("#detImg_"+venCd).attr("src", "/images/epc/layout/lnb_plus.gif");
			$("#detTxt_"+venCd).text("<spring:message code='text.web.field.detTxtOpn' />");
			$('#openYn_'+venCd).val("N");
			
			_doViewDetDiv(venCd, "N");
 			
 		}
		 
	}
	
 	
	// +/- 상태에 따라 상품 목록을 show/hide
	function _doViewDetDiv(venCd, openYn){
		
		
		if(openYn == 'Y'){
			$('#datalist tr').each(function(i){
				if(this.id == 'detStrInfo_'+venCd){
					$(this).css("display","");
				}
			});
		}else{
			$('#datalist tr').each(function(i){
				if(this.id == 'detStrInfo_'+venCd){
					$(this).css("display","none");
				}
			}); 
		}
	}
 	
 	

	
	/* 목록 초기화 */
	function _setTbodyInit() {
		$("#datalist tr").remove();
		$("#paging").empty();
	}
	

	
	// 점포별 체크박스 전체 선택 및 전체 해제
	function selectAllCheckbox(venCd){		
		if($("input:checkbox[name=strAllCheck_"+venCd+"]").is(":checked")){
			$("input:checkbox[name=strCheck_"+venCd+"]").attr("checked", true);
		}else{
			$("input:checkbox[name=strCheck_"+venCd+"]").attr("checked", false);
			$("input:checkbox[name=allCheck]").attr("checked", false);
		}
		

		
		$('input:checkbox[id=box]:checked').each(function() {
				if($(this).attr("disabled")){
				$(this).attr("checked", false);   
			}	
		});

	}
	

	
	/*점포별 상세 상품목록 모달팝업*/
	function prodDelPopUp(ordReqNo,ordPrgsCd){
		
		var param = new Object()
		 	, site = "<c:url value='/edi/weborder/NEDMWEB0210Popup.do'/>"
			, style ="dialogWidth:830px;dialogHeight:546px;resizable:yes;";	

		param.ordReqNo 	= ordReqNo;
		param.ordPrgsCd = ordPrgsCd;
		param.reLoad 	= "F";

		window.showModalDialog(site, param, style);

		if(param && param.reLoad =="T") doSearch();	
			
	}

	
	
	/* 삭제버튼 이벤트 */
	function doDelete(){
		
		var ordReqNo = "";
		$("[id^=strMstTr_]").each(function (index){
			var venCd = $(this).attr('id').replaceAll("strMstTr_", "");
		
				
			$("[id^=detStrInfo_"+venCd+"]").each(function (index){
			
				var strData ={};
				if($(this).find("input[name=strCheck_"+venCd+"]").is(":checked")){
				
					$(this).find('input').map(function() {
						if(this.type != 'button' || this.type != 'checkbox'){		
							if(this.name == 'ordReqNo'){
								if(ordReqNo) ordReqNo +=",";	
								ordReqNo += $(this).val();
							}
						}
					});
				}
			});
		});
		
		
		var ordReqNos =  ordReqNo.split(",");
		
		if(ordReqNos == null || ordReqNos.length == 0){
			alert('<spring:message code="msg.common.error.no.data"/>');
			//hideLoadingMask();
			return;
		}
		
		if (!confirm('<spring:message code="msg.common.confirm.delete"/>')) {
            return;
        }
		
		//loadingMaskFixPos();
		
		/*삭제 조건*/
		var str = {   "ordReqNos" 	: ordReqNos		// 발주번호
		};
		
		$.ajaxSetup({
	  		contentType: "application/json; charset=utf-8" 
			});
	 	$.post("<c:url value='/edi/weborder/NEDMWEB0210Delete.json'/>"
	 			
	  			,JSON.stringify(str)
	  			,function(data){
	 		
	 		
					if(data == null || data.state != "0"){
						if(data.state == "1")
							 alert("<spring:message code='msg.web.error.prodOrdDel' arguments='"+data.state+"'/>");
					}
					else  {
						alert('<spring:message code="msg.common.success.delete"/>');
						doSearch();	//현황 다시 조회
					} 
				 	//hideLoadingMask();
				}, 'json'); 
	

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
			if(event.preventDefault){
				//  IE  
	        	event.preventDefault();
	    	} else {
	    		//  표준 브라우저(IE9, 파이어폭스, 오페라, 사파리, 크롬)
	        	event.returnValue = false;
			}
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
	
	
	
	function _eventClearMder(){
		$('#findEmpNo').val('');
		$('#findEmpNm').val('');
		$('#empNo'  ).val('');
	}
	//MDer find
	function _eventMDerFind(){
		var empNo = $("#findEmpNo").val();	 // 검색사번
		//console.log(empNo);
		<%-- 조회조건 검증 ============================= --%>
	/* 	if(!$.trim(empNo) || empNo.length != 9 ){
			alert("사번 9자리를 정확히 입력하세요!");
			$("#findEmpNo").focus();
			return false;
		} */
		
		if(!$.trim(empNo)){
			alert('<spring:message code="msg.web.error.empNo"/>');
			$("#findEmpNo").focus();
			return false;
		}
		<%-- ----------------------------------------- --%>
		
		$('#findEmpNm').val('');
		$('#empNo'  ).val('');
		
		//loadingMaskFixPos();
		
		
		var str = {  "empNo" 	: empNo	//검색조건  
			      };
	
			$.ajaxSetup({
		  		contentType: "application/json; charset=utf-8" 
				});
			$.post(
					"<c:url value='/edi/weborder/NEDMWEB0099SearchEmplPool.json'/>",
					JSON.stringify(str),
					function(data){
						
						if(data == null || data.state !="0"){
							alert("<spring:message code='msg.web.error.empNoError' arguments='"+data.state+"'/>");
							//hideLoadingMask();
						 	return;
						}
						else
						{
							var empPool = data.empPool;
							
							if(empPool ==null || !empPool.empNo) {
								alert("<spring:message code='msg.web.error.noDataEmpNo'/>");
								//hideLoadingMask();
							 	return;
							}
							
							$('#findEmpNm').val(empPool.empNm);
							$('#empNo'  ).val(empPool.empNo);
							
							
						}
						//hideLoadingMask();
						
					},	"json"
			);
	
	}

	
</script>

<style>
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

	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:<c:out value='${param.widthSize }'/></c:if>  >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form name="searchForm" method="post" action="#">
		<input type="hidden" id="widthSize" name="widthSize" value="<c:out value='${param.widthSize }'/>" > 
		<input type="hidden" name="page" id="page" value="1" />

		<div id="wrap_menu">
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit"><li class="btn">
							<span style="margin-right: 2px;">
							  <span style="color: #c84545; font-size:0.95em; font-weight:"><spring:message code='text.web.field.confMder'/></span>
							  <input type="text" 	id="findEmpNo" maxlength="9" style="border:1px solid #E0E0E0; font-size:1.0em; color:#488ce7; height: 18px; width: 60px; ime-mode:disabled;">
							  <input type="text" disabled="disabled" id="findEmpNm"  style="width:60px; border:1px solid #E0E0E0; color:4A4A4A; background-color:#EEECEA;">
							  <span id="btnEmpClear"> <img src="/images/epc/btn/icon_01.png" class="middle" style="cursor:pointer;" title="삭제" /></span>
							  <input type="hidden"  id="empNo">
							</span>
					
					</li></ul>
					<ul class="tit">
						<li class="tit"><spring:message code='text.web.field.searchCondition'/>  </li>
						<li class="btn">
							<span style="margin-left: 60px; margin-right:20px; text-align: right;"> 
								<span><img src="/images/epc/btn/icon_04.png" alt="Notice" /></span>  
								<span style="font-weight:normal; color: #414fbb;"><spring:message code='text.web.field.prodOrdInfo'/></span> 
							</span>
							<a href="#" class="btn" id ="btnDel" ><span><spring:message code="button.common.delete"/></span></a> 
							<a href="#" class="btn" id ="btnSearch"><span><spring:message code="button.common.inquire"/></span></a>
						</li>
					</ul>
					<table class="bbs_search" cellpadding="1" cellspacing="1" border="0">
					
					<input type="hidden" id="storeVal" name="storeVal"  value="<c:out value='${param.storeVal} '/>}" />
					<input type="hidden" id="productVal" name="productVal" />
					<input type="hidden" id="entpCode" name="entpCode" />
					<colgroup>
				
					
					
						<col style="width:15%" />
						<col style="width:6.2%" />
						<col style="width:16%" />
						<col style="width:2%" />
						<col style="width:4.2%" />		
						
						<col style="width:15%" />
						<col style="width:25.6%" />
						<col style="width:100px" /> 
					</colgroup> 
					<tr>
					
					 
					
						<th><spring:message code='text.web.field.venCd'/></th>
						<td>
							<input type="text" 	 id="vendCd" 	name="vendCd" 	maxlength="6" style="width: 50px; " onkeypress="onlyNumber();">
							<input type="hidden" id="vendCdOrg" name="vendCdOrg">
						</td>
						<td><input type="text" id="vendNm" name="vendNm" style="width: 100%;" class="inputReadOnly" readonly="readonly" ></td>
						<td><span id="btnVendCdDel"> <img src="/images/epc/btn/icon_01.png" class="middle" style="cursor:pointer;" title="삭제" /></span></td>
						<td><span id="btnVendCdfind"><img src="/images/epc/btn/icon_02.png" class="middle" style="cursor:pointer;" title="협력사찾기팝업" /></span></td>
							
						<th><spring:message code='text.web.field.ordAcc'/></th>
						<td>
							<input type="Radio" name="orderSendfg" value="1" <c:if test="${paramMap.orderSendfg eq '1'}"> Checked</c:if> /> <spring:message code='text.web.field.allAcc'/>
							<input type="Radio" name="orderSendfg" value="2" <c:if test="${paramMap.orderSendfg eq '2'}"> Checked</c:if> /> <spring:message code='text.web.field.srchListAcc'/>
						</td>
						<td>
							<a href="#" class="btn" id ="btnFix" ><span style="color: red; font-weight: bold"><spring:message code="button.common.confirmation"/></span></a>
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
						<li class="tit"><spring:message code='text.web.field.searchList'/> </li>
					</ul>
					
					
					
			
					<table id="dataTable" cellpadding="1" cellspacing="1" border="0"  width=100% bgcolor="#EFEFEF">
                        <colgroup>
							<col width="31px"/>
							<col width="30px"/>
							
							<col width="120px"/>
							<col width="60px"/>
							<col width="*"/>
			
							
							<col width="70px"/>
							<col width="70px"/>
							<col width="100px"/>
							<col width="18">
						</colgroup>	
						<thead>
                        <tr bgcolor="#e4e4e4" align=center> 
                          <th rowspan="2"><spring:message code='epc.web.header.No'/></th>
                          <th rowspan="2"><spring:message code='epc.web.header.delete'/></th>
	                      <th colspan="3"><spring:message code='epc.web.header.prodInfo'/></th>
	                      <th colspan="3"><spring:message code='epc.web.header.ordInfo'/></th>
	                      <th rowspan="2"></th>
                        </tr>
                        <tr bgcolor="#e4e4e4" align=center>
						  <th><spring:message code='epc.web.header.ordCd'/></th>
                          <th><spring:message code='text.web.field.strCd'/></th>
                          <th><spring:message code='text.web.field.strNm'/></th>
                          <th><spring:message code='epc.web.header.unitQty'/></th>
                          <th><spring:message code='epc.web.header.ea'/></th>
                          <th><spring:message code='epc.web.header.ea'/></th>
                        </tr>
                        </thead>
                        
                        
                         <tr> 
                         <td colspan=9>   
	                         <div id="_dataList1" style="background-color:#FFFFFF; margin: 0; padding: 0; height:380px; overflow-y: scroll; overflow-x: hidden">
		                         <table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=100% bgcolor="#EFEFEF">
		                        	<colgroup>
									<col width="30px"/>
									<col width="30px"/>

									<col width="120px"/>
									<col width="60px"/>
									<col width="*"/>
									
									<col width="70px"/>
									<col width="70px"/>
									<col width="100px"/>
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
		
		
		
		
		</div>
		</form>
	</div>


	<!-- footer -->
	<div id="footer">
		<div id="footbox">
			<div class="msg" id="resultMsg"></div>
			<div class="notice"></div>
			<div class="location" >
				<ul>
					<li><spring:message code='epc.web.menu.lvl1'/></li>
					<li><spring:message code='epc.web.menu.lvl2'/></li>
					<li><spring:message code='epc.web.menu.mder'/></li>
					<li class="last"><spring:message code='epc.web.menu.prodAccMng'/></li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>
<font color='white'><b>PEDMWEB0010.jsp</b></font>

</html>
