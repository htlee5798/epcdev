<%--
	Page Name 	: CommonMgrCode.jsp
	Description : 코드관리 
	Modification Information
	
	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2025.04.01		js
--%>
<%@ include file="../common.jsp" %>
<%@ include file="/common/scm/scmCommon.jsp" %>
<%@ include file="./CommonProductFunction.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>
<style type="text/css">
.img {
	height: 14px;
}	

#content_wrap{
	width: 99%!important;
}

</style>
	<script type="text/javascript" >
		/* dom이 생성되면 ready method 실행 */
		$(document).ready(function() {
			showEditView("S");  // 서브코드 등록화면 활성화
			$("#saveUpdateFg").val("S");
			toggleRequiredMarkers(true);
			codeMasterList();

			/* BUTTON CLICK 이벤트 처리 ----------------------------------------------------------------------------*/
			$('#btnSearch').unbind().click(null,		codeMasterList()); 			// 검색버튼
			/*-----------------------------------------------------------------------------------------------------*/


			//코드명칭 조회조건 입력필드 enter key이벤트 --------------
			$('#searchNm').unbind().keydown(function(e) {
				switch(e.which) {
					case 13 : codeMasterList(this); break; // enter
					default : return true;
				}
				e.preventDefault();
			});
			//-----------------------------------------------------------------

			
		});
		
		//코드 저장 
		function eventSubCodeSave() {
			var comCd 	= "";	//제조회용 key
			var useYn = "";	//사용여부 Y/N
			var codeInfo = {};
			$('#subForm').find('input, select').map(function() {

				if(this.name =='subUseYn'){
					//사용여부 RADIO 값설정
					if(!useYn) {
						if( $(this)[0].checked) 	useYn = "Y";
						else useYn = "N";
						codeInfo[this.name] = useYn;
					}
				}
				else codeInfo[this.name] = $(this).val();

				if(this.name == "mstComCd"){
				 	comCd = $(this).val();
				}
			});
			
			
			//=============신규 버튼 필수값 검증 =============
			var saveUpdateFg = $("#saveUpdateFg").val();
			
			//S는 전체 저장 D는 업데이트(세부코드 안적혀 있으면 분류코드만 업데이트) 
			if(saveUpdateFg == "S"){
				var mstComCd = $("#mstComCd").val();
				var mstComNm = $("#mstComNm").val();
				
				
				var subDtlCd = $("#subDtlCd").val();
				var	subDtlNm = $("#subDtlNm").val();
				
				if(mstComCd == null || mstComCd == ""){
					alert("분류코드를 입력해주세요.");
					return false;
				}
				
				if(mstComNm == null || mstComNm == ""){
					alert("분류코드명을 입력해주세요.");
					return false;
				}
				
				if(subDtlCd == null || subDtlCd == ""){
					alert("세부코드를 입력해주세요.");
					return false;
				}
				
				if(subDtlNm == null || subDtlNm == ""){
					alert("세부코드명을 입력해주세요.");
					return false;
				}	
			}else{
				var mstComCd = $("#mstComCd").val();
				var mstComNm = $("#mstComNm").val();
			
				if(mstComCd == null || mstComCd == ""){
					alert("분류코드를 입력해주세요.");
					return false;
				}
				
				if(mstComNm == null || mstComNm == ""){
					alert("분류코드명을 입력해주세요.");
					return false;
				}
			}
		
			//=======================================
			
			
			// 저장 이벤트 json 실행---------------------------
			$.ajax({
			      contentType : 'application/json; charset=utf-8', type : 'post', dataType : 'json',
			      url : '<c:url value="/mngr/edi/product/insertMgrDtlCode.json"/>',
			      data : JSON.stringify(codeInfo),
			      success : function(data){

			    	  	if(data == null) msg = '처리된 데이터가 없습니다.'; <%--"처리된 데이터가 없습니다.";--%>
			            else if('success' == data.msgCd)
			            {
			            	msg = '코드 저장이 정상적으로 처리되었습니다.'; 
			            	setTbodyInit("dataListbody2"); // dataList 초기화
			            	codeMasterList();
			            	searchSubCodeList(comCd);		// 세분류코드 제조회
			            	setClearSubCodeDetail();	// 세분류코드 등록화면 초기화
			            }
			            else if('duple' == data.msgCd) 	msg = '이미 등록된 코드 입니다.'; 
			            else msg = '코드 저장중 오류가 발생하였습니다.'; 

		          		alert(msg);
			      }
			});
			//-----------------------------------------------
		}
		
		//코드 삭제 
		function eventSubCodeDelete() {
			var form 		= document.subForm;
			var comCd 		= form.mstComCdOld.value;
			var dtlCdOld	= form.subDtlCdOld.value;
			
			if((comCd == null || comCd == "")  && (dtlCdOld == null || dtlCdOld == "")){
				alert("삭제하실 코드를 선택 해주세요.");
				return false;
			}
			
			
			// 저장확인 메세지 --------------------------------
			if (!confirm( '"코드 ["'+comCd+" | "+dtlCdOld+'"]  세부코드를 삭제 하시겠습니까?"')) {
		        return false;
			}
			//-----------------------------------------------
			
			var str = {'mstComCd'    : comCd, 		// 대분류코드
					   'subDtlCdOld' : dtlCdOld	// 세부  코드
			  		  };
			
			$.ajax({
			      contentType : 'application/json; charset=utf-8', type : 'post', dataType : 'json',
			      url : '<c:url value="/mngr/edi/product/deleteMgrDtlCode.json"/>',
			      data : JSON.stringify(str),
			      success : function(data){
			    	var msg = "";
			    	 if(data == null) msg = '처리된 데이터가 없습니다." />';<%-- "처리된 데이터가 없습니다.";--%>
		            else if('success' == data.msgCd)
		            {
		            	msg = '세부코드 삭제가 정상적으로 처리되었습니다.';<%--"세부코드 삭제가 정상적으로 처리되었습니다.";--%>
		            	codeMasterList();
		            	searchSubCodeList(comCd); // 세부코드 제조회
		            	setClearSubCodeDetail();	 // 세부코드 등록화면 초기화
		            }
		            else msg = '세부코드 삭제 중 오류가 발생하였습니다.';<%--"세부코드 삭제 중 오류가 발생하였습니다.";--%>

		          	alert(msg);
			      }
			});
		}
		
		//세부코드 재조회 
		function searchSubCodeList(comCd){

		    var paramInfo = { majorCd: comCd };

		    $.ajax({
		        contentType: 'application/json; charset=utf-8',
		        type: 'post',
		        dataType: 'json',
		        async: false,
		        url: '<c:url value="/mngr/edi/product/selectMgrCodeDtlList.json"/>',
		        data: JSON.stringify(paramInfo),
		        success: function(data) {
		            $("#pageIndex2").val(pageIndex);
		            _eventSetTbodyValue2(data);
		        }
		    });
		}
		
		/*코드 신규등록 버튼 이벤트*/
		function eventSubCodeInit() {
			var setFrom = document.subForm;

			//분류코드 선택 후 신규 버튼 클릭 시 
			showEditView("S");			// 서브코드 등록화면 활성화
			
			 $("input[name='mstComCd']").attr("disabled", false);
			 $("input[name='mstComNm']").attr("disabled", false);
			 $("#saveUpdateFg").val("S");	// S는 신규 저장 D 는 엄데이트
			setClearSubCodeDetail(); 	// 서브코드 화면 초기화
			toggleRequiredMarkers(true);
		}
		
		
		/*코드 등록 화면초기화*/
		function setClearSubCodeDetail() {

			$("#subForm").find("label").filter(".error").remove();

			$('#modDt').text('');			// 최종수정일자
			$('#modId').text('');		// 최종작업자

			//서브코드 정보----------------------------------
			$("#subForm").find("input[name='mstComCd']").val('');				// 마스터코드
			$("#subForm").find("input[name='mstComCdOld']").val('');				// 마스터코드
			$("#subForm").find("input[name='mstComNm']").val('');				// 마스터코드명
			$("#subForm").find("input[name='mstComNmOld']").val('');				// 마스터코드명
			$("#subForm").find("input[name='subDtlCd']").val('');				// 서브코드
			$("#subForm").find("input[name='subDtlCdOld']").val('');			// 서브코드이전값체크용
			$("#subForm").find("input[name='subDtlNm']").val('');				// 서브코드명
			$("#subForm").find("input[name='subDtlNmOld']").val('');			// 서브코드명이전값체크용
			
			$("#subForm").find("input[name='subExtent01']").val('');				// 마스터코드명
			$("#subForm").find("input[name='subExtent02']").val('');				// 마스터코드명
			$("#subForm").find("input[name='subExtent03']").val('');				// 마스터코드명
			$("#subForm").find("input[name='subExtent04']").val('');				// 마스터코드명
			
			$("#subForm").find("input[name='subExtentCnt01']").val('');				// 마스터코드명
			$("#subForm").find("input[name='subExtentCnt02']").val('');				// 마스터코드명
			$("#subForm").find("input[name='subExtentCnt03']").val('');				// 마스터코드명
			$("#subForm").find("input[name='subExtentCnt04']").val('');				// 마스터코드명
			
			
			$("#subForm").find("input[name='subSortNo']").val('');				// 소트순서
			$("#subForm").find("input[name='subUseYn']")[0].checked = true;	// 사용여부'Y설정'

		}
		
		
		//등록 영역 구분 처리
		function showEditView(type) {

			$("#masterRegister").hide();
			$("#subRegister").show();
			$("#subForm").find("label").filter(".error").remove();

		}
		
		/* CODE MASTER  조회 */
		function codeMasterList() {
			var cdNm   = "";
			var dtlNm   = "";
			
			/*검색버튼 : 이벤트일때만 검색조건으로 검색하도록 함  */
			/*검색조건 : M:분류명칭 검색, S:세부명칭 검색      */
				if($('#searchType')[0].checked) cdNm = $('#searchNm').val();
				else   dtlNm = $('#searchNm').val();
			/*-------------------------------------------*/

			  $("input[name='mstComCd']").attr("disabled", false);
			  $("input[name='mstComNm']").attr("disabled", false);
			
			
			var paramInfo = {};
			
			paramInfo["cdNm"] = cdNm;
			paramInfo["dtlNm"] = dtlNm;
			
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/mngr/edi/product/selectMgrCodeList.json"/>',
				data : JSON.stringify(paramInfo),
				success : function(data) {
					$("#pageIndex").val(pageIndex);
					//json 으로 호출된 결과값을 화면에 Setting
					_eventSetTbodyValue(data);
				}
			}); 
			
		}


		/* List Data 셋팅 */
		function _eventSetTbodyValue(json) {
		    setTbodyInit("dataListbody"); // dataList 초기화
			setTbodyInit("dataListbody2"); // dataList 초기화

		    var data = json.resultList;

		    if (data.length > 0) {
		        $("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
		    //    $("#paging-ul").html(json.contents);
		     }else {
		        setTbodyNoResult("dataListbody", 20);
		   //     $("#paging-ul").html("");
		    }
		}
		
		//마스터폼 데이터 세팅 
		function masterFormDataSetting(data) {
            $("#subForm").find("input[name='mstComCd']").val(data.majorCd);       // 서브코드
            $("#subForm").find("input[name='mstComCdOld']").val(data.majorCd);       // 서브코드
            $("#subForm").find("input[name='mstComNm']").val(data.cdDesc);       // 서브코드 명
            $("#subForm").find("input[name='mstComNmOld']").val(data.cdDesc);       // 서브코드 명
            $('#modDt').text(data.modDate);
            $('#modId').text(data.modId); 
		}
		
		//상세코드 조회 
		function _codeDtlSearch(row) {
			  $("input[name='mstComCd']").attr("disabled", false);
			  $("input[name='mstComNm']").attr("disabled", false);
		    
		    // 클릭한 행(tr)의 데이터를 가져오기
		    var rowData = {};

		    // td 값 가져오기
		    $(row).find("td").each(function(index) {
		        var key = ["rnum", "majorCd", "cdDesc"][index]; // td 순서에 맞게 키 설정
		        rowData[key] = $(this).text().trim();
		    });

		    // hidden input 값 가져오기 
		    $(row).find("input[type='hidden']").each(function() {
		        var key = $(this).attr("name"); // input name 속성을 key로 사용
		        rowData[key] = $(this).val();
		    });

		    setClearSubCodeDetail(); 	// 서브코드 화면 초기화
		    
		    // 마스터 폼에 값 채우기
		    masterFormDataSetting(rowData);
		    $("#saveUpdateFg").val("U");
		    toggleRequiredMarkers(false);

		    var paramInfo = { majorCd: rowData.majorCd };

		    $.ajax({
		        contentType: 'application/json; charset=utf-8',
		        type: 'post',
		        dataType: 'json',
		        async: false,
		        url: '<c:url value="/mngr/edi/product/selectMgrCodeDtlList.json"/>',
		        data: JSON.stringify(paramInfo),
		        success: function(data) {
		            $("#pageIndex2").val(pageIndex);
		            _eventSetTbodyValue2(data);
		        }
		    });
		}
		
		
		/* List Data 셋팅 */
		function _eventSetTbodyValue2(json) {
		    setTbodyInit("dataListbody2"); // dataList 초기화
		    
		    var data = json.resultList;

		    if (data.length > 0) {
		        $("#dataListTemplate2").tmpl(data).appendTo("#dataListbody2");
		    //    $("#paging-ul2").html(json.contents);
		     }else {
		        setTbodyNoResult("dataListbody2", 20);
		     //   $("#paging-ul2").html("");
		    }
		}
		
		//숫자만 입력 가능하게 
		function inputNumberFormat(obj){
			var value = obj.value;
			
			 // 숫자만 남기기
		    value = value.replace(/[^0-9]/g, "");
		}	
		
		//서브 코드 선택 
		function _selectDtlCode(row){
			  showEditView("S");  // 서브코드 등록화면 활성화
			  
			  $("input[name='mstComCd']").attr("disabled", true);
			  $("input[name='mstComNm']").attr("disabled", true);
			  
			  // 클릭한 행(tr)의 데이터를 가져오기
			    var rowData = {};

			    // td 값 가져오기
			    $(row).find("td").each(function(index) {
			        var key = ["rnum", "minorCd", "cdNm"][index]; // td 순서에 맞게 키 설정
			        rowData[key] = $(this).text().trim();
			    });

			    // hidden input 값 가져오기 
			    $(row).find("input[type='hidden']").each(function() {
			        var key = $(this).attr("name"); // input name 속성을 key로 사용
			        rowData[key] = $(this).val();
			    });
			  
			  
			  
			  // 마스터코드,명 정보----------------------------
              $("#subForm").find("input[name='majorCd']").val(rowData.majorCd);  			// 마스터코드
              $("#subForm").find("input[name='cdNm']").val(rowData.cdNm);  	// 마스터코드명
              //---------------------------------------------
              
               $('#modDt').text(rowData.modDate);         // 최종수정일자
               $('#modId').text(rowData.modId);            // 최종작업자

                //서브코드 정보----------------------------------
                $("#subForm").find("input[name='mstComCd']").val(rowData.majorCd);       // 서브코드의 마스터코드
                $("#subForm").find("input[name='mstComCdOld']").val(rowData.majorCd);       // 서브코드의 마스터코드
                $("#subForm").find("input[name='mstComNm']").val(rowData.cdDesc);       // 서브코드의 마스터코드명	
                $("#subForm").find("input[name='mstComNmOld']").val(rowData.cdDesc);       // 서브코드의 마스터코드명	
                $("#subForm").find("input[name='mstComNmOld']").val(rowData.cdDesc);       // 서브코드의 마스터코드명	
                
                $("#subForm").find("input[name='subDtlCd']").val(rowData.minorCd);       // 서브코드
                $("#subForm").find("input[name='subDtlCdOld']").val(rowData.minorCd);    // 서브코드이전값체크용
                $("#subForm").find("input[name='subDtlNm']").val(rowData.cdNm);       // 서브코드명
                $("#subForm").find("input[name='subDtlNmOld']").val(rowData.cdNm);       // 서브코드명이전값체크용
                
                $("#subForm").find("input[name='subExtent01']").val(rowData.subExtent01);       //  세부코드명 확장
                $("#subForm").find("input[name='subExtent02']").val(rowData.subExtent02);       //  세부코드명 확장
                $("#subForm").find("input[name='subExtent03']").val(rowData.subExtent03);       //  세부코드명 확장
                $("#subForm").find("input[name='subExtent04']").val(rowData.subExtent04);       //  세부코드명 확장
                
                $("#subForm").find("input[name='subExtentCnt01']").val(rowData.subExtentCnt01);       //  세부코드숫자 확장
                $("#subForm").find("input[name='subExtentCnt02']").val(rowData.subExtentCnt02);       //  세부코드숫자 확장
                $("#subForm").find("input[name='subExtentCnt03']").val(rowData.subExtentCnt03);       //  세부코드숫자 확장
                $("#subForm").find("input[name='subExtentCnt04']").val(rowData.subExtentCnt04);       //  세부코드숫자 확장
                
                $("#subForm").find("input[name='subSortNo']").val(rowData.orderSeq);     // 정렬순서
                

                //--사용여부 RADIO CHECKED 설정------------------
                if(rowData.useYn =="Y")
                    $("#subForm").find("input[name='subUseYn']")[0].checked = true;
                else $("#subForm").find("input[name='subUseYn']")[1].checked = true;
                //---------------------------------------------
                $("#saveUpdateFg").val("S");
                toggleRequiredMarkers(true);				
		}
		
		// * 표시 여부 제어 함수
	    function toggleRequiredMarkers(isVisible) {
	        $(".required").css("display", isVisible ? "inline" : "none");
	    }
		
	</script>
	
	
	<!-- DATA LIST -->
	<script id="dataListTemplate" type="text/x-jquery-tmpl">    
    <tr class="r1" bgcolor=ffffff  style="cursor:pointer" onClick="_codeDtlSearch(this)" >
		<input type ="hidden" name = "modDate"  id="modDate\${rnum}" value="<c:out value="\${modDate}"/>" />
		<input type ="hidden" name = "modId"  id="modId\${rnum}" value="<c:out value="\${modId}"/>" />
        <td align="center">
            <c:out value="\${rnum}"/>
        </td>
        <td align="center">
            <c:out value="\${majorCd}"/>
        </td>
        <td align="center">
            <c:out value="\${cdDesc}"/>
        </td>
    </tr>
	</script>
	
	
	<!-- DATA LIST -->
	<script id="dataListTemplate2" type="text/x-jquery-tmpl">	
		<tr class="r1" bgcolor=ffffff style="cursor:pointer"  onClick="_selectDtlCode(this) " >
			<input type ="hidden" name = "rnum"  id="rnum\${rnum}" value="<c:out value="\${rnum}"/>" />
			<input type ="hidden" name = "majorCd"  id="majorCd\${rnum}" value="<c:out value="\${majorCd}"/>" />
			<input type ="hidden" name = "cdDesc"  id="cdDesc\${rnum}" value="<c:out value="\${cdDesc}"/>" />
			<input type ="hidden" name = "subExtent01"  id="subExtent01\${rnum}" value="<c:out value="\${subExtent01}"/>" />
			<input type ="hidden" name = "subExtent02"  id="subExtent02\${rnum}" value="<c:out value="\${subExtent02}"/>" />
			<input type ="hidden" name = "subExtent03"  id="subExtent03\${rnum}" value="<c:out value="\${subExtent03}"/>" />
			<input type ="hidden" name = "subExtent04"  id="subExtent04\${rnum}" value="<c:out value="\${subExtent04}"/>" />
			<input type ="hidden" name = "subExtentCnt01"  id="subExtentCnt01\${rnum}" value="<c:out value="\${subExtentCnt01}"/>" />
			<input type ="hidden" name = "subExtentCnt02"  id="subExtentCnt02\${rnum}" value="<c:out value="\${subExtentCnt02}"/>" />
			<input type ="hidden" name = "subExtentCnt03"  id="subExtentCnt03\${rnum}" value="<c:out value="\${subExtentCnt03}"/>" />
			<input type ="hidden" name = "subExtentCnt04"  id="subExtentCnt04\${rnum}" value="<c:out value="\${subExtentCnt04}"/>" />
			<input type ="hidden" name = "useYn"  id="useYn\${rnum}" value="<c:out value="\${useYn}"/>" />
			<input type ="hidden" name = "orderSeq"  id="orderSeq\${rnum}" value="<c:out value="\${orderSeq}"/>" />
			<input type ="hidden" name = "modDate"  id="modDate\${rnum}" value="<c:out value="\${modDate}"/>" />
			<input type ="hidden" name = "modId"  id="modId\${rnum}" value="<c:out value="\${modId}"/>" />
			<td align="center">
				<c:out value="\${rnum}"/>
			</td>
			<td align="center">
				<c:out value="\${minorCd}"/>
			</td>
			<td align="center">
				<c:out value="\${cdNm}"/>
			</td>
		</tr>
	</script>
</head>
<body>
	<div id="content_wrap">
		<div>
				<div id="wrap_menu">
					<form id="codeInfo" name="codeInfo" method="post">
						<div class="wrap_search">
							<div class="bbs_search">
								<ul class="tit">
									<li class="tit">코드관리</li>
									<li class="btn">
										<a href="#" class="btn" onclick="eventSubCodeInit()"><span>신규</span></a>		
									</li>
								</ul>
								<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
									<colgroup>
											<col width="200">
											<col width="*">
									</colgroup>
									<tr>
										<th>분류/세부코드 명칭</th>
										<td>
											<input type="radio"  name="searchType" id="searchType" value="M" checked="checked" />분류 
											<input type="radio"  name="searchType" id="searchType" value="S" />세부
											&nbsp; 
											<input type="text"  id="searchNm"  style="width:200px;" name="searchNm">
											<a href="#" class="btn" onclick="codeMasterList()"><span>조회</span></a>
										</td>
									</tr>
								</table>
							</div>
						</div>
					</form>
					
					 <!-- CONTENT BODY start  ----------------->
					 <div>
	    				<table style="width: 100%">
	    					<colgroup>
									<col width="50%"/>
									<col width="5"/>
									<col width="50%"/>
							</colgroup>
							<tr>
					    		<td>
											<div class="wrap_con" style="width: 650px;">
												<input type="hidden" id="pageIndex" name="pageIndex" value="<c:out value="${param.pageIndex}" />" />
												<div class="bbs_list">
													<ul class="tit">
														<li class="tit">분류코드 List</li>
													</ul>
													<div style="width:650px; height:458px;overflow-x:hidden; overflow-y:scroll; overflow-x:scroll;  table-layout:fixed;white-space:nowrap;">
														<table id="dataTable1" cellpadding="1" cellspacing="1" border="0" width=635px; bgcolor=efefef>
															<colgroup>
																<col style="width:30px"/>
																<col style="width:100px"/>
																<col style="width:150px"/>
															</colgroup>
															<tr bgcolor="#e4e4e4">
																<th>순번</th>
																<th>코드</th>
																<th>분류명칭</th>
															</tr>
															<tbody id="dataListbody" />
														</table>
													</div>
												</div>
											</div>
					
								</td>
								<td></td>
								<td>
											<div class="wrap_con" style="width: 650px;">
												<input type="hidden" id="pageIndex2" name="pageIndex2" value="<c:out value="${param.pageIndex}" />" />
												<div class="bbs_list">
													<ul class="tit">
														<li class="tit">세부코드 List</li>
														<!-- <li class="btn">
															<a href="#" class="btn" onclick="eventSubCodeInit()"><span>신규</span></a>		
														</li> -->
													</ul>
													<div style="width:650px; height:458px;overflow-x:hidden; overflow-y:scroll; overflow-x:scroll;  table-layout:fixed;white-space:nowrap;">
														<table id="dataTable2" cellpadding="1" cellspacing="1" border="0" width=635px; bgcolor=efefef>
															<colgroup>
																<col style="width:30px"/>
																<col style="width:100px"/>
																<col style="width:150px"/>
															</colgroup>
															<tr bgcolor="#e4e4e4">
																<th>순번</th>
																<th>코드</th>
																<th>분류명칭</th>
															</tr>
															<tbody id="dataListbody2" />
														</table>
													</div>
												</div>
												<!-- Pagging End ---------->
											</div>
								</td>
							</tr>
	    				</table>
    				 </div>
    			
    				
					<!--코드 등록/수정 화면 영역  --------------------------->
			   		<div id="subRegister" style="display:none;">
				   		<form name="subForm" id="subForm"  >
					   		<div class="bbs_list">
					   			<input type="hidden" id="saveUpdateFg" name="saveUpdateFg"  style="width: 100px;" >
					   			<ul class="tit">
									<li class="tit">코드 등록/수정  (코드 삭제시 사용여부가 N으로 변경되며 조회는 가능 합니다.)</li>
									<li class="btn">
										<a href="#" class="btn" onclick="eventSubCodeSave()"><span>저장</span></a>
										<a href="#" class="btn" onclick="eventSubCodeDelete()"><span>삭제</span></a>
									</li>
								</ul>
								
								<table class="bbs_search">
									<colgroup>
										<col width="200"/>
										<col width="*"/>
										<col width="200"/>
										<col width="*"/>
									</colgroup>
									<tbody>
										<tr height="26">
											<th>등록자아이디</th>
											<td><span id="modId" class="txt_c"></span></td>
											<th>최종수정일자</th>
											<td><span id="modDt" class="txt_c"></span></td>
										</tr>
										<tr>
											<th>* 분 류 코 드</th>
											<td>
												<input type="text" id="mstComCd" name="mstComCd" maxlength='5' style="width: 100px;" >
												<input type="hidden" id="mstComCdOld" name="mstComCdOld"  style="width: 100px;" >
											</td>
											<th>* 분 류 명 칭</th>
											<td>
												<input type="text" id="mstComNm" name="mstComNm"  style="width: 90%;" >
												<input type="hidden" id="mstComNmOld" name="mstComNmOld"  style="width: 90%;" >
											</td>
										</tr>
										<tr>
											<th><span class="required" style="display: none;">*</span> 세 부 코 드</th>
											<td>
												<input type="text" id="subDtlCd" name="subDtlCd" maxlength='5' style="width: 100px; ime-mode:disabled;"  >
												<input type="hidden" id="subDtlCdOld" name="subDtlCdOld" style="width: 90%;">
											</td>
											<th><span class="required" style="display: none;">*</span> 세 부 명 칭</th>
											<td>
												<input type="text" id="subDtlNm" name="subDtlNm"  style="width: 90%;" >
												<input type="hidden" id="subDtlNmOld" name="subDtlNmOld"  style="width: 90%;" >
											</td>
										</tr>
										<tr>
											<th>추가속성(1)</th>
											<td><input type="text" id="subExtent01" name="subExtent01"  style="width: 90%;" ></td>
											<th>추가속성(2)</th>
											<td><input type="text" id="subExtent02" name="subExtent02"  style="width: 90%;" ></td>
										</tr>
										<tr>
											<th>추가속성(3)</th>
											<td><input type="text" id="subExtent03" name="subExtent03"  style="width: 90%;" ></td>
											<th>추가속성(4)</th>
											<td><input type="text" id="subExtent04" name="subExtent04"  style="width: 90%;" ></td>
										</tr>
										<tr>
											<th>추가숫자속성(1)</th>
											<td><input type="text" id="subExtentCnt01" name="subExtentCnt01" onKeyup="this.value=this.value.replace(/[^-0-9]/g,'');"  style="width: 90%;" ></td>
											<th>추가숫자속성(2)</th>
											<td><input type="text" id="subExtentCnt02" name="subExtentCnt02" onKeyup="this.value=this.value.replace(/[^-0-9]/g,'');"  style="width: 90%;" ></td>
										</tr>
										<tr>
											<th>추가숫자속성(3)</th>
											<td><input type="text" id="subExtentCnt03" name="subExtentCnt03" onKeyup="this.value=this.value.replace(/[^-0-9]/g,'');" style="width: 90%;" ></td>
											<th>추가숫자속성(4)</th>
											<td><input type="text" id="subExtentCnt04" name="subExtentCnt04" onKeyup="this.value=this.value.replace(/[^-0-9]/g,'');" style="width: 90%;" ></td>
										</tr>
										<tr>
											<th>정렬순서/사용여부</th>
											<td>
											    <input type="text" id="subSortNo" name="subSortNo" style="width: 100px; ime-mode:disabled;" > /
												<input type="radio"  name="subUseYn" id="subUseYn" value="Y" />YES 
												<input type="radio"  name="subUseYn" id="subUseYn" value="N" />NO
											</td>
											<th></th>
											<td></td>
										</tr>
									</tbody>
								</table>
								
							</div>
						</form>
					</div>
					<!-- 마스터 코드 등록/수정 화면 영역 end ------------------------->
    				
    				
    				
				</div>
		</div>
	</div>
</body>
</html>
