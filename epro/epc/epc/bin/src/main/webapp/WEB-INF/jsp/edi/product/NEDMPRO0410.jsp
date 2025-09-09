<%--
	Page Name 	: NEDMPRO0410.jsp
	Description : 신상품 입점 제안 조회  
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

#content_wrap{
	width: 99%!important;
}

</style>
	<script type="text/javascript" >
		/* dom이 생성되면 ready method 실행 */
		$(document).ready(function() {

			
			$(document).on("click", "a[name='fileNameLink']", function (e) {
			    e.preventDefault();
			    const imgUrl = $(this).closest("tr").find("input[name='imgfileUrl']").val();
			    const imgId = $(this).closest("tr").find("input[name='imgId']").val();
			    imgFilePreView(imgId,imgUrl);
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

		                // 상세보기 버튼 활성화
		                fileNameLink
		                    .text("상세보기")
		                    .attr("style","padding:1px 10px 0 10px;")
		                    .attr("href", "javascript:imgFilePreView(imgId,imgUrlInput);") // 링크 막기
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
			var srchEntpCd = "<c:out value='${param.srchEntpCd}'/>";  //검색조건 협력업체코드
			var repVendorId = "<c:out value='${epcLoginVO.repVendorId}'/>";  //관리자로 로그인 해서 협력업체 갈아타기 로그인시 협력업체 코드
			if (srchEntpCd.replace(/\s/gi, '') ==  "") {
				$("#searchForm #srchEntpCd").val(repVendorId);
			} else {
				$("#searchForm #srchEntpCd").val(srchEntpCd);
			}
			
		
			
			
			//-----검색조건 팀 변경시 이벤트
			$("#searchForm select[name=srchTeamCd]").change(function() {

				//----- 대, 중, 소분류 초기화
				$("#l1Cd option").not("[value='']").remove();
				$("#grpCd option").not("[value='']").remove();

				_eventSelSrchL1List($(this).val().replace(/\s/gi, ''));
			});
			
			//-----템플릿 내  팀 변경시 이벤트
			$("#searchForm select[name=teamCd]").change(function() {

			//----- 대, 중, 소분류 초기화
			$("#l1Cd option").not("[value='']").remove();
			$("#grpCd option").not("[value='']").remove();

			__eventSelectL1List($(this).val().replace(/\s/gi, ''));
			});
		
		});
		
		
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
				async : false,
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
					
					//json 으로 호출된 결과값을 화면에 Setting
					_eventSetTbodyValue(data);
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
		    
			// 컨트롤러에서 가져온 상온구분 코드 리스트
		    var frzList = [];
		    <c:forEach var="item" items="${frzList}">
		    	frzList.push({
		            frzCd: "${item.CODE_ID}",
		            frzNm: "${item.CODE_NAME}"
		        });
		    </c:forEach>

		    if (data.length > 0) {
		        $("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
		        $("#paging-ul").html(json.contents);
		    	
				
		        for (var i = 0; i < data.length; i++) {
		        	  var rowNum = data[i].rnum;
		        	
		        	  
		        	  var prdtSpecValid = data[i].prdtSpecSex + data[i].prdtSpecAge+ data[i].prdtSpecRange;
		        	  
		        	  //상품소구특징 상세보기 버튼 , 등록하기 버튼 구분 
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
		            
		         	// 셀렉트 박스 동적 생성 (상온구분 셀렉트 박스)
		        	var frzSelect = $("#dataListbody tr").eq(i).find("select[name='frzCd']");
		        	frzSelect.empty(); // 기존 옵션 초기화
		        	frzSelect.append('<option value="">선택</option>');

		            for (var j = 0; j < frzList.length; j++) {
		            	if (data[i].frzCd == frzList[j].frzCd) {
		            		frzSelect.append('<option value="' + frzList[j].frzCd + '" selected>' + frzList[j].frzNm + '</option>');
			            } else {
			            	frzSelect.append('<option value="' + frzList[j].frzCd + '">' + frzList[j].frzNm + '</option>');
			            }
		            }
		            
		            // 채널 체크박스 체크
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
		         	
		         	
		            // select 박스 value 선택 
		            $("#venCd" + data[i].rnum).val(data[i].venCd);
		            $("#teamCd" + data[i].rnum).val(data[i].teamCd);
		            $("#l1Cd" + data[i].rnum).val(data[i].l1Cd);
		            $("#norSesnCd" + data[i].rnum).val(data[i].norSesnCd);
		            $("#sellChanCd" + data[i].rnum).val(data[i].sellChanCd);
		            $("#prdtRt" + data[i].rnum).val(data[i].prdtRt);
		            $("#frzCd" + data[i].rnum).val(data[i].frzCd);
		            $("#orgImgId" + data[i].rnum).val(data[i].imgId);
		            
		            //원가 인풋
		            var norProdPcostInput = $("#norProdPcost" + data[i].rnum);
		            
		            //판매가 인풋
		            var prodSellPrcInput = $("#prodSellPrc" + data[i].rnum);
		            
		            //이익율 인풋
		            var prftRateInput = $("#prftRate" + data[i].rnum);
					
		            //각 인풋갑 포맷--------------------------------------------
		            if (norProdPcostInput.length && norProdPcostInput.val()) {
		                norProdPcostInput.val(comma(uncomma(norProdPcostInput.val())));
		            }
		            if (prodSellPrcInput.length && prodSellPrcInput.val()) {
		                prodSellPrcInput.val(comma(uncomma(prodSellPrcInput.val())));
		            }
		            if (prftRateInput.length && prftRateInput.val()) {
		                prftRateInput.val((prftRateInput.val()) + "%");
		            }
		            //-----------------------------------------------------------
		            
		            
		            //납품가능일 문자열 -> 날짜 형식으로 변환 -----------------------------------
		            var delvPsbtDd = data[i].delvPsbtDd;
		            
		            delvPsbtDd = delvPsbtDd.replace(/(\d{4})(\d{2})(\d{2})/g, '$1-$2-$3');
		            
		            $("#delvPsbtDd" + data[i].rnum).val(delvPsbtDd);
		            //---------------------------------------------------------------
		            
		            if(data[i].teamCd != null && data[i].teamCd != ""){
		            	eventTempleteL1List(data[i].teamCd,data[i].l1Cd,data[i].rnum,data[i].prdtReqYn,data[i].prdtStatus)
		            }
		            
		        }
		    } else {
		        setTbodyNoResult("dataListbody", 20);
		        $("#paging-ul").html("");
		    }
		}
		
		
		//신규 입정 삼품 이미지 파일 업로드 
		function prodImgFileUpload(rowNum){
			 var rowNum =rowNum;
			 $("#prodImgInput"+rowNum).click();
		}
		
		//파일이미지 상세보기 
	 	function imgFilePreView(imgId , imgUrlInput) {
		    $("#hiddenImgUrl").val(imgUrlInput);
		    $("#hiddenImgId").val(imgId);

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
			
			//search 조회 화면 구분
			var screenFlag = "S";
			
			Common.centerPopupWindow(targetUrl+"?propRegNo="+propRegNo+"&rnum="+rnum+"&prdtSpecRange="+prdtSpecRange+"&prdtSpecSex="+prdtSpecSex+"&prdtSpecAge="+prdtSpecAge+"&screenFlag="+screenFlag, 'prodDetailRegPopup', {width : 730, height : heightVal});
		}
		
		//상품 소구 특성 등록 콜백
		function prodDetailcallBack(data) {
		    var callBackData = data;

		    var rowNum = callBackData.rnum;
		    var propRegNo = callBackData.propRegNo;
		    var seq = callBackData.seq;

		    // 필요한 값들을 배열에 담고, 빈 값은 제외한 후 join으로 연결
		    var prodDetailData = [
		        callBackData.priceFg,
		        callBackData.customAgeFg,
		        callBackData.customGenderFg
		    ]
		    .filter(Boolean) // 빈 값(빈 문자열, null, undefined) 제거
		    .join(","); // ,로 연결

		    // 값 입력
		    $("#prdtSpec" + rowNum).val(prodDetailData);

		    // 팝업 상태 변경
		    $("#prodDetailRegPop" + rowNum).hide();
		    $("#prodDetailSelPop" + rowNum).show();
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


	</script>
	
	
	<!-- DATA LIST -->
	<script id="dataListTemplate" type="text/x-jquery-tmpl">
		<tr class="r1" bgcolor=ffffff>
			<input type ="hidden" name = "rnum"  		id="rnum\${rnum}" 		value="<c:out value="\${rnum}"/>" />
			<input type ="hidden" name = "propRegNo"  	id="propRegNo\${rnum}" 	value="<c:out value="\${propRegNo}"/>" />
			<input type ="hidden" name = "prdtReqYn"  	id="prdtReqYn\${rnum}" 	value="<c:out value="\${prdtReqYn}"/>" />
			<input type ="hidden" name = "orgFileNm"  	id="orgFileNm\${rnum}" 	value="<c:out value="\${orgFileNm}"/>" />
			<input type ="hidden" name = "saveFileNm"  	id="saveFileNm\${rnum}" value="<c:out value="\${saveFileNm}"/>" />
			<input type ="hidden" name = "fileSize"  	id="fileSize\${rnum}" 	value="<c:out value="\${fileSize}"/>" />
			<input type ="hidden" name = "filePath"  	id="filePath\${rnum}" 	value="<c:out value="\${filePath}"/>" />
			<input type ="hidden" name = "imgfileUrl"  	id="imgfileUrl\${rnum}" value="<c:out value="\${imgfileUrl}"/>" />
			<input type ="hidden" name = "imgId"  		id="imgId\${rnum}" 		value="<c:out value="\${imgId}"/>" />
			<input type ="hidden" name = "prdtSpec"  	id="prdtSpec\${rnum}" 	value="<c:out value="\${prdtSpec}"/>" />
			<input type ="hidden" name = "prdtStatus"  	id="prdtStatus\${rnum}" value="<c:out value="\${prdtStatus}"/>" />
			<input type ="hidden" name = "orgImgId"  	id="orgImgId\${rnum}" 	value="<c:out value="\${orgImgId}"/>" />
			<input type ="hidden" name = "prdtSpecRange"  	id="prdtSpecRange\${rnum}" value="<c:out value="\${prdtSpecRange}"/>" />
			<input type ="hidden" name = "prdtSpecAge"  	id="prdtSpecAge\${rnum}" value="<c:out value="\${prdtSpecAge}"/>" />
			<input type ="hidden" name = "prdtSpecSex"  	id="prdtSpecSex\${rnum}" value="<c:out value="\${prdtSpecSex}"/>" />
			<td align="center">
					{%if prdtStatus == "0" %}
						파트너사 등록				
					{%elif prdtStatus == "1" %}
						반려
					{%else%}
						승인
					{%/if%}	
			</td>
			<td align="center">
				<select name="venCd" disabled="disabled" style="width:150px;">
            	</select>
			</td>
			<td align="center">
				<select name="teamCd" disabled="disabled" style="width:150px;">
            	</select>
			</td>
			<td align="center">
				<select id="l1Cd\${rnum}" name="l1Cd" disabled="disabled" style="width:150px;">
				</select>
			</td>
			<td align="center">
				<c:out value="\${prodShortNm}"/>
			</td>
			<td align="center">
					<c:out value="\${prodStandardNm}"/>
			</td>
			<td align="center">
				<c:out value="\${norProdPcost}"/>
			</td>	
			<td align="center">
				<c:out value="\${prodSellPrc}"/>
			</td>
			<td align="center">
					<c:out value="\${prftRate}"/>%
			</td>
			<td align="center">
				<select name="norSesnCd" disabled="disabled" style="width:150px;">
						<option value="1" \${norSesnCd == '1' ? 'selected' : ''}>일반</option>
						<option value="2" \${norSesnCd == '2' ? 'selected' : ''}>시즌</option>
					</select>
			</td>
			<td align="center">
				<select name="prdtRt" disabled="disabled" style="width:150px;">
						<option value="1" \${prdtRt == '1' ? 'selected' : ''}>루트</option>
					</select>
			</td>
			<td align="center">
				<c:out value="\${inrcntQty}"/>
			</td>
			<td align="center">
				<c:out value="\${delvPsbtDd}"/>
			</td>
			<td align="center">
				<c:out value="\${sellCd}"/>
			</td>
			<td align="center">
				<input type="checkbox" disabled="true" id="sellChanCd\${rnum}_KR02" name="sellChanCd" class="chk" value="KR02">마트
				<input type="checkbox" disabled="true" id="sellChanCd\${rnum}_KR03" name="sellChanCd" class="chk" value="KR03">MAXX
				<input type="checkbox" disabled="true" id="sellChanCd\${rnum}_KR04" name="sellChanCd" class="chk"  value="KR04">슈퍼
				<input type="checkbox" disabled="true" id="sellChanCd\${rnum}_KR09" name="sellChanCd" class="chk" value="KR09">오카도
			</td>
			<td align="center">
				<select name="frzCd" disabled="disabled" style="width:150px;">
            	</select>
			</td>
			<td align="center">
				<c:out value="\${trgtSaleCurr}"/>
			</td>
			<td align="center">
				<a href="javascript: prodDetailRegPop('\${propRegNo || ''}', '\${rnum}','\${prdtSpec}');"  id="prodDetailSelPop\${rnum}" class="btn"><span>상세보기</span></a>
			</td>
			<td align="center">
				{%if imgId == "" || imgId == null%}

				{%else%}
					<a href="javascript:imgFilePreView(\${imgId},\${imgfileUrl})" class="btn" name="fileNameLink" id="fileNameLink\${rnum}" target="_blank" ><span>상세보기</span></a>
					<input type="file"  name="prodImgInput"  id="prodImgInput\${rnum}" class="prodImgInput" style="display: none;" accept=".jpg">
            		<input type="hidden" name="imgfileUrl" id="imgfileUrl\${rnum}" value="<c:out value="\${imgfileUrl}" />" />
				{%/if%}
        	</td>
			<td align="center">
					<c:out value="\${regDt}"/>
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
								<li class="tit">검색조건</li>
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
									<col style="width:15%" />
									<col style="width:30%" />
								</colgroup>
								<tr>
									<th>파트너사</th>
									<td>
										<c:if test="${epcLoginVO.vendorTypeCd eq '06'}">
											<input type="text" name="srchEntpCd" id="srchEntpCd" readonly="readonly" readonly="readonly" style="width:40%;" />
											<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
										</c:if>
										<c:if test="${epcLoginVO.vendorTypeCd ne '06'}">
											<html:codeTag objId="srchEntpCd" objName="srchEntpCd" width="150px;"  dataType="CP" comType="SELECT" formName="form" defName="전체" />
										</c:if>
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
									<th>대분류</th>
									<td>
										<select id="srchL1Cd" name="srchL1Cd" class="required" style="width: 150px;">
											<option value="">
												<spring:message code="button.common.choice" /></option>
										</select>
									</td>
								</tr>
								<tr>
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

									<th>상품명</th>
									<td>
										<input type="text" name="srchProdNm" id="srchProdNm"  style="width:40%;"  />
									</td>
									<th>진행상태</th>
									<td>
										<div style="float:left;" id="srchIngStatus">
											<select name="srchStatus" id="srchStatus">
												<option value="">전체</option>
												<option value="0">파트너사등록</option>
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
								<li class="tit">검색내역 </li>
								<li class="btn">
									<a href="javascript:void(0);" class="btn" onclick="_eventExcelDown()"><span>Excel 다운로드</span></a>	
								</li>
							</ul>
							<div style="width:100%; height:458px;overflow-x:hidden; overflow-y:scroll; overflow-x:scroll;  table-layout:fixed;white-space:nowrap;">
								<table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=3800px; bgcolor=efefef>
									<colgroup>
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
										<col style="width:180px"/>
										<col style="width:150px"/>
										<col style="width:150px"/>
										<col style="width:150px"/>
										<col style="width:150px"/>
										<col style="width:100px"/>
									</colgroup>
									<tr bgcolor="#e4e4e4">
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
										<th>마트(슈퍼)공동여부</th>
										<th>상온구분</th>
										<th>목표매출</th>
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
				<div id="paging_div">
			        <ul class="paging_align" id="paging-ul" style="width: 400px"></ul>
				</div>
				<!-- Pagging End ---------->
			</form>
			<form name="imgForm" id="imgForm" action="${ctx}/edi/product/imgPreViewPopup.do" method="post">
			    <input type="hidden" id="hiddenImgUrl" name="hiddenImgUrl" value="">
			    <input type="hidden" id="hiddenImgId" name="hiddenImgId" value="">
			</form>
			<form name="excelForm" id="excelForm">
				<input type="hidden" id="hiddenSrchStatus" name="hiddenSrchStatus" value="">
			    <input type="hidden" id="hiddenSrchProdNm" name="hiddenSrchProdNm" value="">
			    <input type="hidden" id="hiddenSrchFromDt" name="hiddenSrchFromDt" value="">
			    <input type="hidden" id="hiddenSrchEndDt" name="hiddenSrchEndDt" value="">
			    <input type="hidden" id="hiddenSrchL1Cd" name="hiddenSrchL1Cd" value="">
			    <input type="hidden" id="hiddenSrchTeamCd" name="hiddenSrchTeamCd" value="">
			    <input type="hidden" id="hiddenSrchEntpCd" name="hiddenSrchEntpCd" value="">
			</form>
		</div>
	</div>
	
</body>
</html>
