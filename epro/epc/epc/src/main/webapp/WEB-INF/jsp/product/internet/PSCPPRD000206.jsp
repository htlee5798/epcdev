<%@page import="com.lottemart.epc.common.util.SecureUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jstl/core_rt"      %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="lfn"    uri="/WEB-INF/tlds/function.tld"            %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"   %>
<%@ taglib prefix="epcutl" uri="/WEB-INF/tlds/epcutl.tld"              %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt"      %>

<% 
	String prodCd = SecureUtil.stripXSS(request.getParameter("prodCd"));
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control"   content="No-Cache"     >
<meta http-equiv="Pragma"          content="No-Cache"     >

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHeadPopup.do" />

<script>
	// 상품정보 업데이트
	function doUpdate()
	{
		var form = document.dataForm;
	    //값 트림, 크기 채크(바이트)
		$('#PROD_NM').val($.trim($('#PROD_NM').val()));
	    if (!fnChkSpcCharByte(form.PROD_NM, "인터넷상품명", 300)) {
	    	$('#PROD_NM').focus();
	        return;
	    }
	   
	    //값 입력 유무 채크
	    if(Common.isEmpty($.trim($('#PROD_NM').val()))){
	        alert('<spring:message code="msg.common.error.required" arguments="인터넷상품명"/>');
	        $('#PROD_NM').focus();
	        return;
	    }
	    
	    var MAX_ORD_PSBT_QTY = parseInt(removeComma($('#MAX_ORD_PSBT_QTY').val()));
	    var MIN_ORD_PSBT_QTY = parseInt(removeComma($('#MIN_ORD_PSBT_QTY').val()));
	    
	    if(!(MIN_ORD_PSBT_QTY <= MAX_ORD_PSBT_QTY)){
	        alert("최대 주문 수량은 최소 주문 수량과 같거나 커야 합니다.!");
	        $('#MIN_ORD_PSBT_QTY').focus();
	        return;
	    }
	    
	    if( confirm("상품정보를 저장 하시겠습니까?") ){
	        callAjaxByForm('#dataForm', '<c:url value="/product/updateProductInfo.do"/>', '#dataForm', 'POST');
	    }
	}
	
	// 상품정보 수정 요청 (ajax)
	function callAjaxByForm(form, url, target, Type) {
	
		var formQueryString = $('*', '#dataForm').fieldSerialize();
		$.ajax({
			type: Type,
			url: url,
			data: formQueryString,
			success: function(json) {
				try {
					if(jQuery.trim(json) == ""){//처리성공
						alert('<spring:message code="msg.common.success.update"/>');
						self.location.reload();
					}else{
						alert(jQuery.trim(json));
					}
	
				} catch (e) {}
			},
			error: function(e) {
				alert(e);
			}
		});
		
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
    
    /**********************************************************
     * 단위가격표시
     ******************************************************** */
    function fnMoneyComma(nMoney, flag) 
    {
        var charRight = String(nMoney);
        var charSplit = '';
        var charMoney = '';
        
        for (index = charRight.length - 1; index >= 0; index--)
        {
           charSplit = charRight.charAt(index);
           charMoney = charSplit + charMoney;
           
           if (index % 3 == charRight.length % 3 && index != 0)
           {
        	   charMoney=',' + charMoney;
           }
        }
        
        if ( flag == 'out' ) {
            document.write(charMoney);
       	}
        else if ( flag == 'in' ) {
        	return charMoney;
        }
        else {
        	alert("flag error");
        }
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
        
        if (str.search(expPart) != -1)
        {
            alert(chkTitle + "항목에는 특수문자 '<'와 '>'를 사용할수 없습니다!");
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
        
        if (len > chklength)
        {
            alert(chkTitle + " 항목은 " + chklength + "Byte (한글 3Byte, 영숫자 1Byte) 까지만\n" + "입력가능합니다.\n\n" + "[ 현재 : " + len + "Byte ]");
            return false;
        }
        
        return true;
    }
    
    /**********************************************************
     * 특수문자 입력 방지 ex) onKeyPress="keyCode(event)"
     ******************************************************** */
    function keyCode(e, chkTitle)
    {
        var code = (window.event) ? event.keyCode : e.which; // IE : FF - Chrome both

        if (code == 60 || code == 62) keyResult(e, chkTitle);
    }
    function keyResult(e, chkTitle)
    {
        alert(chkTitle + "항목은 특수문자 '<'와 '>'를 사용할수 없습니다!");
        
        if (navigator.appName != "Netscape") {
            event.returnValue = false;  //IE - Chrome both
        }
        else {
            e.preventDefault(); //FF - Chrome both
        }
    }
    
    // 상품 열람
    function PopupInfo(prodCD)
    {
    	   var prodCd    = prodCD;
           var targetUrl = "http://www.lottemart.com/product/ProductDetail.do?ProductCD=" + prodCd + "&strCd=307&approvalGbn=N&previewYN=Y";
           Common.centerPopupWindow(targetUrl, 'epcProductDetailView', {width : 970, height : 650, scrollBars : 'YES'});
    }
    
    /**********************************************************
     * 입력한 숫자의 자릿수 체크 / 컴마 표시 및
     * 표시총량 / 상품표시기준 변경에 다른 단위가격표시 변경
     ******************************************************** */
    function doChange(chkValue, chkTitle, chklength, chkYn)
    {
    	if (chkValue.value == null || chkValue.value == "")
    	{
    		chkValue.value = 1;
            return;
        }
    	else if (chkValue.value == 1)
    	{
    		return;
    	}
    	
    	if (removeComma(chkValue.value).length > chklength)
    	{
    		alert(chkTitle + " 항목은 " + chklength + " 자리 까지만 입력가능합니다.");
    		fnFocus(chkTitle);
    		return;
    	}
    	
    	chkValue.value = addTxtComma( chkValue.value ); // 컴마 적용
    }
    
    function fnFocus(chkTitle)
    {
    	if ( chkTitle == "최소주문가능수량" ) {
    		$('#MIN_ORD_PSBT_QTY').val($('#MIN_ORD_PSBT_QTY_ORI').val());
    		$('#MIN_ORD_PSBT_QTY').focus();
    	}else if ( chkTitle == "최대주문가능수량" ) {
    		$('#MAX_ORD_PSBT_QTY').val($('#MAX_ORD_PSBT_QTY_ORI').val());
    		$('#MAX_ORD_PSBT_QTY').focus();
    	}
    }
</script>

<script language="javascript" type="text/javascript">
	$(document).ready(function(){
		//input enter 막기
        $("*").keypress(function(e){
            if(e.keyCode==13) return false;
        });
		
		//상품상세정보 로딩후 아래쪽 탭에 초기 탭페이지 호출
		//document.form1.vendorId.value = document.dataForm.VENDOR_ID.value;
		document.form1.target = "prdDetailFrame";
		document.form1.action = '<c:url value="/product/selectProductItemForm.do"/>';//초기 탭 페이지
		document.form1.submit();
		
        $('#update').click(function() {
        	doUpdate();
        });
        $('#close').click(function() {
        	window.close();
        });
	});
</script>
</head>

<body>

<form name="form1" id="form1">
	<input type="hidden" id="prodCd"   name="prodCd"   value="<%=prodCd%>"            />
	<input type="hidden" id="vendorId" name="vendorId" value="${resultMap.VENDOR_ID}" />
</form>

<form name="dataForm" id="dataForm">
	<input type="hidden" name="PROD_CD"                     id="PROD_CD"                     value="${resultMap.PROD_CD}"                />
	<input type="hidden" name="VENDOR_ID"                   id="VENDOR_ID"                   value="${resultMap.VENDOR_ID}"              />
	<input type="hidden" name="INSERT_STATUS"               id="INSERT_STATUS"               value=""                                    />
	<input type="hidden" name="APRV_YN"                     id="APRV_YN"                     value="${resultMap.APRV_YN}"                />
	<input type="hidden" name="FEDAY_MALL_PROD_DIVN_CD_ORI" id="FEDAY_MALL_PROD_DIVN_CD_ORI" value="${resultMap.FEDAY_MALL_PROD_DIVN_CD}"/>
	<input type="hidden" name="AU_USE_YN"                   id="AU_USE_YN"                   value="${resultMap.AU_USE_YN}"              />
	<input type="hidden" name="HOME_CD"                     id="HOME_CD"                     value="${resultMap.HOME_CD}"                />
	
	<input type="hidden" name="PROD_DIVN_CD"  id="PROD_DIVN_CD"  value="${resultMap.PROD_DIVN_CD}" /><!-- 상품구분코드 -->
	<input type="hidden" name="CURR_SELL_PRC" id="CURR_SELL_PRC" value="${resultMap.CURR_SELL_PRC}"/><!-- 판매가 -->
	<input type="hidden" name="DP_UNIT_NM"    id="DP_UNIT_NM"    value="${resultMap.DP_UNIT_NM}"   /><!-- 표시단위코드명 -->
    <%--<input type="hidden" name="DP_TOT_QTY"    id="DP_TOT_QTY"    value="${resultMap.DP_TOT_QTY}"   /> MD표시총량 --%>
	<input type="hidden" name="ECOM_YN"        id="ECOM_YN"    value="${resultMap.ECOM_YN}"   /><!-- 전상법여부 -->
	<input type="hidden" name="ECOM_YN_APPROVE"         id="ECOM_YN_APPROVE"    value="${resultMap.ECOM_YN_APPROVE}"   /><!-- 전상법여부 -->
	<input type="hidden" name="ONLINE_PROD_TYPE_CD"    id="ONLINE_PROD_TYPE_CD"    value="${resultMap.ONLINE_PROD_TYPE_CD}"   /><!-- 온라인상품유형 -->
	<input type="hidden" name="CMBN_MALL_SELL_PSBT_YN"    id="CMBN_MALL_SELL_PSBT_YN"    value="Y"   />
	<input type="hidden" name="DP_TOTAMT"    id="DP_TOTAMT"    value="0"   />
	<input type="hidden" name="DP_TOT_QTY"    id="DP_TOT_QTY"    value="0"   />
	<input type="hidden" name="DP_BASE_QTY"    id="DP_BASE_QTY"    value="0"   />
	<input type="hidden" name="FRSH_CONV_QTY"    id="FRSH_CONV_QTY"    value="0"   />
	
<div id="popup">
    <!--  @title  -->
    <div id="p_title1">
	    <h1>상품 information</h1>
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
				<li class="tit">상품 information</li>
				<li class="btn">
					<a href="#" class="btn" id="update"  ><span><spring:message code="button.common.update"/></span></a>
                    <a href="#" class="btn" id="close"   ><span><spring:message code="button.common.close" /></span></a>
				</li>
			</ul>
			<table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
				<colgroup>
					   <col width="10%">
	                   <col width="15%">
	                   <col width="10%">
	                   <col width="15%">
	                   <col width="10%">
	                   <col width="15%">
	                   <col width="10%">
	                   <col width="15%">
				</colgroup>
				<tr>
					<th>인터넷상품코드</th>
					<td>${resultMap.PROD_CD}</td>
					<th>판매코드</th>
					<td>${resultMap.MD_SRCMK_CD}</td>
					<th>상품코드</th>
					<td>${resultMap.MD_PROD_CD}</td>
					<th>상품구분</th>
					<td>${resultMap.ONLINE_PROD_TYPE_NM}</td>
				</tr>
				
				<tr>
					<th><span class="star">*</span><a href="javascript:PopupInfo('${resultMap.PROD_CD}');"> 인터넷상품명</a></th>
                    <td><input type="text" name="PROD_NM" id="PROD_NM" value="${resultMap.PROD_NM}" onKeyPress="keyCode(event,'인터넷상품명')" onChange="fnChkSpcCharByte(this,'인터넷상품명',300)"></td>
					<th>MD상품명</th>
					<td>${resultMap.MD_PROD_NM}</td>
					<th>협력사</th>
					<td>${resultMap.VENDOR_ID} / ${resultMap.VENDOR_NM}</td>
				</tr>
				<tr>
					<th>표시단위코드</th>
	                <td>원</td>
	                <th>원가</th>
	                <td><script>fnMoneyComma('${resultMap.BUY_PRC}', 'out')</script></td>
	                <th>매가</th>
	                <td><script>fnMoneyComma('${resultMap.SELL_PRC}', 'out')</script></td>
	                <th>판매가</th>
	                <td><script>fnMoneyComma('${resultMap.CURR_SELL_PRC}', 'out')</script></td>
                </tr>
                <tr>
                	<th><span class="star">*</span> 최소주문<br>&nbsp;&nbsp;가능수량</th>
	                <td>
                        <c:if test="${resultMap.PROD_DIVN_CD eq '02'}" >
                            <input type="text" name="MIN_ORD_PSBT_QTY" id="MIN_ORD_PSBT_QTY" value="${resultMap.MIN_ORD_PSBT_QTY}" maxlength="6" style="ime-mode:disabled" onKeypress="onlyNumber();" onChange="doChange(this, '최소주문가능수량', '5', 'N')">
                            <input type="hidden" name="MIN_ORD_PSBT_QTY_ORI" id="MIN_ORD_PSBT_QTY_ORI" value="${resultMap.MIN_ORD_PSBT_QTY}"/>
                        </c:if>
                        <c:if test="${resultMap.PROD_DIVN_CD != '02'}" >
                            ${resultMap.MIN_ORD_PSBT_QTY}
                        </c:if>
                    </td>
	                <th><span class="star">*</span> 최대주문<br>&nbsp;&nbsp;가능수량 </th>
	                <td>
                        <c:if test="${resultMap.PROD_DIVN_CD eq '02'}" >
	                       <input type="text" name="MAX_ORD_PSBT_QTY" id="MAX_ORD_PSBT_QTY" value="${resultMap.MAX_ORD_PSBT_QTY}" maxlength="6" style="ime-mode:disabled" onKeypress="onlyNumber();" onChange="doChange(this, '최대주문가능수량', '5', 'N')">
	                       <input type="hidden" name="MAX_ORD_PSBT_QTY_ORI" id="MAX_ORD_PSBT_QTY_ORI" value="${resultMap.MAX_ORD_PSBT_QTY}"/>
                        </c:if>
                        <c:if test="${resultMap.PROD_DIVN_CD != '02'}" >
                            ${resultMap.MAX_ORD_PSBT_QTY}
                        </c:if>
	                </td>
                	<th>품절여부</th>
                	<td>${resultMap.SOUT_YN}</td>
                	<th>전시여부</th>
	                <td>
                        <input type="radio" name="DISP_YN" id="DISP_YN" value="Y" ${resultMap.DISP_YN eq 'Y' ? 'checked' : ''} ${resultMap.PROD_DIVN_CD != '02' ? 'disabled' : ''}>Y
                        <input type="radio" name="DISP_YN" id="DISP_YN" value="N" ${resultMap.DISP_YN eq 'Y' ? '' : 'checked'} ${resultMap.PROD_DIVN_CD != '02' ? 'disabled' : ''}>N
	                </td>
                </tr>
	            <tr>
	            	<th><a class="link" href="javascript:popupLocation();">원산지</a></th>
                    <td><input type="text" name="HOME_NM" id="HOME_NM" value="${resultMap.HOME_NM}" readonly></td>
	               <th>브랜드</th>
	               <td><input type="text" name="BRAND_NM" id="BRAND_NM" value="${resultMap.BRAND_NM}" onKeyPress="keyCode(event,'브랜드')" onChange="fnChkSpcCharByte(this,'브랜드',100)"></td>
	               <th>제조사</th>
                   <td><input type="text" name="MAKER_NM" id="MAKER_NM" value="${resultMap.MAKER_NM}" onKeyPress="keyCode(event,'제조사')" onChange="fnChkSpcCharByte(this,'제조사',100)"></td>
                   <th>모델명</th>
				   <td><input type="text" id="MODEL_NM" name="MODEL_NM" maxlength="50" value="${resultMap.MODEL_NM}"></td>
				</tr>
				
				<tr>
					<th>승인여부</th>
                    <td>${resultMap.APRV_YN}</td>
	                <th>승인일자</th>
                    <td>${resultMap.APRV_DATE}</td>
                    <th>승인자</th>
                    <td>${resultMap.APRV_ID}</td>
                     <th>EC전시여부</th>
                    <td>${resultMap.EC_DISP_YN}</td>
				</tr>
					
                <tr>
                    <th>등록자</th>
                    <td>${resultMap.REG_ID}</td>
                    <th>등록일</th>
                    <td>${resultMap.REG_DATE}</td>
                    <th>수정자</th> 
                    <td>${resultMap.MOD_ID}</td>
                    <th>수정일</th>
                    <td>${resultMap.MOD_DATE}</td>
                </tr>
			</table>
		</div>				
	</div>
</div>	
</form>

</body>
</html>