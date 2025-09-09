<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>LOTTE MART Back Office System</title>
	<link rel="stylesheet" href="/css/style_1024.css" />
	<script type="text/javascript" src="/js/jquery/jquery-1.6.1.js"></script>
	<script type="text/javascript" src="/js/epc/Ui_common.js"></script>
	
	<script type="text/javascript">
	var submenu = '';
	function menuclick(n){
	//alert("n:"+n);
	//alert("submenu:"+submenu);

		var className = '';
		if (submenu != n){
			if( submenu != '' ) {
				className = jQuery('#s' + submenu).attr('class');
				//alert("nn:"+n);

				if(className.indexOf("minus") != -1) {
					className = className.replace('minus', 'plus');
				}
				jQuery('#s' + submenu).attr('class', className);	

				if( jQuery('#smenu' + submenu).length ) {
					jQuery('#smenu' + submenu).hide();

					// 하위메뉴 class 초기화
					jQuery('#smenu' + submenu).find('>li').each(function() {
						jQuery(this).attr('class', '');
					});
				}
			}

			className = jQuery('#s' + n).attr('class');
			if(className.indexOf("plus") != -1) {
				className = className.replace('plus', 'minus');
			}
			jQuery('#s' + n).attr('class', className);

			jQuery('.depth2').each(function() {
				jQuery(this).find('>a').attr('class', 'd2');
			});

			jQuery('#s' + n).find('>a').attr('class', 'd2_on');

			if( jQuery('#smenu' + n).length ) {
				if( jQuery('#smenu' + n).attr('clickEvent') != 'Y' ) {
					jQuery('#smenu' + n).find('>li').bind('click', function() {
						var nowObj = jQuery(this);
						jQuery('#smenu' + n).find('>li').each(function() {
							if( jQuery(this).get(0) == jQuery(nowObj).get(0) ) {
								jQuery(this).attr('class', 'on');
							} else {
								jQuery(this).attr('class', '');
							}
						});
					});
					jQuery('#smenu' + n).attr('clickEvent', 'Y');
				}
				jQuery('#smenu' + n).show();
			}

			submenu = n;
		} else {
			className = jQuery('#s' + n).attr('class');
			if(className.indexOf("minus") != -1) {
				className = className.replace('minus', 'plus');
			}
			jQuery('#s' + n).attr('class', className);
			jQuery('#s' + n).find('>a').attr('class', 'd2');

			if( jQuery('#smenu' + n).length ) {
				jQuery('#smenu' + n).hide();

				jQuery('#smenu' + n).find('>li').each(function() {
					jQuery(this).attr('class', '');
				});
			}

			submenu = '';
		}
	}
	
	function initMenu()
	{
		
		<c:choose>
			<c:when test="${empty pgm.pgm_sub }">
				menuclick(1,'N');
			</c:when>
			<c:otherwise>
				menuclick(<c:out value='${pgm.pgm_sub}'/>,'N'); 
			</c:otherwise>	
		</c:choose>		
		
		
		parent.widthFram.cols = "190px,*"; 
		
	}
	</script>
</head>

<body onLoad="initMenu();">
	<!-- left area -->
	<div id="left_wrap">
		<!-- left open -->
		<div id="leftArea">
			<%-- <h2>SRM 정보</h2>
			<ul>
				<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1,'N');" class="d2">SRM 정보</a>
					<ul id="smenu1" style="display: none;">
						<li><a href="<c:url value="/edi/main/SRMVEN0020.do"/>" target="contentFram">인증정보 등록</a></li>
					</ul>
				</li>
			</ul> --%>
			
			
			<h2>SRM정보</h2>
				<ul>
					<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1,'N');" class="d2"> 정보관리</a>
						<ul id="smenu1" style="display:none;">
							<li><a href="<c:url value="/edi/ven/SRMVEN0010.do"/>" target="contentFram">파트너사 정보관리</a></li>
							<li><a href="<c:url value="/edi/ven/SRMVEN0020.do"/>" target="contentFram">인증정보관리</a></li>
							<li><a href="<c:url value="/edi/ven/SRMVEN0040.do"/>" target="contentFram">품질경영평가조치</a></li>
						</ul>
					</li>
					<li id="s2" class="depth2 plus"><a href="#" onclick="menuclick(2,'N');" class="d2"> 모니터링</a>
						<ul id="smenu2" style="display:none;">
							<li><a href="<c:url value="/edi/ven/SRMVEN0030.do"/>" target="contentFram">평가모니터링</a></li>
						</ul>
					</li> 
					<li id="s3" class="depth3 plus"><a href="#" onclick="menuclick(3,'N');" class="d2"> 성과평가</a>
						<ul id="smenu3" style="display:none;">
							<li><a href="<c:url value="/edi/ven/SRMVEN0050.do"/>" target="contentFram">성과평가 결과</a></li>
							<li><a href="<c:url value="/edi/product/NEDMPRO0160.do"/>" target="contentFram">운영중단상품</a></li>
						</ul>
					</li>
				</ul>
			
		</div>
	</div>
</body>
</html>