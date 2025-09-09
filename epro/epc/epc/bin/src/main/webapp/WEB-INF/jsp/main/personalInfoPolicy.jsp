<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
ul, ol{
	list-style-type  : none;
	padding : initial;
}
a{
	text-decoration : none;
	color: #333;
}
table{
	border-collapse : collapse;
	margin: 10px 0 0 0;
	width: 100%;
	color : #5c5c5c;
}
th,td{
	border : 1px solid #bbb;
	padding : 10px;
	line-height: 25px;
}
th{
	background :  #eee;
	text-align: center;
}
ul>li{
	padding: 10px 0 0 0;
}
select{
	width: 200px;
	height: 40px;
	text-align: center;
	margin: -25px 0 0 30px;
}
h2{
	margin: 36px 0 50px 0;
	font-size: 36px;
}
p,li{
	font-size : 14px;
}
.title{
	text-align: center;
}
.wrap{
	margin : 30px;
}
.contents{
	color : #5c5c5c;
	line-height: 25px;
	margin: 0 0 0 0;
	letter-spacing: -1px;
}
#index{
	column-count : 3;
	background : #f8f8f8;
	padding : 23px 20px 30px;
	line-height: 1.7;
}
#sList>li{
	color : #5c5c5c;
	line-height: 25px;
}
#sList1{
	padding : 10px 0 0 0;
}
#sList2{
	padding: 10px 0 15px 0;
}
#list09_p{
	margin: 20px 0 15px 0;
}
.ver_01{
	display : block;
}

</style>

<script type="text/javascript">

function onChange(){
	var selectedVersion = document.getElementById("selectVersion").options[document.getElementById("selectVersion").selectedIndex].value;
	
	if(selectedVersion == "ver_1.00"){
		document.getElementById("ver_1.00").style.display = 'inline';
		window.scrollTo(0,0);
		return;
	}
}
</script>
</head>
<body>
<div class="title">
<h2>개인정보처리방침</h2>
</div>

<div class = "ver_01" id = "ver_1.00" name = "ver_1.00">
<div class = "wrap" >
	<div id = "indexWrap">
	<ol id="index">
		<li> 1. 총칙  </li>
		<li> 2. 개인정보의 수집 항목 및 이용 목적 </li>
		<li> 3. 개인정보의 수집 방법 </li>
		<li> 4. 개인정보의 보유 및 이용기간 </li>
		<li> 5. 개인정보의 제3자 제공 </li>
		<li> 6. 개인정보의 처리 위탁 </li>
		<li> 7. 개인정보의 파기절차 및 방법 </li>
		<li> 8. 이용자 및 법정대리인의 권리ㆍ의무 및 행사방법</li>
		<li> 9. 의견수렴 및 불만처리 </li>
		<li> 10. 개인정보 자동 수집 장치의 설치ㆍ운영 및 거부 </li>
		<li> 11. 개인정보의 기술적ㆍ관리적 보호조치 </li>
		<li> 12. 개인정보 보호책임자 </li>
		<li> 13. 고지의 의무 </li>
	</ol>
	</div>
	
	<div class = "contenctsWrap" >
	<ul>
	<li id="list01">
		<h4>제 1조 (총칙)</h4>
		<p class = "contents">
		롯데쇼핑(주) e커머스(이하 ‘회사’)는 이용자들의 개인정보보호를 중요시하며 정보주체의 자유와 권리 보호를 위해 「개인정보 보호법」 및 관계 법령이 정한 바를 준수하여, 적법하게 개인정보를 처리하고 안전하게 관리하고 있습니다. 
		이에 「개인정보 보호법」 제30조에 따라 정보주체에게 개인정보 처리에 관한 절차 및 기준을 안내하고, 이와 관련한 고충을 신속하고 원활하게 처리할 수 있도록 하기 위하여 다음과 같이 개인정보 처리방침을 수립·공개합니다.
		</p>
	</li>
	<li id="list02">
		<h4>제 2조 (개인정보의 수집 항목 및 이용목적, 보유 및 이용기간)</h4>
		<p class = "contents">
		회사는 다음의 목적을 위하여 개인정보를 처리합니다. </br>
		처리하고 있는 개인정보는 다음의 목적 이외의 용도로는 이용되지 않으며, 이용 목적이 변경되는 경우에는 「개인정보 보호법」 제18조에 따라 별도의 동의를 받는 등 필요한 조치를 이행할 예정입니다.</br>
		1. 시스템 계정생성 시 수집하는 정보
		</p>
		<div>
		<table>
			<colgroup>
				<col width="5%">
				<col width="30%">
				<col width="35%">
				<col width="25%">
			</colgroup>
			<thead>
			<th>구분</th>
			<th>항목</th>
			<th>목적</th>
			<th>보유기간</th>
			</thead>
			<tbody>
			<tr>
				<td>필수</td>
				<td>회사명, 성명, 전화번호, E-mail, 사용자 IP, 계정명(ID)</td>
				<td>- 시스템 계정생성 및 시스템 사용에 따른 이용자 본인 식별 </br>
					- 침해사고 발생 시 사후 추적</td>
				<td rowspan="2">- 퇴사 및 계약종료 시 즉시 파기</td>
			</tr>
			<tr>
				<td>선택</td>
				<td>직급,사번</td>
				<td>- 침해사고 발생 시 사후 추적</td>
			</tr>
			</tbody>
		</table>
		</div>
	</li>
	<li id="list03">
		<h4>제 3조 (개인정보의 수집 방법)</h4>
		<p class = "contents">
		회사는 다음과 같은 방법으로 개인정보를 수집합니다.</br>
			1. 계정 신청서를 통해 수집</br>
			2. 시스템을 통한 수집</br>
		</p>
	</li>
	<li id="list04">
		<h4>제 4조 (개인정보의 보유 및 이용기간)</h4>
		<p class = "contents">
		원칙적으로 개인정보의 수집 및 이용목적 또는 제공받은 목적이 달성되면 지체없이 파기합니다. 
		</p>
	</li>
	<li id="list05">
		<h4>제 5조 (개인정보의 제3자 제공)</h4>
		<p class = "contents">
		회사는 별도의 공지 없이 개인정보를 제 3자에게 제공하지 않습니다. 단, 법령의 규정에 의거하거나, 수사 목적으로 법령에 정해진 절차와 방법에 따라 수사기관의 요구가 있는 경우는 예외로 합니다.
		</p>
	</li>
	<li id="list06">
		<h4>제 6조 (개인정보의 처리 위탁)</h4>
		<p class = "contents">
		회사는 개인정보 처리와 관련한 별도의 위탁사항이 없으며, 개인정보 처리업무를 위탁을 하게되는 경우 본 개인정보 처리방침을 통하여 지체없이 공개하도록 하겠습니다.
		</p>
	</li>
	<li id="list07">
		<h4>제 7조 (개인정보의 파기 절차 및 방법)</h4>
		<p class = "contents">
		1. 회사는 개인정보 보유기간의 경과, 처리목적 달성 등 개인정보가 불필요하게 되었을 때에는 지체없이 해당 개인정보를 파기합니다.</br>
		2. 정보주체로부터 동의받은 개인정보 보유기간이 경과하거나 처리목적이 달성되었음에도 불구하고 다른 법령에 따라 개인정보를 계속 보존하여야 하는 경우에는, 해당 개인정보를 별도의 데이터베이스(DB)로 옮기거나 보관장소를 달리하여 보존합니다.</br>
		3. 개인정보 파기의 절차 및 방법은 다음과 같습니다.</br>
		① 파기절차</br>
		회사는 파기 사유가 발생한 개인정보를 선정하고, 회사의 개인정보 보호책임자의 승인을 받아 개인정보를 파기합니다.</br>
		② 파기방법</br>
		회사는 전자적 파일 형태로 기록·저장된 개인정보는 기록을 재생할 수 없도록 파기하며, 종이 문서에 기록·저장된 개인정보는 분쇄기로 분쇄하거나 소각하여 파기합니다.</br>
		</p>
	</li>
	<li id="list08">
		<h4>제 8조 (이용자 및 법정대리인의 권리ㆍ의무 및 행사방법)</h4>
		<p class = "contents">
		1. 정보주체는 회사에 대해 언제든지 개인정보 열람·정정·삭제·처리정지 요구 등의 권리를 행사할 수 있습니다.</br>
		2. 권리 행사는 회사에 대해 「개인정보 보호법」 시행령에 따라 서면, 전자우편 등을 통하여 하실 수 있으며, 회사는 이에 대해 지체없이 조치하겠습니다.</br>
		3. 권리 행사는 정보주체의 법정대리인이나 위임을 받은 자 등 대리인을 통하여 하실 수도 있습니다.</br>
		4. 개인정보 열람 및 처리정지 요구는 개인정보 보호법 의하여 정보주체의 권리가 제한 될 수 있습니다.</br>
		5. 개인정보의 정정 및 삭제 요구는 다른 법령에서 그 개인정보가 수집 대상으로 명시되어 있는 경우에는 그 삭제를 요구할 수 없습니다.</br>
		6. 회사는 정보주체 권리에 따른 열람의 요구, 정정·삭제의 요구, 처리정지의 요구 시 열람 등 요구를 한 자가 본인이거나 정당한 대리인인지를 확인합니다.</br>
		</p>
	</li>
	<li id="list09">
		<h4>제 9조 (의견수렴 및 불만처리)</h4>
		<p class = "contents">
		1. 정보주체는 문의사항이 있을 경우 아래의 연락처로 문의 하시면 신속하게 답변을 드리겠습니다. 또한 개인정보 보호법에 따른 개인정보의 열람 청구를 아래의 부서에 할 수 있습니다.</br>
		2. 회사는 정보주체의 개인정보 열람청구가 신속하게 처리되도록 노력하겠습니다.</br>
		</p>
		<table>
		<colgroup>
			<col width="25%">
			<col width="25%">
			<col width="50%">
		</colgroup>
		<thead>
			<th colspan="2">구분</th>
			<th>서비스 시간</th>
		</thead>
		<tbody>
		<tr>
			<td>이메일</td>
			<td>LD_CPO@lotte.net</td>
			<td>이메일 접수는 24시간 가능</td>
		</tr>
		</tbody>
		</table>
		<p class = "contents" id="list09_p">
		3. 개인정보에 관한 불만처리 및 피해구제 등 상담이 필요한 경우에는 아래와 같이 문의하실 수 있습니다.
		</p>
		<table>
			<colgroup>
				<col width="20%">
				<col width="25%">
				<col width="50%">
			</colgroup>
			<thead>
			<th colspan="2">문의처</th>
			<th> 홈페이지 </th>
			</thead>
			<tbody>
			<tr>
				<td>개인정보침해신고센터</td>
				<td>(국번없이)118</td>
				<td><a href="http://privacy.kisa.or.kr">http://privacy.kisa.or.kr</a></td>
			</tr>
			<tr>
				<td>대검찰청 사이버수사과</td>
				<td>(국번없이) 1301</td>
				<td><a href = " http://www.spo.go.kr"> http://www.spo.go.kr</a></td>
			</tr>
			<tr>
				<td>사이버범죄 신고시스템(ECRM)</td>
				<td>(국번없이) 182</td>
				<td><a href="http://ecrm.police.go.kr/minwon/main">http://ecrm.police.go.kr/minwon/main</a></td>
			</tr>
			</tbody>
		</table>
	</li>
	<li id="list10">
		<h4>제 10조 (개인정보 자동 수집 장치의 설치ㆍ운영 및 거부)</h4>
		<p class = "contents">
		회사는 정보주체의 이용정보를 저장하고 수시로 불러오는 ‘쿠키(cookie)’를 사용하지 않습니다.
		</p>
	</li>
	<li id="list11">
		<h4>제 11조 (개인정보의 기술적ㆍ관리적 보호조치)</h4>
		<p class = "contents">
		회사는 개인정보보호 관련 법에 따라 개인정보의 안전한 관리를 위하여 필요한 기술적ㆍ관리적 및 물리적 보호조치를 위해 다음과 같은 수행하고 있습니다.</br>
		1. 관리적 조치 : 내부관리계획 수립·시행, 전담조직 운영, 정기적 직원 교육</br>
		2. 기술적 조치 : 개인정보처리시스템 등의 접근권한 관리, 접근통제시스템 설치, 개인정보의 암호화, 보안프로그램 설치 및 갱신</br>
		3. 물리적 조치 : 전산실, 자료보관실 등의 접근통제</br>
		</p>
	</li>
	<li id="list12">
		<h4>제 12조 (개인정보 보호책임자에 관한 사항)</h4>
		<p class = "contents">
		1. 회사는 개인정보 처리에 관한 업무를 총괄해서 책임지고, 개인정보 처리와 관련한 정보주체의 불만처리 및 피해구제 등을 위하여 아래와 같이 개인정보 보호책임자를 지정하고 있습니다.</br>
		</p>
		
		<ol id="sList">
		<li id="sList1">
		① 개인정보 보호책임자 </br> 
		소  속 : 플랫폼부문 </br>
		성  명 : 이재훈 </br>
		직  책 : 부문장 </br>
		연락처 : LD_CPO@lotte.net </br>
		</li>
		<li id="sList2">
		② 개인정보 보호 담당부서 </br>
		부서명 : 개인정보보호팀 </br>
		연락처 : LD_CPO@lotte.net </br>
		</li>
		</ol>
		
		<p class = "contents">
		2. 정보주체는 회사의 서비스(또는 사업)을 이용하시면서 발생한 모든 개인정보보호 관련 문의, 불만처리, 피해구제 등에 관한 사항을 개인정보 보호책임자 및 담당부서로 문의할 수 있습니다.</br> 
		회사는 정보주체의 문의에 대해 지체없이 답변 및 처리해드릴 것입니다.
		</p>
	</li>
	<li id="list13">
		<h4>제 13조 (개인정보 처리방침의 변경에 관한 사항)</h4>
		<p class = "contents">
		1. 이 개인정보 처리방침은 2022. 12. 20.부터 적용됩니다. </br>
		2. 이전의 개인정보 처리방침은 아래에서 확인하실 수 있습니다. </br>
		</p>
	</li>
	</ul>	
	</div>
	</div>
</div>
<div>
		<select id = "selectVersion" name = "selectVersion" onchange="onChange()">
			<option value ="ver_1.00" selected>2022.12.20 ~ 현재</option>>
		</select>
</div>
</body>
</html>