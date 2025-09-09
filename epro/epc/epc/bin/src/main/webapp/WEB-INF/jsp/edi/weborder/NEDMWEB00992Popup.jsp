<%--
- Author(s): 
- Created Date: 2014. 08. 25
- Version : 1.0
- Description : 상품찾기

--%>
<%@include file="../common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>

<script  type="text/javascript" >

	var opener;

	$(function() {
		
		_init();
		
		//버튼 CLICK EVNET -------------------------------------------
		$('#btnSearch').unbind().click(null, doSearch);				// 상품찾기
		//-----------------------------------------------------------
		
		
		// 점포코드 , 협력업체코드, 작업구분 enter key이벤트 --------------
			$('#prodNm, #prodCd, #srcmkCd').unbind().keydown(function(e) {
				switch(e.which) {
			   	case 13 : doSearch(this); break; // enter
			   	default : return true;
			   	}
		    	e.preventDefault();
		});
		//--------------------------------------------------------------
		
		
		/* 페이지번호 Click Event 발생시 조회함수 호출하다. --------------- */
		$('#paging a').live('click', function(e) {
			// #page : 서버로 보내는 Hidden Input Value 
			$('#page').val($(this).attr('link'));
			// 개발자가 만든 조회 함수
			doSearch();
		});
		//---------------------------------------------------------------
		
		
		/* 상품 목록 Click Event ---------------------------------------*/
		$('#datalist tr').live('click', function(e){
			var obj = $(this).children().last();		// last-td
			var prodCds = $(obj).children("input[name='prodCds']").val();
			var prodNms = $(obj).children("input[name='prodNms']").val();

			if(prodCds){
				$("#datalist tr").removeClass('tr-selected');
				$(this).addClass('tr-selected');
				
				opener.prodCd = prodCds;
				opener.prodNm = prodNms;
				
				top.close();
				
			}else{
				return false;
			}
		});
		//---------------------------------------------------------------
		
	});

	/* 초기 설정 */
	function _init(){
		opener = window.dialogArguments;
		
		$("#venCd").val(opener.venCd);
		
		_setTbodyNoResult($("#datalist"), 	4, '<spring:message code="text.web.field.srchInit992"/>');	// MDer tBody 설정
		$("#prodCd").focus();
	}
	
	/*상품검색*/
	function doSearch(){
		
		if(!$.trim($("#prodCd").val()) && !$.trim($("#prodNm").val()) && !$.trim($("#srcmkCd").val()) ){
			alert('<spring:message code="msg.web.error.prodCdProdNmSrcmkCd"/>');
			return false;
		}
		if($.trim($("#prodCd").val()) && $.trim($("#prodCd").val()).length < 5) {
			alert('<spring:message code="msg.web.error.srchKeyMin5"/>');
			$("#prodCd").focus();
			return false;
		}
		
		
		if($.trim($("#srcmkCd").val()) && $.trim($("#srcmkCd").val()).length < 5) {
			alert('<spring:message code="msg.web.error.srcmkCdMin5"/>');
			$("#srcmkCd").focus();
			return false;
		}
		
		if($.trim($("#prodCd").val()) && !isNumber($("#prodCd").val()) ){
			alert('<spring:message code="msg.web.error.valProdCd"/>');
			$("#prodCd").val('');
			$("#prodCd").focus();
			return false;
		}
		
		if($.trim($("#srcmkCd").val()) && !isNumber($("#srcmkCd").val()) ){
			alert('<spring:message code="msg.web.error.valSrcmkCd"/>');
			$("#srcmkCd").val('');
			$("#srcmkCd").focus();
			return false;
		}
		
		
		var ven = {"prodCd"		: $("#prodCd"	).val(),		//상품코드
				   "srcmkCd"	: $("#srcmkCd"	).val(),		//판매코드
				   "prodNm"		: $("#prodNm"	).val(),		//상품명
				   "venCd" 		: $("#venCd"	).val(),		//업체코드
				   "page"  		: $("#page"		).val() 			
		  		  };
		
		loadingMaskFixPos();
		
		$.ajaxSetup({
	  		contentType: "application/json; charset=utf-8" 
			});
		$.post(
				"<c:url value='/edi/weborder/NEDMWEB0099SelectVenProd.json'/>",
				JSON.stringify(ven),
				function(data){
					
					if(data == null || data.state != "0"){
						hideLoadingMask();
						alert('<spring:message code="msg.web.error.srchProdError" arguments="' + data.state + '"/>');
					}
					else {
						hideLoadingMask();
						_setTbodyMasterValue(data); 
					}
					
				},	"json"
			);
	} 
	

	function _setTbodyMasterValue(json){
		_setTbodyInit();
		var data = json.prodCodeList, eleHtml = [], h = -1, pagHtml = [], j = -1,eleHtmlSum=[];
		
		if(data == null || data.length <=0) {
			_setTbodyNoResult($("#datalist"), 5, null );
			return;
		}
		
		var sz = data.length;
		
		for ( var k = 0; k < sz; k++) {
			eleHtml[++h] = "<tr  bgcolor='#ffffff' style='cursor: pointer;'> 		\n";
			eleHtml[++h] = "	<td align='center'><span>"+data[k].prodCd+"</span> </td> 			\n";
			eleHtml[++h] = "	<td align='center'>"+data[k].srcmkCd+"</span></td> 					\n";
			eleHtml[++h] = " 	<td class='dot_web0001_01 through_tr'><span title='"+data[k].prodNm+"' style='color:#516dc0;'>"+data[k].prodNm+"</span></td>	\n";
			eleHtml[++h] = "	<td align='center'>"+data[k].prodStd+"</span> 						\n";
			eleHtml[++h] = "	  <input type='hidden' name='prodCds' value='"+data[k].prodCd+"'> 	\n";
			eleHtml[++h] = "	  <input type='hidden' name='prodNms' value='"+data[k].prodNm+"'> 	\n";
			eleHtml[++h] = "	</td>					\n";
			eleHtml[++h] = "</tr>						\n";
		}

		$("#datalist").append(eleHtml.join(''));
		
	 	var page   =  json.paging.list;
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
	if(!msg) msg = "<spring:message code='text.web.field.srchNodData'/>";
	objBody.append("<tr><td bgcolor='#ffffff' colspan='"+col+"' align=center>"+msg+"</td></tr>");
}

	 

/* 목록 초기화 */
function _setTbodyInit() {
	$("#datalist tr").remove();
	$("#paging").empty();
}


 // 클릭한 것을 지정된 곳에 넣기
function popupPushValue(prodCd, prodNm)
{
	opener.setprodInto(prodCd);
	top.close();
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
	
 
// 로딩바 감추기
function hideLoadingMask(){
	$('#loadingLayer').remove();
	$('#loadingLayerBg').remove();
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

.dot_web0001_01 span{width:150px; height:18px; white-space:nowrap; text-overflow:ellipsis; -o-text-overflow:ellipsis; overflow:hidden;}
</style>

<base target="_self"/>
</head>

<body>
	<form name="popUp" id="vendorPopUp" method="post">
    <input type="hidden" name="page" id="page" value="1" />
	<input type="hidden" name="venCd" id="venCd"  />
		<div id="popup">
		
			<div id="p_title1">
				<h1><spring:message code='epc.web.header.prdInfo'/></h1>
				<span class="logo"><img src="/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
			</div>
		
			<div class="popup_contents">
				
				
				<!-- 1검색조건 ---------------------------------------- -->
				<div class="bbs_list" style="margin-top: 10px;">
					<ul class="tit">
						<li class="tit"><spring:message code='text.web.field.searchCondition'/></li>
						<li class="btn">
							<a href="#" class="btn" id="btnSearch"><span><spring:message code="button.common.inquire"/></span></a>
						</li>
					</ul>
					<table class="bbs_search" >
						<colgroup>
							<col width="80px;">
							<col width="100px;">
							<col width="80px;">
							<col width="120px;">
							<col width="80px;">
							<col width="*">
						</colgroup>
						<tr>
							<th><spring:message code='epc.web.header.prdCd'/></th>
							<td><input type="text" id="prodCd" name="prodCd"  maxlength="10"  onkeypress="onlyNumber();" style='height:20px; width:75px; ime-mode:disabled'></td>
							<th><spring:message code='epc.web.header.buyCd'/></th>
							<td><input type="text" id="srcmkCd" name="srcmkCd"  maxlength="13"  onkeypress="onlyNumber();" style='height:20px;width:90px; ime-mode:disabled'></td>
							<th><spring:message code='epc.web.header.prdNm'/></th>
							<td><input type="text" id="prodNm" name="prodNm"  maxlength="100" style='height:20px; swidth:170px;'></td>
						</tr>
					</table>
				</div>
				<!-- 	1검색조건 // ------------------------------------------------->



				<!-- Data start ----------------------------------------------------->
				<div class="wrap_con">
					<div class="bbs_list">
						<ul class="tit">
							<li class="tit"><spring:message code='text.web.field.srchList'/></li>
						</ul>					
						<table id="dataTable" cellpadding="1" cellspacing="1"  border="0" width=100% bgcolor="#EFEFEF">
							<colgroup>
								<col width="102px"/>
								<col width="100px"/>
								<col width="*"/>
								<col width="100px"/>
								<col width="18px"/>
							</colgroup>	
							<thead>
							<tr bgcolor="#e4e4e4" align=center>
								<th><spring:message code='epc.web.header.prdCd'/></th>
								<th><spring:message code='epc.web.header.buyCd'/></th>
                          		<th><spring:message code='epc.web.header.prdNm'/></th>
                           		<th><spring:message code='epc.web.header.standard'/></th>
                           		<th></th>
                           	</tr>
							</thead>
							<tr>
								<td colspan=5> 
									<div id="_dataList1" style="  background-color:#FFFFFF;  margin: 0; padding: 0; height:211px; overflow-y: scroll; overflow-x: hidden">
				                         <table id="dataTable"    cellpadding="1" cellspacing="1" border="0" width=100% bgcolor="#EFEFEF">
				                        	<colgroup>
												<col width="100px"/>
												<col width="100px"/>
												<col width="*"/>
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
				<!-- Data end --------------------------------------------------------->



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