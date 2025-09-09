package com.lottemart.epc.product.controller;


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.framework.property.ConfigurationService;
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

import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.product.model.PSCPPRD0016VO;
import com.lottemart.epc.product.service.PSCPPRD0016Service;

/**
 * @Class Name : PSCPPRD0016Controller.java
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
@Controller
public class PSCPPRD0016Controller
{
	private static final Logger logger = LoggerFactory.getLogger(PSCPPRD0016Controller.class);

	@Autowired
	private PSCPPRD0016Service pscpprd0016Service;
	@Autowired
	private ConfigurationService config;
	@Autowired
	private MessageSource messageSource;

	/**
	 * 증정품 폼 페이지
	 * @Description : 증정품 목록 초기페이지 로딩
	 * @Method Name : selectProductPresentForm
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping("product/selectProductPresentForm.do")
	public String selectProductPresentForm(HttpServletRequest request) throws Exception
	{
		return "product/internet/PSCPPRD0016";
	}

	/**
	 * 증정품 목록
	 * @Description : 페이지 로딩시 증정품 목록을 얻어서 그리드에 리턴
	 * @Method Name : selectProduectPresentSearch
	 * @param request
	 * @return String
	 * @throws Exception

	@RequestMapping("product/selectProduectPresentSearch_org.do")
	public String selectProduectPresentSearch_org(HttpServletRequest request) throws Exception
	{
		GridData gdRes = new GridData();

		try
		{
			// 요청객체
			String wiseGridData = request.getParameter("WISEGRID_DATA");
			GridData  gdReq = OperateGridData.parse(wiseGridData);

			// 결과객체
			gdRes = OperateGridData.cloneResponseGridData(gdReq);
			gdRes.addParam("mode", gdReq.getParam("mode"));

			// 파라미터
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("prodCd", gdReq.getParam("prodCd"));

			// 데이터 조회
//			logger.debug("::: selectPrdPresentList :::");
			List<PSCPPRD0016VO> list = pscpprd0016Service.selectPrdPresentList(paramMap);
			int listSize = list.size();
//			logger.debug("list.size() ==>" + listSize + "<==");

			// 데이터 없음
			if (list == null || listSize == 0)
			{
//				logger.debug("::: data not exists..! :::");
				gdRes.setStatus("false");
				request.setAttribute("wizeGridResult", gdRes);

				return "common/wiseGridResult";
			}

			// GridData 셋팅
			PSCPPRD0016VO bean = null;

			for (int i = 0; i < listSize; i++)
			{
				bean = (PSCPPRD0016VO)list.get(i);
				gdRes.getHeader("prest").addValue(bean.getPrest(),"");

				String PrestDy="";
				if ("-".equals(bean.getPrestStartDy()) || "-".equals(bean.getPrestEndDy()))
				{
					PrestDy = "-";
				}
				else
				{
					PrestDy =
						bean.getPrestStartDy().substring(0,4)+"-"+bean.getPrestStartDy().substring(4,6)+"-"+bean.getPrestStartDy().substring(6,8)
						+" ~ "+bean.getPrestEndDy().substring(0,4)+"-"+bean.getPrestEndDy().substring(4,6)+"-"+bean.getPrestEndDy().substring(6,8);
				}

				gdRes.getHeader("prestDy").addValue(PrestDy,"");
				gdRes.getHeader("strCd").addValue(bean.getStrCd(),"");
				gdRes.getHeader("prodCd").addValue(bean.getProdCd(),"");
				gdRes.getHeader("strNm").addValue(bean.getStrNm(),"");
				gdRes.getHeader("prestType").addValue(bean.getPrestType(),"");

				// tmp
				gdRes.getHeader("num").addValue(Integer.toString(i+1),"");
				gdRes.getHeader("CHK").addValue("0","");
			}

			// 성공
//			logger.debug("[[[ success ]]]");
			gdRes.setStatus("true");

		}
		catch (Exception e)
		{
			// 오류
//			logger.debug("[[[ Exception ]]]");
			gdRes.setMessage(e.getMessage());
			gdRes.setStatus("false");
			logger.debug("", e);
		}

		request.setAttribute("wizeGridResult", gdRes);
//		logger.debug("[[[ wiseGridResult ]]]");

		return "common/wiseGridResult";
	}*/

	/**
	 * Desc : 증정품 목록 for IBSheet
	 * @Method Name : prdPresentSearch
	 * @param request
	 * @throws Exception
	 * @return Map
	 * @exception Exception
	 */
	@RequestMapping("product/selectProduectPresentSearch.do")
	public @ResponseBody Map selectProduectPresentSearch(HttpServletRequest request) throws Exception {

        Map rtnMap = new HashMap<String, Object>();
		try {

			DataMap param = new DataMap(request);

			logger.debug("prodCd ---> " + param.getString("prodCd"));

			// 데이터 조회
			List<DataMap> list = pscpprd0016Service.selectPrdPresentList((Map)param);

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
	 * 증정품 수정 처리
	 * @Description : 저장, 삭제 버튼 클릭시 해당 처리를 완료한후에 메세지를 띄우고 그리드를 리로드한다.
	 * @Method Name : updateProduectPresentList
	 * @param request
	 * @param response
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping("product/updateProduectPresentList_org.do")
	public void updateProduectPresentList_org(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if( epcLoginVO == null || epcLoginVO.getVendorId() == null )
		{
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}

		GridData gdRes = new GridData();

		// Encode Type을 UTF-8로 변환한다.
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String message = "";

		try
		{
			// 요청객체
			String wiseGridData = request.getParameter("WISEGRID_DATA");
			GridData gdReq = OperateGridData.parse(wiseGridData);

			// 모드
			gdRes.addParam("mode", gdReq.getParam("mode"));

			// 값 셋팅
			List<PSCPPRD0016VO> beanList = new ArrayList<PSCPPRD0016VO>();
			PSCPPRD0016VO bean = null;

			int rowCount = gdReq.getHeader("num").getRowCount();
//			logger.debug("::: updatePrdPresentList set :::");
//			logger.debug("mode ==>" + gdReq.getParam("mode") + "<==");
//			logger.debug("rowCount ==>" + rowCount + "<==");

			for (int i = 0; i < rowCount; i++)
			{
				bean = new PSCPPRD0016VO();
				bean.setStrCd(gdReq.getHeader("strCd").getValue(i));
				bean.setProdCd(gdReq.getHeader("prodCd").getValue(i));

				bean.setPrest(gdReq.getParam("prest"));
				bean.setPrestStartDy(gdReq.getParam("prestStartDy"));
				bean.setPrestEndDy(gdReq.getParam("prestEndDy"));
				bean.setRegId(gdReq.getParam("vendorId"));
				bean.setPrestType(gdReq.getParam("prestType"));

				// 값 채크
				String resultStr = validate(bean);//-- 각기 채크..

				if (!"".equals(resultStr))
				{
//					logger.debug("validate check fail (" + i + ")==>" + resultStr + "<==");
					message  = resultStr;
					gdRes.addParam("saveData",message);
					gdRes.setStatus("true");
				}

				beanList.add(bean);
			}

			int resultCnt = 0;

			// 처리
			resultCnt = pscpprd0016Service.updatePrdPresentList(beanList, (String) gdReq.getParam("mode"));

			// 처리 결과
			if (resultCnt > 0)
			{
				message  = messageSource.getMessage("msg.common.success.request", null, Locale.getDefault());
				gdRes.addParam("saveData",message);
				gdRes.setStatus("true");
			}
			else
			{
				message  = messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault());
				gdRes.addParam("saveData",message);
				gdRes.setStatus("true");//--true
			}
		}
		catch (Exception e)
		{
			// 오류
			gdRes.setMessage(e.getMessage());
			gdRes.setStatus("false");
		}
		finally
		{
			try
			{
				// 자료구조를 전문으로 변경해 Write한다.
				OperateGridData.write(gdRes, out);
			}
			catch (Exception e)
			{
		        logger.error(e.getMessage());
			}
		}
	}

	/**
	 * 증정품 수정 처리
	 * @Description : 저장, 삭제 버튼 클릭시 해당 처리를 완료한후에 메세지를 띄우고 그리드를 리로드한다.
	 * @Method Name : prdPresentListUpdate
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("product/updateProduectPresentList.do")
	public @ResponseBody JSONObject updateProduectPresentList(HttpServletRequest request, HttpServletResponse response) throws Exception {

        JSONObject jObj = new JSONObject();
		String message = "";
		int resultCnt = 0;

		try {
			resultCnt = pscpprd0016Service.updatePrdPresentList(request);

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
	        logger.error(e.getMessage());
			jObj.put("Code", -1);
			jObj.put("Message", e.getMessage());
		}

		return JsonUtils.getResultJson(jObj);
	}

	/*
	 * validate 체크
	 */
	public String validate(PSCPPRD0016VO bean) throws Exception
	{
		if (StringUtils.isEmpty(bean.getProdCd()))
		{
			return "상품 코드가 없습니다.";
		}

		return "";
	}

}
