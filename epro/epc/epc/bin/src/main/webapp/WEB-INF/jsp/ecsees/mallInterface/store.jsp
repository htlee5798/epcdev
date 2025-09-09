<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<ecsees>
	<mall_interface total_count="${total_count}">
		<c:choose>
			<c:when test="${total_count != '0' }">
				<c:forEach items="${store_list }" var="store" begin="0">
					<store id="${store.str_cd}">
						<str_nm><![CDATA[${store.str_nm}]]></str_nm>
						<online_yn>${store.online_yn}</online_yn>
						<feday_mall_yn>${store.feday_mall_yn}</feday_mall_yn>
						<str_recp_yn>${store.str_recp_yn}</str_recp_yn>
						<reg_id>${store.reg_id}</reg_id>
						<reg_date>${store.reg_date}</reg_date>
						<upd_id>${store.upd_id}</upd_id>
						<upd_date>${store.upd_date}</upd_date>
					</store>
				</c:forEach>
			</c:when>
		</c:choose>
	</mall_interface>
</ecsees>