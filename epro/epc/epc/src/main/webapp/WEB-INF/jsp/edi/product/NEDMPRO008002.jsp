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
  <div id="content_wrap" style="width: 100%; height: 100%; overflow-x: hidden; overflow-y: scroll;">
    <div>
      <form name="searchForm" id="searchForm" method="post" action="#">
        <div id="wrap_menu">
          <div class="wrap_con">
            <div class="bbs_list">
              <ul class="tit">
                <li class="tit">판매코드 입력</li>
                <li class="btn"><a href="#" class="btn" onclick="pf.applySellCds2SellCdInput();"><span>판매코드 적용</span></a></li>
              </ul>
              <div style="width: 100%; height: 100%; overflow-y: scroll; overflow-x: hidden; table-layout: fixed;">
                <table id="dataTable" cellpadding="1" cellspacing="1" border="0" width="100%" bgcolor=efefef>
                  <colgroup>
                    <col style="width: 64%" />
                    <col style="width: 18%" />
                    <col style="width: 18%" />
                    <col />
                  </colgroup>
                  <tr bgcolor="#e4e4e4">
                    <th id="thSellCd">조회 판매코드</th>
                    <th id="thAddSellCd"></th>
                    <th id="thRemoveSellCd"></th>
                  </tr>
                  <tbody id="enterSellCdTBody" />
                </table>
              </div>
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

  const ut = {
    ajaxJson : function(url, opt, cbFunc) {
      fetch(url, opt)
      .then((res) => {
        console.log("res : " + res);
        return res.json()
      })
      .then((dataJson) => { 
        cbFunc(dataJson)
      })
      .catch((error) => console.log("ajaxJson Err : ", error));
    }
  }

  const pe = (function pageEventFunction(){
    return {
    
      initPage: function() {
        pf.createSrchSellCdInput();
      }
    }
  })();
  
  const pf = (function pageFunction() {
    return {
    
      createSrchSellCdInput: function(idx) {
        
	    function _hidePrevButton(prevIdx) {

	      let prevBtnAddSellCd = document.getElementById("addSellCdBtn_" + prevIdx);
	      let prevBtnRemoveSellCd = document.getElementById("removeSellCdBtn_" + prevIdx);

	      prevBtnAddSellCd.style.display  = "none";
	      prevBtnRemoveSellCd.style.display  = "none";
	    }
        
		function _getSrchSellCdDom(idx) {
		  return '<tr>'
		       +   '<td align="center">'
		       +     '<input type="text" id="srchSellCd_' + idx + '" name="srchSellCds" minlength="13" maxlength="13">' 
		       +   '</td>'
		       +   '<td>'
		       +     '<a href="#" class="btn" id="addSellCdBtn_' + idx + '" name="addSellCdBtns" onClick="pf.createSrchSellCdInput(' + (idx + 1) + ')" ><span>추가</span></a>'
		       +   '</td>'
		       +   '<td>'
		       +     '<a href="#" class="btn" id="removeSellCdBtn_' + idx + '" name="removeSellCdBtns" onClick="pf.removeASrchSellCdInput(' + (idx) + ')" ><span>삭제</span></a>'
		       +   '</td>' 
		       + '</tr>';
		}
        
        function _createSrchSellCdInput(idx) {
          let _enterSellCdTBody = document.getElementById("enterSellCdTBody");
		  let _srchSellCdObj = _getSrchSellCdDom(idx);
		  
          if (idx && idx != 0) {
            _hidePrevButton(idx-1);
          }

          _enterSellCdTBody.insertAdjacentHTML('beforeend', _srchSellCdObj);
        }

        if (!idx) {
          idx = 0;
        }
        _this = this;
        _createSrchSellCdInput(idx);
      },
	  removeASrchSellCdInput: function(idx) {

		function _showPrevButton(prevIdx) {
		  let prevBtnAddSellCd = document.getElementById("addSellCdBtn_" + prevIdx);
	      let prevBtnRemoveSellCd = document.getElementById("removeSellCdBtn_" + prevIdx);

	      prevBtnAddSellCd.style.display  = "block";
	      prevBtnRemoveSellCd.style.display  = "block";
		}

		function _removeASrchSellCdInput(idx) {
		  let _removeBtnElement = document.getElementById("removeSellCdBtn_" + idx);
		  let _prevIndex = idx-1;
		  if (_prevIndex < 0) {
			alert("삭제할 수 없습니다.")
			return;
		  }
		        
		  _removeBtnElement.parentElement.parentElement.remove();
		  _showPrevButton(_prevIndex);  
		}
		_removeASrchSellCdInput(idx);
	  },
	  applySellCds2SellCdInput: function() {
	    function _convertSellCds2SellCdStr() {
		  let sellCdsElement = document.getElementsByName("srchSellCds");
          let sellCdStr = "";
          
		  for(sellCdElem of sellCdsElement) {
			let sellCdAdded = "," + sellCdElem.value;
			if (!sellCdStr) {
			  sellCdAdded = sellCdElem.value;
		    }
			sellCdStr = sellCdStr + sellCdAdded
		  } 

		  return sellCdStr;
		}

	    function _applySellCds2SellCdInput() {
		  let sellCds = _convertSellCds2SellCdStr()
	      window.opener.document.getElementById("srchSellCode").value = sellCds;
	      window.close();
		}

	    _applySellCds2SellCdInput();
	  }
    } 
  })();

</script>
</body>
</html>
