<%--
- Author(s): 
- Created Date: 2014. 08. 25
- Version : 1.0
- Description :HELP PAGEㅇ

--%>
<%@include file="../common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" >

var opener;

$(function() {
	
	_init();
	
	//버튼 CLICK EVNET ---------------------------------------------------------------
	$('#btnDelete').unbind().click(null, 		doDelete);	// 발주삭제
	$('#btnSave').unbind().click(null, 	 		doSave);	// 발주수량 변경
	
	
	// 체크박스 전체 선택 --------------------------------------------------------------
	$('#allCheck').click(function(){
		if ($("#allCheck").is(":checked")) { 
			$('input:checkbox[id^=box]:not(checked)').attr("checked", true); 
		} else { 
			$('input:checkbox[id^=box]:checked').attr("checked", false); 
		} 
		
		
		$('input:checkbox[id=box]:checked').each(function() {
				if($(this).attr("disabled")){
				$(this).attr("checked", false);   
			}	
		});
			
	}); 
	
});


function _init(){
	



	 opener = window.dialogArguments;
	if(opener == null || !opener.ordReqNo) {
		alert("<spring:message code='msg.web.error.noVenCdOrd'/>");
		top.close();
		return;
	}
	else {
		$("#ordReqNo").val(opener.ordReqNo);
		$("#ordPrgsCd").val(opener.ordPrgsCd);

		doSearch();	
	}

	
}

/*점포별 발주상품 상세 조회*/
function doSearch(){
	
	var str = {"ordReqNo"	:	$("#ordReqNo").val()};
	
	loadingMaskFixPos();
	
	$.ajaxSetup({
  		contentType: "application/json; charset=utf-8" 
		});
	$.post(
			"<c:url value='/edi/weborder/NEDMWEB0210ProdSelect.json'/>",
			JSON.stringify(str),
			function(data){
				if(data == null || data.state !="0"){
					alert('<spring:message code="msg.web.error.selectOrdProdDetailError" arguments="' + data.state + '"/>');
				}
				else _setTbodyMasterValue(data);
				hideLoadingMask();
			},	"json"
		);	

}


//상품별 발주량 수정 저장
function doSave(){
	

	
	var numberCheck = true;
	var strDataList = [];
	

	$("[id^=detStrInfo]").each(function (index){
		var strData ={};
		
			if($(this).find("input[name=oldOrdCfmQty]").val() !=  $(this).find("input[name=ordCfmQty]").val()){
				
				$(this).find('input').map(function() {
					
					if(this.name == 'prodCd'  ||  this.name == 'ordReqNo' ||  this.name == 'ordCfmQty')
					{
						
					 	if( this.name == 'ordCfmQty'){ 
							
					 		if(!isNumber($(this).val())  || !emptyCheck($(this).val()) ) {
								numberCheck=false;
							}
						
						} 
						strData[this.name]=$(this).val();
					}	
					
				});
					
				strData['saveFg'] ='S';
				strDataList.push(strData);	
			}
	});
	
	
	
	if(strDataList == null|| strDataList.length == 0){
		alert('<spring:message code="msg.common.error.no.data"/>');
		return;
	}

	if(!numberCheck){ 
		alert('정상적인 단위수량을 입력하세요!');  
		return;
	}
	
	if (!confirm('<spring:message code="msg.common.confirm.save"/>')) {
        return;
    }
	
	loadingMaskFixPos();
	
	$.ajaxSetup({
  		contentType: "application/json; charset=utf-8" 
		});
 	$.post("<c:url value='/edi/weborder/NEDMWEB0210ProdDetUpdate.json'/>"
  			,JSON.stringify({'tedOrdList' : strDataList})
  			,function(data){
  				
  				if(data == null || data.state !="0"){
  					alert('<spring:message code="msg.common.fail.insert"/>\n[CODE:"+data.state+"]');
				}else{ 
 					alert('<spring:message code="msg.common.success.insert"/>');
 					opener.reLoad = "T";
					doSearch();
				}
  			 	hideLoadingMask();
			}, 'json');
 	

}



// 삭제
function doDelete_BACK(){

	var ordReqNo = "";
	var prodCd = "";
	var ordReqNoProdCd = "";
	var ordReq = "";

	$("[id^=detStrInfo]").each(function (index){
		// 상품 정보  ------------------------------------------
		
		$(this).find('input').map(function() {
		
			if(this.name == 'strAllCheck'){
				if($(this).is(":checked")){
		
					ordReqNo = $(this).parent().parent().children('td:last').children('input[name="ordReqNo"]').attr('value');
					prodCd   = $(this).parent().parent().children('td:last').children('input[name="prodCd"]').attr('value');
					
					if(ordReqNoProdCd) ordReqNoProdCd +=",";
					ordReqNoProdCd += ordReqNo+''+prodCd;
		

					if(ordReq) ordReq +=",";
					ordReq += ordReqNo;
					
					
				}
			}
		});
		
		
	});
	var ordReqNoProdCds = ordReqNoProdCd.split(",");
	var ordReqNos = ordReq.split(",").removeDup();
	
	if(!ordReqNoProdCds || ordReqNoProdCds.length == 0){
		alert('<spring:message code="msg.common.error.no.data"/>');
		return;
	}

	if (!confirm('<spring:message code="msg.common.confirm.delete"/>')) {
        return;
    }

	loadingMaskFixPos();

	/*삭제 조건*/
	var str = {   "ordReqNoProdCds" 	: ordReqNoProdCds		// 발주번호 
				  ,"ordReqNos"			: ordReqNos	
			 };

	$.ajaxSetup({
  		contentType: "application/json; charset=utf-8"
		});
 	$.post("<c:url value='/edi/weborder/PEDMWEB0210ProdDelete.json'/>"
 			,JSON.stringify(str)
  			,function(data){
 				if(data == null || data.state != "0"){
					if(data.state == "1")
						alert('<spring:message code="msg.web.error.noDelOrd" arguments="' + data.state + '"/>');
					else alert('<spring:message code="msg.common.fail.delete"/>\n[CODE:'+data.state+']');	
				}
				else  {
					alert('<spring:message code="msg.common.success.delete"/>');
					opener.reLoad = "T";
					doSearch();	//현황 다시 조회
				} 
 			 	hideLoadingMask();
  			}, 'json'); 
	

	
}





//삭제
function doDelete(){

	var strDataList = [];
	$("[id^=detStrInfo]").each(function (index){
		// 상품 정보  ------------------------------------------
		var strData= {};
		$(this).find('input').map(function() {
			if(this.name == 'strAllCheck'){
				if($(this).is(":checked")){
					strData['ordReqNo'] = $(this).parent().parent().children('td:last').children('input[name="ordReqNo"]').attr('value');
					strData['prodCd'] 	= $(this).parent().parent().children('td:last').children('input[name="prodCd"]').attr('value');
					strDataList.push(strData);
				}
			}
		});
	});
	

	if(strDataList.length == 0){
		alert('<spring:message code="msg.common.error.no.data"/>');
		return;
	}

	if (!confirm('<spring:message code="msg.common.confirm.delete"/>')) {
    	 return;
 	}

	loadingMaskFixPos();

	$.ajaxSetup({
		contentType: "application/json; charset=utf-8"
		});
	$.post("<c:url value='/edi/weborder/PEDMWEB0210ProdDelete.json'/>"
			,JSON.stringify({'tedOrdList' : strDataList})
			,function(data){
				if(data == null || data.state != "0"){
					if(data.state == "1")
						alert('<spring:message code="msg.web.error.noDelOrd" arguments="' + data.state + '"/>');
					else alert('<spring:message code="msg.common.fail.delete"/>\n[CODE:'+data.state+']');	
				}
				else  {
					alert('<spring:message code="msg.common.success.delete"/>');
					opener.reLoad = "T";
					doSearch();	//현황 다시 조회
				} 
				hideLoadingMask();
			}, 'json'); 
	
	
	
}



function _setTbodyMasterValue(json){ 
	_setTbodyInit();
	var data = json.VenProdList, eleHtml = [], h = -1, pagHtml = [], j = -1,eleHtmlSum=[],dataSum=json.VenProdListSum,h1 = -1,tabCnt = 0;

	if($('#tabIdx').val() != ""){
		tabCnt = Number($('#tabIdx').val());
	}
	
   
	
	if(data != null){
	var sz = json.VenProdList.length;

	if (sz > 0) {
		var cnt=0;
		for ( var k = 0; k < sz; k++) {
			tabCnt++;

			eleHtml[++h] = '<tr id="detStrInfo" bgcolor=ffffff>' + "\n";
			eleHtml[++h] = "\t" + '<td style="border-bottom-style:double; border-color:#c0c0c0;" rowspan=2  align="center">' + ++cnt +  '</td>' + "\n";
			
		
			if(data[k].ordCfmQty==0 || 	$("#ordPrgsCd").val() != 02){
				eleHtml[++h] = "\t" + '<td style="border-bottom-style:double; border-color:#c0c0c0;" rowspan=2  align="center"><input type="checkbox" id="box" name="strAllCheck"  disabled></td>' + "\n";
			}else{	
				eleHtml[++h] = "\t" + '<td style="border-bottom-style:double; border-color:#c0c0c0;" rowspan=2  align="center"><input type="checkbox" id="box" name="strAllCheck"  ></td>' + "\n";
			}

			eleHtml[++h] = "\t" + '<td style="border-bottom-style:double; border-color:#c0c0c0;" rowspan=2 align="center"><span title="'+data[k].regStsCdDetail+'" style=color:blue;>'+data[k].regStsNm+'</td>' + "\n";
			
			if(data[k].mdModCd == '00') {
				eleHtml[++h] = "\t" + '<td style="border-bottom-style:double; border-color:#c0c0c0;" rowspan=2 align="center">-</td>' + "\n";
			}else if(data[k].mdModCd == '01'){
				eleHtml[++h] = "\t" + '<td align="center" style="border-bottom-style:double; border-color:#c0c0c0; "  rowspan=2 ><a href="javascript:;" onClick="_viewMdState(this)"><spring:message code="epc.web.header.upt"/></a></td>' + "\n";
			}else if(data[k].mdModCd == '02'){
				eleHtml[++h] = "\t" + '<td style="border-bottom-style:double; border-color:#c0c0c0;" rowspan=2  align="center" style="font-weight: bold; text-decoration:underlin"><a href="javascript:;" onClick="_viewMdState(this)"><spring:message code="epc.web.header.del"/></a></td>' + "\n";
			}else{
				eleHtml[++h] = "\t" + '<td style="border-bottom-style:double; border-color:#c0c0c0;" rowspan=2  align="center" style="font-weight: bold; text-decoration:underlin"><a href="javascript:;" onClick="_viewMdState(this)"><spring:message code="epc.web.header.stp"/></a></td>' + "\n";
			}
			eleHtml[++h] = "\t" + '<td align="center" >'+data[k].prodCd+'</td>' + "\n";

			eleHtml[++h] = "\t" + '<td align="center"> '+data[k].srcmkCd+'</td>' + "\n";
			
			if( data[k].regStsCd==0 || data[k].regStsCd==9){
				eleHtml[++h] = "\t" + '<td style="border-bottom-style:double; border-color:#c0c0c0;" rowspan=2 align="center"><input type="text"  tabindex="'+tabCnt+'"  name="ordCfmQty" id="ordCfmQty" value="'+amtComma(data[k].ordCfmQty)+'" style="text-align: right; width:45px;"  maxlength="10"  onkeypress="onlyNumber();"   onblur="amtFormat(this); sumOrdInfo(this,\''+ data[k].prodCd+'\');"  onFocus="unNumberFormat(this);" >' + "\n";
			}else{
				eleHtml[++h] = "\t" + '<td style="border-bottom-style:double; border-color:#c0c0c0;" rowspan=2 align="center"><input type="text"  tabindex="'+tabCnt+'"  name="ordCfmQty" id="ordCfmQty" value="'+amtComma(data[k].ordCfmQty)+'" class="inputReadOnly" readonly="readonly" style="text-align: right; width:45px;" onkeypress="onlyNumber();"   onblur="amtFormat(this); sumOrdInfo(this,\''+ data[k].prodCd+'\');"  onFocus="unNumberFormat(this);" >' + "\n";
			}
			
			eleHtml[++h] = "\t" + '<input type="hidden" id="oldOrdCfmQty" name="oldOrdCfmQty" value="'+data[k].ordCfmQty+'" ></td>' + "\n";
			eleHtml[++h] = "\t" + '<td style="border-bottom-style:double; border-color:#c0c0c0;" rowspan=2 align="center"><input type="text" id="ordTotAllQty" name="ordTotAllQty"  value="'+amtComma(data[k].eaQty)+'" class="inputReadOnly"  style="text-align: right;  color: blue; width:50px;" onkeypress="onlyNumber();" onblur="amtFormat(this);" onFocus="unNumberFormat(this);"  readonly="readonly"></td>' + "\n";
	        eleHtml[++h] = "\t" + '<td style="border-bottom-style:double; border-color:#c0c0c0;" rowspan=2 align="center"><input type="text" id="ordTotPrc" name="ordTotPrc" value="'+amtComma(data[k].prc)+'" class="inputReadOnly"  style="text-align: right;   color: blue;width:70px;" onkeypress="onlyNumber();" onblur="amtFormat(this);" onFocus="unNumberFormat(this);" readonly="readonly">' + "\n";
	    	eleHtml[++h] = "\t" + '<input type="hidden" name="ordReqNo" value='+data[k].ordReqNo+'>' + "\n";
	    	eleHtml[++h] = "\t" + '<input type="hidden" name="prodCd" value='+data[k].prodCd+'>' + "\n";
	        eleHtml[++h] = "\t" + '<input type="hidden" name="ordIpsu" value='+data[k].ordIpsu+'>' + "\n";
	        eleHtml[++h] = "\t" + '<input type="hidden" name="mdModCd" value='+data[k].mdModCd+'>' + "\n";
	    	eleHtml[++h] = "\t" + '<input type="hidden"  name="hdOrdCfmQty" value="'+data[k].ordCfmQty+'">' + "\n";
			eleHtml[++h] = "\t" + '<input type="hidden"  name="hdOrdQty" value="'+data[k].ordQty+'">' + "\n";
			eleHtml[++h] = "\t" + '<input type="hidden" name="ordBuyPrc" value='+data[k].ordBuyPrc+'></td>' + "\n";
	        eleHtml[++h] = '</tr>' + "\n";
	        
	        
	        
	        eleHtml[++h] = '<tr bgcolor=ffffff >' + "\n";
			eleHtml[++h] = "\t" + '<td style="border-bottom-style:double; border-color:#c0c0c0;" colspan=2 align="left" ><span style="color:blue;" class="dot_web0001" title="'+data[k].prodNm+'">&nbsp;'+data[k].prodNm+'</span></td>' + "\n";
			eleHtml[++h] = "\t" + '</tr>' + "\n";
	        
	        
	        
		}	

		$('#tabIdx').val(tabCnt);
		$("#datalist").append(eleHtml.join(''));
		

		if(dataSum !=null){
			$('#prodSum_otqs').text(amtComma(dataSum.ordTotQtySum));
			$('#prodSum_eqs').text(amtComma(dataSum.eaQtySum));
			$('#prodSum_otps').text(amtComma(dataSum.ordTotPrcSum));
		}
		
	
	}
	
	
	}else {
		_setTbodyNoResult($("#datalist"), 10, "");
	}

}





	
	/* 목록 초기화 */
	function _setTbodyInit() {
		$("#datalist tr").remove();
	}
	
	
	/*목록 검색 결과 없을시  */
	function _setTbodyNoResult(objBody, col, msg) {
		if(!msg) msg = "<spring:message code='text.web.field.srchNodData'/>";
		objBody.append("<tr><td bgcolor='#ffffff' colspan='"+col+"' align=center>"+msg+"</td></tr>");
	}
 	 

	
	// 단위수량 입력시 EA 와 금액 계산
	function sumOrdInfo(obj, prodCd){

		
		// 상품별 합계
		var ordCfmQty = $(obj).parent().parent().children().children('input[name="ordCfmQty"]').attr('value');
		var ordIpsu = $(obj).parent().parent().children('td:last').children('input[name="ordIpsu"]').attr('value');
		var ordBuyPrc = $(obj).parent().parent().children('td:last').children('input[name="ordBuyPrc"]').attr('value');


		
		ordCfmQty = ordCfmQty.replaceAll(",","");
		ordIpsu = ordIpsu.replaceAll(",","");
		ordBuyPrc = ordBuyPrc.replaceAll(",","");
		

		$(obj).parent().parent().children().children('input[name="ordTotAllQty"]').val(amtComma(ordIpsu*ordCfmQty));
		$(obj).parent().parent().children().children('input[name="ordTotPrc"]').val(amtComma((ordBuyPrc*ordIpsu)*ordCfmQty));
		
		
		// 점포별 합계
		var strQty = 0, strEa = 0, strPrc = 0;
		
		$('#datalist tr').each(function(i){
			
			if(i==0 || i%2==0){	
				
				var strQtySum = $(this).children().children('input[name="ordCfmQty"]').attr('value');
				strQty = Number(strQty)+Number(strQtySum.replaceAll(",",""));
				var strEaSum = $(this).children().children('input[name="ordTotAllQty"]').attr('value');
				strEa = Number(strEa)+Number(strEaSum.replaceAll(",",""));
				var strAmtSum = $(this).children().children('input[name="ordTotPrc"]').attr('value');
				strPrc = Number(strPrc)+Number(strAmtSum.replaceAll(",",""));
			
			}
		});
		

		
	 	$('#prodSum_otqs').text(amtComma(strQty));
		$('#prodSum_eqs').text(amtComma(strEa));
		$('#prodSum_otps').text(amtComma(strPrc));
		

	}
	
	
	// 등록구분 상세조회
	function _viewMdState(obj){
	
		var message="";

		var mdModCd =  $(obj).parent().parent().children('td:last').children('input[name="mdModCd"]').attr('value');	
		var ordCfmQty = $(obj).parent().parent().children('td:last').children('input[name="hdOrdCfmQty"]').attr('value');
		var ordQty = $(obj).parent().parent().children('td:last').children('input[name="hdOrdQty"]').attr('value');
		
		
		if(mdModCd == '01') {
			message = "<spring:message code='msg.web.error.viewMdState1' arguments='"+ordQty+","+ordCfmQty+"'/>";
		} else if(mdModCd == '02') { 
			message = "<spring:message code='msg.web.error.viewMdState2'/>";
		} else {
			message = "<spring:message code='msg.web.error.viewMdState3'/>";
		}
		
		
		alert(message);

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
	
	
	//공백체크
	function emptyCheck(str){
		
		var val =str.trim();
		if(val=='') return false;
		return true;

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
	
	Array.prototype.valueIndex=function(pval)

	{

	 var idx = -1;

	 if(this==null || this==undefined || pval==null || pval==undefined){

	 }else{

	  for(var i=0;i<this.length;i++){

	   if(this[i]==pval){

	    idx = i;

	    break;

	   }

	  }

	 }

	 return idx

	};
	
	
	
	Array.prototype.removeDup=function()
	{

	 var resultArray = [];

	 if(this==null || this==undefined){

	 }else{

	  for(var i=0;i<this.length;i++){

	   var el = this[i];

	   if(resultArray.valueIndex(el) === -1) resultArray.push(el);

	  }

	 }

	 return resultArray;

	}
	
</script>

<style>
.dot_web0001 span{width:180px; height:18px; white-space:nowrap; text-overflow:ellipsis; -o-text-overflow:ellipsis; overflow:hidden;}
</style>

<base target="_self"/>
</head>
<body>
	<!--	@ BODY WRAP START 	-->
		<form name="searchForm" method="post" action="#">
		<input type='hidden' name='ordReqNo' id='ordReqNo'>
		<input type='hidden' name='ordPrgsCd' id='ordPrgsCd'>
		<input type="hidden" name="tabIdx" id="tabIdx" />
		<div id="popup">
			<div id="p_title1">
				<h1><spring:message code='epc.web.header.venCdProdInfo'/></h1>
				<span class="logo"><img src="/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
			</div>	
			<!--	2 검색내역 	-->		
	 	<div class="popup_contents">
				<!-- list -->
 			<div class="wrap_con">
				<div class="bbs_list" style="margin-top: 10px;">
					<ul class="tit">
						<li class="tit"><spring:message code='epc.web.header.venCdProdDetailOrdInfo'/> </li>
						<li class="btn">
						<a href="#" class="btn" id="btnDelete"><span><spring:message code="button.common.delete"/></span></a> 
						<a href="#" class="btn" id="btnSave"><span><spring:message code="button.common.save"/></span></a> 
						</li>
					</ul>
				
					
				<!-- 	<div style="width:100%; height:458px;overflow-x:hidden; overflow-y:scroll; overflow-x:scroll;  table-layout:fixed;white-space:nowrap;"> -->
					
					<table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=100% bgcolor=efefef>
					    <colgroup>
						 <col width="31px"/>
						 <col width="30px" />
						 <col width="50px"/>
						 <col width="45px"/>		
					 	 <col width="200px" />
					     <col width="*" />
						 <col width="65px"/>
						 <col width="70px"/>
						 <col width="100px"/>
						 <col width="18px"/>
						</colgroup>	
				
                        
                        <thead>
                          <tr bgcolor="#e4e4e4" align=center> 
                          <th rowspan="2"><spring:message code='epc.web.header.No'/></th>
                          <th rowspan="2"><spring:message code='epc.web.header.delete'/></br><input type="checkbox" id="allCheck" name="allCheck"></th>
                          <th rowspan="2"><spring:message code='epc.web.header.addNgb'/></th>
                          <th rowspan="2"><spring:message code='epc.web.header.uptNgb'/></th>
                          
	                      <th><spring:message code='epc.web.header.prdCd'/></th>
	                      <th><spring:message code='epc.web.header.buyCd'/></th>
	                      
	                      <th colspan="3"><spring:message code='epc.web.header.ordInfo'/></th>
	                      <th rowspan="3"></th>
                         </tr>
                        
                        
                        <tr bgcolor="#e4e4e4" align=center>
                          <th colspan="2"><spring:message code='text.web.field.prodNm'/></th>
                          <th><spring:message code='epc.web.header.unitQty'/></th>
                          <th><spring:message code='epc.web.header.ea'/></th>
                          <th><spring:message code='epc.web.header.amt'/></th>
                        </tr>
                   
                        
                        
                      <tr bgcolor="87CEFA">
							<!-- <th align="center" colspan="6" style="font-weight: bold;">전점합계</th> -->
							<th align="center" colspan="6" style="font-weight: bold;"><spring:message code='epc.web.header.prodTotSum'/></th>
							<th align="center" style="font-weight: bold; font-size: 12px;"><span id="prodSum_otqs"></span></th>
							<th align="center" style="color: red; font-weight: bold; font-size: 12px;" ><span id="prodSum_eqs"></span></th>
							<th align="center"  style="color: red; font-weight: bold; font-size: 12px;"><span id="prodSum_otps"></span></th>
                     </tr>
			      </thead>
                       <tr>
 					   <td colspan=10>   
                       <div id="_dataList1" style="background-color:#FFFFFF; margin: 0; padding: 0; height:384px; overflow-y: scroll; overflow-x: hidden">
                       <table id="dataTable" cellpadding="1" cellspacing="1"  border="0" width=100% bgcolor="#EFEFEF">
					  
					  
					   <colgroup>
					   <col width="29px"/>
					   <col width="30px" />
					   <col width="50px"/>
					   <col width="45px"/>	
					   <col width="200px" />
					   <col width="*" />
					   <col width="65px"/>
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
			</div>
	</div>
</form>
</body>
</html>
