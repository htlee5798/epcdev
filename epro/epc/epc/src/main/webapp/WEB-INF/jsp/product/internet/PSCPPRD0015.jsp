<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="epcutl" uri="/WEB-INF/tlds/epcutl.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.lottemart.epc.common.util.SecureUtil"%>

<%
    String prodCd    = SecureUtil.stripXSS(request.getParameter("prodCd"));
    String vendorId  = SecureUtil.stripXSS(request.getParameter("vendorId"));
    String tabNo     = "4";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Cache-Control"   content="No-Cache"     >
<meta http-equiv="Pragma"          content="No-Cache"     >

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHeadPopup.do" />

<!-- WISE 그리드 초기화 -->
<script type="text/javascript" for="WG1" event="Initialize()">
    setWiseGridProperty(WG1);
    setHeader();
</script>

<!--    서버와의 통신이 정상적으로 완료되면 발생한다.   -->
<script language=javascript for="WG1" event="EndQuery()">
    hideLoadingMask();
    GridEndQuery();
</script>

<!-- 통신 시 에러 발생 -->
<script language="javascript" for="WG1" event="ErrorQuery(strSource, nCode, strMessage, strDescription)">
    hideLoadingMask();
</script>

<!--     WiseGrid의 셀의 내용이 변경되었을때 발생한다. -->
<script language=javascript for="WG1" event="ChangeCell(strColumnKey,nRow,nOldValue,nNewValue)">
    GridChangeCell(strColumnKey, nRow);
</script>

<!--     WiseGrid의 t_combo타입의 컬럼내용이 변경되었을때 발생합니다  -->
<script language=javascript for="WG1" event="ChangeCombo(strColumnKey,nRow,nOldIndex,nNewIndex)">
    GridChangeCell(strColumnKey, nRow);
</script>
<script type="text/javascript" for="WG1" event="CellClick(strColumnKey,nRow)">
    var gridObj = document.WG1;
</script>

<!-- 그리드 헤더처리 -->
<script type="text/javascript">
    /* 서버와의 통신이 정상적으로 완료되면 발생한다. */
    function GridEndQuery()
    {
        var GridObj = document.WG1;

        //서버에서 mode로 셋팅한 파라미터를 가져온다.
        var mode = GridObj.GetParam("mode");

        if (mode == "search")
        {
            if (GridObj.GetStatus() != "true") // 서버에서 전송한 상태코드를 가져온다.
            {
                var error_msg = GridObj.GetMessage(); // 서버에서 전송한 상태코드값이 false라면 에러메세지를 가져온다.

                if (error_msg!='') // error
                {
                    $('#resultMsg').text('<spring:message code="msg.common.fail.request"/>');
                }
                else // 0건
                {
                    $('#resultMsg').text('<spring:message code="msg.common.info.nodata"/>');
                }
            }
            else
            {
                $('#resultMsg').text('<spring:message code="msg.common.success.select"/>');
            }

            /*
            // 조회된 데이터 checkbox 비활성화-------------------------
            var rowCount = GridObj.getRowCount();
            
            for ( var i=0; i<rowCount; i++) 
            {
                if (GridObj.GetCellValue("approvalChk", i) == '1')
                {
                    // 체크박스 비활성화
                    GridObj.SetCellActivation('CHK', i, 'disable');
                }
            }
            */
        }

        if (mode == "insert" || mode == "update" || mode == "delete")
        {
            if (GridObj.GetStatus() == "true") // 서버에서 전송한 상태코드를 가져온다.
            {
                var saveData = GridObj.GetParam("saveData"); //서버에서 saveData 셋팅한 파라미터를 가져온다.
                alert(saveData);
                doSearch();
            }
            else
            {
                var error_msg = GridObj.GetMessage(); // 서버에서 전송한 상태코드값이 false라면 에러메세지를 가져온다.
                alert(error_msg);
            }
        }
    }

    function setHeader()
    {
        var gridObj = document.WG1;
        
        gridObj.AddHeader("num",        "순번",         "t_number",         100,    40,  false);
        gridObj.AddHeader("strNm",      "점포명",       "t_text",           200,    95,  false);
        gridObj.AddHeader("regDate",    "등록일자",     "t_text",           100,    75,  false);
        gridObj.AddHeader("icon1",      "아이콘1",      "t_checkbox",       100,    50,  true);
        gridObj.AddHeader("icon2",      "아이콘2",      "t_checkbox",       100,    50,  true);
        gridObj.AddHeader("icon3",      "아이콘3",      "t_checkbox",       100,    50,  true);
        gridObj.AddHeader("icon4",      "아이콘4",      "t_checkbox",       100,    50,  true);
        gridObj.AddHeader("icon5",      "아이콘5",      "t_checkbox",       100,    50,  true);
        gridObj.AddHeader("icon6",      "아이콘6",      "t_checkbox",       100,    50,  true);
        gridObj.AddHeader("icon7",      "아이콘7",      "t_checkbox",       100,    50,  true);
        gridObj.AddHeader("icon8",      "아이콘8",      "t_checkbox",       100,    50,  true);
        gridObj.AddHeader("icon9",      "아이콘9",      "t_checkbox",       100,    50,  true);
        gridObj.AddHeader("icon10",     "아이콘10",     "t_checkbox",       100,    50,  true);
        gridObj.AddHeader("icon11",     "아이콘11",     "t_checkbox",       100,    50,  true);
        gridObj.AddHeader("icon12",     "아이콘12",     "t_checkbox",       100,    50,  true);
        gridObj.AddHeader("icon13",     "아이콘13",     "t_checkbox",       100,    50,  true);
        gridObj.AddHeader("icon14",     "아이콘14",     "t_checkbox",       100,    50,  true);
        gridObj.AddHeader("delYn",      "사용안함",     "t_checkbox",       100,    55,  true);
        
        gridObj.AddHeader("strCd",      "점포코드",     "t_text",           200,   100,  false);
        gridObj.AddHeader("prodCd",     "상품코드",     "t_text",           100,   100,  false);
        gridObj.AddHeader("prodNm",     "상품명",       "t_text",           100,   100,  false);
        gridObj.AddHeader("CHK",        "채크",         "t_checkbox",       100,    50,  true);

        gridObj.BoundHeader();
        
        gridObj.SetColHDCheckBoxVisible("delYn", true); // 채크박스 전체 선택 가능
        
        // 수정 가능 컬럼에 표시
        gridObj.SetColCellBgColor("icon1",  '<spring:message code="config.common.grid.enableChangeColor"/>');
        gridObj.SetColCellBgColor("icon2",  '<spring:message code="config.common.grid.enableChangeColor"/>');
        gridObj.SetColCellBgColor("icon3",  '<spring:message code="config.common.grid.enableChangeColor"/>');
        gridObj.SetColCellBgColor("icon4",  '<spring:message code="config.common.grid.enableChangeColor"/>');
        gridObj.SetColCellBgColor("icon5",  '<spring:message code="config.common.grid.enableChangeColor"/>');
        gridObj.SetColCellBgColor("icon6",  '<spring:message code="config.common.grid.enableChangeColor"/>');
        gridObj.SetColCellBgColor("icon7",  '<spring:message code="config.common.grid.enableChangeColor"/>');
        gridObj.SetColCellBgColor("icon8",  '<spring:message code="config.common.grid.enableChangeColor"/>');
        gridObj.SetColCellBgColor("icon9",  '<spring:message code="config.common.grid.enableChangeColor"/>');
        gridObj.SetColCellBgColor("icon10", '<spring:message code="config.common.grid.enableChangeColor"/>');
        gridObj.SetColCellBgColor("icon11", '<spring:message code="config.common.grid.enableChangeColor"/>');
        gridObj.SetColCellBgColor("icon12", '<spring:message code="config.common.grid.enableChangeColor"/>');
        gridObj.SetColCellBgColor("icon13", '<spring:message code="config.common.grid.enableChangeColor"/>');
        gridObj.SetColCellBgColor("icon14", '<spring:message code="config.common.grid.enableChangeColor"/>');
        gridObj.SetColCellBgColor("delYn",  '<spring:message code="config.common.grid.enableChangeColor"/>');

        gridObj.SetColCellAlign("num",      "center");
        gridObj.SetColCellAlign("strNm",    "center");
        gridObj.SetColCellAlign("regDate",  "center");

        gridObj.SetColHide("prodCd",    true);
        gridObj.SetColHide("prodNm",    true);
        gridObj.SetColHide("strCd",     true);
        gridObj.SetColHide("CHK",       true);

        gridObj.bHDVisible = false; // 상단 헤더 감추기
    }

    //WiseGrid 통신 시 에러 발생
    function ErrorQuery(strSource, nCode, strMessage, strDescription)
    {
        alert(strSource);
    }

    function GridChangeCell(strColumnKey, nRow)
    {
        var GridObj = document.WG1;
        if (strColumnKey != "CHK")
        {
            GridObj.SetCellValue("CHK", nRow, "1");
            GridObj.SetCellBgColor(strColumnKey, nRow, '<spring:message code="config.common.grid.changedColor"/>');
        }
    }

    function goPage(currentPage)
    {
        var gridObj = document.WG1;
        var url = '<c:url value="/product/selectProduectIconSearch.do"/>';

        gridObj.setParam('prodCd', '<%=prodCd%>');
        gridObj.setParam('strGubun', $('#strGubun').val());
        setStrCd();
        gridObj.setParam('strCd', $('#strCd').val());
        gridObj.setParam('mode', 'search');

        gridObj.DoQuery(url);
        loadingMask();
    }
</script>
<script language="javascript" type="text/javascript">
    $(document).ready(function(){
        //input enter 막기
        $("*").keypress(function(e){
            if(e.keyCode==13) return false;
        });
        $('#search').click(function() {
        	doSearch();
        });
        $('#update').click(function() {
        	doUpdate();
        });
    });

    /** ********************************************************
     * 조회 처리 함수
     ******************************************************** */
    function doSearch()
    {
        goPage('1');
    }

    //리스트 수정
    function doUpdate()
    {
        if (!checkRows())
        {
            return;
        }

        if ( confirm("아이콘 설정 정보를 수정 하시겠습니까?") )
        {
            var gridObj = document.WG1;
            gridObj.SetParam("mode", "update");

            setStrCd();
            gridObj.setParam('strCd', $('#strCd').val());
            gridObj.setParam('vendorId', '<%=vendorId%>');

            var sUrl = '<c:url value="/product/updateProductIconList.do"/>';
            gridObj.DoQuery(sUrl, "CHK");
        }
    }

    function checkRows()
    {
        var GridObj = document.WG1;

        for (i = 0; i < GridObj.GetRowCount(); i++)
        {
            if (GridObj.GetCellValue("CHK", i) == 1)
            {
                return true;
            }
        }

        alert("선택된 아이콘이 없습니다.");

        return false;
    }

    function setStrCd()
    {
        if (!$("#strGubun").val())
        {
            $("#strCd").val($("#strCd00").val());
        }
        else if ($("#strGubun").val()=="01")
        {
            $("#strCd").val($("#strCd01").val());
        }
        else if($("#strGubun").val()=="02")
        {
            $("#strCd").val($("#strCd02").val());
        }
    }

</script>

<script language="javascript" type="text/javascript">
    // 점포분류 콤보값 변경시
    function chStrGubun(v)
    {
        $("#strCd00").hide();
        $("#strCd00").val("");
        $("#strCd01").hide();
        $("#strCd01").val("");
        $("#strCd02").hide();
        $("#strCd02").val("");

        if (v == '')
        {
            $("#strCd00").show();
        }
        else if (v == '01')
        {
            $("#strCd01").show();
        }
        else if (v == '02')
        {
            $("#strCd02").show();
        }
    }

    var ckAlls = new Array(15);

    function checkAll(v)
    {
        var gridObj = document.WG1;

        if (ckAlls[v] != '1')
        {
            ckAlls[v] = '1';
        }
        else
        {
             ckAlls[v] = '0';
        }

        var valName = '';

        if (v == 0)
        {
            valName = "delYn";
        }
        else 
        {
        	valName = "icon" + v;
        }

        for (var i = 0; i < gridObj.GetRowCount(); i++)
        {
            gridObj.SetCellValue(valName, i, ckAlls[v]);
            gridObj.SetCellValue("CHK", i, 1);
        }
    }
</script>
</head>

<body>

<form name="dataForm" id="dataForm">
<input type="hidden" id="strCd" name="strCd">
<div id="content_wrap" style="width:990px; height:200px;">

    <div id="wrap_menu" style="width:990px;">
        <!--    @ 검색조건  -->

        <!-- 상품 상세보기 하단 탭 -->
        <c:set var="tabNo" value="<%=tabNo%>" />
        <c:import url="/common/productDetailTab.do" charEncoding="euc-kr" >
			<c:param name="tabNo" value="${tabNo}" />
			<c:param name="prodCd" value="<%=prodCd%>" />
		</c:import>

        <div class="wrap_con">
            <!-- list -->
            <div class="bbs_list">
                <ul class="tit">
                    <li class="tit">점포분류&nbsp;&nbsp;

                    <select name="strGubun" id="strGubun" onchange="chStrGubun(this.value)">
                        <option value="">전체</option>
                        <option value="01">온라인점포</option>
                        <option value="02">명절점포</option>
                    </select>

                    <select name="strCd00" id="strCd00">
                        <option value="">전체</option>
                    </select>
                    <select name="strCd01" id="strCd01" style="display:none">
                        <option value="">전체</option>
                            <c:forEach items="${storeList}" var="code" begin="0">
                                <c:if test="${code.strGubun == '01'}" >
                                    <option value="${code.strCd }">${code.strNm }</option>
                                </c:if>
                            </c:forEach>
                    </select>
                    <select name="strCd02" id="strCd02" style="display:none">
                        <option value="">전체</option>
                            <c:forEach items="${storeList}" var="code" begin="0">
                                <c:if test="${code.strGubun == '02'}" >
                                    <option value="${code.strCd }">${code.strNm }</option>
                                </c:if>
                            </c:forEach>
                    </select>
                    </li>

                    <li class="btn">
                        <a href="#" class="btn" id="search"><span><spring:message code="button.common.inquire"/></span></a>
                        <a href="#" class="btn" id="update"><span><spring:message code="button.common.update" /></span></a>
                    </li>
                </ul>

                <table cellpadding="0" cellspacing="0" border="0"><!--  class="bbs_search" -->

                    <colgroup>
                        <col width="40">
                        <col width="95">
                        <col width="75">
                        <col width="50">
                        <col width="50">
                        <col width="50">
                        <col width="50">
                        <col width="50">
                        <col width="50">
                        <col width="50">
                        <col width="50">
                        <col width="50">
                        <col width="50">
                        <col width="50">
                        <col width="50">
                        <col width="50">
                        <col width="50">
                        <col width="74">
                    </colgroup>

                    <tr align="center" bgcolor="eeeeee" height="20">
                        <th><span class="star"></span>순번</th>
                        <th><span class="star"></span>점포명</th>
                        <th><span class="star"></span>등록일자</th>
                        <th><img src="https://simage.lottemart.com/lim/static_root/images/product/icon_pdt01.gif" onclick="checkAll(1)" style="cursor:hand"></th>
                        <th><img src="https://simage.lottemart.com/lim/static_root/images/product/icon_pdt02.gif" onclick="checkAll(2)" style="cursor:hand"></th>
                        <th><img src="https://simage.lottemart.com/lim/static_root/images/product/icon_pdt03.gif" onclick="checkAll(3)" style="cursor:hand"></th>
                        <th><img src="https://simage.lottemart.com/lim/static_root/images/product/icon_pdt04_01.gif" onclick="checkAll(4)" style="cursor:hand"></th>
                        <th><img src="https://simage.lottemart.com/lim/static_root/images/product/icon_pdt05.gif" onclick="checkAll(5)" style="cursor:hand"></th>
                        <th><img src="https://simage.lottemart.com/lim/static_root/images/product/icon_pdt06.gif" onclick="checkAll(6)" style="cursor:hand"></th>
                        <th><img src="https://simage.lottemart.com/lim/static_root/images/product/icon_pdt07.gif" onclick="checkAll(7)" style="cursor:hand"></th>
                        <th><img src="https://simage.lottemart.com/lim/static_root/images/product/icon_pdt08.gif" onclick="checkAll(8)" style="cursor:hand"></th>
                        <th><img src="https://simage.lottemart.com/lim/static_root/images/product/icon_pdt09.gif" onclick="checkAll(9)" style="cursor:hand"></th>
                        <th><img src="https://simage.lottemart.com/lim/static_root/images/product/icon_pdt10.gif" onclick="checkAll(10)" style="cursor:hand"></th>
                        <th><img src="https://simage.lottemart.com/lim/static_root/images/product/icon_pdt11.gif" onclick="checkAll(11)" style="cursor:hand"></th>
                        <th><img src="https://simage.lottemart.com/lim/static_root/images/product/icon_pdt12.gif" onclick="checkAll(12)" style="cursor:hand"></th>
                        <th><img src="https://simage.lottemart.com/lim/static_root/images/product/icon_pdt13.gif" onclick="checkAll(13)" style="cursor:hand"></th>
                        <th><img src="https://simage.lottemart.com/lim/static_root/images/product/icon_pdt14.gif" onclick="checkAll(14)" style="cursor:hand"></th>
                        <th align="left">&nbsp;<span onclick="checkAll(0)" style="cursor:hand">사용안함</span></th>
                    </tr>

                    <tr>
                        <td colspan="18"><script type="text/javascript">initWiseGrid("WG1", "984", "180");</script></td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>
</form>

<form name="form1" id="form1">
<input type="hidden" id="prodCd" name="prodCd" value="<%=prodCd%>" />
<input type="hidden" id="vendorId" name="vendorId" value="<%=vendorId%>" />
</form>

</body>
</html>