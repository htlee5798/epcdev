<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jstl/core_rt"         %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions"   %>
<%@ taglib prefix="lfn"    uri="/WEB-INF/tlds/function.tld"               %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui"     uri="http://lcnjf.lcn.co.kr/taglib/paging"     %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"      %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt"         %>
<%@ include file="/common/scm/scmCommon.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<!-- board/PSCMBRD0008 -->
<script type="text/javascript">
    /** ********************************************************
     * jQeury 초기화
     ******************************************************** */
    $(document).ready(function()
    {
        var bflag = '${bflag}';

        if ( bflag == "success" )
        {
            $('#resultMsg').text('<spring:message code="msg.common.success.select"/>');
        }
        else if ( bflag == "zero" )
        {
            $('#resultMsg').text('<spring:message code="msg.common.info.nodata"/>');
        }
        else
        {
            $('#resultMsg').text('<spring:message code="msg.common.fail.request"/>');
        }
     
        $('#search').click(function() {
            doSearch();
        });
        $('#create').click(function() {
        	doInsert();
        });
    }); // end of ready 
    
    /** ********************************************************
     * 조회 처리 함수
     ******************************************************** */
    function doSearch()
    {
        goPage('1');
    }

    /** ********************************************************
     * 페이징 처리 함수
     ******************************************************** */
    function goPage(pageIndex)
    {
        var form = document.adminForm;
        
        var startDate = form.startDate.value.replace( /-/gi, '' );
        var endDate   = form.endDate.value.replace( /-/gi, '' );
        
        <c:choose>
			<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
			if (form.searchVendorId.value == "")
		    {
		        alert('업체선택은 필수입니다.');
		        form.searchVendorId.focus();
		        return;
		    }
			</c:when>
		</c:choose>
		
        if(startDate>endDate)
        {
            alert('시작일자가 종료일자보다 클수 없습니다.');
            return;
        }
        
        var url = '<c:url value="/board/selectCallCenterList.do"/>';
        form.pageIndex.value = pageIndex;
        form.action = url;
        form.submit();
    }
    
    /** ********************************************************
     * 콜센터 상세보기 팝업링크
     ******************************************************** */
     function doDetail(counselSeq)
    {
        var url ='<c:url value="/board/selectCallCenterPopupDetail.do"/>?counselSeq='+counselSeq; /* 팝업창 주소 */
        Common.centerPopupWindow(url, 'epcCallCenterPopupDetail', {width : 600, height : 450});
    }
    
    /** ********************************************************
     * 콜센터 등록 팝업링크
     ******************************************************** */
     function doInsert()
    {
         var url ='<c:url value="/board/insertCallCenterPopupForm.do"/>'; /* 팝업창 주소 */
         Common.centerPopupWindow(url, 'epcCallCenterPopupForm', {width : 600, height : 450});
    }
    

     function setVendorInto(strVendorId, strVendorNm, strCono) { // 이 펑션은 협력업체 검색창에서 호출하는 펑션임   
     	$("#searchVendorId").val(strVendorId);
     }
</script>
</head>

<body>
<form name="adminForm" method="post">
    
<div id="content_wrap">
<div class="content_scroll">

<div id="wrap_menu">

<!-------------------------- 1.조회조건 ------------------------------->
    <div class="wrap_search">
        <!-- 01 : search -->
        <div class="bbs_search">
            <ul class="tit">
                <li class="tit">조회조건</li>
                <li class="btn">
                    <a href="#" class="btn" id="search"><span><spring:message code="button.common.inquire"/></span></a>
                    <a href="#" class="btn" id="create"><span><spring:message code="button.common.create" /></span></a>
                </li>
            </ul>
            <table class="bbs_search" cellpadding="1" cellspacing="0" border="0">
                <colgroup>
                    <col width="12%">
                    <col width="38%">
                    <col width="12%">
                    <col width="38%">
                </colgroup>
                <tr>
                    <th><span class="star">*</span> 접수기간</th>
                    <td>
                        <input type="text" name="startDate" id="startDate" value="<c:out value="${searchVO.startDate}" />" style="width:22%;text-align:center;" class="day" onClick="javascript:openCal('adminForm.startDate')" readOnly="readonly" /> <a href="javascript:openCal('adminForm.startDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
                        ~
                        <input type="text" name="endDate" id="endDate" value="<c:out value="${searchVO.endDate}" />" style="width:22%;text-align:center;" class="day" onClick="javascript:openCal('adminForm.startDate')" readOnly="readonly" /> <a href="javascript:openCal('adminForm.endDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
                    </td> 
                    <th>협력업체코드</th>
                    <td>   
                    	<c:choose>
									<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
										<c:if test="${not empty searchVO.searchVendorId}">
											<input type="text" name="searchVendorId" id="searchVendorId" readonly="readonly" value="${searchVO.searchVendorId}" style="width:40%;"/>
										</c:if>	
										<c:if test="${empty searchVO.searchVendorId}">
											<input type="text" name="searchVendorId" id="searchVendorId" readonly="readonly" value="${epcLoginVO.repVendorId}" style="width:40%;"/>
										</c:if>	
										<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
									</c:when>
									<c:when test="${epcLoginVO.vendorTypeCd != '06'}">
										<select name="searchVendorId" id="searchVendorId" class="select">
											<option value="">전체</option>
										<c:forEach items="${epcLoginVO.vendorId}" var="venArr" begin="0">
										
											<c:if test="${not empty searchVO.searchVendorId}">
												<option value="${venArr}" <c:if test="${venArr eq searchVO.searchVendorId}">selected</c:if>>${venArr}</option>
											</c:if>	
											<c:if test="${empty searchVO.searchVendorId}">
												<option value="${venArr}" <c:if test="${not empty epcLoginVO.repVendorId && venArr eq epcLoginVO.repVendorId }">selected</c:if>>${venArr}</option>
											</c:if>	
					                        
										</c:forEach>
										</select>
									</c:when>
						</c:choose>
						
                    </td>
                </tr>
            </table>
        </div>
     </div>
    
    <div class="wrap_con">
        <div class="bbs_list">
            <ul class="tit">
                <li class="tit">접수현황</li>
            </ul>
            <!----------------검색 결과 테이블--------->
            <table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
                <colgroup>
                    <col width="30" />
                    <col width="85" />
                    <col width="265"/>
                    <col width="60" />
                    <col width="80" />
                    <col width="60" />
                    <col width="90"/>
                    <col width="*"  />
                </colgroup>
    
                <tr>
                    <th class="fst">순번</th>
                    <th>상담순번</th>
                    <th>문의제목</th>
                    <th>처리상태</th>
                    <th>연락처(HP)</th>
                    <th>SMS여부</th>
                    <th>등록자</th>
                    <th>등록일시</th>
                </tr>

            <c:forEach var="result" items="${inquirelist}" varStatus="status">
                <tr class="r1">
                    <td>${result.NUM}</td>                                                               <!-- 순번 -->
                    <td><a href="javascript:doDetail('${result.COUNSEL_SEQ}')">${result.COUNSEL_SEQ}</td><!-- 상담순번 -->
                    <td style="text-align:left;padding-left:5px">${result.TITLE}</td>                    <!-- 문의제목 -->
                    <td>${result.BOARD_PRGS_STS_CD_NM}</td>                                              <!-- 처리상태 -->
                    <td>${result.CELL_NO}</td>                                                           <!-- 연락처(HP) -->
                    <td>${result.ANS_SMS_RECV_YN}</td>                                                   <!-- 문자수신여부 -->
                    <td>${result.REG_ID}</td>                                                            <!-- 등록자 -->
                    <td>${result.REG_DATE}</td>                                                          <!-- 등록일시 -->
                </tr>
            </c:forEach>
                
            <c:if test="${empty inquirelist}">
                <tr class="r1">
                    <td colspan="8"><spring:message code="msg.common.info.nodata"/></td>
                </tr>
            </c:if>
                
            </table>
        </div>
        
        <div id="pagingDiv" style="text-align: center; margin-top: 5px;">
            <ui:pagination paginationInfo = "${paginationInfo}" type="image" jsFunction="goPage" />
        </div>
    </div>
    <input type="hidden" name="pageIndex" />

</div><!-- id wrap_menu// -->
</div><!-- id content_scroll// -->
<!-- -------------------------------------------------------- -->
<!--    footer  -->
<!-- -------------------------------------------------------- -->
<div id="footer">
    <div id="footbox">
        <div class="msg" id="resultMsg"></div>
        <div class="location">
            <ul>
                <li>홈</li>
                <li>협력업체배송관리</li>
                <li class="last">콜센터배송문의등록</li>
            </ul>
        </div>
    </div>
</div>
<!---------------------------------------------end of footer -->
</div><!-- id content_wrap// -->

</form>
</body>

</html>