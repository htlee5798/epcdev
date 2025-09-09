<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jstl/core_rt"      %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="lfn"    uri="/WEB-INF/tlds/function.tld"            %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"   %>
<%@ taglib prefix="epcutl" uri="/WEB-INF/tlds/epcutl.tld"              %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt"      %>
<%@page import="com.lottemart.epc.common.util.SecureUtil"%>

<%
    String mode     = SecureUtil.stripXSS(request.getParameter("mode"    ));
    String prodCd   = SecureUtil.stripXSS(request.getParameter("prodCd"  ));
    String vendorId = SecureUtil.stripXSS(request.getParameter("vendorId"));
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Cache-Control"   content="No-Cache"     >
<meta http-equiv="Pragma"          content="No-Cache"     >

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHeadPopup.do" />
<script type="text/javascript" src="../namoCross/js/namo_scripteditor.js"></script>
<script>
    // 상세이미지정보 입력
    function doInsert()
    {
        if ("<%=mode%>" == "update")
        {
            var ment = "수정";
        }
        else
        {
            var ment = "입력";
        }
	
        //20180715 - 상품수정요청 시 승인요청 또는 반려 일 경우 알림 메시지 제공 
        var aprvCd = '<c:out value="${prdMdAprInfo.aprvCd}" />';

    	if(aprvCd == "001") {
    		alert('현재 수정한 상품은 상품정보수정 승인요청 중인 상품입니다.');
    	} else if(aprvCd == "002") {
    		alert('현재 수정한 상품은 상품정보수정요청 중 반려된 상품으로 수정 후 아래의 경로에서 <재요청> 이 필요합니다.\n\n([SCM]상품정보관리→상품정보수정요청→상세이미지수정리스트→목록 중 [보기] 항목 선택→<재요청> 신청)');
    	}
    	
        $('#addDesc').val(CrossEditor.GetValue());

        if ( confirm("상세이미지를 " + ment + " 하시겠습니까?") )
        {
            $('#dataForm').attr({action:'<c:url value="/product/saveProductDescription.do"/>',target:'_if_save'}).submit();
        }
    }

    // 현재창 닫기
    function doClose()
    {
        top.close();
    }

 	// ImageSplitter Popup에서 데이터가 전해진다.
    function addSplitImage(activeSquareMimeValue)
    {
		var wec = document.Wec;
		var bodyTag = wec.BodyValue;
		wec.MIMEValue = activeSquareMimeValue;
		wec.BodyValue = bodyTag + decodeURI(wec.BodyValue); // base64에서 한글 입력하면 깨진다.
    }

    // 팝업으로 ImageSplitter를 띄운다.
    function doImageSplitterView()
    {
		window.open("/edit/splitter/ImageSplitter.html", "ImageSplitter", "width="+screen.width+", height="+screen.height+", toolbar=no, menubar=no, resizeable=true, fullscreen=true");
    }

    $(document).ready(function(){
        //input enter 막기
        $("*").keypress(function(e){
            if(e.keyCode==13) return false;
        });

        var result = '${result}';

        if (result == 'success')
        {
            alert('${message}');
            top.opener.changeTab(4);
            top.close();
        }
        $('#save').click(function() {
        	doInsert();
        });
        $('#close').click(function() {
        	doClose();
        });
    });
</script>

<%if ("update".equals(mode)) {%>
<!--
<SCRIPT language="JScript" FOR="Wec" EVENT="OnInitCompleted()">
    var wec = document.Wec;
    wec.Value = $('#addDesc').val();
</SCRIPT>
 -->
<%}%>

</head>

<body>
<form name="dataForm" id="dataForm" method="post" enctype="multipart/form-data" action="<c:url value="/product/saveProductDescription.do"/>">
<input type="hidden" name="mode" value="<%=mode%>">
<input type="hidden" name="prodCd" value="<%=prodCd%>">
<input type="hidden" name="mdSrcmkCd" value="${resultDescr.mdSrcmkCd}">
<input type="hidden" name="vendorId" value="<%=vendorId%>">
<input type="hidden" name="seq" value="${prdMdAprInfo.seq}">
<!-- SEQ 001 만 관리됨 -->
<div id="popup">
    <!--  @title  -->
     <div id="p_title1">
        <h1>상세이미지</h1>
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
                <li class="tit">상세이미지 입력</li>
                <li class="btn">
                	<!--<a href="javascript:doImageSplitterView();" class="btn" ><span>이미지추가</span></a> -->
                    <a href="#" class="btn" id="save" ><span><spring:message code="button.common.save" /></span></a>
                    <a href="#" class="btn" id="close"><span><spring:message code="button.common.close"/></span></a>
                </li>
            </ul>
            <table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
            <colgroup>
                <col width="10%">
                <col width="90%">
            </colgroup>

            <tr>
                <th><span class="star"></span>상세이미지</th>
                    <td>
                    	<script type="text/javascript" language="javascript">
							var CrossEditor = new NamoSE('pe_agt');

							CrossEditor.params.Width 		= "100%";
							CrossEditor.params.UserLang 	= "auto";
							CrossEditor.params.FullScreen 	= false;
							CrossEditor.params.SetFocus 	= false; // 에디터 포커스 해제
							CrossEditor.params.ImageSavePath = "edi";
							CrossEditor.params.ActiveTab = 40;
							CrossEditor.params.AccessibilityOption = 1;
							CrossEditor.EditorStart();

							function OnInitCompleted(e){
								e.editorTarget.SetBodyStyle('text-align','center');
								e.editorTarget.SetBodyValue(document.getElementById("addDesc").value);
							}
						</script>
						<!--
                        <DIV id="divShowInstall" style="BORDER-RIGHT: 0px; BORDER-TOP: 0px; Z-INDEX: 0; BORDER-LEFT: 0px; BORDER-BOTTOM: 0px; POSITION: absolute">
                        <EMBED src="/edit/images/NamoBanner.swf" width=741 height=525 type=application/x-shockwave-flash></EMBED>
                        </Div>
                        <script language="javascript" src="/edit/NamoWec7.js"></script>
                         -->
                    </td>
            </tr>
            </table>
        </div>
    </div>
</div>
<input type="hidden" name="addDesc" id="addDesc" value="${resultDescr.addDesc}">
</form>

<iframe name="_if_save" src="/html/epc/blank.html" style="display:none;"></iframe>

</body>
</html>