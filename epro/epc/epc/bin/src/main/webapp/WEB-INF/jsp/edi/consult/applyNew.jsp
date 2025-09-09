<%@ page  pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>    
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>롯데마트 입점상담</title>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
 
<script language="JavaScript">
<c:if test="${not empty resultMessage }">


var count = 0;

 $(function() {
 	$("select[name=kindCd]").change(setupKind);
 	
 });
// $("input").change(function(){
//     alert("The text has been changed.");
// });


function resetAnswer () {
	
	$(".td_p option:selected").removeAttr("selected");
	$(".td_p option:selected").removeAttr("selected");
	$(".td_p option:selected").removeAttr("selected");
	$(".td_p option:selected").removeAttr("selected");
	
}

//카운트 No 카운트를 선택
function checkNoCount(name,val){
	var product = $("#"+name+val+" option:selected").val();
		if(product=="N"){
	count = count + 1 ;	
	if(count>=2) {
	//	alert("No를 1개이상 선택할 수 없습니다.");
				
		count = count -1;
	}
	
	}
}

function showButton(){
    var i = document.getElementById("submit");
   	 i.style.display = "";
  
}



function setupKind() {	
	
	 var kindCd = $("select[name=kindCd]").val();
	 
			if( kindCd == "0" ) {
				 alert("상품,지원,테넌트 선택해주세요");
		 	}
			 else if (kindCd == "1"){
				 $("select[name=kindCd]").val()=="1";
				 setupFieldByProductDivnCode();				 
			 } 
			 else if (kindCd == "2"){
				 $("select[name=kindCd]").val()=="2";
				 setupFieldBySupportDivnCode();	 
				
			 } 
			 else if (kindCd == "3"){
				 $("select[name=kindCd]").val()=="3";
				 setupFieldByTenantDivnCode();				 
			 } 
		
		}




function setupFieldByProductDivnCode() 
	{	
		$("#test10").show();
		$("#test20").hide();
		$("#test30").hide();	
		showButton();
			
	}

function setupFieldBySupportDivnCode() 
	{
		$("#test10").hide();
		$("#test20").show();
		$("#test30").hide();
		showButton();
			
	}

function setupFieldByTenantDivnCode() 
    {
		$("#test10").hide();
		$("#test20").hide();
		$("#test30").show();
		showButton();
	}


//function  submitStep1Form()
function  submitStepApplyKindForm()
{
		
		var form = document.MyForm;	
		var kindCd = $("select[name=kindCd]").val();
		form.kind.value = kindCd;
	
		
		
		if(kindCd=="0")
		{
			alert("구분을 선택해주세요");	
			return false;
		}	
		
	    if (kindCd=="1"){
	    	var product = $("select[name=product_101]").val() + ";" +
	    	              $("select[name=product_102]").val() + ";" +
	    	              $("select[name=product_103]").val() + ";" +
	    	              $("select[name=product_104]").val();
	    	form.answer.value = product;
	    
            if ($("select[name=product_101]").val()=="0"){
         	  alert("첫번째 평가값이 선택입니다.");   
         	 return false;
            }	
            if ($("select[name=product_102]").val()=="0"){
           	  alert("두번째 평가값이 선택입니다.");   
           	 return false;
              }
            if ($("select[name=product_103]").val()=="0"){
           	  alert("세번째 평가값이 선택입니다.");   
           	 return false;
              }
            if ($("select[name=product_104]").val()=="0"){
           	  alert("네번째 평가값이 선택입니다.");   
           	 return false;
              }  
            
 					//상품 갯수 체크
		             var cnt = "0";
		             for (var i = 0, len = product.length; i < len; i++) {
		               var c = product.charAt(i);
		               if(c == "N") cnt++;
		             }               
		            if (cnt>1){	   
		            	//alert("상품구분은 평가선택  N이 2개 이상이면 등록이 불가합니다.");
		            	alert("상품 삼당신청 시,결격사유 2개 이상이면  등록이 불가합니다.");
		            	
		            	var form = document.MyForm;
				 	    var url='${ctx }';
				 		           
				 	    url += '/epc/edi/consult/PEDMCST056.do';
				 	    form.action=url;
				 	    form.submit();
					 	    
					 	    
					 	    
		        	 // return false;	            	
		            }

		}
	    
		if (kindCd=="2"){
				var support = $("select[name=support_201]").val() + ";" +
						  	  $("select[name=support_202]").val() + ";" +
						 	  $("select[name=support_203]").val() + ";" +
							  $("select[name=support_204]").val();
				form.answer.value = support;
		    	if ($("select[name=support_201]").val()=="0"){
	         	  alert("첫번째 평가값이 선택입니다.");   
	         	 return false;
	            }	
	            if ($("select[name=support_202]").val()=="0"){
	           	  alert("두번째 평가값이 선택입니다.");   
	           	 return false;
	              }
	            if ($("select[name=support_203]").val()=="0"){
	           	  alert("세번째 평가값이 선택입니다.");   
	           	 return false;
	              }
	            if ($("select[name=support_204]").val()=="0"){
	           	  alert("네번째 평가값이 선택입니다.");   
	           	 return false;
	              } 

					//테넌트 갯수 체크
	 	            var cnt = "0";
	 	            for (var i = 0, len = support.length; i < len; i++) {
	 	              var c = support.charAt(i);
	 	              if(c == "N") cnt++;
	 	            }
		            if (cnt>0){	         
		            //alert("지원구분은 평가선택  N이 1개 이상이면 등록이 불가합니다.");
		            alert("지원 삼당신청 시,결격사유 1개 이상이면  등록이 불가합니다.");
		        	
		            var form = document.MyForm;
			 	    var url='${ctx }';
			 		           
			 	    url += '/epc/edi/consult/PEDMCST056.do';
			 	    form.action=url;
			 	    form.submit();
		            
			 	    
		             // return false;	            	
		            } 
	            
			    }
		
		if (kindCd=="3"){
				var tenant  =	$("select[name=tenant_301]").val() + ";" +
								$("select[name=tenant_302]").val() + ";" +
								$("select[name=tenant_303]").val() + ";" +
								$("select[name=tenant_304]").val();
				form.answer.value = tenant;
		    	if ($("select[name=tenant_301]").val()=="0"){
	         	  alert("첫번째 평가값이 선택입니다.");   
	         	 return false;
	            }	
	            if ($("select[name=tenant_302]").val()=="0"){
	           	  alert("두번째 평가값이 선택입니다.");   
	           	 return false;
	              }
	            if ($("select[name=tenant_303]").val()=="0"){
	           	  alert("세번째 평가값이 선택입니다.");   
	           	 return false;
	              }
	            if ($("select[name=tenant_304]").val()=="0"){
	           	  alert("네번째 평가값이 선택입니다.");   
	           	 return false;
	              }
 	       
		 	            var cnt = "0";
		 	            for (var i = 0, len = tenant.length; i < len; i++) {
		 	              var c = tenant.charAt(i);
		 	              if(c == "N") cnt++;
		 	            }
			            if (cnt>0){	         
			            	//alert("테넌트 구분은 평가선택  N이 1개 이상이면 등록이 불가합니다.");
			            	alert("테넌트 삼당신청 시,결격사유 1개 이상이면  등록이 불가합니다.");
				        	    
			            	
			            	var form = document.MyForm;
					 	    var url='${ctx }';
					 		           
					 	    url += '/epc/edi/consult/PEDMCST056.do';
					 	    form.action=url;
					 	    form.submit();
						 	    
						 	    
						 	    
			             	//return false;	            	
			            }
	            
		}
	

		
		$("form[name=MyForm]").submit();
		
	}

</script>


</head>

<body>

<form name="MyForm" action="${ctx }/epc/edi/consult/insertApplyKind.do" method="post">
<input type="hidden" name="kind" 			      value="" />
<input type="hidden" name="answer" 			      value="" />



<div id="wrap">

	<div id="con_wrap">
		

		<div class="con_area">

			

		

			<!-- 기본정보 -->
			<div class="s_title">
				<h2>결격사유 자가진단</h2>
			</div>

			
			<div class="tb_form_comm">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<caption>기본정보 입력 화면</caption>
				<colgroup>
				<col width="150px">
				<col width="300px">
				<col width="130px">
				<col width="*"></colgroup>
				<tbody>
			
					<tr>
						<th width="130px"><p class="check">상담신청 분류</p></th>
						<td width="300px">
							<div class="td_t">
								<!-- select -->
								<select id="kindCd" name="kindCd" style="width:124px;" value="" onchange="resetAnswer();">
								    <option value="0"<c:if test="${empty answerList}"> selected</c:if>>선택</option>
								    <option value="1"<c:if test="${answerList[0].kindCd eq '1'}"> selected</c:if>>상품</option>
									<option value="2"<c:if test="${answerList[0].kindCd eq '2'}"> selected</c:if>>지원</option>
									<option value="3"<c:if test="${answerList[0].kindCd eq '3'}"> selected</c:if>>테넌트</option>								
								</select>
								<!--// select -->
							</div>
						</td>
						<th width="130px"><p>사업자등록번호</p></th>
							<td width="300px"><div class="td_p"><span class="t_gray t_12"><input type="text" name="bmanNo" class="txt" value="${ vendorSession.vendor.bmanNo }"  ID="Text1" readonly style="width:232px;"></span></div></td>
					</tr>
				<br><tr></tr>	
						
				
					
	<table id="test10" style="display:none" cellpadding="0" cellspacing="0" border="0" width="100%">

	<colgroup>
		<col width="70px">
		<col width="300px">
		<col width="30px">
		<col width="30px">
	</colgroup>		
		<tbody>
		
			
		<tr>	
				
				<th rowspan="2"><p>구분내용</p></th>
				<th rowspan="2" colspan="1"><p>결격사유 항목 (담당자와 상담시 결격사유 관련 증빙 사본을 제출하셔야 합니다.)</p></th>
				<td colspan="2" align="center">평가</td>
			<tr>
				<td align="center">YES</td>
				<td align="center">NO</td>
			</tr>
			</tr>			
			<tr>
				<th  rowspan="4"><p>상품</p></th>
				<td><p class="td_p">최근 2년간 언론에 부정적 보도 또는 법규위반으로 벌금/과태료 이상 처분을 받은 사실이 없습니까?</p></td>
				<td colspan="2" align="center">
					<div class="td_p">							
						<select id="estVal11" name="product_101" style="width:60px;" value="" onchange="checkNoCount('estVal','11');">
							<option <c:if test="${empty answerList}"> selected</c:if> value="0">선택</option>
							<option value="Y" <c:if test="${answerList[0].colVal eq 'Y'}"> selected</c:if>>YES</option>
							<option value="N" <c:if test="${answerList[0].colVal eq 'N'}"> selected</c:if>>NO</option>
						</select>						
					</div>
				</td>
			</tr>					
			<tr>
				<td>
					<p class="td_p">직접 제조하거나, 직접 수입, 국내 제조사에 총판하는 파트너사 입니까?</p>
				</td>
				<td colspan="2" align="center">
					<div class="td_p">							
						<select id="estVal12" name="product_102" style="width:60px;" onchange="checkNoCount('estVal','12');">
						<option <c:if test="${empty answerList}"> selected</c:if> value="0">선택</option>
							<option value="Y" <c:if test="${answerList[1].colVal eq 'Y'}"> selected</c:if>>YES</option>
							<option value="N" <c:if test="${answerList[1].colVal eq 'N'}"> selected</c:if>>NO</option>
						</select>						
					</div>
				</td>
			</tr>
			<tr>						
				<td ><p class="td_p">신용평가등급이 B등급이상 이십니까?</p></td>				
				<td colspan="2" align="center">
					<div class="td_p">							
						<select id="estVal13" name="product_103" style="width:60px;" onchange="checkNoCount('estVal','13');">
							<option <c:if test="${empty answerList}"> selected</c:if> value="0">선택</option>
							<option value="Y" <c:if test="${answerList[2].colVal eq 'Y'}"> selected</c:if>>YES</option>
							<option value="N" <c:if test="${answerList[2].colVal eq 'N'}"> selected</c:if>>NO</option>
						</select>						
					</div>
				</td>				
			</tr>
					<tr>						
						<td><p class="td_p">품질관련 인증서를 보유하고 있습니까?(친환경인증, HACCP, ISO 등 국내외 품질관련 인증서)</p></td>
						<td colspan="2" align="center">
							<div class="td_p">							
								<select id="estVal14" name="product_104" style="width:60px;" onchange="checkNoCount('estVal','14');">
								<option <c:if test="${empty answerList}"> selected</c:if> value="0">선택</option>
							<option value="Y" <c:if test="${answerList[3].colVal eq 'Y'}"> selected</c:if>>YES</option>
							<option value="N" <c:if test="${answerList[3].colVal eq 'N'}"> selected</c:if>>NO</option>
								</select>						
							</div>
						</td>
					</tr>
				</tbody>
				</table>
			</th>
			</tr>			
			<tr>
			
			
			
		
						
			<table id="test20" style="display:none" cellpadding="0" cellspacing="0" border="0" width="100%">
				<colgroup>
					<col width="70px">
					<col width="300px">
					<col width="30px">
					<col width="30px">
				</colgroup>		
			<tbody>
			
			<tr>	
				<th rowspan="2"><p>구분내용</p></th>
				<th rowspan="2" colspan="1"><p>결격사유 항목 (담당자와 상담시 결격사유 관련 증빙 사본을 제출하셔야 합니다.)</p></th>
				<td colspan="2" align="center">평가</td>
				<tr>
					<td align="center">YES</td>
					<td align="center">NO</td>
				</tr>
			</tr>			
			<tr>
				<th  rowspan="4"><p>지원</p></th>
				<td><p class="td_p">최근 2년간 언론에 부정적 보도 또는 법규위반으로 벌금/과태료 이상 처분을 받은 사실이 없습니까?</p></td>
				<td colspan="2" align="center">
					<div class="td_p">							
						<select id="estVal21" name="support_201" style="width:60px;" onchange="checkNoCount('estVal','21');">
							<option <c:if test="${empty answerList}"> selected</c:if> value="0">선택</option>
							<option value="Y" <c:if test="${answerList[0].colVal eq 'Y'}"> selected</c:if>>YES</option>
							<option value="N" <c:if test="${answerList[0].colVal eq 'N'}"> selected</c:if>>NO</option>
						</select>						
					</div>
				</td>
			</tr>					
			<tr>
				<td >
					<p class="td_p">기업 신용평가 등급이 B등급 이상으로 담당자에게 제출 가능하십니까?(이크레더블, 한국기업데이터,나이스디앤비)</p>
				</td>
				<td colspan="2" align="center">
					<div class="td_p">							
						<select id="estVal22" name="support_202" style="width:60px;" onchange="checkNoCount('estVal','22');">
						<option <c:if test="${empty answerList}"> selected</c:if> value="0">선택</option>
							<option value="Y" <c:if test="${answerList[1].colVal eq 'Y'}"> selected</c:if>>YES</option>
							<option value="N" <c:if test="${answerList[1].colVal eq 'N'}"> selected</c:if>>NO</option>
						</select>						
					</div>
				</td>
			</tr>
			<tr>						
				<td ><p class="td_p">전년도 매출액이 10억원 이상 이십니까?</p></td>
				
				<td colspan="2" align="center">
					<div class="td_p">							
						<select id="estVal23" name="support_203" style="width:60px;" onchange="checkNoCount('estVal','23');">
							<option <c:if test="${empty answerList}"> selected</c:if> value="0">선택</option>
							<option value="Y" <c:if test="${answerList[2].colVal eq 'Y'}"> selected</c:if>>YES</option>
							<option value="N" <c:if test="${answerList[2].colVal eq 'N'}"> selected</c:if>>NO</option>
						</select>						
					</div>
				</td>
				
			</tr>
					<tr>						
						<td><p class="td_p">업체 설립일이 2년이상 경과하였습니까?</p></td>
						<td colspan="2" align="center">
							<div class="td_p">							
								<select id="estVal24" name="support_204" style="width:60px;" onchange="checkNoCount('estVal','24');">
								<option <c:if test="${empty answerList}"> selected</c:if> value="0">선택</option>
							<option value="Y" <c:if test="${answerList[3].colVal eq 'Y'}"> selected</c:if>>YES</option>
							<option value="N" <c:if test="${answerList[3].colVal eq 'N'}"> selected</c:if>>NO</option>
								</select>						
							</div>
						</td>
					</tr>
				
					
				</tbody>
				</table>
			<tr>
	
					
					
					
				
		<table id="test30" style="display:none" cellpadding="0" cellspacing="0" border="0" width="100%" >
		
			<colgroup>
				<col width="70px">
				<col width="300px">
				<col width="30px">
				<col width="30px">
			</colgroup>		
		<tbody>
			
			<tr>	
				
				<th rowspan="2"><p>구분내용</p></th>
				<th rowspan="2" colspan="1"><p>결격사유 항목 (담당자와 상담시 결격사유 관련 증빙 사본을 제출하셔야 합니다.)</p></th>
				<td colspan="2" align="center">평가</td>
				<tr>
					<td align="center">YES</td>
					<td align="center">NO</td>
				</tr>
			</tr>			
			<tr>
				<th  rowspan="4"><p>테넌트</p></th>
				<td><p class="td_p">최근 2년간 언론에 부정적 보도 또는 법규위반으로 벌금/과태료 이상 처분을 받은 사실이 없습니까?</p></td>
				<td colspan="2" align="center">
					<div class="td_p">							
						<select id="estVal31" name="tenant_301" style="width:60px;" onchange="checkNoCount('estVal','31');">
							<option <c:if test="${empty answerList}"> selected</c:if> value="0">선택</option>
							<option value="Y" <c:if test="${answerList[0].colVal eq 'Y'}"> selected</c:if>>YES</option>
							<option value="N" <c:if test="${answerList[0].colVal eq 'N'}"> selected</c:if>>NO</option>
						</select>						
					</div>
				</td>
			</tr>					
			<tr>
				<td >
					<p class="td_p">법인 프랜차이즈 사업자이며, 공정거래위원회 가맹사업 정보공개서가 등록되어 있습니까?</p>
				</td>
				<td colspan="2" align="center">
					<div class="td_p">							
						<select id="estVal32" name="tenant_302" style="width:60px;" onchange="checkNoCount('estVal','32');">
							<option <c:if test="${empty answerList}"> selected</c:if> value="0">선택</option>
							<option value="Y" <c:if test="${answerList[1].colVal eq 'Y'}"> selected</c:if>>YES</option>
							<option value="N" <c:if test="${answerList[1].colVal eq 'N'}"> selected</c:if>>NO</option>
						</select>						
					</div>
				</td>
			</tr>
			<tr>						
				<td ><p class="td_p">신용평가등급이 B등급 이상 이십니까?</p></td>
				
				<td colspan="2" align="center">
					<div class="td_p">							
						<select id="estVal33" name="tenant_303" style="width:60px;" onchange="checkNoCount('estVal','33');">
							<option <c:if test="${empty answerList}"> selected</c:if> value="0">선택</option>
							<option value="Y" <c:if test="${answerList[2].colVal eq 'Y'}"> selected</c:if>>YES</option>
							<option value="N" <c:if test="${answerList[2].colVal eq 'N'}"> selected</c:if>>NO</option>
						</select>						
					</div>
				</td>
				
			</tr>
					<tr>						
						<td><p class="td_p">현재 운영하고 있는 매장이 있으며, 사업자등록증을 제출하실수 있습니까?(면허자격증이 필요한 업종의 경우 함께 제출)</p></td>
						<td colspan="2" align="center">
							<div class="td_p">							
								<select id="estVal34" name="tenant_304" style="width:60px;" onchange="checkNoCount('estVal','34');">
									<option <c:if test="${empty answerList}"> selected</c:if> value="0">선택</option>
							<option value="Y" <c:if test="${answerList[3].colVal eq 'Y'}"> selected</c:if>>YES</option>
							<option value="N" <c:if test="${answerList[3].colVal eq 'N'}"> selected</c:if>>NO</option>
								</select>						
							</div>
						</td>
					</tr>
					
				
					 
				</tbody>
				</table>
								
					</tr>
				
				</tbody>
			</table>
			
			
			<table cellpadding="0" cellspacing="0" border="8" width="100%">
				<caption>기타...............................</caption>
				<colgroup>
					<col width="600px">			
					<col width="200px">			
				</colgroup>
				<tbody>
			<br><br>
				<tr rowspan="3">							
					<td>* 상품 : 파트너사에서 기획, 개발한 우수상품을 롯데마트에 납품하기 위한 상담신청<br></td>	
					<td rowspan="3">
					<div name="submit" id="submit" style="display:none"  class="btn_c_wrap mt25">
					<span class="btn_red"><span><a href="#" onclick="submitStepApplyKindForm();">확인</a></span></span>
					</div>
					</td>
					
				</tr>
				<tr>		
					<td>* 지원 : 롯데마트에서 사용할 물품(소모품, 집기) 및 공사 시행을 위한 상담신청<br></td>	
				
				</tr>
				<tr>
					<td>* 데넌트 : 롯데마트 매장 내 고객편의를 위한 식품/비식품 임대매장 운영을 위한 상담신청<br></td>	
				</tr>
				
				
				
						<td>				

			<!-- button -->
			
			<!--// button -->
			</td>
				</tr>				
				</tbody>
			</table>			
			</div>	
		</div>
	</div>
</div>
<script>
</c:if>
<c:if test="${not empty answerList}">
setupKind();
</c:if>
</script>
</form>
</body>
</html>