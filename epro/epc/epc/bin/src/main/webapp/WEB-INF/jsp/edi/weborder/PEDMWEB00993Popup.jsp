<%--
- Author(s): 
- Created Date: 2014. 08. 30
- Version : 1.0
- Description : 점포선택(협력사의 발주가능 점포 리스트 선택)

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
		$('#btnSearch').unbind().click(null, _eventSearch);				// 점포검색
		$('#btnSubmit').unbind().click(null,  _eventSubmit);			// 점포선택 완료
		//-----------------------------------------------------------
		
		
		// 권역코드, 점포명  enter key이벤트 ---------------------------
		$('#areaCd, #strNm').unbind().keydown(function(e) {
				switch(e.which) {
			   	case 13 : _eventSearch(this); break; // enter
			   	default : return true;
			   	}
		    	e.preventDefault();
		});
		//-----------------------------------------------------------
		
		
		// 삭제 체크박스 전체 선택 -------------------------------------
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
		//-----------------------------------------------------------
		
		
		// 전체 체크 박스 초기화
		$("input[name=allCheck]").attr("disabled",true);
		
	});
	
	
	/* 체크완료 submit */
	function _eventSubmit(){
		
		var strCd = "";
		var strNm = "";
		
		var rtnStrCd = "";
		var rtnStrNm = "";
		
		var maxStr   = 5;
		var rowCnt   = 0;
		
		$("#datalist tr").each(function (index){
			// 상품 정보  ------------------------------------------
			
			$(this).find('input').map(function() {
				if(this.name == 'strBox'){
					if($(this).is(":checked")){
						
						rowCnt++;
						
						strCd = $(this).parent().parent().children('td:last').children('input[name="strCds"]').attr('value');
						strNm = $(this).parent().parent().children('td:last').children('input[name="strNms"]').attr('value');
						
						 
							if(rtnStrCd) rtnStrCd +=",";
							rtnStrCd +=strCd;
							
							if(rowCnt <= maxStr) {
								if(rtnStrNm) rtnStrNm +=", ";
								rtnStrNm += "["+strCd+"]"+strNm;
							}
					
					}
				}
			});
		});

		if(rowCnt > maxStr){
			rtnStrNm+=" (외"+(rowCnt-maxStr)+"점)";
		}

		opener.rtnStrCd  = rtnStrCd;
		opener.rtnStrNm  = rtnStrNm;
		opener.rtnStrCnt = rowCnt;
		
		top.close();
	}
	
	
	/* 초기 설정 */
	function _init(){
		
		opener = window.dialogArguments;
		if(opener == null || !opener.venCd){
			alert('비정상 접근정보 입니다. 다시 실행하세요!');
			top.close();
		}
		$("#venCd").val(opener.venCd);
		$("#strNm").focus();
		
		//_eventSearch();
	}
	

	
	/*점포검색*/
	function _eventSearch(){
		var str = {"venCd"		: $("#venCd"	).val()		//업체코드
				  ,"areaCd"		: $("#areaCd"	).val()		//지역
				  ,"strNm"		: $("#strNm"	).val()		//점포명
		  		  };
		
		loadingMaskFixPos();
		
		$.ajaxSetup({
	  		contentType: "application/json; charset=utf-8" 
			});
		$.post(
				"<c:url value='/edi/weborder/PEDMCOM0009SelectVenStore.do'/>",
				JSON.stringify(str),
				function(data){
					
					if(data == null || data.state != "0"){
						alert("점포 검색중 오류가 발생하였습니다.\n[CODE:"+data.state+"]");
					}
					else _setTbodyMasterValue(data); 
					
				},	"json"
			);
		
		hideLoadingMask();

	} 
	

	function _setTbodyMasterValue(json){
		_setTbodyInit();
		var data = json.dataList, eleHtml = [], h = -1, pagHtml = [], j = -1,eleHtmlSum=[];
		
		if(data == null || data.length <=0) {
			_setTbodyNoResult($("#datalist"), 4, null );
			return;
		}
		
		var sz = data.length;

		for ( var k = 0; k < sz; k++) {
			eleHtml[++h] = "\t" + '<tr  bgcolor="#ffffff" style="cursor: pointer;"> 				' + "\n";
			eleHtml[++h] = "\t" + '	<td align="center"><span>'+(k+1)+'</span> </td> 				' + "\n";
			eleHtml[++h] = "\t" + ' <td align="center"><input type="checkbox" id="strBox" name="strBox" onclick="javascript:chkAllStrBox();"> </td>' + "\n";
			eleHtml[++h] = "\t" + ' <td align="center">'+data[k].strCd+'</td> 						' + "\n";
			eleHtml[++h] = "\t" + ' <td>&nbsp;'+data[k].areaNm+' | '+data[k].strNm+' 							' + "\n";
			eleHtml[++h] = "\t" + ' <input type="hidden" name="strCds" value="'+data[k].strCd+'"> 	' + "\n";
			eleHtml[++h] = "\t" + ' <input type="hidden" name="strNms" value="'+data[k].strNm+'"> 	' + "\n";
			eleHtml[++h] = "\t" + ' </td>	' + "\n";
			eleHtml[++h] = "\t" + '</tr>	' + "\n";
		}

		$("#datalist").append(eleHtml.join(''));
		
	}
	
	

/*목록 검색 결과 없을시  */
function _setTbodyNoResult(objBody, col, msg) {
	if(!msg) msg = "조회된 데이터가 없습니다.";
	objBody.append("<tr><td bgcolor='#ffffff' colspan='"+col+"' align=center>"+msg+"</td></tr>");
}

//전체 체크박스 해제
function chkAllStrBox(){
	$("input:checkbox[name=allCheckStr]").attr("checked", false);
}
	 

/* 목록 초기화 */
function _setTbodyInit() {
	$("#datalist tr").remove();
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
	<input type="hidden" name="venCd" id="venCd"  />
		<div id="popup">
		
			<div id="p_title1">
				<h1>발주가능 점포 정보</h1>
				<span class="logo"><img src="/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
			</div>
		
			<div class="popup_contents">
				
				
				<!-- 1검색조건 ---------------------------------------- -->
				<div class="bbs_search" style="margin-top: 10px;">
					<ul class="tit">
						<li class="tit">조회조건</li>
						<li class="btn">
							<a href="#" class="btn" id="btnSearch"><span><spring:message code="button.common.inquire"/></span></a>
							<a href="#" class="btn" id="btnSubmit"><span><spring:message code="button.common.choice"/></span></a>
						</li>
					</ul>
					<table class="bbs_search" >
						<colgroup>
							<col width="70px;">
							<col width="80px;">
							<col width="70px;">
							<col width="*">
						</colgroup>
						<tr>
							<th>권역</th>
							<td><html:codeTag objId="areaCd" objName="areaCd" formName="form" dataType="AREA" comType="select"  defName="선택"  /></td>
							<th>점포명</th>
							<td><input type="text" id="strNm" name="strNm" style="height: 20px;" ></td>
						</tr>
					</table>
				</div>
				<!-- 	1검색조건 // ------------------------------------------------->



				<!-- Data start ----------------------------------------------------->
				<div class="wrap_con">
					<div class="bbs_list">
						<ul class="tit">
							<li class="tit">조회내역</li>
						</ul>					
						<table id="dataTable" cellpadding="1" cellspacing="1"  border="0" width=100% bgcolor="#EFEFEF">
							<colgroup>
								<col width="30px"/>
								<col width="50px"/>
								<col width="80px"/>
								<col width="*"/>
								<col width="18px"/>
							</colgroup>	
							<thead>
							<tr bgcolor="#e4e4e4" align=center>
								<th>No.</th>
								<th>선택<input type="checkbox" id="allCheckStr" name="allCheckStr"></th>
								<th>점포코드</th>
                          		<th>점포명</th>
                           		<th></th>
                           	</tr>
							</thead>
							<tr>
								<td colspan=5> 
									<div id="_dataList1" style="  background-color:#FFFFFF;  margin: 0; padding: 0; height:211px; overflow-y: scroll; overflow-x: hidden">
				                         <table id="dataTable"    cellpadding="1" cellspacing="1" border="0" width=100% bgcolor="#EFEFEF">
				                        	<colgroup>
												<col width="28px"/>
												<col width="50px"/>
												<col width="80px"/>
												<col width="*"/>
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
			</div>
		</div>


</body>


</html>