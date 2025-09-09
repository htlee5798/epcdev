<%@include file="../common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="html" uri="http://lcnjf.lcn.co.kr/taglib/edi"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>

<script language="JavaScript">

function forwardComent(){
	
	var form = document.forms[0];

	if(form.coment.value.length > 40){
		alert("조치내역 입력은 40자 이내로 하세요.");
		return;
	}

	form.p_coment.value=form.coment.value;
	loadingMaskFixPos();
	form.action  = "<c:url value='/edi/buy/PEDPBUY0021UPDATE.do'/>";
	form.submit();	

}
</script>

</head>




<body>

			
<form id="form1" name="form1" method="post" action="#">
	<input type="hidden" id="p_str_cd" name="p_str_cd" value="${paramMap.p_str_cd }">
	<input type="hidden" id="p_reg_dy" name="p_reg_dy" value="${paramMap.p_reg_dy }">
	<input type="hidden" id="p_prod_cd" name="p_prod_cd" value="${paramMap.p_prod_cd }">
	<input type="hidden" id="p_send_fg" name="p_send_fg" value="${paramMap.p_send_fg }">
	<input type="hidden" id="p_ven_cd" name="p_ven_cd" value="${paramMap.p_ven_cd }">
	<input type="hidden" id="p_coment" name="p_coment">


<div id="popup">
    <!------------------------------------------------------------------ -->
    <!--    title -->
    <!------------------------------------------------------------------ -->
    <div id="p_title1">
        <h1>센터입고거부</h1>
        <span class="logo"><img src="/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
    </div>
    <!-------------------------------------------------- end of title -- -->
    
    <!------------------------------------------------------------------ -->
    <!--    process -->
    <!------------------------------------------------------------------ -->
    <!--    process 없음 -->
    <br>
    <!------------------------------------------------ end of process -- -->
	<div class="popup_contents">

	<!------------------------------------------------------------------ -->
	<!-- 	검색조건 -->
	<!------------------------------------------------------------------ -->
	<div class="bbs_search">
		<ul class="tit">
			<li class="tit">센터입고거부상품 상세내역</li>
			<li class="btn">
			<c:if test="${buyList.SEND_FG eq false }">
				<a href="javascript:forwardComent();"  class="btn"><span><spring:message code="button.common.save"/></span></a>
			</c:if>
				<a href="javascript:window.print();"  class="btn"><span><spring:message code="button.common.print"/></span></a>
				<a href="javascript:window.close();"  class="btn"><span><spring:message code="button.common.close"/></span></a>
				
                
			</li>
		</ul>
	</div>
	
	<!----------------------------------------------------- end of 검색조건 -->
				
	<!-- -------------------------------------------------------- -->
	<!--	검색내역 	-->
	<!-- -------------------------------------------------------- -->
	<div class="wrap_con">
		<div class="bbs_list">
			<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
				
<!-- (상세내역부) -->
	<tr>
		<td width=260>
			<!-- 이미지-->
			<table width=100% height=100% cellpadding=1 cellspacing=1 border=0 bgcolor="#CECCCD">
			<tr>
				<td bgcolor=#ffffff align=center height=185>
					<img src="http://partner.lottemart.com/epc/viewImageBuy.jsp?img_file_nm=${buyList.IMG_FILE_NM }" name=imgbadprod width=260 height=185>
				</td>
			</tr>
			</table>
		</td>
		
		<td width=540>
			<!-- 상세내역 -->
			<table width=100% cellpadding=1 cellspacing=1 border=0 bgcolor="#CECCCD">
			<tr>
				<td height=25 width=100 bgcolor=#FFFFCC align=center>의뢰일자
				</td>
				<td bgcolor=#ffffff >
					&nbsp;<input type=bgcolortext name=edi_send_dy value="${buyList.SEND_DATE }" readonly style="width:380px;">
				</td>
			</tr>
			<tr>
				<td height=25 width=100 bgcolor=#FFFFCC align=center>의뢰자정보</td>
				<td bgcolor=#ffffff>
					&nbsp;<input type=text name=md_emp_nm value="${buyList.VEN_NM }" readonly style="width:120px">
					&nbsp;/&nbsp;<input type=text name=md_str_nm value="${buyList.STR_NM }" readonly style="width:150px">
				</td>
			</tr>
			<tr>
			
				<td height=25 width=100 bgcolor=#FFFFCC align=center>발생일자/지점</td>
				<td bgcolor=#ffffff>
				&nbsp;<input type=text name=str_nm value="${buyList.OCCUR_DATE }" readonly style="width:120px">
					&nbsp;/&nbsp;<input type=text name=str_nm value="${buyList.STR_NM }" readonly style="width:150px">
				</td>
				
			</tr>
			<tr>
				<td height=25 width=100 bgcolor=#FFFFCC align=center>상품정보</td>
				<td bgcolor=#ffffff>
					&nbsp;<input type=text name=prod_cd value="${buyList.PROD_CD }" readonly style="backgroundcolor:#ffffff;width:232px;">
					&nbsp;/&nbsp;<input type=text name=srcmk_cd value="${buyList.SRCMK_CD }" readonly style="backgroundcolor:#ffffff;">
					
				</td>
			</tr>
			<tr>
				<td height=25 width=100 bgcolor=#FFFFCC align=center>상품명&nbsp;</td>
				<td bgcolor=#ffffff>
					
					&nbsp;<input type=text name=prod_nm readonly value="${buyList.PROD_NM }" style="backgroundcolor:#ffffff;width:380px;">
				</td>
			</tr>
			
	
			
			<tr>
				<td height=25 width=100 bgcolor=#FFFFCC align=center>협력업체정보</td>
				<td bgcolor=#ffffff>
					&nbsp;<input type=text name=ven_nm value="${buyList.VEN_NM }" readonly style="backgroundcolor:#ffffff;width:380px;"></td>
			</tr>
		
			
		
				
			</table>
		</td>
	</tr>
	
	<tr>
		<td colspan=2 width=100% bgcolor=#ffffff align=center>
			<!-- 협의내역 및 조치 내역-->
			<table width=100% cellpadding=1 cellspacing=1 border=0 bgcolor="#CECCCD">
			<tr>
				<td height=25 width=260 bgcolor="#88aaff" align=center>점포협의내역 (검품담당)
				</td>
				<td height=25 width=260 bgcolor="#88aaff" align=center>MD협의내역(MDer) 
				</td>
				<td height=25 bgcolor="#88aaff" align=center>조치내역(40자이내,100바이트) 
				</td>
			</tr>
			

			
			<tr>
				<td height=80 width=260 bgcolor=#ffffff align=center>
					<textarea name=an_md_cmt readOnly style="width:100%;height:100%;background-color:#CECCCD;">${buyList.MD_CMT }</textarea>
				</td>
				<td rowspan=3 width=260 height=80 bgcolor=#ffffff align=center>
					<textarea name=an_disc_cmt readOnly style="width:100%;height:100%;background-color:#CECCCD;">${buyList.DISC_CMT }</textarea>
				</td>
	
				<td rowspan=3 height=80 bgcolor=#ffffff align=center>
				<c:if test="${buyList.SEND_FG eq false }">
					<textarea name=ven_cmt  maxlength=20 style="width:100%;height:100%;" id="coment" name="coment"  onkeyup="javascript:cal_byte(this, '100', '조치내역');">${buyList.VEN_CMT }</textarea>
				</c:if>
				<c:if test="${buyList.SEND_FG eq true }">
					<textarea name=ven_cmt  readOnly style="width:100%;height:100%;background-color:#CECCCD;"  style="width:100%;height:100%;" id="coment" name="coment" >${buyList.VEN_CMT }</textarea>
				</c:if>
				</td>
			</tr>
		</table>	
	</td>
	</tr>	
			
				
			</table>	
			
				
		</div>
	</div>
	<!-----------------------------------------------end of 검색내역  -->
    </div><!-- class popup_contents// -->
    
    <br/>

	<!-- -------------------------------------------------------- -->
	<!--    footer  -->
	<!-- -------------------------------------------------------- -->
	
	<!---------------------------------------------end of footer -->

</div><!-- id popup// -->
	










</form>	
				
</body>
</html>
