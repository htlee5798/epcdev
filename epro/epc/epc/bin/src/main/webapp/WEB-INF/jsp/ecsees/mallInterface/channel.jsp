<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<ecsees>
	<mall_interface total_count="${total_count}">
		<c:choose>
			<c:when test="${total_count != '0' }">
				<c:forEach items="${channel_list }" var="channel" begin="0">
					<channel id="${channel.channel_id }">
						<channel_nm><![CDATA[${channel.channel_nm }]]></channel_nm>
						<channel_type_cd>${channel.channel_type_cd }</channel_type_cd>
						<channel_type_nm><![CDATA[${channel.channel_type_nm }]]></channel_type_nm>
						<param_id>${channel.param_id }</param_id>
						<param_val>${channel.param_val }</param_val>
						<partner_co_nm><![CDATA[${channel.partner_co_nm }]]></partner_co_nm>
						<reg_id>${channel.reg_id }</reg_id>
						<reg_date>${channel.reg_date }</reg_date>
						<upd_id>${channel.upd_id }</upd_id>
						<upd_date>${channel.upd_date }</upd_date>
					</channel>
				</c:forEach>
			</c:when>
		</c:choose>
	</mall_interface>
</ecsees>