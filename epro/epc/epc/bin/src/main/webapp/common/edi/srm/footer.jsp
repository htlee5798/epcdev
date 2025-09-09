<%@page pageEncoding="utf-8"%>
<%@ include file="/common/edi/taglibs.jsp" %>

	<!--Footer-->
	<div id="footer">
		<div class="inner float-wrap">
			<div class="float-l">
				<ul class="f_info">
					<li><img src="/images/epc/common/f_logo.png" alt="Lotte Mart" class="img-mid"> <span class="mr10">|</span></li>
					<!-- <li><spring:message code='footer.copyright.text2'/><span>|</span></li> -->	<%--대표이사: 이원준  --%>
					<li><spring:message code='footer.copyright.text3'/><span>|</span></li>	<%--서울시 송파구 올림픽로 269 (신천동 롯대케슬골드) 6, 7층  --%>
					<li>e-Mail : srm@lottemart.com<span>|</span></li>
					<li><a href="https://company.lottemart.com/privacy/privacy.asp" target="_blank"><span style="color: red;">개인정보처리방침</span></a></li>
				</ul>
			</div>
			<div class="float-r">
				Copyright ⓒ <strong><a href="http://www.lottemart.com" target="_blank">LOTTE MART.COM</a></strong>, All rights reserved.
			</div>
		</div>
	</div><!--END Footer-->

	<form id="manualDownForm" name="manualDownForm" method="post" >
		<input type="hidden" id="manulName" name="manulName"/>
	</form>
