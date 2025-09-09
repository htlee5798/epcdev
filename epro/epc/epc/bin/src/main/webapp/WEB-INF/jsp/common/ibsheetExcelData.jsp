<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript">
<%
String error = (String)request.getAttribute("error");

if( error != null && !error.equals("")){
	out.println("alert('처리중 오류가 발생했습니다.\\n["+error+"]');");
}
else {
	List al = (List)request.getAttribute("excelData");
	
	String[] colNms = ((String)request.getAttribute("colNms")).split("\\^"); 		//엑셀의 칼럼명 ^로 구분하여 순서대로 처리
	String functionNm = (String)request.getAttribute("func");							//실행 자바스크립트 함수명
	String sheetNm = (String)request.getAttribute("sheetNm");						//IBSheet 변수명
	String sheetRemoveAll = (String)request.getAttribute("sheetRemoveAll");	//Sheet 초기화 여부
	
	if( al != null && al.size() > 0){
		
		if( sheetRemoveAll !=null && sheetRemoveAll.equalsIgnoreCase("Y")){
			out.println("parent."+ sheetNm +".RemoveAll();");
		}
		out.println("parent."+ sheetNm +".RenderSheet(0);");
		for( int i=0; i<al.size(); i++){
			Map data = (Map)al.get( i);
			
			if( colNms != null){
				out.print("parent."+functionNm+"( ");
					for( int j=0; j<colNms.length; j++){
					String val = (String)data.get( colNms[j]);
					out.print("'"+val+"'"+( ( j<(colNms.length-1))?",":""));
				}
				out.println(");");
			}
		}
		out.println( "parent."+ sheetNm +".RenderSheet(1);");
		//out.println( "alert( parent.mySheet.RowCount());");
	}
}
%>
</script>
</head>
</html>