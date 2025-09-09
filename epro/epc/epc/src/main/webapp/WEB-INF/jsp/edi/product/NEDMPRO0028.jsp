<%--
	Page Name 	: NEDMPRO0028.jsp
	Description : ESG 상품 등록 화면 
	Modification Information
	
	  수정일 			  수정자 						수정내용
	---------- 		---------    	-------------------------------------
	2025.03.21    PARK JONG GYU 				최초생성		
--%>
<%@ include file="../common.jsp" %>
<%@ include file="/common/scm/scmCommon.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<title></title>
<style type="text/css">
	/* TAB */
.tabs {
	height: 31px;
	background: #fff;
}

.tabs ul {
	width: 100%;
	height: 31px;
}

.tabs li {
	float: left;
	width: 130px;
	height: 29px;
	background: #fff;
	border: 1px solid #ccd0d9;
	border-radius: 2px 2px 0 0;
	font-size: 12px;
	color: #666;
	line-height: 30px;
	text-align: center;
}

.tabs li.on {
	border-bottom: #e7eaef 1px solid;
	color: #333;
	font-weight: bold;
}

.tabs li a {
	display: block;
	color: #666;
}

.tabs li.on a {
	color: #333;
	font-weight: bold;
}
	
.img {
	height: 14px;
}	
  
.fileFake {
	padding: 0 15px;
    width: 70%;
    height: 30px;
    border: 1px solid #ddd;
}
  
</style>
<script type="text/javascript" >
let trCount = 100;
let rowDataCnt = 0;
const extLimit = "<c:out value='${extLimit}'/>";

/* dom이 생성되면 ready method 실행 */
$(document).ready(function() {
	
	$('#reqDetailForm').show();
	
	let msg = "<c:out value='${resultMsg}'/>";
	if(msg){
		alert(msg);
	}
	//----- 검색조건 협력업체 Default 설정.
	let srchEntpCd = "<c:out value='${param.srchEntpCd}'/>";  //검색조건 협력업체코드
	let repVendorId = "<c:out value='${epcLoginVO.repVendorId}'/>";  //관리자로 로그인 해서 협력업체 갈아타기 로그인시 협력업체 코드
	if (srchEntpCd.replace(/\s/gi, '') ==  "") {
		$("#searchForm #srchEntpCd").val(repVendorId);
	} else {
		$("#searchForm #srchEntpCd").val(srchEntpCd);
	}
	
	//요청내역 list datepicker click event 동적 binding
	$(document).on("click", "#dataListbody img.datepicker", function(){
		
		let dayInput = $(this).prev("input")[0];
		//if(dayInput.disabled) return;
		let objId = dayInput.id;
		openCal("reqDetailForm."+objId);
		$(this).closest('tr').find('input[name=rowAttri]').val('mdf');
		//openCalWithCallback('reqDetailForm.'+objId,'callBackByOpenCal');
	});
	
	// 전체 check!
	$("#allCheck").click(function() {
		if($("#allCheck").is(":checked")) $("input[name=chk]").prop("checked", true);
		else $("input[name=chk]").prop("checked", false);
	});
	
	//-----탭 클릭 이벤트
	$("#prodTabs li").click(function() {
		var id = this.id;

		var pgmId = $("input[name='pgmId']").val();

		//기본정보 탭
		if (id == "pro01") {
			$("#hiddenForm").attr("action", "<c:url value='/edi/product/NEDMPRO0020Detail.do'/>");
			$("#hiddenForm").attr("method", "post").submit();
		//속성입력 탭
		} else if (id == "pro02") {
			if (pgmId == "") {
				alert("<spring:message code='msg.product.tab.mst'/>");
				return;
			}

			$("#hiddenForm").attr("action", "<c:url value='/edi/product/NEDMPRO0021.do'/>");
			$("#hiddenForm").attr("method", "post").submit();

		//이미지 등록 탭
		} else if (id == "pro03") {
			if (pgmId == "") {
				alert("<spring:message code='msg.product.tab.mst'/>");
				return;
			}

			$("#hiddenForm").attr("action", "<c:url value='/edi/product/NEDMPRO0022.do'/>");
			$("#hiddenForm").attr("method", "post").submit();

		//영양성분 탭
		} else if (id == "pro04") {
			if (pgmId == "") {
				alert("<spring:message code='msg.product.tab.mst'/>");
				return;
			}

			$("#hiddenForm").attr("action", "<c:url value='/edi/product/NEDMPRO0027.do'/>");
			$("#hiddenForm").attr("method", "post").submit();
		//ESG 탭
		} else if (id == "pro05"){
			if (pgmId == "") {
				alert("<spring:message code='msg.product.tab.mst'/>");
				return;
			}

			$("#hiddenForm").attr("action", "<c:url value='/edi/product/NEDMPRO0028.do'/>");
			$("#hiddenForm").attr("method", "post").submit();
		}
	});
	
	// ESG 상품 구분 change event!
	$("select[name='esgYn']").change(function() {
		fncEsgAreaControl($(this).val());
	}); // end ESG 상품 구분 change event!
	
	
	//ESG 구분 변경 (대분류)
	$(document).on("change", "#dataListbody select[name='esgGbn']", function(){
		var lCdVal = $.trim(this.value);
		
		//set mCode
		_setOptEsgCode($(this), "2", lCdVal, "");
		
		//clear sCode
		$(this).closest("tr").find("select[name='esgAuthDtl'] option[value!='']").remove();
	});
	
	//ESG 구분 변경 (중분류)
	$(document).on("change", "#dataListbody select[name='esgAuth']", function(){
		var lCdVal = $.trim($(this).closest("tr").find("select[name='esgGbn']").val()); 
		var mCdVal = $.trim(this.value);
		
		//set mCode
		_setOptEsgCode($(this), "3", lCdVal, mCdVal);
	});
	
	//ESG 구분 변경 (소분류)
	$(document).on("change", "#dataListbody select[name='esgAuthDtl']", function(){
		var selVal = $.trim($(this).val());
		var esgDtFg = $.trim($(this).find("option:selected").attr("data-esgDtFg")).toUpperCase();		//인증기간 필수여부
		
		$(this).closest("tr").find("input[name=esgDtFg]").val(esgDtFg);			//인증기간 필수여부 셋팅
	});
	
	<%-- view용 화면일 경우, 파일 변경 이벤트 발생안함 --%>
	<c:if test="${param.mode ne 'view'}">
	//파일 객체 변경 시 실행,
	$(document).on("change", "#dataListbody input[type=file]", function(){
		//상태값 변경
		$(this).closest('tr').find('input[name=rowAttri]').val('mdf');
		
		if($(this)[0] == undefined || $(this)[0] == null || $(this)[0].files.length == 0){
			alert("파일 정보 읽기에 실패하였습니다.");
			//파일영역 초기화
			fncClearFile(this);
			return;
		}
		
		var fileNm = $.trim($(this)[0].files[0].name);				//업로드파일명
		var fileExt = fileNm.substr(fileNm.lastIndexOf(".")+1).toLowerCase();	//파일확장자명
		var limitExtArr = $.trim(extLimit).split("|") || [];		//업로드가능 파일확장자
	
		//확장자 체크
		var extLimitTxt = extLimit.replace(/\|/g,",");
		if($.inArray(fileExt, limitExtArr) == -1){
			alert(extLimitTxt + " 파일만 업로드 할수 있습니다.");
			//파일영역 초기화
			fncClearFile(this);
			return;
		}
		
		$(this).closest('tr').find("span[name=fileIdView]").text(fileNm);
	});
	</c:if>
	
	
	//화면진입시, ESG영역컨트롤
	<c:if test='${not empty prodDetailInfo.esgYn}'>
	fncEsgAreaControl("<c:out value='${prodDetailInfo.esgYn}'/>");
	</c:if>
	
	
	// 조회
	btnSearch('1');
});

/* ESG 영역 컨트롤 */
function fncEsgAreaControl(esgYn){
	<c:if test="${param.mode eq 'view'}">
		<%-- view 모드일 경우, disabled 처리 --%>
		$("#esgEarth").attr("disabled",true);
		return;
	</c:if>
	
	// ESG 인증 show
	if( $.trim(esgYn) == '1' || $.trim(esgYn) == "")	{	//적용
		$('#reqDetailForm').show();
		$("#esgEarth").removeAttr("disabled");
	}else {							//미적용
		$('#reqDetailForm').hide();
		//ESG 상품 구분 = 미적용일 경우, RE:EARTH 로고 사용여부 = 미사용 고정
		$("#esgEarth").val("2").attr("disabled",true);
	}
}

/* 조회 */
function btnSearch(pageIndex) {
	let searchInfo = {};
	
	$('#searchForm, #hiddenForm').find('input , select').map(function() {
		searchInfo[this.name] = $(this).val();
	});
	
	//console.log(searchInfo);
	
    $.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		url : '<c:url value="/edi/product/selectNewProdEsgList.json"/>',
		data : JSON.stringify(searchInfo),
		success : function(data) {
			$("#pageIndex").val(pageIndex);

			//json 으로 호출된 결과값을 화면에 Setting
			_setTbody(data.list);
		}
	});	
    
}

/* List Data 셋팅 */
function _setTbody(json) {
	let data = json;
	setTbodyInit("dataListbody"); // dataList 초기화

   	if (json.length > 0) {
        // 데이터에 count 추가 (인덱스 역할)
        for (let i = 0; i < data.length; i++) {
            //json[i].count = i + 1; // 1부터 시작하도록 설정
            data[i].rowAttri = 'search';
            data[i].trCount = i;
        }
        
        $("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
		$("select[name='esgGbn']").val( data[0].esgYn );
		$("select[name='esgEarth']").val( data[0].esgEarth );
		
		for (let i = 0; i < json.length; i++) {
			$(".tr"+data[i].trCount).find("select[name='rtnResnDtl']").empty(); // 반품 상세 사유
			
			_setOptEsgCode($(".tr"+data[i].trCount), "1", "", "");
			_setOptEsgCode($(".tr"+data[i].trCount), "2", $.trim(data[i].esgGbn), "");
			_setOptEsgCode($(".tr"+data[i].trCount), "3", $.trim(data[i].esgGbn), $.trim(data[i].esgAuth));
			
// 			$(".tr"+data[i].trCount).find("select[name='esgGbn']").append($("#lLvCdn > option").clone());
// 			$(".tr"+data[i].trCount).find("select[name='esgAuth']").append($("#mLvCdn > option").clone());
// 			$(".tr"+data[i].trCount).find("select[name='esgAuthDtl']").append($("#sLvCdn > option").clone());
			
			$(".tr"+data[i].trCount).find("select[name='esgGbn']").val( data[i].esgGbn );
			$(".tr"+data[i].trCount).find("select[name='esgAuth']").val( data[i].esgAuth );
			$(".tr"+data[i].trCount).find("select[name='esgAuthDtl']").val( data[i].esgAuthDtl );
			
			$(".tr"+data[i].trCount).find("select[name='esgGbn']").attr('disabled', true);
			$(".tr"+data[i].trCount).find("select[name='esgAuth']").attr('disabled', true);
			$(".tr"+data[i].trCount).find("select[name='esgAuthDtl']").attr('disabled', true);
        }
		rowDataCnt = json.length;
   	}else{
   		rowDataCnt = 0;
   		setTbodyNoResult("dataListbody", 11);
   	}
}

//행추가, 행삭제
function rowBtnEvent(gbn){
	// 행추가(Add), 행삭제(Del)
	
	// 행추가
	if(gbn == 'Add'){
		let checkTdm = $("#dataListbody tr").find(".tdm").length > 0;

		// checkTdm 이 있을 때만 초기화 실행
		if (checkTdm) {
			setTbodyInit("dataListbody"); // dataList 초기화
		}

		let rowData = {
					'rowAttri' 	: 	'new'
				,	'esgGbn'	:	''
				,	'esgAuth'	:	''
				,	'esgAuthDtl':	''
				,	'esgFrDt'	:	''
				,	'esgToDt'	:	''
				,	'esgFileId'	:	''
				,	'pgmId'		:	$("#hiddenForm input[name=pgmId]").val()
				,	'cfmFg'		:	"${prodDetailInfo.cfmFg}"
				,	'entpCd'	:	"${prodDetailInfo.entpCd }"
				,	'trCount'	:	trCount++
				,	'esgYn'		:	''
				,	'esgEarth'	:	''
				,	'delYn'		:	'N'
				,	'esgFileId ':	''
		};
		
		$("#dataListTemplate").tmpl(rowData).appendTo("#dataListbody");
		
		_setOptEsgCode($(".tr"+rowData.trCount), "1", "", "");
		
// 		$(".tr"+trCount).find("select[name='esgGbn']").append($("#lLvCdn > option").clone());
// 		$(".tr"+trCount).find("select[name='esgAuth']").append($("#mLvCdn > option").clone());
// 		$(".tr"+trCount).find("select[name='esgAuthDtl']").append($("#sLvCdn > option").clone());
		trCount++;
	}else{
		var pgmId = $.trim($("#hiddenForm input[name=pgmId]").val());	//신상품코드
		var entpCd = $.trim($("#hiddenForm input[name=entpCd]").val());	//협력사코드
		
		//신상품코드 누락
		if("" == pgmId){
			alert("신상품 코드가 존재하지 않습니다.");
			return;
		}
		
		//협력사코드 누락
		if("" == entpCd){
			alert("협력사코드가 존재하지 않습니다.");
			return;
		}
		
		if( !$('#dataListbody tr').find('input:checked').is(':checked') ){
			alert("삭제 할 Row를 먼저 선택해주세요!");
			return;
		}
		
		//삭제하시겠습니까?
		if(!confirm("<spring:message code='msg.common.confirm.delete'/>")) return;
		// 행삭제
		let msg = '<spring:message code="msg.common.success.delete" />';
		
		//등록데이터 삭제대상
		var delProdDataArr = [];
		
		var flag = true;
		$('#dataListbody tr').find('input:checked').each(function(index){
			var delInfo = {};
			var rowAttri = $(this).closest('tr').find('input[name=rowAttri]').val();
			var esgGbn = $.trim($(this).closest('tr').find("select[name='esgGbn']").val());			//ESG 유형코드
			var esgAuth = $.trim($(this).closest('tr').find("select[name='esgAuth']").val());		//ESG 인증유형
			var esgAuthDtl = $.trim($(this).closest('tr').find("select[name='esgAuthDtl']").val());	//ESG 인증유형상세
			
			<%-- 저장된 데이터일 경우에만 체크 --%>
			if(rowAttri == "search"){
				//ESG 유형 없음
				if(esgGbn == ""){
					alert("ESG 유형이 존재하지 않아 삭제가 불가능합니다");
					flag = false;
					return false;
				}
				//ESG 인증유형이 없음
				if(esgAuth == ""){
					alert("ESG 인증유형이 존재하지 않아 삭제가 불가능합니다");
					flag = false;
					return false;
				}
				//ESG 인증상세유형이 없음
				if(esgAuthDtl == ""){
					alert("ESG 인증상세유형이 존재하지 않아 삭제가 불가능합니다");
					flag = false;
					return false;
				}
				
				delInfo.pgmId = pgmId;					//신상품코드					
				delInfo.esgGbn = esgGbn;				//ESG 유형코드
				delInfo.esgAuth = esgAuth;				//ESG 인증유형코드
				delInfo.esgAuthDtl = esgAuthDtl;		//ESG 인증상세유형코드
				
				//ajax 대상 data array 구성
				delProdDataArr.push(delInfo);
			}
		});
		
		if(!flag) return;
		
		if(delProdDataArr.length > 0){
			var delParam = {};
			delParam.pgmId = pgmId;			//신상품코드
			delParam.entpCd = entpCd;		//협력사코드
			delParam.prodDataArr = delProdDataArr;	//삭제대상 list
			
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				url : '<c:url value="/edi/product/deleteNewProdEsg.json"/>',
				data : JSON.stringify(delParam),
				success : function(data) {
					if(data.msg == "success"){
						//선택 data 전체 삭제
						$('#dataListbody tr').find('input:checked').closest("tr").remove();
					}else{
						msg = '삭제에 실패했습니다.\n다시 확인해주세요.';
					}
					alert(msg);
				}
			});
		}else{
			//선택 data 전체 삭제
			$('#dataListbody tr').find('input:checked').closest("tr").remove();
		}
	}
}


// 저장
function btnEvent(gbn){
	var pgmId = $.trim($("#hiddenForm input[name=pgmId]").val());
	var esgYn = $.trim($("select[name='esgYn'] option:selected").val());			//ESG상품구분
	var esgEarth = $.trim($("select[name='esgEarth'] option:selected").val());		//RE:EARTH 로고 사용여부
	var blankLen = $("#dataListbody tr").find("td.tdm").length;						//blankRow length
	var dataLen = (blankLen>0)?0:$("#dataListbody tr").find("td").length;
	var entpCd = $.trim($("#hiddenForm #entpCd").val());							//협력사코드
	var delYn = (esgYn == "1")?"N":"Y";	//삭제여부_ESG적용대상일경우 N
	
	//신상품코드 누락
	if("" == pgmId){
		alert("신상품코드가 존재하지 않아 ESG정보 저장이 불가능합니다.");
		return;
	}
	
	//ESG적용구분 누락
	if(esgYn == ""){
		alert("ESG상품 구분을 선택해주세요!");
		$("#esgYn").focus();
		return;
	}
	
	//ESG적용상품일경우, RE:EARTH로고 선택 필수
	if(esgYn == "1" && esgEarth == "0"){
		alert('RE:EARTH 로고 사용여부를 선택해주세요!');
		$("#esgEarth").focus();
		return;
	}
	
	//ESG적용상품일 경우, 인증정보 1건이상 등록 필수
	if(esgYn == "1" && dataLen == 0){
		alert("ESG 인증정보를 1건이상 등록해주세요.");
		return;
	}
	
	//ESG적용상품이면서, RE:EARTH로고 '미사용'인 경우, confirm message 표시
	if(esgYn == "1" && esgEarth == "2"){
		if(!confirm("RE:EARTH 로고 미사용 상품(분리배출 표기 제외)이 맞습니까?")){ return;}
	}
	
	var formData = new FormData();
	
	var saveInfo = {};
	var prodDataArr = [];	//요청대상
	var duplChkArr = [];	//중복체크대상
	var flag = true;

	//ESG 적용 상품일 경우에만 datalist 구성
	if(esgYn == "1"){
		$("#dataListbody tr").each(function(i,e){
			var prodData = {};
			
			var esgGbn = $.trim($(this).find("select[name='esgGbn']").val());			//ESG 유형코드
			var esgAuth = $.trim($(this).find("select[name='esgAuth']").val());			//ESG 인증유형
			var esgAuthDtl = $.trim($(this).find("select[name='esgAuthDtl']").val());	//ESG 인증유형상세
			var esgToDt	= $.trim($(this).find("input[name='esgFrDt']").val());			//인증시작일
			var esgAfToDt = $.trim($(this).find("input[name='esgToDt']").val());		//인증종료일
			
			//ESG 유형 없음
			if(esgGbn == ""){
				alert("ESG 유형이 존재하지 않아 저장이 불가능합니다.\n행번호:"+Number(i+1));
				flag = false;
				return false;
			}
			//ESG 인증유형이 없음
			if(esgAuth == ""){
				alert("ESG 인증유형이 존재하지 않아 저장이 불가능합니다.\n행번호:"+Number(i+1));
				flag = false;
				return false;
			}
			//ESG 인증상세유형이 없음
			if(esgAuthDtl == ""){
				alert("ESG 인증상세유형이 존재하지 않아 저장이 불가능합니다.\n행번호:"+Number(i+1));
				flag = false;
				return false;
			}
			
			//ESG 인증기간 체크
			if(!fncChkEsgDate(this)){
				flag = false;
				return false;
			}
			
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
					
					if(this.name == "esgGbn" || this.name == "esgAuth" || this.name == "esgAuthDtl"){
						prodData[this.name+"Txt"] = $(this).find("option:selected").text();
					}
					
					//formdata setting
					<%-- 파일 Object 없을 때 formdata 생성하면 getter에서 null exception 발생함 --%>
					if(this.type != "file" || (this.type == "file" && $(this)[0].files.length > 0)){
						formData.append("prodDataArr["+i+"]."+this.name, prodData[this.name]);
					}
				}
			});
			
			
			if(prodData != null){
				duplChkArr.push(prodData);
			}
		});
		
		if(!flag) return;
		
		//ESG 유형 중복체크
		var duplList = duplChkArr.filter((item1, idx1)=>{
			return duplChkArr.findIndex((item2, idx2)=>{
				{return item1.esgGbn == item2.esgGbn  && item1.esgAuth == item2.esgAuth && item1.esgAuthDtl == item2.esgAuthDtl}
			}) != idx1;
		}).map((ele) => [{'esgGbn':ele.esgGbn,'esgAuth':ele.esgAuth,'esgAuthDtl':ele.esgAuthDtl,'esgGbnTxt': ele.esgGbnTxt,'esgAuthTxt': ele.esgAuthTxt,'esgAuthDtlTxt': ele.esgAuthDtlTtx}]);
		
		var esgDuplMsg = "";
		$.each(duplList, function(i,v){
			esgDuplMsg += "\n";
			esgDuplMsg += v.esgGbnTxt;
			esgDuplMsg += "/";
			esgDuplMsg += v.esgAuthTxt;
			esgDuplMsg += "/";
			esgDuplMsg += v.esgAuthDtlTxt;
		});
		
		if(duplList.length > 0){
			alert("ESG 유형/인증유형/인증상세유형 중복값이 존재합니다.\n다시 확인해주세요!"+esgDuplMsg);
			return;
		}
	}
	
	formData.append("pgmId", pgmId);		//신상품코드
	formData.append("esgYn", esgYn);		//ESG상품구분
	formData.append("esgEarth", esgEarth);	//RE:EARTH 로고 사용여부
	formData.append("delYn", delYn);		//삭제여부
	formData.append("entpCd", entpCd);		//협력사코드
	
	$.ajax({
		url:'<c:url value="/edi/product/updateNewProdEsgInfoWithFile.json"/>',
		contentType : false,
		type:"POST",
		dataType: "JSON",
		data:formData,
		cache: false,
		async:false,
		processData: false,
		success:function(result) {
			console.log(result);
			var msg = result.msg;
			
			if("success" == msg){
				alert("저장되었습니다.");
			}else{
				var errMsg = $.trim(data.errMsg) != "" ? data.errMsg : "처리 중 오류가 발생했습니다.";
				alert(errMsg);
			}
			
			btnSearch('1');
		}
	});
}

// btn event
function btnEvent_old(gbn, obj){
	// 저장(sava)
	
	var formData = new FormData();
	let itemList = [];
	
	var pgmId = $.trim($("#hiddenForm input[name=pgmId]").val());
	var esgYn = $.trim($("select[name='esgYn'] option:selected").val());			//ESG상품구분
	var esgEarth = $.trim($("select[name='esgEarth'] option:selected").val());		//RE:EARTH 로고 사용여부
	var blankLen = $("#dataListbody tr").find("td.tdm").length;						//blankRow length
	var dataLen = (blankLen>0)?0:$("#dataListbody tr").find("td").length;
	var entpCd = $.trim($("#hiddenForm #entpCd").val());							//협력사코드
	
	//ESG적용구분 누락
	if(esgYn == ""){
		alert("ESG상품 구분을 선택해주세요!");
		$("#esgYn").focus();
		return;
	}
	
	//ESG적용상품일경우, RE:EARTH로고 선택 필수
	if(esgYn == "1" && esgEarth == "0"){
		alert('RE:EARTH 로고 사용여부를 선택해주세요!');
		$("#esgEarth").focus();
		return;
	}
	
	if(esgYn == "1" && dataLen == 0){
		alert("ESG 인증정보를 1건이상 등록해주세요.");
		return;
	}
	
	// esg 상품 구분이 적용이면.
	if( $("select[name='esgYn'] option:selected").val() == '1'){
		// 첫번째 행은 마스터성 데이터
		let itemObj = {
					'pgmId' : pgmId
				,	'esgYn' : esgYn
				,	'esgEarth' : esgEarth
				,	'delYn'	: 'N'
				,	'entpCd': entpCd
		};
		itemList.push(itemObj);
		
		// 두번째 행부터 itemlist 구성
		$.each($("#dataListbody tr"), function(idx, item) {
			let itemObj = {};
			// 검색 데이터가 아니면 저장 실행
			if( $(this).find('input[name=rowAttri]').val() != 'search' ){
				$.each($(this).find('input , select'), function(idx, item) {
			     	if( this.name == 'esgFrDt' || this.name == 'esgToDt'){
			     		itemObj[this.name] = item.value.replace(/[^0-9]/g, "");	// 날짜
			     	}
			     	else if( this.name == 'esgFileId' ) {
			     		// 첨부파일
			     		let file = $(this)[0].files[0];
			     		if( typeof file != 'undefined') itemObj['ordFileNm'] = file.name;
			     		formData.append("files", file);
			     	}else itemObj[this.name] = item.value;
				});
// 				itemObj['esgYn'] = esgYn;
// 				itemObj['esgEarth'] = esgEarth;
				
				if(typeof itemObj.ordFileNm == 'undefined'){
		     		itemObj['ordFileNm'] = '';
		     	}
				itemList.push(itemObj);
			}
	    });
		
// 		if(itemList.length <= 0){
// 			alert('변경된 내용이 없습니다');
// 			return;
// 		}
		
		if(itemList.length > 1){
			if(!validation(itemList)) return;			
		}
		
		url = "<c:url value='/edi/product/insertNewProdEsg.json'/>";
	}else{
		// esg 상품 구분이 미적용이면
		let itemObj = {
					'pgmId' : pgmId
				,	'esgYn' : esgYn
				,	'esgEarth' : esgEarth
				,	'delYn'	: 'Y'
				,	'entpCd': entpCd
		};
		itemList.push(itemObj);
		url = "<c:url value='/edi/product/updateNewProdEsg.json'/>";
	}
	
	formData.append("jsonData", JSON.stringify(itemList) );

	if(!confirm("<spring:message code='msg.srm.alert.tempSave'/>")) return;
	
	//console.log( itemList );
	
	$.ajax({	
		url: url,
		type: "POST",
		data: formData,
		dataType:'json',
		processData: false,   // tell jQuery not to process the data
		contentType: false ,  // tell jQuery not to set contentType,
		success : function(data){
			if(data.result == false){
				if($.trim(data.errMsg) != ""){
					msg = "저장에 실패하였습니다.\n"+data.errMsg;
				}else{
					msg = '저장에 실패했습니다.\n다시 확인해주세요.';
				}
			} else {
				msg = '<spring:message code="msg.common.success.save" />';
			}
			
			alert(msg);
			
			btnSearch('1');
		}
	});
    
}

//ESG 인증기간 체크
function fncChkEsgDate(obj){
	var esgFrDt = $.trim($(obj).find("input[name='esgFrDt']").val()).replace(/\D/g, "");		//인증시작일
	var esgToDt = $.trim($(obj).find("input[name='esgToDt']").val()).replace(/\D/g, "");		//인증종료일
	var esgDtFg = $.trim($(obj).find("input[name='esgDtFg']").val());					//인증기간 필수입력여부
	var rnum = $.trim($(obj).closest("tr").index());
	
	//인증기간 필수 아닐 경우, 날짜 미입력 시 체크하지 않음(필수X)
	if(esgDtFg == "" && esgFrDt == "" && esgToDt == ""){
		return true;
	}
	if(esgFrDt == "" || esgFrDt.length != 8){
		alert("인증시작일을 올바르게 입력해주세요.");
		$(obj).find("input[name='esgFrDt']").focus();
		return false;
	}
	
	if(esgToDt == "" || esgToDt.length != 8){
		alert("인증종료일을 올바르게 입력해주세요.");
		$(obj).find("input[name='esgToDt']").focus();
		return false;
	}
	
	var today = "${srchFromDt}";

	//인증기간 필수 아닐 경우, 날짜 미입력 시 체크하지 않음(필수X)
	if(esgDtFg == "X" || esgFrDt != "" || esgToDt != ""){
		//날짜 필수값 체크
		if(esgFrDt == "" || esgToDt == ""){
			alert("인증기간 <spring:message code='msg.common.fail.nocalendar'/>");
			return false;
		}
		
		//시작일보다 작은지 여부 체크
	    if (esgFrDt > esgToDt) {
	        alert("인증시작일은 <spring:message code='msg.common.fail.calendar'/>");
	        return false;
	    }
		
		if(esgToDt < today.replace(/[^0-9]/g, "")){
			alert("인증종료일은 과거일자 입력이 불가능합니다!");
	        return false;
		}
	}
	
	return true;
}

//검증
function validation_old(info){
	let dataList = info;
	
	let itemList = [];
	$.each($("#dataListbody tr"), function(idx, item) {
		let itemObj = {};
		$.each($(this).find('input , select'), function(idx, item) {
	     		itemObj[this.name] = item.value;
		});
		itemList.push(itemObj);
	});

	let keyChk = 'success';
	// 키값 중복 체크
	for(let i = 0; i < itemList.length; i++ ){
		for(let j = i+1; j < itemList.length; j++ ){
			// key chk!
			if( itemList[j].esgGbn == itemList[i].esgGbn && itemList[j].esgAuth == itemList[i].esgAuth && itemList[j].esgAuthDtl == itemList[i].esgAuthDtl ) {
				keyChk = 'fail';
			}
		}
	}
	
	if(keyChk != 'success'){
		alert('유형 / 인증유형 / 인증상세유형 중복값이 존재합니다.\n다시 확인해주세요!');
		return false;
	}
	
	// insert할 데이터 검증 (두번째행부터)
	for(let i = 1; i < dataList.length; i++ ){
		let esgFrDt = $.trim(dataList[i].esgFrDt).replace(/[^0-9]/g, "");
		let esgToDt = $.trim(dataList[i].esgToDt).replace(/[^0-9]/g, "");
		let today = "${srchFromDt}";
		
		var limitExtArr = $.trim(extLimit).split("|") || [];
		if(dataList[i].ordFileNm != ''){
			let ext = dataList[i].ordFileNm.split('.').pop().toLowerCase(); // 확장자
			var extLimitTxt = extLimit.replace(/\|/g,",");
			if($.inArray(ext, limitExtArr) == -1){
				alert(extLimitTxt+" 파일만 업로드 할수 있습니다.");
				return false;
			}
		}
		
// 		if( dataList[i].ordFileNm != ''){
// 			let ext = dataList[i].ordFileNm.split('.').pop().toLowerCase(); // 확장자
// 			if($.inArray(ext, ['jpg','jpeg','gif','png', 'pdf']) == -1) {
// 				alert("jpg,gif,jpeg,png,pdf 파일만 업로드 할수 있습니다.");
// 				return false;
// 			}
// 		}
		
// 		if( dataList[i].esgYn == '1' ){
// 			if( dataList[i].esgEarth == '0'){
// 				alert('RE:EARTH 로고 사용을 선택해주세요!');
// 				return false;
// 			}
// 		}
		
		//인증기간 필수 아닐 경우, 날짜 미입력 시 체크하지 않음(필수X)
		if( dataList[i].esgDtFg == "X" || esgFrDt != "" || esgToDt != ""){
			//날짜 필수값 체크
			if(esgFrDt == "" || esgToDt == ""){
				alert("인증기간 <spring:message code='msg.common.fail.nocalendar'/>");
				return false;
			}
			
			//시작일보다 작은지 여부 체크
		    if (esgFrDt > esgToDt) {
		        alert("인증시작일은 <spring:message code='msg.common.fail.calendar'/>");
		        return false;
		    }
			
			if(esgToDt < today.replace(/[^0-9]/g, "")){
				alert("인증종료일은 과거일자 입력이 불가능합니다!");
		        return false;
			}
		}
	}
    return true;
}

// file upload
function fncFileUpload(obj, inputName) {
	$(obj).closest('tr').find('input[name="'+inputName+'"]').trigger("click");
}
 
 // row change
 function chgRowAttri(obj){
	 
	 $(obj).closest('tr').find('input[name=rowAttri]').val('mdf');
	 
 }
 
 // file down~!
 function fncFileDown(esgFileId){
	$('#fileForm input[name=atchFileId]').val(esgFileId);
	$('#fileForm').attr("action", '<c:url value="/edi/product/esgFileDown.do"/>')
	$('#fileForm').submit();
 }
 
//esg 인증 옵션 셋팅
function _setOptEsgCode(obj, selLvl, lCd, mCd){
	if($(obj) == undefined) return;
	
	var selBoxNm = "";
	var optListData = [];
	var optHtml = "";
	
	switch(selLvl){
		case "1":
			selBoxNm = "esgGbn";
			<c:if test="${not empty esgMstL }">
			optListData = ${esgMstL};
			</c:if>
			break;
		case "2":
			selBoxNm = "esgAuth";
			<c:if test="${not empty esgMstM }">
			optListData = ${esgMstM};
			</c:if>
			break;
		case "3":
			selBoxNm = "esgAuthDtl";
			<c:if test="${not empty esgMstS }">
			optListData = ${esgMstS};
			</c:if>
			break;
		default:
			return;
	}
	
	if("" == selBoxNm) return;
	
	//select option 초기화
	var tgSelBox = $(obj).closest("tr").find("select[name='"+selBoxNm+"']"); 
	tgSelBox.find("option[value!='']").remove();
	
	if(optListData == undefined || optListData == null || optListData.length == 0) return;

	switch(selLvl){
		case "1":
			for(var i=0; i<optListData.length; i++){
				optHtml += '<option value="'+$.trim(optListData[i].lLvCdn)+'">'+optListData[i].lLvNmn+'</option>';
			}
			break;
		case "2":
			for(var i=0; i<optListData.length; i++){
				if($.trim(optListData[i].lLvCdn) != $.trim(lCd)) continue;
				optHtml += '<option value="'+$.trim(optListData[i].mLvCdn)+'">'+optListData[i].mLvNmn+'</option>';
			}
			break;
		case "3":
			for(var i=0; i<optListData.length; i++){
				if($.trim(optListData[i].lLvCdn) != $.trim(lCd) || $.trim(optListData[i].mLvCdn) != $.trim(mCd)) continue;
				optHtml += '<option value="'+$.trim(optListData[i].sLvCdn)+'" data-esgDtFg="'+$.trim(optListData[i].esgDtFg)+'">'+optListData[i].sLvNmn+'</option>';
			}
			break;
		default:
			return;
	}
	
	//option setting
	tgSelBox.append(optHtml);
}
 
//file 영역 clear
function fncClearFile(obj){
	//기본 setting mesage
	var defHtml = '<span name="fileIdView">'+extLimit+' Only</span>';
	
	$(obj).closest("tr").find("span[name='fileIdView']").remove();
	$(obj).closest("tr").find("span[name='fileIdView']").closest("a").remove();
	$(obj).after(defHtml);
	$(obj).val("");
}
</script>
<!-- DATA LIST -->
<script id="dataListTemplate" type="text/x-jquery-tmpl">
<tr class="tr\${trCount}"  bgcolor=ffffff>
	<c:choose>
		<c:when test="${param.mode ne 'view'}">
			<td align="center">
				<input type="checkbox" class="notInc" name="chk" value="" style="height: 20px;"  />
			</td>
			<td align="center">
				<select id="esgGbn\${trCount}" name="esgGbn" class="required" style="width:130px;" onchange="chgRowAttri(this)">
					<option value="">선택</option>
				</select>
			</td>
			<td align="center">
				<select id="esgAuth\${trCount}" name="esgAuth" class="required" style="width:130px;" onchange="chgRowAttri(this)">
					<option value="">선택</option>
				</select>
			</td>
			<td align="center">
				<select id="esgAuthDtl\${trCount}" name="esgAuthDtl" class="required" style="width:130px;" onchange="chgRowAttri(this)">
					<option value="">선택</option>
				</select>
			</td>
			<td align="center">
				<input type="text" class="day" name="esgFrDt" id="esgFrDt\${trCount}"  value="<c:out value="\${esgFrDtFmt}"/>" style="width: 80px;" disabled />
				<img src="/images/epc/layout/btn_cal.gif" class="middle datepicker" style="cursor: pointer;" />
			</td>
			<td align="center">
				<input type="text" class="day" name="esgToDt" id="esgToDt\${trCount}"  value="<c:out value="\${esgToDtFmt}"/>" style="width: 80px;" disabled />
				<img src="/images/epc/layout/btn_cal.gif" class="middle datepicker" style="cursor: pointer;" />
			</td>
			<td align="center">
				<div>
					<input type="file" id="esgFile\${trCount}" name="esgFile" style="display:none;"></input>
					{%if esgFileId == '' || esgFileId == null %} 
						<span name="fileIdView"><c:out value='\${extLimit}'/> Only</span>
					{%/if%}
					{%if esgFileId != '' && esgFileId != null %} 
						<a href="#" onclick="fncFileDown('\${esgFileId}');"><span name="fileIdView" style = "color: blue;"><c:out value='\${orgFileNm}'/></span></a>
					{%/if%}
					<a href="#" class="btn" onclick="fncFileUpload(this, 'esgFile')"><span>찾아보기</span></a>
				</div>
			</td>
		</c:when>
		<c:otherwise>
			<td align="center"></td>
			<td align="center"><c:out value="\${esgGbnNm}"/></td>
			<td align="center"><c:out value="\${esgAuthNm}"/></td>
			<td align="center"><c:out value="\${esgAuthDtlNm}"/></td>
			<td align="center"><c:out value="\${esgFrDtFmt}"/></td>
			<td align="center"><c:out value="\${esgToDtFmt}"/></td>
			<td align="center">
				<div>
					{%if esgFileId == '' || esgFileId == null %} - {%/if%}
					{%if esgFileId != '' && esgFileId != null %} 
						<a href="#" onclick="fncFileDown('\${esgFileId}');"><span name="fileIdView" style = "color: blue;"><c:out value='\${orgFileNm}'/></span></a>
					{%/if%}
				</div>
			</td>
		</c:otherwise>
	</c:choose>
	
	<input type="hidden" name="rowAttri" value="\${rowAttri}"  />
	<input type="hidden" name="pgmId" value="\${pgmId}"  />
	<input type="hidden" name="cfmFg" value="\${cfmFg}"  />
	<input type="hidden" name="esgYn" value="\${esgYn}"  />
	<input type="hidden" name="esgEarth" value="\${esgEarth}"  />
	<input type="hidden" name="delYn" value="\${delYn}"  />
	<input type="hidden" name="ordEsgGbn" value="\${esgGbn}"  />
	<input type="hidden" name="ordEsgAuth" value="\${esgAuth}"  />
	<input type="hidden" name="ordEsgAuthDtl" value="\${esgAuthDtl}"  />
	<input type="hidden" name="esgFileId" value="\${esgFileId}"  />
	<input type="hidden" name="saveFileId" value="\${saveFileId}"  />
	<input type="hidden" name="saveFileNm" value="\${saveFileNm}"  />
	<input type="hidden" name="filePath" value="\${filePath}"  />
	<input type="hidden" name="esgDtFg" value="\${esgDtFg}"  />
</tr>
</script>
</head>
<body>
	<div id="content_wrap">
		<!-- tab 구성---------------------------------------------------------------->
		<div id="prodTabs" class="tabs" style="padding-top: 10px;">
			<ul>
				<li id="pro01" style="cursor: pointer;"><spring:message code="msg.product.onOff.default.notice" /></li>	<!-- 기본정보 -->
				<li id="pro02" style="cursor: pointer;"><spring:message code='msg.product.onOff.default.itemProperty' /></li>	<!-- 상품속성 -->
				<li id="pro03" style="cursor: pointer;"><spring:message code='msg.product.tab.img' /></li>			<!-- 이미지  -->
				<li id="pro04" style="cursor: pointer;">영양성분</li>
				<li id="pro05" class="on" style="cursor: pointer;">ESG</li>
			</ul>
		</div>
		<!-- tab 구성---------------------------------------------------------------->
		<div>
			<form name="searchForm" id="searchForm">
				<input type="hidden" id="pageIndex" name="pageIndex" value="<c:out value="${param.pageIndex}" />"  />
				<div id="wrap_menu">
					<div class="wrap_search">
						<div class="bbs_search">
							<ul class="tit">
								<li class="tit"></li>
								<li class="btn">
									<!-- <a href="#" class="btn" onclick="btnSearch('1')"><span>조회</span></a> -->
									<c:if test="${param.mode ne 'view'}">
									<a href="#" class="btn" onclick="btnEvent('save')"><span>저장</span></a>																	
									</c:if>
								</li>
							</ul>
							<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
								<colgroup>
									<col style="width:15%" />
									<col style="width:30%" />
									<col style="width:20%" />
									<col style="*" />
								</colgroup>
								<tr>
									<th>ESG상품 구분</th>
									<td>
										<c:choose>
											<c:when test="${param.mode ne 'view'}">
												<html:codeTag objId="esgYn" objName="esgYn" comType="SELECT" parentCode="ESGGB" formName="form" orderSeqYn="Y" selectParam="${prodDetailInfo.esgYn}"/>
											</c:when>
											<c:otherwise>
												<html:codeTag objId="esgYn" objName="esgYn" comType="SELECT" parentCode="ESGGB" formName="form" orderSeqYn="Y" selectParam="${prodDetailInfo.esgYn}" disabled="disabled"/>
											</c:otherwise>
										</c:choose>
										
									</td>
									<th>RE:EARTH 로고 사용</th>
									<td>
										<c:choose>
											<c:when test="${param.mode ne 'view'}">
												<html:codeTag objId="esgEarth" objName="esgEarth" comType="SELECT" parentCode="ESGRE" formName="form" orderSeqYn="Y" defName="선택" defValue="0" selectParam="${prodDetailInfo.esgEarth}"/>
											</c:when>
											<c:otherwise>
												<html:codeTag objId="esgEarth" objName="esgEarth" comType="SELECT" parentCode="ESGRE" formName="form" orderSeqYn="Y" defName="선택" defValue="0" selectParam="${prodDetailInfo.esgEarth}" disabled="disabled"/>
											</c:otherwise>
										</c:choose>
										<%--
										<select name="lLvCdn" id="lLvCdn" style="display: none;">
											<c:if test="${not empty esgMstL }">
												<c:forEach items="${esgMstL}" var="list" varStatus="index" >
													<option value="${list.lLvCdn}">${list.lLvNmn}</option>
												</c:forEach>
											</c:if>
										</select>
										
										<select name="mLvCdn" id="mLvCdn" style="display: none;">
											<c:if test="${not empty esgMstM }">
												<c:forEach items="${esgMstM}" var="list" varStatus="index" >
													<option value="${list.mLvCdn}">${list.mLvNmn}</option>
												</c:forEach>
											</c:if>
										</select>
										
										<select name="sLvCdn" id="sLvCdn" style="display: none;">
											<c:if test="${not empty esgMstS }">
												<c:forEach items="${esgMstS}" var="list" varStatus="index" >
													<option value="${list.sLvCdn}">${list.sLvNmn}</option>
												</c:forEach>
											</c:if>
										</select>
										 --%>
									</td>
								</tr>
							</table>
						</div>
					</div>
					</div>
				</form>
				<form name="reqDetailForm" id="reqDetailForm" method="post" enctype="multipart/form-data">
					<div class="wrap_con">
						<div class="bbs_list">
							<ul class="tit">
								<li class="tit">ESG 인증 </li>
								<li class="btn">
									<c:if test="${param.mode ne 'view'}">
									<a href="#" class="btn" onclick="rowBtnEvent('Add');"><span>행추가</span></a>
									<a href="#" class="btn" onclick="rowBtnEvent('Del');"><span>행삭제</span></a>	
									</c:if>
								</li>
							</ul>
							<div style="width:100%; height:458px;overflow-x:hidden; overflow-y:scroll; overflow-x:scroll;  table-layout:fixed;white-space:nowrap;">
								<table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=1000 bgcolor=efefef>
									<colgroup>
										<col style="width:2%"/>
										<col style="width:10%"/>
										<col style="width:10%"/>
										<col style="width:10%"/>
										<col style="width:15%"/>
										<col style="width:15%"/>
										<col style="*"/>
									</colgroup>
									<tr bgcolor="#e4e4e4">
										<th><input type="checkbox" id="allCheck" name="chk"></th>
										<th>유형</th>
										<th>인증유형</th>
										<th>인증상세유형</th>
										<th>인증시작일</th>
										<th>인증종료일</th>
										<th>인증서첨부</th>
									</tr>
									<tbody id="dataListbody" />
								</table>
							</div>
						</div>
					</div>
				</form>
				
				<div id="footer">
					<div id="footbox">
						<div class="msg" id="resultMsg"></div>
						<div class="notice"></div>
						<div class="location">
							<ul>
								<li>홈</li>
								<li>상품</li>
								<li>상품현황관리</li>
								<li class="last">신규상품등록</li>
							</ul>
						</div>
					</div>
				</div>
				
			</div>
		</div>
	</div>
	<!-- hidden Form -->
	<form name="hiddenForm" id="hiddenForm">
		<input type="hidden" name="vendorTypeCd" id="vendorTypeCd" value="<c:out value='${epcLoginVO.vendorTypeCd}'/>" />
		<input type="hidden" name="pgmId" id="pgmId" value="<c:out value='${prodDetailInfo.pgmId }'/>" />
		<!-- 상품이 등록되고 나면 등록된 상품의 pgmId가 설정됨 -->
		<input type="hidden" name="entpCd" id="entpCd" value="<c:out value='${prodDetailInfo.entpCd }'/>" />
		<!-- 상품이 등록되고 나면 등록된 상품의 협력업체코드가 설정됨 -->
		<input type="hidden" name="mode" id="mode" value="<c:out value='${param.mode}'/>" />
		<!-- view, modify, ''-->
		<input type="hidden" name="oldL3Cd" id="oldL3Cd" />
		<input type="hidden" name="oldGrpCd" id="oldGrpCd" />
		<input type="hidden" name="l1Cd"    id="l1Cd"    value="<c:out value='${prodDetailInfo.l1Cd }'/>" />
		<input type="hidden" name="l3Cd"    id="l3Cd"    value="<c:out value='${prodDetailInfo.l3Cd }'/>" />
		<input type="hidden" name="oldL3Cd" id="oldL3Cd" value="<c:out value='${prodDetailInfo.l3Cd }'/>" />
		<input type="hidden" name="delNutAmtYn" id="delNutAmtYn" value="N"/>
		<input type="hidden" name="oldProdAttTypFg" id="oldProdAttTypFg" />
		<input type="hidden" name="cfmFg" id="cfmFg" value="<c:out value='${prodDetailInfo.cfmFg}'/>" />
		<input type="hidden" name="hiddenProdDivnCd" id="hiddenProdDivnCd" value="<c:out value='${prodDetailInfo.prodDivnCd}'/>" />
		<input type="hidden" name="ecCategoryKeepYn" id="ecCategoryKeepYn" value="true"/>
		<input type="hidden" name="ecAttrRegYn" 	 id="ecAttrRegYn" />
		<!-- 상품확정구분 -->
		<input type="hidden" name="entpCdPbFlag" id="entpCdPbFlag" value="" />
	</form>
	
	<form id="fileForm" name="fileForm" method="post">
		<input type="hidden" id="atchFileId" name="atchFileId"/>
	</form>

</body>
</html>
