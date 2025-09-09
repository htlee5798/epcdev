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

import com.lottemart.common.login.model.LoginSession;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.product.model.PSCPPRD0020VO;
import com.lottemart.epc.product.service.PSCPPRD0022Service;


@Controller("PSCPPRD0022Controller")
public class PSCPPRD0022Controller {

	private static final Logger logger = LoggerFactory.getLogger(PSCPPRD0022Controller.class);


	@Autowired
	private PSCPPRD0022Service pscpprd0022Service;
	@Autowired
	private MessageSource messageSource;

	/**
	 * 추천상품 폼 페이지
	 * @Description : 상품 추천상품 목록 초기페이지 로딩
	 * @Method Name : prdCertForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/selectProductCertForm.do")
	public String prdCertForm(HttpServletRequest request) throws Exception {
		//return "product/internet/PBOMPRD003901";  //수정필요
		  return "product/internet/PSCPPRD002201";  //수정필요
	}



	@RequestMapping("product/selectProductCertSearch_org.do")
	public String prdCertSearch_org(HttpServletRequest request) throws Exception {

		GridData gdRes = new GridData();

		try {
			// 요청객체
			String wiseGridData = request.getParameter("WISEGRID_DATA");
			GridData  gdReq = OperateGridData.parse(wiseGridData);

			// 결과객체
			gdRes = OperateGridData.cloneResponseGridData(gdReq);
			gdRes.addParam("mode", gdReq.getParam("mode"));

			String prodCd = gdReq.getParam("prodCd");
			String newProdCd = gdReq.getParam("newProdCd");

			// 파라미터
			Map<String, String> paramMap = new HashMap<String, String>();

			//관련정보 검색
			DataMap dm = new DataMap();

			if(newProdCd != null && !"".equals(newProdCd)){
				paramMap.put("prodCd", newProdCd);
				dm = pscpprd0022Service.selectPrdCertTemp(paramMap);  //온라인 상품 가등록 탭 영역
			}else{
				paramMap.put("prodCd", prodCd);
				dm = pscpprd0022Service.selectPrdCertCnt(paramMap);   //인터넷 상품관리 탭 영역
			}

			List<PSCPPRD0020VO> list = null;
			List<DataMap> selectlist = null;
			String tempSelect = "";
			String tempGrpCd = "";

			//select 박스 생성
			paramMap.put("catCd", dm.getString("CAT_CD"));
			selectlist = pscpprd0022Service.selectPrdCertCatCdList(paramMap);

			if(selectlist != null && selectlist.size() > 0) {

				int selectSize = selectlist.size();
				tempSelect += "<option value=''>전체</option>";

				for(int i = 0; i < selectSize; i++) {
					DataMap tempMap = (DataMap)selectlist.get(i);
					if(selectSize == 1){
						tempSelect += "<option value='"+tempMap.getString("INFOGRPCD")+"' selected='selected'>"+tempMap.getString("INFOGRPNM")+"</option>";
					}else{
						tempSelect += "<option value='"+tempMap.getString("INFOGRPCD")+"' >"+tempMap.getString("INFOGRPNM")+"</option>";
					}
				}
			}

			//1:n 관계인지 체크 - select 카테고리 정보 가져오기
			if(dm.getInt("CAT_CNT") > 1){
				//등록된 데이터가 있을경우
				if(dm.getInt("VAL_CNT") > 0){
					paramMap.put("infoGrpCd", dm.getString("INFO_GRP_CD"));
					tempGrpCd = dm.getString("INFO_GRP_CD");

					list 	   = pscpprd0022Service.selectPrdCertList(paramMap);
				}

			}else{
				paramMap.put("infoGrpCd", dm.getString("INFO_GRP_CD"));
				list 	   = pscpprd0022Service.selectPrdCertList(paramMap);
			}

			gdRes.addParam("optionValue", tempSelect);
			gdRes.addParam("infoGrpCd", tempGrpCd);

			// 데이터 없음
			if(list == null || list.size() == 0) {
				gdRes.setStatus("true");
				request.setAttribute("wizeGridResult", gdRes);
				return "common/wiseGridResult";
			}

			PSCPPRD0020VO bean;
			int listSize = list.size();

			for(int i = 0; i < listSize; i++) {
				bean = (PSCPPRD0020VO)list.get(i);

				gdRes.getHeader("infoGrpCd").addValue(bean.getInfoGrpCd(),"");
				gdRes.getHeader("infoColCd").addValue(bean.getInfoColCd(),"");
				gdRes.getHeader("infoColNm").addValue(bean.getInfoColNm(),"");
				gdRes.getHeader("infoColDesc").addValue(bean.getInfoColDesc(),"");
				if ("KC001".equals(bean.getInfoGrpCd())){
					gdRes.getHeader("colVal").addValue(StringUtils.defaultIfEmpty(bean.getColVal(), "해당사항없음"),"");
				}else{
					gdRes.getHeader("colVal").addValue(StringUtils.defaultIfEmpty(bean.getColVal(), ""),"");
				}
				gdRes.getHeader("newProdCd").addValue(StringUtils.defaultIfEmpty(bean.getNewProdCd(), ""),"");
				gdRes.getHeader("chk").addValue("1", "");
			}

			// 성공
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

	/**
	 * KS인증 탭 > 조회 for IBSheet
	 * @Method Name : selectProductCertSearch
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("product/selectProductCertSearch.do")
	public @ResponseBody Map selectProductCertSearch(HttpServletRequest request) throws Exception {

        Map rtnMap = new HashMap<String, Object>();

		try {
			DataMap param = new DataMap(request);
			Map extMap = new HashMap();

			String pgmId  = param.getString("pgmId");

			//관련정보 검색
			DataMap dm = new DataMap();

			if (pgmId != null && !"".equals(pgmId)) {
				param.put("prodCd", pgmId);
				dm = pscpprd0022Service.selectPrdCertTemp((Map)param);  //온라인 상품 가등록 탭 영역
			} else {

				DataMap updateInfo = pscpprd0022Service.prodCertUpdateInfo((Map)param);
				if (updateInfo != null){
					extMap.put("updId", updateInfo.getString("CNFM_ID"));
					extMap.put("updDate", updateInfo.getString("CNFM_DATE"));
				} else {
					extMap.put("updId", "");
					extMap.put("updDate", "");
				}

				dm = pscpprd0022Service.selectPrdCertCnt((Map)param);   //인터넷 상품관리 탭 영역
			}

			List<PSCPPRD0020VO> list = null;
			List<DataMap> selectlist = null;
			String tempSelect = "";
			String tempGrpCd = "";

			//select 박스 생성
			param.put("catCd", dm.getString("CAT_CD"));
			selectlist = pscpprd0022Service.selectPrdCertCatCdList((Map)param);

			if (selectlist != null && selectlist.size() > 0) {

				int selectSize = selectlist.size();

				for (int i = 0; i < selectSize; i++) {
					DataMap tempMap = (DataMap)selectlist.get(i);
					if (selectSize == 1) {
						tempSelect += "<option value='"+tempMap.getString("INFOGRPCD")+"' selected='selected'>"+tempMap.getString("INFOGRPNM")+"</option>";
						tempGrpCd = tempMap.getString("INFOGRPCD");
					} else {
						tempSelect += "<option value='"+tempMap.getString("INFOGRPCD")+"' >"+tempMap.getString("INFOGRPNM")+"</option>";
					}
				}
			}

			//1:n 관계인지 체크 - select 카테고리 정보 가져오기
			if (dm.getInt("CAT_CNT") > 1){
				//등록된 데이터가 있을경우
				if (dm.getInt("VAL_CNT") > 0){
					tempGrpCd = dm.getString("INFO_GRP_CD");
					list 	   = pscpprd0022Service.selectPrdCertList((Map)param);
				}
			} else {
				list 	   = pscpprd0022Service.selectPrdCertList((Map)param);
			}

			extMap.put("optionValue", tempSelect);
			extMap.put("infoGrpCd", tempGrpCd);

	        //rtnMap = JsonUtils.convertList2Json((List)list, null, null);
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
	 * @Description : select버튼 변경시 KC 인증 리스트 호출
	 * @Method Name : selectProductCertSelectSearch_org
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/selectProductCertSelectSearch_org.do")
	public String prdCertList_org(HttpServletRequest request) throws Exception {

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
			paramMap.put("infoGrpCd", gdReq.getParam("infoGrpCd"));

			List<PSCPPRD0020VO> list = pscpprd0022Service.selectPrdCertList(paramMap);

			gdRes.addParam("optionValue", "");
			gdRes.addParam("infoGrpCd", gdReq.getParam("infoGrpCd"));

			// 데이터 없음
			if(list == null || list.size() == 0) {
//				logger.debug("[ no row ]");
				gdRes.setStatus("false");
				request.setAttribute("wizeGridResult", gdRes);
				return "common/wiseGridResult";
			}

			PSCPPRD0020VO bean;
			int listSize = list.size();

			for(int i = 0; i < listSize; i++) {
				bean = (PSCPPRD0020VO)list.get(i);

				gdRes.getHeader("infoGrpCd").addValue(bean.getInfoGrpCd(),"");
				gdRes.getHeader("infoColCd").addValue(bean.getInfoColCd(),"");
				gdRes.getHeader("infoColNm").addValue(bean.getInfoColNm(),"");
				gdRes.getHeader("infoColDesc").addValue(bean.getInfoColDesc(),"");
				gdRes.getHeader("colVal").addValue(StringUtils.defaultIfEmpty(bean.getColVal(), ""),"");
				gdRes.getHeader("newProdCd").addValue(StringUtils.defaultIfEmpty(bean.getNewProdCd(), ""),"");
				gdRes.getHeader("chk").addValue("1", "");
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

	/**
	 * 제품안전인증 탭 > select Dtl조회
	 * @Description : select버튼 변경시 제품안전인증 상세 리스트 호출(selectBox)
	 * @Method Name : selectProductCertDtlSelectSearch
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/selectProductCertDtlSelectSearch.do")
	public @ResponseBody Map selectProductCertDtlSelectSearch(HttpServletRequest request) throws Exception {
        Map rtnMap = new HashMap<String, Object>();

		try {
			DataMap param = new DataMap(request);
			Map extMap = new HashMap();

			extMap.put("infoGrpCd", param.getString("infoGrpCd"));

			String tempSelect = "";
			String tempColCd = "";
			String temp = "";
			List<PSCPPRD0020VO> list = pscpprd0022Service.selectPrdCertList((Map)param);
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					PSCPPRD0020VO tempMap = list.get(i);

					if (i == 0) temp = tempMap.getInfoColCd();	//기 등록된 데이터가 없을 때 사용하기 위해서

					if (tempMap.getColVal() != null && !"".equals(tempMap.getColVal())) {
						tempSelect += "<option value='"+tempMap.getInfoColCd()+"' selected='selected'>"+tempMap.getInfoColNm()+"</option>";
						tempColCd = tempMap.getInfoColCd();
					} else {
						tempSelect += "<option value='"+tempMap.getInfoColCd()+"' >"+tempMap.getInfoColNm()+"</option>";
					}
				}
			}

			extMap.put("optionValueDtl", tempSelect);
			if ("".equals(tempColCd)) {
				extMap.put("infoColCd", temp);
			} else {
				extMap.put("infoColCd", tempColCd);
			}

			//rtnMap = JsonUtils.convertList2Json((List)list, null, null);
	        rtnMap.put("extMsgDtl", extMap);

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
	 * KS인증 탭 > select 조회 for IBSheet
	 * @Description : select버튼 변경시 KC인증 리스트 호출
	 * @Method Name : selectProductCertSelectSearch
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/selectProductCertSelectSearch.do")
	public @ResponseBody Map selectProductCertSelectSearch(HttpServletRequest request) throws Exception {

        Map rtnMap = new HashMap<String, Object>();

		try {
			DataMap param = new DataMap(request);
			Map extMap = new HashMap();

			extMap.put("infoGrpCd", param.getString("infoGrpCd"));

			List<PSCPPRD0020VO> list = pscpprd0022Service.selectPrdCertList((Map)param);

	        rtnMap = JsonUtils.convertList2Json((List)list, null, null);
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
	 * KC 인증 수정
	 * @Description :
	 * @Method Name : prdCertListUpdate
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/selectProductCertListUpdate_org.do")
	public void prdCertListUpdate_org(HttpServletRequest request, HttpServletResponse response) throws Exception {
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

//			int resultCnt = pscpprd0022Service.updatePrdCert(gdReq);
//
//			// 처리 결과
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
	 * KC인증 저장 - for IBSheet
	 * @Description :
	 * @Method Name : prdCertListUpdate
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/selectProductCertListUpdate.do")
	public @ResponseBody JSONObject selectProductCertListUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginSession loginSession = LoginSession.getLoginSession(request);
		JSONObject jObj = new JSONObject();
		String message = "";

		try {

			int resultCnt = pscpprd0022Service.updatePrdCert(request);

			// 처리 결과
			if (resultCnt > 0) {

				//전상법 제휴사 연동
//				DataMap dmap = new DataMap();
//				dmap.put("prodCd",gdReq.getParam("prodCd"));
//				dmap.put("regId",loginSession.getAdminId());
//				pbomprd0047Service.prodCertPartnerUpdate(dmap);

				jObj.put("Code", 1);
				message  = messageSource.getMessage("msg.common.success.request", null, Locale.getDefault());
				jObj.put("Message", resultCnt + "건의 " + message);

			} else {
				jObj.put("Code", 0);
				message  = messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault());
				jObj.put("Message", message);
			}

		} catch (Exception e) {
			jObj.put("Code", -1);
			jObj.put("Message", e.getMessage());
		}

		return JsonUtils.getResultJson(jObj);
	}
}