<%--
	Page Name 	: NEDMTST0010.jsp
	Description : 테스트 페이지
	Modification Information

	  수정일 	     수정자 				   수정내용
	---------- 		---------    		-------------------------------------
	2022.08.30 		NBMSHIP          	 		최초생성
--%>
<%@ include file="../common.jsp" %>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html lang = "kr">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<style>
  tr.on {
    background-color: #E6E6E6;
  }
</style>
<script>
  $(document).ready(function() {
  });

  function init() {}
  
</script>
</head>
<body>
  <div id="content_wrap">
    <form id="searchForm" name="searchForm" method="get" action="#">
    <div class="wrap_search">
      <div class="bbs_search">
        <ul class="tit" >
        </ul>

        
      </div>
    </div>

    <div class = "wrap_con">
      <div class="bbs_list">
        <div style="width:100%; height:800px;overflow-x:hidden; overflow-y:scroll; table-layout:fixed;white-space:nowrap;">
          <table  cellpadding="1" cellspacing="1" border="0" width="820px" bgcolor=efefef>
            <colgroup>
            </colgroup>

            <tr bgcolor="#e4e4e4">
            </tr>

            <tbody id="dataListbody"/>
          </table>
        </div>
      </div>
    </div>
    </form>
    
  </div>
  <div id = "footer">
  </div>
</body>
</html>
