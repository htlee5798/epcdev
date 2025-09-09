<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jstl/core_rt"      %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="lfn"    uri="/WEB-INF/tlds/function.tld"            %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"   %>
<%@ taglib prefix="epcutl" uri="/WEB-INF/tlds/epcutl.tld"              %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt"      %>

<%@page import="lcn.module.common.util.DateUtil"%>
<%@page import="com.lottemart.epc.common.util.SecureUtil"%>

<%
    String startDate = DateUtil.formatDate(DateUtil.getToday(),"-");
    String endDate   = DateUtil.formatDate(DateUtil.getToday(),"-");
    String prodCd    = SecureUtil.stripXSS(request.getParameter("prodCd"));
    String vendorId  = SecureUtil.stripXSS(request.getParameter("vendorId"));
    String tabNo     = "7";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHeadPopup.do" />

<script language="javascript" type="text/javascript">
    $(document).ready(function(){
    	// START of IBSheet Setting
    	createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "320px");
    	mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
    	
    	var ibdata = {};
    	// SizeMode: 
    	ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral}; // 10 row씩 Load
    	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
    	ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
    	ibdata.Cols = [
    		{SaveName:"RNUM"	 	, Header:"순번"		 , Type:"Int"	  , Align:"Center", Width:40,  	Edit:0}
    	  , {SaveName:"CHK"		 	, Header:""			 , Type:"CheckBox", Align:"Center", Width:30,  	Sort:false} // default=1(수정가능)
    	  , {SaveName:"STRNM"	 	, Header:"점포명"	 , Type:"Text"	  , Align:"Center", Width:155,	Edit:0, Ellipsis:true}
    	  , {SaveName:"PREST" 	, Header:"증정품"	 , Type:"Text"	  , Align:"Center", Width:460,	Edit:0, Ellipsis:true}
    	  , {SaveName:"PEST_START_DY"	, Header:"증정시작일", Type:"Text"	  , Align:"Center",	Width:110,	Edit:0, Format:'Ymd'}
    	  , {SaveName:"PEST_END_DY"	, Header:"증정종료일", Type:"Text"	  , Align:"Center",	Width:110,	Edit:0, Format:'Ymd'}
    	  , {SaveName:"PRESTTYPE" 	, Header:"유형"		 , Type:"Text"	  , Align:"Center", Width:100,	Edit:0}
    	  , {SaveName:"STRCD"	 	, Header:"점포코드"	 , Type:"Text"	  , Hidden:true }
    	];
    	
    	IBS_InitSheet(mySheet, ibdata);
    	mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.1
    	mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
    	
    	//input enter 막기
        $("*").keypress(function(e){
            if(e.keyCode==13) return false;
        });
    	
        goPage('1');
        
        $('#save').click(function() {
        	doUpdate();
        });
        $('#delete').click(function() {
        	doDelete();
        });
        
    });

    /** ********************************************************
     * 조회 처리 함수
     ******************************************************** */
    function doSearch()
    {
        goPage('1');
    }
    
    function goPage(currentPage) {
    	var url = '<c:url value="/product/selectProduectPresentSearch.do"/>';
    	loadIBSheetData(mySheet, url, null, '#dataForm', null);
    }

  	//온라인상품 체크
	function prodDivChk(){
		var chkVal = $(window.parent.frames["prdInfoFrame"].document).contents().find("#prodDivVal").val();
		var rtn = false;
		
		if(chkVal == "02"){
			rtn = true;
		}
		
		return rtn;
	}
    
    //리스트 수정
    function doUpdate()
    {
    	if(!prodDivChk()){
			alert("온라인상품만 저장 가능합니다.");
			return;
		}
    	
        //값 트림, 크기 채크(바이트)
        $('#prest').val($.trim($('#prest').val()));
        if(!byteCheck('증정품',$('#prest').val(),400,$('#prest'))){
            return;
        }
        
        if($('#prestStartDy').val() > $('#prestEndDy').val()) {
            alert('증정기간의 선택이 잘못되었습니다. 시작일이 종료일보다 큽니다.');
            return;
        }

        //값 입력 유무 채크
        if(Common.isEmpty($.trim($('#prest').val()))){
            alert('<spring:message code="msg.common.error.required" arguments="증정품"/>');
            $('#prest').focus();
            return;
        }

        if ( confirm("선택된 증정품을 수정 하시겠습니까?") )
        {
            var sUrl = '<c:url value="/product/updateProduectPresentList.do"/>';
            
            var param = new Object();
    		param.prodCd 		= $('#prodCd').val();
    		param.prest 	= $('#prest').val();
    		param.prestStartDy 	= $('#prestStartDy').val();
    		param.prestEndDy 	= $('#prestEndDy').val();
    		param.prestType 	= $('#prestType').val();
    		param.vendorId 	= $('#vendorId').val();
    		param.mode 			= 'update';
    		
    		mySheet.DoSave(sUrl, {Param:$.param(param), Quest:false, Col:1, Sync:2});
        }
    }
    
  //데이터 저장 직후 이벤트
  //Code: 서버에서 저장한 코드, Msg: 서버에서 저장한 메세지, StCode: Http통신코드(200=정상), StMsg:HTTP메세지(OK)
  function mySheet_OnSaveEnd(Code, Msg, StCode, StMsg) {
  	alert(Msg);
  	if (Code == 1) doSearch();
  }

    //리스트 삭제
    function doDelete()
    {
    	if(!prodDivChk()){
			alert("온라인상품만 삭제 가능합니다.");
			return;
		}
    	
        if ( confirm("선택된 증정품을 삭제 하시겠습니까?") )
        {
        	var sUrl = '<c:url value="/product/updateProduectPresentList.do"/>';
        	
            var param = new Object();
    		param.prodCd 		= $('#prodCd').val();
    		param.prestStartDy 	= $('#prestStartDy').val();
    		param.prestEndDy 	= $('#prestEndDy').val();
    		param.vendorId 	= $('#vendorId').val();
    		param.mode 			= 'delete';
            
            mySheet.DoSave(sUrl, {Param:$.param(param), Quest:false, Col:1, Sync:2});
        }
    }
</script>
</head>

<body>
<form name="dataForm" id="dataForm">
<input type="hidden" id="strCd" name="strCd">
<input type="hidden" id="prodCd" name="prodCd" value="<%=prodCd%>" />
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
                    <li class="tit">증정품 :&nbsp;&nbsp;
                        <input type="text" name="prest" id="prest" maxlength="200" style="width:200px;" />
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        기간 :&nbsp;&nbsp;
                        <input type="text" name="prestStartDy" id="prestStartDy" value="<%=startDate%>" class="day" onclick="javascript:openCal('dataForm.prestStartDy');" style="width:64px;" readOnly /> <a href="javascript:openCal('dataForm.prestStartDy');"><img src="${lfn:getString('system.cdn.static.path')}/images/bos/layout/btn_cal.gif" alt="" class="middle" /></a>
                        ~
                        <input type="text" name="prestEndDy" id="prestEndDy" value="<%=endDate%>" class="day" onclick="javascript:openCal('dataForm.prestEndDy');" style="width:64px;" readOnly /> <a href="javascript:openCal('dataForm.prestEndDy');"><img src="${lfn:getString('system.cdn.static.path')}/images/bos/layout/btn_cal.gif" alt="" class="middle" /></a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <select name="prestType" id="prestType" class="select" style="width:100px;margin-bottom:5px;">
							<option value="001">증정</option>
							<option value="002">1+1</option>
							<option value="003" >상품권</option>
							<option value="004" >덤</option>
						</select>
                    </li>
                    <li class="btn">
                        <a href="#" class="btn" id="save"  ><span><spring:message code="button.common.save"  /></span></a>
                        <a href="#" class="btn" id="delete"><span><spring:message code="button.common.delete"/></span></a>
                    </li>
                </ul>

                <table cellpadding="0" cellspacing="0" border="0" width="100%">
                    <tr>
                        <td><div id="ibsheet1"></div></td>
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