<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="epcutl" uri="/WEB-INF/tlds/epcutl.tld" %>
<%@page import="lcn.module.common.util.DateUtil"%>
<%
	/*
    String tabNo = "";
    tabNo = request.getParameter("tabNo");
    String prodCd = request.getParameter("prodCd");
    String prodCdFlag =  prodCd.substring(0,1);
    String cateId = (String)request.getAttribute("categoryId");
    */
    
    String tabNo = request.getParameter("tabNo") == null ? "" : request.getParameter("tabNo");
    String prodCd = request.getParameter("prodCd") == null ? "" : request.getParameter("prodCd");
    
    String prodCdFlag =  "";
    
    if(prodCd != null && prodCd.length() >= 1){
    	prodCdFlag = prodCd.substring(0,1);
    }
    
    String cateId = request.getAttribute("categoryId") == null ? "" : (String)request.getAttribute("categoryId");    
    
    if(cateId != null && cateId.length() >= 4){
    	cateId = cateId.substring(0,4);
    }
    
%>
	<script language="javascript" type="text/javascript">
		$(document).ready(function(){
			var onlineProdTypeCd = parent.parent.prdInfoFrame.dataForm.ONLINE_PROD_TYPE_CD.value;
			
			if(onlineProdTypeCd == "01"){ //일반 상품
				$("#addProdLi").show();
			}
			
		});
	
		function changeTab(val)
		{
			if(!val) val = 1;
	
			tabUrl = new Array(12);
			tabUrl[1]='<c:url value="/product/selectProductItemForm.do"/>';          //단품정보  -03-[03]
			tabUrl[2]='<c:url value="/product/selectproductPriceForm.do"/>';         //가격정보  -04-[18]
			tabUrl[3]='<c:url value="/product/selectProductRecommendForm.do"/>';     //추천상품  -05-[04]
			tabUrl[4]='<c:url value="/product/selectProductDescriptionForm.do"/>';   //상세이미지-07-[05]
			tabUrl[5]='<c:url value="/product/selectProductKeywordForm.do"/>';       //상품키워드-06-[17]
			tabUrl[6]='<c:url value="/product/selectProductImageForm.do"/>';         //대표이미지-10-[06]
			tabUrl[7]='<c:url value="/product/selectProductPresentForm.do"/>';       //증정품    -11-[16]
			tabUrl[8]='<c:url value="/product/selectProductAttributeForm.do"/>';     //추가속성  -19-[19]
		    tabUrl[9]='<c:url value="/product/selectProductCommerceForm.do"/>';	     //전자상거래-[20]
		    tabUrl[10]='<c:url value="/product/selectProductCertForm.do"/>';	 	 //KC인증-22
		    tabUrl[11]='<c:url value="/product/selectProductComponentForm.do"/>';	 //추가구성품-23
		    tabUrl[12]='<c:url value="/product/selectProductRelationForm.do"/>';	 	 //연관상품-23
		    tabUrl[13]='<c:url value="/product/selectDeliveryForm.do"/>';	 	 //배송-24
		    tabUrl[14]='<c:url value="/product/selectTprMpicinfo.do"/>';	 	 //동영상
		    //2016-11-21 키워등록탭 추가 GWSG 최성웅 ( 모든상품(D,L,88,F..) 노출탭 )
		    tabUrl[15]='<c:url value="/product/selectKeywordForm.do"/>';	 	 //키워드등록-[25]
		    
			
			document.form1.target = "prdDetailFrame";
			document.form1.action = tabUrl[val];
			document.form1.submit();
		}
	</script>

	<div class="tab mt20">
		<ul>		 
			<% if( (prodCdFlag.toUpperCase()).equals("L".toUpperCase()) ){ %>
			<li class="<%if(tabNo.equals("1")){%>on<%}%>" ><a href="#" onClick="changeTab(1);">단품정보</a></li>
			<li class="<%if(tabNo.equals("2")){%>on<%}%>" ><a href="#" onClick="changeTab(2);">가격정보</a></li>
			<li class="<%if(tabNo.equals("3")){%>on<%}%>" ><a href="#" onClick="changeTab(3);">추천상품</a></li>
			<li class="<%if(tabNo.equals("4")){%>on<%}%>" ><a href="#" onClick="changeTab(4);">상세이미지</a></li>
			<li class="<%if(tabNo.equals("5")){%>on<%}%>" ><a href="#" onClick="changeTab(5);">상품키워드</a></li>
			<li class="<%if(tabNo.equals("6")){%>on<%}%>" ><a href="#" onClick="changeTab(6);">대표이미지</a></li>
			<li class="<%if(tabNo.equals("7")){%>on<%}%>" ><a href="#" onClick="changeTab(7);">증정품</a></li>
			<li class="<%if(tabNo.equals("8")){%>on<%}%>" ><a href="#" onClick="changeTab(8);">추가속성</a></li>
		    <li class="<%if(tabNo.equals("9")){%>on<%}%>" ><a href="#" onClick="changeTab(9);">전자상거래</a></li>
		    <li class="<%if(tabNo.equals("10")){%>on<%}%>" ><a href="#" onClick="changeTab(10);">제품안전인증</a></li>
		    <li id="addProdLi" class="<%if(tabNo.equals("11")){%>on<%}%>"  style="display:none;"><a href="#" onClick="changeTab(11);">추가구성품</a></li>
		    <li class="<%if(tabNo.equals("12")){%>on<%}%>" ><a href="#" onClick="changeTab(12);">연관상품</a></li>		
		    <li class="<%if(tabNo.equals("13")){%>on<%}%>" ><a href="#" onClick="changeTab(13);">배송</a></li>
		    <li class="<%if(tabNo.equals("14")){%>on<%}%>" ><a href="#" onClick="changeTab(14);">동영상</a></li>
		    <!-- 2016-11-21 키워드관리 탭 추가 GWSG 최성웅 -->
		    <%-- <li class="<%if(tabNo.equals("15")){%>on<%}%>" ><a href="#" onClick="changeTab(15);">키워드관리</a></li> --%>
		    
		    <% }else if( (prodCdFlag.toUpperCase()).equals("C".toUpperCase()) ){ // 추가구성 %>
		    <li class="<%if(tabNo.equals("1")){%>on<%}%>" ><a href="#" onClick="changeTab(1);">단품정보</a></li>
		    <li class="<%if(tabNo.equals("2")){%>on<%}%>" ><a href="#" onClick="changeTab(2);">가격정보</a></li>
		    <li class="<%if(tabNo.equals("6")){%>on<%}%>" ><a href="#" onClick="changeTab(6);">대표이미지</a></li>
		    <!-- 2016-11-21 키워드관리 탭 추가 GWSG 최성웅 -->
		    <%-- <li class="<%if(tabNo.equals("15")){%>on<%}%>" ><a href="#" onClick="changeTab(15);">키워드관리</a></li> --%>
		    
		    <% }else if( (prodCdFlag.toUpperCase()).equals("D".toUpperCase()) ){ // 묶음상품%>
		    <li class="<%if(tabNo.equals("3")){%>on<%}%>" ><a href="#" onClick="changeTab(3);">상품구성정보</a></li>
		    <li class="<%if(tabNo.equals("4")){%>on<%}%>" ><a href="#" onClick="changeTab(4);">상세이미지</a></li>
		    <li class="<%if(tabNo.equals("6")){%>on<%}%>" ><a href="#" onClick="changeTab(6);">대표이미지</a></li>
		    <!-- 2016-11-21 키워드관리 탭 추가 GWSG 최성웅 -->
		    <%-- <li class="<%if(tabNo.equals("15")){%>on<%}%>" ><a href="#" onClick="changeTab(15);">키워드관리</a></li> --%>
		    <li class="<%if(tabNo.equals("14")){%>on<%}%>" ><a href="#" onClick="changeTab(14);">동영상</a></li>

		    <% }else{%>
		    <li class="<%if(tabNo.equals("1")){%>on<%}%>" ><a href="#" onClick="changeTab(1);">단품정보</a></li>
			<li class="<%if(tabNo.equals("2")){%>on<%}%>" ><a href="#" onClick="changeTab(2);">가격정보</a></li>
			<li class="<%if(tabNo.equals("3")){%>on<%}%>" ><a href="#" onClick="changeTab(3);">추천상품</a></li>
			<li class="<%if(tabNo.equals("4")){%>on<%}%>" ><a href="#" onClick="changeTab(4);">상세이미지</a></li>
			<li class="<%if(tabNo.equals("5")){%>on<%}%>" ><a href="#" onClick="changeTab(5);">상품키워드</a></li>
			<li class="<%if(tabNo.equals("6")){%>on<%}%>" ><a href="#" onClick="changeTab(6);">대표이미지</a></li>
			<li class="<%if(tabNo.equals("7")){%>on<%}%>" ><a href="#" onClick="changeTab(7);">증정품</a></li>
			<li class="<%if(tabNo.equals("8")){%>on<%}%>" ><a href="#" onClick="changeTab(8);">추가속성</a></li>
		    <li class="<%if(tabNo.equals("9")){%>on<%}%>" ><a href="#" onClick="changeTab(9);">전자상거래</a></li>
		    <li class="<%if(tabNo.equals("10")){%>on<%}%>" ><a href="#" onClick="changeTab(10);">제품안전인증</a></li>
		    <li class="<%if(tabNo.equals("14")){%>on<%}%>" ><a href="#" onClick="changeTab(14);">동영상</a></li>
		    <!-- 2016-11-21 키워드관리 탭 추가 GWSG 최성웅 -->
		  <%--   <li class="<%if(tabNo.equals("15")){%>on<%}%>" ><a href="#" onClick="changeTab(15);">키워드관리</a></li> --%>
		    <% } %>
		</ul>
	</div>