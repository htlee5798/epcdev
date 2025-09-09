<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>      
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<title><spring:message code='text.consult.field.lotteVendorConsult'/></title>


<script language="JavaScript">
	$(document).ready(function(){		
		$('#agreeForm input[type=radio]').change(function() {       
		    if (this.value == "N") {
		    	alert("<spring:message code='msg.consult.require.vendorAgreeYn'/>");
		    	return;
		    }
		    
		    $("input[name='agreeYn']").val("Y");		    
		    $("#agreeForm").submit();		    
		});
		
		/* 약관 전체동의 */
		$("input[type='checkbox']").live("change", function() {
			var cnt = 0;
			if ($(this).attr("class") == "agreeCheck") {
				$(".agreeCheck").each(function(index) {
					if ($(".agreeCheck").eq(index).attr("checked") == "checked") {
						cnt++;
						if ($(".agreeCheck").length == cnt) {
							$(".checkAll").attr("checked", true);
						}
						$(".notAgreeCheck").eq(index).attr("checked", false);
					} else {
						$(".checkAll").attr("checked", false);
					}
				});
				
			} else if ($(this).attr("class") == "notAgreeCheck") {
				$(".notAgreeCheck").each(function(index) {
					if ($(".notAgreeCheck").eq(index).attr("checked") == "checked") {
						$(".agreeCheck").eq(index).attr("checked", false);
						$(".checkAll").attr("checked", false);
					}
				});
			} else if ($(this).attr("class") == "checkAll") {
				if ($(".checkAll").attr("checked") == "checked") {
					$(".notAgreeCheck").attr("checked", false);
					$(".agreeCheck").attr("checked", true);
				} else {
					$(".agreeCheck").attr("checked", false);
				}
				
			}
		});
	});
	
	/* 약관동의 */
	function fnAgree() { 
		
		var agreeLength = 0;								// 체크된 체크박스 길이
		var notagreeindex = -1;								// 체크안된 첫 체크박스 index
		var target = "";									// 체크안된 약관 이름
		
		for (var i=0; i < $(".agreeCheck").length; i++) {
			if($(".agreeCheck").eq(i).is(":checked")) {
				agreeLength++;
			} else {
				if (notagreeindex == -1) {
					notagreeindex = i;
					target = $(".agreeCheck").eq(i).val();
				}
			}
		}
		
		if ($(".agreeCheck").length != agreeLength) {
			alert("<spring:message code='msg.srm.alert.notAgree' arguments='" + target + "' />");<%--{0} 의 약관에 동의 해주세요. --%>
			$(".agreeCheck").eq(notagreeindex).focus();	// 체크안된 약관 포커스
		} else {
			$("input[name='agreeYn']").val("Y");		    
		    $("#agreeForm").submit();
		}
	}
	
	/* 약관동의하지 않음 */
	function fnUnagree() {
		location.href = "<c:url value="/epc/edi/consult/NEDMCST0310loginHttpsNew.do" />";
	}
</script>

</head>
<body >

	<form name="agreeForm"	id="agreeForm"	method="post"  action="<c:url value='/epc/edi/consult/NEDMCST0310checkVendorBusinessNo.do'/>">
	
		<input type="hidden"		name="businessNo"		id="businessNo"			value="<c:out value='${param.businessNo}'/>"	/>
		<input type="hidden"		name="agreeYn"			id="agreeYn"				/>
		
		<div id="wrap">		
			<div id="con_wrap">
				
				<!-- <div>
					<table align="center">
						<tr>
							<td align="center">
								<strong><span style="font-size:20px;">[신규 입점 관련 개인정보 수집 및 이용 동의서]</span></strong>
							</td>							
						</tr>
					</table>
				</div>
				
				<br/> -->
				
				<div class="terms_box" style="height: 400px;">
					<div style="text-align: center; padding-top: 20px;">
						<strong style="font-size: 18pt;">[신규 입점 관련 개인정보 수집 및 이용 동의서]</strong>
					</div>
					
					<br>
					<br>
					
					<strong style="font-size: 12pt; margin-left: 20px;">1. [개인정보 수집 항목, 수집 • 이용 목적 및 보유 및 이용기간]</strong>
					
					<p class="article__text" style="font-size: 10pt; line-height: 1.5em; padding: 10px;">
						롯데쇼핑(주)롯데마트는 개인정보보호법 등 관련 법령상의 개인정보 규정을 준수하고 있습니다.<br />
						개인정보의 수집 항목과 목적, 기간은 아래와 같으며, 수집된 정보는 본 수집/이용 목적 외에 다른 목적으로는 사용되지 않습니다.<br />
					</p>
					
					<br />
				
					<table class="type4" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<th>수집 항목</th>
							<th>수집/이용 목적</th>
							<th>보유 및 이용기간</th>
						</tr>
				
						<tr>
							<td align="center">
								상호명<br />
								사업자등록번호 <br />
								대표자명<br />
								담당자명<br />
								연락처<br />
								전자우편주소<br />
								주소<br />
								FAX
							</td>
							<td align="center">업무 협의 등 입점과 관련된 의사소통 경로 활용</td>
							<td align="left">
								- 입점 탈락 이후에도 파트너사 우수한 상품 고려, 롯데마트에서 신규거래 재요청 위한 연락 드리기 위해 탈락일로부터 1년 보관 후 파기합니다.
								<br />
								<br />
								- 동의 철회 접수일로부터 5일 이내 파기합니다.
							</td>
						</tr>
					</table>
				
					<br />
					<br />
					
					<strong style="font-size: 12pt; margin-left: 10px;">[개인정보 수집 및 이용 동의 거부의 권리]</strong>
					
					<p class="article__text" style="font-size: 10pt; line-height: 1.5em; padding: 10px;">
						위 사항에 대하여 동의를 거부할 권리가 있으며, 거부할 경우 상담 등의 절차가 지연되거나 중단될 수 있습니다.
					</p>
					
					<div style="padding: 10px; text-align: right; font-weight: bold;">
						<input type="checkbox" class="agreeCheck" value="[개인정보 수집 및 이용 동의 거부의 권리]" /> 동의함 &nbsp;&nbsp;
						<input type="checkbox" class="notAgreeCheck" /> 동의 하지 않음
					</div>
					
					
					<!--  -->
					<strong style="font-size: 12pt; margin-left: 20px;">2. [수집한 개인정보의 위탁]</strong>
					
					<p class="article__text" style="font-size: 10pt; line-height: 1.5em; padding: 10px;">
						롯데마트는 서비스 이행을 위해 아래와 같이 개인정보 취급업무를 외부 전문업체에 위탁하여 운영하고 있습니다.
					</p>
					
					<br />
				
					<table class="type4" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<th width="20%">수탁자</th>
							<th width="20%">위탁의 범위</th>
							<th width="20%">위탁 처리 정보</th>
							<th width="40%">보유 및 이용기간</th>
						</tr>
				
						<tr>
							<td align="center">
								한국에스지에스(주)<br />
								BUREAU VERITAS<br />
								KOREA
							</td>
							<td align="center">파트너사 품질경영평가 업무</td>
							<td align="left">
								사업자명, 담당자명, 대표전화, 이메일, 주소, 업종, 업태, 사업유형, 생산공장유무, 공장운영형태, 주요상품, 인증정보
							</td>
							<td align="left" rowspan="2">
								- 입점 탈락 이후에도 파트너사 우수한 상품 고려, 롯데마트에서 신규거래 재요청 위한 연락 드리기 위해 탈락일로부터 1년 보관 후 파기합니다.
								<br />
								<br />
								- 동의 철회 접수일로부터 5일 이내 파기합니다.
							</td>
						</tr>
						
						<tr>
							<td align="center">
								롯데정보통신
							</td>
							<td align="center">전산시스템 구축 및 유지보수</td>
							<td align="center">
								입력정보 전체
							</td>
						</tr>
					</table>
				
					<br />
					<br />
					
					<strong style="font-size: 12pt; margin-left: 10px;">[위탁 개인정보 수집 및 이용 동의 거부의 권리]</strong>
					
					<p class="article__text" style="font-size: 10pt; line-height: 1.5em; padding: 10px;">
						위 사항에 대하여 동의를 거부할 권리가 있으며, 거부할 경우 상담 등의 절차가 지연되거나 중단될 수 있습니다.
					</p>
					
					<div style="padding: 10px; text-align: right; font-weight: bold;">
						<input type="checkbox" class="agreeCheck" value="[위탁 개인정보 수집 및 이용 동의 거부의 권리]" /> 동의함 &nbsp;&nbsp;
						<input type="checkbox" class="notAgreeCheck" /> 동의 하지 않음
					</div>
					
					<br /><br />
	
					<div class="article">
						<strong>본인은 상기 입점 상담 등과 관련하여 개인정보 수집항목/목적/보유 및 이용기간을 확인하였으며, 위와 같이 개인정보를 수집 및 이용하는 것에 동의합니다.</strong>
						
						<div style="padding: 10px; text-align: center; font-weight: bold;">
							<input type="checkbox" class="agreeCheck" value="개인정보를 수집 및 이용" /> 동의함 &nbsp;&nbsp;
							<input type="checkbox" class="notAgreeCheck" /> 동의 하지 않음
						</div>
					</div>
					
					<br /><br />
					
				</div>
				
				<div style="padding: 10px; text-align: center;">
					<b><spring:message code='text.srm.field.srmjon0010Notice1' /></b><!-- 위 약관의 내용을 확인하였으며,약관에 대하여 전체 동의합니다. -->
					<input type="checkbox" class="checkAll" />
				</div>
				
				<div style="padding-top: 20px;" align="center">
					<table align="center">
						<tr>
							<td align="center">
								<btn>
									<span class="btn_white"><span><a href="#" onclick="fnUnagree();"><spring:message code='button.srm.notAgree' /></a></span></span><%--동의하지 않음 --%>
									&nbsp;&nbsp;
									<span class="btn_red"><span><a href="#" onclick="fnAgree();"><spring:message code='button.srm.agree' /></a></span></span><%--동의합니다 --%>
								</btn>
							</td>
						</tr>
					</table>
				</div>
			</div>	
		</div>
	</form>
</body>
</html>