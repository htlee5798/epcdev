<%-- 
- Author(s): NBMSHIP 
- Created Date: 
- Version : 1.0
- Description : 영양성분관리

--%>
<%@ include file="../common.jsp"%>
<%@ include file="/common/scm/scmCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
</head>

<body>
  <div id="content_wrap">
    <div>
     <form name="searchForm" id="searchForm" method="post" action="#">
	   <div id="wrap_menu" style="width: 100%; height: 100%; overflow-x: hidden; overflow-y: hidden; table-layout: fixed;">
         <div class="wrap_search">
           <div class="bbs_search">
		     <ul class="tit">
			   <li class="tit">상품 영양성분</li>
			     <li class="btn" id="submitBtn" style="display:none;">
			       <a href="#" class="btn" onclick="pd.submitNutAttInfo();" ><span>전송</span></a>
			     </li>
				 <li class="btn" id="modifyBtn" style="display:none;">
				   <a href="#" class="btn" onclick="pd.updateModifyStatusAttr('M');" ><span>확정취소</span></a>
				 </li>
				 <li class="btn" id="confirmBtn" style="display:none;">
				   <a href="#" class="btn" onclick="pd.updateModifyStatusAttr('C');" ><span>확정</span></a>
				 </li>
			     <li class="btn" id="saveBtn" style="display:none;">
			     <a href="#" class="btn" onclick="pd.saveNutAmtInfo();" ><span>저장</span></a>
			    </li>
		      </ul>
			</div>
          </div>
          <div class="wrap_con">
		    <div style="width: 100%; height: 80%; overflow-x: hidden; overflow-y: scroll; overflow-x: hidden; table-layout: fixed;">    
			  <!-- 
			  <div id="param_frame" /> 
			  -->
			  <table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0" style="border-collapse: separate; border-spacing: 0 5px;" > 
			    <colgroup>
			      <col width="20%"/>
			      <col width="30%"/>
			      <col width="20%"/>
			      <col width="30%"/>
			    </colgroup>
			    <tbody id="param_frame"/>
			  </table>
			</div>
		  </div>
		</div>
	   </form>
	  </div>
	</div>
  <script>
  $(document).ready(function() {
    pe.initPage();
  });

  const pe = (function pageEventFunction(){
    return {
    
      initPage: function() {
        pd.setNutInfoDom();
        this.alertSubmitResponseMessage();
      },
      
      alertSubmitResponseMessage: function() {
        let msg = "<c:out value='${prodInfo.msg}'/>";
        if (msg === "S") {
          alert("상품 속성 전송이 완료되었습니다.");
        }

        if (msg === "ALREADY_REQUEST") {
          alert("속성정보 요청이 이미 진행됐습니다.");
        }
      }
    }
  })();

  const ut = {
    // Ajax 호출 함수 (호출 URL, 파라미터, 응답 콜백함수)
    ajaxJson : function(url, opt, cbFunc) {
      fetch(url, opt)
      .then((res) => { 
        console.log("httpStatus: " + res.status);
        return res.json()
      })
      .then((dataJson) => { 
        cbFunc(dataJson)
      })
      .catch((error) => console.log("ajaxJson Err: ", error));
    },
      
    getToday: function(){
      let tdDt;
      let dt = new Date();

      let year  = dt.getFullYear();
      let month = dt.getMonth() + 1;
      if (month < 10) {
        month = "0" + month;
      }
      let day = dt.getDate();
      if (day < 10)  {
        day = "0" + day;
      }
      tdDt = "" + year + month + day;
      return tdDt;
    },
    
    getTime: function(){
      let dt = new Date();
      let tdTm;

      let hour   = dt.getHours();
      if (hour < 10) {
        hour  = "0" + hour;
      }
      let minute = dt.getMinutes();
      if (minute < 10) {
        minute = "0" + minute;
      }
      let second = dt.getSeconds();
      if (second < 10) {
        second = "0" + second;
      }
      tdTm = "" + hour + minute + second;
      return tdTm;
    }, 
    
    generateMsg(targetElem, errMsg) {
      if (!targetElem) return ; 
      
      let errElems = '<div id=\"error_msg\" name=\"error_msg\" style=\"color:red;\">' + errMsg + '</div>';
      targetElem.insertAdjacentHTML('beforebegin', errElems);
    },    
    
    delErrMsg(targetElem) {
      let prevTargetElem = targetElem.previousElementSibling;

      if (!prevTargetElem){
        return ;
      }
      
      let isErrMsg = targetElem.previousElementSibling.getAttribute("name") === "error_msg" ? true : false;
      if (isErrMsg) {
        targetElem.previousElementSibling.remove();
      }
    },   
  }

  const pf = (function pageFunction() {
    return { 
      manageDisplayActBtn: function() {
        let _statusModifyAttr = pf.getStatusModifyAttr();
        let _saveBtn    = document.getElementById("saveBtn");
        let _confirmBtn = document.getElementById("confirmBtn");
        let _modifyBtn  = document.getElementById("modifyBtn");
        let _submitBtn  = document.getElementById("submitBtn");
        
        if (_statusModifyAttr === "M") { // 확정아닌 경우
          _saveBtn.style.display    = "block";
          _confirmBtn.style.display = "block";
          _modifyBtn.style.display  = "none";
          _submitBtn.style.display  = "none";
        }
        
        if (_statusModifyAttr === "C") { // 확정인경우
          _saveBtn.style.display    = "none";
          _confirmBtn.style.display = "none";
          _modifyBtn.style.display  = "block";
          _submitBtn.style.display  = "block";
        }
      },
      // 상품 영양성분 값 확정 상태 받는다.
      getStatusModifyAttr: function() {
        debugger;
        let _statusModifyAttr = "";
        let _nutInputElems = document.getElementsByClassName("nutInput");
        if (!_nutInputElems) {
          alert("관리자 확인 필요합니다_1");
          return ;
        }

        _statusModifyAttr = _nutInputElems[0].dataset["chgFg"];
        if (!_nutInputElems[0].dataset["chgFg"]) {
          _statusModifyAttr = "M";
        }
        
        return _statusModifyAttr;
      },
      // 상품 각 영양성분 코드 및 값을 받는다.
      _getNutInputInfos: function() {
        let _nutInputElems = document.getElementsByClassName("nutInput");
        let _inputNutCdList  = [];
        let _inputNutAmtList = [];

        for (let idx = 0; idx < _nutInputElems.length; idx++) {
          let _nutInputElem  = _nutInputElems[idx];
          if (!_nutInputElem.value) continue;

          let _inputNutCd  = _nutInputElem.dataset["nutCd"];
          let _inputNutAmt = _nutInputElem.value;

          _inputNutCdList.push(_inputNutCd);
          _inputNutAmtList.push(_inputNutAmt);
        }
        return [_inputNutCdList, _inputNutAmtList]
      }
    } 
  })()
   
  const pd = (function pageDataFunction() {
    return {
      // 상품 대분류 해당 영양성분 
      setNutInfoDom: function() {
        function _setNutCdToDom(resNutData) {
          if (!resNutData) {
            return;
          }
            
          for (let nutAttIdx = 0; nutAttIdx < resNutData.length; nutAttIdx++) {
            if (!resNutData[nutAttIdx].NUT_CD) continue;
            let _nutInputElem = document.getElementById("nutInput" + nutAttIdx);
            _nutInputElem.dataset.nutCd  = resNutData[nutAttIdx].NUT_CD;
          }
        }

        function _showTrTagInTBody(idx, trTag) {
          //인덱스 짝수 일때 <tr>
          //홀수 있때 </tr> 
          if (idx % 2 === 0 && trTag === "<tr>") {
            return "<tr>";
          }

          if (idx % 2 !== 0 && trTag === "</tr>") {
            return "</tr>";
          }
          ;
          return "" 
        }

        function _createNoNutInfoDom() {
          let _targetDom = document.getElementById("param_frame");
          let _noNutMsgElems =  '<tr class="r1"  bgcolor="ffffff">' 
                             + '  <td align="center" colspan="5" style="line-height: 25px;text-align:center;">입력가능 영양성분이 없습니다.</td>'               
                             + '</tr>';
                             
          _targetDom.insertAdjacentHTML('beforeend', _noNutMsgElems);
        }

        function _getNutUnit(unit) {
          if ( unit !== null ) {
            unit = " (" + unit + ")";
          }
          return unit;
        }
        
        function _createThTagNutNm(resNutData) {
          return '<th>' + resNutData.NUT_NM + _getNutUnit(resNutData.UNIT) + '</th>';
        }
        
        function _crateTdTagInputNutInfo(nutIdx) {
          return '<td>'
               + '<input type="text" class="nutInput" id="nutInput' + nutIdx + '"/>'
               + '</td>';
        }
        
        function _createNutInfoDom(resNutData) {
          let _targetDom = document.getElementById("param_frame");
          let _inputNutInfoDom = "";
          for (let nutAttIdx = 0; nutAttIdx < resNutData.length; nutAttIdx++) {
            if (!resNutData[nutAttIdx].NUT_CD)continue;

            // 영양성분 입력 html 생성
            _inputNutInfoDom = _inputNutInfoDom +  
              _showTrTagInTBody(nutAttIdx, "<tr>") + 
              _createThTagNutNm(resNutData[nutAttIdx]) + _crateTdTagInputNutInfo(nutAttIdx) +
              _showTrTagInTBody(nutAttIdx, "</tr>")
          }

          _targetDom.insertAdjacentHTML('beforeend', _inputNutInfoDom);
        }

        function _callBackSetNutInfoDom(resNutData) {
          if ( !resNutData || !resNutData.length ) {
            _createNoNutInfoDom();
            return;
          } 
          
          _createNutInfoDom(resNutData);
          _setNutCdToDom(resNutData);
          
          _this.setNutAmtSavedInfo();
        }

        function _getParam() {
          let _l3Cd = "<c:out value='${prodInfo.l3Cd}'/>";
          let _paramMap = {};
          _paramMap["l3Cd"] = _l3Cd;
          return _paramMap
        }
        
        function _setNutInfoDom() {         
          const _url = '<c:url value="/edi/product/getNutAttInfo.json"/>';
          const _ajaxOpt = {
              method: "POST",
              headers: {
                "Content-type": "application/json"
              },
              body: JSON.stringify(_getParam())
          }   
          ut.ajaxJson(_url, _ajaxOpt, _callBackSetNutInfoDom);     
        }

        let _this = this;  
        _setNutInfoDom();
      },  
      // 영양성분 저장했던 값 선택값으로 할당
      setNutAmtSavedInfo: function() {   
        function _getInputWithNutCd(nutCdFind) {
          let _nutInputElems = document.getElementsByClassName("nutInput");
          for (let idx = 0; idx < _nutInputElems.length; idx++) {
            if (_nutInputElems[idx].dataset["nutCd"] == nutCdFind) 
              return _nutInputElems[idx]
          }
          return "";
        }

        function _putNutAmtToInput(inputElemFind, nutAmtSaved) {
          inputElemFind.value = nutAmtSaved;
        }
        
        function _callBackSetNutAmtSavedInfo(resNutData) {
          let _attrStatusModify = 'M';
          for (let idx=0; idx<resNutData.length; idx++) {
            let _nutCdFind = resNutData[idx].NUT_CD; 
            let _nutAmtSaved  = resNutData[idx].NUT_AMT; 
            let _inputElemFind = _getInputWithNutCd(_nutCdFind);
            if (!_inputElemFind) {
              continue;
            }

            _putNutAmtToInput(_inputElemFind, _nutAmtSaved);
            
            _inputElemFind.dataset.chgFg = resNutData[idx].CHG_FG;
            if (resNutData[idx].CHG_FG != 'M') {
              _attrStatusModify = 'C';
            }
          }
          
          let _cntNotNutAttRes = "<c:out value='${cntNotNutAttRes}'/>" * 1;
          let _nutInputElems = document.getElementsByClassName("nutInput");
          let _isDisable = false;

          if (_attrStatusModify == 'C' || _cntNotNutAttRes > 0) {
            _isDisable = true;
          }
          
          for (let idx = 0; idx < _nutInputElems.length; idx++) {
            _nutInputElems[idx].disabled = _isDisable;
          }

          pf.manageDisplayActBtn();
        }

        function _getParam() {
          let _prodCd = "<c:out value='${prodInfo.prodCd}'/>";
          let _paramMap = {};
          _paramMap["prodCd"] = _prodCd;
          return _paramMap;
        }
        
        function _setNutAmtSavedInfo() {
          const _url = '<c:url value="/edi/product/getNutAttAmtSaved.json"/>';
          const _ajaxOpt = {
              method: "POST",
              headers: {
                "Content-type": "application/json"
              },
              body: JSON.stringify(_getParam())
          }
          ut.ajaxJson(_url, _ajaxOpt, _callBackSetNutAmtSavedInfo);
        }
        _setNutAmtSavedInfo();        
      },
      // 영양성분 값 저장
      saveNutAmtInfo: function() {
        
        function _validNutAmt(inputNutAmtList) {
          let isValid = true;
          
          inputNutAmtList.forEach( (val,idx) => {

            let isValidValue = true;
            let errMsg = "";
            
            let nutAmt = val.replace(/\s/gi, '');
            if (val === '0' || val === 0) {
               nutAmt = 0;
            }

            if (nutAmt) {
              let regExpNutAmtValid = /^[0-9]{1,7}(\.{1}[0-9]{1,3})?/g ;
              let nutAmtValid = regExpNutAmtValid.exec(nutAmt);
              if (!nutAmtValid || nutAmt != nutAmtValid[0] || nutAmt.replace('\.','').length > 10) {
                errMsg = "숫자 10자리 소수점 세번째 자리까지 입력가능합니다.";
                isValidValue = false;
                isValid = false;
              }
            } 

            if (nutAmt === 0) {
              errMsg = "'0'을 입력했을 경우 0.001을 입력해주세요";
              isValidValue = false;
              isValid = false;
            }

            let _thisElem = document.getElementById("nutInput" + idx);
            ut.delErrMsg(_thisElem);
            if (!isValidValue) {              
              ut.generateMsg(_thisElem, errMsg);
            }
          })

          return isValid;
        }
        
        function _callBackSaveNutAmtInfo(resData) {
          if (resData.rtnMsg == "F") {
            alert("영양성분 저장실패했습니다.");
            return ;
          }
          alert("영양성분 저장하였습니다.")
          return ;
        }

        function _getParam() {
          let [_inputNutCdList, _inputNutAmtList] = pf._getNutInputInfos();
          let _prodCd = "<c:out value='${prodInfo.prodCd}'/>";
          let _entpCd = "<c:out value='${epcLoginVO.repVendorId}'/>";

          let _paramMap = {};
          _paramMap["nutCd"]  = _inputNutCdList;
          _paramMap["nutAmt"] = _inputNutAmtList;
          _paramMap["prodCd"] = _prodCd;
          _paramMap["entpCd"] = _entpCd;
          return _paramMap;
        }
        
        function _saveNutAmtInfo() {
          // 검증란
          let [_inputNutCdList, _inputNutAmtList] = pf._getNutInputInfos();
          if (_inputNutCdList.length == 0) {
            alert("선택된 속성값이 없습니다.");
            return ;
          }

          if (!_validNutAmt(_inputNutAmtList)) {
            alert("영양성분 값 확인 부탁드립니다.");
            return ;
          }

          let _nutInputElemes = document.getElementsByClassName("nutInput");
          if (_nutInputElemes[0].disabled) {
            alert("확정상태여서 저장할 수 없습니다.");
            return ;
          }
          
          if (!confirm("저장하시겠습니까?")) {
            return;
          }      

          const _url = '<c:url value="/edi/product/saveNutAttInfo.json"/>';
          const _ajaxOpt = {
              method: "POST",
              headers: {
                "Content-type": "application/json"
              },
              body: JSON.stringify(_getParam())
          }

          ut.ajaxJson(_url, _ajaxOpt, _callBackSaveNutAmtInfo);  
        }
        let _this = this;
        _saveNutAmtInfo();
      },
      // 영양성분 확정 및 확정해제
      updateModifyStatusAttr: function(attrStatusModify) {

        function _delAllErrMsg() {
          let inputElems = document.getElementsByClassName("nutInput");

          for (let idx=0; idx < inputElems.length; idx++) {
            ut.delErrMsg(inputElems[idx]);
          }
        }
        
        function _callBackUpdateModifyStatus(rtnData) {
          if (rtnData.rtnMsg == "M" || rtnData.rtnMsg == "C") {
            if (rtnData.rtnMsg == "C") {
              alert("확정처리 되었습니다.");
            }
            if (rtnData.rtnMsg == "M") {
              alert("확정취소처리 되었습니다.");
            }
            _delAllErrMsg();
            _this.setNutAmtSavedInfo();
          }
          if (rtnData.rtnMsg == "-3") {
            alert("신청한 속성 값중 심사중인 값이 있어 해제되지 않습니다.")
          }
          if (rtnData.rtnMsg == "-2") {
            alert("상품 속성을 저장하신 후에 확정 부탁드립니다.");
          }
          if (rtnData.rtnMsg == "-1" || rtnData.rtnMsg == "E") {
            alert("관리자 확인 바랍니다_2.");
          }
          return ;
        }      

        function _getParam(attrStatusModify) {
          let _chgFg  = attrStatusModify;
          let _prodCd = "<c:out value='${prodInfo.prodCd}'/>";
          let _l3Cd   = "<c:out value='${prodInfo.l3Cd}'/>";
          let _entpCd = "<c:out value='${epcLoginVO.repVendorId}'/>";
          let _seq    = "<c:out value='${seqAttr}'/>" * 1;

          let _paramMap = {};

          _paramMap["chgFg"]  = _chgFg;
          _paramMap["prodCd"] = _prodCd;
          _paramMap["l3Cd"]   = _l3Cd;
          _paramMap["entpCd"] = _entpCd;
          _paramMap["seq"]    = _seq;
          return _paramMap;
        }

        function _validation(attrStatusModify) {
          let _nutInputElems = document.getElementsByClassName("nutInput");
          if (_nutInputElems[0].dataset["chgFg"] == attrStatusModify) {
            if (attrStatusModify == "M") {
              alert("이미 확정해제상태입니다.");
            }
            if (attrStatusModify == "C") {
              alert("이미 확정상태입니다.");
            }
            return false;
          }
          return true;
        }
        
        function _updateModifyStatus(attrStatusModify) {
          if (!_validation(attrStatusModify)) {
            return ;
          }

          if (attrStatusModify == "M") {
            if (!confirm("확정해제 하시겠습니까?")) {
              return;
            }
          }
          if (attrStatusModify == "C") {
            if (!confirm("확정 하시겠습니까?")) {
              return;
            }
          }
          
          const _url = '<c:url value="/edi/product/updateNutModifyStatus.json"/>';
          const _ajaxOpt = {
              method: "POST",
              headers: {
                "Content-type": "application/json"
              },
              body: JSON.stringify(_getParam(attrStatusModify))
          }
          
          ut.ajaxJson(_url, _ajaxOpt, _callBackUpdateModifyStatus); 
        }
        let _this = this;
        _updateModifyStatus(attrStatusModify);
      },
      // 영양성분 SAP 전송
      submitNutAttInfo: function() {      

        function _callBackSubmitNutAttInfo(rtnData) {
          if (rtnData.rtnMsg == "F") {
            alert("속성정보 전송에 실패했습니다.");
            return ;
          }

          let _prodCd  = "<c:out value='${prodInfo.prodCd}'/>";
          let _l3Cd    = "<c:out value='${prodInfo.l3Cd}'/>";
          let _msg     = rtnData.rtnMsg;

          let _frameManageAttrInfoObj = parent.document.getElementById("frameManageAttrInfo");
          
          const _param   = "prodCd=" + _prodCd + "&l3Cd=" + _l3Cd + "&msg=" + _msg;
          const _url = '<c:url value="/edi/product/NEDMPRO0201.do"/>';

          _frameManageAttrInfoObj.src = _url + "?" + _param;
          
          return ;
        }

        function _getParam() {
          let [_inputNutCdList, _inputNutAmtList] = pf._getNutInputInfos();
          let _prodCd  = "<c:out value='${prodInfo.prodCd}'/>";
          let _srcmkCd = "<c:out value='${prodInfo.srcmkCd}'/>";
          let _l3Cd    = "<c:out value='${prodInfo.l3Cd}'/>";
          let _entpCd  = "<c:out value='${epcLoginVO.repVendorId}'/>";
          let _seq     = ("<c:out value='${seqAttr}'/>" * 1) + 1; // 저장해야하는 다음 순번
          let _aprvFg  = "1" // 신청
          let _regDt   = ut.getToday();
          let _regTm   = ut.getTime();
          
          let _paramMap = {};

          _paramMap["nutCd"]        = _inputNutCdList;
          _paramMap["nutAmt"]       = _inputNutAmtList;
          _paramMap["prodCd"]       = _prodCd;
          _paramMap["srcmkCd"]      = _srcmkCd;
          _paramMap["l3Cd"]         = _l3Cd;
          _paramMap["entpCd"]       = _entpCd;
          _paramMap["seq"]          = _seq;
          _paramMap["aprvFg"]       = _aprvFg;
          _paramMap["regDt"]        = _regDt;
          _paramMap["regTm"]        = _regTm;
          return _paramMap;
        }

        function _validation() {
          let _cntNotNutAttRes = "<c:out value='${cntNotNutAttRes}'/>" * 1;
          if (_cntNotNutAttRes > 0) {
            alert("요청한 상품 속성 중 응답받지 못한 속성 값이 있습니다.");
            return false;
          }

          if (pf.getStatusModifyAttr() !== "C") {
            alert("영양성분값 확정이 필요합니다.");
            return false;
          }

          return true;
        }
        
        function _submitNutAttInfo() {
          if (!_validation()) {
            return ;
          }

          if (!confirm("상품속성 정보 전송하시겠습니까?")) {
            return;
          }
          
          const url = '<c:url value="/edi/product/submitNutAttInfo.json"/>';
          const ajaxOpt = {
              method: "POST",
              headers: {
                "Content-type": "application/json"
              },
              body: JSON.stringify(_getParam())
          }
          ut.ajaxJson(url, ajaxOpt, _callBackSubmitNutAttInfo);
        }
        let _this = this;
        _submitNutAttInfo();
      }
    }
  })();
</script>
</body>
</html>
