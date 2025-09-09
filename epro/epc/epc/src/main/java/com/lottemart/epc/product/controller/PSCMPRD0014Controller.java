/**
 * @prjectName  : lottemart 프로젝트
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */
package com.lottemart.epc.product.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.util.DateUtil;
import lcn.module.framework.property.ConfigurationService;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lottemart.common.file.service.FileMngService;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.service.CommonService;
import com.lottemart.epc.common.util.LoginUtil;
import com.lottemart.epc.product.model.PSCMPRD0014VO;
import com.lottemart.epc.product.service.PSCMPRD0014Service;

@Controller
public class PSCMPRD0014Controller {

	private static final Logger logger = LoggerFactory.getLogger(PSCMPRD0014Controller.class);

	@Autowired
	private ConfigurationService config;

	@Autowired
	private PSCMPRD0014Service pscmprd0014Service;

	@Autowired
	private FileMngService fileMngService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private CommonService commonService;

	@RequestMapping(value = "/product/selectOutOfStockView.do")
	public String selectOutOfStockView(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);
		request.setAttribute("epcLoginVO", epcLoginVO);

		request.setAttribute("strCd", config.getString("online.rep.str.cd"));

		String endDate = DateUtil.getToday("yyyy-MM-dd");
		String startDate = DateUtil.formatDate(DateUtil.addMonth(endDate, -1), "-");
		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);

		// 20180626 - 업체 공통조건 사용시 무료배송 'N' 처리(주문파트 요청건) 및 업체 배송비 관리 수정 (배송비 업체공통조건 문구가 보여서 추가 처리)
		DataMap param = new DataMap(request);
		param.put("vendorList", LoginUtil.getVendorList(epcLoginVO));

		List<DataMap> deliVendorList = pscmprd0014Service.selectDeliVendorInfo(param);

		request.setAttribute("deliVendorList", deliVendorList);

		return "product/PSCMPRD0014";
	}

	@RequestMapping(value = "/product/selectOutOfStockList.do")
	public @ResponseBody Map selectOutOfStockList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);

		Map rtnMap = new HashMap<String, Object>();

		try {
			DataMap paramMap = new DataMap(request);

			String currentPage = paramMap.getString("currentPage");

			// 선택한 vendorId가null 이거나 repVendorId ( 대표협력업체 ) 일때 협력사 전체 검색
			if (paramMap.getString("vendorId").length() == 0 || epcLoginVO.getRepVendorId().equals(paramMap.get("vendorId"))) {
				paramMap.put("vendorId", LoginUtil.getVendorList(epcLoginVO));
			} else {
				String venderId[] = { paramMap.getString("vendorId") };
				paramMap.put("vendorId", venderId);
			}

			// 선택한 vendor가 openappi 업체일 경우 전체 검색
			List<EpcLoginVO> openappiVendorId = commonService.selectOpenappiVendor();
			for (int l = 0; openappiVendorId.size() > l; l++) {
				if (openappiVendorId.get(l).getRepVendorId().equals(request.getParameter("vendorId"))) {
					paramMap.put("vendorId", LoginUtil.getVendorList(epcLoginVO));
				}
			}

			if (!"".equals(paramMap.getString("prodInVal"))) {
				String prodInVal[] = paramMap.getString("prodInVal").split(",");
				paramMap.put("prodInVal", prodInVal);
			}

			paramMap.put("strCd", config.getString("online.rep.str.cd"));

			// 데이터 조회
			List<PSCMPRD0014VO> pscmprd0014List = pscmprd0014Service.selectOutOfStockList(paramMap);

			rtnMap = JsonUtils.convertList2Json((List) pscmprd0014List, -1, currentPage);

			rtnMap.put("result", true);

		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}

	@RequestMapping(value = "/product/selectOutOfStockUploadList.do")
	public void selectOutOfStockUploadList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;

		Map rtnMap = new HashMap<String, Object>();
		List<DataMap> mapList = new ArrayList<DataMap>();
		String prodCd = "";
		boolean expChk = false;

		try {
			String[] colNms = { "PROD_CD" };

			mapList = fileMngService.readUploadExcelFile(mptRequest, colNms, 1);
//			mapList = fileMngService.readUploadNullColExcelFile(mptRequest, colNms, 1,0); 

			for (int i = 0; i < mapList.size(); i++) { 
				DataMap map = mapList.get(i);
				
				prodCd += "," + map.getString("PROD_CD").trim();
			}

			prodCd = prodCd.substring(1, prodCd.length());
		} catch (Exception e) {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();

			out.println("<script type=\'text/javascript'>");
			out.println("alert('업로드 양식이 다릅니다.');");
			out.println("</script>");

			expChk = true;
			// 작업오류
			logger.error("error message --> " ,e);
		} finally {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();

			try {
				out.println("<script type=\'text/javascript'>");
				out.println("parent.document.adminForm.createFile.value = ''");
				if (mapList.size() < 1000 &&  mapList.size() > 0 && prodCd.length() > 0 && !"false".equals(prodCd)) {
					out.println("parent.document.adminForm.prodInVal.value = '" + prodCd + "'");
					out.println("parent.doSearch();");
				}
				else if(mapList.size() >= 1000) {
					out.println("alert('조회 가능한 상품코드를 초과하였습니다.\\n상품코드는 999개 까지만 조회 가능합니다.');");
				}
				else {
					if (!expChk) {
						out.println("alert('업로드 된 데이터가 없습니다.');");
					}
				}
				out.println("	parent.hideLoadingMask();");
				out.println("</script>");
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}

	@RequestMapping("product/updateOutOfStock.do")
	public @ResponseBody JSONObject updateOutOfStock(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);
		request.setAttribute("modId", epcLoginVO.getRepVendorId());

		JSONObject jObj = new JSONObject();
		String message = "";
		int resultCnt = 0;

		try {
			resultCnt = pscmprd0014Service.updateOutOfStock(request);

			// 처리 결과
			if (resultCnt > 0) {
				jObj.put("Code", 1);
				message = messageSource.getMessage("msg.common.success.request", null, Locale.getDefault());
				jObj.put("Message", resultCnt + "건의 " + message);
			} else {
				jObj.put("Code", 0);
				message = messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault());
				jObj.put("Message", message);
			}

		} catch (Exception e) {
			jObj.put("Code", -1);
			jObj.put("Message", e.getMessage());
		}
		return JsonUtils.getResultJson(jObj);
	}

	/**
	 * Desc : 품절관리목록 엑셀다운로드하는 메소드
	 * @Method Name : selectPriceChangeListExcel
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping(value = "/product/selectOutOfStockListExcel.do")
	public String selectOutOfStockListExcel(HttpServletRequest request, @ModelAttribute("searchVO") PSCMPRD0014VO searchVO, ModelMap model) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		// 선택한 vendorId가null 이거나 repVendorId ( 대표협력업체 ) 일때 협력사 전체 검색
		if (searchVO.getVendorId() == null || epcLoginVO.getRepVendorId().equals(searchVO.getVendorId()) || "".equals(searchVO.getVendorId())) {
			searchVO.setVendorList(LoginUtil.getVendorList(epcLoginVO));
		} else {
			ArrayList<String> vendor = new ArrayList<String>();
			vendor.add(searchVO.getVendorId());
			searchVO.setVendorList(vendor);
		}

		// 선택한 vendor가 openappi 업체일 경우 전체 검색
		List<EpcLoginVO> openappiVendorId = commonService.selectOpenappiVendor();
		for (int l = 0; openappiVendorId.size() > l; l++) {
			if (openappiVendorId.get(l).getRepVendorId().equals(searchVO.getVendorId().toString().replace("[", "").replace("]", "").trim())) {
				searchVO.setVendorList(LoginUtil.getVendorList(epcLoginVO));
			}
		}

		searchVO.setStrCd(config.getString("online.rep.str.cd"));

		// 데이터 조회
		List<PSCMPRD0014VO> list = pscmprd0014Service.selectOutOfStockListExcel(searchVO);
		model.addAttribute("list", list);
		return "product/PSCMPRD001401";
	}

}
