<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LOTTE MART Back Office System</title>
<c:import url="/common/commonHead.do" />
<script type="text/javascript">

$(document).ready(function(){
	//상품특성 ( 가격대 )
	var prdtSpecRange  = $("#prdtSpecRange").val();
	//상품특성 ( 성별 )
	var prdtSpecSex  = $("#prdtSpecSex").val();
	//상품특성 ( 나이 )
	var prdtSpecAge  = $("#prdtSpecAge").val();
	
	//상품 플래그 (R:등록 S:조회)
	var screenFlag  = "${screenFlag}";
	
	//조회 팝업일때 임시저장 버튼 숨김처리 
	const tempSave = $("#tempSave");
	if(screenFlag == "S"){
		tempSave.hide();
	}
	
	// 체크박스 하나만 선택하게 만들기 
	$('input[type="checkbox"]').on('change', function() {
	    var name = $(this).attr('name');
	    if ($(this).is(':checked')) {
	        $('input[name="' + name + '"]').not(this).prop('checked', false);
	    }
	});
	
	
	// 저장된값이 있으면  체크박스 체크
	if (prdtSpecRange) {
		$('input[name="priceFg"][value="' + prdtSpecRange + '"]').prop('checked', true);
	}

	if (prdtSpecAge) {
		$('input[name="customAgeFg"][value="' + prdtSpecAge + '"]').prop('checked', true);
	}

	if (prdtSpecSex) {
		$('input[name="customGenderFg"][value="' + prdtSpecSex + '"]').prop('checked', true);
	}
});



/**********************************************************
 * 소구포인트 임시 저장 
 **********************************************************/
function _eventSave(){

	 var data = {
		        priceFg: "",
		        customAgeFg: "",
		        customGenderFg: "",
		        rnum:""
		    };
	
	  // 각 체크된 항목을 콤마로 구분하여 추가
   $("input[name='priceFg']:checked").each(function () {
       data.priceFg =  $(this).val();
   });
	  
   $("input[name='customAgeFg']:checked").each(function () {
       data.customAgeFg =  $(this).val();
   });
   
   $("input[name='customGenderFg']:checked").each(function () {
       data.customGenderFg =  $(this).val();
   });
   
   data.rnum = $("#rnum").val();
   data.propRegNo = $("#propRegNo").val();

	
	if (!confirm("상품의 소구 포인트를 임시저장하시겠습니까?")) {
		return;
	}
	
	//부모창에 저장된지 콜백 데이터 보내주기 
	window.opener.prodDetailcallBack(data);
	
	//팝업창 닫기
	window.close()
}


</script>
</head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<body>
	<form name="prodDetailRegPopup" id="prodDetailRegPopup" method="post" enctype="multipart/form-data">
	<input type="hidden" id="propRegNo" name="propRegNo" value="<c:out escapeXml='false' value='${propRegNo}'/>" />
	<input type="hidden" id="rnum" name="rnum" value="<c:out escapeXml='false' value='${rnum}'/>" />
	<input type="hidden" id="screenFlag" name="screenFlag" value="<c:out escapeXml='false' value='${screenFlag}'/>" />
	<input type="hidden" id="prdtSpecRange" name="prdtSpecRange" value="<c:out escapeXml='false' value='${prdtSpecRange}'/>" />
	<input type="hidden" id="prdtSpecSex" name="prdtSpecSex" value="<c:out escapeXml='false' value='${prdtSpecSex}'/>" />
	<input type="hidden" id="prdtSpecAge" name="prdtSpecAge" value="<c:out escapeXml='false' value='${prdtSpecAge}'/>" />
		<div id="popup">
			<div class="popup_contents">
				<div class="bbs_search3">
					<ul class="tit">
						<li class="tit"><span id="titleSpan2"></span> 상품특성(소구포인트)</li>
						<li class="btn">
							<a href="#" class="btn" id="tempSave" onclick="_eventSave()"><span>임시저장</span></a>													
							<a href="#" class="btn" onclick="javascript:window.close();"><span>닫기</span></a>				
						</li>
					</ul>
					<table class="bbs_list" cellspacing="0" border="0">
						<colgroup>
							<col width="20%" />
							<col width="25%" />
							<col width="25%" />
							<col width="25%" />
						</colgroup>
						<tr>
							<th>
								<span class="star">가격</span>
							</th>
							<td colspan="3">
								<input type="checkbox" name="priceFg" class="chk" value="1" />가성비
								<input type="checkbox" name="priceFg" class="chk" value="2" />프리미엄
								<input type="checkbox" name="priceFg" class="chk"  value="3" />저가형
							</td>
						</tr>
						<tr>
							<th>
								<span class="star">고객(연령)</span>
							</th>
							<td colspan="3">
								<input type="checkbox" name="customAgeFg" class="chk" value="1" />YOUNG
								<input type="checkbox" name="customAgeFg" class="chk" value="2" />SILVER
								<input type="checkbox" name="customAgeFg" class="chk"  value="3" />일반
							</td>
						</tr>
						<tr>
							<th>
								<span class="star">고객(성별)</span>
							</th>
							<td colspan="3">
								<input type="checkbox" name="customGenderFg" class="chk" value="1" />남
								<input type="checkbox" name="customGenderFg" class="chk" value="2" />여
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</form>
</body>
</html>