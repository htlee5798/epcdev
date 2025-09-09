<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- <meta http-equiv="Content-Type" content="text/html; charset=euc-kr"> -->
<meta http-equiv="Cache-Control" content="No-Cache">
<meta http-equiv="Pragma" content="No-Cache">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 9"> 
<script type="text/javascript" src="/js/epc/Ui_common.js" ></script>
<script type="text/javascript" src="/js/jquery/jquery-1.6.1.js"></script>

<%
	  response.setHeader("Content-Disposition", "attachment;filename=PSCMDLV000501.xls;") ;
	  response.setContentType("application/vnd.ms-excel;charset=UTF-8") ;
	  response.setHeader("Content-Transfer-Encoding", "binary;");
	  response.setHeader("Pragma", "no-cache;");
	  response.setHeader("Expires", "-1;");
%>
</head>


<body>

<div id="content_wrap">
</td>
	<div class="content_scroll">
	
		<div id="wrap_menu">
		
			<!--	2 검색내역 	-->
			<div class="wrap_con">
				<!-- list -->
				<div class="bbs_list">
			 	<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td align="center" colspan="2">[ 매입거래선 중 상품 직배송 업체용]</td>
						<td colspan="2"></td>
						<td align="center" colspan="3" style="background-color: yellow;">매월 첫째주 금요일까지 롯데마트에 제출</td>
					</tr>
				</table> 
				<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" > 
				 <tr>
				 	<td align="center" colspan="7" style="height: 50pt; font-size: medium; font-weight: bold;" >배송지 정보 파기 확인서</td>
				 </tr>
				</table> 
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="1" style="border-top: 50pt; border-left: 50pt; border-right: 50pt;">
					<tr>
						<td class="fst" align="center" rowspan="4" bgcolor="#EAEAEA" style='width:75pt; border: 0px;'></td>
						<td align="center" style="height: 30px; width:120pt; font-weight: bold;">구분</td>
				 		<td align="center" colspan="5" style="width: 280pt">[ &nbsp&nbsp ] 온라인 전용  [ &nbsp&nbsp ] 온오프라인 공통  [ &nbsp&nbsp ] 오프라인 전용  [ &nbsp&nbsp ]기타</td>
					</tr>
					<tr>
						<td align="center" bgcolor="#79AAFF" style="height: 30px; font-weight: bold;">롯데마트 담당자</td>
						<td align="center" style="font-weight: bold; width:20pt;">팀명</td>
						<td align="center" colspan="2" bgcolor="#79AAFF" style="width:50pt;"></td>
						<td align="center" style="font-weight: bold;">담당자명</td>
						<td align="center" bgcolor="#79AAFF" style="width:100pt;"></td>
					</tr>
					<tr>
						<td align="center" style="height: 30px; font-weight: bold;">사업자명 <br/> (사업자등록증상의 상호)</td>
						<td align="center" colspan="3" bgcolor="#79AAFF"></td>
						<td align="center" style="font-weight: bold;">계약 체결일</td>
						<td align="center" bgcolor="#79AAFF">${year }. &nbsp&nbsp&nbsp	. &nbsp&nbsp&nbsp .</td>
					</tr>
						<td align="center" style="height: 30px; font-weight: bold;">사업자등록번호</td>
						<td align="center" colspan="3" bgcolor="#79AAFF">	&nbsp - &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp - &nbsp	</td>
						<td align="center" style="font-weight: bold;">업체코드</td>
						<td align="center" bgcolor="#79AAFF" style='mso-number-format:"\@";'>${vendorId }</td>
					<tr>
						<td align="center" bgcolor="#EAEAEA" style="border: 0px; font-weight: bold;">파트너사</td>
						<td align="center" style="height: 30px; width: 5px; font-weight: bold;" >개인정보관리책임자명</td>
						<td align="center" colspan="2"></td>
						<td align="center" colspan="2" style="font-weight: bold;">대표자명</td>
						<td align="center" bgcolor="#79AAFF"></td>
					</tr>
					<tr>
						<td align="center" bgcolor="#EAEAEA" style="border: 0px; font-weight: bold;">기본 입력 사항</td>
						<td align="center" rowspan="4" style="font-weight: bold;">개인정보취급자명</td>
						<td align="center" style="height: 25pt;"></td>
						<td align="center" style="font-weight: bold; width: 35pt;">이름</td>
						<td align="center"></td>
						<td align="center" style="font-weight: bold;">이름</td>
						<td align="center" style="font-weight: bold;">비고(변동 내역 등)</td>
					</tr>
					<tr>
						<td class="fst" bgcolor="#EAEAEA" align="center" rowspan="5" style='border: 0px;'></td>
						<td align="center" style="height: 25pt;">1</td>
						<td align="center" style="width: 35pt;"></td>
						<td align="center">4</td>
						<td align="center"></td>
						<td align="center"></td>
					</tr>
					<tr>
						<td align="center" style="height: 25pt;">2</td>
						<td align="center" style="width: 35pt;"></td>
						<td align="center">5</td>
						<td align="center"></td>
						<td align="center"></td>
					</tr>
					<tr>
						<td align="center" style="height: 25pt;">3</td>
						<td align="center" style="width: 35pt;"></td>
						<td align="center">6</td>
						<td align="center"></td>
						<td align="center"></td>
					</tr>
					<tr>
						<td align="center" style=" font-weight: bold;">배송지 정보 취급 PC</td>
						<td align="right">&nbsp&nbsp&nbsp 총</td>
						<td align="center" style="width: 35pt;"></td>
						<td align="left">대&nbsp&nbsp&nbsp&nbsp</td>
						<td align="center" style="font-size:xx-small;">취급 PC <br/>전월 대비 증감</td>
						<td align="left">[&nbsp&nbsp&nbsp&nbsp] 대 증가 <br/>[&nbsp&nbsp&nbsp&nbsp] 대 감소</td>
					</tr>
					<tr>
						<td align="center" style="height: 30px; font-weight: bold;">이동식 저장매체</td>
						<td align="center" bgcolor="#D26C9F" >[&nbsp&nbsp&nbsp&nbsp]</td>
						<td align="center" colspan="2" style="width: 45pt;">미사용(권장)</td>
						<td align="center">[&nbsp&nbsp&nbsp&nbsp]</td>
						<td align="left">사용(&nbsp&nbsp&nbsp&nbsp 개)</td>
					</tr>
					</table>
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="1"  style="border-left: 50pt; border-right: 50pt;">
						<tr><td colspan="7" style="height: 5pt;"></td></tr>
					</table>
					 <table class="bbs_list" cellpadding="0" cellspacing="0" border="1" style="border-left: 50pt; border-right: 50pt;">
					<tr>
						<td rowspan="8" align="center" bgcolor="#EAEAEA" style='width:30.4pt; font-weight: bold;'>파기 문건</td>
						<td align="center" style="background-color: yellow; font-weight: bold;">파기 대상(범위)</td>
						<td align="left" colspan="5"><span style="font-weight: bold; text-decoration: underline;">파기 확인서 작성일 현재(매월 마지막주 금요일)</span> 주문 접수된 건 中 <br/> 파트너사에서 고객에게 상품을 배송완료 後 30일 이상 경과된 주문<br/> 건에 대한 개인정보파일(엑셀 등) 전체</td>
					</tr>
					<tr>
						<td align="center" rowspan="2" style="font-weight: bold;">파기대상 주문 건수</td>
						<td align="right" colspan="2" bgcolor="#79AAFF" style="height:25pt;  padding-right: 200%; margin-right: 100%;">${totalOrdCnt}</td>
						<td align="left" colspan="2">건 파기 완료</td>
					</tr>
					<tr>
						<td colspan="5" align="left" style="height:40pt; font-weight: bold; background-color: yellow;">별도의 정함이 없는 경우 <br/>계약 체결일 이후 직전 월까지 주문 접수건수 누계</td>
					</tr>
					<tr>
						<td align="center" rowspan="2" style="font-weight: bold;">개인정보 유형</td>
						<td align="center" style="height:25pt;" bgcolor="#D26C9F">[&nbsp&nbsp&nbsp]</td>
						<td align="left" colspan="4" style="font-weight: bold;">배송지 정보 - 「보내는분 및 받는 분」 이름, 주소, 연락처</td>
					</tr>
					<tr>
						<td align="center" style="height:25pt;">[&nbsp&nbsp&nbsp]</td>
						<td align="left" colspan="4">기타 - 직접기재( &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp )</td>
					</tr>
					<tr>
						<td align="center" rowspan="3" style="font-weight: bold;">파기방법</td>
						<td align="center" style="height:25pt;" bgcolor="#D26C9F">[&nbsp&nbsp&nbsp]</td>
						<td align="left" colspan="4" style="font-weight: bold;">전자적 문서(엑셀 등) -  데이터 완전 삭제</td>
					</tr>
					<tr>
						<td align="center" style="height:25pt;" bgcolor="#D26C9F">[&nbsp&nbsp&nbsp]</td>
						<td align="left" colspan="4" style="font-weight: bold;">오출력 송장 등 종이 문서 - 파쇄기를 통한 파쇄</td>
					</tr>
					<tr>
						<td align="center" style="height:25pt;">[&nbsp&nbsp&nbsp]</td>
						<td align="left" colspan="4" >기타 - 직접기재( &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp)</td>
					</tr>
					</table>
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="1"  style="border-left: 50pt; border-right: 50pt;">
						<tr><td colspan="7" style="height: 5pt;"></td></tr>
					</table>
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="1" style=" border-bottom: 50pt; border-left: 50pt; border-right: 50pt;">
					<tr>
						<td align="center" rowspan="3" bgcolor="#EAEAEA" style="height: 50pt; font-weight: bold;" >파기 확인</td>
						<td align="center" style="height: 25pt; font-weight: bold;">파기 확인일</td>
						<td align="center" colspan="5" bgcolor="#79AAFF">${today }</td>
					</tr>
					<tr>
						<td align="center" style="height: 25pt; font-weight: bold;">파기 확인자</td>
						<td align="center">[ &nbsp&nbsp&nbsp&nbsp ]</td>
						<td align="center" style="font-weight: bold;">개인정보관리책임자</td>
						<td align="center" bgcolor="#D26C9F">[ &nbsp&nbsp&nbsp&nbsp ]</td>
						<td align="center" colspan="2" style="font-weight: bold;">대표자</td>
					</tr>
					<tr>
						<td align="center" style="height: 50pt; font-weight: bold;">특이사항 등<br/> 의견입력란</td>
						<td align="center" colspan="5"></td>
					</tr>
					</table>
					<br/>  
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td colspan="9">
								당사는 롯데마트몰에서 접수된 각 주문건별 배송지 정보를 배송을 위한 목적으로만 사용한 後 배송 완료 즉시 복원 불가능한<br/>
								방법으로 관련 법령 등에서 정하는 바에 따라 삭제하고 있음을 확인하며, 개인정보 파기에 대한 정기적인 확인 절차로서 상기<br/>
								파기 대상 기간에 포함되는 배송지정보의 경우 컴퓨터 보관분 및 이동식 저장매체 보관분 등을 포함하여 모든 자료를 저장 경<br/>
								로 또는 방법 등을 불문하고 복원 불가능한 방법으로 파기 완료되었음 알려드립니다.
							</td>
						</tr>
					</table>
					<br/>  
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td align="center" colspan="7" style="font-size: 15px;">201 &nbsp&nbsp&nbsp    년  &nbsp&nbsp&nbsp    월  &nbsp&nbsp&nbsp    일</td>
						</tr>
						<tr>
							<td colspan="3"></td>
							<td align="center" colspan="2" style="font-size: 15px; height: 25pt;">업체명</td>
							<td colspan="2"></td>
						</tr>
						<tr>
							<td colspan="3"></td>
							<td align="center" colspan="2" style="font-size: 15px; height: 25pt;">대표자</td>
							<td align="right" colspan="2" style="font-size: 15px;">(인)</td>
						</tr>
						<tr>
							<td align="right" colspan="7" style="font-weight: bold; font-size: 20px;">제출처 - 롯데쇼핑 주식회사 롯데마트</td>
						</tr>
					</table>
				</div>
		
			</div>
			<!-- 2검색내역 // -->
		
		</div>

	</div>

</div>

<!--	@ BODY WRAP  END  	// -->
</body>
</html>