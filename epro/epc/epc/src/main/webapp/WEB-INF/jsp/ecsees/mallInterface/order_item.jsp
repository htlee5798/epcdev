<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<ecsees>
	<mall_interface total_count="${total_count}">
		<c:choose>
			<c:when test="${total_count != '0' }">
				<c:forEach items="${order_item_list }" var="order_item" begin="0">
					<order_item order_id="${order_item.order_id}">
						<order_date>${order_item.order_date}</order_date>
						<first_order_id>${order_item.first_order_id}</first_order_id>
						<ord_sts_cd>${order_item.ord_sts_cd}</ord_sts_cd>
						<ord_sts_nm>${order_item.ord_sts_nm}</ord_sts_nm>
						<tot_order_qty>${order_item.tot_order_qty}</tot_order_qty>
						<tot_order_amt>${order_item.tot_order_amt}</tot_order_amt>
						<order_dtl_seq>${order_item.order_dtl_seq}</order_dtl_seq>
						<first_order_item_seq>${order_item.first_order_item_seq}</first_order_item_seq>
						<product_id>${order_item.product_id}</product_id>
						<item_id>${order_item.item_id}</item_id>
						<team_id>${order_item.team_id}</team_id>
						<order_qty>${order_item.order_qty}</order_qty>
						<order_amt>${order_item.order_amt}</order_amt>
						<str_cd>${order_item.str_cd}</str_cd>
						<reg_id>${order_item.reg_id}</reg_id>
						<reg_date>${order_item.reg_date}</reg_date>
						<upd_id>${order_item.upd_id}</upd_id>
						<upd_date>${order_item.upd_date}</upd_date>
						<category_id>${order_item.category_id}</category_id>
						<channel_cd>${order_item.channel_cd}</channel_cd>
						<affiliate_id>${order_item.affiliate_id}</affiliate_id>
					</order_item>
				</c:forEach>
			</c:when>
		</c:choose>
	</mall_interface>
</ecsees>