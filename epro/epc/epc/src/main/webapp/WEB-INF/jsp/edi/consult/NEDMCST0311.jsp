<%@ page  pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%> 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title><spring:message code='text.consult.field.lotteVendorConsult'/></title>

<style type="text/css">
.tab2	{width:100%; margin-left:5px; margin-right:5px; padding-bottom:1px; }
.tab2	ul	{width:100%; overflow:hidden;}
.tab2	li	{float:left; text-align:center; width:100px; height:23px; margin:1px 1px 0 0; padding:7px 10px 0; border-top:1px solid #d5d5d5; border-left:1px solid #d5d5d5; border-right:1px solid #d5d5d5; background-color:white; cursor:pointer; cursor:hand; }
.tab2	li	a:link,	.tab	li	a:hover,	.tab	li	a:active	{color:#999;}
.tab2	li.on	{font-weight:bold; border-top:1px solid #d5d5d5; border-left:1px solid #d5d5d5; border-right:1px solid #d5d5d5;}  
.tab2	li.on	a:link,	.tab	li.on	a:hover,	.tab	li.on	a:active	{font-weight:bold; color:#FFF;}  
.tab2	li.off	{border-top:1px solid #e5e5e5; border-left:1px solid #e5e5e5; border-right:1px solid #e5e5e5; border-bottom:1px solid #e5e5e5; background-color:#f5f5f5;}  /* 111128 ì¶ê° */
.tab2	li.off	a:link,	.tab	li.on	a:hover,	.tab	li.on	a:active	{font-weight:bold; color:#FFF;} 
</style>
 
<script language="JavaScript">
	function changeTab(tab){

		//alert(tab);
	 	switch(tab){
	 		case '1' : $('#tab1').removeClass("off");
					$('#tab2').addClass("off");
					$('#tab2').removeClass("on");
					$('#tab3').addClass("off");
					$('#tab3').removeClass("on");
					$('#tab4').addClass("off");
					$('#tab4').removeClass("on");
	 				$('#process1').show();
 					$('#process2').hide();
 					$('#process3').hide();
 					$('#process4').hide();
 					break;
	 		case '2' : $('#tab1').addClass("off");
					$('#tab1').removeClass("on");
					$('#tab2').addClass("on");
					$('#tab2').removeClass("off");
					$('#tab3').addClass("off");
					$('#tab3').removeClass("on");
					$('#tab4').addClass("off");
					$('#tab4').removeClass("on");
					$('#process1').hide();
					$('#process2').show();
					$('#process3').hide();
					$('#process4').hide();
 					break;
	 		case '3' : $('#tab1').addClass("off");
					$('#tab1').removeClass("on");
					$('#tab2').addClass("off");
					$('#tab2').removeClass("on");
					$('#tab3').addClass("on");
					$('#tab3').removeClass("off");
					$('#tab4').addClass("off");
					$('#tab4').removeClass("on");
					$('#process1').hide();
					$('#process2').hide();
					$('#process3').show();
					$('#process4').hide();
					break;
	 		case '4' : $('#tab1').addClass("off");
					$('#tab1').removeClass("on");
					$('#tab2').addClass("off");
					$('#tab2').removeClass("on");
					$('#tab3').addClass("off");
					$('#tab3').removeClass("on");
					$('#tab4').addClass("on");
					$('#tab4').removeClass("off");
					$('#process1').hide();
					$('#process2').hide();
					$('#process3').hide();
					$('#process4').show();
					break;
	 		default:
	 				break;
	 	}
		
}

function check_go()
{	
		var update_gb = document.MyForm.update_gb.value;
		
		if(update_gb == "0")
		{
			
			strMsg ="<spring:message code='msg.consult.require.updateGb0'/>";         
			popUrl = "pop_message_00.asp?strMsg=" + strMsg;
			openPopup2(popUrl,'ID')   
		
			return;
		}
		
		var bman_no = document.MyForm.bmanno.value;
		var reg_fg = document.MyForm.reg_fg.value;
		location.href = "consult_insertdata_2.asp";

	}

function openPopup2(openUrl, popTitle)
{	
	var WindowWidth = 280;
	var WindowHeight = 166;
	var WindowLeft = (screen.width - WindowWidth)/2;
	var WindowTop= (screen.height - WindowHeight)/2;

	NewWin = window.open(openUrl, popTitle, "titlebar=no, resizable=1, width="+WindowWidth+", height="+WindowHeight+", top="+WindowTop+", left="+WindowLeft);
	NewWin.focus();
}	



</script>


</head>


<body>

<div id="wrap">

	<div id="con_wrap">
	

		<div class="con_area">

			<!-- title -->
			<div class="con_title">
				<h1><img src="/images/epc/edi/consult/h1-advice-procedure.gif" alt="ìë´ ì ì°¨"></h1>
			</div>
			<!--// title -->

			<div class="txt_info_wrap">
				<spring:message code='text.consult.field.lotteVendorConsult'/>
			</div>

			<!-- -------------------------------------------------------- -->
			<!--	Tab (ìí/ì§ì/íëí¸)								  -->
			<!-- -------------------------------------------------------- -->
			<div class="tab2">
				<ul>
					<%-- <li id="tab1" onClick="changeTab('1');"><spring:message code='text.consult.field.consultKind1'/></li> --%>
					<li id="tab2" onClick="changeTab('2');"><spring:message code='text.consult.field.consultKind2'/></li>
					<%-- <li id="tab3" onClick="changeTab('3');"><spring:message code='text.consult.field.consultKind3'/></li>
					<li id="tab4" onClick="changeTab('4');"><spring:message code='text.consult.field.consultKind4'/></li> --%>
				</ul>
			</div>

			<!-- ------------------------------------------ -->
			<!-- ìí										-->
			<!-- ------------------------------------------ -->
			<%-- <div id="process1" class="tb_v_common_prod">
				<br>
				<div align=center>
					<img src="/images/epc/edi/consult/img_chart1.gif" alt="ìí ìë´ì ì°¨">
		 		</div>

		 		<div class="s_title mt20">
					<h2><spring:message code='text.consult.field.consultStep'/></h2>
				</div>
				<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<colgroup>
					<col width="130px"><col width="*">
				</colgroup>
				<tbody>
				<tr>
					<th><p><spring:message code='text.consult.field.consultKind1Title1'/></p></th>
					<td>
						<div class="td_p">
							<p><spring:message code='text.consult.field.consultKind1Text1'/></p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p><spring:message code='text.consult.field.consultKind1Title2'/></p></th>
					<td>
						<div class="td_p">
							<p><spring:message code='text.consult.field.consultKind1Text2'/></p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p><spring:message code='text.consult.field.consultKind1Title3'/></p></th>
					<td>
						<div class="td_p">
							<p><spring:message code='text.consult.field.consultKind1Text3'/></p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p><spring:message code='text.consult.field.consultKind1Title4'/></p></th>
					<td>
						<div class="td_p">
							<p><spring:message code='text.consult.field.consultKind1Text4'/></p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p><spring:message code='text.consult.field.consultKind1Title5'/></p></th>
					<td>
						<div class="td_p">
							<p><spring:message code='text.consult.field.consultKind1Text5'/></p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p><spring:message code='text.consult.field.consultKind1Title6'/></p></th>
					<td>
						<div class="td_p">
							<p><spring:message code='text.consult.field.consultKind1Text6'/></p>
						</div>
					</td>
				</tr>

				</tbody>
				</table>

			</div> --%>

			<!-- ------------------------------------------ -->
			<!-- ì§ì										-->
			<!-- ------------------------------------------ -->
			<div id="process2" class="tb_v_common_prod">
				<br>
				<div align=center>
					<img src="/images/epc/edi/consult/img_chart2.gif" alt="ì§ì ìë´ì ì°¨">
				</div>
				<div class="s_title mt20">
					<h2><spring:message code='text.consult.field.consultStep'/></h2>
				</div>
				<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<colgroup>
					<col width="130px"><col width="*">
				</colgroup>
				<tbody>
				<tr>
					<th><p><spring:message code='text.consult.field.consultKind2Title1'/></p></th>
					<td>
						<div class="td_p">
							<p><spring:message code='text.consult.field.consultKind2Text1'/></p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p><spring:message code='text.consult.field.consultKind2Title2'/></p></th>
					<td>
						<div class="td_p">
							<p><spring:message code='text.consult.field.consultKind2Text2'/></p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p><spring:message code='text.consult.field.consultKind2Title3'/></p></th>
					<td>
						<div class="td_p">
							<p><spring:message code='text.consult.field.consultKind2Text3'/></p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p><spring:message code='text.consult.field.consultKind2Title4'/></p></th>
					<td>
						<div class="td_p">
							<p><spring:message code='text.consult.field.consultKind2Text4'/></p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p><spring:message code='text.consult.field.consultKind2Title5'/></p></th>
					<td>
						<div class="td_p">
							<p><spring:message code='text.consult.field.consultKind2Text5'/></p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p><spring:message code='text.consult.field.consultKind2Title6'/></p></th>
					<td>
						<div class="td_p">
							<p><spring:message code='text.consult.field.consultKind2Text6'/></p>
						</div>
					</td>
				</tr>
				</tbody>
				</table>

			</div>
			<!-- ------------------------------------------ -->
			<!-- íëí¸										-->
			<!-- ------------------------------------------ -->
			<%-- <div id="process3" class="tb_v_common_prod">
				<br>
				<div align=center>
					<img src="/images/epc/edi/consult/img_chart3.gif" alt="íëí¸ ìë´ì ì°¨">
				</div>

				<div class="s_title mt20">
					<h2><spring:message code='text.consult.field.consultStep'/></h2>
				</div>
				<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<colgroup>
					<col width="130px"><col width="*">
				</colgroup>
				<tbody>
				<tr>
					<th><p><spring:message code='text.consult.field.consultKind3Title1'/></p></th>
					<td>
						<div class="td_p">
							<p><spring:message code='text.consult.field.consultKind3Text1'/></p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p><spring:message code='text.consult.field.consultKind3Title2'/></p></th>
					<td>
						<div class="td_p">
							<p><spring:message code='text.consult.field.consultKind3Text2'/></p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p><spring:message code='text.consult.field.consultKind3Title3'/></p></th>
					<td>
						<div class="td_p">
							<p><spring:message code='text.consult.field.consultKind3Text3'/></p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p><spring:message code='text.consult.field.consultKind3Title4'/></p></th>
					<td>
						<div class="td_p">
							<p><spring:message code='text.consult.field.consultKind3Text4'/></p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p><spring:message code='text.consult.field.consultKind3Title5'/></p></th>
					<td>
						<div class="td_p">
							<p><spring:message code='text.consult.field.consultKind3Text5'/></p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p><spring:message code='text.consult.field.consultKind3Title6'/></p></th>
					<td>
						<div class="td_p">
							<p><spring:message code='text.consult.field.consultKind3Text6'/></p>
						</div>
					</td>
				</tr>

				</tbody>
				</table>




			</div> --%>

			<!-- ------------------------------------------ -->
			<!-- ëª¨ë°ì¼										-->
			<!-- ------------------------------------------ -->
			<%-- <div id="process4" class="tb_v_common_prod">
				<br>
				<div align=center>
					<img src="/images/epc/edi/consult/img_chart1.gif" alt="ìí ìë´ì ì°¨">
		 		</div>

		 		<div class="s_title mt20">
					<h2><spring:message code='text.consult.field.consultStep'/></h2>
				</div>
				<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<colgroup>
					<col width="130px"><col width="*">
				</colgroup>
				<tbody>
				<tr>
					<th><p><spring:message code='text.consult.field.consultKind4Title1'/></p></th>
					<td>
						<div class="td_p">
							<p><spring:message code='text.consult.field.consultKind4Text1'/></p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p><spring:message code='text.consult.field.consultKind4Title2'/></p></th>
					<td>
						<div class="td_p">
							<p><spring:message code='text.consult.field.consultKind4Text2'/></p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p><spring:message code='text.consult.field.consultKind4Title3'/></p></th>
					<td>
						<div class="td_p">
							<p><spring:message code='text.consult.field.consultKind4Text3'/></p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p><spring:message code='text.consult.field.consultKind4Title4'/></p></th>
					<td>
						<div class="td_p">
							<p><spring:message code='text.consult.field.consultKind4Text4'/></p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p><spring:message code='text.consult.field.consultKind4Title5'/></p></th>
					<td>
						<div class="td_p">
							<p><spring:message code='text.consult.field.consultKind4Text5'/></p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p><spring:message code='text.consult.field.consultKind4Title6'/></p></th>
					<td>
						<div class="td_p">
							<p><spring:message code='text.consult.field.consultKind4Text6'/></p>
						</div>
					</td>
				</tr>

				</tbody>
				</table>

			</div> --%>
<!-- 
			<div class="s_title">
				<h2>íë ¥íì¬ ì ì  ë° ì´ì©</h2>
			</div>

			<p><img src="/images/epc/edi/consult/img-chart.gif" alt=""></p>

			<div class="s_title mt20">
				<h2>ìë´ì ì°¨</h2>
			</div>

			<div class="tb_v_common_prod">
				<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<colgroup>
					<col width="130px"><col width="*">
				</colgroup>
				<tbody>
				<tr>
					<th><p>ìë´ì ì²­</p></th>
					<td>
						<div class="td_p">
							<p>ë¹ì¬ ííì´ì§ Vendor Pollì íµí´ ë¡¯ë°ë§í¸ Value Chainì ì ìíì¬ ë´ë¹ MDìê² ìë´ ì ì²­ì íë©°,<br>ì¶ê°ì ì¸ ìì²´ì ë³´ë¥¼ ìë ¥í©ëë¤.</p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p>ìë¥ì¬ì¬</p></th>
					<td>
						<div class="td_p">
							<p>ìì²´ê° ì ì²­í ì ë³´ë¥¼ íê°íì¬ ííí ì¬ë¶ë¥¼ ê²°ì íê² ëë©°, ííí ì¼ì ì MDê° íë ¥ìì²´ì ì°ë½íê² ë©ëë¤.</p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p>ííí</p></th>
					<td>
						<div class="td_p">
							<p>ê·ì¬ì íì¬ ë° ìí ìê° ìê°ì ëë¦¬ê³  íê°ììì´ ê³µì íê³  ê°ê´ì ì¸ íê°ë¥¼ íê² ë©ëë¤.</p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p>ìì ê²°ì </p></th>
					<td>
						<div class="td_p">
							<p>ííí ê²°ê³¼ ë° MD íì¥(ê³µì¥)ì¡°ì¬ ê³¼ì ì íµí´ í´ë¹ìì²´ì ìµì¢ ìì  ì¬ë¶ê° ê²°ì ëë©° ê²°ê³¼ë ííí ë° MD íì¥(ê³µì¥)<br>ì¡°ì¬í íµë³´ë¥¼ íê² ë©ëë¤.</p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p>ìì ë±ë¡ ìì</p></th>
					<td>
						<div class="td_p">
							<p>ìì  ê²°ì ì ë°ë¼ ìì ì ê´ë ¨ë ìë¥êµ¬ë¹ ë° ê³ì½ì ìì±ì ì§íí©ëë¤.</p>
							<ul>
								<li>- ì¬ììë±ë¡ì¦, ì¬ììë±ë¡ ì¦ëªì</li>
								<li>- ë²ì¸(ê°ì¸)ì¸ê°ì¦ëªì, ë±ê¸°ë¶ë±ë³¸</li>
								<li>- íµì¥ê°ì¤ì ì²­ì, íµì¥ì¬ë³¸</li>
								<li>- ê¸°ìêµ¬ë§¤ì¹´ëë±ë¡ì ì²­íì¸ì</li>
								<li>- ì¬ë¬´ì í, ê¸°í ì¤ë¹ìë¥ (ë´ë¹ MD ë¬¸ì)</li>
							</ul>
						</div>
					</td>
				</tr>
				<tr>
					<th><p>ë©í(ìíì ì¹)</p></th>
					<td>
						<div class="td_p">
							<p>ìì ë±ë¡ì´ ìë£ë ì´íìë MDì ì´ëë°ì£¼ì ì´ì ë°ë¥¸ ë©íì íìë©´ ë©ëë¤.<br>ì¼ë¶ íëª©ì ìì ì 3ê°ìê° ìí ì ì¹ë¥¼ íµíì¬ ìíì±ì ê²ì¦íë íê°ê³¼ì ì ê±°ì¹ê² ë©ëë¤.</p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p>ì¬íê´ë¦¬</p></th>
					<td>
						<div class="td_p">
							<p>ìì ë ìíì ëí ì¤ì  ê´ë¦¬ë¥¼ íµíì¬ íê° ê´ë¦¬í©ëë¤.</p>
						</div>
					</td>
				</tr>
				</tbody>
				</table>
			</div>
-->

		</div>


	</div>

</div>

<form name="MyForm" method="post">

				<input name="update_gb" type="hidden" value=""> 
				<input name="bmanno" type="hidden" value="">
				<input name="reg_fg" type="hidden" value="">
				
</form>

<script type="text/javascript" language="javascript" src="/js/front/front_input.js"></script>

</body>
</html>