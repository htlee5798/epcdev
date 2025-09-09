<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jstl/core_rt"       %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn"    uri="/WEB-INF/tlds/function.tld"             %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"    %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt"       %>
<%@ include file="/common/scm/scmCommon.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>  
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />

<script type="text/javascript" >

/** ********************************************************
 * 숫자입력 
 ******************************************************** */
function onlyNumber(event){
	event = event || window.event;
	var keyID = (event.which) ? event.which : event.keyCode;
	if ( (keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105) || keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 ) 
		return;
	else
		return false;
}
function removeChar(event) {
	event = event || window.event;
	var keyID = (event.which) ? event.which : event.keyCode;
	if ( keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 ) 
		return;
	else
		event.target.value = event.target.value.replace(/[^0-9]/g, "");
}


	/** ********************************************************
	 * 저장 처리 함수
	 ******************************************************** */
	function insert()
	{
		var form = document.popupForm;
		var url = '<c:url value="/board/insertCcQnaPopup.do"/>';
		
		//입력값 체크
	    if(form.clmLgrpCd.value == "200"){
			if( !chkfield(form.let4Ref, form.let3Ref.value, "상품명") ) 
		    {
		        return;
		    }
		}else if(form.clmLgrpCd.value == "400"){
			if( !chkfield(form.orderId, form.orderId.value, "주문번호") ) 
		    {
		        return;
		    }
		}else if(form.clmLgrpCd.value == "980"){
			if( !chkfield(form.let4Ref, form.let3Ref.value, "상품명") ) 
		    {
		        return;
		    }
			
			if( !chkfield(form.orderId, form.orderId.value, "주문번호") ) 
		    {
		        return;
		    }
		}
			    	    
	   
	    
	    if( !chkfield(form.vendorId, form.vendorId.value, "업체") ) 
	    {
	        return;
	    }
	    
	    if( !chkfield(form.memberNm, form.memberNm.value, "담당자") ) 
	    {
	        return;
	    }
	    
	    if( !chkfield(form.cellNo1, form.cellNo1.value, "연락처") ) 
	    {
	        return;
	    }
	    
	    if( !chkfield(form.cellNo2, form.cellNo2.value, "연락처") ) 
	    {
	        return;
	    }
	    
	    if( !chkfield(form.cellNo3, form.cellNo3.value, "연락처") ) 
	    {
	        return;
	    }
	   
	    if (!(!Common.isEmpty($.trim($('#cellNo1').val())) && !Common.isEmpty($.trim($('#cellNo2').val())) && !Common.isEmpty($.trim($('#cellNo3').val())))) {
			if (!(Common.isEmpty($.trim($('#cellNo1').val())) && Common.isEmpty($.trim($('#cellNo2').val())) && Common.isEmpty($.trim($('#cellNo3').val())))) {
				alert('연락처 정보를 입력하려면 처음,중간,끝 항목을 모두 채워야 합니다.');
				return false;
			}
		}
		
		if(isNaN($('#cellNo1').val()) || isNaN($('#cellNo2').val()) || isNaN($('#cellNo3').val())) {
			alert('<spring:message code="msg.common.error.notNum" arguments="연락처"/>');
			return false;
		}
	    
	    if(form.email.value!=null && form.email.value!=""){

			var str = form.email.value.trim();
			var invalidChars = "\"|&;<>!*\'\\)(][}{^?$%";
			var i,j;

			if(str.indexOf("@") == -1 || str.indexOf(".")<3){
			    alert("이메일의 형식이 잘못되었습니다. ");
			   	return;
			 }				
			for(i = 0; i < str.length; i++) {
				if(str.charAt(i) == ' '){
				    alert("email에 공백을 제거해주세요");
					return;
				}
			}
			
			for(i = 0; i<str.length; i++) {
				for(j = 0; j<invalidChars.length; j++){
					if(str.charAt(i) == invalidChars.charAt(j)){
						alert("잘못된 이메일 주소입니다. 사용하지않는 문자가 들어가 있습니다.");
						return;
					}							
				}													
			}
				
		}
			
		if( !chkfield(form.title, form.title.value, "제목") ) 
	    {
	        return;
	    }
		  
	    if( !chkfield(form.content, form.content.value, "내용") ) 
	    {
	        return;
	    }
	    
	    //특수문자 및 입력길이 체크
	    if( !fnChkSpcCharByte() ) 
	    {
	        return;
	    }
	
	    if (!confirm("문의사항을 등록하시겠습니까?")) 
	    {
	        return;
	    }
	
	    form.action = url;
	    form.submit();
	}
	
	/** ********************************************************
	 * 입력창 null check 함수
	 ******************************************************** */
	function chkfield(focusField, data, name)
	{
	    if (data == "")
	    {
	        alert('<spring:message code="msg.common.error.required" arguments="' + name + '"/>');
	        focusField.focus();
	        return false;
	    }
	    
	    return true;
	}
	
    /** ********************************************************
     * 특수문자 입력여부 및 입력항목의 최대값을 체크한다.
     ******************************************************** */
    function fnChkSpcCharByte()
    {
        var str = document.popupForm.title.value;
        var len = 0;
        var exp = /[~!@\#$%^&*\()<>{}\[\]\-=+_'\"]/gi;

        if (str == null || (event.keyCode == 13))
        {
            return false;
        }

        if (str.search(exp) != -1)
        {
            alert("검색항목에는 특수문자를 사용할수 없습니다!");
            return false;
        }

        for (var i = 0; i < str.length; i++)
        {
            var charSetValue = 'utf-8';

            if ( charSetValue == 'euc-kr' )
            {
                // euc-kr : 한글 2byte용
                var c = escape(str.charAt(i));

                if (c.length == 1) {
                    len++;
                }
                else if (c.indexOf("%u") != -1) {
                    len += 2;
                }
                else if (c.indexOf("%") != -1) {
                    len += c.length / 3;
                }
            }
            else if ( charSetValue == 'utf-8' )
            {
                // utf-8 : 한글 3byte용
                var charCode = str.charCodeAt(i);

                if ( charCode <= 0x0007F ) {
                    len += 1;
                }
                else if ( charCode <= 0x0007FF ) {
                    len += 2;
                }
                else if ( charCode <= 0x00FFFF ) {
                    len += 3;
                }
                else {
                    len += 4;
                }
            }
        }
        
        if (len > 400)
        {
            alert("제목은 400Byte (한글 3Byte, 영숫자 1Byte) 까지만\n" + "입력가능합니다.\n\n" + "[ 현재 : " + len + "Byte ]");
            $("#title").val(str.substr(0,400));
            document.popupForm.title.focus();
            return false;
        }

        return true;
    }
    
    /**********************************************************
     * 특수문자 입력 방지 ex) onKeyPress="keyCode(event)"
     ******************************************************** */
    function keyCode(e)
    {
        var code = (window.event) ? event.keyCode : e.which; // IE : FF - Chrome both

        if (code >  32 && code <  48) keyResult(e);
        if (code >  57 && code <  65) keyResult(e);
        if (code >  90 && code <  97) keyResult(e);
        if (code > 122 && code < 127) keyResult(e);
    }
    function keyResult(e)
    {
        alert("특수문자를 사용할수 없습니다!");

        if (navigator.appName != "Netscape") {
            event.returnValue = false;  //IE - Chrome both
        }
        else {
            e.preventDefault(); //FF - Chrome both
        }
    }
    
    function doAdminId(){
    	var targetUrl = '<c:url value="/board/selectCCagentForm.do"/>'; //01:상품
        Common.centerPopupWindow(targetUrl, 'scmAdminPopup', {width : 700, height : 550});
    }
    
  	//상담원 검색창으로 부터 받은 카테고리 정보 입력
    function setAdminId(loginId,adminNm) // 이 펑션은 상담원 검색창에서 호출하는 펑션임
    {
        document.popupForm.let2Ref.value = adminNm;
        document.popupForm.acceptId.value = loginId;
    }
    
    function doAdminCancel()
    {
    	document.popupForm.let2Ref.value = '';    	
    	document.popupForm.acceptId.value = '';
    }
    
    function doProdCd(){
    	var targetUrl = '<c:url value="/board/selectProductForm.do"/>'; //01:상품
        Common.centerPopupWindow(targetUrl, 'scmProdCdPopup', {width : 700, height : 550});
    }
    
 	//상품 검색창으로 부터 받은 카테고리 정보 입력
    function setProdCd(prodCd,prodNm) // 이 펑션은 상품 검색창에서 호출하는 펑션임
    {
        document.popupForm.let3Ref.value = prodCd;
        document.popupForm.let4Ref.value = prodNm.substring(0,10);
    }
    
    function doProdCancel()
    {
    	document.popupForm.let3Ref.value = '';    	
    	document.popupForm.let4Ref.value = '';
    }
 	
	function doOrderId()
    {
        var targetUrl = '<c:url value="/board/selectOrderIdList.do"/>'; //01:상품
        Common.centerPopupWindow(targetUrl, 'scmOrderIdPopup', {width : 700, height : 550, scrollBars : 'YES'});    	
    }

    //카테고리 검색창으로 부터 받은 카테고리 정보 입력
    function setOrderId(orderId,prodNm) // 이 펑션은 카테고리 검색창에서 호출하는 펑션임
    {
        document.popupForm.orderId.value = orderId;
        document.popupForm.prodNm.value = prodNm;
    }
    
    function doOrderCancel()
    {
        document.popupForm.orderId.value = '';    	
        document.popupForm.prodNm.value = '';
    }	
    
    function chgClmLgrpCd(arg){
		if(arg == "200"){ //상품관련
			$("#clmSpan1").show();
			$("#clmSpan2").hide();
		}else if(arg == "400"){ //주문관련
			$("#clmSpan1").hide();
			$("#clmSpan2").show();
		}else if(arg == "980"){ // A/S접수
			$("#clmSpan1").show();
			$("#clmSpan2").show();
		}else{ //기타
			$("#clmSpan1").hide();
			$("#clmSpan2").hide();
		}
    }   	
</script>

</head>

<body onKeyDown="javascript:if(event.keyCode == 13) event.returnValue=false;">
<form name="popupForm" method="post" enctype="multipart/form-data">
  <div id="popup">
    <!--  @title  -->
     <div id="p_title1">
		<h1>고객센터문의등록</h1>
        <span class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
     </div>
     <!--  @title  //-->
     <!--  @process  -->
     <div id="process1">
		<ul>
			<li>홈</li>
			<li>게시판관리</li>
			<li class="last">고객센터문의관리</li>
		</ul>
     </div>
	 
	 
	 <!-- list -->
	 <div >
		<div class="popup_contents">
		<!--  @작성양식 2 -->
		<div class="bbs_search3">
			<ul class="tit">
				<li class="tit">고객센터문의등록</li>
                <li class="btn">
                    <a href="#" class="btn" onclick="insert();"    ><span><spring:message code="button.common.create"/></span></a>
                    <a href="#" class="btn" onclick="self.close();"><span><spring:message code="button.common.close" /></span></a>
                </li>
			</ul>
			<table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
				<colgroup>
					<col style="width:25%"/>
					<col style="width:75%"/>
				</colgroup>
				<tr>
					<th class="fst"><span class="star">*</span> 문의유형</th>
					<td>
						<select name="clmLgrpCd" id="clmLgrpCd" style="width:150px;" onchange="chgClmLgrpCd(this.value);">
					   	<c:forEach items="${codeList1}" var="code" begin="0">
							<c:if test="${code.MINOR_CD eq '200' || code.MINOR_CD eq '400' || code.MINOR_CD eq '500' || code.MINOR_CD eq '700' || code.MINOR_CD eq '950' || code.MINOR_CD eq '980' }">
							<option value="${code.MINOR_CD}"> ${code.CD_NM }</option>
							</c:if>
						</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th class="fst">상담원</th>
					<td>
						<input type="text" name="let2Ref" id="let2Ref" size="30" readonly="readonly"/>
						<input type="hidden" name="acceptId" id="acceptId" value=""/>
						<a href="javascript:doAdminId();"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/btn/bt_search.gif" alt="" class="middle" /></a>
						<a href="javascript:doAdminCancel();"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/btn/bt_eraser.gif" alt="" class="middle" /></a>
					</td>
				</tr>
				<tr>
					<th class="fst"><span class="star" id="clmSpan1">* </span>상품명</th>
					<td>
						<input type="text" name="let4Ref" id="let4Ref" size="30" readonly="readonly"/>
						<input type="hidden" name="let3Ref" id="let3Ref"/>
						<a href="javascript:doProdCd();"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/btn/bt_search.gif" alt="" class="middle" /></a>
						<a href="javascript:doProdCancel();"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/btn/bt_eraser.gif" alt="" class="middle" /></a>
					</td>
				</tr>
				<tr>
					<th class="fst"><span class="star" id="clmSpan2" style="display:none;">* </span>주문번호</th>
					<td>
						<input type="text" name="orderId" id="orderId" size="30" readonly="readonly"/>
						<input type="text" name="prodNm" id="prodNm" size="30" readonly="readonly"/>
						<a href="javascript:doOrderId();"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/btn/bt_search.gif" alt="" class="middle" /></a>
	                    <a href="javascript:doOrderCancel();"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/btn/bt_eraser.gif" alt="" class="middle" /></a>
					</td>
				</tr>
				<tr>
					<th class="fst"><span class="star">*</span> 협력업체코드</th>
					<td>
                        <c:choose>
						<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
							<input type="text" name="vendorId" id="vendorId" readonly="readonly" value="<c:if test="${not empty epcLoginVO.repVendorId}">${epcLoginVO.repVendorId}</c:if>" style="width:40%;"/>
							<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
						</c:when>
						<c:when test="${epcLoginVO.vendorTypeCd != '06'}">
							<select name="vendorId" id="vendorId" class="select">
							<c:forEach items="${epcLoginVO.vendorId}" var="venArr" begin="0">
		                        <option value="${venArr}" <c:if test="${not empty epcLoginVO.repVendorId && venArr eq epcLoginVO.repVendorId }">selected</c:if>>${venArr}</option>
							</c:forEach>
							</select>
						</c:when>
					</c:choose>
					</td>
				</tr>
				<tr>
					<th class="fst"><span class="star">*</span> 담당자</th>
					<td>
						<input type="text" name="memberNm" id="memberNm" size="30"/>
					</td>
				</tr>
				<tr>
					<th class="fst"><span class="star">*</span> 연락처</th>
					<td>
						<input type="Text" name="cellNo1" id ="cellNo1" size="5"  maxlength="4" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)'/> -
						<input type="Text" name="cellNo2" id ="cellNo2" size="7" maxlength="4" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)'/> -
						<input type="Text" name="cellNo3" id ="cellNo3" size="7" maxlength="4" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)'/>
						SMS 수신여부 
						<input type="radio" name="ansSmsRecvYn" value="Y" checked>Y</input>
					    <input type="radio" name="ansSmsRecvYn" value="N">N</input>
					</td>
				</tr>
				<tr>
					<th class="fst">답변 E-Mail</th>
					<td>
						<input type="text" name="email" id ="email" size="50"/>				
					</td>
				</tr>
				<tr>
					<th class="fst"><span class="star">*</span> 제목</th>
					<td>
					   <input type="text" name="title" id="title" size="100" onKeyPress="keyCode(event)" onChange="fnChkSpcCharByte()"/>
					</td>
				</tr>
	            <tr>
	            <!-- tr>
	                <th class="fst">첨부파일</th>
	                <td>
	                    <input type="file" name="appendFile" size="85"/>
	                </td>
	            </tr> -->
	            <tr>
	                <th class="fst"><span class="star">*</span> 내용</th>
	                <td>
	                    <textarea name="content" id="content" cols="100" rows="15" wrap="hard" style="ime-mode:active"></textarea>
	                </td>
	            </tr>
			</table>
			</div>
		</div>
	</div>
</form>
</body>
</html>