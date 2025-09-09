<%@ page  pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%> 
<%--
	Page Name 	: SRMRST0021.jsp
	Description : 입점상담 결과확인 > 진행현황 상세화면
	Modification Information
	
	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2016.07.06		AN TAE KYUNG	최초생성
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<%-- <title><spring:message code=''/></title> --%>
<title>입점상담 결과 상세</title>

<script language="JavaScript">
	/* 화면 초기화 */
	$(document).ready(function() {
	});
	
	/* TAB 클릭 이벤트 */
	$(function () {
		$("ul.tabs li").click(function () {
			$("ul.tabs li").removeClass("active");
			$(this).addClass("active");
		});
	});
	
	/* 진행현황 리스트 */
	function fnList() {
		location.href="<c:url value="/edi/srm/SRMRST0020.do" />";
	}
</script>

</head>


<body>

<form name="MyForm"  id="MyForm" method="post">
	<div id="wrap">
		<div id="con_wrap">
			<div class="con_area">
				
				<!----- Menu Path Start ------------------------->
				<div class="div_tit">
					<navi>입점상담 결과 &gt; 진행현황(상세)</navi>
				</div>
				<div class="div_hline"></div>
				<p style="padding-top: 10px;" />
				<!----- Menu Path End ------------------------->
				
				<!----- 입점상담 이력/요청 정보 Start ------------------------->
				<div class="div_tit_dot">
					<div class="div_tit">
						<tt>입점상담 이력/요청 정보</tt>
						
						<btn>
							<input type="button" id="" name="" value="목록" class="btn_normal btn_black" onClick="fnList();">
						</btn>
					</div>
				</div>
				
				<div class="tbl_default">
					<table id="tblInfo" name="tblInfo">
						<colgroup>
							<col width="40" />
							<col width="100" />
							<col width="150" />
							<col width="80" />
							<col width="100" />
							<col width="*" />
						</colgroup>
						<thead>
							<tr>
								<th rowspan="2">차수</th>
								<th rowspan="2">상담예정일</th>
								<th rowspan="2">장소</th>
								<th rowspan="2">담당MD</th>
								<th>상태(결과)</th>
								<th>상담요청내용</th>
							</tr>
							<tr>
								<th>처리일자</th>
								<th>상담결과 내용</th>
							</tr>
						</thead>
						
						<tbody>
							<tr>
								<td rowspan="2" style="text-align: center;">1</td>
								<td rowspan="2" style="text-align: center;">2016-07-11 10:00</td>
								<td rowspan="2" style="text-align: center;">롯데마트 본사(행복동)</td>
								<td rowspan="2" style="text-align: center;">김담당</td>
								<td style="text-align: center;">상담완료</td>
								<td>1차 상담에서 협의한 상품의 안전검사 시험성적표 관련 추가 상담</td>
							</tr>
							<tr>
								<td style="text-align: center;">상담완료</td>
								<td>1차 상담에서 협의한 상품의 안전검사 시험성적표 관련 추가 상담</td>
							</tr>
						</tbody>
					</table>
				</div>
				<!----- 입점상담 이력/요청 정보 End ------------------------->
				
				
				<!----- 품평회 이력 Start ------------------------->
				<p style="margin-top: 10px;" />
				<div class="div_tit_dot">
					<div class="div_tit">
						<tt>품평회 이력</tt>
					</div>
				</div>
				
				<div class="tbl_default">
					<table id="tblInfo" name="tblInfo">
						<colgroup>
							<col width="40" />
							<col width="100" />
							<col width="150" />
							<col width="80" />
							<col width="100" />
							<col width="*" />
						</colgroup>
						<thead>
							<tr>
								<th rowspan="2">차수</th>
								<th rowspan="2">품평회예정일</th>
								<th rowspan="2">장소</th>
								<th rowspan="2">담당MD</th>
								<th>상태(결과)</th>
								<th>품평회 요청내용</th>
							</tr>
							<tr>
								<th>처리일자</th>
								<th>품평회 결과 내용</th>
							</tr>
						</thead>
						
						<tbody>
							<tr>
								<td rowspan="2" style="text-align: center;">1</td>
								<td rowspan="2" style="text-align: center;">2016-07-11 10:00</td>
								<td rowspan="2" style="text-align: center;">롯데마트 본사(행복동)</td>
								<td rowspan="2" style="text-align: center;">김담당</td>
								<td style="text-align: center;">상담완료</td>
								<td>1차 상담에서 협의한 상품의 안전검사 시험성적표 관련 추가 상담</td>
							</tr>
							<tr>
								<td style="text-align: center;">상담완료</td>
								<td>1차 상담에서 협의한 상품의 안전검사 시험성적표 관련 추가 상담</td>
							</tr>
						</tbody>
					</table>
				</div>
				<!----- 품평회 이력 End ------------------------->
				
				
				<!----- 이행보증보험 등록 Start ------------------------->
				<p style="margin-top: 10px;" />
				<div class="div_tit_dot">
					<div class="div_tit">
						<tt>이행보증보험 등록</tt>
						
						<btn>
							<input type="button" id="" name="" value="등록" class="btn_normal btn_red">
						</btn>
					</div>
				</div>
				
				<div class="edit_default">
					<table id="tblInfo" name="tblInfo">
						<colgroup>
							<col width="15%" />
							<col width="35%" />
							<col width="15%" />
							<col width="35%" />
						</colgroup>
						<tr>
							<th>요청일</th>
							<td>2016년 07월 30일 까지</td>
							<th>요청사유(비고)</th>
							<td>해당일까지 이행보증보험을 발급 바랍니다.</td>
						</tr>
					</table>
				</div>
				
				<div class="edit_default">
					<table id="tblInfo" name="tblInfo">
						<colgroup>
							<col width="15%" />
							<col width="35%" />
							<col width="15%" />
							<col width="35%" />
						</colgroup>
						<tr>
							<th>보증보험사</th>
							<td>
								<select id="" name="">
									<option value="">선택</option>
									<option value="">서울보증</option>
								</select>
							</td>
							<th>보증증권번호</th>
							<td>
								<input type="text" id="" name="" class="input_txt_default">
							</td>
						</tr>
						<tr>
							<th>보증금액</th>
							<td>
								<input type="text" id="" name="" class="input_txt_default">
							</td>
							<th>보증기간</th>
							<td>
								<input type="text" id="" name="" class="input_txt_default">
							</td>
						</tr>
						<tr>
							<th>보증서</th>
							<td colspan="3">
								<input type="file" id="" name="">
								<br>
								Notice : 이행보증증권을 스캔(Scan)하여 이미지 파일로 업로드 하세요.
							</td>
						</tr>
					</table>
				</div>
				<!----- 이행보증보험 등록 End ------------------------->
				
	 		</div>
 		</div>
 	</div>

</form>

</body>
</html>