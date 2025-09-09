<%--

	Page Name 	: NEDMPRO0027.jsp
	Description : 영양성분 입력탭
	Modification Information

	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2022.05.12 		  PIY        	 최초생성
	
--%>
<%@ include file="../common.jsp"%>
<%@ include file="/common/scm/scmCommon.jsp"%>
<%@ include file="./CommonProductFunction.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><spring:message code='msg.product.onOff.title' /></title>

<style type="text/css">
/* TAB */
.tabs {
	height: 31px;
	background: #fff;
}

.tabs ul {
	width: 100%;
	height: 31px;
}

.tabs li {
	float: left;
	width: 130px;
	height: 29px;
	background: #fff;
	border: 1px solid #ccd0d9;
	border-radius: 2px 2px 0 0;
	font-size: 12px;
	color: #666;
	line-height: 30px;
	text-align: center;
}

.tabs li.on {
	border-bottom: #e7eaef 1px solid;
	color: #333;
	font-weight: bold;
}

.tabs li a {
	display: block;
	color: #666;
}

.tabs li.on a {
	color: #333;
	font-weight: bold;
}

</style>

<script type="text/javascript">
  /* dom이 생성되면 ready method 실행 */
  $(document).ready(function() {
    
    attachEvent.moveTab();
    modifyResultHtml.appendHtml();
    
    attachEvent.blurNutAtt(); // 영양성분속성값 입력하면 에러메세지 지우기
  });

  // Dom 이벤트 추가하는 객체
  var attachEvent = {
    moveTab : function() {
      $("#prodTabs li").click(function() {
	      var id = this.id;

	      if (id == "pro01") {
		    $("#hiddenForm").attr("action","<c:url value='/edi/product/NEDMPRO0020Detail.do'/>");
		    $("#hiddenForm").attr("method","post").submit();

	     } else if (id == "pro02") {
	       if (pgmId == "") {
	         alert("상품의 기본정보를 먼저 등록해주세요.");
	         return;
	       }
	       $("#hiddenForm").attr("action","<c:url value='/edi/product/NEDMPRO0021.do'/>");
	       $("#hiddenForm").attr("method","post").submit();

	     } else if (id == "pro03") {
	       if (pgmId == "") {
	         alert("상품의 기본정보를 먼저 등록해주세요.");
	         return;
	       }

	       $("#hiddenForm").attr("action", "<c:url value='/edi/product/NEDMPRO0022.do'/>");
	       $("#hiddenForm").attr("method", "post").submit(); 
	     }
	  });
    },
    blurNutAtt : function() {
      $(".nutValue").blur(function() {
	    if($(this).val().replace(/\s/gi, '')) {
	      nutFunction.deleteErrMsgNutAmt($(this));
		}
	  });
    }
  }

  // Dom 객체 수정하는 객체
  var modifyResultHtml = {
    createInputDom : function() { 
      var resultHtml = "";
      
      var cntNutCd = ${nutAttInfo.size()};
      if (cntNutCd == 0) {
	    var rstMsg = "입력하실 영양성분 속성값이 없습니다.";
	    resultHtml = "<tr><td colspan='4'><div style='height:30px; text-align:center; padding-top:20px;'>"
                   + rstMsg
                   + "</div></td></tr>";
        return resultHtml;
	  }

      <c:forEach var="nutAttInfo" items="${nutAttInfo}" varStatus="status">
      	var unit = "${nutAttInfo.unit}";
      	var unitStr = "";   
      	if (unit) { 
			unitStr = " (" + unit + ")" 
		}     	 
        var contentHtml = "<th><div style='text-align:center; font-weight: bold;'>" + "${nutAttInfo.nutNm}" + unitStr + "</div></th>"
        				+ "<td><div style='text-align:center;'><input id=nutCd_" + "${nutAttInfo.nutCd}" + " class='nutValue' type='text' style='text-align:right;' size='24' maxlength='14'/></div></td>"
        <c:if test="${status.count%2 ne 0}">
          resultHtml += "<tr>" + contentHtml
        </c:if>
        <c:if test="${status.count%2 eq 0}">
          resultHtml += contentHtml + "</tr>"
        </c:if>
      </c:forEach>

      if (cntNutCd % 2 != 0) {
	    resultHtml += "<td colspan='2'></td></tr>";
      }
	      
      return resultHtml;
    }, 
    appendHtml : function() {
      var tbodyTag = document.getElementById("htmlTbody");
      tbodyTag.innerHTML = this.createInputDom();
      this.getNutAmtByPgmId();
    },
    getNutAmtByPgmId : function() { 
      <c:forEach var="nutAttProd" items="${nutAttProd}">
        var nutCd = "${nutAttProd.nutCd}";
        $("#nutCd_"+nutCd).val("${nutAttProd.nutAmt}");
      </c:forEach> 
    }    
    
  }

  // 영양성분 기능 객체
  var nutFunction = {
    saveAtt : function() {

	  var paramInfo = {};

	  // 파라미터 세팅
	  var nutCdArr = new Array();
      var nutAmtArr = new Array();
	  $(".nutValue").each(function(index,nutInfo) {
	      nutCdArr.push(nutInfo.id.replace("nutCd_",""));
	      nutAmtArr.push(nutInfo.value);
      });
	  var pgmId = $("#pgmId").val();
	  var entpCd = $("#entpCd").val();
	      
	  paramInfo["nutCd"] = nutCdArr;
	  paramInfo["nutAmt"] = nutAmtArr;
	  paramInfo["pgmId"] = pgmId;
	  paramInfo["entpCd"] = entpCd;

	  if(!this.validateNutAmt()) {
	    alert("영양성분 속성 값 입력 확인 한번 부탁드립니다.");
		return;
	  }
	  
	  if (!confirm("영양성분 값을 저장하시겠습니까??")) {
        return;
      }
	      
	  $.ajax({
        contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		async : false,
		url : '<c:url value="/edi/product/updateNutAtt.json"/>',
		data : JSON.stringify(paramInfo),
		success : function(data) {
	      if (data.updateStatus == "success") {
		    alert("영양성분 저장이 완료되었습니다.");
		  } else {
			alert("처리에 실패하였습니다.");
	      } 
		}
      })
	},	
	validateNutAmt : function() {
	  var rst = true;
	  $(".nutValue").each(function(i,e) {
		  var nutAmt = $(this).val().replace(/\s/gi, '');
		  if (nutAmt === '0' || nutAmt === 0) {
			  nutAmt = 0;
			}
		  
		  var tmpRst = true;
		  var errMsg = "";

		  if (nutAmt) { 
		    var regExpNutAmtValid = /^[0-9]{1,7}(\.{1}[0-9]{1,3})?/g ;
		    var nutAmtValid = regExpNutAmtValid.exec(nutAmt);
	        if (!nutAmtValid || nutAmt != nutAmtValid[0] || nutAmt.replace('\.','').length > 10) {
	          errMsg = "숫자 10자리 소수점 세번째 자리까지 &nbsp;&nbsp;&nbsp;입력가능합니다.";
		        rst = false;
		        tmpRst = false;
	        }
		  } 
		  else { // 영양성분 속성 값이 없는 경우
		    errMsg = "성분속성 값 입력부탁드립니다.";
		    if (nutAmt === 0) 
		      errMsg = "'0'을 입력했을 경우 0.001을 입력해주세요";
		    rst = false;
		    tmpRst = false;
		  }
		  
		  if(!tmpRst) {
		    var isErrMsg = $(this).prev("div[name=error_msg]").length;
			  if (isErrMsg) {
			    nutFunction.deleteErrMsgNutAmt($(this));
			  }
			  generateMessage($(this),errMsg);
		  }
    })
    
    return rst;   
	},
	deleteErrMsgNutAmt : function(targetObj) {
	  var isErrMsg = targetObj.prev("div[name=error_msg]").length;
	  if (isErrMsg) {
	    targetObj.prev().remove();
      }
	}
  }

  
</script>
<script type="text/javascript" src="/js/epc/edi/product/validation.js"></script>
</head>

<body>
  <div id="content_wrap">
    <div>
      <div id="wrap_menu">
	    <div class="wrap_search">
		  <div id="prodTabs" class="tabs" style="padding-top: 10px;">
		    <ul>
			  <li id="pro01" style="cursor: pointer;">기본정보</li>
			  <li id="pro02" style="cursor: pointer;">상품속성</li>
			  <li id="pro03" style="cursor: pointer;">상품이미지</li>
			  <li id="pro04" class="on">영양성분</li>
		    </ul>
		  </div>
				
		  <div class="bbs_search">
		    <ul class="tit">
			  <li class="tit">
			    * 영양성분속성
			  </li>
			  <li class="btn">
			    <!-- 입력할 영양성분 속성 값이 있는 경우 -->
			  	<!-- 처음 등록 및 수정시에만 저장버튼 활성화 되도록 -->
			  	<c:if test="${nutAttInfo.size() > 0}"> 
			      <c:if test="${not empty param.mode}">
			        <c:choose>
			          <c:when test="${param.mode eq 'modify'}">
			            <a href="#" class="btn" onclick="nutFunction.saveAtt();"><span>저장</span></a>
			          </c:when>
			        </c:choose>
			      </c:if>
			      <c:if test="${empty param.mode}">
  			        <a href="#" class="btn" onclick="nutFunction.saveAtt();"><span>저장</span></a>
			      </c:if>
			    </c:if>
		      </li>
			</ul>
		  </div>
          <form name="" id="" action="" method="POST">
			<div class="bbs_list">
			  <table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0" style="border-collapse: separate; border-spacing: 0 5px;"" >
			    <tr>
			      <!-- 
                  <td colspan=3 height=40 style="color:778899;">
                    <div style='text-align:center;'><b>공사중</b></div>
                  </td>
                   -->
				</tr>
				<colgroup>
				  <col width="22%"/>
				  <col width="28%"/>
				  <col width="22%"/>
				  <col width="28%"/>
				</colgroup>
				<tbody id="htmlTbody">
              </table>
			</div>
	      </form>
		</div>
      </div>
	</div>

	<!-- footer 시작 -------------------------------------------------------------------------------------->
	<div id="footer">
	  <div id="footbox">
	    <div class="msg" id="resultMsg"></div>
		<div class="notice"></div>
		<div class="location">
		  <ul>
		   <li><spring:message code="msg.product.onOff.default.footerHome" /></li>
		   <li><spring:message code="msg.product.onOff.default.footerItem" /></li>
		   <li><spring:message code="msg.product.onOff.default.footerctrlNewItem" /></li>
		   <li class="last">
		       <spring:message code="msg.product.onOff.default.footerRegNewItem" /></li>
		   </ul>
		</div>
		</div>
	  </div>
	<!-- footer 끝 -------------------------------------------------------------------------------------->
	</div>

	<!-- 탭 이동을 위한 hiddenForm -->
	<form name="hiddenForm" id="hiddenForm">
		<input type="hidden" name="pgmId"  id="pgmId"      value="<c:out value="${productInfo.pgmId}" />" />
		<input type="hidden" name="entpCd" id="entpCd"     value="<c:out value="${productInfo.entpCd}" />" />
		<input type="hidden" name="mode"   id="mode"       value="<c:out value='${param.mode}'/>" /> 
		<input type="hidden" name="cfmFg"  id="cfmFg"      value="<c:out value='${param.cfmFg}'/>" />
	</form>


</body>
</html>
