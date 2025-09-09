<%--
	Page Name 	: NEDMPRO0180.jsp
	Description : PB상품 성적서 관리
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
  });

  function eventSearch() {  // 성적서 조회
    var paramInfo = {};
	var entpCd = $("#entpCd").val();

	if (entpCd) paramInfo["entpCd"] = entpCd;
	else {
      var tmpArr = [];
	  $('#entpCd option').each(function() {
	    if ($(this).val()) tmpArr.push($(this).val());
	  })
	  paramInfo['entpCds'] = tmpArr;
	} 
	  
	$.ajax({
	  contentType : 'application/json; charset=utf-8',
	  type : 'post',
	  dataType : 'json',
	  async : false,
	  url : '<c:url value="/edi/product/NEDMPRO0180Select.json"/>',
	  data : JSON.stringify(paramInfo),
	  success : function(data) {
		    setTbodyMasterValue(data);
	  }
	})
  }  

  function setTbodyMasterValue(json) { // 성적서 조회시 값 세팅
	setTbodyInit("dataListbody");

	var data = json.resultList;

	if (data.length < 1) return; 

	var countColumnIncludingDown = 7; // 성적서 파일리스트 다운로드 칼럼 포항 행 개수
	var tBodyHtmlAdded = '<tr class="r1"  bgcolor=ffffff>';
  
    for (var i = 0; i < data.length; i++) {  
      if (!data[i].uploadDt) {
        data[i].uploadDt = "-";
        data[i].expireDt = "-";
      }
	  data[i].prodNm = removeQuotesProdName(data[i].prodNm);
	  
      tBodyHtmlAdded += '<tr class="r1"  bgcolor=ffffff>'   
              + '<td align="center">' + (i+1) + '</td>' 
	  	      + '<td align="center">' + data[i].prodCd + '</td>' 
	   	      + '<td align="left" style="font-weight: bold" onClick="getPopupRegisterReport('
		      + '\'' + data[i].prodCd + '\',' + '\'' + data[i].prodNm + '\',' + '\'' + data[i].seq + '\')">' + data[i].prodNm + '</td>' 
		      + '<td align="center">' + data[i].uploadDt + '</td>' 
		      + '<td align="center">' + data[i].expireDt + '</td>' 
		      + '<td align="center">' + isReportValid(data[i].expireDt) + '</td>';
	  if (countColumnIncludingDown === $('.bbs_list th').length) {
	    tBodyHtmlAdded += '<td align="center" onClick="getPopupReportFileListForAProd(' + '\'' + data[i].prodCd + '\')">' + "◆" + '</td>';
	  }
	  tBodyHtmlAdded += '</tr>';			  			  
    }
    tBodyHtmlAdded += '</tr>';
    $("#dataListbody").append(tBodyHtmlAdded);
	
  }

  function removeQuotesProdName(prodName) {
    return prodName.replace(/[\"\']/gi,"");
  }
  
  function getPopupRegisterReport(prodCd, prodNm, seq) { // 성적서 등록 팝업

    var reportInfoForm = document.reportPopupForm;
	var title = 'regReportPopup';
	var url = '${ctx}/edi/product/NEDMPRO018001.do';
	var status = 'height=330,width=400,top=50,left=50,location,resizable';
		  
    window.open('',title,status)

    reportInfoForm.action = url;
    reportInfoForm.target = title;
    reportInfoForm.prodCd.value = prodCd;
	reportInfoForm.prodNm.value = prodNm;
	reportInfoForm.seq.value = seq;
    reportInfoForm.submit();
  }

  function isReportValid(reportExpDt) {
    var today = getToday();
	if(today>reportExpDt) {
	  return "N";
	} 

	return "Y";
  }

  function getToday(){
  	var tdDt;
  	var dt = new Date();

  	var year  = dt.getFullYear();
  	var month = dt.getMonth() + 1;
  	if (month<10)  month = "0" + month;
  	var day = dt.getDate();
  	if (day<10) day = "0" + day;

    tdDt = "" + year + month + day;
  	return tdDt;
  }

  function getPopupReportFileListForAProd(prodCd) { // 성적서 파일 리스트 팝업
	var reportInfoForm = document.reportPopupForm;
	var title = 'regReportPopup';
	var url = '${ctx}/edi/product/NEDMPRO018004.do';
	var status = 'height=300,width=410,top=50,left=50,location,resizable'
		  
    window.open('',title,status)

    reportInfoForm.action = url;
	reportInfoForm.target = title;
	reportInfoForm.prodCd.value = prodCd;
	reportInfoForm.submit();
  }

  function getPopupEntpListNotValidReport() { // 성적서 유효 파일 리스트 
	var reportInfoForm = document.reportPopupForm;
	var title = 'entpListNotValidReportPopup';
	var url = '${ctx}/edi/product/NEDMPRO018005.do';
	var status = 'height=300,width=410,top=50,left=50,location,resizable'
		  
	window.open('',title,status)

	reportInfoForm.action = url;
	reportInfoForm.target = title;
	reportInfoForm.submit();
  }

  function doExcel(tbName) {
    var tbody  = $('#' + tbName+ ' tbody');
	var vendor = $("#inputForm input[name='vendor']").val();
	var vencd  = $("#searchForm select[name='entpCd']").val();
    var venCdShowing = "";
    
	if (vencd == "") {
	  venCdShowing = vendor;
	} else {
	  venCdShowing = vencd;
	}
	
	 var bodyValue = "";
	 bodyValue += "<CAPTION>PB상품 성적서 조회<br>";
	 bodyValue += "[협력업체 : " + venCdShowing + "]";
	 bodyValue += "<br>"; 
	 bodyValue += "</CAPTION>";
	 bodyValue += tbody.parent().html();

	 $("#inputForm input[id='staticTableBodyValue']").val(bodyValue);
	
	 $("#inputForm input[id='name']").val("statics");
	 $("#inputForm").attr("target", "_blank");
	 $("#inputForm").attr("action", "<c:url value='/edi/comm/NEDPCOM0030.do'/>");
	 $("#inputForm").submit();
	
	 $("#inputForm").attr("target", "");
	 $("#inputForm").attr("action", "");
 }
</script>
</head>
<body>
  <div id="content_wrap">
    <form id="searchForm" name="searchForm" method="get" action="#">
    <div class="wrap_search">
      <div class="bbs_search">

        <ul class="tit" >
          <li class="tit">PB상품 성적서 관리</li>
          <li class="btn">
          	<a href='/epc/common/edi/LotteMart_PB reports uploading Manual.pdf' download class="btn" id="excel"><span>성적서 업로드 매뉴얼</span></a>
          	<c:if test="${IsAdminDept eq 'Y'}">
            <a href="#" class="btn" onclick="getPopupEntpListNotValidReport()">
              <span>업체리스트</span>
            </a>
            <a href="#" class="btn" onclick="doExcel('listTbl');">
              <span><spring:message code="button.common.excel"/></span>
            </a>
            </c:if>
            <a href="#" class="btn" onclick="eventSearch()">
              <span><spring:message code="button.common.inquire"/></span>
            </a>
          </li>
        </ul>

        <table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
          <colgroup>
            <col style="width:5%;" >
            <col style="width:20%;">
          </colgroup>
          <tr>

          <th><span class="star">*</span> 협력업체</th>
          <td>
            <c:choose>
              <c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
                <c:if test="${not empty param.entpCd}">
                  <input type="text" name="entpCd" id="entpCd" readonly="readonly" value="${param.entpCd}" style="width:40%;"/>
                </c:if>
                <c:if test="${empty param.entpCd}">
                  <input type="text" name="entpCd" id="entpCd" readonly="readonly" value="${epcLoginVO.repVendorId}" style="width:40%;"/>
                </c:if>
                <a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
              </c:when>

              <c:when test="${epcLoginVO.vendorTypeCd != '06'}">
                <c:if test="${not empty param.entpCd}">
                  <html:codeTag objId="entpCd" objName="entpCd" width="150px;" selectParam="<c:out value='${param.entpCd}'/>" dataType="CP" comType="SELECT" formName="form" defName="전체"  />
                </c:if>
                <c:if test="${ empty param.entpCd}">
                  <html:codeTag objId="entpCd" objName="entpCd" width="150px;" selectParam="<c:out value='${epcLoginVO.repVendorId}'/>" dataType="CP" comType="SELECT" formName="form" defName="전체"  />
                </c:if>
              </c:when>
            </c:choose>
          </td>
        </table>
      </div>
    </div>

    <div class = "wrap_con">
      <div class="bbs_list">
        <div style="width:100%; height:446px;overflow-x:hidden; overflow-y:scroll; table-layout:fixed;white-space:nowrap;">
          <table id="listTbl" cellpadding="1" cellspacing="1" border="0" width="820px" bgcolor=efefef>
            <colgroup>
              <col width="3%">
              <col width="5%">
              <col width="10%">
              <col width="5%">
              <col width="5%">
              <col width="6%">
              <c:if test="${IsAdminDept eq 'Y'}">
              <col width="5%">
              </c:if>
            </colgroup>

            <tr bgcolor="#e4e4e4">
              <th rowspan=3>No.</th>
              <th rowspan=3>상품코드</th>
              <th rowspan=3>상품명</th>
              <th rowspan=3>성적서 인증 날짜</th>
              <th rowspan=3>성적서 만료 날짜</th>
              <th rowspan=3>성적서 유효 유무</th>
              <c:if test="${IsAdminDept eq 'Y'}">
              <th rowspan=3>성적서 파일</th>
              </c:if>
            </tr>
		    
		    <tr>
              <tbody id="dataListbody"/>
			</tr>
          </table>
        </div>
      </div>
    </div>
    </form>
    
    <form id="reportPopupForm" name="reportPopupForm" method="post" action="#">
	    <input type="hidden" name="prodCd"/>
	    <input type="hidden" name="prodNm"/>
	    <input type="hidden" name="seq"/>
    </form>
    
    <form name="inputForm"	id="inputForm" method="post" action="#">
        <input type="hidden" name="vendor" id="vendor" value="<c:out value='${paramMap.ven }'/>">
        <input type="hidden" name="staticTableBodyValue" id="staticTableBodyValue">
        <input type="hidden" name="name" id="name">	
    </form>
  </div>
  <div id = "footer">
  </div>
</body>
</html>
