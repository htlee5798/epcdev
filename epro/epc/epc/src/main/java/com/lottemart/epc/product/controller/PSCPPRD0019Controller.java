package com.lottemart.epc.product.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.views.AjaxJsonModelHelper;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import xlib.cmc.GridData;
import xlib.cmc.OperateGridData;

import com.lottemart.common.login.model.LoginSession;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.product.model.PSCPPRD0019VO;
import com.lottemart.epc.product.service.PSCPPRD0019Service;

/**
 *
 * @Class Name : PSCPPRD0019Controller
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자	   수정내용
 *  -------    --------    ---------------------------
 * 2011. 9. 7   jib
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Controller("PSCPPRD0019Controller")
public class PSCPPRD0019Controller {
private static final Logger logger = LoggerFactory.getLogger(PSCPPRD0019Controller.class);

	@Autowired
	private PSCPPRD0019Service PSCPPRD0019Service;
	@Autowired
	private MessageSource messageSource;

	/**
	 * 추가속성 N 수정 및 추가 폼 페이지
	 * @Description : 상품 추가속성 N 의 수정 및 추가 초기페이지 로딩 (팝업)
	 * @Method Name : prdAttributeCategoryInsertForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/selectProductAttributeCategoryInsertForm.do")
	public String prdAttributeCategoryInsertForm(HttpServletRequest request) throws Exception {


		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("prodCd", request.getParameter("prodCd"));
		paramMap.put("categoryId", request.getParameter("categoryId"));
		paramMap.put("addColSeq", request.getParameter("addColSeq"));
		paramMap.put("addColValSeq", request.getParameter("addColValSeq"));
		PSCPPRD0019VO resultColVal = PSCPPRD0019Service.selectPrdAttributeColVal(paramMap);
		String colVal = (String)(resultColVal.getColVal()==null?"":resultColVal.getColVal());

		if(resultColVal != null && colVal != null) {
			request.setAttribute("colVal", colVal);
		}

		return "product/internet/PSCPPRD001903";
	}

	/**
	 * 추가속성 입력 폼 페이지
	 * @Description : 상품 추가속성 입력 초기페이지 로딩 (팝업)
	 * @Method Name : prdAttributeInsertForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/selectProductAttributeInsertForm.do")
	public String prdAttributeInsertForm(HttpServletRequest request) throws Exception {
		return "product/internet/PSCPPRD001902";
	}

	/**
	 * 추가속성 폼 페이지
	 * @Description : 상품 추가속성 목록 초기페이지 로딩
	 * @Method Name : prdAttributeForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/selectProductAttributeForm.do")
	public String prdAttributeForm(HttpServletRequest request) throws Exception {

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("prodCd", request.getParameter("prodCd"));
		PSCPPRD0019VO resultAttributeCategory = PSCPPRD0019Service.selectPrdAttributeCategory(paramMap);
		String categoryId = (String)(resultAttributeCategory.getCategoryId()==null?"":resultAttributeCategory.getCategoryId());
		if(!(resultAttributeCategory == null || categoryId == null || "".equals(categoryId) || "null".equals(categoryId))) {
			//상품에 지정된 카테고리가 있으면 셋팅
			request.setAttribute("categoryId", categoryId);
		}

		return "product/internet/PSCPPRD001901";
	}

	/**
	 * 추가속성 목록
	 * @Description : 조회버튼 클릭시 추가속성 목록을 얻어서 그리드에 리턴
	 * @Method Name : prdAttributeSearch
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/selectProductAttributeSearch_org.do")
	public String prdAttributeSearch_org(HttpServletRequest request) throws Exception {

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
			paramMap.put("prodLinkKindCd", gdReq.getParam("prodLinkKindCd"));

			// 데이터 조회
//			logger.debug("[ selectPrdAttributeList ]");
			List<PSCPPRD0019VO> list = PSCPPRD0019Service.selectPrdAttributeList(paramMap);

			// 데이터 없음
			if(list == null || list.size() == 0) {
//				logger.debug("[ no row ]");
				gdRes.setStatus("false");
				request.setAttribute("wizeGridResult", gdRes);
				return "common/wiseGridResult";
			}

			// GridData 셋팅
//			logger.debug("[ data set : "+list.size()+" rows ]");

			PSCPPRD0019VO bean;
			int listSize = list.size();

			for(int i = 0; i < listSize; i++) {
				bean = (PSCPPRD0019VO)list.get(i);

				gdRes.getHeader("prodCd").addValue(bean.getProdCd(),"");
				gdRes.getHeader("addColSeq").addValue(bean.getAddColSeq(),"");
				gdRes.getHeader("addColValSeq").addValue(bean.getAddColValSeq(),"");
				gdRes.getHeader("categoryId").addValue(bean.getCategoryId(),"");
				gdRes.getHeader("categoryNm").addValue(bean.getCategoryNm(),"");
				gdRes.getHeader("colNm").addValue(bean.getColNm(),"");
				gdRes.getHeader("colVal").addValue(bean.getColVal()==null?"":bean.getColVal(),"");
				gdRes.getHeader("useYn").addValue(bean.getUseYn(),"");
				gdRes.getHeader("condSearchYn").addValue(bean.getCondSearchYn(),"");

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
	}

	/**
	 * 추가속성 목록 for IBSheet
	 * @Description : 조회버튼 클릭시 추가속성 목록을 얻어서 그리드에 리턴
	 * @Method Name : prdAttributeSearch
	 * @param request
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("product/selectProductAttributeSearch.do")
	public @ResponseBody Map selectProductAttributeSearch(HttpServletRequest request) throws Exception {

        Map rtnMap = new HashMap<String, Object>();
		try {

			DataMap param = new DataMap(request);
			logger.debug("prodCd ---> " + param.getString("prodCd"));

			// 데이터 조회
			List<PSCPPRD0019VO> list = PSCPPRD0019Service.selectPrdAttributeList((Map)param);

			rtnMap = JsonUtils.convertList2Json((List)list, null, null);

			// 성공
	        rtnMap.put("result", true);

		} catch (Exception e) {
			// 작업오류
	        logger.error("error message --> " + e.getMessage());
	        rtnMap.put("result", false);
	        rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}

	/**
	 * 추가속성 추가, 삭제 처리
	 * @Description : 추가속성 저장, 삭제 버튼 클릭시 해당 처리를 완료한후에 메세지를 띄고 그리드를 리로드 한다.
	 * @Method Name : prdAttributeListUpdate
	 * @param request
	 * @return
	 * @throws Exception

	@RequestMapping("product/selectProductAttributeListUpdate_org.do")
	public void prdAttributeListUpdate_org(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
			String mode = (String) gdReq.getParam("mode");

			// 값 셋팅
			List<PSCPPRD0019VO> beanList = new ArrayList<PSCPPRD0019VO>();
			PSCPPRD0019VO bean;
			String resultStr;

			int rowCount = gdReq.getHeader("CHK").getRowCount();

//			logger.debug("[ updatePrdAttributeList set ]");
//			logger.debug("[ mode : "+gdReq.getParam("mode")+" ]");
//			logger.debug("[ rowCount : "+rowCount +" ]");

			for (int i = 0; i < rowCount; i++) {
//				logger.debug("[ set i : "+i+" ]");
				bean = new PSCPPRD0019VO();

				if("insert".equals(mode)) {
					bean.setProdCd(gdReq.getHeader("prodCd").getValue(i));
					bean.setCategoryId(gdReq.getHeader("categoryId").getValue(i));
					bean.setAddColSeq(gdReq.getHeader("addColSeq").getValue(i));
					bean.setAddColValSeq(gdReq.getHeader("addColValSeq").getValue(i));
					bean.setCondSearchYn(gdReq.getHeader("condSearchYn").getValue(i));
					bean.setColVal(gdReq.getHeader("colVal").getValue(i));
					bean.setRegId(gdReq.getParam("vendorId"));

				//	logger.debug("[ vendorIdI : "+gdReq.getParam("vendorId") +" ]");
				} else {
					bean.setProdCd(gdReq.getHeader("prodCd").getValue(i));
					bean.setCategoryId(gdReq.getHeader("categoryId").getValue(i));
					bean.setAddColSeq(gdReq.getHeader("addColSeq").getValue(i));
					bean.setAddColValSeq(gdReq.getHeader("addColValSeq").getValue(i));
					bean.setRegId(gdReq.getParam("vendorId"));

//					logger.debug("[ vendorIdE : "+gdReq.getParam("vendorId") +" ]");
				}

				// 값 채크
//				logger.debug("[ validate check ]");
				resultStr = validate2(bean);//-- 각기 채크..
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
//			logger.debug("[ updatePrdAttributeList begin ]");
			resultCnt = PSCPPRD0019Service.updatePrdAttributeList(beanList, (String) gdReq.getParam("mode"));

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
//				logger.debug("[ OperateGridData write ]");
				OperateGridData.write(gdRes, out);
			} catch (Exception e) {
//				logger.debug("[ finally Exception ]");
			}
		}
	} */

	/**
	 * Desc : 등록/삭제
	 * @Method Name : selectProductAttributeListUpdate
	 * @param request
	 * @param response
	 * @return JSONObject
	 * @throws Exception
	 * @exception Exception
	 */
	@RequestMapping("product/selectProductAttributeListUpdate.do")
	public @ResponseBody JSONObject selectProductAttributeListUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {

        JSONObject jObj = new JSONObject();
		String message = "";
		int resultCnt = 0;

		try {

			resultCnt = PSCPPRD0019Service.updatePrdAttributeList(request);

			// 처리 결과
			if (resultCnt > 0) {
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

	/**
	 * 추가속성 카테고리 목록
	 * @Description : 추가속성 카테고리 목록을 로딩하여 그리드에 리턴
	 * @Method Name : prdAttributeCategorySearch
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/selectProductAttributeCategorySearch_org.do")
	public String prdAttributeCategorySearch_org(HttpServletRequest request) throws Exception {

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
			paramMap.put("categoryId", gdReq.getParam("categoryId"));

			// 데이터 조회
//			logger.debug("[ selectPrdAttributeCategoryList ]");
			List<PSCPPRD0019VO> list = PSCPPRD0019Service.selectPrdAttributeCategoryList(paramMap);

			// 데이터 없음
			if(list == null || list.size() == 0) {
//				logger.debug("[ no row ]");
				gdRes.setStatus("false");
				request.setAttribute("wizeGridResult", gdRes);
				return "common/wiseGridResult";
			}

			// GridData 셋팅
//			logger.debug("[ data set ]");

			PSCPPRD0019VO bean;
			int listSize = list.size();

			for(int i = 0; i < listSize; i++) {
				bean = (PSCPPRD0019VO)list.get(i);

				gdRes.getHeader("categoryId").addValue(bean.getCategoryId(),"");
				gdRes.getHeader("categoryNm").addValue(bean.getCategoryNm(),"");
				gdRes.getHeader("colNm").addValue(bean.getColNm()==null?"":bean.getColNm(),"");
				gdRes.getHeader("colVal").addValue(bean.getColVal()==null?"":bean.getColVal(),"");
				gdRes.getHeader("useYn").addValue(bean.getUseYn(),"");
				gdRes.getHeader("condSearchYn").addValue(bean.getCondSearchYn(),"");
				gdRes.getHeader("addColSeq").addValue(bean.getAddColSeq()==null?"":bean.getAddColSeq(),"");
				gdRes.getHeader("addColValSeq").addValue(bean.getAddColValSeq()==null?"":bean.getAddColValSeq(),"");
				gdRes.getHeader("prodCd").addValue(bean.getProdCd(),"");
				// tmp
				gdRes.getHeader("CHK").addValue("0","");
				gdRes.getHeader("num").addValue(bean.getNum(),"");
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
	 * 추가속성 카테고리 목록 for IBSheet
	 * @Description : 추가속성 카테고리 목록을 로딩하여 그리드에 리턴
	 * @Method Name : prdAttributeCategorySearch
	 * @param request
	 * @return Map
	 * @throws Exception
	 * @exception Exception
	 */
	@RequestMapping("product/selectProductAttributeCategorySearch.do")
	public @ResponseBody Map selectProductAttributeCategorySearch(HttpServletRequest request) throws Exception {

        Map rtnMap = new HashMap<String, Object>();
		try {

			DataMap param = new DataMap(request);

			logger.debug("prodCd ---> " + param.getString("prodCd"));

			String[] categoryIdArr =  param.getString("categoryId").split(",");

			param.put("categoryId", categoryIdArr);

			// 데이터 조회
			List<PSCPPRD0019VO> list = PSCPPRD0019Service.selectPrdAttributeCategoryList((Map)param);

			rtnMap = JsonUtils.convertList2Json((List)list, null, null);

			// 성공
	        rtnMap.put("result", true);

		} catch (Exception e) {
			// 작업오류
	        logger.error("error message --> " + e.getMessage());
	        rtnMap.put("result", false);
	        rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}

	/**
	 * 추가속성 N 입력, 업데이트 처리
	 * @Description : 팝업창의 추가속성 항목값 정보 입력 버튼 클릭시 추가속성 입력, 업데이트 처리후 그리드를 리로드한다.
	 * @Method Name : prdAttributeInsert
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("product/selectProductAttributeInsert.do")
	public ModelAndView prdAttributeInsert(HttpServletRequest request, HttpServletResponse response) throws Exception {

		LoginSession loginSession = LoginSession.getLoginSession(request);
    	String message;

		PSCPPRD0019VO bean = new PSCPPRD0019VO();
		bean.setProdCd((String) request.getParameter("prodCd"));
		bean.setCategoryId((String) request.getParameter("categoryId"));
		bean.setAddColSeq((String) request.getParameter("addColSeq"));
		bean.setAddColValSeq((String) request.getParameter("addColValSeq"));
		bean.setColVal((String) request.getParameter("colVal"));
		bean.setCondSearchYn((String) request.getParameter("condSearchYn"));
		bean.setRegId((String) request.getParameter("vendorId"));


		bean.setMode((String) request.getParameter("mode"));

		// 값이 있을경우 에러
		String resultStr = validate(bean);

		message = messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault());
		if(!"".equals(resultStr)){
			return AjaxJsonModelHelper.create(resultStr);
		}
		try {
			int resultCnt = 0;

			resultCnt = PSCPPRD0019Service.insertPrdAttribute(bean);

	    	if(resultCnt > 0){
	    		return AjaxJsonModelHelper.create("");
	    	}else{
	    		return AjaxJsonModelHelper.create(message);
	    	}
		} catch (Exception e) {
			return AjaxJsonModelHelper.create(message);

		}
	}

	/*
	 * validate 체크
	 */
	public String validate(PSCPPRD0019VO bean) throws Exception {
		if(StringUtils.isEmpty(bean.getProdCd())) {
			return "상품 코드가 없습니다.";
		}

		return "";
	}

	public String validate2(PSCPPRD0019VO bean) throws Exception {
		if(StringUtils.isEmpty(bean.getProdCd())) {
			return "상품 코드가 없습니다.";
		}

		return "";
	}

}