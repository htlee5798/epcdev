package com.lottemart.epc.delivery.controller;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.util.StringUtil;
import lcn.module.framework.property.ConfigurationService;
import org.springframework.ui.ModelMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import xlib.cmc.GridData;
import xlib.cmc.OperateGridData;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.delivery.model.PSCMDLV0005VO;
import com.lottemart.epc.delivery.service.PSCMDLV0001Service;
import com.lottemart.epc.util.Utils;
/**
 * Handles requests for the application home page.
 */
@Controller
public class PSCMDLV0001Controller {

	@Autowired
	private ConfigurationService config;

	@Autowired
	private PSCMDLV0001Service pscmdlv0001Service;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/delivery/viewVenStatus2222.do")
	public String viewVenStatus(@ModelAttribute("searchVO") PSCMDLV0005VO searchVO, ModelMap model) throws Exception {

		String from_date = "";     // 기간(시작)
		String to_date 	 = "";     // 기간(끝)

		Calendar NowDate = Calendar.getInstance();
		Calendar NowDate2 = Calendar.getInstance();
		NowDate.add(Calendar.DATE, 0);
		NowDate2.add(Calendar.DATE, -21);

		String today_date = Utils.formatDate(NowDate.getTime(),"yyyy-MM-dd");
		String today_date2 = Utils.formatDate(NowDate2.getTime(),"yyyy-MM-dd");
		// 초최 오픈시 Default 값 세팅
		if(from_date == null || from_date.equals("")) from_date = today_date2;
		if(to_date   == null || to_date.equals(""))   to_date   = today_date;

		searchVO.setStartDate(from_date);
		searchVO.setEndDate(to_date);
		searchVO.setFromDate(searchVO.getStartDate().replaceAll("-", ""));
		searchVO.setToDate(searchVO.getEndDate().replaceAll("-", ""));
		return "delivery/PSCMDLV0001";

	}


	@RequestMapping(value = "/delivery/selectVenStatus2222.do")
	public String selectVenStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridData gdRes = new GridData();

		try {
			// 파라미터 획득
			String wiseGridData = request.getParameter("WISEGRID_DATA");
			GridData  gdReq = OperateGridData.parse(wiseGridData);
			gdRes = OperateGridData.cloneResponseGridData(gdReq);

			// mode 셋팅
			gdRes.addParam("mode", gdReq.getParam("mode"));

			// 합계초기화
	        int totalCountSum = 0;
	        int dstatus30Sum = 0;
	        int dstatus31Sum = 0;
	        int dstatus33Sum = 0;
	        int dstatus36Sum = 0;
	        int dstatus51Sum = 0;
	        int dstatus52Sum = 0;

			//합계초기화
			gdRes.addParam("totalListCount", totalCountSum+"");
	        gdRes.addParam("totalCountSum", dstatus30Sum+"");
	        gdRes.addParam("dstatus30Sum", dstatus31Sum+"");
	        gdRes.addParam("dstatus31Sum", dstatus33Sum+"");
	        gdRes.addParam("dstatus33Sum", dstatus33Sum+"");
	        gdRes.addParam("dstatus36Sum", dstatus36Sum+"");
	        gdRes.addParam("dstatus51Sum", dstatus51Sum+"");
	        gdRes.addParam("dstatus52Sum", dstatus52Sum+"");


			String rowsPerPage = StringUtil.null2string(gdReq.getParam("rowsPerPage"), config.getString("count.row.per.page"));
			DataMap paramMap = new DataMap();
			paramMap.put("currentPage", gdReq.getParam("currentPage"));
			paramMap.put("rowsPerPage", rowsPerPage);
            paramMap.put("startDate", gdReq.getParam("startDate"));
			paramMap.put("endDate", gdReq.getParam("endDate"));

			// 데이터 조회
			List<DataMap> list = pscmdlv0001Service.selectVenStatusList(paramMap);

			// 조회된 데이터 가 없는 경우의 처리
	        if(list == null || list.size() == 0) {
	            gdRes.setStatus("false");
	    		request.setAttribute("wizeGridResult", gdRes);
	    		return "common/wiseGridResult";
	        }

	        // GridData 셋팅
	        for(int i = 0; i < list.size(); i++) {
	        	DataMap map = list.get(i);

	        	gdRes.getHeader("vendorId").addValue(map.getString("VENDOR_ID"),"");
	        	gdRes.getHeader("vendorNm").addValue(map.getString("VENDOR_NM"),"");
	        	gdRes.getHeader("totalCount").addValue(map.getString("TOTALCOUNT"),"0");
	        	gdRes.getHeader("dstatus30").addValue(map.getString("DSTATUS_30"),"0");
	        	gdRes.getHeader("dstatus31").addValue(map.getString("DSTATUS_31"),"0");
	        	gdRes.getHeader("dstatus33").addValue(map.getString("DSTATUS_33"),"0");
	        	gdRes.getHeader("dstatus36").addValue(map.getString("DSTATUS_36"),"0");
	        	gdRes.getHeader("dstatus51").addValue(map.getString("DSTATUS_51"),"0");
	        	gdRes.getHeader("dstatus52").addValue(map.getString("DSTATUS_52"),"0");

	        	totalCountSum += map.getInt("TOTALCOUNT");
	        	dstatus30Sum  += map.getInt("DSTATUS_30");
	        	dstatus31Sum  += map.getInt("DSTATUS_31");
	        	dstatus33Sum  += map.getInt("DSTATUS_33");
	        	dstatus36Sum  += map.getInt("DSTATUS_36");
	        	dstatus51Sum  += map.getInt("DSTATUS_51");
	        	dstatus52Sum  += map.getInt("DSTATUS_52");

	        }

	        String totalCount = list.get(0).getString("TOTAL_COUNT");

	        // 페이징 변수
	        gdRes.addParam("totalCount", totalCount);
	        gdRes.addParam("rowsPerPage", gdReq.getParam("rowsPerPage"));
	        gdRes.addParam("currentPage", gdReq.getParam("currentPage"));

	        //합계
	        gdRes.addParam("totalListCount", ""+totalCount);
	        gdRes.addParam("totalCountSum", ""+totalCountSum);
	        gdRes.addParam("dstatus30Sum", ""+dstatus30Sum);
	        gdRes.addParam("dstatus31Sum", ""+dstatus31Sum);
	        gdRes.addParam("dstatus33Sum", ""+dstatus33Sum);
	        gdRes.addParam("dstatus36Sum", ""+dstatus36Sum);
	        gdRes.addParam("dstatus51Sum", ""+dstatus51Sum);
	        gdRes.addParam("dstatus52Sum", ""+dstatus52Sum);

	        //정상
	        gdRes.setStatus("true");
		} catch(Exception e) {
			gdRes.setStatus("false");
			gdRes.setMessage(e.getMessage());
		}

		request.setAttribute("wizeGridResult", gdRes);
		return "common/wiseGridResult";
	}


}
