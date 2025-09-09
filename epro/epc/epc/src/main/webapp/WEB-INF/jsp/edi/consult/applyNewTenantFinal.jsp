<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<title>롯데마트 입점상담</title>

<script language="javascript">
	function downloadConsultDocumnet(vendorBusinessNo) {
		window.open(
				'${ctx}/edi/consult/step3DocumnetPopup.do?vendorBusinessNo='
						+ vendorBusinessNo, 'docwin',
				'width=200, height=300, scrollbars=yes, resizable=yes');
	}
	

function pwdcheck()	{		
		
		var upw = document.MyForm.password.value;	
		
		if(!/^[a-zA-Z0-9]{8,12}$/.test(upw))
		 {
		  alert('비밀번호는 숫자와 영문자 조합으로 8~12자리를 사용해야 합니다.');		
		 return false;
	 	}		
		 var chk_num = upw.search(/[0-9]/g);
		 var chk_eng = upw.search(/[a-z]/ig);	 
		 if(chk_num < 0 || chk_eng < 0)
			 {
			  alert('비밀번호는 숫자와 영문자를 혼용하여야 합니다.');		
			  return false;
			 }
		 return true;
		 }
 
 	
		
	function checkApply_submit(val)
	{	

	
		if(val=="Y"){
			alert("승인 진행중입니다.");
			return;
		}else if (val=="C"){
			alert("평가 승인완료되어 종료되었습니다.");
			return;		
		
		}else {			
			
			if(!pwdcheck() ) {
				return;
			}
			
				var MyForm = document.getElementsByName("MyForm")[0];
			if(MyForm.update_gb.value != "1")
			{
			
				var cf = confirm("상담신청 하시겠습니까?");
				if (cf == true)
				{
					document.MyForm.update_gb.value = "1";
					//password
					document.MyForm.submit();
				}
				else
					return;
				}
			
			else
				alert("이미 신청 하였습니다!");
		}
		
	}
	
	

	
	function img_view(imgurl, imgpath) {
		
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
		        
		  window.open('${ctx}/edi/consult/step3ImagePopup.do?img=' + imgurl + '&imgpath=' + imgpath + '&h=' + h, 'imgwin', 'width=' + openWidth + ', height=' + openHeight + ', scrollbars=yes, resizable=yes');
		}	
	
</script>
</head>

<body>
	<form name="MyForm"	action="${ctx }/epc/edi/consult/insertStepApplyFinal.do" method="post"	ID="Form1">
	<br>


		<div id="wrap">
			<div id="con_wrap">
				<div class="con_area">
				


	<div class="s_title mt20">
						<h2>상담신청 희망부서</h2>
					</div>
					<div class="tb_v_common">
					<table cellpadding="0" cellspacing="0" border="0" width="100%">						
							<colgroup>
								<col width="130px">
								<col width="300px">
								<col width="130px">
								<col width="300px">
							</colgroup>
							<tbody>
							<tr> 
							<th><p>희망부서</p></th>
								<td>
								<p class="td_p">
								<c:choose>
								<c:when test="${vendor.tSubteamCd=='15502'}">
		           					식품테넌트
		           				</c:when>
		           				<c:when test="${vendor.tSubteamCd=='00088'}">
		           					비식품테넌트
		           				</c:when>           		
		           				 </c:choose>	
		           				 </p>
							</td>
		           				 <th><p>사업자등록번호</p></th>
									<td><p class="td_p">${ vendorSession.vendor.bmanNo }</p></td>
									
									           				 
	           				 </tr>
							</tbody>
						</table>
					</div>
					
					
					
					
					
					<br>
					<div class="s_title mt20">
						<h2>상담 신청인 내역</h2><br>
					</div>
					<div class="tb_v_common">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
							<caption>기본정보 화면</caption>
							<colgroup>
								<col width="130px">
								<col width="300px">
								<col width="130px">
								<col width="300px">
							</colgroup>
							<tbody>
								<tr>
									<th><p>상호명</p></th>
									<td><p class="td_p">${vendor.hndNm}</p></td>
										<th><p>대표자명</p></th>
									<td><p class="td_p">${vendor.ceoNm}</p></td>
								</tr>
           				    <tr>
									<th><p>연락받으실 번호</p></th>
									<td><p class="td_p">${vendor.officetel1}-${vendor.officetel2}-${vendor.officetel3}</p></td>	
									<th><p>E-mail</p></th>
									<td><p class="td_p">${vendor.email}</p></td>									
								</tr>
							</tbody>
						</table>
					</div>
					
					
					
					
					<br>
					<div class="s_title mt20">
						<h2>상담신청 내역</h2><br>
					</div>
					<div class="tb_v_common">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
							<caption>기본정보 화면</caption>
							<colgroup>
								<col width="130px">
								<col width="300px">
								<col width="130px">
								<col width="300px">
							</colgroup>
							<tbody>	
								<tr>									
								<th><p>주력상품</p></th>
									<td><p class="td_p">${vendor.mainProduct}</p></td>
										<th><p>현재 운영 매장 수</p></th>
								<td><p class="td_p">${vendor.asisMystorecnt} 개</p></td>	
							</tr>
								<tr>
								
									<th><p>입점희망점포</p></th>
									<td colspan="3"><p class="td_p">${vendor.wishStore} 개</p></td>	
									
								</tr>
								
							</tbody>
						</table>
					</div>
					
								<br>
			<div class="s_title mt20">
						<h2>별첨 자료</h2>
					</div>
					<div class="tb_v_common">
					<table cellpadding="0" cellspacing="0" border="0" width="100%">						
							<colgroup>
								<col width="130px">
								<col width="300px">
								<col width="130px">
								<col width="300px">
							</colgroup>
							<tbody>	
							<tr>							
								<th><p>사업소개서</p></th>
								<td colspan="3">
									<c:if test="${not empty vendor.attachFileCode }"><span style="margin-left: 50px">${vendor.attachFileCode}</span>&nbsp;
									<a href="javascript:downloadConsultDocumnet('${vendor.bmanNo}')">다운로드</a>					
									</c:if>	
								</td>
							</tr>
							</tbody>
						</table>
					</div>
					
					<br>
			<div class="s_title mt20">
						<h2>특이사항</h2>
					</div>
					<div class="tb_v_common">
					<table cellpadding="0" cellspacing="0" border="0" width="100%">						
							<colgroup>
								<col width="130px">
								<col width="300px">
								<col width="130px">
								<col width="300px">
							</colgroup>
							<tbody>			
						
						<tr>
							<div class="box_etc_input">
							<textarea cols="122" rows="5" name="content" readOnly	style="width: 828px; height: 40px;">${vendor.pecuSubjectContent}</textarea>
						</div>						
						</tr>														
							</tbody>
						</table>
					</div>
					<br>
					<!-- 결격사유 자가진단 -->
					<div class="s_title mt20">
					<br>
						<h2>결격사유 자가진단</h2>
					</div>
					<div class="tb_h_common_02">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
							<caption>결격사유 자가진단(테넌트)</caption>
							<colgroup>
							
								<col width="100px">
								<col width="450px">
								<col width="100px">
								<col width="*">
							</colgroup>
							<br>
							<thead>
							
								<tr>
								
									<th rowspan="2">번호</th>
									<th rowspan="2">결격사유</th>
									<th colspan="2">평가</th>

								</tr>
								<tr>
									<th>YES</th>
									<th>NO</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${not empty answerList}">
									<c:forEach items="${answerList}" var="list" varStatus="status">
										<tr class="line_dot">
										
											<td class="t_center">${list.seq+1}</td>
											<td class="t_left"><p>${list.queryDesc}</p></td>
											<c:choose>
												<c:when test="${list.colVal eq 'Y'}">
													<td align="center">${list.colVal}</td>
													<td align="center"></td>
												</c:when>
												<c:otherwise>
													<td align="center"></td>
													<td align="center">${list.colVal}</td>
												</c:otherwise>
											</c:choose>


										</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
					</div>

				
				 <c:if test="${gubunBlock =='N'}"> 
				 <tr>
						
					</div>
					<div class="tb_v_common">
						<table cellpadding="0" cellspacing="0" border="1" width="100%">
						<colgroup>
								<col width="130px">
								<col width="150px">
								<col width="130px">
								<col width="300px">
							</colgroup>
							<tr>
								<th><p>비밀번호</p></th>
								<td colspan="2">
								<span class="input_txt"><span>
								<input type="password" name="password"  id="password" maxlength="12" class="txt"	style="width: 150px;"></span></span>
								</td>
								<td>
									<p class="td_p">*비밀번호는 영문+숫자 8~12자리입니다.</p>
								</td>
					</tr>
				</c:if>			
		
					</table>
					<br><font color="red">* 비밀번호는 상담신청 후 결과확인을 위해 필요함으로 기억해 두시기 바랍니다.</font>	
					<br><font color="red">* 비밀번호 변경시 대표자명, 사업자번호, E-MAIL이 필요합니다.</font>
				
					</div>				
					<!-- button -->
					<div class="btn_c_wrap mt30">
						<span class="btn_white"><span>
							<a href="javascript:checkApply_submit('${gubunBlock }');"><spring:message code='button.consult.apply' /></a></span></span>
					</div>
					<!--// button -->
				</div>
			</div>
		</div>


		<input type="hidden" name="update_gb" value="" ID="Hidden1"> 
		<input type="hidden" name="teamNm" value="${vendor.teamNm }" ID="Hidden2">
		<input type="hidden" id="gubunIn" name="gubunIn"
			value="${gubunBlock }" />
	</form>
</body>
</html>