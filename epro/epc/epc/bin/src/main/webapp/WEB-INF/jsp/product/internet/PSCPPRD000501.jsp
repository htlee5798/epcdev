<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jstl/core_rt"      %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"   %>
<%@ taglib prefix="epcutl" uri="/WEB-INF/tlds/epcutl.tld"              %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt"      %>
<%@page import="com.lottemart.epc.common.util.SecureUtil"%>

<%
    String prodCd   = SecureUtil.stripXSS(request.getParameter("prodCd"  ));
    String vendorId = SecureUtil.stripXSS(request.getParameter("vendorId"));
    String tabNo    = "4";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Cache-Control"   content="No-Cache"     >
<meta http-equiv="Pragma"          content="No-Cache"     >

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHeadPopup.do" />

<script language="javascript" type="text/javascript">
    $(document).ready(function(){
        //input enter 막기
        $("*").keypress(function(e){
            if(e.keyCode==13) return false;
        });
        $('#create').click(function() {
        	popupInsert();
        });
        $('#update').click(function() {
        	popupUpdate();
        });
    });

    /** ********************************************************
     * 조회 처리 함수
     ******************************************************** */

    //등록 팝업
    function popupInsert()
    {
        var targetUrl = '<c:url value="/product/insertProduectDescriptionForm.do"/>?mode=insert&prodCd=<%=prodCd%>&vendorId=<%=vendorId%>';
        Common.centerPopupWindow(targetUrl, 'prdDescription', {width : 1000, height : 660});
    }

    //수정 팝업
    function popupUpdate()
    {
        var targetUrl = '<c:url value="/product/insertProduectDescriptionForm.do"/>?mode=update&prodCd=<%=prodCd%>&vendorId=<%=vendorId%>';
        Common.centerPopupWindow(targetUrl, 'prdDescription', {width : 1000, height : 660});
    }

</script>

</head>

<body>

<form name="dataForm" id="dataForm">
    <input type="hidden" id="prodCd"    name="prodCd"    value="<%=prodCd%>"             />
    <input type="hidden" id="mdSrcmkCd" name="mdSrcmkCd" value="${resultDescr.mdSrcmkCd}"/>
<div id="content_wrap" style="width:990px; height:200px;">

    <div id="wrap_menu" style="width:990px">
        <!--    @ 검색조건  -->

        <!-- 상품 상세보기 하단 탭 -->
        <c:set var="tabNo" value="<%=tabNo%>" />
        <c:import url="/common/productDetailTab.do" charEncoding="euc-kr" >
			<c:param name="tabNo" value="${tabNo}" />
			<c:param name="prodCd" value="<%=prodCd%>" />
		</c:import>

        <div class="wrap_con">
            <!-- list -->
            <div class="bbs_list">
                <ul class="tit">
                    <li class="tit">상세이미지정보
                    </li>

                    <li class="btn">
                        <c:if test="${resultBeDescr == '1'}" ><a href="#" class="btn" id="update"><span><spring:message code="button.product.update"/></span></a></c:if>
                        <c:if test="${resultBeDescr == '2'}" ><a href="#" class="btn" id="create"><span><spring:message code="button.product.create"/></span></a></c:if>
                    </li>
                </ul>

                <table cellpadding="0" cellspacing="0" border="0" width="100%">
                    <tr>
                        <td>
                        	<c:out value="${resultDescr.addDesc}" escapeXml="false"/>
                        </td>
                    </tr>
                </table>
            </div>
        </div>

</div>
</form>

<form name="form1" id="form1">
    <input type="hidden" id="prodCd"   name="prodCd"   value="<%=prodCd%>"  />
    <input type="hidden" id="vendorId" name="vendorId" value="<%=vendorId%>"/>
</form>

</body>
</html>