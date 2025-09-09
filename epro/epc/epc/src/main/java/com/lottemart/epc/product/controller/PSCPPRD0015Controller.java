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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import xlib.cmc.GridData;
import xlib.cmc.OperateGridData;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.product.model.PSCPPRD0015VO;
import com.lottemart.epc.product.service.PSCPPRD0015Service;

/**
 * @Class Name : PSCPPRD0015Controller.java
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
@Controller("pscpprd0015Controller")
public class PSCPPRD0015Controller 
{
	private static final Logger logger = LoggerFactory.getLogger(PSCPPRD0015Controller.class);
	
	@Autowired
	private PSCPPRD0015Service pscpprd0015Service;
	@Autowired
	private ConfigurationService config;
	@Autowired
	private MessageSource messageSource; 

	/**
	 * 상품 아이콘 폼 페이지
	 * @Description : 상품 아이콘 목록 초기페이지 로딩
	 * @Method Name : selectProduectIconForm
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping("product/selectProductIconForm.do")
	public String selectProduectIconForm(HttpServletRequest request) throws Exception 
	{
		// 점포 COMBO내용
		List<DataMap> storeList = pscpprd0015Service.selectPrdStoreList("");
		request.setAttribute("storeList", storeList);
		
		return "product/internet/PSCPPRD0015";
	}
	
	/**
	 * 상품 아이콘 목록
	 * @Description : 상품 아이콘 목록을 페이지별로 로딩하여 그리드에 리턴
	 * @Method Name : selectProduectIconSearch
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping("product/selectProduectIconSearch.do")
	public String selectProduectIconSearch(HttpServletRequest request) throws Exception 
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
			paramMap.put("strCd", gdReq.getParam("strCd"));			
			paramMap.put("strGubun", gdReq.getParam("strGubun"));

			// 데이터 조회
			logger.debug("::: updatePrdList set :::");
			List<PSCPPRD0015VO> list = pscpprd0015Service.selectPrdIconList(paramMap);
			int listSize = list.size();

			// 데이터 없음
			if (list == null || listSize == 0) 
			{
				logger.debug("::: data not exists..! :::");
				gdRes.setStatus("false");
				request.setAttribute("wizeGridResult", gdRes);
				
				return "common/wiseGridResult";
			}
	   
			PSCPPRD0015VO bean = null;
			// GridData 셋팅
			logger.debug("::: data set :::");
			for (int i = 0; i < listSize; i++) 
			{
				bean = (PSCPPRD0015VO)list.get(i);

				gdRes.getHeader("num").addValue(bean.getNum(),"");
				gdRes.getHeader("prodCd").addValue(bean.getProdCd(),"");
				gdRes.getHeader("prodNm").addValue(bean.getProdNm(),"");
				gdRes.getHeader("strCd").addValue(bean.getStrCd(),"");
				gdRes.getHeader("strNm").addValue(bean.getStrNm(),"");

				gdRes.getHeader("regDate").addValue(bean.getRegDate(),"");
				
				gdRes.getHeader("icon1").addValue(bean.getIcon1(),"");
				gdRes.getHeader("icon2").addValue(bean.getIcon2(),"");
				gdRes.getHeader("icon3").addValue(bean.getIcon3(),"");
				gdRes.getHeader("icon4").addValue(bean.getIcon4(),"");
				gdRes.getHeader("icon5").addValue(bean.getIcon5(),"");
				gdRes.getHeader("icon6").addValue(bean.getIcon6(),"");
				gdRes.getHeader("icon7").addValue(bean.getIcon7(),"");
				gdRes.getHeader("icon8").addValue(bean.getIcon8(),"");
				gdRes.getHeader("icon9").addValue(bean.getIcon9(),"");
				gdRes.getHeader("icon10").addValue(bean.getIcon10(),"");
				gdRes.getHeader("icon11").addValue(bean.getIcon11(),"");
				gdRes.getHeader("icon12").addValue(bean.getIcon12(),"");
				gdRes.getHeader("icon13").addValue(bean.getIcon13(),"");
				gdRes.getHeader("icon14").addValue(bean.getIcon14(),"");
				gdRes.getHeader("delYn").addValue(bean.getDelYn(),"");
				gdRes.getHeader("CHK").addValue("0","");
			}
			// 성공
			logger.debug("[[[ success ]]]");
			gdRes.setStatus("true");

		} 
		catch (Exception e) 
		{
			// 오류
			logger.debug("[[[ Exception ]]]");
			gdRes.setMessage(e.getMessage());
			gdRes.setStatus("false");
			logger.debug("", e);
		}
		
		request.setAttribute("wizeGridResult", gdRes);
		logger.debug("[[[ wiseGridResult ]]]");

		return "common/wiseGridResult";
	}

	/**
	 * 상품 아이콘 수정 처리
	 * @Description : 상품 아이콘 목록에 채크된 항목에 대해 수정처리
	 * @Method Name : updateProductIconList
	 * @param request
	 * @param response
	 * @return void
	 * @throws Exception
	 */
	@RequestMapping("product/updateProductIconList.do")
	public void updateProductIconList(HttpServletRequest request, HttpServletResponse response) throws Exception 
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
			List<PSCPPRD0015VO> beanList = new ArrayList<PSCPPRD0015VO>();
			PSCPPRD0015VO bean = null;
			
			int rowCount = gdReq.getHeader("num").getRowCount();
			logger.debug("::: updatePrdIconList set :::");
			logger.debug("mode ==>" + gdReq.getParam("mode") + "<==");
			logger.debug("rowCount ==>" + rowCount + "<==");

			for (int i = 0; i < rowCount; i++) 
			{
				bean = new PSCPPRD0015VO();
			
				bean.setProdCd(gdReq.getHeader("prodCd").getValue(i));
				bean.setDelYn(gdReq.getHeader("delYn").getValue(i));
				bean.setStrCd(gdReq.getHeader("strCd").getValue(i));
				bean.setIcon1(gdReq.getHeader("icon1").getValue(i));
				bean.setIcon2(gdReq.getHeader("icon2").getValue(i));
				bean.setIcon3(gdReq.getHeader("icon3").getValue(i));
				bean.setIcon4(gdReq.getHeader("icon4").getValue(i));
				bean.setIcon5(gdReq.getHeader("icon5").getValue(i));
				bean.setIcon6(gdReq.getHeader("icon6").getValue(i));
				bean.setIcon7(gdReq.getHeader("icon7").getValue(i));
				bean.setIcon8(gdReq.getHeader("icon8").getValue(i));
				bean.setIcon9(gdReq.getHeader("icon9").getValue(i));
				bean.setIcon10(gdReq.getHeader("icon10").getValue(i));
				bean.setIcon11(gdReq.getHeader("icon11").getValue(i));
				bean.setIcon12(gdReq.getHeader("icon12").getValue(i));
				bean.setIcon13(gdReq.getHeader("icon13").getValue(i));
				bean.setIcon14(gdReq.getHeader("icon14").getValue(i));
				bean.setRegId(gdReq.getParam("vendorId"));

				// 값 채크
				String resultStr = validate(bean);
				
				if (!"".equals(resultStr))
				{
					logger.debug("validate check fail (" + i + ")==>" + resultStr + "<==");
					message  = resultStr;
					gdRes.addParam("saveData",message);
					gdRes.setStatus("true");
				}
				
				beanList.add(bean);
			}
			
			int resultCnt = 0;
			logger.debug("::: updatePrdIconList begin :::");
			
			// 처리
			resultCnt = pscpprd0015Service.updatePrdIconList(beanList);

			// 처리 결과
			if (resultCnt > 0)
			{
				logger.debug("[[[ success ]]]");
				message  = messageSource.getMessage("msg.common.success.request", null, Locale.getDefault());
				gdRes.addParam("saveData",message);
				gdRes.setStatus("true");
			} 
			else 
			{
				logger.debug("[[[ fail ]]]");
				message  = messageSource.getMessage("msg.common.fail.request", null, Locale.getDefault());
				gdRes.addParam("saveData",message);
				gdRes.setStatus("true");//--true   gdRes.setStatus("false");
			}
		} 
		catch (Exception e) 
		{
			// 오류
			logger.debug("[[[ Exception ]]]");
			gdRes.setMessage(e.getMessage());
			gdRes.setStatus("false");
		}
		finally 
		{
			try 
			{
				// 자료구조를 전문으로 변경해 Write한다.
				logger.debug("[[[ OperateGridData write ]]]");
				OperateGridData.write(gdRes, out);
			} catch (Exception e) {
				logger.debug("[[[ finally Exception ]]]");
				logger.debug("",  e);
			}
		}
	}
	
	/*
	 * validate 체크
	 */
	public String validate(PSCPPRD0015VO bean) throws Exception 
	{
		if(StringUtils.isEmpty(bean.getProdCd())) {
			return "상품 코드가 없습니다.";
		}
		if(StringUtils.isEmpty(bean.getStrCd())) {
			return "점포 코드가 없습니다.";
		}
		if(StringUtils.isEmpty(bean.getDelYn())) {
			return "사용여부 코드가 없습니다.";
		}
		if(StringUtils.isEmpty(bean.getRegId())) {
			return "로그인 정보가 없습니다.";
		}
		
		return "";
	}

}
