<%@include file="../common.jsp"%>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<style type="text/css">
.tg {
	border-collapse: collapse;
	border-spacing: 0;
}

.tg td {
	font-family: Arial, sans-serif;
	font-size: 14px;
	padding: 10px 5px;
	border-style: solid;
	border-width: 1px;
	overflow: hidden;
	word-break: normal;
	border-color: black;
}

.tg th {
	font-family: Arial, sans-serif;
	font-size: 14px;
	font-weight: normal;
	padding: 10px 5px;
	border-style: solid;
	border-width: 1px;
	overflow: hidden;
	word-break: normal;
	border-color: black;
	background-color: beige
}

.tg .tg-baqh {
	text-align: center;
	vertical-align: top
}
</style>

<script language="JavaScript">
	$(function() {
		var dataInfo = {};

		//접속 월 시작일, 말일 구하기.		
		var now = new Date();
		// 20190501/20190531
		dataInfo["START_DATE"] = getFormatDate(new Date(now.getFullYear(), now.getMonth(), 1));
		dataInfo["END_DATE"] = getFormatDate(new Date(now.getFullYear(), now.getMonth() + 1, 0));

		//dataInfo["START_DATE"] = "20190501";
		//dataInfo["END_DATE"] = "20190531";

		
		// 업체 코드
		var TAB1 = new Array();
		var list = new Array();
		<c:forEach items="${VEN_CDS}" var="item">
			var info = {};
			info["VEN_CD"] = "${item}";
			TAB1.push(info);
		</c:forEach>

		// 조치 미조치
		var TAB2 = new Array(); //조치 미조치

		// MEASURE 
		// A: 초기입력
		// B: MD입력완료, 업체미등록
		// X: 업체등록 완료. MD 미등록
		// C: 업체등록 완료, MD 등록 완료
		// 00월 전체 등록건수: A + B + X
		// 조치사항 미등록건수: A + B

		var tabInfo2 = {};
		tabInfo2["MEASURE"] = "A";
		TAB2.push(tabInfo2);

		var tabInfo2 = {};
		tabInfo2["MEASURE"] = "B";
		TAB2.push(tabInfo2);

		dataInfo["TAB1"] = TAB1;
		dataInfo["TAB2"] = TAB2;

		// 공통 RequestCommon
		dataInfo["REQCOMMON"] = getReqCommon();
		//console.log(dataInfo);

		//rfc펑션 콜후 콜백 펑션 호출 (콜백 펑션 구현rfcCallBack)
		rfcCall("INV0570", dataInfo);
	});

	/* rfcCall CallBack 함수*/
	function rfcCallBack(data) {		
		var rows = data.result.RESPCOMMON.ZPOROWS; // 전체 건수
		var result = data.result;
		// 조회 건수가 있음.
		if (rows != 0) {			
			_setTbodyPopupValue(result.TAB); // 팝업에 보여줄 테이블을 그리는 함수 호출
		} else { // 조회 건수가 없음.			
			setTbodyNoResult();
		}
	}

	// 팝업 화면 스크립트 단에서 그려주기.
	function _setTbodyPopupValue(result) {
		var now = new Date();
		// 00월 오산저온센터 전체등록건수, 00월 김해저온센터 전체등록건수, 오산 업체조치사항 미등록 건수, 김해 업체조치사항 미등록 건수
		var osan_tot, kim_tot, osan_no_com_reg, kim_no_com_reg

		var osan_init = 0, osan_md_input = 0, osan_com_input = 0 // 각각 flag A, B, X
		var kim_init = 0, kim_md_input = 0, kim_com_input = 0 // 각각 flag A, B, X

		// 20190606 전산정보팀 이상구 수정
		// 객체로 하나만 리턴 받을 경우 배열로 변경 해줌..	
		if(!(result instanceof Array)){
			var tmp = new Array;
			tmp.push(result);
			result = tmp;
		}
		
		
		// 테이블에 그릴 값들을 구한다. 
		result.forEach(function(currentValue, index, arr) {
			console.log(currentValue);
			// 먼저 오산인지, 김해인지 판단한다.
			if (currentValue.STR_CD.trim() === 'W03') {// 오산저온센터
				if (currentValue.SEND_FG.trim() === 'A')
					osan_init++;
				else if (currentValue.SEND_FG.trim() === 'B')
					osan_md_input++;
				else if (currentValue.SEND_FG.trim() === 'X')
					osan_com_input++;
			} else if (currentValue.STR_CD.trim() === 'W04') { // 김해저온센터
				if (currentValue.SEND_FG.trim() === 'A')
					kim_init++;
				else if (currentValue.SEND_FG.trim() === 'B')
					kim_md_input++;
				else if (currentValue.SEND_FG.trim() === 'X')
					kim_com_input++;
			}
		});

		// 데이터 계산
		osan_tot = osan_init + osan_md_input + osan_com_input; //오산저온센터 전체 건수
		kim_tot = kim_init + kim_md_input + kim_com_input; // 김혜저온센터 전체 건수
		osan_no_com_reg = osan_init + osan_md_input; // 오산 업체 미등록 건수
		kim_no_com_reg = kim_init + kim_md_input; // 김혜 업체 미등록 건수
		
		
		var table_str="";		
		table_str = "<table class='tg'>";
		table_str += "<tr>";
		table_str += "	<th class='tg-baqh'>구분</th>";
		table_str += "	<th class='tg-baqh'>" + (1 + now.getMonth()) +"월 전체 등록건수</th>";
		table_str += "	<th class='tg-baqh'>조치사항 미등록건수</th>";
		table_str += "</tr>";		
		
		table_str += "<tr>";
		table_str += 	"<td class='tg-baqh'>오산저온센터</td>";
		table_str += 	"<td class='tg-baqh'>" + (osan_tot == "" ? "0" : osan_tot)  + " 건</td>";
		table_str += 	"<td class='tg-baqh'>" + (osan_no_com_reg == "" ? "0" : osan_no_com_reg) + " 건</td>";		
		table_str += "</tr>";		
		
		table_str += "<tr>";
		table_str += 	"<td class='tg-baqh'>김해저온센터</td>";
		table_str += 	"<td class='tg-baqh'>" + (kim_tot == "" ? "0" : kim_tot )+ " 건</td>";
		table_str += 	"<td class='tg-baqh'>" + (kim_no_com_reg == "" ? "0" : kim_no_com_reg) + " 건</td>";		
		table_str += "</tr>";	
			
		// 테이블을 그려준다.
		$(".popup_contents").prepend("<span> ※롯데마트 물류센터 입하거부상품이 등록되었습니다. <br> - 사유 확인 및 조치사항등록바랍니다. </span>");
		$(".table_data").append(table_str);
		
	}

	// 팝업 화면에 빈값 보여주기.
	function setTbodyNoResult(result) {
		$(".popup_contents").append("<span> ※ 롯데마트 물류센터 입하거부상품 내역이 없습니다. </span>");
	}

	// Date 타입을 YYYYMMDD 형식 리턴
	function getFormatDate(date) {
		var year = date.getFullYear(); //yyyy
		var month = (1 + date.getMonth()); //M
		month = month >= 10 ? month : '0' + month; // month 두자리로 저장
		var day = date.getDate(); //d
		day = day >= 10 ? day : '0' + day; //day 두자리로 저장
		return year + '' + month + '' + day;
	}
</script>

</head>

<body>

	<form id="form1" name="form1" method="post">

		<div id="popup">
			<!------------------------------------------------------------------ -->
			<!--    title -->
			<!------------------------------------------------------------------ -->
			<div id="p_title1">
				<h1>
					<spring:message code='epc.buy.menu.rejectProd' />
				</h1>
				<span class="logo"><img src="/images/epc/popup/logo_pop.gif"
					alt="LOTTE MART" /></span>
			</div>
			<!-------------------------------------------------- end of title -- -->

			<!------------------------------------------------------------------ -->
			<!--    process -->
			<!------------------------------------------------------------------ -->
			<!--    process 없음 -->
			<br>
			<!------------------------------------------------ end of process -- -->
			<div class="popup_contents">				
				<!-- -------------------------------------------------------- -->
				<!-- 입고거부 상품 내역  -->
				<!-- -------------------------------------------------------- -->
				<div class="table_data">					
				</div>

				<!-----------------------------------------------end of 검색내역  -->
			</div>
			<!-- class popup_contents// -->
		</div>
		<!-- id popup// -->
	</form>

</body>
</html>
