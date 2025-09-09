package com.lottemart.epc.ecsees.mallInterface.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lottemart.epc.ecsees.mallInterface.model.*;
import com.lottemart.epc.ecsees.mallInterface.service.EcseesService;

import com.lottemart.epc.common.util.SecureUtil;

@Controller
public class EcseesController {

	@Autowired
	private EcseesService ecseesService;

	@RequestMapping("ecsees/mallInterface/team.do")
	public String getTeamList(HttpServletRequest request) throws Exception{
		String url = "ecsees/mallInterface/team";
		try {
			List<TeamVO> team_list = ecseesService.selectTeamList(SecureUtil.stripXSS(request.getParameter("sync_date")));
			request.setAttribute("team_list", team_list);
			request.setAttribute("total_count", team_list.size());
		} catch (Exception e) {
			request.setAttribute("errorMessage" , e.getMessage());
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			request.setAttribute("errorStackTrace", sw.toString());
			url = "ecsees/mallInterface/error";
		}
		return url;
	};

	@RequestMapping("ecsees/mallInterface/channel.do")
	public String getChannelList(HttpServletRequest request) throws Exception{
		String url = "ecsees/mallInterface/channel";
		try {
			List<ChannelVO> channel_list = ecseesService.selectChannelList(SecureUtil.stripXSS(request.getParameter("sync_date")));
			request.setAttribute("channel_list", channel_list);
			request.setAttribute("total_count", channel_list.size());
		} catch (Exception e) {
			request.setAttribute("errorMessage" ,e.getMessage());
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			request.setAttribute("errorStackTrace", sw.toString());
			url = "ecsees/mallInterface/error";
		}
		return url;
	};

	@RequestMapping("ecsees/mallInterface/order_item.do")
	public String getOrder_itemList(HttpServletRequest request) throws Exception{
		String url = "ecsees/mallInterface/order_item";
		try {
			HashMap<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("startDate", request.getParameter("startDate"));
			paramMap.put("endDate", request.getParameter("endDate"));
			List<Order_itemVO> order_item_list = ecseesService.selectOrder_itemList(paramMap);
			request.setAttribute("order_item_list", order_item_list);
			request.setAttribute("total_count", order_item_list.size());
		} catch (Exception e) {
			request.setAttribute("errorMessage" ,e.getMessage());
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			request.setAttribute("errorStackTrace", sw.toString());
			url = "ecsees/mallInterface/error";
		}
		return url;
	};

	@RequestMapping("ecsees/mallInterface/order_itemById.do")
	public String getOrder_itemById(HttpServletRequest request) throws Exception{
		String url = "ecsees/mallInterface/order_item";
		try {
			if(request.getParameter("orderIdList") == null ) {
				request.setAttribute("total_count", 0);
				return url;
			}
			List<String> orderIdList = new ArrayList<String>(Arrays.asList(request.getParameter("orderIdList").split("#")));

			List<Order_itemVO> order_item_list = ecseesService.selectOrder_itemById(orderIdList);
			request.setAttribute("order_item_list", order_item_list);
			request.setAttribute("total_count", order_item_list.size());
		} catch (Exception e) {
			request.setAttribute("errorMessage" ,e.getMessage());
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			request.setAttribute("errorStackTrace", sw.toString());
			url = "ecsees/mallInterface/error";
		}
		return url;
	};

	@RequestMapping("ecsees/mallInterface/product.do")
	public String getProductList(HttpServletRequest request) throws Exception{
		String url = "ecsees/mallInterface/product";
		try {
			List<ProductVO> product_list = ecseesService.selectProductList(SecureUtil.stripXSS(request.getParameter("sync_date")));
			request.setAttribute("product_list", product_list);
			request.setAttribute("total_count", product_list.size());
		} catch (Exception e) {
			request.setAttribute("errorMessage" ,e.getMessage());
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			request.setAttribute("errorStackTrace", sw.toString());
			url = "ecsees/mallInterface/error";
		}
		return url;
	};

	@RequestMapping("ecsees/mallInterface/productById.do")
	public String getProductById(HttpServletRequest request) throws Exception{
		String url = "ecsees/mallInterface/product";
		try {
			List<ProductVO> product_list = ecseesService.selectProductById(SecureUtil.stripXSS(request.getParameter("product_id")));
			request.setAttribute("product_list", product_list);
			request.setAttribute("total_count", product_list.size());
		} catch (Exception e) {
			request.setAttribute("errorMessage" ,e.getMessage());
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			request.setAttribute("errorStackTrace", sw.toString());
			url = "ecsees/mallInterface/error";
		}
		return url;
	};

	@RequestMapping("ecsees/mallInterface/store.do")
	public String getStoreList(HttpServletRequest request) throws Exception{
		String url = "ecsees/mallInterface/store";
		try {
			List<StoreVO> store_list = ecseesService.selectStoreList(SecureUtil.stripXSS(request.getParameter("sync_date")));
			request.setAttribute("store_list", store_list);
			request.setAttribute("total_count", store_list.size());
		} catch (Exception e) {
			request.setAttribute("errorMessage" ,e.getMessage());
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			request.setAttribute("errorStackTrace", sw.toString());
			url = "ecsees/mallInterface/error";
		}
		return url;
	};

	@RequestMapping("ecsees/mallInterface/category.do")
	public String getCategoryList(HttpServletRequest request) throws Exception{
		String url = "ecsees/mallInterface/category";
		try {
			List<CategoryVO> categoryList = ecseesService.selectCategoryList(SecureUtil.stripXSS(request.getParameter("sync_date")));
			request.setAttribute("categoryList", categoryList);
			request.setAttribute("total_count", categoryList.size());
		} catch (Exception e) {
			request.setAttribute("errorMessage" ,e.getMessage());
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			request.setAttribute("errorStackTrace", sw.toString());
			url = "ecsees/mallInterface/error";
		}
		return url;
	};

	@RequestMapping("ecsees/mallInterface/affiliate.do")
	public String getAffiliateList(HttpServletRequest request) throws Exception{
		String url = "ecsees/mallInterface/affiliate";
		try {
			List<AffiliateVO> affiliateList = ecseesService.selectAffiliateList(SecureUtil.stripXSS(request.getParameter("sync_date")));
			request.setAttribute("affiliate_List", affiliateList);
			request.setAttribute("total_count", affiliateList.size());
		} catch (Exception e) {
			request.setAttribute("errorMessage" ,e.getMessage());
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			request.setAttribute("errorStackTrace", sw.toString());
			url = "ecsees/mallInterface/error";
		}
		return url;
	};

	@RequestMapping("ecsees/mallInterface/orderCancellation.do")
	public String getOrderCancellation(HttpServletRequest request) throws Exception{
		String url = "ecsees/mallInterface/order_item";
		try {
			HashMap<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("startDate", request.getParameter("startDate"));
			paramMap.put("endDate", request.getParameter("endDate"));
			List<Order_itemVO> order_item_list = ecseesService.selectOrderCancellation(paramMap);
			request.setAttribute("order_item_list", order_item_list);
			request.setAttribute("total_count", order_item_list.size());
		} catch (Exception e) {
			request.setAttribute("errorMessage" ,e.getMessage());
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			request.setAttribute("errorStackTrace", sw.toString());
			url = "ecsees/mallInterface/error";
		}
		return url;
	};

	@RequestMapping("ecsees/mallInterface/productNaverFinal.do")
	public String getProductNaverFinal(HttpServletRequest request) throws Exception{
		String url = "ecsees/mallInterface/productNaverFinal";
		try {
			HashMap<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("sync_date", request.getParameter("sync_date"));
			paramMap.put("startRN", request.getParameter("startRN"));
			List<ProductNaverFinalVO> product_naver_final_list = ecseesService.selectProductNaverFinalList(paramMap);
			request.setAttribute("product_naver_final_list", product_naver_final_list);
			request.setAttribute("total_count", product_naver_final_list.size());
		} catch (Exception e) {
			request.setAttribute("errorMessage" ,e.getMessage());
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			request.setAttribute("errorStackTrace", sw.toString());
			url = "ecsees/mallInterface/error";
		}
		return url;
	};
}
