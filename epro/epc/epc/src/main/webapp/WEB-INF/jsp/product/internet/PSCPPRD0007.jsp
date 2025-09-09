<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHeadPopup.do" />

<script language="javascript" type="text/javascript">
	/** ********************************************************
	* 닫기 처리 함수
	********************************************************* */
	function doClose(){
		top.close();
	}
	
	/** ********************************************************
	 * 셀 클릭시 이벤트 처리 함수
	 *  - 원산지를  클릭했을 때 부모창으로 값을 전송하고 본 팝업창을 닫는다
	 ******************************************************** */
	function doPopupPushValue(minorCd,cdNm) 
	{
		//-------------------------------------------
		// 적당한 팝업창 크기	{width : 520, height : 415}
		//-------------------------------------------
		opener.setLocationInto(minorCd, cdNm);
		top.close();
	}
</script>
</head>

<body>

<div id="popup">
    <!------------------------------------------------------------------ -->
    <!--    title -->
    <!------------------------------------------------------------------ -->
    <div id="p_title1">
        <h1>원산지조회팝업</h1>
        <span class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
    </div>
    <!-------------------------------------------------- end of title -- -->
    
    <!------------------------------------------------------------------ -->
    <!--    process -->
    <!------------------------------------------------------------------ -->
    <!--    process 없음 -->
    <br>
    <!------------------------------------------------ end of process -- -->
	<div class="popup_contents">

		<div class="bbs_search3">
			<ul class="tit">
				<li class="tit">[원산지리스트]</li>
				<li class="btn">
				    <a href="javaScript:doClose()" class="btn"><span><spring:message code="button.common.close"/></span></a>
				</li>
			</ul>
			
		<!------------------------------------------------------------------ -->
		<!-- 	table -->
		<!------------------------------------------------------------------ -->
			<table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
				<colgruop>
					<col width="33%">
					<col width="33%">
					<col width="33%">
				</colgruop>
				<tr>
					<c:forEach items="${codeList}" var="code" varStatus="status">
						<c:if test="${(status.count%3-1)==0}">
							</tr><tr>
						</c:if>
						<td>
							<a href="#LINK" onClick="doPopupPushValue('${code.MINOR_CD}','${code.CD_NM}');">${code.CD_NM}</a>
						</td>
					</c:forEach>
				</tr>
			</table>
		<!---------------------------------------------------- end of table -- -->
		</div>
	
</div><!-- class popup_contents// -->

<!-- -------------------------------------------------------- -->
<!--    footer  -->
<!-- -------------------------------------------------------- -->
<div id="footer">
    <div id="footbox">
        <div class="msg" id="resultMsg"></div>
    </div>
</div>
<!---------------------------------------------end of footer -->

</div><!-- id popup// -->

</body>
</html>
</html>