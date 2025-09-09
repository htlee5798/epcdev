<%@include file="../common.jsp" %>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>
<style>
.cycle-pager{
	text-align: center; width: 100%; z-index: 500; position: absolute; top: 2px; overflow: hidden;
}
.cycle-pager span{
	font-family: arial; font-size: 50px; width: 16px; height:16px;
	display: inline-block; color: #ddd; cursor: pointer;
}
.cycle-pager span.cycle-pager-active{color: #D69746;}
.cycle-paer >* {curosr: pointer;}
</style>
<script language="JavaScript">
	var commandType = 'R';

	$(function() {
		var dataInfo = {};

		dataInfo["DOC_NO"] 		= '<c:out value="${param.DOC_NO}" />';
		dataInfo["REQCOMMON"] 	= getReqCommon();
		rfcCall("INV0610" , dataInfo);
	});

	/* 저장 CallBack */
	function rfcCallBack(data) {
		var result = data.result;
		if (commandType == 'R') {
			pageInit(result);
			//console.log(result);
		} else {
			if (result.RESPCOMMON.ZPOSTAT == 'S') {
				alert('<spring:message code="msg.common.success.save" />');
				location.reload();
			}
		}
	}

	/* 페이지 초기화 */
	function pageInit(result) {
		$('#SEND_DATE').val(result.SEND_DATE);
		$('#MD_EMP_NM').val(result.MD_EMP_NM);
		$('#OCCUR_DATE').val(result.OCCUR_DATE);
		$('#VEN_NM').val(result.VEN_NM);
		$('#PROD_CD').val(result.PROD_CD);
		$('#SRCMK_CD').val(result.SRCMK_CD);
		$('#PROD_NM').val(result.PROD_NM);
		$('.STR_NM').val(result.STR_NM);
		$('#VEN_EMP_NM').val(result.VEN_EMP_NM);
		$('#VEN_CMT').val(result.VEN_CMT);
		$('#EDI_CMT').val(result.EDI_CMT);
		$('#BAD_CMT').val(result.BAD_CMT);
		$('#ERFMG').val(result.ERFMG + '(' + result.ERFME + ')');

		var imgPath ='http://partner.lottemart.com/epc/viewImageInven.jsp?img_file_nm=';
		//var imgPath ='http://localhost:8081/epc/viewImageInven.jsp?img_file_nm=';
		//var imgPath ='http://partnerd.lottemart.com/epc/viewImageInven.jsp?img_file_nm=';
		
		$('#IMG_FILE_NM').attr("src", imgPath + result.IMG_FILE_NM);
		$('#IMG_FILE_NM2').attr("src", imgPath + result.IMG_FILE_NM2);
		$('#IMG_FILE_NM3').attr("src", imgPath + result.IMG_FILE_NM3);		

		var imgPath2 ='/images/epc/edi/no_photo.gif';
		if(result.IMG_FILE_NM.replaceAll(' ','') == ''){
			$('#IMG_FILE_NM').attr("src", imgPath2);
		}
		if(result.IMG_FILE_NM2.replaceAll(' ','') == ''){
			$('#IMG_FILE_NM2').attr("src", imgPath2);
		}
		if(result.IMG_FILE_NM3.replaceAll(' ','') == ''){
			$('#IMG_FILE_NM3').attr("src", imgPath2);
		}

		if (result.SEND_FG.replaceAll(' ','') == 'C') { //C 완료 상태
		} else if (result.SEND_FG.replaceAll(' ','') == 'B') {
			$('#BAD_CMT').attr('readonly', false);
			$('#BAD_CMT').css('background-color', '');
			$('#VEN_EMP_NM').attr('readonly', false);
			$('#saveButton').show();
		}
	}

	/* 저장 */ 
	function forwardComent() {
	  if (!confirm("저장단계를 진행하시겠습니까?")) {
		  return ;
		}

		commandType = 'S';
		
		var dataInfo = {};
		dataInfo["DOC_NO"] 			= $('#docNo').val();
		dataInfo["P_COMENT"] 		= $('#BAD_CMT').val();
		dataInfo["WRITENAMEPHONE"] 	= $('#VEN_EMP_NM').val();
		dataInfo["REQCOMMON"] 		= getReqCommon();

		//console.log(dataInfo);
		rfcCall("INV0620" , dataInfo);
	}
</script>
<!-- include jQuery -->
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.js"></script>
<!-- include Cycle2 -->
<script src="https://cdn.jsdelivr.net/gh/malsup/cycle2@2.1.6/build/jquery.cycle2.min.js"></script>

</head>

<body>

	<form id="form1" name="form1"  method="post" >
	<input type="hidden" id="docNo" value="<c:out value="${param.DOC_NO}" />" />

	<div id="popup">
	    <!------------------------------------------------------------------ -->
	    <!--    title -->
	    <!------------------------------------------------------------------ -->
	    <div id="p_title1">
	        <h1><spring:message code='text.inv.field.errorProd'/></h1>
	        <span class="logo"><img src="/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
	    </div>
	    <!----------------:---------------------------------- end of title -- -->

	    <!------------------------------------------------------------------ -->
	    <!--    process -->
	    <!------------------------------------------------------------------ -->
	    <!--    process 없음 -->
	    <br>
	    <!------------------------------------------------ end of process -- -->
		<div class="popup_contents">

			<!------------------------------------------------------------------ -->
			<!-- 	검색조건 -->
			<!------------------------------------------------------------------ -->
			<div class="bbs_search">
				<ul class="tit">
					<li class="tit"><spring:message code='text.inv.field.errorProdDetail'/></li>
					<li class="btn">
							<a id="saveButton" style="display:none;" href="javascript:forwardComent();"  class="btn"><span><spring:message code="button.common.save"/></span></a>
							<a href="javascript:window.print();"  class="btn"><span><spring:message code="button.common.print"/></span></a>
							<a href="javascript:window.close();"  class="btn"><span><spring:message code="button.common.close"/></span></a>
					</li>
				</ul>
			</div>
			<!----------------------------------------------------- end of 검색조건 -->

			<!-- -------------------------------------------------------- -->
			<!--	검색내역 	-->
			<!-- -------------------------------------------------------- -->
			<div class="wrap_con">
				<div class="bbs_list">
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">

						<!-- (상세내역부) -->
						<tr>
							<td width=260>
								<!-- 이미지-->
								<table width=100% height=100% cellpadding=1 cellspacing=1 border=0 bgcolor="#CECCCD">
								<tr>
									<td bgcolor=#ffffff align=center height=185>
										<div class="cycle-slideshow"
											data-cycle-fx=scrollHorz
											data-cycle-timeout=2000
										>
											<div class="cycle-pager"></div>
												<img id="IMG_FILE_NM" name=imgbadprod width=260 height=185>
												<img id="IMG_FILE_NM2" name=imgbadprod1 width=260 height=185>
												<img id="IMG_FILE_NM3" name=imgbadprod2 width=260 height=185>
										</div>



									</td>
								</tr>
								</table>
							</td>

							<td width=540>
								<!-- 상세내역 -->
								<table width=100% cellpadding=1 cellspacing=1 border=0 bgcolor="#CECCCD">
									<tr>
										<td height=25 width=100 bgcolor=#FFFFCC align=center><spring:message code='text.inv.field.reqDate'/>	<!-- 의뢰일자 -->
										</td>
										<td bgcolor=#ffffff >
											&nbsp;<input id="SEND_DATE" type=bgcolortext name=edi_send_dy value="<c:out value="${inventoryList.sendDate}" />" readonly style="width:300px;">
										</td>
									</tr>
									<tr>
										<td height=25 width=100 bgcolor=#FFFFCC align=center><spring:message code='text.inv.field.regDate'/></td>	<!-- 등록일자 -->
										<td bgcolor=#ffffff>
											&nbsp;<input id="OCCUR_DATE" type=text readonly style="width:300px;">
										</td>
									</tr>
									<tr>
										<td height=25 width=100 bgcolor=#FFFFCC align=center><spring:message code='text.inv.field.strNm'/></td>	<!-- 점포명 -->
										<td bgcolor=#ffffff>
										&nbsp;<input type="text" class="STR_NM" readonly style="width:300px;">
										</td>
									</tr>
									<tr>
										<td height=25 width=100 bgcolor=#FFFFCC align=center><spring:message code='text.inv.field.srcmkCd'/></td>	<!-- 판매코드 -->
										<td bgcolor=#ffffff>
											&nbsp;<input id="SRCMK_CD" type=text  readonly style="backgroundcolor:#ffffff; width:300px;">	<!-- 판매코드 -->

										</td>
									</tr>
									<tr>
										<td height=25 width=100 bgcolor=#FFFFCC align=center><spring:message code='text.inv.field.t01112'/>&nbsp;</td>	<!-- 상품명 -->
										<td bgcolor=#ffffff>

											&nbsp;<input type=text id="PROD_NM" readonly style="backgroundcolor:#ffffff;width:300px;">	<!-- 상품명 -->
										</td>
									</tr>
									<tr>
										<td height=25 width=100 bgcolor=#FFFFCC align=center><spring:message code='text.inv.field.t01118'/></td>	<!-- 수중량(단위) -->
										<td bgcolor=#ffffff>
											&nbsp;<input type="text" id="ERFMG" readonly style="backgroundcolor:#ffffff;width:300px;"></td>	<!-- 수중량(단위) -->
									</tr>

								</table>
							</td>
						</tr>

						<tr>
							<td colspan=2 width=100% bgcolor=#ffffff align=center>
								<!-- 협의내역 및 조치 내역-->
								<table width=100% cellpadding=1 cellspacing=1 border=0 bgcolor="#CECCCD">
									<tr>
										<td height=25 width=260 bgcolor="#88aaff" align=center><spring:message code='text.inv.field.t01115'/>
										</td>
										<td height=25 width=260 bgcolor="#88aaff" align=center><spring:message code='text.inv.field.t01116'/>
										</td>
										<td height=25 bgcolor="#88aaff" align=center><spring:message code='text.inv.field.t01117'/>
										</td>
									</tr>
									<tr>
										<td height=80 width=260 bgcolor=#ffffff align=center>
											<textarea id="VEN_CMT" readOnly style="width:100%;height:100%;background-color:#CECCCD;"></textarea>
										</td>
										<td rowspan=3 width=260 height=80 bgcolor=#ffffff align=center>
											<textarea id="EDI_CMT" readOnly style="width:100%;height:100%;background-color:#CECCCD;"></textarea>
										</td>
										<td rowspan=3 height=80 bgcolor=#ffffff align=center>
											<textarea id="BAD_CMT" name="p_coment" readOnly style="width:100%;height:100%;background-color:#CECCCD;" maxlength=100 style="width:100%;height:100%;"></textarea>
										</td>
									</tr>
								</table>
							</td>
						</tr>

					</table>

				</div>
			</div>
		<!-----------------------------------------------end of 검색내역  -->
		</div><!-- class popup_contents// -->

	</div><!-- id popup// -->

	</form>

</body>
</html>
