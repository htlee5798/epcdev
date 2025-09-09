<%--
- Author(s): jhlee
- Created Date: 2016. 04. 21
- Version : 1.0
- Description : 점포 공통 팝업

--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jstl/core_rt"         %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions"   %>
<%@ taglib prefix="lfn"    uri="/WEB-INF/tlds/function.tld"               %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui"     uri="http://lcnjf.lcn.co.kr/taglib/paging"     %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"      %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt"         %>
<%@ taglib prefix="html" uri="http://lcnjf.lcn.co.kr/taglib/edi"  %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<%@ include file="/common/scm/scmCommon.jsp" %>
<link type="text/css" rel="stylesheet" href="/css/epc/new_style.css" ></link>
<!-- board/PSCMPRD0010 -->

<body>
	<form name="bosform" id="bosform" method="post">
		<input type="hidden" name="mode" id="mode" value="<c:out value="${ mode }"/>"/>
		<div class="pop_box_01">
			<h2>점포검색<span class="pop_close"><a href="javascript:window.close();"><img src="/images/bos/new/pop_close.png" alt="close" /></a></span></h2>
			<div class="p_navi">
			    <ul>
			      <li><a href="#"><img src="/images/bos/new/icon_home.png" alt="home" /></a> > 상품 > 상품관리 &gt; 점포선택</li>
			    </ul>
			</div>
			
			<ul>
			<h3>조회조건
				<span class="p_btn"><span class="btnBG1"><a href="javascript:;" class="btn" id="selection"><spring:message code="button.common.selecter"/></a></span></span>
			</h3>
				
			<li>
				<table width="100%" border="0" cellpadding="0" cellspacing="1" class="tbl_02" summary="신상품코드검증" >
			        <col width="13%" />
			        <col width="40%" />
			        <col width="13%" />
			        <col width=" " />
			        <tr>
			          <th colspan="4" scope="col" class="ar_left">
			          	<label>
			              <input type="radio" name="JumpoType" value="A" class="choice" title="전체"/>
			              점포지역별 찾기</label>
			            &nbsp; &nbsp;  &nbsp;
			            <label>
			              <input type="radio" name="JumpoType" value="B" class="choice" title="Y"/>
			              전점에서 찾기 </label>
			            &nbsp; &nbsp;  &nbsp;
			            <select name="regnCd" id="regnCd" onchange="regnCdChange()"style="width:120px" title="지역별 찾기" >
							<c:forEach items="${ str04CodeList }" var="str04Code">
								<option value="${ str04Code.MINOR_CD }"><c:out value="${ str04Code.CD_NM }"/></option>
							</c:forEach>
						</select>
			            &nbsp; &nbsp;  &nbsp;
			            
			             <select name="jumpoSearType" id="jumpoSearType" style="width:120px" title="검색조건" >
							<option value="01">점포코드</option>
							<option value="02">점포명</option>
						</select>
			            &nbsp; &nbsp;  &nbsp;
			            
			            <label>
			              <input type="text" name="jumpoSearValue" id="jumpoSearValue"  style="width:120px" title="점포코드"/>
						  <input style="VISIBILITY: hidden; WIDTH: 0px">
			            </label>
			            <span><span class="btnBG1" id="txtfind"><a href="javascript:;"><spring:message code="button.common.finder"/></a></span></span> </th>
			        </tr>
			        
			        <tr>
			          <th><label for="allChk">지역전체</label>
			          <input type="checkbox" name="allChk" id="allChk" class="a_search" /></th>
			          <td colspan="3">
				          <table width="99.5%" border="0" cellspacing="1" cellpadding="0" class="tbl_05" id="showBox" >
				            <colgroup>
					            <col width="3%" />
					            <col width="17%" />
					            <col width="3%" />
					            <col width="17%" />
					            <col width="3%" />
					            <col width="17%" />
					            <col width="3%" />
					            <col width="17%" />
					            <col width="3%" />
					            <col width=" " />
				            </colgroup>
				          </table>
			          </td>
			        </tr>
			  </table>
			</li>
		  </ul>
		</div>
	</form>
	<script type="text/javascript">
		var selectedIdArr = new Array();
		
		$(document).ready(function(){
			var selectedIdValue = "";
			if( "<c:out value="${id}"/>" != "" ){ // ID가 존재할 경우
				selectedIdValue = $("#<c:out value="${id}"/>", opener.document).val();
			}
			selectedIdArr = selectedIdValue.split(",");
			
			
			// 선택버튼 클릭
		    $('#selection').click(function() {
		    	strCdArray = new Array();
		    	strNmArray = new Array();
		    	strInfo = new Object();
		    	
		    	// 점포가 있는 만큼 반복문 실행
		    	$("input[name='jumpo']").each(function(i){
		    		// 각 엘리먼트가 체크인지 확인
		    		if( $("input[name='jumpo']").eq(i).is(":checked") == true ){
		    			strCdArray.push( $("input[name='jumpo']").eq(i).val() );
						strNmArray.push( $("label[for^='showInputBox_']").eq(i).text() );
		    		}
		    	});
		    	
		    	if ( $("#allChk").attr("disabled") ){ // 선택안됨
		    		if( "<c:out value="${ mode }"/>" == "S" ){
			    		strInfo.strCdArr = strCdArray[0];
			    		strInfo.strNmArr = strNmArray[0];
		    		}else{
				    	strInfo.strCdArr = strCdArray;
				    	strInfo.strNmArr = strNmArray;
		    		}
		    	}else{
		    		if( $("#allChk").is(":checked") == true && $('input[name="JumpoType"]:checked').val() == "B" ){
		    			if('${param.isUnusedAllStoreCode}' == 'true') {
		    				strInfo.strCdArr = strCdArray;
					    	strInfo.strNmArr = strNmArray;
					    	
					    	strInfo.isAllStore = true;
		    			} else {
		    				strCdArray1 = new Array();
		    				strCdArray1.push("999");
		    				
				        	strNmArray1 = new Array();
				        	strNmArray1.push("전점");
				        	
				    		strInfo.strCdArr = strCdArray1;
				    		strInfo.strNmArr = strNmArray1;	
		    			}
			    	}else if( $("#allChk").is(":checked") == true && $('input[name="JumpoType"]:checked').val() == "A" ){
			    		strInfo.strCdArr = strCdArray;
				    	strInfo.strNmArr = strNmArray;
			    	}else if( "<c:out value="${ mode }"/>" == "S" && $("#allChk").is(":checked") == false ){
			    		strInfo.strCdArr = strCdArray[0];
			    		strInfo.strNmArr = strNmArray[0];
			    	}else{
				    	strInfo.strCdArr = strCdArray;
				    	strInfo.strNmArr = strNmArray;
			    	}
		    	}
		    	
				opener.fnSetStr(strInfo);
				self.close();
		    }); 
			
			// 닫기버튼 클릭
		    $('#close').click(function() {
				self.close();
		    });
			
			// 찾기기능을 하기 위해서 선언 된 함수
			$(document).on("click","#txtfind",function() {
				var jumpoSearType = $("#jumpoSearType").val();
				var jumpoSearValue = $("#jumpoSearValue").val();
				var resultFind = "N";
				// 생성된 input에 class 가 showInputBox 선언된 값만 반복문 실행
				$(".showInputBox").each(function(i){
					if( jumpoSearType == '01' ){ // 점포코드 (안보임)
						if( $(".showInputBox").eq(i).val() == jumpoSearValue ){
							$(".showInputBox").eq(i).focus();
							resultFind = "Y";
							if(confirm("입력하신 점포코드 "+jumpoSearValue+"을 찾았습니다. 선택하시겠습니까?")){
								$(".showInputBox").eq(i).attr("checked",true);
								$("#jumpoSearValue").val("");
								$("#jumpoSearValue").focus();
							}
							return false;
						}else{
							resultFind = "N";
						}
					}else if( jumpoSearType == '02' ){ // 점포명 (보이는 명칭) 
						if( $("label[for='"+ $(".showInputBox").eq(i).attr("id") +"']").text() == jumpoSearValue ){
							$("label[for='"+ $(".showInputBox").eq(i).attr("id") +"']").focus();
							resultFind = "Y";
							if(confirm("입력하신 점포명 "+jumpoSearValue+"을 찾았습니다. 선택하시겠습니까?")){
								$(".showInputBox").eq(i).attr("checked",true);
								$("#jumpoSearValue").val("");
								$("#jumpoSearValue").focus();
								
							}
							return false;
						}else{
							resultFind = "N";
						}
					}
				});
				if( resultFind == 'N' ){
					if( jumpoSearType == '01' ){
						alert("입력하신 점포코드 "+jumpoSearValue+" 는 찾을수 없습니다.");
					}else{
						alert("입력하신 점포명 "+jumpoSearValue+" 는 찾을수 없습니다.");
					}
				}
			});
		
			// 화면 셋팅
			if( selectedIdArr.length > 0 ){
				$("input[name='JumpoType']:eq(1)").attr("checked",true);
				$('select[name="regnCd"]').attr("disabled",true);
				$("#allChk").attr("checked",false);
				if( $('input[name="JumpoType"]:checked').val() == "A" ){
					$("#regnCd").removeAttr('disabled');
					$("label[for='allChk']").text( "지역전체" );
					
				}else{
					$("#regnCd").attr('disabled', 'true');
					$("label[for='allChk']").text( "전점" );
				}
				if( "<c:out value="${ mode }"/>" == "S" ){
					if( $('input[name="JumpoType"]:checked').val() == "A" ){
						$("#allChk").attr('disabled', 'true');
					}else{
						$("#allChk").removeAttr('disabled');
					}
				}
			}else{
				$("input[name='JumpoType']:eq(0)").attr("checked",true);
			}
			
			if( "<c:out value="${ mode }"/>" == "S" ){
				if( $('input[name="JumpoType"]:checked').val() == "A" ){
					$("#allChk").attr('disabled', 'true');
				}else{
					$("#allChk").removeAttr('disabled');
				}
			}
			
			// 점포 구분값이 변경시 처리
			$(document).on("change","input[name='JumpoType']",function() {
				$("#allChk").attr("checked",false);
				if( $('input[name="JumpoType"]:checked').val() == "A" ){
					$("#regnCd").removeAttr('disabled');
					$("label[for='allChk']").text( "지역전체" );
					
				}else{
					$("#regnCd").attr('disabled', 'true');
					$("label[for='allChk']").text( "전점" );
				}
				if( "<c:out value="${ mode }"/>" == "S" ){
					if( $('input[name="JumpoType"]:checked').val() == "A" ){
						$("#allChk").attr('disabled', 'true');
					}else{
						$("#allChk").removeAttr('disabled');
					}
				}
				setMenu();
			});
			
			// 전체선택을 클릭시 처리
			$(document).on("click","#allChk",function() {
				if( $("#allChk").is(":checked") ){
					if( "<c:out value="${ mode }"/>" != "S"){
						$(".showInputBox").attr("checked",true);
					}else{
						$(".showInputBox").attr('disabled', 'true');
						$(".showInputBox").attr("checked",false);
					}
				}else{
					if( "<c:out value="${ mode }"/>" != "S"){
						$(".showInputBox").attr("checked",false);
					}else{
						$(".showInputBox").removeAttr('disabled');
						$(".showInputBox").attr("checked",false);
					}
				}
			});
			 
			// 검색 값에 엔터를 누를경우에 처리
			$("#jumpoSearValue").keypress(function(e) {
		        if (e.keyCode==13){
		        	if( $("#jumpoSearValue").val().length > 0 ){
		        		$("#txtfind").click();
		        	}else{
		        		alert("찾을 조건을 입력하세요");
		        	}
		       	}
			});	
			 
			$(document).on("click","input[name='jumpo']",function(){
				if( Number( $("input[name='jumpo']").length ) == Number( $("input[name='jumpo']:checked").length ) ){
					$("input[name='allChk']").prop("checked",true);
				}else{
					$("input[name='allChk']").prop("checked",false);
				}
				
			});
			
			setMenu();
			
		});
		
		// 점포 코드, 코드명 변경시 작동
		function regnCdChange(){
			setMenu();
		}
		
		// 점포에 대한 정보를 불러온다.
		function setMenu(){
			
			var Data = "";
			if( $('input[name="JumpoType"]:checked').val() == "A" ){
				Data = { "regnCd": $("#regnCd option:selected").val() };
			}else{
			    Data = {  };
			}
			$.ajax({
				type: "POST",
				url: '<c:url value="/product/PSCMPRD0060CodeList.do"/>',
				data : Data,
				datatype : "json",
				success: function(json) {
					try {
						
						if(jQuery.trim(json) == "accessAlertFail"){//권한없음	
							alert('<spring:message code="msg.common.error.noAuth"/>');
						}else{
							 creatingInputBox(json.Result.data);
						}
		
					} catch (e) {}
				},
				error: function(e) {
					alert("저장에 실패하였습니다");
				}
			});
		}
		
		// 점포를 생성시 colgroup 이 소멸되지 않도록 화면에 colgroup 을 저장함
		var colgroups = "";
		
		// 점포 화면을 그린다.
		function creatingInputBox(data){
			inputType = "";
			if( "<c:out value="${ mode }"/>" == "M" ){
				inputType = "checkbox";
			}else{
				inputType = "radio";
			}
			if( colgroups == "" ){
				colgroups = $("#showBox").html();
			}
			$("#showBox").children().remove();
			htmls = colgroups;
			for(var i=0; i<data.length;i++){
				if(i == 0){
					htmls += "<tr>";
				}
				
					htmls += "<th><input type='"+inputType+"' name='jumpo' value='"+data[i].STR_CD+"' class='showInputBox validcheck' id='showInputBox_"+i+"'/></th><td><label for='showInputBox_"+i+"'>"+data[i].STR_NM+"</label></td>";
				
				if(i != 0 && (i+1)%5 == 0 ){
					htmls += "</tr><tr>";
				}
				if(i+1 == data.length){
					for(var j = (i+1)%5 ; j < 5 ; j++){
					htmls += "<th></th><td></td>";
					}
					htmls += "</tr>";
				}
				
			}
			
			$("#showBox").append(htmls);
			
			for( var i = 0 ; i < selectedIdArr.length; i++){
				if( selectedIdArr[i] == "999" ){
					if( "<c:out value="${ mode }"/>" != "S"){
						$(".showInputBox").attr("checked",true);
					}else{
						$(".showInputBox").attr('disabled', 'true');
						$(".showInputBox").attr("checked",false);
					}
				} else {
					$("input[name='jumpo']").filter(function(){ return this.value == selectedIdArr[i].trim() }).attr("checked", "checked");
				}
				
				if(i == selectedIdArr.length - 1 && $('[id^=showInputBox_]').length == $('input[name=jumpo]:checked').length){
					$("#allChk").attr("checked","checked");
				};
			}
		}
	</script>
	</body>
</html>