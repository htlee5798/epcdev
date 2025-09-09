package com.lottemart.epc.product.controller;


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xlib.cmc.GridData;
import xlib.cmc.OperateGridData;

import com.lcnjf.util.StringUtil;
import com.lottemart.common.exception.AlertException;
import com.lottemart.common.login.model.LoginSession;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.product.model.PSCPPRD0018VO;
import com.lottemart.epc.product.service.PSCPPRD0018Service;

/**
 *
 * @Class Name : PSCPPRD0018Controller
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자	   수정내용
 *  -------    --------    ---------------------------
 * 2011. 12. 27. 오후 03:03:03 mjChoi
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Controller("pscpprd0018Controller")
public class PSCPPRD0018Controller {
private static final Logger logger = LoggerFactory.getLogger(PSCPPRD0018Controller.class);

	@Autowired
	private PSCPPRD0018Service pscpprd0018Service;
	@Autowired
	private MessageSource messageSource;

	/**
	 * 가격정보 폼 페이지
	 * @Description : 가격정보 목록 초기페이지 로딩
	 * @Method Name : prdPriceForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/selectproductPriceForm.do")
	public String prdPriceForm(HttpServletRequest request) throws Exception {

		// 점포 COMBO 내용
		List<DataMap> storeList = pscpprd0018Service.selectPrdStoreList("");

		request.setAttribute("storeList", storeList);

		// 공통 COMBO 내용
		String prodCd = request.getParameter("prodCd");
		List<DataMap> commonCodeList = pscpprd0018Service.selectPrdCommonCodeList(prodCd);

		request.setAttribute("commonCodeList", commonCodeList);

		// 상품종류
		DataMap dm = (DataMap)pscpprd0018Service.selectPrdDivnType(prodCd);
		String prdDivnType = dm.getString("PROD_DIVN_CD");
		String mallDivnCd  = dm.getString("MALL_DIVN_CD");

		request.setAttribute("prdDivnType", prdDivnType);
		request.setAttribute("mallDivnCd", mallDivnCd);

		return "product/internet/PSCPPRD001801";
	}

	/**
	 * 가격정보 상세 폼 페이지
	 * @Description : 가격정보 상세 초기페이지 로딩 (팝업)
	 * @Method Name : prdPriceDetailForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/prdPriceDetailForm.do")
	public String prdPriceDetailForm(HttpServletRequest request) throws Exception {
		return "product/internet/PSCPPRD001802";
	}

	/**
	 * 가격정보 목록
	 * @Description : 조회버튼 클릭시 가격정보 목록을 얻어서 그리드에 리턴
	 * @Method Name : prdPriceSearch
	 * @param request
	 * @return
	 * @throws Exception

	@RequestMapping("product/prdPriceSearch_org.do")
	public String prdPriceSearch_org(HttpServletRequest request) throws Exception {

		GridData gdRes = new GridData();

		try {
			// 요청객체
			String wiseGridData = request.getParameter("WISEGRID_DATA");
			GridData  gdReq = OperateGridData.parse(wiseGridData);

			// 결과객체
			gdRes = OperateGridData.cloneResponseGridData(gdReq);
			gdRes.addParam("mode", gdReq.getParam("mode"));
			gdRes.addParam("strGubun", gdReq.getParam("strGubun"));

			// 파라미터
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("prodCd", gdReq.getParam("prodCd"));
			paramMap.put("strCd", gdReq.getParam("strCd"));
			paramMap.put("strGubun", gdReq.getParam("strGubun"));

			// 데이터 조회
//			logger.debug("[ selectPrdPriceList ]");
			List<PSCPPRD0018VO> list = pscpprd0018Service.selectPrdPriceList(paramMap);

			// 데이터 없음
			if(list == null || list.size() == 0) {
//				logger.debug("[ no row ]");
				gdRes.setStatus("false");
				request.setAttribute("wizeGridResult", gdRes);
				return "common/wiseGridResult";
			}

			// GridData 셋팅
//			logger.debug("[ data set : "+list.size()+" rows ]");

			PSCPPRD0018VO bean;
			int listSize = list.size();
			int avilStockQty = 0;

			for(int i = 0; i < listSize; i++) {
				bean = (PSCPPRD0018VO)list.get(i);

				gdRes.getHeader("strCd").addValue(bean.getStrCd(),"");
				gdRes.getHeader("strNm").addValue(bean.getStrNm(),"");
				gdRes.getHeader("itemCd").addValue(bean.getItemCd(),"");
				gdRes.getHeader("purStopDivnCd").addValue(bean.getPurStopDivnCd(),"");
				gdRes.getHeader("onlineSoutYn").addValue(bean.getOnlineSoutYn(),"");
				gdRes.getHeader("mdSoutYn").addValue(bean.getMdSoutYn(),"");
				gdRes.getHeader("dispYn").addValue(bean.getDispYn(),"");
				gdRes.getHeader("stkMgrYn").addValue(bean.getStkMgrYn(),"");
				gdRes.getHeader("trdTypeDivnCd").addValue(bean.getTrdTypeDivnCd(),"");
				gdRes.getHeader("forgnDeliYn").addValue(bean.getForgnDeliYn(),"");
				gdRes.getHeader("strPikupYn").addValue(bean.getStrPikupYn(),"");
				gdRes.getHeader("deliOptnCd").addSelectedHiddenValue(bean.getDeliOptnCd());
				gdRes.getHeader("dispApplyBaseDd").addValue(bean.getDispApplyBaseDd(),"");
				gdRes.getHeader("mdRecentSellDy").addValue(bean.getMdRecentSellDy(),"");
				gdRes.getHeader("buyPrc").addValue(bean.getBuyPrc(),"");
				gdRes.getHeader("sellPrc").addValue(bean.getSellPrc(),"");
				gdRes.getHeader("currSellPrc").addValue(bean.getCurrSellPrc(),"");

				avilStockQty = Integer.parseInt(bean.getAvilStockQty()==null?"0":bean.getAvilStockQty());

				if(avilStockQty < 0) { //가능재고 - 로 나올 경우 0 으로 표기 (- 로 나올 경우 상태값임)
					gdRes.getHeader("avilStockQty").addValue("0","");
				}
				else {
					gdRes.getHeader("avilStockQty").addValue(bean.getAvilStockQty(),"");
				}

				gdRes.getHeader("useYn").addValue("사용","");//--임시..


				gdRes.getHeader("prodCd").addValue(gdReq.getParam("prodCd"),"");

				// tmp
				gdRes.getHeader("num").addValue(Integer.toString(i+1),"");
				gdRes.getHeader("CHK").addValue("0","");
			}

			// 성공
//			logger.debug("[ success ]");
			gdRes.setStatus("true");

		} catch (Exception e) {
			// 오류
//			logger.debug("[ Exception ]");
			gdRes.setMessage(e.getMessage());
			gdRes.setStatus("false");
			logger.debug("", e);
		}

		request.setAttribute("wizeGridResult", gdRes);

//		logger.debug("[ wiseGridResult ]");
		return "common/wiseGridResult";
	}*/

	/**
	 * 가격정보 목록
	 * @Description : 조회버튼 클릭시 가격정보 목록을 얻어서 그리드에 리턴
	 * @Method Name : prdPriceSearch
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/prdPriceSearch.do")
	public @ResponseBody Map prdPriceSearch(HttpServletRequest request) throws Exception {

        Map rtnMap = new HashMap<String, Object>();

		try {

			DataMap param = new DataMap(request);

			// 데이터 조회
			List<PSCPPRD0018VO> list = pscpprd0018Service.selectPrdPriceList(param);

			PSCPPRD0018VO bean;
			int listSize = list.size();
			int avilStockQty = 0;
			logger.debug("listSize ==> " + listSize);

			for (int i = 0; i < listSize; i++) {
				bean = (PSCPPRD0018VO)list.get(i);
				logger.debug("i ==> " + i);

				// 2012-06-13 임재유 판매가능여부 수정
				if (bean.getSellPsbtYn() == null) {
					bean.setSellPsbtYn(" ");
				}

				if (bean.getAvilStockQty() == null) {
					bean.setAvilStockQty("0");
				} else {
					avilStockQty = Integer.parseInt(bean.getAvilStockQty());
					if (avilStockQty < 0) {
						bean.setAvilStockQty("0");
					}
				}

				// tmp
				bean.setNum(Integer.toString(i+1));
			}

			rtnMap = JsonUtils.convertList2Json((List)list, null, null);

			// 성공
	        rtnMap.put("result", true);

		} catch (Exception e) {
			// 오류
	        logger.error("error message --> " + e.getMessage());
	        rtnMap.put("result", false);
	        rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}

	/**
	 * 가격정보 수정 처리
	 * @Description : 저장 버튼 클릭시 해당 처리를 완료한후에 메세지를 띄우고 그리드를 리로드한다.
	 * @Method Name : prdPriceListUpdate
	 * @param request
	 * @return
	 * @throws Exception
	@RequestMapping("product/prdPriceListUpdate.do")
	public void prdPriceListUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridData gdRes = new GridData();

		// Encode Type을 UTF-8로 변환한다.
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String message = "";

		try {
			// 요청객체
			String wiseGridData = request.getParameter("WISEGRID_DATA");
			GridData gdReq = OperateGridData.parse(wiseGridData);

			// 모드
			gdRes.addParam("mode", gdReq.getParam("mode"));

			// 값 셋팅
			List<PSCPPRD0018VO> beanList = new ArrayList<PSCPPRD0018VO>();
			PSCPPRD0018VO bean;
			String resultStr;

			int rowCount = gdReq.getHeader("num").getRowCount();
//			logger.debug("[ updatePrdPriceList set ]");
//			logger.debug("[ mode : "+gdReq.getParam("mode")+" ]");
//			logger.debug("[ rowCount : "+rowCount +" ]");

			for (int i = 0; i < rowCount; i++) {
//				logger.debug("[ set i : "+i+" ]");
				bean = new PSCPPRD0018VO();

				bean.setStrCd(gdReq.getHeader("strCd").getValue(i));
				bean.setStrNm(gdReq.getHeader("strNm").getValue(i));
				bean.setItemCd(gdReq.getHeader("itemCd").getValue(i));
				bean.setPurStopDivnCd(gdReq.getHeader("purStopDivnCd").getValue(i));
				bean.setOnlineSoutYn(gdReq.getHeader("onlineSoutYn").getValue(i));
				bean.setMdSoutYn(gdReq.getHeader("mdSoutYn").getValue(i));
				bean.setDispYn(gdReq.getHeader("dispYn").getValue(i));
				bean.setStkMgrYn(gdReq.getHeader("stkMgrYn").getValue(i));
				bean.setTrdTypeDivnCd(gdReq.getHeader("trdTypeDivnCd").getValue(i));
				bean.setForgnDeliYn(gdReq.getHeader("forgnDeliYn").getValue(i));
				bean.setStrPikupYn(gdReq.getHeader("strPikupYn").getValue(i));
				bean.setDeliOptnCd(gdReq.getHeader("deliOptnCd").getComboHiddenValues()[gdReq.getHeader("deliOptnCd").getSelectedIndex(i)]);
				bean.setDispApplyBaseDd(gdReq.getHeader("dispApplyBaseDd").getValue(i));
				bean.setMdRecentSellDy(gdReq.getHeader("mdRecentSellDy").getValue(i).replaceAll("-", ""));
				bean.setBuyPrc(gdReq.getHeader("buyPrc").getValue(i));
				bean.setSellPrc(gdReq.getHeader("sellPrc").getValue(i));
				bean.setCurrSellPrc(gdReq.getHeader("currSellPrc").getValue(i));
				bean.setAvilStockQty(gdReq.getHeader("avilStockQty").getValue(i));

//				logger.debug("[ prdDivnType : "+gdReq.getParam("prdDivnType")+" ]");//상품종류
				//소셜상품인 경우에 판매가를 수정할 수 있다
				bean.setPrdDivnType(gdReq.getParam("prdDivnType"));
				bean.setRegId(gdReq.getParam("vendorId"));

				bean.setProdCd(gdReq.getHeader("prodCd").getValue(i));

				// 값 채크
//				logger.debug("[ validate check ]");
				resultStr = validate(bean);//-- 각기 채크..
				if(!"".equals(resultStr)){
//					logger.debug("[ validate check fail : "+resultStr+" ]");
					message  = resultStr;
					gdRes.addParam("saveData",message);
					gdRes.setStatus("true");
				}

				beanList.add(bean);
			}
			int resultCnt = 0;

			// 처리
//			logger.debug("[ updatePrdPriceList begin ]");
			resultCnt = pscpprd0018Service.updatePrdPriceList(beanList, (String) gdReq.getParam("mode"));

			// 처리 결과
			if(resultCnt > 0){
//				logger.debug("[ success ]");
				message  = messageSource.getMessage("msg.common.success.request", null, Locale.getDefault());
				gdRes.addParam("saveData",resultCnt + "건의 "+message);
				gdRes.setStatus("true");
			} else {
//				logger.debug("[ fail ]");
				message  = messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault());
				gdRes.addParam("saveData",message);
				gdRes.setStatus("true");//--true
			}

		} catch (Exception e) {
			// 오류
//			logger.debug("[ Exception ]");
			gdRes.setMessage(e.getMessage());
			gdRes.setStatus("false");
		}
		finally {
			try {
				// 자료구조를 전문으로 변경해 Write한다.
				OperateGridData.write(gdRes, out);
			} catch (Exception e) {
	        logger.error("error message --> " + e.getMessage());
			}
		}
	}*/

	/**
	 * 가격정보 수정 처리 for IBSheet
	 * @Description : 저장 버튼 클릭시 해당 처리를 완료한후에 메세지를 띄우고 그리드를 리로드한다.
	 * @Method Name : prdPriceListUpdate
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/prdPriceListUpdate.do")
	public @ResponseBody JSONObject prdPriceListUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {

        JSONObject jObj = new JSONObject();
		String message = "";

		try {

			DataMap param = new DataMap(request);

			List<PSCPPRD0018VO> beanList = new ArrayList<PSCPPRD0018VO>();

			String prodCd = param.getString("mProdCd");
			String[] strCds	= request.getParameterValues("strCd");
			
			String[] chkDispApplyBaseDd = request.getParameterValues("dispApplyBaseDd");
			for(String temp : chkDispApplyBaseDd) {
				if (StringUtil.getByteLength(temp) > 3) {
					throw new AlertException("전시적용일은 3byte 이하로 입력해주세요.");
				}
			}
            for (int i=0; i<strCds.length; i++) {

            	PSCPPRD0018VO bean = new PSCPPRD0018VO();

				bean.setStrCd(request.getParameterValues("strCd")[i]);
				bean.setStrNm(request.getParameterValues("strNm")[i]);
				bean.setItemCd(request.getParameterValues("itemCd")[i]);
				bean.setPurStopDivnCd(request.getParameterValues("purStopDivnCd")[i]);
				bean.setOnlineSoutYn(request.getParameterValues("onlineSoutYn")[i]);
				bean.setMdSoutYn(request.getParameterValues("mdSoutYn")[i]);
				bean.setDispYn(request.getParameterValues("dispYn")[i]);
				bean.setStkMgrYn(request.getParameterValues("stkMgrYn")[i]);
				bean.setTrdTypeDivnCd(request.getParameterValues("trdTypeDivnCd")[i]);
				bean.setForgnDeliYn(request.getParameterValues("forgnDeliYn")[i]);
				bean.setStrPikupYn(request.getParameterValues("strPikupYn")[i]);
				bean.setDeliOptnCd(request.getParameterValues("deliOptnCd")[i]);
				bean.setDispApplyBaseDd(request.getParameterValues("dispApplyBaseDd")[i]);
				bean.setMdRecentSellDy(request.getParameterValues("mdRecentSellDy")[i].replaceAll("-", ""));
				bean.setBuyPrc(request.getParameterValues("buyPrc")[i]);
				bean.setSellPrc(request.getParameterValues("sellPrc")[i]);
				bean.setCurrSellPrc(request.getParameterValues("currSellPrc")[i]);
				bean.setAvilStockQty(request.getParameterValues("avilStockQty")[i]);
				bean.setStockQty(request.getParameterValues("stockQty")[i]);

				// 소셜상품인 경우에 판매가를 수정할 수 있다
				bean.setPrdDivnType(param.getString("prdDivnType"));
				bean.setRegId(param.getString("vendorId"));

				bean.setProdCd(prodCd);

				// 값 체크
				String resultStr = validate(bean);
				if (!"".equals(resultStr)) {
					message  = resultStr;
					jObj.put("Code", 0);
					jObj.put("Message", message);
					return jObj;
				}

				beanList.add(bean);
			}
			int resultCnt = 0;

			// 처리
			resultCnt = pscpprd0018Service.updatePrdPriceList(beanList, param.getString("mode"));

			// 처리 결과
			if (resultCnt > 0) {
				message  = messageSource.getMessage("msg.common.success.request", null, Locale.getDefault());
				jObj.put("Code", 1);
				jObj.put("Message", resultCnt + "건의 " + message);
			} else {
				message  = messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault());
				jObj.put("Code", 0);
				jObj.put("Message", message);
			}

		} catch (Exception e) {
			// 오류
			jObj.put("Code", -1);
			jObj.put("Message", e.getMessage());
		}

		return JsonUtils.getResultJson(jObj);
	}

	/**
	 * 가격정보 상세 목록
	 * @Description : 가격정보 상세 목록을 로딩하여 그리드에 리턴
	 * @Method Name : prdPriceDetailSearch
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/prdPriceDetailSearch.do")
	public String prdPriceDetailSearch(HttpServletRequest request) throws Exception {

		GridData gdRes = new GridData();

		try {
			// 요청객체
			String wiseGridData = request.getParameter("WISEGRID_DATA");
			GridData  gdReq = OperateGridData.parse(wiseGridData);

			// 결과객체
			gdRes = OperateGridData.cloneResponseGridData(gdReq);
			gdRes.addParam("mode", gdReq.getParam("mode"));

			// 파라미터
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("prodCd", gdReq.getParam("prodCd"));
			paramMap.put("itemCd", gdReq.getParam("itemCd"));
			paramMap.put("strCd", gdReq.getParam("strCd"));
			// 데이터 조회
//			logger.debug("[ selectPrdPriceDetailList ]");
			List<PSCPPRD0018VO> list = pscpprd0018Service.selectPrdPriceDetailList(paramMap);

			// 데이터 없음
			if(list == null || list.size() == 0) {
//				logger.debug("[ no row ]");
				gdRes.setStatus("false");
				request.setAttribute("wizeGridResult", gdRes);
				return "common/wiseGridResult";
			}

			// GridData 셋팅
//			logger.debug("[ data set ]");

			PSCPPRD0018VO bean;
			int listSize = list.size();

			for(int i = 0; i < listSize; i++) {
				bean = (PSCPPRD0018VO)list.get(i);

				gdRes.getHeader("num").addValue(bean.getNum(), "");
				gdRes.getHeader("applyStartDate").addValue(bean.getApplyStartDate(),"");
				gdRes.getHeader("endDate").addValue(bean.getEndDate(),"");
				gdRes.getHeader("buyPrc").addValue(bean.getBuyPrc(),"");
				gdRes.getHeader("sellPrc").addValue(bean.getSellPrc(),"");
				gdRes.getHeader("currSellPrc").addValue(bean.getCurrSellPrc(),"");
			}

			// 성공
//			logger.debug("[ success ]");
			gdRes.setStatus("true");

		} catch (Exception e) {
			// 오류
//			logger.debug("[ Exception ]");
			gdRes.setMessage(e.getMessage());
			gdRes.setStatus("false");
			logger.debug("", e);
		}

		request.setAttribute("wizeGridResult", gdRes);

//		logger.debug("[ wiseGridResult ]");
		return "common/wiseGridResult";
	}

	/*
	 * validate 체크
	 */
	public String validate(PSCPPRD0018VO bean) throws Exception {
		//-- 값 체크
		if(StringUtils.isEmpty(bean.getProdCd())) {
			return "상품 코드가 없습니다.";
		}

		return "";
	}

}