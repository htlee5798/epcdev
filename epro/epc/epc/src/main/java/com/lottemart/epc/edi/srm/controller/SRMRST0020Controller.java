package com.lottemart.epc.edi.srm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lcn.module.common.paging.PaginationInfo;
import lcn.module.common.util.StringUtil;
import lcn.module.framework.property.ConfigurationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottemart.epc.edi.srm.model.SRMJON0043VO;
import com.lottemart.epc.edi.srm.model.SRMRST002001VO;
import com.lottemart.epc.edi.srm.model.SRMRST002002VO;
import com.lottemart.epc.edi.srm.model.SRMRST002003VO;
import com.lottemart.epc.edi.srm.model.SRMRST002004VO;
import com.lottemart.epc.edi.srm.model.SRMRST002005VO;
import com.lottemart.epc.edi.srm.model.SRMRST002008VO;
import com.lottemart.epc.edi.srm.model.SRMRST0020VO;
import com.lottemart.epc.edi.srm.model.SRMSessionVO;
import com.lottemart.epc.edi.srm.service.SRMJON0043Service;
import com.lottemart.epc.edi.srm.service.SRMRST0020Service;
import com.lottemart.epc.edi.srm.utils.SRMCommonUtils;
import com.lottemart.epc.edi.srm.utils.SRMPagingUtils;

/**
 * 입점상담 > 입점상담결과확인  > 진행현황 Controller
 *
 * @author AN TAE KYUNG
 * @since 2016.07.07
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 * 	수정일				수정자				수정내용
 *  -----------    	------------    ---------------------------
 *   2016.07.07  	AN TAE KYUNG	최초 생성
 *	 2016.07.21  	SHIN SE JIN 	기능 추가
 * </pre>
 */

@Controller
public class SRMRST0020Controller {

	@Autowired
	private SRMRST0020Service srmrst0020Service;

	@Autowired
	private SRMJON0043Service srmjon0043Service;

	@Autowired
	private ConfigurationService config;

	/**
	 * 입점상담 결과 초기화
	 * @param HttpServletRequest
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/SRMRST0020.do")
	public String init(HttpServletRequest request, ModelMap model) throws Exception {
		SRMSessionVO session = (SRMSessionVO) request.getSession().getAttribute(config.getString("lottemart.srm.session.key"));

		/* 파트너사 상세 정보 조회 */
		SRMRST0020VO resultVO = srmrst0020Service.selectPartnerInfo(session);

		model.addAttribute("resultVO", resultVO);

		return "/edi/srm/SRMRST0020";
	}

	/**
	 * 입점상담 내역별 상태 조회
	 * @param SRMRST0020VO
	 * @param HttpServletRequest
	 * @return List<SRMRST0020VO>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/selectStatusList.json", method = RequestMethod.POST)
	public @ResponseBody HashMap<String, Object> selectStatusList(@RequestBody SRMRST0020VO vo, HttpServletRequest request) throws Exception {

		SRMSessionVO session = (SRMSessionVO) request.getSession().getAttribute(config.getString("lottemart.srm.session.key"));
		vo.setHouseCode(session.getHouseCode());
		vo.setSellerCode(session.getIrsNo());

		// Locale 설정
		vo.setLocale(SRMCommonUtils.getLocale(request));

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		List<SRMRST0020VO> list = null;

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(vo.getPageIndex());
		paginationInfo.setRecordCountPerPage(10);
		paginationInfo.setPageSize(vo.getPageSize());

		resultMap.put("paginationInfo", paginationInfo);

		vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
		vo.setLastIndex(paginationInfo.getLastRecordIndex());
		vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		int totalCnt = srmrst0020Service.selectStatusListCount(vo);
		paginationInfo.setTotalRecordCount(totalCnt);

		list = srmrst0020Service.selectStatusList(vo);
		resultMap.put("list", list);

		// 화면에 보여줄 게시물 리스트
		resultMap.put("pageIdx", vo.getPageIndex());

		// 화면에 보여줄 페이징 생성
		resultMap.put("contents", SRMPagingUtils.makingPagingContents(paginationInfo, "goPage"));

		return resultMap;
	}

	/**
	 * 상담 조회 팝업
	 * @param Model
	 * @param SRMRST002001VO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/selectCompCounselInfoPopup.do")
	public String selectCompCounselInfoPopup(Model model, SRMRST002001VO vo, HttpServletRequest request) throws Exception {
		SRMRST002001VO tmpVo = vo;
		//----- Session 에서 생성된 코드 사용 Start --------------------
		SRMSessionVO session = (SRMSessionVO) request.getSession().getAttribute(config.getString("lottemart.srm.session.key"));

		tmpVo.setHouseCode(session.getHouseCode());
		tmpVo.setSellerCode(session.getIrsNo());
		//----- Session 에서 생성된 코드 사용 End --------------------

		// Locale 설정
		tmpVo.setLocale(SRMCommonUtils.getLocale(request));

		tmpVo = srmrst0020Service.selectCompCounselInfo(tmpVo);
		model.addAttribute("srmCompCounsel",tmpVo);
		if(tmpVo != null && !StringUtil.isEmpty(tmpVo.getAttachNo())) {
			model.addAttribute("srmCompCounselFileList", srmrst0020Service.selectCompCounselFileList(tmpVo.getAttachNo()));
		}
		return "/edi/srm/SRMRST002001";
	}

	/**
	 * 품평회 조회 팝업
	 * @param Model
	 * @param SRMRST002002VO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/selectCompFairInfoPopup.do")
	public String selectCompFairInfoPopup(Model model, SRMRST002002VO vo, HttpServletRequest request) throws Exception {
		SRMRST002002VO tmpVo = vo;
		//----- Session 에서 생성된 코드 사용 Start --------------------
		SRMSessionVO session = (SRMSessionVO) request.getSession().getAttribute(config.getString("lottemart.srm.session.key"));

		tmpVo.setHouseCode(session.getHouseCode());
		tmpVo.setSellerCode(session.getIrsNo());
		//----- Session 에서 생성된 코드 사용 End --------------------

		// Locale 설정
		tmpVo.setLocale(SRMCommonUtils.getLocale(request));

		tmpVo = srmrst0020Service.selectCompFairInfo(tmpVo);
		model.addAttribute("srmCompFair",tmpVo);
		if(tmpVo != null && !StringUtil.isEmpty(tmpVo.getAttachNo())) {
			model.addAttribute("srmCompFairFileList",srmrst0020Service.selectCompCounselFileList(tmpVo.getAttachNo()));
		}

		return "/edi/srm/SRMRST002002";
	}



	/**
	 * 이행보증증권 팝업
	 * @param Model
	 * @param SRMRST002003VO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/selectCompInsInfoPopup.do")
	public String selectCompInsInfoPopup(Model model, SRMRST002003VO vo, HttpServletRequest request) throws Exception {
		//----- Session 에서 생성된 코드 사용 Start --------------------
		SRMSessionVO session = (SRMSessionVO) request.getSession().getAttribute(config.getString("lottemart.srm.session.key"));

		vo.setHouseCode(session.getHouseCode());
		vo.setSellerCode(session.getIrsNo());
		//----- Session 에서 생성된 코드 사용 End --------------------

		model.addAttribute("srmCompIns",srmrst0020Service.selectCompInsInfo(vo));
		return "/edi/srm/SRMRST002003";
	}

	/**
	 * 이행보증증권 등록
	 * @param Model
	 * @param SRMRST002003VO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/updateCompInsPopup.do")
	public String updateCompInsPopup(Model model, SRMRST002003VO vo, HttpServletRequest request) throws Exception {
		//----- Session 에서 생성된 코드 사용 Start --------------------
		SRMSessionVO session = (SRMSessionVO) request.getSession().getAttribute(config.getString("lottemart.srm.session.key"));

		vo.setHouseCode(session.getHouseCode());
		vo.setSellerCode(session.getIrsNo());
		//----- Session 에서 생성된 코드 사용 End --------------------

		if(vo != null) {
			srmrst0020Service.updateCompInsInfo(vo, request);
		}

		model.addAttribute("srmCompIns",srmrst0020Service.selectCompInsInfo(vo));
		model.addAttribute("save","Y");
		return "/edi/srm/SRMRST002003";
	}

	/**
	 * 선택한 입점상담 내역 삭제
	 * @param SRMRST0020VO
	 * @param HttpServletRequest
	 * @return List<SRMRST0020VO>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/deleteCounselInfo.json", method = RequestMethod.POST)
	public @ResponseBody String deleteCounselInfo(@RequestBody SRMRST0020VO vo, HttpServletRequest request) throws Exception {

		String msg = "";
		//----- Session 에서 생성된 코드 사용 Start --------------------
		String sessionKey = config.getString("lottemart.srm.session.key");
		SRMSessionVO session = (SRMSessionVO) request.getSession().getAttribute(sessionKey);

		vo.setHouseCode(session.getHouseCode());
		vo.setSellerCode(session.getIrsNo());
		vo.setSellerNameLoc(session.getSellerNameLoc());

		msg = srmrst0020Service.deleteCounselInfo(vo);	// 입점상담 내역 삭제

		if (msg == "logOut") {
			request.getSession().removeAttribute(sessionKey);	//session초기화
		}
		return msg;
	}


	/**
	 * 조치내역 팝업
	 * @param Model
	 * @param SRMRST002004VO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/selectCompSiteVisitCover3Popup.do")
	public String selectCompSiteVisitCover3Popup(Model model, SRMRST002004VO vo, HttpServletRequest request) throws Exception {
		//----- Session 에서 생성된 코드 사용 Start --------------------
		SRMSessionVO session = (SRMSessionVO) request.getSession().getAttribute(config.getString("lottemart.srm.session.key"));

		vo.setHouseCode(session.getHouseCode());
		//----- Session 에서 생성된 코드 사용 End --------------------

		// Locale 설정
		vo.setLocale(SRMCommonUtils.getLocale(request));

		model.addAttribute("srmCompCorver3",srmrst0020Service.selectCompSiteVisitCover3List(vo));

		return "/edi/srm/SRMRST002004";
	}



	/**
	 * 조치내역 상세 조회
	 * @param SRMRST0020VO
	 * @param HttpServletRequest
	 * @return List<SRMRST0020VO>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/selectCompSiteVisitCover3Detail.json", method = RequestMethod.POST)
	public @ResponseBody SRMRST002004VO selectCompSiteVisitCover3Detail(HttpServletRequest request, @RequestBody SRMRST002004VO vo) throws Exception {
		//----- Session 에서 생성된 코드 사용 Start --------------------
		SRMSessionVO session = (SRMSessionVO) request.getSession().getAttribute(config.getString("lottemart.srm.session.key"));

		vo.setHouseCode(session.getHouseCode());
		//----- Session 에서 생성된 코드 사용 End --------------------

		return srmrst0020Service.selectCompSiteVisitCover3Detail(vo);
	}

	/**
	 * 조치내역 등록
	 * @param Model
	 * @param SRMRST002004VO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/updateCompSiteVisitCover3DetailPopup.do")
	public String updateCompSiteVisitCover3Detail(Model model, SRMRST002004VO vo, HttpServletRequest request) throws Exception {
		//----- Session 에서 생성된 코드 사용 Start --------------------
		SRMSessionVO session = (SRMSessionVO) request.getSession().getAttribute(config.getString("lottemart.srm.session.key"));
		vo.setHouseCode(session.getHouseCode());
		vo.setSellerCode(session.getSellerCode());
		//----- Session 에서 생성된 코드 사용 End --------------------

		//저장
		srmrst0020Service.updateCompSiteVisitCover3Detail(vo);
		model.addAttribute("srmCompCorver3",srmrst0020Service.selectCompSiteVisitCover3List(vo));
		model.addAttribute("save","Y");
		model.addAttribute("evNo",vo.getEvNo());
		model.addAttribute("seq",vo.getSeq());
		return "/edi/srm/SRMRST002004";
	}


	/**
	 * 조치내역 상세 초기화
	 * @param SRMRST0020VO
	 * @param HttpServletRequest
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/updateCompSiteVisitCover3Detaildel.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateCompSiteVisitCover3Detaildel(HttpServletRequest request, @RequestBody SRMRST002004VO vo) throws Exception {
		//----- Session 에서 생성된 코드 사용 Start --------------------
		SRMSessionVO session = (SRMSessionVO) request.getSession().getAttribute(config.getString("lottemart.srm.session.key"));

		vo.setHouseCode(session.getHouseCode());
		vo.setSellerCode(session.getSellerCode());
		//----- Session 에서 생성된 코드 사용 End --------------------

		return srmrst0020Service.updateCompSiteVisitCover3Detaildel(vo);
	}


	/**
	 * 품질경영평가 기관 정보 팝업
	 * @param Model
	 * @param SRMRST002004VO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/selectCompSiteVisitCompPopup.do")
	public String selectCompSiteVisitComp(Model model, SRMRST002005VO vo, HttpServletRequest request) throws Exception {
		model.addAttribute("evlComp",srmrst0020Service.selectCompSiteVisitComp(vo, request));
		return "/edi/srm/SRMRST002005";
	}


	/**
	 * 품질경영평가 기관 선택 팝업
	 * @param Model
	 * @param SRMRST002004VO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/selectCompSiteVisitCompListPopup.do")
	public String selectCompSiteVisitCompListPopup(Model model, @RequestParam Map<String,String> paramap, SRMRST002005VO vo, HttpServletRequest request ) throws Exception {

		//----- Session 에서 생성된 코드 사용 Start --------------------
		String sessionKey = config.getString("lottemart.srm.session.key");
		SRMSessionVO session = (SRMSessionVO) request.getSession().getAttribute(sessionKey);
		vo.setHouseCode(session.getHouseCode());

		model.addAttribute("reqSeq",paramap.get("reqSeq"));
		model.addAttribute("sellerCode",paramap.get("sellerCode"));
		model.addAttribute("compList",srmrst0020Service.selectCompSiteVisitCompList(vo));
		model.addAttribute("selectedComp", srmrst0020Service.selectedEvalSellerCode(vo));
		return "/edi/srm/SRMRST002009";
	}

	/**
	 * 품질경영평가 기관 선택 후 저장 행위
	 * @param SRMRST0020VO
	 * @param HttpServletRequest
	 * @return List<SRMRST0020VO>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/UpdateCompSiteVisitComp.json", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> UpdateCompSiteVisitComp(@RequestBody SRMRST002005VO vo, HttpServletRequest request ) throws Exception {
		String msg = "";

		//----- Session 에서 생성된 코드 사용 Start --------------------
		String sessionKey = config.getString("lottemart.srm.session.key");
		SRMSessionVO session = (SRMSessionVO) request.getSession().getAttribute(sessionKey);

		vo.setHouseCode(session.getHouseCode());
		vo.setSellerCode(session.getIrsNo());

		return srmrst0020Service.UpdateCompSiteVisitComp(vo);
	}

	/**
	 * 선택한 입점상담 내역 취소
	 * @param SRMRST0020VO
	 * @param HttpServletRequest
	 * @return List<SRMRST0020VO>
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/updateCounselInfoCancel.json", method = RequestMethod.POST)
	public @ResponseBody String updateCounselInfoCancel(@RequestBody SRMRST0020VO vo, HttpServletRequest request) throws Exception {

		String msg = "";
		//----- Session 에서 생성된 코드 사용 Start --------------------
		String sessionKey = config.getString("lottemart.srm.session.key");
		SRMSessionVO session = (SRMSessionVO) request.getSession().getAttribute(sessionKey);

		vo.setHouseCode(session.getHouseCode());
		vo.setSellerCode(session.getIrsNo());

		msg = srmrst0020Service.updateCounselInfoCancel(vo);	// 입점상담 내역 삭제

		return msg;
	}

	/**
	 * 입점상담 업체 정보확인 팝업
	 * @param Model
	 * @param SRMJON0043VO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/selectHiddenCompInfoPopup.do")
	public String selectHiddenCompInfoPopup(Model model, SRMJON0043VO vo, HttpServletRequest request) throws Exception {
		model.addAttribute("srmComp", srmjon0043Service.selectHiddenComp(vo, request));
		return "/edi/srm/SRMRST002006";
	}

	/**
	 * 입점상담 업체(해외) 정보확인 팝업
	 * @param Model
	 * @param SRMJON0043VO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/selectGlobalHiddenCompInfoPopup.do")
	public String selectGlobalHiddenCompInfoPopup(Model model, SRMJON0043VO vo, HttpServletRequest request) throws Exception {

		Model tmpModel = model;
		tmpModel.addAttribute("srmComp", srmjon0043Service.selectHiddenComp(vo, request));
		return "/edi/srm/SRMRST002007";
	}

	/**
	 * MD거절 사유 조회 팝업
	 * @param Model
	 * @param SRMRST002008VO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edi/srm/selectRejectReasonInfoPopup.do")
	public String selectRejectReasonInfoPopup(Model model, SRMRST002008VO vo, HttpServletRequest request) throws Exception {
		SRMRST002008VO tmpVo = vo;
		//----- Session 에서 생성된 코드 사용 Start --------------------
		SRMSessionVO session = (SRMSessionVO) request.getSession().getAttribute(config.getString("lottemart.srm.session.key"));

		tmpVo.setHouseCode(session.getHouseCode());
		tmpVo.setSellerCode(session.getIrsNo());
		//----- Session 에서 생성된 코드 사용 End --------------------

		// Locale 설정
		tmpVo.setLocale(SRMCommonUtils.getLocale(request));

		tmpVo= srmrst0020Service.selectRejectReasonInfo(tmpVo);
		model.addAttribute("reasonInfo",tmpVo);

		return "/edi/srm/SRMRST002008";
	}


}
