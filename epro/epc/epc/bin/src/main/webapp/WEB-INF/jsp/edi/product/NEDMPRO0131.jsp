<%--
	Page Name 	: NEDMPRO0131.jsp
	Description : 이미지사이즈 관리(사이즈 등록 팝업)
	Modification Information
	
	  수정일 			  수정자 				수정내용
	---------- 		---------    		-------------------------------------
	2015.12.01 		an tae kyung	 		최초생성
	
--%>
<%@include file="../common.jsp"%>
<%@ include file="/common/scm/scmCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>

<script>
	$(document).ready(function() {
		//$("#searchButton").click(doSearch);
	});
	
	/* function validate() {
		var vendorTypeCd = '<c:out value="${epcLoginVO.vendorTypeCd}" />';
		if (vendorTypeCd == "06") {
			if($("#srchEntpCode").val() == ""){
				alert('업체선택은 필수입니다.');
				$("#srchEntpCode").focus();
				return false;
			}
		}
		
		return true;
	} */
	
	
	/* function doSearch() {
		if (!validate()) {
			return;
		}
		
		var searchInfo = {};
		
		searchInfo["srchEntpCode"] 		= $("#srchEntpCode").val();
		searchInfo["srchProdDivnCode"] 	= $("#srchProdDivnCode").val();
		searchInfo["srchProdStatus"] 	= $("#srchProdStatus").val();
		searchInfo["srchStartDate"] 	= $("#srchStartDate").val().replaceAll("-", "");
		searchInfo["srchEndDate"] 		= $("#srchEndDate").val().replaceAll("-", "");
		searchInfo["srchSellCode"] 		= $("#srchSellCode").val();
		//console.log(searchInfo);
		//return;
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/product/NEDMPRO0131Select.json"/>',
			data : JSON.stringify(searchInfo),
			success : function(data) {
				//json 으로 호출된 결과값을 화면에 Setting
				//_setTbodyMasterValue(data);
			}
		});	
	} */
	
	/* Data 후처리 */
	/* function _setTbodyMasterValue(json) {
		setTbodyInit("dataListbody");	// dataList 초기화
		
		var data = json.resultList;
		
		if (data.length > 0) {
			$("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
		} else {
			setTbodyNoResult("dataListbody", 20);
		}
	} */
	
	/* 이미지사이즈 변경 */
	function submitSize() {
		if (!confirm('<spring:message code="msg.common.confirm.save" />')) {
			return;
		}
		
		var applyDy = "<c:out value='${param.applyDy}' />";
		if (applyDy == "") {
			applyDy = "0";
		}
		
		var paramInfo = {};
		
		paramInfo["proxyNm"] 	= "MST0940";
		paramInfo["venCd"] 		= "<c:out value='${param.venCd}' />";
		paramInfo["srcmkCd"] 	= "<c:out value='${param.srcmkCd}' />";
		paramInfo["applyDy"] 	= applyDy;
		paramInfo["prodWidth"] 	= $("#prodWidth").val();
		paramInfo["prodHeight"] = $("#prodHeight").val();
		paramInfo["prodLength"] = $("#prodLength").val();
		paramInfo["sizeUnit"] 	= $("#sizeUnit").val();
		//console.log(paramInfo);
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/product/insertMDSizeReserv.json"/>',
			data : JSON.stringify(paramInfo),
			success : function(data) {
				//console.log(data.result);
				//console.log(data.result.RESPCOMMON);
				/* if (data.result.RESPCOMMON.ZPOSTAT == "E") {
					alert('<spring:message code="msg.common.fail.request" />');
				} else {
					alert('<spring:message code="msg.common.success.save" />');
				} */
				
				if (data.msg == "SUCCESS") {
					alert("<spring:message code='msg.common.success.request'/>");
				} else {
					alert("<spring:message code='msg.common.fail.request'/>");								//실패					
				}
				
				//-----팝업 닫고 리스트 부모페이지 리스트 갱신
				opener.doSearch();
				self.close();
			}
		});
	}
</script>

</head>

<body>
	<div id="popup">
		<!-- @ BODY WRAP START 	image list갯수	${fn:length(uploadedOnlineImageList)} -->
		<form name="offlineImageForm" id="offlineImageForm" method="POST">

		
		<input type="hidden" id="applyDy" name="applyDy" value="<c:out value='${param.applyDy}'/>" />
		<input type="hidden" id="srcmkCd" name="srcmkCd" value="<c:out value='${param.srcmkCd}'/>" 	/>
		<!------------------------------------------------------------------ -->
		<!--    title -->
		<!------------------------------------------------------------------ -->
		
		<div id="p_title1">
			<h1>상품 사이즈 등록</h1>
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
					<li class="tit">사이즈 등록</li>
					<li class="btn">
						<a href="javascript:submitSize();" class="btn"><span><spring:message code="button.common.save"/></span></a>
						<a href="javascript:window.close();" class="btn"><span><spring:message code="button.common.close"/></span></a>
					</li>
				</ul>
			</div>
			<!----------------------------------------------------- end of 검색조건 -->
			
			<!-- -------------------------------------------------------- -->
			<!--	검색내역 	-->
			<!-- -------------------------------------------------------- -->
			<div class="wrap_con">
				<div class="bbs_list">
					<ul class="tit">
						<li class="tit">이미지 등록</li>
					</ul>
					
					<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
							<td>
								<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable1">
									<colgroup>
										<col />
										<col />
										<col />
									</colgroup>
									
									<tr>
										<th>가로 </th>
										<th>세로</th>
										<th>높이</th>
										<th>단위</th>
									</tr>
									<tr>
										<td align="center">
											<input type="text" id="prodWidth" name="prodWidth" maxlength="4"/>
										</td>
										<td align="center">
											<input type="text" id="prodHeight" name="prodHeight" maxlength="4" />
										</td>
										<td align="center" >
											<input type="text" id="prodLength" name="prodLength" maxlength="4" />
										</td>
										<td>
											<select id="sizeUnit" name="sizeUnit">
												<option value="MM">MM</option>
											</select>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<!-----------------------------------------------end of 검색내역  -->
		
		</div><!-- popup_contents -->
		<br/>
	
		</form>

	</div>

</body>
</html>
