package com.lottemart.epc.delivery.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lcn.module.common.util.StringUtil;
import lcn.module.common.views.AjaxJsonModelHelper;
import lcn.module.framework.property.ConfigurationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

//import xlib.cmc.GridData;
//import xlib.cmc.OperateGridData;

import com.lottemart.common.code.service.CommonCodeService;
import com.lottemart.common.exception.AppException;
import com.lottemart.common.util.DataMap;
import com.lottemart.common.util.JsonUtils;
import com.lottemart.common.util.RequestUtils;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.delivery.model.PDCPHPC0001VO;
import com.lottemart.epc.delivery.service.PDCPHPC0001Service;

import zipit.rfnCustCommonAddrList;


/**
 * @author binary27
 * @Class : com.lottemart.epc.delivery.controller
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 12. 6.   binary27
 * @version :  
 */
@Controller("pdcphpc0001Controller")
public class PDCPHPC0001Controller {

	private static final Logger logger = LoggerFactory.getLogger(PDCPHPC0001Controller.class);

	@Autowired
	private PDCPHPC0001Service pdcphpc0001Service;

	@Autowired
	private CommonCodeService commonCodeService;

	@Autowired
	private ConfigurationService config;

	@Autowired
	MessageSource messageSource;

    /** 
    	 * @see PDCPHPC0001
    	 * @Method Name  : PDCPHPC0001Controller.java
    	 * @since      : 2011. 12. 6.
    	 * @author     : binary27
    	 * @version    :
    	 * @Locaton    : com.lottemart.epc.delivery.controller
    	 * @Description : 해피콜 등록을 위한 정보 조회
         * @param 
    	 * @return  String
         * @throws 
    */
	@RequestMapping("delivery/PDCPHPC0001.do")
	public String PDCPHPC0001(HttpServletRequest request) throws Exception {
		DataMap hpclInfo = new DataMap();
		DataMap endHpcMap = new DataMap();
		List<DataMap> hpclProdInfo = new ArrayList<DataMap>();
		List<DataMap> hpcInfoList = new ArrayList<DataMap>();
		try {

			//Session check 실패 --> 메인페이지로 이동
			String sessionKey = config.getString("lottemart.epc.session.key");
			EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);
			if (epcLoginVO == null) { //권한이 종료된 경우
				logger.debug("로그인 권한이 종료되었습니다.");
				request.setAttribute("loginSession", false);
				return "delivery/PDCPHPC0001";
			} else {
				request.setAttribute("loginSession", true);
			}

			//파라미터
			String invoiceNo = request.getParameter("invoiceNo");
			request.setAttribute("invoiceNo", invoiceNo); //운송장번호
			logger.debug("invoiceNo : " + invoiceNo);

			//해피콜 등록을 위한 정보 조회
			hpclInfo = pdcphpc0001Service.selectHpCallRegiInfo(invoiceNo); //기본정보
			hpclProdInfo = pdcphpc0001Service.selectProdInfo(invoiceNo); //상품정보
			hpcInfoList = pdcphpc0001Service.selectHpcInfo(invoiceNo); //해피콜목록

			//최종해피콜정보
			if (hpcInfoList.size() > 0) {
				endHpcMap.put("HPCL_RSLT_NM", hpcInfoList.get(0).get("HPCL_RSLT_NM"));
				endHpcMap.put("MOD_DATE", hpcInfoList.get(0).get("MOD_DATE"));
				endHpcMap.put("CNSR_NM", hpcInfoList.get(0).get("CNSR_NM"));
				endHpcMap.put("HPCL_CONTENT", hpcInfoList.get(0).get("HPCL_CONTENT"));
			}

			request.setAttribute("hpclInfo", hpclInfo); //기본정보
			request.setAttribute("hpclProdInfo", hpclProdInfo); //상품정보
			request.setAttribute("hpcInfoList", hpcInfoList); //해피콜정보List
			request.setAttribute("endHpc", endHpcMap); //최종해피콜

			//해피콜상태코드
			String hpclRsltCd = hpclInfo.getString("HPCL_RSLT_CD");
			logger.debug("hpclRsltCd : " + hpclRsltCd);

			String[] statusCds = null;

			if ("20".equals(hpclRsltCd)) { //사전 해피콜 미확정
				statusCds = new String[] { "20", "30", "40" };
			} else if ("30".equals(hpclRsltCd)) { //사전 해피콜 확정 2018.2.2 요청으로 사후 해피콜 미선택 되도록 수정
				statusCds = new String[] { "20", "30" };
			} else if ("40".equals(hpclRsltCd)) { //취소
				statusCds = new String[] { "20", "30", "40" };
			} else if ("50".equals(hpclRsltCd)) { //사후 해피콜 미확정
				statusCds = new String[] { "50", "60" };
			} else if ("60".equals(hpclRsltCd)) {
				statusCds = new String[] { "60" }; //사후 해피콜 확정
			} else {
				statusCds = new String[] {};
			}

			logger.debug("statusCds : " + statusCds);
			request.setAttribute("hpcStatus", commonCodeService.getCodeList("HO19", statusCds));
			request.setAttribute("loginId", epcLoginVO.getAdminId());
		} catch (Exception e) {
			logger.error("initLoad error occurred!!", e);
		}
		return "delivery/PDCPHPC0001";
	}

	/** 
		 * @see updateHappyCall
		 * @Method Name  : PDCPHPC0001Controller.java
		 * @since      : 2011. 12. 7.
		 * @author     : binary27
		 * @version    :
		 * @Locaton    : com.lottemart.epc.delivery.controller
		 * @Description : 해피콜정보등록
	     * @param 
		 * @return  ModelAndView
	     * @throws 
	*/
	@RequestMapping(value="delivery/updateHappyCall.do")
	public ModelAndView updateHappyCall(HttpServletRequest request, HttpServletResponse response) throws Exception {

		PDCPHPC0001VO pdcphpc0001VO = new PDCPHPC0001VO();

		try {
			DataMap paramMap = RequestUtils.getStringParamDataMap(request);

			//희망배송날짜에서 '-'제거
			String deliHopdDy = paramMap.getString("deliHopeDy");
			if (deliHopdDy.length() > 0) {
				deliHopdDy = deliHopdDy.replaceAll("-", "");
			}

			//우편번호에서 '-'제거
			String recvPsnZipNo = paramMap.getString("recvPsnZipNo");
			if (recvPsnZipNo.length() > 0) {
				recvPsnZipNo = recvPsnZipNo.replaceAll("-", "");
			}

			String sendPsnZipNo = paramMap.getString("sendPsnZipNo");
			if (sendPsnZipNo.length() > 0) {
				sendPsnZipNo = sendPsnZipNo.replaceAll("-", "");
			}

			//촐고일에서 '-'제거
			String deliOrderDy = paramMap.getString("deliOrderDy");
			if (deliOrderDy.length() > 0) {
				deliOrderDy = deliOrderDy.replaceAll("-", "");
			}

			//Session check 실패 --> 메인페이지로 이동
			String sessionKey = config.getString("lottemart.epc.session.key");
			EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);
			if (epcLoginVO == null) { //권한이 종료된 경우
				logger.debug("로그인 권한이 종료되었습니다.");
				return AjaxJsonModelHelper.create(-1);
			}
			String userAdminId = pdcphpc0001Service.getAdminId(epcLoginVO.getAdminId());
			// logger.debug("userId : " + epcLoginVO.getAdminId());
			// logger.debug("adminId : " + userAdminId);

			// logger.debug("invoiceNo : "+paramMap.getString("invoiceNo"));
			// logger.debug("deliHopeDy : "+paramMap.getString("deliHopeDy"));
			// logger.debug("hpclContent : "+paramMap.getString("hpclContent"));
			// logger.debug("hpclRsltCd : "+paramMap.getString("hpclRsltCd"));
			// logger.debug("recvPsnZipNo : "+recvPsnZipNo);
			// logger.debug("recvPsnZipNoSeq : "+paramMap.getString("recvPsnZipNoSeq"));
			// logger.debug("recvPsnZipAddr : "+paramMap.getString("recvPsnZipAddr"));
			// logger.debug("recvPsnAddr : "+paramMap.getString("recvPsnAddr"));
			// logger.debug("recvNm : "+paramMap.getString("recvNm"));
			// logger.debug("recvPsnCellNo : "+paramMap.getString("recvPsnCellNo"));
			// logger.debug("recvPsnTelNo : "+paramMap.getString("recvPsnTelNo"));

			// logger.debug("sendPsnZipNo : "+sendPsnZipNo);
			// logger.debug("sendPsnZipNoSeq : "+paramMap.getString("sendPsnZipNoSeq"));
			// logger.debug("sendPsnZipAddr : "+paramMap.getString("sendPsnZipAddr"));
			// logger.debug("sendPsnAddr : "+paramMap.getString("sendPsnAddr"));
			// logger.debug("sendNm : "+paramMap.getString("sendNm"));
			// logger.debug("sendPsnCellNo : "+paramMap.getString("sendPsnCellNo"));
			// logger.debug("sendPsnTelNo : "+paramMap.getString("sendPsnTelNo"));
			// logger.debug("deliOrderDy : "+deliOrderDy);

			pdcphpc0001VO.setInvoiceNo(paramMap.getString("invoiceNo")); //운송장번호
			pdcphpc0001VO.setHopeDeliDy(deliHopdDy); //희망배송일
			pdcphpc0001VO.setHpclContent(paramMap.getString("hpclContent"));//해피콜내용
			pdcphpc0001VO.setHpclRsltCd(paramMap.getString("hpclRsltCd")); //해피콜상태코드

			//보내시는분
			pdcphpc0001VO.setSendNm(paramMap.getString("sendNm")); //받으실분 이름
			pdcphpc0001VO.setSendPsnCellNo(paramMap.getString("sendPsnCellNo")); //받으실분 핸드폰번호
			pdcphpc0001VO.setSendPsnTelNo(paramMap.getString("sendPsnTelNo")); //받으실분 전화번호
			pdcphpc0001VO.setSendPsnZipNo(sendPsnZipNo); //보내신분 우편번호
			pdcphpc0001VO.setSendPsnZipNoSeq(paramMap.getString("sendPsnZipNoSeq")); //보내신분 우편번호 순번
			pdcphpc0001VO.setSendPsnZipAddr(paramMap.getString("sendPsnZipAddr")); //보내신분 주소
			pdcphpc0001VO.setSendPsnAddr(paramMap.getString("sendPsnAddr")); //보내신분 상세주소

			//받으시는분
			pdcphpc0001VO.setRecvNm(paramMap.getString("recvNm")); //받으실분 이름
			pdcphpc0001VO.setRecvPsnCellNo(paramMap.getString("recvPsnCellNo")); //받으실분 핸드폰번호
			pdcphpc0001VO.setRecvPsnTelNo(paramMap.getString("recvPsnTelNo")); //받으실분 전화번호
			pdcphpc0001VO.setRecvPsnZipNo(recvPsnZipNo); //받으실분 우편번호
			pdcphpc0001VO.setRecvPsnZipNoSeq(paramMap.getString("recvPsnZipNoSeq")); //받으실분 우편번호 순번
			pdcphpc0001VO.setRecvPsnZipAddr(paramMap.getString("recvPsnZipAddr")); //받으실분 주소
			pdcphpc0001VO.setRecvPsnAddr(paramMap.getString("recvPsnAddr")); //받으실분 상세주소

			pdcphpc0001VO.setDeliMsg(paramMap.getString("deliMsg")); //배송메세지
			pdcphpc0001VO.setDeliStrCd(paramMap.getString("deliStrCd")); //배송점포코드
			pdcphpc0001VO.setDeliOrderDy(deliOrderDy); //출고일

			pdcphpc0001VO.setRegId(userAdminId);
			pdcphpc0001VO.setModId(userAdminId);

			//해피콜정보 저장
			int updateNum = pdcphpc0001Service.updateHappyCall(pdcphpc0001VO);
			logger.debug("updateNum : " + updateNum);

			return AjaxJsonModelHelper.create(updateNum);

		} catch (Exception e) {
			logger.error("", e);
			return AjaxJsonModelHelper.create(0);
		}
	}

	/**
	 * 우편번호 조회 페이지 이동
	 * Desc : 
	 * @Method Name : list
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("delivery/post.do")
	public String list(HttpServletRequest request) throws Exception {
		return "delivery/PDCPHPC0001001";
	}

	/**
	 * 우편번호 조회 처리
	 * Desc : 
	 * @Method Name : list
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("delivery/postsearch.do")
	public @ResponseBody Map search(HttpServletRequest request) throws Exception {
		Map rtnMap = new HashMap<String, Object>();

		try {
			DataMap param = new DataMap(request);

			param.put("currentPage", (String) param.get("currentPage"));
			param.put("rowsPerPage", (String) param.get("rowsPerPage"));
			param.put("strCd", (String) param.get("strCd"));
			param.put("yearMonth", (String) param.get("yearMonth"));
			param.put("deli_type_cd", (String) param.get("deli_type_cd"));
			param.put("deli_divn_cd", (String) param.get("deli_divn_cd"));

			String addressDivn = (String) param.get("addressDivn");
			String dongNm = (String) param.get("dongNm");
			String sido = (String) param.get("sido");
			String gungu = (String) param.get("gungu");
			String road = (String) param.get("road");
			String buildNum = (String) param.get("buildNum");
			String buildName = (String) param.get("buildName");

			//validation
			if ("j".equals(addressDivn)) {
				if (StringUtil.isEmpty(dongNm)) {
					//읍/명/동은(는) 필수 입력값입니다.
					throw new AppException(messageSource.getMessage("msg.common.error.required", new String[] { "읍/명/동" }, Locale.getDefault()));
				}
			} else if ("n".equals(addressDivn)) {
				if (StringUtil.isEmpty(sido)) {
					//시/도은(는) 필수 입력값입니다.
					throw new AppException(messageSource.getMessage("msg.common.error.required", new String[] { "시/도" }, Locale.getDefault()));
				}
				if (StringUtil.isEmpty(gungu)) {
					//시/군/구은(는) 필수 입력값입니다.
					throw new AppException(messageSource.getMessage("msg.common.error.required", new String[] { "시/군/구" }, Locale.getDefault()));
				}
				if (StringUtil.isEmpty(road)) {
					//도로명은(는) 필수 입력값입니다.
					throw new AppException(messageSource.getMessage("msg.common.error.required", new String[] { "도로명" }, Locale.getDefault()));
				}
			}
			//put parameter
			DataMap paramMap = new DataMap();
			paramMap.put("dongNm", dongNm);
			paramMap.put("sido", sido);
			paramMap.put("gungu", gungu);
			paramMap.put("road", road);
			paramMap.put("buildNum", buildNum);
			paramMap.put("buildName", buildName);

			// 데이터 조회
			List<DataMap> list = pdcphpc0001Service.selectPostList(addressDivn, paramMap);
			int totalCnt = 0;
			if (list.size() > 0) {
				//totalCnt = list.get(0).getInt("TOTAL_COUNT");		
				for (int i = 0; i < list.size(); i++) {
					DataMap map = (DataMap) list.get(i);
					map.put("SEQC", StringUtil.null2str(map.getString("ZIP_SEQ"), ""));
					map.put("POST_NO", StringUtil.null2str(map.getString("ZIP_CD").substring(0, 3) + "-" + map.getString("ZIP_CD").substring(3), ""));
					map.put("ALL_ADDR", StringUtil.null2str(map.getString("ADDR_NM0") + " " + map.getString("ADDR_NM1"), ""));
					map.put("POST_ADDR", StringUtil.null2str(map.getString("ADDR_NM0"), ""));
					map.put("DTL_ADDR", StringUtil.null2str(map.getString("ADDR_NM1"), ""));
					map.put("ONLINE_STR_CD", StringUtil.null2str(map.getString("ONLINE_STR_CD"), ""));
					map.put("ON_STR_CD", StringUtil.null2str(map.getString("ON_STR_CD"), ""));
					map.put("PRST_MALL_STR_CD", StringUtil.null2str(map.getString("PRST_MALL_STR_CD"), ""));
					map.put("REFGT_DIRDEL_STR_CD", StringUtil.null2str(map.getString("REFGT_DIRDEL_STR_CD"), ""));
				}
			}
			rtnMap = JsonUtils.convertList2Json((List) list, totalCnt, param.getString("currentPage"));

			// 처리성공
			rtnMap.put("result", true);

		} catch (Exception e) {
			// 작업오류
			logger.error("error --> " + e.getMessage());
			rtnMap.put("result", false);
			rtnMap.put("Message", e.getMessage());
		}

		return rtnMap;

	}
/*	public String search(HttpServletRequest request) throws Exception {
		
		GridData gdRes = new GridData();
		
		try {
			// 파라미터 획득
			String wiseGridData = request.getParameter("WISEGRID_DATA");
			GridData  gdReq = OperateGridData.parse(wiseGridData);
			gdRes = OperateGridData.cloneResponseGridData(gdReq);
			
			gdRes.addParam("mode", gdReq.getParam("mode"));
			
//			PCCPCOM0003Model pccpcom0003Model = new PCCPCOM0003Model();
//			pccpcom0003Model.setVirtureStrcd(Const.VIRTURE_STRCD);
//			RequestUtils.getWiseGridParamToDTO(gdReq, pccpcom0003Model);
			
			String addressDivn 	= gdReq.getParam("addressDivn");
			String dongNm 		= gdReq.getParam("dongNm");
			String sido 		= gdReq.getParam("sido");
			String gungu 		= gdReq.getParam("gungu");
			String road 		= gdReq.getParam("road");
			String buildNum 	= gdReq.getParam("buildNum");
			String buildName 	= gdReq.getParam("buildName");
			
			//validation
			if("j".equals(addressDivn)){
				if(StringUtil.isEmpty(dongNm)){
					//읍/명/동은(는) 필수 입력값입니다.
					throw new AppException(messageSource.getMessage("msg.common.error.required", new String[] { "읍/명/동" }, Locale.getDefault()));
				}
			}
			else if("n".equals(addressDivn)){
				if(StringUtil.isEmpty(sido)){
					//시/도은(는) 필수 입력값입니다.
					throw new AppException(messageSource.getMessage("msg.common.error.required", new String[] { "시/도" }, Locale.getDefault()));
				}
				if(StringUtil.isEmpty(gungu)){
					//시/군/구은(는) 필수 입력값입니다.
					throw new AppException(messageSource.getMessage("msg.common.error.required", new String[] { "시/군/구" }, Locale.getDefault()));
				}
				if(StringUtil.isEmpty(road)){
					//도로명은(는) 필수 입력값입니다.
					throw new AppException(messageSource.getMessage("msg.common.error.required", new String[] { "도로명" }, Locale.getDefault()));
				}
			}
			//put parameter
			DataMap paramMap = new DataMap();
			paramMap.put("dongNm"	, dongNm);
			paramMap.put("sido"		, sido);
			paramMap.put("gungu"	, gungu);
			paramMap.put("road"		, road);
			paramMap.put("buildNum"	, buildNum);
			paramMap.put("buildName", buildName);
			
			// 데이터 조회
			List<DataMap> zipList = pdcphpc0001Service.selectPostList(addressDivn, paramMap);
			
			// 조회된 데이터 가 없는 경우의 처리
	        if(zipList == null || zipList.size() == 0) {
	            gdRes.setStatus("false");
	    		request.setAttribute("wizeGridResult", gdRes);
	    		return "common/wiseGridResult";
	        }
		    
	        // GridData 셋팅StringUtil.isNullToString
	        for(int i = 0; i < zipList.size(); i++) {
	        	DataMap map = (DataMap) zipList.get(i);
	        	gdRes.getHeader("SEQC").addValue(map.getString("ZIP_SEQ"), "");
	        	gdRes.getHeader("POST_NO").addValue(map.getString("ZIP_CD").substring(0,3) + "-" + map.getString("ZIP_CD").substring(3)	,"");
	        	gdRes.getHeader("ALL_ADDR").addValue(map.getString("ADDR_NM0") + " " + map.getString("ADDR_NM1"),"");
	        	gdRes.getHeader("POST_ADDR").addValue(map.getString("ADDR_NM0"),"");
	        	gdRes.getHeader("DTL_ADDR").addValue(map.getString("ADDR_NM1"),"");
	        	gdRes.getHeader("ONLINE_STR_CD")	.addValue(map.getString("ONLINE_STR_CD"),"");
	        	gdRes.getHeader("ON_STR_CD").addValue(map.getString("ON_STR_CD"),"");
	        	gdRes.getHeader("PRST_MALL_STR_CD").addValue(map.getString("PRST_MALL_STR_CD"),"");
	        	gdRes.getHeader("REFGT_DIRDEL_STR_CD").addValue(map.getString("REFGT_DIRDEL_STR_CD"),"");
	        	
	        }
	        gdRes.setMessage(messageSource.getMessage("msg.common.success.request", null, Locale.getDefault()));
			gdRes.setStatus("true");
		} catch (Exception e) {
			gdRes.setMessage(e.getMessage());
            gdRes.setStatus("false");
            logger.debug("", e);
		}
		request.setAttribute("wizeGridResult", gdRes);
		
		return "common/wiseGridResult";
	}*/

	@RequestMapping("delivery/selectNewZipCodeSigunguList.do")
	public ModelAndView selectNewZipCodeSigunguList(HttpServletRequest request) throws Throwable {
		String sido = request.getParameter("sido");

		//validation
		if (StringUtil.isEmpty(sido)) {
			//시/도은(는) 필수 입력값입니다.
			throw new AppException(messageSource.getMessage("msg.common.error.required", new String[] { "시/도" }, Locale.getDefault()));
		}

		DataMap param = new DataMap();
		param.put("sido", sido);

		List<DataMap> resultMap = null;
		try {
			resultMap = pdcphpc0001Service.selectNewZipCodeSigunguList(param);
		} catch (Exception e) {
			logger.debug(e.toString());
		}

		return AjaxJsonModelHelper.create(resultMap);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("delivery/rfnCustCommonAddrList.do")
	public ModelAndView rfnCustCommonAddrList(HttpServletRequest request) throws Throwable {
		String zipcode = request.getParameter("zipcode");
		String addr1 = request.getParameter("addr1");
		String addr2 = request.getParameter("addr2");
		String SearchMode = StringUtil.upperCase(request.getParameter("SearchMode"));

		//validation
		if (StringUtil.isEmpty(zipcode)) {
			//우편번호은(는) 필수 입력값입니다.
			throw new AppException(messageSource.getMessage("msg.common.error.required", new String[] { "우편번호" }, Locale.getDefault()));
		}
		if (StringUtil.isEmpty(addr1)) {
			//주소은(는) 필수 입력값입니다.
			throw new AppException(messageSource.getMessage("msg.common.error.required", new String[] { "주소" }, Locale.getDefault()));
		}
		if (StringUtil.isEmpty(SearchMode)) {
			throw new AppException("error ==== SearchMode[" + SearchMode + "]");
		}

		Map resultMap = null;

		try {
			rfnCustCommonAddrList rfn = new rfnCustCommonAddrList();

			// logger.debug("=================================================");
			// logger.debug("@입력파라미터");
			// logger.debug("=================================================");
			// logger.debug("zipcode	:" + zipcode);
			// logger.debug("addr1		:" + addr1);
			// logger.debug("addr2		:" + addr2);
			// logger.debug("SearchMode:" + SearchMode);

			resultMap = rfn.getRfnAddrMap(zipcode, addr1, addr2, "UTF-8", SearchMode);

			// logger.debug("=================================================");
			// logger.debug("@주소변환결과");
			// logger.debug("=================================================");
			// logger.debug("RCD1	:" + resultMap.get("RCD1"));
			// logger.debug("RMG1	:" + resultMap.get("RMG1"));
			// logger.debug("RCD2	:" + resultMap.get("RCD2"));
			// logger.debug("RMG2	:" + resultMap.get("RMG2"));
			// logger.debug("RCD3	:" + resultMap.get("RCD3"));
			// logger.debug("RMG3	:" + resultMap.get("RMG3"));
			// logger.debug("DATA_CNT:" + resultMap.get("DATA_CNT"));

			List<Map<String, String>> addrList = (List<Map<String, String>>) resultMap.get("DATA");

			// logger.debug("=================================================");
			// logger.debug("@주소변환 리스트");
			// logger.debug("=================================================");
			for (Map<String, String> addr : addrList) {
				// logger.debug("-----------------------------------------------");
				// logger.debug("NODE		:" + addr.get("NODE")); //자료데이터타입

				// logger.debug("ZIPSJ		:" + addr.get("ZIPSJ")); //정제지번 SEQ
				// logger.debug("ZIPM6		:" + addr.get("ZIPM6")); //정제지번 우편번호
				// logger.debug("ADDR1H		:" + addr.get("ADDR1H")); //정제지번 주소1
				// logger.debug("STDADDR	:" + addr.get("STDADDR")); //정제지번 주소2

				// logger.debug("ZIPR6		:" + addr.get("ZIPR6")); //도로명 우편변호
				// logger.debug("NADR1S		:" + addr.get("NADR1S")); //도로명 주소1
				// logger.debug("NADR3S		:" + addr.get("NADR3S")); //도로명 주소2

				// logger.debug("NADREH		:" + addr.get("NADREH")); //참조항목1 ??
				// logger.debug("NADREJ		:" + addr.get("NADREJ")); //참조항목2

				// logger.debug("ADDRPS		:" + addr.get("ADDRPS")); //지번PNU
				// logger.debug("NNMZ		:" + addr.get("NNMZ")); //조합된 건물관리번호

				// logger.debug("GISX		:" + addr.get("GISX")); //지번   X좌표
				// logger.debug("GISY		:" + addr.get("GISY")); //지번   Y좌표
				// logger.debug("NNMX		:" + addr.get("NNMX")); //도로명 X좌표
				// logger.debug("NNMY		:" + addr.get("NNMY")); //도로명 Y좌표

				// logger.debug("IDX		:" + addr.get("IDX")); // ??

				// logger.debug("-----------------------------------------------");
			}

			/**
			 * resultMap.get("DATA") 의 정보
			 * 
			      NADREJ	=(아현동 723-8번지)
				, NADREH	=(아현동 723-8번지)
				, ADDR1H	=서울 마포구 아현동
				, CNT		=1
				, NODE		=D
				, NADR3S	=
				, STDADDR	=723-8번지
				, NADR1S	=서울 마포구 손기정로12길 18
				, GISY		=450358
				, ZIPR6		=121858
				, IDX		=0
				, ZIPSJ		=6407733
				, ADDRPS	=1144010100007230008
				, NNMX		=196490.4023486036
				, NNMY		=450368.3985314264
				, ZIPM6		=121858
				, NNMZ		=1144041393560100001800000
				, GISX		=196482
			 */

		} catch (Exception e) {
			logger.debug(e.toString());
		}

		return AjaxJsonModelHelper.create(resultMap);
	}
}
