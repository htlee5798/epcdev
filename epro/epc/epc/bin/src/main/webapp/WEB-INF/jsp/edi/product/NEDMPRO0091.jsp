<%-- 
- Author(s): NBMSHIP 
- Created Date: 
- Version : 1.0
- Description : 분석상품속성관리

--%>
<%@ include file="../common.jsp"%>
<%@ include file="/common/scm/scmCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
			   <li class="tit">상품 분석속성</li>
			     <li class="btn" id="submitBtn" style="display:none;">
			       <a href="#" class="btn" onclick="pd.submitAttOptInfo();" ><span>전송</span></a>
			     </li>
				 <li class="btn" id="modifyBtn" style="display:none;">
				   <a href="#" class="btn" onclick="pd.updateModifyStatusAttr('M');" ><span>확정취소</span></a>
				 </li>
				 <li class="btn" id="confirmBtn" style="display:none;">
				   <a href="#" class="btn" onclick="pd.updateModifyStatusAttr('C');" ><span>확정</span></a>
				 </li>
			     <li class="btn" id="saveBtn" style="display:none;">
			     <a href="#" class="btn" onclick="pd.saveAttrOptInfo();" ><span>저장</span></a>
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
        pd.setAttrInfo();
        this.alertSubmitResponseMessage();
      },
      
      alertSubmitResponseMessage: function() {
        let msg = "<c:out value='${prodInfo.msg}'/>";
        if (msg === "S") {
          alert("상품 속성 전송이 완료되었습니다.");
        }

        if (msg == "ALREADY_REQUEST") {
          alert("속성정보 요청이 이미 진행됐습니다.")
          return;
        }
      }
    }
  })();

  const ut = {
    // Ajax 호출 함수 (호출url, 호출정보, 응답콜백함수)
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
    // select 선택된 옵션정보 가져온다.
    getSelectedOpt: function(id) {
      let selectedObj = document.getElementById(id);
      let selectedOpt = {};
      if (!selectedObj) {
        return false;
      }

      selectedOpt = {"value": selectedObj.options[selectedObj.selectedIndex].value,
        "text": selectedObj.options[selectedObj.selectedIndex].text
      };
      return selectedOpt;
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
        day="0"+day;
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
    }
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
      // 상품 분석속성 값 확정 상태 받는다.
      getStatusModifyAttr: function() {
        let _statusModifyAttr = "";
        let _attrSelectObjs = document.getElementsByClassName("attrSelect");
        if (!_attrSelectObjs) {
          alert("관리자 확인 필요합니다_1");
        }

        _statusModifyAttr = _attrSelectObjs[0].dataset["chgFg"];
        if (!_attrSelectObjs[0].dataset["chgFg"]) {
          _statusModifyAttr = "M";
        }
        
        return _statusModifyAttr;
      },
      // 상품 분석속성 값 및 코드 값을 받는다.
      _getAttrOptVals: function() {
        let _attrSelectObjs = document.getElementsByClassName("attrSelect");
        let _selectedAttrIdList  = [];
        let _selectedAttrValList = [];

        for (let idx = 0; idx < _attrSelectObjs.length; idx++) {
          let _attrSelectObj = _attrSelectObjs[idx];
          let _selectedAttr = _attrSelectObj.options[_attrSelectObj.selectedIndex];
          if (!_selectedAttr.value) continue;

          let _selectedAttrId  = _attrSelectObj.dataset["attId"];
          let _selectedAttrVal = _selectedAttr.value;

          _selectedAttrIdList.push(_selectedAttrId);
          _selectedAttrValList.push(_selectedAttrVal);
        }
        return [_selectedAttrIdList, _selectedAttrValList]
      }
    } 
  })()
   
  const pd = (function pageDataFunction() {
    return {
      // 상품 분석속성 세팅
      setAttrInfo: function() {
        function _setAttrNmDataToSelectDom(attrData) {
          if (!attrData) {
            return;
          }
            
          for (let attrIdx = 0; attrIdx < attrData.length; attrIdx++) {
            if (!attrData[attrIdx].ATT_ID) continue;
            let _selectObj = document.getElementById("attrInfo" + attrIdx);
            _selectObj.dataset.attId  = attrData[attrIdx].ATT_ID;
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
          
          return "" 
        }

        function _createThTagAttNm(attrData, attIdx) {
          return '<th>' + attrData.ATT_NM + '</th>'
        }
        
        function _crateTdTagSelectAttNm(attrData, attIdx) {
          return '<td>'
               + '<select class="attrSelect" id="attrInfo' + attIdx + '">'
               + '<option value="">' + '-' + '</option>'
               + '</select>'
               + '</td>'
        }
        
        function _setSelectDomAttrInfo(attrData) {
          let _targetDom = document.getElementById("param_frame");
          let _inputSelectDomAttrInfo = "";
          for (let attrIdx = 0; attrIdx < attrData.length; attrIdx++) {
            if (!attrData[attrIdx].ATT_ID)continue;

            // 분석속성 입력 html 생성
            _inputSelectDomAttrInfo = _inputSelectDomAttrInfo +  
              _showTrTagInTBody(attrIdx, "<tr>") + 
              _createThTagAttNm(attrData[attrIdx], attrIdx) + _crateTdTagSelectAttNm(attrData[attrIdx], attrIdx) +
              _showTrTagInTBody(attrIdx, "</tr>")
          }

          _targetDom.insertAdjacentHTML('beforeend', _inputSelectDomAttrInfo);
        }

        function _callBackSetAttrInfo(attrData) {
          if ( !attrData || !attrData.length )return; 
          
          _setSelectDomAttrInfo(attrData);
          _setAttrNmDataToSelectDom(attrData);

          _this.setAttrOptInfo();
        }

        function _getParam() {
          let _l3Cd = "<c:out value='${prodInfo.l3Cd}'/>";
          let _paramMap = {};
          _paramMap["l3Cd"] = _l3Cd;
          return _paramMap
        }
        
        function _setAttrInfo() {         
          const _url = '<c:url value="/edi/product/getGrpAttrInfo.json"/>';
          const _ajaxOpt = {
              method: "POST",
              headers: {
                "Content-type": "application/json"
              },
              body: JSON.stringify(_getParam())
          }   
          ut.ajaxJson(_url, _ajaxOpt, _callBackSetAttrInfo);     
        }

        let _this = this;  
        _setAttrInfo();
      },  
      // 상품 분석속성값 세팅
      setAttrOptInfo: function() {
        function _getAttrOptArrWithAttId(attrOptData, attId) {
          let _attrOptArr = [];
          
          for (let optIdx = 0; optIdx < attrOptData.length; optIdx++) {
            if (attrOptData[optIdx].ATT_ID === attId) {
              _attrOptArr.push(attrOptData[optIdx]); 
            }
          }

          return _attrOptArr;
        }

        function _createAttrOptsDom(attrOptData, attrSelectObjs, attrIdx) {
          let _attIdFinding      = attrSelectObjs[attrIdx].dataset.attId;
          let _attrOptArr        = _getAttrOptArrWithAttId(attrOptData, _attIdFinding);          
          let _attrOptionsHtml = "";

          for (let optIdx = 0; optIdx < _attrOptArr.length; optIdx++) {
            _attrOptionsHtml = _attrOptionsHtml 
                            + "<option value='" + _attrOptArr[optIdx].ATT_VAL_ID + "'>"
                            + _attrOptArr[optIdx].ATT_VAL_NM
                            + "</option>";
          }
          attrSelectObjs[attrIdx].insertAdjacentHTML('beforeend', _attrOptionsHtml);
        }
      
        function _setSelectDomAttrOptInfo(attrOptData) {
          let _attrSelectObjs = document.getElementsByClassName("attrSelect");
          if (!_attrSelectObjs) return;

          for (let attrIdx = 0; attrIdx < _attrSelectObjs.length; attrIdx++) {
            _createAttrOptsDom(attrOptData, _attrSelectObjs, attrIdx);
          }
        }

        function _callBackSetAttrOptInfo(attrOptData) {
          _setSelectDomAttrOptInfo(attrOptData)
          _this.setAttrSelectedOptInfo();
        }

        function _getParam() {
          let _l3Cd = "<c:out value='${prodInfo.l3Cd}'/>";
          let _paramMap = {};
          _paramMap["l3Cd"] = _l3Cd;
          return _paramMap
        }
        
        function _setAttrOptInfo() {
          const _url = '<c:url value="/edi/product/getGrpAttrOptInfo.json"/>';
          const _ajaxOpt = {
              method: "POST",
              headers: {
                "Content-type": "application/json"
              },
              body: JSON.stringify(_getParam())
          }     
          ut.ajaxJson(_url, _ajaxOpt, _callBackSetAttrOptInfo);
        }
  
        let _this = this;
        _setAttrOptInfo();
      },
      // 분석속성 저장했던 값 선택값으로 할당
      setAttrSelectedOptInfo: function() {   
        function _getSelectObjWithAttID(attIdFind) {
          let _attrSelectObjs = document.getElementsByClassName("attrSelect");
          for (let idx = 0; idx < _attrSelectObjs.length; idx++) {
            if (_attrSelectObjs[idx].dataset["attId"] == attIdFind) 
              return _attrSelectObjs[idx]
          }
          return "";
        }

        function _putOptValToAttrOpt(selectedObj, attValId) {
          for (let idx = 0; idx < selectedObj.children.length; idx++) {
            if (selectedObj.children[idx].value == attValId) {
              selectedObj.children[idx].setAttribute('selected', '');
              return ;
            }
          }
        }
        
        function _callBackSetAttrSelectedOpt(attrData) {
          let _attrStatusModify = 'M';
          for (let idx=0; idx<attrData.length; idx++) {
            let _attIdFind = attrData[idx].ATT_ID; 
            let _attValId  = attrData[idx].ATT_VAL_ID; 
            let _selectedObj = _getSelectObjWithAttID(_attIdFind);
            if (!_selectedObj) {
              continue;
            }

            _putOptValToAttrOpt(_selectedObj, _attValId);
            
            _selectedObj.dataset.chgFg = attrData[idx].CHG_FG;
            if (attrData[idx].CHG_FG != 'M') {
              _attrStatusModify = 'C';
            }
          }
          
          let _cntNotResponseAttr = "<c:out value='${cntNotResponseAttr}'/>" * 1;
          let _attrSelectObjs = document.getElementsByClassName("attrSelect");
          let _isDisable = false;

          if (_attrStatusModify == 'C' || _cntNotResponseAttr > 0) {
            _isDisable = true;
          }
          
          for (let idx = 0; idx < _attrSelectObjs.length; idx++) {
            _attrSelectObjs[idx].disabled = _isDisable;
          }

          pf.manageDisplayActBtn();
        }

        function _getParam() {
          let _prodCd = "<c:out value='${prodInfo.prodCd}'/>";
          let _paramMap = {};
          _paramMap["prodCd"] = _prodCd;
          return _paramMap;
        }
        
        function _setAttrSelectedOpt() {
          const _url = '<c:url value="/edi/product/getGrpAttrSelectedOptInfo.json"/>';
          const _ajaxOpt = {
              method: "POST",
              headers: {
                "Content-type": "application/json"
              },
              body: JSON.stringify(_getParam())
          }
          ut.ajaxJson(_url, _ajaxOpt, _callBackSetAttrSelectedOpt);
        }
        _setAttrSelectedOpt();        
      },
      // 분석속성 값 저장
      saveAttrOptInfo: function() {
        function _callBackSaveAttrOptInfo(rsltData) {
          if (rsltData.rtnMsg == "F") {
            alert("분석속성 저장실패했습니다.");
            return ;
          }
          alert("분석속성 저장하였습니다.")
          return ;
        }

        function _getParam() {
          let [_selectedAttrIdList, _selectedAttrValList] = pf._getAttrOptVals();
          let _prodCd = "<c:out value='${prodInfo.prodCd}'/>";
          let _entpCd = "<c:out value='${epcLoginVO.repVendorId}'/>";

          let _paramMap = {};
          _paramMap["grpAttrId"]    = _selectedAttrIdList;
          _paramMap["grpAttrValId"] = _selectedAttrValList;
          _paramMap["prodCd"]       = _prodCd;
          _paramMap["entpCd"]       = _entpCd;
          return _paramMap;
        }
        
        function _saveAttrOptInfo() {
          // 검증란
          let [_selectedAttrIdList, _selectedAttrValList] = pf._getAttrOptVals();
          if (_selectedAttrIdList.length == 0) {
            alert("선택된 속성값이 없습니다.");
            return ;
          }

          let _attrSelectObjs = document.getElementsByClassName("attrSelect");
          if (_attrSelectObjs[0].disabled) {
            alert("확정상태여서 저장할 수 없습니다.");
            return ;
          }
          
          if (!confirm("저장하시겠습니까?")) {
            return;
          }      

          const _url = '<c:url value="/edi/product/saveGrpAttrOptInfo.json"/>';
          const _ajaxOpt = {
              method: "POST",
              headers: {
                "Content-type": "application/json"
              },
              body: JSON.stringify(_getParam())
          }

          ut.ajaxJson(_url, _ajaxOpt, _callBackSaveAttrOptInfo);  
        }
        let _this = this;
        _saveAttrOptInfo();
      },
      // 분석속성 확정 및 확정해제
      updateModifyStatusAttr: function(attrStatusModify) {
        function _callBackUpdateModifyStatus(rtnData) {
          if (rtnData.rtnMsg == "M" || rtnData.rtnMsg == "C") {
            if (rtnData.rtnMsg == "C") {
              alert("확정처리 되었습니다.");
            }
            if (rtnData.rtnMsg == "M") {
              alert("확정취소처리 되었습니다.");
            }
            _this.setAttrSelectedOptInfo();
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

        function _validation() {
          let _attrSelectObjs = document.getElementsByClassName("attrSelect");
          if (_attrSelectObjs[0].dataset["chgFg"] == attrStatusModify) {
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
          if (!_validation()) {
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
          
          const _url = '<c:url value="/edi/product/updateModifyStatusAttr.json"/>';
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
      // 분석속성 SAP 전송
      submitAttOptInfo: function() {      
        /* function _getAttrOptValsAndTexts() {
          let _attrSelectObjs = document.getElementsByClassName("attrSelect");
          let _selectedAttrValList     = [];
          let _selectedAttrValTextList = [];

          for (let idx=0; idx<_attrSelectObjs.length; idx++) {
            let _attrSelectObj = _attrSelectObjs[idx];
            let _selectedAttr  = _attrSelectObj.options[_attrSelectObj.selectedIndex];
            if (!_selectedAttr.value) continue;

            let _selectedAttrId      = _attrSelectObj.dataset["attId"];
            let _selectedAttrVal     = _selectedAttr.value;
            let _selectedAttrValText = _selectedAttr.text;

            _selectedAttrValList.push(_selectedAttrVal);
            _selectedAttrValTextList.push(_selectedAttrValText);
          }
          return [_selectedAttrValList,_selectedAttrValTextList]
        } */

        function _callBackSubmitAttOptInfo(rtnData) {
          if (rtnData.rtnMsg == "F") {
            alert("속성정보 전송에 실패했습니다.");
            return ;
          }
          
          let _prodCd  = "<c:out value='${prodInfo.prodCd}'/>";
          let _l3Cd    = "<c:out value='${prodInfo.l3Cd}'/>";
          let _msg     = rtnData.rtnMsg;

          let _frameManageAttrInfoObj = parent.document.getElementById("frameManageAttrInfo");
          
          const _param   = "prodCd=" + _prodCd + "&l3Cd=" + _l3Cd + "&msg=" + _msg;
          const _url = '<c:url value="/edi/product/NEDMPRO0091.do"/>';

          _frameManageAttrInfoObj.src = _url + "?" + _param;
          
          return ;
        }

        function _getParam() {
          //let [_selectedAttrValList, _selectedAttrValTextList] = _getAttrOptValsAndTexts();
          let [_selectedAttrIdList, _selectedAttrValList] = pf._getAttrOptVals();
          let _prodCd  = "<c:out value='${prodInfo.prodCd}'/>";
          let _srcmkCd = "<c:out value='${prodInfo.srcmkCd}'/>";
          let _l3Cd    = "<c:out value='${prodInfo.l3Cd}'/>";
          let _entpCd  = "<c:out value='${epcLoginVO.repVendorId}'/>";
          let _seq     = ("<c:out value='${seqAttr}'/>" * 1) + 1; // 저장해야하는 다음 순번
          let _aprvFg  = "1" // 신청
          let _regDt   = ut.getToday();
          let _regTm   = ut.getTime();
          
          let _paramMap = {};

          _paramMap["grpAttrValId"] = _selectedAttrValList;
          _paramMap["grpAttrId"]    = _selectedAttrIdList;
          _paramMap["prodCd"]       = _prodCd;
          _paramMap["srcmkCd"]      = _srcmkCd;
          _paramMap["l3Cd"]         = _l3Cd;
          _paramMap["entpCd"]       = _entpCd;
          _paramMap["seq"]          = _seq;
          _paramMap["aprvFg"]       = _aprvFg;
          _paramMap["regDt"]        = _regDt;
          _paramMap["regTm"]         = _regTm;
          return _paramMap;
        }

        function _validation() {
          let _cntNotResponseAttr = "<c:out value='${cntNotResponseAttr}'/>" * 1;
          if (_cntNotResponseAttr > 0) {
            alert("요청한 상품 속성 중 응답받지 못한 속성 값이 있습니다.");
            return false;
          }

          if (pf.getStatusModifyAttr() !== "C") {
            alert("분석속성값 확정이 필요합니다.");
            return false;
          }

          return true;
        }
        
        function _submitAttOptInfo() {
          if (!_validation()) {
            return ;
          }

          if (!confirm("상품속성 정보 전송하시겠습니까?")) {
            return;
          }
          
          const url = '<c:url value="/edi/product/submitGrpAttrOptInfo.json"/>';
          const ajaxOpt = {
              method: "POST",
              headers: {
                "Content-type": "application/json"
              },
              body: JSON.stringify(_getParam())
          }
          ut.ajaxJson(url, ajaxOpt, _callBackSubmitAttOptInfo);
        }
        let _this = this;
        _submitAttOptInfo();
      }
    }
  })();
</script>
</body>
</html>
