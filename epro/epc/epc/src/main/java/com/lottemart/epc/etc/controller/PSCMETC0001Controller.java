package com.lottemart.epc.etc.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.util.StringUtil;
import lcn.module.framework.property.ConfigurationService;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import xlib.cmc.GridData;
import xlib.cmc.OperateGridData;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.etc.model.PSCMETC0001VO;
import com.lottemart.epc.etc.service.PSCMETC0001Service;

@Controller
public class PSCMETC0001Controller {
	
	private static final Logger logger = LoggerFactory.getLogger(PSCMETC0001Controller.class);
	
	@Autowired
	private ConfigurationService config;	
	
	@Autowired
	private PSCMETC0001Service pscmetc0001Service;
	
	
	@RequestMapping(value = "etc/codeMainView.do")
	public String codeMainView(Model model) {
		return "etc/PSCMETC0001";
	}
	
	
	@RequestMapping(value = "etc/selectCodeMainList.do")
	public String selectCodeMainList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridData gdRes = new GridData();
		
		try {
			// 파라미터 획득
			String wiseGridData = request.getParameter("WISEGRID_DATA");
			GridData  gdReq = OperateGridData.parse(wiseGridData);
			gdRes = OperateGridData.cloneResponseGridData(gdReq);
			
			// mode 셋팅 
			gdRes.addParam("mode", gdReq.getParam("mode"));
			
			
			String rowsPerPage = StringUtil.null2string(gdReq.getParam("rowsPerPage"), config.getString("count.row.per.page"));
			DataMap paramMap = new DataMap();
			paramMap.put("currentPage", gdReq.getParam("currentPage"));
			paramMap.put("rowsPerPage", rowsPerPage);
            paramMap.put("majorCd", gdReq.getParam("majorCd"));
			paramMap.put("minorCd", gdReq.getParam("minorCd"));
			paramMap.put("cdNm", gdReq.getParam("cdNm"));

			
			// 데이터 조회
			List<DataMap> pscmetc0001List = pscmetc0001Service.selectCodeMainList(paramMap);

			// 조회된 데이터 가 없는 경우의 처리
	        if(pscmetc0001List == null || pscmetc0001List.size() == 0) {
	            //gdRes.setMessage("조회된 데이터가 없습니다.");
	            gdRes.setStatus("false");
	    		request.setAttribute("wizeGridResult", gdRes);
	    		return "common/wiseGridResult";
	        }

	        // GridData 셋팅
	        for(int i = 0; i < pscmetc0001List.size(); i++) {
	        	DataMap map = pscmetc0001List.get(i);
	        	
	        	gdRes.getHeader("crud").addValue("", "");
	        	gdRes.getHeader("selected").addValue("0", "");
	        	gdRes.getHeader("majorCd").addValue(map.getString("MAJOR_CD"),"");
	        	gdRes.getHeader("minorCd").addValue(map.getString("MINOR_CD"),"");
	        	gdRes.getHeader("cdNm").addValue(map.getString("CD_NM"),"");
	        	gdRes.getHeader("cdDesc").addValue(map.getString("CD_DESC"),"");
	        	gdRes.getHeader("orderSeq").addValue(map.getString("ORDER_SEQ"),"");
	        	gdRes.getHeader("regDivnCd").addSelectedHiddenValue(map.getString("REG_DIVN_CD"));
	        	gdRes.getHeader("useYn"  ).addSelectedHiddenValue(map.getString("USE_YN"));
	        	
	        }

	        String totalCount = pscmetc0001List.get(0).getString("TOTAL_COUNT");

	        // 페이징 변수
	        gdRes.addParam("totalCount", totalCount);
	        gdRes.addParam("rowsPerPage", gdReq.getParam("rowsPerPage"));	
	        gdRes.addParam("currentPage", gdReq.getParam("currentPage"));
	        gdRes.setStatus("true");
	        
		} catch(Exception e) {
			gdRes.setStatus("false");
			gdRes.setMessage(e.getMessage());
		}

		request.setAttribute("wizeGridResult", gdRes);		
		return "common/wiseGridResult";
	}
	
	
	/**
	 * 등록/수정/삭제
	 * Desc : 
	 * @Method Name : update
	 * @param request
	 * @return
	 * @throws Exception
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@RequestMapping("etc/saveCodeMainList.do")
	public void saveCodeMainList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridData gdRes = new GridData();
		
		// Encode Type을 UTF-8로 변환한다.
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
		
			String wiseGridData = request.getParameter("WISEGRID_DATA");
			GridData gdReq = OperateGridData.parse(wiseGridData);
			
			// 모드셋팅
			gdRes.addParam("mode", gdReq.getParam("mode"));

			List<PSCMETC0001VO> pscmetc0001VOList = new ArrayList<PSCMETC0001VO>();
			PSCMETC0001VO pscmetc0001VO = null;
	
			// 처리수행
			int rowCount = gdReq.getHeader("selected").getRowCount();
			
			// header data VO객체에 셋팅 
			for(int index = 0; index < rowCount; index++){
				pscmetc0001VO = new PSCMETC0001VO();
				pscmetc0001VO = (PSCMETC0001VO)getWiseGridHeaderDataToObject(index, gdReq, pscmetc0001VO);
				pscmetc0001VOList.add(pscmetc0001VO);
			}
			
			
			//등록, 수정, 삭제를 수행
			gdRes = doSave(gdReq, pscmetc0001VOList);

		} catch (Exception e) {
			gdRes.setMessage(e.getMessage());
		}
		finally {
			try {
				// 자료구조를 전문으로 변경해 Write한다.
				OperateGridData.write(gdRes, out);
			} catch (Exception e) {
				logger.error("error messgae : " + e.getMessage());
			}
		}
	}		
	
	
	/*PSCMETC0001Controller의  DTO셋팅과 등록, 수정, 삭제를 수행한다.*/
	public GridData doSave(GridData gdReq, List<PSCMETC0001VO> pscmetc0001VO) throws Exception {
		
		GridData gdRes = new GridData();
		String returnData = "";
		
		try {
			//저장, 삭제를 수행
			if("save".equals(gdReq.getParam("mode"))){
				pscmetc0001Service.saveCodeMainList(pscmetc0001VO);
			}else{
				pscmetc0001Service.deleteCodeMainList(pscmetc0001VO);
			}
			
			/* 화면에 전달할  파라미터를 설정한다.
			 * 메세지를 셋팅한다.
			 * Status를 설정한다
			 */			
			returnData = getSendData(pscmetc0001VO, gdReq.getParam("mode"));
			gdRes.addParam("saveData", returnData);
			gdRes.setStatus("true");			
			
		} catch (Exception e) {
			gdRes.setMessage(e.getMessage());
			gdRes.setStatus("false");
		}
		gdRes.addParam("mode", gdReq.getParam("mode"));
		return gdRes;		
	}	
	

	private String getSendData(List<PSCMETC0001VO> sendData, String CRUDFlag) {
		
		StringBuffer sbData = new StringBuffer();
							
		if (CRUDFlag.equals("save")) {		
			sbData.append(sendData.size()); 
			sbData.append(" 건의 데이터가 저장되었습니다.\n\n");	
		}
		else if (CRUDFlag.equals("delete")) {
			sbData.append(sendData.size()); 
			sbData.append(" 건의 데이터가 삭제되었습니다.\n\n");
		}	
		return sbData.toString();
	}
	
	
	/**
	 * GridData 헤더 data를 VO객체에 담는다. 
	 * Desc : 
	 * @Method Name : getWiseGridParamToDTO
	 * @param
	 * @param dto
	 * @return
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	public Object getWiseGridHeaderDataToObject(int index, GridData gdReq, Object bean) throws Exception{

		String name = "";
		String obj = "";
		String type = "";
		
		for(int j = 0; j < ((gdReq.getHeaders())).length; j++){
			name = (gdReq.getHeaders())[j].getID();
			
			type = gdReq.getHeader(name).getDataType().toString();
			
			if(type.equals("T")){			// text
				obj = gdReq.getHeader(name).getValue(index);
			}else if(type.equals("L")){		// combo
				obj = gdReq.getHeader(name).getComboHiddenValues()[gdReq.getHeader(name).getSelectedIndex(index)];
			}
				
			BeanUtils.setProperty(bean, name, obj);
		}
		
		return bean;
	}	
	
}
