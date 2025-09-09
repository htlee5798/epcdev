<%@include file="../common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>

<script>
</script>
 
</head>

<body>
	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>  >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form name="searchForm" method="post">
		<div id="wrap_menu">
		
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit">Loan</li>
						<li class="btn">
							<a href='<c:url value="/edi/consult/PEDMCST0007.do"/>' class="btn"><span><spring:message code="button.consult.back"/></span></a>
						</li>
					</ul>
				</div>
			</div>
			<div class="wrap_con">
				<!-- list -->
				<div class="bbs_list">
					<ul class="tit">
							<li class="tit">FAQ</li>
					</ul>
					
					<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col style="width:50px;" />
						<col style="width:50px;" />
						<col />
					</colgroup>
					<tr class="r1">
						<td align="center">
							<div class="con_title">
								<h1><img src="/images/epc/edi/consult/comm_Q_i.gif" ></h1>
							</div>
						</td>
						<td align="center" colspan="2">
							&nbsp;&nbsp;&nbsp;롯데마트 협력업체 자금지원제도 개요는 어떻게 되나요?
						</td>
					</tr>
					<tr class="r1">
						<td align="center">
						</td>
						<td align="center">
							<div class="con_title">
								<h1><img src="/images/epc/edi/consult/comm_A_i.gif" ></h1>
							</div>
						</td>
						<td align="center">
							ㆍ 롯데마트는 협력업체와 상호 신뢰를 바탕으로 지속적인 전략적 동반관계로 성장하기 위하여, 중소기업은행과 업무제휴를 통하여  
   								 협력업체에게 원할한 자금지원을 제공하는 제도 <br>
							ㆍ 첫째, 경영안정자금 ? 롯데마트 패밀리론과 플러스 네트워크론 제도입니다. 롯데마트의 상품 납품/발주금액 또는 납품실적을 
							     근거로 협약 체결은행인 중소기업은행에서 신속하고 편리하게 융자를지원받을 수 있습니다. 패밀리론은 납품즉시 납품금액의 
 							   80%까지 선결제가 가능하고, 플러스네트워크론은 3개월까지 납품 예상액의 80%를 일괄지원까지 가능한제도입니다. 
								    네트워크론은 직전년도실적의 최고 1/6한도로 마이너스통장을 개설해주는 제도입니다.  <br>
								ㆍ 둘째, 경영안정자금 ? 롯데마트 다모아론 제도입니다. 당사 추천을 받은 우수 중소 협력업체는 최고 2%내 금리감면 혜택과 
								    업체 한도 外 추가 3억원 內 한도우대를 받을 수 있는 제도로써, 무보증/무담보가 특징입니다.  <br>
								ㆍ 셋째, 경영회생자금 ? 롯데마트 선급금 지원제도입니다.단기유동성 부족 협력업체에 대하여 10억원규모의 자금을 업체당5천만원
								    까지 당사 선급금으로 지원하는 제도입니다. 
						</td>
					</tr>
					
					<tr class="r1">
						<td align="center">
							<div class="con_title">
								<h1><img src="/images/epc/edi/consult/comm_Q_i.gif" ></h1>
							</div>
						</td>
						<td align="center" colspan="2">
							&nbsp;&nbsp;&nbsp;자금지원제도를 받을수있는 수혜 가능 업체는?
						</td>
					</tr>
					<tr class="r1">
						<td align="center">
						</td>
						<td align="center">
							<div class="con_title">
								<h1><img src="/images/epc/edi/consult/comm_A_i.gif" ></h1>
							</div>
						</td>
						<td align="center">
							 ※ 롯데마트와 거래를 하는 업체라면 모든 업체가 가능 합니다. <br>
							    단, 롯데마트 패밀리론을 제외한 상품은 업체의 신용도에 따라 대출의 제한을 받을 수 있습니다. <br>
							ㆍ경영안전자금(운영자금) - 롯데마트 추천 우수 중소 협력업체(추천주체 : 해당 업체 담당 MD) <br>
							ㆍ경쟁력강화자금(시설/운전자금) - 롯데마트 추천 우수 협력업체 및 우수 임대테넌트 협력업체 <br>
							ㆍ경영회생자금(긴급지원자금) - 단기 유동성 자금 부족업체(갑작스런 자금이 필요한 경우) 
						</td>
					</tr>
					
					<tr class="r1">
						<td align="center">
							<div class="con_title">
								<h1><img src="/images/epc/edi/consult/comm_Q_i.gif" ></h1>
							</div>
						</td>
						<td align="center" colspan="2">
							&nbsp;&nbsp;&nbsp;자금지원제도 신청 절차는?
						</td>
					</tr>
					<tr class="r1">
						<td align="center">
						</td>
						<td align="center">
							<div class="con_title">
								<h1><img src="/images/epc/edi/consult/comm_A_i.gif" ></h1>
							</div>
						</td>
						<td align="center">
							ㆍ경영협력업체 자금지원제도 <a href='<c:url value="/edi/consult/PEDMCST000701.do"/>'>바로가기(클릭)</a> 
						</td>
					</tr>
					
					<tr class="r1">
						<td align="center">
							<div class="con_title">
								<h1><img src="/images/epc/edi/consult/comm_Q_i.gif" ></h1>
							</div>
						</td>
						<td align="center" colspan="2">
							&nbsp;&nbsp;&nbsp;자금지원제도 신청 후 소요시간은?
						</td>
					</tr>
					<tr class="r1">
						<td align="center">
						</td>
						<td align="center">
							<div class="con_title">
								<h1><img src="/images/epc/edi/consult/comm_A_i.gif" ></h1>
							</div>
						</td>
						<td align="center">
							ㆍ자금지원제도 신청후(당사EDI,기업은행 상담후 약정체결) 진행 (소요기간 : 5~6일) 시간이 걸립니다. 
						</td>
					</tr>
					
					<tr class="r1">
						<td align="center">
							<div class="con_title">
								<h1><img src="/images/epc/edi/consult/comm_Q_i.gif" ></h1>
							</div>
						</td>
						<td align="center" colspan="2">
							&nbsp;&nbsp;&nbsp;자금지원제도 신청후 해야할일은?
						</td>
					</tr>
					<tr class="r1">
						<td align="center">
						</td>
						<td align="center">
							<div class="con_title">
								<h1><img src="/images/epc/edi/consult/comm_A_i.gif" ></h1>
							</div>
						</td>
						<td align="center">
							ㆍ협력업체 - 모든 협약체결 완료 시 발주/납품정보 활용을 통한 대출, 다모아론의 경우 한도약정 후 대출실행 <br>
							ㆍ은행 - 약정체결업체 인터넷뱅킹 등록 및 패밀리론의 경우, 법무채권양도 통지 (중소기업은행 → 롯데마트)<br> 
							ㆍ롯데마트 - 법무채권양도 통지 접수 확인 후 해당업체 은행에 발주/납품 정보 통보, 다모아론의 경우 해당없음.   
						</td>
					</tr>
					
					<tr class="r1">
						<td align="center">
							<div class="con_title">
								<h1><img src="/images/epc/edi/consult/comm_Q_i.gif" ></h1>
							</div>
						</td>
						<td align="center" colspan="2">
							&nbsp;&nbsp;&nbsp;자금지원제도 신청후 진행상태를 어떻게 알수있나요? 
						</td>
					</tr>
					<tr class="r1">
						<td align="center">
						</td>
						<td align="center">
							<div class="con_title">
								<h1><img src="/images/epc/edi/consult/comm_A_i.gif" ></h1>
							</div>
						</td>
						<td align="center">
							ㆍ WED EDI  업체등록  ▷ 1~2일후 해당MD 추천확정 ▷ 추천확정 결과 SMS 문자로 통보(경리팀 실시간 추천등록) <br>
							ㆍ 추천 확정 1~2일 후  ▷ 기업은행 협약체결후 결과 문자로 통보 <br>
							   ※롯데마트 경리팀으로 전화를 주시면 진행절차를 보다 빨리 알아보실수 있습니다.   <br>                     
                          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;☎(02)2145-8279 김동화 
						</td>
					</tr>
					
					<tr class="r1">
						<td align="center">
							<div class="con_title">
								<h1><img src="/images/epc/edi/consult/comm_Q_i.gif" ></h1>
							</div>
						</td>
						<td align="center" colspan="2">
							&nbsp;&nbsp;&nbsp;패밀리론 약정체결 협력업체의 납품확인서 산정기준및 통보시기는? 
						</td>
					</tr>
					<tr class="r1">
						<td align="center">
						</td>
						<td align="center">
							<div class="con_title">
								<h1><img src="/images/epc/edi/consult/comm_A_i.gif" ></h1>
							</div>
						</td>
						<td align="center">
							ㆍ패밀리론 은행통보금액 산정기준은 납품(판매)기준으로 익일에 전일 납품(판매)를 토대로 납품정보가 등록되며,
								플러스 네트워크론 발주정보 등록 산정기준은 직전 6개월 납품실적의 평균금액 또는 실제 매입 예정내역으로 
								산정됩니다.<br>
								○ 패밀리론 : 납품/판매 → 익일 납품금액 은행전송 → 대출실행 → 납품대결제 <br>
								→ 대출금 상환 (롯데마트 신용도를 반영한 최저금리 : 3CD+1.5%) <br>
								○ 플러스 네트워크론 : 납품전 3개월 범위內 납품예정량 발주요청신청(Web-EDI) → MD승인 → 은행발주통보 
								→ 대출실행 → 납품 → 납품대결제 → 대출금상환 <br>
								※ 패밀리론 납품/판매정보 은행전송금액 <br>
								(직매입) 매입원가금액 - 약정요율 공제액 - 추가공제예상액(매입원가의 5%) <br>
								(특정매입) 판매원가금액 - 판매취소 및 공제예상액(판매원가의 5%) <br>
								※ 패밀리론 약정체결 협력업체 은행통보내역은 Web-EDI > 결산정보 > 거래실적정보 에서 확인 가능합니다
						</td>
					</tr>
					
					<tr class="r1">
						<td align="center">
							<div class="con_title">
								<h1><img src="/images/epc/edi/consult/comm_Q_i.gif" ></h1>
							</div>
						</td>
						<td align="center" colspan="2">
							&nbsp;&nbsp;&nbsp;자금지원제도 이용시 업체가 받는 불이익이 있나요?
						</td>
					</tr>
					<tr class="r1">
						<td align="center">
						</td>
						<td align="center">
							<div class="con_title">
								<h1><img src="/images/epc/edi/consult/comm_A_i.gif" ></h1>
							</div>
						</td>
						<td align="center">
							ㆍ자금지원제도는 롯데마트에서 협력업체와 상호 신뢰를 바탕으로 지속적인 전략적 동반관계로 성장하기 위하여 추진하는 협력업체지원 제도이므로 전혀 불이익이 돌아가지 않습니다.  
						</td>
					</tr>
					
					</table>
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
					<li>홈</li>
					<li>협업</li>
					<li>협업정보</li>
					<li class="last">Loan</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>

</body>
</html>
