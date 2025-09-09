<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<ecsees>
	<mall_interface total_count="${total_count}">
		<c:choose>
			<c:when test="${total_count != '0' }">
				<c:forEach items="${product_list }" var="product" begin="0">
					<product id="${product.product_id }">
						<product_nm><![CDATA[${product.product_nm }]]></product_nm>
						<vendor_id>${product.vendor_id }</vendor_id>
						<brand_id>${product.brand_id }</brand_id>
						<team_id>${product.team_id }</team_id>
						<reg_id>${product.reg_id }</reg_id>
						<reg_date>${product.reg_date }</reg_date>
						<upd_id>${product.upd_id }</upd_id>
						<upd_date>${product.upd_date }</upd_date>
						<md_srcmk_cd>${product.md_srcmk_cd }</md_srcmk_cd>
					</product>
				</c:forEach>
			</c:when>
		</c:choose>
	</mall_interface>
</ecsees>