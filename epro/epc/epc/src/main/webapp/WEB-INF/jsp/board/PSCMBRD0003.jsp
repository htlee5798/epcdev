<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/common/scm/scmCommon.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<!-- board/PSCMBRD0003 -->
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
	    
	    var url = '<c:url value="/board/selectInquireList.do"/>';
	    form.pageIndex.value = pageIndex;
	    form.action = url;
	    form.submit();
	}
	
	/** ********************************************************
	 * 문의 상세보기 팝업링크
	 ******************************************************** */
	 function popupCounsel(counselSeq, transferSeq)
	{
        var url ='<c:url value="/board/selectCounselPopupDetail.do"/>?counselSeq='+counselSeq + '&transferSeq=' + transferSeq; /* 팝업창 주소 */
        Common.centerPopupWindow(url, 'detail', {width : 570, height : 670});
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
                </li>
            </ul>
            <table class="bbs_search" cellpadding="1" cellspacing="0" border="0">
                <colgroup>
                    <col width="12%">
                    <col width="28%">
                    <col width="12%">
                    <col width="18%">
                    <col width="12%">
                    <col width="18%">
                </colgroup>
                <tr>
                    <th><span class="star">*</span> 접수기간</th>
                    <td>
                        <input type="text" name="startDate" id="startDate" value="<c:out value="${searchVO.startDate}" />" style="width:30%;text-align:center;" class="day" onClick="javascript:openCal('adminForm.startDate')" readOnly="readonly" /> <a href="javascript:openCal('adminForm.startDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
                        ~
                        <input type="text" name="endDate" id="endDate" value="<c:out value="${searchVO.endDate}" />" style="width:30%;text-align:center;" class="day" onClick="javascript:openCal('adminForm.startDate')" readOnly="readonly" /> <a href="javascript:openCal('adminForm.endDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
                    </td> 
                    <th>고객문의구분</th>
                    <td>        
                        <select class="select" name="custQstDivnCd" style="width:100px">
                            <option value="">----전체----</option>
                        <c:forEach items="${custQstDivnCd}" var="cust" begin="0">
                            <option value="${cust.MINOR_CD}" <c:if test="${cust.MINOR_CD == searchVO.custQstDivnCd}">selected</c:if>>${cust.CD_NM}</option>
                        </c:forEach>
                        </select> 
                    </td>
                    <th>접수위치</th>
                    <td>    
                        <select class="select" name="agentLocation" style="width:90px">
                            <option value="">---전체---</option>
                        <c:forEach items="${agentLocation}" var="agent" begin="0">
                            <option value="${agent.MINOR_CD}" <c:if test="${agent.MINOR_CD == searchVO.agentLocation}">selected</c:if>>${agent.CD_NM}</option>
                        </c:forEach>
                        </select>
                    </td>
                </tr>   
                <tr>
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
                    <th>처리상태구분</th>
                    <td colspan="3">
                        <input type="radio" name="typeCheck" value=""  <c:if test="${searchVO.typeCheck eq ''}" >checked</c:if>>전체</input>
                        <input type="radio" name="typeCheck" value="Y" <c:if test="${searchVO.typeCheck eq 'Y'}">checked</c:if>>처리완료</input>
                        <input type="radio" name="typeCheck" value="N" <c:if test="${searchVO.typeCheck eq 'N'}">checked</c:if>>미처리(이관포함)</input>
                    </td>
                </tr>
            </table>
        </div>
     </div>
    
    <div class="wrap_con">
        <div class="bbs_list">
            <table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
              <colgroup>
                <col style="width:16%"/>
                <col style="width:17%"/>
                <col style="width:16%"/>
                <col style="width:17%"/>
                <col style="width:16%"/>
                <col style="width:18%"/>
              </colgroup>
              <tr>
                <th>총접수건</th>
                    <td style="text-align:right;padding-right:5px">
                        <fmt:formatNumber value="${stats.COUNTALL}" type="number" />
                    </td>
                <th>처리건</th>
                    <td style="text-align:right;padding-right:5px">
                        <fmt:formatNumber value="${stats.COUNTPRO}" type="number" />
                    </td>
                <th>완료(답변)건 </th>
                    <td style="text-align:right;padding-right:5px">
                        <fmt:formatNumber value="${stats.COUNTANS}" type="number" />
                    </td>
              </tr>
            </table>
        </div>
        <div style="padding-top:2px;"></div>
        <div class="bbs_list">
            <ul class="tit">
                <li class="tit">접수현황</li>
            </ul>
            <!----------------검색 결과 테이블--------->
            <table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
                <colgroup>
                    <col width="35" />
                    <col width="80" />
                    <col width="80" />
                    <col width="80" />
                    <col width="195"/>
                    <col width="60" />
                    <col width="80" />
                    <col width="60" />
                    <col width="*"  />
                </colgroup>
    
                <tr>
                    <th class="fst">순번</th>
                    <th>상담순번</th>
                    <th>문의유형(대)</th>
                    <th>문의유형(소)</th>
                    <th>문의제목</th>
                    <th>처리상태</th>
                    <th>접수처</th>
                    <th>접수자</th>
                    <th>접수일시</th>
                </tr>

            <c:forEach var="result" items="${inquirelist}" varStatus="status">
                <tr class="r1">
                    <td>${result.ROWNUM}</td>                                                                 <!-- 순번 -->
                    <td><a href="javascript:popupCounsel('${result.COUNSEL_SEQ}', '${result.TRANSFER_SEQ}')">${result.COUNSEL_SEQ}</td> <!-- 상담순번, 이관순번 -->
                    <td>${result.CLM_LGRP_CD_NM}</td>                                                         <!-- 문의유형(대) -->
                    <td>${result.CLM_MGRP_CD_NM}</td>                                                         <!-- 문의유형(소) -->
                    <td style="text-align:left;padding-left:5px">${result.TITLE}</td>                         <!-- 문의제목 -->
                    <td>${result.BOARD_PRGS_STS_CD_NM}</td>                                                   <!-- 처리상태 -->
                    <td>${result.ACCEPT_LOCA_CD_NM}</td>                                                      <!-- 접수처 -->
                    <td>${result.ACCEPT_ID}</td>                                                              <!-- 접수자 -->
                    <td>${result.REG_DATE}</td>                                                               <!-- 접수일시 -->
                </tr>
            </c:forEach>
                
            <c:if test="${empty inquirelist}">
                <tr class="r1">
                    <td colspan="9"><spring:message code="msg.common.info.nodata"/></td>
                </tr>
            </c:if>
                
            </table>
        </div>
        
	    <div id="pagingDiv">
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
                <li>고객의소리</li>
                <li class="last">콜센터1:1문의</li>
            </ul>
        </div>
    </div>
</div>
<!---------------------------------------------end of footer -->
</div><!-- id content_wrap// -->

</form>
</body>

</html>