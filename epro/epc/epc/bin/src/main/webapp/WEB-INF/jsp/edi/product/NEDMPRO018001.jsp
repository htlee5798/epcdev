<%--
	Page Name 	: NEDMPRO018001.jsp
	Description : PB상품 성적서 등록 팝업창
	Modification Information

	  수정일 			  수정자 				수정내용
	---------- 		---------    		-------------------------------------
	2021.11.23 		          	 		최초생성
--%>
<%@ include file="../common.jsp" %>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<style>
  tr.on {
    background-color: #E6E6E6;
  }
</style>
<script>
  $(document).ready(function() {
    init();
  });

  function init() {
    // 상품유형 초기 옵션값 설정
    var prodType = "<c:out value='${prodInfo.prodType}'/>";
    if (prodType) {
      $("#prodType").val(prodType).prop("selected",true);
    } else {
      var defaultOption = $("<option value=''>" + "-" + "</option>");
      $("#prodType").prepend(defaultOption);
      $("#prodType").val("-").prop("selected",true);
    }

    // 메세지코드 확인
    var msgCd = "<c:out value='${resultMsg}'/>";
    if (msgCd) {
      showMsgCd(msgCd);
      if (msgCd>0) window.close();
    }
  }

  function updateReport() { // 성적서 관련 정보 업데이트
    if(!checkReportInfoVal()) return;

    var regAuthDt = $('#uploadDt').val();
    setExpireDt(getExpireDtFromAuthDt(regAuthDt)); // 만료일자 계산 

    if (!confirm("해당 상품의 성적서 파일을 저장하시겠습니다??")) return;
    $("#uploadDt").val(removeHyphenInDate($('#uploadDt').val()));
    $("#expireDt").val(removeHyphenInDate($('#expireDt').val()));
    $("#prodTypeName").val($("#prodType option:checked").text());
        
    $("#registerReportDate").attr("action", "<c:url value='/edi/product/updateReportPbProd.do'/>");
    $("#registerReportDate").submit();
  }

  function downloadReport() {
    $("#packageForDown").attr("action", "<c:url value='/edi/product/downloadReportPbProd.do'/>");
    $("#packageForDown").submit();
  }

  function checkReportInfoVal() { // 성적서 저장 내용이 업데이트 되었는지
    if(!( $.trim($('#prodCd').val()) && $.trim($('#prodCd').val()) && 
	      $.trim($('#uploadDt').val())  && $.trim($('#srcFileNm').val()) &&
	      $('#prodType option:selected').val())) {
        alert("업로드 성적서 업로드 입력값을 확인해주세요");
        return false;
    } 

    return true;
  }
  
  function getExpireDtFromAuthDt(uploadDt) { // 성적서 만료 일자 계산

	if (!uploadDt) {
	  return false;
	}

	uploadDt = removeHyphenInDate(uploadDt);
	var expDt = null;

	var year = uploadDt.substr(0,4);
	var month = uploadDt.substr(4,2);
	month  = month - 1;
	var day = uploadDt.substr(6,2);
	var tmpExpDt = new Date(year,month,day);

	/* 만료일자 계산 (현재 성적서 등록일 기준 + 90일)*/
	tmpExpDt.setDate(tmpExpDt.getDate() + 90);

	var expYear  = tmpExpDt.getFullYear();
    var expMonth = tmpExpDt.getMonth() + 1;
    if(expMonth<10) expMonth = "0" + expMonth;
    var expDay = tmpExpDt.getDate();
    if(expDay<10) expDay = "0" + expDay; 

    expDt = "" + expYear + expMonth + expDay;	    
	   
    return expDt;
  }

  function setExpireDt(expireDt) {
    $('#expireDt').val(expireDt); 
  }


  function fileUpload(obj, inputName) {
    $('#' + inputName).val(obj.files[0].name);
  }

  function fileClear(file, inputName) {
    $('#' + inputName).val("");
	$('#' + file).val("");
  }

  function removeHyphenInDate(date) {
    return date.replaceAll("-","");
  }

  function updateNewReport() { // 성적서 정보 새로 갱신
    if(!checkReportInfoVal()) return;

    var regAuthDt = $('#uploadDt').val();
    setExpireDt(getExpireDtFromAuthDt(regAuthDt)); // 만료일자 계산 
        
    if (!confirm("해당 상품의 성적서 파일을 저장하시겠습니다??")) return;
    $("#uploadDt").val(removeHyphenInDate($('#uploadDt').val()));
    $("#expireDt").val(removeHyphenInDate($('#expireDt').val()));
    $("#prodTypeName").val($("#prodType option:checked").text());
    	
    $("#registerReportDate").attr("action", "<c:url value='/edi/product/updateNewReportPbProd.do'/>");
    $("#registerReportDate").submit();
  }

  function deleteReportInfo() {
    if (!$("#expireDt").val()) {
      alert("삭제할 정보가 없습니다.");
      return;
    }
        
    if (!confirm("해당 상품의 성적서 파일 정보를 삭제하시겠습니까??")) return;

    $("#registerReportDate").attr("action", "<c:url value='/edi/product/deleteReportPbProd.do'/>");
    $("#registerReportDate").submit();
  }

  function showMsgCd(msgCd) {
    if (!msgCd) { 
      return;
    }

    var msgContent = "";
  	if (msgCd<0) {
      msgContent = "[" + msgCd + "]" + " 처리에 실패하엿습니다.";
    } else if (msgCd=="100") {
      msgContent = "성적서 정보 저장이 완료되었습니다.";
    } else if (msgCd=="101") {
      msgContent = "성적서 정보 갱신이 완료되었습니다.";
    } else if (msgCd=="102") {
      msgContent = "성적서 정보 삭제가 완료되었습니다.";
    } 
	alert(msgContent);
  }
</script>
</head>
<body>
   <form id="registerReportDate" name="registerReportDate" method="post" enctype="multipart/form-data">
   <input type="hidden" id="prodCd"       name="prodCd" value="<c:out value='${prodInfo.prodCd}'/>"/>
   <input type="hidden" id="prodNm"       name="prodNm" value="<c:out value='${prodInfo.prodNm}'/>"/>
   <!-- regId는 백엔드에서 epcLoginVO로 받는다 :) 수정후 삭제  -->
   <input type="hidden" id="regId" 	   name="regId" value="ADMIN"/>
   <input type="hidden" id="prodTypeName" name="prodTypeName" />
   <input type="hidden" id="expireDt" 	   name="expireDt"  value="<c:out value='${prodInfo.expireDt}'/>" />
   <input type="hidden" id="seq" 		   name="seq"	    value="<c:out value='${prodInfo.seq}'/>" />
   
   <table cellspacing=1 cellpadding=1 border=0 bgcolor=959595 width=100%>
   <tr>
	<td bgcolor=ffffff>
	  <table cellspacing=0 cellpadding=0 border=0 width=100%>
		<tr height=30>
		  <td>&nbsp; &nbsp;<img src=/images/epc/popup/bull_01.gif border=0>&nbsp;<b>PB상품 성적서 업로드</b></td>
		  <td width=50><a href="#" class="btn" onclick="updateReport();"><span>저장</span></a></td>
		  <td width=50><a href="#" class="btn" onclick="updateNewReport();"><span>갱신</span></a></td>
		  <td width=50><a href="#" class="btn" onclick="deleteReportInfo();"><span>삭제</span></a></td>
		</tr>
		<tr><td height=2 bgcolor="f4383f" colspan=5></td></tr>
	  </table>

	  <table cellspacing=0 cellpadding=0 border=0 width=100%>
	    <tr>
		  <td colspan=2><B>&nbsp;&nbsp;&nbsp;상품명 : <c:out value='${prodInfo.prodNm}'/></B></td>
		</tr>
		<tr><td colspan=2 height=5></td></tr>
		<tr>
		  <td colspan=2><B>&nbsp;&nbsp;&nbsp;상품코드 : <c:out value='${prodInfo.prodCd}'/></B></td>
		</tr>
	      <tr><td colspan=2 height=10></td></tr>
		<tr>
		  <td><B>&nbsp;&nbsp;&nbsp;식품유형 : </B>
		  <html:codeTag objId="prodType" objName="prodType" parentCode="PRD45" comType="SELECT" formName="form"/>
		  </td>
	    </tr>
	    <tr><td colspan=2 height=15></td></tr>
		<tr>
		  <br/><td colspan=2><B>&nbsp;&nbsp;&nbsp;상품 성적서 인증날짜</B></td>
		</tr>
		<tr>
		  <td> 
		    &nbsp; &nbsp;
		    <input type="text" id="uploadDt" name="uploadDt" value="<c:out value='${prodInfo.uploadDt}'/>" onchange="javascript:calExpireDt();" readonly/>
			<img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('registerReportDate.uploadDt');" style="cursor: hand;" />
		  </td>
		</tr>
		<tr><td colspan=2 height=20></td></tr>
		<tr>
		  <td colspan=2><B>&nbsp;&nbsp;&nbsp;상품 성적서 파일 업로드</B></td>
		</tr>
		<tr>
		  <td colspan=2> &nbsp; &nbsp; 
		  <input type="hidden" id="srcFileNm" name="srcFileNm"  class="input_txt_default" readonly="readonly"/>
		  <input type="file" id="reportPbProdFile" name="reportPbProdFile"  onchange="javascript:fileUpload(this, 'srcFileNm');"/>
		<btn>
			<input type="button" id="" name="" class="btn_normal btn_gray" value="<spring:message code='button.srm.cancel'/>" onclick="javascript:fileClear('reportPbProdFile','srcFileNm')"/><%--spring:message : 취소--%>
		<btn>
		<c:if test="${not empty prodInfo.srcFileNm}">				    
		  <div>
		    &nbsp;&nbsp;&nbsp;
		    <a id="reportPbProdFileNm" name="reportPbProdFileNm" href="#" onclick="javascript:downloadReport();"><c:out value="${prodInfo.srcFileNm}"/></a>
		  </div>
		</c:if>		 
		  </td>
	    </tr>
		<tr>
		  <td colspan=2 height=40>
		    <pre>    ※ 최초 업로드시 저장 버튼 / 이후 업데이트시 갱신 버튼
            </pre>
		  </td>
	    </tr>			
      </table>
    </td>
  </tr>
  </table>
   <div id = "footer">
   </div>
 	</form>
  <form id="packageForDown" name="packageForDown" method="post">
 	<input type="hidden" id="prodCd" name="prodCd" value="<c:out value='${prodInfo.prodCd}'/>"/>
    <input type="hidden" id="prodNm" name="prodNm" value="<c:out value='${prodInfo.prodNm}'/>"/>
    <input type="hidden" id="seq"    name="seq"    value="<c:out value='${prodInfo.seq}'/>"/>
  </form>
</body>
</html>
