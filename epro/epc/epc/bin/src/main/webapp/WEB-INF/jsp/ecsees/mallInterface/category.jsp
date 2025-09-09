<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<ecsees>
	<mall_interface total_count="${total_count}">
		<c:choose>
			<c:when test="${total_count != '0' }">
				<c:forEach items="${categoryList }" var="category" begin="0">
					<category id="${category.category_id }">
						<category_nm><![CDATA[${category.category_nm }]]></category_nm>
						<upper_id>${category.upper_id }</upper_id>
						<leaf_yn>${category.leaf_yn }</leaf_yn>
						<category_typr_cd>${category.category_typr_cd }</category_typr_cd>
						<disp_seq>${category.disp_seq }</disp_seq>
						<disp_yn>${category.disp_yn }</disp_yn>
						<lvl>${category.lvl }</lvl>
						<reg_id>${category.reg_id }</reg_id>
						<reg_date>${category.reg_date }</reg_date>
						<upd_id>${category.upd_id }</upd_id>
						<upd_date>${category.upd_date }</upd_date>
					</category>
				</c:forEach>
			</c:when> 
		</c:choose>
	</mall_interface>
</ecsees>