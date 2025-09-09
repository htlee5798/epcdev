<%@ include file="../common.jsp" %>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE>
<html>
<head>
  <meta charset="UTF-8" />
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
</head>
<body>
  <form id="form1" name="form1"  method="post" > 
    <input type="hidden" id="docNo" value="<c:out value="${param.DOC_NO}" />" />
    <input type="hidden" id="SEND_FG" />
	    <div id="popup">

	      <div id="p_title1">
	        <h1><spring:message code='text.inv.field.errorProd'/></h1>
	        <span class="logo"><img src="/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
	      </div>

	      <br>
		    <div class="popup_contents">

			  <div class="bbs_search">
  				<ul class="tit">
				  <li id="subject_stat" class="tit"><spring:message code='text.inv.field.errorProdDetail'/> (TRU)</li>
				  <li class="btn">
					  <a id="aprvButton" style="display:none;" href="javascript:forwardComentNAprvFg('Y');"  class="btn"><span>승인</span></a>
					  <a id="rejectButton" style="display:none;" href="javascript:forwardComentNAprvFg('N');"  class="btn"><span>거절</span></a>
					  <a id="saveButton" style="display:none;" href="javascript:forwardComentNAprvFg('');"  class="btn"><span>저장</span></a>
					  <a id="reqTranNumButton" style="display:none;"href="javascript:fowardTranNumber();"  class="btn"><span>운송번호 전송</span></a>
						<a href="javascript:window.print();"  class="btn"><span><spring:message code="button.common.print"/></span></a>
						<a href='/epc/common/edi/Toysrus_partner_AS_Manuals(EPC).pptx' class="btn" id="excel"><span>진행 매뉴얼</span></a>
					  <a href="javascript:window.close();"  class="btn"><span><spring:message code="button.common.close"/></span></a>
					</li>	
				</ul>
			</div>

			<div class="wrap_con">
				<div class="bbs_list">
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td width=260>
								<table width=100% height=100% cellpadding=1 cellspacing=1 border=0 bgcolor="#CECCCD">
									<tr>
										<td bgcolor=#ffffff align=center height=185>
											<div class="cycle-slideshow" data-cycle-fx=scrollHorz data-cycle-timeout=2000>
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
									<tr>
                    <td height=25 width=100 id="td-sendnum" bgcolor=#FFFFCC align=center>점포 운송번호</td> <!-- 점포TO파트너사 운송번호 -->
                    <td bgcolor=#ffffff>
                      &nbsp;<input type="text" id="SEND_NUMBER" readonly style="backgroundcolor:#ffffff;width:300px;">
                    </td>
                  </tr> 
			            <tr id="tr-tranNumber" class="tr-number">
			              <td height=25 width=100 id="td-trannum" bgcolor=#FFFFCC align=center>파트너사 운송번호</td> <!-- 파트너사TO점포 운송번호 -->
			              <td bgcolor=#ffffff>
			                &nbsp;<input type="text" id="TRAN_NUMBER" readonly style="backgroundcolor:#ffffff;width:300px;">
			              </td>
			            </tr>
								</table>
							</td>
						</tr>
						<!-- <tr id="tranNumTr">
						  <td width=260>
						  </td>
						  <td width=540>
						    <table width=100% cellpadding=1 cellspacing=1 border=0 bgcolor="#CECCCD">
						    <tr>
							    <td height=25 width=100 id="td-trannum"bgcolor=#FFFFCC align=center>운송번호</td> 운송번호
	                <td bgcolor=#ffffff>
	                   &nbsp;<input type="text" id="TRAN_NUMBER" readonly style="backgroundcolor:#ffffff;width:300px;">
	                </td>
	              </tr> 
                </table>
						  </td>
						</tr> -->
						<tr>
							<td colspan=2 width=100% bgcolor=#ffffff align=center>
								<!-- 협의내역 및 조치 내역-->
								<table width=100% cellpadding=1 cellspacing=1 border=0 bgcolor="#CECCCD">
									<tr>
									  <td height=25 width=260 bgcolor="#88aaff" align=center>점포사유 확인 창
                    </td>
										<td height=25 width=260 bgcolor="#88aaff" align=center><spring:message code='text.inv.field.t01115'/>
										</td>
										<td height=25 width=260 bgcolor="#88aaff" align=center><spring:message code='text.inv.field.t01116'/>
										</td>
										<td height=25 bgcolor="#88aaff" align=center><spring:message code='text.inv.field.t01117'/>
										</td>
									</tr>
									<tr>
									  <td rowspan=4 width=260 height=120 bgcolor=#ffffff align=center>
                      <textarea id="LDESC" readOnly style="width:100%;height:100%;background-color:#CECCCD;"></textarea>
                    </td>
										<td rowspan=4 width=260 height=120 bgcolor=#ffffff align=center>
											<textarea id="VEN_CMT" readOnly style="width:100%;height:100%;background-color:#CECCCD;"></textarea>
										</td>
										<td rowspan=4 width=260 height=120 bgcolor=#ffffff align=center>
											<textarea id="EDI_CMT" readOnly style="width:100%;height:100%;background-color:#CECCCD;"></textarea>
										</td>
										<td rowspan=4 width=260 height=120 bgcolor=#ffffff align=center>
											<textarea id="BAD_CMT" name="p_coment" readOnly style="width:100%;height:100%;background-color:#CECCCD;" maxlength=100 style="width:100%;height:100%;"></textarea>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div><!-- class popup_contents// -->

	</div><!-- id popup// -->

	</form>
  <!-- include jQuery -->
  <script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.js"></script>
  <!-- include Cycle2 -->
  <script src="https://cdn.jsdelivr.net/gh/malsup/cycle2@2.1.6/build/jquery.cycle2.min.js"></script>
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
		} else {
			if (result.MSGTYP && result.MSGTYP === 'S') {
			  alert("운송번호가 전송되었습니다.");
			  location.reload();
			}
			else if (result.RESPCOMMON && result.RESPCOMMON.ZPOSTAT == 'S') {
				alert('<spring:message code="msg.common.success.save" />');
				location.reload();
			}
		}
	}

  function hideTranNumElem(stat) {
    let trNumElem = $(".tr-number");
    if (stat === 'A' || stat === 'B' || stat === '1') {
      trNumElem.css("display","none");
    } 
    
    return true;
  }
	
	function showSubjectStat(stat) {
	  let subject = $("#subject_stat").text();
    let statMsg = "";
	  let subjectStat = "";
	  
	  if (stat === 'A') {
	    statMsg = "초기등록";
		} else if (stat === 'B') {
		  statMsg = "진행중";
	  } else if (stat === 'C') {
	    statMsg = "완료";
	  } else if (stat === '1') {
	    statMsg = "파트너사 검토";
	  } else if (stat === '2') {
	    statMsg = "점포 상품회송";
	  } else if (stat === '3') {
	    statMsg = "파트너사 상품회송";
	  } else if (stat === '4') {
	    statMsg = "점포 조치완료중";
	  }

	  if (statMsg) {
	    subjectStat = subject + "   ※" + statMsg;
	    $("#subject_stat").text(subjectStat);
	    return true;
		}
	  
	  return false;
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
		$('#SEND_NUMBER').val(result.SEND_NUMBER);
		$('#TRAN_NUMBER').val(result.TRAN_NUMBER);
		$('#LDESC').val(result.LDESC);
		$('#EDI_CMT').val(result.EDI_CMT);
		$('#VEN_CMT').val(result.VEN_CMT);
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

		var sendFg = result.SEND_FG.replaceAll(' ',''); 
		$('#SEND_FG').val(sendFg);
		
		hideTranNumElem(sendFg);
		showSubjectStat(sendFg);
		
		if (sendFg === '2' || sendFg === 'C') {} //C 완료 상태
    else if (sendFg === '1') 
		{
		  $('#BAD_CMT').attr('readonly', false);
			$('#BAD_CMT').css('background-color', '');
			$('#VEN_EMP_NM').attr('readonly', false);
			$('#aprvButton').show();
			$('#rejectButton').show();
		} else if (sendFg === 'B') {
	    $('#BAD_CMT').attr('readonly', false);
	    $('#BAD_CMT').css('background-color', '');
	    $('#VEN_EMP_NM').attr('readonly', false);
	    $('#saveButton').show();
	  } else if (sendFg === '3') {
	    $('#TRAN_NUMBER').attr('readonly', false);
	    $('#TRAN_NUMBER').css('background-color', '');
	    $("#td-trannum").attr("bgcolor","#B0C4DE");
	    $('#reqTranNumButton').show();
	  }
	}

	/* 저장 (조치내역 및 승인유무) */
	function forwardComentNAprvFg(aprvFg) {
	  let processMsg = "저장";
	  if (aprvFg === "Y" ) {
	    processMsg = "승인";
	  } else if (aprvFg === "N" ) {
	    processMsg = "거절";
	  } 

	  let sendFg = $("#SEND_FG").val();
	  if(!inputValidation(sendFg)) {
		  return ;
		}
	  
	  if (!confirm(processMsg + "단계를 진행하시겠습니까?")) {
		  return ;
		}
		// 승인: Y 거절: N
		commandType = 'S';

		var dataInfo = {};
		dataInfo["DOC_NO"] 			   = $('#docNo').val();
		dataInfo["P_COMENT"] 		   = $('#BAD_CMT').val();
		dataInfo["APRV_FG"]        = aprvFg;
		dataInfo["WRITENAMEPHONE"] = $('#VEN_EMP_NM').val();
		dataInfo["REQCOMMON"] 		 = getReqCommon();

		rfcCall("INV0620" , dataInfo);
	}

	function inputValidation(sendFg) {
		if (sendFg === "1") {
		  if (!$("#BAD_CMT").val().replaceAll(' ','')) {
			  alert("조치내역을 입력해주세요.");
			  return false;			  
			}
		}

		if (sendFg === "3") {
			if (!$("#TRAN_NUMBER").val().replaceAll(' ','')) {
				alert("운송번호를 입력해주세요.");
				return false;
			}
	  }

		return true;
  }
	
	/* 운송번호 전송*/
	function fowardTranNumber() {

	  let sendFg = $("#SEND_FG").val();
	  if(!inputValidation(sendFg)) {
	    return ;
	  }
		
	  if (!confirm("운송번호 전송" + "단계를 진행하시겠습니까?")) {
		  return ;
		}
	  
	  commandType = 'S';


    var dataInfo = {};
    dataInfo["DOC_NO"]      = $('#docNo').val();
    dataInfo["TRAN_NUMBER"] = $('#TRAN_NUMBER').val();
    dataInfo["WRITENAMEPHONE"] = $('#VEN_EMP_NM').val();
    dataInfo["REQCOMMON"]   = getReqCommon();

    rfcCall("INV0621" , dataInfo);	  
	}
</script>
</body>

</html>
