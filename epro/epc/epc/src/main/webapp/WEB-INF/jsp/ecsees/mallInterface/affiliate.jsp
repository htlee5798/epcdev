<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<ecsees>
	<mall_interface total_count="${total_count}">
		<c:choose>
			<c:when test="${total_count != '0' }">
				<c:forEach items="${affiliate_List}" var="affiliate" begin="0">
					<affiliate id="${affiliate.affiliate_id}">
						<affiliate_nm><![CDATA[${affiliate.affiliate_nm}]]></affiliate_nm>
						<upper_id>${affiliate.upper_id}</upper_id>
						<depth>${affiliate.depth}</depth>
						<reg_id>${affiliate.reg_id}</reg_id>
						<reg_date>${affiliate.reg_date}</reg_date>
						<upd_id>${affiliate.upd_date}</upd_id>
						<upd_date>${affiliate.upd_date}</upd_date>
					</affiliate>
				</c:forEach>	
			</c:when>
		</c:choose>
	</mall_interface>
</ecsees>