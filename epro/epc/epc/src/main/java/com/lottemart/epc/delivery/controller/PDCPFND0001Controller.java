package com.lottemart.epc.delivery.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.RequestUtils;
import com.lottemart.epc.delivery.service.PDCPFND0001Service;
import com.lottemart.epc.util.Utils;

import lcn.module.common.util.StringUtil;

/**
 * @author binary27
 * @Class : com.lottemart.epc.delivery.controller
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011.12.22  binary27
 * @version :
 */
@Controller("pdcpfnd0001Controller")
public class PDCPFND0001Controller {

	@Autowired
	private PDCPFND0001Service pdcpfnd0001Service;

    /**
    	 * @see PDCPFND0001
    	 * @Method Name  : PDCPFND0001Controller.java
    	 * @since      : 2011. 12. 22.
    	 * @author     : binary27
    	 * @version    :
    	 * @Locaton    : com.lottemart.epc.delivery.controller
    	 * @Description : 사용자에게 보여줄 배송상태조회 팝업
         * @param
    	 * @return  String
         * @throws
    */
    @RequestMapping(value="delivery/deli.do")
    public String PDCPFND0001(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 파라메터 설정
    	DataMap paramMap = RequestUtils.getStringParamDataMap(request);
    	paramMap.put("currentPage", paramMap.getString("currentPage", "1"));
    	paramMap.put("rowsPerPage", 10);

		// 데이터 조회
    	int totalCount = 0;
    	List<DataMap> acceptList = new ArrayList<DataMap>();
    	if(!paramMap.getString("no").isEmpty() || !paramMap.getString("no2").isEmpty() || !paramMap.getString("sc_hodeco_invoice_no").isEmpty()) {
    		acceptList = pdcpfnd0001Service.selectAcceptList(paramMap);
    		if(acceptList.size() > 0) totalCount = acceptList.get(0).getInt("TOTAL_COUNT");
    	}
		request.setAttribute("acceptList", acceptList);

		//페이징정보
		paramMap.put("totalCount", totalCount);

		request.setAttribute("sc", paramMap);

		if(request.getParameter("holiday") != null) {
			request.setAttribute("close", "Y");
		}

		String Agent = request.getHeader("user-agent");

		if(Agent.toUpperCase().indexOf("IPAD")>-1) {

			request.setAttribute("Agent", "IPAD");
			return "delivery/PDCPFND0001_MOBILE";

		} else if(Agent.toUpperCase().indexOf("IPHONE")>-1) {

			request.setAttribute("Agent", "IPHONE");
			return "delivery/PDCPFND0001_MOBILE";

		} else if(Agent.toUpperCase().indexOf("ANDROID")>-1) {
			request.setAttribute("Agent", "TABLET");

			if(Agent.toUpperCase().indexOf("MOBILE")>-1){
				request.setAttribute("Agent", "ANDROID");
			}
			return "delivery/PDCPFND0001_MOBILE";
		}

		return "delivery/PDCPFND0001";
    }
    
    /**
	 * @see PDCPFND0001
	 * @Method Name  : PDCPFND0001Controller.java
	 * @since      : 2011. 12. 22.
	 * @author     : binary27
	 * @version    :
	 * @Locaton    : com.lottemart.epc.delivery.controller
	 * @Description : 사용자에게 보여줄 배송상태조회 팝업
     * @param
	 * @return  String
     * @throws
*/
@RequestMapping(value="delivery/deli_new.do")
public String PDCPFND0001_NEW(HttpServletRequest request, HttpServletResponse response) throws Exception {

	// 파라메터 설정
	DataMap paramMap = RequestUtils.getStringParamDataMap(request);
	paramMap.put("currentPage", paramMap.getString("currentPage", "1"));
	paramMap.put("rowsPerPage", 10);

	// 데이터 조회
	int totalCount = 0;
	List<DataMap> acceptList = new ArrayList<DataMap>();
	if(!paramMap.getString("no").isEmpty() || !paramMap.getString("no2").isEmpty() || !paramMap.getString("sc_hodeco_invoice_no").isEmpty()) {
		acceptList = pdcpfnd0001Service.selectAcceptList(paramMap);
		if(acceptList.size() > 0) totalCount = acceptList.get(0).getInt("TOTAL_COUNT");
	}
	
	//이름, 주소 마스킹 처리
	if(acceptList != null && acceptList.size() >0){
		for (int i = 0; i < acceptList.size(); i++) {
			acceptList.get(i).put("SEND_PSN_NM",  Utils.getMaskingName2((String)acceptList.get(i).get("SEND_PSN_NM")));
			acceptList.get(i).put("RECV_PSN_NM", Utils.getMaskingName2((String)acceptList.get(i).get("RECV_PSN_NM")));
			acceptList.get(i).put("RECV_PSN_ZIP_ADDR", Utils.getMaskingAddr2((String)acceptList.get(i).get("RECV_PSN_ZIP_ADDR")));
		}
	}

	request.setAttribute("acceptList", acceptList);

	//페이징정보
	paramMap.put("totalCount", totalCount);

	request.setAttribute("sc", paramMap);

	if(request.getParameter("holiday") != null) {
		request.setAttribute("close", "Y");
	}

	String Agent = request.getHeader("user-agent");

	if(Agent.toUpperCase().indexOf("IPAD")>-1) {

		request.setAttribute("Agent", "IPAD");
		return "delivery/PDCPFND0001_MOBILE_NEW";

	} else if(Agent.toUpperCase().indexOf("IPHONE")>-1) {

		request.setAttribute("Agent", "IPHONE");
		return "delivery/PDCPFND0001_MOBILE_NEW";

	} else if(Agent.toUpperCase().indexOf("ANDROID")>-1) {
		request.setAttribute("Agent", "TABLET");

		if(Agent.toUpperCase().indexOf("MOBILE")>-1){
			request.setAttribute("Agent", "ANDROID");
		}
		return "delivery/PDCPFND0001_MOBILE_NEW";
	}

	return "delivery/PDCPFND0001_NEW";
}

    @RequestMapping(value="/delivery/selectStr.do")
    public String selectStr(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// 파라미터 획득
		String strCd = StringUtil.null2str(req.getParameter("strCd"), "");	// 점포코드

		// 데이터 조회
		DataMap paramMap = pdcpfnd0001Service.selectStoreDetailInfo(strCd);

		req.setAttribute("paramInfo", paramMap);

		return "delivery/PDCPFND000101";

	}
}
