<%--
	Page Name 	: NEDMPRO0400.jsp
	Description : 신상품 입점 제안 등록  
	Modification Information
	
	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2025.03.06		js
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
</style>
	<script type="text/javascript" >
		/* dom이 생성되면 ready method 실행 */
		$(document).ready(function() {

			
		    //템플릿 내에 팀 변경시 대분류 변경 
		    $(document).on("change", "#searchForm select[name=teamCd]", function () {
		      //  $("#l1Cd option").not("[value='']").remove();
		      //  $("#grpCd option").not("[value='']").remove();
		      //  _eventSelectL1List($(this).val().replace(/\s/gi, ''));
		        
		        var selectedTeamCd = $(this).val().replace(/\s/gi, '');
		        var row = $(this).closest("tr"); // 현재 선택한 셀렉트 박스가 속한 행 찾기
		        
		        // 해당 행의 l1Cd만 변경하도록 수정
		        var l1CdSelect = row.find("select[name=l1Cd]");
		        l1CdSelect.find("option").not("[value='']").remove();

		        // AJAX 요청
		        var paramInfo = { groupCode: selectedTeamCd };
		        
		        $.ajax({
		            contentType: 'application/json; charset=utf-8',
		            type: 'post',
		            dataType: 'json',
		            async: false,
		            url: '<c:url value="/edi/product/NselectL1List.json"/>',
		            data: JSON.stringify(paramInfo),
		            success: function (data) {
		                var resultList = data.l1List;
		                if (resultList.length > 0) {
		                    var eleHtml = [];
		                    for (var i = 0; i < resultList.length; i++) {
		                        eleHtml.push("<option value='" + resultList[i].l1Cd + "'>" + resultList[i].l1Nm + "</option>");
		                    }
		                    l1CdSelect.append(eleHtml.join(''));
		                }
		            }
		        });
		    });
			
		    // 원가, 판매가 입력 시 이익률 자동 계산
			$(document).on("keyup", ".priceNorFiled, .priceSelFiled", function () {
				var rnum = $(this).attr("id").replace(/[^0-9]/g, ""); // 해당 행의 rnum 추출
			    autoBeneRate(rnum);
			});

			
// 			$(document).on("click", "a[name='fileNameLink']", function (e) {
// 			    e.preventDefault();
// 			    const imgUrl = $(this).closest("tr").find("input[name='imgfileUrl']").val();
// 			    const imgId = $(this).closest("tr").find("input[name='imgId']").val();
// 			    imgFilePreView(imgId,imgUrl);
// 			});
			
			//금액영역 blur comma입력
			$(document).on("blur", ".amt", function(){
				this.value = setComma(this.value);		
			});
			
			//금액입력란 focus 시 comma 제거
			$(document).on("focus", ".amt", function(){
				this.value = this.value.replace(/[^0-9.]/g,"");		
			});
			
		 	// 이미지 처리 로직 
		    $(document).on("change", ".prodImgInput", function (event) {
		    	 var row = $(this).closest("tr");
		        const fileInput = $(event.target);
		        const rowNum = row.find("input[name=rnum]").val();
		        const file = fileInput[0].files[0];
		        
		        var imgId = row.find("input[name=imgId]").val();

		        const fileNameLink = $("#fileNameLink"+rowNum);
		        const uploadBtn = $("#uploadBtn${rowNum}"+rowNum);
		        const imgUrlInput = $("#imgfileUrl${rowNum}"+rowNum);
		        const preview = $("#prodImgPreview${rowNum}"+rowNum);

		        if (file) {
		            // 파일 확장자 검사
		            const allowedExtensions = /(\.jpg)$/i;
		            if (!allowedExtensions.test(file.name)) {
		                alert("이미지는 JPG 파일만 가능합니다.");
		                fileInput.val(""); // 파일 입력 초기화
		                fileNameLink.hide();
		                uploadBtn.show();
		                preview.hide();
		                return;
		            }

		            // 파일 읽기 및 미리보기 설정
		            const reader = new FileReader();
		            reader.onload = function (e) {
		                // URL을 히든 인풋에 저장
		                imgUrlInput.val(e.target.result);
		                $("#imgfileUrl"+rowNum).val(e.target.result);
		                
		                // 상세보기 버튼 활성화
		                fileNameLink
		                .html("<span>상세보기</span>") // <span>으로 감싸지 않으면 css 버튼 깨짐 
		                .attr("class", "btn")
// 		                .attr("href", "javascript:imgFilePreView(imgId,imgUrlInput);") // 링크 막기
		                .attr("title", file.name)
		                .show();
		                
		                
		                // 등록하기 버튼 숨기기
		                uploadBtn.show();

		                // 미리보기 숨김 유지
		                preview.hide();
		                
		                row.find("input[name=orgFileNm]").val(file.name);
		            };
		            reader.readAsDataURL(file);
		        }
		    });


			//----- 검색조건 협력업체 Default 설정.
/* 			var srchEntpCd = "<c:out value='${param.srchEntpCd}'/>";  //검색조건 협력업체코드
			var repVendorId = "<c:out value='${epcLoginVO.repVendorId}'/>";  //관리자로 로그인 해서 협력업체 갈아타기 로그인시 협력업체 코드
			if (srchEntpCd.replace(/\s/gi, '') ==  "") {
				$("#searchForm #srchEntpCd").val(repVendorId);
			} else {
				$("#searchForm #srchEntpCd").val(srchEntpCd);
			} */
			
			//신규 상품 입점 정보 전체 체크박스 ------------------------------------
			// 전체 체크박스 클릭 이벤트
			$("#cbx_chkAll").click(function() {
			    if ($("#cbx_chkAll").is(":checked")) {
			        // 체크할 때만 disabled 아닌 체크박스만 체크
			        $("input[name=chkPropStore]").each(function() {
			            if (!$(this).prop("disabled")) {
			                $(this).prop("checked", true);
			            }
			        });
			    } else {
			        // 해제할 땐 모든 체크박스 해제
			        $("input[name=chkPropStore]").prop("checked", false);
			    }
			});
			
			// 개별 체크박스 클릭 시 전체 선택 체크박스 상태 조정
			$("input[name=chkPropStore]").click(function() {
			    var total = $("input[name=chkPropStore]:not(:disabled)").length; // disabled 제외한 총 체크박스 수
			    var checked = $("input[name=chkPropStore]:checked").length;

			    // 체크된 개수와 총 개수가 같으면 전체 체크박스 체크, 아니면 해제
			    if (total != checked) {
			        $("#cbx_chkAll").prop("checked", false);
			    } else {
			        $("#cbx_chkAll").prop("checked", true);
			    }
			});
			//---------------------------------------------------------------
	
			
			//-----검색조건 팀 변경시 이벤트
			$("#searchForm select[name=srchTeamCd]").change(function() {

				//----- 대, 중, 소분류 초기화
				$("#srchL1Cd option").not("[value='']").remove();
				$("#grpCd option").not("[value='']").remove();

				//doCategoryReset();	//200306 EC전용 카테코리 추가

				_eventSelSrchL1List($(this).val().replace(/\s/gi, ''));
			});
			
			//------ 데이터리스트 변경
			$(document).on("change", "#dataListbody select[name='venCd']", function(){
				let venCd = $.trim($(this).val());	//업체코드
				let pTr = $(this).closest("tr");
				
				//업체코드 없을경우,
				if(venCd == ""){
					//채널정보 전부 삭제처리
					pTr.find("select[name='sellChanCd']").find("option[value!='']").remove();
					return;
				}
				
				
				var selChanCd = $.trim(pTr.find("select[name='sellChanCd']").val());
				eventSelChkVenZzorgInfo(pTr, venCd, selChanCd);
			});
		
		});
		
		/* 등록 되어있는 데이터 조회시 대분류 맵핑 */
		function eventTempleteL1List(teamCd,l1Cd,rnum,prdtReqYn,prdtStatus) {
			var paramInfo	=	{};
			
			if(prdtReqYn == "Y" || prdtStatus == '2'){
				paramInfo["groupCode"]	=	teamCd;
				paramInfo["l1Cd"]	=	l1Cd;
        	}else{
        		paramInfo["groupCode"]	=	teamCd;
        	}
			
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : true,
				url : '<c:url value="/edi/product/selectTempL1List.json"/>',
				data : JSON.stringify(paramInfo),
				success : function(data) {
					var resultList	=	data.l1List;
					if (resultList.length > 0) {

						var eleHtml = [];
						
						for (var i = 0; i < resultList.length; i++) {
						    if (resultList[i].l1Cd == l1Cd) {
						        eleHtml[i] = "<option value='" + resultList[i].l1Cd + "' selected='selected'>" + resultList[i].l1Nm + "</option>\n";
						    } else {
						        eleHtml[i] = "<option value='" + resultList[i].l1Cd + "'>" + resultList[i].l1Nm + "</option>\n";
						    }
						}

						$("#l1Cd"+rnum+ "option").not("[value='']").remove();	//콤보박스 초기화
						$("#l1Cd"+rnum).append(eleHtml.join(''));
					} else {
						$("#l1Cd"+rnum+ "option").not("[value='']").remove();	//콤보박스 초기화
					}
				}
			});
		}
		
		/* 팀의 대분류 조회 */
		function _eventSelSrchL1List(val) {
			var paramInfo	=	{};

			paramInfo["groupCode"]	=	val;

			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/product/NselectL1List.json"/>',
				data : JSON.stringify(paramInfo),
				success : function(data) {
					var resultList	=	data.l1List;
					if (resultList.length > 0) {

						var eleHtml = [];
						for (var i = 0; i < resultList.length; i++) {
							eleHtml[i] = "<option value='"+resultList[i].l1Cd+"'>"+resultList[i].l1Nm+"</option>"+"\n";
						}

						$("#srchL1Cd option").not("[value='']").remove();	//콤보박스 초기화
						$("#srchL1Cd").append(eleHtml.join(''));
					} else {
						$("#srchL1Cd option").not("[value='']").remove();	//콤보박스 초기화
					}
				}
			});
		}

		/* 조회 버튼 실행  */
		function _eventSearch() {
			goPage('1');
		}
		
		/* 조회 */
		function goPage(pageIndex) {
			var paramInfo = {};

			var srchFromDt = $("#searchForm input[name='srchFromDt']").val().replace(/\s/gi, '');
			var srchEndDt = $("#searchForm input[name='srchEndDt']").val().replace(/\s/gi, '');

			if (srchFromDt == "") {
				alert("검색 시작일이 입력되지 않았습니다.");
				$("#searchForm select[name='srchFromDt']").focus();
				return;
			}

			if (srchEndDt == "") {
				alert("검색 종료일이 입력되지 않았습니다.");
				$("#searchForm select[name='srchEndDt']").focus();
				return;
			}


			var dateRegex = RegExp(/^([1-9])\d{3}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/); // EX) 2023-03-01

			if (!dateRegex.test(srchFromDt)) {
				alert("검색 시작일을 정확히 입력해주세요.");
				$("#searchForm select[name='srchFromDt']").focus();
				return;
			}
			if (!dateRegex.test(srchEndDt)) {
				alert("검색 종료일을 정확히 입력해주세요.");
				$("#searchForm select[name='srchEndDt']").focus();
				return;
			}

			paramInfo["srchEntpCd"] = $("#searchForm select[name='srchEntpCd']").val();
			paramInfo["srchTeamCd"] = $("#searchForm select[name='srchTeamCd']").val();
			paramInfo["srchL1Cd"] = $("#searchForm select[name='srchL1Cd']").val();
			paramInfo["srchProdNm"] = $("#srchProdNm").val();
			paramInfo["srchStatus"] = $("#searchForm select[name='srchStatus']").val();
			paramInfo["srchFromDt"] = srchFromDt.replaceAll("-", "");
			paramInfo["srchEndDt"] = srchEndDt.replaceAll("-", "");

		$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/product/selectProposeStore.json"/>',
				data : JSON.stringify(paramInfo),
				success : function(data) {
					$("#pageIndex").val(pageIndex);
					
					//체크박스 전체선택 초기화
					$("#cbx_chkAll").prop("checked", false);
					
					//footer Setting 
					if(data.resultList.length > 0){
						// 조회 성공시 
						$('#resultMsg').text('<spring:message code="msg.common.success.select"/>');
					}else{
						//조회된 건수 없음
						$('#resultMsg').text('<spring:message code="msg.common.info.nodata"/>');
					}
					
					//json 으로 호출된 결과값을 화면에 Setting
					_eventSetTbodyValue(data);
				},
				error : function(request, status, error, jqXHR) {
					// 요청처리를 실패하였습니다.
					$('#resultMsg').text('msg.common.fail.request');
				} 
			}); 
		}

		/* List Data 셋팅 */
		function _eventSetTbodyValue(json) {
		    setTbodyInit("dataListbody"); // dataList 초기화

		    var data = json.resultList;
		    
		 	// 컨트롤러에서 가져온 업체 코드 리스트
		    var vendorList = [];
		    <c:forEach var="venodr" items="${vendorList}">
		   		vendorList.push({
		            venCd: "${venodr.venCd}",
		            venNm: "${venodr.venNm}"
		        });
	    	</c:forEach>
		    
		    
		    // 컨트롤러에서 가져온 팀 코드 리스트
		    var teamList = [];
		    <c:forEach var="team" items="${teamList}">
		        teamList.push({
		            teamCd: "${team.teamCd}",
		            teamNm: "${team.teamNm}"
		        });
		    </c:forEach>
		    


		    if (data.length > 0) {

				for (var i = 0; i < data.length; i++) {
					
		            //각 인풋갑 포맷--------------------------------------------
		           //원가
		            if (data[i].norProdPcost != null && data[i].norProdPcost != "") {
		            	data[i].norProdPcost = comma(uncomma(data[i].norProdPcost));
		            }
		            
		            //판매가
		            if (data[i].prodSellPrc != null && data[i].prodSellPrc != "") {
		            	data[i].prodSellPrc = comma(uncomma(data[i].prodSellPrc));
		            }
		            
		            //목표매출
		            if (data[i].trgtSaleCurr != null  && data[i].trgtSaleCurr != "") {
		            	data[i].trgtSaleCurr = comma(uncomma(data[i].trgtSaleCurr));
		            }
		            
		            //입수
		            if (data[i].inrcntQty != null  && data[i].inrcntQty != "") {
		            	data[i].inrcntQty = comma(uncomma(data[i].inrcntQty));
		            }
		            //-----------------------------------------------------------
		    	}
		    	
				
		    	
		        $("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
		        $("#paging-ul").html(json.contents);
		    	
		        
		        for (var i = 0; i < data.length; i++) {
		        	  var rowNum = data[i].rnum;
		        	// prdtReqYn이 "Y"인 경우 체크박스 비활성화 처리
		            if (data[i].prdtReqYn === "Y") {
		                $("#chkPropStore" + data[i].rnum).prop("disabled", true);
		        		$("#prdtRt" + data[i].rnum).prop("disabled", true);
		        		$("#frzCd" + data[i].rnum).prop("disabled", true);
		        		$("#norSesnCd" + data[i].rnum).prop("disabled", true);
		        		$("#waers" + data[i].rnum).prop("disabled", true);
		        		$("#sellChanCd" + data[i].rnum).prop("disabled", true);
		        		 
		        		/*  $('input:checkbox[id="sellChanCd'+ data[i].rnum + '"]').each(function() {     
		        			$(this).prop("disabled",true);
			              });  */
		            }
		        	
		        	//루트구분 값 세팅 
		        	if(data[i].prdtRt != null){
		        		$("#prdtRt" + data[i].rnum).val(data[i].prdtRt);
		        	}
		        	
		        	//상온구분 값 세팅
		        	if(data[i].frzCd != null){
		        		$("#frzCd" + data[i].rnum).val(data[i].frzCd);
		        	}
		        	
		        	//일반 시즌 값 세팅 
		        	if(data[i].norSesnCd != null){
		        		$("#norSesnCd" + data[i].rnum).val(data[i].norSesnCd);
		        	}
		        	
		        	//통화 구분 값 세팅
		        	if(data[i].waers != null){
		        		$("#waers" + data[i].rnum).val(data[i].waers);
		        	}
		        	
		        	//판매채널 값 세팅 
		        	if(data[i].sellChanCd != null){
		        		$("#sellChanCd" + data[i].rnum).val(data[i].sellChanCd);
		        	}
		        	
		        	
		        	// 판매채널 값 세팅 
		    /*     	if(data[i].sellChanCd != null){
		        		var checkedValues = data[i].sellChanCd;
		        		var rnum = data[i].rnum;
		        		
			            if (checkedValues) {
			                var checkedArray = checkedValues.split(","); // 체크된 값 배열로 변환
			                
			                var length = $('input:checkbox[id="sellChanCd'+ rnum + '"]').length
			                
			                 $('input:checkbox[id="sellChanCd'+ rnum + '"]').each(function() {     
								if(checkedArray.includes($(this).val())){
			                	   this.checked = true; //checked 처리      
			                	} 
			                }); 
			            }
		        	}  */
		        	
		        	
		        	//승인된 값은 수정 불가 
		        	if(data[i].prdtStatus == "2" || (data[i].prdtStatus == "0" && data[i].prdtReqYn == "Y")){
		        		$("#chkPropStore" + data[i].rnum).prop("disabled", true);
		        		$("#prdtRt" + data[i].rnum).prop("disabled", true);
		        		$("#frzCd" + data[i].rnum).prop("disabled", true);
		        		$("#norSesnCd" + data[i].rnum).prop("disabled", true);
		        		$("#waers" + data[i].rnum).prop("disabled", true);
		        		$("#sellChanCd" + data[i].rnum).prop("disabled", true);
		        		/*  $('input:checkbox[id="sellChanCd'+ data[i].rnum + '"]').each(function() {     
		        			$(this).prop("disabled",true);
			              });  */
		        	}else{
						//반려건은 값 수정 가능 
		        		$("#chkPropStore" + data[i].rnum).prop("disabled", false);
		        		$("#prdtRt" + data[i].rnum).prop("disabled", false);
		        		$("#frzCd" + data[i].rnum).prop("disabled", false);
		        		$("#norSesnCd" + data[i].rnum).prop("disabled", false);
		        		$("#waers" + data[i].rnum).prop("disabled", false);
		        		$("#sellChanCd" + data[i].rnum).prop("disabled", false);
		        		/*  $('input:checkbox[id="sellChanCd'+ data[i].rnum + '"]').each(function() {     
		        			$(this).prop("disabled",false);
			              });  */
		        	}
		        	
		        	//상품소구특징 존재여부 확인 
		        	var prdtSpecValid = data[i].prdtSpecSex + data[i].prdtSpecAge+ data[i].prdtSpecRange;
		        	
		        	//상품소구특징 있으면 상세보기 버튼 활성화 없으면 등록하기 버튼 활성화 
		        	if(prdtSpecValid == null || prdtSpecValid == ""){
		        		$("#prodDetailRegPop" + data[i].rnum).show();
		        		$("#prodDetailSelPop" + data[i].rnum).hide();
		        	}else{
		        		$("#prodDetailRegPop" + data[i].rnum).hide();
		        		$("#prodDetailSelPop" + data[i].rnum).show();
		        	}
		        	
		            // 셀렉트 박스 동적 생성 (팀 셀렉트 박스)
		            var teamSelect = $("#dataListbody tr").eq(i).find("select[name='teamCd']");
		            teamSelect.empty(); // 기존 옵션 초기화
		            teamSelect.append('<option value="">선택</option>');

		            for (var j = 0; j < teamList.length; j++) {
		            	if (data[i].teamCd == teamList[j].teamCd) {
			                teamSelect.append('<option value="' + teamList[j].teamCd + '" selected>' + teamList[j].teamNm + '</option>');
			            } else {
			                teamSelect.append('<option value="' + teamList[j].teamCd + '">' + teamList[j].teamNm + '</option>');
			            }
		            }
		            
		         	// 셀렉트 박스 동적 생성 (업체 셀렉트 박스)
		        	var venSelect = $("#dataListbody tr").eq(i).find("select[name='venCd']");
		        	venSelect.empty(); // 기존 옵션 초기화
		        	venSelect.append('<option value="">선택</option>');

		            for (var j = 0; j < vendorList.length; j++) {
		            	if (data[i].venCd == vendorList[j].venCd) {
		            		venSelect.append('<option value="' + vendorList[j].venCd + '" selected>' + vendorList[j].venNm + '</option>');
			            } else {
			            	venSelect.append('<option value="' + vendorList[j].venCd + '">' + vendorList[j].venNm + '</option>');
			            }
		            }
		            
		        	 // 체크박스 체크
		            var checkedValues = data[i].sellChanCd;
		            var rnum = data[i].rnum;

		            if (checkedValues) {
		                var checkedArray = checkedValues.split(","); // 체크된 값 배열로 변환

		                checkedArray.forEach(function(val) {
		                    var checkbox = $("#sellChanCd" + rnum + "_" + val); 
		                    if (checkbox.length > 0 && checkbox.attr("type") === "checkbox") {
		                        checkbox.prop("checked", true); // 체크박스 체크
		                    }
		                });
		            }
		         	
		            // value 값 세팅  
		            $("#l1Cd" + data[i].rnum).val(data[i].l1Cd);
		            $("#orgImgId" + data[i].rnum).val(data[i].imgId);
		            
		            
		            //이익율 인풋
		            var prftRateInput = $("#prftRate" + data[i].rnum);
					
		            
		            //각 인풋갑 포맷--------------------------------------------
		            
		            if (prftRateInput.length && prftRateInput.val()) {
		                prftRateInput.val((prftRateInput.val()));
		            }
		            //-----------------------------------------------------------
		            
		            
		            //납품가능일 문자열 -> 날짜 형식으로 변환 -----------------------------------
		            var delvPsbtDd = data[i].delvPsbtDd;
		        	if(delvPsbtDd != null && delvPsbtDd != ""){
		        		 delvPsbtDd = delvPsbtDd.replace(/(\d{4})(\d{2})(\d{2})/g, '$1-$2-$3');
				            
				         $("#delvPsbtDd" + data[i].rnum).val(delvPsbtDd);					
		        	}
		            //---------------------------------------------------------------
		            
		            /* 조회된 팀 정보를 기반으로 조회된 대분류 맵핑 */
		            if(data[i].teamCd != null && data[i].teamCd != ""){
		            	eventTempleteL1List(data[i].teamCd,data[i].l1Cd,data[i].rnum,data[i].prdtReqYn,data[i].prdtStatus)
		            }
		            
		        }
		    } else {
		        setTbodyNoResult("dataListbody", 20);
		        $("#paging-ul").html("");
		    }
		}


		
		/* 행추가 */
		function _eventAddRow(){
			 // 조회결과가 없습니다 행이 존재하는지 여부
		    var checkTdm = $("#dataListbody tr").find(".tdm").length > 0;

		    // checkTdm 이 있을 때만 초기화 실행
		    if (checkTdm) {
		        setTbodyInit("dataListbody"); // dataList 초기화
		    }
		    
		    
			// 컨트롤러에서 가져온 업체 코드 리스트
		    var vendorList = [];
		    <c:forEach var="venodr" items="${vendorList}">
		   		vendorList.push({
		            venCd: "${venodr.venCd}",
		            venNm: "${venodr.venNm}"
		        });
	    	</c:forEach>
			
		    // 컨트롤러에서 가져온 팀 코드 -----------------------------------
		    var teamList = [];

		    <c:forEach var="team" items="${teamList}">
		        teamList.push({
		            teamCd: "${team.teamCd}",
		            teamNm: "${team.teamNm}"
		        });
		    </c:forEach>
		    //--------------------------------------------------------
		    
			 var newRowData = {
				        rnum: $("#dataListbody tr").length + 1, //  행 개수 + 1
				        propRegNo:"",
				        prdtStatus: "",
				        venCd: "",
				        teamCd: "",
				        l1Cd: "",
				        prodShortNm: "",
				        prodStandardNm: "",
				        norProdPcost: "",
				        prodSellPrc: "",
				        prftRate: "",
				        norSesnCd: "",
				        prdtRt: "",
				        inrcntQty: "",
				        delvPsbtDd: "",
				        sellCd: "",
				        sellChanCd: "",
				        frzCd: "",
				        trgtSaleCurr: "",
				        waers:"",
				        imgId : "",
				        prdtReqYn : "",
				        regDt: ""
				    };
			 
			 $("#dataListTemplate").tmpl(newRowData).appendTo("#dataListbody");
				 	
			
	     		// 팀 셀렉트 박스 동적 생성
			    var lastRow = $("#dataListbody tr:last");
			    var teamSelect = lastRow.find("select[name='teamCd']");

			    teamSelect.append('<option value="">선택</option>');
			    
			    for(var i =0 ; i<teamList.length; i++){
			    	teamSelect.append('<option value='+teamList[i].teamCd+'>'+teamList[i].teamNm+'</option>');
			    }
			    
			    
	     		var venSelect = lastRow.find("select[name='venCd']");
			    
	     		venSelect.append('<option value="">선택</option>');
			    
			    for(var i =0 ; i<vendorList.length; i++){
			    	venSelect.append('<option value='+vendorList[i].venCd+'>'+vendorList[i].venNm+'</option>');
			    }

	     		
				//상품소구특징 있으면 상세보기 버튼 활성화 없으면 등록하기 버튼 활성화 ------------
	     		var rowNum = lastRow.find("input[name=rnum]").val();
				
				var prodDetailRegPop = $("#prodDetailRegPop"+rowNum);
				var prodDetailSelPop = $("#prodDetailSelPop"+rowNum);	
			
		        
				prodDetailRegPop.show();
				prodDetailSelPop.hide();
				//--------------------------------------------------------------

		}
		
		
		//통합 삭제 
		function _eventNewDelete(){
			var tgObj = $("#dataListbody tr").find("input[name='chkPropStore']:checked");
			
			if(tgObj.length == 0){
				alert("선택된 신규 입점 제안 상품이 없습니다.");
				return;
			}
			
			//요청번호가 없고 상태값이 아직 없는 것들만 ajax
			var saveInfo = {};
			var prodDataArr = [];	//DB삭제대상 data
			
			var flag = true;
		
			
			tgObj.closest("tr").each(function(){
				var delInfo = {};
				
				var propRegNo = $(this).find("input[name='propRegNo']").val();				//요청번호
				var prdtStatus = $(this).find("input[name='prdtStatus']").val();			//진행상태
				var rnum = $.trim($(this).find("span[name='rnum']").text());		//행번호
				var prdtReqYn = $.trim($(this).find("span[name='prdtReqYn']").text());		//요청유무
				
				if("2" == prdtStatus || "1" == prdtStatus){
					alert("승인되거나 반려된 상태에서는 삭제가 불가능 합니다."+rnum);
					flag = false;
					return false;
				}
				
				if("Y" == prdtReqYn){
					alert("요청이 되어있는 상태에서는 삭제가 불가능합니다." + rnum);
					flag = false;
					return false;
				}
				
				//DB에서 삭제할 요청번호 담기 
				if(propRegNo != "" && prdtStatus != ""){
					delInfo.propRegNo = propRegNo;
					prodDataArr.push(delInfo);
				}
			});
			
			if(!flag) return;
			
			if(!confirm("삭제하시겠습니까?")) return;
			
			//DB에서 삭제할 데이터가 있을 경우
			if(prodDataArr != null && prodDataArr.length > 0){
				saveInfo = prodDataArr;		//삭제대상리스트
				
			   $.ajax({
					contentType : 'application/json; charset=utf-8',
					type : 'post',
					dataType : 'json',
					async : false,
					url: '<c:url value="/edi/product/deleteNewPropStore.json"/>',
					data : JSON.stringify(saveInfo),
					success: function (data) {
				            if (data.msg === "SUCCESS") {
				                alert("신규 입점 상품이 정상적으로 삭제 되었습니다.");
				                _eventSearch();
				            }else{
				            	var errMsg = $.trim(data.errMsg) != "" ? data.errMsg : "처리 중 오류가 발생했습니다.";
								alert(errMsg);
				            }
				        },
				        error: function () {
				            alert("삭제에 실패했습니다.");
				        }
				});
				
			}else{
				//선택한 행 삭제
				$("#dataListbody tr").find("input[name='chkPropStore']:checked").closest("tr").remove();
			}
			
		}
		
		//저장
		function _eventSave() {
		    var formData = new FormData();
		    var searchInfo = [];
		    var saveValidFlag = true;
		    var flag = true;
		    
		    //납품가능일 날짜 validation
		    var dateValidation = false;
		    
		    var rowIndex = 1; // index
		    var errorMessage = ""; // 오류 메시지 저장용
		    
		    var datalen = $('input[name="chkPropStore"]:checked').length;
		    if (datalen === 0) {
		        alert("체크된 항목이 없습니다!");
		        return;
		    }
		    
		    $('input[name="chkPropStore"]:checked').each(function (i) {
		    	var row = $(this).closest('tr');
		    	var rowData = {};
		    	
		    	//요청여부
		        var prdtReqYn = $.trim(row.find("input[name='prdtReqYn']").val()).toUpperCase()||"N";
		    	//진행상태
		    	var prdtStatus =  $.trim(row.find("input[name='prdtStatus']").val()).toUpperCase()||"";
		        
		        //승인된 건
		        if("2" == prdtStatus){
					alert("이미 승인처리된 제안입니다.(행번호:"+Number(i+1)+")");
					flag = false;
					return false;
				}
		        
		        //요청중인 건
		        if("Y" == prdtReqYn){
		        	alert("요청 중인 제안입니다. (행번호:"+Number(i+1)+")");
					flag = false;
					return false;
				}
		        
		    	var imgUrl =  $(this).closest("tr").find("input[name='imgfileUrl']").val();
		        
				row.find('input, select').each(function () {
		        	var name = $(this).attr('name');
		        	var value = $(this).val();

		            // 숫자 및 날짜 처리
		     //       if (name === "prftRate") value = value.replace('%', '');
		            if (name === "norProdPcost" || name === "prodSellPrc" || name === "trgtSaleCurr" || name === "inrcntQty") value = value.replace(/,/g, '');
		            if (name === "delvPsbtDd") value = value.replace(/-/g, '');

		            // 파일 처리
		             if (name === "prodImgInput") {
		                let fileInput = row.find('.prodImgInput')[0];
		                if (fileInput.files.length > 0) {
		                    formData.append("files", fileInput.files[0]);
		                }else{
		                	formData.append("files", new Blob());  // 파일이 없을 경우 null 값 추가 (인덱스 유지) 빼면 아웃오브바운드 오류남 
		                }
		            }
		            
		            
		            //저장시 필수값 검증 start--------------
		            var requiredFields = ["venCd", "teamCd", "l1Cd", "prodShortNm", "prodStandardNm", "norProdPcost", "prodSellPrc", "prftRate", "norSesnCd", "prdtRt", "inrcntQty", "delvPsbtDd", "sellChanCd", "frzCd", "trgtSaleCurr"];

					if (requiredFields.includes(name)) {
						if(value == null || value === ""){
							saveValidFlag = false;
							var alertName = "";
							
							if(name == "venCd"){
								alertName = "파트너사코드";
							}else if(name == "teamCd"){
								alertName = "팀";
							}else if(name == "l1Cd"){
								alertName = "대분류";
							}else if(name == "prodShortNm"){
								alertName = "쇼카드상품명";
							}else if(name == "prodStandardNm"){
								alertName = "규격";
							}else if(name == "norProdPcost"){
								alertName = "원가";
							}else if(name == "prodSellPrc"){
								alertName = "판매가";
							}else if(name == "prftRate"){
								alertName = "이익율";
							}else if(name == "norSesnCd"){
								alertName = "일반/시즌구분";
							}else if(name == "prdtRt"){
								alertName = "루트";
							}else if(name == "inrcntQty"){
								alertName = "입수";
							}else if(name == "delvPsbtDd"){
								alertName = "납품가능일";
							}else if(name == "frzCd"){
								alertName = "상온구분";
							}else if(name == "trgtSaleCurr"){
								alertName = "목표매출";
							}else if(name == "sellChanCd"){
								alertName = "판매채널";
							}
							errorMessage += "\n 체크된 "+rowIndex+"번째 행: ["+alertName+"] 값이 누락되었습니다.";
						}
					}
					//저장시 필수값 검증 end--------------
					
					
		            rowData[name] = value;
		        });
		        
		       	// 판매채널 체크박스 처리 
		   /*  	var checkedSellChan = [];
		    	row.find('input[name="sellChanCd"]:checked').each(function () {
		    	    checkedSellChan.push($(this).val());
		    	});
		    	
		    	rowData["sellChanCd"] = checkedSellChan.join(","); // 구분자로 여러건 들어가게 저장 
		    	

		    	// 필수값 검증 추가 (sellChanCd)
		    	if (checkedSellChan == null || checkedSellChan.length == 0) {
		    	    saveValidFlag = false;
		    	    errorMessage += "\n 체크된" + rowIndex + "번째 행: [판매채널] 값이 누락되었습니다.";
		    	} */

		        searchInfo.push(rowData);
		        rowIndex++; // 행 번호 증가
		    });
		    
		    if(!flag) return;
		    
		    if(!saveValidFlag){
		    	alert("필수값이 입력되지 않았습니다. 확인 부탁드립니다." + errorMessage);
				return;
		    }

		    // JSON 데이터 추가
		    formData.append("data", JSON.stringify(searchInfo));

		    $.ajax({
		        url: '<c:url value="/edi/product/insertNewPropStore.json"/>',
		        type: 'POST',
		        data: formData,
		        processData: false,
		        contentType: false,
		        success: function (data) {
		            if (data.msg === "SUCCESS") {
		                alert("상품 제안정보가 저장되었습니다.");
		                _eventSearch();
		            }
		        }
		    });
		}
		
		
		
		
		//input 값 금액 포맷 
		function inputNumberFormat(obj){
	       obj.value = comma(uncomma(obj.value));
		}
		
		//, 찍기 금액에
		function comma(str) {
			 str = String(str);
		     return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
		}
		
		//, 컴마 없애기 
		function uncomma(str) {
	        str = String(str);
	        return str.replace(/[^\d]+/g, '');
	    }
		
		// 확률 인풋 값 뒤에 퍼센트 붙이기 
		function inputPercentFormat(obj) {
		    var value = obj.value;

		    // 숫자만 남기기
		    value = value.replace(/[^0-9]/g, "");

		    // 숫자가 있을 때만 퍼센트 붙이기
		    if (value !== "") {
		    	obj.value = value;
		    } else {
		    	obj.value = "";
		    }
		}
		
		//제안 요청 
		function _eventRequest(){
			//----- 선택된 상품이 없으면 return
			var chkLen = $("input[name='chkPropStore']:checked").length;  //체크된 카운트
			if (chkLen <= 0) {
				alert("선택된 신규 입점 제안 상품이 없습니다.");
				return;
			}
			
			 var searchInfo = [];
			 
			 var flag = true;
			
			 // 선택된 행 불러오기 
		    $("input[name='chkPropStore']:checked").each(function (i) {
		        var row = $(this).closest('tr');
		        var rowData = {};
		        
		        //요청여부
		        var prdtReqYn = $.trim(row.find("input[name='prdtReqYn']").val()).toUpperCase()||"N";
		    	//진행상태
		    	var prdtStatus =  $.trim(row.find("input[name='prdtStatus']").val()).toUpperCase()||"";
		        
		        //승인된 건
		        if("2" == prdtStatus){
					alert("이미 승인처리된 제안입니다.(행번호:"+Number(i+1)+")");
					flag = false;
					return false;
				}
		        
				//저장하지 않은 건
				if("0" != prdtStatus){
					alert("임시저장 후 다시 시도해주세요.(행번호:"+Number(i+1)+")");
					flag = false;
					return false;
				}
		        
		        //요청중인 건
		        if("Y" == prdtReqYn){
		        	alert("요청 중인 제안입니다. (행번호:"+Number(i+1)+")");
					flag = false;
					return false;
				}
		        
		        row.find('input, select').map(function () {
		        	if(this.name != undefined && this.name != null && this.name != ""){
		        		switch(this.name){
			        		case "norProdPcost":		//원가
			        		case "prodSellPrc":			//판매가
			        		case "trgtSaleCurr":		//목표매출
			        		case "inrcntQty":			//입수
			        		case "delvPsbtDd":			//납품가능일자
			        			rowData[this.name] = $.trim($(this).val()).replace(/\D/g,"");
			        			break;
		        			default:
		        				rowData[this.name] = $.trim($(this).val());
		        				break;
		        		}
					}
		        });
		        
		        if(rowData != null){
			        searchInfo.push(rowData);
		        }
		    });
			 
		    if(!flag) return;
		    if(!confirm('입점 제안을 요청 하시겠습니까?')) return;
		    
		    
		    $.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url: '<c:url value="/edi/product/updateNewPropStoreRequest.json"/>',
				data : JSON.stringify(searchInfo),
				success: function (data) {
		            if (data.msg === "SUCCESS") {
		                alert("요청되었습니다.");
		                _eventSearch();
		            }else if(data.msg === "FAIL"){
		            	if(data.errMsg != undefined && data.errMsg != null){
		            		alert("요청 중 오류가 발생하였습니다.\n"+$.trim(data.errMsg));	
		            	}else{
			            	alert("요청 중 오류가 발생하였습니다.");
		            	}
		                _eventSearch();
		            }
		        }
			});

		}
		
		//신규 입정 삼품 이미지 파일 업로드 
		function prodImgFileUpload(rowNum){
			 var rowNum =rowNum;
			 $("#prodImgInput"+rowNum).click();
		}
		
		//파일이미지 상세보기 
	 	function imgFilePreView(obj, imgId , imgUrlInput) {
			//임시업로드 이미지 정보
			let imgTmp = $.trim($(obj).closest("td").find("input[name='imgfileUrl']").val());
			
			if(imgTmp != ""){	//임시이미지
				$("#hiddenImgUrl").val(imgTmp);
				$("#hiddenImgId").val("");
			}else{				//저장된이미지
				$("#hiddenImgUrl").val(imgUrlInput);
			    $("#hiddenImgId").val(imgId);
			}

		    let popup = window.open('', 'popup', 'width=700,height=600,top=80,left=450,resizable=yes');
		    
		    if (popup) {
		        document.imgForm.target = 'popup';
		        document.imgForm.submit();
		        popup.focus();
		    } else {
		        alert('팝업 차단이 되어 있습니다. 팝업을 허용해주세요.');
		    }
		} 
		
		
		//상품 소구 특성 등록 팝업
		function prodDetailRegPop(paramPropRegNo,rnum,prdtSpec){
		
			var propRegNo = paramPropRegNo ? paramPropRegNo : "";
		    var rnum = rnum ? rnum : "";
		    
		    var prdtSpecRange = $("#prdtSpecRange"+rnum).val() ? $("#prdtSpecRange"+rnum).val() : "";
		    var prdtSpecSex = $("#prdtSpecSex"+rnum).val() ? $("#prdtSpecSex"+rnum).val() : "";
		    var prdtSpecAge = $("#prdtSpecAge"+rnum).val() ? $("#prdtSpecAge"+rnum).val() : "";
			
		    var heightVal = 350;
			
			var targetUrl = '<c:url value="/edi/product/NEDMPRO0401.do"/>';
			
			//regist 등록 화면 구분 
			var screenFlag = "R";
			
			Common.centerPopupWindow(targetUrl+"?propRegNo="+propRegNo+"&rnum="+rnum+"&prdtSpecRange="+prdtSpecRange+"&prdtSpecSex="+prdtSpecSex+"&prdtSpecAge="+prdtSpecAge+"&screenFlag="+screenFlag, 'prodDetailRegPopup', {width : 730, height : heightVal});
		}
		
		//상품 소구 특성 등록 콜백
		function prodDetailcallBack(data) {
		    var callBackData = data;

		    var rowNum = callBackData.rnum;
		    var propRegNo = callBackData.propRegNo;

		    $("#prdtSpecRange" + rowNum).val(callBackData.priceFg);
		    $("#prdtSpecAge" + rowNum).val(callBackData.customAgeFg);
		    $("#prdtSpecSex" + rowNum).val(callBackData.customGenderFg);
		    
		    // 팝업 상태 변경
		    $("#prodDetailRegPop" + rowNum).hide();
		    $("#prodDetailSelPop" + rowNum).show();
		}
		
		//이익율 자동계산 ((판매가 - 원가) / 원가  * 100 )
		function autoBeneRate(rnum) {
		    var norPrice = parseFloat($("#norProdPcost" + rnum).val().replace(/,/g, "")) || 0;
		    var selPrice = parseFloat($("#prodSellPrc" + rnum).val().replace(/,/g, "")) || 0;

		    let profitRate = 0;
		    if (norPrice > 0 && selPrice > 0) {
		        profitRate = ((selPrice - norPrice) / norPrice * 100).toFixed(2);
		        profitRate = profitRate;
		    }

		    $("#prftRate" + rnum).val(profitRate); // 해당 행의 prftRate만 업데이트
		}
		
		//엑셀 리스트 다운로드
		function _eventExcelDown(){
			
			 var srchStatus = $("#srchStatus").val();
			 var srchProdNm = $("#srchProdNm").val();
			 var srchFromDt = $("#srchFromDt").val();
			 var srchEndDt = $("#srchEndDt").val();
			 var srchL1Cd = $("#srchL1Cd").val();
			 var srchTeamCd = $("#srchTeamCd").val();
			 var srchEntpCd = $("#srchEntpCd").val();
			 
			 $("#hiddenSrchStatus").val(srchStatus);
			 $("#hiddenSrchProdNm").val(srchProdNm);
			 $("#hiddenSrchFromDt").val(srchFromDt);
			 $("#hiddenSrchEndDt").val(srchEndDt);
			 $("#hiddenSrchL1Cd").val(srchL1Cd);
			 $("#hiddenSrchTeamCd").val(srchTeamCd);
			 $("#hiddenSrchEntpCd").val(srchEntpCd);
			 
			var form = document.excelForm;
			form.action = "<c:url value='/edi/product/selectNewPropStoreExcelDownload.do' />";
			form.submit();
		}

		//엑셀 양식 다운로드
	   function _eventExcelTmplDown(){
		   var form = document.excelForm;
		   
		   var chkVal = "400";  //업도르 양식 구분 값 
		   var fileName = "신상품입점제안_일괄업로드";
		   var headerNm = "";
		   
		   if($("#srchEntpCd").val() == ""){
				alert("검색조건에 파트너사를 선택 하세요.");
				$("#srchEntpCd").focus();
				return;
			}
		   
		   var srchStatus = $("#srchStatus").val();
		   var srchProdNm = $("#srchProdNm").val();
		   var srchFromDt = $("#srchFromDt").val();
		   var srchEndDt = $("#srchEndDt").val();
		   var srchL1Cd = $("#srchL1Cd").val();
		   var srchTeamCd = $("#srchTeamCd").val();
		   var srchEntpCd = $("#srchEntpCd").val();
			 
		   $("#hiddenSrchStatus").val(srchStatus);
		   $("#hiddenSrchProdNm").val(srchProdNm);
		   $("#hiddenSrchFromDt").val(srchFromDt);
		   $("#hiddenSrchEndDt").val(srchEndDt);
		   $("#hiddenSrchL1Cd").val(srchL1Cd);
		   $("#hiddenSrchTeamCd").val(srchTeamCd);
		   $("#hiddenSrchEntpCd").val(srchEntpCd);
		   
		   if (!confirm('엑셀 양식을 다운로드 하시겠습니까?')) {
				$("#pgmIdVal").val("");
				return;
			}
		   
			$("#fileName").val(encodeURIComponent(fileName));  // 파일명
			$("#optionVal").val(chkVal);  //양식 번호 
			
			form.target = "frameForExcel";
			form.action = '<c:url value="/edi/product/selectNewPropStoreExcelTmplDownload.do"/>';
			form.submit();
	   }
		
		//엑셀 업로드 
		function _eventExcelUpload(){
			var targetUrl = '<c:url value="/edi/comm/commExcelUploadPopupInit.do"/>';
			var heightVal = 350;
			var excelSysKnd = "400";	//엑셀 업로드 원가변경요청 구분값
			
			if($("#srchEntpCd").val() == ""){
				alert("검색조건에 파트너사를 선택 하세요.");
				$("#srchEntpCd").focus();
				return;
			}
			
			Common.centerPopupWindow(targetUrl+"?excelSysKnd="+excelSysKnd+"&entpCd="+$("#srchEntpCd").val(), 'excelUploadPopup', {width : 730, height : heightVal});
		}
		
		//엑셀 업로드 콜백 데이터 세팅
		function excelUploadcallBack(data){
			 var list = data.resultMap.list;
			 
			 // 기존 데이터 초기화 
			 $("#dataListbody").empty();
			   
			 // i인 인덱스로 맵핑시 제외조건에서도 인덱스가 포함되므로 rnum은 따로 세팅
			  var dataRnum = 1;
			 
			 
				// 컨트롤러에서 가져온 업체 코드 리스트
			    var vendorList = [];
			    <c:forEach var="venodr" items="${vendorList}">
			   		vendorList.push({
			            venCd: "${venodr.venCd}",
			            venNm: "${venodr.venNm}"
			        });
		    	</c:forEach>
				
			    // 컨트롤러에서 가져온 팀 코드 -----------------------------------
			    var teamList = [];

			    <c:forEach var="team" items="${teamList}">
			        teamList.push({
			            teamCd: "${team.teamCd}",
			            teamNm: "${team.teamNm}"
			        });
			    </c:forEach>
			    //--------------------------------------------------------
			 
			 
			 for (var i = 0; i < list.length; i++) {
			        var newRowData = {};
			    
				        newRowData.rnum = dataRnum;  // 현재 데이터의 순번
				        newRowData.propRegNo = "";
						newRowData.prdtStatus = "";
						
						newRowData.venCd = list[i].data1 || "";  // 파트너사
						newRowData.teamCd = list[i].data2 || "";  // 팀
						newRowData.l1Cd = list[i].data3 || "";  // 대분류
						newRowData.prodShortNm = list[i].data4; //상품명
						
				        newRowData.prodStandardNm = list[i].data5 || ""; // 규격
				        newRowData.norProdPcost = ($.trim(list[i].data6) != "")?setComma(list[i].data6):"";
				        newRowData.prodSellPrc = ($.trim(list[i].data7) != "")?setComma(list[i].data7):""; // 판매가
				        newRowData.prftRate = list[i].data8 || "";	//이익율
				        newRowData.norSesnCd = list[i].data9 || ""; // 상품구분
				        newRowData.prdtRt = list[i].data10 || ""; // 루트
				        newRowData.inrcntQty = ($.trim(list[i].data11) != "")?setComma(list[i].data11):""; // 입수
				        newRowData.delvPsbtDd = formatToYyyyMmDd(list[i].data12 || ""); // 변경시작일시
				        
				        newRowData.sellCd = list[i].data13 || ""; // 유사(타겟)판매코드
				        newRowData.sellChanCd = list[i].data14 || ""; // 판매채널
				        
				        newRowData.frzCd = list[i].data15 || ""; // 상온구분
				        newRowData.trgtSaleCurr = ($.trim(list[i].data16) != "")?setComma(list[i].data16):""; // 목표매출
				        newRowData.prdtSpecRange = list[i].data17 || ""; // 상품특성(가격)
				        newRowData.prdtSpecAge = list[i].data18 || ""; // 상품특성(연령)
				        newRowData.prdtSpecSex = list[i].data19 || ""; // 상품특성(성별)
					    newRowData.waers = list[i].data20 || ""; // 통화구분
				        newRowData.prdtReqYn  = "";
				        newRowData.imgId  = "";
				        newRowData.regDt  = "";
				        
// 				        console.log("dataList :" + list[i].data1 + ","+ list[i].data2 + ","+ list[i].data3 + ","+ list[i].data4 + ","+ list[i].data5 + ","+ list[i].data6 + ","+ list[i].data7 + ","+ list[i].data8 + ","+ list[i].data9 + "," + list[i].data10 + "," + list[i].data11+ "," + list[i].data12+ "," + list[i].data13+ "," + list[i].data14+ "," + list[i].data15+ "," + list[i].data16+ "," + list[i].data17+ "," + list[i].data18+ "," + list[i].data19);
				        // 템플릿에 가져온 데이터 맵핑
				        $("#dataListTemplate").tmpl(newRowData).appendTo("#dataListbody");

				        //생성된 로우 지정 
			            var lastRow = $("#dataListbody tr:last");
			            
				        
			        	// 판매채널 값 세팅 
			       /*  	if(newRowData.sellChanCd != null){
			        		var checkedValues = newRowData.sellChanCd;
			        		var rnum = newRowData.rnum;
			        		
				            if (checkedValues) {
				                var checkedArray = checkedValues.split(","); // 체크된 값 배열로 변환
				                
				                var length = $('input:checkbox[id="sellChanCd'+ rnum + '"]').length
				                
				                 $('input:checkbox[id="sellChanCd'+ rnum + '"]').each(function() {     
									if(checkedArray.includes($(this).val())){
				                	   this.checked = true; //checked 처리      
				                	} 
				                }); 
				            }
			        	}  */
				        
				        
			            // 셀렉트 박스 동적 생성 (팀 셀렉트 박스)
					    var teamSelect = lastRow.find("select[name='teamCd']");
			            teamSelect.empty(); // 기존 옵션 초기화
			            teamSelect.append('<option value="">선택</option>');

			            for (var j = 0; j < teamList.length; j++) {
			            	if (newRowData.teamCd == teamList[j].teamCd) {
				                teamSelect.append('<option value="' + teamList[j].teamCd + '" selected>' + teamList[j].teamNm + '</option>');
				            } else {
				                teamSelect.append('<option value="' + teamList[j].teamCd + '">' + teamList[j].teamNm + '</option>');
				            }
			            }
			            
			         	// 셀렉트 박스 동적 생성 (업체 셀렉트 박스)
			        	var venSelect = lastRow.find("select[name='venCd']");
			        	venSelect.empty(); // 기존 옵션 초기화
			        	venSelect.append('<option value="">선택</option>');

			            for (var j = 0; j < vendorList.length; j++) {
			            	if (newRowData.venCd == vendorList[j].venCd) {
			            		venSelect.append('<option value="' + vendorList[j].venCd + '" selected>' + vendorList[j].venNm + '</option>');
				            } else {
				            	venSelect.append('<option value="' + vendorList[j].venCd + '">' + vendorList[j].venNm + '</option>');
				            }
			            }
			            
			        	//루트구분 값 세팅 
			        	if(newRowData.prdtRt != null){
			        		$("#prdtRt" + newRowData.rnum).val(newRowData.prdtRt);
			        	}
			        	
			        	//상온구분 값 세팅
			        	if(newRowData.frzCd != null){
			        		$("#frzCd" + newRowData.rnum).val(newRowData.frzCd);
			        	}
			        	
			        	//일반 시즌 값 세팅 
			        	if(newRowData.norSesnCd != null){
			        		$("#norSesnCd" + newRowData.rnum).val(newRowData.norSesnCd);
			        	}
			            
			        	//통화구분값 세팅
			        	if(newRowData.waers != null){
			        		$("#waers" + newRowData.rnum).val(newRowData.waers);
			        	}
			        	
			        	//판매채널 값 세팅
			        	if(newRowData.sellChanCd != null){
			        		$("#sellChanCd" + newRowData.rnum).val(newRowData.sellChanCd);
			        	}
			        	
			            
			            /* 조회된 팀 정보를 기반으로 조회된 대분류 맵핑 */
			            if(newRowData.teamCd != null && newRowData.teamCd != ""){
			            	eventTempleteL1List(newRowData.teamCd,newRowData.l1Cd,newRowData.rnum,newRowData.prdtReqYn,newRowData.prdtStatus)
			            }
			            
			            
			            //판매채널 체크박스 체크 
				/* 		if (newRowData.sellChanCd) {
						    var sellChanArr = newRowData.sellChanCd.split(",");
						    for (var k = 0; k < sellChanArr.length; k++) {
						        var chkId = "#sellChanCd" + newRowData.rnum + "_" + sellChanArr[k];
						        lastRow.find(chkId).prop("checked", true);
						    }
						} */
			            
			            //rnum 번호 상승을 위한 로직
						dataRnum++;
			        
			    }
		}
		
		
		//날짜검증 및 날짜 변환 
		function formatToYyyyMmDd(dateStr) {
		    if (!dateStr) return "";

		    // 숫자만 남김
		    var cleanDate = dateStr.replace(/[^0-9]/g, "");

		    if (cleanDate.length === 8) {
		        // yyyymmdd -> yyyy-mm-dd
		        return cleanDate.replace(/(\d{4})(\d{2})(\d{2})/, "$1-$2-$3");
		    } else if (cleanDate.length === 6) {
		        // yymmdd -> 20yy-mm-dd (단순 예시)
		        return cleanDate.replace(/(\d{2})(\d{2})(\d{2})/, "20$1-$2-$3");
		    } else {
		        return ""; // 처리할 수 없는 형식
		    }
		}
		
		//업체코드별 구매조직 체크
		function eventSelChkVenZzorgInfo(obj, venCd, selChanCd){
			var paramInfo	=	{};
			paramInfo["venCd"] = $.trim(venCd);			//업체코드
			
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/product/selectVendorZzorgInfo.json"/>',
				data : JSON.stringify(paramInfo),
				success : function(data) {
					//협력사별 구매조직 셋팅 
					fncCtrlVenZzorgInfo(obj, data, selChanCd);
				}
			});
			
		}
		
		//업체코드 별 사용가능 계열사 & 구매조직
		function fncCtrlVenZzorgInfo(obj, json, selChanCd){
			if(obj == undefined || obj == null) return;
			
			//대상 Tr
			const tgObj = $(obj).find("select[name='sellChanCd']");
			tgObj.find("option[value!='']").remove();
			
			//업무에서 사용 가능한 계열사정보
			const propZzorgs = ["MT", "SP"];
			
			//반환데이터 있을 경우,
			if(json != null){
				var venZzorgInfo = json.venZzorgs;		//협력업체코드별 계열사정보
				
				if(venZzorgInfo == undefined || venZzorgInfo == null || venZzorgInfo.length < 1) return;
				
				//업무에서 사용 가능한 계열사를 해당 업체코드로 모두 사용할 수 있는지 확인
				const isAll = propZzorgs.every(val => venZzorgInfo.includes(val));
				if(isAll) venZzorgInfo.push("ALL");
				
				//selectbox option 구성
				let selBoxOpts = "";
				<c:forEach var="list" items="${propChanCodes}">
					if(venZzorgInfo.includes("<c:out value='${list.ZZORG}'/>")){
						selBoxOpts += "<option value='<c:out value='${list.CODE_ID}'/>'><c:out value='${list.CODE_NAME}'/></option>";
					}
				</c:forEach>
				
				if(selBoxOpts == "") return;
				
				tgObj.append(selBoxOpts);
			
				if($.trim(selChanCd) != ""){
					tgObj.val($.trim(selChanCd));
				}
			}
		}
		
	</script>
	
	
	<!-- DATA LIST -->
	<script id="dataListTemplate" type="text/x-jquery-tmpl">
		<tr class="r1" bgcolor=ffffff>
			<td align="center" class="chkGbn">
				<input type="checkbox" name="chkPropStore" id="chkPropStore\${rnum}" value="Y" />
				<input type ="hidden" name = "rnum"  			id="rnum\${rnum}" value="<c:out value="\${rnum}"/>" />
				<input type ="hidden" name = "propRegNo"  		id="propRegNo\${rnum}" value="<c:out value="\${propRegNo}"/>" />
				<input type ="hidden" name = "prdtReqYn"  		id="prdtReqYn\${rnum}" value="<c:out value="\${prdtReqYn}"/>" />
				<input type ="hidden" name = "orgFileNm"  		id="orgFileNm\${rnum}" value="<c:out value="\${orgFileNm}"/>" />
				<input type ="hidden" name = "saveFileNm"  		id="saveFileNm\${rnum}" value="<c:out value="\${saveFileNm}"/>" />
				<input type ="hidden" name = "fileSize"  		id="fileSize\${rnum}" value="<c:out value="\${fileSize}"/>" />
				<input type ="hidden" name = "filePath"  		id="filePath\${rnum}" value="<c:out value="\${filePath}"/>" />
				<input type ="hidden" name = "imgId"  			id="imgId\${rnum}" value="<c:out value="\${imgId}"/>" />
				<input type ="hidden" name = "prdtSpec"  		id="prdtSpec\${rnum}" value="<c:out value="\${prdtSpec}"/>" />
				<input type ="hidden" name = "prdtStatus"  		id="prdtStatus\${rnum}" value="<c:out value="\${prdtStatus}"/>" />
				<input type ="hidden" name = "orgImgId"  		id="orgImgId\${rnum}" value="<c:out value="\${orgImgId}"/>" />
				<input type ="hidden" name = "prdtSpecRange"  	id="prdtSpecRange\${rnum}" value="<c:out value="\${prdtSpecRange}"/>" />
				<input type ="hidden" name = "prdtSpecAge"  	id="prdtSpecAge\${rnum}" value="<c:out value="\${prdtSpecAge}"/>" />
				<input type ="hidden" name = "prdtSpecSex"  	id="prdtSpecSex\${rnum}" value="<c:out value="\${prdtSpecSex}"/>" />
			</td>
			<td align="center">
				{%if prdtStatus == "" || prdtStatus == null%}
					-
				{%else%}
					{%if prdtStatus == "0" %}
						{%if prdtReqYn == "Y"%}
						제안요청				
						{%else%}
						파트너사 등록
						{%/if%}
					{%elif prdtStatus == "1" %}
						반려
					{%else%}
						승인
					{%/if%}	
				{%/if%}
			</td>
			<td align="center">
			{%if prdtReqYn == "Y" && (prdtStatus == '2' || prdtStatus == '0')%}
				<select name="venCd" disabled="disabled" style="width:150px;">
            	</select>
			{%else%}
				<select name="venCd" style="width:150px;">
            	</select>
			{%/if%}
			</td>
			<td align="center">
			{%if prdtReqYn == "Y" && (prdtStatus == '2' || prdtStatus == '0')%}
				<select name="teamCd" id="teamCd\${rnum}" disabled="disabled" style="width:150px;">
            	</select>
			{%else%}
				<select name="teamCd" id="teamCd\${rnum}" style="width:150px;">
            	</select>
			{%/if%}
			</td>
			<td align="center">
			{%if prdtReqYn == "Y" && (prdtStatus == '2' || prdtStatus == '0')%}
				<select id="l1Cd\${rnum}" name="l1Cd" disabled="disabled" style="width:150px;">
				</select>
			{%else%}
				<select id="l1Cd\${rnum}" name="l1Cd" style="width:150px;">
					<option value="" \${l1Cd == '' ? 'selected' : ''}>선택</option>
				</select>
			{%/if%}
			</td>
			<td align="center">
			{%if prdtReqYn == "Y" && (prdtStatus == '2' || prdtStatus == '0')%}
				<input type="text" name="prodShortNm" id="prodShortNm\${rnum}" disabled="disabled"  value="<c:out value="\${prodShortNm}"/>" maxlength="100"/>
			{%else%}
				<input type="text" name="prodShortNm" id="prodShortNm\${rnum}"  value="<c:out value="\${prodShortNm}"/>" maxlength="100"/>
			{%/if%}
			</td>
			<td align="center">
			{%if prdtReqYn == "Y" && (prdtStatus == '2' || prdtStatus == '0')%}
					<input type="text" name="prodStandardNm" id="prodStandardNm\${rnum}" disabled="disabled"  value="<c:out value="\${prodStandardNm}"/>" maxlength="30"/>
			{%else%}
					<input type="text" name="prodStandardNm" id="prodStandardNm\${rnum}"  value="<c:out value="\${prodStandardNm}"/>" maxlength="30"/>
			{%/if%}
			</td>
			<td align="center">
			{%if prdtReqYn == "Y" && (prdtStatus == '2' || prdtStatus == '0')%}
					<input type="text" name="norProdPcost" class="priceNorFiled amt" id="norProdPcost\${rnum}" maxlength="10" oninput="this.value=this.value.replace(/\D/g,'')"  style="text-align:right;" disabled="disabled"  value="<c:out value="\${norProdPcost}"/>" maxlength="9"/>
			{%else%}
					<input type="text" name="norProdPcost" class="priceNorFiled amt" id="norProdPcost\${rnum}" maxlength="10" oninput="this.value=this.value.replace(/\D/g,'')"  style="text-align:right;"  value="<c:out value="\${norProdPcost}"/>" maxlength="9"/>
			{%/if%}
			</td>	
			<td align="center">
			{%if prdtReqYn == "Y" && (prdtStatus == '2' || prdtStatus == '0')%}
					<input type="text" name="prodSellPrc" class="priceSelFiled amt" id="prodSellPrc\${rnum}" maxlength="10" oninput="this.value=this.value.replace(/\D/g,'')"  style="text-align:right;" disabled="disabled" value="<c:out value="\${prodSellPrc}"/>" maxlength="9"/>
			{%else%}
					<input type="text" name="prodSellPrc" class="priceSelFiled amt" id="prodSellPrc\${rnum}" maxlength="10" oninput="this.value=this.value.replace(/\D/g,'')"  style="text-align:right;" value="<c:out value="\${prodSellPrc}"/>" maxlength="9"/>
			{%/if%}
			</td>
			<td align="center">
			{%if prdtReqYn == "Y" && (prdtStatus == '2' || prdtStatus == '0')%}
					<input type="text" name="prftRate" id="prftRate\${rnum}" style="text-align:right;"  disabled="disabled" maxlength="10" onkeyup="inputPercentFormat(this);"  value="<c:out value="\${prftRate}"/>" /> %
			{%else%}
					<input type="text" name="prftRate" id="prftRate\${rnum}" style="text-align:right;"  disabled="disabled" maxlength="10" onkeyup="inputPercentFormat(this);"  value="<c:out value="\${prftRate}"/>" /> %
			{%/if%}
			</td>
			<td align="center">
			{%if prdtReqYn == "Y" && (prdtStatus == '2' || prdtStatus == '0')%}
				<html:codeTag objId="norSesnCd\${rnum}" objName="norSesnCd"  width="150px;"  parentCode="SSFG"  comType="SELECT" dataType="NTCPCD" orderSeqYn="Y" defName="선택" formName="form"/>
			{%else%}
				<html:codeTag objId="norSesnCd\${rnum}" objName="norSesnCd"  width="150px;"  parentCode="SSFG"  comType="SELECT"  dataType="NTCPCD" orderSeqYn="Y" defName="선택" formName="form"/>
			{%/if%}
			</td>
			<td align="center">
			{%if prdtReqYn == "Y" && (prdtStatus == '2' || prdtStatus == '0')%}
				<html:codeTag objId="prdtRt\${rnum}" objName="prdtRt"  width="150px;"  parentCode="SRR04"  comType="SELECT" orderSeqYn="Y" defName="선택" formName="form"/>
			{%else%}
				<html:codeTag objId="prdtRt\${rnum}" objName="prdtRt" width="150px;"  parentCode="SRR04"  comType="SELECT" orderSeqYn="Y" defName="선택" formName="form"/>
			{%/if%}
			</td>
			<td align="center">
			{%if prdtReqYn == "Y" && (prdtStatus == '2' || prdtStatus == '0')%}
				<input type="text" class="amt" oninput="this.value=this.value.replace(/\D/g,'')" name="inrcntQty" id="inrcntQty\${rnum}" style="text-align:right;" disabled="disabled" value="<c:out value="\${inrcntQty}"/>" maxlength="9"/>
			{%else%}
				<input type="text" class="amt" oninput="this.value=this.value.replace(/\D/g,'')" name="inrcntQty" id="inrcntQty\${rnum}" style="text-align:right;" value="<c:out value="\${inrcntQty}"/>" maxlength="9"/>
			{%/if%}
			</td>
			<td align="center">
			{%if prdtReqYn == "Y" && (prdtStatus == '2' || prdtStatus == '0')%}
				<input type="text" name="delvPsbtDd" id="delvPsbtDd\${rnum}" disabled="disabled" value="<c:out value="\${delvPsbtDd}"/>" />
			{%else%}
				{%if delvPsbtDd == "" || delvPsbtDd == null%}
					<input type="text" name="delvPsbtDd" id="delvPsbtDd\${rnum}" disabled="disabled" value="<c:out value="\${delvPsbtDd}"/>" />
					<img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.delvPsbtDd\${rnum}');" style="cursor: pointer;" />
				{%else%}
					<input type="text" name="delvPsbtDd" id="delvPsbtDd\${rnum}" disabled="disabled"  value="<c:out value="\${delvPsbtDd}"/>" />
					<img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.delvPsbtDd\${rnum}');" style="cursor: pointer;" />
				{%/if%}
			{%/if%}
			</td>
			<td align="center">
			{%if prdtReqYn == "Y" && (prdtStatus == '2' || prdtStatus == '0')%}
				<input type="text" name="sellCd" id="sellCd\${rnum}" title="판매코드를 입력해주세요(없을경우 미입력)" placeholder="판매코드를 입력해주세요(없을경우 미입력)"; oninput="this.value=this.value.replace(/\D/g,'')" disabled="disabled"  value="<c:out value="\${sellCd}"/>" maxlength="15"/>
			{%else%}
				<input type="text" name="sellCd" id="sellCd\${rnum}" title="판매코드를 입력해주세요(없을경우 미입력)"  placeholder="판매코드를 입력해주세요(없을경우 미입력)"; oninput="this.value=this.value.replace(/\D/g,'')" value="<c:out value="\${sellCd}"/>" maxlength="15"/>
			{%/if%}
			</td>
			<td align="center">
			{%if prdtReqYn == "Y" && (prdtStatus == '2' || prdtStatus == '0')%}
				<select id="sellChanCd\${rnum}" name="sellChanCd" width="150px" disabled>
			{%else%}
				<select id="sellChanCd\${rnum}" name="sellChanCd" width="150px">
			{%/if%}
					<option value="">선택</option>
					<%-- list 조회 시 함께 조회해 온 파트너사코드별 선택가능 채널 setting --%>
					{%if selBoxChanCds%}
					{{each(idx, list) selBoxChanCds}}
						<option value="\${list.CODE_ID}">
							<c:out value='\${list.CODE_NAME}'/>
						</option>
					{{/each}}
					{%/if%}
			</select>
			<%-- {%if prdtReqYn == "Y" && (prdtStatus == '2' || prdtStatus == '0')%}
					<html:codeTag objId="sellChanCd\${rnum}" objName="sellChanCd"  width="150px;" dataType="NTCPCD"  parentCode="CHAFG"  comType="SELECT" orderSeqYn="Y"  formName="form"/>
			{%else%}
					<html:codeTag objId="sellChanCd\${rnum}" objName="sellChanCd"  width="150px;" dataType="NTCPCD"  parentCode="CHAFG"  comType="SELECT" orderSeqYn="Y"  formName="form"/>
			{%/if%}
			--%>
			</td>
			<td align="center">
			{%if prdtReqYn == "Y" && (prdtStatus == '2' || prdtStatus == '0')%}
				<html:codeTag objId="frzCd\${rnum}" objName="frzCd" width="150px;"   parentCode="PRD12"  comType="SELECT" orderSeqYn="Y" defName="선택" formName="form"/>
			{%else%}
				<html:codeTag objId="frzCd\${rnum}" objName="frzCd" width="150px;"   parentCode="PRD12"  comType="SELECT" orderSeqYn="Y" defName="선택" formName="form"/>
			{%/if%}
			</td>
			<td align="center">
			{%if prdtReqYn == "Y" && (prdtStatus == '2' || prdtStatus == '0')%}
				<input type="text" name="trgtSaleCurr" id="trgtSaleCurr\${rnum}"  class="amt" oninput="this.value=this.value.replace(/\D/g,'')" style="text-align:right;" disabled="disabled" value="<c:out value="\${trgtSaleCurr}"/>" maxlength="10"/>
			{%else%}
				<input type="text" name="trgtSaleCurr" id="trgtSaleCurr\${rnum}"  class="amt" oninput="this.value=this.value.replace(/\D/g,'')" style="text-align:right;" value="<c:out value="\${trgtSaleCurr}"/>" maxlength="10" />
			{%/if%}
			</td>
			<td align="center">
			{%if prdtReqYn == "Y" && (prdtStatus == '2' || prdtStatus == '0')%}
				<html:codeTag objId="waers\${rnum}" objName="waers" width="150px;"   parentCode="PRD40"  comType="SELECT"  selectParam="KRW"  formName="form" />
			{%else%}
				<html:codeTag objId="waers\${rnum}" objName="waers" width="150px;"   parentCode="PRD40"  comType="SELECT"  selectParam="KRW"  formName="form" />
			{%/if%}
			</td>
			<td align="center">
				<a href="javascript: prodDetailRegPop('\${propRegNo || ''}',  '\${rnum}');"  id="prodDetailRegPop\${rnum}" class="btn"><span>등록하기</span></a>
				<a href="javascript: prodDetailRegPop('\${propRegNo || ''}',  '\${rnum}','\${prdtSpec}');"  id="prodDetailSelPop\${rnum}" class="btn"><span>상세보기</span></a>
			</td>
			<td align="center">
			{%if prdtReqYn == "Y" && (prdtStatus == '2' || prdtStatus == '0')%}
				{%if imgId == "" || imgId == null%}
					-
				{%else%}
					<a href="javascript:void(0)" onclick="imgFilePreView(this, '<c:out value="\${imgId}"/>', '<c:out value="\${imgfileUrl}"/>')"  class="btn" name="fileNameLink" id="fileNameLink\${rnum}" ><span>상세보기</span></a>
					<input type="file"  name="prodImgInput"  id="prodImgInput\${rnum}" class="prodImgInput" style="display: none;" accept=".jpg">
            		<input type="hidden" name="imgfileUrl" id="imgfileUrl\${rnum}" value="<c:out value="\${imgfileUrl}" />" />
				{%/if%}
			{%else%}
				{%if imgId == "" || imgId == null%}
					<a href="javascript:prodImgFileUpload(\${rnum});" class="btn" id="uploadBtn\${rnum}"><span>등록하기</span></a>
					<a href="javascript:void(0)" onclick="imgFilePreView(this, '<c:out value="\${imgId}"/>', '<c:out value="\${imgfileUrl}"/>')" class="btn" name="fileNameLink" id="fileNameLink\${rnum}" style="display: none;"></a>
            		<input type="file"  name="prodImgInput"  id="prodImgInput\${rnum}" class="prodImgInput" style="display: none;" accept=".jpg">
            		<input type="hidden" name="imgfileUrl" id="imgfileUrl\${rnum}" value="<c:out value="\${imgfileUrl}" />" />
            		<img id="prodImgPreview\${rnum}" style="display:none; max-width:200px; margin-top:10px;" />
				{%else%}
					<a href="javascript:prodImgFileUpload(\${rnum});" class="btn" id="uploadBtn\${rnum}"><span>등록하기</span></a>
					<a href="javascript:void(0)" onclick="imgFilePreView(this, '<c:out value="\${imgId}"/>', '<c:out value="\${imgfileUrl}"/>')" class="btn" name="fileNameLink" id="fileNameLink\${rnum}"><span>상세보기</span></a>
					<input type="file"  name="prodImgInput"  id="prodImgInput\${rnum}" class="prodImgInput" style="display: none;" accept=".jpg">
            		<input type="hidden" name="imgfileUrl" id="imgfileUrl\${rnum}" value="<c:out value="\${imgfileUrl}" />" />
				{%/if%}
			{%/if%}
        	</td>
			<td align="center">
				{%if regDt == "" || regDt == null%}
					-
				{%else%}
					<c:out value="\${regDt}"/>
				{%/if%}
			</td>
		</tr>
	</script>
</head>
<body>
	<div id="content_wrap">
		<div>
			<form name="searchForm" id="searchForm">
				<input type="hidden" id="pageIndex" name="pageIndex" value="<c:out value="${param.pageIndex}" />" />
				<div id="wrap_menu">
					<div class="wrap_search">
						<div class="bbs_search">
							<ul class="tit">
								<li class="tit">신상품입점제안등록</li>
								<li class="btn">
									<a href="#" class="btn" onclick="_eventSearch()"><span>조회</span></a>
								</li>
							</ul>
							<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
								<colgroup>
									<col style="width:15%" />
									<col style="width:30%" />
									<col style="width:15%" />
									<col style="width:30%" />
								</colgroup>
								<tr>
									<th>파트너사</th>
									<td>
										<html:codeTag objId="srchEntpCd" objName="srchEntpCd" width="150px;"  dataType="CP" comType="SELECT" formName="form" defName="전체" />
									</td>
									<th>팀</th>
									<td>
										<select id="srchTeamCd" name="srchTeamCd" class="required" style="width: 150px;">
											<option value="">
												<spring:message code="button.common.choice" /></option>
												<c:forEach items="${teamList}" var="teamList" varStatus="index">
													<option value="${teamList.teamCd}">${teamList.teamNm}</option>
												</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<th>대분류</th>
									<td>
										<select id="srchL1Cd" name="srchL1Cd" class="required" style="width: 150px;">
											<option value="">
												<spring:message code="button.common.choice" /></option>
										</select>
									</td>
									<th>조회기간</th>
									<td>
										<c:if test="${empty param.srchFromDt}">
											<input type="text" class="day" name="srchFromDt" id="srchFromDt" style="width:80px;" value="<c:out value='${srchFromDt}'/>"> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchFromDt');" style="cursor:hand;" />
											~
											<input type="text" class="day" name="srchEndDt" id="srchEndDt" style="width:80px;" value="<c:out value='${srchEndDt}'/>"> 	<img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchEndDt');"  style="cursor:hand;" />
										</c:if>
										<c:if test="${not empty param.srchFromDt}">
											<input type="text" class="day" name="srchFromDt" id="srchFromDt" style="width:80px;" value="<c:out value='${param.srchFromDt}'/>"> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchFromDt');" style="cursor:hand;"/>
											~
											<input type="text" class="day" name="srchEndDt" id="srchEndDt" style="width:80px;" value="<c:out value='${param.srchEndDt}'/>"> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchEndDt');" style="cursor:hand;" />
										</c:if>
									</td>
								</tr>
								<tr>
									<th>상품명</th>
									<td>
										<input type="text" name="srchProdNm" id="srchProdNm"  style="width:40%;"  />
									</td>
									<th>진행상태</th>
									<td>
										<div style="float:left;" id="srchIngStatus">
											<select name="srchStatus" id="srchStatus">
												<option value="">전체</option>
												<option value="0">파트너사 등록/요청</option>
												<option value="1">반려</option>
												<option value="2">승인</option>
											</select>
										</div>
									</td>
								</tr>
							</table>
						</div>
					</div>
					<div class="wrap_con">
						<div class="bbs_list">
							<ul class="tit">
								<li class="tit">대상상품리스트</li>
								<li class="btn">
									<a href="javascript:void(0);" class="btn" onclick="_eventExcelTmplDown()"><span>Excel 양식다운로드</span></a>	
									<a href="javascript:void(0);" class="btn" onclick="_eventExcelUpload()"><span>Excel 업로드</span></a>	
									<a href="javascript:void(0);" class="btn" onclick="_eventExcelDown()"><span>Excel 다운로드</span></a>	
									<a href="javascript:void(0);" class="btn" onclick="_eventRequest()"><span>제안요청</span></a>		
									<a href="javascript:void(0);" class="btn" onclick="_eventAddRow()"><span>행추가</span></a>
									<a href="javascript:void(0);" class="btn" onclick="_eventSave()"><span>저장</span></a>		
									<a href="javascript:void(0);" class="btn" onclick="_eventNewDelete()"><span>삭제</span></a>		
								</li>
							</ul>
							<div style="width:100%; height:458px;overflow-x:hidden; overflow-y:scroll; overflow-x:scroll;  table-layout:fixed;white-space:nowrap;">
								<table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=3800px; bgcolor=efefef>
									<colgroup>
										<col style="width:30px"/>
										<col style="width:100px"/>
										<col style="width:150px"/>
										<col style="width:150px"/>
										<col style="width:150px;"/>
										<col style="width:200px"/>
										<col style="width:100px"/>
										<col style="width:120px"/>
										<col style="width:120px"/>
										<col style="width:100px;"/>
										<col style="width:100px"/>
										<col style="width:150px"/>
										<col style="width:150px"/>
										<col style="width:150px"/>
										<col style="width:150px"/>
										<col style="width:150px"/>
										<col style="width:150px"/>
										<col style="width:150px"/>
										<col style="width:150px"/>
										<col style="width:150px"/>
										<col style="width:150px"/>
										<col style="width:100px"/>
									</colgroup>
									<tr bgcolor="#e4e4e4">
										<th>
											<input type="checkbox" name="cbx_chkAll" id="cbx_chkAll" value="Y" />
										</th>
										<th>상태</th>
										<th>파트너사코드</th>
										<th>팀</th>
										<th>대분류</th>
										<th>쇼카드상품명</th>
										<th>규격</th>
										<th>원가</th>
										<th>판매가</th>
										<th>이익율</th>
										<th>상품구분</th>
										<th>루트</th>
										<th>입수</th>
										<th>납품가능일자</th>
										<th>유사(타겟)상품</th>
										<th>판매채널</th>
										<th>상온구분</th>
										<th>목표매출</th>
										<th>통화키</th>
										<th>상품특성</th>
										<th>이미지등록</th>
										<th>등록일자</th>
									</tr>
									<tbody id="dataListbody" />
								</table>
							</div>
						</div>
					</div>
				</div>
				
				<!-- Pagging Start ---------->			
			<!-- 	<div id="paging_div">
			        <ul class="paging_align" id="paging-ul" style="width: 400px"></ul>
				</div> -->
				<!-- Pagging End ---------->
			</form>
			<form name="imgForm" id="imgForm" action="${ctx}/edi/product/propImgPreViewPopup.do" method="post">
			    <input type="hidden" id="hiddenImgUrl" name="hiddenImgUrl" value="">
			    <input type="hidden" id="hiddenImgId" name="hiddenImgId" value="">
			</form>
			<form name="excelForm" id="excelForm">
				<iframe name="frameForExcel" style="display:none;"></iframe>
				<input type="hidden" name="fileName" id="fileName"/>
				<input type="hidden" name="optionVal" id="optionVal"/>
				<input type="hidden" name="pgmIdVal" id="pgmIdVal"/>
				<input type="hidden" id="hiddenSrchStatus" name="hiddenSrchStatus" value="">
			    <input type="hidden" id="hiddenSrchProdNm" name="hiddenSrchProdNm" value="">
			    <input type="hidden" id="hiddenSrchFromDt" name="hiddenSrchFromDt" value="">
			    <input type="hidden" id="hiddenSrchEndDt" name="hiddenSrchEndDt" value="">
			    <input type="hidden" id="hiddenSrchL1Cd" name="hiddenSrchL1Cd" value="">
			    <input type="hidden" id="hiddenSrchTeamCd" name="hiddenSrchTeamCd" value="">
			    <input type="hidden" id="hiddenSrchEntpCd" name="hiddenSrchEntpCd" value="">
			</form>
		</div>
		
		<!-- footer -->
		<div id="footer">
			<div id="footbox">
				<div class="msg" id="resultMsg"></div>
				<div class="location">
					<ul>
						<li>상품</li>
						<li>차세대_신규</li>
						<li class="last">신상품입점제안등록</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- footer //-->
	</div>
	
</body>
</html>
