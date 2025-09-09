<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jstl/core_rt"      %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="lfn"    uri="/WEB-INF/tlds/function.tld"            %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"   %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt"      %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>  
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<script language="javascript" type="text/javascript" src="/js/common/json.common.js"></script>
<script language="javascript" type="text/javascript" src="/js/common/json2.js"></script>
<script type="text/javascript" >
	/** ********************************************************
	 * jQeury 초기화
	 ******************************************************** */
	$(document).ready(function()
	{
	    $('#update').click(function() {
	    	doUpdate();
	    });
	    $('#close').click(function() {
	        top.close();
	    });
	}); // end of ready 
	
	/** ********************************************************
	 * 저장 처리 함수
	 ******************************************************** */
	function doUpdate()
	{
		var form = document.popupForm;
	    var url  = '<c:url value="/board/updateCallCenterPopup.do"/>';
	
	    //입력값 체크
	    if ( !chkfield() ) {
	        return;
	    }
	    
	    //입력길이 체크
	    if ( !fnChkSpcCharByte(form.title, "제목", 400) ) {
	        return;
	    }
	    else if ( !fnChkSpcCharByte(form.email, "EMAIL", 150) ) {
	        return;
	    }
        else if ( !fnChkSpcCharByte(form.content, "내용", 0) ) {
            return;
        }
	
	    if (!confirm("콜센터 상세내역을 수정하시겠습니까?")) 
	    {
	        return;
	    }
	    
	    form.action = url;
	    form.submit();
	}
	
	/** ********************************************************
	 * 입력창 null check 함수
	 ******************************************************** */
	function chkfield()
	{
		var form = document.popupForm;
		
	    if (form.title.value == "")
	    {
	        alert('<spring:message code="msg.common.error.required" arguments="제목"/>');
	        form.title.focus();
	        return false;
	    }
	    else if (form.cellNo.value == "" )
	    {
	        alert('<spring:message code="msg.common.error.required" arguments="전화번호(HP)"/>');
	        form.cellNo.focus();
	        return false;
	    }
	    else if (form.email.value == "")
	    {
	        alert('<spring:message code="msg.common.error.required" arguments="EMAIL"/>');
	        form.email.focus();
	        return false;
	    }
	    else if (form.content.value == "")
	    {
	        alert('<spring:message code="msg.common.error.required" arguments="내용"/>');
	        form.content.focus();
	        return false;
	    }
	    
	    return true;
	}
	
    /** ********************************************************
     * 입력항목의 최대값을 체크한다.
     ******************************************************** */
	function fnChkSpcCharByte(chkValue, chkTitle, chklength)
	{
        var str     = chkValue.value;
        var len     = 0;
        var expAll  = /[~!@\#$%^&*\()<>{}\[\]\-=+_'\"]/gi;
        var expPart = /[<>]/gi;
        
        if (str == null || (event.keyCode == 13))
        {
            return false;
        }
        
        if ( chkTitle == "제목" )
        {
            if (str.search(expAll) != -1)
            {
                alert(chkTitle + "항목에는 특수문자를 사용할수 없습니다!");
                chkValue.focus();
                return false;
            }
        }
        else if ( chkTitle == "EMAIL" )
        {
            if (str.search(expPart) != -1)
            {
                alert(chkTitle + "항목에는 특수문자 '<'와 '>'를 사용할수 없습니다!");
                chkValue.focus();
                return false;
            }
        }
        else if ( chkTitle == "내용" )
        {
            if (str.search(expPart) != -1)
            {
                alert(chkTitle + "내용항목에는 특수문자 '<'와 '>'를 사용할수 없습니다!");
                chkValue.focus();
                return false;
            }
        }
        
        if ( chkTitle != "내용" )
        {
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
            
            if (len > chklength)
            {
                alert(chkTitle + " 항목은 " + chklength + "Byte (한글 3Byte, 영숫자 1Byte) 까지만\n" + "입력가능합니다.\n\n" + "[ 현재 : " + len + "Byte ]");
                return false;
            }
        }
	    
	    return true;
	}
    
    /**********************************************************
     * 특수문자 입력 방지 ex) onKeyPress="keyCode(event)"
     ******************************************************** */
    function keyCode(e, chkTitle)
    {
    	var code = (window.event) ? event.keyCode : e.which; // IE : FF - Chrome both
    	
        if ( chkTitle == "제목" )
        {
            if (code >  32 && code <  48) keyResult(e, chkTitle);
            if (code >  57 && code <  65) keyResult(e, chkTitle);
            if (code >  90 && code <  97) keyResult(e, chkTitle);
            if (code > 122 && code < 127) keyResult(e, chkTitle);
        }
        else if ( chkTitle == "내용" )
        {
            if (code == 60 || code == 62) keyResult(e, chkTitle);
        }
    }
    function keyResult(e, chkTitle)
    {
        if ( chkTitle == "제목" )
        {
            alert(chkTitle + "항목은 특수문자를 사용할수 없습니다!");
        }
        else if ( chkTitle == "내용" )
        {
            alert(chkTitle + "항목은 특수문자 '<'와 '>'를 사용할수 없습니다!");
        }
    	
    	if (navigator.appName != "Netscape") {
            event.returnValue = false;  //IE - Chrome both
    	}
    	else {
    		e.preventDefault(); //FF - Chrome both
    	}
    }
    
    /**********************************************************
     * 숫자만 입력 가능
     ******************************************************** */
    function onlyNumber()
    {
        if ((event.keyCode<48) || (event.keyCode>57))
        {
            event.returnValue = false;
        }
    }
    
	$(document).ready(function(){
		
		//*------------------------------------------------------------
		//* 근거리, 명절 콤보박스 변경
		//*------------------------------------------------------------
		$("#typeOne").change(function () {
			//alert("근거리, 명절 콤보변경");
			var _type = $("#typeOne").val();			
			_generateCommonCodeOptionTagVer2(_type, "#typeBig", "문의유형(대)");			
		});
		
		//*------------------------------------------------------------
		//* 문의유형(대) 콤보박스 변경
		//*------------------------------------------------------------
		$("#typeBig").change(function () {
			//alert("문의유형(대) 콤보변경");
			
			var _majorCD = $("#typeOne").val();
			var _minorCD = $("#typeBig").val();
			
			var retCD = _getCommonCodeColumn(_majorCD, _minorCD, "LET_1_REF");
			if(retCD != "error")
				_generateCommonCodeOptionTagVer2(retCD, "#typeSmall", "문의유형(소)");
			else
				alert("change error");
			
		});
		
	}); // end of ready()
	
	function init() {
		//_generateCommonCodeOptionTagVer2("QA001", "#typeBig", "문의유형(대)");
		//_generateCommonCodeOptionTagVer2("QA001", "#typeBigDown", "문의유형(대)");
	}	
	

	/* urlPrefix 문제 때문에... 안으로 복사해서 써야 한다..
	 * 생성일 : 2011-10.04
	 * 
	 * 사용예)
	 * _generateCommonCodeOptionTagVer2("QA001", "#typeBig", "전체01", MethodA);
	 * 
	 * function MethodA() {
	 * 	// do something..
	 * }
	 * 
	 *  commonMajorCode : MAJOR_CD
	 *  toSelectTagID : 대상 select tag ID를 넣으면 된다  
	 *                   <select id="typeBig"> #typeBig가 된다
	 *  firstOptionMessage : 콤보박스 첫 번째를 임의로 집어넣을 데이타..
	 *                       안넣으려면 "none" or null or "null"을 넣어주면 된다
	 *  selectViewCode     : 최초 선택된 값이 필요시 코드값 입력.
	 *  finallyMethod	   : 함수 호출 후 맨 마지막에 콜백할 함수(optional)
	 */
	function _generateCommonCodeOptionTagVer2(commonMajorCode, toSelectTagID, firstOptionMessage, finallyMethod) {
		
		$.post(
				"<c:url value='/CommonCodeHelperController/generateOptionTag.do'/>",
				{"selectedCode": commonMajorCode
				},
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
				},		
				"text"
			);
	}	
	
	/*
	 * 사용예)
	 * var retCD = getCommonCodeColumn("QA001", "200", "LET_1_REF");
	 * generateCommonCodeOptionTag(retCD, "#typeSmall", "문의유형(소)");
	 * 
	 * 설명)MAJOR_CD 가 "QA001" 이고 MINOR_CD 가 "200"인 결과행에서 "LET_1_REF" 컬럼 값을 가져온다
	 * 
	 * majorCD : MAJOR_CD
	 * minorCD : MINOR_CD
	 * columnNM : 얻고자 하는 컬럼 이름, 예) "LET_1_REF"
	 * 
	 */
	 
	function _getCommonCodeColumn(majorCD, minorCD, columnNM) {
		var retVal = "dummy";
			$.ajax({ 
				type : "POST",
				async : false,
				url : "<c:url value='/CommonCodeHelperController/getCommonCodeColumn.do'/>",
				dataType : "json",
				timeout : 5000,
				cache : true,
				data : {
					"majorCD":majorCD,
					"minorCD":minorCD,
					"columnNM":columnNM
				},
			      success: function(data){ 
			    	  retVal = data.RETURN_VALUE;
			      } 
			   } 
		);
		if(retVal == "dummy")
			return "error";
		return retVal;
	}
	
	
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
	
    function doOrderId()
    {
        var targetUrl = '<c:url value="/common/selectOrderIdList.do"/>?vendorId=' + document.popupForm.vendorId.value; //01:상품
        Common.centerPopupWindow(targetUrl, 'epcOrderIdPopup', {width : 700, height : 550, scrollBars : 'YES'});    	
    }

    //카테고리 검색창으로 부터 받은 카테고리 정보 입력
    function setOrderId(orderId) // 이 펑션은 카테고리 검색창에서 호출하는 펑션임
    {
        document.popupForm.orderId.value = orderId;
    }
    
    function doCancel()
    {
        document.popupForm.orderId.value = '';    	
    }	    
</script>
</head>

<body>
<form name="popupForm" method="post">
<input type="hidden" name="counselSeq" id="counselSeq" value="${detail.counselSeq}"/>
  <div id="popup">
    <!--  @title  -->
     <div id="p_title1">
		<h1>배송문의수정</h1>
        <span class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
     </div>
     <!--  @title  //-->
     <!--  @process  -->
     <div id="process1">
		<ul>
			<li>홈</li>
            <li>배송관리</li>
            <li class="last">콜센터배송문의등록</li>
		</ul>
     </div>
	 
	 <!-- list -->
	 <div >
		<div class="popup_contents">
		<!--  @작성양식 2 -->
		<div class="bbs_search3">
			<ul class="tit">
				<li class="tit">배송문의수정</li>
                <li class="btn">
<c:if test="${status eq '1'}" >
                    <a href="#" class="btn" id="update"><span><spring:message code="button.common.update"/></span></a>
</c:if>
                    <a href="#" class="btn" id="close" ><span><spring:message code="button.common.close" /></span></a>
                </li>
			</ul>
			<table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
				<colgroup>
					<col style="width:17%" />
					<col style="width:33%" />
					<col style="width:17%" />
					<col style="width:33%" />
				</colgroup>
				<tr>
					<th class="fst"><span class="star">*</span> 제목</th>
					<td colspan="3">
<c:if test="${status eq '1'}" >
					   <input type="text" name="title" id="title" size="77" value="${detail.title}" onKeyPress="keyCode(event,'제목')" onChange="fnChkSpcCharByte(this,'제목',400)"/>
</c:if>
<c:if test="${status != '1'}" >
					   <input type="text" name="title" id="title" size="77" value="${detail.title}" readOnly/>
</c:if>
					</td>
				</tr>
				<tr>
					<th class="fst">협력업체코드</th>
					<td>
<c:if test="${status eq '1'}" >
                        <select name="vendorId" id="vendorId" class="select">
                            <c:forEach items="${epcLoginVO.vendorId}" var="venArr" begin="0">
                                <option value="${venArr}" <c:if test="${venArr eq detail.regId}">selected</c:if>>${venArr}</option>
                            </c:forEach>                        
                        </select>
</c:if>
<c:if test="${status != '1'}" >
                        <select name="vendorId" id="vendorId" class="select" disabled>
                            <option value="${detail.regId}">${detail.regId}</option>
                        </select>
</c:if>
					</td>
					<th class="fst">SMS수신여부</th>
					<td>
<c:if test="${status eq '1'}" >
					   <input type="radio" name="smsDspYn" value="Y" <c:if test="${detail.smsDspYn eq 'Y'}">checked</c:if>>Y</input>
					   <input type="radio" name="smsDspYn" value="N" <c:if test="${detail.smsDspYn eq 'N'}">checked</c:if>>N</input>
</c:if>
<c:if test="${status != '1'}" >
					   <input type="radio" name="smsDspYn" value="Y" <c:if test="${detail.smsDspYn eq 'Y'}">checked</c:if> disabled>Y</input>
					   <input type="radio" name="smsDspYn" value="N" <c:if test="${detail.smsDspYn eq 'N'}">checked</c:if> disabled>N</input>
</c:if>
					</td>
				</tr>
				<tr>
					<th class="fst"><span class="star">*</span> 전화번호(HP)</th>
					<td>
<c:if test="${status eq '1'}" >
					   <input type="text" name="cellNo" id="cellNo" size="11" maxlength="11" value="${detail.cellNo}" style="ime-mode:disabled" onKeyPress="onlyNumber()"/>
</c:if>
<c:if test="${status != '1'}" >
					   <input type="text" name="cellNo" id="cellNo" size="11" value="${detail.cellNo}" readOnly/>
</c:if>
					</td>
					<th class="fst"><span class="star">*</span> EMAIL</th>
					<td>
<c:if test="${status eq '1'}" >
					   <input type="text" name="email" id="email" size="29" value="${detail.email}" style="ime-mode:disabled" onChange="fnChkSpcCharByte(this,'EMAIL',150)"/>
</c:if>
<c:if test="${status != '1'}" >
					   <input type="text" name="email" id="email" size="29" value="${detail.email}" readOnly/>
</c:if>
					</td>
				</tr>
				
				<tr>
					<th class="fst" ><span class="star">*</span> 주문번호</th>
					<td colspan="3">
						<input type="text" name="orderId" id="orderId" size="29" value="${detail.orderId}" readonly="readonly" onChange="fnChkSpcCharByte(this,'EMAIL',150)" maxlength="12"/>
	                    <a href="javascript:doOrderId();"  ><img src="${lfn:getString('system.cdn.static.path')}/images/epc/btn/bt_search.gif" alt="" class="middle" /></a>
	                    <a href="javascript:doCancel();"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/btn/bt_eraser.gif" alt="" class="middle" /></a>											
					</td>
				</tr>
								
				<tr>
				    <th><span class="star">*</span>고객문의구분</th>
					<td colspan="3"> 
						<select name="typeOne" id="typeOne" class="select" style="width:80px">
	                           <option value="QA001" >근거리</option>
	                           <option value="QAH01" >명절</option>
	                    </select>
	                    
						<select name="typeBig" id="typeBig" class="select" style="width:120px">
							<option value="">문의유형(대)</option>
				<c:forEach items="${typeBigList}" var="codeList">
							<option value="${codeList.MINOR_CD}" <c:if test="${codeList.MINOR_CD eq detail.typeBig}">selected="selected"</c:if>>${codeList.CD_NM}</option>						
				</c:forEach>
						 </select>
						 
						 <select name="typeSmall" id="typeSmall" class="select" style="width:160px">
							<option value="">문의유형(소)</option>
				<c:forEach items="${typeSmallList}" var="codeList">
							<option value="${codeList.MINOR_CD}" <c:if test="${codeList.MINOR_CD eq detail.typeSmall}">selected="selected"</c:if>>${codeList.CD_NM}</option>
				</c:forEach>

						 </select>
					</td>				
				</tr>									
									
	            <tr>
	                <th class="fst"><span class="star">*</span> 내용</th>
	                <td colspan="3">
<c:if test="${status eq '1'}" >
	                   <textarea name="content" id="content" rows="15" cols="75" onKeyPress="keyCode(event,'내용')" onChange="fnChkSpcCharByte(this,'내용',0)">${detail.content}</textarea>
</c:if>
<c:if test="${status != '1'}" >
	                   <textarea name="content" id="content" rows="15" cols="75" readOnly>${detail.content}</textarea>
</c:if>
	                </td>
	            </tr>
			</table>
			</div>
		</div>
	</div>
</form>
</body>
</html>