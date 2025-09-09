package com.lottemart.epc.substn.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.ConfigurationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xlib.cmc.GridData;
import xlib.cmc.OperateGridData;

import com.lcnjf.util.StringUtil;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.service.CommonService;
import com.lottemart.epc.order.model.PSCMORD0002VO;
import com.lottemart.epc.substn.service.PSCMSBT0002Service;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;

/**
 *
 * @Class Name : pscmsbt0002Controller
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자	   수정내용
 *  -------    --------    ---------------------------
 * 2015. 11. 25   skc
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Controller("PSCMSBT0002Controller")
public class PSCMSBT0002Controller {
private static final Logger logger = LoggerFactory.getLogger(PSCMSBT0002Controller.class);

	@Autowired
	private PSCMSBT0002Service pscmsbt0002Service;
	@Autowired
	private ConfigurationService config;
	@Autowired
	private CommonService commonService;


	/**
	 * 공제조회 품 폼 페이지
	 * Desc : 공제조회 초기페이지 로딩
	 * @Method Name : deductForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("substn/viewSubStnForm.do")
	public String viewSubStnForm(HttpServletRequest request) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);
		request.setAttribute("epcLoginVO", epcLoginVO);

		// 대분류콤보
		List<DataMap> daeCdList = commonService.selectDaeCdList();
		request.setAttribute("daeCdList", daeCdList);

		return "substn/PSCMSBT0002";
	}


	/**
	 * 공제 목록 조회, WISEGRID로 리턴
	 * @see selectRepProdCdList
	 * @Locaton    : com.lottemart.bos.product.controller
	 * @MethodName  : selectDeductList
	 * @author     : hjKim
	 * @Description : 공제 목록 조회
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("substn/selectVendorSubStnList_org.do")
	public String selectVendorSubStnList_org(HttpServletRequest request) throws Exception {

		GridData gdRes = null;


		try {

			String sessionKey = config.getString("lottemart.epc.session.key");
			EpcLoginVO epcLoginVO = null;
			epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

			// 요청객체 획득
			String wiseGridData = request.getParameter("WISEGRID_DATA");
			GridData  gdReq = OperateGridData.parse(wiseGridData);

			// 결과객체
			gdRes = OperateGridData.cloneResponseGridData(gdReq);
			gdRes.addParam("mode", gdReq.getParam("mode"));



    		/* GRID getParam */



			String sYear			= gdReq.getParam("year"		);
			String sMonth			= gdReq.getParam("month"		);

			String accotYm			= gdReq.getParam("accotYm"		);
    		String adjSeq			= gdReq.getParam("adjSeq"		);
    		String vendorId			= StringUtil.null2str(gdReq.getParam("vendorId"		),"");
    		String catCd			= StringUtil.null2str(gdReq.getParam("catCd"		),"");


    		String frDt;
    		String toDt;
    		int iMaxDay = DateUtil.getDayCountForMonth(Integer.parseInt(sYear) , Integer.parseInt(sMonth));
    		if(adjSeq.equals("1")){
    			frDt = accotYm + "01";
    			toDt = accotYm + "10";
    		}else if(adjSeq.equals("2")){
    			frDt = accotYm + "11";
    			toDt = accotYm + "20";
    		}else{
    			frDt = accotYm + "21";
    			toDt = accotYm + Integer.toString(iMaxDay);
    		}


    		DataMap paramMap = new DataMap();
			paramMap.put("accotYm"		, accotYm		);
			paramMap.put("frDt"		, frDt		);
			paramMap.put("toDt"		, toDt		);
			paramMap.put("seq"		, adjSeq		);
			//paramMap.put("strCd"	, "981"		);
			paramMap.put("vendorId"	, vendorId		);
			paramMap.put("catCd"	, catCd		    );
			paramMap.put("venCds", epcLoginVO.getVendorId());

			logger.debug("[param]"+accotYm + "  " + adjSeq + "  "+ vendorId);

			// 데이터 조회
			List<DataMap> resultList = pscmsbt0002Service.selectVendorSubStnList(paramMap);

			// 조회된 데이터 가 없는 경우의 처리
	        if(resultList == null || resultList.size() == 0) {
	            gdRes.setStatus("false");
	    		request.setAttribute("wizeGridResult", gdRes);
	    		return "common/wiseGridResult";
	        }

			int size = resultList.size();
			DataMap map = null;

	        // GridData 셋팅
	        for(int i = 0; i < size; i++) {
	        	map = resultList.get(i);

	        	gdRes.getHeader("SALE_VENDOR_ID"           ).addValue(map.getString("SALE_VENDOR_ID"                 ), "");
	    	    gdRes.getHeader("SALE_VENDOR_NM"           ).addValue(map.getString("SALE_VENDOR_NM"                 ), "");
	    	    gdRes.getHeader("VENDOR_ID"                ).addValue(map.getString("VENDOR_ID"                      ), "");
	    	    gdRes.getHeader("VENDOR_NM"                ).addValue(map.getString("VENDOR_NM"                      ), "");
	    	    gdRes.getHeader("BUY_PRC"                  ).addValue(map.getString("BUY_PRC"                        ), "");
	    	    gdRes.getHeader("TAX_AMT"                  ).addValue(map.getString("TAX_AMT"                        ), "");
	    	    gdRes.getHeader("ORDER_AMT"                ).addValue(map.getString("ORDER_AMT"                      ), "");
	    	    gdRes.getHeader("CANCEL_ORDER_AMT"         ).addValue(map.getString("CANCEL_ORDER_AMT"               ), "");
	    	    gdRes.getHeader("TOTAL_ORDER_AMT"          ).addValue(map.getString("TOTAL_ORDER_AMT"                ), "");
	    	    gdRes.getHeader("PROFIT_AMT"               ).addValue(map.getString("PROFIT_AMT"                     ), "");


	    	    gdRes.getHeader("VEN_AMT"            ).addValue(map.getString("VEN_AMT"                  ), "");
	    	    gdRes.getHeader("COM_AMT"            ).addValue(map.getString("COM_AMT"                  ), "");

	    	    //gdRes.getHeader("CUPON_VEN_AMT"            ).addValue(map.getString("CUPON_VEN_AMT"                  ), "");
	    	    //gdRes.getHeader("CUPON_COM_AMT"            ).addValue(map.getString("CUPON_COM_AMT"                  ), "");
//	    	    gdRes.getHeader("CARD_VEN_AMT"            ).addValue(map.getString("CARD_VEN_AMT"                  ), "");
//	    	    gdRes.getHeader("CARD_COM_AMT"            ).addValue(map.getString("CARD_COM_AMT"                  ), "");
	    	    gdRes.getHeader("CARDCO_CARD_COM_AMT"     ).addValue(map.getString("CARDCO_CARD_COM_AMT"                  ), "");
	/*
	    	    gdRes.getHeader("CUPON_VEN_EX_AMT"     	).addValue(map.getString("CUPON_VEN_EX_AMT"                  ), "");
	    	    gdRes.getHeader("CUPON_COM_EX_AMT"     	).addValue(map.getString("CUPON_COM_EX_AMT"                  ), "");
	    	    gdRes.getHeader("CARD_VEN_EX_AMT"     	).addValue(map.getString("CARD_VEN_EX_AMT"                  ), "");
	    	    gdRes.getHeader("CARD_COM_EX_AMT"     	).addValue(map.getString("CARD_COM_EX_AMT"                  ), "");
	    	    gdRes.getHeader("CARDCO_CARD_EX_AMT"    ).addValue(map.getString("CARDCO_CARD_EX_AMT"                  ), "");

	*/    	    gdRes.getHeader("MILEAGE_DIV_AMT"       ).addValue(map.getString("MILEAGE_DIV_AMT"                  ), "");
	    	    gdRes.getHeader("BANN_DIV_AMT"          ).addValue(map.getString("BANN_DIV_AMT"                  ), "");
	    	    gdRes.getHeader("SALE_DELIVERY_AMT"     ).addValue(map.getString("SALE_DELIVERY_AMT"              ), "");

	        }

			// 처리성공
			gdRes.setStatus("true");
		} catch (Exception e) {
			// 작업오류
			gdRes.setMessage(e.getMessage());
	        gdRes.setStatus("false");
	        logger.debug("", e);
		}

		request.setAttribute("wizeGridResult", gdRes);
		return "common/wiseGridResult";
	}

	@RequestMapping(value = "substn/selectVendorSubStnList.do")
	public @ResponseBody Map selectVendorSubStnList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		PSCMORD0002VO searchVO = new PSCMORD0002VO();
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

        Map rtnMap = new HashMap<String, Object>();

		try {
			String sYear			= request.getParameter("year");
			String sMonth		= request.getParameter("month");
			String accotYm		= request.getParameter("accotYm");
    		String adjSeq			= request.getParameter("adjSeq");
    		String vendorId		= StringUtil.null2str(request.getParameter("vendorId"),"");
    		String catCd			= StringUtil.null2str(request.getParameter("catCd"),"");

    		String frDt = "";
    		String toDt = "";
    		int iMaxDay = DateUtil.getDayCountForMonth(Integer.parseInt(sYear) , Integer.parseInt(sMonth));
    		if(adjSeq.equals("1")){
    			frDt = accotYm + "01";
    			toDt = accotYm + "10";
    		}else if(adjSeq.equals("2")){
    			frDt = accotYm + "11";
    			toDt = accotYm + "20";
    		}else if(adjSeq.equals("3")){
    			frDt = accotYm + "21";
    			toDt = accotYm + Integer.toString(iMaxDay);
    		}else if(adjSeq.equals("4")){
    			frDt = accotYm + "01";
    			toDt = accotYm + Integer.toString(iMaxDay);
    		}


    		DataMap paramMap = new DataMap();
			paramMap.put("accotYm", accotYm);
			paramMap.put("frDt"		 , frDt);
			paramMap.put("toDt"		 , toDt);
			paramMap.put("seq"		 , adjSeq);
			paramMap.put("vendorId"	, vendorId	);
			paramMap.put("catCd"	, catCd);
			paramMap.put("venCds", epcLoginVO.getVendorId());


			// 데이터 조회
			List<DataMap> resultList = pscmsbt0002Service.selectVendorSubStnList(paramMap);

			rtnMap = JsonUtils.convertList2Json((List)resultList, resultList.size(), null);

			rtnMap.put("result", true);
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
		    rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}

	@RequestMapping("substn/selectVendorSubStnListExcel.do")
	public void selectVendorSubStnListExcel(HttpServletRequest request, HttpServletResponse res) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		PSCMORD0002VO searchVO = new PSCMORD0002VO();
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		try {
			String sYear			= request.getParameter("year");
			String sMonth		= request.getParameter("month");
			String accotYm		= request.getParameter("accotYm");
    		String adjSeq			= request.getParameter("adjSeq");
    		String vendorId		= StringUtil.null2str(request.getParameter("vendorId"),"");
    		String catCd			= StringUtil.null2str(request.getParameter("catCd"),"");


    		String frDt = "";
    		String toDt = "";
    		int iMaxDay = DateUtil.getDayCountForMonth(Integer.parseInt(sYear) , Integer.parseInt(sMonth));
    		if(adjSeq.equals("1")){
    			frDt = accotYm + "01";
    			toDt = accotYm + "10";
    		}else if(adjSeq.equals("2")){
    			frDt = accotYm + "11";
    			toDt = accotYm + "20";
    		}else if(adjSeq.equals("3")){
    			frDt = accotYm + "21";
    			toDt = accotYm + Integer.toString(iMaxDay);
    		}else if(adjSeq.equals("4")){
    			frDt = accotYm + "01";
    			toDt = accotYm + Integer.toString(iMaxDay);
    		}

    		DataMap paramMap = new DataMap();
			paramMap.put("accotYm", accotYm);
			paramMap.put("frDt"		 , frDt);
			paramMap.put("toDt"		 , toDt);
			paramMap.put("seq"		 , adjSeq);
			paramMap.put("vendorId"	, vendorId	);
			paramMap.put("catCd"	, catCd);
			paramMap.put("venCds", epcLoginVO.getVendorId());


			// 데이터 조회
			List<DataMap> resultList = pscmsbt0002Service.selectVendorSubStnList(paramMap);

			JsonUtils.IbsExcelDownload((List)resultList, request, res);
		} catch (Exception e) {
			// 작업오류
	        logger.error("error --> " + e.getMessage());
		}
	}

	/**
	 * 공제 목록 조회, WISEGRID로 리턴
	 * @see selectRepProdCdList
	 * @Locaton    : com.lottemart.bos.product.controller
	 * @MethodName  : selectDeductList
	 * @author     : hjKim
	 * @Description : 공제 목록 조회
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("substn/selectProdSubStnList_org.do")
	public String selectProdSubStnList_org(HttpServletRequest request) throws Exception {

		GridData gdRes = null;


		try {

			String sessionKey = config.getString("lottemart.epc.session.key");
			EpcLoginVO epcLoginVO = null;
			epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

			// 요청객체 획득
			String wiseGridData = request.getParameter("WISEGRID_DATA");
			GridData  gdReq = OperateGridData.parse(wiseGridData);

			// 결과객체
			gdRes = OperateGridData.cloneResponseGridData(gdReq);
			gdRes.addParam("mode", gdReq.getParam("mode"));



    		/* GRID getParam */



			String sYear			= gdReq.getParam("year"		);
			String sMonth			= gdReq.getParam("month"		);

			String accotYm			= gdReq.getParam("accotYm"		);
    		String adjSeq			= gdReq.getParam("adjSeq"		);
    		String vendorId			= StringUtil.null2str(gdReq.getParam("vendorId"		),"");
    		String catCd			= StringUtil.null2str(gdReq.getParam("catCd"		),"");
    		String frDt = "";
    		String toDt = "";
    		int iMaxDay = DateUtil.getDayCountForMonth(Integer.parseInt(sYear) , Integer.parseInt(sMonth));
    		if(adjSeq.equals("1")){
    			frDt = accotYm + "01";
    			toDt = accotYm + "10";
    		}else if(adjSeq.equals("2")){
    			frDt = accotYm + "11";
    			toDt = accotYm + "20";
    		}else {
    			frDt = accotYm + "21";
    			toDt = accotYm + Integer.toString(iMaxDay);
    		}


    		DataMap paramMap = new DataMap();
			paramMap.put("accotYm"		, accotYm		);
			paramMap.put("frDt"		, frDt		);
			paramMap.put("toDt"		, toDt		);
			paramMap.put("seq"		, adjSeq		);
			//paramMap.put("strCd"	, "981"		);
			paramMap.put("vendorId"	, vendorId		);
			paramMap.put("catCd"	, catCd		    );
			paramMap.put("venCds", epcLoginVO.getVendorId());

			logger.debug("[param]"+accotYm + "  " + adjSeq + "  "+ vendorId);

			// 데이터 조회
			List<DataMap> resultList = pscmsbt0002Service.selectProdSubStnList(paramMap);

			// 조회된 데이터 가 없는 경우의 처리
	        if(resultList == null || resultList.size() == 0) {
	            gdRes.setStatus("false");
	    		request.setAttribute("wizeGridResult", gdRes);
	    		return "common/wiseGridResult";
	        }

			int size = resultList.size();
			DataMap map = null;

	        // GridData 셋팅
	        for(int i = 0; i < size; i++) {
	        	map = resultList.get(i);

	            gdRes.getHeader("NUM"                      ).addValue(map.getString("NUM"                                   ), "");
	            gdRes.getHeader("SALE_VENDOR_ID"           ).addValue(map.getString("SALE_VENDOR_ID"                        ), "");
	            gdRes.getHeader("SALE_VENDOR_NM"           ).addValue(map.getString("SALE_VENDOR_NM"                        ), "");
	            gdRes.getHeader("VENDOR_ID"                ).addValue(map.getString("VENDOR_ID"                             ), "");
	            gdRes.getHeader("VENDOR_NM"                ).addValue(map.getString("VENDOR_NM"                             ), "");
	            gdRes.getHeader("PROD_CD"                  ).addValue(map.getString("PROD_CD"                               ), "");
	            gdRes.getHeader("PROD_NM"                  ).addValue(map.getString("PROD_NM"                               ), "");
	            gdRes.getHeader("BUY_PRC"                  ).addValue(map.getString("BUY_PRC"                               ), "");
	            gdRes.getHeader("TAX_AMT"                  ).addValue(map.getString("TAX_AMT"                               ), "");
	            gdRes.getHeader("ORDER_AMT"                ).addValue(map.getString("ORDER_AMT"                             ), "");
	            gdRes.getHeader("CANCEL_ORDER_AMT"         ).addValue(map.getString("CANCEL_ORDER_AMT"                      ), "");
	            gdRes.getHeader("TOTAL_ORDER_AMT"          ).addValue(map.getString("TOTAL_ORDER_AMT"                       ), "");
	            gdRes.getHeader("PROFIT_AMT"               ).addValue(map.getString("PROFIT_AMT"                            ), "");
	            gdRes.getHeader("SALE_DELIVERY_AMT"        ).addValue(map.getString("SALE_DELIVERY_AMT"                     ), "");


	            gdRes.getHeader("ORDER_CNT"                ).addValue(map.getString("ORDER_CNT"                              ), "");
	            gdRes.getHeader("CANCEL_CNT"               ).addValue(map.getString("CANCEL_CNT"                              ), "");

	            gdRes.getHeader("SALE_QTY"                 ).addValue(map.getString("SALE_QTY"                              ), "");
	            gdRes.getHeader("SALE_AMT"                 ).addValue(map.getString("SALE_AMT"                              ), "");
	            gdRes.getHeader("DC_AMT"                   ).addValue(map.getString("DC_AMT"                                ), "");

	        }

			// 처리성공
			gdRes.setStatus("true");
		} catch (Exception e) {
			// 작업오류
			gdRes.setMessage(e.getMessage());
	        gdRes.setStatus("false");
	        logger.debug("", e);
		}

		request.setAttribute("wizeGridResult", gdRes);
		return "common/wiseGridResult";
	}

	@RequestMapping(value = "substn/selectProdSubStnList.do")
	public @ResponseBody Map selectProdSubStnList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		PSCMORD0002VO searchVO = new PSCMORD0002VO();
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

        Map rtnMap = new HashMap<String, Object>();

		try {
			String sYear			= request.getParameter("year");
			String sMonth		= request.getParameter("month");
			String accotYm		= request.getParameter("accotYm");
    		String adjSeq			= request.getParameter("adjSeq");
    		String vendorId		= StringUtil.null2str(request.getParameter("vendorId"),"");
    		String catCd			= StringUtil.null2str(request.getParameter("catCd"),"");
    		String taxatDivn = request.getParameter("taxatDivn");
    		String taxatDivnCd = "";

    		String frDt;
    		String toDt;
    		int iMaxDay = DateUtil.getDayCountForMonth(Integer.parseInt(sYear) , Integer.parseInt(sMonth));
    		if(adjSeq.equals("1")){
    			frDt = accotYm + "01";
    			toDt = accotYm + "10";
    		}else if(adjSeq.equals("2")){
    			frDt = accotYm + "11";
    			toDt = accotYm + "20";
    		}else if(adjSeq.equals("3")){
    			frDt = accotYm + "21";
    			toDt = accotYm + Integer.toString(iMaxDay);
    		}else{
    			frDt = accotYm + "01";
    			toDt = accotYm + Integer.toString(iMaxDay);
    		}

    		if(taxatDivn.equals("면세")){
    			taxatDivnCd = "0";
    		}else if(taxatDivn.equals("과세")){
    			taxatDivnCd = "1";
    		}else if(taxatDivn.equals("영세")){
    			taxatDivnCd = "2";
    		}
    		DataMap paramMap = new DataMap();
			paramMap.put("accotYm", accotYm);
			paramMap.put("frDt"		 , frDt);
			paramMap.put("toDt"		 , toDt);
			paramMap.put("seq"		 , adjSeq);
			paramMap.put("vendorId"	, vendorId	);
			paramMap.put("catCd"	, catCd);
			paramMap.put("venCds", epcLoginVO.getVendorId());
			paramMap.put("taxatDivnCd"	, taxatDivnCd		    );

			// 데이터 조회
			List<DataMap> resultList = pscmsbt0002Service.selectProdSubStnList(paramMap);

			rtnMap = JsonUtils.convertList2Json((List)resultList, resultList.size(), null);

			rtnMap.put("result", true);
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
		    rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}

	@RequestMapping(value = "substn/selectProdSubStnListExcel.do")
	public void selectProdSubStnListExcel(HttpServletRequest request, HttpServletResponse res) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		PSCMORD0002VO searchVO = new PSCMORD0002VO();
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

        Map rtnMap = new HashMap<String, Object>();

		try {
			String sYear			= request.getParameter("year");
			String sMonth		= request.getParameter("month");
			String accotYm		= request.getParameter("accotYm");
    		String adjSeq			= request.getParameter("adjSeq");
    		String vendorId		= StringUtil.null2str(request.getParameter("vendorId"),"");
    		String catCd			= StringUtil.null2str(request.getParameter("catCd"),"");

    		String taxatDivnCd = request.getParameter("taxatDivnCd");

    		String frDt;
    		String toDt;
    		int iMaxDay = DateUtil.getDayCountForMonth(Integer.parseInt(sYear) , Integer.parseInt(sMonth));
    		if(adjSeq.equals("1")){
    			frDt = accotYm + "01";
    			toDt = accotYm + "10";
    		}else if(adjSeq.equals("2")){
    			frDt = accotYm + "11";
    			toDt = accotYm + "20";
    		}else if(adjSeq.equals("3")){
    			frDt = accotYm + "21";
    			toDt = accotYm + Integer.toString(iMaxDay);
    		}else{
    			frDt = accotYm + "01";
    			toDt = accotYm + Integer.toString(iMaxDay);
    		}


    		DataMap paramMap = new DataMap();
			paramMap.put("accotYm", accotYm);
			paramMap.put("frDt"		 , frDt);
			paramMap.put("toDt"		 , toDt);
			paramMap.put("seq"		 , adjSeq);
			paramMap.put("vendorId"	, vendorId	);
			paramMap.put("catCd"	, catCd);
			paramMap.put("venCds", epcLoginVO.getVendorId());
			paramMap.put("taxatDivnCd"	, taxatDivnCd		    );

			// 데이터 조회
			List<DataMap> resultList = pscmsbt0002Service.selectProdSubStnList(paramMap);

			JsonUtils.IbsExcelDownload((List)resultList, request, res);
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
		}
	}
	@SuppressWarnings("unused")
	private static String lPad(String str, int size, String fStr) {
	    byte[] b = str.getBytes();
	    int len = b.length;
	    String tmpStr = str;

	    int tmp = size - len;

	    for (int i=0; i < tmp ; i++){
	    	tmpStr = fStr + tmpStr;
	    }
	    return tmpStr;
	}
}

