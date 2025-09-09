<%@ page  pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>    
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><spring:message code='text.consult.field.lotteVendorConsult'/></title>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
 
<script language="JavaScript">

<c:if test="${not empty resultMessage }">

var count = 0;

 $(function() {
	 if (document.location.protocol == 'http:') {
		 if ('<c:out value="${serverType}" />' == "prd") {
			 document.location.href = document.location.href.replace('http:', 'https:');
		 }
	 }
	 //$("select[name=kindCd]").change(setupKind);
	
	//-----첨부파일 크기가 5MB가 넘을경우 메세지 띄움 
	var errMsg	=	"<c:out value='${errMsg}'/>"; 
	if (errMsg.replace(/\s/gi, '') != "") {
		alert(errMsg);
	}

	// 약관동의 저장된값 세팅
	var checkRadio = "<c:out value='${vendor.agreeYn}'/>";
	if("<c:out value='${vendor.agreeYn}'/>" != ""){
		$( 'input:radio[name=agreeYn]:input[value='+checkRadio+']' ).attr( "checked" , true);		
	}


	// 상담신청분류 변경되면 보여줄것.
	$("#MyForm select[name='kindCd']").change(function() {
		setupKind();
		$('input:radio[name=agreeYn]:input[value=N]').attr("checked", true);
		
	});
	
 });
// $("input").change(function(){
//     alert("The text has been changed.");
// });


function resetAnswer () {
	
	//$(".td_p option:selected").removeAttr("selected");
	//$(".td_p option:selected").removeAttr("selected");
	//$(".td_p option:selected").removeAttr("selected");
	//$(".td_p option:selected").removeAttr("selected");
	
}

//카운트 No 카운트를 선택
function checkNoCount(id){
	var product = $("#MyForm #"+id+" option:selected").val();
	
	if(product=="N"){
		count = count + 1 ;	
		if(count>=2) {
			count = count -1;
		}
	
	}
	
}

function setupKind() {

	 var kindCd = $("#MyForm select[name=kindCd]").val();
	 
			if( kindCd == "0" ) {
				alert("<spring:message code='msg.consult.require.kindCd'/>");   
		 	}
			 else if (kindCd == "1"){
				 $("#MyForm select[name=kindCd]").val()=="1";
				 setupFieldByProductDivnCode();				 
			 } 
			 else if (kindCd == "2"){
				 $("#MyForm select[name=kindCd]").val()=="2";
				 setupFieldBySupportDivnCode();	 
				
			 } 
			 else if (kindCd == "3"){
				 $("#MyForm select[name=kindCd]").val()=="3";
				 setupFieldByTenantDivnCode();				 
			 } 
			 else if (kindCd == "4"){
				 $("#MyForm select[name=kindCd]").val()=="4";
				 setupFieldByMobileDivnCode();				 
			 } 
		}




function setupFieldByProductDivnCode() 
	{	
		$("#MyForm #test10").show();
		$("#MyForm #test20").hide();
		$("#MyForm #test30").hide();	
		$("#MyForm #test40").hide();	
		$("#MyForm #submit").css("display", "");
			
	}

function setupFieldBySupportDivnCode() 
	{
		$("#MyForm #test10").hide();
		$("#MyForm #test20").show();
		$("#MyForm #test30").hide();
		$("#MyForm #test40").hide();
		$("#MyForm #submit").css("display", "");
			
	}

function setupFieldByTenantDivnCode() 
    {
		$("#MyForm #test10").hide();
		$("#MyForm #test20").hide();
		$("#MyForm #test30").show();
		$("#MyForm #test40").hide();
		$("#MyForm #submit").css("display", "");
	}
function setupFieldByMobileDivnCode() 
    {
		$("#MyForm #test10").hide();
		$("#MyForm #test20").hide();
		$("#MyForm #test30").hide();
		$("#MyForm #test40").show();
		$("#MyForm #submit").css("display", "");
	}

// 파일 사이즈 체크 
// ObjectType
function fileSizeCheck(fileNm){
	var checkFile = false;
	var iSize = document.getElementById(fileNm).files[0].size;	
	// BM 계산	iSize = (Math.round((iSize / 1024 / 1024) * 100) / 100)

	if ( iSize > ( 1024 * 1024 ) * 5) {
			iSize = (Math.round((iSize / 1024) * 100) / 100)
			alert("<spring:message code='msg.consult.require.maxSize5m'/>"); /* 첨부가능한 파일의 용량은 5MB 입니다.  */
	} else {
		checkFile = true;
	}	
	return checkFile;
}
 
function removeFiles(fileNm) {
	fileNm.replaceWith( fileNm.val('').clone(true)); 
}

//function  submitStep1Form()
function  submitStepApplyKindForm()
{		
		var kindCd = $("#MyForm select[name=kindCd]").val();
		$("#MyForm input[name=kind]").val(kindCd);
	
		
		
		if(kindCd=="0")
		{
			alert("<spring:message code='msg.consult.require.kindCd'/>");   
			return false;
		}	
		
	    if (kindCd=="1"){
	    	var product = $("#MyForm select[name=product101]").val() + ";" +
	    	              $("#MyForm select[name=product102]").val() + ";" +
	    	              $("#MyForm select[name=product103]").val() + ";" +
	    	              $("#MyForm select[name=product104]").val();
	    	
	    	$("#MyForm input[name=answer]").val(product);
	    
            if ($("#MyForm select[name=product101]").val()=="0" || $("#MyForm select[name=product101]").val() == null){
         		alert("<spring:message code='msg.consult.require.kindCdOption' arguments='1'/>");   
         		return false;
            }	
            if ($("#MyForm select[name=product102]").val()=="0" || $("#MyForm select[name=product102]").val() == null){
            	alert("<spring:message code='msg.consult.require.kindCdOption' arguments='2'/>");   
           		return false;
            }
            if ($("#MyForm select[name=product103]").val()=="0" || $("#MyForm select[name=product103]").val() == null){
            	alert("<spring:message code='msg.consult.require.kindCdOption' arguments='3'/>");   
           		return false;
            }
            if ($("#MyForm select[name=product104]").val()=="0" || $("#MyForm select[name=product104]").val() == null){
            	alert("<spring:message code='msg.consult.require.kindCdOption' arguments='4'/>");   
           		return false;
            } 
            
            //파일 필수 체크
            if(!$("#MyForm #kindAttachFile1").val() && !$("#MyForm #kindFile1").val() ){

            	alert("<spring:message code='msg.consult.require.kindCd2File'/>");
            	return false;
            }else{
            	
            	//파일 체크
                if ($("#MyForm #kindAttachFile1").val().length != 0){
        			var fileExtension = getFileExtension($("#MyForm #kindAttachFile1").val()).toLowerCase();
        			if(fileExtension != "ppt" & fileExtension != "pptx" & fileExtension != "pdf" & fileExtension != "xls" & fileExtension != "xlsx" ) {
        				alert("<spring:message code='msg.consult.require.fileExtensionPptPptxPdfXlsXlsx'/>");
        				removeFiles($("#MyForm #kindAttachFile1"));
        				return false;
        			} else {
        				/* 파일용량체크 */		
        				/* if(fileSizeCheck("kindAttachFile1") == false){
           					return false;    					
        				} */
       				}
       			}
            	
            }
         	
            
			//상품 갯수 체크
            var cnt = "0";
            for (var i = 0, len = product.length; i < len; i++) {
            	var c = product.charAt(i);
            	if(c == "N") cnt++;
            }      
            
            if (cnt > 1){	   
            	alert("<spring:message code='msg.consult.require.kindCdOption1'/>");
		 	    var url = "<c:url value='/epc/edi/consult/NEDMCST0311.do'/>";
		 	    $("#MyForm").attr("action", url);
		 	    $("#MyForm").submit();
            }

		}
	    
		if (kindCd=="2"){
				var support = $("#MyForm select[name=support201]").val() + ";" +
						  	  $("#MyForm select[name=support202]").val() + ";" +
						 	  $("#MyForm select[name=support203]").val() + ";" +
							  $("#MyForm select[name=support204]").val();
						 	  
				$("#MyForm input[name=answer]").val(support);
				
		    	if ($("#MyForm select[name=support201]").val()=="0" || $("#MyForm select[name=support201]").val() == null){
		    		alert("<spring:message code='msg.consult.require.kindCdOption' arguments='1'/>");   
	         		return false;
	            }	
	            if ($("#MyForm select[name=support202]").val()=="0" || $("#MyForm select[name=support202]").val() == null){
	            	alert("<spring:message code='msg.consult.require.kindCdOption' arguments='2'/>");   
	           		return false;
	            }
	            if ($("#MyForm select[name=support203]").val()=="0" || $("#MyForm select[name=support203]").val() == null){
	            	alert("<spring:message code='msg.consult.require.kindCdOption' arguments='3'/>");   
	           		return false;
	            }
	            if ($("#MyForm select[name=support204]").val()=="0" || $("#MyForm select[name=support204]").val() == null){
	            	alert("<spring:message code='msg.consult.require.kindCdOption' arguments='4'/>");   
	           		return false;
	            } 
	            
	            //파일 필수 체크
	            if(!$("#MyForm #kindAttachFile2").val() && !$("#MyForm #kindFile2").val() ){
	            	
	            	alert("<spring:message code='msg.consult.require.kindCd2File'/>");
	            	return false;
	          
	            }else{
	            	
	            	//파일 체크
        			var fileExtension = "";
	            	if ($("#MyForm #kindAttachFile2").val() != "") {
	            		fileExtension = getFileExtension($("#MyForm #kindAttachFile2").val()).toLowerCase();
	            	} else {
	            		fileExtension = getFileExtension($("#MyForm #kindFile2").val()).toLowerCase();
	            	}
	            	
	            	if(fileExtension != "ppt" & fileExtension != "pptx" & fileExtension != "pdf" & fileExtension != "xls" & fileExtension != "xlsx" ) {
	    				alert("<spring:message code='msg.consult.require.fileExtensionPptPptxPdfXlsXlsx'/>");
	    				removeFiles($("#MyForm #kindAttachFile2"));
	    				return false;
	    			} else {
	    				/* 파일용량체크 */		
	    				/* if(fileSizeCheck("kindAttachFile2") == false){
	       					return false;    					
	    				} */

	    			}
	            }

				//테넌트 갯수 체크
 	            var cnt = "0";
 	            for (var i = 0, len = support.length; i < len; i++) {
 	              var c = support.charAt(i);
 	              if(c == "N") cnt++;
 	            }
	            if (cnt>0){	         
		            alert("<spring:message code='msg.consult.require.kindCdOption2'/>");
		            var url = "<c:url value='/epc/edi/consult/NEDMCST0311.do'/>";
			 	    $("#MyForm").attr("action", url);
			 	    $("#MyForm").submit();
	            } 
            
		    }
		
		if (kindCd=="3"){
				var tenant  =	$("#MyForm select[name=tenant301]").val() + ";" +
								$("#MyForm select[name=tenant302]").val() + ";" +
								$("#MyForm select[name=tenant303]").val() + ";" +
								$("#MyForm select[name=tenant304]").val();
				
				$("#MyForm input[name=answer]").val(tenant);
				
		    	if ($("#MyForm select[name=tenant301]").val()=="0" || $("#MyForm select[name=tenant301]").val()==null){
		    		alert("<spring:message code='msg.consult.require.kindCdOption' arguments='1'/>");   
	         		return false;
	            }	
	            if ($("#MyForm select[name=tenant302]").val()=="0" || $("#MyForm select[name=tenant302]").val() == null){
	            	alert("<spring:message code='msg.consult.require.kindCdOption' arguments='2'/>");   
	           		return false;
	            }
	            if ($("#MyForm select[name=tenant303]").val()=="0" || $("#MyForm select[name=tenant303]").val() == null){
	            	alert("<spring:message code='msg.consult.require.kindCdOption' arguments='3'/>");   
	           		return false;
	            }
	            if ($("#MyForm select[name=tenant304]").val()=="0" || $("#MyForm select[name=tenant304]").val()==null){
	            	alert("<spring:message code='msg.consult.require.kindCdOption' arguments='4'/>");   
	           		return false;
	            }
	            
	          	//파일 체크
	          	
	            //파일 필수 체크
	            if(!$("#MyForm #kindAttachFile3").val() && !$("#MyForm #kindFile3").val() ){
	            	
	            	alert("<spring:message code='msg.consult.require.kindCd2File'/>");
	            	return false;
	          
	            }else{
	            	
	            	
		            if ($("#MyForm #kindAttachFile3").val().length != 0){
		    			var fileExtension = getFileExtension($("#MyForm #kindAttachFile3").val()).toLowerCase();
		    			if(fileExtension != "ppt" & fileExtension != "pptx" & fileExtension != "pdf" & fileExtension != "xls" & fileExtension != "xlsx" ) {
		    				alert("<spring:message code='msg.consult.require.fileExtensionPptPptxPdfXlsXlsx'/>");
		    				removeFiles($("#MyForm #kindAttachFile3"));
		    				return false;
		    			} else {
		    				/* 파일용량체크 */		
		    				/* if(fileSizeCheck("kindAttachFile3") == false){
		       					return false;    					
		    				} */

		    			}
		    		}
	            	
	            }
	      
	           
	          	
 	            var cnt = "0";
 	            for (var i = 0, len = tenant.length; i < len; i++) {
 	              var c = tenant.charAt(i);
 	              if(c == "N") cnt++;
 	            }
	            if (cnt>0){	         
	            	alert("<spring:message code='msg.consult.require.kindCdOption3'/>");    
	            	var url = "<c:url value='/epc/edi/consult/NEDMCST0311.do'/>";
			 	    $("#MyForm").attr("action", url);
			 	    $("#MyForm").submit();
	            }
	            
		}
	
		if (kindCd=="4"){
	    	var product = $("#MyForm select[name=product401]").val() + ";" +
	    	              $("#MyForm select[name=product402]").val() + ";" +
	    	              $("#MyForm select[name=product403]").val() + ";" +
	    	              $("#MyForm select[name=product404]").val();
	    	
	    	$("#MyForm input[name=answer]").val(product);
	    
            if ($("#MyForm select[name=product401]").val()=="0" || $("#MyForm select[name=product401]").val()== null){
         		alert("<spring:message code='msg.consult.require.kindCdOption' arguments='1'/>");   
         		return false;
            }	
            if ($("#MyForm select[name=product402]").val()=="0" || $("#MyForm select[name=product402]").val() == null){
            	alert("<spring:message code='msg.consult.require.kindCdOption' arguments='2'/>");   
           		return false;
            }
            if ($("#MyForm select[name=product403]").val()=="0" || $("#MyForm select[name=product403]").val() == null){
            	alert("<spring:message code='msg.consult.require.kindCdOption' arguments='3'/>");   
           		return false;
            }
            if ($("#MyForm select[name=product404]").val()=="0" || $("#MyForm select[name=product403]").val() == null){
            	alert("<spring:message code='msg.consult.require.kindCdOption' arguments='4'/>");   
           		return false;
            }
            
            
            //파일 필수 체크
            if(!$("#MyForm #kindAttachFile4").val() && !$("#MyForm #kindFile4").val() ){
            	
            	alert("<spring:message code='msg.consult.require.kindCd2File'/>");
            	return false;
          
            }else{
                
              	//파일 체크
                if ($("#MyForm #kindAttachFile4").val().length != 0){
        			var fileExtension = getFileExtension($("#MyForm #kindAttachFile4").val()).toLowerCase();
        			if(fileExtension != "ppt" & fileExtension != "pptx" & fileExtension != "pdf" & fileExtension != "xls" & fileExtension != "xlsx" ) {
        				alert("<spring:message code='msg.consult.require.fileExtensionPptPptxPdfXlsXlsx'/>");
        				removeFiles($("#MyForm #kindAttachFile4"));
        				return false; 
        			} else {
        				/* 파일용량체크 */		
        				/* if(fileSizeCheck("kindAttachFile4") == false){
           					return false;    					
        				} */

        			}
        		}
            	
            }
            
			//상품 갯수 체크
            var cnt = "0";
            for (var i = 0, len = product.length; i < len; i++) {
            	var c = product.charAt(i);
            	if(c == "N") cnt++;
            }      
            
            if (cnt > 1){	   
            	alert("<spring:message code='msg.consult.require.kindCdOption4'/>");
		 	    var url = "<c:url value='/epc/edi/consult/NEDMCST0311.do'/>";
		 	    $("#MyForm").attr("action", url);
		 	    $("#MyForm").submit();
            }

		}
		
		$("#MyForm").submit();
		
	}
	
	function downloadConsultDocumnet(vendorBusinessNo) {
		window.open("<c:url value='/edi/consult/NEDMCST0310step3DocumnetPopupKindCd.do?vendorBusinessNo='/>"+ vendorBusinessNo, "docwin",
				"width=200, height=300, scrollbars=yes, resizable=yes");
	}
	
	function deleteConsultDocumnet(vendorBusinessNo) {
		
		if(confirm("<spring:message code='msg.common.confirm.delete'/>")){
			var str = {"bmanNo" 		: vendorBusinessNo };
					
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : "<c:url value='/edi/consult/NEDMCST0310step3DocumnetDeletePopupKindCd.do'/>",
				data : JSON.stringify(str),
				success : function(data) {	
					if(data>0){
						alert("<spring:message code='msg.common.success.delete'/>");
						location.reload();
					}else{
						
					}
				}
			});			
		}
	}
	
	

</script>


</head>

<body>

<form name="MyForm" id="MyForm" action="<c:url value='/epc/edi/consult/NEDMCST0310insertApplyKind.do'/>" enctype="multipart/form-data" method="post">
<input type="hidden" name="kind"	 id="kind"		      value="" />
<input type="hidden" name="answer" 	 id="answer"	      value="" />
<input type="hidden" name="vendorAgreeYn"  id="vendorAgreeYn"	      value="<c:out value='${vendor.agreeYn}'/>" />
<input type="hidden" name="agreeYn"	id="agreeYn"		value="<c:out value='${param.agreeYn}'/>"/>



<div id="wrap">
	<div id="con_wrap">
		<div class="con_area">

			<!-- 기본정보 -->
			<div class="s_title">
				<h2><spring:message code='text.consult.field.selfDiagnosis'/></h2>
			</div>
			
			<div class="tb_form_comm">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<caption><spring:message code='text.consult.field.basicInformation'/></caption>
				<colgroup>
				<col width="150px">
				<col width="300px">
				<col width="130px">
				<col width="*"></colgroup>
				<tbody>
			
					<tr>
						<th width="130px"><p class="check"><spring:message code='text.consult.field.consultKind'/></p></th>
						<td width="300px">
							<div class="td_t">
								<!-- select -->
								<select id="kindCd" name="kindCd" style="width:124px;" onchange="resetAnswer();">
								  <%--   <option value="0"<c:if test="${empty answerList}"> selected</c:if>>선택</option> --%>
								   <%--  <option value="1"<c:if test="${answerList[0].kindCd eq '1'}"> selected</c:if>><spring:message code='text.consult.field.consultKind1'/></option> --%>
									<option value="2"<c:if test="${answerList[0].kindCd eq '2'}"> selected</c:if>><spring:message code='text.consult.field.consultKind2'/></option>
									<%-- <option value="3"<c:if test="${answerList[0].kindCd eq '3'}"> selected</c:if>><spring:message code='text.consult.field.consultKind3'/></option> --%>								
									<%-- <option value="4"<c:if test="${answerList[0].kindCd eq '4'}"> selected</c:if>><spring:message code='text.consult.field.consultKind4'/></option> --%>								
								</select>
								<!--// select -->
							</div>
						</td>
						<th width="130px"><p><spring:message code='text.consult.field.bmanNo'/></p></th>
							<td width="300px"><div class="td_p"><span class="t_gray t_12"><input type="text" name="bmanNo" class="txt" value="<c:out value='${ vendorSession.vendor.bmanNo }'/>"  id="bmanNo" readonly style="width:232px;"></span></div></td>
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
				
				<th rowspan="2"><p><spring:message code='text.consult.field.consultAnswer'/></p></th>
				<th rowspan="2" colspan="1"><p><spring:message code='text.consult.field.disqualification'/></p></th>
				<td colspan="2" align="center"><spring:message code='text.consult.field.evaluation'/></td>
			<tr>
				<td align="center">YES</td>
				<td align="center">NO</td>
			</tr>
			</tr>			
			<tr>
				<th  rowspan="4"><p><spring:message code='text.consult.field.consultKind1'/></p></th>
				<td><p class="td_p"><spring:message code='text.consult.field.consultKind1Opt1'/></p></td>
				<td colspan="2" align="center">
					<div class="td_p">							
						<select id="product101" name="product101" style="width:60px;" value="" onchange="checkNoCount('product101');">
							<option <c:if test="${empty answerList}"> selected</c:if> value="0">선택</option>
							<option value="Y" <c:if test="${answerList[0].colVal eq 'Y'}"> selected</c:if>>YES</option>
							<option value="N" <c:if test="${answerList[0].colVal eq 'N'}"> selected</c:if>>NO</option>
						</select>						
					</div>
				</td>
			</tr>					
			<tr>
				<td>
					<p class="td_p"><spring:message code='text.consult.field.consultKind1Opt2'/></p>
				</td>
				<td colspan="2" align="center">
					<div class="td_p">							
						<select id="product102" name="product102" style="width:60px;" onchange="checkNoCount('product102');">
						<option <c:if test="${empty answerList}"> selected</c:if> value="0">선택</option>
							<option value="Y" <c:if test="${answerList[1].colVal eq 'Y'}"> selected</c:if>>YES</option>
							<option value="N" <c:if test="${answerList[1].colVal eq 'N'}"> selected</c:if>>NO</option>
						</select>						
					</div>
				</td>
			</tr>
			<tr>						
				<td ><p class="td_p"><spring:message code='text.consult.field.consultKind1Opt3'/></p></td>				
				<td colspan="2" align="center">
					<div class="td_p">							
						<select id="product103" name="product103" style="width:60px;" onchange="checkNoCount('product103');">
							<option <c:if test="${empty answerList}"> selected</c:if> value="0">선택</option>
							<option value="Y" <c:if test="${answerList[2].colVal eq 'Y'}"> selected</c:if>>YES</option>
							<option value="N" <c:if test="${answerList[2].colVal eq 'N'}"> selected</c:if>>NO</option>
						</select>						
					</div>
				</td>				
			</tr>
			<tr>						
				<td><p class="td_p"><spring:message code='text.consult.field.consultKind1Opt4'/></p></td>
				<td colspan="2" align="center">
					<div class="td_p">							
						<select id="product104" name="product104" style="width:60px;" onchange="checkNoCount('product104');">
						<option <c:if test="${empty answerList}"> selected</c:if> value="0">선택</option>
					<option value="Y" <c:if test="${answerList[3].colVal eq 'Y'}"> selected</c:if>>YES</option>
					<option value="N" <c:if test="${answerList[3].colVal eq 'N'}"> selected</c:if>>NO</option>
						</select>						
					</div>
				</td>
			</tr>
			<tr>
				<th><p>파일첨부</p></th>
				<td colspan="3">
					<input type="hidden" name="kindFile1" id="kindFile1" value="${vendor.atchFileKindCd }" /> 	
					<input type="file" name="kindAttachFile1" id="kindAttachFile1" class="docUpload" value="" style="width: 500px; height: 18px;">
					<c:if test="${not empty vendor.atchFileKindCd }">
						<br/><c:out value='${vendor.atchFileKindCd}'/><span style="margin-left: 20px"></span>&nbsp;<a href="javascript:downloadConsultDocumnet('<c:out value="${vendor.bmanNo}"/>')"><spring:message code='text.consult.field.docDownload'/></a>
																 <span style="margin-left: 20px"></span>&nbsp;<a href="javascript:deleteConsultDocumnet('<c:out value="${vendor.bmanNo}"/>')"><spring:message code='text.consult.field.docDelete'/></a>
					</c:if>
					<br>
					<font color="red"><spring:message code='text.consult.field.5mpptXls'/></font>	
				</td>
			</tr>
				</tbody>
				</table>
			</th>
			</tr>			
			<tr>
			
			
			
		
						
			<table id="test20" cellpadding="0" cellspacing="0" border="0" width="100%">
				<colgroup>
					<col width="70px">
					<col width="300px">
					<col width="30px">
					<col width="30px">
				</colgroup>		
			<tbody>
			
			<tr>	
				<th rowspan="2"><p><spring:message code='text.consult.field.consultAnswer'/></p></th>
				<th rowspan="2" colspan="1"><p><spring:message code='text.consult.field.disqualification'/></p></th>
				<td colspan="2" align="center"><spring:message code='text.consult.field.evaluation'/></td>
				<tr>
					<td align="center">YES</td>
					<td align="center">NO</td>
				</tr>
			</tr>			
			<tr>
				<th  rowspan="4"><p><spring:message code='text.consult.field.consultKind2'/></p></th>
				<td><p class="td_p"><spring:message code='text.consult.field.consultKind2Opt1'/></p></td>
				<td colspan="2" align="center">
					<div class="td_p">							
						<select id="support201" name="support201" style="width:60px;" onchange="checkNoCount('support201');">
							<option <c:if test="${empty answerList}"> selected</c:if> value="0">선택</option>
							<option value="Y" <c:if test="${answerList[0].colVal eq 'Y'}"> selected</c:if>>YES</option>
							<option value="N" <c:if test="${answerList[0].colVal eq 'N'}"> selected</c:if>>NO</option>
						</select>						
					</div>
				</td>
			</tr>					
			<tr>
				<td >
					<p class="td_p"><spring:message code='text.consult.field.consultKind2Opt2'/></p>
				</td>
				<td colspan="2" align="center">
					<div class="td_p">							
						<select id="support202" name="support202" style="width:60px;" onchange="checkNoCount('support202');">
						<option <c:if test="${empty answerList}"> selected</c:if> value="0">선택</option>
							<option value="Y" <c:if test="${answerList[1].colVal eq 'Y'}"> selected</c:if>>YES</option>
							<option value="N" <c:if test="${answerList[1].colVal eq 'N'}"> selected</c:if>>NO</option>
						</select>						
					</div>
				</td>
			</tr>
			<tr>						
				<td ><p class="td_p"><spring:message code='text.consult.field.consultKind2Opt3'/></p></td>
				
				<td colspan="2" align="center">
					<div class="td_p">							
						<select id="support203" name="support203" style="width:60px;" onchange="checkNoCount('support203');">
							<option <c:if test="${empty answerList}"> selected</c:if> value="0">선택</option>
							<option value="Y" <c:if test="${answerList[2].colVal eq 'Y'}"> selected</c:if>>YES</option>
							<option value="N" <c:if test="${answerList[2].colVal eq 'N'}"> selected</c:if>>NO</option>
						</select>						
					</div>
				</td>
				
			</tr>
			<tr>						
				<td><p class="td_p"><spring:message code='text.consult.field.consultKind2Opt4'/></p></td>
				<td colspan="2" align="center">
					<div class="td_p">							
						<select id="support204" name="support204" style="width:60px;" onchange="checkNoCount('support204');">
						<option <c:if test="${empty answerList}"> selected</c:if> value="0">선택</option>
					<option value="Y" <c:if test="${answerList[3].colVal eq 'Y'}"> selected</c:if>>YES</option>
					<option value="N" <c:if test="${answerList[3].colVal eq 'N'}"> selected</c:if>>NO</option>
						</select>						
					</div>
				</td>
			</tr>			
			<tr>
				<th><p>파일첨부</p></th>
				<td colspan="3">
					<input type="hidden" name="kindFile2" id="kindFile2" value="${vendor.atchFileKindCd }" /> 	
					<input type="file" name="kindAttachFile2" id="kindAttachFile2" class="docUpload" value="" style="width: 500px; height: 18px;">
					<c:if test="${not empty vendor.atchFileKindCd }">
						<br/><c:out value='${vendor.atchFileKindCd}'/>
							<span style="margin-left: 20px"></span>&nbsp;
								<a href="javascript:downloadConsultDocumnet('<c:out value="${vendor.bmanNo}"/>')">
									<spring:message code='text.consult.field.docDownload'/>
								</a>
								<span style="margin-left: 20px"></span>&nbsp;
								<a href="javascript:deleteConsultDocumnet('<c:out value="${vendor.bmanNo}"/>')">
									<spring:message code='text.consult.field.docDelete'/>
								</a>
					</c:if>
					<br>
					<font color="red"><spring:message code='text.consult.field.5mpptXls'/></font>	
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
				<th rowspan="2"><p><spring:message code='text.consult.field.consultAnswer'/></p></th>
				<th rowspan="2" colspan="1"><p><spring:message code='text.consult.field.disqualification'/></p></th>
				<td colspan="2" align="center"><spring:message code='text.consult.field.evaluation'/></td>
				<tr>
					<td align="center">YES</td>
					<td align="center">NO</td>
				</tr>
			</tr>			
			<tr>
				<th  rowspan="4"><p><spring:message code='text.consult.field.consultKind3'/></p></th>
				<td><p class="td_p"><spring:message code='text.consult.field.consultKind3Opt1'/></p></td>
				<td colspan="2" align="center">
					<div class="td_p">							
						<select id="tenant301" name="tenant301" style="width:60px;" onchange="checkNoCount('tenant301');">
							<option <c:if test="${empty answerList}"> selected</c:if> value="0">선택</option>
							<option value="Y" <c:if test="${answerList[0].colVal eq 'Y'}"> selected</c:if>>YES</option>
							<option value="N" <c:if test="${answerList[0].colVal eq 'N'}"> selected</c:if>>NO</option>
						</select>						
					</div>
				</td>
			</tr>					
			<tr>
				<td >
					<p class="td_p"><spring:message code='text.consult.field.consultKind3Opt2'/></p>
				</td>
				<td colspan="2" align="center">
					<div class="td_p">							
						<select id="tenant302" name="tenant302" style="width:60px;" onchange="checkNoCount('tenant302');">
							<option <c:if test="${empty answerList}"> selected</c:if> value="0">선택</option>
							<option value="Y" <c:if test="${answerList[1].colVal eq 'Y'}"> selected</c:if>>YES</option>
							<option value="N" <c:if test="${answerList[1].colVal eq 'N'}"> selected</c:if>>NO</option>
						</select>						
					</div>
				</td>
			</tr>
			<tr>						
				<td ><p class="td_p"><spring:message code='text.consult.field.consultKind3Opt3'/></p></td>
				
				<td colspan="2" align="center">
					<div class="td_p">							
						<select id="tenant303" name="tenant303" style="width:60px;" onchange="checkNoCount('tenant303');">
							<option <c:if test="${empty answerList}"> selected</c:if> value="0">선택</option>
							<option value="Y" <c:if test="${answerList[2].colVal eq 'Y'}"> selected</c:if>>YES</option>
							<option value="N" <c:if test="${answerList[2].colVal eq 'N'}"> selected</c:if>>NO</option>
						</select>						
					</div>
				</td>
				
			</tr>
			<tr>						
				<td><p class="td_p"><spring:message code='text.consult.field.consultKind3Opt4'/></p></td>
				<td colspan="2" align="center">
					<div class="td_p">							
						<select id="tenant304" name="tenant304" style="width:60px;" onchange="checkNoCount('tenant304');">
							<option <c:if test="${empty answerList}"> selected</c:if> value="0">선택</option>
					<option value="Y" <c:if test="${answerList[3].colVal eq 'Y'}"> selected</c:if>>YES</option>
					<option value="N" <c:if test="${answerList[3].colVal eq 'N'}"> selected</c:if>>NO</option>
						</select>						
					</div>
				</td>
			</tr>
			<tr>
				<th><p>파일첨부</p></th>
				<td colspan="3">
					<input type="hidden" name="kindFile3" id="kindFile3" value="${vendor.atchFileKindCd }" /> 	
					<input type="file" name="kindAttachFile3" id="kindAttachFile3" class="docUpload" value="" style="width: 500px; height: 18px;">
					<c:if test="${not empty vendor.atchFileKindCd }">
						<br/><c:out value='${vendor.atchFileKindCd}'/><span style="margin-left: 20px"></span>&nbsp;<a href="javascript:downloadConsultDocumnet('<c:out value="${vendor.bmanNo}"/>')"><spring:message code='text.consult.field.docDownload'/></a>
																 <span style="margin-left: 20px"></span>&nbsp;<a href="javascript:deleteConsultDocumnet('<c:out value="${vendor.bmanNo}"/>')"><spring:message code='text.consult.field.docDelete'/></a>
					</c:if>
					<br>
					<font color="red"><spring:message code='text.consult.field.5mpptXls'/></font>	
				</td>
			</tr>
				</tbody>
				</table>
					</tr>
					<tr>
	<table id="test40" style="display:none" cellpadding="0" cellspacing="0" border="0" width="100%">

	<colgroup>
		<col width="70px">
		<col width="300px">
		<col width="30px">
		<col width="30px">
	</colgroup>		
		<tbody>
		<tr>	
				
				<th rowspan="2"><p><spring:message code='text.consult.field.consultAnswer'/></p></th>
				<th rowspan="2" colspan="1"><p><spring:message code='text.consult.field.disqualification'/></p></th>
				<td colspan="2" align="center"><spring:message code='text.consult.field.evaluation'/></td>
			<tr>
				<td align="center">YES</td>
				<td align="center">NO</td>
			</tr>
			</tr>			
			<tr>
				<th  rowspan="4"><p><spring:message code='text.consult.field.consultKind4'/></p></th>
				<td><p class="td_p"><spring:message code='text.consult.field.consultKind4Opt1'/></p></td>
				<td colspan="2" align="center">
					<div class="td_p">							
						<select id="product401" name="product401" style="width:60px;" value="" onchange="checkNoCount('product401');">
							<option <c:if test="${empty answerList}"> selected</c:if> value="0">선택</option>
							<option value="Y" <c:if test="${answerList[0].colVal eq 'Y'}"> selected</c:if>>YES</option>
							<option value="N" <c:if test="${answerList[0].colVal eq 'N'}"> selected</c:if>>NO</option>
						</select>						
					</div>
				</td>
			</tr>					
			<tr>
				<td>
					<p class="td_p"><spring:message code='text.consult.field.consultKind4Opt2'/></p>
				</td>
				<td colspan="2" align="center">
					<div class="td_p">							
						<select id="product402" name="product402" style="width:60px;" onchange="checkNoCount('product402');">
						<option <c:if test="${empty answerList}"> selected</c:if> value="0">선택</option>
							<option value="Y" <c:if test="${answerList[1].colVal eq 'Y'}"> selected</c:if>>YES</option>
							<option value="N" <c:if test="${answerList[1].colVal eq 'N'}"> selected</c:if>>NO</option>
						</select>						
					</div>
				</td>
			</tr>
			<tr>						
				<td ><p class="td_p"><spring:message code='text.consult.field.consultKind4Opt3'/></p></td>				
				<td colspan="2" align="center">
					<div class="td_p">							
						<select id="product403" name="product403" style="width:60px;" onchange="checkNoCount('product403');">
							<option <c:if test="${empty answerList}"> selected</c:if> value="0">선택</option>
							<option value="Y" <c:if test="${answerList[2].colVal eq 'Y'}"> selected</c:if>>YES</option>
							<option value="N" <c:if test="${answerList[2].colVal eq 'N'}"> selected</c:if>>NO</option>
						</select>						
					</div>
				</td>				
			</tr>
			<tr>						
				<td><p class="td_p"><spring:message code='text.consult.field.consultKind4Opt4'/></p></td>
				<td colspan="2" align="center">
					<div class="td_p">							
						<select id="product404" name="product404" style="width:60px;" onchange="checkNoCount('product404');">
						<option <c:if test="${empty answerList}"> selected</c:if> value="0">선택</option>
					<option value="Y" <c:if test="${answerList[3].colVal eq 'Y'}"> selected</c:if>>YES</option>
					<option value="N" <c:if test="${answerList[3].colVal eq 'N'}"> selected</c:if>>NO</option>
						</select>						
					</div>
				</td>
			</tr>
			<tr>
				<th><p><spring:message code='text.consult.field.docUpload'/></p></th>
				<td colspan="3">
					<input type="hidden" name="kindFile4" id="kindFile4" value="${vendor.atchFileKindCd }" /> 	
					<input type="file" name="kindAttachFile4" id="kindAttachFile4" class="docUpload" value="" style="width: 500px; height: 18px;">
					<c:if test="${not empty vendor.atchFileKindCd }">
						<br/>
						<c:out value='${vendor.atchFileKindCd}'/>
						<span style="margin-left: 20px">
						</span>&nbsp;
						
						<a href="javascript:downloadConsultDocumnet('<c:out value="${vendor.bmanNo}"/>')">
							<spring:message code='text.consult.field.docDownload'/>
						</a>
						
						<span style="margin-left: 20px"></span>&nbsp;
						
						<a href="javascript:deleteConsultDocumnet('<c:out value="${vendor.bmanNo}"/>')">
							<spring:message code='text.consult.field.docDelete'/>
						</a>
					
					</c:if>
					<br>
					<font color="red"><spring:message code='text.consult.field.5mpptXls'/></font>	
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
				<%-- <tr rowspan="5">							
					<td><spring:message code='text.consult.field.consultKind1Info'/><br></td>	
					<td rowspan="5">
					<div name="submit" id="submit" style="display:none"  class="btn_c_wrap mt25">
						<span class="btn_red"><span><a href="#" onclick="submitStepApplyKindForm();"><spring:message code='text.consult.field.ok'/></a></span></span>
					</div>
					</td>
					
				</tr> --%>
				<tr>		
					<td><spring:message code='text.consult.field.consultKind2Info'/><br></td>	
				
				</tr>
				<%-- <tr>
					<td><spring:message code='text.consult.field.consultKind3Info'/><br></td>	
				</tr>
				<tr>
					<td><spring:message code='text.consult.field.consultKind4Info'/><br></td>	
				</tr> --%>				
				</tbody>
			</table>
			
			<div name="submit" id="submit"  class="btn_c_wrap mt25">
				<span class="btn_red"><span><a href="#" onclick="submitStepApplyKindForm();"><spring:message code='text.consult.field.ok'/></a></span></span>
			</div>			
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


