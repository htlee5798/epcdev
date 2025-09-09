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
import com.lottemart.epc.substn.service.PSCMSBT0001Service;
import com.lottemart.common.code.service.CommonCodeService;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;

/**
 *
 * @Class Name : pscmsbt0001Controller
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
@Controller("PSCMSBT0001Controller")
public class PSCMSBT0001Controller {
private static final Logger logger = LoggerFactory.getLogger(PSCMSBT0001Controller.class);

	@Autowired
	private PSCMSBT0001Service pscmsbt0001Service;
	@Autowired
	private ConfigurationService config;
	@Autowired
	private CommonService commonService;
	@Autowired
	private CommonCodeService commonCodeService;


	/**
	 * 공제조회 품 폼 페이지
	 * Desc : 공제조회 초기페이지 로딩
	 * @Method Name : deductForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("substn/viewPaySubStnForm.do")
	public String viewPaySubStnForm(HttpServletRequest request) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);
		request.setAttribute("epcLoginVO", epcLoginVO);

		// 대분류콤보
		List<DataMap> daeCdList = commonService.selectDaeCdList();
		request.setAttribute("daeCdList", daeCdList);

		Map<String,String> paramMap = new HashMap<String,String>();

		//주문상태
		List<DataMap> ordCdList = commonCodeService.getCodeList("OR002");
		request.setAttribute("ordCdList", ordCdList);

		//매출상태
		List<DataMap> saleCdList = commonCodeService.getCodeList("OR003");
		request.setAttribute("saleCdList", saleCdList);

		//결제수단코드
		//paramMap.put("majorCd", "OR008");

		//쿠폰유형코드
		paramMap.put("majorCd", "SM014");
		List<DataMap> codeList = commonService.selectTetCodeList(paramMap);
		//request.setAttribute("codeListOR008", codeList);
		request.setAttribute("codeListSM014", codeList);

		return "substn/PSCMSBT0001";
	}


	/**
	 * 정산지불현황 조회, WISEGRID로 리턴
	 * @see selectPaySubStnList
	 * @Locaton    : com.lottemart.bos.product.controller
	 * @MethodName  : selectDeductList
	 * @author     : hjKim
	 * @Description : 정산지불현황 조회
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("substn/selectPaySubStnList_org.do")
	public String selectPaySubStnList_org(HttpServletRequest request) throws Exception {

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
    		String catCd			= StringUtil.null2str(gdReq.getParam("catCd"		),"");
    		String setlTypeCd		= gdReq.getParam("setlTypeCd"		);
    		String ordStsCd			= gdReq.getParam("ordStsCd"		);
    		String saleStsCd		= gdReq.getParam("saleStsCd"		);
    		String vendorId			= StringUtil.null2str(gdReq.getParam("vendorId"		),"");
    		String prodCd			= gdReq.getParam("prodCd"		);


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


			//Map<String,String> paramMap = new HashMap<String,String>();
			DataMap paramMap = new DataMap();
			paramMap.put("accotYm"		, accotYm		);
			paramMap.put("frDt"		, frDt		);
			paramMap.put("toDt"		, toDt		);
			paramMap.put("seq"		, adjSeq		);
			paramMap.put("strCd"	, "981"		);
			paramMap.put("catCd"	, catCd		    );
			paramMap.put("setlTypeCd"	, setlTypeCd		    );
			paramMap.put("ordStsCd"	, ordStsCd		    );
			paramMap.put("saleStsCd"	, saleStsCd		    );
			paramMap.put("prodCd"	, prodCd		    );
			paramMap.put("vendorId"	, vendorId		);
			paramMap.put("venCds", epcLoginVO.getVendorId());

			logger.debug("[param]"+accotYm + "  " + adjSeq + "  "+ vendorId);

			// 데이터 조회
			List<DataMap> resultList = pscmsbt0001Service.selectPaySubStnList(paramMap);

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

				gdRes.getHeader("NUM"                      ).addValue(map.getString("NUM"                            ), "");
	        	gdRes.getHeader("SALE_VENDOR_ID"           ).addValue(map.getString("SALE_VENDOR_ID"                 ), "");
	    	    gdRes.getHeader("SALE_VENDOR_NM"           ).addValue(map.getString("SALE_VENDOR_NM"                 ), "");
	    	    gdRes.getHeader("VENDOR_ID"                ).addValue(map.getString("VENDOR_ID"                      ), "");
	    	    gdRes.getHeader("VENDOR_NM"                ).addValue(map.getString("VENDOR_NM"                      ), "");
	    	    gdRes.getHeader("ORDER_ID"                 ).addValue(map.getString("ORDER_ID"                       ), "");
	    	    gdRes.getHeader("FIRST_ORDER_ID"           ).addValue(map.getString("FIRST_ORDER_ID"                 ), "");
	    	    gdRes.getHeader("PROD_CD"                  ).addValue(map.getString("PROD_CD"                      	 ), "");
	    	    gdRes.getHeader("PROD_NM"                  ).addValue(map.getString("PROD_NM"                      	 ), "");
	    	    gdRes.getHeader("SETL_TYPE_CD"             ).addValue(map.getString("SETL_TYPE_CD"                   ), "");
	    	    gdRes.getHeader("SETL_TYPE_NM"             ).addValue(map.getString("SETL_TYPE_NM"                   ), "");
	    	    gdRes.getHeader("COUPON_TYPE_CD"           ).addValue(map.getString("COUPON_TYPE_CD"                 ), "");
	    	    gdRes.getHeader("COUPON_TYPE_NM"           ).addValue(map.getString("COUPON_TYPE_NM"                 ), "");
	    	    gdRes.getHeader("ORD_STS_CD"               ).addValue(map.getString("ORD_STS_CD"                     ), "");
	    	    gdRes.getHeader("ORD_STS_NM"               ).addValue(map.getString("ORD_STS_NM"                     ), "");
	    	    gdRes.getHeader("SALE_STS_CD"              ).addValue(map.getString("SALE_STS_CD"                    ), "");
	    	    gdRes.getHeader("SALE_STS_NM"              ).addValue(map.getString("SALE_STS_NM"                    ), "");
	    	    gdRes.getHeader("PAY_AMT"                  ).addValue(map.getString("PAY_AMT"                        ), "");
	    	    gdRes.getHeader("BUY_PRC"                  ).addValue(map.getString("BUY_PRC"                        ), "");
	    	    gdRes.getHeader("ORD_DY"                   ).addValue(map.getString("ORD_DY"                      	 ), "");
	    	    gdRes.getHeader("CONF_DATE"                ).addValue(map.getString("CONF_DATE"                      ), "");

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

	@RequestMapping(value = "substn/selectPaySubStnList.do")
	public @ResponseBody Map selectPaySubStnList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		PSCMORD0002VO searchVO = new PSCMORD0002VO();
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

        Map rtnMap = new HashMap<String, Object>();

		try {
			String sYear			= request.getParameter("year");
			String sMonth			= request.getParameter("month");

			String accotYm			= request.getParameter("accotYm");
    		String adjSeq			= request.getParameter("adjSeq");
    		String catCd			= StringUtil.null2str(request.getParameter("catCd"),"");
    		String setlTypeCd		= request.getParameter("setlTypeCd");
    		String ordStsCd			= request.getParameter("ordStsCd");
    		String saleStsCd		= request.getParameter("saleStsCd");
    		String vendorId			= StringUtil.null2str(request.getParameter("vendorId"),"");
    		String prodCd			= request.getParameter("prodCd");


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


			//Map<String,String> paramMap = new HashMap<String,String>();
			DataMap paramMap = new DataMap();
			paramMap.put("accotYm", accotYm);
			paramMap.put("frDt", frDt);
			paramMap.put("toDt", toDt);
			paramMap.put("seq", adjSeq);
			paramMap.put("strCd"	, "981");
			paramMap.put("catCd"	, catCd);
			paramMap.put("setlTypeCd"	, setlTypeCd);
			paramMap.put("ordStsCd"	, ordStsCd);
			paramMap.put("saleStsCd"	, saleStsCd);
			paramMap.put("prodCd"	, prodCd);
			paramMap.put("vendorId"	, vendorId);
			paramMap.put("venCds", epcLoginVO.getVendorId());


			// 데이터 조회
			List<DataMap> resultList = pscmsbt0001Service.selectPaySubStnList(paramMap);

			rtnMap = JsonUtils.convertList2Json((List)resultList, resultList.size(), null);

			rtnMap.put("result", true);
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
		    rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}

	@RequestMapping("substn/selectPaySubStnListExcel.do")
	public void selectPaySubStnListExcel(HttpServletRequest request, HttpServletResponse res) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		PSCMORD0002VO searchVO = new PSCMORD0002VO();
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		try {
			String sYear			= request.getParameter("year");
			String sMonth			= request.getParameter("month");

			String accotYm			= request.getParameter("accotYm");
    		String adjSeq			= request.getParameter("adjSeq");
    		String catCd			= StringUtil.null2str(request.getParameter("catCd"),"");
    		String setlTypeCd		= request.getParameter("setlTypeCd");
    		String ordStsCd			= request.getParameter("ordStsCd");
    		String saleStsCd		= request.getParameter("saleStsCd");
    		String vendorId			= StringUtil.null2str(request.getParameter("vendorId"),"");
    		String prodCd			= request.getParameter("prodCd");


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


			//Map<String,String> paramMap = new HashMap<String,String>();
			DataMap paramMap = new DataMap();
			paramMap.put("accotYm", accotYm);
			paramMap.put("frDt", frDt);
			paramMap.put("toDt", toDt);
			paramMap.put("seq", adjSeq);
			paramMap.put("strCd"	, "981");
			paramMap.put("catCd"	, catCd);
			paramMap.put("setlTypeCd"	, setlTypeCd);
			paramMap.put("ordStsCd"	, ordStsCd);
			paramMap.put("saleStsCd"	, saleStsCd);
			paramMap.put("prodCd"	, prodCd);
			paramMap.put("vendorId"	, vendorId);
			paramMap.put("venCds", epcLoginVO.getVendorId());


			// 데이터 조회
			List<DataMap> resultList = pscmsbt0001Service.selectPaySubStnList(paramMap);

			JsonUtils.IbsExcelDownload((List)resultList, request, res);
		} catch (Exception e) {
			// 작업오류
	        logger.error("error --> " + e.getMessage());
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

