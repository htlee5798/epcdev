package com.lottemart.epc.product.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lcn.module.common.views.AjaxJsonModelHelper;
import lcn.module.framework.property.ConfigurationService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.service.PSCMCOM0004Service;
import com.lottemart.epc.product.model.PSCPPRD0002VO;
import com.lottemart.epc.product.model.PSCPPRD0003VO;
import com.lottemart.epc.product.service.PSCPPRD0002Service;
import com.lottemart.epc.product.service.PSCPPRD0003Service;

/**
 * @Class Name : PSCPPRD0002Controller.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 20. 오후 02:02:02 mjChoi
 *
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
@Controller("pscpprd0002Controller")
public class PSCPPRD0002Controller {

	private static final Logger logger = LoggerFactory.getLogger(PSCPPRD0002Controller.class);

	@Autowired
	private PSCPPRD0002Service pscpprd0002Service;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ConfigurationService config;

	@Autowired
	private PSCPPRD0003Service pscpprd0003Service;

	@Autowired
	private PSCMCOM0004Service pscmcom0004Service;

    /**
     * 상품 정보 팝업 프레임 페이지
     * Desc : 상품 정보 조회용 팝업 프레임셋을 표시
     * @Method Name : selectProductFrame
     * @param request
     * @return
     * @throws Exception
     */
	@RequestMapping("product/selectProductFrame.do")
	public String selectProductFrame(HttpServletRequest request) throws Exception {
		return "product/internet/PSCPPRD0002";
	}

    /**
     * 상품 상세 페이지
     * Desc : 상품의 상세정보를 로딩
     * @Method Name : selectProductInfo
     * @param request
     * @return
     * @throws Exception
     */
	@RequestMapping("product/selectProductInfo.do")
	public String selectProductInfo(HttpServletRequest request) throws Exception {
		String prodCd = request.getParameter("prodCd");
		String presentListBe = "";

		// 생산국가콤보
		List<DataMap> commonCodeList = pscpprd0002Service.selectPrdCommonCodeList();
		request.setAttribute("commonCodeList", commonCodeList);

		// 상세정보
		DataMap prdInfo = pscpprd0002Service.selectPrdInfo(prodCd);

		// 증정품 COMBO내용
		List<DataMap> presentList = pscpprd0002Service.selectPrdPresentList(prodCd);

		if (presentList != null && presentList.size() != 0) {
			presentListBe = "1";
		}

		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		String UNIT_SALE_PRC = prdInfo.getString("UNIT_SALE_PRC"); //표시단위가격:(판매가/표시총수량)*표시기준수량
		String DP_UNIT_NM = prdInfo.getString("DP_UNIT_NM"); //표시단위코드명
		String DP_TOT_QTY = prdInfo.getString("DP_TOT_QTY"); //표시총수량
		String DP_BASE_QTY = prdInfo.getString("DP_BASE_QTY"); //표시기준수량
		String ONLINE_PROD_TYPE_CD = prdInfo.getString("ONLINE_PROD_TYPE_CD");
		// logger.debug("==>" + UNIT_SALE_PRC + "<==");
		// logger.debug("==>" + DP_UNIT_NM    + "<==");
		// logger.debug("==>" + DP_TOT_QTY    + "<==");
		// logger.debug("==>" + DP_BASE_QTY   + "<==");

		// 표시총수량과 표시기준수량이 0인경우
		if (!"0".equals(UNIT_SALE_PRC) && !"0".equals(DP_UNIT_NM) && !"0".equals(DP_TOT_QTY) && !"0".equals(DP_BASE_QTY)) {
			String TOT_UNIT_SALE = UNIT_SALE_PRC + "원" + " : " + DP_BASE_QTY + DP_UNIT_NM + "당";
			prdInfo.put("TOT_UNIT_SALE", TOT_UNIT_SALE);
			// logger.debug("==>" + TOT_UNIT_SALE + "<==");
		}
		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		//카테고리 명 재배열
		Map<String, Object> infoMap = new HashMap<String, Object>();

		String newCategoryId = "";
		String newCategoryNm = "";

		String[] categoryIdArr = prdInfo.getString("CATEGORY_ID").split(",");
		infoMap.put("categoryIdArr", categoryIdArr);

		List<DataMap> categoryNmList = pscmcom0004Service.selectCategoryInList(infoMap);

		for (int i = 0; i < categoryNmList.size(); i++) {
			DataMap map = categoryNmList.get(i);

			newCategoryId += "," + map.getString("CATEGORY_ID");
			newCategoryNm += "," + map.getString("CATEGORY_NM");
		}

		if (newCategoryId.length() > 0) {
			newCategoryId = newCategoryId.substring(1, newCategoryId.length());
			newCategoryNm = newCategoryNm.substring(1, newCategoryNm.length());
		}

		prdInfo.put("CATEGORY_ID", newCategoryId);
		prdInfo.put("CATEGORY_NM", newCategoryNm);
		//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------

		request.setAttribute("resultMap", prdInfo);
		//request.setAttribute("themaDealInfo", themaDealInfo);
		request.setAttribute("presentList", presentList);
		request.setAttribute("presentListBe", presentListBe);

		String rtnUrl = "product/internet/PSCPPRD000201";

		if ("05".equals(ONLINE_PROD_TYPE_CD)) { //딜 상품
			rtnUrl = "product/internet/PSCPPRD000205";
		} else if ("06".equals(ONLINE_PROD_TYPE_CD)) { //추가 구성품
			rtnUrl = "product/internet/PSCPPRD000206";
		}

		return rtnUrl;
	}

    /**
     * 상품 상세 업데이트
     * Desc : 수정 버튼 클릭시 상품의 상세정보를 업데이트 한다
     * @Method Name : updateProductInfo
     * @param request
     * @return
     * @throws Exception
     */
	@RequestMapping("product/updateProductInfo.do")
	public ModelAndView updateProductInfo(HttpServletRequest request) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}

		String message = null;
		String resultStr = null;
		String ONLINE_PROD_TYPE_CD = request.getParameter("ONLINE_PROD_TYPE_CD");

		try {
			message = messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault());

			PSCPPRD0002VO bean = new PSCPPRD0002VO();

			bean.setProdCd((String) request.getParameter("PROD_CD")); //인터넷상품코드
			bean.setProdNm((String) request.getParameter("PROD_NM")); //인터넷상품명칭
			bean.setBrandNm((String) request.getParameter("BRAND_NM")); //브랜드
			bean.setMakerNm((String) request.getParameter("MAKER_NM")); //제조사
			bean.setMainMtrlNm((String) request.getParameter("MAIN_MTRL_NM")); //주재료
			bean.setHomeCd((String) request.getParameter("HOME_CD")); //원산지코드
			bean.setProdStandardNm((String) request.getParameter("PROD_STANDARD_NM")); //중량(규격)
			bean.setProductNationCd((String) request.getParameter("PRODUCT_NATION_CD")); //생산국가
			bean.setVariationYn((String) request.getParameter("VARIATION_YN")); //옵션단품연동여부
			bean.setNfomlVariationDesc((String) request.getParameter("NFOML_VARIATION_DESC")); //옵션값
			bean.setDpTotQty((String) request.getParameter("DP_TOT_QTY"));//MD표시총량
			bean.setDpBaseQty((String) request.getParameter("DP_BASE_QTY")); //상품표시기준
			bean.setDpTotamt((String) request.getParameter("DP_TOTAMT")); //표시총량(해외배송)
			bean.setFrshConvQty((String) request.getParameter("FRSH_CONV_QTY")); //신선상품환산수량

			bean.setProdDivnCd((String) request.getParameter("PROD_DIVN_CD")); //상품구분
			bean.setModId((String) request.getParameter("VENDOR_ID")); //협력사ID-수정자
			bean.setRegId((String) request.getParameter("VENDOR_ID")); //협력사ID-등록자
			bean.setDpUnitCd((String) request.getParameter("DP_UNIT_CD")); //협력사ID-등록자
			bean.setProdOptionDesc((String) request.getParameter("PROD_OPTION_DESC")); // 상품유형상세
			bean.setDispReason((String) StringUtils.defaultIfEmpty(request.getParameter("DISP_REASON"), ""));

			bean.setModelNm((String) StringUtils.defaultIfEmpty(request.getParameter("MODEL_NM"), ""));
			bean.setEcoYn((String) StringUtils.defaultIfEmpty(request.getParameter("ECO_YN"), ""));
			bean.setEcoNm((String) StringUtils.defaultIfEmpty(request.getParameter("ECO_NM"), ""));
			bean.setDlvGa((String) StringUtils.defaultIfEmpty(request.getParameter("DLV_GA"), ""));
			bean.setInsCo((String) StringUtils.defaultIfEmpty(request.getParameter("INS_CO"), ""));
			bean.setDlvDt((String) StringUtils.defaultIfEmpty(request.getParameter("DLV_DT"), ""));

			/* 2016.06.02 추가*/
			bean.setOptnLoadSetQty((String) request.getParameter("OPTN_LOAD_SET_QTY"));
			bean.setOptnLoadContent((String) request.getParameter("OPTN_LOAD_CONTENT"));
			bean.setRservOrdPsbtStartDy((String) request.getParameter("RSERV_ORD_PSBT_START_DY"));
			bean.setRservOrdPsbtEndDy((String) request.getParameter("RSERV_ORD_PSBT_END_DY"));
			bean.setRservProdPickIdctDy((String) request.getParameter("RSERV_PROD_PICK_IDCT_DY"));
			bean.setOnlineProdTypeCd((String) request.getParameter("ONLINE_PROD_TYPE_CD"));
			bean.setHopeDeliPsbtDd((String) request.getParameter("HOPE_DELI_PSBT_DD"));
			/* 2016.06.02 추가*/

			/* 체험형 추가 시작*/
			boolean exprProdYn = false;
			String exprYn = request.getParameter("exprProdYn");
			if ("1".equals(exprYn)) {
				exprProdYn = true;
			}
			bean.setExprTypeCd(exprProdYn);
			bean.setSellerRecomm(request.getParameter("sellerRecomm"));
			/* 체험형 추가 끝*/

			if ("02".equals(request.getParameter("PROD_DIVN_CD"))) {
				bean.setMinOrdPsbtQty((String) request.getParameter("MIN_ORD_PSBT_QTY")); //최소주문가능수량
				bean.setMaxOrdPsbtQty((String) request.getParameter("MAX_ORD_PSBT_QTY")); //최대주문가능수량
				bean.setDispYn((String) request.getParameter("DISP_YN")); //전시여부
				bean.setCmbnMallSellPsbtYn((String) request.getParameter("CMBN_MALL_SELL_PSBT_YN")); //통합몰판매가능여부
				bean.setFedayMallSellPsbtYn((String) request.getParameter("FEDAY_MALL_SELL_PSBT_YN")); //명절몰판매가능여부
				String fedayMallSellPsbtYn = (String) request.getParameter("FEDAY_MALL_SELL_PSBT_YN");
				if ("Y".equals(fedayMallSellPsbtYn)) {
					bean.setFedayMallProdDivnCd("5"); //명절몰상품구분
				} else if ("N".equals(fedayMallSellPsbtYn)) {
					bean.setFedayMallProdDivnCd("0"); //명절몰상품구분
				}
				// bean.setFedayMallProdDivnCd ((String) request.getParameter("FEDAY_MALL_PROD_DIVN_CD")); //명절몰상품구분
				// logger.debug("MIN_ORD_PSBT_QTY       	==>" + (String) request.getParameter("MIN_ORD_PSBT_QTY")      	+ "<==");
				// logger.debug("MAX_ORD_PSBT_QTY       	==>" + (String) request.getParameter("MAX_ORD_PSBT_QTY")      	+ "<==");
				// logger.debug("DISP_YN  					==>" + (String) request.getParameter("DISP_YN") 				+ "<==");
				// logger.debug("CMBN_MALL_SELL_PSBT_YN    ==>" + (String) request.getParameter("CMBN_MALL_SELL_PSBT_YN")  + "<==");
				// logger.debug("FEDAY_MALL_SELL_PSBT_YN   ==>" + (String) request.getParameter("FEDAY_MALL_SELL_PSBT_YN") + "<==");
				// logger.debug("FEDAY_MALL_PROD_DIVN_CD   ==>" + (String) request.getParameter("FEDAY_MALL_PROD_DIVN_CD") + "<==");
			}

			if (!"05".equals(ONLINE_PROD_TYPE_CD) && !"06".equals(ONLINE_PROD_TYPE_CD)) {
				//전상법 등록 체크 '1' 상품, '2' 가등록 상품(상품 전시여부 Y일경우 전상법 체크)
				try {
					if ("Y".equals(bean.getVariationYn()) && "02".equals(bean.getProdDivnCd())) {
						// 데이터 조회
						Map<String, String> paramMap = new HashMap<String, String>();
						paramMap.put("prodCd", bean.getProdCd());

						// 데이터 조회
						List<PSCPPRD0003VO> list = pscpprd0003Service.selectPrdItemOnlineList(paramMap);

						// GridData 셋팅
						PSCPPRD0003VO bean0003;
						int listSize = list.size();

						for (int i = 0; i < listSize; i++) {
							bean0003 = (PSCPPRD0003VO) list.get(i);

							if (bean0003.getRservStkQty() == null || "".equals(bean0003.getRservStkQty()) || bean0003.getOptnDesc() == null || "".equals(bean0003.getOptnDesc())) {
								throw new IllegalArgumentException("단품정보가 제대로 등록되지 않았습니다.");
							}
						}
					}
				} catch (Exception e) {
					return AjaxJsonModelHelper.create("단품정보가 제대로 등록되지 않았습니다.");
				}

				//전상법 등록 체크 '1' 상품, '2' 가등록 상품(상품 전시여부 Y일경우 전상법 체크)
				try {
					if ("Y".equals(bean.getDispYn())) {
						pscpprd0002Service.prodCommerceCheck(bean.getProdCd(), "1");
					}

				} catch (Exception e) {
					return AjaxJsonModelHelper.create("전상법이 제대로 등록되지 않았습니다.");
				}
			}
			// 값이 있을경우 에러
			resultStr = validate(bean);
			// logger.debug("resultStr ==>" + resultStr + "<==");

			if (!"".equals(resultStr)) {
				return AjaxJsonModelHelper.create(resultStr);
			}

			try {
				int resultCnt = pscpprd0002Service.updatePrdInfo(bean);

				if (resultCnt > 0) {
					return AjaxJsonModelHelper.create("");
				} else {
					return AjaxJsonModelHelper.create(message);
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				return AjaxJsonModelHelper.create(message);
			}
		} catch (Exception e) {
			return AjaxJsonModelHelper.create(message);
		}
	}

    /*
     * validate 체크
     */
	public String validate(PSCPPRD0002VO bean) throws Exception {
		if (StringUtils.isEmpty(bean.getProdCd())) {
			return "상품 정보가 없습니다.";
		}
		if (StringUtils.isEmpty(bean.getProdNm())) {
			return "상품명 정보가 없습니다.";
		}

		return "";
	}

    /**
	 * 유튜브 미리보기
	 * @Description : 유튜브 미리보기
	 * @Method Name : selectCallCenterPopupDetail
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/ProductDetailYoutubePopup.do")
	public String ProductDetailYoutubePopup(HttpServletRequest request) throws Exception {
		String prodUrl = (String) request.getParameter("prodUrl");

		request.setAttribute("prodUrl", prodUrl);
		return "product/internet/PSCPPRD000203";
	}

	//20180911 상품키워드 입력 기능 추가
	/**
	 * 상품검색어 확인 여부
	 * @Method Name : selectKeywordYnChk
	 * @param HttpServletRequest
	 * @param String prodCd
	 * @return view 페이지
	 */
	@RequestMapping(value = "/edi/product/selectKeywordYnChk.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Map<String, Object> selectKeywordYnChk(HttpServletRequest request, @RequestBody Map<String, Object> paramMap) throws Exception {
		return pscpprd0002Service.selectKeywordYnChk(paramMap);
	}
	//20180911 상품키워드 입력 기능 추가

}
