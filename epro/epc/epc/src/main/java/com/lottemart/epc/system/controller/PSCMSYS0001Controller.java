package com.lottemart.epc.system.controller;


import java.io.PrintWriter;
import java.util.Date;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import xlib.cmc.GridData;
import xlib.cmc.OperateGridData;

import com.lcnjf.util.DateUtil;
import com.lcnjf.util.StringUtil;
import com.lottemart.common.code.service.CommonCodeService;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.board.model.PSCPBRD0012VO;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.util.SecureUtil;
import com.lottemart.epc.system.model.PSCMSYS0001VO;
import com.lottemart.epc.system.service.PSCMSYS0001Service;

/**
 * @Class Name : PSCMSYS0001Controller.java
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
public class PSCMSYS0001Controller 
{
	private static final Logger logger = LoggerFactory.getLogger(PSCMSYS0001Controller.class);

	@Autowired
	private CommonCodeService commonCodeService;	
	@Autowired
	private PSCMSYS0001Service pscmsys0001Service;
	@Autowired
	private ConfigurationService config;
	@Autowired
	private MessageSource messageSource;

	/**
	 * 
	 * @see selectDeliveryChargePolicy
	 * @Method Name  : insertDeliveryChargePolicyPopUp
	 * @version    : 
	 * @Locaton    : com.lottemart.system.controller
	 * @Description : 배송비정책관리 화면을 호출한다
     * @param request
	 * @return String
     * @throws
	 */
	@RequestMapping("system/selectDeliveryChargePolicy.do")
	public String selectDeliveryChargePolicy(HttpServletRequest request) throws Exception 
	{
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		// Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동
		if( epcLoginVO == null || epcLoginVO.getVendorId() == null )
		{
			logger.debug("==>>Session check 실패 --> 에러페이지 혹은 로그인패이지로 이동<<==");
		}
		
		// 배송,반품 공통코드 조회
		List<DataMap> codeList = commonCodeService.getCodeList("DE020");
		
		request.setAttribute("epcLoginVO", epcLoginVO);
		request.setAttribute("codeList", codeList);
		
		return "system/PSCMSYS0001";
	}
	
	/**
	 * 
	 * @see selectDeliveryChargePolicyList
	 * @Method Name  : insertDeliveryChargePolicyPopUp
	 * @version    :
	 * @Locaton    : com.lottemart.system.controller
	 * @Description : 배송비정책관리 화면에서 배송비정보 리스트를 가져온다
     * @param request
	 * @return String
     * @throws
	 */
	@RequestMapping("system/selectDeliveryChargePolicyList.do")
	public String selectDeliveryChargePolicyList(HttpServletRequest request) throws Exception 
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
			
			// 페이징 ------------------------------------------------------
			String rowsPerPage = StringUtil.null2str(gdReq.getParam("rowsPerPage"), config.getString("count.row.per.page"));
			
			int startRow = ((Integer.parseInt(gdReq.getParam("currentPage")) - 1) * Integer.parseInt(rowsPerPage)) + 1;
			int endRow = startRow + Integer.parseInt(rowsPerPage) - 1;
			//------------------------------------------------------페이징//

			String vendorId = gdReq.getParam("vendorId");
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("vendorId", vendorId);
			paramMap.put("deliDivnCd", gdReq.getParam("deliDivnCd"));
			
			// 페이징 ------------------------------------------------------
			paramMap.put("startRow", Integer.toString(startRow));
			paramMap.put("endRow",   Integer.toString(endRow)  );
			//------------------------------------------------------페이징//
			
			// 전체 조회 건수
			int totalCnt = pscmsys0001Service.selectDeliveryAmtInfoTotalCnt(paramMap);
			
			// 페이징 ------------------------------------------------------
			gdRes.addParam("totalCount",  Integer.toString(totalCnt)   );
			gdRes.addParam("rowsPerPage", rowsPerPage                  );
			gdRes.addParam("currentPage", gdReq.getParam("currentPage"));
			//------------------------------------------------------페이징//
			
			// 데이터 조회
			List<PSCMSYS0001VO> deliveryAmtInfoList = pscmsys0001Service.selectDeliveryAmtInfoList(paramMap);
			int listSize = deliveryAmtInfoList.size();

            String currentDay = DateUtil.getCurrentDay();

			// 조회된 데이터 가 없는 경우의 처리
			if (deliveryAmtInfoList == null || listSize == 0) 
			{
				gdRes.setStatus("false");
				request.setAttribute("wizeGridResult", gdRes);
				
				return "common/wiseGridResult";
			}
			
			PSCMSYS0001VO bean = null;
            String modify = messageSource.getMessage("button.common.modify", null, Locale.getDefault());
			String delete = messageSource.getMessage("button.common.delete", null, Locale.getDefault());

			// GridData 셋팅
			for (int i = 0; i < listSize; i++) 
			{
				bean = (PSCMSYS0001VO) deliveryAmtInfoList.get(i);

				gdRes.getHeader("APPLY_START_DY"   ).addValue(bean.getAPPLY_START_DY(),    "");
				gdRes.getHeader("APPLY_END_DY"     ).addValue(bean.getAPPLY_END_DY(),      "");
				gdRes.getHeader("DELI_BASE_MIN_AMT").addValue(bean.getDELI_BASE_MIN_AMT(), "");
				gdRes.getHeader("DELI_BASE_MAX_AMT").addValue(bean.getDELI_BASE_MAX_AMT(), "");
				gdRes.getHeader("DELIVERY_AMT"     ).addValue(bean.getDELIVERY_AMT(),      "");
				
				gdRes.getHeader("DELI_DIVN_NM"     ).addValue(bean.getDELI_DIVN_NM(),      "");
				gdRes.getHeader("DELI_DIVN_CD"     ).addValue(bean.getDELI_DIVN_CD(),      "");

				logger.debug("applyStDay ==>" + Integer.parseInt(bean.getAPPLY_START_DY())  + "<==");
				logger.debug("currentDay ==>" + Integer.parseInt(getOnlyNumber(currentDay)) + "<==");
				if (dateGreaterThan(bean.getAPPLY_START_DY(), getOnlyNumber(currentDay)))
				{
					logger.debug("::: Yes :::");
					gdRes.getHeader("DO_MODIFY").addValue(modify, "");
					gdRes.getHeader("DO_DELETE").addValue(delete, "");
				}
				else
				{
					logger.debug("::: No :::");
					gdRes.getHeader("DO_MODIFY").addValue("", "");
					gdRes.getHeader("DO_DELETE").addValue("", "");
				}
			}
			
			DataMap dataMap = pscmsys0001Service.selectLatestApplyStartDy(paramMap);
			
			if (dataMap != null)
			{
				String maxApplyStartDy = dateFormat(dataMap.getString("MAX_APPLY_START_DY")); 
				request.setAttribute("maxApplyStartDy", maxApplyStartDy);
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
	
	/**
	 * 
	 * @see insertDeliveryChargePolicyPopUp
	 * @Method Name  : insertDeliveryChargePolicyPopUp
	 * @version    :
	 * @Locaton    : com.lottemart.system.controller
	 * @Description : 배송비정책관리 화면에서 등록 버튼을 누르면 등록팝업화면으로 띄운다
     * @param request
	 * @return String
     * @throws 
	 */
	@RequestMapping("system/insertDeliveryChargePolicyPopUp.do")
	public String insertDeliveryChargePolicyPopUp(HttpServletRequest request) throws Exception 
	{
		String rtnUrl = "system/PSCMSYS000101";
		String vendorId = SecureUtil.stripXSS(request.getParameter("vendorId"));
		String deliDivnCd = SecureUtil.stripXSS(request.getParameter("deliDivnCd"));
		String let3Ref = SecureUtil.stripXSS(request.getParameter("let3Ref"));
		
		Map<String, String> paramMap = new HashMap<String, String>();
		
		paramMap.put("vendorId", vendorId);
		paramMap.put("deliDivnCd", deliDivnCd);
		
		DataMap dataMap = pscmsys0001Service.selectLatestApplyStartDy(paramMap);
		
		if(!"10".equals(deliDivnCd)){
			rtnUrl = "system/PSCMSYS000103";
		}

		if (dataMap!=null)
		{
			String maxApplyStartDy = dateFormat(dataMap.getString("MAX_APPLY_START_DY")); 
			request.setAttribute("maxApplyStartDy", maxApplyStartDy);
		}
		request.setAttribute("vendorId", vendorId);
		request.setAttribute("deliDivnCd", deliDivnCd);
		request.setAttribute("let3Ref", let3Ref);
	
		return rtnUrl;
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
	@RequestMapping("system/selectDeliveryAmtList.do")
	public String selectDeliveryAmtList(HttpServletRequest request) throws Exception
	{
		GridData gdRes = new GridData();
		
		try
		{
			// 파라미터 획득
			String wiseGridData = request.getParameter("WISEGRID_DATA");
			GridData gdReq = OperateGridData.parse(wiseGridData);
			gdRes = OperateGridData.cloneResponseGridData(gdReq);
			
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("let3Ref", gdReq.getParam("let3Ref"));
			
			// mode 셋팅
			gdRes.addParam("mode", gdReq.getParam("mode"));
			
			List<PSCMSYS0001VO> deliveryAmtList = pscmsys0001Service.selectDeliveryAmtList(paramMap);
			int listSize = deliveryAmtList.size();
			
			// 조회된 데이터 가 없는 경우의 처리
			if (deliveryAmtList == null || listSize == 0)
			{
				// gdRes.setMessage("조회된 데이터가 없습니다.");
				gdRes.setStatus("false");
				request.setAttribute("wizeGridResult", gdRes);
				return "common/wiseGridResult";
			}
			
			PSCMSYS0001VO bean = null;
			
			// GridData 셋팅
			for (int i = 0; i < listSize; i++)
			{
				bean = (PSCMSYS0001VO) deliveryAmtList.get(i);
				
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
	
	/**
	 * 
	 * @see getMaxApplyStartDyBeforeInsert
	 * @Locaton    : com.lottemart.epc.system.controller
	 * @MethodName  : getMaxApplyStartDyBeforeInsert
	 * @version    :
	 * @Description : 배송비 등록 팝업 화면 > 저장버튼 클릭시 insert 하기 전에 maxApplyStartDy(가장최근에 등록한 적용시작일자)를 가져온다
	 * 저장하려는 적용시작일자(applyStartDy)와 비교하여 applyStartDy > maxApplyStartDy 이어야 등록할 수 있다  
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("system/getMaxApplyStartDyBeforeInsert.do")
	public String getMaxApplyStartDyBeforeInsert(HttpServletRequest request, HttpServletResponse response) throws Exception
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
		
		String vendorId = request.getParameter("vendorId");
		String deliDivnCd = request.getParameter("deliDivnCd");
		
		Map<String, String> paramMap = new HashMap<String, String>();
		
		paramMap.put("vendorId", vendorId);
		paramMap.put("deliDivnCd", deliDivnCd);
		
		DataMap dataMap = pscmsys0001Service.selectLatestApplyStartDy(paramMap);
		
		if (dataMap != null)
		{
			String maxApplyStartDy = dateFormat(dataMap.getString("MAX_APPLY_START_DY")); 
			request.setAttribute("maxApplyStartDy", maxApplyStartDy);
		}
		
		// 배송,반품 공통코드 조회
		List<DataMap> codeList = commonCodeService.getCodeList("DE020");
		
		request.setAttribute("vendorId",vendorId );
		request.setAttribute("deliDivnCd",deliDivnCd );
		request.setAttribute("isInsert", "true");
		request.setAttribute("codeList",codeList );
		
		return "system/PSCMSYS0001";
	}
	
	/**
	 * 
	 * @see insertDeliveryChargePolicy.do
	 * @Method Name  : insertDeliveryChargePolicy
	 * @version    :
	 * @Locaton    : com.lottemart.epc.system.controller
	 * @Description : 배송비 등록 팝업화면 > 저장버튼 클릭 시 적용시작일자와 배송비를 등록한다
     * @param request
     * @param response
     * @throws Exception
	 */
	@RequestMapping("system/insertDeliveryAmt.do")
	public void insertDeliveryAmt(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		GridData gdRes = new GridData();
		
		// Encode Type을 UTF-8로 변환한다.
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String message = "";
		
		try 
		{
			String 	 wiseGridData = request.getParameter("WISEGRID_DATA");
			GridData gdReq        = OperateGridData.parse(wiseGridData);
			gdRes = OperateGridData.cloneResponseGridData(gdReq);
			
			// 모드셋팅
			gdRes.addParam("mode", gdReq.getParam("mode"));
			
            //validation check
			String vendorId	 	  = gdReq.getParam("vendorId");
			String deliDivnCd	 	  = gdReq.getParam("deliDivnCd");
			String applyStartDy   = gdReq.getParam("applyStartDy");
			String oneDayBefore = getOneDayBefore(applyStartDy);
			
			Map<String, String> paramMap = new HashMap<String, String>();
			
			paramMap.put("vendorId", vendorId);
			paramMap.put("deliDivnCd", deliDivnCd);
			paramMap.put("applyStartDy", getOnlyNumber(applyStartDy));
			
			DataMap CHECK_GUBUN = pscmsys0001Service.validateDeliveryAmtInsert(paramMap);
			
			if (CHECK_GUBUN != null)
			{
				String checkMsg = CHECK_GUBUN.getSafeString("CHECK_GUBUN");
				
				if ("ERROR1".equals(checkMsg))
				{
					logger.debug("[[[ error ]]]");
					gdRes.setMessage("새 적용시작일자는 마지막 적용일시작일자 보다 커야합니다");
					gdRes.setStatus("false");
					return;
				}	
			}
			
			paramMap.put("oneDayBefore", getOnlyNumber(oneDayBefore));
			
			//---------------------------------------------
			// insert 하기 전에 이전 종료일자(99991231)를 모두 업데이트 한다
			//---------------------------------------------
			// ex) 등록할 적용시작일자(applyStartDy) 그 하루 전 날 일자(ONE_DAY_BEFORE)
			//     applyStartDy(20111204), ONE_DAY_BEFORE(20111203)
			//     99991231 -> 20111203 로 업데이트
			pscmsys0001Service.updateLatestApplyEndDy(paramMap);
			
			// 처리수행
			int rowCount = gdReq.getHeader("NUM").getRowCount();
			
			logger.debug("rowCount===================="+rowCount);
			// header data VO객체에 셋팅 
			for (int i = 0; i < rowCount; i++)
			{
				paramMap.put("vendorId", vendorId);
				paramMap.put("applyStartDy", getOnlyNumber(applyStartDy));
				paramMap.put("applyEndDy", "99991231");
				paramMap.put("oneDayBefore", getOnlyNumber(oneDayBefore));
				paramMap.put("deliBaseMinAmt", gdReq.getHeader("DELI_BASE_MIN_AMT").getValue(i));
				paramMap.put("deliBaseMaxAmt", gdReq.getHeader("DELI_BASE_MAX_AMT").getValue(i));
				paramMap.put("prodCd", gdReq.getHeader("PROD_CD").getValue(i));
				paramMap.put("deliveryAmt", gdReq.getHeader("DELIVERY_AMT").getValue(i));
				paramMap.put("regId", vendorId);
				paramMap.put("modId", vendorId);
				
				pscmsys0001Service.insertDeliveryAmt(paramMap);
			}
			
			message  = messageSource.getMessage("msg.common.success.request", null, Locale.getDefault());
			gdRes.addParam("saveData",message);
			gdRes.setStatus("true");
		}
		catch (Exception e)
		{
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
				logger.debug("",e);
			}
		}
	}
	
	/**
	 * 
	 * @see insertDeliveryAmt20.do
	 * @Method Name  : insertDeliveryAmt20
	 * @version    :
	 * @Locaton    : com.lottemart.epc.system.controller
	 * @Description : 반품비,교환비 등록
     * @param request
     * @param response
     * @throws Exception
	 */
	@RequestMapping(value = "/system/insertDeliveryAmt20.do")
	public String insertDeliveryAmt20(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		
		String deliveryAmt20 = request.getParameter("deliveryAmt20");
		String deliveryAmt30 = request.getParameter("deliveryAmt30");
		String vendorId	 	= request.getParameter("vendorId");
		String deliDivnCd	= request.getParameter("deliDivnCd");
		String applyStartDy = request.getParameter("applyStartDy");
		String oneDayBefore = getOneDayBefore(applyStartDy);
		
		paramMap.put("vendorId", vendorId);
		paramMap.put("deliDivnCd", deliDivnCd);
		paramMap.put("applyStartDy", getOnlyNumber(applyStartDy));
		
		DataMap CHECK_GUBUN = pscmsys0001Service.validateDeliveryAmtInsert(paramMap);
		
		if (CHECK_GUBUN != null)
		{
			String checkMsg = CHECK_GUBUN.getSafeString("CHECK_GUBUN");
			
			if ("ERROR1".equals(checkMsg))
			{
				logger.debug("[[[ error ]]]");
				model.addAttribute("msg", "새 적용시작일자는 마지막 적용일시작일자 보다 커야합니다.");
				return "common/messageResult";
			}	
		}
		
		paramMap.put("oneDayBefore", getOnlyNumber(oneDayBefore));
		
		//---------------------------------------------
		// insert 하기 전에 이전 종료일자(99991231)를 모두 업데이트 한다
		//---------------------------------------------
		// ex) 등록할 적용시작일자(applyStartDy) 그 하루 전 날 일자(ONE_DAY_BEFORE)
		//     applyStartDy(20111204), ONE_DAY_BEFORE(20111203)
		//     99991231 -> 20111203 로 업데이트
		pscmsys0001Service.updateLatestApplyEndDy(paramMap);
		
		for(int i=0; i<2; i++){
			
			if(i == 0){
				paramMap.put("prodCd", "L999999999998");
				paramMap.put("deliveryAmt", deliveryAmt20);
				paramMap.put("applyEndDy", "99991229");
				paramMap.put("deliDivnCd", "20");
			}else{
				paramMap.put("prodCd", "L999999999999");
				paramMap.put("deliveryAmt", deliveryAmt30);
				paramMap.put("applyEndDy", "99991230");
				paramMap.put("deliDivnCd", "30");
			}
			
			paramMap.put("vendorId", vendorId);
			paramMap.put("applyStartDy", getOnlyNumber(applyStartDy));
			
			paramMap.put("oneDayBefore", getOnlyNumber(oneDayBefore));
			paramMap.put("deliBaseMinAmt", "0");
			paramMap.put("deliBaseMaxAmt", "0");
			paramMap.put("regId", vendorId);
			paramMap.put("modId", vendorId);
			
			pscmsys0001Service.insertDeliveryAmt(paramMap);
		}
		
		model.addAttribute("msg", "저장되었습니다.");
		
		return "common/messageResult";
	}
	
	/**
	 * 
	 * @see deleteDeliveryChargePolicy
	 * @MethodName  : deleteDeliveryChargePolicy
	 * @version    :
	 * @Locaton    : com.lottemart.epc.system.controller
	 * @Description : 배송비정보탭 에서 delete 셀 클릭 시 해당 로우를 삭제한다
	 * @param request
	 * @param response
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping("system/deleteDeliveryChargePolicy.do")
	public String deleteDeliveryChargePolicy(HttpServletRequest request, HttpServletResponse response) throws Exception
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
			
			String vendorId = gdReq.getParam("vendorId");
			String deliDivnCd = gdReq.getParam("deliDivnCd");
			

			Map<String, String> paramMap = new HashMap<String, String>();
			
			paramMap.put("vendorId", vendorId);
			paramMap.put("applyStartDy", gdReq.getParam("APPLY_START_DY"));
			paramMap.put("deliDivnCd", deliDivnCd);
			
			int resultCnt = pscmsys0001Service.deleteDeliveryAmt(paramMap);
			
			if (resultCnt > 0)
			{
				pscmsys0001Service.updateLatestApplyEndDy_AfterDelete(paramMap);
				
				gdRes.addParam("saveData", messageSource.getMessage("msg.common.success.delete", null, Locale.getDefault()) );
				gdRes.setStatus("true");
				
				//---------------------------------------------
				// delete한 후에 이전 종료일자를 '99991231'로 모두 업데이트 한다
				//---------------------------------------------
				// 2009-02-04 ~ 2011-12-11 기간1
				// 2011-12-12 ~ 9999-12-31 기간2 ( 현재일 이후일때만 삭제 가능 )
				// 기간2 삭제시 기간1의 종료일을 9999-12-31일로 변경
//				pscmsys0001Service.updateRecoveryDate(pscmsys0001VO);
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
	
	/**
	 * 
	 * @see updateDeliveryChargePolicyPopUp
	 * @MethodName  : updateDeliveryChargePolicyPopUp
	 * @version    :
	 * @Locaton    : com.lottemart.epc.system.controller
	 * @Description :  배송비관리 화면에서 수정링크 클릭 시 수정팝업화면을 띄운다
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping("system/updateDeliveryChargePolicyPopUp.do")
	public String updateDeliveryChargePolicyPopUp(HttpServletRequest request) throws Exception 
	{
		String rtnUrl = "system/PSCMSYS000102";
		String vendorId 	= SecureUtil.stripXSS(request.getParameter("vendorId"));
		String deliDivnCd 	= SecureUtil.stripXSS(request.getParameter("deliDivnCd"));
		String let3Ref 	= SecureUtil.stripXSS(request.getParameter("let3Ref"));
		String applyStartDy = request.getParameter("applyStartDy");

		if(!"10".equals(deliDivnCd)){
			rtnUrl = "system/PSCMSYS000104";
			
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("vendorId",		vendorId);
			paramMap.put("deliDivnCd",		deliDivnCd);
			paramMap.put("let3Ref", 		let3Ref);
			paramMap.put("applyStartDy",	applyStartDy);
			
			// 데이터 조회
			List<PSCMSYS0001VO> deliveryAmtInfoList = pscmsys0001Service.selectDeliveryAmtInfoList(paramMap);
			
			request.setAttribute("deliveryAmtInfoList", deliveryAmtInfoList);
		}
		
		request.setAttribute("vendorId", vendorId);
		request.setAttribute("deliDivnCd", deliDivnCd);
		request.setAttribute("let3Ref", let3Ref);
		request.setAttribute("applyStartDy", applyStartDy);
		
		return rtnUrl;
	}
	
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
	@RequestMapping("system/selectDeliveryAmtUpdate.do")
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
			paramMap.put("deliDivnCd",		gdReq.getParam("deliDivnCd"));
			paramMap.put("let3Ref", 		gdReq.getParam("let3Ref"));
			paramMap.put("applyStartDy",	gdReq.getParam("applyStartDy"));
			
			
			// 데이터 조회
			List<PSCMSYS0001VO> deliveryAmtInfoList = pscmsys0001Service.selectDeliveryAmtInfoList(paramMap);
			
			// 조회된 데이터 가 없는 경우의 처리
			if (deliveryAmtInfoList == null || deliveryAmtInfoList.size() == 0) {
				// gdRes.setMessage("조회된 데이터가 없습니다.");
				gdRes.setStatus("false");
				request.setAttribute("wizeGridResult", gdRes);
				
				return "common/wiseGridResult";
			}
			
			
			List<PSCMSYS0001VO> deliveryAmtList = pscmsys0001Service.selectDeliveryAmtList(paramMap);
			
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
				PSCMSYS0001VO bean = (PSCMSYS0001VO) deliveryAmtList.get(i);
				
				String deliAmt = bean.getDELIVERY_AMT();
				
				for (j=0; j < infoList_size; j++)
				{
					PSCMSYS0001VO infoBean = (PSCMSYS0001VO) deliveryAmtInfoList.get(j);
					
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
	
	/**
	 * 
	 * @see updateDeliveryAmt
	 * @MethodName  : updateDeliveryAmt
	 * @version    :
	 * @Locaton    : com.lottemart.epc.system.controller
	 * @Description : 배송비정보탭 수정팝업창에서 저장버튼 클릭 시 변경내용을 저장한다
	 * @param request
	 * @return void
	 * @throws Exception
	 */
	@RequestMapping("system/updateDeliveryAmt.do")
	public void updateDeliveryAmt(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		GridData gdRes = new GridData();
		
		// Encode Type을 UTF-8로 변환한다.
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String message = "";
		
		try 
		{
			String wiseGridData = request.getParameter("WISEGRID_DATA");
			GridData gdReq = OperateGridData.parse(wiseGridData);
			gdRes = OperateGridData.cloneResponseGridData(gdReq);
			
			// 모드셋팅
			gdRes.addParam("mode", gdReq.getParam("mode"));
			
			Map<String, String> paramMap = new HashMap<String, String>();
			
			//validation check
			String vendorId	 	  = gdReq.getParam("vendorId");
			String deliDivnCd 	  = gdReq.getParam("deliDivnCd");
			String applyStartDy   = gdReq.getParam("APPLY_START_DY");
			String oneDayBefore = getOneDayBefore(applyStartDy);
			
			paramMap.put("vendorId", vendorId);
			paramMap.put("deliDivnCd", deliDivnCd);
			paramMap.put("applyStartDy", getOnlyNumber(applyStartDy));
			
			// 처리수행
			int rowCount = gdReq.getHeader("NUM").getRowCount();
			
			// header data VO객체에 셋팅 
			for (int i = 0; i < rowCount; i++) 
			{
				paramMap.put("deliveryAmt", gdReq.getHeader("DELIVERY_AMT").getValue(i));
				paramMap.put("applyEndDy", "99991231");
				
				if (pscmsys0001Service.selectTargetCnt(paramMap) > 0 )
				{
					//존재하면 update
					paramMap.put("deliBaseMinAmt", gdReq.getHeader("DELI_BASE_MIN_AMT").getValue(i));
					paramMap.put("deliBaseMaxAmt", gdReq.getHeader("DELI_BASE_MAX_AMT").getValue(i));
					pscmsys0001Service.updateDeliveryAmt(paramMap);
				}
				else
				{
					//존재하지 않으면 insert
					paramMap.put("oneDayBefore", getOnlyNumber(oneDayBefore));
					paramMap.put("deliBaseMinAmt", gdReq.getHeader("DELI_BASE_MIN_AMT").getValue(i));
					paramMap.put("deliBaseMaxAmt", gdReq.getHeader("DELI_BASE_MAX_AMT").getValue(i));
					paramMap.put("prodCd", gdReq.getHeader("PROD_CD").getValue(i));
					paramMap.put("regId", vendorId);
					paramMap.put("modId", vendorId);
					
					pscmsys0001Service.insertDeliveryAmt(paramMap);
				}
			}
			
			message  = messageSource.getMessage("msg.common.success.request", null, Locale.getDefault());
			gdRes.addParam("saveData",message);
			gdRes.setStatus("true");
		}
		catch (Exception e) 
		{
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
				logger.debug("",e);
			}
		}
	}
	
	/**
	 * 
	 * @see updateDeliveryAmt20.do
	 * @Method Name  : updateDeliveryAmt20
	 * @version    :
	 * @Locaton    : com.lottemart.epc.system.controller
	 * @Description : 반품비,교환비 수정
     * @param request
     * @param response
     * @throws Exception
	 */
	@RequestMapping(value = "/system/updateDeliveryAmt20.do")
	public String updateDeliveryAmt20(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		
		String deliveryAmt20 = request.getParameter("deliveryAmt20");
		String deliveryAmt30 = request.getParameter("deliveryAmt30");
		String vendorId	 	= request.getParameter("vendorId");
		String applyStartDy = request.getParameter("applyStartDy");
		
		
		for(int i=0; i<2; i++){
			
			if(i == 0){
				paramMap.put("deliDivnCd", "20");
				paramMap.put("deliveryAmt", deliveryAmt20);
			}else{
				paramMap.put("deliDivnCd", "30");
				paramMap.put("deliveryAmt", deliveryAmt30);
			}
			
			paramMap.put("vendorId", vendorId);
			paramMap.put("applyStartDy", applyStartDy);
			paramMap.put("modId", vendorId);
			
			pscmsys0001Service.updateDeliveryAmt20(paramMap);
		}
		
		model.addAttribute("msg", "저장되었습니다.");
		
		return "common/messageResult";
	}
	
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
	
	/**
	 * 
	 * @see getOneDayBefore
	 * @Locaton    : com.lottemart.epc.system.controller
	 * @MethodName  : getOneDayBefore
	 * @Description : string 형의 날짜를 받아서 하루 전 날짜를 리턴
	 * @param String
	 * @return String
	 */
	private static String getOneDayBefore(String str)
	{
		long time = DateUtil.string2Date(str).getTime();
		time -= 24*60*60*1000; //하루 더하기

		Date oneDayBefore  = new Date();
		oneDayBefore.setTime(time);
		
		return DateUtil.date2String(oneDayBefore);
	}
	
	/**
	 * 날짜 포맷 (YYYY-MM-DD)
	 * Desc : dateFormat
	 * @Method Name : com.lottemart.epc.system.controller
	 * @Description : yyyymmdd 형의 날짜를 받아서 yyyy-mm-dd 형으로 변환하여 리턴
	 * @param String (format : yyyymmdd)
	 * @return String (format : yyyy-mm-dd)
	 */
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
	
}
