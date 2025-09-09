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
	 * 저장 처리 함수
	 ******************************************************** */
	function insert()
	{
		var form = document.popupForm;
	    var url = '<c:url value="/board/insertSuggestionPopup.do"/>';
	
	    //입력값 체크
	    if( !chkfield(form.title, form.title.value, "제목") ) 
	    {
	        return;
	    }
	    
	  //입력값 체크
	    if( !chkfield(form.vendorId, form.vendorId.value, "업체") ) 
	    {
	        return;
	    }
	    
	    //특수문자 및 입력길이 체크
	    if( !fnChkSpcCharByte() ) 
	    {
	        return;
	    }
	
	    if (!confirm("건의사항을 등록하시겠습니까?")) 
	    {
	        return;
	    }
	    $('#contents').val(namoCross1.GetBodyValue());
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
            alert("제목에는 특수문자를 사용할수 없습니다!");
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
        alert("제목은 특수문자를 사용할수 없습니다!");

        if (navigator.appName != "Netscape") {
            event.returnValue = false;  //IE - Chrome both
        }
        else {
            e.preventDefault(); //FF - Chrome both
        }
    }
    
	function setVendorInto(strVendorId, strVendorNm, strCono) { // 이 펑션은 협력업체 검색창에서 호출하는 펑션임   
		$("#vendorId").val(strVendorId);
	}
</script>
<script language="JScript" FOR="Wec" EVENT="OnInitCompleted()">
	var form = document.popupForm;
	var wec = document.Wec;
// 	wec.Value = form.contents.value;
</script>
</head>

<body onKeyDown="javascript:if(event.keyCode == 13) event.returnValue=false;">
<form name="popupForm" method="post" enctype="multipart/form-data">
  <div id="popup">
    <!--  @title  -->
     <div id="p_title1">
		<h1>업체문의등록</h1>
        <span class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
     </div>
     <!--  @title  //-->
     <!--  @process  -->
     <div id="process1">
		<ul>
			<li>홈</li>
			<li>게시판관리</li>
			<li class="last">업체문의관리</li>
		</ul>
     </div>
	 
	 
	 <!-- list -->
	 <div >
		<div class="popup_contents">
		<!--  @작성양식 2 -->
		<div class="bbs_search3">
			<ul class="tit">
				<li class="tit">업체문의등록</li>
                <li class="btn">
                    <a href="#" class="btn" onclick="insert();"    ><span><spring:message code="button.common.create"/></span></a>
                    <a href="#" class="btn" onclick="self.close();"><span><spring:message code="button.common.close" /></span></a>
                </li>
			</ul>
			<table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
				<colgroup>
					<col style="width:15%"/>
					<col style="width:35%"/>
					<col style="width:15%"/>
					<col style="width:35%"/>
				</colgroup>
				<tr>
					<th class="fst"><span class="star">*</span> 제목</th>
					<td colspan="5">
					   <input type="text" name="title" id="title" size="100" onKeyPress="keyCode(event)" onChange="fnChkSpcCharByte()"/>
					</td>
				</tr>
				<tr>
					<th class="fst">공지사항</th>
					<td>
					   <input type="radio" name="noticeYn" value="Y">Y</input>
					   <input type="radio" name="noticeYn" value="N" checked>N</input>
					</td>
					<th class="fst">협력업체코드</th>
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
	            <tr>
	                <th class="fst">첨부파일</th>
	                <td colspan="3">
	                    <input type="file" name="appendFile" size="85"/>
	                </td>
	            </tr>
	            <tr>
	                <th class="fst">내용</th>
	                <td colspan="3">
	                    <input type="hidden" name="contents" id="contents" />
						<script type="text/javascript" language="javascript">
							var namoCross1 = new NamoSE('pe_agt');
							namoCross1.params.Width 		= "98%";
							namoCross1.params.Height 		= "300";
							namoCross1.params.UserLang 	= "auto";
							namoCross1.params.ImageSavePath = "edi";
							namoCross1.params.FullScreen = false;
							namoCross1.params.SetFocus 	= false; // 에디터 포커스 해제
							namoCross1.EditorStart();
						</script>
	                </td>
	            </tr>
			</table>
			</div>
		</div>
	</div>
</form>
</body>
</html>