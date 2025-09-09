<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib 	prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<!-- 공통 css 및 js 파일 commonHead.do 강제 추가 이동빈 20120215-->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link type="text/css" rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/style_1024.css"></link>
<script type="text/javascript" src="/js/jquery/jquery-1.5.2.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.form.js"></script>
<script type="text/javascript" src="/js/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.validate.1.8.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.metadata.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.custom.js"></script>
<script type="text/javascript" src="/js/wisegrid/WiseGridTag.js" ></script>
<script type="text/javascript" src="/js/wisegrid/WiseGridExtension.js" ></script>
<script type="text/javascript" src="/js/wisegrid/WiseGridConfig.js" ></script>
<script type="text/javascript" src="/js/epc/Ui_common.js"></script>
<script type="text/javascript" src="/js/epc/common.js"></script>
<script type="text/javascript" src="/js/epc/paging.js" ></script>
<script type="text/javascript" src="/js/epc/validate.js" ></script>
<script type="text/javascript" src="/js/epc/member.js" ></script>
<script type="text/javascript" src="/js/common/utils.js" ></script>

</head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
<script type="text/javascript">

	function doUpdate(){
		//if(!nullCheck('제목',$('#title').val(),$('#title'))){
		//	return;
		//}
		//if(!byteCheck('제목',$('#title').val(),200,$('#title'))){
		//	return;
		//}
		
		if(!nullCheck('상품평',$('#recommContent').val(),$('#recommContent'))){
			return;
		}
		if(!byteCheck('상품평',$('#recommContent').val(),4000,$('#recommContent'))){
			return;
		}
		
 		if( $("[name=checkExlnSlt]").is(":checked") ){
			$('#exlnSltYn').val("Y");
		}else{
			$('#exlnSltYn').val("N");
		}
		
		if (!confirm('<spring:message code="msg.common.confirm.update"/>')) {
			return;
		}

		callAjaxByForm('#dataForm', '<c:url value="/board/productInfoUpdate.do"/>', '#dataForm', 'POST');
	}

	function viewImg(atchFileId) {
		var targetUrl = '<c:url value="/board/photoReviewDetailForm.do"/>?atchFileId='+atchFileId;
		Common.centerPopupWindow(targetUrl, 'imgDetail', {width : 850, height : 850 , scrollBars : "YES"});
	}
	
	function callAjaxByForm(form, url, target, Type) {

		var formQueryString = $('*', form).fieldSerialize();
		
		$.ajax({
			type: Type,
			url: url,
			data: formQueryString,
			success: function(json) {
				try {
					if(jQuery.trim(json) == ""){//처리성공
						alert('<spring:message code="msg.common.success.request"/>');
						top.close();
						opener.doSearch();
					}else if("accessAlertFail"){
						alert('<spring:message code="msg.common.error.noAuth"/>');
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
</script>

<form name="dataForm" id="dataForm" method="post" >
<input type="hidden" name="recommSeq" id="recommSeq" value="${data.recommSeq}">
<input type="hidden" name="exlnSltYn" id="exlnSltYn" />

  <div id="popup" >
    <!--  @title  -->
     <div id="p_title1">
		<h1>상품평 게시판 상세정보</h1>
        <span class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
     </div>
     <!--  @title  //-->
     <!--  @process  -->
     <div id="process1">
		<ul>
			<li>홈</li>
			<li>게시판관리</li>
			<li class="last">상품평 게시판 관리</li>
		</ul>
     </div>
	 <!--  @process  //-->
	 
	 
	 <div class="popup_contents">
		<!--  @작성양식 2 -->
		<div class="bbs_search3">
			<ul class="tit">
				<li class="tit">상품평정보</li>
				<li class="btn">
					<a href="#" class="btn" onclick="doUpdate();"><span><spring:message code="button.common.save"/></span></a>
					<a href="#" class="btn" onclick="top.close();"><span><spring:message code="button.common.close"/></span></a>
				</li>
			</ul>
			<table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
				<colgroup>
					<col width="15%">
					<col width="15%">
					<col width="15%">
					<col width="15%">
					<col width="15%">
					<col width="25%">
				</colgroup>
				<tr>
					<th><span class="fst"></span>글번호</th>
					<td>${data.recommSeq}</td>
					<th><span class="fst"></span>인터넷상품코드</th>
					<td>${data.prodCd}</td>
					<th><span class="fst"></span>상품명</th>
					<td>${data.prodName}</td>
				</tr>
				<tr>
					<th><span class="fst"></span>글쓴이</th>
					<td>${data.memberName}</td>
					<th><span class="fst"></span>조회수</th>
					<td>${data.viewCnt}</td>
					<th><span class="fst"></span>사용여부</th>
			        <td>
          				<select name="delYN" id="delYN" style="width:100px" title="사용여부" >
            				<option	value="N"  ${data.delYN eq 'N' ? 'selected' : ''}>사용</option>
							<option	value="Y"  ${data.delYN eq 'Y' ? 'selected' : ''}>미사용</option>
          				</select>
          			</td>
				</tr>
				<tr>
					<th><span class="fst"></span>가격</th>
					<td>${data.item1}</td>
					<th><span class="fst"></span>배송</th>
					<td>${data.item2}</td>
					<th><span class="fst"></span>품질</th>
					<td>${data.item3}</td>
				</tr>
				<tr>
					<th><span class="fst"></span>등록일자</th>
					<td>${data.ntceDy}</td>
				<!-- 2014.10.29 박지혜 수정 추가-->	
				<c:if test="${data.mallDivnCd == '00002'}">
					<th><span class="fst"></span>메인전시순번</th>
					<td>
						<select name="mainDpSeq" id="mainDpSeq" style="width:80%" class="select">
							<option	value=""></option>
							<script>
							for(i = 1;i<=5;i++){
								var data = i;

								if('${data.mainDpSeq}' == data){
									document.write("<option value="+data+" selected>"+data+"</option>");
								}else{
									document.write("<option value="+data+">"+data+"</option>");
								}

							}
							</script>
						</select>
					</td>
				</c:if>
				<c:if test="${data.mallDivnCd != '00002'}">
					<th><span class="fst"></span></th>
					<td></td>
				</c:if>
				<!-- 2014.10.29 박지혜 수정 종료-->
                    <td colspan="2">
                    	<input type="checkbox" name="checkExlnSlt" id="checkExlnSlt" ${data.exlnSltYn eq 'Y' ? 'checked' : ''} />우수상품평여부
                    </td>
				</tr>
				
				<tr>
					<th><span class="fst"></span>제목</th>
					<td colspan="5">${data.title}</td>
				</tr>
			</table>
			<c:if test="${data.recommDivnCd == '01'}">
			<table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
				<colgroup>
					<col width="15%">
					<col width="85%">
					
				</colgroup>
				<tr style="height:150px">
					<th><span class="fst"></span>일반상품평</th>
					<td>
						<textarea name="recommContent" id="recommContent" style="width:570px;height:150px" wrap="hard" readOnly>${data.recommContent}</textarea>
					</td>	
				</tr>
			</table>
			</c:if>
			<c:if test="${data.recommDivnCd == '02'}">
			<table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
				<colgroup>
					<col width="15%">
					<col width="25%">
					<col width="60%">
					
				</colgroup>
				<tr style="height:150px">
					<th><span class="fst"></span>포토상품평</th>
					<td>
						<a href="javascript:viewImg('${data.atchFileId}');" ><img id="imgBox" name="imgBox" src='${URL}'  style="height:145px; width:145px; border: 1px;"></a>
					</td>	
					<td>
						<textarea name="recommContent" id="recommContent" style="width:400px;height:150px" wrap="hard" readOnly>${data.recommContent}</textarea>
					</td>	
				</tr>
			</table>
			</c:if>
			<!-- 2014.09.30 박지혜 수정 추가 -->
			<c:if test="${data.mallDivnCd == '00002'}">
			<table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
				<colgroup>
					<col width="15%">
					<col width="85%">
					
				</colgroup>
				<tr style="height:120px">
					<th><span class="fst"></span>상품이미지</th>
					<td>
						<c:forEach items="${fileList}" var="fileVO" begin="0" varStatus="stat">
							<img id="imgBox" name="imgBox" 
								 src='<c:url value='http://toysrus.lottemart.com/frontFile/getImage.do'/>?atchFileId=<c:out value="${fileVO.atchFileId}"/>&fileSn=<c:out value="${fileVO.fileSn}"/>&fileLoc=productreview'  style="height:100px;">
						</c:forEach>
					</td>	
				</tr>
			</table>
			</c:if> 
			<!-- 2014.09.30 박지혜 수정 종료 -->	
		</div>
		<!--  @작성양식  2//-->
	</div>
  </div>
</form>  
<iframe name="_if_save" src="/html/epc/blank.html" style="display:none;"></iframe>
</body>
</html>