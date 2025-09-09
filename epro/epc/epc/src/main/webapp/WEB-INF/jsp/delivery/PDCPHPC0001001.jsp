<%--
- Author(s): sdcho
- Created Date: 2014. 05. 07
- Version : 1.0
- Description : 우편번호 조회 (신주소변환)

--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<c:import url="/common/commonHead.do" />

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<title><spring:message code="title.epc"/></title>

<style type="text/css">
.popup_contents .addr_step {
	line-height: 8px;
}
.add_select {
	width: 97.5%;
}
.add_select p {
	display: block; 
}
.add_select p.tit {  
  margin:15px 0 5px 0;
  line-height: 15px;
  font-weight: bold;
}
.add_select p.add_box01 {  
  border: 1px solid #ddcfc9;
  padding: 10px 14px;
  font-size: 12px;
  margin: 0;
}
.add_select p.add_box02 {  
  border: 1px solid #ddcfc9;
  padding: 10px 14px;
  font-size: 12px;
  margin: 0;
  line-height: 8px;
}

.tab2	{width:100%; margin-left:5px; margin-right:5px; padding-bottom:1px; }
.tab2	ul	{width:100%; overflow:hidden;}
.tab2	li	{float:left; text-align:center; width:44%; height:23px; margin:1px 1px 0 0; padding:7px 10px 0; border-top:1px solid #d5d5d5; border-left:1px solid #d5d5d5; border-right:1px solid #d5d5d5; background-color:white; cursor:pointer; cursor:hand; }
.tab2	li	a:link,	.tab	li	a:hover,	.tab	li	a:active	{color:#999;}
.tab2	li.on	{font-weight:bold; border-top:1px solid #d5d5d5; border-left:1px solid #d5d5d5; border-right:1px solid #d5d5d5;}  
.tab2	li.on	a:link,	.tab	li.on	a:hover,	.tab	li.on	a:active	{font-weight:bold; color:#FFF;}  
.tab2	li.off	{border-top:1px solid #e5e5e5; border-left:1px solid #e5e5e5; border-right:1px solid #e5e5e5; border-bottom:1px solid #e5e5e5; background-color:#f5f5f5;}  /* 111128 추가 */
.tab2	li.off	a:link,	.tab	li.on	a:hover,	.tab	li.on	a:active	{font-weight:bold; color:#FFF;}  

</style>

<!-- 그리드 헤더처리 -->
<script type="text/javascript">

//1번 시트
function setIBS1() {
	createIBSheet2(document.getElementById("ibsheet1"), "ibs1", "100%", "180px");
	ibs1.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
	
	var ibsData = {};	
	ibsData.Cfg = {
			SizeMode: sizeAuto,
			SearchMode: smGeneral,
			Page: 50,
			MergeSheet: msHeaderOnly
	};
	ibsData.HeaderMode = {
		Sort:0, ColMove: 0, ColResize: 1, HeaderCheck: 1	
	};
	ibsData.Cols = [
		{SaveName:"SEQC",				 			Header:"순번", 					Type:"Text",	Width:50,	Align:"Center", Sort:false, Edit:false, Hidden:true},
	    {SaveName:"POST_NO", 						Header: "우편번호", 			Type:"Text",	Width:100, Align:"Center", Sort:false, Edit:false, Cursor:"Pointer", FontColor:"blue"},
	    {SaveName:"ALL_ADDR",			 			Header:"주소",					Type:"Text", 	Width:400,	Align:"Left", 	  Sort:false, Edit:false},
	    {SaveName:"POST_ADDR",			 		Header:"주소1",					Type:"Text", 	Width:340,	Align:"Center", Sort:false, Edit:false, Hidden:true},
	    {SaveName:"DTL_ADDR",			 			Header:"주소2",					Type:"Text", 	Width:340,	Align:"Center", Sort:false, Edit:false, Hidden:true},
	    {SaveName:"ONLINE_STR_CD", 		 	Header:"점코드",  				Type:"Text", 	Width:50,	Align:"Center", Sort:false, Edit:false, Hidden:true},
	    {SaveName:"ON_STR_CD", 			 		Header:"온라인점코드",  	Type:"Text", 	Width:50,	Align:"Center", Sort:false, Edit:false, Hidden:true},
	    {SaveName:"PRST_MALL_STR_CD", 	 	Header:"명절점코드",  		Type:"Text", 	Width:50,	Align:"Center", Sort:false, Edit:false, Hidden:true},
	    {SaveName:"REFGT_DIRDEL_STR_CD", 	Header:"명절냉장점코드",  Type:"Text", 	Width:50,	Align:"Center", Sort:false, Edit:false, Hidden:true}
	 	
	];
	
	IBS_InitSheet(ibs1, ibsData);
	ibs1.SetWaitImageVisible(0);
	
}
//2번 시트
function setIBS2() {
	createIBSheet2(document.getElementById("ibsheet2"), "ibs2", "100%", "180px");
	ibs2.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
	
	var ibsData = {};	
	ibsData.Cfg = {
			SizeMode: sizeAuto,
			SearchMode: smGeneral,
			Page: 50,
			MergeSheet: msHeaderOnly
	};
	ibsData.HeaderMode = {
		Sort:0, ColMove: 0, ColResize: 1, HeaderCheck: 1	
	};
	ibsData.Cols = [
		{SaveName:"SEQC",							Header:"순번", 					Type:"Text", Width:50,		Align:"Center", Sort:false, Edit:false, Hidden:true},
	    {SaveName:"POST_NO", 			 			Header:"우편번호", 			Type:"Text", Width:100,   Align:"Center", Sort:false, Edit:false, Cursor:"Pointer", FontColor:"blue"},
	    {SaveName:"ALL_ADDR",			 			Header:"주소",					Type:"Text", Width:400,	Align:"Left", 	  Sort:false, Edit:false},
	    {SaveName:"POST_ADDR",			 		Header:"주소1",					Type:"Text", Width:340,	Align:"Center", Sort:false, Edit:false, Hidden:true},
	    {SaveName:"DTL_ADDR",			 			Header:"주소2",					Type:"Text", Width:340,	Align:"Center", Sort:false, Edit:false, Hidden:true},
	    {SaveName:"ONLINE_STR_CD", 			Header:"점코드",  				Type:"Text", Width:50,		Align:"Center", Sort:false, Edit:false, Hidden:true},
	    {SaveName:"ON_STR_CD", 			 		Header:"온라인점코드",  	Type:"Text", Width:50,		Align:"Center", Sort:false, Edit:false, Hidden:true},
	    {SaveName:"PRST_MALL_STR_CD", 	 	Header:"명절점코드",  		Type:"Text", Width:50,		Align:"Center", Sort:false, Edit:false, Hidden:true},
	    {SaveName:"REFGT_DIRDEL_STR_CD", 	Header:"명절냉장점코드",  Type:"Text", Width:50,		Align:"Center", Sort:false, Edit:false, Hidden:true}
	];
	
	IBS_InitSheet(ibs2, ibsData);
	ibs2.SetWaitImageVisible(0);
	
}
$(document).ready(function() {
	setIBS1();
	setIBS2();
	
}); //end of ready
/***********************************************************
 * 조회  함수
 ***********************************************************/
function doSearch(addressDivn) {
	 var url = '<c:url value="/delivery/postsearch.do"/>';
	 var param = new Object();
	 if(addressDivn == "j"){
			
			param.addressDivn	= addressDivn;
			param.dongNm 		= $('#dongNm').val();
		}
		else if(addressDivn == "n"){

			param.addressDivn	=addressDivn;
			param.sido				=$('#sido').val();
			param.gungu				=$('#gungu').val();
			param.road				=$('#road').val();
			param.buildNum		=$('#buildNum').val();
			param.buildName		=$('#buildName').val();
		}
		
		//validation
		 if(!validation_doSearch(addressDivn)){
			return;
		} 
	 if(addressDivn == "j"){
		loadIBSheetData(ibs1, url, null, null, param);
	 }
	else if(addressDivn == "n"){
		loadIBSheetData(ibs2, url, null, null, param);
	}
}
function ibs1_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH){
	if(Row == 0)return;
	var strColumnKey = ibs1.ColSaveName(Col);
	var addressDivn = 'j'; 
	if(strColumnKey == "POST_NO")
	{
		if(addressDivn == "j"){
			$('#'+addressDivn+'_input_zipSeq').val(ibs1.GetCellValue(Row, "SEQC"));
			$('#'+addressDivn+'_input_zipCd') .val(ibs1.GetCellValue(Row, "POST_NO"));
			$('#'+addressDivn+'_input_addr1') .val(ibs1.GetCellValue(Row, "POST_ADDR"));
			$('#'+addressDivn+'_input_zipCd').focus();
		}
		else if(addressDivn == "n"){
			$('#'+addressDivn+'_input_zipSeq').val(ibs1.GetCellValue(Row, "SEQC"));
			$('#'+addressDivn+'_input_zipCd') .val(ibs1.GetCellValue(Row, "POST_NO"));
			$('#'+addressDivn+'_input_addr1') .val(ibs1.GetCellValue(Row, "POST_ADDR"));
			$('#'+addressDivn+'_input_addr2') .val(ibs1.GetCellValue(Row, "DTL_ADDR"));
			$('#'+addressDivn+'_input_zipCd').focus();
		}
		$('#online_str_cd').val(ibs1.GetCellValue(Row, "ONLINE_STR_CD"	));
		$('#on_str_cd')	   .val(ibs1.GetCellValue(Row, "ON_STR_CD"));
		$('#pm_str_cd')	   .val(ibs1.GetCellValue(Row, "PRST_MALL_STR_CD"));
		$('#rd_str_cd')	   .val(ibs1.GetCellValue(Row, "REFGT_DIRDEL_STR_CD"));
	}
}
function ibs2_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH){
	if(Row == 0)return;
	var strColumnKey = ibs2.ColSaveName(Col);
	var addressDivn = 'n';
	if(strColumnKey == "POST_NO")
	{
		if(addressDivn == "j"){
			$('#'+addressDivn+'_input_zipSeq').val(ibs2.GetCellValue(Row, "SEQC"));
			$('#'+addressDivn+'_input_zipCd') .val(ibs2.GetCellValue(Row, "POST_NO"));
			$('#'+addressDivn+'_input_addr1') .val(ibs2.GetCellValue(Row, "POST_ADDR"));
			$('#'+addressDivn+'_input_zipCd').focus();
		}
		else if(addressDivn == "n"){
			$('#'+addressDivn+'_input_zipSeq').val(ibs2.GetCellValue(Row, "SEQC"));
			$('#'+addressDivn+'_input_zipCd') .val(ibs2.GetCellValue(Row, "POST_NO"));
			$('#'+addressDivn+'_input_addr1') .val(ibs2.GetCellValue(Row, "POST_ADDR"));
			$('#'+addressDivn+'_input_addr2') .val(ibs2.GetCellValue(Row, "DTL_ADDR"));
			$('#'+addressDivn+'_input_zipCd').focus();
		}
		$('#online_str_cd').val(ibs2.GetCellValue(Row, "ONLINE_STR_CD"	));
		$('#on_str_cd')	   .val(ibs2.GetCellValue(Row, "ON_STR_CD"));
		$('#pm_str_cd')	   .val(ibs2.GetCellValue(Row, "PRST_MALL_STR_CD"));
		$('#rd_str_cd')	   .val(ibs2.GetCellValue(Row, "REFGT_DIRDEL_STR_CD"));
	}
}
</script>
<script type="text/javascript">
//validation
function validation_doSearch(addressDivn){
	
	if(addressDivn == "j"){
		if(Common.isEmpty($('#dongNm').val())){
			alert("읍/면/동을 입력해주세요.");
			return false;
		}
	}
	else if(addressDivn == "n"){
		if(Common.isEmpty($('#sido').val())){
			alert("시/도를 선택해주세요.");
			return false;
		}
		if(Common.isEmpty($('#gungu').val())){
			alert("시/군/구를 선택해주세요.");
			return false;
		}
		if(Common.isEmpty($('#road').val())){
			alert("도로명을 입력해주세요.");
			return false;
		}
	}
	return true;
}
</script>

<script language="javascript" type="text/javascript">
	$(document).ready(function(){
		
		fnOnLoad('j');
		
		// 닫기
		$('#winclose').click(function() {
			this.close();
		});
	});
	
	// 초기화면로드
	function fnOnLoad(addressDivn){
		changeTab(addressDivn);
		windowReset(addressDivn, '1'); // page1 를 위한 팝업크기조절
	}
	
	// 버튼: 다음 page1 -> page2
	function next(addressDivn)
	{
		var zipCd = $('#'+addressDivn+'_input_zipCd').val();
		var addr1 = $('#'+addressDivn+'_input_addr1').val();
		var addr2 = $('#'+addressDivn+'_input_addr2').val();
	
		//validation
		if(!validation_next(zipCd, addr1, addr2, addressDivn)){
			return; 
		}
		
		$('#'+addressDivn+'_step1').hide();
		$('#'+addressDivn+'_step2').hide();
		$('#'+addressDivn+'_step3').hide();
		$('#'+addressDivn+'_step4').show();
		windowReset(addressDivn, '2'); // page2 를 위한 팝업크기조절
		
		rfnAddr(zipCd, addr1, addr2, addressDivn);
	}
	//validation
	function validation_next(zipCd, addr1, addr2, addressDivn){
		if(Common.isEmpty(zipCd)){
			alert("우편번호는 필수 입력값입니다.");
			return false;
		}
		if(Common.isEmpty(addr1)){
			switch(addressDivn){
				case "j": alert("지번주소는 필수 입력값입니다.");   break;
				case "n": alert("도로명주소는 필수 입력값입니다."); break;
			}
			return false;
		}
		if(Common.isEmpty(addr2)){
			alert("상세주소는 필수 입력값입니다.");
			return false;
		}
		if(Common.isEmpty(addressDivn)){
			alert("error:addressDivn["+addressDivn+"]");
			return false;
		}
		return true;
	}
	
	// 버튼: 뒤로 page2 -> page1
	function back(addressDivn)
	{
		$('#'+addressDivn+'_step1').show();
		$('#'+addressDivn+'_step2').show();
		$('#'+addressDivn+'_step3').show();
		$('#'+addressDivn+'_step4').hide();
		
		// step4. 변환된 주소 1,2,3 초기화
		$('#'+addressDivn+'_addr1_div').show();
		$('#'+addressDivn+'_addr2_div').show();
		$('#'+addressDivn+'_addr3_div').show();
		$('#'+addressDivn+'_addr_failed').hide();
		
		// 변환된 주소 삭제
		$('#'+addressDivn+'_addr1').text('');
		$('#'+addressDivn+'_addr2').text('');
		$('#'+addressDivn+'_addr3').text('');
				
		windowReset(addressDivn, '1'); // page1 를 위한 팝업크기조절
	}
	// 버튼: 초기화
	function init(addressDivn){
		if(confirm("검색 및 입력한 내용을 초기화 하시겠습니까?")) {
			var url = '<c:url value="/delivery/post.do?addressDivn='+addressDivn+'"/>';
			//var url = '<c:url value="/delivery/post.do"/>';
			location.href = url;
		}
	}
	// 팝업크기조절
	function windowReset(addressDivn, page){
		if(addressDivn == 'j'){
			switch(page){
			case '1' : window.resizeTo(500,628); break;
			case '2' : window.resizeTo(500,520); break;
			default:
				break;
			}
		}
		else if(addressDivn == 'n'){
			switch(page){
			case '1' : window.resizeTo(500,712); break;
			case '2' : window.resizeTo(500,520); break;
			default:
				break;
			}
		}
	}
	// 탭 
	function changeTab(addressDivn){
		
		$('#'+addressDivn+'_step1').show();
		$('#'+addressDivn+'_step2').show();
		$('#'+addressDivn+'_step3').show();
		$('#'+addressDivn+'_step4').hide();
		
		// step4. 변환된 주소 1,2,3 초기화
		$('#'+addressDivn+'_addr1_div').show();
		$('#'+addressDivn+'_addr2_div').show();
		$('#'+addressDivn+'_addr3_div').show();
		$('#'+addressDivn+'_addr_failed').hide();
		
		windowReset(addressDivn, '1'); // page1 를 위한 팝업크기조절
		switch(addressDivn){
		case 'j' :  $('#j_tab').addClass("on");
					$('#j_tab').removeClass("off");
					$('#n_tab').addClass("off");
					$('#n_tab').removeClass("on");
					$('#j_address').show();
					$('#n_address').hide();
					$('#dongNm').focus();
					break;
		case 'n' :  $('#j_tab').addClass("off");
					$('#j_tab').removeClass("on");
					$('#n_tab').addClass("on");
					$('#n_tab').removeClass("off");
					$('#j_address').hide();
					$('#n_address').show();
					break;
		default:
			break;
		}
	}
	// 주소변환
	function rfnAddr(zipCd, addr1, addr2, addressDivn){
		
		$.ajax({ 
				type : "POST",
				async : false,
				url : "<c:url value='/delivery/rfnCustCommonAddrList.do'/>",
				dataType : "json",
				timeout : 5000,
				cache : true,
				data : {
					"zipcode"	:zipCd,
					"addr1"		:addr1,
					"addr2"		:addr2,
					"SearchMode":addressDivn.toUpperCase()
				},
				success: function(data){
					if(data == null || data.length < 1){
						alert('변환에 실패 했습니다. 창을 닫고 다시 시도해주세요');
						return "";
					}else{
						//callback
						_callBackRfnAddr(addressDivn, data);	
					}
				}
			} 
		);
	}
	function _callBackRfnAddr(addressDivn, data){
		
		// 1. step4에 변환된 주소 보여주기
		fnSetDisplayAddr(addressDivn, data);		
		// 2. 변환된 결과를 form 에 set
		fnSetRfnResult(addressDivn, data);			
	}
	// step4에 변환된 주소 보여주기
	function fnSetDisplayAddr(addressDivn, data){
	/**
		지번주소로 검색했을 때,
			NODE = D 가 정제된주소
			NODE = P 가 도로명주소
		도로명주소로 검색했을 때,
			NODE = P 가 정제된주소
			NODE = D 가 도로명주소
	*/
		var RCD3 = data.RCD3;
		var RMG3 = data.RMG3;
		var list = data.DATA;
		var D = 0; // index of node D
		var P = 0; // index of node P
		for(var i=0; i<list.length; i++){
			if(list[i].NODE == "D"){
				D = i;
			}
			else if(list[i].NODE == "P"){
				P = i;
			}
		}
		// addr1. 입력한 주소
		$('#'+addressDivn+'_addr1').text($('#'+addressDivn+'_input_zipCd').val() + " " + $('#'+addressDivn+'_input_addr1').val() + " " + $('#'+addressDivn+'_input_addr2').val()  );
		$("input:radio[id='"+addressDivn+"_select_addr1']").attr("checked",true);
		// 1:1매칭 케이스(지번주소로 검색) 
		if(RCD3 == "I"){		//addressDivn = j
			// addr2. 정제된 주소
			$('#'+addressDivn+'_addr2').text(formatZipCode(list[D].ZIPM6) +" "+ list[D].ADDR1H +" "+ list[D].STDADDR);
			// addr3. 도로명 주소
			$('#'+addressDivn+'_addr3').text(formatZipCode(list[P].ZIPR6) +" "+ list[P].NADR1S +", "+ list[P].NADR3S + " " + list[P].NADREH);
			// 도로명 주소 선택
			$("input:radio[id='"+addressDivn+"_select_addr3']").attr("checked",true);
		}
		// 1:1매칭 케이스(도로명주소로 검색)
		else if (RCD3 == "H"){	//addressDivn = n
			// addr2. 정제된 주소
			$('#'+addressDivn+'_addr2').text(formatZipCode(list[P].ZIPM6) +" "+ list[P].ADDR1H +" "+ list[P].STDADDR);
			// addr3. 도로명 주소
			$('#'+addressDivn+'_addr3').text(formatZipCode(list[D].ZIPR6) +" "+ list[D].NADR1S +", "+ list[D].NADR3S + " " + list[D].NADREH);
			// 도로명 주소 선택
			$("input:radio[id='"+addressDivn+"_select_addr3']").attr("checked",true);
		}
		//[!]관리자 시스템에선 고려하지 않는 케이스 -> 실패처리
		else{
			$('#'+addressDivn+'_addr2_div').hide();		//정제된 주소 숨김
			$('#'+addressDivn+'_addr3_div').hide();		//도로명 주소 숨김
			$('#'+addressDivn+'_addr_failed').show();	//변환실패문구
		}
	}
	
	function fnSetRfnResult(addressDivn, data){
	/**
		지번주소로 검색했을 때,
			NODE = D 가 정제된주소
			NODE = P 가 도로명주소
		도로명주소로 검색했을 때,
			NODE = P 가 정제된주소
			NODE = D 가 도로명주소
	*/
		var RCD3 = data.RCD3;
		var list = data.DATA;
		
		var D = 0; // index of node D
		var P = 0; // index of node P
		for(var i=0; i<list.length; i++){
			if(list[i].NODE == "D"){
				D = i;
			}
			else if(list[i].NODE == "P"){
				P = i;
			}
		}
		
		// 1.입력한 주소
		$('#InputZipSeq').val($('#'+addressDivn+'_input_zipSeq').val());
		$('#InputZip')	 .val(unformatZipCode($('#'+addressDivn+'_input_zipCd').val()));
		$('#InputAddr1') .val($('#'+addressDivn+'_input_addr1').val());
		$('#InputAddr2') .val($('#'+addressDivn+'_input_addr2').val());
		
		if(RCD3 == "I"){
			// 2.정제된 지번주소
			$('#JibunZipSeq').val(list[D].ZIPSJ);
			$('#JibunZip')	 .val(list[D].ZIPM6);
			$('#JibunAddr1') .val(list[D].ADDR1H);
			$('#JibunAddr2') .val(list[D].STDADDR);
			
			// 3.도로명주소
			$('#RoadZipSeq') .val(list[P].ZIPSJ); 	// :정제된 지번주소의 SEQ 와 도로명주소의 SEQ 는 같음
			$('#RoadZip')	 .val(list[P].ZIPR6);
			$('#RoadAddr1')  .val(list[P].NADR1S);
			$('#RoadAddr2')  .val(list[P].NADR3S + " " + list[P].NADREH);
			
			$('#ADDRPS')  	 .val(list[D].ADDRPS);	//지번PNU
			$('#NNMZ')  	 .val(list[P].NNMZ);	//조합된건물관리번호
		}
		else if(RCD3 == "H"){
			// 2.정제된 지번주소
			$('#JibunZipSeq').val(list[P].ZIPSJ);
			$('#JibunZip')	 .val(list[P].ZIPM6);
			$('#JibunAddr1') .val(list[P].ADDR1H);
			$('#JibunAddr2') .val(list[P].STDADDR);
			
			// 3.도로명주소
			$('#RoadZipSeq') .val(list[D].ZIPSJ);	// :정제된 지번주소의 SEQ 와 도로명주소의 SEQ 는 같음
			$('#RoadZip')	 .val(list[D].ZIPR6);
			$('#RoadAddr1')  .val(list[D].NADR1S);
			$('#RoadAddr2')  .val(list[D].NADR3S + " " + list[D].NADREH);
			
			$('#ADDRPS')  	 .val(list[P].ADDRPS);	//지번PNU
			$('#NNMZ')  	 .val(list[D].NNMZ);	//조합된건물관리번호
		}
		
		$('#addrGetGbn').val('1');			//주소입수구분(온라인:1, 일괄전환:2)
		$('#RCD3')		.val(data.RCD3);	//결과코드
		$('#RMG3')		.val(data.RMG3);	//결과메시지
	}
	
	// 도로명주소Tab. 시/도 select box 변경 시
	function onChangeSido(){
		var sido = $('#sido').val();
		// 시/도를 '선택해주세요'로 바꾸면 시/군/구도 초기화
		if(sido == "" ){
			$('#gungu option').remove();
			$('#gungu').append("<option value=''>선택해 주세요.</option>");
			return;
		}
		
		$.ajax({ 
				type     : "POST",
				async    : false,
				url      : "<c:url value='/delivery/selectNewZipCodeSigunguList.do'/>",
				dataType : "json",
				timeout  : 5000,
				cache    : true,
				data     : {
					"sido"	:sido
				},
				success: function(data){
					if(data == null || data.length < 1){
						alert('null');
						return "";
					}
					_callBackOnChangeSido(data);
				}
		});
		
		return "";
	}
	// 도로명주소Tab. 시/군/구 select box 세팅
	function _callBackOnChangeSido(gunguList){
		//초기화
		$('#gungu option').remove();
		$('#gungu').append("<option value=''>선택해 주세요.</option>");
		
		for(var i=0; i<gunguList.length; i++){
			 $('#gungu').append("<option value='"+gunguList[i].GU_NM+"'>"+gunguList[i].GU_NM+"</option>");
		}
	}
	
	// 버튼. 확인
	function btnConfirm(addressDivn){
			
		var radioValue = $(':radio[name="'+addressDivn+'_select_addr"]:checked').val();
		$('#selAddrType').val(radioValue);	//01:입력주소/02:정제지번주소/03:도로명주소
		
		//validation
		if(!validation_btnConfirm()){
			return;
		}
		
		var post = '';
		
		if (radioValue == '01') {
			post = {POST_NO:$('#InputZip').val(), 
					POST_ADDR:$('#InputAddr1').val(), 
					DTL_ADDR:$('#InputAddr2').val(), 
					SEQC:$('#InputZipSeq').val(), 
					ONLINE_STR_CD:$('#online_str_cd').val(), 
					ON_STR_CD:$('#on_str_cd').val(), 
					PM_STR_CD:$('#pm_str_cd').val(), 
					RD_STR_CD:$('#rd_str_cd').val()};
		} else if (radioValue == '02') {
			post = {POST_NO:$('#JibunZip').val(), 
					POST_ADDR:$('#JibunAddr1').val(), 
					DTL_ADDR:$('#JibunAddr2').val(), 
					SEQC:$('#JibunZipSeq').val(), 
					ONLINE_STR_CD:$('#online_str_cd').val(), 
					ON_STR_CD:$('#on_str_cd').val(), 
					PM_STR_CD:$('#pm_str_cd').val(), 
					RD_STR_CD:$('#rd_str_cd').val()};
		} else {
			post = {POST_NO:$('#RoadZip').val(), 
					POST_ADDR:$('#RoadAddr1').val(), 
					DTL_ADDR:$('#RoadAddr2').val(), 
					SEQC:$('#RoadZipSeq').val(), 
					ONLINE_STR_CD:$('#online_str_cd').val(), 
					ON_STR_CD:$('#on_str_cd').val(), 
					PM_STR_CD:$('#pm_str_cd').val(), 
					RD_STR_CD:$('#rd_str_cd').val()};
		}

		opener.pasteZip(post);
		this.close();
	}
	
	function validation_btnConfirm(){
		var selAddrType = $('#selAddrType').val();
		
		if(Common.isEmpty(selAddrType)){
			alert("주소를 선택해주세요.");
			return false;
		}
		
		if(selAddrType == "01"){
			if(Common.isEmpty($('#InputZipSeq').val())){
				alert("입력하신 주소가 비어있습니다.");
				return false;
			}
		}
		if(selAddrType == "02"){
			if(Common.isEmpty($('#JibunZipSeq').val())){
				alert("정제된 지번주소가 비어있습니다.");
				return false;
			}
		}
		if(selAddrType == "03"){
			if(Common.isEmpty($('#RoadZipSeq').val())){
				alert("도로명주소가 비어있습니다.");
				return false;
			}
		}
		
		return true;
	}
	
	//우편번호포맷터
	// ex) 123456 --> 123-456
	function formatZipCode(zipCode){
		if(zipCode == null) 
			return zipCode;
		
		if(zipCode.length == 6){
			return zipCode.substring(0,3) + "-" + zipCode.substring(3,6);
		}
		else{
			return zipCode;
		}
	}
	//우편번호포맷 제거
	function unformatZipCode(zipCode){
		if (zipCode == null) return zipCode;
		
		return zipCode.replaceAll('-', '');
	}
</script>
</head>

<body>

<form id="resultForm" name="resultForm" method="post" target="_self">

<!--0-->	<input id="addressDivn" name="addressDivn" 	value="" type="hidden">
	
<!--1-->	<input id="selAddrType" name="selAddrType"	value="" type="hidden"> <!-- 선택주소( 01:입력주소/02:정제지번주소/03:도로명주소) -->
	
<!--2-->	<input id="InputZipSeq" name="InputZipSeq" 	value="" type="hidden">
<!--3-->	<input id="InputZip" 	name="InputZip" 	value="" type="hidden">
<!--4-->	<input id="InputAddr1" 	name="InputAddr1" 	value="" type="hidden">
<!--5-->	<input id="InputAddr2" 	name="InputAddr2" 	value="" type="hidden">
	
<!--6-->	<input id="JibunZipSeq" name="JibunZipSeq" 	value="" type="hidden">
<!--7-->	<input id="JibunZip" 	name="JibunZip" 	value="" type="hidden">
<!--8-->	<input id="JibunAddr1" 	name="JibunAddr1" 	value="" type="hidden">
<!--9-->	<input id="JibunAddr2" 	name="JibunAddr2" 	value="" type="hidden">
	
<!--10-->	<input id="RoadZipSeq" 	name="RoadZipSeq" 	value="" type="hidden">
<!--11-->	<input id="RoadZip" 	name="RoadZip" 		value="" type="hidden">
<!--12-->	<input id="RoadAddr1" 	name="RoadAddr1" 	value="" type="hidden">
<!--13-->	<input id="RoadAddr2" 	name="RoadAddr2" 	value="" type="hidden">
	
<!--14-->	<input id="addrGetGbn" 	name="addrGetGbn"	value="" type="hidden"> <!-- 주소입수구분(온라인:1, 일괄전환:2) -->
<!--15-->	<input id="ADDRPS" 		name="ADDRPS"		value="" type="hidden"> <!-- 지번PNU -->
<!--16-->	<input id="NNMZ" 		name="NNMZ"			value="" type="hidden"> <!-- 조합된건물관리번호 -->
<!--17-->	<input id="RCD3" 		name="RCD3" 		value="" type="hidden"> <!-- 결과코드 -->

<!--0-->	<input id="RMG3" 		name="RMG3" 		value="" type="hidden"> <!-- 결과메시지 -->	

<!--0-->	<input id="online_str_cd" 	name="online_str_cd" 	value="" type="hidden">
<!--0-->	<input id="on_str_cd" 		name="on_str_cd" 		value="" type="hidden">
<!--0-->	<input id="pm_str_cd" 		name="pm_str_cd" 		value="" type="hidden">
<!--0-->	<input id="rd_str_cd" 		name="rd_str_cd" 		value="" type="hidden">

<!--0-->	<input id="ZIPSJ" 	value="" 	type="hidden"><!-- 지번 맵핑코드 -->
<!--0-->	<input id="ZIPM6" 	value="" 	type="hidden"><!-- 지번우편번호 -->
<!--0-->	<input id="ADDR1H" 	value="" 	type="hidden"><!-- 지번 주소1 -->
<!--0-->	<input id="STDADDR" value="" 	type="hidden"><!-- 지번 주소2 -->
<!--0-->	<input id="GISX" 	value="" 	type="hidden"><!-- 지번 좌표X -->
<!--0-->	<input id="GISY" 	value="" 	type="hidden"><!-- 지번 좌표Y -->
<!--0-->	<input id="ZIPR6" 	value="" 	type="hidden"><!-- 도로명 우편번호 -->
<!--0-->	<input id="ZIPRSJ" 	value="" 	type="hidden"><!-- 도로명 일련번호 -->
<!--0-->	<input id="NADR1S" 	value="" 	type="hidden"><!-- 도로명 주소1 -->
<!--0-->	<input id="NADR3S" 	value="" 	type="hidden"><!-- 도로명 주소2 -->
<!--0-->	<input id="NNMX" 	value="" 	type="hidden"><!-- 도로명 주소1 -->
<!--0-->	<input id="NNMY" 	value="" 	type="hidden"><!-- 도로명 주소2-->
<!--0-->	<input id="NADREJ" 	value="" 	type="hidden"><!-- 참조항목 (법정동명, 공동주택) -->
<!--0-->	<input id="NADREH" 	value="" 	type="hidden"><!-- 참조항목 (우편번호동, 건물명) -->
	
</form>

<div class="popup">
<form name="dataForm" method="post">
	
	<!-- -------------------------------------------------------- -->
	<!--	title	-->
	<!-- -------------------------------------------------------- -->
	<div id="p_title1">
		<h1>우편번호 찾기</h1>
        <span class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/cc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
	</div><br/>
	<!-- ------------------------------------------- end of title -->
	
	<!-- -------------------------------------------------------- -->
	<!--	Tab (지번주소 or 도로명주소)	-->
	<!-- -------------------------------------------------------- -->
	<div class="tab2">
		<ul>
			<li id="j_tab" onClick="changeTab('j');">지번주소</li>
			<li id="n_tab" onClick="changeTab('n');">도로명주소</li>
		</ul>
	</div>
	<!-- --------------------------------------------- end of Tab -->	
	
	<!-- ------------------------------------------ -->
	<!-- 지번주소 -->
	<!-- ------------------------------------------ -->
	<div id="j_address" class="popup_contents" style="line-height: 8px" >
	
	<!-- ------------------------------------------ -->
	<!-- JIBUN page1 -->
	<!-- ------------------------------------------ -->
		<!-- ------------------------------------------ -->
		<!-- JIBUN STEP1 -->
		<!-- ------------------------------------------ -->
		<div id="j_step1" class="addr_step">
			<br></br>
			<div class="bbs_search2">
				<ul class="tit">
					<li class="tit">STEP 01</li>
					<li class="tit" style="font-weight:lighter;font-size: 11px">찾고자 하는 지역의 읍/면/동을 입력 후 [검색] 버튼을 눌러주세요.</li>
				</ul>
			</div>
			<div class="bbs_search2">									
				<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
				<colgroup>
					<col width="10%"/>
					<col width="50%"/>		
				</colgroup>
				<tr>
					<th><span class="star">*</span>읍/면/동</th>
					<td>
						<input type="text" id="dongNm" name="dongNm" value="" class="day" style="width:40%; IME-MODE: active" onKeypress="if(event.keyCode ==13){doSearch('j');return;}"/>
						<a href="#" class="btn" onclick="doSearch('j')"><span>검색</span></a>
					</td>
				</tr>
				</table>
			</div>
			<br></br>
		</div>
		<!-- ------------------------end of JIBUN STEP1 -->
		
		<!-- ------------------------------------------ -->
		<!-- JIBUN STEP2 -->
		<!-- ------------------------------------------ -->
		<div id="j_step2" class="addr_step">
			<div class="bbs_search2">
				<ul class="tit">
					<li class="tit">STEP 02</li>
					<li class="tit" style="font-weight:lighter;font-size: 11px">해당하는 주소를 선택해주세요.</li>
				</ul>
			</div>
			<!--	list 	-->
			<div class="wrap_con">
				<!-- list -->
				<div class="bbs_search2">
					<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
							<td><div id="ibsheet1"></div></td>
						</tr>
					</table>
				</div>
			</div>	
			<br></br>
		</div>
		<!-- ------------------------end of JIBUN STEP2 -->
		
		<!-- ------------------------------------------ -->
		<!-- JIBUN STEP3 -->
		<!-- ------------------------------------------ -->
		<div id="j_step3" class="addr_step">
			<div class="bbs_search2">
				<ul class="tit">
					<li class="tit">STEP 03</li>
					<li class="tit" style="font-weight:lighter;font-size: 11px">상세주소를 입력하신 후 [다음] 버튼을 눌러주세요.</li>
				</ul>
			</div>
			<div class="bbs_search2">									
				<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
				<colgroup>
					<col width="10%"/>
					<col width="50%"/>		
				</colgroup>
				<tr>
					<th> 지번주소</th>
					<td>
						<input type="hidden"id="j_input_zipSeq" name="j_input_zipSeq" value="" />
						<input type="text" 	id="j_input_zipCd" 	name="j_input_zipCd" class="day" style="width:20%;" readonly="readonly" />
						<input type="text" 	id="j_input_addr1" 	name="j_input_addr1" class="day" style="width:70%;" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<th> 상세주소</th>
					<td>
						<input type="text" id="j_input_addr2" name="j_input_addr2" class="day" style="width:90%;" onKeypress="if(event.keyCode ==13){next('j');return;}" />
					</td>
				</tr>
				</table>
			</div>
			<div style="border:none;" class="bbs_search2">
				<li style="float:right;margin-top: 8px" class="btn">
					<a href="#" class="btn" onclick="init('j')"><span>&nbsp;초기화&nbsp;</span></a>
					<a href="#" class="btn" onclick="next('j')"><span>&nbsp;&nbsp;다음&nbsp;&nbsp;</span></a>
				</li>
			</div>
		</div>
		<!-- ------------------------end of JIBUN STEP3 -->
	<!-- ------------------------end of JIBUN page1 -->
		
	<!-- ------------------------------------------ -->
	<!-- JIBUN page2 -->
	<!-- ------------------------------------------ -->
		<!-- ------------------------------------------ -->
		<!-- JIBUN STEP4 -->
		<!-- ------------------------------------------ -->
		<div id="j_step4" class="addr_step">
			<br></br>
			<div class="bbs_search2">
				<ul class="tit">
					<li class="tit">STEP 04</li>
					<li class="tit" style="font-weight:lighter;font-size: 11px">사용하실 주소를 선택하신 후 [확인] 버튼을 눌러주세요.</li>
				</ul>
			</div>
			<fieldset class="add_select">
				<!-- 입력하신 주소 -->
				<div id="j_addr1_div">
					<p class="tit">
						<input type="radio" class="radio" name="j_select_addr" id="j_select_addr1" value="01"></input>
						입력하신 주소
					</p>
					<p id="j_addr1" class="add_box01"></p>
				</div>
				<!-- 정제된 지번주소 -->
				<div id="j_addr2_div">
					<p class="tit">
						<input type="radio" class="radio" name="j_select_addr" id="j_select_addr2" value="02"></input>
						정제된 지번주소
					</p>
					<p id="j_addr2" class="add_box01"></p>
				</div>
				<!-- 도로명주소 -->
				<div id="j_addr3_div">
					<p class="tit">
						<input type="radio" class="radio" name="j_select_addr" id="j_select_addr3" value="03"></input>
						도로명주소
					</p>
					<p id="j_addr3" class="add_box01"></p>
				</div>
				<!-- 변환실패문구 -->
				<div id="j_addr_failed">
					<p class="tit">정제된 지번주소 / 도로명주소</p>
					<p class="add_box02">
						도로명주소를 찾을 수 없습니다.<br></br>
						주소를 수정하시거나, 입력하신 주소를 선택해주세요.
					</p>
				</div>
			</fieldset>
			<!-- 버튼영역 -->
			<div style="border:none;" class="bbs_search2">
				<li style="float:right;margin-top: 8px" class="btn">
					<a href="#" class="btn" onclick="back('j')"><span>&nbsp;&nbsp;이전&nbsp;&nbsp;</span></a>
					<a href="#" class="btn" onclick="btnConfirm('j')"><span>&nbsp;&nbsp;확인&nbsp;&nbsp;</span></a>
				</li>
			</div>
		</div>
		<!-- ------------------------end of JIBUN STEP4 -->
	<!-- ------------------------end of JIBUN page2 -->		
	</div>
	<!-- -----------------------------end of 지번주소 -->
	
	<!-- ------------------------------------------ -->
	<!-- 도로명주소 -->
	<!-- ------------------------------------------ -->
	<div id="n_address" class="popup_contents" style="line-height: 8px" >
	
	<!-- ------------------------------------------ -->
	<!-- ROAD page1 -->
	<!-- ------------------------------------------ -->
		<!-- ------------------------------------------ -->
		<!-- ROAD STEP1 -->
		<!-- ------------------------------------------ -->
		<div id="n_step1" class="addr_step">
			<br></br>
			<div class="bbs_search2">
				<ul class="tit">
					<li class="tit">STEP 01</li>
					<li class="tit" style="font-weight:lighter;font-size: 11px">찾고자 하는 지역의 읍/면/동을 입력 후 [검색] 버튼을 눌러주세요.</li>
				</ul>
			</div>
			<div class="bbs_search2">									
				<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
				<colgroup>
					<col width="16%"/>
					<col width="27%"/>		
					<col width="16%"/>
					<col width="27%"/>
					<col width="24%"/>		
				</colgroup>
				<tr>
					<th><span class="star">*</span>시/도</th>
					<td colspan="3">
						<select id="sido" name="sido" class="select" onchange="javascript:onChangeSido();" style="display:block;">
							<option value="">선택해 주세요.</option>
							<option value="서울">서울특별시</option>
							<option value="부산">부산광역시</option>
							<option value="대구">대구광역시</option>
							<option value="광주">광주광역시</option>
							<option value="인천">인천광역시</option>
							<option value="대전">대전광역시</option>
							<option value="울산">울산광역시</option>
							<option value="세종">세종자치시</option>
							<option value="경기">경기도</option>
							<option value="강원">강원도</option>
							<option value="충남">충청남도</option>
							<option value="충북">충청북도</option>
							<option value="경남">경상남도</option>
							<option value="경북">경상북도</option>
							<option value="전남">전라남도</option>
							<option value="전북">전라북도</option>
							<option value="제주">제주특별자치도</option>
						</select>
					</td>
					<td></td>
				</tr>
				<tr>
					<th><span class="star">*</span>시/군/구</th>
					<td colspan="3">
						<select id="gungu" name="gungu" class="select">
							<option value="">선택해 주세요.</option>
						</select>
					</td>
					<td></td>
				</tr>
				<tr>
					<th><span class="star">*</span>도로명</th>
					<td colspan="3">
						<input type="text" id="road" name="road" value="" class="day" style="width:96%;" onKeypress="if(event.keyCode ==13){doSearch('n');return;}"/>
					</td>
					<td></td>
				</tr>
				<tr>
					<th>건물번호</th>
					<td>
						<input type="text" id="buildNum" name="buildNum" value="" class="day" style="width:90%;" onKeypress="if(event.keyCode ==13){doSearch('n');return;}"/>
					</td>
					<th>건물명</th>
					<td>
						<input type="text" id="buildName" name="buildName" value="" class="day" style="width:90%;" onKeypress="if(event.keyCode ==13){doSearch('n');return;}"/>
					</td>
					<td>
						<a href="#" class="btn" onclick="doSearch('n')"><span>검색</span></a>
					</td>
				</tr>
				</table>
			</div>
			<br></br>
		</div>
		<!-- -------------------------end of ROAD STEP1 -->
		
		<!-- ------------------------------------------ -->
		<!-- ROAD STEP2 -->
		<!-- ------------------------------------------ -->
		<div id="n_step2" class="addr_step">
			<div class="bbs_search2">
				<ul class="tit">
					<li class="tit">STEP 02</li>
					<li class="tit" style="font-weight:lighter;font-size: 11px">해당하는 주소를 선택해주세요.</li>
				</ul>
			</div>
			<!--	list 	-->
			<div class="wrap_con">
				<!-- list -->
				<div class="bbs_search2">
					<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
							<td><div id="ibsheet2"></div></td>
						</tr>
					</table>
				</div>
			</div>	
			<br></br>
		</div>
		<!-- -------------------------end of ROAD STEP2 -->
		
		<!-- ------------------------------------------ -->
		<!-- ROAD STEP3 -->
		<!-- ------------------------------------------ -->
		<div id="n_step3" class="addr_step">
			<div class="bbs_search2">
				<ul class="tit">
					<li class="tit">STEP 03</li>
					<li class="tit" style="font-weight:lighter;font-size: 11px">상세주소를 입력하신 후 [다음] 버튼을 눌러주세요.</li>
				</ul>
			</div>
			<div class="bbs_search2">									
				<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
				<colgroup>
					<col width="10%"/>
					<col width="50%"/>		
				</colgroup>
				<tr>
					<th> 도로명주소</th>
					<td>
						<input type="hidden"id="n_input_zipSeq" name="n_input_zipSeq" value="" />
						<input type="text"  id="n_input_zipCd" 	name="n_input_zipCd" class="day" style="width:20%;" readonly="readonly" />
						<input type="text"  id="n_input_addr1" 	name="n_input_addr1" class="day" style="width:70%;" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<th> 상세주소</th>
					<td>
						<input type="text" id="n_input_addr2" name="n_input_addr2" class="day" style="width:90%;" onKeypress="if(event.keyCode ==13){next('n');return;}" />
					</td>
				</tr>
				</table>
			</div>
			<div style="border:none;" class="bbs_search2">
				<li style="float:right;margin-top: 8px" class="btn">
					<a href="#" class="btn" onclick="init('n')"><span>&nbsp;초기화&nbsp;</span></a>
					<a href="#" class="btn" onclick="next('n')"><span>&nbsp;&nbsp;다음&nbsp;&nbsp;</span></a>
				</li>
			</div>
		</div>
		<!-- -------------------------end of ROAD STEP3 -->
	<!-- -------------------------end of ROAD page1 -->
		
	<!-- ------------------------------------------ -->
	<!-- ROAD page2 -->
	<!-- ------------------------------------------ -->
		<!-- ------------------------------------------ -->
		<!-- ROAD STEP4 -->
		<!-- ------------------------------------------ -->
		<div id="n_step4" class="addr_step">
			<br></br>
			<div class="bbs_search2">
				<ul class="tit">
					<li class="tit">STEP 04</li>
					<li class="tit" style="font-weight:lighter;font-size: 11px">사용하실 주소를 선택하신 후 [확인] 버튼을 눌러주세요.</li>
				</ul>
			</div>
			<fieldset class="add_select">
				<!-- 입력하신 주소 -->
				<div id="n_addr1_div">
					<p class="tit">
						<input type="radio" class="radio" name="n_select_addr" id="n_select_addr1" value="01"></input>
						입력하신 주소
					</p>
					<p id="n_addr1" class="add_box01"></p>
				</div>
				<!-- 정제된 지번주소 -->
				<div id="n_addr2_div">
					<p class="tit">
						<input type="radio" class="radio" name="n_select_addr" id="n_select_addr2" value="02"></input>
						정제된 지번주소
					</p>
					<p id="n_addr2" class="add_box01"></p>
				</div>
				<!-- 도로명주소 -->
				<div id="n_addr3_div">
					<p class="tit">
						<input type="radio" class="radio" name="n_select_addr" id="n_select_addr3" value="03"></input>
						도로명주소
					</p>
					<p id="n_addr3" class="add_box01"></p>
				</div>
				<!-- 변환실패문구 -->
				<div id="n_addr_failed">
					<p class="tit">정제된 지번주소 / 도로명주소</p>
					<p id="n_addr3" class="add_box02">
						도로명주소를 찾을 수 없습니다.<br></br>
						주소를 수정하시거나, 입력하신 주소를 선택해주세요.
					</p>
				</div>
			</fieldset>
			<!-- 버튼영역 -->
			<div style="border:none;" class="bbs_search2">
				<li style="float:right;margin-top: 8px" class="btn">
					<a href="#" class="btn" onclick="back('n')"><span>&nbsp;&nbsp;이전&nbsp;&nbsp;</span></a>
					<a href="#" class="btn" onclick="btnConfirm('n')"><span>&nbsp;&nbsp;확인&nbsp;&nbsp;</span></a>
				</li>
			</div>
		</div>
		<!-- -------------------------end of ROAD STEP4 -->
	<!-- -------------------------end of ROAD page2 -->		
	</div>
	<!-- -----------------------------end of 도로명주소 -->
	
</form>
</div>

</body>
</html>