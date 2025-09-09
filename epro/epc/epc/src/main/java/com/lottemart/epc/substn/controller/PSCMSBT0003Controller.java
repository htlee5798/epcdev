package com.lottemart.epc.substn.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.util.DateUtil;
import lcn.module.common.views.AjaxJsonModelHelper;
import lcn.module.framework.property.ConfigurationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import xlib.cmc.GridData;
import xlib.cmc.OperateGridData;

import com.lcnjf.util.StringUtil;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.service.CommonService;
import com.lottemart.epc.order.model.PSCMORD0002VO;
import com.lottemart.epc.substn.service.PSCMSBT0003Service;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;

/**
 *
 * @Class Name : pscmsbt0003Controller
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
@Controller("PSCMSBT0003Controller")
public class PSCMSBT0003Controller {
private static final Logger logger = LoggerFactory.getLogger(PSCMSBT0003Controller.class);

	@Autowired
	private PSCMSBT0003Service pscmsbt0003Service;
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
	@RequestMapping("substn/viewSubStnAdjForm.do")
	public String viewSubStnAdjForm(HttpServletRequest request) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);
		request.setAttribute("epcLoginVO", epcLoginVO);

		// 대분류콤보
		List<DataMap> daeCdList = commonService.selectDaeCdList();
		request.setAttribute("daeCdList", daeCdList);

		return "substn/PSCMSBT0003";
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
	@RequestMapping("substn/selectSubStnDeductList_org.do")
	public String selectSubStnDeductList_org(HttpServletRequest request) throws Exception {

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
			List<DataMap> resultList = pscmsbt0003Service.selectSubStnDeductList(paramMap);

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

	        	gdRes.getHeader("FIRST_ORDER_ID"    ).addValue(map.getString("FIRST_ORDER_ID"    	), "");
	        	gdRes.getHeader("MEMBER_NO"         ).addValue(map.getString("MEMBER_NO"        	), "");
	        	gdRes.getHeader("ORD_DY"           	).addValue(map.getString("ORD_DY"           	), "");
	        	gdRes.getHeader("CAT_CD"           	).addValue(map.getString("CAT_CD"           	), "");
	        	gdRes.getHeader("CAT_NM"           	).addValue(map.getString("CAT_NM"           	), "");
	        	gdRes.getHeader("PROD_CD"           ).addValue(map.getString("PROD_CD"           	), "");
	        	gdRes.getHeader("PROD_NM"           ).addValue(map.getString("PROD_NM"           	), "");
	        	gdRes.getHeader("SELL_PRC"           ).addValue(map.getString("SELL_PRC"           	), "");
	        	gdRes.getHeader("TAX_AMT"           ).addValue(map.getString("TAX_AMT"           	), "");
	        	gdRes.getHeader("SALE_AMT"          ).addValue(map.getString("SALE_AMT"           	), "");
	        	gdRes.getHeader("DC_AMT"           	).addValue(map.getString("DC_AMT"           	), "");
	        	gdRes.getHeader("PRFT_RATE"         ).addValue(map.getString("PRFT_RATE"         	), "");
	        	gdRes.getHeader("PRFT_AMT"          ).addValue(map.getString("PRFT_AMT"          	), "");
	        	gdRes.getHeader("COM_SHCH_RT"    	).addValue(map.getString("COM_SHCH_RT"    		), "");
	        	gdRes.getHeader("CORP_SHCH_RT"      ).addValue(map.getString("CORP_SHCH_RT"      	), "");
	        	gdRes.getHeader("CARD_SHCH_RT"      ).addValue(map.getString("CARD_SHCH_RT"      	), "");
	        	gdRes.getHeader("COM_AMT"           ).addValue(map.getString("COM_AMT"           	), "");
	        	gdRes.getHeader("CORP_AMT"          ).addValue(map.getString("CORP_AMT"          	), "");
	        	gdRes.getHeader("MILEAGE_AMT"       ).addValue(map.getString("MILEAGE_AMT"       	), "");
	        	gdRes.getHeader("BANNER_AMT"        ).addValue(map.getString("BANNER_AMT"        	), "");
	        	gdRes.getHeader("DELIVERY_AMT"      ).addValue(map.getString("DELIVERY_AMT"      	), "");

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

	@RequestMapping(value = "substn/selectSubStnDeductList.do")
	public @ResponseBody Map selectSubStnDeductList(HttpServletRequest request, HttpServletResponse response) throws Exception {

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
			paramMap.put("accotYm", accotYm);
			paramMap.put("frDt"		 , frDt);
			paramMap.put("toDt"		 , toDt);
			paramMap.put("seq"		 , adjSeq);
			paramMap.put("vendorId"	, vendorId	);
			paramMap.put("catCd"	, catCd);
			paramMap.put("venCds", epcLoginVO.getVendorId());


			// 데이터 조회
			List<DataMap> resultList = pscmsbt0003Service.selectSubStnDeductList(paramMap);

			rtnMap = JsonUtils.convertList2Json((List)resultList, resultList.size(), null);

			rtnMap.put("result", true);
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
		    rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}

	@RequestMapping("substn/selectSubStnDeductListExcel.do")
	public void selectSubStnDeductListExcel(HttpServletRequest request, HttpServletResponse res) throws Exception {
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
			paramMap.put("accotYm", accotYm);
			paramMap.put("frDt"		 , frDt);
			paramMap.put("toDt"		 , toDt);
			paramMap.put("seq"		 , adjSeq);
			paramMap.put("vendorId"	, vendorId	);
			paramMap.put("catCd"	, catCd);
			paramMap.put("venCds", epcLoginVO.getVendorId());


			// 데이터 조회
			List<DataMap> resultList = pscmsbt0003Service.selectSubStnDeductList(paramMap);

			JsonUtils.IbsExcelDownload((List)resultList, request, res);
		} catch (Exception e) {
			// 작업오류
	        logger.error("error --> " + e.getMessage());
		}
	}

	/**
	 *
	 * Desc :
	 * @Method Name : selectSubStnDeductSumList
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("substn/selectSubStnDeductSumList.do")
	public ModelAndView selectSubStnDeductSumList(HttpServletRequest request) throws Exception {

		/* GRID getParam */

		String sYear			= request.getParameter("year"		);
		String sMonth			= request.getParameter("month"		);

		String accotYm			= request.getParameter("accotYm"		);
		String adjSeq			= request.getParameter("adjSeq"		);
		String vendorId			= StringUtil.null2str(request.getParameter("vendorId"		),"");
		String catCd			= StringUtil.null2str(request.getParameter("catCd"		),"");


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
		paramMap.put("accotYm"	, accotYm		);
		paramMap.put("frDt"		, frDt		);
		paramMap.put("toDt"		, toDt		);
		paramMap.put("seq"		, adjSeq		);
		paramMap.put("strCd"	, "981"		);
		paramMap.put("vendorId"	, vendorId		);
		paramMap.put("catCd"	, catCd		    );


		// 통계 데이터 조회
		List<DataMap> stats = pscmsbt0003Service.selectSubStnDeductSumList(paramMap);

		// json 결과 생성
		DataMap resultMap = new DataMap();
		resultMap.put("stats", stats.get(0));

		return AjaxJsonModelHelper.create(resultMap);
	}


}

