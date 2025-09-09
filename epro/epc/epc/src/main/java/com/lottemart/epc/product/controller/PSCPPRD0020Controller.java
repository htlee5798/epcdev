package com.lottemart.epc.product.controller;

import java.io.PrintWriter;
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
import com.lottemart.common.login.model.LoginSession;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.product.model.PSCPPRD0005VO;
import com.lottemart.epc.product.model.PSCPPRD0020VO;
import com.lottemart.epc.product.service.PSCPPRD0005Service;
import com.lottemart.epc.product.service.PSCPPRD0020Service;


@Controller("PSCPPRD0020Controller")
public class PSCPPRD0020Controller {

	private static final Logger logger = LoggerFactory.getLogger(PSCPPRD0020Controller.class);

	@Autowired
	private PSCPPRD0020Service pscpprd0020Service;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PSCPPRD0005Service pscpprd0005Service;

	/**
	 * 추천상품 폼 페이지
	 * @Description : 상품 추천상품 목록 초기페이지 로딩
	 * @Method Name : prdCommerceForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/selectProductCommerceForm.do")
	public String prdCommerceForm(HttpServletRequest request) throws Exception {
		//return "product/internet/PBOMPRD003901";  //수정필요
		return "product/internet/PSCPPRD002001"; //수정필요
	}

	@RequestMapping("product/selectProductCommerceSearch_org.do")
	public String prdCommerceSearch_org(HttpServletRequest request) throws Exception {

		GridData gdRes = new GridData();

		try {
			// 요청객체
			String wiseGridData = request.getParameter("WISEGRID_DATA");
			GridData gdReq = OperateGridData.parse(wiseGridData);

			// 결과객체
			gdRes = OperateGridData.cloneResponseGridData(gdReq);
			gdRes.addParam("mode", gdReq.getParam("mode"));

			String prodCd = gdReq.getParam("prodCd");
			String newProdCd = gdReq.getParam("newProdCd");

			// 파라미터
			Map<String, String> paramMap = new HashMap<String, String>();

			//관련정보 검색
			DataMap dm = new DataMap();

			if (newProdCd != null && !"".equals(newProdCd)) {
				paramMap.put("prodCd", newProdCd);
				dm = pscpprd0020Service.selectPrdCommerceTemp(paramMap); //온라인 상품 가등록 탭 영역
			} else {
				paramMap.put("prodCd", prodCd);
				dm = pscpprd0020Service.selectPrdCommerceCnt(paramMap); //인터넷 상품관리 탭 영역
			}

			List<PSCPPRD0020VO> list = null;
			List<DataMap> selectlist = null;
			String tempSelect = "";
			String tempGrpCd = "";

			//select 박스 생성
			paramMap.put("catCd", dm.getString("CAT_CD"));
			selectlist = pscpprd0020Service.selectPrdCommerceCatCdList(paramMap);

			if (selectlist != null && selectlist.size() > 0) {

				int selectSize = selectlist.size();
				tempSelect += "<option value=''>전체</option>";

				for (int i = 0; i < selectSize; i++) {
					DataMap tempMap = (DataMap) selectlist.get(i);
					if (selectSize == 1) {
						tempSelect += "<option value='" + tempMap.getString("INFOGRPCD") + "' selected='selected'>" + tempMap.getString("INFOGRPNM") + "</option>";
					} else {
						tempSelect += "<option value='" + tempMap.getString("INFOGRPCD") + "' >" + tempMap.getString("INFOGRPNM") + "</option>";
					}
				}
			}

			//1:n 관계인지 체크 - select 카테고리 정보 가져오기
			if (dm.getInt("CAT_CNT") > 1) {
				//등록된 데이터가 있을경우
				if (dm.getInt("VAL_CNT") > 0) {
					paramMap.put("infoGrpCd", dm.getString("INFO_GRP_CD"));
					tempGrpCd = dm.getString("INFO_GRP_CD");
					list = pscpprd0020Service.selectPrdCommerceList(paramMap);
				}

			} else {
				paramMap.put("infoGrpCd", dm.getString("INFO_GRP_CD"));
				list = pscpprd0020Service.selectPrdCommerceList(paramMap);
			}

			gdRes.addParam("optionValue", tempSelect);
			gdRes.addParam("infoGrpCd", tempGrpCd);

			// 데이터 없음
			if (list == null || list.size() == 0) {
				gdRes.setStatus("true");
				request.setAttribute("wizeGridResult", gdRes);
				return "common/wiseGridResult";
			}

			PSCPPRD0020VO bean;
			int listSize = list.size();

			for (int i = 0; i < listSize; i++) {
				bean = (PSCPPRD0020VO) list.get(i);
				gdRes.getHeader("infoGrpCd").addValue(bean.getInfoGrpCd(), "");
				gdRes.getHeader("infoColCd").addValue(bean.getInfoColCd(), "");
				gdRes.getHeader("infoColNm").addValue(bean.getInfoColNm(), "");
				gdRes.getHeader("infoColDesc").addValue(bean.getInfoColDesc(), "");
				gdRes.getHeader("colVal").addValue(StringUtils.defaultIfEmpty(bean.getColVal(), ""), "");
				gdRes.getHeader("newProdCd").addValue(StringUtils.defaultIfEmpty(bean.getNewProdCd(), ""), "");
				gdRes.getHeader("chk").addValue("1", "");
			}

			// 성공
			gdRes.setStatus("true");

		} catch (Exception e) {
			// 오류
			// logger.debug("[ Exception ]");
			gdRes.setMessage(e.getMessage());
			gdRes.setStatus("false");
			logger.debug("", e);
		}

		request.setAttribute("wizeGridResult", gdRes);

		// logger.debug("[ wiseGridResult ]");
		return "common/wiseGridResult";
	}

	/**
	 * 전상법 목록 for IBSheet
	 * Desc : 인터넷상품관리, 온라인상품가등록관리, 온오프상품속성관리, 수산소분상품관리에서 공통으로 사용
	 * @Method Name : selectProductCommerceSearch
	 * @param request
	 * @return
	 * @throws Exception
	 * @exception Exception
	 */
	@RequestMapping("product/selectProductCommerceSearch.do")
	public @ResponseBody Map selectProductCommerceSearch(HttpServletRequest request) throws Exception {

		Map rtnMap = new HashMap<String, Object>();

		try {
			DataMap param = new DataMap(request);
			Map extMap = new HashMap();

			String pgmId = param.getString("pgmId");

			//관련정보 검색
			DataMap dm = new DataMap();
			logger.debug("pgmId ==> " + pgmId);
			if (pgmId != null && !"".equals(pgmId)) { // pgmId가 있는 경우(온라인상품가등록관리)
				param.put("prodCd", pgmId);
				dm = pscpprd0020Service.selectPrdCommerceTemp((Map) param); // 온라인 상품 가등록 탭 영역
			} else { // prodCd가 있는 경우(인터넷상품관리, 수산소분상품관리)
				DataMap updateInfo = pscpprd0020Service.prodCommerceUpdateInfo((Map) param);
				if (updateInfo != null) {
					extMap.put("updId", updateInfo.getString("CNFM_ID"));
					extMap.put("updDate", updateInfo.getString("CNFM_DATE"));
				} else {
					extMap.put("updId", "");
					extMap.put("updDate", "");
				}

				dm = pscpprd0020Service.selectPrdCommerceCnt((Map) param); // 인터넷 상품관리 탭 영역
			}

			List<PSCPPRD0020VO> list = null;
			List<DataMap> selectlist = null;
			String tempSelect = "";
			String tempGrpCd = "";

			// select 박스 생성
			param.put("catCd", dm.getString("CAT_CD"));
			selectlist = pscpprd0020Service.selectPrdCommerceCatCdList((Map) param);

			if (selectlist != null && selectlist.size() > 0) {

				int selectSize = selectlist.size();
				// tempSelect += "<option value=''>전체</option>"; // 전체는 삭제함 2016.01.25
				for (int i = 0; i < selectSize; i++) {
					DataMap tempMap = (DataMap) selectlist.get(i);
					if (selectSize == 1) {
						tempSelect += "<option value='" + tempMap.getString("INFOGRPCD") + "' selected='selected'>" + tempMap.getString("INFOGRPNM") + "</option>";
					} else {
						tempSelect += "<option value='" + tempMap.getString("INFOGRPCD") + "' >" + tempMap.getString("INFOGRPNM") + "</option>";
					}
				}
			}

			logger.debug("CAT_CNT   ==> " + dm.getInt("CAT_CNT"));
			logger.debug("VAL_CNT   ==> " + dm.getInt("VAL_CNT"));
			logger.debug("INFO_GRP_CD ==> " + dm.getString("INFO_GRP_CD"));
			//1:n 관계인지 체크 - select 카테고리 정보 가져오기
			if (dm.getInt("CAT_CNT") > 1) {
				// 등록된 데이터가 있을경우
				if (dm.getInt("VAL_CNT") > 0) {
					param.put("infoGrpCd", dm.getString("INFO_GRP_CD"));
					tempGrpCd = dm.getString("INFO_GRP_CD");

					list = pscpprd0020Service.selectPrdCommerceList((Map) param);
				}
			} else {
				param.put("infoGrpCd", dm.getString("INFO_GRP_CD"));
				list = pscpprd0020Service.selectPrdCommerceList((Map) param);
			}

			//샵링크 요청사항(openapi). 대분류 매핑이 안되어 있을경우 MD 에게 알리기 위한 alert
			if (dm.getInt("VAL_CNT") > 0) {
				extMap.put("checkMapping", "");
			} else {
				if (dm.getInt("CHECK_CNT") > 0) {
					extMap.put("checkMapping", "데이터가 등록되어 있으나, 전상법 매핑문제로 데이터가 \n보이지 않습니다.시스템관리자에게 연락바랍니다.");
				}
			}

			extMap.put("optionValue", tempSelect);
			extMap.put("infoGrpCd", tempGrpCd);

			//20180715 - 상품수정요청 시 승인요청 또는 반려 일 경우 알림 메시지 제공
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("prodCd", request.getParameter("prodCd"));
			paramMap.put("typeCd", "003");
			PSCPPRD0005VO prdMdAprInfo = pscpprd0005Service.selectPrdMdAprvMst(paramMap);

			if (prdMdAprInfo != null) {
				extMap.put("aprvCd", prdMdAprInfo.getAprvCd());
			} else {
				extMap.put("aprvCd", "");
			}

			rtnMap = JsonUtils.convertList2Json((List) list, null, null);
			rtnMap.put("extMsg", extMap);

			// 처리성공
			rtnMap.put("result", true);
		} catch (Exception e) {
			// 작업오류
			logger.error("error --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}

	/**
	 * 추천상품 목록
	 * @Description : select버튼 변경시 전자상거래 리스트 호출
	 * @Method Name : prdCommerceSearch
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/selectProductCommerceSelectSearch_org.do")
	public String prdCommerceList_org(HttpServletRequest request) throws Exception {

		GridData gdRes = new GridData();

		try {
			// 요청객체
			String wiseGridData = request.getParameter("WISEGRID_DATA");
			GridData gdReq = OperateGridData.parse(wiseGridData);

			// 결과객체
			gdRes = OperateGridData.cloneResponseGridData(gdReq);
			gdRes.addParam("mode", gdReq.getParam("mode"));

			// 파라미터
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("prodCd", gdReq.getParam("prodCd"));
			paramMap.put("infoGrpCd", gdReq.getParam("infoGrpCd"));

			List<PSCPPRD0020VO> list = pscpprd0020Service.selectPrdCommerceList(paramMap);

			gdRes.addParam("optionValue", "");
			gdRes.addParam("infoGrpCd", gdReq.getParam("infoGrpCd"));

			// 데이터 없음
			if (list == null || list.size() == 0) {
				//				logger.debug("[ no row ]");
				gdRes.setStatus("false");
				request.setAttribute("wizeGridResult", gdRes);
				return "common/wiseGridResult";
			}

			PSCPPRD0020VO bean;
			int listSize = list.size();

			for (int i = 0; i < listSize; i++) {
				bean = (PSCPPRD0020VO) list.get(i);

				gdRes.getHeader("infoGrpCd").addValue(bean.getInfoGrpCd(), "");
				gdRes.getHeader("infoColCd").addValue(bean.getInfoColCd(), "");
				gdRes.getHeader("infoColNm").addValue(bean.getInfoColNm(), "");
				gdRes.getHeader("infoColDesc").addValue(bean.getInfoColDesc(), "");
				gdRes.getHeader("colVal").addValue(StringUtils.defaultIfEmpty(bean.getColVal(), ""), "");
				gdRes.getHeader("newProdCd").addValue(StringUtils.defaultIfEmpty(bean.getNewProdCd(), ""), "");
				gdRes.getHeader("chk").addValue("1", "");
			}

			// 성공
			// logger.debug("[ success ]");
			gdRes.setStatus("true");

		} catch (Exception e) {
			// 오류
			// logger.debug("[ Exception ]");
			gdRes.setMessage(e.getMessage());
			gdRes.setStatus("false");
			logger.debug("", e);
		}

		request.setAttribute("wizeGridResult", gdRes);
		// logger.debug("[ wiseGridResult ]");
		return "common/wiseGridResult";
	}

	/**
	 * 인터넷상품관리, 온라인상품가등록관리, 수산소분상품관리 상세탭에서 공통으로 사용 for IBSheet
	 * @Description : select버튼 변경시 전자상거래 리스트 호출
	 * @Method Name : selectProductCommerceSelectSearch
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/selectProductCommerceSelectSearch.do")
	public @ResponseBody Map selectProductCommerceSelectSearch(HttpServletRequest request) throws Exception {

		Map rtnMap = new HashMap<String, Object>();

		try {
			DataMap param = new DataMap(request);
			Map extMap = new HashMap();

			List<PSCPPRD0020VO> list = pscpprd0020Service.selectPrdCommerceList((Map) param);

			extMap.put("infoGrpCd", param.getString("infoGrpCd"));

			rtnMap = JsonUtils.convertList2Json((List) list, null, null);
			rtnMap.put("extMsg", extMap);

			// 처리성공
			rtnMap.put("result", true);
		} catch (Exception e) {
			// 작업오류
			logger.error("error --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}

	/**
	 * 전자상거래 수정
	 * @Description :
	 * @Method Name : prdCommerceListUpdate
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/selectProductCommerceListUpdate_org.do")
	public void prdCommerceListUpdate_org(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginSession loginSession = LoginSession.getLoginSession(request);

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
			gdRes.addParam("vendorId", gdReq.getParam("vendorId"));

			//int resultCnt = pscpprd0020Service.updatePrdCommerce(gdReq);
			//int resultCnt = pscpprd0020Service.updatePrdCommerceHist(gdReq);

			// 처리 결과
//			if(resultCnt > 0){
//				message  = messageSource.getMessage("msg.common.success.request", null, Locale.getDefault());
//				gdRes.addParam("saveData",resultCnt + "건의 "+message);
//				gdRes.setStatus("true");
//			} else {
//				message  = messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault());
//				gdRes.addParam("saveData",message);
//				gdRes.setStatus("true");//--true
//			}

		} catch (Exception e) {
			// 오류
			gdRes.setMessage(e.getMessage());
			gdRes.setStatus("false");
		}
		finally {
			try {
				// 자료구조를 전문으로 변경해 Write한다.
				OperateGridData.write(gdRes, out);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}

	/**
	 * 전자상거래 리스트 수정
	 * @Description :
	 * @Method Name : prdCommerceListUpdate
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/selectProductCommerceListUpdate.do")
	public @ResponseBody JSONObject prdCommerceListUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {

		JSONObject jObj = new JSONObject();
		String message = "";
		
		boolean isOK = true;

		try {
			// 요청객체

			int rowCount = request.getParameterValues("chk").length;
			for (int i = 0; i < rowCount; i++) {
				if (StringUtil.getByteLength(request.getParameterValues("colVal")[i]) > 2000) {
					isOK = false;
				}
			}

			int resultCnt = 0;

//			if (isOK) {
				resultCnt = pscpprd0020Service.updatePrdCommerceHist(request);
//			}

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
}