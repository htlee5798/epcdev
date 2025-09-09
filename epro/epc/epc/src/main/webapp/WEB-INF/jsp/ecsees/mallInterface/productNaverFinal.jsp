<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<ecsees>
	<mall_interface total_count="${total_count}">
		<c:choose>
			<c:when test="${total_count != '0' }">
				<c:forEach items="${product_naver_final_list }" var="product" begin="0">
					<product>
						<tr_dt><![CDATA[${product.tr_dt}]]></tr_dt>
						<tr_hh><![CDATA[${product.tr_hh}]]></tr_hh>
						<prod_cd><![CDATA[${product.prod_cd}]]></prod_cd>
						<item_id><![CDATA[${product.item_id}]]></item_id>
						<srcmk_cd><![CDATA[${product.srcmk_cd}]]></srcmk_cd>
						<nv_mapid><![CDATA[${product.nv_mapid}]]></nv_mapid>
						<nv_pname><![CDATA[${product.nv_pname}]]></nv_pname>
						<nv_price><![CDATA[${product.nv_price}]]></nv_price>
						<nv_pgurl><![CDATA[${product.nv_pgurl}]]></nv_pgurl>
						<nv_igurl><![CDATA[${product.nv_igurl}]]></nv_igurl>
						<nv_cate1><![CDATA[${product.nv_cate1}]]></nv_cate1>
						<nv_cate2><![CDATA[${product.nv_cate2}]]></nv_cate2>
						<nv_cate3><![CDATA[${product.nv_cate3}]]></nv_cate3>
						<nv_cate4><![CDATA[${product.nv_cate4}]]></nv_cate4>
						<nv_caid1><![CDATA[${product.nv_caid1}]]></nv_caid1>
						<nv_caid2><![CDATA[${product.nv_caid2}]]></nv_caid2>
						<nv_caid3><![CDATA[${product.nv_caid3}]]></nv_caid3>
						<nv_caid4><![CDATA[${product.nv_caid4}]]></nv_caid4>
						<nv_model><![CDATA[${product.nv_model}]]></nv_model>
						<nv_brand><![CDATA[${product.nv_brand}]]></nv_brand>
						<nv_maker><![CDATA[${product.nv_maker}]]></nv_maker>
						<nv_origi><![CDATA[${product.nv_origi}]]></nv_origi>
						<nv_pdate><![CDATA[${product.nv_pdate}]]></nv_pdate>
						<nv_deliv><![CDATA[${product.nv_deliv}]]></nv_deliv>
						<nv_event><![CDATA[${product.nv_event}]]></nv_event>
						<nv_coupo><![CDATA[${product.nv_coupo}]]></nv_coupo>
						<nv_pcard><![CDATA[${product.nv_pcard}]]></nv_pcard>
						<nv_point><![CDATA[${product.nv_point}]]></nv_point>
						<nv_modig><![CDATA[${product.nv_modig}]]></nv_modig>
						<nv_ptype><![CDATA[${product.nv_ptype}]]></nv_ptype>
						<nv_dterm><![CDATA[${product.nv_dterm}]]></nv_dterm>
						<nv_risky><![CDATA[${product.nv_risky}]]></nv_risky>
						<nv_barcode><![CDATA[${product.nv_barcode}]]></nv_barcode>
						<nv_class><![CDATA[${product.nv_class}]]></nv_class>
						<nv_utime><![CDATA[${product.nv_utime}]]></nv_utime>
						<reg_date><![CDATA[${product.reg_date}]]></reg_date>
						<reg_id><![CDATA[${product.reg_id}]]></reg_id>
						<mod_date><![CDATA[${product.mod_date}]]></mod_date>
						<mod_id><![CDATA[${product.mod_id}]]></mod_id>
						<nv_revct><![CDATA[${product.nv_revct}]]></nv_revct>  
					</product>
				</c:forEach>
			</c:when>
		</c:choose>
	</mall_interface>
</ecsees>