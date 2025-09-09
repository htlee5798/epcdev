package com.lottemart.epc.edi.product.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lottemart.epc.edi.comm.controller.BaseController;
import com.lottemart.epc.edi.product.model.SearchProduct;


@Controller
public class PEDMPRO0009Controller extends BaseController{

//	@Autowired
//	private PEDPCOM0001Service pedpcom0001service;
//
//	private PEDMPRO000Service ediProductService;

//	@Resource(name = "configurationService")
//	private ConfigurationService config;

//	@Autowired
//	private PSCMORD0004Service pscmord0004Service;

	//private PEDMPRO0009Service pEDMPRO0009Service;

	//private static final Logger logger = LoggerFactory.getLogger(PEDMPRO0009Controller.class);



//	@Autowired
//	public void setEdiProductService(PEDMPRO000Service ediProductService) {
//		this.ediProductService = ediProductService;
//	}

//	@Autowired
//	public void setpEDMPRO0009Service(PEDMPRO0009Service pEDMPRO0009Service) {
//		this.pEDMPRO0009Service = pEDMPRO0009Service;
//	}


	//점포별 상품 현황
	@RequestMapping(value="/edi/product/PEDMPRO0009.do")
    public String wholeStoreProductList(SearchProduct searchParam, HttpServletRequest request, Model model) throws Exception {


		return "edi/product/PEDMPRO0009";
	}






	//전체 현황 다운로드 체크
		@RequestMapping(value="/edi/product/PEDMPRO0009DownloadCheck")
		public ModelAndView wholeStoreProductListDownload(SearchProduct searchParam)
			 {
			return null;
		}

		//전체 상품현황 다운로드
		@RequestMapping(value="/edi/product/PEDMPRO0009Download")
		public void wholeProductListDownload(SearchProduct searchParam, HttpServletRequest request,
				HttpServletResponse response)
			throws Exception {
			/** paging setting */

		}



}
