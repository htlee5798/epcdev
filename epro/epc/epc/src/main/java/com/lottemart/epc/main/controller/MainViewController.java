package com.lottemart.epc.main.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lcn.module.common.util.DateUtil;
import lcn.module.common.views.AjaxJsonModelHelper;
import lcn.module.framework.property.ConfigurationService;
import lcn.module.framework.property.PropertyService;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lottemart.common.code.service.CommonCodeService;
import com.lottemart.common.file.model.FileVO;
import com.lottemart.common.file.service.FileMngService;
import com.lottemart.common.login.dao.LoginDao;
import com.lottemart.common.util.DataMap;
import com.lottemart.epc.board.model.PSCPBRD0002SearchVO;
import com.lottemart.epc.board.service.PSCPBRD0002Service;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.common.util.EPCUtil;
import com.lottemart.epc.common.util.LoginUtil;
import com.lottemart.epc.main.service.MainViewService;

/**
 * @Description : 협력사 메인화면
 * @Class Name :
 * @Description :
 * @Modification Information
 *
 *               <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 5. 오후 2:24:30 jschoi
 *
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
@Controller
public class MainViewController {

	private static final Logger logger = LoggerFactory.getLogger(MainViewController.class);

	@Resource(name = "configurationService")
	private ConfigurationService config;

	@Autowired
	private MainViewService mainViewService;

	@Resource(name = "propertiesService")
	protected PropertyService propertyService;

	@Resource(name = "FileMngService")
	private FileMngService fileMngService;

	@Autowired
	private PSCPBRD0002Service pscpbrd0002Service;

	@Autowired
	private CommonCodeService commonCodeService;

	@Autowired
	private LoginDao loginDao;

	/**
	 * @Description : 협력사Top
	 * @Method Name : viewScmTop
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("main/viewScmTop.do")
	public String viewScmTop(HttpServletRequest request) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);
		request.setAttribute("epcLoginVO", epcLoginVO);

		return "main/scmTop";
	}

	/**
	 * @Description : 협력사Left
	 * @Method Name : viewScmLeft
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("main/viewScmLeft.do")
	public String viewScmLeft(HttpServletRequest request) throws Exception {
		String menuGb = "";
		menuGb = request.getParameter("menuGb");

		if (menuGb == null || "".equals(menuGb)) {
			menuGb = "1";
		}

		request.setAttribute("menuGb", menuGb);

		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = null;
		epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);
		request.setAttribute("epcLoginVO", epcLoginVO);

		return "main/scmLeft";
	}

	/**
	 * @Description : SCM Content Frame
	 * @Method Name : viewScmContent
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("main/viewScmContent.do")
	public String viewScmContent(HttpServletRequest request) throws Exception {

		return "main/scmContentStart";
	}

	/**
	 * @Description : 협력사 Main Frame
	 * @Method Name : viewScmMain
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("main/viewScmMain.do")
	public String viewScmMain(HttpServletRequest request) throws Exception {

		String sessionKey = config.getString("lottemart.epc.session.key");
		//logger.debug("sessingKey = [" + sessionKey + "]");

		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		if (epcLoginVO == null) {
			//logger.debug("Main Access error : session value 가 존재하지 않습니다.");
			throw new IllegalArgumentException("세션정보가 올바르지 않습니다. 다시 로그인하여 주세요");
		}
		request.setAttribute("epcLoginVO", epcLoginVO);
		return "main/scmIndex";
	}

	/**
	 * @Description : 협력사Intro
	 * @Method Name : vendorIntro
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("main/vendorIntro.do_old")
	public String vendorIntro(HttpServletRequest request) throws Exception {
		String sessionKey = config.getString("lottemart.epc.session.key");
		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(sessionKey);

		request.setAttribute("epcLoginVO", epcLoginVO);

		String[] cono = epcLoginVO.getCono();

		// 협력업체코드 설정
		DataMap paramMap = new DataMap();
		paramMap.put("cono", cono[0]);
		paramMap.put("vendorId", LoginUtil.getVendorList(epcLoginVO));
		// 데이터 조회
		
//임시주석		List<DataMap> list = mainViewService.selectIntroCountList(paramMap);
		//원래주석 request.setAttribute("list", list);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
		Calendar calVal = Calendar.getInstance();

		// 시작일자를 현재날짜 한달전으로 셋팅
		calVal.add(Calendar.DAY_OF_MONTH, -31);
		String startDate = dateFormat.format(calVal.getTime());

		/* 1.센터입고거부상품 RFC 요청 조회 =================================================== */
		// RFC 전송할 VEN_CD List 선언
		List<HashMap> lsHmap = new ArrayList<HashMap>();

		// RFC 전송할 두번째 TAB2 배열 선언
		ArrayList arrParam = new ArrayList();

		// RFC 응답을 받을 HashMap
		HashMap reqCommonMap = new HashMap(); // RFC 응답

		// 로그인 세션에 담긴 vendor list를 List<HashMap>에 add
		for (int i = 0; i < LoginUtil.getVendorList(epcLoginVO).size(); i++) {
			HashMap hsMsp = new HashMap();
			hsMsp.put("VEN_CD", LoginUtil.getVendorList(epcLoginVO).get(i).toString());

			lsHmap.add(hsMsp);
		}

		// RFC 응답으로 부터 받는 Key
		reqCommonMap.put("ZPOSOURCE", "");
		reqCommonMap.put("ZPOTARGET", "");
		reqCommonMap.put("ZPONUMS", "");
		reqCommonMap.put("ZPOROWS", "");
		reqCommonMap.put("ZPODATE", "");
		reqCommonMap.put("ZPOTIME", "");

		// json Object 선언
		JSONObject obj = new JSONObject();

		// RFC 전송할 Value
		obj.put("START_DATE", startDate.replaceAll("-", ""));
		obj.put("END_DATE", DateUtil.getToday("yyyyMMdd"));
		obj.put("TAB1", lsHmap);
		obj.put("TAB2", arrParam);

		obj.put("REQCOMMON", reqCommonMap); // RFC 응답 HashMap JsonObject로....

		// RFC로 전송할 json Object 로그출력
		logger.debug("obj.toString====" + obj.toString());

		// ----- 1.RFC CALL("proxyNm", String, String);
		Map<String, Object> rfcMap;

		/*try {
			rfcMap = rfcCommonService.rfcCall("INV0570", obj.toString(), "MAIN");

			//----- 2.RFC 응답 메세지의 성공 / 실패 여부에 따라 리턴메세지를 처리해준다.
			JSONObject mapObj 			= new JSONObject(rfcMap.toString());								//MAP에 담긴 응답메세지를 JSONObject로.................
			JSONObject resultObj    	= mapObj.getJSONObject("result");									//JSONObject에 담긴 응답메세지의 키는 result로 넘어 오기 떄문에 result로 꺼낸다.
			JSONObject respCommonObj    = resultObj.getJSONObject ("RESPCOMMON");							//<-------RESPCOMMON이 RFC 오리지날 응답메세지다.
			String Inv0570Row			= StringUtils.trimToEmpty(respCommonObj.getString("ZPOROWS"));		//RFC 호출 결과 Row수
			=========================================================================

			 2.불량상품 RFC 요청 조회 ===================================================
			//RFC 전송할 VEN_CD List 선언
			List<HashMap>	badProdlsHmap	=	new ArrayList<HashMap>();

			//RFC 전송할 두번째 TAB2 배열 선언
			ArrayList	badProdArrParam	=	new ArrayList();

			//RFC 응답을 받을 HashMap
			HashMap	 		badProdReqCommonMap	=	new HashMap();

			//로그인 세션에 담긴 vendor list를  List<HashMap>에 add
			for (int i = 0; i < LoginUtil.getVendorList(epcLoginVO).size(); i++) {
				HashMap hsMsp	=	new HashMap();
				hsMsp.put("VEN_CD", LoginUtil.getVendorList(epcLoginVO).get(i).toString());

				lsHmap.add(hsMsp);
			}

			//RFC 응답으로 부터 받는 Key
			badProdReqCommonMap.put("ZPOSOURCE", "");
			badProdReqCommonMap.put("ZPOTARGET", "");
			badProdReqCommonMap.put("ZPONUMS", "");
			badProdReqCommonMap.put("ZPOROWS", "");
			badProdReqCommonMap.put("ZPODATE", "");
			badProdReqCommonMap.put("ZPOTIME", "");

			//json Object 선언
			JSONObject badProdObj	=	new JSONObject();

			//RFC 전송할 Value
			obj.put("START_DATE", 	startDate.replaceAll("-", ""));
			obj.put("END_DATE"	, 	DateUtil.getToday("yyyyMMdd"));
			obj.put("TAB1"		, 	badProdlsHmap);
			obj.put("TAB2"		, 	badProdArrParam);

			obj.put("REQCOMMON", badProdReqCommonMap);	// RFC 응답 HashMap JsonObject로....

			//RFC로 전송할 json Object 로그출력
			logger.debug("obj.toString====" + badProdObj.toString());

			//----- 1.RFC CALL("proxyNm", String, String);
			Map<String, Object> badProdRfcMap;

			badProdRfcMap = rfcCommonService.rfcCall("INV0600", obj.toString(), "MAIN");

			//----- 2.RFC 응답 메세지의 성공 / 실패 여부에 따라 리턴메세지를 처리해준다.
			JSONObject badProdMapObj 			= new JSONObject(badProdRfcMap.toString());									//MAP에 담긴 응답메세지를 JSONObject로.................
			JSONObject badProdResultObj    		= badProdMapObj.getJSONObject("result");									//JSONObject에 담긴 응답메세지의 키는 result로 넘어 오기 떄문에 result로 꺼낸다.
			JSONObject badProdRespCommonObj    	= badProdResultObj.getJSONObject ("RESPCOMMON");							//<-------RESPCOMMON이 RFC 오리지날 응답메세지다.
			String Inv0600Row					= StringUtils.trimToEmpty(badProdRespCommonObj.getString("ZPOROWS"));		//RFC 호출 결과 Row수
			=========================================================================

			//메인화면 불량상품, 입고거부상품, POG 이미지 반려건수, 삼진아웃제 차수 value
			ArrayList al	=	new ArrayList();

			al.add(Inv0600Row);
			al.add(Inv0570Row);
			al.add(list.get(0).get("CNT"));
			al.add(list.get(1).get("CNT"));

			request.setAttribute("list", al);
		} catch (Exception e) {
		}*/

		// 데이터 조회
		//List<DataMap> lista = mainViewService.selectBoardList();
		//model.addAttribute("list", list);
		//logger.debug("list ==>" + lista.size() + "<==");

		//List<DataMap> popupList = mainViewService.selectPopupBoardList();
		//request.setAttribute("popupList", popupList);
		//logger.debug("popupList ==>" + popupList.size() + "<==");

		// 발송, 미발송 alert용 2017-04-04 DB
/*		if(epcLoginVO.getVendorId() != null){
			PSCMDLV0005VO  searchVO =new PSCMDLV0005VO();
			searchVO.setVendorId(epcLoginVO.getVendorId());
			List<DataMap> holyList = pscmdlv0005Service.selectPartnerFirmsStatus_All(searchVO);
			request.setAttribute("AD_01", holyList.get(0).getString("AD_01"));	// 미확인건
			request.setAttribute("AD_02", holyList.get(0).getString("AD_02"));	// 발송 예정건
			request.setAttribute("T",'T');
		}
		*/

		// 메인 공지사항 조회
//임시주석		List<DataMap> list2 = mainViewService.selectBoardList();
		List<DataMap> list2 = new ArrayList<DataMap>();
		
		// model.addAttribute("list", list);
		request.setAttribute("list", list2);
		//logger.debug("list ==>" + list2.size() + "<==");

//임시주석		List<DataMap> popupList = mainViewService.selectPopupBoardList(); // 로그인후로 추가
		List<DataMap> popupList = new ArrayList<DataMap>();
		request.setAttribute("popupList", popupList); // 로그인후로 추가
		//logger.debug("popupList ==>" + popupList.size() + "<==");

		if (!StringUtils.isEmpty(epcLoginVO.getRepVendorId())) {
			paramMap.put("repVendorId", epcLoginVO.getRepVendorId());
			// 위수탁 업체 로그인 시 미처리 문의건 확인
	//임시주석		int totalQnaChkCtn = mainViewService.selectTotalQnaChkCnt(paramMap);
			// 업체공통배송조건 및 무료배송여부 셋팅
	// 임시주석		String nochDeliYn = mainViewService.selectEdiNochDeliYn(paramMap);

			int totalQnaChkCtn = 0;
			String nochDeliYn = "Y";
			
			request.setAttribute("CHK_01", totalQnaChkCtn);
			request.setAttribute("CHK_02", nochDeliYn);
		}
		
		// 팝업버튼제어 공통코드 조회
//임시주석		List<DataMap> codeList = commonCodeService.getCodeList("EPC02");
		List<DataMap> codeList = new ArrayList<DataMap>();
		request.setAttribute("codeList", codeList);

		return "main/afMain";
	}

	/**
	 * @Description : 협력사초기화면
	 * @Method Name : Intro
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("main/intro.do")
	public String intro(ModelMap model, HttpServletRequest request) throws Exception {

		// 데이터 조회
//		List<DataMap> list = mainViewService.selectBoardList();
		List<DataMap> list = new ArrayList<DataMap>();
		model.addAttribute("list", list);
		//logger.debug("list ==>" + list.size() + "<==");

//		List<DataMap> popupList = mainViewService.selectPopupBoardList();
		List<DataMap> popupList = new ArrayList<DataMap>();
		request.setAttribute("popupList", popupList);
		// logger.debug("popupList ==>" + popupList.size() + "<==");

		// 로그인버튼제어 공통코드 조회
		List<DataMap> codeList = commonCodeService.getCodeList("EPC01");
		request.setAttribute("codeList", codeList);

		return "main/bfMain";
	}

	/**
	 * @Description : 협력사초기화면에서 공지팝업
	 * @Method Name : Intro
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("main/popUpintro.do")
	public String popUpintro(@ModelAttribute("searchvo") PSCPBRD0002SearchVO vo, ModelMap model, HttpServletRequest request) throws Exception {
		PSCPBRD0002SearchVO paramVO = pscpbrd0002Service.selectDetailPopup(vo);

		List<FileVO> fileList = null;
		if (paramVO.getAtchFileId() != null && !"".equals(paramVO.getAtchFileId())) {
			FileVO fileVO = new FileVO();
			fileVO.setAtchFileId(paramVO.getAtchFileId());
			fileList = fileMngService.selectFileInfs(fileVO);
		}

		// HTML태그가 같이 보여져서 수정(2023.11.28)
		paramVO.setContent(StringEscapeUtils.unescapeHtml(paramVO.getContent()));
				
		model.addAttribute("detail", paramVO);
		model.addAttribute("fileList", fileList);
		return "main/popUpMain";
	}

	/**
	 * @Description : 2차인증 페이지
	 * @Method Name : smsAuthMain
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@SuppressWarnings("unused")
	@RequestMapping("main/smsAuthMain.do")
	public String smsAuthMain(HttpServletRequest request) throws Exception {

		String tempKey = config.getString("lottemart.epc.temp.key"); // 2차 인증용 임시세션
		//logger.debug("tempKey = [" + tempKey + "]");

		EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(tempKey);

		if (epcLoginVO == null) {
			throw new IllegalArgumentException("세션정보가 올바르지 않습니다. 다시 로그인하여 주세요");
		}

		String[] cono = epcLoginVO.getCono();

		// 협력업체코드 설정
		DataMap paramMap = new DataMap();
		paramMap.put("cono", cono[0]);
		paramMap.put("vendorId", LoginUtil.getVendorList(epcLoginVO));
		List<DataMap> vendorList = mainViewService.selectVendorList(cono);

		request.setAttribute("epcLoginVO", epcLoginVO);
		request.setAttribute("vendorList", vendorList);
		return "main/smsAuthMain";
	}
	
	/**
	 * @Description : 2차인증 개인정보 수집이용동의 안내팝업
	 * @Method Name : smsAuthUserInfoApplyNoti
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("main/smsAuthUserInfoApplyNoti.do")
	public String smsAuthUserInfoApplyNoti(HttpServletRequest request) throws Exception {
		return "main/smsAuthUserInfoApplyNoti";
	}

	/**
	 * @Description : 협력업체 전화번호 조회
	 * @Method Name : smsVendorUserTelSelect
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("main/smsVendorUserTelSelect.do")
	public ModelAndView smsVendorUserTelSelect(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("UTF-8");
		DataMap result = new DataMap();
		result.put("vendorId",request.getParameter("vendorId"));
		result.put("searchCellNo",request.getParameter("searchCellNo"));
		List<DataMap> userTelList = mainViewService.vendorUserTelList(result);
		return AjaxJsonModelHelper.create(userTelList);
	}

	/**
	 * @Description : SMS 인증 코드 발송
	 * @Method Name : smsAuthCodeInsert
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("main/smsAuthCodeInsert.do")
	public ModelAndView smsAuthCodeInsert(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("UTF-8");
		DataMap result = new DataMap();
		int smsSuccCode = 0;
		String vendorId = request.getParameter("vendorId");
		result.put("vendorSeq", request.getParameter("userTel"));
		result.put("vendorId", vendorId);

		List<DataMap> userTelList = mainViewService.vendorUserTelList(result);
		DataMap userTelMap = userTelList.get(0);
		String userTel = userTelMap.getString("SMSTEL");
		String userNm = userTelMap.getString("USERNM");
		String userTelMasking =  userTelMap.getString("USERTEL");
		result.put("userTel", userTel);
		String servletPath = ((HttpServletRequest) request).getServletPath();
		Random random = new Random();
		int smsAuthCode = random.nextInt(888888) + 111111;
		result.put("smsAuthCode", smsAuthCode);
		// 인증 가능한 유효시간이 지날경우 초기화
		String timeFlag = request.getParameter("timeFlag");

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("menuId", "SCM_VE");
		paramMap.put("adminId", vendorId);
		paramMap.put("workIP", request.getRemoteAddr());
		paramMap.put("url", servletPath);

		EPCUtil epcUtil = new EPCUtil();
		StringBuffer sb = new StringBuffer();

		if ("N".equals(timeFlag)) {
			sb.append("인증코드 N: ").append(vendorId).append(",").append(result.getString("vendorSeq")).append(",")
			.append(userNm).append(",").append(userTelMasking).append(",")
			.append("DB:").append(mainViewService.getSystimestampMs()).append(" / ")
			.append("WAS:").append(epcUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss.SSS"));

			result.put("smsAuthCode","N");
			mainViewService.smsCodeUpdate(result);

			sb.append(" ~ ").append("DB:").append(mainViewService.getSystimestampMs()).append(" / ")
			.append("WAS:").append(epcUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss.SSS"));

			paramMap.put("param", sb.toString());
			loginDao.insertAdminWorkLogSCM(paramMap); // 로그 저장
		} else {
			sb.append("인증코드 I: ").append(vendorId).append(",").append(result.getString("vendorSeq")).append(",")
			.append(userNm).append(",").append(userTelMasking).append(",").append(smsAuthCode).append(",")
			.append("DB:").append(mainViewService.getSystimestampMs()).append(" / ")
			.append("WAS:").append(epcUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss.SSS"));

			smsSuccCode = mainViewService.smsAuthCodeInsert(result);

			sb.append(" ~ ").append("DB:").append(mainViewService.getSystimestampMs()).append(" / ")
			.append("WAS:").append(epcUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss.SSS"));

			paramMap.put("param", sb.toString());
			loginDao.insertAdminWorkLogSCM(paramMap); // 로그 저장
		}
		return AjaxJsonModelHelper.create(smsSuccCode);
	}

	/**
	 * @Description : SMS 인증 코드 체크
	 * @Method Name : smsCodeCheck
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("main/smsCodeCheck.do")
	public ModelAndView smsCodeCheck(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("UTF-8");
		String servletPath = ((HttpServletRequest) request).getServletPath();
		DataMap result = new DataMap();
		String resultCheck = "";
		StringBuffer sb = new StringBuffer();
		String vendorId = request.getParameter("vendorId");
		String userTel = request.getParameter("userTel");
		String personalInfoUseApply = request.getParameter("personalInfoUseApply");
		result.put("vendorId", vendorId);
		result.put("vendorSeq", userTel);
		String authNumber = request.getParameter("authNumber");

		DataMap resultMap = mainViewService.smsCodeCheckSelect(result);
		String smsCode = (String) resultMap.get("SMSCODE");
		String telNo = (String) resultMap.get("USERTEL");
		String userNm = (String) resultMap.get("USERNM");
		sb.append("인증로그 : ").append(vendorId).append(",").append(userTel).append(",").append(userNm).append(",").append(telNo);

		if (authNumber.equals(smsCode) && "Y".contentEquals(personalInfoUseApply)) {
			// 로그 저장
			sb.append(",").append(smsCode);
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("menuId", "SCM_VE");
			paramMap.put("adminId", vendorId);
			paramMap.put("workIP", request.getRemoteAddr());
			paramMap.put("url", servletPath);
			paramMap.put("param", sb.toString());
			loginDao.insertAdminWorkLogSCM(paramMap);
			// 인증 성공 후 인증코드 초기화
			result.put("smsAuthCode", "Y");
			result.put("personalInfoUseApply", personalInfoUseApply);

			mainViewService.smsCodeUpdate(result);
			mainViewService.insertUserInfoApply(result);
			String domin = config.getString("login.domain.url") + "/main/vendorIntro.do";

			// 임시세션에서 로그인세션으로 변경 (Start) - 절대 지우지 말것!!! 삭제시 로그인처리 무효화됨
			String tempKey = config.getString("lottemart.epc.temp.key");
			EpcLoginVO epcLoginVO = (EpcLoginVO) request.getSession().getAttribute(tempKey);

			String sessionKey = config.getString("lottemart.epc.session.key");
			request.getSession().setAttribute(sessionKey, epcLoginVO);

			request.getSession().removeAttribute(tempKey);
			// 임시세션에서 로그인세션으로 변경(End)

			resultCheck = domin;
		} else {
			resultCheck = "false";
		}
		return AjaxJsonModelHelper.create(resultCheck);
	}

	/**
	 * @Description : 세션아웃
	 * @Method Name : session out
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("main/sessionOut.do")
	public String popUpintro(ModelMap model, HttpServletRequest request) throws Exception {
		return "common/endOfSessionError";
	}

	/**
	 * @Description : 개인정보보호 안내 팝업
	 * @Method Name : personalInfoPolicy
	 * @return
	 * @throws Exception
	 * @param
	 * @return
	 * @exception Exception
	 */
	@RequestMapping("main/personalInfoPolicy.do")
	public String personalInfoPolicy(HttpServletRequest request) throws Exception {
		return "main/personalInfoPolicy";
	}

}
