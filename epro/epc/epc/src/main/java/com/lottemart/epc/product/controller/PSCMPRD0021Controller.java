package com.lottemart.epc.product.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lcn.module.framework.property.ConfigurationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lcnjf.util.StringUtil;
import com.lottemart.common.exception.AlertException;
import com.lottemart.common.exception.AppException;
import com.lottemart.common.exception.TopLevelException;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.service.CommonService;
import com.lottemart.epc.product.service.PSCMPRD0021Service;

/**
 *
 * @author hjKim
 * @Description : 상품관리 - 대표상품코드관리
 * @Class : com.lottemart.epc.product.controller
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011.12.16  hjKim
 * @version :
 * </pre>
 */
@Controller("pscmprd0021Controller")
public class PSCMPRD0021Controller {

	private static final Logger logger = LoggerFactory.getLogger(PSCMPRD0021Controller.class);

	@Autowired
	private PSCMPRD0021Service pscmprd0021Service;

	@Autowired
	private ConfigurationService config;

	@Autowired
	private CommonService commonService;

	/**
	 * 대표상품코드관리 디폴트 페이지
	 * @see repProdCdForm
	 * @Locaton    : com.lottemart.epc.product.controller
	 * @MethodName  : repProdCdForm
	 * @author     : hjKim
	 * @Description : 메뉴 클릭시 뜨는 디폴트 페이지
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/repProduct.do")
	public String repProdCdForm(HttpServletRequest request) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		//if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
		//	logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		//}

		// 협력업체콤보
		request.setAttribute("epcLoginVO", epcLoginVO);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
		Calendar calVal = Calendar.getInstance();
		String endDate = dateFormat.format(calVal.getTime());

		// 시작일자를 현재날짜 한달전으로 셋팅
		calVal.add(Calendar.DAY_OF_MONTH, -31);
		String startDate = dateFormat.format(calVal.getTime());

		// 검색기간 셋팅
		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);

		return "product/PSCMPRD0021";
	}

	/**
	 * 디폴트 페이지에서 조회버튼 클릭시 대표상품코드 목록 조회, WISEGRID로 리턴
	 * @see selectRepProdCdList
	 * @Locaton    : com.lottemart.epc.product.controller
	 * @MethodName  : selectRepProdCdList
	 * @author     : hjKim
	 * @Description : 대표상품코드 목록 조회
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/selectRepProdCdList.do")
	public @ResponseBody Map selectRepProdCdList(HttpServletRequest request) throws Exception {

		Map rtnMap = new HashMap<String, Object>();

		try {
			DataMap param = new DataMap(request);

			String sessionKey = config.getString("lottemart.epc.session.key");
			EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

			// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
			//if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
			//	logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
			//}

			// 요청객체 획득
			ArrayList<String> aryList = null;

			String rowPerPage = param.getString("rowsPerPage");
			String currentPage = param.getString("currentPage");
			int rowPage = Integer.parseInt(rowPerPage);
			int currPage = Integer.parseInt(currentPage);

			///logger.debug("123 = " + param.getString("startDate"));
			////logger.debug("123 = " + param.getString("endDate"));
			// 페이징 관련 변수
			String rowsPerPage = StringUtil.null2str(rowPerPage, config.getString("count.row.per.page"));
			int startRow = ((Integer.parseInt(currentPage) - 1) * Integer.parseInt(rowsPerPage)) + 1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) - 1;
			param.put("startRow", Integer.toString(startRow));
			param.put("endRow", Integer.toString(endRow));
			param.put("startDate", param.getString("startDate").replaceAll("-", ""));
			param.put("endDate", param.getString("endDate").replaceAll("-", ""));

			// 선택한 vendorId가null 이거나 repVendorId ( 대표협력업체 ) 일때 협력사 전체 검색
			if (epcLoginVO != null && (param.getString("vendorId") == null || epcLoginVO.getRepVendorId().equals(param.getString("vendorId")) || "".equals(param.getString("vendorId")))) {
				aryList = new ArrayList<String>();

				for (int i = 0; i < epcLoginVO.getVendorId().length; i++) {
					aryList.add(epcLoginVO.getVendorId()[i]);
				}

				param.put("vendorId", aryList);
			} else {
				aryList = new ArrayList<String>();
				aryList.add(param.getString("vendorId"));
				param.put("vendorId", aryList);
			}

			// 선택한 vendor가 openappi 업체일 경우 전체 검색
			List<EpcLoginVO> openappiVendorId = commonService.selectOpenappiVendor();
			for (int l = 0; openappiVendorId.size() > l; l++) {
				if (openappiVendorId.get(l).getRepVendorId().equals(param.getString("vendorId").replace("[", "").replace("]", "").trim())) {
					aryList = new ArrayList<String>();

					for (int a = 0; a < epcLoginVO.getVendorId().length; a++) {
						aryList.add(epcLoginVO.getVendorId()[a]);
						param.put("vendorId", aryList);
					}
				}
			}

			// 데이터 조회
			List<DataMap> resultList = pscmprd0021Service.selectRepProdCdList(param);

			int size = resultList.size();
			int totalCnt = 0;
			if (size > 0) {
				totalCnt = resultList.get(0).getInt("CNT");
			}

			rtnMap = JsonUtils.convertList2Json((List) resultList, totalCnt, currentPage);

			// 처리성공
			rtnMap.put("result", true);

		} catch (AppException | TopLevelException | AlertException e) {
			logger.error("error getMessage --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		} catch (Exception e) {
			logger.error("error --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("Message", "조회시 오류가 발생하였습니다.\n"+e.getClass().getName());
			rtnMap.put("errMsg", "조회시 오류가 발생하였습니다.");
		}

		return rtnMap;
	}
}
