<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<title>롯데마트 입점상담</title>


<script language="javascript">
<!--
	$(document).ready(function() {
		$("input.imageUpload").change(function() {
			var fileExtension = getFileExtension($(this).val()).toLowerCase();
			if (fileExtension != "gif" & fileExtension != "jpg") {
				alert("gif와 jpg파일만 지원합니다!!");
				//resetImage($(this).get(0));
				$(this).val('');
				$(this).prev().val('');
			} else {
				$(this).prev().val($(this).val());
			}
		});

		/*
		$("input.docUpload").change(function() {
			var fileExtension = getFileExtension($(this).val()).toLowerCase();
			if(fileExtension != "doc" & 
					fileExtension != "xls" & 
					fileExtension != "ppt" ) {
				alert('올바른 파일을 입력하세요!!\n첨부 문서는 아래 문서만 가능합니다.\n(MS워드, 엑셀, 파워포인트)');
				$(this).val('');
				$(this).prev().val('');
			} else {
				$(this).prev().val($(this).val());
			}
		});
		 */

	});
	//-->

	
	$(function() {
	
	//팀
//	$("#teamCd").change(selectL1ListApply);
	

		$("#teamCd").change(function() {
			$.getJSON("<c:url value='/edi/consult/selectL1ListApply.do'/>",
						{
							groupCode: $(this).val() 
						}, function(j){
			      var options = '';

			      if(j.length=='0'){
			    	  options = '<option value=all>선택</option>';
			      }else{
			    	  for (var i = 0; i < j.length; i++) {
						    if(i == 0) {
						    	options += '<option value=all>선택</option>';
							}  
					        options += '<option value="' + j[i].l1Cd + '">' + j[i].l1Nm + '</option>';
				      }
			      }
			      

			      $("#l1GroupCode option").remove();
			      $("#l1GroupCode").html(options);
			    });
		});
		
	
});
	


	function checkStep3Submit() {

		var prodArray = "";
		$('input:checkbox[name=prodArraySeq]:checked').each(function() {

			prodArray = prodArray + $(this).val() + "/";

		});

		document.MyForm.prodArray.value = prodArray;

		var email1 = $("input[name=email1]").val();
		var email2 = $("input[name=email2]").val();
		

		var email = email1 + "@" + email2;
		$("input[name=email]").val(email);

		
	

		
		var l1Cd = MyForm.l1GroupCode.value;
		$("input[name=l1Cd]").val(l1Cd);
	
		
		
		if (!check_submit()) {
			return;
		}

		//	if(check_submit_4()) {
		//	alert(document.MyForm.prodArray.value);
		//	return;

		//alert("상품이미지와 첨부문서가 제대로 나오는지 반드시 확인해주세요. 이미지가 나오지 않는 경우 신청을 누르고 다시 작성해주세요 문의는 2145-8426 입점안내입니다.");
		$("form[name=MyForm]").submit();
		//alert("폼 전송");
		//	}

	}

	function openPopup(openUrl, popTitle) {
		winstyle = "dialogWidth=300px; dialogHeight:220px; center:yes; status: no; scroll: yes; help: no";
		result = window.showModalDialog(openUrl, window, winstyle);
	}

	function img_view(imgurl, imgpath) {

		//alert("img_view")

		var w = "600";
		var h = "600";
		var width = "600";
		var height = "600";

		var path = "upload/" + imgurl;

		var openWidth = "650";
		var openHeight = "600";

		if (openWidth > 800)
			openWidth = 800;
		if (openHeight > 800)
			openHeight = 800;

		window.open('${ctx}/edi/consult/step3ImagePopup.do?img=' + imgurl
				+ '&imgpath=' + imgpath + '&h=' + h, 'imgwin', 'width='
				+ openWidth + ', height=' + openHeight
				+ ', scrollbars=yes, resizable=yes');
	}

	function img_viewNew(imgurl, imgpath) {

//		alert("imgurl:" + imgurl)
//		alert("imgpath:" + imgpath)

		var w = "600";
		var h = "600";
		var width = "600";
		var height = "600";

		var path = "upload/" + imgurl;

		var openWidth = "650";
		var openHeight = "600";

		if (openWidth > 800)
			openWidth = 800;
		if (openHeight > 800)
			openHeight = 800;

		window.open('${ctx}/edi/consult/step3ImagePopupNew.do?img=' + imgurl
				+ '&imgpath=' + imgpath + '&h=' + h, 'imgwin', 'width='
				+ openWidth + ', height=' + openHeight
				+ ', scrollbars=yes, resizable=yes');
	}

	function downloadConsultDocumnet(vendorBusinessNo) {
		window.open(
				'${ctx}/edi/consult/step3DocumnetPopup.do?vendorBusinessNo='
						+ vendorBusinessNo, 'docwin',
				'width=200, height=300, scrollbars=yes, resizable=yes');
	}

	function check_submit_4() {

		var MyForm = document.getElementsByName("MyForm")[0];
		var resultFlag = true;
		var answeredCount1 = 0;
		var notAnsweredCount1 = 0;
		var totalCount = 2;
		$("input.field_1").each(function(index) {
			if ($(this).val() == '' || $(this).val().length == 0) {
				notAnsweredCount1++;
			} else {
				answeredCount1++;
			}
		});

	//	alert("answeredCount1:" + answeredCount1 + " totalCount:" + totalCount);

		if (answeredCount1 != totalCount && notAnsweredCount1 != totalCount) {
			alert("첫번째 항목을 모두 입력해주시기 바랍니다.");
			return false;
		}

		var answeredCount2 = 0;
		var notAnsweredCount2 = 0;
		$("input.field_2").each(function(index) {
			if ($(this).val() == '' || $(this).val().length == 0) {
				notAnsweredCount2++;
			} else {
				answeredCount2++;
			}
		});

	//	alert("answeredCount2:" + answeredCount2 + " totalCount:" + totalCount);
		if (answeredCount2 != totalCount && notAnsweredCount2 != totalCount) {
			alert("두번째 항목을 모두 입력해주시기 바랍니다.");
			return false;
		}

		var answeredCount3 = 0;
		var notAnsweredCount3 = 0;
		$("input.field_3").each(function(index) {
			if ($(this).val() == '' || $(this).val().length == 0) {
				notAnsweredCount3++;
			} else {
				answeredCount3++;
			}
		});

	//	alert("answeredCount3:" + answeredCount3 + " totalCount:" + totalCount);
		if (answeredCount3 != totalCount && notAnsweredCount3 != totalCount) {
			alert("세번째 항목을 모두 입력해주시기 바랍니다.");
			return false;
		}

		if (notAnsweredCount1 == 2 && notAnsweredCount2 == 2
				&& notAnsweredCount3 == 2) {
			alert("세 항목중 적어도 하나는 입력해야 합니다.");
			return false;
		}

		return true;
	}

	function check_submit() {
		var MyForm = document.getElementsByName("MyForm")[0];

		if (MyForm.hndNm.value.length == 0) {
			strMsg = "msg.no.company.name";
			popUrl = "${ctx }/epc/edi/consult/messagePopup.do?strMsg=" + strMsg
					+ "&objFocus=hndNm";
			openPopup(popUrl, '에러');

			return false;
		}
		
		if (MyForm.teamCd.value.length == 0) {
			strMsg = "msg.teamnm";
			popUrl = "${ctx }/epc/edi/consult/messagePopup.do?strMsg=" + strMsg
					+ "&objFocus=teamCd";
			openPopup(popUrl, 'ID');

			return false;
		}
		
		
		if (MyForm.l1GroupCode.value == "all" || MyForm.l1GroupCode.value == "") {
			strMsg = "msg.l1Nm";
			popUrl = "${ctx }/epc/edi/consult/messagePopup.do?strMsg=" + strMsg
					+ "&objFocus=l1GroupCode";
			openPopup(popUrl, 'ID');

			return false;
		}
		
		//alert(MyForm.l1GroupCode.value);
	
		
      
		
		
			
	//	alert(aaa);
	//	return;
		
		
		//대표자명
		if (MyForm.ceoNm.value.length == 0) {
			strMsg = "msg.ceo.name";
			popUrl = "${ctx }/epc/edi/consult/messagePopup.do?strMsg=" + strMsg
					+ "&objFocus=ceoNm";
			openPopup(popUrl, 'ID');
			return false;
		}
	
		//대표전화번호
		if ((MyForm.officetel1.value.length == 0)
				|| (MyForm.officetel2.value.length == 0)
				|| (MyForm.officetel3.value.length == 0)) {
			var targetObject = "";

			if (MyForm.officetel1.value.length == 0) {
				targetObject = "officetel1";
			} else if (MyForm.officetel2.value.length == 0) {
				targetObject = "officetel2";
			}

			else if (MyForm.officetel3.value.length == 0) {
				targetObject = "officetel3";
			} else {
			}

			strMsg = "msg.office.telephone";
			popUrl = "${ctx }/epc/edi/consult/messagePopup.do?strMsg=" + strMsg
					+ "&objFocus=" + targetObject;
			openPopup(popUrl, 'ID');

			return false;
		}

		if (MyForm.email1.value.length == 0 || MyForm.email2.value.length == 0) {
			var targetObject = "";

			if (MyForm.email1.value.length == 0) {
				targetObject = "email1";
			}

			else if (MyForm.email2.value.length == 0) {
				targetObject = "email2";
			} else {
			}

			strMsg = "msg.email";
			popUrl = "${ctx }/epc/edi/consult/messagePopup.do?strMsg=" + strMsg
					+ "&objFocus=" + targetObject;
			openPopup(popUrl, 'ID');

			return false;
		}

		return true;
	}

	function Nextfocus(lenth, name, a) {
		if (name == "tel1") {
			for (i = 0; i < a.value.length; i++) {
				if (a.value.charAt(i) < "0" || a.value.charAt(i) > "9") {
					alert("<spring:message code='msg.common.error.noNum'/>");
					a.focus();
					a.value = "";
					return;
				}
			}
			if (lenth == 3) {
				document.MyForm.tel2.focus();
			}
		} else if (name == "tel2") {

			for (i = 0; i < a.value.length; i++) {
				if (a.value.charAt(i) < "0" || a.value.charAt(i) > "9") {
					alert("<spring:message code='msg.common.error.noNum'/>");
					a.focus();
					a.value = "";
					return;
				}
			}
			if (lenth == 4) {
				document.MyForm.tel3.focus();
			}
		} else if (name == "tel3") {

			for (i = 0; i < a.value.length; i++) {
				if (a.value.charAt(i) < "0" || a.value.charAt(i) > "9") {
					alert("<spring:message code='msg.common.error.noNum'/>");
					a.focus();
					a.value = "";
					return;
				}
			}
			if (lenth == 4) {
				//document.MyForm.fax_no1.focus();
			}

		} else if (name == "officetel1") {
			for (i = 0; i < a.value.length; i++) {
				if (a.value.charAt(i) < "0" || a.value.charAt(i) > "9") {
					alert("<spring:message code='msg.common.error.noNum'/>");
					a.focus();
					a.value = "";
					return;
				}
			}
			if (lenth == 3) {
				document.MyForm.officetel2.focus();
			}
		} else if (name == "officetel2") {
			for (i = 0; i < a.value.length; i++) {
				if (a.value.charAt(i) < "0" || a.value.charAt(i) > "9") {
					alert("<spring:message code='msg.common.error.noNum'/>");
					a.focus();
					a.value = "";
					return;
				}
			}
			if (lenth == 4) {
				document.MyForm.officetel3.focus();
			}
		} else if (name == "officetel3") {
			for (i = 0; i < a.value.length; i++) {
				if (a.value.charAt(i) < "0" || a.value.charAt(i) > "9") {
					alert("<spring:message code='msg.common.error.noNum'/>");
					a.focus();
					a.value = "";
					return;
				}
			}
			if (lenth == 4) {
				//document.MyForm.home_addr.focus();
			}
		}
	}
</script>

</head>

<body>

	<form name="MyForm"	action="${ctx }/epc/edi/consult/insertApplyConfirm.do" method="post" enctype="multipart/form-data" ID="Form1">		
			<input type="hidden" name="bmanNo"	value="${vendorSession.vendor.bmanNo}" /> 
			<input type="hidden" id="gubun" name="gubun" value="${gubun }" /> 
			<input type="hidden" id=prodArray name="prodArray" value="" /> 
			<input type="hidden" name="email" value="" />
			<input type="hidden" name="l1Cd" value="" />
		<div id="wrap">
			<div id="con_wrap">
				<div class="con_area">
					<br>
					
					<div class="s_title">
						<h2>상담신청 희망부서</h2>						
					</div>
					
					<div class="tb_form_comm">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<colgroup>
								<col width="140px">
								<col width="300px">
								<col width="130px">
								<col width="*">
							</colgroup>
							<tbody>			
							<tr>
							<td colspan="4" align="right">
										(상담희망부서 관련 문의처 : 02)2145-8000)
									</td>
									
							</tr>				
						<tr>
								<th><p class="check">희망부서</p></th>
									<td>
										<div class="td_p">

											<select id="teamCd" name="teamCd">
												<option value="">선택</option>
												<c:forEach items="${consultTeamList}" var="code" begin="0">
													<option value="${code.TEAM_CD}"
														<c:if test="${vendor.teamCd eq code.TEAM_CD }"> selected</c:if>>${code.TEAM_NM }</option>
												</c:forEach>
											</select>
									</td>
				
			
					
				<th><p class="check">대분류</p></th>
						<td >
							<select id="l1GroupCode" name="l1GroupCode" >
									<c:if test="${com == 'none'}">
										<option value="all">선택</option>
									</c:if>
								<c:forEach items="${l1GroupList}" var="l1Group" varStatus="indexL1" >
									<c:if test="${indexL1.index == 0 }">
										<option value=all <c:if test="${l1Group.l1Cd eq ''}"> selected</c:if>>선택</option>
									</c:if>
									<option value="${l1Group.l1Cd}" <c:if test="${l1Group.l1Cd eq vendor.l1Cd }"> selected</c:if> >${l1Group.l1Nm} </option> 
								</c:forEach>
							</select>
						</td>							
						</tr>
						
						</tbody>
					</table>
					</div>
					
					<br><br>						
					<div class="s_title">
						<h2>상담신청인 내역</h2>							
					</div>					
					<div class="tb_form_comm">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
							
							<colgroup>
								<col width="140px">
								<col width="300px">
								<col width="130px">
								<col width="*">
							</colgroup>
							<tbody>
								<tr>
							
									<th ><p>사업자등록번호</p></th>
									<td ><div class="td_p">
											<span class="t_gray t_12">
											<input type="text" name="bmanNo" class="txt"	value="${ vendorSession.vendor.bmanNo }" ID="Text1" readonly	style="width: 232px;"></span></div>
									</td>
								</tr>
								
								<tr>
								
								<th width="130px"><p class="check">상호명</p></th>
									<td width="300px">
										<div class="td_p">
											<span class="input_txt"><span>
											<input	maxlength="15" type="text" class="txt" name="hndNm"	value="${vendor.hndNm}" style="width: 232px;"	onkeyup="javascript:cal_byte(this, '50', '상호명');"></span></span>
										</div>
									</td>
									
									<th><p class="check">대표자명</p></th>
									<td><div class="td_p">
											<span class="input_txt"><span><input
													maxlength="15" type="text" name="ceoNm" class="txt"
													value="${vendor.ceoNm}" style="width: 232px;"
													onkeyup="javascript:cal_byte(this, '50', '대표자명');"></span></span>
										</div></td>
								</tr>

								<tr>
									

<!-- 
											<th><p class="check">분류</p></th>
											<td>
												<div class="td_p">
													<select class="select" name="L1Cd">
														<option value="">선택</option>
														<c:forEach items="${L1cdData}" var="L1cdData" begin="0">
															<option value="${L1cdData.L1_CD}">${L1cdData.L1_NM}</option>
														</c:forEach>
													</select>

												</div>
											</td>
-->
									</td>

								</tr>
								<tr>
								
								</tr>
								<tr>
									<th><p class="check">연락 받으실 번호</p></th>
									<td>
										<div class="td_p">
											<span class="input_txt"><span>
												<input	type="text" name="officetel1" class="txt"	value="${vendor.officetel1}" maxlength="3"	onKeyUp="Nextfocus(this.value.length,this.name,this);"	style="width: 54px;"></span></span> - <span class="input_txt"><span>
												<input	type="text" name="officetel2" class="txt"   value="${vendor.officetel2}" maxlength="4" onKeyUp="Nextfocus(this.value.length,this.name,this);"	style="width: 54px;"></span></span> - <span class="input_txt"><span>
												<input	type="text" name="officetel3" class="txt"	value="${vendor.officetel3}" maxlength="4"	onKeyUp="Nextfocus(this.value.length,this.name,this);"	style="width: 54px;"></span></span>
										</div>
									</td>
									<th><p class="check">E-mail</p></th>
									<td>
										<div class="td_p">
											<span class="input_txt"><span>											
											<input type="text" class="txt" name="email1" value="${vendor.email1}" style="width: 90px;"></span></span> @ <span class="input_txt"><span>
											<input type="text"	class="txt" name="email2" value="${vendor.email2}" style="width: 110px;"></span></span>
										</div>
									</td>				
								</tr>
								</tbody>
								</table>
								
								<br><br>
								<div class="s_title">
						<h2>상담신청 상품 내역 </h2>						
					</div>
				
					<div class="tb_form_comm">
					<table cellpadding="0" cellspacing="0" border="0" width="100%">
										<colgroup>
											<col width="150px">
											<col width="150px">
											<col width="150px">
											<col width="150px">
											<col width="150px">
										</colgroup>
										<tr>
											<th class="t_center num">번호</th>
											<th class="t_center num">상품명</th>
											<th colspan="3" class="t_center num">이미지(사진첨부)
											
										 <br> <font color="red">반드시 1M이하 용량 / JPG파일로 올려주세요.</font>
											
											</th>
											<th class="t_center num"></th>



							</tr>
								<c:if test="${fn:length(vendorProductList) > 0 }">
									<c:forEach var="vendorProduct" items="${vendorProductList}"	varStatus="status">
									<tr>
									<td class="t_center num"><span class="t_12">${vendorProduct.seq }</span></td>
										<input type="hidden" name="seq" value="${vendorProduct.seq }" ID="Hidden1">
									<td class="t_center no_b_line"><span class="input_txt"><span>
										<input	type="text" name="productName"	class="txt field_${vendorProduct.seq}"	value="${vendorProduct.productName }" maxlength="20" style="width: 130px;"></span></span>
									</td>
									<td colspan="3" align="left"><input type="hidden"	name="uploadFile${status.count }"	class="field_${vendorProduct.seq}"	value="${vendorProduct.attachFileCode }" /> 
										&nbsp;&nbsp;&nbsp;
										<input	type="file" name="attachFile${status.count }"	class="imageUpload" value="" style="width: 250px; height: 18px;"> 
										<c:if	test="${ not empty vendorProduct.attachFileCode }">
											<a href="javascript:img_view('${vendorProduct.attachFileCode}', '${vendorProduct.atchFileFolder}')"	class="btn">										
											이미지보기 	
											</a>
										</c:if>
									</td>
									</tr>
									</c:forEach>
							 	</c:if>



						<c:if test="${fn:length(vendorProductList) == 0 }">
											<tr>
												<td class="t_center num"><span class="t_12">1</span></td>
													<input type="hidden" name="seq" value="1" ID="Hidden1">
												<td class="t_center no_b_line"><span class="input_txt"><span>
													<input type="text" name="productName" class="txt field_1"	value="" maxlength="20" style="width: 130px;"></span></span>
												</td>
												<td class="t_center no_b_line" colspan="3">
													<input	type="hidden" name="uploadFile1" class="field_1" value="" />
													<input type="file" name="attachFile1" value=""	class="imageUpload" style="width: 250px; height: 18px;">
												</td>
											</tr>
										</c:if>
						<c:if	test="${fn:length(vendorProductList) == 0 || fn:length(vendorProductList) == 1   }">
											<tr>
												<td class="t_center num"><span class="t_12">2</span></td>
												<input type="hidden" name="seq" value="2" ID="Hidden1">
												<td class="t_center no_b_line"><span class="input_txt"><span>
												<input	type="text" name="productName" class="txt field_2"	value="" maxlength="20" style="width: 130px;"></span></span></td>
												<td class="t_center no_b_line" colspan="3">
												<input	type="hidden" name="uploadFile2" value="" class="field_2" />
													<input type="file" name="attachFile2" value=""	class="imageUpload" style="width: 250px; height: 18px;">
												</td>
											</tr>
										</c:if>

					<c:if	test="${ fn:length(vendorProductList) == 0 || fn:length(vendorProductList) == 1  || fn:length(vendorProductList) == 2 }">
										<tr>
											<td class="t_center num"><span class="t_12">3</span></td>
													<input type="hidden" name="seq" value="3" ID="Hidden1">
											<td class="t_center no_b_line">
												<span class="input_txt"><span>
													<input type="text" name="productName" class="txt field_3" value="" maxlength="20" style="width: 130px;">
												</span></span>
											</td>
											<td class="t_center no_b_line" colspan="3">
												<input type="hidden" name="uploadFile3" class="field_3"     value="" />
												<input type="file"   name="attachFile3" class="imageUpload" value="" style="width: 250px; height: 18px;">
											</td>
											</tr>
										</c:if>
									</table>								
								</div>
								
					<br><br>
					<div class="s_title mt20">
						<h2>별첨 자료</h2>
					</div>			
					<div class="tb_form_comm">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<colgroup>
							<col width="140px">
								<col width="300px">
								<col width="130px">
								<col width="*">						
						</colgroup>
						<tbody>			
						<tr>					
						
						<th width="140px"><p>사업소개서</p></th>									
									<td colspan="3">
										<div class="td_p">
											 								
												<input type="hidden" name="vendorFile" value="${vendor.attachFileCode }" /> 	
												<input type="file" name="vendorAttachFile" class="docUpload" value="" style="width: 250px; height: 18px;">
											<c:if test="${not empty vendor.attachFileCode }">
											<span style="margin-left: 50px"></span>&nbsp;<a	href="javascript:downloadConsultDocumnet('${vendor.bmanNo}')">문서다운로드</a>
											</c:if>
											<br>
											<font color="red">첨부문서는 2M이하 용량 / PPT, PPTX, PDF 파일로 올려주세요.</font>	
											
										</div>
									</td>							
								</tr>
						</tbody>
					</table>
					</div>				

<br><br>
					<div class="s_title mt20">
						<h2>특이사항</h2>
					</div>			
					<div class="tb_form_comm">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<colgroup>
							<col width="140px">
								<col width="300px">
								<col width="130px">
								<col width="*">						
						</colgroup>
						<tbody>			
						<tr>					
						
						<td colspan="4" rowspan="5">
						<div class="box_etc_input">
							<textarea cols="122" rows="5" name="content"	style="width: 828px; height: 40px;">${vendor.pecuSubjectContent}</textarea>
						</div>						
						</tr>
					
						</tbody>
					</table>
					</div>

								<!-- button -->
								<div class="btn_c_wrap mt30">
									<span class="btn_red"><span>
									<a	href="javascript:checkStep3Submit();"><spring:message code='button.common.confirm' /></a></span></span>
								</div>
								<!--// button -->

<script>

<c:if test="${not empty vendor.l1Cd}">
//selectL1ListApply();
</c:if>
</script>

								</form>
</body>
</html>