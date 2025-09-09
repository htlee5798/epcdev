<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jstl/core_rt"       %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn"    uri="/WEB-INF/tlds/function.tld"             %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt"       %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"    %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />

<script type="text/javascript">
    /**********************************************************
     * 메모 저장  함수
     *********************************************************/
    function memoSave()
    {
        var form = document.popupForm;
        var url = '<c:url value="/board/updateMemo.do"/>';
        
        if ( form.memoTC.value == form.memoTC_mod.value ) {
        	alert("변경된 내역이 없습니다.");
        	return;
        }
        
        if (!fnChkSpcCharByte()) {
        	return;
        }

        if (!confirm("메모를 저장하시겠습니까?")) {
            return;
        }

        form.action = url;
        form.submit();
    }
    
    /** ********************************************************
     * 입력항목의 최대값을 체크한다.
     ******************************************************** */
    function fnChkSpcCharByte()
    {
        var str = document.popupForm.memoTC.value;
        var len = 0;
        var exp = /[<>]/gi;
        
        if (str == null || (event.keyCode == 13))
        {
            return false;
        }
        
        if (str.search(exp) != -1)
        {
            alert("메모항목에는 특수문자 '<'와 '>'를 사용할수 없습니다!");
            document.popupForm.memoTC.focus();
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
        
        if (len > 4000)
        {
            alert("메모(업체)는 4000Byte (한글 3Byte, 영숫자 1Byte) 까지만\n" + "입력가능합니다.\n\n" + "[ 현재 : " + len + "Byte ]");
            document.popupForm.memoTC.focus();
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

        if (code == 60 || code == 62) keyResult(e);
    }
    function keyResult(e)
    {
        alert("메모항목에는 특수문자 '<'와 '>'를 사용할수 없습니다!");

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

<form name="popupForm" method="post">
<input type="hidden" name="counselSeq" value="${counselDetail.counselSeq}" />
  <div id="popup" style="width:567px;">
    <!--  @title  -->
     <div id="p_title1">
        <h1>1:1문의처리내역</h1>
        <span class="logo"><img src=" ${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
     </div>
     <!--  @process  -->
     <div id="process1">
        <ul>
            <li>홈</li>
            <li>고객의 소리</li>
            <li class="last">1:1문의관리</li>
        </ul>
     </div>
     <div class="wrap_con">
        <div class="bbs_list">
            <ul class="tit">
                <li class="tit">문의내용</li>
                <li class="btn">
<c:if test="${counselDetail.boardPrgsStsCd eq '2'}" >
                    <a href="#" class="btn" onclick="memoSave();"  ><span><spring:message code="button.common.save" /></span></a>
</c:if>
                    <a href="#" class="btn" onclick="self.close();"><span><spring:message code="button.common.close"/></span></a>
                </li>
            </ul>
            <!----------------검색 결과 테이블--------->
            <table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
                <colgroup>
                    <col width="15%"/>
                    <col width="35%"/>
                    <col width="15%"/>
                    <col width="35%"/>
                </colgroup>
                <tr>
                    <tr class="fst">
	                    <th>접수번호</th>
	                    <td>${counselDetail.counselSeq}</td>
	                    <th>접수자</th>
	                    <td>${counselDetail.ansMngrId}(${counselDetail.acceptId})</td>
                    </tr>

                    <tr>
	                    <th>접수일자</th>
	                    <td>${counselDetail.regDate}</td>
	                    <th>회원ID</th>
	                    <td>${counselDetail.acceptId}</td>
                    </tr>

                    <tr>
	                    <th>고객문의</th>
	                    <td>${counselDetail.clmLgrpCd}</td>
	                    <th>전화번호</th>
	                    <td>${counselDetail.cellNo}</td>
                    </tr>

                    <tr>
	                    <th>접수위치</th>
	                    <td>${counselDetail.acceptLocaCd}</td>
	                    <th>E-MAIL</th>
	                    <td>${counselDetail.email}</td>
                    </tr>

                    <tr>
	                    <th>주문번호</th>
	                    <td>${counselDetail.orderId}</td>
	                    <th>상품번호</th>
	                    <td>${counselDetail.prodCd}</td>
                    </tr>

                    <tr>
	                    <th>점포명</th>
	                    <td>${counselDetail.strNm}</td>
	                    <th>청취여부</th>
	                    <td>${counselDetail.delYn}</td>
                    </tr>

                    <tr>
	                    <th>문의제목</th>
	                    <td colspan="3" style="text-align:left;padding-left:5px;">${counselDetail.title}</td>
                    </tr>

                    <tr>
	                    <th>문의내용</th>
	                    <td colspan="3">
	                    <textarea name="content" style="width:465px; height:85px;" readonly>${counselDetail.content}</textarea>
	                    </td>
                    </tr>

                    <tr>
	                    <th>답변</th>
	                    <td colspan="3">
	                    <textarea name="content" style="width:465px; height:85px;" readonly>${counselDetail.answer}</textarea>
	                    </td>
                    </tr>

                    <tr>
	                    <th>메모(상담원)</th>
	                    <td colspan="3">
	                    <textarea name="memoTCT" style="width:465px; height:85px;" readonly>${counselDetail.memoTCT}</textarea>
	                    </td>
                    </tr>

                    <tr>
	                    <th>메모(업체)</th>
	                    <td colspan="3">
<c:if test="${counselDetail.boardPrgsStsCd eq '2'}" >
	                    <textarea name="memoTC" style="width:465px; height:85px;" onKeyPress="keyCode(event)" onChange="fnChkSpcCharByte()">${counselDetail.memoTC}</textarea>
</c:if>
<c:if test="${counselDetail.boardPrgsStsCd != '2'}" >
	                    <textarea name="memoTC" style="width:465px; height:85px;" readOnly>${counselDetail.memoTC}</textarea>
</c:if>
	                    <input type="hidden" name="memoTC_mod" value="${counselDetail.memoTC}" />
	                    </td>
                    </tr>
                </tr>
            </table>
        </div>
    </div>
  </div>
</form>

</body>
</html>