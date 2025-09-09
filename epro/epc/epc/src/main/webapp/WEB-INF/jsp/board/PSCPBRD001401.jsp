<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<!-- board/PSCPBRD001401 -->

<script type="text/javascript">
    $(document).ready(function(){
        comboBox();
    }); // end of ready

    /** ********************************************************
     * 상품 Q&A 글자제한
     ******************************************************** */
    function fnChkByte(obj, maxByte){
        var str = obj.value;
        var str_len = str.length;

        var rbyte = 0;
        var rlen = 0;
        var one_char = "";
        var str2 = "";

        for(var i=0; i<str_len; i++){
            one_char = str.charAt(i);
            if(escape(one_char).length > 4){
                  rbyte += 3;
            }else{
                rbyte++;  //영문 등 나머지 1Byte
            }

            if(rbyte <= maxByte){
                rlen = i+1;  //return할 문자열 갯수
            }
        }

        if(rbyte > maxByte){
            alert("한글 "+parseInt(maxByte/3)+"자 / 영문 "+maxByte+"자를 초과 입력할 수 없습니다.");
            str2 = str.substr(0,rlen);  //문자열 자르기
            obj.value = str2;
            fnChkByte(obj, maxByte);
        }
    }

    /**********************************************************
     * select box 세팅
     ******************************************************** */
    function comboBox() {
        $('#temList').empty();
        
        $.ajax({
            type:"post",
            url:'<c:url value="/board/ComBoxList.do"/>',
            data: $("#temList").serialize(),
            success: function(data) {
                if(data.temComList.length > 0) {
                    $("#temList").find("option").remove().end().append("<option value=> 선택</option>");
                    $.each(data.temComList, function(data, data) {
                        $("#temList").append("<option  value="+data.templateSeq+">" + data.templateTitle + "</option>"); 
                    });
                } else {
                    $("#temList").find("option").remove().end().append("<option> 선택</option>");
                    return;
                }
            },
            error: function(x, o, e) {
            }
        });
    }
    /** ********************************************************
     * 템플릿 선택 이벤트
     ******************************************************** */
    function selectEvent(selectObj) {
        callAjaxByComForm('#dataForm',
            '<c:url value="/board/selectComBox.do"/>', '#dataForm', 'POST');
    }

    function callAjaxByComForm(form, url, target, Type) {

        var formQueryString = $('*', form).fieldSerialize();

        $.ajax({
            async: false,
            type: Type,
            url: url,
            data: formQueryString,
            success: function(data) {
                try {
                    //답변수정
                    $("#ansContent").text(data.temComList.templateContent);
                    //콤보박스 리셋
                    
                } catch (e) {}
            },
            error: function(e) {
                alert(e);
            }
        });
    }

    /** ********************************************************
     * 답변 저장
     ******************************************************** */
    function doSave() {

        if (!nullCheck('답변', $('#ansContent').val().replace(/^\s+|\s+$/g,""), $('#ansContent'))) {
            return;
        }
        if (!byteCheck('답변', $('#ansContent').val(), 2000, $('#ansContent'))) {
            return;
        }

        if (!confirm('<spring:message code="msg.common.confirm.update"/>')) {
            return;
        }

        callAjaxByForm('#dataForm', '<c:url value="/board/qnaAnsUpdate.do"/>', '#dataForm', 'POST');
    }

    function callAjaxByForm(form, url, target, Type) {

        var formQueryString = $('*', form).fieldSerialize();

        $.ajax({
            type: Type,
            url: url,
            data: formQueryString,
            success: function(json) {
                try {
                    if (jQuery.trim(json) == "") { //처리성공
                        alert('<spring:message code="msg.common.success.request"/>');
                        opener.doSearch();
                        top.close();
                    } else if ("accessAlertFail") {
                        alert('<spring:message code="msg.common.error.noAuth"/>');
                    } else {
                        alert(jQuery.trim(json));
                    }
                } catch (e) {}
            },
            error: function(e) {
                alert(e);
            }
        });
    }

    /** ********************************************************
     * 템플릿 삭제
     ******************************************************** */

    function deleteTem() {
        callAjaxByTemDelForm('#dataForm', '<c:url value="/board/temDelete.do?"/>', '#dataForm', 'POST');  
    }

    function callAjaxByTemDelForm(form, url, target, Type) {
        var templateSeq  =$('#temList').val();
        var formQueryString = $('*', form).fieldSerialize()+ "&templateSeq=" + templateSeq;
        $.ajax({
                type: Type,
                url: url,
                data: formQueryString,
                success: function(json) {
                    try {
                        if (jQuery.trim(json) == "") { //처리성공
                            alert('<spring:message code="msg.common.success.request"/>');
                              //콤보 리셋
                            comboBox();
                        } else if ("accessAlertFail") {
                            alert('<spring:message code="msg.common.error.noAuth"/>');
                        } else {
                            alert(jQuery.trim(json));
                        }
                    } catch (e) {}
                },
                error: function(e) {
                    alert(e);
                }
            });
    }

    /** ********************************************************
     * 템플릿 처리
     ******************************************************** */
    function doTem() {
        //null check
        if (!nullCheck('답변', $('#ansContent').val(), $('#ansContent'))) {
            return;
        }
        // byte check
        if (!byteCheck('답변', $('#ansContent').val().replace(/(^\s*)|(\s*$)/gi, ""), 4000, $('#ansContent'))) {
            return;
        }
        //title 
        var templateTitle = prompt(' 제목을 입력하세요', '템플릿제목입력');

        //title check
        if (templateTitle == ""){
             alert("템플릿 제목을 입력하세요");
             return;
         } 

        //title byte check
        if (templateTitle.length > 30){
             alert("템플릿 제목은 한글 30자 이하 입니다");
             return;
         }  

        // 취소
        if (templateTitle == null){
            return ;  
        }
        callAjaxByTemForm('#dataForm', '<c:url value="/board/temAdd.do?"/>', '#dataForm', 'POST',templateTitle);
    }

    function callAjaxByTemForm(form, url, target, Type,templateTitle) {

        var formQueryString = $('*', form).fieldSerialize() + "&templateTitle=" + escape(encodeURIComponent(templateTitle));
        $.ajax({
            type: Type,
            url: url,
            data: formQueryString,
            success: function(json) {
                try {
                    if (jQuery.trim(json) == "") { //처리성공
                        alert('<spring:message code="msg.common.success.request"/>');
                        //콤보 리셋
                        comboBox();
                    } else if ("accessAlertFail") {
                        alert('<spring:message code="msg.common.error.noAuth"/>');
                    } else {
                        alert(jQuery.trim(json));
                    }
                } catch (e) {}
            },
            error: function(e) {
                alert(e);
            }
        });
    }
</script>
</head>
<body>
    <form name="dataForm" id="dataForm" method="post">
        <input type="hidden" name="recommSeq" id="recommSeq" value="${data.prodQnaSeq}" />

        <div id="popup">
            <!--  @title  -->
            <div id="p_title1">
                <h1>상품 Q&amp;A 게시판 상세정보</h1>
                <span class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
            </div>
            <!--  @title  //-->
            <!--  @process  -->
            <div id="process1">
                <ul>
                    <li>홈</li>
                    <li>게시판관리</li>
                    <li>상품 Q&amp;A 게시판</li>
                    <li class="last">상세조회/답변등록</li>
                </ul>
            </div>
            <!--  @process  //-->

            <div class="popup_contents">
                <!-- 질문내용-->
                <div class="bbs_search3">
                    <ul class="tit">
                        <li class="tit">질문내용</li>
                        <li class="btn"><a href="javascript:void(0)" class="btn" onclick="top.close();"><span><spring:message code="button.common.close" /></span></a></li>
                    </ul>
                    <table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
                        <colgroup>
                            <col width="14.5%" />
                            <col width="34.5%" />
                            <col width="14.5%" />
                            <col width="34.5%" />
                        </colgroup>
                        <tr>
                            <th><span class="fst"></span>문의일시</th>
                            <td>${data.regDate}</td>
                            <th><span class="fst"></span>처리일시</th>
                            <td>${data.ansDate}</td>
                        </tr>
                        <tr>
                            <th><span class="fst"></span>문의유형</th>
                            <td>${data.qstTypeNm}
                            </td>
                            <th><span class="fst"></span>회원ID</th>
                            <td>${data.ecMemberId}</td>
                        </tr>
                        <tr>
                            <th><span class="fst"></span>회원명</th>
                            <td>${data.ecMemberNm}</td>
                            <th><span class="fst"></span></th>
                            <td></td>
                        </tr>
                        <tr>
                            <th><span class="fst"></span>제목</th>
                            <td >${data.title}</td>
                            <th><span class="fst"></span>주문번호</th>
                            <td>${data.order_id}</td> 
                        </tr>
                        <tr style="height: 150px">
                            <th><span class="fst"></span>질문내용</th>
                            <td colspan="3">${data.qstContent}</td>
                        </tr>
                    </table>
                    <ul class="tit">
                        <li class="tit"><span class="fst"></span>답변내용</li>
                        <li class="btn"><a href="javascript:void(0)" class="btn" id="btn" onclick="doSave()"><span><spring:message code="button.common.save" /></span></a></li>
                    </ul>
                    <!-- 답변내용-->
                    <table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
                        <colgroup>
                            <col width="15%" />
                            <col width="85%" />
                        </colgroup>
                        <tr>
                            <th>템플릿 선택</th>
                            <td>
                                <select name="temList" id="temList" class="select" readonly style="width: 18%;" onChange="javascript:selectEvent(this)">
                                </select>
                                <a href="javascript:void(0)" class="btn" id="btn" onclick="doTem()"><span>템플릿저장</span></a>
                                <a href="javascript:void(0)" class="btn" id="btn2" onclick="deleteTem()"><span>템플릿삭제</span></a>
                            </td>
                        </tr>
                        <tr style="height: 150px">
                            <th><span class="fst"></span>답변내용</th>
                            <td><textarea name="ansContent" id="ansContent" style="width: 640px; height: 150px" wrap="hard"  onKeyUp="javascript:fnChkByte(this,'2000')">${data.ansContent}</textarea></td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </form>
    <iframe name="_if_save" src="/html/epc/blank.html" style="display: none;"></iframe>
</body>
</html>