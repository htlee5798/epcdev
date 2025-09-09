<%--
	Page Name 	: NEDMPRO0130.jsp
	Description : 이미지사이즈 관리
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
		var imagePath		=	"<c:out value='${imagePath}'/>";
		var imageSubPath	=	"<c:out value='${imgSubPath}'/>";

		$(document).ready(function() {

			$("#searchButton").click(doSearch);
		});

		function validate() {
			var vendorTypeCd = '<c:out value="${epcLoginVO.vendorTypeCd}" />';
			if (vendorTypeCd == "06") {
				if($("#srchEntpCode").val() == ""){
					alert('업체선택은 필수입니다.');
					$("#srchEntpCode").focus();
					return false;
				}
			}

			return true;
		}

		/* 페이징 처리를 위한 조회 함수 */
		function goPage(pageIndex) {
			if (!validate()) {
				return;
			}

			var searchInfo = {};

			if ($("#srchEntpCode").val() == "") {
				alert("협력업체 코드는 필수 입니다.");
				return;
			}

			searchInfo["srchEntpCode"] 		= $("#srchEntpCode").val();
			searchInfo["srchProdDivnCode"] 	= $("#srchProdDivnCode").val();
			searchInfo["srchProdStatus"] 	= $("#srchProdStatus").val();
			searchInfo["srchStartDate"] 	= $("#srchStartDate").val().replaceAll("-", "");
			searchInfo["srchEndDate"] 		= $("#srchEndDate").val().replaceAll("-", "");
			searchInfo["srchSellCode"] 		= $("#srchSellCode").val();
			searchInfo["pageIndex"] 		= pageIndex;
			//console.log(searchInfo);
			//return;

			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/buy/NEDMPRO0130Select.json"/>',
				data : JSON.stringify(searchInfo),
				success : function(data) {
					//alert(data.applyDy);
					// 적용일자 Setting(현재일자 + 1)
					$("#hiddenFrm input[name='applyDy']").val(data.applyDy);

					//json 으로 호출된 결과값을 화면에 Setting
					_setTbodyMasterValue(data);
				}
			});
		}

		/* 조회 */
		function doSearch() {
			goPage("1");
			/* if (!validate()) {
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
                url : '<c:url value="/edi/buy/NEDMPRO0130Select.json"/>',
			data : JSON.stringify(searchInfo),
			success : function(data) {
				alert(data.applyDy);
				// 적용일자 Setting(현재일자 + 1)
				$("#hiddenFrm input[name='applyDy']").val(data.applyDy);

				//json 으로 호출된 결과값을 화면에 Setting
				_setTbodyMasterValue(data);
			}
		});	 */
		}

		/* Data 후처리 */
		function _setTbodyMasterValue(json) {
			setTbodyInit("dataListbody");	// dataList 초기화

			var data = json.resultList;
			var currentSecond = json.currentSecond;
			var applyDy	=	json.applyDy;

			if (data.length > 0) {
				for (var i = 0; i < data.length; i++) {
					data[i].count 			= 	i + 1;								//순번
					data[i].imagePath 		= 	imagePath + "/lim/static_root";		//이미지경로

					//이미지경로 폴더명
					if (data[i].imgSeq != "" && data[i].imgSeq != null) {
						data[i].subFolderNm		= 	data[i].imgSeq.substr(0,4) + "/" + data[i].imgSeq.substr(4,4);
					} else {
						data[i].subFolderNm		= 	"";
					}

					data[i].currentSecond	=	currentSecond;
					data[i].applyDy			=	applyDy;

					//가로
					if (data[i].regWidth != null) {
						data[i].width = data[i].regWidth;
					}

					//깊이
					if (data[i].regLength != null) {
						data[i].length = data[i].regLength;
					}

					//높이
					if (data[i].regHeight != null) {
						data[i].height = data[i].regHeight;
					}

				}
				$("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
				$("#paging-ul").html(json.contents);
			} else {
				setTbodyNoResult("dataListbody", 13);
				$("#paging-ul").html("");
			}
		}

		/* POG 이미지 등록 / 변경 팝업 */
		function uploadPOG(prodCd, venCd, srcmkCd, imgSeq, imgNm, variant) {

			//-----변형속성순번[variant가 없을경우에는 '999'로 셋팅해준다. 이유는 변형속성이 없는 상품의 오프라인 이미지 아이디 설정을 위해 사용한다.]
			//-----[2016.02.25 이후로 사용안함 variant는 sql에서 '000'으로 가져오기 때문에 의미 없음]
			/* if (variant.replace(/\s/gi, '') == "") {
                variant = "999";
            } */

			//----- hiddenForm의 이미지 등록 팝업에 사용하기 위해 value 설정
			$("#hiddenFrm input[name='venCd']").val(venCd);
			$("#hiddenFrm input[name='srcmkCd']").val(srcmkCd);
			$("#hiddenFrm input[name='imgSeq']").val(imgSeq);
			$("#hiddenFrm input[name='imgNm']").val(imgNm);
			$("#hiddenFrm input[name='prodCd']").val(prodCd);
			$("#hiddenFrm input[name='variant']").val(variant);
			$("#hiddenFrm input[name='cfmFg']").val("1");	//심사중으로...

			$("#hiddenFrm").attr("target", "_blankPop");
			$("#hiddenFrm").attr("action", "<c:url value='/edi/product/NEDMPRO0132.do'/>");

			var popInfo = window.open('','_blankPop','status=no, resizeable=yes, width=400, height=250, left=480,top=290, scrollbars=no');
			popInfo.focus();
			$("#hiddenFrm").attr("method", "post").submit();
		}


		/* 사이즈 변경 */
		function sizeChange(prodCd, venCd, srcmkCd, applyDy) {

			window.open("", "SIZE_CHANGE", "status=no, resizeable=yes, width=600, height=350, left=700,top=290, scrollbars=no");

			$("#hiddenFrm input[name='venCd']").val(venCd);
			$("#hiddenFrm input[name='srcmkCd']").val(srcmkCd);

			$("#hiddenFrm").attr("target", "SIZE_CHANGE");
			$("#hiddenFrm").attr("method", "POST");
			$("#hiddenFrm").attr("action", "<c:url value='/edi/product/NEDMPRO0131.do' />");
			$("#hiddenFrm").submit();
		}

		/* 사이즈 변경 */
		function wgtChange(prodCd, venCd, srcmkCd) {

			window.open("", "WGT_CHANGE", "status=no, resizeable=yes, width=600, height=350, left=700,top=290, scrollbars=no");

			$("#hiddenFrm input[name='venCd']").val(venCd);
			$("#hiddenFrm input[name='srcmkCd']").val(srcmkCd);
			$("#hiddenFrm input[name='prodCd']").val(prodCd);

			$("#hiddenFrm").attr("target", "WGT_CHANGE");
			$("#hiddenFrm").attr("method", "POST");
			$("#hiddenFrm").attr("action", "<c:url value='/edi/product/NEDMPRO0133.do' />");
			$("#hiddenFrm").submit();
		}
	</script>

	<script id="dataListTemplate" type="text/x-jquery-tmpl">
		<tr class="r1" bgcolor=ffffff>
            <td align=center><c:out value="\${rnum}"/></td>
		<td align=center><c:out value="\${srcmkCd}" /></td>
		<td><c:out value="\${prodNm}" /></td>

		{%if imgNm == null%}	<!-- 이미지가 없을경우 -->
			<td align="center" width="70px"><img name="ImgPOG1f" width="70px"  src="/images/epc/edi/no_photo.gif"></td>
			<td align="center" width="70px"><img name="ImgPOG2f" width="70px"  src="/images/epc/edi/no_photo.gif"></td>
			<td align="center" width="70px"><img name="ImgPOG3f" width="70px"  src="/images/epc/edi/no_photo.gif"></td>
			<td align="center">
				<a href= "javascript:uploadPOG('<c:out value="\${prodCd}"/>',	'<c:out value="\${venCd}"/>',	'<c:out value="\${srcmkCd}"/>',	'<c:out value="\${imgSeq}"/>',	'<c:out value="\${prodImgId}"/>',	'<c:out value="\${variant}"/>');" class="btn">
					<span><spring:message code="button.common.create"/></span>
				</a>
			</td>
			<td align=center>미등록</td>
		{%else%}				<!--이미지가 있을경우 -->
			{%if cfmFg == '3'%}	<!--이미지가 확정일 경우  -->
				<!-- DEV
					<td align="center" width="70px"><img name="ImgPOG1f" width="59px"	height="107px"  src="http://partnerd.lottemart.com/epc/viewImageProductYes.jsp?img_file_nm=<c:out value='\${srcmkCd}'/>.1"></td>
					<td align="center" width="70px"><img name="ImgPOG2f" width="59px"  	height="107px"	src="http://partnerd.lottemart.com/epc/viewImageProductYes.jsp?img_file_nm=<c:out value='\${srcmkCd}'/>.2"></td>
					<td align="center" width="70px"><img name="ImgPOG3f" width="59px"  	height="107px"	src="http://partnerd.lottemart.com/epc/viewImageProductYes.jsp?img_file_nm=<c:out value='\${srcmkCd}'/>.3"></td>
 				-->


				<!-- REAL -->
					<td align="center" width="70px"><img name="ImgPOG1f" width="59px"	height="107px"  src="http://partner.lottemart.com/epc/viewImageProductYes.jsp?img_file_nm=<c:out value='\${srcmkCd}'/>.1"></td>
					<td align="center" width="70px"><img name="ImgPOG2f" width="59px"  	height="107px"	src="http://partner.lottemart.com/epc/viewImageProductYes.jsp?img_file_nm=<c:out value='\${srcmkCd}'/>.2"></td>
					<td align="center" width="70px"><img name="ImgPOG3f" width="59px"  	height="107px"	src="http://partner.lottemart.com/epc/viewImageProductYes.jsp?img_file_nm=<c:out value='\${srcmkCd}'/>.3"></td>


			{%else%}			<!--이미지가 확정 아닌 경우 -->
				<td align="center" width="70px"><img name="ImgPOG3f" width="59px"  	height="107px"	src="<c:out value='\${imagePath}'/>/images/edi/offline/<c:out value='\${subFolderNm}'/>/<c:out value='\${imgNm}'/>.1.jpg?currentSecond=<c:out value='\${currentSecond}'/>"></td>
				<td align="center" width="70px"><img name="ImgPOG3f" width="59px"  	height="107px"	src="<c:out value='\${imagePath}'/>/images/edi/offline/<c:out value='\${subFolderNm}'/>/<c:out value='\${imgNm}'/>.2.jpg?currentSecond=<c:out value='\${currentSecond}'/>"></td>
				<td align="center" width="70px"><img name="ImgPOG3f" width="59px"  	height="107px"	src="<c:out value='\${imagePath}'/>/images/edi/offline/<c:out value='\${subFolderNm}'/>/<c:out value='\${imgNm}'/>.3.jpg?currentSecond=<c:out value='\${currentSecond}'/>"></td>
			{%/if%}
			<td align="center">
				{%if cfmFg == '0' || cfmFg == '1'%}		<!-- 심사중 -->
					<font color = "blue">
						<a href= "javascript:uploadPOG('<c:out value="\${prodCd}"/>',	'<c:out value="\${venCd}"/>', '<c:out value="\${srcmkCd}"/>', '<c:out value="\${imgSeq}"/>' , '<c:out value="\${prodImgId}"/>',	'<c:out value="\${variant}"/>');"	class="btn">
							<span>수정&변경</span>
						</a>
					</font>
				{%elif cfmFg == '3'%}					<!-- 확정 -->
					<a href= "javascript:uploadPOG('<c:out value="\${prodCd}"/>',	'<c:out value="\${venCd}"/>',	'<c:out value="\${srcmkCd}"/>', '<c:out value="\${imgSeq}"/>' , '<c:out value="\${prodImgId}"/>',	'<c:out value="\${variant}"/>');"	class="btn">
						<span>수정&변경</span>
					</a>
				{%elif cfmFg == '2'%}					<!-- 반려-->
					<a href= "javascript:uploadPOG('<c:out value="\${prodCd}"/>',	'<c:out value="\${venCd}"/>',	'<c:out value="\${srcmkCd}"/>', 	'<c:out value="\${imgSeq}"/>' , '<c:out value="\${prodImgId}"/>',	'<c:out value="\${variant}"/>');"	 class="btn">
						<span><spring:message code="button.common.return"/></span>
					</a>
				{%/if%}
			</td>
			<td align="center">
				{%if cfmFg == '0' || cfmFg == '1'%}		<!-- 심사중 -->
					<font color = "blue">
						심사중
					</font>
				{%elif cfmFg == '3'%}					<!-- 확정 -->
					<font color = "green">
						<strong>확정</strong>
					</font>
				{%elif cfmFg == '2'%}					<!-- 반려-->
					<font color = "red">
						<strong>반려</strong>
					</font>
				{%/if%}
			</td>
		{%/if%}

		<td align="center">
			{%if cfmReasonFg == '1'%}
				여백을 잘라 야함
			{%elif cfmReasonFg == '2'%}
				광고용 사진금지
			{%elif cfmReasonFg == '3'%}
				비스듬히 촬영불가
			{%elif cfmReasonFg == '4'%}
				이미지 방향오류
			{%elif cfmReasonFg == '5'%}
				기타
				<br/>
		<c:out value="\${cfmReasonTxt}"/>
		{%elif cfmReasonFg == '6'%}
            이미지누락
        {%else%}
            -
        {%/if%}
    </td>

    {%if varAttNm == null%}
        <td align="center"> - </td>
    {%else%}
        <td align="left"><c:out value="\${varAttNm}"/></td>
		{%/if%}
		<td align=center>
			<font color="green"><c:out value="\${width}" /></font>*
			<font color="green"><c:out value="\${length}" /></font>*
			<font color="green"><c:out value="\${height}" /></font>
		</td>

		<!--
		<td align="center">
			{%if regWidth == null%}
				예약없음
			{%else%}
				<font color="blue"><c:out value="\${regWidth}" /></font>*
				<font color="blue"><c:out value="\${regLength}" /></font>*
				<font color="blue"><c:out value="\${regHeight}" /></font><br>(예약중)
			{%/if%}
		</td>
		 -->
		<td align=center>
			<a href="javascript:sizeChange('<c:out value="\${prodCd}"/>',	'<c:out value="\${venCd}"/>', '<c:out value="\${srcmkCd}"/>', '<c:out value="\${applyDy}"/>' );" class="btn">
				<span><spring:message code="button.common.update"/></span>
			</a>
			<!--등록이미지-->
		</td>
		<td align=center>
		<c:out value="\${wg}"/>
		</td>
    	<td align=center>
		<c:out value="\${pclWg}"/>
		</td>
		<td align=center>
		<c:out value="\${pclWgUnit}"/>
		</td>
		<td align=center>
			<a href="javascript:wgtChange('<c:out value="\${prodCd}"/>',	'<c:out value="\${venCd}"/>', '<c:out value="\${srcmkCd}"/>', '<c:out value="\${applyDy}"/>' );" class="btn">
				<span><spring:message code="button.common.update"/></span>
			</a>
			<!--등록이미지-->
		</td>
	</tr>
	</script>

</head>

<body>
<div id="content_wrap">
	<div>
		<!--	@ BODY WRAP START 	-->
		<form name="searchForm" method="post" action="#">
			<input type="hidden" id="entpCode" name="entpCode" value="${param.entpCode}" />
			<div id="wrap_menu">
				<!--	@ 검색조건	-->
				<div class="wrap_search">
					<!-- 01 : search -->
					<div class="bbs_search">
						<ul class="tit">
							<li class="tit">전체상품현황</li>
							<li class="btn">
								<a href="#" id="searchButton" class="btn"><span><spring:message code="button.common.inquire" /></span></a>
							</li>
						</ul>

						<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
							<input type="hidden" id="storeVal" name="storeVal" value="<c:out value="${param.storeVal}" />" />
							<input type="hidden" id="productVal" name="productVal" />

							<colgroup>
								<col style="width: 80px;" />
								<col style="width: 200px;" />
								<col style="width: 80px;" />
								<col />
								<col style="width: 80px;" />
								<col />
							</colgroup>
							<tr>
								<th><span class="star">*</span>협력업체</th>
								<td colspan="3">
									<c:choose>
										<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
											<c:if test="${not empty param.srchEntpCode}">
												<input type="text" name="srchEntpCode" id="srchEntpCode" readonly="readonly" value="<c:out value="${param.srchEntpCode}" />" style="width: 40%;" />
											</c:if>
											<c:if test="${empty param.srchEntpCode}">
												<input type="text" name="srchEntpCode" id="srchEntpCode" readonly="readonly" value="<c:out value="${epcLoginVO.repVendorId}" />" style="width: 40%;" />
											</c:if>
											<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
										</c:when>
										<c:when test="${epcLoginVO.vendorTypeCd != '06'}">
											<c:if test="${not empty param.srchEntpCode}">
												<html:codeTag objId="srchEntpCode" objName="srchEntpCode" width="150px;" selectParam="<c:out value='${param.srchEntpCode}'/>" dataType="CP" comType="SELECT" formName="form" defName="전체" />
											</c:if>
											<c:if test="${ empty param.srchEntpCode}">
												<html:codeTag objId="srchEntpCode" objName="srchEntpCode" width="150px;" selectParam="<c:out value='${epcLoginVO.repVendorId}'/>" dataType="CP" comType="SELECT" formName="form" defName="전체" />
											</c:if>
										</c:when>
									</c:choose>
								</td>
								<%-- <th>상품구분</th>
                                <td>
                                    <select id="srchProdDivnCode" name="srchProdDivnCode">
                                        <option value="1" <c:if test="${param.srchProdDivnCode eq '1'}">selected</c:if>>규격</option>
                                        <option value="5" <c:if test="${param.srchProdDivnCode eq '5'}">selected</c:if>>패션</option>
                                    </select>
                                </td> --%>
								<th>이미지 상태</th>
								<td colspan="3">
									<select id="srchProdStatus" name="srchProdStatus">
										<option value="">전체</option>
										<option value="3">이미지 확정상품</option>
										<option value="1">이미지 심사중상품</option>
										<option value="2">이미지 반려상품</option>
									</select>
								</td>
							</tr>
							<tr>
								<th>등록기간</th>
								<td colspan=3>
									<input type="text" class="day" id="srchStartDate" name="srchStartDate" value="<c:out value="${srchStartDate}" />" style="width: 80px;">
									<img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchStartDate');" style="cursor: hand;" />
									~
									<input type="text" class="day" id="srchEndDate" name="srchEndDate" value="<c:out value="${srchEndDate}" />" style="width: 80px;">
									<img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchEndDate');" style="cursor: hand;" />
								</td>
								<th>판매코드</th>
								<td colspan="3">
									<input type="text" class="day" id="srchSellCode" name="srchSellCode" value="${param.srchSellCode}" style="width: 120px;">
								</td>
							</tr>

						</table>
					</div>
					<!-- 1검색조건 // -->
				</div>

				<!--	2 검색내역 	-->
				<div class="wrap_con">
					<!-- list -->
					<div class="bbs_list">
						<ul class="tit">
							<li class="tit">이미지 / 사이즈 수정</li>
						</ul>

						<div style="width:100%; height:458px;overflow-x:hidden; overflow-y:scroll; overflow-x:scroll;  table-layout:fixed;white-space:nowrap;">
							<table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=1800px; bgcolor=efefef>
								<colgroup>
									<col style="width:30px"/>
									<col style="width:100px"/>
									<col style="width:300px"/>

									<col style="width:70px"/>
									<col style="width:70px"/>
									<col style="width:70px"/>

									<col style="width:60px"/>
									<col style="width:60px;"/>
									<col style="width:100px"/>
									<col />

									<col style="width:100px;">
									<!-- <col style="width:100px;"/> -->
									<col style="width:60px"/>

									<col style="width:60px"/>
									<col style="width:60px"/>
									<col style="width:40px"/>
									<col style="width:60px"/>
								</colgroup>

								<tr bgcolor="#e4e4e4">
									<th rowspan="2">No.</th>
									<th rowspan="2">판매코드</th>
									<th rowspan="2">상품명</th>
									<th colspan="7">이미지</th>
									<th colspan="2">사이즈(mm)</th>
									<th colspan="4">중량</th>
								</tr>

								<tr bgcolor="#e4e4e4" align=center>
									<th>정면</th>
									<th>옆면</th>
									<th>윗면</th>
									<th>등록<br>및 수정</th>
									<th>상태</th>
									<th>반려<br>사유</th>
									<th>변형속성</th>
									<th>가로*깊이*높이</th>
									<!-- <th>예약된<br>가로*깊이*높이</th> -->
									<th>변경</th>
									<th>총중량</th>
									<th>순중량</th>
									<th>단위</th>
									<th>변경</th>
								</tr>

								<tbody id="dataListbody" />
							</table>
						</div>




					</div>
				</div>
			</div>

			<!-- Pagging Start ---------->
			<div id="paging_div">
				<ul class="paging_align" id="paging-ul" style="width: 400px"></ul>
			</div>
			<!-- Pagging End ---------->

		</form>
	</div>

	<form id="hiddenFrm" name="hiddenFrm" method="POST">
		<input type="hidden" id="prodCd"	name="prodCd"			 />
		<input type="hidden" id="variant"	name="variant"			 />
		<input type="hidden" id="venCd" 	name="venCd" 	value="" />
		<input type="hidden" id="srcmkCd" 	name="srcmkCd" 	value="" />
		<input type="hidden" id="applyDy" 	name="applyDy" 	value="" />
		<input type="hidden" id="imgSeq" 	name="imgSeq" 	 		 />
		<input type="hidden" id="imgNm"		name="imgNm"  			 />
		<input type="hidden" id="cfmFg"		name="cfmFg"  			 />
		<input type="hidden" id="proxyNm"	name="proxyNm"	value="MST0820" />
	</form>

	<!-- footer -->
	<div id="footer">
		<div id="footbox">
			<div class="msg" id="resultMsg"></div>
			<div class="notice"></div>
			<div class="location">
				<ul>
					<li>홈</li>
					<li>상품</li>
					<li>상품현황관리</li>
					<li class="last">이미지사이즈 관리</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>

</html>
