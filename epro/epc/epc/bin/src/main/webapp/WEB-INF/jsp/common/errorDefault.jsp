<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script language="javascript">
function fn_ErrorPage(){
    history.back(-2);
}
</script>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center" valign="top"><br />
    <br />
    <br />
    <table width="600" border="0" cellpadding="0" cellspacing="0" background="/images/' />">
      <tr>
        <td align="center"><table width="100%" border="0" cellspacing="9" cellpadding="0">
          <tr>
            <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="left"></td>
              </tr>
              <tr>
                <td><br />
                  <br /></td>
              </tr>
              <tr>
                <td align="center">
                <table width="520" border="0" cellspacing="2" cellpadding="2">
                  <tr>
                <!--  <td width="128" rowspan="2" align="center"><img src="/images/epc/edi/error.gif"  width="128" height="128" alt=""/></td>  -->
        
                    <td width="399" align="center" class="lt_text2">알 수 없는 오류가 발생했습니다...........</td>
                  </tr>
                </table>
                <table width="500" border="0" cellspacing="2" cellpadding="2">
                </table></td>
              </tr>
              
              <tr>
                <td><br />
                  <br /></td>
              </tr>
              <tr>
                <td align="center"><a href="#LINK" onClick="fn_ErrorPage();"><b>뒤로가기</a></td>
              </tr>
            </table>
              <br /></td>
          </tr>
        </table></td>
      </tr>
    </table>
    </td>
  </tr>
</table>


