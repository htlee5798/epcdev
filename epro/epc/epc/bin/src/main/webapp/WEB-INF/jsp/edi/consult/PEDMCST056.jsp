<%@ page  pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%> 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title>롯데마트 입점상담</title>

<style type="text/css">
.tab2	{width:100%; margin-left:5px; margin-right:5px; padding-bottom:1px; }
.tab2	ul	{width:100%; overflow:hidden;}
.tab2	li	{float:left; text-align:center; width:100px; height:23px; margin:1px 1px 0 0; padding:7px 10px 0; border-top:1px solid #d5d5d5; border-left:1px solid #d5d5d5; border-right:1px solid #d5d5d5; background-color:white; cursor:pointer; cursor:hand; }
.tab2	li	a:link,	.tab	li	a:hover,	.tab	li	a:active	{color:#999;}
.tab2	li.on	{font-weight:bold; border-top:1px solid #d5d5d5; border-left:1px solid #d5d5d5; border-right:1px solid #d5d5d5;}  
.tab2	li.on	a:link,	.tab	li.on	a:hover,	.tab	li.on	a:active	{font-weight:bold; color:#FFF;}  
.tab2	li.off	{border-top:1px solid #e5e5e5; border-left:1px solid #e5e5e5; border-right:1px solid #e5e5e5; border-bottom:1px solid #e5e5e5; background-color:#f5f5f5;}  /* 111128 추가 */
.tab2	li.off	a:link,	.tab	li.on	a:hover,	.tab	li.on	a:active	{font-weight:bold; color:#FFF;} 
</style>
 
<script language="JavaScript">
	location.href = "<c:url value="/edi/srm/SRMJONMain.do"/>";

function changeTab(tab){		

		//alert(tab);
	 	switch(tab){
	 		case '1' : $('#tab1').removeClass("off");
					$('#tab2').addClass("off");
					$('#tab2').removeClass("on");
					$('#tab3').addClass("off");
					$('#tab3').removeClass("on");
	 				$('#process1').show();
 					$('#process2').hide();
 					$('#process3').hide();
 					break;
	 		case '2' : $('#tab1').addClass("off");
					$('#tab1').removeClass("on");
					$('#tab2').addClass("on");
					$('#tab2').removeClass("off");
					$('#tab3').addClass("off");
					$('#tab3').removeClass("on");
					$('#process1').hide();
					$('#process2').show();
					$('#process3').hide();
 					break;
	 		case '3' : $('#tab1').addClass("off");
					$('#tab1').removeClass("on");
					$('#tab2').addClass("off");
					$('#tab2').removeClass("on");
					$('#tab3').addClass("on");
					$('#tab3').removeClass("off")
					$('#process1').hide();
					$('#process2').hide();
					$('#process3').show();
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
			
			strMsg ="상담신청을 먼저해 주십시요.";         
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
				<h1><img src="/images/epc/edi/consult/h1-advice-procedure.gif" alt="상담 절차"></h1>
			</div>
			<!--// title -->

			<div class="txt_info_wrap">
				<p class="txt_1">롯데마트 <span>신규입점 상담신청</span>을 해주셔서 감사합니다.</p>
				<p>롯데마트는 개방적인 입점 품평회를 실시하여 투명하고 공정한 입점 절차를 준수하고 있으며,
				<br>파트너사와 롯데마트가 상호 발전하는 관계를 만들어 가고자 합니다.</p>
				<p>상담신청 및 입점 과정은 다음과 같이 진행됩니다. 각 단계마다 요구사항을 정확히 입력하시기 바랍니다.</p>
			</div>

			<!-- -------------------------------------------------------- -->
			<!--	Tab (상품/지원/테넌트)								  -->
			<!-- -------------------------------------------------------- -->
			<div class="tab2">
				<ul>
					<li id="tab1" onClick="changeTab('1');">상품</li>
					<li id="tab2" onClick="changeTab('2');">지원</li>
					<li id="tab3" onClick="changeTab('3');">테넌트</li>
				</ul>
			</div>	
			
			<!-- ------------------------------------------ -->
			<!-- 상품										-->
			<!-- ------------------------------------------ -->
			<div id="process1" class="tb_v_common_prod">
				<br>
				<div align=center>
					<img src="/images/epc/edi/consult/img_chart1.gif" alt="상품 상담절차">
		 		</div>
		 	
		 		<div class="s_title mt20">
					<h2>▶상담절차</h2>
				</div>
				<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<colgroup>
					<col width="130px"><col width="*">
				</colgroup>
				<tbody>
				<tr>
					<th><p>상담신청</p></th>
					<td>
						<div class="td_p">
							<p>당사 홈페이지 Vendor Poll을 통해 롯데마트  Value Chain에 접속하여 담당 MD에게 상담 신청을 하며,<br>필요한 업체정보를 입력합니다.</p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p>상담결정</p></th>
					<td>
						<div class="td_p">
							<p>신청한 정보를 평가하여 상담 및 품평회 여부를 결정하게 되며, 해당 MD가 파트너사에 연락하여<br>일정 및 세부사항에 대해 안내해드립니다.</p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p>품평회 및 평가</p></th>
					<td>
						<div class="td_p">
							<p>귀사의 기업 및 상품 소개 시간을 드리고 평가위원이 공정하고 객관적인 평가를 하게 됩니다.</p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p>품질경영 평가</p></th>
					<td>
						<div class="td_p">
							<p>귀사의 현장(공장)조사 및 상품 품질 검사 과정을 통해 해당업체의 최종 입점 여부가 결정되며, <br>결과는 품평회와 현장(공장) 조사가 모두 완료된 후 통보를 하게 됩니다.</p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p>입점 및 상품 납품</p></th>
					<td>
						<div class="td_p">
							<p>입점 결정에 따라 입점에 관련된 서류구비 및 계약서 작성을 진행하며, 구비 서류는 해당 MD가 파트너사에게 안내해 드립니다. 
							<br>입점 등록이 완료된 이후에는 MD의 초도 발주에 따른 납품을 하시면 됩니다.
							</p>
							
						</div>
					</td>
				</tr>
				<tr>
					<th><p>사후 관리</p></th>
					<td>
						<div class="td_p">
							<p>당사의 윤리경영, 가치경영, 품질경영의 평가기준으로 객관화된 실적 데이터 근거 적합성 평가를 진행합니다.</p>
						</div>
					</td>
				</tr>
				
				</tbody>
				</table>
			
			</div>
			
			<!-- ------------------------------------------ -->
			<!-- 지원										-->
			<!-- ------------------------------------------ -->
			<div id="process2" class="tb_v_common_prod">
				<br>
				<div align=center>
					<img src="/images/epc/edi/consult/img_chart2.gif" alt="지원 상담절차">
				</div>
				<div class="s_title mt20">
					<h2>▶상담절차</h2>
				</div>
				<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<colgroup>
					<col width="130px"><col width="*">
				</colgroup>
				<tbody>
				<tr>
					<th><p>상담신청</p></th>
					<td>
						<div class="td_p">
							<p>당사 홈페이지를 통해 담당자에게 상담 신청을 하며, 필요한 업체정보를 입력합니다.</p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p>상담결정</p></th>
					<td>
						<div class="td_p">
							<p>신청한 정보를 평가하여 상담진행 여부를 결정하게 되며, 해당 담당자가 파트너사에 연락하여<br> 일정 및 세부사항에 대해 안내해드립니다. </p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p>업체평가</p></th>
					<td>
						<div class="td_p">
							<p>상담 및 서류를 통해 업체를 평가하여 업체 POOL 등록 여부를 결정하게 됩니다.
								<br>결과는 업체평가 후 해당 담당자가 파트너사에 연락하게 됩니다. 
								
							</p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p>업체 POOL 등록</p></th>
					<td>
						<div class="td_p">
							<p>업체평가 결과에 따라 당사의 업체 POOL에 등록됩니다. 거래가능 업체대상으로 롯데마트 구매웹 등록작업을 진행합니다.</p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p>입찰 기회 부여</p></th>
					<td>
						<div class="td_p">
							<p>신규계약 수요 발생 시, 업체 POOL에 등록된 파트너사 대상으로 입찰기회가 부여되며 거래여부는 낙찰결과에 따릅니다.</p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p>사후 관리</p></th>
					<td>
						<div class="td_p">
							<p>당사의 윤리경영, 품질경영, 계약위반의 평가기준으로 객관화된 실적 데이터 근거 적합성 평가를 진행합니다.</p>
						</div>
					</td>
				</tr>
				</tbody>
				</table>
		
			</div>
			<!-- ------------------------------------------ -->
			<!-- 테넌트										-->
			<!-- ------------------------------------------ -->
			<div id="process3" class="tb_v_common_prod">
				<br>
				<div align=center>
					<img src="/images/epc/edi/consult/img_chart3.gif" alt="테넌트 상담절차">
				</div>
			
				<div class="s_title mt20">
					<h2>▶상담절차</h2>
				</div>
				<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<colgroup>
					<col width="130px"><col width="*">
				</colgroup>
				<tbody>
				<tr>
					<th><p>상담신청</p></th>
					<td>
						<div class="td_p">
							<p>당사 홈페이지를 통해 담당자에게 상담 신청을 하며 필요한 업체 정보를 입력합니다.</p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p>상담결정</p></th>
					<td>
						<div class="td_p">
							<p>신청한 정보를 평가하여 상담진행 여부를 결정하게 되며, 해당 담당자가 파트너사에 연락하여 일정 및 세부사항에 대해 안내해드립니다.</p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p>업체평가</p></th>
					<td>
						<div class="td_p">
							<p>상담 및 서류를 통해 업체를 평가하여 업체 POOL 등록 여부를 결정하게 됩니다.
							<br>결과는 업체평가 후 해당 담당자가 파트너사에 연락하게 됩니다. 
							</p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p>업체 POOL 등록 </p></th>
					<td>
						<div class="td_p">
							<p>업체평가 결과에 따라 당사의 업체 POOL에 등록됩니다. 
							<br>등록된 파트너사는 향후 입점가능 공간 및 기회발생시 입찰 기회를 부여 받게 됩니다.  
							</p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p>입찰 기회 부여 </p></th>
					<td>
						<div class="td_p">
							<p>입점가능 공간 및 기회 발생 시, 업체 POOL에 등록된 파트너사 대상으로 입찰기회가 부여되며 거래여부는 낙찰결과에 따릅니다.</p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p>사후 관리</p></th>
					<td>
						<div class="td_p">
							<p>당사의 윤리경영, 계약위반의 평가기준으로 객관화된 실적 데이터 근거 적합성 평가를 진행합니다.</p>
						</div>
					</td>
				</tr>
				
				</tbody>
				</table>
				
				
				
		
			</div>
<!-- 
			<div class="s_title">
				<h2>협력회사 선정 및 운용</h2>
			</div>

			<p><img src="/images/epc/edi/consult/img-chart.gif" alt=""></p>

			<div class="s_title mt20">
				<h2>상담절차</h2>
			</div>

			<div class="tb_v_common_prod">
				<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<colgroup>
					<col width="130px"><col width="*">
				</colgroup>
				<tbody>
				<tr>
					<th><p>상담신청</p></th>
					<td>
						<div class="td_p">
							<p>당사 홈페이지 Vendor Poll을 통해 롯데마트 Value Chain에 접속하여 담당 MD에게 상담 신청을 하며,<br>추가적인 업체정보를 입력합니다.</p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p>서류심사</p></th>
					<td>
						<div class="td_p">
							<p>업체가 신청한 정보를 평가하여 품평회 여부를 결정하게 되며, 품평회 일정은 MD가 협력업체에 연락하게 됩니다.</p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p>품평회</p></th>
					<td>
						<div class="td_p">
							<p>귀사의 회사 및 상품 소개 시간을 드리고 평가위원이 공정하고 객관적인 평가를 하게 됩니다.</p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p>입점결정</p></th>
					<td>
						<div class="td_p">
							<p>품평회 결과 및 MD 현장(공장)조사 과정을 통해 해당업체의 최종 입점 여부가 결정되며 결과는 품평회 및 MD 현장(공장)<br>조사후 통보를 하게 됩니다.</p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p>입점등록 작업</p></th>
					<td>
						<div class="td_p">
							<p>입점 결정에 따라 입점에 관련된 서류구비 및 계약서 작성을 진행합니다.</p>
							<ul>
								<li>- 사업자등록증, 사업자등록 증명원</li>
								<li>- 법인(개인)인감증명서, 등기부등본</li>
								<li>- 통장개설신청서, 통장사본</li>
								<li>- 기업구매카드등록신청확인서</li>
								<li>- 재무제표, 기타 준비서류 (담당 MD 문의)</li>
							</ul>
						</div>
					</td>
				</tr>
				<tr>
					<th><p>납품(시험유치)</p></th>
					<td>
						<div class="td_p">
							<p>입점등록이 완료된 이후에는 MD의 초도발주와 이에 따른 납품을 하시면 됩니다.<br>일부 품목은 입점시 3개월간 시험 유치를 통하여 상품성을 검증하는 평가과정을 거치게 됩니다.</p>
						</div>
					</td>
				</tr>
				<tr>
					<th><p>사후관리</p></th>
					<td>
						<div class="td_p">
							<p>입점된 상품에 대한 실적 관리를 통하여 평가 관리합니다.</p>
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