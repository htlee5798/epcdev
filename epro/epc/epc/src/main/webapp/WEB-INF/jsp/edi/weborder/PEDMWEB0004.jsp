<%--
- Author(s): 
- Created Date: 2014. 08. 14
- Version : 1.0
- Description : 발주 전체 현황

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
		$('#btnSearch').unbind().click(null,	_eventSearch); 				// 조회 이벤트
		/*-----------------------------------------------------------------------------------------------------*/
		
		// 권역구분, 점포코드 , 협력업체코드, 작업구분 enter key이벤트 --------------
		$('#areaCd,, #strCd, #entp_cd, #prodCd, input[name=mdModFg], input[name=pageRowCount]').unbind().keydown(function(e) {
			switch(e.which) {
	    	case 13 : _eventSearch(this); break; // enter
	    	default : return true;
	    	}
	    	e.preventDefault();
	   	});
		//-----------------------------------------------------------------
		
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
			_eventSearch();
		});
		
		
	});
	
	/* 초기 설정 */
	function _init(){
		_setTbodyNoResult($("#datalist"), 	13, '발주요청일자, 지역/점포, 협력업체 코드, 상품코드, 조정승인을  선택 또는 입력하여 [조회] 하세요!');	// prod tBody 설정
	}
	
	// 업체코드별 발주 가능 점포 목록 조회
	function _eventSearch(){
		loadingMaskFixPos();
		
		var str = { "venCd" 	: $("#entp_cd").val(), 
				    "strCd"		: $("#strCd").val(), 
				    "areaCd"	: $("#areaCd").val(), 
				    "prodCd"	: $("#prodCd").val(), 
				    "page" 		: $("#page").val(),
				    "ordDy"		: $("#ordDy").val().replaceAll("-",""),
				   /*  "regStsfg"	: $('input[name="regStsfg"]:radio:checked').val(), */
				    "mdModFg"	: $('input[name="mdModFg"]:radio:checked').val(),
				    "pageRowCount"	: $('input[name="pageRowCount"]:radio:checked').val()
		};
		
		$.ajaxSetup({
	  		contentType: "application/json; charset=utf-8" 
			});
		$.post(
				"<c:url value='/edi/weborder/tedOrdTotSelect.do'/>",
				JSON.stringify(str),
				function(data){
					_setTbodyStoreOrdValue(data);
					hideLoadingMask();
				},	"json"
			);
	} 
	
	// 발주 가능 점포 목록 리스트에 뷰
	function _setTbodyStoreOrdValue(json) {
		_setTbodyInit();

		var data = json.list, h = -1, eleHtml = [], ordCntSum = json.ordCnt; pagHtml = [], j = -1;
	    
		if(data != null){
			var sz = json.list.length;
			if (sz > 0) {
				for ( var k = 0; k < sz; k++) {
					var cnt = k+1;
					eleHtml[++h] = '<tr bgcolor=ffffff >' + "\n";
					eleHtml[++h] = "\t" + '<td align="center">'+cnt+'</td>' + "\n";
					if(data[k].mdModCd == '00') {
						eleHtml[++h] = "\t" + '<td align="center">정상</td>' + "\n";
					}else if(data[k].mdModCd == '01'){
						eleHtml[++h] = "\t" + '<td align="center" style="font-weight: bold; text-decoration:underlin"><a href="javascript:;" onClick="_viewMdState(this)">조정</a></td>' + "\n";
					}else if(data[k].mdModCd == '02'){
						eleHtml[++h] = "\t" + '<td align="center" style="font-weight: bold; text-decoration:underlin"><a href="javascript:;" onClick="_viewMdState(this)">삭제</a></td>' + "\n";
					}else{
						eleHtml[++h] = "\t" + '<td align="center" style="font-weight: bold; text-decoration:underlin"><a href="javascript:;" onClick="_viewMdState(this)">중단</a></td>' + "\n";
					}
					if(data[k].mdModCd == '00' || data[k].mdModCd == '01'  ) {
						eleHtml[++h] = "\t" + '<td align="center" class="dot_web0004_01"><span title="['+data[k].strCd+']'+data[k].strNm+'">['+data[k].strCd+']'+data[k].strNm+'</span></td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="center">'+data[k].prodCd+'</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="center">'+data[k].srcmkCd+'</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="left" class="dot_web0004_02"><span title="'+data[k].prodNm+'">'+data[k].prodNm+'</span></td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="center">'+data[k].prodStd+'</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="right">'+data[k].ordIpsu+'&nbsp;</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="center">'+data[k].ordUnit+'</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="right" style="font-weight: bold;">'+amtComma(data[k].ordCfmQty)+'&nbsp;</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="right" style="color:red; font-weight: bold;">'+amtComma(data[k].ordTotQtySum)+'&nbsp;</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="right" style="color:red; font-weight: bold;">'+amtComma(data[k].ordTotPrcSum)+'&nbsp;' + "\n";
						eleHtml[++h] = "\t" + '<input type="hidden" id="ordCfmQty" name="ordCfmQty" value="'+data[k].ordCfmQty+'">' + "\n";
						eleHtml[++h] = "\t" + '<input type="hidden" id="ordQty" name="ordQty" value="'+data[k].ordQty+'">' + "\n";
						eleHtml[++h] = "\t" + '<input type="hidden" id="venNm" name="regStsCd" value="'+data[k].regStsCd+'">' + "\n";
						eleHtml[++h] = "\t" + '<input type="hidden" id="mdModCd" name="mdModCd" value="'+data[k].mdModCd+'"></td>' + "\n";
						eleHtml[++h] = "\t" + '</tr>' + "\n";
					}else{
						eleHtml[++h] = "\t" + '<td align="center" class="dot_web0004_01 through_tr"><span title="['+data[k].strCd+']'+data[k].strNm+'">['+data[k].strCd+']'+data[k].strNm+'</span></td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="center" class="through_tr">'+data[k].prodCd+'</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="center" class="through_tr">'+data[k].srcmkCd+'</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="left" class="dot_web0004_02 through_tr"><span title="'+data[k].prodNm+'">'+data[k].prodNm+'</span></td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="center" class="through_tr">'+data[k].prodStd+'</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="right" class="through_tr">'+data[k].ordIpsu+'&nbsp;</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="center" class="through_tr">'+data[k].ordUnit+'</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="right" class="through_tr">'+amtComma(data[k].ordCfmQty)+'&nbsp;</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="right" class="through_tr">'+amtComma(data[k].ordTotQtySum)+'&nbsp;</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="right" class="through_tr">'+amtComma(data[k].ordTotPrcSum)+'&nbsp;' + "\n";
						eleHtml[++h] = "\t" + '<input type="hidden" id="ordCfmQty" name="ordCfmQty" value="'+data[k].ordCfmQty+'">' + "\n";
						eleHtml[++h] = "\t" + '<input type="hidden" id="ordQty" name="ordQty" value="'+data[k].ordQty+'">' + "\n";
						eleHtml[++h] = "\t" + '<input type="hidden" id="venNm" name="regStsCd" value="'+data[k].regStsCd+'">' + "\n";
						eleHtml[++h] = "\t" + '<input type="hidden" id="mdModCd" name="mdModCd" value="'+data[k].mdModCd+'"></td>' + "\n";
						eleHtml[++h] = "\t" + '</tr>' + "\n";
					}
					
					cnt++;
				}
				$("#datalist").append(eleHtml.join(''));
			}
			
		}else {
			_setTbodyNoResult($("#datalist"), 11, null );
		};
		
		var page =  json.paging.list;
		var pageSz = json.paging.list.length;
		
		if(pageSz > 0){
			for ( var m = 0; m < pageSz; m++) {
				if (page[m].pageNumber == '<<'){
					pagHtml[++j] = '<a href="javascript:;" class="btn" link="'+page[m].linkPageNumber+'"><img src="/images/common/btn/btn_first.gif" alt="처음" /></a>' + "\n";
				} else if (page[m].pageNumber == '<'){
					pagHtml[++j] = '<a href="javascript:;" class="btn" link="'+page[m].linkPageNumber+'"><img src="/images/common/btn/btn_pre.gif" alt="이전" /></a>' + "\n";
				}else if(page[m].pageNumber == '>'){
					pagHtml[++j] = '<a href="javascript:;" class="btn" link="'+page[m].linkPageNumber+'"><img src="/images/common/btn/btn_next.gif" alt="다음" /></a>' + "\n";
				}else if(page[m].pageNumber == '>>'){
					pagHtml[++j] = '<a href="javascript:;" class="btn" link="'+page[m].linkPageNumber+'"><img src="/images/common/btn/btn_last.gif"  alt="마지막" /></a>' + "\n";
				}else{
					pagHtml[++j] = '<a href="javascript:;" class="'+page[m].cl+'" link="'+page[m].linkPageNumber+'" title="'+page[m].pageNumber+'">'+page[m].pageNumber+'</a>' + "\n";
				}
			}
			$("#paging").append(pagHtml.join(''));
		}
		
		if(ordCntSum != null){
			$("#strCnt").text(amtComma(ordCntSum.strCnt));
			$("#prodCnt").text(amtComma(ordCntSum.prodCnt));
			$("#ordTotQtySum").text(amtComma(ordCntSum.ordTotQtySum));
			$("#ordTotAllQtySum").text(amtComma(ordCntSum.ordTotAllQtySum));
			$("#ordTotPrcSum").text(amtComma(ordCntSum.ordTotPrcSum));
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

	// 등록구분 상세조회
	function _viewMdState(obj){
		var message = "";
		var mdModCd = $(obj).parent().parent().children('td:last').children('input[name="mdModCd"]').attr('value');
		var ordCfmQty = $(obj).parent().parent().children('td:last').children('input[name="ordCfmQty"]').attr('value');
		var ordQty = $(obj).parent().parent().children('td:last').children('input[name="ordQty"]').attr('value');
		
		if(mdModCd == '01') {
			message = "발주승인 처리 중 조정(01)되었습니다.\n"+"- Mder 승인 전 발주량 변경(요청 : "+ordQty+"  조정 : "+ordCfmQty+")";
		} else if(mdModCd == '02') { 
			message = "발주승인 처리 중 삭제(02)되었습니다.\n"+"내용1 : Mder 승인 전 삭제 처리 후 발주확정\n"+"내용2 : 당일발주 변경에 의해 제외 처리 되었습니다.";
		} else {
			message = "발주가 중단(03)된 상품입니다.\n"+"- 당일발주 변경에 의해 제외 처리 되었습니다.";
		}
		alert(message);
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
	
	//금액 콤마 - value 계산용
	function amtComma(amt) {
	    var num = amt + '';
	    for(var regx = /(\d+)(\d{3})/;regx.test(num); num = num.replace(regx, '$1' + ',' + '$2'));
	    return num;
	}

	// 로딩바 감추기
	function hideLoadingMask(){
		$('#loadingLayer').remove();
		$('#loadingLayerBg').remove();
	}
</script>

<style>
.dot_web0004_01 span{display:block;overflow:hidden;width:70px;white-space:nowrap;text-overflow:ellipsis;-o-text-overow: ellipsis;-moz-binding:url(js/ellipsis.xml#ellipsis)}
.dot_web0004_02 span{display:block;overflow:hidden;width:90px;white-space:nowrap;text-overflow:ellipsis;-o-text-overow: ellipsis;-moz-binding:url(js/ellipsis.xml#ellipsis)}

/* 오류 데이터 라인 + 글씨 Color 회색*/
.through_tr { font-style:italic; text-decoration:line-through; color:gray }

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
		<div id="wrap_menu">
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit">검색조건</li>
						<li class="btn">
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
						<th>발주요청일자</th>
						<td>
							<input type="text" class="day" id="ordDy" name="ordDy" readonly value="${paramMap.ordDy}" style="width:80px;" ><img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.ordDy');" style="cursor:hand;" />
						</td>
						<th>협력업체 코드</th>
						<td>
							<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" formName="form" selectParam="" dataType="CP" comType="SELECT" />
						</td>
					</tr>
					<tr>
						<th>지역/점포</th>
						<td>
							<html:codeTag objId="areaCd" objName="areaCd" width="75px;" formName="form" dataType="AREA" comType="select"  defName="전체"  />
							<select name="strCd" id="strCd" style="width:120px">
								<option value="">전체</option>
							</select>
						</td>
						<th>상품코드</th>
						<td>
							<input type="text" id="prodCd" name="prodCd" value="${paramMap.prodCd}">
						</td>
					</tr>
					<tr>
						<%-- <th>등록상태</th>
						<td>
							<input type="Radio" name="regStsfg" value="1" <c:if test="${paramMap.regStsfg eq '1'}"> Checked</c:if> /> 전체
							<input type="Radio" name="regStsfg" value="2" <c:if test="${paramMap.regStsfg eq '2'}"> Checked</c:if> /> 정상
							<input type="Radio" name="regStsfg" value="3" <c:if test="${paramMap.regStsfg eq '3'}"> Checked</c:if> /> 오류
						</td> --%>
						<th>조정승인</th>
						<td colspan="3">
							<input type="Radio" name="mdModFg" value="1" <c:if test="${paramMap.mdModFg eq '1'}"> Checked</c:if> /> 전체
							<input type="Radio" name="mdModFg" value="2" <c:if test="${paramMap.mdModFg eq '2'}"> Checked</c:if> /> 조정
							<input type="Radio" name="mdModFg" value="3" <c:if test="${paramMap.mdModFg eq '3'}"> Checked</c:if> /> 삭제
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
							<col width="31px"/>
							<col width="40px"/>
							<col width="80px"/>
							<col width="74px"/>
							<col width="100px"/>
							<col width="*"/>
							<col width="65px"/>
							<col width="50px"/>
							<col width="50px"/>
							<col width="50px"/>
							<col width="80px"/>
							<col width="80px"/>
							<col width="18px"/>
						</colgroup>	
						<thead>
                        <tr bgcolor="#e4e4e4" align=center> 
                          <td rowspan="2">No</td>
                          <!-- <td rowspan="2">등록<br>구분</td> -->
	                      <td rowspan="2">조정<br>구분</td>
	                      <td colspan="7">상품정보</td>
	                      <td colspan="3">발주정보</td>
	                      <th rowspan="3"></th>
                        </tr>
                        <tr bgcolor="#e4e4e4" align=center>
						  <td>점포</td>
                          <td>상품코드</td>
                          <td>판매코드</td>
                          <td>상품명</td>
                          <td>규격</td>
                          <td>입수</td>
                          <td>단위</td>
                          <td>단위<br>수량</td>
                          <td>EA</td>
                          <td>금액</td>
                        </tr>
                        <tr bgcolor="87CEFA" >
							<td align="center" colspan="2">합계</td>
							<td align="center">총 <span style="color: red; font-weight: bold;" id="strCnt"></span>개점</td>
							<td align="right" colspan="3">총 <span style="color: red; font-weight: bold;" id="prodCnt"></span>개 상품</td>
							<td align="center">-</td>
							<td align="center">-</td>
							<td align="center">-</td>
							<td align="right" style="font-weight: bold; font-size: 11px;"><span id="ordTotQtySum"></span>&nbsp;</td>
							<td align="right" style="font-weight: bold; font-size: 11px;"><span id="ordTotAllQtySum"></span>&nbsp;</td>
							<td align="right" style="font-weight: bold; font-size: 11px;"><span id="ordTotPrcSum"></span>&nbsp;</td>
                    	</tr>
                        </thead>
                        <tr> 
	                     	<td colspan=13>   
	                        	<div id="_dataList1" style="background-color:#FFFFFF; margin: 0; padding: 0; height:317px; overflow-y: scroll; overflow-x: hidden">
	                        		<table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=100% bgcolor="#EFEFEF">
	                     			<colgroup>
										<col width="31px"/>
										<col width="40px"/>
										<col width="80px"/>
										<col width="74px"/>
										<col width="100px"/>
										<col width="*"/>
										<col width="65px"/>
										<col width="50px"/>
										<col width="50px"/>
										<col width="50px"/>
										<col width="80px"/>
										<col width="80px"/>
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
					<li class="last">발주 전체 현황</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>
<font color='white'><b>PEDMWEB0004.jsp</b></font>

</html>
