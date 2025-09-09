<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib 	prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LOTTE MART Back Office System</title>

<!-- CSS URL -->
<link type="text/css" rel="stylesheet" href="${lfn:getString('system.cdn.staticssl.path')}/css/style_1024.css" ></link>

<!-- JS URL -->
<script type="text/javascript" src="/js/epc/Ui_common.js" ></script>
<script type="text/javascript" src="/js/epc/common.js" ></script>


<script type="text/javascript" src="/js/jquery/jquery-1.6.1.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.blockUI.2.39.js"></script>
<script type="text/javascript" src="/js/epc/paging.js"></script>
<script type="text/javascript" src="/js/epc/edi/consult/common.js"></script>
<script language="javascript" type="text/javascript" src="/js/common/json2.js"></script>

<script type="text/javascript" >


var opener;

$(function() {

	pageOnloadInit();
	
	
	//버튼 CLICK EVNET ---------------------------------------------------------------
	$('#btnSearch').unbind().click(null, doSearch);	// 업체찾기
	//--------------------------------------------------------------------------------


	//  협력업체코드, 협력사명, 사업자번호 enter key이벤트 --------------
		$('#vendorId, #vendorNm, #bmanNo').unbind().keydown(function(e) {
			switch(e.which) {
		   	case 13 : doSearch(this); break; // enter
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
		
		 
		/* 협력사 목록 Click Event */
		$('#datalist tr').live('click', function(e){
			var obj = $(this).children('td:last');		// last-td
			var venCds = $(obj).children("input[name='venCds']").attr('value');
			var venNms = $(obj).children("input[name='venNms']").val();

			if(venCds){
				$("#datalist tr").removeClass('tr-selected');
				$(this).addClass('tr-selected');
				
				opener.venCd = venCds;
				opener.venNm = venNms;
				
				top.close();
				
			}else{
				return false;
			}
		});

});


	function pageOnloadInit(){
		
		opener = window.dialogArguments;
		
		/* 사번 */
		if(opener.empNo) $("#empNo").val(opener.empNo);

		/*01:관리협력사만, 02:전체협력사*/
		if(opener.findType) $("#findType").val(opener.findType);
		else  $("#findType").val("01");
		$("#vendorId").focus();
		_setTbodyNoResult($("#datalist"), 6, '협력사 코드, 협력사명을 입력하여 조회하세요!');
	}


	function doSearch(){
	
		
		if(!$.trim($("#vendorId").val()) && !$.trim($("#vendorNm").val()) &&!$.trim($("#bmanNo").val()) ){
			alert("업체코드 또는 협력사명 또는  사업자번호 중 하나이상의 검색조건을 입력하세요!");
			$("#vendorId").focus();
			return false;
		}

				
		var ven = {"vendorId"	: $("#vendorId").val(),
				   "vendorNm"	: $("#vendorNm").val(),
				   "bmanNo"		: $("#bmanNo").val(),
				   "findType"	: $("#findType").val(),
				   "empNo"      : $('#empNo').val(),
				   "page"		: $("#page").val() 
		  		  };
		
		loadingMaskFixPos();	
		
		$.ajaxSetup({
	  		contentType: "application/json; charset=utf-8" 
			});
		$.post(
				"<c:url value='/edi/weborder/PEDMCOM0008Select.do'/>",
				JSON.stringify(ven),
				function(data){
					
					if(data == null || data.state != "0"){
						alert("협력사 검색중 오류가 발생하였습니다.\n[CODE:"+data.state+"]");	
					}
					else _setTbodyMasterValue(data);
					
					hideLoadingMask();
					
				},	"json"
			);
	} 
	

	//업체view
	function _setTbodyMasterValue(json){
		_setTbodyInit();
		
		
		if(json.vendCodeList == null || json.vendCodeList.length <=0) {
			_setTbodyNoResult($("#datalist"), 6, null );
			return;
		}
		
		var data = json.vendCodeList, eleHtml = [], h = -1, pagHtml = [], j = -1,eleHtmlSum=[];
		var sz = json.vendCodeList.length;
		
		for ( var k = 0; k < sz; k++) {
			
			eleHtml[++h] = "<tr  bgcolor=ffffff style='cursor: pointer;'> 		\n";
			eleHtml[++h] = "  <td align='center' style='border-color:#c0c0c0;'><span> "+data[k].venCd+"</span> </td> \n";
			eleHtml[++h] = " <td class='dot_com0008_01 through_tr'><span title='"+data[k].venNm+"' style='color:#516dc0;'>"+data[k].venNm+"</span></td>	\n";
			
			eleHtml[++h] = "  <td align='center'> "+data[k].bmanNoFmt+"	</td>	\n";
			
			eleHtml[++h] = " <td class='dot_com0008_02 through_tr'><span title='"+data[k].btyp+"'>"+data[k].btyp+"</span></td>	\n";
			eleHtml[++h] = " <td class='dot_com0008_02 through_tr'><span title='"+data[k].bkind+"'>"+data[k].bkind+"</span>	\n";
			
			
			eleHtml[++h] = "    <input type='hidden'  name='venCds' value='"+data[k].venCd+"'>	\n";
			eleHtml[++h] = "    <input type='hidden'  name='venNms' value='"+data[k].venNm+"'>	\n";
			eleHtml[++h] = "   </td> 	\n";
			eleHtml[++h] = "</tr>		\n";
		}	
		
		$("#datalist").append(eleHtml.join(''));
		
		
		
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
	function _setTbodyNoResult() {
		$("#datalist").append('<tr><td colspan="2" align=center>등록된 데이터가 없습니다.</td></tr>');
	}
		 
	
	
	/* 목록 초기화 */
	function _setTbodyInit() {
		$("#datalist tr").remove();
		$("#paging").empty();
	}




	/* // 클릭한 것을 지정된 곳에 넣기
	function popupPushValue(vendorId, vendorName)
	{
		opener.setVendorInto(vendorId, vendorName);
		top.close();
	}
	
	function popupPushValue2(vendorId, vendorName, cono)
	{
		opener.setVendorInto(vendorId, vendorName, cono);
		top.close();
	}
	 */

 
 

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
	
	

	// 로딩바 감추기
	function hideLoadingMask(){
		$('#loadingLayer').remove();
		$('#loadingLayerBg').remove();
	}
		
	
	/*목록 검색 결과 없을시  */
	function _setTbodyNoResult(objBody, col, msg) {
		if(!msg) msg = "조회된 데이터가 없습니다.";
		objBody.append("<tr><td bgcolor='#ffffff' colspan='"+col+"' align=center>"+msg+"</td></tr>");
	}
 
 
</script>

<style>
.page { padding:1px 0 0; text-align:center; }
.page img { vertical-align:middle;}
.page a { display:inline-block; width:15px; height:11px; padding:4px 0 0; text-align:center; border:1px solid #efefef; background:#fff; vertical-align:top; color:#8f8f8f; line-height:11px;}
.page a.btn,
.page a.btn:hover { width:auto; height:auto; padding:0; border:0; background:none;}
.page a.on,
.page a:hover {background:#518aac; font-weight:bold; color:#fff;}


/* SELECTED ROW STYLE*/
.tr-selected { background: #DDDFF1; color: #5860a1; font-weight: bold; }


.dot_com0008_01 span{width:140px; height:18px; white-space:nowrap; text-overflow:ellipsis; -o-text-overflow:ellipsis; overflow:hidden;}
.dot_com0008_02 span{width:170px; height:18px; white-space:nowrap; text-overflow:ellipsis; -o-text-overflow:ellipsis; overflow:hidden;}
</style>

<base target="_self"/>
</head>

<body>
	<form name="popUp" id="vendorPopUp" method="post">
    <input type="hidden" name="page" 	 id="page"  	value="1" />
    <input type="hidden" name="findType" id="findType"	value="01" />
    <input type="hidden" name="empNo" 	 id="empNo"		value="" />
	
		<div id="popup" >
		
			<div id="p_title1">
				<h1>협력사정보</h1>
				<span class="logo"><img src="${lfn:getString('system.cdn.staticssl.path')}/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
			</div>
			<div class="popup_contents">
				<!-- 1검색조건 -->
				<div class="bbs_list" style="margin-top: 10px;">
					<ul class="tit">
						<li class="tit">조회조건</li>
						<li class="btn">
							<a href="#" class="btn" id="btnSearch"><span><spring:message code="button.common.inquire"/></span></a>
						</li>
					</ul>
					<table class="bbs_grid2" cellspacing="0" border="0">
						<colgroup>
							<col width="100px;">
							<col width="80px;">
							<col width="100px;">
							<col width="150px;">
							<col width="100px;">
							<col width="*">
						</colgroup>
						<tr>
							<th><span class="star" style="font-weight: bold;">협력사코드</span></th>
							<td><input type="text" id="vendorId" name="vendorId" maxlength="6"  onkeypress="onlyNumber();" style='ime-mode:disabled; width: 70px;'></td>
							<th><span class="star" style="font-weight: bold;">협력사명</span></th>
							<td><input type="text" id="vendorNm" name="vendorNm"   maxlength="100" style="width: 130px;"></td>
							<th><span class="star" style="font-weight: bold;">사업자번호</span></th>
							<td><input type="text" id="bmanNo" name="bmanNo"   maxlength="10" onkeypress="onlyNumber();" style="ime-mode:disabled; width: 100%;"></td>
						</tr>
					</table>
				</div>
				<!-- 	1검색조건 // -->
				
	
				<!-- 	2검색내역 -->
				<div class="wrap_con">
					<div class="bbs_list">
						<ul class="tit">
							<li class="tit">조회내역</li>
						</ul>					
		
						<table id="dataTable"    cellpadding="1" cellspacing="1" border="0" width=100% bgcolor="#EFEFEF">
							<colgroup>
								<col width="70px"/>
								<col width="*"/>
								<col width="90px"/>
								<col width="150px"/>
								<col width="150px"/>
								<col width="18px"/>
							</colgroup>	
							<thead>
							<tr bgcolor="#e4e4e4" align=center>
								  <th>업체코드</th>
		                          <th>협력업체명</th>
		                          <th>사업자번호</th>
		                          <th>업태</th>
		                          <th>업종</th>
		                          <th></th>
		                    </tr>
							</thead>
							<tr>
								<td colspan=6> 
									<div id="_dataList1" style=" background-color:#FFFFFF;  margin: 0; padding: 0; height:211px; overflow-y: scroll; overflow-x: hidden">
			                         	<table id="dataTable"    cellpadding="1" cellspacing="1" border="0" width=100% bgcolor="#EFEFEF">
			                        		<colgroup>
												<col width="68px"/>
												<col width="*"/>
												<col width="90px"/>
												<col width="150px"/>
												<col width="150px"/>
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
		

				
			</div>
		</div>
		
		
	</form>

</body>


</html>