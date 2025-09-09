<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.lottemart.common.util.DataMap"%>
<%@ page import="java.util.List"%>
<%
	List category = (List)request.getAttribute("categoryList");
	String categoryName = (String)request.getAttribute("categoryName");
	String formName = request.getAttribute("parentFormName")!=null ? (String)request.getAttribute("parentFormName") : "popupForm";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	var categoryName = "<%=categoryName%>";
	var form = parent.document.<%=formName%>;
	var combo;
	if(categoryName == "middle") {
		combo = form.middleCategory;
		parent.makeSelectEmpty(combo);
		if(form.subCategory != null) {
			parent.makeSelectEmpty(form.subCategory);
			form.subCategory.options[0] = new Option('전체','');
		}
		if(form.detailCategory != null) {
			parent.makeSelectEmpty(form.detailCategory);
			form.detailCategory.options[0] = new Option('전체','');
		}
	} else if(categoryName == "sub") {
		combo = form.subCategory;
		parent.makeSelectEmpty(combo);
		if(form.detailCategory != null) {
			parent.makeSelectEmpty(form.detailCategory);
			form.detailCategory.options[0] = new Option('전체','');
		}
	} else if(categoryName == "detail") {
		if(form.detailCategory != null) {
			combo = form.detailCategory;
			parent.makeSelectEmpty(combo);
		}
	}
	combo.options[0] = new Option('전체','');
	<%
		if(category != null && category.size() > 0) {
			for(int i = 0; i < category.size(); i++) {
				DataMap map = (DataMap)category.get(i);
				String catCd = (String)map.get("CAT_CD");
				String catNm = (String)map.get("CAT_NM");
	%>
				combo.options[<%=i+1%>] = new Option('<%=catNm%>','<%=catCd%>');
	<%
			}
		}
	%>
</script>
</meta>
</head>