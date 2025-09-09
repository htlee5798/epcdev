<%-- 
- Author(s): NBMSHIP 
- Created Date: 
- Version : 1.0
- Description : 영양성분속성 조회장표

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

        <div id="wrap_menu">
          <div class="wrap_search">
            <div class="bbs_search">
              <ul class="tit">
                <li class="tit">영양성분 조회</li>
                <li class="btn"><a href="#" class="btn" onclick="pd.fetchProdAttrInfo('1');"><span><spring:message code="button.common.inquire" /></span></a></li>
              </ul>
              <table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
                <colgroup>
                  <col style="width: 10%;" />
                  <col style="width: 20%;" />
                  <col style="width: 13%;" />
                  <col style="width: 27%;" />
                  <col style="width: 10%;" />
                  <col style="width: 20%;" />
                </colgroup>
                <tr>
                  <th></span> 협력업체</th>
                  <td>
                    <c:choose>          
                      <c:when test="${epcLoginVO.vendorTypeCd != '06'}">
                       <c:if test="${not empty param.entpCode}">
                         <c:if test="${param.entpCode ne 'all'}">
                           <html:codeTag objId="entp_cd" objName="entp_cd" width="80px;" selectParam="${param.entpCode}" dataType="CP" comType="SELECT" formName="form" defName="전체"  />   
                         </c:if>         
                         <c:if test="${param.entpCode eq 'all'}">
                           <html:codeTag objId="entp_cd" objName="entp_cd" width="80px;"  dataType="CP" comType="SELECT" formName="form" defName="전체"  />  
                         </c:if>           
                       </c:if>
                       <c:if test="${ empty param.entpCode}">
                         <html:codeTag objId="entpCd" objName="entpCd" width="80px;"  dataType="CP" comType="SELECT" formName="form" defName="전체"  />
                       </c:if>
                      </c:when>
                    </c:choose> 
                  </td>
                  
                  <th>판매(88)코드</th>
                  <td>
                    <input type="text" name="srchSellCode" id="srchSellCode" style="width: 130px;" />
                    <a href="#" class="btn" onclick="pf.enterSellCdsWindow();"><span>입력</span></a>
                  </td>
                  <th>대분류</th>
                  <td>
                    <select id="srchL1Cd" name="srchL1Cd" class="required" style="width: 150px;">
                      <option value="">선택</option>
                    </select>
                  </td>
                  <td></td>
                </tr>
                <tr>
                  <th>확정여부</th>
                  <td>
                    <select id="srchAprvStat" name="srchAprvStat">
                      <option value="">전체</option>
                      <option value="aprvPossible">수정가능</option>
                      <option value="aprvIng">심사중</option>
                      <option value="aprvDone">심사완료</option>
                    </select>
                  </td>
                  <td colspan="5"></td>
                </tr>
              </table>
              <table cellpadding="0" cellspacing="1" border="0" width=100% bgcolor=efefef>
                <tr>
                  <td colspan="6" bgcolor=ffffff>
                    <strong>&nbsp;<font color="red">※업체별로 상품 코드가 많을 경우 수십초의 시간이 걸릴 수 있습니다.</font></strong><br />
                  </td>
                </tr>
              </table>
            </div>
          </div>

          <div class="wrap_con">
            <div class="bbs_list">

              <ul class="tit">
                <li class="tit">상품 조회내역</li>
              </ul>
              <div style="width: 100%; height: 458px; overflow-x: hidden; overflow-y: scroll; overflow-x: scroll; table-layout: fixed;">
                <table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=800px bgcolor=efefef>
                  <colgroup>
                    <col style="width: 10%" />
                    <col style="width: 12%" />
                    <col style="width: 14%" />
                    <col style="width: 30%" />
                    <col style="width: 16%" />
                    <col />
                  </colgroup>
                  <tr bgcolor="#e4e4e4">
                    <!-- <th>확정여부</th> -->
                    <th>상품구분</th>
                    <th id="thProdCd">상품코드</th>
                    <th id="thSrcmkCd">판매(88)코드</th>
                    <th id="thProdNm">상품명</th>
                    <th>ECO 입력속성 / 총 개수</th>
                  </tr>
                  <tbody id="dataListbody" />
                </table>
              </div>

              <div id="paging_div" align="center">
                <ul class="paging_align" id="paging-ul" style="width: 400px"></ul>
              </div>
            </div>
          </div>
        </div>
      </form>
    </div>

    <div id="footer">
      <div id="footbox">
        <div class="msg" id="resultMsg"></div>
        <div class="notice"></div>
        <div class="location">
          <ul>
            <li>홈</li>
            <li>상품</li>
            <li>신규상품관리</li>
            <li class="last">영양성분 조회</li>
          </ul>
        </div>
      </div>
    </div>


  </div>
  <script>
  $(document).ready(function() {
    pe.initPage();
  });

  const pe = (function pageEventFunction(){
    return {
      initPage: function() {
        pd.initL1Cd();
      }
    }
  })();

  const ut = {
    ajaxJson : function(url, opt, cbFunc) {
      fetch(url, opt)
      .then((res) => {
        console.log("httpStatus: " + res.status);
        return res.json()
      })
      .then((dataJson) => { 
        cbFunc(dataJson)
      })
      .catch((error) => console.log("ajaxJson Err : ", error));
    },
    
    getSelectedOpt: function(idFind) {
      let selectedObj = document.getElementById(idFind);
      let selectedOpt = {};
      if (!selectedObj) {
        return false;
      }

      selectedOpt = {"value": selectedObj.options[selectedObj.selectedIndex].value,
        "text": selectedObj.options[selectedObj.selectedIndex].text
      };
      return selectedOpt;
    }
  }

  const pf = (function pageFunction() {
    return {
      showAttrAprvInfoWindow: function(aprvStat, prodCd, l3Cd) {
        const urlParam = "prodCd=" + prodCd + "&l3Cd=" + l3Cd + "&aprvStat=" + aprvStat;
        const url = '<c:url value="/edi/product/NEDMPRO019001.do"/>' + "?" + urlParam;
        const windowStatus = 'height=400, width=450, top=50, left=50, location, resizable';

        window.open(url, "_blank", windowStatus);
      },
      
      enterSellCdsWindow: function() {
        const url = '<c:url value="/edi/product/NEDMPRO008002.do"/>';
        const windowStatus = 'height=320, width=300, top=50, left=50, location, resizable';

        window.open(url, "_blank", windowStatus);
      }, 
      
      getSellCdListWithoutComma: function(strWithComma) {		
        const arrSellCd = strWithComma.split(",");
		    const regExpRemoveNotNumber = /\D/gi;

		    for (let idx = 0; idx < arrSellCd.length; idx++) {
		      let srcmkCdConverted = arrSellCd[idx].replace(regExpRemoveNotNumber,"");

		      arrSellCd[idx] = srcmkCdConverted;
		    }
		
        return arrSellCd;
      }
    } 
  })();

  const pd = (function pageData() {
    
    return {
      initL1Cd: function() {
        let l1CdList = []; 
          <c:forEach items="${l1CdList}" var="l1Cd">
            l1CdList.push({name: "${l1Cd.L1_NM}", cd: "${l1Cd.L1_CD}"}) 
          </c:forEach>

        let l1CdSelectDom = document.getElementById('srchL1Cd'); 

        if (l1CdSelectDom.children.length == 1) {
          l1CdList.forEach(function(list, i) {
            let opt = document.createElement('option');
            opt.setAttribute('value', list.cd);
            opt.innerText = list.name;
            l1CdSelectDom.appendChild(opt);
          })
        }
      },
		//
      fetchProdAttrInfo: function(pageIdx) {
          
        function _entpCd2Arr() {
          let _srchEntpCd = ut.getSelectedOpt("entpCd").value;
          let _entpCdArr  = [];
        
          if (!_srchEntpCd) {
            let _entpCds = document.getElementById('entpCd').options; 
            for (let idx = 0; idx < _entpCds.length; idx++) {
              if (!_entpCds[idx].value) continue;
              _entpCdArr.push(_entpCds[idx].value);
            }
          }
        
          if (!_entpCdArr.length) return false;
          return _entpCdArr;
        }      
        
        // Dom객체에 필요한 데이터 넣기
        function _assginAttrDataToDom(prodAttrInfo) {
          for(let idx = 0; idx < prodAttrInfo.length; idx++) {
            let prodObj = document.getElementById("prod" + idx);
            prodObj.dataset.prodCd  = prodAttrInfo[idx].PROD_CD;
            prodObj.dataset.srcmkCd = prodAttrInfo[idx].SRCMK_CD;
            prodObj.dataset.l3Cd    = prodAttrInfo[idx].L3_CD;
          }
        }
        //
        function _getAprvStatName(attrData) {
          if (attrData.APRV_STAT === "APRVING") {
            if (attrData.REJECT_ATT_CNT > 0) {
              return "심사중/반려"
            }
            return "심사중"
          }

          if (attrData.APRV_STAT === "APRVDONE") {
            if (attrData.REJECT_ATT_CNT > 0) {
              return "심사완료/반려"
            }
            return "심사완료"
          }
          
          return "수정가능"
          }
          // 
        function _createNoAttrTbodyDom() {
          return '<tr class="r1"  bgcolor="ffffff">' 
               + '<td align="center" colspan="5" style="line-height: 25px;">조회되는 상품이 없습니다.</td>'               
               + '</tr>';
        }
        //
        function _createNoAttrInfoDom() {
          let _tBodyDataDom = "";
          let _tBodyObj = document.getElementById("dataListbody");
          
          _tBodyDataDom = _createNoAttrTbodyDom();
          
          _tBodyObj.innerHTML = _tBodyDataDom;
        }
        // 상품 영양성분 Dom 객체 
        function _createTbodyDom(prodAttrInfo, idx) {
          return '<tr class="r1"  bgcolor="ffffff">' 
               + '<td align="center" style="cursor:pointer;" onClick="pf.showAttrAprvInfoWindow(\'' + prodAttrInfo.APRV_STAT + '\',\'' + prodAttrInfo.PROD_CD + '\',\'' + prodAttrInfo.L3_CD + '\')">' + _getAprvStatName(prodAttrInfo) + '</td>'              
               + '<td align="center">' + prodAttrInfo.PROD_CD + '</td>' 
               + '<td align="center">' + prodAttrInfo.SRCMK_CD + '</td>' 
               + '<td align="center" id="prod' + idx + '" style="line-height: 25px;">' + prodAttrInfo.PROD_NM + '</td>' 
               + '<td align="center"> '+ prodAttrInfo.CNT_NUT_CD_SAVED + ' / ' + prodAttrInfo.CNT_NUT_CD + '</td>' 
               + '</tr>';
        }
        // 페이지에 데이터 dom객체들 넣는다.
        function _createAttrInfoDom(prodAttrInfo) {
          let _tBodyDataDom = "";
          let _tBodyObj = document.getElementById("dataListbody");
        
          for (let idx = 0; idx < prodAttrInfo.length; idx++) {
            _tBodyDataDom = _tBodyDataDom + _createTbodyDom(prodAttrInfo[idx], idx);
          }
        
          _tBodyObj.innerHTML = _tBodyDataDom;
        } 
        // Ajax로 가져온 데이터로 할 것들
        function _callBackFetchProdAttrInfo(prodAttrInfoJson) {
          let _prodAttrInfo = prodAttrInfoJson.nutAttrInfoList;
          if (!_prodAttrInfo || !_prodAttrInfo.length ) {

            _createNoAttrInfoDom();
          } 

          if (_prodAttrInfo.length > 0) {            
            _createAttrInfoDom(_prodAttrInfo); // 데이터를 dom객체로 만들어 넣는다.
            _assginAttrDataToDom(_prodAttrInfo);  // dom객체에 데이터를 넣는다.
          }
          // 페이징 부분 처리
          let _pagingTargetObj = document.getElementById("paging-ul");
          _pagingTargetObj.innerHTML = prodAttrInfoJson.pageAttrInfo;
        }
        
        function _getParam(pageIdx) {
          let _srchEntpCd       = ut.getSelectedOpt("entpCd").value;
          let _venCds           = [];
          if (!_srchEntpCd) {
            _venCds = _entpCd2Arr();
          }
          let _srchL1Cd         = ut.getSelectedOpt("srchL1Cd").value;
          let _srchSellCd       = document.getElementById("srchSellCode").value;
          let _srchSellCds      = [];
          if (_srchSellCd.indexOf(",") !== -1) {
            _srchSellCds = pf.getSellCdListWithoutComma(_srchSellCd);
            _srchSellCd = "";
          }
          let _srchAprvStat     = ut.getSelectedOpt("srchAprvStat").value;
          let _pageIndex        = pageIdx;
          
          let _paramMap = {};

          _paramMap["srchEntpCd"]    = _srchEntpCd;
          _paramMap["venCds"]        = _venCds;
          _paramMap["srchL1Cd"]      = _srchL1Cd;
          _paramMap["srchSellCd"]    = _srchSellCd;
          _paramMap["srchSellCds"]   = _srchSellCds;
          _paramMap["srchAprvStat"]  = _srchAprvStat;
          _paramMap["pageIndex"]     = _pageIndex;

          return _paramMap
        }

        function _fetchProdAttrInfo(pageIdx) {
          const _url = '<c:url value="/edi/product/getNutAttr.json"/>';
          const _ajaxOpt = {
            method: "POST",
            headers: {
              "Content-type": "application/json"
            },
            body: JSON.stringify(_getParam(pageIdx))
          }
          ut.ajaxJson(_url, _ajaxOpt, _callBackFetchProdAttrInfo);
        }
        _fetchProdAttrInfo(pageIdx);
      }
    }
  })();
</script>
</body>
</html>
