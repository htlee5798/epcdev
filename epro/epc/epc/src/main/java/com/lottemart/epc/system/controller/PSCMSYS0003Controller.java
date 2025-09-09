package com.lottemart.epc.system.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.paging.PaginationInfo;
import lcn.module.framework.property.ConfigurationService;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xlib.cmc.GridData;
import xlib.cmc.OperateGridData;

import com.lcnjf.util.StringUtil;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.model.PSCMCOM0002VO;
import com.lottemart.epc.common.util.LoginUtil;
import com.lottemart.epc.system.model.PSCMSYS0003VO;
import com.lottemart.epc.system.service.PSCMSYS0003Service;

/**
 * @Class Name : PSCMSYS0003Controller.java
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
public class PSCMSYS0003Controller 
{
	private static final Logger logger = LoggerFactory.getLogger(PSCMSYS0003Controller.class);

	@Autowired
	private PSCMSYS0003Service pscmsys0003Service;
	@Autowired
	private ConfigurationService config;
	@Autowired
	private MessageSource messageSource;

	/**
	 * 
	 * @see selectPartner
	 * @Method Name  : selectPartner
	 * @version    : 
	 * @Locaton    : com.lottemart.system.controller
	 * @Description : 협력사관리 화면을 호출한다
     * @param request
	 * @return String
     * @throws
	 */
	@RequestMapping("system/selectPartner.do")
	public String selectPartner(HttpServletRequest request) throws Exception 
	{
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if( epcLoginVO == null || epcLoginVO.getVendorId() == null )
		{
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}
		
		request.setAttribute("epcLoginVO", epcLoginVO);
		
		return "system/PSCMSYS0003";
	}
	
	/**
	 * 
	 * @see selectPartnerListList
	 * @Method Name  : selectPartnerList
	 * @version    :
	 * @Locaton    : com.lottemart.system.controller
	 * @Description : 협력사관리 리스트를 가져온다
     * @param request
	 * @return String
     * @throws
	 */
	
	@RequestMapping("system/selectPartnerList.do")
	public @ResponseBody Map selectPartnerList(HttpServletRequest request) throws Exception  {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		Map rtnMap = new HashMap<String, Object>();
		try  {
			DataMap param = new DataMap(request);

			// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
			if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
				logger.debug("===== > Sessiomcheck 실패<===== ");
			}

			request.setAttribute("epcLoginVO", epcLoginVO);
			logger.debug("===== >vendorId : "
					+ param.getString("searchVendorId") + "<=====");
			
			// 페이징 
			String rowsPerPage = StringUtil.null2str( (String) param.get("rowsPerPage"), config.getString("count.row.per.page"));

			int startRow = ((Integer .parseInt((String) param.get("currentPage")) - 1) * Integer .parseInt(rowsPerPage)) + 1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) - 1;

			param.put("currentPage", (String) param.get("currentPage"));
			param.put("startRow", Integer.toString(startRow));
			param.put("endRow", Integer.toString(endRow));
			param.put("rowsPerPage", rowsPerPage);

			//조건
			param.put("vendorId", param.getString("vendorId"));
			param.put("vendorNm", param.getString("vendorNm"));
			param.put("cono", epcLoginVO.getCono()[0]);
			
						 
			// 전체 조회 건수
			int totalCnt = pscmsys0003Service.selectPartnerTotalCnt(param);
			param.put("totalCount", Integer.toString(totalCnt));
	
			
			// 데이터 조회
			List<PSCMSYS0003VO> list = pscmsys0003Service.selectPartnerList(param);
			rtnMap = JsonUtils.convertList2Json((List) list, totalCnt, param.getString("currentPage"));


			rtnMap.put("result", true);
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("errMsg", e.getMessage());
		}

		return rtnMap;
	}
	
	
	/**
	 * 
	 * @see insertPartnerListPopUp
	 * @Method Name  : insertPartnerListPopUp
	 * @version    :
	 * @Locaton    : com.lottemart.system.controller
	 * @Description : 협력사관리 화면에서 등록 버튼을 누르면 등록팝업화면으로 띄운다
     * @param request
	 * @return String
     * @throws 
	 */
	@RequestMapping("system/insertPartnerPopUp.do")
	public String insertPartnerListPopUp(HttpServletRequest request) throws Exception 
	{
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);
		
		return "system/PSCMSYS000301";
	}
	
	@RequestMapping(value = "/system/insertPartnerPopup.do")
	public String insertPartnerPopup(@ModelAttribute("vo") PSCMSYS0003VO vo, Model model, HttpServletRequest request) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
		vo.setRegId(epcLoginVO.getCono()[0]); 
		try {
			pscmsys0003Service.insertPartnerPopup(vo);
			model.addAttribute("msg", "저장되었습니다.");
		} catch(Exception e) {
			model.addAttribute("msg", e.getMessage());
		}
		return "common/messageResult";
	}
	
	@RequestMapping("system/deletePartnerList.do")
	public  @ResponseBody JSONObject deletePartnerList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
		
		// 로그인 관련
		if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}
		
		DataMap dataMap = new DataMap(request);
		dataMap.put("cono", epcLoginVO.getCono()[0]);
		
		JSONObject jObj = new JSONObject();
		int resultCnt = 0;	
		
			try{			
				resultCnt = pscmsys0003Service.deletePartnerList(dataMap);
				
				if (resultCnt > 0) {
					jObj.put("Code", 1);
					jObj.put(
							"Message",
							resultCnt
									+ "건의 "
									+ messageSource.getMessage(
											"msg.common.success.request", null,
											Locale.getDefault()));
				} else {
					jObj.put("Code", 0);
					jObj.put("Message", messageSource.getMessage(
							"msg.common.fail.request", null, Locale.getDefault()));
				}
			} catch (Exception e) {
				jObj.put("Code", -1);
				jObj.put("Message", e.getMessage());
				// 처리오류
				logger.error("updateSelPontError==>", e);
			}
			return JsonUtils.getResultJson(jObj);
		}
		
	
	@RequestMapping("system/updatePartnerList.do")
	public @ResponseBody JSONObject updatePartnerList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);
		
		// 로그인 관련
		if (epcLoginVO == null || epcLoginVO.getVendorId() == null) {
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}
		
		DataMap dataMap = new DataMap(request);
		dataMap.put("cono", epcLoginVO.getCono()[0]);
		
		JSONObject jObj = new JSONObject();
		int resultCnt = 0;	
		try{			
			 resultCnt = pscmsys0003Service.updatePartnerList(dataMap);
			
			if (resultCnt > 0) {
				jObj.put("Code", 1);
				jObj.put(
						"Message",
						resultCnt
								+ "건의 "
								+ messageSource.getMessage(
										"msg.common.success.request", null,
										Locale.getDefault()));
			} else {
				jObj.put("Code", 0);
				jObj.put("Message", messageSource.getMessage(
						"msg.common.fail.request", null, Locale.getDefault()));
			}
		} catch (Exception e) {
			jObj.put("Code", -1);
			jObj.put("Message", e.getMessage());
			// 처리오류
			logger.error("updateSelPontError==>", e);
		}
		return JsonUtils.getResultJson(jObj);
	}
	
	
	/**
	 * 
	 * @see selectDeliveryAmtList
	 * @MethodName  : selectDeliveryAmtList
	 * @version    :
	 * @Locaton    : com.lottemart.epc.system.controller
	 * @Description : 배송비 등록 팝업화면을 띄울 때 배송비 리스트를 가져온다.
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	/*
	@RequestMapping("system/selectPartnerListPopup.do")
	public String selectDeliveryAmtList(HttpServletRequest request) throws Exception
	{
		GridData gdRes = new GridData();
		
		try
		{
			// 파라미터 획득
			String wiseGridData = request.getParameter("WISEGRID_DATA");
			GridData gdReq = OperateGridData.parse(wiseGridData);
			gdRes = OperateGridData.cloneResponseGridData(gdReq);
			
			// mode 셋팅
			gdRes.addParam("mode", gdReq.getParam("mode"));
			
			List<PSCMSYS0003VO> deliveryAmtList = pscmsys0003Service.selectDeliveryAmtList();
			int listSize = deliveryAmtList.size();
			
			// 조회된 데이터 가 없는 경우의 처리
			if (deliveryAmtList == null || listSize == 0)
			{
				// gdRes.setMessage("조회된 데이터가 없습니다.");
				gdRes.setStatus("false");
				request.setAttribute("wizeGridResult", gdRes);
				return "common/wiseGridResult";
			}
			
			PSCMSYS0003VO bean = null;
			
			// GridData 셋팅
			for (int i = 0; i < listSize; i++)
			{
				bean = (PSCMSYS0003VO) deliveryAmtList.get(i);
				
				gdRes.getHeader("checked")			.addValue("0"					,"");
				gdRes.getHeader("NUM")				.addValue(Integer.toString(i+1)	,"");
                gdRes.getHeader("APPLY_START_DY")   .addValue(""                    ,"");
                gdRes.getHeader("APPLY_END_DY")     .addValue(bean.getAPPLY_END_DY(),"");
                gdRes.getHeader("DELI_BASE_MIN_AMT").addValue(""					,"");
				gdRes.getHeader("DELI_BASE_MAX_AMT").addValue(""					,"");
				gdRes.getHeader("DELIVERY_AMT")		.addValue(bean.getDELIVERY_AMT(),"");
				gdRes.getHeader("PROD_CD")			.addValue(bean.getPROD_CD()		,"");
			}

			gdRes.setStatus("true");

		}
		catch (Exception e)
		{
			gdRes.setMessage(e.getMessage());
			gdRes.setStatus("false");
			logger.debug("", e);
		}

		request.setAttribute("wizeGridResult", gdRes);

		return "common/wiseGridResult";
	}
	*/
	/**
	 * 
	 * @see deletePartnerList
	 * @MethodName  : deletePartnerList
	 * @version    :
	 * @Locaton    : com.lottemart.epc.system.controller
	 * @Description : 배송비정보탭 에서 delete 셀 클릭 시 해당 로우를 삭제한다
	 * @param request
	 * @param response
	 * @return String
	 * @throws Exception
	 */
	/*
	@RequestMapping("system/deletePartnerList.do")
	public String deletePartnerList(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		GridData gdRes = null;
		
		try
		{
			// 요청객체 획득
			String    wiseGridData = request.getParameter("WISEGRID_DATA");
			GridData  gdReq        = OperateGridData.parse(wiseGridData);
			
			// 모드셋팅
			gdRes = OperateGridData.cloneResponseGridData(gdReq);
			gdRes.addParam("mode", "delete");
			
			PSCMSYS0003VO pscmsys0003VO = new PSCMSYS0003VO();
			String        vendorId      = gdReq.getParam("vendorId");
			
			pscmsys0003VO.setVENDOR_ID     (vendorId                        );
			pscmsys0003VO.setAPPLY_START_DY(gdReq.getParam("APPLY_START_DY"));

			int resultCnt = pscmsys0003Service.deleteDeliveryAmt(pscmsys0003VO);
			
			if (resultCnt > 0)
			{
				pscmsys0003Service.updateLatestApplyEndDy_AfterDelete(pscmsys0003VO);
				
				gdRes.addParam("saveData", messageSource.getMessage("msg.common.success.delete", null, Locale.getDefault()) );
				gdRes.setStatus("true");
				
				//---------------------------------------------
				// delete한 후에 이전 종료일자를 '99991231'로 모두 업데이트 한다
				//---------------------------------------------
				// 2009-02-04 ~ 2011-12-11 기간1
				// 2011-12-12 ~ 9999-12-31 기간2 ( 현재일 이후일때만 삭제 가능 )
				// 기간2 삭제시 기간1의 종료일을 9999-12-31일로 변경
//				pscmsys0003Service.updateRecoveryDate(pscmsys0003VO);
			}
		}
		catch (Exception e)
		{
			// 작업오류
			gdRes.setMessage(e.getMessage());
			gdRes.setStatus("false");
			logger.debug("", e);
		}
		
		request.setAttribute("wizeGridResult", gdRes);		
		return "common/wiseGridResult";
	}
	*/
	/**
	 * 
	 * @see updatePartnerListPopUp
	 * @MethodName  : updatePartnerListPopUp
	 * @version    :
	 * @Locaton    : com.lottemart.epc.system.controller
	 * @Description :  배송비관리 화면에서 수정링크 클릭 시 수정팝업화면을 띄운다
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	/*
	@RequestMapping("system/updatePartnerListPopUp.do")
	public String updatePartnerListPopUp(HttpServletRequest request) throws Exception 
	{
		String vendorId 	= request.getParameter("vendorId");
		String applyStartDy = request.getParameter("applyStartDy");

		request.setAttribute("vendorId", vendorId);
		request.setAttribute("applyStartDy", applyStartDy);
		
		return "system/PSCMSYS000302";
	}
	*/
	/**
	 * 
	 * @see selectDeliveryAmtUpdate
	 * @MethodName  : selectDeliveryAmtUpdate
	 * @version    :
	 * @Locaton    : com.lottemart.epc.system.controller
	 * @Description : 배송비 수정 팝업화면을 띄울 때 배송비 정보를 가져온다
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	/*
	@RequestMapping("system/selectPartnerUpdate.do")
	public String selectDeliveryAmtUpdate(HttpServletRequest request) throws Exception
	{
		GridData gdRes = new GridData();
		
		try
		{
			// 파라미터 획득
			String wiseGridData = request.getParameter("WISEGRID_DATA");
			GridData gdReq = OperateGridData.parse(wiseGridData);
			gdRes = OperateGridData.cloneResponseGridData(gdReq);
			
			// mode 셋팅
			gdRes.addParam("mode", gdReq.getParam("mode"));
			
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("vendorId",		gdReq.getParam("vendorId"));
			paramMap.put("applyStartDy",	gdReq.getParam("applyStartDy"));
			
			// 데이터 조회
			List<PSCMSYS0003VO> deliveryAmtInfoList = pscmsys0003Service.selectDeliveryAmtInfoList(paramMap);
			
			// 조회된 데이터 가 없는 경우의 처리
			if (deliveryAmtInfoList == null || deliveryAmtInfoList.size() == 0) {
				// gdRes.setMessage("조회된 데이터가 없습니다.");
				gdRes.setStatus("false");
				request.setAttribute("wizeGridResult", gdRes);
				
				return "common/wiseGridResult";
			}
			
			List<PSCMSYS0003VO> deliveryAmtList = pscmsys0003Service.selectDeliveryAmtList();
			
			// 조회된 데이터 가 없는 경우의 처리
			if (deliveryAmtList == null || deliveryAmtList.size() == 0) 
			{
				// gdRes.setMessage("조회된 데이터가 없습니다.");
				gdRes.setStatus("false");
				request.setAttribute("wizeGridResult", gdRes);
				
				return "common/wiseGridResult";
			}
			
			int amtList_size  = deliveryAmtList.size();
			int infoList_size = deliveryAmtInfoList.size();
			
			// GridData 셋팅
			int i,j;
			boolean hit=false;
			
			for (i = 0; i < amtList_size; i++) 
			{
				PSCMSYS0003VO bean = (PSCMSYS0003VO) deliveryAmtList.get(i);
				
				String deliAmt = bean.getDELIVERY_AMT();
				
				for (j=0; j < infoList_size; j++)
				{
					PSCMSYS0003VO infoBean = (PSCMSYS0003VO) deliveryAmtInfoList.get(j);
					
					if (infoBean.getDELIVERY_AMT().equals(deliAmt))
					{
						gdRes.getHeader("APPLY_START_DY"   ).addValue(dateFormat(infoBean.getAPPLY_START_DY()), "");
						gdRes.getHeader("APPLY_END_DY"     ).addValue(dateFormat(infoBean.getAPPLY_END_DY()),   "");
						gdRes.getHeader("DELI_BASE_MIN_AMT").addValue(infoBean.getDELI_BASE_MIN_AMT(),          "");
						gdRes.getHeader("DELI_BASE_MAX_AMT").addValue(infoBean.getDELI_BASE_MAX_AMT(),          "");
						hit = true;
					}
				}
				
				if (!hit)
				{
					gdRes.getHeader("APPLY_START_DY"   ).addValue("","");
					gdRes.getHeader("APPLY_END_DY"     ).addValue("","");
					gdRes.getHeader("DELI_BASE_MIN_AMT").addValue("","");
					gdRes.getHeader("DELI_BASE_MAX_AMT").addValue("","");
				}
				else
				{
					hit = false;
				}
				
				gdRes.getHeader("checked"     ).addValue("0",                    "");
				gdRes.getHeader("NUM"         ).addValue(Integer.toString(i+1),  "");		
				gdRes.getHeader("DELIVERY_AMT").addValue(bean.getDELIVERY_AMT(), "");
				gdRes.getHeader("PROD_CD"     ).addValue(bean.getPROD_CD(),      "");
			}

			gdRes.setStatus("true");

		} 
		catch (Exception e) 
		{
			gdRes.setMessage(e.getMessage());
			gdRes.setStatus("false");
			logger.debug("", e);
		}

		request.setAttribute("wizeGridResult", gdRes);

		return "common/wiseGridResult";
	}
	
	*/
	/**
	 * 
	 * @see getOnlyNumber
	 * @Locaton    : com.lottemart.epc.system.controller
	 * @MethodName  : getOnlyNumber
	 * @version    :
	 * @Description : 숫자 스트링만 append 하여 리턴한다
	 * @param str
	 * @return
	 */
	/*
	public static String getOnlyNumber(String str)
	{
		if(str == null)
		{
			return "";
		}
		
		StringBuffer out = new StringBuffer(512);
		char c;
		int str_length = str.length();
		
		for (int i = 0; i < str_length; i++)
		{
			c = str.charAt(i);
			if('0' <= c && c <= '9')
				out.append(c);
		}
		
		return out.toString();
	}
	*/
	/**
	 * 
	 * @see getOneDayBefore
	 * @Locaton    : com.lottemart.epc.system.controller
	 * @MethodName  : getOneDayBefore
	 * @Description : string 형의 날짜를 받아서 하루 전 날짜를 리턴
	 * @param String
	 * @return String
	 */
	/*
	private static String getOneDayBefore(String str)
	{
		long time = DateUtil.string2Date(str).getTime();
		time -= 24*60*60*1000; //하루 더하기

		Date oneDayBefore  = new Date();
		oneDayBefore.setTime(time);
		
		return DateUtil.date2String(oneDayBefore);
	}
	*/
	/**
	 * 날짜 포맷 (YYYY-MM-DD)
	 * Desc : dateFormat
	 * @Method Name : com.lottemart.epc.system.controller
	 * @Description : yyyymmdd 형의 날짜를 받아서 yyyy-mm-dd 형으로 변환하여 리턴
	 * @param String (format : yyyymmdd)
	 * @return String (format : yyyy-mm-dd)
	 */
	/*
	static private String dateFormat(String date)
	{
		if (StringUtils.isEmpty(date)) {
			return "";
		}
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append(date.substring(0, 4)).append("-")
		         .append(date.substring(4, 6)).append("-")
		         .append(date.substring(6, 8));

		return strBuffer.toString();
	}
	*/
	/**
	 * 
	 * @see dateGreaterThan
	 * @Locaton    : com.lottemart.bos.system.basicInfo.controller
	 * @MethodName  : dateGreaterThan
	 * @Description : if ( source 가 target 보다 큰 날짜이면 ) true
	 * 				  else false
	 * @param String (format : yyyymmdd)
	 * @param String (format : yyyymmdd)
	 * @return boolean
	 */
	/*
	static private boolean dateGreaterThan(String source, String target)
	{
		if(source==null)
			return false;
		
		if(source.equals(target))
			return false;
	
		int length = source.length();
		int i;
		
		for(i=0; i< length; i++)
		{
			if(source.charAt(i) > target.charAt(i)) 
				return true;
			if(source.charAt(i) < target.charAt(i))
				return false;
		}
		
		return false;
	}
	*/
}
