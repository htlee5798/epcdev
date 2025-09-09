<%--
	Page Name 	: NEDMPRO0520.jsp
	Description : ESG인증관리
	Modification Information
	
	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2025.03.17		yja				최초생성		
--%>
<%@ include file="../common.jsp" %>
<%@ include file="/common/scm/scmCommon.jsp" %>
<%@ include file="./CommonProductFunction.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>ESG 인증관리</title>
<style>
table.sub-table td{padding:5px 3px; word-break:break-word;}
table.sub-table select, table.sub-table input, table.sub-table textarea{max-width:100%;}  
table.sub-table .tdr{text-align:right;}
table.sub-table .tdc{text-align:center;}
</style>

<script type="text/javascript">
$(function(){
	//list datepicker click event 동적 binding
	$(document).on("click", "#dataListbody img.datepicker", function(){
		var dayInput = $(this).prev("input")[0];
		var objId = dayInput.id;
		var objVal = $.trim(dayInput.value);
		
		openCalSetDt("reqDetailForm."+objId, objVal, "fncCallBackCalendar");
	});
	
	//체크박스 전체선택
	$("#chkAll").unbind().click(function(){
		var all = $(this).is(":checked");
		if(all){
			$("#dataListbody tr").find("input[name='cbox']").prop("checked", true);
		}else{
			$("#dataListbody tr").find("input[name='cbox']").prop("checked", false);
		}
	});	
	
	//row 체크박스 선택
	$(document).on("click","#dataListbody input[name='cbox']", function(){
		var dataLen = $("#dataListbody input[name='cbox']").length;
		var chkedLen = $("#dataListbody input[name='cbox']:checked").length;
		
		if(chkedLen == dataLen){
			$("#chkAll").prop("checked", true);
		}else{
			$("#chkAll").prop("checked", false);
		}
	});
	
	//파일 객체 변경 시 실행, eventSave사용 (eventSaveFormData 사용XXX)
	$(document).on("change", "#dataListbody input[type=file]", function(){
		if($(this)[0] == undefined || $(this)[0] == null || $(this)[0].files.length == 0){
			return;
		}
		
		var tgRow = $(this).closest("tr");	//대상 row
		var esgFileId = $.trim(tgRow.find("input[name='esgFileId']").val());//첨부파일아이디
		var prodCd = $.trim(tgRow.find("input[name='prodCd']").val());		//상품코드
		
		var formData = new FormData();
		formData.append("prodCd", prodCd);			//상품코드
		formData.append("esgFileId", esgFileId);	//첨부파일아이디
		formData.append("esgFile", $(this)[0].files[0]);	//첨부파일
		formData.append("tempYn", "Y");				//임시파일
		
		$.ajax({
			url:'<c:url value="/edi/product/updateProdEsgFileInfo.json"/>',
			contentType : false,
			type:"POST",
			dataType: "JSON",
			data:formData,
			cache: false,
			async:false,
			processData: false,
			success:function(data) {
				var msg = data.msg;
				if(msg == "success"){
					var esgFileId = $.trim(data.esgFileId);
					tgRow.find("input[name='esgFileId']").val(esgFileId);
				}else{
					var errMsg = data.errMsg;
					alert("파일 업로드 중 오류가 발생했습니다.\n"+errMsg);
					return;
				}
				
			}
		});
	});
});

//날짜변경 callback
function fncCallBackCalendar(tgId, cbData){
	if(tgId == null || tgId == undefined || tgId == "") return;	//반환 target 없을 경우 return
	if(tgId.startsWith("srch")) return;	//검색조건 내 캘린더일 경우 return
	
	var tgObj = $(eval(tgId)).closest("tr");
	
	if(tgObj == null || tgObj == undefined) return;
	
	//현재 rowStat
	var currStat = $.trim(tgObj.attr("data-rowStat"));
	
	//기등록된 데이터일 경우,
	if(currStat == "R"){
		//수정상태변경
		tgObj.attr("data-rowStat", "U");
	}
	
	//변경이후 행 체크
	tgObj.find("input[name='cbox']").prop("checked", true);
}

//판매코드 찾기 검색조건 팝업 OPEN
function fncOpenSrchPopSrcmkCd(){
	var targetUrl = "<c:url value='/edi/product/selSrcmkCdPopup.do'/>" + "?trNum=search";
	Common.centerPopupWindow(targetUrl, 'sellCdPopup', {width: 980, height: 700});
}

//판매코드 찾기 팝업 callback
function setSellCd(json){
	if(json == null){
		alert("상품 데이터가 유효하지 않습니다.\n관리자에게 문의하세요.");
		return;
	}
	
	//callback data
	var trNum = json.trNum;	 //대상 row구분 class
	var srcmkCd = json.srcmkCd; //판매코드
	var prodCd = json.prodCd; //상품코드
	var prodNm = json.prodNm; //상품명
	var l1Cd = json.l1Cd;	 //대분류코드
	var l2Cd = json.l2Cd;	 //중분류코드
	var l3Cd = json.l3Cd;	 //소분류코드
	var l1Nm = json.l1Nm;	 //대분류코드명
	var l2Nm = json.l2Nm;	 //중분류코드명
	var l3Nm = json.l3Nm;	 //소분류코드명
	var orgCost = $.trim(json.orgCost)!=""?$.trim(json.orgCost):"0";	//기존원가
	var orgCostFmt = setComma(orgCost);	//기존원가 금액formatting
	var prodPatFg = json.prodPatFg;	//상품유형구분
	var dispUnit = json.dispUnit;	//표시단위

	//검색조건
	if(trNum == "search"){
		$("#searchForm #srchSrcmkCd").val(srcmkCd);
	}
}

//조회
function eventSearch(){
	
	var srchVenCd = $.trim($("#srchVenCd").val());		//검색조건_협력업체
	
	if(srchVenCd == ""){
		alert("협력업체는 필수 선택항목입니다.");
		return;
	}
	
	var srchCnt = 0;
	var searchInfo = {};
	$("#searchForm").find("input, select").each(function(){
		if(this.name != null && this.name != ""){
			if(this.name == "srchExpTgYn"){
				searchInfo[this.name] = $(this).is(":checked")?"Y":"N";				
			}else if($(this).hasClass("day")){
				searchInfo[this.name] = $.trim($(this).val()).replace(/\D/g,"");
			}else{
				searchInfo[this.name] = $.trim($(this).val());
			}
			
			//인증서종료대상/파트너사 검색조건 외 입력했는지 체크
			if(this.name != "srchExpTgYn" && this.name != "srchVenCd" && $.trim($(this).val()) != ""){
				srchCnt ++;
			}
		}
	});
	
	searchInfo.srchYn = (srchCnt > 0)?"Y":"N";
	
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		url : '<c:url value="/edi/product/selectProdEsgList.json"/>',
		data : JSON.stringify(searchInfo),
		success : function(data) {
			//체크박스 전체선택 초기화
			$("#chkAll").prop("checked", false);
			//데이터 셋팅
			fncSetData(data);
		}
	});
	
	
}

//list 데이터 셋팅
function fncSetData(json){
	var itemList = json.itemList;
	//datalist 초기화
	setTbodyInit("dataListbody");
	
	//아이템정보
	if(itemList != null && itemList.length > 0){
		//row index setting
		for(var i=0; i<itemList.length; i++){
			itemList[i].count = itemList[i].rnum;
		}
		
		//datalist setting
		$("#dataListTemplate").tmpl(itemList).appendTo("#dataListbody");
	}else{
		//데이터리스트 없음 처리
		setTbodyNoResult("dataListbody", 13);
	}
}

//변경요청 FormData
function eventSaveFormData(){
	var tgObj= $("#dataListbody tr").find("input[name='cbox']:checked");
	
	//선택된 row 없음
	if(tgObj.length == 0){
		alert("변경요청 건을 1개 이상 선택해주세요.");
		return;
	}
	
	var formData = new FormData();
	
	var saveInfo = {};
	var prodDataArr = [];	//요청대상
	var flag = true;
	tgObj.closest("tr").each(function(i,e){
		var prodData = {};
		
		var prodCd = $.trim($(this).find("input[name='prodCd']").val());			//상품코드
		var esgGbn = $.trim($(this).find("input[name='esgGbn']").val());			//ESG 유형코드
		var esgAuth = $.trim($(this).find("input[name='esgAuth']").val());			//ESG 인증유형
		var esgAuthDtl = $.trim($(this).find("input[name='esgAuthDtl']").val());	//ESG 인증유형상세
		var esgToDt	= $.trim($(this).find("input[name='esgToDt']").val());			//인증종료일(Before)
		var esgAfFrDt = $.trim($(this).find("input[name='esgAfFrDt']").val());		//인증시작일(After)
		var esgAfToDt = $.trim($(this).find("input[name='esgAfToDt']").val());		//인증종료일(After)
		
		//상품코드없음
		if(prodCd == ""){
			alert("상품코드가 존재하지 않아 요청이 불가능합니다.\n행번호:"+Number(i+1));
			flag = false;
			return false;
		}
		//ESG 유형 없음
		if(esgGbn == ""){
			alert("ESG 유형이 존재하지 않아 요청이 불가능합니다.\n행번호:"+Number(i+1));
			flag = false;
			return false;
		}
		//ESG 인증유형이 없음
		if(esgAuth == ""){
			alert("ESG 인증유형이 존재하지 않아 요청이 불가능합니다.\n행번호:"+Number(i+1));
			flag = false;
			return false;
		}
		//ESG 인증상세유형이 없음
		if(esgAuthDtl == ""){
			alert("ESG 인증상세유형이 존재하지 않아 요청이 불가능합니다.\n행번호:"+Number(i+1));
			flag = false;
			return false;
		}
		//ESG 인증요청일자 체크
		if(!fncChkEsgDate($(this))){
			flag=false;
			return false;
		};
		
		//data 생성
		$(this).find("input, select").not(".notInc").map(function(){
			if(this.name != undefined && this.name != null && this.name != ""){
				if(this.type == "file" && $(this)[0].files.length > 0){
					prodData[this.name] = $(this)[0].files[0];
				}else if($(this).hasClass("day") || $(this).hasClass("amt")){
					prodData[this.name] = $.trim($(this).val()).replace(/\D/g,"");
				}else{
					prodData[this.name] = $.trim($(this).val());
				}
				
				//formdata setting
				<%-- 파일 Object 없을 때 formdata 생성하면 getter에서 null exception 발생함 --%>
				if(this.type != "file" || (this.type == "file" && $(this)[0].files.length > 0)){
					formData.append("prodDataArr["+i+"]."+this.name, prodData[this.name]);
				}
			}
		});
// 		if(prodData != null){
// 			prodDataArr.push(prodData);
// 		}
	});
	
	
	console.log(prodDataArr);
	
	if(!flag) return;
// 	saveInfo.prodDataArr = prodDataArr;		//대상상품 list
	
	$.ajax({
		url:'<c:url value="/edi/product/updateProdEsgInfoWithFile.json"/>',
		contentType : false,
		type:"POST",
		dataType: "JSON",
		data:formData,
		cache: false,
		async:false,
		processData: false,
		success:function(result) {
			var msg = result.msg;
			
			if("success" == msg){
				alert("변경요청되었습니다.");
				eventSearch();
			}else{
				var errMsg = $.trim(data.errMsg) != "" ? data.errMsg : "처리 중 오류가 발생했습니다.";
				alert(errMsg);
			}
		}
	});
	
}


//변경요청(file저장 X)
function eventSave(){
	var tgObj= $("#dataListbody tr").find("input[name='cbox']:checked");
	
	//선택된 row 없음
	if(tgObj.length == 0){
		alert("변경요청 건을 1개 이상 선택해주세요.");
		return;
	}
	
	var saveInfo = {};
	var prodDataArr = [];	//요청대상
	var flag = true;
	tgObj.closest("tr").each(function(i,e){
		var prodData = {};
		
		var prodCd = $.trim($(this).find("input[name='prodCd']").val());			//상품코드
		var esgGbn = $.trim($(this).find("input[name='esgGbn']").val());			//ESG 유형코드
		var esgAuth = $.trim($(this).find("input[name='esgAuth']").val());			//ESG 인증유형
		var esgAuthDtl = $.trim($(this).find("input[name='esgAuthDtl']").val());	//ESG 인증유형상세
		var esgToDt	= $.trim($(this).find("input[name='esgToDt']").val());			//인증종료일(Before)
		var esgAfFrDt = $.trim($(this).find("input[name='esgAfFrDt']").val());		//인증시작일(After)
		var esgAfToDt = $.trim($(this).find("input[name='esgAfToDt']").val());		//인증종료일(After)
		
		//상품코드없음
		if(prodCd == ""){
			alert("상품코드가 존재하지 않아 요청이 불가능합니다.\n행번호:"+Number(i+1));
			flag = false;
			return false;
		}
		//ESG 유형 없음
		if(esgGbn == ""){
			alert("ESG 유형이 존재하지 않아 요청이 불가능합니다.\n행번호:"+Number(i+1));
			flag = false;
			return false;
		}
		//ESG 인증유형이 없음
		if(esgAuth == ""){
			alert("ESG 인증유형이 존재하지 않아 요청이 불가능합니다.\n행번호:"+Number(i+1));
			flag = false;
			return false;
		}
		//ESG 인증상세유형이 없음
		if(esgAuthDtl == ""){
			alert("ESG 인증상세유형이 존재하지 않아 요청이 불가능합니다.\n행번호:"+Number(i+1));
			flag = false;
			return false;
		}
		//ESG 인증요청일자 체크
		if(!fncChkEsgDate($(this))){
			flag=false;
			return false;
		};
		$(this).find("input[type!=file], select").not(".notInc").map(function(){
			if(this.name != undefined && this.name != null && this.name != ""){
				if($(this).hasClass("day") || $(this).hasClass("amt")){
					prodData[this.name] = $.trim($(this).val()).replace(/\D/g,"");
				}else{
					prodData[this.name] = $.trim($(this).val());
				}
			}
		});
		if(prodData != null){
			prodDataArr.push(prodData);
		}
	});
	
	
	if(!flag) return;
	saveInfo.prodDataArr = prodDataArr;		//대상상품 list
	
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		url : '<c:url value="/edi/product/updateProdEsgInfo.json"/>',
		data : JSON.stringify(saveInfo),
		success : function(data) {
			var msg = data.msg;
			
			if("success" == msg){
				alert("변경요청되었습니다.");
				eventSearch();
			}else{
				var errMsg = $.trim(data.errMsg) != "" ? data.errMsg : "처리 중 오류가 발생했습니다.";
				alert(errMsg);
			}
			
		}
	});
	
}

//ESG 인증요청일자 체크
function fncChkEsgDate(obj){
	var esgToDt = $.trim($(obj).find("input[name='esgToDt']").val()).replace(/\D/g, "");		//인증종료일(Before)
	var esgAfFrDt = $.trim($(obj).find("input[name='esgAfFrDt']").val()).replace(/\D/g, "");	//인증시작일(After)
	var esgAfToDt = $.trim($(obj).find("input[name='esgAfToDt']").val()).replace(/\D/g, "");	//인증종료일(After)
	
	var rnum = $(obj).find("input[name='rnum']").val();
	
	if(esgToDt == "" || esgToDt.length != 8){
		alert("인증종료일(Before) 정보가 존재하지 않습니다.");
		return false;
	}
	
	if(esgAfFrDt == "" || esgAfFrDt.length != 8){
		alert("인증시작일(After)을 올바르게 입력해주세요.");
		$(obj).find("input[name='esgAfFrDt']").focus();
		return false;
	}
	
	if(esgAfToDt == "" || esgAfToDt.length != 8){
		alert("인증종료일(After)을 올바르게 입력해주세요.");
		$(obj).find("input[name='esgAfToDt']").focus();
		return false;
	}
	
	var esgTo_yy = esgToDt.substring(0,4);	//인증종료일(Before) 연	
	var esgTo_mm = esgToDt.substring(4,6);	//인증종료일(Before) 월	
	var esgTo_dd = esgToDt.substring(6,8);	//인증종료일(Before) 일	
	var esgAfFr_yy = esgAfFrDt.substring(0,4);	//인증시작일(After) 연	
	var esgAfFr_mm = esgAfFrDt.substring(4,6);	//인증시작일(After) 월	
	var esgAfFr_dd = esgAfFrDt.substring(6,8);	//인증시작일(After) 일	
	var esgAfTo_yy = esgAfToDt.substring(0,4);	//인증종료일(After) 연	
	var esgAfTo_mm = esgAfToDt.substring(4,6);	//인증종료일(After) 월	
	var esgAfTo_dd = esgAfToDt.substring(6,8);	//인증종료일(After) 일	
	
	var esgToDate 	= new Date(esgTo_yy, esgTo_mm-1, esgTo_dd);			//인증종료일(Before) Date
	var esgAfFrDate = new Date(esgAfFr_yy, esgAfFr_mm-1, esgAfFr_dd);	//인증시작일(After) Date
	var esgAfToDate	= new Date(esgAfTo_yy, esgAfTo_mm-1, esgAfTo_dd);	//인증종료일(After) Date
	
	//인증시작일(After)은 인증종료일(Before) 이후여야 함
	if(esgToDate >= esgAfFrDate){
		alert("인증시작일(After)는 인증종료일(Before) 이후여야 합니다.");
		$(obj).find("input[name='esgAfFrDt']").val("");
		$(obj).find("input[name='esgAfFrDt']").focus();
		return false;
	}
	
	//인증종료일(After)은 인증시작일(After) 이후여야 함
	if(esgAfFrDate >= esgAfToDate){
		alert("인증종료일(After)는 인증시작일(After) 이후여야 합니다.");
		$(obj).find("input[name='esgAfToDt']").val("");
		$(obj).find("input[name='esgAfToDt']").focus();
		return false;
	}
	
	return true;
}
</script>

<!-- 인증정보 list datalist template -->
<script id="dataListTemplate" type="text/x-jquery-tmpl">
<tr class="r\${count}" bgcolor="#ffffff" data-rowStat="<c:out value='\${rowStat}'/>">
	<td class="tdc">
		<input type="checkbox" id="cbox\${count}" name="cbox" class="notInc"/>
		<input type="hidden" name="rnum" class="notInc" value="<c:out value='\${rnum}'/>"/>
	</td>
	<td class="tdc">
		<c:out value='\${prodCd}'/>
		<input type="hidden" name="prodCd" value="<c:out value='\${prodCd}'/>"/>
	</td>
	<td class="tdc">
		<c:out value='\${srcmkCd}'/>
		<input type="hidden" name="srcmkCd" value="<c:out value='\${srcmkCd}'/>"/>
	</td>
	<td><c:out value='\${prodNm}'/></td>
	<td><c:out value='\${prodStd}'/></td>
	<td>
		<c:out value='\${esgGbn}'/>. <c:out value='\${esgGbnNm}'/>
		<input type="hidden" name="esgGbn" value="<c:out value='\${esgGbn}'/>"/>
	</td>
	<td>
		<c:out value='\${esgAuth}'/>. <c:out value='\${esgAuthNm}'/>
		<input type="hidden" name="esgAuth" value="<c:out value='\${esgAuth}'/>"/>		
	</td>
	<td>
		<c:out value='\${esgAuthDtl}'/>. <c:out value='\${esgAuthDtlNm}'/>
		<input type="hidden" name="esgAuthDtl" value="<c:out value='\${esgAuthDtl}'/>"/>
	</td>
	<td class="tdc">
		<c:out value='\${esgFrDtFmt}'/>
		<input type="hidden" name="esgFrDt" value="<c:out value='\${esgFrDt}'/>"/>
	</td>
	<td class="tdc">
		<c:out value='\${esgToDtFmt}'/>
		<input type="hidden" name="esgToDt" value="<c:out value='\${esgToDt}'/>"/>
	</td>
	<td class="tdc">
		<input type="text" class="day" id="esgAfFrDt\${count}" name="esgAfFrDt" style="width:80px;" value="<c:out value='\${esgAfFrDtFmt}'/>" disabled> <img src="/images/epc/layout/btn_cal.gif" class="middle datepicker" style="cursor:hand;" />
	</td>
	<td class="tdc">
		<input type="text" class="day" id="esgAfToDt\${count}" name="esgAfToDt" style="width:80px;" value="<c:out value='\${esgAfToDtFmt}'/>" disabled> <img src="/images/epc/layout/btn_cal.gif" class="middle datepicker" style="cursor:hand;" />
	</td>
	<td>
		<input type="file" id="esgFile\${count}" name="esgFile" value=""/>
		<input type="hidden" name="esgFileId" value="<c:out value='\${esgFileId}'/>"/>
	</td>
</tr>
</script>
</head>
<body>
<div id="content_wrap">
	<div id="wrap_menu">
		<div class="wrap_search">
			<div class="bbs_search">
				<ul class="tit">
					<li class="tit">ESG인증관리</li>
					<li class="btn">
						<a href="javascript:void(0);" class="btn" onclick="eventSearch()"><span><spring:message code="button.common.inquire"/></span></a>
					</li>
				</ul>
				<!-- 검색조건 start -->
				<form id="searchForm" name="searchForm">
				<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col width="12%"/>
						<col width="20%"/>
						<col width="12%"/>
						<col width="25%"/>
						<col width="12%"/>
						<col width="15%"/>
					</colgroup>
					<tbody>
						<tr>
							<th><span class="star">*</span> 협력업체</th>
							<td>
								<html:codeTag objId="srchVenCd" objName="srchVenCd" width="150px;" dataType="CP" comType="SELECT" defName="선택" formName="form"/>
							</td>
							<th>판매코드</th>
							<td>
								<input type="text" name="srchSrcmkCd" id="srchSrcmkCd" style="width:100px;"/>
								<a href="javascript:void(0);" class="btn" onclick="fncOpenSrchPopSrcmkCd()"><span>찾기</span></a>
							</td>
							<th>대분류</th>
							<td>
								<%-- TODO_JIA :::: --%>
								<html:codeTag objId="srchL1Cd" objName="srchL1Cd" dataType="ORG" comType="SELECT" defName="선택" formName="form"/>
							</td>
						</tr>
						<tr>
							<th>중분류</th>
							<td>
								<%-- TODO_JIA :::: --%>
								<html:codeTag objId="srchL2Cd" objName="srchL2Cd" dataType="ORG" comType="SELECT" defName="선택" formName="form"/>
							</td>
							<th>인증서 종료대상</th>
							<td colspan="3">
								<input type="checkbox" id="srchExpTgYn" name="srchExpTgYn"/>
							</td>
						</tr>
					</tbody>
				</table>
				</form>
				<!-- ./검색조건 end -->
				<!-- 검색내역 start -->
				<div class="wrap_con">
					<div class="bbs_list">
						<ul class="tit">
							<li class="tit">대상상품리스트</li>
							<li class="btn">
								<a href="javascript:void(0);" class="btn" onclick="eventSave()"><span>변경요청</span></a>
							</li>
						</ul>
						<div style="width:100%; height:458px;overflow-x:hidden; overflow-y:scroll; overflow-x:scroll; white-space:nowrap;">
							<form id="reqDetailForm" name="reqDetailForm" method="post">
							<table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=1800px; bgcolor="efefef" style="table-layout:fixed;" class="sub-table">
								<colgroup>
									<col width="2%">	
									<col width="8%">	
									<col width="8%">	
									<col width="12%">	
									<col width="4%">	
									<col width="5%">	
									<col width="5%">	
									<col width="8%">	
									<col width="5%">	
									<col width="5%">	
									<col width="8%">	
									<col width="8%">	
									<col width="8%">	
								</colgroup>
								<thead>
									<tr bgcolor="#e4e4e4">
										<th rowspan="2"><input type="checkbox" id="chkAll"/></th>
										<th rowspan="2">상품코드</th>
										<th rowspan="2">판매코드</th>
										<th rowspan="2">운영상품명</th>
										<th rowspan="2">규격</th>
										<th rowspan="2">유형코드/유형</th>
										<th rowspan="2">인증유형코드/인증유형</th>
										<th rowspan="2">인증상세유형코드/인증상세유형</th>
										<th colspan="2">Before</th>
										<th colspan="2">After</th>
										<th rowspan="2">인증서첨부</th>
									</tr>
									<tr>
										<th>인증시작일</th>
										<th>인증종료일</th>
										<th>인증시작일</th>
										<th>인증종료일</th>
									</tr>
								</thead>
								<tbody id="dataListbody" />
							</table>
							</form>
						</div>
					</div>
				</div>
				<!-- ./검색내역 end -->
			</div>
		</div>
	</div>
</div>
<form id="hiddenForm" name="hiddenForm" method="post" enctype="multipart/form-data">

</form>
</body>
</html>