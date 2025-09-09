package com.lottemart.epc.system.controller;


import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.util.StringUtil;

import lcn.module.framework.property.ConfigurationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import xlib.cmc.GridData;
import xlib.cmc.OperateGridData;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.zip.service.PEDPZIP0001Service;
import com.lottemart.epc.system.model.PSCMSYS0002VO;
import com.lottemart.epc.system.service.PSCMSYS0002Service;

/**
 * @Class Name : PSCMSYS0002Controller.java
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
public class PSCMSYS0002Controller 
{
	private static final Logger logger = LoggerFactory.getLogger(PSCMSYS0002Controller.class);

	@Autowired
	private PEDPZIP0001Service pedpzip0001Service;
	@Autowired
	private PSCMSYS0002Service pscmsys0002Service;
	@Autowired
	private ConfigurationService config;
	@Autowired
	private MessageSource messageSource; 

	/**
	 * 담당자관리 폼 페이지
	 * @Description : 담당자관리 초기페이지 로딩
	 * @Method Name : selectPersonInCharge
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping("system/selectPersonInCharge.do")
	public String selectPersonInCharge(HttpServletRequest request) throws Exception 
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
		
		return "system/PSCMSYS0002";
	}

	/**
	 * 담당자관리 목록
	 * @Description : 담당자관리 목록을 얻어서 그리드에 리턴
	 * @Method Name : selectPersonInChargeList
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping("system/selectPersonInChargeList.do")
	public String selectPersonInChargeList(HttpServletRequest request) throws Exception 
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
			
			paramMap.put("vendorId", gdReq.getParam("vendorId"));
			
			// 데이터 조회
			List<PSCMSYS0002VO> personInChargeInfo = pscmsys0002Service.selectPersonInChargeInfo(paramMap);			

			// 조회된 데이터 가 없는 경우의 처리
			if (personInChargeInfo == null || personInChargeInfo.size() == 0) 
			{
				gdRes.setStatus("false");
				request.setAttribute("wizeGridResult", gdRes);
				return "common/wiseGridResult";
			}

			// GridData 셋팅
			for (int i = 0; i < personInChargeInfo.size(); i++) 
			{
				PSCMSYS0002VO bean = (PSCMSYS0002VO) personInChargeInfo.get(i);
				//hidden column ----------------------------------------------
				gdRes.getHeader("vendorId" ).addValue(bean.getVendorId(), "");
				gdRes.getHeader("vendorSeq").addValue(bean.getVendorSeq(),"");
				//---------------------------------------------------- hidden//			
				
				gdRes.getHeader("checked"        ).addValue("0",                        "");
				gdRes.getHeader("vendorUserGubun").addValue(bean.getVendorUserGubun(),  "");
				gdRes.getHeader("utakNm"         ).addValue(bean.getUtakNm(),           "");
				gdRes.getHeader("utakDeptNm"     ).addValue(bean.getUtakDeptNm(),       "");
				gdRes.getHeader("utakPositionNm" ).addValue(bean.getUtakPositionNm(),   "");
				gdRes.getHeader("utakTelNo"      ).addValue(bean.getUtakTelNo(),        "");
				gdRes.getHeader("utakCellNo"     ).addValue(bean.getUtakCellNo(),       "");
				gdRes.getHeader("utakFaxNo"      ).addValue(bean.getUtakFaxNo(),        "");
				gdRes.getHeader("addr1Nm"	     ).addValue(bean.getAddr1Nm(),     	    "");
				gdRes.getHeader("addr2Nm"        ).addValue(bean.getAddr2Nm(),          "");
				gdRes.getHeader("valiYN"         ).addSelectedHiddenValue(bean.getValiYN());
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
	 * 담당자관리 등록, 수정 처리
	 * @Description : 협력업체번호(vendorId)와 협력업체Seq(vendorSeq)가 매치 되는 것이 있으면 update, 없으면 insert를 수행한다
	 * @Method Name : updatePersonInCharge
	 * @param request
	 * @param response
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping("system/updatePersonInCharge.do")
	public void updatePersonInCharge(HttpServletRequest request, HttpServletResponse response) throws Exception 
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
			String wiseGridData = request.getParameter("WISEGRID_DATA");
			GridData gdReq = OperateGridData.parse(wiseGridData);
			gdRes = OperateGridData.cloneResponseGridData(gdReq);
			
			// 모드셋팅
			gdRes.addParam("mode", gdReq.getParam("mode"));
		
			PSCMSYS0002VO pscmsys0002VO = null;
			
			// 처리수행
			int rowCount = gdReq.getHeader("vendorId").getRowCount();
			
			for (int i=0; i<rowCount; i++)
			{
				if (StringUtil.isEmpty(gdReq.getHeader("vendorId").getValue(i)))
				{
					message  = messageSource.getMessage("msg.common.error.required", new String[] { "협력사번호" }, Locale.getDefault());
					gdRes.setMessage(message);
					gdRes.setStatus("false");
					return; 
				}

				if (StringUtil.isEmpty(gdReq.getHeader("utakTelNo").getValue(i)))
				{
					message  = messageSource.getMessage("msg.common.error.required", new String[] { "전화번호" }, Locale.getDefault());
					gdRes.setMessage(message);
					gdRes.setStatus("false");
					return; 
				}
				
				if (StringUtil.isEmpty(gdReq.getHeader("valiYN").getComboHiddenValues()[gdReq.getHeader("valiYN").getSelectedIndex(i)]))
				{
					message  = messageSource.getMessage("msg.common.error.required", new String[] { "유효여부" }, Locale.getDefault());
					gdRes.setMessage(message);
					gdRes.setStatus("false");
					return;
				}
			}
			
			// header data VO객체에 셋팅 
			for (int i = 0; i < rowCount; i++) 
			{
				pscmsys0002VO = new PSCMSYS0002VO();

				//hidden column---------------------------------------------------------
				// merge의 조건으로만 쓰임
				pscmsys0002VO.setVendorId (gdReq.getHeader("vendorId" ).getValue(i));
				pscmsys0002VO.setVendorSeq(gdReq.getHeader("vendorSeq").getValue(i));
				//-------------------------------------------------------------- hidden//

				pscmsys0002VO.setUtakNm        (gdReq.getHeader("utakNm"        ).getValue(i));
				pscmsys0002VO.setUtakDeptNm    (gdReq.getHeader("utakDeptNm"    ).getValue(i));
				pscmsys0002VO.setUtakPositionNm(gdReq.getHeader("utakPositionNm").getValue(i));
				pscmsys0002VO.setUtakTelNo     (gdReq.getHeader("utakTelNo"     ).getValue(i));
				pscmsys0002VO.setUtakCellNo    (gdReq.getHeader("utakCellNo"    ).getValue(i));
				pscmsys0002VO.setUtakFaxNo     (gdReq.getHeader("utakFaxNo"     ).getValue(i));
				pscmsys0002VO.setAddr1Nm       (gdReq.getHeader("addr1Nm"       ).getValue(i));
				pscmsys0002VO.setAddr2Nm       (gdReq.getHeader("addr2Nm"       ).getValue(i));
				pscmsys0002VO.setValiYN	       (gdReq.getHeader("valiYN"        ).getComboHiddenValues()[gdReq.getHeader("valiYN").getSelectedIndex(i)]);
				pscmsys0002VO.setUserId        (gdReq.getHeader("vendorId"      ).getValue(i));
				
				pscmsys0002Service.updateListPersonInCharge(pscmsys0002VO);
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
				OperateGridData.write(gdRes, out); // 자료구조를 전문으로 변경해 Write한다.
			} 
			catch (Exception e) 
			{
				logger.debug("",e);
			}
		}
	}		

	@RequestMapping("system/zip/PEDPZIP0003List.do")
	public String list(HttpServletRequest request) throws Exception {
		return "system/PEDPZIP0003";
	}
	
	/**
	 * 우편번호 조회 
	 * Desc : 
	 * @Method Name : list
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("system/zip/PEDPZIP0003Select.do")
	public String selectList(@RequestParam Map<String,Object> map, ModelMap model,HttpServletRequest request) throws Exception {
		model.addAttribute("zipList",pedpzip0001Service.selectstreetCodeList(map));
		model.addAttribute("paramMap",map);
		
		return "system/PEDPZIP0003";
	}
}
