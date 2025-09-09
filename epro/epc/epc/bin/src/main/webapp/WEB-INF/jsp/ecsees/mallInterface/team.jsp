<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<ecsees>
	<mall_interface total_count="${total_count}">
		<c:choose>
			<c:when test="${total_count != '0' }">
				<c:forEach items="${team_list}" var="team" begin="0">
					<team id="${team.team_cd}">
						<team_nm><![CDATA[${team.team_nm}]]></team_nm>
					</team>
				</c:forEach>	
			</c:when>
		</c:choose>
	</mall_interface>
</ecsees>