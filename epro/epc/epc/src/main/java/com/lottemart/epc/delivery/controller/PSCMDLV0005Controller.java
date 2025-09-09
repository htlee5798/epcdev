package com.lottemart.epc.delivery.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import lcn.module.common.util.StringUtil;
import lcn.module.framework.property.ConfigurationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.service.CommonService;
import com.lottemart.epc.delivery.model.PSCMDLV0005VO;
import com.lottemart.epc.delivery.service.PSCMDLV0005Service;
import com.lottemart.epc.util.Utils;
/**
 * Handles requests for the application home page.
 */
@Controller
public class PSCMDLV0005Controller {

	@Autowired
	private ConfigurationService config;

	@Autowired
	private PSCMDLV0005Service pscmdlv0005Service;

	@Autowired
	private CommonService commonService;

	@RequestMapping(value = "/delivery/selectPartnerFirmsStatus.do")
	public String selectNaverEdmSummaryList(@ModelAttribute("searchVO") PSCMDLV0005VO searchVO, HttpServletRequest request, ModelMap model) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);

		// 주문일자 조회조건이 없을 경우
		if(StringUtil.isNVL(searchVO.getStartDate()) || StringUtil.isNVL(searchVO.getEndDate())) {

			String from_date = "";     // 기간(시작)
			String to_date 	 = "";     // 기간(끝)

			Calendar NowDate = Calendar.getInstance();
			Calendar NowDate2 = Calendar.getInstance();
			NowDate.add(Calendar.DATE, 0);
			NowDate2.add(Calendar.DATE, -7);

			String today_date = Utils.formatDate(NowDate.getTime(),"yyyy-MM-dd");
			String today_date2 = Utils.formatDate(NowDate2.getTime(),"yyyy-MM-dd");
			// 초최 오픈시 Default 값 세팅
			if(from_date == null || from_date.equals("")) from_date = today_date2;
			if(to_date   == null || to_date.equals(""))   to_date   = today_date;

			searchVO.setStartDate(from_date);
			searchVO.setEndDate(to_date);

		}

		searchVO.setFromDate(searchVO.getStartDate().replaceAll("-", ""));
		searchVO.setToDate(searchVO.getEndDate().replaceAll("-", ""));
		searchVO.setVendorMode(null);

		model.addAttribute("searchVO", searchVO);

		List<DataMap> list = new ArrayList<DataMap>();

		// 협력사코드 전체를 선택한  경우 로그인 세션에 있는 협력사 코드 전체를 설정한다.
		if(searchVO.getVendorId()==null||searchVO.getVendorId().length==0) {
			searchVO.setVendorId(epcLoginVO.getVendorId());
		}else{
			request.setAttribute("vendorId", searchVO.getVendorId()[0].toString());
		}

		if("06".equals(epcLoginVO.getVendorTypeCd())){

			if((searchVO.getVendorId()[0].toString()).indexOf("T")  < 0 ){
				searchVO.setVendorMode("V");
			}
		}

		return "delivery/PSCMDLV0005";
	}

	@RequestMapping(value = "/delivery/selectPartnerFirmsListStatus.do")
	public String selectNaverEdmSummaryLists(@ModelAttribute("searchVO") PSCMDLV0005VO searchVO, HttpServletRequest request, ModelMap model) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);

		// 주문일자 조회조건이 없을 경우
		if(StringUtil.isNVL(searchVO.getStartDate()) || StringUtil.isNVL(searchVO.getEndDate())) {

			String from_date = "";     // 기간(시작)
			String to_date 	 = "";     // 기간(끝)

			Calendar NowDate = Calendar.getInstance();
			Calendar NowDate2 = Calendar.getInstance();
			NowDate.add(Calendar.DATE, 0);
			NowDate2.add(Calendar.DATE, -7);

			String today_date = Utils.formatDate(NowDate.getTime(),"yyyy-MM-dd");
			String today_date2 = Utils.formatDate(NowDate2.getTime(),"yyyy-MM-dd");
			// 초최 오픈시 Default 값 세팅
			if(from_date == null || from_date.equals("")) from_date = today_date2;
			if(to_date   == null || to_date.equals(""))   to_date   = today_date;

			searchVO.setStartDate(from_date);
			searchVO.setEndDate(to_date);

		}

		searchVO.setFromDate(searchVO.getStartDate().replaceAll("-", ""));
		searchVO.setToDate(searchVO.getEndDate().replaceAll("-", ""));
		searchVO.setVendorMode(null);

		model.addAttribute("searchVO", searchVO);

		List<DataMap> list = new ArrayList<DataMap>();

		// 선택한 vendorId가null 이거나 repVendorId ( 대표협력업체 ) 일때 협력사 전체 검색
		if(searchVO.getVendorId()==null || searchVO.getVendorId().length==0 || epcLoginVO.getRepVendorId().equals(searchVO.getVendorId()[0].toString())) {
			searchVO.setVendorId(epcLoginVO.getVendorId());
		}else{
			request.setAttribute("vendorId", searchVO.getVendorId()[0].toString());
		}

		// 선택한 vendor가 openappi 업체일 경우 전체 검색
		List<EpcLoginVO> openappiVendorId = commonService.selectOpenappiVendor();
		for(int l=0; openappiVendorId.size()>l; l++ ){
			if(openappiVendorId.get(l).getRepVendorId().equals(searchVO.getVendorId()[0].toString().replace("[", "").replace("]", "").trim())){
				searchVO.setVendorId(epcLoginVO.getVendorId());
				}
		}

		if("06".equals(epcLoginVO.getVendorTypeCd()) ){

			if((searchVO.getVendorId()[0].toString()).indexOf("T")  < 0 ){
				searchVO.setVendorMode("V");
			}
		}

		List<DataMap> shortList = pscmdlv0005Service.selectPartnerFirmsStatus_short(searchVO);
		summaryAddList(shortList, "short", list);

		// 명절배송조회
		List<DataMap> holyList = pscmdlv0005Service.selectPartnerFirmsStatus_holy(searchVO);
		summaryAddList(holyList,  "holy", list);

		int totalOrdCnt = pscmdlv0005Service.selectTotalOrderCnt(searchVO);
		request.setAttribute("totalOrdCnt", totalOrdCnt);

		// 합계셋팅
		DataMap map = new DataMap();
		map.put("VD_TEXT", "계");
		map.put("VD_01_SUM", list.get(0).getInt("VD_01_SUM") + list.get(1).getInt("VD_01_SUM"));
		map.put("VD_02_SUM", list.get(0).getInt("VD_02_SUM") + list.get(1).getInt("VD_02_SUM"));
		map.put("VD_03_SUM", list.get(0).getInt("VD_03_SUM") + list.get(1).getInt("VD_03_SUM"));
		map.put("VD_04_SUM", list.get(0).getInt("VD_04_SUM") + list.get(1).getInt("VD_04_SUM"));
		map.put("VD_05_SUM", list.get(0).getInt("VD_05_SUM") + list.get(1).getInt("VD_05_SUM"));
		map.put("VD_06_SUM", list.get(0).getInt("VD_06_SUM") + list.get(1).getInt("VD_06_SUM"));
		map.put("VD_07_SUM", list.get(0).getInt("VD_07_SUM") + list.get(1).getInt("VD_07_SUM"));
		map.put("VD_08_SUM", list.get(0).getInt("VD_08_SUM") + list.get(1).getInt("VD_08_SUM"));
		map.put("VD_09_SUM", list.get(0).getInt("VD_09_SUM") + list.get(1).getInt("VD_09_SUM"));
		map.put("VD_10_SUM", list.get(0).getInt("VD_10_SUM") + list.get(1).getInt("VD_10_SUM"));

		model.addAttribute("list", list);
		model.addAttribute("map", map);

		return "delivery/PSCMDLV0005";
	}


	public void summaryAddList(List<DataMap> resultList, String gubun, List<DataMap> list){
		int vd01=0;	int vd02=0;	int vd03=0;	int vd04=0;	int vd05=0;
		int vd06=0;	int vd07=0;	int vd08=0;	int vd09=0; int vd10=0;


		//DataMap map = new DataMap();
		for(int i = 0; i < resultList.size(); i++){
			DataMap map = (DataMap)resultList.get(i);
			vd01 += map.getInt("VD_01");
			vd02 += map.getInt("VD_02");
			vd03 += map.getInt("VD_03");
			vd04 += map.getInt("VD_04");
			vd05 += map.getInt("VD_05");
			vd06 += map.getInt("VD_06");
			vd07 += map.getInt("VD_07");
			vd08 += map.getInt("VD_08");
			vd09 += map.getInt("VD_09");
			vd10 += map.getInt("VD_10");
		}

		DataMap resultMap = new DataMap();

		if(gubun.equals("short")){
			resultMap.put("DELI_TYPE_CD", "04");
			resultMap.put("VD_TEXT", "일반배송");
		}else{
			resultMap.put("DELI_TYPE_CD", "06");
			resultMap.put("VD_TEXT", "명절배송");
		}


		resultMap.put("VD_01_SUM", vd01);
		resultMap.put("VD_02_SUM", vd02);
		resultMap.put("VD_03_SUM", vd03);
		resultMap.put("VD_04_SUM", vd04);
		resultMap.put("VD_05_SUM", vd05);
		resultMap.put("VD_06_SUM", vd06);
		resultMap.put("VD_07_SUM", vd07);
		resultMap.put("VD_08_SUM", vd08);
		resultMap.put("VD_09_SUM", vd09);
		resultMap.put("VD_10_SUM", vd10);
		list.add(resultMap);
	}

	@RequestMapping(value = "delivery/downloadDestructionDocExcel.do")
	public String downloadDestructionDocExcel(@ModelAttribute("searchVO") PSCMDLV0005VO searchVO, HttpServletRequest request) throws Exception{

		String sessionKey = config.getString("lottemart.epc.session.key");
        SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분", Locale.KOREA);
		Calendar cal = Calendar.getInstance();
		String today = format.format(cal.getTime());

		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);

		// 선택한 vendorId가null 이거나 repVendorId ( 대표협력업체 ) 일때 협력사 전체 검색
		if(searchVO.getVendorId()==null || searchVO.getVendorId().length==0 || epcLoginVO.getRepVendorId().equals(searchVO.getVendorId()[0].toString())) {
			searchVO.setVendorId(epcLoginVO.getVendorId());
		}else{
			request.setAttribute("vendorId", searchVO.getVendorId()[0].toString());
		}

		// 선택한 vendor가 openappi 업체일 경우 전체 검색
		List<EpcLoginVO> openappiVendorId = commonService.selectOpenappiVendor();
		for(int l=0; openappiVendorId.size()>l; l++ ){
			if(openappiVendorId.get(l).getRepVendorId().equals(searchVO.getVendorId()[0].toString().replace("[", "").replace("]", "").trim())){
				searchVO.setVendorId(epcLoginVO.getVendorId());
				}
		}

		if("06".equals(epcLoginVO.getVendorTypeCd())){

			if((searchVO.getVendorId()[0].toString()).indexOf("T")  < 0 ){
				searchVO.setVendorMode("V");
			}
		}

		int totalOrdCnt = pscmdlv0005Service.selectTotalOrderCnt(searchVO);
		request.setAttribute("totalOrdCnt", totalOrdCnt);
		request.setAttribute("today", today);
		return "delivery/PSCMDLV000501";
	}
}

