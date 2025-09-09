<%@include file="../common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>

<script>
</script>

</head>

<body>
	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if> >
		<div>
			<form name="searchForm" method="post" action="#">
			<input type="hidden" id="widthSize" name="widthSize" value="${param.widthSize }" > 
			<input type="hidden" id="forward_value" name="forward_value" / >
			<div id="wrap_menu">
				<div class="wrap_con">
					<div class="bbs_search">
						<ul class="tit">
							<li class="tit"></li>
						</ul>
					</div>
					<!-- list -->
					<div class="bbs_list">
						<table width="737" border="0" cellspacing="0" cellpadding="0" align="center" background="/images/epc/edi/consult/complete_01.gif" height="217">
				         <tr> 
				          <td width="281">&nbsp;</td>
				          <td>&nbsp;</td>
				        </tr>
				        <tr> 
				          <td width="281">&nbsp;</td>
				          <td valign="top"> 
				            <table width="400" border="0" cellspacing="0" cellpadding="0">
				              <tr> 
				                <td height="70">&nbsp;</td>
				              </tr>
				              <tr> 
				                <td> 
				                  <div align="right">
				                    <p align="center"><b><font color="#ffffff">등록하신 상품의 <font size = "3" color = "purple" >발주중단 신청</font>이 완료되었습니다.</font></b></p>
				                    <p align="center"><b><font color="#ffffff">문의는 담당 MD에게&nbsp;해 주시기 바랍니다.</font></b></p>
				                  <P align=center><font color="#ffffff"> 등록하신 
				                  상품은 담당 MD가 확정한 후 부터&nbsp; <font color = "red" >발주 중단</font>이 적용됩니다. <br>
				                      신청 후 7일이 지나도
				                      확정이 되지 않았다면 확정은 취소가 됩니다. <br></font><FONT color=#ffffff>언제나 저희 롯데마트와 함께 해 주셔서 
				                  감사합니다.</FONT></P>
				                  </div>
				                </td>
				              </tr>
				            </table>
				          </td>
				        </tr>
				      </table>
			
						</div>
					</div>
					
					<div class="bbs_search">
						<ul class="tit">
							<li class="tit"></li>
							<li class="btn">
								<a href='<c:url value="/edi/consult/PEDMCST0009.do"/>' class="btn"><span><spring:message code="button.consult.back"/></span></a>
							</li>
						</ul>
					</div>
			</div>
			</form>
		</div>
	</div>

</body>
</html>

