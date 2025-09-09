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
                <li class="tit">상품 조회내역</li>
              </ul>
              <div style="width: 100%; height: 100%; overflow-y: scroll; overflow-x: hidden; table-layout: fixed;">
                <table id="dataTable" cellpadding="1" cellspacing="1" border="0" width="100%" bgcolor=efefef>
                  <colgroup>
                    <col style="width: 33%" />
                    <col style="width: 33%" />
                    <col style="width: 34%" />
                    <col />
                  </colgroup>
                  <tr bgcolor="#e4e4e4">
                    <th id="thAttNm">분석속성이름</th>
                    <th id="thAttCdVal">입력한 분석상품속성값</th>
                    <th id="thAttAprvStat">승인상태</th>
                  </tr>
                  <tbody id="attrAprvInfoTBody" />
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
        pd.fetchAttrAprvInfo();
      }
    }
  })();
  
  const pf = (function pageFunction() {
    return {
    } 
  })();

  const pd = (function pageData() {
    
    return {
      fetchAttrAprvInfo: function() {

		function _getAprvStatusVal2Nm(aprvFg) {
	
		  if (!aprvFg || aprvFg === '1') {
		    return "심사중";
		  }

		  if (aprvFg === '2') {
			return "승인";
	      }
	      
		  if (aprvFg === '3') {
			return "반려";
	      }

	      return "-";
		}

		function _getAttrValNmConverted(attValNmReq) {
			if (attValNmReq) {
				return "-";
			}

			return attValNmReq;
		}
        
		function _getAttrAprvInfoRowHtml(prodAttrInfoRow) {
		  return "<tr>" 
		       + "<td align='center'>" + prodAttrInfoRow.ATT_NM + "</td>"
		       + "<td align='center' style='line-height: 25px;'>" + prodAttrInfoRow.ATT_VAL_NM_REQ + "</td>"
		       + "<td align='center'>" + _getAprvStatusVal2Nm(prodAttrInfoRow.APRV_FG_SAP) + "</td>"
		       + "</tr>";
		}
        
		function _createAttrAprvInfoDom(prodAttrInfo) {
		  let _attrAprvInfoTBodyObj = document.getElementById("attrAprvInfoTBody");
		  let _attrAprvInfoDom = "";
		  for (let idx=0; idx<prodAttrInfo.length; idx++) {
		    _attrAprvInfoDom = _attrAprvInfoDom + _getAttrAprvInfoRowHtml(prodAttrInfo[idx]);
		  }

		  _attrAprvInfoTBodyObj.innerHTML = _attrAprvInfoDom;
		}
        
		function _callBackFetchProdAttrInfo(prodAttrInfo) {
		  if (!prodAttrInfo || !prodAttrInfo.length) {
		    return;
		  }

		  _createAttrAprvInfoDom(prodAttrInfo);
		}
        
        function _getParam() {
          let _prodCd   = "<c:out value='${prodCd}'/>";
          let _l3Cd     = "<c:out value='${l3Cd}'/>";
          let _aprvStat = "<c:out value='${aprvStat}'/>";

          let _paramMap = {};

          _paramMap["prodCd"]   = _prodCd;
          _paramMap["l3Cd"]     = _l3Cd;
          _paramMap["aprvStat"] = _aprvStat;
          
          return _paramMap; 
        }

        function _fetchAttrAprvInfo() {
          const _url = '<c:url value="/edi/product/getAttrAprvInfoAProd.json"/>';
          const _ajaxOpt = {
            method: "POST",
            headers: {
              "Content-type": "application/json"
            },
            body: JSON.stringify(_getParam())
          }
          ut.ajaxJson(_url, _ajaxOpt, _callBackFetchProdAttrInfo);
        }
        _fetchAttrAprvInfo();
      }
    }
  })();
</script>
</body>
</html>
