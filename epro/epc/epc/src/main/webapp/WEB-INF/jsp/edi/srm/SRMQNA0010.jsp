<%@ page  pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>

<%--
	Page Name 	: SRMFAQ0010.jsp
	Description : 임대매장 입점 FAQ 글 작성
	Modification Information
	
	수정일      			수정자           		수정내용
	-----------    	------------    ------------------
	2024.07.16  	NBM				 최초 생성
--%>

<!doctype html>
<html lang="ko">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<script type="text/javascript" src="<c:out value='${namoPath}'/>"></script>

	<title><spring:message code='text.srm.field.srmfaq0010.title1'/></title><%--FAQ --%>

</head>

<body>
<!--Container-->
<div id="container">
	<!-- Sub Wrap -->
	<div class="inner sub_wrap">
		<!-- 서브상단 -->
		<div class="sub_top">
			<h2 class="tit_page">패션/F&B/리빙/헬스 테넌트 입점상담문의</h2>
			<p class="page_path">HOME <span>패션/F&B/리빙/헬스 테넌트 입점상담문의</span></p>
		</div><!-- END 서브상단 -->

		<!-- Board wrap -->
		<div class="board-wrap">
<%--			<strong style="font-size: 12pt; margin-left: 20px;">1. [개인정보 수집 항목, 수집 • 이용 목적 및 보유 및 이용기간]</strong>--%>

<%--			<p style="font-size: 10pt; line-height: 1.5em; padding: 10px;">--%>
<%--				롯데쇼핑(주)롯데마트는 개인정보보호법 등 관련 법령상의 개인정보 규정을 준수하고 있습니다.<br />--%>
<%--				개인정보의 수집 항목과 목적, 기간은 아래와 같으며, 수집된 정보는 본 수집/이용 목적 외에 다른 목적으로는 사용되지 않습니다.<br />--%>
<%--			</p>--%>

<%--			<jsp:include page="agree_ko_faq_ver1.0.html" />--%>
<%--			<br/>--%>
<%--			<input type="checkbox" class="notAgreeCheck" /> 개인정보처리방침에 동의합니다.--%>
<%--			<br/><br/>--%>
<%--			<!-- 일반 게시판 리스트 -->--%>
<%--			<div class="faq-list">--%>
<%--				<!-- END 일반 게시판 리스트 -->--%>
<%--			</div>--%>
<%--			<!-- END Board wrap -->--%>

			<table class="tbl_st1 form_style">
				<colgroup>
					<col width="20%"/>
					<col width="*"/>
					<col width="20%"/>
					<col width="*"/>
				</colgroup>
				<tr>
					<th><label for="subject">* 제목</label></th>
					<td colspan="3">
						<input type="text" id="subject" name="subject" title="<spring:message code='text.srm.field.sellerNameLoc'/>" style="width: 90%;" />
					</td>
				</tr>
				<tr>
					<th><label for="category">* 카테고리</label></th>
					<td colspan="3" id="td-cat" >
						<select id="sel-foodYn">
							<option value="">선택</option>
							<option value="Y">식품</option>
							<option value="N">비식품</option>
						</select>
						<select id="sel-cat" disabled>
							<option value="">선택</option>
						</select>
					</td>
				</tr>
				<tr>
					<th><label for="mainName">* 성함</label></th><%--spring:message : 담당자명--%>
					<td>
						<input type="text" id="mainName" name="mainName" title="<spring:message code='text.srm.field.sellerCeoName'/>" value="<c:out value='${srmComp.sellerCeoName}'/>" />
					</td>
					<th><label for="email">* 이메일</label></th><%--spring:message : E-Mail--%>
					<td>
						<input type="text" id="email" name="email" title="<spring:message code='text.srm.field.vEmail'/>" value="<c:out value='${srmComp.vEmail}'/>"  style="width:90%"/>
					</td>
				</tr>

				<tr>
					<th><label for="sellerNameLoc">* 상호명</label></th><%--spring:message : 상호명--%>
					<td>
						<input type="text" id="sellerNameLoc" name="sellerNameLoc" title="<spring:message code='text.srm.field.sellerNameLoc'/>" value="<c:out value='${srmComp.sellerNameLoc}'/>" style="width: 90%;" />
					</td>
					<th><label for="phone">* 휴대전화</label></th><%--spring:message : 휴대전화--%>
					<td>
						<input type="text" id="phone" name="phone" title="<spring:message code='text.srm.field.vMobilePhone'/>" value="<c:out value='${srmComp.vMobilePhone}'/>" class="input_txt_default numberic" />
					</td>
				</tr>
				<tr>
					<th><label for="sellerCode">사업자 번호</label></th>
					<td colspan="3">
						<input type="text" id="irsNo1" name="irsNo1" title="<spring:message code='text.srm.field.irsNo'/>"  maxlength="3" style="width:40px; text-align: center;"/>
						-
						<input type="text" id="irsNo2" name="irsNo2" title="<spring:message code='text.srm.field.irsNo'/>"  maxlength="2" style="width:40px; text-align: center;" />
						-
						<input type="text" id="irsNo3" name="irsNo3" title="<spring:message code='text.srm.field.irsNo'/>" maxlength="5" style="width:80px; text-align: center;" />
					</td>
				</tr>
				<tr>
					<th><label for="hopeStore">희망점포</label></th>
					<td colspan="3" id="td-hopestore">
						<select id="sel-martSuperFg">
							<option value="">선택</option>
							<option value="M">마트</option>
							<option value="S">슈퍼</option>
						</select>
						<select id="sel-storeArea" disabled>
							<option value="">선택</option>
						</select>
						<select id="sel-store" disabled>
							<option value="">선택</option>
						</select>
					</td>
				</tr>
				<tr>
					<th><label for="content">* 내용</label></th>
					<td colspan="3">
						<textarea name="content" id="content" Style="height:200px;font-size:10pt;resize: none;"></textarea>
					</td>
				</tr>

				<tr>
					<th><label for="boardAttach">상담첨부파일</label></th>
					<td colspan="3">
						<input type="hidden" id="boardAttachFileName" name="boardAttachFileFileName" title="<spring:message code='text.srm.field.productImgPath'/>" class="input_txt_default" disabled="disabled" readonly="readonly"/>
						<input type="file" id="boardAttachFile" name="boardAttachFile" title="<spring:message code='text.srm.field.productImgPath'/>"  />
						<btn>
							<input type="button" id="" name="" class="btn_normal btn_gray" value="<spring:message code='button.srm.cancel'/>" onclick="javascript:fileClear('boardAttachFile')"/>
							<btn>
								<div style="color:red;"><spring:message code='text.srm.field.srmjon0041Notice1'/></div>
					</td>
				</tr>
				<tr></tr>
				<tr></tr>
				<tr>
					<th><label for="compInfoAttach">회사소개서(개인Profile)</label></th>
					<td colspan="3">
						<input type="hidden" id="compInfoAttachFileName" name="compInfoFileName" title="<spring:message code='text.srm.field.productImgPath'/>" class="input_txt_default" disabled="disabled" readonly="readonly"/>
						<input type="file" id="compInfoAttachFile" name="compInfoAttachFile" title="<spring:message code='text.srm.field.productImgPath'/>" />
						<btn>
							<input type="button" id="" name="" class="btn_normal btn_gray" value="<spring:message code='button.srm.cancel'/>" onclick="javascript:fileClear('compInfoAttachFile')"/>
							<btn>
					</td>
				</tr>
				<tr>
					<th><label for="bizPlanAttach">사업계획서 or 브랜드소개서</label></th>
					<td colspan="3">
						<input type="hidden" id="bizPlanAttachFileName" name="bizPlanAttachFileName" title="<spring:message code='text.srm.field.productIntroAttachNo'/>" class="input_txt_default" disabled="disabled" readonly="readonly"/>
						<input type="file" id="bizPlanAttachFile" name="bizPlanAttachFile" title="<spring:message code='text.srm.field.productIntroAttachNo'/>" />
						<btn>
							<input type="button" id="" name="" class="btn_normal btn_gray" value="<spring:message code='button.srm.cancel'/>" onclick="javascript:fileClear('bizPlanAttachFile')"/>
							<btn>
					</td>
				</tr>
				<tr>
					<th><label for="bizRegCertAttach">사업자등록증사본(개인제외)</label></th>
					<td colspan="3">
						<input type="hidden" id="bizRegCertAttachFileName" name="bizRegCertAttachFileName" title="<spring:message code='text.srm.field.companyIntroAttachNo'/>" class="input_txt_default" disabled="disabled" readonly="readonly"/>
						<input type="file" id="bizRegCertAttachFile" name="bizRegCertAttachFile" title="<spring:message code='text.srm.field.companyIntroAttachNo'/>" />
						<btn>
							<input type="button" id="" name="" class="btn_normal btn_gray" value="<spring:message code='button.srm.cancel'/>" onclick="javascript:fileClear('bizRegCertAttachFile')"/>
							<btn>
					</td>
				</tr>
			</table>

			<div class="tit_btns">
				<div class="right_btns">
					(*표시 항목 : 필수입력)&nbsp;&nbsp;
					<button type="button" class="btn_normal btn_blue" onclick="javascript:doSave();">작성</button>	<%-- 임시저장--%>
				</div>
			</div>
		</div>
	</div>
	<script>
		const doSave = () => {
			// let params = {};
			const paramData = new FormData();
			const _setParams = () => {
				const __rtnZeroWhenCodeBlank = (sellerCode) => {
					if (!sellerCode) {
						return '0000000000'
					}
					return sellerCode;
				}

				const _sellerCode = __rtnZeroWhenCodeBlank(document.getElementById("irsNo1").value + document.getElementById("irsNo2").value + document.getElementById("irsNo3").value);
				const _subject = document.getElementById("subject").value;
				const _category = document.getElementById("sel-cat").value;
				const _mainName = document.getElementById("mainName").value;
				const _sellerNameLoc = document.getElementById("sellerNameLoc").value;
				const _email = document.getElementById("email").value;
				const _phone = removeSpecialChar(document.getElementById("phone").value);
				const _hopeStore = document.getElementById("sel-store").value;
				const _content = document.getElementById("content").value;
				const _regId = _sellerCode;
				const _agreeFileVer = "agree_ko_faq_ver1.0.html";
				const _boardAttachFileName = document.getElementById("boardAttachFile").value;
				const _boardAttachFile = document.getElementById("boardAttachFile").files[0] !== undefined ? document.getElementById("boardAttachFile").files[0] : new Blob();
				const _compInfoAttachFileName = document.getElementById("compInfoAttachFile").value;
				const _compInfoAttachFile = document.getElementById("compInfoAttachFile").files[0] !== undefined ? document.getElementById("compInfoAttachFile").files[0] : new Blob();
				const _bizPlanAttachFileName = document.getElementById("bizPlanAttachFile").value;
				const _bizPlanAttachFile = document.getElementById("bizPlanAttachFile").files[0] !== undefined ? document.getElementById("bizPlanAttachFile").files[0] : new Blob();
				const _bizRegCertAttachFileName = document.getElementById("bizRegCertAttachFile").value;
				const _bizRegCertAttachFile = document.getElementById("bizRegCertAttachFile").files[0] !== undefined ? document.getElementById("bizRegCertAttachFile").files[0] : new Blob();

				paramData.append("sellerCode", _sellerCode);
				paramData.append("subject", _subject);
				paramData.append("category", _category);
				paramData.append("mainName", _mainName);
				paramData.append("sellerNameLoc", _sellerNameLoc);
				paramData.append("email", _email);
				paramData.append("phone", _phone);
				paramData.append("hopeStore", _hopeStore);
				paramData.append("content", _content);
				paramData.append("regId", _regId);
				paramData.append("agreeFileVer", _agreeFileVer);
				paramData.append("boardAttachFileName", _boardAttachFileName);
				paramData.append("boardAttachFile", _boardAttachFile);
				paramData.append("compInfoAttachFileName", _compInfoAttachFileName);
				paramData.append("compInfoAttachFile", _compInfoAttachFile);
				paramData.append("bizPlanAttachFileName", _bizPlanAttachFileName);
				paramData.append("bizPlanAttachFile", _bizPlanAttachFile);
				paramData.append("bizRegCertAttachFileName", _bizRegCertAttachFileName);
				paramData.append("bizRegCertAttachFile", _bizRegCertAttachFile);
			}

			const _getParams = () => {
				return params;
			}

			const _validation = () => {
				// 허용문자체크 메시지
				const __setPermitMsg = (target) => {
					const __permitMsg = "<spring:message code='text.srm.alert.permitCheck' arguments='"+target+"'/>";
					return __permitMsg;
				}

				// Byte체크 메시지
				const __setByteMsg = (target, size) => {
					const __byteMsg = "<spring:message code='text.srm.alert.byteCheck' arguments='"+target+","+size+"'/>";
					return __byteMsg;
				}

				// 제목 체크
				const _subject = paramData.get("subject");
				if (!_subject?.trim()) {
					alert("제목을 확인해주세요");
					document.getElementById("subject").focus();
					return false;
				}

				// 카테고리 체크
				const _category = paramData.get("category");
				if (!_category?.trim()) {
					alert("카테고리 확인해주세요");
					document.getElementById("sel-foodYn").focus();
					return false;
				}

				// 성함 체크
				const _mainName = paramData.get("mainName");
				if (!_mainName?.trim()) {
					alert("성함 확인해주세요");
					document.getElementById("mainName").focus();
					return false;
				}

				// 이메일 체크
				const _email = paramData.get("email");
				if (!_email?.trim()) {
					alert("이메일 확인해주세요");
					document.getElementById("email").focus();
					return false;
				}

				//Email 검증
				var regVar = "^[a-zA-Z0-9!$&*.=^`|~#%'+\/?_{}-]+@([a-zA-Z0-9_-]+\.)+[a-zA-Z]{2,4}$";
				if (new RegExp(regVar).test(_email) === false) {
					alert("이메일 형식이 아닙니다");
					document.getElementById("email").focus();
					return false;
				}

				// 상호명 체크
				const _sellerNameLoc = paramData.get("sellerNameLoc");
				if (!_sellerNameLoc?.trim()) {
					alert("상호명 확인해주세요");
					document.getElementById("sellerNameLoc").focus();
					return false;
				}

				// 휴대전화 체크
				const _phone = paramData.get("phone");
				if (!_phone?.trim()) {
					alert("휴대전화 확인해주세요");
					document.getElementById("phone").focus();
					return false;
				}

				if (!cal_3byte_qna(_phone, '16', __setPermitMsg("휴대전화 항목"), __setByteMsg("휴대전화 항목", '16'))) {
					document.getElementById("phone").focus();
					return false;
				}

				// 내용 체크
				const _content = paramData.get("content");
				if (!_content?.trim()) {
					alert("내용 확인해주세요");
					document.getElementById("content").focus();
					return false;
				}

				// 사업자번호 체크
				const _sellerCode = paramData.get("sellerCode");
				const _IsSellerCodeBlnak = _sellerCode === null ? true : false;
				if (!_IsSellerCodeBlnak &&
						(_sellerCode.length > 0 && _sellerCode.length < 10)) {
					alert("사업자번호를 확인해주세요");
					document.getElementById("irsNo1").focus();
					return false;
				}

				if (!_IsSellerCodeBlnak &&
						!gfnCheckBizNum(_sellerCode)) {	// 사업자번호 유효성 체크
					alert("유효한 사업자번호가 아닙니다.");
					return false;
				}

				const _boardAttachFileName = paramData.get("boardAttachFileName");
				const _compInfoAttachFileName = paramData.get("compInfoAttachFileName");
				const _bizPlanAttachFileName = paramData.get("bizPlanAttachFileName");
				const _bizRegCertAttachFileName = paramData.get("bizRegCertAttachFileName");

				if (!checkFileExt(_boardAttachFileName) || !checkFileExt(_compInfoAttachFileName)
					|| !checkFileExt(_bizPlanAttachFileName) || !checkFileExt(_bizRegCertAttachFileName)) {
					return false;
				}

				return true;
			}

			const _callSaveApi = () => {
				// Params 체크
				if (!confirm("전송하시겠습니까?")) {
					return false;
				}

				const apiUrl = '<c:url value="/edi/srm/insertQnaInfo.do"/>'
				const options = {
					method: 'POST',
					headers: {
						// 'Content-Type': 'application/json'
					},
					// body : JSON.stringify(_getParams())
					body : paramData
				}

				fetch(apiUrl, options)
						.then(response => {
							if (!response.ok) {
								throw new Error('doSave-response>> ' + response.statusText)
							}

							return response.json();
						})
						.then(result => {
							if (result.rtnStatus == "F") {
								alert("요청 실패했습니다. 담당자 확인 부탁드립니다");
								return ;
							}

							alert("요청 성공했습니다.");
							location.href = "<c:url value='/edi/srm/SRMJONMain.do'/>";
						})
						.catch(error => {
							console.error('doSave-error>> ' + error)
						})
			}
			_setParams();

			if (_validation()) {
			    _callSaveApi();
			}

		}

		const setCatElem = (foodYnFg) => {
			if (!foodYnFg && (foodYnFg !== 'Y' && foodYnFg !== 'N')) {
				return ;
			}

			const apiUrl = '<c:url value="/edi/srm/fetchQnaCategory.json"/>'
			const options = {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body : JSON.stringify({"foodYnFg": foodYnFg})
			}

			fetch(apiUrl, options)
					.then(response => {
						if (!response.ok) {
							throw new Error('setCatElemYn-response>> ' + response.statusText)
						}

						return response.json();
					})
					.then(result => {
						let catElem = document.getElementById("sel-cat");
						catElem.disabled = false;
						result.forEach(optionData => {
							const addOption = document.createElement('option');
							addOption.value = optionData.code;
							addOption.text = optionData.name;
							catElem.appendChild(addOption);
						})
					})
					.catch(error => {
						console.error('setCatElemYn-error>> ' + error)
					})
		}

		const setStoreAreaElem = (martSuperFG) => {
			if (!martSuperFG) {
				return ;
			}

			const apiUrl = '<c:url value="/edi/srm/fetchQnaStoreArea.json"/>'
			const options = {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body : JSON.stringify({"martSuperFg": martSuperFG})
			}

			fetch(apiUrl, options)
					.then(response => {
						if (!response.ok) {
							throw new Error('setStoreAreaElem-response>> ' + response.statusText)
						}

						return response.json();
					})
					.then(result => {
						let storeAreaElem = document.getElementById("sel-storeArea");
						storeAreaElem.disabled = false;
						result.forEach(optionData => {
							const addOption = document.createElement('option');
							addOption.value = optionData.code;
							addOption.text = optionData.name;
							storeAreaElem.appendChild(addOption);
						})
					})
					.catch(error => {
						console.error('setStoreAreaElem-error>> ' + error)
					})
		}

		const setStoreElem = (areaCd) => {
			if (!areaCd) {
				return ;
			}

			const apiUrl = '<c:url value="/edi/srm/fetchQnaStore.json"/>'
			const options = {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body : JSON.stringify({"areaCd": areaCd})
			}

			fetch(apiUrl, options)
					.then(response => {
						if (!response.ok) {
							throw new Error('setStoreElem-response>> ' + response.statusText)
						}

						return response.json();
					})
					.then(result => {
						let storeElem = document.getElementById("sel-store");
						storeElem.disabled = false;
						result.forEach(optionData => {
							const addOption = document.createElement('option');
							addOption.value = optionData.code;
							addOption.text = optionData.name;
							storeElem.appendChild(addOption);
						})
					})
					.catch(error => {
						console.error('setStoreElem-error>> ' + error)
					})
		}

		const initSelectElem = (selElem) => {
			if (!selElem && selElem == null) {
				return ;
			}

			selElem.innerHTML = `<option value="">선택</option>`;
			selElem.disabled = true;
		}

		//24NBM 스크립트
		document.addEventListener("DOMContentLoaded", () => {
			let selFoodYnElem = document.getElementById("sel-foodYn");
			selFoodYnElem.addEventListener('change', (e) => {
				initSelectElem(document.getElementById("sel-cat"));

				if (e.target.value == "Y" || e.target.value == "N") {
					setCatElem(e.target.value);
				}
			})

			let martSuperFgElem = document.getElementById("sel-martSuperFg");
			martSuperFgElem.addEventListener('change', (e) => {
				initSelectElem(document.getElementById("sel-storeArea"));
				initSelectElem(document.getElementById("sel-store"));

				if (e.target.value) {
					setStoreAreaElem(e.target.value);
				}
			})

			let storeAreaElem = document.getElementById("sel-storeArea");
			storeAreaElem.addEventListener('change', (e) => {
				initSelectElem(document.getElementById("sel-store"));

				if (e.target.value) {
					setStoreElem(e.target.value);
				}
			})

		})

		//파일초기화
		const fileClear = (inputName) => {
			document.getElementById(inputName).value = ""
		}

		///byte 체크 (한글 3byte)
		const cal_3byte_qna = (input, VMax, permitMsg, byteMsg) => {
			const tmpStr = new String(input);
			let onechar;
			let tcount = 0;
			let permitChar = /^[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣\s\r\n`~!@#$%^&*()\-_=+\\|\]\["',./:;<>?}{]$/;
			let check5Byte = /^[@&$"%'()+/;<=>?|\-\\]$/;
			let check6Byte = /^[}{]$/;
			let check = /^[\n]$/;

			for (k = 0; k < tmpStr.length; k++) {
				onechar = tmpStr.charAt(k);

				// 허용되지 않은 문자가 포함될 경우
				if (RegExp(permitChar).test(onechar) == false) {
					alert(permitMsg);
					return false;
				}
				if (RegExp(check).test(onechar) == true) {
					tcount += 10;
				} else if (RegExp(check6Byte).test(onechar) == true) {
					tcount += 6;
				} else if (RegExp(check5Byte).test(onechar) == true) {
					tcount += 5;
				} else if (escape(onechar).length > 4) {
					tcount += 3;
				} else {
					tcount++;
				}
			}

			if (tcount > VMax) {
				alert(byteMsg + '(입력값 ' + tcount + 'Byte)')
				return false;
			}

			return true;
		}

		const removeSpecialChar = (str) => {
			if (!str) return str;
			let reg = /[`~!@#$%^&*()_|+\-=?;:'",.<>\{\}\[\]\\\/ ]/gim;
			return str.replace(reg, "");
		}

		const removeFileNameDot = (fileName) => {
			if (fileName.split(".").length !=0) {
				const fileNameSplt = fileName.split(".");
				let fileNameModified = "";
				for (let idx=0; idx<fileNameSplt.length; idx++) {
					if (idx !== fileNameSplt.length -1) {
						fileNameModified += fileNameSplt[idx];
					}
				}

			}
		}

		const checkFileExt = (fileName) => {
			if (!fileName.replace(/\s/gi, '')) return true;

			let ext =  fileName.split('.').pop().toLowerCase();
			if ($.inArray(ext, ['jpg', 'gif', 'png', 'bmp', 'doc', 'docx', 'xls', 'xlsx', 'ppt', 'pptx', 'pdf']) == -1) {
				alert(ext+'파일은 업로드 하실 수 없습니다.');
				return false;
			}

			return true;
		}

	</script>
</body>
</html>