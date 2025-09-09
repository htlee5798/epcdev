<%@include file="../common.jsp" %>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>

<script language="JavaScript">
	var commandType = 'R';
	$(function(){
		var dataInfo = {};
		dataInfo["DOC_NO"] = '<c:out value="${param.DOC_NO}" />';
		dataInfo["REQCOMMON"] = getReqCommon(); 
		rfcCall("INV0580" , dataInfo);
	});

	/* 저장 CallBack */
	function rfcCallBack(data){
		var result = data.result;
		if(commandType == 'R'){
			pageInit(result);
			//console.log(result);	
		}else{
			if(result.RESPCOMMON.ZPOSTAT == 'S'){
				alert('<spring:message code="msg.common.success.save" />');
				location.reload();
			}
		}
	}

	function pageInit(result){
		$('#SEND_DATE').val(result.SEND_DATE);
		$('#MD_EMP_NM').val(result.MD_EMP_NM);
		$('#OCCUR_DATE').val(result.OCCUR_DATE);
		$('#STR_NM').val(result.STR_NM);
		$('#MD_STR_NM').val(result.STR_NM);
		
		$('#PROD_CD').val(result.PROD_CD);
		$('#SRCMK_CD').val(result.SRCMK_CD);
		$('#PROD_NM').val(result.PROD_NM);
		$('.VEN_NM').val(result.VEN_NM);
		$('#MD_CMT').val(result.MD_CMT);
		$('#DISC_CMT').val(result.DISC_CMT);
		$('#VEN_CMT').val(result.VEN_CMT);
		var imgPath ='http://partner.lottemart.com/epc/viewImageInven.jsp?img_file_nm=';
		$('#IMG_FILE_NM').attr("src",imgPath +result.IMG_FILE_NM);
		//$('#IMG_FILE_NM').attr("src","http://10.52.1.16/bad_prod/"+result.IMG_FILE_NM);
		//$('#SEND_FG').val(result.SEND_FG);
		if(result.SEND_FG.trim() == 'C'){ //C 완료 상태
			$('#saveButton').hide();
		}else{
			$('#VEN_CMT').attr('readonly',false);
			$('#VEN_CMT').css('background-color','');
			$('#saveButton').show();
		}
	}


</script>

</head>

<body>
			
	<form id="form1" name="form1"  method="post" >
	<input type="hidden" id="docNo" value="<c:out value="${param.DOC_NO}" />" />
	
	<div id="popup">
	    <div id="p_title1">
	        <h1><spring:message code='epc.buy.menu.rejectProd' /></h1>
	        <span class="logo"><img src="/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
	    </div>


	    <br>

		<div class="popup_contents">
	
			<div class="bbs_search">
				<ul class="tit">
					<li class="tit"><spring:message code='epc.buy.menu.rejectProdDetail' /></li>
					<li class="btn">
						<a href="javascript:window.close();"  class="btn"><span><spring:message code="button.common.close"/></span></a>
					</li>
				</ul>
			</div>

			<div class="wrap_con">
				<div class="bbs_list">
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
						
						<tr>
							<td width=260>
								<!-- 이미지-->
								<table width=100% height=100% cellpadding=1 cellspacing=1 border=0 bgcolor="#CECCCD">
									<tr>
										<td bgcolor=#ffffff align=center height=185>
											<img id="IMG_FILE_NM" src="http://partner.lottemart.com/epc/viewImageInven.jsp?img_file_nm=<c:out value="${inventoryList.imgFileNm}" />" name=imgbadprod width=260 height=185>
										</td>
									</tr>
								</table>
							</td>
							
							<td width=540>
							
								<table width=100% cellpadding=1 cellspacing=1 border=0 bgcolor="#CECCCD">
									<tr>
										<td height=25 width=100 bgcolor=#FFFFCC align=center><spring:message code='epc.buy.header.sendDt' />
										</td>
										<td bgcolor=#ffffff >
											&nbsp;<input id="SEND_DATE" type=bgcolortext name=edi_send_dy value="" readonly style="width:380px;">
										</td>
									</tr>
									<tr>
										<td height=25 width=100 bgcolor=#FFFFCC align=center><spring:message code='epc.buy.header.sendUserInfo' /></td>
										<td bgcolor=#ffffff>
											&nbsp;<input type=text id="MD_VEN_NM" class="VEN_NM" name=md_emp_nm value="" readonly style="width:120px">
											&nbsp;/&nbsp;<input id="MD_STR_NM" type=text name=md_str_nm value="" readonly style="width:150px">
										</td>
									</tr>
									<tr>
									
										<td height=25 width=100 bgcolor=#FFFFCC align=center><spring:message code='epc.buy.header.occureDtStr' /></td>
										<td bgcolor=#ffffff>
										&nbsp;<input type=text id="OCCUR_DATE" name=str_nm value="<c:out value="${inventoryList.occurDate}" />" readonly style="width:120px">
											&nbsp;/&nbsp;<input id="STR_NM" class="STR_NM" type=text name=str_nm value="<c:out value="${inventoryList.strNm}" />" readonly style="width:150px">
										</td>
										
								
										
									</tr>
									<tr>
										<td height=25 width=100 bgcolor=#FFFFCC align=center><spring:message code='epc.buy.header.prodInfo' /></td>
										<td bgcolor=#ffffff>
											&nbsp;<input type=text id="PROD_CD" name=prod_cd value="<c:out value="${inventoryList.prodCd}" />" readonly style="backgroundcolor:#ffffff;width:150px;">
											&nbsp;/&nbsp;<input id="SRCMK_CD" type=text name=srcmk_cd value="<c:out value="${inventoryList.srcmkCd}" />" readonly style="backgroundcolor:#ffffff;">
											
										</td>
									</tr>
									<tr>
										<td height=25 width=100 bgcolor=#FFFFCC align=center><spring:message code='epc.buy.header.prodNm' />&nbsp;</td>
										<td bgcolor=#ffffff>
											
											&nbsp;<input type=text id="PROD_NM" name=prod_nm readonly value="<c:out value="${inventoryList.prodNm}" />" style="backgroundcolor:#ffffff;width:380px;">
										</td>
									</tr>			
									<tr>
										<td height=25 width=100 bgcolor=#FFFFCC align=center><spring:message code='text.buy.field.venCdInfo' /></td>
										<td bgcolor=#ffffff>
											&nbsp;<input type=text class="VEN_NM" name=ven_nm value="<c:out value="${inventoryList.venNm}" />" readonly style="backgroundcolor:#ffffff;width:380px;"></td>
									</tr>			
									<tr>
										<td height=25 width=100 bgcolor=#FFFFCC align=center><spring:message code='epc.buy.header.nmPhone' /></td>
									
										<td bgcolor=#ffffff>
										 &nbsp;<input id="MD_EMP_NM" type=text name=writenamePhone  value="<c:out value="${inventoryList.venEmpNm}" />" style="backgroundcolor:#ffffff;width:200px;">
										</td>
									</tr>
								</table>
							</td>
						</tr>
						
						<tr>
							<td colspan=2 width=100% bgcolor=#ffffff align=center>
								<!-- 협의내역 및 조치 내역-->
								<table width=100% cellpadding=1 cellspacing=1 border=0 bgcolor="#CECCCD">
									<tr>
										<td height=25 width=260 bgcolor="#88aaff" align=center><spring:message code='epc.buy.header.mdCmt' />
										</td>
										<td height=25 width=260 bgcolor="#88aaff" align=center><spring:message code='epc.buy.header.discCmt' /> 
										</td>
										<td height=25 bgcolor="#88aaff" align=center><spring:message code='epc.buy.header.venCmt' />
										</td>
									</tr>
									<tr>
										<td height=80 width=260 bgcolor=#ffffff align=center>
											<textarea id="MD_CMT" name=an_md_cmt readOnly style="width:100%;height:100%;background-color:#CECCCD;"><c:out value="${inventoryList.badCmt}" /></textarea>
										</td>
										<td rowspan=3 width=260 height=80 bgcolor=#ffffff align=center>
											<textarea id="DISC_CMT" name=an_disc_cmt readOnly style="width:100%;height:100%;background-color:#CECCCD;"><c:out value="${inventoryList.ediCmt}" /></textarea>
										</td>
							
										<td rowspan=3 height=80 bgcolor=#ffffff align=center>
											<textarea id="VEN_CMT" readonly  style="width:100%;height:100%;background-color:#CECCCD;" maxlength=100 style="width:100%;height:100%;" ><c:out value="${inventoryList.venCmt}" /></textarea>
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
