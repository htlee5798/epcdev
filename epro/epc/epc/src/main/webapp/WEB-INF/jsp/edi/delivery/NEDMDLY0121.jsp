<%@include file="../common.jsp"%>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<script>
	document.addEventListener("DOMContentLoaded", function() {
		doSearch();
	});

	function doSearch() {
		var data = new Object();
		var testArray = new Object();

		var hangmokList =  "<c:out value='${hangmokList}' />";
		var entpCdList = "<c:out value='${entpCdList}' />";
		var strCdList = "<c:out value='${strCdList}' />"
		
		hangmokList = hangmokList.replace(/[\[ \]]/g, '');
		entpCdList = entpCdList.replace(/[\[ \]]/g,"");
		strCdList = strCdList.replace(/[\[ \]]/g,"");
		
		hangmokList = hangmokList.split(',');
		entpCdList = entpCdList.split(',');
		strCdList = strCdList.split(',');
		
		
		var TAB1 = [];
		if(TAB1.length)delArr(TAB1);
		for( var i=0; i<hangmokList.length;i++)
		{
			var HANGMOK = {};
			HANGMOK["HANGMOK"]=hangmokList[i];
			TAB1.push(HANGMOK);
		}
		
		var TAB2 = [];
		if(TAB2.length)delArr(TAB2);
		for( var i=0; i<entpCdList.length;i++)
		{
			var VEN_CD = {};
			VEN_CD["VEN_CD"]=entpCdList[i];
			TAB2.push(VEN_CD);
		}
		
		var TAB3 = [];
		if(TAB3.length)delArr(TAB3);
		for( var i=0; i<strCdList.length;i++)
		{
			if(!strCdList[i])continue;
			var STR_CD = {};
			STR_CD["STR_CD"]=strCdList[i];
			TAB3.push(STR_CD);
		}
		
		
		/* TAB1.push("<c:out value='${hangmokList}' />");
		TAB2.push("<c:out value='${entpCdList}' />");
		TAB3.push("<c:out value='${strCdList}' />"); */
		
		console.log(TAB1);
		console.log(TAB2);
		console.log(TAB3);
		
		//debugger;
		
		var dataInfo ={};
		dataInfo["START_DATE"] = "<c:out value='${dlyProdInfo.formStartDate}' />";
		dataInfo["END_DATE"] =   "<c:out value='${dlyProdInfo.formEndDate}' />";	
		dataInfo["TAB1"] = TAB1;
		dataInfo["TAB2"] = TAB2;
		dataInfo["TAB3"] = TAB3;
		dataInfo["REQCOMMON"] = getReqCommon();
		
		rfcCall("INV0640" , dataInfo);	
	}
 
 	function rfcCallBack(data){
		gridRfc(data);
	}

	function gridRfc(data){
		
      //data = {"result":{"TAB":[{"REMARK":"20160823 ","ZMSG":"7 건 조회되었습니다.","SALE_DY":"20160820 ","STR_CD":"339 ","PROM_DY":" ","QTY":"1.000-","ACCEPT_FG":"0 ","TRD_NO":"192 ","SELL_PROD_CD":"1 ","CUST_ADDR":"노원구 노원로 330 롯데마트 중계점 토이저러스 (색상:딥블루) ","SRCMK_CD":"5702015591461 ","SLIP_NO":"20160820-339-52-192-1 ","CUST_TEL_NO1":" ","CUST_TEL_NO2":"010-5035-2635 ","RECV_SEQ":"1 ","PROD_NM":"레고 미스포춘의 함선 (70605) ","RECV_ADDR":"노원구 노원로 330 롯데마트 중계점 토이저러스 (색상:딥블루) ","CUST_NM":"김영옥 ","POS_NO":"52 ","ACCEPT_DY":"00000000 ","STR_NM":"TRU중계점 ","RECV_TEL_NO1":" ","RECV_TEL_NO2":"010-5035-2635 ","TRD_TYPE_DIVN_CD":"0R00 ","RECV_NM":"김영옥 "},{"REMARK":"20170817 ","ZMSG":"7 건 조회되었습니다.","SALE_DY":"20170814 ","STR_CD":"340 ","PROM_DY":" ","QTY":"1.000-","ACCEPT_FG":"0 ","TRD_NO":"86 ","SELL_PROD_CD":"1 ","CUST_ADDR":"서울시 서초구 반포대로21길 67 래미안서초5차 501동 203호 ","SRCMK_CD":"5702015868822 ","SLIP_NO":"20170814-340-52-86-1 ","CUST_TEL_NO1":" ","CUST_TEL_NO2":"010-4674-2272 ","RECV_SEQ":"1 ","PROD_NM":"레고 10744 썬더 할로우의 크레이지 8 레이스 ","RECV_ADDR":"서울시 서초구 반포대로21길 67 래미안서초5차 501동 203호 ","CUST_NM":"이종은 ","POS_NO":"52 ","ACCEPT_DY":"00000000 ","STR_NM":"서초점 ","RECV_TEL_NO1":" ","RECV_TEL_NO2":"010-4674-2272 ","TRD_TYPE_DIVN_CD":"0R00 ","RECV_NM":"이종은 "},{"REMARK":"20200125 ","ZMSG":"7 건 조회되었습니다.","SALE_DY":"20200118 ","STR_CD":"402 ","PROM_DY":" ","QTY":"1.000 ","ACCEPT_FG":"0 ","TRD_NO":"11 ","SELL_PROD_CD":"2 ","CUST_ADDR":"서울시영등포구도림로187 대림우성아파트3동1307호 ","SRCMK_CD":"5702016369670 ","SLIP_NO":"20200118-402-25-11-1 ","CUST_TEL_NO1":"010-9182-4701 ","CUST_TEL_NO2":"010-9182-4701 ","RECV_SEQ":"1 ","PROD_NM":"레고 어벤져스 아이언맨 연구소 (76125) ","RECV_ADDR":"서울시영등포구도림로187 대림우성아파트3동1307호 ","CUST_NM":"이승민 ","POS_NO":"25 ","ACCEPT_DY":"00000000 ","STR_NM":"구리점 ","RECV_TEL_NO1":"010-9182-4701 ","RECV_TEL_NO2":"010-9182-4701 ","TRD_TYPE_DIVN_CD":"0000 ","RECV_NM":"이승민 "},{"REMARK":"20200125 ","ZMSG":"7 건 조회되었습니다.","SALE_DY":"20200118 ","STR_CD":"402 ","PROM_DY":" ","QTY":"1.000 ","ACCEPT_FG":"0 ","TRD_NO":"11 ","SELL_PROD_CD":"3 ","CUST_ADDR":"서울시영등포구도림로187 대림우성아파트3동1307호 ","SRCMK_CD":"5702016617801 ","SLIP_NO":"20200118-402-25-11-1 ","CUST_TEL_NO1":"010-9182-4701 ","CUST_TEL_NO2":"010-9182-4701 ","RECV_SEQ":"1 ","PROD_NM":"레고 경찰서 (60246) ","RECV_ADDR":"서울시영등포구도림로187 대림우성아파트3동1307호 ","CUST_NM":"이승민 ","POS_NO":"25 ","ACCEPT_DY":"00000000 ","STR_NM":"구리점 ","RECV_TEL_NO1":"010-9182-4701 ","RECV_TEL_NO2":"010-9182-4701 ","TRD_TYPE_DIVN_CD":"0000 ","RECV_NM":"이승민 "},{"REMARK":"20170515 ","ZMSG":"7 건 조회되었습니다.","SALE_DY":"20170505 ","STR_CD":"418 ","PROM_DY":" ","QTY":"1.000 ","ACCEPT_FG":"0 ","TRD_NO":"205 ","SELL_PROD_CD":"1 ","CUST_ADDR":"부천시 상동 527-3번지 진달래마을 효성 2228-202호(색상:블랙) ","SRCMK_CD":"5702015348164 ","SLIP_NO":"20170505-418-48-205-1 ","CUST_TEL_NO1":"010-9116-9026 ","CUST_TEL_NO2":"010-9116-9026 ","RECV_SEQ":"1 ","PROD_NM":"레고 페라리 F40 (10248) ","RECV_ADDR":"부천시 상동 527-3번지 진달래마을 효성 2228-202호(색상:블랙) ","CUST_NM":"윤세진 ","POS_NO":"48 ","ACCEPT_DY":"00000000 ","STR_NM":"삼산점 ","RECV_TEL_NO1":"010-9116-9026 ","RECV_TEL_NO2":"010-9116-9026 ","TRD_TYPE_DIVN_CD":"0000 ","RECV_NM":"윤세진 "},{"REMARK":"20170917 ","ZMSG":"7 건 조회되었습니다.","SALE_DY":"20170910 ","STR_CD":"614 ","PROM_DY":" ","QTY":"1.000 ","ACCEPT_FG":"0 ","TRD_NO":"30 ","SELL_PROD_CD":"1 ","CUST_ADDR":"부산시 해운대구 세실로 174 삼성아파트 108동 1701호 색상:네이비.증정 ;쿨시트 ","SRCMK_CD":"5702015867528 ","SLIP_NO":"20170910-614-41-30-1 ","CUST_TEL_NO1":" ","CUST_TEL_NO2":"010-9917-2879 ","RECV_SEQ":"1 ","PROD_NM":"레고 31057 에어 블레이저 ","RECV_ADDR":"부산시 해운대구 세실로 174 삼성아파트 108동 1701호 색상:네이비.증정 ;쿨시트 ","CUST_NM":"이혜연 ","POS_NO":"41 ","ACCEPT_DY":"00000000 ","STR_NM":"진장점 ","RECV_TEL_NO1":" ","RECV_TEL_NO2":"010-9917-2879 ","TRD_TYPE_DIVN_CD":"0000 ","RECV_NM":"이혜연 "},{"REMARK":"20190117 ","ZMSG":"7 건 조회되었습니다.","SALE_DY":"20190112 ","STR_CD":"722 ","PROM_DY":"경비실에 보관후 문자나 전화요망!! ","QTY":"1.000 ","ACCEPT_FG":"0 ","TRD_NO":"111 ","SELL_PROD_CD":"1 ","CUST_ADDR":"광주시 광산구 풍영로63 영천마을 9단지 909동1503호 색상:차콜(썬바이저) ","SRCMK_CD":"5702016368871 ","SLIP_NO":"20190112-722-51-111-1 ","CUST_TEL_NO1":"010-2382-4119 ","CUST_TEL_NO2":"010-2382-4119 ","RECV_SEQ":"1 ","PROD_NM":"레고 스파이더맨의 스파이더 크롤러 (76114) ","RECV_ADDR":"광주시 광산구 풍영로63 영천마을 9단지 909동1503호 색상:차콜(썬바이저) ","CUST_NM":"임시연 ","POS_NO":"51 ","ACCEPT_DY":"00000000 ","STR_NM":"TRU수완점 ","RECV_TEL_NO1":"010-2382-4119 ","RECV_TEL_NO2":"010-2382-4119 ","TRD_TYPE_DIVN_CD":"0000 ","RECV_NM":"임시연 "}],"RESPCOMMON":{"ZPOROWS":"7 ","ZPOTIME":111013,"ZPODATE":20210122,"ZPOMSGS":"7 건 조회되었습니다.","ZPOSTAT":"S"}}};

		var rows = data.result.RESPCOMMON.ZPOROWS*1;
	    var result = data.result;
		setTbodyInit("dataListbody");

		if (rows != 0) {
			var dlyProdInfo = result.TAB;
			
			 console.log(dlyProdInfo);
			_setTbodyMasterValue(dlyProdInfo);
		} else {
			setTbodyNoResult("dataListbody", 10);
		}
	}
	
 	function _setTbodyMasterValue(data) {
		setTbodyInit("dataListbody");	// dataList 초기화
		$("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
	}
 	
 	function delArr(arr){
		for(var i=0; i< arr.length; i++)arr.pop();
	}
 	
 	function rfcCall(proxyNm, param) {
		if (proxyNm == "") {
			//alert("PROXYëªì íì¸í´ì£¼ì¸ì.");
			return;
		}
		
		// 입력란
		var requestParam = {};
		requestParam["param"] 	= JSON.stringify(param);
		requestParam["proxyNm"] = proxyNm;
		requestParam["slipNo"] = "<c:out value='${dlyProdInfo.slipNo}' />";
		requestParam["srcmkCd"] = "<c:out value='${dlyProdInfo.srcmkCd}' />";
		//console.log(requestParam);
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/comm/rfcCallDlyProd.json" />',
			data : JSON.stringify(requestParam),
			success : function(data) {
				rfcCallBack(data);
			}
		});
	}
 	
</script>
<script id="dataListTemplate" type="text/x-jquery-tmpl">
<tr class="r1">
		<td align="center" rowspan="3">
				<c:out value="\${SLIP_NO}" />
		</td>
		<td align="center"><c:out value="\${STR_NM}" /></td>
		<td align="center"><c:out value="\${SRCMK_CD}" /></td>
		<td align="left">&nbsp;<c:out value="\${PROD_NM }" /></td>
		<td align="center"><c:out value="\${PROM_DY}" /></td>
		<td align="right">
			<c:out value="\${QTY}" />
		</td>
	</tr>
	<tr class="r1">
		<td align="center"><c:out value="\${SALE_DY}" /></td>
		<td align="center"><c:out value="\${CUST_NM}" /></td>
		<td align="left">&nbsp;<c:out value="\${CUST_ADDR}" /></td>
		<td align="center"><c:out value="\${CUST_TEL_NO1}" /></td>
		<td align="center"><c:out value="\${CUST_TEL_NO2}" /></td>
	</tr>
	<tr class="r1">
		<td align="center"><c:out value="\${ACCEPT_DY}" /></td>
		<td align="center"><c:out value="\${RECV_NM}" /></td>
		<td align="left">&nbsp;<c:out value="\${RECV_ADDR}" /></td>
		<td align="center"><c:out value="\${RECV_TEL_NO1}" /></td>
		<td align="center"><c:out value="\${RECV_TEL_NO2}" /></td>
	</tr>
	<tr class="r1">
		<td align="center" colspan="7">&nbsp; <c:out value="\${REMARK}" /></td>
	</tr>
</script>

</head>
<body>
	<div id='test_inner'></div>
	<table class="bbs_list" cellpadding="0" cellspacing="0" border="0"
		id="testTable1">
		<colgroup>
			<col style="width: 150px;" />
			<col style="width: 90px;" />
			<col style="width: 90px;" />
			<col style="width: 250px;" />
			<col style="width: 90px;" />
			<col style="width: 90px;" />
			<col />
		</colgroup>
		<tr>
			<th rowspan="3">전표번호</th>
			<th>점포명</th>
			<th>판매코드</th>
			<th>상품명</th>
			<th>희망배송일</th>
			<th>수량</th>
		</tr>
		<tr>
			<th>판매일자</th>
			<th>의뢰고객명</th>
			<th>의뢰주소</th>
			<th>의뢰전화1</th>
			<th>의뢰전화2</th>
		</tr>
		<tr>
			<th>접수일자</th>
			<th>인수고객</th>
			<th>인수주소</th>
			<th>인수전화1</th>
			<th>인수전화2</th>
		</tr>
		<tr>
			<th colspan="6">비고</th>
		</tr>
	</table>
	<div
		style="width: 100%; height: 388px; overflow-x: hidden; overflow-y: scroll; overflow-x: scroll; table-layout: fixed;">
		<table class="bbs_list" cellpadding="0" cellspacing="0" border="0"
			id="testTable2">
			<colgroup>
				<col style="width: 150px;" />
				<col style="width: 90px;" />
				<col style="width: 90px;" />
				<col style="width: 250px;" />
				<col style="width: 90px;" />
				<col style="width: 90px;" />
				<col />
			</colgroup>
			<!-- Data List Body Start ------------------------------------------------------------------------------>
			<tbody id="dataListbody" />
			<!-- Data List Body End   ------------------------------------------------------------------------------>
		</table>
	</div>
</body>
</html>
