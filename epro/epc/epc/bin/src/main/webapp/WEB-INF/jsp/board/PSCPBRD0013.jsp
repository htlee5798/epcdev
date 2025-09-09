<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />


<script type="text/javascript">

function insert() {
	var form = document.popupForm;
	var url = '<c:url value="/board/insertReCcQnaDetailPopup.do"/>';
	
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
	
	if(!confirm("답변을 등록 하시겠습니까?")) {
		return;
	}
   
    
	form.action = url;

	loadingMask();
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
    alert("검색항목에는 특수문자를 사용할수 없습니다!");

    if (navigator.appName != "Netscape") {
        event.returnValue = false;  //IE - Chrome both
    }
    else {
        e.preventDefault(); //FF - Chrome both
    }
}
</script>

</head>

<body>

<form name="popupForm" method="post" enctype="multipart/form-data">
<input type="hidden" name="boardSeq" value="${detail.boardSeq}" />
<input type="hidden" name="regId" value="${detail.regId}" />
<input type="hidden"  name="streFileNm" />
<input type="hidden"  name="orignlFileNm" />
<input type="hidden"  name="fileStreCours" />
<input type="hidden" name="fileSn" value="" />
<input type="hidden" name="commentSeq" value="" />
  <div id="popup">
    <!--  @title  -->
     <div id="p_title1">
		<h1>고객센터문의 답변</h1>
        <span class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
     </div>
     <!--  @title  //-->
     <!--  @process  -->
     <div id="process1">
		<ul>
			<li>홈</li>
			<li>고객센터문의관리</li>
			<li class="last">고객센터문의 답변</li>
		</ul>
     </div>
	 
	 <!-- list -->
	<div class="popup_contents">
	<!--  @작성양식 2 -->
		<div class="bbs_search3">
		<ul class="tit">
			<li class="tit">고객센터문의 답변</li>
			<li class="btn">
				<a href="#" class="btn" onclick="insert();"><span><spring:message code="button.common.create"/></span></a>
				<a href="#" class="btn" onclick="self.close();"><span><spring:message code="button.common.close"/></span></a>
			</li>
		</ul>
		<table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
			<colgroup>
				<col style="width:15%" />
				<col style="width:35%" />
				<col style="width:15%" />
				<col style="width:35%" />
			</colgroup>
			<tr>
				<th>문의유형</th>
				<td colspan="3">${detail.clmLgrpNm}</td>
			</tr>
			<tr>
				<th class="fst">상품명</th>
				<td>${detail.let4Ref}</td>
				<th>주문번호</th>
				<td>${detail.orderId}</td>
			</tr>
			<c:if test="${detail.acceptLocaCd eq '7'}">
			<tr>
				<th class="fst">협력업체</th>
				<td>${detail.let1Ref}</td>
				<th>담당자</th>
				<td>${detail.memberNm}</td>
			</tr>
			<tr>
				<th class="fst">연락처</th>
				<td>${detail.cellNo}</td>
				<th>답변 E-Mail</th>
				<td>${detail.email}</td>
			</tr>	
			</c:if>
			
			<c:if test="${detail.acceptLocaCd != '7'}">
			<tr>
				<th class="fst">협력업체</th>
				<td colspan="3">${detail.let1Ref}</td>
			</tr>
			</c:if>				
			<tr>
				<th>작성자</th>
				<td>
					${detail.regId}
					<c:if test="${fn:trim(detail.regNm) != ''}">
					(${detail.regNm})
					</c:if>
				</td>
				<th>담당자</th>
				<td>
					${detail.acceptId} 
					<c:if test="${fn:trim(detail.acceptNm) != ''}">
					(${detail.acceptNm})
					</c:if>
				</td>
			</tr>
			<tr>
				<th class="fst"><span class="star">*</span> 제목<!-- <img src="/lim/static_root/images/boardimg/201110/201110061138808_P3FAPBN7.jpg"> --></th>
				<td colspan="3">
					<input type="text" name="title" id="title" size="100" onKeyPress="keyCode(event)" onChange="fnChkSpcCharByte()"/>
				</td>
			</tr>
			<!-- tr> 우선 답변글은 첨부불가
				<th class="fst">첨부파일</th>
				<td colspan="3">
					<input type="file" name="appendFile" size="85" />
				</td>
			</tr> -->
			<tr>
				<th class="fst"><span class="star">*</span> 내용</th>
				<td colspan="3">
					<textarea name="content" id="content" cols="100" rows="15" wrap="hard" style="ime-mode:active"></textarea>
				</td>
			</tr>
		</table>
		</div>
	</div>
	</br>
  </div>
</form>

</body>
</html>