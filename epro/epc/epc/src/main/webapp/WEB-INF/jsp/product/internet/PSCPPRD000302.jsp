<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="epcutl" uri="/WEB-INF/tlds/epcutl.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.lottemart.epc.common.util.SecureUtil"%>

<%
    String prodCd   = SecureUtil.stripXSS(request.getParameter("prodCd"));
    String vendorId = SecureUtil.stripXSS(request.getParameter("vendorId"));
    String mode     = SecureUtil.stripXSS(request.getParameter("mode"));
    String itemCd   = "";

    if (request.getParameter("itemCd") != null)
    {
        itemCd = SecureUtil.stripXSS(request.getParameter("itemCd"));
    }
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Cache-Control"   content="No-Cache"     >
<meta http-equiv="Pragma"          content="No-Cache"     >

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHeadPopup.do" />

<script>
    // 단품정보 입력
    function doInsert()
    {
        if (!$("#colorCd").val())
        {
            alert("색상을 선택하세요.");
            $("#colorCd").focus();
            return;
        }
        if (!$("#szCatCd").val())
        {
            alert("사이즈구분을 선택하세요.");
            $("#szCatCd").focus();
            return;
        }
        if (!$("#szCd").val())
        {
            alert("사이즈를 선택하세요.");
            $("#szCd").focus();
            return;
        }

        if ("<%=mode%>"=="update")
        {
            var ment="수정";
        }
        else
        {
            var ment="입력";
        }

        if ( confirm("상품 단품정보를 "+ ment +" 하시겠습니까?") )
        {
            callAjaxByForm('#dataForm', '<c:url value="/product/saveProductItem.do"/>', '#dataForm', 'POST');
        }
    }

    // 단품정보 입력 아작스 호출
    function callAjaxByForm(form, url, target, Type)
    {
        var formQueryString = $('*', '#dataForm').fieldSerialize();
        $.ajax({
            type: Type,
            url: url,
            data: formQueryString,
            success: function(json) {
                try
                {
                    if (jQuery.trim(json) == "") //처리성공
                    {
                        alert('<spring:message code="msg.common.success.insert"/>');
                        opener.goPage('1');
                        top.close();
                    }
                    else
                    {
                        alert(jQuery.trim(json));
                    }
                }
                catch (e) {
                	console.log(e);
                }
            },
            error: function(e) {
                alert("저장에 실패하였습니다");
            }
        });
    }

    // 현재창 닫기
    function doClose()
    {
        top.close();
    }

    // JQuery 초기화
    $(document).ready(function(){
        //input enter 막기
        $("*").keypress(function(e){
            if(e.keyCode==13) return false;
        });
<%
    if(mode != null) {
		if(mode.equals("update"))
    	{
%>
        changeSzCatCd();
<%
    	}
    }
%>
    });

    var firstSetSzCdCombo="";

    //콤보박스 변경
    function changeSzCatCd()
    {
        var formQueryString = $('*', '#dataForm').fieldSerialize();
        var targetUrl = "";

        if ( document.dataForm.szCatCd.value == "" )
        {
            comboReset( document.dataForm.szCd );
        }
        else
        {
            targetUrl = '<c:url value="/product/selectProductItemSizeList.do"/>';
            comboReset( document.dataForm.szCd );

            $.ajax({
                type: 'POST',
                url: targetUrl,
                data: formQueryString,
                success: function(data) {
                    try
                    {
                        if (data.comboNm == "szCd")
                        {
                            comboCall( document.dataForm.szCd, data.itemSzCdList, 'ALL');

<%
	if(mode != null) {
		if (mode.equals("update"))
    	{
%>
                            if (firstSetSzCdCombo == "") //최초 한번만 
                            {
                                $("#szCd").val("${resultItemInfo.szCd}");
                                firstSetSzCdCombo="1";
                            }
<%
    	}
	}
%>
                        }
                    }
                    catch (e) {
                    	console.log(e);
                    }
                },
                error: function(e) {
                    alert(e);
                }
            });
        }
    }
</script>
</head>

<body>

<form name="dataForm" id="dataForm">
<input type="hidden" name="prodCd"   value="<%=prodCd%>"  />
<input type="hidden" name="itemCd"   value="<%=itemCd%>"  />
<input type="hidden" name="vendorId" value="<%=vendorId%>"/>
<input type="hidden" name="mode"     value="<%=mode%>"    />

<div id="popup">
    <!--  @title  -->
     <div id="p_title1">
        <h1>단품정보</h1>
        <span class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
     </div>
     <!--  @title  //-->
     <!--  @process  -->
     <div id="process1">
        <ul>
            <li>홈</li>
            <li>상품관리</li>
            <li class="last">인터넷상품관리</li>           
        </ul>
     </div>
     <!--  @process  //-->
     <div class="popup_contents">
        <!--  @작성양식 2 -->
        <div class="bbs_search3">
            <ul class="tit">
                <li class="tit">단품정보 입력</li>
                <li class="btn">
                    <a href="javascript:doInsert();" class="btn" ><span><spring:message code="button.common.save" /></span></a>
                    <a href="javascript:doClose();"  class="btn" ><span><spring:message code="button.common.close"/></span></a>
                </li>
            </ul>
            <table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
            <colgroup>
                <col width="20%">
                <col width="80%">
            </colgroup>
            <tr>
                <th><span class="star">*</span> 색상</th>
                <td>
                    <select name="colorCd" id="colorCd">
                        <option value="">전체</option>
                            <c:forEach items="${colorList}" var="code" begin="0">
                                <option value="${code.colorCd }" ${resultItemInfo.colorCd eq code.colorCd ? 'selected' : ''}>${code.colorNm }</option>
                            </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <th><span class="star">*</span> 사이즈구분</th>
                <td>
                    <select name="szCatCd" id="szCatCd" onChange="javascript:changeSzCatCd();">
                        <option value="">전체</option>
                            <c:forEach items="${szCatList}" var="code" begin="0">
                                <option value="${code.szCatCd }" ${resultItemInfo.szCatCd eq code.szCatCd ? 'selected' : ''}>${code.szCatNm }</option>
                            </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <th><span class="star">*</span> 사이즈</th>
                <td>
                    <select name="szCd" id="szCd">
                        <option value="">전체</option>
                    </select>
                </td>
            </tr>
<!--                 <tr> -->
<!--                     <th><span class="star">*</span> 상품코드</th> -->
<%--                     <td><input type="text" maxlength="30" id="mdProdCd" name="mdProdCd"  value="${resultItemInfo.mdProdCd}" size="30"></td> --%>
<!--                 </tr> -->
<!--                 <tr> -->
<!--                     <th><span class="star">*</span> MD판매코드</th> -->
<%--                     <td><input type="text" maxlength="30" id="mdSrcmkCd" name="mdSrcmkCd"  value="${resultItemInfo.mdSrcmkCd}" size="30"></td> --%>
<!--                 </tr> -->
<!--                 <tr> -->
<!--                     <th><span class="star">*</span> 대표코드</th> -->
<%--                     <td><input type="text" maxlength="30" id="repCd" name="repCd"  value="${resultItemInfo.repCd}" size="30"></td> --%>
<!--                 </tr> -->
            </table>
        </div>
    </div>
</div>
</form>

</body>
</html>