<%@include file="../common.jsp" %>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>

<script>
	function PopupWindow(pageName) {
		var cw=400;
		var ch=440;
		var sw=screen.availWidth;
		var sh=screen.availHeight;
		var px=Math.round((sw-cw)/2);
		var py=Math.round((sh-ch)/2);
		window.open(pageName,"","left="+px+",top="+py+",width="+cw+",height="+ch+",toolbar=no,menubar=no,status=yes,resizable=no,scrollbars=yes");
	}

	function doSearch() {
		var searchInfo = {};
		
		// 조회기간(From)
		searchInfo["srchStartDate"] 	= $("#searchForm input[name='srchStartDate']").val();
		// 조회기간(To)
		searchInfo["srchEndDate"] 		= $("#searchForm input[name='srchEndDate']").val();
		// 점포 Array
		searchInfo["searchStoreAl"] 	= storeValArrList($("#searchForm input[name='storeVal']").val());
		// 협력업체(개별)
		searchInfo["searchEntpCd"] 		= $("#searchForm select[name='entp_cd']").val();
		// 상품코드
		searchInfo["searchProductVal"] 	= $("#searchForm input[name='productVal']").val();
		
		searchInfo["searchOrdering"]			=	$("#searchForm select[name='ordering']").val();
		
		if(dateValid()){
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'html',
				async : false,
				//url : '${ctx}/edi/product/test.json',
				url : '<c:url value="/edi/order/NEDMORD0120Select.json"/>',
				data : JSON.stringify(searchInfo),
				success : function(data) {
					$('#testTable1').html(data);
				}
			});	
		}
	}


	function doUpdate() {
		if(confirm("<spring:message code='epc.ord.confirm22.update.msg'/>")){
			if($('#searchForm input[name=firstList]').val()=="none"){
				alert("<spring:message code='epc.ord.alert22.noResult.msg'/>");
				return;
			}
			
			var $prodCd = $('#searchForm input[name=prodCd]');
			var $venCd = $('#searchForm input[name=venCd]');
			var $negoBuyPrc = $('#searchForm input[name=negoBuyPrc]');
			var $producer = $('#searchForm input[name=producer]');
			var updateParam = {};
			var arrNEDMORD0120VO = new Array();
			$('#searchForm input[name=ordSlipNo]').each(function(index){
				var NEDMORD0120VO = {};
				NEDMORD0120VO["ordSlipNo"] = $(this).val();
				NEDMORD0120VO["prodCd"] = $prodCd.eq(index).val();
				NEDMORD0120VO["venCd"] = $venCd.eq(index).val();
				NEDMORD0120VO["negoBuyPrc"] = $negoBuyPrc.eq(index).val();
				NEDMORD0120VO["producer"] = $producer.eq(index).val();
				arrNEDMORD0120VO.push(NEDMORD0120VO);
			});
			updateParam['arrParam'] = arrNEDMORD0120VO;
			$.ajax({
					contentType : 'application/json; charset=utf-8',	
					type: "post",
					dataType: "json",
					url: "<c:url value='/edi/order/NEDMORD0120Update.json'/>",
					
					data: JSON.stringify(updateParam),
					success: function(data) {
						alert("<spring:message code='epc.ord.msg.save'/>.");
						//console.log(data)
						doSearch();
					}
			});
				
		}
		
		
	}
	
	function forwardValue(){
		var form = document.forms[0];

		$("td.compare_prod").each(function(i){
		
			if($(this).text(tmp[i]) == $.trim($("input[name='pr_code']").val())){
				$("input[name='negoBuyPrc']").eq(i).val($.trim($("input[name='negoprc1']").val()));
				$("input[name='producer']").eq(i).val($.trim($("input[name='origin1']").val()));
			}
		});
	}



	function fnOnlyNumber(){
		if(event.keyCode < 48 || event.keyCode > 57)
		event.keyCode = null;
	}


	function dateValid(){

		var startDate 	= $("#searchForm input[name='srchStartDate']").val();
		var endDate 	= $("#searchForm input[name='srchEndDate']").val();
		var rangeDate = 0;
		
		if(startDate == "" || endDate == ""  ){
			alert("<spring:message code='msg.common.fail.nocalendar'/>");
			form.startDate.focus();
			return false;
		}


		// startDate, endDate 는 yyyy-mm-dd 형식

	    startDate = startDate.substring(0, 4) + startDate.substring(5, 7) + startDate.substring(8, 10);
	    endDate = endDate.substring(0, 4) + endDate.substring(5, 7) + endDate.substring(8, 10);

	   var intStartDate = parseInt(startDate);
	   var intEndDate = parseInt(endDate);
			
		
	    if (intStartDate > intEndDate) {
	        alert("<spring:message code='msg.common.fail.calendar'/>");
	        form.startDate.focus();
	        return false;
	    }

		
	    intStartDate = new Date(startDate.substring(0, 4),startDate.substring(4,6),startDate.substring(6, 8),0,0,0); 
	    endDate = new Date(endDate.substring(0, 4),endDate.substring(4,6),endDate.substring(6, 8),0,0,0); 


	    rangeDate=Date.parse(endDate)-Date.parse(intStartDate);
	    rangeDate=Math.ceil(rangeDate/24/60/60/1000);

	    /*
		if(rangeDate>30){
			alert("<spring:message code='msg.common.fail.rangecalendar_30'/>");
			form.startDate.focus();
			return false;
		}
		*/	

		return true;
	}

	/* 점포선택 초기화 */
	function storeClear() {
		$("#searchForm input[id='storeVal']").val("");
	}
	
	/* 상품선택 초기화 */
	function productClear() {
		$("#searchForm input[id='productVal']").val("");
	}

	function keyValid(obj){
		for( i=0 ; i<obj.value.length; i++){
			if(obj.value.charAt(i) <"0" || obj.value.charAt(i)>"9"){
				alert("<spring:message code='msg.common.error.noNum'/>");
				obj.focus();
				obj.value="0";
				return;
			}
			
		}
	}
	
</script>

</head>

<body>
	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>  >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form id="searchForm" name="searchForm" method="post" action="#">
		<input type="hidden" id="widthSize" name="widthSize" value="<c:out value="${param.widthSize }" />" > 
		<div id="wrap_menu">
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit"><spring:message code='epc.ord.searchCondition'/></li>
						<li class="btn">
							<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
							<a href="#" class="btn" onclick="doUpdate();"><span><spring:message code="button.common.save"/></span></a>
						</li>
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
					
					<input type="hidden" id="storeVal" name="storeVal"  value="<c:out value="${param.storeVal }" />" />
					<input type="hidden" id="productVal" name="productVal" />
					
					<input type="hidden" id="forward_ordno" name="forward_ordno" />
					<input type="hidden" id="forward_prod" name="forward_prod" />
					<input type="hidden" id="forward_ven" name="forward_ven" />
					<input type="hidden" id="forward_nego" name="forward_nego" />
					<input type="hidden" id="forward_origin" name="forward_origin" />
					
					<input type="hidden" id="storeName" name="storeName" />
					
					<colgroup>
						<col style="width:15%" />
						<col style="width:30%" />
						<col style="width:10%" />
						<col style="*" />
					</colgroup>
					<tr>
						<th><span class="star">*</span> <spring:message code='epc.ord.period'/> </th>
						<td>
							<input type="text" class="day" id="srchStartDate" name="srchStartDate" onKeyPress="fnOnlyNumber();" maxlength="10" value="<c:out value="${paramMap.srchStartDate}" />" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchStartDate');" style="cursor:hand;" />
							~
							<input type="text" class="day" id="srchEndDate" name="srchEndDate" onKeyPress="fnOnlyNumber();" maxlength="10" value="<c:out value="${paramMap.srchEndDate}" />" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchEndDate');"  style="cursor:hand;" />
						</td>
						<th><span class="star">*</span> <spring:message code='epc.ord.strCdSelect'/></th>
						<td>
							<input type="Radio" name="strCdType" <c:if test="${empty param.storeVal }"> Checked</c:if>  onclick="javascript:storeClear();"/><spring:message code='epc.ord.allStore'/>
							<input type="Radio" name="strCdType" <c:if test="${not empty param.storeVal }"> Checked</c:if> onclick="javascript:storePopUp();"/><spring:message code='epc.ord.strCdSelect'/>
						</td>
					</tr>
					<tr>
						<th><spring:message code='epc.ord.venCd'/></th>
						<td>
							<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="<c:out value='${param.entp_cd}'/>" dataType="CP" comType="SELECT" formName="form" />
						</td>
						<th><spring:message code='epc.ord.prodCd'/></th>
						<td >
							<input type="Radio"  name="prodCdType" <c:if test="${empty param.productVal }"> Checked</c:if>  onclick="javascript:productClear();"/><spring:message code='epc.ord.allProd'/>
							<input type="Radio"  name="prodCdType" <c:if test="${not empty param.productVal }"> Checked</c:if> onclick="javascript:productPopUp();"/><spring:message code='epc.ord.prodCdSelect'/>
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
						<li class="tit"><spring:message code='epc.ord.search'/></li>
					</ul>
					<div style="width:100%; height:458px;overflow-x:hidden; overflow-y:scroll; overflow-x:scroll;  table-layout:fixed;white-space:nowrap;">
						<table id="testTable1" class="bbs_list" cellpadding="0" cellspacing="0" border="0">
							<input type="hidden" name="firstList" value="none"/>
							<tr><td colspan="10" align=center><spring:message code='epc.ord.emptySearchResult'/></td></tr>
						</table>
					</div>
				</div>
			</div>
		</div>
		</form>
	</div>


	<!-- footer -->
	<div id="footer">
		<div id="footbox">
			<div class="msg" id="resultMsg"></div>
			<div class="notice"></div>
			<div class="location">
				<ul>
					<li><spring:message code='epc.ord.home'/></li>
					<li><spring:message code='epc.ord.orderInfo'/></li>
					<li><spring:message code='epc.ord.orderRes'/></li>
					<li class="last"><spring:message code='epc.ord.freshBuyUpdateInfo'/></li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>
</html>
